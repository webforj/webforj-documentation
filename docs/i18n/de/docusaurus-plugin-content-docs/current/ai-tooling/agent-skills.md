---
title: Agent Skills
sidebar_position: 10
description: >-
  Install Agent Skills so AI coding assistants follow webforJ workflows for
  building forms, adding servlets, styling apps, and creating components.
_i18n_hash: 6dc21bfd21fb27f2e71cb2265f6cde8c
---
Agent Skills lehren KI-Coding-Assistenten, wie man webforJ-Anwendungen mit den richtigen APIs, Design-Token und Komponentenmustern erstellt. Anstatt Vermutungen über Framework-Konventionen anzustellen, lädt der Assistent eine Fähigkeit und folgt einem strukturierten Workflow, um Code zu produzieren, der beim ersten Versuch kompiliert und den besten Praktiken folgt.

:::tip Verwenden Sie das Plugin
Die untenstehenden Fähigkeiten sind im **[webforJ AI-Plugin](/docs/ai-tooling)** zusammen mit dem [MCP-Server](/docs/ai-tooling/mcp) enthalten. Eine Installation liefert Ihrem Assistenten beide Teile.
:::

Fähigkeiten folgen dem offenen [Agent Skills](https://agentskills.io/specification)-Standard und funktionieren mit vielen KI-Assistenten, einschließlich Claude Code, GitHub Copilot, Cursor, Gemini CLI, OpenAI Codex und mehr. Eine Fähigkeit sagt dem Assistenten, welche Art von Aufgabe zu erledigen ist; der Assistent lädt sie automatisch, wenn Ihre Eingabe übereinstimmt. Zum Beispiel löst die Anfrage „Theme diese App mit einer blauen Palette“ die `webforj-styling-apps`-Fähigkeit aus, die den Assistenten anleitet, gültige DWC-Token nachzuschlagen, Scoped-CSS zu schreiben und jeden Variablennamen zu validieren, bevor er etwas auf die Festplatte schreibt.

## Warum Fähigkeiten verwenden? {#why-use-skills}

Der MCP-Server stellt genaue webforJ-Informationen nach Bedarf zur Verfügung, aber allein sagt er dem Assistenten nicht, _wann_ etwas nachgeschlagen werden soll, _welcher_ Ansatz zur Aufgabe passt oder _in welcher Reihenfolge_ die Dinge zu erledigen sind. Hier kommen die Fähigkeiten ins Spiel.

Fähigkeiten bieten dem Assistenten ein aufgabenbezogenes Handbuch: wie die Arbeit vor ihm klassifiziert wird, welche webforJ-Muster passen, welche MCP-Tools bei jedem Schritt konsultiert werden sollten und wie die Ausgabe validiert wird, bevor sie zurückgegeben wird. Das Ergebnis ist konsistenter, konventionsfolgender webforJ-Code anstelle einer Sammlung technischer, aber stilistisch nicht passender Schnipsel.

## Wie Fähigkeiten sich vom MCP unterscheiden {#how-skills-differ-from-mcp}

Fähigkeiten und der [webforJ MCP-Server](/docs/ai-tooling/mcp) erfüllen komplementäre Rollen. Der MCP-Server stellt lebende Tools zur Verfügung, die der Assistent aufrufen kann, um Informationen abzurufen oder Ausgaben zu generieren. Fähigkeiten liefern den Workflow, der dem Assistenten sagt, _wann_ er auf diese Tools zugreifen soll, in welcher Reihenfolge die Dinge zu erledigen sind und wie das Ergebnis validiert wird.

| | MCP-Server | Agent Skills |
|---|---|---|
| **Was es bietet** | Tools, die der Assistent nach Bedarf aufruft (Dokumentsuche, Gerüstbau, Themen-Generierung, Token-Validierung) | Workflows und Entscheidungstabellen, die leiten, wie der Assistent eine Aufgabe angeht |
| **Wann es agiert** | Wenn der Assistent beschließt, ein Tool aufzurufen | Automatisch, wenn der Assistent eine übereinstimmende Aufgabe erkennt |
| **Am besten geeignet für** | Beantwortung spezifischer Fragen, Generierung von Artefakten | End-to-End-Aufgaben, die einen konsistenten webforJ-Ansatz benötigen |

In der Praxis arbeiten die beiden am besten zusammen - und das [webforJ AI-Plugin](https://github.com/webforj/webforj-ai) wird als eine Installation geliefert.

## Installation {#installation}

Installieren Sie das **[webforJ AI-Plugin](/docs/ai-tooling)** - es enthält sowohl die untenstehenden Fähigkeiten als auch den MCP-Server. Für Clients, die keine Plugins unterstützen, listet das [webforJ AI-Repository](https://github.com/webforj/webforj-ai#clients) das Verzeichnis der Fähigkeiten auf, auf das jedes Tool zugreift, sodass Sie die Fähigkeitsordner manuell kopieren können.

## Verfügbare Fähigkeiten {#available-skills}

<AccordionGroup>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>webforj-adding-servlets</code></strong>: REST-Endpunkte, Webhooks und benutzerdefinierte Servlets hinzufügen
  </AccordionSummary>
  <AccordionDetails>
    <div>

Verwenden Sie dies, wenn Sie einen non-UI HTTP-Pfad benötigen - einen REST-Endpunkt, einen Webhook-Handler oder ein Drittanbieterservlet wie Swagger UI oder Spring Web. Der Assistent wählt den richtigen Ansatz für Ihr Projekt (Spring `webforj.exclude-urls`, Remapping von `WebforjServlet` auf einen Unterpfad oder Proxying durch `webforj.conf`) und verbindet den Endpunkt, ohne die UI-Routing von webforJ zu stören.

**Wann es aktiviert wird**

- *„Fügen Sie einen REST-Endpunkt bei `/api/orders` hinzu.“*
- *„Verbinden Sie einen Webhook-Handler für Stripe.“*
- *„Mounten Sie Swagger UI bei `/api/docs`.“*
- *„Stellen Sie ein benutzerdefiniertes Servlet bereit, das neben der webforJ UI läuft.“*

</div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>webforj-building-forms</code></strong>: Formulare mit Bindung, Validierung und Eingabemasken erstellen
  </AccordionSummary>
  <AccordionDetails>
    <div>

Verwenden Sie dies für alle Formulararbeiten in einer webforJ-App: Dateneingabeformulare, bidirektionale Bindung an ein Java-Bean, Jakarta-Validierung, maskierte Eingabekomponenten (Telefon, Währung, IBAN, Daten), Formatierung von Tabellen Spalten als Währung oder Prozent, und responsive Layouts mit mehreren Spalten. Der Assistent führt über `BindingContext`, die `Masked*Field`-Komponenten, die Maskenrenderer der Tabelle und `ColumnsLayout`.

**Wann es aktiviert wird**

- *„Erstellen Sie ein Registrierungsformular, das an mein `User`-Bean gebunden ist.“*
- *„Fügen Sie ein Telefonnummerneingabefeld mit Formatierung während der Eingabe hinzu.“*
- *„Formatieren Sie diese Tabellen Spalte als Währung.“*
- *„Validieren Sie dieses Feld mit `@NotEmpty` und einem benutzerdefinierten E-Mail-Prüfer.“*

</div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>webforj-creating-components</code></strong>: web Komponenten, JS-Bibliotheken oder Kompositionen einbinden
  </AccordionSummary>
  <AccordionDetails>
    <div>

Verwenden Sie dies, wenn Sie eine wiederverwendbare Java-Komponente um jeden Quellcode herum einfügen müssen - eine vorhandene Custom Element-Bibliothek, eine einfache JavaScript-Bibliothek oder eine Komposition bestehender webforJ-Komponenten. Der Assistent wählt die richtige webforJ-Basis-Klasse für die Aufgabe, verbindet Eigenschaften, Ereignisse und Slots mit den richtigen Mustern und erzeugt Tests, die den webforJ-Konventionen folgen.

**Wann es aktiviert wird**

- *„Verpacken Sie diese Custom Element-Bibliothek als webforJ-Komponenten.“*
- *„Komponieren Sie diese webforJ-Komponenten in eine wiederverwendbare Karte.“*
- *„Integrieren Sie diese einfache JavaScript-Bibliothek als webforJ-Komponente.“*
- *„Stellen Sie diese Browser-API als webforJ-Dienstprogramm bereit.“*

</div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>webforj-handling-timers-and-async</code></strong>: Timer, Debouncer und asynchrone Arbeit planen
  </AccordionSummary>
  <AccordionDetails>
    <div>

Verwenden Sie dies für periodische Aufgaben, Abfragen, debouncierte Suchanfragen während der Eingabe, Drosselung und langlaufende Hintergrundarbeiten, die die Benutzerschnittstelle während ihrer Ausführung aktualisieren. Der Assistent wählt das richtige Primärsystem (`Interval`, `Debouncer`, `Environment.runLater`, `PendingResult`) und vermeidet die Laufzeitfallen von raw `java.util.Timer`, `javax.swing.Timer` oder Threads, die außerhalb der webforJ-Umgebung erstellt wurden, die alle `IllegalStateException` auslösen, sobald sie eine UI-Komponente berühren.

**Wann es aktiviert wird**

- *„Aktualisieren Sie dieses Dashboard alle 30 Sekunden.“*
- *„Fügen Sie einen Debouncer für die Suche während der Eingabe hinzu.“*
- *„Führen Sie diese CPU-intensive Arbeit im Hintergrund aus und aktualisieren Sie die Fortschrittsanzeige.“*
- *„Fragen Sie diesen REST-Endpunkt ab, bis er `done` zurückgibt.“*

</div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>webforj-localizing-apps</code></strong>: i18n und Übersetzungsunterstützung hinzufügen
  </AccordionSummary>
  <AccordionDetails>
    <div>

Verwenden Sie dies für alle Internationalisierungsarbeiten: Laden von Nachrichtenpaketen, Umschalten von Sprachen zur Laufzeit, automatische Erkennung der Browsersprache des Benutzers und Übersetzen von Komponentenbezeichnungen. Der Assistent führt über den `BundleTranslationResolver` von webforJ 25.12, die `HasTranslation`-Klausel, `LocaleObserver` und pluggable benutzerdefinierte Resolver und deckt sowohl Spring- als auch einfache webforJ-Pfade ab.

**Wann es aktiviert wird**

- *„Fügen Sie mehrsprachige Unterstützung mit Englisch und Spanisch hinzu.“*
- *„Erkennen Sie die Browsersprache des Benutzers und wenden Sie sie beim Start an.“*
- *„Fügen Sie einen Sprachumschalter zur Navigationsleiste hinzu.“*
- *„Verschieben Sie alle festcodierten Zeichenfolgen in ein Nachrichtenpaket.“*

</div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>webforj-securing-apps</code></strong>: Routen mit Anmeldung und rollenbasierter Zugriffssteuerung schützen
  </AccordionSummary>
  <AccordionDetails>
    <div>

Verwenden Sie dies für alles, was Routen in einer webforJ-App schützt: Anmelden und Abmelden, rollenbasierter Zugriff, öffentliche Landing Pages, nur für Administratoren zugängliche Bereiche, Eigentumsregeln und Sicherheitsrichtlinien. Der Assistent bevorzugt Spring Security, wenn Spring Boot im Klassenpfad vorhanden ist, und greift andernfalls auf das einfache Sicherheitsframework von webforJ zurück. Er wendet die richtigen Annotations (`@AnonymousAccess`, `@PermitAll`, `@RolesAllowed`, `@RouteAccess`, `@RegisteredEvaluator`) an und erklärt, welche terminal und welche composable sind, damit "sicher von Anfang" immer noch das tut, was es verspricht.

**Wann es aktiviert wird**

- *„Schützen Sie `/admin`, damit nur Benutzer mit der Rolle `ADMIN` darauf zugreifen können.“*
- *„Fügen Sie eine öffentliche Landing Page hinzu, die jeder besuchen kann.“*
- *„Zeigen Sie den Namen des angemeldeten Benutzers in der Kopfzeile an.“*
- *„Lassen Sie nur zu, dass ein Benutzer einen Datensatz bearbeitet, der ihm gehört.“*

</div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>webforj-styling-apps</code></strong>: Apps mit DWC-Design-Token gestalten
  </AccordionSummary>
  <AccordionDetails>
    <div>

Verwenden Sie dies für alle visuellen Arbeiten an einer webforJ-App: Palette umgestalten, komponentenspezifische Stilgestaltung, Layout und Abstände, Typografie, vollständige Themen, Tabellenerscheinung oder koordinierte Farben für Google Charts. Der Assistent schreibt CSS, das den DWC-Design-Token folgt, scopt Selektoren korrekt und validiert jeden `--dwc-*`-Verweis gegen den echten Katalog für Ihre webforJ-Version - sodass der Dunkelmodus und das Themenwechseln weiterhin funktionieren.

**Wann es aktiviert wird**

- *„Gestalten Sie diese App mit einer blauen Palette.“*
- *„Gestalten Sie den dwc-button nach den Markenrichtlinien.“*
- *„Gestalten Sie dieses Layout enger - passen Sie Abstände und Typografie an.“*

</div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>webforj-upgrading-versions</code></strong>: über Major-Versionen von webforJ mit OpenRewrite upgraden
  </AccordionSummary>
  <AccordionDetails>
    <div>

Verwenden Sie dies für Upgrades auf Hauptversionen. Der Assistent führt das offizielle `webforj-rewrite` OpenRewrite-Rezept für die Zielversion aus, das `<webforj.version>` und die Java-Version erhöht, umbenannte APIs und Typen umschreibt und `TODO webforJ <major>:` Kommentare an jeder entfernten Methode einfügt, die eine manuelle Entscheidung erfordert. Für ältere Ziele ohne veröffentlichtes Rezept (z. B. 24 bis 25) führt er Sie durch die manuelle Rückfalloption.

**Wann es aktiviert wird**

- *„Upgrade diese App von webforJ 25 auf 26.“*
- *„Führen Sie das Rewrite-Rezept aus und lösen Sie die TODOs.“*
- *„Migrieren Sie manuell von webforJ 24 nach 25, da es kein Rezept gibt.“*
- *„Welche entfernten APIs muss ich nach dem Upgrade beheben?“*

</div>
  </AccordionDetails>
</Accordion>

</AccordionGroup>
