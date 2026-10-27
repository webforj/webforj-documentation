---
title: Commencer
description: >-
  Create a new webforJ project from an archetype using either the startforJ web
  wizard or a Maven command-line generator.
sidebar_position: 2
_i18n_hash: ea669974a5f1b3a02a162f89bb16c6e3
---
Cet article décrit les étapes pour créer une nouvelle application webforJ en utilisant les [archétypes](../building-ui/archetypes/overview.md) webforJ. Les archétypes fournissent des structures de projet préconfigurées et un code de démarrage afin que vous puissiez démarrer un projet rapidement.

Pour créer une nouvelle application webforJ à partir d'un archétype, vous pouvez utiliser [startforJ](#using-startforj) ou la [ligne de commande](#using-the-command-line).

:::tip Prérequis
Avant de commencer, consultez les [prérequis](./prerequisites) nécessaires pour configurer et utiliser webforJ.
:::

## Utilisation de startforJ {#using-startforj}

Le moyen le plus simple de créer une nouvelle application webforJ est [startforJ](https://docs.webforj.com/startforj), qui génère un projet de démarrage minimal basé sur un archétype webforJ choisi. Ce projet de démarrage comprend toutes les dépendances requises, les fichiers de configuration et une mise en page préconçue, de sorte que vous puissiez commencer à travailler dessus immédiatement.

### Personnalisation avec startforJ {#customizing-with-startforj}

Lorsque vous créez une application avec [startforJ](https://docs.webforj.com/startforj), vous pouvez la personnaliser en fournissant les informations suivantes :

- Métadonnées de projet de base (Nom de l'application, ID de groupe, ID d'artefact)
- Version de webforJ et version de Java
- Couleur et icône de thème
- Archétype
- S'exécute sur

Le choix de **S'exécute sur** détermine où l'application s'exécute, avec **Conteneur de servlets** comme valeur par défaut :
  - **Conteneur de servlets** : un WAR pour Jetty, Tomcat ou un autre conteneur de servlets
  - **Spring Boot** : un JAR exécutable avec un serveur intégré
  - **BBjServices** : une application qui s'exécute dans BBjServices via [BBjShip](/docs/configuration/bbjship/overview)

:::tip Archétypes disponibles
webforJ est livré avec plusieurs archétypes prédéfinis pour vous aider à commencer rapidement. Pour une liste complète des archétypes disponibles, consultez le [catalogue des archétypes](../building-ui/archetypes/overview).
:::

En utilisant ces informations, startforJ créera un projet de base à partir de l'archétype choisi avec vos personnalisations choisies. Vous pouvez choisir de télécharger votre projet sous forme de fichier ZIP ou de le publier directement sur GitHub.

Une fois que vous avez téléchargé votre projet, ouvrez le dossier du projet dans votre IDE.

## Utilisation de la ligne de commande {#using-the-command-line}

Si vous préférez utiliser la ligne de commande, vous pouvez générer un projet directement en utilisant l'archétype Maven :

<ComponentArchetype
project="hello-world"
flavor="webforj"
/>
