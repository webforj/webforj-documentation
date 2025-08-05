---
sidebar_position: 5
title: ChoiceBox
slug: choicebox
_i18n_hash: d2e1c4ceeb6346a98d03075f19f5ee1c
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-choicebox" />
<DocChip chip='since' label='23.05' />
<JavadocLink type="foundation" location="com/webforj/component/list/ChoiceBox" top='true'/>

<ParentLink parent="Lijst" />

De `ChoiceBox` component is een gebruikersinterface-element dat is ontworpen om gebruikers een lijst met opties of keuzes voor te leggen. Gebruikers kunnen een enkele optie uit deze lijst selecteren, typisch door op de `ChoiceBox` te klikken, wat de weergave van een dropdownlijst met beschikbare keuzes activeert. Gebruikers kunnen ook de `ChoiceBox` gebruiken met de pijltoetsen. Wanneer een gebruiker een selectie maakt, wordt de gekozen optie weergegeven in de `ChoiceBox`-knop.

## Toepassingen {#usages}
`ChoiceBox`-componenten worden gebruikt voor verschillende doeleinden, zoals het selecteren van items uit een menu, kiezen uit een lijst van categorieën of het picking van opties uit vooraf gedefinieerde sets. Ze bieden een georganiseerde en visueel aantrekkelijke manier voor gebruikers om selecties te maken, vooral wanneer er meerdere opties beschikbaar zijn. Veelvoorkomende toepassingen omvatten:

1. **Selectie van opties door de gebruiker**: Het primaire doel van een `ChoiceBox` is om gebruikers in staat te stellen een enkele optie uit een lijst te selecteren. Dit is waardevol in applicaties die van gebruikers vereisen dat ze keuzes maken, zoals:
    - Kiezen uit een lijst van categorieën
    - Opties kiezen uit vooraf gedefinieerde sets

2. **Formuliereingangen**: Bij het ontwerpen van formulieren die vereisen dat gebruikers specifieke opties invoeren, vereenvoudigt de `ChoiceBox` het selecteren van opties. Of het nu gaat om het selecteren van een land, staat of andere opties uit een vooraf gedefinieerde lijst, de `ChoiceBox` stroomlijnt het invoerproces.

3. **Filteren en sorteren**: `ChoiceBox` kan worden gebruikt voor filter- en sorteertaken in applicaties. Gebruikers kunnen filtercriteria of sorteervoorkeuren uit een lijst kiezen, wat de organisatie en navigatie van gegevens vergemakkelijkt.

4. **Configuratie en instellingen**: Wanneer uw applicatie instellingen of configuratie-opties bevat, biedt de `ChoiceBox` een intuïtieve manier voor gebruikers om voorkeuren aan te passen. Gebruikers kunnen instellingen uit een lijst kiezen, waardoor het makkelijk wordt om de applicatie aan hun wensen aan te passen.

:::tip
De `ChoiceBox` is bedoeld voor gebruik wanneer een vooraf bepaald aantal opties beschikbaar is, en aangepaste opties mogen niet worden toegestaan of inbegrepen. Als het de bedoeling is dat gebruikers aangepaste waarden kunnen invoeren, gebruik dan in plaats daarvan een [`ComboBox`](./combo-box.md).
:::

## Dropdown-type {#dropdown-type}

Het gebruik van de <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setDropdownType(java.lang.String)'>setDropdownType()</JavadocLink> methode zal een waarde toewijzen aan de `type` attribuut van een `ChoiceBox`, en een overeenkomstige waarde voor de `data-dropdown-for` attribuut in de dropdown van de `ChoiceBox`. Dit is nuttig voor styling, aangezien de dropdown uit zijn huidige positie in de DOM wordt gehaald en aan het einde van de pagina-inhoud wordt verplaatst wanneer deze wordt geopend.

<!-- ![voorbeeld type](/img/components/_images/choicebox/type.png)
![voorbeeld type](/img/components/_images/choicebox/type_zoomed.png) -->

Deze loskoppeling creëert een situatie waarbij het direct richten op de dropdown met CSS of schaduwdeelselectoren vanuit de bovenliggende component uitdagend wordt, tenzij u gebruik maakt van het dropdown-type-attribuut.

In de demo hieronder wordt het Dropdown-type ingesteld en gebruikt in het CSS-bestand om de dropdown te selecteren en de achtergrondkleur te veranderen.

<ComponentDemo 
path='/webforj/choiceboxdropdowntype?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/lists/choicebox/ChoiceboxDropdownTypeView.java'
cssURL='/css/lists/combobox/comboBoxDropDownType.css'
height='250px'
/>

## Maximaal aantal rijen {#max-row-count}

Standaard zal het aantal weergegeven rijen in de dropdown van een `ChoiceBox` worden verhoogd om de inhoud te passen. Echter, met de <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setMaxRowCount(int)'>setMaxRowCount()</JavadocLink> methode kan de controle over hoeveel items worden weergegeven, worden uitgevoerd.

:::tip
Het gebruik van een getal dat kleiner is dan of gelijk aan 0 zal resulteren in het resetten van deze eigenschap.
:::

<ComponentDemo 
path='/webforj/choiceboxmaxrow?' 
javaE='https://raw.githubusercontent.com/webforj/webforj/documentation/refs/heads/main/src/main/java/com/webforj/samples/views/lists/choicebox/ChoiceboxMaxRowView.java'
height='450px'
/>

## Openen en sluiten {#opening-and-closing}

De zichtbaarheid van de opties voor een `ChoiceBox` kan programmatisch worden beheerd met de `open()` en `close()` methoden. Deze methoden stellen u in staat om de lijst van opties voor selectie weer te geven of deze indien nodig te verbergen, wat meer flexibiliteit biedt bij het beheren van het gedrag van een `ChoiceBox`.

Bovendien heeft webforJ evenementenluisteraars voor wanneer de `ChoiceBox` wordt gesloten en wanneer deze wordt geopend, waardoor u meer controle heeft om specifieke acties te triggeren.

```Java
//Focus of open het volgende component in een formulier
ChoiceBox universiteit = new ChoiceBox("Universiteit");
ChoiceBox major = new ChoiceBox("Hoofdvak");
Button indienen = new Button("Indienen");

//... Voeg lijsten van universiteiten en majors toe

universiteit.onClose( e ->{
  major.focus();
});

major.onClose( e ->{
  indienen.focus();
});
```

## Openingsdimensies {#opening-dimensions}

De `ChoiceBox` component heeft methoden die manipulatie van de dropdown-dimensies mogelijk maken. De **maximale hoogte** en **minimale breedte** van de dropdown kunnen worden ingesteld met de <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setOpenHeight(int)'>setOpenHeight()</JavadocLink> en <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setOpenWidth(int)'>setOpenWidth()</JavadocLink> methoden, respectievelijk.

:::tip
Het doorgeven van een `String` waarde aan een van deze methoden zal het mogelijk maken om [iedere geldige CSS-eenheid](https://developer.mozilla.org/en-US/docs/Learn/CSS/Building_blocks/Values_and_units) toe te passen, zoals pixels, viewport-dimensies of andere geldige regels. Het doorgeven van een `int` zal de waarde in pixels instellen.
:::

## Voorafgaand en achteraf {#prefix-and-suffix}

Slots bieden flexibele opties om de mogelijkheden van een `ChoiceBox` te verbeteren. U kunt pictogrammen, labels, laadspanningen, wissen/reset mogelijkheden, avatar/profielafbeeldingen en andere nuttige componenten genest in een `ChoiceBox` hebben om de bedoelde betekenis voor gebruikers verder te verduidelijken. 
De `ChoiceBox` heeft twee slots: de `prefix` en `suffix` slots. Gebruik de `setPrefixComponent()` en `setSuffixComponent()` methoden om verschillende componenten vóór en na de weergegeven optie binnen een `ChoiceBox` in te voegen.

```java
ComboBox choiceBox = new ChoiceBox());
  choiceBox.setPrefixComponent(TablerIcon.create("box"));
  choiceBox.setSuffixComponent(TablerIcon.create("box"));
```

## Styling {#styling}

<TableBuilder name="ChoiceBox" />

## Beste praktijken {#best-practices}

Om een optimale gebruikerservaring te waarborgen bij het gebruik van de `ChoiceBox` component, overweeg de volgende beste praktijken:

1. **Duidelijke en Beperkte Opties**: Houd de lijst met keuzes beknopt waar mogelijk, en relevant voor de taak van de gebruiker. Een `ChoiceBox` is ideaal voor het presenteren van een duidelijke lijst met opties.

2. **Gebruiksvriendelijke Labels**: Zorg ervoor dat de weergegeven labels voor elke optie gebruiksvriendelijk en zelfverklarend zijn. Zorg ervoor dat gebruikers gemakkelijk de bedoeling van elke keuze kunnen begrijpen.

3. **Standaardselectie**: Stel een standaardselectie in wanneer de `ChoiceBox` aanvankelijk wordt weergegeven. Dit zorgt voor een vooraf geselecteerde optie, waardoor het aantal benodigde interacties om een keuze te maken, vermindert.

4. **ChoiceBox vs. Andere Lijstcomponenten**: Een `ChoiceBox` is de beste keuze als u de invoer van de gebruiker wilt beperken tot een enkele keuze uit een lijst van vooraf bepaalde opties. Een andere lijstcomponent kan beter zijn als u de volgende gedragingen nodig heeft:
    - Meerdere Selectie en toon alle items tegelijk: [`ListBox`](./list-box.md)
    - Sta aangepaste invoer toe: [`ComboBox`](./combo-box.md)
