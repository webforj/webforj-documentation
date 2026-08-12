---
title: webforJ AI Plugin
sidebar_position: 1
slug: /ai-tooling
description: >-
  Install the webforJ AI plugin to add the MCP server and Agent Skills to Claude
  Code, Copilot, Cursor, Gemini, and Codex in one step.
_i18n_hash: 44bdaad98af3599ab5fcf57c6a4756c1
---
Das **webforJ AI-Plugin** ist die empfohlene Methode, um Ihren AI-Coding-Assistenten mit webforJ zu verbinden. Eine Installation gibt Ihrem Assistenten das volle Werkzeugset: direkten Zugang zu webforJ-Dokumenten, Projekt-Scaffolding, Theme-Generierung, Validierung von Design-Token und strukturierte Workflows, die ihm beibringen, wie man all dies korrekt nutzt.

## Was Sie erhalten {#what-you-get}

Die Installation des Plugins verbindet in einem einzigen Schritt zwei komplementäre Teile:

- **[webforJ MCP-Server](/docs/ai-tooling/mcp)** - Live-Tools, auf die der Assistent bei Bedarf zugreifen kann: Informationen in der webforJ-Wissensdatenbank abrufen, Maven-Projekte aufsetzen, DWC-Themen generieren, die Styling-Oberfläche eines beliebigen DWC-Komponenten lesen und `--dwc-*` Token validieren, bevor sie in Ihrem CSS landen.
- **[Agent Skills](/docs/ai-tooling/agent-skills)** - strukturierte Workflows, die dem Assistenten sagen, _wann_ er auf diese Tools zugreifen soll, in welcher Reihenfolge er die Dinge erledigen soll und wie er das Ergebnis validieren soll. Deckt den Aufbau wiederverwendbarer Komponenten und das Styling von webforJ-Anwendungen von Ende zu Ende ab.

Gemeinsam verwandeln sie einen AI-Assistenten, der Vermutungen über webforJ-Konventionen anstellt, in einen, der sie befolgt.

Zusätzlich liefert webforJ einen Assistenten anderer Art:

- **[craftforJ Assistant](/docs/ai-tooling/craftforj-assistant)** - ein Codierungsagent, der innerhalb Ihrer *laufenden* Anwendung anstatt in Ihrem Editor arbeitet. Er schreibt Java frei, kompiliert jede Bearbeitung, bevor Sie sie sehen, wendet sie an und arbeitet weiter, nachdem Ihre App neu gestartet wurde, während er den Live-Komponentenbaum liest, Eigenschaften ändert, Routen navigiert und das Theme anpasst. Es gibt nichts zu installieren, da er mit webforJ ausgeliefert wird.

:::warning AI kann immer noch Fehler machen
Selbst mit dem Plugin können AI-Assistenten in komplexen Szenarien falschen Code erzeugen. Überprüfen und testen Sie immer den generierten Code, bevor Sie ihn bereitstellen.
:::

## Installation {#installation}

<Tabs groupId="ide">
<TabItem value="claude-code" label="Claude Code" default>

```bash
claude plugin marketplace add webforj/webforj-ai
claude plugin install webforj@webforj-ai
```

Überprüfen Sie in Claude Code:

```
/plugin
/mcp
```

Das `webforj`-Plugin erscheint unter **Installiert**. Der MCP-Server erscheint als `plugin:webforj:webforj-mcp` unter den verbundenen Servern.

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

Öffnen Sie die Befehls-Palette und führen Sie `Chat: Install Plugin From Source` aus, und fügen Sie dann ein:

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

Öffnen Sie dann eine Codex-Session, führen Sie `/plugins` aus, wählen Sie `webforj` und drücken Sie **Leerstaste**, um es zu aktivieren.

Codex lädt keine Skills automatisch anhand der Eingabeaufforderung wie andere Clients. Rufen Sie sie explizit auf:
```
$webforj:webforj-styling-apps theme this app with a blue palette
$webforj:webforj-creating-components wrap this Custom Element as a webforJ component
```

MCP-Tools funktionieren automatisch ohne das `$`-Präfix.

</TabItem>
</Tabs>

### Andere Clients {#other-clients}

Cursor, Kiro, Goose, Junie, Antigravity und jeder andere Agent Skills-kompatible Client unterstützen ebenfalls das Plugin - sie verwenden einfach manuelle Konfiguration anstelle eines Markt-Befehls. Siehe die [Installationsanleitung pro Client](https://github.com/webforj/webforj-ai#clients) für die genauen Schritte.

## Nutzung {#using-it}

Sobald es installiert ist, laden die meisten Assistenten automatisch das richtige Stück basierend auf Ihrer Eingabeaufforderung:

- *"Wrap this Custom Element library as a webforJ component."* - löst den creating-components Skill aus
- *"Style this view with the DWC design tokens."* - löst den styling-apps Skill aus
- *"Scaffold a new webforJ sidemenu project called CustomerPortal."* - ruft den MCP-Projekt-Scaffolder auf
- *"Generate a theme from brand color `#6366f1`."* - ruft den MCP-Themengenerator auf
- *"Find the webforJ docs on `@Route` and routing."* - ruft die MCP-Wissenssuche auf

Für die besten Ergebnisse sollten Sie immer **webforJ** in Ihren Eingabeaufforderungen erwähnen - das ist das Signal, das der Assistent verwendet, um das Plugin anstelle von allgemeinem Java-Wissen zu verwenden.

## Aktualisieren und Deinstallieren {#updating-and-uninstalling}

Jeder unterstützte Client hat seine eigenen Aktualisierungs- und Deinstallationsbefehle. Siehe die [webforj-ai README](https://github.com/webforj/webforj-ai#clients) für die Anweisungen pro Client.
