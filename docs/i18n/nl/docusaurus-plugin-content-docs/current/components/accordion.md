---
sidebar_position: 1
title: Accordion
description: >-
  Group collapsible panels with the Accordion and AccordionPanel components to
  toggle visibility and coordinate expand or collapse behavior.
_i18n_hash: 6537c2d24bfc99dd866f27277ed4f197
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-accordion" />
<DocChip chip='since' label='25.12' />
<JavadocLink type="accordion" location="com/webforj/component/accordion/Accordion" top='true'/>

De `Accordion` component biedt een verticaal gestapelde set van uitklapbare panelen. Elk paneel heeft een klikbare kop die de zichtbaarheid van de inhoud ervan in- of uitschakelt. Een `AccordionPanel` kan worden gebruikt als een op zichzelf staande onthullingssectie, of gebundeld binnen een `Accordion` om het gedrag van uitbreiden en inkrimpen tussen meerdere panelen te coördineren.

<!-- INTRO_END -->

:::tip Wanneer een accordion te gebruiken
Accordions zijn goed voor FAQs, instellingenpagina's en stapsgewijze stromen waarbij het onthullen van alle inhoud tegelijk een overweldigende lay-out zou creëren. Als secties even belangrijk zijn en gebruikers profiteren van het tegelijkertijd zien ervan, overweeg dan in plaats daarvan [tabbladen](/docs/components/tabbedpane).
:::

## `AccordionPanel` <DocChip chip='name' label="dwc-accordion-panel" /> {#accordion-panel}

`AccordionPanel` is de kernbouwsteen van het accordion-systeem. Geef een labelstring door aan de constructor om de koptekst in te stellen, en voeg vervolgens kindercomponenten toe om de inhoud te vullen. Een paneel werkt op zichzelf zonder een omringende `Accordion` groep, waardoor het een nuttige lichte onthullingswidget is wanneer je gewoon een enkele uitklapbare sectie nodig hebt. De constructor zonder argumenten is ook beschikbaar als je de panel volledig na constructie wilt configureren.

```java
// Alleen label - voeg body-inhoud apart toe
AccordionPanel panel = new AccordionPanel("Sectietitel");
panel.add(new Paragraph("De inhoud gaat hier."));

// Label en body-inhoud direct doorgegeven in de constructor
AccordionPanel panel = new AccordionPanel("Titel", new Paragraph("Body-inhoud."));
```

<!-- vale off -->
<ComponentDemo
path='/webforj/accordionbasic'
files={['src/main/java/com/webforj/samples/views/accordion/AccordionBasicView.java']}
height='550px'
/>
<!-- vale on -->

### Openen en sluiten {#opening-and-closing}

Beheer de open/sluitstatus programmatisch op elk moment. `isOpened()` is handig wanneer je de huidige status moet lezen voordat je beslist wat je moet doen. Je kunt bijvoorbeeld een paneel omdraaien naar de tegenovergestelde staat of conditioneel andere delen van de UI tonen of verbergen.

```java
// Vergroot het paneel
panel.open();

// Krimp het paneel
panel.close();

// Geeft true terug als het momenteel uitgebreid is
boolean isOpen = panel.isOpened();
```

Gebruik `setLabel()` om de tekst van de kop na constructie bij te werken. `setText()` is een alias voor dezelfde bewerking, zodat het label in sync kan worden gehouden met dynamische gegevens:

```java
panel.setLabel("Bijgewerkt Label");
```

## Accordion groepen {#accordion-groups}

Door meerdere `AccordionPanel` instanties in een `Accordion` te wikkelen, ontstaat er een gecoördineerde groep. Standaard gebruikt de groep **enkele modus**: het openen van één paneel krimpt automatisch alle andere, waardoor er slechts één sectie tegelijk zichtbaar blijft. Deze standaard is opzettelijk, het houdt de gebruiker gefocust op één stuk inhoud en voorkomt dat de pagina visueel overweldigend wordt wanneer panelen aanzienlijke body-inhoud hebben.

Panelen worden onafhankelijk geconstrueerd en aan de `Accordion` doorgegeven, zodat je elk paneel kunt configureren voordat je ze groepeert. Meerdere afzonderlijke `Accordion` instanties kunnen ook op dezelfde pagina bestaan - elke groep beheert zijn eigen status onafhankelijk, zodat het uitbreiden van een paneel in de ene groep geen effect heeft op een andere.

```java
AccordionPanel panel1 = new AccordionPanel("Wat is webforJ?");
AccordionPanel panel2 = new AccordionPanel("Hoe werken gegroepeerde panelen?");
AccordionPanel panel3 = new AccordionPanel("Kan ik meerdere groepen hebben?");

Accordion accordion = new Accordion(panel1, panel2, panel3);
```

<!-- vale off -->
<ComponentDemo
path='/webforj/accordiongroup'
files={['src/main/java/com/webforj/samples/views/accordion/AccordionGroupView.java']}
height='400px'
/>
<!-- vale on -->

### Meervoudige modus {#multiple-mode}

De meervoudige modus stelt een onbeperkt aantal panelen in staat tegelijkertijd geopend te blijven. Dit is nuttig wanneer gebruikers de inhoud van verschillende secties tegelijk moeten vergelijken, of wanneer elk paneel kort genoeg is dat het uitbreiden van meerdere tegelijk geen rommelige lay-out creëert.

```java
accordion.setMultiple(true);
```

Met de actieve meervoudige modus kunnen alle panelen in de groep tegelijk worden uitgebreid of ingeklapt met behulp van de bulkmethoden:

```java
// Breidt elk paneel in de groep uit
accordion.openAll();

// Krimpt elk paneel in de groep
accordion.closeAll();
```

<!-- vale off -->
<ComponentDemo
path='/webforj/accordionmultiple'
files={['src/main/java/com/webforj/samples/views/accordion/AccordionMultipleView.java']}
height='575px'
/>
<!-- vale on -->

:::info Beperking van enkele modus
`openAll()` is alleen beschikbaar wanneer de meervoudige modus is ingeschakeld. Het aanroepen ervan in enkele modus heeft geen effect. `closeAll()` werkt in beide modi.
:::

<!-- vale off -->
## Ingeschakelde status {#disabled-state}
<!-- vale on -->

Individuele panelen kunnen worden uitgeschakeld om gebruikersinteractie te voorkomen terwijl ze nog steeds zichtbaar zijn. Dit is handig tijdens laadstatussen of wanneer bepaalde secties conditioneel niet beschikbaar zijn, waarbij de panelstructuur wordt getoond zonder voortijdige interactie toe te staan. Een uitgeschakeld paneel dat al geopend was, blijft uitgebreid, maar de kop ervan kan niet meer worden aangeklikt om het te krimpen. Het uitschakelen van de `Accordion` groep past de uitgeschakelde status toe op alle daarin opgenomen panelen tegelijk, zodat je niet individueel door panelen hoeft te lopen.

```java
// Schakel een enkel paneel uit
panel.setEnabled(false);

// Schakel alle panelen in de groep uit
accordion.setEnabled(false);
```

<!-- vale off -->
<ComponentDemo
path='/webforj/accordiondisabled'
files={['src/main/java/com/webforj/samples/views/accordion/AccordionDisabledView.java']}
height='650px'
/>
<!-- vale on -->

## Panelen aanpassen {#customizing-panels}

Buiten labels en basis open/sluit gedrag ondersteunt elk `AccordionPanel` rijkere aanpassing van zowel de inhoud van de kop als het uitbreid-/inkrompictogram.

### Aangepaste kop {#custom-header}

De kop van een paneel rendert zijn label standaard als platte tekst. Gebruik `addToHeader()` om die tekst te vervangen door een component of combinatie van componenten, waardoor het eenvoudig is om pictogrammen, badges, statusindicatoren of andere rijke markup naast het paneellabel op te nemen. Dit is bijzonder nuttig in dashboards of instellingenpanelen waar elke sectiekop extra context moet overbrengen in één oogopslag, zoals een itemaantal, een waarschuwingbadge of een voltooiingsstatus, zonder dat de gebruiker eerst het paneel hoeft uit te vouwen.

```java
FlexLayout headerContent = FlexLayout.create()
    .horizontal()
    .build()
    .setSpacing("var(--dwc-space-s)");

headerContent.add(FeatherIcon.SETTINGS.create(), new Span("Aangepaste Kop met Pictogram"));
panel.addToHeader(headerContent);
```

:::info Vervanging van etiket
Inhoud die via `addToHeader()` is toegevoegd, vervangt volledig de standaard labeltekst. `setLabel()` en `setText()` blijven ook werken naast `addToHeader()`, maar omdat de kopslot visuele prioriteit heeft, wordt de labeltekst niet weergegeven tenzij je deze expliciet in je ingesloten inhoud opneemt.
:::

<!-- vale off -->
<ComponentDemo
path='/webforj/accordioncustomheader'
files={['src/main/java/com/webforj/samples/views/accordion/AccordionCustomHeaderView.java']}
height='300px'
/>
<!-- vale on -->

### Aangepast pictogram {#custom-icon}

De uitbreid-/inkrompindicator is standaard zichtbaar in zowel de open als gesloten staten. `setIcon()` vervangt dit door een [`Icon`](/docs/components/icon) component, nuttig voor merkiconografie of wanneer een andere visuele metafoor beter bij de inhoud past. Het doorgeven van `null` herstelt de standaard chevron. `getIcon()` retourneert het momenteel ingestelde pictogram of `null` als de standaard chevron wordt gebruikt.

```java
// Vervang de standaard chevron door een pluspictogram
panel.setIcon(FeatherIcon.PLUS.create());

// Herstel de standaard chevron
panel.setIcon(null);
```

<!-- vale off -->
<ComponentDemo
path='/webforj/accordioncustomicon'
files={['src/main/java/com/webforj/samples/views/accordion/AccordionCustomIconView.java']}
height='200px'
/>
<!-- vale on -->

## Geneste accordions {#nested-accordions}

Accordions kunnen binnen andere accordionpanelen worden genest, wat nuttig is voor het representeren van hiërarchische inhoud zoals gecategoriseerde instellingen of navigatie op meerdere niveaus. Voeg een interne `Accordion` toe aan een externe `AccordionPanel` als elke andere kindercomponent. Houd de nesting ondiep. Eén of twee niveaus is meestal voldoende. Diepere hiërarchieën zijn vaak moeilijker te navigeren en signaleren vaak dat de inhoudsstructuur zelf moet worden herzien.

```java
AccordionPanel innerA = new AccordionPanel("Inner Paneel A");
AccordionPanel innerB = new AccordionPanel("Inner Paneel B");
Accordion innerAccordion = new Accordion(innerA, innerB);

AccordionPanel outer = new AccordionPanel("Buiten Paneel");
outer.add(innerAccordion);
```

<!-- vale off -->
<ComponentDemo
path='/webforj/accordionnested'
files={['src/main/java/com/webforj/samples/views/accordion/AccordionNestedView.java']}
height='550px'
/>
<!-- vale on -->

## Evenementen {#events}

`AccordionPanel` genereert evenementen in elke fase van de toggle-lifecycle. De drie evenementtypes dekken verschillende momenten, dus kies op basis van wanneer jouw logica moet worden uitgevoerd:

| Evenement | Vindt plaats |
|-----------|--------------|
| `AccordionPanelToggleEvent` | Voordat de status verandert |
| `AccordionPanelOpenEvent` | Nadat het paneel volledig is geopend |
| `AccordionPanelCloseEvent` | Nadat het paneel volledig is gesloten |

```java
panel.onToggle(e -> {
    // Vindt plaats voordat het paneel van status verandert.
    // e.isOpened() weerspiegelt de status waar naartoe wordt overgeschakeld, niet de huidige status.
    String direction = e.isOpened() ? "opening" : "closing";
});

panel.onOpen(e -> {
    // Vindt plaats nadat het paneel volledig is geopend — goed voor lazy-loading inhoud.
});

panel.onClose(e -> {
    // Vindt plaats nadat het paneel volledig is gesloten — goed voor opruimen of samenvatting updates.
});
```

## Stijlen {#styling}

<TableBuilder name={['Accordion', 'AccordionPanel']} />
