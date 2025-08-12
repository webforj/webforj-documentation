---
title: RadioButton
slug: radiobutton
sidebar_position: 95
_i18n_hash: efd1171b68ca07b593064abe0366ded7
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-radio" />
<DocChip chip='since' label='23.01' />
<JavadocLink type="foundation" location="com/webforj/component/optioninput/RadioButton" top='true'/>

De `RadioButton`-klasse creëert een object dat geselecteerd of gedeselecteerd kan worden, en dat zijn status aan de gebruiker toont. Bij conventie kan slechts één radio-knop in een groep tegelijkertijd geselecteerd zijn. Radio-knoppen worden vaak gebruikt wanneer er onderling exclusieve opties beschikbaar zijn, waardoor de gebruiker één optie uit een set keuzes kan kiezen.

<ComponentDemo 
path='/webforj/radiobuttongroup?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/radiobutton/RadioButtonGroupView.java'
height="200px"
/>

## Toepassingen {#usages}

De `RadioButton` is het beste te gebruiken in scenario's waarin gebruikers een enkele selectie moeten maken uit een vooraf gedefinieerde set opties. Hier zijn enkele voorbeelden van wanneer de `RadioButton` te gebruiken:

1. **Enquêtes of Vragenlijsten**: Radio-knoppen worden vaak gebruikt in enquêtes of vragenlijsten waarbij gebruikers een enkele reactie uit een lijst met opties moeten selecteren.

2. **Voorkeursinstellingen**: Toepassingen die voorkeur- of instellingenpanelen bevatten, gebruiken vaak radio-knoppen om gebruikers in staat te stellen een enkele optie uit een set onderling exclusieve keuzes te kiezen.

3. **Filteren of Sorteren**: Een `RadioButton` kan worden gebruikt in toepassingen die vereisen dat gebruikers een enkele filter- of sorteermogelijkheid selecteren, zoals het sorteren van een lijst met items op verschillende criteria.

## Tekst en positionering {#text-and-positioning}

Radio-knoppen kunnen de ```setText(String text)```-methode gebruiken, die nabij de radio-knop wordt gepositioneerd volgens de ingebouwde `Position`. Radio-knoppen hebben ingebouwde functionaliteit om tekst weer te geven, hetzij rechts, hetzij links van de component. Standaard wordt de tekst rechts van de component weergegeven. Positionering van de horizontale tekst wordt ondersteund door gebruik van de `HorizontalAlignment` enum-klasse. Hier zijn de twee instellingen: <br/>

<ComponentDemo 
path='/webforj/radiobuttontext?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/radiobutton/RadioButtonTextView.java'
height="120px"
/>

## Activatie {#activation}

Radio-knoppen kunnen worden bediend met twee soorten activatie: handmatige activatie en automatische activatie. Deze bepalen wanneer een `RadioButton` zijn status verandert.

<ComponentDemo 
path='/webforj/radiobuttonactivation?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/radiobutton/RadioButtonActivationView.java'
height="175px"
/>

### Handmatige activatie {#manual-activation}

Wanneer een radio-knop is ingesteld op handmatige activatie, betekent dit dat deze niet automatisch wordt aangevinkt wanneer deze focus krijgt. Handmatige activatie stelt de gebruiker in staat om door de radio-knop opties te navigeren met het toetsenbord of andere invoermethoden zonder onmiddellijk de geselecteerde optie te veranderen.

Als de radio-knop deel uitmaakt van een groep, wordt het selecteren van een andere radio-knop binnen de groep automatisch de eerder geselecteerde radio-knop gedeselecteerd. Handmatige activatie biedt meer controle over het selectieproces, wat een expliciete actie van de gebruiker vereist om de geselecteerde optie te veranderen.

### Automatische activatie {#auto-activation}

Automatische activatie is de standaardstatus voor een `RadioButton`, en betekent dat de knop wordt aangevinkt wanneer deze om welke reden dan ook focus krijgt. Dit betekent dat niet alleen klikken, maar ook automatisch focus of tab-navigatie de knop zal aanvinken.

:::tip Opmerking
De standaard activatiewaarde is **`MANUAL`** activatie.
:::

## Schakelaars {#switches}

Een `RadioButton` kan ook worden ingesteld om als een schakelaar weer te geven, wat een alternatieve visuele vertegenwoordiging biedt voor het selecteren van opties. Normaal gesproken zijn radio-knoppen cirkelvormig of afgerond van vorm en geven ze een enkele keuze uit een groep opties aan.

<ComponentDemo 
path='/webforj/radiobuttonswitch?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/radiobutton/RadioButtonSwitchView.java'
height="120px"
/>

Een `RadioButton` kan worden omgezet in een schakelaar die lijkt op een wip-schakelaar of schuifknop met behulp van een van de twee methoden:

1. **De Fabrieksmethode**: De RadioButton kan worden gemaakt met behulp van de volgende fabrieksmethoden:

```java
Switch(String text, boolean checked);
Switch(String text);
Switch();
```

Deze methoden spiegelen een `RadioButton`-constructor en zullen de component maken met de schakelaarfunctie al ingeschakeld.

2. **Setter**: Het is ook mogelijk om een al bestaande `RadioButton` in een schakelaar te veranderen door de juiste setter te gebruiken:

```java
myRadioButton.setSwitch(true);
``` 

Wanneer een `RadioButton` wordt weergegeven als een schakelaar, verschijnt deze meestal als een langwerpige vorm met een indicator die aan of uit kan worden gezet. Deze visuele vertegenwoordiging biedt gebruikers een intuïtieve en vertrouwde interface, vergelijkbaar met fysieke schakelaars die vaak in elektronische apparaten worden aangetroffen.

Het instellen van een `RadioButton` als een schakelaar kan de gebruikerservaring verbeteren door een duidelijke en eenvoudige manier te bieden om opties te selecteren. Het kan de visuele aantrekkingskracht en bruikbaarheid van formulieren, instellingenpanelen of andere interface-elementen die meerdere keuzes vereisen, verbeteren.

:::info
Het gedrag van de `RadioButton` blijft hetzelfde wanneer deze als een schakelaar wordt weergegeven, wat betekent dat binnen een groep slechts één optie tegelijk kan worden geselecteerd. De schakelaarachtige uitstraling is een visuele transformatie die de functionaliteit van een `RadioButton` behoudt.
:::

<br/>

## Styling {#styling}

### Uitzettingen {#expanses}
Er zijn vijf checkbox-uitzettingen die worden ondersteund en die snelle styling mogelijk maken zonder CSS te gebruiken. Uitzettingen worden ondersteund door gebruik van de `Expanse` enum-klasse. Hieronder staan de ondersteunde uitzettingen voor de checkboxcomponent: <br/>

<TableBuilder name="RadioButton" />

## Best practices {#best-practices}

Om een optimale gebruikerservaring te waarborgen bij het gebruik van de RadioButton-component, overweeg de volgende best practices:

1. **Opties Duidelijk Labelen**: Zorg voor duidelijke en beknopte tekst voor elke `RadioButton`-optie om de keuze nauwkeurig te beschrijven. De tekst moet gemakkelijk te begrijpen zijn en van elkaar te onderscheiden.

2. **Groeperen van Radio-knoppen**: Groepeer gerelateerde radio-knoppen om hun associatie aan te geven. Dit helpt gebruikers te begrijpen dat binnen een specifieke groep slechts één optie kan worden geselecteerd. Dit kan effectief worden gedaan met behulp van de [`RadioButtonGroup`](/docs/components/radiobuttongroup) component.

3. **Bied een Standaardselectie Aan**: Indien van toepassing, overweeg om een standaardselectie voor radio-knoppen te bieden om gebruikers te begeleiden wanneer ze de opties voor het eerst tegenkomen. De standaardselectie moet overeenkomen met de meest voorkomende of gewenste keuze.
