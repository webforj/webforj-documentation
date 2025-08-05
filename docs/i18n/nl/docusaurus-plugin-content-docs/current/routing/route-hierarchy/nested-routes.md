---
sidebar_position: 2
title: Nested Routes
_i18n_hash: 5c431e57443e65c98f6f9b2e1098ad99
---
Geneste routes stellen in staat om kindroutes binnen ouderroutes te renderen, wat een modulaire en herbruikbare gebruikersinterface creëert. Ouder routes definiëren gedeelde componenten, terwijl kindroutes worden geïnjecteerd in specifieke uitgangen binnen deze oudercomponenten.

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
    getBoundComponent().add(new H1("Dashboard Content"));
  }
}

@Route(outlet = DashboardView.class)
public class SettingsView extends Composite<Div> {
  public SettingsView() {
    getBoundComponent().add(new H1("Settings Content"));
  }
}
```

In dit voorbeeld:

- `MainLayout` is een **[Layout Route](./route-types#layout-routes)**.
- `DashboardView` is een **[View Route](./route-types#view-routes)** genest binnen `MainLayout`.
- `SettingsView` is een **[View Route](./route-types#view-routes)** genest binnen `DashboardView`.

Wanneer je naar `/dashboard/settings` navigeert, gebeurt het volgende:
1. `MainLayout` wordt weergegeven.
2. `DashboardView` wordt geïnjecteerd in de outlet van `MainLayout`.
3. Tenslotte wordt `SettingsView` geïnjecteerd in de outlet van `DashboardView`.

Deze hiërarchische structuur wordt weerspiegeld in de URL, waar elk segment een niveau in de componenthiërarchie vertegenwoordigt:

- **URL**: `/dashboard/settings`
- **Hiërarchie**:
  - `MainLayout`: Layout Route
  - `DashboardView`: View Route
  - `SettingsView`: View Route

Deze structuur zorgt voor consistente gedeelde UI-componenten (zoals koppen of navigatiemenu's) terwijl de inhoud binnen deze lay-outs dynamisch kan veranderen.
