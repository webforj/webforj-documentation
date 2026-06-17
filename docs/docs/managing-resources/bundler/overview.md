---
title: Frontend bundler
hide_table_of_contents: true
hide_giscus_comments: true
sidebar_class_name: new-content
description: Understand how the webforJ frontend bundler turns class-level annotations into compiled, per-route frontend assets, when to reach for it, and how a class binds to the entry it needs.
---

<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

# Frontend bundler <DocChip chip='since' label='26.01' /> {#frontend-bundler}

The frontend bundler lets a webforJ class draw on the [npm](https://www.npmjs.com/) ecosystem, write components in [React](https://react.dev/), [Svelte](https://svelte.dev/), or [Lit](https://lit.dev/), and author [SCSS](https://sass-lang.com/), all from a Java project with no Node toolchain to install or run. A class names the frontend it needs with an annotation, and the build installs the packages, compiles the sources, and loads the result when a component of that class is created.

The bundler runs as part of the [webforJ build plugin](/docs/configuration/build-plugin), which you add once to your Maven or Gradle build. The mechanics of compiling a specific kind of source, SCSS, [Less](https://lesscss.org/), [Tailwind](https://tailwindcss.com/), and the rest, are the job of [extensions](/docs/managing-resources/bundler/extensions/overview).

## When you need the bundler {#when-you-need-the-bundler}

webforJ runs without a bundler. If you already have a script or a stylesheet and you want to attach it to a component or the app, the [asset annotations](/docs/managing-resources/importing-assets) do that with no build step, no npm, and no compile.

The bundler earns its place when the frontend is more than a static file:

- You want a package from npm, by name and version, installed and compiled into your app.
- You want to write a component in React, Svelte, or Lit and consume it from Java.
- You want to author SCSS, Sass, or Less, or compile Tailwind utilities.
- You want to run frontend tests as part of the build.

The bundler is the default path for that work, and it does everything the asset annotations do, so a project that adopts it doesn't lose the simpler option.

## Adding it to an existing project {#adding-it-to-an-existing-project}

The bundler is opt in, so you can add it to an app that already uses the [asset annotations](/docs/managing-resources/importing-assets) and reach for it only where you need it.

1. Add the [webforJ build plugin](/docs/configuration/build-plugin) to your Maven or Gradle build. It manages Bun for you, so there's no Node toolchain to install.
2. Author your frontend sources under `src/main/frontend`.
3. Declare what a class needs with `@BundlePackage` and `@BundleEntry`.

Your existing `@StyleSheet`, `@JavaScript`, and the other asset annotations keep working unchanged, so you can move a resource onto the bundler when you want a package, a compiled source, or a test, and leave the rest alone.

## Binding a class to an entry {#binding-a-class-to-an-entry}

`@BundleEntry` ties a class to a frontend entry, and `@BundlePackage` declares the npm packages that entry imports. Both live on the class, so the frontend a view needs travels with the view.

```java title="GreetingView.java"
@Route("/greeting")
@BundlePackage(value = "lit", version = "^2.0.0")
@BundleEntry("greeting/hello-greeting.ts")
public class GreetingView extends Composite<FlexLayout> {

  public GreetingView() {
    getBoundComponent().add(new Element("hello-greeting"));
  }
}
```

```ts title="greeting/hello-greeting.ts"
import { LitElement, html } from 'lit';
import { customElement } from 'lit/decorators.js';

@customElement("hello-greeting")
class HelloGreeting extends LitElement {
  render() {
    return html`<p>Greetings from webforJ</p>`;
  }
}
```

The entry registers the `hello-greeting` custom element and owns its rendering. The Java side consumes it with `new Element("hello-greeting")` and listens to its events. The build compiles the entry, installs `lit`, and loads the output when `/greeting` renders.

## Topics {#topics}

<DocCardList className="topics-section" />
