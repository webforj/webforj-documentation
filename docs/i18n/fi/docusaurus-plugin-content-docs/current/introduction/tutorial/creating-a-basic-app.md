---
title: Creating a Basic App
sidebar_position: 2
_i18n_hash: 9bd91b6d733198a2c16c9377029e8162
---
Tämä ensimmäinen vaihe luo perustan asiakashallintasovellukselle luomalla yksinkertaisen, interaktiivisen käyttöliittymän. Tämä näyttää, kuinka perustaa yksinkertainen webforJ-sovellus, jossa on yksi painike, joka avaa dialogin, kun sitä napsautetaan. Se on suoraviivainen toteutus, joka esittelee keskeisiä komponentteja ja antaa käsityksen siitä, miten webforJ toimii.

Tässä vaiheessa hyödynnetään webforJ:n tarjoamaa perussovellusluokkaa sovelluksen rakenteen ja käyttäytymisen määrittämiseen. Jatkamalla myöhempiin vaiheisiin siirtyminen tapahtuu kehittyneempään asetukseen, jossa käytetään reititystä useiden näyttöjen hallintaan, esiteltynä artikkelissa [Scaling with Routing and Composites](./scaling-with-routing-and-composites).

Tämän vaiheen lopussa sinulla on toimiva sovellus, joka demonstroi perusvuorovaikutusta komponenttien ja tapahtumakäsittelyn kanssa webforJ:ssä. Sovelluksen suorittamiseksi:

- Siirry hakemistoon `1-creating-a-basic-app`
- Suorita komento `mvn jetty:run`

<div class="videos-container">
  <video controls>
    <source src="https://cdn.webforj.com/webforj-documentation/video/tutorials/creating-a-basic-app.mp4" type="video/mp4"/>
  </video>
</div>

## webforJ-sovelluksen luominen {#creating-a-webforj-app}

WebforJ:ssä `App` edustaa keskeistä keskusta projektin määrittämisessä ja hallinnassa. Jokainen webforJ-sovellus alkaa luomalla yksi luokka, joka laajentaa perustana olevaa `App`-luokkaa, joka toimii ydinkehyksenä:

- Hallita sovelluksen elinkaarta, mukaan lukien alustaminen ja lopettaminen.
- Käsitellä reititystä ja navigointia, jos se on käytössä.
- Määrittää sovelluksen teema, alue ja muut yleiset asetukset.
- Tarjota olennaisia työkaluja ympäristön ja komponenttien kanssa vuorovaikutukseen.

### `App`-luokan laajentaminen {#extending-the-app-class}

Tässä vaiheessa luodaan luokka nimeltä `DemoApplication.java`, joka laajentaa `App`-luokkaa.

```java title="DemoApplication.java"
public class DemoApplication extends App {
  @Override
  public void run() {
    // Sovelluksen ydinkoodi tulee tähän
  }
}
```

:::tip Tärkeät konfigurointiominaisuudet

Tässä demo-sovelluksessa `webforj.conf` -tiedosto on määritetty seuraavilla kahdella olennaisella ominaisuudella:

- **`webforj.entry`**: Määrittää luokan täydellisen nimen, joka laajentaa `App`:a ja toimii projektisi pääsisäänkäyntinä. Tämän oppaan vuoksi sen tulee olla asetettu arvoon `com.webforj.demos.DemoApplication` , jotta alustamisen aikana ei synny epäselvyyksiä.
  ```hocon
  webforj.entry = com.webforj.demos.DemoApplication
  ```
- **`webforj.debug`**: Ota käyttöön virheenkorjaustila yksityiskohtaisia lokitietoja ja virheiden näkyvyyttä varten kehityksessä. Varmista, että tämä on asetettu arvoon `true` työskennellessäsi tämän oppaan parissa:
  ```hocon
  webforj.debug = true
  ```

Lisätietoja muista konfigurointivaihtoehdoista on [Konfigurointiohjeessa](../../configuration/overview).
:::

### `run()`-menetelmän liittäminen {#overriding-the-run-method}

Kun olet varmistanut, että projektin konfigurointi on oikea, `run()`-metodi `App`-luokassasi ylikirjoitetaan.

`run()`-metodi on sovelluksesi ydin webforJ:ssä. Se määrittelee, mitä tapahtuu sovelluksen alustamisen jälkeen, ja se on pääsisisäänkäynti sovelluksen ominaisuuksiin. Ylikirjoittamalla `run()`-menetelmän voit toteuttaa logiikan, joka luo ja hallitsee sovelluksesi käyttöliittymää ja käyttäytymistä.

:::tip Reitityksen käyttäminen
Reitittämisen toteuttaminen sovelluksessa ei edellytä `run()`-menetelmän ylikirjoittamista, koska kehys käsittelee automaattisesti reittien alustamisen ja alkuperäisen `Frame`:n luomisen. `run()`-menetelmää kutsutaan sen jälkeen, kun perusreitti on ratkaistu, varmistaen, että sovelluksen navigointijärjestelmä on täysin alustettu ennen minkään logiikan suorittamista. Tämä opas käsittelee syvällisemmin reitityksen toteuttamista [vaiheessa 3](scaling-with-routing-and-composites). Lisätietoja on myös saatavilla [Reititysartikkelissa](../../routing/overview).
:::

```java title="DemoApplication.java"
public class DemoApplication extends App {
  @Override
  public void run() throws WebforjException {
    // Sovelluslogiikka
  }
}
```

## Komponenttien lisääminen {#adding-components}

WebforJ:ssä komponentit ovat rakennuspalikoita sovelluksesi käyttöliittymässä. Nämä komponentit edustavat erillisiä osia sovelluksesi käyttöliittymässä, kuten painikkeita, tekstikenttiä, dialogeja tai tauluja.

Voit ajatella käyttöliittymää komponenttipuuna, jossa `Frame` toimii juurena. Jokainen `Frame`:hin lisättävä komponentti muodostaa oksan tai lehden tässä puussa, ja se myötävaikuttaa sovelluksesi kokonaisrakenteeseen ja käyttäytymiseen.

:::tip Komponenttitietokanta
Katso [tältä sivulta](../../components/overview) luettelo erilaisista webforJ:ssä saatavilla olevista komponenteista.
:::

### Sovelluksen `Frame` {#app-frame}

`Frame`-luokka webforJ:ssä edustaa ei-sisäkkäistä, ykköstason ikkunaa sovelluksessasi. `Frame` toimii yleensä pääkonttainerina käyttöliittymäkomponenteille, joten se on olennainen rakennuspalikka käyttöliittymän rakentamisessa. Jokainen sovellus alkaa yhdellä `Frame`:lla, ja voit lisätä komponentteja, kuten painikkeita, dialogeja tai lomakkeita näihin kehyksiin.

`Frame` luodaan `run()`-menetelmässä tässä vaiheessa - myöhemmin komponentteja lisätään tänne.

```java title="DemoApplication.java"
public class DemoApplication extends App {
  @Override
  public void run() throws WebforjException {
    Frame mainFrame = new Frame();
  }
}
```

### Palvelin- ja asiakaspuolen komponentit {#server-and-client-side-components}

Jokaisella palvelinpuolen komponentilla webforJ:ssä on vastaava asiakaspuolen web-komponentti. Palvelinpuolen komponentit käsittelevät logiikkaa ja taustakäsittelyä, kun taas asiakaspuolen komponentit, kuten `dwc-button` ja `dwc-dialog`, hallitsevat etupään renderöintiä ja muotoilua.

:::tip Koostekomponentit

Yhdessä webforJ:ssä tarjottujen ydin komponenttien kanssa voit suunnitella räätälöityjä koostekomponentteja ryhmittelemällä useita elementtejä yhdeksi käytettäväksi yksiköksi. Tätä käsitellään oppaan tässä vaiheessa. Lisätietoja on saatavilla [Koosteaartikkeleissa](../../building-ui/composite-components).
:::

Komponentit on lisättävä säilytysluokkaan, joka toteuttaa <JavadocLink type="foundation" location="com/webforj/concern/HasComponents" code='true'>HasComponents</JavadocLink> -rajapinnan. `Frame` on yksi tällainen luokka - tässä vaiheessa lisää `Paragraph` ja `Button` kehyksiin, jotka renderöidään käyttöliittymässä selaimessa:

```java title="DemoApplication.java"
public class DemoApplication extends App {
  Paragraph demo = new Paragraph("Demo Application!");
  Button btn = new Button("Info");

  @Override
  public void run() throws WebforjException {
    Frame mainFrame = new Frame();
    btn.setTheme(ButtonTheme.PRIMARY)
        .addClickListener(e -> showMessageDialog("Tämä on demo!", "Info"));
    mainFrame.add(demo, btn);
  }
}
```

Kun tämä suoritetaan, sinun pitäisi saada yksinkertainen tyylitelty painike, joka mahdollistaa viestin ilmestymisen, jossa lukee "Tämä on demo!".

## Tyylittely CSS:llä {#styling-with-css}

Tyylittely webforJ:ssä antaa sinulle täydellisen joustavuuden suunnitella sovelluksesi ulkoasua. Vaikka kehys tukee yhtenäistä muotoilua ja tyyliä ilman mittakaavaa, se ei pakota tiettyä tyylittelystyöhön liittyvää lähestymistapaa, mahdollistaen mukautettujen tyylien soveltamisen, jotka vastaavat sovelluksesi vaatimuksia.

WebforJ:n avulla voit dynaamisesti soveltaa luokkien nimiä komponentteihin ehtojen tai interaktiivisen tyylittelyn perusteella, käyttää CSS:ää johdonmukaisen ja skaalautuvan muotoilujärjestelmän luomiseen ja liittää koko inline- tai ulkoiset tyylit.

### CSS-luokkien lisääminen komponentteihin {#adding-css-classes-to-components}

Voit dynaamisesti lisätä tai poistaa luokkien nimiä komponentteihin käyttämällä `addClassName()` ja `removeClassName()` -menetelmiä. Nämä menetelmät antavat sinulle mahdollisuuden hallita komponentin tyylejä sovelluksesi logiikan perusteella. Lisää `mainFrame`-luokan nimi aiemmin luotuun `Frame`:en sisällyttämällä seuraava koodi `run()`-menetelmässä:

```java
mainFrame.addClassName("mainFrame");
```

### CSS-tiedostojen liittäminen {#attaching-css-files}

Sovelluksesi tyylittelyyn voit liittää CSS-tiedostoja projektiisi joko käyttämällä resurssiannotaatioita tai hyödyntämällä webforJ:n <JavadocLink type="foundation" location="com/webforj/Page">asset API</JavadocLink>:a ajon aikana. [Katso tämä artikkeli](../../managing-resources/importing-assets) lisätietoja varten.

Esimerkiksi @StyleSheet-annotaatio käytetään tyylin liittämiseen resources/static-hakemistosta. Se luo automaattisesti URL-osoitteen määritetylle tiedostolle ja liittää sen DOM:iin varmistaen, että tyylit sovelletaan sovellukseesi. Huomaa, että tiedostot, jotka eivät sijaitse staattisessa hakemistossa, eivät ole käytettävissä.

```java title="DemoApplication.java"
@StyleSheet("ws://styles/library.css")
public class DemoApplication extends App {
  @Override
  public void run() {
    // Sovelluslogiikka tässä
  }
}
```
:::tip Verkkopalvelimen URL-osoitteet
Varmistaaksesi, että staattiset tiedostot ovat käytettävissä, niiden tulisi olla kohdassa resources/static. Liittääksesi staattisen tiedoston, voit rakentaa sen URL-osoitteen käyttämällä verkkopalvelun protokollaa.
:::

### Esimerkkikoodia CSS:llä {#sample-css-code}

CSS-tiedostoja käytetään projektissasi kohdassa `resources > static > css > demoApplication.css`, ja seuraava CSS käytetään sovelluksen perusmuotoiluun.

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

Kun tämä on tehty, seuraava annotaatio tulisi lisätä `App`-luokkaasi:

```java title="DemoApplication.java"
@StyleSheet("ws://css/demoApplication.css")
@AppTitle("Demo Vaihe 1")
public class DemoApplication extends App {
```

CSS-tyylit sovelletaan pää `Frame`:en ja tarjoavat rakennetta järjestämällä komponentteja [ruudukon asettelun](https://developer.mozilla.org/en-US/docs/Web/CSS/CSS_grid_layout) avulla, sekä lisäämällä marginaali-, täyttö- ja reunustyylejä, jotta käyttöliittymä olisi visuaalisesti järjestäytynyt.
