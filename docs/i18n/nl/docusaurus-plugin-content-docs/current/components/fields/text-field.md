---
sidebar_position: 35
title: TextField
slug: textfield
description: A single-line input component for entering and editing text data.
_i18n_hash: 0e0bfbd737ce384055397a7ff18b6579
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-field" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/field/TextField" top='true'/>

De `TextField` widget stelt gebruikers in staat om tekst in te voeren en te bewerken in een enkele regel. Je kunt het veld configureren om een specifiek virtueel toetsenbord weer te geven, zoals een numeriek toetsenbord, e-mailinvoer, telefooninvoer of URL-invoer. De component biedt ook ingebouwde validatie om waarden te weigeren die niet voldoen aan het opgegeven type.

<!-- INTRO_END -->

## Usages {#usages}

<ParentLink parent="Field" />

De `TextField` is geschikt voor een breed scala aan scenario's waarin tekstinvoer of -bewerking vereist is. Hier zijn enkele voorbeelden van wanneer je de `TextField` kunt gebruiken:

1. **Formuliervelden**: Een `TextField` wordt vaak gebruikt in formulieren voor het vastleggen van gebruikersinvoer, zoals namen, adressen of opmerkingen. Het is het beste om een `TextField` in een app te gebruiken wanneer de invoer over het algemeen kort genoeg is om op één regel te passen.

2. **Zoekfunctionaliteit**: `TextField` kan worden gebruikt om een zoekvak te bieden, waardoor gebruikers zoekopdrachten kunnen invoeren.

3. **Tekstbewerking**: Een `TextField` is ideaal voor apps die tekstbewerking of inhoudcreatie vereisen, zoals documentbewerkers, chat-apps of notitie-apps.

## Types {#types}

Je kunt het type van de TextField specificeren met de methode `setType()`. Evenzo kun je het type ophalen met de methode `getType()`, die een enumwaarde retourneert.

- `Type.TEXT`: Dit is het standaardtype voor een `TextField` en verwijdert automatisch regeleinden uit de invoerwaarde.

- `Type.EMAIL`: Dit type is voor het invoeren van e-mailadressen. Het valideert de invoer volgens de standaard e-mailsyntax. Bovendien biedt het compatibele browsers en apparaten een dynamisch toetsenbord dat het typen vereenvoudigt door veelgebruikte toetsen zoals <kbd>.com</kbd> en <kbd>@</kbd> op te nemen.

  :::note
  Hoewel deze validatie het formaat van het e-mailadres bevestigt, garandeert het niet dat de e-mail bestaat.
  :::

- `Type.TEL`: Dit type wordt gebruikt voor het invoeren van een telefoonnummer. Het veld toont een telefoontoetsenbord op sommige apparaten met dynamische toetsenborden.

- `Type.URL`: Dit type is voor het invoeren van URL's. Het valideert of een gebruiker een URL invoert die een schema en een domeinnaam bevat, bijvoorbeeld: https://webforj.com. Bovendien biedt het compatibele browsers en apparaten een dynamisch toetsenbord dat het typen vereenvoudigt door veelgebruikte toetsen zoals <kbd>:</kbd>, <kbd>/</kbd>, en <kbd>.com</kbd> op te nemen.

- `Type.SEARCH`: Een eencellige tekstinvoer voor het invoeren van zoekstrings. Regeleinden worden automatisch verwijderd uit de invoerwaarde.

<ComponentDemo
path='/webforj/textfield'
files={['src/main/java/com/webforj/samples/views/fields/textfield/TextFieldView.java']}
height='250px'
/>

## Veldwaarde {#field-value}

De waarde van een `TextField` vertegenwoordigt de huidige gebruikersinvoer als een tekenreeks. In webforJ kan dit worden bereikt met de methode `getValue()`, of programmatig worden bijgewerkt met `setValue(String)`.

```java
//Stel de initiële inhoud in
textField.setValue("Initiële inhoud");

//Haal de huidige waarde op
String text = textField.getValue();
```

Als de methode `getValue()` wordt gebruikt op een veld zonder huidige waarde, retourneert het een lege tekenreeks (`""`).

Dit gedrag is consistent met hoe het HTML `<input type="text">` element zijn waarde blootstelt via JavaScript.

:::tip Combineer waardeafhandeling met validatie
Pas beperkingen toe zoals een [patroon](#pattern-matching), [minimumlengte](#setminlength), of een [maximumlengte](#setmaxlength) om te definiëren wanneer een waarde als geldig wordt beschouwd. 
:::

## Plekhoudertekst {#placeholder-text}

Je kunt plekhoudertekst voor de `TextField` instellen met behulp van de methode `setPlaceholder()`. De plekhoudertekst wordt weergegeven wanneer het veld leeg is, en helpt de gebruiker om geschikte invoer in de `TextField` in te voeren.

## Geselecteerde tekst {#selected-text}

Het is mogelijk om met de `TextField`-klasse te interageren om de geselecteerde tekst van een gebruiker op te halen en informatie over de selectie van de gebruiker te krijgen. Je kunt de geselecteerde tekst in de `TextField` ophalen met de methode `getSelectedText()`. Dit gedrag wordt doorgaans gebruikt in combinatie met een gebeurtenis.

```java
TextField textField = new TextField("Voer iets in...");
Button getSelectionBtn = new Button("Haal Geselecteerde Tekst Op");

getSelectionBtn.onClick(e -> {
  String selected = textField.getSelectedText();
  System.out.println("Geselecteerde tekst: " + selected);
});
```

Evenzo is het mogelijk om het huidige selectiegebied van de `TextField` op te halen met de methode `getSelectionRange()`. Dit retourneert een `SelectionRange` object dat de begin- en eindindex van de geselecteerde tekst vertegenwoordigt.

:::tip Gebruik `getSelectedText()` vs gebeurtenispayload
Hoewel je `getSelectedText()` handmatig kunt aanroepen binnen een gebeurtenishandler, is het efficiënter om de selectiedata te gebruiken die in de payload van de gebeurtenis wordt verstrekt—zoals in een `SelectionChangeEvent`—om extra opzoekingen te vermijden.
:::

## Patroonherkenning {#pattern-matching}

Je kunt de methode `setPattern()` gebruiken om een validatieregel voor de `TextField` te definiëren met behulp van een reguliere expressie. Een patroon instellen voegt een beperking-validatie toe die vereist dat de invoerwaarde overeenkomt met het opgegeven patroon.

Het patroon moet een geldige [JavaScript reguliere expressie](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_expressions) zijn, zoals geïnterpreteerd door de browser. De `u` (Unicode) vlag wordt intern toegepast om nauwkeurige overeenkomsten van Unicode-codepunten te waarborgen. Wikkel het patroon niet in schuine strepen (`/`), omdat deze niet vereist zijn en als letterlijke tekens worden behandeld.

```java
textField.setPattern("[A-Za-z]{3}\\d{2}"); // bijv. ABC12
```

Als er geen patroon wordt aangeboden, of de syntaxis ongeldig is, wordt de validatieregel genegeerd.

:::tip Contextuele hulp
Bij gebruik van complexe patronen voor een `TextField`, overweeg om een combinatie van de methoden `setLabel()`, `setHelperText()`, en `setTooltipText()` te gebruiken om aanvullende hints en begeleiding te bieden.
:::

## Minimale en maximale lengte {#minimum-and-maximum-length}

De `TextField` widget ondersteunt beperking-validatie op basis van het aantal tekens dat door de gebruiker is ingevoerd. Dit kan worden beheerd met behulp van de methoden `setMinLength()` en `setMaxLength()`. Gebruik beide methoden om een duidelijke grens van aanvaardbare invoerlengtes te definiëren.

:::info Lengte-eisen
Standaard toont het veld een bericht wanneer de invoerwaarde buiten bereik is, waarmee aan de gebruiker wordt aangegeven of ze hun invoer moeten verkorten of verlengen. Dit bericht kan worden overschreven met de methode `setInvalidMessage()`.
:::

### `setMinLength()` {#setminlength}

Deze methode stelt het **minimum aantal UTF-16-code-eenheden** in dat moet worden ingevoerd zodat het veld als geldig wordt beschouwd. De waarde moet een heel getal zijn en mag de geconfigureerde maximale lengte niet overschrijden.

```java
textField.setMinLength(5); // Gebruiker moet minimaal 5 tekens invoeren
```

Als de invoer minder tekens bevat dan het vereiste minimum, zal de invoer de beperking-validatie niet doorstaan. Deze regel is alleen van toepassing wanneer de gebruiker de waarde van het veld wijzigt.

### `setMaxLength()` {#setmaxlength}

Deze methode stelt het **maximum aantal UTF-16-code-eenheden** in dat is toegestaan in het tekstveld. De waarde moet `0` of meer zijn. Als deze niet is ingesteld, of op een ongeldige waarde is ingesteld, wordt er geen maximum gehandhaafd.

```java
textField.setMaxLength(20); // Gebruiker kan niet meer dan 20 tekens invoeren
```

Het veld faalt in beperking-validatie als de invoerlengte het minimum overschrijdt. Net als bij `setMinLength()`, wordt deze validatie alleen geactiveerd wanneer de waarde door de gebruiker wordt gewijzigd.

## Beste praktijken {#best-practices}

De volgende sectie beschrijft enkele voorgestelde best practices voor het gebruik van de `TextField`.

- **Bied duidelijke labels en instructies**: Label de `TextField` duidelijk om het type informatie aan te geven dat gebruikers moeten invoeren. Bied aanvullende instructietekst of plekhouderwaarden aan om gebruikers te begeleiden en verwachtingen te scheppen voor de vereiste invoer.

- **Spellingscontrole en automatiseren**: Overweeg, waar van toepassing, spellingscontrole te gebruiken met `setSpellCheck()` en/of autocomplete in een `TextField`. Deze functies kunnen gebruikers helpen informatie sneller in te voeren en fouten te verminderen door voorgestelde waarden te bieden op basis van eerdere invoeren of vooraf gedefinieerde opties.

- **Toegankelijkheid**: Maak gebruik van de `TextField` widget met toegankelijkheid in gedachten, met inachtneming van toegankelijkheidsnormen zoals juiste labeling, ondersteuning voor navigatie met het toetsenbord en compatibiliteit met ondersteunende technologieën. Zorg ervoor dat gebruikers met een handicap effectief met de `TextField` kunnen interageren.
