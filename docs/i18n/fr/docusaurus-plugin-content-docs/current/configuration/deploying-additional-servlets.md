---
sidebar_position: 35
title: Deploying Additional Servlets
sidebar_class_name: new-content
_i18n_hash: 95695a68854d595e78a58904d7214208
---
<!-- vale off -->
# Déploiement de servlets supplémentaires <DocChip chip='since' label='25.02' />
<!-- vale on -->

webforJ route toutes les demandes via `WebforjServlet`, qui est mappé par défaut à `/*` dans web.xml. Ce servlet gère le cycle de vie des composants, le routage et les mises à jour de l'interface utilisateur qui alimentent votre application webforJ.

Dans certains scénarios, vous pouvez avoir besoin de déployer des servlets supplémentaires aux côtés de votre application webforJ :
- Intégrer des bibliothèques tierces qui fournissent leurs propres servlets
- Implémenter des API REST ou des webhooks
- Gérer les téléchargements de fichiers avec un traitement personnalisé
- Supporter du code basé sur des servlets obsolètes

webforJ propose deux approches pour déployer des servlets personnalisés aux côtés de votre application :

## Approche 1 : Remapping `WebforjServlet` {#approach-1-remapping-webforjservlet}

Cette approche remappe le `WebforjServlet` de `/*` à un chemin spécifique comme `/ui/*`, libérant ainsi l'espace de noms d'URL pour des servlets personnalisés. Bien que cela nécessite de modifier `web.xml`, cela donne aux servlets personnalisés un accès direct à leurs motifs d'URL sans surcharge de proxy.

```xml
<web-app>
  <!-- WebforjServlet remappé pour ne gérer que /ui/* -->
  <servlet>
    <servlet-name>WebforjServlet</servlet-name>
    <servlet-class>com.webforj.servlet.WebforjServlet</servlet-class>
    <load-on-startup>1</load-on-startup>
  </servlet>
  <servlet-mapping>
    <servlet-name>WebforjServlet</servlet-name>
    <url-pattern>/ui/*</url-pattern>
  </servlet-mapping>
  
  <!-- Servlet personnalisé avec son propre motif d'URL -->
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

Avec cette configuration :
- Les composants webforJ sont accessibles sous `/ui/`
- Le servlet personnalisé gère les demandes vers `/hello-world`
- Aucun mécanisme de proxy impliqué - routage direct du conteneur de servlets

:::tip Configuration de Spring Boot
Lorsque vous utilisez webforJ avec Spring Boot, il n'y a pas de fichier `web.xml`. Au lieu de cela, configurez le mappage du servlet dans `application.properties` :

```Ini
webforj.servlet-mapping=/ui/*
```

Cette propriété remappe `WebforjServlet` de l'`/*` par défaut à `/ui/*`, libérant ainsi l'espace de noms d'URL pour vos servlets personnalisés. N'incluez pas de guillemets autour de la valeur - ils seront interprétés comme une partie du motif d'URL.
:::

## Approche 2 : Configuration du proxy `WebforjServlet` {#approach-2-webforjservlet-proxy-configuration}

Cette approche garde `WebforjServlet` à `/*` et configure des servlets personnalisés dans `webforj.conf`. Le `WebforjServlet` intercepte toutes les demandes et proxi les motifs correspondants vers vos servlets personnalisés.

### Configuration standard de web.xml {#standard-webxml-configuration}

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

<!-- Servlet personnalisé avec son propre motif d'URL -->
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

### Configuration de webforJ.conf {#webforjconf-configuration}

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

Avec cette configuration :
- `WebforjServlet` gère toutes les demandes
- Les demandes vers `/hello-world` sont proxyées vers `HelloWorldServlet`
- La clé `config` optionnelle fournit des paires nom/valeur comme paramètres d'initialisation pour le servlet
