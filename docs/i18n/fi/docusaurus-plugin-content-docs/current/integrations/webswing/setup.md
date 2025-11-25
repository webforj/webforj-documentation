---
title: Asennus ja Konfigurointi
sidebar_position: 2
_i18n_hash: 5d819b2a84de98748b48e7b3b1c9ab66
---
Webswingin integrointi webforJ:llä sisältää kaksi komponenttia: Webswing-palvelimen, joka isännöi Swing-sovellustasi, ja `WebswingConnector`-komponentin webforJ-sovelluksessasi, joka upottaa sen.

## Esivaatimukset

Ennen kuin aloitat, varmista, että sinulla on seuraavat esivaatimukset:

- **Java-desktop-sovellus**: Swing-, JavaFX- tai SWT-sovellus, joka on pakattu JAR-tiedostoksi
- **Webswing-palvelin**: lataa [webswing.org](https://webswing.org) -sivustolta
- **webforJ versio `25.10` tai uudempi**: vaaditaan `WebswingConnector`-tuennan vuoksi

## Arkkitehtuurin yleiskatsaus

Integrointiarkkitehtuuri koostuu seuraavista osista:

1. **Webswing-palvelin**: ajaa Swing-sovellustasi, kaappaa GUI:n renderöinnin ja käsittelee käyttäjän syötteen
2. **webforJ-sovellus**: isännöi verkkosovellustasi upotetun `WebswingConnector`:in kanssa
3. **Selaimen asiakas**: näyttää sekä webforJ-käyttöliittymän että upotetun Swing-sovelluksen

:::important Porttikonfigurointi
Webswingin ja webforJ:n on toimittava eri porteilla ristiriitojen välttämiseksi. Sekä webforJ että Webswing toimivat tyypillisesti portissa `8080`. Sinun tulisi vaihtaa joko Webswingin portti tai webforJ:n portti.
:::

## Webswing-palvelimen asennus

### Asennus ja käynnistys

1. **Lataa Webswing** [viralliselta verkkosivustolta](https://www.webswing.org/en/downloads)
2. **Pura arkisto** haluamaasi sijaintiin (esim. `/opt/webswing` tai `C:\webswing`)
3. **Käynnistä palvelin** käyttämällä alustan spesifisiä skriptejä:

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

### Sovelluksen konfigurointi

Kun palvelin on käynnissä, pääset hallintakonsoliin osoitteessa `http://localhost:8080/admin` lisätäksesi ja konfiguroidaksesi Swing-sovelluksesi.

Hallintakonsolissa konfiguroi:

- **Sovelluksen nimi**: tulee osaksi URL-polku (esim. `myapp` → `http://localhost:8080/myapp/`)
- **Pääluokka**: Swing-sovelluksesi sisäänkäyntipiste
- **Classpath**: polku sovelluksen JAR:lle ja riippuvuuksille
- **JVM-argumentit**: muistin asetukset, järjestelmän ominaisuudet ja muut JVM-vaihtoehdot
- **Kotihakemisto**: sovelluksen työskentelyhakemisto

Konfiguroinnin jälkeen Swing-sovelluksesi on käytettävissä osoitteessa `http://localhost:8080/[app-name]/`

## webforJ-integrointi

Kun Webswing-palvelin on käynnissä Swing-sovelluksesi konfiguroimisen kanssa, voit integroida sen webforJ-sovellukseesi. Tämä sisältää riippuvuuden lisäämisen, Cross-Origin Resource Sharing (CORS) -konfiguroinnin ja näkymän luomisen `WebswingConnector`-komponentilla.

### Lisää riippuvuus

Lisää Webswing-integraatiomodule webforJ-projektiisi. Tämä tarjoaa `WebswingConnector`-komponentin ja siihen liittyvät luokat.

```xml
<dependency>
    <groupId>com.webforj</groupId>
    <artifactId>webforj-webswing</artifactId>
</dependency>
```

### CORS-konfigurointi

Cross-Origin Resource Sharing (CORS) -konfigurointi on tarpeen, kun Webswing ja webforJ toimivat eri porteilla tai domaineilla. Selaimen oman alkuperän politiikka estää pyyntöjä eri alkuperien välillä ilman asianmukaisia CORS-otsikoita.

Luo servlet-suodatin lisätäksesi CORS-otsikot webforJ-sovellukseesi:

```java title="CorsFilter.java"
package com.example.config;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletResponse;

public class CorsFilter implements Filter {

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
    // pass
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    if (response instanceof HttpServletResponse) {
      HttpServletResponse httpResponse = (HttpServletResponse) response;
      httpResponse.setHeader("Access-Control-Allow-Origin", "*");
      httpResponse.setHeader("Access-Control-Allow-Methods",
          "GET, POST, PUT, DELETE, OPTIONS, HEAD");
      httpResponse.setHeader("Access-Control-Allow-Headers",
          "Content-Type, Authorization, X-Requested-With");
      httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
    }

    chain.doFilter(request, response);
  }

  @Override
  public void destroy() {
    // pass
  }
}
```

Rekisteröi suodatin `web.xml`-tiedostossasi:

```xml
<filter>
  <filter-name>CorsFilter</filter-name>
  <filter-class>com.example.config.CorsFilter</filter-class>
</filter>

<filter-mapping>
  <filter-name>CorsFilter</filter-name>
  <url-pattern>/*</url-pattern>
</filter-mapping>
```

:::important Pääsy tuotantoympäristöissä
Tuotantoympäristöjä varten vaihda jokerimerkki (`*`) `Access-Control-Allow-Origin` -otsikossa erityiseen Webswing-palvelimesi URL-osoitteeseen turvallisuuden vuoksi.
:::

### Perusimplementaatio

Luo näkymä, joka upottaa Swing-sovelluksesi käyttäen `WebswingConnector`:ia:

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
    // Alusta liitin Webswing-sovelluksesi URL-osoitteella
    connector = new WebswingConnector("http://localhost:8080/myapp/");

    // Aseta näyttömitat
    connector.setSize("100%", "600px");

    // Lisää näkymän konttiin
    getBoundComponent().add(connector);
  }
}
```

Liitin luo automaattisesti yhteyden Webswing-palvelimeen, kun se lisätään DOM:iin. Swing-sovelluksen käyttöliittymä renderöidään sitten liitin-komponentissa.

## Konfigurointivaihtoehdot

`WebswingOptions`-luokka antaa sinun mukauttaa liittimen käyttäytymistä. Oletuksena liitin käynnistyy automaattisesti luotuna ja käyttää vakioa yhteysasetuksia. Voit muuttaa tätä käyttäytymistä luomalla `WebswingOptions`-instanssin ja soveltamalla sitä liittimeen.

Esimerkiksi, jos haluat piilottaa kirjautumispainikkeen tuotantoympäristössä, jossa hallitset todennusta webforJ-sovelluksesi kautta:

```java
WebswingConnector connector = new WebswingConnector("http://localhost:8080/myapp/");

WebswingOptions options = new WebswingOptions()
    .setDisableLogout(true);  // Piilota kirjautumispainike

connector.setOptions(options);
```

Tai jos tarvitset manuaalista hallintaa siitä, milloin yhteys käynnistyy:

```java
// Luo liitin ilman automaattista käynnistystä
WebswingConnector connector = new WebswingConnector(url, false);

// Määrittele ja käynnistä kun olet valmis
WebswingOptions options = new WebswingOptions();
connector.setOptions(options);
connector.start();
```

Vaihtoehdot kattavat yhteyden hallinnan, todennuksen, vianetsinnän ja seurannan.
