---
sidebar_position: 15
title: ListBox
slug: listbox
description: >-
  Show a scrollable, always-visible list with the ListBox component, supporting
  single or multiple selection and keyboard navigation.
_i18n_hash: 0a9d307607ef33fdebb557f190abd7cf
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-listbox" />
<DocChip chip='since' label='23.05' />
<JavadocLink type="foundation" location="com/webforj/component/list/ListBox" top='true'/>

De component `ListBox` toont een scrollbare lijst met items die zichtbaar blijft zonder dat er een dropdown hoeft te worden geopend. De component ondersteunt zowel enkelvoudige als meervoudige selectie en is handig wanneer gebruikers alle beschikbare opties tegelijk moeten kunnen zien.

<!-- INTRO_END -->

## Toepassingen {#usages}

<ParentLink parent="List" />

1. **Gebruikersrollen toewijzen**: In applicaties met toegangsbeheer kunnen beheerders een `ListBox` gebruiken om rollen en machtigingen aan gebruikers toe te wijzen. Gebruikers worden geselecteerd uit een lijst en op basis van hun selectie worden rollen of machtigingen toegewezen. Zo wordt nauwkeurige en gecontroleerde toegang tot verschillende functies en gegevens binnen de applicatie gewaarborgd.

2. **Projecttaken toewijzen**: In projectbeheersoftware zijn `ListBox`-componenten handig om taken aan teamleden toe te wijzen. Gebruikers kunnen taken uit een lijst selecteren en aan verschillende teamleden toewijzen. Dit vereenvoudigt de taakverdeling en zorgt ervoor dat verantwoordelijkheden binnen het team duidelijk zijn vastgelegd.

3. **Filteren op meerdere categorieën**: In een zoekapplicatie moeten gebruikers zoekresultaten vaak op basis van meerdere criteria filteren. Een `ListBox` kan verschillende filteropties weergeven, zoals
>- Productkenmerken
>- Prijsklassen
>- Merken.

  Gebruikers kunnen items uit elke filtercategorie selecteren om de zoekresultaten te verfijnen en precies te vinden wat ze zoeken.

4. **Inhoud categoriseren**: In contentmanagementsystemen helpen `ListBox`-componenten bij het categoriseren van artikelen, afbeeldingen of bestanden. Gebruikers kunnen een of meer categorieën selecteren om aan hun inhoud te koppelen. Zo kunnen inhoudsitems in het systeem eenvoudiger worden geordend en doorzocht.

## Selectieopties {#selection-options}

Standaard is de keuzelijst ingesteld op selectie van één item tegelijk. De `ListBox` implementeert echter de interface <JavadocLink type="foundation" location="com/webforj/component/list/MultipleSelectableList" code='true'>MultipleSelectableList</JavadocLink>, die kan worden geconfigureerd met een ingebouwde methode waarmee gebruikers meerdere items kunnen selecteren: ***met de toets <kbd>Shift</kbd>*** voor aaneengesloten selecties en ***met de toets <kbd>Ctrl</kbd> (Windows) of <kbd>Cmd</kbd> (Mac)*** voor afzonderlijke, meervoudige selecties.

Gebruik de functie <JavadocLink type="foundation" location="com/webforj/component/list/ListBox" code='true' suffix='#setSelectionMode(org.dwcj.component.list.MultipleSelectableList.SelectionMode)'>setSelectionMode()</JavadocLink> om deze eigenschap te wijzigen. Deze methode accepteert `SelectionMode.SINGLE` of `SelectionMode.MULTIPLE`.

:::info Gedrag op apparaten met aanraakscherm
Op apparaten met een aanraakscherm kunnen gebruikers, wanneer meervoudige selectie is ingeschakeld, meerdere items selecteren zonder de Shift-toets ingedrukt te houden.
:::

Daarnaast kunnen de pijltoetsen worden gebruikt om door de `ListBox` te navigeren. Als de `ListBox` focus heeft, selecteert het typen van een lettertoets de optie die met die letter begint. Als er meerdere opties met die letter beginnen, kunt u ermee door de opties bladeren.

<ComponentDemo
path='/webforj/listboxmultipleselection'
files={['src/main/java/com/webforj/samples/views/lists/listbox/ListboxMultipleSelectionView.java']}
height='250px'
/>

## Styling {#styling}

<TableBuilder name="ListBox" />

## Aanbevolen werkwijzen {#best-practices}

Houd rekening met de volgende aanbevolen werkwijzen voor een optimale gebruikerservaring met de component `ChoiceBox`:

1. **Geef prioriteit aan de informatiehiërarchie**: Zorg er bij het gebruik van een `ListBox` voor dat de items in een logische, hiërarchische volgorde staan. Plaats de belangrijkste en meest gebruikte opties bovenaan de lijst. Zo kunnen gebruikers gemakkelijker vinden wat ze nodig hebben, zonder overmatig te scrollen.

2. **Beperk de lengte van de lijst**: Voorkom dat gebruikers worden overweldigd door een `ListBox` met te veel items. Als er veel items moeten worden weergegeven, kunt u paginering, zoeken of filteropties implementeren zodat gebruikers items snel kunnen vinden. U kunt items ook groeperen in categorieën om de lijst korter te maken.

3. **Duidelijke en beschrijvende labels**: Geef elk item in de `ListBox` een duidelijk en beschrijvend label. Gebruikers moeten zonder onduidelijkheid begrijpen waarvoor elke optie dient. Gebruik beknopte en betekenisvolle itemlabels.

4. **Feedback bij meervoudige selectie**: Als gebruikers in uw `ListBox` meerdere items kunnen selecteren, geef dan visuele of tekstuele feedback die aangeeft dat meerdere items uit de lijst kunnen worden geselecteerd.

5. **Standaardselectie**: Overweeg een standaardselectie in te stellen voor de `ListBox`, vooral als een optie vaker wordt gebruikt dan andere. Door de meest waarschijnlijke keuze vooraf te selecteren, verloopt de gebruikerservaring soepeler.
