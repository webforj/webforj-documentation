---
title: Integrating the AppLayout
sidebar_position: 7
draft: true
description: Step 6 - Use the AppLayout component.
_i18n_hash: 14b409262af6d7a8a25a67278f687250
---
En este paso, integrarás las características implementadas en pasos anteriores, como enrutamiento y vistas, en un diseño de aplicación cohesivo. Esta estructura proporcionará un sistema de navegación unificado y áreas de contenido dinámico.

## Ejecutando la aplicación {#running-the-app}

A medida que desarrollas tu aplicación, puedes usar [6-integrating-an-app-layout](https://github.com/webforj/webforj-tutorial/tree/main/6-integrating-an-app-layout) como comparación. Para ver la aplicación en acción:

1. Navega al directorio de nivel superior que contiene el archivo `pom.xml`, este es `6-integrating-an-app-layout` si estás siguiendo la versión en GitHub.

2. Utiliza el siguiente comando de Maven para ejecutar la aplicación de Spring Boot localmente:
    ```bash
    mvn
    ```

Ejecutar la aplicación abrirá automáticamente un nuevo navegador en `http://localhost:8080`.

## Propósito del diseño de la aplicación {#purpose-of-the-app-layout}

El `AppLayout` sirve como la base para gestionar la estructura y el flujo general de tu aplicación. Proporciona:
- **Navegación Global**: Una forma consistente de cambiar entre secciones clave.
- **Renderizado de Contenido Dinámico**: Un diseño centralizado para mostrar vistas enrutadas.

## Usando `AppNav` {#using-appnav}

El componente `AppNav` se utiliza para crear un menú de navegación dentro de la interfaz de usuario de la aplicación. Este menú proporciona enlaces a diferentes vistas en tu aplicación, como el `DemoView`:

```java title="MainLayout.java"
private void setDrawer() {
  AppLayout layout = getBoundComponent();

  AppNav appNav = new AppNav();
  appNav.addItem(new AppNavItem("Dashboard", DemoView.class, FeatherIcon.MESSAGE_CIRCLE.create()));

  layout.addToDrawer(appNav);
}
```

En este ejemplo:
- El menú de navegación se añade al cajón de la aplicación.
- Cada elemento del menú es un `AppNavItem` que especifica:
  - La etiqueta, por ejemplo "Dashboard."
  - La vista de destino por ejemplo `DemoView`.
  - Un icono opcional, por ejemplo un icono de columnas.

## Rutas y salidas del diseño {#layout-routes-and-outlets}

El diseño utiliza rutas y salidas para renderizar dinámicamente contenido dentro de un diseño estructurado. En webforJ:
- **Rutas** definen cómo las vistas se mapean a rutas específicas.
- **Salidas** actúan como marcadores de posición en los diseños donde se muestran las vistas enrutadas.

### Ejemplo: Configurando una ruta de diseño {#example-setting-up-a-layout-route}

En la clase `MainLayout`, la anotación `@Route` la define como el diseño base, y la `DemoView` se renderiza a través de una salida en este diseño:

```java title="MainLayout.java"
@Route("/")
public class MainLayout extends Composite<AppLayout> {
  public MainLayout() {
    setHeader();
    setDrawer();
  }
}
```

La anotación `@Route` para `DemoView` especifica que utiliza `MainLayout` como su salida:

```java title="DemoView.java"
@Route(value = "/demo", outlet = MainLayout.class)
@FrameTitle("Demo")
public class DemoView extends Composite<Div> {
  // Lógica de DemoView
}
```

## Agregando contenido dinámico con `RouteOutlet` {#adding-dynamic-content-with-routeoutlet}

Un `RouteOutlet` muestra dinámicamente vistas según la ruta activa. En el diseño, vistas como `DemoView` se renderizan a través del `RouteOutlet`. Mientras que el `RouteOutlet` se maneja implícitamente mediante la especificación de la salida en las anotaciones de las rutas.
