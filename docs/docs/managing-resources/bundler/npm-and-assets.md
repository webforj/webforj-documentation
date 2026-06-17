---
title: Packages and assets
sidebar_position: 30
sidebar_class_name: new-content
description: Declare npm packages, load a module straight from one, install build-only dependencies, rely on tree shaking, and import CSS and assets from a component's entry.
---

An entry draws on more than its own source. It imports packages from npm, loads a module straight from one, pulls in a stylesheet or an image, and can arrive from a class you extend or a library you depend on. This page covers how those pieces reach the build.

## Declaring a package {#declaring-a-package}

`@BundlePackage` declares an npm dependency an entry imports. The build collects every declaration on the classpath and adds it to your project's [`package.json`](https://docs.npmjs.com/cli/v11/configuring-npm/package-json), then [Bun](https://bun.sh/) installs it before compiling, so a package is present by the time its entry compiles. Your own edits to that file are preserved, and a project that declares no packages and has no `package.json` of its own keeps none, so npm stays out of a build that doesn't need it.

A web component library is the common case. Declare the package, then point `@BundleEntry` at the component modules you want:

```java title="Ui5View.java"
@Route("/ui5")
@BundlePackage(value = "@ui5/webcomponents", version = "^2.0.0")
@BundleEntry("@ui5/webcomponents/dist/Button.js")
@BundleEntry("@ui5/webcomponents/dist/Input.js")
public class Ui5View extends Composite<FlexLayout> {

  private final FlexLayout self = getBoundComponent();

  public Ui5View() {
    self.setAlignment(FlexAlignment.CENTER)
      .setStyle("margin", "1em");

    Element input = new Element("ui5-input");
    input.setProperty("placeholder", "Type something");

    Element button = new Element("ui5-button");
    button.setProperty("design", "Emphasized");
    button.setText("Say hello");
    button.addEventListener("click", e ->
      Toast.show("Hello " + input.getProperty("value"))
    );

    self.add(input, button);
  }
}
```

The `version` follows npm version syntax, so `^2.0.0` accepts compatible 2.x releases. Both `@BundlePackage` and `@BundleEntry` are repeatable, so a class declares as many packages and loads as many modules as it needs.

### A file entry or an npm module {#a-file-entry-or-an-npm-module}

The value of `@BundleEntry` is one of two things: a path to a file you author under `src/main/frontend`, or a path to a module inside an npm package. The view above names module paths inside `@ui5/webcomponents`, so it carries no source file of its own. Each of those modules registers its own custom element when it loads, which is why the view consumes `ui5-input` and `ui5-button` with no wrapper. A file entry instead points at a `.ts`, `.js`, or `.css` file you wrote, compiled the same way.

### Build dependencies {#build-dependencies}

A package needed only to compile the sources, not at runtime, is a build dependency. Set `dev = true` on `@BundlePackage`, and the build installs it as a `devDependency`:

```java
@BundlePackage(value = "typescript", version = "^5.0.0", dev = true)
```

The curated extensions use this for the packages their compilers need, which is why a SCSS source pulls in `sass` as a build dependency and nothing at runtime.

## Only what you import {#tree-shaking}

The compiler includes only the code an entry actually imports. Naming `@ui5/webcomponents/dist/Button.js` pulls in the Button component and what it needs, not the rest of the library. A broad library costs only the parts you reach for, so there's no penalty for declaring a large package and loading one module from it.

### Shared code {#shared-code}

When two entries import the same package, the build factors the shared code into a chunk that both load, rather than copying it into each. Several components built on the same library, a set of Lit elements for example, share that library's code on a page instead of paying for it once per element.

## How entries load {#how-entries-load}

An entry produces a script, a stylesheet, or both, and the runtime loads that output the first time a component of its class is created, wherever that component is used and however deeply it's nested. A routed view and a layout are components like any other, so an entry binds to component creation, not to routing. Two details follow from the annotation living on the class:

- **Inheritance.** `@BundleEntry` and `@BundlePackage` are inherited. A base class declares the entry, and a subclass that adds nothing of its own still loads it.
- **Debug entries.** An entry declared `@BundleEntry(value = "...", debug = true)` loads only when the app runs in debug mode, which suits a development only diagnostic.

## Importing CSS and assets {#importing-css-and-assets}

A component's entry handles stylesheets and other assets through imports, with no annotation and no extension. Bun resolves them at compile time.

Import a stylesheet for its side effect, and the bundler includes it in the entry's styles. Import a non-code asset, and the import gives you a URL to use:

```ts title="card/card.ts"
import './card.css';
import logoPath from './logo.svg';

const logo = new URL(logoPath, import.meta.url).href;
// use logo as an image source inside the element
```

Resolve an asset URL against `import.meta.url`, not the document, so it points at the compiled asset wherever the output is served.

Import a stylesheet as text instead, and apply it inside a shadow root to scope the styles to one element:

```ts title="swatch/swatch.ts"
import sheet from './swatch.css' with { type: 'text' };

class ColorSwatch extends HTMLElement {
  connectedCallback() {
    const root = this.attachShadow({ mode: 'open' });
    const style = document.createElement('style');
    style.textContent = sheet;
    root.append(style);
  }
}
```

An entry can also be a plain `.css` file with no script, bound to a class the same way a script entry is. The runtime loads it as styles for the view:

```java title="ThemeView.java"
@Route("/theme")
@BundleEntry("theme/theme.css")
public class ThemeView extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();

  public ThemeView() {
    SELF.add(new Div("Styled by a CSS only bundle entry")
                 .addClassName("themed-label"));
  }
}
```