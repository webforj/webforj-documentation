---
title: Jetty
sidebar_position: 40
description: >-
  Run a webforJ app on the embedded Jetty server with the Maven Jetty plugin,
  with live reload and hotswap during development.
_i18n_hash: 73514e3b51a43e4a876aefd5cf933577
---
Le plugin Maven Jetty exécute l'application dans un serveur Jetty intégré directement à partir du projet. Un projet archétype définit `compile webforj:watch jetty:run` comme son objectif Maven par défaut, donc `mvn` sans arguments compile l'application, démarre le [frontend watch](/docs/configuration/deploy-reload/frontend-watch), et sert l'application sur Jetty.

## Exigences {#requirements}

Un projet Jetty déclare lui-même les outils de développement, dans le profil utilisé pour les exécutions de développement :

```xml title="pom.xml"
<profiles>
  <profile>
    <id>dev</id>
    <activation>
      <activeByDefault>true</activeByDefault>
    </activation>
    <dependencies>
      <dependency>
        <groupId>com.webforj</groupId>
        <artifactId>webforj-devtools</artifactId>
      </dependency>
    </dependencies>
  </profile>
</profiles>
```

La version provient du Bill of Materials (BOM) webforJ. Le profil maintient la dépendance hors du war packagé. Un projet créé à partir d'un [archétype](/docs/introduction/getting-started) a ce profil.

## Activer le rechargement en direct {#turning-live-reload-on}

```ini title="webforj.conf"
webforj.devtools.livereload.enabled = true
```

Les clés sont les mêmes que celles qu'une application Spring Boot définit dans `application.properties`, répertoriées dans les [paramètres](/docs/configuration/deploy-reload/overview#settings).

## Changements de classe {#class-changes}

Avec un [outil de hotswap](/docs/configuration/deploy-reload/hotswap) configuré, l'outil applique les modifications de classe et Jetty ne redéploie rien. Deux propriétés Jetty soutiennent cela, et un projet archétype définit les deux :

- `scan` est `0`, ce qui désactive le scan de fichiers de Jetty.
- `deployMode` reste non défini. Hotswap nécessite le mode forké, et le plugin le sélectionne. Une construction qui définit `deployMode` à une autre valeur démarre sans l'outil et le journalise.

Sans un outil de hotswap, réglez `scan` sur un intervalle en secondes et Jetty redéploiera l'application lorsque les classes ou ressources compilées changent :

| Propriété | Description | Par défaut |
|-----------|-------------|------------|
| `scan` | Intervalle en secondes entre les scans de la sortie compilée, défini comme la propriété `jetty.scan`. `0` désactive le scan. Des intervalles plus longs diminuent la charge et retardent le redéploiement. | `1` |

## Considérations d'utilisation {#usage-considerations}

- **Mémoire et CPU** : de faibles valeurs `scan` augmentent la consommation de ressources sur de grands projets. Des intervalles plus longs la diminuent et retardent le redéploiement.
- **Développement uniquement** : le plugin Jetty n'est pas destiné aux déploiements en production.
- **Sessions** : un redéploiement peut supprimer les sessions utilisateur. Un [outil de hotswap](/docs/configuration/deploy-reload/hotswap) applique les modifications sans redéploiement, et la session survit.
