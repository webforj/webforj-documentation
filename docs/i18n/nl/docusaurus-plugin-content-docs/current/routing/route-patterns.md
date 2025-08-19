---
sidebar_position: 5
title: Route Patterns
_i18n_hash: 2f1668e34197bb2f4bb6c5b3ec6e87e5
---
**Routepatronen** worden gebruikt om te definiëren hoe URL's worden gekoppeld aan specifieke weergaven, inclusief dynamische en optionele segmenten, reguliere expressies en jokertekens. Routepatronen stellen het framework in staat om URL's te matchen, parameters te extraheren en URL's dynamisch te genereren. Ze spelen een cruciale rol in het structureren van de navigatie van een app en de rendering van componenten op basis van de locatie van de browser.

## Syntax van routepatronen {#route-pattern-syntax}

Routepatronen in webforJ zijn zeer flexibel en ondersteunen de volgende functies:

- **Genoemde Parameters:** Aangeduid met `:paramName`, zijn vereist tenzij ze als optioneel zijn gemarkeerd.
- **Optionele Parameters:** Aangeduid met `:paramName?`, kunnen worden weggelaten uit de URL.
- **Jokersegmenten:** Vertegenwoordigd door `*`, vangen ze alle resterende segmenten van de URL.
- **Beperkingen voor Reguliere Expressies:** Beperkingen kunnen alleen worden toegevoegd aan genoemde parameters (bijvoorbeeld `:id<[0-9]+>`).

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

- `:id<[0-9]+>` vangt een numerieke klant ID.
- `:name` vangt een naam.
- `*` vangt eventuele aanvullende padsegmenten na `named/:name`.

## Genoemde parameters {#named-parameters}

Genoemde parameters worden gedefinieerd door een dubbele punt `:` voor de parameternaam in het patroon. Ze zijn vereist tenzij ze als optioneel zijn gemarkeerd. Genoemde parameters kunnen ook reguliere expressie [beperkingen](#regular-expression-constraints) hebben om de waarden te valideren.

### Voorbeeld: {#example}

```java
@Route("product/:id")
public class ProductView extends Composite<Div> {
  // Componentlogica hier
}
```

Dit patroon komt overeen met URL's zoals `/product/123` waar `id` `123` is.

## Optionele parameters {#optional-parameters}

Optionele parameters worden aangegeven door een `?` achter de parameternaam toe te voegen. Deze segmenten zijn niet vereist en kunnen uit de URL worden weggelaten.

### Voorbeeld: {#example-1}

```java
@Route("order/:id?<[0-9]+>")
public class OrderView extends Composite<Div> implements DidEnterObserver {

  @Override
  public void onDidEnter(DidEnterEvent event, ParametersBag parameters) {
    parameters.getInt("id").ifPresentOrElse(
      id -> console().log("Bestelling ID: " + id),
      () -> console().log("Geen Bestelling ID opgegeven")
    );
  }
}
```

Dit patroon komt overeen met zowel `/order/123` om een numerieke waarde op te nemen als `/order`, waardoor het mogelijk is om een numerieke waarde weg te laten wanneer `/order` wordt ingevoerd.

## Beperkingen voor reguliere expressies {#regular-expression-constraints}

U kunt beperkingen voor reguliere expressies toepassen op parameters door ze binnen hoekige haken `<>` toe te voegen. Dit stelt u in staat om strengere matchregels voor parameters op te geven.

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

Dit patroon komt alleen overeen met productcodes in het formaat `ABC-1234`. Bijvoorbeeld, `/product/XYZ-5678` zal overeenkomen, maar `/product/abc-5678` niet.

## Jokersegmenten {#wildcard-segments}

Jokertekens kunnen worden gebruikt om volledige paden vast te leggen die volgen op een specifiek route-segment, maar ze kunnen alleen als het laatste segment in het patroon verschijnen, waarmee alle volgende waarden in de URL worden opgelost. Voor een betere leesbaarheid kunnen jokersegmenten worden genoemd. Echter, in tegenstelling tot genoemde parameters, kunnen jokersegmenten geen beperkingen hebben.

### Voorbeeld: {#example-3}

```java
@Route("files/:pathname*")
public class FileManagerView extends Composite<Div> implements DidEnterObserver {

  @Override
  public void onDidEnter(DidEnterEvent event, ParametersBag parameters) {
    parameters.get("pathname").ifPresentOrElse(
      pathname -> console().log("FileManagerView: " + pathname),
      () -> console().log("FileManagerView: Geen pathname parameter")
    );
  }
}
```

Dit patroon komt overeen met elke URL die begint met `/files` en vangt de rest van het pad als een joker.

## Routeprioriteit {#route-priority}

Wanneer meerdere routes overeenkomen met een gegeven URL, bepaalt de prioriteitsattribuut van een route welke route als eerste wordt geselecteerd. Dit is vooral nuttig wanneer twee of meer routes overlappen in hun padpatronen, en je een manier nodig hebt om te controleren welke prioriteit heeft. Het prioriteitsattribuut is beschikbaar in zowel de `@Route` als de `@RouteAlias` annotaties.

### Hoe het prioriteitssysteem werkt {#how-the-priority-system-works}

Het prioriteitsattribuut stelt de router in staat om de volgorde te bepalen waarin routes worden geëvalueerd wanneer meerdere routes overeenkomen met een gegeven URL. Routes worden gesorteerd op basis van hun prioriteitswaarden, waarbij hogere prioriteit (lagere numerieke waarden) als eerste worden gematcht. Dit zorgt ervoor dat specifiekere routes voorrang krijgen op meer algemene.

Als twee routes dezelfde prioriteit delen, lost de router het conflict op door de route te selecteren die het eerst is geregistreerd. Dit mechanisme zorgt ervoor dat de juiste route wordt gekozen, zelfs wanneer meerdere routes overlappen in hun URL-patronen.

:::info Standaardprioriteit  
Standaard hebben alle routes een prioriteit van `10`.  
:::

### Voorbeeld: Conflicterende routes {#example-conflicting-routes}

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

- **`ProductCategoryView`** komt overeen met URL's zoals `/products/electronics`.
- **`ProductView`** komt overeen met specifiekere URL's zoals `/products/electronics/123`, waarbij `123` de product ID is.

In dit geval kunnen beide routes overeenkomen met de URL `/products/electronics`. Echter, omdat `ProductCategoryView` een hogere prioriteit heeft (prioriteit = 9), wordt deze als eerste gematcht wanneer er geen `productId` in de URL staat. Voor URL's zoals `/products/electronics/123`, zal `ProductView` worden gematcht vanwege de aanwezigheid van de `productId` parameter.
