---
sidebar_position: 35
title: Deploying Additional Servlets
_i18n_hash: e7f1a0bcf3986ff50dcfd89281ab3339
---
<!-- vale off -->
# Déploiement de servlets supplémentaires <DocChip chip='since' label='25.02' />
<!-- vale on -->

webforJ achemine toutes les requêtes via `WebforjServlet`, qui est mappé par défaut sur `/*` dans web.xml. Ce servlet gère le cycle de vie des composants, le routage et les mises à jour de l'interface utilisateur qui alimentent votre application webforJ.

Dans certains scénarios, vous pourriez avoir besoin de déployer des servlets supplémentaires aux côtés de votre application webforJ :
- Intégration de bibliothèques tierces qui fournissent leurs propres servlets
- Mise en œuvre d'API REST ou de webhooks
- Gestion des fichiers téléchargés avec un traitement personnalisé
- Prise en charge de code basé sur des servlets hérités

webforJ propose deux approches pour déployer des servlets personnalisés aux côtés de votre application :

<AISkillTip skill="webforj-adding-servlets" />

## Approche 1 : Remappage de `WebforjServlet` {#approach-1-remapping-webforjservlet}

Cette approche remappe le `WebforjServlet` de `/*` à un chemin spécifique comme `/ui/*`, libérant ainsi l'espace d'URL pour les servlets personnalisés. Bien que cela nécessite de modifier `web.xml`, cela permet aux servlets personnalisés d'accéder directement à leurs motifs d'URL sans aucune surcharge de proxy.

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
- Le servlet personnalisé gère les requêtes vers `/hello-world`
- Aucun mécanisme de proxy impliqué - routage direct du conteneur de servlets

:::tip Configuration Spring Boot
Lors de l'utilisation de webforJ avec Spring Boot, il n'y a pas de fichier `web.xml`. Au lieu de cela, configurez le mappage des servlets dans `application.properties` :

```Ini
webforj.servlet-mapping=/ui/*
```

Cette propriété remappe `WebforjServlet` du par défaut `/*` au `/ui/*`, libérant ainsi l'espace d'URL pour vos servlets personnalisés. N'incluez pas de guillemets autour de la valeur - ils seront interprétés comme faisant partie du motif d'URL.
:::

## Approche 2 : Configuration proxy de `WebforjServlet` {#approach-2-webforjservlet-proxy-configuration}

Cette approche garde `WebforjServlet` à `/*` et configure des servlets personnalisés dans `webforj.conf`. Le `WebforjServlet` intercepte toutes les requêtes et les proxis vers les servlets personnalisés correspondants.

### Configuration standard web.xml {#standard-webxml-configuration}

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

### Configuration webforJ.conf {#webforjconf-configuration}

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
- `WebforjServlet` gère toutes les requêtes
- Les requêtes vers `/hello-world` sont proxis vers `HelloWorldServlet`
- La clé `config` optionnelle fournit des paires nom/valeur comme paramètres d'initialisation pour le servlet
