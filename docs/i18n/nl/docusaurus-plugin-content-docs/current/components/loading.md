---
title: Loading
sidebar_position: 65
_i18n_hash: 45fa6bcfc4a2fd5995a06dc98b6f91bf
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-loading" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="loading" location="com/webforj/component/loading/Loading" top='true'/>

De `Loading`-component toont een overlay op een specifiek component of gebied, waarmee wordt aangegeven dat een bewerking bezig is en tijdelijk interactie wordt geblokkeerd. Het werkt goed voor taken zoals gegevens laden, berekeningen of achtergrondprocessen. Voor wereldwijde, app-brede processen bedekt de [`BusyIndicator`](../components/busyindicator) component de gehele interface.

<!-- INTRO_END -->

## Basisprincipes {#basics}

De eenvoudigste manier om een `Loading`-component te creëren is door het zonder extra instellingen te initialiseren. Standaard toont dit een basis-spinner bovenop de ouderinhoud. U kunt echter ook een bericht geven voor meer context.

Hier is een voorbeeld van het creëren van een `Loading`-component met een bericht:

<ComponentDemo 
path='/webforj/loadingdemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/loading/LoadingDemoView.java'
cssURL='/css/loadingstyles/loadingdemo.css'
height = '300px'
/>

## Scoped {#scoping}

De `Loading`-component in webforJ kan zich beperken tot een specifieke bovenliggende container, zoals een `Div`, zodat het alleen de interactie van de gebruiker binnen dat element blokkeert. Standaard is de `Loading`-component relatief ten opzichte van zijn bovenliggende element, wat betekent dat het de bovenliggende component bedekt in plaats van de gehele app.

Om de `Loading`-component te beperken tot zijn bovenliggende element, voegt u simpelweg de `Loading`-component toe aan de bovenliggende container. Bijvoorbeeld, als u het toevoegt aan een `Div`, geldt de laadoverlay alleen voor die `Div`:

```java
Div parentDiv = new Div();  
parentDiv.setStyle("position", "relative");
Loading loading = new Loading();
parentDiv.add(loading);
loading.open();  // Loading blokkeert alleen de interactie binnen de parentDiv
```

## Achtergrond {#backdrop}

De `Loading`-component in webforJ stelt u in staat om een achtergrond te tonen om de interactie van de gebruiker te blokkeren terwijl een proces aan de gang is. Standaard staat de component de achtergrond toe, maar u heeft de optie om deze uit te schakelen indien nodig.

Voor de `Loading`-component is de achtergrond standaard zichtbaar. U kunt deze expliciet in- of uitschakelen met de methode `setBackdropVisible()`:

```java
Loading loading = new Loading();
loading.setBackdropVisible(false);  // Schakelt de achtergrond uit
loading.open();
```
:::info Achtergrond Uit
Zelfs als u de achtergrond uitschakelt, blijft de `Loading`-component de interactie van de gebruiker blokkeren om ervoor te zorgen dat het onderliggende proces onafgebroken wordt voltooid. De achtergrond regelt simpelweg de visuele overlay, niet het blokkeren van de interactie.
:::

## `Spinner` {#spinner}

De `Loading`-component in webforJ bevat een `Spinner` die visueel aangeeft dat een achtergrondoperatie aan de gang is. U kunt deze spinner aanpassen met verschillende opties, waaronder grootte, snelheid, richting, thema en zichtbaarheid.

Hier is een voorbeeld van hoe u de spinner binnen een `Loading`-component kunt aanpassen:

<ComponentDemo 
path='/webforj/loadingspinnerdemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/loading/LoadingSpinnerDemoView.java'
cssURL='/css/loadingstyles/loadingspinnerdemo.css'
height = '300px'
/>

## Toepassingsgevallen {#use-cases}
- **Gegevens Ophalen**  
   Bij het ophalen van gegevens van een server of API, overlayt de `Loading`-component een specifiek gedeelte van de gebruikersinterface, zoals een kaart of formulier, om gebruikers te informeren dat het systeem op de achtergrond werkt. Dit is ideaal wanneer u voortgang op slechts één deel van het scherm wilt tonen zonder de gehele interface te blokkeren.

- **Inhoud Laden in Kaarten/Gedeelten**  
   De `Loading`-component kan worden beperkt tot specifieke gebieden van een pagina, zoals individuele kaarten of containers. Dit is nuttig wanneer u wilt aangeven dat een bepaald gedeelte van de gebruikersinterface nog aan het laden is, terwijl gebruikers met andere delen van de pagina kunnen interageren.

- **Complexe Formulierinzendingen**  
   Voor langere formulierinzendingen waar validatie of verwerking tijd kost, biedt de `Loading`-component visuele feedback aan gebruikers, waarmee bevestiging wordt gegeven dat hun invoer actief wordt verwerkt.

## Stijlen {#styling}

<TableBuilder name="Loading" />
