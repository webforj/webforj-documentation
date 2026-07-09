---
sidebar_position: 35
title: TextField
slug: textfield
description: A single-line input component for entering and editing text data.
_i18n_hash: 18d2a614ed2e9c53513a92017b3622e0
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-field" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/field/TextField" top='true'/>

De `TextField` component stelt gebruikers in staat om tekst in te voeren en te bewerken in een enkele regel. Je kunt het veld configureren om een specifiek virtueel toetsenbord weer te geven, zoals een numeriek toetsenbord, emailinvoer, telefooninvoer of URL-invoer. De component biedt ook ingebouwde validatie om waarden te verwerpen die niet voldoen aan het gespecificeerde type.

<!-- INTRO_END -->

## Usages {#usages}

<ParentLink parent="Field" />

De `TextField` is geschikt voor een breed scala aan scenario's waarin tekstinvoer of -bewerking vereist is. Hier zijn enkele voorbeelden van wanneer je de `TextField` kunt gebruiken:

1. **Formulieren**: Een `TextField` wordt vaak gebruikt in formulieren voor het vastleggen van gebruikersinvoer, zoals namen, adressen of opmerkingen. Het is het beste om een `TextField` in een app te gebruiken wanneer de invoer over het algemeen kort genoeg is om op één regel te passen.

2. **Zoekfunctionaliteit**: `TextField` kan worden gebruikt om een zoekvak te bieden, zodat gebruikers zoekopdrachten kunnen invoeren.

3. **Tekstbewerking**: Een `TextField` is ideaal voor apps die tekstbewerking of contentcreatie vereisen, zoals documentbewerkers, chatapps of notitie-apps.

## Types {#types}

Je kunt het type van de TextField specificeren met de `setType()` methode. Evenzo kun je het type ophalen met de `getType()` methode, die een enumwaarde retourneert.

- `Type.TEXT`: Dit is het standaardtype voor een `TextField` en verwijdert automatisch regeleinden uit de invoerwaarde.

- `Type.EMAIL`: Dit type is voor het invoeren van e-mailadressen. Het valideert de invoer volgens de standaard e-mailsyntaxis. Bovendien biedt het compatibele browsers en apparaten een dynamisch toetsenbord dat het typen vergemakkelijkt door veelgebruikte toetsen zoals <kbd>.com</kbd> en <kbd>@</kbd> op te nemen.

  :::note
  Hoewel deze validatie het formaat van het e-mailadres bevestigt, garandeert het niet dat het e-mailadres bestaat.
  :::

- `Type.TEL`: Dit type wordt gebruikt voor het invoeren van een telefoonnummer. Het veld toont op sommige apparaten met dynamische toetsenborden een telefoon.toetsenbord.

- `Type.URL`: Dit type is voor het invoeren van URL's. Het valideert of een gebruiker een URL invoert die een schema en een domeinnaam bevat, bijvoorbeeld: https://webforj.com. Bovendien biedt het compatibele browsers en apparaten een dynamisch toetsenbord dat het typen vergemakkelijkt door veelgebruikte toetsen zoals <kbd>:</kbd>, <kbd>/</kbd>, en <kbd>.com</kbd> op te nemen.

- `Type.SEARCH`: Een tekstveld met één regel voor het invoeren van zoekstrings. Regeleinden worden automatisch verwijderd uit de invoerwaarde.

<ComponentDemo
path='/webforj/textfield'
files={['src/main/java/com/webforj/samples/views/fields/textfield/TextFieldView.java']}
height='250px'
/>

## Field value {#field-value}

De waarde van een `TextField` vertegenwoordigt de huidige gebruikersinvoer als een string. In webforJ kun je dit bereiken met de `getValue()` methode of programmatisch bijwerken met `setValue(String)`.

```java
//Stel de initiële inhoud in
textField.setValue("Initiële inhoud");

//Haal de huidige waarde op
String text = textField.getValue();
```

Als de `getValue()`-methode wordt gebruikt op een veld zonder huidige waarde, retourneert deze een lege string (`""`).

Dit gedrag is consistent met hoe het HTML `<input type="text">` element zijn waarde via JavaScript exposeert.

:::tip Combineer waarde-afhandeling met validatie
Pas beperkingen zoals een [patroon](#pattern-matching), [minimum lengte](#setminlength), of een [maximum lengte](#setmaxlength) toe om te definiëren wanneer een waarde als geldig wordt beschouwd.
:::

## Placeholder text {#placeholder-text}

Je kunt een placeholder-tekst instellen voor de `TextField` met de `setPlaceholder()` methode. De placeholder-tekst wordt weergegeven wanneer het veld leeg is, wat helpt om de gebruiker te stimuleren om geschikte invoer in de `TextField` in te voeren.

## Selected text {#selected-text}

Het is mogelijk om te interageren met de `TextField` klasse om de geselecteerde tekst van een gebruiker op te halen en informatie over de selectie van de gebruiker te krijgen. Je kunt de geselecteerde tekst in de `TextField` ophalen met de `getSelectedText()` methode. Dit gedrag wordt doorgaans gebruikt in combinatie met een gebeurtenis.

```java
TextField textField = new TextField("Voer iets in...");
Button getSelectionBtn = new Button("Haal Geselecteerde Tekst Op");

getSelectionBtn.onClick(e -> {
  String selected = textField.getSelectedText();
  System.out.println("Geselecteerde tekst: " + selected);
});
```

Op dezelfde manier is het mogelijk om het huidige selectiebereik van de `TextField` op te halen met de `getSelectionRange()` methode. Dit retourneert een `SelectionRange` object dat de start- en eindindexen van de geselecteerde tekst vertegenwoordigt.

:::tip Gebruik `getSelectedText()` vs gebeurtenispayload
Hoewel je `getSelectedText()` handmatig binnen een gebeurtenishandler kunt aanroepen, is het efficiënter om de selectiedata te gebruiken die in de payload van de gebeurtenis worden geleverd—zoals in een `SelectionChangeEvent`—om extra zoekopdrachten te vermijden.
:::

## Pattern matching {#pattern-matching}

Je kunt de `setPattern()` methode gebruiken om een validatieregel voor de `TextField` te definiëren met behulp van een reguliere expressie. Het instellen van een patroon voegt een constraint-validatie toe die vereist dat de invoerwaarde overeenkomt met het gespecificeerde patroon.

Het patroon moet een geldige [JavaScript reguliere expressie](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_expressions) zijn, zoals geïnterpreteerd door de browser. De `u` (Unicode) vlag wordt intern toegepast om een nauwkeurige matching van Unicode-codepunten te waarborgen. Wikkel het patroon niet in schuine strepen (`/`), aangezien deze niet vereist zijn en als letterlijke tekens worden behandeld.

```java
textField.setPattern("[A-Za-z]{3}\\d{2}"); // bijv. ABC12
```

Als er geen patroon wordt opgegeven of de syntaxis ongeldig is, wordt de validatieregel genegeerd.

:::tip Contextuele hulp
Wanneer je complexe patronen voor een `TextField` gebruikt, overweeg dan het gebruik van een combinatie van de `setLabel()`, `setHelperText()` en `setTooltipText()` methoden
om extra hints en begeleiding te bieden.
:::

## Minimum and maximum length {#minimum-and-maximum-length}

De `TextField` component ondersteunt constraint-validatie op basis van het aantal karakters dat door de gebruiker is ingevoerd. Dit kan worden geregeld met de `setMinLength()` en `setMaxLength()` methoden. Gebruik beide methoden om een duidelijke grens te definiëren voor aanvaardbare invoerlengtes.

:::info Lengte-eisen
Standaard toont het veld een bericht wanneer de invoerwaarde buiten bereik is, waarbij aan de gebruiker wordt aangegeven of ze hun invoer moeten verkorten of verlengen. Dit bericht kan worden overschreven met de `setInvalidMessage()` methode.
:::

### `setMinLength()` {#setminlength}

Deze methode stelt het **minimum aantal UTF-16 code-eenheden** in dat moet worden ingevoerd om het veld als geldig te beschouwen. De waarde moet een geheel getal zijn en mag de geconfigureerde maximale lengte niet overschrijden.

```java
textField.setMinLength(5); // Gebruiker moet minstens 5 karakters invoeren
```

Als de invoer minder karakters bevat dan het minimumvereiste, zal de invoer falen voor constraint-validatie. Deze regel geldt alleen wanneer de gebruiker de waarde van het veld wijzigt.

### `setMaxLength()` {#setmaxlength}

Deze methode stelt het **maximum aantal UTF-16 code-eenheden** in dat is toegestaan in het tekstveld. De waarde moet `0` of groter zijn. Als deze niet is ingesteld of op een ongeldige waarde is ingesteld, wordt er geen maximum afgedwongen.

```java
textField.setMaxLength(20); // Gebruiker kan niet meer dan 20 karakters invoeren
```

Het veld faalt voor constraint-validatie als de invoerlengte de maximale lengte overschrijdt. Net als bij `setMinLength()` wordt deze validatie alleen geactiveerd wanneer de waarde door de gebruiker wordt gewijzigd.

## Best practices {#best-practices}

Het volgende gedeelte schetst enkele aanbevolen best practices voor het gebruik van de `TextField`.

- **Bied Duidelijke Labels en Instructies**: Label de `TextField` duidelijk om aan te geven welk type informatie gebruikers moeten invoeren. Bied aanvullende instructietekst of placeholder-waarden om gebruikers te begeleiden en verwachtingen te scheppen voor de vereiste invoer.

- **Spellingcontrole en Auto-Compleet**: Overweeg, waar van toepassing, het gebruik van spellingcontrole met `setSpellCheck()` en/of het gebruik van auto-complete in een `TextField`. Deze functies kunnen gebruikers helpen informatie sneller in te voeren en fouten te verminderen door voorgestelde waarden op basis van eerdere invoer of vooraf gedefinieerde opties te bieden.

- **Toegankelijkheid**: Gebruik de `TextField` component met toegankelijkheid in gedachten, in overeenstemming met toegankelijkheidsnormen zoals juiste labeling, ondersteuning voor toetsenbordnavigatie en compatibiliteit met ondersteunende technologieën. Zorg ervoor dat gebruikers met een handicap effectief interactie kunnen hebben met de `TextField`.
