---
title: Writing your own extension
sidebar_position: 70
description: >-
  Add a build step by shipping a BundleExtension that declares packages,
  contributes a Bun plugin, and activates on a file type, shown by building a
  Svelte extension end to end.
_i18n_hash: fb042c0be37d53bf5f46eb1536056275
---
<DocChip chip='since' label='26.01' />
<DocChip chip='experimental' />

Sie fügen einen Compiler für eine andere Art von Quelle hinzu, indem Sie eine Erweiterung versenden, die denselben Vertrag verwendet, den die integrierten Erweiterungen nutzen, sodass eine benutzerdefinierte Erweiterung genau wie eine gelieferte aussieht.

Diese Seite erstellt eine **[Svelte](https://svelte.dev/)**-Erweiterung als komplettes Beispiel. Svelte ist ein Komponenten-Framework, dessen `.svelte`-Dateien zur Build-Zeit in JavaScript kompiliert werden. Der Bundler verarbeitet diese Quelle nicht von allein, sodass der Verbrauch einer Svelte-Komponente von webforJ bedeutet, den Build beizubringen, sie zu kompilieren, was genau das ist, was eine Erweiterung tut. Das macht es zu einem passenden Beispiel: ein neuer Quelltyp, dem der Build durch die Erweiterung, die Sie schreiben, beigebracht wird, und dann einer Klasse gebunden wird, ebenso wie jeder andere Einstieg.

<!-- Verwenden Sie auf dieser Seite NICHT <ExperimentalWarning />. -->
:::warning Experimentelle API
Die Erweiterungs-API ist experimentell und kann zwischen den Veröffentlichungen Änderungen unterliegen, während sie sich stabilisiert. Der Mechanismus selbst wird bleiben, die integrierten Erweiterungen basieren auf demselben Vertrag, aber die Methodensignaturen können sich ändern, sodass Sie erwarten sollten, eine benutzerdefinierte Erweiterung anzupassen, wenn Sie webforJ aktualisieren.
:::

## Die Abhängigkeit zur Build-Zeit {#the-build-time-dependency}

Um eine Erweiterung zu schreiben, kompilieren Sie gegen die Bundler-API. Fügen Sie es mit dem `provided` Scope hinzu, da der Build es bereitstellt und Sie es nicht versenden:

```xml title="pom.xml"
<dependency>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-bundle-bun</artifactId>
  <version>${webforj.version}</version>
  <scope>provided</scope>
</dependency>
```

## Der Plugin-Wrapper {#the-bun-plugin-wrapper}

Eine Erweiterung trägt einen Bun-Plugin bei. Halten Sie das Plugin in einer kleinen `.mjs`-Ressource. Es exportiert standardmäßig eine Fabrik, die der Build mit den Optionen aufruft, die Sie für diese Erweiterung festgelegt haben, und gibt das Bun-Plugin zurück:

```js title="src/main/resources/frontend-extensions/svelte.mjs"
import { SveltePlugin } from 'bun-plugin-svelte';

export default (options) => SveltePlugin({ forceSide: 'client', ...options });
```

## Die Erweiterung {#the-extension}

Implementieren Sie `BundleExtension`. Es benennt sich mit einer ID, entscheidet, wann es aktiviert wird, und erklärt in `onWillBundle` seine Pakete und trägt den Wrapper bei:

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

- `getId()` gibt die ID zurück, die verwendet wird, um die Erweiterung ein- oder auszuschalten und um ihre Optionen zu keyen, die dieselbe Rolle spielt, die `webforj-scss` für die SCSS-Erweiterung spielt.
- `isEnabledByDefault` aktiviert die Erweiterung, wenn eine `.svelte`-Quelle vorhanden ist, die Regel [Aktivierung nach Dateityp](/docs/managing-resources/bundler/extensions/overview#activated-by-file-type), der die kuratierten Erweiterungen folgen.
- `onWillBundle` erklärt die Build-Abhängigkeiten, die der Compiler benötigt, und trägt das Bun-Plugin unter der ID der Erweiterung bei.

## Registrierung der Erweiterung {#registering-the-extension}

Der Build entdeckt eine Erweiterung als Dienst im Klassenpfad. Fügen Sie eine Dienstdatei mit dem Namen für `BundleExtension` hinzu, mit dem vollqualifizierten Klassennamen:

```text title="META-INF/services/com.webforj.bundle.bun.BundleExtension"
com.example.SvelteExtension
```

Mit der Dienstdatei an Ort und Stelle kompiliert eine `.svelte`-Quelle jetzt, und die Ansicht verbraucht das Element, das die Komponente registriert.

## Optionen {#options}

Die Erweiterung leitet die Optionen, die Sie unter ihrer ID festgelegt haben, direkt an das Bun-Plugin weiter. Legen Sie sie in `bun.config.ts` fest:

```ts title="src/main/frontend/bun.config.ts"
export const options = {
  'svelte': { /* bun-plugin-svelte options */ }
};
```
