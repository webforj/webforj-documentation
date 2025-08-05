---
sidebar_position: 50
title: Deploying Additional Servlets
sidebar_class_name: new-content
_i18n_hash: 4506bcc85ddfa8698f4f8138fe6b4e33
---
<!-- vale off -->
# Extra Servlets Implementeren <DocChip chip='since' label='25.02' />
<!-- vale on -->

webforJ leidt alle verzoeken via `WebforjServlet`, dat standaard is gemapped naar `/*` in web.xml. Deze servlet beheert de componentlevenscyclus, routering en UI-updates die uw webforJ-app aandrijven.

In sommige scenario's moet u mogelijk extra servlets implementeren naast uw webforJ-app:
- Integreren van externe bibliotheken die hun eigen servlets bieden
- Implementeren van REST API's of webhooks
- Behandelen van bestandsuploads met aangepaste verwerking
- Ondersteuning van legacy servlet-gebaseerde code

webforJ biedt twee benaderingen voor het implementeren van aangepaste servlets naast uw app:

## Benadering 1: `WebforjServlet` opnieuw toewijzen {#approach-1-remapping-webforjservlet}

Deze benadering wijst de `WebforjServlet` opnieuw toe van `/*` naar een specifieke pad zoals `/ui/*`, waardoor de URL-namespace vrij komt voor aangepaste servlets. Hoewel dit het wijzigen van `web.xml` vereist, geeft het aangepaste servlets directe toegang tot hun URL-patronen zonder enige proxy-overhead.

```xml
<web-app>
  <!-- WebforjServlet opnieuw toegewezen om alleen /ui/* te behandelen -->
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
- Aangepaste servlet behandelt verzoeken naar `/hello-world`
- Geen proxymechanisme betrokken - directe routering naar de servletcontainer

:::tip Spring Boot configuratie
Wanneer u webforJ gebruikt met Spring Boot, is er geen `web.xml`-bestand. Configureer in plaats daarvan de servlet-mapping in `application.properties`:

```Ini
webforj.servlet-mapping=/ui/*
```

Deze eigenschap wijst `WebforjServlet` opnieuw toe van de standaard `/*` naar `/ui/*`, waardoor de URL-namespace vrij komt voor uw aangepaste servlets. Voeg geen aanhalingstekens rond de waarde toe - deze worden geïnterpreteerd als onderdeel van het URL-patroon.
:::

## Benadering 2: `WebforjServlet` proxyconfiguratie {#approach-2-webforjservlet-proxy-configuration}

Deze benadering houdt `WebforjServlet` op `/*` en configureert aangepaste servlets in `webforJ.conf`. De `WebforjServlet` onderschept alle verzoeken en proxy't overeenkomende patronen naar uw aangepaste servlets.

### Standaard web.xml configuratie {#standard-webxml-configuration}

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

### webforJ.conf configuratie {#webforjconf-configuration}

```hocon
servlets = [
  {
    name = "hello-world"
    class = "com.example.HelloWorldServlet"
  }
]
```

Met deze configuratie:
- `WebforjServlet` behandelt alle verzoeken
- Verzoeken naar `/hello-world` worden geproxy't naar `HelloWorldServlet`
