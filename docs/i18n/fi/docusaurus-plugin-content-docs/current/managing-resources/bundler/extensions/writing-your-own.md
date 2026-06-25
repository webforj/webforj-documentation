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

Voit lisätä kääntäjän toisen tyyppisen lähteen lisäämällä laajennuksen, joka käyttää samaa sopimusta kuin sisäänrakennetut laajennukset, joten mukautettu laajennus lukee tarkalleen kuten toimitettu.

Tällä sivulla rakennetaan **[Svelte](https://svelte.dev/)**-laajennus täydellisenä esimerkkinä. Svelte on komponenttipohjainen kehys, jonka `.svelte`-tiedostot käännetään JavaScriptiksi rakennusvaiheessa. Pakkaaja ei käsittele tuota lähdettä yksinään, joten Svelte-komponentin kuluttaminen webforJ:ltä tarkoittaa, että rakennukselle opetetaan kääntämään se, mikä on juuri se, mitä laajennus tekee. Tämä tekee siitä sopivan esimerkin: uusi lähdetyyppi, jonka rakennus opetetaan kirjoittamasi laajennuksen avulla, sitten sidottu luokkaan samalla tavalla kuin mikä tahansa muu syöte.

<!-- ÄLÄ käytä <ExperimentalWarning /> tällä sivulla. -->
:::warning Kokeellinen API
Laajennus API on kokeellinen, ja se voi muuttua julkaisujen välillä sen vakiinnuttaessa. Mekanismi itsessään jää voimaan, sisäänrakennetut laajennukset perustuvat samaan sopimukseen, mutta sen menetelmien allekirjoitukset voivat muuttua, joten odota mukauttavasi mukautettua laajennusta, kun päivität webforJ:ta.
:::

## Rakennusaikainen riippuvuus {#the-build-time-dependency}

Kirjoittaaksesi laajennuksen, sinun on käännettävä pakkaajan API:n mukaan. Lisää se `provided`-alueella, koska rakennus toimittaa sen etkä sinä toimita sitä:

```xml title="pom.xml"
<dependency>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-bundle-bun</artifactId>
  <version>${webforj.version}</version>
  <scope>provided</scope>
</dependency>
```

## Laajennuksen wrapper {#the-bun-plugin-wrapper}

Laajennus antaa panoksensa Bun-laajennukseen. Pidä laajennus pienessä `.mjs`-resurssissa. Se vie oletusvientinä tehtaan, jota rakennus kutsuu asetusten kanssa, jotka asetat tälle laajennukselle, ja palauttaa Bun-laajennuksen:

```js title="src/main/resources/frontend-extensions/svelte.mjs"
import { SveltePlugin } from 'bun-plugin-svelte';

export default (options) => SveltePlugin({ forceSide: 'client', ...options });
```

## Laajennus {#the-extension}

Toteuta `BundleExtension`. Se nimeää itsensä id:llä, päättää milloin se aktivoituu, ja `onWillBundle`-menetelmässä julkistaa pakettinsa ja antaa wrapperin:

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
        throw new IOException("puuttuva wrapper " + path);
      }

      return new String(in.readAllBytes(), StandardCharsets.UTF_8);
    } catch (IOException e) {
      throw new UncheckedIOException(e);
    }
  }
}
```

Tässä esimerkissä:

- `getId()` palauttaa id:n, jota käytetään laajennuksen kytkemiseen päälle tai pois sekä sen asetusten avaimena, sama rooli kuin `webforj-scss` SCSS-laajennukselle.
- `isEnabledByDefault` aktivoi laajennuksen kun `.svelte`-lähde on läsnä, [aktivointi tiedostotyypin mukaan](/docs/managing-resources/bundler/extensions/overview#activated-by-file-type) sääntö, jota kuratoidut laajennukset noudattavat.
- `onWillBundle` julkistaa rakennusriippuvuudet, joita kääntäjä tarvitsee, ja antaa Bun-laajennuksen laajennuksen id:n alla.

## Laajennuksen rekisteröinti {#registering-the-extension}

Rakennus havaitsee laajennuksen palveluna luokkatiessä. Lisää palvelutiedosto, joka on nimetty `BundleExtension`, täydellisesti kvalifioidulla luokan nimellä:

```text title="META-INF/services/com.webforj.bundle.bun.BundleExtension"
com.example.SvelteExtension
```

Kun palvelutiedosto on paikallaan, nyt `.svelte`-lähde käännetään, ja näkymä käyttää komponentin rekisteröimää elementtiä.

## Asetukset {#options}

Laajennus välittää asetukset, jotka asetat sen id:n alla suoraan Bun-laajennukselle. Aseta ne `bun.config.ts`-tiedostoon:

```ts title="src/main/frontend/bun.config.ts"
export const options = {
  'svelte': { /* bun-plugin-svelte asetukset */ }
};
```
