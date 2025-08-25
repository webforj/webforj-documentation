---
sidebar_position: 35
title: Deploying Additional Servlets
sidebar_class_name: new-content
_i18n_hash: 0717fa071511a4ca3b71dcf0592146e7
---
<!-- vale off -->
# Déploiement de Servlets Supplémentaires <DocChip chip='since' label='25.02' />
<!-- vale on -->

webforJ achemine toutes les requêtes via `WebforjServlet`, qui est mappé sur `/*` dans web.xml par défaut. Ce servlet gère le cycle de vie des composants, le routage et les mises à jour de l'interface utilisateur qui alimentent votre application webforJ.

Dans certains scénarios, vous pourriez avoir besoin de déployer des servlets supplémentaires aux côtés de votre application webforJ :
- Intégration de bibliothèques tierces qui fournissent leurs propres servlets
- Mise en œuvre d'API REST ou de webhooks
- Gestion des téléchargements de fichiers avec un traitement personnalisé
- Support de code basé sur des servlets anciens

webforJ propose deux approches pour déployer des servlets personnalisés aux côtés de votre application :

## Approche 1 : Remappage de `WebforjServlet` {#approach-1-remapping-webforjservlet}

Cette approche remappe le `WebforjServlet` de `/*` à un chemin spécifique comme `/ui/*`, libérant ainsi l'espace d'URL pour des servlets personnalisés. Bien que cela nécessite de modifier `web.xml`, cela donne aux servlets personnalisés un accès direct à leurs modèles d'URL sans surcharge de proxy.

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
  
  <!-- Servlet personnalisé avec son propre modèle d'URL -->
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
- Le servlet personnalisé gère les requêtes à `/hello-world`
- Aucun mécanisme de proxy impliqué - routage direct du conteneur de servlets

:::tip Configuration Spring Boot
Lors de l'utilisation de webforJ avec Spring Boot, il n'y a pas de fichier `web.xml`. Au lieu de cela, configurez le mappage des servlets dans `application.properties` :

```Ini
webforj.servlet-mapping=/ui/*
```

Cette propriété remappe `WebforjServlet` de l'`/*` par défaut à `/ui/*`, libérant ainsi l'espace d'URL pour vos servlets personnalisés. N'incluez pas de guillemets autour de la valeur - ils seront interprétés comme faisant partie du modèle d'URL.
:::

## Approche 2 : Configuration de proxy pour `WebforjServlet` {#approach-2-webforjservlet-proxy-configuration}

Cette approche garde `WebforjServlet` à `/*` et configure les servlets personnalisés dans `webforJ.conf`. Le `WebforjServlet` intercepte toutes les requêtes et les proxy vers les servlets personnalisés correspondants.

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

<!-- Servlet personnalisé avec son propre modèle d'URL -->
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
servlets = [
  {
    name = "hello-world"
    class = "com.example.HelloWorldServlet"
  }
]
```

Avec cette configuration :
- `WebforjServlet` gère toutes les requêtes
- Les requêtes à `/hello-world` sont proxy vers `HelloWorldServlet`
