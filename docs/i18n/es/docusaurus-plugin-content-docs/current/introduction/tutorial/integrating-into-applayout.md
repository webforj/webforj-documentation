---
title: Integrating the AppLayout
sidebar_position: 6
draft: true
_i18n_hash: fd2f3844bbcb102adf05ae01b07ff8d8
---
En este paso, integrarás las características implementadas en pasos anteriores, como el enrutamiento y las vistas, en un diseño cohesivo de la aplicación. Esta estructura proporcionará un sistema de navegación unificado y áreas de contenido dinámico.

## Propósito del diseño de la aplicación {#purpose-of-the-app-layout}

El `AppLayout` sirve como la base para gestionar la estructura general y el flujo de tu aplicación. Proporciona:
- **Navegación Global**: Una manera consistente de cambiar entre secciones clave.
- **Renderización de Contenido Dinámico**: Un diseño centralizado para mostrar vistas enrutadas.

## Uso de `AppNav` {#using-appnav}

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
- El menú de navegación se agrega al cajón de la aplicación.
- Cada elemento del menú es un `AppNavItem` que especifica:
  - La etiqueta, por ejemplo "Dashboard."
  - La vista objetivo, por ejemplo `DemoView`.
  - Un icono opcional, por ejemplo un icono de columnas.

## Rutas y salidas del diseño {#layout-routes-and-outlets}

El diseño utiliza rutas y salidas para renderizar dinámicamente el contenido dentro de un diseño estructurado. En webforJ:
- **Las rutas** definen cómo las vistas se mapean a rutas específicas.
- **Las salidas** actúan como marcadores de posición en los diseños donde se muestran las vistas enrutadas.

### Ejemplo: Configurando una ruta de diseño {#example-setting-up-a-layout-route}

En la clase `MainLayout`, la anotación `@Route` la define como el diseño base, y `DemoView` se renderiza a través de una salida en este diseño:

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

Un `RouteOutlet` muestra dinámicamente vistas basadas en la ruta activa. En el diseño, vistas como `DemoView` se renderizan a través del `RouteOutlet`. Mientras que el `RouteOutlet` se maneja implícitamente por la especificación de salida en las anotaciones de ruta.
