---
title: Confirm
sidebar_position: 5
_i18n_hash: d77902dcb6290597159d340941f5e8b7
---
<DocChip chip='shadow' />
<DocChip chip='since' label='24.02' />
<JavadocLink type="foundation" location="com/webforj/component/optiondialog/ConfirmDialog" top='true'/>

Een `ConfirmDialog` is een modale dialoog ontworpen om de gebruiker de mogelijkheid te bieden om een van een set van maximaal 3 opties te kiezen. De dialoog blokkeert de uitvoering van de app totdat de gebruiker ermee interactie heeft of het sluit vanwege een time-out.

## Usages {#usages}

De `ConfirmDialog` biedt een manier om gebruikers om bevestiging te vragen of om te kiezen tussen meerdere opties, zoals `Ja/Nee` of `OK/Annuleren`, waarbij ervoor wordt gezorgd dat ze hun acties erkennen en bevestigen.

<ComponentDemo 
path='/webforj/confirmdialogconstructor?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/optiondialog/confirm/ConfirmDialogConstructorView.java'
height = '350px'
/>

## Types {#types}

### Optietype {#option-type}

De `ConfirmDialog` ondersteunt de volgende optietypes, die de knoppen bepalen die in de dialoog worden weergegeven:

1. **`OK`**: Toont een `OK`-knop.
2. **`OK_CANCEL`**: Toont `OK`- en `Annuleren`-knoppen.
3. **`ABORT_RETRY_IGNORE`**: Toont `Abort`, `Retry` en `Ignore`-knoppen.
4. **`YES_NO_CANCEL`**: Toont `Ja`, `Nee` en `Annuleren`-knoppen.
5. **`YES_NO`**: Toont `Ja` en `Nee`-knoppen.
6. **`RETRY_CANCEL`**: Toont `Retry` en `Annuleren`-knoppen.
7. **`CUSTOM`**: Toont aangepaste knoppen zoals opgegeven.

### Berichten type {#message-type}

De `ConfirmDialog` ondersteunt de volgende berichttypes. Wanneer je een type configureert, toont de dialoog een pictogram naast het bericht, en het thema van de dialoog wordt bijgewerkt volgens de regels van het webforJ-ontwerpsysteem.

1. `PLAIN`: Toont het bericht zonder pictogram, met het standaardthema.
2. `ERROR`: Toont een foutpictogram naast het bericht met het foutthema toegepast.
3. `QUESTION`: Toont een vraagtekenpictogram naast het bericht, met het primaire thema.
4. `WARNING`: Toont een waarschuwing pictogram naast het bericht met het waarschuwings-thema toegepast.
5. `INFO`: Toont een informatief pictogram naast het bericht, met het informatie-thema.

In het volgende voorbeeld configureert de code een bevestigingsdialoog van het type `CUSTOM` met een aangepaste titel en bericht.

<ComponentDemo 
path='/webforj/confirmdialogoptions?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/optiondialog/confirm/ConfirmDialogOptionsView.java'
height = '350px'
/>

## Resultaat {#result}

De `ConfirmDialog` geeft een resultaat terug op basis van de interactie van de gebruiker met de dialoog. Dit resultaat geeft aan welke knop de gebruiker heeft aangeklikt of of de dialoog is afgesloten vanwege een time-out.

:::important
Het resultaat zal worden geretourneerd vanuit de `show()`-methode, of de equivalente `OptionDialog`-methode zoals hieronder weergegeven. 
:::

De `ConfirmDialog.Result` enum omvat de volgende mogelijke resultaten:

1. **`OK`**: De gebruiker klikte op de `OK`-knop.
2. **`CANCEL`**: De gebruiker klikte op de `CANCEL`-knop.
3. **`YES`**: De gebruiker klikte op de `JA`-knop.
4. **`NO`**: De gebruiker klikte op de `NEE`-knop.
5. **`ABORT`**: De gebruiker klikte op de `ABORT`-knop.
6. **`RETRY`**: De gebruiker klikte op de `RETRY`-knop.
7. **`IGNORE`**: De gebruiker klikte op de `IGNORE`-knop.
8. **`FIRST_CUSTOM_BUTTON`**: De gebruiker klikte op de eerste aangepaste knop.
9. **`SECOND_CUSTOM_BUTTON`**: De gebruiker klikte op de tweede aangepaste knop.
10. **`THIRD_CUSTOM_BUTTON`**: De gebruiker klikte op de derde aangepaste knop.
11. **`TIMEOUT`**: De dialoog heeft een time-out.
12. **`UNKNOWN`**: Een onbekend resultaat, meestal gebruikt als een standaard of foutstatus.

```java showLineNumbers
if (result == ConfirmDialog.Result.FIRST_CUSTOM_BUTTON) {
    OptionDialog.showMessageDialog("Veranderingen verworpen", "Verworpen", "Snap het");
} else {
    OptionDialog.showMessageDialog(
        "Veranderingen opgeslagen", "Opgeslagen", "Snap het", MessageDialog.MessageType.INFO);
}
```

## Standaardknop {#default-button}

De `ConfirmDialog` stelt je in staat om een standaardknop op te geven die vooraf is geselecteerd wanneer de dialoog wordt weergegeven. Dit verbetert de gebruikerservaring door een voorgestelde actie te bieden die snel kan worden bevestigd door op de <kbd>Enter</kbd> toets te drukken.

```java showLineNumbers
ConfirmDialog dialog = new ConfirmDialog(
    "Weet je het zeker?", "Bevestigen", ConfirmDialog.OptionType.YES_NO);
dialog.setDefaultButton(Button.SECOND); // tweede knop
dialog.show();
```

## Knoptekst {#buttons-text}

Je kunt de tekst van de knoppen configureren met de methode `setButtonText(ConfirmDialog.Button button, String text)`.

```java showLineNumbers
ConfirmDialog dialog = new ConfirmDialog(
    "Weet je het zeker?", "Bevestigen", ConfirmDialog.OptionType.CUSTOM);
dialog.setButtonText(ConfirmDialog.Button.FIRST, "Absoluut");
dialog.setButtonText(ConfirmDialog.Button.SECOND, "Nee");
dialog.show();
```

## HTML-verwerking {#html-processing}

Standaard verwerkt de bevestigingsdialoog en rendert het HTML-inhoud. Je kunt deze functie uitschakelen door het te configureren om in plaats daarvan onbewerkte tekst weer te geven.

```java showLineNumbers
ConfirmDialog dialog = new ConfirmDialog(
    "<b>Weet je het zeker?</b>", "Bevestigen",
    ConfirmDialog.OptionType.YES_NO, ConfirmDialog.MessageType.QUESTION);
dialog.setRawText(true);
dialog.show();
```

## Time-out {#timeout}

De `ConfirmDialog` stelt je in staat om een time-outduur in te stellen waarna de dialoog automatisch sluit. Deze functie is nuttig voor niet-kritieke bevestigingen of acties die geen onmiddellijke interactie van de gebruiker vereisen.

Je kunt de time-out voor de dialoog configureren met de methode `setTimeout(int timeout)`. De time-outduur is in seconden. Als de opgegeven tijd verstrijkt zonder enige interactie van de gebruiker, sluit de dialoog automatisch.

```java showLineNumbers
ConfirmDialog dialog = new ConfirmDialog(
    "Weet je het zeker?", "Bevestigen", ConfirmDialog.OptionType.YES_NO);
dialog.setDefaultButton(Button.SECOND);
dialog.setTimeout(3);
ConfirmDialog.Result result = dialog.show();

switch (result) {
  case TIMEOUT:
    OptionDialog.showMessageDialog(
        "Je deed te lang over je beslissing", "Time-out", "Snap het",
        MessageDialog.MessageType.WARNING);
    break;
  case YES:
    OptionDialog.showMessageDialog(
        "Je klikte op Yes", "Ja", "Snap het",
        MessageDialog.MessageType.INFO);
    break;
  default:
    OptionDialog.showMessageDialog(
        "Je klikte op No", "Nee", "Snap het",
        MessageDialog.MessageType.INFO);
    break;
}
```

## Best practices {#best-practices}

1. **Duidelijke en Bondige Vragen**: Zorg ervoor dat de vraag duidelijk uitlegt welke actie de gebruiker wordt gevraagd te bevestigen. Vermijd ambiguïteit.
2. **Geschikte Optietypes**: Kies optietypes die passen bij de context van de actie. Voor eenvoudige ja/nee-beslissingen, gebruik eenvoudige opties. Voor complexere scenario's, bied extra knoppen zoals "Annuleren" aan om gebruikers de mogelijkheid te geven om zonder keuze terug te komen.
3. **Logische Standaardknop**: Stel een standaardknop in die aansluit bij de meest waarschijnlijke of aanbevolen gebruikersactie om het beslissingsproces te stroomlijnen.
4. **Consistent Thema**: Stem de thema's van de dialoog en knoppen af op het ontwerp van je app voor een samenhangende gebruikerservaring.
5. **Oordeelkundig Gebruik van Time-out**: Stel time-outs in voor niet-kritieke bevestigingen, zodat gebruikers voldoende tijd hebben om de vraag te lezen en te begrijpen.
6. **Minimaliseer Overmatig Gebruik**: Gebruik bevestigingsdialogen spaarzaam om frustratie bij gebruikers te voorkomen. Beperk ze tot kritieke acties die expliciete bevestiging van de gebruiker vereisen.
