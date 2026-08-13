---
title: MCP Server
sidebar_position: 5
description: >-
  Connect AI assistants to the webforJ MCP server for live documentation lookup,
  project scaffolding, theme generation, and token validation.
_i18n_hash: e51aa2e6a5a0f6c37a18c404c1104684
---
Der webforJ Model Context Protocol (MCP) Server integriert KI-Coding-Assistenten in die Dokumentation, APIs, Design-Tokens und Scaffold-Tools von webforJ. Anstatt zu raten, was Konventionen im Framework sind, fragt der Assistent den Server und erhält Antworten, die auf dem echten webforJ basieren.

:::tip Verwende das Plugin
Es sei denn, du weißt, dass du nur den MCP-Server möchtest, installiere stattdessen das **[webforJ AI plugin](/docs/ai-tooling)** - es bündelt diesen Server mit den passenden [Agent Skills](/docs/ai-tooling/agent-skills) in einer einzigen Installation.
:::

## Was ist ein MCP? {#whats-an-mcp}

Das Model Context Protocol ist ein offener Standard, der es KI-Assistenten ermöglicht, externe Tools nach Bedarf anzusprechen. Der webforJ MCP-Server implementiert dieses Protokoll, damit dein Assistent:

- Informationen in den webforJ-Dokumenten nachschlagen kann, anstatt fiktive Methodennamen zu raten.
- Neue webforJ-Projekte aus offiziellen Maven-Archetypen scaffolden kann.
- Zugängliche DWC-Themen aus einer Markenfarbe generieren kann.
- Die tatsächliche Styling-Oberfläche einer DWC-Komponente lesen und jedes `--dwc-*` Token validieren kann, bevor es in deinem CSS landet.

:::warning KI kann immer noch Fehler machen
Der MCP-Server verbessert die Genauigkeit erheblich, aber KI-Assistenten können in komplexen Szenarien immer noch falschen Code erzeugen. Überprüfe und teste immer den generierten Code, bevor du ihn veröffentlichst.
:::

## Installation {#installation}

Für das volle Erlebnis installiere das **[webforJ AI plugin](/docs/ai-tooling)** - es konfiguriert diesen Server zusammen mit den Agent Skills, die dein Assistent benötigt, um ihn gut zu nutzen.

Wenn du nur den MCP-Server möchtest (ohne Skills), richte deinen Client auf `https://mcp.webforj.com/mcp`:

<Tabs groupId="ide">
<TabItem value="claude-code" label="Claude Code" default>

```bash
claude mcp add webforj-mcp https://mcp.webforj.com/mcp -t http -s user
```

</TabItem>
<TabItem value="copilot-cli" label="GitHub Copilot CLI">

Der empfohlene Weg beim Copilot CLI ist das **[webforJ AI plugin](/docs/ai-tooling)** - es registriert den MCP-Server für dich in einem Schritt. Für eine Roh-MCP-Setup siehe die spezifischen Anleitungen in dem [webforJ AI-Repository](https://github.com/webforj/webforj-ai#clients).

</TabItem>
<TabItem value="vscode" label="VS Code + Copilot">

Füge deinen VS Code-Einstellungen Folgendes hinzu:

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

Füge zu `~/.gemini/settings.json` hinzu:

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

Füge zu `~/.codex/config.toml` hinzu:

```toml
[mcp_servers.webforj-mcp]
url = "https://mcp.webforj.com/mcp"
```

</TabItem>
</Tabs>

### Andere Clients {#other-clients}

Cursor, Kiro, Goose, Junie, Antigravity und jeder andere MCP-over-HTTP-Client funktionieren ebenfalls - sie verwenden einfach ihr eigenes Konfigurationsformat. Siehe die [per-client Installationsanleitung](https://github.com/webforj/webforj-ai#clients) für den genauen Code-Snippet für jeden.

## Was der Server tun kann {#capabilities}

Wenn der MCP-Server verbunden ist, erhält dein KI-Assistent die folgenden Fähigkeiten. Jede von ihnen kann durch eine Anfrage in natürlicher Sprache ausgelöst werden - der Assistent wählt automatisch die richtige aus.

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>Die richtige webforJ-Version anvisieren</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Bevor er versionensensitive Fragen (alles, was Styling oder API betrifft) beantwortet, ermittelt der Assistent, welche webforJ-Version du verwendest. Er liest `pom.xml`, wenn verfügbar, und fragt dich andernfalls. Jede anschließende Antwort ist auf diese Version bezogen.
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>Informationen in der webforJ-Wissensdatenbank nachschlagen</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Der Assistent kann die gesamte webforJ-Wissensdatenbank abfragen, um Antworten basierend auf dem echten Framework zu erhalten. Die Ergebnisse sind auf das, worüber du fragst, abgestimmt - eine API-Frage, ein Leitfaden, ein Codebeispiel oder die Kotlin DSL.

      **Beispielanfragen:**
      ```
      "Finde die Beispiele für die Ereignisbehandlung der webforJ Button-Komponente"

      "Wie richte ich das Routing mit @Route in webforJ ein?"

      "Zeige mir ein Beispiel für die Formularvalidierung in webforJ"
      ```
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>Ein neues webforJ-Projekt scaffolden</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Der Assistent generiert den richtigen Maven-Archetypenbefehl für eine neue webforJ-App basierend auf deinen Anforderungen (Archetyp, Spring-Integration, Name, Gruppe).

      **Archetypen:**
      - `hello-world` - Starter-App mit Beispielkomponenten
      - `blank` - minimale Projektstruktur
      - `tabs` - Layout mit Registerkarten
      - `sidemenu` - Layout mit Seitennavigation

      **Varianten:**
      - `webforj` - Standard webforJ-App
      - `webforj-spring` - webforJ integriert mit Spring Boot

      **Beispielanfragen:**
      ```
      "Erstelle ein webforJ-Projekt mit dem Namen CustomerPortal unter Verwendung des sidemenu-Archetyps"

      "Generiere ein webforJ Spring Boot-Projekt mit dem Registerkartenlayout namens Dashboard"
      ```

      :::tip Verfügbare Archetypen
      Für die vollständige Liste der Archetypen siehe das [Archetypen-Katalog](/docs/building-ui/archetypes/overview).
      :::
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>Ein DWC-Theme generieren</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Aus einer einzigen Markenfarbe erstellt der Assistent ein komplettes DWC-Theme: primäre, erfolgreiche, Warn-, Gefahr-, Info-, Standard- und Graupaletten mit automatischem Textkontrast. Die Ausgabe umfasst das Stylesheet sowie die Verkabelung von `@AppTheme` / `@StyleSheet`.

      **Beispielanfragen:**
      ```
      "Generiere ein webforJ-Theme aus der Markenfarbe #6366f1"

      "Erstelle ein barrierefreies Theme mit HSL 220, 70, 50 als primär"
      ```
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>DWC-Komponenten korrekt stylen</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Der Assistent liest die tatsächliche Styling-Oberfläche jeder DWC-Komponente - CSS-Custom Properties, Schattenparts, reflektierte Attribute und Slots - bevor er CSS schreibt. Er kann auch jeden DWC-Tag aufzählen und die Java-Klassennamen von webforJ (`Button`, `TextField`) mit ihren DWC-Entsprechungen abgleichen.

      **Beispielanfragen:**
      ```
      "Welche CSS-Variablen und Teile bietet dwc-button?"

      "Zeige mir jeden verfügbaren Slot auf dwc-dialog"

      "Welchem DWC-Tag entspricht die webforJ-Klasse TextField?"
      ```

      Kombiniere dies mit der [styling-apps Agent Skill](/docs/ai-tooling/agent-skills) für durchgängige Styling-Arbeitsabläufe.
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>Mit DWC-Design-Tokens arbeiten</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Der Assistent kann das autoritative Katalog der `--dwc-*` Tokens für deine webforJ-Version auflisten - Palettensamen, Schattierungen, Oberflächen, Abstände, Typografie, Ränder - gefiltert nach Präfix oder Teilstring. Er wird auch jedes CSS-, Java- oder Markdown-Quellmaterial, das du ihm gibst, gegen den echten Token-Katalog validieren und unbekannte Namen mit vorgeschlagenen Korrekturen kennzeichnen.

      **Beispielanfragen:**
      ```
      "Liste jeden --dwc-space-* Token auf"

      "Validiere app.css auf unbekannte --dwc-* Tokens"

      "Welche Schattierungen der primären Palette sind verfügbar?"
      ```

      Die Validierung erkennt Tippfehler und erfundene Tokens, bevor sie als stillschweigend fehlerhaftes CSS veröffentlicht werden.
    </div>
  </AccordionDetails>
</Accordion>

## Gute Anfragen schreiben {#writing-good-prompts}

Der MCP-Server wird nur konsultiert, wenn dein Assistent denkt, dass es relevant ist. Einige Gewohnheiten halten ihn aktiv:

- **Nenne das Framework.** Erwähne "webforJ" in der Anfrage, damit der Assistent den MCP-Server erreicht, anstatt auf sein allgemeines Java-Wissen zurückzugreifen.
- **Sei spezifisch.** `"Erstelle ein webforJ-Projekt mit dem Namen InventorySystem unter Verwendung des sidemenu-Archetyps und Spring Boot"` ist besser als `"mache eine App"`.
- **Frage nach einer Bestätigung.** Phrasen wie `"verifiziere anhand der webforJ-Dokumentation"` oder `"prüfe dieses CSS auf fehlerhafte --dwc-* Tokens"` drängen den Assistenten dazu, die Tools zu verwenden, anstatt zu raten.

Wenn dein Assistent immer noch ohne Konsultation des Servers antwortet, installiere das [webforJ AI plugin](https://github.com/webforj/webforj-ai) - es enthält passende Agent Skills, die den Assistenten dazu bringen, die MCP-Tools automatisch für webforJ-Aufgaben zu verwenden.

## FAQ {#faq}

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>Warum verwendet der KI-Assistent den MCP-Server nicht?</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Die meisten Assistenten greifen nur dann auf MCP zurück, wenn sie denken, dass die Frage es benötigt. Zwei Lösungen:

      1. **Installiere das [webforJ AI plugin](https://github.com/webforj/webforj-ai)**, das den Server mit Agent Skills kombiniert, die den Assistenten anweisen, MCP für webforJ-Aufgaben zu verwenden.
      2. **Sei explizit in deiner Anfrage**: Füge "webforJ" in die Frage ein und sage in hartnäckigen Fällen "verwende den webforJ MCP-Server zur Antwort".
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>Wie kann ich überprüfen, ob die MCP-Verbindung funktioniert?</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Verwende den MCP-Inspektor:

      ```bash
      npx @modelcontextprotocol/inspector
      ```

      Verbinde dich dann im Inspektor mit `https://mcp.webforj.com/mcp` und erkunde die verfügbaren Tools.
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>Wie melde ich Probleme?</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Öffne ein Ticket unter Verwendung der [webforJ MCP-Issue-Vorlage](https://github.com/webforj/webforj/issues/new?template=mcp_report.yml). Füge die Anfrage, das erwartete Ergebnis und das, was du erhalten hast, hinzu.
    </div>
  </AccordionDetails>
</Accordion>
<br />
