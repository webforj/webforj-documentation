---
title: Eine MCP-App konfigurieren
sidebar_position: 30
description: >-
  Configure the public app origin, allowed embedding clients, and external
  browser domains.
_i18n_hash: 6d6d861d57b9a398007bd9a792e9ec1f
---
Fügen Sie MCP-App-Einstellungen zu `application.properties` oder zu `webforj.conf` hinzu, wenn Sie die standardmäßige webforJ-Einrichtung verwenden. Setzen Sie die Adresse, unter der der Client die App erreichen kann, und fügen Sie dann nur die Client- und Browser-Ursprünge hinzu, die für die Bereitstellung erforderlich sind.

## Setzen Sie den App-Ursprung {#app-origin}

`webforj.origin` ist der öffentliche Ursprung, der in der App-Ressource, der Inhalts-Sicherheitsrichtlinie und den webforJ-Komponenten-URLs verwendet wird. Während des lokalen Testens ist es die Adresse der App:

```Ini
webforj.origin=http://localhost:8080
```

Wenn ein Tunnel oder ein Reverse-Proxy die App exponiert, verwenden Sie den öffentlichen Ursprung, den der MCP-Client erreichen kann:

```Ini
webforj.origin=https://example.trycloudflare.com
```

Schließen Sie `/mcp` in dieser Eigenschaft nicht ein. Der Pfad gehört zum MCP-Endpunkt, nicht zum App-Ursprung.

## Erlauben Sie den einbettenden Client {#allowed-origins}

`webforj.mcp.allowed-origins` steuert, welche Browser-Ursprünge Cross-Origin-Anfragen durchführen und die Ansicht einbetten können. Für einen lokalen [MCPJam](./testing#mcpjam)-Browser, der unter dem repräsentativen Ursprung `http://127.0.0.1:6274` läuft, verwenden Sie:

```Ini
webforj.mcp.allowed-origins=http://127.0.0.1:6274
```

Verwenden Sie den Ursprung, der in der Adressleiste des Clients angezeigt wird, da lokale Tools einen anderen Port auswählen können. Die Tunneladresse ist kein erlaubter Client-Ursprung; sie gehört in `webforj.origin`.

webforJ erlaubt bereits die bekannten Ursprungsmuster der Codex-App und der Claude Desktop-Sandbox. Fügen Sie diese Eigenschaft nur für einen anderen Client-Ursprung hinzu. Ein Platzhalter wie `https://*.example.com` entspricht Hostbezeichnungen, nicht willkürlichem URL-Text.

## Erlauben Sie externe Ressourcen und Verbindungen {#browser-domains}

Der eingebettete Frame beginnt mit einer restriktiven Inhalts-Sicherheitsrichtlinie. Fügen Sie `resource-domains` hinzu, wenn die Benutzeroberfläche ein Skript, eine Stilvorlage, ein Bild, eine Schrift oder eine andere Browserressource von einem anderen Ursprung laden muss:

```Ini
webforj.mcp.resource-domains=https://cdn.example.com
```

Fügen Sie `connect-domains` hinzu, wenn der Browsercode im Frame eine Verbindung zu einer externen API, WebSocket oder ähnlichem Endpunkt herstellen muss:

```Ini
webforj.mcp.connect-domains=https://api.example.com
```

Diese Eigenschaften erweitern, was der eingebettete Frame laden oder kontaktieren kann. Sie erlauben es nicht, dass ein anderer Client die App einbettet; verwenden Sie dafür `allowed-origins`.

## Konfigurieren Sie eine Standardbereitstellung {#standard-deployment}

Spring Boot liest diese Werte aus `application.properties`. Eine standardmäßige Servlet-Bereitstellung verwendet `webforj.conf` mit den äquivalenten Werten:

```Ini
webforj.origin = "https://app.example.com"
webforj.mcp.allowed-origins = ["https://assistant.example.com"]
webforj.mcp.resource-domains = ["https://cdn.example.com"]
webforj.mcp.connect-domains = ["https://api.example.com"]
```

Fügen Sie nur die Domänen hinzu, die die App benötigt. [Client-Tests](./testing) zeigen, wo der lokale Client-Ursprung zu finden ist und wann ein öffentlicher App-Ursprung erforderlich ist.
