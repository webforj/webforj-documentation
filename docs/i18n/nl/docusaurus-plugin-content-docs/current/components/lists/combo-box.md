---
sidebar_position: 10
title: ComboBox
slug: combobox
_i18n_hash: 199696bfbf6489520cec364f16226489
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-combobox" />
<DocChip chip='since' label='23.05' />
<JavadocLink type="foundation" location="com/webforj/component/list/ComboBox" top='true'/>

De `ComboBox` component combineert een dropdownlijst met een tekstinvoer, zodat gebruikers kunnen kiezen uit vooraf gedefinieerde opties of een aangepaste waarde kunnen invoeren. Wanneer aangepaste invoer naast een set van voorgestelde opties nodig is, vult het de ruimte die `ChoiceBox` niet dekt.

<!-- INTRO_END -->

## Gebruik {#usages}

<ParentLink parent="Lijst" />

De ComboBox-component is een veelzijdig invoerelement dat de functies van zowel een dropdownlijst als een tekstinvoerveld combineert. Het stelt gebruikers in staat om items te selecteren uit een vooraf gedefinieerde lijst of aangepaste waarden in te voeren indien nodig. Deze sectie onderzoekt veelvoorkomende gebruiksvoorbeelden van de ComboBox-component in verschillende scenario's:

1. **Productzoekopdracht en invoer**: Gebruik een ComboBox om een functie voor productzoekopdracht en invoer te implementeren. Gebruikers kunnen ofwel een product kiezen uit een vooraf gedefinieerde lijst of een aangepaste productnaam invoeren. Dit is nuttig voor toepassingen zoals e-commerce sites waar producten talrijk en divers zijn.

2. **Tagselectie en invoer**: In toepassingen waarbij inhoud moet worden getagd, kan een ComboBox een uitstekende keuze zijn. Gebruikers kunnen kiezen uit een lijst met bestaande tags of aangepaste tags toevoegen door deze in te typen. Dit is nuttig voor het organiseren en categoriseren van inhoud. Voorbeelden van dergelijke tags zijn:
    >- Projectlabels: In een projectmanagementtool kunnen gebruikers labels of tags (bijv. "Urgent", "In Progress", "Completed") selecteren om taken of projecten te categoriseren, en ze kunnen aangepaste labels maken indien nodig.
    >- Receptingrediënten: In een kook- of receptapp kunnen gebruikers ingrediënten selecteren uit een lijst (bijv. "Tomaten", "Uien", "Kip") of hun eigen ingrediënten toevoegen voor aangepaste recepten.
    >- Locatietags: In een kaart- of geotaggingtoepassing kunnen gebruikers vooraf gedefinieerde locatietags (bijv. "Strand", "Stad", "Park") kiezen of aangepaste tags maken om specifieke plaatsen op een kaart te markeren.

3. **Gegevensinvoer met voorgestelde waarden**: In gegevensinvoervormen kan een ComboBox worden gebruikt om de invoer te versnellen door een lijst van voorgestelde waarden op basis van gebruikersinvoer te bieden. Dit helpt gebruikers om gegevens nauwkeurig en efficiënt in te voeren.

    :::tip
    De `ComboBox` moet worden gebruikt wanneer gebruikers aangepaste waarden mogen invoeren. Als alleen vooraf ingestelde waarden gewenst zijn, gebruik dan een [`ChoiceBox`](./choice-box.md) in plaats daarvan.
    :::

## Aangepaste waarde {#custom-value}

Het veranderen van de eigenschap voor aangepaste waarde stelt je in staat om te controleren of een gebruiker de waarde in het invoerveld van de `ComboBox`-component kan wijzigen. Als `true`, wat de standaardwaarde is, kan een gebruiker de waarde veranderen. Als het is ingesteld op `false`, kan de gebruiker de waarde niet wijzigen. Dit kan worden ingesteld met behulp van de <JavadocLink type="foundation" location="com/webforj/component/list/ComboBox" code='true' suffix='#setAllowCustomValue(boolean)'>setAllowCustomValue()</JavadocLink> methode.

<ComponentDemo
path='/webforj/comboboxcustomvalue'
files={['src/main/java/com/webforj/samples/views/lists/combobox/ComboBoxCustomValueView.java']}
height='200px'
/>

## Placeholder {#placeholder}

Een placeholder kan worden ingesteld voor een `ComboBox`, die wordt weergegeven in het tekstveld van de component wanneer het leeg is om gebruikers aan te moedigen de gewenste invoer in het veld in te voeren. Dit kan worden gedaan met de <JavadocLink type="foundation" location="com/webforj/component/list/ComboBox" code='true' suffix='#setPlaceholder(java.lang.String)'>setPlaceholder()</JavadocLink> methode.

<ComponentDemo
path='/webforj/comboboxplaceholder'
files={['src/main/java/com/webforj/samples/views/lists/combobox/ComboBoxPlaceholderView.java']}
height='200px'
/>

## Dropdown-type {#dropdown-type}

Met de <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setDropdownType(java.lang.String)'>setDropdownType()</JavadocLink> methode kan een waarde aan het `type` attribuut van een `ComboBox` worden toegewezen, en een bijbehorende waarde voor het `data-dropdown-for` attribuut in de dropdown van de `ComboBox`. Dit is nuttig voor styling, omdat de dropdown uit zijn huidige positie in de DOM wordt gehaald en naar het einde van de pagina-inhoud wordt verplaatst wanneer deze wordt geopend.

Deze ontkoppeling creëert een situatie waarin het direct targeten van de dropdown met CSS of shadow part selectors vanuit de bovenliggende component uitdagend wordt, tenzij je gebruik maakt van het dropdown-type attribuut.

In de onderstaande demo is het Dropdown-type ingesteld en gebruikt in het CSS-bestand om de dropdown te selecteren en de achtergrondkleur te veranderen.

<ComponentDemo
path='/webforj/comboboxdropdowntype'
files={[
  'src/main/java/com/webforj/samples/views/lists/combobox/ComboBoxDropdownTypeView.java',
  'src/main/resources/static/css/lists/combobox/comboBoxDropDownType.css',
]}
height='250px'
/>

## Max. rijen tellen {#max-row-count}

Standaard wordt het aantal rijen dat wordt weergegeven in de dropdown van een `ComboBox` verhoogd om de inhoud in te passen. Met de <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setMaxRowCount(int)'>setMaxRowCount()</JavadocLink> methode kan echter worden gecontroleerd hoeveel items worden weergegeven.

:::caution
Een getal dat kleiner dan of gelijk aan 0 is, zal ertoe leiden dat deze eigenschap wordt opgeheven.
:::

<ComponentDemo
path='/webforj/comboboxmaxrow'
files={['src/main/java/com/webforj/samples/views/lists/combobox/ComboBoxMaxRowView.java']}
height='450px'
/>

## Openen en sluiten {#opening-and-closing}

De zichtbaarheid van de opties voor een `ComboBox` kan programmatisch worden gecontroleerd met de `open()` en `close()` methoden. Deze methoden stellen je in staat om de lijst met opties voor selectie weer te geven of deze indien nodig te verbergen, wat meer flexibiliteit biedt bij het beheren van het gedrag van een `ComboBox`.

Bovendien heeft webforJ gebeurtenisluisteraars voor wanneer de `ComboBox` wordt gesloten en wanneer deze wordt geopend, waardoor je meer controle hebt om specifieke acties te triggeren.

```Java
//Focussen of het volgende component in een formulier openen
ComboBox universiteit = new ComboBox("Universiteit");
ComboBox major = new ComboBox("Hoofdvak");
Button indienen = new Button("Indienen");

//... Voeg lijsten van universiteiten en hoofdvakken toe

universiteit.onClose( e ->{
  major.open();
});

major.onClose( e ->{
  indienen.focus();
});
```

## Openingsdimensies {#opening-dimensions}

De `ComboBox` component heeft methoden waarmee de afmetingen van de dropdown kunnen worden gemanipuleerd. De **maximale hoogte** en **minimale breedte** van de dropdown kunnen worden ingesteld met de <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setOpenHeight(int)'>setOpenHeight()</JavadocLink> en <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setOpenWidth(int)'>setOpenWidth()</JavadocLink> methoden, respectievelijk.

:::tip
Een `String`-waarde doorgeven aan een van deze methoden maakt het mogelijk om [elke geldige CSS-eenheid](https://developer.mozilla.org/en-US/docs/Learn/CSS/Building_blocks/Values_and_units) toe te passen, zoals pixels, viewport-dimensies of andere geldige regels. Een `int` doorgeven stelt de waarde in pixels in.
:::

## Markeren {#highlighting}

Bij het werken met een `ComboBox` kun je aanpassen wanneer de tekst wordt gemarkeerd op basis van hoe de component focus krijgt. Deze functie kan invoerfouten verminderen wanneer gebruikers formulieren invullen en kan de algehele navigatie-ervaring verbeteren. Wijzig het markeer gedrag met de `setHighlightOnFocus()` methode met een van de ingebouwde `HasHighlightOnFocus.Behavior` enums:

- `ALL`
Inhoud van de component wordt altijd automatisch gemarkeerd wanneer de component focus krijgt.
- `FOCUS`
Inhoud van de component wordt automatisch gemarkeerd wanneer de component onder programmabeheersing focus krijgt.
- `FOCUS_OR_KEY`
Inhoud van de component wordt automatisch gemarkeerd wanneer de component onder programmabeheersing focus krijgt of door op de tabtoets erin te komen.
- `FOCUS_OR_MOUSE`
Inhoud van de component wordt automatisch gemarkeerd wanneer de component onder programmabeheersing focus krijgt of door erin te klikken met de muis.
- `KEY`
Inhoud van de component wordt automatisch gemarkeerd wanneer de component focus krijgt door er met de tabtoets in te gaan.
- `KEY_MOUSE`
Inhoud van de component wordt automatisch gemarkeerd wanneer de component focus krijgt door er met de tabtoets in te gaan of door erin te klikken met de muis.
- `MOUSE`
Inhoud van de component wordt automatisch gemarkeerd wanneer de component focus krijgt door erin te klikken met de muis.
- `NONE`
Inhoud van de component wordt nooit automatisch gemarkeerd wanneer de component focus krijgt.

:::note
Als inhoud was gemarkeerd bij het verliezen van focus, wordt deze opnieuw gemarkeerd bij het opnieuw krijgen van focus, ongeacht het ingestelde gedrag.
:::

## Prefix en suffix {#prefix-and-suffix}

Slots bieden flexibele opties voor het verbeteren van de functionaliteit van een `ComboBox`. Je kunt iconen, labels, laadsnelheidspinners, wissen/reset mogelijkheden, avatar/profiel foto’s en andere nuttige componenten binnen een `ComboBox` nestelen om de bedoelde betekenis verder duidelijk te maken voor gebruikers. De `ComboBox` heeft twee slots: de `prefix` en `suffix` slots. Gebruik de `setPrefixComponent()` en `setSuffixComponent()` methoden om verschillende componenten voor en na de opties binnen een `ComboBox` in te voegen.

```java
ComboBox comboBox = new ComboBox());
  comboBox.setPrefixComponent(TablerIcon.create("box"));
  comboBox.setSuffixComponent(TablerIcon.create("box"));
```

## Styling {#styling}

<TableBuilder name="ComboBox" />

## Beste praktijken {#best-practices}

Om een optimale gebruikerservaring te waarborgen bij het gebruik van de `ComboBox` component, overweeg de volgende beste praktijken:

1. **Preload Veelvoorkomende Waarden**: Als er veelvoorkomende of vaak gebruikte items zijn, laad deze dan voor in de lijst van de `ComboBox`. Dit versnelt de selectie voor vaak gekozen items en bevordert consistentie.

2. **Gebruiksvriendelijke Labels**: Zorg ervoor dat de weergegeven labels voor elke optie gebruiksvriendelijk en zelfverklarend zijn. Zorg ervoor dat gebruikers gemakkelijk de bedoeling van elke keuze kunnen begrijpen.

3. **Validatie**: Implementeer invoervalidatie om aangepaste invoer te verwerken. Controleer op nauwkeurigheid en consistentie van gegevens. Je wilt misschien suggesties geven voor correcties of bevestigingen voor onduidelijke invoer.

4. **Standaardselectie**: Stel een standaardselectie in, vooral als er een veelvoorkomende of aanbevolen keuze is. Dit verbetert de gebruikerservaring door de noodzaak voor extra klikken te verminderen.

5. **ComboBox vs. Andere Lijstcomponenten**: Een `ComboBox` is de beste keuze als je een enkele invoer van de gebruiker nodig hebt en je hen vooraf bepaalde keuzes en de mogelijkheid wilt bieden om hun invoer aan te passen. Een andere lijstcomponent kan beter zijn als je de volgende gedragingen nodig hebt:
    - Meervoudige selectie en alles tegelijk weergeven: [Lijstbox](./list-box.md)
    - Voorkomen van aangepaste invoer: [ChoiceBox](./choice-box.md)
