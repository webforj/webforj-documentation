---
title: Confirm
sidebar_position: 5
_i18n_hash: 712808f446f16655074e93cda2231286
---
<DocChip chip='shadow' />
<DocChip chip='since' label='24.02' />
<JavadocLink type="foundation" location="com/webforj/component/optiondialog/ConfirmDialog" top='true'/>

Een `ConfirmDialog` is een modale dialoog die is ontworpen om de gebruiker de mogelijkheid te geven om een van een set van maximaal 3 opties te kiezen. De dialoog blokkeert de uitvoering van de app totdat de gebruiker ermee interactie heeft of deze sluit vanwege een time-out.

<!-- INTRO_END -->

## Usages {#usages}

De `ConfirmDialog` biedt een manier om gebruikers om bevestiging te vragen of om te kiezen tussen meerdere opties, zoals `Ja/Nee` of `OK/Annuleren`, zodat ze hun acties erkennen en bevestigen.

<ComponentDemo
path='/webforj/confirmdialogconstructor'
files={['src/main/java/com/webforj/samples/views/optiondialog/confirm/ConfirmDialogConstructorView.java']}
height='350px'
/>

## Types {#types}

### Optietype {#option-type}

De `ConfirmDialog` ondersteunt de volgende optietypen, die de knoppen bepalen die in de dialoog worden weergegeven:

1. **`OK`**: Toont een `OK` knop.
2. **`OK_CANCEL`**: Toont `OK` en `Annuleren` knoppen.
3. **`ABORT_RETRY_IGNORE`**: Toont `Abort`, `Retry`, en `Ignore` knoppen.
4. **`YES_NO_CANCEL`**: Toont `Ja`, `Nee`, en `Annuleren` knoppen.
5. **`YES_NO`**: Toont `Ja` en `Nee` knoppen.
6. **`RETRY_CANCEL`**: Toont `Retry` en `Annuleren` knoppen.
7. **`CUSTOM`**: Toont aangepaste knoppen zoals gespecificeerd.

### Berichttype {#message-type}

De `ConfirmDialog` ondersteunt de volgende berichttypen. Wanneer u een type configureert, toont de dialoog een pictogram naast het bericht, en het thema van de dialoog wordt bijgewerkt volgens de ontwerpregels van webforJ.

1. `PLAIN`: Toont het bericht zonder pictogram, met gebruik van het standaardthema.
2. `ERROR`: Toont een foutpictogram naast het bericht met het foutthema toegepast.
3. `QUESTION`: Toont een vraagtekenpictogram naast het bericht, met gebruik van het primaire thema.
4. `WARNING`: Toont een waarschuwingspictogram naast het bericht met het waarschuwings-thema toegepast.
5. `INFO`: Toont een info-pictogram naast het bericht, met gebruik van het info-thema.

In het volgende voorbeeld configureert de code een bevestigingsdialoog van het type `CUSTOM` met een aangepaste titel en bericht.

<ComponentDemo
path='/webforj/confirmdialogoptions'
files={['src/main/java/com/webforj/samples/views/optiondialog/confirm/ConfirmDialogOptionsView.java']}
height='350px'
/>

## Resultaat {#result}

De `ConfirmDialog` retourneert een resultaat op basis van de interactie van de gebruiker met de dialoog. Dit resultaat geeft aan welke knop de gebruiker heeft ingedrukt of of de dialoog is afgesloten vanwege een time-out.

:::important
Het resultaat zal worden geretourneerd vanuit de `show()` methode, of de equivalente `OptionDialog` methode zoals hieronder weergegeven. 
:::

De `ConfirmDialog.Result` enum omvat de volgende mogelijke resultaten:

1. **`OK`**: De gebruiker heeft op de `OK` knop gedrukt.
2. **`CANCEL`**: De gebruiker heeft op de `CANCEL` knop gedrukt.
3. **`YES`**: De gebruiker heeft op de `JA` knop gedrukt.
4. **`NO`**: De gebruiker heeft op de `NEE` knop gedrukt.
5. **`ABORT`**: De gebruiker heeft op de `ABORT` knop gedrukt.
6. **`RETRY`**: De gebruiker heeft op de `RETRY` knop gedrukt.
7. **`IGNORE`**: De gebruiker heeft op de `IGNORE` knop gedrukt.
8. **`FIRST_CUSTOM_BUTTON`**: De gebruiker heeft op de eerste aangepaste knop gedrukt
9. **`SECOND_CUSTOM_BUTTON`**: De gebruiker heeft op de tweede aangepaste knop gedrukt
10. **`THIRD_CUSTOM_BUTTON`**: De gebruiker heeft op de derde aangepaste knop gedrukt
11. **`TIMEOUT`**: De dialoog is tijdoverschreden.
12. **`UNKNOWN`**: Een onbekend resultaat, meestal gebruikt als een standaard- of fouttoestand.

```java showLineNumbers
if (result == ConfirmDialog.Result.FIRST_CUSTOM_BUTTON) {
  OptionDialog.showMessageDialog("Wijzigingen weggegooid", "Weggegooid", "Begrijp het");
} else {
  OptionDialog.showMessageDialog(
    "Wijzigingen opgeslagen", "Opgeslagen", "Begrijp het", MessageDialog.MessageType.INFO);
}
```

## Standaardknop {#default-button}

De `ConfirmDialog` stelt u in staat om een standaardknop op te geven die vooraf is geselecteerd wanneer de dialoog wordt weergegeven. Dit verbetert de gebruikerservaring door een voorgestelde actie te bieden die snel kan worden bevestigd door op de <kbd>Enter</kbd> toets te drukken.

```java showLineNumbers
ConfirmDialog dialog = new ConfirmDialog(
  "Weet je het zeker?", "Bevestigen", ConfirmDialog.OptionType.YES_NO);
dialog.setDefaultButton(Button.SECOND); // tweede knop
dialog.show();
```

## Knoptekst {#buttons-text}

U kunt de tekst van de knoppen configureren met behulp van de methode `setButtonText(ConfirmDialog.Button button, String text)`.

```java showLineNumbers
ConfirmDialog dialog = new ConfirmDialog(
  "Weet je het zeker?", "Bevestigen", ConfirmDialog.OptionType.CUSTOM);
dialog.setButtonText(ConfirmDialog.Button.FIRST, "Absoluut");
dialog.setButtonText(ConfirmDialog.Button.SECOND, "Nee");
dialog.show();
```

## HTML-verwerking {#html-processing}

Standaard verwerkt de bevestigingsdialoog HTML-inhoud en rendert deze. U kunt deze functie uitschakelen door deze te configureren om in plaats daarvan ruwe tekst weer te geven.

```java showLineNumbers
ConfirmDialog dialog = new ConfirmDialog(
  "<b>Weet je het zeker?</b>", "Bevestigen",
  ConfirmDialog.OptionType.YES_NO, ConfirmDialog.MessageType.QUESTION);
dialog.setRawText(true);
dialog.show();
```

## Time-out {#timeout}

De `ConfirmDialog` stelt u in staat om een time-outduur in te stellen waarna de dialoog automatisch sluit. Deze functie is nuttig voor niet-kritieke bevestigingen of acties die geen onmiddellijke interactie van de gebruiker vereisen.

U kunt de time-out voor de dialoog configureren met behulp van de methode `setTimeout(int timeout)`. De time-outduur is in seconden. Als de gespecificeerde tijd verstrijkt zonder enige gebruikersinteractie, sluit de dialoog automatisch.

```java showLineNumbers
ConfirmDialog dialog = new ConfirmDialog(
    "Weet je het zeker?", "Bevestigen", ConfirmDialog.OptionType.YES_NO);
dialog.setDefaultButton(Button.SECOND);
dialog.setTimeout(3);
ConfirmDialog.Result result = dialog.show();

switch (result) {
  case TIMEOUT:
    OptionDialog.showMessageDialog(
        "Je nam te veel tijd om te beslissen", "Time-out", "Begrijp het",
        MessageDialog.MessageType.WARNING);
    break;
  case YES:
    OptionDialog.showMessageDialog(
        "Je klikte Ja", "Ja", "Begrijp het",
        MessageDialog.MessageType.INFO);
    break;
  default:
    OptionDialog.showMessageDialog(
        "Je klikte Nee", "Nee", "Begrijp het",
        MessageDialog.MessageType.INFO);
    break;
}
```

## Beste praktijken {#best-practices}

1. **Duidelijke en beknopte prompts**: Zorg ervoor dat het promptbericht duidelijk uitlegt welke actie de gebruiker wordt gevraagd te bevestigen. Vermijd ambiguïteit.
2. **Geschikte optietypen**: Kies optietypen die passen bij de context van de actie. Voor eenvoudige ja/nee-beslissingen, gebruik rechttoe rechtaan opties. Voor complexere scenario's kunnen extra knoppen zoals "Annuleren" worden toegevoegd, zodat gebruikers kunnen terugtrekken zonder een keuze te maken.
3. **Logische standaardknop**: Stel een standaardknop in die overeenkomt met de meest waarschijnlijke of aanbevolen gebruikersactie om het besluitvormingsproces te stroomlijnen.
4. **Consistent thema**: Stem de thema's van de dialoog en knoppen af op het ontwerp van uw app voor een samenhangende gebruikerservaring.
5. **Doordacht gebruik van time-out**: Stel time-outs in voor niet-kritieke bevestigingen, zodat gebruikers voldoende tijd hebben om het promptbericht te lezen en te begrijpen.
6. **Minimaliseer overmatig gebruik**: Gebruik bevestigingsdialogen spaarzaam om frustratie bij gebruikers te voorkomen. Beperk ze tot kritieke acties die expliciete bevestiging van de gebruiker vereisen.
