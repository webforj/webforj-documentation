---
sidebar_position: 50
title: Deploying Additional Servlets
sidebar_class_name: new-content
_i18n_hash: 0717fa071511a4ca3b71dcf0592146e7
---
<!-- vale off -->
# Lisäservlettien käyttöönotto <DocChip chip='since' label='25.02' />
<!-- vale on -->

webforJ ohjaa kaikki pyynnöt `WebforjServlet`:in kautta, joka on oletusarvoisesti määritetty `/*` web.xml-tiedostossa. Tämä servletti hallitsee komponenttien elinkaaria, reititystä ja käyttöliittymän päivityksiä, jotka tukevat webforJ-sovellustasi.

Tietyissä tilanteissa saatat tarvita lisäservlettejä webforJ-sovelluksesi rinnalle:
- Kolmansien osapuolten kirjastojen integroiminen, jotka tarjoavat omat servlettinsä
- REST-rajapintojen tai webhooksien toteuttaminen
- Tiedostojen latausten käsittely räätälöidyllä käsittelyllä
- Perinteisten servlettipohjaisten koodien tukeminen

webforJ tarjoaa kaksi lähestymistapaa mukautettujen servletien käyttöönottoon sovelluksesi rinnalle:

## Lähestymistapa 1: `WebforjServlet` -palvelun reitittäminen {#approach-1-remapping-webforjservlet}

Tässä lähestymistavassa `WebforjServlet` reititetään pois `/*` tietylle polulle, kuten `/ui/*`, vapauttaen URL-tilan mukautetuille servleteille. Vaikka tämä vaatii `web.xml`-tiedoston muokkaamista, se antaa mukautetuille servleteille suoran pääsyn niiden URL-malleihin ilman mitään proxy-yhteyden overheadia.

```xml
<web-app>
  <!-- WebforjServlet reititetään käsittelemään vain /ui/* -->
  <servlet>
    <servlet-name>WebforjServlet</servlet-name>
    <servlet-class>com.webforj.servlet.WebforjServlet</servlet-class>
    <load-on-startup>1</load-on-startup>
  </servlet>
  <servlet-mapping>
    <servlet-name>WebforjServlet</servlet-name>
    <url-pattern>/ui/*</url-pattern>
  </servlet-mapping>
  
  <!-- Mukautettu servletti, jolla on oma URL-malli -->
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

Tällä kokoonpanolla:
- webforJ-komponentit ovat saatavilla `/ui/`-polussa
- Mukautettu servletti käsittelee pyyntöjä `/hello-world`
- Ei proxy-mekanismia - suora servlet-kontainerin reititys

:::tip Spring Boot -kokoonpano
Kun käytät webforJ:ta yhdessä Spring Bootin kanssa, `web.xml`-tiedostoa ei ole. Sen sijaan määritä servletin reititys `application.properties`-tiedostossa:

```Ini
webforj.servlet-mapping=/ui/*
```

Tämä ominaisuus reitittää `WebforjServlet`:in oletusarvoisesta `/*`-polusta `/ui/*`-polkuun, vapauttaen URL-tilan mukautetuille servletillesi. Älä sisällytä lainausmerkkejä arvon ympärille - ne tulkitaan osaksi URL-mallia.
:::

## Lähestymistapa 2: `WebforjServlet` -proxy-kokoonpano {#approach-2-webforjservlet-proxy-configuration}

Tässä lähestymistavassa `WebforjServlet` pidetään polussa `/*` ja mukautettuja servlettejä määritetään `webforJ.conf`-tiedostossa. `WebforjServlet` keskeyttää kaikki pyynnöt ja välittää vastaavat mallit mukautetuille servletillesi.

### Standardin web.xml -kokoonpano {#standard-webxml-configuration}

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

<!-- Mukautettu servletti, jolla on oma URL-malli -->
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

### webforJ.conf -kokoonpano {#webforjconf-configuration}

```hocon
servlets = [
  {
    name = "hello-world"
    class = "com.example.HelloWorldServlet"
  }
]
```

Tämän kokoonpanon avulla:
- `WebforjServlet` käsittelee kaikki pyynnöt
- Pyyntöjä `/hello-world` välitetään `HelloWorldServlet`:iin
