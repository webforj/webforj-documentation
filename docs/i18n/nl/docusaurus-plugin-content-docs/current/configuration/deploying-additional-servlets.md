---
sidebar_position: 25
title: Deploying Additional Servlets
description: >-
  Host REST endpoints and third-party servlets alongside a webforJ app by
  remapping WebforjServlet or proxying through webforj.conf.
_i18n_hash: 1e25ddc6c7e56063c26b9f911c0be5d2
---
<!-- vale off -->
# Extra Servlets Implementeren <DocChip chip='since' label='25.02' />
<!-- vale on -->

webforJ leidt alle verzoeken standaard via `WebforjServlet`, dat in web.xml is gemapt naar `/*`. Deze servlet beheert de componentlevenscyclus, routing en UI-updates die je webforJ-app aandrijven.

In sommige scenario's heb je mogelijk behoefte aan het implementeren van extra servlets naast je webforJ-app:
- Integreren van externe bibliotheken die hun eigen servlets bieden
- Implementeren van REST API's of webhooks
- Verwerken van bestanduploads met aangepaste verwerking
- Ondersteunen van legacy servlet-gebaseerde code

webforJ biedt twee benaderingen voor het implementeren van aangepaste servlets naast je app:

<AISkillTip skill="webforj-adding-servlets" />

## Benadering 1: Remappen van `WebforjServlet` {#approach-1-remapping-webforjservlet}

Deze benadering remapt de `WebforjServlet` van `/*` naar een specifieke pad zoals `/ui/*`, waardoor de URL-namespace vrij komt voor aangepaste servlets. Hoewel dit het aanpassen van `web.xml` vereist, geeft het aangepaste servlets directe toegang tot hun URL-patronen zonder enige proxy-overhead.

```xml
<web-app>
  <!-- WebforjServlet geremappt om alleen /ui/* te verwerken -->
  <servlet>
    <servlet-name>WebforjServlet</servlet-name>
    <servlet-class>com.webforj.servlet.WebforjServlet</servlet-class>
    <load-on-startup>1</load-on-startup>
  </servlet>
  <servlet-mapping>
    <servlet-name>WebforjServlet</servlet-name>
    <url-pattern>/ui/*</url-pattern>
  </servlet-mapping>

  <!-- Aangepaste servlet met zijn eigen URL-patroon -->
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

Met deze configuratie:
- webforJ-componenten zijn toegankelijk onder `/ui/`
- Aangepaste servlet verwerkt verzoeken naar `/hello-world`
- Geen proxymechanisme betrokken - directe routing in de servlet-container

:::tip Spring Boot configuratie
Wanneer je webforJ gebruikt met Spring Boot, is er geen `web.xml`-bestand. In plaats daarvan configureer je de servletmapping in `application.properties`:

```Ini
webforj.servlet-mapping=/ui/*
```

Deze eigenschap remapt `WebforjServlet` van de standaard `/*` naar `/ui/*`, waardoor de URL-namespace vrijkomt voor je aangepaste servlets. Voeg geen aanhalingstekens rond de waarde toe - deze zullen worden geïnterpreteerd als onderdeel van het URL-patroon.
:::

## Benadering 2: Proxy-configuratie voor `WebforjServlet` {#approach-2-webforjservlet-proxy-configuration}

Deze benadering houdt `WebforjServlet` op `/*` en configureert aangepaste servlets in `webforj.conf`. De `WebforjServlet` onderschept alle verzoeken en proxy's overeenkomende patronen naar je aangepaste servlets.

### Standaard web.xml-configuratie {#standard-webxml-configuration}

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

<!-- Aangepaste servlet met zijn eigen URL-patroon -->
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

### webforJ.conf-configuratie {#webforjconf-configuration}

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

Met deze configuratie:
- `WebforjServlet` verwerkt alle verzoeken
- Verzoeken naar `/hello-world` worden geproxy'd naar `HelloWorldServlet`
- De optionele `config`-sleutel biedt naam/waarde-paren als initiële parameters voor de servlet
