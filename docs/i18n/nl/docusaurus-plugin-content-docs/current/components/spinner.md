---
title: Spinner
sidebar_position: 110
_i18n_hash: 8ab95efdcfcc1e42df56c372da27cc81
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-spinner" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="spinner" location="com/webforj/component/spinner/Spinner" top='true'/>

De `Spinner` component biedt een visuele indicator die aangeeft dat er een verwerking of laadproces op de achtergrond plaatsvindt. Het wordt vaak gebruikt om aan te geven dat het systeem gegevens aan het ophalen is of wanneer een proces tijd nodig heeft om te voltooien. De spinner biedt gebruikersfeedback en geeft aan dat het systeem actief bezig is.

## Basisprincipes {#basics}

Om een `Spinner` te maken, kun je het thema en de expanse specificeren. De basis syntaxis omvat het maken van een `Spinner` instantie en het definiëren van het uiterlijk en gedrag via methoden zoals `setTheme()` en `setExpanse()`.

<ComponentDemo 
path='/webforj/spinnerdemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/spinner/SpinnerDemoView.java'
cssURL='/css/spinnerstyles/spinnerdemo.css'
height = '225px'
/>

## Snelheid beheren en pauzeren {#managing-speed-and-pausing}

Het is mogelijk om de snelheid in milliseconden voor de `Spinner` in te stellen en de animatie eenvoudig te pauzeren/herstarten.

Toepassingen voor het instellen van snelheden omvatten het differentiëren tussen laadprocessen. Snellere `Spinners` zijn geschikt voor kleinere taken, terwijl langzamere `Spinners` beter zijn voor grotere taken. Pauzeren is handig wanneer gebruikersactie of bevestiging vereist is voordat het proces wordt voortgezet.

### Snelheid aanpassen {#adjusting-speed}

Je kunt de snelheid waarmee de `Spinner` draait regelen door de snelheid in milliseconden aan te passen met de `setSpeed()` methode. Een lagere waarde zorgt ervoor dat de `Spinner` sneller draait, terwijl hogere waarden deze vertragen.

```java
spinner.setSpeed(500); // Draait sneller
```

:::info Standaard snelheid
Standaard zal de `Spinner` 1000 milliseconden nodig hebben om een volledige rotatie te voltooien.
:::

### Pauzeren en hervatten {#pausing-and-resuming}

Het pauzeren van de `Spinner` is nuttig wanneer een programma tijdelijk wordt gepauzeerd of wacht op gebruikersinvoer. Het laat gebruikers weten dat het programma in de wacht staat, in plaats van actief te draaien, wat de helderheid tijdens meerstappenprocessen verbetert.

Om de Spinner te pauzeren en te hervatten, gebruik je de `setPaused()` methode. Dit is vooral nuttig wanneer je de draaiende animatie tijdelijk wilt stoppen.

```java
spinner.setPaused(true);  // Pauzeer de spinner
spinner.setPaused(false); // Hervat de spinner
```

Dit voorbeeld laat zien hoe je de snelheid kunt instellen en hoe je de `Spinner` kunt pauzeren/hervatten:

<ComponentDemo 
path='/webforj/spinnerspeeddemo?'  
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/spinner/SpinnerSpeedDemoView.java'
cssURL='/css/spinnerstyles/spinnerspeeddemo.css'
height = '150px'
/>

## Draairichting {#spin-direction}

De draairichting van de `Spinner` kan worden geregeld om **met de klok mee** of **tegen de klok in** te draaien. Je kunt dit gedrag specificeren met de `setClockwise()` methode.

```java
spinner.setClockwise(false);  // Draait tegen de klok in
spinner.setClockwise(true);   // Draait met de klok mee
```

Deze optie geeft visueel een speciale status aan of dient als een unieke ontwerpkeuze. Het wijzigen van de draairichting kan helpen om verschillende proces types te onderscheiden, zoals voortgang versus omkering, of een onderscheidend visueel signaal te bieden in specifieke contexten.

<ComponentDemo 
path='/webforj/spinnerdirectiondemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/spinner/SpinnerDirectionDemoView.java'
height = '150px'
/>

## Stijlen {#styling}

### Thema's {#themes}

De `Spinner` component wordt geleverd met verschillende ingebouwde thema's die je in staat stellen om snel stijlen toe te passen zonder dat je aangepaste CSS nodig hebt. Deze thema's veranderen het visuele uiterlijk van de spinner, zodat deze geschikt is voor verschillende gebruikssituaties en contexten. Het gebruik van deze vooraf gedefinieerde thema's zorgt voor consistentie in de styling door de hele app.

Terwijl spinners voor verschillende situaties dienen, zijn hier enkele voorbeeldtoepassingen voor de verschillende thema's:

- **Primair**: Ideaal om een laadstatus te benadrukken die een essentieel onderdeel van de gebruikersflow is, zoals tijdens het indienen van een formulier of het verwerken van een belangrijke actie.
  
- **Succes**: Nuttig om succesvolle achtergrondprocessen weer te geven, zoals wanneer een gebruiker een formulier indient en de app de laatste stappen van het proces uitvoert.
  
- **Gevaar**: Gebruik dit voor risicovolle of hoog-stakes operaties, zoals het verwijderen van belangrijke gegevens of het aanbrengen van onomkeerbare wijzigingen, waarbij een visuele indicator van urgentie of voorzichtigheid nodig is.
  
- **Waarschuwing**: Gebruik dit om een waarschuwend of minder dringend proces aan te geven, zoals wanneer de gebruiker wacht op gegevensvalidatie, maar geen onmiddellijke actie vereist is.

- **Grijs**: Werkt goed voor subtiele achtergrondprocessen, zoals taken met lage prioriteit of passieve laadprocessen, zoals het ophalen van aanvullende gegevens dat de gebruikerservaring niet rechtstreeks beïnvloedt.
  
- **Info**: Geschikt voor laadscenario's waarbij je aanvullende informatie of verduidelijking aan de gebruiker biedt, zoals het weergeven van een spinner naast een bericht dat het lopende proces uitlegt.

Je kunt deze thema's programmatisch op de spinner toepassen, waardoor visuele aanwijzingen worden gegeven die aansluiten bij de context en belangrijkheid van de operatie.

Je kunt dit gedrag specificeren met de `setTheme()` methode.

<ComponentDemo 
path='/webforj/spinnerthemedemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/spinner/SpinnerThemeDemoView.java'
cssURL='/css/spinnerstyles/spinnerthemedemo.css'
height = '100px'
/>

### Expansies {#expanses}

Je kunt de grootte van de spinner aanpassen, bekend als **expanse**, om in de visuele ruimte te passen die je nodig hebt. De spinner ondersteunt verschillende formaten, waaronder `Expanse.SMALL`, `Expanse.MEDIUM` en `Expanse.LARGE`.

<ComponentDemo 
path= '/webforj/spinnerexpansedemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/spinner/SpinnerExpanseDemoView.java'
cssURL='/css/spinnerstyles/spinnerexpansedemo.css'
height = '100px'
/>

<TableBuilder name="Spinner" />
