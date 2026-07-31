---
title: Dialog
sidebar_position: 30
description: >-
  Open modal popups with the Dialog component, including header, content, and
  footer sections, backdrop blur, and configurable close behavior.
_i18n_hash: 385730b12eeec91287bcbbf77b4e9c77
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-dialog" />
<DocChip chip='since' label='23.06' />
<JavadocLink type="dialog" location="com/webforj/component/dialog/Dialog" top='true'/>

De `Dialog` component toont een pop-up venster dat de huidige weergave overlapt, waardoor de aandacht wordt gevestigd op gefocuste inhoud zoals formulieren, bevestigingen of informatieve berichten.

<!-- INTRO_END -->

## `Dialog` structuur {#dialog-structure}

De `Dialog` is georganiseerd in drie secties: een kop, een inhoudsgebied en een voettekst. Componenten kunnen aan elke sectie worden toegevoegd met `addToHeader()`, `addToContent()`, en `addToFooter()`.

<ComponentDemo
path='/webforj/dialogsections'
files={['src/main/java/com/webforj/samples/views/dialog/DialogSectionsView.java']}
height='225px'
/>

## Gebruik {#usages}

1. **Gebruikersfeedback en Bevestiging**: `Dialog` componenten worden vaak gebruikt om feedback te geven of om bevestiging van de gebruiker te vragen. Ze kunnen verschillende belangrijke feedback aan een gebruiker tonen, zoals:

  >- Succesberichten
  >- Foutmeldingen
  >- Bevestigen van indieningen

2. **Formulierveld en Bewerking**: Je kunt dialogen gebruiken om gebruikersinvoer te verzamelen of hen in staat te stellen informatie op een gecontroleerde en gerichte manier te bewerken. Bijvoorbeeld, een dialoog kan verschijnen om de details van een gebruikersprofiel te bewerken of een meerstapsformulier in te vullen.

3. **Contextuele Informatie**: Het tonen van aanvullende contextuele informatie of tooltips in een dialoog kan gebruikers helpen complexe functies of data te begrijpen. Dialogen kunnen diepgaande uitleg, grafieken of helpdocumentatie bieden.

4. **Afbeeldingen en Media Voorvertoningen**: Wanneer gebruikers stukken media moeten bekijken, kan een `Dialog` worden gebruikt om grotere voorvertoningen of galerijen weer te geven, zoals bij interactie met:
  >- Afbeeldingen
  >- Video's
  >- Andere media

## Achtergrond en vervaging {#backdrop-and-blur}

Een open `Dialog` component heeft een gedimde achtergrond die subtiel de aandacht op de inhoud vestigt. Met `setBackdrop()` en `setBlurred()` kun je wijzigen hoe webforJ de inhoud achter de `Dialog` toont (of verbergt). Het aanpassen van deze attributen kan gebruikers helpen door diepte en visuele hiërarchie te bieden.

<ComponentDemo
path='/webforj/dialogbackdropblur'
files={['src/main/java/com/webforj/samples/views/dialog/DialogBackdropBlurView.java']}
height='600px'
/>

## Het openen en sluiten van de `Dialog` {#opening-and-closing-the-dialog}

Na het maken van een nieuw `Dialog` object, gebruik je de `open()` methode om de dialoog weer te geven. Vervolgens kan de `Dialog` component sluiten door een van deze acties:
- Gebruik de `close()` methode
- Druk op de <kbd>ESC</kbd> toets
- Klik buiten de `Dialog`

Ontwikkelaars kunnen kiezen welke interacties de `Dialog` sluiten met `setCancelOnEscKey()` en `setCancelOnOutsideClick()`. Daarnaast kan de `setClosable()` methode voorkomen of toestaan dat zowel het indrukken van de <kbd>ESC</kbd> toets als het klikken buiten de `Dialog` de component sluit.

<ComponentDemo
path='/webforj/dialogclose'
files={['src/main/java/com/webforj/samples/views/dialog/DialogCloseView.java']}
height='350px'
/>

## Autofocus {#auto-focus}

Wanneer ingeschakeld, geeft autofocus automatisch focus aan het eerste element binnen de dialoog dat gefocust kan worden. Dit is nuttig om de aandacht van gebruikers te richten en is aanpasbaar via de `setAutoFocus()` methode.

<ComponentDemo
path='/webforj/dialogautofocus'
files={['src/main/java/com/webforj/samples/views/dialog/DialogAutoFocusView.java']}
height='350px'
/>

## Versleepbaar {#draggable}

De `Dialog` heeft ingebouwde functionaliteit om versleepbaar te zijn, waardoor de gebruiker het `Dialog` venster kan verplaatsen door te klikken en te slepen. De positie van de `Dialog` kan worden gemanipuleerd vanuit elk van de velden erin: de kop, inhoud of voettekst.

### Vastklikken aan de rand {#snap-to-edge}
Het is ook mogelijk om dit gedrag aan te passen zodat het vastklikt aan de rand van het scherm, wat betekent dat de `Dialog` zichzelf automatisch uitlijnt met de rand van het display wanneer het wordt losgelaten van zijn sleep- en neerzetplek. Vastklikken kan worden gewijzigd via de `setSnapToEdge()` methode. De `setSnapThreshold()` neemt een aantal pixels aan, die instellen hoe ver de `Dialog` van de zijkanten van het scherm moet zijn voordat het automatisch aan de randen vastklikt.

<ComponentDemo
path='/webforj/dialogdraggable'
files={['src/main/java/com/webforj/samples/views/dialog/DialogDraggableView.java']}
height='350px'
/>

## Positionering {#positioning}

De positie van de dialoog kan worden gemanipuleerd met de ingebouwde `setPosx()` en `setPosy()` methoden. Deze methoden vereisen een stringargument dat een relevante CSS-eenheid van lengte kan vertegenwoordigen, zoals pixels of schermhoogte/-breedte. Een lijst van deze metingen [is te vinden via deze link](https://developer.mozilla.org/en-US/docs/Learn/CSS/Building_blocks/Values_and_units#numbers_lengths_and_percentages).

<ComponentDemo
path='/webforj/dialogpositioning'
files={['src/main/java/com/webforj/samples/views/dialog/DialogPositioningView.java']}
height='350px'
/>

### Verticale uitlijning {#vertical-alignment}

Naast het handmatig toewijzen van een dialoog's X- en Y-positie, is het mogelijk om de ingebouwde enum-klasse van de dialoog te gebruiken om de `Dialog` uit te lijnen. Er zijn drie mogelijke waarden: `TOP`, `CENTER` en `BOTTOM`, die allemaal kunnen worden gebruikt met de `setAlignment()` methode.

<ComponentDemo
path='/webforj/dialogalignments'
files={['src/main/java/com/webforj/samples/views/dialog/DialogAlignmentsView.java']}
height='550px'
/>

### Volledig scherm en breekpunten {#full-screen-and-breakpoints}

De `Dialog` kan worden ingesteld om de volledig schermmodus in te schakelen. Wanneer de volledig scherm modus is ingeschakeld, kan de `Dialog` niet worden verplaatst of gepositioneerd. Deze modus kan worden gemanipuleerd met het breekpuntattribuut van de `Dialog`. Het breekpunt is een mediaquery die bepaalt wanneer de `Dialog` automatisch naar de volledig schermmodus zal overschakelen. Wanneer de query overeenkomt, verandert de `Dialog` naar volledig scherm - anders wordt deze gepositioneerd.

### Automatische breedte <DocChip chip='since' label='26.00' /> {#auto-width}

Standaard rekt de `Dialog` zich uit om de beschikbare horizontale ruimte in te vullen. Wanneer de automatische breedte is ingeschakeld via `setAutoWidth(true)`, past de `Dialog` zich aan op basis van de breedte van de inhoud.

<ComponentDemo
path='/webforj/dialogautowidth'
files={['src/main/java/com/webforj/samples/views/dialog/DialogAutoWidthView.java']}
height='350px'
/>

## Stijlen {#styling}

### Thema's {#themes}

`Dialog` componenten worden geleverd met <JavadocLink type="foundation" location="com/webforj/component/dialog/Dialog.Theme.html">7 discrete thema's </JavadocLink> ingebouwd voor snelle styling zonder het gebruik van CSS. Deze thema's zijn vooraf gedefinieerde stijlen die op knoppen kunnen worden toegepast om hun uiterlijk en visuele presentatie te veranderen. Ze bieden een snelle en consistente manier om het uiterlijk van knoppen door een applicatie heen aan te passen.

Hoewel er veel gebruikscases zijn voor elk van de verschillende thema's, zijn hier enkele voorbeeldtoepassingen:

  - **Gevaren**: Acties met ernstige gevolgen, zoals het wissen van ingevulde informatie of het permanent verwijderen van een account/data is een goede gebruikscase voor dialogen met het Gevaren-thema.
  - **Standaard**: Het standaardthema is geschikt voor acties door een applicatie die geen speciale aandacht vereisen en die algemeen zijn, zoals het in- of uitschakelen van een instelling.
  - **Primair**: Dit thema is geschikt als een hoofd "actie-oproep" op een pagina, zoals aanmelden, wijzigingen opslaan of doorgaan naar een andere pagina.
  - **Succes**: Succes-thema dialogen zijn uitstekend voor het visualiseren van een succesvolle voltooiing van een element in een applicatie, zoals bij het indienen van een formulier of het voltooien van een aanmeldproces. Het succes-thema kan programmatisch worden toegepast zodra een succesvolle actie is voltooid.
  - **Waarschuwing**: Waarschuwingsdialogen zijn nuttig om gebruikers te wijzen op een potentieel riskante actie, zoals wanneer ze een pagina met niet-opgeslagen wijzigingen verlaten. Deze acties zijn vaak minder ingrijpend dan die welke het Gevaren-thema zouden gebruiken.
  - **Grijs**: Goed voor subtiele acties, zoals kleine instellingen of acties die meer aanvullend zijn op een pagina, en niet deel uitmaken van de belangrijkste functionaliteit.
  - **Informatie**: Het Informatie-thema is een goede keuze om verduidelijkende, aanvullende informatie aan een gebruiker te bieden wanneer dit nodig is.

<ComponentDemo
path='/webforj/dialogthemes'
files={['src/main/java/com/webforj/samples/views/dialog/DialogThemesView.java']}
height='500px'
/>

<TableBuilder name="Dialog" />
