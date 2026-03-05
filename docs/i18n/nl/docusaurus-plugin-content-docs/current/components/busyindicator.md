---
title: BusyIndicator
sidebar_position: 10
_i18n_hash: e8d5c8ba0e26f0cc8fb98a640069347f
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-loading" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/BusyIndicator" top='true'/>

De `BusyIndicator` is een overlay over het volledige scherm die een lopend proces aangeeft en gebruikersinteractie blokkeert totdat het is voltooid. Het bedekt de hele interface tijdens bewerkingen zoals initialisatie of datasynchronisatie. Terwijl de [`Loading`](../components/loading) component zich richt op specifieke gebieden binnen de interface, wordt de `BusyIndicator` globaal toegepast.

<!-- INTRO_END -->

## Basis {#basics}

De `BusyIndicator` in webforJ wordt weergegeven als een eenvoudige spinner, waardoor het gemakkelijk te gebruiken is zonder configuratie. U kunt het echter aanpassen door een bericht toe te voegen, het thema van de spinner aan te passen of de zichtbaarheid instellingen te wijzigen. Dit stelt u in staat om meer context of stijl te bieden, terwijl u een functionele, kant-en-klare oplossing behoudt.

In dit voorbeeld voorkomt de `BusyIndicator` dat gebruikersacties op de gehele interface worden uitgevoerd totdat de bewerking is voltooid.

<ComponentDemo 
path='/webforj/busydemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/busyindicator/BusyDemoView.java'
height = '300px'
/>

## Achtergrond {#backdrops}

De `BusyIndicator` component in webforJ stelt u in staat om een achtergrond weer te geven om gebruikersinteractie te blokkeren terwijl een proces aan de gang is. Standaard is de achtergrond ingeschakeld, maar u heeft de optie om deze uit te schakelen indien nodig.

De `BusyIndicator` toont standaard een achtergrond. U kunt de zichtbaarheid van de achtergrond regelen met de `setBackdropVisible()` methode, zoals hieronder weergegeven:

```java
BusyIndicator busyIndicator = getBusyIndicator();
busyIndicator.setBackdropVisible(false);  // Schakelt de achtergrond uit
busyIndicator.open();
```
:::info Achtergrond Uitschakelen
Zelfs wanneer u de achtergrond uitschakelt, blijft de `BusyIndicator` component gebruikersinteractie blokkeren om te zorgen dat het onderliggende proces ononderbroken kan worden voltooid. De achtergrond regelt simpelweg de visuele overlay, niet het blokkeren van interactie.
:::

## `Spinner` {#spinner}

De `BusyIndicator` component in webforJ bevat een `Spinner` die visueel aangeeft dat een achtergrondbewerking aan de gang is. U kunt deze spinner aanpassen met verschillende opties, waaronder de grootte, snelheid, richting, thema en zichtbaarheid.

Hier is een voorbeeld van hoe u de spinner binnen een `BusyIndicator` component kunt aanpassen:

<ComponentDemo 
path='/webforj/busyspinnerdemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/busyindicator/BusySpinnerDemoView.java'
height = '200px'
/>

## Toepassingen {#use-cases}
- **Pagina-brede Verwerking**  
   De `BusyIndicator` is zeer geschikt voor grotere, pagina-brede bewerkingen, zoals wanneer een gebruiker een taak initieert die de hele pagina beïnvloedt, zoals het uploaden van een bestand of het verwerken van gegevens in meerdere secties. Het kan gebruikers informeren dat de hele app bezig is, waardoor verdere interactie wordt voorkomen totdat het proces is voltooid.

- **Kritieke Systeemoperaties**  
   Bij het uitvoeren van systeemkritische taken, zoals het synchroniseren van gegevens, het toepassen van systeemwijde updates of het verwerken van gevoelige informatie, geeft de `BusyIndicator` duidelijke visuele feedback dat er een belangrijke bewerking aan de gang is, zodat de gebruiker kan wachten totdat deze is voltooid.

- **Asynchrone Gegevensladingen**  
   In scenario's waarin asynchrone gegevensverwerking aan de orde is, zoals bij het aanroepen van meerdere API's of het wachten op complexe berekeningen, geeft de `BusyIndicator` component actief aan dat het systeem bezig is, wat gebruikers aanmoedigt om te wachten voordat ze aanvullende acties ondernemen.

## Stijlen {#styling}

<TableBuilder name="BusyIndicator" />
