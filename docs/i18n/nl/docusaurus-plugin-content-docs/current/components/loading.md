---
title: Loading
sidebar_position: 65
_i18n_hash: c81b8d0ced3e4097693a186a05f18dbf
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-loading" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="loading" location="com/webforj/component/loading/Loading" top='true'/>

De `Loading` component toont een overlay op een specifiek component of gebied, wat aangeeft dat er een operatie gaande is en tijdelijk de interactie blokkeert. Het is goed te gebruiken voor taken zoals gegevens laden, berekeningen of achtergrondprocessen. Voor globale processen in de app bedekt de [`BusyIndicator`](../components/busyindicator) component de hele interface.

<!-- INTRO_END -->

## Basis {#basics}

De eenvoudigste manier om een `Loading` component te maken is door het zonder extra instellingen te initialiseren. Standaard toont dit een basis spinner bovenop de inhoud van de ouder. Je kunt ook een bericht toevoegen voor meer context.

Hier is een voorbeeld van het maken van een `Loading` component met een bericht:

<ComponentDemo
path='/webforj/loadingdemo'
files={[
  'src/main/java/com/webforj/samples/views/loading/LoadingDemoView.java',
  'src/main/resources/static/css/loadingstyles/loadingdemo.css',
]}
height='300px'
/>

## Scoping {#scoping}

De `Loading` component in webforJ kan zich beperken tot een specifieke oudercontainer, zoals een `Div`, zodat het alleen de gebruikersinteractie binnen dat element blokkeert. Standaard is de `Loading` component relatief aan zijn ouder, wat betekent dat het de oudercomponent overlayt in plaats van de hele app.

Om de `Loading` component te beperken tot zijn ouder, voeg je eenvoudig de `Loading` component toe aan de oudercontainer. Als je deze bijvoorbeeld aan een `Div` toevoegt, geldt de loading overlay alleen voor die `Div`:

```java
Div parentDiv = new Div();  
parentDiv.setStyle("position", "relative");
Loading loading = new Loading();
parentDiv.add(loading);
loading.open();  // Loading blokkeert alleen de interactie binnen de parentDiv
```

## Achtergrond {#backdrop}

De `Loading` component in webforJ stelt je in staat om een achtergrond weer te geven om gebruikersinteractie te blokkeren terwijl een proces gaande is. Standaard staat de component de achtergrond toe, maar je hebt de optie om deze uit te schakelen indien nodig.

Voor de `Loading` component is de achtergrond standaard zichtbaar. Je kunt deze expliciet in- of uitschakelen met de `setBackdropVisible()` methode:

```java
Loading loading = new Loading();
loading.setBackdropVisible(false);  // Schakelt de achtergrond uit
loading.open();
```
:::info Achtergrond Uit
Zelfs wanneer je de achtergrond uitschakelt, blijft de `Loading` component de gebruikersinteractie blokkeren om ervoor te zorgen dat het onderliggende proces ononderbroken wordt voltooid. De achtergrond regelt eenvoudig de visuele overlay, niet het blokkeren van de interactie.
:::

## `Spinner` {#spinner}

De `Loading` component in webforJ bevat een `Spinner` die visueel aangeeft dat er een achtergrondoperatie gaande is. Je kunt deze spinner aanpassen met verschillende opties, waaronder de grootte, snelheid, richting, thema en zichtbaarheid.

Hier is een voorbeeld van hoe je de spinner binnen een `Loading` component kunt aanpassen:

<ComponentDemo
path='/webforj/loadingspinnerdemo'
files={[
  'src/main/java/com/webforj/samples/views/loading/LoadingSpinnerDemoView.java',
  'src/main/resources/static/css/loadingstyles/loadingspinnerdemo.css',
]}
height='300px'
/>

## Gebruikssituaties {#use-cases}
- **Gegevens Ophalen**  
   Bij het ophalen van gegevens van een server of API overlayt de `Loading` component een specifiek gedeelte van de UI, zoals een kaart of formulier, om gebruikers te informeren dat het systeem op de achtergrond werkt. Dit is ideaal wanneer je voortgang wilt tonen op slechts één deel van het scherm zonder de hele interface te blokkeren.

- **Inhoud Laden in Kaarten/Gedeelten**  
   De `Loading` component kan worden beperkt tot specifieke gebieden van een pagina, zoals individuele kaarten of containers. Dit is nuttig wanneer je wilt aangeven dat een bepaald gedeelte van de UI nog aan het laden is, terwijl gebruikers met andere delen van de pagina kunnen interageren.

- **Complexe Formulierindieningen**  
   Voor langere formulierindieningen waarbij validatie of verwerking tijd kost, biedt de `Loading` component visuele feedback aan gebruikers, waardoor ze gerustgesteld worden dat hun invoer actief wordt verwerkt.

## Styling {#styling}

<TableBuilder name="Loading" />
