---
sidebar_position: 3
title: Elements
slug: element
_i18n_hash: d77ff55b483b72de9ee1d36473d7751d
---
<DocChip chip='since' label='23.06' />
<JavadocLink type="foundation" location="com/webforj/component/element/Element" top='true'/>

webforJ-ontwikkelaars hebben de mogelijkheid om niet alleen te kiezen uit de rijke bibliotheek van aangeboden componenten, maar ook om componenten van elders te integreren. Om dit te vergemakkelijken, kan de `Element`-component worden gebruikt om de integratie van alles, van eenvoudige HTML-elementen tot complexere aangepaste webcomponenten, te vereenvoudigen.

:::important
De `Element`-component kan niet worden uitgebreid en is niet de basiscomponent voor alle componenten binnen webforJ. Lees meer over de componenthiërarchie van webforJ in [dit artikel](../architecture/controls-components.md).
:::

<ComponentDemo 
path='/webforj/elementinputdemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/element/ElementInputDemoView.java'
cssURL='/css/element/elementInput.css'
/>

## Gebeurtenissen toevoegen {#adding-events}

Om gebruik te maken van gebeurtenissen die bij uw element kunnen komen, kunt u de `addEventListener`-methoden van de `Element`-component gebruiken. Het toevoegen van een gebeurtenis vereist ten minste het type/naam van de gebeurtenis die de component verwacht, en een listener die aan de gebeurtenis wordt toegevoegd.

Er zijn ook aanvullende opties om gebeurtenissen verder aan te passen met behulp van de configuraties voor gebeurtenisopties.

<ComponentDemo 
path='/webforj/elementinputevent?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/element/ElementInputEventView.java'
cssURL='/css/element/elementInputEvent.css'
height='240px'
/>

## Componentinteractie {#component-interaction}

De `Element`-component fungeert als een container voor andere componenten. Het biedt een manier om informatie voor kindcomponenten te organiseren en op te halen, en biedt een duidelijke set functies om deze kindcomponenten indien nodig toe te voegen of te verwijderen.

### Kindcomponenten toevoegen {#adding-child-components}

De `Element`-component ondersteunt de samenstelling van kindcomponenten. Ontwikkelaars kunnen complexe UI-structuren organiseren en beheren door componenten als kinderen aan de `Element` toe te voegen. Er zijn drie methoden beschikbaar om inhoud binnen een `Element` in te stellen:

1. **`add(Component... components)`**: Deze methode stelt in staat om één of meerdere componenten toe te voegen aan een optionele `String` die een specifieke slot aanduidt wanneer deze wordt gebruikt met een Web Component. Het weglaten van de slot voegt de component tussen de HTML-tags toe.

2. **`setHtml(String html)`**: Deze methode neemt de `String` die aan de methode is doorgegeven en injecteert het als HTML binnen de component. Afhankelijk van de `Element` kan dit op verschillende manieren worden weergegeven.

3. **`setText(String text)`**: Deze methode gedraagt zich vergelijkbaar met de `setHtml()`-methode, maar injecteert literaal tekst in de `Element`.

<ComponentDemo 
path='/webforj/elementinputtext?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/element/ElementInputTextView.java'
cssURL='/css/element/elementInput.css'
height='175px'
/>

:::tip
Het aanroepen van `setHtml()` of `setText()` vervangt de inhoud die momenteel tussen de opening- en sluittags van het element staat.
:::

### Componenten verwijderen {#removing-components}

Naast het toevoegen van componenten aan een `Element`, zijn de volgende methoden geïmplementeerd om verschillende kindcomponenten te verwijderen:

1. **`remove(Component... components)`**: Deze methode neemt één of meerdere componenten en verwijdert ze als kindcomponenten.

2. **`removeAll()`**: Deze methode verwijdert alle kindcomponenten uit de `Element`.

### Componenten toegankelijk maken {#accessing-components}

Om toegang te krijgen tot de verschillende kindcomponenten binnen een `Element`, of informatie over deze componenten, zijn de volgende methoden beschikbaar:

1. **`getComponents()`**: Deze methode retourneert een Java `List` van alle kinderen van de `Element`.

2. **`getComponents(String id)`**: Deze methode is vergelijkbaar met de bovenstaande methode, maar neemt de server-side ID van een specifieke component en retourneert deze wanneer deze is gevonden.

3. **`getComponentCount()`**: Retourneert het aantal kindcomponenten dat aanwezig is binnen de `Element`.

## JavaScript-functies aanroepen {#calling-javascript-functions}

De `Element`-component biedt twee API-methoden waarmee JavaScript-functies op HTML-elementen kunnen worden aangeroepen.

1. **`callJsFunction(String functionName, Object... arguments)`**: Deze methode neemt een functienaam als een string en neemt optioneel één of meer Objecten als parameters voor de functie. Deze methode wordt synchronistisch uitgevoerd, wat betekent dat de **uitvoerende thread wordt geblokkeerd** totdat de JS-methode retourneert, wat resulteert in een roundtrip. De resultaten van de functie worden geretourneerd als een `Object`, dat kan worden gecast en in Java kan worden gebruikt.

2. **`callJsFunctionAsync(String functionName, Object... arguments)`**: Net als bij de vorige methode kan een functienaam en optionele argumenten voor de functie worden doorgegeven. Deze methode wordt asynchronisch uitgevoerd en **blokkeert de uitvoerende thread niet**. Het retourneert een <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink>, waarmee verdere interactie met de functie en zijn payload mogelijk is.

### Parameters doorgeven {#passing-parameters}

Argumenten die aan deze methoden worden doorgegeven en die worden gebruikt bij de uitvoering van JS-functies, worden geserialiseerd als een JSON-array. Er zijn twee opmerkelijke argumenttypes die als volgt worden behandeld:
- `this`: Het gebruik van het sleutelwoord `this` geeft de methode een referentie naar de client-side versie van de aanroepende component.
- `Component`: Elke Java-componentinstantie die wordt doorgegeven aan een van de JsFunction-methoden, wordt vervangen door de client-side versie van de component.

:::info
Zowel synchronische als asynchronische functie-aanroepen wachten om een methode aan te roepen totdat de `Element` is toegevoegd aan de DOM voordat ze een functie uitvoeren, maar `callJsFunction()` wacht niet op enige `component`-argumenten om aan te sluiten, wat kan leiden tot falen. Daarentegen kan het aanroepen van `callJsFunctionAsync()` nooit voltooid worden als een componentargument nooit is aangesloten.
:::

In de onderstaande demo wordt een evenement toegevoegd aan een HTML `Button`. Dit evenement wordt vervolgens programmatisch geactiveerd door de methode `callJsFunctionAsync()` aan te roepen. De resulterende <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink> wordt vervolgens gebruikt om een andere boodschapdoos te creëren zodra de asynchronische functie is voltooid.

<ComponentDemo 
path='/webforj/elementinputfunction?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/element/ElementInputFunctionView.java'
cssURL='/css/element/elementInput.css'
height='240px'
/>

## JavaScript uitvoeren {#executing-javascript}

Naast het uitvoeren van JavaScript vanuit het toepassingsniveau, is het ook mogelijk om JavaScript vanuit het `Element`-niveau uit te voeren. Het uitvoeren van deze uitvoering op het `Element`-niveau stelt de context van het HTML-element in staat om te worden opgenomen in de uitvoering. Dit is een krachtige tool die fungeert als een kanaal voor de ontwikkelaar naar interactieve mogelijkheden met client-side omgevingen.

Vergelijkbaar met functie-uitvoering kan JavaScript synchronisch of asynchronisch worden uitgevoerd met de volgende methoden:

1. **`executeJs(String script)`**: Deze methode neemt een `String`, die wordt uitgevoerd als JavaScript-code aan de client. Dit script wordt synchronisch uitgevoerd, wat betekent dat de **uitvoerende thread wordt geblokkeerd** totdat de JS-uitvoering retourneert en resulteert in een roundtrip. De resultaten van de functie worden geretourneerd als een `Object`, dat kan worden gecast en in Java kan worden gebruikt.

2. **`executeJsAsync(String script)`**: Net als bij de vorige methode zal een doorgegeven `String`-parameter worden uitgevoerd als JavaScript-code aan de client. Deze methode wordt asynchronisch uitgevoerd en **blokkeert de uitvoerende thread niet**. Het retourneert een <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink>, waarmee verdere interactie met de functie en zijn payload mogelijk is.

:::tip
Deze methoden hebben toegang tot het sleutelwoord `component`, waarmee de JavaScript-code toegang heeft tot de client-side instantie van de component die de JavaScript uitvoert.
:::
