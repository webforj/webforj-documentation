---
title: Integrations
hide_table_of_contents: true
sidebar_class_name: new-content
hide_giscus_comments: true
_i18n_hash: 987f1fb9ef8aa9e50ff4ec00320d2dd7
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

webforJ est conçu comme une couche UI indépendante de tout framework pour les applications Java. Il se concentre exclusivement sur la création d'interfaces utilisateur riches et basées sur des composants, tout en laissant entièrement à votre charge les décisions relatives à l'architecture backend. Cette séparation claire des préoccupations permet à webforJ de fonctionner avec n'importe quelle pile technologique Java, des servlets traditionnels aux microservices modernes.

## Philosophie de l'architecture {#architecture-philosophy}

webforJ sépare délibérément les préoccupations UI et backend. Contrairement aux frameworks full-stack, qui dictent toute la structure de votre application, webforJ ne fournit que ce dont vous avez besoin pour créer des interfaces utilisateur sophistiquées. Votre choix de couche de persistance, de framework d'injection de dépendances, de mise en œuvre de la sécurité et d'architecture de services reste complètement indépendant de votre technologie UI.

Cette approche reconnaît que la plupart des organisations ont des modèles backend établis, des couches de service existantes et des piles technologiques privilégiées. webforJ améliore ces applications avec un framework UI moderne sans nécessiter de changements architecturaux ou de migrations technologiques. Votre logique métier, vos modèles d'accès aux données et vos mises en œuvre de sécurité continuent de fonctionner exactement comme avant.

##Compatibilité avec les frameworks backend {#backend-framework-compatibility}

webforJ fonctionne avec n'importe quel framework backend Java ou modèle d'architecture que vous utilisez déjà. Que vous travailliez sur Jakarta EE, que vous utilisiez une architecture de microservices ou que vous travailliez avec un framework personnalisé, webforJ fournit la couche UI sans interférer avec votre conception backend.

Pour certains frameworks populaires, webforJ propose des intégrations spécifiques qui réduisent le code boilerplate et simplifient le développement. Ces intégrations offrent des commodités telles que l'injection de dépendances automatique dans les composants UI, une configuration simplifiée et un support d'outils spécifique au framework. Si vous ne voyez pas votre framework répertorié ci-dessous, cela ne signifie pas que webforJ ne fonctionnera pas avec - cela signifie simplement que vous configurerez la connexion en utilisant les modèles standards de votre framework plutôt qu'en utilisant une intégration préconçue.

Les intégrations ci-dessous sont entièrement optionnelles. Elles existent pour améliorer l'expérience développeur lors de l'utilisation de frameworks spécifiques, mais les fonctionnalités de base de webforJ fonctionnent de manière identique que vous utilisiez ou non une intégration. Votre framework backend continue de gérer les services, l'accès aux données et la logique métier tandis que webforJ s'occupe de la couche de présentation.

## Sujets {#topics}

<DocCardList className="topics-section" />
