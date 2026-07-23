---
title: webforJ AI Plugin
sidebar_position: 1
slug: /ai-tooling
sidebar_class_name: new-content
description: >-
  Install the webforJ AI plugin to add the MCP server and Agent Skills to Claude
  Code, Copilot, Cursor, Gemini, and Codex in one step.
_i18n_hash: db80016ad151e338c6e353caaa7070d9
---
Das **webforJ AI-Plugin** ist die empfohlene Möglichkeit, um deinen AI-Coding-Assistenten mit webforJ zu verbinden. Mit einer Installation erhält dein Assistent das vollständige Toolkit: Live-Zugriff auf die webforJ-Dokumentation, Projekt-Scaffolding, Theme-Generierung, Validierung von Designtokens und strukturierte Workflows, die ihm beibringen, all dies korrekt zu nutzen.

## Was du bekommst {#what-you-get}

Die Installation des Plugins verbindet zwei komplementäre Komponenten in einem einzigen Schritt:

- **[webforJ MCP-Server](/docs/ai-tooling/mcp)** - Live-Tools, die der Assistent auf Abruf nutzen kann: Informationen in der webforJ-Wissensdatenbank nachschlagen, Maven-Projekte scaffolden, DWC-Themen generieren, die Styling-Oberfläche von DWC-Komponenten lesen und `--dwc-*` Tokens validieren, bevor sie in dein CSS gelangen.
- **[Agent Skills](/docs/ai-tooling/agent-skills)** - strukturierte Workflows, die dem Assistenten sagen, _wann_ er auf diese Tools zurückgreifen soll, in welcher Reihenfolge die Dinge zu tun sind und wie das Ergebnis validiert wird. Es umfasst den Bau wiederverwendbarer Komponenten und das Styling von webforJ-Apps von Anfang bis Ende.

Zusammen verwandeln sie einen AI-Assistenten, der Vermutungen über webforJ-Konventionen anstellt, in einen, der ihnen folgt.

:::warning AI kann immer noch Fehler machen
Selbst mit dem Plugin können AI-Assistenten in komplexen Szenarien fehlerhaften Code erzeugen. Überprüfe und teste immer den generierten Code, bevor du ihn versendest.
:::

## Installation {#installation}

<Tabs groupId="ide">
<TabItem value="claude-code" label="Claude Code" default>

```bash
claude plugin marketplace add webforj/webforj-ai
claude plugin install webforj@webforj-ai
```

Überprüfe innerhalb von Claude Code:

```
/plugin
/mcp
```

Das `webforj` Plugin erscheint unter **Installiert**. Der MCP-Server erscheint als `plugin:webforj:webforj-mcp` unter verbundenen Servern.

</TabItem>
<TabItem value="copilot-cli" label="GitHub Copilot CLI">

```bash
copilot plugin marketplace add webforj/webforj-ai
copilot plugin install webforj@webforj-ai
```

Überprüfen:

```bash
copilot plugin list
```

</TabItem>
<TabItem value="vscode" label="VS Code + Copilot">

Öffne die Befehls-Palette und führe `Chat: Install Plugin From Source` aus, und füge dann ein:

```
webforj/webforj-ai
```

</TabItem>
<TabItem value="gemini" label="Gemini CLI">

```bash
gemini extensions install https://github.com/webforj/webforj-ai
```

Überprüfen:

```bash
gemini extensions list
```

</TabItem>
<TabItem value="codex" label="OpenAI Codex CLI">

```bash
codex plugin marketplace add webforj/webforj-ai
```

Öffne dann eine Codex-Sitzung, führe `/plugins` aus, wähle `webforj` aus und drücke **Leertaste**, um es zu aktivieren.

Codex lädt Fähigkeiten nicht automatisch anhand des Eingabeaufforderung-Match wie andere Clients. Rufe sie explizit auf:
Codex lädt Fähigkeiten nicht automatisch anhand des Eingabeaufforderung-Match wie andere Clients. Rufe sie explizit auf:

```
$webforj:webforj-styling-apps theme this app with a blue palette
$webforj:webforj-creating-components wrap this Custom Element as a webforJ component
```

MCP-Tools funktionieren automatisch ohne das `$`-Präfix.

</TabItem>
</Tabs>

### Andere Clients {#other-clients}

Cursor, Kiro, Goose, Junie, Antigravity und jeder andere Client, der mit Agent Skills kompatibel ist, unterstützt ebenfalls das Plugin - sie verwenden jedoch manuelle Konfiguration anstelle eines Marktplatzbefehls. Siehe die [Installationsanleitung pro Client](https://github.com/webforj/webforj-ai#clients) für die genauen Schritte.

## Verwendung {#using-it}

Nach der Installation laden die meisten Assistenten automatisch das richtige Stück basierend auf deinem Prompt:

- *"Wickel diese benutzerdefinierte Elementbibliothek als eine webforJ-Komponente ein."* - löst die creating-components Fähigkeit aus
- *"Style diese Ansicht mit den DWC-Design-Tokens."* - löst die styling-apps Fähigkeit aus
- *"Scaffold ein neues webforJ-Sidemenu-Projekt mit dem Namen CustomerPortal."* - ruft den MCP-Projekt-Scaffolder auf
- *"Generiere ein Theme aus der Markenfarbe `#6366f1`."* - ruft den MCP-Theme-Generator auf
- *"Finde die webforJ-Dokumentation zu `@Route` und Routing."* - ruft die MCP-Wissen-Suche auf

Für die besten Ergebnisse solltest du immer **webforJ** in deiner Eingabeaufforderung erwähnen - das ist das Signal, das der Assistent verwendet, um auf das Plugin zuzugreifen, anstelle von allgemeinem Java-Wissen.

## Aktualisierung und Deinstallation {#updating-and-uninstalling}

Jeder unterstützte Client hat seine eigenen Befehle zum Aktualisieren und Deinstallieren. Siehe die [webforj-ai README](https://github.com/webforj/webforj-ai#clients) für Anweisungen pro Client.
