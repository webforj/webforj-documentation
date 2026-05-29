---
sidebar_position: 15
title: ListBox
slug: listbox
_i18n_hash: 48b12429da76fbbe3a7961a0eac4efa9
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-listbox" />
<DocChip chip='since' label='23.05' />
<JavadocLink type="foundation" location="com/webforj/component/list/ListBox" top='true'/>

De `ListBox`-component toont een scrollbare lijst van items die zichtbaar blijft zonder dat er een dropdown geopend hoeft te worden. Het ondersteunt zowel enkele als meerdere selecties en werkt goed wanneer gebruikers alle beschikbare opties in één keer willen zien.

<!-- INTRO_END -->

## Toepassingen {#usages}

<ParentLink parent="List" />

1. **Toewijzing van Gebruikersrollen**: In applicaties met gebruikers toegangscontrole kunnen beheerders een `ListBox` gebruiken om rollen en bevoegdheden aan gebruikers toe te wijzen. Gebruikers worden geselecteerd uit een lijst, en de rollen of bevoegdheden worden toegewezen op basis van hun selectie. Dit zorgt voor precieze en gecontroleerde toegang tot verschillende functies en gegevens binnen de applicatie.

2. **Toewijzing van Projecttaken**: In projectmanagementsoftware zijn `ListBox`-componenten nuttig voor het toewijzen van taken aan teamleden. Gebruikers kunnen taken uit een lijst selecteren en deze toewijzen aan verschillende teamleden. Dit vereenvoudigt de taakdelegatie en zorgt ervoor dat verantwoordelijkheden duidelijk zijn gedefinieerd binnen het team.

3. **Multi-Categorie Filtering**: In een zoekapplicatie moeten gebruikers vaak zoekresultaten filteren op basis van meerdere criteria. Een `ListBox` kan verschillende filteropties tonen, zoals 
>- Productkenmerken
>- Prijscategorieën
>- Merken. 

  Gebruikers kunnen items uit elke filtercategorie selecteren, zodat ze de zoekresultaten kunnen verfijnen en precies kunnen vinden wat ze zoeken.

4. **Inhoudscategorisatie**: In contentmanagementsystemen helpen `ListBox`-componenten bij het categoriseren van artikelen, afbeeldingen of bestanden. Gebruikers kunnen één of meer categorieën selecteren om aan hun inhoud te koppelen, waardoor het gemakkelijker wordt om inhoudselementen in het systeem te organiseren en op te zoeken.

## Selectieopties {#selection-options}

Standaard is de lijstbox geconfigureerd om de selectie van één item per keer toe te staan. De `ListBox` implementeert echter de <JavadocLink type="foundation" location="com/webforj/component/list/MultipleSelectableList" code='true'>MultipleSelectableList</JavadocLink> interface, die kan worden geconfigureerd met een ingebouwde methode waarmee gebruikers meerdere items kunnen selecteren ***met de `Shift`-toets*** voor aaneengeschakelde selectie en ***`Control` (Windows) of `Command` (Mac) toets*** voor afzonderlijke, meervoudige itemselectie. 

Gebruik de <JavadocLink type="foundation" location="com/webforj/component/list/ListBox" code='true' suffix='#setSelectionMode(org.dwcj.component.list.MultipleSelectableList.SelectionMode)'>setSelectionMode()</JavadocLink> functie om deze eigenschap te wijzigen. Deze methode accepteert ofwel `SelectionMode.SINGLE` of `SelectionMode.MULTIPLE`.

:::info Gedrag op aanraakapparaten
Op aanraakapparaten, wanneer meerdere selecties zijn ingeschakeld, kunnen gebruikers meerdere items selecteren zonder de shift-toets ingedrukt te houden.
:::

Daarnaast kunnen de pijltoetsen worden gebruikt om door de `ListBox` te navigeren en het typen van een lettertoets terwijl de `ListBox` focus heeft, selecteert de optie die begint met die letter, of cycled door de opties die beginnen met die letter als er meerdere opties bestaan.

<ComponentDemo
path='/webforj/listboxmultipleselection'
files={['src/main/java/com/webforj/samples/views/lists/listbox/ListboxMultipleSelectionView.java']}
height='250px'
/>

## Stijl {#styling}

<TableBuilder name="ListBox" />

## Best Practices {#best-practices}

Om een optimale gebruikerservaring te waarborgen bij het gebruik van de `ChoiceBox`-component, overweeg dan de volgende best practices:

1. **Prioriteer Informatiehiërarchie**: Bij het gebruik van een `ListBox`, zorg ervoor dat de items in een logische en hiërarchische volgorde zijn georganiseerd. Plaats de belangrijkste en het meest gebruikte opties bovenaan de lijst. Dit maakt het gemakkelijker voor gebruikers om te vinden wat ze nodig hebben zonder te veel te scrollen.

2. **Beperk de Lijstlengte**: Vermijd het overweldigen van gebruikers met een overtollig lange `ListBox`. Als er een groot aantal items moet worden weergegeven, overweeg dan om paginering, zoeken of filteropties te implementeren om gebruikers te helpen items snel te vinden. Alternatief kun je items in categorieën groeperen om de lijstlengte te verminderen.

3. **Duidelijke en Beschrijvende Labels**: Zorg voor duidelijke en beschrijvende labels voor elk item in de `ListBox`. Gebruikers moeten de bedoeling van elke optie kunnen begrijpen zonder ambiguïteit. Gebruik beknopte en zinvolle itemlabels.

4. **Feedback voor Meervoudige Selectie**: Als je `ListBox` meervoudige selecties toestaat, bied dan visuele of tekstuele feedback die aangeeft dat meerdere items uit de lijst kunnen worden geselecteerd.

5. **Standaardselectie**: Overweeg om een standaardselectie voor de `ListBox` in te stellen, vooral als één optie vaker wordt gebruikt dan andere. Dit kan de gebruikerservaring vereenvoudigen door de meest waarschijnlijke keuze vooraf te selecteren.
