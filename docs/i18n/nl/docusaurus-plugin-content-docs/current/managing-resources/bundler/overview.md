---
title: Frontend bundler
hide_table_of_contents: true
hide_giscus_comments: true
sidebar_class_name: new-content
description: >-
  Understand how the webforJ frontend bundler turns class-level annotations into
  compiled, per-route frontend assets, when to reach for it, and how a class
  binds to the entry it needs.
_i18n_hash: 1276a78ae5197924d6577eae5bafe73b
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

# Frontend bundler <DocChip chip='since' label='26.01' /> {#frontend-bundler}

De frontend bundler stelt een webforJ-klasse in staat om gebruik te maken van het [npm](https://www.npmjs.com/) ecosysteem, componenten te schrijven in [React](https://react.dev/), [Svelte](https://svelte.dev/) of [Lit](https://lit.dev/), en [SCSS](https://sass-lang.com/) te authoren, allemaal vanuit een Java-project zonder dat er een Node-toolchain hoeft te worden geïnstalleerd of uitgevoerd. Een klasse benoemt de frontend die het nodig heeft met een annotatie, en de build installeert de pakketten, compileert de bronnen en laad het resultaat wanneer een component van die klasse wordt aangemaakt.

De bundler draait als onderdeel van de [webforJ build plugin](/docs/configuration/build-plugin), die je eenmaal toevoegt aan je Maven- of Gradle-build. De mechanismen voor het compileren van een specifieke soort bron, SCSS, [Less](https://lesscss.org/), [Tailwind](https://tailwindcss.com/), en de rest, zijn het werk van [extensies](/docs/managing-resources/bundler/extensions/overview).

## Wanneer je de bundler nodig hebt {#when-you-need-the-bundler}

webforJ draait zonder bundler. Als je al een script of een stylesheet hebt en je wilt deze aan een component of de app koppelen, doen de [asset annotaties](/docs/managing-resources/importing-assets) dat zonder bouwstap, zonder npm en zonder compilatie.

De bundler verdient zijn plek wanneer de frontend meer is dan een statisch bestand:

- Je wilt een pakket van npm, bij naam en versie, geïnstalleerd en gecompileerd in je app.
- Je wilt een component in React, Svelte of Lit schrijven en deze vanuit Java consumeren.
- Je wilt SCSS, Sass of Less authoren, of Tailwind-hulpmiddelen compileren.
- Je wilt frontend tests uitvoeren als onderdeel van de build.

De bundler is het standaardpad voor dat werk, en het doet alles wat de asset annotaties doen, zodat een project dat het aanneemt de eenvoudigere optie niet verliest.

## Het toevoegen aan een bestaand project {#adding-it-to-an-existing-project}

De bundler is op instapbasis, dus je kunt het toevoegen aan een app die al de [asset annotaties](/docs/managing-resources/importing-assets) gebruikt en het alleen gebruiken waar je het nodig hebt.

1. Voeg de [webforJ build plugin](/docs/configuration/build-plugin) toe aan je Maven- of Gradle-build. Het beheert Bun voor jou, dus er is geen Node-toolchain te installeren.
2. Author je frontend-bronnen onder `src/main/frontend`.
3. Declareer wat een klasse nodig heeft met `@BundlePackage` en `@BundleEntry`.

Je bestaande `@StyleSheet`, `@JavaScript`, en de andere asset annotaties blijven onveranderd werken, zodat je een bron naar de bundler kunt verplaatsen wanneer je een pakket, een gecompileerde bron of een test wilt, en de rest ongemoeid kunt laten.

## Een klasse aan een entry binden {#binding-a-class-to-an-entry}

`@BundleEntry` bindt een klasse aan een frontend entry, en `@BundlePackage` declareert de npm-pakketten die die entry importeert. Beide leven op de klasse, zodat de frontend die een view nodig heeft met de view meereist.

```java title="GreetingView.java"
@Route("/greeting")
@BundlePackage(value = "lit", version = "^2.0.0")
@BundleEntry("greeting/hello-greeting.ts")
public class GreetingView extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();

  public GreetingView() {
    self.add(new Element("hello-greeting"));
  }
}
```

```ts title="greeting/hello-greeting.ts"
import { LitElement, html } from 'lit';
import { customElement } from 'lit/decorators.js';

@customElement("hello-greeting")
class HelloGreeting extends LitElement {
  render() {
    return html`<p>Groeten vanuit webforJ</p>`;
  }
}
```

De entry registreert het `hello-greeting` aangepaste element en is verantwoordelijk voor de rendering. De Java-kant consumeert het met `new Element("hello-greeting")` en luistert naar de gebeurtenissen. De build compileert de entry, installeert `lit`, en laad de uitvoer wanneer `/greeting` wordt gerenderd.

## Onderwerpen {#topics}

<DocCardList className="topics-section" />
