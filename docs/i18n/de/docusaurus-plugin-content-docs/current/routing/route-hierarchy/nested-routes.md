---
sidebar_position: 2
title: Nested Routes
_i18n_hash: 5324d20d84c35f52067d0ba6d6448b71
---
Verschachtelte Routen ermöglichen es, untergeordnete Routen innerhalb von übergeordneten Routen darzustellen, wodurch eine modulare und wiederverwendbare Benutzeroberfläche entsteht. Übergeordnete Routen definieren gemeinsame Komponenten, während untergeordnete Routen in spezifische Ausgaben innerhalb dieser übergeordneten Komponenten eingefügt werden.

## Definieren von verschachtelten Routen {#defining-nested-routes}

Verschachtelte Routen werden mit dem Parameter `outlet` in der `@Route`-Annotation erstellt, die eine Eltern-Kind-Beziehung herstellt. Das `outlet` bestimmt, wo die untergeordnete Komponente innerhalb der übergeordneten Route gerendert wird.

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
    self.add(new H1("Inhalt des Dashboards"));
  }
}

@Route(outlet = DashboardView.class)
public class SettingsView extends Composite<Div> {
  private final Div self = getBoundComponent();

  public SettingsView() {
    self.add(new H1("Inhalt der Einstellungen"));
  }
}
```

In diesem Beispiel:

- `MainLayout` ist eine **[Layout-Route](./route-types#layout-routes)**.
- `DashboardView` ist eine **[View-Route](./route-types#view-routes)**, die innerhalb von `MainLayout` verschachtelt ist.
- `SettingsView` ist eine **[View-Route](./route-types#view-routes)**, die innerhalb von `DashboardView` verschachtelt ist.

Bei der Navigation zu `/dashboard/settings` führt der Router:
1. `MainLayout` aus.
2. Injiziert `DashboardView` in das Outlet von `MainLayout`.
3. Schließlich injiziert `SettingsView` in das Outlet von `DashboardView`.

Diese hierarchische Struktur spiegelt sich in der URL wider, wobei jeder Abschnitt ein Niveau in der Komponenten-Hierarchie darstellt:

- **URL**: `/dashboard/settings`
- **Hierarchie**:
  - `MainLayout`: Layout-Route
  - `DashboardView`: View-Route
  - `SettingsView`: View-Route

Diese Struktur gewährleistet konsistente, gemeinsame UI-Komponenten (wie Kopfleisten oder Navigationsmenüs), während der Inhalt innerhalb dieser Layouts dynamisch geändert werden kann.
