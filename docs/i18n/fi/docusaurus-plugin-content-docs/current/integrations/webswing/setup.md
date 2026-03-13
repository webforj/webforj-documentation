---
title: Setup and Configuration
sidebar_position: 2
_i18n_hash: cd2108b15ca8583794ddb366329c3638
---
Webswingin integroiminen webforJ:n kanssa koostuu kahdesta komponenteista: Webswing-palvelimesta, joka isännöi Swing-sovellustasi, ja `WebswingConnector`-komponentista webforJ-sovelluksessasi, joka upottaa sen.

## Esivaatimukset {#prerequisites}

Ennen kuin aloitat, varmista, että sinulla on seuraavat esivaatimukset:

- **Java-desktop-sovellus**: Swing-, JavaFX- tai SWT-sovellus, joka on pakattu JAR-tiedostoksi
- **Webswing-palvelin**: lataa osoitteesta [webswing.org](https://webswing.org)
- **webforJ-versio `25.10` tai uudempi**: vaaditaan `WebswingConnector`-tuesta

## Arkkitehtuuri yleiskatsaus {#architecture-overview}

Integrointiarkkitehtuuri koostuu:

1. **Webswing-palvelin**: ajaa Swing-sovellustasi, kaappaa käyttöliittymän renderöinnin ja käsittelee käyttäjäsyötteen
2. **webforJ-sovellus**: isännöi web-sovellustasi upotetulla `WebswingConnector`-komponentilla
3. **Selaimen asiakas**: näyttää sekä webforJ-käyttöliittymän että upotetun Swing-sovelluksen

:::important Portin kokoonpano
Webswingin ja webforJ:n on toimittava eri porteissa, jotta vältytään ristiriidoilta. Sekä webforJ että Webswing toimivat tyypillisesti portissa `8080`. Sinun tulisi muuttaa joko Webswingin tai webforJ:n portti.
:::

## Webswing-palvelimen asetus {#webswing-server-setup}

### Asennus ja käynnistys {#installation-and-startup}

1. **Lataa Webswing** [viralliselta verkkosivustolta](https://www.webswing.org/en/downloads)
2. **Pura arkisto** haluamaasi sijaintiin (esim. `/opt/webswing` tai `C:\webswing`)
3. **Käynnistä palvelin** käyttämällä alustoille spesifisiä skriptejä:

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

4. **Varmista, että palvelin on käynnissä** siirtymällä osoitteeseen `http://localhost:8080`

### Sovelluksen kokoonpano {#application-configuration}

Kun palvelin on käynnissä, siirry hallintakonsoliin osoitteessa `http://localhost:8080/admin` lisätäksesi ja konfiguroidaksesi Swing-sovelluksesi.

Hallintakonsolissa määritä:

- **Sovelluksen nimi**: tulee osaksi URL-polkurajaa (esim. `myapp` → `http://localhost:8080/myapp/`)
- **Pääluokka**: Swing-sovelluksesi aloituspiste
- **Luettelo polut**: polku sovelluksen JAR-tiedostoon ja riippuvuuksiin
- **JVM-argumentit**: muistiasetukset, järjestelmätiedot ja muut JVM-vaihtoehdot
- **Kotikansio**: sovelluksen työskentelyhakemisto

Konfiguroinnin jälkeen Swing-sovelluksesi on käytettävissä osoitteessa `http://localhost:8080/[app-name]/`

### CORS-kokoonpano {#cors-configuration}

Kun upotat Webswingin webforJ-sovellukseen, joka toimii eri portissa tai toimialueella, sinun on määritettävä Cross-Origin Resource Sharing (CORS) Webswingissä. Tämä mahdollistaa selaimen lataavan Webswing-sisältöä webforJ-sivustoltasi.

Webswingin hallintakonsolissa siirry sovelluksesi kokoonpanoon ja määritä:

- **Sallitut alkuperät**: Lisää webforJ-sovelluksesi alkuperä (esim. `http://localhost:8090` tai `*` kehitystä varten)

Tämä asetus vastaa Webswingin sovelluskokoonpanon `allowedCorsOrigins`-vaihtoehtoa.

## webforJ-integraatio {#webforj-integration}

Kun Webswing-palvelimesi on käynnissä ja Swing-sovelluksesi on määritetty ja CORS otettu käyttöön, voit integroida sen webforJ-sovellukseesi.

### Lisää riippuvuus {#add-dependency}

Webswing-integraatio riippuu webforJ:n Webswing-integraatiomoduulista, joka tarjoaa `WebswingConnector`-komponentin ja siihen liittyvät luokat. Lisää seuraava `pom.xml`-tiedostoosi:

```xml
<dependency>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-webswing</artifactId>
  <version>${webforj.version}</version>
</dependency>
```

### Perusimplementaatio {#basic-implementation}

Luo näkymä, joka upottaa Swing-sovelluksesi käyttäen `WebswingConnector`:ia:

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
    // Alusta yhdistin Webswing-sovelluksesi URL-osoitteella
    connector = new WebswingConnector("http://localhost:8080/myapp/");

    // Aseta näyttödimenensioita
    connector.setSize("100%", "600px");

    // Lisää näkymäkonttiin
    self.add(connector);
  }
}
```

Yhdistin muodostaa automaattisesti yhteyden Webswing-palvelimeen, kun se lisätään DOM:iin. Swing-sovelluksen käyttöliittymä renderöidään sen jälkeen yhdistin-komponenttiin.

## Kokoonpanoasetukset {#configuration-options}

`WebswingOptions`-luokka sallii sinun mukauttaa yhdistimen käyttäytymistä. Oletusarvoisesti yhdistin käynnistyy automaattisesti luodessaan ja käyttää vakioyhteysasetuksia. Voit muuttaa tätä käyttäytymistä luomalla `WebswingOptions`-instanssin ja soveltamalla sen yhdistimeen.

Esimerkiksi, jotta voit piilottaa uloskirjautumispainikkeen tuotantoympäristössä, jossa hallitset todennusta webforJ-sovelluksesi kautta:

```java
WebswingConnector connector = new WebswingConnector("http://localhost:8080/myapp/");

WebswingOptions options = new WebswingOptions()
    .setDisableLogout(true);  // Piilota uloskirjautumispainike

connector.setOptions(options);
```

Tai jos tarvitset manuaalista hallintaa siitä, milloin yhteys käynnistyy:

```java
// Luo yhdistin ilman automaattista käynnistystä
WebswingConnector connector = new WebswingConnector(url, false);

// Määritä ja käynnistä, kun olet valmis
WebswingOptions options = new WebswingOptions();
connector.setOptions(options);
connector.start();
```

Asetukset kattavat yhteyden hallinnan, todennuksen, virheiden seurannan ja valvonnan.
