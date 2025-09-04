---
sidebar_position: 25
title: Input Dialog
_i18n_hash: 60c8f92b63b241996eda4f5a08df8027
---
# Invoervenster

<DocChip chip='shadow' />
<DocChip chip='since' label='24.02' />
<JavadocLink type="foundation" location="com/webforj/component/optiondialog/InputDialog" top='true'/>

Een `InputDialog` is een modaal venster dat is ontworpen om de gebruiker om invoer te vragen. Het venster blokkeert de uitvoering van de app totdat de gebruiker de invoer heeft verstrekt of het venster sluit.

<ComponentDemo 
path='/webforj/inputdialogbasic?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/optiondialog/input/InputDialogBasicView.java'
height = '500px'
/>

## Gebruiken {#usages}

De `InputDialog` biedt een manier om invoer van gebruikers te vragen, zoals tekst, nummers of andere gegevens, en zorgt ervoor dat ze de nodige informatie verstrekken voordat ze verder gaan.

## Typen {#types}

### Invoertypen {#input-types}

De `InputDialog` ondersteunt verschillende typen invoervelden, waardoor je de invoermethode kunt afstemmen op je specifieke behoeften:

1. **TEKST**: Een standaard eenregelig tekstinvoerveld.
2. **WACHTWOORD**: Een wachtwoordinvoerveld dat de invoer van de gebruiker verbergt.
3. **NUMMER**: Een numeriek invoerveld.
4. **E-MAIL**: Een invoerveld voor e-mailadressen.
5. **URL**: Een invoerveld voor URL's.
6. **ZOEKEN**: Een tekstinvoerveld voor zoekopdrachten.
7. **DATUM**: Een invoerveld voor het selecteren van datums.
8. **TIJD**: Een invoerveld voor het selecteren van tijd.
9. **DATUMTIJD_LOKAAL**: Een invoerveld voor het selecteren van lokale datum en tijd.
10. **KLEUR**: Een invoerveld voor het selecteren van een kleur.

### Berichten type {#message-type}

De `InputDialog` ondersteunt de volgende berichttypen. Wanneer je een type configureert, toont het venster een pictogram naast het bericht, en het thema van het venster wordt bijgewerkt volgens de ontwerpregels van webforJ.

1. `PLAIN`: Toont het bericht zonder pictogram, met gebruik van het standaardthema.
2. `ERROR`: Toont een foutpictogram naast het bericht met het foutthema toegepast.
3. `QUESTION`: Toont een vraagtekenpictogram naast het bericht, met gebruik van het primaire thema.
4. `WARNING`: Toont een waarschuwingspictogram naast het bericht met het waarschuwings-thema toegepast.
5. `INFO`: Toont een informatiepictogram naast het bericht, met gebruik van het informatie-thema.

In het volgende voorbeeld wordt de gebruiker gevraagd zijn wachtwoord in te voeren om toegang tot de app te krijgen. Als de aanmelding mislukt, wordt de gebruiker opnieuw gevraagd.

<ComponentDemo 
path='/webforj/inputdialogtype?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/optiondialog/input/InputDialogTypeView.java'
height = '350px'
/>

## Resultaat {#result}

De `InputDialog` retourneert de invoer van de gebruiker als een string. Als de gebruiker het venster sluit zonder invoer te geven, zal het resultaat `null` zijn.

:::important
De resulterende string wordt geretourneerd door de `show()`-methode, of de equivalente `OptionDialog`-methode zoals hieronder weergegeven. 
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

## Standaardwaarde {#default-value}

De `InputDialog` stelt je in staat om een standaardwaarde op te geven die verschijnt in het invoerveld wanneer het venster wordt weergegeven. Dit kan gebruikers een suggestie of een eerder ingevoerde waarde bieden.

```java showLineNumbers
InputDialog dialog = new InputDialog(
    "Voer alstublieft uw naam in:", "Naam Invoer", "John Doe", InputDialog.InputType.TEXT);
String result = dialog.show();
```

## Time-out {#timeout}

De `InputDialog` stelt je in staat om een time-outduur in te stellen waarna het venster automatisch sluit. Deze functie is nuttig voor niet-kritieke invoervragen of acties die geen onmiddellijke interactie van de gebruiker vereisen.

Je kunt de time-out voor het venster configureren met behulp van de `setTimeout(int timeout)`-methode. De time-outduur is in seconden. Als de opgegeven tijd verstrijkt zonder enige interactie van de gebruiker, sluit het venster automatisch.

```java showLineNumbers
InputDialog dialog = new InputDialog(
    "Voer alstublieft uw naam in:", "Naam Invoer", "John Doe");
dialog.setTimeout(5);
String result = dialog.show();

OptionDialog.showMessageDialog(
    "U heeft ingevoerd: " + result, "Invoer Ontvangen", "OK", MessageDialog.MessageType.INFO);
```

## Best practices {#best-practices}

1. **Duidelijke en Bondige Vragen**: Zorg ervoor dat het bericht duidelijk uitlegt welke informatie de gebruiker wordt gevraagd te verstrekken.
2. **Geschikte Invoertypen**: Kies invoertypen die overeenkomen met de vereiste gegevens om nauwkeurige en relevante invoer van de gebruiker te waarborgen.
3. **Logische Standaardwaarden**: Stel standaardwaarden in die nuttige suggesties of eerdere invoer bieden om de invoer van de gebruiker te stroomlijnen.
5. **Geselecteerd Gebruik van Time-out**: Stel time-outs in voor niet-kritieke invoervragen, zodat gebruikers voldoende tijd hebben om de vereiste informatie te verstrekken.
6. **Minimaal Gebruik**: Gebruik invoervensters spaarzaam om frustratie bij de gebruiker te voorkomen. Bewaar ze voor acties die specifieke invoer van de gebruiker vereisen.
