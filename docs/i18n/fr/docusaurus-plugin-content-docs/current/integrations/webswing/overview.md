---
title: 'Webswing avec webforJ : moderniser une application Swing étape par étape'
hide_table_of_contents: true
hide_giscus_comments: true
description: >-
  Exécutez une application Swing, JavaFX ou SWT dans le navigateur avec
  Webswing, intégrez-la à webforJ et remplacez-la progressivement par des écrans
  web.
_i18n_title_hash: 698c1a9e98e2ef808c204b0bc4be4669
_i18n_description_hash: 3bd3bdba7583bc1699e8d6153577eb56
_i18n_hash: 53117d208e8a0b0a2e46705f9edd1cdc
---
# Webswing <DocChip chip='since' label='25.10' />

<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

Webswing exécute une application Swing, JavaFX ou SWT dans le navigateur sans modification du code source. Avec webforJ, vous intégrez cette application dans une application web et la remplacez écran par écran. Pour que les deux puissent échanger des messages, vous ajoutez l’API Webswing à l’application Swing.

Webswing est une technologie de serveur web. Elle restitue l’application de bureau sur le serveur et diffuse son interface dans le navigateur à l’aide d’un canevas HTML5, tout en gérant de manière transparente les interactions de l’utilisateur.

Pour avoir un aperçu du parcours complet, de l’exécution de l’application de bureau dans le navigateur jusqu’à son retrait, consultez [Modernisation de Swing](/modernization).

## Ce que Webswing permet de résoudre {#what-webswing-solves}

De nombreuses organisations ont investi de façon importante dans des applications de bureau Java qui contiennent une logique métier critique, développée au fil des ans ou des décennies. Il est souvent difficile de réécrire ces applications en raison des éléments suivants :

- Une logique métier complexe qu’il serait risqué de recréer
- Une intégration à des bibliothèques ou à du matériel propres aux environnements de bureau
- Les contraintes de temps et de coût liées à une réécriture complète
- La nécessité de conserver la parité fonctionnelle avec l’application existante

Webswing permet de rendre ces applications accessibles sur le web sans les modifier. Elles conservent leurs fonctionnalités et leur apparence d’origine.

## Intégration avec webforJ {#integration-with-webforj}

L’intégration Webswing de webforJ fournit le composant `WebswingConnector`, qui permet d’intégrer directement dans votre application webforJ des applications hébergées par Webswing. Cela ouvre des possibilités :

### Modernisation progressive {#progressive-modernization}

Plutôt que d’opter pour une réécriture complète ou de ne rien changer, vous pouvez :

1. Commencer par intégrer votre application Swing existante avec `WebswingConnector`
2. Développer de nouvelles fonctionnalités avec webforJ autour de l’application intégrée
3. Remplacer progressivement les composants Swing par leurs équivalents webforJ
4. Finir par retirer entièrement l’application existante

### Applications hybrides {#hybrid-applications}

Combinez une interface web moderne créée avec webforJ et des fonctionnalités spécialisées de bureau :

- Utilisez webforJ pour les interfaces destinées aux utilisateurs, les tableaux de bord et les rapports
- Conservez Swing pour les visualisations complexes ou les éditeurs spécialisés
- Maintenez une expérience applicative intégrée unique

:::tip Remplacer les équivalents Swing
Consultez [Coming from Swing](/docs/introduction/coming-from-swing) pour comparer les composants Swing à leurs équivalents webforJ. Ces comparaisons incluent des références utiles pour vous aider à utiliser les fonctionnalités de chaque équivalent webforJ.
:::

## Fonctionnement {#how-it-works}

L’intégration fonctionne à travers trois couches :

1. **Serveur Webswing** : exécute votre application de bureau Java, capture son rendu visuel et traite les saisies de l’utilisateur
2. **Composant WebswingConnector** : composant webforJ qui intègre le client Webswing et gère la connexion ainsi que la communication avec le serveur
3. **Protocole de communication** : échange de messages bidirectionnel qui permet à votre application webforJ d’envoyer des commandes à l’application Swing et d’en recevoir des événements

Lorsqu’un utilisateur accède à votre application webforJ, `WebswingConnector` établit une connexion avec le serveur Webswing. Le serveur crée une instance de l’application ou s’y reconnecte, puis commence à diffuser son état visuel dans le navigateur. Les interactions de l’utilisateur (souris, clavier) sont capturées et envoyées au serveur, où elles sont reproduites dans l’application Swing réelle.

## Sujets {#topics}

<DocCardList className="topics-section" />
