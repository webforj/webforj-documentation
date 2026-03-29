---
title: Confirm
sidebar_position: 5
_i18n_hash: f55c50a799ee979b4bd4dfd24ba56a19
---
<DocChip chip='shadow' />
<DocChip chip='since' label='24.02' />
<JavadocLink type="foundation" location="com/webforj/component/optiondialog/ConfirmDialog" top='true'/>

Een `ConfirmDialog` is een modaal dialoogvenster ontworpen om de gebruiker de keuze te laten maken uit een set van maximaal 3 opties. Het dialoogvenster blokkeert de uitvoer van de app totdat de gebruiker ermee interactie heeft of het sluit vanwege een time-out.

<!-- INTRO_END -->

## Usages {#usages}

De `ConfirmDialog` biedt een manier om gebruikers om bevestiging te vragen of om tussen meerdere opties te kiezen, zoals `Ja/Nee` of `OK/Annuleren`, om ervoor te zorgen dat ze hun acties erkennen en bevestigen.

<ComponentDemo 
path='/webforj/confirmdialogconstructor?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/optiondialog/confirm/ConfirmDialogConstructorView.java'
height = '350px'
/>

## Types {#types}

### Optietype {#option-type}

De `ConfirmDialog` ondersteunt de volgende optietypes, die bepalen welke knoppen in het dialoogvenster worden weergegeven:

1. **`OK`**: Toont een `OK`-knop.
2. **`OK_CANCEL`**: Toont `OK`- en `Annuleren`-knoppen.
3. **`ABORT_RETRY_IGNORE`**: Toont `Annuleren`, `Opnieuw` en `Negeren` knoppen.
4. **`YES_NO_CANCEL`**: Toont `Ja`, `Nee` en `Annuleren` knoppen.
5. **`YES_NO`**: Toont `Ja` en `Nee` knoppen.
6. **`RETRY_CANCEL`**: Toont `Opnieuw` en `Annuleren` knoppen.
7. **`CUSTOM`**: Toont op maat gemaakte knoppen zoals gespecificeerd.

### Berichts type {#message-type}

De `ConfirmDialog` ondersteunt de volgende berichttypes. Wanneer je een type configureert, toont het dialoogvenster een pictogram naast het bericht, en het thema van het dialoogvenster wordt bijgewerkt volgens de richtlijnen van het webforJ ontwerpsysteem.

1. `PLAIN`: Toont het bericht zonder pictogram, met het standaardthema.
2. `ERROR`: Toont een foutpictogram naast het bericht met het foutthema toegepast.
3. `QUESTION`: Toont een vraagtekenpictogram naast het bericht, met het primaire thema.
4. `WARNING`: Toont een waarschuwingspictogram naast het bericht met het waarschuwings thema toegepast.
5. `INFO`: Toont een info-pictogram naast het bericht, met het info-thema.

In het volgende voorbeeld configureert de code een bevestigingsdialoog van het type `CUSTOM` met een aangepaste titel en bericht.

<ComponentDemo 
path='/webforj/confirmdialogoptions?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/optiondialog/confirm/ConfirmDialogOptionsView.java'
height = '350px'
/>

## Resultaat {#result}

De `ConfirmDialog` retourneert een resultaat op basis van de interactie van de gebruiker met het dialoogvenster. Dit resultaat geeft aan welke knop de gebruiker heeft aangeklikt of of het dialoogvenster is afgesloten vanwege een time-out.

:::important
Het resultaat wordt geretourneerd vanuit de `show()`-methode, of de equivalente `OptionDialog`-methode zoals hieronder weergegeven. 
:::

De `ConfirmDialog.Result` enum bevat de volgende mogelijke resultaten:

1. **`OK`**: De gebruiker klikte op de `OK`-knop.
2. **`CANCEL`**: De gebruiker klikte op de `CANCEL`-knop.
3. **`YES`**: De gebruiker klikte op de `JA`-knop.
4. **`NO`**: De gebruiker klikte op de `NEE`-knop.
5. **`ABORT`**: De gebruiker klikte op de `ANNULEREN`-knop.
6. **`RETRY`**: De gebruiker klikte op de `OPNIEUW`-knop.
7. **`IGNORE`**: De gebruiker klikte op de `NEGEREN`-knop.
8. **`FIRST_CUSTOM_BUTTON`**: De gebruiker klikte op de eerste aangepaste knop.
9. **`SECOND_CUSTOM_BUTTON`**: De gebruiker klikte op de tweede aangepaste knop.
10. **`THIRD_CUSTOM_BUTTON`**: De gebruiker klikte op de derde aangepaste knop.
11. **`TIMEOUT`**: Het dialoogvenster is timemout.
12. **`UNKNOWN`**: Een onbekend resultaat, meestal gebruikt als een standaard of foutstatus.

```java showLineNumbers
if (result == ConfirmDialog.Result.FIRST_CUSTOM_BUTTON) {
  OptionDialog.showMessageDialog("Wijzigingen afgewezen", "Afgewezen", "Snap het");
} else {
  OptionDialog.showMessageDialog(
    "Wijzigingen opgeslagen", "Opgeslagen", "Snap het", MessageDialog.MessageType.INFO);
}
```

## Standaardknop {#default-button}

De `ConfirmDialog` stelt je in staat een standaardknop op te geven die vooraf is geselecteerd wanneer het dialoogvenster wordt weergegeven. Dit verbetert de gebruikerservaring door een voorgestelde actie aan te bieden die snel kan worden bevestigd door op de <kbd>Enter</kbd> toets te drukken.

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

Standaard verwerkt en rendert het bevestigingsdialoogvenster HTML-inhoud. Je kunt deze functie uitschakelen door het te configureren om in plaats daarvan platte tekst weer te geven.

```java showLineNumbers
ConfirmDialog dialog = new ConfirmDialog(
  "<b>Weet je het zeker?</b>", "Bevestigen",
  ConfirmDialog.OptionType.YES_NO, ConfirmDialog.MessageType.QUESTION);
dialog.setRawText(true);
dialog.show();
```

## Time-out {#timeout}

De `ConfirmDialog` stelt je in staat een tijdslimiet in te stellen waarna het dialoogvenster automatisch sluit. Deze functie is nuttig voor niet-kritische bevestigingen of acties die de onmiddellijke interactie van de gebruiker niet vereisen.

Je kunt de time-out voor het dialoogvenster configureren met de methode `setTimeout(int timeout)`. De time-outduur is in seconden. Als de opgegeven tijd verstrijkt zonder enige gebruikersinteractie, sluit het dialoogvenster automatisch.

```java showLineNumbers
ConfirmDialog dialog = new ConfirmDialog(
    "Weet je het zeker?", "Bevestigen", ConfirmDialog.OptionType.YES_NO);
dialog.setDefaultButton(Button.SECOND);
dialog.setTimeout(3);
ConfirmDialog.Result result = dialog.show();

switch (result) {
  case TIMEOUT:
    OptionDialog.showMessageDialog(
        "Je hebt te lang om een beslissing gevraagd", "Time-out", "Snap het",
        MessageDialog.MessageType.WARNING);
    break;
  case YES:
    OptionDialog.showMessageDialog(
        "Je klikte op Ja", "Ja", "Snap het",
        MessageDialog.MessageType.INFO);
    break;
  default:
    OptionDialog.showMessageDialog(
        "Je klikte op Nee", "Nee", "Snap het",
        MessageDialog.MessageType.INFO);
    break;
}
```

## Best practices {#best-practices}

1. **Duidelijke en beknopte prompts**: Zorg ervoor dat de prompttekst duidelijk uitlegt welke actie de gebruiker wordt gevraagd te bevestigen. Vermijd ambiguïteit.
2. **Geschikte optietypes**: Kies optietypes die passen bij de context van de actie. Voor eenvoudige ja/nee-beslissingen, gebruik simpele opties. Voor meer complexe scenario's, bied extra knoppen zoals "Annuleren" aan, zodat gebruikers kunnen terugtreden zonder een keuze te maken.
3. **Logische standaardknop**: Stel een standaardknop in die overeenkomt met de meest waarschijnlijke of aanbevolen actie van de gebruiker om het besluitvormingsproces te stroomlijnen.
4. **Consistente thematisering**: Stem de thema's van het dialoogvenster en de knoppen af op het ontwerp van je app voor een samenhangende gebruikerservaring.
5. **Verstandig gebruik van time-out**: Stel time-outs in voor niet-kritische bevestigingen, zodat gebruikers voldoende tijd hebben om de prompt te lezen en te begrijpen.
6. **Minimaliseer overmatig gebruik**: Gebruik bevestigingsdialoogvensters spaarzaam om frustratie bij de gebruiker te voorkomen. Reserveer ze voor kritische acties die expliciete bevestiging van de gebruiker vereisen.
