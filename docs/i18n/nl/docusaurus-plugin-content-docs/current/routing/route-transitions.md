---
sidebar_position: 12
title: Route Transitions
sidebar_class_name: new-content
_i18n_hash: 5991e12089a2044ef0fd6b15cae1fb13
---
<JavadocLink type="foundation" location="com/webforj/router/annotation/RouteTransition" top='true'/>

<DocChip chip='since' label='25.11' />
<DocChip chip='experimental' />

Route-overgangen bieden declaratieve geanimeerde overgangen bij het navigeren tussen routes. Gebouwd op de [View Transitions](/docs/advanced/view-transitions) API, laat het toevoegen van de `@RouteTransition` annotatie aan uw routecomponenten de router automatisch de animatielifecycle tijdens navigatie beheren.

:::warning Experimentele API
Deze API is gemarkeerd als experimenteel sinds 25.11 en kan in toekomstige releases veranderen. De API-handtekening, het gedrag en de prestatiekenmerken kunnen worden gewijzigd.
:::

:::info Programma controle
Voor complexere overgangsscenario's of programmatische controle, gebruik de [View Transitions](/docs/advanced/view-transitions) API direct.
:::

## De `@RouteTransition` annotatie {#the-routetransition-annotation}

De `@RouteTransition` annotatie definieert hoe een routecomponent animeert bij het binnenkomen of verlaten van de weergave:

```java
@Route
@RouteTransition(enter = ViewTransition.ZOOM, exit = ViewTransition.FADE)
public class DashboardView extends Composite<Div> {
  // view-implementatie
}
```

De annotatie accepteert de volgende eigenschappen:

| Eigenschap | Beschrijving |
|------------|--------------|
| `enter`    | Animatie die wordt toegepast wanneer deze weergave verschijnt |
| `exit`     | Animatie die wordt toegepast wanneer deze weergave verlaat |

Beide eigenschappen accepteren een van de vooraf gedefinieerde overgangstypen of een aangepaste stringwaarde:

| Constant | Effect |
|----------|--------|
| `ViewTransition.NONE` | Geen animatie |
| `ViewTransition.FADE` | Kruisfade tussen oude en nieuwe inhoud |
| `ViewTransition.SLIDE_LEFT` | Inhoud vloeit naar links (zoals voorwaartse navigatie) |
| `ViewTransition.SLIDE_RIGHT` | Inhoud vloeit naar rechts (zoals terug navigatie) |
| `ViewTransition.SLIDE_UP` | Inhoud vloeit omhoog |
| `ViewTransition.SLIDE_DOWN` | Inhoud vloeit omlaag |
| `ViewTransition.ZOOM` | Oude inhoud krimpt weg, nieuwe inhoud groeit in |
| `ViewTransition.ZOOM_OUT` | Oude inhoud groeit weg, nieuwe inhoud krimpt in |

## Basisgebruik {#basic-usage}

Voeg de annotatie toe aan een routecomponent om overgangen in te schakelen:

```java title="InboxView.java"
@Route(value = "inbox", outlet = MainLayout.class)
@RouteTransition(enter = ViewTransition.ZOOM, exit = ViewTransition.SLIDE_RIGHT)
@FrameTitle("Inbox")
public class InboxView extends Composite<FlexLayout> {

  public InboxView() {
    getBoundComponent().add(new H1("Inbox"));
    // ...
  }
}
```

In dit voorbeeld:
- Bij het navigeren naar `InboxView`, komt de component binnen met een zoomanimatie
- Bij het navigeren weg van `InboxView`, verlaat de component met inhoud die naar rechts vloeit

## Navigatiestroom {#navigation-flow}

Bij het navigeren tussen twee routes coördineert de router de overgangsvolgorde:

1. De `exit` animatie van de vertrekkende component begint
2. [DOM](/docs/glossary#dom) veranderingen vinden plaats (oude weergave verwijderd, nieuwe weergave toegevoegd)
3. De `enter` animatie van de binnenkomende component speelt af

Als er naar dezelfde weergave wordt genavigeerd die al wordt weergegeven, wordt de overgang overgeslagen om onnodige animaties te vermijden.

:::tip Consistente exit-animaties
Het gebruik van dezelfde exit-animatie over alle weergaven creëert directionele consistentie. Bijvoorbeeld, het configureren van alle weergaven om te verlaten met `SLIDE_RIGHT` creëert een uniforme "terug" bewegingpatroon, waardoor het navigatiegedrag voorspelbaar is, ongeacht de oorspronkelijke weergave.
:::

## Overgangs-erfelijkheid {#transition-inheritance}

Routes erven overgangen van hun bovenliggende routes. Wanneer een route geen `@RouteTransition` heeft, loopt de router omhoog in de hiërarchie om er een te vinden.

```java
@Route
@RouteTransition(enter = ViewTransition.ZOOM)
public class MainLayout extends Composite<AppLayout> {
  // Bovenliggende lay-out met overgang
}

@Route(value = "/inbox", outlet = MainLayout.class)
public class InboxView extends Composite<FlexLayout> {
  // Erft ZOOM van MainLayout
}

@Route(value = "/sub", outlet = InboxView.class)
public class SubView extends Composite<FlexLayout> {
  // Erft ZOOM van MainLayout (via InboxView)
}
```

Alle kindroutes erven dezelfde animatiestijl zonder de annotatie te herhalen.

### Het overschrijven van geërfde overgangen {#overriding-inherited-transitions}

Kindroutes kunnen de geërfde overgang overschrijven door hun eigen `@RouteTransition` te definiëren:

```java
@Route
@RouteTransition(enter = ViewTransition.ZOOM)
public class MainLayout extends Composite<AppLayout> {}

@Route(value = "/inbox", outlet = MainLayout.class)
public class InboxView extends Composite<FlexLayout> {
  // Erft ZOOM
}

@Route(value = "/settings", outlet = MainLayout.class)
@RouteTransition(enter = ViewTransition.SLIDE_UP, exit = ViewTransition.SLIDE_DOWN)
public class SettingsView extends Composite<FlexLayout> {
  // Overschrijft met SLIDE_UP/SLIDE_DOWN
}
```

## Gedeelde componentovergangen {#shared-component-transitions}

U kunt routeovergangen combineren met gedeelde componentanimaties om verbonden ervaringen te creëren. Componenten met overeenkomende `view-transition-name` waarden veranderen tussen weergaven. Gebruik de `setViewTransitionName()` methode, beschikbaar op elk component dat de <JavadocLink type="foundation" location="com/webforj/concern/HasStyle" code='true'>HasStyle</JavadocLink> interface implementeert.

```java title="ProductListView.java"
@Route(value = "products", outlet = MainLayout.class)
@RouteTransition(enter = ViewTransition.FADE)
public class ProductListView extends Composite<FlexLayout> {

  private void buildProductCard(Product product) {
      Img thumbnail = new Img(product.getImageUrl());
      thumbnail.setViewTransitionName("product-image-" + product.getId());
      // ...
  }
}
```

```java title="ProductDetailView.java"
@Route(value = "products/:id", outlet = MainLayout.class)
@RouteTransition(enter = ViewTransition.FADE)
public class ProductDetailView extends Composite<FlexLayout> implements DidEnterObserver {

  private Img heroImage = new Img();

  @Override
  public void onDidEnter(DidEnterEvent event, ParametersBag parameters) {
      String id = parameters.get("id").orElse("");
      heroImage.setViewTransitionName("product-image-" + id);
      // ...
  }
}
```

Bij het navigeren van de lijst naar de detailweergave, verandert de productminiatuur in de positie van de heldenafbeelding terwijl de rest van de inhoud overgaat met de fade-animatie.
