---
title: Spinner
sidebar_position: 110
_i18n_hash: b1137c43133bce5c5a16df51c0aa82e3
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-spinner" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="spinner" location="com/webforj/component/spinner/Spinner" top='true'/>

De `Spinner` component biedt een visuele indicator die aangeeft dat er op de achtergrond een verwerking of laadproces plaatsvindt. Het wordt vaak gebruikt om aan te geven dat het systeem gegevens ophaalt of wanneer een proces tijd nodig heeft om te voltooien. De spinner biedt gebruikersfeedback, waarmee wordt aangegeven dat het systeem actief aan het werk is.

## Basisprincipes {#basics}

Om een `Spinner` te maken, kun je het thema en de omvang specificeren. De basis syntaxis omvat het maken van een `Spinner` instantie en het definiëren van zijn uiterlijk en gedrag via methoden zoals `setTheme()` en `setExpanse()`.

<ComponentDemo 
path='/webforj/spinnerdemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/spinner/SpinnerDemoView.java'
cssURL='/css/spinnerstyles/spinnerdemo.css'
height = '225px'
/>

## Snelheid en pauzeren beheren {#managing-speed-and-pausing}

Het is mogelijk om de snelheid in milliseconden voor de `Spinner` in te stellen en de animatie eenvoudig te pauzeren/herstarten.

Gebruik scenario's voor het instellen van snelheden zijn onder andere het onderscheiden van laadprocessen. Snellere `Spinners` zijn geschikt voor kleinere taken, terwijl langzamere `Spinners` beter zijn voor grotere taken. Pauzeren is nuttig wanneer gebruikersactie of bevestiging vereist is voordat het proces wordt voortgezet.

### Snelheid aanpassen {#adjusting-speed}

Je kunt de snelheid waarmee de `Spinner` draait regelen door de snelheid in milliseconden aan te passen met de `setSpeed()` methode. Een lagere waarde zorgt ervoor dat de `Spinner` sneller draait, terwijl hogere waarden het vertragen.

```java
spinner.setSpeed(500); // Draaft sneller
```

:::info Standaard Snelheid
Standaard neemt de `Spinner` 1000 milliseconden in beslag om één volle rotatie te voltooien.
:::

### Pauzeren en herstarten {#pausing-and-resuming}

Het pauzeren van de `Spinner` is nuttig wanneer een programma tijdelijk wordt onderbroken of wacht op gebruikersinvoer. Het laat gebruikers weten dat het programma in de wachtstand staat, in plaats van actief te draaien, wat de duidelijkheid tijdens meerstapsprocessen verbetert.

Om de Spinner te pauzeren en te herstarten, gebruik je de `setPaused()` methode. Dit is bijzonder handig wanneer je de draaiende animatie tijdelijk wilt stoppen.

```java
spinner.setPaused(true);  // Pauze de spinner
spinner.setPaused(false); // Herstart de spinner
```

Dit voorbeeld laat zien hoe je de snelheid kunt instellen en hoe je de `Spinner` kunt pauzeren/herstaten:

<ComponentDemo 
path='/webforj/spinnerspeeddemo?'  
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/spinner/SpinnerSpeedDemoView.java'
cssURL='/css/spinnerstyles/spinnerspeeddemo.css'
height = '150px'
/>

## Draaibeweging {#spin-direction}

De draaibeweging van de `Spinner` kan worden geregeld om **met de klok mee** of **tegen de klok in** te draaien. Je kunt dit gedrag specificeren met de `setClockwise()` methode.

```java
spinner.setClockwise(false);  // Draait tegen de klok in
spinner.setClockwise(true);   // Draait met de klok mee
```

Deze optie geeft visueel een speciale staat aan of fungeert als een unieke ontwerpkeuze. Het veranderen van de draaibeweging kan helpen bij het onderscheiden van verschillende soorten processen, zoals voortgang versus terugdraaiing, of een onderscheidend visueel signaal bieden in specifieke contexten.

<ComponentDemo 
path='/webforj/spinnerdirectiondemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/spinner/SpinnerDirectionDemoView.java'
height = '150px'
/>

## Stylen {#styling}

### Thema's {#themes}

De `Spinner` component wordt geleverd met verschillende ingebouwde thema's die je in staat stellen om snel stijlen toe te passen zonder dat je aangepaste CSS nodig hebt. Deze thema's veranderen het visuele uiterlijk van de spinner, waardoor deze geschikt is voor verschillende gebruikssituaties en contexten. Het gebruik van deze vooraf gedefinieerde thema's zorgt voor consistentie in de styling door je app.

Hoewel spinners voor verschillende situaties dienen, zijn hier enkele voorbeeld gebruikssituaties voor de verschillende thema's:

- **Primair**: Ideaal om een laadtoestand te benadrukken die een belangrijk onderdeel van de gebruikersstroom is, zoals bij het indienen van een formulier of het verwerken van een belangrijke actie.
  
- **Succes**: Nuttig om succesvolle achtergrondsprocessen weer te geven, zoals wanneer een gebruiker een formulier indient en de app de laatste stappen van het proces uitvoert.
  
- **Gevaar**: Gebruik dit voor risicovolle of belangrijke operaties, zoals het verwijderen van belangrijke gegevens of het maken van onomkeerbare wijzigingen, waar een visuele indicator van urgentie of voorzichtigheid noodzakelijk is.
  
- **Waarschuwing**: Gebruik dit om een voorzichtige of minder urgente actie aan te geven, zoals wanneer de gebruiker wacht op gegevensvalidatie, maar geen onmiddellijke actie vereist.

- **Grijs**: Werkt goed voor subtiele achtergrondprocessen, zoals taken met een lage prioriteit of passieve laadtaken, zoals wanneer aanvullende gegevens worden opgehaald die de gebruikerservaring niet rechtstreeks beïnvloeden.
  
- **Informatie**: Geschikt voor laadsituaties waarin je de gebruiker aanvullende informatie of verduidelijking geeft, zoals het weergeven van een spinner naast een bericht dat het lopende proces uitlegt.

Je kunt deze thema's programmatisch op de spinner toepassen, waardoor visuele signalen worden gegeven die aansluiten bij de context en belangrijkheid van de operatie.

Je kunt dit gedrag specificeren met de `setTheme()` methode.

<ComponentDemo 
path='/webforj/spinnerthemedemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/spinner/SpinnerThemeDemoView.java'
cssURL='/css/spinnerstyles/spinnerthemedemo.css'
height = '100px'
/>

### Omvangen {#expanses}

Je kunt de grootte van de spinner aanpassen, die bekend staat als **omvang**, om in de visuele ruimte te passen die je nodig hebt. De spinner ondersteunt verschillende maten, waaronder `Expanse.SMALL`, `Expanse.MEDIUM`, en `Expanse.LARGE`.

<ComponentDemo 
path= '/webforj/spinnerexpansedemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/spinner/SpinnerExpanseDemoView.java'
cssURL='/css/spinnerstyles/spinnerexpansedemo.css'
height = '100px'
/>

<TableBuilder name="Spinner" />
