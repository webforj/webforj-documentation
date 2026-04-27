---
title: Input Dialog
sidebar_position: 25
_i18n_hash: 3c045d4085b917bd2f338916cc61d276
---
<DocChip chip='shadow' />
<DocChip chip='since' label='24.02' />
<JavadocLink type="foundation" location="com/webforj/component/optiondialog/InputDialog" top='true'/>

Een `InputDialog` is een modale dialoog die is ontworpen om de gebruiker om invoer te vragen. De dialoog blokkeert de uitvoering van de app totdat de gebruiker de invoer heeft gegeven of de dialoog sluit.

<!-- INTRO_END -->

## Usages {#usages}

De `InputDialog` vraagt gebruikers om invoer, zoals tekst, nummers of andere gegevens. Omdat de dialoog modaal is, wacht de app op de gebruiker om te reageren voordat deze doorgaat:

<ComponentDemo 
path='/webforj/inputdialogbasic?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/optiondialog/input/InputDialogBasicView.java'
height = '500px'
/>

## Types {#types}

### Input types {#input-types}

De `InputDialog` ondersteunt verschillende soorten invoervelden, zodat je de invoermethode kunt afstemmen op jouw specifieke behoeften:

1. **TEXT**: Een standaard enkel-regelig tekstinvoerveld.
2. **PASSWORD**: Een wachtwoordinvoerveld dat de invoer van de gebruiker verbergt.
3. **NUMBER**: Een numeriek invoerveld.
4. **EMAIL**: Een invoerveld voor e-mailadressen.
5. **URL**: Een invoerveld voor URL's.
6. **SEARCH**: Een tekstinvoerveld voor zoekopdrachten.
7. **DATE**: Een invoerveld voor het selecteren van data.
8. **TIME**: Een invoerveld voor het selecteren van tijd.
9. **DATETIME_LOCAL**: Een invoerveld voor het selecteren van lokale datum en tijd.
10. **COLOR**: Een invoerveld voor het selecteren van een kleur.

### Message type {#message-type}

De `InputDialog` ondersteunt de volgende berichttypes. Wanneer je een type configureert, toont de dialoog een pictogram naast het bericht, en het thema van de dialoog wordt bijgewerkt volgens de ontwerpsysteemregels van webforJ.

1. `PLAIN`: Toont het bericht zonder pictogram, met de standaardthema.
2. `ERROR`: Toont een foutpictogram naast het bericht met het foutthema toegepast.
3. `QUESTION`: Toont een vraagtekenpictogram naast het bericht, met het primaire thema.
4. `WARNING`: Toont een waarschuwingspictogram naast het bericht met het waarschuwings-thema toegepast.
5. `INFO`: Toont een infopictogram naast het bericht, met het info-thema.

In het volgende voorbeeld wordt de gebruiker gevraagd om zijn wachtwoord in te voeren om toegang te krijgen tot de app. Als de aanmelding mislukt, wordt de gebruiker opnieuw gevraagd.

<ComponentDemo 
path='/webforj/inputdialogtype?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/optiondialog/input/InputDialogTypeView.java'
height = '350px'
/>

## Result {#result}

De `InputDialog` retourneert de invoer van de gebruiker als een string. Als de gebruiker de dialoog sluit zonder invoer te geven, is het resultaat `null`.

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

De `InputDialog` stelt je in staat om een tijdslimiet in te stellen waarna de dialoog automatisch sluit. Deze functie is nuttig voor niet-kritieke invoerverzoeken of acties die de onmiddellijke interactie van de gebruiker niet vereisen.

Je kunt de tijdslimiet voor de dialoog configureren met de `setTimeout(int timeout)`-methode. De tijdslimiet is in seconden. Als de opgegeven tijd verstrijkt zonder enige interactie van de gebruiker, sluit de dialoog automatisch.

```java showLineNumbers
InputDialog dialog = new InputDialog(
  "Voer alstublieft uw naam in:", "Naam Invoer", "John Doe");
dialog.setTimeout(5);
String result = dialog.show();

OptionDialog.showMessageDialog(
  "U heeft ingevoerd: " + result, "Invoer Ontvangen", "OK", MessageDialog.MessageType.INFO);
```

## Best practices {#best-practices}

1. **Duidelijke en Bondige Prompts**: Zorg ervoor dat de prompt duidelijk uitlegt welke informatie de gebruiker wordt gevraagd te verstrekken.
2. **Geschikte Invoertypes**: Kies invoertypes die overeenkomen met de vereiste gegevens om nauwkeurige en relevante gebruikersinvoer te waarborgen.
3. **Logische Standaardwaarden**: Stel standaardwaarden in die nuttige suggesties of eerdere invoer bieden om gebruikersinvoer te stroomlijnen.
5. **Zorgvuldig Gebruik van Tijdslimieten**: Stel tijdslimieten in voor niet-kritieke invoerverzoeken, zodat gebruikers voldoende tijd hebben om de vereiste informatie te verstrekken.
6. **Minimaliseer Overgebruik**: Gebruik invoerdialoogvensters spaarzaam om frustratie bij gebruikers te voorkomen. Reserveer ze voor acties die specifieke gebruikersinvoer vereisen.
