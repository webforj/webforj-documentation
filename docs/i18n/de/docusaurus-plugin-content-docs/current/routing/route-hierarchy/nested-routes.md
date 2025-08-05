---
sidebar_position: 2
title: Nested Routes
_i18n_hash: 5c431e57443e65c98f6f9b2e1098ad99
---
Verschachtelte Routen ermöglichen es, Kindrouten innerhalb von Elternrouten zu rendern, wodurch eine modulare und wiederverwendbare Benutzeroberfläche entsteht. Elternrouten definieren gemeinsame Komponenten, während Kindrouten in spezifische Ausgabebereiche innerhalb dieser Elternkomponenten injiziert werden.

## Definieren von verschachtelten Routen {#defining-nested-routes}

Verschachtelte Routen werden mithilfe des `outlet`-Parameters in der `@Route`-Annotation erstellt, der eine Eltern-Kind-Beziehung herstellt. Das `outlet` bestimmt, wo die Kindkomponente innerhalb der Elternroute gerendert wird.

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
    getBoundComponent().add(new H1("Inhalt des Dashboards"));
  }
}

@Route(outlet = DashboardView.class)
public class SettingsView extends Composite<Div> {
  public SettingsView() {
    getBoundComponent().add(new H1("Inhalt der Einstellungen"));
  }
}
```

In diesem Beispiel:

- `MainLayout` ist eine **[Layout Route](./route-types#layout-routes)**.
- `DashboardView` ist eine **[View Route](./route-types#view-routes)**, die in `MainLayout` eingebettet ist.
- `SettingsView` ist eine **[View Route](./route-types#view-routes)**, die in `DashboardView` eingebettet ist.

Beim Navigieren zu `/dashboard/settings` führt der Router:
1. Rendert `MainLayout`.
2. Injiziert `DashboardView` in das Outlet von `MainLayout`.
3. Injiziert schließlich `SettingsView` in das Outlet von `DashboardView`.

Diese hierarchische Struktur spiegelt sich in der URL wider, wo jedes Segment eine Ebene in der Komponentenhierarchie darstellt:

- **URL**: `/dashboard/settings`
- **Hierarchie**:
  - `MainLayout`: Layout Route
  - `DashboardView`: View Route
  - `SettingsView`: View Route

Diese Struktur gewährleistet konsistente gemeinsame UI-Komponenten (wie Überschriften oder Navigationsmenüs), während der Inhalt innerhalb dieser Layouts dynamisch gewechselt werden kann.
