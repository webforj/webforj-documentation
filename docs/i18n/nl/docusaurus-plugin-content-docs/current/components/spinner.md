---
title: Spinner
sidebar_position: 110
_i18n_hash: bb61c6f2d3cf7073ca2d1c6fc6e1c0c4
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-spinner" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="spinner" location="com/webforj/component/spinner/Spinner" top='true'/>

De `Spinner` component biedt een visuele indicatie die aangeeft dat er op de achtergrond een verwerking of laadactie plaatsvindt. Het wordt vaak gebruikt om aan te geven dat het systeem gegevens aan het ophalen is of wanneer een proces tijd kost om te voltooien. De `Spinner` biedt gebruikersfeedback, waardoor wordt gesignaleerd dat het systeem actief aan het werk is.

<!-- INTRO_END -->

## Basisprincipes {#basics}

Om een `Spinner` te maken, kun je het thema en de expanse specificeren. De basis syntaxis omvat het creëren van een `Spinner` instantie en het definiëren van zijn uiterlijk en gedrag via methoden zoals `setTheme()` en `setExpanse()`.

<ComponentDemo 
path='/webforj/spinnerdemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/spinner/SpinnerDemoView.java'
height = '225px'
/>

## Snelheid beheren en pauzeren {#managing-speed-and-pausing}

Het is mogelijk om de snelheid in milliseconden voor de `Spinner` in te stellen en de animatie eenvoudig te pauzeren/te hervatten.

Gebruikssituaties voor het instellen van snelheden omvatten het onderscheiden van laadprocessen. Snellere `Spinners` zijn geschikt voor kleinere taken, terwijl tragere `Spinners` beter zijn voor grotere taken. Pauzeren is nuttig wanneer gebruikersactie of bevestiging vereist is voordat het proces wordt voortgezet.

### Snelheid aanpassen {#adjusting-speed}

Je kunt beheersen hoe snel de `Spinner` draait door de snelheid in milliseconden aan te passen met de `setSpeed()` methode. Een lagere waarde laat de `Spinner` sneller draaien, terwijl hogere waarden deze vertragen.

```java
spinner.setSpeed(500); // Draait sneller
```

:::info Standaard snelheid
Standaard neemt de `Spinner` 1000 milliseconden om één volledige rotatie te voltooien.
:::

### Pauzeren en hervatten {#pausing-and-resuming}

Het pauzeren van de `Spinner` is nuttig wanneer een programma tijdelijk is gepauzeerd of wacht op gebruikersinvoer. Het laat gebruikers weten dat het programma in de wacht staat, in plaats van actief te draaien, wat de duidelijkheid tijdens meerfasige processen verbetert.

Om de Spinner te pauzeren en te hervatten, gebruik je de `setPaused()` methode. Dit is bijzonder handig wanneer je de draaiende animatie tijdelijk moet stopzetten.

```java
spinner.setPaused(true);  // Pauzeer de spinner
spinner.setPaused(false); // Hervat de spinner
```

Dit voorbeeld toont hoe je de snelheid instelt en hoe je de `Spinner` kunt pauzeren/hervatten:

<ComponentDemo 
path='/webforj/spinnerspeeddemo?'  
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/spinner/SpinnerSpeedDemoView.java'
height = '150px'
/>

## Draairichting {#spin-direction}

De draairichting van de `Spinner` kan worden gecontroleerd om **met de klok mee** of **tegen de klok in** te draaien. Je kunt dit gedrag specificeren met de `setClockwise()` methode.

```java
spinner.setClockwise(false);  // Draait tegen de klok in
spinner.setClockwise(true);   // Draait met de klok mee
```

Deze optie geeft visueel een bijzondere staat aan of fungeert als een unieke ontwerpkeuze. Het veranderen van de draairichting kan helpen bij het onderscheiden van typen processen, zoals voortgang versus omkering, of bieden een distinctieve visuele aanwijzing in specifieke contexten.

<ComponentDemo 
path='/webforj/spinnerdirectiondemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/spinner/SpinnerDirectionDemoView.java'
height = '150px'
/>

## Stijling {#styling}

### Thema's {#themes}

De `Spinner` component wordt geleverd met verschillende ingebouwde thema's die je in staat stellen om snel stijlen toe te passen zonder aangepaste CSS nodig te hebben. Deze thema's veranderen het visuele uiterlijk van de spinner, waardoor deze geschikt is voor verschillende gebruikssituaties en contexten. Door deze gedefinieerde thema's te gebruiken is er consistentie in de styling gedurende je app.

Terwijl spinners verschillende situaties dienen, zijn hier enkele voorbeeldgebruikssituaties voor de verschillende thema's:

- **Primair**: Ideaal voor het benadrukken van een laadstatus die een belangrijk onderdeel van de gebruikersstroom is, zoals bij het indienen van een formulier of het verwerken van een belangrijke actie.
  
- **Succes**: Nuttig om succesvolle achtergrondprocessen weer te geven, zoals wanneer een gebruiker een formulier indient en de app de laatste stappen van het proces uitvoert.
  
- **Gevaren**: Gebruik dit voor risicovolle of hoog-stakes operaties, zoals het verwijderen van belangrijke gegevens of het maken van onomkeerbare wijzigingen, waar een visuele indicatie van urgentie of voorzichtigheid noodzakelijk is.
  
- **Waarschuwing**: Gebruik dit om een waarschuwing of minder urgente proces aan te geven, zoals wanneer de gebruiker wacht op gegevensvalidatie, maar geen onmiddellijke actie vereist is.

- **Grijs**: Werkt goed voor subtiele achtergrondprocessen, zoals laag-prioritaire of passieve laadtaken, zoals wanneer aanvullende gegevens worden opgehaald die geen directe impact op de gebruikerservaring hebben.
  
- **Info**: Geschikt voor laadscenario's waarin je extra informatie of verduidelijking aan de gebruiker biedt, zoals het weergeven van een spinner naast een bericht dat het lopende proces uitlegt.

Je kunt deze thema's programmatisch toepassen op de spinner, waarbij visuele aanwijzingen worden gegeven die aansluiten bij de context en het belang van de operatie.

Je kunt dit gedrag specificeren met de `setTheme()` methode.

<ComponentDemo 
path='/webforj/spinnerthemedemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/spinner/SpinnerThemeDemoView.java'
height = '100px'
/>

### Expansies {#expanses}

Je kunt de grootte van de spinner, bekend als **expanse**, aanpassen om in de visuele ruimte te passen die je nodig hebt. De spinner ondersteunt verschillende maten, waaronder `Expanse.SMALL`, `Expanse.MEDIUM`, en `Expanse.LARGE`.

<ComponentDemo 
path= '/webforj/spinnerexpansedemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/spinner/SpinnerExpanseDemoView.java'
height = '100px'
/>

<TableBuilder name="Spinner" />
