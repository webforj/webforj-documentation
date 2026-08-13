---
title: Writing your own extension
sidebar_position: 70
description: Add a build step by shipping a BundleExtension that declares packages, contributes a Bun plugin, and activates on a file type, shown by building a Svelte extension end to end.
---

<DocChip chip='since' label='26.01' />
<DocChip chip='experimental' />

You add a compiler for another kind of source by shipping an extension, on the same contract the built-in extensions use, so a custom extension reads exactly like a shipped one.

This page builds a **[Svelte](https://svelte.dev/)** extension as a complete example. Svelte is a component framework whose `.svelte` files compile to JavaScript at build time. The bundler doesn't handle that source on its own, so consuming a Svelte component from webforJ means teaching the build to compile it, which is exactly what an extension does. That makes it a fitting example: a new source type, taught to the build by the extension you write, then bound to a class the same way any other entry is.

<!-- Do NOT use <ExperimentalWarning /> on this page. -->
:::warning Experimental API
The extension API is experimental and may change between releases while it settles. The mechanism itself is here to stay, the built-in extensions are built on the same contract, but its method signatures may shift, so expect to adjust a custom extension when you upgrade webforJ.
:::

## The build-time dependency {#the-build-time-dependency}

To write an extension you compile against the bundler API. Add it with `provided` scope, since the build supplies it and you don't ship it:

```xml title="pom.xml"
<dependency>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-bundle-bun</artifactId>
  <version>${webforj.version}</version>
  <scope>provided</scope>
</dependency>
```

## The plugin wrapper {#the-bun-plugin-wrapper}

An extension contributes a Bun plugin. Keep the plugin in a small `.mjs` resource. It default-exports a factory that the build calls with the options you set for this extension, and returns the Bun plugin:

```js title="src/main/resources/frontend-extensions/svelte.mjs"
import { SveltePlugin } from 'bun-plugin-svelte';

export default (options) => SveltePlugin({ forceSide: 'client', ...options });
```

## The extension {#the-extension}

Implement `BundleExtension`. It names itself with an id, decides when it activates, and in `onWillBundle` declares its packages and contributes the wrapper:

```java title="SvelteExtension.java"
public class SvelteExtension implements BundleExtension {

  @Override
  public String getId() {
    return "svelte";
  }

  @Override
  public boolean isEnabledByDefault(BundleContext context) {
    return context.getSourceExtensions().contains("svelte");
  }

  @Override
  public void onWillBundle(BundleContext context) {
    context.addPackage(new BundlePackageDeclaration()
        .setName("bun-plugin-svelte")
        .setVersion("^0.0.6")
        .setDev(true));
    context.addPackage(new BundlePackageDeclaration()
        .setName("svelte")
        .setVersion("^5.56.2")
        .setDev(true));

    context.addPlugin(getId(), readWrapper("/frontend-extensions/svelte.mjs"));
  }

  private String readWrapper(String path) {
    try (InputStream in = getClass().getResourceAsStream(path)) {
      if (in == null) {
        throw new IOException("missing wrapper " + path);
      }

      return new String(in.readAllBytes(), StandardCharsets.UTF_8);
    } catch (IOException e) {
      throw new UncheckedIOException(e);
    }
  }
}
```

In this example:

- `getId()` returns the id used to turn the extension on or off and to key its options, the same role `webforj-scss` plays for the SCSS extension.
- `isEnabledByDefault` activates the extension when a `.svelte` source is present, the [activate by file type](/docs/managing-resources/bundler/extensions/overview#activated-by-file-type) rule the curated extensions follow.
- `onWillBundle` declares the build dependencies the compiler needs and contributes the Bun plugin under the extension's id.

## Registering the extension {#registering-the-extension}

The build discovers an extension as a service on the classpath. Add a service file named for `BundleExtension`, with the fully qualified class name:

```text title="META-INF/services/com.webforj.bundle.bun.BundleExtension"
com.example.SvelteExtension
```

With the service file in place, a `.svelte` source now compiles, and the view consumes the element the component registers.

## Options {#options}

The extension forwards the options you set under its id straight to the Bun plugin. Set them in `bun.config.ts`:

```ts title="src/main/frontend/bun.config.ts"
export const options = {
  'svelte': { /* bun-plugin-svelte options */ }
};
```