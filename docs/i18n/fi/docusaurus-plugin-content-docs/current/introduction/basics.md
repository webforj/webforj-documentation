---
title: App Basics
sidebar_position: 3
_i18n_hash: ad73702df52f27ebff7e226bb75e3a6a
---
Once webforJ ja sen riippuvuudet on asetettu projektiisi, olet valmis luomaan sovelluksen rakenteen. Tämä artikkeli käy läpi keskeisiä elementtejä perus webforJ-sovelluksessa, erityisesti keskittyen `Application`- ja `HomeView`-luokkiin, jotka ovat perusluokkia `webforj-archetype-hello-world`-starter-projektissa.

## Pääsovellusluokka: `Application.java` {#main-app-class-applicationjava}

`Application`-luokka toimii entry pointina webforJ-sovelluksellesi, asettaen olennaiset asetukset ja reitit. Aluksi, huomaa luokan julkistus ja anotoinnit.

Tämä luokka perii ydin `App`-luokan webforJ:sta, tehden siitä tunnistettavan webforJ-sovelluksena. Erilaiset anotoinnit määrittävät sovelluksen teeman, otsikon ja reitityksen.

```java
@Routify(packages = "com.samples.views")
@AppTitle("webforJ Hello World")
@StyleSheet("ws://app.css")
public class Application extends App {
}
```

- `@Routify`: Määrittää, että webforJ:n tulee skannata `com.samples.views`-pakettia reittikomponentteja varten.
- `@AppTitle`: Määrittää otsikon, joka näkyy sovelluksen selainvälilehdellä.
- `@StyleSheet`: Liittää ulkoisen CSS-tiedoston, `app.css`, mahdollistaen mukautettujen tyylien soveltamisen sovellukseen.

`Application`-luokka ei sisällä muita metodeja, koska asetukset on tehty annotaatioiden kautta, ja webforJ hoitaa sovelluksen alustusprosessin.

Kun `Application.java` on asetettu, sovellus on nyt konfiguroitu otsikolla ja reiteillä, jotka osoittavat näkymäpakkiin. Seuraavaksi `HomeView`-luokan yleiskatsaus antaa käsityksen siitä, mitä näytetään, kun sovellus ajetaan.

### Sovelluksen löytaminen {#discovering-an-app}

Yksi <JavadocLink type="foundation" location="com/webforj/App" code='true'>App</JavadocLink> -rajoitus on voimassa webforJ:ssä, mikä siirtää kaikki virheiden käsittelyvastuut Java-puolelle ja antaa kehittäjille täyden hallinnan virheiden hallintaan.

WebforJ:n käynnistysohjelmassa kaikki luokat, jotka perivät <JavadocLink type="foundation" location="com/webforj/App" code='true'>com.webforj.App</JavadocLink>, skannataan. Jos useita sovelluksia löytyy, järjestelmä etsii <JavadocLink type="foundation" location="com/webforj/annotation/AppEntry" code='true'>com.webforj.annotation.AppEntry</JavadocLink> -annotaatiota. Jos jokin löydetyistä luokista on merkitty <JavadocLink type="foundation" location="com/webforj/annotation/AppEntry" code='true'>@AppEntry</JavadocLink>-annotaatiolla, ensimmäinen kohdattu klas will will be considered the entry point.

- Jos luokka on merkitty `@AppEntry`, se luokka valitaan entry pointiksi.
- Jos useat luokat on merkitty `@AppEntry`, poikkeus heitetään, ja luetellaan kaikki löydetyt luokat.
- Jos kukaan luokista ei ole merkitty ja vain yksi App:n aliluokka on löydetty, tämä luokka valitaan entry pointiksi.
- Jos kukaan luokista ei ole merkitty ja useita App:n aliluokkia on löydetty, poikkeus heitetään, jossa yksityiskohtaisesti kerrotaan jokaisesta aliluokasta.

:::tip Virheiden käsittely
Lisätietoja virheiden käsittelystä webforJ:ssä, katso [tämä artikkeli](../advanced/error-handling).
:::

## Päänäkymäluokka: `HomeView.java` {#main-view-class-homeviewjava}

`HomeView`-luokka määrittelee yksinkertaisen näkymäkomponentin, joka toimii sovelluksen etusivuna. Se näyttää kentän ja painikkeen, joka tervehtii käyttäjän kirjoittamaa nimeä.

### Luokan julkistus ja anotoinnit {#class-declaration-and-annotations}

`HomeView` perii `Composite<FlexLayout>`, joka mahdollistaa sen toimimisen uudelleenkäytettävänä komponenttina, joka koostuu [`FlexLayout`](../components/flex-layout) -komponentista. [`@Route("/")`](../routing/overview) tekee tästä sovelluksen juurireitin.

```java
@Route("/")
public class HelloWorldView extends Composite<FlexLayout> {

  private FlexLayout self = getBoundComponent();
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

### Komponentin alustus {#component-initialization}

Luokan sisällä useita käyttöliittymäelementtejä alustetaan ja määritellään:

```java
private FlexLayout self = getBoundComponent();
private TextField hello = new TextField("Mikä on nimesi?");
private Button btn = new Button("Tervehdi");
```

- `self`: Pääasiallinen asettelu komponentti, joka käyttää [`FlexLayout`](../components/flex-layout), ja on määritetty elementtien säilöksi. Tämä elementti käyttää `getBoundComponent()` -menetelmää säilyttääkseen pääasiallisen `FlexLayout`:n, jota luokka sisältää.
- `hello`: [`TextField`](../components/fields/textfield), jonka etiketti on `Mikä on nimesi?`, käyttäjien nimien syöttämiseksi.
- `btn`: Primaarityylillä varustettu [`Button`](../components/button), jonka etiketti on `Tervehdi`.

### Asettelun konfigurointi {#layout-configuration}

Asettelua `(self)` määritellään muutamilla keskeisillä tyyliparametreilla:

- `FlexDirection.COLUMN` pinotaan elementit pystysuorasti.
- `setMaxWidth(300)` rajoittaa leveyden 300 pikseliin tiiviimmän ulkoasun saamiseksi.
- `setStyle("margin", "1em auto")` keskittää asettelun marginaalilla sen ympäri.

### Komponenttien lisääminen asetteluun {#adding-components-to-the-layout}
Lopuksi, hello-tekstikenttä ja btn-painike lisätään [`FlexLayout`](../components/flex-layout) -säilöön kutsumalla `self.add(hello, btn)`. Tämä järjestely määrittelee näkymän rakenteen, tehden lomakkeesta sekä vuorovaikutteisen että visuaalisesti keskittyneen.

## Sovelluksen tyylitys {#styling-the-app}

`styles.css` -tiedosto tarjoaa mukautettuja tyylejä webforJ-sovelluksellesi. Tämä CSS-tiedosto viitataan `Application`-luokassa käyttäen [`@StyleSheet`](../managing-resources/importing-assets#importing-css-files) annotaatiota, mikä mahdollistaa sovelluksen sovellusten tyyliin soveltuvien tyylien soveltamisen.

Tämä tiedosto sijaitsee projektin `resources/static`-hakemistossa, ja sen voi viitata verkkopalvelimen URL-osoitteen `ws://app.css` avulla.
