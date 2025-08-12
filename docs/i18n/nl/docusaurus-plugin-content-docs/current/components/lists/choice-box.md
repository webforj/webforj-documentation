---
sidebar_position: 5
title: ChoiceBox
slug: choicebox
_i18n_hash: e90d77e503b1c8f7fc20109633b1e7be
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-choicebox" />
<DocChip chip='since' label='23.05' />
<JavadocLink type="foundation" location="com/webforj/component/list/ChoiceBox" top='true'/>

<ParentLink parent="Lijst" />

De `ChoiceBox` component is een gebruikersinterface-element dat is ontworpen om gebruikers een lijst van opties of keuzes voor te leggen. Gebruikers kunnen een enkele optie uit deze lijst selecteren, doorgaans door op de `ChoiceBox` te klikken, wat de weergave van een dropdown-lijst met beschikbare keuzes activeert. Gebruikers kunnen ook met de pijltoetsen in de `ChoiceBox` interageren. Wanneer een gebruiker een keuze maakt, wordt de gekozen optie weergegeven in de `ChoiceBox`-knop.

## Gebruik {#usages}
`ChoiceBox`-componenten worden voor verschillende doeleinden gebruikt, zoals het selecteren van items uit een menu, kiezen uit een lijst van categorieën of het kiezen van opties uit vooraf gedefinieerde sets. Ze bieden een georganiseerde en visueel aantrekkelijke manier voor gebruikers om selecties te maken, vooral wanneer er meerdere opties beschikbaar zijn. Veelvoorkomende toepassingen zijn onder andere:

1. **Gebruikersselectie van opties**: Het primaire doel van een `ChoiceBox` is gebruikers in staat te stellen een enkele optie uit een lijst te selecteren. Dit is waardevol in toepassingen waarbij gebruikers keuzes moeten maken, zoals:
    - Kiezen uit een lijst van categorieën
    - Opties selecteren uit vooraf gedefinieerde sets

2. **Formuliervelden**: Bij het ontwerpen van formulieren die vereisen dat gebruikers specifieke opties invoeren, vereenvoudigt de `ChoiceBox` het selecteerproces. Of het nu gaat om het selecteren van een land, staat of een andere optie uit een vooraf gedefinieerde lijst, de `ChoiceBox` stroomlijnt het invoerproces.

3. **Filteren en sorteren**: `ChoiceBox` kan worden gebruikt voor filtering en sorteringstaken in toepassingen. Gebruikers kunnen filtercriteria of sorteervoorkeuren uit een lijst kiezen, waardoor de organisatie en navigatie van gegevens wordt vergemakkelijkt.

4. **Configuratie en instellingen**: Wanneer uw toepassing instellingen of configuratie-opties bevat, biedt de `ChoiceBox` een intuïtieve manier voor gebruikers om voorkeuren aan te passen. Gebruikers kunnen instellingen uit een lijst selecteren, waardoor het eenvoudig is om de applicatie aan hun behoeften aan te passen.

:::tip
De `ChoiceBox` is bedoeld voor gebruik wanneer er een vooraf ingesteld aantal opties beschikbaar is en waar geen aangepaste opties mogen worden toegestaan of inbegrepen. Als het de bedoeling is dat gebruikers aangepaste waarden kunnen invoeren, gebruik dan in plaats daarvan een [`ComboBox`](./combo-box.md).
:::

## Dropdown-type {#dropdown-type}

Met de <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setDropdownType(java.lang.String)'>setDropdownType()</JavadocLink> methode kan een waarde aan het `type`-attribuut van een `ChoiceBox` worden toegewezen, en een overeenkomstige waarde voor het `data-dropdown-for` attribuut in de dropdown van de `ChoiceBox`. Dit is nuttig voor styling, aangezien de dropdown uit zijn huidige positie in de DOM wordt gehaald en naar het einde van de paginainhoud wordt verplaatst wanneer deze wordt geopend.

<!-- ![voorbeeld type](/img/components/_images/choicebox/type.png)
![voorbeeld type](/img/components/_images/choicebox/type_zoomed.png) -->

Deze ontkoppeling creëert een situatie waarin het moeilijk wordt om de
dropdown direct aan te spreken met CSS of schaduwdeelselectoren vanuit de oudercomponent, tenzij u gebruik maakt van het dropdown-type attribuut.

In de demo hieronder wordt het Dropdown-type ingesteld en gebruikt in het CSS-bestand om de dropdown te selecteren en de achtergrondkleur te veranderen.

<ComponentDemo 
path='/webforj/choiceboxdropdowntype?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/lists/choicebox/ChoiceboxDropdownTypeView.java'
cssURL='/css/lists/combobox/comboBoxDropDownType.css'
height='250px'
/>

## Maximaal rij-aantal {#max-row-count}

Standaard wordt het aantal rijen dat in de dropdown van een `ChoiceBox` wordt weergegeven, vergroot om de inhoud te passen. Met de <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setMaxRowCount(int)'>setMaxRowCount()</JavadocLink> methode kan echter worden geregeld hoeveel items worden weergegeven.

:::tip
Het gebruik van een nummer dat kleiner dan of gelijk aan 0 is, resulteert in het unsetten van deze eigenschap.
:::

<ComponentDemo 
path='/webforj/choiceboxmaxrow?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/lists/choicebox/ChoiceboxMaxRowView.java'
height='450px'
/>

## Openen en sluiten {#opening-and-closing}

De zichtbaarheid van de opties voor een `ChoiceBox` kan programmatic gewijzigd worden met de `open()` en `close()` methoden. Deze methoden stellen je in staat om de lijst met opties voor selectie weer te geven of deze te verbergen, wat meer flexibiliteit biedt bij het beheren van het gedrag van een `ChoiceBox`.

Bovendien heeft webforJ gebeurtenislijsten wanneer de `ChoiceBox` is gesloten en wanneer deze is geopend, waardoor je meer controle krijgt om specifieke acties te triggeren.

```Java
//Focus of open het volgende component in een formulier
ChoiceBox universiteit = new ChoiceBox("Universiteit");
ChoiceBox major = new ChoiceBox("Major");
Button verzenden = new Button("Verzenden");

//... Lijsten van universiteiten en majors toevoegen

universiteit.onClose( e ->{
  major.focus();
});

major.onClose( e ->{
  verzenden.focus();
});
```

## Openen afmetingen {#opening-dimensions}

De `ChoiceBox` component heeft methoden waarmee de afmetingen van de dropdown kunnen worden gemanipuleerd. De **maximale hoogte** en **minimale breedte** van de dropdown kunnen respectievelijk worden ingesteld met de <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setOpenHeight(int)'>setOpenHeight()</JavadocLink> en <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setOpenWidth(int)'>setOpenWidth()</JavadocLink> methoden.

:::tip
Het doorgeven van een `String`-waarde aan een van deze methoden staat toe dat [elke geldige CSS-eenheid](https://developer.mozilla.org/en-US/docs/Learn/CSS/Building_blocks/Values_and_units) kan worden toegepast, zoals pixels, viewportdimensies of andere geldige regels. Het doorgeven van een `int` stelt de doorgegeven waarde in pixels in.
:::

## Voorvoegsel en achtervoegsel {#prefix-and-suffix}

Slots bieden flexibele opties voor het verbeteren van de functionaliteit van een `ChoiceBox`. Je kunt iconen, labels, laadspinners, de mogelijkheid om te wissen/resetten, avatar/profielafbeeldingen en andere nuttige componenten nestelen binnen een `ChoiceBox` om de bedoelde betekenis voor gebruikers verder te verduidelijken. De `ChoiceBox` heeft twee slots: de `prefix` en `suffix` slots. Gebruik de `setPrefixComponent()` en `setSuffixComponent()` methoden om verschillende componenten vóór en na de weergegeven optie binnen een `ChoiceBox` in te voegen.

```java
ComboBox choiceBox = new ChoiceBox());
  choiceBox.setPrefixComponent(TablerIcon.create("box"));
  choiceBox.setSuffixComponent(TablerIcon.create("box"));
```

## Styling {#styling}

<TableBuilder name="ChoiceBox" />

## Best practices {#best-practices}

Om een optimale gebruikerservaring te waarborgen bij het gebruik van de `ChoiceBox`-component, overweeg de volgende beste praktijken:

1. **Duidelijke en Beperkte Opties**: Houd de lijst met keuzes zo beknopt mogelijk en relevant voor de taak van de gebruiker. Een `ChoiceBox` is ideaal voor het presenteren van een duidelijke lijst met opties.

2. **Gebruiksvriendelijke Labels**: Zorg ervoor dat de weergegeven labels voor elke optie gebruiksvriendelijk en zelfverklarend zijn. Zorg ervoor dat gebruikers de bedoeling van elke keuze gemakkelijk kunnen begrijpen.

3. **Standaardselectie**: Stel een standaardselectie in wanneer de ChoiceBox aanvankelijk wordt weergegeven. Dit zorgt voor een vooraf geselecteerde optie, wat het aantal benodigde interacties om een keuze te maken, vermindert.

4. **ChoiceBox versus andere lijstcomponenten**: Een `ChoiceBox` is de beste keuze als je de gebruikersinvoer wilt beperken tot een enkele keuze uit een lijst van vooraf bepaalde opties. Een andere lijstcomponent kan beter zijn als je de volgende gedrag nodig hebt:
    - Meervoudige selectie en alle items tegelijk weergeven: [`ListBox`](./list-box.md)
    - Aangepaste invoer toestaan: [`ComboBox`](./combo-box.md)
