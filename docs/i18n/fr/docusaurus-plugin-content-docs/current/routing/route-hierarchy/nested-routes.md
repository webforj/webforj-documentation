---
sidebar_position: 2
title: Nested Routes
_i18n_hash: 8c3365b48d048d5bc7c4c47f253acb24
---
Les routes imbriquées permettent aux routes enfants d'être rendues dans des routes parentes, créant une interface utilisateur modulaire et réutilisable. Les routes parentes définissent des composants partagés, tandis que les routes enfants sont injectées dans des emplacements spécifiques à l'intérieur de ces composants parents.

## Définir des routes imbriquées {#defining-nested-routes}

Les routes imbriquées sont créées en utilisant le paramètre `outlet` dans l'annotation `@Route`, qui établit une relation parent-enfant. L'`outlet` détermine où le composant enfant sera rendu dans la route parente.

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
    getBoundComponent().add(new H1("Contenu du tableau de bord"));
  }
}

@Route(outlet = DashboardView.class)
public class SettingsView extends Composite<Div> {
  public SettingsView() {
    getBoundComponent().add(new H1("Contenu des paramètres"));
  }
}
```

Dans cet exemple :

- `MainLayout` est une **[Route de mise en page](./route-types#layout-routes)**.
- `DashboardView` est une **[Route de vue](./route-types#view-routes)** imbriquée dans `MainLayout`.
- `SettingsView` est une **[Route de vue](./route-types#view-routes)** imbriquée dans `DashboardView`.

Lors de la navigation vers `/dashboard/settings`, le routeur :
1. Rendre `MainLayout`.
2. Injecte `DashboardView` dans l'outlet de `MainLayout`.
3. Enfin, injecte `SettingsView` dans l'outlet de `DashboardView`.

Cette structure hiérarchique se reflète dans l'URL, où chaque segment représente un niveau dans la hiérarchie des composants :

- **URL** : `/dashboard/settings`
- **Hiérarchie** :
  - `MainLayout` : Route de mise en page
  - `DashboardView` : Route de vue
  - `SettingsView` : Route de vue

Cette structure garantit des composants UI partagés cohérents (tels que des en-têtes ou des menus de navigation) tout en permettant au contenu de ces mises en page de changer dynamiquement.
