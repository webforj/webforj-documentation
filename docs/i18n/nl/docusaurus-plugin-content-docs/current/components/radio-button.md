---
title: RadioButton
slug: radiobutton
sidebar_position: 95
_i18n_hash: 491fdadd826e3b34acc02b8833704faf
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-radio" />
<DocChip chip='since' label='23.01' />
<JavadocLink type="foundation" location="com/webforj/component/optioninput/RadioButton" top='true'/>

De `RadioButton` component vertegenwoordigt een enkele optie die kan worden geselecteerd of afgevinkt. Radioknoppen worden doorgaans gegroepeerd, zodat het selecteren van een knop automatisch de andere afvinkt, waardoor gebruikers de keuze hebben uit een enkele optie uit een groep van onderling exclusieve opties.

<!-- INTRO_END -->

## Usages {#usages}

De `RadioButton` is het meest geschikt voor scenario's waarin gebruikers een enkele selectie moeten maken uit een vooraf gedefinieerde set opties. Hier zijn enkele voorbeelden van wanneer je de `RadioButton` kunt gebruiken:

1. **Enquêtes of Vragenlijsten**: Radioknoppen worden vaak gebruikt in enquêtes of vragenlijsten waar gebruikers een enkele reactie uit een lijst met opties moeten kiezen.

2. **Voorkeursinstellingen**: Toepassingen die betrekking hebben op voorkeur- of instellingspanelen gebruiken vaak radioknoppen om gebruikers in staat te stellen een enkele optie uit een set onderling exclusieve keuzes te kiezen.

3. **Filteren of Sorteren**: Een `RadioButton` kan worden gebruikt in toepassingen waar gebruikers een enkele filter- of sorteervariant moeten selecteren, zoals het sorteren van een lijst met items op verschillende criteria.

:::tip Groeperen van `RadioButton` componenten
Gebruik een [`RadioButtonGroup`](/docs/components/radiobuttongroup) om een set radioknoppen te beheren wanneer je wilt dat gebruikers een enkele optie selecteren.
:::

## Text en positionering {#text-and-positioning}

Radioknoppen kunnen de ```setText(String text)``` methode gebruiken, die in de buurt van de radioknop kan worden gepositioneerd volgens de ingebouwde `Position`.
Radioknoppen hebben ingebouwde functionaliteit om tekst weer te geven, hetzij rechts of links van de component. Standaard wordt de tekst rechts van de component weergegeven. Positionering van de horizontale tekst wordt ondersteund door gebruik te maken van de `HorizontalAlignment` enum klasse. Hieronder staan de twee instellingen: <br/>

<ComponentDemo
path='/webforj/radiobuttontext'
files={['src/main/java/com/webforj/samples/views/radiobutton/RadioButtonTextView.java']}
height='120px'
/>

## Activatie {#activation}

Radioknoppen kunnen worden bestuurd met behulp van twee soorten activatie: handmatige activatie en automatische activatie. Dit bepaalt wanneer een `RadioButton` zijn status zal veranderen.

<ComponentDemo
path='/webforj/radiobuttonactivation'
files={['src/main/java/com/webforj/samples/views/radiobutton/RadioButtonActivationView.java']}
height='175px'
/>

### Handmatige activatie {#manual-activation}

Wanneer een radioknop is ingesteld op handmatige activatie, betekent dit dat deze niet automatisch wordt aangevinkt wanneer deze focus krijgt.
Handmatige activatie stelt de gebruiker in staat om door de opties van de radioknop te navigeren met behulp van het toetsenbord of andere invoermethoden zonder onmiddellijk de geselecteerde optie te veranderen.

Als de radioknop deel uitmaakt van een groep, zal het selecteren van een andere radioknop binnen de groep automatisch de eerder geselecteerde radioknop afvinken.
Handmatige activatie biedt fijnere controle over het selecteerproces, waarbij een expliciete actie van de gebruiker vereist is om de geselecteerde optie te veranderen.


### Automatische activatie {#auto-activation}

Automatische activatie is de standaardtoestand voor een `RadioButton`, en betekent dat de knop wordt aangevinkt telkens wanneer deze focus krijgt om welke reden dan ook. Dit betekent dat
niet alleen klikken, maar ook auto-focus of tab-navigatie de knop zal aanvinken.

:::tip Opmerking
De standaard activatiewaarde is **`MANUAL`** activatie.
:::

## Schakelaars {#switches}

Een `RadioButton` kan ook worden ingesteld om als een schakelaar te worden weergegeven, wat een alternatieve visuele weergave biedt voor het selecteren van opties. Normaal gesproken zijn radioknoppen rond of afgerond van vorm en geven ze een enkele keuze aan uit een groep opties.

<ComponentDemo
path='/webforj/radiobuttonswitch'
files={['src/main/java/com/webforj/samples/views/radiobutton/RadioButtonSwitchView.java']}
height='120px'
/>

Een `RadioButton` kan worden omgevormd tot een schakelaar die lijkt op een toggle-switch of slider met behulp van een van de twee methoden:

1. **De Fabrieksmethode**: De Radiobutton kan worden gemaakt met de volgende Fabrieksmethoden:

```java
Switch(String text, boolean checked);
Switch(String text);
Switch();
```

Deze methoden spiegelen een `RadioButton` constructor, en zullen de component maken met de switch-eigenschap al ingeschakeld.

2. **Setter**: Het is ook mogelijk om een reeds bestaande `RadioButton` in een schakelaar te veranderen door de juiste setter te gebruiken:

```java
myRadioButton.setSwitch(true);
```

Wanneer een `RadioButton` als een schakelaar wordt weergegeven, verschijnt deze doorgaans als een langwerpige vorm met een indicatie die in- of uitgeschakeld kan worden. Deze visuele weergave geeft gebruikers een meer intuïtieve en herkenbare interface, vergelijkbaar met fysieke schakelaars die vaak in elektronische apparaten worden aangetroffen.

Een `RadioButton` instellen om als een schakelaar weer te geven kan de gebruikerservaring verbeteren door een duidelijke en eenvoudige manier te bieden om opties te selecteren. Het kan de visuele aantrekkingskracht en bruikbaarheid van formulieren, instellingenpanelen of elk ander interface-element dat meerdere keuzes vereist verbeteren.

:::info
Het gedrag van de `RadioButton` blijft hetzelfde wanneer deze wordt weergegeven als een Schakelaar, wat betekent dat er slechts één optie tegelijkertijd binnen een groep kan worden geselecteerd. Het uiterlijk van de schakelaar is een visuele transformatie die de functionaliteit van een `RadioButton` behoudt.
:::

<br/>

## Stijlen {#styling}

### Uitbreidingen {#expanses}
Er zijn vijf checkbox-uitbreidingen die worden ondersteund, die snelle styling mogelijk maken zonder CSS te gebruiken.
Uitbreidingen worden ondersteund door gebruik te maken van de `Expanse` enum klasse. Hieronder staan de uitbreidingen die worden ondersteund voor de checkboxcomponent: <br/>

<TableBuilder name="RadioButton" />

## Beste praktijken {#best-practices}

Om een optimale gebruikerservaring te waarborgen bij het gebruik van de RadioButton-component, overweeg de volgende beste praktijken:

1. **Duidelijk labelen van opties**: Zorg voor duidelijke en beknopte tekst voor elke `RadioButton` optie om de keuze nauwkeurig te beschrijven. De tekst moet gemakkelijk te begrijpen en van elkaar te onderscheiden zijn.

2. **Groepeer Radioknoppen**: Groepeer gerelateerde radioknoppen om hun associatie aan te geven. Dit helpt gebruikers te begrijpen dat er slechts één optie binnen een specifieke groep kan worden geselecteerd. Dit kan effectief worden gedaan met de [`RadioButtonGroup`](/docs/components/radiobuttongroup) component.

3. **Bied een standaardselectie aan**: Indien van toepassing, overweeg dan om een standaardselectie voor radioknoppen te bieden om gebruikers te begeleiden wanneer ze de opties voor het eerst tegenkomen. De standaardselectie moet overeenkomen met de meest voorkomende of voorkeurskeuze.
