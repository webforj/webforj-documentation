---
sidebar_position: 2
title: Nested Routes
_i18n_hash: 5324d20d84c35f52067d0ba6d6448b71
---
Les routes imbriquées permettent de rendre des routes enfants au sein de routes parent, créant ainsi une interface utilisateur modulaire et réutilisable. Les routes parent définissent des composants partagés, tandis que les routes enfants sont injectées dans des points de sortie spécifiques au sein de ces composants parent.

## Définir des routes imbriquées {#defining-nested-routes}

Les routes imbriquées sont créées en utilisant le paramètre `outlet` dans l'annotation `@Route`, qui établit une relation parent-enfant. L'`outlet` détermine où le composant enfant sera rendu dans la route parent.

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
    self.add(new H1("Contenu du tableau de bord"));
  }
}

@Route(outlet = DashboardView.class)
public class SettingsView extends Composite<Div> {
  private final Div self = getBoundComponent();

  public SettingsView() {
    self.add(new H1("Contenu des paramètres"));
  }
}
```

Dans cet exemple :

- `MainLayout` est une **[Layout Route](./route-types#layout-routes)**.
- `DashboardView` est une **[View Route](./route-types#view-routes)** imbriquée à l'intérieur de `MainLayout`.
- `SettingsView` est une **[View Route](./route-types#view-routes)** imbriquée à l'intérieur de `DashboardView`.

Lors de la navigation vers `/dashboard/settings`, le routeur :
1. Rendu `MainLayout`.
2. Injecte `DashboardView` dans le point de sortie de `MainLayout`.
3. Enfin, injecte `SettingsView` dans le point de sortie de `DashboardView`.

Cette structure hiérarchique est reflétée dans l'URL, où chaque segment représente un niveau dans la hiérarchie des composants :

- **URL** : `/dashboard/settings`
- **Hiérarchie** :
  - `MainLayout` : Layout Route
  - `DashboardView` : View Route
  - `SettingsView` : View Route

Cette structure garantit des composants d'interface utilisateur partagés cohérents (tels que des en-têtes ou des menus de navigation) tout en permettant au contenu de ces mises en page de changer dynamiquement.
