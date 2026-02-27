---
title: MCP Server
sidebar_position: 5
_i18n_hash: a45888cf39bbbce0002177da8fe95eba
---
Le serveur du Protocole de Contexte de Modèle webforJ (MCP) offre aux assistants IA un accès direct à la documentation officielle de webforJ, à des exemples de code vérifiés et à des modèles spécifiques au framework, permettant des réponses plus précises et une génération automatisée de projets spécifiquement pour le développement webforJ.

## Qu'est-ce qu'un MCP ?

Le Protocole de Contexte de Modèle est une norme ouverte qui permet aux assistants IA de se connecter à des outils et à de la documentation externes. Le serveur MCP de webforJ implémente ce protocole pour fournir :

- **Recherche de connaissance** - Recherche en langage naturel à travers la documentation, les exemples de code et les modèles de webforJ
- **Génération de projet** - Créer des applications webforJ à partir de modèles officiels avec une structure appropriée
- **Création de thèmes** - Générer des thèmes CSS accessibles suivant les modèles de conception de webforJ

## Pourquoi utiliser le MCP ?

Bien que les assistants IA en codage excellent dans la réponse à des questions basiques, ils peinent avec des requêtes complexes spécifiques à webforJ qui s'étendent sur plusieurs sections de documentation. Sans accès direct aux sources officielles, ils peuvent :

- Générer des méthodes qui n'existent pas dans webforJ
- Référencer des modèles d'API obsolètes ou incorrects  
- Fournir du code qui ne compilera pas
- Confondre la syntaxe de webforJ avec d'autres frameworks Java
- Mal comprendre les modèles spécifiques à webforJ

Avec l'intégration du MCP, les réponses IA sont ancrées dans la documentation réelle de webforJ, des exemples de code et des modèles de framework, fournissant des réponses vérifiables avec des liens directs vers les sources officielles pour une exploration plus approfondie.

:::warning L'IA peut encore faire des erreurs
Bien que le MCP améliore considérablement l'exactitude en fournissant l'accès à des ressources officielles de webforJ, il ne garantit pas une génération de code parfaite. Les assistants IA peuvent encore faire des erreurs dans des scénarios complexes. Vérifiez toujours le code généré et testez-le minutieusement avant de l'utiliser en production.
:::

## Installation

Le serveur MCP de webforJ est hébergé à `https://mcp.webforj.com` avec deux points de terminaison :

- **Point de terminaison MCP** (`/mcp`) - Pour Claude, VS Code, Cursor
- **Point de terminaison SSE** (`/sse`) - Pour les clients legacy

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

1. Ouvrez Claude Desktop et allez dans Paramètres
2. Cliquez sur "Intégrations" dans la barre latérale
3. Cliquez sur "Ajouter une intégration" et collez l'URL : `https://mcp.webforj.com/mcp`
4. Suivez l'assistant de configuration pour compléter la configuration

Pour des instructions détaillées, consultez le [guide d'intégration officiel](https://support.anthropic.com/en/articles/11175166-about-custom-integrations-using-remote-mcp).

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

Les outils sont des fonctions spécialisées que le serveur MCP offre aux assistants IA. Lorsque vous posez une question ou faites une demande, l'IA peut appeler ces outils pour rechercher de la documentation, générer des projets ou créer des thèmes. Chaque outil accepte des paramètres spécifiques et renvoie des données structurées qui aident l'IA à fournir une assistance précise et consciente du contexte.

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>webforj-knowledge-base</code></strong> - Rechercher de la documentation et des exemples
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Cet outil fournit des capacités de recherche sémantique à travers l'écosystème complet de documentation de webforJ. Il comprend le contexte et les relations entre différents concepts de framework, renvoyant des sections de documentation pertinentes, des références d'API et des exemples de code fonctionnels.

      **Exemples de requêtes :**
      ```
      "Rechercher dans la documentation webforJ des exemples de composants Button avec icône"

      "Trouver des modèles de validation de formulaires webforJ dans la dernière documentation"

      "Montrez-moi la configuration actuelle de routage webforJ avec l'annotation @Route"

      "Rechercher dans les docs webforJ des modèles de design responsive FlexLayout"

      "Trouver l'intégration des composants webforj dans la documentation officielle"
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
      Élabore des applications webforJ complètes en utilisant des archétypes Maven officiels. L'outil crée une structure de répertoire de projet standardisée et inclut du code de démarrage basé sur le modèle sélectionné. Les projets générés incluent un système de construction prêt à l'emploi, des dossiers de ressources et des fichiers de configuration pour un développement et un déploiement immédiats.

      **Exemples d'instructions :**
      ```
      "Créer un projet webforJ nommé CustomerPortal en utilisant l'archétype hello-world"

      "Générer un projet webforJ Spring Boot avec mise en page par onglets nommé Dashboard"

      "Créer une nouvelle application webforJ avec un archétype de menu latéral pour le projet AdminPanel"

      "Générer un projet webforJ vierge nommé TestApp avec un groupId com.example"

      "Créer le projet webforJ InventorySystem en utilisant l'archétype de menu latéral avec Spring Boot"
      ```

      Lorsque vous utilisez cet outil, vous pouvez choisir parmi plusieurs modèles de projet :

      **Archétypes** (modèles de projet) :
      - `hello-world` - Application de base avec des composants d'exemple pour démontrer les fonctionnalités de webforJ
      - `blank` - Structure de projet minimale pour partir de zéro
      - `tabs` - Mise en page d'interface à onglets préconstruite pour des applications multi-vues
      - `sidemenu` - Mise en page de menu de navigation latéral pour des panneaux administratifs ou des dashboards

      **Flavors** (intégration de framework) :
      - `webforj` - Application webforJ standard
      - `webforj-spring` - webforJ intégré avec Spring Boot pour l'injection de dépendances et les fonctionnalités d'entreprise

      :::tip Archétypes disponibles
      webforJ est livré avec plusieurs archétypes prédéfinis pour vous aider à démarrer rapidement. Pour une liste complète des archétypes disponibles, consultez le [catalogue des archétypes](/docs/building-ui/archetypes/overview).
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
      Génère des configurations de thème webforJ en utilisant [DWC HueCraft](https://huecraft.dwc.style/). L'outil crée des ensembles complets de propriétés CSS personnalisées avec des variantes de couleurs primaire, secondaire, de succès, d'avertissement, de danger et neutres.

      **Exemples de demandes :**
      ```
      "Générer un thème webforJ avec HSL 220, 70, 50 comme couleur primaire pour notre marque"

      "Créer un thème webforJ accessible nommé 'océan' avec la couleur primaire #0066CC"

      "Générer un thème webforJ utilisant notre couleur de marque #FF5733"

      "Créer un thème webforJ avec HSL 30, 100, 50 nommé 'coucher de soleil' pour notre application"

      "Générer un thème webforJ accessible avec primary RGB 44, 123, 229"
      ```
    </div>
  </AccordionDetails>
</Accordion>

## Prompts disponibles {#available-prompts}

Les prompts sont des instructions IA préconfigurées qui combinent plusieurs outils et workflows pour des tâches courantes. Ils guident l'IA à travers des étapes et des paramètres spécifiques pour fournir des résultats fiables et répétables pour chaque workflow pris en charge.

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>create-app</code></strong> - Créer et exécuter une application webforJ
  </AccordionSummary>
  <AccordionDetails>
    <div>
      **Arguments :**
      - `appName` (requis) - Nom de l'application (ex. : MyApp, TodoList, Dashboard)
      - `archetype` (requis) - Choisissez parmi : `blank`, `hello-world`, `tabs`, `sidemenu`
      - `runServer` (optionnel) - Exécutez automatiquement le serveur de développement (oui/non)
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
      - `primaryColor` (requis) - Couleur en hex (#FF5733), rgb (255,87,51) ou hsl (9,100,60)
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
      2. Écrire un code complet, prêt pour la production
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
3. Remplissez les paramètres requis lorsqu'il est demandé

</TabItem>
<TabItem value="claude-desktop" label="Claude Desktop">

1. Cliquez sur l'icône **+** (plus) dans la zone de saisie du prompt
2. Sélectionnez **"Ajouter depuis webforJ"** dans le menu
3. Choisissez le prompt souhaité (ex. : `create-app`, `create-theme`, `search-webforj`)
4. Claude vous demandera d'entrer les arguments requis
5. Remplissez les paramètres comme demandé

:::tip Vérifier que le MCP est connecté
Recherchez l'icône des outils dans le coin inférieur de la zone de saisie pour confirmer que le serveur MCP de webforJ est connecté.
:::

</TabItem>
</Tabs>

## Meilleures pratiques

Pour obtenir l'assistance webforJ la plus précise et à jour, suivez ces directives pour tirer pleinement parti des fonctionnalités du serveur MCP.

### Assurer l'utilisation du serveur MCP

Les modèles IA peuvent ignorer le serveur MCP s'ils estiment déjà connaître la réponse. Pour garantir que le serveur MCP est effectivement utilisé :

- **Soyez explicite sur webforJ** : Mentionnez toujours "webforJ" dans votre requête pour déclencher des recherches spécifiques au framework
- **Demandez des informations actuelles** : Incluez des phrases comme "dernière documentation webforJ" ou "modèles webforJ actuels"
- **Demandez des exemples vérifiés** : Demandez "exemples de code webforJ fonctionnels" pour forcer la recherche de documentation
- **Référencez des versions spécifiques** : Mentionnez votre version webforJ (ex. : "webforJ `25.02`") pour obtenir des résultats précis

### Écriture de prompts spécifiques

**Bonnes exemples :**
```
"Rechercher dans la documentation webforJ sur la gestion des événements des composants Button avec exemples"

"Créer un projet webforJ nommé InventorySystem en utilisant l'archétype de menu latéral avec Spring Boot"

"Générer un thème webforJ avec HSL 220, 70, 50 comme couleur primaire pour la marque"
```

**Mauvaises exemples :**
```
"Comment fonctionnent les boutons"

"Faites une application"

"Rendez-la bleue"
```

### Forcer l'utilisation de l'outil MCP

Si l'IA fournit des réponses génériques sans utiliser le serveur MCP :

1. **Demandez explicitement** : "Utilisez le serveur MCP de webforJ pour rechercher `[query]`"
2. **Demandez des références de documentation** : "Trouvez dans la documentation webforJ comment faire `[query]`"
3. **Demandez une vérification** : "Vérifiez cette solution contre la documentation de webforJ"
4. **Soyez spécifique au framework** : Incluez toujours "webforJ" dans vos requêtes

## Personnalisation de l'IA {#ai-customization}

Configurez vos assistants IA pour qu'ils utilisent automatiquement le serveur MCP et suivent les meilleures pratiques de webforJ. Ajoutez des instructions spécifiques au projet afin que vos assistants IA utilisent toujours le serveur MCP, suivent les normes de documentation de webforJ et fournissent des réponses précises et à jour qui correspondent aux exigences de votre équipe.

### Fichiers de configuration de projet

- Pour **VS Code et Copilot**, créez `.github/copilot-instructions.md`
- Pour **Claude Code**, créez `CLAUDE.md` à la racine de votre projet

Ajoutez ce qui suit au fichier markdown créé :
```markdown
## Utilisez le serveur MCP de webforJ pour répondre à toutes les questions webforJ

- Appelez toujours l'outil "webforj-knowledge-base" pour récupérer les docs pertinents à la question
- Vérifiez toutes les signatures d'API contre la documentation officielle
- Ne supposez jamais que les noms de méthodes ou les paramètres existent sans vérification

Vérifiez toujours que le code compile avec `mvn compile` avant de suggérer.
```

## FAQ

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>Pourquoi l'IA n'utilise-t-elle pas le serveur MCP de webforJ ?</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      La plupart des assistants IA nécessitent des instructions explicites pour utiliser les serveurs MCP. Configurez votre client IA avec les instructions de la section [Personnalisation de l'IA](#ai-customization). Sans ces instructions, les assistants IA peuvent se baser sur leurs données d'apprentissage au lieu de consulter le serveur MCP.

      **Solution rapide :**
      Incluez "utiliser le webforJ MCP" dans votre prompt ou créez le fichier de configuration approprié (`.github/copilot-instructions.md` ou `CLAUDE.md`).
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
      3. Visualisez les outils disponibles et testez des requêtes
      4. Surveillez les journaux de requêtes/réponses pour débogage
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
      - **Point de terminaison SSE** (`/sse`) - Événements envoyés par le serveur pour des clients legacy comme Windsurf

      La plupart des utilisateurs devraient utiliser le point de terminaison MCP. Utilisez SSE uniquement si votre client ne prend pas en charge le protocole MCP standard.
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>Est-il possible d'utiliser le serveur MCP sans fichiers de configuration ?</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Oui, mais ce n'est pas recommandé. Sans fichiers de configuration, vous devez manuellement demander à l'IA d'utiliser le serveur MCP à chaque conversation. Les fichiers de configuration instruisent automatiquement l'IA d'utiliser le serveur MCP pour chaque interaction, afin que vous n'ayez pas à répéter les instructions à chaque fois.

      **Approche manuelle :**
      Commencez les prompts par : "Utiliser le serveur webforJ MCP pour..."

      **Alternative : utilisez des prompts préconfigurés**
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
      **Signaler des problèmes :** [Modèle de problème MCP webforJ](https://github.com/webforj/webforj/issues/new?template=mcp_report.yml)
      
      **Problèmes courants à signaler :**
      - Documentation obsolète dans les résultats de recherche
      - Méthodes ou composants d'API manquants
      - Exemples de code incorrects
      - Erreurs d'exécution des outils

      Incluez votre requête, le résultat attendu et le résultat réel lorsque vous signalez des problèmes.
    </div>
  </AccordionDetails>
</Accordion>
<br />
