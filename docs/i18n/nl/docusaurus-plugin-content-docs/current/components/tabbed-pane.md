---
title: TabbedPane
slug: tabbedpane
sidebar_position: 125
description: >-
  Organize content into switchable Tab sections with the TabbedPane component,
  supporting icons, keys, and customizable tab properties.
_i18n_hash: 563f9251b928e2e9426d69d4b5192880
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-tabbed-pane" />
<DocChip chip='since' label='23.06' />
<JavadocLink type="foundation" location="com/webforj/component/tabbedpane/TabbedPane" top='true'/>

Meerdere secties inhoud kunnen worden georganiseerd onder een enkele `TabbedPane`, waarbij elke sectie is gekoppeld aan een klikbare `Tab`. Slechts één sectie is tegelijkertijd zichtbaar, en tabs kunnen tekst, pictogrammen of beide weergeven om gebruikers te helpen tussen hen te navigeren.

<!-- INTRO_END -->

## Usages {#usages}

De `TabbedPane` klasse biedt ontwikkelaars een krachtig hulpmiddel voor het organiseren en presenteren van meerdere tabs of secties binnen een gebruikersinterface. Hier zijn enkele typische scenario's waarin je een `TabbedPane` in je applicatie zou kunnen gebruiken:

1. **Documentviewer**: Implementeren van een documentviewer waarbij elke tab een ander document of bestand vertegenwoordigt. Gebruikers kunnen eenvoudig schakelen tussen open documenten voor efficiënte multitasking.

2. **Gegevensbeheer:**: Gebruik een `TabbedPane` om gegevensbeheertaken te organiseren, bijvoorbeeld:
    >- Verschillende datasets die in een applicatie moeten worden weergegeven
    >- Diverse gebruikersprofielen kunnen binnen aparte tabs worden weergegeven
    >- Verschillende profielen in een gebruikersbeheersysteem

3. **Moduleselectie**: Een `TabbedPane` kan verschillende modules of secties vertegenwoordigen. Elke tab kan de functionaliteiten van een specifieke module omsluiten, waardoor gebruikers zich op één aspect van de applicatie tegelijk kunnen concentreren.

4. **Takenbeheer**: Takenbeheerapplicaties kunnen een `TabbedPane` gebruiken om verschillende projecten of taken weer te geven. Elke tab kan overeenkomen met een specifiek project, waardoor gebruikers taken afzonderlijk kunnen beheren en volgen.

5. **Programmanavigatie**: Binnen een applicatie die verschillende programma's moet uitvoeren, kan een `TabbedPane`:
    >- Dienen als een zijbalk waarmee verschillende applicaties of programma's binnen een enkele applicatie kunnen worden uitgevoerd, zoals wat wordt weergegeven in de [`AppLayout`](./app-layout.md) template
    >- Een bovenste balk creëren die een soortgelijk doel kan dienen, of sub-applicaties binnen een reeds geselecteerde applicatie kan vertegenwoordigen.

## Tabs {#tabs}

Tabs zijn UI-elementen die aan tabbladen kunnen worden toegevoegd om verschillende inhoudsweergaven te organiseren en tussen hen te schakelen.

:::important
Tabs zijn niet bedoeld om als zelfstandige componenten te worden gebruikt. Ze zijn bedoeld om in combinatie met tabbladen te worden gebruikt. Deze klasse is geen `Component` en mag niet als zodanig worden gebruikt.
:::

### Properties {#properties}

Tabs bestaan uit de volgende eigenschappen, die worden gebruikt bij het toevoegen aan een `TabbedPane`. Deze eigenschappen hebben getters en setters om aanpassing binnen een `TabbedPane` te vergemakkelijken.

1. **Key(`Object`)**: Vertegenwoordigt de unieke identificatie voor de `Tab`.

2. **Text(`String`)**: De tekst die wordt weergegeven als een titel voor de `Tab` binnen de `TabbedPane`. Dit wordt ook wel de titel genoemd via de `getTitle()` en `setTitle(String title)` methoden.

3. **Tooltip(`String`)**: De tooltiptekst die is gekoppeld aan de `Tab`, die wordt weergegeven wanneer de cursor boven de `Tab` zweeft.

4. **Enabled(`boolean`)**: Vertegenwoordigt of de `Tab` momenteel is ingeschakeld of niet. Kan worden gewijzigd met de `setEnabled(boolean enabled)` methode.

5. **Closeable(`boolean`)**: Vertegenwoordigt of de `Tab` kan worden gesloten. Kan worden gewijzigd met de `setCloseable(boolean enabled)` methode. Dit zal een sluitknop op de `Tab` toevoegen die door de gebruiker kan worden aangeklikt, en verzendt een verwijderingsgebeurtenis. De `TabbedPane` component bepaalt hoe de verwijdering moet worden afgehandeld.

6. **Slot(`Component`)**:
   Slots bieden flexibele opties voor het verbeteren van de mogelijkheden van een `Tab`. Je kunt pictogrammen, labels, laadspinners, wissen/resetmogelijkheden, avatar/profielafbeeldingen en andere nuttige componenten binnen een `Tab` nestelen om de beoogde betekenis voor gebruikers verder te verduidelijken.
   Je kunt een component toevoegen aan de `prefix` slot van een `Tab` tijdens de constructie. Alternatief kun je de `setPrefixComponent()` en `setSuffixComponent()` methoden gebruiken om verschillende componenten voor en na de weergegeven optie binnen een `Tab` in te voegen.

        ```java
        TabbedPane pane = new TabbedPane();
        pane.addTab(new Tab("Documents", TablerIcon.create("files")));
        ```

## `Tab` manipulatie {#tab-manipulation}

Er zijn verschillende methoden beschikbaar om ontwikkelaars in staat te stellen tabs toe te voegen, in te voegen, te verwijderen en verschillende eigenschappen van `Tab` elementen binnen de `TabbedPane` te manipuleren.

### Een `Tab` toevoegen {#adding-a-tab}

De `addTab()` en `add()` methoden zijn beschikbaar in verschillende overloaded capaciteiten om ontwikkelaars flexibiliteit te bieden bij het toevoegen van nieuwe tabs aan de `TabbedPane`. Een `Tab` toevoegen plaatst het na alle eerder bestaande tabs.

1. **`addTab(String text)`** - Voegt een `Tab` toe aan de `TabbedPane` met de opgegeven `String` als de tekst van de `Tab`.
2. **`addTab(Tab tab)`** - Voegt de `Tab` die als parameter is gegeven toe aan de `TabbedPane`.
3. **`addTab(String text, Component component)`** - Voegt een `Tab` toe met de gegeven `String` als de tekst van de `Tab`, en de opgegeven `Component` weergegeven in het inhoudssectie van de `TabbedPane`.
4. **`addTab(Tab tab, Component component)`** - Voegt de opgegeven `Tab` toe en toont de opgegeven `Component` in het inhoudssectie van de `TabbedPane`.
5. **`add(Component... component)`** - Voegt een of meer `Component` instanties toe aan de `TabbedPane`, waarbij een aparte `Tab` voor elke wordt gemaakt, met de tekst ingesteld op de naam van de `Component`.

:::info
De `add(Component... component)` bepaalt de naam van de doorgegeven `Component` door `component.getName()` aan te roepen op het doorgegeven argument.
:::

### Een `Tab` invoegen {#inserting-a-tab}

Naast het toevoegen van een `Tab` aan het einde van de bestaande tabs, is het ook mogelijk om een nieuwe tab op een aangewezen positie te creëren. Om dit te doen, zijn er meerdere overloaded versies van de `insertTab()`.

1. **`insertTab(int index, String text)`** - Voegt een `Tab` toe aan de `TabbedPane` op de opgegeven index met de opgegeven `String` als de tekst van de `Tab`.
2. **`insertTab(int index, Tab tab)`** - Voegt de `Tab` die als parameter is gegeven toe aan de `TabbedPane` op de opgegeven index.
3. **`insertTab(int index, String text, Component component)`** - Voegt een `Tab` toe met de opgegeven `String` als de tekst van de `Tab`, en de opgegeven `Component` weergegeven in het inhoudssectie van de `TabbedPane`.
4. **`insertTab(int index, Tab tab, Component component)`** - Voegt de opgegeven `Tab` toe en toont de opgegeven `Component` in het inhoudssectie van de `TabbedPane`.

### Een `Tab` verwijderen {#removing-a-tab}

Om een enkele `Tab` uit de `TabbedPane` te verwijderen, gebruik je een van de volgende methoden:

1. **`removeTab(Tab tab)`** - Verwijdert een `Tab` uit de `TabbedPane` door de Tab-instantie die moet worden verwijderd door te geven.
2. **`removeTab(int index)`** - Verwijdert een `Tab` uit de `TabbedPane` door de index van de `Tab` die moet worden verwijderd op te geven.

Naast de twee bovenstaande methoden voor het verwijderen van een enkele `Tab`, gebruik de **`removeAllTabs()`** methode om de `TabbedPane` van alle tabs te ontdoen.

:::info
De `remove()` en `removeAll()` methoden verwijderen geen tabs binnen de component.
:::

### Tab/Component associatie {#tabcomponent-association}

Om de `Component` te wijzigen die voor een gegeven `Tab` moet worden weergegeven, roep je de `setComponentFor()` methode aan en geef je ofwel de instantie van de `Tab`, of de index van die Tab binnen de `TabbedPane` door.

:::info
Als deze methode wordt gebruikt op een `Tab` die al is gekoppeld aan een `Component`, wordt de eerder gekoppelde `Component` vernietigd.
:::

## Configuratie en lay-out {#configuration-and-layout}

De `TabbedPane` klasse heeft twee samenstellende delen: een `Tab` die op een opgegeven locatie wordt weergegeven, en een component die moet worden weergegeven. Dit kan een enkele component zijn, of een [`Composite`](/docs/building-ui/composing-components) component, die de weergave van complexere componenten binnen de inhoudssectie van een tab mogelijk maakt.

### Swiping {#swiping}

De `TabbedPane` ondersteunt navigatie door verschillende tabs via swiping. Dit is ideaal voor een mobiele applicatie, maar kan ook worden geconfigureerd via een ingebouwde methode om muis-swipe te ondersteunen. Zowel swiping als muis-swipe zijn standaard uitgeschakeld, maar kunnen worden ingeschakeld met de `setSwipable(boolean)` en `setSwipableWithMouse(boolean)` methoden.

### Tabplaatsing {#tab-placement}

De `Tabs` binnen een `TabbedPane` kunnen op verschillende posities binnen de component worden geplaatst op basis van de voorkeur van de applicatie-ontwikkelaars. De beschikbare opties worden ingesteld met behulp van de beschikbare enum, die de waarden `TOP`, `BOTTOM`, `LEFT`, `RIGHT`, of `HIDDEN` heeft. De standaardinstelling is `TOP`.

<ComponentDemo
path='/webforj/tabbedpaneplacement'
files={['src/main/java/com/webforj/samples/views/tabbedpane/TabbedPanePlacementView.java']}
height='400px'
/>

### Uitlijning {#alignment}

Naast het wijzigen van de plaatsing van de `Tab` elementen binnen de `TabbedPane`, is het ook mogelijk om te configureren hoe de tabs binnen de component worden uitgelijnd. Standaard is de instelling `AUTO` van kracht, wat de plaatsing van de tabs toestaat om hun uitlijning te dictateren.

De andere opties zijn `START`, `END`, `CENTER`, en `STRETCH`. De eerste drie beschrijven de positie ten opzichte van de component, terwijl `STRETCH` de tabs de beschikbare ruimte laat vullen.

<ComponentDemo
path='/webforj/tabbedpanealignment'
files={['src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneAlignmentView.java']}
height='250px'
/>

### Rand en activiteitindicator {#border-and-activity-indicator}

De `TabbedPane` heeft standaard een rand die voor de tabs wordt weergegeven, afhankelijk van welke `Placement` is ingesteld. Deze rand helpt om de ruimte die de verschillende tabs binnen het paneel innemen te visualiseren.

Wanneer een `Tab` wordt aangeklikt, wordt er standaard een activiteitindicator weergegeven nabij die `Tab` om te helpen benadrukken welke de momenteel geselecteerde `Tab` is.

Beide opties kunnen door een ontwikkelaar worden aangepast door de booleaanse waarden te wijzigen met behulp van de juiste setter-methoden. Om te wijzigen of de rand zichtbaar is, kan de `setBorderless(boolean)` methode worden gebruikt, waarbij `true` de rand verbergt, en `false`, de standaardwaarde, de rand weergeeft.

:::info
Deze rand is niet van toepassing op de gehele `TabbedPane` component, en dient slechts als een scheiding tussen de tabs en de inhoud van de component.
:::

Om de zichtbaarheid van de actieve indicator in te stellen, kan de `setHideActiveIndicator(boolean)` methode worden gebruikt. Het doorgeven van `true` aan deze methode zal de actieve indicator onder een actieve `Tab` verbergen, terwijl `false`, de standaard, de indicator zichtbaar houdt.

<ComponentDemo
path='/webforj/tabbedpaneborder'
files={['src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneBorderView.java']}
height='300px'
/>

### Activatiemodi {#activation-modes}

Voor meer gedetailleerde controle over hoe de `TabbedPane` zich gedraagt wanneer deze via het toetsenbord wordt genavigeerd, kan de `Activation` modus worden ingesteld om te specificeren hoe de component zich moet gedragen.

- **`Auto`**: Wanneer ingesteld op automatisch, zal navigeren tussen tabs met de pijltjestoetsen onmiddellijk de bijbehorende tabcomponent tonen.

- **`Manual`**: Wanneer ingesteld op handmatig, zal de tab focus krijgen maar niet worden weergegeven totdat de gebruiker op spatie of enter drukt.

<ComponentDemo
path='/webforj/tabbedpaneactivation'
files={['src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneActivationView.java']}
height='250px'
/>

### Verwijderopties {#removal-options}

Individuele `Tab` elementen kunnen als sluitbaar worden ingesteld. Sluitbare tabs zullen een sluitknop aan de tab hebben, wat een sluitgebeurtenis afvuurt wanneer erop wordt geklikt. De `TabbedPane` bepaalt hoe dit gedrag wordt afgehandeld.

- **`Manual`**: Standaard is verwijderen ingesteld op `MANUAL`, wat betekent dat de gebeurtenis wordt afgevuurd, maar het is aan de ontwikkelaar om deze gebeurtenis op de door hem gekozen manier af te handelen.

- **`Auto`**: Alternatief kan `AUTO` worden gebruikt, wat de gebeurtenis afvuurt, en ook de `Tab` uit de component voor de ontwikkelaar verwijdert, waardoor de ontwikkelaar deze gedraging handmatig hoeft te implementeren.

### Segmentbesturing <DocChip chip='since' label='26.00' /> {#segment-control}

De `TabbedPane` kan worden weergegeven als een segmentcontrole door de `segment` eigenschap in te schakelen met `setSegment(true)`. In deze modus worden tabs weergegeven met een glijdende pillindicatie die de actieve selectie benadrukt, wat een compacte alternatieve optie biedt voor de standaard tab-interface.

<ComponentDemo
path='/webforj/tabbedpanesegment'
files={['src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneSegmentView.java']}
height='250px'
/>

## Styling {#styling}

### Expansie en thema {#expanse-and-theme}

De `TabbedPane` wordt geleverd met ingebouwde `Expanse` en `Thema` opties die vergelijkbaar zijn met andere webforJ componenten. Deze kunnen worden gebruikt om snel styling toe te voegen die verschillende betekenissen overbrengt aan de eindgebruiker zonder de component met CSS te stylen.

<ComponentDemo
path='/webforj/tabbedpaneexpansetheme'
files={['src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneExpanseThemeView.java']}
height='250px'
/>

<TableBuilder name={['Tab', 'TabbedPane']} />

## Beste praktijken {#best-practices}

De volgende praktijken worden aanbevolen voor het gebruik van de `TabbedPane` binnen applicaties:

- **Logische Groepering**: Gebruik tabs om gerelateerde inhoud logisch te groeperen
    >- Elke tab moet een duidelijke categorie of functionaliteit binnen je applicatie vertegenwoordigen
    >- Groepeer vergelijkbare of logische tabs dicht bij elkaar

- **Beperkte Tabs**: Vermijd het overweldigen van gebruikers met te veel tabs. Overweeg waar van toepassing een hiërarchische structuur of andere navigatiepatronen te gebruiken voor een schone interface

- **Duidelijke Labels**: Label je Tabs duidelijk voor intuïtief gebruik
    >- Geef duidelijke en beknopte labels voor elke tab
    >- Labels moeten de inhoud of het doel weerspiegelen, zodat gebruikers het gemakkelijk begrijpen
    >- Maak waar nodig gebruik van pictogrammen en onderscheidende kleuren

- **Toetsenbordnavigatie** Gebruik de toetsenbordnavigatieondersteuning van webforJ's `TabbedPane` om interactie met de `TabbedPane` naadlozer en intuïtiever te maken voor de eindgebruiker

- **Standaard Tab**: Als de standaard tab niet aan het begin van de `TabbedPane` is geplaatst, overweeg dan om deze tab als standaard in te stellen voor essentiële of vaak gebruikte informatie.
