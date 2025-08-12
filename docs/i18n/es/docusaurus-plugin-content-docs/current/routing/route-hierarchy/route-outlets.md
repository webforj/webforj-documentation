---
sidebar_position: 3
title: Route Outlets
_i18n_hash: bab7ef02dabbb653741f7c8176913213
---
Un **outlet** es un componente designado, ya sea un [diseño de ruta](./route-types#layout-routes) o una [vista de ruta](./route-types#view-routes), donde se renderizan dinámicamente las rutas secundarias. Define dónde aparecerá el contenido de la ruta secundaria dentro de la ruta principal. Los outlets son fundamentales para crear interfaces de usuario modulares y anidadas y estructuras de navegación flexibles.

## Definición de un outlet {#defining-an-outlet}

Los outlets suelen implementarse utilizando componentes contenedores que pueden mantener y gestionar contenido secundario. En webforJ, cualquier componente que implemente la interfaz `HasComponents`, o una composición de tales componentes, puede servir como un outlet. Por ejemplo, [`FlexLayout`](../../components/flex-layout) implementa la interfaz `HasComponents`, lo que lo convierte en un outlet válido para rutas secundarias.

Si no se define explícitamente un outlet para una ruta, se utiliza el primer `Frame` de la aplicación como el outlet predeterminado. Este comportamiento asegura que cada ruta secundaria tenga un lugar para ser renderizada.

:::tip Gestión de Frame
En aplicaciones con múltiples frames, puedes especificar qué frame usar como el outlet para las rutas secundarias configurando el atributo `frame` en la anotación `@Route`. El atributo `frame` acepta el nombre del frame que se utilizará para renderizar.
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
  public DashboardView() {
    getBoundComponent().add(new H1("Contenido del Dashboard"));
  }
}
```

En este ejemplo:

- `MainLayout` actúa como el contenedor del diseño, pero dado que no se define un outlet específico, se utiliza el `Frame` predeterminado de la aplicación.
- La `DashboardView` se renderiza dentro de `MainLayout` utilizando el outlet predeterminado (área de contenido) del `AppLayout`.

Así, las rutas secundarias de `MainLayout` se renderizarán automáticamente en la ranura de contenido del `AppLayout`, a menos que se especifique un outlet o frame diferente.

## Ciclo de vida del outlet {#outlet-lifecycle}

Los outlets están estrechamente relacionados con el ciclo de vida de las rutas. Cuando cambia la ruta activa, el outlet actualiza su contenido dinámicamente al inyectar el componente secundario apropiado y eliminar cualquier componente que ya no se necesite. Esto asegura que solo las vistas relevantes se rendericen en un momento dado.

- **Creación**: Los outlets se inicializan antes de que se creen los componentes secundarios.
- **Inyección de contenido**: Cuando se encuentra una ruta secundaria, su componente se inyecta en el outlet.
- **Actualización**: Al navegar entre rutas, el outlet actualiza su contenido, inyectando el nuevo componente secundario y eliminando cualquier componente obsoleto.

## Outlets personalizados {#custom-outlets}

La interfaz `RouteOutlet` es responsable de gestionar el ciclo de vida de los componentes de ruta, determinando cómo se renderizan y se eliminan los componentes. Cualquier componente que implemente esta interfaz puede actuar como un outlet para otros componentes.

### Métodos clave en `RouteOutlet`: {#key-methods-in-routeoutlet}

- **`showRouteContent(Component component)`**: Responsable de renderizar el componente provisto en el outlet. Esto se llama cuando el enrutador coincide con una ruta y el componente secundario necesita ser mostrado.
- **`removeRouteContent(Component component)`**: Maneja la eliminación del componente del outlet, generalmente llamado al navegar lejos de la ruta actual.

Al implementar `RouteOutlet`, los desarrolladores pueden controlar cómo se inyectan las rutas en áreas específicas de la aplicación. Por ejemplo:

```java
import com.webforj.router.RouteOutlet;

public class MainLayout extends Composite<AppLayout> implements RouteOutlet {

  @Override
  public void showRouteContent(Component component) {
    AppLayout layout = getBoundComponent();
    layout.addToDrawer(component);
  }

  @Override
  public void removeRouteContent(Component component) {
    AppLayout layout = getBoundComponent();
    layout.remove(component);
  }
}
```

En este ejemplo, la clase `MainLayout` implementa la interfaz `RouteOutlet`, permitiendo que los componentes se añadan o eliminen del cajón de `AppLayout` dinámicamente según la navegación de ruta en lugar del área de contenido predeterminada definida en el componente `AppLayout`.

## Componentes de outlet en caché {#caching-outlet-components}

Por defecto, los outlets añaden y eliminan componentes dinámicamente al navegar a y desde las rutas. Sin embargo, en ciertos casos—particularmente para vistas con componentes complejos—puede ser preferible alternar la visibilidad de los componentes en lugar de eliminarlos completamente del DOM. Aquí es donde entra en juego el `PersistentRouteOutlet`, permitiendo que los componentes permanezcan en memoria y simplemente se oculten o muestren, en lugar de ser destruidos y recreados.

El `PersistentRouteOutlet` almacena en caché los componentes renderizados, manteniéndolos en memoria cuando el usuario navega lejos. Esto mejora el rendimiento al evitar la destrucción y recreación innecesarias de componentes, lo cual es especialmente beneficioso para aplicaciones donde los usuarios cambian con frecuencia entre vistas.

### Cómo funciona `PersistentRouteOutlet`: {#how-persistentrouteoutlet-works}

- **Caché de componentes**: Mantiene una caché en memoria de todos los componentes que han sido renderizados dentro del outlet.
- **Alternar visibilidad**: En lugar de eliminar los componentes del DOM, los oculta al navegar lejos de una ruta.
- **Restauración de componentes**: Cuando el usuario navega de regreso a una ruta previamente almacenada en caché, el componente simplemente se muestra nuevamente sin necesidad de recreación.

Este comportamiento es particularmente útil para UIs complejas donde la constante re-renderización de componentes puede degradar el rendimiento. Sin embargo, para que esta alternancia de visibilidad funcione, los componentes gestionados deben implementar la interfaz `HasVisibility`, que permite al `PersistentRouteOutlet` controlar su visibilidad.

:::tip Cuándo usar `PersistentRouteOutlet`
Usa `PersistentRouteOutlet` cuando la creación y destrucción frecuente de componentes conduce a cuellos de botella de rendimiento en tu aplicación. Generalmente se recomienda permitir el comportamiento predeterminado de crear y destruir componentes durante las transiciones de ruta, ya que esto ayuda a evitar posibles errores y problemas relacionados con el mantenimiento de un estado consistente. Sin embargo, en escenarios donde el rendimiento es crítico y los componentes son complejos o costosos de recrear, `PersistentRouteOutlet` puede ofrecer mejoras significativas al almacenar en caché los componentes y gestionar su visibilidad.
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

En este ejemplo, `MainLayout` utiliza `PersistentRouteOutlet` para gestionar sus rutas secundarias. Al navegar entre rutas, los componentes no se eliminan del DOM sino que se ocultan, asegurando que permanezcan disponibles para un rápido re-renderizado cuando el usuario navega de regreso. Este enfoque mejora significativamente el rendimiento, especialmente para vistas con contenido complejo o un alto consumo de recursos.
