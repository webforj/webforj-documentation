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
_i18n_hash: e5fd219fd4360d24535c26696d1c9d1d
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

<DocChip chip='since' label='26.02' />

**craftforJ** est l'environnement de développement visuel qui est livré avec webforJ. Il s'exécute à l'intérieur de votre application en mode développement et vous donne une vue en direct des composants que votre code Java a créés. Vous pouvez sélectionner un composant, modifier ses propriétés, voir l'application en cours d'exécution se mettre à jour immédiatement et écrire les modifications que vous souhaitez conserver dans le fichier Java qui les a créées.

<!-- INTRO_END -->

Parce que craftforJ lit l'application par l'intermédiaire de webforJ lui-même, il décrit l'application dans les termes dans lesquels vous l'avez écrite. L'arborescence liste vos composants plutôt que le balisage rendu par le navigateur, les propriétés sont celles que vos composants déclarent, et les itinéraires sont ceux que votre routeur a enregistrés, avec les règles d'accès que vous y avez annotées.

<div class="videos-container">
      <video controls preload="metadata">
        <source src="https://cdn.webforj.com/webforj-documentation/video/craftforJ/intro.mp4#t=0.5" type="video/mp4" />
      </video>
</div>

## Ce que vous pouvez en faire {#what-you-can-do-with-it}

- **[Inspecter les composants](/docs/craftforj/inspector)** - parcourez l'arborescence des composants, sélectionnez un composant en cliquant dessus sur la page, et modifiez ses propriétés pendant que l'application s'exécute.
- **[Écrire les modifications dans la source](/docs/craftforj/source-changes)** - examinez vos modifications en direct sous forme de diff et appliquez-les à vos fichiers Java.
- **[Travailler avec les itinéraires](/docs/craftforj/routes)** - voyez le tableau de routage, naviguez vers n'importe quel itinéraire et changez les règles d'accès déclarées.
- **[Thématiser l'application](/docs/craftforj/theme)** - ajustez les tokens de conception de votre application et enregistrez le résultat dans votre feuille de style.
- **[Utiliser l'agent IA](/docs/craftforj/ai)** - un agent de codage à l'intérieur de l'application en cours d'exécution qui écrit du Java librement, compile ce qu'il a écrit et l'applique avec votre approbation.

## En quoi cela diffère d'un débogueur {#how-it-differs-from-a-debugger}

Un débogueur met votre code en pause et vous montre l'état de vos variables à ce moment-là. craftforJ laisse l'application s'exécuter et vous montre l'interface produite par votre code, vous permettant ainsi de travailler avec le résultat plutôt qu'avec l'exécution. Les deux répondent à des questions différentes et sont souvent utilisés ensemble.

## Mode développement uniquement {#development-mode-only}

craftforJ nécessite que deux paramètres séparés soient activés, et par défaut, il ne répond qu'au navigateur s'exécutant sur la même machine que l'application. Les projets créés avec [startforJ](https://docs.webforj.com/startforj) ou à partir d'un [archétype](/docs/building-ui/archetypes/overview) webforJ l'activent pour vous, de sorte qu'il est disponible la première fois que vous les exécutez. Voir [Sécurité](/docs/craftforj/security) pour ce que craftforJ peut atteindre et comment confirmer qu'il est désactivé en production.

## Sujets {#topics}

<DocCardList className="topics-section" />
