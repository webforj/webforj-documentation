---
sidebar_position: 5
title: Route Patterns
description: >-
  Define dynamic URL segments, optional parameters, wildcards, and regex
  constraints to match webforJ routes precisely.
_i18n_hash: a6c1267e034c1562652cc01d0f336640
---
**Route Patterns** worden gebruikt om te definiëren hoe URL's map naar specifieke views, inclusief dynamische en optionele segmenten, reguliere expressies en wildcards. Routepatronen stellen het framework in staat om URL's te matchen, parameters te extraheren en URL's dynamisch te genereren. Ze spelen een cruciale rol in de structuur van de navigatie van een app en de weergave van componenten op basis van de locatie van de browser.

## Route patroon syntaxis {#route-pattern-syntax}

Routepatronen in webforJ zijn zeer flexibel en ondersteunen de volgende functies:

- **Genomineerde Parameters:** Aangeduid met `:paramName`, ze zijn verplicht tenzij gemarkeerd als optioneel.
- **Optionele Parameters:** Aangeduid met `:paramName?`, ze kunnen worden weggelaten uit de URL.
- **Wildcard Segmenten:** Weergegeven door `*`, ze vangen alle resterende segmenten van de URL.
- **Reguliere Expressie Beperkingen:** Beperkingen kunnen alleen worden toegevoegd aan genomineerde parameters (bijvoorbeeld, `:id<[0-9]+>`).

### Voorbeeld van routepatroon definities {#example-of-route-pattern-definitions}

```java
@Route("customer/:id<[0-9]+>/named/:name/*")
public class CustomerView extends Composite<Div> implements DidEnterObserver {

  @Override
  public void onDidEnter(DidEnterEvent event, ParametersBag parameters) {
    int id = parameters.getInt("id").orElse(0);
    String name = parameters.getAlpha("name").orElse("Onbekend");
    String extra = parameters.getAlpha("*").orElse("");

    String result =
        "Klant ID: " + id + "-" +
        "Naam: " + name + "-" +
        "*: " + extra;

    console().log(result);
  }
}
```

In dit voorbeeld:

- `:id<[0-9]+>` vangt een numerieke klant-ID.
- `:name` vangt een naam.
- `*` vangt eventuele extra padsegmenten naast `named/:name`.

## Genomineerde parameters {#named-parameters}

Genomineerde parameters worden gedefinieerd door een dubbele punt `:` voor de parameternaam in het patroon te plaatsen. Ze zijn verplicht tenzij gemarkeerd als optioneel. Genomineerde parameters kunnen ook reguliere expressie [beperkingen](#regular-expression-constraints) hebben om de waarden te valideren.

### Voorbeeld: {#example}

```java
@Route("product/:id")
public class ProductView extends Composite<Div> {
  // Componentlogica hier
}
```

Dit patroon matcht URL's zoals `/product/123` waarbij `id` `123` is.

## Optionele parameters {#optional-parameters}

Optionele parameters worden aangegeven door een `?` achter de parameternaam toe te voegen. Deze segmenten zijn niet vereist en kunnen uit de URL worden weggelaten.

### Voorbeeld: {#example-1}

```java
@Route("order/:id?<[0-9]+>")
public class OrderView extends Composite<Div> implements DidEnterObserver {

  @Override
  public void onDidEnter(DidEnterEvent event, ParametersBag parameters) {
    parameters.getInt("id").ifPresentOrElse(
      id -> console().log("Order ID: " + id),
      () -> console().log("Geen Order ID opgegeven")
    );
  }
}
```

Dit patroon matcht zowel `/order/123` om een numerieke waarde op te nemen als `/order`, waarbij de weglating van een numerieke waarde mogelijk is wanneer `/order` wordt ingevoerd.

## Reguliere expressie beperkingen {#regular-expression-constraints}

Je kunt reguliere expressie beperkingen toepassen op parameters door ze binnen haakjes `<>` toe te voegen. Dit stelt je in staat om strengere匹配regels voor parameters op te geven.

### Voorbeeld: {#example-2}

```java
@Route("product/:code<[A-Z]{3}-[0-9]{4}>")
public class ProductView extends Composite<FlexLayout> implements DidEnterObserver {

  @Override
  public void onDidEnter(DidEnterEvent event, ParametersBag parameters) {
    parameters.get("code").ifPresentOrElse(
      code -> console().log("Productcode: " + code),
      () -> console().error("Productcode niet gevonden"));
  }
}
```

Dit patroon matcht alleen productcodes in het formaat `ABC-1234`. Bijvoorbeeld, `/product/XYZ-5678` zal matchen, maar `/product/abc-5678` niet.

## Wildcard segmenten {#wildcard-segments}

Wildcards kunnen worden gebruikt om gehele paden te vangen die volgen op een specifiek route-segment, maar ze kunnen alleen als het laatste segment in het patroon verschijnen, waarbij alle daaropvolgende waarden in de URL worden opgelost. Voor een betere leesbaarheid kunnen wildcard segmenten worden genoemd. Echter, in tegenstelling tot genomineerde parameters, kunnen wildcard segmenten geen beperkingen hebben.

### Voorbeeld: {#example-3}

```java
@Route("files/:pathname*")
public class FileManagerView extends Composite<Div> implements DidEnterObserver {

  @Override
  public void onDidEnter(DidEnterEvent event, ParametersBag parameters) {
    parameters.get("pathname").ifPresentOrElse(
      pathname -> console().log("FileManagerView: " + pathname),
      () -> console().log("FileManagerView: Geen pathname-parameter")
    );
  }
}
```

Dit patroon matcht elke URL die begint met `/files` en vangt de rest van het pad als een wildcard.

## Route prioriteit {#route-priority}

Wanneer meerdere routes overeenkomen met een gegeven URL, bepaalt de prioriteitsattribut van een route welke route als eerste wordt geselecteerd. Dit is vooral nuttig wanneer twee of meer routes overlappen in hun padpatronen, en je een manier nodig hebt om te controleren welke prioriteit heeft. Het prioriteitsattribuut is beschikbaar in zowel `@Route` als `@RouteAlias` annotaties.

### Hoe het prioriteitssysteem werkt {#how-the-priority-system-works}

Het prioriteitsattribuut stelt de router in staat om de volgorde te bepalen waarin routes worden geëvalueerd wanneer meerdere routes een gegeven URL zouden kunnen matchen. Routes worden gesorteerd op basis van hun prioriteitswaarden, waarbij hogere prioriteit (lagere numerieke waarden) als eerste worden gematcht. Dit zorgt ervoor dat meer specifieke routes voorrang krijgen op meer algemene.

Als twee routes dezelfde prioriteit delen, lost de router het conflict op door de route te selecteren die als eerste is geregistreerd. Dit mechanisme zorgt ervoor dat de juiste route wordt gekozen, zelfs wanneer meerdere routes overlappen in hun URL-patronen.

:::info Standaard Prioriteit
Standaard krijgen alle routes een prioriteit van `10`.
:::

### Voorbeeld: Conflict verhogende routes {#example-conflicting-routes}

Overweeg een scenario waarin twee routes vergelijkbare URL-patronen matchen:

```java
@Route(value = "products/:category", priority = 9)
public class ProductCategoryView extends Composite<Div> implements DidEnterObserver {
  @Override
  public void onDidEnter(DidEnterEvent event, ParametersBag parameters) {
    String category = parameters.get("category").orElse("onbekend");
    console().log("Categorie bekijken: " + category);
  }
}

@Route(value = "products/:category/:productId?<[0-9]+>")
public class ProductView extends Composite<Div> implements DidEnterObserver {
  @Override
  public void onDidEnter(DidEnterEvent event, ParametersBag parameters) {
    String productId = parameters.get("productId").orElse("onbekend");
    console().log("Product bekijken: " + productId);
  }
}
```

Hier is hoe het prioriteitssysteem helpt bij het oplossen van conflicten:

- **`ProductCategoryView`** matcht URL's zoals `/products/electronics`.
- **`ProductView`** matcht specifiekere URL's zoals `/products/electronics/123`, waarbij `123` de product-ID is.

In dit geval zouden beide routes de URL `/products/electronics` kunnen matchen. Echter, omdat `ProductCategoryView` een hogere prioriteit heeft (prioriteit = 9), zal deze als eerste worden gematcht wanneer er geen `productId` in de URL staat. Voor URL's zoals `/products/electronics/123` zal `ProductView` worden gematcht vanwege de aanwezigheid van de parameter `productId`.
