---
title: Intégrations
hide_table_of_contents: true
hide_giscus_comments: true
sidebar_class_name: has-new-content
description: >-
  Associez webforJ à Spring Boot, Spring Data, Kotlin DSL, Webswing, JBang et
  des outils d’IA tout en gardant votre pile backend indépendante.
_i18n_title_hash: e01aecb528730f3bfe10f9d57f1317bf
_i18n_description_hash: 7852312ad863b9413ed92d7f27e590d1
_i18n_hash: 2272fe4265260266e667c1b22703a3b6
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

webforJ est le framework Java full-stack pour les applications web métier, et il est indépendant du backend. Vous développez l’application entière en Java, des écrans et du routage aux formulaires, en passant par la liaison de données et la sécurité des routes, tandis que vous gardez le choix de votre couche de persistance, de l’injection de dépendances, des services et de l’architecture backend. C’est pourquoi webforJ fonctionne avec n’importe quelle pile technologique Java, des servlets traditionnels à Spring Boot et aux microservices.

## Philosophie de l’architecture {#architecture-philosophy}

webforJ couvre tout ce dont votre application a besoin pour présenter des données aux utilisateurs et leur permettre d’interagir avec celles-ci : composants, mises en page, routage, formulaires avec liaison et validation, tableaux adossés à des dépôts, sécurité des routes et traduction. Il n’impose pas le fonctionnement du reste de votre système. Le choix de votre couche de persistance, de votre framework d’injection de dépendances, de votre fournisseur d’identité et de votre architecture de services reste indépendant de webforJ.

Cette approche tient compte du fait que la plupart des organisations ont déjà établi des modèles backend, des couches de services existantes et des piles technologiques de prédilection. webforJ amène ces applications sur le web sans nécessiter de changements architecturaux ni de migrations technologiques. Votre logique métier, vos modèles d’accès aux données et vos implémentations de sécurité continuent de fonctionner exactement comme avant.

## Compatibilité avec les frameworks backend {#backend-framework-compatibility}

webforJ fonctionne avec tous les frameworks backend Java ou modèles d’architecture que vous utilisez déjà. Que vous développiez avec Jakarta EE, que vous utilisiez une architecture de microservices ou que vous travailliez avec un framework personnalisé, webforJ s’y connecte sans interférer avec la conception de votre backend.

Pour certains frameworks populaires, webforJ propose des intégrations spécifiques qui réduisent le code répétitif et simplifient le développement. Ces intégrations offrent des fonctionnalités pratiques telles que l’injection automatique de dépendances dans les composants d’interface utilisateur, une configuration simplifiée et la prise en charge d’outils propres au framework. Si votre framework ne figure pas dans la liste ci-dessous, cela ne signifie pas que webforJ est incompatible. Cela signifie que vous configurerez la connexion selon les pratiques standard de votre framework, plutôt qu’en utilisant une intégration préconfigurée.

Les intégrations ci-dessous sont entièrement facultatives. Elles visent à améliorer l’expérience des développeurs qui utilisent des frameworks spécifiques, mais les fonctionnalités de base de webforJ fonctionnent de la même manière, que vous utilisiez une intégration ou non. Votre framework backend continue de gérer les services, l’accès aux données et la logique métier, tandis que webforJ prend en charge les écrans, la navigation et les interactions utilisateur.

## Sujets {#topics}

<DocCardList className="topics-section" />
