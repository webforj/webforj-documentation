---
title: Agent Skills
sidebar_position: 10
_i18n_hash: 98d3f61447c289339f92fc4872e734f1
---
Agent Skills leren AI-coderingsassistenten hoe ze webforJ-toepassingen kunnen bouwen met de juiste API's, ontwerptokens en componentpatronen. In plaats van te gissen naar de conventies van het framework, laadt de assistent een vaardigheid en volgt een gestructureerde workflow om code te produceren die compileert en de beste praktijken volgt bij de eerste poging.

:::tip Gebruik de plugin
De onderstaande vaardigheden worden geleverd binnen de **[webforJ AI-plugin](/docs/integrations/ai-tooling)** samen met de [MCP-server](/docs/integrations/ai-tooling/mcp). Eén installatie geeft uw assistent beide onderdelen.
:::

Vaardigheden volgen de open [Agent Skills](https://agentskills.io/specification) standaard en werken met veel AI-assistenten, waaronder Claude Code, GitHub Copilot, Cursor, Gemini CLI, OpenAI Codex, en meer. Een vaardigheid vertelt de assistent welk type taak het afhandelt; de assistent laadt deze automatisch wanneer je prompt overeenkomt. Bijvoorbeeld, het vragen "thematiseer deze app met een blauw palet" triggert de `webforj-styling-apps` vaardigheid, die de assistent door het opzoeken van geldige DWC-tokens, het schrijven van scope-CSS, en het valideren van elke variabelenaam begeleidt voordat er iets naar de schijf wordt geschreven.

## Waarom vaardigheden gebruiken? {#why-use-skills}

De MCP-server maakt nauwkeurige webforJ-informatie beschikbaar op aanvraag, maar op zichzelf vertelt het de assistent niet _wanneer_ iets moet worden opgezocht, _welke_ aanpak bij de taak past, of _in welke volgorde_ dingen gedaan moeten worden. Dat is waar vaardigheden in het spel komen.

Vaardigheden geven de assistent een taak-specifiek speelboek: hoe het werk voor zich moet classificeren, welke webforJ-patronen passen, welke MCP-tools in elke stap te raadplegen en hoe de output te valideren voordat deze wordt teruggegeven. Het resultaat is consistente, conventie-volgende webforJ-code in plaats van een verzameling technisch geldige maar stylistisch inconsistent snippets.

## Hoe vaardigheden verschillen van MCP {#how-skills-differ-from-mcp}

Vaardigheden en de [webforJ MCP-server](/docs/integrations/ai-tooling/mcp) vervullen complementaire rollen. De MCP-server biedt live-tools die de assistent kan aanroepen om informatie op te halen of output te genereren. Vaardigheden bieden de workflow die de assistent vertelt _wanneer_ deze die tools moet gebruiken, in welke volgorde dingen gedaan moeten worden, en hoe het resultaat te valideren.

| | MCP-server | Agent Skills |
|---|---|---|
| **Wat het biedt** | Tools die de assistent op aanvraag oproept (documentzoektocht, scaffolding, themageneratie, tokenvalidatie) | Workflows en besluitvormingstabellen die de aanpak van de assistent bij een taak begeleiden |
| **Wanneer het handelt** | Wanneer de assistent beslist om een tool aan te roepen | Automatisch, wanneer de assistent een overeenkomende taak detecteert |
| **Het beste voor** | Specifieke vragen beantwoorden, artefacten genereren | End-to-end taken die een consistente webforJ-aanpak vereisen |

In de praktijk werken de twee het best samen - en de [webforJ AI-plugin](https://github.com/webforj/webforj-ai) levert ze als één installatie.

## Installatie {#installation}

Installeer de **[webforJ AI-plugin](/docs/integrations/ai-tooling)** - het levert beide onderstaande vaardigheden samen met de MCP-server. Voor clients die geen plugins ondersteunen, lijst de [webforJ AI-repository](https://github.com/webforj/webforj-ai#clients) de vaardighedencatalogus die elk hulpmiddel leest, zodat u de vaardigheidsmappen handmatig kunt kopiëren.

## Beschikbare vaardigheden {#available-skills}

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>webforj-creating-components</code></strong>: bouw herbruikbare webforJ-componenten vanuit webcomponentbibliotheken, JavaScript-bibliotheken of bestaande webforJ-componenten
  </AccordionSummary>
  <AccordionDetails>
    <div>

Gebruik dit wanneer u een herbruikbare Java-component nodig heeft rond een bron - een bestaande Custom Element-bibliotheek, een gewone JavaScript-bibliotheek, of een samenstelling van bestaande webforJ-componenten. De assistent kiest de juiste webforJ-basis klasse voor de taak, sluit eigenschappen, evenementen en slots correct aan met de juiste patronen, en produceert tests die voldoen aan de webforJ-conventies.

**Wanneer het in werking treedt**

- *"Wikkel deze Custom Element-bibliotheek als webforJ-componenten."*
- *"Samenstellen van deze webforJ-componenten tot een herbruikbare kaart."*
- *"Integreer deze gewone JavaScript-bibliotheek als een webforJ-component."*
- *"Exposeer deze Browser API als een webforJ-hulpmiddel."*

</div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>webforj-styling-apps</code></strong>: thema en style webforJ-toepassingen met behulp van het DWC-ontwerptokensysteem
  </AccordionSummary>
  <AccordionDetails>
    <div>

Gebruik dit voor elk visueel werk aan een webforJ-app: paletverversingen, component-specifieke styling, lay-out en spatiëring, typografie, volledige thema's, tabelweergave, of gecoördineerde Google Charts kleuren. De assistent schrijft CSS die zich aan DWC-ontwerptokens houdt, selectoren correct scopeert, en elke `--dwc-*` referentie valideert tegen de echte catalogus voor jouw webforJ-versie - zodat donkere modus en themaswitching blijven werken.

**Wanneer het in werking treedt**

- *"Thematiseer deze app met een blauw palet."*
- *"Stijl de dwc-button om aan de merk richtlijnen te voldoen."*
- *"Maak deze lay-out strakker - pas spatiëring en typografie aan."*

</div>
  </AccordionDetails>
</Accordion>
