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

De bundler weet niet hoe een SCSS-stylesheet op zichzelf moet worden gecompileerd. Dat is de taak van een **extensie**: een eenheid die de compiler voor een bepaald soort bron bijdraagt, de npm-pakketten declareert die de compiler nodig heeft, en eigen vermeldingen kan genereren. webforJ levert een paar extensies, en het model is open, zodat je je eigen kunt toevoegen voor elke andere bron. SCSS, Less en Tailwind zijn geen aparte functies die aan de bundler zijn toegevoegd. Het zijn extensies, en ze delen hetzelfde model.

Zodra je weet hoe een extensie wordt geactiveerd en wat deze bijdraagt, leest elke extensie op dezelfde manier, zowel de meegeleverde als de extensies die je zelf schrijft.

## Wat een extensie bijdraagt {#what-an-extension-contributes}

Een extensie krijgt de kans om te handelen voordat de compilatie plaatsvindt. Wanneer deze handelt, kan het drie dingen doen:

- **Pakketten declareren.** Het voegt de npm-pakketten toe die de compiler nodig heeft, meestal als bouwafhankelijkheden, zodat ze vóór de compilatie worden geïnstalleerd.
- **Een compiler bijdragen.** Het registreert een Bun-plugin die de compilatie leert hoe om te gaan met een soort bron.
- **Vermeldingen genereren.** Het kan een eigen frontend-vermelding schrijven, wat is hoe Tailwind een stylesheet produceert zonder dat jij er een schrijft.

Elke extensie heeft een **id**, een korte naam zoals `webforj-scss` of `webforj-less`. De id is hoe je naar een extensie verwijst in de configuratie, en hoe de opties deze bereiken.

## Hoe een extensie wordt geactiveerd {#how-an-extension-activates}

Een extensie doet zijn werk alleen als deze actief is. Er zijn drie manieren waarop iemand actief wordt, en weten welk pad een integratie gebruikt, vertelt je precies wanneer het wordt uitgevoerd.

### Geactiveerd door bestands type {#activated-by-file-type}

Een gecureerde extensie verklaart de bestandsextensies die deze verwerkt en schakelt zichzelf in wanneer een bron van dat type aanwezig is onder `src/main/frontend`. Je schakelt het niet in. Het schrijven van het bestand is het signaal.

Schrijf een `.scss`-bestand en de SCSS-extensie activeert. Schrijf een `.less`-bestand en de Less-extensie activeert. Verwijder de laatste bron van dat type en de extensie blijft uitgeschakeld, zodat de pakketten nooit worden geïnstalleerd en de compiler nooit wordt uitgevoerd. Dit houdt een build slank: een project betaalt alleen voor de soorten bronnen die het daadwerkelijk schrijft. Een extensie die je schrijft volgt dezelfde regel en activeert op het bestands type dat het declareert.

### Ingeschakeld via id {#enabled-by-id}

Elke extensie kan aan of uit worden gezet via zijn id, wat de standaard voor bestandstype overschrijft. Dit is belangrijk in twee gevallen. Een extensie die standaard is uitgeschakeld, zoals Tailwind, wordt ingeschakeld via id. En een extensie die zou activeren op basis van een aanwezige bron, kan geforceerd worden uitgeschakeld via id.

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

Het bovenstaande blok schakelt Tailwind in en schakelt de SCSS-compilatie uit, zelfs als er een `.scss`-bron aanwezig is.

### Wanneer er geen extensie nodig is {#when-no-extension-is-needed}

Een extensie bestaat om een compiler bij te dragen. Een framework dat tijdens runtime rendert, in plaats van naar iets nieuws te compileren, heeft geen compiler nodig, dus heeft het geen extensie nodig. Bun behandelt al de TypeScript en JSX waarin een dergelijk framework is geschreven.

[React](https://react.dev/) is een JavaScript-bibliotheek voor het bouwen van gebruikersinterfaces en het duidelijkste geval. De invoer is eenvoudige TypeScript die Bun compileert zonder plugin. Je verpakt de component als een aangepast element met een bibliotheek van jouw keuze, zoals `@r2wc/react-to-web-component`, declareert de React-pakketten met `@BundlePackage`, en de weergave consumeert het resulterende element. Er is geen React-extensie om in te schakelen, omdat er geen React-compiler is om bij te dragen, alleen pakketten om te installeren.

## Een extensie configureren {#configuring-an-extension}

Sommige extensies nemen opties. De SCSS-compiler neemt bijvoorbeeld een laadpad. Opties bevinden zich in `bun.config.ts`, een bestand dat je maakt in de root van je frontend-bronroot, `src/main/frontend/bun.config.ts`, naast de vermeldingen die je schrijft. Ze komen in een `options`-object dat is geëtiketteerd met de extensie-id, en de build geeft elke extensie het object dat is opgeslagen onder zijn eigen id:

```ts title="src/main/frontend/bun.config.ts"
export const options = {
  'webforj-scss': { loadPaths: ['styles'] }
};
```

:::tip Waar de opties vandaan komen
Een extensie geeft zijn opties rechtstreeks door aan de tool die het omhulde, dus de opties die een extensie accepteert, zijn de opties van die tool. Elke extensiepagina hieronder noemt de tool waar het naar doorstuurt en linkt naar de referentie van die tool, waar je de volledige lijst kunt vinden.
:::

Hetzelfde bestand kan extra Bun-plug-ins toevoegen via een standaardexport, voor een bouwstap die door geen gecureerde extensie wordt gedekt:

```ts title="src/main/frontend/bun.config.ts"
import myPlugin from './my-plugin';

export default [myPlugin()];
```

## De gecureerde extensies {#the-curated-extensions}

webforJ levert een extensie voor SCSS, Less en Tailwind. Elke volgt het bovenstaande model: het activeert wanneer zijn brontype aanwezig is, verklaart de pakketten die zijn compiler nodig heeft als bouwafhankelijkheden en draagt de compiler bij.

| Extensie | Id | Activeert bij | Installeert (bouwafhankelijkheid) |
|-----------|----|--------------|------------------------------|
| [SCSS en Sass](/docs/managing-resources/bundler/extensions/scss) | `webforj-scss` | een `.scss` of `.sass`-bron | `sass` |
| [Less](/docs/managing-resources/bundler/extensions/less) | `webforj-less` | een `.less`-bron | `less` |
| [Tailwind](/docs/managing-resources/bundler/extensions/tailwind) | `webforj-tailwind` | standaard uitgeschakeld, ingeschakeld via id | `tailwindcss`, `bun-plugin-tailwind` |

Om een ander soort bron te compileren, schrijf je je eigen extensie op hetzelfde contract. Zie [Schrijf je eigen extensie](/docs/managing-resources/bundler/extensions/writing-your-own), die er een van begin tot eind bouwt.
