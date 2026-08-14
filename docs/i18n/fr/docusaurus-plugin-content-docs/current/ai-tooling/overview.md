---
title: webforJ AI Plugin
sidebar_position: 1
slug: /ai-tooling
description: >-
  Install the webforJ AI plugin to add the MCP server and Agent Skills to Claude
  Code, Copilot, Cursor, Gemini, and Codex in one step.
_i18n_hash: 44bdaad98af3599ab5fcf57c6a4756c1
---
Le **plugin webforJ AI** est la méthode recommandée pour connecter votre assistant de codage AI à webforJ. Une installation donne à votre assistant l'ensemble des outils : accès en direct à la documentation webforJ, création d'architecture de projet, génération de thèmes, validation de tokens de conception et flux de travail structurés qui lui enseignent comment les utiliser correctement.

## Ce que vous obtenez {#what-you-get}

L'installation du plugin connecte deux éléments complémentaires en une seule étape :

- **[serveur MCP webforJ](/docs/ai-tooling/mcp)** - outils en direct que l'assistant peut appeler à la demande : rechercher des informations dans la base de connaissances webforJ, créer des projets Maven, générer des thèmes DWC, lire la surface de style de tout composant DWC, et valider les tokens `--dwc-*` avant qu'ils n'atterrissent dans votre CSS.
- **[Compétences de l'Agent](/docs/ai-tooling/agent-skills)** - flux de travail structurés qui disent à l'assistant _quand_ se servir de ces outils, dans quel ordre faire les choses, et comment valider le résultat. Cela couvre la création de composants réutilisables et le style des applications webforJ de bout en bout.

Ensemble, ils transforment un assistant AI qui devine les conventions webforJ en un assistant qui les suit.

En plus d'eux, webforJ livre un assistant d'un autre genre :

- **[Assistant craftforJ](/docs/ai-tooling/craftforj-assistant)** - un agent de codage qui fonctionne à l'intérieur de votre application *en cours d'exécution* plutôt que dans votre éditeur. Il écrit du Java librement, compile chaque modification avant que vous ne la voyiez, l'applique, et continue à travailler après le redémarrage de votre application, tout en lisant l'arbre des composants en direct, en changeant des propriétés, en naviguant dans des routes et en ajustant le thème. Il n'y a rien à installer, car il est livré avec webforJ.

:::warning L'IA peut encore faire des erreurs
Même avec le plugin, les assistants IA peuvent produire du code incorrect dans des scénarios complexes. Toujours revoir et tester le code généré avant de le publier.
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

Codex ne charge pas automatiquement les compétences par correspondance de prompt comme d'autres clients. Invoquez-les explicitement :
Codex ne charge pas automatiquement les compétences par correspondance de prompt comme d'autres clients. Invoquez-les explicitement :

```
$webforj:webforj-styling-apps theme this app with a blue palette
$webforj:webforj-creating-components wrap this Custom Element as a webforJ component
```

Les outils MCP fonctionnent automatiquement sans le préfixe `$`.

</TabItem>
</Tabs>

### Autres clients {#other-clients}

Cursor, Kiro, Goose, Junie, Antigravity, et tout autre client compatible avec les Compétences de l'Agent prennent également en charge le plugin - ils utilisent simplement une configuration manuelle plutôt qu'une commande de marché. Voir le [guide d'installation par client](https://github.com/webforj/webforj-ai#clients) pour les étapes exactes.

## Utilisation {#using-it}

Une fois installé, la plupart des assistants chargent automatiquement le bon élément en fonction de votre prompt :

- *"Enveloppez cette bibliothèque d'éléments personnalisés en tant que composant webforJ."* - déclenche la compétence de création de composants
- *"Stylez cette vue avec les tokens de conception DWC."* - déclenche la compétence de style d'applications
- *"Créez un nouveau projet de menu latéral webforJ appelé CustomerPortal."* - appelle le générateur d'architecture de projet MCP
- *"Générez un thème à partir de la couleur de marque `#6366f1`."* - appelle le générateur de thème MCP
- *"Trouvez la documentation webforJ sur `@Route` et le routage."* - appelle la recherche de connaissance MCP

Pour de meilleurs résultats, mentionnez toujours **webforJ** dans vos prompts - c'est le signal que l'assistant utilise pour se tourner vers le plugin plutôt que vers les connaissances générales en Java.

## Mise à jour et désinstallation {#updating-and-uninstalling}

Chaque client pris en charge a ses propres commandes de mise à jour et de désinstallation. Voir le [README de webforj-ai](https://github.com/webforj/webforj-ai#clients) pour les instructions par client.
