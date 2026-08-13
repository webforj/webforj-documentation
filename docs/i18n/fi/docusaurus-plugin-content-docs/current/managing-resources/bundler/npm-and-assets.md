---
title: Packages and assets
sidebar_position: 30
sidebar_class_name: new-content
description: >-
  Declare npm packages, load a module straight from one, install build-only
  dependencies, rely on tree shaking, and import CSS and assets from a
  component's entry.
_i18n_hash: 3b538e3785ee74397f156dd38ef8506a
---
Entry perustuu enemmän kuin omaan lähteeseensä. Se tuo paketteja npm:stä, lataa moduulin suoraan yhdestä, tuo tyylitiedoston tai kuvan ja voi saapua luokasta, jota laajennat tai kirjastosta, jota käytät. Tämä sivu käsittelee, miten nämä osat saavat rakennteen.

## Paketin määrittäminen {#declaring-a-package}

`@BundlePackage` määrittelee npm-riippuvuuden, jota entry tuo. Rakennus kerää jokaisen määritelmän luokkapolulta ja lisää sen projektiisi [`package.json`](https://docs.npmjs.com/cli/v11/configuring-npm/package-json), sitten [Bun](https://bun.sh/) asentaa sen ennen käännöstä, joten paketti on läsnä, ennen kuin sen entry käännetään. Omia muokkauksiasi kyseiseen tiedostoon säilytetään, ja projekti, joka ei määrittele paketteja ja jolla ei ole omaa `package.json`-tiedostoa, ei pidä mitään, joten npm pysyy poissa rakennuksesta, joka ei tarvitse sitä.

Web-komponenttikirjasto on yleinen tapaus. Määrittele paketti, sitten osoita `@BundleEntry` komponenttimoduuleihin, joita haluat:

```java title="Ui5View.java"
@Route("/ui5")
@BundlePackage(value = "@ui5/webcomponents", version = "^2.0.0")
@BundleEntry("@ui5/webcomponents/dist/Button.js")
@BundleEntry("@ui5/webcomponents/dist/Input.js")
public class Ui5View extends Composite<FlexLayout> {

  private final FlexLayout self = getBoundComponent();

  public Ui5View() {
    self.setAlignment(FlexAlignment.CENTER)
      .setStyle("margin", "1em");

    Element input = new Element("ui5-input");
    input.setProperty("placeholder", "Kirjoita jotain");

    Element button = new Element("ui5-button");
    button.setProperty("design", "Emphasized");
    button.setText("Sano hei");
    button.addEventListener("click", e ->
      Toast.show("Hei " + input.getProperty("value"))
    );

    self.add(input, button);
  }
}
```

`version` seuraa npm-version syntaksia, joten `^2.0.0` hyväksyy yhteensopivia 2.x-julkaisuja. Sekä `@BundlePackage` että `@BundleEntry` ovat toistettavia, joten luokka voi määritellä niin monta pakettia ja ladata niin monta moduulia kuin tarvitsee.

### Tiedostoeentry tai npm-moduuli {#a-file-entry-or-an-npm-module}

`@BundleEntry`:n arvo on yksi kahdesta asiasta: polku tiedostoon, jonka kirjoitat `src/main/frontend`-kansioon, tai polku moduuliin npm-paketin sisällä. Edellä oleva näkymä nimeää moduulipolkuja `@ui5/webcomponents`-kansiossa, joten sillä ei ole omaa lähdetiedostoa. Kukin näistä moduuleista rekisteröi oman mukautetun elementtinsä ladattaessa, minkä vuoksi näkymä käyttää `ui5-input` ja `ui5-button` ilman kääreitä. Tiedostoeentry osoittaa sen sijaan `.ts`, `.js` tai `.css` tiedostoon, jonka olet kirjoittanut, käännetty samalla tavalla.

### Rakennusriippuvuudet {#build-dependencies}

Paketille, jota tarvitaan vain lähteiden kääntämiseen, ei ajonaikaisesti, on rakennusriippuvuus. Aseta `dev = true` @BundlePackage:lla, ja rakennus asentaa sen `devDependency`:ksi:

```java
@BundlePackage(value = "typescript", version = "^5.0.0", dev = true)
```

Kuratut laajennokset käyttävät tätä paketteihin, joita niiden kääntäjät tarvitsevat, minkä vuoksi SCSS-lähde tuo `sass`:n rakennusriippuvuudeksi ja ei mitään ajonaikaisesti.

## Vain mitä tuodaan {#tree-shaking}

Kompilaattori sisällyttää vain sen koodin, jonka entry todellisuudessa tuo. `@ui5/webcomponents/dist/Button.js` nimeäminen tuo Button-komponentin ja mitä se tarvitsee, ei loput kirjastosta. Laaja kirjasto maksaa vain osista, joita käytät, joten suuren paketin määrittämisessä ja yhdestä moduulista lataamisessa ei ole rangaistusta.

### Jaettu koodi {#shared-code}

Kun kaksi entrytä tuo saman paketin, rakennus koostaa jaetun koodin osaksi, jota molemmat lataavat, sen sijaan että kopioisi sen jokaiseen. Useat komponentit, jotka on rakennettu saman kirjaston päälle, esimerkiksi joukko Lit-elementtejä, jakavat kyseisen kirjaston koodin sivulla sen sijaan, että maksaisivat siitä kerran per elementti.

## Kuinka entryt latautuvat {#how-entries-load}

Entry tuottaa skriptin, tyylitiedoston tai molemmat, ja ajonaika lataa tuon ulostulon ensimmäisen kerran, kun sen luokan komponentti luodaan, riippumatta siitä, missä komponenttia käytetään ja kuinka syvällä se on kenkänsä. Reititetty näkymä ja asettelu ovat komponentteja kuten muutkin, joten entry sitoutuu komponentin luomiseen, ei reitittämiseen. Kaksi yksityiskohtaa seuraa luokkakirjoituksen elämisestä:

- **Perintö.** `@BundleEntry` ja `@BundlePackage` periytyvät. Perusluokka määrittelee entryn, ja alaluokka, joka ei lisää mitään omaa, lataa silti sen.
- **Vikapaikat.** Annettu entry `@BundleEntry(value = "...", debug = true)` lataa vain, kun sovellus ajetaan debug-tilassa, mikä sopii vain kehityksen diagnostiikaksi.

## CSS:n ja resurssien tuominen {#importing-css-and-assets}

Komponentin entry käsittelee tyylitiedostoja ja muita resursseja tuontien kautta, ilman annotaatiota ja ilman laajennusta. Bun ratkaisee ne käännöshetkellä.

Tuo tyylitiedosto sen sivuvaikutuksen vuoksi, ja pakan niputtaja sisällyttää sen entryn tyyleihin. Tuo ei-koodiresurssi, ja tuonti antaa sinulle URL-osoitteen käytettäväksi:

```ts title="card/card.ts"
import './card.css';
import logoPath from './logo.svg';

const logo = new URL(logoPath, import.meta.url).href;
// käytä logoa kuvana elementin sisällä
```

Ratkaise resurssin URL-osoite `import.meta.url`:n mukaan, ei asiakirjan mukaan, jotta se osoittaa koottuun resurssi, riippumatta siitä, missä ulostulo tarjotaan.

Tuo tyylitiedosto tekstinä sen sijaan, ja sovita se varjojuureen, jotta tyylit rajoittuvat yhteen elementtiin:

```ts title="swatch/swatch.ts"
import sheet from './swatch.css' with { type: 'text' };

class ColorSwatch extends HTMLElement {
  connectedCallback() {
    const root = this.attachShadow({ mode: 'open' });
    const style = document.createElement('style');
    style.textContent = sheet;
    root.append(style);
  }
}
```

Entry voi olla myös pelkkä `.css`-tiedosto ilman skriptiä, sidottu luokkaan samalla tavalla kuin skriptien entryt. Ajonaika lataa sen tyyliksi näkymälle:

```java title="ThemeView.java"
@Route("/theme")
@BundleEntry("theme/theme.css")
public class ThemeView extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();

  public ThemeView() {
    self.add(new Div("Tyylitelty CSS:llä vain bundle entry")
                 .addClassName("themed-label"));
  }
}
```
