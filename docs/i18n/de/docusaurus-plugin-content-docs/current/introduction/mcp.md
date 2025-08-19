---
title: MCP Server
sidebar_position: 2.5
sidebar_class_name: new-content
_i18n_hash: caf6cb2973387f33706be4c4416a594c
---
Das webforJ Model Context Protocol (MCP) Server bietet KI-Assistenten direkten Zugang zur offiziellen webforJ-Dokumentation, verifizierten Codebeispielen und frameworkspezifischen Mustern, wodurch die Antworten präziser werden und eine automatisierte Projektgenerierung speziell für die webforJ-Entwicklung ermöglicht wird.

## Was ist ein MCP?

Das Model Context Protocol ist ein offener Standard, der KI-Assistenten die Verbindung zu externen Tools und Dokumentationen ermöglicht. Der webforJ MCP-Server implementiert dieses Protokoll, um zu bieten:

- **Wissenssuche** - Natürliche Sprachsuche in der webforJ-Dokumentation, Codebeispielen und Mustern
- **Projektgenerierung** - Erstellen von webforJ-Anwendungen aus offiziellen Vorlagen mit ordentlicher Struktur
- **Themenkreation** - Generieren von barrierefreien CSS-Themen gemäß webforJ-Designmustern

## Warum MCP verwenden?

Während KI-Coding-Assistenten gut darin sind, grundlegende Fragen zu beantworten, haben sie Schwierigkeiten mit komplexen, webspezifischen Anfragen, die mehrere Dokumentationsabschnitte umfassen. Ohne direkten Zugang zu offiziellen Quellen können sie:

- Methoden generieren, die in webforJ nicht existieren
- Veraltete oder falsche API-Muster referenzieren  
- Code bereitstellen, der nicht kompilierbar ist
- Die webforJ-Syntax mit anderen Java-Frameworks verwechseln
- webspezifische Muster missverstehen

Mit der MCP-Integration sind KI-Antworten an die tatsächliche webforJ-Dokumentation, Codebeispiele und Framework-Muster gebunden, die überprüfbare Antworten mit direkten Links zu offiziellen Quellen für tiefere Erkundungen bieten.

:::warning KI kann weiterhin Fehler machen
Obwohl MCP die Genauigkeit erheblich verbessert, indem es Zugang zu offiziellen webforJ-Ressourcen bietet, garantiert es nicht die perfekte Code-Generierung. KI-Assistenten können in komplexen Szenarien weiterhin Fehler machen. Überprüfen Sie immer den generierten Code und testen Sie gründlich, bevor Sie ihn in der Produktion verwenden.
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

Verwenden Sie den Claude CLI-Befehl, um den Server zu registrieren:

```bash
claude mcp add webforj-mcp https://mcp.webforj.com/mcp -t http -s user
```

Dies konfiguriert den MCP-Server automatisch in Ihrer Claude Code-Umgebung.

</TabItem>
<TabItem value="claude-desktop" label="Claude Desktop">

Fügen Sie diesen Server über das Integrationsfeld in den Einstellungen von Claude Desktop hinzu:

1. Öffnen Sie Claude Desktop und gehen Sie zu den Einstellungen
2. Klicken Sie im Sidebar auf "Integrationen"
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

Tools sind spezialisierte Funktionen, die der MCP-Server den KI-Assistenten bereitstellt. Wenn Sie eine Frage stellen oder eine Anfrage machen, kann die KI diese Tools nutzen, um Dokumentationen zu durchsuchen, Projekte zu generieren oder Themen zu erstellen. Jedes Tool akzeptiert spezifische Parameter und gibt strukturierte Daten zurück, die der KI helfen, genaue, kontextbewusste Unterstützung zu bieten.

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>webforj-knowledge-base</code></strong> - Dokumentation und Beispiele durchsuchen
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Dieses Tool ermöglicht semantische Suchfunktionen in der gesamten webforJ-Dokumentation. Es versteht den Kontext und die Beziehungen zwischen verschiedenen Konzepten des Frameworks und gibt relevante Dokumentationsabschnitte, API-Referenzen und funktionierende Codebeispiele zurück.

      **Beispielanfragen:**
      ```
      "Suche in der webforJ-Dokumentation nach Beispielen für die Schaltflächenkomponente mit Symbolen"

      "Finde Muster zur Validierung von webforJ-Formularen in der neuesten Dokumentation"

      "Zeige mir die aktuelle webforJ-Routing-Konfiguration mit @Route-Anmerkung"

      "Suche in den webforJ-Dokumenten nach flexiblen Layout-Designmustern"

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
      Erzeugt vollständige webforJ-Anwendungen mithilfe offizieller Maven-Archetypen. Das Tool erstellt ein standardisiertes Projektverzeichnislayout und enthält Beispielcode basierend auf der ausgewählten Vorlage. Generierte Projekte umfassen ein einsatzbereites Build-System, Ressourcenordner und Konfigurationsdateien für sofortige Entwicklung und Bereitstellung.

      **Beispielaufforderungen:**
      ```
      "Erstelle ein webforJ-Projekt mit dem Namen CustomerPortal mithilfe des hello-world-Archetyps"

      "Generiere ein webforJ-Spring-Boot-Projekt mit einem Tab-Layout namens Dashboard"

      "Erstelle eine neue webforJ-App mit dem sidemenu-Archetyp für das AdminPanel-Projekt"

      "Generiere ein leeres webforJ-Projekt mit dem Namen TestApp mit der groupId com.example"

      "Erstelle ein webforJ-Projekt namens InventorySystem mit dem sidemenu-Archetyp und Spring Boot"
      ```

      Bei der Verwendung dieses Tools können Sie aus mehreren Projektvorlagen auswählen:

      **Archetypen** (Projektvorlagen):
      - `hello-world` - Basis-App mit Beispielkomponenten zur Demonstration der webforJ-Funktionen
      - `blank` - Minimale Projektstruktur für den Start von Grund auf
      - `tabs` - Vorgefertigtes tabbed Interface-Layout für Multi-View-Anwendungen
      - `sidemenu` - Layout mit seitlicher Navigationsleiste für Administrationspanels oder Dashboards

      **Flavors** (Framework-Integration):
      - `webforj` - Standard-webforJ-App
      - `webforj-spring` - webforJ in Integration mit Spring Boot für Abhängigkeitsinjektion und Unternehmensmerkmale

      :::tip Verfügbare Archetypen
      webforJ kommt mit mehreren vordefinierten Archetypen, um Ihnen den Einstieg zu erleichtern. Eine vollständige Liste der verfügbaren Archetypen finden Sie im [Archetypen-Katalog](../building-ui/archetypes/overview).
      :::
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>webforj-create-theme</code></strong> - Erstellen von zugänglichen CSS-Themen
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Generiert webforJ-Themenkonfigurationen mithilfe von [DWC HueCraft](https://huecraft.dwc.style/). Das Tool erstellt vollständige CSS-Custom-Property-Sets mit Primär-, Sekundär-, Erfolgs-, Warn-, Gefahren- und neutralen Farbvarianten.

      **Beispielanfragen:**
      ```
      "Generiere ein webforJ-Thema mit HSL 220, 70, 50 als Primärfarbe für unsere Unternehmensmarke"

      "Erstelle ein barrierefreies webforJ-Thema mit dem Namen 'ocean' und der Primärfarbe #0066CC"

      "Generiere ein webforJ-Thema mit unserer Markenfarbe #FF5733"

      "Erstelle ein webforJ-Thema mit HSL 30, 100, 50 mit dem Namen 'sunset' für unsere App"

      "Generiere ein barrierefreies webforJ-Thema mit primärem RGB 44, 123, 229"
      ```
    </div>
  </AccordionDetails>
</Accordion>

## Verfügbare Aufforderungen {#available-prompts}

Aufforderungen sind vorkonfigurierte KI-Anweisungen, die mehrere Tools und Abläufe für häufige Aufgaben kombinieren. Sie führen die KI durch spezifische Schritte und Parameter, um zuverlässige, wiederholbare Ergebnisse für jeden unterstützten Ablauf zu liefern.

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>create-app</code></strong> - Erstelle und führe eine webforJ-App aus
  </AccordionSummary>
  <AccordionDetails>
    <div>
      **Argumente:**
      - `appName` (erforderlich) - Anwendungsname (z.B. MyApp, TodoList, Dashboard)
      - `archetype` (erforderlich) - Wählen Sie aus: `blank`, `hello-world`, `tabs`, `sidemenu`
      - `runServer` (optional) - Automatisch den Entwicklungsserver ausführen (ja/nein)
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>create-theme</code></strong> - Generiere ein webforJ-Thema aus einer Primärfarbe
  </AccordionSummary>
  <AccordionDetails>
    <div>
      **Argumente:**
      - `primaryColor` (erforderlich) - Farbe im Hex-Format (#FF5733), RGB (255,87,51) oder HSL (9,100,60)
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>search-webforj</code></strong> - Erweiterte Suche mit autonomer Problemlösung
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Die Aufforderung konfiguriert die KI, um:

      1. Umfassend in der Wissensdatenbank zu suchen
      2. Vollständigen, produktionsbereiten Code zu schreiben
      3. Das Projekt mit `mvn compile` zu kompilieren, um sicherzustellen, dass keine Build-Fehler vorhanden sind
      4. Fehler iterativ zu beheben, bis alles funktioniert
    </div>
  </AccordionDetails>
</Accordion>

### So verwenden Sie Aufforderungen

<Tabs groupId="ide">
<TabItem value="vscode" label="VS Code und Claude Code">

1. Geben Sie <kbd>/</kbd> im Chat ein, um verfügbare Aufforderungen zu sehen
2. Wählen Sie eine Aufforderung aus dem Dropdown-Menü aus
3. Füllen Sie die erforderlichen Parameter aus, wenn Sie dazu aufgefordert werden

</TabItem>
<TabItem value="claude-desktop" label="Claude Desktop">

1. Klicken Sie auf das **+** (Plus)-Symbol im Eingabebereich für Aufforderungen
2. Wählen Sie **"Von webforJ hinzufügen"** aus dem Menü
3. Wählen Sie die gewünschte Aufforderung (z.B. `create-app`, `create-theme`, `search-webforj`)
4. Claude wird Sie auffordern, die erforderlichen Argumente einzugeben
5. Füllen Sie die Parameter wie angefordert aus

:::tip Überprüfen Sie, ob MCP verbunden ist
Achten Sie auf das Werkzeugsymbol in der unteren Ecke des Eingabebereichs, um zu bestätigen, dass der webforJ MCP-Server verbunden ist.
:::

</TabItem>
</Tabs>

## Best Practices

Um die genaueste und aktuellste webforJ-Unterstützung zu erhalten, befolgen Sie diese Richtlinien, um die Funktionen des MCP-Servers vollständig zu nutzen.

### Sicherstellen der MCP-Servernutzung

KI-Modelle können den MCP-Server überspringen, wenn sie glauben, bereits die Antwort zu kennen. Um sicherzustellen, dass der MCP-Server tatsächlich verwendet wird:

- **Seien Sie explizit über webforJ**: Erwähnen Sie immer "webforJ" in Ihrer Anfrage, um frameworkspezifische Suchen auszulösen
- **Fordern Sie aktuelle Informationen an**: Fügen Sie Phrasen wie "neueste webforJ-Dokumentation" oder "aktuelle webforJ-Muster" hinzu
- **Bitten Sie um verifizierte Beispiele**: Fordern Sie "funktionierende webforJ-Codebeispiele" an, um die Dokumentationssuche zu erzwingen
- **Verweisen Sie auf spezifische Versionen**: Nennen Sie Ihre webforJ-Version (z.B. "webforJ `25.02`"), um genaue Ergebnisse zu erhalten

### Schreiben spezifischer Aufforderungen

**Gute Beispiele:**
```
"Suche in der webforJ-Dokumentation nach der Schaltflächenkomponente mit Ereignisbehandlung und Beispielen"

"Erstelle ein webforJ-Projekt namens InventorySystem mit dem sidemenu-Archetyp und Spring Boot"

"Generiere ein webforJ-Thema mit HSL 220, 70, 50 als Primärfarbe für die Unternehmensmarke"
```

**Schlechte Beispiele:**
```
"Wie funktionieren Buttons"

"Mach eine App"

"Mach es blau"
```

### Erzwingen der MCP-Toolnutzung

Wenn die KI generische Antworten gibt, ohne den MCP-Server zu nutzen:

1. **Klar anfordern**: "Nutze den webforJ MCP-Server, um nach `[Anfrage]` zu suchen"
2. **Fragen Sie nach Dokumentationsreferenzen**: "Finde in der webforJ-Dokumentation heraus, wie man `[Anfrage]` macht"
3. **Bitte um Verifizierung**: "Überprüfe diese Lösung anhand der webforJ-Dokumentation"
4. **Seien Sie frameworkspezifisch**: Fügen Sie immer "webforJ" in Ihre Anfragen ein

## KI-Anpassung {#ai-customization}

Konfigurieren Sie Ihre KI-Assistenten so, dass sie automatisch den MCP-Server verwenden und den besten Praktiken von webforJ folgen. Fügen Sie projektspezifische Anweisungen hinzu, damit Ihre KI-Assistenten immer den MCP-Server verwenden, den Standards der webforJ-Dokumentation folgen und genaue, aktuelle Antworten liefern, die den Anforderungen Ihres Teams entsprechen.

### Projektkonfigurationsdateien

- Für **VS Code und Copilot**, erstellen Sie `.github/copilot-instructions.md`
- Für **Claude Code**, erstellen Sie `CLAUDE.md` im Stammverzeichnis Ihres Projekts

Fügen Sie Folgendes in die erstellte Markdown-Datei ein:
```markdown
## Verwenden Sie den webforJ MCP-Server, um alle Fragen zu webforJ zu beantworten

- Rufen Sie immer das Tool "webforj-knowledge-base" auf, um relevante Dokumente zu der Frage abzurufen
- Überprüfen Sie alle API-Signaturen anhand der offiziellen Dokumentation
- Gehen Sie niemals davon aus, dass Methode Namen oder Parameter existieren, ohne zu überprüfen

Überprüfen Sie immer, ob der Code mit `mvn compile` kompiliert, bevor Sie Vorschläge machen.
```

## FAQ

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>Warum verwendet die KI nicht den webforJ MCP-Server?</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Die meisten KI-Assistenten benötigen ausdrückliche Anweisungen, um MCP-Server zu verwenden. Konfigurieren Sie Ihren KI-Client mit den Anweisungen aus dem Abschnitt [KI-Anpassung](#ai-customization). Ohne diese Anweisungen können KI-Assistenten möglicherweise standardmäßig auf ihre Trainingsdaten zurückgreifen, anstatt den MCP-Server abzufragen.

      **Schnelle Lösung:**
      Fügen Sie "verwenden Sie webforJ MCP" in Ihre Aufforderung ein oder erstellen Sie die entsprechende Konfigurationsdatei (`.github/copilot-instructions.md` oder `CLAUDE.md`).
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>Wie überprüfe ich, ob die MCP-Verbindung funktioniert</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Verwenden Sie den MCP-Inspektor, um Verbindungen zu debuggen:

      ```bash
      npx @modelcontextprotocol/inspector
      ```

      Warten Sie auf die Nachricht: `🔍 MCP Inspector läuft und ist unter http://127.0.0.1:6274` (Port kann variieren)

      Geben Sie dann im Inspektor Folgendes ein:
      1. MCP-Server-URL: `https://mcp.webforj.com/mcp`
      2. Klicken Sie auf "Verbinden", um die Verbindung herzustellen
      3. Sehen Sie sich verfügbare Tools an und testen Sie Abfragen
      4. Überwachen Sie die Anfrage-/Antwortprotokolle zur Debugging-Zwecken
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
      Ja, aber es wird nicht empfohlen. Ohne Konfigurationsdateien müssen Sie die KI manuell auffordern, den MCP-Server in jedem Gespräch zu nutzen. Konfigurationsdateien geben der KI automatisch Anweisungen, den MCP-Server für jede Interaktion zu verwenden, sodass Sie nicht bei jedem Mal wiederholen müssen.

      **Manueller Ansatz:**
      Beginnen Sie Aufforderungen mit: "Verwenden Sie den webforJ MCP-Server zu..."

      **Alternativ: Verwenden Sie vorkonfigurierte Aufforderungen**
      Der MCP-Server bietet Aufforderungen, die ohne Konfigurationsdateien funktionieren:
      - `/create-app` - Generieren Sie neue webforJ-Anwendungen
      - `/create-theme` - Erstellen Sie zugängliche CSS-Themen
      - `/search-webforj` - Erweiterte Dokumentationssuche

      Siehe [Verfügbare Aufforderungen](#available-prompts) für Details.
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>Wie kann ich beitragen oder Probleme melden</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      **Probleme melden:** [webforJ MCP Feedback](https://github.com/webforj/webforj-mcp-feedback/issues)
      
      **Häufige Probleme zur Meldung:**
      - Veraltete Dokumentation in Suchergebnissen
      - Fehlende API-Methoden oder Komponenten
      - Falsche Codebeispiele
      - Fehler beim Ausführen des Tools

      Geben Sie bei der Meldung von Problemen Ihre Anfrage, das erwartete Ergebnis und das tatsächliche Ergebnis an.
    </div>
  </AccordionDetails>
</Accordion>
<br />
