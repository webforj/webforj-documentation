---
title: Loading
sidebar_position: 65
description: >-
  Overlay a parent container with the Loading component to block interaction
  during async tasks, with backdrop and spinner customization.
_i18n_hash: 8106f15ba96904324822afd0169ec09b
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-loading" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="loading" location="com/webforj/component/loading/Loading" top='true'/>

De `Loading` component toont een overlay op een specifieke component of gebied, wat aangeeft dat een bewerking bezig is en tijdelijk interactie blokkeert. Het werkt goed voor taken zoals gegevens laden, berekeningen of achtergrondprocessen. Voor globale, app-brede processen dekt de [`BusyIndicator`](../components/busyindicator) component de gehele interface.

<!-- INTRO_END -->

Het initialiseren van een `Loading` component zonder aanvullende instellingen toont een spinner over de inhoud van de oudercomponent. Geef een bericht door, zoals in het onderstaande voorbeeld, wanneer het proces meer context nodig heeft.

<ComponentDemo
path='/webforj/loadingdemo'
files={[
  'src/main/java/com/webforj/samples/views/loading/LoadingDemoView.java',
  'src/main/frontend/css/loadingstyles/loadingdemo.css',
]}
height='300px'
/>

## Scoping {#scoping}

De `Loading` component in webforJ kan zich beperken tot een specifieke bovenliggende container, zoals een `Div`, zodat het alleen gebruikersinteractie binnen dat element blokkeert. Standaard is de `Loading` component relatief ten opzichte van zijn ouder, wat betekent dat het de oudercomponent overlayt in plaats van de hele app.

Om de `Loading` component te beperken tot zijn ouder, voeg je eenvoudig de `Loading` component toe aan de bovenliggende container. Als je het bijvoorbeeld toevoegt aan een `Div`, dan geldt de laadoverlay alleen voor die `Div`:

```java
Div parentDiv = new Div();
parentDiv.setStyle("position", "relative");
Loading loading = new Loading();
parentDiv.add(loading);
loading.open();  // Loading zal alleen interactie binnen de parentDiv blokkeren
```

## Achtergrond {#backdrop}

De `Loading` component in webforJ stelt je in staat om een achtergrond weer te geven die gebruikersinteractie blokkeert terwijl een proces bezig is. Standaard heeft de component de achtergrond ingeschakeld, maar je hebt de optie om deze uit te schakelen indien nodig.

Voor de `Loading` component is de achtergrond standaard zichtbaar. Je kunt deze expliciet inschakelen of uitschakelen met de `setBackdropVisible()` methode:

```java
Loading loading = new Loading();
loading.setBackdropVisible(false);  // Schakelt de achtergrond uit
loading.open();
```
:::info Achtergrond Uit
Zelfs wanneer je de achtergrond uitschakelt, blijft de `Loading` component gebruikersinteractie blokkeren om ervoor te zorgen dat het onderliggende proces onafgebroken wordt voltooid. De achtergrond regelt simpelweg de visuele overlay, niet het blokkeren van de interactie.
:::

## `Spinner` {#spinner}

De `Loading` component in webforJ bevat een `Spinner` die visueel aangeeft dat er een achtergrondbewerking bezig is. Je kunt deze spinner aanpassen met verschillende opties, waaronder de grootte, snelheid, richting, thema en zichtbaarheid.

Hier is een voorbeeld van hoe je de spinner binnen een `Loading` component kunt aanpassen:

<ComponentDemo
path='/webforj/loadingspinnerdemo'
files={[
  'src/main/java/com/webforj/samples/views/loading/LoadingSpinnerDemoView.java',
  'src/main/frontend/css/loadingstyles/loadingspinnerdemo.css',
]}
height='300px'
/>

## Gebruikscases {#use-cases}
- **Gegevens ophalen**
   Bij het ophalen van gegevens van een server of API, overlayt de `Loading` component een specifiek gedeelte van de UI, zoals een kaart of formulier, om gebruikers te informeren dat het systeem op de achtergrond werkt. Dit is ideaal wanneer je voortgang wilt tonen in slechts één deel van het scherm zonder de hele interface te blokkeren.

- **Inhoud laden in kaarten/secties**
   De `Loading` component kan beperkt worden tot specifieke gebieden van een pagina, zoals afzonderlijke kaarten of containers. Dit is handig wanneer je wilt aangeven dat een bepaald gedeelte van de UI nog laadt, terwijl gebruikers met andere delen van de pagina kunnen interageren.

- **Complexe formulierindieningen**
   Voor langere formulierindieningen waarbij validatie of verwerking tijd kost, biedt de `Loading` component visuele feedback aan gebruikers, waardoor ze gerustgesteld worden dat hun invoer actief wordt verwerkt.

## Styling {#styling}

<TableBuilder name="Loading" />
