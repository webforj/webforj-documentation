---
title: Configure an MCP App
sidebar_position: 30
description: >-
  Configure the public app origin, allowed embedding clients, and external
  browser domains.
_i18n_hash: 6d6d861d57b9a398007bd9a792e9ec1f
---
Ajoutez les paramètres de l'application MCP à `application.properties`, ou à `webforj.conf` lorsque vous utilisez la configuration standard de webforJ. Définissez l'adresse où le client peut accéder à l'application, puis ajoutez uniquement les origines de client et de navigateur nécessaires au déploiement.

## Définir l'origine de l'application {#app-origin}

`webforj.origin` est l'origine publique utilisée dans la ressource de l'application, la politique de sécurité de contenu et les URL des composants webforJ. Lors des tests locaux, c'est l'adresse de l'application :

```Ini
webforj.origin=http://localhost:8080
```

Lorsqu'un tunnel ou un proxy inverse expose l'application, utilisez l'origine publique à laquelle le client MCP peut accéder :

```Ini
webforj.origin=https://example.trycloudflare.com
```

N'incluez pas `/mcp` dans cette propriété. Le chemin appartient au point de terminaison MCP, pas à l'origine de l'application.

## Autoriser le client d'incorporation {#allowed-origins}

`webforj.mcp.allowed-origins` contrôle quelles origines de navigateur peuvent effectuer des requêtes inter-origines et intégrer la vue. Pour un navigateur [MCPJam](./testing#mcpjam) local fonctionnant à l'origine représentative `http://127.0.0.1:6274`, utilisez :

```Ini
webforj.mcp.allowed-origins=http://127.0.0.1:6274
```

Utilisez l'origine affichée dans la barre d'adresse du navigateur du client, car les outils locaux peuvent choisir un port différent. L'adresse du tunnel n'est pas une origine de client autorisée ; elle appartient à `webforj.origin`.

webforJ autorise déjà les modèles d'origine des applications Codex connues et du bac à sable Claude Desktop. Ajoutez cette propriété uniquement pour une autre origine de client. Un joker tel que `https://*.example.com` correspond aux étiquettes d'hôte, pas au texte URL arbitraire.

## Autoriser les ressources et connexions externes {#browser-domains}

Le cadre intégré commence avec une politique de sécurité de contenu restrictive. Ajoutez `resource-domains` lorsque l'interface utilisateur doit charger un script, un style, une image, une police ou une autre ressource de navigateur depuis une autre origine :

```Ini
webforj.mcp.resource-domains=https://cdn.example.com
```

Ajoutez `connect-domains` lorsque le code du navigateur dans le cadre doit se connecter à une API externe, un WebSocket ou un point de terminaison similaire :

```Ini
webforj.mcp.connect-domains=https://api.example.com
```

Ces propriétés étendent ce que le cadre intégré peut charger ou contacter. Elles ne permettent pas à un autre client d'incorporer l'application ; utilisez `allowed-origins` pour cela.

## Configurer un déploiement standard {#standard-deployment}

Spring Boot lit ces valeurs à partir de `application.properties`. Un déploiement de servlet standard utilise `webforj.conf` avec les valeurs équivalentes :

```Ini
webforj.origin = "https://app.example.com"
webforj.mcp.allowed-origins = ["https://assistant.example.com"]
webforj.mcp.resource-domains = ["https://cdn.example.com"]
webforj.mcp.connect-domains = ["https://api.example.com"]
```

Ajoutez uniquement les domaines nécessaires à l'application. [Les tests clients](./testing) montrent où trouver l'origine du client local et quand une origine d'application publique est requise.
