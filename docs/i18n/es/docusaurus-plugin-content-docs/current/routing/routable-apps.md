---
sidebar_position: 2
title: Routable Apps
description: >-
  Enable webforJ routing with the @Routify annotation to scan packages, manage
  frames, and control browser history.
_i18n_hash: bea0848523a00ddfff8d79265ea699ac
---
El enrutamiento en webforJ es una herramienta opcional. Los desarrolladores pueden elegir entre la solución de enrutamiento de webforJ o un modelo tradicional con manipulación de `Frame` y sin enlaces profundos. Para habilitar el enrutamiento, la anotación **`@Routify`** debe aplicarse a nivel de una clase que implemente `App`. Esto otorga a webforJ la autoridad para gestionar el historial del navegador, responder a eventos de navegación y renderizar los componentes de la aplicación en función de la URL.

:::info
Para aprender más sobre cómo construir interfaces de usuario utilizando marcos, componentes integrados y personalizados, visita [Building UIs](../building-ui/overview).
:::

## Propósito de la Anotación `@Routify` {#purpose-of-the-routify-annotation}

**`@Routify`** permite al marco registrar automáticamente rutas, gestionar la visibilidad del marco y definir comportamientos de enrutamiento como depuración e inicialización de marcos, lo que permite un enrutamiento dinámico y flexible en la aplicación.

## Uso de `@Routify` {#usage-of-routify}

La anotación **`@Routify`** se aplica a nivel de clase de la clase principal de la aplicación. Especifica el conjunto de paquetes a escanear en busca de rutas y gestiona otras configuraciones relacionadas con el enrutamiento, como la inicialización de marcos y la gestión de la visibilidad.

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

:::tip Configuraciones Predeterminadas de Routify
La anotación **`@Routify`** viene con configuraciones predeterminadas razonables. Asume que el paquete actual donde se define la aplicación, junto con todos sus subpaquetes, debe ser escaneado en busca de rutas. Además, asume que la aplicación gestiona solo un marco de forma predeterminada. Si tu aplicación sigue esta estructura, no hay necesidad de proporcionar configuraciones personalizadas a la anotación.
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

El flag `initializeFrame` determina si el marco debe inicializar automáticamente el primer marco cuando la aplicación se inicia. Establecer esto en `true` simplifica la configuración inicial del marco.

```java
@Routify(initializeFrame = true)
```

### 4. **`manageFramesVisibility`** {#4-manageframesvisibility}

Este elemento controla si el marco debe alternar automáticamente la visibilidad de los marcos durante la navegación. Cuando está habilitado, la ruta coincidente muestra automáticamente el marco correspondiente mientras oculta otros, asegurando una UI limpia y enfocada. Esta configuración solo es relevante cuando tu aplicación gestiona múltiples marcos.

```java
@Routify(manageFramesVisibility = true)
```

### 5. **`debug`** {#5-debug}

El flag `debug` habilita o desactiva el modo de depuración del enrutamiento. Cuando está habilitado, la información y las acciones de enrutamiento se registran en la consola para facilitar la depuración durante el desarrollo.

```java
@Routify(debug = true)
```

:::info Modo de Depuración del Router y Modo de Depuración de webforJ
Si el modo de depuración del router está configurado en `true` pero el modo de depuración de webforJ está configurado en `false`, no se mostrará información de depuración en la consola.
:::
