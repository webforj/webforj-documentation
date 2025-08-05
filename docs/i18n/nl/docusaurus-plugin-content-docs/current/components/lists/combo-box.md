---
sidebar_position: 10
title: ComboBox
slug: combobox
_i18n_hash: d0112ef19b8ef7b0b2621af5c500a6c9
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-combobox" />
<DocChip chip='since' label='23.05' />
<JavadocLink type="foundation" location="com/webforj/component/list/ComboBox" top='true'/>

<ParentLink parent="Lijst" />

De `ComboBox`-component is een interface-element dat is ontworpen om gebruikers een lijst met opties of keuzes te presenteren, evenals een veld voor het invoeren van hun eigen aangepaste waarden. Gebruikers kunnen een enkele optie uit deze lijst selecteren, meestal door op de `ComboBox` te klikken, wat de weergave van een dropdownlijst met beschikbare keuzes activeert, of een aangepaste waarde typen. Gebruikers kunnen ook met de pijltjestoetsen interactie hebben met de `ComboBox`. Wanneer een gebruiker een selectie maakt, wordt de gekozen optie weergegeven in de `ComboBox`. 

## Toepassingen {#usages}

De ComboBox-component is een veelzijdig invoerelement dat de functies van zowel een dropdownlijst als een tekstinvoerveld combineert. Het stelt gebruikers in staat om items uit een vooraf gedefinieerde lijst te selecteren of, indien nodig, aangepaste waarden in te voeren. Deze sectie verkent veelvoorkomende toepassingen van de ComboBox-component in verschillende scenario's:

1. **Productzoekfunctie en invoer**: Gebruik een ComboBox om een productzoek- en invoerfunctie te implementeren. Gebruikers kunnen een product uit een vooraf gedefinieerde lijst selecteren of een aangepaste productnaam typen. Dit is nuttig voor applicaties zoals e-commerce-sites waar producten talrijk en divers zijn.

2. **Tagselectie en invoer**: In applicaties die inhoudslabeling omvatten, kan een ComboBox een uitstekende keuze zijn. Gebruikers kunnen kiezen uit een lijst van bestaande tags of aangepaste tags toevoegen door ze te typen. Dit is nuttig voor het organiseren en categoriseren van inhoud. Voorbeelden van dergelijke tags zijn:
    >- Projectlabels: In een projectmanagementtool kunnen gebruikers labels of tags selecteren (bijv. "Dringend," "In uitvoering," "Voltooid") om taken of projecten te categoriseren, en ze kunnen indien nodig aangepaste labels maken.
    >- Receptingrediënten: In een kook- of recepten-app kunnen gebruikers ingrediënten uit een lijst selecteren (bijv. "Tomaten," "Uien," "Kip") of hun eigen ingrediënten toevoegen voor aangepaste recepten.
    >- Locatietags: In een mapping- of geotagging-applicatie kunnen gebruikers vooraf gedefinieerde locatietags kiezen (bijv. "Strand," "Stad," "Park") of aangepaste tags maken om specifieke plaatsen op een kaart te markeren.

3. **Gegevensinvoer met voorgestelde waarden**: In gegevensinvoervormen kan een ComboBox worden gebruikt om de invoer te versnellen door een lijst met voorgestelde waarden op basis van gebruikersinvoer te bieden. Dit helpt gebruikers om gegevens nauwkeurig en efficiënt in te voeren.

    :::tip
    De `ComboBox` moet worden gebruikt wanneer gebruikers aangepaste waarden mogen invoeren. Als alleen vooraf ingestelde waarden gewenst zijn, gebruik dan een [`ChoiceBox`](./choice-box.md) in plaats daarvan.
    :::

## Aangepaste waarde {#custom-value}

Het wijzigen van de eigenschap voor aangepaste waarde biedt controle over of een gebruiker de waarde in het invoerveld van de `ComboBox`-component kan wijzigen. Als `true`, wat de standaard is, kan een gebruiker de waarde wijzigen. Als dit op `false` is ingesteld, kan de gebruiker de waarde niet wijzigen. Dit kan worden ingesteld met de <JavadocLink type="foundation" location="com/webforj/component/list/ComboBox" code='true' suffix='#setAllowCustomValue(boolean)'>setAllowCustomValue()</JavadocLink>-methode.

<ComponentDemo 
path='/webforj/comboboxcustomvalue?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/lists/combobox/ComboBoxCustomValueView.java'
height = '200px'
/>

## Placeholder {#placeholder}

Een placeholder kan worden ingesteld voor een `ComboBox`, die wordt weergegeven in het tekstveld van de component wanneer deze leeg is om gebruikers te stimuleren tot invoer in het veld. Dit kan worden gedaan met de <JavadocLink type="foundation" location="com/webforj/component/list/ComboBox" code='true' suffix='#setPlaceholder(java.lang.String)'>setPlaceholder()</JavadocLink>-methode.

<ComponentDemo 
path='/webforj/comboboxplaceholder?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/lists/combobox/ComboBoxPlaceholderView.java'
height = '200px'
/>

## Dropdown-type {#dropdown-type}

Met de <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setDropdownType(java.lang.String)'>setDropdownType()</JavadocLink>-methode kan een waarde worden toegewezen aan het `type`-attribuut van een `ComboBox`, en een bijbehorende waarde voor het `data-dropdown-for`-attribuut in de dropdown van de `ComboBox`. Dit is nuttig voor styling, aangezien de dropdown uit zijn huidige positie in de DOM wordt gehaald en naar de onderkant van de pagina wordt verplaatst wanneer deze wordt geopend.

Deze ontkoppeling creëert een situatie waarin het rechtstreeks targeten van de dropdown met behulp van CSS- of schaduwdeelselectoren vanuit de bovenliggende component moeilijk wordt, tenzij u gebruikmaakt van het dropdown-type attribuut.

In de demo hieronder wordt het dropdown-type ingesteld en gebruikt in het CSS-bestand om de dropdown te selecteren en de achtergrondkleur te wijzigen.

<ComponentDemo 
path='/webforj/comboboxdropdowntype?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/lists/combobox/ComboBoxDropdownTypeView.java'
cssURL='/css/lists/combobox/comboBoxDropDownType.css'
height='250px'
/>

## Maximal aantal rijen {#max-row-count}

Standaard wordt het aantal rijen dat in de dropdown van een `ComboBox` wordt weergegeven, verhoogd om de inhoud te passen. Met de <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setMaxRowCount(int)'>setMaxRowCount()</JavadocLink>-methode kan echter worden gecontroleerd hoeveel items worden weergegeven.

:::caution
Het gebruik van een aantal dat kleiner is dan of gelijk is aan 0 zal leiden tot het unsetten van deze eigenschap.
:::

<ComponentDemo 
path='/webforj/comboboxmaxrow?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/lists/combobox/ComboBoxMaxRowView.java'
height='450px'
/>

## Openen en sluiten {#opening-and-closing}

De zichtbaarheid van de opties voor een `ComboBox` kan programmatisch worden beheerd met de `open()`- en `close()`-methoden. Deze methoden stellen u in staat om de lijst met opties voor selectie weer te geven of te verbergen indien nodig, wat meer flexibiliteit biedt in het beheren van het gedrag van een `ComboBox`.

Bovendien heeft webforJ gebeurtenisluiters voor wanneer de `ComboBox` is gesloten en wanneer deze is geopend, waardoor u meer controle hebt om specifieke acties in gang te zetten.

```Java
//Focus of open het volgende component in een formulier
ComboBox universiteit = new ComboBox("Universiteit");
ComboBox major = new ComboBox("Hoofdvak");
Button verzenden = new Button("Verzenden");

//... Voeg lijsten met universiteiten en hoofdvakken toe

universiteit.onClose( e ->{
  major.open();
});

major.onClose( e ->{
  verzenden.focus();
});
```

## Openingsdimensies {#opening-dimensions}

De `ComboBox`-component heeft methoden waarmee de afmetingen van de dropdown kunnen worden aangepast. De **maximale hoogte** en **minimale breedte** van de dropdown kunnen worden ingesteld met de <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setOpenHeight(int)'>setOpenHeight()</JavadocLink>- en <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setOpenWidth(int)'>setOpenWidth()</JavadocLink>-methoden, respectievelijk. 

:::tip
Als u een `String`-waarde aan een van deze methoden doorgeeft, kunt u [elke geldige CSS-eenheid](https://developer.mozilla.org/en-US/docs/Learn/CSS/Building_blocks/Values_and_units) toepassen, zoals pixels, viewport-dimensies of andere geldige regels. Het doorgeven van een `int` stelt de waarde in pixels in.
:::

## Markeren {#highlighting}

Bij het werken met een `ComboBox` kunt u aanpassen wanneer de tekst wordt gemarkeerd op basis van hoe de component focus krijgt. Deze functie kan invoerfouten verminderen wanneer gebruikers formulieren invullen en kan de algehele navigatie-ervaring verbeteren. Wijzig het markeer gedrag met de `setHighlightOnFocus()`-methode met een van de ingebouwde `HasHighlightOnFocus.Behavior`-enumeraties:

- `ALL`
Inhoud van de component is altijd automatisch gemarkeerd wanneer de component focus krijgt.
- `FOCUS`
Inhoud van de component is automatisch gemarkeerd wanneer de component focus krijgt onder programmatische controle.
- `FOCUS_OR_KEY`
Inhoud van de component is automatisch gemarkeerd wanneer de component focus krijgt onder programmatische controle of door erin te tabben.
- `FOCUS_OR_MOUSE`
Inhoud van de component is automatisch gemarkeerd wanneer de component focus krijgt onder programmatische controle of door erop te klikken met de muis.
- `KEY`
Inhoud van de component is automatisch gemarkeerd wanneer de component focus krijgt door erin te tabben.
- `KEY_MOUSE`
Inhoud van de component is automatisch gemarkeerd wanneer de component focus krijgt door erin te tabben of door erop te klikken met de muis.
- `MOUSE`
Inhoud van de component is automatisch gemarkeerd wanneer de component focus krijgt door erop te klikken met de muis.
- `NONE`
Inhoud van de component wordt nooit automatisch gemarkeerd wanneer de component focus krijgt.

:::note
Als de inhoud was gemarkeerd bij het verliezen van focus, wordt deze opnieuw gemarkeerd bij het terugkrijgen van focus, ongeacht het ingestelde gedrag.
:::

## Voorvoegsel en achtervoegsel {#prefix-and-suffix}

Slots bieden flexibele opties voor het verbeteren van de capaciteit van een `ComboBox`. U kunt pictogrammen, labels, laadsymbolen, wissen/resetten, avatar/profielafbeeldingen en andere nuttige componenten binnen een `ComboBox` nestelen om de bedoelde betekenis voor gebruikers verder te verduidelijken.
De `ComboBox` heeft twee slots: de `prefix`- en `suffix`-slots. Gebruik de `setPrefixComponent()`- en `setSuffixComponent()`-methoden om verschillende componenten vóór en na de opties binnen een `ComboBox` in te voegen.

```java
ComboBox comboBox = new ComboBox());
  comboBox.setPrefixComponent(TablerIcon.create("box"));
  comboBox.setSuffixComponent(TablerIcon.create("box"));
```

## Stijlen {#styling}

<TableBuilder name="ComboBox" />

## Best practices {#best-practices}

Om een optimale gebruikerservaring te waarborgen bij het gebruik van de `ComboBox`-component, overweeg de volgende best practices:

1. **Preload veelgebruikte waarden**: Als er veelvoorkomende of vaak gebruikte items zijn, laad deze dan vooraf in de `ComboBox`-lijst. Dit versnelt de selectie voor vaak gekozen items en bevordert consistentie.

2. **Gebruiksvriendelijke labels**: Zorg ervoor dat de weergegeven labels voor elke optie gebruiksvriendelijk en zelfverklarend zijn. Zorg ervoor dat gebruikers gemakkelijk het doel van elke keuze kunnen begrijpen.

3. **Validatie**: Implementeer invoervalidatie om aangepaste invoer te verwerken. Controleer op nauwkeurigheid en consistentie van gegevens. U wilt mogelijk suggesties voor correcties of bevestigingen voor meerduidige invoer geven.

4. **Standaardselectie**: Stel een standaardselectie in, vooral als er een veelvoorkomende of aanbevolen keuze is. Dit verbetert de gebruikerservaring door de noodzaak voor extra klikken te verminderen.

5. **ComboBox versus andere lijstcomponenten**: Een `ComboBox` is de beste keuze als u een enkele invoer van de gebruiker nodig heeft en u hen vooraf bepaalde keuzes en de mogelijkheid wilt bieden om hun invoer aan te passen. Een andere lijstcomponent kan beter zijn als u de volgende gedragingen nodig heeft:
    - Meervoudige selectie en alle items tegelijk weergeven: [ListBox](./list-box.md)
    - Voorkom aangepaste invoer: [ChoiceBox](./choice-box.md)
