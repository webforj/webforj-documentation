---
title: webforJ AI Plugin
sidebar_position: 1
slug: /integrations/ai-tooling
sidebar_class_name: new-content
_i18n_hash: e02b32f83a943a803532854ffd334a9b
---
De **webforJ AI-plugin** is de aanbevolen manier om uw AI-coderingsassistent met webforJ te verbinden. Eén installatie geeft uw assistent de volledige toolkit: live toegang tot webforJ-documentatie, projectstructurering, thema-generatie, validatie van ontwerp-tokens en gestructureerde workflows die het leren hoe alles correct te gebruiken.

## Wat je krijgt {#what-you-get}

Het installeren van de plugin verbindt twee complementaire onderdelen in één stap:

- **[webforJ MCP-server](/docs/integrations/ai-tooling/mcp)** - live tools die de assistent op aanvraag kan gebruiken: zoek dingen op in de webforJ-kennisdatabase, scaffold Maven-projecten, genereer DWC-thema's, lees het stijlinventaris van elke DWC-component en valideer `--dwc-*` tokens voordat ze in uw CSS terechtkomen.
- **[Agent Skills](/docs/integrations/ai-tooling/agent-skills)** - gestructureerde workflows die de assistent vertellen _wanneer_ hij naar die tools moet grijpen, in welke volgorde taken moeten worden uitgevoerd en hoe het resultaat moet worden gevalideerd. Behandelt het bouwen van herbruikbare componenten en het stylen van webforJ-apps van begin tot eind.

Samen transformeren ze een AI-assistent die gissingen doet over webforJ-conventies in een die ze volgt.

:::warning AI kan nog steeds fouten maken
Zelfs met de plugin kunnen AI-assistenten in complexe scenario's onjuiste code genereren. Controleer en test altijd de gegenereerde code voordat u deze verzendt.
:::

## Installatie {#installation}

<Tabs groupId="ide">
<TabItem value="claude-code" label="Claude Code" default>

```bash
claude plugin marketplace add webforj/webforj-ai
claude plugin install webforj@webforj-ai
```

Controleer in Claude Code:

```
/plugin
/mcp
```

De `webforj`-plugin verschijnt onder **Geïnstalleerd**. De MCP-server verschijnt als `plugin:webforj:webforj-mcp` onder verbonden servers.

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

Open vervolgens een Codex-sessie, voer `/plugins` uit, selecteer `webforj` en druk op **Spatie** om deze in te schakelen.

Codex laadt geen vaardigheden automatisch op basis van promptovereenkomsten zoals andere cliënten. Roep ze expliciet op:

```
$webforj:webforj-styling-apps thema deze app met een blauw palet
$webforj:webforj-creating-components wikkel dit aangepaste element als een webforJ-component
```

MCP-tools werken automatisch zonder de `$`-prefix.

</TabItem>
</Tabs>

### Andere cliënten {#other-clients}

Cursor, Kiro, Goose, Junie, Antigravity en elke andere client die compatibel is met Agent Skills ondersteunt ook de plugin - ze gebruiken gewoon handmatige configuratie in plaats van een marktplaatsopdracht. Zie de [per-client installatiehandleiding](https://github.com/webforj/webforj-ai#clients) voor de exacte stappen.

## Gebruik {#using-it}

Eenmaal geïnstalleerd, laden de meeste assistenten het juiste onderdeel automatisch op basis van uw prompt:

- *"Wikkel deze aangepaste Elementbibliotheek als een webforJ-component."* - activeert de creating-components vaardigheid
- *"Stijl deze weergave met de DWC-ontwerp-tokens."* - activeert de styling-apps vaardigheid
- *"Scaffold een nieuw webforJ-sidemenu project genaamd CustomerPortal."* - roept de MCP-projectstructurering aan
- *"Genereer een thema van merk kleur `#6366f1`."* - roept de MCP-thema-generator aan
- *"Vind de webforJ-documentatie over `@Route` en routering."* - roept de MCP-kenniszoekfunctie aan

Voor de beste resultaten, noem altijd **webforJ** in uw prompts - dat is het signaal dat de assistent gebruikt om naar de plugin te grijpen in plaats van naar algemene Java-kennis.

## Bijwerken en verwijderen {#updating-and-uninstalling}

Elke ondersteunde client heeft zijn eigen update- en verwijdercommando's. Zie de [webforj-ai README](https://github.com/webforj/webforj-ai#clients) voor per-client instructies.
