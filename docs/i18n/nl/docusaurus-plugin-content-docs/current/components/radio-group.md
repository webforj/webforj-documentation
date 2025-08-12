---
title: RadioButtonGroup
slug: radiobuttongroup
sidebar_position: 100
_i18n_hash: 91d753e882e3d6d59deef5044ee7bc4c
---
<DocChip chip='since' label='23.01' />
<JavadocLink type="foundation" location="com/webforj/component/optioninput/RadioButtonGroup" top='true'/>

De `RadioButtonGroup` klasse wordt gebruikt om gerelateerde keuzerondjes samen te groeperen, wat helpt om de wederzijdse exclusiviteit van de opties binnen die groep vast te stellen. Gebruikers kunnen slechts één keuzerondje binnen een bepaalde radio-groep selecteren. Wanneer een gebruiker een keuzerondje binnen een groep selecteert, wordt elk eerder geselecteerd keuzerondje in dezelfde groep automatisch deselecteerd. Dit zorgt ervoor dat er steeds maar één optie tegelijk kan worden gekozen.

:::tip
Een `RadioButton` component slaat de groep op waartoe het behoort, welke toegankelijk is via de `getButtonGroup()` methode.
:::

<ComponentDemo 
path='/webforj/radiobuttongroup?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/radiobutton/RadioButtonGroupView.java'
height="200px"
/>

:::important
De `RadioButtonGroup` component rendert geen HTML-element op de pagina. Het is eerder alleen
logica die ervoor zorgt dat een groep RadioButtons zich als een groep gedraagt in plaats van individueel.
:::

## Usages {#usages}

De `RadioButtonGroup` is het beste te gebruiken in scenario's waarin gebruikers een enkele selectie moeten maken uit een vooraf gedefinieerde reeks opties die worden gepresenteerd als keuzerondjes. Hier zijn enkele voorbeelden van wanneer de `RadioButtonGroup` te gebruiken:

1. **Enquêtes of Vragenlijsten**: `RadioButtonGroup` componenten worden vaak gebruikt in enquêtes of vragenlijsten waarbij gebruikers een enkel antwoord uit een lijst van opties moeten selecteren.

2. **Voorkeurinstellingen**: Applicaties die voorkeur- of instellingspanelen bevatten, gebruiken vaak de RadioButtonGroup component om gebruikers in staat te stellen een enkele optie te kiezen uit een set van wederzijds exclusieve keuzes.

3. **Filteren of Sorteren**: Een `RadioButton` kan worden gebruikt in applicaties die gebruikers vereisen om een enkele filter- of sorteermogelijkheid te selecteren, zoals het sorteren van een lijst met items op verschillende criteria.

<!-- vale off -->
## RadioButtons toevoegen en verwijderen {#adding-and-removing-radiobuttons}
<!-- vale on -->

Het is mogelijk om één of meerdere `RadioButton` objecten aan een groep toe te voegen of te verwijderen, waarbij wordt gegarandeerd dat ze wederzijds exclusieve selecteergedrag vertonen en zijn geassocieerd met een naam die mogelijk bij de groep hoort.

## Naamgeving {#naming}

De naamattribuut in een `RadioButtonGroup` groepeert gerelateerde RadioButtons, zodat gebruikers een enkele keuze kunnen maken uit de aangeboden opties en exclusiviteit onder de RadioButtons afdwingen. De naam van een groep wordt echter niet weergegeven in de DOM, maar is een handige utility voor de Java-ontwikkelaar.

## Best practices {#best-practices}

Om een optimale gebruikerservaring te waarborgen bij het gebruik van de RadioButton component, overweeg de volgende best practices:

1. **Opties Duidelijk Labelen**: Geef duidelijke en beknopte labels voor elke `RadioButton` optie om de keuze nauwkeurig te beschrijven. Labels moeten gemakkelijk te begrijpen zijn en van elkaar te onderscheiden.

2. **Geef een Standaardselectie**: Indien van toepassing, overweeg dan om een standaardselectie voor de keuzerondjes te bieden om gebruikers te begeleiden wanneer ze voor het eerst met de opties in aanraking komen. De standaardselectie moet overeenkomen met de meest voorkomende of gewenste keuze.
