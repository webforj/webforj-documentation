---
title: MCP Server
sidebar_position: 5
description: >-
  Connect AI assistants to the webforJ MCP server for live documentation lookup,
  project scaffolding, theme generation, and token validation.
_i18n_hash: fb0e068ee7d7a489237e021b24a883b0
---
De webforJ Model Context Protocol (MCP) server sluit AI-codingassistenten aan op de documentatie, API's, design tokens en scaffolding-tools van webforJ. In plaats van te gissen naar frameworkconventies, vraagt de assistent de server en krijgt antwoorden die zijn gebaseerd op het echte webforJ.

:::tip Gebruik de plugin
Tenzij je zeker weet dat je alleen de MCP-server wilt, installeer de **[webforJ AI-plugin](/docs/ai-tooling)** in plaats daarvan - het bundelt deze server met de bijbehorende [Agent Skills](/docs/ai-tooling/agent-skills) in één installatie.
:::

## Wat is een MCP? {#whats-an-mcp}

Model Context Protocol is een open standaard die AI-assistenten in staat stelt externe tools op aanvraag aan te roepen. De webforJ MCP-server implementeert dit protocol, zodat je assistent kan:

- In de webforJ-documentatie zoeken in plaats van te hallucinerende methoden
- Nieuwe webforJ-projecten creëren vanuit officiële Maven-archetypen
- Toegankelijke DWC-thema's genereren op basis van een merk kleur
- Het echte stylingoppervlak van een DWC-component lezen en elk `--dwc-*`-token valideren voordat het in je CSS terechtkomt

:::warning AI kan nog steeds fouten maken
De MCP-server verbetert de nauwkeurigheid aanzienlijk, maar AI-assistenten kunnen nog steeds onjuiste code produceren in complexe scenario's. Controleer en test altijd de gegenereerde code voordat je deze uitrolt.
:::

## Installatie {#installation}

Voor de volledige ervaring, installeer de **[webforJ AI-plugin](/docs/ai-tooling)** - het configureert deze server naast de Agent Skills die je assistent nodig heeft om het goed te gebruiken.

Als je alleen de MCP-server wilt (geen vaardigheden), wijs je client dan naar `https://mcp.webforj.com/mcp`:

<Tabs groupId="ide">
<TabItem value="claude-code" label="Claude Code" default>

```bash
claude mcp add webforj-mcp https://mcp.webforj.com/mcp -t http -s user
```

</TabItem>
<TabItem value="copilot-cli" label="GitHub Copilot CLI">

Het aanbevolen pad op Copilot CLI is de **[webforJ AI-plugin](/docs/ai-tooling)** - het registreert de MCP-server voor je in één stap. Voor een ruwe MCP-only setup, zie de per-client instructies in de [webforJ AI-repository](https://github.com/webforj/webforj-ai#clients).

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

Cursor, Kiro, Goose, Junie, Antigravity, en elke andere MCP-over-HTTP client werkt ook - ze gebruiken gewoon hun eigen configuratieformaat. Zie de [per-client installatiewijzer](https://github.com/webforj/webforj-ai#clients) voor exact fragment voor elk.

## Wat de server kan doen {#capabilities}

Wanneer de MCP-server is verbonden, krijgt je AI-assistent de volgende mogelijkheden. Elk van deze kan worden geactiveerd door een verzoek in natuurlijke taal - de assistent kiest automatisch de juiste.

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>Richt je op de juiste webforJ-versie</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Voordat hij vragen over versiegevoelige onderwerpen beantwoordt (alles dat met styling of API te maken heeft), bepaalt de assistent welke webforJ-versie je gebruikt. Hij leest `pom.xml` als dit beschikbaar is en vraagt anders aan jou. Elk volgend antwoord is beperkt tot die versie.
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>Zoek dingen op in de webforJ-kennisbank</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      De assistent kan de volledige webforJ-kennisbank raadplegen voor antwoorden die zijn gebaseerd op het echte framework. Resultaten zijn beperkt tot waar je naar vraagt - een API-vraag, een handleiding, een codevoorbeeld, of de Kotlin DSL.

      **Voorbeeldvragen:**
      ```
      "Vind de webforJ Button-component evenementafhandelingsvoorbeelden"

      "Hoe configureer ik routing met @Route in webforJ?"

      "Toon me een webforJ-formuliervalidatievoorbeeld"
      ```
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>Creëer een nieuw webforJ-project</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      De assistent genereert het juiste Maven-archetypecommando voor een nieuwe webforJ-app op basis van jouw vereisten (archetype, Spring-integratie, naam, groep).

      **Archetypen:**
      - `hello-world` - starter app met voorbeeldcomponenten
      - `blank` - minimale projectstructuur
      - `tabs` - tabbladinterface-indeling
      - `sidemenu` - zijmenu-indeling

      **Varianten:**
      - `webforj` - standaard webforJ-app
      - `webforj-spring` - webforJ geïntegreerd met Spring Boot

      **Voorbeeldvragen:**
      ```
      "Creëer een webforJ-project met de naam CustomerPortal met het sidemenu-archetype"

      "Genereer een webforJ Spring Boot-project met de tabs-indeling met de naam Dashboard"
      ```

      :::tip Beschikbare Archetypen
      Voor de volledige lijst van archetypen, zie het [archetypenoverzicht](/docs/building-ui/archetypes/overview).
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
      Op basis van een enkele merk kleur produceert de assistent een compleet DWC-thema: primaire, succes, waarschuwing, gevaar, info, standaard en grijze paletten met automatische tekstcontrast. De output omvat de stylesheet plus de `@AppTheme` / `@StyleSheet` wiring.

      **Voorbeeldvragen:**
      ```
      "Genereer een webforJ-thema van merk kleur #6366f1"

      "Maak een toegankelijk thema met HSL 220, 70, 50 als primair"
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
      De assistent leest het echte stylingoppervlak van elke DWC-component - CSS-aangepaste eigenschappen, schaduwonderdelen, weerspiegelde attributen en slots - voordat hij CSS schrijft. Hij kan ook elke DWC-tag opsommen en webforJ Java-klassenamen (`Button`, `TextField`) koppelen aan hun DWC-equivalenten.

      **Voorbeeldvragen:**
      ```
      "Welke CSS-variabelen en delen stelt dwc-button bloot?"

      "Toon me elke slot die beschikbaar is op dwc-dialog"

      "Welke DWC-tag komt overeen met de webforJ TextField-klasse?"
      ```

      Combineer dit met de [styling-apps agent skill](/docs/ai-tooling/agent-skills) voor end-to-end styling workflows.
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>Werken met DWC-design tokens</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      De assistent kan de autoritatieve catalogus van `--dwc-*` tokens voor jouw webforJ-versie opsommen - palet zaden, schaduwen, oppervlakken, spaties, typografie, randen - gefilterd op prefix of substring. Hij valideert ook elke CSS-, Java- of Markdown-bron die je hem geeft tegen de echte tokencatalogus en markeert onbekende namen met voorgestelde correcties.

      **Voorbeeldvragen:**
      ```
      "Lijst elke --dwc-space-* token op"

      "Valideer app.css op onbekende --dwc-* tokens"

      "Welke primaire palet schaduwen zijn beschikbaar?"
      ```

      Validatie vangt typfouten en verzonnen tokens voordat ze als stilzwijgend falende CSS worden verzonden.
    </div>
  </AccordionDetails>
</Accordion>

## Goede prompts schrijven {#writing-good-prompts}

De MCP-server wordt alleen geraadpleegd wanneer je assistent denkt dat dit relevant is. Een paar gewoonten houden het actief:

- **Noem het framework.** Vermeld "webforJ" in de prompt zodat de assistent de MCP-server gebruikt in plaats van zijn algemene Java-kennis.
- **Wees specifiek.** `"Creëer een webforJ-project met de naam InventorySystem met het sidemenu-archetype en Spring Boot"` is beter dan `"maak een app"`.
- **Vraag om verificatie.** Zinnen als `"verifieer tegen webforJ-documenten"` of `"controleer deze CSS op slechte --dwc-* tokens"` duwen de assistent om in plaats van te gokken de tools te gebruiken.

Als je assistent nog steeds antwoorden geeft zonder de server te raadplegen, installeer de [webforJ AI-plugin](https://github.com/webforj/webforj-ai) - het levert bijpassende Agent Skills die de assistent automatisch aanmoedigen om de MCP-tools te gebruiken voor webforJ-taken.

## FAQ {#faq}

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>Waarom gebruikt de AI-assistent de MCP-server niet?</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      De meeste assistenten grijpen alleen naar MCP wanneer ze denken dat de vraag dit vereist. Twee oplossingen:

      1. **Installeer de [webforJ AI-plugin](https://github.com/webforj/webforj-ai)**, die de server koppelt aan Agent Skills die de assistent vertellen om MCP te gebruiken voor webforJ-taken.
      2. **Wees expliciet in je prompt**: voeg "webforJ" toe aan de vraag, en voor koppige gevallen zeg "gebruik de webforJ MCP-server om te antwoorden".
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>Hoe bevestig ik dat de MCP-verbinding werkt?</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Gebruik de MCP-inspector:

      ```bash
      npx @modelcontextprotocol/inspector
      ```

      Verbind je daarna in de inspector met `https://mcp.webforj.com/mcp` en verken de beschikbare tools.
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>Hoe rapporteer ik problemen?</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Open een ticket met behulp van de [webforJ MCP-probleemtemplate](https://github.com/webforj/webforj/issues/new?template=mcp_report.yml). Voeg de prompt, het verwachte resultaat en wat je hebt gekregen toe.
    </div>
  </AccordionDetails>
</Accordion>
<br />
