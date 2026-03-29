---
sidebar_position: 5
title: Elements
slug: element
_i18n_hash: 2ea3ba8ae8756dcea1ee5d0eb9fb0cf9
---
<JavadocLink type="foundation" location="com/webforj/component/element/Element" top='true'/>

webforJ-ontwikkelaars hebben de optie niet alleen te kiezen uit de rijke bibliotheek van componenten die worden aangeboden, maar ook componenten van elders te integreren. Om dit te vergemakkelijken, kan de `Element`-component worden gebruikt om de integratie van alles, van eenvoudige HTML-elementen tot meer complexe aangepaste webcomponenten, te vereenvoudigen. 

:::important
De `Element`-component kan niet worden uitgebreid en is geen basiscomponent voor alle componenten binnen webforJ. Om meer te lezen over de componenthiërarchie van webforJ, lees [dit artikel](../architecture/controls-components.md).
:::

<ComponentDemo 
path='/webforj/elementinputdemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/element/ElementInputDemoView.java'
cssURL='/css/element/elementInput.css'
/>

## Evenementen toevoegen {#adding-events}

Om gebruik te maken van evenementen die mogelijk bij uw element komen, kunt u de methoden `addEventListener` van de `Element`-component gebruiken. Het toevoegen van een evenement vereist minimaal het type/naam van het evenement dat de component verwacht en een listener die aan het evenement moet worden toegevoegd. 

Er zijn ook extra opties om evenementen verder aan te passen door gebruik te maken van de configuraties voor Evenementopties.

<ComponentDemo 
path='/webforj/elementinputevent?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/element/ElementInputEventView.java'
cssURL='/css/element/elementInputEvent.css'
height='240px'
/>

## Componentinteractie {#component-interaction}

De `Element`-component fungeert als een container voor andere componenten. Het biedt een manier om informatie voor kindcomponenten te organiseren en op te halen, en biedt een duidelijke set functies om deze kindcomponenten naar behoefte toe te voegen of te verwijderen.

### Kindercomponenten toevoegen {#adding-child-components}

De `Element`-component ondersteunt de samenstelling van kindercomponenten. Ontwikkelaars kunnen complexe UI-structuren organiseren en beheren door componenten als kinderen aan de `Element` toe te voegen. Drie methoden bestaan om inhoud binnen een `Element` in te stellen:

1. **`add(Component... components)`**: Deze methode laat één of meerdere componenten toe om te worden toegevoegd aan een optionele `String` die een specifieke slot aangeeft wanneer deze wordt gebruikt met een Web Component. Het weglaten van de slot zal de component toevoegen tussen de HTML-tags.

2. **`setHtml(String html)`**: Deze methode neemt de `String` die aan de methode is doorgegeven en injecteert deze als HTML binnen de component. Afhankelijk van de `Element` kan dit op verschillende manieren worden weergegeven.

3. **`setText(String text)`**: Deze methode gedraagt zich vergelijkbaar met de methode `setHtml()`, maar injecteert letterlijk tekst in de `Element`.

<ComponentDemo 
path='/webforj/elementinputtext?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/element/ElementInputTextView.java'
cssURL='/css/element/elementInput.css'
height='175px'
/>

:::tip
Het aanroepen van `setHtml()` of `setText()` zal de inhoud vervangen die momenteel tussen de opening en sluitingstags van het element staat.
:::

### Componenten verwijderen {#removing-components}

Naast het toevoegen van componenten aan een `Element` zijn de volgende methoden geïmplementeerd voor het verwijderen van verschillende kindcomponenten:

1. **`remove(Component... components)`**: Deze methode neemt een of meer componenten en zal ze verwijderen als kindcomponenten.

2. **`removeAll()`**: Deze methode verwijdert alle kindcomponenten uit de `Element`.

### Componenten benaderen {#accessing-components}

Om toegang te krijgen tot de verschillende kindcomponenten die aanwezig zijn binnen een `Element`, of informatie met betrekking tot deze componenten, zijn de volgende methoden beschikbaar:

1. **`getComponents()`**: Deze methode retourneert een Java `List` van alle kinderen van de `Element`. 

2. **`getComponents(String id)`**: Deze methode is vergelijkbaar met de bovenstaande methode, maar neemt de serverzijde ID van een specifieke component en retourneert deze wanneer deze wordt gevonden.

3. **`getComponentCount()`**: Retourneert het aantal kindcomponenten dat aanwezig is binnen de `Element`. 


## JavaScript-functies aanroepen {#calling-javascript-functions}

De `Element`-component biedt twee API-methoden waarmee JavaScript-functies op HTML-elementen kunnen worden aangeroepen. 

1. **`callJsFunction(String functionName, Object... arguments)`**: Deze methode neemt een functienaam als string en kan optioneel een of meer Objecten als parameters voor de functie nemen. Deze methode wordt synchronisch uitgevoerd, wat betekent dat de **uitvoerende thread wordt geblokkeerd** totdat de JS-methode retourneert en resulteert in een heen-en-weertje. De resultaten van de functie worden geretourneerd als een `Object`, dat kan worden gecast en in Java kan worden gebruikt. 

2. **`callJsFunctionAsync(String functionName, Object... arguments)`**: Net als bij de vorige methode kan een functienaam en optionele argumenten voor de functie worden doorgegeven. Deze methode wordt asynchroon uitgevoerd en **blokkeert de uitvoerende thread niet**. Het retourneert een <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink>, dat verdere interactie met de functie en zijn payload mogelijk maakt.

### Parameters doorgeven {#passing-parameters}

Argumenten die naar deze methoden worden doorgegeven en die worden gebruikt bij de uitvoer van JS-functies worden geserialiseerd als een JSON-array. Er zijn twee opmerkelijke argumenttypes die als volgt worden behandeld:
- `this`: Het gebruik van het `this`-keyword geeft de methode een referentie naar de client-side versie van de aanroepende component.
- `Component`: Alle Java-componentinstanties die in een van de JsFunction-methoden worden doorgegeven, worden vervangen door de client-side versie van de component.

:::info
Zowel synchrone als asynchrone functie-aanroepen wachten om een methode aan te roepen totdat de `Element` aan de DOM is toegevoegd voordat een functie wordt uitgevoerd, maar `callJsFunction()` wacht niet op de attachment van `component`-argumenten, wat kan resulteren in een mislukking. Omgekeerd kan het aanroepen van `callJsFunctionAsync()` mogelijk nooit worden voltooid als een componentargument nooit is gekoppeld.
:::

In de demo hieronder wordt een gebeurtenis toegevoegd aan een HTML `Button`. Deze gebeurtenis wordt vervolgens programmatisch geactiveerd door de methode `callJsFunctionAsync()` aan te roepen. De resulterende <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink> wordt vervolgens gebruikt om een andere boodschapdoos te maken zodra de asynchrone functie is voltooid.

<ComponentDemo 
path='/webforj/elementinputfunction?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/element/ElementInputFunctionView.java'
cssURL='/css/element/elementInput.css'
height='240px'
/>

## JavaScript uitvoeren {#executing-javascript}

Naast het uitvoeren van JavaScript op niveau van de applicatie, is het ook mogelijk om JavaScript op niveau van de `Element` uit te voeren. Het uitvoeren van deze uitvoering op het niveau van de `Element` maakt het mogelijk om de context van het HTML-element op te nemen in de uitvoering. Dit is een krachtig hulpmiddel dat fungeert als een conduit voor ontwikkelaars om interactieve mogelijkheden met client-side omgevingen te creëren.

Net als bij het uitvoeren van functies kan JavaScript synchronisch of asynchroon worden uitgevoerd met de volgende methoden:

1. **`executeJs(String script)`**: Deze methode neemt een `String`, die als JavaScript-code op de client zal worden uitgevoerd. Dit script wordt synchronisch uitgevoerd, wat betekent dat de **uitvoerende thread wordt geblokkeerd** totdat de JS-uitvoering retourneert en resulteert in een heen-en-weertje. De resultaten van de functie worden geretourneerd als een `Object`, dat kan worden gecast en in Java kan worden gebruikt.

2. **`executeJsAsync(String script)`**: Net als bij de vorige methode wordt een doorgegeven `String`-parameter uitgevoerd als JavaScript-code op de client. Deze methode wordt asynchroon uitgevoerd en **blokkeert de uitvoerende thread niet**. Het retourneert een <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink>, dat verdere interactie met de functie en zijn payload mogelijk maakt.

:::tip
Deze methoden hebben toegang tot het `component`-keyword, dat de JavaScript-code toegang geeft tot de client-side instantie van de component die de JavaScript uitvoert.
:::
