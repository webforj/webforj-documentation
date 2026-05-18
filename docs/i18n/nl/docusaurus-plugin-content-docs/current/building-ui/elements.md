---
sidebar_position: 5
title: Elements
slug: element
_i18n_hash: 820bed6c059dad74a523673f245f3b2a
---
<JavadocLink type="foundation" location="com/webforj/component/element/Element" top='true'/>

webforJ-ontwikkelaars hebben de mogelijkheid om niet alleen te kiezen uit de rijke bibliotheek van componenten, maar ook om componenten van elders te integreren. Om dit te vergemakkelijken, kan de `Element`-component worden gebruikt om de integratie van alles te vereenvoudigen, van eenvoudige HTML-elementen tot complexere aangepaste webcomponenten. 

:::important
De `Element`-component kan niet worden uitgebreid en is geen basiscomponent voor alle componenten binnen webforJ. Voor meer informatie over de componenthiërarchie van webforJ, lees [dit artikel](../architecture/controls-components.md).
:::

<ComponentDemo
path='/webforj/elementinputdemo'
files={[
  'src/main/java/com/webforj/samples/views/element/ElementInputDemoView.java',
  'src/main/resources/static/css/element/elementInput.css',
]}
/>

## Toevoegen van gebeurtenissen {#adding-events}

Om gebruik te maken van de gebeurtenissen die mogelijk bij uw element komen, kunt u de `addEventListener`-methoden van de `Element`-component gebruiken. Het toevoegen van een gebeurtenis vereist ten minste het type/naam van de gebeurtenis die de component verwacht, en een luisteraar die aan de gebeurtenis moet worden toegevoegd.

Er zijn ook aanvullende opties om evenementen verder aan te passen met behulp van de configuraties voor Gebeurtenisopties.

<ComponentDemo
path='/webforj/elementinputevent'
files={[
  'src/main/java/com/webforj/samples/views/element/ElementInputEventView.java',
  'src/main/resources/static/css/element/elementInputEvent.css',
]}
height='240px'
/>

## Componentinteractie {#component-interaction}

De `Element`-component fungeert als een container voor andere componenten. Het biedt een manier om informatie voor kindcomponenten te organiseren en te verkrijgen, en biedt een duidelijke set functies om deze kindcomponenten indien nodig toe te voegen of te verwijderen.

### Toevoegen van kindcomponenten {#adding-child-components}

De `Element`-component ondersteunt de samenstelling van kindcomponenten. Ontwikkelaars kunnen complexe UI-structuren organiseren en beheren door componenten als kinderen toe te voegen aan de `Element`. Er zijn drie methoden om inhoud binnen een `Element` in te stellen:

1. **`add(Component... components)`**: Deze methode stelt in staat om één of meerdere componenten toe te voegen aan een optionele `String` die een specifieke slot aangeeft bij gebruik met een Web Component. Het weglaten van de slot zal de component tussen de HTML-tags toevoegen.

2. **`setHtml(String html)`**: Deze methode neemt de `String` die aan de methode is doorgegeven en injecteert deze als HTML binnen de component. Afhankelijk van de `Element` kan dit op verschillende manieren worden weergegeven.

3. **`setText(String text)`**: Deze methode werkt op een vergelijkbare manier als de `setHtml()`-methode, maar injecteert letterlijke tekst in de `Element`.

<ComponentDemo
path='/webforj/elementinputtext'
files={[
  'src/main/java/com/webforj/samples/views/element/ElementInputTextView.java',
  'src/main/resources/static/css/element/elementInput.css',
]}
height='175px'
/>

:::tip
Het aanroepen van `setHtml()` of `setText()` vervangt de inhoud die momenteel tussen de opening en sluitingstags van het element staat.
:::

### Verwijderen van componenten {#removing-components}

Naast het toevoegen van componenten aan een `Element`, zijn de volgende methoden geïmplementeerd voor het verwijderen van verschillende kindcomponenten:

1. **`remove(Component... components)`**: Deze methode neemt één of meer componenten en verwijdert deze als kindcomponenten.

2. **`removeAll()`**: Deze methode verwijdert alle kindcomponenten van de `Element`.

### Toegang tot componenten {#accessing-components}

Om toegang te krijgen tot de verschillende kindcomponenten die aanwezig zijn binnen een `Element`, of informatie met betrekking tot deze componenten, zijn de volgende methoden beschikbaar:

1. **`getComponents()`**: Deze methode retourneert een Java `List` van alle kinderen van de `Element`. 

2. **`getComponents(String id)`**: Deze methode is vergelijkbaar met de methode hierboven, maar neemt de serverzijde ID van een specifieke component en retourneert deze wanneer gevonden.

3. **`getComponentCount()`**: Retourneert het aantal kindcomponenten dat aanwezig is binnen de `Element`. 

## Aanroepen van JavaScript-functies {#calling-javascript-functions}

De `Element`-component biedt twee API-methoden waarmee JavaScript-functies op HTML-elementen kunnen worden aangeroepen. 

1. **`callJsFunction(String functionName, Object... arguments)`**: Deze methode neemt een functienaam als string en optioneel één of meer Objecten als parameters voor de functie. Deze methode wordt synchronisch uitgevoerd, wat betekent dat de **uitvoerende thread wordt geblokkeerd** totdat de JS-methode terugkeert, en resulteert in een round trip. De resultaten van de functie worden geretourneerd als een `Object`, dat kan worden gecast en gebruikt in Java. 

2. **`callJsFunctionAsync(String functionName, Object... arguments)`**: Net als bij de vorige methode kan een functienaam en optionele argumenten voor de functie worden doorgegeven. Deze methode wordt asynchroon uitgevoerd en **blokkeert de uitvoerende thread niet**. Het retourneert een <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink>, waarmee verder interactie mogelijk is met de functie en zijn payload.

### Parameters doorgeven {#passing-parameters}

Argumenten die aan deze methoden worden doorgegeven en die worden gebruikt bij de uitvoering van JS-functies, worden als een JSON-array seriëel. Er zijn twee opmerkelijke argumenttypen die als volgt worden behandeld:
- `this`: Het gebruik van het sleutelwoord `this` geeft de methode een referentie naar de client-side versie van de aanroepende component.
- `Component`: Elke Java-componentinstance die aan een van de JsFunction-methoden wordt doorgegeven, wordt vervangen door de client-side versie van de component.

:::info
Zowel synchronische als asynchronische functietoevoegingen wachten totdat de `Element` aan de DOM is toegevoegd voordat een functie wordt uitgevoerd, maar `callJsFunction()` wacht niet op enige `component`-argumenten om aan te sluiten, wat kan resulteren in een mislukking. Omgekeerd kan het aanroepen van `callJsFunctionAsync()` nooit voltooid worden als een componentargument nooit is aangesloten.
:::

In de onderstaande demo wordt een gebeurtenis toegevoegd aan een HTML `Button`. Deze gebeurtenis wordt vervolgens programma-gewijs afgevuurd door de `callJsFunctionAsync()`-methode aan te roepen. De resulterende <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink> wordt vervolgens gebruikt om een andere berichtbox te maken zodra de asynchronische functie is voltooid.

<ComponentDemo
path='/webforj/elementinputfunction'
files={[
  'src/main/java/com/webforj/samples/views/element/ElementInputFunctionView.java',
  'src/main/resources/static/css/element/elementInput.css',
]}
height='240px'
/>

## Uitvoeren van JavaScript {#executing-javascript}

Naast het uitvoeren van JavaScript vanuit het appniveau, is het mogelijk om JavaScript ook op het niveau van de `Element` uit te voeren. Deze uitvoer op het niveau van de `Element` stelt de context van het HTML-element in staat om bij de uitvoering te worden betrokken. Dit is een krachtige tool die fungeert als een conduit voor ontwikkelaars naar interactieve mogelijkheden met client-side omgevingen.

Vergelijkbaar met functiewerkzaamheden, kan het uitvoeren van JavaScript synchronisch of asynchronisch worden gedaan met de volgende methoden:

1. **`executeJs(String script)`**: Deze methode neemt een `String`, die als JavaScript-code op de client zal worden uitgevoerd. Dit script wordt synchronisch uitgevoerd, wat betekent dat de **uitvoerende thread wordt geblokkeerd** totdat de JS-uitvoering terugkeert, en resulteert in een round trip. De resultaten van de functie worden geretourneerd als een `Object`, dat kan worden gecast en gebruikt in Java.

2. **`executeJsAsync(String script)`**: Net als bij de vorige methode, zal een doorgegeven `String`-parameter worden uitgevoerd als JavaScript-code op de client. Deze methode wordt asynchroon uitgevoerd en **blokkeert de uitvoerende thread niet**. Het retourneert een <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink>, waarmee verder interactie mogelijk is met de functie en zijn payload.

:::tip
Deze methoden hebben toegang tot het `component`-sleutelwoord, waarmee de JavaScript-code toegang heeft tot de client-side instantie van de component die de JavaScript uitvoert.
:::
