---
sidebar_position: 5
title: Route Patterns
_i18n_hash: d18952e5072af2c542c1459e3f65d787
---
**Routenmuster** werden verwendet, um zu definieren, wie URLs bestimmten Ansichten zugeordnet werden, einschließlich dynamischer und optionaler Segmente, regulärer Ausdrücke und Platzhalter. Routenmuster ermöglichen es dem Framework, URLs abzugleichen, Parameter zu extrahieren und URLs dynamisch zu generieren. Sie spielen eine entscheidende Rolle bei der Strukturierung der Navigation einer App und der Darstellung von Komponenten basierend auf dem Standort des Browsers.

## Routenmuster-Syntax {#route-pattern-syntax}

Routenmuster in webforJ sind äußerst flexibel und unterstützen die folgenden Funktionen:

- **Benannte Parameter:** Dargestellt durch `:paramName`, sie sind erforderlich, es sei denn, sie sind als optional gekennzeichnet.
- **Optionale Parameter:** Dargestellt durch `:paramName?`, sie können aus der URL weggelassen werden.
- **Wildcard-Segmente:** Dargestellt durch `*`, sie erfassen alle verbleibenden Segmente der URL.
- **Reguläre Ausdrucksbedingungen:** Bedingungen können nur für benannte Parameter hinzugefügt werden (zum Beispiel `:id<[0-9]+>`).

### Beispiel für Routenmusterdaten {#example-of-route-pattern-definitions}

```java
@Route("customer/:id<[0-9]+>/named/:name/*")
public class CustomerView extends Composite<Div> implements DidEnterObserver {

  @Override
  public void onDidEnter(DidEnterEvent event, ParametersBag parameters) {
    int id = parameters.getInt("id").orElse(0);
    String name = parameters.getAlpha("name").orElse("Unbekannt");
    String extra = parameters.getAlpha("*").orElse("");

    String result =
        "Kunden-ID: " + id + "-" +
        "Name: " + name + "-" +
        "*: " + extra;

    console().log(result);
  }
}
```

In diesem Beispiel:

- `:id<[0-9]+>` erfasst eine numerische Kunden-ID.
- `:name` erfasst einen Namen.
- `*` erfasst alle zusätzlichen Pfadsegmente über `named/:name` hinaus.

## Benannte Parameter {#named-parameters}

Benannte Parameter werden definiert, indem ein Doppelpunkt `:` vor den Parameternamen im Muster gesetzt wird. Sie sind erforderlich, es sei denn, sie sind als optional gekennzeichnet. Benannte Parameter können auch reguläre Ausdrucksbedingungen [constraints](#regular-expression-constraints) haben, um die Werte zu validieren.

### Beispiel: {#example}

```java
@Route("product/:id")
public class ProductView extends Composite<Div> {
  // Komponentenlogik hier
}
```

Dieses Muster entspricht URLs wie `/product/123`, wo `id` `123` ist.

## Optionale Parameter {#optional-parameters}

Optionale Parameter werden angezeigt, indem ein `?` nach dem Parameternamen hinzugefügt wird. Diese Segmente sind nicht erforderlich und können aus der URL weggelassen werden.

### Beispiel: {#example-1}

```java
@Route("order/:id?<[0-9]+>")
public class OrderView extends Composite<Div> implements DidEnterObserver {

  @Override
  public void onDidEnter(DidEnterEvent event, ParametersBag parameters) {
    parameters.getInt("id").ifPresentOrElse(
      id -> console().log("Bestell-ID: " + id),
      () -> console().log("Es wurde keine Bestell-ID angegeben")
    );
  }
}
```

Dieses Muster entspricht sowohl `/order/123`, um einen numerischen Wert einzuschließen, als auch `/order`, was das Weglassen eines numerischen Werts ermöglicht, wenn `/order` eingegeben wird.

## Reguläre Ausdrucksbedingungen {#regular-expression-constraints}

Sie können reguläre Ausdrucksbedingungen auf Parameter anwenden, indem Sie sie in spitze Klammern `<>` setzen. Dies ermöglicht es Ihnen, strengere Übereinstimmungsregeln für Parameter festzulegen.

### Beispiel: {#example-2}

```java
@Route("product/:code<[A-Z]{3}-[0-9]{4}>")
public class ProductView extends Composite<FlexLayout> implements DidEnterObserver {

  @Override
  public void onDidEnter(DidEnterEvent event, ParametersBag parameters) {
    parameters.get("code").ifPresentOrElse(
      code -> console().log("Produktcode: " + code),
      () -> console().error("Produktcode nicht gefunden"));
  }
}
```

Dieses Muster entspricht nur Produktcodes im Format `ABC-1234`. Beispielsweise wird `/product/XYZ-5678` übereinstimmen, aber `/product/abc-5678` nicht.

## Wildcard-Segmente {#wildcard-segments}

Wildcard-Segmente können verwendet werden, um gesamte Pfade zu erfassen, die einem bestimmten Routen-Segment folgen, dürfen jedoch nur als letztes Segment im Muster erscheinen, das alle nachfolgenden Werte in der URL auflöst. Zur besseren Lesbarkeit können Wildcard-Segmente benannt werden. Im Gegensatz zu benannten Parametern können Wildcard-Segmente jedoch keine Bedingungen haben.

### Beispiel: {#example-3}

```java
@Route("files/:pathname*")
public class FileManagerView extends Composite<Div> implements DidEnterObserver {

  @Override
  public void onDidEnter(DidEnterEvent event, ParametersBag parameters) {
    parameters.get("pathname").ifPresentOrElse(
      pathname -> console().log("FileManagerView: " + pathname),
      () -> console().log("FileManagerView: Kein Pfadnamenparameter")
    );
  }
}
```

Dieses Muster entspricht jeder URL, die mit `/files` beginnt und den Rest des Pfades als Wildcard erfasst.

## Routenpriorität {#route-priority}

Wenn mehrere Routen mit einer bestimmten URL übereinstimmen, bestimmt das Prioritätsattribut einer Route, welche Route zuerst ausgewählt wird. Dies ist besonders nützlich, wenn zwei oder mehr Routen in ihren Pfadmuster überlappen und Sie eine Möglichkeit benötigen, zu steuern, welche Priorität hat. Das Prioritätsattribut ist in den Annotations `@Route` und `@RouteAlias` verfügbar.

### Funktionsweise des Prioritätssystems {#how-the-priority-system-works}

Das Prioritätsattribut ermöglicht es dem Router, die Reihenfolge festzulegen, in der Routen bewertet werden, wenn mehrere Routen mit einer bestimmten URL übereinstimmen könnten. Routen werden basierend auf ihren Prioritätswerten sortiert, wobei höhere Priorität (niedrigere numerische Werte) zuerst übereinstimmt. Dies stellt sicher, dass spezifischere Routen Vorrang vor allgemeineren haben.

Wenn zwei Routen dieselbe Priorität haben, löst der Router den Konflikt, indem er die Route auswählt, die zuerst registriert wurde. Dieser Mechanismus stellt sicher, dass die korrekte Route ausgewählt wird, selbst wenn mehrere Routen in ihren URL-Mustern überlappen.

:::info Standardpriorität  
Standardmäßig wird allen Routen eine Priorität von `10` zugewiesen.  
:::  

### Beispiel: Konfliktierende Routen {#example-conflicting-routes}

Betrachten Sie ein Szenario, in dem zwei Routen ähnliche URL-Muster übereinstimmen:

```java
@Route(value = "products/:category", priority = 9)
public class ProductCategoryView extends Composite<Div> implements DidEnterObserver {
  @Override
  public void onDidEnter(DidEnterEvent event, ParametersBag parameters) {
    String category = parameters.get("category").orElse("unbekannt");
    console().log("Kategorie anzeigen: " + category);
  }
}

@Route(value = "products/:category/:productId?<[0-9]+>")
public class ProductView extends Composite<Div> implements DidEnterObserver {
  @Override
  public void onDidEnter(DidEnterEvent event, ParametersBag parameters) {
    String productId = parameters.get("productId").orElse("unbekannt");
    console().log("Produkt anzeigen: " + productId);
  }
}
```

So hilft das Prioritätssystem bei der Lösung von Konflikten:

- **`ProductCategoryView`** entspricht URLs wie `/products/electronics`.
- **`ProductView`** entspricht spezifischeren URLs wie `/products/electronics/123`, wo `123` die Produkt-ID ist.

In diesem Fall könnten beide Routen mit der URL `/products/electronics` übereinstimmen. Da jedoch `ProductCategoryView` eine höhere Priorität hat (Priorität = 9), wird sie zuerst zugewiesen, wenn in der URL keine `productId` vorhanden ist. Für URLs wie `/products/electronics/123` wird `ProductView` aufgrund der Anwesenheit des Parameters `productId` zugewiesen.
