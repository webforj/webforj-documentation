---
sidebar_position: 5
title: Confirm
_i18n_hash: 1a5d5c10371b3d751853eb3c3bcbe66f
---
# Bevestigingsdialoog

<DocChip chip='shadow' />
<DocChip chip='since' label='24.02' />
<JavadocLink type="foundation" location="com/webforj/component/optiondialog/ConfirmDialog" top='true'/>

Een `ConfirmDialog` is een modale dialoog ontworpen om de gebruiker de mogelijkheid te geven om een van een set van maximaal 3 opties te kiezen. De dialoog blokkeert de uitvoering van de app totdat de gebruiker ermee interacteert of deze sluit vanwege een time-out.

```java
ConfirmDialog.Result result = OptionDialog.showConfirmDialog(
    "Bevestigt u dit?",
    "Bevestiging",
    ConfirmDialog.OptionType.OK_CANCEL,
    ConfirmDialog.MessageType.QUESTION);
```

## Gebruik {#usages}

De `ConfirmDialog` biedt een manier om gebruikers om bevestiging te vragen of om tussen meerdere opties te kiezen, zoals `Ja/Nee` of `OK/Annuleren`, zodat ze hun acties erkennen en bevestigen.

<ComponentDemo 
path='/webforj/confirmdialogconstructor?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/optiondialog/confirm/ConfirmDialogConstructorView.java'
height = '350px'
/>

## Typen {#types}

### Optietype {#option-type}

De `ConfirmDialog` ondersteunt de volgende optietypen, die bepalen welke knoppen in de dialoog worden weergegeven:

1. **`OK`**: Toont een `OK`-knop.
2. **`OK_CANCEL`**: Toont `OK` en `Annuleren` knoppen.
3. **`ABORT_RETRY_IGNORE`**: Toont `Afbreken`, `Opnieuw` en `Negeren` knoppen.
4. **`YES_NO_CANCEL`**: Toont `Ja`, `Nee`, en `Annuleren` knoppen.
5. **`YES_NO`**: Toont `Ja` en `Nee` knoppen.
6. **`RETRY_CANCEL`**: Toont `Opnieuw` en `Annuleren` knoppen.
7. **`CUSTOM`**: Toont aangepaste knoppen zoals gespecificeerd.

### Berichten type {#message-type}

De `ConfirmDialog` ondersteunt de volgende berichttypen. Wanneer u een type configureert, toont de dialoog een pictogram naast het bericht, en het thema van de dialoog wordt bijgewerkt volgens de regels van het webforJ-ontwerp.

1. `PLAIN`: Toont het bericht zonder pictogram, met het standaardthema.
2. `ERROR`: Toont een foutpictogram naast het bericht met het foutthema toegepast.
3. `QUESTION`: Toont een vraagtekenpictogram naast het bericht, met het primaire thema.
4. `WARNING`: Toont een waarschuwingspictogram naast het bericht met het waarschuwings-thema toegepast.
5. `INFO`: Toont een informatiedpictogram naast het bericht, met het informatiethema.

In het volgende voorbeeld configureert de code een bevestigingsdialoog van het type `CUSTOM` met een aangepaste titel en bericht.

<ComponentDemo 
path='/webforj/confirmdialogoptions?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/optiondialog/confirm/ConfirmDialogOptionsView.java'
height = '350px'
/>

## Resultaat {#result}

De `ConfirmDialog` retourneert een resultaat op basis van de interactie van de gebruiker met de dialoog. Dit resultaat geeft aan welke knop de gebruiker heeft ingedrukt of of de dialoog is afgesloten vanwege een time-out.

:::important
Het resultaat wordt geretourneerd vanuit de `show()`-methode, of de equivalente `OptionDialog`-methode zoals hieronder weergegeven. 
:::

De `ConfirmDialog.Result` enum bevat de volgende mogelijke resultaten:

1. **`OK`**: De gebruiker heeft op de `OK`-knop geklikt.
2. **`CANCEL`**: De gebruiker heeft op de `CANCEL`-knop geklikt.
3. **`YES`**: De gebruiker heeft op de `YES`-knop geklikt.
4. **`NO`**: De gebruiker heeft op de `NO`-knop geklikt.
5. **`ABORT`**: De gebruiker heeft op de `ABORT`-knop geklikt.
6. **`RETRY`**: De gebruiker heeft op de `RETRY`-knop geklikt.
7. **`IGNORE`**: De gebruiker heeft op de `IGNORE`-knop geklikt.
8. **`FIRST_CUSTOM_BUTTON`**: De gebruiker heeft op de eerste aangepaste knop geklikt.
9. **`SECOND_CUSTOM_BUTTON`**: De gebruiker heeft op de tweede aangepaste knop geklikt.
10. **`THIRD_CUSTOM_BUTTON`**: De gebruiker heeft op de derde aangepaste knop geklikt.
11. **`TIMEOUT`**: De dialoog time-out.
12. **`UNKNOWN`**: Een onbekend resultaat, meestal gebruikt als een standaard- of foutstatus.

```java showLineNumbers
if (result == ConfirmDialog.Result.FIRST_CUSTOM_BUTTON) {
    OptionDialog.showMessageDialog("Wijzigingen verworpen", "Verworpen", "Begrepen");
} else {
    OptionDialog.showMessageDialog(
        "Wijzigingen opgeslagen", "Opgeslagen", "Begrepen", MessageDialog.MessageType.INFO);
}
```

## Standaardknop {#default-button}

De `ConfirmDialog` stelt u in staat om een standaardknop op te geven die vooraf is geselecteerd wanneer de dialoog wordt weergegeven. Dit verbetert de gebruikerservaring door een voorgestelde actie te bieden die snel kan worden bevestigd door op de <kbd>Enter</kbd> toets te drukken.

```java showLineNumbers
ConfirmDialog dialog = new ConfirmDialog(
    "Weet u het zeker?", "Bevestigen", ConfirmDialog.OptionType.YES_NO);
dialog.setDefaultButton(Button.SECOND); // tweede knop
dialog.show();
```

## Knoptekst {#buttons-text}

U kunt de tekst van de knoppen configureren met behulp van de `setButtonText(ConfirmDialog.Button button, String text)`-methode.

```java showLineNumbers
ConfirmDialog dialog = new ConfirmDialog(
    "Weet u het zeker?", "Bevestigen", ConfirmDialog.OptionType.CUSTOM);
dialog.setButtonText(ConfirmDialog.Button.FIRST, "Absoluut");
dialog.setButtonText(ConfirmDialog.Button.SECOND, "Nee");
dialog.show();
```

## HTML-verwerking {#html-processing}

Standaard verwerkt de bevestigingsdialoog HTML-inhoud en rendert deze. U kunt deze functie uitschakelen door deze zo te configureren dat deze onbewerkte tekst weergeeft.

```java showLineNumbers
ConfirmDialog dialog = new ConfirmDialog(
    "<b>Weet u het zeker?</b>", "Bevestigen",
    ConfirmDialog.OptionType.YES_NO, ConfirmDialog.MessageType.QUESTION);
dialog.setRawText(true);
dialog.show();
```

## Timeout {#timeout}

De `ConfirmDialog` stelt u in staat om een time-outduur in te stellen waarna de dialoog automatisch sluit. Deze functie is nuttig voor niet-kritische bevestigingen of acties die de onmiddellijke interactie van de gebruiker niet vereisen.

U kunt de time-out voor de dialoog configureren met de methode `setTimeout(int timeout)`. De time-outduur is in seconden. Als de gespecificeerde tijd verstrijkt zonder enige interactie van de gebruiker, sluit de dialoog automatisch.

```java showLineNumbers
ConfirmDialog dialog = new ConfirmDialog(
    "Weet u het zeker?", "Bevestigen", ConfirmDialog.OptionType.YES_NO);
dialog.setDefaultButton(Button.SECOND);
dialog.setTimeout(3);
ConfirmDialog.Result result = dialog.show();

switch (result) {
  case TIMEOUT:
    OptionDialog.showMessageDialog(
        "U nam te veel tijd om te beslissen", "Time-out", "Begrepen",
        MessageDialog.MessageType.WARNING);
    break;
  case YES:
    OptionDialog.showMessageDialog(
        "U klikte op Ja", "Ja", "Begrepen",
        MessageDialog.MessageType.INFO);
    break;
  default:
    OptionDialog.showMessageDialog(
        "U klikte op Nee", "Nee", "Begrepen",
        MessageDialog.MessageType.INFO);
    break;
}
```

## Beste praktijken {#best-practices}

1. **Duidelijke en beknopte prompts**: Zorg ervoor dat het promptbericht duidelijk uitlegt welke actie de gebruiker wordt gevraagd te bevestigen. Vermijd ambiguïteit.
2. **Geschikte optietypen**: Kies optietypen die passen bij de context van de actie. Voor eenvoudige ja/nee-beslissingen, gebruik duidelijke opties. Voor complexere scenario's, biedt extra knoppen zoals "Annuleren" aan om gebruikers in staat te stellen terug te gaan zonder een keuze te maken.
3. **Logische standaardknop**: Stel een standaardknop in die aansluit bij de meest waarschijnlijke of aanbevolen gebruikersactie om het beslissingsproces te versnellen.
4. **Consistente thematisering**: Stem de thema's van de dialoog en de knoppen af op het ontwerp van uw app voor een samenhangende gebruikerservaring.
5. **Geselecteerde gebruik van time-out**: Stel time-outs in voor niet-kritische bevestigingen, zodat gebruikers voldoende tijd hebben om de prompt te lezen en te begrijpen.
6. **Minimaliseer overmatig gebruik**: Gebruik bevestigingsdialoogvensters spaarzaam om gebruikersfrustratie te voorkomen. Beperk ze tot kritische acties die expliciete bevestiging van de gebruiker vereisen.
