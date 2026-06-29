---
title: Namespaces
sidebar_position: 40
description: >-
  Share thread-safe key-value state across sessions, thread groups, or the
  entire JVM using Private, Group, and Global namespaces.
_i18n_hash: 82037bcac961ffa8fefb90bf7579a3af
---
<DocChip chip='since' label='24.22' />
<JavadocLink type="foundation" location="com/webforj/environment/namespace/Namespace" top='true'/>

Los espacios de nombres en webforJ proporcionan un mecanismo para almacenar y recuperar datos compartidos a través de diferentes ámbitos en una aplicación web. Permiten la comunicación de datos entre componentes y entre sesiones sin depender de técnicas de almacenamiento tradicionales, como atributos de sesión o campos estáticos. Esta abstracción permite a los desarrolladores encapsular y acceder al estado de una manera controlada y segura para hilos. Los espacios de nombres son ideales para construir herramientas de colaboración multiusuario o simplemente para mantener configuraciones globales consistentes, y te permiten coordinar datos de manera segura y eficiente.

## ¿Qué es un espacio de nombres? {#whats-a-namespace}

Un espacio de nombres es un contenedor nombrado que almacena pares clave-valor. Estos valores pueden ser accesibles y modificados en diferentes partes de tu aplicación, dependiendo del tipo de espacio de nombres que uses. Piénsalo como un mapa distribuido seguro para hilos, con manejo de eventos y mecanismos de bloqueo integrados.

### Cuándo usar espacios de nombres {#when-to-use-namespaces}

Usa espacios de nombres cuando:

- Necesites compartir valores entre sesiones de usuario o componentes de la aplicación.
- Quieras reaccionar a cambios de valores a través de escuchas.
- Requieras un bloqueo detallado para secciones críticas.
- Necesites persistir y recuperar el estado de manera eficiente a través de tu aplicación.

### Tipos de espacios de nombres {#types-of-namespaces}

webforJ ofrece tres tipos de espacios de nombres:

| Tipo        | Ámbito                                                                                                                 | Uso Típico                                 |
|-------------|-----------------------------------------------------------------------------------------------------------------------|--------------------------------------------|
| **Privado** | Compartido entre clientes que usan el mismo prefijo y nombre. La memoria se libera automáticamente cuando no quedan referencias. | Estado compartido entre sesiones de usuario relacionadas. |
| **Grupo**   | Compartido por todos los hilos generados a partir del mismo hilo padre.                                              | Coordinación de estado dentro de un grupo de hilos.   |
| **Global**  | Accesible a través de todos los hilos del servidor (en toda la JVM). La memoria se retiene hasta que las claves se eliminan explícitamente.              | Estado compartido a nivel de aplicación.              |

:::tip Elegir un valor predeterminado - Prefiere `PrivateNamespace`
Cuando dudes, usa un `PrivateNamespace`. Ofrece un intercambio seguro y acotado entre sesiones relacionadas sin afectar el estado global o del servidor. Esto lo convierte en un valor predeterminado fiable para la mayoría de las aplicaciones. 
:::

## Crear y usar un espacio de nombres {#creating-and-using-a-namespace}

Los espacios de nombres se crean instanciando uno de los tipos disponibles. Cada tipo define cómo y dónde se comparten los datos. Los ejemplos a continuación demuestran cómo crear un espacio de nombres e interactuar con sus valores.

### Espacio de nombres `Privado` {#private-namespace}

El nombre del espacio de nombres privado está compuesto por dos partes:

- **Prefijo**: Un identificador definido por el desarrollador que debe ser único para tu aplicación o módulo para evitar conflictos.
- **Nombre base** : El nombre específico para el contexto o dato compartido que deseas gestionar.

Juntos, forman el nombre completo del espacio de nombres utilizando el formato:

```text
prefijo + "." + nombreBase
```

Por ejemplo, `"myApp.sharedState"`.

Los espacios de nombres creados con el mismo prefijo y nombre base siempre se refieren a la _misma instancia subyacente_. Esto asegura un acceso compartido consistente a través de todas las llamadas a `PrivateNamespace` usando los mismos identificadores.

```java
// Crear o recuperar un espacio de nombres privado
PrivateNamespace ns = new PrivateNamespace("myApp", "sharedState");
```

Puedes verificar la existencia antes de la creación:

```java
if (PrivateNamespace.isPresent("myApp.sharedState")) {
  PrivateNamespace ns = PrivateNamespace.ofExisting("myApp.sharedState");
}
```

:::tip Pautas de Nombres
Al nombrar un `PrivateNamespace`, sigue estas reglas:

- Ambas partes deben ser no vacías.
- Cada una debe comenzar con una letra.
- Solo se permiten caracteres imprimibles.
- No se permite el espacio en blanco.

Ejemplos:
- ✓ mycrm.sessionData
- ✓ acme.analytics
- X shared.data (demasiado genérico, probablemente en conflicto)
:::

### Espacios de nombres `Grupo` y `Global` {#group-and-global-namespaces}

Además de `PrivateNamespace`, webforJ proporciona otros dos tipos para contextos de intercambio más amplios. Estos son útiles cuando el estado necesita persistir más allá de una sola sesión o grupo de hilos.

- **Espacio de nombres Global**: Accesible a través de todos los hilos del servidor (en toda la JVM).
- **Espacio de nombres de Grupo**: Compartido entre hilos que originan del mismo padre.

```java
// Estado compartido global, accesible a nivel de aplicación
GlobalNamespace globalNs = new GlobalNamespace();
globalNs.put("globalTheme", "dark");

// Estado específico de grupo, limitado a hilos que comparten un padre común
GroupNamespace groupNs = new GroupNamespace();
groupNs.put("localCache", new HashMap<>());
```

## Trabajando con valores {#working-with-values}

Los espacios de nombres proporcionan una interfaz consistente para gestionar datos compartidos a través de pares clave-valor. Esto incluye establecer, recuperar, eliminar valores, sincronizar el acceso y observar cambios en tiempo real.

### Estableciendo y eliminando valores {#setting-and-removing-values}

Usa `put()` para almacenar un valor bajo una clave específica. Si la clave está actualmente bloqueada, el método espera hasta que se libere el bloqueo o expire el tiempo de espera.

```java
// Espera hasta 20ms (por defecto) para establecer el valor
ns.put("username", "admin");

// Especifica un tiempo de espera personalizado en milisegundos
ns.put("config", configObject, 100);
```

Para eliminar una clave del espacio de nombres:

```java
ns.remove("username");
```

Tanto `put()` como `remove()` son operaciones bloqueantes si la clave objetivo está bloqueada. Si el tiempo de espera expira antes de que se libere el bloqueo, se lanza una `NamespaceLockedException`.

Para actualizaciones concurrentes seguras donde solo necesitas sobrescribir el valor, usa `atomicPut()`. Bloquea la clave, escribe el valor y libera el bloqueo en un solo paso:

```java
ns.atomicPut("counter", 42);
```

Esto previene condiciones de carrera y evita la necesidad de un bloqueo manual en escenarios de actualización simples.

### Obteniendo valores {#getting-values}

Para recuperar un valor, usa `get()`:

```java
Object value = ns.get("username");
```

Si la clave no existe, esto lanza una `NoSuchElementException`. Para evitar excepciones, usa `getOrDefault()`:

```java
Object value = ns.getOrDefault("username", "guest");
```

Para verificar si se ha definido una clave:

```java
if (ns.contains("username")) {
  // la clave existe
}
```

Si deseas inicializar un valor perezosamente solo cuando falta, usa `computeIfAbsent()`:

```java
Object token = ns.computeIfAbsent("authToken", key -> generateToken());
```

Esto es útil para valores compartidos que se crean una vez y se reutilizan, como tokens de sesión, bloques de configuración o datos en caché.

### Bloqueo manual {#manual-locking}

Si necesitas realizar múltiples operaciones sobre la misma clave o coordinar a través de múltiples claves, usa el bloqueo manual.

```java
ns.setLock("flag", 500); // Esperar hasta 500ms para el bloqueo

// Comienza la sección crítica
Object existing = ns.get("flag");
ns.put("flag", "in-progress");
// Termina la sección crítica

ns.removeLock("flag");
```

Usa este patrón cuando una secuencia de operaciones deba llevarse a cabo atómicamente a través de lecturas y escrituras. Siempre asegúrate de liberar el bloqueo para evitar bloquear otros hilos.

### Escuchando cambios {#listening-for-changes}

Los espacios de nombres admiten escuchas de eventos que te permiten reaccionar al acceso o modificación de valores. Esto es útil para escenarios tales como:

- Registro o auditoría de acceso a claves sensibles
- Activación de actualizaciones cuando un valor de configuración cambie
- Monitoreo de cambios en el estado compartido en aplicaciones multiusuario

#### Métodos de escucha disponibles {#available-listener-methods}

| Método                   | Disparador                      | Ámbito               |
|--------------------------|---------------------------------|----------------------|
| `onAccess`               | Cualquier clave es leída        | Todo el espacio de nombres |
| `onChange`               | Cualquier clave es modificada   | Todo el espacio de nombres |
| `onKeyAccess("clave")`      | Se lee una clave específica     | Por clave            |
| `onKeyChange("clave")`      | Se modifica una clave específica | Por clave            |

Cada escucha recibe un objeto de evento que contiene:
- El nombre de la clave
- El valor antiguo
- El valor nuevo
- Una referencia al espacio de nombres

#### Ejemplo: Responder a cualquier cambio de clave {#example-respond-to-any-key-change}

```java
ns.onChange(event -> {
  System.out.println("Clave cambiada: " + event.getVariableName());
  System.out.println("Valor antiguo: " + event.getOldValue());
  System.out.println("Nuevo valor: " + event.getNewValue());
});
```

#### Ejemplo: Rastrear acceso a una clave específica {#example-track-access-to-a-specific-key}

```java
ns.onKeyAccess("sessionToken", event -> {
  System.out.println("Token fue accedido: " + event.getNewValue());
});
```

Los oyentes devuelven un objeto `ListenerRegistration` que puedes usar para desregistrar el oyente más tarde:

```java
ListenerRegistration<NamespaceKeyChangeEvent> reg = ns.onKeyChange("status", event -> {
  // lógica
});
reg.remove();
```

## Ejemplo: Compartiendo el estado del juego en Tres en Raya {#example-sharing-game-state-in-tic-tac-toe}

La [demostración de Tres en Raya de webforJ](https://github.com/webforj/webforj-tictactoe) proporciona un juego simple de dos jugadores donde los turnos se comparten entre usuarios. El proyecto demuestra cómo se puede usar `Namespace` para coordinar el estado sin depender de herramientas externas como bases de datos o APIs.

En este ejemplo, un objeto de juego Java compartido se almacena en un `PrivateNamespace`, lo que permite que múltiples clientes interactúen con la misma lógica de juego. El espacio de nombres sirve como un contenedor central para el estado del juego, asegurando que:

- Ambos jugadores vean actualizaciones consistentes del tablero
- Los turnos estén sincronizados
- La lógica del juego se comparta a través de sesiones

No se necesitan servicios externos (como REST o WebSockets). Toda la coordinación se realiza a través de espacios de nombres, destacando su capacidad para gestionar el estado compartido en tiempo real con una infraestructura mínima.

Explora el código: [webforj/webforj-tictactoe](https://github.com/webforj/webforj-tictactoe)

<div class="videos-container">
  <video controls>
    <source src="https://cdn.webforj.com/webforj-documentation/video/tic-tac-toe.mp4" type="video/mp4"/>
  </video>
</div>
