---
sidebar_position: 5
title: Elements
description: >-
  Integrate raw HTML tags and custom web components in webforJ using the Element
  class to add children, set content, and invoke JavaScript.
slug: element
_i18n_hash: 2af99ca4f1e5c8c2f7c31b3d7f647b41
---
<JavadocLink type="foundation" location="com/webforj/component/element/Element" top='true'/>

webforJ-ontwikkelaars hebben de optie om niet alleen te kiezen uit de rijke bibliotheek van componenten, maar ook om componenten van elders te integreren. Om dit te vergemakkelijken, kan de `Element`-component worden gebruikt om de integratie van alles, van eenvoudige HTML-elementen tot meer complexe aangepaste webcomponenten, te vereenvoudigen.

:::important
De `Element`-component kan niet worden uitgebreid en is niet de basisonderdeel voor alle componenten binnen webforJ. Om meer te lezen over de componenthiërarchie van webforJ, lees [dit artikel](../architecture/controls-components.md).
:::

<ComponentDemo
path='/webforj/elementinputdemo'
files={[
  'src/main/java/com/webforj/samples/views/element/ElementInputDemoView.java',
  'src/main/frontend/css/element/elementInput.css',
]}
/>

## Evenementen toevoegen {#adding-events}

Om gebruik te maken van evenementen die mogelijk bij uw element komen, kunt u de methoden `addEventListener` van de `Element`-component gebruiken. Het toevoegen van een evenement vereist ten minste het type/naam van het evenement dat de component verwacht, en een luisteraar die aan het evenement moet worden toegevoegd.

Er zijn ook extra opties om evenementen verder te personaliseren met behulp van de configuraties van de Evenementopties.

<ComponentDemo
path='/webforj/elementinputevent'
files={[
  'src/main/java/com/webforj/samples/views/element/ElementInputEventView.java',
  'src/main/frontend/css/element/elementInputEvent.css',
]}
height='240px'
/>

## Componentinteractie {#component-interaction}

De `Element`-component fungeert als een container voor andere componenten. Het biedt een manier om informatie voor kindcomponenten te organiseren en op te halen, en biedt een duidelijke set functies om deze kindcomponenten naar behoefte toe te voegen of te verwijderen.

### Kindcomponenten toevoegen {#adding-child-components}

De `Element`-component ondersteunt de samenstelling van kindcomponenten. Ontwikkelaars kunnen complexe UI-structuren organiseren en beheren door componenten als kinderen aan de `Element` toe te voegen. Er zijn drie methoden om inhoud binnen een `Element` in te stellen:

1. **`add(Component... components)`**: Deze methode stelt een of meerdere componenten in staat om te worden toegevoegd aan een optionele `String` die een specifieke ruimte aangeeft wanneer deze wordt gebruikt met een Web Component. Het weglaten van de ruimte voegt de component tussen de HTML-tags toe.

2. **`setText(String text)`**: Deze methode gedraagt zich vergelijkbaar met de `setHtml()`-methode, maar injecteert letterlijke tekst in de `Element`.

  ```java
  // Wordt weergegeven als de letterlijke tekens "<b>Status: gereed</b>"
  element.setText("<b>Status: gereed</b>");
  ```

  :::note Gebruik van de `<html>`-tag
  Eerdere versies van webforJ behandelden een waarde die in `<html>` is gewikkeld en aan `setText()` is doorgegeven als HTML. Dit gedrag is verouderd en zal worden verwijderd in webforJ 27.00.

  De eerste keer dat een waarde die in `<html>` is gewikkeld `setText()` bereikt, wordt een waarschuwing gelogd die de component en de aanroepplaats naamgeeft, zodat de aanroep naar `setHtml()` kan worden verplaatst.

  Om vooraf de standaard van webforJ 27.00 aan te nemen, stelt u `webforj.legacyHtmlInText` in op `false`. In een Spring-app wordt dezelfde waarde ingesteld via `webforj.legacy-html-in-text`.

  ```java
  // webforj.legacyHtmlInText = true (standaard)
  element.setText("<html><b>Status: gereed</b></html>"); // rendert vet

  // webforj.legacyHtmlInText = false
  element.setText("<html><b>Status: gereed</b></html>"); // toont de tekens <b>Status: gereed</b>
  ```
  :::

3. **`setHtml(String html)`**: Deze methode neemt de `String` die aan de methode is doorgegeven en injecteert deze als HTML binnen de component. Afhankelijk van de `Element` kan dit op verschillende manieren worden gerenderd.

  :::danger Cross-site Scripting (XSS)
  Als voorzorg tegen [cross-site scripting (XSS)-aanvallen](/docs/security/application-security/common-threats#cross-site-scripting-xss), gebruik `setHtml()` alleen met inhoud die u direct controleert.
  :::

<ComponentDemo
path='/webforj/elementinputtext'
files={[
  'src/main/java/com/webforj/samples/views/element/ElementInputTextView.java',
  'src/main/frontend/css/element/elementInput.css',
]}
height='175px'
/>

:::tip
Het aanroepen van `setHtml()` of `setText()` vervangt de inhoud die momenteel tussen de openings- en sluitingstags van het element staat.
:::


### Componenten verwijderen {#removing-components}

Naast het toevoegen van componenten aan een `Element`, zijn de volgende methoden geïmplementeerd voor het verwijderen van verschillende kindcomponenten:

1. **`remove(Component... components)`**: Deze methode neemt een of meer componenten en verwijdert deze als kindcomponenten.

2. **`removeAll()`**: Deze methode verwijdert alle kindcomponenten uit de `Element`.

### Componenten toegankelijk maken {#accessing-components}

Om toegang te krijgen tot de verschillende kindcomponenten die aanwezig zijn binnen een `Element`, of informatie over deze componenten, zijn de volgende methoden beschikbaar:

1. **`getComponents()`**: Deze methode retourneert een Java `List` van alle kinderen van de `Element`.

2. **`getComponents(String id)`**: Deze methode is vergelijkbaar met de bovenstaande methode, maar neemt de serverzijde ID van een specifieke component en retourneert deze wanneer deze is gevonden.

3. **`getComponentCount()`**: Retourneert het aantal kindcomponenten dat aanwezig is binnen de `Element`.

## JavaScript-functies aanroepen {#calling-javascript-functions}

De `Element`-component biedt twee API-methoden waarmee JavaScript-functies op HTML-elementen kunnen worden aangeroepen.

1. **`callJsFunction(String functionName, Object... arguments)`**: Deze methode neemt een functienaam als een string en neemt optioneel een of meer Objecten als parameters voor de functie. Deze methode wordt synchronisch uitgevoerd, wat betekent dat de **uitvoerende thread wordt geblokkeerd** totdat de JS-methode retourneert, en resulteert in een roundtrip. De resultaten van de functie worden geretourneerd als een `Object`, dat kan worden gecast en in Java kan worden gebruikt.

2. **`callJsFunctionAsync(String functionName, Object... arguments)`**: Net als bij de vorige methode kan een functienaam en optionele argumenten voor de functie worden doorgegeven. Deze methode voert asynchroon uit en **blokkeert de uitvoerende thread niet**. Het retourneert een <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink>, die verdere interactie met de functie en de payload mogelijk maakt.

### Parameters doorgeven {#passing-parameters}

Argumenten die naar deze methoden worden doorgegeven en worden gebruikt bij de uitvoering van JS-functies worden geserialiseerd als een JSON-array. Er zijn twee opmerkelijke argumenttypen die als volgt worden behandeld:
- `this`: Het gebruik van het sleutelwoord `this` geeft de methode een verwijzing naar de clientzijde versie van de oproepende component.
- `Component`: Alle Java-componentinstanties die in een van de JsFunction-methoden worden doorgegeven, worden vervangen door de clientzijde versie van de component.

:::info
Zowel synchronische als asynchronische functieaanroep wachten totdat de `Element` aan de DOM is toegevoegd voordat ze een functie uitvoeren, maar `callJsFunction()` wacht niet op enige `component`-argumenten om aan te sluiten, wat kan resulteren in een fout. Omgekeerd kan het aanroepen van `callJsFunctionAsync()` nooit voltooid worden als een componentargument nooit is aangesloten.
:::

In de onderstaande demo wordt een evenement toegevoegd aan een HTML `Button`. Dit evenement wordt vervolgens programmatisch geactiveerd door de methode `callJsFunctionAsync()` aan te roepen. De resulterende <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink> wordt vervolgens gebruikt om een ander berichtvenster te creëren zodra de asynchrone functie is voltooid.

<ComponentDemo
path='/webforj/elementinputfunction'
files={[
  'src/main/java/com/webforj/samples/views/element/ElementInputFunctionView.java',
  'src/main/frontend/css/element/elementInput.css',
]}
height='240px'
/>

## JavaScript uitvoeren {#executing-javascript}

Naast het uitvoeren van JavaScript vanuit het applicatieniveau is het ook mogelijk om JavaScript vanuit het `Element`-niveau uit te voeren. Het uitvoeren van deze uitvoering op het `Element`-niveau stelt de context van het HTML-element in staat om te worden opgenomen in de uitvoering. Dit is een krachtig hulpmiddel dat als conduit voor de ontwikkelaar fungeert voor interactieve mogelijkheden met client-side omgevingen.

Net als bij functie-uitvoering kan het uitvoeren van JavaScript zowel synchroon als asynchroon worden gedaan met de volgende methoden:

1. **`executeJs(String script)`**: Deze methode neemt een `String`, die als JavaScript-code in de client zal worden uitgevoerd. Dit script wordt synchronisch uitgevoerd, wat betekent dat de **uitvoerende thread wordt geblokkeerd** totdat de JS-uitvoering retourneert, en resulteert in een roundtrip. De resultaten van de functie worden geretourneerd als een `Object`, dat kan worden gecast en in Java kan worden gebruikt.

2. **`executeJsAsync(String script)`**: Net als bij de vorige methode zal een doorgegeven `String`-parameter worden uitgevoerd als JavaScript-code op de client. Deze methode wordt asynchroon uitgevoerd en **blokkeert de uitvoerende thread niet**. Het retourneert een <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink>, die verdere interactie met de functie en de payload mogelijk maakt.

:::tip
Deze methoden hebben toegang tot het sleutelwoord `component`, waarmee de JavaScript-code toegang heeft tot de clientzijde instantie van de component die de JavaScript uitvoert.
:::
