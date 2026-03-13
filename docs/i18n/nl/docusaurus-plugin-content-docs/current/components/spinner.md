---
title: Spinner
sidebar_position: 110
_i18n_hash: c60e7d3c3604a39de7f659f169d973a6
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-spinner" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="spinner" location="com/webforj/component/spinner/Spinner" top='true'/>

De `Spinner` component biedt een visuele indicator die aangeeft dat er een verwerking of laden op de achtergrond plaatsvindt. Het wordt vaak gebruikt om aan te geven dat het systeem gegevens ophaalt of wanneer een proces tijd kost om te voltooien. De `Spinner` biedt gebruikersfeedback en signaleert dat het systeem actief aan het werk is.

<!-- INTRO_END -->

## Basisprincipes {#basics}

Om een `Spinner` te maken, kunt u het thema en de expanse specificeren. De basis syntaxis omvat het creëren van een `Spinner` instantie en het definiëren van zijn uiterlijk en gedrag via methoden zoals `setTheme()` en `setExpanse()`.

<ComponentDemo 
path='/webforj/spinnerdemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/spinner/SpinnerDemoView.java'
cssURL='/css/spinnerstyles/spinnerdemo.css'
height = '225px'
/>

## Snelheid en pauzeren beheren {#managing-speed-and-pausing}

Het is mogelijk om de snelheid in milliseconden voor de `Spinner` in te stellen en de animatie eenvoudig te pauzeren/herstarten. 

Toepassingen voor het instellen van snelheden omvatten het onderscheiden van laadtaken. Snellere `Spinners` zijn geschikt voor kleinere taken, terwijl langzamere `Spinners` beter zijn voor grotere taken. Pauzeren is nuttig wanneer er gebruikersactie of bevestiging nodig is voordat het proces wordt voortgezet.

### Snelheid aanpassen {#adjusting-speed}

U kunt de snelheid waarmee de `Spinner` draait regelen door de snelheid in milliseconden aan te passen met behulp van de `setSpeed()` methode. Een lagere waarde laat de `Spinner` sneller draaien, terwijl hogere waarden het vertragend maken.

```java
spinner.setSpeed(500); // Draait sneller
```

:::info Standaard snelheid
Standaard duurt het 1000 milliseconden voordat de `Spinner` één volledige rotatie maakt.
:::

### Pauzeren en hervatten {#pausing-and-resuming}

Het pauzeren van de `Spinner` is nuttig wanneer een programma tijdelijk is gepauzeerd of wacht op gebruikersinvoer. Het laat gebruikers weten dat het programma in de wacht staat, in plaats van actief te draaien, wat de duidelijkheid tijdens meerstaps processen bevordert.

Om de Spinner te pauzeren en te hervatten, gebruikt u de `setPaused()` methode. Dit is bijzonder nuttig wanneer u de rotatie-animatie tijdelijk moet stoppen.      

```java
spinner.setPaused(true);  // Pauzeert de spinner
spinner.setPaused(false); // Hervat de spinner
```

Dit voorbeeld laat zien hoe u de snelheid kunt instellen en hoe u de `Spinner` kunt pauzeren/hervatten:

<ComponentDemo 
path='/webforj/spinnerspeeddemo?'  
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/spinner/SpinnerSpeedDemoView.java'
cssURL='/css/spinnerstyles/spinnerspeeddemo.css'
height = '150px'
/>

## Draairichting {#spin-direction}

De draairichting van de `Spinner` kan worden geregeld om **met de klok mee** of **tegen de klok in** te draaien. U kunt dit gedrag specificeren met behulp van de `setClockwise()` methode.

```java
spinner.setClockwise(false);  // Draait tegen de klok in
spinner.setClockwise(true);   // Draait met de klok mee
```

Deze optie geeft visueel een speciale staat aan of dient als een unieke ontwerpkeuze. Het veranderen van de draairichting kan helpen om typen processen te onderscheiden, zoals voortgang versus omkering, of een duidelijke visuele aanwijzing te geven in specifieke contexten.

<ComponentDemo 
path='/webforj/spinnerdirectiondemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/spinner/SpinnerDirectionDemoView.java'
height = '150px'
/>

## Stijlen {#styling}

### Thema's {#themes}

De `Spinner` component wordt geleverd met verschillende ingebouwde thema's die het mogelijk maken om snel stijlen toe te passen zonder aangepaste CSS nodig te hebben. Deze thema's veranderen het visuele uiterlijk van de spinner, waardoor deze geschikt is voor verschillende gebruikssituaties en contexten. Het gebruik van deze vooraf gedefinieerde thema's zorgt voor consistentie in de styling van uw app.

Hoewel spinners voor verschillende situaties dienen, zijn hier enkele voorbeeldtoepassingen voor de verschillende thema's:

- **Primair**: Ideaal om een laadstaat te benadrukken die een belangrijk onderdeel van de gebruikersflow is, zoals bij het indienen van een formulier of het verwerken van een belangrijke actie.
  
- **Succes**: Nuttig om succesvolle achtergrondprocessen weer te geven, zoals wanneer een gebruiker een formulier indient en de app de laatste stappen van het proces uitvoert.
  
- **Gevaren**: Gebruik dit voor risicovolle of belangrijke operaties, zoals het verwijderen van belangrijke gegevens of het maken van onomkeerbare wijzigingen, waarbij een visuele indicator van urgentie of voorzichtigheid nodig is.
  
- **Waarschuwing**: Gebruik dit om een waarschuwend of minder urgent proces aan te geven, zoals wanneer de gebruiker wacht op gegevensvalidatie, maar geen onmiddellijke actie vereist.

- **Grijs**: Werkt goed voor subtiele achtergrondprocessen, zoals taken met een lage prioriteit of passieve laadtaken, zoals wanneer aanvullende gegevens worden opgehaald die geen directe impact hebben op de gebruikerservaring.
  
- **Informatie**: Geschikt voor laadsituaties waarin u aanvullende informatie of verduidelijking aan de gebruiker biedt, zoals het weergeven van een spinner naast een boodschap die het lopende proces uitlegt.

U kunt deze thema's programmatisch op de spinner toepassen, wat visuele aanwijzingen biedt die in lijn zijn met de context en belangrijkheid van de operatie.

U kunt dit gedrag specificeren met behulp van de `setTheme()` methode.

<ComponentDemo 
path='/webforj/spinnerthemedemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/spinner/SpinnerThemeDemoView.java'
cssURL='/css/spinnerstyles/spinnerthemedemo.css'
height = '100px'
/>

### Expansies {#expanses}

U kunt de grootte van de spinner aanpassen, die bekend staat als **expanse**, om te passen bij de visuele ruimte die u nodig heeft. De spinner ondersteunt verschillende maten, waaronder `Expanse.SMALL`, `Expanse.MEDIUM` en `Expanse.LARGE`.

<ComponentDemo 
path= '/webforj/spinnerexpansedemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/spinner/SpinnerExpanseDemoView.java'
cssURL='/css/spinnerstyles/spinnerexpansedemo.css'
height = '100px'
/>

<TableBuilder name="Spinner" />
