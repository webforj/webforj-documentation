---
sidebar_position: 15
title: ListBox
slug: listbox
_i18n_hash: 33476ec9bd7a601aaec3f1e37e7c099f
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-listbox" />
<DocChip chip='since' label='23.05' />
<JavadocLink type="foundation" location="com/webforj/component/list/ListBox" top='true'/>

<ParentLink parent="Lijst" />

De `ListBox`-component is een interface-element dat is ontworpen om een scrollbar lijst van objecten weer te geven en stelt gebruikers in staat om een enkele of meerdere items uit de lijst te selecteren. Gebruikers kunnen ook met de `ListBox` interageren met de pijltjestoetsen. 

## Usages {#usages}

1. **Toewijzing van Gebruikersrollen**: In toepassingen met gebruikers toegangscontrole kunnen beheerders een `ListBox` gebruiken om rollen en permissies aan gebruikers toe te wijzen. Gebruikers worden geselecteerd uit een lijst, en de rollen of permissies worden toegewezen op basis van hun selectie. Dit zorgt voor nauwkeurige en gecontroleerde toegang tot verschillende functies en gegevens binnen de applicatie.

2. **Toewijzing van Projecttaken**: In project managementsoftware zijn `ListBox`-componenten nuttig voor het toewijzen van taken aan teamleden. Gebruikers kunnen taken uit een lijst selecteren en deze aan verschillende teamleden toewijzen. Dit vereenvoudigt de taakdelegatie en zorgt ervoor dat verantwoordelijkheden duidelijk zijn gedefinieerd binnen het team.

3. **Filtering op Meerdere Categorieën**: In een zoekapplicatie moeten gebruikers vaak zoekresultaten filteren op basis van meerdere criteria. Een `ListBox` kan verschillende filteropties weergeven, zoals 
>- Productkenmerken
>- Prijsklassen
>- Merken.

  Gebruikers kunnen items uit elke filtercategorie selecteren, zodat ze hun zoekresultaten kunnen verfijnen en precies kunnen vinden wat ze zoeken.

4. **Inhoudscategorisering**: In contentmanagementsystemen helpen `ListBox`-componenten bij het categoriseren van artikelen, afbeeldingen of bestanden. Gebruikers kunnen een of meerdere categorieën selecteren om te associëren met hun inhoud, waardoor het gemakkelijker wordt om inhoudsitems in het systeem te organiseren en te doorzoeken.

## Selection Options {#selection-options}

Standaard is de lijstbox zo geconfigureerd dat slechts één item tegelijk kan worden geselecteerd. De `ListBox` implementeert echter de <JavadocLink type="foundation" location="com/webforj/component/list/MultipleSelectableList" code='true'>MultipleSelectableList</JavadocLink>-interface, die kan worden geconfigureerd met een ingebouwde methode waarmee gebruikers meerdere items kunnen selecteren ***door de `Shift`-toets*** in te drukken voor aaneengeschakelde selectie en ***de `Control` (Windows) of `Command` (Mac) toets*** voor afzonderlijke, meerdere itemselectie. 

Gebruik de <JavadocLink type="foundation" location="com/webforj/component/list/ListBox" code='true' suffix='#setSelectionMode(org.dwcj.component.list.MultipleSelectableList.SelectionMode)'>setSelectionMode()</JavadocLink>-functie om deze eigenschap te wijzigen. Deze methode accepteert ofwel `SelectionMode.SINGLE` of `SelectionMode.MULTIPLE`.

:::info Gedrag op aanraakapparaten
Op aanraakapparaten, wanneer meerdere selectie is ingeschakeld, kunnen gebruikers meerdere items selecteren zonder de shift-toets ingedrukt te houden.
:::

Daarnaast kunnen de pijltjestoetsen worden gebruikt om door de `ListBox` te navigeren, en het typen van een lettertoets terwijl de `ListBox` focus heeft, selecteert de optie die met die letter begint, of doorloopt de opties die met die letter beginnen als er meerdere opties bestaan.

<ComponentDemo 
path='/webforj/listboxmultipleselection?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/lists/listbox/ListboxMultipleSelectionView.java'
height = '250px'
/>

## Styling {#styling}

<TableBuilder name="ListBox" />

## Best Practices {#best-practices}

Om een optimale gebruikerservaring te waarborgen bij het gebruik van de `ChoiceBox`-component, overweeg dan de volgende best practices:

1. **Prioriteer Informatiehiërarchie**: Zorg ervoor dat de items in een `ListBox` logisch en hiërarchisch zijn georganiseerd. Plaats de belangrijkste en het vaakst gebruikte opties bovenaan de lijst. Dit maakt het gemakkelijker voor gebruikers om te vinden wat ze nodig hebben zonder overmatig scrollen.

2. **Beperk Lijstlengte**: Voorkom dat gebruikers overweldigd worden door een te lange `ListBox`. Als er een groot aantal items is om weer te geven, overweeg dan om paginering, zoeken of filteropties te implementeren om gebruikers te helpen items snel te lokaliseren. Alternatief kunt u items in categorieën groeperen om de lijstlengte te verminderen.

3. **Duidelijke en Beschrijvende Labels**: Zorg voor duidelijke en beschrijvende labels voor elk item in de `ListBox`. Gebruikers moeten de bedoeling van elke optie kunnen begrijpen zonder ambiguïteit. Gebruik beknopte en betekenisvolle itemlabels.

4. **Feedback voor Meerdere Selecties**: Als uw `ListBox` meerdere selecties toestaat, zorg dan voor visuele of tekstuele feedback die aangeeft dat meerdere items uit de lijst kunnen worden geselecteerd.

5. **Standaardselectie**: Overweeg om een standaardselectie in te stellen voor de `ListBox`, vooral als één optie vaker wordt gebruikt dan andere. Dit kan de gebruikerservaring stroomlijnen door de meest waarschijnlijke keuze vooraf te selecteren.
