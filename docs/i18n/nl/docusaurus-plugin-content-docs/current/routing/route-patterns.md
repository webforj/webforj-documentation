---
sidebar_position: 5
title: Route Patterns
_i18n_hash: d18952e5072af2c542c1459e3f65d787
---
**Routepatronen** worden gebruikt om te definiëren hoe URL's in kaart worden gebracht naar specifieke weergaven, inclusief dynamische en optionele segmenten, reguliere expressies en wildcard-argumenten. Routepatronen stellen het framework in staat om URL's te matchen, parameters te extraheren en URL's dynamisch te genereren. Ze spelen een cruciale rol in het structureren van de navigatie en het renderen van componenten in een app op basis van de locatie van de browser.

## Routepatroon syntaxis {#route-pattern-syntax}

Routepatronen in webforJ zijn zeer flexibel en ondersteunen de volgende functies:

- **Genoteerde Parameters:** Aangeduid met `:paramName`, deze zijn verplicht tenzij als optioneel gemarkeerd.
- **Optionele Parameters:** Aangeduid met `:paramName?`, deze kunnen worden weggelaten uit de URL.
- **Wildcard Segmenten:** Vertegenwoordigd door `*`, deze vangen alle resterende segmenten van de URL.
- **Reguliere Expressie Beperkingen:** Beperkingen kunnen alleen aan genoteerde parameters worden toegevoegd (bijvoorbeeld, `:id<[0-9]+>`).

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
- `*` vangt alle extra padsegmenten voorbij `named/:name`.

## Genoteerde parameters {#named-parameters}

Genoteerde parameters worden gedefinieerd door een dubbele punt `:` voor de parameternaam in het patroon te plaatsen. Ze zijn verplicht tenzij als optioneel gemarkeerd. Genoteerde parameters kunnen ook reguliere expressie [beperkingen](#regular-expression-constraints) hebben om de waarden te valideren.

### Voorbeeld: {#example}

```java
@Route("product/:id")
public class ProductView extends Composite<Div> {
  // Componentlogica hier
}
```

Dit patroon komt overeen met URL's zoals `/product/123` waar `id` `123` is.

## Optionele parameters {#optional-parameters}

Optionele parameters worden aangegeven door een `?` achter de parameternaam toe te voegen. Deze segmenten zijn niet verplicht en kunnen uit de URL worden weggelaten.

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

Dit patroon komt overeen met zowel `/order/123`, waar een numerieke waarde moet worden ingevoerd, als `/order`, waardoor het mogelijk is om een numerieke waarde weg te laten wanneer `/order` wordt ingevoerd.

## Reguliere expressie beperkingen {#regular-expression-constraints}

Je kunt reguliere expressie beperkingen toepassen op parameters door ze binnen haken `< >` toe te voegen. Dit stelt je in staat om striktere matchregels voor parameters op te geven.

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

## Wildcard segmenten {#wildcard-segments}

Wildcard-argumenten kunnen worden gebruikt om gehele paden na een specifiek route-segment te vangen, maar ze kunnen alleen als het laatste segment in het patroon voorkomen, waardoor alle volgende waarden in de URL worden opgelost. Voor een betere leesbaarheid kunnen wildcard-argumenten een naam hebben. Echter, in tegenstelling tot genoteerde parameters, kunnen wildcard-argumenten geen beperkingen hebben.

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

Dit patroon komt overeen met elke URL die begint met `/files` en vangt de rest van het pad als een wildcard.

## Routeprioriteit {#route-priority}

Wanneer meerdere routes overeenkomen met een gegeven URL, bepaalt het prioriteitsattribuut van een route welke route als eerste wordt geselecteerd. Dit is vooral handig wanneer twee of meer routes overlappen in hun padpatronen en je een manier nodig hebt om te beheersen welke prioriteit krijgt. Het prioriteitsattribuut is beschikbaar in zowel `@Route` als `@RouteAlias` annotaties.

### Hoe het prioriteitssysteem werkt {#how-the-priority-system-works}

Het prioriteitsattribuut stelt de router in staat om de volgorde te bepalen waarin routes worden geëvalueerd wanneer meerdere routes overeenkomen met een gegeven URL. Routes worden gesorteerd op basis van hun prioriteitswaarden, waarbij een hogere prioriteit (lagere numerieke waarden) als eerste wordt gematcht. Dit zorgt ervoor dat meer specifieke routes de voorkeur krijgen boven meer algemene.

Als twee routes dezelfde prioriteit delen, lost de router het conflict op door de route te selecteren die als eerste is geregistreerd. Dit mechanisme zorgt ervoor dat de juiste route wordt gekozen, zelfs als meerdere routes overlappen in hun URL-patronen.

:::info Standaardprioriteit  
Standaardmatig krijgen alle routes een prioriteit van `10`.  
:::

### Voorbeeld: Conflicterende routes {#example-conflicting-routes}

Overweeg een scenario waarin twee routes overeenkomen met vergelijkbare URL-patronen:

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

Hier is hoe het prioriteitssysteem helpt conflicten op te lossen:

- **`ProductCategoryView`** komt overeen met URL's zoals `/products/electronics`.
- **`ProductView`** komt overeen met specifiekere URL's zoals `/products/electronics/123`, waarbij `123` de product-ID is.

In dit geval zouden beide routes overeenkomen met de URL `/products/electronics`. Echter, omdat `ProductCategoryView` een hogere prioriteit heeft (prioriteit = 9), zal deze als eerste worden gematcht wanneer er geen `productId` in de URL staat. Voor URL's zoals `/products/electronics/123`, zal `ProductView` worden gematcht vanwege de aanwezigheid van de `productId` parameter.
