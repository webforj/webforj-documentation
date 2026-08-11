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
De **webforJ AI-plugin** is de aanbevolen manier om uw AI-coderingsassistent te verbinden met webforJ. Eén installatie geeft uw assistent de volledige toolkit: live toegang tot webforJ-documentatie, projectstructurering, thema-generatie, validatie van ontwerptokens en gestructureerde workflows die het leren hoe het al deze functies correct te gebruiken.

## Wat u krijgt {#what-you-get}

Het installeren van de plugin verbindt twee aanvullende onderdelen in één stap:

- **[webforJ MCP-server](/docs/ai-tooling/mcp)** - live tools die de assistent op verzoek kan aanroepen: opzoeken in de webforJ-kennisdatabase, Maven-projecten structureren, DWC-thema's genereren, de styling-oppervlakte van elk DWC-component lezen en `--dwc-*` tokens validieren voordat ze in uw CSS komen.
- **[Agent Skills](/docs/ai-tooling/agent-skills)** - gestructureerde workflows die de assistent vertellen _wanneer_ hij die tools moet gebruiken, in welke volgorde hij dingen moet doen, en hoe hij het resultaat moet valideren. Dit dekt het bouwen van herbruikbare componenten en het stylen van webforJ-apps van begin tot eind.

Samen transformeren ze een AI-assistent die raadt naar webforJ-conventies in een die ze volgt.

:::warning AI Kan Nog Steeds Fouten Maken
Zelfs met de plugin kan de AI-assistent onjuiste code genereren in complexe scenario's. Controleer en test altijd de gegenereerde code voordat u deze verzendt.
:::

## Installatie {#installation}

<Tabs groupId="ide">
<TabItem value="claude-code" label="Claude Code" default>

```bash
claude plugin marketplace add webforj/webforj-ai
claude plugin install webforj@webforj-ai
```

Verifieer binnen Claude Code:

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

Verifieer:

```bash
copilot plugin list
```

</TabItem>
<TabItem value="vscode" label="VS Code + Copilot">

Voer vanuit de commando-palette `Chat: Install Plugin From Source` uit, en plak vervolgens:

```
webforj/webforj-ai
```

</TabItem>
<TabItem value="gemini" label="Gemini CLI">

```bash
gemini extensions install https://github.com/webforj/webforj-ai
```

Verifieer:

```bash
gemini extensions list
```

</TabItem>
<TabItem value="codex" label="OpenAI Codex CLI">

```bash
codex plugin marketplace add webforj/webforj-ai
```

Open vervolgens een Codex-sessie, voer `/plugins` uit, selecteer `webforj`, en druk op **Spatie** om het in te schakelen.

Codex laadt geen vaardigheden automatisch op basis van promptovereenkomsten zoals andere clients. Activeer ze expliciet:

```
$webforj:webforj-styling-apps thema deze app met een blauw palet
$webforj:webforj-creating-components verpakt dit Aangepaste Element als een webforJ-component
```

MCP-tools werken automatisch zonder het `$`-prefix.

</TabItem>
</Tabs>

### Andere clients {#other-clients}

Cursor, Kiro, Goose, Junie, Antigravity, en elke andere Agent Skills-compatibele client ondersteunt ook de plugin - ze gebruiken alleen handmatige configuratie in plaats van een marktplaatsopdracht. Zie de [per-client installatiewijzer](https://github.com/webforj/webforj-ai#clients) voor de exacte stappen.

## Gebruik {#using-it}

Eenmaal geïnstalleerd, laden de meeste assistenten automatisch het juiste onderdeel op basis van uw prompt:

- *"Verpak deze Aangepaste Element-bibliotheek als een webforJ-component."* - activeert de creating-components vaardigheid
- *"Stijl deze weergave met de DWC-ontwerptokens."* - activeert de styling-apps vaardigheid
- *"Structureren een nieuw webforJ zijmenu project genaamd CustomerPortal."* - roept de MCP projectstructurer op
- *"Genereer een thema vanuit merkkleur `#6366f1`."* - roept de MCP thema-generator aan
- *"Vind de webforJ-documentatie over `@Route` en routing."* - roept de MCP kenniszoekfunctie aan

Voor de beste resultaten, noem altijd **webforJ** in uw prompts - dat is het signaal dat de assistent gebruikt om naar de plugin te zoeken in plaats van algemene Java-kennis.

## Bijwerken en verwijderen {#updating-and-uninstalling}

Elke ondersteunde client heeft zijn eigen update- en verwijderopdrachten. Zie de [webforj-ai README](https://github.com/webforj/webforj-ai#clients) voor instructies per client.
