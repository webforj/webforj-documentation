---
title: TabbedPane
slug: tabbedpane
sidebar_position: 125
sidebar_class_name: new-content
_i18n_hash: 790dce3f2bce2da54e03b7407c11204b
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-tabbed-pane" />
<DocChip chip='since' label='23.06' />
<JavadocLink type="foundation" location="com/webforj/component/tabbedpane/TabbedPane" top='true'/>

Meerdere secties van inhoud kunnen worden georganiseerd onder een enkele `TabbedPane`, waarbij elke sectie is verbonden met een klikbare `Tab`. Slechts één sectie is tegelijkertijd zichtbaar, en tabbladen kunnen tekst, pictogrammen of beide weergeven om gebruikers te helpen tussen hen te navigeren.

<!-- INTRO_END -->

## Gebruiken {#usages}

De `TabbedPane` klasse biedt ontwikkelaars een krachtig middel voor het organiseren en presenteren van meerdere tabbladen of secties binnen een gebruikersinterface. Hier zijn enkele typische scenario's waarin je een `TabbedPane` in je applicatie kunt gebruiken:

1. **Documentviewer**: Implementeren van een documentviewer waar elk tabblad een ander document of bestand vertegenwoordigt. Gebruikers kunnen gemakkelijk tussen open documenten wisselen voor efficiënt multitasking.

2. **Gegevensbeheer:**: Gebruik een `TabbedPane` om gegevensbeheertaken te organiseren, bijvoorbeeld:
    >- Verschillende datasets die in een applicatie moeten worden weergegeven
    >- Verschillende gebruikersprofielen kunnen binnen aparte tabbladen worden weergegeven
    >- Verschillende profielen in een gebruikersbeheersysteem

3. **Moduleselectie**: Een `TabbedPane` kan verschillende modules of secties vertegenwoordigen. Elk tabblad kan de functionaliteiten van een specifieke module encapsuleren, zodat gebruikers zich kunnen concentreren op één aspect van de applicatie tegelijk.

4. **Taakbeheer**: Taakbeheertoepassingen kunnen een `TabbedPane` gebruiken om verschillende projecten of taken weer te geven. Elk tabblad kan corresponderen met een specifiek project, waardoor gebruikers taken afzonderlijk kunnen beheren en volgen.

5. **Programmanavigatie**: Binnen een applicatie die verschillende programma's moet uitvoeren, kan een `TabbedPane`:
    >- Dienen als een zijbalk waarmee verschillende applicaties of programma's binnen een enkele applicatie kunnen worden uitgevoerd, zoals getoond in de [`AppLayout`](./app-layout.md) sjabloon
    >- Een bovenbalk creëren die een vergelijkbaar doel kan dienen, of subapplicaties kan vertegenwoordigen binnen een al geselecteerde applicatie.

## Tabs {#tabs}

Tabs zijn UI-elementen die aan tabbladenpannen kunnen worden toegevoegd om verschillende inhoudsweergaven te organiseren en te schakelen.

:::important
Tabs zijn niet bedoeld om als zelfstandige componenten te worden gebruikt. Ze zijn bedoeld om in combinatie met tabbladenpannen te worden gebruikt. Deze klasse is geen `Component` en moet niet als zodanig worden gebruikt.
:::

### Eigenschappen {#properties}

Tabs bestaan uit de volgende eigenschappen, die worden gebruikt bij het toevoegen ervan aan een `TabbedPane`. Deze eigenschappen hebben getters en setters om aanpassing binnen een `TabbedPane` te vergemakkelijken.

1. **Sleutel(`Object`)**: Vertegenwoordigt de unieke identificatie voor het `Tab`.

2. **Tekst(`String`)**: De tekst die als titel voor het `Tab` binnen de `TabbedPane` zal worden weergegeven. Dit wordt ook wel de titel genoemd via de `getTitle()` en `setTitle(String title)` methoden.

3. **Tooltip(`String`)**: De tooltiptekst die is gekoppeld aan het `Tab`, welke wordt weergegeven wanneer de cursor over het `Tab` zweeft.

4. **Ingeschakeld(`boolean`)**: Vertegenwoordigt of het `Tab` momenteel is ingeschakeld of niet. Kan worden aangepast met de `setEnabled(boolean enabled)` methode.

5. **Sluitbaar(`boolean`)**: Vertegenwoordigt of het `Tab` kan worden gesloten. Kan worden aangepast met de `setCloseable(boolean enabled)` methode. Dit voegt een sluitknop toe op het `Tab` die door de gebruiker kan worden aangeklikt en genereert een verwijderingsgebeurtenis. De `TabbedPane` component bepaalt hoe om te gaan met de verwijdering.

6. **Slot(`Component`)**: 
    Slots bieden flexibele opties voor het verbeteren van de capaciteit van een `Tab`. Je kunt pictogrammen, labels, laadspinners, wissen/resetmogelijkheden, avatar/profielafbeeldingen en andere nuttige componenten binnen een `Tab` nestelen om de bedoelde betekenis voor gebruikers verder te verduidelijken.
    Je kunt een component aan de `prefix` slot van een `Tab` toevoegen tijdens de constructie. Alternatief kun je de `setPrefixComponent()` en `setSuffixComponent()` methoden gebruiken om verschillende componenten vóór en na de weergegeven optie binnen een `Tab` in te voegen.

        ```java
        TabbedPane pane = new TabbedPane();
        pane.addTab(new Tab("Documenten", TablerIcon.create("files")));
        ```

## `Tab` manipulatie {#tab-manipulation}

Verschillende methoden zijn beschikbaar om ontwikkelaars toe te staan om verschillende eigenschappen van `Tab` elementen binnen de `TabbedPane` toe te voegen, in te voegen, te verwijderen en te manipuleren.

### Een `Tab` toevoegen {#adding-a-tab}

De `addTab()` en `add()` methoden bestaan in verschillende overloaded capaciteiten om ontwikkelaars flexibiliteit te bieden bij het toevoegen van nieuwe tabbladen aan de `TabbedPane`. Een `Tab` toevoegen plaatst deze na eerder bestaande tabbladen.

1. **`addTab(String text)`** - Voegt een `Tab` toe aan de `TabbedPane` met de opgegeven `String` als de tekst van het `Tab`.
2. **`addTab(Tab tab)`** - Voegt de `Tab` die als parameter is opgegeven toe aan de `TabbedPane`.
3. **`addTab(String text, Component component)`** - Voegt een `Tab` toe met de gegeven `String` als de tekst van het `Tab`, en de opgegeven `Component` die wordt weergegeven in de inhoudsectie van de `TabbedPane`.
4. **`addTab(Tab tab, Component component)`** - Voegt de opgegeven `Tab` toe en toont de opgegeven `Component` in de inhoudsectie van de `TabbedPane`.
5. **`add(Component... component)`** - Voegt een of meer `Component` instanties toe aan de `TabbedPane`, waardoor een afzonderlijk `Tab` voor elke wordt gemaakt, met de tekst ingesteld op de naam van de `Component`.

:::info
De `add(Component... component)` bepaalt de naam van de doorgegeven `Component` door de `component.getName()` methode op het doorgegeven argument aan te roepen.
:::

### Een `Tab` invoegen {#inserting-a-tab}

Naast het toevoegen van een `Tab` aan het einde van de bestaande tabbladen, is het ook mogelijk om een nieuwe in te voegen op een aangewezen positie. Dit kan worden gedaan met behulp van meerdere overloaded versies van de `insertTab()`.

1. **`insertTab(int index, String text)`** - Voegt een `Tab` in de `TabbedPane` in op de gegeven index met de opgegeven `String` als de tekst van het `Tab`.
2. **`insertTab(int index, Tab tab)`** - Voegt de als parameter opgegeven `Tab` toe aan de `TabbedPane` op de opgegeven index.
3. **`insertTab(int index, String text, Component component)`** - Voegt een `Tab` in met de gegeven `String` als de tekst van het `Tab`, en de opgegeven `Component` die in de inhoudsectie van de `TabbedPane` wordt weergegeven.
4. **`insertTab(int index, Tab tab, Component component)`** - Voegt de opgegeven `Tab` in en toont de opgegeven `Component` in de inhoudsectie van de `TabbedPane`.

### Een `Tab` verwijderen {#removing-a-tab}

Om een enkele `Tab` uit de `TabbedPane` te verwijderen, gebruik een van de volgende methoden:

1. **`removeTab(Tab tab)`** - Verwijdert een `Tab` uit de `TabbedPane` door de te verwijderen Tab-instantie door te geven.
2. **`removeTab(int index)`** - Verwijdert een `Tab` uit de `TabbedPane` door de index van de te verwijderen `Tab` op te geven.

Naast de twee bovenstaande methoden voor het verwijderen van een enkele `Tab`, gebruik de **`removeAllTabs()`** methode om de `TabbedPane` van alle tabbladen te wissen.

:::info
De `remove()` en `removeAll()` methoden verwijderen geen tabbladen binnen de component.
:::

### Tab/Component associatie {#tabcomponent-association}

Om de `Component` die voor een bepaald `Tab` wordt weergegeven te wijzigen, roep je de `setComponentFor()` methode aan en geef je ofwel de instantie van de `Tab`, of de index van die Tab binnen de `TabbedPane` door.

:::info
Als deze methode wordt gebruikt op een `Tab` die al gekoppeld is aan een `Component`, wordt de eerder gekoppelde `Component` vernietigd.
:::

## Configuratie en lay-out {#configuration-and-layout}

De `TabbedPane` klasse heeft twee samenstellende delen: een `Tab` die op een specifieke plaats wordt weergegeven, en een component die moet worden weergegeven. Dit kan een enkele component of een [`Composite`](../building-ui/composite-components) component zijn, waarmee complexere componenten binnen een tab's inhoudsectie kunnen worden weergegeven.

### Veegbewegingen {#swiping}

De `TabbedPane` ondersteunt navigeren door de verschillende tabbladen via veegbewegingen. Dit is ideaal voor een mobiele applicatie, maar kan ook worden geconfigureerd via een ingebouwde methode om muisveegbewegingen te ondersteunen. Zowel veegbewegingen als muisveegbewegingen zijn standaard uitgeschakeld, maar kunnen worden ingeschakeld met de `setSwipable(boolean)` en `setSwipableWithMouse(boolean)` methoden.

### Tabplaatsing {#tab-placement}

De `Tabs` binnen een `TabbedPane` kunnen op verschillende posities binnen de component worden geplaatst op basis van de voorkeur van de applicatieontwikkelaar. Beschikbare opties worden ingesteld met behulp van de verstrekte enum, die de waarden `TOP`, `BOTTOM`, `LEFT`, `RIGHT`, of `HIDDEN` heeft. De standaardinstelling is `TOP`.

<ComponentDemo 
path='/webforj/tabbedpaneplacement?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tabbedpane/TabbedPanePlacementView.java'
height="400px"
/>

### Uitlijning {#alignment}

Naast het veranderen van de plaatsing van de `Tab` elementen binnen de `TabbedPane`, is het ook mogelijk om te configureren hoe de tabbladen zich uitlijnen binnen de component. Standaard is de instelling `AUTO` van kracht, wat de plaatsing van de tabbladen toestaat om hun uitlijning te dicteren.

De andere opties zijn `START`, `END`, `CENTER`, en `STRETCH`. De eerste drie beschrijven de positie ten opzichte van de component, terwijl `STRETCH` de tabbladen laat vullen met de beschikbare ruimte.

<ComponentDemo 
path='/webforj/tabbedpanealignment?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneAlignmentView.java'
height="250px"
/>

### Border en activiteitindicator {#border-and-activity-indicator}

De `TabbedPane` heeft standaard een rand die voor de tabbladen binnenin wordt weergegeven, geplaatst afhankelijk van welke `Plaatsing` is ingesteld. Deze rand helpt om de ruimte te visualiseren die de verschillende tabbladen binnen het paneel innemen.

Wanneer een `Tab` wordt aangeklikt, wordt standaard een activiteitindicator weergegeven nabij dat `Tab` om te helpen benadrukken welk `Tab` momenteel is geselecteerd.

Beide opties kunnen worden aangepast door een ontwikkelaar door de booleaanse waarden te wijzigen met de juiste setter-methoden. Om te veranderen of de rand wordt getoond of niet, kan de `setBorderless(boolean)` methode worden gebruikt, waarbij `true` de rand verbergt, en `false`, de standaardwaarde, de rand weergeeft.

:::info
Deze rand geldt niet voor de gehele `TabbedPane` component, en dient slechts als scheiding tussen de tabbladen en de inhoud van de component.
:::

Om de zichtbaarheid van de actieve indicator in te stellen, kan de `setHideActiveIndicator(boolean)` methode worden gebruikt. Het doorgeven van `true` aan deze methode verbergt de actieve indicator onder een actieve `Tab`, terwijl `false`, de standaardwaarde, de indicator zichtbaar houdt.

<ComponentDemo 
path='/webforj/tabbedpaneborder?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneBorderView.java'
height="300px"
/>

### Activatiemodi {#activation-modes}

Voor meer gedetailleerde controle over hoe de `TabbedPane` zich gedraagt wanneer deze via het toetsenbord wordt genavigeerd, kan de `Activatie` modus worden ingesteld om te specificeren hoe de component zich moet gedragen.

- **`Auto`**: Wanneer ingesteld op automatisch, toont het navigeren tussen tabbladen met de pijltoetsen onmiddellijk de overeenkomstige tabcomponent.

- **`Handmatig`**: Wanneer ingesteld op handmatig, ontvangt het tabblad focus maar wordt niet weergegeven totdat de gebruiker op spatie of enter drukt.

<ComponentDemo 
path='/webforj/tabbedpaneactivation?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneActivationView.java'
height="250px"
/>

### Verwijderopties {#removal-options}

Individuele `Tab` elementen kunnen worden ingesteld als sluitbaar. Sluitbare tabbladen krijgen een sluitknop toegevoegd aan het tabblad, die een sluitgebeurtenis genereert wanneer erop wordt geklikt. De `TabbedPane` bepaalt hoe dit gedrag wordt afgehandeld.

- **`Handmatig`**: Standaard is verwijdering ingesteld op `MANUAL`, wat betekent dat de gebeurtenis wordt geactiveerd, maar het aan de ontwikkelaar is om deze gebeurtenis op welke manier dan ook af te handelen.

- **`Auto`**: Alternatief kan `AUTO` worden gebruikt, wat de gebeurtenis zal activeren en het `Tab` ook uit de component verwijderen voor de ontwikkelaar, waardoor het voor de ontwikkelaar niet nodig is om dit gedrag handmatig te implementeren.

### Segmentregeling <DocChip chip='since' label='26.00' /> {#segment-control}

De `TabbedPane` kan worden weergegeven als een segmentregeling door de `segment` eigenschap in te schakelen met `setSegment(true)`. In deze modus worden tabbladen weergegeven met een schuifindicator die de actieve selectie benadrukt, wat een compacte alternatieve biedt voor de standaard tabbladinterface. 

<ComponentDemo 
path='/webforj/tabbedpanesegment?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneSegmentView.java'
height="250px"
/>

## Styling {#styling}

### Expansie en thema {#expanse-and-theme}

De `TabbedPane` wordt geleverd met ingebouwde `Expanse` en `Thema` opties, vergelijkbaar met andere webforJ componenten. Deze kunnen worden gebruikt om snel styling toe te voegen die verschillende betekenissen aan de eindgebruiker overbrengt zonder de component met CSS te stylen.

<ComponentDemo 
path='/webforj/tabbedpaneexpansetheme?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneExpanseThemeView.java'
height="250px"
/>

<TableBuilder name={['Tab', 'TabbedPane']} />

## Beste praktijken {#best-practices}

De volgende praktijken worden aanbevolen voor het gebruik van de `TabbedPane` binnen applicaties:

- **Logische Groepering**: Gebruik tabbladen om gerelateerde inhoud logisch te groeperen
    >- Elk tabblad moet een duidelijke categorie of functionaliteit binnen je applicatie vertegenwoordigen
    >- Groepeer vergelijkbare of logische tabbladen nabij elkaar

- **Beperkte Tabs**: Vermijd het overweldigen van gebruikers met te veel tabbladen. Overweeg een hiërarchische structuur of andere navigatiepatronen waar van toepassing voor een schone interface

- **Heldere Labels**: Label je Tabs duidelijk voor intuïtief gebruik
    >- Bied duidelijke en beknopte labels voor elk tabblad
    >- Labels moeten de inhoud of het doel weerspiegelen, waardoor het voor gebruikers gemakkelijk te begrijpen is
    >- Maak gebruik van pictogrammen en verschillende kleuren waar van toepassing

- **Toetsenbordnavigatie**: Gebruik de toetsenbordnavigatieondersteuning van webforJ's `TabbedPane` om de interactie met de `TabbedPane` soepel en intuïtief te maken voor de eindgebruiker

- **Standaard Tab**: Als de standaard tab niet aan het begin van de `TabbedPane` is geplaatst, overweeg dan om deze tab als standaard in te stellen voor essentiële of veelgebruikte informatie.
