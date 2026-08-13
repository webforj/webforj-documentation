---
sidebar_position: 15
title: ListBox
slug: listbox
description: >-
  Show a scrollable, always-visible list with the ListBox component, supporting
  single or multiple selection and keyboard navigation.
_i18n_hash: ea83ed0b82b2f6f91d7fbe9dedebeef2
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-listbox" />
<DocChip chip='since' label='23.05' />
<JavadocLink type="foundation" location="com/webforj/component/list/ListBox" top='true'/>

De `ListBox` component toont een scrollbare lijst van items die zichtbaar blijft zonder dat een dropdown geopend hoeft te worden. Het ondersteunt zowel enkele als meerdere selecties en is ideaal wanneer gebruikers alle beschikbare opties tegelijk moeten zien.

<!-- INTRO_END -->

## Usages {#usages}

<ParentLink parent="List" />

1. **Toewijzing van Gebruikersrollen**: In applicaties met gebruikers toegangscontrole kunnen beheerders een `ListBox` gebruiken om rollen en machtigingen aan gebruikers toe te wijzen. Gebruikers worden geselecteerd uit een lijst en de rollen of machtigingen worden toegewezen op basis van hun selectie. Dit zorgt voor een nauwkeurige en gecontroleerde toegang tot verschillende functies en gegevens binnen de applicatie.

2. **Toewijzing van Projecttaken**: In projectmanagementsoftware zijn `ListBox`-componenten nuttig voor het toewijzen van taken aan teamleden. Gebruikers kunnen taken uit een lijst selecteren en deze aan verschillende teamleden toewijzen. Dit vereenvoudigt de taakdelegatie en zorgt ervoor dat verantwoordelijkheden klarend zijn binnen het team.

3. **Multi-Categorie Filtering**: In een zoekapplicatie moeten gebruikers vaak zoekresultaten filteren op basis van meerdere criteria. Een `ListBox` kan verschillende filteropties tonen, zoals
>- Productkenmerken
>- Prijsklassen
>- Merken.

  Gebruikers kunnen items uit elke filtercategorie selecteren, waardoor ze de zoekresultaten kunnen verfijnen en precies kunnen vinden wat ze zoeken.

4. **Inhoudscategorisatie**: In contentmanagementsystemen helpen `ListBox`-componenten bij het categoriseren van artikelen, afbeeldingen of bestanden. Gebruikers kunnen een of meer categorieën selecteren om aan hun inhoud te koppelen, waardoor het gemakkelijker wordt om inhoudsitems in het systeem te organiseren en te doorzoeken.

## Selection Options {#selection-options}

Standaard is de lijstbox ingesteld om de selectie van één item tegelijk toe te staan. De `ListBox` implementeert echter de <JavadocLink type="foundation" location="com/webforj/component/list/MultipleSelectableList" code='true'>MultipleSelectableList</JavadocLink> interface, die kan worden geconfigureerd met een ingebouwde methode waarmee gebruikers meerdere items kunnen selecteren ***met de `Shift` toets*** voor aaneengeschakelde selectie en ***de `Control` (Windows) of `Command` (Mac) toets*** voor afzonderlijke, meerdere itemselectie.

Gebruik de <JavadocLink type="foundation" location="com/webforj/component/list/ListBox" code='true' suffix='#setSelectionMode(org.dwcj.component.list.MultipleSelectableList.SelectionMode)'>setSelectionMode()</JavadocLink> functie om deze eigenschap te wijzigen. Deze methode accepteert ofwel `SelectionMode.SINGLE` of `SelectionMode.MULTIPLE`.

:::info Gedrag op aanraakapparaten
Bij aanraakapparaten kunnen gebruikers, wanneer meerdere selecties zijn ingeschakeld, meerdere items selecteren zonder de shift-toets ingedrukt te houden.
:::

Daarnaast kunnen de pijltoetsen worden gebruikt om door de `ListBox` te navigeren, en het typen van een lettertoets terwijl de `ListBox` focus heeft, zal de optie selecteren die met die letter begint, of door de opties cyclusen die met die letter beginnen als er meerdere opties beschikbaar zijn.

<ComponentDemo
path='/webforj/listboxmultipleselection'
files={['src/main/java/com/webforj/samples/views/lists/listbox/ListboxMultipleSelectionView.java']}
height='250px'
/>

## Styling {#styling}

<TableBuilder name="ListBox" />

## Best Practices {#best-practices}

Om een optimale gebruikerservaring te waarborgen bij het gebruik van de `ChoiceBox` component, overweeg de volgende best practices:

1. **Prioriteer Informatiehiërarchie**: Zorg ervoor dat de items in een `ListBox` georganiseerd zijn in een logische en hiërarchische volgorde. Plaats de belangrijkste en het meest gebruikte opties bovenaan de lijst. Dit maakt het gemakkelijker voor gebruikers om te vinden wat ze nodig hebben zonder overmatig te scrollen.

2. **Beperk Lijstlengte**: Vermijd het overweldigen van gebruikers met een onterecht lange `ListBox`. Als er veel items te tonen zijn, overweeg dan paginering, zoek- of filteropties te implementeren om gebruikers te helpen items snel te vinden. Als alternatief kunt u items in categorieën groeperen om de lijstlengte te verminderen.

3. **Duidelijke en Beschrijvende Labels**: Geef duidelijke en beschrijvende labels voor elk item in de `ListBox`. Gebruikers moeten in staat zijn om het doel van elke optie zonder ambiguïteit te begrijpen. Gebruik beknopte en betekenisvolle itemlabels.

4. **Multi-Selectie Feedback**: Als uw `ListBox` meerdere selecties toelaat, geef dan visuele of tekstuele feedback die aangeeft dat meerdere items uit de lijst kunnen worden geselecteerd.

5. **Standaardselectie**: Overweeg een standaardselectie voor de `ListBox` in te stellen, vooral als één optie gebruikelijker is dan andere. Dit kan de gebruikerservaring stroomlijnen door de meest waarschijnlijke keuze vooraf te selecteren.
