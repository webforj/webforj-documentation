---
title: Extensions
hide_giscus_comments: true
sidebar_class_name: new-content
description: >-
  Understand how a bundler extension contributes a compiler, the three ways an
  extension activates, how to configure one through bun.config.ts, and how to
  write your own.
_i18n_hash: 00c5421ebf8023e2d273f3836176733e
---
import Tabs from '@theme/Tabs';
import TabItem from '@theme/TabItem';

Kokoaja ei tiedä, kuinka kääntää SCSS-tyylitiedostoa yksin. Se on **laajennuksen** tehtävä: yksikkö, joka tuo kääntäjän yhdenlaista lähdettä varten, ilmoittaa kääntäjän tarvitsemat npm-paketit ja voi luoda omia merkintöjään. webforJ toimittaa muutaman laajennuksen, ja malli on avoin, joten voit lisätä omasi mihin tahansa muuhun lähteeseen. SCSS, Less ja Tailwind eivät ole erillisiä ominaisuuksia, jotka on kiinnitetty kääntäjään. Ne ovat laajennuksia, ja ne jakavat saman mallin.

Kun tiedät, kuinka laajennus aktivoituu ja mitä se tuo, jokainen laajennus luetaan samalla tavalla, sekä toimitetut että kirjoittamasi.

## Mitä laajennus tuo {#what-an-extension-contributes}

Laajennukselle annetaan mahdollisuus toimia ennen käännöksen suorittamista. Kun se toimii, se voi tehdä kolme asiaa:

- **Ilmoita paketeista.** Se lisää kääntäjän tarvitsemat npm-paketit, yleensä rakennusriippuvuuksina, jotta ne asennetaan ennen käännöstä.
- **Toimita kääntäjä.** Se rekisteröi Bun-laajennuksen, joka opettaa käännökselle, miten käsitellä tietynlaista lähdettä.
- **Generoi merkinnät.** Se voi kirjoittaa oman frontend-merkintänsä, mikä on tapa, jolla Tailwind tuottaa tyylitiedoston ilman, että sinun tarvitsee kirjoittaa sellaista.

Jokainen laajennus kantaa **id**:ta, lyhyttä nimeä, kuten `webforj-scss` tai `webforj-less`. ID:tä käytetään viittaamaan laajennukseen konfiguraatiossa ja siihen, miten sen asetukset saavuttavat sen.

## Kuinka laajennus aktivoi {#how-an-extension-activates}

Laajennus tekee työtään vain, kun se on aktiivinen. On kolme tapaa, joilla se voi tulla aktiiviseksi, ja tietäminen, mitä polkua integraatio käyttää, kertoo tarkalleen, milloin se suoritetaan.

### Aktivoi tiedostotyypin mukaan {#activated-by-file-type}

Kuraattori-laajennus ilmoittaa käsittelemänsä tiedostotyyppien laajennukset, ja se kytkeytyy päälle, kun kyseisen tyyppistä lähdettä on läsnä `src/main/frontend` -hakemistossa. Et ota sitä käyttöön. Tiedoston kirjoittaminen on signaali.

Kirjoita `.scss`-tiedosto, ja SCSS-laajennus aktivoituu. Kirjoita `.less`-tiedosto, ja Less-laajennus aktivoituu. Poista viimeinen kyseisen tyyppinen lähde, ja laajennus pysyy pois päältä, joten sen paketteja ei koskaan asenneta eikä sen kääntäjää koskaan kutsuta. Tämä pitää rakennuksen kevyenä: projekti maksaa vain niistä lähteistä, joita se todella kirjoittaa. Kirjoittamasi laajennus noudattaa samaa sääntöä, aktivoitumalla ilmoittamansa tiedostotyypin mukaan.

### Ota käyttöön ID:n mukaan {#enabled-by-id}

Jokainen laajennus voidaan kytkeä päälle tai pois päältä sen ID:n mukaan, joka ohittaa tiedostotyypin oletuksen. Tämä on tärkeää kahdessa tapauksessa. Laajennus, joka toimitetaan pois päältä, kuten Tailwind, otetaan käyttöön ID:n avulla. Ja laajennus, joka aktivoituisi olemassa olevan lähteen perusteella, voidaan pakottaa pois päältä ID:n avulla.

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

Yllä oleva lohko kytkee Tailwindin päälle ja kytkee SCSS-kääntämisen pois päältä, vaikka `.scss`-lähde on läsnä.

### Kun laajennusta ei tarvita {#when-no-extension-is-needed}

Laajennus on olemassa kääntäjän toimittamiseksi. Kehys, joka renderöi suorituksessa eikä käänny jotain uutta, ei tarvitse kääntäjää, joten se ei tarvitse laajennusta. Bun käsittelee jo TypeScriptin ja JSX:n, joilla tällainen kehys on kirjoitettu.

[React](https://react.dev/) on JavaScript-kirjasto, joka on suunniteltu käyttäjäliittymien rakentamiseen, ja se on selvin tapaus. Sen merkintä on tavallinen TypeScript, jonka Bun kääntää ilman laajennusta. Käärit komponentin mukautetuksi elementiksi valitsemallasi kirjastolla, kuten `@r2wc/react-to-web-component`, ilmoitat React-paketit `@BundlePackage`:llä, ja näkymä kuluttaa syntynyttä elementtiä. Ei ole React-laajennusta, jota aktivoidaan, koska ei ole React-kääntäjää, jota voitaisiin toimittaa, vain paketteja asennettavaksi.

## Laajennuksen konfigurointi {#configuring-an-extension}

Jotkin laajennukset ottavat asetuksia. SCSS-kääntäjä ottaa esimerkiksi kuormituspolun. Asetukset sijaitsevat tiedostossa `bun.config.ts`, tiedostossa, jonka luot frontend-lähdejuuren juureen, `src/main/frontend/bun.config.ts`, lähettyvillä kirjoittamiesi merkintöjen. Ne menevät `options`-objektiin, jonka avaimena on laajennuksen ID, ja rakennus antaa jokaiselle laajennukselle sen oman ID:n alla tallennetun objektin:

```ts title="src/main/frontend/bun.config.ts"
export const options = {
  'webforj-scss': { loadPaths: ['styles'] }
};
```

:::tip Mistä asetukset tulevat
Laajennus välittää asetuksensa suoraan työkaluun, jota se ympäröi, joten laajennuksen hyväksymät asetukset ovat kyseisen työkalun asetuksia. Jokaisen alla olevan laajennussivun kohdalla mainitaan työkalu, johon se välittää, ja linkittää sen viitteeseen, jossa löydät täydellisen luettelon.
:::

Sama tiedosto voi liittää ylimääräisiä Bun-laajennuksia oletusvientiä kautta, rakennusvaihetta, jota mikään kuratoitu laajennus ei kata:

```ts title="src/main/frontend/bun.config.ts"
import myPlugin from './my-plugin';

export default [myPlugin()];
```

## Kuratoidut laajennukset {#the-curated-extensions}

webforJ toimittaa laajennuksen SCSS:lle, Less:lle ja Tailwindille. Kukin seuraa yllä esitettyä mallia: se aktivoituu, kun sen lähdetyyppi on läsnä, ilmoittaa sen kääntäjän tarvitsemat paketit rakennusriippuvuuksina ja tuo kääntäjän.

| Laajennus | ID | Aktivoi | Asentaa (rakennusriippuvuus) |
|-----------|----|---------|------------------------------|
| [SCSS ja Sass](/docs/managing-resources/bundler/extensions/scss) | `webforj-scss` | `.scss`- tai `.sass`-lähde | `sass` |
| [Less](/docs/managing-resources/bundler/extensions/less) | `webforj-less` | `.less`-lähde | `less` |
| [Tailwind](/docs/managing-resources/bundler/extensions/tailwind) | `webforj-tailwind` | pois päältä oletuksena, aktivoituu ID:n avulla | `tailwindcss`, `bun-plugin-tailwind` |

Käänteisen toisen tyyppisen lähteen, kirjoitat oman laajennuksesi saman sopimuksen mukaan. Katso [Oman laajennuksen kirjoittaminen](/docs/managing-resources/bundler/extensions/writing-your-own), joka rakentaa sen päästä päähän.
