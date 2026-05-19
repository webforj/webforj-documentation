---
title: Spinner
sidebar_position: 110
_i18n_hash: d93d5704fff2acc975910f1a10e34d0b
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-spinner" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="spinner" location="com/webforj/component/spinner/Spinner" top='true'/>

De `Spinner` component biedt een visuele indicator die aangeeft dat er momenteel een proces of laadtijd op de achtergrond plaatsvindt. Het wordt vaak gebruikt om te tonen dat het systeem gegevens aan het ophalen is of wanneer een proces tijd kost om te voltooien. De `Spinner` biedt gebruikersfeedback en geeft aan dat het systeem actief bezig is.

<!-- INTRO_END -->

## Basisprincipes {#basics}

Om een `Spinner` te maken, kun je het thema en de expanse opgeven. De basis syntaxis omvat het creëren van een `Spinner` instantie en het definiëren van zijn uiterlijk en gedrag door middel van methoden zoals `setTheme()` en `setExpanse()`.

<ComponentDemo
path='/webforj/spinnerdemo'
files={['src/main/java/com/webforj/samples/views/spinner/SpinnerDemoView.java']}
height='225px'
/>

## Snelheid beheren en pauzeren {#managing-speed-and-pausing}

Het is mogelijk om de snelheid in milliseconden voor de `Spinner` in te stellen en de animatie eenvoudig te pauzeren/te hervatten. 

Gebruikscases voor het instellen van snelheden omvatten het differentiëren tussen laadprocessen. Snellere `Spinners` zijn geschikt voor kleinere taken, terwijl langzamere `Spinners` beter zijn voor grotere taken. Pauzeren is nuttig wanneer gebruikersactie of bevestiging vereist is voordat het proces wordt voortgezet.

### Snelheid aanpassen {#adjusting-speed}

Je kunt controleren hoe snel de `Spinner` draait door de snelheid in milliseconden aan te passen met de `setSpeed()` methode. Een lagere waarde maakt dat de `Spinner` sneller draait, terwijl hogere waarden het vertragen.

```java
spinner.setSpeed(500); // Draait sneller
```

:::info Standaard Snelheid
Standaard heeft de `Spinner` 1000 milliseconden nodig om één volledige rotatie te voltooien.
:::

### Pauzeren en hervatten {#pausing-and-resuming}

Het pauzeren van de `Spinner` is nuttig wanneer een programma tijdelijk is stopgezet of wacht op gebruikersinvoer. Het laat gebruikers weten dat het programma is gepauzeerd in plaats van actief te draaien, wat de duidelijkheid tijdens meerstapsprocessen vergroot.

Om de Spinner te pauzeren en te hervatten, gebruik je de `setPaused()` methode. Dit is vooral handig wanneer je de draaiende animatie tijdelijk wilt stopzetten.      

```java
spinner.setPaused(true);  // Pauze de spinner
spinner.setPaused(false); // Hervat de spinner
```

Dit voorbeeld toont hoe je de snelheid kunt instellen en hoe je de `Spinner` kunt pauzeren/hervatten:

<ComponentDemo
path='/webforj/spinnerspeeddemo'
files={['src/main/java/com/webforj/samples/views/spinner/SpinnerSpeedDemoView.java']}
height='150px'
/>

## Draairichting {#spin-direction}

De draairichting van de `Spinner` kan worden ingesteld om **met de klok mee** of **tegen de klok in** te draaien. Je kunt dit gedrag specificeren met de `setClockwise()` methode.

```java
spinner.setClockwise(false);  // Draait tegen de klok in
spinner.setClockwise(true);   // Draait met de klok mee
```

Deze optie geeft visueel een speciale status aan of dient als een unieke ontwerpkeuze. Het veranderen van de draairichting kan helpen om verschillende soorten processen te onderscheiden, zoals voortgang versus omkering, of een duidelijke visuele aanwijzing te geven in specifieke contexten.

<ComponentDemo
path='/webforj/spinnerdirectiondemo'
files={['src/main/java/com/webforj/samples/views/spinner/SpinnerDirectionDemoView.java']}
height='150px'
/>

## Stylen {#styling}

### Thema's {#themes}

De `Spinner` component wordt geleverd met verschillende ingebouwde thema's die je in staat stellen om snel stijlen toe te passen zonder dat je aangepaste CSS nodig hebt. Deze thema's veranderen het visuele uiterlijk van de spinner, waardoor het geschikt is voor verschillende gebruikssituaties en contexten. Het gebruik van deze vooraf gedefinieerde thema's zorgt voor consistentie in de styling van je app.

Hoewel spinners in verschillende situaties worden gebruikt, zijn hier enkele voorbeelden van gebruikssituaties voor de verschillende thema's:

- **Primair**: Ideaal voor het benadrukken van een laadstatus die een belangrijk onderdeel is van de gebruikersflow, zoals bij het indienen van een formulier of het verwerken van een belangrijke actie.
  
- **Succes**: Nuttig om succesvolle achtergrondprocessen weer te geven, zoals wanneer een gebruiker een formulier indient en de app de laatste stappen van het proces uitvoert.
  
- **Gevaren**: Gebruik dit voor risicovolle of hoge-stakes operaties, zoals het verwijderen van belangrijke gegevens of het maken van onomkeerbare wijzigingen, waar een visuele indicator van urgentie of voorzichtigheid noodzakelijk is.
  
- **Waarschuwing**: Gebruik dit om een voorzichtig of minder urgent proces aan te geven, zoals wanneer de gebruiker wacht op gegevensvalidatie, maar geen onmiddellijke actie vereist.

- **Grijs**: Werkt goed voor subtiele achtergrondprocessen, zoals laag-prioriteit of passieve laadtaken, zoals bij het ophalen van aanvullende gegevens die de gebruikerservaring niet direct beïnvloeden.
  
- **Informatie**: Geschikt voor laadscenario's waarin je aanvullende informatie of verduidelijking aan de gebruiker verstrekt, zoals het weergeven van een spinner naast een bericht dat het aanhoudende proces uitlegt.

Je kunt deze thema's programmatisch op de spinner toepassen, waardoor visuele aanwijzingen worden gegeven die aansluiten bij de context en de belangrijkheid van de operatie.

Je kunt dit gedrag specificeren met de `setTheme()` methode.

<ComponentDemo
path='/webforj/spinnerthemedemo'
files={['src/main/java/com/webforj/samples/views/spinner/SpinnerThemeDemoView.java']}
height='100px'
/>

### Expansies {#expanses}

Je kunt de grootte van de spinner aanpassen, bekend als **expanse**, om in de visuele ruimte te passen die je nodig hebt. De spinner ondersteunt verschillende maten, waaronder `Expanse.SMALL`, `Expanse.MEDIUM`, en `Expanse.LARGE`.

<ComponentDemo
path='/webforj/spinnerexpansedemo'
files={['src/main/java/com/webforj/samples/views/spinner/SpinnerExpanseDemoView.java']}
height='100px'
/>

<TableBuilder name="Spinner" />
