---
title: MCP Server
sidebar_position: 5
_i18n_hash: eea9d8f962b10512151bf7c6935f25e0
---
De webforJ Model Context Protocol (MCP) server sluit AI code-assistenten aan op de documentatie, API's, ontwerptokens en scaffoldingtools van webforJ. In plaats van te gokken naar conventies van het framework, vraagt de assistent de server en krijgt antwoorden die zijn verankerd in de echte webforJ.

:::tip Gebruik de plugin
Tenzij je zeker weet dat je alleen de MCP-server wilt, installeer dan de **[webforJ AI plugin](/docs/integrations/ai-tooling)** in plaats daarvan - het bundelt deze server met de bijpassende [Agent Skills](/docs/integrations/ai-tooling/agent-skills) in één installatie.
:::

## Wat is een MCP? {#whats-an-mcp}

Model Context Protocol is een open standaard die AI-assistenten in staat stelt om op aanvraag externe tools aan te roepen. De webforJ MCP-server implementeert dit protocol, zodat je assistent kan:

- Zoeken in de webforJ-documentatie in plaats van methodenamen te hallucinereren
- Nieuwe webforJ-projecten bouwen uit officiële Maven-archetypes
- Toegankelijke DWC-thema's genereren op basis van een merk-kleur
- Het echte stylingoppervlak van een DWC-component lezen en elk `--dwc-*` token valideren voordat het in je CSS terechtkomt

:::warning AI kan nog steeds fouten maken
De MCP-server verbetert de nauwkeurigheid aanzienlijk, maar AI-assistenten kunnen nog steeds onjuiste code produceren in complexe scenario's. Controleer en test altijd de gegenereerde code voordat je deze verzendt.
:::

## Installatie {#installation}

Voor de volledige ervaring, installeer de **[webforJ AI plugin](/docs/integrations/ai-tooling)** - het configureert deze server samen met de Agent Skills die je assistent nodig heeft om deze goed te gebruiken.

Als je alleen de MCP-server wilt (geen vaardigheden), wijs je client dan naar `https://mcp.webforj.com/mcp`:

<Tabs groupId="ide">
<TabItem value="claude-code" label="Claude Code" default>

```bash
claude mcp add webforj-mcp https://mcp.webforj.com/mcp -t http -s user
```

</TabItem>
<TabItem value="copilot-cli" label="GitHub Copilot CLI">

Het aanbevolen pad op Copilot CLI is de **[webforJ AI plugin](/docs/integrations/ai-tooling)** - het registreert de MCP-server voor jou in één stap. Voor een ruwe MCP-only setup, zie de instructies per client in de [webforJ AI-repository](https://github.com/webforj/webforj-ai#clients).

</TabItem>
<TabItem value="vscode" label="VS Code + Copilot">

Voeg toe aan je VS Code-instellingen:

```json
"mcp": {
  "servers": {
    "webforj-mcp": {
      "url": "https://mcp.webforj.com/mcp"
    }
  }
}
```

</TabItem>
<TabItem value="gemini" label="Gemini CLI">

Voeg toe aan `~/.gemini/settings.json`:

```json
{
  "mcpServers": {
    "webforj-mcp": {
      "httpUrl": "https://mcp.webforj.com/mcp"
    }
  }
}
```

</TabItem>
<TabItem value="codex" label="OpenAI Codex CLI">

Voeg toe aan `~/.codex/config.toml`:

```toml
[mcp_servers.webforj-mcp]
url = "https://mcp.webforj.com/mcp"
```

</TabItem>
</Tabs>

### Andere clients {#other-clients}

Cursor, Kiro, Goose, Junie, Antigravity en elke andere MCP-over-HTTP-client werken ook - ze gebruiken alleen hun eigen configuratieformaat. Zie de [per-client installatiegids](https://github.com/webforj/webforj-ai#clients) voor de exacte snippet voor elk.

## Wat de server kan doen {#capabilities}

Wanneer de MCP-server is verbonden, krijgt je AI-assistent de volgende mogelijkheden. Een van deze kan worden geactiveerd door een verzoek in natuurlijke taal - de assistent kiest automatisch de juiste.

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>Richt je op de juiste webforJ-versie</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Voordat de assistent versiegevoelige vragen beantwoordt (iets dat met styling of API te maken heeft), bepaalt de assistent welke webforJ-versie je gebruikt. Het leest `pom.xml` wanneer beschikbaar en vraagt anders aan jou. Elk daaropvolgend antwoord is afgebakend naar die versie.
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>Zoeken in de webforJ-kennisbank</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      De assistent kan de volledige webforJ-kennisbank doorzoeken voor antwoorden die zijn verankerd in het echte framework. Resultaten zijn afgebakend op basis van wat je vraagt - een API-vraag, een gids, een codevoorbeeld of de Kotlin DSL.

      **Voorbeeld prompts:**
      ```
      "Vind de webforJ Button-component event handling voorbeelden"

      "Hoe stel ik routing in met @Route in webforJ?"

      "Toon me een webforJ formulier validatie voorbeeld"
      ```
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>Bouw een nieuw webforJ-project</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      De assistent genereert het juiste Maven-archetype commando voor een nieuwe webforJ-app op basis van jouw vereisten (archetype, Spring-integratie, naam, groep).

      **Archetypes:**
      - `hello-world` - starter-app met voorbeeldcomponenten
      - `blank` - minimale projectstructuur
      - `tabs` - tabinterface lay-out
      - `sidemenu` - zijmenu lay-out

      **Varianten:**
      - `webforj` - standaard webforJ-app
      - `webforj-spring` - webforJ geïntegreerd met Spring Boot

      **Voorbeeld prompts:**
      ```
      "Creëer een webforJ-project genaamd CustomerPortal met het sidemenu-archetype"

      "Genereer een webforJ Spring Boot-project met de tabbladenlay-out genaamd Dashboard"
      ```

      :::tip Beschikbare Archetypes
      Voor de volledige lijst van archetypes, zie het [archetypes catalogus](/docs/building-ui/archetypes/overview).
      :::
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>Genereer een DWC-thema</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Vanuit een enkele merk-kleur produceert de assistent een compleet DWC-thema: primaire, succes, waarschuwing, gevaar, info, standaard, en grijze paletten met automatische tekstcontrast. De uitvoer bevat de stylesheet plus de `@AppTheme` / `@StyleSheet` bedrading.

      **Voorbeeld prompts:**
      ```
      "Genereer een webforJ-thema van merk-kleur #6366f1"

      "Creëer een toegankelijk thema met HSL 220, 70, 50 als primaire kleur"
      ```
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>Stijl DWC-componenten correct</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      De assistent leest het echte stylingoppervlak van elke DWC-component - CSS aangepaste eigenschappen, schaduwonderdelen, weerspiegelde attributen en slots - voordat hij CSS schrijft. Hij kan ook elke DWC-tag opsommen en webforJ Java-klassenamen (`Button`, `TextField`) naar hun DWC-equivalenten omzetten.

      **Voorbeeld prompts:**
      ```
      "Welke CSS-variabelen en onderdelen exposeert dwc-button?"

      "Toon me elke beschikbare slot op dwc-dialog"

      "Welke DWC-tag komt overeen met de webforJ TextField-klasse?"
      ```

      Combineer dit met de [styling-apps agent skill](/docs/integrations/ai-tooling/agent-skills) voor end-to-end styling workflows.
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>Werken met DWC ontwerptokens</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      De assistent kan de autoritatieve catalogus van `--dwc-*` tokens voor jouw webforJ-versie opsommen - paletzaden, tinten, oppervlakken, ruimte, typografie, randen - gefilterd op prefix of substring. Hij valideert ook elke CSS, Java of Markdown-bron die je hem geeft tegen de echte token-catalogus en markeert onbekende namen met voorgestelde correcties.

      **Voorbeeld prompts:**
      ```
      "Lijst elke --dwc-space-* token op"

      "Valideer app.css op onbekende --dwc-* tokens"

      "Welke primaire palet tinten zijn beschikbaar?"
      ```

      Validatie vangt typfouten en verzonnen tokens voordat ze als stilzwijgend falende CSS worden verzonden.
    </div>
  </AccordionDetails>
</Accordion>

## Goede prompts schrijven {#writing-good-prompts}

De MCP-server wordt alleen geraadpleegd wanneer je assistent denkt dat het relevant is. Een paar gewoonten houden het betrokken:

- **Noem het framework.** Vermeld "webforJ" in de prompt, zodat de assistent de MCP-server kan bereiken in plaats van zijn algemene Java-kennis.
- **Wees specifiek.** `"Creëer een webforJ-project genaamd InventorySystem met het sidemenu-archetype en Spring Boot"` is beter dan `"maak een app"`.
- **Vraag om verificatie.** Zinnen zoals `"verifieer tegen webforJ-documentatie"` of `"controleer deze CSS op slechte --dwc-* tokens"` duwen de assistent om de tools te gebruiken in plaats van te gokken.

Als je assistent nog steeds antwoord geeft zonder de server te raadplegen, installeer dan de [webforJ AI plugin](https://github.com/webforj/webforj-ai) - het levert bijpassende Agent Skills die de assistent automatisch aanmoedigen om de MCP-tools voor webforJ-taken te gebruiken.

## Veelgestelde vragen {#faq}

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>Waarom gebruikt de AI-assistent de MCP-server niet?</p>
    <p>Waarom gebruikt de AI-assistent de MCP-server niet?</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      De meeste assistenten grijpen alleen naar MCP wanneer ze denken dat de vraag het nodig heeft. Twee oplossingen:

      1. **Installeer de [webforJ AI plugin](https://github.com/webforj/webforj-ai)**, die de server koppelt aan Agent Skills die de assistent vertellen om MCP te gebruiken voor webforJ-taken.
      2. **Wees expliciet in je prompt**: voeg "webforJ" toe aan de vraag, en voor hardnekkige gevallen zeg "gebruik de webforJ MCP-server om te antwoorden".
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>Hoe verifieer je of de MCP-verbinding werkt?</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Gebruik de MCP-inspecteur:

      ```bash
      npx @modelcontextprotocol/inspector
      ```

      Verbind daarna in de inspecteur met `https://mcp.webforj.com/mcp` en verken de beschikbare tools.
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>Hoe meld je problemen?</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Open een ticket met de [webforJ MCP probleemtemplate](https://github.com/webforj/webforj/issues/new?template=mcp_report.yml). Vermeld de prompt, het verwachte resultaat en wat je hebt gekregen.
    </div>
  </AccordionDetails>
</Accordion>
<br />
