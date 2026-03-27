---
title: Agent Skills
sidebar_position: 10
sidebar_class_name: new-content
_i18n_hash: cf22942f0e73a936bef31cf8a3a9a043
---
Agentvaardigheden leren AI-codingassistenten hoe ze webforJ-toepassingen kunnen bouwen met de juiste API's, ontwerptokens en componentpatronen. In plaats van te gokken bij de conventies van het framework laadt een AI-assistent een vaardigheid en volgt deze een gestructureerde workflow om code te produceren die compileert en in de eerste poging aan de beste praktijken voldoet.

Vaardigheden volgen de open [Agent Skills](https://agentskills.io/specification) specificatie en werken met meerdere AI-assistenten, waaronder Claude Code, GitHub Copilot in VS Code, en Cursor. 
Elke vaardigheid is een enkele map met een `SKILL.md`-bestand dat het doel en de workflow van de vaardigheid beschrijft, samen met `references/` en `scripts/` mappen voor ondersteunende documentatie en hulpscripts.

Agentvaardigheden voor webforJ zijn beschikbaar in de GitHub-repository [webforj/webforj-agent-skills](https://github.com/webforj/webforJ-agent-skills). 
Met deze vaardigheden geïnstalleerd, zal een AI deze bestanden automatisch laden wanneer het een relevante taak detecteert. 
Bijvoorbeeld, het vragen aan een AI om "deze app het thema van een blauwe palet te geven" activeert de `styling-apps` vaardigheid, die de AI begeleidt bij het opzoeken van geldige DWC-tokens, het schrijven van scoped CSS, en het valideren van elke variabele naam voordat er output wordt geproduceerd.

## Waarom vaardigheden gebruiken? {#why-use-skills}

Zonder vaardigheden produceren AI-assistenten vaak webforJ-code die er plausibel uitziet, maar in de praktijk faalt. Veelvoorkomende problemen zijn:

- Het uitvinden van `--dwc-*` token namen die niet bestaan (CSS compileert maar heeft geen effect)
- Het gebruiken van de verkeerde basis klasse voor component wrappers (`Composite` in plaats van `ElementComposite`, of omgekeerd)
- Ontbrekende `PropertyDescriptor` patronen, evenementannotaties, of concerninterfaces
- Hardcoded kleuren die de donkere modus breken
- Het overslaan van validatiestappen die stille mislukkingen opvangen

Vaardigheden elimineren deze problemen door de AI exacte beslissingsregisters, opzoekscripts en validatielijsten voor elk type taak te geven.

## Hoe vaardigheden verschillen van MCP {#how-skills-differ-from-mcp}

Vaardigheden en de [webforJ MCP-server](./mcp) vervullen complementaire rollen. MCP biedt live tools die de AI tijdens runtime kan aanroepen om documentatie te doorzoeken of projecten te genereren. Vaardigheden bieden statische kennis en stap-voor-stap workflows die de AI begeleiden in de aanpak van een taak.

| | MCP-server | Agentvaardigheden |
|---|---|---|
| **Wat het biedt** | Live tools: documentatiezoektocht, project scaffolding, thema generatie | Statische kennis: workflows, beslissingsregisters, referentiedocumenten, hulpscripts |
| **Wanneer het actief is** | Op aanvraag, wanneer de AI een tool aanroept | Automatisch, wanneer de AI een passende taak detecteert |
| **Het beste voor** | Het opzoeken van specifieke API's, het genereren van starterprojecten, het creëren van themapalletten | Einde-tot-einde taken die vereisen dat frameworkconventies en meerstappenworkflows worden gevolgd |

In de praktijk werken de twee goed samen. De MCP-server's `webforj-create-theme` tool genereert een geldige palet vanuit een enkele kleur, en de `styling-apps` vaardigheid begeleidt de AI vervolgens bij componentniveau styling en validaite van de donkere modus met behulp van die palet.

Vaardigheden zijn statische bestanden die vanaf de schijf worden gelezen—ze voegen geen runtime overhead toe of doen externe API-aanroepen. De AI laadt het referentiemateriaal van een vaardigheid in zijn contextvenster wanneer dit relevant is, wat een aantal contexttokens gebruikt, maar de resulterende outputkwaliteit voor framework-specifiek werk is aanzienlijk hoger.

## Installatie {#installation}

Clone de [webforJ agent vaardigheden repository](https://github.com/webforj/webforJ-agent-skills), en kopieer vervolgens de vaardigheidsmappen naar de locatie die jouw AI-tool verwacht. Elke tool ondersteunt twee scopes:

- **Project scope**: de vaardigheid is alleen beschikbaar in dat project
- **User scope**: de vaardigheid is beschikbaar in al je projecten

<Tabs groupId="ide">
<TabItem value="claude-code" label="Claude Code" default>

```bash
git clone https://github.com/webforj/webforJ-agent-skills.git
cd webforJ-agent-skills

# Project scope
cp -r creating-components /path/to/your/project/.claude/skills/
cp -r styling-apps /path/to/your/project/.claude/skills/

# User scope
cp -r creating-components ~/.claude/skills/
cp -r styling-apps ~/.claude/skills/
```

</TabItem>
<TabItem value="vscode" label="VS Code Copilot">

```bash
git clone https://github.com/webforj/webforJ-agent-skills.git
cd webforJ-agent-skills

# Project scope
cp -r creating-components /path/to/your/project/.github/skills/
cp -r styling-apps /path/to/your/project/.github/skills/

# User scope
cp -r creating-components ~/.copilot/skills/
cp -r styling-apps ~/.copilot/skills/
```

</TabItem>
<TabItem value="cursor" label="Cursor">

```bash
git clone https://github.com/webforj/webforJ-agent-skills.git
cd webforJ-agent-skills

# Project scope
cp -r creating-components /path/to/your/project/.cursor/skills/
cp -r styling-apps /path/to/your/project/.cursor/skills/

# User scope
cp -r creating-components ~/.cursor/skills/
cp -r styling-apps ~/.cursor/skills/
```

</TabItem>
</Tabs>

:::tip[Welke scope te gebruiken]
Gebruik **project scope** wanneer je samenwerkt met een team, zodat iedereen in het project profiteert van dezelfde vaardigheden. Gebruik **user scope** wanneer je aan meerdere webforJ-projecten werkt en de vaardigheden overal beschikbaar wilt hebben zonder ze in elk repository te kopiëren.
:::

## Beschikbare vaardigheden {#available-skills}

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>creating-components</code></strong>: bouw herbruikbare webforJ-componenten van webcomponentbibliotheken, JavaScript-bibliotheken of bestaande webforJ-componenten
  </AccordionSummary>
  <AccordionDetails>
    <div>

[Deze vaardigheid](https://github.com/webforj/webforJ-agent-skills/tree/main/creating-components) begeleidt een AI-assistent bij het bouwen van herbruikbare Java-componenten vanuit elke bron, of dat nu een bestaande webcomponentbibliotheek, een gewone JavaScript-bibliotheek, of een samenstelling van bestaande webforJ-componenten is.

**Wat het dekt**

De vaardigheid definieert vijf paden voor het creëren van componenten en leert de AI om het juiste pad te selecteren op basis van de taak:

| Pad | Wanneer te gebruiken | Basis klasse |
|---|---|---|
| Een bestaand Custom Element-bibliotheek wrappen | Bibliotheek levert Custom Elements (`<x-button>`, `<x-dialog>`) | `ElementComposite` / `ElementCompositeContainer` |
| Een Custom Element bouwen en het vervolgens wrappen | Nieuwe visuele component of een gewone JS-bibliotheek wrappen | `ElementComposite` / `ElementCompositeContainer` |
| Samenstellen van webforJ-componenten | Bestaande webforJ-componenten combineren in een herbruikbare eenheid | `Composite<T>` |
| Een HTML-element uitbreiden | Lichtgewicht eenmalige integratie zonder Shadow DOM | `Div`, `Span`, enz. |
| Page-level utility | Browser-API of globale functie zonder DOM-widget | Gewone Java-klasse + `EventDispatcher` |

**Workflow**

Voor het wrappen van Custom Element (het meest voorkomende pad) leidt de vaardigheid de AI door een gestructureerde workflow:

1. **Setup**: download third-party JS/CSS in de `src/main/resources/static/libs/` map van het project. De vaardigheid instrueert de AI om lokale bronnen boven CDN-links te verkiezen voor offline betrouwbaarheid.
2. **Componentgegevens extraheren**: gebruik het bijgevoegde `extract_components.mjs` script om een Custom Elements-manifest te parseren en een gestructureerde specificatie van de eigenschappen, evenementen, slots en CSS-aanpassingen van elke component te produceren.
3. **Java-wrappers schrijven**: maak `ElementComposite` of `ElementCompositeContainer` klassen met `PropertyDescriptor` velden, evenementenklassen, slotmethoden en concerninterfaces, allemaal volgens webforJ-conventies.
4. **Tests schrijven**: genereer JUnit 5-tests met behulp van `PropertyDescriptorTester` en gestructureerde testpatronen voor eigenschappen, slots en evenementen.

**Referentiemateriaal**

De vaardigheid bevat acht referentiedocumenten die `ElementComposite` patronen, componentcompositie, property descriptors, gebeurtenisafhandeling, JavaScript-interoperabiliteit, testpatronen en veelvoorkomende anti-patronen dekken.

</div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>styling-apps</code></strong>: thema en stijl webforJ-toepassingen met behulp van het DWC-ontwerptoken-systeem
  </AccordionSummary>
  <AccordionDetails>
    <div>

[Deze vaardigheid](https://github.com/webforj/webforJ-agent-skills/tree/main/styling-apps) leert een AI-assistent hoe te stylen webforJ-toepassingen met behulp van het DWC-ontwerptoken-systeem. Het kernprincipe is dat alle visuele waarden gebruikmaken van `--dwc-*` CSS-aangepaste eigenschappen. De vaardigheid handhaaft dit door validatiestappen en opzoekscripts te bieden die voorkomen dat de AI token namen uitvindt of kleuren hardcodeert.

**Wat het dekt**

| Taak | Benadering die de vaardigheid leert |
|------|---------------------------|
| Kleuren reskin | Overschrijven van paletkleur, verzadiging en contrasttokens op `:root` |
| Componentstyling | Zoek eerst de CSS-variabelen van de component op, val terug op `::part()` alleen wanneer nodig |
| Lay-out en ruimte | Gebruik `--dwc-space-*` en `--dwc-size-*` tokens |
| Typografie | Gebruik `--dwc-font-*` tokens |
| Volledig thema | Paletconfiguratie met semantische token-remapping |
| Tafelstyling | Alleen `::part()` selectors (tafels exposeren geen CSS-variabelen) |
| Google Charts | JSON-thema bestand geladen via `Assets.contentOf()` en Gson |

**Workflow**

De vaardigheid handhaaft een strikte opzoek-voor-schrijfdiscipline:

1. **Classificeer de taak**: bepaal of dit een paletreskin, componentstyling, lay-outwerk, of een volledig thema is.
2. **Scan de app**: lees de Java-bron om elke component, thema-variant en expanse in gebruik te vinden.
3. **Zoek elke component op**: voer het bijgeleverde `component_styles.py` script uit om de exacte CSS-variabelen, `::part()` namen en gereflecteerde attributen elke component ondersteunt, op te halen. De AI schrijft geen CSS totdat deze stap is voltooid.
4. **Schrijf CSS**: produceer geneste, compacte CSS die de DWC-conventies volgt: globale tokens eerst, dan component CSS-variabelen, dan `::part()` overschrijvingen als laatste redmiddel.
5. **Valideer**: voer het opzoekscript opnieuw uit en verifieer dat elke token, partnaam en selector in de output daadwerkelijk bestaat. Los alles op dat faalt.

**Belangrijke regels die de vaardigheid afdwingt**

- **Zeven paletten alleen**: `primary`, `success`, `warning`, `danger`, `info`, `default`, en `gray`. Namen zoals `secondary` of `accent` bestaan niet in DWC en falen stil.
- **Geen hardcoded kleuren**: elke kleurwaarde moet een `var()` referentie zijn, ook binnen `box-shadow` en `border`. Hardcoded waarden breken de donkere modus.
- **CSS-variabelen boven `::part()`**: component CSS-variabelen zijn de bedoelde styling-API. `::part()` is de ontsnappingsroute voor gevallen waarin er geen variabele bestaat.
- **Scoped selectors**: blote tagselectoren op componenten met `theme` of `expanse` attributen overschrijven alle varianten. De vaardigheid vereist `:not([theme])` of `[theme~="value"]` scoping.

</div>
  </AccordionDetails>
</Accordion>
