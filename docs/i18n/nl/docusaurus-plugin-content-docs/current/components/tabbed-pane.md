---
title: TabbedPane
slug: tabbedpane
sidebar_position: 125
_i18n_hash: 2e67673ef0ac49904be50764ef47ecb0
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-tabbed-pane" />
<DocChip chip='since' label='23.06' />
<JavadocLink type="foundation" location="com/webforj/component/tabbedpane/TabbedPane" top='true'/>

De `TabbedPane` klasse biedt een compacte en georganiseerde manier om inhoud weer te geven die is verdeeld in meerdere secties, elk gekoppeld aan een `Tab`. Gebruikers kunnen tussen deze secties schakelen door op de respectieve tabbladen te klikken, die vaak zijn gelabeld met tekst en/of pictogrammen. Deze klasse vereenvoudigt het maken van veelzijdige interfaces waar verschillende inhoud of formulieren toegankelijk moeten zijn, maar niet tegelijkertijd zichtbaar.

## Toepassingen {#usages}

De `TabbedPane` klasse biedt ontwikkelaars een krachtige tool voor het organiseren en presenteren van meerdere tabs of secties binnen een gebruikersinterface. Hier zijn enkele typische scenario's waarin je een `TabbedPane` in je applicatie zou kunnen gebruiken:

1. **Documentviewer**: Het implementeren van een documentviewer waarbij elk tabblad een ander document of bestand vertegenwoordigt. Gebruikers kunnen eenvoudig schakelen tussen geopende documenten voor efficiënte multitasking.

2. **Gegevensbeheer:**: Gebruik een `TabbedPane` om gegevensbeheertaken te organiseren, bijvoorbeeld:
    >- Verschillende datasets die in een applicatie moeten worden weergegeven
    >- Diverse gebruikersprofielen kunnen binnen afzonderlijke tabbladen worden weergegeven
    >- Verschillende profielen in een gebruikersbeheersysteem

3. **Moduleselectie**: Een `TabbedPane` kan verschillende modules of secties vertegenwoordigen. Elk tabblad kan de functionaliteiten van een specifieke module encapsuleren, waardoor gebruikers zich op één aspect van de applicatie tegelijk kunnen concentreren.

4. **Taakbeheer**: Taakbeheerapplicaties kunnen een `TabbedPane` gebruiken om verschillende projecten of taken weer te geven. Elk tabblad kan corresponderen met een specifiek project, zodat gebruikers taken afzonderlijk kunnen beheren en volgen.

5. **Programmanavigatie**: Binnen een applicatie die verschillende programma's moet uitvoeren, kan een `TabbedPane`:
    >- Fungeren als een zijbalk die verschillende applicaties of programma's binnen een enkele applicatie mogelijk maakt, zoals datgene dat wordt getoond in de [`AppLayout`](./app-layout.md) sjabloon
    >- Een bovenbalk creëren die een soortgelijk doel kan dienen, of sub-applicaties binnen een al geselecteerde applicatie kan vertegenwoordigen.

## Tabs {#tabs}

Tabs zijn UI-elementen die aan tabbladen kunnen worden toegevoegd om verschillende inhoudsweergaven te organiseren en te schakelen.

:::important
Tabs zijn niet bedoeld om als zelfstandige componenten te worden gebruikt. Ze zijn bedoeld om samen met tabbladen te worden gebruikt. Deze klasse is geen `Component` en moet niet als zodanig worden gebruikt.
:::

### Eigenschappen {#properties}

Tabs bestaan uit de volgende eigenschappen, die worden gebruikt bij het toevoegen aan een `TabbedPane`. Deze eigenschappen hebben getters en setters om aanpassing binnen een `TabbedPane` te vergemakkelijken.

1. **Sleutel(`Object`)**: Vertegenwoordigt de unieke identificatie voor de `Tab`.

2. **Tekst(`String`)**: De tekst die als titel voor de `Tab` binnen de `TabbedPane` wordt weergegeven. Dit wordt ook vermeld als de titel via de `getTitle()` en `setTitle(String title)` methoden.

3. **Tooltip(`String`)**: De tooltiptekst die is gekoppeld aan de `Tab`, die wordt weergegeven wanneer de cursor boven de `Tab` zweeft.

4. **Ingeschakeld(`boolean`)**: Vertegenwoordigt of de `Tab` momenteel is ingeschakeld of niet. Kan worden gewijzigd met de `setEnabled(boolean enabled)` methode.

5. **Sluitbaar(`boolean`)**: Vertegenwoordigt of de `Tab` kan worden gesloten. Kan worden gewijzigd met de `setCloseable(boolean enabled)` methode. Dit voegt een sluitknop op de `Tab` toe die door de gebruiker kan worden aangeklikt, en genereert een verwijderingsgebeurtenis. De `TabbedPane` component bepaalt hoe om te gaan met de verwijdering.

6. **Slot(`Component`)**: 
    Slots bieden flexibele opties om de mogelijkheden van een `Tab` te verbeteren. Je kunt pictogrammen, labels, laadspinners, wissen/resetten mogelijkheden, avatar/profielafbeeldingen en andere nuttige componenten binnen een `Tab` nestelen om de beoogde betekenis voor gebruikers verder te verduidelijken.
    Je kunt een component aan de `prefix`-slot van een `Tab` toevoegen tijdens de constructie. Alternatief kun je de methoden `setPrefixComponent()` en `setSuffixComponent()` gebruiken om verschillende componenten voor en na de weergegeven optie binnen een `Tab` in te voegen.

        ```java
        TabbedPane pane = new TabbedPane();
        pane.addTab(new Tab("Documents", TablerIcon.create("files")));
        ```

## `Tab` manipulatie {#tab-manipulation}

Diverse methoden bestaan om ontwikkelaars in staat te stellen om tabs toe te voegen, in te voegen, te verwijderen en verschillende eigenschappen van `Tab` elementen binnen de `TabbedPane` te manipuleren.

### Een `Tab` toevoegen {#adding-a-tab}

De `addTab()` en `add()` methoden bestaan in verschillende overloaded capaciteiten om ontwikkelaars flexibiliteit te bieden bij het toevoegen van nieuwe tabs aan de `TabbedPane`. Een `Tab` toevoegen plaatst deze achter alle eerder bestaande tabs.

1. **`addTab(String text)`** - Voegt een `Tab` toe aan de `TabbedPane` met de opgegeven `String` als de tekst van de `Tab`.
2. **`addTab(Tab tab)`** - Voegt de `Tab` die als parameter is opgegeven toe aan de `TabbedPane`.
3. **`addTab(String text, Component component)`** - Voegt een `Tab` toe met de gegeven `String` als de tekst van de `Tab`, en de verstrekte `Component` die in de inhoudsectie van de `TabbedPane` wordt weergegeven.
4. **`addTab(Tab tab, Component component)`** - Voegt de opgegeven `Tab` toe en geeft de opgegeven `Component` weer in de inhoudsectie van de `TabbedPane`.
5. **`add(Component... component)`** - Voegt een of meer `Component` instanties toe aan de `TabbedPane`, waarbij voor elke een discrete `Tab` wordt gemaakt, met de tekst ingesteld op de naam van de `Component`.

:::info
De `add(Component... component)` bepaalt de naam van de doorgegeven `Component` door `component.getName()` aan te roepen op het doorgegeven argument.
:::

### Een `Tab` invoegen {#inserting-a-tab}

Naast het toevoegen van een `Tab` aan het einde van de bestaande tabs, is het ook mogelijk om een nieuwe in te voegen op een aangewezen positie. Om dit te doen, zijn er meerdere overloaded versies van de `insertTab()`.

1. **`insertTab(int index, String text)`** - Voegt een `Tab` toe aan de `TabbedPane` op de gegeven index met de opgegeven `String` als de tekst van de `Tab`.
2. **`insertTab(int index, Tab tab)`** - Voegt de `Tab` die als parameter is opgegeven toe aan de `TabbedPane` op de opgegeven index.
3. **`insertTab(int index, String text, Component component)`** - Voegt een `Tab` toe met de gegeven `String` als de tekst van de `Tab`, en de opgegeven `Component` die in de inhoudsectie van de `TabbedPane` wordt weergegeven.
4. **`insertTab(int index, Tab tab, Component component)`** - Voegt de opgegeven `Tab` toe en geeft de opgegeven `Component` weer in de inhoudsectie van de `TabbedPane`.

### Een `Tab` verwijderen {#removing-a-tab}

Om een enkele `Tab` uit de `TabbedPane` te verwijderen, gebruik een van de volgende methoden:

1. **`removeTab(Tab tab)`** - Verwijdert een `Tab` uit de `TabbedPane` door het Tab-instantie dat moet worden verwijderd door te geven.
2. **`removeTab(int index)`** - Verwijdert een `Tab` uit de `TabbedPane` door de index van de te verwijderen `Tab` op te geven.

Naast de twee bovenstaande methoden voor het verwijderen van een enkele `Tab`, gebruik de **`removeAllTabs()`** methode om de `TabbedPane` van alle tabs te wissen.

:::info
De `remove()` en `removeAll()` methoden verwijderen geen tabs binnen de component.
:::

### Tab/Component associatie {#tabcomponent-association}

Om de `Component` te wijzigen die voor een bepaalde `Tab` wordt weergegeven, roep de `setComponentFor()` methode aan en geef ofwel de instantie van de `Tab`, of de index van die Tab binnen de `TabbedPane` door.

:::info
Als deze methode wordt gebruikt op een `Tab` die al is gekoppeld aan een `Component`, wordt de eerder gekoppelde `Component` vernietigd.
:::

## Configuratie en lay-out {#configuration-and-layout}

De `TabbedPane` klasse heeft twee samenstellende delen: een `Tab` die op een specifieke locatie wordt weergegeven, en een component die moet worden weergegeven. Dit kan een enkele component zijn, of een [`Composite`](../building-ui/composite-components) component, waarmee het mogelijk is om complexere componenten binnen de inhoudsectie van een tab weer te geven.

### Veegbewegingen {#swiping}

De `TabbedPane` ondersteunt het navigeren door de verschillende tabs via veegbewegingen. Dit is ideaal voor een mobiele applicatie, maar kan ook worden geconfigureerd via een ingebouwde methode om muisvegen te ondersteunen. Zowel veegbewegingen als muisvegen zijn standaard uitgeschakeld, maar kunnen worden ingeschakeld met de methoden `setSwipable(boolean)` en `setSwipableWithMouse(boolean)`, respectievelijk. 

### Tabplaatsing {#tab-placement}

De `Tabs` binnen een `TabbedPane` kunnen op verschillende posities binnen de component worden geplaatst, afhankelijk van de voorkeur van de ontwikkelaar van de applicatie. Biedt opties die worden ingesteld met behulp van de opgegeven enum, die de waarden `TOP`, `BOTTOM`, `LEFT`, `RIGHT`, of `HIDDEN` heeft. De standaardinstelling is `TOP`.

<ComponentDemo 
path='/webforj/tabbedpaneplacement?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tabbedpane/TabbedPanePlacementView.java'
height="400px"
/>

### Uitlijning {#alignment}

Naast het wijzigen van de plaatsing van de `Tab` elementen binnen de `TabbedPane`, is het ook mogelijk om in te stellen hoe de tabs binnen de component uitgelijnd zullen worden. Standaard is de instelling `AUTO` van kracht, wat de plaatsing van de tabs toestaat om hun uitlijning te dicteren.

De andere opties zijn `START`, `END`, `CENTER`, en `STRETCH`. De eerste drie beschrijven de positie ten opzichte van de component, terwijl `STRETCH` de tabs de beschikbare ruimte laat vullen.

<ComponentDemo 
path='/webforj/tabbedpanealignment?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneAlignmentView.java'
height="250px"
/>

### Rand en activiteitindicator {#border-and-activity-indicator}

De `TabbedPane` zal standaard een rand voor de tabs binnenin hebben, afhankelijk van welke `Plaatsing` is ingesteld. Deze rand helpt de ruimte te visualiseren die de verschillende tabs binnen het paneel innemen. 

Wanneer op een `Tab` wordt geklikt, wordt er standaard een activiteitindicator weergegeven nabij die `Tab` om te helpen benadrukken welke de momenteel geselecteerde `Tab` is.

Beide opties kunnen door een ontwikkelaar worden aangepast door de booleaanse waarden te wijzigen met de juiste setter-methoden. Om in te stellen of de rand wel of niet zichtbaar is, kan de `setBorderless(boolean)` methode worden gebruikt, waarbij `true` de rand verbergt en `false`, de standaardwaarde, de rand weergeef.

:::info
Deze rand geldt niet voor het geheel van de `TabbedPane` component, en dient slechts als een scheiding tussen de tabs en de inhoud van de component.
:::

Om de zichtbaarheid van de actieve indicator in te stellen, kan de `setHideActiveIndicator(boolean)` methode worden gebruikt. Het doorgeven van `true` aan deze methode verbergt de actieve indicator onder een actieve `Tab`, terwijl `false`, de standaard, de indicator zichtbaar houdt.

<ComponentDemo 
path='/webforj/tabbedpaneborder?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneBorderView.java'
height="300px"
/>

### Activatiemodi {#activation-modes}

Voor meer gedetailleerde controle over hoe de `TabbedPane` zich gedraagt bij navigatie met het toetsenbord, kan de `Activatie` modus worden ingesteld om te specificeren hoe de component zich moet gedragen.

- **`Auto`**: Wanneer ingesteld op automatisch, zal het navigeren door tabs met de pijltoetsen onmiddellijk de bijbehorende tabcomponent weergeven.

- **`Handmatig`**: Wanneer ingesteld op handmatig, zal het tabblad focus ontvangen, maar niet worden weergegeven totdat de gebruiker op spatie of enter drukt.

<ComponentDemo 
path='/webforj/tabbedpaneactivation?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneActivationView.java'
height="250px"
/>

### Verwijderingsopties {#removal-options}

Individuele `Tab` elementen kunnen closable worden ingesteld. Sluitbare tabs krijgen een sluitknop toegevoegd aan de tab, die een sluitgebeurtenis afvuurt wanneer erop wordt geklikt. De `TabbedPane` bepaalt hoe dit gedrag wordt afgehandeld.

- **`Handmatig`**: Standaard is de verwijdering ingesteld op `MANUAL`, wat betekent dat de gebeurtenis wordt afgevuurd, maar het is aan de ontwikkelaar om deze gebeurtenis op welke manier dan ook af te handelen.

- **`Auto`**: Alternatief kan `AUTO` worden gebruikt, wat de gebeurtenis afvuurt en ook de `Tab` uit de component verwijdert voor de ontwikkelaar, waardoor de ontwikkelaar deze handeling niet handmatig hoeft te implementeren.

## Styling {#styling}

### Uitbreiding en thema {#expanse-and-theme}

De `TabbedPane` komt met ingebouwde `Expanse` en `Thema` opties, vergelijkbaar met andere webforJ componenten. Deze kunnen worden gebruikt om snel styling toe te voegen die verschillende betekenissen overbrengt naar de eindgebruiker zonder dat de component met CSS hoeft te worden gestyled.

<ComponentDemo 
path='/webforj/tabbedpaneexpansetheme?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneExpanseThemeView.java'
height="250px"
/>

<TableBuilder name="TabbedPane" />

## Beste praktijken {#best-practices}

De volgende praktijken worden aanbevolen voor het gebruik van de `TabbedPane` binnen applicaties:

- **Logische Groepering**: Gebruik tabs om gerelateerde inhoud logisch te groeperen
    >- Elk tabblad moet een aparte categorie of functionaliteit binnen je applicatie vertegenwoordigen
    >- Groepeer soortgelijke of logische tabs dicht bij elkaar

- **Beperkte Tabs**: Vermijd gebruikers te overweldigen met te veel tabs. Overweeg het gebruik van een hiërarchische structuur of andere navigatiepatronen waar toepasselijk voor een schone interface

- **Duidelijke Labels**: Label je Tabs duidelijk voor intuïtief gebruik
    >- Bied duidelijke en beknopte labels voor elk tabblad
    >- Labels moeten de inhoud of het doel weerspiegelen, zodat gebruikers het gemakkelijk kunnen begrijpen
    >- Gebruik pictogrammen en verschillende kleuren waar toepasselijk

- **Toetsenbordnavigatie** Gebruik de toetsbordnavigatieondersteuning van webforJ's `TabbedPane` om de interactie met de `TabbedPane` naadlozer en intuïtiever te maken voor de eindgebruiker

- **Standaard Tab**: Als de standaardtab niet aan het begin van de `TabbedPane` is geplaatst, overweeg dan om deze tab in te stellen als standaard voor essentiële of vaak gebruikte informatie.
