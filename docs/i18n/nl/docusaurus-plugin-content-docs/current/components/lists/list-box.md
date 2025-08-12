---
sidebar_position: 15
title: ListBox
slug: listbox
_i18n_hash: 7bd48c55ca5607255c1d6503c500a25d
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-listbox" />
<DocChip chip='since' label='23.05' />
<JavadocLink type="foundation" location="com/webforj/component/list/ListBox" top='true'/>

<ParentLink parent="Lijst" />

De `ListBox` component is een gebruikersinterface-element dat is ontworpen om een scrollbare lijst van objecten weer te geven en gebruikers in staat stelt om enkele of meerdere items uit de lijst te selecteren. Gebruikers kunnen ook interageren met de `ListBox` met de pijltoetsen.

## Usages {#usages}

1. **Toewijzing van Gebruikersrollen**: In applicaties met gebruikers toegangscontrole kunnen beheerders een `ListBox` gebruiken om rollen en machtigingen aan gebruikers toe te wijzen. Gebruikers worden geselecteerd uit een lijst, en de rollen of machtigingen worden toegewezen op basis van hun selectie. Dit zorgt voor nauwkeurige en gecontroleerde toegang tot verschillende functies en gegevens binnen de applicatie.

2. **Toewijzing van Projecttaken**: In projectmanagementsoftware zijn `ListBox` componenten nuttig voor het toewijzen van taken aan teamleden. Gebruikers kunnen taken selecteren uit een lijst en deze toewijzen aan verschillende teamleden. Dit vereenvoudigt de taakdelegatie en zorgt ervoor dat verantwoordelijkheden duidelijk gedefinieerd zijn binnen het team.

3. **Multi-Categorie Filtering**: In een zoekapplicatie moeten gebruikers vaak zoekresultaten filteren op basis van meerdere criteria. Een `ListBox` kan verschillende filteropties weergeven, zoals 
>- Productkenmerken
>- Prijsklassen
>- Merken.

  Gebruikers kunnen items selecteren uit elke filtercategorie, zodat ze de zoekresultaten kunnen verfijnen en precies kunnen vinden wat ze zoeken.

4. **Inhoudscategorisering**: In contentmanagementsystemen helpen `ListBox` componenten bij het categoriseren van artikelen, afbeeldingen of bestanden. Gebruikers kunnen een of meer categorieën selecteren om te associëren met hun inhoud, wat het gemakkelijker maakt om inhoudsitems in het systeem te organiseren en te doorzoeken.

## Selection Options {#selection-options}

Standaard is de lijstbox geconfigureerd om selectie van één item per keer toe te staan. De `ListBox` implementeert echter de <JavadocLink type="foundation" location="com/webforj/component/list/MultipleSelectableList" code='true'>MultipleSelectableList</JavadocLink> interface, die kan worden geconfigureerd met een ingebouwde methode die gebruikers in staat stelt om meerdere items ***met de `Shift` toets*** te selecteren voor aaneengeschakelde invoerselectie en ***`Control` (Windows) of `Command` (Mac) toets*** voor aparte, meervoudige itemselectie.

Gebruik de <JavadocLink type="foundation" location="com/webforj/component/list/ListBox" code='true' suffix='#setSelectionMode(org.dwcj.component.list.MultipleSelectableList.SelectionMode)'>setSelectionMode()</JavadocLink> functie om deze eigenschap te wijzigen. Deze methode accepteert ofwel `SelectionMode.SINGLE` of `SelectionMode.MULTIPLE`.

:::info Gedrag van aanraakapparaten
Op aanraakapparaten, wanneer meervoudige selectie is ingeschakeld, kunnen gebruikers meerdere items selecteren zonder de shift-toets ingedrukt te houden.
:::

Bovendien kunnen de pijltoetsen worden gebruikt om door de `ListBox` te navigeren, en door een lettertoets in te typen terwijl de `ListBox` focus heeft, wordt de optie geselecteerd die begint met die letter, of cyclus door de opties die beginnen met die letter, als er meerdere opties bestaan.

<ComponentDemo 
path='/webforj/listboxmultipleselection?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/lists/listbox/ListboxMultipleSelectionView.java'
height = '250px'
/>

## Styling {#styling}

<TableBuilder name="ListBox" />

## Best Practices {#best-practices}

Om een optimale gebruikerservaring te waarborgen bij het gebruik van de `ChoiceBox` component, overweeg de volgende best practices:

1. **Prioriteer Informatiehiërarchie**: Zorg ervoor dat de items in een `ListBox` georganiseerd zijn in een logische en hiërarchische volgorde. Plaats de belangrijkere en vaak gebruikte opties bovenaan de lijst. Dit maakt het gemakkelijker voor gebruikers om te vinden wat ze nodig hebben zonder overmatig scrollen.

2. **Beperk Lijstlengte**: Vermijd het overweldigend maken van gebruikers met een overmatig lange `ListBox`. Als er een groot aantal items te tonen is, overweeg dan om paginering, zoeken of filteropties te implementeren om gebruikers te helpen items snel te lokaliseren. Alternatief kunt u items in categorieën groeperen om de lijstlengte te verminderen.

3. **Duidelijke en Beschrijvende Labels**: Geef duidelijke en beschrijvende labels voor elk item in de `ListBox`. Gebruikers moeten het doel van elke optie zonder ambiguïteit kunnen begrijpen. Gebruik beknopte en betekenisvolle itemlabels.

4. **Feedback voor Meervoudige Selectie**: Als uw `ListBox` meerdere selecties toestaat, geef visuele of tekstuele feedback die aangeeft dat meerdere items uit de lijst kunnen worden geselecteerd.

5. **Standaardselectie**: Overweeg om een standaardselectie voor de `ListBox` in te stellen, vooral als één optie vaker wordt gebruikt dan andere. Dit kan de gebruikerservaring stroomlijnen door de waarschijnlijk te kiezen optie vooraf te selecteren.
