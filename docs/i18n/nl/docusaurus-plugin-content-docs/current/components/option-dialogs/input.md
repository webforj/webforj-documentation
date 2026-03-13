---
title: Input Dialog
sidebar_position: 25
_i18n_hash: 1dbd6d7664b01a9c3282ff4f3df65ea8
---
<DocChip chip='shadow' />
<DocChip chip='since' label='24.02' />
<JavadocLink type="foundation" location="com/webforj/component/optiondialog/InputDialog" top='true'/>

Een `InputDialog` is een modaal dialoogvenster dat is ontworpen om de gebruiker om invoer te vragen. Het dialoogvenster blokkeert de uitvoering van de app totdat de gebruiker de invoer levert of het dialoogvenster sluit.

<!-- INTRO_END -->

## Usages {#usages}

De `InputDialog` vraagt gebruikers om invoer, zoals tekst, nummers of andere gegevens. Omdat het dialoogvenster modaal is, wacht de app op de reactie van de gebruiker voordat deze verdergaat:

<ComponentDemo 
path='/webforj/inputdialogbasic?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/optiondialog/input/InputDialogBasicView.java'
height = '500px'
/>

## Types {#types}

### Input types {#input-types}

De `InputDialog` ondersteunt verschillende soorten invoervelden, zodat u de invoermethode kunt afstemmen op uw specifieke behoeften:

1. **TEKST**: Een standaard invoerveld voor enkele regels tekst.
2. **WACHTWOORD**: Een invoerveld voor wachtwoorden dat de invoer van de gebruiker verbergt.
3. **NUMMER**: Een numeriek invoerveld.
4. **EMAIL**: Een invoerveld voor e-mailadressen.
5. **URL**: Een invoerveld voor URL's.
6. **ZOEKEN**: Een invoerveld voor zoekopdrachten.
7. **DATUM**: Een invoerveld voor het selecteren van data.
8. **TIJD**: Een invoerveld voor het selecteren van tijd.
9. **DATUMTIJD_LOKAAL**: Een invoerveld voor het selecteren van lokale datum en tijd.
10. **KLEUR**: Een invoerveld voor het selecteren van een kleur.

### Message type {#message-type}

De `InputDialog` ondersteunt de volgende berichttypen. Wanneer u een type configureert, toont het dialoogvenster een pictogram naast het bericht en wordt het thema van het dialoogvenster bijgewerkt volgens de regels van het webforJ-ontwerp.

1. `PLAIN`: Toont het bericht zonder pictogram, met het standaardthema.
2. `ERROR`: Toont een foutpictogram naast het bericht met het foutthema toegepast.
3. `QUESTION`: Toont een vraagtekenpictogram naast het bericht, met het primaire thema.
4. `WARNING`: Toont een waarschuwingspictogram naast het bericht met het waarschuwings-thema toegepast.
5. `INFO`: Toont een info-pictogram naast het bericht, met het info-thema.

In het volgende voorbeeld wordt de gebruiker gevraagd zijn wachtwoord in te voeren om toegang te krijgen tot de app. Als het inloggen mislukt, wordt de gebruiker opnieuw gevraagd.

<ComponentDemo 
path='/webforj/inputdialogtype?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/optiondialog/input/InputDialogTypeView.java'
height = '350px'
/>

## Result {#result}

De `InputDialog` retourneert de invoer van de gebruiker als een string. Als de gebruiker het dialoogvenster sluit zonder invoer te geven, is het resultaat `null`.

:::important
De resulterende string wordt teruggegeven vanuit de `show()`-methode, of de equivalente `OptionDialog`-methode zoals hieronder weergegeven. 
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

De `InputDialog` stelt u in staat om een standaardwaarde op te geven die verschijnt in het invoerveld wanneer het dialoogvenster wordt weergegeven. Dit kan gebruikers voorzien van een suggestie of een eerder ingevoerde waarde.

```java showLineNumbers
InputDialog dialog = new InputDialog(
    "Voer alstublieft uw naam in:", "Naam Invoer", "John Doe", InputDialog.InputType.TEXT);
String result = dialog.show();
```

## Timeout {#timeout}

De `InputDialog` maakt het mogelijk om een tijdslimiet in te stellen waarna het dialoogvenster automatisch sluit. Deze functie is nuttig voor niet-kritische invoerverzoeken of acties die de onmiddellijke interactie van de gebruiker niet vereisen.

U kunt de tijdslimiet voor het dialoogvenster instellen met de `setTimeout(int timeout)`-methode. De tijdslimiet is in seconden. Als de opgegeven tijd verstrijkt zonder enige gebruikersinteractie, sluit het dialoogvenster automatisch.

```java showLineNumbers
InputDialog dialog = new InputDialog(
    "Voer alstublieft uw naam in:", "Naam Invoer", "John Doe");
dialog.setTimeout(5);
String result = dialog.show();

OptionDialog.showMessageDialog(
    "U heeft ingevoerd: " + result, "Invoer Ontvangen", "OK", MessageDialog.MessageType.INFO);
```

## Best practices {#best-practices}

1. **Duidelijke en Bondige Aanvragen**: Zorg ervoor dat het aanvraagbericht duidelijk uitlegt welke informatie de gebruiker moet verstrekken.
2. **Geschikte Invoertypes**: Kies invoertypes die overeenkomen met de vereiste gegevens om nauwkeurige en relevante gebruikersinvoer te waarborgen.
3. **Logische Standaardwaarden**: Stel standaardwaarden in die nuttige suggesties of eerdere invoeren bieden om de invoer van de gebruiker te stroomlijnen.
5. **Doordacht Gebruik van Tijdslimieten**: Stel tijdslimieten in voor niet-kritische invoer verzoeken, zodat gebruikers voldoende tijd hebben om de vereiste informatie te geven.
6. **Minimaliseer Overmatig Gebruik**: Gebruik invoerdialoogvensters spaarzaam om frustratie van gebruikers te voorkomen. Beperk ze tot acties die specifieke gebruikersinvoer vereisen.
