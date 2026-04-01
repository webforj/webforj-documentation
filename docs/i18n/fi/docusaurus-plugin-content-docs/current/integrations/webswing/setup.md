---
title: Setup and Configuration
sidebar_position: 2
_i18n_hash: d948bababbedcfe831d4af62f8b6b088
---
Webswingin integroiminen webforJ:hin koostuu kahdesta komponentista: Webswing-palvelimesta, joka isännöi Swing-sovellustasi, ja `WebswingConnector`-komponentista webforJ-sovelluksessasi, joka upottaa sen.

## Edellytykset {#prerequisites}

Ennen kuin aloitat, varmista, että sinulla on seuraavat edellytykset:

- **Java-desktop-sovellus**: Swing-, JavaFX- tai SWT-sovellus, joka on pakattu JAR-tiedostoksi
- **Webswing-palvelin**: lataa [webswing.org](https://webswing.org)
- **webforJ versio `25.10` tai uudempi**: vaaditaan `WebswingConnector`-tuesta

## Arkkitehtuurin yleiskatsaus {#architecture-overview}

Integrointiarkkitehtuuri koostuu:

1. **Webswing-palvelin**: ajaa Swing-sovellustasi, tallentaa GUI:n renderoinnin ja käsittelee käyttäjän syötteen
2. **webforJ-sovellus**: isännöi verkkosovellustasi upotetun `WebswingConnector`:in kanssa
3. **Selaimen asiakas**: näyttää sekä webforJ-käyttöliittymän että upotetun Swing-sovelluksen

:::important Porttien konfigurointi
Webswingin ja webforJ:n on toimittava eri porteilla konfliktien välttämiseksi. Molemmat webforJ ja Webswing toimivat tyypillisesti portilla `8080`. Sinun on vaihdettava joko Webswingin portti tai webforJ:n portti.
:::

## Webswing-palvelimen asennus {#webswing-server-setup}

### Asennus ja käynnistys {#installation-and-startup}

1. **Lataa Webswing** [viralliselta verkkosivustolta](https://www.webswing.org/en/downloads)
2. **Pura arkisto** haluamaasi sijaintiin (esim. `/opt/webswing` tai `C:\webswing`)
3. **Käynnistä palvelin** käyttöjärjestelmäkohtaisilla skripteillä:

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

Kun palvelin on käynnissä, siirry hallintapaneeliin osoitteessa `http://localhost:8080/admin` lisätäksesi ja konfiguroidaksesi Swing-sovelluksesi.

Hallintapaneelissa määrittele:

- **Sovelluksen nimi**: tulee osaksi URL-osoitetta (esim. `myapp` → `http://localhost:8080/myapp/`)
- **Pääluokka**: Swing-sovelluksesi pääsylkä
- **Classpath**: polku sovelluksen JAR:iin ja riippuvuuksiin
- **JVM-argumentit**: muistiasetukset, järjestelmäominaisuudet ja muut JVM-vaihtoehdot
- **Kotihakemisto**: sovelluksen työskentelyhakemisto

Konfiguraation jälkeen Swing-sovelluksesi on käytettävissä osoitteessa `http://localhost:8080/[app-name]/`

### CORS-konfigurointi {#cors-configuration}

Kun upotat Webswingin webforJ-sovellukseen, joka toimii eri portilla tai verkkotunnuksessa, sinun on konfiguroitava Cross-Origin Resource Sharing (CORS) Webswingissä. Tämä mahdollistaa selaimen ladata Webswing-sisältöä webforJ-sivultasi.

Webswingin hallintapaneelissa siirry sovelluksesi konfigurointiin ja aseta:

- **Sallitut alkuperät**: Lisää webforJ-sovelluksesi alkuperä (esim. `http://localhost:8090` tai `*` kehitystä varten)

Tämä asetus vastaa `allowedCorsOrigins`-vaihtoehtoa Webswingin sovelluksen konfiguraatiossa.


## webforJ-integrointi {#webforj-integration}

Kun Webswing-palvelimesi toimii Swing-sovelluksesi kanssa konfiguroituna ja CORS aktivoitu, voit integroida sen webforJ-sovellukseesi.

### Lisää riippuvuus {#add-dependency}

Webswingin integrointi riippuu webforJ:n Webswing-integrointimoduulista, joka tarjoaa `WebswingConnector`-komponentin ja siihen liittyvät luokat.
Lisää seuraava `pom.xml`-tiedostoosi:

```xml
<dependency>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-webswing</artifactId>
  <version>${webforj.version}</version>
</dependency>
```

### Perusimplementaatio {#basic-implementation}

Luo näkymä, joka upottaa Swing-sovelluksesi käyttäen `WebswingConnector`:a:

```java title="SwingAppView.java"
package com.example.views;

import com.webforj.annotation.Route;
import com.webforj.component.Composite;
import com.webforj.component.html.elements.Div;
import com.webforj.component.webswing.WebswingConnector;

@Route
public class SwingAppView extends Composite<Div> {
  private final Div self = getBoundComponent();
  private WebswingConnector connector;

  public SwingAppView() {
    // Alusta liitin Webswing-sovelluksesi URL-osoitteella
    connector = new WebswingConnector("http://localhost:8080/myapp/");

    // Aseta näyttömitat
    connector.setSize("100%", "600px");

    // Lisää näkymäastiassa
    self.add(connector);
  }
}
```

Liitin luo automaattisesti yhteyden Webswing-palvelimeen, kun se lisätään DOM:iin. Swing-sovelluksen käyttöliittymä renderöidään sitten liitin-komponentissa.

## Konfigurointivaihtoehdot {#configuration-options}

`WebswingOptions`-luokka mahdollistaa liittimen käyttäytymisen muokkaamisen. Oletusarvoisesti liitin käynnistyy automaattisesti, kun se luodaan, ja käyttää vakioyhteysasetuksia. Voit muuttaa tätä käyttäytymistä luomalla `WebswingOptions`-instanssin ja soveltamalla sitä liittimeen.

Esimerkiksi, jos haluat piilottaa kirjautumispainikkeen tuotantoympäristössä, jossa hallitset todennusta webforJ-sovelluksesi kautta:

```java
WebswingConnector connector = new WebswingConnector("http://localhost:8080/myapp/");

WebswingOptions options = new WebswingOptions()
  .setDisableLogout(true);  // Piilota kirjautumispainike

connector.setOptions(options);
```

Tai jos tarvitset manuaalista kontrollia siitä, milloin yhteys käynnistyy:

```java
// Luo liitin ilman automaattista käynnistystä
WebswingConnector connector = new WebswingConnector(url, false);

// Konfiguroi ja käynnistä, kun olet valmis
WebswingOptions options = new WebswingOptions();
connector.setOptions(options);
connector.start();
```

Vaihtoehdot kattavat yhteydenhallinnan, todennuksen, virheenkorjauksen ja valvonnan.
