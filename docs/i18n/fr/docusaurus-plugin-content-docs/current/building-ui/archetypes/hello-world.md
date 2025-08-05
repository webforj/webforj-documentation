---
title: HelloWorld
sidebar_position: 4
hide_table_of_contents: true
_i18n_hash: e1da494f783aca68616cd374b92e700c
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

Cet archétype crée une simple application hello world pour démontrer les bases de la construction d'une interface utilisateur avec webforJ. Ce modèle est idéal pour les débutants souhaitant commencer rapidement. Il fournit un exemple simple de la manière de configurer et de faire fonctionner une application webforJ de base, ce qui en fait un excellent point de départ pour les nouveaux développeurs.

:::tip Commencer de zéro
Cet archétype crée une application minimaliste avec quelques composants et un peu de style. Pour les développeurs souhaitant créer un projet avec un minimum de structure, voir l'[`archétype` vide](./blank).
:::

:::tip Utilisation de startforJ
Pour un meilleur contrôle sur la personnalisation et la configuration, vous pouvez utiliser [startforJ](https://docs.webforj.com/startforj/) pour créer votre projet - il vous suffit de sélectionner l'archétype `HelloWorld` lors du choix des options de configuration.
:::

## Utilisation de l'archétype `hello-world` {#using-the-hello-world-archetype}

<ComponentArchetype
project="hello-world"
/>

## Exécution de l'application {#running-the-app}

Avant d'exécuter votre application, installez les [prérequis](../../introduction/prerequisites) si ce n'est pas déjà fait. 
Ensuite, naviguez vers le répertoire racine du projet et exécutez la commande suivante :

```bash
# pour une application webforJ standard
mvn jetty:run

# pour webforJ + Spring Boot
mvn spring-boot:run
```

Une fois le serveur en cours d'exécution, ouvrez votre navigateur et allez à [http://localhost:8080](http://localhost:8080) pour voir l'application.
