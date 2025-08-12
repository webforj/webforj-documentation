---
sidebar_position: 35
title: TextField
slug: textfield
description: A single-line input component for entering and editing text data.
sidebar_class_name: updated-content
_i18n_hash: 71ebfc077bb8042752cbfea71a971e47
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-field" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/field/TextField" top='true'/>

<ParentLink parent="Field" />

De `TextField` component stelt gebruikers in staat om tekst in te voeren en te bewerken in een enkele regel. Je kunt het veld configureren om een specifiek virtueel toetsenbord weer te geven, zoals een numeriek toetsenbord, e-mailinvoer, telefooninvoer of URL-invoer. De component biedt ook ingebouwde validatie om waarden te verwerpen die niet voldoen aan het gespecificeerde type.

## Usages {#usages}

De `TextField` is geschikt voor een breed scala aan scenario's waar tekstinvoer of -bewerking vereist is. Hier zijn enkele voorbeelden van wanneer je de `TextField` kunt gebruiken:

1. **Formulierinvoer**: Een `TextField` wordt vaak gebruikt in formulieren om gebruikersinvoer vast te leggen, zoals namen, adressen of opmerkingen. Het is het beste om een `TextField` in een app te gebruiken wanneer de invoer over het algemeen kort genoeg is om op één regel te passen.

2. **Zoekfunctionaliteit**: `TextField` kan worden gebruikt om een zoekbox te bieden, waarmee gebruikers zoekopdrachten kunnen invoeren.

3. **Tekstbewerking**: Een `TextField` is ideaal voor apps die vereisen dat tekst wordt bewerkt of inhoud wordt gemaakt, zoals documenteditors, chat-apps of notitie-apps.

## Types {#types}

Je kunt het type van de TextField specificeren met de `setType()` methode. Op dezelfde manier kun je het type ophalen met de `getType()` methode, die een enumwaarde retourneert.

- `Type.TEXT`: Dit is het standaardtype voor een `TextField` en verwijdert automatisch regeleinden uit de invoerwaarde.

- `Type.EMAIL`: Dit type is voor het invoeren van e-mailadressen. Het valideert de invoer volgens de standaard e-mailsyntaxis. Bovendien biedt het compatibele browsers en apparaten een dynamisch toetsenbord dat het typen vereenvoudigt door vaak gebruikte toetsen zoals <kbd>.com</kbd> en <kbd>@</kbd> op te nemen.

  :::note
  Hoewel deze validatie het formaat van het e-mailadres bevestigt, garandeert het niet dat het e-mailadres bestaat.
  :::

- `Type.TEL`: Dit type wordt gebruikt voor het invoeren van een telefoonnummer. Het veld toont een telefonisch toetsenbord op sommige apparaten met dynamische toetsenborden.

- `Type.URL`: Dit type is voor het invoeren van URL's. Het valideert of een gebruiker een URL invoert die een schema en een domeinnaam bevat, bijvoorbeeld: https://webforj.com. Bovendien biedt het compatibele browsers en apparaten een dynamisch toetsenbord dat het typen vereenvoudigt door vaak gebruikte toetsen zoals <kbd>:</kbd>, <kbd>/</kbd> en <kbd>.com</kbd> op te nemen.

- `Type.SEARCH`: Een tekstveld van één regel voor het invoeren van zoekopdrachten. Regelonderbrekingen worden automatisch verwijderd uit de invoerwaarde.

<ComponentDemo 
path='/webforj/textfield?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/textfield/TextFieldView.java'
/>

## Veldwaarde {#field-value}

De waarde van een `TextField` vertegenwoordigt de huidige gebruikersinvoer als een string. In webforJ kan dit worden geopend met de `getValue()` methode, of programatisch worden bijgewerkt met `setValue(String)`.

```java
//Stel de initiële inhoud in
textField.setValue("Initiële inhoud");

//Haal de huidige waarde op
String text = textField.getValue();
```

Als de `getValue()` methode wordt gebruikt op een veld zonder een huidige waarde, retourneert het een lege string (`""`).

Dit gedrag is consistent met hoe het HTML `<input type="text">` element zijn waarde blootstelt via JavaScript.

:::tip Combineer waarde-afhandeling met validatie
Pas beperkingen toe zoals een [patroon](#pattern-matching), [minimumlengte](#setminlength), of een [maximumlengte](#setmaxlength) om te definiëren wanneer een waarde als geldig wordt beschouwd. 
:::

## Plaatsvervangende tekst {#placeholder-text}

Je kunt plaatsvervangende tekst instellen voor de `TextField` met de `setPlaceholder()` methode. De plaatsvervangende tekst wordt weergegeven wanneer het veld leeg is, wat helpt om de gebruiker te stimuleren om geschikte invoer in de `TextField` in te voeren.

## Geselecteerde tekst {#selected-text}

Het is mogelijk om te interageren met de `TextField` klasse om de geselecteerde tekst van een gebruiker op te halen en informatie over de selectie van de gebruiker te krijgen. Je kunt de geselecteerde tekst in de `TextField` ophalen met de `getSelectedText()` methode. Dit gedrag zou doorgaans worden gebruikt in combinatie met een gebeurtenis. 

```java
TextField textField = new TextField("Voer iets in...");
Button getSelectionBtn = new Button("Haal Geselecteerde Tekst Op");

getSelectionBtn.onClick(e -> {
  String selected = textField.getSelectedText();
  System.out.println("Geselecteerde tekst: " + selected);
});
```

Evenzo is het mogelijk om het huidige selectiegebied van de `TextField` op te halen met de `getSelectionRange()` methode. Dit retourneert een `SelectionRange` object dat de begin- en eindindexen van de geselecteerde tekst vertegenwoordigt.

:::tip Gebruik `getSelectedText()` vs gebeurtenislasten
Hoewel je `getSelectedText()` handmatig kunt aanroepen binnen een gebeurtenishandler, is het efficiënter om de selectiedata te gebruiken die in de payload van de gebeurtenis wordt verstrekt—zoals in een `SelectionChangeEvent`—om aanvullende opzoekingen te vermijden.
:::

## Patroonmatching {#pattern-matching}

Je kunt de `setPattern()` methode gebruiken om een validatieregel voor de `TextField` te definiëren met behulp van een reguliere expressie. Een patroon instellen voegt een beperkingvalidatie toe die vereist dat de invoerwaarde overeenkomt met het gespecificeerde patroon.

Het patroon moet een geldige [JavaScript reguliere expressie](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_expressions) zijn, zoals geïnterpreteerd door de browser. De `u` (Unicode) vlag wordt intern toegepast om een nauwkeurige matching van Unicode-codepunten te garanderen. Verpak het patroon niet in een schuine streep (`/`), aangezien deze niet vereist zijn en als letterlijke tekens worden behandeld.

```java
textField.setPattern("[A-Za-z]{3}\\d{2}"); // bijv. ABC12
```

Als er geen patroon is opgegeven, of als de syntaxis ongeldig is, wordt de validatieregel genegeerd.

:::tip Contextuele hulp
Bij het gebruik van complexe patronen voor een `TextField`, overweeg het gebruik van een combinatie van de `setLabel()`, `setHelperText()`, en `setTooltipText()` methoden
om aanvullende aanwijzingen en richtlijnen te geven.
:::

## Minimum- en maximumlengte {#minimum-and-maximum-length}

De `TextField` component ondersteunt beperkingvalidatie op basis van het aantal tekens dat door de gebruiker is ingevoerd. Dit kan worden gecontroleerd met de `setMinLength()` en `setMaxLength()` methoden. Gebruik beide methoden om een duidelijke grens van aanvaardbare invoerlengtes te definiëren.

:::info Lengte-eisen
Standaard toont het veld een bericht wanneer de invoerwaarde buiten bereik is, waarmee aan de gebruiker wordt aangegeven of ze hun invoer moeten verkorten of verlengen. Dit bericht kan worden overschreven met de `setInvalidMessage()` methode.
:::

### `setMinLength()` {#setminlength}

Deze methode stelt het **minimale aantal UTF-16 code-eenheden** in dat moet worden ingevoerd zodat het veld als geldig wordt beschouwd. De waarde moet een geheel getal zijn en mag de geconfigureerde maximumlengte niet overschrijden.

```java
textField.setMinLength(5); // Gebruiker moet minstens 5 tekens invoeren
```

Als de invoer minder tekens bevat dan het minimale vereiste, mislukt de invoer de beperkingvalidatie. Deze regel is alleen van toepassing wanneer de gebruiker de waarde van het veld wijzigt.

### `setMaxLength()` {#setmaxlength}

Deze methode stelt het **maximale aantal UTF-16 code-eenheden** in dat is toegestaan in het tekstveld. De waarde moet `0` of groter zijn. Als deze niet is ingesteld, of is ingesteld op een ongeldige waarde, wordt er geen maximum afgedwongen.

```java
textField.setMaxLength(20); // Gebruiker kan niet meer dan 20 tekens invoeren
```

Het veld faalt de beperkingvalidatie als de invoerlengte de minimumlengte overschrijdt. Net als bij `setMinLength()` wordt deze validatie alleen geactiveerd wanneer de waarde door de gebruiker wordt gewijzigd.

## Best practices {#best-practices}

De volgende sectie beschrijft enkele aanbevolen best practices voor het gebruik van de `TextField`.

- **Bied Duidelijke Labels en Instructies**: Label de `TextField` duidelijk om aan te geven welk type informatie gebruikers moeten invoeren. Bied aanvullende instructietekst of plaatsvervangende waarden om gebruikers te begeleiden en verwachtingen te scheppen voor de vereiste invoer.

- **Spellingscontrole en Auto-Complete**: Wanneer van toepassing, overweeg het gebruik van spellingscontrole met `setSpellCheck()` en/of het gebruik van auto-complete in een `TextField`. Deze functies kunnen gebruikers helpen informatie sneller in te voeren en fouten te verminderen door voorgestelde waarden te bieden op basis van eerdere invoer of vooraf gedefinieerde opties.

- **Toegankelijkheid**: Gebruik de `TextField` component met toegankelijkheid in gedachten, volgens toegankelijkheidsnormen zoals juiste labeling, ondersteuning voor navigatie met toetsenborden, en compatibiliteit met hulpmiddelen voor mensen met een handicap. Zorg ervoor dat gebruikers met een beperking effectief met de `TextField` kunnen interageren.
