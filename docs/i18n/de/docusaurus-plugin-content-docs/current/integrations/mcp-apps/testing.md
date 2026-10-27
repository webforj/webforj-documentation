---
title: Eine MCP-App testen
sidebar_position: 10
description: >-
  Test a webforJ MCP App with an MCP Apps-capable host, including the Codex app,
  Claude Desktop, and MCPJam.
_i18n_hash: fb9683202651a3aca86843cf27c0626e
---
webforJ MCP Apps können auf jedem MCP Apps-fähigen Host ausgeführt werden. Die hier beschriebenen Anweisungen betreffen die Codex-App und Claude Desktop über einen erreichbaren HTTPS-Endpunkt sowie MCPJam über localhost. Das minimalistische, eingabefreie `inventory`-Tool aus dem [Spring Boot-Setup](./spring) reicht aus, um zu bestätigen, dass ein Host das Tool entdecken und die Inventory-Ansicht rendern kann.

## Remote-Clients {#remote-clients}

Die Codex-App und Claude Desktop verbinden sich von außerhalb der Entwicklungsmaschine. Sie können `http://localhost:8080/mcp` nicht erreichen, daher benötigt die laufende App eine öffentliche HTTPS MCP-URL.

### Eine lokale App freigeben {#expose-a-local-app}

Verwenden Sie einen [Cloudflare Tunnel](https://developers.cloudflare.com/tunnel/setup/), um einen öffentlichen HTTPS-Ursprung zu reservieren und auszudrucken, der zur App am Standard-Local-Port `8080` weiterleitet. Sie können den Tunnel vor der App starten:

```bash
cloudflared tunnel --url http://localhost:8080
```

Der Befehl druckt einen HTTPS-Ursprung aus, wie z.B. `https://example.trycloudflare.com`. Setzen Sie diesen ausgedruckten Ursprung in `src/main/resources/application.properties`:

```Ini
webforj.origin=https://example.trycloudflare.com
```

Starten Sie die App über ihren normalen Workflow. Der Ursprung hat kein `/mcp`; die Client-URL fügt `/mcp` hinzu:

```text
https://example.trycloudflare.com/mcp
```

:::warning[Entwicklungstunnel]

Ein Entwicklungstunnel macht die App öffentlich erreichbar. Verwenden Sie Testdaten, erwarten Sie einen neuen Hostnamen jedes Mal, wenn der schnelle Tunnel gestartet wird, und verwenden Sie einen stabilen verwalteten Tunnel, wenn der Hostname gleich bleiben muss.
:::

### Codex-App {#codex-app}

<!-- Video: Verbinden und testen Sie die Inventory MCP-App in der Codex-App. -->

Der [Plugins-Leitfaden](https://developers.openai.com/codex/plugins) von OpenAI behandelt die aktuellen Plugin-Steuerelemente.

1. Öffnen Sie in **Einstellungen** **Plugins** und wählen Sie **MCP-Server hinzufügen**.
2. Geben Sie die öffentliche MCP-URL ein:

```text
https://example.trycloudflare.com/mcp
```

3. Fügen Sie den Server hinzu und starten Sie ein neues Codex-Gespräch.
4. Fordern Sie die Codex-App auf:

```text
Öffnen Sie die Inventory-App.
```

5. Bestätigen Sie, dass die gerenderte Inventory-Ansicht erscheint.

<!-- vale Google.Headings = NO -->
### Claude Desktop {#claude-desktop}

<!-- Video: Verbinden und testen Sie die Inventory MCP-App in Claude Desktop. -->
<!-- vale Google.Headings = YES -->

Der Remote-Custom-Connector von Claude Desktop wird über die Infrastruktur von Anthropic verwaltet, daher benötigt er ebenfalls die öffentliche HTTPS MCP-URL. Der [Connector-Leitfaden von Anthropic](https://support.claude.com/en/articles/11176164-use-connectors-to-extend-claude-s-capabilities) behandelt die aktuellen Connector-Steuerelemente.

1. Öffnen Sie **Einstellungen**, wählen Sie **Connectoren** und klicken Sie auf die Hinzufügen-Schaltfläche.
2. Wählen Sie **Custom Connector hinzufügen**, geben Sie einen Namen ein und verwenden Sie die öffentliche MCP-URL:

```text
https://example.trycloudflare.com/mcp
```

3. Fügen Sie den Connector hinzu.
4. In einem Gespräch fordern Sie Claude Desktop auf:

```text
Öffnen Sie die Inventory-App.
```

6. Bestätigen Sie, dass die gerenderte Inventory-Ansicht erscheint.

Wenn der Server OAuth 2.0 erfordert, schließen Sie den Anmeldefluss ab, bevor Sie das Tool aufrufen.

:::tip[Benennen Sie den MCP-Server in der Aufforderung]

Wenn Codex oder Claude nicht die erwartete Aktion auswählt, fügen Sie den Namen des MCP-Servers in die Aufforderung ein. Dies kann passieren, wenn mehrere Tools anwendbar sind oder die Aufforderung zu vage ist. Zum Beispiel: `Verwenden Sie den Inventory MCP-Server, um die Inventory-App zu öffnen.`
:::

## MCPJam {#mcpjam}

[MCPJam](https://github.com/MCPJam/inspector) kann direkt mit einem auf demselben Computer laufenden MCP-Server verbunden werden. Verwenden Sie den lokalen Inspector für einen einfachen HTTP-Endpunkt; die gehostete MCPJam-App akzeptiert nur HTTPS-Endpunkte.

1. Starten Sie den lokalen Inspector und öffnen Sie die localhost-URL, die er ausgibt:

```bash
npx @mcpjam/inspector@latest
```

2. Konfigurieren Sie vor dem Start der webforJ-App deren lokalen Ursprung und erlauben Sie den MCPJam-Browser-Ursprung. Ersetzen Sie den repräsentativen MCPJam-Ursprung unten, falls der Inspector einen anderen ausgedruckt hat:

```Ini
webforj.origin=http://localhost:8080
webforj.mcp.allowed-origins=http://127.0.0.1:6274
```

`webforj.origin` legt den Standort fest, von dem die gerenderte MCP-App ihre webforJ-Ressourcen lädt. `webforj.mcp.allowed-origins` erlaubt der MCPJam-Seite, sich mit der App einzubetten und zu kommunizieren.

3. Starten Sie die webforJ-App über ihren normalen Workflow.

4. Öffnen Sie in MCPJam **Verbinden** und wählen Sie **Server hinzufügen**. Geben Sie einen Namen ein, wählen Sie **HTTP** als Transport und verwenden Sie den lokalen MCP-Endpunkt:

```text
http://localhost:8080/mcp
```

5. Wählen Sie **Keine Authentifizierung**, und verbinden Sie dann den Server. Eine erfolgreiche Verbindung macht die Werkzeuge des Servers für MCPJam verfügbar.
6. Öffnen Sie **Playground** und dann **Tools** im linken Bereich.
7. Wählen Sie `inventory` und klicken Sie auf **Run**. Das Tool benötigt keine Eingabe, und seine Inventory-Ansicht wird im Gespräch gerendert.

:::warning[MCPJam-Inhaltssicherheitsrichtlinie-Modus]

Stellen Sie den **Content Security Policy (CSP) Modus** in der Playground-Symbolleiste auf **Permissive**, bevor Sie das Tool ausführen. Der Strenge Modus blockiert die dynamische JavaScript-Auswertung, die beim aktuellen Start von webforJ verwendet wird. Verwenden Sie den permissiven Modus nur mit MCP-Servern und App-Code, dem Sie vertrauen.
:::

## Anwendung überprüfen {#verify-the-app}

Verwenden Sie diese Basislinie für jeden Client:

- Der Client verbindet sich mit dem MCP-Endpunkt.
- Das `inventory`-Tool ist sichtbar.
- Das Aufrufen von `inventory` rendert die **Inventory**-Überschrift.
- Die gerenderte Benutzeroberfläche ist interaktiv.

Nachdem die Basislinie funktioniert, fügen Sie [öffnende Eingaben](./opening-apps), [Aktionen und Updates](./actions-updates) und [Host-Interaktionen](./host-interaction) hinzu, wenn die MCP-App diese Funktionen benötigt.

## Fehlersuche {#troubleshooting}

| Problem | Überprüfen |
| --- | --- |
| Client kann sich nicht verbinden | Bestätigen Sie, dass die App läuft, der Tunnel für Remote-Clients läuft und die vollständige Client-URL mit `/mcp` endet. |
| Tool sichtbar, aber Ressource oder Öffnen schlägt fehl | Bestätigen Sie, dass `webforj.origin` mit dem aktuellen App-Ursprung übereinstimmt und dass die App läuft. |
| MCPJam ist leer oder lädt mit einem Fehler bei der Inhalts-Sicherheitsrichtlinie `eval` | Deaktivieren Sie **Streng**. |
| Metadaten sind veraltet | Trennen Sie den Client oder starten Sie ein neues Gespräch. |
