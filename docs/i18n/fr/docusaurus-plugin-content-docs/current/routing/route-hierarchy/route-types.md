---
sidebar_position: 1
title: Route Types
_i18n_hash: d297703f4a165ebcfbb540c3256f825e
---
Les itinéraires sont classés en deux types principaux, **Itinéraires de vue** et **Itinéraires de mise en page**. Le choix du type d'itinéraire détermine comment les composants sont mappés aux URL et comment ils interagissent avec d'autres parties de votre application.

## Itinéraires de vue {#view-routes}

Les itinéraires de vue sont directement mappés à un segment d'URL et représentent des pages spécifiques dans votre application. Ces itinéraires sont reflétés dans l'URL du navigateur et sont généralement utilisés pour des vues ou pages distinctes.

```java
@Route(value = "home")
public class HomeView extends Composite<Div> {
  public HomeView() {
    Div content = getBoundComponent();
    content.add(new H1("Page d'accueil"));
  }
}
```

- **URL**: `/home`
- **Composant rendu**: `HomeView`

Dans cet exemple, naviguer vers `/home` rend le composant `HomeView`.

## Itinéraires de mise en page {#layout-routes}

Les itinéraires de mise en page englobent des vues enfants sans contribuer à l'URL. Les mises en page fournissent des éléments d'interface utilisateur partagés tels que des en-têtes ou des barres latérales qui sont cohérents à travers plusieurs vues. Les itinéraires enfants sont rendus dans la zone de contenu de la mise en page.

```java
@Route(type = Route.Type.LAYOUT)
public class MainLayout extends Composite<AppLayout> {
  public MainLayout() {
    setHeader();
    setDrawer();
  }
}
```

Dans ce cas, `MainLayout` est un itinéraire de mise en page qui entoure les vues enfants. Il définit des éléments d'interface utilisateur communs comme un en-tête et un tiroir. Les itinéraires enfants associés à cette mise en page seront injectés dans la zone de contenu du composant `AppLayout`.

## Détection automatique des types d'itinéraires {#auto-detection-of-route-types}

Par défaut, le type d'itinéraire est détecté automatiquement que l'itinéraire soit une **vue** ou une **mise en page** en fonction du nom de la classe :

- Les classes se terminant par `Layout` sont traitées comme **itinéraires de mise en page**.
- Les classes se terminant par `View` sont traitées comme **itinéraires de vue**.

Alternativement, les développeurs peuvent spécifier manuellement le type d'itinéraire en définissant `Route.Type` dans l'annotation `@Route`.

```java
// Détecté automatiquement comme Mise en page
@Route
public class MainLayout extends Composite<AppLayout> {
  public MainLayout() {
    setHeader();
    setDrawer();
  }
}
```

```java
// Détecté automatiquement comme Vue
@Route(outlet = MainLayout.class)
public class DashboardView extends Composite<Div> {
  public DashboardView() {
    Div content = getBoundComponent();
    content.add(new H1("Contenu du tableau de bord"));
  }
}
```
