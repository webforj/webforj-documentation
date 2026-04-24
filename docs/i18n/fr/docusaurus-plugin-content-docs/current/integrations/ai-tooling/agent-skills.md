---
title: Agent Skills
sidebar_position: 10
_i18n_hash: 98d3f61447c289339f92fc4872e734f1
---
Les compétences des agents enseignent aux assistants de codage AI comment créer des applications webforJ en utilisant les API, les jetons de conception et les modèles de composants appropriés. Au lieu de deviner les conventions du cadre, l'assistant charge une compétence et suit un flux de travail structuré pour produire du code qui compile et suit les meilleures pratiques dès le premier essai.

:::tip Utiliser le plugin
Les compétences ci-dessous sont incluses dans le **[plugin AI webforJ](/docs/integrations/ai-tooling)** avec le [serveur MCP](/docs/integrations/ai-tooling/mcp). Une installation donne à votre assistant les deux éléments.
:::

Les compétences suivent la norme ouverte [Agent Skills](https://agentskills.io/specification) et fonctionnent avec de nombreux assistants AI, y compris Claude Code, GitHub Copilot, Cursor, Gemini CLI, OpenAI Codex, et plus encore. Une compétence indique à l'assistant quel type de tâche elle gère ; l'assistant la charge automatiquement lorsque votre prompt correspond. Par exemple, demander "thème cette application avec une palette bleue" déclenche la compétence `webforj-styling-apps`, qui guide l'assistant dans la recherche de jetons DWC valides, l'écriture de CSS scopés, et la validation de chaque nom de variable avant d'écrire quoi que ce soit sur le disque.

## Pourquoi utiliser des compétences ? {#why-use-skills}

Le serveur MCP rend les informations précises sur webforJ disponibles à la demande, mais à lui seul, il ne dit pas à l'assistant _quand_ rechercher quelque chose, _quelle_ approche convient à la tâche, ou _dans quel ordre_ faire les choses. C'est là que les compétences interviennent.

Les compétences donnent à l'assistant un manuel de jeu spécifique à la tâche : comment classer le travail qui se présente à lui, quels modèles webforJ conviennent, quels outils MCP consulter à chaque étape, et comment valider le résultat avant de le renvoyer. Le résultat est un code webforJ cohérent et respectant les conventions, plutôt qu'une collection d'extraits techniquement valides mais stylistiquement incohérents.

## Comment les compétences diffèrent du MCP {#how-skills-differ-from-mcp}

Les compétences et le [serveur MCP webforJ](/docs/integrations/ai-tooling/mcp) remplissent des rôles complémentaires. Le serveur MCP fournit des outils en direct que l'assistant peut appeler pour obtenir des informations ou générer des résultats. Les compétences fournissent le flux de travail qui dit à l'assistant _quand_ utiliser ces outils, dans quel ordre faire les choses, et comment valider le résultat.

| | Serveur MCP | Compétences des agents |
|---|---|---|
| **Ce qu'il fournit** | Outils que l'assistant appelle à la demande (recherche de documents, génération de structures, création de thèmes, validation de jetons) | Flux de travail et tableaux de décision qui guident la façon dont l'assistant aborde une tâche |
| **Quand il agit** | Lorsque l'assistant décide d'appeler un outil | Automatiquement, lorsque l'assistant détecte une tâche correspondante |
| **Meilleur pour** | Répondre à des questions spécifiques, générer des artefacts | Tâches de bout en bout qui nécessitent une approche webforJ cohérente |

En pratique, les deux fonctionnent mieux ensemble - et le [plugin AI webforJ](https://github.com/webforj/webforj-ai) les inclut en une seule installation.

## Installation {#installation}

Installez le **[plugin AI webforJ](/docs/integrations/ai-tooling)** - il inclut les deux compétences ci-dessous avec le serveur MCP. Pour les clients qui ne prennent pas en charge les plugins, le [dépôt AI webforJ](https://github.com/webforj/webforj-ai#clients) liste le répertoire des compétences que chaque outil lit, afin que vous puissiez copier manuellement les dossiers de compétences.

## Compétences disponibles {#available-skills}

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>webforj-creating-components</code></strong>: créer des composants réutilisables webforJ à partir de bibliothèques de composants web, de bibliothèques JavaScript ou de composants webforJ existants
  </AccordionSummary>
  <AccordionDetails>
    <div>

Utilisez ceci lorsque vous avez besoin d'un composant Java réutilisable encapsulé autour de toute source - une bibliothèque d'éléments personnalisés existants, une bibliothèque JavaScript simple, ou une composition de composants webforJ existants. L'assistant choisit la bonne classe de base webforJ pour le travail, connecte les propriétés, les événements et les emplacements avec les modèles corrects, et produit des tests qui suivent les conventions webforJ.

**Quand cela se déclenche**

- *"Enveloppez cette bibliothèque d'éléments personnalisés en tant que composants webforJ."*
- *"Composez ces composants webforJ en une carte réutilisable."*
- *"Intégrez cette bibliothèque JavaScript simple en tant que composant webforJ."*
- *"Exposez cette API du navigateur en tant qu'utilitaire webforJ."*

</div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>webforj-styling-apps</code></strong>: thématiser et styliser les applications webforJ en utilisant le système de jetons de conception DWC
  </AccordionSummary>
  <AccordionDetails>
    <div>

Utilisez ceci pour tout travail visuel sur une application webforJ : reskins de palette, stylisation au niveau des composants, mise en page et espacement, typographie, thèmes complets, apparence des tableaux, ou couleurs coordonnées des Google Charts. L'assistant écrit du CSS qui respecte les jetons de conception DWC, scoupe correctement les sélecteurs, et valide chaque référence `--dwc-*` par rapport au véritable catalogue de votre version webforJ - de sorte que le mode sombre et le changement de thème continuent de fonctionner.

**Quand cela se déclenche**

- *"Thématisez cette application avec une palette bleue."*
- *"Stylisez le dwc-button pour correspondre aux directives de la marque."*
- *"Resserrez cette mise en page - ajustez l'espacement et la typographie."*

</div>
  </AccordionDetails>
</Accordion>
