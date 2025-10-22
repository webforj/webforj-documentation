---
title: MCP Server
sidebar_position: 2.5
sidebar_class_name: new-content
_i18n_hash: cfe1c4447876aff3ab7ba15b26966cba
---
De webforJ Model Context Protocol (MCP) server biedt AI-assistenten directe toegang tot officiële webforJ-documentatie, geverifieerde codevoorbeelden en framework-specifieke patronen, waardoor antwoorden nauwkeuriger zijn en geautomatiseerde projectgeneratie specifiek voor de ontwikkeling van webforJ mogelijk is.

## Wat is een MCP?

Model Context Protocol is een open standaard die AI-assistenten in staat stelt om verbinding te maken met externe tools en documentatie. De webforJ MCP-server implementeert dit protocol om te voorzien in:

- **Kenniszoektocht** - Natuurlijke taalzoekopdrachten in webforJ-documentatie, codevoorbeelden en patronen
- **Projectgeneratie** - Maak webforJ-toepassingen op basis van officiële sjablonen met de juiste structuur
- **Thema creatie** - Genereer toegankelijke CSS-thema's volgens webforJ-ontwerppatronen

## Waarom MCP gebruiken?

Hoewel AI-coderingsassistenten goed zijn in het beantwoorden van basisvragen, hebben ze moeite met complexe webforJ-specifieke vragen die meerdere documentatiedelen bestrijken. Zonder directe toegang tot officiële bronnen kunnen ze:

- Methodes genereren die niet bestaan in webforJ
- Verwijzen naar verouderde of onjuiste API-patronen  
- Code aanleveren die niet compileert
- De syntaxis van webforJ verwarren met andere Java-frameworks
- WebforJ-specifieke patronen verkeerd begrijpen

Met MCP-integratie zijn AI-antwoorden verankerd in daadwerkelijke webforJ-documentatie, codevoorbeelden en frameworkpatronen, waardoor verifieerbare antwoorden worden gegeven met directe links naar officiële bronnen voor diepere verkenning.

:::warning AI Kan Nog Steeds Fouten Maken
Hoewel MCP de nauwkeurigheid aanzienlijk verbetert door toegang te bieden tot officiële webforJ-bronnen, garandeert het geen perfecte codegeneratie. AI-assistenten kunnen nog steeds fouten maken in complexe scenario's. Verifieer altijd de gegenereerde code en test grondig voordat je deze in productie gebruikt.
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

Voeg deze server toe via het Integratiescherm in de instellingen van Claude Desktop:

1. Open Claude Desktop en ga naar Instellingen
2. Klik op "Integraties" in de zijbalk
3. Klik op "Integratie toevoegen" en plak de URL: `https://mcp.webforj.com/mcp`
4. Volg de installatiewizard om de configuratie te voltooien

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

Tools zijn gespecialiseerde functies die de MCP-server biedt aan AI-assistenten. Wanneer je een vraag stelt of een verzoek doet, kan de AI deze tools gebruiken om documentatie te doorzoeken, projecten te genereren of thema's te creëren. Elke tool accepteert specifieke parameters en retourneert gestructureerde data die de AI helpt om nauwkeurige, contextbewuste assistentie te bieden.

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>webforj-knowledge-base</code></strong> - Zoek documentatie en voorbeelden
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Deze tool biedt semantische zoekmogelijkheden door het gehele webforJ-documentatie-ecosysteem. Het begrijpt context en relaties tussen verschillende frameworkconcepten en retourneert relevante documentatie-secties, API-referenties en werkende codevoorbeelden.

      **Voorbeeldvragen:**
      ```
      "Zoek in de webforJ-documentatie naar de Button-component met voorbeeldiconen"

      "Vind webforJ formulier validatie patronen in de nieuwste documentatie"

      "Toon me de huidige webforJ routering setup met @Route annotatie"

      "Zoek in de webforJ-docs naar FlexLayout responsieve ontwerppatronen"

      "Vind webforJ webcomponentintegratie in de officiële documentatie"
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
      Scaffold volledige webforJ-toepassingen met officiële Maven-archetypes. De tool creëert een gestandaardiseerde projectdirectory-indeling en omvat startercode op basis van het geselecteerde sjabloon. Gecreëerde projecten bevatten een kant-en-klare build-systeem, resource-mappen en configuratiebestanden voor onmiddellijke ontwikkeling en implementatie.

      **Voorbeeldprompts:**
      ```
      "Maak een webforJ-project met de naam CustomerPortal met behulp van de hello-world archetype"

      "Genereer een webforJ Spring Boot-project met tabbladenindeling genaamd Dashboard"

      "Maak een nieuwe webforJ-app met sidemenu-archetype voor het AdminPanel-project"

      "Genereer een webforJ leeg project met de naam TestApp met com.example groupId"

      "Maak een webforJ-project InventorySystem met behulp van sidemenu-archetype met Spring Boot"
      ```

      Bij het gebruik van deze tool, kun je kiezen uit verschillende project-sjablonen:

      **Archetypes** (project-sjablonen):
      - `hello-world` - Basisapp met voorbeeldcomponenten om webforJ-functies te demonstreren
      - `blank` - Minimale projectstructuur om vanaf nul te beginnen
      - `tabs` - Vooraf gebouwde tabinterface-indeling voor multi-view toepassingen
      - `sidemenu` - Indeling voor zij-navigatiemenu voor adminpanelen of dashboards

      **Flavors** (framework-integratie):
      - `webforj` - Standaard webforJ-app
      - `webforj-spring` - webforj geïntegreerd met Spring Boot voor afhankelijkheidsinjectie en enterprise-functies

      :::tip Beschikbare Archetypes
      webforJ wordt geleverd met verschillende vooraf gedefinieerde archetypes om je snel op weg te helpen. Voor een complete lijst van beschikbare archetypes, zie de [archetypes catalogus](../building-ui/archetypes/overview).
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
      Genereert webforJ-thema configuraties met behulp van [DWC HueCraft](https://huecraft.dwc.style/). De tool maakt volledige CSS aangepaste eigenschapssets met primaire, secundaire, succes-, waarschuwings-, gevaar- en neutrale kleurvarianten.

      **Voorbeeldverzoeken:**
      ```
      "Genereer een webforJ-thema met HSL 220, 70, 50 als primaire kleur voor ons bedrijfsmerk"

      "Maak een webforJ toegankelijk thema met de naam 'ocean' met primaire kleur #0066CC"

      "Genereer een webforJ-thema met onze merk kleur #FF5733"

      "Maak een webforJ-thema met HSL 30, 100, 50 met de naam 'sunset' voor onze app"

      "Genereer toegankelijk webforJ-thema met primaire RGB 44, 123, 229"
      ```
    </div>
  </AccordionDetails>
</Accordion>

## Beschikbare prompts {#available-prompts}

Prompts zijn vooraf geconfigureerde AI-instructies die meerdere tools en workflows combineren voor veelvoorkomende taken. Ze begeleiden de AI door specifieke stappen en parameters om betrouwbare, reproduceerbare resultaten te leveren voor elke ondersteunde workflow.

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>create-app</code></strong> - Maak en voer een webforJ-app uit
  </AccordionSummary>
  <AccordionDetails>
    <div>
      **Argumenten:**
      - `appName` (vereist) - Toepassingsnaam (bijv. MyApp, TodoList, Dashboard)
      - `archetype` (vereist) - Kies uit: `blank`, `hello-world`, `tabs`, `sidemenu`
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
      2. Volledige, productieklare code te schrijven
      3. Het project te compileren met `mvn compile` om te verifiëren dat er geen bouwfouten zijn
      4. Fouten iteratief te verhelpen totdat alles werkt
    </div>
  </AccordionDetails>
</Accordion>

### Hoe prompts te gebruiken

<Tabs groupId="ide">
<TabItem value="vscode" label="VS Code en Claude Code">

1. Typ <kbd>/</kbd> in de chat om beschikbare prompts te zien
2. Selecteer een prompt uit het dropdownmenu
3. Vul de vereiste parameters in wanneer daarom gevraagd

</TabItem>
<TabItem value="claude-desktop" label="Claude Desktop">

1. Klik op het **+** (plus) pictogram in het invoerveld voor prompts
2. Selecteer **"Toevoegen vanuit webforJ"** uit het menu
3. Kies de gewenste prompt (bijv. `create-app`, `create-theme`, `search-webforj`)
4. Claude zal je vragen om de vereiste argumenten in te voeren
5. Vul de parameters in zoals gevraagd

:::tip Verifieer of MCP is aangesloten
Zoek naar het tools-pictogram in de rechteronderhoek van het invoerveld om te bevestigen dat de webforJ MCP-server is verbonden.
:::

</TabItem>
</Tabs>

## Best practices

Om de meest nauwkeurige en actuele webforJ-assistentie te krijgen, volg je deze richtlijnen om optimaal gebruik te maken van de functies van de MCP-server.

### Zorgen voor gebruik van MCP-server

AI-modellen kunnen de MCP-server overslaan als ze denken dat ze het antwoord al weten. Om ervoor te zorgen dat de MCP-server daadwerkelijk wordt gebruikt:

- **Wees expliciet over webforJ**: Vermeld altijd "webforJ" in je vraag om framework-specifieke zoekopdrachten te activeren
- **Vraag om actuele informatie**: Gebruik zinnen als "laatste webforJ-documentatie" of "huidige webforJ-patronen"
- **Vraag om geverifieerde voorbeelden**: Vraag om "werkende webforJ-codevoorbeelden" om het ophalen van documentatie te forceren
- **Verwijs naar specifieke versies**: Noem je webforJ-versie (bijv. "webforJ `25.02`") om nauwkeurige resultaten te krijgen

### Specifieke prompts schrijven

**Goede voorbeelden:**
```
"Zoek in de webforJ-documentatie naar de Button-component met evenementafhandeling met voorbeelden"

"Maak een webforJ-project met de naam InventorySystem met behulp van het sidemenu-archetype met Spring Boot"

"Genereer een webforJ-thema met HSL 220, 70, 50 als primaire kleur voor het bedrijfsmerk"
```

**Slechte voorbeelden:**
```
"Hoe werken knoppen"

"Maak een app"

"Maak het blauw"
```

### Dwing gebruik van MCP-tools af

Als de AI algemene antwoorden geeft zonder de MCP-server te gebruiken:

1. **Vraag expliciet**: "Gebruik de webforJ MCP-server om te zoeken naar `[query]`"
2. **Vraag om documentatiereferenties**: "Vind in de webforJ-docs hoe je `[query]` doet"
3. **Vraag om verificatie**: "Verifieer deze oplossing met de webforJ-documentatie"
4. **Wees framework-specifiek**: Vermeld altijd "webforJ" in je vragen

## AI-configuratie {#ai-customization}

Configureer je AI-assistenten zodat ze automatisch de MCP-server gebruiken en de beste praktijken van webforJ volgen. Voeg project-specifieke instructies toe zodat je AI-assistenten altijd de MCP-server gebruiken, de documentatiestandaarden van webforJ volgen, en nauwkeurige, actuele antwoorden geven die voldoen aan de vereisten van je team.

### Projectconfiguratiebestanden

- Voor **VS Code en Copilot**, maak `.github/copilot-instructions.md`
- Voor **Claude Code**, maak `CLAUDE.md` in je projectroot

Voeg het volgende toe aan het gemaakte markdown-bestand:
```markdown
## Gebruik de webforJ MCP-server om eventuele webforJ-vragen te beantwoorden

- Roep altijd de "webforj-knowledge-base"-tool aan om documentatie op te halen die relevant is voor de vraag
- Controleer alle API-handtekeningen aan de hand van de officiële documentatie
- Veronderstel nooit dat methodenamen of parameters bestaan zonder te controleren

Verifieer altijd of de code compileert met `mvn compile` voordat je deze voorstelt.
```

## Veelgestelde vragen

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>Waarom gebruikt de AI de webforJ MCP-server niet?</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      De meeste AI-assistenten hebben expliciete instructies nodig om MCP-servers te gebruiken. Configureer je AI-client met de instructies uit de [AI-configuratie](#ai-customization) sectie. Zonder deze instructies kunnen AI-assistenten de voorkeur geven aan hun trainingsdata in plaats van de MCP-server te raadplegen.

      **Snelle oplossing:**
      Voeg "gebruik webforJ MCP" toe aan je prompt of maak het juiste configuratiebestand (`.github/copilot-instructions.md` of `CLAUDE.md`).
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>Hoe controleer je of de MCP-verbinding werkt?</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Gebruik de MCP-inspecteur om verbindingen te debuggen:

      ```bash
      npx @modelcontextprotocol/inspector
      ```

      Wacht op het bericht: `🔍 MCP Inspector is up and running at http://127.0.0.1:6274` (poort kan variëren)

      Voer vervolgens in de inspecteur uit:
      1. Voer de MCP-server-URL in: `https://mcp.webforj.com/mcp`
      2. Klik op "Verbinden" om de verbinding tot stand te brengen
      3. Bekijk beschikbare tools en test-query's
      4. Houd verzoek-/antwoordlogs in de gaten voor het debuggen
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

      De meeste gebruikers moeten het MCP-eindpunt gebruiken. Gebruik alleen SSE als je client het standaard MCP-protocol niet ondersteunt.
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>Is het mogelijk om de MCP-server zonder configuratiebestanden te gebruiken?</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Ja, maar het is niet aanbevolen. Zonder configuratiebestanden moet je de AI handmatig aanmoedigen om de MCP-server in elk gesprek te gebruiken. Configuratiebestanden instrueren de AI automatisch om de MCP-server voor elke interactie te gebruiken, zodat je de instructies niet telkens hoeft te herhalen.

      **Handmatige benadering:**
      Begin prompt met: "Gebruik de webforJ MCP-server om..."

      **Alternatief: Gebruik vooraf geconfigureerde prompts**
      De MCP-server biedt prompts die werken zonder configuratiebestanden:
      - `/create-app` - Genereer nieuwe webforJ-toepassingen
      - `/create-theme` - Maak toegankelijke CSS-thema's
      - `/search-webforj` - Geavanceerde documentatiezoektocht

      Zie [Beschikbare prompts](#available-prompts) voor details.
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>Hoe bij te dragen of problemen te rapporteren</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      **Problemen rapporteren:** [webforJ MCP Probleemtemplate](https://github.com/webforj/webforj/issues/new?template=mcp_report.yml)
      
      **Veelvoorkomende problemen om te rapporteren:**
      - Verouderde documentatie in zoekresultaten
      - Ontbrekende API-methoden of componenten
      - Onjuiste codevoorbeelden
      - Fouten bij tooluitvoering

      Vermeld je query, verwachte resultaat en werkelijke resultaat bij het rapporteren van problemen.
    </div>
  </AccordionDetails>
</Accordion>
<br />
