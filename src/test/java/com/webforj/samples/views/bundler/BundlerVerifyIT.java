package com.webforj.samples.views.bundler;

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.microsoft.playwright.ConsoleMessage;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.TimeoutError;
import com.microsoft.playwright.options.LoadState;
import com.webforj.samples.views.BaseTest;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Sanity walk over every routed view whose @StyleSheet / @JavaScript annotation was migrated
 * to @BundleEntry / @BundlePackage. Navigates each route, waits for network idle, screenshots, and
 * fails the test if any page throws a JS console error.
 *
 * <p>Run headed with slow-mo so a human can watch:
 *
 * <pre>
 *   mvn verify -Dit.test=BundlerVerifyIT -Dheadless=false -Dslowmo=250
 * </pre>
 */
class BundlerVerifyIT extends BaseTest {

  private static final List<String> ROUTES =
      List.of(
          // smoke test (baseline)
          "tablerowstyling",
          // shared applayout.css (6 views)
          "applayout",
          "applayoutdrawerutility",
          "applayoutfullnavbar",
          "applayoutmobiledrawer",
          "applayoutmultipleheaders",
          "applayoutstickytoolbar",
          // avatar, composite
          "avatar",
          "analyticscardcomposite",
          "composite",
          // drawer
          "drawercontact",
          "drawertask",
          "drawerwelcome",
          // element
          "elementinputdemo",
          "elementinputevent",
          "elementinputfunction",
          "elementinputtext",
          // elementcomposite — Shoelace-backed
          "card",
          "qrdemo",
          "qrevent",
          "qrproperties",
          "rating",
          "relativetime",
          "relativetimeproperties",
          // fields
          "colorfield",
          // flexlayout (shared flexContainerBuilder.css across 5)
          "flexlayout",
          "flexcontainerbuilder",
          "flexdirection",
          "flexpositioning",
          "flexorder",
          "flexselfalign",
          // googlecharts
          "chartgallery",
          "chartredraw",
          "chart",
          // infinitescroll
          "infinitescroll",
          "infinitescrollcustomloading",
          "infinitescrollloading",
          // lists
          "choiceboxdropdowntype",
          "comboboxdropdowntype",
          // loading
          "loadingdemo",
          "loadingspinnerdemo",
          // login
          "logincustomfields",
          // markdownviewer (also exercises CodeDisplay if the demo shows code)
          "markdownviewerstreaming",
          // refresher (shared refresher.css)
          "refresher",
          "refresheri18n",
          "refreshericon",
          "refresherthemes",
          // table
          "tablecellstyling",
          "tabledynamicstyling",
          "tablerichcontent",
          "tablestyledcolumngroups",
          // terminal, textarea
          "terminal",
          "textareapredictedtext",
          // toast, tree, upload
          "toast",
          "toasttheme",
          "treemodify",
          "upload",
          "uploadevents",
          "uploadpickingfiles",
          "uploadpresets",
          // usingcomponents
          "conditionalstate",
          "formvalidation",
          "progressivedisclosure",
          // viewtransitions
          "viewtransitionchat",
          "viewtransitionenterexit",
          "viewtransitionmorph",
          "viewtransitionshuffle",
          // helloworld + blog
          "helloworldjava",
          "blogs/building-better-css");

  @Test
  @DisplayName("every migrated view loads, renders, and produces no JS console errors")
  void walkAllMigratedViews() throws Exception {
    Path screenshotDir = Paths.get("target/bundler-verify-screenshots");
    Files.createDirectories(screenshotDir);

    List<String> failures = new ArrayList<>();
    List<ConsoleMessage> consoleErrors = new CopyOnWriteArrayList<>();
    page.onConsoleMessage(
        msg -> {
          if ("error".equals(msg.type())) {
            consoleErrors.add(msg);
          }
        });

    // Cap the settle wait at 3s so a chatty page doesn't hold the walk hostage;
    // leave navigation on Playwright's default so cold-loaded views don't cascade.
    for (String route : ROUTES) {
      consoleErrors.clear();
      try {
        navigateToRoute(route);
        try {
          page.waitForLoadState(
              LoadState.NETWORKIDLE, new Page.WaitForLoadStateOptions().setTimeout(3000));
        } catch (TimeoutError timeout) {
          // page kept making requests past 3s — take the screenshot anyway
        }

        String safe = route.replace('/', '_');
        page.screenshot(
            new com.microsoft.playwright.Page.ScreenshotOptions()
                .setPath(screenshotDir.resolve(safe + ".png"))
                .setFullPage(true));

        if (!consoleErrors.isEmpty()) {
          StringBuilder sb = new StringBuilder(route).append(" -> ");
          for (ConsoleMessage m : consoleErrors) {
            sb.append('[').append(m.text()).append(']');
          }
          failures.add(sb.toString());
        }
      } catch (RuntimeException e) {
        failures.add(route + " -> " + e.getMessage());
        // If the page/context died, recreate so the walk keeps going instead of cascading.
        if (page.isClosed()) {
          page = context.newPage();
          page.onConsoleMessage(
              msg -> {
                if ("error".equals(msg.type())) {
                  consoleErrors.add(msg);
                }
              });
        }
      }
    }

    if (!failures.isEmpty()) {
      System.err.println("---- BundlerVerifyIT failures ----");
      failures.forEach(System.err::println);
    }
    assertTrue(
        failures.isEmpty(),
        "One or more migrated views failed to load cleanly:\n  " + String.join("\n  ", failures));
  }
}
