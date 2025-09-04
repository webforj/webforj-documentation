---
sidebar_position: 1
title: Route Types
_i18n_hash: ff067ccd8461640c772c1f8fa0dcc856
---
Les routes sont classées en deux types principaux, **View Routes** et **Layout Routes**. Le choix du type de route détermine comment les composants sont mappés aux URL et comment ils interagissent avec d'autres parties de votre application.

## View routes {#view-routes}

Les routes view se mappent directement à un segment d'URL et représentent des pages spécifiques de votre application. Ces routes sont reflétées dans l'URL du navigateur et sont généralement utilisées pour des vues ou des pages distinctes.

```java
@Route(value = "home")
public class HomeView extends Composite<Div> {
  public HomeView() {
    Div content = getBoundComponent();
    content.add(new H1("Page d'Accueil"));
  }
}
```

- **URL**: `/home`
- **Composant Rendu**: `HomeView`

Dans cet exemple, naviguer vers `/home` rend le composant `HomeView`.

## Layout routes {#layout-routes}

Les routes layout englobent des vues enfants sans contribuer à l'URL. Les mises en page fournissent des éléments d'interface utilisateur partagés tels que des en-têtes ou des barres latérales qui sont cohérents à travers plusieurs vues. Les routes enfants sont rendues dans la zone de contenu de la mise en page.

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

Par défaut, le type de route est détecté automatiquement, que la route soit une **view** ou une **layout** en fonction du nom de la classe :

- Les classes se terminant par `Layout` sont considérées comme des **layout routes**.
- Les classes se terminant par `View` sont considérées comme des **view routes**.

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
  public DashboardView() {
    Div content = getBoundComponent();
    content.add(new H1("Contenu du Dashboard"));
  }
}
```
