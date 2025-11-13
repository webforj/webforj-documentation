---
sidebar_position: 11
title: Registro de Rutas
_i18n_hash: 5075588c497508fc77d7f76b1b412cf4
---
Además de [registrar rutas usando las anotaciones `@Route`](./defining-routes), es posible registrar, actualizar o eliminar rutas de manera dinámica en tiempo de ejecución según la lógica de la aplicación, los roles de usuario u otras condiciones. Esta flexibilidad te permite gestionar la navegación de manera más dinámica, en lugar de definir rutas de forma estática en tiempo de compilación.

## Registrando rutas dinámicamente {#registering-routes-dynamically}

Puedes registrar una ruta dinámicamente utilizando la clase `RouteRegistry`, que es accesible a través del `Router`. Esto te permite agregar nuevas rutas durante el tiempo de ejecución, habilitando una navegación flexible.

### Ejemplo: Registrando una ruta dinámica {#example-registering-a-dynamic-route}

```java
Router router = Router.getCurrent();
RouteRegistry registry = router.getRegistry();

// Registrar la ruta de configuración dinámicamente
registry.register("/settings", SettingsView.class);

// Navegar a la vista de configuración
router.navigate(SettingsView.class);
```

En este ejemplo, la ruta `/settings` se registra dinámicamente y la aplicación navega a la vista recién registrada.

## Registro condicional de rutas {#conditional-route-registration}

A menudo, las rutas necesitan ser añadidas o eliminadas según condiciones específicas como los roles de usuario o el estado de la aplicación. Con el enrutamiento dinámico, puedes registrar o anular el registro de rutas condicionalmente en tiempo de ejecución.

### Ejemplo: registro condicional basado en el rol de usuario {#example-conditional-registration-based-on-user-role}

```java
Router router = Router.getCurrent();
RouteRegistry registry = router.getRegistry();

// Verificar rol de usuario y registrar rutas apropiadas
if (user.hasRole("editor")) {
    registry.register("/editor/dashboard", EditorDashboardView.class);
} else if (user.hasRole("viewer")) {
    registry.register("/viewer/dashboard", ViewerDashboardView.class);
}

// Navegar al tablero apropiado
if (user.hasRole("editor")) {
    router.navigate(EditorDashboardView.class);
} else if (user.hasRole("viewer")) {
    router.navigate(ViewerDashboardView.class);
}
```

En este ejemplo:
- La ruta `/editor/dashboard` o `/viewer/dashboard` se registra dinámicamente según el rol del usuario.
- La aplicación navega al tablero apropiado basado en los derechos de acceso del usuario.

## Eliminando rutas {#removing-routes}

Así como las rutas pueden ser añadidas dinámicamente, también pueden ser eliminadas en tiempo de ejecución cuando ya no son necesarias, o cuando el contexto de la aplicación cambia.

### Ejemplo: Eliminando una ruta registrada {#example-removing-a-registered-route}

```java
Router router = Router.getCurrent();
RouteRegistry registry = router.getRegistry();

// Eliminar la ruta de la vista de configuración
registry.unregister("/settings");

// Opcionalmente, eliminar por clase de componente
registry.unregister(SettingsView.class);
```

En este ejemplo, la ruta `/settings` se elimina dinámicamente cuando ya no es requerida.

## Registrando rutas al inicio de la aplicación {#registering-routes-at-app-startup}

Puedes registrar rutas dinámicas durante la inicialización de la aplicación, permitiendo que ciertas vistas estén disponibles según el entorno o la configuración al inicio.

### Ejemplo: Registrando rutas durante el inicio de la aplicación {#example-registering-routes-during-app-startup}

```java
@Routify
public class Application extends App {

  @Override
  protected void onWillRun() {
    // Registrar una vista de depuración solo en modo de desarrollo
    if (Environment.getCurrent().isDebug()) {
      Router router = Router.getCurrent();
      RouteRegistry registry = router.getRegistry();

      registry.register("/debug", DebugView.class);
    }
  }
}
```

En este ejemplo:
- Una `DebugView` se registra dinámicamente durante el inicio de la aplicación, pero solo si la aplicación está ejecutándose en modo de desarrollo.

## Registrando componentes anotados con `@Route` dinámicamente {#registering-route-annotated-components-dynamically}

Además de definir rutas manualmente, es posible registrar dinámicamente componentes ya anotados con `@Route`. Esto es útil cuando quieres aprovechar clases pre-anotadas pero registrarlas dinámicamente según la lógica de la aplicación.

### Ejemplo: Registrando un componente anotado con `@Route` {#example-registering-an-route-annotated-component}

```java
@Route("profile")
public class ProfileView extends Composite<Div> {
    // Lógica de vista de perfil
}

Router router = Router.getCurrent();
RouteRegistry registry = router.getRegistry();

// Registrar dinámicamente el ProfileView con su anotación @Route
registry.register(ProfileView.class);

// Navegar a la ProfileView
router.navigate(ProfileView.class);
```

En este ejemplo:
- La clase `ProfileView` está anotada con `@Route("profile")`.
- La ruta se registra dinámicamente en tiempo de ejecución usando `registry.register(ProfileView.class)`.

## Registrando rutas desde un paquete entero {#registering-routes-from-an-entire-package}

Si tu aplicación tiene un gran número de rutas organizadas dentro de un paquete, puedes registrar todos los componentes anotados con `@Route` desde el paquete de manera dinámica.

### Ejemplo: Registrando todas las rutas desde un paquete {#example-registering-all-routes-from-a-package}

```java
Router router = Router.getCurrent();
RouteRegistry registry = router.getRegistry();

// Registrar todas las rutas dentro del paquete "com.myapp.admin"
RouteRegistry.ofPackage(new String[] { "com.myapp.admin" }, registry);
```

En este ejemplo:
- El método `ofPackage` escanea el paquete `com.myapp.admin` y registra todas las clases anotadas con `@Route`.
- Esto es particularmente útil para aplicaciones grandes con numerosas rutas organizadas por paquetes.

## Recuperando rutas registradas {#retrieving-registered-routes}

Para recuperar una lista de todas las rutas registradas dinámicamente, utiliza la clase `RouteRegistry`. Esto es útil cuando necesitas gestionar o mostrar programáticamente las rutas disponibles.

### Ejemplo: Recuperando y mostrando todas las rutas registradas {#example-retrieving-and-displaying-all-registered-routes}

```java
Router router = Router.getCurrent();
RouteRegistry registry = router.getRegistry();

List<RouteEntry> routes = registry.getAvailableRouteEntires();
routes.forEach(route -> console().log("Path: " + route.getPath()));
```

En este ejemplo, la aplicación recupera todas las rutas actualmente registradas y muestra sus rutas.

## Gestionando alias de rutas dinámicamente {#managing-route-aliases-dynamically}

webforJ te permite registrar múltiples alias para una sola vista. Esto significa que los usuarios pueden acceder a la misma vista utilizando diferentes rutas URL.

### Ejemplo: Registrando alias de rutas dinámicamente {#example-registering-route-aliases-dynamically}

```java
Router router = Router.getCurrent();
RouteRegistry registry = router.getRegistry();

// Registrar una ruta principal
registry.register("/contact", ContactView.class);

// Registrar alias para la vista de contacto
registry.register("/support", ContactView.class);
registry.register("/help", ContactView.class);
```

En este ejemplo, la `ContactView` es accesible a través de tres rutas diferentes: `/contact`, `/support` y `/help`.
