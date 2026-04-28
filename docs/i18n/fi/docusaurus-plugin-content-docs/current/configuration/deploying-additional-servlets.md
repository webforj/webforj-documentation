---
sidebar_position: 35
title: Deploying Additional Servlets
_i18n_hash: e7f1a0bcf3986ff50dcfd89281ab3339
---
<!-- vale off -->
# Lisäservlettien käyttöönotto <DocChip chip='since' label='25.02' />
<!-- vale on -->

webforJ reitittää kaikki pyynnöt `WebforjServletin` kautta, joka on oletuksena kartoitettu `/*` web.xml:ssä. Tämä servletti hallitsee komponenttien elinkaarta, reititystä ja käyttöliittymän päivityksiä, jotka voimaannuttavat webforJ-sovellustasi.

Joissakin skenaarioissa saatat tarvita lisäservlettejä webforJ-sovelluksen rinnalle:
- Kolmansien osapuolien kirjastojen integroiminen, jotka tarjoavat omia servlettejä
- REST API:en tai webhookien toteuttaminen
- Tiedostojen latausten käsittely mukautetulla käsittelyllä
- Legacy-servlet-pohjaisen koodin tukeminen

webforJ tarjoaa kaksi lähestymistapaa mukautettujen servlettien käyttöönottoon sovelluksen rinnalla:

<AISkillTip skill="webforj-adding-servlets" />

## Lähestymistapa 1: `WebforjServletin` uudelleenreititys {#approach-1-remapping-webforjservlet}

Tässä lähestymistavassa `WebforjServlet` uudelleenreititetään `/*` -polusta tiettyyn polkuun, kuten `/ui/*`, vapauttaen URL-nimensivälin mukautetuille servleteille. Vaikka tämä vaatii `web.xml`:n muokkaamista, se antaa mukautetuille servleteille suoran pääsyn omiin URL-malleihinsa ilman mitään välikäsiä.

```xml
<web-app>
  <!-- WebforjServlet on uudelleenreititetty käsittelemään vain /ui/* -->
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
- Mukautettu servletti käsittelee pyyntöjä `/hello-world`
- Ei välikäsiä - suora servlettikonttorin reititys

:::tip Spring Boot -konfiguraatio
Kun käytät webforJ:ta Spring Bootin kanssa, ei ole `web.xml` -tiedostoa. Sen sijaan konfiguroi servlettireititys `application.properties`-tiedostossa:

```Ini
webforj.servlet-mapping=/ui/*
```

Tämä ominaisuus uudelleenreitittää `WebforjServletin` oletus `/*` -sarjasta `/ui/*` -sarjaan, vapauttaen URL-nimensivälin mukautetuille servleteille. Älä sisällytä lainausmerkkejä arvon ympärille - niitä tulkitaan osana URL-mallia.
:::

## Lähestymistapa 2: `WebforjServletin` proxy-konfiguraatio {#approach-2-webforjservlet-proxy-configuration}

Tässä lähestymistavassa `WebforjServlet` pysyy `/*` -polussa ja mukautetut servleteet konfiguroidaan `webforj.conf`:ssa. `WebforjServlet` keskeyttää kaikki pyynnöt ja proxytoi vastaavat mallit mukautetuille servleteille.

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

Tällä konfiguraatiolla:
- `WebforjServlet` käsittelee kaikki pyynnöt
- Pyyntöjä `/hello-world` proksataan `HelloWorldServletille`
- Valinnainen `config` -avain tarjoaa nimi/arvo-pareja servletin alustamisparametreiksi
