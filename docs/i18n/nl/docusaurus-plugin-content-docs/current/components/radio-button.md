---
title: RadioButton
slug: radiobutton
sidebar_position: 95
_i18n_hash: 0445bb7e995db7e0d725964c66690d19
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-radio" />
<DocChip chip='since' label='23.01' />
<JavadocLink type="foundation" location="com/webforj/component/optioninput/RadioButton" top='true'/>

De `RadioButton` component is een object dat geselecteerd of niet geselecteerd kan worden, en toont zijn staat aan de gebruiker. Radio knoppen worden vaak gebruikt wanneer er wederzijds exclusieve opties beschikbaar zijn, waardoor de gebruiker een enkele optie uit een set keuzes kan kiezen.

<ComponentDemo 
path='/webforj/radiobuttongroup?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/radiobutton/RadioButtonGroupView.java'
height="200px"
/>

:::tip Groeperen van `RadioButton` componenten
Gebruik een [`RadioButtonGroup`](/docs/components/radiobuttongroup) om een set radio knoppen te beheren wanneer je wilt dat gebruikers een enkele optie kiezen.
:::

## Gebruik {#usages}

De `RadioButton` is het beste te gebruiken in scenario's waarin gebruikers een enkele selectie moeten maken uit een vooraf gedefinieerde set opties. Hier zijn enkele voorbeelden van wanneer je de `RadioButton` kunt gebruiken:

1. **Enquêtes of Vragenlijsten**: Radio knoppen worden vaak gebruikt in enquêtes of vragenlijsten waarin gebruikers een enkele reactie uit een lijst van opties moeten selecteren.

2. **Voorkeursinstellingen**: Toepassingen die betrokken zijn bij voorkeur- of instellingpanelen gebruiken vaak Radio knoppen om gebruikers in staat te stellen een enkele optie uit een set wederzijds exclusieve keuzes te kiezen.

3. **Filteren of Sorteren**: Een `RadioButton` kan worden gebruikt in toepassingen die vereisen dat gebruikers een enkele filter- of sorteermogelijkheid selecteren, zoals het sorteren van een lijst met items op verschillende criteria.

## Tekst en positionering {#text-and-positioning}

Radio knoppen kunnen de ```setText(String text)``` methode gebruiken, die naast de radio knop wordt gepositioneerd volgens de ingebouwde `Position`. Radio knoppen hebben ingebouwde functionaliteit om tekst weer te geven, hetzij aan de rechter- of linkerkant van de component. Standaard wordt de tekst aan de rechterkant van de component weergegeven. Positionering van de horizontale tekst wordt ondersteund door gebruik te maken van de `HorizontalAlignment` enum klasse. Hieronder staan de twee instellingen: <br/>

<ComponentDemo 
path='/webforj/radiobuttontext?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/radiobutton/RadioButtonTextView.java'
height="120px"
/>

## Activatie {#activation}

Radio knoppen kunnen worden gecontroleerd met behulp van twee soorten activatie: handmatige activatie en automatische activatie. Deze bepalen wanneer een `RadioButton` zijn staat verandert.

<ComponentDemo 
path='/webforj/radiobuttonactivation?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/radiobutton/RadioButtonActivationView.java'
height="175px"
/>

### Handmatige activatie {#manual-activation}

Wanneer een radio knop is ingesteld op handmatige activatie, betekent dit dat deze niet automatisch wordt aangevinkt wanneer deze focus krijgt. Handmatige activatie stelt de gebruiker in staat om door de radio knop opties te navigeren met behulp van het toetsenbord of andere invoermethoden zonder onmiddellijk de geselecteerde optie te wijzigen.

Als de radio knop deel uitmaakt van een groep, zal het selecteren van een andere radio knop binnen de groep automatisch de eerder geselecteerde radio knop uitschakelen. Handmatige activatie biedt fijnere controle over het selectieproces, waarbij een expliciete actie van de gebruiker vereist is om de geselecteerde optie te wijzigen.

### Automatische activatie {#auto-activation}

Automatische activatie is de standaard staat voor een `RadioButton`, en betekent dat de knop wordt aangevinkt telkens wanneer deze om welke reden dan ook focus krijgt. Dit betekent dat niet alleen klikken, maar ook automatisch focus of tab-navigatie de knop zal aanvinken.

:::tip Opmerking
De standaard activatiewaarde is **`MANUAL`** activatie.
:::

## Schakelaars {#switches}

Een `RadioButton` kan ook worden ingesteld om als een schakelaar te worden weergegeven, wat een alternatieve visuele weergave biedt voor het selecteren van opties. Normaal gesproken zijn radio knoppen cirkelvormig of afgerond en geven ze een enkele keuze uit een groep opties aan.

<ComponentDemo 
path='/webforj/radiobuttonswitch?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/radiobutton/RadioButtonSwitchView.java'
height="120px"
/>

Een `RadioButton` kan worden omgevormd tot een schakelaar die lijkt op een schakelaartje of schuifregelaar met behulp van een van de twee methoden:

1. **De Fabrieksmethode**: De RadioButton kan worden aangemaakt met behulp van de volgende fabrieksmethoden:

```java
Switch(String text, boolean checked);
Switch(String text);
Switch();
```

Deze methoden spiegelen een `RadioButton` constructor en creëren de component met de schakelaar functionaliteit al ingeschakeld.

2. **Setter**: Het is ook mogelijk om een al bestaande `RadioButton` in een schakelaar te veranderen door de juiste setter te gebruiken:

```java
myRadioButton.setSwitch(true);
```

Wanneer een `RadioButton` als een schakelaar wordt weergegeven, verschijnt deze doorgaans als een langwerpige vorm met een indicator die kan worden in- of uitgeschakeld. Deze visuele weergave biedt gebruikers een intuïtieve en vertrouwde interface, vergelijkbaar met fysieke schakelaars die vaak in elektronische apparaten worden aangetroffen.

Het instellen van een `RadioButton` om als een schakelaar te worden weergegeven, kan de gebruikerservaring verbeteren door een duidelijke en eenvoudige manier te bieden om opties te selecteren. Het kan de visuele aantrekkingskracht en bruikbaarheid van formulieren, instellingpanelen of elk ander interface-element dat meerdere keuzes vereist, verbeteren.

:::info
Het gedrag van de `RadioButton` blijft hetzelfde wanneer deze als een schakelaar wordt weergegeven, wat betekent dat slechts één optie binnen een groep geselecteerd kan worden. Het schakelaarachtige uiterlijk is een visuele transformatie die de functionaliteit van een `RadioButton` behoudt.
:::

<br/>

## Stijlen {#styling}

### Uitbreidingen {#expanses}
Er zijn vijf checkbox-uitbreidingen die worden ondersteund en die snelle styling mogelijk maken zonder CSS te gebruiken. Uitbreidingen worden ondersteund door gebruik te maken van de `Expanse` enum klasse. Hieronder staan de ondersteunde uitbreidingen voor de checkboxcomponent: <br/>

<TableBuilder name="RadioButton" />

## Beste praktijken {#best-practices}

Om een optimale gebruikerservaring te waarborgen bij het gebruik van de RadioButton-component, overweeg de volgende beste praktijken:

1. **Duidelijk labels voor opties**: Zorg voor duidelijke en beknopte tekst voor elke `RadioButton` optie om de keuze nauwkeurig te beschrijven. De tekst moet gemakkelijk te begrijpen zijn en van elkaar te onderscheiden.

2. **Groep Radio knoppen**: Groepeer gerelateerde radio knoppen om hun associatie aan te geven. Dit helpt gebruikers te begrijpen dat binnen een specifieke groep slechts één optie kan worden geselecteerd. Dit kan effectief worden gedaan met de [`RadioButtonGroup`](/docs/components/radiobuttongroup) component.

3. **Voorzie standaardselectie**: Indien van toepassing, overweeg om een standaardselectie voor radio knoppen te bieden om gebruikers te begeleiden wanneer zij voor het eerst met de opties worden geconfronteerd. De standaardselectie moet overeenkomen met de meest voorkomende of gewenste keuze.
