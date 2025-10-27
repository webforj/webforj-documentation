---
title: MCP Server
sidebar_position: 2.5
sidebar_class_name: new-content
_i18n_hash: cfe1c4447876aff3ab7ba15b26966cba
---
Der webforJ Model Context Protocol (MCP) Server bietet KI-Assistenten direkten Zugang zu offiziellen webforJ-Dokumentationen, verifizierten Codebeispielen und rahmenspezifischen Mustern, wodurch Antworten mit genaueren Antworten und automatisierter Projektgenerierung speziell für die webforJ-Entwicklung ermöglicht werden.

## Was ist ein MCP?

Das Model Context Protocol ist ein offener Standard, der es KI-Assistenten ermöglicht, sich mit externen Tools und Dokumentationen zu verbinden. Der webforJ MCP-Server implementiert dieses Protokoll, um Folgendes bereitzustellen:

- **Wissenssuche** - Natürliche Sprachsuche über die webforJ-Dokumentation, Codebeispiele und Muster
- **Projekterstellung** - Erstellen von webforJ-Anwendungen aus offiziellen Vorlagen mit korrekter Struktur
- **Theme-Erstellung** - Generieren von barrierefreien CSS-Themen nach den Designmustern von webforJ

## Warum MCP verwenden?

Während KI-Coding-Assistenten bei der Beantwortung grundlegender Fragen hervorragend sind, haben sie Schwierigkeiten mit komplexen, webforJ-spezifischen Anfragen, die mehrere Dokumentationsabschnitte umfassen. Ohne direkten Zugriff auf offizielle Quellen können sie:

- Methoden generieren, die in webforJ nicht existieren
- Veraltete oder falsche API-Muster referenzieren
- Code bereitstellen, der nicht kompiliert
- Die Syntax von webforJ mit anderen Java-Frameworks verwechseln
- webforJ-spezifische Muster missverstehen

Mit der MCP-Integration sind KI-Antworten an tatsächliche webforJ-Dokumentationen, Codebeispiele und Rahmenmuster gebunden, die überprüfbare Antworten mit direkten Links zu offiziellen Quellen für eine tiefere Erkundung bieten.

:::warning KI kann immer noch Fehler machen
Obwohl MCP die Genauigkeit erheblich verbessert, indem es Zugriff auf offizielle webforJ-Ressourcen bietet, garantiert es nicht perfekte Codegenerierung. KI-Assistenten können in komplexen Szenarien weiterhin Fehler machen. Überprüfen Sie immer den generierten Code und testen Sie gründlich, bevor Sie ihn in der Produktion verwenden.
:::

## Installation

Der webforJ MCP-Server wird unter `https://mcp.webforj.com` mit zwei Endpunkten gehostet:

- **MCP-Endpunkt** (`/mcp`) - Für Claude, VS Code, Cursor
- **SSE-Endpunkt** (`/sse`) - Für ältere Clients

<Tabs groupId="ide">
<TabItem value="vscode" label="VS Code">

Fügen Sie diese Konfiguration zu Ihrer settings.json-Datei von VS Code hinzu:

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

Dies konfiguriert automatisch den MCP-Server in Ihrer Claude Code-Umgebung.

</TabItem>
<TabItem value="claude-desktop" label="Claude Desktop">

Fügen Sie diesen Server über das Integrationsfeld in den Einstellungen von Claude Desktop hinzu:

1. Öffnen Sie Claude Desktop und gehen Sie zu Einstellungen
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

Tools sind spezialisierte Funktionen, die der MCP-Server den KI-Assistenten bereitstellt. Wenn Sie eine Frage stellen oder eine Anfrage machen, kann die KI diese Tools aufrufen, um Dokumentationen zu durchsuchen, Projekte zu generieren oder Themes zu erstellen. Jedes Tool akzeptiert spezifische Parameter und gibt strukturierte Daten zurück, die der KI helfen, genaue, kontextbewusste Unterstützung zu bieten.

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>webforj-knowledge-base</code></strong> - Dokumentation und Beispiele durchsuchen
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Dieses Tool bietet semantische Suchfunktionen über das gesamte webforJ-Dokumentationsökosystem. Es versteht den Kontext und die Beziehungen zwischen verschiedenen Rahmenkonzepten und gibt relevante Dokumentationsabschnitte, API-Referenzen und funktionierende Codebeispiele zurück.

      **Beispielanfragen:**
      ```
      "Durchsuche die webforJ-Dokumentation nach Button-Komponenten mit Icon-Beispielen"

      "Finde webforJ-Formvalidierungsmuster in der neuesten Dokumentation"

      "Zeig mir die aktuelle webforJ-Routing-Konfiguration mit @Route-Annotation"

      "Durchsuche die webforJ-Dokumente nach FlexLayout-responsiven Design-Mustern"

      "Finde die Integration von webforJ-Web-Komponenten in der offiziellen Dokumentation"
      ```
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>webforj-create-project</code></strong> - Generiere neue webforJ-Projekte  
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Scaffoldet vollständige webforJ-Anwendungen mit offiziellen Maven-Archetypen. Das Tool erstellt ein standardisiertes Projektverzeichnislayout und enthält Startercode basierend auf der ausgewählten Vorlage. Generierte Projekte umfassen ein sofort einsatzbereites Build-System, Ressourcenordner und Konfigurationsdateien für die sofortige Entwicklung und Bereitstellung.

      **Beispielaufforderungen:**
      ```
      "Erstelle ein webforJ-Projekt namens CustomerPortal mit dem hello-world-Archetyp"

      "Generiere ein webforJ Spring Boot-Projekt mit Tab-Layout namens Dashboard"

      "Erstelle eine neue webforJ-App mit dem sidemenu-Archetyp für das AdminPanel-Projekt"

      "Generiere ein leeres webforJ-Projekt namens TestApp mit com.example groupId"

      "Erstelle ein webforJ-Projekt InventorySystem mit dem sidemenu-Archetyp und Spring Boot"
      ```

      Bei Verwendung dieses Tools können Sie aus mehreren Projektvorlagen auswählen:

      **Archetypen** (Projektvorlagen):
      - `hello-world` - Basis-App mit Beispielkomponenten zur Demonstration der webforJ-Funktionen
      - `blank` - Minimaler Projektstruktur zum Starten von Grund auf
      - `tabs` - Vorgefertigtes Layout für tabbed interfaces für Multi-View-Anwendungen
      - `sidemenu` - Layout mit seitlichem Navigationsmenü für Administrationspanels oder Dashboards

      **Flavors** (Framework-Integration):
      - `webforj` - Standard-webforJ-App
      - `webforj-spring` - webforJ integriert mit Spring Boot für Dependency Injection und Enterprise-Funktionen

      :::tip Verfügbare Archetypen
      webforJ bietet mehrere vordefinierte Archetypen, um Ihnen einen schnellen Einstieg zu ermöglichen. Für eine vollständige Liste der verfügbaren Archetypen siehe den [Archetypen-Katalog](../building-ui/archetypes/overview).
      :::
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>webforj-create-theme</code></strong> - Erstelle barrierefreie CSS-Themen
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Generiert webforJ-Themenkonfigurationen mit [DWC HueCraft](https://huecraft.dwc.style/). Das Tool erstellt vollständige CSS-Custom-Property-Sets mit primären, sekundären, Erfolg-, Warn-, Gefahren- und neutralen Farbvarianten.

      **Beispielanfragen:**
      ```
      "Generiere ein webforJ-Thema mit HSL 220, 70, 50 als Primärfarbe für unsere Unternehmensmarke"

      "Erstelle ein webforJ-barrierefreies Thema namens 'ocean' mit der Primärfarbe #0066CC"

      "Generiere ein webforJ-Thema mit unserer Markenfarbe #FF5733"

      "Erstelle ein webforJ-Thema mit HSL 30, 100, 50 namens 'sunset' für unsere App"

      "Generiere ein barrierefreies webforJ-Thema mit primärem RGB 44, 123, 229"
      ```
    </div>
  </AccordionDetails>
</Accordion>

## Verfügbare Aufforderungen {#available-prompts}

Aufforderungen sind vordefinierte KI-Anweisungen, die mehrere Tools und Workflows für häufige Aufgaben kombinieren. Sie leiten die KI durch spezifische Schritte und Parameter, um zuverlässige, reproduzierbare Ergebnisse für jeden unterstützten Workflow zu liefern.

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>create-app</code></strong> - Erstelle und führe eine webforJ-App aus
  </AccordionSummary>
  <AccordionDetails>
    <div>
      **Argumente:**
      - `appName` (erforderlich) - Anwendungsname (z.B. MyApp, TodoList, Dashboard)
      - `archetype` (erforderlich) - Wählen Sie aus: `blank`, `hello-world`, `tabs`, `sidemenu`
      - `runServer` (optional) - Entwicklungserver automatisch ausführen (ja/nein)
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
      - `primaryColor` (erforderlich) - Farbe im Hex-Format (#FF5733), rgb (255,87,51) oder hsl (9,100,60)
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

      1. Die Wissensdatenbank umfassend zu durchsuchen
      2. Vollständigen, produktionsreifen Code zu schreiben
      3. Das Projekt mit `mvn compile` zu kompilieren, um sicherzustellen, dass es keine Build-Fehler gibt
      4. Fehler iterativ zu beheben, bis alles funktioniert
    </div>
  </AccordionDetails>
</Accordion>

### So verwenden Sie Aufforderungen

<Tabs groupId="ide">
<TabItem value="vscode" label="VS Code und Claude Code">

1. Geben Sie <kbd>/</kbd> im Chat ein, um verfügbare Aufforderungen zu sehen
2. Wählen Sie eine Aufforderung aus dem Dropdown-Menü
3. Füllen Sie die erforderlichen Parameter aus, wenn Sie dazu aufgefordert werden

</TabItem>
<TabItem value="claude-desktop" label="Claude Desktop">

1. Klicken Sie auf das **+** (Plus)-Symbol im Eingabebereich für Aufforderungen
2. Wählen Sie **"Von webforJ hinzufügen"** aus dem Menü
3. Wählen Sie die gewünschte Aufforderung (z.B. `create-app`, `create-theme`, `search-webforj`)
4. Claude wird Sie auffordern, die erforderlichen Argumente einzugeben
5. Füllen Sie die Parameter aus, wie angefordert

:::tip Überprüfen Sie, ob MCP verbunden ist
Suchen Sie nach dem Symbol für Tools in der unteren Ecke des Eingabebereichs, um zu bestätigen, dass der webforJ MCP-Server verbunden ist.
:::

</TabItem>
</Tabs>

## Beste Praktiken

Um die genauesten und aktuellsten webforJ-Hilfen zu erhalten, befolgen Sie diese Richtlinien, um die Funktionen des MCP-Servers optimal zu nutzen.

### Sicherstellung der Nutzung des MCP-Servers

KI-Modelle können den MCP-Server überspringen, wenn sie der Meinung sind, sie wüssten bereits die Antwort. Um sicherzustellen, dass der MCP-Server tatsächlich verwendet wird:

- **Seien Sie explizit bezüglich webforJ**: Erwähnen Sie immer "webforJ" in Ihrer Anfrage, um rahmenspezifische Suchen auszulösen
- **Fordern Sie aktuelle Informationen an**: Verwenden Sie Ausdrücke wie "neueste webforJ-Dokumentation" oder "aktuelle webforJ-Muster"
- **Bitten Sie um verifizierte Beispiele**: Fordern Sie "funktionierende webforJ-Codebeispiele" an, um eine Dokumentationssuche zu erzwingen
- **Referenzieren Sie spezifische Versionen**: Nennen Sie Ihre webforJ-Version (z.B. "webforJ `25.02`"), um genaue Ergebnisse zu erhalten

### Schreiben spezifischer Aufforderungen

**Gute Beispiele:**
```
"Durchsuche die webforJ-Dokumentation nach Button-Komponenten-Event-Handling mit Beispielen"

"Erstelle ein webforJ-Projekt namens InventorySystem mit dem sidemenu-Archetyp und Spring Boot"

"Generiere ein webforJ-Thema mit HSL 220, 70, 50 als Primärfarbe für die Unternehmensmarke"
```

**Schlechte Beispiele:**
```
"Wie funktionieren Buttons"

"Mach eine App"

"Mach es blau"
```

### Erzwingen der Nutzung von MCP-Tools

Wenn die KI generische Antworten gibt, ohne den MCP-Server zu verwenden:

1. **Fordern Sie ausdrücklich an**: "Verwende den webforJ MCP-Server, um nach `[query]` zu suchen"
2. **Bitten Sie um Dokumentationsreferenzen**: "Finde in den webforJ-Dokumenten, wie man `[query]`"
3. **Fordern Sie eine Verifizierung an**: "Überprüfe diese Lösung anhand der webforJ-Dokumentation"
4. **Seien Sie rahmenspezifisch**: Fügen Sie immer "webforJ" in Ihre Abfragen ein

## KI-Anpassung {#ai-customization}

Konfigurieren Sie Ihre KI-Assistenten so, dass sie automatisch den MCP-Server verwenden und den besten Praktiken von webforJ folgen. Fügen Sie projektspezifische Anweisungen hinzu, damit Ihre KI-Assistenten immer den MCP-Server verwenden, die Standards der webforJ-Dokumentation befolgen und genaue, aktuelle Antworten geben, die den Anforderungen Ihres Teams entsprechen.

### Projektkonfigurationsdateien

- Für **VS Code und Copilot** erstellen Sie `.github/copilot-instructions.md`
- Für **Claude Code** erstellen Sie `CLAUDE.md` im Stammverzeichnis Ihres Projekts

Fügen Sie Folgendes in die erstellte Markdown-Datei ein:
```markdown
## Verwenden Sie den webforJ MCP-Server, um alle webforJ-Fragen zu beantworten

- Rufen Sie immer das Tool "webforj-knowledge-base" auf, um relevante Dokumente zur Frage abzurufen
- Überprüfen Sie alle API-Signaturen anhand der offiziellen Dokumentation
- Gehen Sie niemals davon aus, dass Methodennamen oder Parameter existieren, ohne dies zu überprüfen

Überprüfen Sie immer, ob der Code mit `mvn compile` kompiliert, bevor Sie ihn vorschlagen.
```

## FAQ

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>Warum verwendet die KI den webforJ MCP-Server nicht?</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Die meisten KI-Assistenten benötigen explizite Anweisungen, um MCP-Server zu verwenden. Konfigurieren Sie Ihren KI-Client mit den Anweisungen aus dem Abschnitt [KI-Anpassung](#ai-customization). Ohne diese Anweisungen können KI-Assistenten dazu neigen, auf ihre Trainingsdaten zurückzugreifen, anstatt den MCP-Server abzufragen.

      **Schnelle Lösung:**
      Fügen Sie "verwende webforJ MCP" in Ihre Aufforderung ein oder erstellen Sie die entsprechende Konfigurationsdatei (`.github/copilot-instructions.md` oder `CLAUDE.md`).
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

      Warten Sie auf die Nachricht: `🔍 MCP Inspector ist jetzt unter http://127.0.0.1:6274 aktiv` (der Port kann variieren)

      Geben Sie dann im Inspektor Folgendes ein:
      1. Geben Sie die MCP-Server-URL ein: `https://mcp.webforj.com/mcp`
      2. Klicken Sie auf "Verbinden", um die Verbindung herzustellen
      3. Sehen Sie sich verfügbare Tools an und testen Sie Abfragen
      4. Überwachen Sie Anfrage-/Antwortprotokolle zur Fehlersuche
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
      - **SSE-Endpunkt** (`/sse`) - Server-sent Events für ältere Clients wie Windsurf

      Die meisten Benutzer sollten den MCP-Endpunkt nutzen. Verwenden Sie SSE nur, wenn Ihr Client das Standardprotokoll MCP nicht unterstützt.
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>Ist es möglich, den MCP-Server ohne Konfigurationsdateien zu verwenden?</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Ja, aber es wird nicht empfohlen. Ohne Konfigurationsdateien müssen Sie die KI manuell auffordern, den MCP-Server in jedem Gespräch zu verwenden. Konfigurationsdateien geben der KI automatisch Anweisungen, den MCP-Server für jede Interaktion zu verwenden, sodass Sie nicht bei jeder Anfrage Anweisungen wiederholen müssen.

      **Manueller Ansatz:**
      Beginnen Sie Aufforderungen mit: "Verwenden Sie den webforJ MCP-Server, um..."

      **Alternative: Verwenden Sie vorgefertigte Aufforderungen**
      Der MCP-Server bietet Aufforderungen, die ohne Konfigurationsdateien funktionieren:
      - `/create-app` - Generiere neue webforJ-Anwendungen
      - `/create-theme` - Erstelle barrierefreie CSS-Themen
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
      **Probleme melden:** [webforJ MCP-Issue-Template](https://github.com/webforj/webforj/issues/new?template=mcp_report.yml)
      
      **Häufige Probleme, die gemeldet werden sollten:**
      - Veraltete Dokumentation in den Suchergebnissen
      - Fehlende API-Methoden oder Komponenten
      - Falsche Codebeispiele
      - Fehler bei der Ausführung von Tools

      Fügen Sie Ihrer Anfrage, dem erwarteten Ergebnis und dem tatsächlichen Ergebnis beim Melden von Problemen hinzu.
    </div>
  </AccordionDetails>
</Accordion>
<br />
