---
sidebar_position: 35
title: TextField
slug: textfield
description: A single-line input component for entering and editing text data.
_i18n_hash: 2f01abb14f66d83dd6edd3cc18ac560e
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-field" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/field/TextField" top='true'/>

Met de component `TextField` kunnen gebruikers tekst op één regel invoeren en bewerken. Je kunt het veld zo configureren dat het een specifiek virtueel toetsenbord weergeeft, zoals een numeriek toetsenblok, een toetsenbord voor e-mailinvoer, telefooninvoer of URL-invoer. De component biedt ook ingebouwde validatie om waarden af te wijzen die niet aan het opgegeven type voldoen.

<!-- INTRO_END -->

## Gebruik {#usages}

<ParentLink parent="Field" />

`TextField` is geschikt voor uiteenlopende scenario's waarin tekstinvoer of -bewerking nodig is. Hier volgen enkele voorbeelden van situaties waarin je `TextField` kunt gebruiken:

1. **Formulierinvoer**: `TextField` wordt vaak gebruikt in formulieren om gebruikersinvoer vast te leggen, zoals namen, adressen of opmerkingen. Gebruik `TextField` bij voorkeur in een app wanneer de invoer doorgaans kort genoeg is om op één regel te passen.

2. **Zoekfunctionaliteit**: `TextField` kan worden gebruikt als zoekveld, zodat gebruikers zoekopdrachten kunnen invoeren.

3. **Tekstbewerking**: `TextField` is ideaal voor apps waarin tekstbewerking of het maken van inhoud nodig is, zoals documenteditors, chat-apps of notitie-apps.

## Typen {#types}

Je kunt het type van `TextField` opgeven met de methode `setType()`. Op dezelfde manier kun je het type ophalen met de methode `getType()`, die een enum-waarde retourneert.

- `Type.TEXT`: dit is het standaardtype voor `TextField`. Regeleinden worden automatisch uit de invoerwaarde verwijderd.

- `Type.EMAIL`: dit type is bedoeld voor het invoeren van e-mailadressen. Het valideert de invoer volgens de standaardnotatie voor e-mailadressen. Daarnaast biedt het compatibele browsers en apparaten een dynamisch toetsenbord dat het typen vereenvoudigt door veelgebruikte toetsen zoals <kbd>.com</kbd> en <kbd>@</kbd> toe te voegen.

  :::note
  Deze validatie bevestigt wel dat het e-mailadres de juiste notatie heeft, maar garandeert niet dat het e-mailadres bestaat.
  :::

- `Type.TEL`: dit type is bedoeld voor het invoeren van een telefoonnummer. Op sommige apparaten met dynamische toetsenborden wordt een telefoontoetsenblok weergegeven.

- `Type.URL`: dit type is bedoeld voor het invoeren van URL's. Het valideert of een ingevoerde URL een schema en domeinnaam bevat, bijvoorbeeld: https://webforj.com. Daarnaast biedt het compatibele browsers en apparaten een dynamisch toetsenbord dat het typen vereenvoudigt door veelgebruikte toetsen zoals <kbd>:</kbd>, <kbd>/</kbd> en <kbd>.com</kbd> toe te voegen.

- `Type.SEARCH`: een tekstveld op één regel voor het invoeren van zoektermen. Regeleinden worden automatisch uit de invoerwaarde verwijderd.

<ComponentDemo
path='/webforj/textfield'
files={['src/main/java/com/webforj/samples/views/fields/textfield/TextFieldView.java']}
height='250px'
/>

## Veldwaarde {#field-value}

De waarde van een `TextField` is de huidige gebruikersinvoer als tekenreeks. In webforJ kun je deze ophalen met de methode `getValue()` of programmatisch bijwerken met `setValue(String)`.

```java
//Set the inital content
textField.setValue("Initial content");

//Retrieve the current value
String text = textField.getValue();
```

Als de methode `getValue()` wordt gebruikt voor een veld zonder huidige waarde, retourneert deze een lege tekenreeks (`""`).

Dit gedrag komt overeen met de manier waarop het HTML-element `<input type="text">` zijn waarde beschikbaar maakt via JavaScript.

:::tip Waarden combineren met validatie
Pas beperkingen toe, zoals een [patroon](#pattern-matching), een [minimumlengte](#setminlength) of een [maximumlengte](#setmaxlength), om te bepalen wanneer een waarde als geldig wordt beschouwd.
:::

## Plaathoudertekst {#placeholder-text}

Je kunt plaathoudertekst instellen voor `TextField` met de methode `setPlaceholder()`. De plaathoudertekst wordt weergegeven wanneer het veld leeg is en helpt de gebruiker om de juiste invoer in `TextField` in te voeren.

## Geselecteerde tekst {#selected-text}

Je kunt met de klasse `TextField` de geselecteerde tekst van een gebruiker ophalen en informatie over de selectie opvragen. Je haalt de geselecteerde tekst in `TextField` op met de methode `getSelectedText()`. Dit gedrag wordt vaak in combinatie met een gebeurtenis gebruikt.

```java
TextField textField = new TextField("Enter something...");
Button getSelectionBtn = new Button("Get Selected Text");

getSelectionBtn.onClick(e -> {
  String selected = textField.getSelectedText();
  System.out.println("Selected text: " + selected);
});
```

Op dezelfde manier kun je met de methode `getSelectionRange()` het huidige selectiebereik van `TextField` ophalen. Deze methode retourneert een object van het type `SelectionRange` dat de begin- en eindindex van de geselecteerde tekst bevat.

:::tip Waardewijzigingen en selectie
Gebruik `onValueChange()` om wijzigingen in de waarde van het veld af te handelen. De `ValueChangeEvent<String>` bevat de nieuwe waarde via `event.getValue()`, maar geen selectiegegevens. Roep `getSelectedText()` of `getSelectionRange()` aan op het veld om de huidige selectie op te halen.
:::

## Patroonvergelijking {#pattern-matching}

Je kunt de methode `setPattern()` gebruiken om met een reguliere expressie een validatieregel voor `TextField` te definiëren. Door een patroon in te stellen, voeg je een validatiebeperking toe die vereist dat de invoerwaarde overeenkomt met het opgegeven patroon.

Het patroon moet een geldige [JavaScript-reguliere expressie](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_expressions) zijn, zoals geïnterpreteerd door de browser. Intern wordt de vlag `u` (Unicode) toegepast om te zorgen voor nauwkeurige overeenkomsten met Unicode-codepunten. Plaats het patroon niet tussen slashes (`/`): die zijn niet nodig en worden als letterlijke tekens behandeld.

```java
textField.setPattern("[A-Za-z]{3}\\d{2}"); // e.g. ABC12
```

Als er geen patroon is opgegeven of als de syntaxis ongeldig is, wordt de validatieregel genegeerd.

:::tip Contextuele hulp
Overweeg bij complexe patronen voor `TextField` een combinatie van de methoden `setLabel()`, `setHelperText()` en `setTooltipText()` te gebruiken
om extra hints en aanwijzingen te geven.
:::

## Minimum- en maximumlengte {#minimum-and-maximum-length}

De component `TextField` ondersteunt validatiebeperkingen op basis van het aantal tekens dat de gebruiker invoert. Je kunt dit instellen met de methoden `setMinLength()` en `setMaxLength()`. Gebruik beide methoden om duidelijke grenzen vast te leggen voor acceptabele invoerlengten.

:::info Lengtevereisten
Standaard geeft het veld een bericht weer wanneer de invoerwaarde buiten het toegestane bereik valt. Zo weet de gebruiker of de invoer korter of langer moet zijn. Je kunt dit bericht overschrijven met de methode `setInvalidMessage()`.
:::

### `setMinLength()` {#setminlength}

Deze methode stelt het **minimale aantal UTF-16-code-eenheden** in dat moet worden ingevoerd om het veld als geldig te beschouwen. De waarde moet een geheel getal zijn en mag de geconfigureerde maximumlengte niet overschrijden.

```java
textField.setMinLength(5); // User must enter at least 5 characters
```

Als de invoer minder tekens bevat dan het vereiste minimum, voldoet deze niet aan de validatiebeperking. Deze regel is alleen van toepassing wanneer de gebruiker de waarde van het veld wijzigt.

### `setMaxLength()` {#setmaxlength}

Deze methode stelt het **maximale aantal UTF-16-code-eenheden** in dat in het tekstveld is toegestaan. De waarde moet `0` of hoger zijn. Als de waarde niet is ingesteld of ongeldig is, geldt er geen maximum.

```java
textField.setMaxLength(20); // User cannot enter more than 20 characters
```

Het veld voldoet niet aan de validatiebeperking als de invoerlengte de maximumlengte overschrijdt. Net als bij `setMinLength()` wordt deze validatie alleen uitgevoerd wanneer de gebruiker de waarde wijzigt.

## Aanbevolen werkwijzen {#best-practices}

In het volgende gedeelte staan enkele aanbevolen werkwijzen voor het gebruik van `TextField`.

- **Duidelijke labels en instructies geven**: geef `TextField` een duidelijk label dat aangeeft welk type informatie gebruikers moeten invoeren. Voeg aanvullende instructietekst of tijdelijke waarden toe om gebruikers te begeleiden en duidelijk te maken welke invoer wordt verwacht.

- **Spellingcontrole en automatisch aanvullen**: overweeg waar van toepassing spellingcontrole met `setSpellCheck()` en/of automatisch aanvullen in `TextField` te gebruiken. Deze functies kunnen gebruikers helpen sneller informatie in te voeren en fouten te verminderen door waarden voor te stellen op basis van eerdere invoer of vooraf gedefinieerde opties.

- **Toegankelijkheid**: gebruik duidelijke labels, toetsenbordnavigatie en ondersteuning voor hulptechnologieën, zodat gebruikers met een beperking het veld kunnen gebruiken.
