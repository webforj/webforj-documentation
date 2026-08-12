---
sidebar_position: 1
title: craftforJ
slug: /craftforj
hide_table_of_contents: true
hide_giscus_comments: true
description: >-
  Inspect the component tree of a running webforJ app, change components live,
  and write the changes you keep back into your Java source.
sidebar_class_name: new-content
_i18n_hash: 6b642a9d173c5943acbb99934542e3a3
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

<DocChip chip='since' label='26.02' />

**craftforJ** est l'environnement de développement visuel qui est livré avec webforJ. Il s'exécute à l'intérieur de votre application en mode développement et vous offre une vue en direct des composants que votre code Java a créés. Vous pouvez sélectionner un composant, modifier ses propriétés, voir l'application en cours de fonctionnement se mettre à jour immédiatement et écrire les modifications que vous souhaitez conserver dans le fichier Java qui les a créées.

<!-- INTRO_END -->

Parce que craftforJ lit l'application à travers webforJ lui-même, il décrit l'application dans les termes dans lesquels vous l'avez écrite. L'arbre liste vos composants plutôt que le balisage que le navigateur a rendu, les propriétés sont celles que vos composants déclarent, et les routes sont celles que votre routeur a enregistrées, ainsi que les règles d'accès que vous les avez annotées avec.

<div class="videos-container">
      <video controls>
        <source src="https://cdn.webforj.com/webforj-documentation/video/craftforJ/intro.mp4" type="video/mp4" />
      </video>
</div>

## Ce que vous pouvez en faire {#what-you-can-do-with-it}

- **[Inspecter les composants](/docs/craftforj/inspector)** - parcourez l'arbre des composants, sélectionnez un composant en cliquant dessus sur la page, et modifiez ses propriétés pendant que l'application fonctionne.
- **[Écrire des modifications dans le code source](/docs/craftforj/source-changes)** - examinez vos modifications en direct sous forme de diff et appliquez-les à vos fichiers Java.
- **[Travailler avec les routes](/docs/craftforj/routes)** - consultez la table de routage, naviguez vers n'importe quelle route et modifiez les règles d'accès déclarées dessus.
- **[Personnaliser l'apparence de l'application](/docs/craftforj/theme)** - ajustez les tokens de design à partir desquels votre application est construite et enregistrez le résultat dans votre feuille de style.
- **[Utiliser l'agent AI](/docs/craftforj/ai)** - un agent de codage à l'intérieur de l'application en cours d'exécution qui écrit du Java librement, compile ce qu'il a écrit et l'applique avec votre approbation.

## Comment cela diffère d'un débogueur {#how-it-differs-from-a-debugger}

Un débogueur interrompt votre code et vous montre l'état de vos variables à ce moment-là. craftforJ laisse l'application fonctionner et vous montre l'interface produite par votre code, donc vous travaillez avec le résultat plutôt qu'avec l'exécution. Les deux répondent à des questions différentes et sont couramment utilisés ensemble.

## Mode développement uniquement {#development-mode-only}

craftforJ nécessite que deux paramètres distincts soient activés, et par défaut, il ne répond qu'au navigateur exécuté sur la même machine que l'application. Les projets créés avec [startforJ](https://docs.webforj.com/startforj) ou à partir d'un [archétype](/docs/building-ui/archetypes/overview) webforJ l'activent pour vous, donc il est disponible la première fois que vous les exécutez. Consultez [Sécurité](/docs/craftforj/security) pour savoir ce que craftforJ peut atteindre et comment confirmer qu'il est désactivé en production.

## Sujets {#topics}

<DocCardList className="topics-section" />
