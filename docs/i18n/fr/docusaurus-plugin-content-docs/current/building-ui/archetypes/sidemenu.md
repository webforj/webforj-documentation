---
title: SideMenu
sidebar_position: 3
hide_table_of_contents: true
_i18n_hash: 0d0c302e47e1711d573c9bf6860547ae
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

<!-- vale off -->
# Archétype de SideMenu
<!-- vale on -->

Pour les projets qui ont besoin d'un système de navigation structuré, l'archétype `sidemenu` est un excellent point de départ. Cet archétype contient un menu latéral et une zone de contenu, et est conçu pour vous aider à créer des applications avec une structure de navigation claire et intuitive, facilitant la recherche et l'accès à différentes parties de votre application par les utilisateurs.

:::tip Utiliser startforJ
Pour plus de contrôle sur la personnalisation et la configuration, vous pouvez utiliser [startforJ](https://docs.webforj.com/startforj/) pour créer votre projet - il suffit de sélectionner l'archétype `SideMenu` lors du choix des options de configuration.
:::

## Utilisation de l'archétype `sidemenu` {#using-the-sidemenu-archetype}

<ComponentArchetype
project="sidemenu"
/>

## Exécution de l'application {#running-the-app}

Avant d'exécuter votre application, installez les [prérequis](../../introduction/prerequisites) si ce n'est pas encore fait. Ensuite, naviguez jusqu'au répertoire racine du projet et exécutez la commande suivante :

```bash
# pour une application webforJ standard
mvn jetty:run

# pour webforJ + Spring Boot
mvn spring-boot:run
```

Une fois le serveur en cours d'exécution, ouvrez votre navigateur et allez sur [http://localhost:8080](http://localhost:8080) pour voir l'application.
