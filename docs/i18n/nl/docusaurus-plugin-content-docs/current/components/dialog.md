---
title: Dialog
sidebar_position: 30
_i18n_hash: d0087974ac244db9b082133be7966a3e
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-dialog" />
<DocChip chip='since' label='23.06' />
<JavadocLink type="dialog" location="com/webforj/component/dialog/Dialog" top='true'/>

De webforJ dialoogcomponent is ontworpen om een ontwikkelaar in staat te stellen snel en eenvoudig een dialoog op hun applicatie weer te geven, bijvoorbeeld voor een inlogmenu of informatiebox.

De component is opgebouwd uit drie secties, die elk `Panel` componenten zijn: de **header**, de **inhoud**, en de **footer**.

<ComponentDemo 
path='/webforj/dialogsections?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/dialog/DialogSectionsView.java'
height = '225px'
/>

## Gebruik {#usages}

1. **Feedback en bevestiging van de gebruiker**: `Dialoog` componenten worden vaak gebruikt om feedback te geven of om bevestiging van de gebruiker te vragen. Ze kunnen verschillende belangrijke feedback aan een gebruiker tonen, zoals:

  >- Succesberichten 
  >- Foutmeldingen
  >- Bevestiging van indieningen

2. **Formulierinvoer en bewerking**: Je kunt dialoogvensters gebruiken om gebruikersinvoer te verzamelen of hen in staat te stellen informatie op een gecontroleerde en gerichte manier te bewerken. Bijvoorbeeld, een dialoog kan verschijnen om gebruikersprofielgegevens te bewerken of om een meerstapsformulierveld te voltooien.

3. **Contextuele informatie**: Het tonen van aanvullende contextuele informatie of tooltips in een dialoog kan gebruikers helpen complexe functies of gegevens te begrijpen. Dialoogvensters kunnen diepgaande uitleg, grafieken of hulpdocumentatie bieden.

4. **Afbeeldingen en mediapreviews**: Wanneer gebruikers media moeten bekijken, kan een `Dialoog` worden gebruikt om grotere voorvertoningen of galerijen te tonen, zoals wanneer interactie met:
  >- Afbeeldingen
  >- Video's
  >- Andere media

## Achtergrond en vervaging {#backdrop-and-blur}

Door de achtergrondattribuut van de webforJ `Dialoog` component in te schakelen, wordt er een achtergrond weergegeven achter de `Dialoog`. Bovendien, wanneer ingeschakeld, zal de vervaagde attribuut van de Dialoog de achtergrond van de `Dialoog` vervagen. Het wijzigen van deze instellingen kan gebruikers helpen door dieptes, visuele hiërarchie en context te bieden, wat leidt tot meer duidelijke begeleiding voor een gebruiker.

<ComponentDemo 
path='/webforj/dialogbackdropblur?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/dialog/DialogBackdropBlurView.java'
height = '300px'
/>

## Het openen en sluiten van de `Dialoog` {#opening-and-closing-the-dialog}

Na het aanmaken van een nieuw `Dialoog` object, gebruik je de `open()` methode om de dialoog weer te geven. Vervolgens kan de `Dialoog` component sluiten door een van deze acties:
- Gebruik van de `close()` methode
- Drukken op de <kbd>ESC</kbd> toets
- Klikken buiten de `Dialoog`

Ontwikkelaars kunnen kiezen welke interacties de `Dialoog` sluiten met `setCancelOnEscKey()` en `setCancelOnOutsideClick()`. Bovendien kan de `setClosable()` methode voorkomen dat of toestemming geven dat zowel het drukken op de <kbd>ESC</kbd> toets als klikken buiten de `Dialoog` de component sluit.

<ComponentDemo 
path='/webforj/dialogclose?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/dialog/DialogCloseView.java'
height = '350px'
/>

## Automatische focus {#auto-focus}

Wanneer ingeschakeld, geeft automatische focus automatisch focus aan het eerste element binnen de dialoog dat gefocust kan worden. Dit is nuttig om de aandacht van gebruikers te sturen en is aanpasbaar via de `setAutoFocus()` methode.

<ComponentDemo 
path='/webforj/dialogautofocus?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/dialog/DialogAutoFocusView.java'
height = '350px'
/>

## Slepen {#draggable}

De `Dialoog` heeft ingebouwde functionaliteit om slepend te zijn, waardoor de gebruiker het `Dialoog` venster kan verplaatsen door te klikken en te slepen. De positie van de `Dialoog` kan worden gemanipuleerd vanaf een van de velden binnenin: de header, inhoud of footer.

### Vastklikken aan de rand {#snap-to-edge}
Het is ook mogelijk om dit gedrag te kalibreren om vast te klikken aan de rand van het scherm, wat betekent dat de `Dialoog` zichzelf automatisch uitlijnt met de rand van het display wanneer het wordt losgelaten vanuit zijn sleep- en laatdatum. Vastklikken kan worden gewijzigd via de `setSnapToEdge()` methode. De `setSnapThreshold()` neemt een aantal pixels, waarmee wordt ingesteld hoe ver de `Dialoog` van de zijkanten van het scherm moet zijn voordat deze automatisch aan de randen vastklikt.

<ComponentDemo 
path='/webforj/dialogdraggable?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/dialog/DialogDraggableView.java'
height = '350px'
/>

## Positionering {#positioning}

De positie van de dialoog kan worden gemanipuleerd met behulp van de ingebouwde `setPosx()` en `setPosy()` methoden. Deze methoden nemen een stringargument dat elke toepasbare CSS-eenheid van lengte kan vertegenwoordigen, zoals pixels of weergavehoogte/-breedte. Een lijst van deze metingen [is te vinden via deze link](https://developer.mozilla.org/en-US/docs/Learn/CSS/Building_blocks/Values_and_units#numbers_lengths_and_percentages).

<ComponentDemo 
path='/webforj/dialogpositioning?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/dialog/DialogPositioningView.java'
height = '350px'
/>

### Verticale uitlijning {#vertical-alignment}

Naast het handmatig toewijzen van een dialoog's X- en Y-positie, is het ook mogelijk om de ingebouwde enum-klasse van de dialoog te gebruiken om de `Dialoog` uit te lijnen. Er zijn drie mogelijke waarden, `TOP`, `CENTER` en `BOTTOM`, die elk kunnen worden gebruikt met de `setAlignment()` methode. 

<ComponentDemo 
path='/webforj/dialogalignments?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/dialog/DialogAlignmentsView.java'
height = '550px'
/>

### Volledig scherm en breekpunten {#full-screen-and-breakpoints}

De `Dialoog` kan worden ingesteld om de volledige schermmodus in te voeren. Wanneer de volledige schermmodus is ingeschakeld, kan de `Dialoog` niet worden verplaatst of gepositioneerd. Deze modus kan worden gemanipuleerd met behulp van het breekpuntattribuut van de `Dialoog`. Het breekpunt is een mediaquery die bepaalt wanneer de `Dialoog` automatisch naar de volledige schermmodus zal overschakelen. Wanneer de query overeenkomt, verandert de `Dialoog` naar volledig scherm - anders is deze gepositioneerd.

## Stijling {#styling}

### Thema's {#themes}

`Dialoog` componenten komen met <JavadocLink type="foundation" location="com/webforj/component/dialog/Dialog.Theme.html">7 discrete thema's </JavadocLink> ingebouwd voor snelle stijling zonder gebruik van CSS. Deze thema's zijn vooraf gedefinieerde stijlen die kunnen worden toegepast op knoppen om hun uiterlijk en visuele presentatie te veranderen. Ze bieden een snelle en consistente manier om de uitstraling van knoppen in een applicatie aan te passen. 

Hoewel er veel toepassingen zijn voor elk van de verschillende thema's, zijn hier enkele voorbeelden:

  - **Gevaren**: Acties met ernstige gevolgen, zoals het wissen van ingevulde informatie of het permanent verwijderen van een account/gegevens, vertegenwoordigt een goede toepassing voor dialoogvensters met het Gevaren-thema.
  - **Standaard**: Het standaardthema is geschikt voor acties in een applicatie die geen speciale aandacht vereisen en die algemeen zijn, zoals het in- of uitschakelen van een instelling.
  - **Primair**: Dit thema is geschikt als een belangrijke "oproep tot actie" op een pagina, zoals aanmelden, wijzigingen opslaan of doorgaan naar een andere pagina.
  - **Succes**: Dialoogvensters met het Succes-thema zijn uitstekend voor het visualiseren van succesvolle voltooiing van een element in een applicatie, zoals het indienen van een formulier of voltooiing van een aanmeldingsproces. Het succes-thema kan programmatisch worden toegepast zodra een succesvolle actie is voltooid.
  - **Waarschuwing**: Waarschuwingsdialoogvensters zijn nuttig om gebruikers te indiceren dat ze op het punt staan een potentieel risicovolle actie uit te voeren, zoals wanneer ze een pagina met onopgeslagen wijzigingen verlaten. Deze acties zijn vaak minder impactvol dan die welke het Gevaren-thema zouden gebruiken.
  - **Grijs**: Goed voor subtiele acties, zoals kleine instellingen of acties die meer aanvullend zijn voor een pagina, en niet deel uitmaken van de belangrijkste functionaliteit.
  - **Info**: Het Info-thema is een goede keuze om aanvullende informatie te verstrekken aan een gebruiker wanneer deze is aangesproken.

<ComponentDemo 
path='/webforj/dialogthemes?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/dialog/DialogThemesView.java'
height = '500px'
/>

<TableBuilder name="Dialog" />
