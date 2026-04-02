---
sidebar_position: 35
title: TextField
slug: textfield
description: A single-line input component for entering and editing text data.
_i18n_hash: 138207c2dd99dac9837172067950ab2f
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-field" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/field/TextField" top='true'/>

Het `TextField` component stelt gebruikers in staat om tekst in te voeren en te bewerken in een enkele regel. Je kunt het veld configureren om een specifiek virtueel toetsenbord weer te geven, zoals een numeriek toetsenbord, e-mailinvoer, telefooninvoer of URL-invoer. Het component biedt ook ingebouwde validatie om waarden af te wijzen die niet voldoen aan het gespecificeerde type.

<!-- INTRO_END -->

## Usages {#usages}

<ParentLink parent="Field" />

Het `TextField` is geschikt voor een breed scala aan scenario's waarin tekstinvoer of -bewerking vereist is. Hier zijn enkele voorbeelden van wanneer je het `TextField` kunt gebruiken:

1. **Formulierinvoer**: Een `TextField` wordt vaak gebruikt in formulieren voor het vastleggen van gebruikersinvoer, zoals namen, adressen of opmerkingen. Het is het beste om een `TextField` in een app te gebruiken wanneer de invoer doorgaans kort genoeg is om op één regel te passen.

2. **Zoekfunctionaliteit**: Het `TextField` kan worden gebruikt om een zoekvak te bieden, waarmee gebruikers zoekopdrachten kunnen invoeren.

3. **Tekstbewerking**: Een `TextField` is ideaal voor apps die tekstbewerking of inhoudcreatie vereisen, zoals documenteditors, chat-apps of notitie-apps.

## Types {#types}

Je kunt het type van het TextField specificeren met behulp van de `setType()` methode. Evenzo kun je het type ophalen met de `getType()` methode, die een enum-waarde retourneert.

- `Type.TEXT`: Dit is het standaardtype voor een `TextField` en verwijdert automatisch regeleinden uit de invoerwaarde.

- `Type.EMAIL`: Dit type is voor het invoeren van e-mailadressen. Het valideert de invoer volgens de standaard e-mailsyntaxis. Bovendien biedt het compatibele browsers en apparaten een dynamisch toetsenbord dat het typen vereenvoudigt door veelgebruikte toetsen zoals <kbd>.com</kbd> en <kbd>@</kbd> op te nemen.

  :::note
  Hoewel deze validatie het formaat van het e-mailadres bevestigt, garandeert het niet dat het e-mailadres bestaat.
  :::

- `Type.TEL`: Dit type wordt gebruikt voor het invoeren van een telefoonnummer. Het veld zal op sommige apparaten een telefoon-toetsenbord weergeven met dynamische toetsenborden.

- `Type.URL`: Dit type is voor het invoeren van URLs. Het valideert of een gebruiker een URL invoert die een schema en een domeinnaam bevat, bijvoorbeeld: https://webforj.com. Bovendien biedt het compatibele browsers en apparaten een dynamisch toetsenbord dat het typen vereenvoudigt door veelgebruikte toetsen zoals <kbd>:</kbd>, <kbd>/</kbd> en <kbd>.com</kbd> op te nemen.

- `Type.SEARCH`: Een enkele tekenregel voor het invoeren van zoektermen. Regelbreuken worden automatisch uit de invoerwaarde verwijderd.

<ComponentDemo 
path='/webforj/textfield?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/textfield/TextFieldView.java'
height='225px'
/>

## Field value {#field-value}

De waarde van een `TextField` vertegenwoordigt de huidige gebruikersinvoer als een string. In webforJ kan dit worden toegankelijk gemaakt met de `getValue()` methode, of programmatisch worden bijgewerkt met `setValue(String)`.

```java
// Stel de initiële inhoud in
textField.setValue("Initiële inhoud");

// Haal de huidige waarde op
String text = textField.getValue();
```

Als de `getValue()` methode wordt gebruikt op een veld zonder huidige waarde, retourneert het een lege string (`""`).

Dit gedrag is consistent met hoe het HTML `<input type="text">` element zijn waarde via JavaScript blootlegt.

:::tip Combineer waarde-afhandeling met validatie
Pas beperkingen toe zoals een [patroon](#pattern-matching), [minimale lengte](#setminlength), of een [maximale lengte](#setmaxlength) om te definiëren wanneer een waarde als geldig wordt beschouwd. 
:::

## Placeholder text {#placeholder-text}

Je kunt placeholdertekst voor het `TextField` instellen met de `setPlaceholder()` methode. De placeholdertekst wordt weergegeven wanneer het veld leeg is, en helpt de gebruiker eraan te herinneren om geschikte invoer in het `TextField` in te voeren.

## Selected text {#selected-text}

Het is mogelijk om te interageren met de `TextField` klasse om de geselecteerde tekst van een gebruiker op te halen, en om informatie te krijgen over de selectie van de gebruiker. Je kunt de geselecteerde tekst in het `TextField` ophalen met de `getSelectedText()` methode. Dit gedrag zou vaak in combinatie met een gebeurtenis worden gebruikt.

```java
TextField textField = new TextField("Voer iets in...");
Button getSelectionBtn = new Button("Krijg Geselecteerde Tekst");

getSelectionBtn.onClick(e -> {
  String selected = textField.getSelectedText();
  System.out.println("Geselecteerde tekst: " + selected);
});
```

Evenzo is het mogelijk om het huidige selectiegebied van het `TextField` op te halen met de `getSelectionRange()` methode. Dit retourneert een `SelectionRange` object dat de start- en eindindexen van de geselecteerde tekst weergeeft.

:::tip Gebruik `getSelectedText()` versus gebeurtenispayload
Hoewel je `getSelectedText()` handmatig binnen een gebeurtenis handler kunt aanroepen, is het efficiënter om de selectiedata te gebruiken die in de payload van de gebeurtenis wordt gegeven—zoals in een `SelectionChangeEvent`—om aanvullende opzoeken te vermijden.
:::

## Pattern matching {#pattern-matching}

Je kunt de `setPattern()` methode gebruiken om een validatieregel voor het `TextField` te definiëren met behulp van een reguliere expressie. Het instellen van een patroon voegt een beperking validatie toe die vereist dat de invoerwaarde overeenkomt met het gespecificeerde patroon.

Het patroon moet een geldige [JavaScript reguliere expressie](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_expressions) zijn, zoals geïnterpreteerd door de browser. De `u` (Unicode) vlag wordt intern toegepast om een nauwkeurige matching van Unicode codepunten te waarborgen. Verpak het patroon niet in schuine strepen (`/`), omdat deze niet vereist zijn en als letterlijke tekens worden behandeld.

```java
textField.setPattern("[A-Za-z]{3}\\d{2}"); // bijv. ABC12
```

Als er geen patroon wordt opgegeven, of de syntaxis ongeldig is, wordt de validatieregel genegeerd.

:::tip Contextuele hulp
Bij het gebruik van complexe patronen voor een `TextField`, overweeg om een combinatie van de `setLabel()`, `setHelperText()`, en `setTooltipText()` methoden te gebruiken om extra hints en begeleiding te bieden.
:::

## Minimum and maximum length {#minimum-and-maximum-length}

Het `TextField` component ondersteunt beperking validatie op basis van het aantal tekens dat door de gebruiker is ingevoerd. Dit kan worden beheerd met de `setMinLength()` en `setMaxLength()` methoden. Gebruik beide methoden om een duidelijke grens voor aanvaardbare invoerlengten te definiëren.

:::info Lengtevereisten
Standaard geeft het veld een melding weer wanneer de invoerwaarde buiten het bereik ligt, waarmee aan de gebruiker wordt aangegeven of ze hun invoer moeten inkorten of verlengen. Dit bericht kan worden overschreven met de `setInvalidMessage()` methode.
:::

### `setMinLength()` {#setminlength}

Deze methode stelt het **minimale aantal UTF-16-code-eenheden** in dat moet worden ingevoerd voor het veld als geldig te worden beschouwd. De waarde moet een geheel getal zijn en mag de geconfigureerde maximale lengte niet overschrijden.

```java
textField.setMinLength(5); // De gebruiker moet minimaal 5 tekens invoeren
```

Als de invoer minder tekens bevat dan het minimum vereist, zal de invoer niet voldoen aan de beperking validatie. Deze regel is alleen van toepassing wanneer de gebruiker de waarde van het veld verandert.

### `setMaxLength()` {#setmaxlength}

Deze methode stelt het **maximale aantal UTF-16 code-eenheden** in dat in het tekstveld is toegestaan. De waarde moet `0` of groter zijn. Als deze niet is ingesteld, of is ingesteld op een ongeldige waarde, wordt er geen maximum afgedwongen.

```java
textField.setMaxLength(20); // De gebruiker kan niet meer dan 20 tekens invoeren
```

Het veld voldoet niet aan de beperking validatie als de invoerlengte de minimale lengte overschrijdt. Zoals bij `setMinLength()`, wordt deze validatie alleen geactiveerd wanneer de waarde door de gebruiker wordt veranderd.

## Best practices {#best-practices}

De volgende sectie schetst enkele aanbevolen best practices voor het gebruik van het `TextField`.

- **Bied Duidelijke Labels en Instructies**: Label het `TextField` duidelijk om aan te geven welk type informatie gebruikers moeten invoeren. Geef aanvullende instructietekst of placeholderwaarden om gebruikers te begeleiden en verwachtingen te scheppen voor de vereiste invoer.

- **Spellingscontrole en Auto-aanvullen**: Overweeg, waar mogelijk, het gebruik van spellingscontrole met `setSpellCheck()` en/of het gebruik van auto-aanvullen in een `TextField`. Deze functies kunnen gebruikers helpen informatie sneller in te voeren en fouten te verminderen door voorgestelde waarden te bieden op basis van eerdere invoeren of vooraf gedefinieerde opties.

- **Toegankelijkheid**: Gebruik het `TextField` component met toegankelijkheid in gedachten, waarbij je je houdt aan toegankelijkheidsnormen, zoals juiste labeling, ondersteuning voor toetsenbordnavigatie en compatibiliteit met assistieve technologieën. Zorg ervoor dat gebruikers met een handicap effectief met het `TextField` kunnen interageren.
