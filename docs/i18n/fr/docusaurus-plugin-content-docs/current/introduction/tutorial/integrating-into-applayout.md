---
title: Integrating the AppLayout
sidebar_position: 7
draft: true
description: Step 6 - Use the AppLayout component.
_i18n_hash: 14b409262af6d7a8a25a67278f687250
---
Dans cette étape, vous allez intégrer les fonctionnalités mises en œuvre dans les étapes précédentes, telles que le routage et les vues, dans une mise en page d'application cohérente. Cette structure fournira un système de navigation unifié et des zones de contenu dynamiques.

## Exécution de l'application {#running-the-app}

Au fur et à mesure que vous développez votre application, vous pouvez utiliser [6-integrating-an-app-layout](https://github.com/webforj/webforj-tutorial/tree/main/6-integrating-an-app-layout) comme référence. Pour voir l'application en action :

1. Naviguez vers le répertoire de niveau supérieur contenant le fichier `pom.xml`, c'est `6-integrating-an-app-layout` si vous suivez la version sur GitHub.

2. Utilisez la commande Maven suivante pour exécuter l'application Spring Boot localement :
    ```bash
    mvn
    ```

L'exécution de l'application ouvre automatiquement un nouveau navigateur à `http://localhost:8080`.

## Objectif de la mise en page de l'application {#purpose-of-the-app-layout}

L'`AppLayout` sert de fondation pour gérer la structure et le flux globaux de votre application. Il fournit :
- **Navigation Globale** : Un moyen cohérent de passer entre les sections clés.
- **Rendu de Contenu Dynamique** : Une mise en page centralisée pour afficher les vues routées.

## Utilisation de `AppNav` {#using-appnav}

Le composant `AppNav` est utilisé pour créer un menu de navigation au sein de l'interface utilisateur de l'application. Ce menu fournit des liens vers différentes vues de votre application, comme la `DemoView` :

```java title="MainLayout.java"
private void setDrawer() {
  AppLayout layout = getBoundComponent();

  AppNav appNav = new AppNav();
  appNav.addItem(new AppNavItem("Dashboard", DemoView.class, FeatherIcon.MESSAGE_CIRCLE.create()));

  layout.addToDrawer(appNav);
}
```

Dans cet exemple :
- Le menu de navigation est ajouté au tiroir de l'application.
- Chaque élément du menu est un `AppNavItem` qui spécifie :
  - L'étiquette, par exemple "Dashboard."
  - La vue cible, par exemple `DemoView`.
  - Une icône optionnelle, par exemple une icône de colonnes.

## Routes et sorties de mise en page {#layout-routes-and-outlets}

La mise en page utilise des routes et des sorties pour afficher dynamiquement du contenu au sein d'une mise en page structurée. Dans webforJ :
- **Les Routes** définissent comment les vues mappent à des chemins spécifiques.
- **Les Sorties** agissent comme des espaces réservés dans les mises en page où les vues routées sont affichées.

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
@FrameTitle("Demo")
public class DemoView extends Composite<Div> {
  // Logique de DemoView
}
```

## Ajout de contenu dynamique avec `RouteOutlet` {#adding-dynamic-content-with-routeoutlet}

Un `RouteOutlet` affiche dynamiquement des vues en fonction de la route active. Dans la mise en page, des vues comme `DemoView` sont rendues via le `RouteOutlet`. Alors que le `RouteOutlet` est géré implicitement par la spécification de sortie dans les annotations de route.
