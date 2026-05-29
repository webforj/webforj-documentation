---
title: Agent Skills
sidebar_position: 10
_i18n_hash: 0458a29cc4337ff83f08afb415097a1c
---
Agent Skills lehren KI-Coding-Assistenten, wie man webforJ-Anwendungen mit den richtigen APIs, Design-Token und Komponentenmustern erstellt. Anstatt über Framework-Konventionen zu raten, lädt der Assistent eine Fähigkeit und folgt einem strukturierten Workflow, um Code zu erzeugen, der beim ersten Versuch kompiliert und den besten Praktiken folgt.

:::tip Verwenden Sie das Plugin
Die unten aufgeführten Fähigkeiten sind im **[webforJ AI-Plugin](/docs/integrations/ai-tooling)** zusammen mit dem [MCP-Server](/docs/integrations/ai-tooling/mcp) enthalten. Eine Installation gibt Ihrem Assistenten beide Teile.
:::

Fähigkeiten folgen dem offenen [Agent Skills](https://agentskills.io/specification) Standard und funktionieren mit vielen KI-Assistenten, darunter Claude Code, GitHub Copilot, Cursor, Gemini CLI, OpenAI Codex und mehr. Eine Fähigkeit sagt dem Assistenten, welche Art von Aufgabe sie übernimmt; der Assistent lädt sie automatisch, wenn Ihre Eingabe übereinstimmt. Zum Beispiel löst die Anfrage "Gestalte diese App mit einer blauen Palette" die `webforj-styling-apps` Fähigkeit aus, die den Assistenten durch das Auffinden gültiger DWC-Token, das Schreiben von lokalem CSS und das Validieren jedes Variablennamens führt, bevor irgendetwas auf der Festplatte geschrieben wird.

## Warum Fähigkeiten nutzen? {#why-use-skills}

Der MCP-Server stellt genaue webforJ-Informationen auf Abruf zur Verfügung, sagt dem Assistenten jedoch nicht, _wann_ er etwas nachschlagen soll, _welcher_ Ansatz zur Aufgabe passt oder _in welcher Reihenfolge_ die Dinge zu tun sind. Hier kommen die Fähigkeiten ins Spiel.

Fähigkeiten geben dem Assistenten ein aufgaben spezifisches Playbook: wie man die vor ihm liegende Arbeit klassifiziert, welche webforJ-Muster passen, welche MCP-Tools bei jedem Schritt zu Rate gezogen werden sollen und wie man das Ergebnis validiert, bevor es zurückgegeben wird. Das Ergebnis ist konsistenter, konventioneller webforJ-Code anstelle einer Sammlung technisch gültiger, aber stilistisch nicht passender Snippets.

## Wie sich Fähigkeiten vom MCP unterscheiden {#how-skills-differ-from-mcp}

Fähigkeiten und der [webforJ MCP-Server](/docs/integrations/ai-tooling/mcp) übernehmen komplementäre Rollen. Der MCP-Server stellt Live-Tools bereit, die der Assistent abrufen kann, um Informationen zu erhalten oder Ausgaben zu generieren. Fähigkeiten bieten den Workflow, der dem Assistenten sagt, _wann_ er diese Tools nutzen soll, in welcher Reihenfolge die Dinge zu erledigen sind und wie das Ergebnis zu validieren ist.

| | MCP-Server | Agent Skills |
|---|---|---|
| **Was es bereitstellt** | Werkzeuge, die der Assistent auf Abruf anruft (Dokumentsuche, Scaffoldierung, Themen-Generierung, Token-Validierung) | Workflows und Entscheidungstabellen, die leiten, wie der Assistent eine Aufgabe angeht |
| **Wann es agiert** | Wenn der Assistent beschließt, ein Tool anzurufen | Automatisch, wenn der Assistent eine übereinstimmende Aufgabe erkennt |
| **Am besten geeignet für** | Beantwortung spezifischer Fragen, Erzeugen von Artefakten | End-to-End-Aufgaben, die einen konsistenten webforJ-Ansatz benötigen |

In der Praxis funktionieren die beiden am besten zusammen - und das [webforJ AI-Plugin](https://github.com/webforj/webforj-ai) wird als eine Installation geliefert.

## Installation {#installation}

Installieren Sie das **[webforJ AI-Plugin](/docs/integrations/ai-tooling)** - es enthält beide unten aufgeführten Fähigkeiten zusammen mit dem MCP-Server. Für Clients, die Plugins nicht unterstützen, listet das [webforJ AI-Repository](https://github.com/webforj/webforj-ai#clients) das Verzeichnis der Fähigkeiten auf, aus dem jedes Tool liest, sodass Sie die Fähigkeitsordner manuell kopieren können.

## Verfügbare Fähigkeiten {#available-skills}

<AccordionGroup>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>webforj-adding-servlets</code></strong>: REST-Endpunkte, Webhooks und benutzerdefinierte Servlets hinzufügen
  </AccordionSummary>
  <AccordionDetails>
    <div>

Verwenden Sie dies, wenn Sie einen nicht-UI HTTP-Pfad benötigen - einen REST-Endpunkt, einen Webhook-Handler oder ein Drittanbieter-Servlet wie Swagger UI oder Spring Web. Der Assistent wählt den richtigen Ansatz für Ihr Projekt (Spring `webforj.exclude-urls`, Remapping von `WebforjServlet` zu einem Unterpfad oder Proxying durch `webforj.conf`) und verkabelt den Endpunkt, ohne die UI-Routing von webforJ zu stören.

**Wann es aktiv wird**

- *"Fügen Sie einen REST-Endpunkt unter `/api/orders` hinzu."*
- *"Verbinden Sie einen Webhook-Handler für Stripe."*
- *"Mount Swagger UI unter `/api/docs`."*
- *"Expose ein benutzerdefiniertes Servlet, das neben der webforJ-UI läuft."*

</div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>webforj-building-forms</code></strong>: Formulare mit Binding, Validierung und Eingabemasken erstellen
  </AccordionSummary>
  <AccordionDetails>
    <div>

Verwenden Sie dies für alle Formulararbeiten in einer webforJ-App: Dateneingabeformulare, bidirektionales Binding an ein Java-Bean, Jakarta-Validierung, maskierte Eingabekomponenten (Telefon, Währung, IBAN, Daten), Spalten der Tabelle als Währung oder Prozentsatz formatieren und responsive Mehrspaltenlayouts. Der Assistent organisiert über `BindingContext`, die `Masked*Field` Komponenten, die Maskenrenderer der Tabelle und `ColumnsLayout`.

**Wann es aktiv wird**

- *"Erstellen Sie ein Registrierungsformular, das an mein `User`-Bean gebunden ist."*
- *"Fügen Sie eine Telefonnummer-Eingabe mit Formatierung während der Eingabe hinzu."*
- *"Formatieren Sie diese Tabellen-Spalte als Währung."*
- *"Validieren Sie dieses Feld mit `@NotEmpty` und einem benutzerdefinierten E-Mail-Checker."*

</div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>webforj-creating-components</code></strong>: Webkomponenten, JS-Bibliotheken oder Kompositionen umwickeln
  </AccordionSummary>
  <AccordionDetails>
    <div>

Verwenden Sie dies, wenn Sie eine wiederverwendbare Java-Komponente benötigen, die um jede Quelle gewickelt ist - eine vorhandene benutzerdefinierte Elementbibliothek, eine einfache JavaScript-Bibliothek oder eine Komposition bestehender webforJ-Komponenten. Der Assistent wählt die richtige webforJ-Basisklasse für den Job aus, verkabelt Eigenschaften, Ereignisse und Slots mit den richtigen Mustern und erzeugt Tests, die den webforJ-Konventionen folgen.

**Wann es aktiv wird**

- *"Wickeln Sie diese benutzerdefinierte Elementbibliothek als webforJ-Komponenten."*
- *"Komponieren Sie diese webforJ-Komponenten zu einer wiederverwendbaren Karte."*
- *"Integrieren Sie diese einfache JavaScript-Bibliothek als webforJ-Komponente."*
- *"Expose diese Browser-API als webforJ-Hilfsprogramm."*

</div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>webforj-handling-timers-and-async</code></strong>: Timer, Debouncer und asynchrone Arbeiten planen
  </AccordionSummary>
  <AccordionDetails>
    <div>

Verwenden Sie dies für periodische Aufgaben, Polling, debounced Suche beim Typisieren, Throttling und langlaufende Hintergrundarbeit, die das UI während der Ausführung aktualisiert. Der Assistent wählt das richtige Primitive (`Interval`, `Debouncer`, `Environment.runLater`, `PendingResult`) und vermeidet die Laufzeitfallen von rohen `java.util.Timer`, `javax.swing.Timer` oder Threads, die außerhalb der webforJ-Umgebung erstellt wurden, die alle `IllegalStateException` werfen, sobald sie ein UI-Element berühren.

**Wann es aktiv wird**

- *"Aktualisieren Sie dieses Dashboard alle 30 Sekunden."*
- *"Fügen Sie einen Debouncer für die Suche beim Tippen hinzu."*
- *"Führen Sie diese CPU-intensiven Arbeiten im Hintergrund aus und aktualisieren Sie die Fortschrittsanzeige."*
- *"Pollen Sie diesen REST-Endpunkt, bis er `done` zurückgibt."*

</div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>webforj-localizing-apps</code></strong>: i18n und Übersetzungsunterstützung hinzufügen
  </AccordionSummary>
  <AccordionDetails>
    <div>

Verwenden Sie dies für alle Internationalisierungsarbeiten: Laden von Nachrichtenbündeln, Wechseln von Sprachen zur Laufzeit, automatisches Erkennen der Browsersprache des Benutzers und Übersetzen von Komponentenlabels. Der Assistent organisiert über den `BundleTranslationResolver` von webforJ 25.12, die `HasTranslation`-Sorge, `LocaleObserver` und steckbare benutzerdefinierte Resolver und deckt sowohl Spring- als auch einfache webforJ-Pfade ab.

**Wann es aktiv wird**

- *"Fügen Sie Mehrsprachigkeit mit Englisch und Spanisch hinzu."*
- *"Erkennen Sie die Browsersprache des Benutzers und wenden Sie sie beim Start an."*
- *"Fügen Sie einen Sprachumschalter zur Navigationsleiste hinzu."*
- *"Bewegen Sie alle hartcodierten Strings in ein Nachrichtenbündel."*

</div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>webforj-securing-apps</code></strong>: Routen mit Login und rollenbasierter Zugriffsschutz absichern
  </AccordionSummary>
  <AccordionDetails>
    <div>

Verwenden Sie dies für alles, was Routen in einer webforJ-App schützt: Login und Logout, rollenbasierter Zugriff, öffentliche Landingpages, nur für Administratoren zugängliche Bereiche, Eigentumsregeln und sichere Standards. Der Assistent bevorzugt Spring Security, wenn Spring Boot im Klassenpfad vorhanden ist, und greift andernfalls auf das einfache Sicherheitsframework von webforJ zurück. Er wendet die richtigen Annotations (`@AnonymousAccess`, `@PermitAll`, `@RolesAllowed`, `@RouteAccess`, `@RegisteredEvaluator`) an und erklärt, welche terminal und welche zusammensetzbar sind, damit sicherheitsrelevante Standards ihre Funktion erfüllen.

**Wann es aktiv wird**

- *"Sichern Sie `/admin`, damit nur Benutzer mit der `ADMIN`-Rolle es sehen können."*
- *"Fügen Sie eine öffentliche Landingpage hinzu, die jeder besuchen kann."*
- *"Zeigen Sie den Namen des angemeldeten Benutzers in der Kopfzeile an."*
- *"Erlauben Sie nur dem Benutzer, einen Datensatz zu bearbeiten, den er besitzt."*

</div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>webforj-styling-apps</code></strong>: Apps mit DWC-Design-Token gestalten
  </AccordionSummary>
  <AccordionDetails>
    <div>

Verwenden Sie dies für alle visuellen Arbeiten an einer webforJ-App: Farbpalettenanpassungen, Styling auf Komponentenebene, Layout und Abstände, Typografie, vollständige Themen, Erscheinungsbild von Tabellen oder koordinierte Google Charts-Farben. Der Assistent schreibt CSS, das an DWC-Design-Tokens hält, wählt Selektoren korrekt aus und validiert jedes `--dwc-*` Referenz gegen den echten Katalog für Ihre webforJ-Version - damit der Dunkelmodus und das Wechseln von Themen weiterhin funktionieren.

**Wann es aktiv wird**

- *"Gestalte diese App mit einer blauen Palette."*
- *"Stylen Sie den dwc-button, um den Markenrichtlinien zu entsprechen."*
- *"Machen Sie dieses Layout enger - passen Sie Abstände und Typografie an."*

</div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>webforj-upgrading-versions</code></strong>: Upgrade über webforJ-Major-Versionen mit OpenRewrite
  </AccordionSummary>
  <AccordionDetails>
    <div>

Verwenden Sie dies für Hauptversions-Upgrades. Der Assistent führt das offizielle `webforj-rewrite` OpenRewrite-Rezept für die Zielversion aus, das `<webforj.version>` und die Java-Version erhöht, umbenannte APIs und Typen umschreibt und `TODO webforJ <major>:` Kommentare an jeder entfernten Methode einfügt, die eine manuelle Entscheidung benötigt. Für ältere Ziele ohne veröffentlichtes Rezept (zum Beispiel 24 bis 25) führt es Sie durch den manuellen Rückfall.

**Wann es aktiv wird**

- *"Upgrade diese App von webforJ 25 auf 26."*
- *"Führen Sie das Rewrite-Rezept aus und lösen Sie die TODOs."*
- *"Migrieren Sie von webforJ 24 auf 25 manuell, da es kein Rezept gibt."*
- *"Welche entfernten APIs muss ich nach dem Upgrade beheben?"*

</div>
  </AccordionDetails>
</Accordion>

</AccordionGroup>
