---
sidebar_position: 6
title: Query Parameters
_i18n_hash: 5a8313b16d83bfbef6e8d43589430f90
---
Queryparameters stellen je in staat om extra gegevens via URL's door te geven, met het formaat `?key1=value1&key2=value2`. Terwijl routeparameters worden gebruikt om vereiste gegevens binnen het URL-pad door te geven, bieden queryparameters een flexibele methode voor het doorgeven van optionele of extra gegevens. Ze zijn vooral nuttig bij het filteren van inhoud, sorteren of het verwerken van meerdere waarden voor dezelfde sleutel.

## Overzicht van queryparameters {#query-parameters-overview}

Queryparameters in webforJ volgen de typische URL-conventie: sleutel-waarde paren gescheiden door `=` en samengevoegd met `&`. Ze worden aan de URL toegevoegd na een `?` en bieden een flexibele manier om optionele gegevens door te geven, zoals filter- of sorteervoorkeuren.

Bijvoorbeeld:

```
/products?category=electronics&sort=price
```

## Queryparameters ophalen {#retrieving-query-parameters}

Queryparameters worden benaderd via het `ParametersBag`-object. Om queryparameters op te halen, gebruik je de `getQueryParameters()`-methode van het `Location`-object.

Hier is hoe je queryparameters uit een URL in een view kunt ophalen:

```java
@Route(value = "products")
public class ProductView extends Composite<Div> implements DidEnterObserver {

  @Override
  public void onDidEnter(DidEnterEvent event, ParametersBag parameters) {
    ParametersBag queryParameters = event.getLocation().getQueryParameters();

    String category = queryParameters.get("category").orElse("all");
    String sort = queryParameters.get("sort").orElse("default");

    console().log("Categorie: " + category);
    console().log("Sorteren: " + sort);
  }
}
```

In dit voorbeeld:
- De `onDidEnter`-methode haalt queryparameters op van het `Location`-object dat door het `DidEnterEvent` wordt geleverd.
- De `ParametersBag` stelt je in staat om specifieke queryparameters op te halen met `get()`, wat een `Optional<String>` retourneert. Je kunt een standaardwaarde opgeven met `orElse()` als de parameter niet aanwezig is.

:::tip `ParametersBag` getters
De `ParametersBag` biedt verschillende getter-varianten om te helpen bij het casten van de waarde van queryparameters naar specifieke typen en het filteren ervan. Hieronder staat de volledige lijst van beschikbare getters:

- **`get(String key)`**: Haalt de waarde van de parameter op als een `String`.
- **`getAlpha(String key)`**: Retourneert alleen alfabetische tekens uit de parameterwaarde.
- **`getAlnum(String key)`**: Retourneert alleen alfanumerieke tekens uit de parameterwaarde.
- **`getDigits(String key)`**: Retourneert alleen de numerieke cijfers uit de parameterwaarde.
- **`getInt(String key)`**: Parsen en retourneert de parameterwaarde als een `Integer`.
- **`getFloat(String key)`**: Parsen en retourneert de parameterwaarde als een `Float`.
- **`getDouble(String key)`**: Parsen en retourneert de parameterwaarde als een `Double`.
- **`getBoolean(String key)`**: Parsen en retourneert de parameterwaarde als een `Boolean`.

Deze methoden helpen je ervoor te zorgen dat de waarden correct zijn geformatteerd en gecast, waardoor handmatige parsing of validatie overbodig wordt.
:::

## Meerdere waarden voor een queryparameter afhandelen {#handling-multiple-values-for-a-query-parameter}

Soms kan een queryparameter meerdere waarden hebben voor dezelfde sleutel, zoals in het volgende voorbeeld:

```
/products?category=electronics,appliances&sort=price
```

De `ParametersBag` biedt een methode om dit af te handelen door waarden als een lijst op te halen:

```java
@Route(value = "products")
public class ProductView extends Composite<Div> implements DidEnterObserver {

  @Override
  public void onDidEnter(DidEnterEvent event, ParametersBag parameters) {
    ParametersBag queryParameters = event.getLocation().getQueryParameters();

    List<String> categories = queryParameters.getList("category").orElse(List.of("all"));
    String sort = queryParameters.get("sort").orElse("default");

    console().log("Categorieën: " + categories);
    console().log("Sorteren: " + sort);
  }
}
```

In dit voorbeeld:
- `getList("category")` haalt alle waarden op die zijn gekoppeld aan de sleutel `category`, en retourneert deze als een lijst.

:::tip Meerdere Waarden Scheidingsteken
Standaard gebruikt de `getList()`-methode een komma (`,`) als scheidingsteken. Je kunt het scheidingsteken aanpassen door een ander teken of een reguliere expressie als tweede parameter door te geven aan de `getList(String key, String regex)`-methode.
:::

## Gebruiksscenario's voor queryparameters {#use-cases-for-query-parameters}

- **Inhoud filteren**: Queryparameters worden vaak gebruikt om filters toe te passen, zoals categorieën of zoekwoorden.
- **Gegevens sorteren**: Je kunt sorteervoorkeuren via queryparameters doorgeven, zoals sorteren op prijs, beoordeling of datum.
- **Optionele parameters afhandelen**: Wanneer je gegevens moet doorgeven die niet deel uitmaken van de vereiste route-structuur, bieden queryparameters flexibiliteit.
- **Meerdere waarden doorgeven**: Queryparameters stellen je in staat om meerdere waarden voor een enkele sleutel door te geven, wat nuttig is wanneer gebruikers meerdere opties selecteren, zoals productcategorieën of filters.
