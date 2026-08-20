---
sidebar_position: 11
title: Execute JavaScript
sidebar_class_name: new-content
description: >-
  Run client-side JavaScript from Java with executeJs, executeJsAsync, and
  executeJsVoidAsync at the app or element level.
slug: execute-javascript
_i18n_hash: c1d5b030c6f39ac6c83afc05ca4bb398
---
import JavadocLink from '@site/src/components/DocsTools/JavadocLink';

webforJ draait op de server, maar er zijn momenten dat je de client moet bereiken: scrollen in het venster, een veld focussen, een browserwaarde lezen of een methode aanroepen op een webcomponent. De <JavadocLink type="foundation" location="com/webforj/concern/HasJsExecution" code='true'>HasJsExecution</JavadocLink> interface biedt die brug. Het is op twee niveaus geïmplementeerd:

- De [`Page`](#app-level-execution) draait script in de context van de hele pagina.
- Een [`Element`](#element-level-execution) draait script dat is beperkt tot een enkel clientelement.

Beide exposeren dezelfde drie methoden, dus zodra je de vormen hieronder kent, lezen ze hetzelfde, of je ze nu aanroept op `Page` of een `Element`.

## Uitvoeringsmethoden {#execution-methods}

Elk niveau biedt een synchrone methode en twee asynchrone. Het verschil is of de aanroepende thread wacht en of er een resultaat terugkomt.

1. **`executeJs(String script)`**: voert het script synchronisch uit. De **uitvoerende thread is geblokkeerd** totdat de client terugkomt, wat één server-client round trip kost. Het resultaat komt terug als een `Object` dat je kunt casten en gebruiken in Java.

2. **`executeJsAsync(String script)`**: voert het script asynchroon uit en **blokkeert de uitvoerende thread niet**. Het retourneert een <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink> dat voltooid wordt wanneer het script klaar is, zodat je later op het resultaat kunt reageren.

3. **`executeJsVoidAsync(String script)`**: voert het script asynchroon uit en retourneert niets naar de server. Gebruik het voor fire-and-forget werk waarbij je het resultaat niet nodig hebt. Beschikbaar sinds `24.11`.

:::tip Een methode kiezen
Reik standaard naar `executeJsVoidAsync` wanneer je alleen een bijeffect op de client veroorzaakt (scrollen, focussen, een methode aanroepen). Gebruik `executeJsAsync` wanneer je de waarde nodig hebt, maar niet-blokkerend wilt blijven, en reserveer de synchrone `executeJs` voor het zeldzame geval waarin je het resultaat moet hebben voordat de volgende regel Java draait, omdat het de thread voor een volledige round trip vasthoudt.
:::

### Resultaten lezen {#reading-results}

Wanneer een script een waarde retourneert, converteert webforJ deze naar het bijbehorende Java-type:

| JavaScript-waarde       | Java-type                             |
| ----------------------- | ------------------------------------- |
| number                  | `Integer`, `Long` of `Double`        |
| string                  | `String`                              |
| boolean                 | `Boolean`                             |
| `null` of `undefined`   | `null`                                |
| elk ander type          | zijn stringrepresentatie              |

Lees waarden met `executeJsAsync`, wat de conversie betrouwbaar toepast. Een geretourneerd nummer kan aankomen als `Integer`, `Long` of `Double`, dus lees het via `Number`:

```java
Page.getCurrent()
    .executeJsAsync("return window.innerWidth;")
    .thenAccept(result -> {
      int width = ((Number) result).intValue();
      // gebruik width
    });
```

:::warning Geef de async-vorm de voorkeur wanneer je de waarde nodig hebt
De synchrone `executeJs` retourneert `null` wanneer de uitvoeringscontext niet gereed is, bijvoorbeeld wanneer het wordt aangeroepen voordat de component is gekoppeld. Gebruik `executeJsAsync` wanneer je afhankelijk bent van de geretourneerde waarde, en vermijd het casten van een synchrone resultaat naar een specifiek type.
:::

## App-niveau uitvoering {#app-level-execution}

Roep de methoden aan op <JavadocLink type="foundation" location="com/webforj/Page" code='true'>Page</JavadocLink> wanneer het script betrekking heeft op de pagina als geheel in plaats van één component. Verkrijg de huidige pagina met `Page.getCurrent()`.

Een veelvoorkomend geval is terugscrollen naar de bovenkant na een routerwijziging. Er hoeft niets terug te komen, dus `executeJsVoidAsync` past:

```java
Page.getCurrent().executeJsVoidAsync(
    "window.scrollTo({ top: 0, behavior: 'smooth' });");
```

Wanneer je een clientwaarde op de server nodig hebt, lees deze dan asynchroon en reageer op het resultaat wanneer het aankomt:

```java
Page.getCurrent()
    .executeJsAsync("return navigator.language;")
    .thenAccept(language -> {
      // language is de browser locale, bijvoorbeeld "en-US"
      applyLocale(String.valueOf(language));
    });
```

:::info Pagina versus element scope
Gebruik [element-niveau uitvoering](#element-level-execution) wanneer het script moet handelen op een specifiek clientelement in plaats van de pagina als geheel.
:::

In de demo hieronder, het selecteren van **Kopieer link** voert een script uit via `Page` met `executeJsVoidAsync` om de uitnodigingslink naar het klembord van de bezoeker te schrijven. Kopiëren is een bijeffect met niets om terug te geven, dus de fire-and-forget methode is de juiste keuze.

<ComponentDemo
path='/webforj/executejavascript'
files={[
  'src/main/java/com/webforj/samples/views/javascript/ExecuteJavaScriptView.java',
]}
height='260px'
/>

## Element-niveau uitvoering {#element-level-execution}

Het aanroepen van dezelfde methoden op een <JavadocLink type="foundation" location="com/webforj/component/element/Element" code='true'>Element</JavadocLink> beperkt het script tot dat element in plaats van de pagina. De retourwaarden en het synchrone en asynchrone gedrag komen overeen met de voorafgaande pagina-niveau methoden.

Elementscripts worden in de wachtrij geplaatst totdat het element aan de DOM is gekoppeld, en worden dan uitgevoerd, zodat je ze tijdens de opzet kunt aanroepen zonder te wachten op de koppeling zelf.

### Een functie op een element aanroepen {#calling-a-function}

Wanneer je een benoemde client-side functie wilt aanroepen in plaats van een scriptstring uit te voeren, biedt `Element` een parallel set van methoden. In plaats van een script geef je de functienaam en de argumenten door, die webforJ serialiseert en doorgeeft. Twee argumenttypes worden speciaal behandeld: `this` wordt vervangen door het clientelement, en elk `Component` argument wordt vervangen door zijn clientinstantie zodra deze is gekoppeld.

Deze spiegelen de uitvoermethoden, waarbij het enige verschil is of de thread wacht en of er een resultaat retourneert:

1. **`callJsFunction(String name, Object... args)`**: roept de functie synchronisch aan en retourneert het resultaat als een `Object`. De uitvoerende thread blokkeert voor één round trip.

2. **`callJsFunctionAsync(String name, Object... args)`**: roept de functie asynchroon aan zonder te blokkeren, en retourneert een `PendingResult` dat voltooid wordt met het resultaat van de functie. Beschikbaar sinds `24.11`.

3. **`callJsFunctionVoidAsync(String name, Object... args)`**: roept de functie asynchroon aan en retourneert niets naar de server. Gebruik het voor fire-and-forget aanroepen waarbij je de retourwaarde niet nodig hebt. Beschikbaar sinds `24.11`.

Omdat de aanroep wacht op elke `Component` argument om te koppelen voordat deze wordt uitgevoerd, voltooit een aanroep die een component doorgeeft die nooit koppelt nooit.

```java
// Focus een invoerveld van een webcomponent door de client-side methode aan te roepen
searchElement.callJsFunctionVoidAsync("focus");
```
