---
sidebar_position: 2
title: Rutas Anidadas
_i18n_hash: 8c3365b48d048d5bc7c4c47f253acb24
---
Las rutas anidadas permiten que las rutas hijas se rendericen dentro de las rutas padre, creando una interfaz de usuario modular y reutilizable. Las rutas padre definen componentes compartidos, mientras que las rutas hijas se inyectan en salidas específicas dentro de estos componentes padre.

## Definiendo rutas anidadas {#defining-nested-routes}

Las rutas anidadas se crean utilizando el parámetro `outlet` en la anotación `@Route`, que establece una relación padre-hijo. El `outlet` determina dónde se renderizará el componente hijo dentro de la ruta padre.

```java
@Route
public class MainLayout extends Composite<AppLayout> {
  public MainLayout() {
    setHeader();
    setDrawer();
  }
}

@Route(outlet = MainLayout.class)
public class DashboardView extends Composite<Div> {
  public DashboardView() {
    getBoundComponent().add(new H1("Contenido del Dashboard"));
  }
}

@Route(outlet = DashboardView.class)
public class SettingsView extends Composite<Div> {
  public SettingsView() {
    getBoundComponent().add(new H1("Contenido de Configuración"));
  }
}
```

En este ejemplo:

- `MainLayout` es una **[Ruta de Layout](./route-types#layout-routes)**.
- `DashboardView` es una **[Ruta de Vista](./route-types#view-routes)** anidada dentro de `MainLayout`.
- `SettingsView` es una **[Ruta de Vista](./route-types#view-routes)** anidada dentro de `DashboardView`.

Al navegar a `/dashboard/settings`, el enrutador:
1. Renderiza `MainLayout`.
2. Inyecta `DashboardView` en el outlet de `MainLayout`.
3. Finalmente, inyecta `SettingsView` en el outlet de `DashboardView`.

Esta estructura jerárquica se refleja en la URL, donde cada segmento representa un nivel en la jerarquía de componentes:

- **URL**: `/dashboard/settings`
- **Jerarquía**:
  - `MainLayout`: Ruta de Layout
  - `DashboardView`: Ruta de Vista
  - `SettingsView`: Ruta de Vista

Esta estructura asegura componentes de interfaz de usuario compartidos consistentes (como encabezados o menús de navegación) mientras permite que el contenido dentro de esos layouts cambie dinámicamente.
