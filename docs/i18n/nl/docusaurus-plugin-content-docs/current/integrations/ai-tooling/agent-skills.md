---
title: Agent Skills
sidebar_position: 10
_i18n_hash: 0458a29cc4337ff83f08afb415097a1c
---
Agent Skills leren AI-coderingsassistenten hoe ze webforJ-toepassingen kunnen bouwen met de juiste API's, ontwerptokens en componentpatronen. In plaats van te gokken bij raamwerkconventies, laadt de assistent een vaardigheid en volgt een gestructureerde workflow om code te produceren die compileert en de beste praktijken volgt bij de eerste poging.

:::tip Gebruik de plugin
De onderstaande vaardigheden worden geleverd binnen de **[webforJ AI-plugin](/docs/integrations/ai-tooling)** samen met de [MCP-server](/docs/integrations/ai-tooling/mcp). Eén installatie geeft je assistent beide onderdelen.
:::

Vaardigheden volgen de open [Agent Skills](https://agentskills.io/specification) standaard en werken met veel AI-assistenten, waaronder Claude Code, GitHub Copilot, Cursor, Gemini CLI, OpenAI Codex en meer. Een vaardigheid vertelt de assistent welk soort taak het behandelt; de assistent laadt het automatisch wanneer je prompt overeenkomt. Bijvoorbeeld, het vragen om "deze app te stylen met een blauwe palet" activeert de `webforj-styling-apps` vaardigheid, die de assistent begeleidt bij het opzoeken van geldige DWC-tokens, het schrijven van scoped CSS en het valideren van elke variabele naam voordat er iets naar schijf wordt geschreven.

## Waarom vaardigheden gebruiken? {#why-use-skills}

De MCP-server maakt nauwkeurige webforJ-informatie op aanvraag beschikbaar, maar op zich vertelt het de assistent _wanneer_ hij iets moet opzoeken, _welke_ benadering past bij de taak, of _in welke volgorde_ dingen moeten worden gedaan. Dat is waar vaardigheden om de hoek komen kijken.

Vaardigheden geven de assistent een taak-specifieke handleiding: hoe het werk dat voor hem ligt te classificeren, welke webforJ-patronen passen, welke MCP-tools in elke stap te raadplegen, en hoe de output te valideren voordat deze wordt teruggegeven. Het resultaat is consistente, conventievriendelijke webforJ-code in plaats van een verzameling technisch geldige maar stijlmatig mismatched snippets.

## Hoe vaardigheden verschillen van MCP {#how-skills-differ-from-mcp}

Vaardigheden en de [webforJ MCP-server](/docs/integrations/ai-tooling/mcp) vervullen complementaire rollen. De MCP-server biedt live tools die de assistent kan oproepen om informatie op te halen of output te genereren. Vaardigheden bieden de workflow die de assistent vertelt _wanneer_ hij naar die tools moet reiken, in welke volgorde dingen moeten worden gedaan, en hoe het resultaat te valideren.

| | MCP-server | Agent Skills |
|---|---|---|
| **Wat het biedt** | Tools die de assistent op aanvraag oproept (documentzoektocht, scaffolding, themageneratie, tokenvalidatie) | Workflows en beslis-tabellen die de assistent begeleiden hoe een taak aan te pakken |
| **Wanneer het handelt** | Wanneer de assistent besluit een tool aan te roepen | Automatisch, wanneer de assistent een bijpassende taak detecteert |
| **Het beste voor** | Het beantwoorden van specifieke vragen, artefacten genereren | Eind-tot-eind-taken die een consistente webforJ-aanpak vereisen |

In de praktijk werken de twee het beste samen - en de [webforJ AI-plugin](https://github.com/webforj/webforj-ai) wordt geleverd als één installatie.

## Installatie {#installation}

Installeer de **[webforJ AI-plugin](/docs/integrations/ai-tooling)** - het bevat beide onderstaande vaardigheden naast de MCP-server. Voor clients die geen plugins ondersteunen, biedt de [webforJ AI-repository](https://github.com/webforj/webforj-ai#clients) de skill-directory die elke tool leest, zodat je de skill-mappen handmatig kunt kopiëren.

## Beschikbare vaardigheden {#available-skills}

<AccordionGroup>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>webforj-adding-servlets</code></strong>: voeg REST-eindpunten, webhooks en aangepaste servlets toe
  </AccordionSummary>
  <AccordionDetails>
    <div>

Gebruik dit wanneer je een non-UI HTTP-pad nodig hebt - een REST-eindpunt, een webhook-handler of een derde partij servlet zoals Swagger UI of Spring Web. De assistent kiest de juiste aanpak voor jouw project (Spring `webforj.exclude-urls`, het opnieuw toewijzen van `WebforjServlet` naar een sub-pad, of proxying via `webforj.conf`) en sluit het eindpunt aan zonder de UI-routering van webforJ te verstoren.

**Wanneer het op gang komt**

- *"Voeg een REST-eindpunt toe op `/api/orders`."*
- *"Verbind een webhook-handler voor Stripe."*
- *"Bevestig Swagger UI op `/api/docs`."*
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

Gebruik dit voor al het formulierwerk in een webforJ-app: gegevensinvoervormen, tweezijdige binding aan een Java-bean, Jakarta-validatie, gemaskeerde invoercomponenten (telefoon, valuta, IBAN, datums), tabelkolommen opmaken als valuta of percentage, en responsieve multi-kolom lay-outs. De assistent routeert door `BindingContext`, de `Masked*Field` componenten, de Tabelmasker-renderers, en `ColumnsLayout`.

**Wanneer het op gang komt**

- *"Bouw een registratieformulier dat is gebonden aan mijn `User` bean."*
- *"Voeg een telefoonnummerinvoer toe met een format-naar-type."*
- *"Formatteer deze tabelkolom als valuta."*
- *"Valideer dit veld met `@NotEmpty` en een aangepaste e-mailchecker."*

</div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>webforj-creating-components</code></strong>: omhul webcomponenten, JS-bibliotheken of composities
  </AccordionSummary>
  <AccordionDetails>
    <div>

Gebruik dit wanneer je een herbruikbare Java-component nodig hebt die om elk bron is gewikkeld - een bestaande Custom Element-bibliotheek, een gewone JavaScript-bibliotheek, of een samenstelling van bestaande webforJ-componenten. De assistent kiest de juiste webforJ-basis klasse voor de taak, verbindt eigenschappen, gebeurtenissen en slots met de juiste patronen, en produceert tests die de webforJ-conventies volgen.

**Wanneer het op gang komt**

- *"Wikkel deze Custom Element-bibliotheek als webforJ-componenten."*
- *"Samenstellen deze webforJ-componenten in een herbruikbare kaart."*
- *"Integreer deze gewone JavaScript-bibliotheek als een webforJ-component."*
- *"Expose deze Browser API als een webforJ-hulpmiddel."*

</div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>webforj-handling-timers-and-async</code></strong>: plan timers, debouncers en asynchrone taken
  </AccordionSummary>
  <AccordionDetails>
    <div>

Gebruik dit voor periodieke taken, polling, debounced zoeken- terwijl je typt, throttling, en langlopende achtergrondtaken die de UI bijwerken terwijl ze draaien. De assistent kiest de juiste primitive (`Interval`, `Debouncer`, `Environment.runLater`, `PendingResult`) en vermijdt de runtime vallen van rauwe `java.util.Timer`, `javax.swing.Timer`, of threads die buiten de webforJ-omgeving zijn gemaakt, die allemaal `IllegalStateException` gooien op het moment dat zij een UI-component aanraken.

**Wanneer het op gang komt**

- *"Vernieuw dit dashboard elke 30 seconden."*
- *"Voeg een zoek-en-typ debouncer toe."*
- *"Voer dit rekenkracht intensieve werk op de achtergrond uit en werk de voortgangsbalk bij."*
- *"Polling dit REST-eindpunt totdat het `done` retourneert."*

</div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>webforj-localizing-apps</code></strong>: voeg i18n en vertaaldiensten toe
  </AccordionSummary>
  <AccordionDetails>
    <div>

Gebruik dit voor al het internationalisatie werk: het laden van berichtbundels, het wisselen van talen tijdens runtime, het automatisch detecteren van de browser-lokale van de gebruiker, en het vertalen van componentlabels. De assistent routeert door webforJ 25.12's `BundleTranslationResolver`, de `HasTranslation` bezorgdheid, `LocaleObserver`, en pluggable aangepaste resolvers, en dekt zowel Spring en plain webforJ-paden.

**Wanneer het op gang komt**

- *"Voeg ondersteuning voor meerdere talen toe met Engels en Spaans."*
- *"Detecteer de browser-lokale van de gebruiker en pas deze toe bij opstarten."*
- *"Voeg een taalwisselaar toe aan de navigatiebalk."*
- *"Verplaats alle hard-coded strings naar een berichtenbundel."*

</div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>webforj-securing-apps</code></strong>: bescherm routes met inloggen en rol-gebaseerde toegang
  </AccordionSummary>
  <AccordionDetails>
    <div>

Gebruik dit voor alles wat routes in een webforJ-app beschermt: inloggen en uitloggen, rol-gebaseerde toegang, openbare landingspagina's, secties alleen voor beheerders, eigendomsregels, en beveiliging-per-default-beleid. De assistent geeft de voorkeur aan Spring Security wanneer Spring Boot op het classpath staat en valt terug op de plain security-structuur van webforJ. Het past de juiste annotaties toe (`@AnonymousAccess`, `@PermitAll`, `@RolesAllowed`, `@RouteAccess`, `@RegisteredEvaluator`) en legt uit welke terminal zijn versus samenstel-baar zodat beveiliging-per-default nog steeds doet wat het belooft.

**Wanneer het op gang komt**

- *"Bescherm `/admin` zodat alleen gebruikers met de rol `ADMIN` het kunnen zien."*
- *"Voeg een openbare landingspagina toe die iedereen kan bezoeken."*
- *"Toon de naam van de ingelogde gebruiker in de kop."*
- *"Laat alleen een gebruiker een record bewerken dat zij bezitten."*

</div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>webforj-styling-apps</code></strong>: style apps met DWC-ontwerptokens
  </AccordionSummary>
  <AccordionDetails>
    <div>

Gebruik dit voor al het visuele werk in een webforJ-app: palet-wederopbouwen, styling op componentniveau, lay-out en ruimte, typografie, volledige thema's, tabel-uitstraling, of gecoördineerde Google Charts-kleuren. De assistent schrijft CSS die zich houdt aan DWC-ontwerptokens, scopt selectors correct, en valideert elke `--dwc-*` referentie tegen de echte catalogus voor jouw webforJ-versie - zodat de donkere modus en themaswitching blijven werken.

**Wanneer het op gang komt**

- *"Style deze app met een blauwe palet."*
- *"Style de dwc-button om overeen te komen met de merkrichtlijnen."*
- *"Maak deze lay-out strakker - pas de ruimte en typografie aan."*

</div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>webforj-upgrading-versions</code></strong>: upgrade tussen webforJ-hoofdelingen met OpenRewrite
  </AccordionSummary>
  <AccordionDetails>
    <div>

Gebruik dit voor upgrades naar nieuwe hoofdversies. De assistent voert het officiële `webforj-rewrite` OpenRewrite-recept uit voor de doelsversie, wat `<webforj.version>` en de Java-release verhoogt, hernoemde API's en types herschrijft, en voegt `TODO webforJ <major>:` opmerkingen toe bij elke verwijderde methode die een handmatige beslissing vereist. Voor oudere doelen zonder gepubliceerd recept (bijvoorbeeld van 24 naar 25), begeleidt het je door de handmatige terugval.

**Wanneer het op gang komt**

- *"Upgrade deze app van webforJ 25 naar 26."*
- *"Voer het herschrijf-recept uit en los de TODO's op."*
- *"Migreer handmatig van webforJ 24 naar 25 omdat er geen recept is."*
- *"Welke verwijderde API's moet ik repareren na de upgrade?"*

</div>
  </AccordionDetails>
</Accordion>

</AccordionGroup>
