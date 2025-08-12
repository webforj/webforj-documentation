---
title: MCP Server
sidebar_position: 2.5
sidebar_class_name: new-content
_i18n_hash: caf6cb2973387f33706be4c4416a594c
---
De webforJ Model Context Protocol (MCP) server biedt AI-assistenten directe toegang tot officiële webforJ-documentatie, geverifieerde codevoorbeelden en specifieke patronen voor frameworks, wat reacties met nauwkeurigere antwoorden mogelijk maakt, en automatische projectgeneratie specifiek voor webforJ-ontwikkeling.

## Wat is een MCP?

Model Context Protocol is een open standaard die AI-assistenten in staat stelt om verbinding te maken met externe tools en documentatie. De webforJ MCP-server implementeert dit protocol om te voorzien in:

- **Kenniszoektocht** - Zoekopdracht in natuurlijke taal in webforJ-documentatie, codevoorbeelden en patronen
- **Projectgeneratie** - Maak webforJ-toepassingen vanaf officiële sjablonen met een juiste structuur
- **Thema's maken** - Genereer toegankelijke CSS-thema's volgens webforJ-ontwerpatronen

## Waarom MCP gebruiken?

Hoewel AI-codingassistenten uitblinken in het beantwoorden van basisvragen, hebben ze moeite met complexe webforJ-specifieke vragen die meerdere documentatie-secties bestrijken. Zonder directe toegang tot officiële bronnen kunnen ze:

- Methoden genereren die niet bestaan in webforJ
- Verwijzen naar verouderde of onjuiste API-patronen  
- Code leveren die niet compileert
- WebforJ-syntaxis verwarren met andere Java-frameworks
- WebforJ-specifieke patronen verkeerd begrijpen

Met MCP-integratie zijn AI-antwoorden verankerd in de daadwerkelijke webforJ-documentatie, codevoorbeelden en frameworkpatronen, wat verifieerbare antwoorden met directe links naar officiële bronnen voor diepere verkenning mogelijk maakt.

:::warning AI kan nog steeds fouten maken
Hoewel MCP de nauwkeurigheid aanzienlijk verbetert door toegang te bieden tot officiële webforJ-bronnen, garandeert het geen perfecte codegeneratie. AI-assistenten kunnen nog steeds fouten maken in complexe scenario's. Verifieer altijd de gegenereerde code en test grondig voordat je deze in productie gebruikt.
:::

## Installatie

De webforJ MCP-server is gehost op `https://mcp.webforj.com` met twee eindpunten:

- **MCP-eindpunt** (`/mcp`) - Voor Claude, VS Code, Cursor
- **SSE-eindpunt** (`/sse`) - Voor legacy clients

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

Gebruik het Claude CLI-commando om de server te registreren:

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
4. Volg de wizard om de configuratie te voltooien

Voor gedetailleerde instructies, zie de [officiële integratiegids](https://support.anthropic.com/en/articles/11175166-about-custom-integrations-using-remote-mcp).

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

Tools zijn gespecialiseerde functies die de MCP-server biedt aan AI-assistenten. Wanneer je een vraag stelt of een verzoek doet, kan de AI deze tools aanroepen om documentatie te doorzoeken, projecten te genereren of thema's te maken. Elke tool accepteert specifieke parameters en retourneert gestructureerde gegevens die de AI helpen om nauwkeurige, contextbewuste assistentie te bieden.

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>webforj-knowledge-base</code></strong> - Doorzoek documentatie en voorbeelden
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Deze tool biedt semantische zoekmogelijkheden door het hele webforJ-documentatie-ecosysteem. Het begrijpt de context en relaties tussen verschillende frameworkconcepten en retourneert relevante documentatiedelen, API-referenties en werkende codevoorbeelden.

      **Voorbeeldvragen:**
      ```
      "Zoek webforJ documentatie voor Button-component met voorbeeldiconen"

      "Vind webforJ formulier validatiepatronen in de nieuwste documentatie"

      "Toon me de huidige webforJ routeringssetup met de @Route-annotatie"

      "Zoek webforJ-documenten voor FlexLayout responsieve ontwerppatronen"

      "Vind de integratie van het webforJ-webcomponent in de officiële documentatie"
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
      Scaffold complete webforJ-toepassingen met behulp van officiële Maven-archetypen. De tool maakt een gestandaardiseerd projectdirectory-indeling en bevat startercode op basis van de geselecteerde sjabloon. Gegenerateerde projecten omvatten een kant-en-klare build-systeem, resource-mappen en configuratiebestanden voor onmiddellijke ontwikkeling en implementatie.

      **Voorbeeldprompts:**
      ```
      "Maak een webforJ-project genaamd CustomerPortal met het hello-world-archetype"

      "Genereer een webforJ Spring Boot-project met tabbladindeling genaamd Dashboard"

      "Maak een nieuwe webforJ-app met het sidemenu-archetype voor het AdminPanel-project"

      "Genereer een webforJ blanco project genaamd TestApp met com.example groupId"

      "Maak webforJ-project InventorySystem met sidemenu-archetype met Spring Boot"
      ```

      Bij het gebruik van deze tool kun je kiezen uit verschillende projecttemplates:

      **Archetypes** (projecttemplates):
      - `hello-world` - Basisapp met voorbeeldcomponenten om webforJ-functies te demonstreren
      - `blank` - Minimale projectstructuur voor het beginnen vanaf nul
      - `tabs` - Vooraf gebouwde tabbladinterface-indeling voor applicaties met meerdere weergaven
      - `sidemenu` - Indeling met zij-navigatiemenu voor beheerderspanelen of dashboards

      **Flavors** (frameworkintegratie):
      - `webforj` - Standaard webforJ-app
      - `webforj-spring` - webforJ geïntegreerd met Spring Boot voor afhankelijkheidsinjectie en enterprise-functies

      :::tip Beschikbare Archetypes
      webforJ komt met verschillende vooraf gedefinieerde archetypes om je snel op weg te helpen. Voor een complete lijst van beschikbare archetypes, zie de [archetypes catalogus](../building-ui/archetypes/overview).
      :::
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>webforj-create-theme</code></strong> - Maak toegankelijke CSS-thema's
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Genereert webforJ-thema-configuraties met behulp van [DWC HueCraft](https://huecraft.dwc.style/). De tool genereert complete sets van CSS-aangepaste eigenschappen met primaire, secundaire, succes-, waarschuwing-, gevaar- en neutrale kleurvarianten.

      **Voorbeeldverzoeken:**
      ```
      "Genereer een webforJ-thema met HSL 220, 70, 50 als primaire kleur voor ons bedrijfsmerk"

      "Maak webforJ-toegankelijk thema genaamd 'ocean' met primaire kleur #0066CC"

      "Genereer een webforJ-thema met onze merkkleur #FF5733"

      "Maak een webforJ-thema met HSL 30, 100, 50 genaamd 'sunset' voor onze app"

      "Genereer toegankelijke webforJ-thema met primaire RGB 44, 123, 229"
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
      - `appName` (vereist) - Toepassingsnaam (bijv. MyApp, TodoList, Dashboard)
      - `archetype` (vereist) - Kies uit: `blank`, `hello-world`, `tabs`, `sidemenu`
      - `runServer` (optioneel) - Voer automatisch de ontwikkelserver uit (ja/nee)
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
      - `primaryColor` (vereist) - Kleur in hex (#FF5733), rgb (255,87,51) of hsl (9,100,60) formaat
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
      2. Compleet, productieklaar code te schrijven
      3. Het project te samenstellen met `mvn compile` om te verifiëren dat er geen bouwfouten zijn
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

1. Klik op het **+** (plus) icoon in het promptinvoerveld
2. Selecteer **"Toevoegen vanuit webforJ"** uit het menu
3. Kies de gewenste prompt (bijv. `create-app`, `create-theme`, `search-webforj`)
4. Claude vraagt je om de vereiste argumenten in te voeren
5. Vul de parameters in zoals gevraagd

:::tip Verifieer MCP is verbonden
Zoek naar het tools-pictogram in de onderste hoek van het invoerveld om te bevestigen dat de webforJ MCP-server is verbonden.
:::

</TabItem>
</Tabs>

## Best practices

Om de meest nauwkeurige en actuele webforJ-assistentie te krijgen, volg je deze richtlijnen om optimaal gebruik te maken van de functies van de MCP-server.

### Zorgen voor gebruik van de MCP-server

AI-modellen kunnen de MCP-server overslaan als ze denken dat ze het antwoord al weten. Om ervoor te zorgen dat de MCP-server daadwerkelijk wordt gebruikt:

- **Wees expliciet over webforJ**: Vermeld altijd "webforJ" in je vraag om framework-specifieke zoekopdrachten te activeren
- **Vraag om actuele informatie**: Voeg zinnen toe zoals "laatste webforJ-documentatie" of "huidige webforJ-patronen"
- **Vraag om geverifieerde voorbeelden**: Vraag om "werkende webforJ-codevoorbeelden" om een documentatie-opzoeking af te dwingen
- **Verwijs naar specifieke versies**: Vermeld je webforJ-versie (bijv. "webforJ `25.02`") om nauwkeurige resultaten te krijgen

### Schrijf specifieke prompts

**Goede voorbeelden:**
```
"Doorzoek webforJ-documentatie naar Button-component eventafhandeling met voorbeelden"

"Maak een webforJ-project genaamd InventorySystem met het sidemenu-archetype met Spring Boot"

"Genereer een webforJ-thema met HSL 220, 70, 50 als primaire kleur voor bedrijfsmerk"
```

**Slechte voorbeelden:**
```
"Hoe werken knoppen"

"Maak een app"

"Maak het blauw"
```

### Forceer gebruik van de MCP-tool

Als de AI algemene antwoorden geeft zonder de MCP-server te gebruiken:

1. **Vraag expliciet aan**: "Gebruik de webforJ MCP-server om te zoeken naar `[query]`"
2. **Vraag om documentatiereferenties**: "Vind in webforJ-documenten hoe te `[query]`"
3. **Vraag om verificatie**: "Verifieer deze oplossing aan de hand van de webforJ-documentatie"
4. **Wees framework-specifiek**: Vermeld altijd "webforJ" in je vragen

## AI-aanpassing {#ai-customization}

Configureer je AI-assistenten zodat ze automatisch de MCP-server gebruiken en de beste praktijken van webforJ volgen. Voeg project-specifieke instructies toe, zodat je AI-assistenten altijd de MCP-server gebruiken, de webforJ-documentatiestandaarden volgen en nauwkeurige, actuele antwoorden geven die aansluiten bij de vereisten van je team.

### Projectconfiguratiebestanden

- Voor **VS Code en Copilot**, maak .github/copilot-instructions.md
- Voor **Claude Code**, maak CLAUDE.md in je projectroot

Voeg het volgende toe aan het gemaakte markdown-bestand:
```markdown
## Gebruik de webforJ MCP-server om webforJ-vragen te beantwoorden

- Roep altijd de "webforj-knowledge-base" tool aan om relevante documentatie op te halen
- Verifieer alle API-handtekeningen tegen de officiële documentatie
- Neem nooit aan dat methodenamen of parameters bestaan zonder te controleren

Verifieer altijd of de code compileert met `mvn compile` voordat je suggesties doet.
```

## FAQ

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>Waarom gebruikt de AI de webforJ MCP-server niet?</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      De meeste AI-assistenten hebben expliciete instructies nodig om MCP-servers te gebruiken. Configureer je AI-client met de instructies uit de [AI-aanpassings](#ai-customization) sectie. Zonder deze instructies kunnen AI-assistenten terugvallen op hun trainingsgegevens in plaats van de MCP-server te raadplegen.

      **Snelle oplossing:**
      Voeg "gebruik webforJ MCP" toe aan je prompt of maak het juiste configuratiebestand aan (.github/copilot-instructions.md of CLAUDE.md).
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>Hoe verifieer je of de MCP-verbinding werkt</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Gebruik de MCP-inspecteur om verbindingen te debuggen:

      ```bash
      npx @modelcontextprotocol/inspector
      ```

      Wacht op het bericht: `🔍 MCP Inspector is up and running at http://127.0.0.1:6274` (poort kan variëren)

      Voer vervolgens in de inspecteur:
      1. Voer de MCP-server-URL in: `https://mcp.webforj.com/mcp`
      2. Klik op "Verbinden" om de verbinding tot stand te brengen
      3. Bekijk beschikbare tools en testquery's
      4. Houd verzoek/antwoordlogs in de gaten voor debugging
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
      - **SSE-eindpunt** (`/sse`) - Server-Sent Events voor legacy clients zoals Windsurf

      De meeste gebruikers moeten het MCP-eindpunt gebruiken. Gebruik SSE alleen als je client het standaard MCP-protocol niet ondersteunt.
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>Is het mogelijk om de MCP-server te gebruiken zonder configuratiebestanden?</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Ja, maar het is niet aanbevolen. Zonder configuratiebestanden moet je de AI handmatig vragen om de MCP-server in elke conversatie te gebruiken. Configuratiebestanden instrueren de AI automatisch om de MCP-server te gebruiken voor elke interactie, zodat je de instructies niet elke keer hoeft te herhalen.

      **Handmatige aanpak:**
      Begin prompts met: "Gebruik de webforJ MCP-server om..."

      **Alternatief: Gebruik vooraf geconfigureerde prompts**
      De MCP-server biedt prompts die werken zonder configuratiebestanden:
      - `/create-app` - Genereer nieuwe webforJ-toepassingen
      - `/create-theme` - Maak toegankelijke CSS-thema's
      - `/search-webforj` - Geavanceerde documentatiezoekopdracht

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
      **Problemen melden:** [webforJ MCP Feedback](https://github.com/webforj/webforj-mcp-feedback/issues)
      
      **Veel voorkomende problemen om te melden:**
      - Verouderde documentatie in zoekresultaten
      - Ontbrekende API-methoden of componenten
      - Onjuiste codevoorbeelden
      - Fouten bij de uitvoering van tools

      Voeg je vraag, verwachte resultaat en feitelijke resultaat toe bij het melden van problemen.
    </div>
  </AccordionDetails>
</Accordion>
<br />
