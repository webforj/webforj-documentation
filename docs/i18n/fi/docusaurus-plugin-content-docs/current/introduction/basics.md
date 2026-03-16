---
title: App Basics
sidebar_position: 3
_i18n_hash: e4eae914f0cbd5c9e5eacb6e681570a9
---
Once webforJ ja sen riippuvuudet on asetettu projektiisi, olet valmis luomaan sovelluksen rakenteen. Tämä artikkeli käy läpi keskeiset elementit yksinkertaisessa webforJ-sovelluksessa, erityisesti keskittyen `Application`- ja `HomeView`-luokkiin, jotka ovat perusluokkia `webforj-archetype-hello-world`-aloitusprojektissa.

## Päällys sovellusluokka: `Application.java` {#main-app-class-applicationjava}

`Application`-luokka toimii lähtöpisteenä webforJ-sovelluksellesi, ja se asettaa olennaiset asetukset ja reitit. Ensinnäkin, huomaa luokan julistus ja annotaatiot.

Tämä luokka laajentaa ydin `App`-luokkaa webforJ:stä, mikä tekee siitä tunnistettavan webforJ-sovelluksena. Eri annotaatiot määrittävät sovelluksen teeman, nimen ja reitityksen.

```java
@Routify(packages = "com.samples.views")
@AppTitle("webforJ Hello World")
@StyleSheet("ws://app.css")
public class Application extends App {
}
```

- `@Routify`: Määrittää, että webforJ:n tulee skannata `com.samples.views` -paketti reittikomponenteille.
- `@AppTitle`: Määrittää nimen, joka näkyy sovelluksen selainvälilehdellä.
- `@StyleSheet`: Linkittää ulkoisen CSS-tiedoston `app.css`, jolloin sovellukselle voidaan käyttää mukautettua tyylitystä.

`Application`-luokassa ei ole muita menetelmiä, koska asetukset tehdään annotaatioiden kautta, ja webforJ hoitaa sovelluksen aloituksen.

Kun `Application.java` on asetettu, sovellus on nyt konfiguroitu nimellä ja reiteillä, jotka osoittavat näkymäpakettiin. Seuraavaksi `HomeView`-luokan yleiskatsaus antaa käsityksen siitä, mitä näytetään, kun sovellus käynnistetään.

### Löydä `App` {#discovering-an-app}

Yksi <JavadocLink type="foundation" location="com/webforj/App" code='true'>App</JavadocLink> -rajoitus on voimassa webforJ:ssä, joka siirtää kaikki virheiden käsittelyvastuut Java-puolelle ja antaa kehittäjille täydellisen hallinnan virheiden hallintaan.

WebforJ:n käynnistysprosessin aikana kaikki luokat, jotka laajentavat <JavadocLink type="foundation" location="com/webforj/App" code='true'>com.webforj.App</JavadocLink>, skannataan. Jos löytyy useita sovelluksia, järjestelmä etsii <JavadocLink type="foundation" location="com/webforj/annotation/AppEntry" code='true'>com.webforj.annotation.AppEntry</JavadocLink> -annotaatiota. Jos jokin löydetty luokka on merkitty <JavadocLink type="foundation" location="com/webforj/annotation/AppEntry" code='true'>@AppEntry</JavadocLink> -annotaatiolla, ensimmäinen kohdattu luokka katsotaan lähtöpisteeksi.

- Jos luokka on merkitty `@AppEntry`, tämä luokka valitaan lähtöpisteeksi.
- Jos useilla luokilla on `@AppEntry` -annotaatio, poikkeus heitetään, jossa luetellaan kaikki löydetyt luokat.
- Jos mikään luokka ei ole merkitty ja vain yksi `App`-aliluokka löytyy, tämä luokka valitaan lähtöpisteeksi.
- Jos mikään luokka ei ole merkitty ja useita `App`-aliluokkia löytyy, poikkeus heitetään, jossa kuvataan jokainen aliluokka.

:::tip Virheiden käsittely
Lisätietoja virheiden käsittelystä webforJ:ssä, katso [tämä artikkeli](../advanced/error-handling).
:::

## Päällys näkymäluokka: `HomeView.java` {#main-view-class-homeviewjava}

`HomeView`-luokka määrittelee yksinkertaisen näkymäkomponentin, joka toimii sovelluksen etusivuna. Se näyttää kentän ja painikkeen, joka tervehtii käyttäjän kirjoittamaa nimeä.

### Luokan julistus ja annotaatiot {#class-declaration-and-annotations}

`HomeView` laajentaa `Composite<FlexLayout>`-luokkaa, mikä mahdollistaa sen toimivan uudelleenkäytettävänä komponenttina, joka koostuu [`FlexLayout`](../components/flex-layout) -komponentista. [`@Route("/")`](../routing/overview) tekee tästä sovelluksen juurireitin.

```java
@Route("/")
public class HelloWorldView extends Composite<FlexLayout> {

  private final FlexLayout self = getBoundComponent();
  private TextField hello = new TextField("What is your name?");
  private Button btn = new Button("Say Hello");

  public HelloWorldView(){
    self.setDirection(FlexDirection.COLUMN);
    self.setMaxWidth(300);
    self.setStyle("margin", "1em auto");

    btn.setTheme(ButtonTheme.PRIMARY)
        .addClickListener(e -> 
          Toast.show("Welcome to webforJ Starter " + hello.getValue() + "!", Theme.GRAY));

    self.add(hello, btn);
  }
}
```

### Komponentin alustaminen {#component-initialization}

Luokan sisällä useita käyttöliittymäelementtejä alustetaan ja julistetaan:

```java
private final FlexLayout self = getBoundComponent();
private TextField hello = new TextField("What is your name?");
private Button btn = new Button("Say Hello");
```

- `self`: Pää asettelukomponentti, joka käyttää [`FlexLayout`](../components/flex-layout), konfiguroitu elementtien säilyttämiseksi. Tämä elementti käyttää `getBoundComponent()`-metodia tallentaakseen pää `FlexLayout`-elementin, jota luokka sisältää.
- `hello`: [`TextField`](../components/fields/textfield), jonka etiketti on `What is your name?`, johon käyttäjät voivat syöttää nimensä.
- `btn`: Päätyylillä varustettu [`Button`](../components/button), jonka etiketti on `Say Hello`.

### Asettelun konfigurointi {#layout-configuration}

Asettelu `(self)` on konfiguroitu muutamalla avaintyylillä:

- `FlexDirection.COLUMN` pinotaan elementit pystysuoraan.
- `setMaxWidth(300)` rajoittaa leveyden 300 pikseliin kompaktin asettelun saavuttamiseksi.
- `setStyle("margin", "1em auto")` keskitää asettelun sen ympärillä olevan marginaalin avulla.

### Komponenttien lisääminen asetteluun {#adding-components-to-the-layout}

Viimeiseksi, hello-kenttä ja btn-painike lisätään [`FlexLayout`](../components/flex-layout) -kontekstiin kutsumalla `self.add(hello, btn)`. Tämä järjestely määrittelee näkymän rakenteen, tehden lomakkeesta sekä vuorovaikutteisen että visuaalisesti keskitetyn.

## Sovelluksen tyylittely {#styling-the-app}

`styles.css` -tiedosto tarjoaa mukautettua tyylittelyä webforJ-sovelluksellesi. Tätä CSS-tiedostoa viitataan Application-luokassa käyttäen [`@StyleSheet`](../managing-resources/importing-assets#importing-css-files) -annotaatiota, joka mahdollistaa sovelluksen käyttää tyylejä sovelluksen komponentteihin.

Tämä tiedosto sijaitsee projektin `resources/static`-hakemistossa ja voidaan viitata käyttöpalvelimen URL-osoitteella `ws://app.css`.
