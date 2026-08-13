---
sidebar_position: 25
title: Deploying Additional Servlets
description: >-
  Host REST endpoints and third-party servlets alongside a webforJ app by
  remapping WebforjServlet or proxying through webforj.conf.
_i18n_hash: 1e25ddc6c7e56063c26b9f911c0be5d2
---
<!-- vale off -->
# Déploiement de Servlets Supplémentaires <DocChip chip='since' label='25.02' />
<!-- vale on -->

webforJ achemine toutes les demandes via `WebforjServlet`, qui est mappé par défaut sur `/*` dans web.xml. Ce servlet gère le cycle de vie des composants, le routage et les mises à jour de l'interface utilisateur qui alimentent votre application webforJ.

Dans certains cas, vous pourriez avoir besoin de déployer des servlets supplémentaires aux côtés de votre application webforJ :
- Intégration de bibliothèques tierces qui fournissent leurs propres servlets
- Mise en œuvre d'APIs REST ou de webhooks
- Gestion des téléchargements de fichiers avec un traitement personnalisé
- Support de code basé sur des servlets hérités

webforJ propose deux approches pour déployer des servlets personnalisés à côté de votre application :

<AISkillTip skill="webforj-adding-servlets" />

## Approche 1 : Remapping de `WebforjServlet` {#approach-1-remapping-webforjservlet}

Cette approche remappe le `WebforjServlet` de `/*` vers un chemin spécifique comme `/ui/*`, libérant ainsi l'espace d'URL pour des servlets personnalisés. Bien que cela nécessite de modifier `web.xml`, cela donne aux servlets personnalisés un accès direct à leurs motifs d'URL sans surcharge de proxy.

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
- Le servlet personnalisé gère les demandes à `/hello-world`
- Aucun mécanisme de proxy impliqué - routage direct de conteneur de servlet

:::tip Configuration Spring Boot
Lors de l'utilisation de webforJ avec Spring Boot, il n'y a pas de fichier `web.xml`. À la place, configurez le mappage des servlets dans `application.properties` :

```Ini
webforj.servlet-mapping=/ui/*
```

Cette propriété remappe `WebforjServlet` de l'`/*` par défaut à `/ui/*`, libérant ainsi l'espace d'URL pour vos servlets personnalisés. N'incluez pas de guillemets autour de la valeur - ils seront interprétés comme faisant partie du motif d'URL.
:::

## Approche 2 : configuration de proxy `WebforjServlet` {#approach-2-webforjservlet-proxy-configuration}

Cette approche garde `WebforjServlet` à `/*` et configure les servlets personnalisés dans `webforj.conf`. Le `WebforjServlet` intercepte toutes les demandes et proxis les motifs correspondants vers vos servlets personnalisés.

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
- `WebforjServlet` gère toutes les demandes
- Les demandes à `/hello-world` sont proxy vers `HelloWorldServlet`
- La clé `config` optionnelle fournit des paires nom/valeur comme paramètres d'initialisation pour le servlet
