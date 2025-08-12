---
title: Terminate and Error Actions
sidebar_position: 40
_i18n_hash: 1a250a51020b32c8b3471ae75ea8f750
---
<!-- vale off -->
# Acciones de Terminación y Error <DocChip chip='since' label='23.06' />
<!-- vale on -->

Al desarrollar aplicaciones con webforJ, es esencial definir cómo se comporta tu aplicación cuando termina o encuentra un error. El marco proporciona mecanismos para personalizar estos comportamientos a través de acciones de `terminate` y `error`.

## Descripción general {#overview}

La clase `App` te permite definir acciones que se ejecutan cuando la aplicación termina normalmente o cuando encuentra un error. Estas acciones son instancias de la interfaz `AppCloseAction` y se pueden establecer usando:

- `setTerminateAction(AppCloseAction action)`: Establece la acción a ejecutar en la terminación normal.
- `setErrorAction(AppCloseAction action)`: Establece la acción a ejecutar cuando ocurre un error.

Las implementaciones disponibles de `AppCloseAction` incluyen:

- `DefaultAction`: Borra el navegador y muestra un mensaje localizado que solicita al usuario que recargue la aplicación.
- `NoneAction`: No realiza ninguna acción, reiniciando efectivamente cualquier acción previamente establecida.
- `MessageAction`: Muestra un mensaje con un enlace personalizado.
- `RedirectAction`: Redirige al usuario a una URL específica.

:::info Distinguiendo las Acciones de Terminación de las Acciones de Error en webforJ
webforJ no trata la terminación debido a una excepción lanzada o no manejada como una acción de error, sino más bien como una acción de terminación porque la aplicación se cierra normalmente. Una acción de error ocurre cuando la aplicación recibe una señal de terminación debido a un error externo, como cuando el navegador no puede conectarse al servidor que ejecuta la aplicación.
:::

## Comportamiento predeterminado {#default-behavior}

En la versión `24.11` y anteriores de webforJ, la aplicación por defecto utiliza `DefaultAction` para eventos de terminación y error. Esto significa que cuando la aplicación termina o encuentra un error, el navegador muestra un mensaje que solicita al usuario que recargue la aplicación.

A partir de la versión `24.12`, webforJ por defecto utiliza `NoneAction` para eventos de terminación y error. Este cambio significa que no se realiza ninguna acción cuando la aplicación termina o ocurre un error, permitiendo a webforJ delegar el manejo de errores a un `ErrorHandler` apropiado si está configurado, o recurrir a sus mecanismos de manejo de errores genéricos. Al usar `NoneAction`, la aplicación evita interrumpir el flujo de manejo de errores predeterminado, permitiéndote definir controladores de errores personalizados o confiar en la gestión de errores integrada de webforJ.

## Personalizando acciones {#customizing-actions}

Para cambiar el comportamiento predeterminado, utiliza los métodos `setTerminateAction()` y `setErrorAction()` en tu subclase de `App`.

### Estableciendo una acción de mensaje personalizado {#setting-a-custom-message-action}

Si deseas mostrar un mensaje personalizado al finalizar normalmente:

```java
public class MyApp extends App {

  @Override
  public void run() throws WebforjException {
    // Establecer una acción de mensaje personalizado
    setTerminateAction(new MessageAction(
        "¡Gracias por usar nuestra aplicación!. Haz clic para recargar"
    ));
  }
}
```

### Estableciendo una acción de redirección {#setting-a-redirect-action}

Para redirigir al usuario a una URL específica al finalizar normalmente:

```java
public class MyApp extends App {

  @Override
  public void run() throws WebforjException {
    // Establecer una acción de redirección en caso de error
    setTerminateAction(new RedirectAction(
        "https://example.com/error"
    ));
  }
}
```

## Terminando la aplicación {#terminating-the-app}

Puedes terminar programáticamente tu aplicación llamando al método `terminate()`:

```java
public class MyApp extends App {

  @Override
  public void run() throws WebforjException {
    // Terminar la aplicación bajo ciertas condiciones
    if (someCondition) {
      terminate();
    }
  }
}
```

Al llamar a `terminate()`, se ejecuta la acción definida por `setTerminateAction()`.

## Hooks para la terminación {#hooks-for-termination}

La clase `App` proporciona métodos de hook para realizar acciones antes y después de la terminación:

- `onWillTerminate()`: Se llama antes de la terminación.
- `onDidTerminate()`: Se llama después de la terminación.

```java
public class MyApp extends App {

  @Override
  protected void onWillTerminate() {
    // Realizar tareas de limpieza
  }

  @Override
  protected void onDidTerminate() {
    // Acciones después de la terminación
  }
}
```

:::tip Listeners de ciclo de vida externos
Para una gestión de ciclo de vida más avanzada, considera usar `AppLifecycleListener` para manejar eventos de terminación desde componentes externos sin modificar la clase `App`. Esto es particularmente útil para arquitecturas de plugins o cuando múltiples componentes necesitan responder a la terminación de la aplicación. Aprende más sobre [Listeners de Ciclo de Vida](lifecycle-listeners.md).
:::

### Página de terminación personalizada {#custom-termination-page}

En algunos casos, podrías querer mostrar una página de terminación personalizada cuando tu aplicación finaliza, proporcionando a los usuarios un mensaje personalizado o recursos adicionales. Esto se puede lograr sobrescribiendo el método `onDidTerminate()` en tu subclase de `App` e inyectando HTML personalizado en la página.

Aquí hay un ejemplo de cómo crear una página de terminación personalizada:

```java
public class MyApp extends App {

  @Override
  public void run() throws WebforjException {
    setTerminateAction(new NoneAction());
    terminate();
  }

  @Override
  protected void onDidTerminate() {
    String html = """
    <div style="display: flex; justify-content: center; align-items: center; height: 100vh; flex-direction: column;">
        <h1>Gracias por usar webforJ</h1>
        <p>Para más información, visita <a href="https://webforj.com">webforj.com</a></p>
    </div>
    """;

    Page.getCurrent().executeJsVoidAsync(
        String.format("document.body.innerHTML = `%s`", html)
    );
  }
}
```
