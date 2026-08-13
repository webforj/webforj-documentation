---
sidebar_position: 6
title: Custom Evaluators
description: >-
  Write custom RouteSecurityEvaluators for context-aware checks like ownership
  verification beyond role-based permissions.
_i18n_hash: d1edb7260efb2928e988a2cdf313f380
---
Los evaluadores personalizados extienden el sistema de seguridad de webforJ con lógica de control de acceso especializada más allá de la autenticación básica y las verificaciones de rol. Úselos cuando necesite verificar condiciones dinámicas que dependen del contexto de solicitud, y no solo de los permisos del usuario.

:::info Enfoque de Spring Security
Esta guía cubre evaluadores personalizados para Spring Security. Si no está utilizando Spring Boot, consulte la [guía de Cadena de Evaluadores](/docs/security/architecture/evaluator-chain) para entender cómo funcionan los evaluadores y [Implementación Completa](/docs/security/architecture/custom-implementation) para un ejemplo en funcionamiento.
:::

## Qué son los evaluadores personalizados {#what-are-custom-evaluators}

Un evaluador determina si un usuario puede acceder a una ruta específica basada en lógica personalizada. Los evaluadores se verifican durante la navegación antes de que se renderice cualquier componente, lo que le permite interceptar y controlar el acceso dinámicamente.

webforJ incluye evaluadores integrados para las anotaciones estándar de Jakarta:

- `AnonymousAccessEvaluator` - Maneja `@AnonymousAccess`
- `PermitAllEvaluator` - Maneja `@PermitAll`
- `RolesAllowedEvaluator` - Maneja `@RolesAllowed`
- `DenyAllEvaluator` - Maneja `@DenyAll`

Los evaluadores personalizados siguen el mismo patrón, lo que le permite crear sus propias anotaciones y lógica de control de acceso.

:::tip[Aprender más sobre anotaciones integradas]
Para conocer los detalles sobre `@AnonymousAccess`, `@PermitAll`, `@RolesAllowed` y `@DenyAll`, consulte la [guía de Anotaciones de Seguridad](/docs/security/annotations).
:::

## Caso de uso: Verificación de propiedad {#use-case-ownership-verification}

Un requisito común es permitir que los usuarios accedan solo a sus propios recursos. Por ejemplo, los usuarios solo deberían poder editar su propio perfil, no el perfil de otra persona.

**El problema**: `@RolesAllowed("USER")` otorga acceso a todos los usuarios autenticados, pero no verifica si el usuario está accediendo a su propio recurso. Necesita comparar el ID de usuario del usuario conectado con el ID del recurso en la URL.

**Escenario de ejemplo:**
- El ID de usuario `123` está conectado
- Navega a `/users/456/edit`
- ¿Deberían acceder a esta página? **NO** - solo pueden editar `/users/123/edit`

No puede resolver esto con roles porque depende del parámetro de ruta `:userId`, que cambia para cada solicitud.

### Creando una anotación personalizada {#creating-a-custom-annotation}

Defina una anotación para marcar rutas que requieren verificación de propiedad:

```java title="RequireOwnership.java"
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequireOwnership {
  /**
   * El nombre del parámetro de ruta que contiene el ID del usuario.
   */
  String value() default "userId";
}
```

Utilícela en las rutas que requieren verificaciones de propiedad:

```java title="EditProfileView.java"
@Route(value = "/users/:userId/edit", outlet = MainLayout.class)
@RequireOwnership("userId")
public class EditProfileView extends Composite<Div> {
  private final Div self = getBoundComponent();

  public EditProfileView() {
    self.setText("Página de Edición de Perfil");
  }
}
```

### Implementando el evaluador {#implementing-the-evaluator}

Cree un evaluador gestionado por Spring que compare el ID del usuario conectado con el parámetro de ruta:

```java title="OwnershipEvaluator.java"
@RegisteredEvaluator(priority = 10)
public class OwnershipEvaluator implements RouteSecurityEvaluator {

  @Override
  public boolean supports(Class<?> routeClass) {
    return routeClass.isAnnotationPresent(RequireOwnership.class);
  }

  @Override
  public RouteAccessDecision evaluate(Class<?> routeClass, NavigationContext context,
      RouteSecurityContext securityContext, SecurityEvaluatorChain chain) {

    // Primero verifica la autenticación
    if (!securityContext.isAuthenticated()) {
      return RouteAccessDecision.denyAuthentication();
    }

    // Obtiene la anotación
    RequireOwnership annotation = routeClass.getAnnotation(RequireOwnership.class);
    String paramName = annotation.value();

    // Obtiene el ID del usuario conectado desde el contexto de seguridad
    String currentUserId = securityContext.getPrincipal()
        .filter(p -> p instanceof UserDetails)
        .map(p -> ((UserDetails) p).getUsername())
        .orElse(null);

    // Obtiene :userId de los parámetros de ruta
    String requestedUserId = context.getRouteParameters()
        .get(paramName)
        .orElse(null);

    // Verifica si coinciden
    if (currentUserId != null && currentUserId.equals(requestedUserId)) {
      // Propiedad verificada - continúa la cadena para permitir otros evaluadores
      return chain.evaluate(routeClass, context, securityContext);
    }

    return RouteAccessDecision.deny("Solo puede acceder a sus propios recursos");
  }
}
```

Spring descubre y registra automáticamente los evaluadores anotados con `@RegisteredEvaluator`.

### Cómo funciona {#how-it-works}

La implementación del evaluador tiene dos métodos clave:

#### `supports(Class<?> routeClass)` {#supports-method}

- Devuelve `true` si este evaluador debe manejar la ruta.
- Solo se invocan los evaluadores que devuelven `true` para la ruta.
- Filtra rutas verificando la anotación `@RequireOwnership`.

#### `evaluate(...)` {#evaluate-method}

- Primero verifica si el usuario está autenticado.
- Obtiene el ID del usuario conectado desde `securityContext.getPrincipal()`.
- Obtiene el valor del parámetro de ruta desde `context.getRouteParameters().get(paramName)`.
- Compara los dos IDs.
- Si coinciden, delega a `chain.evaluate()` para permitir que otros evaluadores se ejecuten.
- Si no coinciden, devuelve `deny()` con una razón.

### Ejemplo de flujo {#flow-example}

**Cuando la verificación de propiedad falla:**

1. El usuario `123` inicia sesión y navega a `/users/456/edit`.
2. `OwnershipEvaluator.supports()` devuelve `true` (la ruta tiene `@RequireOwnership`).
3. `OwnershipEvaluator.evaluate()` se ejecuta:
   - `currentUserId = "123"` (del contexto de seguridad).
   - `requestedUserId = "456"` (del parámetro de ruta `:userId`).
   - `"123".equals("456")` → `false`.
   - Devuelve `RouteAccessDecision.deny("Solo puede acceder a sus propios recursos")`.
4. El usuario es redirigido a la página de acceso denegado.

**Cuando la verificación de propiedad pasa:**

1. El usuario `123` inicia sesión y navega a `/users/123/edit`.
2. `OwnershipEvaluator.evaluate()` se ejecuta:
   - `currentUserId = "123"`, `requestedUserId = "123"`.
   - Los IDs coinciden → llama a `chain.evaluate()` para continuar.
3. Si ningún otro evaluador deniega el acceso, se concede acceso al usuario.

## Entendiendo la cadena de evaluadores {#understanding-the-evaluator-chain}

El sistema de seguridad utiliza un **patrón de cadena de responsabilidad** donde los evaluadores se procesan en orden de prioridad. Los evaluadores pueden tomar decisiones terminales o delegar a la cadena para combinar múltiples verificaciones.

### Cómo funciona la cadena {#how-chain-works}

1. Los evaluadores se ordenan por prioridad (primeramente los números más bajos).
2. Para cada evaluador, se llama a `supports(routeClass)` para verificar si se aplica.
3. Si `supports()` devuelve `true`, se llama al método `evaluate()` del evaluador.
4. El evaluador puede:
   - **Devolver una decisión terminal** (`grant()` o `deny()`) - **detiene la cadena**.
   - **Delegar a la cadena** llamando a `chain.evaluate()` - **permite que otros evaluadores se ejecuten**.
5. Si la cadena completa sin una decisión y la lógica de seguro por defecto está habilitada, se deniega el acceso a los usuarios no autenticados.

### Decisiones terminales {#terminal-decisions}

Detiene la cadena inmediatamente:

#### `RouteAccessDecision.grant()` {#routeaccessdecisiongrant}

- Concede acceso y detiene la evaluación posterior.
- Usado por `@AnonymousAccess` y `@PermitAll` - estas son autorizaciones completas que no se combinan con otras verificaciones.

#### `RouteAccessDecision.deny(reason)` {#routeaccessdecisiondenyreason}

- Deniega el acceso y detiene la evaluación posterior.
- Usado por `@DenyAll` y cuando las verificaciones personalizadas fallan.
- Ejemplo: `RouteAccessDecision.deny("Solo puede acceder a sus propios recursos")`.

#### `RouteAccessDecision.denyAuthentication()` {#routeaccessdecisiondenyauthentication}

- Redirige a la página de inicio de sesión.
- Usado cuando se requiere autenticación pero falta.

### Delegación de cadena {#chain-delegation}

Permite combinar verificaciones:

#### `chain.evaluate(routeClass, context, securityContext)` {#chainevaluaterouteclass-context-securitycontext}

- Pasa el control al siguiente evaluador en la cadena.
- Permite combinar múltiples verificaciones de autorización.
- Usado por `@RolesAllowed` y `@RouteAccess` después de que sus verificaciones pasan.
- Los evaluadores personalizados deben usar este patrón cuando las verificaciones pasan para permitir la composición.

## Prioridad del evaluador {#evaluator-priority}

Los evaluadores se verifican en orden de prioridad (primeramente los números más bajos). Los evaluadores del marco utilizan prioridades del 1 al 9, los evaluadores personalizados deben utilizar 10 o más.

Los evaluadores integrados se registran en este orden:

```java
// Prioridad 1: @DenyAll - bloquea todo
// Prioridad 2: @AnonymousAccess - permite acceso no autenticado
// Prioridad 3: AuthenticationRequiredEvaluator - asegura auth para @PermitAll/@RolesAllowed
// Prioridad 4: @PermitAll - requiere autenticación únicamente
// Prioridad 5: @RolesAllowed - requiere roles específicos
// Prioridad 6: @RouteAccess - expresiones SpEL (solo Spring Security)
// Prioridad 10+: Evaluadores personalizados (como @RequireOwnership)
```

### Cómo la prioridad afecta la evaluación {#priority-affects-evaluation}

- Los evaluadores de menor prioridad se ejecutan primero y pueden "cortar" la cadena.
- `@DenyAll` (prioridad 1) se ejecuta primero - si está presente, el acceso siempre está denegado.
- `@AnonymousAccess` (prioridad 2) se ejecuta a continuación - si está presente, el acceso siempre se concede (incluso sin auth).
- `AuthenticationRequiredEvaluator` (prioridad 3) verifica si la ruta necesita auth y si el usuario está autenticado.
- Si ningún evaluador maneja la ruta, se aplica la lógica de seguro por defecto.

### Configurando la prioridad {#setting-priority}

Configure la prioridad con la anotación `@RegisteredEvaluator`:

```java
@RegisteredEvaluator(priority = 10)  // Se ejecuta después de los evaluadores integrados
public class OwnershipEvaluator implements RouteSecurityEvaluator {
  // ...
}
```

:::tip Rango de prioridades
Los evaluadores personalizados deben utilizar prioridades 10 o superiores. Las prioridades del 1 al 9 están reservadas para los evaluadores del marco. Si usa una prioridad en el rango reservado, recibirá una advertencia en los registros.
:::

## Combinando evaluadores {#combining-evaluators}

Los evaluadores que delegan en la cadena pueden combinarse para crear lógica de autorización compleja. Las rutas pueden tener múltiples anotaciones de seguridad:

### Combinando verificaciones de rol con lógica personalizada {#combining-roles-custom}

```java
@Route("/users/:userId/settings")
@RolesAllowed("USER")
@RequireOwnership("userId")
public class UserSettingsView extends Composite<Div> {
  // Debe tener rol USER Y estar accediendo a sus propias configuraciones
}
```

**Cómo funciona:**
1. `RolesAllowedEvaluator` (prioridad 5) verifica si el usuario tiene el rol "USER".
2. Si es así, llama a `chain.evaluate()` para continuar.
3. `OwnershipEvaluator` (prioridad 10) verifica si `userId` coincide con el usuario conectado.
4. Si es así, llama a `chain.evaluate()` para continuar.
5. La cadena termina → acceso concedido.

### Combinando expresiones SpEL con lógica personalizada {#combining-spel-custom}

```java
@Route("/admin/users/:userId/edit")
@RouteAccess("hasRole('ADMIN')")
@RequireOwnership("userId")
public class AdminEditUserView extends Composite<Div> {
  // Debe ser administrador Y estar accediendo a su propia cuenta
}
```

### Qué no se puede combinar {#cant-combine}

`@AnonymousAccess` y `@PermitAll` toman **decisiones terminales** - conceden acceso inmediatamente sin llamar a la cadena. No puede combinarlos con evaluadores personalizados:

```java
// @PermitAll concede acceso inmediatamente, @RequireOwnership nunca se ejecuta
@Route("/users/:userId/profile")
@PermitAll
@RequireOwnership("userId")
public class ProfileView extends Composite<Div> {
  // ...
}
```

Para recursos a los que todos los usuarios autenticados pueden acceder, use `@RolesAllowed` con un rol común en su lugar:

```java
// @RolesAllowed delega en la cadena
@Route("/users/:userId/profile")
@RolesAllowed("USER")
@RequireOwnership("userId")
public class ProfileView extends Composite<Div> {
  // Debe ser un usuario autenticado Y estar accediendo a su propio perfil
}
```
