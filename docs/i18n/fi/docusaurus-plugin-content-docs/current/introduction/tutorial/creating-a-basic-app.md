---
title: Creating a Basic App
sidebar_position: 2
description: Step 1 - Add components to an app.
_i18n_hash: ac74bc5c04bce477a7407c9ff94323a4
---
In [Projektin asetukset](/docs/introduction/tutorial/project-setup), loit webforJ-projektin. Nyt on aika luoda projektin pääluokka ja lisätä interaktiivinen käyttöliittymä webforJ-komponenttien avulla. Tässä vaiheessa opit:

- Sovellusten sisäänkäyntipiste webforJ:n ja Spring Bootin avulla
- webforJ- ja HTML-elementtikomponentit
- CSS:n käyttäminen komponenttien tyylittämiseen

Tämän vaiheen suorittaminen luo version [1-creating-a-basic-app](https://github.com/webforj/webforj-tutorial/tree/main/1-creating-a-basic-app).

<!-- Insert video here -->

## Sovelluksen ajaminen {#running-the-app}

Sovellustasi kehittäessäsi voit käyttää [1-creating-a-basic-app](https://github.com/webforj/webforj-tutorial/tree/main/1-creating-a-basic-app) vertailukohtana. Näet sovelluksen toiminnassa:

1. Siirry pääkansioon, joka sisältää `pom.xml` tiedoston, tämä on `1-creating-a-basic-app`, jos seuraat mukana GitHubin versiota.

2. Käytä seuraavaa Maven komentoa ajaaksesi Spring Boot sovellusta paikallisesti:
    ```bash
    mvn
    ```

Sovelluksen ajaminen avaa automaattisesti uuden selaimen osoitteessa `http://localhost:8080`.

## Sisäänkäyntipiste {#entry-point}

Jokaisessa webforJ-sovelluksessa on yksi luokka, joka laajentaa <JavadocLink type="foundation" location="com/webforj/App" code='true'>App</JavadocLink>. Tätä tutoriaalia ja muita julkaistuja webforJ-projekteja varten se on yleisesti nimeltään `Application`. Tämä luokka sijaitsee paketissa, joka on nimetty käyttämäsi `groupId`:n mukaan, jonka käytit [Projektin asetuksissa](/docs/introduction/tutorial/project-setup):

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

`Application`-luokan sisällä `SpringApplication.run()`-metodi käyttää konfiguraatioita sovelluksen käynnistämiseen. Erilaiset annotaatiot ovat sovelluksen konfiguraatioita varten.

```java title="Application.java"
@SpringBootApplication
@StyleSheet("ws://css/card.css")
@AppTheme("system")
@AppProfile(name = "Asiakassovellus", shortName = "Asiakassovellus")
public class Application extends App {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }
}
```

### Annotaatit {#annotations}

[`@SpringBootApplication`](https://docs.spring.io/spring-boot/api/java/org/springframework/boot/autoconfigure/SpringBootApplication.html) on keskeinen annotaatio Spring Bootissa. Laitat tämän annotaation pääluokkaan merkitäksesi sen sovelluksesi aloituspisteeksi.

`@StyleSheet`, `@AppTheme`, ja `@AppProfile` ovat vain muutamia monista <JavadocLink type="foundation" location="com/webforj/annotation/package-summary">webforJ-annotaatioista</JavadocLink>, jotka ovat käytettävissä, kun haluat erikseen asettaa konfiguraatioita.

- **`@StyleSheet`** upottaa CSS-tiedoston verkkosivulle. Lisätietoja siitä, kuinka vuorovaikuttaa tietyn CSS-tiedoston kanssa, on myöhemmin kohdassa [Tyylitettäessä CSS:llä](#styling-with-css).

- **`@AppTheme`** hallitsee sovelluksen visuaalista teemaa. Jos se on asetettu `system`, sovellus omaksuu automaattisesti käyttäjän suosiman teeman: `light`, `dark` tai `dark-pure`. Lisätietoja erilaisten teemojen luomisesta tai oletusteemojen ylikirjoittamisesta löytyy [Teemat](/docs/styling/themes) artikkelista.

- **`@AppProfile`** auttaa konfiguroimaan, miten sovellus esitetään käyttäjälle [asennettavana sovelluksena](/docs/configuration/installable-apps). Vähimmäisvaatimuksena tällä annotaatiolla on `name` sovelluksen täydelle nimelle ja `shortName` käytettäväksi, kun tilaa on rajallisesti. `shortName` ei saisi ylittää 12 merkkiä.  

## Käyttöliittymän luominen {#creating-a-ui}

Luodaksesi käyttöliittymäsi, sinun täytyy lisätä [HTML-elementtikomponentteja](/docs/components/html-elements) ja [webforJ-komponentteja](/docs/components/overview). Tällä hetkellä sinulla on vain yksisivuinen sovellus, joten lisäät komponentit suoraan `Application`-luokkaan. 
Tätä varten ylitse `App.run()`-metodin ja luo `Frame`, johon voit lisätä komponentteja. 

```java
@Override
public void run() throws WebforjException {
  Frame mainFrame = new Frame();

  // Luo käyttöliittymän komponentit ja lisää ne kehykseen

}
```

### HTML-elementtien käyttäminen {#using-html-elements}

Voit lisätä standardeja HTML-elementtejä sovellukseesi [HTML-elementtikomponenttien](/docs/components/html-elements) avulla.
Luo uusi komponenttitapaus, ja käytä `add()`-metodia lisätäksesi sen `Frameen`:

```java
// Luo säiliö käyttöliittymän elementeille
Frame mainFrame = new Frame();

// Luo HTML-komponentti
Paragraph tutorial = new Paragraph("Tutoriaalisovellus!");

// Lisää komponentti säiliöön
mainFrame.add(tutorial);
```

### webforJ-komponenttien käyttäminen {#webforj-components-and-html-elements}

Vaikka HTML-elementit ovat hyödyllisiä rakenteen, semantiikan ja kevyiden käyttöliittymävaatimusten kohdalla, [webforJ-komponentit](/docs/components/overview) tarjoavat monimutkaisempaa ja dynaamisempaa käyttäytymistä.

Alla oleva koodi lisää [Button](/docs/components/button) komponentin, muuttaa sen ulkonäköä `setTheme()`-metodilla, ja lisää tapahtumakuuntelijan luodakseen [Message Dialog](/docs/components/option-dialogs/message) komponentin, kun painiketta napsautetaan.
Useimmat webforJ-komponenttien metodit, jotka muokkaavat komponenttia, palauttavat itse komponentin, joten voit ketjuttaa useita metodeja tiiviimpää koodia varten.

```java
// Luo säiliö käyttöliittymän elementeille
Frame mainFrame = new Frame();

// Luo webforJ-komponentti
Button btn = new Button("Info");

// Muokkaa webforJ-komponenttia ja lisää tapahtumakuuntelija
btn.setTheme(ButtonTheme.PRIMARY)
  .addClickListener(e -> OptionDialog.showMessageDialog("Tämä on tutoriaali!", "Info"));

// Lisää komponentti säiliöön
mainFrame.add(btn);
```

## Tyylitys CSS:llä {#styling-with-css}

Useimmilla webforJ-komponenteilla on sisäänrakennettuja metodeja yleisten tyylimuutosten, kuten koon ja teeman, tekemiseen.

```java
// Aseta kehyksemittakaava CSS-avainsanan avulla
mainFrame.setWidth("fit-content");

// Aseta painikkeen maksimi leveys pikseleinä
btn.setMaxWidth(200);

// Aseta painikkeen teema PRIMARYksi
btn.setTheme(ButtonTheme.PRIMARY);
```

Nämä menetelmät lisäksi voit tyylittää sovellustasi CSS:llä. **Tyylittely**-osiossa minkä tahansa komponentin asiakirjasivulla on tarkkoja tietoja soveltuvista CSS-ominaisuuksista.

webforJ:llä on myös joukko suunniteltuja CSS-muuttujia, joita kutsutaan DWC-tokeneiksi. Katso [Tyylitys](/docs/styling/overview) asiakirjasta saadaksesi tarkkaa tietoa siitä, kuinka tyylittää webforJ-komponentteja ja kuinka käyttää tokeneja.

### CSS-tiedoston viittaaminen {#referencing-a-css-file} 

On parasta omistaa erillinen CSS-tiedosto, jotta kaikki pysyy järjestyksessä ja helposti ylläpidettävänä. Luo tiedosto nimeltä `card.css` sisään `src/main/resources/static/css`, seuraavalla CSS-luokkadefiniitiolla:

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

Viittaa sitten tiedostoon `Application.java` käyttämällä `@StyleSheet` annotaatiota CSS-tiedoston nimellä. Tällä vaiheella se on `@StyleSheet("ws://css/card.css")`.

:::tip Verkkopalvelin protokolla
Tässä tutoriaalissa käytetään verkkopalvelinprotokollaa CSS-tiedoston viittaamiseen. Lisätietoja siitä, kuinka tämä toimii, katso [Resurssien hallinta](/docs/managing-resources/overview).
:::

### CSS-luokkien lisääminen komponentteihin {#adding-css-classes-to-components}

Voit dynaamisesti lisätä tai poistaa luokkien nimiä komponentteihin käyttämällä `addClassName()` ja `removeClassName()` menetelmiä. Tässä tutoriaalissa käytetään vain yhtä CSS-luokkaa:

```java
mainFrame.addClassName("card");
```

## Valmis `Application` {#completed-application}

`Application`-luokkasi tulisi nyt näyttää seuraavalta:

```java title="Application.java"
@SpringBootApplication
@StyleSheet("ws://css/card.css")
@AppTheme("system")
@AppProfile(name = "Asiakassovellus", shortName = "Asiakassovellus")
public class Application extends App {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  @Override
  public void run() throws WebforjException {
    Frame mainFrame = new Frame();
    Paragraph tutorial = new Paragraph("Tutoriaalisovellus!");
    Button btn = new Button("Info");

    btn.setTheme(ButtonTheme.PRIMARY)
        .setMaxWidth(200)
        .addClickListener(e -> OptionDialog.showMessageDialog("Tämä on tutoriaali!", "Info"));

    mainFrame.setWidth("fit-content")
        .addClassName("card")
        .add(tutorial, btn);
  }

}
```

:::tip Useita sivuja
Monimutkaisemmassa sovelluksessa voit jakaa käyttöliittymän useisiin sivuihin paremman järjestämisen vuoksi. Tämä käsite käsitellään myöhemmin tässä tutoriaalissa kohdassa [Reititys ja yhdistelmät](/docs/introduction/tutorial/routing-and-composites).
:::

## Seuraava vaihe {#next-step}

Kun olet luonut toimivan sovelluksen perustason käyttöliittymällä, seuraava vaihe on lisätä tietomalli ja näyttää tulokset `Table` komponentissa kohdassa [Työskentely datan kanssa](/docs/introduction/tutorial/working-with-data).
