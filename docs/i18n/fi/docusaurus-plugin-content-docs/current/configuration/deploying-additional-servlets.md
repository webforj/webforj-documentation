---
sidebar_position: 35
title: Deploying Additional Servlets
sidebar_class_name: new-content
_i18n_hash: 95695a68854d595e78a58904d7214208
---
<!-- vale off -->
# Lisäservlettien käyttöönotto <DocChip chip='since' label='25.02' />
<!-- vale on -->

webforJ ohjaa kaikki pyynnöt `WebforjServlet`:in kautta, joka on oletuksena määritetty `/*` web.xml-tiedostossa. Tämä servletti hallitsee komponenttien elinkaarta, reititystä ja käyttöliittymän päivityksiä, jotka tukevat webforJ-sovellustasi.

Tietyissä scenaarioissa saatat tarvita lisäservlettejä webforJ-sovelluksesi rinnalla:
- Kolmansien osapuolien kirjastojen integrointi, jotka tarjoavat omat servlettinsä
- REST API:en tai webhookkien toteuttaminen
- Tiedostojen latausten käsittely mukautetulla käsittelyllä
- Perinteisen servlet-pohjaisen koodin tukeminen

webforJ tarjoaa kaksi lähestymistapaa mukautettujen servlettien käyttöönottoon sovelluksesi rinnalla:

## Lähestymistapa 1: `WebforjServlet`:in uudelleenreittaus {#approach-1-remapping-webforjservlet}

Tässä lähestymistavassa `WebforjServlet` uudelleenreitetään `/*`:sta erityiseen polkuun, kuten `/ui/*`, vapauttaen URL-namespace mukautetuille servleteille. Vaikka tämä vaatii web.xml-tiedoston muokkaamista, se antaa mukautetuille servleteille suoran pääsyn niiden URL-malleihin ilman mitään proxy-ylikuormitusta.

```xml
<web-app>
  <!-- WebforjServlet uudelleenreitetty käsittelemään vain /ui/* -->
  <servlet>
    <servlet-name>WebforjServlet</servlet-name>
    <servlet-class>com.webforj.servlet.WebforjServlet</servlet-class>
    <load-on-startup>1</load-on-startup>
  </servlet>
  <servlet-mapping>
    <servlet-name>WebforjServlet</servlet-name>
    <url-pattern>/ui/*</url-pattern>
  </servlet-mapping>
  
  <!-- Mukautettu servletti omalla URL-mallillaan -->
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

Tällä asetuksella:
- webforJ-komponentit ovat saatavilla polulla `/ui/`
- Mukautettu servletti käsittelee pyyntöjä polkuun `/hello-world`
- Ei ole käytössä proxy-mekanismia - suora reititys servlet-kontissa

:::tip Spring Boot -konfiguraatio
Kun käytät webforJ:ta Spring Bootin kanssa, ei ole `web.xml`-tiedostoa. Sen sijaan määritä servletti-reititys `application.properties`-tiedostossa:

```Ini
webforj.servlet-mapping=/ui/*
```

Tämä ominaisuus uudelleenreittaa `WebforjServlet`:in oletus `/*`:sta `/ui/*`:een, vapauttaen URL-namespacen mukautetuille servleteille. Älä sisällytä lainausmerkkejä arvon ympärille - ne tulkitaan osaksi URL-mallia.
:::

## Lähestymistapa 2: `WebforjServlet`:in proxy-konfiguraatio {#approach-2-webforjservlet-proxy-configuration}

Tässä lähestymistavassa `WebforjServlet` pidetään kohdassa `/*` ja mukautetut servletit konfiguroidaan `webforj.conf`:issa. `WebforjServlet` sieppaa kaikki pyynnöt ja proxy-kohtaavat mallit mukautetuille servleteillesi.

### Vakio web.xml -konfiguraatio {#standard-webxml-configuration}

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

<!-- Mukautettu servletti omalla URL-mallillaan -->
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
servlets: [
  {
    class: "com.example.HelloWorldServlet",
    name: "hello-world",
    config: {
      foo: "bar",
      baz: "bang"
    }
  }
]
```

Tällä asetuksella:
- `WebforjServlet` käsittelee kaikki pyynnöt
- Pyyntöjä polkuun `/hello-world` ohjataan `HelloWorldServlet`:ille
- Valinnainen `config`-avain tarjoaa nimi/arvo-pareja servlettien alustamista varten
