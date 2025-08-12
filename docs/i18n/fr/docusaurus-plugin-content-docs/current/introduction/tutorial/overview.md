---
title: Overview
hide_giscus_comments: true
_i18n_hash: 4174ea766ba47277c5bcb607c4111e29
---
Ce tutoriel est conçu pour vous guider étape par étape à travers le processus de création de l'application. Cette application, conçue pour gérer les informations des clients, démontre comment utiliser webforJ pour construire une interface fonctionnelle et conviviale avec des fonctionnalités pour visualiser, ajouter et modifier les données des clients. Chaque section s'appuiera sur la précédente, mais n'hésitez pas à avancer selon vos besoins.

Chaque étape du tutoriel aboutira à un programme qui se compile en un fichier WAR, qui peut être déployé sur n'importe quel serveur d'application web Java. Pour ce tutoriel, le plugin Maven Jetty sera utilisé pour déployer l'application localement. Cette configuration légère garantit que l'application peut s'exécuter rapidement et que les modifications seront visibles en temps réel pendant le développement.

## Fonctionnalités de l'application tutoriel {#tutorial-app-features}

 - Travailler avec des données dans un tableau.
 - Utilisation de l[`ObjectTable`](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/environment/ObjectTable.html) et gestion des actifs.
 - [Routage](../../routing/overview) et [navigation](../../routing/route-navigation)
 - [Liaisons de données](../../data-binding/overview) et [validation](../../data-binding/validation/overview)

## Prérequis {#prerequisites}

Pour tirer le meilleur parti de ce tutoriel, il est supposé que vous avez une compréhension de base de la programmation Java et que vous êtes familier avec des outils comme Maven. Si vous êtes nouveau dans webforJ, ne vous inquiétez pas - les fondamentaux du framework seront abordés en cours de route.

Les outils / ressources suivants doivent être présents sur votre machine de développement

<!-- vale off -->
- Java 17 ou supérieur
- Maven
- Un IDE Java
- Un navigateur web
- Git (recommandé mais pas obligatoire)
<!-- vale on -->

:::tip Conditions préalables webforJ
Voir [cet article](../prerequisites) pour un aperçu plus détaillé des outils requis.
:::

## Sections {#sections}

Le tutoriel est divisé en sections suivantes. Procédez séquentiellement pour un parcours complet, ou avancez pour obtenir des informations spécifiques.

:::tip Configuration du projet
Pour ceux qui souhaitent avancer vers des sujets spécifiques, il est recommandé de lire d'abord la section Configuration du Projet avant de continuer. 
:::

<DocCardList className="topics-section" />
