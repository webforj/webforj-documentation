---
sidebar_position: 1
title: Route Types
_i18n_hash: 75cb67715544b94ca99fc81c736ebcc7
---
Les routes sont classées en deux types principaux, **Routes de Vue** et **Routes de Mise en Page**. Le choix du type de route détermine comment les composants sont mappés aux URL et comment ils interagissent avec d'autres parties de votre application.

## Routes de vue {#view-routes}

Les routes de vue se mappent directement à un segment d'URL et représentent des pages spécifiques dans votre application. Ces routes sont reflétées dans l'URL du navigateur et sont généralement utilisées pour des vues ou des pages distinctes.

```java
@Route(value = "home")
public class HomeView extends Composite<Div> {
  private final Div self = getBoundComponent();

  public HomeView() {
    self.add(new H1("Page d'accueil"));
  }
}
```

- **URL**: `/home`
- **Composant rendu**: `HomeView`

Dans cet exemple, naviguer vers `/home` rend le composant `HomeView`.

## Routes de mise en page {#layout-routes}

Les routes de mise en page enveloppent les vues enfants sans contribuer à l'URL. Les mises en page fournissent des éléments d'interface utilisateur partagés tels que des en-têtes ou des barres latérales qui sont cohérents à travers plusieurs vues. Les routes enfants sont rendues à l'intérieur de la zone de contenu de la mise en page.

```java
@Route(type = Route.Type.LAYOUT)
public class MainLayout extends Composite<AppLayout> {
  public MainLayout() {
    setHeader();
    setDrawer();
  }
}
```

Dans ce cas, `MainLayout` est une route de mise en page qui entoure les vues enfants. Elle définit des éléments d'interface utilisateur communs comme un en-tête et un tiroir. Les routes enfants associées à cette mise en page seront injectées dans la zone de contenu du composant `AppLayout`.

## Détection automatique des types de routes {#auto-detection-of-route-types}

Par défaut, le type de route est automatiquement détecté selon que la route est une **vue** ou une **mise en page** en fonction du nom de la classe :

- Les classes se terminant par `Layout` sont considérées comme **routes de mise en page**.
- Les classes se terminant par `View` sont considérées comme **routes de vue**.

Alternativement, les développeurs peuvent spécifier manuellement le type de route en définissant `Route.Type` dans l'annotation `@Route`.

```java
// Détecté automatiquement comme Layout
@Route
public class MainLayout extends Composite<AppLayout> {
  public MainLayout() {
    setHeader();
    setDrawer();
  }
}
```

```java
// Détecté automatiquement comme View
@Route(outlet = MainLayout.class)
public class DashboardView extends Composite<Div> {
  private final Div self = getBoundComponent();

  public DashboardView() {
    self.add(new H1("Contenu du tableau de bord"));
  }
}
```
