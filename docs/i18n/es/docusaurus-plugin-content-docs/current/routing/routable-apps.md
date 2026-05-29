---
sidebar_position: 2
title: Routable Apps
_i18n_hash: edec1086b0723febd831816f8d1fa76a
---
El enrutamiento en webforJ es una herramienta opcional. Los desarrolladores pueden elegir entre la solución de enrutamiento de webforJ o un modelo tradicional con manipulación de `Frame` y sin enlaces profundos. Para habilitar el enrutamiento, se debe aplicar la anotación **`@Routify`** en el nivel de una clase que implemente `App`. Esto otorga a webforJ la autoridad para gestionar el historial del navegador, responder a eventos de navegación y renderizar los componentes de la aplicación según la URL.

:::info
Para obtener más información sobre cómo construir interfaces de usuario utilizando marcos, componentes integrados y personalizados, visita [Building UIs](../building-ui/overview).
:::

## Propósito de la anotación `@Routify` {#purpose-of-the-routify-annotation}

La **`@Routify`** permite al marco registrar automáticamente rutas, gestionar la visibilidad de los marcos y definir comportamientos de enrutamiento como depuración e inicialización de marcos, lo que permite un enrutamiento dinámico y flexible en la aplicación.

## Uso de `@Routify` {#usage-of-routify}

La anotación **`@Routify`** se aplica en el nivel de clase de la clase principal de la aplicación. Especifica el conjunto de paquetes a escanear en busca de rutas y maneja otras configuraciones relacionadas con el enrutamiento, como la inicialización de marcos y la gestión de visibilidad.

Aquí hay un ejemplo básico:

```java
@Routify(
  packages = {"com.myapp.views"},
  defaultFrameName = "MainFrame",
  initializeFrame = true,
  manageFramesVisibility = false,
  debug = true
)
public class MyApp extends App {

  @Override
  public void run() {
    // La lógica de la aplicación va aquí
  }
}
```

:::tip Configuraciones predeterminadas de Routify
La anotación **`@Routify`** viene con configuraciones predeterminadas razonables. Asume que el paquete actual donde se define la aplicación, junto con todos sus subpaquetes, debe ser escaneado en busca de rutas. Además, asume que la aplicación gestiona solo un marco por defecto. Si tu aplicación sigue esta estructura, no es necesario proporcionar configuraciones personalizadas a la anotación.
:::

## Elementos clave de `@Routify` {#key-elements-of-routify}

### 1. **`packages`** {#1-packages}

El elemento `packages` define qué paquetes deben ser escaneados en busca de definiciones de rutas. Permite el descubrimiento automático de rutas sin registro manual, agilizando el proceso de expansión del sistema de enrutamiento de la aplicación.

```java
@Routify(packages = {"com.myapp.views"})
```

Si no se especifican paquetes, se utiliza el paquete predeterminado de la aplicación.

### 2. **`defaultFrameName`** {#2-defaultframename}

Este elemento especifica el nombre del marco predeterminado que la aplicación inicializa. Los marcos representan contenedores de interfaz de usuario de nivel superior, y esta configuración controla cómo se nombra y gestiona el primer marco.

```java
@Routify(defaultFrameName = "MainFrame")
```

Por defecto, si no se proporciona explícitamente, el valor se establece en `Routify.DEFAULT_FRAME_NAME`.

### 3. **`initializeFrame`** {#3-initializeframe}

El indicador `initializeFrame` determina si el marco debe inicializar automáticamente el primer marco cuando se inicia la aplicación. Configurar esto en `true` simplifica la configuración inicial del marco.

```java
@Routify(initializeFrame = true)
```

### 4. **`manageFramesVisibility`** {#4-manageframesvisibility}

Este elemento controla si el marco debe alternar automáticamente la visibilidad de los marcos durante la navegación. Cuando está habilitado, la ruta coincidente muestra automáticamente el marco correspondiente mientras oculta otros, asegurando una interfaz de usuario limpia y enfocada. Esta configuración solo es relevante cuando tu aplicación gestiona múltiples marcos.

```java
@Routify(manageFramesVisibility = true)
```

### 5. **`debug`** {#5-debug}

El indicador `debug` habilita o deshabilita el modo de depuración del enrutamiento. Cuando está habilitado, se registran en la consola la información y las acciones del enrutamiento para facilitar la depuración durante el desarrollo.

```java
@Routify(debug = true)
```

:::info Modo de depuración del enrutador y modo de depuración de webforJ  
Si el modo de depuración del enrutador está configurado en `true` pero el modo de depuración de webforJ está configurado en `false`, no se mostrará información de depuración en la consola.  
:::
