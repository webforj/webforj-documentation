---
sidebar_position: 35
title: TextField
slug: textfield
description: A single-line input component for entering and editing text data.
_i18n_hash: 0d350dce3bf30a09bc54f089107b7a25
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-field" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/field/TextField" top='true'/>

De `TextField` component stelt gebruikers in staat om tekst in te voeren en te bewerken in een enkele regel. Je kunt het veld configureren om een specifiek virtueel toetsenbord weer te geven, zoals een numeriek toetsenbord, e-mailinvoer, telefooninvoer of URL-invoer. De component biedt ook ingebouwde validatie om waarden te weigeren die niet voldoen aan het gespecificeerde type.

<!-- INTRO_END -->

## Gebruik {#usages}

<ParentLink parent="Field" />

De `TextField` is geschikt voor een breed scala aan scenario's waarin tekstinvoer of -bewerking vereist is. Hier zijn enkele voorbeelden van wanneer je de `TextField` kunt gebruiken:

1. **Formuliervelden**: Een `TextField` wordt vaak gebruikt in formulieren om gebruikersinvoer vast te leggen, zoals namen, adressen of opmerkingen. Het is het beste om een `TextField` in een app te gebruiken wanneer de invoer doorgaans kort genoeg is om op één regel te passen.

2. **Zoekfunctionaliteit**: De `TextField` kan worden gebruikt om een zoekvak te bieden, zodat gebruikers zoekopdrachten kunnen invoeren.

3. **Tekstbewerking**: Een `TextField` is ideaal voor apps die tekstbewerking of inhoudcreatie vereisen, zoals documenteditors, chat-apps of notitie-apps.

## Typen {#types}

Je kunt het type van de `TextField` specificeren met de `setType()` methode. Op dezelfde manier kun je het type ophalen met de `getType()` methode, die een enumwaarde retourneert.

- `Type.TEXT`: Dit is het standaardtype voor een `TextField` en verwijdert automatisch regeleinden uit de invoerwaarde.

- `Type.EMAIL`: Dit type is voor het invoeren van e-mailadressen. Het valideert de invoer volgens de standaard e-mailsyntax. Bovendien biedt het compatibele browsers en apparaten een dynamisch toetsenbord dat het typen vergemakkelijkt door veelgebruikte toetsen zoals <kbd>.com</kbd> en <kbd>@</kbd> op te nemen.

  :::note
  Hoewel deze validatie het formaat van het e-mailadres bevestigt, garandeert het niet dat het e-mailadres bestaat.
  :::

- `Type.TEL`: Dit type wordt gebruikt voor het invoeren van een telefoonnummer. Het veld toont op sommige apparaten een telefoon-toetsenbord met dynamische toetsen.

- `Type.URL`: Dit type is voor het invoeren van URL's. Het valideert of een gebruiker een URL invoert die een schema en een domeinnaam bevat, bijvoorbeeld: https://webforj.com. Bovendien biedt het compatibele browsers en apparaten een dynamisch toetsenbord dat het typen vergemakkelijkt door veelgebruikte toetsen zoals <kbd>:</kbd>, <kbd>/</kbd>, en <kbd>.com</kbd> op te nemen.

- `Type.SEARCH`: Een enkel-regel tekstveld voor het invoeren van zoektermen. Regeleinden worden automatisch uit de invoerwaarde verwijderd.

<ComponentDemo
path='/webforj/textfield'
files={['src/main/java/com/webforj/samples/views/fields/textfield/TextFieldView.java']}
height='250px'
/>

## Veldwaarde {#field-value}

De waarde van een `TextField` vertegenwoordigt de huidige gebruikersinvoer als een string. In webforJ kan dit worden verkregen met de `getValue()` methode, of programmatisch worden bijgewerkt met `setValue(String)`.

```java
//Stel de initiële inhoud in
textField.setValue("Initiële inhoud");

//Haal de huidige waarde op
String text = textField.getValue();
```

Als de `getValue()` methode wordt gebruikt op een veld zonder een huidige waarde, retourneert het een lege string (`""`).

Dit gedrag is consistent met hoe het HTML `<input type="text">` element zijn waarde via JavaScript blootlegt.

:::tip Combineer waarde-afhandeling met validatie
Pas beperkingen toe zoals een [patroon](#pattern-matching), [minimumlengte](#setminlength), of een [maximumlengte](#setmaxlength) om te definiëren wanneer een waarde als geldig wordt beschouwd.
:::

## Plaatsvervangende tekst {#placeholder-text}

Je kunt plaatsvervangende tekst instellen voor de `TextField` met behulp van de `setPlaceholder()` methode. De plaatsvervangende tekst wordt weergegeven wanneer het veld leeg is, en helpt om de gebruiker te stimuleren om geschikte invoer in de `TextField` in te voeren.

## Geselecteerde tekst {#selected-text}

Het is mogelijk om te interageren met de `TextField` klasse om de geselecteerde tekst van de gebruiker te verkrijgen en informatie over de selectie van de gebruiker op te halen. Je kunt de geselecteerde tekst in de `TextField` ophalen met behulp van de `getSelectedText()` methode. Dit gedrag wordt vaak in combinatie met een evenement gebruikt.

```java
TextField textField = new TextField("Voer iets in...");
Button getSelectionBtn = new Button("Haal Geselecteerde Tekst Op");

getSelectionBtn.onClick(e -> {
  String selected = textField.getSelectedText();
  System.out.println("Geselecteerde tekst: " + selected);
});
```

Evenzo is het mogelijk om het huidige selectiegebied van de `TextField` op te halen met behulp van de `getSelectionRange()` methode. Dit retourneert een `SelectionRange` object dat de start- en eindindices van de geselecteerde tekst vertegenwoordigt.

:::tip Waarde veranderingen en selectie
Gebruik `onValueChange()` om veranderingen in de waarde van het veld te verwerken. De `ValueChangeEvent<String>` biedt de nieuwe waarde via `event.getValue()`, maar bevat geen selectiedata. Om de huidige selectie te lezen, roep je `getSelectedText()` of `getSelectionRange()` aan op het veld.
:::

## Patroonherkenning {#pattern-matching}

Je kunt de `setPattern()` methode gebruiken om een validatieregel voor de `TextField` te definiëren met behulp van een reguliere expressie. Het instellen van een patroon voegt een beperking validatie toe die vereist dat de invoerwaarde overeenkomt met het gespecificeerde patroon.

Het patroon moet een geldige [JavaScript reguliere expressie](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_expressions) zijn, zoals geïnterpreteerd door de browser. De `u` (Unicode) vlag wordt intern toegepast om nauwkeurige overeenstemming van Unicode-codepunten te garanderen. Wikkel het patroon niet in schuine strepen (`/`), omdat deze niet vereist zijn en als letterlijke tekens worden behandeld.

```java
textField.setPattern("[A-Za-z]{3}\\d{2}"); // bv. ABC12
```

Als er geen patroon is opgegeven, of de syntaxis ongeldig is, wordt de validatieregel genegeerd.

:::tip Contextuele help
Bij het gebruik van complexe patronen voor een `TextField`, overweeg om een combinatie van de `setLabel()`, `setHelperText()`, en `setTooltipText()` methoden te gebruiken om aanvullende hints en begeleiding te bieden.
:::

## Minimale en maximale lengte {#minimum-and-maximum-length}

De `TextField` component ondersteunt beperking validatie op basis van het aantal door de gebruiker ingevoerde karakters. Dit kan worden gecontroleerd met behulp van de `setMinLength()` en `setMaxLength()` methoden. Gebruik beide methoden om een duidelijke grens voor acceptabele invoerlengtes te definiëren.

:::info Lengtevereisten
Standaard geeft het veld een bericht weer wanneer de invoerwaarde buiten het bereik ligt, waarmee de gebruiker wordt geïnformeerd of ze hun invoer moeten inkorten of verlengen. Dit bericht kan worden overschreven met de `setInvalidMessage()` methode.
:::

### `setMinLength()` {#setminlength}

Deze methode stelt het **minimum aantal UTF-16 code-eenheden** in dat moet worden ingevoerd voor het veld om als geldig te worden beschouwd. De waarde moet een heel getal zijn en mag de geconfigureerde maximale lengte niet overschrijden.

```java
textField.setMinLength(5); // De gebruiker moet ten minste 5 tekens invoeren
```

Als de invoer minder karakters bevat dan het minimum vereist, faalt de invoer de beperking validatie. Deze regel geldt alleen wanneer de gebruiker de waarde van het veld verandert.

### `setMaxLength()` {#setmaxlength}

Deze methode stelt het **maximum aantal UTF-16 code-eenheden** in dat is toegestaan in het tekstveld. De waarde moet `0` of groter zijn. Als het niet is ingesteld, of is ingesteld op een ongeldig waarde, wordt er geen maximum afgedwongen.

```java
textField.setMaxLength(20); // De gebruiker kan niet meer dan 20 tekens invoeren
```

Het veld faalt de beperking validatie als de invoerlengte de minimale lengte overschrijdt. Zoals bij `setMinLength()`, wordt deze validatie alleen geactiveerd wanneer de waarde door de gebruiker wordt gewijzigd.

## Beste praktijken {#best-practices}

De volgende sectie schetst enkele aanbevolen beste praktijken voor het gebruik van de `TextField`.

- **Zorg voor Duidelijke Labels en Instructies**: Label de `TextField` duidelijk om aan te geven welk type informatie gebruikers moeten invoeren. Bied aanvullende instructietekst of plaatsvervangende waarden om gebruikers te begeleiden en verwachtingen te scheppen voor de vereiste invoer.

- **Spellingcontrole en Auto-aanvulling**: Wanneer van toepassing, overweeg dan om spellingcontrole te gebruiken met `setSpellCheck()` en/of auto-aanvulling in een `TextField`. Deze functies kunnen gebruikers helpen informatie sneller in te voeren en fouten te verminderen door voorgestelde waarden te bieden op basis van eerdere invoer of vooraf gedefinieerde opties.

- **Toegankelijkheid**: Zorg voor duidelijke labels, navigatie via het toetsenbord en ondersteuning voor hulpmiddelen, zodat gebruikers met een handicap het veld kunnen gebruiken.
