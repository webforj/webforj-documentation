---
title: Commençant
sidebar_position: 2
description: >-
  Create a webforJ app that runs with BBjServices from startforJ or the Maven
  archetypes, run it on your local BBj and deploy it with BBjShip.
_i18n_hash: 06ef0b29df76b835ecd12d7268e4eca6
---
Ce guide crée une application webforJ qui fonctionne avec BBjServices, la lance sur votre BBj local pendant que vous développez et la déploie avec BBjShip.

:::tip Prérequis
Vous avez besoin d'un projet Maven et d'une installation BBj de la même version que webforJ, par exemple BBj 26.03 pour webforJ 26.03, avec BBjServices en cours d'exécution. BBjShip ne prend pas en charge Gradle. Consultez les [prérequis](/docs/introduction/prerequisites) pour le reste des outils.
:::

## Créer une application BBj {#create-a-bbj-app}

Vous avez deux options pour créer une nouvelle application webforJ qui fonctionne avec BBjServices : utiliser l'outil graphique startforJ ou la ligne de commande Maven.

<!-- vale off -->
### Option 1 : utiliser startforJ {#option-1-using-startforj}
<!-- vale on -->

Lorsque vous créez une application avec [startforJ](https://docs.webforj.com/startforj), vous pouvez la personnaliser en fournissant les informations suivantes :

- Métadonnées de base du projet (Nom de l'application, ID de groupe, ID d'artefact)
- Version de webforJ et version de Java
- Couleur de thème et icône
- Archétype
- **Fonctionne sur** - Sélectionnez **BBjServices** pour créer un projet qui fonctionne avec BBjServices

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

Avec le paramètre `flavor`, l'archétype génère un projet qui fonctionne avec BBjServices.

Cela crée un projet avec :
- La dépendance `webforj` sans le moteur webforJ, que fournit BBjServices
- Le plugin Maven webforJ, configuré pour déployer sur BBjShip sur votre BBj local sous l'ID d'artefact comme nom de l'application
- HotswapAgent pour les exécutions de développement
- Des vues d'exemple
- Fichiers de configuration webforJ pour le développement et la production

## Exécuter l'application {#run-the-app}

Un projet d'archétype définit son objectif Maven par défaut, donc `mvn` sans arguments compile l'application et démarre une [exécution de développement](/docs/configuration/deploy-reload/bbjservices) dans vos BBjServices locaux :

```bash
mvn
```

La première exécution prépare votre BBj local. Elle installe BBjShip et ajoute HotswapAgent à BBjServices, ce qui redémarre BBjServices une fois. La compilation affiche ensuite l'adresse de votre application :

```text
[INFO] webforJ watch: my-app is running at http://localhost:8888/webapp/my-app
[INFO] webforJ watch: press Ctrl+C to stop the app and the watch
```

Les classes que vous compilez atteignent l'application en cours d'exécution sans redémarrage. Arrêtez la compilation avec `Ctrl+C` pour arrêter l'application.

## Déployer l'application {#deploy-the-app}

Pour déployer l'application emballée, exécutez :

```bash
mvn install
```

L'application est alors publiée à `http://localhost:8888/webapp/my-app` jusqu'à ce que vous la déployiez à nouveau ou que vous la supprimiez. Consultez [Déploiement](/docs/configuration/bbjship/deploying) pour déployer sur un autre serveur BBj, par exemple à partir d'un pipeline CI.
