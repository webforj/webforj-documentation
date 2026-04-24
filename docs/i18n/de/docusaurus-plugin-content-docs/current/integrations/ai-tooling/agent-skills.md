---
title: Agent Skills
sidebar_position: 10
_i18n_hash: 98d3f61447c289339f92fc4872e734f1
---
Agent Skills lehren KI-Coding-Assistenten, wie man webforJ-Anwendungen mit den richtigen APIs, Design-Tokens und Komponentenmustern erstellt. Anstatt in den Rahmenkonventionen zu raten, lädt der Assistent eine Fähigkeit und folgt einem strukturierten Workflow, um Code zu erzeugen, der beim ersten Versuch kompiliert und bewährte Methoden einhält.

:::tip Nutzen Sie das Plugin
Die folgenden Fähigkeiten sind im **[webforJ AI-Plugin](/docs/integrations/ai-tooling)** zusammen mit dem [MCP-Server](/docs/integrations/ai-tooling/mcp) enthalten. Eine Installation bietet Ihrem Assistenten beide Komponenten.
:::

Fähigkeiten folgen dem offenen [Agent Skills](https://agentskills.io/specification)-Standard und funktionieren mit vielen KI-Assistenten, einschließlich Claude Code, GitHub Copilot, Cursor, Gemini CLI, OpenAI Codex und mehr. Eine Fähigkeit sagt dem Assistenten, welche Art von Aufgabe sie bearbeitet; der Assistent lädt sie automatisch, wenn Ihre Eingabe übereinstimmt. Wenn Sie beispielsweise "Gestalte diese App mit einer blauen Farbpalette" fragen, wird die Fähigkeit `webforj-styling-apps` ausgelöst, die den Assistenten durch das Nachschlagen gültiger DWC-Tokens, Schreiben von Scoped-CSS und Validierung jedes Variablennamens führt, bevor etwas auf der Festplatte geschrieben wird.

## Warum Fähigkeiten verwenden? {#why-use-skills}

Der MCP-Server stellt genaue webforJ-Informationen auf Abruf zur Verfügung, aber allein sagt er dem Assistenten nicht, _wann_ etwas nachgeschlagen werden soll, _welcher_ Ansatz zur Aufgabe passt oder _in welcher Reihenfolge_ die Dinge zu tun sind. Hier kommen die Fähigkeiten ins Spiel.

Fähigkeiten geben dem Assistenten ein aufgabenbezogenes Handbuch: wie die Arbeit vor ihm klassifiziert wird, welche webforJ-Muster passen, welche MCP-Tools in jedem Schritt konsultiert werden sollen, und wie das Ergebnis validiert werden kann, bevor es zurückgegeben wird. Das Ergebnis ist konsistenter, konventioneller webforJ-Code anstelle einer Sammlung von technisch gültigen, aber stilistisch nicht passenden Snippets.

## Wie sich Fähigkeiten vom MCP unterscheiden {#how-skills-differ-from-mcp}

Fähigkeiten und der [webforJ MCP-Server](/docs/integrations/ai-tooling/mcp) haben komplementäre Rollen. Der MCP-Server stellt Live-Tools zur Verfügung, die der Assistent anrufen kann, um Informationen abzurufen oder Ausgaben zu generieren. Fähigkeiten bieten den Workflow, der dem Assistenten sagt, _wann_ er auf diese Tools zugreifen soll, in welcher Reihenfolge die Dinge zu tun sind und wie das Ergebnis validiert werden kann.

| | MCP-Server | Agent Skills |
|---|---|---|
| **Was es bereitstellt** | Werkzeuge, die der Assistent bei Bedarf anruft (Dokumenten Suche, Scaffold, Theme-Generierung, Token-Validierung) | Workflows und Entscheidungstabellen, die leiten, wie der Assistent eine Aufgabe angeht |
| **Wann es aktiv ist** | Wenn der Assistent entscheidet, ein Werkzeug anzurufen | Automatisch, wenn der Assistent eine passende Aufgabe erkennt |
| **Am besten geeignet für** | Beantwortung spezifischer Fragen, Erzeugung von Artefakten | End-to-End-Aufgaben, die einen konsistenten webforJ-Ansatz benötigen |

In der Praxis funktionieren die beiden am besten zusammen - und das [webforJ AI-Plugin](https://github.com/webforj/webforj-ai) wird als eine Installation angeboten.

## Installation {#installation}

Installieren Sie das **[webforJ AI-Plugin](/docs/integrations/ai-tooling)** - es enthält beide Fähigkeiten zusammen mit dem MCP-Server. Für Clients, die keine Plugins unterstützen, listet das [webforJ AI-Repository](https://github.com/webforj/webforj-ai#clients) das Verzeichnis der Fähigkeiten auf, aus dem jedes Tool liest, sodass Sie die Fähigkeitsordner manuell kopieren können.

## Verfügbare Fähigkeiten {#available-skills}

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>webforj-creating-components</code></strong>: Erstellen Sie wiederverwendbare webforJ-Komponenten aus Webkomponentenbibliotheken, JavaScript-Bibliotheken oder vorhandenen webforJ-Komponenten
  </AccordionSummary>
  <AccordionDetails>
    <div>

Verwenden Sie dies, wenn Sie eine wiederverwendbare Java-Komponente um jede Quelle herum benötigen - eine vorhandene benutzerdefinierte Elementbibliothek, eine einfache JavaScript-Bibliothek oder eine Komposition vorhandener webforJ-Komponenten. Der Assistent wählt die richtige webforJ-Basisklasse für die Aufgabe aus, verkabelt Eigenschaften, Ereignisse und Slots mit den richtigen Mustern und erzeugt Tests, die den webforJ-Konventionen folgen.

**Wann es aktiv wird**

- *"Umhüllen Sie diese benutzerdefinierte Elementbibliothek als webforJ-Komponenten."*
- *"Komponieren Sie diese webforJ-Komponenten zu einer wiederverwendbaren Karte."*
- *"Integrieren Sie diese einfache JavaScript-Bibliothek als webforJ-Komponente."*
- *"Exponieren Sie diese Browser-API als webforJ-Hilfsmittel."*

</div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>webforj-styling-apps</code></strong>: Thema und Stil webforJ-Anwendungen mithilfe des DWC-Design-Token-Systems
  </AccordionSummary>
  <AccordionDetails>
    <div>

Verwenden Sie dies für alle visuellen Arbeiten an einer webforJ-App: FarbpalettenneuGestaltungen, komponentenspezifisches Styling, Layout und Abstände, Typografie, vollständige Designs, Tabellenlayouts oder koordinierte Google Charts-Farben. Der Assistent schreibt CSS, das an DWC-Design-Tokens gebunden ist, die Selektoren korrekt einschränkt und jede `--dwc-*`-Referenz gegen den echten Katalog für Ihre webforJ-Version validiert, damit der dunkle Modus und das Themenwechseln weiterhin funktionieren.

**Wann es aktiv wird**

- *"Gestalte diese App mit einer blauen Farbpalette."*
- *"Stylen Sie den dwc-button, um den Markenrichtlinien zu entsprechen."*
- *"Machen Sie dieses Layout enger - passen Sie Abstände und Typografie an."*

</div>
  </AccordionDetails>
</Accordion>
