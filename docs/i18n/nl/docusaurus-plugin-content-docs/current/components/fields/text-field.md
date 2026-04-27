---
sidebar_position: 35
title: TextField
slug: textfield
description: A single-line input component for entering and editing text data.
_i18n_hash: 51fe8b136580a1fca9e5a918389f33bf
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-field" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/field/TextField" top='true'/>

De `TextField` component stelt gebruikers in staat om tekst in te voeren en te bewerken in een enkele regel. U kunt het veld configureren om een specifiek virtueel toetsenbord weer te geven, zoals een numeriek toetsenbord, e-mailinvoer, telefooninvoer of URL-invoer. De component biedt ook ingebouwde validatie om waarden te verwerpen die niet voldoen aan het opgegeven type.

<!-- INTRO_END -->

## Usages {#usages}

<ParentLink parent="Field" />

De `TextField` is geschikt voor een breed scala aan scenario's waarin tekstinvoer of -bewerking vereist is. Hier zijn enkele voorbeelden van wanneer de `TextField` te gebruiken:

1. **Formuliervelden**: Een `TextField` wordt vaak gebruikt in formulieren om gebruikersinvoer vast te leggen, zoals namen, adressen of opmerkingen. Het is het beste om een `TextField` in een app te gebruiken wanneer de invoer over het algemeen kort genoeg is om op één regel te passen.

2. **Zoekfunctionaliteit**: `TextField` kan worden gebruikt om een zoekvak te bieden, waarmee gebruikers zoekopdrachten kunnen invoeren.

3. **Tekstbewerking**: Een `TextField` is ideaal voor apps die tekstbewerking of contentcreatie vereisen, zoals documenteditors, chat-apps of notitie-apps.

## Types {#types}

U kunt het type van de TextField opgeven met de `setType()`-methode. Evenzo kunt u het type ophalen met de `getType()`-methode, die een enum-waarde retourneert.

- `Type.TEXT`: Dit is het standaardtype voor een `TextField` en verwijdert automatisch regeleinden uit de invoerwaarde.

- `Type.EMAIL`: Dit type is voor het invoeren van e-mailadressen. Het valideert de invoer volgens de standaard e-mailsyntax. Bovendien biedt het compatibele browsers en apparaten een dynamisch toetsenbord dat het typen vereenvoudigt door veelgebruikte toetsen zoals <kbd>.com</kbd> en <kbd>@</kbd> op te nemen.

  :::note
  Hoewel deze validatie bevestigt dat het e-mailadres een geldig formaat heeft, garandeert het niet dat het e-mailadres bestaat.
  :::

- `Type.TEL`: Dit type wordt gebruikt voor het invoeren van een telefoonnummer. Het veld toont een telefoon-toetsenbord op sommige apparaten met dynamische toetsenborden.

- `Type.URL`: Dit type is voor het invoeren van URL's. Het valideert of een gebruiker een URL invoert die een schema en een domeinnaam bevat, bijvoorbeeld: https://webforj.com. Bovendien biedt het compatibele browsers en apparaten een dynamisch toetsenbord dat het typen vereenvoudigt door veelgebruikte toetsen zoals <kbd>:</kbd>, <kbd>/</kbd> en <kbd>.com</kbd> op te nemen.

- `Type.SEARCH`: Een tekstveld voor het invoeren van zoekstrings in één regel. Regeleinden worden automatisch verwijderd uit de invoerwaarde.

<ComponentDemo 
path='/webforj/textfield?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/textfield/TextFieldView.java'
height='250px'
/>

## Veldwaarde {#field-value}

De waarde van een `TextField` vertegenwoordigt de huidige gebruikersinvoer als een string. In webforJ kan dit worden geraadpleegd met de `getValue()`-methode, of programmatisch worden bijgewerkt met `setValue(String)`.

```java
//Stel de initiële inhoud in
textField.setValue("Initiële inhoud");

//Haal de huidige waarde op
String text = textField.getValue();
```

Als de `getValue()`-methode wordt gebruikt op een veld zonder huidige waarde, retourneert het een lege string (`""`).

Dit gedrag is consistent met hoe het HTML `<input type="text">` element zijn waarde beschikbaar stelt via JavaScript.

:::tip Combineer waardeafhandeling met validatie
Breng beperkingen aan, zoals een [patroon](#pattern-matching), [minimumlengte](#setminlength), of een [maximumlengte](#setmaxlength) om te definiëren wanneer een waarde als geldig wordt beschouwd. 
:::

## Placeholder-tekst {#placeholder-text}

U kunt placeholder-tekst instellen voor de `TextField` met de `setPlaceholder()`-methode. De placeholder-tekst wordt weergegeven wanneer het veld leeg is, en helpt de gebruiker om geschikte invoer in de `TextField` te geven.

## Geselecteerde tekst {#selected-text}

Het is mogelijk om met de `TextField`-klasse te interageren om de geselecteerde tekst van een gebruiker op te halen, en om informatie over de selectie van de gebruiker te krijgen. U kunt de geselecteerde tekst in de `TextField` ophalen met de `getSelectedText()`-methode. Dit gedrag wordt meestal gebruikt in combinatie met een gebeurtenis. 

```java
TextField textField = new TextField("Voer iets in...");
Button getSelectionBtn = new Button("Haal Geselecteerde Tekst Op");

getSelectionBtn.onClick(e -> {
  String selected = textField.getSelectedText();
  System.out.println("Geselecteerde tekst: " + selected);
});
```

Evenzo is het mogelijk om het huidige selectiegebied van de `TextField` op te halen met de `getSelectionRange()`-methode. Dit retourneert een `SelectionRange`-object dat de start- en eindindexen van de geselecteerde tekst vertegenwoordigt.

:::tip Gebruik `getSelectedText()` vs gebeurtenispayload
Hoewel u `getSelectedText()` handmatig kunt aanroepen binnen een gebeurtenishandler, is het efficiënter om de selectiedata te gebruiken die in de payload van de gebeurtenis is opgenomen—zoals in een `SelectionChangeEvent`—om extra zoekopdrachten te vermijden.
:::

## Patroonmatching {#pattern-matching}

U kunt de `setPattern()`-methode gebruiken om een validatieregel voor de `TextField` te definiëren met behulp van een reguliere expressie. Het instellen van een patroon voegt een beperkingsvalidatie toe die vereist dat de invoerwaarde overeenkomt met het opgegeven patroon.

Het patroon moet een geldige [JavaScript-reguliere expressie](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_expressions) zijn, zoals geïnterpreteerd door de browser. De `u` (Unicode) vlag wordt intern toegepast om nauwkeurige overeenstemming van Unicode-codepunten te waarborgen. Wikkel het patroon niet in schuine strepen (`/`), aangezien die niet vereist zijn en als letterlijke tekens worden behandeld.

```java
textField.setPattern("[A-Za-z]{3}\\d{2}"); // bijv. ABC12
```

Als er geen patroon wordt opgegeven, of als de syntax ongeldig is, wordt de validatieregel genegeerd.

:::tip Contextuele hulp
Bij het gebruik van complexe patronen voor een `TextField`, overweeg een combinatie van de `setLabel()`, `setHelperText()` en `setTooltipText()` methoden
om extra aanwijzingen en begeleiding te bieden.
:::

## Minimum en maximum lengte {#minimum-and-maximum-length}

De `TextField`-component ondersteunt beperkingsvalidatie op basis van het aantal door de gebruiker ingevoerde tekens. Dit kan worden gecontroleerd met de `setMinLength()` en `setMaxLength()`-methoden. Gebruik beide methoden om een duidelijke grens van acceptabele invoerlengtes te definiëren.

:::info Lengte-eisen
Standaard toont het veld een bericht wanneer de invoerwaarde buiten het bereik ligt, zodat de gebruiker weet of ze hun invoer moeten verkorten of verlengen. Dit bericht kan worden overschreven met de `setInvalidMessage()`-methode.
:::

### `setMinLength()` {#setminlength}

Deze methode stelt het **minimum aantal UTF-16-code-eenheden** in dat moet worden ingevoerd om het veld als geldig te beschouwen. De waarde moet een geheel getal zijn en mag de geconfigureerde maximumlengte niet overschrijden.

```java
textField.setMinLength(5); // Gebruiker moet minimaal 5 tekens invoeren
```

Als de invoer minder tekens bevat dan het vereiste minimum, zal de invoer niet voldoen aan de beperkingsvalidatie. Deze regel geldt alleen wanneer de gebruiker de waarde van het veld wijzigt.

### `setMaxLength()` {#setmaxlength}

Deze methode stelt het **maximum aantal UTF-16-code-eenheden** in dat is toegestaan in het tekstveld. De waarde moet `0` of groter zijn. Als dit niet is ingesteld, of als het op een ongeldige waarde is ingesteld, wordt er geen maximum afgedwongen.

```java
textField.setMaxLength(20); // Gebruiker kan niet meer dan 20 tekens invoeren
```

Het veld voldoet niet aan de beperkingsvalidatie als de invoerlengte de minimumlengte overschrijdt. Net als bij `setMinLength()` wordt deze validatie alleen geactiveerd wanneer de waarde door de gebruiker wordt gewijzigd.

## Best practices {#best-practices}

De volgende sectie schetst enkele voorgestelde best practices voor het gebruik van de `TextField`.

- **Bied duidelijke labels en instructies aan**: Label de `TextField` duidelijk om het type informatie aan te geven dat gebruikers moeten invoeren. Geef aanvullende instructietekst of placeholderwaarden om gebruikers te begeleiden en verwachtingen vast te stellen voor de vereiste invoer.

- **Spellingcontrole en automatisch aanvullen**: Wanneer van toepassing, overweeg om spellingcontrole te gebruiken met `setSpellCheck()` en/of automatisch aanvullen in een `TextField`. Deze functies kunnen gebruikers helpen informatie sneller in te voeren en fouten te verminderen door gesuggereerde waarden op basis van eerdere invoer of vooraf gedefinieerde opties te bieden.

- **Toegankelijkheid**: Gebruik de `TextField`-component met toegankelijkheid in gedachten, en houd rekening met toegankelijkheidsnormen zoals goede labeling, ondersteuning voor navigatie met toetsenbord, en compatibiliteit met ondersteunende technologieën. Zorg ervoor dat gebruikers met een handicap effectief met de `TextField` kunnen interageren.
