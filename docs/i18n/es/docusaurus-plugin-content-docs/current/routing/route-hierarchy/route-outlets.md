---
sidebar_position: 3
title: Route Outlets
_i18n_hash: 8a64cd917fe9f1de3f37ee01254e80e7
---
Un **outlet** es un componente designado, ya sea un [layout de ruta](./route-types#layout-routes) o una [vista de ruta](./route-types#view-routes), donde se renderizan dinámicamente las rutas secundarias. Define dónde aparecerá el contenido de la ruta secundaria dentro de la ruta principal. Los outlets son fundamentales para crear interfaces de usuario modulares y anidadas, así como estructuras de navegación flexibles.

## Definiendo un outlet {#defining-an-outlet}

Los outlets se implementan típicamente utilizando componentes contenedores que pueden sostener y gestionar contenido secundario. En webforJ, cualquier componente que implemente la interfaz `HasComponents`, o un compuesto de tales componentes, puede servir como un outlet. Por ejemplo, [`FlexLayout`](../../components/flex-layout) implementa la interfaz `HasComponents`, lo que lo convierte en un outlet válido para rutas secundarias.

Si no se define explícitamente un outlet para una ruta, se utiliza el primer `Frame` de la aplicación como el outlet predeterminado. Este comportamiento asegura que cada ruta secundaria tenga un lugar para ser renderizada.

:::tip Gestión de Frames
En aplicaciones con múltiples frames, puedes especificar qué frame usar como el outlet para las rutas secundarias estableciendo el atributo `frame` en la anotación `@Route`. El atributo `frame` acepta el nombre del frame que se utilizará para el renderizado.
:::

### Ejemplo: {#example}

```java
@Route
public class MainLayout extends Composite<AppLayout> {
  public MainLayout() {
    setHeader();
    setDrawer();
  }
}

@Route(outlet = MainLayout.class)
public class DashboardView extends Composite<Div> {
  private final Div self = getBoundComponent();

  public DashboardView() {
    self.add(new H1("Contenido del Dashboard"));
  }
}
```

En este ejemplo:

- `MainLayout` actúa como el contenedor del layout, pero como no se define un outlet específico, se utiliza el `Frame` predeterminado de la aplicación.
- La `DashboardView` se renderiza dentro de `MainLayout` utilizando el outlet predeterminado (área de contenido) del `AppLayout`.

Así, las rutas secundarias de `MainLayout` se renderizarán automáticamente en la ranura de contenido de `AppLayout`, a menos que se especifique un outlet o frame diferente.

## Ciclo de vida del outlet {#outlet-lifecycle}

Los outlets están estrechamente vinculados al ciclo de vida de las rutas. Cuando la ruta activa cambia, el outlet actualiza su contenido dinámicamente al inyectar el componente secundario apropiado y eliminar cualquier componente que ya no sea necesario. Esto asegura que solo se rendericen las vistas relevantes en un momento dado.

- **Creación**: Los outlets se inicializan antes de que se creen los componentes secundarios.
- **Inyección de contenido**: Cuando se coincide con una ruta secundaria, su componente se inyecta en el outlet.
- **Actualización**: Al navegar entre rutas, el outlet actualiza su contenido, inyectando el nuevo componente secundario y eliminando cualquier componente obsoleto.

## Outlets personalizados {#custom-outlets}

La interfaz `RouteOutlet` es responsable de gestionar el ciclo de vida de los componentes de ruta, determinando cómo se renderizan y eliminan los componentes. Cualquier componente que implemente esta interfaz puede actuar como un outlet para otros componentes.

### Métodos clave en `RouteOutlet`: {#key-methods-in-routeoutlet}

- **`showRouteContent(Component component)`**: Responsable de renderizar el componente proporcionado en el outlet. Se llama cuando el enrutador coincide con una ruta y es necesario mostrar el componente secundario.
- **`removeRouteContent(Component component)`**: Maneja la eliminación del componente del outlet, típicamente se llama al navegar fuera de la ruta actual.

Al implementar `RouteOutlet`, los desarrolladores pueden controlar cómo se inyectan las rutas en áreas específicas de la aplicación. por ejemplo

```java
import com.webforj.router.RouteOutlet;

public class MainLayout extends Composite<AppLayout> implements RouteOutlet {
  private final AppLayout self = getBoundComponent();

  @Override
  public void showRouteContent(Component component) {
    self.addToDrawer(component);
  }

  @Override
  public void removeRouteContent(Component component) {
    self.remove(component);
  }
}
```

En este ejemplo, la clase `MainLayout` implementa la interfaz `RouteOutlet`, lo que permite agregar o eliminar componentes del cajón del AppLayout dinámicamente según la navegación de rutas en lugar del área de contenido predeterminada definida en el componente `AppLayout`.

## Componentes de outlet en caché {#caching-outlet-components}

Por defecto, los outlets agregan y eliminan componentes dinámicamente al navegar hacia y desde rutas. Sin embargo, en ciertos casos—particularmente para vistas con componentes complejos—puede ser preferible alternar la visibilidad de los componentes en lugar de eliminarlos completamente del DOM. Aquí es donde entra en juego el `PersistentRouteOutlet`, que permite que los componentes permanezcan en memoria y simplemente se oculten o muestren, en lugar de ser destruidos y recreados.

El `PersistentRouteOutlet` almacena en caché los componentes renderizados, manteniéndolos en memoria cuando el usuario navega fuera. Esto mejora el rendimiento al evitar la destrucción y recreación innecesarias de componentes, lo cual es especialmente beneficioso para aplicaciones donde los usuarios cambian frecuentemente entre vistas.

### Cómo funciona `PersistentRouteOutlet`: {#how-persistentrouteoutlet-works}

- **Caché de componentes**: Mantiene un caché en memoria de todos los componentes que han sido renderizados dentro del outlet.
- **Alternar visibilidad**: En lugar de eliminar componentes del DOM, los oculta al navegar fuera de una ruta.
- **Restauración de componentes**: Cuando el usuario navega de regreso a una ruta previamente almacenada en caché, el componente simplemente se muestra de nuevo sin necesidad de recreación.

Este comportamiento es particularmente útil para UIs complejas donde la renderización constante de los componentes puede degradar el rendimiento. Sin embargo, para que esta alternancia de visibilidad funcione, los componentes gestionados deben implementar la interfaz `HasVisibility`, que permite al `PersistentRouteOutlet` controlar su visibilidad.

:::tip Cuándo usar `PersistentRouteOutlet`
Utiliza `PersistentRouteOutlet` cuando crear y destruir componentes frecuentemente conduce a cuellos de botella de rendimiento en tu aplicación. Se recomienda generalmente permitir el comportamiento predeterminado de crear y destruir componentes durante las transiciones de ruta, ya que esto ayuda a evitar posibles errores y problemas relacionados con el mantenimiento de un estado consistente. Sin embargo, en escenarios donde el rendimiento es crítico y los componentes son complejos o costosos de recrear, `PersistentRouteOutlet` puede ofrecer mejoras significativas al almacenar en caché los componentes y gestionar su visibilidad.
:::

### Ejemplo de implementación de `PersistentRouteOutlet`: {#example-of-persistentrouteoutlet-implementation}

```java
@Route
public class MainLayout extends Composite<AppLayout> implements RouteOutlet {
  PersistentRouteOutlet outlet = new PersistentRouteOutlet(this);

  public MainLayout() {
    setHeader();
    setDrawer();
  }

  @Override
  public void removeRouteContent(Component component) {
    outlet.removeRouteContent(component);
  }

  @Override
  public void showRouteContent(Component component) {
    outlet.showRouteContent(component);
  }
}
```

En este ejemplo, `MainLayout` utiliza `PersistentRouteOutlet` para gestionar sus rutas secundarias. Al navegar entre rutas, los componentes no se eliminan del DOM, sino que se ocultan, asegurando que permanezcan disponibles para una rápida renderización cuando el usuario navega de regreso. Este enfoque mejora significativamente el rendimiento, especialmente para vistas con contenido complejo o uso intensivo de recursos.
