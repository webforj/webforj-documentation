---
title: MCP Server
sidebar_position: 2.5
sidebar_class_name: new-content
_i18n_hash: 7b656643222d616e7c44d14ed1de7bd3
---
De webforJ Model Context Protocol (MCP) server biedt AI-assistenten directe toegang tot officiële webforJ-documentatie, geverifieerde codevoorbeelden en framework-specifieke patronen, waardoor reacties met meer nauwkeurige antwoorden en geautomatiseerde projectgeneratie specifiek voor webforJ-ontwikkeling mogelijk zijn.

## Wat is een MCP?

Model Context Protocol is een open standaard die AI-assistenten in staat stelt verbinding te maken met externe tools en documentatie. De webforJ MCP-server implementeert dit protocol om te voorzien in:

- **Kenniszoektocht** - Natuurlijke taalzoekopdracht binnen webforJ-documentatie, codevoorbeelden en patronen
- **Projectgeneratie** - Creëer webforJ-toepassingen vanuit officiële sjablonen met de juiste structuur
- **Thema- creatie** - Genereer toegankelijke CSS-thema's volgens webforJ-ontwerppatronen

## Waarom MCP gebruiken?

Hoewel AI-coderingsassistenten goed zijn in het beantwoorden van basisvragen, hebben ze moeite met complexe webforJ-specifieke vragen die meerdere documentsecties beslaan. Zonder directe toegang tot officiële bronnen kunnen ze:

- Methodes genereren die niet bestaan in webforJ
- Verwijzen naar verouderde of incorrecte API-patronen  
- Code geven die niet compileert
- WebforJ-syntaxis verwarren met andere Java-frameworks
- WebforJ-specifieke patronen verkeerd begrijpen

Met MCP-integratie zijn AI-antwoorden verankerd in daadwerkelijke webforJ-documentatie, codevoorbeelden en frameworkpatronen, wat verifieerbare antwoorden biedt met directe links naar officiële bronnen voor diepere verkenning.

:::warning AI kan nog steeds fouten maken
Hoewel MCP de nauwkeurigheid aanzienlijk verbetert door toegang te bieden tot officiële webforJ-bronnen, garandeert het geen perfecte codegeneratie. AI-assistenten kunnen nog steeds fouten maken in complexe scenario's. Verifieer altijd de gegenereerde code en test grondig voordat u deze in productie gebruikt.
:::

## Installatie

De webforJ MCP-server is gehost op `https://mcp.webforj.com` met twee eindpunten:

- **MCP-eindpunt** (`/mcp`) - Voor Claude, VS Code, Cursor
- **SSE-eindpunt** (`/sse`) - Voor legacy-clients

<Tabs groupId="ide">
<TabItem value="vscode" label="VS Code">

Voeg deze configuratie toe aan je VS Code settings.json-bestand:

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
<TabItem value="cursor" label="Cursor">

Voeg deze configuratie toe aan je Cursor-instellingen:

```json
"mcpServers": {
  "webforj-mcp": {
    "url": "https://mcp.webforj.com/mcp"
  }
}
```

</TabItem>
<TabItem value="claude-code" label="Claude Code" default>

Gebruik de Claude CLI-opdracht om de server te registreren:

```bash
claude mcp add webforj-mcp https://mcp.webforj.com/mcp -t http -s user
```

Dit configureert automatisch de MCP-server in jouw Claude Code-omgeving.

</TabItem>
<TabItem value="claude-desktop" label="Claude Desktop">

Voeg deze server toe via het Integrations-paneel in de instellingen van Claude Desktop:

1. Open Claude Desktop en ga naar Instellingen
2. Klik op "Integraties" in de zijbalk
3. Klik op "Voeg integratie toe" en plak de URL: `https://mcp.webforj.com/mcp`
4. Volg de installatiegids om de configuratie te voltooien

Voor gedetailleerde instructies, zie de [officiële integratiehandleiding](https://support.anthropic.com/en/articles/11175166-about-custom-integrations-using-remote-mcp).

</TabItem>
<TabItem value="windsurf" label="Windsurf">

Voeg deze serverconfiguratie toe aan je Windsurf MCP-instellingen:

```json
{
  "mcpServers": {
    "webforj-mcp": {
      "serverUrl": "https://mcp.webforj.com/sse"
    }
  }
}
```

</TabItem>
</Tabs>

## Beschikbare tools

Tools zijn gespecialiseerde functies die de MCP-server aan AI-assistenten biedt. Wanneer je een vraag stelt of een verzoek doet, kan de AI deze tools aanroepen om documentatie te doorzoeken, projecten te genereren of thema's te creëren. Elke tool accepteert specifieke parameters en retourneert gestructureerde gegevens die de AI helpen om nauwkeurige, contextbewuste assistentie te bieden.

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>webforj-knowledge-base</code></strong> - Doorzoek documentatie en voorbeelden
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Deze tool biedt semantische zoekcapaciteiten over het gehele webforJ-documentatie-ecosysteem. Het begrijpt de context en de relaties tussen verschillende frameworkconcepten en retourneert relevante documentsecties, API-referenties en werkende codevoorbeelden.

      **Voorbeeldvragen:**
      ```
      "Doorzoek webforJ-documentatie naar Button-component met voorbeeldicons"

      "Zoek webforJ-formuliervalidatiepatronen in de nieuwste documentatie"

      "Toon me de huidige webforJ-routeringsconfiguratie met @Route-annotatie"

      "Doorzoek webforJ-documenten voor FlexLayout-responsieve ontwerppatronen"

      "Vind webforJ-webcomponentintegratie in officiële documentatie"
      ```
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>webforj-create-project</code></strong> - Genereer nieuwe webforJ-projecten  
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Scaffold volledige webforJ-toepassingen met behulp van officiële Maven-archetypes. De tool creëert een gestandaardiseerde projectdirectory-indeling en bevat startercode op basis van het geselecteerde sjabloon. Gegenereerde projecten omvatten een kant-en-klaar buildsysteem, resource-mappen en configuratiebestanden voor directe ontwikkeling en implementatie.

      **Voorbeeldprompts:**
      ```
      "Creëer een webforJ-project genaamd CustomerPortal met behulp van het hello-world-archetype"

      "Genereer een webforJ Spring Boot-project met tabbladenlay-out genaamd Dashboard"

      "Maak een nieuwe webforJ-app met sidemenu-archetype voor het AdminPanel-project"

      "Genereer een webforJ-blank project genaamd TestApp met com.example groupId"

      "Creëer webforJ-project InventorySystem met sidemenu-archetype en Spring Boot"
      ```

      Bij het gebruik van deze tool kun je kiezen uit verschillende projecttemplates:

      **Archetypes** (projecttemplates):
      - `hello-world` - Basisapp met voorbeeldcomponenten om webforJ-functies te demonstreren
      - `blank` - Minimale projectstructuur om vanaf nul te beginnen
      - `tabs` - Vooraf gebouwde tabbed interface-indeling voor multi-viewapplicaties
      - `sidemenu` - Indeling voor zijmenu-navigatie voor beheerderspanelen of dashboards

      **Flavors** (frameworkintegratie):
      - `webforj` - Standaard webforJ-app
      - `webforj-spring` - webforJ geïntegreerd met Spring Boot voor dependency injection en enterprise-functies

      :::tip Beschikbare Archetypes
      webforJ komt met verschillende vooraf gedefinieerde archetypes om je snel aan de slag te helpen. Voor een compleet overzicht van beschikbare archetypes, zie de [archetypescatalogus](../building-ui/archetypes/overview).
      :::
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>webforj-create-theme</code></strong> - Creëer toegankelijke CSS-thema's
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Genereert webforJ-thema-configuraties met behulp van [DWC HueCraft](https://huecraft.dwc.style/). De tool creëert complete sets van CSS-aangepaste eigenschappen met primaire, secundaire, succes-, waarschuwing-, gevaar- en neutrale kleurvariant.

      **Voorbeeldverzoeken:**
      ```
      "Genereer een webforJ-thema met HSL 220, 70, 50 als primaire kleur voor ons bedrijfsmerk"

      "Creëer webforJ-toegankelijk thema genaamd 'ocean' met primaire kleur #0066CC"

      "Genereer een webforJ-thema met onze merk kleur #FF5733"

      "Creëer webforJ-thema met HSL 30, 100, 50 genaamd 'sunset' voor onze app"

      "Genereer toegankelijk webforJ-thema met primaire RGB 44, 123, 229"
      ```
    </div>
  </AccordionDetails>
</Accordion>

## Beschikbare prompts {#available-prompts}

Prompts zijn vooraf geconfigureerde AI-instructies die meerdere tools en workflows combineren voor veelvoorkomende taken. Ze begeleiden de AI door specifieke stappen en parameters om betrouwbare, herhaalbare resultaten te leveren voor elke ondersteunde workflow.

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>create-app</code></strong> - Maak en voer een webforJ-app uit
  </AccordionSummary>
  <AccordionDetails>
    <div>
      **Argumenten:**
      - `appName` (vereist) - Naam van de toepassing (bijv. MyApp, TodoList, Dashboard)
      - `archetype` (vereist) - Kies uit: `blank`, `hello-world`, `tabs`, `sidemenu`
      - `runServer` (optioneel) - Voer automatisch de ontwikkelingsserver uit (ja/nee)
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>create-theme</code></strong> - Genereer een webforJ-thema van een primaire kleur
  </AccordionSummary>
  <AccordionDetails>
    <div>
      **Argumenten:**
      - `primaryColor` (vereist) - Kleur in hex (#FF5733), rgb (255,87,51), of hsl (9,100,60) formaat
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>search-webforj</code></strong> - Geavanceerd zoeken met autonome probleemoplossing
  </AccordionSummary>
  <AccordionDetails>
    <div>
      De prompt configureert de AI om:

      1. De kennisbasis uitgebreid te doorzoeken
      2. Volledige, productieklare code te schrijven
      3. Het project te compiler met `mvn compile` om te verifiëren dat er geen buildfouten zijn
      4. Fouten iteratief te corrigeren totdat alles werkt
    </div>
  </AccordionDetails>
</Accordion>

### Hoe prompts te gebruiken

<Tabs groupId="ide">
<TabItem value="vscode" label="VS Code en Claude Code">

1. Typ <kbd>/</kbd> in de chat om beschikbare prompts te zien
2. Selecteer een prompt uit het dropdownmenu
3. Vul de vereiste parameters in wanneer daarom wordt gevraagd

</TabItem>
<TabItem value="claude-desktop" label="Claude Desktop">

1. Klik op het **+** (plus) icoon in het prompt-invoerveld
2. Selecteer **"Voeg toe vanuit webforJ"** uit het menu
3. Kies de gewenste prompt (bijv. `create-app`, `create-theme`, `search-webforj`)
4. Claude vraagt je om de vereiste argumenten in te voeren
5. Vul de parameters in zoals gevraagd

:::tip Verifieer of MCP is verbonden
Zoek het gereedschapicoon in de rechterbenedenhoek van het invoerveld om te bevestigen dat de webforJ MCP-server is verbonden.
:::

</TabItem>
</Tabs>

## Beste praktijken

Om de meest nauwkeurige en actuele webforJ-assistentie te krijgen, volg deze richtlijnen om optimaal gebruik te maken van de functies van de MCP-server.

### Zorgen voor gebruik van de MCP-server

AI-modellen kunnen de MCP-server overslaan als ze denken dat ze het antwoord al kennen. Om ervoor te zorgen dat de MCP-server daadwerkelijk wordt gebruikt:

- **Wees expliciet over webforJ**: Vermeld altijd "webforJ" in je vraag om framework-specifieke zoekopdrachten te activeren
- **Vraag om actuele informatie**: Voeg zinnen toe zoals "laatste webforJ-documentatie" of "huidige webforJ-patronen"
- **Vraag om geverifieerde voorbeelden**: Vraag om "werkende webforJ-codevoorbeelden" om documentatiezoekopdracht af te dwingen
- **Verwijs naar specifieke versies**: Noem jouw webforJ-versie (bijv. "webforJ `25.02`") om nauwkeurige resultaten te krijgen

### Specifieke prompts schrijven

**Goede voorbeelden:**
```
"Doorzoek webforJ-documentatie voor Button-component gebeurtenishandeling met voorbeelden"

"Creëer een webforJ-project genaamd InventorySystem met het sidemenu-archetype en Spring Boot"

"Genereer een webforJ-thema met HSL 220, 70, 50 als primaire kleur voor bedrijfsmerk"
```

**Slechte voorbeelden:**
```
"Hoe werken knoppen"

"Maak een app"

"Maak het blauw"
```

### Dwang tot gebruik van MCP-tools

Als de AI generieke antwoorden geeft zonder gebruik te maken van de MCP-server:

1. **Vraag expliciet aan**: "Gebruik de webforJ MCP-server om te zoeken naar `[vraag]`"
2. **Vraag om documentatiereferenties**: "Vind in webforJ-documenten hoe te `[vraag]`"
3. **Vraag om verificatie**: "Verifieer deze oplossing met de webforJ-documentatie"
4. **Wees framework-specifiek**: Vermeld altijd "webforJ" in je vragen

## AI- aanpassing {#ai-customization}

Configureer jouw AI-assistenten om automatisch de MCP-server te gebruiken en de beste praktijken van webforJ te volgen. Voeg project-specifieke instructies toe, zodat jouw AI-assistenten altijd de MCP-server gebruiken, voldoen aan de documentatiestandaarden van webforJ, en nauwkeurige, actuele antwoorden bieden die voldoen aan de vereisten van jouw team.

### Projectconfiguratiebestanden

- Voor **VS Code en Copilot**, maak `.github/copilot-instructions.md`
- Voor **Claude Code**, maak `CLAUDE.md` in de hoofdmap van jouw project

Voeg het volgende toe aan het gemaakte markdown-bestand:
```markdown
## Gebruik de webforJ MCP-server om alle webforJ-vragen te beantwoorden

- Roep altijd de "webforj-knowledge-base" tool aan om documentatie te halen relevant voor de vraag
- Verifieer alle API-handtekeningen aan de officiële documentatie
- Neem nooit aan dat methodenamen of parameters bestaan zonder controle

Verifieer altijd of de code compileert met `mvn compile` voordat je suggesties doet.
```

## FAQ

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>Waarom gebruikt de AI de webforJ MCP-server niet?</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      De meeste AI-assistenten hebben expliciete instructies nodig om MCP-servers te gebruiken. Configureer jouw AI-client met de instructies uit de sectie [AI-aanpassing](#ai-customization). Zonder deze instructies kunnen AI-assistenten standaard op hun trainingsgegevens vertrouwen in plaats van de MCP-server te raadplegen.

      **Snelle oplossing:**
      Voeg "gebruik webforJ MCP" toe aan je prompt of maak het juiste configuratiebestand (`.github/copilot-instructions.md` of `CLAUDE.md`).
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>Hoe verifieer ik of de MCP-verbinding werkt</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Gebruik de MCP-inspector om verbindingen te debuggen:

      ```bash
      npx @modelcontextprotocol/inspector
      ```

      Wacht op de melding: `🔍 MCP Inspector is up and running at http://127.0.0.1:6274` (poort kan variëren)

      Vervolgens in de inspector:
      1. Voer de MCP-server-URL in: `https://mcp.webforj.com/mcp`
      2. Klik op "Verbinding maken" om de verbinding tot stand te brengen
      3. Bekijk beschikbare tools en testqueries
      4. Houd aanvraag-/antwoordlogs in de gaten voor debuggen
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>Wat is het verschil tussen MCP- en SSE-eindpunten?</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      De webforJ MCP-server biedt twee eindpunten:

      - **MCP-eindpunt** (`/mcp`) - Modern protocol voor Claude, VS Code, Cursor
      - **SSE-eindpunt** (`/sse`) - Server-Sent Events voor legacy-clients zoals Windsurf

      De meeste gebruikers moeten het MCP-eindpunt gebruiken. Gebruik alleen SSE als jouw client het standaard MCP-protocol niet ondersteunt.
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>Is het mogelijk om de MCP-server zonder configuratiebestanden te gebruiken?</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Ja, maar het wordt niet aanbevolen. Zonder configuratiebestanden moet je de AI handmatig vragen om de MCP-server in elk gesprek te gebruiken. Configuratiebestanden instrueren de AI automatisch om de MCP-server voor elke interactie te gebruiken, zodat je deze instructies niet elke keer hoeft te herhalen.

      **Handmatige aanpak:**
      Begin prompts met: "Gebruik de webforJ MCP-server om..."

      **Alternatief: Gebruik vooraf geconfigureerde prompts**
      De MCP-server biedt prompts die zonder configuratiebestanden werken:
      - `/create-app` - Genereer nieuwe webforJ-toepassingen
      - `/create-theme` - Creëer toegankelijke CSS-thema's
      - `/search-webforj` - Geavanceerd documentatiezoekopdracht

      Zie [Beschikbare prompts](#available-prompts) voor details.
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>Hoe bij te dragen of problemen te melden</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      **Problemen melden:** [webforJ MCP Probleemtemplate](https://github.com/webforj/webforj/issues/new?template=mcp_report.yml)
      
      **Veelvoorkomende problemen om te melden:**
      - Verouderde documentatie in zoekresultaten
      - Ontbrekende API-methoden of componenten
      - Onjuiste codevoorbeelden
      - Fouten bij de uitvoering van tools

      Vermeld je vraag, verwachte resultaat en werkelijke resultaat bij het rapporteren van problemen.
    </div>
  </AccordionDetails>
</Accordion>
<br />
