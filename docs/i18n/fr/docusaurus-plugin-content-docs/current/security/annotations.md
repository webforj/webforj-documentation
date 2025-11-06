---
sidebar_position: 3
title: Security Annotations
_i18n_hash: af9997b8bee96bfa4005a65998fddaf5
---
Les annotations de sécurité fournissent un moyen déclaratif de contrôler l'accès aux routes dans votre application webforJ. En ajoutant des annotations à vos composants de route, vous définissez qui peut accéder à chaque vue sans écrire de vérifications de permissions manuelles. Le système de sécurité applique automatiquement ces règles avant que tout composant ne soit rendu.

:::info Note de mise en œuvre
Ce guide fonctionne avec n'importe quelle implémentation de sécurité. Les exemples présentés fonctionnent avec Spring Security et des implémentations personnalisées. Si vous n'utilisez pas Spring Boot, consultez le [guide d'architecture de sécurité](/docs/security/architecture/overview) pour comprendre les bases et mettre en œuvre une sécurité personnalisée.
:::

## `@AnonymousAccess` - routes publiques {#anonymousaccess-public-routes}

L'annotation `@AnonymousAccess` marque une route comme accessible publiquement. Les utilisateurs n'ont pas besoin d'être authentifiés pour accéder à ces routes. Cette annotation est généralement utilisée pour les pages de connexion, les pages d'accueil publiques ou d'autres contenus qui doivent être disponibles pour tout le monde.

```java title="LoginView.java"
@Route("/login")
@AnonymousAccess
public class LoginView extends Composite<Login> {

  public LoginView() {
    // vue de connexion
  }
}
```

Dans cet exemple :
- Tout utilisateur, authentifié ou non, peut accéder à la route `/login`.
- Lorsqu'`@AnonymousAccess` est présent, les utilisateurs non authentifiés sont autorisés à accéder à cette page sans être redirigés.

:::tip Cas d'utilisation courants
Utilisez `@AnonymousAccess` pour les pages de connexion, les pages d'inscription, les pages d'accueil publiques, les conditions de service, les politiques de confidentialité et tout autre contenu qui doit être accessible sans authentification.
:::

## `@PermitAll` - routes authentifiées {#permitall-authenticated-routes}

L'annotation `@PermitAll` nécessite que les utilisateurs soient authentifiés mais n'impose pas d'exigences de rôle spécifiques. Tout utilisateur connecté peut accéder à ces routes, quel que soit son rôle ou ses permissions.

```java title="InboxView.java"
@Route(value = "/", outlet = MainLayout.class)
@PermitAll
public class InboxView extends Composite<FlexLayout> {

  public InboxView() {
    FlexLayout layout = getBoundComponent();
    layout.setHeight("100%");
    layout.add(new Explore("Boîte de Réception"));
  }
}
```

Dans cet exemple :
- Les utilisateurs doivent être authentifiés pour accéder à la boîte de réception.
- Tout utilisateur authentifié peut voir cette page, quel que soit son rôle.
- Les utilisateurs non authentifiés sont redirigés vers la page de connexion.

:::info Mode sécurisé par défaut
Lorsque le mode sécurisé par défaut est activé, les routes sans aucune annotation de sécurité se comportent de la même manière que `@PermitAll`— elles nécessitent une authentification. Consultez la [section sur le mode sécurisé par défaut](#secure-by-default) pour plus de détails.
:::

## `@RolesAllowed` - routes basées sur les rôles {#rolesallowed-role-based-routes}

L'annotation `@RolesAllowed` restreint l'accès aux utilisateurs avec des rôles spécifiques. Vous pouvez spécifier un ou plusieurs rôles, et les utilisateurs doivent avoir au moins un des rôles listés pour accéder à la route.

### Exigence d'un rôle unique {#single-role-requirement}

```java title="TrashView.java"
@Route(value = "/trash", outlet = MainLayout.class)
@RolesAllowed("ADMIN")
public class TrashView extends Composite<FlexLayout> {

  public TrashView() {
    FlexLayout layout = getBoundComponent();
    layout.setHeight("100%");
    layout.add(new Explore("Corbeille"));
  }
}
```

Dans cet exemple :
- Seuls les utilisateurs avec le rôle `ADMIN` peuvent accéder à la vue de la corbeille.
- Les utilisateurs sans le rôle `ADMIN` sont redirigés vers la page d'accès refusé.

### Exigences de plusieurs rôles {#multiple-role-requirements}

```java title="SettingsView.java"
@Route(value = "/settings", outlet = MainLayout.class)
@RolesAllowed({"ADMIN", "MANAGER"})
public class SettingsView extends Composite<FlexLayout> {

  public SettingsView() {
    FlexLayout layout = getBoundComponent();
    layout.add(new Explore("Paramètres"));
  }
}
```

Dans cet exemple :
- Les utilisateurs ayant soit le rôle `ADMIN` soit le rôle `MANAGER` peuvent accéder aux paramètres.
- L'utilisateur n'a besoin que d'un des rôles listés, pas de tous.

:::tip Conventions de nommage des rôles
Utilisez des noms de rôles en majuscules (comme `ADMIN`, `USER`, `MANAGER`) pour la cohérence. Cela correspond aux conventions courantes des frameworks de sécurité et rend votre code plus lisible.
:::

## `@DenyAll` - routes bloquées {#denyall-blocked-routes}

L'annotation `@DenyAll` bloque l'accès à une route pour tous les utilisateurs, quel que soit leur statut d'authentification ou leurs rôles. Cela est utile pour désactiver temporairement des routes pendant la maintenance ou pour des routes en cours de développement.

```java title="MaintenanceView.java"
@Route(value = "/maintenance", outlet = MainLayout.class)
@DenyAll
public class MaintenanceView extends Composite<FlexLayout> {

  public MaintenanceView() {
    FlexLayout layout = getBoundComponent();
    layout.add(new Paragraph("Cette page est en maintenance."));
  }
}
```

Dans cet exemple :
- Aucun utilisateur ne peut accéder à cette route, même les administrateurs.
- Toutes les tentatives d'accès aboutissent à une redirection vers la page d'accès refusé.

:::warning Utilisation temporaire
`@DenyAll` est généralement utilisé temporairement pendant le développement ou la maintenance. Pour les applications en production, envisagez de retirer complètement la route ou d'utiliser des restrictions de rôle appropriées à la place.
:::

## Que se passe-t-il lorsqu'un accès est refusé {#what-happens-when-access-is-denied}

Lorsqu'un utilisateur tente d'accéder à une route qu'il n'est pas autorisé à voir, le système de sécurité gère le refus automatiquement :

1. **Utilisateurs non authentifiés** : redirigés vers la page de connexion configurée dans votre configuration de sécurité.
2. **Utilisateurs authentifiés sans rôles requis** : redirigés vers la page d'accès refusé.
3. **Tous les utilisateurs sur les routes `@DenyAll`** : redirigés vers la page d'accès refusé.

Vous pouvez personnaliser ces emplacements de redirection pour correspondre à la structure de navigation de votre application afin que les refus d'accès et les demandes d'authentification mènent aux pages prévues. Consultez [Configurer Spring Security](/docs/security/getting-started#configure-spring-security) pour les détails de configuration.

## Sécurisé par défaut {#secure-by-default}

Sécurisé par défaut est une option de configuration qui détermine comment les routes sans annotation de sécurité sont traitées. Lorsqu'il est activé, toutes les routes nécessitent une authentification par défaut, sauf si elles sont explicitement marquées avec `@AnonymousAccess`.

### Activé (recommandé pour la production) {#enabled-recommended-for-production}

Ajoutez ceci à votre `application.properties` :

```properties title="application.properties"
webforj.security.secure-by-default=true
```

Avec le mode sécurisé par défaut activé :
- Les routes sans annotations nécessitent une authentification (la même chose que `@PermitAll`).
- Seules les routes `@AnonymousAccess` sont accessibles publiquement.
- Vous devez explicitement marquer les routes publiques, réduisant le risque d'exposer accidentellement du contenu protégé.

```java
// Nécessite une authentification (pas d'annotation)
@Route("/dashboard")
public class DashboardView extends Composite<Div> { }

// Accès public (marqué explicitement)
@Route("/about")
@AnonymousAccess
public class AboutView extends Composite<Div> { }
```

### Désactivé (autoriser par défaut) {#disabled-allow-by-default}

```properties title="application.properties"
webforj.security.secure-by-default=false
```

Avec le mode sécurisé par défaut désactivé :
- Les routes sans annotations sont accessibles publiquement.
- Vous devez explicitement ajouter `@PermitAll` ou `@RolesAllowed` pour protéger les routes.
- Plus facile pour le développement, mais plus risqué pour la production.

```java
// Accès public (pas d'annotation)
@Route("/about")
public class AboutView extends Composite<Div> { }

// Nécessite une authentification (marqué explicitement)
@Route("/dashboard")
@PermitAll
public class DashboardView extends Composite<Div> { }
```

:::tip Meilleure pratique
Activez `secure-by-default` pour les applications de production. Avec ce paramètre, les routes protégées ne sont pas exposées à moins d'être explicitement marquées comme publiques, réduisant le risque d'exposition accidentelle due à des annotations manquantes. Ne désactivez cette option que pendant le développement initial si vous trouvez que les annotations supplémentaires sont encombrantes.
:::
