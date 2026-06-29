---
sidebar_position: 5
title: Elements
description: >-
  Integrate raw HTML tags and custom web components in webforJ using the Element
  class to add children, set content, and invoke JavaScript.
slug: element
_i18n_hash: d941e314cdd63d19471e80936ef5d6bc
---
<JavadocLink type="foundation" location="com/webforj/component/element/Element" top='true'/>

webforJ-ontwikkelaars hebben de optie om niet alleen te kiezen uit de rijke bibliotheek van componenten die worden aangeboden, maar ook componenten van elders te integreren. Om dit te vergemakkelijken kan de `Element`-component worden gebruikt om de integratie van alles te vereenvoudigen, van eenvoudige HTML-elementen tot meer complexe aangepaste webcomponenten. 

:::important
De `Element`-component kan niet worden uitgebreid en is geen basiscomponent voor alle componenten binnen webforJ. Om meer te lezen over de componenthiërarchie van webforJ, lees [dit artikel](../architecture/controls-components.md).
:::

<ComponentDemo
path='/webforj/elementinputdemo'
files={[
  'src/main/java/com/webforj/samples/views/element/ElementInputDemoView.java',
  'src/main/resources/static/css/element/elementInput.css',
]}
/>

## Toevoegen van evenementen {#adding-events}

Om gebruik te maken van evenementen die mogelijk bij uw element komen, kunt u de `addEventListener`-methoden van de `Element`-component gebruiken. Het toevoegen van een evenement vereist ten minste het type/naam van het evenement dat de component verwacht en een listener die aan het evenement moet worden toegevoegd. 

Er zijn ook extra opties om evenementen verder aan te passen door gebruik te maken van de Event Options-configuraties.

<ComponentDemo
path='/webforj/elementinputevent'
files={[
  'src/main/java/com/webforj/samples/views/element/ElementInputEventView.java',
  'src/main/resources/static/css/element/elementInputEvent.css',
]}
height='240px'
/>

## Componentinteractie {#component-interaction}

De `Element`-component fungeert als een container voor andere componenten. Het biedt een manier om informatie voor kindcomponenten te organiseren en op te halen, en biedt een duidelijke set functies om deze kindcomponenten toe te voegen of te verwijderen zoals nodig.


### Toevoegen van kindcomponenten {#adding-child-components}

De `Element`-component ondersteunt de samenstelling van kindcomponenten. Ontwikkelaars kunnen complexe UI-structuren organiseren en beheren door componenten als kinderen aan de `Element` toe te voegen. Drie methoden zijn er om inhoud binnen een `Element` in te stellen:

1. **`add(Component... components)`**: Deze methode staat toe dat een of meerdere componenten worden toegevoegd aan een optioneel `String` dat een specifieke slot aanduidt wanneer gebruikt met een Web Component. Het weglaten van de slot zal de component tussen de HTML-tags toevoegen.

2. **`setText(String text)`**: Deze methode werkt vergelijkbaar met de `setHtml()`-methode, maar injecteert letterlijke tekst in de `Element`.

  ```java
  // Wordt weergegeven als de letterlijke tekens "<b>Status: gereed</b>"
  element.setText("<b>Status: gereed</b>");
  ```

  :::note Gebruik van de `<html>`-tag
  Eerdere versies van webforJ behandelden een waarde die in `<html>` was gewikkeld en naar `setText()` werd doorgegeven als HTML. Dit gedrag is verouderd en zal worden verwijderd in webforJ 27.00.

  De eerste keer dat een in `<html>` gewikkelde waarde `setText()` bereikt, wordt er een waarschuwing gelogd die de component en de aanroepplaats noemt, zodat de aanroep kan worden verplaatst naar `setHtml()`.

  Om de standaard van webforJ 27.00 vooruitlopend aan te nemen, stelt u `webforj.legacyHtmlInText` in op `false`. In een Spring-app wordt dezelfde waarde ingesteld via `webforj.legacy-html-in-text`.

  ```java
  // webforj.legacyHtmlInText = true (standaard)
  element.setText("<html><b>Status: gereed</b></html>"); // geeft vet weer

  // webforj.legacyHtmlInText = false
  element.setText("<html><b>Status: gereed</b></html>"); // toont de tekens <b>Status: gereed</b>
  ```
  :::

3. **`setHtml(String html)`**: Deze methode neemt de `String` die naar de methode is doorgegeven en injecteert deze als HTML binnen de component. Afhankelijk van de `Element` kan dit op verschillende manieren worden weergegeven.

  :::danger Cross-site scripting (XSS)
  Ter bescherming tegen [cross-site scripting (XSS)-aanvallen](/docs/security/application-security/common-threats#cross-site-scripting-xss), gebruik `setHtml()` alleen met inhoud die u direct beheert.
  :::

<ComponentDemo
path='/webforj/elementinputtext'
files={[
  'src/main/java/com/webforj/samples/views/element/ElementInputTextView.java',
  'src/main/resources/static/css/element/elementInput.css',
]}
height='175px'
/>

:::tip
Het aanroepen van `setHtml()` of `setText()` vervangt de inhoud die momenteel tussen de openings- en sluitingstags van het element wordt bevat.
:::

### Componenten verwijderen {#removing-components}

Naast het toevoegen van componenten aan een `Element`, zijn de volgende methoden geïmplementeerd voor het verwijderen van verschillende kindcomponenten:

1. **`remove(Component... components)`**: Deze methode neemt een of meer componenten en verwijdert ze als kindcomponenten.

2. **`removeAll()`**: Deze methode verwijdert alle kindcomponenten uit de `Element`.

### Componenten toegankelijk maken {#accessing-components}

Om toegang te krijgen tot de verschillende kindcomponenten die aanwezig zijn binnen een `Element`, of informatie over deze componenten, zijn de volgende methoden beschikbaar:

1. **`getComponents()`**: Deze methode retourneert een Java `List` van alle kinderen van de `Element`. 

2. **`getComponents(String id)`**: Deze methode is vergelijkbaar met de hierboven vermelde methode, maar accepteert de serverzijde ID van een specifieke component en retourneert deze wanneer deze wordt gevonden.

3. **`getComponentCount()`**: Geeft het aantal kindcomponenten aan dat aanwezig is binnen de `Element`. 


## Aanroepen van JavaScript-functies {#calling-javascript-functions}

De `Element`-component biedt twee API-methoden waarmee JavaScript-functies op HTML-elementen kunnen worden aangeroepen. 

1. **`callJsFunction(String functionName, Object... arguments)`**: Deze methode neemt een functienaam als string en accepteert optioneel een of meer Objecten als parameters voor de functie. Deze methode wordt synchronisch uitgevoerd, wat betekent dat de **uitvoerende thread wordt geblokkeerd** totdat de JS-methode retourneert, wat resulteert in een rondreis. De resultaten van de functie worden geretourneerd als een `Object`, dat kan worden gecast en gebruikt in Java. 

2. **`callJsFunctionAsync(String functionName, Object... arguments)`**: Zoals bij de vorige methode kan een functienaam en optionele argumenten voor de functie worden doorgegeven. Deze methode voert asynchroon uit en **blokkeert de uitvoerende thread niet**. Het retourneert een <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink>, wat verdere interactie met de functie en zijn payload mogelijk maakt.

### Parameters doorgeven {#passing-parameters}

Argumenten die naar deze methoden worden doorgegeven en die worden gebruikt bij de uitvoering van JS-functies, worden geserialiseerd als een JSON-array. Er zijn twee opmerkelijke argumenttypes die als volgt worden behandeld:
- `this`: Het gebruik van het `this`-type geeft de methode een verwijzing naar de client-side versie van de aanroepende component.
- `Component`: Alle Java-componentinstellingen die naar een van de JsFunction-methoden zijn doorgegeven, worden vervangen door de client-side versie van de component.

:::info
Zowel synchronische als asynchronische functie-aanroepen wachten totdat de `Element` aan de DOM is toegevoegd voordat een functie wordt uitgevoerd, maar `callJsFunction()` wacht niet op de aanhechting van enige `component`-argumenten, wat kan resulteren in een mislukking. Omgekeerd kan het aanroepen van `callJsFunctionAsync()` nooit worden voltooid als een componentargument nooit is gehecht.
:::

In de demo hieronder wordt een evenement toegevoegd aan een HTML `Button`. Dit evenement wordt vervolgens programmatig geactiveerd door de methode `callJsFunctionAsync()` aan te roepen. Het resulterende <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink> wordt vervolgens gebruikt om een ander berichtvenster te creëren zodra de asynchronische functie is voltooid.

<ComponentDemo
path='/webforj/elementinputfunction'
files={[
  'src/main/java/com/webforj/samples/views/element/ElementInputFunctionView.java',
  'src/main/resources/static/css/element/elementInput.css',
]}
height='240px'
/>

## JavaScript uitvoeren {#executing-javascript}

Naast het uitvoeren van JavaScript vanuit de applicatieniveau, is het ook mogelijk om JavaScript vanuit het `Element`-niveau uit te voeren. Het uitvoeren van deze uitvoering op het `Element`-niveau maakt het mogelijk om de context van het HTML-element in de uitvoering op te nemen. Dit is een krachtig hulpmiddel dat fungeert als een conduit voor de ontwikkelaar voor interactieve mogelijkheden met client-side omgevingen.

Vergelijkbaar met functie-uitvoering kan JavaScript synchronisch of asynchroon worden uitgevoerd met de volgende methoden:

1. **`executeJs(String script)`**: Deze methode neemt een `String`, die zal worden uitgevoerd als JavaScript-code in de client. Dit script wordt synchronisch uitgevoerd, wat betekent dat de **uitvoerende thread wordt geblokkeerd** totdat de JS-uitvoering retourneert, wat resulteert in een rondreis. De resultaten van de functie worden geretourneerd als een `Object`, dat kan worden gecast en gebruikt in Java.

2. **`executeJsAsync(String script)`**: Zoals bij de vorige methode zal een doorgegeven `String`-parameter worden uitgevoerd als JavaScript-code op de client. Deze methode voert asynchroon uit en **blokkeert de uitvoerende thread niet**. Het retourneert een <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink>, wat verdere interactie met de functie en zijn payload mogelijk maakt.

:::tip
Deze methoden hebben toegang tot het `component`-sleutelwoord, wat de JavaScript-code toegang geeft tot de client-side instantie van de component die de JavaScript uitvoert.
:::
