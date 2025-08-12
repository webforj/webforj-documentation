---
sidebar_position: 2
title: Routable Apps
_i18n_hash: 6d09e8327e3391cedd4e8059d9390d09
---
El enrutamiento en webforJ es una herramienta opcional. Los desarrolladores pueden elegir entre la solución de enrutamiento de webforJ o un modelo tradicional con manipulación de `Frame` y sin enlaces profundos. Para habilitar el enrutamiento, se debe aplicar la anotación **`@Routify`** a nivel de la clase que implementa `App`. Esto otorga a webforJ la autoridad para gestionar el historial del navegador, responder a eventos de navegación y renderizar los componentes de la aplicación según la URL.

:::info
Para obtener más información acerca de la construcción de interfaces de usuario utilizando frames, componentes integrados y personalizados, visita la sección [Building UIs](../building-ui/basics).
:::

## Propósito de la Anotación `@Routify` {#purpose-of-the-routify-annotation}

**`@Routify`** permite que el marco registre automáticamente rutas, gestione la visibilidad de los frames y defina comportamientos de enrutamiento como la depuración y la inicialización de frames, permitiendo un enrutamiento dinámico y flexible en la aplicación.

## Uso de `@Routify` {#usage-of-routify}

La anotación **`@Routify`** se aplica a nivel de clase de la clase principal de la aplicación. Especifica el conjunto de paquetes que se deben escanear en busca de rutas y maneja otras configuraciones relacionadas con el enrutamiento, como la inicialización de frames y la gestión de visibilidad.

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

:::tip Configuraciones Predetermiadas de Routify
La anotación **`@Routify`** viene con configuraciones predeterminadas razonables. Asume que el paquete actual donde se define la aplicación, junto con todos sus subpaquetes, deben ser escaneados en busca de rutas. Además, asume que la aplicación gestiona solo un frame por defecto. Si tu aplicación sigue esta estructura, no es necesario proporcionar configuraciones personalizadas a la anotación.
:::

## Elementos Clave de `@Routify` {#key-elements-of-routify}

### 1. **`packages`** {#1-packages}

El elemento `packages` define qué paquetes deben ser escaneados en busca de definiciones de rutas. Permite el descubrimiento automático de rutas sin necesidad de registro manual, simplificando el proceso de expansión del sistema de enrutamiento de la aplicación.

```java
@Routify(packages = {"com.myapp.views"})
```

Si no se especifican paquetes, se utiliza el paquete predeterminado de la aplicación.

### 2. **`defaultFrameName`** {#2-defaultframename}

Este elemento especifica el nombre del frame predeterminado que la aplicación inicializa. Los frames representan contenedores de UI de nivel superior, y esta configuración controla cómo se nombra y gestiona el primer frame.

```java
@Routify(defaultFrameName = "MainFrame")
```

Por defecto, si no se proporciona explícitamente, el valor se establece en `Routify.DEFAULT_FRAME_NAME`.

### 3. **`initializeFrame`** {#3-initializeframe}

El flag `initializeFrame` determina si el marco debe inicializar automáticamente el primer frame cuando la aplicación se inicia. Establecer esto en `true` simplifica la configuración inicial del frame.

```java
@Routify(initializeFrame = true)
```

### 4. **`manageFramesVisibility`** {#4-manageframesvisibility}

Este elemento controla si el marco debe alternar automáticamente la visibilidad de los frames durante la navegación. Cuando está habilitado, la ruta coincidente muestra automáticamente el frame correspondiente mientras oculta otros, asegurando una interfaz limpia y enfocada. Esta configuración solo es relevante cuando tu aplicación gestiona múltiples frames.

```java
@Routify(manageFramesVisibility = true)
```

### 5. **`debug`** {#5-debug}

El flag `debug` habilita o deshabilita el modo de depuración de enrutamiento. Cuando está habilitado, la información y las acciones de enrutamiento se registran en la consola para facilitar la depuración durante el desarrollo. 

```java
@Routify(debug = true)
```

:::info Modo de Depuración del Enrutador y Modo de Depuración de webforJ  
Si el modo de depuración del enrutador está configurado en `true` pero el modo de depuración de webforJ está configurado en `false`, no se mostrará información de depuración en la consola.  
:::
