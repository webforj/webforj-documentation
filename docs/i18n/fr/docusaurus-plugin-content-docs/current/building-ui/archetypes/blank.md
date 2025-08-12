---
title: Blank
sidebar_position: 1
hide_table_of_contents: true
_i18n_hash: 5e7b116f0fea5cee2aa0d880d6fee05a
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

# Archétype vierge

L'archétype `blank` est un projet de démarrage fondamental pour les applications webforJ. Ce modèle fournit une toile vierge pour que vous puissiez construire votre application à partir de zéro. Il est idéal pour les développeurs qui souhaitent un contrôle total sur la structure et les composants de leur application sans aucune contrainte prédéfinie.

:::tip Utiliser startforJ
Pour un plus grand contrôle sur la personnalisation et la configuration, vous pouvez utiliser [startforJ](https://docs.webforj.com/startforj/) pour créer votre projet - il vous suffit de sélectionner l'archétype `Blank` lors du choix des options de configuration.
:::

## Utiliser l'archétype `blank` {#using-the-blank-archetype}

<ComponentArchetype
project="blank"
/>

## Exécuter l'application {#running-the-app}

Avant d'exécuter votre application, installez les [prérequis](../../introduction/prerequisites) si vous ne l'avez pas encore fait. 
Ensuite, naviguez vers le répertoire racine du projet et exécutez la commande suivante :

```bash
# pour une application webforJ standard
mvn jetty:run

# pour webforJ + Spring Boot
mvn spring-boot:run
```

Une fois le serveur en cours d'exécution, ouvrez votre navigateur et allez à [http://localhost:8080](http://localhost:8080) pour voir l'application.
