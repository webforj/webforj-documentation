---
title: Setup and Configuration
sidebar_position: 2
_i18n_hash: e3af6f7983bbd6ed7db57428412466c8
---
Webswingin integrointi webforJ:n kanssa sisältää kaksi komponenttia: Webswing-palvelimen, joka isännöi Swing-sovellustasi, ja `WebswingConnector`-komponentin webforJ-sovelluksessa, joka upottaa sen.

## Ehdot {#prerequisites}

Ennen kuin aloitat, varmista, että sinulla on seuraavat vaatimukset:

- **Java-työpöytäsovellus**: Swing, JavaFX tai SWT -sovellus pakattuna JAR-tiedostoksi
- **Webswing-palvelin**: lataa sivustolta [webswing.org](https://webswing.org)
- **webforJ versio `25.10` tai uudempi**: vaaditaan `WebswingConnector`-tuennan vuoksi

## Arkkitehtuurin yleiskatsaus {#architecture-overview}

Integrointiarkkitehtuuri koostuu:

1. **Webswing-palvelin**: suorittaa Swing-sovellustasi, tallentaa GUI:n renderointia ja käsittelee käyttäjäsyötteitä
2. **webforJ-sovellus**: isännöi verkkosovellustasi upotetun `WebswingConnectorin` kanssa
3. **Selaimen asiakas**: näyttää sekä webforJ-käyttöliittymän että upotetun Swing-sovelluksen

:::important Porttikonfigurointi
Webswingin ja webforJ:n on toimittava eri porteissa konfliktien välttämiseksi. Sekä webforJ että Webswing toimivat tyypillisesti portissa `8080`. sinun tulee muuttaa joko Webswingin porttia tai webforJ:n porttia.
:::

## Webswing-palvelimen asetukset {#webswing-server-setup}

### Asennus ja käynnistys {#installation-and-startup}

1. **Lataa Webswing** [viralliselta verkkosivustolta](https://www.webswing.org/en/downloads)
2. **Purettava arkisto** haluamaasi sijaintiin (esim. `/opt/webswing` tai `C:\webswing`)
3. **Käynnistä palvelin** käyttöjärjestelmälähtöisillä skripteillä:

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

### Sovelluksen konfigurointi {#application-configuration}

Kun palvelin on käynnissä, siirry admin-konsoliin osoitteessa `http://localhost:8080/admin` lisätäksesi ja konfiguroidaksesi Swing-sovelluksesi.

Admin-konsolissa konfiguroi:

- **Sovelluksen nimi**: tulee osaksi URL-polku, (esim. `myapp` → `http://localhost:8080/myapp/`)
- **Pääluokka**: Swing-sovelluksesi sisäänkäyntipiste
- **Classpath**: polku sovelluksesi JARiin ja riippuvuuksiin
- **JVM-parametrit**: muistiasetukset, järjestelmäominaisuudet ja muut JVM-vaihtoehdot
- **Kotihakemisto**: työskentelyhakemisto sovellukselle

Konfiguroinnin jälkeen Swing-sovelluksesi on saatavilla osoitteessa `http://localhost:8080/[app-name]/`

### CORS-konfigurointi {#cors-configuration}

Kun upotat Webswingin webforJ-sovellukseen, joka toimii eri portissa tai domainissa, sinun on määritettävä Cross-Origin Resource Sharing (CORS) Webswingissä. Tämä mahdollistaa selaimen ladata Webswing-sisältöä webforJ-sivultasi.

Webswingin admin-konsolissa siirry sovelluksesi konfigurointiin ja aseta:

- **Sallitut alkuperät**: Lisää webforJ-sovelluksesi alkuperä (esim. `http://localhost:8090` tai `*` kehittämistä varten)

Tämä asetus vastaa Webswingin sovelluskonfiguraatiossa olevaa `allowedCorsOrigins`-vaihtoehtoa.

## webforJ-integraatio {#webforj-integration}

Kun Webswing-palvelimesi on käynnissä Swing-sovelluksesi kanssa konfiguroituna ja CORS aktivoituna, voit integroida sen webforJ-sovellukseesi.

### Lisää riippuvuus {#add-dependency}

Lisää Webswing-integraatiomoduuli webforJ-projektiisi. Tämä tarjoaa `WebswingConnector`-komponentin ja siihen liittyvät luokat.

```xml
<dependency>
    <groupId>com.webforj</groupId>
    <artifactId>webforj-webswing</artifactId>
</dependency>
```

### Perusimplementaatio {#basic-implementation}

Luo näkymä, joka upottaa Swing-sovelluksesi `WebswingConnector`in avulla:

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
    // Alusta yhdistin Webswing-sovelluksesi URL-osoitteella
    connector = new WebswingConnector("http://localhost:8080/myapp/");

    // Aseta näyttömitat
    connector.setSize("100%", "600px");

    // Lisää näkymän konttiin
    getBoundComponent().add(connector);
  }
}
```

Yhdistin luo automaattisesti yhteyden Webswing-palvelimeen, kun se lisätään DOM:iin. Swing-sovelluksen käyttöliittymä renderöidään sitten yhdistin-komponenttiin.

## Konfigurointivaihtoehdot {#configuration-options}

`WebswingOptions`-luokka mahdollistaa yhdistimen käyttäytymisen räätälöimisen. Oletuksena yhdistin käynnistyy automaattisesti luotaessa ja käyttää vakioyhteysasetuksia. Voit muuttaa tätä käyttäytymistä luomalla `WebswingOptions`-instanssin ja soveltamalla sitä yhdistimeen.

Esimerkiksi voidaksesi piilottaa kirjautumispainikkeen tuotantoympäristössä, jossa hallitset todennusta webforJ-sovelluksesi kautta:

```java
WebswingConnector connector = new WebswingConnector("http://localhost:8080/myapp/");

WebswingOptions options = new WebswingOptions()
    .setDisableLogout(true);  // Piilota kirjautumispainike

connector.setOptions(options);
```

Tai jos tarvitset manuaalista hallintaa siitä, milloin yhteys käynnistyy:

```java
// Luo yhdistin ilman automaattista käynnistystä
WebswingConnector connector = new WebswingConnector(url, false);

// Määritä ja käynnistä kun valmis
WebswingOptions options = new WebswingOptions();
connector.setOptions(options);
connector.start();
```

Asetukset kattavat yhteyden hallinnan, todennuksen, virheenkäsittelyn ja seurannan.
