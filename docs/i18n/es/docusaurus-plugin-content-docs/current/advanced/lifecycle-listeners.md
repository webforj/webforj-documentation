---
sidebar_position: 10
title: Lifecycle Listeners
sidebar_class_name: new-content
_i18n_hash: 475cc2842226c605bbe7f2ee931955dd
---
<!-- vale off -->
# Escuchas del ciclo de vida <DocChip chip='since' label='25.02' />
<!-- vale on -->

La interfaz `AppLifecycleListener` permite que el código externo observe y responda a los eventos del ciclo de vida de la aplicación. Al implementar esta interfaz, puedes ejecutar código en momentos específicos durante el inicio y la parada de la aplicación sin modificar la clase `App` en sí misma.

Los escuchas del ciclo de vida son descubiertos y cargados automáticamente en tiempo de ejecución a través de archivos de configuración del proveedor de servicios. Cada instancia de la aplicación recibe su propio conjunto de instancias de escuchas, manteniendo la separación entre diferentes aplicaciones que se ejecutan en el mismo entorno.

## Cuándo usar escuchas del ciclo de vida {#when-to-use-lifecycle-listeners}

Usa escuchas del ciclo de vida cuando necesites:
- Inicializar recursos o servicios antes de que se ejecute una aplicación
- Limpiar recursos cuando una aplicación termina  
- Agregar preocupaciones transversales sin modificar la clase `App`
- Construir arquitecturas de plugins

## La interfaz `AppLifecycleListener` {#the-applifecyclelistener-interface}

```java title="AppLifecycleListener.java"
public interface AppLifecycleListener {
    default void onWillRun(App app) {}
    default void onDidRun(App app) {}
    default void onWillTerminate(App app) {}
    default void onDidTerminate(App app) {}
}
```

:::info Aislamiento de aplicaciones
Cada instancia de la aplicación recibe su propio conjunto de instancias de escuchas:
- Los escuchas están aislados entre diferentes aplicaciones
- Los campos estáticos en los escuchas no se compartirán entre aplicaciones
- Las instancias de escucha se crean cuando la aplicación se inicia y se destruyen cuando finaliza

Si necesitas compartir datos entre aplicaciones, utiliza mecanismos de almacenamiento externos como bases de datos o servicios compartidos.
:::

### Eventos del ciclo de vida {#lifecycle-events}

| Evento | Cuándo se llama | Usos comunes |
|-------|-------------|-------------|
| `onWillRun` | Antes de que se ejecute `app.run()` | Inicializar recursos, configurar servicios |
| `onDidRun` | Después de que `app.run()` se complete exitosamente | Iniciar tareas en segundo plano, registrar inicio exitoso |
| `onWillTerminate` | Antes de la terminación de la aplicación | Guardar estado, prepararse para el cierre |
| `onDidTerminate` | Después de la terminación de la aplicación | Limpiar recursos, registro final |

## Creando un escucha del ciclo de vida {#creating-a-lifecycle-listener}

### Implementación básica {#basic-implementation}

```java title="StartupListener.java"
import com.webforj.App;
import com.webforj.AppLifecycleListener;

public class StartupListener implements AppLifecycleListener {
    
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

### Registrando el escucha {#registering-the-listener}

Crea un archivo de configuración del proveedor de servicios:

**Archivo**: `src/main/resources/META-INF/services/com.webforj.AppLifecycleListener`

```
com.example.listeners.StartupListener
```

:::tip Usando AutoService
Es fácil olvidar actualizar los descriptores de servicio. Utiliza el [AutoService](https://github.com/google/auto/blob/main/service/README.md) de Google para generar automáticamente el archivo de servicio:

```java title="StartupListener.java"
import com.google.auto.service.AutoService;

@AutoService(AppLifecycleListener.class)
public class StartupListener implements AppLifecycleListener {
    // Implementación
}
```
:::

## Controlando el orden de ejecución {#controlling-execution-order}

Cuando se registran múltiples escuchas, puedes controlar su orden de ejecución usando la anotación `@AppListenerPriority`. Esto es particularmente importante cuando las escuchas tienen dependencias entre sí o cuando ciertas inicializaciones deben ocurrir antes que otras.

Los valores de prioridad funcionan en orden ascendente - **los números más bajos se ejecutan primero**. La prioridad predeterminada es 10, por lo que los escuchas sin anotaciones de prioridad explícitas se ejecutarán después de aquellos con valores de prioridad más bajos.

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

### Flujo de ejecución con ganchos de la App {#execution-flow-with-app-hooks}

Más allá de controlar el orden entre múltiples escuchas, es importante entender cómo interactúan los escuchas con los propios ganchos de ciclo de vida de la clase `App`. Para cada evento del ciclo de vida, el marco sigue una secuencia de ejecución específica que determina cuándo se ejecutan tus escuchas en relación con los ganchos integrados de `App`.

El diagrama a continuación ilustra este flujo de ejecución, mostrando el momento preciso en que se llaman los métodos `AppLifecycleListener` en relación con los ganchos correspondientes de `App`: 

<div align="center">

![Escuchas AppLifecycleListener VS ganchos de `App` ](/img/lifecycle-listeners.svg)

</div>


## Manejo de errores {#error-handling}

Las excepciones lanzadas por los escuchas se registran pero no impiden que otros escuchas se ejecuten o que la aplicación funcione. Siempre maneja las excepciones dentro de tus escuchas:

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
