---
sidebar_position: 2
title: Nested Routes
_i18n_hash: 5324d20d84c35f52067d0ba6d6448b71
---
Las rutas anidadas permiten que las rutas secundarias se representen dentro de las rutas principales, creando una interfaz de usuario modular y reutilizable. Las rutas principales definen componentes compartidos, mientras que las rutas secundarias se inyectan en salidas específicas dentro de estos componentes principales.

## Definiendo rutas anidadas {#defining-nested-routes}

Las rutas anidadas se crean utilizando el parámetro `outlet` en la anotación `@Route`, que establece una relación de padre-hijo. El `outlet` determina dónde se renderizará el componente secundario dentro de la ruta principal.

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
  private final Div self = getBoundComponent();

  public DashboardView() {
    self.add(new H1("Contenido del Dashboard"));
  }
}

@Route(outlet = DashboardView.class)
public class SettingsView extends Composite<Div> {
  private final Div self = getBoundComponent();

  public SettingsView() {
    self.add(new H1("Contenido de Configuración"));
  }
}
```

En este ejemplo:

- `MainLayout` es una **[Layout Route](./route-types#layout-routes)**.
- `DashboardView` es una **[View Route](./route-types#view-routes)** anidada dentro de `MainLayout`.
- `SettingsView` es una **[View Route](./route-types#view-routes)** anidada dentro de `DashboardView`.

Al navegar a `/dashboard/settings`, el enrutador:
1. Renderiza `MainLayout`.
2. Inyecta `DashboardView` en el outlet de `MainLayout`.
3. Finalmente, inyecta `SettingsView` en el outlet de `DashboardView`.

Esta estructura jerárquica se refleja en la URL, donde cada segmento representa un nivel en la jerarquía de componentes:

- **URL**: `/dashboard/settings`
- **Jerarquía**:
  - `MainLayout`: Layout Route
  - `DashboardView`: View Route
  - `SettingsView`: View Route

Esta estructura asegura componentes de UI compartidos consistentes (como encabezados o menús de navegación) mientras permite que el contenido dentro de esos diseños cambie dinámicamente.
