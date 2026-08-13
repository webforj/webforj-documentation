---
title: Extensions
hide_giscus_comments: true
sidebar_class_name: new-content
description: Understand how a bundler extension contributes a compiler, the three ways an extension activates, how to configure one through bun.config.ts, and how to write your own.
---

import Tabs from '@theme/Tabs';
import TabItem from '@theme/TabItem';

The bundler doesn't know how to compile a SCSS stylesheet on its own. That's the job of an **extension**: a unit that contributes the compiler for one kind of source, declares the npm packages that compiler needs, and can generate entries of its own. webforJ ships a few extensions, and the model is open, so you add your own for any other source. SCSS, Less, and Tailwind aren't separate features bolted onto the bundler. They're extensions, and they share one model.

Once you know how an extension activates and what it contributes, every extension reads the same way, the shipped ones and the ones you write.

## What an extension contributes {#what-an-extension-contributes}

An extension is given a chance to act before the compile runs. When it acts, it can do three things:

- **Declare packages.** It adds the npm packages its compiler needs, usually as build dependencies, so they install before the compile.
- **Contribute a compiler.** It registers a Bun plugin that teaches the compile how to handle a kind of source.
- **Generate entries.** It can write a frontend entry of its own, which is how Tailwind produces a stylesheet without you authoring one.

Every extension carries an **id**, a short name such as `webforj-scss` or `webforj-less`. The id is how you refer to an extension in configuration, and how its options reach it.

## How an extension activates {#how-an-extension-activates}

An extension does its work only when it's active. There are three ways one becomes active, and knowing which path an integration uses tells you exactly when it runs.

### Activated by file type {#activated-by-file-type}

A curated extension declares the file extensions it handles, and it turns itself on when a source of that type is present under `src/main/frontend`. You don't enable it. Authoring the file is the signal.

Write a `.scss` file, and the SCSS extension activates. Write a `.less` file, and the Less extension activates. Remove the last source of that type, and the extension stays off, so its packages are never installed and its compiler never runs. This keeps a build lean: a project pays only for the kinds of source it actually authors. An extension you write follows the same rule, activating on the file type it declares.

### Enabled by id {#enabled-by-id}

Every extension can be turned on or off by its id, which overrides the file-type default. This matters in two cases. An extension that ships off, such as Tailwind, is turned on by id. And an extension that would activate from a present source can be forced off by id.

<Tabs>
<TabItem value="maven" label="Maven">

```xml title="pom.xml"
<plugin>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-maven-plugin</artifactId>
  <extensions>true</extensions>
  <configuration>
    <plugins>
      <webforj-tailwind>true</webforj-tailwind>
      <webforj-scss>false</webforj-scss>
    </plugins>
  </configuration>
</plugin>
```

</TabItem>
<TabItem value="gradle" label="Gradle">

```groovy title="build.gradle"
webforj {
  plugins.put('webforj-tailwind', 'true')
  plugins.put('webforj-scss', 'false')
}
```

</TabItem>
</Tabs>

The block above turns Tailwind on and turns SCSS compilation off, even if a `.scss` source is present.

### When no extension is needed {#when-no-extension-is-needed}

An extension exists to contribute a compiler. A framework that renders at runtime, rather than compiling to something new, needs no compiler, so it needs no extension. Bun already handles the TypeScript and JSX such a framework is written in.

[React](https://react.dev/) is a JavaScript library for building user interfaces, and the clearest case. Its entry is plain TypeScript that Bun compiles with no plugin. You wrap the component as a custom element with a library of your choice, such as `@r2wc/react-to-web-component`, declare the React packages with `@BundlePackage`, and the view consumes the resulting element. There is no React extension to enable, because there is no React compiler to contribute, only packages to install.

## Configuring an extension {#configuring-an-extension}

Some extensions take options. The SCSS compiler takes a load path, for example. Options live in `bun.config.ts`, a file you create at the root of your frontend source root, `src/main/frontend/bun.config.ts`, next to the entries you author. They go in an `options` object keyed by extension id, and the build hands each extension the object stored under its own id:

```ts title="src/main/frontend/bun.config.ts"
export const options = {
  'webforj-scss': { loadPaths: ['styles'] }
};
```

:::tip Where the options come from
An extension forwards its options straight to the tool it wraps, so the options an extension accepts are the options of that tool. Each extension page below names the tool it forwards to and links to that tool's reference, which is where you find the full list.
:::

The same file can append extra Bun plugins through a default export, for a build step no curated extension covers:

```ts title="src/main/frontend/bun.config.ts"
import myPlugin from './my-plugin';

export default [myPlugin()];
```

## The curated extensions {#the-curated-extensions}

webforJ ships an extension for SCSS, Less, and Tailwind. Each follows the model above: it activates when its source type is present, declares the packages its compiler needs as build dependencies, and contributes the compiler.

| Extension | Id | Activates on | Installs (build dependency) |
|-----------|----|--------------|------------------------------|
| [SCSS and Sass](/docs/managing-resources/bundler/extensions/scss) | `webforj-scss` | a `.scss` or `.sass` source | `sass` |
| [Less](/docs/managing-resources/bundler/extensions/less) | `webforj-less` | a `.less` source | `less` |
| [Tailwind](/docs/managing-resources/bundler/extensions/tailwind) | `webforj-tailwind` | off by default, enabled by id | `tailwindcss`, `bun-plugin-tailwind` |

To compile another kind of source, you write your own extension on the same contract. See [Writing your own extension](/docs/managing-resources/bundler/extensions/writing-your-own), which builds one end to end.
