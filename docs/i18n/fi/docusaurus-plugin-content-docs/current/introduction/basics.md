---
title: App Basics
sidebar_position: 3
_i18n_hash: 23f93367391ac7cd42c28bf4cd3640ee
---
Kun webforJ ja sen riippuvuudet on asetettu projektiisi, olet valmis luomaan sovelluksen rakenteen. Tämä artikkeli käy läpi keskeiset elementit yksinkertaisessa webforJ-sovelluksessa, erityisesti keskittyen `Application`- ja `HomeView`-luokkiin, jotka ovat perustavanlaatuisia luokkia `webforj-archetype-hello-world` aloitusprojektissa.

## Pääsovellusluokka: `Application.java` {#main-app-class-applicationjava}

`Application`-luokka toimii sovelluksesi webforJ-sovelluksen sisäänkäyntipisteenä, asettaen olennaiset asetukset ja reitit. Huomaa aluksi luokan julistus ja annotaatiot.

Tämä luokka laajentaa webforJ:n ydin `App`-luokkaa, jolloin se tunnetaan webforJ-sovelluksena. Eri annotaatiot määrittävät sovelluksen teeman, otsikon ja reitit.

```java
@Routify(packages = "com.samples.views")
@AppTitle("webforJ Hello World")
@StyleSheet("ws://app.css")
public class Application extends App {
}
```

- `@Routify`: Määrittää, että webforJ:n tulee skannata `com.samples.views`-paketti reittikomponenttien löytämiseksi.
- `@AppTitle`: Määrittää otsikon, joka näkyy sovelluksen selainvälilehdellä.
- `@StyleSheet`: Linkittää ulkoisen CSS-tiedoston, `app.css`, jolloin sovellukselle voidaan soveltaa mukautettua tyylitystä.

`Application`-luokka ei sisällä lisämenetelmiä, koska asetukset on määritetty annotaatioiden avulla, ja webforJ hoitaa sovelluksen alustusprosessin.

Kun `Application.java` on asetettu, sovellus on nyt konfiguroitu otsikolla ja reiteillä, jotka osoittavat näkymäpakettiin. Seuraavaksi `HomeView`-luokan yleiskuva antaa käsityksen siitä, mitä näytetään, kun sovellus käynnistetään.

### Sovelluksen löytämistä {#discovering-an-app}

Yksi <JavadocLink type="foundation" location="com/webforj/App" code='true'>App</JavadocLink> -rajoitus on voimassa webforJ:ssä, mikä siirtää kaikki virheiden käsittelyvastuut Java-puolelle ja antaa kehittäjille täyden hallinnan virheiden hallintaan.

WebforJ:n käynnistysprosessin aikana kaikki luokat, jotka laajentavat <JavadocLink type="foundation" location="com/webforj/App" code='true'>com.webforj.App</JavadocLink>, skannataan. Jos useita sovelluksia löytyy, järjestelmä etsii <JavadocLink type="foundation" location="com/webforj/annotation/AppEntry" code='true'>com.webforj.annotation.AppEntry</JavadocLink> -annotaatiota. Jos jokin löydetyistä luokista on merkitty <JavadocLink type="foundation" location="com/webforj/annotation/AppEntry" code='true' >@AppEntry</JavadocLink> -annotaatiolla, ensimmäinen kohdattu luokka katsotaan sisäänkäyntipisteeksi.

- Jos luokka on merkitty `@AppEntry`, tätä luokkaa käytetään sisäänkäyntipisteenä.
- Jos useita luokkia on merkitty `@AppEntry`, poikkeus heitetään, jossa luetellaan kaikki löydetyt luokat.
- Jos yhtään luokkaa ei ole merkitty ja vain yksi `App`-alaluokka löytyy, tätä luokkaa käytetään sisäänkäyntipisteenä.
- Jos yhtään luokkaa ei ole merkitty ja useita `App`-alaluokkia on löydetty, poikkeus heitetään, jossa kerrotaan jokaisesta alaluokasta.

:::tip Virheiden käsittely
Lisätietoja virheiden käsittelystä webforJ:ssä, katso [tämä artikkeli](../advanced/error-handling).
:::

## Päänäkymäluokka: `HomeView.java` {#main-view-class-homeviewjava}

`HomeView`-luokka määrittelee yksinkertaisen näkymäkomponentin, joka toimii sovelluksen etusivuna. Se näyttää kentän ja painikkeen, joka tervehtii käyttäjän kirjoittamaa nimeä.

### Luokan julistus ja annotaatiot {#class-declaration-and-annotations}

`HomeView` laajentaa `Composite<FlexLayout>`, mikä mahdollistaa sen toimimisen uudelleenkäytettävänä komponenttina, joka koostuu [`FlexLayout`](../components/flex-layout) -komponentista. [`@Route("/")`](../routing/overview) tekee tästä sovelluksen juurereitin.

```java
@Route("/")
public class HelloWorldView extends Composite<FlexLayout> {

  private final FlexLayout self = getBoundComponent();
  private TextField hello = new TextField("Mikä on nimesi?");
  private Button btn = new Button("Tervehdi");

  public HelloWorldView(){
    self.setDirection(FlexDirection.COLUMN);
    self.setMaxWidth(300);
    self.setStyle("margin", "1em auto");

    btn.setTheme(ButtonTheme.PRIMARY)
        .addClickListener(e -> 
          Toast.show("Tervetuloa webforJ Starter " + hello.getValue() + "!", Theme.GRAY));

    self.add(hello, btn);
  }
}
```

### Komponentin alustaminen {#component-initialization}

Luokan sisällä useita käyttöliittymäelementtejä alustetaan ja julistetaan:

```java
private final FlexLayout self = getBoundComponent();
private TextField hello = new TextField("Mikä on nimesi?");
private Button btn = new Button("Tervehdi");
```

- `self`: Pääasiallinen asettelu komponentti, joka käyttää [`FlexLayout`](../components/flex-layout), konfiguroitu elementtien säilöksi. Tämä elementti käyttää `getBoundComponent()`-menetelmää tallentaakseen pääasiallisen `FlexLayoutin`, jonka luokka sisältää.
- `hello`: [`TextField`](../components/fields/textfield), jossa on etiketti `Mikä on nimesi?`, jonka avulla käyttäjät voivat syöttää nimensä.
- `btn`: Päästyylillä varustettu [`Button`](../components/button), jonka etiketti on `Tervehdi`.

### Asettelun konfigurointi {#layout-configuration}

Asettelu `(self)` on konfiguroitu muutamalla avaintyylillä:

- `FlexDirection.COLUMN` pinnee elementit pystysuoraan.
- `setMaxWidth(300)` rajoittaa leveyden 300 pikseliin kompaktin asettelun saavuttamiseksi.
- `setStyle("margin", "1em auto")` keskittää asettelun marginaalilla ympärillään.

### Komponenttien lisääminen asetteluun {#adding-components-to-the-layout}
Lopuksi hello tekstikenttä ja btn-painike lisätään [`FlexLayout`](../components/flex-layout) -säiliöön kutsumalla `self.add(hello, btn)`. Tämä järjestely määrittelee näkymän rakenteen, mikä tekee lomakkeesta sekä interaktiivisen että visuaalisesti keskittyneen.

## Sovelluksen tyylittäminen {#styling-the-app}

`styles.css`-tiedosto tarjoaa mukautettua tyylitystä webforJ-sovelluksellesi. Tämä CSS-tiedosto viitataan Application-luokassa käyttäen [`@StyleSheet`](../managing-resources/importing-assets#importing-css-files) -annotaatiota, joka sallii sovelluksen soveltaa tyylejä sovelluksen komponentteihin.

Tämä tiedosto sijaitsee projektin `resources/static`-hakemistossa, ja sitä voidaan viitata verkkopalvelimen URL-osoitteella `ws://app.css`.
