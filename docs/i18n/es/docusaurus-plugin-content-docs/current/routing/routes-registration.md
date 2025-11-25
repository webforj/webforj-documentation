---
sidebar_position: 11
title: Routes Registration
_i18n_hash: 0fadade88e7248bc679d489ed50b537d
---
Además de [registrar rutas usando las anotaciones `@Route`](./defining-routes), es posible registrar, actualizar o eliminar rutas de manera dinámica en tiempo de ejecución en función de la lógica de la aplicación, los roles de usuario u otras condiciones. Esta flexibilidad te permite gestionar la navegación de manera más dinámica, en lugar de definir las rutas de forma estática en el tiempo de compilación.

## Registrando rutas de manera dinámica {#registering-routes-dynamically}

Puedes registrar una ruta de manera dinámica utilizando la clase `RouteRegistry`, que es accesible a través del `Router`. Esto te permite agregar nuevas rutas durante el tiempo de ejecución, lo que habilita una navegación flexible.

### Ejemplo: Registrando una ruta dinámica {#example-registering-a-dynamic-route}

```java
Router router = Router.getCurrent();
RouteRegistry registry = router.getRegistry();

// Registra la ruta de configuración de manera dinámica
registry.register("/settings", SettingsView.class);

// Navega a la vista de configuración
router.navigate(SettingsView.class);
```

En este ejemplo, la ruta `/settings` es registrada de manera dinámica, y la aplicación navega a la vista recién registrada.

## Registro de rutas condicional {#conditional-route-registration}

A menudo, es necesario agregar o eliminar rutas en función de condiciones específicas, como los roles de usuario o el estado de la aplicación. Con el enrutamiento dinámico, puedes registrar o anular el registro de rutas de forma condicional en tiempo de ejecución.

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

// Navega al panel apropiado
if (user.hasRole("editor")) {
    router.navigate(EditorDashboardView.class);
} else if (user.hasRole("viewer")) {
    router.navigate(ViewerDashboardView.class);
}
```

En este ejemplo:
- La ruta `/editor/dashboard` o `/viewer/dashboard` se registra de manera dinámica en función del rol del usuario.
- La aplicación navega al panel apropiado basado en los derechos de acceso del usuario.

## Eliminando rutas {#removing-routes}

Así como las rutas pueden ser agregadas de manera dinámica, también pueden ser eliminadas en tiempo de ejecución cuando ya no son necesarias o cuando cambia el contexto de la aplicación.

### Ejemplo: Eliminando una ruta registrada {#example-removing-a-registered-route}

```java
Router router = Router.getCurrent();
RouteRegistry registry = router.getRegistry();

// Elimina la ruta para la vista de configuración
registry.unregister("/settings");

// Opcionalmente, elimina por clase de componente
registry.unregister(SettingsView.class);
```

En este ejemplo, la ruta `/settings` es eliminada de manera dinámica cuando ya no es requerida.

## Registrando rutas al inicio de la aplicación {#registering-routes-at-app-startup}

Puedes registrar rutas dinámicas durante la inicialización de la aplicación, permitiendo que ciertas vistas estén disponibles en función del entorno o la configuración al inicio.

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
- Una `DebugView` es registrada de manera dinámica durante el inicio de la aplicación, pero solo si la aplicación está en modo de desarrollo.

## Registrando componentes anotados con `@Route` de forma dinámica {#registering-route-annotated-components-dynamically}

Además de definir rutas manualmente, es posible registrar dinámicamente componentes que ya están anotados con `@Route`. Esto es útil cuando deseas aprovechar clases previamente anotadas pero registrarlas dinámicamente en función de la lógica de la aplicación.

### Ejemplo: Registrando un componente anotado con `@Route` {#example-registering-an-route-annotated-component}

```java
@Route("profile")
public class ProfileView extends Composite<Div> {
    // Lógica de vista de perfil
}

Router router = Router.getCurrent();
RouteRegistry registry = router.getRegistry();

// Registra dinámicamente el ProfileView con su anotación @Route
registry.register(ProfileView.class);

// Navega a la ProfileView
router.navigate(ProfileView.class);
```

En este ejemplo:
- La clase `ProfileView` está anotada con `@Route("profile")`.
- La ruta es registrada de manera dinámica en tiempo de ejecución usando `registry.register(ProfileView.class)`.

## Registrando rutas desde un paquete entero {#registering-routes-from-an-entire-package}

Si tu aplicación tiene un gran número de rutas organizadas dentro de un paquete, puedes registrar todos los componentes anotados con `@Route` desde el paquete de manera dinámica.

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
A partir de 25.11, los frameworks de integración pueden proporcionar su propio mecanismo de descubrimiento de rutas a través del SPI `RouteRegistryProvider`. Esto permite características específicas del framework como la inyección de dependencias para rutas registradas dinámicamente. Consulta [Proveedor de Registro de Rutas](/docs/advanced/route-registry-provider) para más detalles.
:::

## Recuperando rutas registradas {#retrieving-registered-routes}

Para recuperar una lista de todas las rutas registradas de manera dinámica, utiliza la clase `RouteRegistry`. Esto es útil cuando necesitas gestionar o mostrar programáticamente las rutas disponibles.

### Ejemplo: Recuperando y mostrando todas las rutas registradas {#example-retrieving-and-displaying-all-registered-routes}

```java
Router router = Router.getCurrent();
RouteRegistry registry = router.getRegistry();

List<RouteEntry> routes = registry.getAvailableRouteEntires();
routes.forEach(route -> console().log("Ruta: " + route.getPath()));
```

En este ejemplo, la aplicación recupera todas las rutas actualmente registradas y imprime sus rutas.

## Gestionando alias de rutas de manera dinámica {#managing-route-aliases-dynamically}

webforJ te permite registrar múltiples alias para una única vista. Esto significa que los usuarios pueden acceder a la misma vista utilizando diferentes rutas URL.

### Ejemplo: Registrando alias de rutas de manera dinámica {#example-registering-route-aliases-dynamically}

```java
Router router = Router.getCurrent();
RouteRegistry registry = router.getRegistry();

// Registra una ruta principal
registry.register("/contact", ContactView.class);

// Registra alias para la vista de contacto
registry.register("/support", ContactView.class);
registry.register("/help", ContactView.class);
```

En este ejemplo, la `ContactView` es accesible a través de tres rutas diferentes: `/contact`, `/support`, y `/help`.
