---
title: Agent Skills
sidebar_position: 10
sidebar_class_name: new-content
_i18n_hash: cf22942f0e73a936bef31cf8a3a9a043
---
Agent skills lehren KI-Coding-Assistenten, wie man webforJ-Anwendungen mit den richtigen APIs, Design-Tokens und Komponentenmustern erstellt. Anstatt Vermutungen über die Konventionen des Frameworks anzustellen, lädt ein KI-Assistent eine Fähigkeit und folgt deren strukturiertem Workflow, um Code zu erzeugen, der beim ersten Versuch kompiliert und den besten Praktiken folgt.

Fähigkeiten folgen der offenen [Agent Skills](https://agentskills.io/specification) Spezifikation und funktionieren mit mehreren KI-Assistenten, darunter Claude Code, GitHub Copilot in VS Code und Cursor. Jede Fähigkeit ist ein einzelnes Verzeichnis mit einer `SKILL.md`-Datei, die den Zweck und den Workflow der Fähigkeit beschreibt, sowie `references/` und `scripts/` Verzeichnissen für unterstützende Dokumentation und Hilfsskripte.

Agentfähigkeiten für webforJ sind im GitHub-Repository [webforj/webforj-agent-skills](https://github.com/webforj/webforJ-agent-skills) verfügbar. Mit diesen installierten Fähigkeiten lädt eine KI diese Dateien automatisch, wenn sie eine relevante Aufgabe erkennt. Zum Beispiel löst die Aufforderung an eine KI, "diese App mit einer blauen Palette zu gestalten", die Fähigkeit `styling-apps` aus, die die KI dazu führt, gültige DWC-Tokens zu recherchieren, scoped CSS zu schreiben und jeden Variablennamen zu validieren, bevor sie eine Ausgabe erzeugt.

## Warum Fähigkeiten nutzen? {#why-use-skills}

Ohne Fähigkeiten produzieren KI-Assistenten oft webforJ-Code, der plausibel aussieht, aber in der Praxis fehlschlägt. Häufige Probleme sind:

- Erfindung von `--dwc-*` Token-Namen, die nicht existieren (CSS kompiliert, hat aber keine Wirkung)
- Verwendung der falschen Basisklasse für Komponentenwrapper (`Composite` anstelle von `ElementComposite` oder umgekehrt)
- Fehlende `PropertyDescriptor`-Muster, Ereignisannotationen oder Concern-Interfaces
- Hardcodierte Farben, die den Dunkelmodus brechen
- Auslassen von Validierungsschritten, die stille Fehler erkennen

Fähigkeiten beseitigen diese Probleme, indem sie der KI genaue Entscheidungstabellen, Lookup-Skripte und Validierungs-Checklisten für jeden Aufgabentyp zur Verfügung stellen.

## Wie sich Fähigkeiten von MCP unterscheiden {#how-skills-differ-from-mcp}

Fähigkeiten und der [webforJ MCP-Server](./mcp) erfüllen komplementäre Rollen. MCP bietet Live-Tools, die die KI zur Laufzeit aufrufen kann, um Dokumentationen zu durchsuchen oder Projekte zu generieren. Fähigkeiten bieten statisches Wissen und Schritt-für-Schritt-Workflows, die der KI helfen, eine Aufgabe anzugehen.

| | MCP-Server | Agentfähigkeiten |
|---|---|---|
| **Was es bereitstellt** | Live-Tools: Dokumentationssuche, Projekt-Scaffolding, Themen-Generierung | Statisches Wissen: Workflows, Entscheidungstabellen, Referenzdokumente, Hilfsskripte |
| **Wann es agiert** | Auf Anfrage, wenn die KI ein Tool aufruft | Automatisch, wenn die KI eine passende Aufgabe erkennt |
| **Am besten für** | Nachschlagen spezifischer APIs, Generierung von Starter-Projekten, Erstellung von Themenpaletten | End-to-End-Aufgaben, die das Befolgen von Framework-Konventionen und mehrstufigen Workflows erfordern |

In der Praxis arbeiten die beiden gut zusammen. Das `webforj-create-theme`-Tool des MCP-Servers generiert eine gültige Palette aus einer einzigen Farbe, und die Fähigkeit `styling-apps` führt die KI dann durch das Styling auf Komponentenebene und die Validierung des Dunkelmodus unter Verwendung dieser Palette.

Fähigkeiten sind statische Dateien, die vom Speicher gelesen werden – sie verursachen keinen Laufzeit-Overhead oder führen externe API-Aufrufe durch. Die KI lädt das Referenzmaterial einer Fähigkeit in ihr Kontextfenster, wenn es relevant ist, was einige Kontext-Token verwendet, aber die resultierende Ausgabequalität für framework-spezifische Arbeiten ist erheblich höher.

## Installation {#installation}

Klone das [webforJ-Agentfähigkeiten-Repository](https://github.com/webforj/webforJ-agent-skills) und kopiere dann die Fähigkeitsordner an den Ort, den dein KI-Tool erwartet. Jedes Tool unterstützt zwei Bereiche:

- **Projektbereich**: die Fähigkeit ist nur in diesem Projekt verfügbar
- **Benutzerbereich**: die Fähigkeit ist in all deinen Projekten verfügbar

<Tabs groupId="ide">
<TabItem value="claude-code" label="Claude Code" default>

```bash
git clone https://github.com/webforj/webforJ-agent-skills.git
cd webforJ-agent-skills

# Projektbereich
cp -r creating-components /path/to/your/project/.claude/skills/
cp -r styling-apps /path/to/your/project/.claude/skills/

# Benutzerbereich
cp -r creating-components ~/.claude/skills/
cp -r styling-apps ~/.claude/skills/
```

</TabItem>
<TabItem value="vscode" label="VS Code Copilot">

```bash
git clone https://github.com/webforj/webforJ-agent-skills.git
cd webforJ-agent-skills

# Projektbereich
cp -r creating-components /path/to/your/project/.github/skills/
cp -r styling-apps /path/to/your/project/.github/skills/

# Benutzerbereich
cp -r creating-components ~/.copilot/skills/
cp -r styling-apps ~/.copilot/skills/
```

</TabItem>
<TabItem value="cursor" label="Cursor">

```bash
git clone https://github.com/webforj/webforJ-agent-skills.git
cd webforJ-agent-skills

# Projektbereich
cp -r creating-components /path/to/your/project/.cursor/skills/
cp -r styling-apps /path/to/your/project/.cursor/skills/

# Benutzerbereich
cp -r creating-components ~/.cursor/skills/
cp -r styling-apps ~/.cursor/skills/
```

</TabItem>
</Tabs>

:::tip[Welchen Bereich verwenden]
Verwende **Projektbereich**, wenn du mit einem Team zusammenarbeitest, damit jeder im Projekt von denselben Fähigkeiten profitiert. Verwende **Benutzerbereich**, wenn du an mehreren webforJ-Projekten arbeitest und die Fähigkeiten überall verfügbar haben möchtest, ohne sie in jedes Repository kopieren zu müssen.
:::

## Verfügbare Fähigkeiten {#available-skills}

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>creating-components</code></strong>: Erstellen Sie wiederverwendbare webforJ-Komponenten aus Webkomponentenbibliotheken, JavaScript-Bibliotheken oder bestehenden webforJ-Komponenten.
  </AccordionSummary>
  <AccordionDetails>
    <div>

[Diese Fähigkeit](https://github.com/webforj/webforJ-agent-skills/tree/main/creating-components) führt einen KI-Assistenten durch den Prozess der Erstellung wiederverwendbarer Java-Komponenten aus jeder Quelle, sei es eine bestehende Webkomponentenbibliothek, eine einfache JavaScript-Bibliothek oder eine Komposition vorhandener webforJ-Komponenten.

**Was sie abdeckt**

Die Fähigkeit definiert fünf Pfade zur Erstellung von Komponenten und lehrt die KI, den richtigen basierend auf der Aufgabe auszuwählen:

| Pfad | Wann zu verwenden | Basisklasse |
|---|---|---|
| Wrapper für eine vorhandene Custom Element-Bibliothek | Bibliothek versendet Custom Elements (`<x-button>`, `<x-dialog>`) | `ElementComposite` / `ElementCompositeContainer` |
| Erstellen Sie ein Custom Element und wickeln Sie es dann ein | Neues visuelles Element oder Wickeln einer einfachen JS-Bibliothek | `ElementComposite` / `ElementCompositeContainer` |
| Kombinieren von webforJ-Komponenten | Kombination vorhandener webforJ-Komponenten zu einer wiederverwendbaren Einheit | `Composite<T>` |
| HTML-Element erweitern | Leichtgewichtige einmalige Integration ohne Shadow DOM | `Div`, `Span` usw. |
| Seitenbenutzungsprogramm | Browser-API oder globale Funktion ohne DOM-Widget | Einfache Java-Klasse + `EventDispatcher` |

**Workflow**

Für das Wrapping von Custom Elements (dem häufigsten Pfad) führt die Fähigkeit die KI durch einen strukturierten Workflow:

1. **Einrichtung**: Third-Party-JS/CSS in das Verzeichnis `src/main/resources/static/libs/` des Projekts herunterladen. Die Fähigkeit weist die KI an, lokale Ressourcen gegenüber CDN-Links für Offline-Zuverlässigkeit zu bevorzugen.
2. **Komponentendaten extrahieren**: Verwende das enthaltene `extract_components.mjs`-Skript, um ein Manifest für Custom Elements zu analysieren und eine strukturierte Spezifikation der Eigenschaften, Ereignisse, Slots und CSS-Variablen jeder Komponente zu erstellen.
3. **Java-Wrappers schreiben**: Erstelle Klassen `ElementComposite` oder `ElementCompositeContainer` mit `PropertyDescriptor`-Feldern, Ereignisklassen, Slot-Methoden und Concern-Interfaces, alles gemäß den Konventionen von webforJ.
4. **Tests schreiben**: Generiere JUnit 5-Tests mit `PropertyDescriptorTester` und strukturierten Testmustern für Eigenschaften, Slots und Ereignisse.

**Referenzmaterial**

Die Fähigkeit umfasst acht Referenzdokumente zu `ElementComposite`-Mustern, Komponentenkomposition, Property Deskriptoren, Ereignisbehandlung, JavaScript-Interop, Testmustern und häufigen Anti-Mustern.

</div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>styling-apps</code></strong>: Themen und Styling für webforJ-Anwendungen unter Verwendung des DWC-Design-Token-Systems.
  </AccordionSummary>
  <AccordionDetails>
    <div>

[Diese Fähigkeit](https://github.com/webforj/webforJ-agent-skills/tree/main/styling-apps) lehrt einen KI-Assistenten, wie man webforJ-Anwendungen mit dem DWC-Design-Token-System stylt. Das Kernprinzip ist, dass alle visuellen Werte `--dwc-*` CSS-Variablen verwenden. Die Fähigkeit setzt dies durch, indem sie Validierungsschritte und Lookup-Skripte bereitstellt, die verhindern, dass die KI Token-Namen erfindet oder Farben hardcodiert.

**Was sie abdeckt**

| Aufgabe | Ansatz, den die Fähigkeit lehrt |
|------|---------------------------|
| Farbänderung | Überschreiben von Farbton-, Sättigungs- und Kontrast-Token in `:root` |
| Komponentenstyling | Zuerst die CSS-Variablen der Komponente nachschlagen, nur falls nötig auf `::part()` zurückgreifen |
| Layout und Abstände | Verwende `--dwc-space-*` und `--dwc-size-*` Tokens |
| Typografie | Verwende `--dwc-font-*` Tokens |
| Vollständiges Thema | Palette-Konfiguration mit semantischer Token-Zuordnung |
| Tabellenstyling | Nur `::part()`-Selektoren (Tabellen bieten keine CSS-Variablen an) |
| Google Charts | JSON-Themendatei, die über `Assets.contentOf()` und Gson geladen wird |

**Workflow**

Die Fähigkeit setzt eine strikte Lookup-vor-Schreiben-Disziplin durch:

1. **Klassifizieren Sie die Aufgabe**: Bestimmen, ob es sich um eine Farbänderung, Komponentenstyling, Layoutarbeiten oder ein vollständiges Thema handelt.
2. **Scanne die App**: Lese den Java-Quellcode, um jede Komponente, Themenvariante und Erweiterung zu finden, die verwendet wird.
3. **Jede Komponente nachschlagen**: Führe das enthaltene `component_styles.py`-Skript aus, um die genauen CSS-Variablen, `::part()`-Namen und reflektierte Attribute abzurufen, die jede Komponente unterstützt. Die KI schreibt keinen CSS, bis dieser Schritt abgeschlossen ist.
4. **CSS schreiben**: Erzeuge geschachteltes, kompaktes CSS, das den DWC-Konventionen folgt: globale Tokens zuerst, dann-Komponenten-CSS-Variablen, dann `::part()`-Überschreibungen als letzte Möglichkeit.
5. **Validiere**: Führe das Lookup-Skript erneut aus und überprüfe, dass jeder Token, jeder Teilname und jeder Selektor in der Ausgabe tatsächlich existiert. Korrigiere alles, was fehlschlägt.

**Wichtige Regeln, die die Fähigkeit durchsetzt**

- **Nur sieben Paletten**: `primary`, `success`, `warning`, `danger`, `info`, `default` und `gray`. Namen wie `secondary` oder `accent` existieren nicht im DWC und scheitern still.
- **Keine hardcodierten Farben**: Jeder Farbwert muss ein `var()`-Referenz sein, auch innerhalb von `box-shadow` und `border`. Hardcodierte Werte brechen den Dunkelmodus.
- **CSS-Variablen über `::part()`**: Komponenten-CSS-Variablen sind die beabsichtigte Styling-API. `::part()` ist der Fluchtweg für Fälle, in denen keine Variable vorhanden ist.
- **Scoped Selektoren**: Bereinigen von Tag-Selektoren bei Komponenten mit `theme` oder `expanse`-Attributen überschreibt alle Varianten. Die Fähigkeit verlangt `:not([theme])` oder `[theme~="value"]`-Scoping.

</div>
  </AccordionDetails>
</Accordion>
