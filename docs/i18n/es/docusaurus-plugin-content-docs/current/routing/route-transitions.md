---
sidebar_position: 12
title: Route Transitions
sidebar_class_name: new-content
_i18n_hash: 5991e12089a2044ef0fd6b15cae1fb13
---
<JavadocLink type="foundation" location="com/webforj/router/annotation/RouteTransition" top='true'/>

<DocChip chip='since' label='25.11' />
<DocChip chip='experimental' />

Las transiciones de ruta proporcionan transiciones animadas declarativas al navegar entre rutas. Basado en la API de [View Transitions](/docs/advanced/view-transitions), agregar la anotación `@RouteTransition` a tus componentes de ruta permite que el enrutador maneje automáticamente el ciclo de vida de la animación durante la navegación.

:::warning API experimental
Esta API está marcada como experimental desde 25.11 y puede cambiar en futuras versiones. La firma de la API, el comportamiento y las características de rendimiento están sujetos a modificación.
:::

:::info Control programático
Para escenarios de transición más complejos o control programático, usa la API de [View Transitions](/docs/advanced/view-transitions) directamente.
:::

## La anotación `@RouteTransition` {#the-routetransition-annotation}

La anotación `@RouteTransition` define cómo un componente de ruta se anima al entrar o salir de la vista:

```java
@Route
@RouteTransition(enter = ViewTransition.ZOOM, exit = ViewTransition.FADE)
public class DashboardView extends Composite<Div> {
  // implementación de vista
}
```

La anotación acepta las siguientes propiedades:

| Propiedad | Descripción |
|-----------|-------------|
| `enter`   | Animación aplicada cuando esta vista aparece |
| `exit`    | Animación aplicada cuando esta vista se cierra |

Ambas propiedades aceptan cualquiera de los tipos de transición predefinidos o un valor de cadena personalizado:

| Constante                      | Efecto                                                      |
|--------------------------------|-------------------------------------------------------------|
| `ViewTransition.NONE`          | Sin animación                                              |
| `ViewTransition.FADE`          | Transición cruzada entre el contenido viejo y nuevo      |
| `ViewTransition.SLIDE_LEFT`    | El contenido fluye hacia la izquierda (como en la navegación hacia adelante) |
| `ViewTransition.SLIDE_RIGHT`   | El contenido fluye hacia la derecha (como en la navegación hacia atrás) |
| `ViewTransition.SLIDE_UP`      | El contenido fluye hacia arriba                           |
| `ViewTransition.SLIDE_DOWN`    | El contenido fluye hacia abajo                            |
| `ViewTransition.ZOOM`          | El contenido viejo se reduce, el nuevo contenido crece   |
| `ViewTransition.ZOOM_OUT`      | El contenido viejo crece, el nuevo contenido se reduce   |

## Uso básico {#basic-usage}

Agrega la anotación a cualquier componente de ruta para habilitar transiciones:

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

En este ejemplo:
- Al navegar a `InboxView`, el componente entra con una animación de zoom
- Al navegar fuera de `InboxView`, el componente sale con el contenido fluyendo hacia la derecha

## Flujo de navegación {#navigation-flow}

Al navegar entre dos rutas, el enrutador coordina la secuencia de transición:

1. Comienza la animación `exit` del componente que está saliendo
2. Ocurren cambios en el [DOM](/docs/glossary#dom) (vista antigua eliminada, nueva vista añadida)
3. Se reproduce la animación `enter` del componente que está entrando

Si navegas a la misma vista que ya se está mostrando, se omite la transición para evitar animaciones innecesarias.

:::tip Animaciones de salida consistentes
Usar la misma animación de salida en todas las vistas crea consistencia direccional. Por ejemplo, configurar todas las vistas para salir con `SLIDE_RIGHT` establece un patrón de movimiento "hacia atrás" uniforme, haciendo que el comportamiento de la navegación sea predecible sin importar la vista de origen.
:::

## Herencia de transiciones {#transition-inheritance}

Las rutas heredan transiciones de sus rutas padres. Cuando una ruta no tiene `@RouteTransition`, el enrutador sube en la jerarquía para encontrar una.

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

Puedes combinar las transiciones de ruta con animaciones de componentes compartidos para crear experiencias conectadas. Los componentes con valores coincidentes de `view-transition-name` se transforman entre vistas. Usa el método `setViewTransitionName()`, disponible en cualquier componente que implemente la interfaz <JavadocLink type="foundation" location="com/webforj/concern/HasStyle" code='true'>HasStyle</JavadocLink>.

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

Al navegar de la lista a la vista de detalles, la miniatura del producto se transforma en la posición de la imagen principal mientras el resto del contenido transiciona con la animación de desvanecimiento.
