---
title: MCP Server
sidebar_position: 2.5
sidebar_class_name: new-content
_i18n_hash: 7b656643222d616e7c44d14ed1de7bd3
---
Le serveur du protocole de contexte de modèle webforJ (MCP) fournit aux assistants IA un accès direct à la documentation officielle de webforJ, à des exemples de code vérifiés et à des modèles spécifiques au framework, permettant des réponses avec des réponses plus précises et une génération de projet automatisée spécifiquement pour le développement webforJ.

## Qu'est-ce qu'un MCP ?

Le protocole de contexte de modèle est une norme ouverte qui permet aux assistants IA de se connecter à des outils et à de la documentation externes. Le serveur MCP de webforJ implémente ce protocole pour fournir :

- **Recherche de connaissances** - Recherche en langage naturel à travers la documentation webforJ, des exemples de code et des modèles
- **Génération de projet** - Créer des applications webforJ à partir de modèles officiels avec une structure appropriée
- **Création de thème** - Générer des thèmes CSS accessibles suivant les modèles de design webforJ

## Pourquoi utiliser le MCP ?

Bien que les assistants de codage IA excellent à répondre aux questions basiques, ils ont du mal avec des requêtes webforJ complexes qui s'étendent sur plusieurs sections de documentation. Sans accès direct à des sources officielles, ils peuvent :

- Générer des méthodes qui n'existent pas dans webforJ
- Référencer des modèles d'API obsolètes ou incorrects  
- Fournir du code qui ne peut pas être compilé
- Confondre la syntaxe webforJ avec d'autres frameworks Java
- Mal comprendre les modèles spécifiques à webforJ

Avec l'intégration MCP, les réponses IA sont ancrées dans la documentation officielle de webforJ, des exemples de code et des modèles de framework, fournissant des réponses vérifiables avec des liens directs vers des sources officielles pour une exploration plus approfondie.

:::warning L'IA peut encore faire des erreurs
Bien que le MCP améliore considérablement la précision en fournissant l'accès à des ressources officielles de webforJ, cela ne garantit pas une génération de code parfaite. Les assistants IA peuvent encore faire des erreurs dans des scénarios complexes. Toujours vérifier le code généré et tester minutieusement avant de l'utiliser en production.
:::

## Installation

Le serveur MCP de webforJ est hébergé à `https://mcp.webforj.com` avec deux points de terminaison :

- **Point de terminaison MCP** (`/mcp`) - Pour Claude, VS Code, Cursor
- **Point de terminaison SSE** (`/sse`) - Pour les clients hérités

<Tabs groupId="ide">
<TabItem value="vscode" label="VS Code">

Ajoutez cette configuration à votre fichier settings.json de VS Code :

```json
"mcp": {
  "servers": {
    "webforj-mcp": {
      "url": "https://mcp.webforj.com/mcp"
    }
  }
}
```

</TabItem>
<TabItem value="cursor" label="Cursor">

Ajoutez cette configuration à vos paramètres de Cursor :

```json
"mcpServers": {
  "webforj-mcp": {
    "url": "https://mcp.webforj.com/mcp"
  }
}
```

</TabItem>
<TabItem value="claude-code" label="Claude Code" default>

Utilisez la commande CLI de Claude pour enregistrer le serveur :

```bash
claude mcp add webforj-mcp https://mcp.webforj.com/mcp -t http -s user
```

Cela configurera automatiquement le serveur MCP dans votre environnement Claude Code.

</TabItem>
<TabItem value="claude-desktop" label="Claude Desktop">

Ajoutez ce serveur en utilisant le panneau d'intégrations dans les paramètres de Claude Desktop :

1. Ouvrez Claude Desktop et allez dans les paramètres
2. Cliquez sur "Intégrations" dans la barre latérale
3. Cliquez sur "Ajouter une intégration" et collez l'URL : `https://mcp.webforj.com/mcp`
4. Suivez l'assistant de configuration pour compléter l'installation

Pour des instructions détaillées, voir le [guide d'intégration officiel](https://support.anthropic.com/en/articles/11175166-about-custom-integrations-using-remote-mcp).

</TabItem>
<TabItem value="windsurf" label="Windsurf">

Ajoutez cette configuration de serveur à vos paramètres MCP de Windsurf :

```json
{
  "mcpServers": {
    "webforj-mcp": {
      "serverUrl": "https://mcp.webforj.com/sse"
    }
  }
}
```

</TabItem>
</Tabs>

## Outils disponibles

Les outils sont des fonctions spécialisées que le serveur MCP fournit aux assistants IA. Lorsque vous posez une question ou faites une demande, l'IA peut appeler ces outils pour rechercher de la documentation, générer des projets ou créer des thèmes. Chaque outil accepte des paramètres spécifiques et renvoie des données structurées qui aident l'IA à fournir une assistance précise et contextuelle.

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>webforj-knowledge-base</code></strong> - Rechercher de la documentation et des exemples
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Cet outil fournit des capacités de recherche sémantique à travers tout l'écosystème de documentation de webforJ. Il comprend le contexte et les relations entre différents concepts du framework, renvoyant des sections de documentation pertinentes, des références API et des exemples de code fonctionnels.

      **Exemples de requêtes :**
      ```
      "Rechercher dans la documentation webforJ des exemples de composant Button avec icône"

      "Trouver des modèles de validation de formulaire webforJ dans la dernière documentation"

      "Montrez-moi la configuration actuelle du routage webforJ avec l'annotation @Route"

      "Recherchez dans la documentation webforJ des modèles de design responsive FlexLayout"

      "Trouver l'intégration de composant web dans la documentation officielle de webforJ"
      ```
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>webforj-create-project</code></strong> - Générer de nouveaux projets webforJ  
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Construisez des applications webforJ complètes en utilisant des archétypes Maven officiels. L'outil crée une structure de répertoires de projet standardisée et inclut du code de démarrage basé sur le modèle sélectionné. Les projets générés incluent un système de build prêt à l'emploi, des dossiers de ressources et des fichiers de configuration pour un développement et un déploiement immédiats.

      **Exemples de requêtes :**
      ```
      "Créer un projet webforJ nommé CustomerPortal en utilisant l'archétype hello-world"

      "Générer un projet webforJ Spring Boot avec une disposition à onglets nommée Dashboard"

      "Créer une nouvelle application webforJ avec l'archétype sidebar pour le projet AdminPanel"

      "Générer un projet webforJ vide nommé TestApp avec com.example groupId"

      "Créer un projet webforJ InventorySystem utilisant l'archétype sidebar avec Spring Boot"
      ```

      Lorsque vous utilisez cet outil, vous pouvez choisir parmi plusieurs modèles de projets :

      **Archétypes** (modèles de projet) :
      - `hello-world` - Application de base avec des composants d'exemple pour démontrer les fonctionnalités de webforJ
      - `blank` - Structure de projet minimale pour partir de zéro
      - `tabs` - Interface à onglets pré-construite pour des applications à vue multiple
      - `sidemenu` - Mise en page de menu de navigation latérale pour les panneaux administrateurs ou les tableaux de bord

      **Flavors** (intégration de framework) :
      - `webforj` - Application webforJ standard
      - `webforj-spring` - webforJ intégré avec Spring Boot pour l'injection de dépendances et les fonctionnalités d'entreprise

      :::tip Archétypes disponibles
      webforJ vient avec plusieurs archétypes prédéfinis pour vous aider à commencer rapidement. Pour une liste complète des archétypes disponibles, voir le [catalogue d'archétypes](../building-ui/archetypes/overview).
      :::
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>webforj-create-theme</code></strong> - Créer des thèmes CSS accessibles
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Génère des configurations de thème webforJ en utilisant [DWC HueCraft](https://huecraft.dwc.style/). L'outil crée des ensembles de propriétés CSS personnalisées complètes avec des variantes de couleur primaire, secondaire, de succès, d'avertissement, de danger, et neutre.

      **Exemples de requêtes :**
      ```
      "Générer un thème webforJ avec HSL 220, 70, 50 comme couleur primaire pour notre marque d'entreprise"

      "Créer un thème webforJ accessible nommé 'ocean' avec la couleur primaire #0066CC"

      "Générer un thème webforJ en utilisant notre couleur de marque #FF5733"

      "Créer un thème webforJ avec HSL 30, 100, 50 nommé 'sunset' pour notre application"

      "Générer un thème webforJ accessible avec RGB primaire 44, 123, 229"
      ```
    </div>
  </AccordionDetails>
</Accordion>

## Prompts disponibles {#available-prompts}

Les prompts sont des instructions IA préconfigurées qui combinent plusieurs outils et workflows pour des tâches courantes. Ils guident l'IA à travers des étapes et paramètres spécifiques pour fournir des résultats fiables et répétables pour chaque workflow pris en charge.

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>create-app</code></strong> - Créer et exécuter une application webforJ
  </AccordionSummary>
  <AccordionDetails>
    <div>
      **Arguments :**
      - `appName` (obligatoire) - Nom de l'application (par ex., MyApp, TodoList, Dashboard)
      - `archetype` (obligatoire) - Choisissez parmi : `blank`, `hello-world`, `tabs`, `sidemenu`
      - `runServer` (facultatif) - Exécutez automatiquement le serveur de développement (oui/non)
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>create-theme</code></strong> - Générer un thème webforJ à partir d'une couleur primaire
  </AccordionSummary>
  <AccordionDetails>
    <div>
      **Arguments :**
      - `primaryColor` (obligatoire) - Couleur au format hex (#FF5733), rgb (255,87,51) ou hsl (9,100,60)
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>search-webforj</code></strong> - Recherche avancée avec résolution de problèmes autonome
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Le prompt configure l'IA pour :

      1. Rechercher dans la base de connaissances de manière exhaustive
      2. Écrire du code complet et prêt à la production
      3. Compiler le projet en utilisant `mvn compile` pour vérifier qu'il n'y a pas d'erreurs de construction
      4. Corriger les erreurs de manière itérative jusqu'à ce que tout fonctionne
    </div>
  </AccordionDetails>
</Accordion>

### Comment utiliser les prompts

<Tabs groupId="ide">
<TabItem value="vscode" label="VS Code et Claude Code">

1. Tapez <kbd>/</kbd> dans le chat pour voir les prompts disponibles
2. Sélectionnez un prompt dans le menu déroulant
3. Remplissez les paramètres requis lorsque vous y êtes invité

</TabItem>
<TabItem value="claude-desktop" label="Claude Desktop">

1. Cliquez sur l'icône **+** (plus) dans la zone de saisie des prompts
2. Sélectionnez **"Ajouter depuis webforJ"** dans le menu
3. Choisissez le prompt souhaité (par ex., `create-app`, `create-theme`, `search-webforj`)
4. Claude vous demandera d'entrer les arguments requis
5. Remplissez les paramètres demandés

:::tip Vérifiez que le MCP est connecté
Recherchez l'icône des outils dans le coin inférieur de la zone de saisie pour confirmer que le serveur MCP de webforJ est connecté.
:::

</TabItem>
</Tabs>

## Meilleures pratiques

Pour obtenir l'assistance webforJ la plus précise et à jour, suivez ces directives pour tirer pleinement parti des fonctionnalités du serveur MCP.

### Assurer l'utilisation du serveur MCP

Les modèles IA peuvent ignorer le serveur MCP s'ils estiment déjà connaître la réponse. Pour garantir que le serveur MCP est réellement utilisé :

- **Soyez explicite sur webforJ** : Mentionnez toujours "webforJ" dans votre requête pour déclencher des recherches spécifiques au framework
- **Demandez des informations actuelles** : Incluez des phrases comme "dernière documentation webforJ" ou "modèles webforJ actuels"
- **Demandez des exemples vérifiés** : Demandez "exemples de code webforJ fonctionnels" pour forcer la recherche dans la documentation
- **Référencez des versions spécifiques** : Mentionnez votre version webforJ (par ex., "webforJ `25.02`") pour obtenir des résultats précis

### Rédaction de prompts spécifiques

**Bon exemples :**
```
"Rechercher dans la documentation webforJ des exemples de gestion d'événements pour le composant Button"

"Créer un projet webforJ nommé InventorySystem en utilisant l'archétype sidemenu avec Spring Boot"

"Générer un thème webforJ avec HSL 220, 70, 50 comme couleur primaire pour la marque d'entreprise"
```

**Mauvais exemples :**
```
"Comment fonctionnent les boutons"

"Faites une application"

"Faites-la bleue"
```

### Forcer l'utilisation des outils MCP

Si l'IA fournit des réponses génériques sans utiliser le serveur MCP :

1. **Demandez explicitement** : "Utilisez le serveur MCP de webforJ pour rechercher `[query]`"
2. **Demandez des références de documentation** : "Trouvez dans la documentation webforJ comment `[query]`"
3. **Demandez une vérification** : "Vérifiez cette solution contre la documentation webforJ"
4. **Soyez spécifique au framework** : Incluez toujours "webforJ" dans vos requêtes

## Personnalisation de l'IA {#ai-customization}

Configurez vos assistants IA pour qu'ils utilisent automatiquement le serveur MCP et suivent les meilleures pratiques de webforJ. Ajoutez des instructions spécifiques au projet pour que vos assistants IA utilisent toujours le serveur MCP, suivent les normes de documentation de webforJ et fournissent des réponses précises et à jour qui correspondent aux exigences de votre équipe.

### Fichiers de configuration de projet

- Pour **VS Code et Copilot**, créez `.github/copilot-instructions.md`
- Pour **Claude Code**, créez `CLAUDE.md` dans la racine de votre projet

Ajoutez ce qui suit au fichier markdown créé :
```markdown
## Utilisez le serveur MCP de webforJ pour répondre à toutes les questions concernant webforJ

- Appelez toujours l'outil "webforj-knowledge-base" pour rechercher des docs pertinentes pour la question
- Vérifiez toutes les signatures API contre la documentation officielle
- Ne supposez jamais que des noms de méthodes ou des paramètres existent sans vérification

Vérifiez toujours que le code se compile avec `mvn compile` avant de le suggérer.
```

## FAQ

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>Pourquoi l'IA n'utilise-t-elle pas le serveur MCP de webforJ ?</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      La plupart des assistants IA nécessitent des instructions explicites pour utiliser les serveurs MCP. Configurez votre client IA avec les instructions de la section [Personnalisation de l'IA](#ai-customization). Sans ces instructions, les assistants IA peuvent par défaut utiliser leurs données d'entraînement au lieu de questionner le serveur MCP.

      **Solution rapide :**
      Incluez "utiliser le MCP de webforJ" dans votre prompt ou créez le fichier de configuration approprié (`.github/copilot-instructions.md` ou `CLAUDE.md`).
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>Comment vérifier que la connexion MCP fonctionne ?</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Utilisez l'inspecteur MCP pour déboguer les connexions :

      ```bash
      npx @modelcontextprotocol/inspector
      ```

      Attendez le message : `🔍 MCP Inspector est en cours d'exécution à http://127.0.0.1:6274` (le port peut varier)

      Ensuite, dans l'inspecteur :
      1. Entrez l'URL du serveur MCP : `https://mcp.webforj.com/mcp`
      2. Cliquez sur "Connecter" pour établir la connexion
      3. Consultez les outils disponibles et testez les requêtes
      4. Surveillez les journaux de demande/réponse pour le débogage
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>Quelle est la différence entre les points de terminaison MCP et SSE ?</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Le serveur MCP de webforJ fournit deux points de terminaison :

      - **Point de terminaison MCP** (`/mcp`) - Protocole moderne pour Claude, VS Code, Cursor
      - **Point de terminaison SSE** (`/sse`) - Événements envoyés par le serveur pour les clients hérités comme Windsurf

      La plupart des utilisateurs devraient utiliser le point de terminaison MCP. N'utilisez le SSE que si votre client ne prend pas en charge le protocole standard MCP.
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>Est-il possible d'utiliser le serveur MCP sans fichiers de configuration ?</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Oui, mais ce n'est pas recommandé. Sans fichiers de configuration, vous devez manuellement inciter l'IA à utiliser le serveur MCP dans chaque conversation. Les fichiers de configuration instruisent automatiquement l'IA à utiliser le serveur MCP pour chaque interaction, afin que vous n'ayez pas à répéter les instructions à chaque fois.

      **Approche manuelle :**
      Commencez les prompts par : "Utilisez le serveur MCP de webforJ pour..."

      **Alternative : Utilisez des prompts préconfigurés**
      Le serveur MCP fournit des prompts qui fonctionnent sans fichiers de configuration :
      - `/create-app` - Générer de nouvelles applications webforJ
      - `/create-theme` - Créer des thèmes CSS accessibles
      - `/search-webforj` - Recherche documentaire avancée

      Voir [Prompts disponibles](#available-prompts) pour les détails.
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>Comment contribuer ou signaler des problèmes</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      **Signaler des problèmes :** [Modèle de problème MCP webforJ](https://github.com/webforj/webforj/issues/new?template=mcp_report.yml)
      
      **Problèmes courants à signaler :**
      - Documentation obsolète dans les résultats de recherche
      - Méthodes ou composants API manquants
      - Exemples de code incorrects
      - Erreurs d'exécution des outils

      Incluez votre requête, le résultat attendu et le résultat réel lors de la signalisation des problèmes.
    </div>
  </AccordionDetails>
</Accordion>
<br />
