---
sidebar_position: 35
title: Deploying Additional Servlets
_i18n_hash: e7f1a0bcf3986ff50dcfd89281ab3339
---
<!-- vale off -->
# Het implementeren van Extra Servlets <DocChip chip='since' label='25.02' />
<!-- vale on -->

webforJ leidt alle verzoeken via `WebforjServlet`, dat standaard is toegewezen aan `/*` in web.xml. Deze servlet beheert de levenscyclus van componenten, routering en UI-updates die uw webforJ-app aandrijven.

In sommige scenario's moet u mogelijk extra servlets implementeren naast uw webforJ-app:
- Integreren van externe bibliotheken die hun eigen servlets bieden
- Implementeren van REST-API's of webhooks
- Afhandelen van bestand uploads met aangepaste verwerking
- Ondersteunen van legacy servlet-gebaseerde code

webforJ biedt twee benaderingen voor het implementeren van aangepaste servlets naast uw app:

<AISkillTip skill="webforj-adding-servlets" />

## Benadering 1: Herverdeling van `WebforjServlet` {#approach-1-remapping-webforjservlet}

Deze benadering herverdeel de `WebforjServlet` van `/*` naar een specifieke pad zoals `/ui/*`, waardoor de URL-namespace vrijkomt voor aangepaste servlets. Hoewel dit vereist dat `web.xml` wordt gewijzigd, geeft het aangepaste servlets directe toegang tot hun URL-patronen zonder enige overhead van een proxy.

```xml
<web-app>
  <!-- WebforjServlet herverdeeld om alleen /ui/* af te handelen -->
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
- Geen proxymechanisme betrokken - directe routering door de servletcontainer

:::tip Spring Boot-configuratie
Bij gebruik van webforJ met Spring Boot, is er geen `web.xml`-bestand. Configureer in plaats daarvan de servlet-mapping in `application.properties`:

```Ini
webforj.servlet-mapping=/ui/*
```

Deze eigenschap herverdeelt `WebforjServlet` van de standaard `/*` naar `/ui/*`, waardoor de URL-namespace vrijkomt voor uw aangepaste servlets. Voeg geen aanhalingstekens rond de waarde toe - deze worden geïnterpreteerd als onderdeel van het URL-patroon.
:::

## Benadering 2: `WebforjServlet` proxyconfiguratie {#approach-2-webforjservlet-proxy-configuration}

Deze benadering houdt `WebforjServlet` op `/*` en configureert aangepaste servlets in `webforj.conf`. De `WebforjServlet` onderschept alle verzoeken en proxy's overeenkomstige patronen naar uw aangepaste servlets.

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
- `WebforjServlet` behandelt alle verzoeken
- Verzoeken naar `/hello-world` worden geproxied naar `HelloWorldServlet`
- De optionele `config`-sleutel biedt naam/waardeparen als initiële parameters voor de servlet
