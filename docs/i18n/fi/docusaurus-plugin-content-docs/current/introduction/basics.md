---
title: App Basics
description: >-
  Walk through the Application and HomeView classes of the hello-world archetype
  to see how routing, annotations, and views shape a webforJ app.
sidebar_position: 3
_i18n_hash: 2ebddfe300802013e4376681bc2ccf04
---
Kun webforJ ja sen riippuvuudet on määritetty projektissasi, olet valmis luomaan sovellusrakenteen. Tämä artikkeli käy läpi keskeiset elementit yksinkertaisessa webforJ-sovelluksessa, erityisesti keskittyen `Application`- ja `HomeView`-luokkiin, jotka ovat perustavia luokkia `webforj-archetype-hello-world`-aloitushankkeessa.

## Pään sovellusluokka: `Application.java` {#main-app-class-applicationjava}

`Application`-luokka toimii pääsisäänkäyntinä webforJ-sovelluksellesi, määrittäen olennaiset asetukset ja reitit. Aloita huomaamalla luokan julistus ja annotaatiot.

Tämä luokka laajentaa webforJ:n ydin `App`-luokkaa, mikä tekee siitä tunnistettavan webforJ-sovelluksena. Erilaiset annotaatiot määrittävät sovelluksen teeman, otsikon ja reitit.

```java
@Routify(packages = "com.samples.views")
@AppTitle("webforJ Hello World")
@StyleSheet("ws://app.css")
public class Application extends App {
}
```

- `@Routify`: Määrittää, että webforJ:n tulee skannata `com.samples.views` -paketti reittikomponenteille.
- `@AppTitle`: Määrittää otsikon, joka näytetään sovelluksen selainvälilehdellä.
- `@StyleSheet`: Linkittää ulkoisen CSS-tiedoston, `app.css`, joka mahdollistaa mukautetut tyylit sovellukselle.

`Application`-luokka ei sisällä muita metodeja, koska asetukset määritetään annotaatioiden kautta, ja webforJ hoitaa sovelluksen aloituksen.

Kun `Application.java` on määritetty, sovellus on nyt konfiguroitu otsikolla ja reiteillä, jotka osoittavat näkymäpakkaukseen. Seuraavaksi `HomeView`-luokan yleiskatsaus antaa käsityksen siitä, mitä näytetään, kun sovellus käynnistetään.

### Löydetään `App` {#discovering-an-app}

webforJ:ssä pakollinen <JavadocLink type="foundation" location="com/webforj/App" code='true'>App</JavadocLink>-rajaus toteutuu, mikä siirtää kaikki virheiden käsittelyvastuut Java-puolelle ja antaa kehittäjille täyden hallinnan virheiden hallintaan.

WebforJ:n käynnistysprosessin aikana skannataan kaikki luokat, jotka laajentavat <JavadocLink type="foundation" location="com/webforj/App" code='true'>com.webforj.App</JavadocLink>. Jos useita sovelluksia löytyy, järjestelmä etsii <JavadocLink type="foundation" location="com/webforj/annotation/AppEntry" code='true'>com.webforj.annotation.AppEntry</JavadocLink> -annotaatiota. Jos jokin löytyneistä luokista on merkitty <JavadocLink type="foundation" location="com/webforj/annotation/AppEntry" code='true'>@AppEntry</JavadocLink> -annotaatiolla, ensimmäinen, jota kohdattiin, katsotaan pääsisäänkäynniksi.

- Jos luokka on merkitty `@AppEntry`, kyseistä luokkaa käytetään pääsisäänkäyntinä.
- Jos useat luokat ovat merkitty `@AppEntry`, poikkeus heitetään, ja listataan kaikki löydetyt luokat.
- Jos mikään luokka ei ole merkitty ja vain yksi `App`-aliluokka löytyy, tätä luokkaa käytetään pääsisäänkäyntinä.
- Jos mikään luokka ei ole merkitty ja useita `App`-aliluokkia löytyy, poikkeus heitetään, jossa eritellään jokainen aliluokka.

:::tip Virheiden käsittely
Lisätietoja siitä, miten virheitä käsitellään webforJ:ssä, katso [tämä artikkeli](../advanced/error-handling).
:::

## Pään näkymäluokka: `HomeView.java` {#main-view-class-homeviewjava}

`HomeView`-luokka määrittelee yksinkertaisen näkymäkomponentin, joka toimii sovelluksen etusivuna. Se näyttää kentän ja painikkeen, joka tervehtii käyttäjän syöttämää nimeä.

### Luokan julistus ja annotaatiot {#class-declaration-and-annotations}

`HomeView` laajentaa `Composite<FlexLayout>`, mikä mahdollistaa sen toimimisen uudelleenkäytettävänä komponenttina, joka koostuu [`FlexLayout`](../components/flex-layout) -komponentista. [`@Route("/")`](../routing/overview) tekee tästä sovelluksen juurireitin.

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

- `self`: Pääasiallinen asettelu, joka käyttää [`FlexLayout`](../components/flex-layout) -komponenttia, määritetty elementtien säilöksi. Tämä elementti käyttää `getBoundComponent()`-metodia tallentaakseen pääasiallisen `FlexLayout`-luokan.
- `hello`: [`TextField`](../components/fields/textfield) nimeltään `Mikä on nimesi?` käyttäjien nimensä syöttämistä varten.
- `btn`: Päätyylinen [`Button`](../components/button) nimeltään `Tervehdi`.

### Asettelu ja konfigurointi {#layout-configuration}

Asettelu (self) konfiguroidaan muutamilla keskeisillä tyyliparametreilla:

- `FlexDirection.COLUMN` pinotaan elementit pystysuoraan.
- `setMaxWidth(300)` rajoittaa leveyden 300 pikseliin kompaktin asettelun saavuttamiseksi.
- `setStyle("margin", "1em auto")` keskittää asettelun marginaalilla sen ympärillä.

### Komponenttien lisääminen asetteluun {#adding-components-to-the-layout}
Lopuksi hello-tekstikenttä ja btn-painike lisätään [`FlexLayout`](../components/flex-layout) -säilöön kutsumalla `self.add(hello, btn)`. Tämä järjestely määrittelee näkymän rakenteen, jolloin lomake on sekä vuorovaikutteinen että visuaalisesti keskitetty.

## Sovelluksen tyylittely {#styling-the-app}

`styles.css`-tiedosto tarjoaa mukautettuja tyylejä webforJ-sovelluksellesi. Tätä CSS-tiedostoa käytetään Application-luokassa [`@StyleSheet`](../managing-resources/importing-assets#importing-css-files) -annotaation avulla, mikä mahdollistaa sovelluksen tyylin soveltamisen komponenteille.

Tämä tiedosto sijaitsee projektin `resources/static`-hakemistossa ja sitä voidaan viitata verkkopalvelimen URL:llä `ws://app.css`.
