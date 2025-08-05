---
title: Integrating the AppLayout
sidebar_position: 6
draft: true
_i18n_hash: fd2f3844bbcb102adf05ae01b07ff8d8
---
Dans cette étape, vous intégrerez les fonctionnalités mises en œuvre dans les étapes précédentes, telles que le routage et les vues, dans une mise en page d'application cohérente. Cette structure fournira un système de navigation unifié et des zones de contenu dynamiques.

## Objectif de la mise en page de l'application {#purpose-of-the-app-layout}

La `AppLayout` sert de fondation pour gérer la structure générale et le flux de votre application. Elle fournit :
- **Navigation Globale** : Un moyen cohérent de passer entre les sections clés.
- **Rendu de Contenu Dynamique** : Une mise en page centralisée pour afficher les vues routées.

## Utilisation de `AppNav` {#using-appnav}

Le composant `AppNav` est utilisé pour créer un menu de navigation dans l'interface utilisateur de l'application. Ce menu fournit des liens vers différentes vues de votre application, comme la `DemoView` :

```java title="MainLayout.java"
private void setDrawer() {
  AppLayout layout = getBoundComponent();

  AppNav appNav = new AppNav();
  appNav.addItem(new AppNavItem("Tableau de bord", DemoView.class, FeatherIcon.MESSAGE_CIRCLE.create()));

  layout.addToDrawer(appNav);
}
```

Dans cet exemple :
- Le menu de navigation est ajouté au tiroir de l'application.
- Chaque élément du menu est un `AppNavItem` qui spécifie :
  - L'étiquette, par exemple "Tableau de bord."
  - La vue cible par exemple `DemoView`.
  - Une icône optionnelle par exemple une icône de colonnes.

## Routes et sorties de mise en page {#layout-routes-and-outlets}

La mise en page utilise des routes et des sorties pour rendre dynamiquement du contenu dans une mise en page structurée. Dans webforJ :
- **Routes** définissent comment les vues se mappent à des chemins spécifiques.
- **Sorties** agissent comme des espaces réservés dans les mises en page où les vues routées sont affichées.

### Exemple : Configuration d'une route de mise en page {#example-setting-up-a-layout-route}

Dans la classe `MainLayout`, l'annotation `@Route` la définit comme la mise en page de base, et la `DemoView` est rendue via une sortie dans cette mise en page :

```java title="MainLayout.java"
@Route("/")
public class MainLayout extends Composite<AppLayout> {
    public MainLayout() {
        setHeader();
        setDrawer();
    }
}
```

L'annotation `@Route` pour `DemoView` spécifie qu'elle utilise `MainLayout` comme sa sortie :

```java title="DemoView.java"
@Route(value = "/demo", outlet = MainLayout.class)
@FrameTitle("Démonstration")
public class DemoView extends Composite<Div> {
    // Logique de DemoView
}
```

## Ajout de contenu dynamique avec `RouteOutlet` {#adding-dynamic-content-with-routeoutlet}

Un `RouteOutlet` affiche dynamiquement des vues en fonction de la route active. Dans la mise en page, des vues comme `DemoView` sont rendues via le `RouteOutlet`. Le `RouteOutlet` est implicitement géré par la spécification de sortie dans les annotations de route.
