---
title: Creating a Basic App
sidebar_position: 2
description: Step 1 - Add components to an app.
_i18n_hash: d7385c22706cf76508b7e1971186f88d
---
In [Projektin asetukset](/docs/introduction/tutorial/project-setup) loit webforJ-projektin. Nyt on aika luoda projektin pääluokka ja lisätä interaktiivinen käyttöliittymä webforJ-komponentteja käyttäen. Tässä vaiheessa opit seuraavista asioista:

- WebforJ:n ja Spring Bootin käyttävien sovellusten aloituspisteestä
- WebforJ- ja HTML-elementtien komponenteista
- CSS:n käyttämisestä komponenttien tyylittämiseen

Tämän vaiheen suorittaminen luo version [1-creating-a-basic-app](https://github.com/webforj/webforj-tutorial/tree/main/1-creating-a-basic-app).

<!-- Insert video here -->

## Sovelluksen suorittaminen {#running-the-app}

Sovellusta kehittäessäsi voit käyttää [1-creating-a-basic-app](https://github.com/webforj/webforj-tutorial/tree/main/1-creating-a-basic-app) vertailuna. Näet sovelluksen toiminnassa:

1. Siirry ylimmälle tasolle hakemistoon, joka sisältää `pom.xml`-tiedoston, tämä on `1-creating-a-basic-app`, jos seuraat GitHubin versiota.

2. Suorita seuraava Maven-komento Spring Boot -sovelluksen suorittamiseksi paikallisesti:
    ```bash
    mvn
    ```

Sovelluksen suorittaminen avaa automaattisesti uuden selaimen osoitteessa `http://localhost:8080`.

## Aloituspiste {#entry-point}

Jokaisessa webforJ-sovelluksessa on yksi luokka, joka laajentaa <JavadocLink type="foundation" location="com/webforj/App" code='true'>App</JavadocLink>. Tätä opasta varten, sekä muissa julkaistuissa webforJ-projekteissa, sitä kutsutaan yleensä nimellä `Application`. Tämä luokka on pakkaus, jonka nimi vastaa [Projektin asetuksissa](/docs/introduction/tutorial/project-setup) käyttämääsi `groupId`-arvoa:

```
1-creating-a-basic-app
│   .editorconfig
│   .gitignore
│   pom.xml
│   README.md
│
├───.vscode
├───src/main/java
// highlight-next-line
│   └──com/webforj/tutorial
// highlight-next-line
│       └──Application.java
└───target
```

`Application`-luokassa `SpringApplication.run()` -metodi käyttää asetuksia sovelluksen käynnistämiseen. Erilaiset anotoinnit ovat sovelluksen asetuksia varten.

```java title="Application.java"
@SpringBootApplication
@BundleEntry("css/card.css")
@AppTheme("system")
@AppProfile(name = "Asiakassovellus", shortName = "CustomerApp")
public class Application extends App {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }
}
```

### Annotoinnit {#annotations}

[`@SpringBootApplication`](https://docs.spring.io/spring-boot/api/java/org/springframework/boot/autoconfigure/SpringBootApplication.html) on keskeinen annotaatio Spring Bootissa. Aseta tämä annotaatio pääluokkaan merkitäksesi sen sovelluksesi lähtökohtana.

`@BundleEntry`, `@AppTheme` ja `@AppProfile` ovat vain muutamia <JavadocLink type="foundation" location="com/webforj/annotation/package-summary">webforJ-annotaatioita</JavadocLink>, joita voit käyttää, kun haluat eksplisiittisesti asettaa asetuksia.

- **`@BundleEntry`** lisää tiedoston `src/main/frontend`:istä sovelluksen frontend-pakettiin. Tässä vaiheessa se lataa CSS-tiedoston, jonka tulet luomaan myöhemmin [CSS:n tyylittelyssä](#styling-with-css).

- **`@AppTheme`** hallitsee sovelluksen visuaalista teemaa. Jos se asetetaan `system`:iksi, sovellus ottaa automaattisesti käyttäjän suosiman teeman: `light`, `dark` tai `dark-pure`. Tietoja mukautettujen teemojen luomisesta tai oletusteemojen ylittämisestä löytyy [Teemoista](/docs/styling/themes).

- **`@AppProfile`** auttaa määrittämään, miten sovellus esitetään käyttäjälle asennettavana sovelluksena [installoitavana sovelluksena](/docs/configuration/installable-apps). Vähintään tämä annotaatio tarvitsee `name`:n sovelluksen täydelle nimelle ja `shortName`:n, jota käytetään, kun tilaa on rajoitetusti. `shortName`:n ei tule ylittää 12 merkkiä.

## Käyttöliittymän luominen {#creating-a-ui}

Luodaksesi käyttöliittymäsi, sinun on lisättävä [HTML-elementtien komponentteja](/docs/components/html-elements) ja [webforJ-komponentteja](/docs/components/overview). Tällä hetkellä sinulla on vain yksisivuinen sovellus, joten lisäät komponentteja suoraan `Application`-luokkaan. Tämä tehdään ylikirjoittamalla `App.run()` -metodi ja luomalla `Frame`, jolle lisätään komponentteja.

```java
@Override
public void run() throws WebforjException {
  Frame mainFrame = new Frame();

  // Luo käyttöliittymäkomponentit ja lisää ne kehykseen

}
```

### HTML-elementtien käyttäminen {#using-html-elements}

Voit lisätä standardeja HTML-elementtejä sovellukseesi [HTML-elementtien komponenttien](/docs/components/html-elements) avulla. Luo uusi instanssi komponentista ja käytä sitten `add()`-metodia sen lisäämiseksi `Frame`:een:

```java
// Luo säiliö käyttöliittymäelementeille
Frame mainFrame = new Frame();

// Luo HTML-komponentti
Paragraph tutorial = new Paragraph("Opasohjelma!");

// Lisää komponentti säiliöön
mainFrame.add(tutorial);
```

### WebforJ-komponenttien käyttäminen {#webforj-components-and-html-elements}

Vaikka HTML-elementit ovat hyödyllisiä rakenteen, semantiikan ja kevyiden käyttöliittymäkokonaisuuksien suhteen, [webforJ-komponentit](/docs/components/overview) tarjoavat monimutkaisempaa ja dynaamisempaa käyttäytymistä.

Alla oleva koodi lisää [Button](/docs/components/button) -komponentin, muuttaa sen ulkonäköä `setTheme()`-metodilla ja lisää tapahtumakuuntelijan, joka luo [Viesti-dialogin](/docs/components/option-dialogs/message) komponentin, kun painiketta napsautetaan. Useimmat webforJ-komponenttimenetelmät, jotka muokkaavat komponenttia, palauttavat komponentin itsensä, joten voit ketjuttaa useita metodeja tiiviimpää koodia varten.

```java
// Luo säiliö käyttöliittymäelementeille
Frame mainFrame = new Frame();

// Luo webforJ-komponentti
Button btn = new Button("Tietoja");

// Muokkaa webforJ-komponenttia ja lisää tapahtumakuuntelija
btn.setTheme(ButtonTheme.PRIMARY)
  .addClickListener(e -> OptionDialog.showMessageDialog("Tämä on opasohjelma!", "Tietoja"));

// Lisää komponentti säiliöön
mainFrame.add(btn);
```

## CSS:n tyylittely {#styling-with-css}

Useimmilla webforJ-komponenteilla on sisäänrakennetut menetelmät, joilla voidaan tehdä yleisiä tyylimuutoksia, kuten koon ja teeman säätämistä.

```java
// Aseta Kehyksen leveys CSS-avainsanan avulla
mainFrame.setWidth("fit-content");

// Aseta Painikkeen maksimi-leveys pikseleinä
btn.setMaxWidth(200);

// Aseta Painikkeen teema PRIMARY:ksi
btn.setTheme(ButtonTheme.PRIMARY);
```

Näiden menetelmien lisäksi voit tyylittää sovellustasi CSS:llä. **Tyylittely**-osiossa minkä tahansa komponentin dokumentointisivulla on erityisiä tietoja asiaankuuluvista CSS-ominaisuuksista.

WebforJ:ssa on myös suunniteltu CSS-muuttujien sarja, jota kutsutaan DWC-tokeniksi. Katso [Tyylittely](/docs/styling/overview) -dokumentaatiosta yksityiskohtaiset tiedot siitä, miten webforJ-komponentteja tyylitetään, ja miten käyttää näitä tokenia.


### CSS:n lisääminen frontend-pakettiin {#referencing-a-css-file}

On parasta käyttää erillistä CSS-tiedostoa, jotta kaikki pysyy järjestettynä ja ylläpidettävänä. Luo tiedosto nimeltä `card.css` hakemistoon `src/main/frontend/css`, seuraavalla CSS-luokkakuvannolla:

```css title="card.css"
.card {
  display: grid;
  gap: var(--dwc-space-l);
  padding: var(--dwc-space-l);
  margin: var(--dwc-space-l) auto;
  border: thin solid var(--dwc-color-default);
  border-radius: 16px;
  background-color: var(--dwc-surface-3);
  box-shadow: var(--dwc-shadow-xs);
}
```

Lisää sitten tiedosto frontend-pakettiin `Application.java`:sta käyttämällä `@BundleEntry("css/card.css")`. Polku on suhteellinen `src/main/frontend`:iin.

:::tip Frontend-pakettityökalu
Opasprojektin Maven-asetukset käynnistävät webforJ:n frontend-valvojan, kun käynnistät sovelluksen `mvn`:llä, joten muutokset hakemiston `src/main/frontend` alla kootaan uudelleen kehityksen aikana. Lisätietoja löytyy [Frontend-pakettityökalusta](/docs/managing-resources/bundler/overview).
:::

### CSS-luokkien lisääminen komponentteihin {#adding-css-classes-to-components}

Voit dynaamisesti lisätä tai poistaa luokkien nimiä komponentteihin käyttämällä `addClassName()` ja `removeClassName()` -menetelmiä. Tässä oppaassa käytetään vain yhtä CSS-luokkaa:

```java
mainFrame.addClassName("card");
```

## Valmis `Application` {#completed-application}

`Application`-luokkasi pitäisi nyt näyttää seuraavan kaltaiselta:

```java title="Application.java"
@SpringBootApplication
@BundleEntry("css/card.css")
@AppTheme("system")
@AppProfile(name = "Asiakassovellus", shortName = "CustomerApp")
public class Application extends App {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  @Override
  public void run() throws WebforjException {
    Frame mainFrame = new Frame();
    Paragraph tutorial = new Paragraph("Opasohjelma!");
    Button btn = new Button("Tietoja");

    btn
      .setTheme(ButtonTheme.PRIMARY)
      .setMaxWidth(200)
      .addClickListener(e ->
        OptionDialog.showMessageDialog("Tämä on opasohjelma!", "Tietoja")
      );

    mainFrame.setWidth("fit-content").addClassName("card").add(tutorial, btn);
  }
}
```

:::tip Useita sivuja
Monimutkaisemmassa sovelluksessa voit jakaa käyttöliittymän useisiin sivuihin parempaa järjestelyä varten. Tätä käsitellään myöhemmin tässä oppaassa [Reititys ja koostekomponentit](/docs/introduction/tutorial/routing-and-composites).
:::

## Seuraava vaihe {#next-step}

Kun olet luonut toimivan sovelluksen, jossa on peruskäyttöliittymä, seuraava vaihe on lisätä tietomalli ja näyttää tulokset `Table`-komponentissa [Työskentely datan kanssa](/docs/introduction/tutorial/working-with-data).
