---
title: Een MCP-app testen
sidebar_position: 10
description: >-
  Test a webforJ MCP App with an MCP Apps-capable host, including the Codex app,
  Claude Desktop, and MCPJam.
_i18n_hash: fb9683202651a3aca86843cf27c0626e
---
webforJ MCP-apps kunnen draaien op elke MCP-apps-geschikte host. De instructies hier dekken de Codex-app en Claude Desktop via een bereikbare HTTPS-eindpunt, en MCPJam via localhost. De minimale no-input `inventory` tool uit de [Spring Boot-setup](./spring) is genoeg om te bevestigen dat een host de tool kan ontdekken en de Inventory-weergave kan renderen.

## Remote clients {#remote-clients}

De Codex-app en Claude Desktop verbinden van buiten de ontwikkelingsmachine. Ze kunnen `http://localhost:8080/mcp` niet bereiken, dus de draaiende app heeft een openbaar HTTPS MCP-URL nodig.

### Expose a local app {#expose-a-local-app}

Gebruik een [Cloudflare Tunnel](https://developers.cloudflare.com/tunnel/setup/) om een openbaar HTTPS-oorsprong te reserveren en af te drukken die doorstuurt naar de app op de standaard lokale poort, `8080`. Je kunt de tunnel starten voordat je de app start:

```bash
cloudflared tunnel --url http://localhost:8080
```

De opdracht drukt een HTTPS-oorsprong af, zoals `https://example.trycloudflare.com`. Stel die afgedrukte oorsprong in `src/main/resources/application.properties` in:

```Ini
webforj.origin=https://example.trycloudflare.com
```

Start de app via zijn normale workflow. De oorsprong heeft geen `/mcp`; de client-URL voegt `/mcp` toe:

```text
https://example.trycloudflare.com/mcp
```

:::warning[Development tunnel]

Een ontwikkelingstunnel maakt de app openbaar bereikbaar. Gebruik testgegevens, verwacht een nieuwe hostnaam elke keer wanneer de snelle tunnel wordt gestart, en gebruik een stabiele beheerde tunnel wanneer de hostnaam hetzelfde moet blijven.
:::

### Codex app {#codex-app}

<!-- Video: Connect and test the inventory MCP App in the Codex app. -->

OpenAI's [Plugins-gids](https://developers.openai.com/codex/plugins) behandelt de huidige plugincontroles.

1. Ga naar **Instellingen**, open **Plugins** en selecteer **MCP-server toevoegen**.
2. Voer de openbare MCP-URL in:

```text
https://example.trycloudflare.com/mcp
```

3. Voeg de server toe en start vervolgens een nieuw Codex-gesprek.
4. Vraag de Codex-app:

```text
Open de inventory-app.
```

5. Bevestig dat de gerenderde Inventory-weergave verschijnt.

<!-- vale Google.Headings = NO -->
### Claude Desktop {#claude-desktop}

<!-- Video: Connect and test the inventory MCP App in Claude Desktop. -->
<!-- vale Google.Headings = YES -->

Claude Desktop's externe aangepaste connector wordt bemiddeld via de infrastructuur van Anthropic, dus deze heeft ook de openbare HTTPS MCP-URL nodig. De [connectoren-gids](https://support.claude.com/en/articles/11176164-use-connectors-to-extend-claude-s-capabilities) van Anthropic behandelt de huidige connectorcontroles.

1. Open **Instellingen**, selecteer **Connectors** en klik op de knop om toe te voegen.
2. Selecteer **Voeg aangepaste connector toe**, voer een naam in en gebruik de openbare MCP-URL:

```text
https://example.trycloudflare.com/mcp
```

3. Voeg de connector toe.
4. In een gesprek, vraag Claude Desktop:

```text
Open de inventory-app.
```

6. Bevestig dat de gerenderde Inventory-weergave verschijnt.

Als de server OAuth 2.0 vereist, voltooi dan de aanmeldflow voordat je de tool aanroept.

:::tip[Noem de MCP-server in de prompt]

Als Codex of Claude de verwachte actie niet kiest, voeg dan de naam van de MCP-server toe in de prompt. Dit kan gebeuren wanneer verschillende tools van toepassing kunnen zijn of de prompt te vaag is. Bijvoorbeeld: `Gebruik de inventory MCP-server, open de inventory-app.`
:::

## MCPJam {#mcpjam}

[MCPJam](https://github.com/MCPJam/inspector) kan rechtstreeks verbinding maken met een MCP-server die op dezelfde machine draait. Gebruik de lokale inspector voor een eenvoudige HTTP-eindpunt; de gehoste MCPJam-app accepteert alleen HTTPS-eindpunten.

1. Start de lokale inspector en open de localhost-URL die deze afdrukt:

```bash
npx @mcpjam/inspector@latest
```

2. Voordat je de webforJ-app start, configureer je de lokale oorsprong en sta je de MCPJam-browser oorsprong toe. Vervang de representatieve MCPJam-oorsprong hieronder als de inspector een andere heeft afgedrukt:

```Ini
webforj.origin=http://localhost:8080
webforj.mcp.allowed-origins=http://127.0.0.1:6274
```

`webforj.origin` stelt de locatie in waarvan de gerenderde MCP-app zijn webforJ-bronnen laadt. `webforj.mcp.allowed-origins` staat de MCPJam-pagina toe om de app in te sluiten en te communiceren.

3. Start de webforJ-app via zijn normale workflow.

4. In MCPJam, open **Verbinden** en selecteer **Voeg server toe**. Voer een naam in, selecteer **HTTP** als transport, en gebruik het lokale MCP-eindpunt:

```text
http://localhost:8080/mcp
```

5. Selecteer **Geen authenticatie**, en verbind vervolgens de server. Een succesvolle verbinding maakt de tools van de server beschikbaar voor MCPJam.
6. Open **Playground**, en open vervolgens **Tools** in de linker zijbalk.
7. Selecteer `inventory` en klik op **Uitvoeren**. De tool vereist geen invoer, en de Inventory-weergave wordt gerenderd in het gesprek.

:::warning[MCPJam content security policy mode]

Stel de **Content Security Policy (CSP) Mode** in de Playground-toolbar in op **Toegestaan** voordat je de tool uitvoert. Strikte modus blokkeert de dynamische JavaScript-evaluatie die wordt gebruikt tijdens de huidige opstart van webforJ. Gebruik de Permissieve modus alleen met MCP-servers en app-code die je vertrouwt.
:::

## Verify the app {#verify-the-app}

Gebruik deze basislijn voor elke client:

- De client maakt verbinding met het MCP-eindpunt.
- De `inventory` tool is zichtbaar.
- Het aanroepen van `inventory` rendert de **Inventory** kop.
- De gerenderde UI is interactief.

Nadat de basislijn werkt, voeg dan [opening input](./opening-apps), [acties en updates](./actions-updates), en [hostinteractie](./host-interaction) toe wanneer de MCP-app deze functies nodig heeft.

## Troubleshooting {#troubleshooting}

| Probleem | Controleer |
| --- | --- |
| Client kan niet verbinden | Bevestig dat de app draait, de tunnel draait voor externe clients, en dat de volledige client-URL eindigt met `/mcp`. |
| Tool zichtbaar maar resource of open mislukt | Bevestig dat `webforj.origin` overeenkomt met de huidige app-oorsprong en dat de app draait. |
| MCPJam is leeg of laadt met een content security policy `eval` fout | Zet **Strikt** uit. |
| Metadata is verouderd | Maak opnieuw verbinding met de client of start een nieuw gesprek. |
