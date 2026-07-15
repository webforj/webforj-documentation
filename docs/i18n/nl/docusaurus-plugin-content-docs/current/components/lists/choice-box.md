---
sidebar_position: 5
title: ChoiceBox
slug: choicebox
description: >-
  Pick a single value from a fixed set with the ChoiceBox dropdown, including
  dropdown type styling, max row count, and keyboard navigation.
_i18n_hash: f897ac9d3f5c252ac323762080e1edcf
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-choicebox" />
<DocChip chip='since' label='23.05' />
<JavadocLink type="foundation" location="com/webforj/component/list/ChoiceBox" top='true'/>

De `ChoiceBox`-component presenteert een dropdownlijst waaruit gebruikers een enkele optie kunnen selecteren. Wanneer een selectie is gemaakt, wordt de gekozen waarde in de knop weergegeven. Het is een goede keuze wanneer gebruikers moeten kiezen uit een vast aantal vooraf gedefinieerde keuzes, en de pijltjestoetsen kunnen worden gebruikt om door de lijst te navigeren.

<!-- INTRO_END -->

## Usages {#usages}

<ParentLink parent="List" />

`ChoiceBox`-componenten worden voor verschillende doeleinden gebruikt, zoals het selecteren van items uit een menu, kiezen uit een lijst van categorieën of het kiezen van opties uit vooraf gedefinieerde sets. Ze bieden een georganiseerde en visueel aantrekkelijke manier voor gebruikers om selecties te maken, vooral wanneer er meerdere opties beschikbaar zijn. Veelvoorkomende toepassingen zijn:

1. **Selectie van opties door gebruikers**: Het primaire doel van een `ChoiceBox` is om gebruikers in staat te stellen een enkele optie uit een lijst te selecteren. Dit is waardevol in applicaties die gebruikers vragen om keuzes te maken, zoals:
    - Kiezen uit een lijst van categorieën
    - Opties kiezen uit vooraf gedefinieerde sets

2. **Formulierinvoer**: Bij het ontwerpen van formulieren die vereisen dat gebruikers specifieke opties invoeren, vereenvoudigt de `ChoiceBox` het selectieproces. Of het nu gaat om het selecteren van een land, staat of een andere optie uit een vooraf gedefinieerde lijst, de `ChoiceBox` stroomlijnt het invoerproces.

3. **Filteren en sorteren**: `ChoiceBox` kan worden gebruikt voor filter- en sorteertaken in applicaties. Gebruikers kunnen filtercriteria of sorteervoorkeuren uit een lijst kiezen, wat de organisatie en navigatie van gegevens vergemakkelijkt.

4. **Configuratie en instellingen**: Wanneer uw applicatie instellingen of configuratieopties bevat, biedt de `ChoiceBox` een intuïtieve manier voor gebruikers om voorkeuren aan te passen. Gebruikers kunnen instellingen uit een lijst kiezen, waardoor het eenvoudig wordt om de applicatie aan hun behoeften aan te passen.

:::tip
De `ChoiceBox` is bedoeld voor gebruik wanneer een vooraf ingesteld aantal opties beschikbaar is, en aangepaste opties niet zijn toegestaan of moeten worden opgenomen. Als het de bedoeling is om gebruikers aangepaste waarden te laten invoeren, gebruik dan in plaats daarvan een [`ComboBox`](./combo-box.md).
:::

## Dropdown type {#dropdown-type}

Met de <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setDropdownType(java.lang.String)'>setDropdownType()</JavadocLink>-methode kan er een waarde aan de `type`-attribuut van een `ChoiceBox` worden toegewezen, en een overeenkomstige waarde voor het `data-dropdown-for`-attribuut in de dropdown van de `ChoiceBox`. Dit is nuttig voor styling, aangezien de dropdown uit zijn huidige positie in de DOM wordt gehaald en naar het einde van de pagina-lichaam wordt verplaatst wanneer deze wordt geopend.

<!-- ![example type](/img/components/_images/choicebox/type.png)
![example type](/img/components/_images/choicebox/type_zoomed.png) -->

Deze detachering creëert een situatie waarin het lastig wordt om de dropdown rechtstreeks te targeten met CSS of schaduwdeelselectoren van de bovenliggende component, tenzij u gebruik maakt van het dropdown type-attribuut.

In de demo hieronder is het dropdown type ingesteld en wordt het gebruikt in het CSS-bestand om een optie te vergroten wanneer u eroverheen hoverd.

<ComponentDemo
path='/webforj/choiceboxdropdowntype'
files={[
  'src/main/java/com/webforj/samples/views/lists/choicebox/ChoiceboxDropdownTypeView.java',
  'src/main/frontend/css/lists/combobox/comboBoxDropDownType.css',
]}
height='250px'
/>

## Max row count {#max-row-count}

Standaard zal het aantal rijen dat in de dropdown van een `ChoiceBox` wordt weergegeven, worden verhoogd om de inhoud te passen. Echter, met de <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setMaxRowCount(int)'>setMaxRowCount()</JavadocLink>-methode kan de controle over hoeveel items worden weergegeven worden uitgevoerd.

:::tip
Een getal dat kleiner dan of gelijk aan 0 is, zal resulteren in het ongedaan maken van deze eigenschap.
:::

<ComponentDemo
path='/webforj/choiceboxmaxrow'
files={['src/main/java/com/webforj/samples/views/lists/choicebox/ChoiceboxMaxRowView.java']}
height='450px'
/>

## Opening and closing {#opening-and-closing}

De zichtbaarheid van de opties voor een `ChoiceBox` kan programmatisch worden beheerd met de `open()` en `close()`-methoden. Deze methoden stellen u in staat om de lijst met opties voor selectie weer te geven of deze indien nodig te verbergen, wat grotere flexibiliteit biedt bij het beheren van het gedrag van een `ChoiceBox`.

Bovendien heeft webforJ gebeurtenislisteners voor wanneer de `ChoiceBox` wordt gesloten en wanneer deze wordt geopend, waardoor u meer controle heeft om specifieke acties te triggeren.

```Java
//Focus of open de volgende component in een formulier
ChoiceBox university = new ChoiceBox("University");
ChoiceBox major = new ChoiceBox("Major");
Button submit = new Button("Submit");

//... Voeg lijsten van universiteiten en majors toe

university.onClose( e ->{
  major.focus();
});

major.onClose( e ->{
  submit.focus();
});
```

## Opening dimensions {#opening-dimensions}

De `ChoiceBox`-component heeft methoden waarmee de afmetingen van de dropdown kunnen worden gemanipuleerd. De **maximale hoogte** en **minimale breedte** van de dropdown kunnen respectievelijk worden ingesteld met de <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setOpenHeight(int)'>setOpenHeight()</JavadocLink>- en <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setOpenWidth(int)'>setOpenWidth()</JavadocLink>-methoden.

:::tip
Het doorgeven van een `String`-waarde aan een van deze methoden zal het mogelijk maken om [een geldige CSS-eenheid](https://developer.mozilla.org/en-US/docs/Learn/CSS/Building_blocks/Values_and_units) toe te passen, zoals pixels, viewport-afmetingen of andere geldige regels. Het doorgeven van een `int` zal de waarde die in pixels is doorgegeven instellen.
:::

## Prefix and suffix {#prefix-and-suffix}

Slots bieden flexibele opties om de mogelijkheden van een `ChoiceBox` te verbeteren. U kunt pictogrammen, labels, laadspinners, wissen/resetmogelijkheden, avatar/profielafbeeldingen en andere nuttige componenten binnen een `ChoiceBox` nestelen om de bedoelde betekenis voor gebruikers verder te verduidelijken. De `ChoiceBox` heeft twee slots: de `prefix` en `suffix` slots. Gebruik de `setPrefixComponent()` en `setSuffixComponent()`-methoden om verschillende componenten voor en na de weergegeven optie binnen een `ChoiceBox` in te voegen.

```java
ComboBox choiceBox = new ChoiceBox());
  choiceBox.setPrefixComponent(TablerIcon.create("box"));
  choiceBox.setSuffixComponent(TablerIcon.create("box"));
```

## Styling {#styling}

<TableBuilder name="ChoiceBox" />

## Best practices {#best-practices}

Om een optimale gebruikerservaring te waarborgen bij het gebruik van de `ChoiceBox`-component, overweeg de volgende best practices:

1. **Duidelijke en beperkte opties**: Houd de lijst met keuzes beknopt waar mogelijk en relevant voor de taak van de gebruiker. Een `ChoiceBox` is ideaal voor het presenteren van een duidelijke lijst van opties.

2. **Gebruiksvriendelijke labels**: Zorg ervoor dat de weergegeven labels voor elke optie gebruiksvriendelijk en zelfverklarend zijn. Zorg ervoor dat gebruikers gemakkelijk het doel van elke keuze kunnen begrijpen.

3. **Standaardselectie**: Stel een standaardselectie in wanneer de `ChoiceBox` voor het eerst wordt weergegeven. Dit zorgt voor een vooraf geselecteerde optie, waardoor het aantal interacties dat nodig is om een keuze te maken, wordt verminderd.

4. **ChoiceBox versus andere lijstcomponenten**: Een `ChoiceBox` is de beste keuze als u de gebruikersinvoer wilt beperken tot een enkele keuze uit een lijst van vooraf bepaalde opties. Een andere lijstcomponent kan geschikter zijn als u de volgende gedragingen nodig heeft:
    - Meerdere selecties en toon alle items tegelijk: [`ListBox`](./list-box.md)
    - Sta aangepaste invoer toe: [`ComboBox`](./combo-box.md)
