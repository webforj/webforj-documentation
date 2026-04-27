---
title: TabbedPane
slug: tabbedpane
sidebar_position: 125
_i18n_hash: f903eeae452aae41b3eb04c170b9e98e
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-tabbed-pane" />
<DocChip chip='since' label='23.06' />
<JavadocLink type="foundation" location="com/webforj/component/tabbedpane/TabbedPane" top='true'/>

Verschillende secties van inhoud kunnen worden georganiseerd onder een enkele `TabbedPane`, waarbij elke sectie is gekoppeld aan een klikbare `Tab`. Slechts één sectie is op een gegeven moment zichtbaar, en tabs kunnen tekst, pictogrammen of beide weergeven om gebruikers te helpen tussen hen te navigeren.

<!-- INTRO_END -->

## Usages {#usages}

De `TabbedPane` klasse biedt ontwikkelaars een krachtig hulpmiddel voor het organiseren en presenteren van meerdere tabs of secties binnen een UI. Hier zijn enkele typische scenario's waarin je een `TabbedPane` in jouw applicatie zou kunnen gebruiken:

1. **Documentviewer**: Het implementeren van een documentviewer waarbij elke tab een ander document of bestand vertegenwoordigt. Gebruikers kunnen eenvoudig schakelen tussen geopende documenten voor efficiënte multitasking.

2. **Gegevensbeheer:**: Gebruik een `TabbedPane` om gegevensbeheertaken te organiseren, bijvoorbeeld:
    >- Verschillende datasets die in een applicatie moeten worden weergegeven
    >- Diverse gebruikersprofielen kunnen binnen aparte tabs worden weergegeven
    >- Verschillende profielen in een gebruikersbeheersysteem

3. **Moduleselectie**: Een `TabbedPane` kan verschillende modules of secties vertegenwoordigen. Elke tab kan de functionaliteiten van een specifieke module omvatten, waardoor gebruikers zich op één aspect van de applicatie tegelijk kunnen concentreren.

4. **Taakbeheer**: Taakbeheertoepassingen kunnen een `TabbedPane` gebruiken om verschillende projecten of taken weer te geven. Elke tab kan overeenkomen met een specifiek project, waardoor gebruikers taken apart kunnen beheren en volgen.

5. **Programmanavigatie**: Binnen een applicatie die verschillende programma's moet uitvoeren, kan een `TabbedPane`:
    >- Dienen als een zijbalk die het mogelijk maakt verschillende applicaties of programma's binnen een enkele applicatie uit te voeren, zoals wat wordt getoond in de [`AppLayout`](./app-layout.md) sjabloon
    >- Een bovenbalk maken die een soortgelijke functie kan hebben, of sub-applicaties binnen een reeds geselecteerde applicatie kan weergeven.

## Tabs {#tabs}

Tabs zijn UI-elementen die kunnen worden toegevoegd aan tabbladen om verschillende inhoudsweergaven te organiseren en te schakelen.

:::important
Tabs zijn niet bedoeld om als zelfstandige componenten te worden gebruikt. Ze zijn bedoeld om in combinatie met tabbladen te worden gebruikt. Deze klasse is geen `Component` en moet niet als zodanig worden gebruikt.
:::

### Eigenschappen {#properties}

Tabs bestaan uit de volgende eigenschappen, die vervolgens worden gebruikt bij het toevoegen ervan in een `TabbedPane`. Deze eigenschappen hebben getters en setters om aanpassing binnen een `TabbedPane` te vergemakkelijken.

1. **Sleutel(`Object`)**: Vertegenwoordigt de unieke identificatie voor de `Tab`.

2. **Tekst(`String`)**: De tekst die wordt weergegeven als een titel voor de `Tab` binnen de `TabbedPane`. Dit wordt ook wel de titel genoemd via de `getTitle()` en `setTitle(String title)` methoden.

3. **Tooltip(`String`)**: De tooltip-tekst die is gekoppeld aan de `Tab`, die wordt weergegeven wanneer de cursor over de `Tab` zweeft.

4. **Ingeschakeld(`boolean`)**: Vertegenwoordigt of de `Tab` momenteel ingeschakeld is of niet. Kan worden gewijzigd met de `setEnabled(boolean enabled)` methode.

5. **Sluitbaar(`boolean`)**: Vertegenwoordigt of de `Tab` kan worden gesloten. Kan worden gewijzigd met de `setCloseable(boolean enabled)` methode. Dit voegt een sluitknop toe op de `Tab`, waarop gebruikers kunnen klikken, wat een verwijderingsevenement genereert. De `TabbedPane` component bepaalt hoe met de verwijdering moet worden omgegaan.

6. **Slot(`Component`)**: 
    Slots bieden flexibele opties om de mogelijkheden van een `Tab` te verbeteren. Je kunt pictogrammen, labels, laadspinners, wissen/resetmogelijkheden, avatar/profielafbeeldingen en andere nuttige componenten binnen een `Tab` plaatsen om de bedoelde betekenis voor gebruikers verder te verduidelijken. 
    Je kunt een component aan de `prefix` slot van een `Tab` toevoegen tijdens de constructie. Alternatief kun je de `setPrefixComponent()` en `setSuffixComponent()` methoden gebruiken om verschillende componenten vóór en na de weergegeven optie binnen een `Tab` in te voegen.

        ```java
        TabbedPane pane = new TabbedPane();
        pane.addTab(new Tab("Documenten", TablerIcon.create("files")));
        ```

## `Tab` manipulatie {#tab-manipulation}

Er bestaan verschillende methoden om ontwikkelaars in staat te stellen tabs toe te voegen, in te voegen, te verwijderen en verschillende eigenschappen van `Tab` elementen binnen de `TabbedPane` te manipuleren.

### Een `Tab` toevoegen {#adding-a-tab}

De `addTab()` en `add()` methoden bestaan in verschillende overbelaste varianten om ontwikkelaars de flexibiliteit te bieden nieuwe tabs aan de `TabbedPane` toe te voegen. Het toevoegen van een `Tab` plaatst deze achter eerder bestaande tabs.

1. **`addTab(String text)`** - Voegt een `Tab` toe aan de `TabbedPane` met de opgegeven `String` als de tekst van de `Tab`.
2. **`addTab(Tab tab)`** - Voegt de `Tab` die als parameter is opgegeven toe aan de `TabbedPane`.
3. **`addTab(String text, Component component)`** - Voegt een `Tab` toe met de gegeven `String` als de tekst van de `Tab`, en de opgegeven `Component` weergegeven in het inhoudsgedeelte van de `TabbedPane`.
4. **`addTab(Tab tab, Component component)`** - Voegt de opgegeven `Tab` toe en toont de opgegeven `Component` in het inhoudsgedeelte van de `TabbedPane`.
5. **`add(Component... component)`** - Voegt een of meer `Component` instanties toe aan de `TabbedPane`, waarbij voor elk een afzonderlijke `Tab` wordt gemaakt, met de tekst ingesteld op de naam van de `Component`.

:::info
De `add(Component... component)` bepaalt de naam van de doorgegeven `Component` door `component.getName()` aan te roepen op de doorgegeven argumenten.
:::

### Een `Tab` invoegen {#inserting-a-tab}

Naast het toevoegen van een `Tab` aan het einde van de bestaande tabs, is het ook mogelijk om er een op een aangewezen positie te maken. Hiervoor zijn verschillende overbelaste versies van de `insertTab()`.

1. **`insertTab(int index, String text)`** - Voegt een `Tab` in de `TabbedPane` in op de gegeven index met de opgegeven `String` als de tekst van de `Tab`.
2. **`insertTab(int index, Tab tab)`** - Voegt de `Tab` die als parameter is opgegeven in de `TabbedPane` in op de gespecificeerde index.
3. **`insertTab(int index, String text, Component component)`** - Voegt een `Tab` in met de gegeven `String` als de tekst van de `Tab`, en de opgegeven `Component` weergegeven in het inhoudsgedeelte van de `TabbedPane`.
4. **`insertTab(int index, Tab tab, Component component)`** - Voegt de opgegeven `Tab` in en toont de opgegeven `Component` in het inhoudsgedeelte van de `TabbedPane`.

### Een `Tab` verwijderen {#removing-a-tab}

Om een enkele `Tab` uit de `TabbedPane` te verwijderen, gebruik een van de volgende methoden:

1. **`removeTab(Tab tab)`** - Verwijdert een `Tab` uit de `TabbedPane` door de Tab-instantie door te geven die moet worden verwijderd.
2. **`removeTab(int index)`** - Verwijdert een `Tab` uit de `TabbedPane` door de index van de `Tab` op te geven die moet worden verwijderd.

Naast de twee bovenstaande methoden om een enkele `Tab` te verwijderen, gebruik de **`removeAllTabs()`** methode om de `TabbedPane` van alle tabs te ontdoen.

:::info
De `remove()` en `removeAll()` methoden verwijderen geen tabs binnen de component.
:::

### Tab/Component associatie {#tabcomponent-association}

Om de `Component` te veranderen die wordt weergegeven voor een gegeven `Tab`, roep de `setComponentFor()` methode aan en geef ofwel de instantie van de `Tab` of de index van die Tab binnen de `TabbedPane` door.

:::info
Als deze methode wordt gebruikt op een `Tab` die al is gekoppeld aan een `Component`, wordt de eerder gekoppelde `Component` vernietigd.
:::

## Configuratie en lay-out {#configuration-and-layout}

De `TabbedPane` klasse heeft twee samenstellende delen: een `Tab` die op een specifieke locatie wordt weergegeven, en een component die moet worden weergegeven. Dit kan een enkele component zijn, of een [`Composite`](../building-ui/composite-components) component, waardoor het mogelijk is complexere componenten binnen een tab's inhoudsgedeelte weer te geven.

### Veegbeweging {#swiping}

De `TabbedPane` ondersteunt navigatie door verschillende tabs via veegbewegingen. Dit is ideaal voor een mobiele applicatie, maar kan ook worden geconfigureerd via een ingebouwde methode om muisveegbewegingen te ondersteunen. Zowel veeg- als muisveegbewegingen zijn standaard uitgeschakeld, maar kunnen worden ingeschakeld met de `setSwipable(boolean)` en `setSwipableWithMouse(boolean)` methoden, respectievelijk.

### Tabplaatsing {#tab-placement}

De `Tabs` binnen een `TabbedPane` kunnen op verschillende posities binnen de component worden geplaatst, afhankelijk van de voorkeur van de applicatieontwikkelaar. Beschikbare opties worden ingesteld met behulp van de gegeven enum, die de waarden `TOP`, `BOTTOM`, `LEFT`, `RIGHT`, of `HIDDEN` heeft. De standaardinstelling is `TOP`.

<ComponentDemo 
path='/webforj/tabbedpaneplacement?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tabbedpane/TabbedPanePlacementView.java'
height="400px"
/>

### Uitlijning {#alignment}

Naast het veranderen van de plaatsing van de `Tab` elementen binnen de `TabbedPane`, is het ook mogelijk om te configureren hoe de tabs zich uitlijnen binnen de component. Standaard is de instelling `AUTO` van kracht, wat de plaatsing van de tabs de uitlijning laat dicteren.

De andere opties zijn `START`, `END`, `CENTER`, en `STRETCH`. De eerste drie beschrijven de positie ten opzichte van de component, terwijl `STRETCH` de tabs de beschikbare ruimte laat vullen.

<ComponentDemo 
path='/webforj/tabbedpanealignment?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneAlignmentView.java'
height="250px"
/>

### Rand en activiteitindicator {#border-and-activity-indicator}

De `TabbedPane` zal standaard een rand weergeven voor de tabs binnenin, geplaatst afhankelijk van welke `Placement` is ingesteld. Deze rand helpt om de ruimte weer te geven die de verschillende tabs binnen het paneel in beslag nemen.

Wanneer een `Tab` wordt aangeklikt, wordt standaard een activiteitindicator weergegeven nabij die `Tab` om te helpen benadrukken welke `Tab` momenteel is geselecteerd.

Beide opties kunnen door een ontwikkelaar worden aangepast door de booleaanse waarden te wijzigen met de juiste setter-methoden. Om te veranderen of de rand wordt weergegeven, kan de `setBorderless(boolean)` methode worden gebruikt, waarbij `true` de rand verbergt, en `false`, de standaardwaarde, de rand weergeeft.

:::info
Deze rand is niet van toepassing op de gehele `TabbedPane` component, en fungeert slechts als een scheiding tussen de tabs en de inhoud van de component.
:::

Om de zichtbaarheid van de actieve indicator in te stellen, kan de `setHideActiveIndicator(boolean)` methode worden gebruikt. Door `true` naar deze methode door te geven, wordt de actieve indicator verborgen onder een actieve `Tab`, terwijl `false`, de standaardwaarde, de indicator zichtbaar houdt.

<ComponentDemo 
path='/webforj/tabbedpaneborder?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneBorderView.java'
height="300px"
/>

### Activatiemodi {#activation-modes}

Voor fijnere controle over hoe de `TabbedPane` zich gedraagt bij navigatie met het toetsenbord, kan de `Activation` mode worden ingesteld om te specificeren hoe de component zich moet gedragen.

- **`Auto`**: Wanneer ingesteld op automatisch, zal het navigeren door tabs met de pijltoetsen onmiddellijk de bijbehorende tab-component tonen.

- **`Handmatig`**: Wanneer ingesteld op handmatig, ontvangt de tab focus maar wordt niet weergegeven totdat de gebruiker op spatie of enter drukt.

<ComponentDemo 
path='/webforj/tabbedpaneactivation?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneActivationView.java'
height="250px"
/>

### Verwijderingsopties {#removal-options}

Individuele `Tab` elementen kunnen zodanig worden ingesteld dat ze sluitbaar zijn. Sluitbare tabs hebben een sluitknop die aan de tab wordt toegevoegd, die een sluitevenement veroorzaakt wanneer erop wordt geklikt. De `TabbedPane` bepaalt hoe dit gedrag wordt afgehandeld.

- **`Handmatig`**: Standaard is de verwijdering ingesteld op `MANUAL`, wat betekent dat het evenement wordt afgevuurd, maar het is aan de ontwikkelaar om dit evenement op welke manier dan ook te verwerken.

- **`Automatisch`**: Alternatief kan `AUTO` worden gebruikt, dat het evenement afvuurt en ook de `Tab` uit de component voor de ontwikkelaar verwijderd, waardoor de noodzaak voor de ontwikkelaar om dit gedrag handmatig te implementeren, vervalt.

## Stijlen {#styling}

### Uitgebreid en thema {#expanse-and-theme}

De `TabbedPane` wordt geleverd met ingebouwde `Expanse` en `Thema` opties die vergelijkbaar zijn met andere webforJ componenten. Deze kunnen snel worden gebruikt om stijlen toe te voegen die verschillende betekenissen voor de eindgebruiker overbrengen zonder dat de component met CSS hoeft te worden gestyled.

<ComponentDemo 
path='/webforj/tabbedpaneexpansetheme?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneExpanseThemeView.java'
height="250px"
/>

<TableBuilder name={['Tab', 'TabbedPane']} />

## Best practices {#best-practices}

De volgende praktijken worden aanbevolen voor het gebruik van de `TabbedPane` binnen applicaties:

- **Logische Groepering**: Gebruik tabs om gerelateerde inhoud logisch te groeperen.
    >- Elke tab moet een afzonderlijke categorie of functionaliteit binnen jouw applicatie vertegenwoordigen.
    >- Groepeer vergelijkbare of logische tabs dicht bij elkaar.

- **Beperkte Tabs**: Vermijd om gebruikers te overweldigen met te veel tabs. Overweeg een hiërarchische structuur of andere navigatiepatronen toe te passen waar nodig voor een schone interface.

- **Duidelijke Labels**: Label je Tabs duidelijk voor intuïtief gebruik.
    >- Voorzie duidelijke en beknopte labels voor elke tab.
    >- Labels moeten de inhoud of het doel weerspiegelen, zodat gebruikers het gemakkelijk begrijpen.
    >- Gebruik pictogrammen en verschillende kleuren waar van toepassing.

- **Toetsenbordnavigatie**: Gebruik de toetsenbordnavigatie ondersteuning van webforJ's `TabbedPane` om de interactie met de `TabbedPane` naadlozer en intuïtiever te maken voor de eindgebruiker.

- **Standaard Tab**: Als de standaard tab niet aan het begin van de `TabbedPane` is geplaatst, overweeg dan deze tab als standaard in te stellen voor essentiële of vaak gebruikte informatie.
