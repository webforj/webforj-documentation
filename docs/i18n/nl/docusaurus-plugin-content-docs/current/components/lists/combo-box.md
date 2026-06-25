---
sidebar_position: 10
title: ComboBox
slug: combobox
description: >-
  Combine a dropdown list with a text input in the ComboBox to let users select
  preset items or type custom values with placeholder support.
_i18n_hash: 4ef8ce7040bed877e314790f155f728a
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-combobox" />
<DocChip chip='since' label='23.05' />
<JavadocLink type="foundation" location="com/webforj/component/list/ComboBox" top='true'/>

De `ComboBox`-component combineert een dropdownlijst met een tekstinvoerveld, zodat gebruikers kunnen kiezen uit vooraf gedefinieerde opties of een aangepaste waarde kunnen invoeren. Wanneer aangepaste invoer naast een set voorgestelde opties toegestaan moet zijn, vult het de kloof die `ChoiceBox` niet dekt.

<!-- INTRO_END -->

## Gebruik {#usages}

<ParentLink parent="List" />

De ComboBox-component is een veelzijdig invoerelement dat de functies van zowel een dropdownlijst als een tekstinvoerveld combineert. Het stelt gebruikers in staat om items te selecteren uit een vooraf gedefinieerde lijst of naar behoefte aangepaste waarden in te voeren. Deze sectie verkent de veelvoorkomende toepassingen van de ComboBox-component in verschillende scenario's:

1. **Productzoek- en invoerfunctie**: Gebruik een ComboBox om een productzoek- en invoerfunctie te implementeren. Gebruikers kunnen een product uit een vooraf gedefinieerde lijst selecteren of een aangepaste productnaam typen. Dit is nuttig voor toepassingen zoals e-commerce sites waar producten talrijk en divers zijn.

2. **Tagselectie en invoer**: In toepassingen die betrekking hebben op content-tagging, kan een ComboBox een uitstekende keuze zijn. Gebruikers kunnen kiezen uit een lijst van bestaande tags of aangepaste tags toevoegen door ze te typen. Dit is nuttig voor het organiseren en categoriseren van inhoud. Voorbeelden van dergelijke tags zijn:
    >- Projectlabels: In een projectmanagementtool kunnen gebruikers labels of tags (bijv. "Urgent", "In Behandeling", "Voltooid") selecteren om taken of projecten te categoriseren en kunnen ze naar behoefte aangepaste labels maken.
    >- Recept ingrediënten: In een kook- of recepten-app kunnen gebruikers ingrediënten uit een lijst selecteren (bijv. "Tomaten", "Uien", "Kip") of hun eigen ingrediënten toevoegen voor aangepaste recepten.
    >- Locatietags: In een mapping- of geotaggingtoepassing kunnen gebruikers vooraf gedefinieerde locatietags (bijv. "Strand", "Stad", "Park") kiezen of aangepaste tags maken om specifieke plaatsen op een kaart te markeren.

3. **Gegevensinvoer met voorgestelde waarden**: In gegevensinvoerformulieren kan een ComboBox worden gebruikt om de invoer te versnellen door een lijst met voorgestelde waarden op basis van de gebruikersinvoer te bieden. Dit helpt gebruikers om gegevens nauwkeurig en efficiënt in te voeren.

    :::tip
    De `ComboBox` moet worden gebruikt wanneer gebruikers toegelaten zijn om aangepaste waarden in te voeren. Als alleen vooraf ingestelde waarden gewenst zijn, gebruik dan een [`ChoiceBox`](./choice-box.md) in plaats daarvan.
    :::

## Aangepaste waarde {#custom-value}

Het wijzigen van de eigenschap voor de aangepaste waarde maakt controle mogelijk over het al dan niet toestaan dat een gebruiker de waarde in het invoerveld van de `ComboBox` kan wijzigen. Als `true`, wat de standaardwaarde is, kan een gebruiker de waarde wijzigen. Als dit is ingesteld op `false`, kan de gebruiker de waarde niet wijzigen. Dit kan worden ingesteld met behulp van de <JavadocLink type="foundation" location="com/webforj/component/list/ComboBox" code='true' suffix='#setAllowCustomValue(boolean)'>setAllowCustomValue()</JavadocLink> methode.

<ComponentDemo
path='/webforj/comboboxcustomvalue'
files={['src/main/java/com/webforj/samples/views/lists/combobox/ComboBoxCustomValueView.java']}
height='200px'
/>

## Plaatsvervanger {#placeholder}

Een plaatsvervanger kan worden ingesteld voor een `ComboBox`, die wordt weergegeven in het tekstveld van de component wanneer deze leeg is om gebruikers te vragen om de gewenste invoer in het veld. Dit kan worden gedaan met behulp van de <JavadocLink type="foundation" location="com/webforj/component/list/ComboBox" code='true' suffix='#setPlaceholder(java.lang.String)'>setPlaceholder()</JavadocLink> methode.

<ComponentDemo
path='/webforj/comboboxplaceholder'
files={['src/main/java/com/webforj/samples/views/lists/combobox/ComboBoxPlaceholderView.java']}
height='200px'
/>

## Dropdowntype {#dropdown-type}

Met de <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setDropdownType(java.lang.String)'>setDropdownType()</JavadocLink> methode kan een waarde worden toegewezen aan de `type` eigenschap van een `ComboBox`, en een bijbehorende waarde voor de `data-dropdown-for` eigenschap in de dropdown van de `ComboBox`. Dit is nuttig voor styling, aangezien de dropdown uit zijn huidige positie in de DOM wordt gehaald en naar het einde van de pagina-lichaam wordt verplaatst wanneer deze wordt geopend.

Deze loskoppeling creëert een situatie waarbij het moeilijk wordt om de dropdown rechtstreeks met CSS- of shadow part-selectors vanuit de bovenliggende component te targeten, tenzij je gebruik maakt van de dropdown-type-eigenschap.

In de demo hieronder is het Dropdowntype ingesteld en gebruikt in het CSS-bestand om een optie te vergroten wanneer je eroverheen gaat.

<ComponentDemo
path='/webforj/comboboxdropdowntype'
files={[
  'src/main/java/com/webforj/samples/views/lists/combobox/ComboBoxDropdownTypeView.java',
  'src/main/resources/static/css/lists/combobox/comboBoxDropDownType.css',
]}
height='250px'
/>

## Maximale aantal rijen {#max-row-count}

Standaard wordt het aantal weergegeven rijen in de dropdown van een `ComboBox` vergroot om de inhoud te passen. Met de <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setMaxRowCount(int)'>setMaxRowCount()</JavadocLink> methode kan echter controle worden uitgeoefend over hoeveel items worden weergegeven.

:::caution
Het gebruiken van een getal dat kleiner dan of gelijk aan 0 is, resulteert in het unsetten van deze eigenschap.
:::

<ComponentDemo
path='/webforj/comboboxmaxrow'
files={['src/main/java/com/webforj/samples/views/lists/combobox/ComboBoxMaxRowView.java']}
height='450px'
/>

## Openen en sluiten {#opening-and-closing}

De zichtbaarheid van de opties voor een `ComboBox` kan programmatisch worden gecontroleerd met de `open()` en `close()` methoden.
Deze methoden stellen je in staat om de lijst met opties te tonen voor selectie of deze te verbergen indien nodig, waardoor meer flexibiliteit wordt geboden bij het beheren van het gedrag van een `ComboBox`.

Bovendien heeft webforJ gebeurtenisluisteraars voor wanneer de `ComboBox` wordt gesloten en wanneer hij wordt geopend, zodat je meer controle hebt om specifieke acties te triggeren.

```Java
//Focus of open het volgende component in een formulier
ComboBox universiteit = new ComboBox("Universiteit");
ComboBox major = new ComboBox("Hoofdvak");
Button verzenden = new Button("Verzenden");

//... Voeg lijsten van universiteiten en majors toe

universiteit.onClose( e ->{
  major.open();
});

major.onClose( e ->{
  verzenden.focus();
});
```

## Openingsdimensies {#opening-dimensions}

De `ComboBox`-component heeft methoden die manipulatie van de dropdowndimensies mogelijk maken. De **maximale hoogte** en **minimale breedte** van de dropdown kunnen worden ingesteld met de <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setOpenHeight(int)'>setOpenHeight()</JavadocLink> en <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setOpenWidth(int)'>setOpenWidth()</JavadocLink> methoden, respectievelijk. 

:::tip
Het doorgeven van een `String` waarde aan een van deze methoden stelt je in staat om [elke geldige CSS-eenheid](https://developer.mozilla.org/en-US/docs/Learn/CSS/Building_blocks/Values_and_units) toe te passen, zoals pixels, viewport-dimensies of andere geldige regels. Het doorgeven van een `int` zal de waarde die is doorgegeven in pixels instellen.
:::

## Markeren {#highlighting}

Wanneer je met een `ComboBox` werkt, kun je aanpassen wanneer de tekst wordt gemarkeerd op basis van hoe de component focus krijgt. Deze functie kan invoerfouten verminderen wanneer gebruikers formulieren invullen en kan de algehele navigatie-ervaring verbeteren. Verander het markeer gedrag met de `setHighlightOnFocus()` methode met een van de ingebouwde `HasHighlightOnFocus.Behavior` enums:

- `ALL`
Inhoud van de component wordt altijd automatisch gemarkeerd wanneer de component focus krijgt.
- `FOCUS`
Inhoud van de component wordt automatisch gemarkeerd wanneer de component onder programmatische controle focus krijgt.
- `FOCUS_OR_KEY`
Inhoud van de component wordt automatisch gemarkeerd wanneer de component onder programmatische controle of door tabben erin focus krijgt.
- `FOCUS_OR_MOUSE`
Inhoud van de component wordt automatisch gemarkeerd wanneer de component onder programmatische controle of door erop te klikken met de muis focus krijgt.
- `KEY`
Inhoud van de component wordt automatisch gemarkeerd wanneer de component focus krijgt door erin te tabben.
- `KEY_MOUSE`
Inhoud van de component wordt automatisch gemarkeerd wanneer de component focus krijgt door erin te tabben of door erop te klikken met de muis.
- `MOUSE`
Inhoud van de component wordt automatisch gemarkeerd wanneer de component focus krijgt door erop te klikken met de muis.
- `NONE`
Inhoud van de component wordt nooit automatisch gemarkeerd wanneer de component focus krijgt.

:::note
Als inhoud was gemarkeerd bij het verliezen van focus, zal deze opnieuw worden gemarkeerd bij het opnieuw verkrijgen van focus, ongeacht het ingestelde gedrag.
:::

## Voorvoegsel en achtervoegsel {#prefix-and-suffix}

Slots bieden flexibele opties voor het verbeteren van de mogelijkheid van een `ComboBox`. Je kunt iconen, labels, laadspinners, wis/resetmogelijkheden, avatar-/profielafbeeldingen en andere nuttige componenten nestelen binnen een `ComboBox` om de bedoelde betekenis voor gebruikers verder te verduidelijken.
De `ComboBox` heeft twee slots: de `prefix` en `suffix` slots. Gebruik de `setPrefixComponent()` en `setSuffixComponent()` methoden om verschillende componenten voor en na de opties binnen een `ComboBox` in te voegen.

```java
ComboBox comboBox = new ComboBox());
  comboBox.setPrefixComponent(TablerIcon.create("box"));
  comboBox.setSuffixComponent(TablerIcon.create("box"));
```

## Stijling {#styling}

<TableBuilder name="ComboBox" />

## Best practices {#best-practices}

Om een optimale gebruikerservaring te waarborgen bij het gebruik van de `ComboBox`-component, houd rekening met de volgende best practices:

1. **Veelgebruikte waarden vooraf laden**: Als er veelgebruikte items zijn, laad ze dan vooraf in de `ComboBox`-lijst. Dit versnelt de selectie voor vaak gekozen items en moedigt consistentie aan.

2. **Gebruiksvriendelijke labels**: Zorg ervoor dat de weergegeven labels voor elke optie gebruiksvriendelijk en vanzelfsprekend zijn. Zorg ervoor dat gebruikers de bedoeling van elke keuze gemakkelijk kunnen begrijpen.

3. **Validatie**: Implementeer invoervalidatie om aangepaste invoer af te handelen. Controleer op nauwkeurigheid en consistentie van de gegevens. Je wilt misschien suggesties doen voor correcties of bevestigingen voor ambigue invoer.

4. **Standaardselectie**: Stel een standaardselectie in, vooral als er een veelgebruikte of aanbevolen keuze is. Dit verbetert de gebruikerservaring door de behoefte aan extra klikken te verminderen.

5. **ComboBox vs. andere lijstcomponenten**: Een `ComboBox` is de beste keuze als je een enkele invoer van de gebruiker nodig hebt en je hen vooraf bepaalde keuzes wilt bieden en de mogelijkheid wilt geven om hun invoer aan te passen. Een andere lijstcomponent kan beter zijn als je de volgende gedragingen nodig hebt:
    - Meerdere selectie en alle items tegelijkertijd weergeven: [ListBox](./list-box.md)
    - Voorkom aangepaste invoer: [ChoiceBox](./choice-box.md)
