---
sidebar_position: 5
title: Elements
description: >-
  Integrate raw HTML tags and custom web components in webforJ using the Element
  class to add children, set content, and call JavaScript functions.
slug: element
_i18n_hash: dff3b1c4df821aad3c4c7a4c66cfff65
---
<JavadocLink type="foundation" location="com/webforj/component/element/Element" top='true'/>

webforJ-ontwikkelaars hebben de optie om niet alleen te kiezen uit de rijke bibliotheek van beschikbare componenten, maar ook om componenten van elders te integreren. Om dit te vergemakkelijken kan de `Element`-component worden gebruikt om de integratie van alles, van eenvoudige HTML-elementen tot meer complexe aangepaste webcomponenten, te vereenvoudigen.

:::important
De `Element`-component kan niet worden uitgebreid en is geen basiscomponent voor alle componenten binnen webforJ. Voor meer informatie over de componenthiërarchie van webforJ, lees [dit artikel](../architecture/controls-components.md).
:::

<ComponentDemo
path='/webforj/elementmeter'
files={[
  'src/main/java/com.webforj/samples/views/element/ElementMeterView.java'
]}
height='250px'
/>

## Evenementen toevoegen {#adding-events}

Om gebruik te maken van evenementen die mogelijk bij uw element horen, kunt u de `addEventListener`-methoden van de `Element`-component gebruiken. Het toevoegen van een evenement vereist minimaal het type/naam van het evenement dat de component verwacht, en een listener die aan het evenement moet worden toegevoegd.

Er zijn ook aanvullende opties om de evenementen verder te personaliseren door gebruik te maken van de configuraties voor Evenementopties.

<ComponentDemo
path='/webforj/elementtaginput'
files={[
  'src/main/java/com.webforj/samples/views/element/ElementTagInputView.java',
  'src/main/frontend/css/element/elementtaginput.css',
]}
height='250px'
/>

## Componentinteractie {#component-interaction}

De `Element`-component fungeert als een container voor andere componenten. Het biedt een manier om informatie voor kindcomponenten te organiseren en op te halen, en biedt een duidelijke set functies om deze kindcomponenten toe te voegen of te verwijderen indien nodig.

### Kindcomponenten toevoegen {#adding-child-components}

De `Element`-component ondersteunt de samenstelling van kindcomponenten. Ontwikkelaars kunnen complexe UI-structuren organiseren en beheren door componenten als kinderen aan de `Element` toe te voegen. Er zijn drie methoden om inhoud binnen een `Element` in te stellen:

1. **`add(Component... components)`**: Deze methode stelt één of meerdere componenten in staat om te worden toegevoegd aan een optionele `String` die een opgegeven slot aanduidt wanneer deze met een Web Component wordt gebruikt. Het weglaten van het slot voegt de component toe tussen de HTML-tags.

2. **`setHtml(String html)`**: Deze methode neemt de `String` die aan de methode wordt doorgegeven en injecteert deze als HTML binnen de component. Afhankelijk van de `Element` kan dit op verschillende manieren worden weergegeven.

3. **`setText(String text)`**: Deze methode gedraagt zich vergelijkbaar met de `setHtml()`-methode, maar injecteert letterlijke tekst in de `Element`.

<ComponentDemo
path='/webforj/elementfigure'
files={[
  'src/main/java/com.webforj/samples/views/element/ElementFigureView.java',
  'src/main/frontend/css/element/elementfigure.css',
]}
height='250px'
/>

:::warning Inhoud vervangen
Het aanroepen van `setHtml()` of `setText()` vervangt de inhoud die momenteel tussen de openings- en sluit-tags van het element staat.
:::

### Componenten verwijderen {#removing-components}

Naast het toevoegen van componenten aan een `Element`, zijn de volgende methoden geïmplementeerd voor het verwijderen van verschillende kindcomponenten:

1. **`remove(Component... components)`**: Deze methode neemt een of meerdere componenten en verwijdert deze als kindcomponenten.

2. **`removeAll()`**: Deze methode verwijdert alle kindcomponenten van de `Element`.

### Componenten benaderen {#accessing-components}

Om toegang te krijgen tot de verschillende kindcomponenten binnen een `Element`, of informatie over deze componenten, zijn de volgende methoden beschikbaar:

1. **`getComponents()`**: Deze methode retourneert een Java `List` van alle kinderen van de `Element`.

2. **`getComponents(String id)`**: Deze methode is vergelijkbaar met de bovenstaande methode, maar neemt de server-side ID van een specifieke component en retourneert deze wanneer deze is gevonden.

3. **`getComponentCount()`**: Retourneert het aantal kindcomponenten dat aanwezig is binnen de `Element`.

## JavaScript-functies aanroepen {#calling-javascript-functions}

De `Element`-component biedt twee API-methoden waarmee JavaScript-functies op HTML-elementen kunnen worden aangeroepen.

1. **`callJsFunction(String functionName, Object... arguments)`**: Deze methode neemt een functienaam als een string en neemt optioneel een of meer Objecten als parameters voor de functie. Deze methode wordt synchroon uitgevoerd, wat betekent dat de **uitvoerende thread wordt geblokkeerd** totdat de JS-methode retourneert, wat resulteert in een round trip. De resultaten van de functie worden teruggegeven als een `Object`, dat kan worden gecast en in Java kan worden gebruikt.

2. **`callJsFunctionAsync(String functionName, Object... arguments)`**: Zoals bij de vorige methode kan een functienaam en optionele argumenten voor de functie worden doorgegeven. Deze methode wordt asynchroon uitgevoerd en **blokkeert de uitvoerende thread niet**. Het retourneert een <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink>, waarmee verdere interactie met de functie en de bijbehorende payload mogelijk is.

### Parameters doorgeven {#passing-parameters}

Argumenten die aan deze methoden worden doorgegeven en die worden gebruikt bij de uitvoering van JS-functies, worden geserialiseerd als een JSON-array. Er zijn twee opmerkelijke argumenttypes die als volgt worden behandeld:
- `this`: Het gebruik van het sleutelwoord `this` geeft de methode een referentie naar de client-side versie van de aanroepende component.
- `Component`: Alle Java-componentinstellingen die in een van de JsFunction-methoden zijn doorgegeven, worden vervangen door de client-side versie van de component.

:::warning Wachten op componentargumenten
Zowel synchronisatie- als asynchrone functie-aanroepen wachten totdat de `Element` aan de DOM is toegevoegd voordat een functie wordt uitgevoerd, maar `callJsFunction()` wacht niet op eventuele `component`-argumenten om aan te sluiten, wat kan resulteren in falen. Daarentegen kan het aanroepen van `callJsFunctionAsync()` nooit compleet zijn als een componentargument nooit wordt aangesloten.
:::

In de onderstaande demo roept het selecteren van **Focus search** de native `focus()`-methode aan op het zoekinvoerelement met `callJsFunctionAsync()`. De resulterende <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink> wordt gebruikt om de oproep te bevestigen met een toast zodra de asynchrone functie is voltooid.

<ComponentDemo
path='/webforj/elementsearch'
files={[
  'src/main/java/com.webforj/samples/views/element/ElementSearchView.java',
  'src/main/frontend/css/element/elementsearch.css',
]}
height='250px'
/>

## JavaScript uitvoeren {#executing-javascript}

Naast het aanroepen van benoemde functies, kan een `Element` raw scripts uitvoeren die zijn beperkt tot dat element met `executeJs`, `executeJsAsync` en `executeJsVoidAsync`. Zie [JavaScript uitvoeren](./execute-javascript.md) voor deze methoden, hun synchrone en asynchrone gedrag, en hoe teruggegeven waarden worden omgezet naar Java-typen.
