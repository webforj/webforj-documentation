---
title: TabbedPane
slug: tabbedpane
sidebar_position: 125
description: >-
  Organize content into switchable Tab sections with the TabbedPane component,
  supporting icons and customizable tab properties.
_i18n_hash: 2d2e78638206283a7423cea736e3bf2c
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-tabbed-pane" />
<DocChip chip='since' label='23.06' />
<JavadocLink type="foundation" location="com/webforj/component/tabbedpane/TabbedPane" top='true'/>

Meerdere secties van inhoud kunnen worden georganiseerd onder een enkele `TabbedPane`, waarbij elke sectie is gekoppeld aan een klikbare `Tab`. Slechts één sectie is tegelijkertijd zichtbaar, en tabs kunnen tekst, pictogrammen of beide weergeven om gebruikers te helpen tussen hen te navigeren.

## Toepassingen {#usages}

De `TabbedPane` klasse biedt ontwikkelaars een krachtig hulpmiddel voor het organiseren en presenteren van meerdere tabs of secties binnen een gebruikersinterface. Hier zijn enkele typische scenario's waarin je een `TabbedPane` in je app zou kunnen gebruiken:

1. **Documentviewer**: Het implementeren van een documentviewer waarbij elke tab een ander document of bestand vertegenwoordigt. Gebruikers kunnen eenvoudig schakelen tussen geopende documenten voor efficiënt multitasking.

2. **Gegevensbeheer**: Gebruik een `TabbedPane` om gegevensbeheertaken te organiseren, bijvoorbeeld:
    - Verschillende datasets die in een app worden weergegeven
    - Diverse gebruikersprofielen kunnen binnen aparte tabs worden weergegeven
    - Verschillende profielen in een gebruikersbeheersysteem

3. **Module-selectie**: Een `TabbedPane` kan verschillende modules of secties vertegenwoordigen. Elke tab kan de functionaliteiten van een specifieke module encapsuleren, waardoor gebruikers zich op één aspect van de app tegelijk kunnen concentreren.

4. **Taakbeheer**: Taakbeheertoepassingen kunnen een `TabbedPane` gebruiken om verschillende projecten of taken weer te geven. Elke tab kan overeenkomen met een specifiek project, zodat gebruikers de taken afzonderlijk kunnen beheren en volgen.

5. **Programmanavigatie**: Binnen een app die verschillende programma's moet uitvoeren, kan een `TabbedPane`:
    - Dien als een zijbalk waarmee verschillende apps of programma's binnen een enkele app kunnen worden uitgevoerd, zoals wat wordt getoond in de [`AppLayout`](./app-layout.md) template
    - Een bovenbalk creëren die een soortgelijk doel kan dienen, of sub-applicaties binnen een reeds geselecteerde app kan vertegenwoordigen.

## Tabs <DocChip chip='name' label="dwc-tab" /> {#tabs}

Tabs zijn UI-elementen die kunnen worden toegevoegd aan tabbladen om verschillende weergaven van inhoud te organiseren en tussen hen te schakelen.

:::important
Tabs zijn niet bedoeld om als zelfstandige componenten te worden gebruikt. Ze zijn bedoeld om samen met tabbladen te worden gebruikt. Deze klasse is geen `Component` en mag niet als zodanig worden gebruikt.
:::

### Eigenschappen {#properties}

Tabs zijn samengesteld uit de volgende eigenschappen, die worden gebruikt bij het toevoegen aan een `TabbedPane`. Deze eigenschappen hebben getters en setters om aanpassing binnen een `TabbedPane` te vergemakkelijken.

1. **Tekst(`String`)**: De tekst die wordt weergegeven als een titel voor de `Tab` binnen de `TabbedPane`. Dit wordt ook wel de titel genoemd via de `getTitle()` en `setTitle(String title)` methoden.

2. **Tooltip(`String`)**: De tooltiptekst die geassocieerd is met de `Tab`, die wordt weergegeven wanneer de cursor over de `Tab` zweeft.

3. **Ingeschakeld(`boolean`)**: Vertegenwoordigt of de `Tab` is ingeschakeld of niet. Kan worden gewijzigd met de `setEnabled(boolean enabled)` methode.

4. **Sluitbaar(`boolean`)**: Vertegenwoordigt of de `Tab` kan worden gesloten. Kan worden gewijzigd met de `setClosable(boolean closable)` methode. Dit voegt een sluitknop toe aan de `Tab` die kan worden aangeklikt en een verwijderingsgebeurtenis activeert. De `TabbedPane` component bepaalt hoe om te gaan met de verwijdering.

5. **Slot(`Component`)**:
    Slots bieden flexibele opties voor het verbeteren van de functionaliteit van een `Tab`. Je kunt pictogrammen, labels, laadspinners, wissen/reset mogelijkheden, avatar/profielafbeeldingen en andere nuttige componenten binnen een `Tab` nestelen om de bedoeling verder te verduidelijken voor gebruikers.
    Je kunt een component toevoegen aan de `prefix` slot van een `Tab` tijdens de constructie. Alternatief kun je de methoden `setPrefixComponent()` en `setSuffixComponent()` gebruiken om verschillende componenten voor en na de weergegeven optie binnen een `Tab` in te voegen.

        ```java
        TabbedPane pane = new TabbedPane();
        pane.addTab(new Tab("Documenten", TablerIcon.create("files")));
        ```

## `Tab` manipulatie {#tab-manipulation}

Diverse methoden bestaan om ontwikkelaars in staat te stellen tabbladen toe te voegen, in te voegen, te verwijderen en verschillende eigenschappen van `Tab`-elementen binnen de `TabbedPane` te manipuleren.

### Een `Tab` toevoegen {#adding-a-tab}

De methoden `addTab()` en `add()` bestaan in verschillende overbelastingscapaciteiten om ontwikkelaars flexibiliteit te bieden bij het toevoegen van nieuwe tabs aan de `TabbedPane`. Het toevoegen van een `Tab` plaatst deze na eerder bestaande tabs.

1. **`addTab(String text)`**: voegt een `Tab` toe aan de `TabbedPane` met de opgegeven `String` als de tekst van de `Tab`.
2. **`addTab(Tab tab)`**: voegt de `Tab` die als parameter is opgegeven toe aan de `TabbedPane`.
3. **`addTab(String text, Component component)`**: voegt een `Tab` toe met de gegeven `String` als de tekst van de `Tab`, en de opgegeven `Component` die wordt weergegeven in het inhoudsgedeelte van de `TabbedPane`.
4. **`addTab(Tab tab, Component component)`**: voegt de opgegeven `Tab` toe en toont de opgegeven `Component` in het inhoudsgedeelte van de `TabbedPane`.
5. **`add(Component... component)`**: voegt een of meer instanties van `Component` toe aan de `TabbedPane`, waarbij voor elk een afzonderlijke `Tab` wordt gecreëerd, waarbij de tekst wordt ingesteld op de naam van de `Component`.

:::info
De `add(Component... component)` methode bepaalt de naam van de doorgegeven `Component` door `component.getName()` aan te roepen op het doorgegeven argument.
:::

### Een `Tab` invoegen {#inserting-a-tab}

Naast het toevoegen van een `Tab` aan het einde van de bestaande tabs, is het ook mogelijk om een nieuwe in te voegen op een aangewezen positie. Om dit te doen, zijn er meerdere overbelaste versies van de `insertTab()` methode.

1. **`insertTab(int index, String text)`**: voegt een `Tab` in de `TabbedPane` in op de gegeven index met de opgegeven `String` als de tekst van de `Tab`.
2. **`insertTab(int index, Tab tab)`**: voegt de `Tab` die als parameter is opgegeven toe aan de `TabbedPane` op de gespecificeerde index.
3. **`insertTab(int index, String text, Component component)`**: voegt een `Tab` in met de gegeven `String` als de tekst van de `Tab`, en de opgegeven `Component` die wordt weergegeven in het inhoudsgedeelte van de `TabbedPane`.
4. **`insertTab(int index, Tab tab, Component component)`**: voegt de opgegeven `Tab` in en toont de opgegeven `Component` in het inhoudsgedeelte van de `TabbedPane`.

### Een `Tab` verwijderen {#removing-a-tab}

Om een enkele `Tab` uit de `TabbedPane` te verwijderen, gebruik een van de volgende methoden:

1. **`removeTab(Tab tab)`**: verwijdert een `Tab` uit de `TabbedPane` door de Tab-instantie door te geven die moet worden verwijderd.
2. **`removeTab(int index)`**: verwijdert een `Tab` uit de `TabbedPane` door de index van de te verwijderen `Tab` op te geven.

Naast de twee bovenstaande methoden voor het verwijderen van een enkele `Tab`, gebruik de **`removeAllTabs()`** methode om de `TabbedPane` van alle tabs te verwijderen.

:::info
De `remove()` en `removeAll()` methoden verwijderen geen tabs binnen de component.
:::

### Tab/Component associatie {#tabcomponent-association}

Om de `Component` die voor een gegeven `Tab` moet worden weergegeven te wijzigen, roep de `setComponentFor()` methode aan en geef of de instantie van de `Tab`, of de index van die Tab binnen de `TabbedPane` door.

:::info
Als deze methode wordt gebruikt op een `Tab` die al is geassocieerd met een `Component`, wordt de eerder geassocieerde `Component` vernietigd.
:::

## Configuratie en lay-out {#configuration-and-layout}

De `TabbedPane` klasse heeft twee samenstellende delen: een `Tab` die op een specifieke locatie wordt weergegeven, en een component die moet worden weergegeven. Dit kan een enkele component of een [`Composite`](/docs/building-ui/composing-components) component zijn, waardoor het weergeven van complexere componenten binnen een tab-inhoud sectie mogelijk is.

### Vegen {#swiping}

De `TabbedPane` ondersteunt navigeren door de verschillende tabs via vegen. Dit is ideaal voor een mobiele app, maar kan ook worden geconfigureerd via een ingebouwde methode om muisvegen te ondersteunen. Zowel vegen als muisvegen zijn standaard uitgeschakeld, maar kunnen worden ingeschakeld met de methoden `setSwipeable(boolean)` en `setSwipeWithMouse(boolean)`.

### Tab plaatsing {#tab-placement}

De `Tabs` binnen een `TabbedPane` kunnen in verschillende posities binnen de component worden geplaatst, afhankelijk van de voorkeur van de app-ontwikkelaar. De aangeboden opties worden ingesteld met behulp van de verstrekte enum, die de waarden `TOP`, `BOTTOM`, `LEFT`, `RIGHT`, of `HIDDEN` heeft. De standaardinstelling is `TOP`.

<ComponentDemo
path='/webforj/tabbedpaneplacement'
files={['src/main/java/com/webforj/samples/views/tabbedpane/TabbedPanePlacementView.java']}
height='400px'
/>

### Uitlijning {#alignment}

Naast het veranderen van de plaatsing van de `Tab` elementen binnen de `TabbedPane`, is het ook mogelijk om te configureren hoe de tabs zullen uitlijnen binnen de component. Standaard is de instelling `AUTO` van kracht, wat de plaatsing van de tabs hun uitlijning laat dicteren.

De andere opties zijn `START`, `END`, `CENTER`, en `STRETCH`. De eerste drie beschrijven de positie ten opzichte van de component, waarbij `STRETCH` de tabs het beschikbare ruimte laat vullen.

<ComponentDemo
path='/webforj/tabbedpanealignment'
files={['src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneAlignmentView.java']}
height='250px'
/>

### Rand en activiteitindicator {#border-and-activity-indicator}

De `TabbedPane` zal standaard een rand voor de tabs weergeven, afhankelijk van welke `Placement` is ingesteld. Deze rand helpt om de ruimte die de verschillende tabs binnen het paneel innemen te visualiseren.

Wanneer een `Tab` wordt aangeklikt, wordt standaard een activiteitindicator weergegeven nabij die `Tab` om te helpen benadrukken welke de momenteel geselecteerde `Tab` is.

Beide opties kunnen worden aangepast door de booleaanse waarden te wijzigen met behulp van de juiste setter-methoden. Om te veranderen of de rand wordt weergegeven, kan de `setBorderless(boolean)` methode worden gebruikt, waarbij `true` de rand verbergt, en `false`, de standaardwaarde, de rand weergeeft.

:::info
Deze rand geldt niet voor de gehele `TabbedPane` component, en dient slechts als een scheiding tussen de tabs en de inhoud van de component.
:::

Om de zichtbaarheid van de actieve indicator in te stellen, kan de `setHideActiveIndicator(boolean)` methode worden gebruikt. Door `true` aan deze methode door te geven, wordt de actieve indicator onder een actieve `Tab` verborgen, terwijl `false`, de standaardwaarde, de indicator zichtbaar houdt.

<ComponentDemo
path='/webforj/tabbedpaneborder'
files={['src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneBorderView.java']}
height='300px'
/>

### Activatiemodi {#activation-modes}

Voor meer gedetailleerde controle over hoe de `TabbedPane` zich gedraagt met toetsenbordnavigatie, kan de `Activation` modus worden ingesteld om te specificeren hoe de component zich moet gedragen.

- **`Auto`**: wanneer ingesteld op automatisch, zal navigeren tussen tabs met de pijltjestoetsen direct de bijbehorende tabcomponent tonen.

- **`Manual`**: wanneer ingesteld op handmatig, ontvangt de tab focus, maar wordt niet weergegeven totdat de gebruiker op spatie of enter drukt.

<ComponentDemo
path='/webforj/tabbedpaneactivation'
files={['src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneActivationView.java']}
height='250px'
/>

### Verwijderopties {#removal-options}

Individuele `Tab` elementen kunnen worden ingesteld als sluitbaar. Sluitbare tabs zullen een sluitknop aan de tab hebben, die een sluitgebeurtenis activeert wanneer erop wordt geklikt. De `TabbedPane` bepaalt hoe dit gedrag wordt verwerkt.

- **`Manual`**: standaard is de verwijdering ingesteld op `MANUAL`, wat betekent dat de gebeurtenis wordt geactiveerd, maar het aan de ontwikkelaar is om deze gebeurtenis op de manier te verwerken die ze kiezen.

- **`Auto`**: als alternatief kan `AUTO` worden gebruikt, wat de gebeurtenis activeert en ook de `Tab` uit de component verwijdert voor de ontwikkelaar, waardoor de behoefte aan de ontwikkelaar om deze functionaliteit handmatig te implementeren wordt weggenomen.

### Segmentcontrole <DocChip chip='since' label='26.00' /> {#segment-control}

De `TabbedPane` kan worden weergegeven als een segmentcontrole door de `segment` eigenschap in te schakelen met `setSegment(true)`. In deze modus worden tabs weergegeven met een schuivende pilindicator die de actieve selectie benadrukt, en biedt een compact alternatief voor de standaard tab-interface.

<ComponentDemo
path='/webforj/tabbedpanesegment'
files={['src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneSegmentView.java']}
height='250px'
/>

## Stijl {#styling}

### Ruimte en thema {#expanse-and-theme}

De `TabbedPane` komt met ingebouwde `Expanse` en `Theme` opties vergelijkbaar met andere webforJ componenten. Deze kunnen worden gebruikt om snel styling toe te voegen die verschillende betekenissen overbrengt aan de eindgebruiker zonder dat de component met CSS hoeft te worden gestyled.

<ComponentDemo
path='/webforj/tabbedpaneexpansetheme'
files={['src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneExpanseThemeView.java']}
height='250px'
/>

<TableBuilder name={['Tab', 'TabbedPane']} />

## Best practices {#best-practices}

De volgende praktijken worden aanbevolen voor het gebruik van de `TabbedPane` binnen applicaties:

- **Logische Groepering**: Gebruik tabs om verwante inhoud logisch te groeperen:
    - Elke tab moet een duidelijke categorie of functionaliteit binnen je app vertegenwoordigen.
    - Groepeer vergelijkbare of logische tabs dicht bij elkaar.

- **Beperkte Tabs**: Vermijd het overweldigen van gebruikers met te veel tabs. Overweeg het gebruik van een hiërarchische structuur of andere navigatiepatronen waar toepasbaar voor een schone interface.

- **Duidelijke Labels**: Label je Tabs duidelijk voor intuïtief gebruik:
    - Geef duidelijke en beknopte labels voor elke tab.
    - Labels moeten de inhoud of het doel reflecteren, waardoor het gemakkelijk is voor gebruikers om te begrijpen.
    - Gebruik pictogrammen en onderscheidende kleuren waar toepasbaar.

- **Toetsenbordnavigatie**: Gebruik de toetsenbordnavigatie-ondersteuning van webforJ's `TabbedPane` om de interactie met de `TabbedPane` naadlozer en intuïtiever te maken voor de eindgebruiker.

- **Standaard Tab**: Als de standaard tab niet aan het begin van de `TabbedPane` is geplaatst, overweeg dan om deze tab in te stellen als standaard voor essentiële of veelgebruikte informatie.
