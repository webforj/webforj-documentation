---
title: Blank
sidebar_position: 1
hide_table_of_contents: true
_i18n_hash: 135ed95be60a01a6a5ccb297c6bcce8f
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

# Archétype vierge

L'archetype `blank` est un projet de démarrage fondamental pour les applications webforJ. Ce modèle offre une toile vierge pour construire votre application de zéro. Il est idéal pour les développeurs qui souhaitent un contrôle total sur la structure et les composants de leur application sans contraintes prédéfinies.

:::tip Utiliser startforJ
Pour un contrôle accru sur la personnalisation et la configuration, vous pouvez utiliser [startforJ](https://docs.webforj.com/startforj/) pour créer votre projet - il suffit de sélectionner l'archetype `Blank` lors du choix des options de configuration.
:::

## Utilisation de l'archetype `blank` {#using-the-blank-archetype}

<ComponentArchetype
project="blank"
/>

## Exécution de l'application {#running-the-app}

Avant de lancer votre application, installez les [prérequis](../../introduction/prerequisites) si ce n'est pas déjà fait. 
Ensuite, naviguez jusqu'au répertoire racine du projet et exécutez la commande suivante :

```bash
# pour l'application webforJ standard
mvn jetty:run

# pour webforJ + Spring Boot
mvn spring-boot:run
```

Une fois le serveur en cours d'exécution, ouvrez votre navigateur et allez sur [http://localhost:8080](http://localhost:8080) pour voir l'application.
