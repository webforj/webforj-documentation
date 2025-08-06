---
title: Loading
sidebar_position: 65
_i18n_hash: fd3e1e31d1a614494358f9d67a9d3cd8
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-loading" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="loading" location="com/webforj/component/loading/Loading" top='true'/>

De `Loading` component in webforJ toont een overlay die aangeeft dat een bewerking wordt verwerkt, waardoor tijdelijke gebruikersinteractie wordt verhinderd totdat de taak is voltooid. Deze functie verbetert de gebruikerservaring, vooral in situaties waarin taken zoals gegevens laden, berekeningen of achtergrondprocessen enige tijd kunnen duren. Voor globale, app-omvattende processen, overweeg het gebruik van de [`BusyIndicator`](../components/busyindicator) component, die interactie over de hele interface blokkeert.

## Basisprincipes {#basics}

De eenvoudigste manier om een `Loading` component te creëren, is door het zonder aanvullende instellingen te initialiseren. Standaard toont dit een basisspinner boven de inhoud van de bovenliggende component. Je kunt echter ook een bericht toevoegen voor meer context.

Hier is een voorbeeld van het creëren van een `Loading` component met een bericht:

<ComponentDemo 
path='/webforj/loadingdemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/loading/LoadingDemoView.java'
cssURL='/css/loadingstyles/loadingdemo.css'
height = '300px'
/>

## Scoping {#scoping}

De `Loading` component in webforJ kan zichzelf beperken tot een specifieke bovenliggende container, zoals een `Div`, waardoor het alleen gebruikersinteractie binnen dat element blokkeert. Standaard is de `Loading` component relatief aan zijn bovenliggende component, wat betekent dat het de bovenliggende component overlayt in plaats van de hele app.

Om de `Loading` component tot zijn bovenliggende component te beperken, voeg je eenvoudig de `Loading` component toe aan de bovenliggende container. Als je deze bijvoorbeeld toevoegt aan een `Div`, wordt de laad-overlay alleen op die `Div` toegepast:

```java
Div parentDiv = new Div();  
parentDiv.setStyle("position", "relative");
Loading loading = new Loading();
parentDiv.add(loading);
loading.open();  // Loading zal alleen interactie binnen de parentDiv blokkeren
```

## Achtergrond {#backdrop}

De `Loading` component in webforJ stelt je in staat om een achtergrond weer te geven om gebruikersinteractie te blokkeren terwijl een proces aan de gang is. Standaard is de achtergrond ingeschakeld, maar je hebt de optie om deze uit te schakelen indien nodig.

Voor de `Loading` component is de achtergrond standaard zichtbaar. Je kunt deze expliciet inschakelen of uitschakelen met de methode `setBackdropVisible()`:

```java
Loading loading = new Loading();
loading.setBackdropVisible(false);  // Schakelt de achtergrond uit
loading.open();
```
:::info Achtergrond Uit
Zelfs wanneer je de achtergrond uitschakelt, blijft de `Loading` component gebruikersinteractie blokkeren om ervoor te zorgen dat het onderliggende proces ononderbroken kan worden voltooid. De achtergrond bestuurt eenvoudig de visuele overlay, niet het gedrag van het blokkeren van interactie.
:::

## `Spinner` {#spinner}

De `Loading` component in webforJ bevat een `Spinner` die visueel aangeeft dat er een achtergrondbewerking aan de gang is. Je kunt deze spinner aanpassen met verschillende opties, waaronder grootte, snelheid, richting, thema en zichtbaarheid.

Hier is een voorbeeld van hoe je de spinner binnen een `Loading` component kunt aanpassen:

<ComponentDemo 
path='/webforj/loadingspinnerdemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/loading/LoadingSpinnerDemoView.java'
cssURL='/css/loadingstyles/loadingspinnerdemo.css'
height = '300px'
/>

## Gebruikscases {#use-cases}
- **Gegevens ophalen**  
   Bij het ophalen van gegevens van een server of API, overlayt de `Loading` component een specifiek gedeelte van de UI, zoals een kaart of formulier, om gebruikers te informeren dat het systeem op de achtergrond werkt. Dit is ideaal wanneer je voortgang over slechts één deel van het scherm wilt tonen zonder de hele interface te blokkeren.

- **Inhoud laden in kaarten/gedeelten**  
   De `Loading` component kan worden beperkt tot specifieke gebieden van een pagina, zoals individuele kaarten of containers. Dit is nuttig wanneer je wilt aangeven dat een bepaald gedeelte van de UI nog aan het laden is, terwijl gebruikers met andere delen van de pagina kunnen interageren.

- **Complexe formulierindieningen**  
   Voor langere formulierindieningen waarbij validatie of verwerking tijd kost, biedt de `Loading` component visuele feedback aan gebruikers, waarmee wordt bevestigd dat hun invoer actief wordt verwerkt.

## Stylen {#styling}

<TableBuilder name="Loading" />
