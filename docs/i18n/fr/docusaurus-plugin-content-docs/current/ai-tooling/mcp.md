---
title: MCP Server
sidebar_position: 5
description: >-
  Connect AI assistants to the webforJ MCP server for live documentation lookup,
  project scaffolding, theme generation, and token validation.
_i18n_hash: fb0e068ee7d7a489237e021b24a883b0
---
Le serveur du protocole Model Context Protocol (MCP) de webforJ intègre des assistants de codage AI dans la documentation, les API, les tokens de design et les outils de scaffolding de webforJ. Au lieu de deviner les conventions de framework, l'assistant interroge le serveur et obtient des réponses basées sur le véritable webforJ.

:::tip Utilisez le plugin
À moins que vous sachiez que vous ne souhaitez que le serveur MCP, installez le **[plugin AI webforJ](/docs/ai-tooling)** à la place - il regroupe ce serveur avec les [Compétences de l'Agent](/docs/ai-tooling/agent-skills) correspondantes en une seule installation.
:::

## Qu'est-ce qu'un MCP ? {#whats-an-mcp}

Le Model Context Protocol est un standard ouvert qui permet aux assistants AI d'appeler des outils externes à la demande. Le serveur MCP de webforJ met en œuvre ce protocole afin que votre assistant puisse :

- Rechercher des informations dans la documentation webforJ au lieu d'halluciner des noms de méthodes
- Scaffolder de nouveaux projets webforJ à partir d'archétypes Maven officiels
- Générer des thèmes DWC accessibles à partir d'une couleur de marque
- Lire la véritable surface de style d'un composant DWC, et valider tout token `--dwc-*` avant qu'il n'atterrisse dans votre CSS

:::warning L'IA peut encore faire des erreurs
Le serveur MCP améliore considérablement l'exactitude, mais les assistants AI peuvent toujours produire un code incorrect dans des scénarios complexes. Passez toujours en revue et testez le code généré avant de le déployer.
:::

## Installation {#installation}

Pour une expérience complète, installez le **[plugin AI webforJ](/docs/ai-tooling)** - il configure ce serveur avec les Compétences de l'Agent dont votre assistant a besoin pour bien l'utiliser.

Si vous ne souhaitez que le serveur MCP (sans compétences), dirigez votre client vers `https://mcp.webforj.com/mcp` :

<Tabs groupId="ide">
<TabItem value="claude-code" label="Claude Code" default>

```bash
claude mcp add webforj-mcp https://mcp.webforj.com/mcp -t http -s user
```

</TabItem>
<TabItem value="copilot-cli" label="GitHub Copilot CLI">

Le chemin recommandé sur Copilot CLI est le **[plugin AI webforJ](/docs/ai-tooling)** - il enregistre le serveur MCP pour vous en une seule étape. Pour une configuration brute uniquement MCP, voir les instructions par client dans le [dépôt AI de webforJ](https://github.com/webforj/webforj-ai#clients).

</TabItem>
<TabItem value="vscode" label="VS Code + Copilot">

Ajoutez à vos paramètres VS Code :

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
<TabItem value="gemini" label="Gemini CLI">

Ajoutez à `~/.gemini/settings.json` :

```json
{
  "mcpServers": {
    "webforj-mcp": {
      "httpUrl": "https://mcp.webforj.com/mcp"
    }
  }
}
```

</TabItem>
<TabItem value="codex" label="OpenAI Codex CLI">

Ajoutez à `~/.codex/config.toml` :

```toml
[mcp_servers.webforj-mcp]
url = "https://mcp.webforj.com/mcp"
```

</TabItem>
</Tabs>

### Autres clients {#other-clients}

Cursor, Kiro, Goose, Junie, Antigravity, et tout autre client MCP-over-HTTP fonctionnent également - ils utilisent simplement leur propre format de configuration. Consultez le [guide d'installation par client](https://github.com/webforj/webforj-ai#clients) pour le snippet exact pour chacun.

## Ce que le serveur peut faire {#capabilities}

Lorsque le serveur MCP est connecté, votre assistant AI gagne les capacités suivantes. Chacune d'elles peut être déclenchée par une demande en langage naturel - l'assistant choisit la bonne automatiquement.

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>Cibler la bonne version de webforJ</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Avant de répondre à des questions sensibles à la version (tout ce qui concerne le style ou l'API), l'assistant détermine sur quelle version de webforJ vous vous trouvez. Il lit `pom.xml` lorsque c'est disponible et sinon vous demande. Chaque réponse suivante est limitée à cette version.
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>Rechercher des informations dans la base de connaissances webforJ</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      L'assistant peut interroger l'intégralité de la base de connaissances webforJ pour obtenir des réponses ancrées dans le véritable framework. Les résultats sont limités à ce que vous demandez - une question API, un guide, un exemple de code, ou le Kotlin DSL.

      **Exemples de requêtes :**
      ```
      "Trouvez les exemples de gestion des événements du composant Button de webforJ"

      "Comment configurer le routage avec @Route dans webforJ ?"

      "Montrez-moi un exemple de validation de formulaire webforJ"
      ```
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>Scaffolder un nouveau projet webforJ</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      L'assistant génère la commande d'archétype Maven correcte pour une nouvelle application webforJ en fonction de vos besoins (archétype, intégration Spring, nom, groupe).

      **Archétypes :**
      - `hello-world` - application de démarrage avec des composants d'exemple
      - `blank` - structure de projet minimale
      - `tabs` - présentation d'interface avec onglets
      - `sidemenu` - présentation de navigation latérale

      **Variantes :**
      - `webforj` - application webforJ standard
      - `webforj-spring` - webforJ intégré avec Spring Boot

      **Exemples de requêtes :**
      ```
      "Créez un projet webforJ nommé CustomerPortal en utilisant l'archétype sidemenu"

      "Générez un projet webforJ Spring Boot avec la présentation en onglets nommé Dashboard"
      ```

      :::tip Archétypes disponibles
      Pour la liste complète des archétypes, consultez le [catalogue des archétypes](/docs/building-ui/archetypes/overview).
      :::
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>Générer un thème DWC</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      À partir d'une seule couleur de marque, l'assistant produit un thème DWC complet : palettes primaire, succès, avertissement, danger, info, par défaut, et gris avec contraste de texte automatique. La sortie comprend la feuille de style ainsi que le câblage `@AppTheme` / `@StyleSheet`.

      **Exemples de requêtes :**
      ```
      "Générez un thème webforJ à partir de la couleur de marque #6366f1"

      "Créez un thème accessible avec HSL 220, 70, 50 comme couleur primaire"
      ```
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>Styliser correctement les composants DWC</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      L'assistant lit la véritable surface de style de chaque composant DWC - propriétés CSS personnalisées, parties d'ombre, attributs réfléchis et slots - avant d'écrire du CSS. Il peut également énumérer chaque tag DWC et résoudre les noms de classes Java de webforJ (`Button`, `TextField`) en leurs équivalents DWC.

      **Exemples de requêtes :**
      ```
      "Quelles variables CSS et parties le dwc-button expose-t-il ?"

      "Montrez-moi chaque slot disponible sur dwc-dialog"

      "À quel tag DWC la classe TextField de webforJ correspond-elle ?"
      ```

      Associez cela avec la compétence d'agent [styling-apps](/docs/ai-tooling/agent-skills) pour des flux de travail de stylisation de bout en bout.
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>Travailler avec les tokens de design DWC</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      L'assistant peut lister le catalogue autoritaire de tokens `--dwc-*` pour votre version de webforJ - graines de palette, nuances, surfaces, espacements, typographie, bordures - filtré par préfixe ou sous-chaîne. Il validé également tout CSS, Java ou source Markdown que vous lui soumettez par rapport au véritable catalogue de tokens et signale les noms inconnus avec des corrections suggérées.

      **Exemples de requêtes :**
      ```
      "Listez chaque token --dwc-space-*"

      "Validez app.css pour tout token --dwc-* inconnu"

      "Quelles nuances de palette primaire sont disponibles ?"
      ```

      La validation détecte les fautes de frappe et les tokens inventés avant qu'ils ne soient silencieusement perdus en CSS.
    </div>
  </AccordionDetails>
</Accordion>

## Rédiger de bonnes requêtes {#writing-good-prompts}

Le serveur MCP n'est consulté que lorsque votre assistant pense que c'est pertinent. Quelques habitudes maintiennent son engagement :

- **Nommez le framework.** Mentionnez "webforJ" dans la requête afin que l'assistant se tourne vers le serveur MCP plutôt que vers ses connaissances générales en Java.
- **Soyez spécifique.** `"Créez un projet webforJ nommé InventorySystem avec l'archétype sidemenu et Spring Boot"` vaut mieux que `"faites une application"`.
- **Demandez une vérification.** Des phrases comme `"vérifiez contre la documentation webforJ"` ou `"vérifiez ce CSS pour de mauvais tokens --dwc-*"` incitent l'assistant à utiliser les outils plutôt qu'à deviner.

Si votre assistant continue de répondre sans consulter le serveur, installez le [plugin AI webforJ](https://github.com/webforj/webforj-ai) - il expédie des Compétences d'Agent correspondantes qui incitent l'assistant à utiliser les outils MCP automatiquement pour les tâches webforJ.

## FAQ {#faq}

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>Pourquoi l'assistant AI n'utilise-t-il pas le serveur MCP ?</p>
    <p>Pourquoi l'assistant AI n'utilise-t-il pas le serveur MCP ?</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      La plupart des assistants ne se tournent vers le MCP que lorsqu'ils pensent que la question en a besoin. Deux solutions :

      1. **Installez le [plugin AI webforJ](https://github.com/webforj/webforj-ai)**, qui associe le serveur avec des Compétences d'Agent qui instruisent l'assistant à utiliser le MCP pour les tâches webforJ.
      2. **Soyez explicite dans votre requête** : incluez "webforJ" dans la question, et pour les cas obstinés, dites "utilisez le serveur MCP de webforJ pour répondre".
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>Comment vérifier si la connexion MCP fonctionne ?</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Utilisez l'inspecteur MCP :

      ```bash
      npx @modelcontextprotocol/inspector
      ```

      Ensuite, dans l'inspecteur, connectez-vous à `https://mcp.webforj.com/mcp` et explorez les outils disponibles.
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>Comment signaler des problèmes ?</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Ouvrez un ticket en utilisant le [modèle de problème MCP de webforJ](https://github.com/webforj/webforj/issues/new?template=mcp_report.yml). Incluez la requête, le résultat attendu, et ce que vous avez obtenu.
    </div>
  </AccordionDetails>
</Accordion>
<br />
