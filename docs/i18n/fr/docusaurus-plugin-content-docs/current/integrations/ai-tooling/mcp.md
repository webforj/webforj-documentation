---
title: MCP Server
sidebar_position: 5
_i18n_hash: eea9d8f962b10512151bf7c6935f25e0
---
Le serveur du Protocole de Contexte de Modèle (MCP) de webforJ intègre des assistants de codage AI dans la documentation, les API, les jetons de design et les outils de structure de webforJ. Au lieu de deviner les conventions du framework, l'assistant interroge le serveur et obtient des réponses ancrées dans le véritable webforJ.

:::tip Utilisez le plugin
À moins que vous ne sachiez que vous ne voulez que le serveur MCP, installez le **[plugin AI webforJ](/docs/integrations/ai-tooling)** à la place - il regroupe ce serveur avec les [Compétences de l'Agent](/docs/integrations/ai-tooling/agent-skills) correspondantes en une seule installation.
:::

## Qu'est-ce qu'un MCP ? {#whats-an-mcp}

Le Protocole de Contexte de Modèle est une norme ouverte qui permet aux assistants AI d'appeler des outils externes à la demande. Le serveur MCP de webforJ met en œuvre ce protocole afin que votre assistant puisse :

- Chercher des informations dans la documentation webforJ au lieu de halluciner des noms de méthodes
- Générer de nouveaux projets webforJ à partir des archétypes Maven officiels
- Générer des thèmes DWC accessibles à partir d'une couleur de marque
- Lire la véritable surface de style d'un composant DWC et valider tout jeton `--dwc-*` avant qu'il n'atterrisse dans votre CSS

:::warning L'IA peut encore faire des erreurs
Le serveur MCP améliore considérablement la précision, mais les assistants AI peuvent encore produire du code incorrect dans des scénarios complexes. Passez toujours en revue et testez le code généré avant de le déployer.
:::

## Installation {#installation}

Pour une expérience complète, installez le **[plugin AI webforJ](/docs/integrations/ai-tooling)** - il configure ce serveur aux côtés des Compétences d'Agent dont votre assistant a besoin pour bien l'utiliser.

Si vous ne voulez que le serveur MCP (sans compétences), dirigez votre client vers `https://mcp.webforj.com/mcp` :

<Tabs groupId="ide">
<TabItem value="claude-code" label="Claude Code" default>

```bash
claude mcp add webforj-mcp https://mcp.webforj.com/mcp -t http -s user
```

</TabItem>
<TabItem value="copilot-cli" label="GitHub Copilot CLI">

Le chemin recommandé sur Copilot CLI est le **[plugin AI webforJ](/docs/integrations/ai-tooling)** - il enregistre le serveur MCP pour vous en une étape. Pour une configuration brute uniquement MCP, consultez les instructions par client dans le [dépôt AI webforJ](https://github.com/webforj/webforj-ai#clients).

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

Cursor, Kiro, Goose, Junie, Antigravity, et tout autre client MCP-over-HTTP fonctionnent également - ils utilisent simplement leur propre format de configuration. Consultez le [guide d'installation par client](https://github.com/webforj/webforj-ai#clients) pour le code exact pour chacun.

## Ce que le serveur peut faire {#capabilities}

Lorsque le serveur MCP est connecté, votre assistant AI acquiert les capacités suivantes. Chacune d'elles peut être déclenchée par une demande en langage naturel - l'assistant choisit la bonne automatiquement.

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>Cibler la bonne version de webforJ</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Avant de répondre à des questions sensibles à la version (tout ce qui est spécifique au style ou à l'API), l'assistant détermine la version de webforJ que vous utilisez. Il lit `pom.xml` lorsque c'est disponible et sinon vous pose la question. Chaque réponse ultérieure est limitée à cette version.
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>Cherchez des informations dans la base de connaissances webforJ</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      L'assistant peut interroger la base de connaissances complète de webforJ pour obtenir des réponses ancrées dans le véritable framework. Les résultats sont limités à ce que vous demandez - une question API, un guide, un exemple de code ou le DSL Kotlin.

      **Exemples de demandes :**
      ```
      "Trouvez des exemples de gestion d'événements pour le composant Button de webforJ"

      "Comment configurer le routage avec @Route dans webforJ ?"

      "Montrez-moi un exemple de validation de formulaire webforJ"
      ```
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>Générer un nouveau projet webforJ</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      L'assistant génère la bonne commande d'archétype Maven pour une nouvelle application webforJ à partir de vos exigences (archétype, intégration Spring, nom, groupe).

      **Archétypes :**
      - `hello-world` - application de démarrage avec des composants d'exemple
      - `blank` - structure de projet minimale
      - `tabs` - mise en page d'interface à onglets
      - `sidemenu` - mise en page de navigation latérale

      **Versions :**
      - `webforj` - application webforJ standard
      - `webforj-spring` - webforJ intégré à Spring Boot

      **Exemples de demandes :**
      ```
      "Créez un projet webforJ appelé CustomerPortal en utilisant l'archétype sidemenu"

      "Générez un projet webforJ Spring Boot avec la mise en page à onglets nommée Dashboard"
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
      À partir d'une seule couleur de marque, l'assistant produit un thème DWC complet : palettes primaire, succès, avertissement, danger, info, par défaut et grise avec un contraste de texte automatique. La sortie comprend la feuille de style plus le câblage `@AppTheme` / `@StyleSheet`.

      **Exemples de demandes :**
      ```
      "Générez un thème webforJ à partir de la couleur de marque #6366f1"

      "Créez un thème accessible avec HSL 220, 70, 50 comme couleur primaire"
      ```
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>Bien styliser les composants DWC</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      L'assistant lit la véritable surface de style de chaque composant DWC - propriétés CSS personnalisées, parties d'ombre, attributs réfléchis et slots - avant d'écrire du CSS. Il peut également énumérer chaque balise DWC et résoudre les noms de classe Java de webforJ (`Button`, `TextField`) à leurs équivalents DWC.

      **Exemples de demandes :**
      ```
      "Quelles variables et parties CSS le dwc-button expose-t-il ?"

      "Montrez-moi chaque slot disponible sur dwc-dialog"

      "Quelle balise DWC correspond à la classe TextField de webforJ ?"
      ```

      Associez cela à la compétence d'agent [styling-apps](/docs/integrations/ai-tooling/agent-skills) pour des flux de travail de style de bout en bout.
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>Travailler avec les jetons de design DWC</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      L'assistant peut lister le catalogue autorisé des jetons `--dwc-*` pour votre version de webforJ - graines de palette, nuances, surfaces, espacements, typographie, bordures - filtrés par préfixe ou sous-chaîne. Il validera également tout CSS, Java ou source Markdown que vous lui fournissez par rapport au véritable catalogue de jetons et signalera les noms inconnus avec des corrections suggérées.

      **Exemples de demandes :**
      ```
      "Listez tous les jetons --dwc-space-*"

      "Validez app.css pour tout jeton --dwc-* inconnu"

      "Quelles nuances de palette primaire sont disponibles ?"
      ```

      La validation capture les fautes de frappe et les jetons inventés avant qu'ils ne soient expédiés comme CSS à échec silencieux.
    </div>
  </AccordionDetails>
</Accordion>

## Rédiger de bonnes demandes {#writing-good-prompts}

Le serveur MCP n'est consulté que lorsque votre assistant pense que c'est pertinent. Quelques habitudes le maintiennent engagé :

- **Nommez le framework.** Mentionnez "webforJ" dans la demande afin que l'assistant fasse appel au serveur MCP plutôt qu'à ses connaissances générales en Java.
- **Soyez précis.** `"Créez un projet webforJ appelé InventorySystem avec l'archétype sidemenu et Spring Boot"` est meilleur que `"faites une application"`.
- **Demandez une vérification.** Des phrases comme `"vérifiez contre les docs de webforJ"` ou `"vérifiez ce CSS pour des jetons --dwc-* mauvais"` poussent l'assistant à utiliser les outils au lieu de deviner.

Si votre assistant répond toujours sans consulter le serveur, installez le [plugin AI webforJ](https://github.com/webforj/webforj-ai) - il fournit des compétences d'agent correspondantes qui incitent l'assistant à utiliser automatiquement les outils MCP pour les tâches webforJ.

## FAQ {#faq}

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>Pourquoi l'assistant AI n'utilise-t-il pas le serveur MCP ?</p>
    <p>Pourquoi l'assistant AI n'utilise-t-il pas le serveur MCP ?</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      La plupart des assistants n'accèdent à MCP que lorsqu'ils pensent que la question en a besoin. Deux solutions :

      1. **Installez le [plugin AI webforJ](https://github.com/webforj/webforj-ai)**, qui associe le serveur avec des Compétences d'Agent qui indiquent à l'assistant d'utiliser MCP pour les tâches webforJ.
      2. **Soyez explicite dans votre demande** : incluez "webforJ" dans la question, et pour les cas obstinés, dites "utilisez le serveur MCP de webforJ pour répondre".
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>Comment vérifier que la connexion MCP fonctionne ?</p>
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
      Ouvrez un ticket en utilisant le [modèle de problème MCP de webforJ](https://github.com/webforj/webforj/issues/new?template=mcp_report.yml). Incluez la demande, le résultat attendu et ce que vous avez obtenu.
    </div>
  </AccordionDetails>
</Accordion>
<br />
