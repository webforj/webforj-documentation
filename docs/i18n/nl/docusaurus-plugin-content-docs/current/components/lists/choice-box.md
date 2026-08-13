---
sidebar_position: 5
title: ChoiceBox
slug: choicebox
description: >-
  Pick a single value from a fixed set with the ChoiceBox dropdown, including
  dropdown type styling, max row count, and keyboard navigation.
_i18n_hash: 1c1224ca662a0e268606dc1cb6a0e96a
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-choicebox" />
<DocChip chip='since' label='23.05' />
<JavadocLink type="foundation" location="com/webforj/component/list/ChoiceBox" top='true'/>

De `ChoiceBox` component presenteert een dropdownlijst waaruit gebruikers een enkele optie kunnen selecteren. Wanneer een selectie is gemaakt, wordt de gekozen waarde in de knop weergegeven. Het is een goede keuze wanneer gebruikers moeten kiezen uit een vaste set van vooraf gedefinieerde keuzes, en pijltoetsen kunnen worden gebruikt om door de lijst te navigeren.

<!-- INTRO_END -->

## Gebruik {#usages}

<ParentLink parent="List" />

`ChoiceBox` componenten worden voor verschillende doeleinden gebruikt, zoals het selecteren van items uit een menu, kiezen uit een lijst van categorieën of het maken van keuzes uit vooraf gedefinieerde sets. Ze bieden een georganiseerde en visueel aantrekkelijke manier voor gebruikers om selecties te maken, vooral wanneer er meerdere opties beschikbaar zijn. Veelvoorkomende toepassingen zijn:

1. **Selectie door de gebruiker**: Het primaire doel van een `ChoiceBox` is om gebruikers in staat te stellen een enkele optie uit een lijst te selecteren. Dit is waardevol in applicaties die gebruikers vragen om keuzes te maken, zoals:
    - Kiezen uit een lijst van categorieën
    - Opties kiezen uit vooraf gedefinieerde sets

2. **Formuliervelden**: Bij het ontwerpen van formulieren die gebruikers vragen om specifieke opties in te voeren, vereenvoudigt de `ChoiceBox` het selectieproces. Of het nu gaat om het selecteren van een land, staat of een andere optie uit een vooraf gedefinieerde lijst, de `ChoiceBox` stroomlijnt het invoerproces.

3. **Filteren en sorteren**: `ChoiceBox` kan worden toegepast voor filter- en sorteertaken in applicaties. Gebruikers kunnen filtercriteria of sorteervoorkeuren kiezen uit een lijst, wat de organisatie en navigatie van gegevens vergemakkelijkt.

4. **Configuratie en instellingen**: Wanneer uw applicatie instellingen of configuratieopties bevat, biedt de `ChoiceBox` een intuïtieve manier voor gebruikers om voorkeuren aan te passen. Gebruikers kunnen instellingen uit een lijst kiezen, waardoor het gemakkelijk is om de applicatie aan te passen aan hun behoeften.

:::tip
De `ChoiceBox` is bedoeld voor gebruik wanneer een vooraf ingesteld aantal opties beschikbaar is, en aangepaste opties niet zijn toegestaan of inbegrepen. Als het de bedoeling is om gebruikers aangepaste waarden te laten invoeren, gebruik dan in plaats daarvan een [`ComboBox`](./combo-box.md).
:::

## Dropdown type {#dropdown-type}

Het gebruik van de <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setDropdownType(java.lang.String)'>setDropdownType()</JavadocLink> methode wijst een waarde toe aan het `type` attribuut van een `ChoiceBox`, en een overeenkomstige waarde voor het `data-dropdown-for` attribuut in de dropdown van de `ChoiceBox`. Dit is nuttig voor styling, aangezien de dropdown uit zijn huidige positie in de DOM wordt gehaald en aan het einde van de pagina-inhoud wordt geplaatst wanneer deze geopend is.

Deze ontkoppeling creëert een situatie waarin het uitdagend wordt om de dropdown rechtstreeks te targeten met CSS of schaduwdeelselectors vanuit de oudercomponent, tenzij je gebruik maakt van het dropdown-type attribuut.

In de onderstaande demo is het dropdown-type ingesteld en gebruikt in het CSS-bestand om een optie te vergroten wanneer je eroverheen hovert.

<ComponentDemo
path='/webforj/choiceboxdropdowntype'
files={[
  'src/main/java/com/webforj/samples/views/lists/choicebox/ChoiceboxDropdownTypeView.java',
  'src/main/frontend/css/lists/combobox/comboBoxDropDownType.css',
]}
height='250px'
/>

## Maximaal aantal rijen {#max-row-count}

Standaard wordt het aantal weergegeven rijen in de dropdown van een `ChoiceBox` verhoogd om de inhoud te passen. Het gebruik van de <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setMaxRowCount(int)'>setMaxRowCount()</JavadocLink> methode maakt het mogelijk om te bepalen hoeveel items er worden weergegeven.

:::tip
Het gebruik van een getal dat kleiner dan of gelijk aan 0 is, zal deze eigenschap uitschakelen.
:::

<ComponentDemo
path='/webforj/choiceboxmaxrow'
files={['src/main/java/com/webforj/samples/views/lists/choicebox/ChoiceboxMaxRowView.java']}
height='450px'
/>

## Openen en sluiten {#opening-and-closing}

De zichtbaarheid van de opties voor een `ChoiceBox` kan programmatisch worden gecontroleerd met de `open()` en `close()` methoden. Deze methoden stellen je in staat om de lijst van opties voor selectie weer te geven of te verbergen wanneer dat nodig is, wat meer flexibiliteit biedt in het beheren van het gedrag van een `ChoiceBox`.

Daarnaast heeft webforJ evenementluisteraars voor wanneer de `ChoiceBox` wordt gesloten en wanneer deze wordt geopend, waardoor je meer controle hebt om specifieke acties te triggeren.

```Java
//Focus of open de volgende component in een formulier
ChoiceBox university = new ChoiceBox("Universiteit");
ChoiceBox major = new ChoiceBox("Hoofdvak");
Button submit = new Button("Indienen");

//... Voeg lijsten van universiteiten en hoofdvakken toe

university.onClose( e ->{
  major.focus();
});

major.onClose( e ->{
  submit.focus();
});
```

## Openingsdimensies {#opening-dimensions}

De `ChoiceBox` component heeft methoden waarmee je de dimensies van de dropdown kunt manipuleren. De **maximale hoogte** en **minimale breedte** van de dropdown kunnen worden ingesteld met de <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setOpenHeight(int)'>setOpenHeight()</JavadocLink> en <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setOpenWidth(int)'>setOpenWidth()</JavadocLink> methoden, respectievelijk.

:::tip
Het doorgeven van een `String` waarde aan een van deze methoden stelt je in staat om [elke geldige CSS-eenheid](https://developer.mozilla.org/en-US/docs/Learn/CSS/Building_blocks/Values_and_units) toe te passen, zoals pixels, viewport-dimensies of andere geldige regels. Het doorgeven van een `int` zal de waarde in pixels instellen.
:::

## Voorvoegsel en achtervoegsel {#prefix-and-suffix}

Slots bieden flexibele opties voor het verbeteren van de mogelijkheden van een `ChoiceBox`. Je kunt iconen, labels, laadspinners, wissen/reset mogelijkheden, avatar/profielafbeeldingen en andere nuttige componenten binnen een `ChoiceBox` nestelen om de bedoelde betekenis voor gebruikers verder te verduidelijken. De `ChoiceBox` heeft twee slots: de `prefix` en `suffix` slots. Gebruik de `setPrefixComponent()` en `setSuffixComponent()` methoden om verschillende componenten voor en na de weergegeven optie binnen een `ChoiceBox` in te voegen.

```java
ComboBox choiceBox = new ChoiceBox());
  choiceBox.setPrefixComponent(TablerIcon.create("box"));
  choiceBox.setSuffixComponent(TablerIcon.create("box"));
```

## Styling {#styling}

<TableBuilder name="ChoiceBox" />

## Beste praktijken {#best-practices}

Om een optimale gebruikerservaring te waarborgen bij het gebruik van de `ChoiceBox` component, overweeg de volgende beste praktijken:

1. **Duidelijke en beperkte opties**: Houd de lijst met keuzes zo kort mogelijk en relevant voor de taak van de gebruiker. Een `ChoiceBox` is ideaal voor het presenteren van een duidelijke lijst van opties.

2. **Gebruiksvriendelijke labels**: Zorg ervoor dat de weergegeven labels voor elke optie gebruiksvriendelijk en zelfverklarend zijn. Zorg ervoor dat gebruikers gemakkelijk het doel van elke keuze kunnen begrijpen.

3. **Standaardkeuze**: Stel een standaardselectie in wanneer de ChoiceBox voor het eerst wordt weergegeven. Dit zorgt voor een vooraf geselecteerde optie, waardoor het aantal interacties dat nodig is om een keuze te maken, wordt verminderd.

4. **ChoiceBox vs. andere lijstcomponenten**: Een `ChoiceBox` is de beste keuze als je de gebruikersinvoer wilt beperken tot een enkele keuze uit een lijst met predetermined opties. Een andere lijstcomponent kan beter zijn als je de volgende gedragingen nodig hebt:
    - Meervoudige selectie en toont alle items tegelijk: [`ListBox`](./list-box.md)
    - Aangepaste invoer toestaan: [`ComboBox`](./combo-box.md)
