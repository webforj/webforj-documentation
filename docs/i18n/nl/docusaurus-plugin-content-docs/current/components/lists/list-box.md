---
sidebar_position: 15
title: ListBox
slug: listbox
_i18n_hash: 9bf0e23b101252295342c62ce6a0dee9
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-listbox" />
<DocChip chip='since' label='23.05' />
<JavadocLink type="foundation" location="com/webforj/component/list/ListBox" top='true'/>

De `ListBox` component toont een scrollbare lijst van items die zichtbaar blijft zonder dat een dropdown geopend hoeft te worden. Het ondersteunt zowel enkele als meerdere selecties en werkt goed wanneer gebruikers alle beschikbare opties tegelijk moeten zien.

<!-- INTRO_END -->

## Gebruik {#usages}

<ParentLink parent="Lijst" />

1. **Toewijzing van Gebruikersrollen**: In applicaties met gebruikers-toegangscontrole kunnen beheerders een `ListBox` gebruiken om rollen en machtigingen aan gebruikers toe te wijzen. Gebruikers worden geselecteerd uit een lijst, en de rollen of machtigingen worden toegewezen op basis van hun selectie. Dit zorgt voor nauwkeurige en gecontroleerde toegang tot verschillende functies en gegevens binnen de applicatie.

2. **Toewijzing van Projecttaken**: In projectmanagementsoftware zijn `ListBox`-componenten nuttig voor het toewijzen van taken aan teamleden. Gebruikers kunnen taken uit een lijst selecteren en deze aan verschillende teamleden toewijzen. Dit vereenvoudigt de taakdelegatie en zorgt ervoor dat verantwoordelijkheden duidelijk gedefinieerd zijn binnen het team.

3. **Filtering op Meerdere Categorieën**: In een zoekapplicatie moeten gebruikers vaak zoekresultaten filteren op basis van meerdere criteria. Een `ListBox` kan verschillende filteropties tonen, zoals 
>- Productkenmerken
>- Prijsklassen
>- Merken. 

  Gebruikers kunnen items uit elke filtercategorie selecteren, zodat ze zoekresultaten kunnen verfijnen en precies kunnen vinden wat ze zoeken.

4. **Inhoudscategorisering**: In contentmanagementsystemen helpen `ListBox`-componenten bij het categoriseren van artikelen, afbeeldingen of bestanden. Gebruikers kunnen een of meer categorieën selecteren om aan hun inhoud te koppelen, waardoor het eenvoudiger wordt om inhoudselementen in het systeem te organiseren en op te zoeken.

## Selectieopties {#selection-options}

Standaard is de lijstbox geconfigureerd om de selectie van één item tegelijk toe te staan. De `ListBox` implementeert echter de <JavadocLink type="foundation" location="com/webforj/component/list/MultipleSelectableList" code='true'>MultipleSelectableList</JavadocLink> interface, die kan worden geconfigureerd met een ingebouwde methode waarmee gebruikers meerdere items kunnen selecteren ***met de `Shift` toets*** voor aaneengeschakelde selectie en ***de `Control` (Windows) of `Command` (Mac) toets*** voor afzonderlijke, meerdere itemselecties. 

Gebruik de <JavadocLink type="foundation" location="com/webforj/component/list/ListBox" code='true' suffix='#setSelectionMode(org.dwcj.component.list.MultipleSelectableList.SelectionMode)'>setSelectionMode()</JavadocLink> functie om deze eigenschap te wijzigen. Deze methode accepteert ofwel `SelectionMode.SINGLE` of `SelectionMode.MULTIPLE`.

:::info Gedrag op aanraakapparaten
Op aanraakapparaten, wanneer meerdere selecties zijn ingeschakeld, kunnen gebruikers meerdere items selecteren zonder de shift-toets ingedrukt te houden.
:::

Bovendien kunnen de pijltoetsen worden gebruikt om door de `ListBox` te navigeren, en het typen van een lettertoets terwijl de `ListBox` focus heeft, selecteert de optie die met die letter begint, of cyclus door de opties die met die letter beginnen als er meerdere opties bestaan.

<ComponentDemo 
path='/webforj/listboxmultipleselection?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/lists/listbox/ListboxMultipleSelectionView.java'
height = '250px'
/>

## Stijling {#styling}

<TableBuilder name="ListBox" />

## Beste praktijken {#best-practices}

Om een optimale gebruikerservaring te garanderen bij het gebruik van de `ChoiceBox` component, overweeg de volgende beste praktijken:

1. **Prioriteer Informatiehiërarchie**: Zorg ervoor dat de items in een `ListBox` zijn georganiseerd in een logische en hiërarchische volgorde. Plaats de belangrijkste en het meest gebruikte opties bovenaan de lijst. Dit maakt het gemakkelijker voor gebruikers om te vinden wat ze nodig hebben zonder overmatig te scrollen.

2. **Beperk Lijstlengte**: Vermijd het overweldigen van gebruikers met een overdreven lange `ListBox`. Als er een groot aantal items te tonen is, overweeg dan het implementeren van paginering, zoeken, of filtering opties om gebruikers te helpen items snel te vinden. Alternatief kun je items in categorieën groeperen om de lijstlengte te verkorten.

3. **Duidelijke en Beschrijvende Labels**: Geef duidelijke en beschrijvende labels voor elk item in de `ListBox`. Gebruikers moeten het doel van elke optie zonder ambiguïteit kunnen begrijpen. Gebruik beknopte en betekenisvolle itemlabels.

4. **Feedback voor Meervoudige Selectie**: Als je `ListBox` meerdere selecties toestaat, biedt dan visuele of tekstuele feedback die aangeeft dat meerdere items uit de lijst kunnen worden geselecteerd.

5. **Standaardselectie**: Overweeg om een standaardselectie voor de `ListBox` in te stellen, vooral als één optie vaker gebruikt wordt dan andere. Dit kan de gebruikerservaring stroomlijnen door de meest waarschijnlijke keuze vooraf te selecteren.
