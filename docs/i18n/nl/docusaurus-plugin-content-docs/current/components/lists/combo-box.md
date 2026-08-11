---
sidebar_position: 10
title: ComboBox
slug: combobox
description: >-
  Combine a dropdown list with a text input in the ComboBox to let users select
  preset items or type custom values with placeholder support.
_i18n_hash: 9e5c0f54f07f604ee91a84210189ca30
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-combobox" />
<DocChip chip='since' label='23.05' />
<JavadocLink type="foundation" location="com/webforj/component/list/ComboBox" top='true'/>

De `ComboBox` component combineert een dropdownlijst met een tekstinvoer, zodat gebruikers kunnen kiezen uit vooraf gedefinieerde opties of een aangepaste waarde kunnen invoeren. Wanneer aangepaste invoer naast een set voorgestelde opties moet worden toegestaan, vul je de lacune die `ChoiceBox` niet dekt.

<!-- INTRO_END -->

## Usages {#usages}

<ParentLink parent="List" />

De ComboBox-component is een veelzijdig invoerelement dat de functies van zowel een dropdownlijst als een tekstinvoerveld combineert. Het stelt gebruikers in staat om items te selecteren uit een vooraf gedefinieerde lijst of om indien nodig aangepaste waarden in te voeren. Dit gedeelte verkent veelvoorkomende toepassingen van de ComboBox-component in verschillende scenario's:

1. **Productzoek- en invoerfunctie**: Gebruik een ComboBox om een functie voor productzoek- en invoer te implementeren. Gebruikers kunnen een product kiezen uit een vooraf gedefinieerde lijst of een aangepaste productnaam invoeren. Dit is nuttig voor applicaties zoals e-commerce sites waar producten talrijk en divers zijn.

2. **Tagselectie en invoer**: In applicaties die verband houden met content-tagging kan een ComboBox een uitstekende keuze zijn. Gebruikers kunnen kiezen uit een lijst van bestaande tags of aangepaste tags toevoegen door ze in te typen. Dit is nuttig voor het organiseren en categoriseren van inhoud. Voorbeelden van dergelijke tags zijn:
    >- Projectlabels: In een projectmanagementtool kunnen gebruikers labels of tags (bijv. "Urgent", "In uitvoering", "Voltooid") selecteren om taken of projecten te categoriseren, en ze kunnen indien nodig aangepaste labels maken.
    >- Receptingrediënten: In een kook- of recepten-app kunnen gebruikers ingrediënten selecteren uit een lijst (bijv. "Tomaten", "Uien", "Kip") of hun eigen ingrediënten toevoegen voor aangepaste recepten.
    >- Locatietags: In een mapping- of geotagging-app kunnen gebruikers kiezen uit vooraf gedefinieerde locatietags (bijv. "Strand", "Stad", "Park") of aangepaste tags maken om specifieke plaatsen op een kaart te markeren.

3. **Gegevensinvoer met voorgestelde waarden**: In gegevensinvoervormen kan een ComboBox worden gebruikt om de invoer te versnellen door een lijst met voorgestelde waarden op basis van gebruikersinvoer te bieden. Dit helpt gebruikers om gegevens nauwkeurig en efficiënt in te voeren.

    :::tip
    De `ComboBox` moet worden gebruikt wanneer gebruikers zijn toegestaan om aangepaste waarden in te voeren. Als alleen vooraf ingestelde waarden gewenst zijn, gebruik dan in plaats daarvan een [`ChoiceBox`](./choice-box.md).
    :::

## Custom value {#custom-value}

Door de eigenschap voor aangepaste waarde te wijzigen, kun je bepalen of een gebruiker de waarde in het invoerveld van de `ComboBox` component kan wijzigen. Als `waar`, wat de standaard is, kan een gebruiker de waarde wijzigen. Als het is ingesteld op `onwaar`, kan de gebruiker de waarde niet wijzigen. Dit kan worden ingesteld met de <JavadocLink type="foundation" location="com/webforj/component/list/ComboBox" code='true' suffix='#setAllowCustomValue(boolean)'>setAllowCustomValue()</JavadocLink> methode.

<ComponentDemo
path='/webforj/comboboxcustomvalue'
files={['src/main/java/com/webforj/samples/views/lists/combobox/ComboBoxCustomValueView.java']}
height='200px'
/>

## Placeholder {#placeholder}

Een placeholder kan worden ingesteld voor een `ComboBox` die in het tekstveld van de component wordt weergegeven wanneer het leeg is om gebruikers te stimuleren om de gewenste invoer in het veld te doen. Dit kan worden gedaan met de <JavadocLink type="foundation" location="com/webforj/component/list/ComboBox" code='true' suffix='#setPlaceholder(java.lang.String)'>setPlaceholder()</JavadocLink> methode.

<ComponentDemo
path='/webforj/comboboxplaceholder'
files={['src/main/java/com/webforj/samples/views/lists/combobox/ComboBoxPlaceholderView.java']}
height='200px'
/>

## Dropdown type {#dropdown-type}

Met de <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setDropdownType(java.lang.String)'>setDropdownType()</JavadocLink> methode kan een waarde worden toegewezen aan de `type` eigenschap van een `ComboBox`, en een overeenkomstige waarde voor de `data-dropdown-for` eigenschap in de dropdown van de `ComboBox`. Dit is nuttig voor styling, omdat de dropdown uit zijn huidige positie in de DOM wordt gehaald en naar het einde van het paginabody wordt verplaatst wanneer deze wordt geopend.

Deze loskoppeling creëert een situatie waarin het uitdagend wordt om de dropdown rechtstreeks te targeten met CSS of shadow part-selectors vanuit de bovenliggende component, tenzij je gebruikmaakt van de dropdown type-eigenschap.

In de demo hieronder is het Dropdown-type ingesteld en gebruikt in het CSS-bestand om een optie te vergroten wanneer je eroverheen hover.

<ComponentDemo
path='/webforj/comboboxdropdowntype'
files={[
  'src/main/java/com/webforj/samples/views/lists/combobox/ComboBoxDropdownTypeView.java',
  'src/main/frontend/css/lists/combobox/comboBoxDropDownType.css',
]}
height='250px'
/>

## Max row count {#max-row-count}

Standaard wordt het aantal rijen dat in de dropdown van een `ComboBox` wordt weergegeven verhoogd om de inhoud te passen. Met de <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setMaxRowCount(int)'>setMaxRowCount()</JavadocLink> methode kan echter worden bepaald hoeveel items worden weergegeven.

:::caution
Gebruik een getal dat kleiner is dan of gelijk aan 0 resulteert in het ongedaan maken van deze eigenschap.
:::

<ComponentDemo
path='/webforj/comboboxmaxrow'
files={['src/main/java/com/webforj/samples/views/lists/combobox/ComboBoxMaxRowView.java']}
height='450px'
/>

## Opening and closing {#opening-and-closing}

De zichtbaarheid van de opties voor een `ComboBox` kan programmatisch worden beheerd met de `open()` en `close()` methoden. Deze methoden stellen je in staat om de lijst met opties voor selectie weer te geven of deze te verbergen indien nodig, waardoor je meer flexibiliteit krijgt in het beheren van het gedrag van een `ComboBox`.

Bovendien heeft webforJ gebeurtenisluisteraars voor wanneer de `ComboBox` wordt gesloten en wanneer deze wordt geopend, wat je meer controle geeft om specifieke acties te triggeren.

```Java
//Focus of open het volgende component in een formulier
ComboBox university = new ComboBox("Universiteit");
ComboBox major = new ComboBox("Hoofdvak");
Button submit = new Button("Versturen");

//... Voeg lijsten van universiteiten en hoofdvakken toe

university.onClose( e ->{
  major.open();
});

major.onClose( e ->{
  submit.focus();
});
```

## Opening dimensions {#opening-dimensions}

De `ComboBox` component heeft methoden die manipulatie van de dropdown-afmetingen mogelijk maken. De **maximale hoogte** en **minimale breedte** van de dropdown kunnen worden ingesteld met de <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setOpenHeight(int)'>setOpenHeight()</JavadocLink> en <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setOpenWidth(int)'>setOpenWidth()</JavadocLink> methoden, respectievelijk.

:::tip
Het doorgeven van een `String` waarde aan een van deze methoden zal zorgen voor [kwaliteitsgoedkeuring CSS-eenheid](https://developer.mozilla.org/en-US/docs/Learn/CSS/Building_blocks/Values_and_units), zoals pixels, viewport-afmetingen of andere geldige regels. Het doorgeven van een `int` stelt de waarde in pixels in.
:::

## Highlighting {#highlighting}

Bij het werken met een `ComboBox` kun je aanpassen wanneer de tekst wordt gemarkeerd op basis van hoe de component focus krijgt. Deze functie kan invoerfouten verminderen wanneer gebruikers formulieren invullen en kan de algehele navigatie-ervaring verbeteren. Wijzig het markeer gedrag met de `setHighlightOnFocus()` methode met een van de ingebouwde `HasHighlightOnFocus.Behavior` enums:

- `ALL`
De inhoud van de component wordt altijd automatisch gemarkeerd wanneer de component focus krijgt.
- `FOCUS`
De inhoud van de component wordt automatisch gemarkeerd wanneer de component focus krijgt onder programmatische controle.
- `FOCUS_OR_KEY`
De inhoud van de component wordt automatisch gemarkeerd wanneer de component focus krijgt onder programmatische controle of door er met de tab in te komen.
- `FOCUS_OR_MOUSE`
De inhoud van de component wordt automatisch gemarkeerd wanneer de component focus krijgt onder programmatische controle of door er met de muis in te klikken.
- `KEY`
De inhoud van de component wordt automatisch gemarkeerd wanneer de component focus krijgt door er met de tab in te komen.
- `KEY_MOUSE`
De inhoud van de component wordt automatisch gemarkeerd wanneer de component focus krijgt door er met de tab in te komen of door er met de muis in te klikken.
- `MOUSE`
De inhoud van de component wordt automatisch gemarkeerd wanneer de component focus krijgt door er met de muis in te klikken.
- `NONE`
De inhoud van de component wordt nooit automatisch gemarkeerd wanneer de component focus krijgt.

:::note
Als de inhoud werd gemarkeerd bij het verliezen van focus, wordt deze opnieuw gemarkeerd wanneer de focus wordt herwonnen, ongeacht het ingestelde gedrag.
:::

## Prefix and suffix {#prefix-and-suffix}

Slots bieden flexibele opties om de mogelijkheden van een `ComboBox` te verbeteren. Je kunt iconen, labels, laadspinners, mogelijkheid tot wissen/resetten, avatars/profielafbeeldingen en andere nuttige componenten binnen een `ComboBox` nestelen om de bedoelde betekenis voor gebruikers verder te verduidelijken. De `ComboBox` heeft twee slots: de `prefix` en `suffix` slots. Gebruik de `setPrefixComponent()` en `setSuffixComponent()` methoden om verschillende componenten in te voegen voor en na de opties binnen een `ComboBox`.

```java
ComboBox comboBox = new ComboBox());
  comboBox.setPrefixComponent(TablerIcon.create("box"));
  comboBox.setSuffixComponent(TablerIcon.create("box"));
```

## Styling {#styling}

<TableBuilder name="ComboBox" />

## Best practices {#best-practices}

Om een optimale gebruikerservaring te waarborgen bij het gebruik van de `ComboBox` component, overweeg de volgende best practices:

1. **Common values vooraf laden**: Als er veelvoorkomende of vaak gebruikte items zijn, laad ze dan vooraf in de lijst van de `ComboBox`. Dit versnelt de selectie voor vaak gekozen items en bevordert consistentie.

2. **Gebruiksvriendelijke labels**: Zorg ervoor dat de weergegeven labels voor elke optie gebruiksvriendelijk en zelfverklarend zijn. Zorg ervoor dat gebruikers gemakkelijk de bedoeling van elke keuze kunnen begrijpen.

3. **Validatie**: Implementeer invoervalidatie om aangepaste invoer te verwerken. Controleer op gegevensnauwkeurigheid en consistentie. Je wilt misschien correcties of bevestigingen voorstellen voor ambiguë invoer.

4. **Standaardselectie**: Stel een standaardselectie in, vooral als er een veelvoorkomende of aanbevolen keuze is. Dit verbetert de gebruikerservaring door de noodzaak van extra klikken te verminderen.

5. **ComboBox versus andere lijstcomponenten**: Een `ComboBox` is de beste keuze als je een enkele invoer van de gebruiker nodig hebt en je hen vooraf gedefinieerde keuzes wilt bieden en de mogelijkheid om hun invoer aan te passen. Een andere lijstcomponent kan beter zijn als je de volgende gedrag nodig hebt:
    - Meerdere selectie en alle items in één keer weergeven: [ListBox](./list-box.md)
    - Voorkom aangepaste invoer: [ChoiceBox](./choice-box.md)
