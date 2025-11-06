---
sidebar_position: 5
title: SpEL Expressions
_i18n_hash: 1019aac355c5ef0efc8623660c3501e5
---
Spring Expression Language (`SpEL`) proporciona una forma declarativa de definir reglas de autorización directamente en anotaciones. La anotación `@RouteAccess` evalúa expresiones `SpEL` utilizando las funciones de autorización integradas de Spring Security.

:::info Solo Spring Security
Las expresiones `SpEL` están disponibles solo cuando se utiliza la integración de Spring.
:::

## Uso básico {#basic-usage}

La anotación `@RouteAccess` acepta una expresión `SpEL` que evalúa a un booleano:

```java
@Route("/admin/dashboard")
@RouteAccess("hasRole('ADMIN')")
public class AdminDashboardView extends Composite<Div> {
  // Solo los usuarios con autoridad ROLE_ADMIN pueden acceder
}
```

Si la expresión evalúa a `true`, se concede el acceso. Si es `false`, el usuario es redirigido a la página de acceso denegado.

## Funciones de seguridad integradas {#built-in-security-functions}

Spring Security proporciona las siguientes funciones de autorización a través de `SecurityExpressionRoot`:

| Función | Parámetros | Descripción | Ejemplo |
|----------|-----------|-------------|---------|
| `hasRole` | `String role` | Verifica si el usuario tiene el rol especificado (automáticamente lo precede con `ROLE_`) | `hasRole('ADMIN')` coincide con `ROLE_ADMIN` |
| `hasAnyRole` | `String... roles` | Verifica si el usuario tiene alguno de los roles especificados | `hasAnyRole('ADMIN', 'MANAGER')` |
| `hasAuthority` | `String authority` | Verifica si el usuario tiene la cadena de autoridad exacta | `hasAuthority('REPORTS:READ')` |
| `hasAnyAuthority` | `String... authorities` | Verifica si el usuario tiene alguna de las autoridades especificadas | `hasAnyAuthority('REPORTS:READ', 'REPORTS:WRITE')` |
| `isAuthenticated` | Ninguno | Devuelve `true` si el usuario está autenticado | `isAuthenticated()` |

### Ejemplos {#examples}

```java
// Verificación de rol
@Route("/admin")
@RouteAccess("hasRole('ADMIN')")
public class AdminView extends Composite<Div> { }

// Múltiples roles
@Route("/staff")
@RouteAccess("hasAnyRole('ADMIN', 'MANAGER', 'SUPERVISOR')")
public class StaffView extends Composite<Div> { }

// Verificación de autoridad
@Route("/reports")
@RouteAccess("hasAuthority('REPORTS:READ')")
public class ReportsView extends Composite<Div> { }

// Requiere autenticación
@Route("/profile")
@RouteAccess("isAuthenticated()")
public class ProfileView extends Composite<Div> { }
```

## Combinando condiciones {#combining-conditions}

Utiliza operadores booleanos (`and`, `or`, `!`) para crear reglas de autorización complejas:

```java
// Ambas condiciones son requeridas
@Route("/moderator/reports")
@RouteAccess("hasRole('MODERATOR') and hasAuthority('REPORTS:VIEW')")
public class ModeratorReportsView extends Composite<Div> { }

// Cualquiera de las condiciones concede acceso
@Route("/support")
@RouteAccess("hasRole('ADMIN') or hasRole('SUPPORT')")
public class SupportView extends Composite<Div> { }

// Negación
@Route("/trial/features")
@RouteAccess("isAuthenticated() and !hasAuthority('PREMIUM')")
public class TrialFeaturesView extends Composite<Div> { }

// Expresión compleja en múltiples líneas
@Route("/reports/advanced")
@RouteAccess("""
  hasRole('ADMIN') or
  (hasRole('ANALYST') and hasAuthority('REPORTS:ADVANCED'))
  """)
public class AdvancedReportsView extends Composite<Div> { }
```

## Combinando con otras anotaciones {#combining-with-other-annotations}

`@RouteAccess` funciona junto con anotaciones de seguridad estándar. Los evaluadores se ejecutan en orden de prioridad:

```java
@Route("/team/admin")
@RolesAllowed("USER")
@RouteAccess("hasAuthority('TEAM:ADMIN')")
public class TeamAdminView extends Composite<Div> {
  // Debe tener el rol USER Y autoridad TEAM:ADMIN
}
```

Orden de evaluación:
1. El evaluador `@RolesAllowed` (prioridad 5) verifica el rol `USER`
2. Si se aprueba, el evaluador `@RouteAccess` (prioridad 6) evalúa la expresión `SpEL`
3. Si se aprueba, se ejecutan los evaluadores personalizados (prioridad 10+)

## Códigos de error personalizados {#custom-error-codes}

Proporciona códigos de error significativos para las denegaciones de acceso:

```java
@Route("/premium/features")
@RouteAccess(
  value = "hasAuthority('PREMIUM')",
  code = "PREMIUM_SUBSCRIPTION_REQUIRED"
)
public class PremiumFeaturesView extends Composite<Div> { }
```

El parámetro `code` identifica la razón de denegación cuando la expresión evalúa a `false`.

## Variables disponibles {#available-variables}

Las expresiones `SpEL` tienen acceso a estas variables en el contexto de evaluación:

| Variable | Tipo | Descripción |
|----------|------|-------------|
| `authentication` | `Authentication` | Objeto de autenticación de Spring Security |
| `principal` | `Object` | El principal autenticado (generalmente `UserDetails`) |
| `routeClass` | `Class<? extends Component>` | La clase del componente de ruta que se está accediendo |
| `context` | `NavigationContext` | contexto de navegación de webforJ |
| `securityContext` | `RouteSecurityContext` | contexto de seguridad de ruta de webforJ |

Ejemplo usando variables:

```java
@Route("/admin")
@RouteAccess("authentication.name == 'superadmin'")
public class SuperAdminView extends Composite<Div> { }
```

## Cuándo usar `SpEL` VS evaluadores personalizados {#when-to-use-spel-vs-custom-evaluators}

**Usa `@RouteAccess` `SpEL` cuando:**
- La autorización se basa puramente en roles o autoridades
- Combinando funciones de seguridad integradas con lógica booleana
- Reglas específicas de ruta que no requieren reutilización

**Usa evaluadores personalizados cuando:**
- La autorización depende de parámetros de ruta (verificaciones de propiedad)
- Lógica empresarial compleja que requiere la integración de servicios de Spring
- Patrones de autorización reutilizables en múltiples rutas
- Anotaciones personalizadas que documentan la intención de autorización

Consulta la [guía de Evaluadores Personalizados](/docs/security/custom-evaluators) para implementar escenarios de autorización avanzados.
