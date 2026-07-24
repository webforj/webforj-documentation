---
title: Overview
description: >-
  Follow a six-step tutorial to build a customer management app with webforJ and
  Spring Boot, adding routing, data binding, and layout.
hide_giscus_comments: true
_i18n_hash: afa15cff5443cd3aab487ad1c8e8b0de
---
Ce tutoriel étape par étape vous guide à travers le processus de création d'une application de gestion de clients en utilisant webforJ et Spring Boot. Il vous apprend à créer une interface moderne et conviviale pour visualiser, ajouter et modifier les données des clients.

Chaque étape présente de nouveaux concepts et aboutit à une application Spring Boot exécutable (JAR). Vous pouvez lancer votre application localement en utilisant Maven et interagir avec elle dans un navigateur web. Avec cette configuration, vous bénéficiez d'un cycle de développement rapide avec regroupement frontend et d'un modèle de déploiement prêt pour la production, utilisant le serveur embarqué de Spring Boot.

Aucune expérience préalable avec Spring Boot ou webforJ n'est nécessaire, mais vous devriez avoir une compréhension de base de Java et de Maven pour tirer le meilleur parti de ce tutoriel. Ce tutoriel couvrira les concepts Spring au fur et à mesure, mais ceux qui souhaitent une compréhension approfondie de Spring peuvent consulter [la documentation principale de Spring](https://spring.io/learn) et la documentation de Spring concernant [Spring Boot](https://docs.spring.io/spring-boot/index.html).

## Concepts du tutoriel {#tutorial-concepts}

La première partie du tutoriel est consacrée à [la configuration du projet](/docs/introduction/tutorial/project-setup) pour préparer votre environnement Spring Boot + webforJ. Ensuite, les étapes suivantes introduisent de nouvelles fonctionnalités et font progresser votre projet. En suivant le tutoriel, vous obtiendrez une compréhension claire de l'évolution d'une application à mesure que vous implémentez des fonctionnalités.

Chaque étape a une application exécutable correspondante disponible sur GitHub :

| Étape | Documentation | GitHub |
| ----- | ----- | ----- |
| 1 | [Création d'une application de base](/docs/introduction/tutorial/creating-a-basic-app)                               | [Application étape 1](https://github.com/webforj/webforj-tutorial/tree/main/1-creating-a-basic-app)
| 2 | [Travail avec les données](/docs/introduction/tutorial/working-with-data)                                     | [Application étape 2](https://github.com/webforj/webforj-tutorial/tree/main/2-working-with-data)
| 3 | [Routage et composites](/docs/introduction/tutorial/routing-and-composites)                           | [Application étape 3](https://github.com/webforj/webforj-tutorial/tree/main/3-routing-and-composites)
| 4 | [Observateurs et paramètres de route](/docs/introduction/tutorial/observers-and-route-parameters)           | [Application étape 4](https://github.com/webforj/webforj-tutorial/tree/main/4-observers-and-route-parameters)
| 5 | [Validation et liaison des données](/docs/introduction/tutorial/validating-and-binding-data)                 | [Application étape 5](https://github.com/webforj/webforj-tutorial/tree/main/5-validating-and-binding-data)
| 6 | [Intégration d'une mise en page d'application](/docs/introduction/tutorial/integrating-an-app-layout)                     | [Application étape 6](https://github.com/webforj/webforj-tutorial/tree/main/6-integrating-an-app-layout)

## Prérequis {#prerequisites}

Vous devez disposer des outils/ressources suivants sur votre machine de développement :

- Java 21 ou 25
- Maven
- Un IDE Java
- Git (recommandé mais pas nécessaire)

:::info Prérequis webforJ
Consultez l'[article sur les prérequis](/docs/introduction/prerequisites) pour un aperçu plus détaillé des outils nécessaires à votre environnement de développement.
:::

<DocCardList className="topics-section" />
