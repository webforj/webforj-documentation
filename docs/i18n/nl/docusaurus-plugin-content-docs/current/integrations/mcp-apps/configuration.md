---
title: Een MCP-app configureren
sidebar_position: 30
description: >-
  Configure the public app origin, allowed embedding clients, and external
  browser domains.
_i18n_hash: 6d6d861d57b9a398007bd9a792e9ec1f
---
Voeg MCP App-instellingen toe aan `application.properties`, of aan `webforj.conf` bij gebruik van de standaard webforJ-opzet. Stel het adres in waar de client de app kan bereiken, en voeg alleen de client- en browserorigine toe die de implementatie vereist.

## Stel de app-origin in {#app-origin}

`webforj.origin` is de publieke oorsprong die wordt gebruikt in de app-resource, contentbeveiligingsbeleid en webforJ-component-URLs. Tijdens lokaal testen is het het adres van de app:

```Ini
webforj.origin=http://localhost:8080
```

Wanneer een tunnel of reverse proxy de app blootstelt, gebruik dan de publieke oorsprong die de MCP-client kan bereiken:

```Ini
webforj.origin=https://example.trycloudflare.com
```

Sluit `/mcp` niet in deze eigenschap op. Het pad behoort tot de MCP-eindpunt, niet de app-origin.

## Sta de insluitende client toe {#allowed-origins}

`webforj.mcp.allowed-origins` bepaalt welke browser-oorsprongen cross-origin verzoeken kunnen doen en de weergave kunnen insluiten. Voor een lokale [MCPJam](./testing#mcpjam) browser die draait op de representatieve oorsprong `http://127.0.0.1:6274`, gebruik:

```Ini
webforj.mcp.allowed-origins=http://127.0.0.1:6274
```

Gebruik de oorsprong die in de adresbalk van de browser van de client wordt weergegeven, omdat lokale tools een andere poort kunnen kiezen. Het tunneladres is geen toegestane client-origin; dit behoort in `webforj.origin`.

webforJ staat al de bekende Codex app en Claude Desktop sandbox oorsprongspatronen toe. Voeg deze eigenschap alleen toe voor een andere client-origin. Een wildcard zoals `https://*.example.com` matcht hostlabels, niet willekeurige URL-tekst.

## Sta externe bronnen en verbindingen toe {#browser-domains}

Het ingebedde frame begint met een restrictief contentbeveiligingsbeleid. Voeg `resource-domains` toe wanneer de UI een script, stijl, afbeelding, lettertype of andere browserbron van een andere oorsprong moet laden:

```Ini
webforj.mcp.resource-domains=https://cdn.example.com
```

Voeg `connect-domains` toe wanneer browsercode in het frame verbinding moet maken met een externe API, WebSocket of vergelijkbaar eindpunt:

```Ini
webforj.mcp.connect-domains=https://api.example.com
```

Deze eigenschappen breiden uit wat het ingebedde frame kan laden of contact kan maken. Ze staan een andere client niet toe om de app in te sluiten; gebruik daarvoor `allowed-origins`.

## Configureer een standaard implementatie {#standard-deployment}

Spring Boot leest deze waarden uit `application.properties`. Een standaard servlet-implementatie gebruikt `webforj.conf` met de equivalente waarden:

```Ini
webforj.origin = "https://app.example.com"
webforj.mcp.allowed-origins = ["https://assistant.example.com"]
webforj.mcp.resource-domains = ["https://cdn.example.com"]
webforj.mcp.connect-domains = ["https://api.example.com"]
```

Voeg alleen domeinen toe die de app nodig heeft. [Clienttesting](./testing) toont waar je de lokale client-oorsprong kunt vinden en wanneer een publieke app-origin vereist is.
