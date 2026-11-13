---
sidebar_class_name: experimental-content
title: Eigene Erweiterungen schreiben
sidebar_position: 70
description: >-
  Add a build step by shipping a BundleExtension that declares packages,
  contributes a Bun plugin, and activates on a file type, shown by building a
  Svelte extension end to end.
_i18n_hash: bffbcd5a76f34d542765e3c2b59ed24d
---
<DocChip chip='since' label='26.01' />
<DocChip chip='experimental' />

Du fügst einen Compiler für eine weitere Quellart hinzu, indem du eine Erweiterung bereitstellst, die denselben Vertrag wie die integrierten Erweiterungen erfüllt. Dadurch liest sich eine benutzerdefinierte Erweiterung genauso wie eine mitgelieferte.

Diese Seite erstellt eine Erweiterung für **[Svelte](https://svelte.dev/)** als vollständiges Beispiel. Svelte ist ein Komponenten-Framework, dessen `.svelte`-Dateien zur Build-Zeit zu JavaScript kompiliert werden. Der Bundler verarbeitet diesen Quellcode nicht selbst. Damit webforJ eine Svelte-Komponente verwenden kann, muss der Build also so eingerichtet werden, dass er sie kompiliert – genau das leistet eine Erweiterung. Deshalb eignet sich dieses Beispiel: Ein neuer Quelltyp wird durch die von dir geschriebene Erweiterung dem Build beigebracht und anschließend wie jeder andere Eintrag an eine Klasse gebunden.

<!-- Do NOT use <ExperimentalWarning /> on this page. -->
:::info Vorschau-API
Die Erweiterungs-API befindet sich in der Vorschau und kann sich zwischen Releases noch ändern. Der Mechanismus selbst bleibt bestehen: Die integrierten Erweiterungen basieren auf demselben Vertrag. Die Methodensignaturen können sich jedoch verschieben. Stelle dich daher darauf ein, eine benutzerdefinierte Erweiterung bei einem Upgrade von webforJ anzupassen.
:::

## Build-Zeit-Abhängigkeit {#the-build-time-dependency}

Um eine Erweiterung zu schreiben, musst du sie gegen die Bundler-API kompilieren. Füge diese mit dem `provided`-Scope hinzu, da der Build sie bereitstellt und du sie nicht auslieferst:

```xml title="pom.xml"
<dependency>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-bundle-bun</artifactId>
  <version>${webforj.version}</version>
  <scope>provided</scope>
</dependency>
```

## Der Plugin-Wrapper {#the-bun-plugin-wrapper}

Eine Erweiterung stellt ein Bun-Plugin bereit. Lege das Plugin in einer kleinen `.mjs`-Ressource ab. Sie exportiert standardmäßig eine Factory, die der Build mit den für diese Erweiterung festgelegten Optionen aufruft und die das Bun-Plugin zurückgibt:

```js title="src/main/resources/frontend-extensions/svelte.mjs"
import { SveltePlugin } from 'bun-plugin-svelte';

export default (options) => SveltePlugin({ forceSide: 'client', ...options });
```

## Die Erweiterung {#the-extension}

Implementiere `BundleExtension`. Die Erweiterung gibt sich über eine ID zu erkennen, entscheidet, wann sie aktiviert wird, und deklariert in `onWillBundle` ihre Pakete und stellt den Wrapper bereit:

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

In diesem Beispiel:

- `getId()` gibt die ID zurück, mit der die Erweiterung aktiviert oder deaktiviert und auf ihre Optionen zugegriffen wird. Für die SCSS-Erweiterung erfüllt `webforj-scss` dieselbe Aufgabe.
- `isEnabledByDefault` aktiviert die Erweiterung, wenn eine `.svelte`-Quelldatei vorhanden ist. Das entspricht der Regel [Aktivierung nach Dateityp](/docs/managing-resources/bundler/extensions/overview#activated-by-file-type), der die kuratierten Erweiterungen folgen.
- `onWillBundle` deklariert die vom Compiler benötigten Build-Abhängigkeiten und stellt das Bun-Plugin unter der ID der Erweiterung bereit.

## Registrieren der Erweiterung {#registering-the-extension}

Der Build erkennt eine Erweiterung als Service im Klassenpfad. Füge eine nach `BundleExtension` benannte Service-Datei mit dem voll qualifizierten Klassennamen hinzu:

```text title="META-INF/services/com.webforj.bundle.bun.BundleExtension"
com.example.SvelteExtension
```

Sobald die Service-Datei vorhanden ist, wird eine `.svelte`-Quelldatei kompiliert, und die Ansicht verwendet das von der Komponente registrierte Element.

## Optionen {#options}

Die Erweiterung leitet die unter ihrer ID festgelegten Optionen direkt an das Bun-Plugin weiter. Lege sie in `bun.config.ts` fest:

```ts title="src/main/frontend/bun.config.ts"
export const options = {
  'svelte': { /* bun-plugin-svelte options */ }
};
```
