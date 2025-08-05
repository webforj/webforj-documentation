---
title: MCP Server
sidebar_position: 2.5
sidebar_class_name: new-content
_i18n_hash: 640740e70970d2eaa1379ce63374ed94
---
Le serveur du Protocole de Contexte de Modèle (MCP) webforJ fournit aux assistants AI un accès direct à la documentation officielle de webforJ, à des exemples de code vérifiés, et à des modèles spécifiques au framework, permettant des réponses avec des réponses plus précises et la génération automatique de projets spécifiquement pour le développement webforJ.

## Qu'est-ce qu'un MCP ?

Le Protocole de Contexte de Modèle est une norme ouverte qui permet aux assistants AI de se connecter à des outils externes et à de la documentation. Le serveur MCP de webforJ implémente ce protocole pour fournir :

- **Recherche de connaissances** - Recherche en langage naturel à travers la documentation webforJ, des exemples de code et des modèles
- **Génération de projets** - Créer des applications webforJ à partir de modèles officiels avec une structure appropriée
- **Création de thèmes** - Générer des thèmes CSS accessibles suivant les modèles de design webforJ

## Pourquoi utiliser le MCP ?

Alors que les assistants de codage AI excellent dans la réponse à des questions basiques, ils peinent avec des requêtes complexes spécifiques à webforJ qui s'étendent sur plusieurs sections de documentation. Sans accès direct à des sources officielles, ils peuvent :

- Générer des méthodes qui n'existent pas dans webforJ
- Référencer des modèles d'API obsolètes ou incorrects
- Fournir du code qui ne compile pas
- Confondre la syntaxe webforJ avec d'autres frameworks Java
- Mal interpréter les modèles spécifiques à webforJ

Avec l'intégration du MCP, les réponses AI sont ancrées dans la documentation réelle de webforJ, des exemples de code et des modèles de framework, fournissant des réponses vérifiables avec des liens directs vers des sources officielles pour une exploration plus approfondie.

:::warning L'IA peut encore faire des erreurs
Bien que le MCP améliore considérablement la précision en fournissant un accès à des ressources officielles de webforJ, cela ne garantit pas une génération de code parfaite. Les assistants AI peuvent encore faire des erreurs dans des scénarios complexes. Vérifiez toujours le code généré et testez-le soigneusement avant de l'utiliser en production.
:::

## Installation

Le serveur MCP de webforJ est hébergé à `https://mcp.webforj.com` avec deux points de terminaison :

- **Point de terminaison MCP** (`/mcp`) - Pour Claude, VS Code, Cursor
- **Point de terminaison SSE** (`/sse`) - Pour les clients légataires

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

Ajoutez ce serveur en utilisant le panneau d'intégrations dans les paramètres de Claude Desktop :

1. Ouvrez Claude Desktop et allez dans les paramètres
2. Cliquez sur "Intégrations" dans la barre latérale
3. Cliquez sur "Ajouter une intégration" et collez l'URL : `https://mcp.webforj.com/mcp`
4. Suivez l'assistant de configuration pour terminer la configuration

Pour des instructions détaillées, voyez le [guide d'intégration officiel](https://support.anthropic.com/en/articles/11175166-about-custom-integrations-using-remote-mcp).

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

Les outils sont des fonctions spécialisées que le serveur MCP fournit aux assistants AI. Lorsque vous posez une question ou faites une demande, l'IA peut appeler ces outils pour rechercher de la documentation, générer des projets ou créer des thèmes. Chaque outil accepte des paramètres spécifiques et renvoie des données structurées qui aident l'IA à fournir une assistance précise et contextuelle.

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>webforj-knowledge-base</code></strong> - Recherche de documentation et d'exemples
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Cet outil fournit des capacités de recherche sémantique à travers l'écosystème de documentation de webforJ. Il comprend le contexte et les relations entre différents concepts du framework, renvoyant des sections de documentation pertinentes, des références d'API et des exemples de code fonctionnels.

      **Exemples de requêtes :**
      ```
      "Rechercher dans la documentation de webforJ pour le composant Button avec des exemples d'icônes"

      "Trouver des modèles de validation de formulaire webforJ dans la documentation la plus récente"

      "Montrez-moi la configuration actuelle de routage webforJ avec l'annotation @Route"

      "Rechercher dans la documentation de webforJ des modèles de design responsive FlexLayout"

      "Trouver l'intégration des composants web dans la documentation officielle de webforJ"
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
      Configure des applications webforJ complètes en utilisant des archétypes Maven officiels. L'outil crée une structure de répertoire de projet standardisée et inclut du code de démarrage basé sur le modèle sélectionné. Les projets générés incluent un système de build prêt à l'emploi, des dossiers de ressources et des fichiers de configuration pour un développement et un déploiement immédiats.

      **Exemples de requêtes :**
      ```
      "Créer un projet webforJ nommé CustomerPortal en utilisant l'archétype hello-world"

      "Générer un projet webforJ Spring Boot avec un layout d'onglets nommé Dashboard"

      "Créer une nouvelle application webforJ avec l'archétype sidemenu pour le projet AdminPanel"

      "Générer un projet webforJ vide nommé TestApp avec le groupId com.example"

      "Créer un projet webforJ InventorySystem en utilisant l'archétype sidemenu avec Spring Boot"
      ```

      Lors de l'utilisation de cet outil, vous pouvez choisir parmi plusieurs modèles de projet :

      **Archétypes** (modèles de projet) :
      - `hello-world` - Application de base avec des composants d'exemple pour démontrer les fonctionnalités de webforJ
      - `blank` - Structure de projet minimale pour partir de zéro
      - `tabs` - Interface de layout à onglets préconstruite pour les applications multi-vues
      - `sidemenu` - Layout de menu de navigation latéral pour les panneaux administratifs ou les tableaux de bord

      **Flavors** (intégration de framework) :
      - `webforj` - Application webforJ standard
      - `webforj-spring` - webforJ intégré avec Spring Boot pour l'injection de dépendances et les fonctionnalités d'entreprise

      :::tip Archétypes disponibles
      webforJ est livré avec plusieurs archétypes prédéfinis pour vous aider à démarrer rapidement. Pour une liste complète des archétypes disponibles, voir le [catalogue des archétypes](../building-ui/archetypes/overview).
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
      Génère des configurations de thème webforJ en utilisant [DWC HueCraft](https://huecraft.dwc.style/). L'outil crée des ensembles complets de propriétés CSS personnalisées avec des variantes de couleurs primaire, secondaire, succès, avertissement, danger et neutre.

      **Exemples de requêtes :**
      ```
      "Générer un thème webforJ avec HSL 220, 70, 50 comme couleur primaire pour notre marque"

      "Créer un thème webforJ accessible nommé 'ocean' avec la couleur primaire #0066CC"

      "Générer un thème webforJ en utilisant notre couleur de marque #FF5733"

      "Créer un thème webforJ avec HSL 30, 100, 50 nommé 'sunset' pour notre application"

      "Générer un thème webforJ accessible avec la couleur RGB primaire 44, 123, 229"
      ```
    </div>
  </AccordionDetails>
</Accordion>

## Instructions disponibles {#available-prompts}

Les instructions sont des instructions AI préconfigurées qui combinent plusieurs outils et flux de travail pour des tâches courantes. Elles guident l'IA à travers des étapes et des paramètres spécifiques pour fournir des résultats fiables et répétables pour chaque flux de travail pris en charge.

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>create-app</code></strong> - Créer et exécuter une application webforJ
  </AccordionSummary>
  <AccordionDetails>
    <div>
      **Arguments :**
      - `appName` (obligatoire) - Nom de l'application (par ex. : MyApp, TodoList, Dashboard)
      - `archetype` (obligatoire) - Choisir parmi : `blank`, `hello-world`, `tabs`, `sidemenu`
      - `runServer` (facultatif) - Exécuter automatiquement le serveur de développement (oui/non)
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
      L'instruction configure l'IA pour :

      1. Rechercher dans la base de connaissances de manière exhaustive
      2. Écrire du code complet et prêt pour la production
      3. Compiler le projet en utilisant `mvn compile` pour vérifier qu'il n'y a pas d'erreurs de build
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
3. Choisissez le prompt souhaité (par ex. : `create-app`, `create-theme`, `search-webforj`)
4. Claude vous invitera à entrer les arguments requis
5. Remplissez les paramètres comme demandé

:::tip Vérifiez que le MCP est connecté
Recherchez l'icône des outils dans le coin inférieur de la zone de saisie pour confirmer que le serveur MCP de webforJ est connecté.
:::

</TabItem>
</Tabs>

## Meilleures pratiques

Pour obtenir l'assistance webforJ la plus précise et à jour, suivez ces directives pour profiter pleinement des fonctionnalités du serveur MCP.

### Assurer l'utilisation du serveur MCP

Les modèles AI peuvent ignorer le serveur MCP s'ils estiment déjà connaître la réponse. Pour s'assurer que le serveur MCP est réellement utilisé :

- **Soyez explicite sur webforJ** : Mentionnez toujours "webforJ" dans votre requête pour déclencher les recherches spécifiques au framework
- **Demandez des informations actuelles** : Incluez des phrases telles que "dernière documentation webforJ" ou "modèles webforJ actuels"
- **Demandez des exemples vérifiés** : Demandez "exemples de code webforJ fonctionnels" pour forcer la recherche de documentation
- **Référencez des versions spécifiques** : Mentionnez votre version de webforJ (par ex. : "webforJ `25.02`") pour obtenir des résultats précis

### Rédaction de prompts spécifiques

**Bonnes exemples :**
```
"Rechercher dans la documentation de webforJ pour la gestion d'événements du composant Button avec des exemples"

"Créer un projet webforJ nommé InventorySystem en utilisant l'archétype sidemenu avec Spring Boot"

"Générer un thème webforJ avec HSL 220, 70, 50 comme couleur primaire pour la marque d'entreprise"
```

**Mauvaises exemples :**
```
"Comment fonctionnent les boutons"

"Faites une application"

"Rendez-le bleu"
```

### Forcer l'utilisation des outils MCP

Si l'IA fournit des réponses génériques sans utiliser le serveur MCP :

1. **Demandez explicitement** : "Utilisez le serveur MCP de webforJ pour rechercher `[requête]`"
2. **Demandez des références de documentation** : "Trouvez dans la documentation webforJ comment `[requête]`"
3. **Demandez une vérification** : "Vérifiez cette solution par rapport à la documentation de webforJ"
4. **Soyez spécifique au framework** : Incluez toujours "webforJ" dans vos requêtes

## Personnalisation de l'IA {#ai-customization}

Configurez vos assistants AI pour qu'ils utilisent automatiquement le serveur MCP et suivent les meilleures pratiques de webforJ. Ajoutez des instructions spécifiques au projet afin que vos assistants AI utilisent toujours le serveur MCP, suivent les normes de documentation webforJ, et fournissent des réponses précises et à jour qui correspondent aux exigences de votre équipe.

### Fichiers de configuration de projet

- Pour **VS Code et Copilot**, créez `.github/copilot-instructions.md`
- Pour **Claude Code**, créez `CLAUDE.md` à la racine de votre projet

Ajoutez ce qui suit au fichier markdown créé :
```markdown
## Utilisez le serveur MCP de webforJ pour répondre à toutes les questions sur webforJ

- Appelez toujours l'outil "webforj-knowledge-base" pour récupérer les documents pertinents à la question
- Vérifiez toutes les signatures d'API par rapport à la documentation officielle
- Ne supposez jamais que des noms de méthode ou des paramètres existent sans vérifier

Vérifiez toujours que le code compile avec `mvn compile` avant de suggérer.
```

## FAQ

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>Pourquoi l'IA n'utilise-t-elle pas le serveur MCP de webforJ ?</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      La plupart des assistants AI nécessitent des instructions explicites pour utiliser les serveurs MCP. Configurez votre client AI avec les instructions de la section [Personnalisation de l'IA](#ai-customization). Sans ces instructions, les assistants AI peuvent se fier à leurs données d'entraînement plutôt qu'à interroger le serveur MCP.

      **Solution rapide :**
      Incluez "utiliser le MCP de webforJ" dans votre prompt ou créez le fichier de configuration approprié (`.github/copilot-instructions.md` ou `CLAUDE.md`).
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

      Attendez le message : `🔍 L'inspecteur MCP est opérationnel à http://127.0.0.1:6274` (le port peut varier)

      Ensuite, dans l'inspecteur :
      1. Entrez l'URL du serveur MCP : `https://mcp.webforj.com/mcp`
      2. Cliquez sur "Connecter" pour établir la connexion
      3. Visualisez les outils disponibles et testez les requêtes
      4. Surveillez les journaux de requêtes/réponses pour déboguer
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
      - **Point de terminaison SSE** (`/sse`) - Événements envoyés par le serveur pour les clients légataires comme Windsurf

      La plupart des utilisateurs devraient utiliser le point de terminaison MCP. N'utilisez le SSE que si votre client ne prend pas en charge le protocole MCP standard.
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>Est-il possible d'utiliser le serveur MCP sans fichiers de configuration ?</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Oui, mais ce n'est pas recommandé. Sans fichiers de configuration, vous devez manuellement demander à l'IA d'utiliser le serveur MCP dans chaque conversation. Les fichiers de configuration instruisent automatiquement l'IA d'utiliser le serveur MCP pour chaque interaction, donc vous n'avez pas à répéter les instructions à chaque fois.

      **Approche manuelle :**
      Commencez les prompts par : "Utilisez le serveur MCP de webforJ pour..."

      **Alternative : Utilisez des prompts préconfigurés**
      Le serveur MCP fournit des prompts qui fonctionnent sans fichiers de configuration :
      - `/create-app` - Générer de nouvelles applications webforJ
      - `/create-theme` - Créer des thèmes CSS accessibles
      - `/search-webforj` - Recherche avancée dans la documentation

      Voir [Instructions disponibles](#available-prompts) pour plus de détails.
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>Comment contribuer ou signaler des problèmes</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      **Signalez des problèmes :** [Commentaires sur le MCP de webforJ](https://github.com/webforj/webforj-mcp-feedback/issues)
      
      **Problèmes courants à signaler :**
      - Documentation obsolète dans les résultats de recherche
      - Méthodes ou composants d'API manquants
      - Exemples de code incorrects
      - Erreurs d'exécution d'outil

      Incluez votre requête, le résultat attendu et le résultat réel lors de la signalisation de problèmes.
    </div>
  </AccordionDetails>
</Accordion>
<br />
