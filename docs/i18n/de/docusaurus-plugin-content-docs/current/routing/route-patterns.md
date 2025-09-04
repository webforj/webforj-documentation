---
sidebar_position: 5
title: Route Patterns
_i18n_hash: 2f1668e34197bb2f4bb6c5b3ec6e87e5
---
**Routenmuster** werden verwendet, um zu definieren, wie URLs bestimmten Ansichten zugeordnet werden, einschließlich dynamischer und optionaler Segmente, regulärer Ausdrücke und Wildcards. Routenmuster ermöglichen es dem Framework, URLs abzugleichen, Parameter zu extrahieren und URLs dynamisch zu generieren. Sie spielen eine entscheidende Rolle bei der Strukturierung der Navigation und der Komponentendarstellung einer App basierend auf dem Standort des Browsers.

## Syntax der Routenmuster {#route-pattern-syntax}

Routenmuster in webforJ sind äußerst flexibel und unterstützen die folgenden Funktionen:

- **Benannte Parameter:** Gekennzeichnet durch `:paramName`, sind sie erforderlich, es sei denn, sie sind als optional gekennzeichnet.
- **Optionale Parameter:** Gekennzeichnet durch `:paramName?`, können sie in der URL weggelassen werden.
- **Wildcards:** Dargestellt durch `*`, erfassen sie alle verbleibenden Segmente der URL.
- **Einschränkungen für reguläre Ausdrücke:** Einschränkungen können nur für benannte Parameter hinzugefügt werden (zum Beispiel `:id<[0-9]+>`).

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
- `*` erfasst zusätzliche Pfadsegmente über `named/:name` hinaus.

## Benannte Parameter {#named-parameters}

Benannte Parameter werden definiert, indem einem Doppelpunkt `:` der Parametername im Muster vorangestellt wird. Sie sind erforderlich, es sei denn, sie sind als optional gekennzeichnet. Benannte Parameter können auch reguläre Ausdrucks-[Einschränkungen](#regular-expression-constraints) haben, um die Werte zu validieren.

### Beispiel: {#example}

```java
@Route("product/:id")
public class ProductView extends Composite<Div> {
  // Komponentenlogik hier
}
```

Dieses Muster entspricht URLs wie `/product/123`, wobei `id` `123` ist.

## Optionale Parameter {#optional-parameters}

Optionale Parameter werden angezeigt, indem ein `?` nach dem Parameternamen hinzugefügt wird. Diese Segmente sind nicht erforderlich und können in der URL weggelassen werden.

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

Dieses Muster entspricht sowohl `/order/123`, um einen numerischen Wert einzuschließen, als auch `/order`, wodurch das Weglassen eines numerischen Wertes beim Eingeben von `/order` ermöglicht wird.

## Einschränkungen für reguläre Ausdrücke {#regular-expression-constraints}

Sie können reguläre Ausdruckseinschränkungen auf Parameter anwenden, indem Sie sie in spitze Klammern `<>` einfügen. Dies ermöglicht es Ihnen, strengere Übereinstimmungsregeln für Parameter festzulegen.

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

Dieses Muster entspricht nur Produktcodes im Format `ABC-1234`. Zum Beispiel wird `/product/XYZ-5678` übereinstimmen, aber `/product/abc-5678` nicht.

## Wildcard-Segmente {#wildcard-segments}

Wildcards können verwendet werden, um gesamte Pfade zu erfassen, die einem bestimmten Routensegment folgen, dürfen jedoch nur als letztes Segment im Muster erscheinen und erfassen alle nachfolgenden Werte in der URL. Für eine bessere Lesbarkeit können Wildcard-Segmente benannt werden. Anders als benannte Parameter können Wildcard-Segmente jedoch keine Einschränkungen haben.

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

Dieses Muster entspricht jeder URL, die mit `/files` beginnt und den Rest des Pfades als Wildcard erfasst.

## Routenpriorität {#route-priority}

Wenn mehrere Routen mit einer bestimmten URL übereinstimmen, bestimmt das Prioritätsattribut einer Route, welche Route zuerst ausgewählt wird. Dies ist besonders nützlich, wenn zwei oder mehr Routen in ihren Pfadmuster überlappen und Sie eine Möglichkeit benötigen, zu steuern, welche Vorrang hat. Das Prioritätsattribut ist sowohl in den Anmerkungen `@Route` als auch `@RouteAlias` verfügbar.

### Wie das Prioritätssystem funktioniert {#how-the-priority-system-works}

Das Prioritätsattribut ermöglicht es dem Router zu bestimmen, in welcher Reihenfolge Routen bewertet werden, wenn mehrere Routen mit einer bestimmten URL übereinstimmen könnten. Routen werden basierend auf ihren Prioritätswerten sortiert, wobei höhere Priorität (niedrigere numerische Werte) zuerst übereinstimmt. Dies stellt sicher, dass spezifischere Routen Vorrang vor allgemeineren haben.

Wenn zwei Routen die gleiche Priorität haben, löst der Router den Konflikt, indem er die Route auswählt, die zuerst registriert wurde. Dieser Mechanismus sorgt dafür, dass die korrekte Route ausgewählt wird, selbst wenn mehrere Routen in ihren URL-Mustern überlappen.

:::info Standardpriorität  
Standardmäßig haben alle Routen eine Priorität von `10`.  
:::

### Beispiel: Konfliktierende Routen {#example-conflicting-routes}

Betrachten Sie ein Szenario, in dem zwei Routen ähnliche URL-Muster entsprechen:

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

Hier ist, wie das Prioritätssystem bei der Auflösung von Konflikten hilft:

- **`ProductCategoryView`** entspricht URLs wie `/products/electronics`.
- **`ProductView`** entspricht spezifischeren URLs wie `/products/electronics/123`, wobei `123` die Produkt-ID ist.

In diesem Fall könnten beide Routen der URL `/products/electronics` entsprechen. Da jedoch `ProductCategoryView` eine höhere Priorität hat (Priorität = 9), wird sie zuerst abgeglichen, wenn keine `productId` in der URL vorhanden ist. Für URLs wie `/products/electronics/123` wird `ProductView` aufgrund der Anwesenheit des `productId`-Parameters abgeglichen.
