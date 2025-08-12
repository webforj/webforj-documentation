---
sidebar_position: 50
title: Deploying Additional Servlets
sidebar_class_name: new-content
_i18n_hash: 0717fa071511a4ca3b71dcf0592146e7
---
<!-- vale off -->
# Extra Servlets Implementeren <DocChip chip='since' label='25.02' />
<!-- vale on -->

webforJ routeert alle verzoeken via `WebforjServlet`, dat standaard is toegewezen aan `/*` in web.xml. Deze servlet beheert de levenscyclus van componenten, routing en UI-updates die uw webforJ-app aandrijven.

In sommige scenario's moet u mogelijk extra servlets naast uw webforJ-app implementeren:
- Integreren van externe bibliotheken die hun eigen servlets bieden
- Implementeren van REST-API's of webhooks
- Afhandelen van bestandsuploads met aangepaste verwerking
- Ondersteunen van legacy servlet-gebaseerde code

webforJ biedt twee benaderingen voor het implementeren van aangepaste servlets naast uw app:

## Benadering 1: `WebforjServlet` herconfiguratie {#approach-1-remapping-webforjservlet}

Deze benadering herconfigureert de `WebforjServlet` van `/*` naar een specifieke pad zoals `/ui/*`, waardoor de URL-namespace vrijkomt voor aangepaste servlets. Hoewel dit vereist dat u `web.xml` wijzigt, geeft het aangepaste servlets directe toegang tot hun URL-patronen zonder proxy-overhead.

```xml
<web-app>
  <!-- WebforjServlet hergeconfigureerd om alleen /ui/* te behandelen -->
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
- Geen proxymechanisme betrokken - directe routing van de servletcontainer

:::tip Spring Boot configuratie
Bij gebruik van webforJ met Spring Boot, is er geen `web.xml`-bestand. Configureer in plaats daarvan de servletmapping in `application.properties`:

```Ini
webforj.servlet-mapping=/ui/*
```

Deze eigenschap herconfigureert `WebforjServlet` van de standaard `/*` naar `/ui/*`, waardoor de URL-namespace vrijkomt voor uw aangepaste servlets. Voeg geen aanhalingstekens rond de waarde toe - deze worden geïnterpreteerd als deel van het URL-patroon.
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
- Verzoeken naar `/hello-world` worden geproxyd naar `HelloWorldServlet`
