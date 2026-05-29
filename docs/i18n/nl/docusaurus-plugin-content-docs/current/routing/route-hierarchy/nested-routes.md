---
sidebar_position: 2
title: Nested Routes
_i18n_hash: 5324d20d84c35f52067d0ba6d6448b71
---
Geneste routes stellen het mogelijk om child routes te renderen binnen parent routes, waardoor een modulaire en herbruikbare UI ontstaat. Parent routes definiëren gedeelde componenten, terwijl child routes worden geïnjecteerd in specifieke uitgangen binnen deze parent componenten.

## Geneste routes definiëren {#defining-nested-routes}

Geneste routes worden gemaakt met de `outlet` parameter in de `@Route` annotatie, die een ouder-kind relatie tot stand brengt. De `outlet` bepaalt waar de child component zal worden gerenderd binnen de parent route.

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
    self.add(new H1("Dashboard Inhoud"));
  }
}

@Route(outlet = DashboardView.class)
public class SettingsView extends Composite<Div> {
  private final Div self = getBoundComponent();

  public SettingsView() {
    self.add(new H1("Instellingen Inhoud"));
  }
}
```

In dit voorbeeld:

- `MainLayout` is een **[Layout Route](./route-types#layout-routes)**.
- `DashboardView` is een **[View Route](./route-types#view-routes)** genest binnen `MainLayout`.
- `SettingsView` is een **[View Route](./route-types#view-routes)** genest binnen `DashboardView`.

Bij het navigeren naar `/dashboard/settings`, doet de router:
1. Rendert `MainLayout`.
2. Injecteert `DashboardView` in de outlet van `MainLayout`.
3. Injecteert tenslotte `SettingsView` in de outlet van `DashboardView`.

Deze hiërarchische structuur wordt weerspiegeld in de URL, waar elk segment een niveau in de componenthiërarchie vertegenwoordigt:

- **URL**: `/dashboard/settings`
- **Hiërarchie**:
  - `MainLayout`: Layout Route
  - `DashboardView`: View Route
  - `SettingsView`: View Route

Deze structuur zorgt voor consistente gedeelde UI-componenten (zoals headers of navigatiemenu's) terwijl de inhoud binnen die lay-outs dynamisch kan veranderen.
