---
sidebar_position: 11
title: Routes Registration
_i18n_hash: 8f6b7b85dd246adc8d98c8a5bf994a39
---
En plus de [l'enregistrement des routes à l'aide des annotations `@Route`](./defining-routes), il est possible de gérer dynamiquement l'enregistrement, la mise à jour ou la suppression des routes à l'exécution en fonction de la logique de l'application, des rôles des utilisateurs ou d'autres conditions. Cette flexibilité vous permet de gérer la navigation de manière plus dynamique, plutôt que de définir statiquement les routes au moment de la compilation.


## Enregistrement des routes de manière dynamique {#registering-routes-dynamically}

Vous pouvez enregistrer une route de manière dynamique en utilisant la classe `RouteRegistry`, accessible via le `Router`. Cela vous permet d'ajouter de nouvelles routes pendant l'exécution, offrant ainsi une navigation souple.

### Exemple : Enregistrement d'une route dynamique {#example-registering-a-dynamic-route}

```java
Router router = Router.getCurrent();
RouteRegistry registry = router.getRegistry();

// Enregistrer la route des paramètres de manière dynamique
registry.register("/settings", SettingsView.class);

// Naviguer vers la vue des paramètres
router.navigate(SettingsView.class);
```

Dans cet exemple, la route `/settings` est enregistrée de manière dynamique, et l'application navigue vers la vue nouvellement enregistrée.

## Enregistrement de routes conditionnel {#conditional-route-registration}

Souvent, les routes doivent être ajoutées ou supprimées en fonction de conditions spécifiques telles que les rôles des utilisateurs ou l'état de l'application. Avec le routage dynamique, vous pouvez enregistrer ou désenregistrer des routes de manière conditionnelle à l'exécution.

### Exemple : enregistrement conditionnel basé sur le rôle de l'utilisateur {#example-conditional-registration-based-on-user-role}

```java
Router router = Router.getCurrent();
RouteRegistry registry = router.getRegistry();

// Vérifier le rôle de l'utilisateur et enregistrer les routes appropriées
if (user.hasRole("editor")) {
    registry.register("/editor/dashboard", EditorDashboardView.class);
} else if (user.hasRole("viewer")) {
    registry.register("/viewer/dashboard", ViewerDashboardView.class);
}

// Naviguer vers le tableau de bord approprié
if (user.hasRole("editor")) {
    router.navigate(EditorDashboardView.class);
} else if (user.hasRole("viewer")) {
    router.navigate(ViewerDashboardView.class);
}
```

Dans cet exemple :
- La route `/editor/dashboard` ou `/viewer/dashboard` est enregistrée de manière dynamique en fonction du rôle de l'utilisateur.
- L'application navigue vers le tableau de bord approprié en fonction des droits d'accès de l'utilisateur.

## Suppression de routes {#removing-routes}

Tout comme les routes peuvent être ajoutées dynamiquement, elles peuvent également être supprimées à l'exécution lorsqu'elles ne sont plus nécessaires, ou lorsque le contexte de l'application change.

### Exemple : Suppression d'une route enregistrée {#example-removing-a-registered-route}

```java
Router router = Router.getCurrent();
RouteRegistry registry = router.getRegistry();

// Supprimer la route pour la vue des paramètres
registry.unregister("/settings");

// Optionnellement, supprimer par classe de composant
registry.unregister(SettingsView.class);
```

Dans cet exemple, la route `/settings` est supprimée dynamiquement lorsqu'elle n'est plus nécessaire.

## Enregistrement des routes au démarrage de l'application {#registering-routes-at-app-startup}

Vous pouvez enregistrer des routes dynamiques lors de l'initialisation de l'application, permettant à certaines vues d'être disponibles en fonction de l'environnement ou de la configuration au démarrage.

### Exemple : Enregistrement de routes lors du démarrage de l'application {#example-registering-routes-during-app-startup}

```java
@Routify
public class Application extends App {

  @Override
  protected void onWillRun() {
    // Enregistrer une vue de débogage uniquement en mode développement
    if (Environment.getCurrent().isDebug()) {
      Router router = Router.getCurrent();
      RouteRegistry registry = router.getRegistry();

      registry.register("/debug", DebugView.class);
    }
  }
}
```

Dans cet exemple :
- Une `DebugView` est enregistrée dynamiquement lors du démarrage de l'application, mais uniquement si l'application est en mode développement.

## Enregistrement de composants annotés `@Route` de manière dynamique {#registering-route-annotated-components-dynamically}

En plus de définir manuellement des routes, il est possible d'enregistrer dynamiquement des composants déjà annotés avec `@Route`. Cela est utile lorsque vous souhaitez tirer parti des classes pré-annotées mais les enregistrer dynamiquement en fonction de la logique de l'application.

### Exemple : Enregistrement d'un composant annoté `@Route` {#example-registering-an-route-annotated-component}

```java
@Route("profile")
public class ProfileView extends Composite<Div> {
    // Logique de la vue de profil
}

Router router = Router.getCurrent();
RouteRegistry registry = router.getRegistry();

// Enregistrer dynamiquement le ProfileView avec son annotation @Route
registry.register(ProfileView.class);

// Naviguer vers le ProfileView
router.navigate(ProfileView.class);
```

Dans cet exemple :
- La classe `ProfileView` est annotée avec `@Route("profile")`.
- La route est enregistrée dynamiquement à l'exécution en utilisant `registry.register(ProfileView.class)`.

## Enregistrement des routes à partir d'un package entier {#registering-routes-from-an-entire-package}

Si votre application contient un grand nombre de routes organisées au sein d'un package, vous pouvez enregistrer tous les composants annotés `@Route` à partir du package de manière dynamique.

### Exemple : Enregistrement de toutes les routes d'un package {#example-registering-all-routes-from-a-package}

```java
Router router = Router.getCurrent();
RouteRegistry registry = router.getRegistry();

// Enregistrer toutes les routes dans le package "com.myapp.admin"
RouteRegistry.ofPackage(new String[] { "com.myapp.admin" }, registry);
```

Dans cet exemple :
- La méthode `ofPackage` parcourt le package `com.myapp.admin` et enregistre toutes les classes annotées avec `@Route`.
- Cela est particulièrement utile pour les grandes applications avec de nombreuses routes organisées par packages.

## Récupération des routes enregistrées {#retrieving-registered-routes}

Pour récupérer une liste de toutes les routes enregistrées dynamiquement, utilisez la classe `RouteRegistry`. Cela est utile lorsque vous devez gérer ou afficher programmatique les routes disponibles.

### Exemple : Récupération et affichage de toutes les routes enregistrées {#example-retrieving-and-displaying-all-registered-routes}

```java
Router router = Router.getCurrent();
RouteRegistry registry = router.getRegistry();

List<RouteEntry> routes = registry.getAvailableRouteEntries();
routes.forEach(route -> console().log("Path: " + route.getPath()));
```

Dans cet exemple, l'application récupère toutes les routes actuellement enregistrées et imprime leurs chemins.

## Gestion des alias de route de manière dynamique {#managing-route-aliases-dynamically}

webforJ vous permet d'enregistrer plusieurs alias pour une seule vue. Cela signifie que les utilisateurs peuvent accéder à la même vue en utilisant différents chemins d'URL.

### Exemple : Enregistrement d'alias de route de manière dynamique {#example-registering-route-aliases-dynamically}

```java
Router router = Router.getCurrent();
RouteRegistry registry = router.getRegistry();

// Enregistrer une route principale
registry.register("/contact", ContactView.class);

// Enregistrer des alias pour la vue de contact
registry.register("/support", ContactView.class);
registry.register("/help", ContactView.class);
```

Dans cet exemple, le `ContactView` est accessible via trois chemins différents : `/contact`, `/support` et `/help`.
