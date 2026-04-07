---
sidebar_position: 1
title: Accordion
sidebar_class_name: new-content
_i18n_hash: 560172f4743427476d9ecaadebd1d61d
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-accordion" />
<DocChip chip='name' label="dwc-accordion-panel" />
<DocChip chip='since' label='25.12' />
<JavadocLink type="accordion" location="com/webforj/component/accordion/Accordion" top='true'/>

De `Accordion` component biedt een verticaal gestapelde set van inklapbare panelen. Elk paneel heeft een klikbare kop die de zichtbaarheid van de inhoud van het lichaam omschakelt. Een `AccordionPanel` kan worden gebruikt als een op zichzelf staande onthullingssectie, of gegroepeerd binnen een `Accordion` om het uitbreiden en inklappen van meerdere panelen te coördineren.

<!-- INTRO_END -->

:::tip Wanneer een accordion te gebruiken
Accordions werken goed voor FAQ's, instellingenpagina's en stap-voor-stap flows waarbij het onthullen van alle inhoud tegelijk een overweldigende lay-out zou creëren. Als secties even belangrijk zijn en gebruikers profiteren van het gelijktijdig zien ervan, overweeg dan in plaats daarvan [tabbladen](/docs/components/tabbedpane).
:::

## `AccordionPanel` {#accordion-panel}

`AccordionPanel` is de kerncomponent van het accordion-systeem. Geef een labelstring door aan de constructor om de koptekst in te stellen, en voeg vervolgens kindcomponenten toe om de body te vullen. Een paneel werkt op zichzelf zonder enige omringende `Accordion` groep, waardoor het een nuttige lichte onthulwidget is wanneer je gewoon een enkele inklapbare sectie nodig hebt. De constructor zonder argumenten is ook beschikbaar wanneer je het paneel volledig wilt configureren na de constructie.

```java
// Alleen een label - voeg de inhoud van het lichaam apart toe
AccordionPanel panel = new AccordionPanel("Sectietitel");
panel.add(new Paragraph("Hier komt de inhoud van het lichaam."));

// Label en inhoud van het lichaam direct doorgegeven aan de constructor
AccordionPanel panel = new AccordionPanel("Titel", new Paragraph("Inhoud van het lichaam."));
```

<!-- vale off -->
<ComponentDemo
path='/webforj/accordionbasic'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/accordion/AccordionBasicView.java'
height='500px'
/>
<!-- vale on -->

### Openen en sluiten {#opening-and-closing}

Bestuur de open/sluiten status programmatisch op elk moment. `isOpened()` is nuttig wanneer je de huidige status moet lezen voordat je besluit wat te doen. Je kunt bijvoorbeeld een paneel naar de tegenovergestelde status toggelen of conditioneel andere delen van de UI tonen of verbergen.

```java
// Breid het paneel uit
panel.open();

// Vouw het paneel in
panel.close();                    

// Geeft waar terug als het momenteel uitgebreid is
boolean isOpen = panel.isOpened();
```

Gebruik `setLabel()` om de koptekst na de constructie bij te werken. `setText()` is een alias voor dezelfde bewerking, zodat het label kan worden gesynchroniseerd met dynamische gegevens:

```java
panel.setLabel("Bijgewerkt Label");
```

## Accordion groepen {#accordion-groups}

Het omwikkelen van meerdere `AccordionPanel` instanties binnen een `Accordion` creëert een gecoördineerde groep. Standaard gebruikt de groep **enkelvoudige modus**: het openen van één paneel vouwt automatisch alle andere in, zodat er steeds maar één sectie zichtbaar is. Deze standaardinstelling is opzettelijk; het houdt de gebruiker gefocust op één stuk inhoud en voorkomt dat de pagina visueel overweldigend wordt wanneer panelen aanzienlijke inhoud hebben.

Panelen worden onafhankelijk geconstrueerd en aan de `Accordion` doorgegeven, zodat je elk panel kunt configureren voordat je ze groepeert. Meerdere aparte `Accordion` instanties kunnen ook op dezelfde pagina bestaan—elke groep beheert zijn eigen status onafhankelijk, zodat het uitbreiden van een paneel in de ene groep geen effect heeft op een andere.

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

### Meerdere modus {#multiple-mode}

Meerdere modus staat een onbeperkt aantal panelen toe om tegelijkertijd uitgebreid te blijven. Dit is nuttig wanneer gebruikers de inhoud van verschillende secties tegelijk moeten vergelijken, of wanneer elk paneel kort genoeg is zodat het uitbreiden van meerdere panelen tegelijk geen rommelige lay-out creëert.

```java
accordion.setMultiple(true);
```

Met meerdere modus actief, kunnen alle panelen in de groep tegelijk worden uitgebreid of ingeklapt met behulp van de bulkmethoden:

```java
// Breid elk paneel in de groep uit
accordion.openAll();

// Vouw elk paneel in de groep in
accordion.closeAll();   
```

<!-- vale off -->
<ComponentDemo
path='/webforj/accordionmultiple'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/accordion/AccordionMultipleView.java'
height='500px'
/>
<!-- vale on -->

:::info Restrictie op enkelvoudige modus
`openAll()` is alleen beschikbaar wanneer de meerdere modus is ingeschakeld. Het aanroepen ervan in enkelvoudige modus heeft geen effect. `closeAll()` werkt in beide modi.
:::

<!-- vale off -->
## Uitgeschakelde staat {#disabled-state}
<!-- vale on -->

Individuele panelen kunnen worden uitgeschakeld om gebruikersinteractie te voorkomen terwijl ze zichtbaar blijven. Dit is handig tijdens laadstatussen of wanneer bepaalde secties conditioneel niet beschikbaar zijn, waarbij de panelstructuur wordt getoond zonder voortijdige interactie toe te staan. Een uitgeschakeld paneel dat al open was, blijft uitgebreid, maar de kop kan niet meer worden aangeklikt om het in te klappen. Het uitschakelen van de `Accordion` groep past de uitgeschakelde staat toe op alle daarin bevattende panelen tegelijk, zodat je niet door panelen afzonderlijk hoeft te lopen.

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
height='600px'
/>
<!-- vale on -->

## Panels aanpassen {#customizing-panels}

Bovenop labels en basis open/sluit gedrag, ondersteunt elk `AccordionPanel` rijkere aanpassing van zowel de inhoud van de kop als het expanderen/inklappen pictogram.

### Aangepaste kop {#custom-header}

De kop van een paneel geeft standaard zijn label weer als platte tekst. Gebruik `addToHeader()` om die tekst te vervangen door een component of combinatie van componenten, zodat het eenvoudig is om iconen, badges, statusindicatoren of andere rijke markup naast het paneellabel op te nemen. Dit is vooral nuttig in dashboards of instellingenpanelen waar elke sectiekop extra context in één oogopslag nodig heeft, zoals een aantal items, een waarschuwingsbadge, of een voltooiingsstatus, zonder dat de gebruiker het paneel eerst hoeft uit te vouwen.

```java
FlexLayout headerContent = FlexLayout.create()
    .horizontal()
    .build()
    .setSpacing("var(--dwc-space-s)");

headerContent.add(FeatherIcon.SETTINGS.create(), new Span("Aangepaste Kop met Pictogram"));
panel.addToHeader(headerContent);
```

:::info Vervanging van het label
Inhoud die via `addToHeader()` wordt toegevoegd vervangt volledig de standaardlabeltekst. `setLabel()` en `setText()` blijven naast `addToHeader()` werken, maar aangezien de kopruimte visueel prioriteit heeft, wordt de labeltekst niet weergegeven tenzij je deze expliciet opneemt in je gesloten inhoud.
:::

<!-- vale off -->
<ComponentDemo
path='/webforj/accordioncustomheader'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/accordion/AccordionCustomHeaderView.java'
height='300px'
/>
<!-- vale on -->

### Aangepast pictogram {#custom-icon}

De indicator voor uitvouwen/inklappen standaard is een chevron die zichtbaar is in zowel de open als gesloten status. `setIcon()` vervangt deze door een [`Icon`](/docs/components/icon) component, nuttig voor merkiconografie of wanneer een andere visuele metafoor beter past bij de inhoud. Het doorgeven van `null` herstelt de standaard chevron. `getIcon()` retourneert het momenteel ingestelde pictogram, of `null` als de standaard chevron in gebruik is.

```java
// Vervang de standaard chevron door een pluspictogram
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

Accordions kunnen genest worden binnen andere accordionpanelen, wat nuttig is voor het weergeven van hiërarchische inhoud zoals gecategoriseerde instellingen of navigatie op meerdere niveaus. Voeg een innerlijke `Accordion` toe aan een uiterlijke `AccordionPanel` als elke andere kindcomponent. Houd de nesting ondiep. Eén of twee niveaus is meestal voldoende. Diepere hiërarchieën zijn vaak moeilijker te navigeren en signaleren vaak dat de inhoudsstructuur zelf opnieuw moet worden nagedacht.

```java
AccordionPanel innerA = new AccordionPanel("Binnenpaneel A");
AccordionPanel innerB = new AccordionPanel("Binnenpaneel B");
Accordion innerAccordion = new Accordion(innerA, innerB);

AccordionPanel outer = new AccordionPanel("Buitenpaneel");
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

`AccordionPanel` genereert gebeurtenissen in elke fase van de toggle-lifecycle. De drie gebeurtenistypen dekken verschillende momenten, dus kies op basis van wanneer je logica moet draaien:

| Gebeurtenis | Vindt plaats |
|-------|-------|
| `AccordionPanelToggleEvent` | Voordat de status verandert |
| `AccordionPanelOpenEvent` | Nadat het paneel volledig is geopend |
| `AccordionPanelCloseEvent` | Nadat het paneel volledig is gesloten |

```java
panel.onToggle(e -> {
    // Vindt plaats voordat het paneel van status verandert.
    // e.isOpened() weerspiegelt de status waarvoor wordt overgeschakeld, niet de huidige status.
    String direction = e.isOpened() ? "opening" : "closing";
});

panel.onOpen(e -> {
    // Vindt plaats nadat het paneel volledig open is — goed voor lazy-loading inhoud.
});

panel.onClose(e -> {
    // Vindt plaats nadat het paneel volledig is gesloten — goed voor schoonmaak of samenvattingsupdates.
});
```

## Stijlen {#styling}

<TableBuilder name="AccordionPanel" />
