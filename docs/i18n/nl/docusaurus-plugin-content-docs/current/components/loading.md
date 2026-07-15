---
title: Loading
sidebar_position: 65
description: >-
  Overlay a parent container with the Loading component to block interaction
  during async tasks, with backdrop and spinner customization.
_i18n_hash: e17c9249d41752ed1f4b98d18028371a
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-loading" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="loading" location="com/webforj/component/loading/Loading" top='true'/>

De `Loading` component toont een overlay op een specifiek component of gebied, waarmee wordt aangegeven dat een bewerking gaande is en tijdelijk interactie wordt geblokkeerd. Het werkt goed voor taken zoals het laden van gegevens, berekeningen of achtergrondprocessen. Voor globale, app-brede processen dekt de [`BusyIndicator`](../components/busyindicator) component de hele interface.

<!-- INTRO_END -->

## Basisprincipes {#basics}

De eenvoudigste manier om een `Loading` component te maken, is door het te initialiseren zonder aanvullende instellingen. Standaard toont dit een basis spinner over zijn bovenliggende inhoud. U kunt echter ook een bericht voorzien voor meer context.

Hier is een voorbeeld van het maken van een `Loading` component met een bericht:

<ComponentDemo
path='/webforj/loadingdemo'
files={[
  'src/main/java/com/webforj/samples/views/loading/LoadingDemoView.java',
  'src/main/frontend/css/loadingstyles/loadingdemo.css',
]}
height='300px'
/>

## Scoping {#scoping}

De `Loading` component in webforJ kan zichzelf afschermen voor een specifieke bovenliggende container, zoals een `Div`, waarmee wordt gegarandeerd dat het alleen gebruikersinteractie binnen dat element blokkeert. Standaard is de `Loading` component relatief aan zijn bovenliggende, wat betekent dat het de bovenliggende component overlayt in plaats van de hele app.

Om de `Loading` component te beperken tot zijn bovenliggende, voegt u eenvoudig de `Loading` component toe aan de bovenliggende container. Bijvoorbeeld, als u het aan een `Div` toevoegt, past de laadoverlay alleen op die `Div`:

```java
Div parentDiv = new Div();
parentDiv.setStyle("position", "relative");
Loading loading = new Loading();
parentDiv.add(loading);
loading.open();  // Loading blokkeert alleen de interactie binnen de parentDiv
```

## Achtergrond {#backdrop}

De `Loading` component in webforJ stelt u in staat om een achtergrond te tonen om gebruikersinteractie te blokkeren terwijl een proces gaande is. Standaard staat de component de achtergrond toe, maar u heeft de optie om deze uit te schakelen indien nodig.

Voor de `Loading` component is de achtergrond standaard zichtbaar. U kunt expliciet in- of uitschakelen met de `setBackdropVisible()` methode:

```java
Loading loading = new Loading();
loading.setBackdropVisible(false);  // Schakelt de achtergrond uit
loading.open();
```
:::info Achtergrond Uit
Zelfs als u de achtergrond uitschakelt, blijft de `Loading` component gebruikersinteractie blokkeren om te waarborgen dat het onderliggende proces ongestoord wordt voltooid. De achtergrond regelt simpelweg de visuele overlay, niet de interactieblokkerende werking.
:::

## `Spinner` {#spinner}

De `Loading` component in webforJ omvat een `Spinner` die visueel aangeeft dat er een achtergrondbewerking gaande is. U kunt deze spinner aanpassen met verschillende opties, waaronder grootte, snelheid, richting, thema en zichtbaarheid.

Hier is een voorbeeld van hoe u de spinner binnen een `Loading` component kunt aanpassen:

<ComponentDemo
path='/webforj/loadingspinnerdemo'
files={[
  'src/main/java/com/webforj/samples/views/loading/LoadingSpinnerDemoView.java',
  'src/main/frontend/css/loadingstyles/loadingspinnerdemo.css',
]}
height='300px'
/>

## Toepassingsgevallen {#use-cases}
- **Gegevens Ophalen**
   Bij het ophalen van gegevens van een server of API overlayt de `Loading` component een specifiek gedeelte van de UI, zoals een kaart of formulier, om gebruikers te informeren dat het systeem op de achtergrond werkt. Dit is ideaal wanneer u voortgang wilt tonen op slechts één deel van het scherm zonder de hele interface te blokkeren.

- **Inhoud Laden in Kaarten/Gedeelten**
   De `Loading` component kan worden beperkt tot specifieke gebieden van een pagina, zoals individuele kaarten of containers. Dit is nuttig wanneer u wilt aangeven dat een bepaald gedeelte van de UI nog aan het laden is terwijl gebruikers interactie kunnen hebben met andere delen van de pagina.

- **Complexe Formulierinzendingen**
   Voor langere formulierinzendingen waar validatie of verwerking tijd kost, biedt de `Loading` component visuele feedback aan gebruikers, waarmee wordt gerustgesteld dat hun invoer actief wordt verwerkt.

## Styling {#styling}

<TableBuilder name="Loading" />
