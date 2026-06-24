#!/usr/bin/env node

import { execFileSync } from 'child_process';
import {
  cpSync,
  existsSync,
  mkdirSync,
  readFileSync,
  rmSync,
  writeFileSync,
} from 'fs';
import { dirname, join, relative, resolve } from 'path';
import { fileURLToPath } from 'url';
import fg from 'fast-glob';

const __dirname = dirname(fileURLToPath(import.meta.url));
const docsRoot = resolve(__dirname, '..');
const repoRoot = resolve(docsRoot, '..');
const cookbookRoot = join(docsRoot, 'cookbook');
const targetRoot = join(repoRoot, 'target', 'cookbook-runtime-tests');
const sourceRoot = join(targetRoot, 'src');
const classpathFile = join(targetRoot, 'classpath.txt');
const manifestFile = join(targetRoot, 'routes.json');
const targetClasses = join(repoRoot, 'target', 'classes');
const staticRoot = join(targetClasses, 'static', 'cookbook-static');
const runtimeBaseUrl = (
  process.env.COOKBOOK_TEST_BASE_URL ?? 'http://127.0.0.1:8091'
).replace(/\/+$/, '');

const RECIPE_CONFIG = {
  'components/card-with-slots.md': {
    packageName: 'com.webforj.samples.views.cookbooktest.cardwithslots',
    route: 'cookbook-test/card-with-slots',
    wrapper: ({ packageName, publicType }) => `
package ${packageName};

import com.webforj.component.Composite;
import com.webforj.component.button.Button;
import com.webforj.component.button.ButtonTheme;
import com.webforj.component.html.elements.Div;
import com.webforj.component.html.elements.H2;
import com.webforj.component.html.elements.Paragraph;
import com.webforj.router.annotation.Route;

@Route("cookbook-test/card-with-slots")
public class CardWithSlotsTestView extends Composite<Div> {
  public CardWithSlotsTestView() {
    ${publicType} card = new ${publicType}()
        .setTitle("Confirm subscription")
        .addToBody(new Paragraph("You're about to subscribe to the monthly newsletter."))
        .addToFooter(
            new Button("Cancel", ButtonTheme.DEFAULT),
            new Button("Subscribe", ButtonTheme.PRIMARY));
    getBoundComponent().add(new H2("Card with slots"), card);
  }
}
`,
    expected: ['Card with slots', 'Confirm subscription', 'Subscribe'],
  },
  'components/enum-property-on-element-composite.md': {
    packageName: 'com.webforj.samples.views.cookbooktest.enumproperty',
    route: 'cookbook-test/enum-property',
    wrapper: ({ packageName, publicType }) => `
package ${packageName};

import com.webforj.component.Composite;
import com.webforj.component.html.elements.Div;
import com.webforj.component.html.elements.H2;
import com.webforj.router.annotation.Route;

@Route("cookbook-test/enum-property")
public class EnumPropertyTestView extends Composite<Div> {
  public EnumPropertyTestView() {
    ${publicType} card = new ${publicType}().setVariant(${publicType}.Variant.DANGER);
    getBoundComponent().add(
        new H2("Enum property"),
        card,
        new Div("Variant: " + card.getVariant()));
  }
}
`,
    expected: ['Enum property', 'Variant: DANGER'],
  },
  'components/save-feedback-toast.md': {
    packageName: 'com.webforj.samples.views.cookbooktest.savefeedback',
    route: 'cookbook-test/save-feedback-toast',
    routeReplacements: [['@Route("customer-save")', '@Route("cookbook-test/save-feedback-toast")']],
    expected: ['Save'],
  },
  'components/typed-custom-event-from-composite.md': {
    packageName: 'com.webforj.samples.views.cookbooktest.typedevent',
    route: 'cookbook-test/typed-event',
    wrapper: ({ packageName, publicType }) => `
package ${packageName};

import com.webforj.component.Composite;
import com.webforj.component.html.elements.Div;
import com.webforj.component.html.elements.H2;
import com.webforj.router.annotation.Route;

@Route("cookbook-test/typed-event")
public class TypedEventTestView extends Composite<Div> {
  public TypedEventTestView() {
    ${publicType} picker = new ${publicType}();
    Div value = new Div("Rating: 0");
    picker.onRatingChanged(event -> value.setText("Rating: " + event.getValue()));
    getBoundComponent().add(new H2("Typed event"), picker, value);
  }
}
`,
    expected: ['Typed event', 'Rating: 0'],
  },
  'css/hide-on-small-screens.md': {
    packageName: 'com.webforj.samples.views.cookbooktest.responsivehide',
    route: 'cookbook-test/hide-on-small-screens',
    routeReplacements: [['@Route("responsive-hide")', '@Route("cookbook-test/hide-on-small-screens")']],
    cssFile: 'responsive-hide.css',
    expected: ['Main content', 'Quick links'],
  },
  'css/inject-inline-css.md': {
    packageName: 'com.webforj.samples.views.cookbooktest.inlinecss',
    route: 'cookbook-test/inject-inline-css',
    routeReplacements: [['@Route\n', '@Route("cookbook-test/inject-inline-css")\n']],
    expected: [],
  },
  'forms/debounced-email-validation.md': {
    packageName: 'com.webforj.samples.views.cookbooktest.debouncedemail',
    route: 'cookbook-test/debounced-email',
    routeReplacements: [['@Route("cookbook-email-validation")', '@Route("cookbook-test/debounced-email")']],
    expected: ['Email address'],
  },
  'forms/dialog-form-with-binding-context.md': {
    packageName: 'com.webforj.samples.views.cookbooktest.contactdialog',
    route: 'cookbook-test/contact-dialog',
    wrapper: ({ packageName, publicType }) => `
package ${packageName};

import com.webforj.component.Composite;
import com.webforj.component.button.Button;
import com.webforj.component.html.elements.Div;
import com.webforj.component.html.elements.H2;
import com.webforj.router.annotation.Route;

@Route("cookbook-test/contact-dialog")
public class ContactDialogTestView extends Composite<Div> {
  private final ${publicType} dialog = new ${publicType}();
  private final Div saved = new Div("Saved: none");

  public ContactDialogTestView() {
    Button open = new Button("Open contact dialog");
    open.onClick(event -> dialog.open(
        new ${publicType}.Contact("Ada Lovelace", "ada@example.com"),
        contact -> saved.setText("Saved: " + contact.getName() + " / " + contact.getEmail())));
    getBoundComponent().add(new H2("Contact dialog"), open, saved, dialog);
  }
}
`,
    expected: ['Contact dialog', 'Open contact dialog', 'Saved: none'],
  },
  'forms/disable-button-during-save.md': {
    packageName: 'com.webforj.samples.views.cookbooktest.disablesave',
    route: 'cookbook-test/disable-save',
    routeReplacements: [['@Route("order-save")', '@Route("cookbook-test/disable-save")']],
    expected: ['Save'],
  },
  'javascript/call-dom-method.md': {
    packageName: 'com.webforj.samples.views.cookbooktest.dommethod',
    route: 'cookbook-test/call-dom-method',
    routeReplacements: [['@Route("scroll-demo")', '@Route("cookbook-test/call-dom-method")']],
    expected: ['Jump to footer', 'You scrolled here from Java.'],
  },
  'layout/centered-layout.md': {
    packageName: 'com.webforj.samples.views.cookbooktest.centeredlayout',
    route: 'cookbook-test/centered-layout',
    wrapper: ({ packageName, publicType }) => `
package ${packageName};

import com.webforj.component.Composite;
import com.webforj.component.html.elements.Div;
import com.webforj.component.html.elements.Paragraph;
import com.webforj.router.annotation.Route;

@Route("cookbook-test/centered-layout")
public class CenteredLayoutTestView extends Composite<Div> {
  public CenteredLayoutTestView() {
    TestLayout layout = new TestLayout();
    getBoundComponent().add(layout);
  }

  private static class TestLayout extends ${publicType} {
    private TestLayout() {
      getBoundComponent()
          .setSize("100%", "320px")
          .add(new Paragraph("Centered content"));
    }
  }
}
`,
    expected: ['Centered content'],
  },
  'routing/block-navigation-unsaved-changes.md': {
    packageName: 'com.webforj.samples.views.cookbooktest.blocknavigation',
    route: 'cookbook-test/block-navigation',
    routeReplacements: [['@Route("edit-product")', '@Route("cookbook-test/block-navigation")']],
    expected: ['Product name'],
  },
  'routing/dynamic-page-title.md': {
    packageName: 'com.webforj.samples.views.cookbooktest.dynamicpagetitle',
    route: 'cookbook-test/dynamic-page-title/42',
    routeReplacements: [['@Route("products/:id")', '@Route("cookbook-test/dynamic-page-title/:id")']],
    expected: ['Product details'],
  },
  'routing/navigate-programmatically.md': {
    packageName: 'com.webforj.samples.views.cookbooktest.navigate',
    route: 'cookbook-test/navigate',
    routeReplacements: [
      ['@Route\n', '@Route("cookbook-test/navigate")\n'],
      ['@Route("dashboard")', '@Route("cookbook-test/navigate/dashboard")'],
    ],
    expected: ['Go to Dashboard'],
  },
  'routing/open-in-new-tab.md': {
    packageName: 'com.webforj.samples.views.cookbooktest.newtab',
    route: 'cookbook-test/open-in-new-tab',
    routeReplacements: [
      ['@Route\n', '@Route("cookbook-test/open-in-new-tab")\n'],
      ['@Route("report")', '@Route("cookbook-test/open-in-new-tab/report")'],
    ],
    expected: ['Open report in new tab'],
  },
  'routing/read-query-parameter.md': {
    packageName: 'com.webforj.samples.views.cookbooktest.queryparameter',
    route: 'cookbook-test/read-query-parameter?category=books&sort=title',
    routeReplacements: [['@Route(value = "catalog")', '@Route(value = "cookbook-test/read-query-parameter")']],
    sourceTransform: (source) => source.replace(
      'String sort = query.get("sort").orElse("default");',
      'String sort = query.get("sort").orElse("default");\n    getBoundComponent().setText("Category: " + category + ", sort: " + sort);',
    ),
    expected: ['Category: books, sort: title'],
  },
  'table/boolean-column-renderer.md': {
    packageName: 'com.webforj.samples.views.cookbooktest.booleanrenderer',
    route: 'cookbook-test/boolean-renderer',
    routeReplacements: [['@Route("tasks")', '@Route("cookbook-test/boolean-renderer")']],
    expected: ['Review invoice', 'Send reminder'],
  },
  'table/empty-state-message.md': {
    packageName: 'com.webforj.samples.views.cookbooktest.emptystate',
    route: 'cookbook-test/empty-state',
    routeReplacements: [['@Route("books")', '@Route("cookbook-test/empty-state")']],
    cssFile: 'empty-state.css',
    expected: ['Modern Java', 'Practical UI'],
  },
  'table/persist-column-widths.md': {
    packageName: 'com.webforj.samples.views.cookbooktest.persistwidths',
    route: 'cookbook-test/persist-column-widths',
    routeReplacements: [['@Route("products")', '@Route("cookbook-test/persist-column-widths")']],
    expected: ['Keyboard', 'Dock'],
  },
  'theme/AppThemeToggle.md': {
    packageName: 'com.webforj.samples.views.cookbooktest.themetoggle',
    route: 'cookbook-test/theme-toggle',
    wrapper: ({ packageName, publicType }) => `
package ${packageName};

import com.webforj.component.Composite;
import com.webforj.component.html.elements.Div;
import com.webforj.component.html.elements.H2;
import com.webforj.router.annotation.Route;

@Route("cookbook-test/theme-toggle")
public class ThemeToggleTestView extends Composite<Div> {
  public ThemeToggleTestView() {
    getBoundComponent().add(new H2("Theme toggle"), new ${publicType}());
  }
}
`,
    expected: ['Theme toggle'],
  },
};

function normalizePath(value) {
  return value.replace(/\\/g, '/');
}

function run(command, args, options = {}) {
  return execFileSync(command, args, {
    cwd: options.cwd ?? repoRoot,
    encoding: 'utf8',
    stdio: options.stdio ?? 'pipe',
  });
}

function quoteWindowsCommandArg(arg) {
  return /^[A-Za-z0-9_./:=\\-]+$/.test(arg)
    ? arg
    : `"${arg.replace(/"/g, '""')}"`;
}

function runMaven(args, options = {}) {
  if (process.platform === 'win32') {
    const commandLine = ['mvn.cmd', ...args].map(quoteWindowsCommandArg).join(' ');
    return run('cmd.exe', ['/d', '/s', '/c', commandLine], options);
  }
  return run('mvn', args, options);
}

function extractBlocks(filePath) {
  const lines = readFileSync(filePath, 'utf8').split(/\r?\n/);
  const blocks = [];
  let active = null;

  lines.forEach((line, index) => {
    const fence = line.match(/^```(\S*)/);
    if (fence && !active) {
      active = { lang: fence[1] ?? '', startLine: index + 1, lines: [] };
    } else if (fence && active) {
      blocks.push({
        lang: active.lang,
        startLine: active.startLine,
        content: active.lines.join('\n'),
      });
      active = null;
    } else if (active) {
      active.lines.push(line);
    }
  });

  return blocks;
}

function findPublicType(source) {
  const match = source.match(
    /\bpublic\s+(?:final\s+|abstract\s+)?(?:class|interface|enum|record)\s+([A-Za-z_$][\w$]*)/,
  );
  if (!match) {
    throw new Error('No public Java type found');
  }
  return match[1];
}

function writeJava(packageName, fileName, source) {
  const packageDir = join(sourceRoot, ...packageName.split('.'));
  mkdirSync(packageDir, { recursive: true });
  const filePath = join(packageDir, fileName);
  writeFileSync(filePath, source.trimStart(), 'utf8');
  return filePath;
}

function transformSource(source, config) {
  let transformed = source;
  for (const [from, to] of config.routeReplacements ?? []) {
    transformed = transformed.replace(from, to);
  }
  if (config.sourceTransform) {
    transformed = config.sourceTransform(transformed);
  }
  return `package ${config.packageName};\n\n${transformed}`;
}

function prepareSources() {
  rmSync(sourceRoot, { recursive: true, force: true });
  rmSync(classpathFile, { force: true });
  rmSync(manifestFile, { force: true });
  mkdirSync(sourceRoot, { recursive: true });

  const manifest = [];
  const javaFiles = [];
  const configuredFiles = new Set(Object.keys(RECIPE_CONFIG));

  for (const [recipe, config] of Object.entries(RECIPE_CONFIG)) {
    const filePath = join(cookbookRoot, recipe);
    if (!existsSync(filePath)) {
      throw new Error(`Cookbook recipe not found: ${recipe}`);
    }

    const blocks = extractBlocks(filePath);
    const javaBlock = blocks.find((block) =>
      block.lang === 'java' && /\bpublic\s+(?:class|interface|enum|record)\b/.test(block.content));
    if (!javaBlock) {
      throw new Error(`Full Java block not found in ${recipe}`);
    }

    const publicType = findPublicType(javaBlock.content);
    javaFiles.push(writeJava(
      config.packageName,
      `${publicType}.java`,
      transformSource(javaBlock.content, config),
    ));

    if (config.wrapper) {
      const wrapperSource = config.wrapper({
        packageName: config.packageName,
        publicType,
      });
      const wrapperType = findPublicType(wrapperSource);
      javaFiles.push(writeJava(
        config.packageName,
        `${wrapperType}.java`,
        wrapperSource,
      ));
    }

    if (config.cssFile) {
      const cssBlock = blocks.find((block) => block.lang === 'css');
      if (!cssBlock) {
        throw new Error(`CSS block not found in ${recipe}`);
      }
      mkdirSync(staticRoot, { recursive: true });
      writeFileSync(join(staticRoot, config.cssFile), cssBlock.content, 'utf8');
    }

    manifest.push({
      recipe,
      route: `${runtimeBaseUrl}/${config.route}`,
      expected: config.expected ?? [],
    });
  }

  const allRecipes = fg.sync('**/*.{md,mdx}', {
    cwd: cookbookRoot,
    ignore: ['README.md', 'index.mdx', 'recipe-template.md', 'recipe-template.mdx'],
  }).map(normalizePath);
  const missing = allRecipes.filter((file) => !configuredFiles.has(file));
  if (missing.length > 0) {
    throw new Error(`Runtime test configuration missing for: ${missing.join(', ')}`);
  }

  writeFileSync(manifestFile, `${JSON.stringify(manifest, null, 2)}\n`, 'utf8');
  return { javaFiles, manifest };
}

function compileSources(javaFiles) {
  runMaven(['-q', '-DskipTests', '-Dskip.npm', 'compile'], {
    cwd: repoRoot,
    stdio: 'inherit',
  });

  runMaven([
    '-q',
    '-DskipTests',
    'dependency:build-classpath',
    `-Dmdep.outputFile=${classpathFile}`,
  ], { cwd: repoRoot });

  const dependencyClasspath = readFileSync(classpathFile, 'utf8').trim();
  const classpath = `${targetClasses};${dependencyClasspath}`;
  run(process.platform === 'win32' ? 'javac.exe' : 'javac', [
    '--release',
    '21',
    '-classpath',
    classpath,
    '-d',
    targetClasses,
    ...javaFiles,
  ], { cwd: repoRoot, stdio: 'inherit' });
}

function main() {
  const { javaFiles, manifest } = prepareSources();
  compileSources(javaFiles);
  cpSync(manifestFile, join(targetClasses, 'cookbook-runtime-routes.json'));
  console.log(`Prepared ${manifest.length} cookbook runtime route(s).`);
  console.log(`Manifest: ${manifestFile}`);
}

main();
