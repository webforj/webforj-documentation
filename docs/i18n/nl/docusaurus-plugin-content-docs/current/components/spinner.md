---
title: Spinner
sidebar_position: 110
description: >-
  Indicate background activity with the Spinner component, configuring theme,
  expanse, rotation speed, and pause or resume.
_i18n_hash: bd35c3da6c5fc265d0bb249bbde86215
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-spinner" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="spinner" location="com/webforj/component/spinner/Spinner" top='true'/>

De `Spinner` component biedt een visuele indicator die aangeeft dat er momenteel wordt verwerkt of geladen op de achtergrond. Het wordt vaak gebruikt om aan te geven dat het systeem gegevens ophaalt of wanneer een proces tijd kost om te voltooien. De `Spinner` biedt feedback aan de gebruiker, om te signaleren dat het systeem actief aan het werk is.

<!-- INTRO_END -->

## Basisprincipes {#basics}

Om een `Spinner` te maken, kunt u het thema en de expansie specificeren. De basis syntaxis omvat het creëren van een `Spinner` instantie en het definiëren van het uiterlijk en gedrag via methoden zoals `setTheme()` en `setExpanse()`.

<ComponentDemo
path='/webforj/spinnerdemo'
files={['src/main/java/com/webforj/samples/views/spinner/SpinnerDemoView.java']}
height='225px'
/>

## Snelheid beheren en pauzeren {#managing-speed-and-pausing}

Het is mogelijk om de snelheid in milliseconden voor de `Spinner` in te stellen en de animatie met gemak te pauzeren/resumeren.

Toepassingen voor het instellen van snelheden omvatten het differentiëren tussen laadprocessen. Snellere `Spinners` zijn geschikt voor kleinere taken, terwijl langzamere `Spinners` beter zijn voor grotere taken. Pauzeren is nuttig wanneer gebruikersactie of bevestiging vereist is voordat het proces wordt voortgezet.

### Snelheid aanpassen {#adjusting-speed}

U kunt regelen hoe snel de `Spinner` draait door de snelheid in milliseconden aan te passen met de `setSpeed()` methode. Een lagere waarde laat de `Spinner` sneller draaien, terwijl hogere waarden het vertraagt.

```java
spinner.setSpeed(500); // Draait sneller
```

:::info Standaard Snelheid
Standaard duurt het 1000 milliseconden voor de `Spinner` om één volledige rotatie te voltooien.
:::

### Pauzeren en hervatten {#pausing-and-resuming}

Het pauzeren van de `Spinner` is nuttig wanneer een programma tijdelijk is gepauseerd of wacht op gebruikersinvoer. Het laat gebruikers weten dat het programma in de wacht staat, in plaats van actief te draaien, wat de duidelijkheid tijdens meerstapsprocessen verbetert.

Om de Spinner te pauzeren en te hervatten, gebruikt u de `setPaused()` methode. Dit is bijzonder nuttig wanneer u de draaiende animatie tijdelijk wilt stoppen.

```java
spinner.setPaused(true);  // Pauzeer de spinner
spinner.setPaused(false); // Hervat de spinner
```

Dit voorbeeld laat zien hoe u de snelheid kunt instellen en hoe u de `Spinner` kunt pauzeren/hervatten:

<ComponentDemo
path='/webforj/spinnerspeeddemo'
files={['src/main/java/com/webforj/samples/views/spinner/SpinnerSpeedDemoView.java']}
height='150px'
/>

## Draairichting {#spin-direction}

De richting van de `Spinner` kan worden beheerd om **met de klok mee** of **tegen de klok in** te draaien. U kunt dit gedrag specificeren met de `setClockwise()` methode.

```java
spinner.setClockwise(false);  // Draait tegen de klok in
spinner.setClockwise(true);   // Draait met de klok mee
```

Deze optie geeft visueel een speciale status aan of dient als een unieke ontwerpkeuze. Het veranderen van de draairichting kan helpen bij het onderscheiden van verschillende soorten processen, zoals voortgang versus omkering, of kan een distinctieve visuele aanwijzing bieden in specifieke contexten.

<ComponentDemo
path='/webforj/spinnerdirectiondemo'
files={['src/main/java/com/webforj/samples/views/spinner/SpinnerDirectionDemoView.java']}
height='150px'
/>

## Stijlen {#styling}

### Thema's {#themes}

De `Spinner` component wordt geleverd met verschillende ingebouwde thema's die u in staat stellen om snel stijlen toe te passen zonder dat u aangepaste CSS nodig heeft. Deze thema's veranderen het visuele uiterlijk van de spinner, waardoor deze geschikt is voor verschillende gebruikssituaties en contexten. Het gebruik van deze vooraf gedefinieerde thema's zorgt voor consistentie in de styling door uw app heen.

Hoewel spinners verschillende toepassingen dienen, zijn hier enkele voorbeeldtoepassingen voor de verschillende thema's:

- **Primair**: Ideaal om een laadstatus te benadrukken die een belangrijk onderdeel van de gebruikersstroom is, zoals tijdens het indienen van een formulier of het verwerken van een belangrijke actie.

- **Succes**: Nuttig om succesvolle achtergrondprocessen vertegenwoordigen, zoals wanneer een gebruiker een formulier indient en de app de laatste stappen van het proces uitvoert.

- **Gevaren**: Gebruik dit voor risicovolle of belangrijke handelingen, zoals het verwijderen van belangrijke gegevens of het maken van onomkeerbare wijzigingen, waar een visuele indicatie van urgentie of voorzichtigheid nodig is.

- **Waarschuwing**: Gebruik dit om een waarschuwend of minder urgent proces aan te geven, zoals wanneer de gebruiker wacht op gegevensvalidatie, maar geen onmiddellijke actie vereist is.

- **Grijs**: Werkt goed voor subtiele achtergrondprocessen, zoals laagtijdige of passieve laadtaken, zoals wanneer aanvullende gegevens worden opgehaald die geen directe impact op de gebruikerservaring hebben.

- **Informatie**: Geschikt voor laadsituaties waarbij u aanvullende informatie of verduidelijking aan de gebruiker biedt, zoals het weergeven van een spinner naast een bericht dat het lopende proces uitlegt.

U kunt deze thema's programmatisch op de spinner toepassen, zodat visuele aanwijzingen overeenkomen met de context en het belang van de operatie.

U kunt dit gedrag specificeren met de `setTheme()` methode.

<ComponentDemo
path='/webforj/spinnerthemedemo'
files={['src/main/java/com/webforj/samples/views/spinner/SpinnerThemeDemoView.java']}
height='100px'
/>

### Expansies {#expanses}

U kunt de grootte van de spinner, bekend als **expansie**, aanpassen om de visuele ruimte te passen die u nodig hebt. De spinner ondersteunt verschillende maten, waaronder `Expanse.SMALL`, `Expanse.MEDIUM`, en `Expanse.LARGE`.

<ComponentDemo
path='/webforj/spinnerexpansedemo'
files={['src/main/java/com/webforj/samples/views/spinner/SpinnerExpanseDemoView.java']}
height='100px'
/>

<TableBuilder name="Spinner" />
