---
title: BusyIndicator
sidebar_position: 10
description: >-
  Block the entire interface during long-running operations using the
  BusyIndicator overlay with a customizable spinner, message, and backdrop.
_i18n_hash: 663fb0d605695631bad3753aadf178e5
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-loading" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/BusyIndicator" top='true'/>

De `BusyIndicator` is een overlay voor het hele scherm die een lopend proces aangeeft en gebruikersinteractie blokkeert totdat het proces is voltooid. Het bedekt de hele interface tijdens bewerkingen zoals initialisatie of gegevenssynchronisatie. Terwijl de [`Loading`](../components/loading) component zich richt op specifieke gebieden binnen de interface, past de `BusyIndicator` zich globaal toe.

De `BusyIndicator` wordt weergegeven als een spiner zonder dat er configuratie nodig is. Voeg een bericht toe, verander het thema van de spiner of pas de zichtbaarheidinstellingen aan wanneer een proces meer context nodig heeft.

<!-- INTRO_END -->

<ComponentDemo
path='/webforj/busydemo'
files={['src/main/java/com/webforj/samples/views/busyindicator/BusyDemoView.java']}
height='300px'
/>

## Achtergronden {#backdrops}

De `BusyIndicator` component in webforJ maakt het mogelijk om een achtergrond weer te geven om gebruikersinteractie te blokkeren terwijl een proces gaande is. Standaard staat de component de achtergrond toe, maar je hebt de optie om deze uit te schakelen indien nodig.

De `BusyIndicator` toont standaard een achtergrond. Je kunt de zichtbaarheid van de achtergrond regelen met de `setBackdropVisible()` methode, zoals hieronder weergegeven:

```java
BusyIndicator busyIndicator = getBusyIndicator();
busyIndicator.setBackdropVisible(false);  // Deactiveert de achtergrond
busyIndicator.open();
```
:::info Achtergrond Uitschakelen
Zelfs als je de achtergrond uitschakelt, blijft de `BusyIndicator` component gebruikersinteractie blokkeren om ervoor te zorgen dat het onderliggende proces ononderbroken voltooid wordt. De achtergrond regelt simpelweg de visuele overlay, niet het blokkeren van interactie.
:::

## `Spinner` {#spinner}

De `BusyIndicator` component in webforJ bevat een `Spinner` die visueel aangeeft dat er een achtergrondoperatie aan de gang is. Je kunt deze spiner aanpassen met verschillende opties, waaronder grootte, snelheid, richting, thema en zichtbaarheid.

Hier is een voorbeeld van hoe je de spiner binnen een `BusyIndicator` component kunt aanpassen:

<ComponentDemo
path='/webforj/busyspinnerdemo'
files={['src/main/java/com/webforj/samples/views/busyindicator/BusySpinnerDemoView.java']}
height='200px'
/>

## Toepassingsgevallen {#use-cases}
- **Pagina-brede Verwerking**
   De `BusyIndicator` is bijzonder goed geschikt voor grotere, pagina-brede operaties, zoals wanneer een gebruiker een taak initieert die de hele pagina beïnvloedt, zoals het uploaden van een bestand of het verwerken van gegevens over meerdere secties. Het kan gebruikers informeren dat de hele applicatie aan het werk is, waardoor verdere interactie wordt voorkomen totdat het proces is voltooid.

- **Kritieke Systeemoperaties**
   Bij het uitvoeren van systeemkritieke taken zoals het synchroniseren van gegevens, het toepassen van systeembrede updates of het verwerken van gevoelige informatie, biedt de `BusyIndicator` duidelijke visuele feedback dat een grote operatie aan de gang is, zodat de gebruiker kan wachten totdat deze is voltooid.

- **Asynchrone Gegevensladingen**
   In scenario's waarbij asynchrone gegevensverwerking betrokken is, zoals wanneer meerdere API's worden aangeroepen of gewacht moet worden op complexe berekeningen, geeft de `BusyIndicator` component actief aan dat het systeem bezet is, wat gebruikers aanmoedigt te wachten voordat ze aanvullende acties ondernemen.

## Stijlen {#styling}

<TableBuilder name="BusyIndicator" />
