---
title: Prise en main
sidebar_position: 2
_i18n_hash: 5c658711bfa3dc70787cccbf2dfb6d2d
---
Cet article décrit les étapes pour créer une nouvelle application webforJ en utilisant les [archétypes](../building-ui/archetypes/overview.md) webforJ. Les archétypes fournissent des structures de projet préconfigurées et du code de démarrage, vous permettant de mettre en place un projet rapidement.
Pour créer une nouvelle application webforJ à partir d'un archétype, vous pouvez utiliser [startforJ](#using-startforj) ou la [ligne de commande](#using-the-command-line). 

:::tip Prérequis
Avant de commencer, passez en revue les [prérequis](./prerequisites) nécessaires pour configurer et utiliser webforJ.
:::


## Utilisation de startforJ {#using-startforj}

Le moyen le plus simple de créer une nouvelle application webforJ est [startforJ](https://docs.webforj.com/startforj), qui génère un projet de démarrage minimal basé sur un archétype webforJ choisi. Ce projet de démarrage comprend toutes les dépendances requises, les fichiers de configuration, et une mise en page prédéfinie, afin que vous puissiez commencer à construire immédiatement.

<div class="videos-container">
  <video controls>
    <source src="https://cdn.webforj.com/webforj-documentation/video/archetypes/startforj.mp4" type="video/mp4" />
  </video>
</div>


### Personnaliser avec startforJ {#customizing-with-startforj}

Lorsque vous créez une application avec [startforJ](https://docs.webforj.com/startforj), vous pouvez la personnaliser en fournissant les informations suivantes :

- Métadonnées de projet de base (Nom de l'application, ID de groupe, ID d'artéfact)  
- Version de webforJ et version de Java
- Couleur et icône du thème
- Archétype
- Saveur

Il y a deux options de saveur parmi lesquelles choisir, "webforJ Only" étant la valeur par défaut :
  - **webforJ Only** : Application webforJ standard
  - **webforJ + Spring Boot** : Application webforJ avec support Spring Boot

:::tip Archétypes disponibles
webforJ est accompagné de plusieurs archétypes prédéfinis pour vous aider à démarrer rapidement. Pour une liste complète des archétypes disponibles, consultez le [catalogue des archétypes](../building-ui/archetypes/overview).
:::

Avec ces informations, startforJ va créer un projet de base à partir de votre archétype choisi avec vos personnalisations sélectionnées.
Vous pouvez choisir de télécharger votre projet en tant que fichier ZIP ou de le publier directement sur GitHub.

Une fois que vous avez téléchargé votre projet, ouvrez le dossier du projet dans votre IDE et passez à [l'exécution de l'application](#running-the-app).

## Utilisation de la ligne de commande {#using-the-command-line}


Si vous préférez utiliser la ligne de commande, vous pouvez générer un projet directement en utilisant l'archétype Maven :

<ComponentArchetype
project="hello-world"
flavor="webforj"
/>
