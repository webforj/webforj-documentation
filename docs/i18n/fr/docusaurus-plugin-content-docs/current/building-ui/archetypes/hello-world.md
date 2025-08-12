---
title: HelloWorld
sidebar_position: 4
hide_table_of_contents: true
_i18n_hash: 145d1e89a5f688fa0c912b87056a35d1
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

<!-- vale off -->
# Archétype HelloWorld
<!-- vale on -->

Cet archétype crée une application simple "hello world" pour démontrer les bases de la construction d'une interface utilisateur avec webforJ. Ce modèle est idéal pour les débutants afin de commencer rapidement. Il fournit un exemple clair de la manière de configurer et d'exécuter une application webforJ de base, ce qui en fait un excellent point de départ pour les nouveaux développeurs.

:::tip Commencer à partir de zéro
Cet archétype crée une application minimaliste avec quelques composants et un peu de style. Pour les développeurs souhaitant créer un projet avec un minimum d'infrastructure, voir l'[`archétype vide`](./blank).
:::

:::tip Utiliser startforJ
Pour plus de contrôle sur la personnalisation et la configuration, vous pouvez utiliser [startforJ](https://docs.webforj.com/startforj/) pour créer votre projet - il suffit de sélectionner l'archétype `HelloWorld` lors du choix des options de configuration.
:::

## Utilisation de l'archétype `hello-world` {#using-the-hello-world-archetype}

<ComponentArchetype
project="hello-world"
/>

## Exécution de l'application {#running-the-app}

Avant d'exécuter votre application, installez les [prérequis](../../introduction/prerequisites) si vous ne l'avez pas encore fait. 
Ensuite, naviguez vers le répertoire racine du projet et exécutez la commande suivante :

```bash
# pour une application standard webforJ
mvn jetty:run

# pour webforJ + Spring Boot
mvn spring-boot:run
```

Une fois le serveur en cours d'exécution, ouvrez votre navigateur et allez sur [http://localhost:8080](http://localhost:8080) pour voir l'application.
