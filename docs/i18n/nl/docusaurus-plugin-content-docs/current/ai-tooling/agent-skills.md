---
title: Agent Skills
sidebar_position: 10
description: >-
  Install Agent Skills so AI coding assistants follow webforJ workflows for
  building forms, adding servlets, styling apps, and creating components.
_i18n_hash: 6dc21bfd21fb27f2e71cb2265f6cde8c
---
Agent Skills leren AI-coderingsassistenten hoe ze webforJ-toepassingen moeten bouwen met de juiste API's, ontwerptokens en componentpatronen. In plaats van te gissen naar frameworkconventies, laadt de assistent een vaardigheid en volgt een gestructureerde workflow om code te produceren die compileert en de beste praktijken volgt bij de eerste poging.

:::tip Gebruik de plugin
De onderstaande vaardigheden worden geleverd in de **[webforJ AI-plugin](/docs/ai-tooling)** samen met de [MCP-server](/docs/ai-tooling/mcp). Eén installatie geeft je assistent beide onderdelen.
:::

Vaardigheden volgen de open [Agent Skills](https://agentskills.io/specification) standaard en werken met veel AI-assistenten, waaronder Claude Code, GitHub Copilot, Cursor, Gemini CLI, OpenAI Codex, en meer. Een vaardigheid vertelt de assistent welke soort taak het behandelt; de assistent laadt deze automatisch wanneer jouw prompt overeenkomt. Bijvoorbeeld, het vragen "thema deze app met een blauw palet" activeert de `webforj-styling-apps` vaardigheid, die de assistent begeleidt bij het opzoeken van geldige DWC-tokens, het schrijven van specifieke CSS en het valideren van elke variabelenaam voordat er iets naar de schijf wordt geschreven.

## Waarom vaardigheden gebruiken? {#why-use-skills}

De MCP-server maakt nauwkeurige webforJ-informatie beschikbaar op aanvraag, maar alleen vertelt het de assistent _wanneer_ iets moet worden opgezocht, _welke_ benadering bij de taak past, of _in welke volgorde_ dingen moeten gebeuren. Daar komen vaardigheden om de hoek kijken.

Vaardigheden geven de assistent een taak-specifiek speelboek: hoe de werkzaamheden voor zich moeten classificeren, welke webforJ-patronen passen, welke MCP-tools bij elke stap geraadpleegd moeten worden, en hoe de output gevalideerd moet worden voordat deze wordt teruggegeven. Het resultaat is consistente, conventievolgende webforJ-code in plaats van een verzameling technisch geldige, maar stijlmatig mismatched snippets.

## Hoe vaardigheden verschillen van MCP {#how-skills-differ-from-mcp}

Vaardigheden en de [webforJ MCP-server](/docs/ai-tooling/mcp) vervullen aanvullende rollen. De MCP-server biedt live tools die de assistent kan aanroepen om informatie op te halen of output te genereren. Vaardigheden bieden de workflow die de assistent vertelt _wanneer_ hij die tools moet aanspreken, in welke volgorde dingen moeten gebeuren, en hoe het resultaat gevalideerd moet worden.

| | MCP-server | Agent Skills |
|---|---|---|
| **Wat het biedt** | Tools die de assistent op aanvraag oproept (documentzoekfunctie, scaffolding, themageneratie, tokenvalidatie) | Workflows en beslissingsschema's die begeleiden hoe de assistent een taak benadert |
| **Wanneer het actief is** | Wanneer de assistent besluit een tool aan te roepen | Automatisch, wanneer de assistent een overeenkomstige taak detecteert |
| **Het beste voor** | Het beantwoorden van specifieke vragen, genereren van artefacten | Einde-tot-einde taken die een consistente webforJ-aanpak nodig hebben |

In de praktijk werken deze twee het beste samen - en de [webforJ AI-plugin](https://github.com/webforj/webforj-ai) wordt als één installatie geleverd.

## Installatie {#installation}

Installeer de **[webforJ AI-plugin](/docs/ai-tooling)** - het levert beide onderstaande vaardigheden samen met de MCP-server. Voor clients die geen plugins ondersteunen, vermeldt de [webforJ AI-repository](https://github.com/webforj/webforj-ai#clients) de vaardighedengids die elke tool leest, zodat je de vaardigheid mappen handmatig kunt kopiëren.

## Beschikbare vaardigheden {#available-skills}

<AccordionGroup>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>webforj-adding-servlets</code></strong>: voeg REST-eindpunten, webhooks en aangepaste servlets toe
  </AccordionSummary>
  <AccordionDetails>
    <div>

Gebruik dit wanneer je een niet-UI HTTP-pad nodig hebt - een REST-eindpunt, een webhook-handler, of een derde partij servlet zoals Swagger UI of Spring Web. De assistent kiest de juiste aanpak voor jouw project (Spring `webforj.exclude-urls`, het omleiden van `WebforjServlet` naar een sub-pad, of proxyen via `webforj.conf`) en verbindt het eindpunt zonder de UI-routing van webforJ te verstoren.

**Wanneer het actief is**

- *"Voeg een REST-eindpunt toe bij `/api/orders`."*
- *"Verbind een webhook-handler voor Stripe."*
- *"Monteer Swagger UI bij `/api/docs`."*
- *"Expose een aangepaste servlet die naast de webforJ UI draait."*

</div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>webforj-building-forms</code></strong>: bouw formulieren met binding, validatie en invoermaskers
  </AccordionSummary>
  <AccordionDetails>
    <div>

Gebruik dit voor al het formulierwerk in een webforJ-app: gegevensinvoerformulieren, tweewegbinding met een Java-bean, Jakarta-validatie, gemaskeerde invoercomponenten (telefoon, valuta, IBAN, data), kolommen van de tabel formatteren als valuta of percentage, en responsieve lay-outs met meerdere kolommen. De assistent routet door `BindingContext`, de `Masked*Field` componenten, de masker renderers van de tabel, en `ColumnsLayout`

**Wanneer het actief is**

- *"Bouw een registratieformulier dat is gebonden aan mijn `User` bean."*
- *"Voeg een telefoonnummerinvoer toe met format-as-you-type."*
- *"Formatteer deze tabelkolom als valuta."*
- *"Valideer dit veld met `@NotEmpty` en een aangepaste e-mailchecker."*

</div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>webforj-creating-components</code></strong>: wikkel webcomponenten, JS-bibliotheken of composities
  </AccordionSummary>
  <AccordionDetails>
    <div>

Gebruik dit wanneer je een herbruikbare Java-component nodig hebt die is gewikkeld rond elke bron - een bestaande bibliotheek met aangepaste elementen, een gewone JavaScript-bibliotheek, of een compositie van bestaande webforJ-componenten. De assistent kiest de juiste webforJ-basisclass voor de taak, verbindt eigenschappen, gebeurtenissen en slots met de juiste patronen en produceert tests die voldoen aan de webforJ-conventies.

**Wanneer het actief is**

- *"Wikkel deze bibliotheek met aangepaste elementen als webforJ-componenten."*
- *"Comprimeer deze webforJ-componenten tot een herbruikbare kaart."*
- *"Integreer deze gewone JavaScript-bibliotheek als een webforJ-component."*
- *"Expose deze Browser-API als een webforJ-hulpprogramma."*

</div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>webforj-handling-timers-and-async</code></strong>: plan timers, debouncers en asynchrone taken
  </AccordionSummary>
  <AccordionDetails>
    <div>

Gebruik dit voor periodieke taken, polling, debounced search-as-you-type, throttling, en langdurig achtergrondwerk dat de UI bijwerkt terwijl het draait. De assistent kiest de juiste primitive (`Interval`, `Debouncer`, `Environment.runLater`, `PendingResult`) en vermijdt de runtime-valstrikken van raw `java.util.Timer`, `javax.swing.Timer`, of threads die buiten de webforJ-omgeving zijn aangemaakt, die allemaal `IllegalStateException` gooien van het moment dat ze een UI-component aanraken.

**Wanneer het actief is**

- *"Ververs dit dashboard elke 30 seconden."*
- *"Voeg een debouncer voor zoeken terwijl je typt toe."*
- *"Voer dit CPU-zware werk op de achtergrond uit en werk de voortgangsbalk bij."*
- *"Poll dit REST-eindpunt totdat het `done` retourneert."*

</div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>webforj-localizing-apps</code></strong>: voeg i18n en vertalingsondersteuning toe
  </AccordionSummary>
  <AccordionDetails>
    <div>

Gebruik dit voor al het internationalisatiewerk: het laden van berichtbundels, het wisselen van talen tijdens runtime, het automatisch detecteren van de browser-locale van de gebruiker, en het vertalen van componentlabels. De assistent routet door de `BundleTranslationResolver` van webforJ 25.12, de `HasTranslation` concern, `LocaleObserver`, en pluggable custom resolvers, en dekt zowel de Spring- als plain webforJ-paden.

**Wanneer het actief is**

- *"Voeg meertalige ondersteuning toe met Engels en Spaans."*
- *"Detecteer de browser-locale van de gebruiker en pas deze toe bij de opstart."*
- *"Voeg een taalschakelaar toe aan de navigatiebalk."*
- *"Verplaats alle hardgecodeerde strings naar een berichtenbundel."*

</div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>webforj-securing-apps</code></strong>: bescherm routes met login en rolgebaseerde toegang
  </AccordionSummary>
  <AccordionDetails>
    <div>

Gebruik dit voor alles wat routes in een webforJ-app beschermt: inloggen en uitloggen, rolgebaseerde toegang, openbare landingspagina's, secties alleen voor beheerders, eigendomsregels, en beveiligde standaard beleidsregels. De assistent geeft de voorkeur aan Spring Security wanneer Spring Boot op de classpath staat en valt terug op het eenvoudige beveiligingsframework van webforJ als dit niet het geval is. Het past de juiste annotaties toe (`@AnonymousAccess`, `@PermitAll`, `@RolesAllowed`, `@RouteAccess`, `@RegisteredEvaluator`) en legt uit welke terminal versus samenstelbaar zijn, zodat de beveiligde standaard nog steeds doet wat hij zegt.

**Wanneer het actief is**

- *"Bescherm `/admin` zodat alleen gebruikers met de rol `ADMIN` deze kunnen zien."*
- *"Voeg een openbare landingspagina toe die iedereen kan bezoeken."*
- *"Toon de naam van de ingelogde gebruiker in de header."*
- *"Laat alleen een gebruiker een record bewerken dat zij bezitten."*

</div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>webforj-styling-apps</code></strong>: onderwerpen van apps met DWC-ontwerptokens
  </AccordionSummary>
  <AccordionDetails>
    <div>

Gebruik dit voor al het visuele werk op een webforJ-app: palet herkleuren, styling op componentniveau, lay-out en spacing, typografie, volledige thema's, het uiterlijk van tabellen, of gecoördineerde kleuren voor Google Charts. De assistent schrijft CSS die zich houdt aan DWC-ontwerptokens, scoped selectors correct, en valideert elke `--dwc-*` referentie tegen de echte catalogus voor jouw versie van webforJ - zodat de donkere modus en themawisseling blijven werken.

**Wanneer het actief is**

- *"Thema deze app met een blauw palet."*
- *"Style de dwc-button om overeen te komen met de merk richtlijnen."*
- *"Maak deze lay-out strakker - pas spacing en typografie aan."*

</div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>webforj-upgrading-versions</code></strong>: upgrades over webforJ-hoofdfuncties met OpenRewrite
  </AccordionSummary>
  <AccordionDetails>
    <div>

Gebruik dit voor upgrades van belangrijke versies. De assistent voert het officiële `webforj-rewrite` OpenRewrite-recept uit voor de doelversie, dat `<webforj.version>` en de Java-release verhoogt, hernoemde API's en types herschrijft, en voegt `TODO webforJ <major>:` opmerkingen in bij elke verwijderde methode die een handmatige beslissing vereist. Voor oudere doelen zonder gepubliceerd recept (bijvoorbeeld van 24 naar 25) begeleidt het je door de handmatige terugval.

**Wanneer het actief is**

- *"Upgrade deze app van webforJ 25 naar 26."*
- *"Voer het herschrijfrecept uit en los de TODO's op."*
- *"Migreer handmatig van webforJ 24 naar 25 omdat er geen recept is."*
- *"Welke verwijderde API's moet ik corrigeren na de upgrade?"*

</div>
  </AccordionDetails>
</Accordion>

</AccordionGroup>
