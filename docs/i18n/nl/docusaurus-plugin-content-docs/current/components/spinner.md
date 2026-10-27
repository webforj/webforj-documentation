---
title: Spinner
sidebar_position: 110
description: >-
  Indicate background activity with the Spinner component, configuring theme,
  expanse, rotation speed, and pause or resume.
_i18n_hash: 22812c9195f148410b746c3547a0f118
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-spinner" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="spinner" location="com/webforj/component/spinner/Spinner" top='true'/>

De `Spinner` component biedt een visuele indicator voor aanhoudende verwerking of laden op de achtergrond. Het wordt vaak gebruikt om aan te geven dat het systeem gegevens ophaalt of wanneer een proces tijd nodig heeft om te voltooien. De `Spinner` biedt feedback aan de gebruiker, wat aangeeft dat het systeem actief bezig is.

<!-- INTRO_END -->

Maak een `Spinner`-instantie en definieer vervolgens zijn uiterlijk en gedrag met methoden zoals `setTheme()` en `setExpanse()`.

<ComponentDemo
path='/webforj/spinnerdemo'
files={['src/main/java/com/webforj/samples/views/spinner/SpinnerDemoView.java']}
height='225px'
/>

## Snelheid beheren en pauzeren {#managing-speed-and-pausing}

Het is mogelijk om de snelheid in milliseconden voor de `Spinner` in te stellen en de animatie eenvoudig te pauzeren/herstarten.

Toepassingen voor het instellen van snelheden zijn onder meer het onderscheiden van laadprocessen. Bijvoorbeeld, snellere `Spinners` zijn geschikt voor kleinere taken, terwijl langzamere `Spinners` beter zijn voor grotere taken. Pauzeren is nuttig wanneer gebruikersactie of bevestiging vereist is voordat het proces wordt voortgezet.

### Snelheid aanpassen {#adjusting-speed}

Je kunt regelen hoe snel de `Spinner` draait door zijn snelheid in milliseconden aan te passen met de `setSpeed()`-methode. Een lagere waarde maakt de `Spinner` sneller, terwijl hogere waarden deze vertragen.

```java
spinner.setSpeed(500); // Draait sneller
```

:::info Standaard snelheid
Standaard zal de `Spinner` 1000 milliseconden duren om één volledige rotatie te voltooien.
:::

### Pauzeren en hervatten {#pausing-and-resuming}

Het pauzeren van de `Spinner` is nuttig wanneer een programma tijdelijk is gepauzeerd of wacht op gebruikersinvoer. Het laat gebruikers weten dat het programma in de wacht staat, in plaats van actief te draaien, wat de duidelijkheid tijdens meerstapsprocessen vergroot.

Om de Spinner te pauzeren en te hervatten, gebruik je de `setPaused()`-methode. Dit is bijzonder nuttig wanneer je de draaiende animatie tijdelijk moet stoppen.

```java
spinner.setPaused(true);  // Pauzeert de spinner
spinner.setPaused(false); // Hervat de spinner
```

Dit voorbeeld laat zien hoe je de snelheid kunt instellen en hoe je de `Spinner` kunt pauzeren/hervatten:

<ComponentDemo
path='/webforj/spinnerspeeddemo'
files={['src/main/java/com/webforj/samples/views/spinner/SpinnerSpeedDemoView.java']}
height='150px'
/>

## Draairichting {#spin-direction}

De richting van de `Spinner` kan worden geregeld om **met de klok mee** of **tegen de klok in** te draaien. Je kunt dit gedrag specificeren met de `setClockwise()`-methode.

```java
spinner.setClockwise(false);  // Draait tegen de klok in
spinner.setClockwise(true);   // Draait met de klok mee
```

Deze optie geeft visueel een speciale staat aan of dient als een unieke ontwerpkeuze. Het wijzigen van de draairichting kan helpen om verschillende soorten processen te onderscheiden, zoals voortgang versus omkering, of een onderscheidend visueel signaal te bieden in specifieke contexten.

<ComponentDemo
path='/webforj/spinnerdirectiondemo'
files={['src/main/java/com/webforj/samples/views/spinner/SpinnerDirectionDemoView.java']}
height='150px'
/>

## Stijl {#styling}

### Thema's {#themes}

De `Spinner` component wordt geleverd met verschillende ingebouwde thema's waarmee je snel stijlen kunt toepassen zonder aangepaste CSS nodig te hebben. Deze thema's veranderen het visuele uiterlijk van de spinner, waardoor deze geschikt wordt voor verschillende gebruikstoepassingen en contexten. Het gebruik van deze vooraf gedefinieerde thema's zorgt voor consistentie in de opmaak gedurende je app.

Hoewel spinners voor verschillende situaties dienen, zijn hier enkele voorbeeldtoepassingen voor de verschillende thema's:

- **Primair**: Ideaal om een laadstatus te benadrukken die een cruciaal onderdeel van de gebruikersstroom is, zoals bij het indienen van een formulier of het verwerken van een belangrijke actie.

- **Succes**: Nuttig om succesvolle achtergrondprocessen weer te geven, zoals wanneer een gebruiker een formulier indient en de app de laatste stappen van het proces uitvoert.

- **Gevaar**: Gebruik dit voor risicovolle of hoog-stakes operaties, zoals het verwijderen van belangrijke gegevens of het maken van onomkeerbare wijzigingen, waarbij een visuele indicator van urgentie of voorzichtigheid noodzakelijk is.

- **Waarschuwing**: Gebruik dit om een waarschuwings- of minder urgente proces aan te geven, zoals wanneer de gebruiker wacht op gegevenvalidatie, maar geen onmiddellijke actie vereist.

- **Grijs**: Werkt goed voor subtiele achtergrondprocessen, zoals taken met lage prioriteit of passieve laadactiviteiten, zoals het ophalen van aanvullende gegevens die de gebruikerservaring niet rechtstreeks beïnvloeden.

- **Informatie**: Geschikt voor laadsituaties waarbij je aanvullende informatie of verduidelijking aan de gebruiker biedt, zoals het weergeven van een spinner naast een bericht dat het lopende proces uitlegt.

Je kunt deze thema's programatisch aan de spinner toepassen, met visuele aanwijzingen die aansluiten bij de context en belangrijkheid van de operatie.

Je kunt dit gedrag specificeren met de `setTheme()`-methode.

<ComponentDemo
path='/webforj/spinnerthemedemo'
files={['src/main/java/com/webforj/samples/views/spinner/SpinnerThemeDemoView.java']}
height='100px'
/>

### Uitbreidingen {#expanses}

Je kunt de grootte van de spinner aanpassen, ook wel **uitbreiding** genoemd, om in de visuele ruimte te passen die je nodig hebt. De spinner ondersteunt verschillende maten, waaronder `Expanse.SMALL`, `Expanse.MEDIUM`, en `Expanse.LARGE`.

<ComponentDemo
path='/webforj/spinnerexpansedemo'
files={['src/main/java/com/webforj/samples/views/spinner/SpinnerExpanseDemoView.java']}
height='100px'
/>

<TableBuilder name="Spinner" />
