---
sidebar_position: 10
title: ComboBox
slug: combobox
_i18n_hash: b1ed30653bdca5af11b2f138a491baef
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-combobox" />
<DocChip chip='since' label='23.05' />
<JavadocLink type="foundation" location="com/webforj/component/list/ComboBox" top='true'/>

De `ComboBox`-component combineert een dropdown-lijst met een tekstinvoer, zodat gebruikers kunnen kiezen uit vooraf gedefinieerde opties of een aangepaste waarde kunnen typen. Wanneer aangepaste invoer naast een set voorgestelde opties moet worden toegestaan, vult het de leemte die `ChoiceBox` niet dekt.

<!-- INTRO_END -->

## Gebruiken {#usages}

<ParentLink parent="Lijst" />

De ComboBox-component is een veelzijdig invoerelement dat de functionaliteiten van zowel een dropdown-lijst als een tekstinvoerveld combineert. Het stelt gebruikers in staat om items te selecteren uit een vooraf gedefinieerde lijst of indien nodig aangepaste waarden in te voeren. Deze sectie verkent veelvoorkomende toepassingen van de ComboBox-component in verschillende scenario's:

1. **Productzoek- en invoer**: Gebruik een ComboBox om een productzoek- en invoerfunctie te implementeren. Gebruikers kunnen ofwel een product selecteren uit een vooraf gedefinieerde lijst of een aangepaste productnaam typen. Dit is nuttig voor toepassingen zoals e-commerce sites waar producten talrijk en divers zijn.

2. **Tagselectie en invoer**: In toepassingen die content-tagging omvatten, kan een ComboBox als een uitstekende keuze dienen. Gebruikers kunnen kiezen uit een lijst van bestaande tags of aangepaste tags toevoegen door ze te typen. Dit is nuttig voor het organiseren en categoriseren van content. Voorbeelden van dergelijke tags zijn:
    >- Projectlabels: In een projectmanagementtool kunnen gebruikers labels of tags (bijv. "Dringend", "In Behandeling", "Voltooid") selecteren om taken of projecten te categoriseren, en ze kunnen aangepaste labels creëren indien nodig.
    >- Ingrediënten voor recepten: In een kook- of recepten-app kunnen gebruikers ingrediënten uit een lijst selecteren (bijv. "Tomaten", "Uien", "Kip") of hun eigen ingrediënten toevoegen voor aangepaste recepten.
    >- Locatietags: In een mapping- of geotaggingtoepassing kunnen gebruikers vooraf gedefinieerde locatietags kiezen (bijv. "Strand", "Stad", "Park") of aangepaste tags creëren om specifieke plaatsen op een kaart te markeren.

3. **Gegevensinvoer met voorgestelde waarden**: In gegevensinvoervormen kan een ComboBox worden gebruikt om de invoer te versnellen door een lijst van voorgestelde waarden op basis van gebruikersinvoer te bieden. Dit helpt gebruikers om gegevens nauwkeurig en efficiënt in te voeren.

    :::tip
    De `ComboBox` moet worden gebruikt wanneer gebruikers aangepaste waarden mogen invoeren. Als alleen vooraf ingestelde waarden gewenst zijn, gebruik dan een [`ChoiceBox`](./choice-box.md).
    :::

## Aangepaste waarde {#custom-value}

Het wijzigen van de aangepaste waarde-eigenschap biedt controle over of een gebruiker de waarde in het invoerveld van de `ComboBox`-component kan wijzigen. Als `true`, wat de standaardwaarde is, kan een gebruiker de waarde wijzigen. Als het is ingesteld op `false`, kan de gebruiker de waarde niet wijzigen. Dit kan worden ingesteld met behulp van de <JavadocLink type="foundation" location="com/webforj/component/list/ComboBox" code='true' suffix='#setAllowCustomValue(boolean)'>setAllowCustomValue()</JavadocLink> methode.

<ComponentDemo 
path='/webforj/comboboxcustomvalue?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/lists/combobox/ComboBoxCustomValueView.java'
height = '200px'
/>

## Plaatsvervanger {#placeholder}

Een plaatsvervanger kan worden ingesteld voor een `ComboBox` die wordt weergegeven in het tekstveld van de component wanneer het leeg is om gebruikers te vragen om de gewenste invoer in het veld. Dit kan worden gedaan met behulp van de <JavadocLink type="foundation" location="com/webforj/component/list/ComboBox" code='true' suffix='#setPlaceholder(java.lang.String)'>setPlaceholder()</JavadocLink> methode.

<ComponentDemo 
path='/webforj/comboboxplaceholder?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/lists/combobox/ComboBoxPlaceholderView.java'
height = '200px'
/>

## Dropdown-type {#dropdown-type}

Met de <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setDropdownType(java.lang.String)'>setDropdownType()</JavadocLink> methode kan een waarde worden toegewezen aan de `type`-attribuut van een `ComboBox`, en een bijbehorende waarde voor het `data-dropdown-for` attribuut in de dropdown van de `ComboBox`. Dit is nuttig voor styling, omdat de dropdown uit zijn huidige positie in de DOM wordt gehaald en naar het einde van de paginainhoud wordt verplaatst wanneer deze wordt geopend.

Deze loskoppeling creëert een situatie waarin het moeilijk wordt om de dropdown rechtstreeks te targeten met CSS- of schaduwdeel-selectoren van de bovenliggende component, tenzij je gebruikmaakt van het dropdown-type-attribuut.

In de demo hieronder wordt het Dropdown-type ingesteld en gebruikt in het CSS-bestand om de dropdown te selecteren en de achtergrondkleur te veranderen.

<ComponentDemo 
path='/webforj/comboboxdropdowntype?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/lists/combobox/ComboBoxDropdownTypeView.java'
cssURL='/css/lists/combobox/comboBoxDropDownType.css'
height='250px'
/>

## Maximaal aantal rijen {#max-row-count}

Standaard wordt het aantal weergegeven rijen in de dropdown van een `ComboBox` vergroot om de inhoud te passen. Het gebruik van de <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setMaxRowCount(int)'>setMaxRowCount()</JavadocLink> methode maakt het mogelijk om controle uit te oefenen over hoeveel items worden weergegeven.

:::caution
Het gebruik van een getal dat kleiner is dan of gelijk is aan 0 resulteert in het uitschakelen van deze eigenschap.
:::

<ComponentDemo 
path='/webforj/comboboxmaxrow?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/lists/combobox/ComboBoxMaxRowView.java'
height='450px'
/>

## Openen en sluiten {#opening-and-closing}

De zichtbaarheid van de opties voor een `ComboBox` kan programmatic worden gecontroleerd met de `open()` en `close()` methoden. Deze methoden stellen je in staat om de lijst met opties voor selectie weer te geven of te verbergen indien nodig, wat meer flexibiliteit biedt in het beheren van het gedrag van een `ComboBox`.

Bovendien heeft webforJ gebeurtenislijsten voor wanneer de `ComboBox` wordt gesloten en wanneer deze wordt geopend, waardoor je meer controle hebt om specifieke acties te activeren.

```Java
//Focus of open het volgende component in een formulier
ComboBox universiteit = new ComboBox("Universiteit");
ComboBox hoofdvak = new ComboBox("Hoofdvak");
Button indienen = new Button("Indienen");

//... Voeg lijsten van universiteiten en hoofdvakken toe

universiteit.onClose( e ->{
  hoofdvak.open();
});

hoofdvak.onClose( e ->{
  indienen.focus();
});
```

## Openingsdimensies {#opening-dimensions}

De `ComboBox`-component heeft methoden die manipulatie van de dropdown-dimensies mogelijk maken. De **maximale hoogte** en **minimale breedte** van de dropdown kunnen worden ingesteld met behulp van de <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setOpenHeight(int)'>setOpenHeight()</JavadocLink> en <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setOpenWidth(int)'>setOpenWidth()</JavadocLink> methoden, respectievelijk.

:::tip
Door een `String`-waarde naar een van deze methoden door te geven, kunnen [alle geldige CSS-eenheden](https://developer.mozilla.org/en-US/docs/Learn/CSS/Building_blocks/Values_and_units) worden toegepast, zoals pixels, viewport-dimensies of andere geldige regels. Door een `int` door te geven, wordt de waarde in pixels ingesteld.
:::

## Markeren {#highlighting}

Bij het werken met een `ComboBox` kun je aanpassen wanneer de tekst wordt gemarkeerd, afhankelijk van hoe de component focus krijgt. Deze functie kan invoerfouten verminderen wanneer gebruikers formulieren invullen en kan de algehele navigatie-ervaring verbeteren. Wijzig het markeer gedrag met de `setHighlightOnFocus()` methode met een van de ingebouwde `HasHighlightOnFocus.Behavior` enums:

- `ALL`
De inhoud van de component wordt altijd automatisch gemarkeerd wanneer de component focus krijgt.
- `FOCUS`
De inhoud van de component wordt automatisch gemarkeerd wanneer de component focus krijgt onder programmatische controle.
- `FOCUS_OR_KEY`
De inhoud van de component wordt automatisch gemarkeerd wanneer de component focus krijgt onder programmatische controle of door erop te tabben.
- `FOCUS_OR_MOUSE`
De inhoud van de component wordt automatisch gemarkeerd wanneer de component focus krijgt onder programmatische controle of door erop te klikken met de muis.
- `KEY`
De inhoud van de component wordt automatisch gemarkeerd wanneer de component focus krijgt door erop te tabben.
- `KEY_MOUSE`
De inhoud van de component wordt automatisch gemarkeerd wanneer de component focus krijgt door erop te tabben of door erop te klikken met de muis.
- `MOUSE`
De inhoud van de component wordt automatisch gemarkeerd wanneer de component focus krijgt door erop te klikken met de muis.
- `NONE`
De inhoud van de component wordt nooit automatisch gemarkeerd wanneer de component focus krijgt.

:::note
Als de inhoud was gemarkeerd bij het verliezen van de focus, zal het opnieuw worden gemarkeerd bij het terugkrijgen van de focus, ongeacht het ingestelde gedrag.
:::

## Voorvoegsel en achtervoegsel {#prefix-and-suffix}

Slots bieden flexibele mogelijkheden om de capaciteit van een `ComboBox` te verbeteren. Je kunt iconen, labels, laadspinners, wissen/resetmogelijkheden, avatar/profielafbeeldingen en andere nuttige componenten binnen een `ComboBox` nestelen om de bedoelde betekenis voor gebruikers verder te verduidelijken. De `ComboBox` heeft twee slots: de `prefix`- en `suffix`-slots. Gebruik de `setPrefixComponent()` en `setSuffixComponent()` methoden om verschillende componenten vóór en na de opties binnen een `ComboBox` in te voegen.

```java
ComboBox comboBox = new ComboBox());
  comboBox.setPrefixComponent(TablerIcon.create("box"));
  comboBox.setSuffixComponent(TablerIcon.create("box"));
```

## Styling {#styling}

<TableBuilder name="ComboBox" />

## Beste praktijken {#best-practices}

Om een optimale gebruikerservaring te garanderen bij het gebruik van de `ComboBox`-component, overweeg de volgende beste praktijken:

1. **Vooraf laden van veelgebruikte waarden**: Als er veelvoorkomende of vaak gebruikte items zijn, laad ze dan vooraf in de `ComboBox`-lijst. Dit versnelt de selectie voor vaak gekozen items en moedigt consistentie aan.

2. **Gebruiksvriendelijke labels**: Zorg ervoor dat de weergegeven labels voor elke optie gebruiksvriendelijk en zelfverklarend zijn. Zorg ervoor dat gebruikers de bedoeling van elke keuze gemakkelijk begrijpen.

3. **Validatie**: Implementeer invoervalidatie om aangepaste invoer af te handelen. Controleer op datanauwkeurigheid en consistentie. Je wilt mogelijk suggesties doen voor correcties of bevestigingen voor dubbelzinnige invoer.

4. **Standaardselectie**: Stel een standaardselectie in, vooral als er een veelvoorkomende of aanbevolen keuze is. Dit verbetert de gebruikerservaring door de noodzaak voor extra klikken te verminderen.

5. **ComboBox versus andere lijstcomponenten**: Een `ComboBox` is de beste keuze als je een enkele invoer van de gebruiker nodig hebt en je ze wilt voorzien van vooraf bepaalde keuzes en de mogelijkheid om hun invoer aan te passen. Een andere lijstcomponent kan beter zijn als je de volgende gedragingen nodig hebt:
    - Meerdere selecties en alle items tegelijk weergeven: [ListBox](./list-box.md)
    - Voorkom aangepaste invoer: [ChoiceBox](./choice-box.md)
