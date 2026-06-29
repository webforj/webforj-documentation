---
sidebar_position: 5
title: ChoiceBox
slug: choicebox
description: >-
  Pick a single value from a fixed set with the ChoiceBox dropdown, including
  dropdown type styling, max row count, and keyboard navigation.
_i18n_hash: cf4d092418fcf1f593b8b8d00a47344b
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-choicebox" />
<DocChip chip='since' label='23.05' />
<JavadocLink type="foundation" location="com/webforj/component/list/ChoiceBox" top='true'/>

De `ChoiceBox`-component presenteert een keuzelijst waaruit gebruikers een enkele optie kunnen selecteren. Wanneer er een keuze is gemaakt, wordt de gekozen waarde in de knop weergegeven. Het is een goede keuze wanneer gebruikers moeten kiezen uit een vaste set van vooraf gedefinieerde keuzes, en de pijltoetsen kunnen worden gebruikt om door de lijst te navigeren.

<!-- INTRO_END -->

## Usages {#usages}

<ParentLink parent="List" />

`ChoiceBox`-componenten worden voor verschillende doeleinden gebruikt, zoals het selecteren van items uit een menu, kiezen uit een lijst van categorieën, of het kiezen van opties uit vooraf gedefinieerde sets. Ze bieden een georganiseerde en visueel aantrekkelijke manier voor gebruikers om keuzes te maken, vooral wanneer er meerdere opties beschikbaar zijn. Veelvoorkomende toepassingen zijn onder andere:

1. **Selectie van opties door gebruikers**: Het primaire doel van een `ChoiceBox` is gebruikers in staat te stellen een enkele optie uit een lijst te selecteren. Dit is waardevol in applicaties die vereisen dat gebruikers keuzes maken, zoals:
    - Kiezen uit een lijst van categorieën
    - Opties kiezen uit vooraf gedefinieerde sets

2. **Formulierinvoer**: Bij het ontwerpen van formulieren die vereisen dat gebruikers specifieke opties invoeren, vereenvoudigt de `ChoiceBox` het selectieproces. Of het nu gaat om het selecteren van een land, staat of een andere optie uit een vooraf gedefinieerde lijst, de `ChoiceBox` stroomlijnt het invoerproces.

3. **Filteren en sorteren**: `ChoiceBox` kan worden gebruikt voor filter- en sorteertaken in applicaties. Gebruikers kunnen filtercriteria of sorteervoorkeuren uit een lijst kiezen, wat de organisatie en navigatie van gegevens vergemakkelijkt.

4. **Configuratie en instellingen**: Wanneer uw applicatie instellingen of configuratieopties bevat, biedt de `ChoiceBox` een intuïtieve manier voor gebruikers om voorkeuren aan te passen. Gebruikers kunnen instellingen uit een lijst kiezen, waardoor het eenvoudig is om de applicatie aan hun behoeften aan te passen.

:::tip
De `ChoiceBox` is bedoeld voor gebruik wanneer er een vooraf bepaalde hoeveelheid opties beschikbaar is, en er moeten geen aangepaste opties worden toegestaan of opgenomen. Als het de bedoeling is om gebruikers aangepaste waarden te laten invoeren, gebruik dan in plaats daarvan een [`ComboBox`](./combo-box.md).
:::

## Dropdown type {#dropdown-type}

Met de <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setDropdownType(java.lang.String)'>setDropdownType()</JavadocLink>-methode wordt een waarde toegewezen aan het `type`-attribuut van een `ChoiceBox`, en een bijbehorende waarde voor het `data-dropdown-for`-attribuut in de dropdown van de `ChoiceBox`. Dit is nuttig voor styling, omdat de dropdown uit zijn huidige positie in de DOM wordt gehaald en aan het einde van de pagina-inhoud wordt verplaatst wanneer deze wordt geopend.

<!-- ![example type](/img/components/_images/choicebox/type.png)
![example type](/img/components/_images/choicebox/type_zoomed.png) -->

Deze ontkoppeling creëert een situatie waarin het rechtstreeks targeten van de dropdown met CSS of schaduwdeelselectoren vanuit de bovenliggende component moeilijk wordt, tenzij u gebruik maakt van het dropdown type attribuut.

In de onderstaande demo wordt het Dropdown-type ingesteld en gebruik in het CSS-bestand om een optie te vergroten wanneer je er met de muis overheen beweegt.

<ComponentDemo
path='/webforj/choiceboxdropdowntype'
files={[
  'src/main/java/com/webforj/samples/views/lists/choicebox/ChoiceboxDropdownTypeView.java',
  'src/main/resources/static/css/lists/combobox/comboBoxDropDownType.css',
]}
height='250px'
/>

## Max rijcount {#max-row-count}

Standaard wordt het aantal rijen dat in de dropdown van een `ChoiceBox` wordt weergegeven verhoogd om de inhoud te passen. Echter, met de <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setMaxRowCount(int)'>setMaxRowCount()</JavadocLink>-methode kan er controle worden uitgeoefend over het aantal weergegeven items.

:::tip
Het gebruik van een getal dat kleiner dan of gelijk aan 0 is, resulteert in het unsetten van deze eigenschap.
:::

<ComponentDemo
path='/webforj/choiceboxmaxrow'
files={['src/main/java/com/webforj/samples/views/lists/choicebox/ChoiceboxMaxRowView.java']}
height='450px'
/>

## openen en sluiten {#opening-and-closing}

De zichtbaarheid van de opties voor een `ChoiceBox` kan programmatisch worden gecontroleerd met de `open()`- en `close()`-methoden. Deze methoden stellen je in staat om de lijst met opties voor selectie weer te geven of te verbergen indien nodig, waardoor er meer flexibiliteit ontstaat bij het beheren van het gedrag van een `ChoiceBox`.

Bovendien heeft webforJ eventlisteners voor wanneer de `ChoiceBox` wordt gesloten en wanneer deze wordt geopend, zodat u meer controle heeft om specifieke acties te triggeren.

```Java
//Focus of open de volgende component in een formulier
ChoiceBox universiteit = new ChoiceBox("Universiteit");
ChoiceBox major = new ChoiceBox("Hoofdvak");
Button verzenden = new Button("Verzenden");

//... Voeg lijsten van universiteiten en hoofdvakken toe

universiteit.onClose( e ->{
  major.focus();
});

major.onClose( e ->{
  verzenden.focus();
});
```

## Openingsdimensies {#opening-dimensions}

De `ChoiceBox`-component heeft methoden waarmee de dimensies van de dropdown kunnen worden gemanipuleerd. De **maximale hoogte** en **minimale breedte** van de dropdown kunnen worden ingesteld met de <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setOpenHeight(int)'>setOpenHeight()</JavadocLink>- en <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setOpenWidth(int)'>setOpenWidth()</JavadocLink>-methoden, respectievelijk.

:::tip
Het doorgeven van een `String`-waarde aan een van deze methoden stelt u in staat om [elke geldige CSS-eenheid](https://developer.mozilla.org/en-US/docs/Learn/CSS/Building_blocks/Values_and_units) toe te passen, zoals pixels, viewportdimensies of andere geldige regels. Het doorgeven van een `int` stelt de waarde die is doorgegeven in pixels in.
:::

## Voorvoegsel en achtervoegsel {#prefix-and-suffix}

Slots bieden flexibele opties om de functionaliteit van een `ChoiceBox` te verbeteren. U kunt pictogrammen, labels, laadspinners, wissen/resetmogelijkheden, avatar/profielafbeeldingen en andere nuttige componenten genest binnen een `ChoiceBox` hebben om de bedoelde betekenis aan gebruikers verder te verduidelijken. De `ChoiceBox` heeft twee slots: de `prefix`- en `suffix`-slots. Gebruik de `setPrefixComponent()`- en `setSuffixComponent()`-methoden om verschillende componenten vóór en na de weergegeven optie binnen een `ChoiceBox` in te voegen.

```java
ComboBox choiceBox = new ChoiceBox());
  choiceBox.setPrefixComponent(TablerIcon.create("box"));
  choiceBox.setSuffixComponent(TablerIcon.create("box"));
```

## Styling {#styling}

<TableBuilder name="ChoiceBox" />

## Beste werkwijzen {#best-practices}

Om een optimale gebruikerservaring te waarborgen bij het gebruik van de `ChoiceBox`-component, overweeg de volgende beste werkwijzen:

1. **Duidelijke en beperkte opties**: Houd de lijst met keuzes beknopt waar mogelijk, en relevant voor de taak van de gebruiker. Een `ChoiceBox` is ideaal voor het presenteren van een duidelijke lijst van opties.

2. **Gebruiksvriendelijke labels**: Zorg ervoor dat de weergegeven labels voor elke optie gebruiksvriendelijk en vanzelfsprekend zijn. Zorg ervoor dat gebruikers gemakkelijk de bedoeling van elke keuze begrijpen.

3. **Standaardselectie**: Stel een standaardselectie in wanneer de `ChoiceBox` voor het eerst wordt weergegeven. Dit zorgt voor een vooraf geselecteerde optie, waardoor het aantal interacties dat nodig is om een keuze te maken, wordt verminderd.

4. **ChoiceBox vs. andere lijstcomponenten**: Een `ChoiceBox` is de beste keuze als u de invoer van de gebruiker wilt beperken tot een enkele keuze uit een lijst met vooraf bepaalde opties. Een andere lijstcomponent kan beter zijn als u de volgende gedragingen nodig heeft:
    - Meerdere selecties en alle items tegelijk weergeven: [`ListBox`](./list-box.md)
    - Aangepaste invoer toestaan: [`ComboBox`](./combo-box.md)
