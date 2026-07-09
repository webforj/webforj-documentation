---
title: Confirm
sidebar_position: 5
description: >-
  Show a blocking ConfirmDialog with up to three options, configurable button
  sets, message types, and timeout behavior.
_i18n_hash: 0d5432d42be98a19b1a6938b306b0442
---
<DocChip chip='shadow' />
<DocChip chip='since' label='24.02' />
<JavadocLink type="foundation" location="com/webforj/component/optiondialog/ConfirmDialog" top='true'/>

Een `ConfirmDialog` is een modaal dialoogvenster dat is ontworpen om de gebruiker in staat te stellen om te kiezen uit een set van maximaal 3 opties. Het dialoogvenster blokkeert de uitvoering van de app totdat de gebruiker ermee interacteert of het wordt gesloten door een timeout.

<!-- INTRO_END -->

## Toepassingen {#usages}

De `ConfirmDialog` biedt een manier om gebruikers om bevestiging te vragen of om te kiezen tussen meerdere opties, zoals `Ja/Nee` of `OK/Annuleren`, zodat ze hun acties erkennen en bevestigen.

<ComponentDemo
path='/webforj/confirmdialogconstructor'
files={['src/main/java/com/webforj/samples/views/optiondialog/confirm/ConfirmDialogConstructorView.java']}
height='350px'
/>

## Types {#types}

### Optietype {#option-type}

De `ConfirmDialog` ondersteunt de volgende optietypen, die bepalen welke knoppen in het dialoogvenster worden weergegeven:

1. **`OK`**: Toont een `OK`-knop.
2. **`OK_CANCEL`**: Toont `OK`- en `Annuleren`-knoppen.
3. **`ABORT_RETRY_IGNORE`**: Toont `Afbreken`, `Opnieuw` en `Negeren` knoppen.
4. **`YES_NO_CANCEL`**: Toont `Ja`, `Nee` en `Annuleren` knoppen.
5. **`YES_NO`**: Toont `Ja` en `Nee` knoppen.
6. **`RETRY_CANCEL`**: Toont `Opnieuw` en `Annuleren` knoppen.
7. **`CUSTOM`**: Toont aangepaste knoppen zoals gespecificeerd.

### Berichten type {#message-type}

De `ConfirmDialog` ondersteunt de volgende berichttypen. Wanneer je een type configureert, wordt er een pictogram naast het bericht weergegeven, en het thema van het dialoogvenster wordt bijgewerkt volgens de ontwerpsysteemregels van webforJ.

1. `PLAIN`: Toont het bericht zonder pictogram, met het standaardthema.
2. `ERROR`: Toont een foutpictogram naast het bericht met het foutthema toegepast.
3. `QUESTION`: Toont een vraagtekenpictogram naast het bericht, met het primaire thema.
4. `WARNING`: Toont een waarschuwingpictogram naast het bericht met het waarschuwingsthema toegepast.
5. `INFO`: Toont een informatiete tekenpictogram naast het bericht, met het informatie thema.

In het volgende voorbeeld configureert de code een bevestigingsdialoog van het type `CUSTOM` met een aangepaste titel en boodschap.

<ComponentDemo
path='/webforj/confirmdialogoptions'
files={['src/main/java/com/webforj/samples/views/optiondialog/confirm/ConfirmDialogOptionsView.java']}
height='350px'
/>

## Resultaat {#result}

De `ConfirmDialog` retourneert een resultaat op basis van de interactie van de gebruiker met het dialoogvenster. Dit resultaat geeft aan welke knop de gebruiker heeft aangeklikt of als het dialoogvenster werd gesloten als gevolg van een timeout.

:::important
Het resultaat wordt geretourneerd uit de `show()`-methode, of de equivalente `OptionDialog`-methode zoals hieronder weergegeven.
:::

De `ConfirmDialog.Result` enum bevat de volgende mogelijke resultaten:

1. **`OK`**: De gebruiker heeft op de `OK`-knop geklikt.
2. **`CANCEL`**: De gebruiker heeft op de `CANCEL`-knop geklikt.
3. **`YES`**: De gebruiker heeft op de `JA`-knop geklikt.
4. **`NO`**: De gebruiker heeft op de `NEE`-knop geklikt.
5. **`ABORT`**: De gebruiker heeft op de `AFBREKEN`-knop geklikt.
6. **`RETRY`**: De gebruiker heeft op de `OPNIEUW`-knop geklikt.
7. **`IGNORE`**: De gebruiker heeft op de `NEGEREN`-knop geklikt.
8. **`FIRST_CUSTOM_BUTTON`**: De gebruiker heeft op de eerste aangepaste knop geklikt.
9. **`SECOND_CUSTOM_BUTTON`**: De gebruiker heeft op de tweede aangepaste knop geklikt.
10. **`THIRD_CUSTOM_BUTTON`**: De gebruiker heeft op de derde aangepaste knop geklikt.
11. **`TIMEOUT`**: Het dialoogvenster verloopt.
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

De `ConfirmDialog` stelt je in staat om een standaardknop op te geven die vooraf is geselecteerd wanneer het dialoogvenster wordt weergegeven. Dit verbetert de gebruikerservaring door een voorgesteld actie te bieden die snel kan worden bevestigd door op de <kbd>Enter</kbd> toets te drukken.

```java showLineNumbers
ConfirmDialog dialog = new ConfirmDialog(
  "Weet je het zeker?", "Bevestigen", ConfirmDialog.OptionType.YES_NO);
dialog.setDefaultButton(Button.SECOND); // tweede knop
dialog.show();
```

## Knoptekst {#buttons-text}

Je kunt de tekst van de knoppen configureren met behulp van de `setButtonText(ConfirmDialog.Button button, String text)` methode.

```java showLineNumbers
ConfirmDialog dialog = new ConfirmDialog(
  "Weet je het zeker?", "Bevestigen", ConfirmDialog.OptionType.CUSTOM);
dialog.setButtonText(ConfirmDialog.Button.FIRST, "Absoluut");
dialog.setButtonText(ConfirmDialog.Button.SECOND, "Nee");
dialog.show();
```

## HTML-verwerking {#html-processing}

Standaard verwerkt het bevestigingsdialoogvenster en rendert het HTML-inhoud. Je kunt deze functie uitschakelen door het zo te configureren dat het ruwe tekst weergeeft.

```java showLineNumbers
ConfirmDialog dialog = new ConfirmDialog(
  "<b>Weet je het zeker?</b>", "Bevestigen",
  ConfirmDialog.OptionType.YES_NO, ConfirmDialog.MessageType.QUESTION);
dialog.setRawText(true);
dialog.show();
```

## Timeout {#timeout}

De `ConfirmDialog` stelt je in staat om een timeoutduur in te stellen waarna het dialoogvenster automatisch sluit. Deze functie is nuttig voor niet-kritieke bevestigingen of acties die geen onmiddellijke interactie van de gebruiker vereisen.

Je kunt de timeout voor het dialoogvenster configureren met de `setTimeout(int timeout)` methode. De timeoutduur is in seconden. Als de opgegeven tijd verstrijkt zonder enige gebruikersinteractie, sluit het dialoogvenster automatisch.

```java showLineNumbers
ConfirmDialog dialog = new ConfirmDialog(
    "Weet je het zeker?", "Bevestigen", ConfirmDialog.OptionType.YES_NO);
dialog.setDefaultButton(Button.SECOND);
dialog.setTimeout(3);
ConfirmDialog.Result result = dialog.show();

switch (result) {
  case TIMEOUT:
    OptionDialog.showMessageDialog(
        "Je hebt te lang de tijd genomen om te beslissen", "Timeout", "Begrepen",
        MessageDialog.MessageType.WARNING);
    break;
  case YES:
    OptionDialog.showMessageDialog(
        "Je hebt Ja geklikt", "Ja", "Begrepen",
        MessageDialog.MessageType.INFO);
    break;
  default:
    OptionDialog.showMessageDialog(
        "Je hebt Nee geklikt", "Nee", "Begrepen",
        MessageDialog.MessageType.INFO);
    break;
}
```

## Beste praktijken {#best-practices}

1. **Duidelijke en Bondige Prompt**: Zorg ervoor dat het promptbericht duidelijk uitlegt welke actie de gebruiker wordt gevraagd te bevestigen. Vermijd ambiguïteit.
2. **Geschikte Optietypen**: Kies optietypen die passen bij de context van de actie. Voor eenvoudige ja/nee-beslissingen, gebruik eenvoudige opties. Voor complexere scenario's, bied extra knoppen aan zoals "Annuleren" om gebruikers in staat te stellen terug te trekken zonder een keuze te maken.
3. **Logische Standaardknop**: Stel een standaardknop in die aansluit bij de meest waarschijnlijke of aanbevolen gebruikersactie om het beslissingsproces te stroomlijnen.
4. **Consistente Theming**: Stem het thema van het dialoogvenster en de knoppen af op het ontwerp van je app voor een samenhangende gebruikerservaring.
5. **Juiste Gebruik van Timeout**: Stel timeouts in voor niet-kritieke bevestigingen, zodat gebruikers voldoende tijd hebben om de prompt te lezen en te begrijpen.
6. **Minimaliseer Overmatig Gebruik**: Gebruik bevestigingsdialoogvensters spaarzaam om frustratie bij gebruikers te voorkomen. Reserveer ze voor kritieke acties die expliciete bevestiging van de gebruiker vereisen.
