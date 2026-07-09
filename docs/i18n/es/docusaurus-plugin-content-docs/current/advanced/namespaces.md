---
title: Namespaces
sidebar_position: 40
description: >-
  Share thread-safe key-value state across sessions, thread groups, or the
  entire JVM using Private, Group, and Global namespaces.
_i18n_hash: fb5d7a0ef2a65790f0692612c07d9044
---
<DocChip chip='since' label='24.22' />
<JavadocLink type="foundation" location="com/webforj/environment/namespace/Namespace" top='true'/>

Los namespaces en webforJ proporcionan un mecanismo para almacenar y recuperar datos compartidos a través de diferentes ámbitos en una aplicación web. Permiten la comunicación de datos entre componentes y sesiones cruzadas sin depender de técnicas de almacenamiento tradicionales como atributos de sesión o campos estáticos. Esta abstracción permite a los desarrolladores encapsular y acceder al estado de manera controlada y segura para hilos. Los namespaces son ideales para construir herramientas de colaboración multiusuario o simplemente para mantener configuraciones globales consistentes, y permiten coordinar datos de manera segura y eficiente.

## ¿Qué es un namespace? {#whats-a-namespace}

Un namespace es un contenedor nombrado que almacena pares clave-valor. Estos valores se pueden acceder y modificar a través de diferentes partes de tu aplicación según el tipo de namespace que utilices. Piénsalo como un mapa distribuido seguro para hilos con control de eventos y mecanismos de bloqueo incorporados.

### Cuándo usar namespaces {#when-to-use-namespaces}

Utiliza namespaces cuando:

- Necesites compartir valores entre sesiones de usuario o componentes de la aplicación.
- Quieras reaccionar a los cambios de valor a través de oyentes.
- Requieras un bloqueo granular para secciones críticas.
- Necesites persistir y recuperar el estado de manera eficiente a través de tu aplicación.

### Tipos de namespaces {#types-of-namespaces}

webforJ ofrece tres tipos de namespaces:

| Tipo         | Ámbito                                                                                                               | Uso Típico                               |
|--------------|---------------------------------------------------------------------------------------------------------------------|------------------------------------------|
| **Privado**  | Compartido entre clientes que utilizan el mismo prefijo y nombre. La memoria se libera automáticamente cuando no quedan referencias. | Estado compartido entre sesiones de usuario relacionadas. |
| **Grupo**    | Compartido por todos los hilos generados desde el mismo hilo padre.                                                  | Coordinación de estado dentro de un grupo de hilos.   |
| **Global**   | Accesible a través de todos los hilos del servidor (a nivel de JVM). La memoria se retiene hasta que las claves se eliminan explícitamente. | Estado compartido a nivel de aplicación. |

:::tip Eligiendo un predeterminado - Preferir `PrivateNamespace`
Cuando tengas dudas, utiliza un `PrivateNamespace`. Ofrece un compartimiento seguro y delimitado entre sesiones relacionadas sin afectar el estado global o a nivel de servidor. Esto lo convierte en un valor predeterminado confiable para la mayoría de las aplicaciones.
:::

## Creando y usando un namespace {#creating-and-using-a-namespace}

Los namespaces se crean instanciando uno de los tipos disponibles. Cada tipo define cómo y dónde se comparte la información. Los ejemplos a continuación demuestran cómo crear un namespace e interactuar con sus valores.

### Namespace `Privado` {#private-namespace}

El nombre del namespace privado se compone de dos partes:

- **Prefijo**: Un identificador definido por el desarrollador que debe ser único para tu aplicación o módulo para evitar conflictos.
- **Nombre base**: El nombre específico para el contexto o dato compartido que deseas gestionar.

Juntos, forman el nombre completo del namespace utilizando el formato:

```text
prefijo + "." + nombreBase
```

Por ejemplo, `"myApp.sharedState"`.

Los namespaces creados con el mismo prefijo y nombre base siempre se refieren a la _misma instancia subyacente_. Esto asegura un acceso compartido consistente en todas las llamadas a `PrivateNamespace` que utilizan los mismos identificadores.

```java
// Crear o recuperar un namespace privado
PrivateNamespace ns = new PrivateNamespace("myApp", "sharedState");
```

Puedes comprobar la existencia antes de la creación:

```java
if (PrivateNamespace.isPresent("myApp.sharedState")) {
  PrivateNamespace ns = PrivateNamespace.ofExisting("myApp.sharedState");
}
```

:::tip Directrices de Nomenclatura
Al nombrar un `PrivateNamespace`, sigue estas reglas:

- Ambas partes deben estar no vacías.
- Cada una debe comenzar con una letra.
- Solo se permiten caracteres imprimibles.
- No se permite el espacio en blanco.

Ejemplos:
- ✓ mycrm.sessionData
- ✓ acme.analytics
- X shared.data (demasiado genérico, propenso a conflictos)
:::

### Namespaces `Grupo` y `Global` {#group-and-global-namespaces}

Además de `PrivateNamespace`, webforJ proporciona dos otros tipos para contextos de compartición más amplios. Estos son útiles cuando el estado necesita persistir más allá de una sola sesión o grupo de hilos.

- **Namespace Global**: Accesible a través de todos los hilos del servidor (a nivel de JVM).
- **Namespace de Grupo**: Compartido entre hilos que provienen del mismo padre.

```java
// Estado compartido global, accesible a nivel de aplicación
GlobalNamespace globalNs = new GlobalNamespace();
globalNs.put("globalTheme", "dark");

// Estado específico del grupo, limitado a hilos que comparten un padre común
GroupNamespace groupNs = new GroupNamespace();
groupNs.put("localCache", new HashMap<>());
```

## Trabajando con valores {#working-with-values}

Los namespaces proporcionan una interfaz consistente para gestionar datos compartidos a través de pares clave-valor. Esto incluye establecer, recuperar, eliminar valores, sincronizar el acceso y observar cambios en tiempo real.

### Estableciendo y eliminando valores {#setting-and-removing-values}

Utiliza `put()` para almacenar un valor bajo una clave específica. Si la clave está actualmente bloqueada, el método espera hasta que el bloqueo se libere o expire el tiempo de espera.

```java
// Espera hasta 20 ms (por defecto) para establecer el valor
ns.put("username", "admin");

// Especifica un tiempo de espera personalizado en milisegundos
ns.put("config", configObject, 100);
```

Para eliminar una clave del namespace:

```java
ns.remove("username");
```

Tanto `put()` como `remove()` son operaciones bloqueantes si la clave objetivo está bloqueada. Si el tiempo de espera expira antes de que se libere el bloqueo, se lanza una `NamespaceLockedException`.

Para actualizaciones concurrentes seguras donde solo necesitas sobrescribir el valor, utiliza `atomicPut()`. Bloquea la clave, escribe el valor y libera el bloqueo en un solo paso:

```java
ns.atomicPut("counter", 42);
```

Esto previene condiciones de carrera y evita la necesidad de bloqueos manuales en escenarios de actualización simples.

### Obteniendo valores {#getting-values}

Para recuperar un valor, utiliza `get()`:

```java
Object value = ns.get("username");
```

Si la clave no existe, esto lanza una `NoSuchElementException`. Para evitar excepciones, utiliza `getOrDefault()`:

```java
Object value = ns.getOrDefault("username", "guest");
```

Para verificar si una clave está definida:

```java
if (ns.contains("username")) {
  // la clave existe
}
```

Si deseas inicializar un valor perezosamente solo cuando falta, utiliza `computeIfAbsent()`:

```java
Object token = ns.computeIfAbsent("authToken", key -> generateToken());
```

Esto es útil para valores compartidos que se crean una vez y se reutilizan, como tokens de sesión, bloques de configuración o datos en caché.

### Bloqueo manual {#manual-locking}

Si necesitas realizar múltiples operaciones sobre la misma clave o coordinar a través de múltiples claves, utiliza el bloqueo manual.

```java
ns.setLock("flag", 500); // Espera hasta 500 ms para el bloqueo

// La sección crítica comienza
Object existing = ns.get("flag");
ns.put("flag", "in-progress");
// La sección crítica termina

ns.removeLock("flag");
```

Utiliza este patrón cuando una secuencia de operaciones debe realizarse atómicamente a través de lecturas y escrituras. Asegúrate siempre de liberar el bloqueo para evitar bloquear otros hilos.

### Escuchar cambios {#listening-for-changes}

Los namespaces admiten oyentes de eventos que te permiten reaccionar al acceso o la modificación de valores. Esto es útil para escenarios tales como:

- Registrar o auditar el acceso a claves sensibles
- Activar actualizaciones cuando un valor de configuración cambia
- Monitorear cambios de estado compartidos en aplicaciones multiusuario

#### Métodos de oyente disponibles {#available-listener-methods}

| Método                    | Disparador                      | Ámbito             |
|---------------------------|----------------------------------|--------------------|
| `onAccess`                | Cualquier clave es leída        | Todo el namespace   |
| `onChange`                | Cualquier clave es modificada   | Todo el namespace   |
| `onKeyAccess("key")`      | Una clave específica es leída    | Por clave          |
| `onKeyChange("key")`      | Una clave específica es modificada| Por clave          |

Cada oyente recibe un objeto de evento que contiene:
- El nombre de la clave
- El valor antiguo
- El nuevo valor
- Una referencia al namespace

#### Ejemplo: Responder a cualquier cambio de clave {#example-respond-to-any-key-change}

```java
ns.onChange(event -> {
  System.out.println("Cambio de clave: " + event.getVariableName());
  System.out.println("Valor antiguo: " + event.getOldValue());
  System.out.println("Nuevo valor: " + event.getNewValue());
});
```

#### Ejemplo: Rastrear el acceso a una clave específica {#example-track-access-to-a-specific-key}

```java
ns.onKeyAccess("sessionToken", event -> {
  System.out.println("Token fue accedido: " + event.getNewValue());
});
```

Los oyentes devuelven un objeto `ListenerRegistration` que puedes utilizar para anular el registro del oyente más tarde:

```java
ListenerRegistration<NamespaceKeyChangeEvent> reg = ns.onKeyChange("status", event -> {
  // lógica
});
reg.remove();
```

## Ejemplo: Compartiendo el estado del juego en Tres en Raya {#example-sharing-game-state-in-tic-tac-toe}

La [demostración de Tres en Raya de webforJ](https://github.com/webforj/webforj-tictactoe) proporciona un simple juego de dos jugadores donde los turnos se comparten entre los usuarios. El proyecto demuestra cómo se puede utilizar `Namespace` para coordinar el estado sin depender de herramientas externas como bases de datos o APIs.

En este ejemplo, un objeto de juego en Java compartido se almacena en un `PrivateNamespace`, permitiendo que múltiples clientes interactúen con la misma lógica del juego. El namespace sirve como un contenedor central para el estado del juego, asegurando que:

- Ambos jugadores vean actualizaciones consistentes del tablero
- Los turnos estén sincronizados
- La lógica del juego esté compartida entre sesiones

No se necesitan servicios externos (como REST o WebSockets). Toda la coordinación se realiza a través de namespaces, destacando su capacidad para gestionar el estado compartido en tiempo real con una infraestructura mínima.

Explora el código: [webforj/webforj-tictactoe](https://github.com/webforj/webforj-tictactoe)

<div class="videos-container">
  <video controls>
    <source src="https://cdn.webforj.com/webforj-documentation/video/tic-tac-toe.mp4" type="video/mp4"/>
  </video>
</div>
