---
title: MCP-server
sidebar_position: 5
description: >-
  Connect AI assistants to the webforJ MCP server for live documentation lookup,
  project scaffolding, theme generation, and token validation.
_i18n_hash: e51aa2e6a5a0f6c37a18c404c1104684
---
De webforJ Model Context Protocol (MCP) server sluit AI-codingassistenten aan op de documentatie, API's, ontwerp tokens en scaffoldingtools van webforJ. In plaats van te raden naar de conventies van het framework, vraagt de assistent de server en krijgt antwoorden die zijn gebaseerd op de echte webforJ.

:::tip Gebruik de plugin
Tenzij je zeker weet dat je alleen de MCP-server wilt, installeer dan de **[webforJ AI-plugin](/docs/ai-tooling)** in plaats daarvan - het bundelt deze server met de bijbehorende [Agent Skills](/docs/ai-tooling/agent-skills) in één installatie.
:::

## Wat is een MCP? {#whats-an-mcp}

Model Context Protocol is een open standaard die AI-assistenten in staat stelt om op aanvraag externe tools aan te roepen. De webforJ MCP-server implementeert dit protocol zodat je assistent:

- dingen kan opzoeken in de webforJ-documentatie in plaats van methodenamen te hallucinereren
- nieuwe webforJ-projecten kan scaffolden vanuit officiële Maven-archetypes
- toegankelijke DWC-thema's kan genereren op basis van een merk kleur
- het echte stijlingoppervlak van een DWC-component kan lezen en kan valideren of een `--dwc-*` token geldig is voordat het in je CSS terechtkomt

:::warning AI Kan Nog Steeds Fouten Maken
De MCP-server verbetert de nauwkeurigheid aanzienlijk, maar AI-assistenten kunnen nog steeds onjuiste code genereren in complexe scenario's. Beoordeel en test altijd de gegenereerde code voordat je deze verzendt.
:::

## Installatie {#installation}

Voor de volledige ervaring installeer je de **[webforJ AI-plugin](/docs/ai-tooling)** - het configureert deze server samen met de Agent Skills die je assistent nodig heeft om het goed te gebruiken.

Als je alleen de MCP-server wilt (geen vaardigheden), wijs dan je client aan `https://mcp.webforj.com/mcp`:

<Tabs groupId="ide">
<TabItem value="claude-code" label="Claude Code" default>

```bash
claude mcp add webforj-mcp https://mcp.webforj.com/mcp -t http -s user
```

</TabItem>
<TabItem value="copilot-cli" label="GitHub Copilot CLI">

Het aanbevolen pad op Copilot CLI is de **[webforJ AI-plugin](/docs/ai-tooling)** - het registreert de MCP-server voor je in één stap. Voor een ruwe MCP-only setup, zie de instructies per client in de [webforJ AI-repository](https://github.com/webforj/webforj-ai#clients).

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

Cursor, Kiro, Goose, Junie, Antigravity, en elke andere MCP-over-HTTP-client werkt ook - ze gebruiken gewoon hun eigen configuratieformaat. Zie de [per-client install guide](https://github.com/webforj/webforj-ai#clients) voor het exacte snippet voor elk.

## Wat de server kan doen {#capabilities}

Wanneer de MCP-server is aangesloten, krijgt je AI-assistent de volgende mogelijkheden. Een van deze kan worden geactiveerd door een verzoek in natuurlijke taal - de assistent kiest automatisch de juiste.

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>Richt je op de juiste webforJ-versie</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Voordat er op versiegevoelige vragen (iets stijl- of API-specifiek) wordt geantwoord, lost de assistent op welke webforJ-versie je gebruikt. Het leest `pom.xml` wanneer beschikbaar en vraagt anders aan jou. Elk daaropvolgend antwoord is beperkt tot die versie.
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>Zoek dingen op in de webforJ-kennisbasis</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      De assistent kan de volledige kennisbasis van webforJ doorzoeken voor antwoorden die zijn gebaseerd op het echte framework. Resultaten zijn beperkt tot waar je naar vraagt - een API-vraag, een gids, een codevoorbeeld of de Kotlin DSL.

      **Voorbeeld prompts:**
      ```
      "Vind de webforJ Button-component evenementafhandelingsvoorbeelden"

      "Hoe stel ik routen in met @Route in webforJ?"

      "Laat me een webforJ-formulier validatievoorbeeld zien"
      ```
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>Scaffold een nieuw webforJ-project</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      De assistent genereert het juiste Maven-archetypecommando voor een nieuwe webforJ-app op basis van jouw vereisten (archetype, Spring-integratie, naam, groep).

      **Archetypes:**
      - `hello-world` - starter-app met voorbeeldcomponenten
      - `blank` - minimale projectstructuur
      - `tabs` - tabbladinterface-indeling
      - `sidemenu` - zij-navigatie-indeling

      **Flavors:**
      - `webforj` - standaard webforJ-app
      - `webforj-spring` - webforJ geïntegreerd met Spring Boot

      **Voorbeeld prompts:**
      ```
      "Maak een webforJ-project genaamd CustomerPortal met het sidemenu-archetype"

      "Genereer een webforJ Spring Boot-project met de tabbladindeling genaamd Dashboard"
      ```

      :::tip Beschikbare Archetypes
      Voor de volledige lijst van archetypes, zie de [archetypes catalogus](/docs/building-ui/archetypes/overview).
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
      Op basis van een enkele merk kleur, produceert de assistent een compleet DWC-thema: primaire, succes, waarschuwing, gevaar, info, standaard en grijze paletten met automatische tekstcontrast. De output omvat de stijlblad plus de `@AppTheme` / `@StyleSheet` wiring.

      **Voorbeeld prompts:**
      ```
      "Genereer een webforJ-thema van merk kleur #6366f1"

      "Creëer een toegankelijk thema met HSL 220, 70, 50 als primair"
      ```
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>Style DWC-componenten correct</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      De assistent leest het echte stijlingoppervlak van elke DWC-component - CSS-aangepaste eigenschappen, schaduwonderdelen, gereflecteerde attributen en sloten - voordat hij enige CSS schrijft. Hij kan ook elke DWC-tag op sommen en de webforJ Java-klassenamen (`Button`, `TextField`) naar hun DWC-vergelijkingen resolveren.

      **Voorbeeld prompts:**
      ```
      "Welke CSS-variabelen en onderdelen exposeert dwc-button?"

      "Laat me elke slot zien die beschikbaar is op dwc-dialog"

      "Welke DWC-tag correspondeert met de webforJ TextField-klasse?"
      ```

      Combineer dit met de [styling-apps agent skill](/docs/ai-tooling/agent-skills) voor end-to-end styling workflows.
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>Werken met DWC-ontwerp tokens</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      De assistent kan de autoritatieve catalogus van `--dwc-*` tokens voor jouw webforJ-versie opsommen - paletzaden, tinten, oppervlakken, ruimte, typografie, randen - gefilterd op prefix of substring. Hij zal ook elke CSS-, Java- of Markdown-bron die je hem geeft valideren tegen de echte token catalogus en onbekende namen markeren met voorgestelde correcties.

      **Voorbeeld prompts:**
      ```
      "Som elke --dwc-space-* token op"

      "Valideer app.css voor onbekende --dwc-* tokens"

      "Welke primaire-paletten zijn beschikbaar?"
      ```

      Validatie vangt typfouten en uitgevonden tokens voordat ze worden verzonden als stilzwijgend falende CSS.
    </div>
  </AccordionDetails>
</Accordion>

## Goede prompts schrijven {#writing-good-prompts}

De MCP-server wordt alleen geraadpleegd wanneer jouw assistent denkt dat het relevant is. Een paar gewoonten houden het betrokken:

- **Noem het framework.** Vermeld "webforJ" in de prompt, zodat de assistent de MCP-server aanspreekt in plaats van zijn algemene Java-kennis.
- **Wees specifiek.** `"Creëer een webforJ-project genaamd InventorySystem met het sidemenu-archetype en Spring Boot"` is beter dan `"maak een app"`.
- **Vraag om verificatie.** Zinnen als `"verifieer tegen de webforJ-documenten"` of `"controleer deze CSS op slechte --dwc-* tokens"` duwen de assistent om de tools te gebruiken in plaats van te gokken.

Als jouw assistent nog steeds antwoord geeft zonder de server te raadplegen, installeer dan de [webforJ AI-plugin](https://github.com/webforj/webforj-ai) - deze levert bijpassende Agent Skills die de assistent automatisch aanmoedigen om de MCP-tools te gebruiken voor webforJ-taken.

## FAQ {#faq}

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>Waarom gebruikt de AI-assistent de MCP-server niet?</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      De meeste assistenten schakelen alleen over naar MCP wanneer ze denken dat de vraag het nodig heeft. Twee oplossingen:

      1. **Installeer de [webforJ AI-plugin](https://github.com/webforj/webforj-ai)**, die de server koppelt aan Agent Skills die de assistent vertellen om MCP te gebruiken voor webforJ-taken.
      2. **Wees expliciet in je prompt**: voeg "webforJ" toe aan de vraag, en voor hardnekkige gevallen zeg "gebruik de webforJ MCP-server om te antwoorden".
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>Hoe verifieer ik of de MCP-verbinding werkt?</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Gebruik de MCP-inspecteur:

      ```bash
      npx @modelcontextprotocol/inspector
      ```

      Verbind vervolgens in de inspecteur met `https://mcp.webforj.com/mcp` en verken de beschikbare tools.
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>Hoe meld ik problemen?</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Open een ticket met behulp van de [webforJ MCP-issue template](https://github.com/webforj/webforj/issues/new?template=mcp_report.yml). Vermeld de prompt, het verwachte resultaat en wat je hebt gekregen.
    </div>
  </AccordionDetails>
</Accordion>
<br />
