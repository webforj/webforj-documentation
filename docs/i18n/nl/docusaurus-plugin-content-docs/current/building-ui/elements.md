---
sidebar_position: 5
title: Elements
slug: element
_i18n_hash: 067ff9e31676f6991dab011252151043
---
<JavadocLink type="foundation" location="com/webforj/component/element/Element" top='true'/>

webforJ-ontwikkelaars hebben de optie om niet alleen te kiezen uit de rijke bibliotheek van componenten die worden aangeboden, maar ook om componenten van elders te integreren. Om dit te vergemakkelijken, kan het `Element`-component worden gebruikt om de integratie van alles te vereenvoudigen, van eenvoudige HTML-elementen tot complexere aangepaste webcomponenten.

:::important
Het `Element`-component kan niet worden uitgebreid en is niet de basiscomponent voor alle componenten binnen webforJ. Om meer te lezen over de componenthiërarchie van webforJ, lees [dit artikel](../architecture/controls-components.md).
:::

<ComponentDemo 
path='/webforj/elementinputdemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/element/ElementInputDemoView.java'
cssURL='/css/element/elementInput.css'
/>

## Toevoegen van evenementen {#adding-events}

Om gebruik te maken van evenementen die bij uw element kunnen komen, kunt u de `addEventListener`-methoden van het `Element`-component gebruiken. Het toevoegen van een evenement vereist ten minste het type/naam van het evenement dat de component verwacht en een listener die aan het evenement wordt toegevoegd.

Er zijn ook aanvullende opties om evenementen verder aan te passen met behulp van de configuraties voor Event Options.

<ComponentDemo 
path='/webforj/elementinputevent?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/element/ElementInputEventView.java'
cssURL='/css/element/elementInputEvent.css'
height='240px'
/>

## Componentinteractie {#component-interaction}

Het `Element`-component fungeert als een container voor andere componenten. Het biedt een manier om informatie voor kindcomponenten te organiseren en op te halen, en biedt een duidelijke set functies om deze kindcomponenten naar behoefte toe te voegen of te verwijderen.

### Kindcomponenten toevoegen {#adding-child-components}

Het `Element`-component ondersteunt de compositie van kindcomponenten. Ontwikkelaars kunnen complexe UI-structuren organiseren en beheren door componenten als kinderen aan het `Element` toe te voegen. Er zijn drie methoden om inhoud binnen een `Element` in te stellen:

1. **`add(Component... components)`**: Deze methode stelt één of meerdere componenten in staat om te worden toegevoegd aan een optionele `String` die een specifieke slot aanduidt wanneer deze wordt gebruikt met een Web Component. Het weglaten van de slot voegt de component tussen de HTML-tags toe.

2. **`setHtml(String html)`**: Deze methode neemt de `String` die aan de methode wordt doorgegeven en injecteert deze als HTML binnen de component. Afhankelijk van het `Element` kan dit op verschillende manieren worden weergegeven.

3. **`setText(String text)`**: Deze methode gedraagt zich op een vergelijkbare manier als de `setHtml()`-methode, maar injecteert letterlijke tekst in het `Element`.

<ComponentDemo 
path='/webforj/elementinputtext?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/element/ElementInputTextView.java'
cssURL='/css/element/elementInput.css'
height='175px'
/>

:::tip
Het aanroepen van `setHtml()` of `setText()` vervangt de inhoud die momenteel tussen de openings- en sluitingstags van het element staat.
:::

### Componenten verwijderen {#removing-components}

Naast het toevoegen van componenten aan een `Element` zijn de volgende methoden geïmplementeerd voor het verwijderen van verschillende kindcomponenten:

1. **`remove(Component... components)`**: Deze methode neemt één of meerdere componenten en verwijdert ze als kindcomponenten.

2. **`removeAll()`**: Deze methode verwijdert alle kindcomponenten van het `Element`.

### Componenten benaderen {#accessing-components}

Om toegang te krijgen tot de verschillende kindcomponenten binnen een `Element`, of informatie over deze componenten, zijn de volgende methoden beschikbaar:

1. **`getComponents()`**: Deze methode retourneert een Java `List` van alle kinderen van het `Element`.

2. **`getComponents(String id)`**: Deze methode is vergelijkbaar met de bovenstaande methode, maar neemt de serverzijde ID van een specifieke component en retourneert deze wanneer deze wordt gevonden.

3. **`getComponentCount()`**: Retourneert het aantal kindcomponenten binnen het `Element`.

## JavaScript-functies aanroepen {#calling-javascript-functions}

Het `Element`-component biedt twee API-methoden waarmee JavaScript-functies op HTML-elementen kunnen worden aangeroepen.

1. **`callJsFunction(String functionName, Object... arguments)`**: Deze methode neemt een functienaam als een string en neemt optioneel één of meer Objecten als parameters voor de functie. Deze methode wordt synchroon uitgevoerd, wat betekent dat de **uitvoerende thread geblokkeerd is** totdat de JS-methode retourneert en resulteert in een rondreis. De resultaten van de functie worden geretourneerd als een `Object`, dat kan worden gecast en in Java kan worden gebruikt.

2. **`callJsFunctionAsync(String functionName, Object... arguments)`**: Net als bij de vorige methode kan een functienaam en optionele argumenten voor de functie worden doorgegeven. Deze methode wordt asynchroon uitgevoerd en **blokkeert de uitvoerende thread niet**. Het retourneert een <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink>, dat verdere interactie met de functie en de payload mogelijk maakt.

### Parameters doorgeven {#passing-parameters}

Argumenten die aan deze methoden worden doorgegeven en die worden gebruikt bij de uitvoering van JS-functies worden geserialiseerd als een JSON-array. Er zijn twee opmerkelijke argumenttypes die als volgt worden behandeld:
- `this`: Het gebruik van het `this`-sleutelwoord geeft de methode een referentie naar de clientzijde versie van de aanroepende component.
- `Component`: Elke Java-componentinstantie die in een van de JsFunction-methoden wordt doorgegeven, wordt vervangen door de clientzijde versie van de component.

:::info
Zowel synchroon als asynchroon functienaam zullen wachten totdat het `Element` aan de DOM is toegevoegd voordat een functie wordt uitgevoerd, maar `callJsFunction()` wacht niet op het aanhechten van eventuele `component`-argumenten, wat tot mislukking kan leiden. Omgekeerd kan het aanroepen van `callJsFunctionAsync()` nooit compleet zijn als een componentargument nooit is gehecht.
:::

In de demo hieronder wordt een evenement toegevoegd aan een HTML `Button`. Dit evenement wordt vervolgens programmatisch geactiveerd door de methode `callJsFunctionAsync()` aan te roepen. De resulterende <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink> wordt daarna gebruikt om een ander berichtvenster te creëren zodra de asynchrone functie is voltooid.

<ComponentDemo 
path='/webforj/elementinputfunction?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/element/ElementInputFunctionView.java'
cssURL='/css/element/elementInput.css'
height='240px'
/>

## JavaScript uitvoeren {#executing-javascript}

Naast het uitvoeren van JavaScript op app-niveau, is het ook mogelijk om JavaScript op het niveau van het `Element` uit te voeren. Het uitvoeren van deze uitvoering op het niveau van het `Element` stelt de context van het HTML-element in staat om in de uitvoer te worden opgenomen. Dit is een krachtig hulpmiddel dat als een kanaal voor de ontwikkelaar fungeert naar interactieve mogelijkheden met client-side omgevingen.

Vergelijkbaar met functiemanagement kan JavaScript synchronisch of asynchroon worden uitgevoerd met de volgende methoden:

1. **`executeJs(String script)`**: Deze methode neemt een `String`, die als JavaScript-code in de client zal worden uitgevoerd. Dit script wordt synchronisch uitgevoerd, wat betekent dat de **uitvoerende thread geblokkeerd is** totdat de JS-uitvoering retourneert, en resulteert in een rondreis. De resultaten van de functie worden geretourneerd als een `Object`, dat kan worden gecast en in Java kan worden gebruikt.

2. **`executeJsAsync(String script)`**: Net als bij de vorige methode zal een doorgegeven `String`-parameter als JavaScript-code op de client worden uitgevoerd. Deze methode wordt asynchroon uitgevoerd en **blokkeert de uitvoerende thread niet**. Het retourneert een <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink>, dat verdere interactie met de functie en de payload mogelijk maakt.

:::tip
Deze methoden hebben toegang tot het sleutelwoord `component`, waardoor de JavaScript-code toegang heeft tot de clientzijde instantie van de component die de JavaScript uitvoert.
:::
