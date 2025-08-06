---
title: Tabs
sidebar_position: 2
hide_table_of_contents: true
_i18n_hash: ba161760eed1006a71d42f2d566aff54
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

# Archétype des onglets

Le projet de démarrage `tabs` génère une application avec une interface à onglets simple. Idéal pour les projets nécessitant plusieurs vues ou sections accessibles via des onglets, cet archétype offre un moyen clair et organisé de gérer différentes parties de votre application, facilitant la navigation entre les diverses sections sans encombrer l'interface utilisateur.

:::tip Utilisation de startforJ
Pour un meilleur contrôle sur la personnalisation et la configuration, vous pouvez utiliser [startforJ](https://docs.webforj.com/startforj/) pour créer votre projet - il vous suffit de sélectionner l'archétype `Tabs` lors du choix des options de configuration.
:::

## Utilisation de l'archétype `tabs` {#using-the-tabs-archetype}

<ComponentArchetype
project="tabs"
/>

## Exécution de l'application {#running-the-app}

Avant d'exécuter votre application, installez les [prérequis](../../introduction/prerequisites) si ce n'est pas déjà fait. 
Ensuite, naviguez jusqu'au répertoire racine du projet et exécutez la commande suivante :

```bash
# pour une application webforJ standard
mvn jetty:run

# pour webforJ + Spring Boot
mvn spring-boot:run
```

Une fois le serveur en cours d'exécution, ouvrez votre navigateur et allez à [http://localhost:8080](http://localhost:8080) pour voir l'application.
