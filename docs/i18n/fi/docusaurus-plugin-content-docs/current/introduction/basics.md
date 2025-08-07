---
title: App Basics
sidebar_position: 3
_i18n_hash: eb456b4bb94bf617f33f8aa8425ad97f
---
Kun webforJ ja sen riippuvuudet on asetettu projektiisi, olet valmis luomaan sovellusrakenteen. Tämä artikkeli kävelee läpi perustavanlaatuisen webforJ-sovelluksen keskeiset elementit, erityisesti keskittyen `Application`- ja `HomeView`-luokkiin, jotka ovat perustavia luokkia `webforj-archetype-hello-world`-aloitusprojektissa.

## Päänsävy-luokka: `Application.java` {#main-app-class-applicationjava}

`Application`-luokka toimii webforJ-sovelluksesi pääkäynnistyskohtana, asetellen olennaiset konfiguraatiot ja reitit. Aloitetaan huomioimalla luokan julistus ja annotaatiot.

Tämä luokka perii ydin `App`-luokan webforJ:ltä, tehden siitä tunnistettavan webforJ-sovelluksena. Erilaiset annotaatiot määrittävät sovelluksen teeman, otsikon ja reitit.

```java
@Routify(packages = "com.samples.views")
@AppTitle("webforJ Hello World")
@StyleSheet("ws://app.css")
public class Application extends App {
}
```

- `@Routify`: Määrittää, että webforJ:n tulee skannata `com.samples.views`-paketti reittikomponenttien löytämiseksi.
- `@AppTitle`: Määrittelee otsikon, joka näkyy sovelluksen selainvälilehdessä.
- `@StyleSheet`: Linkittää ulkoiseen CSS-tiedostoon, `app.css`, salliessaan mukautetun tyylittelyn sovellukselle.

`Application`-luokassa ei ole muita metodeja, koska konfiguraatiot on asetettu annotaatioiden kautta ja webforJ hoitaa sovelluksen alustamisen.

Kun `Application.java` on asetettu, sovellus on nyt konfiguroitu otsikolla ja reiteillä, jotka osoittavat näkymäpakettiin. Seuraavaksi katsaus `HomeView`-luokkaan antaa käsityksen siitä, mitä näytetään kun sovellus ajetaan.

### Löydä `App` {#discovering-an-app}

Yksi <JavadocLink type="foundation" location="com/webforj/App" code='true'>App</JavadocLink>-rajaus täsmennetään webforJ:ssä, siirtäen kaikki virheiden käsittelyvastuut Java-puolelle ja antaen kehittäjille täydellisen hallinnan virheiden hallinnassa.

WebforJ:n käynnistysprosessin aikana kaikki luokat, jotka perivät <JavadocLink type="foundation" location="com/webforj/App" code='true'>com.webforj.App</JavadocLink>-luokan, skannataan. Jos useita sovelluksia löytyy, järjestelmä etsii <JavadocLink type="foundation" location="com/webforj/annotation/AppEntry" code='true'>com.webforj.annotation.AppEntry</JavadocLink>-annotaatiota. Jos jokin löydetyistä luokista on merkitty <JavadocLink type="foundation" location="com/webforj/annotation/AppEntry" code='true'>@AppEntry</JavadocLink>-annotaatiolla, ensimmäinen kohdattaessa otetaan huomioon pääkäynnistyskohtana.

- Jos luokkaa on merkitty `@AppEntry`, tätä luokkaa käytetään pääkäynnistyskohtana.
- Jos useita luokkia on merkitty `@AppEntry`, heitetään poikkeus, joka listaa kaikki löydetyt luokat.
- Jos mitään luokkaa ei ole merkitty ja vain yksi `App`:n aliluokka löytyy, tätä luokkaa käytetään pääkäynnistyskohtana.
- Jos mitään luokkaa ei ole merkitty ja useita `App`:n aliluokkia löytyy, heitetään poikkeus, joka yksityiskohtaisesti kertoo jokaisesta aliluokasta.

:::tip Virheiden käsittely
Lisätietoja virheiden käsittelystä webforJ:ssa, katso [tämä artikkeli](../advanced/error-handling).
:::

## Päänäkymä-luokka: `HomeView.java` {#main-view-class-homeviewjava}

`HomeView`-luokka määrittelee yksinkertaisen näkymäkomponentin, joka toimii sovelluksen kotisivuna. Se näyttää kentän ja painikkeen, joka tervehtii käyttäjän kirjoittamaa nimeä.

### Luokan julistus ja annotaatiot {#class-declaration-and-annotations}

`HomeView` perii `Composite<FlexLayout>`-luokan, mikä mahdollistaa sen toimimisen uudelleenkäytettävänä komponenttina, joka koostuu [`FlexLayout`](../components/flex-layout) komponentista. [`@Route("/")`](../routing/overview) tekee tästä sovelluksen juurireitin.

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

Luokan sisällä useita käyttöliittymäelementtejä alustetaan ja julistetaan:

```java
private FlexLayout self = getBoundComponent();
private TextField hello = new TextField("Mikä on nimesi?");
private Button btn = new Button("Tervehdi");
```

- `self`: Pääasiallinen asettelukomponentti, joka käyttää [`FlexLayout`](../components/flex-layout)-komponenttia, konfiguroituna säiliöksi elementeille. Tämä elementti käyttää `getBoundComponent()`-metodia tallentaakseen pääasiallisen `FlexLayout`-luokan, joka sisältää.
- `hello`: [`TextField`](../components/fields/textfield), jonka nimilappu on `Mikä on nimesi?`, antaa käyttäjille mahdollisuuden syöttää nimensä.
- `btn`: Pääteemalla varustettu [`Button`](../components/button) nimeltä `Tervehdi`.

### Asettelu konfigurointi {#layout-configuration}

Asettelu `(self)` konfiguroidaan muutamalla avaintyylillä:

- `FlexDirection.COLUMN` pinotaan elementit pystysuoraan.
- `setMaxWidth(300)` rajoittaa leveyden 300 pikseliin tiiviiksi asetteluksi.
- `setStyle("margin", "1em auto")` keskittää asettelun marginaalilla sen ympärillä.

### Komponenttien lisääminen asetteluun {#adding-components-to-the-layout}
Lopuksi, tervehtimiskenttä ja btn-painike lisätään [`FlexLayout`](../components/flex-layout) säiliöön kutsumalla `self.add(hello, btn)`. Tämä järjestely määrittelee näkymän rakenteen, tehden lomakkeesta sekä interaktiivisen että visuaalisesti keskitetyyn.

## Sovelluksen tyylittäminen {#styling-the-app}

`styles.css`-tiedosto tarjoaa mukautettua tyylittelyä webforJ-sovelluksellesi. Tätä CSS-tiedostoa viitataan Application-luokassa käyttäen [`@StyleSheet`](../managing-resources/importing-assets#importing-css-files) annotaatiota, mikä sallii sovelluksen soveltaa tyylejä sovelluksen sisällä oleviin komponentteihin.

Tämä tiedosto sijaitsee projektin `resources/static`-hakemistossa ja voidaan viitata käyttämällä verkkopalvelimen URL-osoitetta `ws://app.css`.
