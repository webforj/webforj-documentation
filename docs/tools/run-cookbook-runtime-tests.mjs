#!/usr/bin/env node

import { execFileSync } from 'child_process';
import {
  existsSync,
  mkdirSync,
  readFileSync,
  writeFileSync,
} from 'fs';
import { tmpdir } from 'os';
import { basename, dirname, join, resolve } from 'path';
import { setTimeout as delay } from 'timers/promises';
import { fileURLToPath } from 'url';

const __dirname = dirname(fileURLToPath(import.meta.url));
const docsRoot = resolve(__dirname, '..');
const repoRoot = resolve(docsRoot, '..');
const runtimeRoot = join(repoRoot, 'target', 'cookbook-runtime-tests');
const manifestFile = join(runtimeRoot, 'routes.json');
const classpathFile = join(runtimeRoot, 'classpath.txt');
const runnerRoot = join(
  tmpdir(),
  'webforj-cookbook-runtime-tests',
  basename(repoRoot),
);
const runnerSource = join(runnerRoot, 'CookbookRuntimePlaywright.java');
const runnerClasses = join(runnerRoot, 'classes');
const targetClasses = join(repoRoot, 'target', 'classes');

function run(command, args, options = {}) {
  return execFileSync(command, args, {
    cwd: options.cwd ?? repoRoot,
    encoding: 'utf8',
    stdio: options.stdio ?? 'pipe',
  });
}

function javaString(value) {
  return `"${value
    .replace(/\\/g, '\\\\')
    .replace(/"/g, '\\"')
    .replace(/\r/g, '\\r')
    .replace(/\n/g, '\\n')}"`;
}

async function waitForServer(url) {
  let consecutiveSuccesses = 0;
  let lastError = 'no response';

  for (let attempt = 0; attempt < 240; attempt += 1) {
    try {
      const response = await fetch(url, { redirect: 'manual' });
      if (response.status === 200) {
        consecutiveSuccesses += 1;
        if (consecutiveSuccesses >= 10) {
          return;
        }
      } else {
        consecutiveSuccesses = 0;
        lastError = `HTTP ${response.status}`;
      }
    } catch (error) {
      consecutiveSuccesses = 0;
      lastError = error.message;
    }
    await delay(500);
  }

  throw new Error(`Cookbook test server did not become stable: ${lastError}`);
}

function buildRunnerSource(manifest) {
  const cases = manifest.map((entry) => {
    const expected = entry.expected.map(javaString).join(', ');
    return `new TestCase(${javaString(entry.recipe)}, ${javaString(entry.route)}, List.of(${expected}))`;
  }).join(',\n        ');

  return `
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.BoundingBox;
import com.microsoft.playwright.options.LoadState;
import com.microsoft.playwright.options.WaitUntilState;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class CookbookRuntimePlaywright {
  private record TestCase(String recipe, String url, List<String> expected) {}
  private record Result(String recipe, boolean passed, String detail) {}

  private static final List<TestCase> TESTS = List.of(
        ${cases});

  private static final Path SCREENSHOTS =
      Paths.get("target", "cookbook-runtime-tests", "screenshots");

  public static void main(String[] args) throws Exception {
    Files.createDirectories(SCREENSHOTS);
    List<Result> results = new ArrayList<>();

    try (Playwright playwright = Playwright.create()) {
      Browser browser = playwright.chromium().launch(
          new BrowserType.LaunchOptions().setHeadless(true));
      BrowserContext context = browser.newContext(
          new Browser.NewContextOptions()
              .setViewportSize(1440, 900)
              .setIgnoreHTTPSErrors(true));

      for (TestCase test : TESTS) {
        Page page = context.newPage();
        List<String> errors = new ArrayList<>();
        page.onConsoleMessage(message -> {
          if ("error".equals(message.type())) {
            errors.add("console: " + message.text());
          }
        });
        page.onPageError(error -> errors.add("page: " + error));

        try {
          page.navigate(
              test.url(),
              new Page.NavigateOptions()
                  .setWaitUntil(WaitUntilState.DOMCONTENTLOADED)
                  .setTimeout(30000));
          page.waitForLoadState(LoadState.DOMCONTENTLOADED);
          page.waitForTimeout(900);

          assertExpectedText(page, test);
          runRecipeChecks(page, test.recipe());

          List<String> unexpectedErrors =
              errors.stream()
                  .filter(error -> !isKnownTableRuntimeError(test.recipe(), error))
                  .toList();
          if (!unexpectedErrors.isEmpty()) {
            throw new AssertionError(String.join(" | ", unexpectedErrors));
          }
          results.add(new Result(test.recipe(), true, "OK"));
          System.out.println("PASS " + test.recipe());
        } catch (Throwable error) {
          String slug = test.recipe().replaceAll("[^A-Za-z0-9]+", "-");
          Path screenshot = SCREENSHOTS.resolve(slug + ".png");
          try {
            page.screenshot(new Page.ScreenshotOptions().setPath(screenshot).setFullPage(true));
          } catch (Throwable ignored) {
          }
          String detail = error.getMessage() == null ? error.toString() : error.getMessage();
          results.add(new Result(test.recipe(), false, detail));
          System.out.println("FAIL " + test.recipe() + " - " + detail);
          System.out.println("     screenshot: " + screenshot);
        } finally {
          page.close();
        }
      }

      context.close();
      browser.close();
    }

    long failures = results.stream().filter(result -> !result.passed()).count();
    System.out.println();
    System.out.println("Cookbook runtime tests: " + results.size() + ", failures: " + failures);
    if (failures > 0) {
      System.exit(1);
    }
  }

  private static void assertExpectedText(Page page, TestCase test) {
    for (String expected : test.expected()) {
      Locator match = page.getByText(expected);
      match.first().waitFor(new Locator.WaitForOptions().setTimeout(15000));
      if (!match.first().isVisible()) {
        throw new AssertionError("Expected text is not visible: " + expected);
      }
    }
  }

  private static void runRecipeChecks(Page page, String recipe) {
    switch (recipe) {
      case "components/card-with-slots.md" -> {
        assertVisibleBox(page.locator(".cookbook-card"), "card");
        assertVisible(page.getByText("Confirm subscription"), "card title");
      }
      case "components/enum-property-on-element-composite.md" -> {
        Locator card = page.locator("my-card");
        card.waitFor(
            new Locator.WaitForOptions()
                .setState(com.microsoft.playwright.options.WaitForSelectorState.ATTACHED)
                .setTimeout(10000));
        assertVisible(page.getByText("Variant: DANGER"), "enum property value");
      }
      case "components/save-feedback-toast.md" -> {
        page.getByText("Save").click();
        Locator toast = page.locator("dwc-toast").last();
        assertVisibleBox(toast, "success toast");
      }
      case "components/typed-custom-event-from-composite.md" -> {
        Locator stars = page.locator("dwc-button");
        if (stars.count() < 4) {
          throw new AssertionError("Expected five rating buttons, got " + stars.count());
        }
        stars.nth(3).click();
        assertVisible(page.getByText("Rating: 4"), "rating event output");
      }
      case "css/hide-on-small-screens.md" -> {
        Locator sidebar = page.locator(".cookbook-responsive-hide__sidebar");
        assertVisible(sidebar, "responsive sidebar");
        page.setViewportSize(600, 900);
        page.waitForTimeout(150);
        if (sidebar.isVisible()) {
          throw new AssertionError("Sidebar remains visible below 768px");
        }
        page.setViewportSize(1440, 900);
      }
      case "css/inject-inline-css.md" -> {
        Locator highlight = page.locator(".highlight");
        assertVisible(highlight, "highlight element");
        String weight = highlight.evaluate("element => getComputedStyle(element).fontWeight").toString();
        String background =
            highlight.evaluate("element => getComputedStyle(element).backgroundColor").toString();
        if (!("600".equals(weight) || "700".equals(weight) || "bold".equals(weight))) {
          throw new AssertionError("Highlight font weight is not emphasized: " + weight);
        }
        if ("rgba(0, 0, 0, 0)".equals(background) || "transparent".equals(background)) {
          throw new AssertionError("Highlight background was not applied");
        }
      }
      case "forms/debounced-email-validation.md" -> {
        Locator field = firstInput(page);
        field.fill("invalid");
        page.waitForTimeout(750);
        Locator validation = page.locator("div[class*='dwc-positioner']");
        assertVisible(validation, "invalid email message");
        field.fill("valid@example.com");
        page.waitForTimeout(750);
        if (validation.isVisible()) {
          throw new AssertionError("Invalid email message did not clear");
        }
      }
      case "forms/dialog-form-with-binding-context.md" -> {
        page.getByText("Open contact dialog").click();
        assertVisible(page.getByText("Edit contact"), "contact dialog");
        page.getByText("Save", new Page.GetByTextOptions().setExact(true)).click();
        assertVisible(page.getByText("Saved: Ada Lovelace / ada@example.com"), "saved contact");
      }
      case "forms/disable-button-during-save.md" -> {
        page.getByText("Save").click();
        assertVisible(page.getByText("Saved"), "completed save button");
      }
      case "javascript/call-dom-method.md" -> {
        page.getByText("Jump to footer").click();
        page.waitForTimeout(900);
        Locator footer = page.getByText("You scrolled here from Java.");
        assertVisible(footer, "footer");
        BoundingBox box = footer.boundingBox();
        if (box == null || box.y > 900) {
          throw new AssertionError("Footer was not scrolled into the viewport");
        }
      }
      case "layout/centered-layout.md" -> {
        Locator text = page.getByText("Centered content");
        assertVisible(text, "centered text");
        BoundingBox textBox = text.boundingBox();
        BoundingBox parentBox = text.locator("xpath=..").boundingBox();
        if (textBox == null || parentBox == null) {
          throw new AssertionError("Unable to measure centered content");
        }
        double textCenterX = textBox.x + textBox.width / 2;
        double parentCenterX = parentBox.x + parentBox.width / 2;
        if (Math.abs(textCenterX - parentCenterX) > 8) {
          throw new AssertionError("Content is not horizontally centered");
        }
      }
      case "routing/dynamic-page-title.md" -> {
        page.waitForFunction(
            "() => document.title.startsWith('Product #42 - My Shop')",
            null,
            new Page.WaitForFunctionOptions().setTimeout(10000));
        String title = page.title();
        if (!title.startsWith("Product #42 - My Shop")) {
          throw new AssertionError("Unexpected page title: " + title);
        }
      }
      case "routing/navigate-programmatically.md" -> {
        page.getByText("Go to Dashboard").click();
        page.waitForTimeout(500);
        if (!page.url().endsWith("/cookbook-test/navigate/dashboard")) {
          throw new AssertionError("Unexpected navigation URL: " + page.url());
        }
      }
      case "routing/open-in-new-tab.md" -> {
        Page popup = page.waitForPopup(
            () -> page.getByText("Open report in new tab").click());
        popup.waitForLoadState(LoadState.DOMCONTENTLOADED);
        if (!popup.url().endsWith("/cookbook-test/open-in-new-tab/report")) {
          throw new AssertionError("Unexpected popup URL: " + popup.url());
        }
        popup.close();
      }
      case "table/boolean-column-renderer.md" -> {
        Locator table = page.locator("dwc-table");
        assertVisibleBox(table, "table");
        assertVisibleBox(
            page.locator("dwc-table dwc-icon[name='circle-check']"),
            "true boolean icon");
        assertVisibleBox(
            page.locator("dwc-table dwc-icon[name='circle-x']"),
            "false boolean icon");
      }
      case "table/empty-state-message.md",
           "table/persist-column-widths.md" -> {
        Locator table = page.locator("dwc-table");
        assertVisibleBox(table, "table");
      }
      case "theme/AppThemeToggle.md" -> {
        Locator button = page.locator("dwc-icon").first();
        assertVisible(button, "theme toggle");
        String before = String.valueOf(
            page.locator("html").getAttribute("data-app-theme"));
        button.click();
        page.waitForTimeout(250);
        String after = String.valueOf(
            page.locator("html").getAttribute("data-app-theme"));
        if (before.equals(after)) {
          throw new AssertionError("Theme did not change after clicking the toggle");
        }
      }
      default -> {
        // Route load and expected text checks cover this recipe.
      }
    }
  }

  private static Locator firstInput(Page page) {
    Locator inputs = page.locator("input");
    if (inputs.count() == 0) {
      throw new AssertionError("No input element found");
    }
    return inputs.first();
  }

  private static void assertVisible(Locator locator, String label) {
    locator.waitFor(new Locator.WaitForOptions().setTimeout(10000));
    if (!locator.isVisible()) {
      throw new AssertionError(label + " is not visible");
    }
  }

  private static void assertVisibleBox(Locator locator, String label) {
    assertVisible(locator, label);
    BoundingBox box = locator.boundingBox();
    if (box == null || box.width <= 1 || box.height <= 1) {
      throw new AssertionError(label + " has no rendered size");
    }
  }

  private static boolean isKnownTableRuntimeError(String recipe, String error) {
    return recipe.startsWith("table/")
        && error.contains("Cannot read properties of null (reading 'removeAttribute')")
        && error.contains("p-060bbddc.entry.js");
  }
}
`;
}

async function main() {
  if (!existsSync(manifestFile) || !existsSync(classpathFile)) {
    throw new Error(
      'Runtime harness is not prepared. Run npm run prepare:cookbook-runtime-tests first.',
    );
  }

  const manifest = JSON.parse(readFileSync(manifestFile, 'utf8'));
  mkdirSync(dirname(runnerSource), { recursive: true });
  mkdirSync(runnerClasses, { recursive: true });
  writeFileSync(runnerSource, buildRunnerSource(manifest), 'utf8');

  const dependencies = readFileSync(classpathFile, 'utf8').trim();
  const separator = process.platform === 'win32' ? ';' : ':';
  const classpath = `${targetClasses}${separator}${dependencies}`;
  const javac = process.platform === 'win32' ? 'javac.exe' : 'javac';
  const java = process.platform === 'win32' ? 'java.exe' : 'java';

  run(javac, [
    '--release',
    '21',
    '-classpath',
    classpath,
    '-d',
    runnerClasses,
    runnerSource,
  ], { stdio: 'inherit' });

  await waitForServer(manifest[0].route);

  run(java, [
    '-classpath',
    `${runnerClasses}${separator}${classpath}`,
    'CookbookRuntimePlaywright',
  ], { stdio: 'inherit' });
}

await main();
