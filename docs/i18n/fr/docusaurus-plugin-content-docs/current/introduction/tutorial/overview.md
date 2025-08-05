---
title: Overview
hide_giscus_comments: true
_i18n_hash: 4d70b1e894fa3ca05afb5a4bc6ed982d
---
Ce tutoriel est conçu pour vous guider étape par étape dans le processus de création de l'application. Cette application, conçue pour gérer les informations des clients, démontre comment utiliser webforJ pour construire une interface fonctionnelle et conviviale avec des fonctionnalités pour visualiser, ajouter et modifier les données des clients. Chaque section s'appuiera sur la précédente, mais n'hésitez pas à avancer selon vos besoins.

Chaque étape du tutoriel aboutira à un programme qui se compile en un fichier WAR, qui peut être déployé sur n'importe quel serveur d'application web Java. Pour ce tutoriel, le plugin Maven Jetty sera utilisé pour déployer l'application localement. Cette configuration légère garantit que l'application peut fonctionner rapidement et que les modifications seront visibles en temps réel pendant le développement.

## Fonctionnalités de l'application tutoriel {#tutorial-app-features}

 - Travailler avec des données dans un tableau.
 - Utiliser le [`ObjectTable`](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/environment/ObjectTable.html) et la gestion des actifs.
 - [Routage](../../routing/overview) et [navigation](../../routing/route-navigation)
 - [Liens de données](../../data-binding/overview) et [validation](../../data-binding/validation/overview)

## Prérequis {#prerequisites}

Pour tirer le meilleur parti de ce tutoriel, il est supposé que vous avez une compréhension de base de la programmation Java et êtes familiarisé avec des outils comme Maven. Si vous êtes nouveau dans webforJ, ne vous inquiétez pas - les bases du framework seront couvertes en cours de route.

Les outils/ressources suivants devraient être présents sur votre machine de développement

<!-- vale off -->
- Java 17 ou supérieur
- Maven
- Un IDE Java
- Un navigateur web
- Git (recommandé mais pas obligatoire)
<!-- vale on -->

:::tip Prérequis webforJ
Consultez [cet article](../prerequisites) pour un aperçu plus détaillé des outils requis.
:::

## Sections {#sections}

Le tutoriel est divisé en plusieurs sections. Procédez séquentiellement pour un parcours complet, ou avancez pour des informations spécifiques.

:::tip Configuration du projet
Pour ceux qui cherchent à avancer vers des sujets spécifiques, il est recommandé de lire d'abord la section Configuration du projet avant de continuer. 
:::

<DocCardList className="topics-section" />
