---
title: Loading
sidebar_position: 65
description: >-
  Overlay a parent container with the Loading component to block interaction
  during async tasks, with backdrop and spinner customization.
_i18n_hash: 6e4493f64eb29033ed8a5d594accdb33
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-loading" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="loading" location="com/webforj/component/loading/Loading" top='true'/>

De `Loading` component toont een overlay op een specifieke component of gebied, wat aangeeft dat een operatie bezig is en tijdelijk de interactie blokkeert. Het werkt goed voor taken zoals dataloading, berekeningen of achtergrondprocessen. Voor globale, app-brede processen dekt de [`BusyIndicator`](../components/busyindicator) component de gehele interface.

<!-- INTRO_END -->

Het initialiseren van een `Loading` component zonder extra instellingen toont een spinner over de ouderinhoud. Geef een boodschap door, zoals in het onderstaande voorbeeld, wanneer het proces meer context nodig heeft.

<ComponentDemo
path='/webforj/loadingdemo'
files={[
  'src/main/java/com/webforj/samples/views/loading/LoadingDemoView.java',
  'src/main/frontend/css/loadingstyles/loadingdemo.css',
]}
height='300px'
/>

## Scoping {#scoping}

De `Loading` component in webforJ kan zichzelf beperken tot een specifieke oudercontainer, zoals een `Div`, waardoor het alleen de gebruikersinteractie binnen dat element blokkeert. Standaard is de `Loading` component relatief aan zijn ouder, wat betekent dat het de oudercomponent overlaget in plaats van de gehele app.

Om de `Loading` component te beperken tot zijn ouder, voeg je simpelweg de `Loading` component toe aan de oudercontainer. Bijvoorbeeld, als je het toevoegt aan een `Div`, is de loadoverlay alleen van toepassing op die `Div`:

```java
Div parentDiv = new Div();
parentDiv.setStyle("position", "relative");
Loading loading = new Loading();
parentDiv.add(loading);
loading.open();  // Loading blokkeert alleen de interactie binnen de parentDiv
```

## Backdrop {#backdrop}

De `Loading` component in webforJ stelt je in staat om een backdrop weer te geven om de gebruikersinteractie te blokkeren terwijl een proces bezig is. Standaard schakelt de component de backdrop in, maar je hebt de optie om deze uit te schakelen indien nodig.

Voor de `Loading` component is de backdrop standaard zichtbaar. Je kunt deze expliciet in- of uitschakelen met de `setBackdropVisible()` methode:

```java
Loading loading = new Loading();
loading.setBackdropVisible(false);  // Schakelt de backdrop uit
loading.open();
```
:::info Backdrop Uit
Zelfs wanneer je de backdrop uitschakelt, blijft de `Loading` component de gebruikersinteractie blokkeren om te zorgen dat het onderliggende proces ononderbroken wordt voltooid. De backdrop controleert simpelweg de visuele overlay, niet het blokkerende gedrag van interactie.
:::

## `Spinner` {#spinner}

De `Loading` component in webforJ bevat een `Spinner` die visueel aangeeft dat er een achtergrondoperatie bezig is. Je kunt deze spinner aanpassen met verschillende opties, waaronder de grootte, snelheid, richting, thema en zichtbaarheid.

Hier is een voorbeeld van hoe je de spinner binnen een `Loading` component kunt aanpassen:

<ComponentDemo
path='/webforj/loadingspinnerdemo'
files={[
  'src/main/java/com/webforj/samples/views/loading/LoadingSpinnerDemoView.java',
]}
height='300px'
/>

## Gebruikscases {#use-cases}
- **Data Ophalen**
   Bij het ophalen van gegevens van een server of API overlayt de `Loading` component een specifiek gedeelte van de UI, zoals een kaart of formulier, om gebruikers te informeren dat het systeem op de achtergrond werkt. Dit is ideaal wanneer je voortgang op slechts één deel van het scherm wilt tonen zonder de hele interface te blokkeren.

- **Inhoud Laden in Kaarten/Segmenten**
   De `Loading` component kan worden beperkt tot specifieke gebieden van een pagina, zoals individuele kaarten of containers. Dit is nuttig wanneer je wilt aangeven dat een bepaald gedeelte van de UI nog steeds aan het laden is, terwijl gebruikers met andere delen van de pagina kunnen interageren.

- **Complexe Formulierindieningen**
   Voor langere formulierindieningen waarbij validatie of verwerking tijd kost, biedt de `Loading` component visuele feedback aan gebruikers, wat hen geruststelt dat hun invoer actief wordt verwerkt.

## Stijlen {#styling}

<TableBuilder name="Loading" />
