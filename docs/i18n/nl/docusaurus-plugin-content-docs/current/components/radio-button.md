---
title: RadioButton
slug: radiobutton
sidebar_position: 95
_i18n_hash: bf7e30274560f1e29fc307b5894c533a
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-radio" />
<DocChip chip='since' label='23.01' />
<JavadocLink type="foundation" location="com/webforj/component/optioninput/RadioButton" top='true'/>

De `RadioButton` class maakt een object aan dat geselecteerd of gedeselecteerd kan worden, en dat zijn status aan de gebruiker toont. Bij conventie kan slechts één radio button in een groep tegelijkertijd geselecteerd zijn. Radio buttons worden vaak gebruikt wanneer er onderling exclusieve opties beschikbaar zijn, waardoor de gebruiker een enkele optie uit een set keuzes kan kiezen.

<ComponentDemo 
path='/webforj/radiobuttongroup?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/radiobutton/RadioButtonGroupView.java'
height="200px"
/>

## Gebruik {#usages}

De `RadioButton` is het best te gebruiken in scenario's waarin gebruikers een enkele selectie moeten maken uit een vooraf gedefinieerde set opties. Hier zijn enkele voorbeelden van wanneer je de `RadioButton` moet gebruiken:

1. **Enquêtes of Vragenlijsten**: Radio buttons worden vaak gebruikt in enquêtes of vragenlijsten waar gebruikers een enkele response uit een lijst met opties moeten selecteren.

2. **Voorkeurinstellingen**: Toepassingen die voorkeuren of instellingenpanelen omvatten, gebruiken vaak Radio buttons om gebruikers in staat te stellen een enkele optie uit een set onderling exclusieve keuzes te kiezen.

3. **Filteren of Sorteren**: Een `RadioButton` kan worden gebruikt in applicaties die van gebruikers vereisen dat ze een enkele filter- of sorteermogelijkheid kiezen, zoals het sorteren van een lijst met items op verschillende criteria.

## Tekst en positionering {#text-and-positioning}

Radio buttons kunnen de ```setText(String text)``` methode gebruiken, die zich nabij de radio button positioneert volgens de ingebouwde `Position`. Radio buttons hebben ingebouwde functionaliteit om tekst weer te geven, hetzij rechts of links van het element. Standaard wordt de tekst rechts van het element weergegeven. Positionering van de horizontale tekst wordt ondersteund door gebruik van de `HorizontalAlignment` enum klasse. Hieronder staan de twee instellingen: <br/>

<ComponentDemo 
path='/webforj/radiobuttontext?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/radiobutton/RadioButtonTextView.java'
height="120px"
/>

## Activatie {#activation}

Radio buttons kunnen worden bestuurd met twee soorten activatie: handmatige activatie en automatische activatie. Dit bepaalt wanneer een `RadioButton` zijn status wijzigt.

<ComponentDemo 
path='/webforj/radiobuttonactivation?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/radiobutton/RadioButtonActivationView.java'
height="175px"
/>

### Handmatige activatie {#manual-activation}

Wanneer een radio button is ingesteld op handmatige activatie, betekent dit dat deze niet automatisch wordt aangevinkt wanneer deze focus krijgt. Handmatige activatie stelt de gebruiker in staat om door de radio button opties te navigeren met behulp van het toetsenbord of andere invoermethoden zonder onmiddellijk de geselecteerde optie te wijzigen.

Als de radio button deel uitmaakt van een groep, wordt het automatisch deselecteren van de eerder geselecteerde radio button uitgevoerd wanneer een andere radio button binnen de groep wordt geselecteerd. Handmatige activatie biedt meer controle over het selecteerproces, omdat er een expliciete actie van de gebruiker vereist is om de geselecteerde optie te wijzigen.

### Auto activatie {#auto-activation}

Automatische activatie is de standaardstatus voor een `RadioButton`, en betekent dat de button altijd wordt aangevinkt wanneer deze om welke reden dan ook focus krijgt. Dit betekent dat niet alleen klikken, maar ook automatische focus of tabnavigatie de button aan zal vinken.

:::tip Opmerking
De standaard activatiewaarde is **`MANUAL`** activatie.
:::

## Schakelaars {#switches}

Een `RadioButton` kan ook worden ingesteld om als een schakelaar weer te geven, wat een alternatieve visuele weergave biedt voor het selecteren van opties. Normaal gesproken zijn radio buttons rond of cirkelvormig en geven ze een enkele keuze aan uit een groep opties.

<ComponentDemo 
path='/webforj/radiobuttonswitch?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/radiobutton/RadioButtonSwitchView.java'
height="120px"
/>

Een `RadioButton` kan worden omgezet in een schakelaar die lijkt op een toggle-schakelaar of schuifregelaar met behulp van een van de twee methoden:

1. **De Fabrieksmethode**: De RadioButton kan worden aangemaakt met behulp van de volgende Fabrieksmethoden:

```java
Switch(String text, boolean checked);
Switch(String text);
Switch();
```

Deze methoden spiegelen een `RadioButton` constructor en maken het component aan met de schakelaar al ingeschakeld.

2. **Setter**: Het is ook mogelijk om een al bestaande `RadioButton` om te zetten in een schakelaar door de juiste setter te gebruiken:

```java
myRadioButton.setSwitch(true);
```

Wanneer een `RadioButton` als een schakelaar wordt weergegeven, ziet deze er doorgaans uit als een langwerpige vorm met een indicator die kan worden in- of uitgeschakeld. Deze visuele weergave biedt gebruikers een meer intuïtieve en vertrouwde interface, vergelijkbaar met fysieke schakelaars die vaak worden aangetroffen in elektronische apparaten.

Het instellen van een `RadioButton` om als een schakelaar weer te geven kan de gebruikerservaring verbeteren door een duidelijke en eenvoudige manier te bieden om opties te selecteren. Het kan de visuele aantrekkingskracht en bruikbaarheid van formulieren, instellingenpanelen of elk ander interface-element dat meerdere keuzes vereist, verbeteren.

:::info
Het gedrag van de `RadioButton` blijft hetzelfde wanneer deze wordt weergegeven als een Switch, wat betekent dat er maar één optie tegelijk binnen een groep kan worden geselecteerd. Het switch-achtige uiterlijk is een visuele transformatie die de functionaliteit van een `RadioButton` behoudt.
:::

<br/>

## Stijlen {#styling}

### Uitzettingen {#expanses}
Er zijn vijf checkbox-uitzettingen die worden ondersteund en die snelle styling mogelijk maken zonder CSS te gebruiken. Uitzettingen worden ondersteund door gebruik van de `Expanse` enum klasse. Hieronder staan de uitzettingen die voor het checkboxcomponent worden ondersteund: <br/>

<TableBuilder name="RadioButton" />

## Best practices {#best-practices}

Om een optimale gebruikerservaring te waarborgen bij het gebruik van de RadioButton component, overweeg de volgende best practices:

1. **Duidelijk Labelen van Opties**: Bied duidelijke en beknopte tekst voor elke `RadioButton` optie om de keuze nauwkeurig te beschrijven. De tekst moet gemakkelijk te begrijpen zijn en van elkaar te onderscheiden.

2. **Groep Radio buttons**: Groepeer gerelateerde Radio buttons samen om hun associatie aan te geven. Dit helpt gebruikers te begrijpen dat er maar één optie kan worden geselecteerd binnen een specifieke groep. Dit kan effectief worden gedaan met behulp van de [`RadioButtonGroup`](/docs/components/radiobuttongroup) component.

3. **Voorzien van Standaardselectie**: Indien van toepassing, overweeg een standaardselectie voor Radio buttons aan te bieden om gebruikers te begeleiden wanneer ze de opties voor het eerst tegenkomen. De standaardselectie moet aansluiten bij de meest voorkomende of favoriete keuze.
