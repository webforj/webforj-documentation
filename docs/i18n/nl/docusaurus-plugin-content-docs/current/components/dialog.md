---
title: Dialog
sidebar_position: 30
description: >-
  Open modal popups with the Dialog component, including header, content, and
  footer sections, backdrop blur, and configurable close behavior.
_i18n_hash: 8f78bdc280006c73955232e7f52ed365
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-dialog" />
<DocChip chip='since' label='23.06' />
<JavadocLink type="dialog" location="com/webforj/component/dialog/Dialog" top='true'/>

De `Dialog` component toont een pop-up venster dat zich over de huidige weergave heen legt en de aandacht vestigt op gefocuste inhoud, zoals formulieren, bevestigingen of informatieve berichten.

## `Dialog` structuur {#dialog-structure}

De `Dialog` is georganiseerd in drie secties: een header, een contentgebied en een footer. Componenten kunnen aan elke sectie worden toegevoegd met `addToHeader()`, `addToContent()`, en `addToFooter()`.

<ComponentDemo
path='/webforj/dialogsections'
files={['src/main/java/com/webforj/samples/views/dialog/DialogSectionsView.java']}
height='375px'
/>

## Toepassingen {#usages}

1. **Feedback van gebruikers en bevestiging**: `Dialog` componenten worden vaak gebruikt om feedback te geven of om bevestiging van de gebruiker te vragen. Ze kunnen verschillende belangrijke feedbackstukken aan een gebruiker tonen, zoals:

  >- Succesberichten
  >- Foutmeldingen
  >- Bevestiging van inzendingen

2. **Invoer van formulieren en bewerking**: U kunt dialogen gebruiken om gebruikersinvoer te verzamelen of hen in staat te stellen informatie op een gecontroleerde en gefocuste manier te bewerken. Een dialoog kan bijvoorbeeld opkomen om gebruikersprofielgegevens te bewerken of een meerfasig formulier in te vullen.

3. **Contextuele informatie**: Het weergeven van aanvullende contextuele informatie of tooltips in een dialoog kan gebruikers helpen complexe functies of gegevens te begrijpen. Dialogen kunnen diepgaande uitleg, grafieken of documentatie bieden.

4. **Afbeelding en mediavoorbeelden**: Wanneer gebruikers mediastukken moeten bekijken, kan een `Dialog` worden gebruikt om grotere voorbeelden of galerijen weer te geven, zoals bij interactie met:
  >- Afbeeldingen
  >- Video's
  >- Andere media

## Achtergrond en vervaging {#backdrop-and-blur}

Een geopende `Dialog` component heeft een gedimde achtergrond die subtiel de aandacht vestigt op de inhoud. Met behulp van `setBackdrop()` en `setBlurred()` kunt u wijzigen hoe webforJ de inhoud achter de `Dialog` weergeeft (of verbergt). Het aanpassen van deze attributen kan gebruikers helpen door diepte en visuele hiërarchie te bieden.

<ComponentDemo
path='/webforj/dialogbackdropblur'
files={['src/main/java/com/webforj/samples/views/dialog/DialogBackdropBlurView.java']}
height='600px'
/>

## Het openen en sluiten van de `Dialog` {#opening-and-closing-the-dialog}

Na het creëren van een nieuw `Dialog` object, gebruik de `open()` methode om de dialoog weer te geven. Om deze programmatisch te sluiten, gebruikt u de `close()` methode. U kunt ook controleren welke gebruikersinteracties de dialoog sluiten:

- **Klikken buiten** sluit de `Dialog` standaard, omdat `cancelOnOutsideClick` standaard op `true` staat. Gebruik `setCancelOnOutsideClick(false)` om dit gedrag uit te schakelen.
- **Drukken op <kbd>ESC</kbd>** sluit de `Dialog` niet standaard, omdat `cancelOnEscKey` standaard op `false` staat. Gebruik `setCancelOnEscKey(true)` om dit gedrag in te schakelen.

Gebruik `setCloseable(true)` om beide interacties in te schakelen, of `setCloseable(false)` om beide uit te schakelen. De volgende demo activeert beide interacties.

<ComponentDemo
path='/webforj/dialogclose'
files={['src/main/java/com/webforj/samples/views/dialog/DialogCloseView.java']}
height='375px'
/>

## Auto-focus {#auto-focus}

Wanneer ingeschakeld, zal auto-focus automatisch de focus geven aan het eerste element binnen de dialoog dat gefocust kan worden. Dit is nuttig om de aandacht van gebruikers te richten en is aanpasbaar via de `setAutoFocus()` methode.

<ComponentDemo
path='/webforj/dialogautofocus'
files={['src/main/java/com/webforj/samples/views/dialog/DialogAutoFocusView.java']}
height='400px'
/>

## Slepen {#draggable}

De `Dialog` heeft ingebouwde functionaliteit om te slepen, zodat de gebruiker het `Dialog` venster kan verplaatsen door te klikken en te slepen. De positie van de `Dialog` kan worden gemanipuleerd vanuit elk van de velden binnenin: de header, de inhoud of de footer.

### Snap naar de rand {#snap-to-edge}

Het is ook mogelijk om dit gedrag te kalibreren zodat het naar de rand van het scherm "snapt", wat betekent dat de `Dialog` zich automatisch uitlijnt met de rand van het display wanneer het wordt losgelaten na het slepen. Snappen kan worden gewijzigd via de `setSnapToEdge()` methode. De `setSnapThreshold()` neemt een aantal pixels op, die instelt hoe ver de `Dialog` van de zijkanten van het scherm moet zijn voordat deze automatisch naar de randen "snapt".

<ComponentDemo
path='/webforj/dialogdraggable'
files={['src/main/java/com/webforj/samples/views/dialog/DialogDraggableView.java']}
height='325px'
/>

## Positionering {#positioning}

De positie van de dialoog kan worden gemanipuleerd met behulp van de ingebouwde `setPosx()` en `setPosy()` methoden. Deze methoden nemen een stringargument dat elke toepasselijke CSS-eenheid van lengte kan vertegenwoordigen, zoals pixels of weergavehoogte/-breedte. Een lijst van deze metingen [is te vinden via deze link](https://developer.mozilla.org/en-US/docs/Learn/CSS/Building_blocks/Values_and_units#numbers_lengths_and_percentages).

<ComponentDemo
path='/webforj/dialogpositioning'
files={['src/main/java/com/webforj/samples/views/dialog/DialogPositioningView.java']}
height='400px'
/>

### Verticale uitlijning {#vertical-alignment}

Naast de handmatige toewijzing van de X- en Y-positie van een dialoog, is het mogelijk om de ingebouwde enum-klasse van de dialoog te gebruiken om de `Dialog` uit te lijnen. Er zijn drie mogelijke waarden, `TOP`, `CENTER` en `BOTTOM`, die elke kan worden gebruikt met de `setAlignment()` methode.

<ComponentDemo
path='/webforj/dialogalignments'
files={['src/main/java/com/webforj/samples/views/dialog/DialogAlignmentsView.java']}
height='450px'
/>

### Volledig scherm en breekpunten {#full-screen-and-breakpoints}

De `Dialog` kan worden ingesteld op de volledig scherm modus. Wanneer volledig scherm is ingeschakeld, kan de `Dialog` niet worden verplaatst of gepositioneerd. Deze modus kan worden gemanipuleerd met de breekpuntsattributen van de `Dialog`. Het breekpunt is een mediaquery die bepaalt wanneer de `Dialog` automatisch in de volledig scherm modus zal worden omgeschakeld. Wanneer de query overeenkomt, verandert de `Dialog` in volledig scherm - anders is deze gepositioneerd.

### Auto breedte <DocChip chip='since' label='26.00' /> {#auto-width}

Standaard strekt de `Dialog` zich uit om de beschikbare horizontale ruimte in te vullen. Wanneer auto breedte is ingeschakeld via `setAutoWidth(true)`, past de `Dialog` zich in plaats daarvan aan op basis van de inhoudsbreedte.

<ComponentDemo
path='/webforj/dialogautowidth'
files={['src/main/java/com/webforj/samples/views/dialog/DialogAutoWidthView.java']}
height='350px'
/>

## Stijlen {#styling}

### Thema's {#themes}

`Dialog` componenten komen met <JavadocLink type="foundation" location="com/webforj/component/dialog/Dialog.Theme.html">7 discrete thema's</JavadocLink> die zijn ingebouwd voor snelle styling zonder het gebruik van CSS. Deze thema's zijn vooraf gedefinieerde stijlen die op knoppen kunnen worden toegepast om hun uiterlijk en visuele presentatie te veranderen. Ze bieden een snelle en consistente manier om de uitstraling van knoppen in een applicatie te personaliseren.

Hoewel er veel gebruiksmogelijkheden zijn voor elk van de verschillende thema's, zijn hier enkele voorbeeldtoepassingen:

  - **Gevaren**: Acties met ernstige gevolgen, zoals het wissen van ingevulde informatie of permanent verwijderen van een account/data, vormen een goed gebruik van dialogen met het Gevaren-thema.
  - **Standaard**: Het standaardthema is geschikt voor acties binnen een applicatie die geen speciale aandacht vereisen en generiek zijn, zoals het omschakelen van een instelling.
  - **Primair**: Dit thema is geschikt als een belangrijkste "call-to-action" op een pagina, zoals inschrijven, wijzigingen opslaan of doorgaan naar een andere pagina.
  - **Succes**: Succes-thema dialogen zijn uitstekend voor het visualiseren van een succesvolle voltooiing van een element in een applicatie, zoals de indienen van een formulier of voltooiing van een aanmeldproces. Het succes-thema kan programmatisch worden toegepast nadat een succesvolle actie is voltooid.
  - **Waarschuwing**: Waarschuwingsdialogen zijn nuttig om gebruikers te waarschuwen dat ze op het punt staan om een potentieel risicovolle actie uit te voeren, zoals wanneer ze navigeren weg van een pagina met niet-opgeslagen wijzigingen. Deze acties zijn vaak minder impactvol dan die gebruik zouden maken van het Gevaren-thema.
  - **Grijs**: Goed voor subtiele acties, zoals minimale instellingen of acties die meer aanvullend zijn voor een pagina, en niet deel uitmaken van de hoofdfunctie.
  - **Info**: Het Info-thema is een goede keuze om verduidelijkende, aanvullende informatie aan een gebruiker te geven wanneer dat nodig is.

<ComponentDemo
path='/webforj/dialogthemes'
files={['src/main/java/com/webforj/samples/views/dialog/DialogThemesView.java']}
height='375px'
/>

<TableBuilder name="Dialog" />
