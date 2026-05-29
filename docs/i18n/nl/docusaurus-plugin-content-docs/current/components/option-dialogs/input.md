---
title: Input Dialog
sidebar_position: 25
_i18n_hash: 96ced3bbe3c9ec87ebf19010833b62c5
---
<DocChip chip='shadow' />
<DocChip chip='since' label='24.02' />
<JavadocLink type="foundation" location="com/webforj/component/optiondialog/InputDialog" top='true'/>

Een `InputDialog` is een modale dialoog ontworpen om de gebruiker om input te vragen. De dialoog blokkeert de uitvoering van de app totdat de gebruiker de input heeft gegeven of de dialoog heeft gesloten.

<!-- INTRO_END -->

## Usages {#usages}

De `InputDialog` vraagt gebruikers om input, zoals tekst, cijfers of andere gegevens. Omdat de dialoog modaal is, wacht de app op een reactie van de gebruiker voordat deze verder gaat:

<ComponentDemo
path='/webforj/inputdialogbasic'
files={['src/main/java/com/webforj/samples/views/optiondialog/input/InputDialogBasicView.java']}
height='500px'
/>

## Types {#types}

### Input types {#input-types}

De `InputDialog` ondersteunt verschillende soorten invoervelden, waardoor je de invoermethode kunt aanpassen aan jouw specifieke behoeften:

1. **TEKST**: Een standaard eenregelige tekstinvoer.
2. **WACHTWOORD**: Een invoerveld voor wachtwoorden dat de invoer van de gebruiker verbergt.
3. **NUMMER**: Een numeriek invoerveld.
4. **E-MAIL**: Een invoerveld voor e-mailadressen.
5. **URL**: Een invoerveld voor URL's.
6. **ZOEKEN**: Een tekstinvoerveld voor zoeken.
7. **DATUM**: Een invoerveld voor het selecteren van data.
8. **TIJD**: Een invoerveld voor het selecteren van tijd.
9. **DATUMTIJD_LOKAAL**: Een invoerveld voor het selecteren van lokale datum en tijd.
10. **KLEUR**: Een invoerveld voor het selecteren van een kleur.

### Message type {#message-type}

De `InputDialog` ondersteunt de volgende berichttypen. Wanneer je een type configureert, toont de dialoog een pictogram naast het bericht en wordt het thema van de dialoog bijgewerkt volgens de regels van het webforJ-ontwerpsysteem.

1. `PLAIN`: Toont het bericht zonder pictogram, met gebruik van het standaardthema.
2. `ERROR`: Toont een foutpictogram naast het bericht met het foutthema toegepast.
3. `QUESTION`: Toont een vraagtekenpictogram naast het bericht, met gebruik van het primaire thema.
4. `WARNING`: Toont een waarschuwingpictogram naast het bericht met het waarschuwings-thema toegepast.
5. `INFO`: Toont een info-pictogram naast het bericht, met gebruik van het infothema.

In het volgende voorbeeld wordt de gebruiker gevraagd om hun wachtwoord in te voeren om toegang te krijgen tot de app. Als de aanmelding mislukt, wordt de gebruiker opnieuw gevraagd.

<ComponentDemo
path='/webforj/inputdialogtype'
files={['src/main/java/com/webforj/samples/views/optiondialog/input/InputDialogTypeView.java']}
height='350px'
/>

## Result {#result}

De `InputDialog` retourneert de invoer van de gebruiker als een string. Als de gebruiker de dialoog sluit zonder input te geven, is het resultaat `null`.

:::important
De resulterende string wordt geretourneerd vanuit de `show()`-methode, of de equivalente `OptionDialog`-methode zoals hieronder weergegeven. 
:::

```java showLineNumbers
String result = OptionDialog.showInputDialog(
  "Voer alstublieft uw leeftijd in:", "Leeftijd Invoer", "", InputDialog.InputType.NUMBER);

if (result != null) {
  OptionDialog.showMessageDialog("U heeft ingevoerd: " + result, "Invoer Ontvangen");
} else {
  OptionDialog.showMessageDialog("Geen invoer ontvangen", "Invoer Geannuleerd");
}
```

## Default value {#default-value}

De `InputDialog` stelt je in staat om een standaardwaarde op te geven die in het invoerveld verschijnt wanneer de dialoog wordt weergegeven. Dit kan gebruikers een suggestie of een eerder ingevoerde waarde bieden.

```java showLineNumbers
InputDialog dialog = new InputDialog(
  "Voer alstublieft uw naam in:", "Naam Invoer", "John Doe", InputDialog.InputType.TEXT);
String result = dialog.show();
```

## Timeout {#timeout}

De `InputDialog` stelt je in staat om een time-outduur in te stellen waarna de dialoog automatisch sluit. Deze functie is nuttig voor niet-kritieke invoerverzoeken of acties die geen onmiddellijke interactie van de gebruiker vereisen.

Je kunt de time-out voor de dialoog configureren met de methode `setTimeout(int timeout)`. De tijdsduur van de time-out is in seconden. Als de opgegeven tijd verstrijkt zonder enige interactie van de gebruiker, sluit de dialoog automatisch.

```java showLineNumbers
InputDialog dialog = new InputDialog(
  "Voer alstublieft uw naam in:", "Naam Invoer", "John Doe");
dialog.setTimeout(5);
String result = dialog.show();

OptionDialog.showMessageDialog(
  "U heeft ingevoerd: " + result, "Invoer Ontvangen", "OK", MessageDialog.MessageType.INFO);
```

## Best practices {#best-practices}

1. **Duidelijke en Bondige Vragen**: Zorg ervoor dat het promptbericht duidelijk uitlegt welke informatie de gebruiker wordt gevraagd te verstrekken.
2. **Geschikte Invoertypen**: Kies invoertypen die overeenkomen met de vereiste gegevens om nauwkeurige en relevante gebruikersinvoer te waarborgen.
3. **Logische Standaardwaarden**: Stel standaardwaarden in die nuttige suggesties of eerdere invoer bieden om het invoeren van de gebruiker te stroomlijnen.
5. **Verstandige Gebruik van Time-out**: Stel time-outs in voor niet-kritieke invoerverzoeken, zodat gebruikers voldoende tijd hebben om de vereiste informatie te verstrekken.
6. **Minimaal Gebruik**: Gebruik invoerdialogen spaarzaam om frustratie bij de gebruiker te voorkomen. Bewaar ze voor acties die specifieke gebruikersinvoer vereisen.
