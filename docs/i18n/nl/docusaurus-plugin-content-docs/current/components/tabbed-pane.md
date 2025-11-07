---
title: TabbedPane
slug: tabbedpane
sidebar_position: 125
_i18n_hash: ebf6bff550fd69aeb6ab8e4dfefd2323
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-tabbed-pane" />
<DocChip chip='since' label='23.06' />
<JavadocLink type="foundation" location="com/webforj/component/tabbedpane/TabbedPane" top='true'/>

De `TabbedPane` klasse biedt een compacte en georganiseerde manier om inhoud weer te geven die is verdeeld in meerdere secties, die elk zijn geassocieerd met een `Tab`. Gebruikers kunnen tussen deze secties schakelen door op de respectieve tabs te klikken, die vaak zijn gelabeld met tekst en/of pictogrammen. Deze klasse vereenvoudigt de creatie van veelzijdige interfaces waar verschillende inhoud of formulieren toegankelijk moeten zijn, maar niet tegelijkertijd zichtbaar.

## Usages {#usages}

De `TabbedPane` klasse geeft ontwikkelaars een krachtig hulpmiddel voor het organiseren en presenteren van meerdere tabs of secties binnen een UI. Hier zijn enkele typische scenario's waarin je een `TabbedPane` in je toepassing zou kunnen gebruiken:

1. **Document Viewer**: Implementeren van een documentviewer waarbij elke tab een ander document of bestand vertegenwoordigt. Gebruikers kunnen eenvoudig schakelen tussen geopende documenten voor efficiënt multitasking.

2. **Gegevensbeheer:** Gebruik een `TabbedPane` om gegevensbeheertaken te organiseren, bijvoorbeeld:
    >- Verschillende datasets die in een applicatie moeten worden weergegeven
    >- Verschillende gebruikersprofielen kunnen binnen aparte tabs worden weergegeven
    >- Verschillende profielen in een gebruikersbeheersysteem

3. **Moduleselectie**: Een `TabbedPane` kan verschillende modules of secties vertegenwoordigen. Elke tab kan de functionaliteiten van een specifieke module encapsuleren, zodat gebruikers zich op één aspect van de applicatie tegelijk kunnen concentreren.

4. **Taakbeheer**: Taakbeheertoepassingen kunnen een `TabbedPane` gebruiken om verschillende projecten of taken weer te geven. Elke tab kan overeenkomen met een specifiek project, zodat gebruikers taken afzonderlijk kunnen beheren en volgen.

5. **Programmanavigatie**: Binnen een applicatie die verschillende programma's moet uitvoeren, kan een `TabbedPane`:
    >- Dienen als een zijbalk die het mogelijk maakt om verschillende applicaties of programma's binnen een enkele applicatie uit te voeren, zoals wat wordt getoond in de [`AppLayout`](./app-layout.md) template
    >- Een bovenbalk creëren die een soortgelijk doel kan dienen, of subapplicaties binnen een reeds geselecteerde applicatie kan weergeven.
  
## Tabs {#tabs}

Tabs zijn UI-elementen die aan tabbladen kunnen worden toegevoegd om verschillende inhoudsweergaven te organiseren en tussen hen te schakelen.

:::important
Tabs zijn niet bedoeld om als zelfstandige componenten te worden gebruikt. Ze zijn bedoeld om in combinatie met tabbladen te worden gebruikt. Deze klasse is geen `Component` en moet niet als zodanig worden gebruikt.
:::

### Properties {#properties}

Tabs zijn samengesteld uit de volgende eigenschappen, die worden gebruikt bij het toevoegen ervan in een `TabbedPane`. Deze eigenschappen hebben getters en setters om aanpassing binnen een `TabbedPane` te vergemakkelijken.

1. **Key(`Object`)**: Vertegenwoordigt de unieke identifier voor de `Tab`.

2. **Text(`String`)**: De tekst die als titel voor de `Tab` binnen de `TabbedPane` wordt weergegeven. Dit wordt ook wel de titel genoemd via de `getTitle()` en `setTitle(String title)` methoden.

3. **Tooltip(`String`)**: De tooltip-tekst die aan de `Tab` is gekoppeld, die wordt weergegeven wanneer de cursor over de `Tab` zweeft.

4. **Enabled(`boolean`)**: Vertegenwoordigt of de `Tab` momenteel is ingeschakeld of niet. Dit kan worden aangepast met de `setEnabled(boolean enabled)` methode.

5. **Closeable(`boolean`)**: Vertegenwoordigt of de `Tab` kan worden gesloten. Dit kan worden aangepast met de `setCloseable(boolean enabled)` methode. Dit voegt een sluitknop toe aan de `Tab` die door de gebruiker kan worden aangeklikt en genereert een verwijderingsevenement. De `TabbedPane` component bepaalt hoe om te gaan met de verwijdering.

6. **Slot(`Component`)**: 
    Slots bieden flexibele opties voor het verbeteren van de mogelijkheden van een `Tab`. Je kunt pictogrammen, labels, laadspinners, wissen/resetcapaciteit, avatar/profielafbeeldingen en andere nuttige componenten binnen een `Tab`nestelen om de bedoeling voor gebruikers verder te verduidelijken.
    Je kunt een component aan de `prefix` slot van een `Tab` toevoegen tijdens de constructie. Alternatief kun je de `setPrefixComponent()` en `setSuffixComponent()` methoden gebruiken om verschillende componenten vóór en na de weergegeven optie binnen een `Tab` in te voegen.

        ```java
        TabbedPane pane = new TabbedPane();
        pane.addTab(new Tab("Documenten", TablerIcon.create("files")));
        ```

## `Tab` manipulatie {#tab-manipulation}

Er zijn verschillende methoden beschikbaar om ontwikkelaars in staat te stellen tabs toe te voegen, in te voegen, te verwijderen en verschillende eigenschappen van `Tab` elementen binnen de `TabbedPane` te manipuleren.

### Een `Tab` toevoegen {#adding-a-tab}

De `addTab()` en `add()` methoden bestaan in verschillende overbelaste capaciteiten om ontwikkelaars flexibiliteit te bieden bij het toevoegen van nieuwe tabs aan de `TabbedPane`. Het toevoegen van een `Tab` zal het na alle eerder bestaande tabs plaatsen.

1. **`addTab(String text)`** - Voegt een `Tab` toe aan de `TabbedPane` met de gespecificeerde `String` als de tekst van de `Tab`.
2. **`addTab(Tab tab)`** - Voegt de `Tab` die als parameter is opgegeven toe aan de `TabbedPane`.
3. **`addTab(String text, Component component)`** - Voegt een `Tab` toe met de gegeven `String` als de tekst van de `Tab`, en de gegeven `Component` weergegeven in het inhoudssectie van de `TabbedPane`.
4. **`addTab(Tab tab, Component component)`** - Voegt de opgegeven `Tab` toe en toont de opgegeven `Component` in het inhoudssectie van de `TabbedPane`.
5. **`add(Component... component)`** - Voegt een of meer `Component` instanties toe aan de `TabbedPane`, waarbij voor elke een aparte `Tab` wordt gemaakt, met de tekst ingesteld op de naam van de `Component`

:::info
Het `add(Component... component)` bepaalt de naam van de doorgegeven `Component` door `component.getName()` aan te roepen op het doorgegeven argument.
:::

### Een `Tab` invoegen {#inserting-a-tab}

Naast het toevoegen van een `Tab` aan het einde van de bestaande tabs, is het ook mogelijk om er een nieuwe te creëren op een aangewezen positie. Dit kan door gebruik te maken van meerdere overbelaste versies van de `insertTab()`. 

1. **`insertTab(int index, String text)`** - Voegt een `Tab` toe aan de `TabbedPane` op de gegeven index met de gespecificeerde `String` als de tekst van de `Tab`.
2. **`insertTab(int index, Tab tab)`** - Voegt de `Tab` toe die als parameter is opgegeven aan de `TabbedPane` op de opgegeven index.
3. **`insertTab(int index, String text, Component component)`** - Voegt een `Tab` toe met de gegeven `String` als de tekst van de `Tab`, en de opgegeven `Component` weergegeven in de inhoudssectie van de `TabbedPane`.
4. **`insertTab(int index, Tab tab, Component component)`** - Voegt de opgegeven `Tab` toe en toont de opgegeven `Component` in de inhoudssectie van de `TabbedPane`.

### Een `Tab` verwijderen {#removing-a-tab}

Om een enkele `Tab` uit de `TabbedPane` te verwijderen, gebruik een van de volgende methoden:

1. **`removeTab(Tab tab)`** - Verwijdert een `Tab` uit de `TabbedPane` door de Tab-instantie door te geven die moet worden verwijderd.
2. **`removeTab(int index)`** - Verwijdert een `Tab` uit de `TabbedPane` door de index van de `Tab` te specificeren die moet worden verwijderd.

Naast de twee bovenstaande methoden voor het verwijderen van een enkele `Tab`, gebruik de **`removeAllTabs()`** methode om de `TabbedPane` van alle tabs te wissen.

:::info
De `remove()` en `removeAll()` methoden verwijderen geen tabs binnen de component.
:::

### Tab/Component associatie {#tabcomponent-association}

Om de `Component` die voor een bepaalde `Tab` moet worden weergegeven te wijzigen, roep de `setComponentFor()` methode aan en geef de instantie van de `Tab` of de index van die Tab binnen de `TabbedPane` door.

:::info
Als deze methode wordt gebruikt op een `Tab` die al is gekoppeld aan een `Component`, zal de eerder gekoppelde `Component` worden vernietigd.
:::

## Configuratie en layout {#configuration-and-layout}

De `TabbedPane` klasse heeft twee constituerende delen: een `Tab` die op een specifieke locatie wordt weergegeven, en een component die wordt weergegeven. Dit kan een enkele component of een [`Composite`](../building-ui/composite-components) component zijn, waardoor de weergave van complexere componenten binnen de inhoudssectie van een tab mogelijk is.

### Swiping {#swiping}

De `TabbedPane` ondersteunt navigatie door de verschillende tabs via swipen. Dit is ideaal voor een mobiele applicatie, maar kan ook worden geconfigureerd via een ingebouwde methode om muisswiping te ondersteunen. Zowel swipen als muisswiping zijn standaard uitgeschakeld, maar kunnen worden ingeschakeld met de `setSwipable(boolean)` en `setSwipableWithMouse(boolean)` methoden.

### Tab plaatsing {#tab-placement}

De `Tabs` binnen een `TabbedPane` kunnen op verschillende posities binnen de component worden geplaatst op basis van de voorkeur van de applicatie-ontwikkelaar. Biedt opties worden ingesteld met behulp van de aangeboden enum, die de waarden `TOP`, `BOTTOM`, `LEFT`, `RIGHT` of `HIDDEN` heeft. De standaardinstelling is `TOP`.

<ComponentDemo 
path='/webforj/tabbedpaneplacement?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tabbedpane/TabbedPanePlacementView.java'
height="400px"
/>

### Uitlijning {#alignment}

Naast het wijzigen van de plaatsing van de `Tab` elementen binnen de `TabbedPane`, is het ook mogelijk om in te stellen hoe de tabs binnen de component zullen uitlijnen. Standaard is de instelling `AUTO` van kracht, waarmee de plaatsing van de tabs hun uitlijning bepaalt.

De andere opties zijn `START`, `END`, `CENTER`, en `STRETCH`. De eerste drie beschrijven de positie ten opzichte van de component, terwijl `STRETCH` de tabs de beschikbare ruimte laat vullen.

<ComponentDemo 
path='/webforj/tabbedpanealignment?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneAlignmentView.java'
height="250px"
/>

### Rand en activiteit indicator {#border-and-activity-indicator}

De `TabbedPane` zal standaard een rand tonen voor de tabs erin, geplaatst afhankelijk van welke `Placement` is ingesteld. Deze rand helpt om de ruimte die de verschillende tabs binnen het paneel innemen te visualiseren. 

Wanneer een `Tab` wordt aangeklikt, wordt standaard een activiteitindicator dicht bij die `Tab` weergegeven om te helpen benadrukken welke de momenteel geselecteerde `Tab` is.

Beide opties kunnen door een ontwikkelaar worden aangepast door de booleaanse waarden te wijzigen met behulp van de juiste settermethoden. Om te wijzigen of de rand wordt weergegeven, kan de `setBorderless(boolean)` methode worden gebruikt, waarbij `true` de rand verbergt, en `false`, de standaardwaarde, de rand weergeeft.

:::info
Deze rand is niet van toepassing op de gehele `TabbedPane` component en dient slechts als een scheiding tussen de tabs en de inhoud van de component.
:::

Om de zichtbaarheid van de actieve indicator in te stellen, kan de `setHideActiveIndicator(boolean)` methode worden gebruikt. Door `true` aan deze methode door te geven, wordt de actieve indicator onder een actieve `Tab` verborgen, terwijl `false`, de standaard, de indicator zichtbaar houdt.

<ComponentDemo 
path='/webforj/tabbedpaneborder?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneBorderView.java'
height="300px"
/>

### Activatiemodi {#activation-modes}

Voor meer gedetailleerde controle over hoe de `TabbedPane` zich gedraagt wanneer deze met het toetsenbord wordt genavigeerd, kan de `Activation` modus worden ingesteld om te specificeren hoe de component zich moet gedragen.

- **`Auto`**: Wanneer ingesteld op auto, zal het navigeren door tabs met de pijltoetsen onmiddellijk de overeenkomstige tabcomponent weergeven.

- **`Manual`**: Wanneer ingesteld op handmatig, krijgt de tab focus maar wordt deze niet weergegeven totdat de gebruiker op spatie of enter drukt.

<ComponentDemo 
path='/webforj/tabbedpaneactivation?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneActivationView.java'
height="250px"
/>

### Verwijderopties {#removal-options}

Individuele `Tab` elementen kunnen zodanig worden ingesteld dat ze sluitbaar zijn. Sluitbare tabs krijgen een sluitknop toegevoegd aan de tab, die een sluitevenement genereert wanneer erop wordt geklikt. De `TabbedPane` bepaalt hoe dit gedrag wordt afgehandeld.

- **`Manual`**: Standaard is het verwijderen ingesteld op `MANUAL`, wat betekent dat het evenement wordt gegenereerd, maar het aan de ontwikkelaar is om dit evenement op welke manier dan ook af te handelen.

- **`Auto`**: Alternatief kan `AUTO` worden gebruikt, wat het evenement zal genereren en de `Tab` ook automatisch uit de component zal verwijderen, waardoor de ontwikkelaar deze handeling niet handmatig hoeft te implementeren. 

## Styling {#styling}

### Uiterlijk en thema {#expanse-and-theme}

De `TabbedPane` wordt geleverd met ingebouwde `Uiterlijk` en `Thema` opties vergelijkbaar met andere webforJ componenten. Deze kunnen worden gebruikt om snel styling toe te voegen die verschillende betekenissen overbrengt naar de eindgebruiker zonder de component met CSS te moeten stylen.

<ComponentDemo 
path='/webforj/tabbedpaneexpansetheme?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneExpanseThemeView.java'
height="250px"
/>

<TableBuilder name="TabbedPane" />

## Best practices {#best-practices}

De volgende praktijken worden aanbevolen voor het gebruik van de `TabbedPane` binnen applicaties:

- **Logische Groepering**: Gebruik tabs om gerelateerde inhoud logisch te groeperen
    >- Elke tab moet een duidelijke categorie of functionaliteit binnen je applicatie vertegenwoordigen
    >- Groepeer vergelijkbare of logische tabs dicht bij elkaar

- **Beperkte Tabs**: Vermijd het overweldigen van gebruikers met te veel tabs. Overweeg het gebruik van een hiërarchische structuur of andere navigatiepatronen waar van toepassing voor een schone interface

- **Duidelijke Labels**: Geef je Tabs duidelijk aan voor intuïtief gebruik
    >- Zorg voor duidelijke en beknopte labels voor elke tab
    >- Labels moeten de inhoud of het doel weerspiegelen, zodat het voor gebruikers eenvoudig te begrijpen is
    >- Gebruik pictogrammen en verschillende kleuren waar van toepassing

- **Toetsenbordnavigatie**: Gebruik de toetsenbordnavigatieondersteuning van webforJ's `TabbedPane` om de interactie met de `TabbedPane` naadlozer en intuïtiever te maken voor de eindgebruiker

- **Standaard Tab**: Als de standaardtab zich niet aan het begin van de `TabbedPane` bevindt, overweeg dan om deze tab in te stellen als standaard voor essentiële of vaak gebruikte informatie.
