---
title: Error Handling
sidebar_position: 25
_i18n_hash: 15106dd9fa7ccf0d4f722ca675f0d362
---
El manejo de errores es un aspecto crucial en el desarrollo de aplicaciones web robustas. En webforJ, el manejo de errores está diseñado para ser flexible y personalizable, lo que permite a los desarrolladores manejar excepciones de la manera que mejor se adapte a las necesidades de su aplicación.

## Resumen {#overview}

En webforJ, el manejo de errores gira en torno a la interfaz `ErrorHandler`. Esta interfaz permite a los desarrolladores definir cómo debe responder su aplicación cuando ocurren excepciones durante la ejecución. Por defecto, webforJ proporciona un `GlobalErrorHandler` que maneja todas las excepciones de manera genérica. Sin embargo, los desarrolladores pueden crear controladores de errores personalizados para excepciones específicas y proporcionar respuestas más ajustadas.

## Descubriendo y utilizando controladores de errores {#discovering-and-using-error-handlers}

webforJ utiliza la Interfaz de Proveedor de Servicios (SPI) de Java para descubrir y cargar controladores de errores.

### Proceso de descubrimiento {#discovery-process}

1. **Registro de servicio**: Los controladores de errores se registran a través del mecanismo `META-INF/services`.
2. **Carga de servicio**: Al iniciar la aplicación, webforJ carga todas las clases listadas en `META-INF/services/com.webforj.error.ErrorHandler`.
3. **Manejo de errores**: Cuando ocurre una excepción, webforJ verifica si existe un controlador de errores para esa excepción específica.

### Selección del controlador {#handler-selection}

- Si existe un controlador específico para la excepción, se utiliza.
- Si no se encuentra un controlador específico, pero se define un controlador de errores global personalizado `WebforjGlobalErrorHandler`, se utiliza.
- Si no se encuentra ninguno, se utiliza el controlador `GlobalErrorHandler` por defecto.

## La interfaz `ErrorHandler` {#the-errorhandler-interface}

La interfaz `ErrorHandler` está diseñada para manejar los errores que ocurren durante la ejecución de una aplicación webforJ. Las aplicaciones que desean gestionar excepciones específicas deben implementar esta interfaz.

### Métodos {#methods}

- **`onError(Throwable throwable, boolean debug)`**: Se llama cuando ocurre un error. Este método debe contener la lógica para manejar la excepción.
- **`showErrorPage(String title, String content)`**: Un método por defecto que muestra la página de error con el título y contenido dados.

### Convención de nombres {#naming-convention}

La clase que implementa debe nombrarse según la excepción que maneja, con el sufijo `ErrorHandler`. Por ejemplo, para manejar `NullPointerException`, la clase debe llamarse `NullPointerExceptionErrorHandler`.

### Registro {#registration}

El controlador de errores personalizado debe registrarse en el archivo `META-INF/services/com.webforj.error.ErrorHandler` para que webforJ pueda descubrirlo y utilizarlo.

## Implementando un controlador de errores personalizado {#implementing-a-custom-error-handler}

Los siguientes pasos describen la implementación de un controlador de errores personalizado para una excepción específica:

### Paso 1: Crear la clase del controlador de errores {#step-1-create-the-error-handler-class}

Cree una nueva clase que implemente la interfaz `ErrorHandler` y esté nombrada según la excepción que maneja.

```java
package com.example.error;

import com.webforj.error.ErrorHandler;

public class NullPointerExceptionErrorHandler implements ErrorHandler {

  @Override
  public void onError(Throwable throwable, boolean debug) {
    // Lógica de manejo personalizada para NullPointerException
    String title = "Excepción de Puntero Nulo";
    String content = "Se encontró un valor nulo donde se requiere un objeto.";

    showErrorPage(title, content);
  }
}
```

:::info Método `showErrorPage()`
El método `showErrorPage` es un método utilitario que utiliza la API de webforJ para enviar el contenido HTML y el título de la página proporcionados al navegador, mostrando una página de error. Cuando ocurre una excepción y la aplicación no puede recuperarse, se vuelve imposible utilizar los componentes de webforJ para construir una página de error personalizada. Sin embargo, la API `Page` sigue siendo accesible, permitiendo al desarrollador redirigir o mostrar una página de error como último intento.
:::

### Paso 2: Registrar el controlador de errores {#step-2-register-the-error-handler}

Cree un archivo llamado `com.webforj.error.ErrorHandler` dentro del directorio `META-INF/services` de su aplicación. Agregue el nombre completamente calificado de su clase de controlador de errores a este archivo.

**Archivo**: `META-INF/services/com.webforj.error.ErrorHandler`

```
com.example.error.NullPointerExceptionErrorHandler
```

Ahora, cada vez que se lance una `NullPointerException`, webforJ seleccionará su controlador registrado y ejecutará su lógica para manejar el error.

## Usando `AutoService` para simplificar el registro {#using-autoservice-to-simplify-registration}

Es fácil que los desarrolladores olviden actualizar o especificar correctamente los descriptores de servicio. Al usar `AutoService` de Google, puede automatizar la generación del archivo `META-INF/services/com.webforj.error.ErrorHandler`. Todo lo que necesita hacer es anotar el controlador de errores con la anotación `AutoService`. Puede aprender más sobre [AutoService aquí](https://github.com/google/auto/blob/main/service/README.md).

```java
@AutoService(ErrorHandler.class)
public class NullPointerExceptionErrorHandler implements ErrorHandler {

  @Override
  public void onError(Throwable throwable, boolean debug) {
    // Lógica de manejo personalizada para NullPointerException
    String title = "Excepción de Puntero Nulo";
    String content = "Se encontró un valor nulo donde se requiere un objeto.";

    showErrorPage(title, content);
  }
}
```

## La clase `GlobalErrorHandler` {#the-globalerrorhandler-class}

El `GlobalErrorHandler` es el controlador de errores por defecto proporcionado por webforJ. Implementa la interfaz `ErrorHandler` y proporciona un manejo de errores genérico.

### Comportamiento {#behavior}

- **Registro**: Los errores se registran en las consolas del servidor y del navegador.
- **Visualización de la página de error**: Dependiendo del modo de depuración, la página de error muestra el seguimiento de pila o un mensaje de error genérico.

### Definiendo un controlador de errores global personalizado {#defining-a-custom-global-error-handler}

Para definir un controlador de errores global, debe crear un nuevo controlador de errores llamado `WebforjGlobalErrorHandler`. Luego siga [los pasos para registrar controladores de errores](#step-2-register-the-error-handler) como se explicó anteriormente. En este caso, webforJ primero busca cualquier controlador de errores personalizado para gestionar excepciones. Si no se encuentran, webforJ recurre al controlador de errores global personalizado.

:::info
Si se registran múltiples `WebforjGlobalErrorHandler`, entonces webforJ selecciona el primero.
:::
