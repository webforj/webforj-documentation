---
sidebar_position: 6
title: Query Parameters
_i18n_hash: 5a8313b16d83bfbef6e8d43589430f90
---
Abfrageparameter ermöglichen es Ihnen, zusätzliche Daten über URLs zu übermitteln, und zwar im Format `?key1=value1&key2=value2`. Während Routenparameter verwendet werden, um erforderliche Daten innerhalb des URL-Pfads zu übermitteln, bieten Abfrageparameter einen flexiblen Mechanismus zum Übermitteln von optionalen oder zusätzlichen Daten. Sie sind besonders nützlich beim Filtern von Inhalten, Sortieren oder Verarbeiten mehrerer Werte für denselben Schlüssel.

## Übersicht über Abfrageparameter {#query-parameters-overview}

Abfrageparameter in webforJ folgen der typischen URL-Konvention: Schlüssel-Wert-Paare, die durch `=` getrennt und mit `&` verknüpft sind. Sie werden an die URL nach einem `?` angehängt und bieten eine flexible Möglichkeit, optionale Daten wie Filter- oder Sortierpräferenzen zu übermitteln.

Zum Beispiel:

```
/products?category=electronics&sort=price
```

## Abfrageparameter abrufen {#retrieving-query-parameters}

Abfrageparameter werden über das Objekt `ParametersBag` zugegriffen. Um Abfrageparameter abzurufen, verwenden Sie die Methode `getQueryParameters()` des `Location`-Objekts.

So können Sie Abfrageparameter aus einer URL in einer Ansicht abrufen:

```java
@Route(value = "products")
public class ProductView extends Composite<Div> implements DidEnterObserver {

  @Override
  public void onDidEnter(DidEnterEvent event, ParametersBag parameters) {
    ParametersBag queryParameters = event.getLocation().getQueryParameters();

    String category = queryParameters.get("category").orElse("all");
    String sort = queryParameters.get("sort").orElse("default");

    console().log("Kategorie: " + category);
    console().log("Sortierung: " + sort);
  }
}
```

In diesem Beispiel:
- Die Methode `onDidEnter` ruft Abfrageparameter aus dem `Location`-Objekt ab, das vom `DidEnterEvent` bereitgestellt wird.
- Das `ParametersBag` ermöglicht es Ihnen, bestimmte Abfrageparameter mit `get()` abzurufen, was ein `Optional<String>` zurückgibt. Sie können einen Standardwert mit `orElse()` angeben, wenn der Parameter nicht vorhanden ist.

:::tip `ParametersBag`-Getter
Das `ParametersBag` bietet mehrere Getter-Varianten, um den Wert von Abfrageparametern in spezifische Typen zu casten und zu filtern. Die folgende Liste enthält alle verfügbaren Getter:

- **`get(String key)`**: Ruft den Wert des Parameters als `String` ab.
- **`getAlpha(String key)`**: Gibt nur alphabetische Zeichen aus dem Parameterwert zurück.
- **`getAlnum(String key)`**: Gibt nur alphanumerische Zeichen aus dem Parameterwert zurück.
- **`getDigits(String key)`**: Gibt nur die numerischen Ziffern aus dem Parameterwert zurück.
- **`getInt(String key)`**: Analysiert und gibt den Parameterwert als `Integer` zurück.
- **`getFloat(String key)`**: Analysiert und gibt den Parameterwert als `Float` zurück.
- **`getDouble(String key)`**: Analysiert und gibt den Parameterwert als `Double` zurück.
- **`getBoolean(String key)`**: Analysiert und gibt den Parameterwert als `Boolean` zurück.

Diese Methoden helfen Ihnen sicherzustellen, dass die Werte korrekt formatiert und gecastet werden, um manuelle Analysen oder Validierungen zu vermeiden.
:::

## Umgang mit mehreren Werten für einen Abfrageparameter {#handling-multiple-values-for-a-query-parameter}

Manchmal kann ein Abfrageparameter mehrere Werte für denselben Schlüssel haben, wie im folgenden Beispiel:

```
/products?category=electronics,appliances&sort=price
```

Das `ParametersBag` bietet eine Methode, um dies zu handhaben, indem Werte als Liste abgerufen werden:

```java
@Route(value = "products")
public class ProductView extends Composite<Div> implements DidEnterObserver {

  @Override
  public void onDidEnter(DidEnterEvent event, ParametersBag parameters) {
    ParametersBag queryParameters = event.getLocation().getQueryParameters();

    List<String> categories = queryParameters.getList("category").orElse(List.of("all"));
    String sort = queryParameters.get("sort").orElse("default");

    console().log("Kategorien: " + categories);
    console().log("Sortierung: " + sort);
  }
}
```

In diesem Beispiel:
- `getList("category")` ruft alle Werte ab, die mit dem Schlüssel `category` verknüpft sind, und gibt sie als Liste zurück.

:::tip Mehrwerte-Trenner
Standardmäßig verwendet die Methode `getList()` ein Komma (`,`) als Trenner. Sie können den Trenner anpassen, indem Sie ein anderes Zeichen oder einen regulären Ausdruck als zweiten Parameter an die Methode `getList(String key, String regex)` übergeben.
:::

## Anwendungsfälle für Abfrageparameter {#use-cases-for-query-parameters}

- **Inhalte filtern**: Abfrageparameter werden häufig verwendet, um Filter anzuwenden, wie Kategorien oder Suchbegriffe.
- **Daten sortieren**: Sie können Sortierpräferenzen über Abfrageparameter übermitteln, zum Beispiel nach Preis, Bewertung oder Datum.
- **Optionale Parameter behandeln**: Wenn Sie Daten übermitteln müssen, die nicht Teil der erforderlichen Routenstruktur sind, bieten Abfrageparameter Flexibilität.
- **Mehrere Werte übermitteln**: Abfrageparameter ermöglichen es Ihnen, mehrere Werte für einen einzelnen Schlüssel zu übermitteln, was nützlich ist, wenn Benutzer mehrere Optionen auswählen, wie Produktkategorien oder Filter.
