---
title: MCP Server
sidebar_position: 5
description: >-
  Connect AI assistants to the webforJ MCP server for live documentation lookup,
  project scaffolding, theme generation, and token validation.
_i18n_hash: e51aa2e6a5a0f6c37a18c404c1104684
---
Le serveur Model Context Protocol (MCP) de webforJ connecte des assistants de codage AI à la documentation, aux API, aux jetons de design et aux outils de scaffolding de webforJ. Au lieu de deviner les conventions du framework, l'assistant interroge le serveur pour obtenir des réponses basées sur le véritable webforJ.

:::tip Utilisez le plugin 
À moins que vous ne sachiez que vous ne souhaitez que le serveur MCP, installez le **[plugin AI webforJ](/docs/ai-tooling)** à la place - il regroupe ce serveur avec les [Compétences d'Agent](/docs/ai-tooling/agent-skills) correspondantes dans une seule installation.
:::

## Qu'est-ce qu'un MCP ? {#whats-an-mcp}

Le Model Context Protocol est une norme ouverte qui permet aux assistants AI d'appeler des outils externes à la demande. Le serveur MCP de webforJ implémente ce protocole afin que votre assistant puisse :

- Rechercher des informations dans la documentation de webforJ au lieu de halluciner des noms de méthode
- Scaffolder de nouveaux projets webforJ à partir des archétypes Maven officiels
- Générer des thèmes DWC accessibles à partir d'une couleur de marque
- Lire la véritable surface de stylage d'un composant DWC et valider tout jeton `--dwc-*` avant qu'il ne se retrouve dans votre CSS

:::warning L'IA peut toujours faire des erreurs 
Le serveur MCP améliore considérablement la précision, mais les assistants AI peuvent toujours produire du code incorrect dans des scénarios complexes. Passez toujours en revue et testez le code généré avant de le déployer.
:::

## Installation {#installation}

Pour une expérience complète, installez le **[plugin AI webforJ](/docs/ai-tooling)** - il configure ce serveur aux côtés des Compétences d'Agent dont votre assistant a besoin pour l'utiliser efficacement.

Si vous souhaitez uniquement le serveur MCP (sans compétences), pointez votre client vers `https://mcp.webforj.com/mcp` :

<Tabs groupId="ide">
<TabItem value="claude-code" label="Claude Code" default>

```bash
claude mcp add webforj-mcp https://mcp.webforj.com/mcp -t http -s user
```

</TabItem>
<TabItem value="copilot-cli" label="GitHub Copilot CLI">

Le chemin recommandé sur Copilot CLI est le **[plugin AI webforJ](/docs/ai-tooling)** - il enregistre le serveur MCP pour vous en une seule étape. Pour une configuration brute uniquement MCP, consultez les instructions par client dans le [dépôt AI de webforJ](https://github.com/webforj/webforj-ai#clients).

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

Cursor, Kiro, Goose, Junie, Antigravity et tout autre client MCP via HTTP fonctionnent également - ils utilisent simplement leur propre format de configuration. Consultez le [guide d'installation par client](https://github.com/webforj/webforj-ai#clients) pour le fragment exact pour chacun.

## Ce que le serveur peut faire {#capabilities}

Lorsque le serveur MCP est connecté, votre assistant AI acquiert les capacités suivantes. Chacune d'elles peut être déclenchée par une demande en langage naturel - l'assistant choisit automatiquement la bonne.

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>Cibler la bonne version de webforJ</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Avant de répondre à des questions sensibles à la version (toute question spécifique au stylage ou à l'API), l'assistant détermine quelle version de webforJ vous utilisez. Il lit `pom.xml` quand c'est disponible et sinon vous le demande. Chaque réponse suivante est limitée à cette version.
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>Rechercher des informations dans la base de connaissances de webforJ</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      L'assistant peut interroger l'intégralité de la base de connaissances de webforJ pour des réponses ancrées dans le véritable framework. Les résultats sont limités à ce que vous demandez - une question sur l'API, un guide, un exemple de code, ou le DSL Kotlin.

      **Exemples de demandes :**
      ```
      "Trouvez les exemples de gestion d'événements du composant Button de webforJ"

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
      L'assistant génère la bonne commande d'archétype Maven pour une nouvelle application webforJ à partir de vos exigences (archétype, intégration Spring, nom, groupe).

      **Archétypes :**
      - `hello-world` - application de démarrage avec des composants exemples
      - `blank` - structure de projet minimale
      - `tabs` - mise en page d'interface à onglets
      - `sidemenu` - mise en page de navigation latérale

      **Variantes :**
      - `webforj` - application standard webforJ
      - `webforj-spring` - webforJ intégré avec Spring Boot

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
      À partir d'une seule couleur de marque, l'assistant produit un thème DWC complet : palettes primaire, succès, avertissement, danger, info, par défaut, et gris avec un contraste de texte automatique. La sortie comprend la feuille de style ainsi que le câblage `@AppTheme` / `@StyleSheet`.

      **Exemples de demandes :**
      ```
      "Générez un thème webforJ à partir de la couleur de marque #6366f1"

      "Créez un thème accessible avec HSL 220, 70, 50 comme primaire"
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
      L'assistant lit la véritable surface de stylage de chaque composant DWC - propriétés CSS personnalisées, parties d'ombre, attributs réfléchis, et emplacements - avant d'écrire du CSS. Il peut également énumérer chaque balise DWC et résoudre les noms de classes Java de webforJ (`Button`, `TextField`) en leurs équivalents DWC.

      **Exemples de demandes :**
      ```
      "Quelles variables CSS et parties le dwc-button expose-t-il ?"

      "Montrez-moi chaque emplacement disponible sur dwc-dialog"

      "À quelle balise DWC la classe TextField de webforJ correspond-elle ?"
      ```

      Associez cela avec la [compétence d'agent styling-apps](/docs/ai-tooling/agent-skills) pour des flux de travail de stylage de bout en bout.
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>Travailler avec les jetons de design DWC</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      L'assistant peut lister le catalogue autoritaire des jetons `--dwc-*` pour votre version de webforJ - semences de palette, nuances, surfaces, espacements, typographie, bordures - filtrés par préfixe ou sous-chaîne. Il validera également tout CSS, Java ou source Markdown que vous lui fournissez par rapport au véritable catalogue de jetons et signalera les noms inconnus avec des corrections suggérées.

      **Exemples de demandes :**
      ```
      "Listez chaque jeton --dwc-space-*"

      "Validez app.css pour tout jeton --dwc-* inconnu"

      "Quelles nuances de palette primaire sont disponibles ?"
      ```

      La validation attrape les fautes de frappe et les jetons inventés avant qu'ils ne soient expédiés en tant que CSS silencieusement échouant.
    </div>
  </AccordionDetails>
</Accordion>

## Rédaction de bonnes demandes {#writing-good-prompts}

Le serveur MCP est uniquement consulté lorsque votre assistant pense que cela est pertinent. Quelques habitudes le maintiennent engagé :

- **Nommez le framework.** Mentionnez "webforJ" dans la demande afin que l'assistant s'adresse au serveur MCP plutôt qu'à ses connaissances générales en Java.
- **Soyez spécifique.** `"Créez un projet webforJ appelé InventorySystem avec l'archétype sidemenu et Spring Boot"` est meilleur que `"faites une application"`.
- **Demandez une vérification.** Des phrases comme `"vérifiez par rapport à la documentation webforJ"` ou `"vérifiez ce CSS pour des jetons --dwc-* mauvais"` incitent l'assistant à utiliser les outils plutôt qu'à deviner.

Si votre assistant répond toujours sans consulter le serveur, installez le [plugin AI webforJ](https://github.com/webforj/webforj-ai) - il expédie des Compétences d'Agent correspondantes qui incitent l'assistant à utiliser automatiquement les outils MCP pour les tâches webforJ.

## FAQ {#faq}

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>Pourquoi l'assistant AI n'utilise-t-il pas le serveur MCP ?</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      La plupart des assistants n'interrogent le MCP que lorsqu'ils pensent que la question le nécessite. Deux solutions :

      1. **Installez le [plugin AI webforJ](https://github.com/webforj/webforj-ai)**, qui associe le serveur aux Compétences d'Agent qui disent à l'assistant d'utiliser le MCP pour les tâches webforJ.
      2. **Soyez explicite dans votre demande** : incluez "webforJ" dans la question, et pour les cas obstinés, dites "utilisez le serveur MCP de webforJ pour répondre".
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

      Puis dans l'inspecteur, connectez-vous à `https://mcp.webforj.com/mcp` et explorez les outils disponibles.
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>Comment signaler des problèmes ?</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Ouvrez un ticket en utilisant le [modèle de problème MCP de webforJ](https://github.com/webforj/webforj/issues/new?template=mcp_report.yml). Incluez la demande, le résultat attendu, et ce que vous avez obtenu.
    </div>
  </AccordionDetails>
</Accordion>
<br />
