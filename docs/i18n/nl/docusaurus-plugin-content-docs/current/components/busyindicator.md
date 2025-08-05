---
title: BusyIndicator
sidebar_position: 10
_i18n_hash: 0ecb07a1364b90d27e17484ade2199ae
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-loading" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/BusyIndicator" top='true'/>

De `BusyIndicator` biedt visuele aanwijzingen om ervoor te zorgen dat gebruikers zich bewust zijn van lopende processen, waardoor ze worden verhinderd om te vroeg met het systeem te interageren. Het bedekt doorgaans de gehele app-interface voor mondiale operaties.

Terwijl de [`Loading`](../components/loading) component zich concentreert op specifieke gebieden of componenten binnen de app, behandelt de `BusyIndicator` globale, app-brede processen en blokkeert interactie over de gehele interface. Dit verschil in reikwijdte maakt de [`Loading`](../components/loading) component ideaal voor meer gelokaliseerde, component-specifieke scenario's, zoals het laden van gegevens in een bepaald gedeelte van een pagina. In tegenstelling daarmee is de `BusyIndicator` geschikt voor systeem-brede operaties die de gehele app beïnvloeden, zoals het initialiseren van de app of het uitvoeren van een grote gegevenssynchronisatie.

## Basisprincipes {#basics}

De `BusyIndicator` in webforJ wordt weergegeven als een eenvoudige draaiende indicator, waardoor deze gemakkelijk te gebruiken is zonder configuratie. U kunt deze echter aanpassen door een bericht toe te voegen, het thema van de draaiende indicator aan te passen of zichtbaarheidinstellingen te wijzigen. Dit stelt u in staat om meer context of stijl te bieden terwijl u een functionele, out-of-the-box oplossing behoudt.

In dit voorbeeld verhindert de `BusyIndicator` alle gebruikersacties over de gehele interface totdat de bewerking is voltooid.

<ComponentDemo 
path='/webforj/busydemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/busyindicator/BusyDemoView.java'
height = '300px'
/>

## Achtergronden {#backdrops}

De `BusyIndicator` component in webforJ stelt u in staat om een achtergrond weer te geven om gebruikerse interactie te blokkeren terwijl een proces aan de gang is. Standaard is de achtergrond ingeschakeld, maar u hebt de optie om deze uit te schakelen indien nodig. 

De `BusyIndicator` toont standaard een achtergrond. U kunt de zichtbaarheid van de achtergrond regelen met de methode `setBackdropVisible()`, zoals hieronder weergegeven:

```java
BusyIndicator busyIndicator = getBusyIndicator();
busyIndicator.setBackdropVisible(false);  // Deactiveert de achtergrond
busyIndicator.open();
```
:::info Achtergrond Uitschakelen
Zelfs wanneer u de achtergrond uitschakelt, blijft de `BusyIndicator` component gebruikersinteractie blokkeren om ervoor te zorgen dat het onderliggende proces ononderbroken wordt voltooid. De achtergrond controleert eenvoudigweg de visuele overlay, niet de interactie-blokkerende functie.
:::

## `Spinner` {#spinner}

De `BusyIndicator` component in webforJ bevat een `Spinner` die visueel aangeeft dat er een achtergrondoperatie aan de gang is. U kunt deze spinner aanpassen met verschillende opties, waaronder de grootte, snelheid, richting, thema en zichtbaarheid.

Hier is een voorbeeld van hoe u de spinner kunt aanpassen binnen een `BusyIndicator` component:

<ComponentDemo 
path='/webforj/busyspinnerdemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/busyindicator/BusySpinnerDemoView.java'
height = '200px'
/>

## Toepassingen {#use-cases}
- **Pagina-brede Verwerking**  
   De `BusyIndicator` is goed geschikt voor grotere, pagina-brede operaties, zoals wanneer een gebruiker een taak start die de hele pagina beïnvloedt, zoals het uploaden van een bestand of het verwerken van gegevens over meerdere secties. Het kan gebruikers informeren dat de hele app bezig is, waardoor verdere interactie wordt voorkomen totdat het proces is voltooid.

- **Kritieke Systeemoperaties**  
   Bij het uitvoeren van systeem-kritieke taken zoals het synchroniseren van gegevens, het aanbrengen van systeem-brede updates of het verwerken van gevoelige informatie geeft de `BusyIndicator` duidelijke visuele feedback dat een grote operatie gaande is, waardoor de gebruiker kan wachten tot deze is voltooid.

- **Asynchrone Gegevensladingen**  
   In scenario's waarin asynchrone gegevensverwerking aan de gang is, zoals bij het aanroepen van meerdere API's of het wachten op complexe berekeningen, geeft de `BusyIndicator` component actief aan dat het systeem druk bezig is, waardoor gebruikers worden aangespoord om te wachten voordat ze aanvullende acties ondernemen.

## Styling {#styling}

<TableBuilder name="BusyIndicator" />
