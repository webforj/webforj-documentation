---
sidebar_position: 3
title: Defining Routes
_i18n_hash: 6d7133c5636f63b82b13dd0a07a97620
---
Définir des routes est essentiel pour mapper les URLs à des composants spécifiques. Cela vous permet de contrôler comment différentes parties de votre interface utilisateur sont rendues en fonction de la structure de l'URL. Le framework utilise l'annotation `@Route` pour rendre ce processus déclaratif et simple, réduisant ainsi le besoin de configuration manuelle.

:::info Inscription des Routes
Les routes peuvent être enregistrées de manière statique en utilisant l'annotation `@Route` ou dynamiquement via l'API `RouteRegistry`. Pour plus d'informations, consultez la [documentation sur l'inscription des routes](./routes-registration).
:::

## Définir une route avec `@Route` {#defining-a-route-with-route}

L'annotation `@Route` est utilisée pour lier un chemin URL à un composant spécifique. Cela permet au composant d'être rendu chaque fois que l'application navigue vers cette URL. Voici un exemple simple :

```java
@Route(value = "dashboard")
public class DashboardView extends Composite<Div> {
  // Logique du composant ici
}
```

Dans cet exemple :
- Le composant `DashboardView` est lié à l'URL `/dashboard`.
- Lorsqu'un utilisateur navigue vers `/dashboard`, le `DashboardView` sera rendu dynamiquement par le framework.

### Le paramètre `value` {#the-value-parameter}

Le paramètre `value` dans l'annotation `@Route` définit le chemin de l'URL. Cela peut être un chemin statique comme `"dashboard"` ou plus dynamique, permettant un routage flexible.

```java
@Route(value = "user/:id")
public class UserView extends Composite<Div> {
  // Logique du composant ici
}
```

Dans ce cas, naviguer vers `/user/123` affichera le `UserView`.

:::tip Modèles de Route
Le `user/:id` est connu sous le nom de modèle de route. Le routeur peut gérer des modèles simples, qui correspondent à un segment statique unique, et des modèles complexes, qui peuvent correspondre à plusieurs segments statiques, requis et optionnels. Pour plus d'informations sur la configuration des modèles, consultez le [plongée approfondie dans les modèles de route](./route-patterns).
:::

## Définir des alias de route {#defining-route-aliases}

Dans certains cas, vous pourriez vouloir permettre à plusieurs URLs de pointer vers le même composant. Par exemple, vous pourriez vouloir que les utilisateurs puissent accéder à leur profil via `/profile` ou `/user/me`. webforJ permet cela grâce à l'annotation **`@RouteAlias`**, vous permettant de définir plusieurs alias pour une seule route.

Voici un exemple dans lequel le composant est accessible à la fois via `/profile` et `/user/me` :

```java
@Route(value = "profile")
@RouteAlias("user/me")
public class UserProfileView extends Composite<Div> {
  // Logique du composant ici
}
```

Définir des alias de route augmente la flexibilité de votre conception de navigation, permettant aux utilisateurs d'accéder au même contenu à travers différentes URLs.
