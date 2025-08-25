---
sidebar_position: 35
title: Deploying Additional Servlets
sidebar_class_name: new-content
_i18n_hash: 0717fa071511a4ca3b71dcf0592146e7
---
<!-- vale off -->
# Extra Servlets Implementeren <DocChip chip='since' label='25.02' />
<!-- vale on -->

webforJ routeert alle verzoeken via `WebforjServlet`, dat standaard aan `/*` in web.xml is gekoppeld. Deze servlet beheert de levenscyclus van componenten, routing en UI-updates die je webforJ-app aandrijven.

In sommige gevallen moet je mogelijk extra servlets naast je webforJ-app implementeren:
- Integratie van derden bibliotheken die hun eigen servlets bieden
- Implementatie van REST API's of webhooks
- Afhandelen van bestandsuploads met aangepaste verwerking
- Ondersteunen van legacy code op basis van servlets

webforJ biedt twee benaderingen voor het implementeren van aangepaste servlets naast je app:

## Benadering 1: Remapping `WebforjServlet` {#approach-1-remapping-webforjservlet}

Deze benadering remapt de `WebforjServlet` van `/*` naar een specifieke pad zoals `/ui/*`, waardoor de URL-naamruimte vrij komt voor aangepaste servlets. Hoewel dit vereist dat je `web.xml` aanpast, krijgen aangepaste servlets directe toegang tot hun URL-patronen zonder enige proxy overhead.

```xml
<web-app>
  <!-- WebforjServlet remapped om alleen /ui/* te verwerken -->
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
- De aangepaste servlet verwerkt verzoeken naar `/hello-world`
- Geen proxymechanisme betrokken - directe routing in de servletcontainer

:::tip Spring Boot-configuratie
Bij gebruik van webforJ met Spring Boot is er geen `web.xml`-bestand. In plaats daarvan configureer je de servletmapping in `application.properties`:

```Ini
webforj.servlet-mapping=/ui/*
```

Deze eigenschap remapt `WebforjServlet` van de standaard `/*` naar `/ui/*`, waardoor de URL-naamruimte vrij komt voor je aangepaste servlets. Voeg geen aanhalingstekens rond de waarde toe - deze worden als onderdeel van het URL-patroon geïnterpreteerd.
:::

## Benadering 2: Configuratie van `WebforjServlet` proxy {#approach-2-webforjservlet-proxy-configuration}

Deze benadering houdt `WebforjServlet` op `/*` en configureert aangepaste servlets in `webforJ.conf`. De `WebforjServlet` onderschept alle verzoeken en proxy's overeenkomende patronen naar je aangepaste servlets.

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

### Configuratie van webforJ.conf {#webforjconf-configuration}

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
- Verzoeken naar `/hello-world` worden geproxied naar `HelloWorldServlet`
