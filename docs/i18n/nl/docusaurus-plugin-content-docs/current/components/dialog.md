---
title: Dialog
sidebar_position: 30
_i18n_hash: e0d440fddf7ad6be7a78958ae1ddde1a
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-dialog" />
<DocChip chip='since' label='23.06' />
<JavadocLink type="dialog" location="com/webforj/component/dialog/Dialog" top='true'/>

De webforJ dialoogcomponent is ontworpen om een ontwikkelaar in staat te stellen snel en gemakkelijk een dialoog in hun applicatie weer te geven, bijvoorbeeld voor een inlogmenu of informatieve venster.

De component is opgebouwd uit drie secties, die elk `Panel`-componenten zijn: de **kop**, de **inhoud** en de **voettekst**.

<ComponentDemo 
path='/webforj/dialogsections?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/dialog/DialogSectionsView.java'
height = '225px'
/>

## Gebruik {#usages}

1. **Gebruikersfeedback en bevestiging**: `Dialog`-componenten worden vaak gebruikt om feedback te geven of om bevestiging van de gebruiker te vragen. Ze kunnen verschillende belangrijke feedback aan een gebruiker tonen, zoals:

  >- Succesberichten 
  >- Foutmeldingen
  >- Bevestiging van inzendingen

2. **Formulierinvoer en bewerking**: Je kunt dialogen gebruiken om gebruikersinvoer te verzamelen of hen in staat te stellen informatie op een gecontroleerde en gefocuste manier te bewerken. Bijvoorbeeld, een dialoog kan verschijnen om de details van het gebruikersprofiel te bewerken of om een meerfasenformulier te voltooien.

3. **Contextuele informatie**: Het weergeven van aanvullende contextuele informatie of tips in een dialoog kan gebruikers helpen complexe functies of gegevens te begrijpen. Dialogen kunnen diepgaande uitleg, grafieken of helpdocumentatie bieden.

4. **Afbeelding- en mediavoorbeelden**: Wanneer gebruikers media moeten bekijken, kan een `Dialog` worden gebruikt om grotere voorbeelden of galerijen weer te geven, bijvoorbeeld bij interactie met:
  >- Afbeeldingen
  >- Video's
  >- Andere media

## Achtergrond en vervaging {#backdrop-and-blur}

Door de achtergrondattributen van de webforJ `Dialog`-component in te schakelen, wordt er een achtergrond weergegeven achter de `Dialog`. Daarnaast zal, wanneer ingeschakeld, de vervagingseigenschap van de Dialog de achtergrond van de `Dialog` vervagen. Het aanpassen van deze instellingen kan gebruikers helpen door diepte, visuele hiërarchie en context te bieden, wat leidt tot duidelijkere begeleiding voor de gebruiker.

<ComponentDemo 
path='/webforj/dialogbackdropblur?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/dialog/DialogBackdropBlurView.java'
height = '300px'
/>

## Het openen en sluiten van de `Dialog` {#opening-and-closing-the-dialog}

Nadat je een nieuw `Dialog`-object hebt gemaakt, gebruik je de `open()`-methode om de dialoog weer te geven. Vervolgens kan de `Dialog`-component sluiten door een van deze acties:
- De `close()`-methode te gebruiken
- De <kbd>ESC</kbd>-toets in te drukken
- Buiten de `Dialog` te klikken

Ontwikkelaars kunnen kiezen welke interacties de `Dialog` sluiten met `setCancelOnEscKey()` en `setCancelOnOutsideClick()`. Daarnaast kan de `setClosable()`-methode voorkomen of toestaan dat zowel het indrukken van de <kbd>ESC</kbd>-toets als het klikken buiten de `Dialog` de component sluit.

<ComponentDemo 
path='/webforj/dialogclose?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/dialog/DialogCloseView.java'
height = '350px'
/>

## Auto-focus {#auto-focus}

Wanneer ingeschakeld, zal de auto-focus automatisch focus geven aan het eerste element binnen de dialoog dat gefocust kan worden. Dit is nuttig om de aandacht van gebruikers te richten en kan worden aangepast via de `setAutoFocus()`-methode.

<ComponentDemo 
path='/webforj/dialogautofocus?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/dialog/DialogAutoFocusView.java'
height = '350px'
/>

## Versleepbaar {#draggable}

De `Dialog` heeft ingebouwde functionaliteit om verplaatsbaar te zijn, waardoor de gebruiker het `Dialog`-venster kan verplaatsen door te klikken en te slepen. De positie van de `Dialog` kan worden aangepast vanuit een van de velden erin: de kop, inhoud of voettekst.

### Kleven aan de rand {#snap-to-edge}
Het is ook mogelijk om dit gedrag te kalibreren zodat het aan de rand van het scherm kleeft, wat betekent dat de `Dialog` automatisch zal uitlijnen met de rand van het display wanneer deze wordt losgelaten van het slepen. Het kleven kan worden gewijzigd via de `setSnapToEdge()`-methode. De `setSnapThreshold()` accepteert een aantal pixels, dat bepaalt hoe ver de `Dialog` van de zijkanten van het scherm moet zijn voordat deze automatisch aan de randen kleeft.  

<ComponentDemo 
path='/webforj/dialogdraggable?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/dialog/DialogDraggableView.java'
height = '350px'
/>

## Positionering {#positioning}

De positie van de dialoog kan worden aangepast met behulp van de ingebouwde `setPosx()`- en `setPosy()`-methoden. Deze methoden accepteren een stringargument dat elke toepasselijke CSS-eenheid van lengte kan vertegenwoordigen, zoals pixels of weergavehoogte/-breedte. Een lijst van deze metingen [kan op deze link worden gevonden](https://developer.mozilla.org/en-US/docs/Learn/CSS/Building_blocks/Values_and_units#numbers_lengths_and_percentages).

<ComponentDemo 
path='/webforj/dialogpositioning?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/dialog/DialogPositioningView.java'
height = '350px'
/>

### Verticale uitlijning {#vertical-alignment}

Naast handmatige toewijzing van de X- en Y-positie van een dialoog, is het mogelijk om de ingebouwde enum-klasse van de dialoog te gebruiken om de `Dialog` uit te lijnen. Er zijn drie mogelijke waarden: `TOP`, `CENTER` en `BOTTOM`, die allemaal met de `setAlignment()`-methode kunnen worden gebruikt. 

<ComponentDemo 
path='/webforj/dialogalignments?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/dialog/DialogAlignmentsView.java'
height = '550px'
/>

### Volledig scherm en breekpunten {#full-screen-and-breakpoints}

De `Dialog` kan worden ingesteld om de volledige schermmodus in te gaan. Wanneer de volledig schermmodus is ingeschakeld, kan de `Dialog` niet worden verplaatst of gepositioneerd. Deze modus kan worden aangepast met het breekpuntattribuut van de `Dialog`. Het breekpunt is een mediaquery die aangeeft wanneer de `Dialog` automatisch naar de volledig schermmodus zal overschakelen. Wanneer de query overeenkomt, verandert de `Dialog` in volledig scherm - anders is deze gepositioneerd.

## Stijl {#styling}

### Thema's {#themes}

`Dialog`-componenten worden geleverd met <JavadocLink type="foundation" location="com/webforj/component/dialog/Dialog.Theme.html">7 afzonderlijke thema's </JavadocLink> die zijn ingebouwd voor snelle styling zonder het gebruik van CSS. Deze thema's zijn vooraf gedefinieerde stijlen die op knoppen kunnen worden toegepast om hun uiterlijk en visuele presentatie te veranderen. Ze bieden een snelle en consistente manier om de uitstraling van knoppen in een applicatie aan te passen.

Hoewel er veel gebruiksgevallen zijn voor elk van de verschillende thema's, zijn enkele voorbeelden van gebruik:

  - **Danger**: Acties met ernstige consequenties, zoals het wissen van ingevulde informatie of het permanent verwijderen van een account/gegevens, zijn een goed gebruiksgeval voor dialogen met het Danger-thema.
  - **Default**: Het standaardthema is geschikt voor acties in een applicatie die geen speciale aandacht vereisen en die generiek zijn, zoals het wijzigen van een instelling.
  - **Primary**: Dit thema is geschikt als een belangrijkste "call-to-action" op een pagina, zoals inschrijven, wijzigingen opslaan of naar een andere pagina gaan.
  - **Success**: Success-thema dialogen zijn uitstekend voor het visualiseren van de succesvolle voltooiing van een element in een applicatie, zoals de indiening van een formulier of de voltooiing van een aanmeldproces. Het succes-thema kan programmadig worden toegepast zodra een succesvolle actie is voltooid.
  - **Warning**: Waarschuwingsdialogen zijn nuttig om gebruikers te wijzen op een mogelijk risicovolle actie, zoals wanneer ze zich van een pagina met onopgeslagen wijzigingen verwijderen. Deze acties zijn vaak minder ingrijpend dan die welke het Danger-thema zouden gebruiken.
  - **Gray**: Goed voor subtiele acties, zoals kleine instellingen of acties die meer aanvullend zijn voor een pagina en niet deel uitmaken van de belangrijkste functionaliteit.
  - **Info**: Het Info-thema is een goede keuze om verduidelijkende, aanvullende informatie aan een gebruiker te bieden wanneer dat nodig is.

<ComponentDemo 
path='/webforj/dialogthemes?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/dialog/DialogThemesView.java'
height = '500px'
/>

<TableBuilder name="Dialog" />
