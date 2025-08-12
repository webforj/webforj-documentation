---
sidebar_position: 2
title: Nested Routes
_i18n_hash: 8c3365b48d048d5bc7c4c47f253acb24
---
Geneste routes stellen het mogelijk om kindroutes binnen ouderroutes weer te geven, wat resulteert in een modulaire en herbruikbare gebruikersinterface. Ouderroutes definiëren gedeelde componenten, terwijl kindroutes worden ingevoegd in specifieke uitgangen binnen deze oudercomponenten.

## Geneste routes definiëren {#defining-nested-routes}

Geneste routes worden gemaakt met behulp van de `outlet` parameter in de `@Route` annotatie, die een ouder-kind relatie tot stand brengt. De `outlet` bepaalt waar de kindcomponent binnen de ouderroute zal worden weergegeven.

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
    getBoundComponent().add(new H1("Dashboard Inhoud"));
  }
}

@Route(outlet = DashboardView.class)
public class SettingsView extends Composite<Div> {
  public SettingsView() {
    getBoundComponent().add(new H1("Instellingen Inhoud"));
  }
}
```

In dit voorbeeld:

- `MainLayout` is een **[Layout Route](./route-types#layout-routes)**.
- `DashboardView` is een **[View Route](./route-types#view-routes)** genest binnen `MainLayout`.
- `SettingsView` is een **[View Route](./route-types#view-routes)** genest binnen `DashboardView`.

Bij het navigeren naar `/dashboard/settings`:

1. Wordt `MainLayout` weergegeven.
2. Wordt `DashboardView` ingevoegd in de outlet van `MainLayout`.
3. Wordt uiteindelijk `SettingsView` ingevoegd in de outlet van `DashboardView`.

Deze hiërarchische structuur wordt weerspiegeld in de URL, waar elk segment een niveau in de componenthiërarchie vertegenwoordigt:

- **URL**: `/dashboard/settings`
- **Hiërarchie**:
  - `MainLayout`: Layout Route
  - `DashboardView`: View Route
  - `SettingsView`: View Route

Deze structuur zorgt voor consistente gedeelde gebruikersinterfacecomponenten (zoals kopteksten of navigatiemenu's) terwijl de inhoud binnen die lay-outs dynamisch kan veranderen.
