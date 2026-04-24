---
title: webforJ AI Plugin
sidebar_position: 1
slug: /integrations/ai-tooling
sidebar_class_name: new-content
_i18n_hash: e02b32f83a943a803532854ffd334a9b
---
Le **plugin webforJ AI** est la méthode recommandée pour connecter votre assistant de codage AI à webforJ. Une installation donne à votre assistant l'ensemble complet d'outils : accès en direct à la documentation webforJ, création de projet, génération de thème, validation de jetons de design, et workflows structurés qui lui enseignent comment utiliser tout cela correctement.

## Ce que vous obtenez {#what-you-get}

L'installation du plugin connecte deux éléments complémentaires en une seule étape :

- **[serveur MCP webforJ](/docs/integrations/ai-tooling/mcp)** - outils en direct que l'assistant peut appeler à la demande : rechercher des informations dans la base de connaissances webforJ, créer des projets Maven, générer des thèmes DWC, lire la surface de style de n'importe quel composant DWC et valider les jetons `--dwc-*` avant qu'ils n'atterrissent dans votre CSS.
- **[Compétences de l'agent](/docs/integrations/ai-tooling/agent-skills)** - workflows structurés qui indiquent à l'assistant _quand_ utiliser ces outils, dans quel ordre effectuer les actions et comment valider le résultat. Couvre la création de composants réutilisables et le stylage des applications webforJ de bout en bout.

Ensemble, ils transforment un assistant AI qui devine les conventions webforJ en un qui les suit.

:::warning L'IA peut encore faire des erreurs
Même avec le plugin, les assistants AI peuvent produire du code incorrect dans des scénarios complexes. Vérifiez toujours et testez le code généré avant de le déployer.
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

Ensuite, ouvrez une session Codex, exécutez `/plugins`, sélectionnez `webforj`, et appuyez sur **Espace** pour l'activer.

Codex ne charge pas automatiquement les compétences par correspondance de prompt comme les autres clients. Invoquez-les explicitement :
Codex ne charge pas automatiquement les compétences par correspondance de prompt comme les autres clients. Invoquez-les explicitement :

```
$webforj:webforj-styling-apps theme this app with a blue palette
$webforj:webforj-creating-components wrap this Custom Element as a webforJ component
```

Les outils MCP fonctionnent automatiquement sans le préfixe `$`.

</TabItem>
</Tabs>

### Autres clients {#other-clients}

Cursor, Kiro, Goose, Junie, Antigravity, et tout autre client compatible avec les compétences d'Agent prennent également en charge le plugin - ils utilisent simplement une configuration manuelle plutôt qu'une commande de marché. Consultez le [guide d'installation par client](https://github.com/webforj/webforj-ai#clients) pour les étapes exactes.

## Utilisation {#using-it}

Une fois installé, la plupart des assistants chargent automatiquement le bon élément en fonction de votre prompt :

- *"Enveloppez cette bibliothèque Custom Element en tant que composant webforJ."* - déclenche la compétence de création de composants
- *"Stylisez cette vue avec les jetons de design DWC."* - déclenche la compétence de stylisation d'applications
- *"Créez un nouveau projet sidemenu webforJ appelé CustomerPortal."* - appelle le générateur de projet MCP
- *"Générez un thème à partir de la couleur de marque `#6366f1`."* - appelle le générateur de thème MCP
- *"Trouvez la documentation webforJ sur `@Route` et le routage."* - appelle la recherche de connaissances MCP

Pour de meilleurs résultats, mentionnez toujours **webforJ** dans vos prompts - c'est le signal que l'assistant utilise pour se tourner vers le plugin plutôt que vers la connaissance générale de Java.

## Mise à jour et désinstallation {#updating-and-uninstalling}

Chaque client pris en charge a ses propres commandes de mise à jour et de désinstallation. Consultez le [README webforj-ai](https://github.com/webforj/webforj-ai#clients) pour des instructions par client.
