---
title: Getting Started
sidebar_position: 2
_i18n_hash: e0271a7db26a5c4b3fdc29508119aade
---
Cet article décrit les étapes pour créer une nouvelle application webforJ en utilisant les [archétypes](../building-ui/archetypes/overview.md) webforJ. Les archétypes fournissent des structures de projet préconfigurées et du code de démarrage afin que vous puissiez rapidement démarrer un projet.

Pour créer une nouvelle application webforJ à partir d'un archétype, vous pouvez utiliser [startforJ](#using-startforj) ou la [ligne de commande](#using-the-command-line).

:::tip Prérequis
Avant de commencer, examinez les [prérequis](./prerequisites) nécessaires pour configurer et utiliser webforJ.
:::

## Utiliser startforJ {#using-startforj}

Le moyen le plus simple de créer une nouvelle application webforJ est [startforJ](https://docs.webforj.com/startforj), qui génère un projet de démarrage minimal basé sur un archétype webforJ choisi. Ce projet de démarrage comprend toutes les dépendances requises, les fichiers de configuration et un layout pré-fait, afin que vous puissiez commencer à construire immédiatement.

### Personnaliser avec startforJ {#customizing-with-startforj}

Lorsque vous créez une application avec [startforJ](https://docs.webforj.com/startforj), vous pouvez la personnaliser en fournissant les informations suivantes :

- Métadonnées de base du projet (Nom de l'application, ID de groupe, ID d'artifact)  
- Version webforJ et version Java
- Couleur du thème et icône
- Archétype
- Saveur

Il y a deux options de saveur parmi lesquelles choisir, avec "webforJ Only" étant la défaut :
  - **webforJ Only** : Application webforJ standard
  - **webforJ + Spring Boot** : Application webforJ avec support Spring Boot

:::tip Archétypes disponibles
webforJ propose plusieurs archétypes prédéfinis pour vous aider à démarrer rapidement. Pour une liste complète des archétypes disponibles, consultez le [catalogue des archétypes](../building-ui/archetypes/overview).
:::

Avec ces informations, startforJ créera un projet basique à partir de votre archétype choisi avec vos personnalisations choisies. Vous pouvez choisir de télécharger votre projet sous forme de fichier ZIP ou de le publier directement sur GitHub.

Une fois que vous avez téléchargé votre projet, ouvrez le dossier du projet dans votre IDE.

## Utiliser la ligne de commande {#using-the-command-line}

Si vous préférez utiliser la ligne de commande, vous pouvez générer un projet directement en utilisant l'archétype Maven :

<ComponentArchetype
project="hello-world"
flavor="webforj"
/>
