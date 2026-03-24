---
title: TabbedPane
slug: tabbedpane
sidebar_position: 125
_i18n_hash: d1522c6bd692420a02df494fa0509a23
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-tabbed-pane" />
<DocChip chip='since' label='23.06' />
<JavadocLink type="foundation" location="com/webforj/component/tabbedpane/TabbedPane" top='true'/>

Meerdere secties van inhoud kunnen worden georganiseerd onder een enkele `TabbedPane`, waarbij elke sectie is gekoppeld aan een klikbare `Tab`. Slechts één sectie is zichtbaar tegelijk, en tabs kunnen tekst, pictogrammen of beide weergeven om gebruikers te helpen tussen hen te navigeren.

<!-- INTRO_END -->

## Usages {#usages}

De `TabbedPane`-klasse biedt ontwikkelaars een krachtig hulpmiddel voor het organiseren en presenteren van meerdere tabs of secties binnen een UI. Hier zijn enkele typische scenario's waarin je een `TabbedPane` in je applicatie kunt gebruiken:

1. **Documentviewer**: Implementatie van een documentviewer waar elke tab een ander document of bestand vertegenwoordigt. Gebruikers kunnen eenvoudig schakelen tussen geopende documenten voor efficiënte multitasking.

2. **Gegevensbeheer**: Gebruik een `TabbedPane` om taken voor gegevensbeheer te organiseren, bijvoorbeeld:
    >- Verschillende dataset die in een applicatie moeten worden weergegeven
    >- Diverse gebruikersprofielen kunnen binnen aparte tabs worden weergegeven
    >- Verschillende profielen in een gebruikersbeheersysteem

3. **Module Selectie**: Een `TabbedPane` kan verschillende modules of secties vertegenwoordigen. Elke tab kan de functionaliteiten van een specifieke module omvatten, zodat gebruikers zich op één aspect van de applicatie tegelijk kunnen concentreren.

4. **Taakbeheer**: Taakbeheertoepassingen kunnen een `TabbedPane` gebruiken om verschillende projecten of taken weer te geven. Elke tab kan overeenkomen met een specifiek project, zodat gebruikers taken afzonderlijk kunnen beheren en volgen.

5. **Programmabeheer**: Binnen een applicatie die verschillende programma's moet uitvoeren, kan een `TabbedPane`:
    >- Fungeren als een zijbalk die het mogelijk maakt verschillende applicaties of programma's binnen een enkele applicatie uit te voeren, zoals wat wordt getoond in de [`AppLayout`](./app-layout.md) sjabloon
    >- Een bovenbalk creëren die een vergelijkbaar doel kan dienen, of sub-applicaties binnen een reeds geselecteerde applicatie kan vertegenwoordigen.
  
## Tabs {#tabs}

Tabs zijn UI-elementen die aan tabbladen kunnen worden toegevoegd om verschillende inhoudsweergaven te organiseren en te schakelen.

:::important
Tabs zijn niet bedoeld om als zelfstandige componenten te worden gebruikt. Ze zijn bedoeld om in combinatie met tabbladen te worden gebruikt. Deze klasse is geen `Component` en mag niet als zodanig worden gebruikt.
:::

### Eigenschappen {#properties}

Tabs zijn samengesteld uit de volgende eigenschappen, die vervolgens worden gebruikt bij het toevoegen aan een `TabbedPane`. Deze eigenschappen hebben getters en setters om aanpassing binnen een `TabbedPane` te vergemakkelijken.

1. **Sleutel(`Object`)**: Vertegenwoordigt de unieke identificatie voor de `Tab`.

2. **Tekst(`String`)**: De tekst die wordt weergegeven als titel voor de `Tab` binnen de `TabbedPane`. Dit wordt ook wel de titel genoemd via de `getTitle()` en `setTitle(String title)` methoden.

3. **Tooltip(`String`)**: De tooltip-tekst die is gekoppeld aan de `Tab`, die wordt weergegeven wanneer de cursor boven de `Tab` zweeft.

4. **Ingeschakeld(`boolean`)**: Vertegenwoordigt of de `Tab` momenteel ingeschakeld is of niet. Kan worden gewijzigd met de `setEnabled(boolean enabled)` methode.

5. **Sluitbaar(`boolean`)**: Vertegenwoordigt of de `Tab` kan worden gesloten. Kan worden gewijzigd met de `setCloseable(boolean enabled)` methode. Dit voegt een sluitknop toe op de `Tab` die door de gebruiker kan worden aangeklikt, en genereert een verwijderingsgebeurtenis. De `TabbedPane`-component bepaalt hoe om te gaan met de verwijdering.

6. **Slot(`Component`)**: 
    Slots bieden flexibele opties voor het verbeteren van de functionaliteit van een `Tab`. Je kunt pictogrammen, labels, laadspinners, opruim-/resetmogelijkheden, avatar/profielafbeeldingen en andere nuttige componenten binnen een `Tab` nestelen om verder duidelijk te maken wat de bedoeling is voor gebruikers. 
    Je kunt een component toevoegen aan de `prefix`-slot van een `Tab` tijdens de constructie. Alternatief kun je de `setPrefixComponent()` en `setSuffixComponent()` methoden gebruiken om verschillende componenten voor en na de weergegeven optie binnen een `Tab` in te voegen.

        ```java
        TabbedPane pane = new TabbedPane();
        pane.addTab(new Tab("Documenten", TablerIcon.create("bestanden")));
        ```

## `Tab` manipulatie {#tab-manipulation}

Er zijn verschillende methoden beschikbaar waarmee ontwikkelaars tabs kunnen toevoegen, invoegen, verwijderen en manipuleren binnen de `TabbedPane`.

### Een `Tab` toevoegen {#adding-a-tab}

De `addTab()` en `add()` methoden bestaan in verschillende overbelaste mogelijkheden om ontwikkelaars flexibiliteit te bieden bij het toevoegen van nieuwe tabs aan de `TabbedPane`. Het toevoegen van een `Tab` plaatst deze na alle eerder bestaande tabs.

1. **`addTab(String tekst)`** - Voegt een `Tab` toe aan de `TabbedPane` met de opgegeven `String` als de tekst van de `Tab`.
2. **`addTab(Tab tab)`** - Voegt de `Tab` die als parameter is opgegeven toe aan de `TabbedPane`.
3. **`addTab(String tekst, Component component)`** - Voegt een `Tab` toe met de gegeven `String` als de tekst van de `Tab`, en de opgegeven `Component` die wordt weergegeven in de inhoudssectie van de `TabbedPane`.
4. **`addTab(Tab tab, Component component)`** - Voegt de opgegeven `Tab` toe en geeft de opgegeven `Component` weer in de inhoudssectie van de `TabbedPane`.
5. **`add(Component... component)`** - Voegt een of meer `Component`-instanties toe aan de `TabbedPane`, waarbij voor elke één een afzonderlijke `Tab` wordt gemaakt, met de tekst ingesteld op de naam van de `Component`.

:::info
De `add(Component... component)` bepaalt de naam van de doorgegeven `Component` door de `component.getName()` op de doorgegeven parameter aan te roepen.
:::

### Een `Tab` invoegen {#inserting-a-tab}

Naast het toevoegen van een `Tab` aan het einde van de bestaande tabs, is het ook mogelijk om een nieuwe op een aangewezen positie te creëren. Om dit te doen, zijn er meerdere overbelaste versies van de `insertTab()`.

1. **`insertTab(int index, String tekst)`** - Voegt een `Tab` in de `TabbedPane` in op de gegeven index met de opgegeven `String` als de tekst van de `Tab`.
2. **`insertTab(int index, Tab tab)`** - Voegt de `Tab` die als parameter is opgegeven toe aan de `TabbedPane` op de gespecificeerde index.
3. **`insertTab(int index, String tekst, Component component)`** - Voegt een `Tab` in met de gegeven `String` als de tekst van de `Tab`, en de opgegeven `Component` die wordt weergegeven in de inhoudssectie van de `TabbedPane`.
4. **`insertTab(int index, Tab tab, Component component)`** - Voegt de opgegeven `Tab` in en geeft de opgegeven `Component` weer in de inhoudssectie van de `TabbedPane`.

### Een `Tab` verwijderen {#removing-a-tab}

Om een enkele `Tab` uit de `TabbedPane` te verwijderen, gebruik een van de volgende methoden:

1. **`removeTab(Tab tab)`** - Verwijdert een `Tab` uit de `TabbedPane` door de Tab-instantie door te geven die moet worden verwijderd.
2. **`removeTab(int index)`** - Verwijdert een `Tab` uit de `TabbedPane` door de index van de te verwijderen `Tab` op te geven.

Naast de twee bovenstaande methoden voor het verwijderen van een enkele `Tab`, gebruik de **`removeAllTabs()`** methode om de `TabbedPane` van alle tabs te ontdoen.

:::info
De `remove()` en `removeAll()` methoden verwijderen geen tabs binnen de component.
:::

### Tab/Component associatie {#tabcomponent-association}

Om de `Component` die voor een gegeven `Tab` moet worden weergegeven te wijzigen, roep de `setComponentFor()` methode aan en geef ofwel de instantie van de `Tab`, of de index van die Tab binnen de `TabbedPane` door.

:::info
Als deze methode wordt gebruikt op een `Tab` die al is gekoppeld aan een `Component`, wordt de eerder gekoppelde `Component` vernietigd.
:::

## Configuratie en lay-out {#configuration-and-layout}

De `TabbedPane`-klasse heeft twee samenstellende delen: een `Tab` die op een specifieke locatie wordt weergegeven, en een component die moet worden weergegeven. Dit kan een enkele component zijn, of een [`Composite`](../building-ui/composite-components) component, waardoor het mogelijk is complexere componenten binnen de inhoudssectie van een tab weer te geven.

### Vegen {#swiping}

De `TabbedPane` ondersteunt navigatie door de verschillende tabs via vegen. Dit is ideaal voor een mobiele applicatie, maar kan ook worden geconfigureerd via een ingebouwde methode om muisvegen te ondersteunen. Zowel vegen als muisvegen zijn standaard uitgeschakeld, maar kunnen worden ingeschakeld met de `setSwipable(boolean)` en `setSwipableWithMouse(boolean)` methoden, respectievelijk. 

### Tabplaatsing {#tab-placement}

De `Tabs` binnen een `TabbedPane` kunnen op verschillende posities binnen de component worden geplaatst op basis van de voorkeur van de applicatieontwikkelaar. Geleverde opties zijn ingesteld met behulp van de beschikbare enum, die de waarden `TOP`, `BOTTOM`, `LEFT`, `RIGHT`, of `HIDDEN` heeft. De standaard instelling is `TOP`.

<ComponentDemo 
path='/webforj/tabbedpaneplacement?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tabbedpane/TabbedPanePlacementView.java'
height="400px"
/>

### Uitlijning {#alignment}

Naast het wijzigen van de plaatsing van de `Tab`-elementen binnen de `TabbedPane`, is het ook mogelijk te configureren hoe de tabs zullen uitlijnen binnen de component. Standaard is de instelling `AUTO` van kracht, wat de plaatsing van de tabs toestaat om hun uitlijning te dicteren.

De andere opties zijn `START`, `END`, `CENTER`, en `STRETCH`. De eerste drie beschrijven de positie ten opzichte van de component, waarbij `STRETCH` de tabs de beschikbare ruimte laat vullen.

<ComponentDemo 
path='/webforj/tabbedpanealignment?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneAlignmentView.java'
height="250px"
/>

### Rand en activiteitsindicator {#border-and-activity-indicator}

De `TabbedPane` zal standaard een rand tonen voor de tabs erin, afhankelijk van welke `Placement` is ingesteld. Deze rand helpt om de ruimte te visualiseren die de verschillende tabs binnen het paneel innemen. 

Wanneer er op een `Tab` wordt geklikt, wordt er standaard een activiteitsindicator weergegeven nabij die `Tab` om te helpen benadrukken welke de momenteel geselecteerde `Tab` is.

Beide opties kunnen door een ontwikkelaar worden gepersonaliseerd door de booleaanse waarden te wijzigen met behulp van de juiste setter-methoden. Om te wijzigen of de rand zichtbaar is, kan de `setBorderless(boolean)` methode worden gebruikt, waarbij `true` de rand verbergt, en `false`, de standaardwaarde, de rand weergeeft.

:::info
Deze rand is niet van toepassing op de gehele `TabbedPane`-component, en dient enkel als een scheiding tussen de tabs en de inhoud van de component.
:::

Om de zichtbaarheid van de actieve indicator in te stellen, kan de `setHideActiveIndicator(boolean)` methode worden gebruikt. Het doorgeven van `true` aan deze methode verbergt de actieve indicator onder een actieve `Tab`, terwijl `false`, de standaardwaarde, de indicator zichtbaar houdt.

<ComponentDemo 
path='/webforj/tabbedpaneborder?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneBorderView.java'
height="300px"
/>

### Activatiemodi {#activation-modes}

Voor meer gedetailleerde controle over hoe de `TabbedPane` zich gedraagt wanneer deze via het toetsenbord wordt genavigeerd, kan de `Activation`-modus worden ingesteld om te specificeren hoe de component moet functioneren.

- **`Auto`**: Wanneer ingesteld op auto, zullen de tabs met de pijltjestoetsen onmiddellijk de bijbehorende tabcomponent tonen.

- **`Manual`**: Wanneer ingesteld op handmatig, zal de tab focus krijgen, maar zal deze niet zichtbaar worden totdat de gebruiker op spatie of enter drukt.

<ComponentDemo 
path='/webforj/tabbedpaneactivation?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneActivationView.java'
height="250px"
/>

### Verwijderopties {#removal-options}

Individuele `Tab`-elementen kunnen zo worden ingesteld dat ze sluitbaar zijn. Sluitbare tabs zullen een sluitknop aan de tab hebben, die een sluitgebeurtenis genereert wanneer erop wordt geklikt. De `TabbedPane` bepaalt hoe dit gedrag wordt behandeld.

- **`Manual`**: Standaard is verwijdering ingesteld op `MANUAL`, wat betekent dat de gebeurtenis wordt gegenereerd, maar het aan de ontwikkelaar is om deze gebeurtenis op welke manier dan ook af te handelen.

- **`Auto`**: Alternatief kan `AUTO` worden gebruikt, wat de gebeurtenis zal genereren en ook de `Tab` automatisch uit de component zal verwijderen voor de ontwikkelaar, waardoor het niet nodig is voor de ontwikkelaar om dit gedrag handmatig te implementeren. 

## Styling {#styling}

### Expansie en thema {#expanse-and-theme}

De `TabbedPane` komt met ingebouwde `Expanse`- en `Thema`-opties die vergelijkbaar zijn met andere webforJ-componenten. Deze kunnen worden gebruikt om snel styling toe te voegen die verschillende betekenis aan de eindgebruiker overbrengt zonder dat de component met CSS moet worden gestyled.

<ComponentDemo 
path='/webforj/tabbedpaneexpansetheme?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneExpanseThemeView.java'
height="250px"
/>

<TableBuilder name="TabbedPane" />

## Beste praktijken {#best-practices}

De volgende praktijken worden aanbevolen voor het gebruiken van de `TabbedPane` binnen applicaties:

- **Logische Groepering**: Gebruik tabs om gerelateerde inhoud logisch te groeperen
    >- Elke tab moet een duidelijke categorie of functionaliteit binnen je applicatie vertegenwoordigen
    >- Groepeer vergelijkbare of logische tabs dicht bij elkaar

- **Beperkte Tabs**: Vermijd het overweldigen van gebruikers met te veel tabs. Overweeg om een hiërarchische structuur of andere navigatiepatronen waar toepasbaar te gebruiken voor een schone interface

- **Heldere Labels**: Label je Tabs duidelijk voor intuïtief gebruik
    >- Geef duidelijke en beknopte labels voor elke tab
    >- Labels moeten de inhoud of het doel weerspiegelen, waardoor het voor gebruikers gemakkelijk te begrijpen is
    >- Gebruik pictogrammen en distinctieve kleuren waar mogelijk

- **Toetsenbordnavigatie**: Maak gebruik van de toetsenbordnavigatie ondersteuning van webforJ's `TabbedPane` om de interactie met de `TabbedPane` naadlozer en intuïtiever te maken voor de eindgebruiker

- **Standaard Tab**: Als de standaard tab niet aan het begin van de `TabbedPane` is geplaatst, overweeg dan deze tab als standaard in te stellen voor essentiële of vaak gebruikte informatie.
