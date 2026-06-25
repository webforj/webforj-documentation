---
sidebar_position: 1
title: Accordion
_i18n_hash: 207c70347cc18d88661a8a9279988417
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-accordion" />
<DocChip chip='name' label="dwc-accordion-panel" />
<DocChip chip='since' label='25.12' />
<JavadocLink type="accordion" location="com/webforj/component/accordion/Accordion" top='true'/>

De `Accordion`-component biedt een verticaal gestapeld set van samenvouwbare panelen. Elk paneel heeft een klikbare kop die de zichtbaarheid van de inhoud van het lichaam om schakelt. Een `AccordionPanel` kan worden gebruikt als een zelfstandige openbaarmakingssectie, of gegroepeerd binnen een `Accordion` om het uitbreiden en samentrekken van meerdere panelen te coördineren.

<!-- INTRO_END -->

:::tip Wanneer een accordion te gebruiken
Accordions zijn goed geschikt voor FAQ's, instellingenpagina's en stapsgewijze flows waarbij het onthullen van alle inhoud tegelijk een overweldigende lay-out zou creëren. Als secties even belangrijk zijn en gebruikers profiteren van het gelijktijdig zien ervan, overweeg dan in plaats daarvan [tabbladen](/docs/components/tabbedpane).
:::

## `AccordionPanel` {#accordion-panel}

`AccordionPanel` is het kernbouwsteen van het accordion-systeem. Geef een labelstring door aan de constructor om de koptekst in te stellen, en voeg vervolgens kindcomponenten toe om de inhoud van de body te vullen. Een paneel werkt op zichzelf zonder enige omringende `Accordion`-groep, waardoor het een nuttige lichte openbaarmakingswidget is wanneer je alleen een enkele samenvouwbare sectie nodig hebt. De no-argument constructor is ook beschikbaar wanneer je de voorkeur geeft aan het volledig configureren van het paneel na constructie.

```java
// Alleen label - voeg inhoud van de body afzonderlijk toe
AccordionPanel panel = new AccordionPanel("Sectietitel");
panel.add(new Paragraph("Body-inhoud komt hier."));

// Label en inhoud van de body direct doorgegeven in de constructor
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

Bestuur de open/sluit status programmatisch op elk moment. `isOpened()` is handig wanneer je de huidige status moet lezen voordat je beslist wat te doen. Bijvoorbeeld, je kunt een paneel om schakelen naar de tegenovergestelde status of conditioneel andere delen van de UI tonen of verbergen.

```java
// Breid het paneel uit
panel.open();

// Verkleur het paneel
panel.close();                    

// Geeft waar terug als het momenteel uitgebreid is
boolean isOpen = panel.isOpened();
```

Gebruik `setLabel()` om de koptekst bij te werken na constructie. `setText()` is een alias voor dezelfde bewerking, zodat het label synchroon kan worden gehouden met dynamische gegevens:

```java
panel.setLabel("Bijgewerkt Label");
```

## Accordion groepen {#accordion-groups}

Het inpassen van meerdere `AccordionPanel`-instanties binnen een `Accordion` creëert een gecoördineerde groep. Standaard gebruikt de groep **single mode**: het openen van één paneel verkleint automatisch alle andere, waardoor slechts één sectie tegelijkertijd zichtbaar blijft. Deze standaard is opzettelijk, het houdt de gebruiker gefocust op één stuk inhoud en voorkomt dat de pagina visueel overweldigend wordt wanneer panelen aanzienlijke inhoud bevatten.

Panelen worden onafhankelijk geconstrueerd en aan de `Accordion` doorgegeven, zodat je elk afzonderlijk kunt configureren voordat je ze groepeert. Meerdere afzonderlijke `Accordion`-instanties kunnen ook op dezelfde pagina bestaan—elk groep beheert zijn eigen status onafhankelijk, zodat het uitbreiden van een paneel in de ene groep geen effect heeft op een andere.

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

### Meerdere modus {#multiple-mode}

Meerdere modus stelt een onbeperkt aantal panelen in staat om gelijktijdig uitgebreid te blijven. Dit is nuttig wanneer gebruikers de inhoud van verschillende secties tegelijk moeten vergelijken, of wanneer elk paneel kort genoeg is dat het gelijktijdig uitbreiden van meerdere geen rommelige lay-out creëert.

```java
accordion.setMultiple(true);
```

Met actieve meerdere modus kunnen alle panelen in de groep tegelijk worden uitgebreid of verkleind met behulp van de bulkmethoden:

```java
// Breid elk paneel in de groep uit
accordion.openAll();

// Verkleur elk paneel in de groep
accordion.closeAll();   
```

<!-- vale off -->
<ComponentDemo
path='/webforj/accordionmultiple'
files={['src/main/java/com/webforj/samples/views/accordion/AccordionMultipleView.java']}
height='575px'
/>
<!-- vale on -->

:::info Beperkingen van enkele modus
`openAll()` is alleen beschikbaar wanneer de meerdere modus is ingeschakeld. Het aanroepen in de enkele modus heeft geen effect. `closeAll()` werkt in beide modi.
:::

<!-- vale off -->
## Gehandicapte staat {#disabled-state}
<!-- vale on -->

Individuele panelen kunnen worden uitgeschakeld om interactie van de gebruiker te voorkomen terwijl ze nog steeds zichtbaar blijven. Dit is handig tijdens laadstatussen of wanneer bepaalde secties voorwaardelijk niet beschikbaar zijn, waardoor de panelstructuur wordt weergegeven zonder voortijdige interactie toe te staan. Een uitgeschakeld paneel dat al open was blijft uitgebreid, maar de kop betekent kan niet meer worden aangeklikt om het te verkleinen. Het uitschakelen van de `Accordion`-groep past de uitgeschakelde status toe op alle bijbehorende panelen tegelijk, zodat je niet door panelen individueel hoeft te lopen.

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

Naast labels en basis open/sluit gedrag, ondersteunt elk `AccordionPanel` rijkere aanpassing van zowel zijn kopinhoud als het uitbreid/sluit pictogram.

### Aangepaste kop {#custom-header}

De kop van een paneel geeft standaard de tekst van het label weer als platte tekst. Gebruik `addToHeader()` om die tekst te vervangen door een component of combinatie van componenten, wat het eenvoudig maakt om pictogrammen, badges, statusindicatoren of andere rijke markup naast het paneellabel op te nemen. Dit is bijzonder nuttig in dashboards of instellingenpanelen waar elke sectorkop extra context moet overbrengen in één oogopslag, zoals een item telling, een waarschuwing badge, of een voltooiing status, zonder dat de gebruiker het paneel eerst hoeft uit te vouwen.

```java
FlexLayout headerContent = FlexLayout.create()
    .horizontal()
    .build()
    .setSpacing("var(--dwc-space-s)");

headerContent.add(FeatherIcon.SETTINGS.create(), new Span("Aangepaste kop met pictogram"));
panel.addToHeader(headerContent);
```

:::info Vervanging van het label
Inhoud die via `addToHeader()` wordt toegevoegd, vervangt volledig de standaard labeltekst. `setLabel()` en `setText()` blijven werken naast `addToHeader()`, maar aangezien de kop-slot visuele prioriteit heeft, wordt de labeltekst niet weergegeven, tenzij je deze expliciet in je ingesloten inhoud opneemt.
:::

<!-- vale off -->
<ComponentDemo
path='/webforj/accordioncustomheader'
files={['src/main/java/com/webforj/samples/views/accordion/AccordionCustomHeaderView.java']}
height='300px'
/>
<!-- vale on -->

### Aangepast pictogram {#custom-icon}

De uitbreid/sluit indicator geeft standaard een chevron weer dat zichtbaar is in zowel de open als gesloten staten. `setIcon()` vervangt het met elk [`Icon`](/docs/components/icon) component, nuttig voor merkiconografie of wanneer een andere visuele metafoor beter past bij de inhoud. Het doorgeven van `null` herstelt de standaard chevron. `getIcon()` retourneert het momenteel ingestelde pictogram, of `null` als de standaard chevron in gebruik is.

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

Accordions kunnen worden genest binnen andere accordion-panelen, wat nuttig is voor het vertegenwoordigen van hiërarchische inhoud, zoals gecategoriseerde instellingen of navigatie op meerdere niveaus. Voeg een innerlijke `Accordion` toe aan een uiterlijke `AccordionPanel` als elke andere kindcomponent. Houd de nesting ondiep. Eén of twee niveaus is meestal genoeg. Diepere hiërarchieën zijn vaak moeilijker te navigeren en wijzen vaak op dat de inhoudstructuur zelf opnieuw moet worden doordacht.

```java
AccordionPanel innerA = new AccordionPanel("Inner Paneel A");
AccordionPanel innerB = new AccordionPanel("Inner Paneel B");
Accordion innerAccordion = new Accordion(innerA, innerB);

AccordionPanel outer = new AccordionPanel("Outer Paneel");
outer.add(innerAccordion);
```

<!-- vale off -->
<ComponentDemo
path='/webforj/accordionnested'
files={['src/main/java/com/webforj/samples/views/accordion/AccordionNestedView.java']}
height='550px'
/>
<!-- vale on -->

## Gebeurtenissen {#events}

`AccordionPanel` genereert gebeurtenissen in elke fase van de toggle-leven. De drie gebeurtenistypes dekken verschillende momenten, dus kies op basis van wanneer je logica moet draaien:

| Gebeurtenis | Vindt plaats |
|-------|-------|
| `AccordionPanelToggleEvent` | Voordat de staat verandert |
| `AccordionPanelOpenEvent` | Nadat het paneel volledig is geopend |
| `AccordionPanelCloseEvent` | Nadat het paneel volledig is gesloten |

```java
panel.onToggle(e -> {
    // Vindt plaats voordat het paneel van staat verandert.
    // e.isOpened() weerspiegelt de staat waarnaar overgeschakeld wordt, niet de huidige staat.
    String direction = e.isOpened() ? "openend" : "sluitend";
});

panel.onOpen(e -> {
    // Vindt plaats nadat het paneel volledig open is — goed voor lui laden van inhoud.
});

panel.onClose(e -> {
    // Vindt plaats nadat het paneel volledig gesloten is — goed voor opruimen of samenvattingsupdates.
});
```

## Stijlen {#styling}

<TableBuilder name={['Accordion', 'AccordionPanel']} />
