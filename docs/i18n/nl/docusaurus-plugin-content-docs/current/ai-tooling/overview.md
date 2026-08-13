---
title: webforJ AI Plugin
sidebar_position: 1
slug: /ai-tooling
description: >-
  Install the webforJ AI plugin to add the MCP server and Agent Skills to Claude
  Code, Copilot, Cursor, Gemini, and Codex in one step.
_i18n_hash: 44bdaad98af3599ab5fcf57c6a4756c1
---
De **webforJ AI-plugin** is de aanbevolen manier om je AI-coderingsassistent te verbinden met webforJ. Eén installatie geeft je assistent de volledige toolkit: live toegang tot webforJ-documentatie, projectopzet, thema-generatie, validatie van ontwerptokens en gestructureerde workflows die het leren aan hoe het alles correct te gebruiken.

## Wat je krijgt {#what-you-get}

Het installeren van de plugin verbindt twee complementaire stukken in één stap:

- **[webforJ MCP-server](/docs/ai-tooling/mcp)** - live tools die de assistent op aanvraag kan gebruiken: gegevens opzoeken in de webforJ-kennisbank, Maven-projecten opzetten, DWC-thema's genereren, het stijloppervlak van elk DWC-component lezen en `--dwc-*` tokens valideren voordat ze in je CSS terechtkomen.
- **[Agent Skills](/docs/ai-tooling/agent-skills)** - gestructureerde workflows die de assistent vertellen _wanneer_ deze tools te gebruiken, in welke volgorde dingen te doen en hoe het resultaat te valideren. Behandelt het bouwen van herbruikbare componenten en het stylen van webforJ-apps van begin tot eind.

Samen veranderen ze een AI-assistent die gissingen doet over webforJ-conventies in een die deze volgt.

Naast hen levert webforJ een assistent van een ander soort:

- **[craftforJ Assistant](/docs/ai-tooling/craftforj-assistant)** - een coderingsagent die binnen je *lopende* app werkt in plaats van in je editor. Het schrijft Java vrij, compilet elke wijziging voordat je deze ziet, past het toe en blijft werken nadat je app opnieuw is opgestart, terwijl het de live componentenboom leest, eigenschappen wijzigt, routes navigeert en het thema aanpast. Er is niets te installeren, omdat het met webforJ wordt meegeleverd.

:::warning AI Kan Nog Steeds Fouten Maken
Zelfs met de plugin kunnen AI-assistenten foutieve code genereren in complexe scenario's. Controleer en test altijd de gegenereerde code voordat je deze verzendt.
:::

## Installatie {#installation}

<Tabs groupId="ide">
<TabItem value="claude-code" label="Claude Code" default>

```bash
claude plugin marketplace add webforj/webforj-ai
claude plugin install webforj@webforj-ai
```

Controleer binnen Claude Code:

```
/plugin
/mcp
```

De `webforj` plugin verschijnt onder **Geïnstalleerd**. De MCP-server verschijnt als `plugin:webforj:webforj-mcp` onder verbonden servers.

</TabItem>
<TabItem value="copilot-cli" label="GitHub Copilot CLI">

```bash
copilot plugin marketplace add webforj/webforj-ai
copilot plugin install webforj@webforj-ai
```

Controleer:

```bash
copilot plugin list
```

</TabItem>
<TabItem value="vscode" label="VS Code + Copilot">

Voer vanuit de opdrachtpalet `Chat: Install Plugin From Source` uit, en plak dan:

```
webforj/webforj-ai
```

</TabItem>
<TabItem value="gemini" label="Gemini CLI">

```bash
gemini extensions install https://github.com/webforj/webforj-ai
```

Controleer:

```bash
gemini extensions list
```

</TabItem>
<TabItem value="codex" label="OpenAI Codex CLI">

```bash
codex plugin marketplace add webforj/webforj-ai
```

Open vervolgens een Codex-sessie, voer `/plugins` uit, selecteer `webforj`, en druk op **Spatie** om deze in te schakelen.

Codex laadt geen vaardigheden automatisch op basis van promptovereenkomsten zoals andere clients. Roep ze expliciet aan:
Codex laadt geen vaardigheden automatisch op basis van promptovereenkomsten zoals andere clients. Roep ze expliciet aan:

```
$webforj:webforj-styling-apps theme this app with a blue palette
$webforj:webforj-creating-components wrap this Custom Element as a webforJ component
```

MCP-tools werken automatisch zonder de `$`-prefix.

</TabItem>
</Tabs>

### Andere clients {#other-clients}

Cursor, Kiro, Goose, Junie, Antigravity en elke andere Agent Skills-compatibele client ondersteunen ook de plugin - zij gebruiken echter handmatige configuratie in plaats van een marktplaatsopdracht. Zie de [per-client installatiewijzer](https://github.com/webforj/webforj-ai#clients) voor de exacte stappen.

## Het gebruiken {#using-it}

Zodra het is geïnstalleerd, laden de meeste assistenten automatisch het juiste onderdeel op basis van je prompt:

- *"Wrap this Custom Element library as a webforJ component."* - activeert de creating-components vaardigheid
- *"Style this view with the DWC design tokens."* - activeert de styling-apps vaardigheid
- *"Scaffold a new webforJ sidemenu project called CustomerPortal."* - roept de MCP projectopzet aan
- *"Generate a theme from brand color `#6366f1`."* - roept de MCP thema-generator aan
- *"Find the webforJ docs on `@Route` and routing."* - roept de MCP kenniszoekfunctie aan

Voor de beste resultaten, noem altijd **webforJ** in je prompts - dat is het teken dat de assistent gebruikt om naar de plugin te reiken in plaats van naar algemene Java-kennis.

## Bijwerken en verwijderen {#updating-and-uninstalling}

Elke ondersteunde client heeft zijn eigen update- en verwijdercommando's. Zie de [webforj-ai README](https://github.com/webforj/webforj-ai#clients) voor per-client instructies.
