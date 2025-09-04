---
title: BusyIndicator
sidebar_position: 10
_i18n_hash: a61f487d0d763856c6055898a7284011
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-loading" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/BusyIndicator" top='true'/>

De `BusyIndicator` biedt visuele aanwijzingen om ervoor te zorgen dat gebruikers zich bewust zijn van lopende processen, zodat ze niet te vroeg met het systeem interageren. Het bedekt meestal de volledige app-interface voor globale bewerkingen.

Terwijl de [`Loading`](../components/loading) component zich richt op specifieke gebieden of componenten binnen de app, behandelt de `BusyIndicator` globale, app-brede processen en blokkeert de interactie over de hele interface. Dit verschil in reikwijdte maakt de [`Loading`](../components/loading) component ideaal voor meer gelokaliseerde, component-specifieke scenario's, zoals het laden van gegevens in een bepaald gedeelte van een pagina. Daarentegen is de `BusyIndicator` geschikt voor systeem-brede bewerkingen die de hele app beïnvloeden, zoals het initialiseren van de app of het uitvoeren van een grote gegevenssync.

## Basics {#basics}

De `BusyIndicator` in webforJ verschijnt als een eenvoudige spinner, waardoor hij gemakkelijk te gebruiken is zonder configuratie. Echter, je kunt het aanpassen door een bericht toe te voegen, het thema van de spinner aan te passen of de zichtbaarheidinstellingen te wijzigen. Dit stelt je in staat om meer context of stijl te bieden, terwijl je een functionele, kant-en-klare oplossing behoudt.

In dit voorbeeld voorkomt de `BusyIndicator` dat gebruikersacties plaatsvinden over de hele interface totdat de bewerking is voltooid.

<ComponentDemo 
path='/webforj/busydemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/busyindicator/BusyDemoView.java'
height = '300px'
/>

## Backdrops {#backdrops}

De `BusyIndicator` component in webforJ stelt je in staat om een backdrop weer te geven om interactie van de gebruiker te blokkeren terwijl een proces aan de gang is. Standaard staat de component de backdrop toe, maar je hebt de optie om deze uit te schakelen indien nodig.

De `BusyIndicator` toont standaard een backdrop. Je kunt de zichtbaarheid van de backdrop regelen met de `setBackdropVisible()` methode, zoals hieronder weergegeven:

```java
BusyIndicator busyIndicator = getBusyIndicator();
busyIndicator.setBackdropVisible(false);  // Zet de backdrop uit
busyIndicator.open();
```
:::info De Backdrop Uitschakelen
Zelfs wanneer je de backdrop uitschakelt, blokkeert de `BusyIndicator` component nog steeds de interactie van de gebruiker om ervoor te zorgen dat het onderliggende proces ononderbroken wordt voltooid. De backdrop regelt eenvoudig de visuele overlay, niet het blokkeren van interactie.
:::

## `Spinner` {#spinner}

De `BusyIndicator` component in webforJ bevat een `Spinner` die visueel aangeeft dat een achtergrondbewerking aan de gang is. Je kunt deze spinner aanpassen met verschillende opties, waaronder de grootte, snelheid, richting, thema en zichtbaarheid.

Hier is een voorbeeld van hoe je de spinner kunt aanpassen binnen een `BusyIndicator` component:

<ComponentDemo 
path='/webforj/busyspinnerdemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/busyindicator/BusySpinnerDemoView.java'
height = '200px'
/>

## Use cases {#use-cases}
- **Pagina-brede Verwerking**  
   De `BusyIndicator` is goed geschikt voor grotere, pagina-brede bewerkingen, zoals wanneer een gebruiker een taak initieert die de hele pagina beïnvloedt, zoals het uploaden van een bestand of het verwerken van gegevens over meerdere secties. Het kan gebruikers informeren dat de hele app aan het werk is, waardoor verdere interactie wordt voorkomen totdat het proces is voltooid.

- **Kritische Systeemoperaties**  
   Bij het uitvoeren van systeem-kritische taken zoals gegevens synchroniseren, systeem-wijde updates toepassen, of gevoelige informatie verwerken, biedt de `BusyIndicator` duidelijke visuele feedback dat een grote operatie aan de gang is, waardoor de gebruiker kan wachten tot deze is voltooid.

- **Asynchrone Gegevensladingen**  
   In scenario's waarin asynchrone gegevensverwerking aan de orde is, zoals bij het aanroepen van meerdere API's of wachten op complexe berekeningen, geeft de `BusyIndicator` component actief aan dat het systeem druk is, waardoor gebruikers worden aangespoord te wachten voordat ze aanvullende acties ondernemen.

## Styling {#styling}

<TableBuilder name="BusyIndicator" />
