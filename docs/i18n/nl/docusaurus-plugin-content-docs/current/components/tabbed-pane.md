---
title: TabbedPane
slug: tabbedpane
sidebar_position: 125
sidebar_class_name: new-content
description: >-
  Organize content into switchable Tab sections with the TabbedPane component,
  supporting icons, keys, and customizable tab properties.
_i18n_hash: 9a348db865b5ea1688eb09c4f6f75a57
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-tabbed-pane" />
<DocChip chip='since' label='23.06' />
<JavadocLink type="foundation" location="com/webforj/component/tabbedpane/TabbedPane" top='true'/>

Meerdere secties van inhoud kunnen worden georganiseerd onder een enkele `TabbedPane`, waar elke sectie is gekoppeld aan een klikbare `Tab`. Slechts één sectie is op een gegeven moment zichtbaar, en tabs kunnen tekst, iconen of beide weergeven om gebruikers te helpen tussen hen te navigeren.

<!-- INTRO_END -->

## Gebruik {#usages}

De `TabbedPane`-klasse biedt ontwikkelaars een krachtig hulpmiddel voor het organiseren en presenteren van meerdere tabs of secties binnen een UI. Hier zijn enkele typische scenario's waarin je een `TabbedPane` in je toepassing zou kunnen gebruiken:

1. **Documentviewer**: Implementatie van een documentviewer waarbij elke tab een ander document of bestand vertegenwoordigt. Gebruikers kunnen eenvoudig schakelen tussen open documenten voor efficiënte multitasking.

2. **Gegevensbeheer:**: Gebruik een `TabbedPane` om gegevensbeheertaken te organiseren, bijvoorbeeld:
    >- Verschillende datasets die in een toepassing moeten worden weergegeven
    >- Diverse gebruikersprofielen kunnen binnen aparte tabs worden weergegeven
    >- Verschillende profielen in een gebruikersbeheersysteem

3. **Modulekeuze**: Een `TabbedPane` kan verschillende modules of secties vertegenwoordigen. Elke tab kan de functionaliteiten van een specifieke module omvatten, zodat gebruikers zich op één aspect van de toepassing tegelijk kunnen concentreren.

4. **Taakbeheer**: Toepassingen voor taakbeheer kunnen een `TabbedPane` gebruiken om verschillende projecten of taken weer te geven. Elke tab kan overeenkomen met een specifiek project, zodat gebruikers taken afzonderlijk kunnen beheren en volgen.

5. **Programmanavigatie**: Binnen een toepassing die verschillende programma's moet uitvoeren, kan een `TabbedPane`:
    >- Dienen als een zijbalk die het mogelijk maakt verschillende toepassingen of programma's binnen een enkele toepassing uit te voeren, zoals wat wordt getoond in de [`AppLayout`](./app-layout.md) sjabloon
    >- Een bovenste balk creëren die een soortgelijk doel kan dienen, of sub-toepassingen binnen een al geselecteerde toepassing kan vertegenwoordigen.
  
## Tabs {#tabs}

Tabs zijn UI-elementen die kunnen worden toegevoegd aan tabbed panes om verschillende inhoudsweergaven te organiseren en te schakelen.

:::important
Tabs zijn niet bedoeld om te worden gebruikt als standalone componenten. Ze zijn bedoeld om in combinatie met tabbed panes te worden gebruikt. Deze klasse is geen `Component` en moet niet als zodanig worden gebruikt.
:::

### Eigenschappen {#properties}

Tabs bestaan uit de volgende eigenschappen, die vervolgens worden gebruikt bij het toevoegen in een `TabbedPane`. Deze eigenschappen hebben getters en setters om aanpassing binnen een `TabbedPane` te vergemakkelijken.

1. **Sleutel(`Object`)**: Vertegenwoordigt de unieke identificatie voor de `Tab`.

2. **Tekst(`String`)**: De tekst die als titel voor de `Tab` binnen de `TabbedPane` wordt weergegeven. Dit wordt ook wel de titel genoemd via de `getTitle()` en `setTitle(String title)` methoden.

3. **Tooltip(`String`)**: De tooltip-tekst die is gekoppeld aan de `Tab`, die wordt weergegeven wanneer de cursor over de `Tab` zweeft.

4. **Ingeschakeld(`boolean`)**: Vertegenwoordigt of de `Tab` momenteel is ingeschakeld of niet. Kan worden aangepast met de `setEnabled(boolean enabled)` methode.

5. **Sluitbaar(`boolean`)**: Vertegenwoordigt of de `Tab` kan worden gesloten. Kan worden aangepast met de `setCloseable(boolean enabled)` methode. Dit voegt een sluitknop op de `Tab` toe die door de gebruiker kan worden aangeklikt en een verwijderingsgebeurtenis activeert. De `TabbedPane`-component dicteert hoe met de verwijdering moet worden omgegaan.

6. **Slot(`Component`)**: 
    Slots bieden flexibele opties om de functionaliteit van een `Tab` te verbeteren. Je kunt iconen, labels, laadspinners, wissen/resetten mogelijkheden, avatar/profielafbeeldingen en andere nuttige componenten binnen een `Tab` nestelen om de bedoelde betekenis voor gebruikers verder te verduidelijken.
    Je kunt een component aan de `prefix`-slot van een `Tab` toevoegen tijdens de constructie. Alternatief kun je de `setPrefixComponent()` en `setSuffixComponent()` methoden gebruiken om verschillende componenten vóór en na de weergegeven optie binnen een `Tab` in te voegen.

        ```java
        TabbedPane pane = new TabbedPane();
        pane.addTab(new Tab("Documenten", TablerIcon.create("files")));
        ```

## `Tab` manipulatie {#tab-manipulation}

Verschillende methoden bestaan om ontwikkelaars toe te staan `Tab`-elementen binnen de `TabbedPane` toe te voegen, in te voegen, te verwijderen en verschillende eigenschappen ervan te manipuleren.

### Een `Tab` toevoegen {#adding-a-tab}

De `addTab()` en `add()` methoden bestaan in verschillende overbelaste capaciteiten om ontwikkelaars flexibiliteit te bieden bij het toevoegen van nieuwe tabs aan de `TabbedPane`. Een `Tab` toevoegen plaatst deze na alle eerder bestaande tabs.

1. **`addTab(String text)`** - Voegt een `Tab` toe aan de `TabbedPane` met de opgegeven `String` als de tekst van de `Tab`.
2. **`addTab(Tab tab)`** - Voegt de `Tab` die als parameter is opgegeven toe aan de `TabbedPane`.
3. **`addTab(String text, Component component)`** - Voegt een `Tab` toe met de gegeven `String` als de tekst van de `Tab`, en de opgegeven `Component` weergegeven in de inhoudsectie van de `TabbedPane`.
4. **`addTab(Tab tab, Component component)`** - Voegt de opgegeven `Tab` toe en toont de opgegeven `Component` in de inhoudsectie van de `TabbedPane`.
5. **`add(Component... component)`** - Voegt één of meerdere instances van `Component` toe aan de `TabbedPane`, waarbij voor elke één een discrete `Tab` wordt gemaakt, met de tekst die is ingesteld op de naam van de `Component`.

:::info
De `add(Component... component)` bepaalt de naam van de doorgegeven `Component` door de `component.getName()` aan te roepen op het doorgegeven argument.
:::

### Een `Tab` invoegen {#inserting-a-tab}

Naast het toevoegen van een `Tab` aan het einde van de bestaande tabs, is het ook mogelijk om een nieuwe in te voegen op een aangewezen positie. Om dit te doen, zijn er meerdere overbelaste versies van de `insertTab()`.

1. **`insertTab(int index, String text)`** - Voegt een `Tab` in de `TabbedPane` in de aangegeven index toe met de opgegeven `String` als de tekst van de `Tab`.
2. **`insertTab(int index, Tab tab)`** - Voegt de `Tab` die als parameter is opgegeven in de `TabbedPane` in op de opgegeven index.
3. **`insertTab(int index, String text, Component component)`** - Voegt een `Tab` met de gegeven `String` als de tekst van de `Tab` in en toont de opgegeven `Component` in de inhoudsectie van de `TabbedPane`.
4. **`insertTab(int index, Tab tab, Component component)`** - Voegt de opgegeven `Tab` in en toont de opgegeven `Component` in de inhoudsectie van de `TabbedPane`.

### Een `Tab` verwijderen {#removing-a-tab}

Om een enkele `Tab` uit de `TabbedPane` te verwijderen, gebruik je een van de volgende methoden:

1. **`removeTab(Tab tab)`** - Verwijdert een `Tab` uit de `TabbedPane` door de Tab-instantie die moet worden verwijderd door te geven.
2. **`removeTab(int index)`** - Verwijdert een `Tab` uit de `TabbedPane` door de index van de te verwijderen `Tab` op te geven.

Naast de twee bovenstaande methoden voor het verwijderen van een enkele `Tab`, kan de **`removeAllTabs()`** methode worden gebruikt om de `TabbedPane` van alle tabs te wissen.

:::info
De `remove()` en `removeAll()` methoden verwijderen geen tabs binnen de component.
:::

### Tab/Component associatie {#tabcomponent-association}

Om de `Component` te wijzigen die voor een bepaalde `Tab` moet worden weergegeven, roep je de `setComponentFor()` methode aan en geef je ofwel de instantie van de `Tab` of de index van die Tab binnen de `TabbedPane`.

:::info
Als deze methode wordt gebruikt op een `Tab` die al is gekoppeld aan een `Component`, zal de eerder gekoppelde `Component` worden vernietigd.
:::

## Configuratie en lay-out {#configuration-and-layout}

De `TabbedPane`-klasse heeft twee samenstellende delen: een `Tab` die op een specifieke locatie wordt weergegeven, en een component die moet worden weergegeven. Dit kan een enkele component zijn, of een [`Composite`](/docs/building-ui/composing-components) component, wat de weergave van complexere componenten binnen de inhoudsectie van een tab mogelijk maakt.

### Veegbeweging {#swiping}

De `TabbedPane` ondersteunt navigatie door de verschillende tabs via veegbeweging. Dit is ideaal voor een mobiele applicatie, maar kan ook worden geconfigureerd via een ingebouwde methode om muisvegen te ondersteunen. Zowel veegbeweging als muisvegen zijn standaard uitgeschakeld, maar kunnen worden ingeschakeld met de `setSwipable(boolean)` en `setSwipableWithMouse(boolean)` methoden, respectievelijk. 

### Tabplaatsing {#tab-placement}

De `Tabs` binnen een `TabbedPane` kunnen op verschillende posities binnen de component worden geplaatst op basis van de voorkeur van de toepassingsontwikkelaar. Gegeven opties worden ingesteld met behulp van de beschikbare enum, die de waarden `TOP`, `BOTTOM`, `LEFT`, `RIGHT` of `HIDDEN` heeft. De standaardinstelling is `TOP`.

<ComponentDemo
path='/webforj/tabbedpaneplacement'
files={['src/main/java/com/webforj/samples/views/tabbedpane/TabbedPanePlacementView.java']}
height='400px'
/>

### Uitlijning {#alignment}

Naast het wijzigen van de plaatsing van de `Tab`-elementen binnen de `TabbedPane`, is het ook mogelijk om te configureren hoe de tabs zullen uitlijnen binnen de component. Standaard is de instelling `AUTO` van kracht, wat de plaatsing van de tabs de uitlijning laat dicteren.

De andere opties zijn `START`, `END`, `CENTER` en `STRETCH`. De eerste drie beschrijven de positie ten opzichte van de component, waarbij `STRETCH` de tabs laat vullen wat beschikbare ruimte betreft.

<ComponentDemo
path='/webforj/tabbedpanealignment'
files={['src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneAlignmentView.java']}
height='250px'
/>

### Rand en activiteitindicator {#border-and-activity-indicator}

De `TabbedPane` heeft standaard een rand die voor de tabs binnenin wordt weergegeven, afhankelijk van welke `Placement` is ingesteld. Deze rand helpt om de ruimte te visualiseren die de verschillende tabs binnen het paneel innemen.

Wanneer op een `Tab` wordt geklikt, wordt er standaard een activiteitindicator weergegeven bij die `Tab` om te helpen benadrukken welke de momenteel geselecteerde `Tab` is.

Beide opties kunnen door een ontwikkelaar worden aangepast door de booleaanse waarden te wijzigen met de juiste settermethoden. Om te wijzigen of de rand wordt weergegeven, kan de `setBorderless(boolean)` methode worden gebruikt, waarbij `true` de rand verbergt en `false`, de standaardwaarde, de rand weergeeft.

:::info
Deze rand geldt niet voor de gehele `TabbedPane`-component en dient slechts als een scheider tussen de tabs en de inhoud van de component.
:::

Om de zichtbaarheid van de actieve indicator in te stellen, kan de `setHideActiveIndicator(boolean)` methode worden gebruikt. Het doorgeven van `true` aan deze methode verbergt de actieve indicator onder een actieve `Tab`, terwijl `false`, de standaard, de indicator zichtbaar houdt.

<ComponentDemo
path='/webforj/tabbedpaneborder'
files={['src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneBorderView.java']}
height='300px'
/>

### Activatiemodi {#activation-modes}

Voor meer gedetailleerde controle over hoe de `TabbedPane` zich gedraagt wanneer deze via het toetsenbord wordt genavigeerd, kan de `Activation`-modus worden ingesteld om te specificeren hoe de component zich moet gedragen.

- **`Auto`**: Wanneer ingesteld op automatisch, worden tabs met de pijltjestoetsen onmiddellijk de bijbehorende tabcomponent weergegeven.

- **`Handmatig`**: Wanneer ingesteld op handmatig, krijgt de tab focus maar wordt deze pas weergegeven wanneer de gebruiker spatie of enter indrukt.

<ComponentDemo
path='/webforj/tabbedpaneactivation'
files={['src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneActivationView.java']}
height='250px'
/>

### Verwijderingsopties {#removal-options}

Individuele `Tab`-elementen kunnen instelbaar worden gemaakt om te sluiten. Sluitbare tabs hebben een sluitknop toegevoegd aan de tab, die een sluitgebeurtenis activeert wanneer erop wordt geklikt. De `TabbedPane` dicteert hoe dit gedrag wordt afgehandeld.

- **`Handmatig`**: Standaard is de verwijdering ingesteld op `MANUAL`, wat betekent dat de gebeurtenis wordt geactiveerd, maar het is aan de ontwikkelaar om deze gebeurtenis op welke manier dan ook af te handelen.

- **`Auto`**: Alternatief kan `AUTO` worden gebruikt, wat de gebeurtenis activeert en ook de `Tab` uit de component verwijdert voor de ontwikkelaar, waardoor de noodzaak voor de ontwikkelaar om dit gedrag handmatig te implementeren, vervalt. 

### Segmentcontrole <DocChip chip='since' label='26.00' /> {#segment-control}

De `TabbedPane` kan worden weergegeven als een segmentcontrole door de `segment`-eigenschap in te schakelen met `setSegment(true)`. In deze modus worden tabs weergegeven met een schuifindicator die de actieve selectie benadrukt, wat een compacte alternatieve manier biedt voor de standaard tab-interface.

<ComponentDemo
path='/webforj/tabbedpanesegment'
files={['src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneSegmentView.java']}
height='250px'
/>

## Stijlen {#styling}

### Uitbreiding en thema {#expanse-and-theme}

De `TabbedPane` komt met ingebouwde `Expanse` en `Thema` opties die vergelijkbaar zijn met andere webforJ-componenten. Deze kunnen worden gebruikt om snel stijlen toe te voegen die verschillende betekenissen voor de eindgebruiker overbrengen zonder de component met CSS te hoeven stylen.

<ComponentDemo
path='/webforj/tabbedpaneexpansetheme'
files={['src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneExpanseThemeView.java']}
height='250px'
/>

<TableBuilder name={['Tab', 'TabbedPane']} />

## Beste praktijken {#best-practices}

De volgende praktijken worden aanbevolen voor het gebruik van de `TabbedPane` binnen toepassingen:

- **Logische Groepering**: Gebruik tabs om gerelateerde inhoud logisch te groeperen
    >- Elke tab moet een afzonderlijke categorie of functionaliteit binnen je toepassing vertegenwoordigen
    >- Groepeer vergelijkbare of logische tabs nabij elkaar

- **Beperkte Tabs**: Vermijd het overweldigen van gebruikers met te veel tabs. Overweeg om een hiërarchische structuur of andere navigatiepatronen te gebruiken waar van toepassing voor een schone interface.

- **Duidelijke Labels**: Label je Tabs duidelijk voor intuïtief gebruik
    >- Geef duidelijke en beknopte labels voor elke tab
    >- Labels moeten de inhoud of het doel weergeven, zodat het voor gebruikers gemakkelijk te begrijpen is
    >- Gebruik iconen en onderscheidende kleuren waar van toepassing

- **Toetsenbordnavigatie**: Gebruik de toetsenbordnavigatie-ondersteuning van webforJ's `TabbedPane` om de interactie met de `TabbedPane` naadlozer en intuïtiever te maken voor de eindgebruiker.

- **Standaard Tab**: Als de standaard tab niet aan het begin van de `TabbedPane` is geplaatst, overweeg dan om deze tab in te stellen als standaard voor essentiële of veelgebruikte informatie.
