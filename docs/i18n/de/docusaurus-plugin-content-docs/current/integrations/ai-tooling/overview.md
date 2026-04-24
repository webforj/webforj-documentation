---
title: webforJ AI Plugin
sidebar_position: 1
slug: /integrations/ai-tooling
sidebar_class_name: new-content
_i18n_hash: e02b32f83a943a803532854ffd334a9b
---
Das **webforJ AI-Plugin** ist die empfohlene Methode, um Ihren KI-Coding-Assistenten mit webforJ zu verbinden. Eine Installation gibt Ihrem Assistenten das vollständige Toolkit: Live-Zugriff auf die webforJ-Dokumentation, Projektaufbau, Themen-Generierung, Validierung von Design-Token und strukturierte Workflows, die ihm beibringen, wie man all dies korrekt verwendet.

## Was Sie erhalten {#what-you-get}

Die Installation des Plugins verbindet zwei komplementäre Elemente in einem einzigen Schritt:

- **[webforJ MCP-Server](/docs/integrations/ai-tooling/mcp)** - Live-Tools, auf die der Assistent auf Anfrage zugreifen kann: in der webforJ-Wissensdatenbank nachschlagen, Maven-Projekte erstellen, DWC-Themen generieren, die Styling-Oberfläche eines DWC-Komponenten lesen und `--dwc-*` Tokens validieren, bevor sie in Ihrem CSS landen.
- **[Agent Skills](/docs/integrations/ai-tooling/agent-skills)** - strukturierte Workflows, die dem Assistenten sagen, _wann_ er auf diese Tools zugreifen soll, in welcher Reihenfolge die Dinge zu erledigen sind und wie man das Ergebnis validiert. Umfasst den Aufbau von wiederverwendbaren Komponenten und das Styling von webforJ-Apps von Anfang bis Ende.

Zusammen verwandeln sie einen KI-Assistenten, der Vermutungen zu webforJ-Konventionen anstellt, in einen, der sie befolgt.

:::warning KI kann weiterhin Fehler machen
Selbst mit dem Plugin können KI-Assistenten in komplexen Szenarien falschen Code erzeugen. Überprüfen und testen Sie always den generierten Code, bevor Sie ihn veröffentlichen.
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

Führen Sie über die Befehlspalette `Chat: Plugin von Quelle installieren` aus und fügen Sie dann ein:

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

Öffnen Sie dann eine Codex-Sitzung, führen Sie `/plugins` aus, wählen Sie `webforj` aus und drücken Sie **Leertaste**, um es zu aktivieren.

Codex lädt keine Skills automatisch durch Prompt-Match wie andere Clients. Rufen Sie sie explizit auf:

```
$webforj:webforj-styling-apps gestalten Sie diese App mit einer blauen Farbpalette
$webforj:webforj-creating-components wickeln Sie dieses benutzerdefinierte Element als webforJ-Komponente
```

MCP-Tools funktionieren automatisch ohne das `$`-Präfix.

</TabItem>
</Tabs>

### Andere Clients {#other-clients}

Cursor, Kiro, Goose, Junie, Antigravity und jeder andere Agent Skills-kompatible Client unterstützen ebenfalls das Plugin - sie verwenden nur manuelle Konfiguration statt eines Marktplatzbefehls. Siehe die [pro Client Installationsanleitung](https://github.com/webforj/webforj-ai#clients) für die genauen Schritte.

## Verwendung {#using-it}

Nach der Installation laden die meisten Assistenten das richtige Element automatisch basierend auf Ihrem Prompt:

- *"Wickeln Sie diese benutzerdefinierte Elementbibliothek als webforJ-Komponente."* - aktiviert den creating-components Skill
- *"Stylen Sie diese Ansicht mit den DWC-Design-Token."* - aktiviert den styling-apps Skill
- *"Scaffolding ein neues webforJ Sidemenu-Projekt namens CustomerPortal."* - ruft den MCP-Projekt-Scaffolder auf
- *"Generieren Sie ein Thema aus der Markenfarbe `#6366f1`."* - ruft den MCP-Themengenerator auf
- *"Finden Sie die webforJ-Dokumentation zu `@Route` und Routing."* - ruft die MCP-Wissenssuche auf

Für die besten Ergebnisse sollten Sie immer **webforJ** in Ihren Prompts erwähnen - das ist das Signal, das der Assistent verwendet, um auf das Plugin zuzugreifen, anstatt auf allgemeines Java-Wissen zurückzugreifen.

## Aktualisierung und Deinstallation {#updating-and-uninstalling}

Jeder unterstützte Client hat seine eigenen Befehle zum Aktualisieren und Deinstallieren. Siehe die [webforj-ai README](https://github.com/webforj/webforj-ai#clients) für Anweisungen pro Client.
