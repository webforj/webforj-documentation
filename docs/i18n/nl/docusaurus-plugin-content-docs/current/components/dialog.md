---
title: Dialog
sidebar_position: 30
sidebar_class_name: new-content
_i18n_hash: 750f3d1f7c1c905274eac22a90b270de
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-dialog" />
<DocChip chip='since' label='23.06' />
<JavadocLink type="dialog" location="com/webforj/component/dialog/Dialog" top='true'/>

De `Dialog` component toont een popupvenster dat de huidige weergave overschaduwt en de aandacht vestigt op gefocuste inhoud zoals formulieren, bevestigingen of informatieve berichten.

<!-- INTRO_END -->

## `Dialog` structuur {#dialog-structure}

De `Dialog` is georganiseerd in drie secties: een kop, een inhoudsgebied en een voettekst. Componenten kunnen aan elke sectie worden toegevoegd met `addToHeader()`, `addToContent()`, en `addToFooter()`.

<ComponentDemo
path='/webforj/dialogsections'
files={['src/main/java/com/webforj/samples/views/dialog/DialogSectionsView.java']}
height='225px'
/>

## Gebruik {#usages}

1. **Gebruikersfeedback en bevestiging**: `Dialog` componenten worden vaak gebruikt om feedback te geven of om bevestiging van de gebruiker te vragen. Ze kunnen verschillende belangrijke feedback aan een gebruiker tonen, zoals:

  >- Succesboodschappen 
  >- Foutmeldingen
  >- Bevestigen van indienen

2. **Formulierveld en bewerken**: Je kunt dialogen gebruiken om gebruikersinvoer te verzamelen of hen in staat te stellen informatie op een gecontroleerde en gerichte manier te bewerken. Bijvoorbeeld, een dialoog kan opkomen om de profielgegevens van een gebruiker te bewerken of een meertraps formulier te voltooien.

3. **Contextuele informatie**: Het tonen van aanvullende contextuele informatie of tooltip-informatie in een dialoog kan gebruikers helpen complexe functies of gegevens te begrijpen. Dialogen kunnen diepgaande uitleg, grafieken of helpdocumentatie bieden.

4. **Afbeelding- en mediavisualisaties**: Wanneer gebruikers stukken media moeten bekijken, kan een `Dialog` worden gebruikt om grotere voorvertoningen of galerijen te tonen, zoals wanneer zij interageren met:
  >- Afbeeldingen
  >- Video's
  >- Andere media

## Achtergrond en vervaging {#backdrop-and-blur}

Door de achtergrond-attribuut van de webforJ `Dialog` component in te schakelen, wordt er een achtergrond achter de `Dialog` weergegeven. Bovendien, wanneer ingeschakeld, zal de vervaagde eigenschap van de Dialog de achtergrond van de `Dialog` vervagen. Het aanpassen van deze instellingen kan gebruikers helpen door diepte, visuele hiërarchie en context te bieden, wat leidt tot duidelijkere begeleiding voor een gebruiker.

<ComponentDemo
path='/webforj/dialogbackdropblur'
files={['src/main/java/com/webforj/samples/views/dialog/DialogBackdropBlurView.java']}
height='300px'
/>

## Het openen en sluiten van de `Dialog` {#opening-and-closing-the-dialog}

Nadat je een nieuw `Dialog` object hebt gemaakt, gebruik je de `open()` methode om de dialoog weer te geven. Vervolgens kan de `Dialog` component sluiten door een van deze acties:
- Gebruik de `close()` methode
- Druk op de <kbd>ESC</kbd> toets
- Klik buiten de `Dialog`

Ontwikkelaars kunnen kiezen welke interacties de `Dialog` sluiten met `setCancelOnEscKey()` en `setCancelOnOutsideClick()`. Bovendien kan de `setClosable()` methode voorkomen of toestaan dat zowel het indrukken van de <kbd>ESC</kbd> toets als het klikken buiten de `Dialog` de component sluiten.

<ComponentDemo
path='/webforj/dialogclose'
files={['src/main/java/com/webforj/samples/views/dialog/DialogCloseView.java']}
height='350px'
/>

## Automatische focus {#auto-focus}

Wanneer ingeschakeld, geeft de automatische focus automatisch focus aan het eerste element binnen de dialoog dat gefocust kan worden. Dit is nuttig om de aandacht van gebruikers te richten en is aanpasbaar via de `setAutoFocus()` methode.

<ComponentDemo
path='/webforj/dialogautofocus'
files={['src/main/java/com/webforj/samples/views/dialog/DialogAutoFocusView.java']}
height='350px'
/>

## Versleepbaar {#draggable}

De `Dialog` heeft ingebouwde functionaliteit om versleepbaar te zijn, waardoor de gebruiker het `Dialog` venster kan verplaatsen door te klikken en te slepen. De positie van de `Dialog` kan worden gemanipuleerd vanuit elk van de velden binnenin: de kop, inhoud of voettekst.

### Aansluiten op de rand {#snap-to-edge}
Het is ook mogelijk om dit gedrag te kalibreren om aan de rand van het scherm aan te sluiten, wat betekent dat de `Dialog` zichzelf automatisch uitlijnt met de rand van het display wanneer deze wordt vrijgegeven van zijn sleepdatum. Aansluiten kan worden gewijzigd via de `setSnapToEdge()` methode. De `setSnapThreshold()` neemt een aantal pixels, die zal instellen hoe ver de `Dialog` van de zijkanten van het scherm moet zijn voordat deze automatisch naar de randen zal aansluiten.

<ComponentDemo
path='/webforj/dialogdraggable'
files={['src/main/java/com/webforj/samples/views/dialog/DialogDraggableView.java']}
height='350px'
/>

## Positionering {#positioning}

De positie van de dialoog kan worden gemanipuleerd met behulp van de ingebouwde `setPosx()` en `setPosy()` methoden. Deze methoden nemen een stringargument dat elke toepasselijke CSS-eenheid van lengte kan vertegenwoordigen, zoals pixels of weergavehoogte/-breedte. Een lijst van deze metingen [is te vinden via deze link](https://developer.mozilla.org/en-US/docs/Learn/CSS/Building_blocks/Values_and_units#numbers_lengths_and_percentages).

<ComponentDemo
path='/webforj/dialogpositioning'
files={['src/main/java/com/webforj/samples/views/dialog/DialogPositioningView.java']}
height='350px'
/>

### Verticale uitlijning {#vertical-alignment}

Naast de handmatige toewijzing van de X- en Y-positie van een dialoog, is het mogelijk om de ingebouwde enum-klasse van de dialoog te gebruiken om de `Dialog` uit te lijnen. Er zijn drie mogelijke waarden, `TOP`, `CENTER` en `BOTTOM`, die allemaal kunnen worden gebruikt met de `setAlignment()` methode.

<ComponentDemo
path='/webforj/dialogalignments'
files={['src/main/java/com/webforj/samples/views/dialog/DialogAlignmentsView.java']}
height='550px'
/>

### Volledig scherm en breekpunten {#full-screen-and-breakpoints}

De `Dialog` kan worden ingesteld om de volledig schermmodus in te schakelen. Wanneer de volledig schermmodus is ingeschakeld, kan de `Dialog` niet worden verplaatst of gepositioneerd. Deze modus kan worden aangepast met het breekpunt-attribuut van de `Dialog`. Het breekpunt is een media-query waarin de `Dialog` automatisch naar de volledig schermmodus zal schakelen. Wanneer de query overeenkomt, verandert de `Dialog` in volledig scherm - anders is hij gepositioneerd.

### Automatische breedte <DocChip chip='since' label='26.00' /> {#auto-width}

Standaard strekt de `Dialog` zich uit om de beschikbare horizontale ruimte in te vullen. Wanneer de automatische breedte is ingeschakeld via `setAutoWidth(true)`, past de `Dialog` zichzelf aan op basis van de breedte van de inhoud.

<ComponentDemo
path='/webforj/dialogautowidth'
files={['src/main/java/com/webforj/samples/views/dialog/DialogAutoWidthView.java']}
height='350px'
/>

## Stijling {#styling}

### Thema's {#themes}

`Dialog` componenten worden geleverd met <JavadocLink type="foundation" location="com/webforj/component/dialog/Dialog.Theme.html">7 discrete thema's</JavadocLink> die zijn ingebouwd voor snelle stijling zonder het gebruik van CSS. Deze thema's zijn vooraf gedefinieerde stijlen die kunnen worden toegepast op knoppen om hun uiterlijk en visuele presentatie te veranderen. Ze bieden een snelle en consistente manier om de uitstraling van knoppen in een applicatie aan te passen.

Hoewel er veel gebruiksmogelijkheden zijn voor elk van de verschillende thema's, zijn hier enkele voorbeelden:

  - **Gevoeligheid**: Acties met ernstige gevolgen, zoals het wissen van ingevulde informatie of het permanent verwijderen van een account/gegevens, vertegenwoordigen een goede gebruiksfunctie voor dialogen met het Gevoeligheid-thema.
  - **Standaard**: Het standaardthema is geschikt voor acties in een applicatie die geen bijzondere aandacht vereisen en die algemeen zijn, zoals het toggelen van een instelling.
  - **Primair**: Dit thema is geschikt als een belangrijkste "oproep tot actie" op een pagina, zoals aanmelden, wijzigingen opslaan of doorgaan naar een andere pagina.
  - **Succes**: Succes-thema dialogen zijn uitstekend voor het visualiseren van succesvolle voltooiing van een element in een applicatie, zoals het indienen van een formulier of het voltooien van een aanmeldproces. Het succes-thema kan programmatisch worden toegepast zodra een succesvolle actie is voltooid.
  - **Waarschuwing**: Waarschuwingsdialogen zijn nuttig om gebruikers te wijzen op een mogelijk risicovolle actie, zoals wanneer ze een pagina met onopgeslagen wijzigingen verlaten. Deze acties zijn vaak minder impactvol dan die welke het Gevoeligheid-thema zouden gebruiken.
  - **Grijs**: Goed voor subtiele acties, zoals kleinere instellingen of acties die meer aanvullend zijn voor een pagina, en geen deel uitmaken van de belangrijkste functionaliteit.
  - **Informatie**: Het Informatie-thema is een goede keuze om verduidelijkende, aanvullende informatie aan een gebruiker te geven wanneer deze wordt gepushed.

<ComponentDemo
path='/webforj/dialogthemes'
files={['src/main/java/com/webforj/samples/views/dialog/DialogThemesView.java']}
height='500px'
/>

<TableBuilder name="Dialog" />
