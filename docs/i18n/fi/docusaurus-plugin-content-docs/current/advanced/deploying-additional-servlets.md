---
sidebar_position: 50
title: Deploying Additional Servlets
sidebar_class_name: new-content
_i18n_hash: 4506bcc85ddfa8698f4f8138fe6b4e33
---
<!-- vale off -->
# Lisäservlettien käyttöönotto <DocChip chip='since' label='25.02' />
<!-- vale on -->

webforJ reitittää kaikki pyynnöt `WebforjServlet`-palvelimen kautta, joka on oletuksena määritetty `/*` web.xml:ssä. Tämä servlet hallitsee komponenttien elinkaaren, reitityksen ja käyttöliittymän päivitykset, jotka voimauttavat webforJ-sovelluksesi.

Joissakin skenaarioissa saatat tarvita lisäservlettejä yhdessä webforJ-sovelluksesi kanssa:
- Kolmansien osapuolien kirjastojen integroiminen, jotka tarjoavat omat servletit
- REST API:en tai webhookien toteuttaminen
- Tiedostojen lataaminen mukautetulla käsittelyllä
- Perinteisen servlet-pohjaisen koodin tukeminen

webforJ tarjoaa kaksi lähestymistapaa mukautettujen servletien käyttöönottoon sovelluksesi rinnalla:

## Lähestymistapa 1: `WebforjServlet`-reitityksen muuttaminen {#approach-1-remapping-webforjservlet}

Tämä lähestymistapa muuttaa `WebforjServlet`-palvelimen reititysohjeen `/*`:sta tiettyyn polkuun, kuten `/ui/*`, vapauttaen URL-tilan mukautetuille servletteille. Vaikka tämä vaatii `web.xml`:n muokkaamista, se antaa mukautetuille servletteille suoran pääsyn niiden URL-kuvioihin ilman mitään välitysjärjestelmän viivettä.

```xml
<web-app>
  <!-- WebforjServlet uudelleenreitetty vain /ui/* käsittelemään -->
  <servlet>
    <servlet-name>WebforjServlet</servlet-name>
    <servlet-class>com.webforj.servlet.WebforjServlet</servlet-class>
    <load-on-startup>1</load-on-startup>
  </servlet>
  <servlet-mapping>
    <servlet-name>WebforjServlet</servlet-name>
    <url-pattern>/ui/*</url-pattern>
  </servlet-mapping>
  
  <!-- Mukautettu servlet sen omalla URL-kuviolla -->
  <servlet>
    <servlet-name>HelloWorldServlet</servlet-name>
    <servlet-class>com.example.HelloWorldServlet</servlet-class>
  </servlet>
  <servlet-mapping>
    <servlet-name>HelloWorldServlet</servlet-name>
    <url-pattern>/hello-world</url-pattern>
  </servlet-mapping>
</web-app>
```

Tämän konfiguraation myötä:
- webforJ-komponentit ovat saavutettavissa `/ui/`-polkujen alla
- Mukautettu servlet käsittelee pyyntöjä `/hello-world`
- Ei käytetä mitään välitysmekanismia - suora reititys servlet-kontissa

:::tip Spring Boot -konfiguraatio
Kun käytät webforJ:ta Spring Bootin kanssa, `web.xml`-tiedostoa ei ole. Sen sijaan määritä servletin reititys `application.properties`-tiedostossa:

```Ini
webforj.servlet-mapping=/ui/*
```

Tämä ominaisuus muuttaa `WebforjServlet`-palvelimen oletusarvoisesta `/*`:sta `/ui/*`:een, vapauttaen URL-tilan mukautetuille servletillesi. Älä käytä lainausmerkkejä arvon ympärillä - niitä tulkitaan osana URL-kuviota.
:::

## Lähestymistapa 2: `WebforjServlet`-välityskonfiguraatio {#approach-2-webforjservlet-proxy-configuration}

Tämä lähestymistapa pitää `WebforjServlet`-palvelimen `/*`-paikassa ja määrittää mukautetut servletit `webforJ.conf`-tiedostossa. `WebforjServlet` sieppaa kaikki pyynnöt ja välittää vastaavat kaaviot mukautetuille servletillesi.

### Standardi web.xml -konfiguraatio {#standard-webxml-configuration}

```xml
<servlet>
  <servlet-name>WebforjServlet</servlet-name>
  <servlet-class>com.webforj.servlet.WebforjServlet</servlet-class>
  <load-on-startup>1</load-on-startup>
</servlet>
<servlet-mapping>
  <servlet-name>WebforjServlet</servlet-name>
  <url-pattern>/*</url-pattern>
</servlet-mapping>

<!-- Mukautettu servlet sen omalla URL-kuviolla -->
<servlet>
  <servlet-name>HelloWorldServlet</servlet-name>
  <servlet-class>com.example.HelloWorldServlet</servlet-class>
</servlet>
<servlet-mapping>
  <servlet-name>HelloWorldServlet</servlet-name>
  <url-pattern>/hello-world</url-pattern>
</servlet-mapping>
</web-app>
```

### webforJ.conf -konfiguraatio {#webforjconf-configuration}

```hocon
servlets = [
  {
    name = "hello-world"
    class = "com.example.HelloWorldServlet"
  }
]
```

Tämän konfiguraation myötä:
- `WebforjServlet` käsittelee kaikki pyynnöt
- Pyyntöjä `/hello-world` ohjataan `HelloWorldServlet`:iin
