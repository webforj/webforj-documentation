---
title: MCP Server
sidebar_position: 2.5
sidebar_class_name: new-content
_i18n_hash: 7b656643222d616e7c44d14ed1de7bd3
---
Der webforJ Model Context Protocol (MCP) Server bietet KI-Assistenten direkten Zugriff auf die offizielle webforJ-Dokumentation, verifizierte Codebeispiele und frameworkspezifische Muster, was genauere Antworten und automatisierte Projektgenerierung speziell für die webforJ-Entwicklung ermöglicht.

## Was ist ein MCP?

Das Model Context Protocol ist ein offener Standard, der es KI-Assistenten ermöglicht, sich mit externen Tools und Dokumentationen zu verbinden. Der webforJ MCP-Server implementiert dieses Protokoll, um Folgendes bereitzustellen:

- **Wissenssuche** - Suche in natürlicher Sprache in der webforJ-Dokumentation, Codebeispielen und Mustern
- **Projektgenerierung** - Erstellen von webforJ-Anwendungen aus offiziellen Vorlagen mit der richtigen Struktur
- **Theme-Erstellung** - Generierung barrierefreier CSS-Themen gemäß webforJ-Designmustern

## Warum MCP verwenden?

Während KI-Programmierassistenten in der Beantwortung grundlegender Fragen hervorragend abschneiden, haben sie Schwierigkeiten mit komplexen, webforJ-spezifischen Abfragen, die mehrere Dokumentationsabschnitte umfassen. Ohne direkten Zugriff auf offizielle Quellen können sie:

- Methoden generieren, die in webforJ nicht existieren
- Veraltete oder falsche API-Muster referenzieren
- Code bereitstellen, der nicht kompiliert
- Die webforJ-Syntax mit anderen Java-Frameworks verwechseln
- webforJ-spezifische Muster missverstehen

Mit der MCP-Integration sind KI-Antworten an tatsächliche webforJ-Dokumentation, Codebeispiele und Framework-Muster gebunden, was verifizierbare Antworten mit direkten Links zu offiziellen Quellen für tiefere Erkundungen bietet.

:::warning KI kann immer noch Fehler machen
Obwohl MCP die Genauigkeit erheblich verbessert, indem es Zugriff auf offizielle webforJ-Ressourcen bietet, garantiert es keine perfekte Codegenerierung. KI-Assistenten können in komplexen Szenarien weiterhin Fehler machen. Überprüfen Sie immer den generierten Code und testen Sie gründlich, bevor Sie ihn in der Produktion verwenden.
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

Verwenden Sie den CLI-Befehl von Claude, um den Server zu registrieren:

```bash
claude mcp add webforj-mcp https://mcp.webforj.com/mcp -t http -s user
```

Damit wird der MCP-Server automatisch in Ihrer Claude Code-Umgebung konfiguriert.

</TabItem>
<TabItem value="claude-desktop" label="Claude Desktop">

Fügen Sie diesen Server über das Integrationen-Panel in den Claude Desktop-Einstellungen hinzu:

1. Öffnen Sie Claude Desktop und gehen Sie zu den Einstellungen
2. Klicken Sie in der Seitenleiste auf "Integrationen"
3. Klicken Sie auf "Integration hinzufügen" und fügen Sie die URL ein: `https://mcp.webforj.com/mcp`
4. Befolgen Sie den Einrichtungsassistenten, um die Konfiguration abzuschließen

Für detaillierte Anweisungen siehe das [offizielle Integrationshandbuch](https://support.anthropic.com/en/articles/11175166-about-custom-integrations-using-remote-mcp).

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

Tools sind spezialisierte Funktionen, die der MCP-Server den KI-Assistenten bietet. Wenn Sie eine Frage stellen oder eine Anfrage machen, kann die KI diese Tools aufrufen, um Dokumentation zu durchsuchen, Projekte zu generieren oder Themes zu erstellen. Jedes Tool akzeptiert spezifische Parameter und gibt strukturierte Daten zurück, die der KI helfen, genaue, kontextbewusste Unterstützung zu bieten.

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>webforj-wissen-datenbank</code></strong> - Dokumentation und Beispiele durchsuchen
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Dieses Tool bietet semantische Suchfähigkeiten über das gesamte webforJ-Dokumentationsecosystem. Es versteht den Kontext und die Beziehungen zwischen verschiedenen Framework-Konzepten und gibt relevante Dokumentationsabschnitte, API-Referenzen und funktionierende Codebeispiele zurück.

      **Beispielabfragen:**
      ```
      "Durchsuche die webforJ-Dokumentation nach Button-Komponenten mit Icon-Beispielen"

      "Finde webforJ-Formularvalidierungsmuster in der neuesten Dokumentation"

      "Zeige mir die aktuelle webforJ-Routing-Konfiguration mit @Route-Annotation"

      "Durchsuche die webforJ-Dokumente nach FlexLayout-Responsive-Designmustern"

      "Finde die Integration von webforJ-Webkomponenten in der offiziellen Dokumentation"
      ```
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>webforj-erstellen-projekt</code></strong> - Neue webforJ-Projekte generieren  
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Erzeugt vollständige webforJ-Anwendungen mithilfe offizieller Maven-Archetypen. Das Tool erstellt ein standardisiertes Projektverzeichnislayout und enthält Startercode basierend auf der ausgewählten Vorlage. Generierte Projekte umfassen ein gebrauchsfertiges Build-System, Ressourcenordner und Konfigurationsdateien für die sofortige Entwicklung und Bereitstellung.

      **Beispielaufforderungen:**
      ```
      "Erstelle ein webforJ-Projekt namens CustomerPortal mit dem hello-world-Archetyp"

      "Generiere ein webforJ-Spring-Boot-Projekt mit Tab-Layout namens Dashboard"

      "Erstelle eine neue webforJ-App mit dem Sidemenu-Archetyp für das Projekt AdminPanel"

      "Generiere ein leeres webforJ-Projekt namens TestApp mit com.example groupId"

      "Erstelle das webforJ-Projekt InventorySystem mit dem Sidemenu-Archetyp und Spring Boot"
      ```

      Wenn Sie dieses Tool verwenden, können Sie aus mehreren Projektvorlagen wählen:

      **Archetypen** (Projektvorlagen):
      - `hello-world` - Basis-App mit Beispielkomponenten zur Demonstration der webforJ-Funktionen
      - `blank` - Minimale Projektstruktur für den Start von Grund auf
      - `tabs` - Vorgefertigte Tab-Oberflächenlayout für Multi-View-Anwendungen
      - `sidemenu` - Layout für ein seitliches Navigationsmenü für Administratorenpanels oder Dashboards

      **Flavors** (Framework-Integration):
      - `webforj` - Standard-webforJ-App
      - `webforj-spring` - webforJ integriert mit Spring Boot für Dependency Injection und Enterprise-Funktionen

      :::tip Verfügbare Archetypen
      webforJ wird mit mehreren vordefinierten Archetypen geliefert, um Ihnen den schnellen Einstieg zu erleichtern. Für eine vollständige Liste der verfügbaren Archetypen siehe den [Archetypen-Katalog](../building-ui/archetypes/overview).
      :::
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>webforj-erstellen-theme</code></strong> - Erstelle barrierefreie CSS-Themen
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Generiert webforJ-Theme-Konfigurationen mithilfe von [DWC HueCraft](https://huecraft.dwc.style/). Das Tool erstellt vollständige CSS-Custom-Property-Sets mit primären, sekundären, Erfolgs-, Warn-, Gefahren- und neutralen Farbvarianten.

      **Beispielanfragen:**
      ```
      "Generiere ein webforJ-Theme mit HSL 220, 70, 50 als Primärfarbe für unsere Unternehmensmarke"

      "Erstelle ein barrierefreies webforJ-Theme namens 'ocean' mit der Primärfarbe #0066CC"

      "Generiere ein webforJ-Theme mit unserer Markenfarbe #FF5733"

      "Erstelle ein webforJ-Theme mit HSL 30, 100, 50, das 'sunset' für unsere App genannt wird"

      "Generiere ein barrierefreies webforJ-Theme mit primär RGB 44, 123, 229"
      ```
    </div>
  </AccordionDetails>
</Accordion>

## Verfügbare Aufforderungen {#available-prompts}

Aufforderungen sind vorkonfigurierte KI-Anweisungen, die mehrere Tools und Arbeitsabläufe für häufige Aufgaben kombinieren. Sie führen die KI durch spezifische Schritte und Parameter, um zuverlässige, wiederholbare Ergebnisse für jeden unterstützten Arbeitsablauf zu liefern.

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>app-erstellen</code></strong> - Erstelle und führe eine webforJ-App aus
  </AccordionSummary>
  <AccordionDetails>
    <div>
      **Argumente:**
      - `appName` (erforderlich) - Anwendungsname (z.B. MyApp, TodoList, Dashboard)
      - `archetyp` (erforderlich) - Wählen Sie aus: `blank`, `hello-world`, `tabs`, `sidemenu`
      - `runServer` (optional) - Entwicklungsserver automatisch ausführen (ja/nein)
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>theme-erstellen</code></strong> - Generiere ein webforJ-Theme aus einer Primärfarbe
  </AccordionSummary>
  <AccordionDetails>
    <div>
      **Argumente:**
      - `primaryColor` (erforderlich) - Farbe im hex (#FF5733), rgb (255,87,51) oder hsl (9,100,60) Format
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
      2. Vollständigen, produktionsbereiten Code zu schreiben
      3. Das Projekt mit `mvn compile` zu kompilieren, um sicherzustellen, dass keine Build-Fehler vorliegen
      4. Fehler iterativ zu beheben, bis alles funktioniert
    </div>
  </AccordionDetails>
</Accordion>

### So verwenden Sie Aufforderungen

<Tabs groupId="ide">
<TabItem value="vscode" label="VS Code und Claude Code">

1. Tippen Sie <kbd>/</kbd> im Chat, um die verfügbaren Aufforderungen anzuzeigen
2. Wählen Sie eine Aufforderung aus dem Dropdown-Menü aus
3. Füllen Sie die erforderlichen Parameter aus, wenn Sie dazu aufgefordert werden

</TabItem>
<TabItem value="claude-desktop" label="Claude Desktop">

1. Klicken Sie auf das **+** (Plus)-Symbol im Eingabebereich für Aufforderungen
2. Wählen Sie **"Von webforJ hinzufügen"** aus dem Menü
3. Wählen Sie die gewünschte Aufforderung aus (z.B. `app-erstellen`, `theme-erstellen`, `search-webforj`)
4. Claude fordert Sie auf, die erforderlichen Argumente einzugeben
5. Füllen Sie die Parameter wie angefordert aus

:::tip MCP-Verbindung überprüfen
Achten Sie auf das Toolsymbol in der unteren Ecke des Eingabebereichs, um zu bestätigen, dass der webforJ MCP-Server verbunden ist.
:::

</TabItem>
</Tabs>

## Beste Praktiken

Um die genaueste und aktuellste webforJ-Unterstützung zu erhalten, befolgen Sie diese Richtlinien, um die Funktionen des MCP-Servers optimal zu nutzen.

### Sicherstellen der Nutzung des MCP-Servers

KI-Modelle könnten den MCP-Server überspringen, wenn sie glauben, die Antwort bereits zu kennen. Um sicherzustellen, dass der MCP-Server tatsächlich verwendet wird:

- **Seien Sie explizit über webforJ**: Nennen Sie immer "webforJ" in Ihrer Anfrage, um frameworkspezifische Suchen auszulösen
- **Fragen Sie nach aktuellen Informationen**: Beziehen Sie Phrasen wie "neueste webforJ-Dokumentation" oder "aktuelle webforJ-Muster" ein
- **Bitten Sie um verifizierte Beispiele**: Fordern Sie "funktionierende webforJ-Codebeispiele" an, um die Dokumentationssuche zu erzwingen
- **Verweisen Sie auf spezifische Versionen**: Erwähnen Sie Ihre webforJ-Version (z.B. "webforJ `25.02`"), um genaue Ergebnisse zu erhalten

### Schreiben spezifischer Aufforderungen

**Gute Beispiele:**
```
"Durchsuche die webforJ-Dokumentation nach Eventbehandlung von Button-Komponenten mit Beispielen"

"Erstelle ein webforJ-Projekt namens InventorySystem mit dem Sidemenu-Archetyp und Spring Boot"

"Generiere ein webforJ-Theme mit HSL 220, 70, 50 als Primärfarbe für die Unternehmensmarke"
```

**Schlechte Beispiele:**
```
"Wie funktionieren Buttons"

"Erstelle eine App"

"Mach es blau"
```

### Erzwingen der Nutzung von MCP-Tools

Wenn die KI generische Antworten gibt, ohne den MCP-Server zu nutzen:

1. **Fordern Sie ausdrücklich an**: "Verwenden Sie den webforJ MCP-Server, um nach `[Anfrage]` zu suchen"
2. **Fragen Sie nach Dokumentationsreferenzen**: "Finden Sie in den webforJ-Dokumenten heraus, wie man `[Anfrage]` macht"
3. **Bitten Sie um Verifizierung**: "Überprüfen Sie diese Lösung anhand der webforJ-Dokumentation"
4. **Seien Sie frameworkspezifisch**: Nennen Sie immer "webforJ" in Ihren Anfragen

## KI-Anpassung {#ai-customization}

Konfigurieren Sie Ihre KI-Assistenten so, dass sie automatisch den MCP-Server verwenden und den besten webforJ-Praktiken folgen. Fügen Sie projektspezifische Anweisungen hinzu, damit Ihre KI-Assistenten immer den MCP-Server nutzen, den Standards der webforJ-Dokumentation folgen und genaue, aktuelle Antworten liefern, die den Anforderungen Ihres Teams entsprechen.

### Projektkonfigurationsdateien

- Für **VS Code und Copilot** erstellen Sie `.github/copilot-instructions.md`
- Für **Claude Code** erstellen Sie `CLAUDE.md` im Stammverzeichnis Ihres Projekts

Fügen Sie Folgendes in die erstellte Markdown-Datei ein:
```markdown
## Verwenden Sie den webforJ MCP-Server, um alle webforJ-Fragen zu beantworten

- Rufen Sie immer das Tool "webforj-knowledge-base" auf, um relevante Dokumente zur Frage abzurufen
- Überprüfen Sie alle API-Signaturen anhand der offiziellen Dokumentation
- Nehmen Sie niemals an, dass Methodennamen oder Parameter existieren, ohne dies zu überprüfen

Überprüfen Sie immer, ob der Code mit `mvn compile` kompiliert, bevor Sie Vorschläge machen.
```

## FAQ

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>Warum verwendet die KI den webforJ MCP-Server nicht?</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Die meisten KI-Assistenten benötigen ausdrückliche Anweisungen, um MCP-Server zu verwenden. Konfigurieren Sie Ihren KI-Client mit den Anweisungen aus dem Abschnitt [KI-Anpassung](#ai-customization). Ohne diese Anweisungen überlassen es KI-Assistenten möglicherweise ihren Trainingsdaten statt den Abfragen des MCP-Servers.

      **Schnelle Lösung:**
      Fügen Sie "verwendet webforJ MCP" in Ihrer Aufforderung hinzu oder erstellen Sie die entsprechende Konfigurationsdatei (`.github/copilot-instructions.md` oder `CLAUDE.md`).
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>Wie verifiziert man, dass die MCP-Verbindung funktioniert</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Verwenden Sie den MCP-Inspektor, um Verbindungen zu debuggen:

      ```bash
      npx @modelcontextprotocol/inspector
      ```

      Warten Sie auf die Nachricht: `🔍 MCP Inspector ist aktiv und läuft unter http://127.0.0.1:6274` (Port kann variieren)

      Geben Sie dann im Inspektor Folgendes ein:
      1. Geben Sie die MCP-Server-URL ein: `https://mcp.webforj.com/mcp`
      2. Klicken Sie auf "Verbinden", um die Verbindung herzustellen
      3. Anzeigen verfügbarer Tools und Testabfragen
      4. Überwachen Sie die Anfrage-/Antwortprotokolle zum Debuggen
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

      Die meisten Benutzer sollten den MCP-Endpunkt verwenden. Verwenden Sie nur SSE, wenn Ihr Client das Standard-MCP-Protokoll nicht unterstützt.
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>Ist es möglich, den MCP-Server ohne Konfigurationsdateien zu verwenden?</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Ja, aber es wird nicht empfohlen. Ohne Konfigurationsdateien müssen Sie die KI manuell auffordern, den MCP-Server in jeder Konversation zu nutzen. Konfigurationsdateien instruieren die KI automatisch, den MCP-Server für jede Interaktion zu verwenden, so dass Sie die Anweisungen nicht jedes Mal wiederholen müssen.

      **Manueller Ansatz:**
      Beginnen Sie Aufforderungen mit: "Verwenden Sie den webforJ MCP-Server zu..."

      **Alternative: Verwenden Sie vorkonfigurierte Aufforderungen**
      Der MCP-Server bietet Aufforderungen, die ohne Konfigurationsdateien funktionieren:
      - `/create-app` - Generieren neuer webforJ-Anwendungen
      - `/create-theme` - Erstellen barrierefreier CSS-Themen
      - `/search-webforj` - Erweiterte Dokumentationssuche

      Siehe [Verfügbare Aufforderungen](#available-prompts) für Details.
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>Wie trägt man zur Verbesserung bei oder meldet Probleme?</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      **Probleme melden:** [webforJ MCP Issue Template](https://github.com/webforj/webforj/issues/new?template=mcp_report.yml)
      
      **Häufige Probleme zur Meldung:**
      - Veraltete Dokumentation in Suchergebnissen
      - Fehlende API-Methoden oder Komponenten
      - Falsche Codebeispiele
      - Fehler bei der Toolausführung

      Geben Sie bei der Meldung von Problemen Ihre Anfrage, das erwartete Ergebnis und das tatsächliche Ergebnis an.
    </div>
  </AccordionDetails>
</Accordion>
<br />
