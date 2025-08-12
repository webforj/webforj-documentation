---
sidebar_position: 3
title: Elements
slug: element
_i18n_hash: 749e84016c244ec7349221d00dc0de9a
---
<DocChip chip='since' label='23.06' />
<JavadocLink type="foundation" location="com/webforj/component/element/Element" top='true'/>

webforJ-ontwikkelaars hebben de mogelijkheid om niet alleen te kiezen uit de rijke bibliotheek van beschikbare componenten, maar ook componenten van elders te integreren. Om dit te vergemakkelijken kan de `Element`-component worden gebruikt om de integratie van alles, van eenvoudige HTML-elementen tot meer complexe aangepaste webcomponenten, te vereenvoudigen.

:::important
De `Element`-component kan niet worden uitgebreid en is niet de basisonderdeel voor alle componenten binnen webforJ. Om meer te lezen over de componentenhiërarchie van webforJ, lees [dit artikel](../architecture/controls-components.md).
:::

<ComponentDemo 
path='/webforj/elementinputdemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/element/ElementInputDemoView.java'
cssURL='/css/element/elementInput.css'
/>

## Evenementen toevoegen {#adding-events}

Om gebruik te maken van evenementen die mogelijk bij uw element horen, kunt u de `addEventListener`-methoden van de `Element`-component gebruiken. Het toevoegen van een evenement vereist op zijn minst het type/naam van het evenement dat de component verwacht, en een luisteraar die aan het evenement moet worden toegevoegd. 

Er zijn ook aanvullende opties om evenementen verder aan te passen door gebruik te maken van de configuraties van evenementenopties.

<ComponentDemo 
path='/webforj/elementinputevent?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/element/ElementInputEventView.java'
cssURL='/css/element/elementInputEvent.css'
height='240px'
/>

## Interactie met componenten {#component-interaction}

De `Element`-component fungeert als een container voor andere componenten. Het biedt een manier om informatie voor kindcomponenten te organiseren en op te halen, en biedt een duidelijke set functies om deze kindcomponenten indien nodig toe te voegen of te verwijderen.

### Kindcomponenten toevoegen {#adding-child-components}

De `Element`-component ondersteunt de samenstelling van kindcomponenten. Ontwikkelaars kunnen complexe UI-structuren organiseren en beheren door componenten als kinderen aan de `Element` toe te voegen. Er zijn drie methoden beschikbaar om inhoud binnen een `Element` in te stellen:

1. **`add(Component... components)`**: Deze methode stelt één of meerdere componenten in staat om te worden toegevoegd aan een optionele `String` die een specifieke slot aangeeft wanneer deze wordt gebruikt met een Web Component. Het weglaten van de slot zal de component tussen de HTML-tags toevoegen.

2. **`setHtml(String html)`**: Deze methode neemt de `String` die aan de methode is doorgegeven en injecteert het als HTML binnen de component. Afhankelijk van de `Element` kan dit op verschillende manieren worden gerenderd.

3. **`setText(String text)`**: Deze methode gedraagt zich vergelijkbaar met de `setHtml()`-methode, maar voegt letterlijke tekst in de `Element` in.

<ComponentDemo 
path='/webforj/elementinputtext?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/element/ElementInputTextView.java'
cssURL='/css/element/elementInput.css'
height='175px'
/>

:::tip
Het aanroepen van `setHtml()` of `setText()` vervangt de inhoud die momenteel tussen de openings- en sluitingstags van het element is. 
:::

### Componenten verwijderen {#removing-components}

Naast het toevoegen van componenten aan een `Element`, zijn de volgende methoden geïmplementeerd voor het verwijderen van verschillende kindcomponenten:

1. **`remove(Component... components)`**: Deze methode neemt een of meer componenten en zal deze verwijderen als kindcomponenten.

2. **`removeAll()`**: Deze methode verwijdert alle kindcomponenten uit de `Element`.

### Componenten toegankelijk maken {#accessing-components}

Om toegang te krijgen tot de verschillende kindcomponenten die aanwezig zijn binnen een `Element`, of informatie over deze componenten, zijn de volgende methoden beschikbaar:

1. **`getComponents()`**: Deze methode retourneert een Java `List` van alle kinderen van de `Element`.

2. **`getComponents(String id)`**: Deze methode is vergelijkbaar met de bovenstaande methode, maar neemt de serverzijde ID van een specifieke component en retourneert deze wanneer gevonden.

3. **`getComponentCount()`**: Retourneert het aantal kindcomponenten dat aanwezig is binnen de `Element`.

## Het aanroepen van JavaScript-functies {#calling-javascript-functions}

De `Element`-component biedt twee API-methoden waarmee JavaScript-functies kunnen worden aangeroepen op HTML-elementen.

1. **`callJsFunction(String functionName, Object... arguments)`**: Deze methode neemt een functienaam als een string en neemt optioneel een of meer Objecten als parameters voor de functie. Deze methode wordt synchronisch uitgevoerd, wat betekent dat de **uitvoerende thread is geblokkeerd** totdat de JS-methode retourneert, wat resulteert in een round trip. De resultaten van de functie worden geretourneerd als een `Object`, dat kan worden gecast en gebruikt in Java.

2. **`callJsFunctionAsync(String functionName, Object... arguments)`**: Net als bij de vorige methode kan een functienaam en optionele argumenten voor de functie worden doorgegeven. Deze methode wordt asynchroon uitgevoerd en **blokkeert de uitvoerende thread niet**. Het retourneert een <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink>, waarmee verder kan worden gecommuniceerd met de functie en de bijbehorende gegevens.

### Parameters doorgeven {#passing-parameters}

Argumenten die aan deze methoden worden doorgegeven en die worden gebruikt bij de uitvoering van JS-functies, worden geserializeerd als een JSON-array. Er zijn twee opmerkelijke argumenttypes die als volgt worden behandeld:
- `this`: Het gebruik van het sleutelwoord `this` geeft de methode een referentie naar de clientzijde versie van de oproepende component.
- `Component`: Alle Java-componentinstanties die in een van de JsFunction-methoden worden doorgegeven, worden vervangen door de clientzijde versie van de component.

:::info
Zowel synchronische als asynchronische functieaanroepen wachten om een methode aan te roepen totdat de `Element` aan de DOM is toegevoegd voordat de functie wordt uitgevoerd, maar `callJsFunction()` wacht niet op enige `component`-argumenten om te bevestigen, wat kan resulteren in falen. Omgekeerd kan het aanroepen van `callJsFunctionAsync()` nooit voltooid worden als er nooit een componentargument wordt bevestigd.
:::

In de onderstaande demo wordt een evenement toegevoegd aan een HTML `Button`. Dit evenement wordt vervolgens programmatig geactiveerd door de `callJsFunctionAsync()`-methode aan te roepen. De resulterende <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink> wordt vervolgens gebruikt om een ander berichtvenster te maken zodra de asynchroon functie is voltooid.

<ComponentDemo 
path='/webforj/elementinputfunction?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/element/ElementInputFunctionView.java'
cssURL='/css/element/elementInput.css'
height='240px'
/>

## JavaScript uitvoeren {#executing-javascript}

Naast het uitvoeren van JavaScript vanuit het applicatieniveau, is het ook mogelijk om JavaScript vanuit het `Element`-niveau uit te voeren. Het uitvoeren van deze uitvoering op het `Element`-niveau maakt het mogelijk om de context van het HTML-element in de uitvoering op te nemen. Dit is een krachtig hulpmiddel dat fungeert als een conduit voor ontwikkelaars naar interactieve mogelijkheden met clientzijde omgevingen.

Net als bij het uitvoeren van functies kan JavaScript synchronisch of asynchroon worden uitgevoerd met behulp van de volgende methoden:

1. **`executeJs(String script)`**: Deze methode neemt een `String` die wordt uitgevoerd als JavaScript-code aan de client. Dit script wordt synchronisch uitgevoerd, wat betekent dat de **uitvoerende thread is geblokkeerd** totdat de JS-uitvoering retourneert en resulteert in een round trip. De resultaten van de functie worden geretourneerd als een `Object`, dat kan worden gecast en gebruikt in Java.

2. **`executeJsAsync(String script)`**: Net als bij de vorige methode, wordt een doorgegeven `String`-parameter uitgevoerd als JavaScript-code aan de client. Deze methode wordt asynchroon uitgevoerd en **blokkeert de uitvoerende thread niet**. Het retourneert een <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink>, waarmee verder kan worden gecommuniceerd met de functie en de bijbehorende gegevens.

:::tip
Deze methoden hebben toegang tot het sleutelwoord `component`, wat de JavaScript-code toegang geeft tot de clientzijde instantie van de component die de JavaScript uitvoert.
:::
