---
sidebar_position: 50
title: Deploying Additional Servlets
sidebar_class_name: new-content
_i18n_hash: 4506bcc85ddfa8698f4f8138fe6b4e33
---
<!-- vale off -->
# Déploiement de Servlets Supplémentaires <DocChip chip='since' label='25.02' />
<!-- vale on -->

webforJ achemine toutes les requêtes via `WebforjServlet`, qui est mappé sur `/*` dans web.xml par défaut. Ce servlet gère le cycle de vie des composants, le routage et les mises à jour de l'interface utilisateur qui alimentent votre application webforJ.

Dans certains scénarios, vous devrez peut-être déployer des servlets supplémentaires aux côtés de votre application webforJ :
- Intégration de bibliothèques tierces qui fournissent leurs propres servlets
- Mise en œuvre d'API REST ou webhooks
- Gestion des téléchargements de fichiers avec traitement personnalisé
- Prise en charge du code basé sur des servlets légataires

webforJ propose deux approches pour déployer des servlets personnalisés aux côtés de votre application :

## Approche 1 : Remappage de `WebforjServlet` {#approach-1-remapping-webforjservlet}

Cette approche remappe le `WebforjServlet` de `/*` à un chemin spécifique comme `/ui/*`, libérant ainsi l'espace de noms d'URL pour les servlets personnalisés. Bien que cela nécessite de modifier `web.xml`, cela donne aux servlets personnalisés un accès direct à leurs motifs d'URL sans surcharge de proxy.

```xml
<web-app>
  <!-- WebforjServlet remappé pour gérer uniquement /ui/* -->
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
- Le servlet personnalisé gère les requêtes à `/hello-world`
- Aucun mécanisme de proxy impliqué - routage direct dans le conteneur de servlets

:::tip Configuration Spring Boot
Lors de l'utilisation de webforJ avec Spring Boot, il n'y a pas de fichier `web.xml`. Au lieu de cela, configurez le mappage des servlets dans `application.properties` :

```Ini
webforj.servlet-mapping=/ui/*
```

Cette propriété remappe `WebforjServlet` de l'URL par défaut `/*` à `/ui/*`, libérant ainsi l'espace de noms d'URL pour vos servlets personnalisés. N'incluez pas de guillemets autour de la valeur - ils seront interprétés comme faisant partie du motif d'URL.
:::

## Approche 2 : Configuration de proxy de `WebforjServlet` {#approach-2-webforjservlet-proxy-configuration}

Cette approche laisse `WebforjServlet` à `/*` et configure des servlets personnalisés dans `webforJ.conf`. Le `WebforjServlet` intercepte toutes les requêtes et fait passer les motifs correspondants à vos servlets personnalisés.

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
servlets = [
  {
    name = "hello-world"
    class = "com.example.HelloWorldServlet"
  }
]
```

Avec cette configuration :
- `WebforjServlet` gère toutes les requêtes
- Les requêtes à `/hello-world` sont proxyées vers `HelloWorldServlet`
