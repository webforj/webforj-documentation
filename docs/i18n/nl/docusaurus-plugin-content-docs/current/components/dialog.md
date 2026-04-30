---
title: Dialog
sidebar_position: 30
sidebar_class_name: new-content
_i18n_hash: 621dc045e979c7513b41ef04c6cd242a
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-dialog" />
<DocChip chip='since' label='23.06' />
<JavadocLink type="dialog" location="com/webforj/component/dialog/Dialog" top='true'/>

De `Dialog` component toont een pop-upvenster dat over de huidige weergave heen ligt en de aandacht vestigt op de gefocuste inhoud zoals formulieren, bevestigingen of informatieve berichten.

<!-- INTRO_END -->

## `Dialog` structuur {#dialog-structure}

De `Dialog` is georganiseerd in drie secties: een kop, een inhoudsgebied en een voettekst. Componenten kunnen aan elke sectie worden toegevoegd met behulp van `addToHeader()`, `addToContent()` en `addToFooter()`.

<ComponentDemo 
path='/webforj/dialogsections?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/dialog/DialogSectionsView.java'
height = '225px'
/>

## Toepassingen {#usages}

1. **Gebruikersfeedback en Bevestiging**: `Dialog` componenten worden vaak gebruikt om feedback te geven of om de gebruiker om bevestiging te vragen. Ze kunnen verschillende belangrijke feedback aan een gebruiker tonen, zoals:

  >- Succesberichten 
  >- Foutmeldingen
  >- Bevestiging van indieningen

2. **Formulierinvoer en Bewerking**: Je kunt dialogen gebruiken om gebruikersinvoer te verzamelen of ze in staat te stellen informatie op een gecontroleerde en gefocuste manier te bewerken. Bijvoorbeeld, een dialoog kan verschijnen om de gebruikersprofielgegevens te bewerken of om een meerstapsformulier te voltooien.

3. **Contextuele Informatie**: Het weergeven van aanvullende contextuele informatie of tooltips in een dialoog kan gebruikers helpen complexe functies of gegevens te begrijpen. Dialogen kunnen diepgaande uitleg, grafieken of helpdocumentatie bieden.

4. **Afbeelding en Media Voorbeelden**: Wanneer gebruikers media-inhoud moeten bekijken, kan een `Dialog` worden gebruikt om grotere voorbeelden of galerijen te tonen, zoals bij interactie met:
  >- Afbeeldingen
  >- Video's
  >- Andere media

## Achtergrond en vervaging {#backdrop-and-blur}

Door de achtergrond-attribuut van de webforJ `Dialog` component in te schakelen, wordt er een achtergrond achter de `Dialog` weergegeven. Bovendien, wanneer ingeschakeld, zal het vervagingsattribuut van de Dialog de achtergrond van de `Dialog` vervagen. Het wijzigen van deze instellingen kan gebruikers helpen door diepten, visuele hiërarchie en context te bieden, wat leidt tot duidelijkere richtlijnen voor de gebruiker.

<ComponentDemo 
path='/webforj/dialogbackdropblur?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/dialog/DialogBackdropBlurView.java'
height = '300px'
/>

## Het openen en sluiten van de `Dialog` {#opening-and-closing-the-dialog}

Na het maken van een nieuw `Dialog` object, gebruik je de `open()` methode om de dialoog weer te geven. Vervolgens kan de `Dialog` component sluiten door een van deze acties:
- Gebruik maken van de `close()` methode
- Drukken op de <kbd>ESC</kbd> toets
- Klikken buiten de `Dialog`

Ontwikkelaars kunnen kiezen welke interacties de `Dialog` sluiten met `setCancelOnEscKey()` en `setCancelOnOutsideClick()`. Bovendien kan de `setClosable()` methode voorkomen of toestaan dat zowel het indrukken van de <kbd>ESC</kbd> toets als het klikken buiten de `Dialog` de component sluit.

<ComponentDemo 
path='/webforj/dialogclose?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/dialog/DialogCloseView.java'
height = '350px'
/>

## Auto-focus {#auto-focus}

Wanneer ingeschakeld, zal auto-focus automatisch focus geven aan het eerste element binnen de dialoog dat gefocust kan worden. Dit is nuttig om de aandacht van gebruikers te richten en is aanpasbaar via de `setAutoFocus()` methode.

<ComponentDemo 
path='/webforj/dialogautofocus?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/dialog/DialogAutoFocusView.java'
height = '350px'
/>

## Versleepbaar {#draggable}

De `Dialog` heeft ingebouwde functionaliteit om versleepbaar te zijn, zodat de gebruiker het `Dialog` venster kan verplaatsen door te klikken en te slepen. De positie van de `Dialog` kan worden aangepast vanuit een van de velden erin: de kop, inhoud of voettekst.

### Vastklikken aan de rand {#snap-to-edge}
Het is ook mogelijk om dit gedrag te calibreren zodat het vastklikt aan de rand van het scherm, wat betekent dat de `Dialog` zichzelf automatisch aligneert met de rand van het display wanneer het wordt losgelaten vanuit zijn slepen-en-neerzetten toestand. Vastklikken kan worden gewijzigd via de `setSnapToEdge()` methode. De `setSnapThreshold()` neemt een aantal pixels, waarmee wordt ingesteld hoe ver de `Dialog` van de zijkanten van het scherm moet zijn voordat deze automatisch aan de randen vastklikt.

<ComponentDemo 
path='/webforj/dialogdraggable?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/dialog/DialogDraggableView.java'
height = '350px'
/>

## Positionering {#positioning}

De positie van de dialoog kan worden aangepast met behulp van de ingebouwde `setPosx()` en `setPosy()` methoden. Deze methoden nemen een stringargument dat elke toepasselijke CSS-eenheid van lengte kan vertegenwoordigen, zoals pixels of weergavehoogte/-breedte. Een lijst van deze metingen [is te vinden via deze link](https://developer.mozilla.org/en-US/docs/Learn/CSS/Building_blocks/Values_and_units#numbers_lengths_and_percentages).

<ComponentDemo 
path='/webforj/dialogpositioning?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/dialog/DialogPositioningView.java'
height = '350px'
/>

### Verticale uitlijning {#vertical-alignment}

Naast de handmatige toewijzing van de X- en Y-positie van een dialoog, is het mogelijk om de ingebouwde enum-klasse van de dialoog te gebruiken om de `Dialog` uit te lijnen. Er zijn drie mogelijke waarden, `TOP`, `CENTER` en `BOTTOM`, die elk kunnen worden gebruikt met de `setAlignment()` methode.

<ComponentDemo 
path='/webforj/dialogalignments?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/dialog/DialogAlignmentsView.java'
height = '550px'
/>

### Volledig scherm en breakpoints {#full-screen-and-breakpoints}

De `Dialog` kan worden ingesteld om de volledig scherm modus te activeren. Wanneer volledig scherm is ingeschakeld, kan de `Dialog` niet worden verplaatst of gepositioneerd. Deze modus kan worden aangepast met het breakpoint-attribuut van de `Dialog`. Het breakpoint is een mediaquery die bepaalt wanneer de `Dialog` automatisch overgaat naar de volledig scherm modus. Wanneer de query overeenkomt, verandert de `Dialog` naar volledig scherm - anders is het gepositioneerd.

### Auto breedte <DocChip chip='since' label='26.00' /> {#auto-width}

Standaard strekt de `Dialog` zich uit om de beschikbare horizontale ruimte in te nemen. Wanneer de auto breedte is ingeschakeld via `setAutoWidth(true)`, past de `Dialog` zichzelf aan op basis van de breedte van de inhoud.

<ComponentDemo 
path='/webforj/dialogautowidth?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/dialog/DialogAutoWidthView.java'
height = '350px'
/>

## Stijling {#styling}

### Thema's {#themes}

`Dialog` componenten worden geleverd met <JavadocLink type="foundation" location="com/webforj/component/dialog/Dialog.Theme.html">7 discrete thema's</JavadocLink> die ingebouwd zijn voor snelle styling zonder het gebruik van CSS. Deze thema's zijn vooraf gedefinieerde stijlen die op knoppen kunnen worden toegepast om hun uiterlijk en visuele presentatie te veranderen. Ze bieden een snelle en consistente manier om de uitstraling van knoppen in een applicatie aan te passen.

Hoewel er veel gebruiksmogelijkheden zijn voor elk van de verschillende thema's, zijn hier enkele voorbeelden:

  - **Gevaren**: Acties met ernstige gevolgen, zoals het wissen van ingevulde informatie of het permanent verwijderen van een account/gegevens, vormen een goede gebruiksmogelijkheid voor dialogen met het Gevaren-thema.
  - **Standaard**: Het standaardthema is geschikt voor acties binnen een applicatie die geen speciale aandacht vereisen en die algemeen zijn, zoals het in- of uitschakelen van een instelling.
  - **Primaire**: Dit thema is geschikt als de belangrijkste "call-to-action" op een pagina, zoals inschrijven, wijzigingen opslaan of doorgaan naar een andere pagina.
  - **Succes**: Succes-thema dialogen zijn uitstekend voor het visualiseren van succesvolle voltooiing van een element in een applicatie, zoals het indienen van een formulier of het voltooien van een aanmeldproces. Het succes-thema kan programmatisch worden toegepast zodra een succesvolle actie is voltooid.
  - **Waarschuwing**: Waarschuwingsdialogen zijn nuttig om gebruikers aan te geven dat ze op het punt staan een potentieel risicovolle actie uit te voeren, zoals wanneer ze een pagina verlaten met niet-opgeslagen wijzigingen. Deze acties zijn vaak minder impactvol dan die welke het Gevaren-thema zouden gebruiken.
  - **Grijs**: Goed voor subtiele acties, zoals kleine instellingen of acties die meer aanvullend zijn voor een pagina en geen deel uitmaken van de belangrijkste functionaliteit.
  - **Informatie**: Het Informatie-thema is een goede keuze om verduidelijkende, aanvullende informatie aan een gebruiker te bieden wanneer dat nodig is.

<ComponentDemo 
path='/webforj/dialogthemes?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/dialog/DialogThemesView.java'
height = '500px'
/>

<TableBuilder name="Dialog" />
