---
title: Error Handling
sidebar_position: 5
_i18n_hash: 7957d907ae8a5bd9e7b3f7c2fdba2623
---
El manejo de errores es un aspecto crucial del desarrollo de aplicaciones web confiables. En webforJ, el manejo de errores está diseñado para ser flexible y personalizable, permitiendo a los desarrolladores gestionar excepciones de la manera que mejor se adapte a las necesidades de su aplicación.

## Overview {#overview}

En webforJ, el manejo de errores gira en torno a la interfaz `ErrorHandler`. Esta interfaz permite a los desarrolladores definir cómo debería responder su aplicación cuando ocurren excepciones durante la ejecución. De forma predeterminada, webforJ proporciona un `GlobalErrorHandler` que maneja todas las excepciones de manera genérica. Sin embargo, los desarrolladores pueden crear controladores de errores personalizados para excepciones específicas para proporcionar respuestas más adecuadas.

## Discovering and using error handlers {#discovering-and-using-error-handlers}

webforJ utiliza la Interfaz de Proveedor de Servicios (SPI) de Java para descubrir y cargar controladores de errores.

### Discovery process {#discovery-process}

1. **Registro de Servicio**: Los controladores de errores se registran a través del mecanismo `META-INF/services`.
2. **Carga de Servicio**: Al iniciar la aplicación, webforJ carga todas las clases listadas en `META-INF/services/com.webforj.error.ErrorHandler`.
3. **Manejo de Errores**: Cuando ocurre una excepción, webforJ verifica si existe un controlador de errores para esa excepción específica.

### Handler selection {#handler-selection}

- Si existe un controlador específico para la excepción, se utiliza.
- Si no se encuentra un controlador específico, pero se define un controlador de errores global personalizado `WebforjGlobalErrorHandler`, se utiliza.
- Si no se encuentra ninguno, se utiliza el `GlobalErrorHandler` por defecto.

## The `ErrorHandler` Interface {#the-errorhandler-interface}

La interfaz `ErrorHandler` está diseñada para manejar errores que ocurren durante la ejecución de una aplicación webforJ. Las aplicaciones que quieren gestionar excepciones específicas deben implementar esta interfaz.

### Methods {#methods}

- **`onError(Throwable throwable, boolean debug)`**: Se llama cuando ocurre un error. Este método debe contener la lógica para manejar la excepción.
- **`showErrorPage(String title, String content)`**: Un método predeterminado que muestra la página de error con el título y contenido dados.

### Naming convention {#naming-convention}

La clase implementadora debe nombrarse según la excepción que maneja, con el sufijo `ErrorHandler`. Por ejemplo, para manejar `NullPointerException`, la clase debe llamarse `NullPointerExceptionErrorHandler`.

### Registration {#registration}

El controlador de errores personalizado debe registrarse en el archivo `META-INF/services/com.webforj.error.ErrorHandler` para que webforJ pueda descubrirlo y utilizarlo.

## Implementing a custom error handler {#implementing-a-custom-error-handler}

Los siguientes pasos detallan la implementación de un controlador de errores personalizado para una excepción específica:

### Step 1: Create the error handler class {#step-1-create-the-error-handler-class}

Crea una nueva clase que implemente la interfaz `ErrorHandler` y que esté nombrada de acuerdo a la excepción que maneja.

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

:::info `showErrorPage()` method
El método `showErrorPage` es un método de utilidad que utiliza la API de webforJ para enviar el contenido HTML proporcionado y el título de la página al navegador, mostrando una página de error. Cuando ocurre una excepción y la aplicación no puede recuperarse, se vuelve imposible usar los componentes de webforJ para construir una página de error personalizada. Sin embargo, la API `Page` sigue siendo accesible, lo que permite al desarrollador redirigir o mostrar una página de error como último intento.
:::

### Step 2: Register the error handler {#step-2-register-the-error-handler}

Crea un archivo llamado `com.webforj.error.ErrorHandler` dentro del directorio `META-INF/services` de tu aplicación. Agrega el nombre completamente calificado de tu clase de controlador de errores a este archivo.

**Archivo**: `META-INF/services/com.webforj.error.ErrorHandler`

```
com.example.error.NullPointerExceptionErrorHandler
```

Ahora, cada vez que se lance una `NullPointerException`, webforJ selecciona tu controlador registrado y ejecuta su lógica para manejar el error.

## Using `AutoService` to simplify registration {#using-autoservice-to-simplify-registration}

Es fácil que los desarrolladores olviden actualizar o especificar correctamente los descriptores de servicio. Al usar `AutoService` de Google, puedes automatizar la generación del archivo `META-INF/services/com.webforj.error.ErrorHandler`. Todo lo que necesitas hacer es anotar el controlador de errores con la anotación `AutoService`. Puedes aprender más sobre [AutoService aquí](https://github.com/google/auto/blob/main/service/README.md).

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

## The `GlobalErrorHandler` class {#the-globalerrorhandler-class}

El `GlobalErrorHandler` es el controlador de errores predeterminado proporcionado por webforJ. Implementa la interfaz `ErrorHandler` y proporciona un manejo de errores genérico.

### Behavior {#behavior}

- **Registro**: Los errores se registran tanto en el servidor como en las consolas del navegador.
- **Visualización de Página de Error**: Dependiendo del modo de depuración, la página de error muestra el seguimiento de pila o un mensaje de error genérico.

### Defining a custom global error handler {#defining-a-custom-global-error-handler}

Para definir un controlador de errores global, necesitas crear un nuevo controlador de errores llamado `WebforjGlobalErrorHandler`. Luego, sigue [los pasos para registrar controladores de errores](#step-2-register-the-error-handler) como se explicó anteriormente. En este caso, webforJ primero busca cualquier controlador de errores personalizado para gestionar excepciones. Si no se encuentran, webforJ recurre al controlador de errores global personalizado.

:::info
Si se registran múltiples `WebforjGlobalErrorHandler`, entonces webforJ selecciona el primero.
:::
