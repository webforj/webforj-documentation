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

Je voegt een compiler voor een andere soort bron toe door een extensie te verzenden, op hetzelfde contract dat de ingebouwde extensies gebruiken, zodat een aangepaste extensie precies zoals een verzonden extensie wordt gelezen.

Deze pagina bouwt een **[Svelte](https://svelte.dev/)** extensie als een compleet voorbeeld. Svelte is een componentframework waarvan de `.svelte`-bestanden tijdens de bouwtijd naar JavaScript worden gecompileerd. De bundler behandelt die bron niet op zichzelf, dus het consumeren van een Svelte-component vanuit webforJ betekent het leren aan de build om het te compileren, wat precies is wat een extensie doet. Dat maakt het een passend voorbeeld: een nieuw soort bron, geleerd aan de build door de extensie die je schrijft, en vervolgens gebonden aan een klasse op dezelfde manier als elk ander invoerpunt.

<!-- Gebruik <ExperimentalWarning /> niet op deze pagina. -->
:::warning Experimentele API
De extensie-API is experimenteel en kan tussen versies veranderen terwijl deze zich vestigt. Het mechanisme zelf is hier om te blijven, de ingebouwde extensies zijn gebaseerd op hetzelfde contract, maar de methodesignaturen kunnen verschuiven, dus verwacht aan te passen een aangepaste extensie wanneer je webforJ upgradt.
:::

## De afhankelijkheid tijdens de bouwtijd {#the-build-time-dependency}

Om een extensie te schrijven compileer je tegen de bundler-API. Voeg het toe met `provided` scope, aangezien de build deze levert en je het niet verzendt:

```xml title="pom.xml"
<dependency>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-bundle-bun</artifactId>
  <version>${webforj.version}</version>
  <scope>provided</scope>
</dependency>
```

## De pluginwrapper {#the-bun-plugin-wrapper}

Een extensie draagt bij aan een Bun-plugin. Houd de plugin in een kleine `.mjs` bron. Het exporteert standaard een factory die de build aanroept met de opties die je voor deze extensie instelt, en retourneert de Bun-plugin:

```js title="src/main/resources/frontend-extensions/svelte.mjs"
import { SveltePlugin } from 'bun-plugin-svelte';

export default (options) => SveltePlugin({ forceSide: 'client', ...options });
```

## De extensie {#the-extension}

Implementeer `BundleExtension`. Het benoemt zichzelf met een id, beslist wanneer het activeert, en in `onWillBundle` verklaart het zijn pakketten en draagt het de wrapper bij:

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

- `getId()` retourneert de id die wordt gebruikt om de extensie in of uit te schakelen en om zijn opties te koppelen, dezelfde rol die `webforj-scss` speelt voor de SCSS-extensie.
- `isEnabledByDefault` activeert de extensie wanneer een `.svelte`-bron aanwezig is, de [activeren op bestandstype](/docs/managing-resources/bundler/extensions/overview#activated-by-file-type) regel die de gecureerde extensies volgen.
- `onWillBundle` verklaart de bouwafhankelijkheden die de compiler nodig heeft en draagt de Bun-plugin bij onder de id van de extensie.

## De extensie registreren {#registering-the-extension}

De build ontdekt een extensie als een service op het classpath. Voeg een servicebestand toe met de naam `BundleExtension`, met de volledig gekwalificeerde naam van de klasse:

```text title="META-INF/services/com.webforj.bundle.bun.BundleExtension"
com.example.SvelteExtension
```

Met het servicebestand op zijn plaats, compileert een `.svelte`-bron nu, en de view consumeert het element dat de component registreert.

## Opties {#options}

De extensie forward de opties die je onder zijn id instelt rechtstreeks naar de Bun-plugin. Stel ze in `bun.config.ts` in:

```ts title="src/main/frontend/bun.config.ts"
export const options = {
  'svelte': { /* bun-plugin-svelte options */ }
};
```
