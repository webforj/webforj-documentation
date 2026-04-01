---
sidebar_position: 11
title: Routes Registration
_i18n_hash: def139d3db58322c269afef10acdf5fd
---
Además de [registrar rutas utilizando las anotaciones `@Route`](./defining-routes), es posible registrar, actualizar o eliminar rutas dinámicamente en tiempo de ejecución según la lógica de la aplicación, los roles de usuario u otras condiciones. Esta flexibilidad permite gestionar la navegación de forma más dinámica, en lugar de definir rutas de manera estática en el momento de la compilación.

## Registrando rutas dinámicamente {#registering-routes-dynamically}

Puedes registrar una ruta dinámicamente utilizando la clase `RouteRegistry`, que es accesible a través del `Router`. Esto te permite añadir nuevas rutas durante el tiempo de ejecución, habilitando una navegación flexible.

### Ejemplo: Registrando una ruta dinámica {#example-registering-a-dynamic-route}

```java
Router router = Router.getCurrent();
RouteRegistry registry = router.getRegistry();

// Registra la ruta de configuración dinámicamente
registry.register("/settings", SettingsView.class);

// Navega a la vista de configuración
router.navigate(SettingsView.class);
```

En este ejemplo, la ruta `/settings` se registra dinámicamente, y la aplicación navega a la vista recién registrada.

## Registro de rutas condicional {#conditional-route-registration}

A menudo, es necesario añadir o eliminar rutas según condiciones específicas, como roles de usuario o el estado de la aplicación. Con la enrutación dinámica, puedes registrar o cancelar rutas condicionalmente en tiempo de ejecución.

### Ejemplo: registro condicional basado en el rol del usuario {#example-conditional-registration-based-on-user-role}

```java
Router router = Router.getCurrent();
RouteRegistry registry = router.getRegistry();

// Verifica el rol del usuario y registra las rutas apropiadas
if (user.hasRole("editor")) {
  registry.register("/editor/dashboard", EditorDashboardView.class);
} else if (user.hasRole("viewer")) {
  registry.register("/viewer/dashboard", ViewerDashboardView.class);
}

// Navega al tablero apropiado
if (user.hasRole("editor")) {
  router.navigate(EditorDashboardView.class);
} else if (user.hasRole("viewer")) {
  router.navigate(ViewerDashboardView.class);
}
```

En este ejemplo:
- La ruta `/editor/dashboard` o `/viewer/dashboard` se registra dinámicamente según el rol del usuario.
- La aplicación navega al tablero apropiado según los derechos de acceso del usuario.

## Eliminando rutas {#removing-routes}

Al igual que las rutas pueden añadirse dinámicamente, también pueden eliminarse en tiempo de ejecución cuando ya no son necesarias, o cuando cambia el contexto de la aplicación.

### Ejemplo: Eliminando una ruta registrada {#example-removing-a-registered-route}

```java
Router router = Router.getCurrent();
RouteRegistry registry = router.getRegistry();

// Elimina la ruta para la vista de configuración
registry.unregister("/settings");

// Opcionalmente, eliminar por clase del componente
registry.unregister(SettingsView.class);
```

En este ejemplo, la ruta `/settings` se elimina dinámicamente cuando ya no se requiere.

## Registrando rutas al inicio de la aplicación {#registering-routes-at-app-startup}

Puedes registrar rutas dinámicas durante la inicialización de la aplicación, permitiendo que ciertas vistas estén disponibles según el entorno o la configuración al inicio.

### Ejemplo: Registrando rutas durante el inicio de la aplicación {#example-registering-routes-during-app-startup}

```java
@Routify
public class Application extends App {

  @Override
  protected void onWillRun() {
    // Registra una vista de depuración solo en modo de desarrollo
    if (Environment.getCurrent().isDebug()) {
      Router router = Router.getCurrent();
      RouteRegistry registry = router.getRegistry();

      registry.register("/debug", DebugView.class);
    }
  }
}
```

En este ejemplo:
- Una `DebugView` se registra dinámicamente al inicio de la aplicación, pero solo si la aplicación se está ejecutando en modo de desarrollo.

## Registrando componentes anotados con `@Route` dinámicamente {#registering-route-annotated-components-dynamically}

Además de definir rutas manualmente, es posible registrar dinámicamente componentes que ya están anotados con `@Route`. Esto es útil cuando deseas aprovechar clases pre-anotadas pero registrarlas dinámicamente según la lógica de la aplicación.

### Ejemplo: Registrando un componente anotado con `@Route` {#example-registering-an-route-annotated-component}

```java
@Route("profile")
public class ProfileView extends Composite<Div> {
  // Lógica de la vista de perfil
}

Router router = Router.getCurrent();
RouteRegistry registry = router.getRegistry();

// Registra dinámicamente el ProfileView con su anotación @Route
registry.register(ProfileView.class);

// Navega hacia ProfileView
router.navigate(ProfileView.class);
```

En este ejemplo:
- La clase `ProfileView` está anotada con `@Route("profile")`.
- La ruta se registra dinámicamente en tiempo de ejecución utilizando `registry.register(ProfileView.class)`.

## Registrando rutas de un paquete entero {#registering-routes-from-an-entire-package}

Si tu aplicación tiene un gran número de rutas organizadas dentro de un paquete, puedes registrar todos los componentes anotados con `@Route` del paquete dinámicamente.

### Ejemplo: Registrando todas las rutas de un paquete {#example-registering-all-routes-from-a-package}

```java
Router router = Router.getCurrent();
RouteRegistry registry = router.getRegistry();

// Registra todas las rutas dentro del paquete "com.myapp.admin"
RouteRegistry.ofPackage(new String[] { "com.myapp.admin" }, registry);
```

En este ejemplo:
- El método `ofPackage` escanea el paquete `com.myapp.admin` y registra todas las clases anotadas con `@Route`.
- Esto es particularmente útil para aplicaciones grandes con numerosas rutas organizadas por paquetes.

:::info Descubrimiento de rutas personalizado
A partir de la versión 25.11, los frameworks de integración pueden proporcionar su propio mecanismo de descubrimiento de rutas a través del SPI `RouteRegistryProvider`. Esto permite características específicas del framework como la inyección de dependencias para rutas registradas dinámicamente. Consulta [Proveedor de Registro de Rutas](/docs/advanced/route-registry-provider) para más detalles.
:::

## Recuperando rutas registradas {#retrieving-registered-routes}

Para recuperar una lista de todas las rutas registradas dinámicamente, utiliza la clase `RouteRegistry`. Esto es útil cuando necesitas gestionar o mostrar programáticamente las rutas disponibles.

### Ejemplo: Recuperando y mostrando todas las rutas registradas {#example-retrieving-and-displaying-all-registered-routes}

```java
Router router = Router.getCurrent();
RouteRegistry registry = router.getRegistry();

List<RouteEntry> routes = registry.getAvailableRouteEntires();
routes.forEach(route -> console().log("Path: " + route.getPath()));
```

En este ejemplo, la aplicación recupera todas las rutas actualmente registradas y imprime sus caminos.

## Gestionando alias de rutas dinámicamente {#managing-route-aliases-dynamically}

webforJ te permite registrar múltiples alias para una única vista. Esto significa que los usuarios pueden acceder a la misma vista usando diferentes rutas URL.

### Ejemplo: Registrando alias de rutas dinámicamente {#example-registering-route-aliases-dynamically}

```java
Router router = Router.getCurrent();
RouteRegistry registry = router.getRegistry();

// Registra una ruta principal
registry.register("/contact", ContactView.class);

// Registra alias para la vista de contacto
registry.register("/support", ContactView.class);
registry.register("/help", ContactView.class);
```

En este ejemplo, el `ContactView` es accesible a través de tres rutas diferentes: `/contact`, `/support` y `/help`.
