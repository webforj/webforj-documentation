---
sidebar_position: 1
title: Route Types
_i18n_hash: ff067ccd8461640c772c1f8fa0dcc856
---
Las rutas se clasifican en dos tipos principales, **Rutas de Vista** y **Rutas de Diseño**. La elección del tipo de ruta determina cómo se mapean los componentes a las URLs y cómo interactúan con otras partes de tu aplicación.

## Rutas de vista {#view-routes}

Las rutas de vista se mapean directamente a un segmento de URL y representan páginas específicas en tu aplicación. Estas rutas se reflejan en la URL del navegador y se utilizan típicamente para vistas o páginas distintas.

```java
@Route(value = "home")
public class HomeView extends Composite<Div> {
  public HomeView() {
    Div content = getBoundComponent();
    content.add(new H1("Página de inicio"));
  }
}
```

- **URL**: `/home`
- **Componente Renderizado**: `HomeView`

En este ejemplo, navegar a `/home` renderiza el componente `HomeView`.

## Rutas de diseño {#layout-routes}

Las rutas de diseño envuelven vistas secundarias sin contribuir a la URL. Los diseños proporcionan elementos de interfaz de usuario compartidos, como encabezados o barras laterales que son consistentes en varias vistas. Las rutas secundarias se renderizan dentro del área de contenido del diseño.

```java
@Route(type = Route.Type.LAYOUT)
public class MainLayout extends Composite<AppLayout> {
  public MainLayout() {
    setHeader();
    setDrawer();
  }
}
```

En este caso, `MainLayout` es una ruta de diseño que envuelve vistas secundarias. Define elementos de UI comunes como un encabezado y un cajón. Las rutas secundarias asociadas con este diseño se inyectarán en el área de contenido del componente `AppLayout`.

## Detección automática de tipos de rutas {#auto-detection-of-route-types}

Por defecto, el tipo de ruta se detecta automáticamente, ya sea que la ruta sea una **vista** o un **diseño** basado en el nombre de la clase:

- Las clases que terminan en `Layout` se tratan como **rutas de diseño**.
- Las clases que terminan en `View` se tratan como **rutas de vista**.

Alternativamente, los desarrolladores pueden especificar manualmente el tipo de ruta estableciendo `Route.Type` en la anotación `@Route`.

```java
// Detectado automáticamente como Layout
@Route
public class MainLayout extends Composite<AppLayout> {
  public MainLayout() {
    setHeader();
    setDrawer();
  }
}
```

```java
// Detectado automáticamente como View
@Route(outlet = MainLayout.class)
public class DashboardView extends Composite<Div> {
  public DashboardView() {
    Div content = getBoundComponent();
    content.add(new H1("Contenido del Dashboard"));
  }
}
```
