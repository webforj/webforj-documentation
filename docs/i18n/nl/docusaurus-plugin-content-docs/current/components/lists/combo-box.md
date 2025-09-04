---
sidebar_position: 10
title: ComboBox
slug: combobox
_i18n_hash: ec3f88523477bf08e92fe9153b014b91
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-combobox" />
<DocChip chip='since' label='23.05' />
<JavadocLink type="foundation" location="com/webforj/component/list/ComboBox" top='true'/>

<ParentLink parent="Lijst" />

De `ComboBox` component is een interface-element dat is ontworpen om gebruikers een lijst met opties of keuzes te presenteren, evenals een veld voor het invoeren van hun eigen aangepaste waarden. Gebruikers kunnen een enkele optie uit deze lijst selecteren, doorgaans door op de `ComboBox` te klikken, wat de weergave van een dropdown-lijst met beschikbare keuzes activeert, of een aangepaste waarde intypen. Gebruikers kunnen ook met de pijltoetsen interactie hebben met de `ComboBox`. Wanneer een gebruiker een keuze maakt, wordt de gekozen optie in de `ComboBox` weergegeven.

## Gebruik {#usages}

De ComboBox-component is een veelzijdig invoerelement dat de functies van zowel een dropdown-lijst als een tekstinvoerveld combineert. Hiermee kunnen gebruikers items selecteren uit een vooraf gedefinieerde lijst of naar behoefte aangepaste waarden invoeren. Deze sectie verkent veelvoorkomende toepassingen van de ComboBox-component in verschillende scenario's:

1. **Productzoekfunctie en invoer**: Gebruik een ComboBox om een productzoekfunctionaliteit en invoerfunctie te implementeren. Gebruikers kunnen ofwel een product selecteren uit een vooraf gedefinieerde lijst of een aangepaste productnaam invoeren. Dit is handig voor toepassingen zoals e-commerce-sites waar producten talrijk en divers zijn.

2. **Tagselectie en invoer**: In toepassingen die inhoudstags vereisen, kan een ComboBox een uitstekende keuze zijn. Gebruikers kunnen uit een lijst van bestaande tags selecteren of aangepaste tags toevoegen door ze te typen. Dit is nuttig voor het organiseren en categoriseren van inhoud. Voorbeelden van dergelijke tags zijn:
    >- Projectlabels: In een projectmanagementtool kunnen gebruikers labels of tags (bijv. "Urgent," "In uitvoering," "Voltooid") selecteren om taken of projecten te categoriseren, en ze kunnen indien nodig aangepaste labels maken.
    >- Receptingrediënten: In een kook- of receptenapp kunnen gebruikers ingrediënten uit een lijst selecteren (bijv. "Tomaten," "Uien," "Kip") of hun eigen ingrediënten toevoegen voor aangepaste recepten.
    >- Locatietags: In een kaart- of geotaggingtoepassing kunnen gebruikers vooraf gedefinieerde locatietags selecteren (bijv. "Strand," "Stad," "Park") of aangepaste tags maken om specifieke plaatsen op een kaart te markeren.

3. **Gegevensinvoer met voorgestelde waarden**: In gegevensinvoervormen kan een ComboBox worden gebruikt om de invoer te versnellen door een lijst met voorgestelde waarden op basis van gebruikersinvoer te bieden. Dit helpt gebruikers om gegevens nauwkeurig en efficiënt in te voeren.

    :::tip
    De `ComboBox` moet worden gebruikt wanneer gebruikers worden toegestaan om aangepaste waarden in te voeren. Als alleen vooraf gedefinieerde waarden gewenst zijn, gebruik dan in plaats daarvan een [`ChoiceBox`](./choice-box.md).
    :::

## Aangepaste waarde {#custom-value}

Het wijzigen van de eigenschap voor aangepaste waarden stelt gebruikers in staat om te bepalen of een gebruiker de waarde in het invoerveld van de `ComboBox` kan wijzigen. Als `true`, wat de standaardwaarde is, kan de gebruiker de waarde wijzigen. Als deze is ingesteld op `false`, kan de gebruiker de waarde niet wijzigen. Dit kan worden ingesteld met behulp van de <JavadocLink type="foundation" location="com/webforj/component/list/ComboBox" code='true' suffix='#setAllowCustomValue(boolean)'>setAllowCustomValue()</JavadocLink> methode.

<ComponentDemo 
path='/webforj/comboboxcustomvalue?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/lists/combobox/ComboBoxCustomValueView.java'
height = '200px'
/>

## Plaatsvervanger {#placeholder}

Er kan een plaatsvervanger voor een `ComboBox` worden ingesteld die in het tekstveld van de component wordt weergegeven wanneer deze leeg is om gebruikers te wijzen op de gewenste invoer in het veld. Dit kan worden gedaan met de <JavadocLink type="foundation" location="com/webforj/component/list/ComboBox" code='true' suffix='#setPlaceholder(java.lang.String)'>setPlaceholder()</JavadocLink> methode.

<ComponentDemo 
path='/webforj/comboboxplaceholder?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/lists/combobox/ComboBoxPlaceholderView.java'
height = '200px'
/>

## Dropdown-type {#dropdown-type}

Het gebruik van de <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setDropdownType(java.lang.String)'>setDropdownType()</JavadocLink> methode zal een waarde toewijzen aan het `type` attribuut van een `ComboBox`, en een overeenkomstige waarde voor het `data-dropdown-for` attribuut in de dropdown van de `ComboBox`. Dit is handig voor styling, aangezien de dropdown uit zijn huidige positie in de DOM wordt verwijderd en naar het einde van de pagina-inhoud wordt verplaatst wanneer deze wordt geopend.

Deze detachering creëert een situatie waarin het uitdagend wordt om de dropdown direct te targeten met CSS of shadow part-selectors vanuit de bovenliggende component, tenzij je gebruikmaakt van het dropdown-type attribuut.

In de demo hieronder is het dropdown-type ingesteld en gebruikt in het CSS-bestand om de dropdown te selecteren en de achtergrondkleur te veranderen.

<ComponentDemo 
path='/webforj/comboboxdropdowntype?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/lists/combobox/ComboBoxDropdownTypeView.java'
cssURL='/css/lists/combobox/comboBoxDropDownType.css'
height='250px'
/>

## Maximaal aantal rijen {#max-row-count}

Standaard zal het aantal rijen dat in de dropdown van een `ComboBox` wordt weergegeven, worden verhoogd om de inhoud te passen. Met de <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setMaxRowCount(int)'>setMaxRowCount()</JavadocLink> methode kan echter worden bepaald hoeveel items er worden weergegeven.

:::caution
Het gebruik van een getal dat kleiner is dan of gelijk is aan 0 zal resulteren in het ongedaan maken van deze eigenschap.
:::

<ComponentDemo 
path='/webforj/comboboxmaxrow?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/lists/combobox/ComboBoxMaxRowView.java'
height='450px'
/>

## Openen en sluiten {#opening-and-closing}

De zichtbaarheid van de opties voor een `ComboBox` kan programmaticaal worden gecontroleerd met de `open()` en `close()` methoden. Deze methoden stellen je in staat om de lijst met opties te tonen voor selectie of deze naar behoefte te verbergen, wat meer flexibiliteit biedt bij het beheren van het gedrag van een `ComboBox`.

Bovendien heeft webforJ gebeurtenislijsten voor wanneer de `ComboBox` is gesloten en wanneer deze geopend is, waardoor je meer controle hebt om specifieke acties te activeren.

```Java
//Focus of open het volgende component in een formulier
ComboBox universiteit = new ComboBox("Universiteit");
ComboBox major = new ComboBox("Major");
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

De `ComboBox` component biedt methoden waarmee de dimensies van de dropdown kunnen worden gemanipuleerd. De **maximale hoogte** en **minimale breedte** van de dropdown kunnen worden ingesteld met de <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setOpenHeight(int)'>setOpenHeight()</JavadocLink> en <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setOpenWidth(int)'>setOpenWidth()</JavadocLink> methoden, respectievelijk.

:::tip
Het doorgeven van een `String` waarde aan een van deze methoden zal ervoor zorgen dat [elke geldige CSS-eenheid](https://developer.mozilla.org/en-US/docs/Learn/CSS/Building_blocks/Values_and_units) kan worden toegepast, zoals pixels, viewport-dimensions of andere geldige regels. Het doorgeven van een `int` zal de waarde in pixels instellen.
:::

## Markeren {#highlighting}

Wanneer je met een `ComboBox` werkt, kun je het markeren van tekst aanpassen op basis van hoe de component de focus verkrijgt. Deze functie kan invoerfouten verminderen wanneer gebruikers formulieren invullen en kan de algehele navigatie-ervaring verbeteren. Wijzig het markeer gedrag met de `setHighlightOnFocus()` methode met een van de ingebouwde `HasHighlightOnFocus.Behavior` enums:

- `ALL`
Inhoud van de component wordt altijd automatisch gemarkeerd wanneer de component focus krijgt.
- `FOCUS`
Inhoud van de component wordt automatisch gemarkeerd wanneer de component focus krijgt onder programmatische controle.
- `FOCUS_OR_KEY`
Inhoud van de component wordt automatisch gemarkeerd wanneer de component focus krijgt onder programmatische controle of door er met de tab-toets in te komen.
- `FOCUS_OR_MOUSE`
Inhoud van de component wordt automatisch gemarkeerd wanneer de component focus krijgt onder programmatische controle of door erop te klikken met de muis.
- `KEY`
Inhoud van de component wordt automatisch gemarkeerd wanneer de component focus krijgt door er met de tab-toets in te komen.
- `KEY_MOUSE`
Inhoud van de component wordt automatisch gemarkeerd wanneer de component focus krijgt door er met de tab-toets in te komen of door erop te klikken met de muis.
- `MOUSE`
Inhoud van de component wordt automatisch gemarkeerd wanneer de component focus krijgt door erop te klikken met de muis.
- `NONE`
Inhoud van de component wordt nooit automatisch gemarkeerd wanneer de component focus krijgt.

:::note
Als de inhoud was gemarkeerd bij het verliezen van focus, wordt deze opnieuw gemarkeerd bij het opnieuw verkrijgen van focus, ongeacht het ingestelde gedrag.
:::

## Voorvoegsel en achtervoegsel {#prefix-and-suffix}

Slots bieden flexibele opties om de functionaliteit van een `ComboBox` te verbeteren. Je kunt pictogrammen, labels, laadsymbolen, wissen/resetten mogelijkheid, avatar/profielafbeeldingen en andere nuttige componenten binnen een `ComboBox` nestelen om betekenis verder te verduidelijken aan gebruikers. De `ComboBox` heeft twee slots: de `prefix` en `suffix` slots. Gebruik de `setPrefixComponent()` en `setSuffixComponent()` methoden om verschillende componenten voor en na de opties binnen een `ComboBox` in te voegen.

```java
ComboBox comboBox = new ComboBox());
  comboBox.setPrefixComponent(TablerIcon.create("box"));
  comboBox.setSuffixComponent(TablerIcon.create("box"));
```

## Stylen {#styling}

<TableBuilder name="ComboBox" />

## Beste praktijken {#best-practices}

Om een optimale gebruikerservaring te waarborgen bij het gebruik van de `ComboBox` component, zijn hier enkele beste praktijken om rekening mee te houden:

1. **Voorgeschakelde gemeenschappelijke waarden**: Als er veelvoorkomende of vaak gebruikte items zijn, laad deze dan vooraf in de lijst van de `ComboBox`. Dit versnelt de selectie voor vaak gekozen items en bevordert consistentie.

2. **Gebruiksvriendelijke labels**: Zorg ervoor dat de weergegeven labels voor elke optie gebruiksvriendelijk en zelfverklarend zijn. Zorg ervoor dat gebruikers gemakkelijk kunnen begrijpen wat het doel van elke keuze is.

3. **Validatie**: Implementeer invoervalidatie om aangepaste invoer te verwerken. Controleer op gegevensnauwkeurigheid en consistentie. Je wilt misschien correcties of bevestigingen voor dubbelzinnige invoer suggereren.

4. **Standaardselectie**: Stel een standaardselectie in, vooral als er een veelvoorkomende of aanbevolen keuze is. Dit verbetert de gebruikerservaring door de noodzaak voor extra klikken te verminderen.

5. **ComboBox versus andere lijstcomponenten**: Een `ComboBox` is de beste keuze als je een enkele invoer van de gebruiker nodig hebt en je hen vooraf bepaalde keuzes wilt bieden en de mogelijkheid om hun invoer aan te passen. Een andere lijstcomponent is wellicht beter als je de volgende gedragingen nodig hebt:
    - Meerdere selectie en alle items in één keer weergeven: [ListBox](./list-box.md)
    - Voorkomen van aangepaste invoer: [ChoiceBox](./choice-box.md)
