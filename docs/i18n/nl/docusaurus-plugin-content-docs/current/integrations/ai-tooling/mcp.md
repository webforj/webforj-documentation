---
title: MCP Server
sidebar_position: 5
_i18n_hash: a45888cf39bbbce0002177da8fe95eba
---
De webforJ Model Context Protocol (MCP) server biedt AI-assistenten directe toegang tot officiële webforJ-documentatie, geverifieerde codevoorbeelden en frameworkspecifieke patronen, waardoor antwoorden met nauwkeurigere antwoorden en geautomatiseerde projectgeneratie specifiek voor webforJ-ontwikkeling mogelijk zijn.

## Wat is een MCP?

Model Context Protocol is een open standaard die AI-assistenten in staat stelt verbinding te maken met externe tools en documentatie. De webforJ MCP-server implementeert dit protocol om te voorzien in:

- **Kenniszoekfunctie** - Zoeken in natuurlijke taal door webforJ-documentatie, codevoorbeelden en patronen
- **Projectgeneratie** - Maak webforJ-applicaties vanuit officiële sjablonen met een juiste structuur
- **Thema-creatie** - Genereer toegankelijke CSS-thema's volgens webforJ-ontwerppatronen

## Waarom de MCP gebruiken?

Hoewel AI-codingassistenten uitblinken in het beantwoorden van basisvragen, hebben ze moeite met complexe webforJ-specifieke vragen die meerdere documentatiedelen bestrijken. Zonder directe toegang tot officiële bronnen kunnen ze:

- Methoden genereren die niet bestaan in webforJ
- Verwijzen naar verouderde of onjuiste API-patronen
- Code bieden die niet compileert
- WebforJ-syntaxis verwarren met andere Java-frameworks
- WebforJ-specifieke patronen verkeerd begrijpen

Met MCP-integratie zijn AI-antwoorden verankerd in de daadwerkelijke webforJ-documentatie, codevoorbeelden en frameworkpatronen, wat verifieerbare antwoorden biedt met directe links naar officiële bronnen voor verdere verkenning.

:::warning AI kan nog steeds fouten maken
Hoewel MCP de nauwkeurigheid aanzienlijk verbetert door toegang te bieden tot officiële webforJ-bronnen, garandeert het geen perfecte codegeneratie. AI-assistenten kunnen nog steeds fouten maken in complexe scenario's. Verifieer altijd de gegenereerde code en test grondig voordat je deze in productie gebruikt.
:::

## Installatie

De webforJ MCP-server is gehost op `https://mcp.webforj.com` met twee eindpunten:

- **MCP-eindpunt** (`/mcp`) - Voor Claude, VS Code, Cursor
- **SSE-eindpunt** (`/sse`) - Voor oudere clients

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

Dit configureert automatisch de MCP-server in je Claude Code-omgeving.

</TabItem>
<TabItem value="claude-desktop" label="Claude Desktop">

Voeg deze server toe via het Integraties-paneel in de instellingen van Claude Desktop:

1. Open Claude Desktop en ga naar Instellingen
2. Klik op "Integraties" in de zijbalk
3. Klik op "Integratie toevoegen" en plak de URL: `https://mcp.webforj.com/mcp`
4. Volg de setup wizard om de configuratie te voltooien

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

Tools zijn gespecialiseerde functies die de MCP-server biedt aan AI-assistenten. Wanneer je een vraag stelt of een verzoek indient, kan de AI deze tools aanroepen om documentatie te doorzoeken, projecten te genereren of thema's te creëren. Elke tool accepteert specifieke parameters en retourneert gestructureerde gegevens die de AI helpen om nauwkeurige, contextbewuste assistentie te bieden.

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>webforj-knowledge-base</code></strong> - Zoek documentatie en voorbeelden
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Deze tool biedt semantische zoekcapaciteiten door het gehele webforJ-documentatie-ecosysteem. Het begrijpt context en relaties tussen verschillende raamwerkconcepten en retourneert relevante documentatiesecties, API-referenties en werkende codevoorbeelden.

      **Voorbeeldvragen:**
      ```
      "Zoek webforJ-documentatie voor Button-component met voorbeeldiconen"

      "Vind webforJ-formuliervalidatiepatronen in de laatste documentatie"

      "Toon me de huidige webforJ-routingconfiguratie met @Route-annotatie"

      "Zoek webforJ-documenten voor FlexLayout-responsieve ontwerppatronen"

      "Vind de integratie van webforJ-webcomponenten in de officiële documentatie"
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
      Scaffolds complete webforJ-applicaties met behulp van officiële Maven-archetypes. De tool creëert een gestandaardiseerde projectdirectory-indeling en bevat startcode op basis van het gekozen sjabloon. Gegenereerde projecten omvatten een klaar-voor-gebruik buildsysteem, resource-mappen en configuratiebestanden voor onmiddellijke ontwikkeling en implementatie.

      **Voorbeeldopdrachten:**
      ```
      "Maak een webforJ-project genaamd CustomerPortal met behulp van het hello-world-archetype"

      "Genereer een webforJ Spring Boot-project met tabbladindeling genaamd Dashboard"

      "Maak een nieuwe webforJ-app met sidemenu-archetype voor het AdminPanel-project"

      "Genereer een leeg webforJ-project genaamd TestApp met com.example groupId"

      "Creëer een webforJ-project InventorySystem met sidemenu-archetype met Spring Boot"
      ```

      Wanneer je deze tool gebruikt, kun je kiezen uit verschillende projecttemplates:

      **Archetypes** (projecttemplates):
      - `hello-world` - Basisapp met voorbeeldcomponenten om webforJ-functies te demonstreren
      - `blank` - Minimale projectstructuur om vanaf nul te beginnen
      - `tabs` - Vooraf gebouwde tab-interface-indeling voor multi-view applicaties
      - `sidemenu` - Indeling met zijnavigatiemenu voor administratiepanelen of dashboards

      **Flavors** (raamwerkintegratie):
      - `webforj` - Standaard webforJ-app
      - `webforj-spring` - webforJ geïntegreerd met Spring Boot voor dependency injection en enterprise-functies

      :::tip Beschikbare Archetypes
      webforJ wordt geleverd met verschillende vooraf gedefinieerde archetypes om je snel op weg te helpen. Voor een complete lijst van beschikbare archetypes, zie de [archetypes-catalogus](/docs/building-ui/archetypes/overview).
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
      Genereert webforJ-themaconfiguraties met behulp van [DWC HueCraft](https://huecraft.dwc.style/). De tool creëert complete sets van CSS-aangepaste eigenschappen met primaire, secundaire, succes-, waarschuwing-, gevaar- en neutrale kleurvarianten.

      **Voorbeeldverzoeken:**
      ```
      "Genereer een webforJ-thema met HSL 220, 70, 50 als primaire kleur voor ons corporate brand"

      "Creëer webforJ-toegankelijk thema genaamd 'ocean' met primaire kleur #0066CC"

      "Genereer een webforJ-thema met onze merk kleur #FF5733"

      "Creëer webforJ-thema met HSL 30, 100, 50 genaamd 'sunset' voor onze app"

      "Genereer toegankelijk webforJ-thema met primaire RGB 44, 123, 229"
      ```
    </div>
  </AccordionDetails>
</Accordion>

## Beschikbare prompts {#available-prompts}

Prompts zijn vooraf geconfigureerde AI-instructies die meerdere tools en workflows combineren voor veelvoorkomende taken. Ze leiden de AI door specifieke stappen en parameters om betrouwbare, herhaalbare resultaten te leveren voor elke ondersteunde workflow.

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>create-app</code></strong> - Maak en voer een webforJ-app uit
  </AccordionSummary>
  <AccordionDetails>
    <div>
      **Argumenten:**
      - `appName` (verplicht) - Applicatienaam (bijv. MyApp, TodoList, Dashboard)
      - `archetype` (verplicht) - Kies uit: `blank`, `hello-world`, `tabs`, `sidemenu`
      - `runServer` (optioneel) - Voer automatisch de ontwikkelingsserver uit (ja/nee)
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>create-theme</code></strong> - Genereer een webforJ-thema vanuit een primaire kleur
  </AccordionSummary>
  <AccordionDetails>
    <div>
      **Argumenten:**
      - `primaryColor` (verplicht) - Kleur in hex (#FF5733), rgb (255,87,51) of hsl (9,100,60) formaat
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

      1. Uitgebreid te zoeken in de kennisbasis
      2. Volledige, productieklare code te schrijven
      3. Het project te compileren met `mvn compile` om te verifiëren dat er geen buildfouten zijn
      4. Fouten iteratief te verhelpen totdat alles werkt
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

1. Klik op het **+** (plus) icoon in het invoerveld voor prompts
2. Selecteer **"Toevoegen vanuit webforJ"** uit het menu
3. Kies de gewenste prompt (bijv. `create-app`, `create-theme`, `search-webforj`)
4. Claude vraagt je om de vereiste argumenten in te voeren
5. Vul de parameters in zoals gevraagd

:::tip Verifieer dat MCP is verbonden
Zoek naar het tools-icoon in de hoek onderaan het invoerveld om te bevestigen dat de webforJ MCP-server is verbonden.
:::

</TabItem>
</Tabs>

## Beste praktijken

Om de meest nauwkeurige en up-to-date webforJ-assistentie te krijgen, volg je deze richtlijnen om optimaal gebruik te maken van de functies van de MCP-server.

### Zorgen voor het gebruik van MCP-server

AI-modellen kunnen de MCP-server overslaan als ze denken dat ze het antwoord al weten. Om ervoor te zorgen dat de MCP-server daadwerkelijk wordt gebruikt:

- **Wees expliciet over webforJ**: Noem altijd "webforJ" in je vraag om frameworkspecifieke zoekopdrachten te activeren
- **Vraag om actuele informatie**: Voeg zinnen toe zoals "laatste webforJ-documentatie" of "huidige webforJ-patronen"
- **Vraag om geverifieerde voorbeelden**: Vraag om "werkende webforJ-codevoorbeelden" om een documentatie-opzoeking af te dwingen
- **Verwijs naar specifieke versies**: Noem je webforJ-versie (bijv. "webforJ `25.02`") om nauwkeurige resultaten te krijgen

### Schrijven van specifieke prompts

**Goede voorbeelden:**
```
"Zoek webforJ-documentatie voor Button-component gebeurtenisafhandeling met voorbeelden"

"Maak een webforJ-project genaamd InventorySystem met gebruik van het sidemenu-archetype met Spring Boot"

"Genereer een webforJ-thema met HSL 220, 70, 50 als primaire kleur voor het corporate brand"
```

**Slechte voorbeelden:**
```
"Hoe werken knoppen"

"Maak een app"

"Maak het blauw"
```

### Dwing het gebruik van MCP-tool af

Als de AI generieke antwoorden geeft zonder de MCP-server te gebruiken:

1. **Vraag expliciet**: "Gebruik de webforJ MCP-server om te zoeken naar `[query]`"
2. **Vraag om documentatiereferenties**: "Vind in webforJ-docs hoe te `[query]`"
3. **Vraag om verificatie**: "Verifieer deze oplossing aan de hand van webforJ-documentatie"
4. **Wees frameworkspecifiek**: Vermeld altijd "webforJ" in je vragen

## AI-configuratie {#ai-customization}

Configureer je AI-assistenten om automatisch de MCP-server te gebruiken en de beste praktijken van webforJ te volgen. Voeg projectspecifieke instructies toe zodat je AI-assistenten altijd de MCP-server gebruiken, volgen de documentatiestandaarden van webforJ en nauwkeurige, actuele antwoorden bieden die voldoen aan de vereisten van je team.

### Projectconfiguratiebestanden

- Voor **VS Code en Copilot**, creëer `.github/copilot-instructions.md`
- Voor **Claude Code**, creëer `CLAUDE.md` in de hoofdmap van je project

Voeg het volgende toe aan het gemaakte markdown-bestand:
```markdown
## Gebruik de webforJ MCP-server om elke webforJ-vraag te beantwoorden

- Roep altijd de "webforj-knowledge-base" tool aan om documentatie relevant voor de vraag op te halen
- Verifieer alle API-handtekeningen tegen de officiële documentatie
- Neem nooit aan dat methodenamen of parameters bestaan zonder te controleren

Verifieer altijd of de code compileert met `mvn compile` voordat je suggesties geeft.
```

## FAQ

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>Waarom gebruikt de AI de webforJ MCP-server niet?</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      De meeste AI-assistenten hebben expliciete instructies nodig om MCP-servers te gebruiken. Configureer je AI-client met de instructies uit de sectie [AI-configuratie](#ai-customization). Zonder deze instructies kunnen AI-assistenten terugvallen op hun trainingsgegevens in plaats van de MCP-server te raadplegen.

      **Snelle oplossing:**
      Vermeld "gebruik webforJ MCP" in je prompt of creëer het juiste configuratiebestand (`.github/copilot-instructions.md` of `CLAUDE.md`).
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>Hoe te verifiëren of de MCP-verbinding werkt</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Gebruik de MCP-inspector om verbindingen te debuggen:

      ```bash
      npx @modelcontextprotocol/inspector
      ```

      Wacht op de boodschap: `🔍 MCP Inspector is up and running at http://127.0.0.1:6274` (poort kan variëren)

      Voer vervolgens in de inspector in:
      1. Voer de MCP-server-URL in: `https://mcp.webforj.com/mcp`
      2. Klik op "Verbinden" om de verbinding tot stand te brengen
      3. Bekijk de beschikbare tools en testquery's
      4. Controleer de aanvraag-/antwoordlogs voor debugging
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
      - **SSE-eindpunt** (`/sse`) - Server-Sent Events voor oudere clients zoals Windsurf

      De meeste gebruikers moeten het MCP-eindpunt gebruiken. Gebruik alleen SSE als je client het standaard MCP-protocol niet ondersteunt.
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>Is het mogelijk om de MCP-server te gebruiken zonder configuratiebestanden?</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Ja, maar het wordt niet aanbevolen. Zonder configuratiebestanden moet je de AI handmatig aanmoedigen om de MCP-server in elk gesprek te gebruiken. Configuratiebestanden instrueren de AI automatisch om de MCP-server voor elke interactie te gebruiken, zodat je niet elke keer instructies hoeft te herhalen.

      **Handmatige benadering:**
      Begin prompts met: "Gebruik de webforJ MCP-server om..."

      **Alternatief: Gebruik vooraf geconfigureerde prompts**
      De MCP-server biedt prompts die werken zonder configuratiebestanden:
      - `/create-app` - Genereer nieuwe webforJ-applicaties
      - `/create-theme` - Creëer toegankelijke CSS-thema's
      - `/search-webforj` - Geavanceerd documentatie zoeken

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
      - Fouten bij het uitvoeren van tools

      Vermeld je query, verwachte resultaat en werkelijke resultaat bij het melden van problemen.
    </div>
  </AccordionDetails>
</Accordion>
<br />
