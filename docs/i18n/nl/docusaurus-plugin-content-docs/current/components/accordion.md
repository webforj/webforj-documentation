---
sidebar_position: 1
title: Accordion
sidebar_class_name: new-content
_i18n_hash: 2bf90130b3a767840e2604045504ee91
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-accordion" />
<DocChip chip='name' label="dwc-accordion-panel" />
<DocChip chip='since' label='25.12' />
<JavadocLink type="accordion" location="com/webforj/component/accordion/Accordion" top='true'/>

De `Accordion` component biedt een verticaal gestapeld set van in te klappen panelen. Elk paneel heeft een klikbare kop die de zichtbaarheid van de inhoud van het lichaam toggelt. Een `AccordionPanel` kan worden gebruikt als een zelfstandige openbaarmakingssectie, of gegroepeerd binnen een `Accordion` om het openen en sluiten over meerdere panelen te coördineren.

<!-- INTRO_END -->

:::tip Wanneer een accordion te gebruiken
Accordions werken goed voor FAQ's, instellingenpagina's en stap-voor-stap flows waarbij het onthullen van alle inhoud tegelijk een overweldigende lay-out zou creëren. Als secties even belangrijk zijn en gebruikers voordeel hebben bij het gelijktijdig zien ervan, overweeg dan in plaats daarvan [tabs](/docs/components/tabbedpane).
:::

## `AccordionPanel` {#accordion-panel}

`AccordionPanel` is het kernbouwblok van het accordion-systeem. Geef een labelstring door aan de constructor om de koptekst in te stellen, en voeg vervolgens kindcomponenten toe om de inhoud van het lichaam te vullen. Een paneel kan zelfstandig werken zonder groepsgewijze `Accordion`, wat het een nuttige lichte openbaarmakingswidget maakt wanneer je alleen een enkel in te klappen sectie nodig hebt. De constructor zonder argumenten is ook beschikbaar wanneer je het paneel volledig na de constructie wilt configureren.

```java
// Alleen label - voeg de inhoud van het lichaam apart toe
AccordionPanel panel = new AccordionPanel("Sectietitel");
panel.add(new Paragraph("Hier komt de inhoud."));

// Label en inhoud van het lichaam direct doorgegeven in de constructor
AccordionPanel panel = new AccordionPanel("Titel", new Paragraph("Inhoud van het lichaam."));
```

<!-- vale off -->
<ComponentDemo
path='/webforj/accordionbasic'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/accordion/AccordionBasicView.java'
height='550px'
/>
<!-- vale on -->

### Openen en sluiten {#opening-and-closing}

Controleer de open/dicht status programmatisch op elk moment. `isOpened()` is nuttig wanneer je de huidige status moet lezen voordat je beslist wat je moet doen. Bijvoorbeeld, je kunt een paneel naar de tegenovergestelde staat toggelen of conditioneel andere delen van de UI laten zien of verbergen.

```java
// Zet het paneel open
panel.open();

// Zet het paneel dicht
panel.close();                    

// Geeft true terug als het momenteel geopend is
boolean isOpen = panel.isOpened();
```

Gebruik `setLabel()` om de koptekst na de constructie bij te werken. `setText()` is een alias voor dezelfde bewerking, zodat het label in sync kan worden gehouden met dynamische gegevens:

```java
panel.setLabel("Bijgewerkt Label");
```

## Accordion groepen {#accordion-groups}

Het wikkelen van meerdere `AccordionPanel` instanties binnen een `Accordion` creëert een gecoördineerde groep. Standaard gebruikt de groep **single mode**: het openen van één paneel vouwt automatisch alle andere in, waardoor er telkens slechts één sectie zichtbaar is. Dit standaardinstelling is opzettelijk, het houdt de gebruiker gefocust op één stuk inhoud en voorkomt dat de pagina visueel overweldigend wordt wanneer panelen aanzienlijke inhoud hebben.

Panelen worden onafhankelijk geconstrueerd en doorgegeven aan de `Accordion`, zodat je elk afzonderlijk kunt configureren voordat je ze groepeert. Meerdere afzonderlijke `Accordion` instanties kunnen ook op dezelfde pagina bestaan—iedere groep beheert zijn eigen status onafhankelijk, zodat het uitbreiden van een paneel in de ene groep geen effect heeft op een andere.

```java
AccordionPanel panel1 = new AccordionPanel("Wat is webforJ?");
AccordionPanel panel2 = new AccordionPanel("Hoe werken gegroepeerde panelen?");
AccordionPanel panel3 = new AccordionPanel("Kan ik meerdere groepen hebben?");

Accordion accordion = new Accordion(panel1, panel2, panel3);
```

<!-- vale off -->
<ComponentDemo
path='/webforj/accordiongroup'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/accordion/AccordionGroupView.java'
height='400px'
/>
<!-- vale on -->

### Meervoudige modus {#multiple-mode}

Meervoudige modus staat toe dat een onbeperkt aantal panelen tegelijkertijd geopend blijft. Dit is nuttig wanneer gebruikers de inhoud van verschillende secties tegelijkertijd moeten vergelijken, of wanneer elk paneel kort genoeg is zodat het tegelijkertijd openen van meerdere geen rommelige lay-out creëert.

```java
accordion.setMultiple(true);
```

Met de actieve meervoudige modus kunnen alle panelen in de groep tegelijkertijd worden geopend of gesloten met de bulkmethoden:

```java
// Open elk paneel in de groep
accordion.openAll();

// Sluit elk paneel in de groep
accordion.closeAll();   
```

<!-- vale off -->
<ComponentDemo
path='/webforj/accordionmultiple'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/accordion/AccordionMultipleView.java'
height='575px'
/>
<!-- vale on -->

:::info Beperking in single mode
`openAll()` is alleen beschikbaar wanneer meervoudige modus is ingeschakeld. Het aanroepen ervan in single mode heeft geen effect. `closeAll()` werkt in beide modes.
:::

<!-- vale off -->
## Uitschakelen staat {#disabled-state}
<!-- vale on -->

Individuele panelen kunnen worden uitgeschakeld om gebruikersinteractie te voorkomen terwijl ze zichtbaar blijven. Dit is handig tijdens laadstatussen of wanneer bepaalde secties conditioneel niet beschikbaar zijn, waarbij de paneelstructuur wordt getoond zonder voortijdige interactie toe te staan. Een uitgeschakeld paneel dat al open was, blijft geopend, maar de kop kan niet meer worden geklikt om het te sluiten. Het uitschakelen van de `Accordion` groep past de uitgeschakelde status tegelijkertijd toe op alle ingesloten panelen, zodat je ze niet individueel hoeft door te nemen.

```java
// Schakel een enkel paneel uit
panel.setEnabled(false);

// Schakel alle panelen in de groep uit
accordion.setEnabled(false);
```

<!-- vale off -->
<ComponentDemo
path='/webforj/accordiondisabled'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/accordion/AccordionDisabledView.java'
height='650px'
/>
<!-- vale on -->

## Aanpassen van panelen {#customizing-panels}

Naast labels en basis open/sluit gedrag ondersteunt elk `AccordionPanel` rijkere aanpassing van zowel de inhoud van de kop als het expand-/inklapicoon.

### Aangepaste kop {#custom-header}

De kop van een paneel geeft standaard zijn label weer als platte tekst. Gebruik `addToHeader()` om die tekst te vervangen door een component of combinatie van componenten, wat het eenvoudig maakt om iconen, badges, statusindicatoren of andere rijke opmaak naast het paneellabel op te nemen. Dit is vooral handig in dashboards of instellingenpanelen waar elke sectiekop extra context in één oogopslag moet overbrengen, zoals een itemaantal, een waarschuwingsbadge of een voltooiingsstatus, zonder dat de gebruiker het paneel eerst hoeft uit te klappen.

```java
FlexLayout headerContent = FlexLayout.create()
    .horizontal()
    .build()
    .setSpacing("var(--dwc-space-s)");

headerContent.add(FeatherIcon.SETTINGS.create(), new Span("Aangepaste Kop met Icon"));
panel.addToHeader(headerContent);
```

:::info Label vervanging
Inhoud toegevoegd via `addToHeader()` vervangt volledig de standaard labeltekst. `setLabel()` en `setText()` blijven werken naast `addToHeader()`, maar omdat de header slot visuele voorkeur heeft, wordt de labeltekst niet weergegeven, tenzij je deze expliciet in je ingesloten inhoud opneemt.
:::

<!-- vale off -->
<ComponentDemo
path='/webforj/accordioncustomheader'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/accordion/AccordionCustomHeaderView.java'
height='300px'
/>
<!-- vale on -->

### Aangepast icoon {#custom-icon}

De expand/invouw indicator is standaard een chevron die zichtbaar is in zowel de open als gesloten staten. `setIcon()` vervangt deze door een willekeurige [`Icon`](/docs/components/icon) component, nuttig voor gebrandmerkte iconografie of wanneer een andere visuele metafoor beter bij de inhoud past. Het doorgeven van `null` herstelt de standaard chevron. `getIcon()` retourneert het momenteel ingestelde icoon, of `null` als de standaard chevron in gebruik is.

```java
// Vervang de standaard chevron door een plusicoon
panel.setIcon(FeatherIcon.PLUS.create());

// Herstel de standaard chevron
panel.setIcon(null);
```

<!-- vale off -->
<ComponentDemo
path='/webforj/accordioncustomicon'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/accordion/AccordionCustomIconView.java'
height='200px'
/>
<!-- vale on -->

## Geneste accordions {#nested-accordions}

Accordions kunnen binnen andere accordion panelen worden genest, wat nuttig is voor het weergeven van hiërarchische inhoud, zoals gecategoriseerde instellingen of navigatie op meerdere niveaus. Voeg een interne `Accordion` toe aan een externe `AccordionPanel` zoals elke andere kindcomponent. Houd de nesting ondiep. Eén of twee niveaus is meestal genoeg. Diepere hiërarchieën blijken vaak moeilijker te navigeren en duiden vaak aan dat de inhoudstructuur zelf heroverwogen moet worden.

```java
AccordionPanel innerA = new AccordionPanel("Binnenpaneel A");
AccordionPanel innerB = new AccordionPanel("Binnenpaneel B");
Accordion innerAccordion = new Accordion(innerA, innerB);

AccordionPanel outer = new AccordionPanel("Buitenkanaal");
outer.add(innerAccordion);
```

<!-- vale off -->
<ComponentDemo
path='/webforj/accordionnested'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/accordion/AccordionNestedView.java'
height='550px'
/>
<!-- vale on -->

## Gebeurtenissen {#events}

`AccordionPanel` genereert gebeurtenissen in elke fase van de toggle-lifecycle. De drie types gebeurtenissen dekken verschillende momenten, dus kies op basis van wanneer jouw logica moet draaien:

| Gebeurtenis | Vindt plaats |
|-------|-------|
| `AccordionPanelToggleEvent` | Voordat de status verandert |
| `AccordionPanelOpenEvent` | Nadat het paneel volledig is geopend |
| `AccordionPanelCloseEvent` | Nadat het paneel volledig is gesloten |

```java
panel.onToggle(e -> {
    // Vindt plaats voordat het paneel van status verandert.
    // e.isOpened() weerspiegelt de status waarheen men overgaat, niet de huidige status.
    String direction = e.isOpened() ? "openend" : "sluitend";
});

panel.onOpen(e -> {
    // Vindt plaats nadat het paneel volledig is geopend — goed voor lazy-loading inhoud.
});

panel.onClose(e -> {
    // Vindt plaats nadat het paneel volledig is gesloten — goed voor opschoning of samenvattingen.
});
```

## Stijlen {#styling}

<TableBuilder name={['Accordion', 'AccordionPanel']} />
