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
_i18n_hash: 727e01ac5960e204363665aba672d6c7
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

<!-- vale off -->
# craftforJ <DocChip chip='since' label='26.02' />
<!-- vale on -->

**craftforJ** est l’environnement de développement visuel fourni avec webforJ. Il s’exécute dans votre application en mode développement et vous donne une vue en direct des composants créés par votre code Java. Vous pouvez sélectionner un composant, modifier ses propriétés, voir l’application en cours d’exécution se mettre à jour immédiatement et réécrire dans le fichier Java qui l’a créé les modifications que vous souhaitez conserver.

<!-- INTRO_END -->

Comme craftforJ lit l’application par l’intermédiaire de webforJ lui-même, il la décrit dans les termes que vous avez utilisés pour l’écrire. L’arborescence répertorie vos composants plutôt que le balisage rendu par le navigateur, les propriétés sont celles que vos composants déclarent et les routes sont celles enregistrées par votre routeur, ainsi que les règles d’accès que vous leur avez associées.

<div class="videos-container">
      <video controls preload="metadata">
        <source src="https://cdn.webforj.com/webforj-documentation/video/craftforJ/intro.mp4#t=0.5" type="video/mp4" />
      </video>
</div>

## Ce que vous pouvez faire avec {#what-you-can-do-with-it}

- **[Inspecter les composants](/docs/craftforj/inspector)** - parcourir l’arborescence des composants, sélectionner un composant en cliquant dessus dans la page et modifier ses propriétés pendant l’exécution de l’application.
- **[Réécrire les modifications dans le code source](/docs/craftforj/source-changes)** - examiner vos modifications en direct sous forme de diff et les appliquer à vos fichiers Java.
- **[Travailler avec les routes](/docs/craftforj/routes)** - consulter la table de routage, accéder à n’importe quelle route et modifier les règles d’accès qui lui sont associées.
- **[Personnaliser le thème de l’application](/docs/craftforj/theme)** - ajuster les jetons de conception qui constituent la base de votre application et enregistrer le résultat dans votre feuille de style.
- **[Utiliser l’agent IA](/docs/craftforj/ai)** - un agent de programmation intégré à l’application en cours d’exécution, qui écrit librement du code Java, compile ce qu’il a écrit et l’applique avec votre approbation.

## En quoi il diffère d’un débogueur {#how-it-differs-from-a-debugger}

Un débogueur met votre code en pause et vous montre l’état de vos variables à cet instant. craftforJ laisse l’application s’exécuter et vous montre l’interface produite par votre code : vous travaillez donc avec le résultat plutôt qu’avec l’exécution. Ces outils répondent à des questions différentes et sont souvent utilisés ensemble.

## Uniquement en mode développement {#development-mode-only}

craftforJ nécessite l’activation de deux paramètres distincts et, par défaut, ne répond qu’au navigateur exécuté sur la même machine que l’application. Les projets créés avec [startforJ](https://docs.webforj.com/startforj) ou à partir d’un [archetype](/docs/building-ui/archetypes/overview) webforJ l’activent automatiquement : il est donc disponible dès leur première exécution. Consultez la page [Sécurité](/docs/craftforj/security) pour savoir à quoi craftforJ peut accéder et comment vérifier qu’il est désactivé en production.

## Sujets {#topics}

<DocCardList className="topics-section" />
