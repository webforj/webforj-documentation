---
title: Setup and Configuration
sidebar_position: 2
_i18n_hash: 76bc55d5b841ae3c06bcd2cd9e8b2632
---
Webswingin integroiminen webforJ:hen sisältää kaksi komponenttia: Webswing-palvelimen, joka isännöi Swing-sovellustasi, ja `WebswingConnector`-komponentin webforJ-sovelluksessa, joka upottaa sen.

## Esivaatimukset {#prerequisites}

Ennen kuin aloitat, varmista, että sinulla on seuraavat esivaatimukset:

- **Java-työpöytäsovellus**: Swing-, JavaFX- tai SWT-sovellus pakattuna JAR-tiedostoksi
- **Webswing-palvelin**: lataa osoitteesta [webswing.org](https://webswing.org)
- **webforJ versio `25.10` tai uudempi**: vaatimuksena `WebswingConnector`-tuki

## Arkkitehtuurikatsaus {#architecture-overview}

Integrointiarkkitehtuuri koostuu:

1. **Webswing-palvelin**: suorittaa Swing-sovellustasi, kaappaa GUI:n renderöinnin ja käsittelee käyttäjän syötteet
2. **webforJ-sovellus**: isännöi verkkosovellustasi upotetulla `WebswingConnector`:lla
3. **Selaimen asiakas**: näyttää sekä webforJ-käyttöliittymän että upotetun Swing-sovelluksen

:::important Porttien konfigurointi
Webswingin ja webforJ:n on toimittava eri porteilla yhteensopimattomuuden välttämiseksi. Sekä webforJ että Webswing toimivat tyypillisesti portissa `8080`. Sinun pitäisi vaihtaa joko Webswingin portti tai webforJ:n portti.
:::

## Webswing-palvelimen asennus {#webswing-server-setup}

### Asennus ja käynnistys {#installation-and-startup}

1. **Lataa Webswing** [virallisilta verkkosivuilta](https://www.webswing.org/en/downloads)
2. **Pura arkisto** haluamaasi sijaintiin (esim. `/opt/webswing` tai `C:\webswing`)
3. **Käynnistä palvelin** käyttäen alustoittain mukautettuja skriptejä:

<Tabs>
      <TabItem value="Linux" label="Linux" default>
        ```bash
        webswing.sh
        ```
      </TabItem>
      <TabItem value="macOS" label="macOS">
        ```bash
        webswing.command
        ```
      </TabItem>
      <TabItem value="Windows" label="Windows">
        ```bash
        webswing.bat
        ```
      </TabItem>
</Tabs>

4. **Varmista, että palvelin on käynnissä** pääsemällä osoitteeseen `http://localhost:8080`

### Sovelluksen konfigurointi {#application-configuration}

Kun palvelin on käynnissä, pääse admin konsoliin osoitteessa `http://localhost:8080/admin` lisätäksesi ja konfiguroidaksesi Swing-sovellustasi.

Admin konsolissa konfiguroi:

- **Sovelluksen nimi**: tulee osaksi URL-polku (esim. `myapp` → `http://localhost:8080/myapp/`)
- **Pääluokka**: Swing-sovelluksesi lähtöpiste
- **Classpath**: polku sovelluksesi JAR-tiedostoon ja riippuvuuksiin
- **JVM-argumentit**: muistin asetukset, järjestelmän ominaisuudet ja muut JVM-vaihtoehdot
- **Kotikansio**: sovelluksen työskentelyhakemisto

Konfiguraation jälkeen Swing-sovelluksesi on käytettävissä osoitteessa `http://localhost:8080/[app-name]/`

### CORS-konfigurointi {#cors-configuration}

Kun Webswingiä upotetaan webforJ-sovellukseen, joka toimii eri portissa tai verkkotunnuksessa, sinun on konfiguroitava Cross-Origin Resource Sharing (CORS) Webswingissä. Tämä mahdollistaa selaimen ladata Webswing-sisältöä webforJ-sivustoltasi.

Webswingin admin konsolissa siirry sovelluksesi konfiguraatioon ja aseta:

- **Sallitut alkuperät**: Lisää webforJ-sovelluksesi alkuperä (esim. `http://localhost:8090` tai `*` kehitystä varten)

Tämä asetuksen vastaa `allowedCorsOrigins`-vaihtoehtoa Webswingin sovelluksen konfiguraatiossa.

## webforJ-integrointi {#webforj-integration}

Kun Webswing-palvelin toimii Swing-sovelluksesi kanssa konfiguroituna ja CORS käytössä, voit integroida sen webforJ-sovellukseesi.

### Lisää riippuvuus {#add-dependency}

Webswing-integrointi riippuu webforJ:n Webswing-integraatiomoduulista, joka tarjoaa `WebswingConnector`-komponentin ja siihen liittyvät luokat. Lisää seuraava `pom.xml`-tiedostoosi:

```xml
<dependency>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-webswing</artifactId>
  <version>${webforj.version}</version>
</dependency>
```

### Perusimplementaatio {#basic-implementation}

Luo näkymä, joka upottaa Swing-sovelluksesi käyttämällä `WebswingConnector`:ia:

```java title="SwingAppView.java"
package com.example.views;

import com.webforj.annotation.Route;
import com.webforj.component.Composite;
import com.webforj.component.html.elements.Div;
import com.webforj.component.webswing.WebswingConnector;

@Route
public class SwingAppView extends Composite<Div> {
  private WebswingConnector connector;

  public SwingAppView() {
    // Alustaa liittimen Webswing-sovelluksesi URL-osoitteella
    connector = new WebswingConnector("http://localhost:8080/myapp/");

    // Aseta näyttömitat
    connector.setSize("100%", "600px");

    // Lisää näkymäkontrolleriin
    getBoundComponent().add(connector);
  }
}
```

Liitin luo automaattisesti yhteyden Webswing-palvelimeen, kun se lisätään DOM:iin. Swing-sovelluksen käyttöliittymä renderoidaan sitten liittimen komponentissa.

## Konfiguraatioasetukset {#configuration-options}

`WebswingOptions`-luokka mahdollistaa liittimen käyttäytymisen mukauttamisen. Oletusarvoisesti liitin käynnistyy automaattisesti, kun se luodaan ja käyttää tavanomaisia yhteysasetuksia. Voit muuttaa tätä käyttäytymistä luomalla `WebswingOptions`-instanssin ja soveltamalla sitä liittimeen.

Esimerkiksi, jos haluat piilottaa uloskirjautumispainikkeen tuotantoympäristössä, jossa hallinnoit todennusta webforJ-sovelluksesi kautta:

```java
WebswingConnector connector = new WebswingConnector("http://localhost:8080/myapp/");

WebswingOptions options = new WebswingOptions()
    .setDisableLogout(true);  // Piilota uloskirjautumispainike

connector.setOptions(options);
```

Tai jos tarvitset käsin hallintaa siitä, milloin yhteys käynnistyy:

```java
// Luo liitin ilman automaattista käynnistystä
WebswingConnector connector = new WebswingConnector(url, false);

// Konfiguroi ja käynnistä, kun olet valmis
WebswingOptions options = new WebswingOptions();
connector.setOptions(options);
connector.start();
```

Asetukset kattavat yhteyden hallinnan, todennuksen, virheiden seurannan ja tarkkailun.
