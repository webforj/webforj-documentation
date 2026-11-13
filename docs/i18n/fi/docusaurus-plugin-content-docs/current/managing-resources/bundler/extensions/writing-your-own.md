---
sidebar_class_name: experimental-content
title: Oman laajennuksen kirjoittaminen
sidebar_position: 70
description: >-
  Add a build step by shipping a BundleExtension that declares packages,
  contributes a Bun plugin, and activates on a file type, shown by building a
  Svelte extension end to end.
_i18n_hash: bffbcd5a76f34d542765e3c2b59ed24d
---
<DocChip chip='since' label='26.01' />
<DocChip chip='experimental' />

Lisää kääntäjä toiselle lähdetyypille toimittamalla laajennus, joka noudattaa samaa sopimusta kuin sisäänrakennetut laajennukset. Näin mukautettu laajennus toimii samalla tavalla kuin toimitettu laajennus.

Tällä sivulla rakennetaan **[Svelte](https://svelte.dev/)**-laajennus kokonaisena esimerkkinä. Svelte on komponenttikehys, jonka `.svelte`-tiedostot käännetään JavaScriptiksi koontivaiheessa. Bundleri ei käsittele kyseistä lähdekoodia itsenäisesti, joten Svelte-komponentin käyttäminen webforJ:ssä edellyttää koonnin opettamista kääntämään se. Juuri tämän laajennus tekee. Siksi se sopii esimerkiksi: uusi lähdetyyppi opetetaan koonnille kirjoittamallasi laajennuksella, minkä jälkeen se sidotaan luokkaan samalla tavoin kuin mikä tahansa muukin kohde.

<!-- Do NOT use <ExperimentalWarning /> on this page. -->
:::info Esikatselussa oleva API
Laajennus-API on esikatselussa, ja se saattaa muuttua julkaisuversioiden välillä sen vakiintuessa. Mekanismi itsessään on pysyvä, ja sisäänrakennetut laajennukset perustuvat samaan sopimukseen, mutta menetelmien allekirjoitukset voivat muuttua. Varaudu siis muokkaamaan mukautettua laajennusta, kun päivität webforJ:n.
:::

## Koontiajan riippuvuus {#the-build-time-dependency}

Laajennuksen kirjoittamista varten käännä se bundlerin API:a vasten. Lisää riippuvuus `provided`-laajuudella, sillä koonti toimittaa sen eikä sitä sisällytetä toimitukseesi:

```xml title="pom.xml"
<dependency>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-bundle-bun</artifactId>
  <version>${webforj.version}</version>
  <scope>provided</scope>
</dependency>
```

## Plugin-kääre {#the-bun-plugin-wrapper}

Laajennus tuo käyttöön Bun-pluginin. Sijoita plugin pieneen `.mjs`-resurssitiedostoon. Se vie oletuksena tehtaana toimivan funktion, jota koonti kutsuu tälle laajennukselle määrittämilläsi asetuksilla. Funktio palauttaa Bun-pluginin:

```js title="src/main/resources/frontend-extensions/svelte.mjs"
import { SveltePlugin } from 'bun-plugin-svelte';

export default (options) => SveltePlugin({ forceSide: 'client', ...options });
```

## Laajennus {#the-extension}

Toteuta `BundleExtension`. Se nimeää itsensä tunnisteella, määrittää, milloin se aktivoituu, ja ilmoittaa paketit sekä tuo kääreen käyttöön `onWillBundle`-metodissa:

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

Tässä esimerkissä:

- `getId()` palauttaa tunnisteen, jolla laajennus otetaan käyttöön tai poistetaan käytöstä ja jonka alle sen asetukset määritetään. SCSS-laajennuksessa vastaavassa roolissa on `webforj-scss`.
- `isEnabledByDefault` aktivoi laajennuksen, kun käytössä on `.svelte`-lähdetiedosto. Tämä on [tiedostotyypin perusteella aktivoinnin](/docs/managing-resources/bundler/extensions/overview#activated-by-file-type) sääntö, jota valikoidut laajennukset noudattavat.
- `onWillBundle` ilmoittaa kääntäjän tarvitsemat koontiriippuvuudet ja tuo Bun-pluginin käyttöön laajennuksen tunnisteella.

## Laajennuksen rekisteröinti {#registering-the-extension}

Koonti löytää laajennuksen luokkapolulta palveluna. Lisää `BundleExtension`-rajapinnan mukaan nimetty palvelutiedosto, johon merkitään luokan täydellinen nimi:

```text title="META-INF/services/com.webforj.bundle.bun.BundleExtension"
com.example.SvelteExtension
```

Kun palvelutiedosto on paikallaan, `.svelte`-lähdetiedosto käännetään ja näkymä käyttää komponentin rekisteröimää elementtiä.

## Asetukset {#options}

Laajennus välittää tunnisteensa alle määrittämäsi asetukset suoraan Bun-pluginille. Määritä ne tiedostossa `bun.config.ts`:

```ts title="src/main/frontend/bun.config.ts"
export const options = {
  'svelte': { /* bun-plugin-svelte options */ }
};
```
