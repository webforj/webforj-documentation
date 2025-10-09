---
title: Getting Started
sidebar_position: 2
_i18n_hash: 24c0a494b270fb4ea83106005e173ae8
---
Cet article décrit les étapes pour créer une nouvelle application webforJ en utilisant les [archétypes](../building-ui/archetypes/overview.md) webforJ. Les archétypes fournissent des structures de projet préconfigurées et du code de démarrage afin que vous puissiez mettre en place un projet rapidement. 
Pour créer une nouvelle application webforJ à partir d'un archétype, vous pouvez utiliser [startforJ](#using-startforj) ou la [ligne de commande](#using-the-command-line).

:::tip Prérequis
Avant de commencer, consultez les [prérequis](./prerequisites) nécessaires pour configurer et utiliser webforJ.
:::

## Utilisation de startforJ {#using-startforj}

La manière la plus simple de créer une nouvelle application webforJ est [startforJ](https://docs.webforj.com/startforj), qui génère un projet de démarrage minimal basé sur un archétype webforJ choisi. Ce projet de démarrage inclut toutes les dépendances requises, les fichiers de configuration et une mise en page prédéfinie, afin que vous puissiez commencer à construire dessus immédiatement.

<div class="videos-container">
  <video controls>
    <source src="https://cdn.webforj.com/webforj-documentation/video/archetypes/startforj.mp4" type="video/mp4" />
  </video>
</div>

### Personnalisation avec startforJ {#customizing-with-startforj}

Lorsque vous créez une application avec [startforJ](https://docs.webforj.com/startforj), vous pouvez la personnaliser en fournissant les informations suivantes :

- Métadonnées de projet de base (Nom de l'Application, ID de Groupe, ID d'Artifact)
- Version de webforJ et version de Java
- Couleur du Thème et Icône
- Archétype
- Saveur

Il existe deux options de saveur parmi lesquelles choisir, "webforJ Only" étant la valeur par défaut :
  - **webforJ Only** : Application webforJ standard
  - **webforJ + Spring Boot** : Application webforJ avec support Spring Boot

:::caution Support Spring Boot
La saveur Spring Boot n'est disponible que dans la version 25.02 de webforJ et supérieure. Si vous sélectionnez cette option, assurez-vous de choisir une version compatible.
:::

:::tip Archétypes Disponibles
webforJ est fourni avec plusieurs archétypes prédéfinis pour vous aider à commencer rapidement. Pour une liste complète des archétypes disponibles, consultez le [catalogue des archétypes](../building-ui/archetypes/overview).
:::

En utilisant ces informations, startforJ créera un projet de base à partir de votre archétype choisi avec vos personnalisations choisies. 
Vous pouvez choisir de télécharger votre projet en tant que fichier ZIP ou de le publier directement sur GitHub.

Une fois votre projet téléchargé, ouvrez le dossier du projet dans votre IDE et passez à [l'exécution de l'application](#running-the-app).

## Utilisation de la ligne de commande {#using-the-command-line}

Si vous préférez utiliser la ligne de commande, vous pouvez générer un projet directement en utilisant l'archétype Maven :

<ComponentArchetype
project="hello-world"
flavor="webforj"
/>

## Exécution de l'application {#running-the-app}

Avant d'exécuter votre application, installez les [prérequis](./prerequisites.md) si ce n'est pas déjà fait. 
Ensuite, naviguez jusqu'au répertoire racine du projet et exécutez la commande suivante :

```bash
# pour une application webforj standard
mvn jetty:run

# pour webforj + Spring Boot
mvn spring-boot:run
```

Une fois le serveur en cours d'exécution, ouvrez votre navigateur et allez sur [http://localhost:8080](http://localhost:8080) pour voir l'application.
