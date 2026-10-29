---
title: Spinner
sidebar_position: 110
description: >-
  Indicate background activity with the Spinner component, configuring theme,
  expanse, rotation speed, and pause or resume.
_i18n_hash: 6a667297edb572a6990b58bee72c8b1b
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-spinner" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="spinner" location="com/webforj/component/spinner/Spinner" top='true'/>

De `Spinner` component biedt een visuele indicator die aangeeft dat er op de achtergrond een proces of laadactie plaatsvindt. Het wordt vaak gebruikt om te laten zien dat het systeem gegevens aan het ophalen is of wanneer een proces tijd kost om te voltooien. De `Spinner` biedt gebruikersfeedback, waarmee wordt aangegeven dat het systeem actief aan het werk is.

<!-- INTRO_END -->

Maak een `Spinner` instantie en definieer vervolgens het uiterlijk en gedrag met methoden zoals `setTheme()` en `setExpanse()`.

<ComponentDemo
path='/webforj/spinnerdemo'
files={['src/main/java/com/webforj/samples/views/spinner/SpinnerDemoView.java']}
height='225px'
/>

## Snelheid en pauzeren beheren {#managing-speed-and-pausing}

Het is mogelijk om de snelheid in milliseconden voor de `Spinner` in te stellen en de animatie eenvoudig te pauzeren/resumeren.

Gebruikssituaties voor het instellen van snelheden omvatten het onderscheiden van laadprocessen. Snellere `Spinners` zijn geschikt voor kleinere taken, terwijl langzamere `Spinners` beter zijn voor grotere taken. Pauzeren is nuttig wanneer gebruikersactie of bevestiging vereist is voordat het proces wordt voortgezet.

### Snelheid aanpassen {#adjusting-speed}

U kunt de snelheid waarmee de `Spinner` draait regelen door deze in milliseconden aan te passen met de `setSpeed()` methode. Een lagere waarde zorgt ervoor dat de `Spinner` sneller draait, terwijl hogere waarden ervoor zorgen dat deze langzamer draait.

```java
spinner.setSpeed(500); // Draait sneller
```

:::info Standaardsnelheid
Standaard heeft de `Spinner` 1000 milliseconden nodig om een volledige rotatie te voltooien.
:::

### Pauzeren en hervatten {#pausing-and-resuming}

Het pauzeren van de `Spinner` is nuttig wanneer een programma tijdelijk wordt stopgezet of wacht op gebruikersinvoer. Dit laat gebruikers weten dat het programma in afwachting is en niet actief draait, wat de helderheid tijdens meerdere stappenprocessen vergroot.

Om de Spinner te pauzeren en te hervatten, gebruikt u de `setPaused()` methode. Dit is bijzonder handig wanneer u de draaiende animatie tijdelijk moet stoppen.

```java
spinner.setPaused(true);  // Pauzeert de spinner
spinner.setPaused(false); // Hervat de spinner
```

Dit voorbeeld laat zien hoe u de snelheid kunt instellen en hoe u de `Spinner` kunt pauzeren/hervatten:

<ComponentDemo
path='/webforj/spinnerspeeddemo'
files={['src/main/java/com/webforj/samples/views/spinner/SpinnerSpeedDemoView.java']}
height='150px'
/>

## Draairichting {#spin-direction}

De richting van de `Spinner` kan worden geregeld om **met de klok mee** of **tegen de klok in** te draaien. U kunt dit gedrag specificeren met de `setClockwise()` methode.

```java
spinner.setClockwise(false);  // Draait tegen de klok in
spinner.setClockwise(true);   // Draait met de klok mee
```

Deze optie geeft visueel een speciale staat aan of dient als een unieke ontwerpkeuze. Het veranderen van de draairichting kan helpen om verschillende soorten processen te onderscheiden, zoals voortgang versus omkering, of kan een onderscheidende visuele aanwijzing geven in specifieke contexten.

<ComponentDemo
path='/webforj/spinnerdirectiondemo'
files={['src/main/java/com/webforj/samples/views/spinner/SpinnerDirectionDemoView.java']}
height='150px'
/>

## Stijling {#styling}

### Thema's {#themes}

De `Spinner` component wordt geleverd met verschillende ingebouwde thema's waarmee u snel stijlen kunt toepassen zonder aangepaste CSS nodig te hebben. Deze thema's veranderen het visuele uiterlijk van de spinner, waardoor deze geschikt is voor verschillende gebruikssituaties en contexten. Het gebruik van deze vooraf gedefinieerde thema's zorgt voor consistentie in de stijl door uw app.

Hoewel spinners voor verschillende situaties dienen, zijn hier enkele voorbeeldgebruikssituaties voor de verschillende thema's:

- **Primair**: Ideaal om een laadtoestand te benadrukken die een belangrijk onderdeel van de gebruikersflow is, zoals tijdens het indienen van een formulier of het verwerken van een belangrijke actie.

- **Succes**: Nuttig om succesvolle achtergrondprocessen weer te geven, zoals wanneer een gebruiker een formulier indient en de app de laatste stappen van het proces uitvoert.

- **Gevaren**: Gebruik dit voor risicovolle of hoogstakes operaties, zoals het verwijderen van belangrijke gegevens of het maken van onomkeerbare wijzigingen, waar een visuele indicator van urgentie of voorzichtigheid noodzakelijk is.

- **Waarschuwing**: Gebruik dit om een waarschuwings- of minder urgente process aan te geven, zoals wanneer de gebruiker wacht op gegevensvalidatie, maar geen onmiddellijke actie vereist is.

- **Grijs**: Werkt goed voor subtiele achtergrondprocessen, zoals taken met lage prioriteit of passieve laadacties, zoals het ophalen van aanvullende gegevens die geen directe impact op de gebruikerservaring hebben.

- **Informatie**: Geschikt voor laadsituaties waarin u de gebruiker aanvullende informatie of verduidelijking biedt, zoals het tonen van een spinner naast een bericht dat het lopende proces uitlegt.

U kunt deze thema's programmatisch op de spinner toepassen en visuele aanwijzingen geven die passen bij de context en belangrijkheid van de operatie.

U kunt dit gedrag specificeren met de `setTheme()` methode.

<ComponentDemo
path='/webforj/spinnerthemedemo'
files={['src/main/java/com/webforj/samples/views/spinner/SpinnerThemeDemoView.java']}
height='100px'
/>

### Expansies {#expanses}

U kunt de grootte van de spinner, ook wel **expanse** genoemd, aanpassen met `setExpanse()` met de `SpinnerExpanse` enum. De negen beschikbare maten zijn `XXXSMALL`, `XXSMALL`, `XSMALL`, `SMALL`, `MEDIUM`, `LARGE`, `XLARGE`, `XXLARGE` en `XXXLARGE`. De standaardwaarde is `SpinnerExpanse.NONE`.

```java
spinner.setExpanse(SpinnerExpanse.LARGE);
```

<ComponentDemo
path='/webforj/spinnerexpansedemo'
files={['src/main/java/com/webforj/samples/views/spinner/SpinnerExpanseDemoView.java']}
height='100px'
/>

<TableBuilder name="Spinner" />
