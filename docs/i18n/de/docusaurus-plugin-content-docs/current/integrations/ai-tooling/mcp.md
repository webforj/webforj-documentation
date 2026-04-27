---
title: MCP Server
sidebar_position: 5
_i18n_hash: eea9d8f962b10512151bf7c6935f25e0
---
Der webforJ Model Context Protocol (MCP) Server integriert KI-Coding-Assistenten in die Dokumentation, APIs, Design-Tokens und Scaffold-Tools von webforJ. Anstatt Vermutungen über Framework-Konventionen anzustellen, fragt der Assistent den Server und erhält Antworten, die auf dem echten webforJ basieren.

:::tip Plugin verwenden
Es sei denn, Sie wissen, dass Sie nur den MCP-Server möchten, installieren Sie stattdessen das **[webforJ AI-Plugin](/docs/integrations/ai-tooling)** - es bündelt diesen Server mit den passenden [Agent Skills](/docs/integrations/ai-tooling/agent-skills) in einer einzigen Installation.
:::

## Was ist ein MCP? {#whats-an-mcp}

Das Model Context Protocol ist ein offener Standard, der es KI-Assistenten ermöglicht, externe Tools auf Abruf zu nutzen. Der webforJ MCP-Server implementiert dieses Protokoll, damit Ihr Assistent:

- Dinge in den webforJ-Dokumenten nachschlagen kann, anstatt Methodenbezeichnungen zu halluzinieren
- Neue webforJ-Projekte aus offiziellen Maven-Archetypen generieren kann
- Zugängliche DWC-Themen aus einer Markenfarbe erstellen kann
- Die echten Styling-Oberflächen eines DWC-Komponenten lesen und jeden `--dwc-*` Token validieren kann, bevor er in Ihrem CSS landet

:::warning KI kann immer noch Fehler machen
Der MCP-Server verbessert die Genauigkeit erheblich, aber KI-Assistenten können in komplexen Szenarien immer noch falschen Code erzeugen. Überprüfen und testen Sie den generierten Code immer, bevor Sie ihn ausliefern.
:::

## Installation {#installation}

Für das volle Erlebnis installieren Sie das **[webforJ AI-Plugin](/docs/integrations/ai-tooling)** - es konfiguriert diesen Server zusammen mit den Agent Skills, die Ihr Assistent benötigt, um ihn optimal zu nutzen.

Wenn Sie nur den MCP-Server (keine Skills) möchten, richten Sie Ihren Client auf `https://mcp.webforj.com/mcp`:

<Tabs groupId="ide">
<TabItem value="claude-code" label="Claude Code" default>

```bash
claude mcp add webforj-mcp https://mcp.webforj.com/mcp -t http -s user
```

</TabItem>
<TabItem value="copilot-cli" label="GitHub Copilot CLI">

Der empfohlene Weg über Copilot CLI ist das **[webforJ AI-Plugin](/docs/integrations/ai-tooling)** - es registriert den MCP-Server für Sie in einem Schritt. Für ein reines MCP-Setup ohne Skills sehen Sie die Anweisungen pro Client im [webforJ AI-Repository](https://github.com/webforj/webforj-ai#clients).

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

Fügen Sie in `~/.gemini/settings.json` hinzu:

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

Fügen Sie in `~/.codex/config.toml` hinzu:

```toml
[mcp_servers.webforj-mcp]
url = "https://mcp.webforj.com/mcp"
```

</TabItem>
</Tabs>

### Andere Clients {#other-clients}

Cursor, Kiro, Goose, Junie, Antigravity und jeder andere MCP-over-HTTP-Client funktioniert ebenfalls - sie verwenden einfach ihr eigenes Konfigurationsformat. Siehe den [Installationsleitfaden pro Client](https://github.com/webforj/webforj-ai#clients) für das genaue Snippet für jeden.

## Was der Server tun kann {#capabilities}

Wenn der MCP-Server verbunden ist, erhält Ihr KI-Assistent die folgenden Möglichkeiten. Jede davon kann durch eine Anfrage in natürlicher Sprache ausgelöst werden - der Assistent wählt automatisch die richtige aus.

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>Richtige webforJ-Version anvisieren</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Bevor er versionssensible Fragen beantwortet (alles, was Stil oder API betrifft), ermittelt der Assistent, welche webforJ-Version Sie verwenden. Er liest `pom.xml`, wenn verfügbar, und fragt Sie sonst. Jedes nachfolgende Antwort bezieht sich auf diese Version.
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>Dinge in der webforJ-Wissensdatenbank nachschlagen</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Der Assistent kann die gesamte webforJ-Wissensdatenbank abfragen, um Antworten zu liefern, die auf dem echten Framework basieren. Die Ergebnisse beziehen sich auf das, worüber Sie fragen - eine API-Frage, einen Leitfaden, ein Codebeispiel oder die Kotlin DSL.

      **Beispielanfragen:**
      ```
      "Finden Sie die Beispiele zur Ereignisbehandlung des webforJ Button-Komponenten"

      "Wie richte ich das Routing mit @Route in webforJ ein?"

      "Zeigen Sie mir ein Beispiel zur Formularvalidierung in webforJ"
      ```
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>Neues webforJ-Projekt scaffolden</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Der Assistent generiert den richtigen Maven-Archetyp-Befehl für eine neue webforJ-App basierend auf Ihren Anforderungen (Archetyp, Spring-Integration, Name, Gruppe).

      **Archetypen:**
      - `hello-world` - Start-App mit Beispielkomponenten
      - `blank` - minimales Projektstrukture
      - `tabs` - tabbed Interface Layout
      - `sidemenu` - seitliches Navigationslayout

      **Varianten:**
      - `webforj` - Standard webforJ-App
      - `webforj-spring` - webforJ integriert mit Spring Boot

      **Beispielanfragen:**
      ```
      "Erstellen Sie ein webforJ-Projekt mit dem Namen CustomerPortal unter Verwendung des sidemenu-Archetyps"

      "Generieren Sie ein webforJ Spring Boot-Projekt mit dem Tabs-Layout namens Dashboard"
      ```

      :::tip Verfügbare Archetypen
      Für die vollständige Liste der Archetypen siehe das [Archetypen-Katalog](/docs/building-ui/archetypes/overview).
      :::
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>DWC-Thema generieren</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Aus einer einzigen Markenfarbe erstellt der Assistent ein komplettes DWC-Thema: primär, erfolgreich, warnend, gefährlich, informativ, standard und graue Paletten mit automatischem Textkontrast. Die Ausgabe umfasst das Stylesheet sowie die Verkabelung `@AppTheme` / `@StyleSheet`.

      **Beispielanfragen:**
      ```
      "Generieren Sie ein webforJ-Thema aus der Markenfarbe #6366f1"

      "Erstellen Sie ein zugängliches Thema mit HSL 220, 70, 50 als Primärfarbe"
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
      Der Assistent liest die echten Styling-Oberflächen jedes DWC-Komponentens - CSS-Custom-Properties, Schattenbestandteile, reflektierte Attribute und Slots - bevor er CSS schreibt. Er kann auch jeden DWC-Tag auflisten und die Java-Klassennamen von webforJ (`Button`, `TextField`) auf ihre DWC-Entsprechungen auflösen.

      **Beispielanfragen:**
      ```
      "Welche CSS-Variablen und Teile gibt es bei dwc-button?"

      "Zeigen Sie mir jeden Slot, der bei dwc-dialog verfügbar ist"

      "Welchem DWC-Tag entspricht die webforJ TextField-Klasse?"
      ```

      Kombinieren Sie dies mit der [styling-apps Agent Skill](/docs/integrations/ai-tooling/agent-skills) für durchgängige Styling-Workflows.
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>Mit DWC-Design-Tokens arbeiten</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Der Assistent kann das autoritative Katalog von `--dwc-*` Tokens für Ihre webforJ-Version auflisten - Farbpaletten, Schattierungen, Oberflächen, Abstände, Typografie, Rahmen - gefiltert nach Präfix oder Teilstring. Er wird auch jeden CSS-, Java- oder Markdown-Quellcode, den Sie ihm geben, gegen den echten Token-Katalog validieren und unbekannte Namen mit vorgeschlagenen Korrekturen kennzeichnen.

      **Beispielanfragen:**
      ```
      "Listen Sie jeden --dwc-space-* Token auf"

      "Validieren Sie app.css auf unbekannte --dwc-* Tokens"

      "Welche Schattierungen der Primärpalette sind verfügbar?"
      ```

      Die Validierung findet Tippfehler und erfundene Tokens, bevor sie als lautlos fehlgeschlagenes CSS ausgeliefert werden.
    </div>
  </AccordionDetails>
</Accordion>

## Gute Anfragen schreiben {#writing-good-prompts}

Der MCP-Server wird nur konsultiert, wenn Ihr Assistent denkt, dass es relevant ist. Einige Gewohnheiten halten ihn aktiv:

- **Nennen Sie das Framework.** Erwähnen Sie "webforJ" in der Anfrage, damit der Assistent auf den MCP-Server zugreift, anstatt auf sein allgemeines Java-Wissen.
- **Seien Sie spezifisch.** `"Erstellen Sie ein webforJ-Projekt namens InventorySystem mit dem sidemenu-Archetyp und Spring Boot"` ist besser als `"machen Sie eine App"`.
- **Fragen Sie nach einer Überprüfung.** Phrasen wie `"überprüfen Sie anhand der webforJ-Dokumente"` oder `"überprüfen Sie dieses CSS auf schlechte --dwc-* Tokens"` bringen den Assistenten dazu, die Tools zu verwenden, anstatt zu raten.

Wenn Ihr Assistent immer noch antwortet, ohne den Server zu konsultieren, installieren Sie das [webforJ AI-Plugin](https://github.com/webforj/webforj-ai) - es liefert passende Agent Skills, die den Assistenten automatisch dazu anregen, die MCP-Tools bei Aufgaben in webforJ zu nutzen.

## FAQ {#faq}

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>Warum verwendet der KI-Assistent den MCP-Server nicht?</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Die meisten Assistenten greifen nur auf den MCP zu, wenn sie denken, dass die Frage es benötigt. Zwei Lösungen:

      1. **Installieren Sie das [webforJ AI-Plugin](https://github.com/webforj/webforj-ai)**, das den Server mit Agent Skills kombiniert, die dem Assistenten sagen, den MCP für webforJ-Aufgaben zu verwenden.
      2. **Seien Sie explizit in Ihrer Anfrage**: Fügen Sie "webforJ" zur Frage hinzu und sagen Sie in hartnäckigen Fällen "verwenden Sie den webforJ MCP-Server, um zu antworten".
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
      Öffnen Sie ein Ticket mit der [webforJ MCP-Problemvorlage](https://github.com/webforj/webforj/issues/new?template=mcp_report.yml). Geben Sie die Anfrage, das erwartete Ergebnis und das, was Sie erhalten haben, an.
    </div>
  </AccordionDetails>
</Accordion>
<br />
