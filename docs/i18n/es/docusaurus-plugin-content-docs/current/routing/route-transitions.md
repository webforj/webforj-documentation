---
sidebar_position: 12
title: Route Transitions
sidebar_class_name: new-content
_i18n_hash: 34159c78405282a71774c6148a31f18a
---
<JavadocLink type="foundation" location="com/webforj/router/annotation/RouteTransition" top='true'/>

<DocChip chip='since' label='25.11' />
<DocChip chip='experimental' />

Las transiciones de ruta proporcionan transiciones animadas declarativas al navegar entre rutas. Basándose en la API de [Transiciones de Vista](/docs/advanced/view-transitions), añadir la anotación `@RouteTransition` a sus componentes de ruta permite que el enrutador maneje automáticamente el ciclo de vida de la animación durante la navegación.

:::warning API Experimental
Esta API está marcada como experimental desde el 25.11 y puede cambiar en futuras versiones. La firma de la API, el comportamiento y las características de rendimiento están sujetos a modificación.
:::

:::info Control programático
Para escenarios de transición más complejos o control programático, utilice la API de [Transiciones de Vista](/docs/advanced/view-transitions) directamente.
:::

## La anotación `@RouteTransition` {#the-routetransition-annotation}

La anotación `@RouteTransition` define cómo un componente de ruta anima al entrar o salir de la vista:

```java
@Route
@RouteTransition(enter = ViewTransition.ZOOM, exit = ViewTransition.FADE)
public class DashboardView extends Composite<Div> {
  // implementación de la vista
}
```

La anotación acepta las siguientes propiedades:

| Propiedad | Descripción |
|-----------|-------------|
| `enter`   | Animación aplicada cuando esta vista aparece |
| `exit`    | Animación aplicada cuando esta vista se deja |

Ambas propiedades aceptan cualquiera de los tipos de transición predefinidos o un valor de cadena personalizado:

| Constante                     | Efecto                                                      |
|-------------------------------|-------------------------------------------------------------|
| `ViewTransition.NONE`         | Sin animación                                              |
| `ViewTransition.FADE`         | Transición cruzada entre contenido antiguo y nuevo       |
| `ViewTransition.SLIDE_LEFT`   | El contenido fluye a la izquierda (como navegación hacia adelante) |
| `ViewTransition.SLIDE_RIGHT`  | El contenido fluye a la derecha (como navegación hacia atrás) |
| `ViewTransition.SLIDE_UP`     | El contenido fluye hacia arriba                           |
| `ViewTransition.SLIDE_DOWN`   | El contenido fluye hacia abajo                           |
| `ViewTransition.ZOOM`         | El contenido antiguo se reduce, el nuevo contenido crece |
| `ViewTransition.ZOOM_OUT`     | El contenido antiguo crece, el nuevo contenido se reduce |

## Uso básico {#basic-usage}

Agregue la anotación a cualquier componente de ruta para habilitar transiciones:

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

En este ejemplo:
- Al navegar a `InboxView`, el componente entra con una animación de zoom.
- Al navegar lejos de `InboxView`, el componente sale con el contenido fluyendo hacia la derecha.

## Flujo de navegación {#navigation-flow}

Al navegar entre dos rutas, el enrutador coordina la secuencia de transición:

1. Comienza la animación de `exit` del componente que sale.
2. Ocurren cambios en el [DOM](/docs/glossary#dom) (la vista antigua se elimina, la nueva vista se agrega).
3. Se reproduce la animación de `enter` del componente que entra.

Si se navega hacia la misma vista que ya está mostrada, se omite la transición para evitar animaciones innecesarias.

:::tip Animaciones de salida consistentes
Usar la misma animación de salida en todas las vistas crea consistencia direccional. Por ejemplo, configurar todas las vistas para salir con `SLIDE_RIGHT` establece un patrón de movimiento "hacia atrás" uniforme, haciendo que el comportamiento de navegación sea predecible sin importar la vista de origen.
:::

## Herencia de transiciones {#transition-inheritance}

Las rutas heredan transiciones de sus rutas padre. Cuando una ruta no tiene `@RouteTransition`, el enrutador sube por la jerarquía para encontrar una.

```java
@Route
@RouteTransition(enter = ViewTransition.ZOOM)
public class MainLayout extends Composite<AppLayout> {
  // Diseño padre con transición
}

@Route(value = "/inbox", outlet = MainLayout.class)
public class InboxView extends Composite<FlexLayout> {
  // Hereda ZOOM de MainLayout
}

@Route(value = "/sub", outlet = InboxView.class)
public class SubView extends Composite<FlexLayout> {
  // Hereda ZOOM de MainLayout (a través de InboxView)
}
```

Todas las rutas hijas heredan el mismo estilo de animación sin repetir la anotación.

### Sobrescribiendo transiciones heredadas {#overriding-inherited-transitions}

Las rutas hijas pueden sobrescribir la transición heredada definiendo su propio `@RouteTransition`:

```java
@Route
@RouteTransition(enter = ViewTransition.ZOOM)
public class MainLayout extends Composite<AppLayout> {}

@Route(value = "/inbox", outlet = MainLayout.class)
public class InboxView extends Composite<FlexLayout> {
  // Hereda ZOOM
}

@Route(value = "/settings", outlet = MainLayout.class)
@RouteTransition(enter = ViewTransition.SLIDE_UP, exit = ViewTransition.SLIDE_DOWN)
public class SettingsView extends Composite<FlexLayout> {
  // Sobrescribe con SLIDE_UP/SLIDE_DOWN
}
```

## Transiciones de componentes compartidos {#shared-component-transitions}

Puede combinar transiciones de ruta con animaciones de componentes compartidos para crear experiencias conectadas. Los componentes con valores de `view-transition-name` coincidentes se transforman entre vistas. Use el método `setViewTransitionName()`, disponible en cualquier componente que implemente la interfaz <JavadocLink type="foundation" location="com/webforj/concern/HasStyle" code='true'>HasStyle</JavadocLink>.

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

Al navegar de la lista a la vista de detalle, la miniatura del producto se transforma en la posición de la imagen principal mientras el resto del contenido transiciona con la animación de desvanecimiento.
