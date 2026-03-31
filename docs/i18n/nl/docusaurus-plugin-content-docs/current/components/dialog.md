---
title: Dialog
sidebar_position: 30
_i18n_hash: 4896ea2a90b7c5155fe9ef291e69b2ad
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-dialog" />
<DocChip chip='since' label='23.06' />
<JavadocLink type="dialog" location="com/webforj/component/dialog/Dialog" top='true'/>

De `Dialog` component toont een pop-upvenster dat over de huidige weergave heen ligt en de aandacht vestigt op gefocuste inhoud zoals formulieren, inhoudsbevestigingen of informatieve berichten.

<!-- INTRO_END -->

## `Dialog` structuur {#dialog-structure}

De `Dialog` is georganiseerd in drie secties: een koptekst, een inhoudsgebied en een voettekst. Componenten kunnen aan elke sectie worden toegevoegd met `addToHeader()`, `addToContent()` en `addToFooter()`.

<ComponentDemo 
path='/webforj/dialogsections?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/dialog/DialogSectionsView.java'
height = '225px'
/>

## Gebruik {#usages}

1. **Feedback en bevestiging van de gebruiker**: `Dialog` componenten worden vaak gebruikt om feedback te geven of om bevestiging van de gebruiker te vragen. Ze kunnen verschillende belangrijke feedback aan een gebruiker tonen, zoals:

  >- Succesberichten 
  >- Foutmeldingen
  >- Bevestigingen van indieningen

2. **Formulierinvoer en bewerking**: Je kunt dialogen gebruiken om gebruikersinvoer te verzamelen of om hen in staat te stellen informatie op een gecontroleerde en gefocuste manier te bewerken. Bijvoorbeeld, een dialoog kan verschijnen om gebruikersprofielgegevens te bewerken of een meerstappenformulier in te vullen.

3. **Contextuele informatie**: Het weergeven van aanvullende contextuele informatie of tooltip-informatie in een dialoog kan gebruikers helpen complexe functies of gegevens te begrijpen. Dialogen kunnen diepgaande uitleg, grafieken of hulpdocumentatie bieden.

4. **Afbeelding en media voorvertoningen**: Wanneer gebruikers stukjes media moeten bekijken, kan een `Dialog` worden gebruikt om grotere voorvertoningen of galeries te tonen, zoals bij interactie met:
  >- Afbeeldingen
  >- Video's
  >- Andere media

## Achtergrond en vervaging {#backdrop-and-blur}

Door de achtergrondattribuut van de webforJ `Dialog` component in te schakelen, wordt er een achtergrond weergegeven achter de `Dialog`. Bovendien zal de vervaagde attribuut van de `Dialog` de achtergrond vervagen wanneer deze is ingeschakeld. Het aanpassen van deze instellingen kan gebruikers helpen door diepte, visuele hiërarchie en context te bieden, wat leidt tot duidelijkere begeleiding voor een gebruiker.

<ComponentDemo 
path='/webforj/dialogbackdropblur?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/dialog/DialogBackdropBlurView.java'
height = '300px'
/>

## Het openen en sluiten van de `Dialog` {#opening-and-closing-the-dialog}

Na het maken van een nieuw `Dialog` object, gebruik de `open()` methode om de dialoog weer te geven. Vervolgens kan de `Dialog` component worden gesloten door een van deze acties:
- Gebruik van de `close()` methode
- Druk op de <kbd>ESC</kbd> toets
- Klik buiten de `Dialog`

Ontwikkelaars kunnen kiezen welke interacties de `Dialog` sluiten met `setCancelOnEscKey()` en `setCancelOnOutsideClick()`. Bovendien kan de `setClosable()` methode verhinderen of toestaan dat zowel het indrukken van de <kbd>ESC</kbd> toets als het klikken buiten de `Dialog` de component sluit.

<ComponentDemo 
path='/webforj/dialogclose?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/dialog/DialogCloseView.java'
height = '350px'
/>

## Auto-focus {#auto-focus}

Wanneer ingeschakeld, zal auto-focus automatisch de focus geven aan het eerste element binnen de dialoog dat gefocust kan worden. Dit is nuttig om de aandacht van gebruikers te sturen en kan worden aangepast via de `setAutoFocus()` methode.

<ComponentDemo 
path='/webforj/dialogautofocus?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/dialog/DialogAutoFocusView.java'
height = '350px'
/>

## Versleepbaar {#draggable}

De `Dialog` heeft ingebouwde functionaliteit om versleepbaar te zijn, waardoor de gebruiker het `Dialog` venster kan verplaatsen door te klikken en te slepen. De positie van de `Dialog` kan worden aangepast vanaf elk van de velden binnenin: de koptekst, inhoud of voettekst.

### Vastklikken aan de rand {#snap-to-edge}
Het is ook mogelijk om dit gedrag te calibreren om aan de rand van het scherm te vast te klikken, wat betekent dat de `Dialog` zichzelf automatisch zal uitlijnen met de rand van het display wanneer het wordt vrijgegeven van zijn sleep- en laatdatum. Vastklikken kan worden gewijzigd via de `setSnapToEdge()` methode. De `setSnapThreshold()` neemt een aantal pixels, wat zal instellen hoe ver de `Dialog` van de zijkanten van het scherm moet zijn voordat het automatisch aan de randen vastklikt.  

<ComponentDemo 
path='/webforj/dialogdraggable?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/dialog/DialogDraggableView.java'
height = '350px'
/>

## Positionering {#positioning}

De positie van de dialoog kan worden aangepast met behulp van de ingebouwde `setPosx()` en `setPosy()` methoden. Deze methoden nemen een stringargument dat elke toepasselijke CSS-eenheid van lengte kan vertegenwoordigen, zoals pixels of weergavehoogte/-breedte. Een lijst van deze metingen [kan worden gevonden via deze link](https://developer.mozilla.org/en-US/docs/Learn/CSS/Building_blocks/Values_and_units#numbers_lengths_and_percentages).

<ComponentDemo 
path='/webforj/dialogpositioning?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/dialog/DialogPositioningView.java'
height = '350px'
/>

### Verticale uitlijning {#vertical-alignment}

Naast handmatige toewijzing van de X- en Y-positie van een dialoog, is het mogelijk om de ingebouwde enum-klasse van de dialoog te gebruiken om de `Dialog` uit te lijnen. Er zijn drie mogelijke waarden, `TOP`, `CENTER` en `BOTTOM`, die allemaal kunnen worden gebruikt met de `setAlignment()` methode. 

<ComponentDemo 
path='/webforj/dialogalignments?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/dialog/DialogAlignmentsView.java'
height = '550px'
/>

### Volledig scherm en breekpunten {#full-screen-and-breakpoints}

De `Dialog` kan worden ingesteld om de volledige schermmodi te betreden. Wanneer de volledige schermmodus is ingeschakeld, kan de `Dialog` niet worden verplaatst of gepositioneerd. Deze modus kan worden aangepast met de breekpuntattribuut van de `Dialog`. De breekpunt is een mediaquery die componenten aangeeft wanneer de `Dialog` automatisch naar de volle schermmodus zal overschakelen. Wanneer de query overeenkomt, verandert de `Dialog` in volledig scherm - anders is deze gepositioneerd.

## Stijling {#styling}

### Thema's {#themes}

`Dialog` componenten worden geleverd met <JavadocLink type="foundation" location="com/webforj/component/dialog/Dialog.Theme.html">7 discrete thema's </JavadocLink> ingebouwd voor snelle styling zonder gebruik van CSS. Deze thema's zijn vooraf gedefinieerde stijlen die op knoppen kunnen worden toegepast om hun uiterlijk en visuele presentatie te veranderen. Ze bieden een snelle en consistente manier om de uitstraling van knoppen in een applicatie aan te passen.

Hoewel er veel gebruikscases zijn voor elk van de verschillende thema's, zijn enkele voorbeelden:

  - **Gevaren**: Acties met ernstige gevolgen, zoals het wissen van ingevulde informatie, of het permanent verwijderen van een account/data vertegenwoordigen een goede gebruik case voor dialogen met het Gevaren-thema.
  - **Standaard**: Het standaardthema is geschikt voor acties in een applicatie die geen speciale aandacht vereisen en die generiek zijn, zoals het toggelen van een instelling.
  - **Primair**: Dit thema is geschikt als een belangrijke "call-to-action" op een pagina, zoals aanmelden, wijzigingen opslaan of verder gaan naar een andere pagina.
  - **Succes**: Succesthema-dialogen zijn uitstekend voor het visualiseren van succesvolle voltooiing van een element in een applicatie, zoals het indienen van een formulier of de voltooiing van een aanmeldproces. Het succes-thema kan programmatic worden toegepast zodra een succesvolle actie is voltooid.
  - **Waarschuwing**: Waarschuwingsdialogen zijn nuttig om gebruikers aan te geven dat ze op het punt staan een potentieel riskante actie uit te voeren, zoals bij het navigeren weg van een pagina met niet-opgeslagen wijzigingen. Deze acties zijn vaak minder ingrijpend dan die welke het Gevaren-thema zouden gebruiken.
  - **Grijs**: Goed voor subtiele acties, zoals kleinere instellingen of acties die meer aanvullend zijn aan een pagina, en niet deel uitmaken van de belangrijkste functionaliteit.
  - **Informatie**: Het Informatie-thema is een goede keuze om verduidelijkende, aanvullende informatie aan een gebruiker te bieden wanneer dat nodig is.

<ComponentDemo 
path='/webforj/dialogthemes?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/dialog/DialogThemesView.java'
height = '500px'
/>

<TableBuilder name="Dialog" />
