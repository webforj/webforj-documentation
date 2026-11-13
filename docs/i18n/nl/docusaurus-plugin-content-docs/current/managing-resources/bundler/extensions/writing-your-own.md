---
sidebar_class_name: experimental-content
title: Je eigen extensie schrijven
sidebar_position: 70
description: >-
  Add a build step by shipping a BundleExtension that declares packages,
  contributes a Bun plugin, and activates on a file type, shown by building a
  Svelte extension end to end.
_i18n_hash: bffbcd5a76f34d542765e3c2b59ed24d
---
<DocChip chip='since' label='26.01' />
<DocChip chip='experimental' />

Je voegt een compiler voor een ander soort bronbestand toe door een extensie te leveren, volgens hetzelfde contract als de ingebouwde extensies. Zo werkt een aangepaste extensie precies zoals een meegeleverde extensie.

Op deze pagina bouwen we een **[Svelte](https://svelte.dev/)**-extensie als compleet voorbeeld. Svelte is een componentframework waarvan `.svelte`-bestanden tijdens het bouwen naar JavaScript worden gecompileerd. De bundler verwerkt die bronbestanden niet zelfstandig. Om een Svelte-component in webforJ te gebruiken, moet je de build dus leren hoe die de bestanden compileert. Dat is precies wat een extensie doet. Daarom is dit een passend voorbeeld: een nieuw brontype dat door de extensie die je schrijft aan de build wordt aangeleerd en vervolgens aan een klasse wordt gekoppeld, net als elk ander entrypoint.

<!-- Do NOT use <ExperimentalWarning /> on this page. -->
:::info Preview-API
De extensie-API is in preview en kan tussen releases veranderen terwijl deze verder wordt ontwikkeld. Het mechanisme zelf blijft bestaan en de ingebouwde extensies zijn op hetzelfde contract gebaseerd. De methodesignatures kunnen echter veranderen, dus houd er rekening mee dat je een aangepaste extensie mogelijk moet aanpassen wanneer je webforJ bijwerkt.
:::

## De buildafhankelijkheid {#the-build-time-dependency}

Om een extensie te schrijven, compileer je tegen de bundler-API. Voeg deze toe met de scope `provided`, omdat de build deze levert en je hem niet meelevert:

```xml title="pom.xml"
<dependency>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-bundle-bun</artifactId>
  <version>${webforj.version}</version>
  <scope>provided</scope>
</dependency>
```

## De pluginwrapper {#the-bun-plugin-wrapper}

Een extensie levert een Bun-plugin. Houd de plugin in een kleine `.mjs`-resource. Deze exporteert standaard een factory die de build aanroept met de opties die je voor deze extensie instelt, en retourneert de Bun-plugin:

```js title="src/main/resources/frontend-extensions/svelte.mjs"
import { SveltePlugin } from 'bun-plugin-svelte';

export default (options) => SveltePlugin({ forceSide: 'client', ...options });
```

## De extensie {#the-extension}

Implementeer `BundleExtension`. De extensie geeft zichzelf een id, bepaalt wanneer deze wordt geactiveerd en declareert in `onWillBundle` de benodigde pakketten en de wrapper:

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

In dit voorbeeld:

- `getId()` retourneert de id waarmee je de extensie in- of uitschakelt en waaraan de opties worden gekoppeld. Dit is dezelfde rol die `webforj-scss` vervult voor de SCSS-extensie.
- `isEnabledByDefault` activeert de extensie wanneer er een `.svelte`-bronbestand aanwezig is. Dit volgt de regel [activeren op basis van bestandstype](/docs/managing-resources/bundler/extensions/overview#activated-by-file-type) die de samengestelde extensies hanteren.
- `onWillBundle` declareert de buildafhankelijkheden die de compiler nodig heeft en levert de Bun-plugin onder de id van de extensie.

## De extensie registreren {#registering-the-extension}

De build ontdekt een extensie als service op het classpath. Voeg een servicebestand toe met de naam van `BundleExtension` en de volledig gekwalificeerde klassenaam:

```text title="META-INF/services/com.webforj.bundle.bun.BundleExtension"
com.example.SvelteExtension
```

Zodra het servicebestand aanwezig is, wordt een `.svelte`-bronbestand gecompileerd en gebruikt de view het element dat de component registreert.

## Opties {#options}

De extensie geeft de opties die je onder de id ervan instelt rechtstreeks door aan de Bun-plugin. Stel ze in `bun.config.ts` in:

```ts title="src/main/frontend/bun.config.ts"
export const options = {
  'svelte': { /* bun-plugin-svelte options */ }
};
```
