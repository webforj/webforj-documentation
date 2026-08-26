---
title: Tester une app MCP
sidebar_position: 10
description: >-
  Test a webforJ MCP App with an MCP Apps-capable host, including the Codex app,
  Claude Desktop, and MCPJam.
_i18n_hash: fb9683202651a3aca86843cf27c0626e
---
Les applications MCP de webforJ peuvent fonctionner sur n'importe quel hôte compatible avec les applications MCP. Les instructions ici couvrent l'application Codex et Claude Desktop via un point de terminaison HTTPS accessible, et MCPJam via localhost. L'outil minimal sans saisie `inventory` du [Spring Boot setup](./spring) est suffisant pour confirmer qu'un hôte peut découvrir l'outil et rendre la vue Inventory.

## Clients distants {#remote-clients}

L'application Codex et Claude Desktop se connectent depuis l'extérieur de la machine de développement. Elles ne peuvent pas atteindre `http://localhost:8080/mcp`, donc l'application en cours d'exécution nécessite une URL publique HTTPS MCP.

### Exposer une application locale {#expose-a-local-app}

Utilisez un [Tunnel Cloudflare](https://developers.cloudflare.com/tunnel/setup/) pour réserver et imprimer une origine HTTPS publique qui redirige vers l'application sur le port local par défaut, `8080`. Vous pouvez démarrer le tunnel avant de lancer l'application :

```bash
cloudflared tunnel --url http://localhost:8080
```

La commande imprime une origine HTTPS, telle que `https://example.trycloudflare.com`. Configurez cette origine imprimée dans `src/main/resources/application.properties` :

```Ini
webforj.origin=https://example.trycloudflare.com
```

Démarrez l'application selon son workflow normal. L'origine n'a pas de `/mcp` ; l'URL du client ajoute `/mcp` :

```text
https://example.trycloudflare.com/mcp
```

:::warning[Tunnel de développement]

Un tunnel de développement rend l'application publiquement accessible. Utilisez des données de test, attendez-vous à un nouveau nom d'hôte chaque fois que le tunnel rapide est démarré, et utilisez un tunnel géré stable lorsque le nom d'hôte doit rester le même.
:::

### Application Codex {#codex-app}

<!-- Vidéo : Connectez et testez l'application MCP Inventory dans l'application Codex. -->

Le [guide des plugins d'OpenAI](https://developers.openai.com/codex/plugins) couvre les contrôles de plugin actuels.

1. Dans **Paramètres**, ouvrez **Plugins** et sélectionnez **Ajouter un serveur MCP**.
2. Entrez l'URL publique MCP :

```text
https://example.trycloudflare.com/mcp
```

3. Ajoutez le serveur, puis démarrez une nouvelle conversation Codex.
4. Invitez l'application Codex :

```text
Ouvrez l'application d'inventaire.
```

5. Confirmez que la vue Inventory rendue apparaît.

<!-- vale Google.Headings = NO -->
### Claude Desktop {#claude-desktop}

<!-- Vidéo : Connectez et testez l'application MCP Inventory dans Claude Desktop. -->
<!-- vale Google.Headings = YES -->

Le connecteur personnalisé distant de Claude Desktop est médié par l'infrastructure d'Anthropic, donc il a également besoin de l'URL publique HTTPS MCP. Le [guide des connecteurs d'Anthropic](https://support.claude.com/en/articles/11176164-use-connectors-to-extend-claude-s-capabilities) couvre les contrôles de connecteurs actuels.

1. Ouvrez **Paramètres**, sélectionnez **Connecteurs**, et cliquez sur le bouton d'ajout.
2. Sélectionnez **Ajouter un connecteur personnalisé**, entrez un nom, et utilisez l'URL publique MCP :

```text
https://example.trycloudflare.com/mcp
```

3. Ajoutez le connecteur.
4. Dans une conversation, Invitez Claude Desktop :

```text
Ouvrez l'application d'inventaire.
```

6. Confirmez que la vue Inventory rendue apparaît.

Si le serveur nécessite OAuth 2.0, terminez le flux d'authentification avant d'invoquer l'outil.

:::tip[Nommez le serveur MCP dans l'invite]

Si Codex ou Claude ne choisit pas l'action attendue, incluez le nom du serveur MCP dans l'invite. Cela peut arriver lorsque plusieurs outils pourraient s'appliquer ou que l'invite est trop vague. Par exemple : `En utilisant le serveur MCP d'inventaire, ouvrez l'application d'inventaire.`
:::

## MCPJam {#mcpjam}

[MCPJam](https://github.com/MCPJam/inspector) peut se connecter directement à un serveur MCP fonctionnant sur la même machine. Utilisez l'inspecteur local pour un point de terminaison HTTP classique ; l'application MCPJam hébergée accepte uniquement des points de terminaison HTTPS.

1. Démarrez l'inspecteur local et ouvrez l'URL localhost qu'il imprime :

```bash
npx @mcpjam/inspector@latest
```

2. Avant de démarrer l'application webforJ, configurez son origine locale et autorisez l'origine du navigateur MCPJam. Remplacez l'origine MCPJam représentative ci-dessous si l'inspecteur a imprimé une différente :

```Ini
webforj.origin=http://localhost:8080
webforj.mcp.allowed-origins=http://127.0.0.1:6274
```

`webforj.origin` définit l'emplacement à partir duquel l'application MCP rendue charge ses ressources webforJ. `webforj.mcp.allowed-origins` permet à la page MCPJam d'incorporer et de communiquer avec l'application.

3. Démarrez l'application webforJ selon son workflow normal.

4. Dans MCPJam, ouvrez **Connecter** et sélectionnez **Ajouter un serveur**. Entrez un nom, sélectionnez **HTTP** comme transport, et utilisez le point de terminaison MCP local :

```text
http://localhost:8080/mcp
```

5. Sélectionnez **Pas d'authentification**, puis connectez le serveur. Une connexion réussie rend les outils du serveur disponibles pour MCPJam.
6. Ouvrez **Playground**, puis ouvrez **Outils** dans le rail gauche.
7. Sélectionnez `inventory` et cliquez sur **Exécuter**. L'outil ne prend pas d'entrée, et sa vue Inventory se rend dans la conversation.

:::warning[Politique de sécurité de contenu de MCPJam]

Définissez le **Mode de Politique de Sécurité de Contenu (CSP)** dans la barre d'outils Playground sur **Permissif** avant d'exécuter l'outil. Le mode strict bloque l'évaluation dynamique de JavaScript utilisée lors du démarrage actuel de webforJ. Utilisez le mode Permissif uniquement avec des serveurs MCP et du code d'application de confiance.
:::

## Vérifier l'application {#verify-the-app}

Utilisez cette base pour chaque client :

- Le client se connecte au point de terminaison MCP.
- L'outil `inventory` est visible.
- L'invocation de `inventory` rend le titre **Inventory**.
- L'interface utilisateur rendue est interactive.

Une fois que la base fonctionne, ajoutez [l'ouverture d'entrée](./opening-apps), [les actions et mises à jour](./actions-updates), et [l'interaction avec l'hôte](./host-interaction) lorsque l'application MCP nécessite ces fonctionnalités.

## Dépannage {#troubleshooting}

| Problème | Vérifiez |
| --- | --- |
| Le client ne peut pas se connecter | Confirmez que l'application fonctionne, que le tunnel est en cours d'exécution pour les clients distants, et que l'URL complète du client se termine par `/mcp`. |
| Outil visible mais la ressource ou l'ouverture échoue | Confirmez que `webforj.origin` correspond à l'origine actuelle de l'application et que l'application fonctionne. |
| MCPJam est vide ou se charge avec une erreur de politique de sécurité de contenu `eval` | Désactivez **Strict**. |
| Les métadonnées sont obsolètes | Reconnectez le client ou commencez une nouvelle conversation. |
