---
title: MCP Server
sidebar_position: 2.5
sidebar_class_name: new-content
_i18n_hash: 640740e70970d2eaa1379ce63374ed94
---
Der webforJ Model Context Protocol (MCP) Server bietet KI-Assistenten direkten Zugang zu offiziellen webforJ-Dokumentationen, verifizierten Codebeispielen und frameworkspezifischen Mustern, sodass Antworten mit genaueren Antworten und automatisierter Projektgenerierung speziell für die webforJ-Entwicklung ermöglicht werden.

## Was ist ein MCP?

Das Model Context Protocol ist ein offener Standard, der es KI-Assistenten ermöglicht, sich mit externen Tools und Dokumentationen zu verbinden. Der webforJ MCP-Server implementiert dieses Protokoll, um Folgendes bereitzustellen:

- **Wissenssuche** - Natürliche Sprachsuche in der webforJ-Dokumentation, Codebeispielen und Mustern
- **Projektgenerierung** - Erstellen von webforJ-Anwendungen aus offiziellen Vorlagen mit der richtigen Struktur
- **Theme-Erstellung** - Generieren von barrierefreien CSS-Themen gemäß den Designmustern von webforJ

## Warum MCP verwenden?

Während KI-Coding-Assistenten bei der Beantwortung grundlegender Fragen hervorragend sind, haben sie Schwierigkeiten mit komplexen, webforJ-spezifischen Anfragen, die mehrere Dokumentationsabschnitte umfassen. Ohne direkten Zugang zu offiziellen Quellen könnten sie:

- Methoden erzeugen, die in webforJ nicht existieren
- Veraltete oder falsche API-Muster referenzieren
- Code bereitstellen, der nicht kompiliert
- Die Syntax von webforJ mit anderen Java-Frameworks verwechseln
- webforJ-spezifische Muster missverstehen

Mit der MCP-Integration sind KI-Antworten an tatsächliche webforJ-Dokumentationen, Codebeispiele und Framework-Muster gebunden, was verifizierbare Antworten mit direkten Links zu offiziellen Quellen für eine tiefere Erkundung bereitstellt.

:::warning KI kann weiterhin Fehler machen
Obwohl MCP die Genauigkeit erheblich verbessert, indem es den Zugriff auf offizielle webforJ-Ressourcen bereitstellt, garantiert es keine perfekte Codegenerierung. KI-Assistenten können in komplexen Szenarien weiterhin Fehler machen. Überprüfen Sie immer den generierten Code und testen Sie gründlich, bevor Sie ihn in der Produktion verwenden.
:::

## Installation

Der webforJ MCP-Server wird unter `https://mcp.webforj.com` gehostet und bietet zwei Endpunkte:

- **MCP-Endpunkt** (`/mcp`) - Für Claude, VS Code, Cursor
- **SSE-Endpunkt** (`/sse`) - Für ältere Clients

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

Verwenden Sie den Claude CLI-Befehl, um den Server zu registrieren:

```bash
claude mcp add webforj-mcp https://mcp.webforj.com/mcp -t http -s user
```

Dies konfiguriert den MCP-Server automatisch in Ihrer Claude-Code-Umgebung.

</TabItem>
<TabItem value="claude-desktop" label="Claude Desktop">

Fügen Sie diesen Server über das Integrationsfeld in den Einstellungen von Claude Desktop hinzu:

1. Öffnen Sie Claude Desktop und gehen Sie zu den Einstellungen
2. Klicken Sie in der Seitenleiste auf "Integrationen"
3. Klicken Sie auf "Integration hinzufügen" und fügen Sie die URL ein: `https://mcp.webforj.com/mcp`
4. Folgen Sie dem Einrichtungsassistenten, um die Konfiguration abzuschließen

Für detaillierte Anweisungen siehe die [offizielle Integrationsanleitung](https://support.anthropic.com/en/articles/11175166-about-custom-integrations-using-remote-mcp).

</TabItem>
<TabItem value="windsurf" label="Windsurf">

Fügen Sie diese Serverkonfiguration zu Ihren Windsurf MCP-Einstellungen hinzu:

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

## Verfügbare Tools

Tools sind spezialisierte Funktionen, die der MCP-Server KI-Assistenten bereitstellt. Wenn Sie eine Frage stellen oder eine Anfrage machen, kann die KI diese Tools nutzen, um Dokumentationen zu durchsuchen, Projekte zu generieren oder Themen zu erstellen. Jedes Tool akzeptiert spezifische Parameter und gibt strukturierte Daten zurück, die der KI helfen, genaue, kontextbezogene Unterstützung zu bieten.

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>webforj-knowledge-base</code></strong> - Dokumentation und Beispiele durchsuchen
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Dieses Tool bietet semantische Suchfunktionen über das gesamte webforJ-Dokumentationsökosystem. Es versteht Kontexte und Beziehungen zwischen verschiedenen Framework-Konzepten und gibt relevante Dokumentationsabschnitte, API-Referenzen und funktionierende Codebeispiele zurück.

      **Beispielanfragen:**
      ```
      "Durchsuche die webforJ-Dokumentation nach Button-Komponenten mit Icon-Beispielen"

      "Finde webforJ-Formularvalidierungsmuster in der neuesten Dokumentation"

      "Zeige mir aktuelle webforJ-Routing-Einstellungen mit @Route-Annotation"

      "Durchsuche die webforJ-Dokumentation nach FlexLayout-Responsive-Designmustern"

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
      Scaffoldet komplette webforJ-Anwendungen unter Verwendung offizieller Maven-Archetypen. Das Tool erstellt ein standardisiertes Projektverzeichnislayout und enthält Startercode basierend auf der ausgewählten Vorlage. Generierte Projekte umfassen ein sofort einsatzbereites Build-System, Ressourcenordner und Konfigurationsdateien für sofortige Entwicklung und Bereitstellung.

      **Beispielaufforderungen:**
      ```
      "Erstelle ein webforJ-Projekt mit dem Namen CustomerPortal unter Verwendung des hello-world-Archetyps"

      "Generiere ein webforJ-Spring-Boot-Projekt mit einem Tab-Layout namens Dashboard"

      "Erstelle eine neue webforJ-App mit dem Sidemenü-Archetyp für das AdminPanel-Projekt"

      "Generiere ein leeres webforJ-Projekt mit dem Namen TestApp mit der groupId com.example"

      "Erstelle ein webforJ-Projekt InventorySystem mit dem Sidemenü-Archetyp und Spring Boot"
      ```

      Bei Verwendung dieses Tools können Sie aus mehreren Projektvorlagen wählen:

      **Archetypen** (Projektvorlagen):
      - `hello-world` - Basis-App mit Beispielkomponenten zur Demonstration der webforJ-Funktionen
      - `blank` - Minimale Projektstruktur zum Starten von Grund auf
      - `tabs` - Vorgefertigtes tab-basiertes Layout für Mehransichts-Anwendungen
      - `sidemenu` - Layout mit seitlicher Navigationsleiste für Administrator-Panels oder Dashboards

      **Flavors** (Framework-Integration):
      - `webforj` - Standard-webforJ-App
      - `webforj-spring` - webforJ integriert mit Spring Boot für Dependency Injection und Enterprise-Funktionen

      :::tip Verfügbare Archetypen
      webforJ wird mit mehreren vordefinierten Archetypen geliefert, um Ihnen den schnellen Einstieg zu erleichtern. Eine vollständige Liste der verfügbaren Archetypen finden Sie im [Archetypen-Katalog](../building-ui/archetypes/overview).
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
      Generiert webforJ-Theme-Konfigurationen unter Verwendung von [DWC HueCraft](https://huecraft.dwc.style/). Das Tool erstellt vollständige CSS-Custom-Property-Sets mit primären, sekundären, Erfolgs-, Warn-, Gefahren- und neutralen Farbvarianten.

      **Beispielanfragen:**
      ```
      "Generiere ein webforJ-Theme mit HSL 220, 70, 50 als Primärfarbe für unsere Firmenmarke"

      "Erstelle ein webforJ-barrierefreies Theme mit dem Namen 'ocean' und der Primärfarbe #0066CC"

      "Generiere ein webforJ-Theme mit unserer Markenfarbe #FF5733"

      "Erstelle ein webforJ-Theme mit HSL 30, 100, 50 mit dem Namen 'sunset' für unsere App"

      "Generiere ein barrierefreies webforJ-Theme mit der Primärfarbe RGB 44, 123, 229"
      ```
    </div>
  </AccordionDetails>
</Accordion>

## Verfügbare Eingabeaufforderungen {#available-prompts}

Eingabeaufforderungen sind vordefinierte KI-Anweisungen, die mehrere Tools und Arbeitsabläufe für gängige Aufgaben kombinieren. Sie führen die KI durch spezifische Schritte und Parameter, um zuverlässige, wiederholbare Ergebnisse für jeden unterstützten Arbeitsablauf zu liefern.

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>create-app</code></strong> - Erstelle und führe eine webforJ-App aus
  </AccordionSummary>
  <AccordionDetails>
    <div>
      **Argumente:**
      - `appName` (erforderlich) - Anwendungsname (z. B. MyApp, TodoList, Dashboard)
      - `archetype` (erforderlich) - Wählen Sie aus: `blank`, `hello-world`, `tabs`, `sidemenu`
      - `runServer` (optional) - Entwicklungsserver automatisch ausführen (ja/nein)
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>create-theme</code></strong> - Generiere ein webforJ-Theme aus einer Primärfarbe
  </AccordionSummary>
  <AccordionDetails>
    <div>
      **Argumente:**
      - `primaryColor` (erforderlich) - Farbe im Hex (#FF5733), rgb (255,87,51) oder hsl (9,100,60) Format
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>search-webforj</code></strong> - Erweiterte Suche mit autonomer Problemlösung
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Die Eingabeaufforderung konfiguriert die KI dazu:

      1. Die Wissensdatenbank umfassend zu durchsuchen
      2. Vollständigen, produktionsbereiten Code zu schreiben
      3. Das Projekt mit `mvn compile` zu kompilieren, um sicherzustellen, dass es keine Build-Fehler gibt
      4. Fehler iterativ zu beheben, bis alles funktioniert
    </div>
  </AccordionDetails>
</Accordion>

### So verwenden Sie Eingabeaufforderungen

<Tabs groupId="ide">
<TabItem value="vscode" label="VS Code und Claude Code">

1. Tippen Sie <kbd>/</kbd> im Chat ein, um verfügbare Eingabeaufforderungen anzuzeigen
2. Wählen Sie eine Eingabeaufforderung aus dem Dropdown-Menü aus
3. Füllen Sie die erforderlichen Parameter aus, wenn Sie dazu aufgefordert werden

</TabItem>
<TabItem value="claude-desktop" label="Claude Desktop">

1. Klicken Sie auf das **+** (Plus)-Symbol im Eingabefeld für die Eingabeaufforderungen
2. Wählen Sie **"Hinzufügen von webforJ"** aus dem Menü
3. Wählen Sie die gewünschte Eingabeaufforderung (z. B. `create-app`, `create-theme`, `search-webforj`)
4. Claude wird Sie auffordern, die erforderlichen Argumente einzugeben
5. Füllen Sie die Parameter wie angefordert aus

:::tip Überprüfen Sie, ob MCP verbunden ist
Achten Sie auf das Tools-Symbol in der unteren Ecke des Eingabebereichs, um zu bestätigen, dass der webforJ MCP-Server verbunden ist.
:::

</TabItem>
</Tabs>

## Best Practices

Um die genaueste und aktuellste webforJ-Hilfe zu erhalten, befolgen Sie diese Richtlinien, um die Funktionen des MCP-Servers optimal zu nutzen.

### Sicherstellen der Verwendung des MCP-Servers

KI-Modelle können den MCP-Server überspringen, wenn sie glauben, die Antwort bereits zu kennen. Um sicherzustellen, dass der MCP-Server tatsächlich verwendet wird:

- **Seien Sie explizit zu webforJ**: Erwähnen Sie immer "webforJ" in Ihrer Anfrage, um frameworkspezifische Suchen auszulösen
- **Fordern Sie aktuelle Informationen an**: Fügen Sie Phrasen wie "neueste webforJ-Dokumentation" oder "aktuelle webforJ-Muster" hinzu
- **Fragen Sie nach verifizierten Beispielen**: Fordern Sie "funktionierende webforJ-Codebeispiele" an, um eine Dokumentationssuche zu erzwingen
- **Verweisen Sie auf spezifische Versionen**: Erwähnen Sie Ihre webforJ-Version (z. B. "webforJ `25.02`"), um genaue Ergebnisse zu erhalten

### Schreiben spezifischer Eingabeaufforderungen

**Gute Beispiele:**
```
"Durchsuche die webforJ-Dokumentation nach Button-Komponenten mit Ereignisbehandlung und Beispielen"

"Erstelle ein webforJ-Projekt mit dem Namen InventorySystem mit dem Sidemenü-Archetyp und Spring Boot"

"Generiere ein webforJ-Theme mit HSL 220, 70, 50 als Primärfarbe für die Firmenmarke"
```

**Schlechte Beispiele:**
```
"Wie funktionieren Buttons"

"Mach eine App"

"Mach es blau"
```

### Erzwingen der Verwendung des MCP-Tools

Wenn die KI allgemeine Antworten gibt, ohne den MCP-Server zu verwenden:

1. **Fordern Sie explizit an**: "Verwenden Sie den webforJ MCP-Server, um nach `[query]` zu suchen"
2. **Fragen Sie nach Dokumentationsreferenzen**: "Finden Sie in der webforJ-Dokumentation heraus, wie man `[query]`"
3. **Fordern Sie eine Verifizierung an**: "Überprüfen Sie diese Lösung anhand der webforJ-Dokumentation"
4. **Seien Sie frameworkspezifisch**: Fügen Sie immer "webforJ" in Ihre Anfragen ein

## KI-Anpassung {#ai-customization}

Konfigurieren Sie Ihre KI-Assistenten so, dass sie automatisch den MCP-Server verwenden und den besten Praktiken von webforJ folgen. Fügen Sie projektspezifische Anweisungen hinzu, damit Ihre KI-Assistenten immer den MCP-Server verwenden, den Standards der webforJ-Dokumentation folgen und genaue, aktuelle Antworten liefern, die den Anforderungen Ihres Teams entsprechen.

### Projektkonfigurationsdateien

- Für **VS Code und Copilot** erstellen Sie `.github/copilot-instructions.md`
- Für **Claude Code** erstellen Sie `CLAUDE.md` im Hauptverzeichnis Ihres Projekts

Fügen Sie Folgendes in die erstellte Markdown-Datei ein:
```markdown
## Verwenden Sie den webforJ MCP-Server, um Fragen zu webforJ zu beantworten

- Rufen Sie immer das Tool "webforj-knowledge-base" auf, um relevante Dokumente zur Frage abzurufen
- Überprüfen Sie alle API-Signaturen anhand der offiziellen Dokumentation
- Gehen Sie niemals davon aus, dass Methodennamen oder Parameter existieren, ohne dies zu überprüfen

Überprüfen Sie immer, ob der Code mit `mvn compile` kompiliert, bevor Sie Vorschläge machen.
```

## FAQ

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>Warum verwendet die KI nicht den webforJ MCP-Server?</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Die meisten KI-Assistenten benötigen explizite Anweisungen, um MCP-Server zu verwenden. Konfigurieren Sie Ihren KI-Client mit den Anweisungen aus dem Abschnitt [KI-Anpassung](#ai-customization). Ohne diese Anweisungen könnten KI-Assistenten standardmäßig auf ihre Trainingsdaten zurückgreifen, anstatt den MCP-Server abzufragen.

      **Schnellbehebung:**
      Fügen Sie "verwenden Sie webforJ MCP" in Ihrer Eingabeaufforderung hinzu oder erstellen Sie die entsprechende Konfigurationsdatei (`.github/copilot-instructions.md` oder `CLAUDE.md`).
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>Wie überprüfe ich, ob die MCP-Verbindung funktioniert?</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Verwenden Sie den MCP-Inspektor, um Verbindungen zu debuggen:

      ```bash
      npx @modelcontextprotocol/inspector
      ```

      Warten Sie auf die Nachricht: `🔍 MCP Inspector ist aktiv und läuft unter http://127.0.0.1:6274` (Port kann variieren)

      Gehen Sie dann im Inspektor so vor:
      1. Geben Sie die MCP-Server-URL ein: `https://mcp.webforj.com/mcp`
      2. Klicken Sie auf "Verbinden", um die Verbindung herzustellen
      3. Anzeigen von verfügbaren Tools und Testanfragen
      4. Überwachen Sie Protokolle für Anfragen/Antworten zum Debugging
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
      - **SSE-Endpunkt** (`/sse`) - Server-Sent Events für ältere Clients wie Windsurf

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
      Ja, aber es wird nicht empfohlen. Ohne Konfigurationsdateien müssen Sie die KI manuell auffordern, den MCP-Server in jedem Gespräch zu verwenden. Konfigurationsdateien weisen die KI automatisch an, den MCP-Server für jede Interaktion zu verwenden, sodass Sie nicht bei jeder Anfrage Anweisungen wiederholen müssen.

      **Manueller Ansatz:**
      Beginnen Sie Eingabeaufforderungen mit: "Verwenden Sie den webforJ MCP-Server, um..."

      **Alternative: Verwenden Sie vordefinierte Eingabeaufforderungen**
      Der MCP-Server bietet Eingabeaufforderungen, die ohne Konfigurationsdateien funktionieren:
      - `/create-app` - Generieren Sie neue webforJ-Anwendungen
      - `/create-theme` - Erstellen Sie barrierefreie CSS-Themen
      - `/search-webforj` - Erweiterte Dokumentationssuche

      Weitere Einzelheiten finden Sie in den [verfügbaren Eingabeaufforderungen](#available-prompts).
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>Wie kann ich beitragen oder Probleme melden?</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      **Probleme melden:** [webforJ MCP Feedback](https://github.com/webforj/webforj-mcp-feedback/issues)

      **Häufige Probleme, die gemeldet werden müssen:**
      - Veraltete Dokumentation in den Suchergebnissen
      - Fehlende API-Methoden oder Komponenten
      - Falsche Codebeispiele
      - Fehler bei der Ausführung des Tools

      Geben Sie bei der Meldung von Problemen Ihre Anfrage, das erwartete Ergebnis und das tatsächliche Ergebnis an.
    </div>
  </AccordionDetails>
</Accordion>
<br />
