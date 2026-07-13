---
title: Creating a Basic App
sidebar_position: 2
description: Step 1 - Add components to an app.
_i18n_hash: 7c98bf3851e1db10d5e0dd68045ea22d
---
In [Projektin asetukset](/docs/introduction/tutorial/project-setup) loit webforJ-projektin. Nyt on aika luoda projektin pääluokka ja lisätä vuorovaikutteinen käyttöliittymä webforJ-komponenttien avulla. Tässä vaiheessa opit:

- Sovellusten pääsisäänkäynnistä, jotka käyttävät webforJ:ta ja Spring Bootia
- webforJ- ja HTML-elementtikomponenteista
- CSS:n käytöstä komponenttien muotoilemiseksi

Tämän vaiheen suorittaminen luo version [1-luodaan-perussovellus] (https://github.com/webforj/webforj-tutorial/tree/main/1-creating-a-basic-app).

<!-- Insert video here -->

## Sovelluksen suorittaminen {#running-the-app}

Kun kehität sovellustasi, voit käyttää [1-luodaan-perussovellus] (https://github.com/webforj/webforj-tutorial/tree/main/1-creating-a-basic-app) vertailukohtana. Näet sovelluksen toiminnassa:

1. Siirry ykköstason hakemistoon, joka sisältää `pom.xml`-tiedoston, tämä on `1-luodaan-perussovellus`, jos seuraat GitHubin versiota.

2. Käytä seuraavaa Maven-komentoa suorittaaksesi Spring Boot -sovelluksen paikallisesti:
    ```bash
    mvn
    ```

Sovelluksen suorittaminen avaa automaattisesti uuden selaimen osoitteessa `http://localhost:8080`.

## Pääsääntö {#entry-point}

Jokaisessa webforJ-sovelluksessa on yksi luokka, joka laajentaa <JavadocLink type="foundation" location="com/webforj/App" code='true'>App</JavadocLink>. Tämän oppaan ja muiden julkaistujen webforJ-projektien osalta sitä kutsutaan yleisesti `Application`:iksi. Tämä luokka on paketissa, jonka nimi perustuu käyttämääsi `groupId`:hen [Projektin asetukset](/docs/introduction/tutorial/project-setup):

```
1-luodaan-perussovellus
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

`Application`-luokassa `SpringApplication.run()`-metodi käyttää asetuksia sovelluksen käynnistämiseen. Eri annotaatiot ovat sovelluksen asetuksia varten.

```java title="Application.java"
@SpringBootApplication
@StyleSheet("ws://css/card.css")
@AppTheme("system")
@AppProfile(name = "Asiakkaan sovellus", shortName = "AsiakasApp")
public class Application extends App {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }
}
```

### Annotaatit {#annotations}

[`@SpringBootApplication`](https://docs.spring.io/spring-boot/api/java/org/springframework/boot/autoconfigure/SpringBootApplication.html) on ydannotaatio Spring Bootissa. Asetat tämän annotaation pääluokkaan merkitäksesi sen sovelluksesi aloituspisteeksi.

`@StyleSheet`, `@AppTheme` ja `@AppProfile` ovat vain joitain lukuisista <JavadocLink type="foundation" location="com/webforj/annotation/package-summary">webforJ-annotaatioista</JavadocLink>, joita voit käyttää, kun haluat määrittää asetuksia selvästi.

- **`@StyleSheet`** upottaa CSS-tiedoston verkkosivulle. Lisää tietoja siitä, miten vuorovaikuttaa tietyn CSS-tiedoston kanssa, löytyy myöhemmin kohdasta [Muotoilu CSS:llä](#styling-with-css).

- **`@AppTheme`** hallitsee sovelluksen visuaalista teemaa. Jos se on asetettu `system`, sovellus ottaa automaattisesti käyttäjän suosiman teeman: `light`, `dark` tai `dark-pure`. Tietoa mukautettujen teemojen luomisesta tai oletusteemojen ylikirjoittamisesta löydät [Teemat](/docs/styling/themes) -artikkelista.

- **`@AppProfile`** auttaa määrittämään, miten sovellus esitetään käyttäjälle [asennettavana sovelluksena](/docs/configuration/installable-apps). Vähintään tämä annotaatio tarvitsee `name` sovelluksen koko nimeksi ja `shortName` käytettäväksi, kun tilaa on rajoitetusti. `shortName` ei saisi ylittää 12 merkkiä.

## Käyttöliittymän luominen {#creating-a-ui}

Luodaksesi käyttöliittymäsi, sinun on lisättävä [HTML-elementtikomponentteja](/docs/components/html-elements) ja [webforJ-komponentteja](/docs/components/overview). Tällä hetkellä sinulla on vain yhden sivun sovellus, joten lisäät komponentit suoraan `Application`-luokkaan.
Tätä varten ylikirjoitat `App.run()`-metodin ja luot `Frame`:n, johon lisätään komponentteja.

```java
@Override
public void run() throws WebforjException {
  Frame mainFrame = new Frame();

  // Luo käyttöliittymäkomponentit ja lisää ne kehykseen

}
```

### HTML-elementtien käyttö {#using-html-elements}

Voit lisätä standardeja HTML-elementtejä sovellukseesi [HTML-elementtikomponenttien](/docs/components/html-elements) avulla.
Luo uusi instanssi komponentista ja käytä `add()`-metodia lisätäksesi se `Frame`:en:

```java
// Luo säiliö käyttöliittymäelementeille
Frame mainFrame = new Frame();

// Luo HTML-komponentti
Paragraph tutorial = new Paragraph("Oppimisohjelma!");

// Lisää komponentti säiliöön
mainFrame.add(tutorial);
```

### webforJ-komponenttien käyttö {#webforj-components-and-html-elements}

Vaikka HTML-elementit ovat hyödyllisiä rakenteessa, semantiikassa ja kevyissä käyttöliittymätarpeissa, [webforJ-komponentit](/docs/components/overview) tarjoavat monimutkaisempaa ja dynaamisempaa käyttäytymistä.

Alla oleva koodi lisää [Button](/docs/components/button) -komponentin, muuttaa sen ulkoasua `setTheme()`-metodilla ja lisää tapahtumakuuntelijan luodakseen [Message Dialog](/docs/components/option-dialogs/message) -komponentin, kun painiketta napsautetaan. Suurin osa webforJ-komponenttien metodeista, jotka muokkaavat komponenttia, palauttaa itse komponentin, joten voit ketjuttaa useita metodeja tiiviimpää koodia varten.

```java
// Luo säiliö käyttöliittymäelementeille
Frame mainFrame = new Frame();

// Luo webforJ-komponentti
Button btn = new Button("Info");

// Muokkaa webforJ-komponenttia ja lisää tapahtumakuuntelija
btn.setTheme(ButtonTheme.PRIMARY)
  .addClickListener(e -> OptionDialog.showMessageDialog("Tämä on oppimisohjelma!", "Info"));

// Lisää komponentti säiliöön
mainFrame.add(btn);
```

## Muotoilu CSS:llä {#styling-with-css}

Useimmissa webforJ-komponenteissa on sisäänrakennettuja metodeja yleisten tyylimuutosten, kuten koon ja teeman, tekemiseen.

```java
// Aseta kehykseen leveys CSS-avaimen avulla
mainFrame.setWidth("fit-content");

// Aseta painikkeen maksimi leveys pikseleinä
btn.setMaxWidth(200);

// Aseta painikkeen teema PRIMARY:ksi
btn.setTheme(ButtonTheme.PRIMARY);
```

Näiden metodien lisäksi voit muotoilla sovellustasi käyttämällä CSS:ää. Jokaisen komponentin dokumentointisivun **Muotoilu**-osiossa on erityisiä tietoja asiaankuuluvista CSS-ominaisuuksista.

webforJ:lla on myös joukko suunniteltuja CSS-muuttujia, joita kutsutaan DWC-tokeneiksi. Katso [Muotoilu](/docs/styling/overview) -dokumentaatio tarkkoja tietoja webforJ-komponenttien muotoilusta ja tokenien käytöstä.

### CSS-tiedoston viittaaminen {#referencing-a-css-file}

On parasta pitää erillinen CSS-tiedosto, jotta kaikki pysyy järjestyksessä ja helposti ylläpidettävissä. Luo tiedosto nimeltä `card.css` hakemistoon `src/main/resources/static/css`, jossa on seuraava CSS-luokkakuvasto:

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

Tämän jälkeen viittaa tiedostoon `Application.java` käyttämällä `@StyleSheet`-annotaatiota CSS-tiedoston nimen kanssa. Tässä vaiheessa se on `@StyleSheet("ws://css/card.css")`.

:::tip Verkkopalvelinprotokolla
Tässä oppaassa käytetään verkkopalvelinprotokollaa CSS-tiedoston viittaamiseen. Lisätietoja siitä, miten tämä toimii, katso [Resurssien hallinta](/docs/managing-resources/overview).
:::

### CSS-luokkien lisääminen komponentteihin {#adding-css-classes-to-components}

Voit dynaamisesti lisätä tai poistaa luokkien nimiä komponentteihin käyttämällä `addClassName()` ja `removeClassName()` -metodeja. Tässä oppaassa käytetään vain yhtä CSS-luokkaa:

```java
mainFrame.addClassName("card");
```

## Valmis `Application` {#completed-application}

`Application`-luokkasi pitäisi nyt näyttää seuraavan kaltaiselta:

```java title="Application.java"
@SpringBootApplication
@StyleSheet("ws://css/card.css")
@AppTheme("system")
@AppProfile(name = "Asiakkaan sovellus", shortName = "AsiakasApp")
public class Application extends App {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  @Override
  public void run() throws WebforjException {
    Frame mainFrame = new Frame();
    Paragraph tutorial = new Paragraph("Oppimisohjelma!");
    Button btn = new Button("Info");

    btn.setTheme(ButtonTheme.PRIMARY)
        .setMaxWidth(200)
        .addClickListener(e -> OptionDialog.showMessageDialog("Tämä on oppimisohjelma!", "Info"));

    mainFrame.setWidth("fit-content")
        .addClassName("card")
        .add(tutorial, btn);
  }

}
```

:::tip Useita sivuja
Monimutkaisemmassa sovelluksessa voit jakaa käyttöliittymän useisiin sivuihin paremman järjestelyn vuoksi. Tätä käsitellään myöhemmin tässä oppaassa kohdassa [Reititys ja yhdistelmät](/docs/introduction/tutorial/routing-and-composites).
:::

## Seuraava vaihe {#next-step}

Kun olet luonut toimivan sovelluksen, jossa on peruskäyttöliittymä, seuraava vaihe on lisätä tietomalli ja esittää tulokset `Table`-komponentissa kohdassa [Työskentely datan kanssa](/docs/introduction/tutorial/working-with-data).
