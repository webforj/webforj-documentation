---
sidebar_position: 3
title: Security Annotations
_i18n_hash: af9997b8bee96bfa4005a65998fddaf5
---
Las anotaciones de seguridad proporcionan una forma declarativa de controlar el acceso a las rutas en tu aplicación webforJ. Al añadir anotaciones a tus componentes de ruta, defines quién puede acceder a cada vista sin necesidad de realizar comprobaciones de permisos manualmente. El sistema de seguridad aplica automáticamente estas reglas antes de que se renderice cualquier componente.

:::info Nota de implementación
Esta guía funciona con cualquier implementación de seguridad. Los ejemplos mostrados funcionan tanto con Spring Security como con implementaciones personalizadas. Si no estás utilizando Spring Boot, consulta la [guía de Arquitectura de Seguridad](/docs/security/architecture/overview) para entender la base y implementar seguridad personalizada.
:::

## `@AnonymousAccess` - rutas públicas {#anonymousaccess-public-routes}

La anotación `@AnonymousAccess` marca una ruta como accesible públicamente. Los usuarios no necesitan estar autenticados para acceder a estas rutas. Esta anotación se utiliza típicamente para páginas de inicio de sesión, páginas de aterrizaje públicas u otro contenido que debería estar disponible para todos.

```java title="LoginView.java"
@Route("/login")
@AnonymousAccess
public class LoginView extends Composite<Login> {

  public LoginView() {
    // vista de inicio de sesión
  }
}
```

En este ejemplo:
- Cualquier usuario, autenticado o no, puede acceder a la ruta `/login`.
- Cuando `@AnonymousAccess` está presente, a los usuarios no autenticados se les permite acceder a esta página sin ser redirigidos.

:::tip Casos de uso comunes
Utiliza `@AnonymousAccess` para páginas de inicio de sesión, páginas de registro, páginas de inicio públicas, términos de servicio, políticas de privacidad y cualquier otro contenido que debería ser accesible sin autenticación.
:::

## `@PermitAll` - rutas autenticadas {#permitall-authenticated-routes}

La anotación `@PermitAll` requiere que los usuarios estén autenticados, pero no impone requisitos de roles específicos. Cualquier usuario conectado puede acceder a estas rutas, independientemente de sus roles o permisos.

```java title="InboxView.java"
@Route(value = "/", outlet = MainLayout.class)
@PermitAll
public class InboxView extends Composite<FlexLayout> {

  public InboxView() {
    FlexLayout layout = getBoundComponent();
    layout.setHeight("100%");
    layout.add(new Explore("Inbox"));
  }
}
```

En este ejemplo:
- Los usuarios deben estar autenticados para acceder a la bandeja de entrada.
- Cualquier usuario autenticado puede ver esta página, sin importar su rol.
- Los usuarios no autenticados son redirigidos a la página de inicio de sesión.

:::info Modo seguro por defecto
Cuando el modo seguro por defecto está habilitado, las rutas sin ninguna anotación de seguridad se comportan igual que `@PermitAll`: requieren autenticación. Consulta la [sección de seguro por defecto](#secure-by-default) para más detalles.
:::

## `@RolesAllowed` - rutas basadas en roles {#rolesallowed-role-based-routes}

La anotación `@RolesAllowed` restringe el acceso a los usuarios con roles específicos. Puedes especificar uno o más roles, y los usuarios deben tener al menos uno de los roles listados para acceder a la ruta.

### Requisito de un solo rol {#single-role-requirement}

```java title="TrashView.java"
@Route(value = "/trash", outlet = MainLayout.class)
@RolesAllowed("ADMIN")
public class TrashView extends Composite<FlexLayout> {

  public TrashView() {
    FlexLayout layout = getBoundComponent();
    layout.setHeight("100%");
    layout.add(new Explore("Trash"));
  }
}
```

En este ejemplo:
- Solo los usuarios con el rol `ADMIN` pueden acceder a la vista de basura.
- Los usuarios sin el rol `ADMIN` son redirigidos a la página de acceso denegado.

### Requisitos de múltiples roles {#multiple-role-requirements}

```java title="SettingsView.java"
@Route(value = "/settings", outlet = MainLayout.class)
@RolesAllowed({"ADMIN", "MANAGER"})
public class SettingsView extends Composite<FlexLayout> {

  public SettingsView() {
    FlexLayout layout = getBoundComponent();
    layout.add(new Explore("Settings"));
  }
}
```

En este ejemplo:
- Los usuarios con el rol `ADMIN` o `MANAGER` pueden acceder a la configuración.
- El usuario solo necesita uno de los roles listados, no todos ellos.

:::tip Convenciones de nomenclatura de roles
Utiliza nombres de roles en mayúsculas (como `ADMIN`, `USER`, `MANAGER`) para mayor consistencia. Esto coincide con las convenciones comunes de los marcos de seguridad y hace que tu código sea más legible.
:::

## `@DenyAll` - rutas bloqueadas {#denyall-blocked-routes}

La anotación `@DenyAll` bloquea el acceso a una ruta para todos los usuarios, independientemente de su estado de autenticación o roles. Esto es útil para deshabilitar temporalmente rutas durante el mantenimiento o para rutas que están en desarrollo.

```java title="MaintenanceView.java"
@Route(value = "/maintenance", outlet = MainLayout.class)
@DenyAll
public class MaintenanceView extends Composite<FlexLayout> {

  public MaintenanceView() {
    FlexLayout layout = getBoundComponent();
    layout.add(new Paragraph("Esta página está en mantenimiento."));
  }
}
```

En este ejemplo:
- Ningún usuario puede acceder a esta ruta, ni siquiera los administradores.
- Todos los intentos de acceso resultan en una redirección a la página de acceso denegado.

:::warning Uso temporal
`@DenyAll` se utiliza generalmente de forma temporal durante el desarrollo o mantenimiento. Para aplicaciones en producción, considera eliminar la ruta por completo o utilizar restricciones de roles adecuadas en su lugar.
:::

## Qué sucede cuando se niega el acceso {#what-happens-when-access-is-denied}

Cuando un usuario intenta acceder a una ruta a la que no está autorizado, el sistema de seguridad maneja la denegación automáticamente:

1. **Usuarios no autenticados**: Se redirigen a la página de inicio de sesión configurada en tu configuración de seguridad.
2. **Usuarios autenticados sin los roles requeridos**: Se redirigen a la página de acceso denegado.
3. **Todos los usuarios en rutas `@DenyAll`**: Se redirigen a la página de acceso denegado.

Puedes personalizar estas ubicaciones de redirección para que coincidan con la estructura de navegación de tu aplicación, de modo que las denegaciones de acceso y las solicitudes de autenticación lleven a las páginas deseadas. Consulta [Configurar Spring Security](/docs/security/getting-started#configure-spring-security) para detalles de configuración.

## Seguro por defecto {#secure-by-default}

Seguro por defecto es una opción de configuración que determina cómo se tratan las rutas que no tienen ninguna anotación de seguridad. Cuando está habilitado, todas las rutas requieren autenticación de forma predeterminada, a menos que se marquen explícitamente con `@AnonymousAccess`.

### Habilitado (recomendado para producción) {#enabled-recommended-for-production}

Añade esto a tu `application.properties`:

```properties title="application.properties"
webforj.security.secure-by-default=true
```

Con seguro por defecto habilitado:
- Las rutas sin anotaciones requieren autenticación (igual que `@PermitAll`).
- Solo las rutas `@AnonymousAccess` son accesibles públicamente.
- Debes marcar explícitamente las rutas públicas, reduciendo el riesgo de exponer accidentalmente contenido protegido.

```java
// Requiere autenticación (sin anotación)
@Route("/dashboard")
public class DashboardView extends Composite<Div> { }

// Acceso público (marcado explícitamente)
@Route("/about")
@AnonymousAccess
public class AboutView extends Composite<Div> { }
```

### Deshabilitado (permitir por defecto) {#disabled-allow-by-default}

```properties title="application.properties"
webforj.security.secure-by-default=false
```

Con seguro por defecto deshabilitado:
- Las rutas sin anotaciones son accesibles públicamente.
- Debes añadir explícitamente `@PermitAll` o `@RolesAllowed` para proteger las rutas.
- Más fácil para el desarrollo, pero más arriesgado para producción.

```java
// Acceso público (sin anotación)
@Route("/about")
public class AboutView extends Composite<Div> { }

// Requiere autenticación (marcado explícitamente)
@Route("/dashboard")
@PermitAll
public class DashboardView extends Composite<Div> { }
```

:::tip Mejor práctica
Habilita `secure-by-default` para aplicaciones en producción. Con esta configuración, las rutas protegidas no se exponen a menos que se marquen explícitamente como públicas, reduciendo el riesgo de exposición accidental debido a anotaciones faltantes. Solo desactívala durante el desarrollo inicial si encuentras que las anotaciones adicionales son engorrosas.
:::
