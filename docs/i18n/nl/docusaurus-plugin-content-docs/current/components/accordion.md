---
sidebar_position: 1
title: Accordion
description: >-
  Group collapsible panels with the Accordion and AccordionPanel components to
  toggle visibility and coordinate expand or collapse behavior.
_i18n_hash: b11e2d2ef8854f757454635c984da1d6
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-accordion" />
<DocChip chip='name' label="dwc-accordion-panel" />
<DocChip chip='since' label='25.12' />
<JavadocLink type="accordion" location="com/webforj/component/accordion/Accordion" top='true'/>

De `Accordion` component biedt een verticaal gestapeld stel uitklapbare panelen. Elk paneel heeft een aanklikbare koptekst die de zichtbaarheid van de hoofdinhoud toggelt. Een `AccordionPanel` kan worden gebruikt als een op zichzelf staande onthullingssectie, of gegroepeerd binnen een `Accordion` om het uitbreiden en inkrimpen van meerdere panelen te coördineren.

<!-- INTRO_END -->

:::tip Wanneer een accordion te gebruiken
Accordions werken goed voor FAQ's, instellingenpagina's en stapsgewijze workflows waarbij het onthullen van alle inhoud tegelijk een overweldigende lay-out zou creëren. Als secties even belangrijk zijn en gebruikers profiteren van het gelijktijdig zien ervan, overweeg dan [tabs](/docs/components/tabbedpane) in plaats daarvan.
:::

## `AccordionPanel` {#accordion-panel}

`AccordionPanel` is de kernbouwsteen van het accordion-systeem. Geef een labelstring door aan de constructor om de koptekst in te stellen, en voeg vervolgens kindcomponenten toe om de inhoud te vullen. Een paneel werkt op zichzelf zonder enige omringende `Accordion` groep, wat het een nuttige lichte onthullingswidget maakt wanneer je slechts één uitklapbare sectie nodig hebt. De constructor zonder argumenten is ook beschikbaar wanneer je de panel helemaal na de constructie wilt configureren.

```java
// Alleen label - voeg hoofdinhoud afzonderlijk toe
AccordionPanel panel = new AccordionPanel("Sectietitel");
panel.add(new Paragraph("Hoofdinhoud gaat hier."));

// Label en hoofdinhoud worden rechtstreeks in de constructor doorgegeven
AccordionPanel panel = new AccordionPanel("Titel", new Paragraph("Hoofdinhoud."));
```

<!-- vale off -->
<ComponentDemo
path='/webforj/accordionbasic'
files={['src/main/java/com/webforj/samples/views/accordion/AccordionBasicView.java']}
height='550px'
/>
<!-- vale on -->

### Openen en sluiten {#opening-and-closing}

Controleer de open/gesloten status programmatisch op elk moment. `isOpened()` is nuttig wanneer je de huidige status wilt lezen voordat je beslist wat te doen. Je kunt bijvoorbeeld een paneel naar de tegenovergestelde staat toggelen of voorwaardelijk andere delen van de UI tonen of verbergen.

```java
// Breid het paneel uit
panel.open();

// Klap het paneel in
panel.close();

// Geeft waar terug als het momenteel is uitgebreid
boolean isOpen = panel.isOpened();
```

Gebruik `setLabel()` om de koptekst bij te werken na de constructie. `setText()` is een alias voor dezelfde operatie, zodat het label kan worden gesynchroniseerd met dynamische gegevens:

```java
panel.setLabel("Bijgewerkt Label");
```

## Accordion groepen {#accordion-groups}

Meerdere `AccordionPanel` instanties binnen een `Accordion` creëren een gecoördineerde groep. Standaard gebruikt de groep **enkelvoudige modus**: het openen van één paneel vouwt automatisch alle andere in, waardoor telkens maar één sectie zichtbaar is. Deze standaardinstelling is opzettelijk, het houdt de gebruiker gefocust op één stuk inhoud en voorkomt dat de pagina visueel overweldigend wordt wanneer panelen aanzienlijke hoofdinhoud hebben.

Panelen worden onafhankelijk geconstrueerd en aan de `Accordion` doorgegeven, zodat je elk afzonderlijk kunt configureren voordat je ze groepeert. Meerdere aparte `Accordion` instanties kunnen ook op dezelfde pagina bestaan—elke groep beheert zijn eigen status onafhankelijk, zodat het uitbreiden van een paneel in de ene groep geen effect heeft op een andere.

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

Meervoudige modus staat toe dat een onbeperkt aantal panelen gelijktijdig uitgebreid blijft. Dit is nuttig wanneer gebruikers de inhoud van verschillende secties tegelijk moeten vergelijken, of wanneer elk paneel kort genoeg is zodat het uitbreiden van meerdere tegelijk geen rommelige lay-out creëert.

```java
accordion.setMultiple(true);
```

Met actieve meervoudige modus kunnen alle panelen in de groep tegelijk worden uitgebreid of ingeklapt met behulp van de bulkmethoden:

```java
// Breid elk paneel in de groep uit
accordion.openAll();

// Klap elk paneel in de groep in
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
`openAll()` is alleen beschikbaar wanneer de meervoudige modus is ingeschakeld. Het aanroepen ervan in de enkelvoudige modus heeft geen effect. `closeAll()` werkt in beide modi.
:::

<!-- vale off -->
## Uitgeschakelde staat {#disabled-state}
<!-- vale on -->

Individuele panelen kunnen worden uitgeschakeld om interactie van de gebruiker te voorkomen, terwijl ze nog steeds zichtbaar blijven. Dit is handig tijdens laadtoestanden of wanneer bepaalde secties voorwaardelijk niet beschikbaar zijn, waardoor de panelstructuur wordt getoond zonder voortijdige interactie toe te staan. Een uitgeschakeld paneel dat al open was, blijft uitgevouwen, maar de koptekst kan niet langer worden aangeklikt om het in te klappen. Het uitschakelen van de `Accordion` groep past de uitgeschakelde status toe op alle aanwezige panelen tegelijk, zodat je niet individuele panelen hoeft te doorlopen.

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

## Het aanpassen van panelen {#customizing-panels}

Naast labels en basis open/sluit gedrag, ondersteunt elk `AccordionPanel` rijkere aanpassing van zowel de koptekstinhoud als het expanderen/inkrimpen pictogram.

### Aangepaste koptekst {#custom-header}

De koptekst van een paneel rendert zijn label standaard als platte tekst. Gebruik `addToHeader()` om die tekst te vervangen door een component of combinatie van componenten, waardoor het eenvoudig is om pictogrammen, badges, statusindicatoren of andere rijke markup naast het panellabel op te nemen. Dit is vooral nuttig in dashboards of instellingenpanelen, waar elke sectiekop extra context moet bieden in één oogopslag, zoals een itemaantal, een waarschuwing badge of een voltooiingsstatus, zonder dat de gebruiker het paneel eerst hoeft uit te klappen.

```java
FlexLayout headerContent = FlexLayout.create()
    .horizontal()
    .build()
    .setSpacing("var(--dwc-space-s)");

headerContent.add(FeatherIcon.SETTINGS.create(), new Span("Aangepaste Koptekst met Pictogram"));
panel.addToHeader(headerContent);
```

:::info Vervangen van label
Inhoud die via `addToHeader()` wordt toegevoegd, vervangt volledig de standaard labeltekst. `setLabel()` en `setText()` blijven naast `addToHeader()` werken, maar aangezien de koptekstslot visuele prioriteit heeft, wordt de labeltekst niet weergegeven tenzij je deze expliciet in je ingesloten inhoud opneemt.
:::

<!-- vale off -->
<ComponentDemo
path='/webforj/accordioncustomheader'
files={['src/main/java/com/webforj/samples/views/accordion/AccordionCustomHeaderView.java']}
height='300px'
/>
<!-- vale on -->

### Aangepast pictogram {#custom-icon}

De indicator voor uitbreiden/inkrimpen is standaard een chevron die zichtbaar is in zowel de open als gesloten status. `setIcon()` vervangt het door elk [`Icon`](/docs/components/icon) component, handig voor merkpictogrammen of wanneer een andere visuele metafoor beter bij de inhoud past. Het doorgeven van `null` herstelt de standaard chevron. `getIcon()` retourneert het momenteel ingestelde pictogram, of `null` als de standaard chevron in gebruik is.

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

Accordions kunnen worden genest binnen andere accordion panelen, wat nuttig is voor het weergeven van hiërarchische inhoud, zoals gecategoriseerde instellingen of meervoudige navigatie. Voeg een innerlijke `Accordion` toe aan een uiterlijke `AccordionPanel` als elke andere kindcomponent. Houd het nestelen ondiep. Een of twee niveaus zijn meestal voldoende. Diepere hiërarchieën zijn vaak moeilijker te navigeren en geven vaak aan dat de inhoudsstructuur zelf moet worden heroverwogen.

```java
AccordionPanel innerA = new AccordionPanel("Inner Paneel A");
AccordionPanel innerB = new AccordionPanel("Inner Paneel B");
Accordion innerAccordion = new Accordion(innerA, innerB);

AccordionPanel outer = new AccordionPanel("Uiterlijk Paneel");
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

`AccordionPanel` genereert evenementen in elke fase van de toggle levenscyclus. De drie evenementtypen dekken verschillende momenten, kies dus op basis van wanneer jouw logica moet worden uitgevoerd:

| Evenement | Vindt plaats |
|-----------|--------------|
| `AccordionPanelToggleEvent` | Voordat de status verandert |
| `AccordionPanelOpenEvent` | Nadat het paneel volledig is geopend |
| `AccordionPanelCloseEvent` | Nadat het paneel volledig is gesloten |

```java
panel.onToggle(e -> {
    // Vindt plaats voordat het paneel van status verandert.
    // e.isOpened() weerspiegelt de status waaraan wordt overgeschakeld, niet de huidige status.
    String direction = e.isOpened() ? "opening" : "closing";
});

panel.onOpen(e -> {
    // Vindt plaats nadat het paneel volledig open is — goed voor het laadt content.
});

panel.onClose(e -> {
    // Vindt plaats nadat het paneel volledig is gesloten — goed voor opruimen of samenvattingsupdates.
});
```

## Styling {#styling}

<TableBuilder name={['Accordion', 'AccordionPanel']} />
