---
sidebar_position: 5
title: Route Patterns
description: >-
  Define dynamic URL segments, optional parameters, wildcards, and regex
  constraints to match webforJ routes precisely.
_i18n_hash: a6c1267e034c1562652cc01d0f336640
---
**Routenmuster** werden verwendet, um zu definieren, wie URLs bestimmten Ansichten zugeordnet werden, einschließlich dynamischer und optionaler Segmente, regulärer Ausdrücke und Wildcards. Routenmuster ermöglichen es dem Framework, URLs abzugleichen, Parameter zu extrahieren und URLs dynamisch zu generieren. Sie spielen eine entscheidende Rolle bei der Strukturierung der Navigation einer Anwendung und der Komponentenanzeige basierend auf dem Standort des Browsers.

## Syntax von Routenmustern {#route-pattern-syntax}

Routenmuster in webforJ sind äußerst flexibel und unterstützen die folgenden Funktionen:

- **Benannte Parameter:** Durch `:paramName` gekennzeichnet, sind sie erforderlich, es sei denn, sie sind als optional markiert.
- **Optionale Parameter:** Durch `:paramName?` gekennzeichnet, können sie von der URL weggelassen werden.
- **Wildcard-Segmente:** Durch `*` dargestellt, erfassen sie alle verbleibenden Segmente der URL.
- **Einschränkungen durch reguläre Ausdrücke:** Einschränkungen können nur zu benannten Parametern hinzugefügt werden (zum Beispiel `:id<[0-9]+>`).

### Beispiel für Routenmusterdefinitionen {#example-of-route-pattern-definitions}

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
- `*` erfasst alle zusätzlichen Pfade über `named/:name`.

## Benannte Parameter {#named-parameters}

Benannte Parameter werden definiert, indem man einen Doppelpunkt `:` vor den Parameternamen im Muster setzt. Sie sind erforderlich, es sei denn, sie sind als optional markiert. Benannte Parameter können auch reguläre Ausdrücke [Einschränkungen](#regular-expression-constraints) haben, um die Werte zu validieren.

### Beispiel: {#example}

```java
@Route("product/:id")
public class ProductView extends Composite<Div> {
  // Logik der Komponente hier
}
```

Dieses Muster passt zu URLs wie `/product/123`, wobei `id` `123` ist.

## Optionale Parameter {#optional-parameters}

Optionale Parameter werden angezeigt, indem man ein `?` nach dem Parameternamen hinzufügt. Diese Segmente sind nicht erforderlich und können von der URL weggelassen werden.

### Beispiel: {#example-1}

```java
@Route("order/:id?<[0-9]+>")
public class OrderView extends Composite<Div> implements DidEnterObserver {

  @Override
  public void onDidEnter(DidEnterEvent event, ParametersBag parameters) {
    parameters.getInt("id").ifPresentOrElse(
      id -> console().log("Bestell-ID: " + id),
      () -> console().log("Keine Bestell-ID wurde angegeben")
    );
  }
}
```

Dieses Muster passt sowohl zu `/order/123`, um einen numerischen Wert einzuschließen, als auch zu `/order`, was das Weglassen eines numerischen Wertes beim Eingeben von `/order` ermöglicht.

## Einschränkungen durch reguläre Ausdrücke {#regular-expression-constraints}

Sie können Einschränkungen durch reguläre Ausdrücke auf Parameter anwenden, indem Sie sie innerhalb von spitzen Klammern `<>` hinzufügen. Dadurch können Sie strengere Übereinstimmungsregeln für Parameter angeben.

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

Dieses Muster passt nur zu Produktcodes im Format `ABC-1234`. Beispielsweise wird `/product/XYZ-5678` übereinstimmen, aber `/product/abc-5678` nicht.

## Wildcard-Segmente {#wildcard-segments}

Wildcards können verwendet werden, um gesamte Pfade zu erfassen, die einem bestimmten Routensegment folgen, dürfen jedoch nur als letztes Segment im Muster erscheinen und lösen alle nachfolgenden Werte in der URL auf. Zur besseren Lesbarkeit können Wildcard-Segmente benannt werden. Im Gegensatz zu benannten Parametern können Wildcard-Segmente jedoch keine Einschränkungen haben.

### Beispiel: {#example-3}

```java
@Route("files/:pathname*")
public class FileManagerView extends Composite<Div> implements DidEnterObserver {

  @Override
  public void onDidEnter(DidEnterEvent event, ParametersBag parameters) {
    parameters.get("pathname").ifPresentOrElse(
      pathname -> console().log("FileManagerView: " + pathname),
      () -> console().log("FileManagerView: Kein pathname-Parameter")
    );
  }
}
```

Dieses Muster passt zu jeder URL, die mit `/files` beginnt, und erfasst den Rest des Pfades als Wildcard.

## Routenvorrang {#route-priority}

Wenn mehrere Routen mit einer bestimmten URL übereinstimmen, bestimmt das Prioritätsattribut einer Route, welche Route zuerst ausgewählt wird. Dies ist besonders nützlich, wenn zwei oder mehr Routen in ihren Pfadmuster überlappen und Sie eine Möglichkeit benötigen, zu steuern, welche Priorität hat. Das Prioritätsattribut ist sowohl in den Annotationen `@Route` als auch `@RouteAlias` verfügbar.

### Wie das Prioritätssystem funktioniert {#how-the-priority-system-works}

Das Prioritätsattribut ermöglicht es dem Router, die Reihenfolge festzulegen, in der Routen bewertet werden, wenn mehrere Routen eine bestimmte URL übereinstimmen könnten. Routen werden basierend auf ihren Prioritätswerten sortiert, wobei höhere Priorität (niedrigere numerische Werte) zuerst abgeglichen werden. Dies stellt sicher, dass spezifischere Routen Vorrang vor allgemeineren haben.

Wenn zwei Routen die gleiche Priorität haben, löst der Router den Konflikt, indem er die Route auswählt, die zuerst registriert wurde. Dieser Mechanismus stellt sicher, dass die richtige Route ausgewählt wird, auch wenn mehrere Routen in ihren URL-Mustern überlappen.

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

So hilft das Prioritätssystem, Konflikte aufzulösen:

- **`ProductCategoryView`** passt zu URLs wie `/products/electronics`.
- **`ProductView`** passt zu spezifischeren URLs wie `/products/electronics/123`, wobei `123` die Produkt-ID ist.

In diesem Fall könnten beide Routen die URL `/products/electronics` übereinstimmen. Da jedoch `ProductCategoryView` eine höhere Priorität (Priorität = 9) hat, wird sie zuerst abgeglichen, wenn keine `productId` in der URL vorhanden ist. Für URLs wie `/products/electronics/123` wird `ProductView` aufgrund der Präsenz des Parameters `productId` abgeglichen.
