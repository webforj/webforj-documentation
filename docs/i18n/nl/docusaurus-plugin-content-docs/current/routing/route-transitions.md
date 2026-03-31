---
sidebar_position: 12
title: Route Transitions
sidebar_class_name: new-content
_i18n_hash: 34159c78405282a71774c6148a31f18a
---
<JavadocLink type="foundation" location="com/webforj/router/annotation/RouteTransition" top='true'/>

<DocChip chip='since' label='25.11' />
<DocChip chip='experimental' />

Route-overgangen bieden declaratieve geanimeerde overgangen bij het navigeren tussen routes. Gebouwd op de [View Transitions](/docs/advanced/view-transitions) API, laat het toevoegen van de `@RouteTransition` annotatie aan je routecomponenten de router automatisch de animatiefase tijdens de navigatie afhandelen.

:::warning Experimentele API
Deze API is gemarkeerd als experimenteel sinds 25.11 en kan in toekomstige releases veranderen. De API-handtekening, gedrag en prestatiekenmerken kunnen worden gewijzigd.
:::

:::info Programmatiche controle
Voor complexere overgangscenario's of programmatiche controle, gebruik de [View Transitions](/docs/advanced/view-transitions) API rechtstreeks.
:::

## De `@RouteTransition` annotatie {#the-routetransition-annotation}

De `@RouteTransition` annotatie definieert hoe een routecomponent animeert bij het binnenkomen of verlaten van de weergave:

```java
@Route
@RouteTransition(enter = ViewTransition.ZOOM, exit = ViewTransition.FADE)
public class DashboardView extends Composite<Div> {
  // weergave-implementatie
}
```

De annotatie accepteert de volgende eigenschappen:

| Eigenschap | Beschrijving |
|------------|--------------|
| `enter`    | Animatie toegepast wanneer deze weergave verschijnt |
| `exit`     | Animatie toegepast wanneer deze weergave verlaat |

Beide eigenschappen accepteren een van de vooraf gedefinieerde overgangstypen of een aangepaste string waarde:

| Constant | Effect |
|----------|--------|
| `ViewTransition.NONE` | Geen animatie |
| `ViewTransition.FADE` | Crossfade tussen oude en nieuwe inhoud |
| `ViewTransition.SLIDE_LEFT` | Inhoud beweegt naar links (zoals vooruit navigeren) |
| `ViewTransition.SLIDE_RIGHT` | Inhoud beweegt naar rechts (zoals terug navigeren) |
| `ViewTransition.SLIDE_UP` | Inhoud beweegt omhoog |
| `ViewTransition.SLIDE_DOWN` | Inhoud beweegt omlaag |
| `ViewTransition.ZOOM` | Oude inhoud krimpt weg, nieuwe inhoud groeit in |
| `ViewTransition.ZOOM_OUT` | Oude inhoud groeit weg, nieuwe inhoud krimpt in |

## Basisgebruik {#basic-usage}

Voeg de annotatie toe aan elke routecomponent om overgangen in te schakelen:

```java title="InboxView.java"
@Route(value = "inbox", outlet = MainLayout.class)
@RouteTransition(enter = ViewTransition.ZOOM, exit = ViewTransition.SLIDE_RIGHT)
@FrameTitle("Inbox")
public class InboxView extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();

  public InboxView() {
    self.add(new H1("Inbox"));
    // ...
  }
}
```

In dit voorbeeld:
- Bij het navigeren naar `InboxView`, komt de component binnen met een zoomanimatie
- Bij het navigeren weg van `InboxView`, verlaat de component met inhoud die naar rechts vloeit

## Navigatiestroom {#navigation-flow}

Bij het navigeren tussen twee routes coördineert de router de overgangsreeks:

1. De `exit` animatie van de verdwijnende component begint
2. [DOM](/docs/glossary#dom) veranderingen vinden plaats (oude weergave verwijderd, nieuwe weergave toegevoegd)
3. De `enter` animatie van de binnenkomende component speelt af

Als je naar dezelfde weergave navigeert die al wordt weergegeven, wordt de overgang overgeslagen om onnodige animaties te vermijden.

:::tip Consistente exit-animaties
Het gebruik van dezelfde exit-animatie over alle weergaven creëert directionele consistentie. Bijvoorbeeld, door alle weergaven te configureren om te verlaten met `SLIDE_RIGHT` wordt een uniforme "terug" bewegingspatroon vastgesteld, waardoor het navigatiegedrag voorspelbaar is, ongeacht de oorspronkelijke weergave.
:::

## Overgangs-erfenis {#transition-inheritance}

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

Alle onderliggende routes erven dezelfde animatiestijl zonder de annotatie te herhalen.

### Het overschrijven van geërfde overgangen {#overriding-inherited-transitions}

Onderliggende routes kunnen de geërfde overgang overschrijven door hun eigen `@RouteTransition` te definiëren:

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

## Gedeelde component-overgangen {#shared-component-transitions}

Je kunt route-overgangen combineren met gedeelde componentanimaties om verbonden ervaringen te creëren. Componenten met overeenkomende `view-transition-name` waarden vervormen tussen weergaven. Gebruik de `setViewTransitionName()` methode, beschikbaar op elke component die de <JavadocLink type="foundation" location="com/webforj/concern/HasStyle" code='true'>HasStyle</JavadocLink> interface implementeert.

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

Bij het navigeren van de lijst naar de detailweergave vervormt de productthumbnail naar de positie van de hero-afbeelding terwijl de rest van de inhoud overgaat met de fade-animatie.
