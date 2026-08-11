---
title: RadioButton
slug: radiobutton
sidebar_position: 95
description: >-
  Add a single-choice RadioButton with text positioning, activation modes, and
  grouping for mutually exclusive selections.
_i18n_hash: 32d2e2f74e7f255b901de15622e8e2cc
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-radio" />
<DocChip chip='since' label='23.01' />
<JavadocLink type="foundation" location="com/webforj/component/optioninput/RadioButton" top='true'/>

De `RadioButton`-component vertegenwoordigt een enkele optie die kan worden geselecteerd of gedeselecteerd. Radioknoppen worden doorgaans gegroepeerd zodat het selecteren van één knop automatisch de anderen deselecteert, waardoor gebruikers een enkele keuze kunnen maken uit een set van wederzijds exclusieve opties.

<!-- INTRO_END -->

## Gebruik {#usages}

De `RadioButton` is het beste te gebruiken in situaties waarin gebruikers een enkele selectie moeten maken uit een vooraf gedefinieerde set opties. Hier zijn enkele voorbeelden van wanneer je de `RadioButton` kunt gebruiken:

1. **Enquêtes of Vragenlijsten**: Radioknoppen worden vaak gebruikt in enquêtes of vragenlijsten waarbij gebruikers een enkele reactie uit een lijst van opties moeten selecteren.

2. **Voorkeurinstellingen**: Toepassingen die voorkeur- of instellingenpanelen omvatten, gebruiken vaak radioknoppen om gebruikers in staat te stellen een enkele optie te kiezen uit een set van wederzijds exclusieve keuzes.

3. **Filteren of Sorteren**: Een `RadioButton` kan worden gebruikt in applicaties waarbij gebruikers een enkele filter- of sorteermogelijkheid moeten selecteren, zoals het sorteren van een lijst met items op verschillende criteria.

:::tip Groeperen van `RadioButton`-componenten
Gebruik een [`RadioButtonGroup`](/docs/components/radiobuttongroup) om een set radioknoppen te beheren wanneer je wilt dat gebruikers een enkele optie kiezen.
:::

## Tekst en positionering {#text-and-positioning}

Radioknoppen kunnen de ```setText(String text)```-methode gebruiken, die dicht bij de radioknop wordt gepositioneerd volgens de ingebouwde `Position`. Radioknoppen hebben ingebouwde functionaliteit om tekst weer te geven die aan de rechter- of linkerkant van de component staat. Standaard wordt de tekst aan de rechterkant van de component weergegeven. Positionering van de horizontale tekst wordt ondersteund door gebruik van de `HorizontalAlignment` enum-klasse. Hieronder staan de twee instellingen: <br/>

<ComponentDemo
path='/webforj/radiobuttontext'
files={['src/main/java/com/webforj/samples/views/radiobutton/RadioButtonTextView.java']}
height='120px'
/>


## Activatie {#activation}

Radioknoppen kunnen worden bediend met twee soorten activatie: handmatige activatie en automatische activatie. Deze bepalen wanneer een `RadioButton` zijn status wijzigt.

<ComponentDemo
path='/webforj/radiobuttonactivation'
files={['src/main/java/com/webforj/samples/views/radiobutton/RadioButtonActivationView.java']}
height='175px'
/>

### Handmatige activatie {#manual-activation}

Wanneer een radioknop is ingesteld op handmatige activatie, betekent dit dat deze niet automatisch wordt aangevinkt wanneer deze de focus krijgt. Handmatige activatie stelt de gebruiker in staat om door de opties van de radioknoppen te navigeren met behulp van het toetsenbord of andere invoermethoden zonder onmiddellijk de geselecteerde optie te wijzigen.

Als de radioknop deel uitmaakt van een groep, zal het selecteren van een andere radioknop binnen de groep automatisch de previously geselecteerde radioknop deselecteren. Handmatige activatie biedt fijnmazige controle over het selectieproces, en vereist een expliciete actie van de gebruiker om de geselecteerde optie te wijzigen.


### Automatische activatie {#auto-activation}

Automatische activatie is de standaardstatus voor een `RadioButton`, en betekent dat de knop wordt aangevinkt wanneer deze om welke reden dan ook de focus krijgt. Dit betekent dat niet alleen klikken, maar ook autofocus of tabnavigatie de knop zal aanvinken.

:::tip Opmerking
De standaard activatiewaarde is **`MANUAL`** activatie.
:::


## Schakelaars {#switches}

Een `RadioButton` kan ook worden ingesteld om als een schakelaar weer te geven, wat een alternatieve visuele weergave biedt voor het selecteren van opties. Normaal gesproken zijn radioknoppen cirkelvormig of afgerond en geven ze een enkele keuze aan uit een groep opties.

<ComponentDemo
path='/webforj/radiobuttonswitch'
files={['src/main/java/com/webforj/samples/views/radiobutton/RadioButtonSwitchView.java']}
height='120px'
/>

Een `RadioButton` kan worden omgezet in een schakelaar die lijkt op een schakelaartje of schuifknop met behulp van een van de twee methoden:

1. **De Fabrieksmethode**: De radioknop kan worden gemaakt met behulp van de volgende fabrieksmethoden:

```java
Switch(String text, boolean checked);
Switch(String text);
Switch();
```

Deze methoden weerspiegelen een `RadioButton`-constructor, en zullen de component maken met de schakelaar-eigenschap al ingeschakeld.

2. **Setter**: Het is ook mogelijk om een al bestaande `RadioButton` in een schakelaar te veranderen door de juiste setter te gebruiken:

```java
myRadioButton.setSwitch(true);
```


Wanneer een `RadioButton` als een schakelaar wordt weergegeven, heeft deze doorgaans een langwerpige vorm met een indicatie die kan worden in- of uitgeschakeld. Deze visuele representatie biedt gebruikers een intuïtieve en bekende interface, vergelijkbaar met fysieke schakelaars die vaak in elektronische apparaten worden aangetroffen.

Het instellen van een `RadioButton` om als een schakelaar weer te geven, kan de gebruikerservaring verbeteren door een duidelijke en eenvoudige manier te bieden om opties te selecteren. Het kan de visuele aantrekkingskracht en bruikbaarheid van formulieren, instellingenpanelen of andere interface-elementen die meerdere keuzes vereisen, verbeteren.

:::info
Het gedrag van de `RadioButton` blijft hetzelfde wanneer het als een schakelaars wordt weergegeven, wat betekent dat binnen een groep slechts één optie tegelijk kan worden geselecteerd. Het schakelaarachtige uiterlijk is een visuele transformatie die de functionaliteit van een `RadioButton` behoudt.
:::

<br/>

## Stijlen {#styling}

### Uitbreidingen {#expanses}
Er zijn vijf checkbox-uitbreidingen die worden ondersteund en die snelle styling mogelijk maken zonder gebruik te maken van CSS. Uitbreidingen worden ondersteund door gebruik van de `Expanse` enum-klasse. Hieronder staan de ondersteunde uitbreidingen voor het checkboxcomponent: <br/>

<TableBuilder name="RadioButton" />

## Beste praktijken {#best-practices}

Om een optimale gebruikerservaring te waarborgen bij het gebruik van de RadioButton-component, overweeg de volgende beste praktijken:

1. **Label opties duidelijk**: Geef duidelijke en beknopte tekst voor elke `RadioButton`-optie om de keuze nauwkeurig te beschrijven. De tekst moet gemakkelijk te begrijpen zijn en van elkaar te onderscheiden.

2. **Groep radioknoppen**: Groepeer gerelateerde radioknoppen om hun associatie aan te geven. Dit helpt gebruikers te begrijpen dat binnen een specifieke groep slechts één optie kan worden geselecteerd. Dit kan effectief worden gedaan met behulp van de [`RadioButtonGroup`](/docs/components/radiobuttongroup) component.

3. **Bied standaardselectie aan**: Indien van toepassing, overweeg een standaardselectie voor radioknoppen aan te bieden om gebruikers te begeleiden wanneer ze de opties voor het eerst tegenkomen. De standaardselectie moet overeenkomen met de meest voorkomende of voorkeurkeuze.
