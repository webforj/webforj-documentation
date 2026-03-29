---
title: RadioButton
slug: radiobutton
sidebar_position: 95
_i18n_hash: 19e51a9c57a6524781ac008abcebc790
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-radio" />
<DocChip chip='since' label='23.01' />
<JavadocLink type="foundation" location="com/webforj/component/optioninput/RadioButton" top='true'/>

De `RadioButton` component vertegenwoordigt een enkele optie die geselecteerd of deselecteerd kan worden. Radio knoppen worden doorgaans gegroepeerd zodat het selecteren van één automatisch de andere deselecteert, waardoor gebruikers een enkele keuze kunnen maken uit een set wederzijds exclusieve opties.

<!-- INTRO_END -->

## Usages {#usages}

De `RadioButton` is het beste te gebruiken in scenario's waarin gebruikers een enkele selectie moeten maken uit een vooraf gedefinieerde set opties. Hier zijn enkele voorbeelden wanneer je de `RadioButton` kunt gebruiken:

1. **Enquêtes of Vragenlijsten**: Radio knoppen worden vaak gebruikt in enquêtes of vragenlijsten waarbij gebruikers een enkele reactie uit een lijst met opties moeten selecteren.

2. **Voorkeurinstellingen**: Toepassingen die betrokken zijn bij voorkeur- of instellingenpanelen gebruiken vaak radio knoppen om gebruikers in staat te stellen een enkele optie uit een set weerzijdse exclusieve keuzes te kiezen.

3. **Filteren of Sorteren**: Een `RadioButton` kan worden gebruikt in toepassingen die vereisen dat gebruikers een enkele filter- of sorteermogelijkheid selecteren, zoals het sorteren van een lijst met items op verschillende criteria.

:::tip Groeperen van `RadioButton` componenten
Gebruik een [`RadioButtonGroup`](/docs/components/radiobuttongroup) om een set radio knoppen te beheren wanneer je wilt dat gebruikers een enkele optie kiezen.
:::

## Tekst en positionering {#text-and-positioning}

Radio knoppen kunnen de ```setText(String text)``` methode gebruiken, die dichtbij de radio knop wordt gepositioneerd volgens de ingebouwde `Position`. Radio knoppen hebben ingebouwde functionaliteit om tekst in te stellen die ofwel rechts of links van de component wordt weergegeven. Standaard wordt de tekst rechts van de component weergegeven. Positionering van de horizontale tekst wordt ondersteund door gebruik te maken van de `HorizontalAlignment` enum klasse. Hieronder worden de twee instellingen weergegeven: <br/>

<ComponentDemo 
path='/webforj/radiobuttontext?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/radiobutton/RadioButtonTextView.java'
height="120px"
/>


## Activatie {#activation}

Radio knoppen kunnen worden bestuurd met twee soorten activatie: handmatige activatie en automatische activatie. Deze bepalen wanneer een `RadioButton` zijn status zal veranderen.

<ComponentDemo 
path='/webforj/radiobuttonactivation?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/radiobutton/RadioButtonActivationView.java'
height="175px"
/>

### Handmatige activatie {#manual-activation}

Wanneer een radio knop is ingesteld op handmatige activatie, betekent dit dat deze niet automatisch wordt aangevinkt wanneer deze focus krijgt.
Handmatige activatie stelt de gebruiker in staat om door de radio knop opties te navigeren met behulp van het toetsenbord of andere invoermethoden zonder onmiddellijk de geselecteerde optie te veranderen.

Als de radio knop deel uitmaakt van een groep, zal het selecteren van een andere radio knop binnen de groep automatisch de eerder geselecteerde radio knop deselecteren.
Handmatige activatie biedt fijnere controle over het selectieproces, waarbij een expliciete actie van de gebruiker vereist is om de geselecteerde optie te veranderen.

### Automatische activatie {#auto-activation}

Automatische activatie is de standaardstatus voor een `RadioButton`, en betekent dat de knop wordt aangevinkt wanneer deze om welke reden dan ook focus krijgt. Dit betekent dat
niet alleen klikken, maar ook auto-focus of tab-navigatie de knop zal aanzetten.

:::tip Opmerking
De standaard activatiewaarde is **`MANUAL`** activatie.
:::


## Schakelaars {#switches}

Een `RadioButton` kan ook worden ingesteld om als een schakelaar weer te geven, wat een alternatieve visuele weergave biedt voor het selecteren van opties. Normaal gesproken zijn radio knoppen cirkelvormig of afgerond van vorm en geven ze een enkele keuze aan uit een groep opties. 

<ComponentDemo 
path='/webforj/radiobuttonswitch?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/radiobutton/RadioButtonSwitchView.java'
height="120px"
/>

Een `RadioButton` kan worden getransformeerd in een schakelaar die lijkt op een toggle-schakelaar of schuifregelaar met behulp van een van de twee methoden:

1. **De Fabrieksmethode**: De RadioButton kan worden gemaakt met de volgende Fabrieksmethoden:

```java
Switch(String text, boolean checked);
Switch(String text);
Switch();
```

Deze methoden spiegelen een `RadioButton` constructor, en zullen de component creëren met de schakelaarfunctie al ingeschakeld.

2. **Setter**: Het is ook mogelijk om een reeds bestaande `RadioButton` in een schakelaar te veranderen door de juiste setter te gebruiken:

```java
myRadioButton.setSwitch(true);
```

Wanneer een `RadioButton` wordt weergegeven als een schakelaar, verschijnt deze typisch als een langwerpige vorm met een indicator die aan of uit kan worden geschakeld. Deze visuele weergave geeft gebruikers een meer intuïtieve en vertrouwde interface, vergelijkbaar met fysieke schakelaars die vaak in elektronische apparaten te vinden zijn.

Het instellen van een `RadioButton` om als een schakelaar weer te geven kan de gebruikerservaring verbeteren door een duidelijke en eenvoudige manier te bieden om opties te selecteren. Het kan de visuele aantrekkingskracht en bruikbaarheid van formulieren, instellingenpanelen, of elk ander interface-element dat meerdere keuzes vereist, verbeteren.

:::info
De functionaliteit van de `RadioButton` blijft hetzelfde wanneer deze wordt weergegeven als een Schakelaar, wat betekent dat er steeds maar één optie tegelijk binnen een groep kan worden geselecteerd. Het schakelaarachtige uiterlijk is een visuele transformatie die de functionaliteit van een `RadioButton` behoudt.
:::

<br/>

## Stijlen {#styling}

### Uitbreidingen {#expanses}
Er zijn vijf checkbox-uitbreidingen die worden ondersteund waarmee snel gestyled kan worden zonder CSS te gebruiken.
Uitbreidingen worden ondersteund door gebruik te maken van de `Expanse` enum klasse. Hieronder staan de ondersteunde uitbreidingen voor de checkboxcomponent: <br/>

<TableBuilder name="RadioButton" />

## Beste praktijken {#best-practices}

Om een optimale gebruikerservaring te garanderen bij het gebruik van de RadioButton component, overweeg de volgende beste praktijken:

1. **Label opties duidelijk**: Bied duidelijke en beknopte tekst voor elke `RadioButton` optie om de keuze nauwkeurig te beschrijven. Tekst moet gemakkelijk te begrijpen en van elkaar te onderscheiden zijn.

2. **Groep radio knoppen**: Groepeer gerelateerde radio knoppen samen om hun associatie aan te geven. Dit helpt gebruikers begrijpen dat er slechts één optie kan worden geselecteerd binnen een specifieke groep. Dit kan effectief worden gedaan met de [`RadioButtonGroup`](/docs/components/radiobuttongroup) component.

3. **Bied standaardselectie aan**: Overweeg, indien van toepassing, een standaardselectie voor radio knoppen aan te bieden om gebruikers te begeleiden wanneer ze de opties voor het eerst tegenkomen. De standaardselectie moet overeenkomen met de meest voorkomende of gewenste keuze.
