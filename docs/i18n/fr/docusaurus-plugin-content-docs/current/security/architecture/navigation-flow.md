---
sidebar_position: 3
title: Navigation Flow
_i18n_hash: f2083b0f83ed9e0098250dafdb37a753
---
L'application de la sécurité dans webforJ se fait automatiquement lors de la navigation. Lorsqu'un utilisateur clique sur un lien ou navigue vers une route, le système de sécurité intercepte la navigation, évalue les règles d'accès, et permet soit la poursuite de la navigation, soit redirige l'utilisateur vers une page appropriée. Cette interception est invisible pour les utilisateurs et ne nécessite aucun contrôle de sécurité manuel dans votre code de composant.

Comprendre comment fonctionne l'interception de la navigation vous aide à résoudre des problèmes de sécurité et à construire une logique de navigation personnalisée qui s'intègre au système de sécurité.

## Le `RouteSecurityObserver` {#the-routesecurityobserver}

Le `RouteSecurityObserver` est un observateur de navigation qui s'intègre dans le cycle de vie du routeur. Il écoute les événements de navigation et évalue les règles de sécurité avant que tout composant ne soit rendu.

L'observateur est rattaché au renderer du routeur lors du démarrage de l'application :

```java
// Créer l'observateur avec votre gestionnaire de sécurité
RouteSecurityObserver observer = new RouteSecurityObserver(securityManager);

// L'attacher au renderer du routeur
Router router = Router.getCurrent();
if (router != null) {
  router.getRenderer().addObserver(observer);
}
```

Une fois attaché, l'observateur intercepte chaque demande de navigation. L'observateur se trouve entre les demandes de navigation et le rendu des composants. Lorsque la navigation commence, il demande au gestionnaire de sécurité d'évaluer l'accès. Ce n'est que si l'accès est accordé que le composant est rendu.

## Flux d'interception de la navigation {#navigation-interception-flow}

Lorsqu'un utilisateur navigue vers une route, la séquence suivante se produit :

```mermaid
sequenceDiagram
  participant User
  participant Router
  participant Observer as RouteSecurityObserver
  participant Manager as RouteSecurityManager
  participant Component as Route Component

  User->>Router: Naviguer vers /admin
  Router->>Observer: onRouteRendererLifecycleEvent(BEFORE_CREATE)
  Observer->>Manager: évaluer(routeClass, context)
  Manager->>Manager: Exécuter les évaluateurs par priorité
  Manager-->>Observer: RouteAccessDecision

  alt Accès accordé
    Observer-->>Router: Autoriser le rendu
    Router->>Component: Créer le composant
    Component-->>User: Afficher la vue
  else Accès refusé
    Observer->>Manager: onAccessDenied(decision, context)
    Manager->>Router: Rediriger vers la page de connexion/de refus
    Router-->>User: Afficher la page de connexion
  end
```

Ce flux montre que l'évaluation de la sécurité se produit avant l'exécution de tout code sensible de la route. Si l'accès est refusé, le composant n'est jamais instancié, empêchant les utilisateurs non autorisés de déclencher une logique métier ou d'accéder à des données protégées.

## Points d'interception {#interception-points}

L'observateur intercepte la navigation à un moment spécifique dans le cycle de vie du routage :

**Avant le rendu** La méthode `onRouteRendererLifecycleEvent()` de l'observateur est appelée avec l'événement `LifecycleEvent.BEFORE_CREATE` après que la route a été résolue mais avant que le composant soit créé. C'est le point de contrôle de sécurité critique.

À ce moment, le routeur sait quelle classe de route sera rendue, mais la route n'a pas encore été instanciée. L'observateur peut évaluer les annotations de sécurité sur la classe sans exécuter la logique de la route.

Si l'accès est refusé, l'observateur empêche le rendu et déclenche une redirection. La route d'origine n'est jamais instanciée.

## Le processus d'évaluation {#the-evaluation-process}

Lorsque l'observateur intercepte une navigation, il délègue l'évaluation au gestionnaire de sécurité. L'observateur récupère la classe de route du contexte de navigation et demande au gestionnaire d'évaluer l'accès. Si la décision accorde l'accès, la navigation se poursuit normalement. Si la décision refuse l'accès, l'observateur stoppe la propagation pour empêcher le rendu et laisse le gestionnaire gérer le refus.

Le gestionnaire coordonne l'évaluation en :

1. Vérifiant si la sécurité est activée dans la configuration
2. Obtenant le contexte de sécurité actuel (informations sur l'utilisateur)
3. Exécutant la chaîne d'évaluateurs dans l'ordre de priorité
4. Renvoyant la décision finale d'accès

L'observateur agit sur la décision : si accordé, la navigation se poursuit ; si refusé, l'observateur arrête la propagation et laisse le gestionnaire gérer le refus.

## Comment les décisions d'accès sont prises {#how-access-decisions-are-made}

Le gestionnaire de sécurité crée une chaîne d'évaluateurs et exécute chaque évaluateur dans l'ordre de priorité. Les évaluateurs peuvent prendre trois types de décisions :

- **Accès accordé :** L'évaluateur approuve la navigation, et la route se rend. Aucun évaluateur supplémentaire n'est consulté. L'évaluateur renvoie une décision indiquant que l'accès est accordé.

- **Accès refusé :** L'évaluateur bloque la navigation. L'observateur arrête le rendu et déclenche une redirection. L'évaluateur renvoie une décision de refus, éventuellement avec un message de raison. Le refus peut être dû à un manque d'authentification (requérant une connexion) ou à un manque d'autorisation (permissions insuffisantes).

- **Déléguer au prochain évaluateur :** L'évaluateur ne prend pas de décision et passe le contrôle au prochain évaluateur de la chaîne. L'évaluateur appelle la méthode d'évaluation de la chaîne, qui avance au prochain évaluateur dans l'ordre de priorité.

La plupart des évaluateurs ne gèrent que les routes avec des annotations spécifiques. Par exemple, `RolesAllowedEvaluator` évalue uniquement les routes annotées avec `@RolesAllowed`. Si l'annotation n'est pas présente, il délègue au prochain évaluateur.

## Gestion du refus d'accès {#handling-access-denial}

Lorsque l'accès est refusé, la méthode `onAccessDenied()` du gestionnaire gère le refus en fonction du type de refus :

- **Authentification requise :** L'utilisateur n'est pas connecté. Rediriger vers la page de connexion configurée dans `RouteSecurityConfiguration.getAuthenticationLocation()`.

- **Accès refusé :** L'utilisateur est connecté mais manque de permissions. Rediriger vers la page d'accès refusé configurée dans `RouteSecurityConfiguration.getDenyLocation()`.

Avant de rediriger, le gestionnaire stocke l'emplacement demandé à l'origine dans la session HTTP. Après une connexion réussie, cet emplacement peut être récupéré à l'aide de la méthode `consumePreAuthenticationLocation()` du gestionnaire, qui renvoie l'emplacement stocké et le supprime de la session. Si un emplacement a été stocké, l'application peut y naviguer ; sinon, elle navigue vers une page par défaut.

## Lorsque la sécurité est désactivée {#when-security-is-disabled}

Si `RouteSecurityConfiguration.isEnabled()` renvoie `false`, le gestionnaire contourne toute évaluation et accorde immédiatement l'accès à chaque route. La chaîne d'évaluateurs ne s'exécute jamais, et aucune vérification de sécurité n'a lieu.

C'est utile pendant le développement ou pour les applications qui ne nécessitent pas de sécurité. Vous pouvez activer ou désactiver la sécurité sans retirer les annotations ou désinscrire l'observateur.

## Intégration avec le cycle de vie de la navigation {#integration-with-navigation-lifecycle}

L'observateur de sécurité s'intègre avec le [cycle de vie de la navigation](/docs/routing/navigation-lifecycle/overview) plus large, où plusieurs observateurs peuvent s'accrocher aux événements de navigation. L'évaluation de sécurité se produit tôt dans ce cycle de vie, avant le blocage de la navigation ou les événements de cycle de vie des composants.

Si vous implémentez des observateurs de navigation personnalisés, soyez conscient que l'évaluation de sécurité se produit en premier. Si l'accès est refusé, l'événement `onRouteRendererLifecycleEvent()` de votre observateur ne sera pas appelé avec `BEFORE_CREATE` car la navigation est arrêtée.
