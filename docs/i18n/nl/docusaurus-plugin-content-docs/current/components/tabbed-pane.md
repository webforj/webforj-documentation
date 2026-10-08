---
title: TabbedPane
slug: tabbedpane
sidebar_position: 125
description: >-
  Organize content into switchable Tab sections with the TabbedPane component,
  supporting icons and customizable tab properties.
_i18n_hash: 544ab11783e8369075f1c02aba2d8dc8
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-tabbed-pane" />
<DocChip chip='since' label='23.06' />
<JavadocLink type="foundation" location="com/webforj/component/tabbedpane/TabbedPane" top='true'/>

Meerdere secties van inhoud kunnen worden georganiseerd onder een enkele `TabbedPane`, waarbij elke sectie is gekoppeld aan een klikbare `Tab`. Slechts één sectie is op een gegeven moment zichtbaar, en tabs kunnen tekst, pictogrammen of beide weergeven om gebruikers te helpen tussen hen te navigeren.

<!-- INTRO_END -->

## Gebruik {#usages}

De `TabbedPane` klasse biedt ontwikkelaars een krachtig hulpmiddel voor het organiseren en presenteren van meerdere tabs of secties binnen een UI. Hier zijn enkele typische scenario's waarin je een `TabbedPane` in je app zou kunnen gebruiken:

1. **Documentviewer**: Implementatie van een documentviewer waarbij elke tab een ander document of bestand vertegenwoordigt. Gebruikers kunnen eenvoudig schakelen tussen open documenten voor efficiënte multitasking.

2. **Gegevensbeheer**: Gebruik een `TabbedPane` om gegevensbeheertaken te organiseren, bijvoorbeeld:
    - Verschillende datasets die in een app moeten worden weergegeven
    - Diverse gebruikersprofielen kunnen binnen aparte tabs worden weergegeven
    - Verschillende profielen in een gebruikersbeheersysteem

3. **Moduleselectie**: Een `TabbedPane` kan verschillende modules of secties vertegenwoordigen. Elke tab kan de functionaliteiten van een specifieke module encapsuleren, zodat gebruikers zich op één aspect van de app tegelijk kunnen concentreren.

4. **Taakbeheer**: Taakbeheer-apps kunnen een `TabbedPane` gebruiken om verschillende projecten of taken weer te geven. Elke tab kan overeenkomen met een specifiek project, zodat gebruikers taken apart kunnen beheren en volgen.

5. **Programmanavigatie**: Binnen een app die verschillende programma's moet uitvoeren, kan een `TabbedPane`:
    - Dienen als een zijbalk waarin verschillende apps of programma's binnen een enkele app kunnen worden uitgevoerd, zoals weergegeven in de [`AppLayout`](./app-layout.md) sjabloon
    - Een bovenste balk creëren die hetzelfde doel kan dienen, of sub-applicaties kan vertegenwoordigen binnen een al geselecteerde app

## Tabs {#tabs}

Tabs zijn UI-elementen die aan tabbladen kunnen worden toegevoegd om verschillende inhoudsweergaven te organiseren en te wisselen.

:::important
Tabs zijn niet bedoeld om als zelfstandige componenten te worden gebruikt. Ze zijn bedoeld om samen met tabbladen te worden gebruikt. Deze klasse is geen `Component` en moet niet als zodanig worden gebruikt.
:::

### Eigenschappen {#properties}

Tabs zijn samengesteld uit de volgende eigenschappen, die worden gebruikt bij het toevoegen ervan in een `TabbedPane`. Deze eigenschappen hebben getters en setters om aanpassing binnen een `TabbedPane` te vergemakkelijken.

1. **Tekst(`String`)**: De tekst die wordt weergegeven als een titel voor de `Tab` binnen de `TabbedPane`. Dit wordt ook wel de titel genoemd via de `getTitle()` en `setTitle(String title)` methoden.

2. **Tooltip(`String`)**: De tooltiptekst die aan de `Tab` is gekoppeld, die wordt weergegeven wanneer de cursor boven de `Tab` zweeft.

3. **Ingeschakeld(`boolean`)**: Geeft aan of de `Tab` is ingeschakeld of niet. Kan worden gewijzigd met de `setEnabled(boolean enabled)` methode.

4. **Sluitbaar(`boolean`)**: Geeft aan of de `Tab` kan worden gesloten. Kan worden gewijzigd met de `setClosable(boolean closable)` methode. Dit voegt een sluitknop toe op de `Tab` die kan worden aangeklikt en een verwijder gebeurtenis activeert. De `TabbedPane` component bepaalt hoe om te gaan met de verwijdering.

5. **Slot(`Component`)**:
    Slots bieden flexibele opties om de mogelijkheden van een `Tab` te verbeteren. Je kunt pictogrammen, labels, laadsnelheden, wissen/resetten, avatar/profielafbeeldingen en andere nuttige componenten binnen een `Tab` nestelen om de bedoeling voor gebruikers verder te verduidelijken.
    Je kunt een component aan de `prefix` slot van een `Tab` toevoegen tijdens de constructie. Alternatief kun je de methoden `setPrefixComponent()` en `setSuffixComponent()` gebruiken om verschillende componenten voor en na de weergegeven optie binnen een `Tab` in te voegen.

        ```java
        TabbedPane pane = new TabbedPane();
        pane.addTab(new Tab("Documenten", TablerIcon.create("bestanden")));
        ```

## `Tab` manipulatie {#tab-manipulation}

Er zijn verschillende methoden beschikbaar waarmee ontwikkelaars verschillende eigenschappen van `Tab` elementen binnen de `TabbedPane` kunnen toevoegen, invoegen, verwijderen en manipuleren.

### Een `Tab` toevoegen {#adding-a-tab}

De methoden `addTab()` en `add()` bestaan in verschillende overbelaste versies om ontwikkelaars de flexibiliteit te geven om nieuwe tabs toe te voegen aan de `TabbedPane`. Het toevoegen van een `Tab` plaatst deze achter eventuele eerder bestaande tabs.

1. **`addTab(String text)`**: voegt een `Tab` toe aan de `TabbedPane` met de opgegeven `String` als de tekst van de `Tab`.
2. **`addTab(Tab tab)`**: voegt de `Tab` die als parameter is opgegeven toe aan de `TabbedPane`.
3. **`addTab(String text, Component component)`**: voegt een `Tab` toe met de gegeven `String` als de tekst van de `Tab`, en de opgegeven `Component` die in het inhoudsgedeelte van de `TabbedPane` wordt weergegeven.
4. **`addTab(Tab tab, Component component)`**: voegt de opgegeven `Tab` toe en toont de opgegeven `Component` in het inhoudsgedeelte van de `TabbedPane`.
5. **`add(Component... component)`**: voegt een of meer `Component` instanties toe aan de `TabbedPane`, en creëert een discrete `Tab` voor elk, met de tekst ingesteld op de naam van de `Component`.

:::info
De `add(Component... component)` bepaalt de naam van de doorgegeven `Component` door `component.getName()` aan te roepen op het doorgegeven argument.
:::

### Een `Tab` invoegen {#inserting-a-tab}

Naast het toevoegen van een `Tab` aan het einde van de bestaande tabs, is het ook mogelijk om een nieuwe te creëren op een aangewezen positie. Dit kan met behulp van verschillende overbelaste versies van de `insertTab()`.

1. **`insertTab(int index, String text)`**: voegt een `Tab` toe aan de `TabbedPane` op de gegeven index met de opgegeven `String` als de tekst van de `Tab`.
2. **`insertTab(int index, Tab tab)`**: voegt de `Tab` die als parameter is opgegeven toe aan de `TabbedPane` op de opgegeven index.
3. **`insertTab(int index, String text, Component component)`**: voegt een `Tab` toe met de opgegeven `String` als de tekst van de `Tab`, en de opgegeven `Component` die in het inhoudsgedeelte van de `TabbedPane` wordt weergegeven.
4. **`insertTab(int index, Tab tab, Component component)`**: voegt de opgegeven `Tab` toe en toont de opgegeven `Component` in het inhoudsgedeelte van de `TabbedPane`.

### Een `Tab` verwijderen {#removing-a-tab}

Om een enkele `Tab` uit de `TabbedPane` te verwijderen, gebruik je een van de volgende methoden:

1. **`removeTab(Tab tab)`**: verwijdert een `Tab` uit de `TabbedPane` door de Tab-instantie door te geven die moet worden verwijderd.
2. **`removeTab(int index)`**: verwijdert een `Tab` uit de `TabbedPane` door de index van de te verwijderen `Tab` op te geven.

Naast de twee bovenstaande methoden voor het verwijderen van een enkele `Tab`, gebruik de **`removeAllTabs()`** methode om de `TabbedPane` van alle tabs te ontdoen.

:::info
De methoden `remove()` en `removeAll()` verwijderen geen tabs binnen de component.
:::

### Tab/Component associatie {#tabcomponent-association}

Om de `Component` die moet worden weergegeven voor een gegeven `Tab` te wijzigen, roep je de `setComponentFor()` methode aan en geef je de instantie van de `Tab`, of de index van die Tab binnen de `TabbedPane` door.

:::info
Als deze methode wordt gebruikt op een `Tab` die al is geassocieerd met een `Component`, wordt de eerder geassocieerde `Component` vernietigd.
:::

## Configuratie en lay-out {#configuration-and-layout}

De `TabbedPane` klasse heeft twee samenstellende delen: een `Tab` die op een specifieke locatie wordt weergegeven, en een component die wordt weergegeven. Dit kan een enkele component zijn, of een [`Composite`](/docs/building-ui/composing-components) component, waardoor het mogelijk is om complexere componenten binnen een tab's inhoudsgedeelte weer te geven.

### Veegbeweging {#swiping}

De `TabbedPane` ondersteunt navigeren door de verschillende tabs via veegbewegingen. Dit is ideaal voor een mobiele app, maar kan ook worden geconfigureerd via een ingebouwde methode om muisveegbewegingen te ondersteunen. Zowel veegbewegingen als muisveegbewegingen zijn standaard uitgeschakeld, maar kunnen worden ingeschakeld met de methoden `setSwipeable(boolean)` en `setSwipeWithMouse(boolean)`.

### Tab plaatsing {#tab-placement}

De `Tabs` binnen een `TabbedPane` kunnen op verschillende posities binnen de component worden geplaatst op basis van de voorkeur van de app-ontwikkelaar. De beschikbare opties worden ingesteld met behulp van de gegeven enum, die de waarden `TOP`, `BOTTOM`, `LEFT`, `RIGHT` of `HIDDEN` heeft. De standaard instelling is `TOP`.


<ComponentDemo
path='/webforj/tabbedpaneplacement'
files={['src/main/java/com/webforj/samples/views/tabbedpane/TabbedPanePlacementView.java']}
height='400px'
/>

### Uitlijning {#alignment}

Naast het wijzigen van de plaatsing van de `Tab` elementen binnen de `TabbedPane`, is het ook mogelijk om te configureren hoe de tabs binnen de component zullen uitlijnen. Standaard is de instelling `AUTO` van kracht, wat het mogelijk maakt om de plaatsing van de tabs hun uitlijning te bepalen.

De andere opties zijn `START`, `END`, `CENTER` en `STRETCH`. De eerste drie beschrijven de positie ten opzichte van de component, waarbij `STRETCH` de tabs de beschikbare ruimte laat vullen.

<ComponentDemo
path='/webforj/tabbedpanealignment'
files={['src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneAlignmentView.java']}
height='250px'
/>

### Rand en activiteitsindicator {#border-and-activity-indicator}

De `TabbedPane` zal standaard een rand voor de tabs binnenin weergeven, afhankelijk van welke `Plaatsing` is ingesteld. Deze rand helpt de ruimte te visualiseren die de verschillende tabs binnen het paneel innemen.

Wanneer een `Tab` wordt aangeklikt, wordt er standaard een activiteitsindicator vlakbij die `Tab` weergegeven om te helpen benadrukken welke de momenteel geselecteerde `Tab` is.

Beide opties kunnen worden aangepast door de booleaanse waarden te wijzigen met behulp van de juiste setter-methodes. Om te wijzigen of de rand wordt weergegeven, kan de methode `setBorderless(boolean)` worden gebruikt, waarbij `true` de rand verbergt, en `false`, de standaardwaarde, de rand weergeeft.

:::info
Deze rand geldt niet voor de gehele `TabbedPane` component, en dient enkel als een scheidingslijn tussen de tabs en de inhoud van de component.
:::

Om de zichtbaarheid van de actieve indicator in te stellen, kan de methode `setHideActiveIndicator(boolean)` worden gebruikt. Het doorgeven van `true` aan deze methode verbergt de actieve indicator onder een actieve `Tab`, terwijl `false`, de standaard, de indicator zichtbaar houdt.

<ComponentDemo
path='/webforj/tabbedpaneborder'
files={['src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneBorderView.java']}
height='300px'
/>

### Activatiemodi {#activation-modes}

Voor meer gedetailleerde controle over hoe de `TabbedPane` zich gedraagt met toetsenbordnavigatie, kan de `Activatie` modus worden ingesteld om te specificeren hoe de component moet reageren.

- **`Auto`**: wanneer ingesteld op automatisch, zullen navigeren tussen tabs met de pijltoetsen onmiddellijk de bijbehorende tabcomponent weergeven.

- **`Handmatig`**: wanneer ingesteld op handmatig, zal de tab focus krijgen maar niet worden weergegeven totdat de gebruiker op spatie of enter drukt.

<ComponentDemo
path='/webforj/tabbedpaneactivation'
files={['src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneActivationView.java']}
height='250px'
/>

### Verwijderingsopties {#removal-options}

Individuele `Tab` elementen kunnen zo worden ingesteld dat ze sluitbaar zijn. Sluitbare tabs hebben een sluitknop aan de tab toegevoegd, die een sluit gebeurtenis activeert wanneer erop wordt geklikt. De `TabbedPane` bepaalt hoe dit gedrag wordt afgehandeld.

- **`Handmatig`**: standaard is verwijdering ingesteld op `MANUAL`, wat betekent dat de gebeurtenis wordt geactiveerd, maar het aan de ontwikkelaar is om deze gebeurtenis op welke manier dan ook te verwerken.

- **`Automatisch`**: als alternatief kan `AUTO` worden gebruikt, wat de gebeurtenis activeert en ook de `Tab` uit de component verwijdert voor de ontwikkelaar, waardoor het onnodig is voor de ontwikkelaar om dit gedrag handmatig te implementeren.

### Segmentcontrole <DocChip chip='since' label='26.00' /> {#segment-control}

De `TabbedPane` kan worden weergegeven als een segmentcontrole door de `segment` eigenschap in te schakelen met `setSegment(true)`. In deze modus worden tabs weergegeven met een schuifpillenindicator die de actieve selectie benadrukt, wat een compacte alternatieve biedt voor de standaard tab-interface.

<ComponentDemo
path='/webforj/tabbedpanesegment'
files={['src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneSegmentView.java']}
height='250px'
/>

## Stijl {#styling}

### Uitbreiding en thema {#expanse-and-theme}

De `TabbedPane` wordt geleverd met ingebouwde `Expanse` en `Thema` opties, vergelijkbaar met andere webforJ componenten. Deze kunnen worden gebruikt om snel styling toe te voegen die verschillende betekenissen naar de eindgebruiker communiceert zonder dat het nodig is om de component met CSS te stylen.

<ComponentDemo
path='/webforj/tabbedpaneexpansetheme'
files={['src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneExpanseThemeView.java']}
height='250px'
/>

<TableBuilder name={['Tab', 'TabbedPane']} />

## Beste praktijken {#best-practices}

De volgende praktijken worden aanbevolen voor het gebruik van de `TabbedPane` binnen applicaties:

- **Logische Groepering**: Gebruik tabs om gerelateerde inhoud logisch te groeperen:
    - Elke tab moet een duidelijke categorie of functionaliteit binnen je app vertegenwoordigen.
    - Groepeer soortgelijke of logische tabs dicht bij elkaar.

- **Beperkte Tabs**: Vermijd het overweldigen van gebruikers met te veel tabs. Overweeg het gebruik van een hiërarchische structuur of andere navigatiepatronen waar van toepassing voor een schone interface.

- **Duidelijke Labels**: Label je Tabs duidelijk voor intuïtief gebruik:
    - Bied duidelijke en beknopte labels voor elke tab.
    - Labels moeten de inhoud of het doel weerspiegelen, zodat het voor gebruikers gemakkelijk te begrijpen is.
    - Gebruik pictogrammen en verschillende kleuren waar van toepassing.

- **Toetsenbordnavigatie**: Gebruik de toetsenbordnavigatie-ondersteuning van webforJ's `TabbedPane` om de interactie met de `TabbedPane` voor de eindgebruiker soepeler en intuïtiever te maken.

- **Standaard Tab**: Als de standaardtab niet aan het begin van de `TabbedPane` is geplaatst, overweeg dan om deze tab als standaard in te stellen voor essentiële of vaak gebruikte informatie.
