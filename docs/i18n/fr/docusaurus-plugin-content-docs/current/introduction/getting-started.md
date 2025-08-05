---
title: Getting Started
sidebar_position: 2
_i18n_hash: 37b65c983d6210a89474156b10af1e93
---
Cet article décrit les étapes pour créer une nouvelle application webforJ en utilisant les [archétypes](../building-ui/archetypes/overview.md) webforJ. Les archétypes fournissent des structures de projet préconfigurées et du code de démarrage afin que vous puissiez rapidement mettre en place un projet.

Pour créer une nouvelle application webforJ à partir d'un archétype, vous pouvez utiliser [startforJ](#using-startforj) ou la [ligne de commande](#using-the-command-line).

:::tip Prérequis
Avant de commencer, examinez les [prérequis](./prerequisites) nécessaires pour configurer et utiliser webforJ.
:::

<!-- vale off -->
import DocCardList from '@theme/DocCardList';

<!-- vale on -->

## Utilisation de startforJ {#using-startforj}

La façon la plus simple de créer une nouvelle application webforJ est [startforJ](https://docs.webforj.com/startforj), qui génère un projet de démarrage minimal basé sur un archétype webforJ choisi. Ce projet de démarrage comprend toutes les dépendances requises, des fichiers de configuration et une mise en page préétablie, de sorte que vous puissiez commencer à construire immédiatement.

<div class="videos-container">
  <video controls>
    <source src="https://cdn.webforj.com/webforj-documentation/video/archetypes/startforj.mp4" type="video/mp4" />
  </video>
</div>

### Personnalisation avec startforJ {#customizing-with-startforj}

Lorsque vous créez une application avec [startforJ](https://docs.webforj.com/startforj), vous pouvez la personnaliser en fournissant les informations suivantes :

- Métadonnées de base du projet (Nom de l'application, ID de groupe, ID de l'artéfact)
- Version de webforJ et version de Java
- Couleur et icône de thème
- Archétype
- Saveur

Il existe deux options de saveur parmi lesquelles choisir, "webforJ Only" étant la valeur par défaut :
  - **webforJ Only** : Application webforJ standard
  - **webforJ + Spring Boot** : Application webforJ avec support Spring Boot

:::caution Support Spring Boot
La saveur Spring Boot n'est disponible que dans les versions de webforJ 25.02 et supérieures. Si vous choisissez cette option, assurez-vous de sélectionner une version compatible.
:::

:::tip Archétypes disponibles
webforJ propose plusieurs archétypes prédéfinis pour vous aider à démarrer rapidement. Pour une liste complète des archétypes disponibles, consultez le [catalogue des archétypes](../building-ui/archetypes/overview).
:::

En utilisant ces informations, startforJ créera un projet de base à partir de l'archétype choisi avec vos personnalisations sélectionnées. 
Vous pouvez choisir de télécharger votre projet sous forme de fichier ZIP ou de le publier directement sur GitHub.

Une fois que vous avez téléchargé votre projet, ouvrez le dossier du projet dans votre IDE et passez à [l’exécution de l'application](#running-the-app).

## Utilisation de la ligne de commande {#using-the-command-line}

Si vous préférez utiliser la ligne de commande, vous pouvez générer un projet directement à l'aide de l'archétype Maven :

<ComponentArchetype
project="hello-world"
flavor="webforj"
/>

## Exécution de l'application {#running-the-app}

Avant d'exécuter votre application, installez les [prérequis](./prerequisites.md) si vous ne l'avez pas encore fait.
Ensuite, naviguez jusqu'au répertoire racine du projet et exécutez la commande suivante :

```bash
# pour l'application webforj standard
mvn jetty:run

# pour webforj + Spring Boot
mvn spring-boot:run
```

Une fois le serveur en cours d'exécution, ouvrez votre navigateur et allez à [http://localhost:8080](http://localhost:8080) pour voir l'application.

:::info Licence et filigrane
Pour des informations sur le filigrane présent dans les projets non licenciés, consultez [Licence et filigrane](../configuration/licensing-and-watermark).
:::
