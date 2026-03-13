---
title: Overview
hide_giscus_comments: true
sidebar_class_name: updated-content
_i18n_hash: 9dc213747204cb02b3b1624b39293445
---
Ce tutoriel étape par étape vous guide à travers le processus de création d'une application de gestion de clients en utilisant webforJ et Spring Boot. Il vous enseigne comment créer une interface moderne et conviviale pour visualiser, ajouter et modifier les données des clients.

Chaque étape introduit de nouveaux concepts et aboutit à une application Spring Boot exécutable (JAR). Vous pouvez lancer votre application localement en utilisant Maven et interagir avec elle dans un navigateur web. Avec cette configuration, vous bénéficiez d'un cycle de développement rapide et d'un modèle de déploiement prêt pour la production, en utilisant le serveur intégré de Spring Boot.

Aucune expérience préalable avec Spring Boot ou webforJ n'est nécessaire, mais vous devriez avoir une compréhension de base de Java et de Maven pour tirer le meilleur parti de ce tutoriel. Ce tutoriel couvrira les concepts de Spring au fur et à mesure, mais ceux qui souhaitent avoir une compréhension approfondie de Spring peuvent consulter [la documentation principale de Spring](https://spring.io/learn) et la documentation de Spring sur [Spring Boot](https://docs.spring.io/spring-boot/index.html).

## Concepts du tutoriel {#tutorial-concepts}

La première partie du tutoriel est dédiée à [la configuration du projet](/docs/introduction/tutorial/project-setup) pour préparer votre environnement Spring Boot + webforJ. Ensuite, les étapes suivantes présentent de nouvelles fonctionnalités et avancent votre projet. En suivant ce tutoriel, vous acquérez une compréhension claire de l'évolution d'une application au fur et à mesure de l'implémentation des fonctionnalités.

Chaque étape a une application exécutable correspondante disponible sur GitHub :

| Étape | Documentation | GitHub |
| ----- | ----- | ----- |
| 1 | [Créer une application de base](/docs/introduction/tutorial/creating-a-basic-app)                               | [Application étape 1](https://github.com/webforj/webforj-tutorial/tree/main/1-creating-a-basic-app)
| 2 | [Travailler avec des données](/docs/introduction/tutorial/working-with-data)                                     | [Application étape 2](https://github.com/webforj/webforj-tutorial/tree/main/2-working-with-data)
| 3 | [Routage et composites](/docs/introduction/tutorial/routing-and-composites)                           | [Application étape 3](https://github.com/webforj/webforj-tutorial/tree/main/3-routing-and-composites)
| 4 | [Observateurs et paramètres de route](/docs/introduction/tutorial/observers-and-route-parameters)           | [Application étape 4](https://github.com/webforj/webforj-tutorial/tree/main/4-observers-and-route-parameters)
| 5 | [Validation et liaison des données](/docs/introduction/tutorial/validating-and-binding-data)                 | [Application étape 5](https://github.com/webforj/webforj-tutorial/tree/main/5-validating-and-binding-data)

## Prérequis {#prerequisites}

Vous devez disposer des outils/ressources suivants sur votre machine de développement :

- Java 17 ou 21
- Maven
- Un IDE Java
- Git (recommandé mais non requis)

:::info Prérequis webforJ
Consultez l'[article sur les prérequis](/docs/introduction/prerequisites) pour une vue d'ensemble plus détaillée des outils requis pour votre environnement de développement.
:::

<DocCardList className="topics-section" />
