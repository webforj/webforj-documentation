---
sidebar_position: 25
title: Input Dialog
_i18n_hash: ba46203db1b4c35878c6509a514a70e5
---
# Invoerveld

<DocChip chip='shadow' />
<DocChip chip='since' label='24.02' />
<JavadocLink type="foundation" location="com/webforj/component/optiondialog/InputDialog" top='true'/>

Een `InputDialog` is een modaal venster dat is ontworpen om de gebruiker om invoer te vragen. Het venster blokkeert de uitvoering van de app totdat de gebruiker de invoer heeft gegeven of het venster heeft gesloten.

<ComponentDemo 
path='/webforj/inputdialogbasic?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/optiondialog/input/InputDialogBasicView.java'
height = '500px'
/>

## Gebruiken {#usages}

De `InputDialog` biedt een manier om invoer van gebruikers te vragen, zoals tekst, getallen of andere gegevens, en zorgt ervoor dat zij de benodigde informatie verstrekken voordat zij verder gaan.

## Types {#types}

### Invoertypes {#input-types}

De `InputDialog` ondersteunt verschillende typen invoervelden, waardoor je de invoermethode kunt afstemmen op je specifieke behoeften:

1. **TEKST**: Een standaard eenregelige tekstinvoer.
2. **WACHTWOORD**: Een invoerveld voor wachtwoorden dat de invoer van de gebruiker verbergt.
3. **GETAL**: Een numeriek invoerveld.
4. **E-MAIL**: Een invoerveld voor e-mailadressen.
5. **URL**: Een invoerveld voor URL's.
6. **ZOEKEN**: Een tekstinvoerveld voor zoeken.
7. **DATUM**: Een invoerveld voor het selecteren van data.
8. **TIJD**: Een invoerveld voor het selecteren van tijd.
9. **DATUMTIJD_LAAG**: Een invoerveld voor het selecteren van lokale datum en tijd.
10. **KLEUR**: Een invoerveld voor het selecteren van een kleur.

### Berichts type {#message-type}

De `InputDialog` ondersteunt de volgende berichttypes. Wanneer je een type configureert, toont het venster een pictogram naast het bericht en wordt het thema van het venster bijgewerkt volgens de regels van het webforJ-ontwerpsysteem.

1. `PLAIN`: Toont het bericht zonder pictogram, met de standaardthema.
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

## Resultaat {#result}

De `InputDialog` retourneert de invoer van de gebruiker als een tekenreeks. Als de gebruiker het venster sluit zonder invoer te geven, is de waarde `null`.

:::important
De resulterende tekenreeks wordt geretourneerd vanuit de `show()`-methode, of de equivalente `OptionDialog`-methode zoals hieronder weergegeven. 
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

De `InputDialog` stelt je in staat om een standaardwaarde op te geven die verschijnt in het invoerveld wanneer het venster wordt weergegeven. Dit kan gebruikers een suggestie of een eerder ingevoerde waarde bieden.

```java showLineNumbers
InputDialog dialog = new InputDialog(
    "Voer uw naam in:", "Naam Invoer", "John Doe", InputDialog.InputType.TEXT);
String result = dialog.show();
```

## Timeout {#timeout}

De `InputDialog` maakt het mogelijk om een timeoutduur in te stellen waarna het venster automatisch sluit. Deze functie is handig voor invoervragen of acties die niet de onmiddellijke interactie van de gebruiker vereisen.

Je kunt de timeout voor het venster configureren met de methode `setTimeout(int timeout)`. De timeoutduur is in seconden. Als de opgegeven tijd verstrijkt zonder enige gebruikersinteractie, sluit het venster automatisch.

```java showLineNumbers
InputDialog dialog = new InputDialog(
    "Voer uw naam in:", "Naam Invoer", "John Doe");
dialog.setTimeout(5);
String result = dialog.show();

OptionDialog.showMessageDialog(
    "U heeft ingevoerd: " + result, "Invoer Ontvangen", "OK", MessageDialog.MessageType.INFO);
```

## Beste praktijken {#best-practices}

1. **Duidelijke en Bondige Prompts**: Zorg ervoor dat het promptbericht duidelijk uitlegt welke informatie van de gebruiker wordt gevraagd.
2. **Geschikte Invoertypen**: Kies invoertypen die overeenkomen met de vereiste gegevens om nauwkeurige en relevante gebruikersinvoer te waarborgen.
3. **Logische Standaardwaarden**: Stel standaardwaarden in die nuttige suggesties of eerdere invoeren bieden om de gebruikersinvoer te stroomlijnen.
4. **Verstandig Gebruik van Timeout**: Stel time-outs in voor niet-kritische invoervragen, zodat gebruikers voldoende tijd hebben om de vereiste informatie te geven.
5. **Minimaliseer Overgebruik**: Gebruik invoervelden spaarzaam om frustratie bij gebruikers te voorkomen. Beperk ze tot acties waarvoor specifieke gebruikersinvoer vereist is.
