---
title: Input Dialog
sidebar_position: 25
description: >-
  Prompt users for text, numbers, dates, colors, or other typed values with the
  modal InputDialog and message-type styling.
_i18n_hash: b797a58a2e413b1be6d2cfd814d74efa
---
<DocChip chip='shadow' />
<DocChip chip='since' label='24.02' />
<JavadocLink type="foundation" location="com/webforj/component/optiondialog/InputDialog" top='true'/>

Een `InputDialog` is een modale dialoog die ontworpen is om de gebruiker om invoer te vragen. De dialoog blokkeert de app-uitvoering totdat de gebruiker de invoer geeft of de dialoog sluit.

<!-- INTRO_END -->

## Usages {#usages}

De `InputDialog` vraagt gebruikers om invoer, zoals tekst, nummers of andere gegevens. Omdat de dialoog modaal is, wacht de app op een reactie van de gebruiker voordat deze doorgaat:

<ComponentDemo
path='/webforj/inputdialogbasic'
files={['src/main/java/com/webforj/samples/views/optiondialog/input/InputDialogBasicView.java']}
height='500px'
/>

## Types {#types}

### Invoertypen {#input-types}

De `InputDialog` ondersteunt verschillende types invoervelden, zodat je de invoermethode kunt afstemmen op je specifieke behoeften:

1. **TEKST**: Een standaard enkelregel tekstinvoer.
2. **WACHTWOORD**: Een wachtwoordinvoerveld dat de invoer van de gebruiker verbergt.
3. **NUMMER**: Een numeriek invoerveld.
4. **EMAIL**: Een invoerveld voor e-mailadressen.
5. **URL**: Een invoerveld voor URL's.
6. **ZOEKEN**: Een zoektekstinvoerveld.
7. **DATUM**: Een invoerveld voor het selecteren van datums.
8. **TIJD**: Een invoerveld voor het selecteren van tijd.
9. **DATUMTIJD_LOKAAL**: Een invoerveld voor het selecteren van lokale datum en tijd.
10. **KLEUR**: Een invoerveld voor het selecteren van een kleur.

### Berichttype {#message-type}

De `InputDialog` ondersteunt de volgende berichttypes. Wanneer je een type configureert, toont de dialoog een pictogram naast het bericht, en het thema van de dialoog wordt bijgewerkt volgens de regels van het webforJ-ontwerpsysteem.

1. `PLAIN`: Toont het bericht zonder pictogram, met het standaardthema.
2. `ERROR`: Toont een foutpictogram naast het bericht met het foutthema toegepast.
3. `QUESTION`: Toont een vraagtekenpictogram naast het bericht, met het primaire thema.
4. `WARNING`: Toont een waarschuwingspictogram naast het bericht met het waarschuwings-thema toegepast.
5. `INFO`: Toont een informatiepictogram naast het bericht, met het info-thema.

In het volgende voorbeeld wordt de gebruiker gevraagd zijn wachtwoord in te voeren om toegang te krijgen tot de app. Als de inlog mislukt, wordt de gebruiker opnieuw gevraagd.

<ComponentDemo
path='/webforj/inputdialogtype'
files={['src/main/java/com/webforj/samples/views/optiondialog/input/InputDialogTypeView.java']}
height='350px'
/>

## Resultaat {#result}

De `InputDialog` retourneert de invoer van de gebruiker als een string. Als de gebruiker de dialoog sluit zonder invoer te geven, is het resultaat `null`.

:::important
De resulterende string zal worden geretourneerd vanuit de `show()` methode, of de equivalente `OptionDialog` methode zoals hieronder weergegeven.
:::

```java showLineNumbers
String result = OptionDialog.showInputDialog(
  "Voer uw leeftijd in:", "Leeftijd Invoer", "", InputDialog.InputType.NUMBER);

if (result != null) {
  OptionDialog.showMessageDialog("U heeft ingevoerd: " + result, "Invoer Ontvangen");
} else {
  OptionDialog.showMessageDialog("Geen invoer ontvangen", "Invoer Geannuleerd");
}
```

## Standaardwaarde {#default-value}

De `InputDialog` stelt je in staat om een standaardwaarde op te geven die verschijnt in het invoerveld wanneer de dialoog wordt weergegeven. Dit kan gebruikers een suggestie of een eerder ingevoerde waarde bieden.

```java showLineNumbers
InputDialog dialog = new InputDialog(
  "Voer uw naam in:", "Naam Invoer", "John Doe", InputDialog.InputType.TEXT);
String result = dialog.show();
```

## Timeout {#timeout}

De `InputDialog` stelt je in staat om een timeoutduur in te stellen waarna de dialoog automatisch sluit. Deze functie is nuttig voor niet-kritische invoerverzoeken of acties die geen directe interactie van de gebruiker vereisen.

Je kunt de timeout voor de dialoog configureren met de methode `setTimeout(int timeout)`. De timeoutduur is in seconden. Als de opgegeven tijd verstrijkt zonder enige interactie van de gebruiker, sluit de dialoog automatisch.

```java showLineNumbers
InputDialog dialog = new InputDialog(
  "Voer uw naam in:", "Naam Invoer", "John Doe");
dialog.setTimeout(5);
String result = dialog.show();

OptionDialog.showMessageDialog(
  "U heeft ingevoerd: " + result, "Invoer Ontvangen", "OK", MessageDialog.MessageType.INFO);
```

## Best practices {#best-practices}

1. **Duidelijke en Bondige Prompt**: Zorg ervoor dat de prompt duidelijk uitlegt welke informatie de gebruiker moet verstrekken.
2. **Geschikte Invoertypes**: Kies invoertypes die passen bij de vereiste gegevens om nauwkeurige en relevante invoer van de gebruiker te waarborgen.
3. **Logische Standaardwaarden**: Stel standaardwaarden in die nuttige suggesties of eerdere invoer bieden om de invoer van de gebruiker te stroomlijnen.
4. **Zorgvuldig Gebruik van Timeout**: Stel time-outs in voor niet-kritische invoerverzoeken, zodat gebruikers voldoende tijd hebben om de gevraagde informatie te verstrekken.
5. **Minimaliseer Overmatig Gebruik**: Gebruik invoerdialoogvensters spaarzaam om frustratie bij gebruikers te voorkomen. Beperk ze tot acties die specifieke invoer van de gebruiker vereisen.
