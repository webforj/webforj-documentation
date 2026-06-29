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

# Frontend-bundler <DocChip chip='since' label='26.01' /> {#frontend-bundler}

Frontend-bundleri mahdollistaa webforJ-luokan hyödyntävän [npm](https://www.npmjs.com/) ekosysteemiä, komponenttien kirjoittamisen [React](https://react.dev/), [Svelte](https://svelte.dev/) tai [Lit](https://lit.dev/) avulla sekä [SCSS](https://sass-lang.com/) -koodin laatimisen, kaikki Java-projektista ilman Node-työkaluketjun asentamista tai käyttöä. Luokka nimeää tarvitsemansa frontendin annotaatiolla, ja rakentaminen asentaa paketit, kääntää lähdekoodit ja lataa tuloksen, kun kyseisestä luokasta luodaan komponentti.

Bundleri toimii osana [webforJ-build-pluginia](/docs/configuration/build-plugin), joka lisätään kerran Maven- tai Gradle-rakennukseen. Tietynlaisten lähdejärjestelmien, kuten SCSS:n, [Less](https://lesscss.org/), [Tailwind](https://tailwindcss.com/) jne., kääntäminen on [laajennusten](/docs/managing-resources/bundler/extensions/overview) vastuulla.

## Kun tarvitset bundlerin {#when-you-need-the-bundler}

webforJ toimii ilman bundleria. Jos sinulla on jo skripti tai tyylitiedosto ja haluat liittää sen komponenttiin tai sovellukseen, [assets-annotaatiot](/docs/managing-resources/importing-assets) tekevät sen ilman rakentamisvaihetta, ilman npm:ää ja ilman käännöstä.

Bundleri ansaitsee paikkansa, kun frontend on enemmän kuin staattinen tiedosto:

- Haluat paketin npm:stä, nimeltä ja versiolta, asennettuna ja käännettynä sovellukseesi.
- Haluat kirjoittaa komponentin Reactissa, Sveltessä tai Litissä ja käyttää sitä Javasta.
- Haluat laatia SCSS-, Sass- tai Less-tiedostoja tai kääntää Tailwind-työkaluja.
- Haluat suorittaa frontend-testit osana rakennusprosessia.

Bundleri on oletuspolku tälle työlle, ja se tekee kaiken, mitä assets-annotaatiot tekevät, joten projekti, joka ottaa sen käyttöön, ei menetä yksinkertaisempaa vaihtoehtoa.

## Sen lisääminen olemassa olevaan projektiin {#adding-it-to-an-existing-project}

Bundleri on valinnainen, joten voit lisätä sen sovellukseen, joka jo käyttää [assets-annotaatioita](/docs/managing-resources/importing-assets), ja käyttää sitä vain siellä, missä tarvitset.

1. Lisää [webforJ-build-plugin](/docs/configuration/build-plugin) Maven- tai Gradle-rakennukseesi. Se hallinnoi Bunia puolestasi, joten Node-työkaluketjua ei tarvitse asentaa.
2. Laadi frontend-lähteesi kansioon `src/main/frontend`.
3. Julista, mitä luokka tarvitsee, käyttämällä `@BundlePackage` ja `@BundleEntry`.

Olemassa olevat `@StyleSheet`, `@JavaScript` ja muut assets-annotaatiot toimivat muuttumattomina, joten voit siirtää resurssin bundlerin käyttöön, kun tarvitset pakettia, käännettyä lähdettä tai testiä, ja jättää loput rauhaan.

## Luokan sitominen merkintään {#binding-a-class-to-an-entry}

`@BundleEntry` sitoo luokan frontend-merkintään, ja `@BundlePackage` julistaa npm-paketit, joita merkintä tuo. Molemmat sijaitsevat luokassa, joten tarvittava frontend näkyy näkymän mukana.

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
    return html`<p>Greetings from webforJ</p>`;
  }
}
```

Merkintä rekisteröi `hello-greeting` -räätälöidyn elementin ja omistaa sen renderöinnin. Java-puoli käyttää sitä `new Element("hello-greeting")` ja kuuntelee sen tapahtumia. Rakennus kääntää merkinnän, asentaa `lit`-kirjaston ja lataa tuloksen, kun `/greeting` renderöidään.

## Aiheet {#topics}

<DocCardList className="topics-section" />
