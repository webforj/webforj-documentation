---
title: Dialog
sidebar_position: 30
description: >-
  Open modal popups with the Dialog component, including header, content, and
  footer sections, backdrop blur, and configurable close behavior.
_i18n_hash: 3dcdd5a9a66f2b00229064500da2bb79
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-dialog" />
<DocChip chip='since' label='23.06' />
<JavadocLink type="dialog" location="com/webforj/component/dialog/Dialog" top='true'/>

De `Dialog` component toont een popupvenster dat bovenop de huidige weergave verschijnt, waarmee de aandacht wordt gevestigd op belangrijke inhoud zoals formulieren, bevestigingen of informatieve berichten.

<!-- INTRO_END -->

## `Dialog` structuur {#dialog-structure}

De `Dialog` is georganiseerd in drie secties: een koptekst, een inhoudsgebied en een voettekst. Componenten kunnen aan elke sectie worden toegevoegd met `addToHeader()`, `addToContent()` en `addToFooter()`.

<ComponentDemo
path='/webforj/dialogsections'
files={['src/main/java/com/webforj/samples/views/dialog/DialogSectionsView.java']}
height='225px'
/>

## Toepassingen {#usages}

1. **Gebruikersfeedback en bevestiging**: `Dialog` componenten worden vaak gebruikt om feedback te geven of om bevestiging van de gebruiker te vragen. Ze kunnen verschillende belangrijke feedback voor een gebruiker tonen, zoals:

  >- Succes berichten
  >- Foutmeldingen
  >- Bevestigingsindieningen

2. **Formulierinvoer en bewerking**: Je kunt dialogen gebruiken om gebruikersinvoer te verzamelen of om hen in staat te stellen informatie op een gecontroleerde en gerichte manier te bewerken. Bijvoorbeeld, een dialoog kan verschijnen om gebruikersprofiel details te bewerken of een meerstapsformulier in te vullen.

3. **Contextuele informatie**: Het tonen van aanvullende contextuele informatie of tooltips in een dialoog kan gebruikers helpen complexe functies of gegevens te begrijpen. Dialogen kunnen diepere uitleg, grafieken of hulppublicaties bieden.

4. **Afbeelding en media voorvertoningen**: Wanneer gebruikers media willen bekijken, kan een `Dialog` worden gebruikt om grotere voorvertoningen of galerijen te tonen, zoals bij interactie met:
  >- Afbeeldingen
  >- Video's
  >- Andere media

## Achtergrond en vervaging {#backdrop-and-blur}

Door de achtergrond-attribuut van de webforJ `Dialog` component in te schakelen, wordt er een achtergrond weergegeven achter de `Dialog`. Bovendien zal, wanneer ingeschakeld, het vervagingsattribuut van de Dialog de achtergrond van de `Dialog` vervagen. Het aanpassen van deze instellingen kan gebruikers helpen door dieptes, visuele hiërarchie en context te bieden, wat leidt tot duidelijkere begeleiding voor een gebruiker.

<ComponentDemo
path='/webforj/dialogbackdropblur'
files={['src/main/java/com/webforj/samples/views/dialog/DialogBackdropBlurView.java']}
height='300px'
/>

## Het openen en sluiten van de `Dialog` {#opening-and-closing-the-dialog}

Na het creëren van een nieuw `Dialog` object, gebruik je de `open()` methode om de dialoog weer te geven. Vervolgens kan de `Dialog` component worden gesloten door een van deze acties:
- De `close()` methode te gebruiken
- De <kbd>ESC</kbd> toets in te drukken
- Buiten de `Dialog` te klikken

Ontwikkelaars kunnen kiezen welke interacties de `Dialog` sluiten met `setCancelOnEscKey()` en `setCancelOnOutsideClick()`. Bovendien kan de `setClosable()` methode voorkomen of toestaan dat zowel het indrukken van de <kbd>ESC</kbd> toets als klikken buiten de `Dialog` de component sluit.

<ComponentDemo
path='/webforj/dialogclose'
files={['src/main/java/com/webforj/samples/views/dialog/DialogCloseView.java']}
height='350px'
/>

## Auto-focus {#auto-focus}

Wanneer ingeschakeld, zal auto-focus automatisch de focus geven aan het eerste element binnen de dialoog dat gefocust kan worden. Dit is nuttig om de aandacht van gebruikers te richten en is aanpasbaar via de `setAutoFocus()` methode.

<ComponentDemo
path='/webforj/dialogautofocus'
files={['src/main/java/com/webforj/samples/views/dialog/DialogAutoFocusView.java']}
height='350px'
/>

## Versleepbaar {#draggable}

De `Dialog` heeft ingebouwde functionaliteit om versleepbaar te zijn, waardoor de gebruiker het `Dialog` venster kan verplaatsen door te klikken en te slepen. De positie van de `Dialog` kan worden aangepast vanuit elk van de velden erin: de koptekst, inhoud of voettekst.

### Vastklikken aan de rand {#snap-to-edge}
Het is ook mogelijk om dit gedrag te calibreren om aan de rand van het scherm vast te klikken, wat betekent dat de `Dialog` automatisch zich uitlijnt met de rand van het apparaat wanneer deze wordt losgelaten na het slepen en laten vallen. Vastklikken kan worden gewijzigd via de `setSnapToEdge()` methode. De `setSnapThreshold()` neemt een aantal pixels, dat instelt hoe ver de `Dialog` van de zijden van het scherm moet zijn voordat deze automatisch aan de randen vastklikt.

<ComponentDemo
path='/webforj/dialogdraggable'
files={['src/main/java/com/webforj/samples/views/dialog/DialogDraggableView.java']}
height='350px'
/>

## Positionering {#positioning}

De positie van de dialoog kan worden aangepast met behulp van de ingebouwde `setPosx()` en `setPosy()` methoden. Deze methoden nemen een stringargument dat elke toepasbare CSS-eenheid van lengte kan vertegenwoordigen, zoals pixels of zicht hoogte/breedte. Een lijst van deze metingen [is te vinden op deze link](https://developer.mozilla.org/en-US/docs/Learn/CSS/Building_blocks/Values_and_units#numbers_lengths_and_percentages).

<ComponentDemo
path='/webforj/dialogpositioning'
files={['src/main/java/com/webforj/samples/views/dialog/DialogPositioningView.java']}
height='350px'
/>

### Verticale uitlijning {#vertical-alignment}

Naast handmatige toewijzing van de X- en Y-positie van een dialoog, is het ook mogelijk om de ingebouwde enum-klasse van de dialoog te gebruiken om de `Dialog` uit te lijnen. Er zijn drie mogelijke waarden, `TOP`, `CENTER` en `BOTTOM`, die elk kunnen worden gebruikt met de `setAlignment()` methode.

<ComponentDemo
path='/webforj/dialogalignments'
files={['src/main/java/com/webforj/samples/views/dialog/DialogAlignmentsView.java']}
height='550px'
/>

### Volledig scherm en breekpunten {#full-screen-and-breakpoints}

De `Dialog` kan worden ingesteld om de volledige schermmodus in te schakelen. Wanneer de volledige schermmodus is ingeschakeld, kan de `Dialog` niet worden verplaatst of gepositioneerd. Deze modus kan worden aangepast met het breekpunt attribuut van de `Dialog`. Het breekpunt is een mediaquery die bepaalt wanneer de `Dialog` automatisch naar de volledige schermmodus overschakelt. Wanneer de query overeenkomt, verandert de `Dialog` naar volledig scherm - anders is het gepositioneerd.

### Auto breedte <DocChip chip='since' label='26.00' /> {#auto-width}

Standaard strekt de `Dialog` zich uit om de beschikbare horizontale ruimte in te vullen. Wanneer de auto breedte is ingeschakeld via `setAutoWidth(true)`, past de `Dialog` zijn grootte aan op basis van de breedte van de inhoud.

<ComponentDemo
path='/webforj/dialogautowidth'
files={['src/main/java/com/webforj/samples/views/dialog/DialogAutoWidthView.java']}
height='350px'
/>

## Stijling {#styling}

### Thema's {#themes}

`Dialog` componenten worden geleverd met <JavadocLink type="foundation" location="com/webforj/component/dialog/Dialog.Theme.html">7 discrete thema's</JavadocLink> ingebouwd voor snelle stijling zonder het gebruik van CSS. Deze thema's zijn vooraf gedefinieerde stijlen die kunnen worden toegepast op knoppen om hun uiterlijk en visuele presentatie te veranderen. Ze bieden een snelle en consistente manier om het uiterlijk van knoppen in een applicatie aan te passen.

Hoewel er veel gebruiksgevallen zijn voor elk van de verschillende thema's, zijn hier enkele voorbeelden:

  - **Danger**: Acties met ernstige gevolgen, zoals het wissen van ingevulde informatie of het permanent verwijderen van een account/gegevens is een goed gebruik voor dialoogvensters met het Danger-thema.
  - **Default**: Het standaardthema is geschikt voor acties binnen een applicatie die geen speciale aandacht vereisen en die algemeen van aard zijn, zoals het schakelen van een instelling.
  - **Primary**: Dit thema is geschikt als een hoofd "call-to-action" op een pagina, zoals inschrijven, wijzigingen opslaan of doorgaan naar een andere pagina.
  - **Success**: Succes-thema dialoogvensters zijn uitstekend voor het visualiseren van succesvolle voltooiing van een element in een applicatie, zoals de indiening van een formulier of de voltooiing van een inschrijfproces. Het succes-thema kan programmatic worden toegepast zodra een succesvolle actie is voltooid.
  - **Warning**: Waarschuwing dialoogvensters zijn nuttig om gebruikers erop te wijzen dat ze een potentieel risicovolle actie gaan uitvoeren, zoals wanneer ze een pagina met onopgeslagen wijzigingen verlaten. Deze acties zijn vaak minder impactvol dan die welke het Danger-thema zouden gebruiken.
  - **Gray**: Goed voor subtiele acties, zoals kleine instellingen of acties die meer aanvullend zijn voor een pagina en niet deel uitmaken van de hoofd functionaliteit.
  - **Info**: Het Info-thema is een goede keuze om verduidelijkende, aanvullende informatie aan een gebruiker te geven wanneer dat nodig is.

<ComponentDemo
path='/webforj/dialogthemes'
files={['src/main/java/com/webforj/samples/views/dialog/DialogThemesView.java']}
height='500px'
/>

<TableBuilder name="Dialog" />
