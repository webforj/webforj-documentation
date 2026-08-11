---
title: webforJ AI Plugin
sidebar_position: 1
slug: /ai-tooling
sidebar_class_name: new-content
description: >-
  Install the webforJ AI plugin to add the MCP server and Agent Skills to Claude
  Code, Copilot, Cursor, Gemini, and Codex in one step.
_i18n_hash: db80016ad151e338c6e353caaa7070d9
---
Le **plugin webforJ AI** est la méthode recommandée pour connecter votre assistant de codage AI à webforJ. Une seule installation donne à votre assistant l'ensemble des outils : accès en direct aux documents webforJ, génération de projets, génération de thèmes, validation des design tokens et flux de travail structurés qui lui enseignent comment tout utiliser correctement.

## Ce que vous obtenez {#what-you-get}

L'installation du plugin connecte deux éléments complémentaires en une seule étape :

- **[serveur MCP webforJ](/docs/ai-tooling/mcp)** - outils en direct que l'assistant peut appeler à la demande : rechercher des informations dans la base de connaissances webforJ, générer des projets Maven, créer des thèmes DWC, lire la surface de style de n'importe quel composant DWC et valider les tokens `--dwc-*` avant qu'ils n'atterrissent dans votre CSS.
- **[Compétences de l'agent](/docs/ai-tooling/agent-skills)** - flux de travail structurés qui indiquent à l'assistant _quand_ utiliser ces outils, dans quel ordre procéder, et comment valider le résultat. Couvre la construction de composants réutilisables et le style des applications webforJ de bout en bout.

Ensemble, ils transforment un assistant AI qui devine les conventions de webforJ en un qui les suit.

:::warning L'IA peut encore faire des erreurs
Même avec le plugin, les assistants AI peuvent produire du code incorrect dans des scénarios complexes. Vérifiez toujours et testez le code généré avant de le mettre en production.
:::

## Installation {#installation}

<Tabs groupId="ide">
<TabItem value="claude-code" label="Claude Code" default>

```bash
claude plugin marketplace add webforj/webforj-ai
claude plugin install webforj@webforj-ai
```

Vérifiez dans Claude Code :

```
/plugin
/mcp
```

Le plugin `webforj` apparaît sous **Installé**. Le serveur MCP apparaît comme `plugin:webforj:webforj-mcp` sous serveurs connectés.

</TabItem>
<TabItem value="copilot-cli" label="GitHub Copilot CLI">

```bash
copilot plugin marketplace add webforj/webforj-ai
copilot plugin install webforj@webforj-ai
```

Vérifiez :

```bash
copilot plugin list
```

</TabItem>
<TabItem value="vscode" label="VS Code + Copilot">

Depuis la palette de commandes, exécutez `Chat: Install Plugin From Source`, puis collez :

```
webforj/webforj-ai
```

</TabItem>
<TabItem value="gemini" label="Gemini CLI">

```bash
gemini extensions install https://github.com/webforj/webforj-ai
```

Vérifiez :

```bash
gemini extensions list
```

</TabItem>
<TabItem value="codex" label="OpenAI Codex CLI">

```bash
codex plugin marketplace add webforj/webforj-ai
```

Puis ouvrez une session Codex, exécutez `/plugins`, sélectionnez `webforj`, et appuyez sur **Espace** pour l'activer.

Codex ne charge pas automatiquement les compétences par correspondance d'invite comme d'autres clients. Invoquez-les explicitement :
```
$webforj:webforj-styling-apps theme this app with a blue palette
$webforj:webforj-creating-components wrap this Custom Element as a webforJ component
```

Les outils MCP fonctionnent automatiquement sans le préfixe `$`.

</TabItem>
</Tabs>

### Autres clients {#other-clients}

Cursor, Kiro, Goose, Junie, Antigravity, et tout autre client compatible avec les compétences d'agent prennent également en charge le plugin - ils utilisent simplement une configuration manuelle plutôt qu'une commande de marché. Consultez le [guide d'installation par client](https://github.com/webforj/webforj-ai#clients) pour les étapes exactes.

## Utilisation {#using-it}

Une fois installé, la plupart des assistants chargent le bon élément automatiquement en fonction de votre invite :

- *"Enveloppez cette bibliothèque d'éléments personnalisés en tant que composant webforJ."* - déclenche la compétence de création de composants
- *"Stylisez cette vue avec les design tokens DWC."* - déclenche la compétence de stylisation d'applications
- *"Générez un nouveau projet de menu latéral webforJ appelé CustomerPortal."* - appelle le générateur de projets MCP
- *"Générez un thème à partir de la couleur de marque `#6366f1`."* - appelle le générateur de thèmes MCP
- *"Trouvez les documents webforJ sur `@Route` et le routage."* - appelle la recherche de connaissances MCP

Pour de meilleurs résultats, mentionnez toujours **webforJ** dans vos invites - c'est le signal que l'assistant utilise pour accéder au plugin plutôt qu'à des connaissances générales sur Java.

## Mise à jour et désinstallation {#updating-and-uninstalling}

Chaque client pris en charge a ses propres commandes de mise à jour et de désinstallation. Consultez le [README webforj-ai](https://github.com/webforj/webforj-ai#clients) pour des instructions spécifiques à chaque client.
