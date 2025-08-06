---
title: RadioButtonGroup
slug: radiobuttongroup
sidebar_position: 100
_i18n_hash: 8e58efd7b052a00eaf8cfce276cda92e
---
<DocChip chip='since' label='23.01' />
<JavadocLink type="foundation" location="com/webforj/component/optioninput/RadioButtonGroup" top='true'/>

De `RadioButtonGroup` klasse wordt gebruikt om verwante radio-knoppen samen te groeperen, wat helpt om de wederzijdse exclusiviteit tussen de opties binnen die groep vast te stellen. Gebruikers kunnen slechts één radio-knop selecteren binnen een gegeven radiogroep. Wanneer een gebruiker een radio-knop binnen een groep selecteert, wordt een eerder geselecteerde radio-knop in dezelfde groep automatisch deselecteerd. Dit zorgt ervoor dat er slechts één optie tegelijk kan worden gekozen.

:::tip
Een `RadioButton` component slaat de groep op waartoe het behoort, die kan worden benaderd via de `getButtonGroup()` methode.
:::

<ComponentDemo 
path='/webforj/radiobuttongroup?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/radiobutton/RadioButtonGroupView.java'
height="200px"
/>

:::important
De `RadioButtonGroup` component rendert geen HTML-element op de pagina. Het is eerder enkel
logica die ervoor zorgt dat een groep van RadioButtons zich als een groep gedraagt in plaats van individueel.
:::

## Usages {#usages}

De `RadioButtonGroup` is het beste te gebruiken in scenario's waar gebruikers een enkele selectie moeten maken uit een vooraf gedefinieerde set opties die worden gepresenteerd als radio-knoppen. Hier zijn enkele voorbeelden van wanneer de `RadioButtonGroup` te gebruiken:

1. **Enquêtes of Vragenlijsten**: `RadioButtonGroup` componenten worden vaak gebruikt in enquêtes of vragenlijsten waar gebruikers een enkele reactie uit een lijst met opties moeten selecteren.

2. **Voorkeurinstellingen**: Applicaties die voorkeur- of instellingenpanelen bevatten, gebruiken vaak de RadioButtonGroup component om gebruikers in staat te stellen een enkele optie te kiezen uit een set van wederzijds exclusieve keuzes.

3. **Filteren of Sorteren**: Een `RadioButton` kan worden gebruikt in applicaties die vereisen dat gebruikers een enkele filter- of sorteermogelijkheid selecteren, zoals het sorteren van een lijst met items op verschillende criteria.

<!-- vale off -->
## Toevoegen en verwijderen van RadioButtons {#adding-and-removing-radiobuttons}
<!-- vale on -->

Het is mogelijk om enkelvoudige of meerdere `RadioButton` objecten aan een groep toe te voegen en te verwijderen, zodat ze wederzijds exclusief controlegedrag vertonen, en zijn geassocieerd met een naam die mogelijk tot de groep behoort.

## Naamgeving {#naming}

Het naamattribuut in een `RadioButtonGroup` groepeert verwante RadioButtons samen, waardoor gebruikers een enkele keuze kunnen maken uit de aangeboden opties en exclusiviteit tussen de RadioButtons afdwingen. De naam van een groep wordt echter niet weerspiegeld in de DOM, maar is een handige tool voor de Java-ontwikkelaar.

## Best practices {#best-practices}

Om een optimale gebruikerservaring te waarborgen bij het gebruik van de RadioButton component, overweeg de volgende best practices:

1. **Duidelijk Labelen van Opties**: Voorzie duidelijke en beknopte labels voor elke `RadioButton` optie om de keuze nauwkeurig te beschrijven. Labels moeten gemakkelijk te begrijpen zijn en te onderscheiden van elkaar.

2. **Voorzie Standaardselectie**: Indien van toepassing, overweeg dan om een standaardselectie voor radio-knoppen te bieden om gebruikers te begeleiden wanneer ze de opties voor het eerst tegenkomen. De standaardselectie moet overeenkomen met de meest voorkomende of favoriete keuze.
