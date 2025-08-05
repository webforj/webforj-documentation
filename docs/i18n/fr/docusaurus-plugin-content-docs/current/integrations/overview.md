---
title: Integrations
hide_table_of_contents: true
sidebar_class_name: new-content
hide_giscus_comments: true
_i18n_hash: 366829e324b872af8247a509f9c55783
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

webforJ est conçu comme une couche UI indépendante de tout framework pour les applications Java. Il se concentre exclusivement sur la création d'interfaces utilisateur riches et basées sur des composants, tout en laissant entièrement à vous le choix de l'architecture backend. Cette séparation claire des préoccupations permet à webforJ de fonctionner avec n'importe quelle pile technologique Java, des servlets traditionnels aux microservices modernes.

## Philosophie de l'architecture {#architecture-philosophy}

webforJ sépare délibérément les préoccupations UI et backend. Contrairement aux cadres full-stack, qui dictent la structure complète de votre application, webforJ ne fournit que ce dont vous avez besoin pour créer des interfaces utilisateur sophistiquées. Votre choix de couche de persistance, de framework d'injection de dépendances, de mise en œuvre de la sécurité et d'architecture de services reste complètement indépendant de votre technologie UI.

Cette approche reconnaît que la plupart des organisations ont des modèles backend établis, des couches de service existantes et des stacks technologiques préférés. webforJ améliore ces applications avec un cadre UI moderne sans nécessiter de changements architecturaux ou de migrations technologiques. Votre logique de domaine, vos modèles d'accès aux données et vos implémentations de sécurité continuent de fonctionner exactement comme auparavant.

## Compatibilité des frameworks backend {#backend-framework-compatibility}

webforJ fonctionne avec n'importe quel framework backend Java ou modèle d'architecture que vous utilisez déjà. Que vous vous appuyiez sur Jakarta EE, utilisiez une architecture de microservices ou travailliez avec un framework personnalisé, webforJ fournit la couche UI sans interférer avec votre conception backend.

Pour certains frameworks populaires, webforJ offre des intégrations spécifiques qui réduisent le code standard et simplifient le développement. Ces intégrations offrent des commodités telles que l'injection automatique de dépendances dans les composants UI, une configuration simplifiée et un support d'outils spécifiques au framework. Si vous ne voyez pas votre framework dans la liste ci-dessous, cela ne signifie pas que webforJ ne fonctionnera pas avec - cela signifie simplement que vous configurerez la connexion en utilisant les modèles standard de votre framework plutôt qu'une intégration pré-construite.

Les intégrations ci-dessous sont entièrement optionnelles. Elles existent pour améliorer l'expérience des développeurs lors de l'utilisation de frameworks spécifiques, mais les fonctionnalités essentielles de webforJ fonctionnent de manière identique que vous utilisiez une intégration ou non. Votre framework backend continue de gérer les services, l'accès aux données et la logique métier pendant que webforJ s'occupe de la couche de présentation.

## Thèmes {#topics}

<DocCardList className="topics-section" />
