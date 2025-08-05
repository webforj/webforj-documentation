---
sidebar_position: 6
title: Query Parameters
_i18n_hash: c3b57611c46f7cd4fa9946ff704213cc
---
Query-Parameter ermöglichen es Ihnen, zusätzliche Daten über URLs zu übergeben, im Format `?key1=value1&key2=value2`. Während Routenparameter verwendet werden, um erforderliche Daten innerhalb des URL-Pfads zu übergeben, bieten Query-Parameter einen flexiblen Mechanismus zur Übermittlung optionaler oder zusätzlicher Daten. Sie sind besonders nützlich, wenn Inhalte gefiltert, sortiert oder mehrere Werte für denselben Schlüssel verarbeitet werden müssen.

## Übersicht über Query-Parameter {#query-parameters-overview}

Query-Parameter in webforJ folgen der typischen URL-Konvention: Schlüssel-Wert-Paare, die durch `=` getrennt und mit `&` verknüpft sind. Sie werden der URL nach einem `?` hinzugefügt und bieten eine flexible Möglichkeit, optionale Daten wie Filter- oder Sortierpräferenzen zu übermitteln.

Zum Beispiel:

```
/products?category=electronics&sort=price
```

## Abrufen von Query-Parametern {#retrieving-query-parameters}

Query-Parameter werden über das Objekt `ParametersBag` zugegriffen. Um Query-Parameter abzurufen, verwenden Sie die Methode `getQueryParameters()` des `Location`-Objekts.

Hier ist, wie Sie Query-Parameter aus einer URL in einer Ansicht abrufen können:

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
- Die Methode `onDidEnter` ruft die Query-Parameter aus dem `Location`-Objekt ab, das durch das `DidEnterEvent` bereitgestellt wird.
- Das `ParametersBag` ermöglicht es Ihnen, spezifische Query-Parameter mit `get()` abzurufen, was ein `Optional<String>` zurückgibt. Sie können einen Standardwert mit `orElse()` angeben, falls der Parameter nicht vorhanden ist.

:::tip `ParametersBag` Getter
Das `ParametersBag` bietet mehrere Getter-Variationen, um den Wert von Query-Parametern auf spezifische Typen zu casten und sie zu filtern. Folgendes ist die vollständige Liste der verfügbaren Getter:

- **`get(String key)`**: Ruft den Wert des Parameters als `String` ab.
- **`getAlpha(String key)`**: Gibt nur alphabetische Zeichen aus dem Parameterwert zurück.
- **`getAlnum(String key)`**: Gibt nur alphanumerische Zeichen aus dem Parameterwert zurück.
- **`getDigits(String key)`**: Gibt nur die numerischen Ziffern aus dem Parameterwert zurück.
- **`getInt(String key)`**: Parst und gibt den Parameterwert als `Integer` zurück.
- **`getFloat(String key)`**: Parst und gibt den Parameterwert als `Float` zurück.
- **`getDouble(String key)`**: Parst und gibt den Parameterwert als `Double` zurück.
- **`getBoolean(String key)`**: Parst und gibt den Parameterwert als `Boolean` zurück.

Diese Methoden helfen Ihnen, sicherzustellen, dass die Werte korrekt formatiert und gecastet sind, wodurch die Notwendigkeit für manuelles Parsen oder Validierung entfällt.
:::

## Handhabung mehrerer Werte für einen Query-Parameter {#handling-multiple-values-for-a-query-parameter}

Manchmal kann ein Query-Parameter mehrere Werte für denselben Schlüssel haben, wie im folgenden Beispiel:

```
/products?category=electronics,appliances&sort=price
```

Das `ParametersBag` bietet eine Methode, um dies zu handhaben, indem es die Werte als Liste abruft:

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

:::tip Trennzeichen für mehrere Werte
Standardmäßig verwendet die Methode `getList()` ein Komma (`,`) als Trennzeichen. Sie können das Trennzeichen anpassen, indem Sie ein anderes Zeichen oder einen regulären Ausdruck als zweiten Parameter an die Methode `getList(String key, String regex)` übergeben.
:::

## Anwendungsfälle für Query-Parameter {#use-cases-for-query-parameters}

- **Inhalte filtern**: Query-Parameter werden häufig verwendet, um Filter anzuwenden, wie Kategorien oder Suchbegriffe.
- **Daten sortieren**: Sie können Sortierpräferenzen über Query-Parameter übergeben, wie z. B. nach Preis, Bewertung oder Datum sortieren.
- **Optionale Parameter handhaben**: Wenn Sie Daten übergeben müssen, die nicht Teil der erforderlichen Routenstruktur sind, bieten Query-Parameter Flexibilität.
- **Mehrere Werte übergeben**: Query-Parameter ermöglichen es Ihnen, mehrere Werte für einen einzelnen Schlüssel zu senden, was nützlich ist, wenn Benutzer mehrere Optionen auswählen, wie Produktkategorien oder Filter.
