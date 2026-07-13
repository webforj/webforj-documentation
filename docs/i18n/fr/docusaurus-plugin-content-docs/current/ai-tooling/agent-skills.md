---
title: Agent Skills
sidebar_position: 10
description: >-
  Install Agent Skills so AI coding assistants follow webforJ workflows for
  building forms, adding servlets, styling apps, and creating components.
_i18n_hash: 6dc21bfd21fb27f2e71cb2265f6cde8c
---
Les compétences des agents enseignent aux assistants de codage IA comment construire des applications webforJ en utilisant les API appropriées, les jetons de conception et les modèles de composants. Au lieu de deviner les conventions du framework, l'assistant charge une compétence et suit un flux de travail structuré pour produire du code qui se compile et suit les meilleures pratiques dès le premier essai.

:::tip Utilisez le plugin
Les compétences ci-dessous sont fournies dans le **[plugin IA webforJ](/docs/ai-tooling)** avec le [serveur MCP](/docs/ai-tooling/mcp). Une seule installation donne à votre assistant les deux éléments.
:::

Les compétences suivent la norme ouverte [Agent Skills](https://agentskills.io/specification) et fonctionnent avec de nombreux assistants IA, y compris Claude Code, GitHub Copilot, Cursor, Gemini CLI, OpenAI Codex, et plus encore. Une compétence indique à l'assistant quel type de tâche elle gère ; il la charge automatiquement lorsque votre demande correspond. Par exemple, demander "thématisez cette application avec une palette bleue" déclenche la compétence `webforj-styling-apps`, qui guide l'assistant dans la recherche de jetons DWC valides, l'écriture de CSS ciblé et la validation de chaque nom de variable avant d'écrire quoi que ce soit sur disque.

## Pourquoi utiliser des compétences ? {#why-use-skills}

Le serveur MCP rend les informations webforJ disponibles à la demande, mais à lui seul, il ne dit pas à l'assistant _quand_ rechercher quelque chose, _quelle_ approche convient à la tâche, ou _dans quel ordre_ procéder. C'est là que les compétences entrent en jeu.

Les compétences donnent à l'assistant un cahier des charges spécifique à la tâche : comment classifier le travail devant lui, quels modèles webforJ conviennent, quels outils MCP consulter à chaque étape, et comment valider le résultat avant de le retourner. Le résultat est un code webforJ cohérent et respectant les conventions plutôt qu'un ensemble de snippets techniquement valides mais stylistiquement dépareillés.

## Comment les compétences diffèrent du MCP {#how-skills-differ-from-mcp}

Les compétences et le [serveur MCP webforJ](/docs/ai-tooling/mcp) remplissent des rôles complémentaires. Le serveur MCP fournit des outils en direct que l'assistant peut appeler pour obtenir des informations ou générer des résultats. Les compétences fournissent le flux de travail qui indique à l'assistant _quand_ utiliser ces outils, dans quel ordre faire les choses, et comment valider le résultat.

| | Serveur MCP | Compétences des agents |
|---|---|---|
| **Ce qu'il fournit** | Outils que l'assistant appelle à la demande (recherche de documents, génération de structures, validation de jetons) | Flux de travail et tableaux de décision qui guident l'approche de l'assistant pour une tâche |
| **Quand il agit** | Lorsque l'assistant décide d'appeler un outil | Automatiquement, lorsque l'assistant détecte une tâche correspondante |
| **Meilleur pour** | Répondre à des questions spécifiques, générer des artefacts | Tâches de bout en bout qui nécessitent une approche webforJ cohérente |

Dans la pratique, les deux fonctionnent mieux ensemble - et le [plugin IA webforJ](https://github.com/webforj/webforj-ai) les expédie en une seule installation.

## Installation {#installation}

Installez le **[plugin IA webforJ](/docs/ai-tooling)** - il fournit les deux compétences ci-dessous avec le serveur MCP. Pour les clients qui ne prennent pas en charge les plugins, le [dépôt IA webforJ](https://github.com/webforj/webforj-ai#clients) répertorie le répertoire de compétences que chaque outil lit, afin que vous puissiez copier les dossiers de compétences manuellement.

## Compétences disponibles {#available-skills}

<AccordionGroup>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>webforj-adding-servlets</code></strong>: ajouter des points de terminaison REST, des webhooks et des servlets personnalisés
  </AccordionSummary>
  <AccordionDetails>
    <div>

Utilisez ceci lorsque vous avez besoin d'un chemin HTTP non-UI - un point de terminaison REST, un gestionnaire de webhook, ou un servlet tiers tel que Swagger UI ou Spring Web. L'assistant choisit la bonne approche pour votre projet (Spring `webforj.exclude-urls`, remappage de `WebforjServlet` vers un sous-chemin, ou proxy à travers `webforj.conf`) et relie le point de terminaison sans perturber le routage UI de webforJ.

**Quand ça commence**

- *"Ajoutez un point de terminaison REST à `/api/orders`."*
- *"Reliez un gestionnaire de webhook pour Stripe."*
- *"Montez Swagger UI à `/api/docs`."*
- *"Exposez un servlet personnalisé qui fonctionne aux côtés de l'UI de webforJ."*

</div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>webforj-building-forms</code></strong>: construire des formulaires avec liaison, validation et masques d'entrée
  </AccordionSummary>
  <AccordionDetails>
    <div>

Utilisez ceci pour tout travail de formulaire dans une application webforJ : formulaires de saisie de données, liaison bidirectionnelle avec un bean Java, validation Jakarta, composants d'entrée masqués (téléphone, devise, IBAN, dates), formatage des colonnes de tableau en tant que devise ou pourcentage, et mises en page réactives à plusieurs colonnes. L'assistant passe par `BindingContext`, les composants `Masked*Field`, les rendus de masque de tableau, et `ColumnsLayout`.

**Quand ça commence**

- *"Construisez un formulaire d'inscription lié à mon bean `User`."*
- *"Ajoutez un champ d'entrée pour le numéro de téléphone avec formatage à la saisie."*
- *"Formattez cette colonne de tableau en tant que devise."*
- *"Validez ce champ avec `@NotEmpty` et un vérificateur d'email personnalisé."*

</div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>webforj-creating-components</code></strong>: envelopper des composants web, des bibliothèques JS ou des compositions
  </AccordionSummary>
  <AccordionDetails>
    <div>

Utilisez ceci lorsque vous avez besoin d'un composant Java réutilisable entouré de n'importe quelle source - une bibliothèque d'éléments personnalisés existante, une bibliothèque JavaScript simple, ou une composition de composants webforJ existants. L'assistant choisit la bonne classe de base webforJ pour le travail, relie les propriétés, les événements et les emplacements avec les bons modèles, et produit des tests qui suivent les conventions webforJ.

**Quand ça commence**

- *"Enveloppez cette bibliothèque d'éléments personnalisés en tant que composants webforJ."*
- *"Composez ces composants webforJ en une carte réutilisable."*
- *"Intégrez cette bibliothèque JavaScript simple en tant que composant webforJ."*
- *"Exposez cette API de navigateur en tant qu'utilitaire webforJ."*

</div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>webforj-handling-timers-and-async</code></strong>: planifier des minuteries, des debounceurs et du travail asynchrone
  </AccordionSummary>
  <AccordionDetails>
    <div>

Utilisez ceci pour des tâches périodiques, du polling, de la recherche avec délai à la saisie, du throttling, et du travail de fond de longue durée qui met à jour l'UI au fur et à mesure. L'assistant choisit le bon primitif (`Interval`, `Debouncer`, `Environment.runLater`, `PendingResult`) et évite les pièges d'exécution de `java.util.Timer`, `javax.swing.Timer`, ou des threads créés en dehors de l'environnement webforJ, tous ceux-ci lançant `IllegalStateException` dès qu'ils touchent un composant UI.

**Quand ça commence**

- *"Rafraîchir ce tableau de bord toutes les 30 secondes."*
- *"Ajoutez un debounceur pour la recherche à la saisie."*
- *"Exécutez ce travail lourd en arrière-plan et mettez à jour la barre de progression."*
- *"Interrogez ce point de terminaison REST jusqu'à ce qu'il renvoie `done`."*

</div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>webforj-localizing-apps</code></strong>: ajouter des fonctionnalités i18n et de traduction
  </AccordionSummary>
  <AccordionDetails>
    <div>

Utilisez ceci pour tout travail d'internationalisation : chargement de bundles de messages, changement de langue à l'exécution, détection automatique de la locale du navigateur de l'utilisateur et traduction des étiquettes de composants. L'assistant passe par le `BundleTranslationResolver` de webforJ 25.12, le souci `HasTranslation`, `LocaleObserver`, et les résolveurs personnalisés plug-in, et couvre à la fois les chemins Spring et plain webforJ.

**Quand ça commence**

- *"Ajoutez un support multilingue avec l'anglais et l'espagnol."*
- *"Détectez la locale du navigateur de l'utilisateur et appliquez-la au démarrage."*
- *"Ajoutez un sélecteur de langue à la barre de navigation."*
- *"Déplacez toutes les chaînes codées en dur dans un bundle de messages."*

</div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>webforj-securing-apps</code></strong>: protéger les routes avec connexion et accès basé sur les rôles
  </AccordionSummary>
  <AccordionDetails>
    <div>

Utilisez ceci pour tout ce qui protège les routes dans une application webforJ : connexion et déconnexion, accès basé sur les rôles, pages d'atterrissage publiques, sections réservées aux administrateurs, règles de propriété, et politiques de sécurité par défaut. L'assistant préfère Spring Security lorsque Spring Boot est dans le classpath et revient au cadre de sécurité simple de webforJ sinon. Il applique les bonnes annotations (`@AnonymousAccess`, `@PermitAll`, `@RolesAllowed`, `@RouteAccess`, `@RegisteredEvaluator`) et explique lesquelles sont terminales ou composables afin que la sécurité par défaut reste efficace.

**Quand ça commence**

- *"Protégez `/admin` afin que seuls les utilisateurs avec le rôle `ADMIN` puissent le voir."*
- *"Ajoutez une page d'atterrissage publique que tout le monde peut visiter."*
- *"Montrez le nom de l'utilisateur connecté dans l'en-tête."*
- *"Permettez uniquement à un utilisateur d'éditer un enregistrement qu'il possède."*

</div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>webforj-styling-apps</code></strong>: thématiser des applications avec des jetons de conception DWC
  </AccordionSummary>
  <AccordionDetails>
    <div>

Utilisez ceci pour tout travail visuel sur une application webforJ : reskin de palette, style au niveau des composants, mise en page et espacement, typographie, thèmes complets, apparence du tableau, ou couleurs coordonnées pour Google Charts. L'assistant écrit du CSS qui respecte les jetons de conception DWC, cible correctement les sélecteurs, et valide chaque référence `--dwc-*` par rapport au véritable catalogue pour votre version de webforJ - de sorte que le mode sombre et le changement de thème continuent de fonctionner.

**Quand ça commence**

- *"Thématisez cette application avec une palette bleue."*
- *"Stylez le dwc-button pour correspondre aux directives de la marque."*
- *"Resserrez cette mise en page - ajustez l'espacement et la typographie."*

</div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>webforj-upgrading-versions</code></strong>: mettre à niveau vers de nouvelles versions majeures de webforJ avec OpenRewrite
  </AccordionSummary>
  <AccordionDetails>
    <div>

Utilisez ceci pour les mises à niveau de versions majeures. L'assistant exécute la recette officielle `webforj-rewrite` OpenRewrite pour la version cible, qui augmente `<webforj.version>` et la version Java, réécrit les API et types renommés, et insère des commentaires `TODO webforJ <majeur>:` à chaque méthode supprimée qui nécessite une décision manuelle. Pour les cibles plus anciennes sans recette publiée (par exemple de 24 à 25), il vous guide à travers le repli manuel.

**Quand ça commence**

- *"Mettez à niveau cette application de webforJ 25 à 26."*
- *"Exécutez la recette de réécriture et résolvez les TODO."*
- *"Migrez de webforJ 24 à 25 manuellement puisque aucune recette n'est disponible."*
- *"Quelles API supprimées dois-je corriger après la mise à niveau ?"*

</div>
  </AccordionDetails>
</Accordion>

</AccordionGroup>
