---
sidebar_position: 5
title: Elements
sidebar_class_name: updated-content
description: >-
  Integrate raw HTML tags and custom web components in webforJ using the Element
  class to add children, set content, and call JavaScript functions.
slug: element
_i18n_hash: 988b2a49584036eee3b0475215a707ae
---
<JavadocLink type="foundation" location="com/webforj/component/element/Element" top='true'/>

webforJ-ontwikkelaars hebben niet alleen de keuze uit de rijke bibliotheek van geleverde componenten, maar kunnen ook componenten van elders integreren. Om dit te vergemakkelijken, kan de `Element`-component worden gebruikt om de integratie van alles, van eenvoudige HTML-elementen tot meer complexe aangepaste webcomponenten, te vereenvoudigen.

:::important
De `Element`-component kan niet worden uitgebreid en is niet de basiscomponent voor alle componenten binnen webforJ. Om meer te lezen over de componenthiërarchie van webforJ, lees [dit artikel](../architecture/controls-components.md).
:::

<ComponentDemo
path='/webforj/elementmeter'
files={[
  'src/main/java/com/webforj/samples/views/element/ElementMeterView.java',
  'src/main/resources/static/css/element/elementMeter.css',
]}
height='240px'
/>

## Evenementen toevoegen {#adding-events}

Om gebruik te maken van evenementen die mogelijk bij uw element horen, kunt u de `addEventListener`-methoden van de `Element`-component gebruiken. Het toevoegen van een evenement vereist ten minste het type/naam van het evenement dat de component verwacht en een listener die aan het evenement moet worden toegevoegd.

Er zijn ook extra opties om evenementen verder aan te passen met behulp van de Event Options-configuraties.

<ComponentDemo
path='/webforj/elementtaginput'
files={[
  'src/main/java/com/webforj/samples/views/element/ElementTagInputView.java',
  'src/main/resources/static/css/element/elementTagInput.css',
]}
height='240px'
/>

## Componentinteractie {#component-interaction}

De `Element`-component fungeert als een container voor andere componenten. Het biedt een manier om informatie voor kindcomponenten te organiseren en op te halen, en biedt een duidelijke set functies om deze kindcomponenten indien nodig toe te voegen of te verwijderen.

### Kindcomponenten toevoegen {#adding-child-components}

De `Element`-component ondersteunt de samenstelling van kindcomponenten. Ontwikkelaars kunnen complexe UI-structuren organiseren en beheren door componenten als kinderen aan de `Element` toe te voegen. Er zijn drie methoden om inhoud binnen een `Element` in te stellen:

1. **`add(Component... components)`**: Deze methode maakt het mogelijk om een of meerdere componenten toe te voegen aan een optionele `String` die een specifieke slot aangeeft wanneer deze wordt gebruikt met een Web Component. Het weglaten van de slot voegt de component tussen de HTML-tags in.

2. **`setHtml(String html)`**: Deze methode neemt de `String` die naar de methode is doorgegeven en injecteert deze als HTML binnen de component. Afhankelijk van de `Element` kan dit op verschillende manieren worden weergegeven.

3. **`setText(String text)`**: Deze methode gedraagt zich vergelijkbaar met de `setHtml()`-methode, maar voegt letterlijke tekst in de `Element` in.

<ComponentDemo
path='/webforj/elementfigure'
files={[
  'src/main/java/com/webforj/samples/views/element/ElementFigureView.java',
  'src/main/resources/static/css/element/elementFigure.css',
]}
height='240px'
/>

:::warning Inhoud vervangen
Het aanroepen van `setHtml()` of `setText()` vervangt de inhoud die momenteel tussen de openings- en sluitingstags van het element staat.
:::

### Componenten verwijderen {#removing-components}

Naast het toevoegen van componenten aan een `Element`, zijn de volgende methoden geïmplementeerd voor het verwijderen van verschillende kindcomponenten:

1. **`remove(Component... components)`**: Deze methode neemt een of meer componenten en verwijdert ze als kindcomponenten.

2. **`removeAll()`**: Deze methode verwijdert alle kindcomponenten van de `Element`.

### Componenten verkrijgen {#accessing-components}

Om toegang te krijgen tot de verschillende kindcomponenten binnen een `Element`, of informatie over deze componenten, zijn de volgende methoden beschikbaar:

1. **`getComponents()`**: Deze methode retourneert een Java `List` van alle kinderen van de `Element`.

2. **`getComponents(String id)`**: Deze methode is vergelijkbaar met de bovenstaande methode, maar neemt de serverzijde ID van een specifieke component en retourneert deze wanneer gevonden.

3. **`getComponentCount()`**: Retourneert het aantal kindcomponenten dat aanwezig is binnen de `Element`.

## JavaScript-functies aanroepen {#calling-javascript-functions}

De `Element`-component biedt twee API-methoden waarmee JavaScript-functies op HTML-elementen kunnen worden aangeroepen.

1. **`callJsFunction(String functionName, Object... arguments)`**: Deze methode neemt een functienaam als string en kan optioneel een of meer Objecten als parameters voor de functie nemen. Deze methode wordt synchroon uitgevoerd, wat betekent dat de **uitvoerende thread wordt geblokkeerd** totdat de JS-methode retourneert, wat resulteert in een round trip. De resultaten van de functie worden geretourneerd als een `Object`, dat kan worden gecast en gebruikt in Java.

2. **`callJsFunctionAsync(String functionName, Object... arguments)`**: Net als bij de vorige methode kan een functienaam en optionele argumenten voor de functie worden doorgegeven. Deze methode voert asynchroon uit en **blokkeert de uitvoerende thread niet**. Het retourneert een <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink>, waarmee verdere interactie met de functie en zijn payload mogelijk is.

### Parameters doorgeven {#passing-parameters}

Argumenten die naar deze methoden worden doorgegeven en worden gebruikt bij de uitvoering van JS-functies worden serieel gemaakt als een JSON-array. Er zijn twee opmerkelijke argumenttypes die als volgt worden behandeld:
- `this`: Het gebruik van het `this`-trefwoord geeft de methode een verwijzing naar de client-side versie van de aanroepende component.
- `Component`: Elke Java-componentinstantie die aan een van de JsFunction-methoden wordt doorgegeven, wordt vervangen door de client-side versie van de component.

:::warning Wachten op componentargumenten
Zowel synchrone als asynchrone functie-aanroepen wachten totdat de `Element` aan de DOM is toegevoegd voordat een functie wordt uitgevoerd, maar `callJsFunction()` wacht niet op het aanhechten van eventuele `component`-argumenten, wat kan leiden tot falen. Omgekeerd kan het aanroepen van `callJsFunctionAsync()` nooit worden voltooid als een componentargument nooit is gehecht.
:::

In de onderstaande demo roept het selecteren van **Focus zoeken** de native `focus()`-methode op het zoekinvoerveld aan met `callJsFunctionAsync()`. De resulterende <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink> wordt gebruikt om de oproep te bevestigen met een toast zodra de asynchrone functie is voltooid.

<ComponentDemo
path='/webforj/elementsearch'
files={[
  'src/main/java/com/webforj/samples/views/element/ElementSearchView.java',
  'src/main/resources/static/css/element/elementSearch.css',
]}
height='240px'
/>

## JavaScript uitvoeren {#executing-javascript}

Naast het aanroepen van benoemde functies kan een `Element` ruwe scripts uitvoeren die zijn beperkt tot dat element met `executeJs`, `executeJsAsync` en `executeJsVoidAsync`. Zie [JavaScript uitvoeren](./execute-javascript.md) voor deze methoden, hun synchrone en asynchrone gedrag en hoe geretourneerde waarden naar Java-types worden geconverteerd.
