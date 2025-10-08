---
title: MCP Server
sidebar_position: 2.5
sidebar_class_name: new-content
_i18n_hash: cfe1c4447876aff3ab7ba15b26966cba
---
Le serveur du Protocole de Contexte de Modèle webforJ (MCP) fournit aux assistants IA un accès direct à la documentation officielle de webforJ, à des exemples de code vérifiés et à des modèles spécifiques au framework, permettant des réponses plus précises et une génération de projets automatisée spécifiquement pour le développement webforJ.

## Qu'est-ce qu'un MCP ?

Le Protocole de Contexte de Modèle est une norme ouverte qui permet aux assistants IA de se connecter à des outils et à de la documentation externes. Le serveur MCP de webforJ met en œuvre ce protocole pour fournir :

- **Recherche de connaissances** - Recherche en langage naturel à travers la documentation webforJ, des exemples de code et des modèles
- **Génération de projets** - Créer des applications webforJ à partir de modèles officiels avec une structure appropriée
- **Création de thèmes** - Générer des thèmes CSS accessibles suivant les modèles de design webforJ

## Pourquoi utiliser le MCP ?

Bien que les assistants de codage IA excellent à répondre à des questions de base, ils ont du mal avec des requêtes spécifiques à webforJ qui couvrent plusieurs sections de documentation. Sans accès direct à des sources officielles, ils peuvent :

- Générer des méthodes qui n'existent pas dans webforJ
- Se référer à des modèles d'API obsolètes ou incorrects  
- Fournir du code qui ne se compile pas
- Confondre la syntaxe webforJ avec d'autres frameworks Java
- Mal interpréter les modèles spécifiques à webforJ

Avec l'intégration du MCP, les réponses IA sont ancrées dans la documentation webforJ réelle, des exemples de code et des modèles du framework, fournissant des réponses vérifiables avec des liens directs vers des sources officielles pour une exploration plus approfondie.

:::warning L'IA peut encore faire des erreurs
Bien que le MCP améliore considérablement la précision en fournissant un accès aux ressources officielles de webforJ, cela ne garantit pas une génération de code parfaite. Les assistants IA peuvent encore faire des erreurs dans des scénarios complexes. Vérifiez toujours le code généré et testez-le soigneusement avant de l'utiliser en production.
:::

## Installation

Le serveur MCP de webforJ est hébergé à `https://mcp.webforj.com` avec deux points de terminaison :

- **Point de terminaison MCP** (`/mcp`) - Pour Claude, VS Code, Cursor
- **Point de terminaison SSE** (`/sse`) - Pour les clients légacy

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

Ajoutez cette configuration à vos paramètres Cursor :

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

Ajoutez ce serveur à l'aide du panneau Intégrations dans les paramètres de Claude Desktop :

1. Ouvrez Claude Desktop et allez dans Paramètres
2. Cliquez sur "Intégrations" dans la barre latérale
3. Cliquez sur "Ajouter une intégration" et collez l'URL : `https://mcp.webforj.com/mcp`
4. Suivez l'assistant de configuration pour terminer 

Pour des instructions détaillées, consultez le [guide d'intégration officiel](https://support.anthropic.com/en/articles/11175166-about-custom-integrations-using-remote-mcp).

</TabItem>
<TabItem value="windsurf" label="Windsurf">

Ajoutez cette configuration de serveur à vos paramètres MCP Windsurf :

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

Les outils sont des fonctions spécialisées que le serveur MCP fournit aux assistants IA. Lorsque vous posez une question ou faites une demande, l'IA peut appeler ces outils pour rechercher dans la documentation, générer des projets ou créer des thèmes. Chaque outil accepte des paramètres spécifiques et renvoie des données structurées qui aident l'IA à fournir une assistance précise et contextuelle.

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>webforj-knowledge-base</code></strong> - Recherche dans la documentation et les exemples
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Cet outil fournit des capacités de recherche sémantique à travers l'ensemble de l'écosystème de documentation webforJ. Il comprend le contexte et les relations entre différents concepts du framework, renvoyant des sections de documentation pertinentes, des références API et des exemples de code fonctionnels.

      **Exemples de requêtes :**
      ```
      "Rechercher dans la documentation webforJ des exemples de composants Button avec icône"

      "Trouver des modèles de validation de formulaire webforJ dans la documentation la plus récente"

      "Montrez-moi la configuration du routage webforJ actuel avec l'annotation @Route"

      "Recherchez dans la documentation webforJ des modèles de design responsive FlexLayout"

      "Trouvez l'intégration des composants web de webforJ dans la documentation officielle"
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
      Élaborer des applications webforJ complètes en utilisant des archétypes Maven officiels. L'outil crée une structure de répertoire de projet standardisée et inclut du code de démarrage basé sur le modèle sélectionné. Les projets générés comprennent un système de construction prêt à l'emploi, des dossiers de ressources et des fichiers de configuration pour un développement et une déploiement immédiats.

      **Exemples de prompts :**
      ```
      "Créer un projet webforJ nommé CustomerPortal utilisant l'archétype hello-world"

      "Générer un projet webforJ Spring Boot avec un layout d'onglets nommé Dashboard"

      "Créer une nouvelle application webforJ avec l'archétype sidemenu pour le projet AdminPanel"

      "Générer un projet webforJ vide nommé TestApp avec com.example groupId"

      "Créer un projet webforJ InventorySystem utilisant l'archétype sidemenu avec Spring Boot"
      ```

      Lors de l'utilisation de cet outil, vous pouvez choisir parmi plusieurs modèles de projet :

      **Archétypes** (modèles de projet) :
      - `hello-world` - Application de base avec des composants d'exemple pour démontrer les fonctionnalités de webforJ
      - `blank` - Structure de projet minimale pour commencer à zéro
      - `tabs` - Mise en page d'interface à onglets pré-construite pour des applications à vues multiples
      - `sidemenu` - Mise en page de menu de navigation latéral pour des panneaux administratifs ou des tableaux de bord

      **Flavors** (intégration de framework) :
      - `webforj` - Application webforJ standard
      - `webforj-spring` - webforJ intégré avec Spring Boot pour l'injection de dépendances et des fonctionnalités d'entreprise

      :::tip Archétypes Disponibles
      webforJ propose plusieurs archétypes prédéfinis pour vous aider à commencer rapidement. Pour une liste complète des archétypes disponibles, consultez le [catalogue des archétypes](../building-ui/archetypes/overview).
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
      Génère des configurations de thème webforJ en utilisant [DWC HueCraft](https://huecraft.dwc.style/). L'outil crée des ensembles complets de propriétés CSS personnalisées avec des variantes de couleur primaire, secondaire, réussite, avertissement, danger et neutre.

      **Exemples de demandes :**
      ```
      "Générer un thème webforJ avec HSL 220, 70, 50 comme couleur primaire pour notre marque"

      "Créer un thème accessible webforJ nommé 'ocean' avec la couleur primaire #0066CC"

      "Générer un thème webforJ utilisant notre couleur de marque #FF5733"

      "Créer un thème webforJ avec HSL 30, 100, 50 nommé 'sunset' pour notre application"

      "Générer un thème webforJ accessible avec la couleur primaire RGB 44, 123, 229"
      ```
    </div>
  </AccordionDetails>
</Accordion>

## Prompts disponibles {#available-prompts}

Les prompts sont des instructions IA pré-configurées qui combinent plusieurs outils et workflows pour des tâches courantes. Ils guident l'IA à travers des étapes et des paramètres spécifiques pour livrer des résultats fiables et répétables pour chaque flux de travail pris en charge.

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>create-app</code></strong> - Créer et exécuter une application webforJ
  </AccordionSummary>
  <AccordionDetails>
    <div>
      **Arguments :**
      - `appName` (obligatoire) - Nom de l'application (par ex., MyApp, TodoList, Dashboard)
      - `archetype` (obligatoire) - Choisir parmi : `blank`, `hello-world`, `tabs`, `sidemenu`
      - `runServer` (optionnel) - Exécuter automatiquement le serveur de développement (oui/non)
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
    <strong><code>search-webforj</code></strong> - Recherche avancée avec résolution autonome de problèmes
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Le prompt configure l'IA pour :

      1. Rechercher dans la base de connaissances de manière extensive
      2. Écrire du code complet prêt pour la production
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
3. Remplissez les paramètres requis lorsqu'on vous y invite

</TabItem>
<TabItem value="claude-desktop" label="Claude Desktop">

1. Cliquez sur l'icône **+** (plus) dans la zone de saisie du prompt
2. Sélectionnez **"Ajouter depuis webforJ"** dans le menu
3. Choisissez le prompt désiré (par ex., `create-app`, `create-theme`, `search-webforj`)
4. Claude vous demandera d'entrer les arguments requis
5. Remplissez les paramètres selon la demande

:::tip Vérifiez que le MCP est connecté
Recherchez l'icône des outils dans le coin inférieur de la zone de saisie pour confirmer que le serveur MCP de webforJ est connecté.
:::

</TabItem>
</Tabs>

## Bonnes pratiques

Pour obtenir l'assistance webforJ la plus précise et à jour, suivez ces lignes directrices pour tirer pleinement parti des fonctionnalités du serveur MCP.

### Assurer l'utilisation du serveur MCP

Les modèles IA peuvent ignorer le serveur MCP s'ils pensent qu'ils connaissent déjà la réponse. Pour garantir que le serveur MCP est réellement utilisé :

- **Soyez explicite sur webforJ** : Mentionnez toujours "webforJ" dans votre requête pour déclencher des recherches spécifiques au framework
- **Demandez des informations actuelles** : Incluez des phrases comme "dernière documentation webforJ" ou "modèles webforJ actuels"
- **Demandez des exemples vérifiés** : Demandez "exemples de code webforJ fonctionnels" pour forcer la recherche dans la documentation
- **Faites référence à des versions spécifiques** : Mentionnez votre version de webforJ (par ex., "webforJ `25.02`") pour obtenir des résultats précis

### Écrire des prompts spécifiques

**Bonnes exemples :**
```
"Rechercher dans la documentation webforJ pour la gestion des événements du composant Button avec des exemples"

"Créer un projet webforJ nommé InventorySystem utilisant l'archétype sidemenu avec Spring Boot"

"Générer un thème webforJ avec HSL 220, 70, 50 comme couleur primaire pour la marque d'entreprise"
```

**Mauvaises exemples :**
```
"Comment fonctionnent les boutons"

"Faites une application"

"Faites-le bleu"
```

### Forcer l'utilisation de l'outil MCP

Si l'IA fournit des réponses génériques sans utiliser le serveur MCP :

1. **Demander explicitement** : "Utilisez le serveur MCP de webforJ pour rechercher `[requête]`"
2. **Demander des références de documentation** : "Trouvez dans la documentation webforJ comment `[requête]`"
3. **Demander une vérification** : "Vérifiez cette solution contre la documentation webforJ"
4. **Soyez spécifique au framework** : Incluez toujours "webforJ" dans vos requêtes

## Personnalisation de l'IA {#ai-customization}

Configurez vos assistants IA pour utiliser automatiquement le serveur MCP et suivre les meilleures pratiques webforJ. Ajoutez des instructions spécifiques au projet afin que vos assistants IA utilisent toujours le serveur MCP, suivent les normes de documentation webforJ et fournissent des réponses précises et à jour qui correspondent aux exigences de votre équipe.

### Fichiers de configuration de projet

- Pour **VS Code et Copilot**, créez `.github/copilot-instructions.md`
- Pour **Claude Code**, créez `CLAUDE.md` à la racine de votre projet

Ajoutez ce qui suit au fichier markdown créé :
```markdown
## Utiliser le serveur MCP de webforJ pour répondre à toutes les questions webforJ

- Appelez toujours l'outil "webforj-knowledge-base" pour récupérer la documentation pertinente à la question
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
      La plupart des assistants IA nécessitent des instructions explicites pour utiliser les serveurs MCP. Configurez votre client IA avec les instructions de la section [Personnalisation de l'IA](#ai-customization). Sans ces instructions, les assistants IA peuvent revenir à leurs données d'entraînement au lieu d'interroger le serveur MCP.

      **Solution rapide :**
      Incluez "utiliser le MCP webforJ" dans votre prompt ou créez le fichier de configuration approprié (`.github/copilot-instructions.md` ou `CLAUDE.md`).
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>Comment vérifier que la connexion MCP fonctionne</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Utilisez l'inspecteur MCP pour déboguer les connexions :

      ```bash
      npx @modelcontextprotocol/inspector
      ```

      Attendez le message : `🔍 L'inspecteur MCP est en cours d'exécution à http://127.0.0.1:6274` (le port peut varier)

      Ensuite, dans l'inspecteur :
      1. Entrez l'URL du serveur MCP : `https://mcp.webforj.com/mcp`
      2. Cliquez sur "Connecter" pour établir la connexion
      3. Affichez les outils disponibles et testez les requêtes
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
      - **Point de terminaison SSE** (`/sse`) - Événements envoyés par le serveur pour les clients légacy comme Windsurf

      La plupart des utilisateurs devraient utiliser le point de terminaison MCP. N'utilisez SSE que si votre client ne prend pas en charge le protocole MCP standard.
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>Est-il possible d'utiliser le serveur MCP sans fichiers de configuration ?</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Oui, mais ce n'est pas recommandé. Sans fichiers de configuration, vous devez inviter manuellement l'IA à utiliser le serveur MCP à chaque conversation. Les fichiers de configuration instruisent automatiquement l'IA à utiliser le serveur MCP pour chaque interaction, donc vous n'avez pas à répéter les instructions à chaque fois.

      **Approche manuelle :**
      Commencez les prompts par : "Utiliser le serveur MCP de webforJ pour..."

      **Alternative : Utiliser des prompts pré-configurés**
      Le serveur MCP fournit des prompts qui fonctionnent sans fichiers de configuration :
      - `/create-app` - Générer de nouvelles applications webforJ
      - `/create-theme` - Créer des thèmes CSS accessibles
      - `/search-webforj` - Recherche avancée de documentation

      Consultez [Prompts disponibles](#available-prompts) pour plus de détails.
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>Comment contribuer ou signaler des problèmes</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      **Signaler des problèmes :** [Modèle de problème MCP de webforJ](https://github.com/webforj/webforj/issues/new?template=mcp_report.yml)

      **Problèmes courants à signaler :**
      - Documentation obsolète dans les résultats de recherche
      - Méthodes ou composants API manquants
      - Exemples de code incorrects
      - Erreurs d'exécution d'outils

      Incluez votre requête, le résultat attendu et le résultat réel lors de la signalisation de problèmes.
    </div>
  </AccordionDetails>
</Accordion>
<br />
