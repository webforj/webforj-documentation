---
sidebar_position: 35
title: TextField
slug: textfield
description: A single-line input component for entering and editing text data.
sidebar_class_name: updated-content
_i18n_hash: d582e67d9cfff3b1934f0e3b1a8396ab
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-field" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/field/TextField" top='true'/>

<ParentLink parent="Field" />

De `TextField` component stelt gebruikers in staat om tekst in te voeren en te bewerken in een enkele regel. U kunt het veld configureren om een specifiek virtueel toetsenbord weer te geven, zoals een numeriek toetsenbord, e-mailinvoer, telefooninvoer of URL-invoer. De component biedt ook ingebouwde validatie om waarden die niet voldoen aan het gespecificeerde type te weigeren.

## Usages {#usages}

De `TextField` is geschikt voor een breed scala aan scenario's waarin tekstinvoer of -bewerking vereist is. Hier zijn enkele voorbeelden van wanneer u de `TextField` kunt gebruiken:

1. **Formulieren**: Een `TextField` wordt vaak gebruikt in formulieren om gebruikersinvoer vast te leggen, zoals namen, adressen of opmerkingen. Het is het beste om een `TextField` te gebruiken in een app wanneer de invoer over het algemeen kort genoeg is om op één regel te passen.

2. **Zoekfunctionaliteit**: `TextField` kan worden gebruikt om een zoekvak te bieden, waarmee gebruikers zoekopdrachten kunnen invoeren.

3. **Tekstbewerking**: Een `TextField` is ideaal voor apps die tekstbewerking of contentcreatie vereisen, zoals document editors, chat-apps of notitie-apps.

## Types {#types}

U kunt het type van de TextField specificeren met de `setType()`-methode. Evenzo kunt u het type ophalen met de `getType()`-methode, die een enumwaarde retourneert.

- `Type.TEXT`: Dit is het standaardtype voor een `TextField` en verwijdert automatisch regelafbrekingen uit de invoerwaarde.

- `Type.EMAIL`: Dit type is voor het invoeren van e-mailadressen. Het valideert de invoer volgens de standaard e-mailsyntax. Bovendien biedt het compatibele browsers en apparaten een dynamisch toetsenbord dat het typen vereenvoudigt door veelgebruikte toetsen zoals <kbd>.com</kbd> en <kbd>@</kbd> op te nemen.

  :::note
  Terwijl deze validatie het formaat van het e-mailadres bevestigt, garandeert het niet dat het e-mailadres bestaat.
  :::

- `Type.TEL`: Dit type wordt gebruikt voor het invoeren van een telefoonnummer. Het veld toont op sommige apparaten met dynamische toetsenborden een telefoon-toetsenbord.

- `Type.URL`: Dit type is voor het invoeren van URL's. Het valideert of een gebruiker een URL invoert die een schema en een domeinnaam bevat, bijvoorbeeld: https://webforj.com. Bovendien biedt het compatibele browsers en apparaten een dynamisch toetsenbord dat het typen vereenvoudigt door veelgebruikte toetsen zoals <kbd>:</kbd>, <kbd>/</kbd>, en <kbd>.com</kbd> op te nemen.

- `Type.SEARCH`: Een tekstveld van één regel voor het invoeren van zoekopdrachten. Regelafbrekingen worden automatisch verwijderd uit de invoerwaarde.

<ComponentDemo 
path='/webforj/textfield?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/textfield/TextFieldView.java'
/>

## Veldwaarde {#field-value}

De waarde van een `TextField` vertegenwoordigt de huidige gebruikersinvoer als een string. In webforJ kan dit worden verkregen met de `getValue()`-methode, of programmatically worden bijgewerkt met `setValue(String)`.

```java
//Stel de initiële inhoud in
textField.setValue("Initiële inhoud");

//Haal de huidige waarde op
String text = textField.getValue();
```

Als de `getValue()`-methode wordt gebruikt op een veld zonder huidige waarde, retourneert het een lege string (`""`).

Dit gedrag is consistent met hoe het HTML `<input type="text">` element zijn waarde blootlegt via JavaScript.

:::tip Combineer waardeafhandeling met validatie
Pas beperkingen toe zoals een [patroon](#pattern-matching), [minimumlengte](#setminlength), of een [maximumlengte](#setmaxlength) om te definiëren wanneer een waarde als geldig wordt beschouwd.
:::

## Placeholdertekst {#placeholder-text}

U kunt placeholdertekst voor de `TextField` instellen met de `setPlaceholder()`-methode. De placeholdertekst wordt weergegeven wanneer het veld leeg is, helpt de gebruiker om geschikte invoer in de `TextField` in te voeren.

## Geselecteerde tekst {#selected-text}

Het is mogelijk om interactie te hebben met de `TextField`-klasse om de geselecteerde tekst van de gebruiker op te halen en informatie te krijgen over de selectie van de gebruiker. U kunt de geselecteerde tekst in de `TextField` ophalen met de `getSelectedText()`-methode. Dit gedrag zou vaak worden gebruikt in combinatie met een evenement.

```java
TextField textField = new TextField("Voer iets in...");
Button getSelectionBtn = new Button("Haal Geselecteerde Tekst Op");

getSelectionBtn.onClick(e -> {
  String selected = textField.getSelectedText();
  System.out.println("Geselecteerde tekst: " + selected);
});
```

Evenzo is het mogelijk om het huidige selectie bereik van de `TextField` op te halen met de `getSelectionRange()`-methode. Dit retourneert een `SelectionRange`-object dat de start- en eindindices van de geselecteerde tekst vertegenwoordigt.

:::tip Gebruik `getSelectedText()` vs evenement laad
Hoewel u `getSelectedText()` handmatig kunt aanroepen binnen een gebeurtenishandler, is het efficiënter om de selectiedata te gebruiken die in de payload van het evenement wordt geleverd—zoals in een `SelectionChangeEvent`—om extra opzoekingen te vermijden.
:::

## Patroon matching {#pattern-matching}

U kunt de `setPattern()`-methode gebruiken om een validatieregel voor de `TextField` te definiëren met behulp van een reguliere expressie. Het instellen van een patroon voegt een constraint validatie toe die vereist dat de invoerwaarde aan het opgegeven patroon voldoet.

Het patroon moet een geldige [JavaScript reguliere expressie](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_expressions) zijn, zoals geïnterpreteerd door de browser. De `u` (Unicode) vlag wordt intern toegepast om een nauwkeurige matching van Unicode-codepunten te waarborgen. Wikkel het patroon niet in schuine strepen (`/`), aangezien deze niet vereist zijn en als letterlijke tekens zullen worden behandeld.

```java
textField.setPattern("[A-Za-z]{3}\\d{2}"); // bijv. ABC12
```

Als er geen patroon wordt opgegeven, of de syntaxis ongeldig is, wordt de validatieregel genegeerd.

:::tip Contextuele hulp
Bij het gebruik van complexe patronen voor een `TextField`, overweeg om een combinatie van de `setLabel()`, `setHelperText()`, en `setTooltipText()` methoden te gebruiken om extra hints en begeleiding te bieden.
:::

## Minimum- en maximumlengte {#minimum-and-maximum-length}

De `TextField` component ondersteunt constraint validatie op basis van het aantal tekens dat door de gebruiker is ingevoerd. Dit kan worden gecontroleerd met de `setMinLength()` en `setMaxLength()` methoden. Gebruik beide methoden om een duidelijke grens te definiëren voor aanvaardbare invoerlengtes.

:::info Lengtevereisten
Standaard geeft het veld een bericht weer wanneer de invoerwaarde buiten het bereik ligt, waarin aan de gebruiker wordt aangegeven of ze hun invoer moeten verkorten of verlengen. Dit bericht kan worden vervangen door de `setInvalidMessage()`-methode.
:::

### `setMinLength()` {#setminlength}

Deze methode stelt het **minimum aantal UTF-16-code-eenheden** in dat moet worden ingevoerd om het veld als geldig te beschouwen. De waarde moet een geheel getal zijn en mag de geconfigureerde maximumlengte niet overschrijden.

```java
textField.setMinLength(5); // De gebruiker moet minimaal 5 tekens invoeren
```

Als de invoer minder tekens bevat dan het minimum vereist, zal de invoer falen voor constraint validatie. Deze regel geldt alleen wanneer de gebruiker de waarde van het veld wijzigt.

### `setMaxLength()` {#setmaxlength}

Deze methode stelt het **maximum aantal UTF-16-code-eenheden** in die in het tekstveld zijn toegestaan. De waarde moet `0` of groter zijn. Als dit niet is ingesteld, of ingesteld op een ongeldige waarde, wordt er geen maximum afgedwongen.

```java
textField.setMaxLength(20); // De gebruiker kan niet meer dan 20 tekens invoeren
```

Het veld faalt bij constraint validatie als de invoerlengte de minimumlengte overschrijdt. Net als bij `setMinLength()` wordt deze validatie alleen geactiveerd wanneer de waarde door de gebruiker is gewijzigd.

## Beste praktijken {#best-practices}

De volgende sectie schetst enkele aanbevolen beste praktijken voor het gebruik van de `TextField`.

- **Geef Duidelijke Labels en Instructies**: Label de `TextField` duidelijk om aan te geven welk type informatie gebruikers moeten invoeren. Geef aanvullende instructietekst of placeholderwaarden om gebruikers te begeleiden en verwachtingen te scheppen voor de vereiste invoer.

- **Spellingcontrole en Auto-Voltooiing**: Wanneer van toepassing, overweeg dan om spellingcontrole te gebruiken met `setSpellCheck()` en/of auto-voltooiing te gebruiken in een `TextField`. Deze functies kunnen gebruikers helpen informatie sneller in te voeren en fouten te verminderen door voorgestelde waarden op basis van eerdere invoer of vooraf gedefinieerde opties te bieden.

- **Toegankelijkheid**: Gebruik de `TextField` component met toegankelijkheid in gedachten, en houd u aan toegankelijkheidsnormen zoals correcte labeling, ondersteuning voor toetsenbordnavigatie en compatibiliteit met hulpmiddelen voor mensen met een handicap. Zorg ervoor dat gebruikers met een handicap effectief kunnen interageren met de `TextField`.
