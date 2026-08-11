---
sidebar_position: 10
title: Lifecycle Listeners
description: >-
  Hook into webforJ app startup and shutdown phases with AppLifecycleListener to
  initialize services, modify config, or clean up resources.
_i18n_hash: 3ef33ca5104ef421c38d3db16c9fa453
---
<!-- vale off -->
# Listeners del Ciclo de Vida <DocChip chip='since' label='25.02' />
<!-- vale on -->

La interfaz `AppLifecycleListener` permite que el código externo observe y responda a los eventos del ciclo de vida de la aplicación. Al implementar esta interfaz, puedes ejecutar código en momentos específicos durante el inicio y la finalización de la aplicación sin modificar la clase `App` misma.

Los listeners del ciclo de vida se descubren y cargan automáticamente en tiempo de ejecución a través de archivos de configuración del proveedor de servicios. Cada instancia de aplicación recibe su propio conjunto de instancias de listeners, manteniendo la isolación entre las diferentes aplicaciones que se ejecutan en el mismo entorno.

## Cuándo usar listeners del ciclo de vida {#when-to-use-lifecycle-listeners}

Utiliza listeners del ciclo de vida cuando necesites:

- Inicializar recursos o servicios antes de que una aplicación se ejecute
- Limpiar recursos cuando una aplicación termina
- Agregar preocupaciones transversales sin modificar la clase `App`
- Construir arquitecturas de plugins

## La interfaz `AppLifecycleListener` {#the-applifecyclelistener-interface}

```java title="AppLifecycleListener.java"
public interface AppLifecycleListener {
  default void onWillCreate(Environment env) {}     // Desde 25.03
  default void onDidCreate(App app) {}              // Desde 25.03
  default void onWillRun(App app) {}
  default void onDidRun(App app) {}
  default void onWillTerminate(App app) {}
  default void onDidTerminate(App app) {}
}
```

:::info Aislamiento de la aplicación
Cada instancia de aplicación recibe su propio conjunto de instancias de listeners:

- Los listeners están aislados entre diferentes aplicaciones
- Los campos estáticos en los listeners no se compartirán entre aplicaciones
- Las instancias de listeners se crean cuando la aplicación inicia y se destruyen cuando termina

Si necesitas compartir datos entre aplicaciones, utiliza mecanismos de almacenamiento externos como bases de datos o servicios compartidos.
:::

### Eventos del ciclo de vida {#lifecycle-events}

| Evento             | Cuándo se llama                                        | Usos comunes                                       |
| ------------------ | ----------------------------------------------------- | -------------------------------------------------- |
| `onWillCreate`&nbsp;<DocChip chip='since' label='25.03' /> | Después de la inicialización del entorno, antes de la creación de la aplicación | Modificar la configuración, fusionar fuentes de configuración externas |
| `onDidCreate`&nbsp;<DocChip chip='since' label='25.03' />  | Después de la instanciación de la aplicación, antes de la inicialización | Configuración temprana a nivel de aplicación, registrar servicios |
| `onWillRun`       | Antes de que se ejecute `app.run()`                  | Inicializar recursos, configurar servicios         |
| `onDidRun`        | Después de que `app.run()` se complete exitosamente   | Iniciar tareas en segundo plano, registrar inicio exitoso |
| `onWillTerminate` | Antes de la terminación de la aplicación              | Guardar estado, prepararse para el cierre         |
| `onDidTerminate`  | Después de la terminación de la aplicación            | Limpiar recursos, registro final                  |

## Creando un listener del ciclo de vida {#creating-a-lifecycle-listener}

### Implementación básica {#basic-implementation}

```java title="StartupListener.java"
import com.webforj.App;
import com.webforj.AppLifecycleListener;
import com.webforj.Environment;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

public class StartupListener implements AppLifecycleListener {

  @Override
  public void onWillCreate(Environment env) {
    // Modificar configuración antes de la creación de la aplicación
    Config additionalConfig = ConfigFactory.parseString(
      "myapp.feature.enabled = true"
    );
    env.setConfig(additionalConfig);
  }

  @Override
  public void onDidCreate(App app) {
    System.out.println("Aplicación creada: " + app.getId());
  }

  @Override
  public void onWillRun(App app) {
    System.out.println("Aplicación iniciando: " + app.getId());
  }

  @Override
  public void onDidRun(App app) {
    System.out.println("Aplicación iniciada: " + app.getId());
  }
}
```

### Registrando el listener {#registering-the-listener}

Crea un archivo de configuración del proveedor de servicios:

**Archivo**: `src/main/resources/META-INF/services/com.webforj.AppLifecycleListener`

```
com.example.listeners.StartupListener
```

:::tip Usando AutoService
Es fácil olvidar actualizar los descriptores de servicio. Utiliza [AutoService](https://github.com/google/auto/blob/main/service/README.md) de Google para generar automáticamente el archivo de servicio:

```java title="StartupListener.java"
import com.google.auto.service.AutoService;

@AutoService(AppLifecycleListener.class)
public class StartupListener implements AppLifecycleListener {
  // Implementación
}
```
:::

## Controlando el orden de ejecución {#controlling-execution-order}

Cuando se registran múltiples listeners, puedes controlar su orden de ejecución utilizando la anotación `@AppListenerPriority`. Esto es particularmente importante cuando los listeners tienen dependencias entre sí o cuando ciertas inicializaciones deben ocurrir antes que otras.

Los valores de prioridad funcionan en orden ascendente - **números más bajos se ejecutan primero**. La prioridad predeterminada es 10, por lo que los listeners sin anotaciones de prioridad explícitas se ejecutarán después de aquellos con valores de prioridad más bajos.

```java title="SecurityListener.java"
@AutoService(AppLifecycleListener.class)
@AppListenerPriority(1)  // Se ejecuta primero - configuración de seguridad crítica
public class SecurityListener implements AppLifecycleListener {
  @Override
  public void onWillRun(App app) {
    initializeSecurity();
  }
}

@AutoService(AppLifecycleListener.class)
@AppListenerPriority(10) // Prioridad predeterminada - registro general
public class LoggingListener implements AppLifecycleListener {
  @Override
  public void onWillRun(App app) {
    initializeLogging();
  }
}
```

### Flujo de ejecución con hooks de App {#execution-flow-with-app-hooks}

Más allá de controlar el orden entre múltiples listeners, es importante entender cómo los listeners interactúan con los propios hooks del ciclo de vida de la clase `App`. Para cada evento del ciclo de vida, el marco sigue una secuencia de ejecución específica que determina cuándo se ejecutan tus listeners en relación con los hooks integrados de la aplicación.

El diagrama a continuación ilustra este flujo de ejecución, mostrando el momento preciso en que se llaman los métodos `AppLifecycleListener` en relación con los correspondientes hooks de `App`:

<div align="center">

![Listeners de AppLifecycleListener VS hooks de `App` ](/img/lifecycle-listeners.svg)

</div>

## Manejo de errores {#error-handling}

Las excepciones lanzadas por los listeners se registran pero no evitan que otros listeners se ejecuten o que la aplicación se ejecute. Siempre maneja las excepciones dentro de tus listeners:

```java title="Ejemplo de manejo de errores"
@Override
public void onWillRun(App app) {
  try {
    riskyInitialization();
  } catch (Exception e) {
    logger.error("La inicialización falló", e);
  }
}
```
