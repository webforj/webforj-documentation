---
title: TabbedPane
slug: tabbedpane
sidebar_position: 125
sidebar_class_name: new-content
_i18n_hash: 0b623c02434c6f0d140de0ade3a22c5d
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-tabbed-pane" />
<DocChip chip='since' label='23.06' />
<JavadocLink type="foundation" location="com/webforj/component/tabbedpane/TabbedPane" top='true'/>

Meerdere secties van inhoud kunnen worden georganiseerd onder een enkele `TabbedPane`, waarbij elke sectie is verbonden met een klikbare `Tab`. Slechts één sectie is tegelijkertijd zichtbaar, en tabbladen kunnen tekst, pictogrammen of beide weergeven om gebruikers te helpen tussen hen te navigeren.

<!-- INTRO_END -->

## Gebruik {#usages}

De `TabbedPane`-klasse biedt ontwikkelaars een krachtig hulpmiddel voor het organiseren en presenteren van meerdere tabbladen of secties binnen een gebruikersinterface. Hier zijn enkele typische scenario's waarin je een `TabbedPane` in je applicatie zou kunnen gebruiken:

1. **Documentviewer**: Het implementeren van een documentviewer waarbij elk tabblad een ander document of bestand vertegenwoordigt. Gebruikers kunnen eenvoudig schakelen tussen open documenten voor efficiënte multitasking.

2. **Gegevensbeheer:** Gebruik een `TabbedPane` om gegevensbeheertaken te organiseren, bijvoorbeeld:
    >- Verschillende datasets die in een applicatie worden weergegeven
    >- Diverse gebruikersprofielen kunnen binnen afzonderlijke tabbladen worden weergegeven
    >- Verschillende profielen in een gebruikersbeheersysteem

3. **Module-selectie**: Een `TabbedPane` kan verschillende modules of secties vertegenwoordigen. Elk tabblad kan de functionaliteiten van een specifieke module encapsuleren, zodat gebruikers zich op één aspect van de applicatie tegelijkertijd kunnen concentreren.

4. **Taakbeheer**: Taakbeheertoepassingen kunnen een `TabbedPane` gebruiken om verschillende projecten of taken weer te geven. Elk tabblad zou kunnen overeenkomen met een specifiek project, waardoor gebruikers taken afzonderlijk kunnen beheren en volgen.

5. **Programmanavigatie**: Binnen een applicatie die verschillende programma's moet uitvoeren, kan een `TabbedPane`:
    >- Dienen als een zijbalk waarmee verschillende applicaties of programma's binnen een enkele applicatie kunnen worden uitgevoerd, zoals getoond in de [`AppLayout`](./app-layout.md) sjabloon
    >- Een bovenbalk maken die een vergelijkbaar doel kan dienen, of sub-applicaties binnen een reeds geselecteerde applicatie vertegenwoordigen.

## Tabs {#tabs}

Tabbladen zijn UI-elementen die aan tabbed panes kunnen worden toegevoegd om verschillende contentweergaven te organiseren en tussen hen te schakelen.

:::important
Tabs zijn niet bedoeld om als zelfstandige componenten te worden gebruikt. Ze zijn bedoeld om in combinatie met tabbed panes te worden gebruikt. Deze klasse is geen `Component` en moet als zodanig niet worden gebruikt.
:::

### Eigenschappen {#properties}

Tabbladen bestaan uit de volgende eigenschappen, die vervolgens worden gebruikt bij het toevoegen ervan in een `TabbedPane`. Deze eigenschappen hebben getters en setters om aanpassingen binnen een `TabbedPane` te vergemakkelijken.

1. **Sleutel(`Object`)**: Vertegenwoordigt de unieke identificator voor de `Tab`.

2. **Tekst(`String`)**: De tekst die als titel voor de `Tab` binnen de `TabbedPane` wordt weergegeven. Dit wordt ook wel de titel genoemd via de `getTitle()` en `setTitle(String title)` methoden.

3. **Tooltip(`String`)**: De tooltip-tekst die aan de `Tab` is gekoppeld, die wordt weergegeven wanneer de cursor over de `Tab` beweegt.

4. **Ingeschakeld(`boolean`)**: Vertegenwoordigt of de `Tab` momenteel is ingeschakeld of niet. Kan worden gewijzigd met de `setEnabled(boolean enabled)` methode.

5. **Sluitbaar(`boolean`)**: Vertegenwoordigt of de `Tab` kan worden gesloten. Kan worden gewijzigd met de `setCloseable(boolean enabled)` methode. Dit voegt een sluitknop toe op de `Tab`, die door de gebruiker kan worden aangeklikt, en genereert een verwijderingsgebeurtenis. De `TabbedPane` component bepaalt hoe om te gaan met de verwijdering.

6. **Slot(`Component`)**: 
    Slots bieden flexibele opties voor het verbeteren van de mogelijkheden van een `Tab`. Je kunt pictogrammen, labels, laadspinners, wissen/resetten functionaliteit, avatar/profielafbeeldingen en andere nuttige componenten binnen een `Tab` nestelen om de bedoelde betekenis voor gebruikers verder te verduidelijken.
    Je kunt een component aan de `prefix`-slot van een `Tab` toevoegen tijdens de constructie. Alternatief kun je de `setPrefixComponent()` en `setSuffixComponent()` methoden gebruiken om verschillende componenten voor en na de weergegeven optie binnen een `Tab` in te voegen.

        ```java
        TabbedPane pane = new TabbedPane();
        pane.addTab(new Tab("Documenten", TablerIcon.create("files")));
        ```

## `Tab` manipulatie {#tab-manipulation}

Verschillende methoden bestaan om ontwikkelaars in staat te stellen `Tab`-elementen binnen de `TabbedPane` toe te voegen, in te voegen, te verwijderen en verschillende eigenschappen te manipuleren.

### Een `Tab` toevoegen {#adding-a-tab}

De `addTab()` en `add()` methoden bestaan in verschillende overbelaste capaciteiten om ontwikkelaars flexibiliteit te bieden bij het toevoegen van nieuwe tabs aan de `TabbedPane`. Het toevoegen van een `Tab` plaatst deze na alle eerder bestaande tabs.

1. **`addTab(String text)`** - Voegt een `Tab` toe aan de `TabbedPane` met de opgegeven `String` als tekst van de `Tab`.
2. **`addTab(Tab tab)`** - Voegt de `Tab` toe die als parameter is opgegeven aan de `TabbedPane`.
3. **`addTab(String text, Component component)`** - Voegt een `Tab` toe met de gegeven `String` als tekst van de `Tab`, en de opgegeven `Component` die in de inhoudssectie van de `TabbedPane` wordt weergegeven.
4. **`addTab(Tab tab, Component component)`** - Voegt de opgegeven `Tab` toe en toont de opgegeven `Component` in de inhoudssectie van de `TabbedPane`.
5. **`add(Component... component)`** - Voegt een of meer `Component`-instanties toe aan de `TabbedPane`, waarbij voor elke een afzonderlijk `Tab` wordt gemaakt, met de tekst ingesteld op de naam van de `Component`.

:::info
De `add(Component... component)` bepaalt de naam van de doorgegeven `Component` door `component.getName()` aan te roepen op het doorgegeven argument.
:::

### Een `Tab` invoegen {#inserting-a-tab}

Naast het toevoegen van een `Tab` aan het einde van de bestaande tabs, is het ook mogelijk om er een nieuwe te maken op een aangewezen positie. Hiervoor zijn meerdere overbelaste versies van `insertTab()` beschikbaar.

1. **`insertTab(int index, String text)`** - Voegt een `Tab` toe aan de `TabbedPane` op de aangegeven index met de opgegeven `String` als tekst van de `Tab`.
2. **`insertTab(int index, Tab tab)`** - Voegt de `Tab` die als parameter is opgegeven toe aan de `TabbedPane` op de aangegeven index.
3. **`insertTab(int index, String text, Component component)`** - Voegt een `Tab` toe met de gegeven `String` als tekst van de `Tab`, en de opgegeven `Component` in de inhoudssectie van de `TabbedPane`.
4. **`insertTab(int index, Tab tab, Component component)`** - Voegt de opgegeven `Tab` toe en toont de opgegeven `Component` in de inhoudssectie van de `TabbedPane`.

### Een `Tab` verwijderen {#removing-a-tab}

Om een enkele `Tab` uit de `TabbedPane` te verwijderen, gebruik een van de volgende methoden:

1. **`removeTab(Tab tab)`** - Verwijdert een `Tab` uit de `TabbedPane` door de Tab-instantie door te geven die moet worden verwijderd.
2. **`removeTab(int index)`** - Verwijdert een `Tab` uit de `TabbedPane` door de index van de te verwijderen `Tab` op te geven.

Naast de twee bovenstaande methoden voor het verwijderen van een enkele `Tab`, gebruik de **`removeAllTabs()`** methode om de `TabbedPane` van alle tabs te wissen.

:::info
De `remove()` en `removeAll()` methoden verwijderen geen tabs binnen de component.
:::

### Tab/Component associatie {#tabcomponent-association}

Om de `Component` die voor een bepaalde `Tab` moet worden weergegeven te veranderen, roep je de `setComponentFor()` methode aan en geef je ofwel de instantie van de `Tab` door, of de index van die Tab binnen de `TabbedPane`.

:::info
Als deze methode wordt gebruikt op een `Tab` die al is geassocieerd met een `Component`, wordt de eerder geassocieerde `Component` vernietigd.
:::

## Configuratie en lay-out {#configuration-and-layout}

De `TabbedPane`-klasse heeft twee samenstellende delen: een `Tab` die op een specifieke locatie wordt weergegeven, en een component die wordt weergegeven. Dit kan een enkele component zijn of een [`Composite`](../building-ui/composite-components) component, waarmee complexere componenten binnen de inhoudssectie van een tab kunnen worden weergegeven.

### Swipen {#swiping}

De `TabbedPane` ondersteunt navigeren door de verschillende tabs via swipen. Dit is ideaal voor een mobiele applicatie, maar kan ook worden geconfigureerd via een ingebouwde methode om muis-swipen te ondersteunen. Zowel swipen als muis-swipen zijn standaard uitgeschakeld, maar kunnen worden ingeschakeld met de `setSwipable(boolean)` en `setSwipableWithMouse(boolean)` methoden.

### Tabs plaatsen {#tab-placement}

De `Tabs` binnen een `TabbedPane` kunnen op verschillende posities binnen de component worden geplaatst op basis van de voorkeuren van de applicatieontwikkelaar. Geleverd opties worden ingesteld met behulp van de opgegeven enum, die de waarden `TOP`, `BOTTOM`, `LEFT`, `RIGHT` of `HIDDEN` heeft. De standaardinstelling is `TOP`.

<ComponentDemo
path='/webforj/tabbedpaneplacement'
files={['src/main/java/com/webforj/samples/views/tabbedpane/TabbedPanePlacementView.java']}
height='400px'
/>

### Uitlijning {#alignment}

Naast het veranderen van de plaatsing van de `Tab`-elementen binnen de `TabbedPane`, is het ook mogelijk om te configureren hoe de tabs binnen de component zullen uitlijnen. Standaard is de instelling `AUTO` van kracht, wat de plaatsing van de tabs toestaat om hun uitlijning te dicteren.

De andere opties zijn `START`, `END`, `CENTER`, en `STRETCH`. De eerste drie beschrijven de positie ten opzichte van de component, terwijl `STRETCH` ervoor zorgt dat de tabs de beschikbare ruimte vullen.

<ComponentDemo
path='/webforj/tabbedpanealignment'
files={['src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneAlignmentView.java']}
height='250px'
/>

### Rand en activiteitsindicator {#border-and-activity-indicator}

De `TabbedPane` heeft standaard een rand die wordt weergegeven voor de tabbladen erin, afhankelijk van welke `Placement` is ingesteld. Deze rand helpt om de ruimte te visualiseren die de verschillende tabbladen binnen het paneel innemen.

Wanneer een `Tab` wordt aangeklikt, wordt standaard een activiteitsindicator weergegeven nabij dat `Tab` om te helpen benadrukken welk tabblad momenteel is geselecteerd.

Beide opties kunnen door een ontwikkelaar worden aangepast door de booleaanse waarden te wijzigen met behulp van de geschikte setter-methoden. Om te veranderen of de rand wordt weergegeven, kan de `setBorderless(boolean)` methode worden gebruikt, waarbij `true` de rand verbergt, en `false`, de standaardwaarde, de rand weergeeft.

:::info
Deze rand is niet van toepassing op de geheel `TabbedPane` component, en dient slechts als een scheiding tussen de tabs en de inhoud van de component.
:::

Om de zichtbaarheid van de actieve indicator in te stellen, kan de `setHideActiveIndicator(boolean)` methode worden gebruikt. Het doorgeven van `true` aan deze methode zal de actieve indicator verbergen onder een actieve `Tab`, terwijl `false`, de standaardwaarde, de indicator zichtbaar houdt.

<ComponentDemo
path='/webforj/tabbedpaneborder'
files={['src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneBorderView.java']}
height='300px'
/>

### Activatiemodi {#activation-modes}

Voor meer verfijnde controle over hoe de `TabbedPane` zich gedraagt wanneer deze met het toetsenbord wordt genavigeerd, kan de `Activation`-modus worden ingesteld om op te geven hoe de component zich moet gedragen.

- **`Auto`**: Wanneer ingesteld op automatisch, zal navigeren door tabs met de pijltoetsen onmiddellijk de bijbehorende tabcomponent weergeven.

- **`Handmatig`**: Wanneer ingesteld op handmatig, krijgt het tabblad focus maar wordt het niet weergegeven totdat de gebruiker op spatie of enter drukt.

<ComponentDemo
path='/webforj/tabbedpaneactivation'
files={['src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneActivationView.java']}
height='250px'
/>

### Verwijderopties {#removal-options}

Individuele `Tab`-elementen kunnen zo worden ingesteld dat ze sluitbaar zijn. Sluitbare tabbladen krijgen een sluitknop toegevoegd aan het tabblad, die een sluit gebeurtenis genereert wanneer erop wordt geklikt. De `TabbedPane` bepaalt hoe dit gedrag wordt afgehandeld.

- **`Handmatig`**: Standaard is verwijderen ingesteld op `MANUAL`, wat betekent dat de gebeurtenis wordt gegenereerd, maar het is aan de ontwikkelaar om deze gebeurtenis op welke manier dan ook af te handelen.

- **`Automatisch`**: Alternatief kan `AUTO` worden gebruikt, wat de gebeurtenis zal genereren en ook de `Tab` van de component zal verwijderen voor de ontwikkelaar, waardoor de behoefte aan de ontwikkelaar om dit gedrag handmatig te implementeren komt te vervallen.

### Segmentcontrole <DocChip chip='since' label='26.00' /> {#segment-control}

De `TabbedPane` kan worden weergegeven als een segmentcontrole door de `segment` property in te schakelen met `setSegment(true)`. In deze modus worden tabbladen weergegeven met een schuifindicatie die de actieve selectie benadrukt, wat een compact alternatief biedt voor de standaard tab-interface.

<ComponentDemo
path='/webforj/tabbedpanesegment'
files={['src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneSegmentView.java']}
height='250px'
/>

## Stijl {#styling}

### Uitbreiding en thema {#expanse-and-theme}

De `TabbedPane` wordt geleverd met ingebouwde `Expanse` en `Thema` opties, vergelijkbaar met andere webforJ-componenten. Deze kunnen worden gebruikt om snel styling toe te voegen die verschillende betekenissen aan de eindgebruiker overbrengt zonder dat de component met CSS hoeft te worden gestyled.

<ComponentDemo
path='/webforj/tabbedpaneexpansetheme'
files={['src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneExpanseThemeView.java']}
height='250px'
/>

<TableBuilder name={['Tab', 'TabbedPane']} />

## Beste werkwijzen {#best-practices}

De volgende praktijken worden aanbevolen voor het gebruiken van de `TabbedPane` binnen applicaties:

- **Logische groepsvorming**: Gebruik tabs om gerelateerde inhoud logisch te groeperen
    >- Elk tabblad moet een afzonderlijke categorie of functionaliteit binnen je applicatie vertegenwoordigen
    >- Groepeer vergelijkbare of logische tabbladen dicht bij elkaar

- **Beperkte tabs**: Vermijd het overweldigen van gebruikers met te veel tabs. Overweeg het gebruik van een hiërarchische structuur of andere navigatiepatronen waar van toepassing voor een schone interface

- **Duidelijke labels**: Label je Tabs duidelijk voor intuïtief gebruik
    >- Zorg voor duidelijke en beknopte labels voor elk tabblad
    >- Labels moeten de inhoud of het doel weerspiegelen, zodat het voor gebruikers gemakkelijk te begrijpen is
    >- Maak gebruik van pictogrammen en onderscheidende kleuren waar van toepassing

- **Toetsenbordnavigatie**: Gebruik webforJ's `TabbedPane` toetsenbordnavigatie-ondersteuning om interactie met de `TabbedPane` soepeler en intuïtiever te maken voor de eindgebruiker

- **Standaardtab**: Als de standaardtab niet aan het begin van de `TabbedPane` is geplaatst, overweeg dan om dit tabblad als standaard in te stellen voor essentiële of veelgebruikte informatie.
