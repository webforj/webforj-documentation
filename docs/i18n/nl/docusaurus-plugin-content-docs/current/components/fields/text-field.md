---
sidebar_position: 35
title: TextField
slug: textfield
description: A single-line input component for entering and editing text data.
_i18n_hash: e972f03e1d4deb1802bc4f56104e61b3
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-veld" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/field/TextField" top='true'/>

De `TextField`-component stelt gebruikers in staat om tekst in te voeren en te bewerken in een enkele regel. Je kunt het veld configureren om een specifiek virtueel toetsenbord weer te geven, zoals een numeriek toetsenbord, e-mailinvoer, telefooninvoer of URL-invoer. De component biedt ook ingebouwde validatie om waarden af te wijzen die niet voldoen aan het opgegeven type.

<!-- INTRO_END -->

## Gebruik {#usages}

<ParentLink parent="Veld" />

De `TextField` is geschikt voor een breed scala aan scenario's waarin tekstinvoer of -bewerking vereist is. Hier zijn enkele voorbeelden van wanneer je de `TextField` kunt gebruiken:

1. **Formuliervelden**: Een `TextField` wordt vaak gebruikt in formulieren voor het vastleggen van gebruikersinvoer, zoals namen, adressen of opmerkingen. Het is het beste om een `TextField` in een app te gebruiken wanneer de invoer over het algemeen kort genoeg is om op één regel te passen.

2. **Zoekfunctionaliteit**: `TextField` kan worden gebruikt om een zoekvak te bieden, zodat gebruikers zoekopdrachten kunnen invoeren.

3. **Tekstbewerking**: Een `TextField` is ideaal voor apps die tekstbewerking of contentcreatie vereisen, zoals documentbewerkers, chat-apps of notitie-apps.

## Typen {#types}

Je kunt het type van de TextField specificeren met de `setType()`-methode. Evenzo kun je het type ophalen met de `getType()`-methode, die een enumwaarde retourneert.

- `Type.TEXT`: Dit is het standaardtype voor een `TextField` en verwijdert automatisch regels in de invoerwaarde.

- `Type.EMAIL`: Dit type is voor het invoeren van e-mailadressen. Het valideert de invoer volgens de standaard e-mailsyntaxis. Daarnaast biedt het compatibele browsers en apparaten een dynamisch toetsenbord dat het typen vereenvoudigt door gemeenschappelijk gebruikte toetsen zoals <kbd>.com</kbd> en <kbd>@</kbd> op te nemen.

  :::note
  Hoewel deze validatie het formaat van het e-mailadres bevestigt, garandeert het niet dat het e-mailadres bestaat.
  :::

- `Type.TEL`: Dit type wordt gebruikt voor het invoeren van een telefoonnummer. Het veld toont een telefoontoetsenbord op sommige apparaten met dynamische toetsenborden.

- `Type.URL`: Dit type is voor het invoeren van URL's. Het valideert of een gebruiker een URL invoert die een schema en een domeinnaam bevat, bijvoorbeeld: https://webforj.com. Daarnaast biedt het compatibele browsers en apparaten een dynamisch toetsenbord dat het typen vereenvoudigt door gemeenschappelijk gebruikte toetsen zoals <kbd>:</kbd>, <kbd>/</kbd>, en <kbd>.com</kbd> op te nemen.

- `Type.SEARCH`: Een tekstveld van één regel voor het invoeren van zoektermen. Regelafbrekingen worden automatisch verwijderd uit de invoerwaarde.

<ComponentDemo 
path='/webforj/textfield?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/textfield/TextFieldView.java'
/>

## Veldwaarde {#field-value}

De waarde van een `TextField` vertegenwoordigt de huidige gebruikersinvoer als een tekenreeks. In webforJ kan dit worden benaderd met de `getValue()`-methode, of programmatic worden bijgewerkt met `setValue(String)`.

```java
//Stel de initiële inhoud in
textField.setValue("Initiële inhoud");

//Haal de huidige waarde op
String text = textField.getValue();
```

Als de `getValue()`-methode wordt gebruikt op een veld zonder huidige waarde, retourneert deze een lege tekenreeks (`""`).

Dit gedrag is consistent met hoe het HTML `<input type="text">`-element zijn waarde via JavaScript blootlegt.

:::tip Combineer waardeafhandeling met validatie
Pas beperkingen toe zoals een [patroon](#pattern-matching), [minimale lengte](#setminlength), of een [maximale lengte](#setmaxlength) om te definiëren wanneer een waarde als geldig wordt beschouwd. 
:::

## Plaatsvervangende tekst {#placeholder-text}

Je kunt plaatsvervangende tekst instellen voor de `TextField` met de `setPlaceholder()`-methode. De plaatsvervangende tekst wordt weergegeven wanneer het veld leeg is, en helpt de gebruiker te stimuleren om geschikte invoer in de `TextField` in te voeren.

## Geselecteerde tekst {#selected-text}

Het is mogelijk om interactie te hebben met de `TextField`-klasse om de geselecteerde tekst van een gebruiker op te halen, en om informatie te krijgen over de selectie van de gebruiker. Je kunt de geselecteerde tekst in de `TextField` ophalen met de `getSelectedText()`-methode. Dit gedrag zou doorgaans in combinatie met een gebeurtenis worden gebruikt.

```java
TextField textField = new TextField("Voer iets in...");
Button getSelectionBtn = new Button("Haal Geselecteerde Tekst Op");

getSelectionBtn.onClick(e -> {
  String selected = textField.getSelectedText();
  System.out.println("Geselecteerde tekst: " + selected);
});
```

Evenzo is het mogelijk om het huidige selectiebereik van de `TextField` te verkrijgen met de `getSelectionRange()`-methode. Dit retourneert een `SelectionRange`-object dat de begin- en eindindex van de geselecteerde tekst vertegenwoordigt.

:::tip Gebruik `getSelectedText()` vs gebeurtenislast
Hoewel je `getSelectedText()` handmatig kunt aanroepen binnen een gebeurtenishandler, is het efficiënter om de selectiedata te gebruiken die in de last van de gebeurtenis wordt geleverd—zoals in een `SelectionChangeEvent`—om extra opzoekingen te vermijden.
:::

## Patroonmatching {#pattern-matching}

Je kunt de `setPattern()`-methode gebruiken om een validatieregel voor de `TextField` te definiëren met behulp van een reguliere expressie. Het instellen van een patroon voegt een beperkingsvalidatie toe die vereist dat de invoerwaarde overeenkomt met het opgegeven patroon.

Het patroon moet een geldige [JavaScript reguliere expressie](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_expressions) zijn, zoals geïnterpreteerd door de browser. De `u` (Unicode) vlag wordt intern toegepast om nauwkeurige overeenstemming van Unicode-codepunten te waarborgen. Wikkel het patroon niet in schuine strepen (`/`), aangezien deze niet nodig zijn en als letterlijke tekens worden behandeld.

```java
textField.setPattern("[A-Za-z]{3}\\d{2}"); // bijv. ABC12
```

Als er geen patroon is opgegeven, of als de syntaxis ongeldig is, wordt de validatieregel genegeerd.

:::tip Contextuele hulp
Bij het gebruik van complexe patronen voor een `TextField`, overweeg dan om een combinatie van de `setLabel()`, `setHelperText()`, en `setTooltipText()`-methoden te gebruiken om aanvullende hints en richtlijnen te bieden.
:::

## Minimaal en maximaal aantal tekens {#minimum-and-maximum-length}

De `TextField`-component ondersteunt beperkingsvalidatie op basis van het aantal tekens dat door de gebruiker is ingevoerd. Dit kan worden geregeld met de `setMinLength()` en `setMaxLength()`-methoden. Gebruik beide methoden om een duidelijke grens te definiëren voor acceptabele invoerlengten.

:::info Lengte-eisen
Standaard toont het veld een bericht wanneer de invoerwaarde buiten bereik is, dat aan de gebruiker aangeeft of zij hun invoer moeten verkorten of verlengen. Dit bericht kan worden overschreven met de `setInvalidMessage()`-methode.
:::

### `setMinLength()` {#setminlength}

Deze methode stelt het **minimale aantal UTF-16-code-eenheden** in dat moet worden ingevoerd voor het veld als geldig te worden beschouwd. De waarde moet een heel getal zijn en mag de geconfigureerde maximale lengte niet overschrijden.

```java
textField.setMinLength(5); // Gebruiker moet ten minste 5 tekens invoeren
```

Als de invoer minder dan het minimale aantal tekens bevat, faalt de invoer bij de beperkingsvalidatie. Deze regel is alleen van toepassing wanneer de gebruiker de waarde van het veld wijzigt.

### `setMaxLength()` {#setmaxlength}

Deze methode stelt het **maximale aantal UTF-16-code-eenheden** in dat in het tekstveld is toegestaan. De waarde moet `0` of groter zijn. Als het niet is ingesteld, of als het is ingesteld op een ongeldige waarde, wordt er geen maximum afgedwongen.

```java
textField.setMaxLength(20); // Gebruiker kan niet meer dan 20 tekens invoeren
```

Het veld faalt bij de beperkingsvalidatie als de invoerlengte de minimale lengte overschrijdt. Net als bij `setMinLength()` wordt deze validatie alleen geactiveerd wanneer de waarde door de gebruiker is gewijzigd.

## Beste praktijken {#best-practices}

De volgende sectie beschrijft enkele aanbevolen beste praktijken voor het gebruik van de `TextField`.

- **Bied Duidelijke Labeling en Instructies**: Label de `TextField` duidelijk om aan te geven welk type informatie gebruikers moeten invoeren. Bied aanvullende instructietekst of plaatsvervangende waarden aan om gebruikers te begeleiden en verwachtingen te scheppen voor de vereiste invoer.

- **Spellingscontrole en Auto-Voltooiing**: Wanneer van toepassing, overweeg dan het gebruik van spellingscontrole met `setSpellCheck()` en/of het gebruik van auto-voltooiing in een `TextField`. Deze functies kunnen gebruikers helpen informatie sneller in te voeren en fouten te verminderen door voorgestelde waarden te bieden op basis van eerdere invoer of gedefinieerde opties.

- **Toegankelijkheid**: Gebruik de `TextField`-component met het oog op toegankelijkheid, door te voldoen aan toegankelijkheidsnormen zoals juiste labeling, ondersteuning voor toetsenbordnavigatie en compatibiliteit met hulpmiddelen. Zorg ervoor dat gebruikers met een handicap effectief kunnen omgaan met de `TextField`.
