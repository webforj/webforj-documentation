---
sidebar_position: 3
title: Defining Routes
_i18n_hash: 4f7189d5ef27386506e9ecf950f145ed
---
Definir rutas es esencial para mapear URLs a componentes específicos. Esto te permite controlar cómo se representan las diferentes partes de tu interfaz de usuario según la estructura de la URL. El framework utiliza la anotación `@Route` para hacer que este proceso sea declarativo y sencillo, reduciendo la necesidad de configuración manual.

:::info Registro de Rutas
Las rutas se pueden registrar de forma estática utilizando la anotación `@Route` o dinámicamente a través de la API `RouteRegistry`. Para más información, consulta la [documentación sobre el Registro de Rutas](./routes-registration).
:::

## Definiendo una ruta con `@Route` {#defining-a-route-with-route}

La anotación `@Route` se utiliza para vincular una ruta URL a un componente específico. Esto permite que el componente se represente cada vez que la aplicación navega a esa URL. Aquí tienes un ejemplo simple:

```java
@Route(value = "dashboard")
public class DashboardView extends Composite<Div> {
  // Lógica del componente aquí
}
```

En este ejemplo:
- El componente `DashboardView` está vinculado a la URL `/dashboard`.
- Cuando un usuario navega a `/dashboard`, el `DashboardView` será renderizado dinámicamente por el framework.

### El parámetro `value` {#the-value-parameter}

El parámetro `value` en la anotación `@Route` define la ruta URL. Esto puede ser una ruta estática como `"dashboard"` o más dinámica, permitiendo un enrutamiento flexible.

```java
@Route(value = "user/:id")
public class UserView extends Composite<Div> {
  // Lógica del componente aquí
}
```

En este caso, navegar a `/user/123` mostrará el `UserView`.

:::tip Patrones de Ruta
El `user/:id` se conoce como un patrón de ruta. El enrutador puede manejar tanto patrones simples, que coinciden con un solo segmento estático, como patrones complejos, que pueden coincidir con múltiples segmentos estáticos, requeridos y opcionales. Para más información sobre cómo configurar patrones, consulta el [análisis profundo de los patrones de ruta](./route-patterns).
:::

## Definiendo alias de ruta {#defining-route-aliases}

En algunos casos, podrías querer permitir que múltiples URLs apunten al mismo componente. Por ejemplo, podrías querer que los usuarios puedan acceder a su perfil a través de `/profile` o `/user/me`. webforJ permite esto mediante la anotación **`@RouteAlias`**, lo que te permite definir múltiples alias para una sola ruta.

Aquí tienes un ejemplo en el que el componente es accesible tanto a través de `/profile` como de `/user/me`:

```java
@Route(value = "profile")
@RouteAlias("user/me")
public class UserProfileView extends Composite<Div> {
  // Lógica del componente aquí
}
```

Definir alias de ruta aumenta la flexibilidad en el diseño de tu navegación, permitiendo que los usuarios accedan al mismo contenido a través de diferentes URLs.
