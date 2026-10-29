---
title: BusyIndicator
sidebar_position: 10
description: >-
  Block the entire interface during long-running operations using the
  BusyIndicator overlay with a customizable spinner, message, and backdrop.
_i18n_hash: a6ab827c0422e8f10369500acc310007
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-loading" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/BusyIndicator" top='true'/>

De `BusyIndicator` is een overlay over het hele scherm die een lopend proces aangeeft en gebruikersinteractie blokkeert totdat het is voltooid. Het bedekt de gehele interface tijdens operaties zoals initialisatie of gegevenssynchronisaties. Terwijl de [`Loading`](../components/loading) component zich richt op specifieke gebieden binnen de interface, past de `BusyIndicator` zich globaal toe.

De `BusyIndicator` wordt weergegeven als een spinner zonder dat er configuratie vereist is. Voeg een bericht toe, verander het thema van de spinner of pas de zichtbaarheid aan wanneer een proces meer context nodig heeft.

<!-- INTRO_END -->

<ComponentDemo
path='/webforj/busydemo'
files={['src/main/java/com/webforj/samples/views/busyindicator/BusyDemoView.java']}
height='300px'
/>

## Achtergronden {#backdrops}

De `BusyIndicator` component in webforJ stelt je in staat om een achtergrond weer te geven om gebruikersinteractie te blokkeren terwijl een proces aan de gang is. Standaard staat de component de achtergrond toe, maar je hebt de optie om deze uit te schakelen indien nodig.

De `BusyIndicator` toont standaard een achtergrond. Je kunt de zichtbaarheid van de achtergrond controleren met de `setBackdropVisible()` methode, zoals hieronder weergegeven:

```java
BusyIndicator busyIndicator = App.getBusyIndicator();
busyIndicator.setBackdropVisible(false);  // Schakelt de achtergrond uit
busyIndicator.open();
```
:::info Achtergrond uitschakelen
Zelfs wanneer je de achtergrond uitschakelt, blokkeert de `BusyIndicator` component nog steeds gebruikersinteractie om ervoor te zorgen dat het onderliggende proces ononderbroken wordt voltooid. De achtergrond beheert eenvoudigweg de visuele overlay, niet het gedrag van blokkering van interactie.
:::

## `Spinner` {#spinner}

De `BusyIndicator` component in webforJ omvat een `Spinner` die visueel aangeeft dat een achtergrondoperatie aan de gang is. Je kunt deze spinner aanpassen met verschillende opties, waaronder grootte, snelheid, richting, thema en zichtbaarheid.

Hier is een voorbeeld van hoe je de spinner binnen een `BusyIndicator` component kunt aanpassen:

<ComponentDemo
path='/webforj/busyspinnerdemo'
files={['src/main/java/com/webforj/samples/views/busyindicator/BusySpinnerDemoView.java']}
height='200px'
/>

## Toepassingsgevallen {#use-cases}
- **Verwerking over de hele pagina**
   De `BusyIndicator` is bijzonder geschikt voor grotere, paginabrede operaties, zoals wanneer een gebruiker een taak initieert die de hele pagina beïnvloedt, zoals het uploaden van een bestand of het verwerken van gegevens over meerdere secties. Het kan gebruikers informeren dat de hele applicatie aan het werken is, waardoor verdere interactie wordt voorkomen totdat het proces is voltooid.

- **Kritieke systeemoperaties**
   Bij het uitvoeren van systeenkritische taken zoals het synchroniseren van gegevens, het toepassen van systeembrede updates of het verwerken van gevoelige informatie, biedt de `BusyIndicator` duidelijke visuele feedback dat een belangrijke operatie aan de gang is, zodat de gebruiker kan wachten tot deze is afgerond.

- **Asynchrone gegevensladingen**
   In scenario's waar asynchrone gegevensverwerking aan de orde is, zoals bij het aanroepen van meerdere API's of het wachten op complexe berekeningen, geeft de `BusyIndicator` component actief aan dat het systeem bezig is, wat gebruikers aanmoedigt om te wachten voordat ze aanvullende acties ondernemen.

## Stylen {#styling}

<TableBuilder name="BusyIndicator" />
