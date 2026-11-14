---
title: Espacios de nombres
sidebar_position: 40
description: >-
  Share thread-safe key-value state across sessions, thread groups, or the
  entire JVM using Private, Group, and Global namespaces.
_i18n_hash: f932a175d81df4147cae1335991db3d5
---
<DocChip chip='since' label='24.22' />
<JavadocLink type="foundation" location="com/webforj/environment/namespace/Namespace" top='true'/>

Los espacios de nombres en webforJ ofrecen un mecanismo para almacenar y recuperar datos compartidos en distintos ámbitos de una aplicación web. Permiten la comunicación de datos entre componentes y sesiones sin depender de técnicas de almacenamiento tradicionales, como los atributos de sesión o los campos estáticos. Esta abstracción permite a los desarrolladores encapsular y acceder al estado de forma controlada y segura para los subprocesos. Los espacios de nombres son ideales para crear herramientas de colaboración multiusuario o simplemente mantener una configuración global coherente, y permiten coordinar datos de forma segura y eficiente.

## ¿Qué es un espacio de nombres? {#whats-a-namespace}

Un espacio de nombres es un contenedor con nombre que almacena pares clave-valor. Se puede acceder a estos valores y modificarlos desde distintas partes de la aplicación, según el tipo de espacio de nombres que se utilice. Considérelo como un mapa distribuido y seguro para los subprocesos, con mecanismos integrados de gestión de eventos y bloqueo.

### Cuándo usar espacios de nombres {#when-to-use-namespaces}

Use espacios de nombres cuando:

- Necesite compartir valores entre sesiones de usuario o componentes de la aplicación.
- Quiera reaccionar a los cambios de valor mediante escuchas.
- Necesite un bloqueo detallado para secciones críticas.
- Necesite conservar y recuperar el estado de forma eficiente en toda la aplicación.

### Tipos de espacios de nombres {#types-of-namespaces}

webforJ ofrece tres tipos de espacios de nombres:

| Tipo        | Ámbito                                                                                                               | Uso habitual                                 |
| ----------- | ------------------------------------------------------------------------------------------------------------------- | ------------------------------------------- |
| **Private** | Compartido entre los clientes que usan el mismo prefijo y nombre. La memoria se libera automáticamente cuando ya no quedan referencias. | Estado compartido entre sesiones de usuario relacionadas. |
| **Group**   | Compartido por todos los subprocesos iniciados desde el mismo subproceso principal.                                  | Coordinación del estado dentro de un grupo de subprocesos.   |
| **Global**  | Accesible desde todos los subprocesos del servidor (en toda la JVM). La memoria se conserva hasta que se eliminen explícitamente las claves.              | Estado compartido en toda la aplicación.              |

:::tip Cómo elegir uno de forma predeterminada: prefiera `PrivateNamespace`
Si tiene dudas, use un `PrivateNamespace`. Ofrece un uso compartido seguro y acotado entre sesiones relacionadas, sin afectar al estado global ni al estado de todo el servidor. Por eso es una opción predeterminada fiable para la mayoría de las aplicaciones.
:::

## Crear y usar un espacio de nombres {#creating-and-using-a-namespace}

Los espacios de nombres se crean mediante la instanciación de uno de los tipos disponibles. Cada tipo determina cómo y dónde se comparten los datos. Los ejemplos siguientes muestran cómo crear un espacio de nombres e interactuar con sus valores.

### Espacio de nombres `Private` {#private-namespace}

El nombre de un espacio de nombres privado consta de dos partes:

- **Prefijo**: identificador definido por el desarrollador que debe ser único para la aplicación o el módulo, a fin de evitar conflictos.
- **Nombre base**: nombre específico del contexto o los datos compartidos que se desean gestionar.

Juntas, estas partes forman el nombre completo del espacio de nombres con el formato:

```text
prefix + "." + baseName
```

Por ejemplo, `"myApp.sharedState"`.

Los espacios de nombres creados con el mismo prefijo y nombre base siempre hacen referencia a la _misma instancia subyacente_. Esto garantiza un acceso compartido coherente en todas las llamadas a `PrivateNamespace` que usen los mismos identificadores.

```java
// Crear o recuperar un espacio de nombres privado
PrivateNamespace ns = new PrivateNamespace("myApp", "sharedState");
```

Puede comprobar si existe antes de crearlo:

```java
if (PrivateNamespace.isPresent("myApp.sharedState")) {
  PrivateNamespace ns = PrivateNamespace.ofExisting("myApp.sharedState");
}
```

:::tip Reglas para asignar nombres
Al asignar un nombre a un `PrivateNamespace`, siga estas reglas:

- Ninguna de las dos partes puede estar vacía.
- Cada parte debe empezar con una letra.
- Solo se permiten caracteres imprimibles.
- No se permiten espacios en blanco.

Ejemplos:
- ✓ mycrm.sessionData
- ✓ acme.analytics
- X shared.data (demasiado genérico; es probable que entre en conflicto)
:::

### Espacios de nombres `Group` y `Global` {#group-and-global-namespaces}

Además de `PrivateNamespace`, webforJ proporciona otros dos tipos para contextos de uso compartido más amplios. Son útiles cuando el estado debe persistir más allá de una sesión o un grupo de subprocesos.

- **Espacio de nombres global**: accesible desde todos los subprocesos del servidor (en toda la JVM).
- **Espacio de nombres de grupo**: compartido entre los subprocesos que se originan en el mismo principal.

```java
// Estado compartido global, accesible en toda la aplicación
GlobalNamespace globalNs = new GlobalNamespace();
globalNs.put("globalTheme", "dark");

// Estado específico del grupo, limitado a los subprocesos que comparten un principal
GroupNamespace groupNs = new GroupNamespace();
groupNs.put("localCache", new HashMap<>());
```

## Trabajar con valores {#working-with-values}

Los espacios de nombres proporcionan una interfaz coherente para gestionar datos compartidos mediante pares clave-valor. Esto incluye definir, recuperar y eliminar valores, sincronizar el acceso y observar los cambios en tiempo real.

### Definir y eliminar valores {#setting-and-removing-values}

Use `put()` para almacenar un valor con una clave específica. Si la clave está bloqueada, el método espera hasta que se libere el bloqueo o se agote el tiempo de espera.

```java
// Esperar hasta 20 ms (valor predeterminado) para definir el valor
ns.put("username", "admin");

// Especificar un tiempo de espera personalizado en milisegundos
ns.put("config", configObject, 100);
```

Para eliminar una clave del espacio de nombres:

```java
ns.remove("username");
```

Tanto `put()` como `remove()` son operaciones bloqueantes si la clave de destino está bloqueada. Si se agota el tiempo de espera antes de que se libere el bloqueo, se lanza una `NamespaceLockedException`.

Para realizar actualizaciones simultáneas de forma segura cuando solo necesite sobrescribir el valor, use `atomicPut()`. Este método bloquea la clave, escribe el valor y libera el bloqueo en un solo paso:

```java
ns.atomicPut("counter", 42);
```

Esto evita las condiciones de carrera y la necesidad de bloquear manualmente en situaciones sencillas de actualización.

### Obtener valores {#getting-values}

Para recuperar un valor, use `get()`:

```java
Object value = ns.get("username");
```

Si la clave no existe, se lanza una `NoSuchElementException`. Para evitar excepciones, use `getOrDefault()`:

```java
Object value = ns.getOrDefault("username", "guest");
```

Para comprobar si una clave está definida:

```java
if (ns.contains("username")) {
  // la clave existe
}
```

Si quiere inicializar un valor de forma diferida solo cuando no exista, use `computeIfAbsent()`:

```java
Object token = ns.computeIfAbsent("authToken", key -> generateToken());
```

Esto resulta útil para los valores compartidos que se crean una vez y se reutilizan, como los tokens de sesión, los bloques de configuración o los datos almacenados en caché.

### Bloqueo manual {#manual-locking}

Si necesita realizar varias operaciones en la misma clave o coordinar varias claves, use el bloqueo manual.

```java
ns.setLock("flag", 500); // Esperar hasta 500 ms para obtener el bloqueo

// Inicio de la sección crítica
Object existing = ns.get("flag");
ns.put("flag", "in-progress");
// Fin de la sección crítica

ns.removeLock("flag");
```

Use este patrón cuando una secuencia de operaciones deba ejecutarse de forma atómica entre lecturas y escrituras. Asegúrese siempre de liberar el bloqueo para evitar bloquear otros subprocesos.

### Escuchar los cambios {#listening-for-changes}

Los espacios de nombres admiten escuchas de eventos que permiten reaccionar al acceso o la modificación de valores. Esto resulta útil en situaciones como:

- Registrar o auditar el acceso a claves confidenciales
- Activar actualizaciones cuando cambia un valor de configuración
- Supervisar cambios en el estado compartido en aplicaciones multiusuario

#### Métodos de escucha disponibles {#available-listener-methods}

| Método                    | Activador                        | Ámbito              |
|---------------------------|--------------------------------|--------------------|
| `onAccess`                | Se lee cualquier clave                | Espacio de nombres completo    |
| `onChange`                | Se modifica cualquier clave            | Espacio de nombres completo    |
| `onKeyAccess("key")`      | Se lee una clave específica         | Por clave            |
| `onKeyChange("key")`      | Se modifica una clave específica     | Por clave            |

Cada escucha recibe un objeto de evento que contiene:
- El nombre de la clave
- El valor anterior
- El nuevo valor
- Una referencia al espacio de nombres

#### Ejemplo: responder a cualquier cambio de clave {#example-respond-to-any-key-change}

```java
ns.onChange(event -> {
  System.out.println("Key changed: " + event.getVariableName());
  System.out.println("Old value: " + event.getOldValue());
  System.out.println("New value: " + event.getNewValue());
});
```

#### Ejemplo: hacer un seguimiento del acceso a una clave específica {#example-track-access-to-a-specific-key}

```java
ns.onKeyAccess("sessionToken", event -> {
  System.out.println("Token was accessed: " + event.getNewValue());
});
```

Las escuchas devuelven un objeto `ListenerRegistration` que puede utilizar para anular el registro de la escucha más adelante:

```java
ListenerRegistration<NamespaceKeyChangeEvent> reg = ns.onKeyChange("status", event -> {
  // lógica
});
reg.remove();
```

## Ejemplo: compartir el estado de una partida de tres en raya {#example-sharing-game-state-in-tic-tac-toe}

La [demo de tres en raya de webforJ](https://github.com/webforj/webforj-tictactoe) ofrece un sencillo juego para dos jugadores en el que los turnos se comparten entre los usuarios. El proyecto muestra cómo se puede usar `Namespace` para coordinar el estado sin depender de herramientas externas como bases de datos o API.

En este ejemplo, se almacena un objeto de juego Java compartido en un `PrivateNamespace`, lo que permite que varios clientes interactúen con la misma lógica de juego. El espacio de nombres funciona como contenedor central del estado de la partida y garantiza que:

- Ambos jugadores vean actualizaciones coherentes del tablero
- Los turnos estén sincronizados
- La lógica del juego se comparta entre sesiones

No se necesitan servicios externos (como REST o WebSockets). Toda la coordinación se realiza mediante espacios de nombres, lo que demuestra su capacidad para gestionar el estado compartido en tiempo real con una infraestructura mínima.

Explore el código: [webforj/webforj-tictactoe](https://github.com/webforj/webforj-tictactoe)

<VideoPlayer video="namespaces/tic-tac-toe" width={1660} height={902} title="Tic tac toe" />
