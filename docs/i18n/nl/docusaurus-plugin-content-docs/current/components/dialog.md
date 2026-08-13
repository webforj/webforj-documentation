---
title: Dialog
sidebar_position: 30
description: >-
  Open modal popups with the Dialog component, including header, content, and
  footer sections, backdrop blur, and configurable close behavior.
_i18n_hash: 901c54134f4c21092deb23457747a29b
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-dialog" />
<DocChip chip='since' label='23.06' />
<JavadocLink type="dialog" location="com/webforj/component/dialog/Dialog" top='true'/>

De `Dialog` component toont een popupvenster dat de huidige weergave overlapt en de aandacht vestigt op gefocuste inhoud zoals formulieren, bevestigingen of informatieve berichten.

<!-- INTRO_END -->

## `Dialog` structuur {#dialog-structure}

De `Dialog` is georganiseerd in drie secties: een kop, een inhoudsgebied en een voettekst. Componenten kunnen aan elke sectie worden toegevoegd met behulp van `addToHeader()`, `addToContent()` en `addToFooter()`.

<ComponentDemo
path='/webforj/dialogsections'
files={['src/main/java/com/webforj/samples/views/dialog/DialogSectionsView.java']}
height='375px'
/>

## Toepassingen {#usages}

1. **Gebruikersfeedback en bevestiging**: `Dialog` componenten worden vaak gebruikt om feedback te geven of om bevestiging van de gebruiker te vragen. Ze kunnen verschillende belangrijke feedback aan een gebruiker tonen, zoals:

  >- Succesberichten
  >- Foutmeldingen
  >- Bevestigen van indieningen

2. **Formuliervelden en bewerking**: Je kunt dialoogvensters gebruiken om gebruikersinvoer te verzamelen of hen in staat te stellen informatie op een gecontroleerde en gefocuste manier te bewerken. Bijvoorbeeld, een dialoog kan verschijnen om gebruikersprofielgegevens te bewerken of een meerstapsformulier te voltooien.

3. **Contextuele informatie**: Het tonen van aanvullende contextuele informatie of tooltips in een dialoog kan gebruikers helpen complexe functies of gegevens te begrijpen. Dialoogvensters kunnen diepgaande uitleg, grafieken of helpdocumentatie bieden.

4. **Afbeeldingen en media voorvertoningen**: Wanneer gebruikers delen van media moeten bekijken, kan een `Dialog` worden gebruikt om grotere voorvertoningen of galerijen te tonen, zoals wanneer ze interactie hebben met:
  >- Afbeeldingen
  >- Video's
  >- Andere media

## Achtergrond en vervaging {#backdrop-and-blur}

Een geopende `Dialog` component heeft een gedimde achtergrond die subtiel de aandacht vestigt op de inhoud. Met `setBackdrop()` en `setBlurred()` kun je wijzigen hoe webforJ de inhoud achter de `Dialog` toont (of verbergt). Het aanpassen van deze attributen kan gebruikers helpen door diepte en visuele hiërarchie te bieden.

<ComponentDemo
path='/webforj/dialogbackdropblur'
files={['src/main/java/com/webforj/samples/views/dialog/DialogBackdropBlurView.java']}
height='600px'
/>

## Het openen en sluiten van de `Dialog` {#opening-and-closing-the-dialog}

Na het aanmaken van een nieuw `Dialog` object, gebruik de `open()` methode om de dialoog weer te geven. Vervolgens kan de `Dialog` component sluiten door een van deze acties:
- De `close()` methode gebruiken
- De <kbd>ESC</kbd> toets indrukken
- Buiten de `Dialog` klikken

Ontwikkelaars kunnen kiezen welke interacties de `Dialog` sluiten met `setCancelOnEscKey()` en `setCancelOnOutsideClick()`. Bovendien kan de `setClosable()` methode voorkomen of toestaan dat zowel het indrukken van de <kbd>ESC</kbd> toets als het klikken buiten de `Dialog` de component sluit.

<ComponentDemo
path='/webforj/dialogclose'
files={['src/main/java/com/webforj/samples/views/dialog/DialogCloseView.java']}
height='375px'
/>

## Autofocus {#auto-focus}

Wanneer ingeschakeld, zal autofocus automatisch de focus geven aan het eerste element binnen de dialoog dat gefocust kan worden. Dit is nuttig om de aandacht van gebruikers te sturen en is aanpasbaar via de `setAutoFocus()` methode.

<ComponentDemo
path='/webforj/dialogautofocus'
files={['src/main/java/com/webforj/samples/views/dialog/DialogAutoFocusView.java']}
height='400px'
/>

## Versleepbaar {#draggable}

De `Dialog` heeft ingebouwde functionaliteit om versleepbaar te zijn, waardoor de gebruiker het `Dialog` venster kan verplaatsen door te klikken en te slepen. De positie van de `Dialog` kan worden aangepast vanuit elk van de velden binnenin: de kop, inhoud of voettekst.

### Vastklikken aan de rand {#snap-to-edge}
Het is ook mogelijk om dit gedrag te kalibreren zodat het vastklikt aan de rand van het scherm, wat betekent dat de `Dialog` zichzelf automatisch uitlijnt met de rand van het scherm wanneer het wordt losgelaten tijdens het slepen. Vastklikken kan worden gewijzigd via de `setSnapToEdge()` methode. De `setSnapThreshold()` neemt een aantal pixels aan, wat aangeeft hoe ver de `Dialog` van de zijkanten van het scherm moet zijn voordat deze automatisch naar de randen klikt.

<ComponentDemo
path='/webforj/dialogdraggable'
files={['src/main/java/com/webforj/samples/views/dialog/DialogDraggableView.java']}
height='325px'
/>

## Positionering {#positioning}

De positie van de dialoog kan worden aangepast met behulp van de ingebouwde `setPosx()` en `setPosy()` methoden. Deze methoden nemen een stringargument dat elke toepasselijke CSS-eenheid van lengte kan vertegenwoordigen, zoals pixels of hoogte/breedte van het uitzicht. Een lijst van deze metingen [kan hier worden gevonden](https://developer.mozilla.org/en-US/docs/Learn/CSS/Building_blocks/Values_and_units#numbers_lengths_and_percentages).

<ComponentDemo
path='/webforj/dialogpositioning'
files={['src/main/java/com/webforj/samples/views/dialog/DialogPositioningView.java']}
height='400px'
/>

### Verticale uitlijning {#vertical-alignment}

Naast handmatige toewijzing van een dialoog's X- en Y-positie, is het mogelijk om de ingebouwde enum-klasse van de dialoog te gebruiken om de `Dialog` uit te lijnen. Er zijn drie mogelijke waarden, `TOP`, `CENTER` en `BOTTOM`, die allemaal kunnen worden gebruikt met de `setAlignment()` methode.

<ComponentDemo
path='/webforj/dialogalignments'
files={['src/main/java/com/webforj/samples/views/dialog/DialogAlignmentsView.java']}
height='450px'
/>

### Volledig scherm en breekpunten {#full-screen-and-breakpoints}

De `Dialog` kan worden ingesteld op volledig scherm modus. Wanneer volledig scherm is ingeschakeld, kan de `Dialog` niet worden verplaatst of gepositioneerd. Deze modus kan worden aangepast met het breekpuntattribut van de `Dialog`. Het breekpunt is een mediaquery die bepaalt wanneer de `Dialog` automatisch naar de volledig schermmodus overschakelt. Wanneer de query overeenkomt, verandert de `Dialog` naar volledig scherm - anders is hij gepositioneerd.

### Automatische breedte <DocChip chip='since' label='26.00' /> {#auto-width}

Standaard strekt de `Dialog` zich uit om de beschikbare horizontale ruimte te vullen. Wanneer automatische breedte wordt ingeschakeld via `setAutoWidth(true)`, past de `Dialog` zijn grootte aan op basis van de breedte van de inhoud.

<ComponentDemo
path='/webforj/dialogautowidth'
files={['src/main/java/com/webforj/samples/views/dialog/DialogAutoWidthView.java']}
height='350px'
/>

## Stijlen {#styling}

### Thema's {#themes}

`Dialog` componenten worden geleverd met <JavadocLink type="foundation" location="com/webforj/component/dialog/Dialog.Theme.html">7 discrete thema's</JavadocLink> die zijn ingebouwd voor snelle styling zonder gebruik van CSS. Deze thema's zijn vooraf gedefinieerde stijlen die kunnen worden toegepast op knoppen om hun uiterlijk en visuele presentatie te wijzigen. Ze bieden een snelle en consistente manier om het uiterlijk van knoppen door een toepassing heen aan te passen.

Hoewel er vele gebruiksgevallen zijn voor elk van de verschillende thema's, zijn hier enkele voorbeelden:

  - **Gevaren**: Acties met ernstige gevolgen, zoals het wissen van ingevulde informatie of het permanent verwijderen van een account/gegevens, vertegenwoordigen een goed gebruiksgegeven voor dialoogvensters met het Gevaar-thema.
  - **Standaard**: Het standaardthema is geschikt voor acties binnen een toepassing die geen speciale aandacht vereisen en die algemeen zijn, zoals het toggelen van een instelling.
  - **Primaire**: Dit thema is geschikt als de hoofdaanroep tot actie op een pagina, zoals aanmelden, wijzigingen opslaan of doorgaan naar een andere pagina.
  - **Succes**: Succes-thema dialoogvensters zijn uitstekend voor het visualiseren van de succesvolle voltooiing van een element in een toepassing, zoals het indienen van een formulier of de voltooiing van een aanmeldingsproces. Het succesvolle thema kan programmatisch worden toegepast zodra een succesvolle actie is voltooid.
  - **Waarschuwing**: Waarschuwingsdialoogvensters zijn nuttig om gebruikers te laten weten dat ze op het punt staan een potentieel risicovolle actie uit te voeren, zoals wanneer ze van een pagina navigeren met onopgeslagen wijzigingen. Deze acties zijn vaak minder impactvol dan die welke het Gevaar-thema zouden gebruiken.
  - **Grijs**: Goed voor subtiele acties, zoals kleine instellingen of acties die meer aanvullend zijn voor een pagina en niet deel uitmaken van de belangrijkste functionaliteit.
  - **Info**: Het Info-thema is een goede keuze om verduidelijkende, aanvullende informatie aan een gebruiker te bieden wanneer dat nodig is.

<ComponentDemo
path='/webforj/dialogthemes'
files={['src/main/java/com/webforj/samples/views/dialog/DialogThemesView.java']}
height='375px'
/>

<TableBuilder name="Dialog" />
