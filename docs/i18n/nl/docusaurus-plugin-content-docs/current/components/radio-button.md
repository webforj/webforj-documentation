---
title: RadioButton
slug: radiobutton
sidebar_position: 95
description: >-
  Add a single-choice RadioButton with text positioning, activation modes, and
  grouping for mutually exclusive selections.
_i18n_hash: b261deb2944ba67d2e9ae8cbbb8b26eb
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-radio" />
<DocChip chip='since' label='23.01' />
<JavadocLink type="foundation" location="com/webforj/component/optioninput/RadioButton" top='true'/>

De `RadioButton` component vertegenwoordigt een enkele optie die kan worden geselecteerd of gedeselecteerd. Radioknoppen worden doorgaans gegroepeerd, zodat het selecteren van één knop automatisch de anderen deselecteert, waardoor gebruikers een enkele keuze kunnen maken uit een set van wederzijds exclusieve opties.

<!-- INTRO_END -->

## Gebruik {#usages}

De `RadioButton` is het beste te gebruiken in scenario's waarin gebruikers een enkele selectie moeten maken uit een vooraf gedefinieerde set van opties. Hier zijn enkele voorbeelden van wanneer je de `RadioButton` kunt gebruiken:

1. **Enquêtes of vragenlijsten**: Radioknoppen worden vaak gebruikt in enquêtes of vragenlijsten waar gebruikers een enkele reactie uit een lijst van opties moeten selecteren.

2. **Voorkeurinstellingen**: Toepassingen die voorkeuren of instellingen bevatten, gebruiken vaak radioknoppen om gebruikers een enkele optie te laten kiezen uit een set van wederzijds exclusieve keuzes.

3. **Filteren of sorteren**: Een `RadioButton` kan worden gebruikt in toepassingen die vereisen dat gebruikers een enkele filter- of sorteeroptie selecteren, zoals het sorteren van een lijst met items op verschillende criteria.

:::tip Groeperen van `RadioButton` componenten
Gebruik een [`RadioButtonGroup`](/docs/components/radiobuttongroup) om een set radioknoppen te beheren wanneer je wilt dat gebruikers een enkele optie kiezen.
:::

## Tekst en positionering {#text-and-positioning}

Gebruik `setText(String text)` om het label van een radioknop in te stellen. Standaard verschijnt de tekst rechts van de component. Om de positie te wijzigen, gebruik `setTextPosition(Position.LEFT)` of `setTextPosition(Position.RIGHT)` met de `HasTextPosition.Position` enum.

<ComponentDemo
path='/webforj/radiobuttontext'
files={['src/main/java/com/webforj/samples/views/radiobutton/RadioButtonTextView.java']}
height='120px'
/>

## Activatie {#activation}

Radioknoppen kunnen worden aangestuurd met twee soorten activatie: handmatige activatie en automatische activatie. Deze bepalen wanneer een `RadioButton` zijn status wijzigt. De standaardinstelling is `Activation.MANUAL`, uit de `RadioButton.Activation` enum.

<ComponentDemo
path='/webforj/radiobuttonactivation'
files={['src/main/java/com/webforj/samples/views/radiobutton/RadioButtonActivationView.java']}
height='175px'
/>

### Handmatige activatie {#manual-activation}

Wanneer een radioknop is ingesteld op handmatige activatie, betekent dit dat deze niet automatisch wordt geselecteerd wanneer deze de focus krijgt. Handmatige activatie stelt de gebruiker in staat om door de opties van de radioknop te navigeren met behulp van het toetsenbord of andere invoermethoden zonder onmiddellijk de geselecteerde optie te wijzigen.

Als de radioknop deel uitmaakt van een groep, zal het selecteren van een andere radioknop binnen de groep automatisch de eerder geselecteerde radioknop deselecteren. Handmatige activatie biedt meer controle over het selectieproces, waarbij een expliciete actie van de gebruiker vereist is om de geselecteerde optie te wijzigen.

### Automatische activatie {#auto-activation}

Schakel automatische activatie in met `setActivation(Activation.AUTO)`. In deze modus wordt de radioknop geselecteerd telkens wanneer deze de focus krijgt, inclusief via klikken, automatische focus of toetsenbordnavigatie.

## Schakelaars {#switches}

Een `RadioButton` kan ook worden ingesteld om als een schakelaar weer te geven, wat een alternatieve visuele weergave biedt voor het selecteren van opties. Normaal gesproken zijn radioknoppen cirkelvormig of afgerond en geven ze een enkele keuze aan uit een groep opties.

<ComponentDemo
path='/webforj/radiobuttonswitch'
files={['src/main/java/com/webforj/samples/views/radiobutton/RadioButtonSwitchView.java']}
height='120px'
/>

Een `RadioButton` kan worden omgevormd tot een switch die lijkt op een toggle-schakelaar of schuifregelaar met een van de twee methoden:

1. **De Fabrieksmethode**: De radioknop kan worden gemaakt met de volgende fabrieksmethoden:

```java
Switch(String text, boolean checked);
Switch(String text);
Switch();
```

Deze methoden weerspiegelen een `RadioButton` constructor en zullen de component met de schakelaarfunctie al ingeschakeld maken.

2. **Setter**: Het is ook mogelijk om een al bestaande `RadioButton` in een schakelaar te veranderen met de juiste setter:

```java
myRadioButton.setSwitch(true);
```

Wanneer een `RadioButton` als een schakelaar wordt weergegeven, verschijnt deze doorgaans in een oblonge vorm met een indicator die kan worden in- of uitgeschakeld. Deze visuele weergave geeft gebruikers een meer intuïtieve en bekende interface, vergelijkbaar met fysieke schakelaars die vaak in elektronische apparaten worden aangetroffen.

Het instellen van een `RadioButton` om als een schakelaar weer te geven, kan de gebruikerservaring verbeteren door een duidelijke en eenvoudige manier te bieden om opties te selecteren. Het kan de visuele aantrekkingskracht en bruikbaarheid van formulieren, instellingenpanelen of andere interface-elementen die meerdere keuzes vereisen, verbeteren.

:::info
Het gedrag van de `RadioButton` blijft hetzelfde wanneer deze als een schakelaar wordt weergegeven, wat betekent dat slechts één optie tegelijk in een groep kan worden geselecteerd. De schakelaar-achtige uitstraling is een visuele transformatie die de functionaliteit van een `RadioButton` behoudt.
:::

<br/>

## Stijlen {#styling}

### Verhoudingen {#expanses}

Gebruik `setExpanse()` met de `Expanse` enum om de grootte van een `RadioButton` aan te passen zonder CSS te schrijven. De beschikbare waarden zijn `XSMALL`, `SMALL`, `MEDIUM`, `LARGE`, `XLARGE`, en `NONE`.

<TableBuilder name="RadioButton" />

## Beste praktijken {#best-practices}

Om een optimale gebruikerservaring te garanderen bij het gebruik van de RadioButton component, overweeg de volgende beste praktijken:

1. **Duidelijk labelen van opties**: Zorg voor duidelijke en beknopte tekst voor elke `RadioButton` optie om de keuze nauwkeurig te beschrijven. De tekst moet gemakkelijk te begrijpen en van elkaar te onderscheiden zijn.

2. **Groep radioknoppen**: Groepeer gerelateerde radioknoppen samen om hun associatie aan te geven. Dit helpt gebruikers te begrijpen dat slechts één optie binnen een specifieke groep kan worden geselecteerd. Dit kan effectief worden gedaan met de [`RadioButtonGroup`](/docs/components/radiobuttongroup) component.

3. **Bied standaardselectie aan**: Als dit van toepassing is, overweeg dan om een standaardselectie voor radioknoppen te geven om gebruikers te begeleiden wanneer ze voor het eerst met de opties worden geconfronteerd. De standaardselectie moet overeenkomen met de meest voorkomende of gewenste keuze.
