---
title: Namespaces
sidebar_position: 30
_i18n_hash: f3d79da01b17871bddf7543682a5e7e5
---
<DocChip chip='since' label='24.22' />
<JavadocLink type="foundation" location="com/webforj/environment/namespace/Namespace" top='true'/>

Los espacios de nombres en webforJ proporcionan un mecanismo para almacenar y recuperar datos compartidos a través de diferentes ámbitos en una aplicación web. Permiten la comunicación de datos entre componentes y entre sesiones sin depender de técnicas de almacenamiento tradicionales, como atributos de sesión o campos estáticos. Esta abstracción permite a los desarrolladores encapsular y acceder al estado de manera controlada y segura para múltiples hilos. Los espacios de nombres son ideales para construir herramientas de colaboración multiusuario o simplemente para mantener configuraciones globales consistentes, y te permiten coordinar datos de manera segura y eficiente.

## ¿Qué es un espacio de nombres? {#whats-a-namespace}

Un espacio de nombres es un contenedor nombrado que almacena pares clave-valor. Estos valores pueden ser accedidos y modificados a través de diferentes partes de tu aplicación según el tipo de espacio de nombres que uses. Puedes pensarlo como un mapa distribuido seguro para hilos, con manejo de eventos y mecanismos de bloqueo incorporados.

### Cuándo usar espacios de nombres {#when-to-use-namespaces}

Usa espacios de nombres cuando:

- Necesites compartir valores entre sesiones de usuario o componentes de la aplicación.
- Quieras reaccionar a cambios de valores a través de oyentes.
- Requieras un bloqueo detallado para secciones críticas.
- Necesites persistir y recuperar estado de manera eficiente a través de tu aplicación.

### Tipos de espacios de nombres {#types-of-namespaces}

webforJ ofrece tres tipos de espacios de nombres:

| Tipo        | Ámbito                                                                                                               | Uso Típico                                 |
| ----------- | ------------------------------------------------------------------------------------------------------------------- | ------------------------------------------- |
| **Privado** | Compartido entre clientes que utilizan el mismo prefijo y nombre. La memoria se libera automáticamente cuando no quedan referencias. | Estado compartido entre sesiones de usuario relacionadas. |
| **Grupo**   | Compartido por todos los hilos generados a partir del mismo hilo padre.                                              | Coordinación del estado dentro de un grupo de hilos.   |
| **Global**  | Accesible a través de todos los hilos del servidor (a nivel de JVM). La memoria se retiene hasta que las claves se eliminan explícitamente. | Estado compartido a nivel de la aplicación.              |

:::tip Elegir un valor predeterminado - Prefiere `PrivateNamespace`
Cuando tengas dudas, usa un `PrivateNamespace`. Ofrece un compartir seguro y con ámbito entre sesiones relacionadas sin impactar en el estado global o de servidor. Esto lo convierte en un valor predeterminado confiable para la mayoría de las aplicaciones. 
:::

## Creando y usando un espacio de nombres {#creating-and-using-a-namespace}

Los espacios de nombres se crean instanciando uno de los tipos disponibles. Cada tipo define cómo y dónde se comparten los datos. Los ejemplos a continuación demuestran cómo crear un espacio de nombres e interactuar con sus valores.

### Espacio de nombres `Privado` {#private-namespace}

El nombre del espacio de nombres privado se compone de dos partes:

- **Prefijo**: Un identificador definido por el desarrollador que debe ser único para tu aplicación o módulo para evitar conflictos.
- **Nombre base**: El nombre específico para el contexto compartido o los datos que deseas administrar.

Juntos, forman el nombre completo del espacio de nombres utilizando el formato:

```text
prefijo + "." + nombreBase
```

Por ejemplo, `"miApp.estadoCompartido"`.

Los espacios de nombres creados con el mismo prefijo y nombre base siempre se refieren a la _misma instancia subyacente_. Esto asegura un acceso compartido consistente a través de todas las llamadas a `PrivateNamespace` que utilizan los mismos identificadores.

```java
// Crear o recuperar un espacio de nombres privado
PrivateNamespace ns = new PrivateNamespace("miApp", "estadoCompartido");
```

Puedes verificar la existencia antes de la creación:

```java
if (PrivateNamespace.isPresent("miApp.estadoCompartido")) {
  PrivateNamespace ns = PrivateNamespace.ofExisting("miApp.estadoCompartido");
}
```

:::tip Directrices para nombrar
Al nombrar un `PrivateNamespace`, sigue estas reglas:

- Ambas partes deben ser no vacías.
- Cada una debe comenzar con una letra.
- Solo se permiten caracteres imprimibles.
- No se permite el espacio en blanco.

Ejemplos:
- ✓ miCRM.datosDeSesion
- ✓ acme.analitica
- X datos.compartidos (demasiado genérico, probablemente en conflicto)
:::

### Espacios de nombres `Grupo` y `Global` {#group-and-global-namespaces}

Además de `PrivateNamespace`, webforJ proporciona otros dos tipos para contextos de compartir más amplios. Estos son útiles cuando el estado necesita persistir más allá de una sola sesión o grupo de hilos.

- **Espacio de Nombres Global**: Accesible a través de todos los hilos del servidor (a nivel de JVM).
- **Espacio de Nombres de Grupo**: Compartido entre hilos que se originan del mismo padre.

```java
// Estado compartido global, accesible a nivel de la aplicación
GlobalNamespace globalNs = new GlobalNamespace();
globalNs.put("temaGlobal", "oscuro");

// Estado específico del grupo, limitado a hilos que comparten un padre común
GroupNamespace groupNs = new GroupNamespace();
groupNs.put("cacheLocal", new HashMap<>());
```

## Trabajando con valores {#working-with-values}

Los espacios de nombres proporcionan una interfaz consistente para gestionar datos compartidos a través de pares clave-valor. Esto incluye establecer, recuperar, eliminar valores, sincronizar el acceso y observar cambios en tiempo real.

### Estableciendo y eliminando valores {#setting-and-removing-values}

Usa `put()` para almacenar un valor bajo una clave específica. Si la clave está actualmente bloqueada, el método espera hasta que se libere el bloqueo o el tiempo de espera expire.

```java
// Espera hasta 20ms (por defecto) para establecer el valor
ns.put("nombreDeUsuario", "admin");

// Especifica un tiempo de espera personalizado en milisegundos
ns.put("config", objetoConfig, 100);
```

Para eliminar una clave del espacio de nombres:

```java
ns.remove("nombreDeUsuario");
```

Tanto `put()` como `remove()` son operaciones bloqueantes si la clave objetivo está bloqueada. Si el tiempo de espera expira antes de que se libere el bloqueo, se lanza una `NamespaceLockedException`.

Para actualizaciones concurrentes seguras donde solo necesitas sobrescribir el valor, usa `atomicPut()`. Bloquea la clave, escribe el valor y libera el bloqueo en un solo paso:

```java
ns.atomicPut("contador", 42);
```

Esto previene condiciones de competencia y evita la necesidad de bloqueo manual en escenarios de actualización simples.

### Obteniendo valores {#getting-values}

Para recuperar un valor, usa `get()`:

```java
Object value = ns.get("nombreDeUsuario");
```

Si la clave no existe, se lanza una `NoSuchElementException`. Para evitar excepciones, usa `getOrDefault()`:

```java
Object value = ns.getOrDefault("nombreDeUsuario", "invitado");
```

Para verificar si una clave está definida:

```java
if (ns.contains("nombreDeUsuario")) {
  // la clave existe
}
```

Si deseas inicializar perezosamente un valor solo cuando falta, usa `computeIfAbsent()`:

```java
Object token = ns.computeIfAbsent("tokenDeAuth", key -> generarToken());
```

Esto es útil para valores compartidos que se crean una vez y se reutilizan, como tokens de sesión, bloques de configuración o datos en caché.

### Bloqueo manual {#manual-locking}

Si necesitas realizar múltiples operaciones en la misma clave o coordinar a través de múltiples claves, usa el bloqueo manual.

```java
ns.setLock("bandera", 500); // Espera hasta 500ms por el bloqueo

// La sección crítica comienza
Object existing = ns.get("bandera");
ns.put("bandera", "en-proceso");
// La sección crítica termina

ns.removeLock("bandera");
```

Usa este patrón cuando una secuencia de operaciones debe realizarse atómicamente entre lecturas y escrituras. Siempre asegúrate de que el bloqueo sea liberado para evitar bloquear otros hilos.

### Escuchando cambios {#listening-for-changes}

Los espacios de nombres admiten oyentes de eventos que te permiten reaccionar al acceso o modificación de valores. Esto es útil para escenarios como:

- Registrar o auditar el acceso a claves sensibles
- Activar actualizaciones cuando cambia un valor de configuración
- Monitorear cambios en el estado compartido en aplicaciones multiusuario

#### Métodos de oyente disponibles {#available-listener-methods}

| Método                    | Activador                       | Ámbito              |
|---------------------------|---------------------------------|---------------------|
| `onAccess`                | Cualquier clave es leída       | Todo el espacio de nombres    |
| `onChange`                | Cualquier clave es modificada   | Todo el espacio de nombres    |
| `onKeyAccess("clave")`    | Una clave específica es leída   | Por clave            |
| `onKeyChange("clave")`    | Una clave específica es modificada | Por clave            |

Cada oyente recibe un objeto de evento que contiene:
- El nombre de la clave
- El valor antiguo
- El nuevo valor
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
ns.onKeyAccess("tokenDeSesion", event -> {
  System.out.println("Token fue accedido: " + event.getNewValue());
});
```

Los oyentes devuelven un objeto `ListenerRegistration` que puedes usar para anular el registro del oyente más tarde:

```java
ListenerRegistration<NamespaceKeyChangeEvent> reg = ns.onKeyChange("estado", event -> {
  // lógica
});
reg.remove();
```

## Ejemplo: Compartiendo el estado del juego en Tic-Tac-Toe {#example-sharing-game-state-in-tic-tac-toe}

La [demostración de Tic-Tac-Toe de webforJ](https://github.com/webforj/webforj-tictactoe) proporciona un simple juego de dos jugadores donde los turnos se comparten entre los usuarios. El proyecto demuestra cómo `Namespace` se puede usar para coordinar el estado sin depender de herramientas externas como bases de datos o APIs.

En este ejemplo, un objeto de juego en Java compartido se almacena en un `PrivateNamespace`, permitiendo que múltiples clientes interactúen con la misma lógica del juego. El espacio de nombres sirve como un contenedor central para el estado del juego, asegurando que:

- Ambos jugadores vean actualizaciones de tablero consistentes
- Los turnos estén sincronizados
- La lógica del juego se comparta a través de sesiones

No se necesitan servicios externos (como REST o WebSockets). Toda la coordinación se realiza a través de espacios de nombres, destacando su capacidad para gestionar el estado compartido en tiempo real con una infraestructura mínima.

Explora el código: [webforj/webforj-tictactoe](https://github.com/webforj/webforj-tictactoe)

<div class="videos-container">
  <video controls>
    <source src="https://cdn.webforj.com/webforj-documentation/video/tic-tac-toe.mp4" type="video/mp4"/>
  </video>
</div>
