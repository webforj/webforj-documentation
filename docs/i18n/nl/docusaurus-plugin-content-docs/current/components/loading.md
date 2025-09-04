---
title: Loading
sidebar_position: 65
_i18n_hash: 9bdb4d5c978b4070d3628566e5105088
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-loading" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="loading" location="com/webforj/component/loading/Loading" top='true'/>

De `Loading` component in webforJ toont een overlay die aangeeft dat een bewerking wordt verwerkt, en voorkomt tijdelijk gebruikersinteractie totdat de taak is voltooid. Deze functie verbetert de gebruikerservaring, vooral in situaties waar taken zoals het laden van gegevens, berekeningen of achtergrondprocessen enige tijd kunnen duren. Voor wereldwijde, applicatiebrede processen kunt u overwegen om de [`BusyIndicator`](../components/busyindicator) component te gebruiken, die interactie blokkeert over de gehele interface.

## Basisprincipes {#basics}

De eenvoudigste manier om een `Loading` component te creëren, is door het zonder extra instellingen te initialiseren. Standaard toont dit een basis spinner over de inhoud van de bovenliggende laag. U kunt echter ook een bericht geven voor meer context.

Hier is een voorbeeld van het creëren van een `Loading` component met een bericht:

<ComponentDemo 
path='/webforj/loadingdemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/loading/LoadingDemoView.java'
cssURL='/css/loadingstyles/loadingdemo.css'
height = '300px'
/>

## Afbakening {#scoping}

De `Loading` component in webforJ kan zich afbakenen tot een specifieke bovenliggende container, zoals een `Div`, waardoor het alleen gebruikersinteractie blokkeert binnen dat element. Standaard is de `Loading` component relatief aan zijn ouder, wat betekent dat het de bovenliggende component overlaget in plaats van de hele app.

Om de `Loading` component te beperken tot zijn ouder, hoeft u alleen maar de `Loading` component aan de bovenliggende container toe te voegen. Als u het bijvoorbeeld aan een `Div` toevoegt, geldt de laadoverlay alleen voor die `Div`:

```java
Div parentDiv = new Div();  
parentDiv.setStyle("position", "relative");
Loading loading = new Loading();
parentDiv.add(loading);
loading.open();  // Loading blokkeert alleen interactie binnen de parentDiv
```

## Achtergrond {#backdrop}

De `Loading` component in webforJ laat u een achtergrond weergeven om gebruikersinteractie te blokkeren terwijl een proces aan de gang is. Standaard is de achtergrond ingeschakeld, maar u heeft de optie om deze uit te schakelen indien nodig.

Voor de `Loading` component is de achtergrond standaard zichtbaar. U kunt deze expliciet in- of uitschakelen met de `setBackdropVisible()` methode:

```java
Loading loading = new Loading();
loading.setBackdropVisible(false);  // Schakelt de achtergrond uit
loading.open();
```
:::info Achtergrond Uit
Zelfs als u de achtergrond uitschakelt, blijft de `Loading` component gebruikersinteractie blokkeren om te zorgen dat het onderliggende proces ononderbroken kan worden voltooid. De achtergrond regelt simpelweg de visuele overlay, niet het blokkeren van interactie.
:::

## `Spinner` {#spinner}

De `Loading` component in webforJ bevat een `Spinner` die visueel aangeeft dat er een achtergrondbewerking bezig is. U kunt deze spinner aanpassen met verschillende opties, waaronder de grootte, snelheid, richting, thema en zichtbaarheid.

Hier is een voorbeeld van hoe u de spinner binnen een `Loading` component kunt aanpassen:

<ComponentDemo 
path='/webforj/loadingspinnerdemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/loading/LoadingSpinnerDemoView.java'
cssURL='/css/loadingstyles/loadingspinnerdemo.css'
height = '300px'
/>

## Gebruikscenarios {#use-cases}
- **Gegevens Ophalen**  
   Bij het ophalen van gegevens van een server of API, overlayt de `Loading` component een specifiek gedeelte van de gebruikersinterface, zoals een kaart of formulier, om gebruikers te informeren dat het systeem op de achtergrond bezig is. Dit is ideaal wanneer u voortgang wilt tonen op slechts één deel van het scherm zonder de hele interface te blokkeren.

- **Inhoud Laden in Kaarten/Gedeelten**  
   De `Loading` component kan beperkt worden tot specifieke gebieden van een pagina, zoals individuele kaarten of containers. Dit is nuttig wanneer u wilt aangeven dat een bepaald gedeelte van de gebruikersinterface nog aan het laden is, terwijl gebruikers kunnen blijven interageren met andere delen van de pagina.

- **Complexe Formulierverschrijvingen**  
   Voor langere formulierverschrijvingen waarbij validatie of verwerking enige tijd kost, biedt de `Loading` component visuele feedback aan gebruikers, wat hen geruststelt dat hun invoer actief wordt verwerkt.

## Stijlen {#styling}

<TableBuilder name="Loading" />
