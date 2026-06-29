---
title: Frontend watch
sidebar_position: 20
sidebar_class_name: new-content
description: >-
  Rebuild frontend sources while a webforJ app runs and refresh the browser,
  with stylesheet and image changes applied in place and script changes
  reloading the view.
_i18n_hash: efb22f8bcac71567979d21178e62ba7c
---
Le watch frontend reconstruit vos sources `src/main/frontend` pendant que l'application fonctionne et actualise le navigateur, de sorte qu'un changement dans le frontend apparaisse sans une construction manuelle ni un redémarrage du serveur. C'est le côté développement du [frontend bundler](/docs/managing-resources/bundler/overview).

## Exécution du watch {#running-the-watch}

Exécutez-le en parallèle avec votre serveur, car l'objectif avant celui qui démarre l'application. Un projet Spring définit cela comme son objectif par défaut, donc `mvn` sans arguments démarre les deux :

```bash
mvn compile webforj:watch spring-boot:run
```

Pour le [plugin Maven Jetty](/docs/configuration/deploy-reload/maven-jetty-plugin), démarrez-le de la même manière :

```bash
mvn compile webforj:watch jetty:run
```

Pour le watch en tant qu'étape de construction, voir [Construction et tests](/docs/managing-resources/bundler/build-and-tests#the-development-watch).

## Que se passe-t-il lors d'un changement {#what-happens-on-a-change}

Lorsque vous enregistrez une source, le watch reconstruit et envoie les fichiers modifiés au navigateur. Ce que fait le navigateur dépend de la sortie, pas de la source que vous avez modifiée :

- Une **feuille de style** se patch en place, sans rechargement, donc les données du formulaire et la position de défilement restent. Une modification de `.css`, `.scss`, `.sass` ou `.less` tombe ici.
- Une **image** s'échange en place, sans rechargement.
- **Tout le reste** recharge la vue. Une modification de `.ts`, `.tsx` ou `.js` tombe ici, car un nouveau comportement nécessite une page fraîche.

Si une reconstruction touche plusieurs fichiers et que tous peuvent se patcher en place, le navigateur reste à sa position. Si même un seul ne le peut pas, il recharge, donc vous ne voyez jamais un changement apparaître à moitié.

## Lors d'un redémarrage du serveur {#across-a-server-restart}

Un changement Java redémarre le serveur, via [Spring DevTools](/docs/configuration/deploy-reload/spring-devtools), le [plugin Maven Jetty](/docs/configuration/deploy-reload/maven-jetty-plugin), ou [JRebel](/docs/configuration/deploy-reload/jrebel). Le watch garde le frontend synchronisé avec cela :

- **Vos styles restent appliqués** pendant le redémarrage, au lieu de clignoter sans style.
- **La page se recharge une fois l'application prête**, pas avant, pour éviter de rencontrer une erreur due à un rechargement trop précoce. Un petit indicateur s'affiche pendant que le serveur redémarre ; un rechargement manuel ne montre rien.
- **Ajouter ou supprimer un `@BundleEntry` prend effet lors du prochain redémarrage.**

## Configuration {#configuration}

Le rechargement en direct est un paramètre webforJ, il s'applique quel que soit le serveur que vous exécutez. Une application Spring lit ces clés depuis `application.properties` ; un serveur autonome, tel que le [plugin Maven Jetty](/docs/configuration/deploy-reload/maven-jetty-plugin), lit les mêmes clés depuis `webforj.conf`.

```ini title="application.properties"
# Actualiser le navigateur lors d'un changement
webforj.devtools.livereload.enabled=true

# Patch les feuilles de style et les images en place au lieu de recharger (par défaut : true)
webforj.devtools.livereload.static-resources-enabled=true

# Port et chemin sur lesquels le navigateur se connecte (par défaut : 35730, /webforj-devtools-ws)
webforj.devtools.livereload.websocket-port=35730
webforj.devtools.livereload.websocket-path=/webforj-devtools-ws

# Intervalle de battement en millisecondes (par défaut : 30000)
webforj.devtools.livereload.heartbeat-interval=30000
```
