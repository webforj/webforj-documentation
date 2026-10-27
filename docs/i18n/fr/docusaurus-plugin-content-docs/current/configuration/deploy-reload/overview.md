---
title: Redeployment and Live Reload
hide_table_of_contents: false
hide_giscus_comments: true
description: >-
  Apply code changes to a running webforJ app during development, on the server
  through hotswap or a restart, and in the browser through live reload.
_i18n_hash: 243a55fe41bec8aeb4dfc9f70561d80c
---
Pendant le développement, webforJ applique les modifications enregistrées à l'application en cours d'exécution et met à jour le navigateur. Les modifications de classe atteignent l'application via un [outil de hotswap](/docs/configuration/deploy-reload/hotswap) ou par un redémarrage. Le rechargement en direct met à jour le navigateur après l'un ou l'autre.

Les projets créés à partir d'un [archétype](/docs/introduction/getting-started) sont préconfigurés. Pour un projet existant, suivez [Spring Boot](/docs/configuration/deploy-reload/spring-devtools), [Jetty](/docs/configuration/deploy-reload/maven-jetty-plugin) ou [BBjServices](/docs/configuration/deploy-reload/bbjservices).

## Comment chaque changement s'applique {#how-each-change-applies}

| Changement | Résultat | Référence |
|---|---|---|
| Classe Java, outil de hotswap attaché | La classe est mise à jour dans l'application en cours d'exécution. La partie concernée de la page est reconstruite et l'état de l'application reste. | [Hotswap](/docs/configuration/deploy-reload/hotswap) |
| Classe Java, pas d'outil de hotswap | L'application redémarre. Le navigateur se recharge lorsque l'application est prête. | [Spring Boot](/docs/configuration/deploy-reload/spring-devtools), [Jetty](/docs/configuration/deploy-reload/maven-jetty-plugin) |
| Classe Java dans BBjServices, pas d'outil de hotswap | Le changement ne s'applique pas tant que la surveillance ne redémarre pas. | [BBjServices](/docs/configuration/deploy-reload/bbjservices) |
| Feuille de style ou image | La page l'applique en place, sans rechargement. | [Settings](#settings) |
| Source sous `src/main/frontend` | La surveillance la reconstruit et met à jour le navigateur. | [Frontend watch](/docs/configuration/deploy-reload/frontend-watch) |

## Paramètres {#settings}

Ces paramètres contrôlent le rechargement live pendant le développement :

| Propriété | Par défaut | Description |
|----------|---------|-------------|
| `webforj.devtools.livereload.enabled` | `false` | Active le rechargement en direct pour les exécutions de développement. |
| `webforj.devtools.livereload.websocket-port` | `35730` | Port pour la connexion du navigateur. |
| `webforj.devtools.livereload.websocket-path` | `/webforj-devtools-ws` | Chemin pour la connexion du navigateur. |
| `webforj.devtools.livereload.static-resources-enabled` | `true` | Applique les modifications de feuille de style et d'image en place plutôt que de recharger la page. |
| `webforj.devtools.livereload.heartbeat-interval` | `30000` | Intervalle en millisecondes pour les vérifications de connexion qui détectent un serveur redémarrant. |

Les clés n'ont aucun effet dans une application empaquetée. Les applications empaquetées ne contiennent pas d'outils de développement.

## Sujets {#topics}

<DocCardList className="topics-section" />
