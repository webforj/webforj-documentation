---
title: Perustan perustavan ohjelman
sidebar_position: 2
_i18n_hash: c59ff0def84230ed79877cba3d5e5aa4
---
Tämä ensimmäinen vaihe luo perustan asiakashallintaohjelmalle luomalla yksinkertaisen, interaktiivisen käyttöliittymän. Tämä osoittaa, kuinka perustaa perus webforJ -sovellus, jossa on yksi painike, joka avaa dialogin, kun sitä napsautetaan. Tämä on yksinkertainen toteutus, joka esittelee keskeiset komponentit ja antaa sinulle käsityksen siitä, kuinka webforJ toimii.

Tämä vaihe hyödyntää webforJ:n tarjoamaa perus sovelluskirjastoa määrittämään sovelluksen rakennetta ja käyttäytymistä. Seuraavat vaiheet siirtyvät kehittyneempään asetukseen, joka käyttää reititystä useiden näyttöjen hallintaan, joka esitellään kohdassa [Scaling with Routing and Composites](./scaling-with-routing-and-composites).

Tämän vaiheen lopuksi sinulla on toimiva sovellus, joka osoittaa perusvuorovaikutusta komponenttien ja tapahtumien käsittelyn kanssa webforJ:ssa. Suorittaaksesi sovelluksen:

- Siirry `1-creating-a-basic-app` -hakemistoon
- Suorita komento `mvn jetty:run`

<div class="videos-container">
  <video controls>
    <source src="https://cdn.webforj.com/webforj-documentation/video/tutorials/creating-a-basic-app.mp4" type="video/mp4"/>
  </video>
</div>

## Creating a webforJ app {#creating-a-webforj-app}

WebforJ:ssa `App` edustaa keskushubia projektisi määrittämiseksi ja hallitsemiseksi. Jokainen webforJ -sovellus alkaa luomalla yksi luokka, joka laajentaa perus `App`-luokkaa, joka toimii ydinkehykseksi:

- Hallita sovelluksen elinkaarta, mukaan lukien alustaminen ja lopettaminen.
- Hallita reititystä ja navigaatiota, jos se on käytössä.
- Määrittää sovelluksen teema, alue ja muut yleiset asetukset.
- Tarjota olennaisia työkaluja ympäristön ja komponenttien kanssa vuorovaikutukseen.

### Extending the `App` class {#extending-the-app-class}

Tässä vaiheessa luodaan luokka nimeltä `DemoApplication.java`, joka laajentaa `App`-luokkaa.

```java title="DemoApplication.java"
public class DemoApplication extends App {
  @Override
  public void run() {
    // Ydin sovelluslogiikka tulee tähän
  }
}
```

:::tip Avainasetukset

Tässä demo-sovelluksessa `webforj.conf` -tiedosto on määritetty seuraavilla kahdella olennaisella ominaisuudella:

- **`webforj.entry`**: Määrittää täydellisen luokan nimen, joka laajentaa `App`:ta ja toimii pääsijaintina projektillesi. Tämän oppaan vuoksi aseta se arvoon `com.webforj.demos.DemoApplication` estääksesi epäselvyyksiä alustamisen aikana.
  ```hocon
  webforj.entry = com.webforj.demos.DemoApplication
  ```
- **`webforj.debug`**: Ota käyttöön virhetilatila yksityiskohtaisia lokitietoja ja virheiden näkyvyyttä varten kehityksen aikana. Varmista, että se on asetettu `true`, kun työskentelet tämän oppaan parissa:
  ```hocon
  webforj.debug = true
  ```

Lisätietoja muista asetuksista löytyy [Configuration Guide](../../configuration/overview).
:::

### Overriding the `run()` method {#overriding-the-run-method}

Kun projekti on oikein määritetty, `run()`-metodi `App`-luokassa ylikirjoitetaan.

`run()`-metodi on sovelluksesi ydin webforJ:ssa. Se määrittää, mitä tapahtuu sovelluksen alustamisen jälkeen, ja on pääsijainti sovelluksesi ominaisuuksille. Ylikirjoittamalla `run()`-metodin voit toteuttaa logiikan, joka luo ja hallitsee sovelluksesi käyttöliittymää ja käyttäytymistä.

:::tip Reitityksen käyttäminen
Reitityksen toteuttaminen sovelluksessa ei vaadi `run()`-metodin ylikirjoittamista, sillä kehys käsittelee automaattisesti reittien alustamisen ja alkuperäisen `Frame`:n luomisen. `run()`-metodia kutsutaan sen jälkeen, kun perusreitti on ratkaistu, varmistaen, että sovelluksen navigointijärjestelmä on täysin alustettu ennen kuin mitään logiikkaa suoritetaan. Tämä opas syventää reitityksen toteutusta [step 3](scaling-with-routing-and-composites). Lisätietoja on myös [Reititysartikkelissa](../../routing/overview).
:::

```java title="DemoApplication.java"
public class DemoApplication extends App {
  @Override
  public void run() throws WebforjException {
    // Sovelluslogiikka
  }
}
```

## Adding components {#adding-components}

WebforJ:ssa komponentit ovat sovelluksesi käyttöliittymän rakennuspalikoita. Nämä komponentit edustavat erillisiä osia sovelluksesi käyttöliittymästä, kuten painikkeita, tekstikenttiä, dialogueja tai taulukoita.

Voit ajatella käyttöliittymää komponenttien puuna, jossa `Frame` toimii juurena. Jokainen `Frame`:en lisätty komponentti muodostaa oksan tai lehden tässä puussa, mikä vaikuttaa sovelluksesi kokonaisrakenteeseen ja käyttäytymiseen.

:::tip Komponenttikatalogi
Katso [tämä sivu](../../components/overview) saadaksesi luettelon eri komponenteista, jotka ovat käytettävissä webforJ:ssa.
:::

### App `Frame` {#app-frame}

`Frame`-luokka webforJ:ssa edustaa ei-sisäkkästä, ylimmällä tasolla olevaa ikkunaa sovelluksessasi. `Frame` toimii tyypillisesti käyttöliittymäkomponenttien pääkonteinerina, joten se on olennainen rakennuspalikka käyttöliittymän rakentamisessa. Jokaisessa sovelluksessa on oltava vähintään yksi `Frame`, ja voit lisätä komponentteja, kuten painikkeita, dialogeja tai lomakkeita näihin kehyksiin.

`Frame` luodaan `run()`-metodissa tässä vaiheessa - myöhemmin komponentteja lisätään tänne.

```java title="DemoApplication.java"
public class DemoApplication extends App {
  @Override
  public void run() throws WebforjException {
    Frame mainFrame = new Frame();
  }
}
```

### Server and client side components {#server-and-client-side-components}

Jokaisella palvelinpuolen komponentilla webforJ:ssa on vastaava asiakaspuolen verkkokomponentti. Palvelinpuolen komponentit käsittelevät logiikkaa ja taustatoimintoja, kun taas asiakaspuolen komponentit, kuten `dwc-button` ja `dwc-dialog`, hallitsevat etupään renderointia ja tyylitystä.

:::tip Kokoavat komponentit

Peruskomponenttien lisäksi, joita webforJ tarjoaa, voit suunnitella mukautettuja kokoavia komponentteja ryhmittämällä useita elementtejä yhdeksi uudelleen käytettäväksi yksiköksi. Tätä käsitettä käsitellään tämän oppaan vaiheessa. Lisätietoja on [Composite Article](../../building-ui/composite-components)
:::

Komponentit on lisättävä säilytys- luokkaan, joka toteuttaa <JavadocLink type="foundation" location="com/webforj/concern/HasComponents" code='true' >HasComponents</JavadocLink> -rajapinnan. `Frame` on yksi tällainen luokka - tässä vaiheessa lisää `Paragraph` ja `Button` `Frame`:en, jotka näkyvät käyttöliittymässä selaimessa:

```java title="DemoApplication.java"
public class DemoApplication extends App {
  Paragraph demo = new Paragraph("Demo Application!");
  Button btn = new Button("Info");

  @Override
  public void run() throws WebforjException {
    Frame mainFrame = new Frame();
    btn.setTheme(ButtonTheme.PRIMARY)
        .addClickListener(e -> showMessageDialog("This is a demo!", "Info"));
    mainFrame.add(demo, btn);
  }
}
```

Tämän suorittaminen antaa sinulle yksinkertaisesti muotoillun painikkeen, joka mahdollistaa viestin näyttämisen, jossa lukee "Tämä on demo!"

## Styling with CSS {#styling-with-css}

Tyylittely webforJ:ssa antaa sinulle täydellisen joustavuuden suunnitella sovelluksesi ulkoasua. Vaikka kehys tukee yhtenäistä suunnittelua ja tyyliä suoraan laatikosta, se ei pakota erityistä tyylittelytapaa, mikä mahdollistaa mukautettujen tyylien soveltamisen, jotka ovat linjassa sovelluksesi vaatimusten kanssa.

WebforJ:n avulla voit dynaamisesti lisätä luokkien nimiä komponentteihin ehdollista tai interaktiivista tyylittelyä varten, käyttää CSS:ää johdonmukaisen ja tehokkaan suunnittelujärjestelmän saavuttamiseksi, ja liittää kokonaisia sisäisiä tai ulkoisia tyylitiedostoja.

### Adding CSS classes to components {#adding-css-classes-to-components}

Voit dynaamisesti lisätä tai poistaa luokkia komponentteista käyttämällä `addClassName()` ja `removeClassName()` -menetelmiä. Nämä menetelmät mahdollistavat komponentin tyylien hallinnan sovelluksesi logiikan perusteella. Lisää `mainFrame`-luokan nimi edellisissä vaiheissa luotuun `Frame`:en lisäämällä seuraava koodi `run()`-metodiin:

```java
mainFrame.addClassName("mainFrame");
```

### Attaching CSS files {#attaching-css-files}

Tyylitelläksesi sovellustasi voit lisätä CSS-tiedostoja projektiisi joko käyttämällä resurssiannotaatioita tai hyödyntämällä webforJ <JavadocLink type="foundation" location="com/webforj/Page" >asset-API:a</JavadocLink> ajon aikana. [Katso tämä artikkeli](../../managing-resources/importing-assets) saadaksesi lisätietoja.

Esimerkiksi @StyleSheet-annotaatiota käytetään tyylien lisäämiseksi resurssit/staattinen-hakemistosta. Se luo automaattisesti URL-osoitteen määritetylle tiedostolle ja liittää sen DOM:iin varmistaen, että tyylit sovelletaan sovellukseesi. Huomaa, että tiedostot, jotka sijaitsevat staattisen hakemiston ulkopuolella, eivät ole käytettävissä.

```java title="DemoApplication.java"
@StyleSheet("ws://styles/library.css")
public class DemoApplication extends App {
  @Override
  public void run() {
    // Sovelluslogiikka täällä
  }
}
```
:::tip Verkkopalvelimen URL-osoitteet
Varmistaaksesi, että staattiset tiedostot ovat käytettävissä, ne tulee sijoittaa hakemistoon resources/static. Jotta voit liittää staattisen tiedoston, voit rakentaa sen URL-osoitteen käyttämällä verkkopalvelun protokollaa.
:::

### Sample CSS code {#sample-css-code}

CSS-tiedostoa käytetään projektissasi hakemistossa `resources > static > css > demoApplication.css`, ja seuraavaa CSS:ää käytetään sovelluksen perusmuotoilun soveltamiseen.

```css
.mainFrame {
  display: inline-grid;
  gap: 20px;
  margin: 20px;
  padding: 20px;
  border: 1px dashed;
  border-radius: 10px;
}
```

Tämän jälkeen seuraava annotaatio tulisi lisätä `App`-luokkaasi:

```java title="DemoApplication.java"
@StyleSheet("ws://css/demoApplication.css")
@AppTitle("Demo Step 1")
public class DemoApplication extends App {
```

CSS-tyylit sovelletaan pää `Frame`:een ja tarjoavat rakennetta järjestämällä komponentteja [verkkogalleria](https://developer.mozilla.org/en-US/docs/Web/CSS/CSS_grid_layout)-asettelun avulla, ja lisäämällä marginaali-, täyttö- ja reunustyylejä, jotta käyttöliittymä näyttää visuaalisesti järjestäytyneeltä.
