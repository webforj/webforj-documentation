---
title: Premiers pas
sidebar_position: 2
description: >-
  Create a webforJ app that runs with BBjServices from startforJ or the Maven
  archetypes, run it on your local BBj and deploy it with BBjShip.
_i18n_hash: 84b3ad107540f450350f754730522e0e
---
Ce guide crée une application webforJ qui fonctionne avec BBjServices, l’exécute sur votre BBj local pendant le développement et la déploie avec BBjShip.

:::tip Prérequis
Vous avez besoin d’un projet Maven et d’une installation de BBj de la même version que webforJ, par exemple BBj 26.03 pour webforJ 26.03, avec BBjServices en cours d’exécution. BBjShip ne prend pas en charge Gradle. Consultez les [prérequis](/docs/introduction/prerequisites) pour connaître les autres outils nécessaires.
:::

## Créer une application BBj {#create-a-bbj-app}

Vous avez deux possibilités pour créer une application webforJ qui fonctionne avec BBjServices : utiliser l’outil graphique startforJ ou la ligne de commande Maven.

<!-- vale off -->
### Option 1 : utiliser startforJ {#option-1-using-startforj}
<!-- vale on -->

Lorsque vous créez une application avec [startforJ](https://docs.webforj.com/startforj), vous pouvez la personnaliser en fournissant les informations suivantes :

- Métadonnées de base du projet (nom de l’application, ID de groupe, ID d’artefact)
- Version de webforJ et version de Java
- Couleur du thème et icône
- Archétype
- **Fonctionne avec** — Sélectionnez **BBjServices** pour créer un projet qui fonctionne avec BBjServices

### Option 2 : utiliser la ligne de commande {#option-2-using-the-command-line}

Si vous préférez utiliser la ligne de commande, générez le projet avec les archétypes officiels de webforJ :

```bash {8}
mvn -B archetype:generate \
  -DarchetypeGroupId=com.webforj \
  -DarchetypeArtifactId=webforj-archetype-hello-world \
  -DarchetypeVersion=LATEST \
  -DgroupId=org.example \
  -DartifactId=my-app \
  -Dversion=1.0-SNAPSHOT \
  -Dflavor=webforj-bbj
```

Avec le paramètre `flavor`, l’archétype génère un projet qui fonctionne avec BBjServices.

Cela crée un projet avec :
- La dépendance `webforj` sans le moteur webforJ, fourni par BBjServices
- Le plug-in Maven webforJ, configuré pour déployer l’application sur BBjShip de votre BBj local sous le nom de l’application correspondant à l’ID d’artefact
- HotswapAgent pour les exécutions en développement
- Des vues d’exemple
- Des fichiers de configuration webforJ pour le développement et la production

## Exécuter l’application {#run-the-app}

Un projet d’archétype définit son objectif Maven par défaut. Ainsi, la commande `mvn` sans argument compile l’application et lance une [exécution de développement](/docs/configuration/deploy-reload/bbjservices) dans votre BBjServices local :

```bash
mvn
```

La première exécution prépare votre BBj local. Elle installe BBjShip et ajoute HotswapAgent à BBjServices, qui redémarre une fois. La compilation affiche ensuite l’adresse de votre application :

```text
[INFO] webforJ watch: my-app is running at http://localhost:8888/webapp/my-app
[INFO] webforJ watch: press <kbd>Ctrl</kbd>+<kbd>C</kbd> to stop the app and the watch
```

Les classes que vous compilez sont prises en compte par l’application en cours d’exécution sans redémarrage. Arrêtez la compilation avec <kbd>Ctrl</kbd>+<kbd>C</kbd> pour arrêter l’application.

## Déployer l’application {#deploy-the-app}

Pour déployer l’application empaquetée, exécutez :

```bash
mvn install
```

L’application est alors publiée à l’adresse `http://localhost:8888/webapp/my-app` jusqu’à ce que vous la déployiez à nouveau ou la supprimiez. Consultez la section [Déploiement](/docs/configuration/bbjship/deploying) pour la déployer sur un autre serveur BBj, par exemple depuis un pipeline CI.
