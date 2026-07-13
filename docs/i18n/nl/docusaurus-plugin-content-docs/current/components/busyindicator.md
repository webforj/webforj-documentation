---
title: BusyIndicator
sidebar_position: 10
description: >-
  Block the entire interface during long-running operations using the
  BusyIndicator overlay with a customizable spinner, message, and backdrop.
_i18n_hash: 30ca15f8b8170f6d7da6a786ddafea7f
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-loading" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/BusyIndicator" top='true'/>

De `BusyIndicator` is een overlay over het volledige scherm die aangeeft dat een proces gaande is en blokkeert de gebruikersinteractie totdat dit is voltooid. Het bedekt de gehele interface tijdens handelingen zoals initialisatie of gegevenssynchronisatie. Terwijl de [`Loading`](../components/loading) component zich richt op specifieke gebieden binnen de interface, wordt de `BusyIndicator` globaal toegepast.

<!-- INTRO_END -->

## Basisprincipes {#basics}

De `BusyIndicator` in webforJ wordt weergegeven als een eenvoudige spinner, waardoor het gemakkelijk te gebruiken is zonder configuratie. Je kunt het echter aanpassen door een bericht toe te voegen, het thema van de spinner aan te passen of de zichtbaarheid instellingen te wijzigen. Dit stelt je in staat om meer context of stijl te bieden terwijl je een functionele, kant-en-klare oplossing behoudt.

In dit voorbeeld voorkomt de `BusyIndicator` dat gebruikersacties plaatsvinden over de gehele interface totdat de operatie is voltooid.

<ComponentDemo
path='/webforj/busydemo'
files={['src/main/java/com/webforj/samples/views/busyindicator/BusyDemoView.java']}
height='300px'
/>

## Achtergronden {#backdrops}

De `BusyIndicator` component in webforJ stelt je in staat een achtergrond weer te geven om gebruikersinteractie te blokkeren terwijl een proces gaande is. Standaard is de achtergrond ingeschakeld, maar je hebt de optie om deze uit te schakelen indien nodig.

De `BusyIndicator` toont standaard een achtergrond. Je kunt de zichtbaarheid van de achtergrond regelen met de `setBackdropVisible()` methode, zoals hieronder weergegeven:

```java
BusyIndicator busyIndicator = getBusyIndicator();
busyIndicator.setBackdropVisible(false);  // Schakelt de achtergrond uit
busyIndicator.open();
```
:::info Achtergrond Uitschakelen
Zelfs wanneer je de achtergrond uitschakelt, blijft de `BusyIndicator` component de gebruikersinteractie blokkeren om ervoor te zorgen dat het onderliggende proces ononderbroken wordt voltooid. De achtergrond beheert eenvoudig de visuele overlay, niet het gedrag van het blokkeren van interactie.
:::

## `Spinner` {#spinner}

De `BusyIndicator` component in webforJ omvat een `Spinner` die visueel aangeeft dat een achtergrondoperatie gaande is. Je kunt deze spinner aanpassen met verschillende opties, waaronder de grootte, snelheid, richting, thema en zichtbaarheid.

Hier is een voorbeeld van hoe je de spinner binnen een `BusyIndicator` component kunt aanpassen:

<ComponentDemo
path='/webforj/busyspinnerdemo'
files={['src/main/java/com/webforj/samples/views/busyindicator/BusySpinnerDemoView.java']}
height='200px'
/>

## Toepassingsgevallen {#use-cases}
- **Pagina-brede Verwerking**
   De `BusyIndicator` is goed geschikt voor grotere, pagina-brede operaties, zoals wanneer een gebruiker een taak initieert die de gehele pagina beïnvloedt, zoals het uploaden van een bestand of het verwerken van gegevens over meerdere secties. Het kan gebruikers informeren dat de gehele applicatie aan het werk is, zodat verdere interactie wordt voorkomen totdat het proces is voltooid.

- **Kritieke Systeemoperaties**
   Bij het uitvoeren van systeemkritische taken, zoals het synchroniseren van gegevens, het toepassen van systeem-brede updates, of het verwerken van gevoelige informatie, geeft de `BusyIndicator` duidelijke visuele feedback dat een belangrijke operatie aan de gang is, waardoor de gebruiker kan wachten tot het is voltooid.

- **Asynchrone Gegevensladen**
   In scenario's waarin asynchrone gegevensverwerking betrokken is, zoals wanneer meerdere API's worden aangeroepen of gewacht wordt op complexe berekeningen, geeft de `BusyIndicator` component actief aan dat het systeem bezig is, wat gebruikers aanmoedigt om te wachten voordat ze aanvullende acties ondernemen.

## Stijling {#styling}

<TableBuilder name="BusyIndicator" />
