---
title: TabbedPane
slug: tabbedpane
sidebar_position: 125
_i18n_hash: 4f2421ca62abb88a3edd750e7887d2db
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-tabbed-pane" />
<DocChip chip='since' label='23.06' />
<JavadocLink type="foundation" location="com/webforj/component/tabbedpane/TabbedPane" top='true'/>

De `TabbedPane` klasse biedt een compacte en georganiseerde manier om inhoud weer te geven die is verdeeld in meerdere secties, elk geassocieerd met een `Tab`. Gebruikers kunnen tussen deze secties schakelen door op de respectieve tabs te klikken, die vaak zijn gelabeld met tekst en/of pictogrammen. Deze klasse vereenvoudigt de creatie van veelzijdige interfaces waar verschillende inhoud of formulieren toegankelijk moeten zijn, maar niet gelijktijdig zichtbaar.

## Usages {#usages}

De `TabbedPane` klasse biedt ontwikkelaars een krachtig hulpmiddel voor het organiseren en presenteren van meerdere tabs of secties binnen een gebruikersinterface. Hier zijn enkele typische scenario's waarin je een `TabbedPane` in jouw applicatie zou kunnen gebruiken:

1. **Documentviewer**: Een documentviewer implementeren waarbij elke tab een ander document of bestand vertegenwoordigt. Gebruikers kunnen eenvoudig tussen geopende documenten schakelen voor efficiënte multitasking.

2. **Gegevensbeheer**: Gebruik een `TabbedPane` om taken voor gegevensbeheer te organiseren, bijvoorbeeld:
    >- Verschillende datasets die in een applicatie moeten worden weergegeven
    >- Diverse gebruikersprofielen kunnen binnen aparte tabs worden weergegeven
    >- Verschillende profielen in een gebruikersbeheersysteem

3. **Module Selectie**: Een `TabbedPane` kan verschillende modules of secties vertegenwoordigen. Elke tab kan de functionaliteiten van een specifieke module encapsuleren, zodat gebruikers zich op één aspect van de applicatie tegelijk kunnen concentreren.

4. **Taakmanagement**: Taakmanagementapplicaties kunnen een `TabbedPane` gebruiken om verschillende projecten of taken weer te geven. Elke tab zou overeenkomen met een specifiek project, waardoor gebruikers taken afzonderlijk kunnen beheren en volgen.

5. **Applicatienavigatie**: Binnen een applicatie die verschillende programma's moet uitvoeren, zou een `TabbedPane` kunnen:
    >- Dienen als een zijbalk die het mogelijk maakt verschillende applicaties of programma's binnen een enkele applicatie te starten, zoals getoond in de [`AppLayout`](./app-layout.md) sjabloon
    >- Een bovenbalk creëren die een soortgelijke rol kan vervullen, of sub-applicaties binnen een reeds geselecteerde applicatie vertegenwoordigt.
  
## Tabs {#tabs}

Tabs zijn UI-elementen die aan tabbladen kunnen worden toegevoegd om verschillende inhoudsweergaven te organiseren en te wisselen.

:::important
Tabs zijn niet bedoeld om als zelfstandige componenten te worden gebruikt. Ze zijn bedoeld om in samenhang met tabbladen te worden gebruikt. Deze klasse is geen `Component` en mag niet als zodanig worden gebruikt.
:::

### Eigenschappen {#properties}

Tabs bestaan uit de volgende eigenschappen, die vervolgens worden gebruikt wanneer ze aan een `TabbedPane` worden toegevoegd. Deze eigenschappen hebben getters en setters om aanpassingen binnen een `TabbedPane` te vergemakkelijken.

1. **Key(`Object`)**: Vertegenwoordigt de unieke identifier voor de `Tab`.

2. **Text(`String`)**: De tekst die als titel voor de `Tab` binnen de `TabbedPane` wordt weergegeven. Dit wordt ook wel de titel genoemd via de `getTitle()` en `setTitle(String title)` methoden.

3. **Tooltip(`String`)**: De tooltip-tekst die is geassocieerd met de `Tab`, die wordt weergegeven wanneer de cursor boven de `Tab` zweeft.

4. **Enabled(`boolean`)**: Vertegenwoordigt of de `Tab` momenteel ingeschakeld is of niet. Kan worden gewijzigd met de `setEnabled(boolean enabled)` methode.

5. **Closeable(`boolean`)**: Vertegenwoordigt of de `Tab` gesloten kan worden. Kan worden gewijzigd met de `setCloseable(boolean enabled)` methode. Dit voegt een sluitknop toe op de `Tab`, die door de gebruiker kan worden aangeklikt en een verwijderingsgebeurtenis activeert. De `TabbedPane` component dicteert hoe met de verwijdering moet worden omgegaan.

6. **Slot(`Component`)**: 
    Slots bieden flexibele opties om de mogelijkheden van een `Tab` te verbeteren. Je kunt pictogrammen, labels, laadspinners, wissen/resetten mogelijkheden, avatar/profielafbeeldingen en andere nuttige componenten binnen een `Tab` nestelen om de bedoelde betekenis aan gebruikers verder te verduidelijken.
    Je kunt een component aan de `prefix` slot van een `Tab` toevoegen tijdens de constructie. Alternatief kun je de `setPrefixComponent()` en `setSuffixComponent()` methoden gebruiken om verschillende componenten voor en na de weergegeven optie binnen een `Tab` in te voegen.

        ```java
        TabbedPane pane = new TabbedPane();
        pane.addTab(new Tab("Documents", TablerIcon.create("files")));
        ```

## `Tab` manipulatie {#tab-manipulation}

Er zijn verschillende methoden beschikbaar waarmee ontwikkelaars verschillende eigenschappen van `Tab` elementen binnen de `TabbedPane` kunnen toevoegen, invoegen, verwijderen en manipuleren.

### Een `Tab` toevoegen {#adding-a-tab}

De `addTab()` en `add()` methoden bestaan in verschillende overbelaste capaciteiten om ontwikkelaars flexibiliteit te bieden bij het toevoegen van nieuwe tabs aan de `TabbedPane`. Het toevoegen van een `Tab` plaatst deze na alle eerder bestaande tabs.

1. **`addTab(String text)`** - Voegt een `Tab` toe aan de `TabbedPane` met de opgegeven `String` als de tekst van de `Tab`.
2. **`addTab(Tab tab)`** - Voegt de `Tab` die als parameter wordt opgegeven toe aan de `TabbedPane`.
3. **`addTab(String text, Component component)`** - Voegt een `Tab` toe met de opgegeven `String` als de tekst van de `Tab`, en de opgegeven `Component` die in de inhoudsectie van de `TabbedPane` wordt weergegeven.
4. **`addTab(Tab tab, Component component)`** - Voegt de opgegeven `Tab` toe en toont de opgegeven `Component` in de inhoudsectie van de `TabbedPane`.
5. **`add(Component... component)`** - Voegt een of meer `Component` instanties toe aan de `TabbedPane`, waarbij een aparte `Tab` voor elk wordt aangemaakt, met de tekst die is ingesteld op de naam van de `Component`.

:::info
De `add(Component... component)` bepaalt de naam van de doorgegeven `Component` door `component.getName()` op het doorgegeven argument aan te roepen.
:::

### Een `Tab` invoegen {#inserting-a-tab}

Naast het toevoegen van een `Tab` aan het einde van de bestaande tabs, is het ook mogelijk om een nieuwe op een aangewezen positie te creëren. Hiervoor zijn meerdere overbelaste versies van de `insertTab()` methode beschikbaar.

1. **`insertTab(int index, String text)`** - Voegt een `Tab` in de `TabbedPane` in op de gegeven index met de opgegeven `String` als de tekst van de `Tab`.
2. **`insertTab(int index, Tab tab)`** - Voegt de `Tab` die als parameter wordt opgegeven toe aan de `TabbedPane` op de opgegeven index.
3. **`insertTab(int index, String text, Component component)`** - Voegt een `Tab` in met de opgegeven `String` als de tekst van de `Tab`, en de opgegeven `Component` die in de inhoudsectie van de `TabbedPane` wordt weergegeven.
4. **`insertTab(int index, Tab tab, Component component)`** - Voegt de opgegeven `Tab` toe en toont de opgegeven `Component` in de inhoudsectie van de `TabbedPane`.

### Een `Tab` verwijderen {#removing-a-tab}

Om een enkele `Tab` uit de `TabbedPane` te verwijderen, gebruik je een van de volgende methoden:

1. **`removeTab(Tab tab)`** - Verwijdert een `Tab` uit de `TabbedPane` door de tab-instantie die moet worden verwijderd door te geven.
2. **`removeTab(int index)`** - Verwijdert een `Tab` uit de `TabbedPane` door de index van de `Tab` die moet worden verwijderd op te geven.

Naast de bovenstaande twee methoden voor het verwijderen van een enkele `Tab`, gebruik de **`removeAllTabs()`** methode om de `TabbedPane` van alle tabs te ontdoen.

:::info
De `remove()` en `removeAll()` methoden verwijderen geen tabs binnen de component.
:::

### Tab/Component associatie {#tabcomponent-association}

Om de `Component` die voor een gegeven `Tab` moet worden weergegeven te wijzigen, roep je de `setComponentFor()` methode aan en geef je ofwel de instantie van de `Tab`, of de index van die `Tab` binnen de `TabbedPane` door.

:::info
Als deze methode wordt gebruikt op een `Tab` die al is geassocieerd met een `Component`, wordt de eerder geassocieerde `Component` vernietigd.
:::

## Configuratie en lay-out {#configuration-and-layout}

De `TabbedPane` klasse heeft twee samenstellende delen: een `Tab` die op een specifieke locatie wordt weergegeven, en een component die moet worden weergegeven. Dit kan een enkele component of een [`Composite`](../building-ui/composite-components) component zijn, waarmee complexere componenten binnen de inhoudsectie van een tab kunnen worden weergegeven.

### Swipen {#swiping}

De `TabbedPane` ondersteunt navigeren door de verschillende tabs via swipen. Dit is ideaal voor een mobiele applicatie, maar kan ook worden geconfigureerd via een ingebouwde methode om muisswipen te ondersteunen. Zowel swipen als muisswipen zijn standaard uitgeschakeld, maar kunnen worden ingeschakeld met de `setSwipable(boolean)` en `setSwipableWithMouse(boolean)` methoden.

### Tab plaatsing {#tab-placement}

De `Tabs` binnen een `TabbedPane` kunnen op verschillende posities binnen de component worden geplaatst, afhankelijk van de voorkeur van de applicatie-ontwikkelaars. Geleverde opties worden ingesteld met behulp van de gegeven enum, die de waarden `TOP`, `BOTTOM`, `LEFT`, `RIGHT`, of `HIDDEN` heeft. De standaardinstelling is `TOP`.

<ComponentDemo 
path='/webforj/tabbedpaneplacement?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tabbedpane/TabbedPanePlacementView.java'
height="400px"
/>

### Uitlijning {#alignment}

Naast het veranderen van de plaatsing van de `Tab` elementen binnen de `TabbedPane`, is het ook mogelijk om te configureren hoe de tabs binnen de component zullen worden uitgelijnd. Standaard is de instelling `AUTO` actief, die de plaatsing van de tabs de uitlijning laat dicteren.

De andere opties zijn `START`, `END`, `CENTER`, en `STRETCH`. De eerste drie beschrijven de positie ten opzichte van de component, terwijl `STRETCH` ervoor zorgt dat de tabs de beschikbare ruimte vullen.

<ComponentDemo 
path='/webforj/tabbedpanealignment?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneAlignmentView.java'
height="250px"
/>

### Rand en activiteitindicator {#border-and-activity-indicator}

De `TabbedPane` heeft standaard een rand voor de tabs binnenin, afhankelijk van welke `Placement` is ingesteld. Deze rand helpt om de ruimte te visualiseren die de verschillende tabs binnen het paneel innemen. 

Wanneer op een `Tab` wordt geklikt, wordt standaard een activiteitindicator weergegeven dichtbij die `Tab` om te helpen benadrukken welke de momenteel geselecteerde `Tab` is.

Beide opties kunnen door een ontwikkelaar worden aangepast door de boolean waarden te wijzigen met behulp van de juiste setter methoden. Om te wijzigen of de rand wordt weergegeven of niet, kan de `setBorderless(boolean)` methode worden gebruikt, waarbij `true` de rand verbergt, en `false`, de standaardwaarde, de rand weergeeft.

:::info
Deze rand geldt niet voor de gehele `TabbedPane` component, en dient enkel als scheiding tussen de tabs en de inhoud van de component.
:::

Om de zichtbaarheid van de actieve indicator in te stellen, kan de `setHideActiveIndicator(boolean)` methode worden gebruikt. Het doorgeven van `true` aan deze methode verbergt de actieve indicator onder een actieve `Tab`, terwijl `false`, de standaardwaarde, de indicator zichtbaar houdt.

<ComponentDemo 
path='/webforj/tabbedpaneborder?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneBorderView.java'
height="300px"
/>

### Activatiemodi {#activation-modes}

Voor meer gedetailleerde controle over hoe de `TabbedPane` zich gedraagt tijdens navigatie via het toetsenbord, kan de `Activation` modus worden ingesteld om te specificeren hoe de component zich moet gedragen.

- **`Auto`**: Wanneer ingesteld op automatisch, toont het navigeren door tabs met de pijltoetsen onmiddellijk de overeenkomstige tabcomponent.

- **`Manual`**: Wanneer ingesteld op handmatig, ontvangt de tab focus maar wordt niet weergegeven totdat de gebruiker spatie of enter indrukt.

<ComponentDemo 
path='/webforj/tabbedpaneactivation?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneActivationView.java'
height="250px"
/>

### Verwijderopties {#removal-options}

Individuele `Tab` elementen kunnen zodanig worden ingesteld dat ze sluitbaar zijn. Sluitbare tabs krijgen een sluitknop toegevoegd aan de tab, die een sluitgebeurtenis activeert wanneer erop wordt geklikt. De `TabbedPane` dicteert hoe dit gedrag wordt afgehandeld.

- **`Manual`**: Standaard is verwijdering ingesteld op `MANUAL`, wat betekent dat de gebeurtenis wordt geactiveerd, maar het is aan de ontwikkelaar om deze gebeurtenis op welke manier dan ook af te handelen.

- **`Auto`**: Alternatief kan `AUTO` worden gebruikt, die de gebeurtenis activeert, en ook de `Tab` uit de component verwijderd voor de ontwikkelaar, waardoor de noodzaak voor de ontwikkelaar om dit gedrag handmatig te implementeren vervalt.

## Styling {#styling}

### Uitbreiding en thema {#expanse-and-theme}

De `TabbedPane` komt met ingebouwde `Expanse` en `Theme` opties, vergelijkbaar met andere webforJ componenten. Deze kunnen worden gebruikt om snel stijlen toe te voegen die verschillende betekenissen overbrengen aan de eindgebruiker zonder de component met CSS te stylen.

<ComponentDemo 
path='/webforj/tabbedpaneexpansetheme?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneExpanseThemeView.java'
height="250px"
/>

<TableBuilder name="TabbedPane" />

## Best practices {#best-practices}

De volgende praktijken worden aanbevolen voor het gebruiken van de `TabbedPane` binnen applicaties:

- **Logische Groepering**: Gebruik tabs om gerelateerde inhoud logisch te groeperen
    >- Elke tab moet een duidelijke categorie of functionaliteit binnen jouw applicatie vertegenwoordigen
    >- Groepeer vergelijkbare of logische tabs dicht bij elkaar

- **Beperkte Tabs**: Vermijd het overweldigen van gebruikers met te veel tabs. Overweeg het gebruik van een hiërarchische structuur of andere navigatiepatronen waar van toepassing voor een schone interface

- **Duidelijke Labels**: Label je Tabs duidelijk voor intuïtief gebruik
    >- Voorzie duidelijke en beknopte labels voor elke tab
    >- Labels moeten de inhoud of het doel weerspiegelen, wat het gemakkelijk maakt voor gebruikers om het te begrijpen
    >- Gebruik pictogrammen en verschillende kleuren waar van toepassing

- **Toetsenbordnavigatie**: Gebruik de toetsenbordnavigatieondersteuning van webforJ's `TabbedPane` om de interactie met de `TabbedPane` naadlozer en intuïtiever te maken voor de eindgebruiker

- **Standaard Tab**: Als de standaard tab niet aan het begin van de `TabbedPane` is geplaatst, overweeg dan om deze tab als standaard in te stellen voor essentiële of veelgebruikte informatie.
