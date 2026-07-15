---
title: MCP Server
sidebar_position: 5
description: >-
  Connect AI assistants to the webforJ MCP server for live documentation lookup,
  project scaffolding, theme generation, and token validation.
_i18n_hash: fb0e068ee7d7a489237e021b24a883b0
---
Der webforJ Model Context Protocol (MCP) Server integriert KI-Coding-Assistenten in die Dokumentation, APIs, Design-Tokens und Scaffold-Tools von webforJ. Anstatt Vermutungen über Framework-Konventionen anzustellen, fragt der Assistent den Server und erhält Antworten, die auf dem echten webforJ basieren.

:::tip Verwenden Sie das Plugin
Es sei denn, Sie wissen, dass Sie nur den MCP-Server want, installieren Sie stattdessen das **[webforJ AI plugin](/docs/ai-tooling)** - es bündelt diesen Server mit den passenden [Agent Skills](/docs/ai-tooling/agent-skills) in einer einzigen Installation.
:::

## Was ist ein MCP? {#whats-an-mcp}

Das Model Context Protocol ist ein offener Standard, der es KI-Assistenten ermöglicht, bei Bedarf externe Werkzeuge aufzurufen. Der webforJ MCP-Server implementiert dieses Protokoll, damit Ihr Assistent:

- Dinge in den webforJ-Dokumenten nachschlagen kann, anstatt Methoden-Namen zu halluzinieren
- Neue webforJ-Projekte aus offiziellen Maven-Archetypen scaffolden kann
- Zugängliche DWC-Themen aus einer Markenfarbe generieren kann
- Die reale Styling-Oberfläche einer DWC-Komponente lesen und jeden `--dwc-*` Token validieren kann, bevor er in Ihrem CSS landet

:::warning KI kann immer noch Fehler machen
Der MCP-Server verbessert die Genauigkeit erheblich, aber KI-Assistenten können dennoch in komplexen Szenarien fehlerhaften Code produzieren. Überprüfen und testen Sie immer den generierten Code, bevor Sie ihn einsetzen.
:::

## Installation {#installation}

Für das volle Erlebnis installieren Sie das **[webforJ AI plugin](/docs/ai-tooling)** - es konfiguriert diesen Server zusammen mit den Agent Skills, die Ihr Assistent benötigt, um ihn gut zu nutzen.

Wenn Sie nur den MCP-Server (keine Skills) möchten, richten Sie Ihren Client auf `https://mcp.webforj.com/mcp`:

<Tabs groupId="ide">
<TabItem value="claude-code" label="Claude Code" default>

```bash
claude mcp add webforj-mcp https://mcp.webforj.com/mcp -t http -s user
```

</TabItem>
<TabItem value="copilot-cli" label="GitHub Copilot CLI">

Der empfohlene Weg mit Copilot CLI ist das **[webforJ AI plugin](/docs/ai-tooling)** - es registriert den MCP-Server für Sie in einem Schritt. Für ein reines MCP-Setup sehen Sie die client-spezifischen Anweisungen im [webforJ AI-Repository](https://github.com/webforj/webforj-ai#clients).

</TabItem>
<TabItem value="vscode" label="VS Code + Copilot">

Fügen Sie zu Ihren VS Code-Einstellungen hinzu:

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

Fügen Sie zu `~/.gemini/settings.json` hinzu:

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

Fügen Sie zu `~/.codex/config.toml` hinzu:

```toml
[mcp_servers.webforj-mcp]
url = "https://mcp.webforj.com/mcp"
```

</TabItem>
</Tabs>

### Andere Clients {#other-clients}

Cursor, Kiro, Goose, Junie, Antigravity und jeder andere MCP-über-HTTP-Client funktioniert ebenfalls - sie verwenden einfach ihr eigenes Konfigurationsformat. Siehe die [client-spezifische Installationsanleitung](https://github.com/webforj/webforj-ai#clients) für das genaue Snippet für jeden.

## Was der Server tun kann {#capabilities}

Wenn der MCP-Server verbunden ist, erhält Ihr KI-Assistent die folgenden Fähigkeiten. Jede davon kann durch eine Anfrage in natürlicher Sprache ausgelöst werden - der Assistent wählt automatisch die richtige aus.

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>Richten Sie die richtige webforJ-Version ein</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Bevor der Assistent versionssensitives Fragen beantwortet (alles, was Styling oder API-spezifisch ist), stellt er fest, welche webforJ-Version Sie verwenden. Er liest `pom.xml`, wenn dies verfügbar ist, und fragt Sie andernfalls. Jede nachfolgende Antwort bezieht sich auf diese Version.
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>In der webforJ Wissensdatenbank nachschlagen</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Der Assistent kann die gesamte webforJ Wissensdatenbank abfragen, um Antworten zu liefern, die auf dem echten Framework basieren. Die Ergebnisse beziehen sich auf das, worum Sie fragen - eine API-Frage, einen Leitfaden, ein Codebeispiel oder die Kotlin DSL.

      **Beispielaufforderungen:**
      ```
      "Finde die Beispiele zur Ereignisbehandlung des webforJ Button-Komponenten"

      "Wie richte ich Routing mit @Route in webforJ ein?"

      "Zeige mir ein Beispiel zur Formularvalidierung in webforJ"
      ```
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>Scaffold ein neues webforJ-Projekt</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Der Assistent generiert den korrekten Maven-Archetypen-Befehl für eine neue webforJ-App basierend auf Ihren Anforderungen (Archetyp, Spring-Integration, Name, Gruppe).

      **Archetypen:**
      - `hello-world` - Starter-App mit Beispielkomponenten
      - `blank` - minimales Projektgerüst
      - `tabs` - tabbed Interface Layout
      - `sidemenu` - seitliches Navigationslayout

      **Varianten:**
      - `webforj` - Standard-webforJ-App
      - `webforj-spring` - webforJ integriert mit Spring Boot

      **Beispielaufforderungen:**
      ```
      "Erstelle ein webforJ-Projekt namens CustomerPortal mit dem sidemenu-Archetyp"

      "Generiere ein webforJ Spring Boot-Projekt mit dem Tabs-Layout namens Dashboard"
      ```

      :::tip Verfügbare Archetypen
      Für die vollständige Liste der Archetypen siehe das [Archetypen-Katalog](/docs/building-ui/archetypes/overview).
      :::
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>Erzeuge ein DWC-Thema</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Aus einer einzigen Markenfarbe erzeugt der Assistent ein vollständiges DWC-Thema: primäre, Erfolgs-, Warn-, Gefahren-, Informations-, Standard- und Graupaletten mit automatischem Textkontrast. Die Ausgabe umfasst das Stylesheet sowie die Verdrahtung `@AppTheme` / `@StyleSheet`.

      **Beispielaufforderungen:**
      ```
      "Generiere ein webforJ-Thema aus der Markenfarbe #6366f1"

      "Erstelle ein zugängliches Thema mit HSL 220, 70, 50 als primäre Farbe"
      ```
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>Stylen Sie DWC-Komponenten korrekt</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Der Assistent liest die reale Styling-Oberfläche jeder DWC-Komponente - CSS-Custom-Properties, Schattenparts, reflektierte Attribute und Slots - bevor er CSS schreibt. Er kann auch jeden DWC-Tag auflisten und webforJ-Java-Klassennamen (`Button`, `TextField`) ihren DWC-Entsprechungen zuordnen.

      **Beispielaufforderungen:**
      ```
      "Welche CSS-Variablen und Teile stellt dwc-button zur Verfügung?"

      "Zeige mir jeden Slot, der auf dwc-dialog verfügbar ist"

      "Welchem DWC-Tag entspricht die webforJ TextField-Klasse?"
      ```

      Kombinieren Sie dies mit der [styling-apps Agent-Skill](/docs/ai-tooling/agent-skills) für End-to-End-Styling-Workflows.
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>Arbeiten Sie mit DWC-Design-Token</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Der Assistent kann das autoritative Katalog von `--dwc-*` Token für Ihre webforJ-Version auflisten - Farbsamen, Schattierungen, Oberflächen, Abstände, Typografie, Rahmen - nach Präfix oder Teilstring gefiltert. Er wird auch jedes CSS, Java oder Markdown-Quellmaterial, das Sie ihm geben, gegen den realen Token-Katalog validieren und unbekannte Namen mit Vorschlägen zur Korrektur kennzeichnen.

      **Beispielaufforderungen:**
      ```
      "Liste jeden --dwc-space-* Token auf"

      "Validiere app.css auf unbekannte --dwc-* Token"

      "Welche Schattierungen der primären Palette sind verfügbar?"
      ```

      Die Validierung fängt Tippfehler und erfundene Tokens ab, bevor sie als stillschweigend fehlschlagendes CSS verschickt werden.
    </div>
  </AccordionDetails>
</Accordion>

## Gute Aufforderungen schreiben {#writing-good-prompts}

Der MCP-Server wird nur konsultiert, wenn Ihr Assistent denkt, dass es relevant ist. Einige Gewohnheiten halten ihn beschäftigt:

- **Nennen Sie das Framework.** Erwähnen Sie "webforJ" in der Aufforderung, damit der Assistent auf den MCP-Server zugreift, anstatt auf sein allgemeines Java-Wissen zurückzugreifen.
- **Seien Sie spezifisch.** `"Erstelle ein webforJ-Projekt namens InventorySystem mit dem sidemenu-Archetyp und Spring Boot"` schlägt `"Mach eine App"`.
- **Fragen Sie nach einer Überprüfung.** Formulierungen wie `"Vergleiche mit den webforJ-Dokumenten"` oder `"Überprüfe dieses CSS auf unerwünschte --dwc-* Tokens"` fordern den Assistenten dazu auf, die Tools zu verwenden, anstatt Vermutungen anzustellen.

Wenn Ihr Assistent immer noch antwortet, ohne den Server zu konsultieren, installieren Sie das [webforJ AI plugin](https://github.com/webforj/webforj-ai) - es liefert passende Agent Skills, die den Assistenten dazu drängen, die MCP-Tools automatisch für webforJ-Aufgaben zu verwenden.

## FAQ {#faq}

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>Warum verwendet der KI-Assistent den MCP-Server nicht?</p>
    <p>Warum verwendet der KI-Assistent den MCP-Server nicht?</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Die meisten Assistenten greifen nur auf den MCP zu, wenn sie denken, dass die Frage es erfordert. Zwei Lösungen:

      1. **Installieren Sie das [webforJ AI plugin](https://github.com/webforj/webforj-ai)**, das den Server mit Agent Skills kombiniert, die den Assistenten anweisen, MCP für webforJ-Aufgaben zu verwenden.
      2. **Seien Sie explizit in Ihrer Aufforderung**: Fügen Sie "webforJ" in die Frage ein und sagen Sie in hartnäckigen Fällen "Verwenden Sie den webforJ MCP-Server, um zu antworten".
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>Wie überprüfe ich, ob die MCP-Verbindung funktioniert?</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Verwenden Sie den MCP-Inspektor:

      ```bash
      npx @modelcontextprotocol/inspector
      ```

      Verbinden Sie sich dann im Inspektor mit `https://mcp.webforj.com/mcp` und erkunden Sie die verfügbaren Tools.
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>Wie melde ich Probleme?</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Öffnen Sie ein Ticket mit der [webforJ MCP-Vorlage für Probleme](https://github.com/webforj/webforj/issues/new?template=mcp_report.yml). Fügen Sie die Aufforderung, das erwartete Ergebnis und das, was Sie erhalten haben, hinzu.
    </div>
  </AccordionDetails>
</Accordion>
<br />
