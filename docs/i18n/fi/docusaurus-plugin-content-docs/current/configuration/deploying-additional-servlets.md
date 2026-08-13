---
sidebar_position: 25
title: Deploying Additional Servlets
description: >-
  Host REST endpoints and third-party servlets alongside a webforJ app by
  remapping WebforjServlet or proxying through webforj.conf.
_i18n_hash: 1e25ddc6c7e56063c26b9f911c0be5d2
---
<!-- vale off -->
# Lisäservlettien käyttöönotto <DocChip chip='since' label='25.02' />
<!-- vale on -->

webforJ ohjaa kaikki pyynnöt `WebforjServlet`in kautta, joka on oletuksena määritetty `/*` web.xml-tiedostossa. Tämä servletti hallitsee komponenttien elinkaaria, reititystä ja käyttöliittymän päivityksiä, jotka tukevat webforJ-sovellustasi.

Joissakin tapauksissa saatat tarvita lisäservlettejä webforJ-sovelluksesi rinnalle:
- Kolmannen osapuolen kirjastojen integroiminen, jotka tarjoavat omia servlettejä
- REST API:en tai webhookkien toteuttaminen
- Tiedostojen lataaminen mukautetulla käsittelyllä
- Perinteisen servlet-pohjaisen koodin tukeminen

webforJ tarjoaa kaksi lähestymistapaa mukautettujen servletien käyttöönottoon sovelluksesi rinnalle:

<AISkillTip skill="webforj-adding-servlets" />

## Lähestymistapa 1: `WebforjServlet` uudelleenmäärittäminen {#approach-1-remapping-webforjservlet}

Tässä lähestymistavassa `WebforjServlet` uudelleenmääritetään `/*`:sta tiettyyn polkuun, kuten `/ui/*`, mikä vapauttaa URL-tilan mukautetuille servletteille. Vaikka tämä vaatii `web.xml`-tiedoston muokkaamista, se antaa mukautetuille servletteille suoran pääsyn URL-malleihinsa ilman välitysohjelmien ylikuormitusta.

```xml
<web-app>
  <!-- WebforjServlet uudelleenmääritetty käsittelemään vain /ui/* -->
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

Tällä kokoonpanolla:
- webforJ-komponentit ovat saatavilla `/ui/`-polun alla
- Mukautettu servletti käsittelee pyyntöjä `/hello-world`
- Ei käytetä välitysohjelmamekanismia - suoraa reititystä servlet-kontissa

:::tip Spring Boot -kokoonpano
Kun käytät webforJ:ta Spring Bootin kanssa, `web.xml`-tiedostoa ei ole. Sen sijaan määritä servlettireititys `application.properties`-tiedostossa:

```Ini
webforj.servlet-mapping=/ui/*
```

Tämä ominaisuus uudelleenmäärittää `WebforjServlet`:n oletusarvoisesta `/*` `/ui/*`:ksi, vapauttaen URL-tilan mukautetuille servletillesi. Älä sisällytä lainausmerkkejä arvon ympärille - niitä tulkitaan osana URL-mallia.
:::

## Lähestymistapa 2: `WebforjServlet` välitysohjelman kokoonpano {#approach-2-webforjservlet-proxy-configuration}

Tässä lähestymistavassa `WebforjServlet` pidetään `/*`:ssa ja mukautettuja servlettejä määritellään `webforj.conf`:issa. `WebforjServlet` sieppaa kaikki pyynnöt ja välittää vastaavat mallit mukautetuille servletteille.

### Standardi web.xml -kokoonpano {#standard-webxml-configuration}

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

### webforJ.conf -kokoonpano {#webforjconf-configuration}

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

Tällä kokoonpanolla:
- `WebforjServlet` käsittelee kaikki pyynnöt
- Pyyntöjä `/hello-world` käsitellään `HelloWorldServlet`:lle
- Valinnainen `config`-avain antaa nimi/arvo-pareja servletin alustamisparametreiksi
