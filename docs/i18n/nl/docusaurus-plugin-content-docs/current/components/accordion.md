---
sidebar_position: 1
title: Accordion
sidebar_class_name: new-content
_i18n_hash: 99f4482faa552334ce209b3f9296f4f5
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-accordion" />
<DocChip chip='name' label="dwc-accordion-panel" />
<DocChip chip='since' label='25.12' />
<JavadocLink type="accordion" location="com/webforj/component/accordion/Accordion" top='true'/>

De `Accordion` component biedt een verticaal gestapelde set van inklapbare panelen. Elk paneel heeft een klikbare header die de zichtbaarheid van de inhoud van het lichaam togglet. Een `AccordionPanel` kan worden gebruikt als een standalone onthullingssectie, of gegroepeerd binnen een `Accordion` om de uitvouw- en inklapgedrag over meerdere panelen te coördineren.

<!-- INTRO_END -->

:::tip Wanneer een accordion te gebruiken
Accordions werken goed voor FAQ's, instellingenpagina's en stap-voor-stap flows waarbij het onthullen van alle inhoud tegelijk een overweldigende lay-out zou creëren. Als secties even belangrijk zijn en gebruikers baat hebben bij het gelijktijdig zien ervan, overweeg dan in plaats daarvan [tabbladen](/docs/components/tabbedpane).
:::

## `AccordionPanel` {#accordion-panel}

`AccordionPanel` is de kernbouwsteen van het accordion-systeem. Geef een labelstring door aan de constructor om de headertekst in te stellen, en voeg vervolgens kindcomponenten toe om het lichaam te vullen. Een paneel werkt op zichzelf zonder enige omringende `Accordion`-groep, waardoor het een nuttige lichtgewicht onthullingswidget is wanneer je alleen een enkele inklapbare sectie nodig hebt. De constructor zonder argumenten is ook beschikbaar wanneer je het paneel volledig na de constructie wilt configureren.

```java
// Alleen label - voeg de inhoud van het lichaam apart toe
AccordionPanel panel = new AccordionPanel("Sectietitel");
panel.add(new Paragraph("Inhoud van het lichaam komt hier.")); 

// Label en inhoud van het lichaam direct doorgegeven in de constructor
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

Controleer de open/gesloten status programmatisch op elk moment. `isOpened()` is nuttig wanneer je de huidige status moet lezen voordat je beslist wat te doen. Je kunt bijvoorbeeld een paneel naar de tegengestelde staat toggelen of conditioneel andere delen van de UI tonen of verbergen.

```java
// Breid het paneel uit
panel.open();

// Verkleur het paneel
panel.close();                    

// Retourneert true als momenteel uitgebreid
boolean isOpen = panel.isOpened();
```

Gebruik `setLabel()` om de headertekst na de constructie bij te werken. `setText()` is een alias voor dezelfde operatie, zodat het label gesynchroniseerd kan blijven met dynamische gegevens:

```java
panel.setLabel("Bijgewerkt Label");
```

## Accordion-groepen {#accordion-groups}

Het wikkelen van meerdere `AccordionPanel`-instanties binnen een `Accordion` creëert een gecoördineerde groep. Standaard gebruikt de groep **enkele modus**: het openen van één paneel verkleint automatisch alle andere, waardoor slechts één sectie tegelijk zichtbaar is. Deze standaard is opzettelijk, zodat de gebruiker zich kan concentreren op één stuk inhoud en voorkomt dat de pagina visueel overweldigend wordt wanneer panelen aanzienlijke inhoud van het lichaam hebben.

Panelen worden onafhankelijk geconstrueerd en aan de `Accordion` doorgegeven, zodat je elk paneel kunt configureren voordat je ze groepeert. Meerdere afzonderlijke `Accordion`-instanties kunnen ook op dezelfde pagina bestaan—iedere groep beheert zijn eigen status onafhankelijk, zodat het uitbreiden van een paneel in één groep geen effect heeft op een andere.

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

Meervoudige modus staat een onbeperkt aantal panelen toe om tegelijkertijd uitgebreid te blijven. Dit is nuttig wanneer gebruikers de inhoud van verschillende secties tegelijk moeten vergelijken, of wanneer elk paneel kort genoeg is dat het tegelijkertijd uitbreiden van meerdere geen rommelige lay-out creëert.

```java
accordion.setMultiple(true);
```

Met actieve meervoudige modus kunnen alle panelen in de groep tegelijk worden uitgebreid of ingeklapt met de bulkmethoden:

```java
// Breid elk paneel in de groep uit
accordion.openAll();

// Verkleur elk paneel in de groep
accordion.closeAll();   
```

<!-- vale off -->
<ComponentDemo
path='/webforj/accordionmultiple'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/accordion/AccordionMultipleView.java'
height='500px'
/>
<!-- vale on -->

:::info Beperkingen van de enkele modus
`openAll()` is alleen beschikbaar wanneer de meervoudige modus is ingeschakeld. Het aanroepen ervan in de enkele modus heeft geen effect. `closeAll()` werkt in beide modi.
:::

<!-- vale off -->
## Uitgeschakelde staat {#disabled-state}
<!-- vale on -->

Individuele panelen kunnen worden uitgeschakeld om gebruikersinteractie te voorkomen terwijl ze nog zichtbaar blijven. Dit is handig tijdens laadstatussen of wanneer bepaalde secties conditioneel niet beschikbaar zijn, waardoor de paneelstructuur wordt getoond zonder voortijdige interactie toe te staan. Een uitgeschakeld paneel dat al open was, blijft uitgebreid, maar de header kan niet meer worden aangeklikt om het te verkleinen. Het uitschakelen van de `Accordion`-groep past de uitgeschakelde staat toe op alle opgenomen panelen tegelijk, zodat je niet door panelen individueel hoeft te lopen.

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

## Panelen aanpassen {#customizing-panels}

Buiten labels en basis open/gesloten gedrag ondersteunt elk `AccordionPanel` rijkere aanpassing van zowel de inhoud van de header als het uitvouw-/inklapicoon.

### Aangepaste header {#custom-header}

De header van een paneel rendert zijn label standaard als platte tekst. Gebruik `addToHeader()` om die tekst te vervangen door een willekeurige component of combinatie van componenten, waardoor het eenvoudig wordt om iconen, badges, statusindicatoren of andere rijke markup naast het paneellabel op te nemen. Dit is vooral nuttig in dashboards of instellingenpanelen waar elke sectieheader extra context moet overbrengen met een blik, zoals een itemtel, een waarschuwingsbadge of een voltooiingsstatus, zonder dat de gebruiker het paneel eerst hoeft uit te vouwen.

```java
FlexLayout headerContent = FlexLayout.create()
    .horizontal()
    .build()
    .setSpacing("var(--dwc-space-s)");

headerContent.add(FeatherIcon.SETTINGS.create(), new Span("Aangepaste Header met Icoon"));
panel.addToHeader(headerContent);
```

:::info Vervanging van het label
Inhoud die via `addToHeader()` wordt toegevoegd, vervangt volledig de standaard labeltekst. `setLabel()` en `setText()` blijven naast `addToHeader()` werken, maar omdat de header slot visuele prioriteit heeft, wordt de labeltekst niet weergegeven, tenzij je deze expliciet in je gesloten inhoud opneemt.
:::

<!-- vale off -->
<ComponentDemo
path='/webforj/accordioncustomheader'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/accordion/AccordionCustomHeaderView.java'
height='300px'
/>
<!-- vale on -->

### Aangepast icoon {#custom-icon}

De uitvouw-/inklapindicator is standaard een chevron die zichtbaar is in zowel de open als gesloten staten. `setIcon()` vervangt deze door een willekeurige [`Icon`](/docs/components/icon) component, nuttig voor merkiconografie of wanneer een andere visuele metafoor beter bij de inhoud past. Het doorgeven van `null` herstelt de standaard chevron. `getIcon()` retourneert het momenteel ingestelde icoon, of `null` als de standaard chevron in gebruik is.

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

Accordions kunnen binnen andere accordionpanelen worden genest, wat nuttig is voor het vertegenwoordigen van hiërarchische inhoud zoals gecategoriseerde instellingen of meerdere niveaus van navigatie. Voeg een interne `Accordion` toe aan een externe `AccordionPanel` als elke andere kindcomponent. Houd het nestelen ondiep. Eén of twee niveaus is doorgaans genoeg. Diepere hiërarchieën zijn moeilijker te navigeren en signaleren vaak dat de inhoudsstructuur zelf opnieuw moet worden doordacht.

```java
AccordionPanel innerA = new AccordionPanel("Binnen Paneel A");
AccordionPanel innerB = new AccordionPanel("Binnen Paneel B");
Accordion innerAccordion = new Accordion(innerA, innerB);

AccordionPanel outer = new AccordionPanel("Buiten Paneel");
outer.add(innerAccordion);
```

<!-- vale off -->
<ComponentDemo
path='/webforj/accordionnested'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/accordion/AccordionNestedView.java'
height='550px'
/>
<!-- vale on -->

## Evenementen {#events}

`AccordionPanel` genereert evenementen in elke fase van de toggling levenscyclus. De drie evenementtypes dekken verschillende momenten, dus kies op basis van wanneer jouw logica moet draaien:

| Evenement | Vindt plaats |
|-----------|--------------|
| `AccordionPanelToggleEvent` | Voordat de status verandert |
| `AccordionPanelOpenEvent` | Nadat het paneel volledig is geopend |
| `AccordionPanelCloseEvent` | Nadat het paneel volledig is gesloten |

```java
panel.onToggle(e -> {
    // Vindt plaats voordat het paneel van status verandert.
    // e.isOpened() weerspiegelt de status waarmee wordt overgestapt, niet de huidige status.
    String direction = e.isOpened() ? "opening" : "closing";
});

panel.onOpen(e -> {
    // Vindt plaats nadat het paneel volledig open is — goed voor het lazy-loaden van inhoud.
});

panel.onClose(e -> {
    // Vindt plaats nadat het paneel volledig is gesloten — goed voor opruiming of samenvattingen.
});
```

## Stijling {#styling}

<TableBuilder name={['Accordion', 'AccordionPanel']} />
