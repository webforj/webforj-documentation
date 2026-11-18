---
title: Webswing
hide_table_of_contents: true
hide_giscus_comments: true
description: >-
  Intégrez les applications Swing, JavaFX ou SWT existantes dans l’interface
  webforJ via Webswing pour moderniser progressivement et créer des expériences
  hybrides.
_i18n_title_hash: 2e5d4e6133d5cb0ee82f8b6e37cd0767
_i18n_description_hash: 349a14bb13871c58d909824c9d874837
_i18n_hash: 300c45aa8cc9f51831219f75d74d31b6
---
# Webswing <DocChip chip='since' label='25.10' />

<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

Webswing est une technologie de serveur web qui permet d’exécuter des applications Java de bureau (Swing, JavaFX, SWT) dans un navigateur web sans modifier le code source d’origine. Elle exécute l’application de bureau sur le serveur et diffuse son interface dans le navigateur à l’aide d’un canevas HTML5, tout en gérant de manière transparente les interactions de l’utilisateur.

## Ce que Webswing permet de résoudre {#what-webswing-solves}

De nombreuses organisations ont beaucoup investi dans des applications Java de bureau qui contiennent une logique métier essentielle, développée au fil des ans, voire des décennies. Il est souvent difficile de réécrire ces applications en raison des facteurs suivants :

- Une logique métier complexe qu’il serait risqué de recréer
- Une intégration avec des bibliothèques ou du matériel propres aux applications de bureau
- Les contraintes de temps et de coût liées à une réécriture complète
- La nécessité de préserver la parité fonctionnelle avec les fonctionnalités existantes

Webswing permet de rendre ces applications accessibles sur le web sans les modifier, tout en préservant leurs fonctionnalités et leur apparence d’origine.

## Intégration avec webforJ {#integration-with-webforj}

L’intégration de Webswing à webforJ fournit le composant `WebswingConnector`, qui vous permet d’intégrer directement dans votre application webforJ des applications hébergées sur Webswing. Cela ouvre la voie à plusieurs possibilités :

### Modernisation progressive {#progressive-modernization}

Au lieu de réécrire l’application entièrement ou pas du tout, vous pouvez :

1. Commencer par intégrer votre application Swing existante avec `WebswingConnector`
2. Créer de nouvelles fonctionnalités dans webforJ autour de l’application intégrée
3. Remplacer progressivement les composants Swing par leurs équivalents webforJ
4. Éliminer à terme l’application héritée

### Applications hybrides {#hybrid-applications}

Associez une interface web moderne créée avec webforJ à des fonctionnalités de bureau spécialisées :

- Utiliser webforJ pour les interfaces destinées aux utilisateurs, les tableaux de bord et les rapports
- Tirer parti de Swing pour les visualisations complexes ou les éditeurs spécialisés
- Préserver une expérience unifiée dans une seule application

:::tip Remplacer les équivalents Swing
Consultez [Passer de Swing à webforJ](/docs/introduction/coming-from-swing) pour comparer les composants Swing à leurs équivalents dans webforJ. Ces comparaisons incluent des références pertinentes pour vous aider à utiliser chaque fonctionnalité équivalente de webforJ.
:::

## Fonctionnement {#how-it-works}

L’intégration repose sur trois couches :

1. **Serveur Webswing** : exécute votre application Java de bureau, capture son rendu visuel et traite les entrées de l’utilisateur
2. **Composant WebswingConnector** : composant webforJ qui intègre le client Webswing et gère la connexion ainsi que la communication avec le serveur
3. **Protocole de communication** : messagerie bidirectionnelle qui permet à votre application webforJ d’envoyer des commandes à l’application Swing et d’en recevoir des événements

Lorsqu’un utilisateur accède à votre application webforJ, `WebswingConnector` établit une connexion avec le serveur Webswing. Le serveur crée une instance de l’application ou s’y reconnecte, puis commence à diffuser son état visuel dans le navigateur. Les interactions de l’utilisateur (souris, clavier) sont capturées et envoyées au serveur, où elles sont reproduites dans l’application Swing proprement dite.

## Thèmes {#topics}

<DocCardList className="topics-section" />
