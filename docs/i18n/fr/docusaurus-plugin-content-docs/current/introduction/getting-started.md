---
title: Getting Started
description: >-
  Create a new webforJ project from an archetype using either the startforJ web
  wizard or a Maven command-line generator.
sidebar_position: 2
_i18n_hash: c1867c61e2072cb6657bad9492f22083
---
Cet article décrit les étapes pour créer une nouvelle application webforJ en utilisant les [archétypes](../building-ui/archetypes/overview.md) webforJ. Les archétypes fournissent des structures de projet préconfigurées et du code de démarrage afin que vous puissiez lancer un projet rapidement.
Pour créer une nouvelle application webforJ à partir d'un archétype, vous pouvez utiliser [startforJ](#using-startforj) ou la [ligne de commande](#using-the-command-line).

:::tip Prérequis
Avant de commencer, consultez les [prérequis](./prerequisites) nécessaires pour configurer et utiliser webforJ.
:::

## Utilisation de startforJ {#using-startforj}

Le moyen le plus simple de créer une nouvelle application webforJ est [startforJ](https://docs.webforj.com/startforj), qui génère un projet de démarrage minimal basé sur un archétype webforJ choisi. Ce projet de démarrage inclut toutes les dépendances requises, les fichiers de configuration et une mise en page préfaite, afin que vous puissiez commencer à construire directement.

### Personnalisation avec startforJ {#customizing-with-startforj}

Lorsque vous créez une application avec [startforJ](https://docs.webforj.com/startforj), vous pouvez la personnaliser en fournissant les informations suivantes :

- Métadonnées de base du projet (Nom de l'application, ID de groupe, ID de l'artéfact)
- Version de webforJ et version de Java
- Couleur et icône du thème
- Archétype
- Saveur

Il existe deux options de saveur parmi lesquelles choisir, "webforJ uniquement" étant la valeur par défaut :
  - **webforJ uniquement** : application webforJ standard
  - **webforJ + Spring Boot** : application webforJ avec support Spring Boot

:::tip Archétypes disponibles
webforJ propose plusieurs archétypes prédéfinis pour vous aider à démarrer rapidement. Pour une liste complète des archétypes disponibles, consultez le [catalogue des archétypes](../building-ui/archetypes/overview).
:::

En utilisant ces informations, startforJ créera un projet de base à partir de l'archétype choisi avec vos personnalisations souhaitées.
Vous pouvez choisir de télécharger votre projet sous forme de fichier ZIP ou de le publier directement sur GitHub.

Une fois que vous avez téléchargé votre projet, ouvrez le dossier du projet dans votre IDE.

## Utilisation de la ligne de commande {#using-the-command-line}

Si vous préférez utiliser la ligne de commande, vous pouvez générer un projet directement en utilisant l'archétype Maven :

<ComponentArchetype
project="hello-world"
flavor="webforj"
/>
