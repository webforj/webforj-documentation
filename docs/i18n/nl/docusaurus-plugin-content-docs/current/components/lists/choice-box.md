---
sidebar_position: 5
title: ChoiceBox
slug: choicebox
_i18n_hash: 6e04ceea1fadc5f159b8d4dd9645e014
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-choicebox" />
<DocChip chip='since' label='23.05' />
<JavadocLink type="foundation" location="com/webforj/component/list/ChoiceBox" top='true'/>

De `ChoiceBox` component presenteert een dropdownlijst waaruit gebruikers een enkele optie kunnen selecteren. Wanneer een selectie is gemaakt, wordt de gekozen waarde weergegeven in de knop. Het is een goede optie wanneer gebruikers moeten kiezen uit een vaste set vooraf gedefinieerde keuzes, waarbij de pijltoetsen kunnen worden gebruikt om door de lijst te navigeren.

<!-- INTRO_END -->

## Usages {#usages}

<ParentLink parent="List" />

`ChoiceBox`-componenten worden voor verschillende doeleinden gebruikt, zoals het selecteren van items uit een menu, kiezen uit een lijst van categorieën of het kiezen van opties uit vooraf gedefinieerde sets. Ze bieden een georganiseerde en visueel prettige manier voor gebruikers om keuzes te maken, vooral wanneer er meerdere opties beschikbaar zijn. Veelvoorkomende toepassingen zijn:

1. **Selectie van gebruikersopties**: Het primaire doel van een `ChoiceBox` is om gebruikers in staat te stellen een enkele optie uit een lijst te selecteren. Dit is waardevol in applicaties die gebruikers om keuzes vragen, zoals:
    - Kiezen uit een lijst van categorieën
    - Opties kiezen uit vooraf gedefinieerde sets

2. **Formulieren**: Bij het ontwerpen van formulieren waarin gebruikers specifieke opties moeten invoeren, vereenvoudigt de `ChoiceBox` het selectieproces. Of het nu gaat om het selecteren van een land, staat of een andere optie uit een vooraf gedefinieerde lijst, de `ChoiceBox` stroomlijnt het invoerproces.

3. **Filteren en Sorteren**: De `ChoiceBox` kan worden gebruikt voor filter- en sorteertaken in applicaties. Gebruikers kunnen filtercriteria of sorteervoorkeuren kiezen uit een lijst, wat de organisatie en navigatie van gegevens vergemakkelijkt.

4. **Configuratie en Instellingen**: Wanneer uw applicatie instellingen of configuratieopties bevat, biedt de `ChoiceBox` een intuïtieve manier voor gebruikers om voorkeuren aan te passen. Gebruikers kunnen instellingen uit een lijst kiezen, waardoor het eenvoudig is om de applicatie aan hun behoeften aan te passen.

:::tip
De `ChoiceBox` is bedoeld voor gebruik wanneer een vastgesteld aantal opties beschikbaar is, en aangepaste opties mogen niet worden toegestaan of opgenomen. Als het de bedoeling is om gebruikers aangepaste waarden te laten invoeren, gebruik dan in plaats daarvan een [`ComboBox`](./combo-box.md).
:::

## Dropdown type {#dropdown-type}

Met de <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setDropdownType(java.lang.String)'>setDropdownType()</JavadocLink>-methode wordt een waarde toegewezen aan de `type`-attribuut van een `ChoiceBox`, en een overeenkomstige waarde voor het `data-dropdown-for`-attribuut in de dropdown van de `ChoiceBox`. Dit is handig voor opmaak, aangezien de dropdown uit zijn huidige positie in de DOM wordt gehaald en aan het einde van de pagina-inhoud wordt geplaatst wanneer deze geopend is.

<!-- ![example type](/img/components/_images/choicebox/type.png)
![example type](/img/components/_images/choicebox/type_zoomed.png) -->

Deze detachering creëert een situatie waarin het moeilijk wordt om de dropdown rechtstreeks aan te spreken met CSS of shadow part-selectors vanuit de bovenliggende component, tenzij je gebruik maakt van het dropdowntype-attribuut.

In de demo hieronder wordt het dropdowntype ingesteld en gebruikt in het CSS-bestand om de dropdown te selecteren en de achtergrondkleur te wijzigen.

<ComponentDemo
path='/webforj/choiceboxdropdowntype'
files={[
  'src/main/java/com/webforj/samples/views/lists/choicebox/ChoiceboxDropdownTypeView.java',
  'src/main/resources/static/css/lists/combobox/comboBoxDropDownType.css',
]}
height='250px'
/>

## Max row count {#max-row-count}

Standaard wordt het aantal rijen dat in de dropdown van een `ChoiceBox` wordt weergegeven vergroot om de inhoud te passen. Het gebruik van de <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setMaxRowCount(int)'>setMaxRowCount()</JavadocLink>-methode maakt het mogelijk om te bepalen hoeveel items er worden weergegeven.

:::tip
Het gebruik van een getal dat kleiner is of gelijk aan 0 zal resulteren in het unsetten van deze eigenschap.
:::

<ComponentDemo
path='/webforj/choiceboxmaxrow'
files={['src/main/java/com/webforj/samples/views/lists/choicebox/ChoiceboxMaxRowView.java']}
height='450px'
/>

## Opening and closing {#opening-and-closing}

De zichtbaarheid van de opties voor een `ChoiceBox` kan programmatisch worden beheerd met de `open()`- en `close()`-methoden. Deze methoden stellen je in staat om de lijst met opties voor selectie weer te geven of deze te verbergen indien nodig, wat meer flexibiliteit biedt in het beheer van het gedrag van een `ChoiceBox`.

Bovendien heeft webforJ event listeners voor wanneer de `ChoiceBox` wordt gesloten en wanneer deze wordt geopend, waardoor je meer controle hebt om specifieke acties te triggeren.

```Java
//Focus of open het volgende component in een formulier
ChoiceBox university = new ChoiceBox("Universiteit");
ChoiceBox major = new ChoiceBox("Hoofdvak");
Button submit = new Button("Verzenden");

//... Voeg lijsten met universiteiten en hoofdvakken toe

university.onClose( e ->{
  major.focus();
});

major.onClose( e ->{
  submit.focus();
});
```

## Opening dimensions {#opening-dimensions}

De `ChoiceBox` component heeft methoden waarmee de afmetingen van de dropdown kunnen worden gemanipuleerd. De **maximale hoogte** en **minimale breedte** van de dropdown kunnen worden ingesteld met behulp van de <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setOpenHeight(int)'>setOpenHeight()</JavadocLink>- en <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setOpenWidth(int)'>setOpenWidth()</JavadocLink>-methoden, respectievelijk.

:::tip
Het doorgeven van een `String`-waarde aan een van deze methoden stelt u in staat om [elke geldige CSS-eenheid](https://developer.mozilla.org/en-US/docs/Learn/CSS/Building_blocks/Values_and_units) toe te passen, zoals pixels, viewport-dimensies of andere geldige regels. Het doorgeven van een `int` zal de waarde instellen die in pixels is doorgegeven.
:::

## Prefix and suffix {#prefix-and-suffix}

Slots bieden flexibele opties om de mogelijkheden van een `ChoiceBox` te verbeteren. U kunt iconen, labels, laadspinners, wis-/resetmogelijkheden, avatar/profielafbeeldingen en andere nuttige componenten binnen een `ChoiceBox` nestelen om de bedoelde betekenis voor gebruikers verder te verduidelijken. De `ChoiceBox` heeft twee slots: de `prefix` en `suffix` slots. Gebruik de `setPrefixComponent()` en `setSuffixComponent()` methoden om verschillende componenten voor en na de weergegeven optie binnen een `ChoiceBox` in te voegen.

```java
ComboBox choiceBox = new ChoiceBox());
  choiceBox.setPrefixComponent(TablerIcon.create("box"));
  choiceBox.setSuffixComponent(TablerIcon.create("box"));
```

## Styling {#styling}

<TableBuilder name="ChoiceBox" />

## Best practices {#best-practices}

Om een optimale gebruikerservaring te garanderen bij het gebruik van de `ChoiceBox` component, overweeg de volgende beste praktijken:

1. **Duidelijke en Beperkte Opties**: Houd de lijst met keuzes beknopt waar mogelijk en relevant voor de taak van de gebruiker. Een `ChoiceBox` is ideaal voor het presenteren van een duidelijke lijst met opties.

2. **Gebruiksvriendelijke Labels**: Zorg ervoor dat de weergegeven labels voor elke optie gebruiksvriendelijk en vanzelfsprekend zijn. Zorg ervoor dat gebruikers de bedoeling van elke keuze eenvoudig kunnen begrijpen.

3. **Standaardselectie**: Stel een standaardselectie in wanneer de `ChoiceBox` aanvankelijk wordt weergegeven. Dit zorgt voor een vooraf geselecteerde optie, waardoor het aantal interacties dat nodig is om een keuze te maken, wordt verminderd.

4. **ChoiceBox vs. Andere Lijstcomponenten**: Een `ChoiceBox` is de beste keuze als je de invoer van de gebruiker wilt beperken tot een enkele keuze uit een lijst van vooraf bepaalde opties. Een andere lijstcomponent kan geschikter zijn als je de volgende gedragingen nodig hebt:
    - Meerdere selecties en alle items tegelijk weergeven: [`ListBox`](./list-box.md)
    - Aangepaste invoer toestaan: [`ComboBox`](./combo-box.md)
