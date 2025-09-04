---
sidebar_position: 2
title: Nested Routes
_i18n_hash: 8c3365b48d048d5bc7c4c47f253acb24
---
Verschachtelte Routen ermöglichen es, untergeordnete Routen innerhalb von übergeordneten Routen darzustellen, wodurch eine modulare und wiederverwendbare Benutzeroberfläche entsteht. Übergeordnete Routen definieren gemeinsame Komponenten, während untergeordnete Routen in spezifische Ausgabestellen innerhalb dieser übergeordneten Komponenten eingefügt werden.

## Definieren von verschachtelten Routen {#defining-nested-routes}

Verschachtelte Routen werden mit dem Parameter `outlet` in der Annotation `@Route` erstellt, die eine Eltern-Kind-Beziehung herstellt. Das `outlet` bestimmt, wo die untergeordnete Komponente innerhalb der übergeordneten Route gerendert wird.

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
    getBoundComponent().add(new H1("Dashboard-Inhalt"));
  }
}

@Route(outlet = DashboardView.class)
public class SettingsView extends Composite<Div> {
  public SettingsView() {
    getBoundComponent().add(new H1("Einstellungen-Inhalt"));
  }
}
```

In diesem Beispiel:

- `MainLayout` ist eine **[Layout-Route](./route-types#layout-routes)**.
- `DashboardView` ist eine **[View-Route](./route-types#view-routes)**, die in `MainLayout` verschachtelt ist.
- `SettingsView` ist eine **[View-Route](./route-types#view-routes)**, die in `DashboardView` verschachtelt ist.

Beim Navigieren zu `/dashboard/settings` führt der Router Folgendes aus:
1. Rendert `MainLayout`.
2. Injiziert `DashboardView` in das Outlet von `MainLayout`.
3. Injiziert schließlich `SettingsView` in das Outlet von `DashboardView`.

Diese hierarchische Struktur spiegelt sich in der URL wider, wobei jeder Abschnitt ein Level in der Komponentenhierarchie darstellt:

- **URL**: `/dashboard/settings`
- **Hierarchie**:
  - `MainLayout`: Layout-Route
  - `DashboardView`: View-Route
  - `SettingsView`: View-Route

Diese Struktur stellt konsistente gemeinsame UI-Komponenten (wie Kopfzeilen oder Navigationsmenüs) sicher, während der Inhalt innerhalb dieser Layouts dynamisch geändert werden kann.
