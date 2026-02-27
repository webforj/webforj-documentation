---
title: MCP Server
sidebar_position: 5
_i18n_hash: a45888cf39bbbce0002177da8fe95eba
---
Der webforJ Model Context Protocol (MCP) Server bietet KI-Assistenten direkten Zugang zu offizieller webforJ-Dokumentation, verifizierten Codebeispielen und frameworkspezifischen Mustern, sodass Antworten mit genaueren Antworten und automatisierter Projektgenerierung speziell für die webforJ-Entwicklung möglich sind.

## Was ist ein MCP?

Das Model Context Protocol ist ein offener Standard, der es KI-Assistenten ermöglicht, mit externen Tools und Dokumentationen zu verbinden. Der webforJ MCP-Server implementiert dieses Protokoll, um Folgendes bereitzustellen:

- **Wissenssuche** - Natürliche Sprache Suche in der webforJ-Dokumentation, Codebeispielen und Mustern
- **Projektgenerierung** - Erstellen von webforJ-Anwendungen aus offiziellen Vorlagen mit ordnungsgemäßer Struktur
- **Theme-Erstellung** - Generierung barrierefreier CSS-Themen gemäß den webforJ-Designmustern

## Warum MCP verwenden?

Während KI-Coding-Assistenten darin glänzen, grundlegende Fragen zu beantworten, haben sie Schwierigkeiten mit komplexen, webforJ-spezifischen Anfragen, die mehrere Dokumentationsabschnitte umfassen. Ohne direkten Zugang zu offiziellen Quellen können sie:

- Methoden generieren, die es in webforJ nicht gibt
- Veraltete oder falsche API-Muster referenzieren  
- Code bereitstellen, der nicht übersetzt werden kann
- Die webforJ-Syntax mit anderen Java-Frameworks verwechseln
- webforJ-spezifische Muster missverstehen

Mit der MCP-Integration sind KI-Antworten an tatsächliche webforJ-Dokumentation, Codebeispiele und Framework-Muster verankert, was überprüfbare Antworten mit direkten Links zu offiziellen Quellen für eine tiefere Erkundung bietet.

:::warning KI kann immer noch Fehler machen
Obwohl MCP die Genauigkeit erheblich verbessert, indem es Zugang zu offiziellen webforJ-Ressourcen bietet, garantiert es keine perfekte Codegenerierung. KI-Assistenten können in komplexen Szenarien weiterhin Fehler machen. Überprüfen Sie stets den generierten Code und testen Sie gründlich, bevor Sie ihn in der Produktion verwenden.
:::

## Installation

Der webforJ MCP-Server wird unter `https://mcp.webforj.com` mit zwei Endpunkten gehostet:

- **MCP-Endpunkt** (`/mcp`) - Für Claude, VS Code, Cursor
- **SSE-Endpunkt** (`/sse`) - Für Legacy-Clients

<Tabs groupId="ide">
<TabItem value="vscode" label="VS Code">

Fügen Sie diese Konfiguration zu Ihrer VS Code settings.json-Datei hinzu:

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
<TabItem value="cursor" label="Cursor">

Fügen Sie diese Konfiguration zu Ihren Cursor-Einstellungen hinzu:

```json
"mcpServers": {
  "webforj-mcp": {
    "url": "https://mcp.webforj.com/mcp"
  }
}
```

</TabItem>
<TabItem value="claude-code" label="Claude Code" default>

Verwenden Sie den Claude-CLI-Befehl, um den Server zu registrieren:

```bash
claude mcp add webforj-mcp https://mcp.webforj.com/mcp -t http -s user
```

Dies konfiguriert den MCP-Server automatisch in Ihrer Claude-Code-Umgebung.

</TabItem>
<TabItem value="claude-desktop" label="Claude Desktop">

Fügen Sie diesen Server über das Integrationen-Panel in den Claude-Desktop-Einstellungen hinzu:

1. Öffnen Sie Claude Desktop und gehen Sie zu Einstellungen
2. Klicken Sie in der Seitenleiste auf "Integrationen"
3. Klicken Sie auf "Integration hinzufügen" und fügen Sie die URL ein: `https://mcp.webforj.com/mcp`
4. Folgen Sie dem Einrichtungsassistenten, um die Konfiguration abzuschließen

Für detaillierte Anweisungen siehe den [offiziellen Integrationsleitfaden](https://support.anthropic.com/en/articles/11175166-about-custom-integrations-using-remote-mcp).

</TabItem>
<TabItem value="windsurf" label="Windsurf">

Fügen Sie diese Serverkonfiguration zu Ihren Windsurf-MCP-Einstellungen hinzu:

```json
{
  "mcpServers": {
    "webforj-mcp": {
      "serverUrl": "https://mcp.webforj.com/sse"
    }
  }
}
```

</TabItem>
</Tabs>

## Verfügbare Werkzeuge

Werkzeuge sind spezialisierte Funktionen, die der MCP-Server KI-Assistenten bereitstellt. Wenn Sie eine Frage stellen oder eine Anfrage machen, kann die KI diese Werkzeuge aufrufen, um Dokumentation zu durchsuchen, Projekte zu generieren oder Themen zu erstellen. Jedes Werkzeug akzeptiert spezifische Parameter und gibt strukturierte Daten zurück, die der KI helfen, genaue, kontextbezogene Unterstützung zu bieten.

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>webforj-knowledge-base</code></strong> - Dokumentation und Beispiele durchsuchen
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Dieses Werkzeug bietet semantische Suchfunktionen über das gesamte webforJ-Dokumentations-Ökosystem. Es versteht den Kontext und Beziehungen zwischen verschiedenen Framework-Konzepten und gibt relevante Dokumentationsabschnitte, API-Referenzen und funktionierende Codebeispiele zurück.

      **Beispielabfragen:**
      ```
      "Durchsuche die webforJ-Dokumentation nach Button-Komponenten mit Icon-Beispielen"

      "Finde webforJ-Formularvalidierungsmuster in der neuesten Dokumentation"

      "Zeig mir die aktuelle webforJ-Routing-Konfiguration mit der @Route-Anmerkung"

      "Durchsuche die webforJ-Dokumente nach FlexLayout-Responsive-Designmustern"

      "Finde die Integration von webforJ-Webkomponenten in der offiziellen Dokumentation"
      ```
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>webforj-create-project</code></strong> - Neue webforJ-Projekte generieren  
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Erstellen Sie vollständige webforJ-Anwendungen mithilfe offizieller Maven-Archetypen. Das Werkzeug erstellt ein standardisiertes Projektverzeichnislayout und umfasst Starter-Code basierend auf der gewählten Vorlage. Generierte Projekte enthalten ein sofort einsatzbereites Build-System, Ressourcenordner und Konfigurationsdateien für die sofortige Entwicklung und Bereitstellung.

      **Beispielaufforderungen:**
      ```
      "Erstelle ein webforJ-Projekt namens CustomerPortal mit dem hello-world-Archetyp"

      "Generiere ein webforJ Spring Boot-Projekt mit einem Tab-Layout namens Dashboard"

      "Erstelle eine neue webforJ-App mit dem Sidemenu-Archetyp für das AdminPanel-Projekt"

      "Generiere ein leeres webforJ-Projekt namens TestApp mit der groupId com.example"

      "Erstelle ein webforJ-Projekt InventorySystem mit dem Sidemenu-Archetyp und Spring Boot"
      ```

      Bei der Verwendung dieses Werkzeugs können Sie aus mehreren Projektvorlagen auswählen:

      **Archetypen** (Projektvorlagen):
      - `hello-world` - Grundlegende App mit Beispielkomponenten, um die Funktionen von webforJ zu demonstrieren
      - `blank` - Minimale Projektstruktur zum Starten von Grund auf
      - `tabs` - Vorgefertigte, registerkartenbasierte Benutzeroberflächenlayout für Anwendungen mit mehreren Ansichten
      - `sidemenu` - Layout mit seitlicher Navigationsleiste für Administrationspanels oder Dashboards

      **Flavors** (Framework-Integration):
      - `webforj` - Standard-webforJ-App
      - `webforj-spring` - webforj, integriert mit Spring Boot für Dependency Injection und Unternehmensfunktionen

      :::tip Verfügbare Archetypen
      webforJ kommt mit mehreren vordefinierten Archetypen, um Ihnen einen schnellen Start zu ermöglichen. Für eine vollständige Liste der verfügbaren Archetypen siehe den [Archetypen-Katalog](/docs/building-ui/archetypes/overview).
      :::
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>webforj-create-theme</code></strong> - Barrierefreie CSS-Themen erstellen
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Generiert webforJ-Themkonfigurationen mit [DWC HueCraft](https://huecraft.dwc.style/). Das Werkzeug erstellt vollständige CSS-Kategorieneigenschaften mit primären, sekundären, Erfolgs-, Warn-, Gefahren- und neutralen Farbvarianten.

      **Beispielanfragen:**
      ```
      "Generiere ein webforJ-Thema mit HSL 220, 70, 50 als Hauptfarbe für unsere Unternehmensmarke"

      "Erstelle ein barrierefreies webforJ-Thema namens 'ocean' mit der Hauptfarbe #0066CC"

      "Generiere ein webforJ-Thema mit unserer Markenfarbe #FF5733"

      "Erstelle ein webforJ-Thema mit HSL 30, 100, 50, das 'sunset' für unsere App genannt wird"

      "Generiere ein barrierefreies webforJ-Thema mit der Hauptfarbe RGB 44, 123, 229"
      ```
    </div>
  </AccordionDetails>
</Accordion>

## Verfügbare Aufforderungen {#available-prompts}

Aufforderungen sind vorkonfigurierte KI-Anweisungen, die mehrere Werkzeuge und Arbeitsabläufe für häufige Aufgaben kombinieren. Sie führen die KI durch spezifische Schritte und Parameter, um zuverlässige, wiederholbare Ergebnisse für jeden unterstützten Arbeitsablauf zu liefern.

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>create-app</code></strong> - Erstellen und Ausführen einer webforJ-App
  </AccordionSummary>
  <AccordionDetails>
    <div>
      **Argumente:**
      - `appName` (erforderlich) - Anwendungsname (z.B. MyApp, TodoList, Dashboard)
      - `archetype` (erforderlich) - Wählen Sie aus: `blank`, `hello-world`, `tabs`, `sidemenu`
      - `runServer` (optional) - Den Entwicklungsserver automatisch ausführen (ja/nein)
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>create-theme</code></strong> - Generiere ein webforJ-Thema aus einer Hauptfarbe
  </AccordionSummary>
  <AccordionDetails>
    <div>
      **Argumente:**
      - `primaryColor` (erforderlich) - Farbe im Hex-Format (#FF5733), rgb (255,87,51) oder hsl (9,100,60)
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>search-webforj</code></strong> - Erweiterte Suche mit autonomem Problemlösen
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Die Aufforderung konfiguriert die KI dazu:

      1. Umfassend in der Wissensdatenbank zu suchen
      2. Vollständigen, produktionsbereiten Code zu schreiben
      3. Das Projekt mit `mvn compile` zu kompilieren, um sicherzustellen, dass es keine Build-Fehler gibt
      4. Fehler iterativ zu beheben, bis alles funktioniert
    </div>
  </AccordionDetails>
</Accordion>

### Wie man Aufforderungen verwendet

<Tabs groupId="ide">
<TabItem value="vscode" label="VS Code und Claude Code">

1. Geben Sie <kbd>/</kbd> im Chat ein, um verfügbare Aufforderungen zu sehen
2. Wählen Sie eine Aufforderung aus dem Dropdown-Menü aus
3. Füllen Sie die erforderlichen Parameter aus, wenn Sie dazu aufgefordert werden

</TabItem>
<TabItem value="claude-desktop" label="Claude Desktop">

1. Klicken Sie auf das **+** (Plus)-Symbol im Aufforderungs Eingabefeld
2. Wählen Sie **"Aus webforJ hinzufügen"** im Menü
3. Wählen Sie die gewünschte Aufforderung (z.B. `create-app`, `create-theme`, `search-webforj`)
4. Claude fordert Sie auf, die erforderlichen Argumente einzugeben
5. Füllen Sie die Parameter wie angefordert aus

:::tip MCP-Verbindung überprüfen
Achten Sie auf das Werkzeugsymbol in der unteren Ecke des Eingabebereichs, um zu bestätigen, dass der webforJ MCP-Server verbunden ist.
:::

</TabItem>
</Tabs>

## Best Practices

Um die genauesten und aktuellsten webforJ-Hilfen zu erhalten, befolgen Sie diese Richtlinien, um die Funktionen des MCP-Servers voll auszuschöpfen.

### Sicherstellen der Nutzung des MCP-Servers

KI-Modelle könnten den MCP-Server überspringen, wenn sie glauben, sie wüssten bereits die Antwort. Um sicherzustellen, dass der MCP-Server tatsächlich verwendet wird:

- **Seien Sie explizit zu webforJ**: Erwähnen Sie immer "webforJ" in Ihrer Anfrage, um frameworkspezifische Suchen auszulösen
- **Fordern Sie aktuelle Informationen an**: Fügen Sie Phrasen wie "neueste webforJ-Dokumentation" oder "aktuelle webforJ-Muster" hinzu
- **Fragen Sie nach verifizierten Beispielen**: Fordern Sie "funktionierende webforJ-Codebeispiele" an, um die Dokumentationssuche zu erzwingen
- **Referenzieren Sie spezifische Versionen**: Erwähnen Sie Ihre webforJ-Version (z.B. "webforJ `25.02`"), um genaue Ergebnisse zu erhalten

### Schreiben spezifischer Aufforderungen

**Gute Beispiele:**
```
"Durchsuche die webforJ-Dokumentation nach Button-Komponenten-Eventhandling mit Beispielen"

"Erstelle ein webforJ-Projekt namens InventorySystem mit dem Sidemenu-Archetyp und Spring Boot"

"Generiere ein webforJ-Thema mit HSL 220, 70, 50 als Hauptfarbe für die Unternehmensmarke"
```

**Schlechte Beispiele:**
```
"Wie funktionieren Buttons"

"Mach eine App"

"Mach es blau"
```

### Erzwingen der Verwendung von MCP-Tool

Wenn die KI allgemeine Antworten ohne Nutzung des MCP-Servers gibt:

1. **Explizit anfordern**: "Verwenden Sie den webforJ MCP-Server, um nach `[query]` zu suchen"
2. **Nach Dokumentationsreferenzen fragen**: "Finde in der webforJ-Dokumentation heraus, wie man `[query]`"
3. **Überprüfungen anfordern**: "Überprüfen Sie diese Lösung anhand der webforJ-Dokumentation"
4. **Framework-spezifisch sein**: Fügen Sie immer "webforJ" in Ihre Anfragen ein

## Anpassung von KI {#ai-customization}

Konfigurieren Sie Ihre KI-Assistenten so, dass sie den MCP-Server automatisch verwenden und die besten Praktiken von webforJ befolgen. Fügen Sie projektspezifische Anweisungen hinzu, damit Ihre KI-Assistenten den MCP-Server stets verwenden, die Dokumentationsstandards von webforJ befolgen und genaue, aktuelle Antworten liefern, die den Anforderungen Ihres Teams entsprechen.

### Projektkonfigurationsdateien

- Für **VS Code und Copilot** erstellen Sie `.github/copilot-instructions.md`
- Für **Claude Code** erstellen Sie `CLAUDE.md` im Projektstammverzeichnis

Fügen Sie Folgendes zu der erstellten Markdown-Datei hinzu:
```markdown
## Verwenden Sie den webforJ MCP-Server, um alle Fragen zu webforJ zu beantworten

- Rufen Sie immer das Werkzeug "webforj-knowledge-base" auf, um relevante Dokumente zu suchen
- Überprüfen Sie alle API-Signaturen anhand der offiziellen Dokumentation
- Nehmen Sie niemals an, dass Methoden- oder Parameternamen existieren, ohne dies zu überprüfen

Überprüfen Sie immer, ob der Code mit `mvn compile` übersetzt wird, bevor Sie Vorschläge machen.
```

## FAQ

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>Warum verwendet die KI den webforJ MCP-Server nicht?</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Die meisten KI-Assistenten benötigen explizite Anweisungen, um MCP-Server zu verwenden. Konfigurieren Sie Ihren KI-Client mit den Anweisungen aus dem Abschnitt [KI-Anpassung](#ai-customization). Ohne diese Anweisungen verwenden KI-Assistenten möglicherweise standardmäßig ihre Trainingsdaten, anstatt den MCP-Server zu befragen.

      **Schnelle Lösung:**
      Fügen Sie "verwendet den webforJ MCP" in Ihre Aufforderung ein oder erstellen Sie die entsprechende Konfigurationsdatei (`.github/copilot-instructions.md` oder `CLAUDE.md`).
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>Wie kann ich überprüfen, ob die MCP-Verbindung funktioniert?</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Verwenden Sie den MCP-Inspektor, um Verbindungen zu debuggen:

      ```bash
      npx @modelcontextprotocol/inspector
      ```

      Warten Sie auf die Nachricht: `🔍 MCP Inspector ist aktiv und läuft unter http://127.0.0.1:6274` (Port kann variieren)

      Dann im Inspektor:
      1. Geben Sie die MCP-Server-URL ein: `https://mcp.webforj.com/mcp`
      2. Klicken Sie auf "Verbinden", um die Verbindung herzustellen
      3. Sehen Sie sich verfügbare Werkzeuge an und testen Sie Anfragen
      4. Überwachen Sie die Anforderungs-/Antwortprotokolle zur Fehlersuche
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>Was ist der Unterschied zwischen MCP- und SSE-Endpunkten?</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Der webforJ MCP-Server bietet zwei Endpunkte:

      - **MCP-Endpunkt** (`/mcp`) - Modernes Protokoll für Claude, VS Code, Cursor
      - **SSE-Endpunkt** (`/sse`) - Server-Sent Events für Legacy-Clients wie Windsurf

      Die meisten Benutzer sollten den MCP-Endpunkt verwenden. Verwenden Sie SSE nur, wenn Ihr Client das Standard-MCP-Protokoll nicht unterstützt.
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>Ist es möglich, den MCP-Server ohne Konfigurationsdateien zu verwenden?</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Ja, aber es wird nicht empfohlen. Ohne Konfigurationsdateien müssen Sie die KI manuell auffordern, den MCP-Server in jeder Konversation zu verwenden. Konfigurationsdateien instruieren die KI automatisch, den MCP-Server für jede Interaktion zu verwenden, sodass Sie nicht jedes Mal Anweisungen wiederholen müssen.

      **Manuelle Vorgehensweise:**
      Beginnen Sie Aufforderungen mit: "Verwenden Sie den webforJ MCP-Server, um..."

      **Alternativ: Verwenden Sie vorkonfigurierte Aufforderungen**
      Der MCP-Server bietet Aufforderungen, die ohne Konfigurationsdateien funktionieren:
      - `/create-app` - Neue webforJ-Anwendungen generieren
      - `/create-theme` - Barrierefreie CSS-Themen erstellen
      - `/search-webforj` - Erweiterte Dokumentationssuche

      Siehe [Verfügbare Aufforderungen](#available-prompts) für Details.
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>Wie kann ich beitragen oder Probleme melden?</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      **Probleme melden:** [webforJ MCP-Issue-Vorlage](https://github.com/webforj/webforj/issues/new?template=mcp_report.yml)
      
      **Häufige Probleme, die gemeldet werden sollten:**
      - Veraltete Dokumentation in den Suchergebnissen
      - Fehlende API-Methoden oder -Komponenten
      - Falsche Codebeispiele
      - Werkzeugausführungsfehler

      Geben Sie Ihre Anfrage, das erwartete Ergebnis und das tatsächliche Ergebnis an, wenn Sie Probleme melden.
    </div>
  </AccordionDetails>
</Accordion>
<br />
