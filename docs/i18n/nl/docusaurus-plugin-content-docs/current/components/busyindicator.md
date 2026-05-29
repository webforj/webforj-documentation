---
title: BusyIndicator
sidebar_position: 10
_i18n_hash: 456b6118cd6219f530c5292611ba46e0
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-loading" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/BusyIndicator" top='true'/>

De `BusyIndicator` is een overlay voor het volledige scherm die een lopend proces aangeeft en gebruikersinteractie blokkeert totdat het is voltooid. Het bedekt de gehele interface tijdens bewerkingen zoals initialisatie of datasynchronisatie. Terwijl de [`Loading`](../components/loading) component zich richt op specifieke gebieden binnen de interface, heeft de `BusyIndicator` een globale toepassing.

<!-- INTRO_END -->

## Basics {#basics}

De `BusyIndicator` in webforJ verschijnt als een eenvoudige spinner, waardoor het gemakkelijk te gebruiken is zonder configuratie. U kunt het echter aanpassen door een bericht toe te voegen, het thema van de spinner aan te passen of de zichtbaarheid instellingen te wijzigen. Dit stelt u in staat om meer context of stijl te bieden terwijl u een functionele, kant-en-klare oplossing behoudt.

In dit voorbeeld voorkomt de `BusyIndicator` dat gebruikersacties op de gehele interface plaatsvinden totdat de bewerking is voltooid.

<ComponentDemo
path='/webforj/busydemo'
files={['src/main/java/com/webforj/samples/views/busyindicator/BusyDemoView.java']}
height='300px'
/>

## Backdrops {#backdrops}

De `BusyIndicator` component in webforJ stelt u in staat om een backdrop weer te geven om gebruikersinteractie te blokkeren terwijl een proces bezig is. Standaard staat de component de backdrop toe, maar u heeft de optie om deze uit te schakelen indien nodig.

De `BusyIndicator` toont standaard een backdrop. U kunt de zichtbaarheid van de backdrop regelen met de methode `setBackdropVisible()`, zoals hieronder weergegeven:

```java
BusyIndicator busyIndicator = getBusyIndicator();
busyIndicator.setBackdropVisible(false);  // Schakelt de backdrop uit
busyIndicator.open();
```
:::info Uitschakelen van de Backdrop
Zelfs wanneer u de backdrop uitschakelt, blijft de `BusyIndicator` component gebruikersinteractie blokkeren om ervoor te zorgen dat het onderliggende proces ononderbroken wordt voltooid. De backdrop regelt eenvoudig de visuele overlay, niet het gedrag van het blokkeren van interacties.
:::

## `Spinner` {#spinner}

De `BusyIndicator` component in webforJ bevat een `Spinner` die visueel aangeeft dat er een achtergrondbewerking gaande is. U kunt deze spinner aanpassen met verschillende opties, waaronder de grootte, snelheid, richting, thema en zichtbaarheid.

Hier is een voorbeeld van hoe u de spinner binnen een `BusyIndicator` component kunt aanpassen:

<ComponentDemo
path='/webforj/busyspinnerdemo'
files={['src/main/java/com/webforj/samples/views/busyindicator/BusySpinnerDemoView.java']}
height='200px'
/>

## Use cases {#use-cases}
- **Pagina-brede Verwerking**  
   De `BusyIndicator` is zeer geschikt voor grotere, pagina-brede bewerkingen, zoals wanneer een gebruiker een taak initieert die de hele pagina beïnvloedt, zoals het uploaden van een bestand of het verwerken van gegevens over meerdere secties. Het kan gebruikers informeren dat de gehele app aan het werken is, waardoor verdere interactie wordt voorkomen totdat het proces is voltooid.

- **Kritieke Systeembewerkingen**  
   Bij het uitvoeren van systeemkritieke taken, zoals het synchroniseren van gegevens, het toepassen van systeemwijde updates of het verwerken van gevoelige informatie, biedt de `BusyIndicator` duidelijke visuele feedback dat er een belangrijke bewerking gaande is, waardoor de gebruiker kan wachten totdat deze is voltooid.

- **Asynchrone Gegevensladingen**  
   In scenario's waarin asynchrone gegevensverwerking betrokken is, zoals bij het aanroepen van meerdere API's of het wachten op complexe berekeningen, geeft de `BusyIndicator` component actief aan dat het systeem bezig is, waardoor gebruikers worden aangespoord om te wachten voordat ze extra acties ondernemen.

## Styling {#styling}

<TableBuilder name="BusyIndicator" />
