---
sidebar_position: 2
title: Nested Routes
_i18n_hash: 5c431e57443e65c98f6f9b2e1098ad99
---
Las rutas anidadas permiten que las rutas secundarias se rendericen dentro de las rutas principales, creando una interfaz de usuario modular y reutilizable. Las rutas principales definen componentes compartidos, mientras que las rutas secundarias se inyectan en salidas específicas dentro de estos componentes principales.

## Definiendo rutas anidadas {#defining-nested-routes}

Las rutas anidadas se crean utilizando el parámetro `outlet` en la anotación `@Route`, que establece una relación padre-hijo. El `outlet` determina dónde se renderizará el componente secundario dentro de la ruta principal.

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
    getBoundComponent().add(new H1("Contenido del Panel de Control"));
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

- `MainLayout` es una **[Ruta de Diseño](./route-types#layout-routes)**.
- `DashboardView` es una **[Ruta de Vista](./route-types#view-routes)** anidada dentro de `MainLayout`.
- `SettingsView` es una **[Ruta de Vista](./route-types#view-routes)** anidada dentro de `DashboardView`.

Al navegar a `/dashboard/settings`, el enrutador:
1. Renderiza `MainLayout`.
2. Inyecta `DashboardView` en el outlet de `MainLayout`.
3. Finalmente, inyecta `SettingsView` en el outlet de `DashboardView`.

Esta estructura jerárquica se refleja en la URL, donde cada segmento representa un nivel en la jerarquía de componentes:

- **URL**: `/dashboard/settings`
- **Jerarquía**:
  - `MainLayout`: Ruta de Diseño
  - `DashboardView`: Ruta de Vista
  - `SettingsView`: Ruta de Vista

Esta estructura asegura componentes de interfaz de usuario compartidos de manera consistente (como encabezados o menús de navegación) mientras permite que el contenido dentro de esos diseños cambie dinámicamente.
