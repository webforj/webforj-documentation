---
sidebar_position: 5
title: ChoiceBox
slug: choicebox
_i18n_hash: 1da4824585c11423d72c2b75b451a6db
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-choicebox" />
<DocChip chip='since' label='23.05' />
<JavadocLink type="foundation" location="com/webforj/component/list/ChoiceBox" top='true'/>

De `ChoiceBox`-component presenteert een dropdownlijst waaruit gebruikers een enkele optie kunnen selecteren. Wanneer een selectie is gemaakt, wordt de gekozen waarde in de knop weergegeven. Het is een goede optie wanneer gebruikers moeten kiezen uit een vaste set vooraf gedefinieerde keuzes, en de pijltjestoetsen kunnen worden gebruikt om door de lijst te navigeren.

<!-- INTRO_END -->

## Gebruik {#usages}

<ParentLink parent="Lijst" />

`ChoiceBox`-componenten worden voor verschillende doeleinden gebruikt, zoals het selecteren van items uit een menu, het kiezen uit een lijst van categorieën, of het kiezen van opties uit vooraf gedefinieerde sets. Ze bieden een georganiseerde en visueel aantrekkelijke manier voor gebruikers om keuzes te maken, vooral wanneer er meerdere opties beschikbaar zijn. Veelvoorkomende toepassingen zijn:

1. **Selectie van opties door de gebruiker**: Het primaire doel van een `ChoiceBox` is om gebruikers in staat te stellen een enkele optie uit een lijst te selecteren. Dit is waardevol in toepassingen die vereisen dat gebruikers keuzes maken, zoals:
    - Kiezen uit een lijst van categorieën
    - Opties kiezen uit vooraf gedefinieerde sets

2. **Formuliereingangen**: Bij het ontwerpen van formulieren die vereisen dat gebruikers specifieke opties invoeren, vereenvoudigt de `ChoiceBox` het selectieproces. Of het nu gaat om het selecteren van een land, staat of een andere optie uit een vooraf gedefinieerde lijst, de `ChoiceBox` stroomlijnt het invoerproces.

3. **Filteren en sorteren**: `ChoiceBox` kan worden gebruikt voor filter- en sorteer taken in toepassingen. Gebruikers kunnen filtercriteria of sorteervoorkeuren uit een lijst kiezen, waardoor de organisatie en navigatie van gegevens wordt vergemakkelijkt.

4. **Configuratie en instellingen**: Wanneer jouw toepassing instellingen of configuratieopties bevat, biedt de `ChoiceBox` een intuïtieve manier voor gebruikers om voorkeuren aan te passen. Gebruikers kunnen instellingen uit een lijst kiezen, waardoor het eenvoudig wordt om de toepassing aan hun behoeften aan te passen.

:::tip
De `ChoiceBox` is bedoeld voor gebruik wanneer een vooraf ingesteld aantal opties beschikbaar is, en aangepaste opties mogen niet worden toegestaan of opgenomen. Als het de bedoeling is om gebruikers aangepaste waarden te laten invoeren, gebruik dan in plaats daarvan een [`ComboBox`](./combo-box.md).
:::

## Dropdown-type {#dropdown-type}

Met de <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setDropdownType(java.lang.String)'>setDropdownType()</JavadocLink> methode wordt een waarde toegewezen aan de `type` attribuut van een `ChoiceBox`, en een overeenkomstige waarde voor de `data-dropdown-for` attribuut in de dropdown van de `ChoiceBox`. Dit is nuttig voor styling, omdat de dropdown uit zijn huidige positie in de DOM wordt gehaald en naar het einde van de pagina-inhoud wordt verplaatst wanneer deze wordt geopend.

<!-- ![voorbeeld type](/img/components/_images/choicebox/type.png)
![voorbeeld type](/img/components/_images/choicebox/type_zoomed.png) -->

Deze loskoppeling creëert een situatie waarin het moeilijk wordt om de dropdown rechtstreeks te targeten met CSS of schaduwdeelselectors vanuit de bovenliggende component, tenzij je gebruik maakt van het dropdown-type attribuut.

In de onderstaande demo is het Dropdown-type ingesteld en gebruikt in het CSS-bestand om de dropdown te selecteren en de achtergrondkleur te veranderen.

<ComponentDemo 
path='/webforj/choiceboxdropdowntype?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/lists/choicebox/ChoiceboxDropdownTypeView.java'
cssURL='/css/lists/combobox/comboBoxDropDownType.css'
height='250px'
/>

## Maximaal aantal rijen {#max-row-count}

Standaard wordt het aantal rijen dat in de dropdown van een `ChoiceBox` wordt weergegeven, vergroot om de inhoud passend te maken. Met de <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setMaxRowCount(int)'>setMaxRowCount()</JavadocLink> methode kan echter gecontroleerd worden hoeveel items worden weergegeven.

:::tip
Het gebruik van een getal dat kleiner is dan of gelijk aan 0 zal resulteren in het ongedaan maken van deze eigenschap.
:::

<ComponentDemo 
path='/webforj/choiceboxmaxrow?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/lists/choicebox/ChoiceboxMaxRowView.java'
height='450px'
/>

## Openen en sluiten {#opening-and-closing}

De zichtbaarheid van de opties voor een `ChoiceBox` kan programmatisch worden beheerd met de `open()` en `close()` methoden. Deze methoden stellen je in staat om de lijst met opties voor selectie weer te geven of deze indien nodig te verbergen, wat meer flexibiliteit biedt in het beheer van het gedrag van een `ChoiceBox`.

Bovendien heeft webforJ evenementlisteners voor wanneer de `ChoiceBox` wordt gesloten en wanneer deze wordt geopend, waardoor je meer controle hebt om specifieke acties te triggeren.

```Java
//Focus of open het volgende component in een formulier
ChoiceBox universiteit = new ChoiceBox("Universiteit");
ChoiceBox major = new ChoiceBox("Specialisatie");
Button verzenden = new Button("Verzenden");

//... Voeg lijsten van universiteiten en specialisaties toe

universiteit.onClose( e ->{
  major.focus();
});

major.onClose( e ->{
  verzenden.focus();
});
```

## Openingsdimensies {#opening-dimensions}

De `ChoiceBox`-component heeft methoden die manipulatie van de dropdown-dimensies mogelijk maken. De **maximale hoogte** en **minimale breedte** van de dropdown kunnen worden ingesteld met de <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setOpenHeight(int)'>setOpenHeight()</JavadocLink> en <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setOpenWidth(int)'>setOpenWidth()</JavadocLink> methoden, respectievelijk.

:::tip
Het doorgeven van een `String`-waarde aan een van deze methoden maakt het mogelijk om [elk geldig CSS-eenheid](https://developer.mozilla.org/en-US/docs/Learn/CSS/Building_blocks/Values_and_units) toe te passen, zoals pixels, viewportdimensies, of andere geldige regels. Het doorgeven van een `int` zal de waarde instellen in pixels.
:::

## Prefix en suffix {#prefix-and-suffix}

Slots bieden flexibele opties om de functionaliteit van een `ChoiceBox` te verbeteren. Je kunt iconen, labels, laadspinners, wissen/reset mogelijkheden, avatar/profielafbeeldingen en andere nuttige componenten in een `ChoiceBox` nestelen om de bedoelde betekenis aan gebruikers verder te verduidelijken. 
De `ChoiceBox` heeft twee slots: de `prefix` en `suffix` slots. Gebruik de `setPrefixComponent()` en `setSuffixComponent()` methoden om verschillende componenten vóór en na de weergegeven optie binnen een `ChoiceBox` in te voegen.

```java
ComboBox choiceBox = new ChoiceBox());
  choiceBox.setPrefixComponent(TablerIcon.create("box"));
  choiceBox.setSuffixComponent(TablerIcon.create("box"));
```

## Styling {#styling}

<TableBuilder name="ChoiceBox" />

## Best Practices {#best-practices}

Om een optimale gebruikerservaring te waarborgen bij het gebruik van de `ChoiceBox`-component, overweeg de volgende best practices:

1. **Duidelijke en beperkte opties**: Houd de lijst van keuzes beknopt waar mogelijk, en relevant voor de taak van de gebruiker. Een `ChoiceBox` is ideaal voor het presenteren van een duidelijke lijst met opties.

2. **Gebruiksvriendelijke labels**: Zorg ervoor dat de weergegeven labels voor elke optie gebruiksvriendelijk en zelfverklarend zijn. Zorg ervoor dat gebruikers gemakkelijk begrijpen wat het doel van elke keuze is.

3. **Standaardselectie**: Stel een standaardselectie in wanneer de `ChoiceBox` voor het eerst wordt weergegeven. Dit zorgt voor een vooraf geselecteerde optie, waardoor het aantal benodigde interacties om een keuze te maken, wordt verminderd.

4. **ChoiceBox versus andere lijstcomponenten**: Een `ChoiceBox` is de beste keuze als je gebruikersinvoer wilt beperken tot een enkele keuze uit een lijst van vooraf bepaalde opties. Een andere lijstcomponent kan beter zijn als je de volgende gedragingen nodig hebt:
    - Meervoudige selectie en het in één keer weergeven van alle items: [`ListBox`](./list-box.md)
    - Aangepaste invoer toestaan: [`ComboBox`](./combo-box.md)
