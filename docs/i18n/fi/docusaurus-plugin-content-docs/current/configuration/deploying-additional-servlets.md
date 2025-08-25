---
sidebar_position: 35
title: Deploying Additional Servlets
sidebar_class_name: new-content
_i18n_hash: 0717fa071511a4ca3b71dcf0592146e7
---
<!-- vale off -->
# Lisäservlettien käyttöönotto <DocChip chip='since' label='25.02' />
<!-- vale on -->

webforJ reitittää kaikki pyynnöt `WebforjServlet`-palvelimen kautta, joka on oletuksena mappingattu `/*` web.xml-tiedostossa. Tämä palvelin hallitsee komponenttien elinkaarta, reititystä ja käyttöliittymän päivityksiä, jotka voimaantuvat webforJ-sovelluksessasi.

Joissakin tilanteissa saatat tarvita lisäservlettejä webforJ-sovelluksesi rinnalle:
- Kolmansien osapuolten kirjastojen integrointi, jotka tarjoavat omia servlettejä
- REST API:en tai webhookien toteuttaminen
- Tiedostojen lataaminen mukautetulla käsittelyllä
- Perinteisen servlet-pohjaisen koodin tukeminen

webforJ tarjoaa kaksi lähestymistapaa mukautettujen servlettien käyttöönottoon sovelluksesi rinnalla:

## Lähestymistapa 1: `WebforjServlet`-palvelimen uudelleenreittaus {#approach-1-remapping-webforjservlet}

Tämä lähestymistapa uudelleenreittaa `WebforjServlet`-palvelimen `/*`-reitistä tiettyyn polkuun, kuten `/ui/*`, vapauttaen URL-nimisavun mukautetuille servletteille. Vaikka tämä vaatii web.xml-tiedoston muokkaamista, se antaa mukautetuille servletteille suoran pääsyn URL-malleihinsa ilman välitysohjelman ylikuormitusta.

```xml
<web-app>
  <!-- WebforjServlet uudelleenreitattu käsittelemään vain /ui/* -->
  <servlet>
    <servlet-name>WebforjServlet</servlet-name>
    <servlet-class>com.webforj.servlet.WebforjServlet</servlet-class>
    <load-on-startup>1</load-on-startup>
  </servlet>
  <servlet-mapping>
    <servlet-name>WebforjServlet</servlet-name>
    <url-pattern>/ui/*</url-pattern>
  </servlet-mapping>
  
  <!-- Mukautettu servletti omalla URL-mallilla -->
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

Tällä konfiguraatiolla:
- webforJ-komponentit ovat käytettävissä osoitteessa `/ui/`
- Mukautettu servletti käsittelee pyyntöjä osoitteessa `/hello-world`
- Ei välitysohjelman mekaniikkaa - suora reititys servlet-konttiin

:::tip Spring Boot -konfiguraatio
Kun käytät webforJ:ta Spring Bootin kanssa, `web.xml`-tiedostoa ei ole. Sen sijaan, määritä servletin uudelleenreititys `application.properties`-tiedostossa:

```Ini
webforj.servlet-mapping=/ui/*
```

Tämä ominaisuus uudelleenreittaa `WebforjServlet` oletusmäärityksestä `/*` osoitteeseen `/ui/*`, vapauttaen URL-nimisavun mukautetuille servletteille. Älä sisällytä lainausmerkkejä arvon ympärille - niitä tulkitaan osaksi URL-mallia.
:::

## Lähestymistapa 2: `WebforjServlet`-palvelimen välitysohjelman konfigurointi {#approach-2-webforjservlet-proxy-configuration}

Tässä lähestymistavassa `WebforjServlet` pysyy `/*`-reitissä ja mukautetut servletit konfiguroidaan `webforJ.conf`-tiedostossa. `WebforjServlet` sieppaa kaikki pyynnöt ja välittää vastaavat mallit mukautetuille servletillesi.

### Vakiot web.xml-konfiguraatio {#standard-webxml-configuration}

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

<!-- Mukautettu servletti omalla URL-mallilla -->
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

Tällä konfiguraatiolla:
- `WebforjServlet` käsittelee kaikki pyynnöt
- Pyyntöjä osoitteeseen `/hello-world` välitetään `HelloWorldServlet`:ille
