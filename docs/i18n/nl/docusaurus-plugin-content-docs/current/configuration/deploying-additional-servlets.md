---
sidebar_position: 35
title: Deploying Additional Servlets
sidebar_class_name: new-content
_i18n_hash: 95695a68854d595e78a58904d7214208
---
<!-- vale off -->
# Implementatie van Extra Servlets <DocChip chip='since' label='25.02' />
<!-- vale on -->

webforJ leidt alle aanvragen via `WebforjServlet`, dat standaard in web.xml is gekoppeld aan `/*`. Deze servlet beheert de levenscyclus van componenten, routering en UI-updates die uw webforJ-app aansteken.

In sommige scenario's heeft u mogelijk behoefte aan het implementeren van extra servlets naast uw webforJ-app:
- Integratie van derde partijen bibliotheken die hun eigen servlets aanbieden
- Implementatie van REST API's of webhooks
- Verwerking van bestandsuploads met aangepaste verwerking
- Ondersteuning van legacy servlet-gebaseerde code

webforJ biedt twee manieren om aangepaste servlets naast uw app te implementeren:

## Aanpak 1: Remapping van `WebforjServlet` {#approach-1-remapping-webforjservlet}

Deze aanpak remapt de `WebforjServlet` van `/*` naar een specifieke route zoals `/ui/*`, waardoor de URL-namespace vrij komt voor aangepaste servlets. Terwijl dit het wijzigen van `web.xml` vereist, geeft het aangepaste servlets directe toegang tot hun URL-patronen zonder enige proxy-overhead.

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
- Aangepaste servlet verwerkt aanvragen naar `/hello-world`
- Geen proxymechanisme betrokken - directe routering naar de servletcontainer

:::tip Spring Boot-configuratie
Bij gebruik van webforJ met Spring Boot, is er geen `web.xml`-bestand. In plaats daarvan configureert u de servletmapping in `application.properties`:

```Ini
webforj.servlet-mapping=/ui/*
```

Deze eigenschap remapt `WebforjServlet` van de standaard `/*` naar `/ui/*`, waardoor de URL-namespace vrij komt voor uw aangepaste servlets. Voeg geen aanhalingstekens rond de waarde toe - deze worden geïnterpreteerd als onderdeel van het URL-patroon.
:::

## Aanpak 2: `WebforjServlet` proxyconfiguratie {#approach-2-webforjservlet-proxy-configuration}

Deze aanpak houdt `WebforjServlet` bij `/*` en configureert aangepaste servlets in `webforj.conf`. De `WebforjServlet` onderschept alle aanvragen en proxy's bijpassende patronen naar uw aangepaste servlets.

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
- `WebforjServlet` verwerkt alle aanvragen
- Aanvragen naar `/hello-world` worden naar `HelloWorldServlet` gepyproxy'd
- De optionele `config` sleutel biedt naam/waarde-paren als initialisatieparameters voor de servlet
