---
sidebar_position: 2
title: Foundational Architecture
_i18n_hash: 0506f859c3bd22ddca70550b6f3e368a
---
El sistema de seguridad webforJ se basa en una fundación de interfaces centrales que trabajan en conjunto para proporcionar control de acceso a nivel de ruta. Estas interfaces definen los contratos para el comportamiento de seguridad, permitiendo que diferentes implementaciones, ya sean basadas en sesiones, en JSON Web Tokens (JWT), integradas con LDAP o respaldadas por bases de datos, se conecten al mismo marco subyacente.

Entender esta arquitectura te ayuda a ver cómo se evalúan las anotaciones de seguridad como `@RolesAllowed` y `@PermitAll`, cómo funciona la interceptación de navegación y cómo puedes construir implementaciones de seguridad personalizadas para tus necesidades específicas.

## Las interfaces principales {#the-four-core-interfaces}

La fundación de seguridad se basa en abstracciones clave, cada una con una responsabilidad específica:

### `RouteSecurityManager` {#routesecuritymanager}

El `RouteSecurityManager` es el coordinador central del sistema de seguridad. Gestiona los evaluadores de seguridad, orquesta el proceso de evaluación y maneja la denegación de acceso redirigiendo a los usuarios a las páginas apropiadas.

**Responsabilidades:**

- Registrar y gestionar evaluadores de seguridad con prioridades
- Coordinar el proceso de evaluación cuando un usuario navega a una ruta
- Manejar la denegación de acceso activando redirecciones a páginas de inicio de sesión o acceso denegado
- Almacenar y recuperar ubicaciones de pre-autenticación para redirecciones después del inicio de sesión

```java
public interface RouteSecurityManager {
  RouteAccessDecision evaluate(Class<?> routeClass, NavigationContext context);
  void onAccessDenied(RouteAccessDecision decision, NavigationContext context);
  RouteSecurityContext getSecurityContext();
  RouteSecurityConfiguration getConfiguration();
  void registerEvaluator(RouteSecurityEvaluator evaluator, int priority);
  Optional<Location> consumePreAuthenticationLocation();
}
```

El gerente no toma decisiones de seguridad por sí mismo, delega a los evaluadores y configuraciones. Es el pegamento que conecta todos los componentes de seguridad.

### `RouteSecurityContext` {#routesecuritycontext}

El `RouteSecurityContext` proporciona acceso al estado de autenticación del usuario actual. Responde preguntas como si el usuario está autenticado, cuál es su nombre de usuario y si tiene el rol `ADMIN`.

**Responsabilidades:**

- Determinar si el usuario actual está autenticado
- Proporcionar el principal del usuario (típicamente su nombre de usuario u objeto de usuario)
- Verificar si el usuario tiene roles o autoridades específicos
- Almacenar y recuperar atributos de seguridad personalizados

```java
public interface RouteSecurityContext {
  boolean isAuthenticated();
  Optional<Object> getPrincipal();
  boolean hasRole(String role);
  boolean hasAuthority(String authority);
  Optional<Object> getAttribute(String name);
  void setAttribute(String name, Object value);
}
```

Las implementaciones varían según el sistema de autenticación, el almacenamiento en sesión HTTP, los tokens JWT decodificados de cabeceras, consultas a bases de datos, búsquedas en LDAP u otros mecanismos.

### `RouteSecurityConfiguration` {#routesecurityconfiguration}

La `RouteSecurityConfiguration` define el comportamiento de seguridad y las ubicaciones de redirección. Indica al sistema de seguridad a dónde enviar a los usuarios cuando se requiere autenticación o se niega el acceso.

**Responsabilidades:**

- Definir si la seguridad está habilitada
- Especificar el comportamiento seguro por defecto
- Proporcionar la ubicación de la página de autenticación (típicamente `/login`)
- Proporcionar la ubicación de la página de acceso denegado

```java
public interface RouteSecurityConfiguration {
  default boolean isEnabled() { return true; }
  default boolean isSecureByDefault() { return true; }
  default Optional<Location> getAuthenticationLocation() {
    return Optional.of(new Location("/login"));
  }
  default Optional<Location> getDenyLocation() { /* ... */ }
}
```

Esta interfaz separa la política de seguridad de la aplicación de seguridad. Puedes cambiar las ubicaciones de redirección o alternar el comportamiento seguro por defecto sin modificar el gerente o los evaluadores.

### `RouteSecurityEvaluator` {#routesecurityevaluator}

El `RouteSecurityEvaluator` es donde se verifican las reglas de seguridad reales. Cada evaluador examina una ruta y decide si otorgar acceso, denegar acceso o delegar la decisión al siguiente evaluador en la cadena.

**Responsabilidades:**

- Determinar si este evaluador maneja la ruta dada
- Evaluar las anotaciones de seguridad en la clase de la ruta
- Otorgar acceso, denegar acceso o delegar al siguiente evaluador
- Participar en el patrón de cadena de responsabilidad

```java
public interface RouteSecurityEvaluator {
  RouteAccessDecision evaluate(Class<?> routeClass,
                                NavigationContext context,
                                RouteSecurityContext securityContext,
                                SecurityEvaluatorChain chain);
  default boolean supports(Class<?> routeClass) { return true; }
}
```

Los evaluadores integrados manejan anotaciones estándar como `@RolesAllowed`, `@PermitAll`, `@DenyAll` y `@AnonymousAccess`. Puedes crear evaluadores personalizados para implementar la lógica de seguridad específica del dominio.

## Cómo trabajan juntas las interfaces {#how-the-interfaces-work-together}

Estas cuatro interfaces colaboran durante la navegación para hacer cumplir las reglas de seguridad:

```mermaid
flowchart TB
  User["El usuario navega a la ruta"] --> Observer["RouteSecurityObserver<br/>(intercepta la navegación)"]
  Observer --> Manager["RouteSecurityManager<br/>(orquesta la evaluación)"]

  Manager --> Config["RouteSecurityConfiguration<br/>(proporciona configuraciones)"]
  Manager --> Context["RouteSecurityContext<br/>(proporciona información del usuario)"]
  Manager --> Chain["Cadena de Evaluadores<br/>(ejecuta evaluadores en orden de prioridad)"]

  Chain --> Decision{"Decisión de Acceso"}
  Decision -->|"Otorgar"| Render["Renderizar componente"]
  Decision -->|"Denegar"| Redirect["RouteSecurityManager.onAccessDenied()<br/>Redirigir a página de inicio de sesión o denegación"]
```

Cuando un usuario navega, el `RouteSecurityObserver` intercepta la navegación y le pide al `RouteSecurityManager` que evalúe el acceso. El gerente consulta la `RouteSecurityConfiguration` para obtener configuraciones, obtiene información del usuario del `RouteSecurityContext` y ejecuta cada `RouteSecurityEvaluator` en orden de prioridad hasta que uno toma una decisión.

## Interfaces como contratos {#the-interfaces-as-contracts}

Cada interfaz define un contrato, un conjunto de preguntas que el sistema de seguridad necesita responder. **Cómo** respondes esas preguntas es tu elección de implementación:

**Contrato de `RouteSecurityContext`:**

- "¿Está autenticado el usuario actual?" (`isAuthenticated()`)
- "¿Quién es el usuario?" (`getPrincipal()`)
- "¿Tiene el usuario el rol X?" (`hasRole()`)

Tú decides de dónde proviene esta información: sesiones HTTP, tokens JWT decodificados de cabeceras, búsquedas en bases de datos, consultas LDAP o cualquier otro respaldo de autenticación.

**Contrato de `RouteSecurityConfiguration`:**

- "¿Está habilitada la seguridad?" (`isEnabled()`)
- "¿Deberían ser seguras las rutas por defecto?" (`isSecureByDefault()`)
- "¿A dónde deberían ir los usuarios no autenticados?" (`getAuthenticationLocation()`)

Tú decides cómo obtener estos valores: codificados, desde archivos de configuración, desde variables de entorno, desde una base de datos, o calculados dinámicamente.

**Contrato de `RouteSecurityManager`:**

- "¿Debería este usuario acceder a esta ruta?" (`evaluate()`)
- "¿Qué sucede cuando se niega el acceso?" (`onAccessDenied()`)
- "¿Qué evaluadores deberían ejecutarse?" (`registerEvaluator()`)

Tú decides el flujo de autenticación, dónde almacenar ubicaciones de pre-autenticación y cómo manejar escenarios personalizados de denegación.

La arquitectura de la fundación define estos contratos, pero la implementación es flexible. Diferentes sistemas pueden implementar estas interfaces de maneras completamente diferentes según los requisitos específicos.

## La clase base `AbstractRouteSecurityManager` {#the-abstractroutesecuritymanager-base-class}

La mayoría de las implementaciones no implementan `RouteSecurityManager` directamente. En su lugar, extienden `AbstractRouteSecurityManager`, que proporciona:

- Registro de evaluadores y ordenación basada en prioridad
- Lógica de ejecución de cadena
- Manejo de denegación de acceso con redirecciones automáticas
- Almacenamiento de ubicaciones de pre-autenticación en la sesión HTTP
- Comportamiento de retroceso seguro por defecto

La clase base implementa la interfaz `RouteSecurityManager` y proporciona implementaciones concretas para la gestión de evaluadores, evaluación de acceso y manejo de denegación. Las subclases solo necesitan proporcionar el contexto de seguridad y la configuración. La clase base se encarga de la gestión de evaluadores, la ejecución de la cadena y el manejo de denegaciones automáticamente.
