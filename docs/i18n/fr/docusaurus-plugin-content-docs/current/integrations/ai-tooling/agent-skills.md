---
title: Agent Skills
sidebar_position: 10
_i18n_hash: 0458a29cc4337ff83f08afb415097a1c
---
Les compétences des agents enseignent aux assistants de codage IA comment construire des applications webforJ en utilisant les bonnes API, les tokens de conception et les modèles de composants. Au lieu de deviner les conventions de framework, l'assistant charge une compétence et suit un flux de travail structuré pour produire du code qui se compile et respecte les meilleures pratiques dès la première tentative.

:::tip Utilisez le plugin
Les compétences ci-dessous sont livrées dans le **[plugin IA webforJ](/docs/integrations/ai-tooling)**, avec le [serveur MCP](/docs/integrations/ai-tooling/mcp). Une installation donne à votre assistant les deux éléments.
:::

Les compétences suivent la norme ouverte [Agent Skills](https://agentskills.io/specification) et fonctionnent avec de nombreux assistants IA, y compris Claude Code, GitHub Copilot, Cursor, Gemini CLI, OpenAI Codex, et plus encore. Une compétence indique à l'assistant quel type de tâche il gère ; l'assistant la charge automatiquement lorsque votre prompt correspond. Par exemple, demander "thématisez cette application avec une palette bleue" déclenche la compétence `webforj-styling-apps`, qui guide l'assistant pour rechercher des tokens DWC valides, rédiger du CSS scope, et valider chaque nom de variable avant d'écrire quoi que ce soit sur le disque.

## Pourquoi utiliser des compétences ? {#why-use-skills}

Le serveur MCP met à disposition des informations précises sur webforJ à la demande, mais à lui seul, il ne dit pas à l'assistant _quand_ rechercher quelque chose, _quelle_ approche convient à la tâche, ou _dans quel ordre_ faire les choses. C'est là que les compétences entrent en jeu.

Les compétences donnent à l'assistant un manuel de jeu spécifique à la tâche : comment classifier le travail devant lui, quels modèles webforJ s'appliquent, quels outils MCP consulter à chaque étape, et comment valider la sortie avant de la remettre. Le résultat est un code webforJ cohérent et conforme aux conventions plutôt qu'une collection de snippets techniquement valides mais stylistiquement mal assortis.

## Comment les compétences diffèrent du MCP {#how-skills-differ-from-mcp}

Les compétences et le [serveur MCP webforJ](/docs/integrations/ai-tooling/mcp) remplissent des rôles complémentaires. Le serveur MCP fournit des outils en direct que l'assistant peut appeler pour récupérer des informations ou générer des sorties. Les compétences fournissent le flux de travail qui indique à l'assistant _quand_ saisir ces outils, dans quel ordre procéder, et comment valider le résultat.

| | Serveur MCP | Compétences des agents |
|---|---|---|
| **Ce qu'il fournit** | Outils que l'assistant appelle à la demande (recherche de documents, scaffolding, génération de thèmes, validation des tokens) | Flux de travail et tableaux de décision qui guident la manière dont l'assistant aborde une tâche |
| **Quand il agit** | Lorsque l'assistant décide d'appeler un outil | Automatiquement, lorsque l'assistant détecte une tâche correspondante |
| **Meilleur pour** | Répondre à des questions spécifiques, générer des artefacts | Tâches de bout en bout qui ont besoin d'une approche webforJ cohérente |

En pratique, les deux fonctionnent mieux ensemble - et le [plugin IA webforJ](https://github.com/webforj/webforj-ai) les livre en une seule installation.

## Installation {#installation}

Installez le **[plugin IA webforJ](/docs/integrations/ai-tooling)** - il livre les deux compétences ci-dessous avec le serveur MCP. Pour les clients qui ne prennent pas en charge les plugins, le [référentiel IA webforJ](https://github.com/webforj/webforj-ai#clients) répertorie le répertoire des compétences que chaque outil lit, afin que vous puissiez copier les dossiers de compétences à la main.

## Compétences disponibles {#available-skills}

<AccordionGroup>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>webforj-adding-servlets</code></strong>: ajouter des points de terminaison REST, des webhooks et des servlets personnalisés
  </AccordionSummary>
  <AccordionDetails>
    <div>

Utilisez ceci lorsque vous avez besoin d'un chemin HTTP non-UI - un point de terminaison REST, un gestionnaire de webhook, ou un servlet tiers tel que Swagger UI ou Spring Web. L'assistant choisit la bonne approche pour votre projet (Spring `webforj.exclude-urls`, remappant `WebforjServlet` à un sous-chemin, ou proxy via `webforj.conf`) et configure le point de terminaison sans perturber le routage UI de webforJ.

**Quand cela s'active**

- *"Ajoutez un point de terminaison REST à `/api/orders`."*
- *"Configurez un gestionnaire de webhook pour Stripe."*
- *"Montez Swagger UI à `/api/docs`."*
- *"Exposez un servlet personnalisé qui fonctionne aux côtés de l'UI webforJ."*

</div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>webforj-building-forms</code></strong>: construire des formulaires avec liaison, validation et masques d'entrée
  </AccordionSummary>
  <AccordionDetails>
    <div>

Utilisez ceci pour tout travail de formulaire dans une application webforJ : formulaires de saisie de données, liaison bidirectionnelle avec un bean Java, validation Jakarta, composants d'entrée masqués (téléphone, devise, IBAN, dates), formatage des colonnes de table en tant que devise ou pourcentage, et mises en page multi-colonnes responsives. L'assistant passe par `BindingContext`, les composants `Masked*Field`, les rendus de masque de Table, et `ColumnsLayout`.

**Quand cela s'active**

- *"Construisez un formulaire d'inscription lié à mon bean `User`."*
- *"Ajoutez une entrée de numéro de téléphone avec formatage en temps réel."*
- *"Formatez cette colonne de table en tant que devise."*
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

Utilisez ceci lorsque vous avez besoin d'un composant Java réutilisable enveloppé autour de n'importe quelle source - une bibliothèque d'éléments personnalisés existant, une bibliothèque JavaScript ordinaire, ou une composition de composants webforJ existants. L'assistant choisit la bonne classe de base webforJ pour le travail, connecte les propriétés, les événements, et les slots avec les bons modèles, et produit des tests qui suivent les conventions webforJ.

**Quand cela s'active**

- *"Enveloppez cette bibliothèque d'éléments personnalisés en tant que composants webforJ."*
- *"Composez ces composants webforJ en une carte réutilisable."*
- *"Intégrez cette bibliothèque JavaScript ordinaire en tant que composant webforJ."*
- *"Exposez cette API du navigateur en tant qu'utilitaire webforJ."*

</div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>webforj-handling-timers-and-async</code></strong>: planifier des minuteries, des débounceurs et du travail asynchrone
  </AccordionSummary>
  <AccordionDetails>
    <div>

Utilisez ceci pour des tâches périodiques, le polling, la recherche avec débounce en temps réel, le throttling et le travail de fond de longue durée qui met à jour l'UI pendant son exécution. L'assistant choisit le bon primitif (`Interval`, `Debouncer`, `Environment.runLater`, `PendingResult`) et évite les pièges d'exécution des `java.util.Timer`, `javax.swing.Timer`, ou des threads créés en dehors de l'environnement webforJ, tous lancent une `IllegalStateException` dès qu'ils touchent un composant UI.

**Quand cela s'active**

- *"Rafraîchissez ce tableau de bord toutes les 30 secondes."*
- *"Ajoutez un débounceur pour la recherche en temps réel."*
- *"Exécutez ce travail gourmand en CPU en arrière-plan et mettez à jour la barre de progression."*
- *"Faites un polling de ce point de terminaison REST jusqu'à ce qu'il retourne `done`."*

</div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>webforj-localizing-apps</code></strong>: ajouter le support i18n et traduction
  </AccordionSummary>
  <AccordionDetails>
    <div>

Utilisez ceci pour tout travail d'internationalisation : chargement de bundles de messages, changement de langue à l'exécution, détection automatique de la locale du navigateur de l'utilisateur, et traduction des étiquettes de composants. L'assistant passe par le `BundleTranslationResolver` de webforJ 25.12, la préoccupation `HasTranslation`, `LocaleObserver`, et des résolveurs personnalisables, et couvre à la fois les chemins Spring et plain webforJ.

**Quand cela s'active**

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

Utilisez ceci pour tout ce qui protège les routes dans une application webforJ : connexion et déconnexion, accès basé sur les rôles, pages d'accueil publiques, sections réservées aux administrateurs, règles de propriété, et politiques sécurisées par défaut. L'assistant préfère Spring Security lorsque Spring Boot est dans le classpath et revient à l'environnement de sécurité plain webforJ sinon. Il applique les bonnes annotations (`@AnonymousAccess`, `@PermitAll`, `@RolesAllowed`, `@RouteAccess`, `@RegisteredEvaluator`) et explique lesquelles sont terminales versus composables afin que le sécurisé par défaut fasse toujours ce qu'il dit.

**Quand cela s'active**

- *"Protégez `/admin` afin que seuls les utilisateurs avec le rôle `ADMIN` puissent le voir."*
- *"Ajoutez une page d'accueil publique que tout le monde peut visiter."*
- *"Affichez le nom de l'utilisateur connecté dans l'en-tête."*
- *"Laissez un utilisateur n'éditer qu'un enregistrement qu'il possède."*

</div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>webforj-styling-apps</code></strong>: thématiser les applications avec des tokens de design DWC
  </AccordionSummary>
  <AccordionDetails>
    <div>

Utilisez ceci pour tout travail visuel sur une application webforJ : re-colorations de palette, stylisation au niveau des composants, mise en page et espacement, typographie, thèmes complets, apparence des tableaux, ou couleurs coordonnées des graphiques Google. L'assistant rédige du CSS qui respecte les tokens de design DWC, scope correctement les sélecteurs, et valide chaque référence `--dwc-*` par rapport au vrai catalogue pour votre version de webforJ - de sorte que le mode sombre et le changement de thème continuent de fonctionner.

**Quand cela s'active**

- *"Thématisez cette application avec une palette bleue."*
- *"Stylisez le dwc-button pour qu'il corresponde aux directives de la marque."*
- *"Rendez cette mise en page plus compacte - ajustez l'espacement et la typographie."*

</div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>webforj-upgrading-versions</code></strong>: mise à niveau entre les versions majeures de webforJ avec OpenRewrite
  </AccordionSummary>
  <AccordionDetails>
    <div>

Utilisez ceci pour les mises à niveau de version majeure. L'assistant exécute la recette officielle `webforj-rewrite` OpenRewrite pour la version cible, qui met à jour `<webforj.version>` et la version Java, réécrit les API et types renommés, et insère des commentaires `TODO webforJ <major>:` à chaque méthode supprimée qui nécessite une décision manuelle. Pour les cibles plus anciennes sans recette publiée (par exemple de 24 à 25), il vous guide à travers le repli manuel.

**Quand cela s'active**

- *"Mettez à niveau cette application de webforJ 25 à 26."*
- *"Exécutez la recette de réécriture et résolvez les TODO."*
- *"Migrez de webforJ 24 à 25 manuellement car il n'y a pas de recette."*
- *"Quelles API supprimées dois-je corriger après la mise à niveau ?"*

</div>
  </AccordionDetails>
</Accordion>

</AccordionGroup>
