---
sidebar_position: 2
title: Foundational Architecture
_i18n_hash: 0506f859c3bd22ddca70550b6f3e368a
---
Le système de sécurité webforJ est construit sur une fondation d'interfaces principales qui travaillent ensemble pour fournir un contrôle d'accès au niveau des routes. Ces interfaces définissent les contrats pour le comportement de sécurité, permettant à différentes implémentations, qu'elles soient basées sur des sessions, sur des JSON Web Tokens (JWT), intégrées LDAP ou basées sur des bases de données, de se brancher sur le même framework sous-jacent.

Comprendre cette architecture vous aide à voir comment les annotations de sécurité comme `@RolesAllowed` et `@PermitAll` sont évaluées, comment l'interception de navigation fonctionne, et comment vous pouvez construire des implémentations de sécurité sur mesure pour vos besoins spécifiques.

## Les interfaces principales {#the-four-core-interfaces}

La fondation de la sécurité est construite sur des abstractions clés, chacune avec une responsabilité spécifique :

### `RouteSecurityManager` {#routesecuritymanager}

Le `RouteSecurityManager` est le coordinateur central du système de sécurité. Il gère les évaluateurs de sécurité, orchestre le processus d'évaluation et gère le refus d'accès en redirigeant les utilisateurs vers les pages appropriées.

**Responsabilités :**

- Enregistrer et gérer les évaluateurs de sécurité avec des priorités
- Coordonner le processus d'évaluation lorsque l'utilisateur navigue vers une route
- Gérer le refus d'accès en déclenchant des redirections vers les pages de connexion ou d'accès refusé
- Stocker et récupérer les emplacements de pré-authentification pour les redirections post-connexion

```java
public interface RouteSecurityManager {
  RouteAccessDecision evaluate(Class<?> routeClass, NavigationContext context);
  void onAccessDenied(RouteAccessDecision decision, NavigationContext context);
  RouteSecurityContext getSecurityContext();
  RouteSecurityConfiguration getConfiguration();
  void registerEvaluator(RouteSecurityEvaluator evaluator, int priority);
  Optional<Location> consumePreAuthenticationLocation();
}
```

Le manager ne prend pas de décisions de sécurité lui-même, il délègue aux évaluateurs et à la configuration. C'est le lien qui connecte tous les composants de sécurité.

### `RouteSecurityContext` {#routesecuritycontext}

Le `RouteSecurityContext` fournit un accès à l'état d'authentification de l'utilisateur actuel. Il répond à des questions telles que si l'utilisateur est authentifié, quel est son nom d'utilisateur et s'il a le rôle `ADMIN`.

**Responsabilités :**

- Déterminer si l'utilisateur actuel est authentifié
- Fournir le principal de l'utilisateur (typiquement son nom d'utilisateur ou objet utilisateur)
- Vérifier si l'utilisateur a des rôles ou autorités spécifiques
- Stocker et récupérer des attributs de sécurité personnalisés

```java
public interface RouteSecurityContext {
  boolean isAuthenticated();
  Optional<Object> getPrincipal();
  boolean hasRole(String role);
  boolean hasAuthority(String authority);
  Optional<Object> getAttribute(String name);
  void setAttribute(String name, Object value);
}
```

Les implémentations varient en fonction du système d'authentification, du stockage de session HTTP, des tokens JWT décodés à partir des en-têtes, des requêtes de base de données, des recherches LDAP ou d'autres mécanismes.

### `RouteSecurityConfiguration` {#routesecurityconfiguration}

La `RouteSecurityConfiguration` définit le comportement de sécurité et les emplacements de redirection. Elle indique au système de sécurité où envoyer les utilisateurs lorsque l'authentification est requise ou lorsque l'accès est refusé.

**Responsabilités :**

- Définir si la sécurité est activée
- Spécifier le comportement sécurisé par défaut
- Fournir l'emplacement de la page d'authentification (typiquement `/login`)
- Fournir l'emplacement de la page d'accès refusé

```java
public interface RouteSecurityConfiguration {
  default boolean isEnabled() { return true; }
  default boolean isSecureByDefault() { return true; }
  default Optional<Location> getAuthenticationLocation() {
    return Optional.of(new Location("/login"));
  }
  default Optional<Location> getDenyLocation() { /* ... */ }
}
```

Cette interface sépare la politique de sécurité de l'application de la sécurité. Vous pouvez changer les emplacements de redirection ou activer le comportement sécurisé par défaut sans modifier le manager ou les évaluateurs.

### `RouteSecurityEvaluator` {#routesecurityevaluator}

Le `RouteSecurityEvaluator` est l'endroit où les règles de sécurité réelles sont vérifiées. Chaque évaluateur examine une route et décide d'accorder l'accès, de refuser l'accès ou de déléguer la décision au prochain évaluateur de la chaîne.

**Responsabilités :**

- Déterminer si cet évaluateur gère la route donnée
- Évaluer les annotations de sécurité sur la classe de route
- Accorder l'accès, refuser l'accès ou déléguer au prochain évaluateur
- Participer au modèle de chaîne de responsabilité

```java
public interface RouteSecurityEvaluator {
  RouteAccessDecision evaluate(Class<?> routeClass,
                                NavigationContext context,
                                RouteSecurityContext securityContext,
                                SecurityEvaluatorChain chain);
  default boolean supports(Class<?> routeClass) { return true; }
}
```

Les évaluateurs intégrés gèrent les annotations standard comme `@RolesAllowed`, `@PermitAll`, `@DenyAll`, et `@AnonymousAccess`. Vous pouvez créer des évaluateurs personnalisés pour mettre en œuvre une logique de sécurité spécifique au domaine.

## Comment les interfaces travaillent ensemble {#how-the-interfaces-work-together}

Ces quatre interfaces collaborent lors de la navigation pour faire respecter les règles de sécurité :

```mermaid
flowchart TB
  User["User navigates to route"] --> Observer["RouteSecurityObserver<br/>(intercepts navigation)"]
  Observer --> Manager["RouteSecurityManager<br/>(orchestrates evaluation)"]

  Manager --> Config["RouteSecurityConfiguration<br/>(provides settings)"]
  Manager --> Context["RouteSecurityContext<br/>(provides user info)"]
  Manager --> Chain["Evaluator Chain<br/>(runs evaluators in priority order)"]

  Chain --> Decision{"Access Decision"}
  Decision -->|"Grant"| Render["Render component"]
  Decision -->|"Deny"| Redirect["RouteSecurityManager.onAccessDenied()<br/>Redirect to login or deny page"]
```

Lorsque l'utilisateur navigue, le `RouteSecurityObserver` intercepte la navigation et demande au `RouteSecurityManager` d'évaluer l'accès. Le manager consulte le `RouteSecurityConfiguration` pour obtenir les paramètres, obtient des informations sur l'utilisateur à partir du `RouteSecurityContext`, et exécute chaque `RouteSecurityEvaluator` dans l'ordre de priorité jusqu'à ce que l'un d'eux prenne une décision.

## Interfaces comme contrats {#the-interfaces-as-contracts}

Chaque interface définit un contrat, un ensemble de questions auxquelles le système de sécurité doit répondre. **Comment** vous répondez à ces questions est un choix d'implémentation :

**Contrat de `RouteSecurityContext` :**

- "L'utilisateur actuel est-il authentifié ?" (`isAuthenticated()`)
- "Qui est l'utilisateur ?" (`getPrincipal()`)
- "L'utilisateur a-t-il le rôle X ?" (`hasRole()`)

Vous décidez d'où provient cette information : sessions HTTP, tokens JWT décodés à partir des en-têtes, recherches dans la base de données, requêtes LDAP ou tout autre backend d'authentification.

**Contrat de `RouteSecurityConfiguration` :**

- "La sécurité est-elle activée ?" (`isEnabled()`)
- "Les routes doivent-elles être sécurisées par défaut ?" (`isSecureByDefault()`)
- "Où doivent aller les utilisateurs non authentifiés ?" (`getAuthenticationLocation()`)

Vous décidez comment obtenir ces valeurs : en les codant en dur, à partir de fichiers de configuration, de variables d'environnement, d'une base de données ou en les calculant dynamiquement.

**Contrat de `RouteSecurityManager` :**

- "Cet utilisateur doit-il accéder à cette route ?" (`evaluate()`)
- "Que se passe-t-il lorsque l'accès est refusé ?" (`onAccessDenied()`)
- "Quels évaluateurs doivent être exécutés ?" (`registerEvaluator()`)

Vous décidez du flux d'authentification, d'où stocker les emplacements de pré-authentification et comment gérer les scénarios de refus personnalisés.

L'architecture de fond définit ces contrats, mais l'implémentation est flexible. Différents systèmes peuvent implémenter ces interfaces de manière complètement différente en fonction des exigences spécifiques.

## La classe de base `AbstractRouteSecurityManager` {#the-abstractroutesecuritymanager-base-class}

La plupart des implémentations n'implémentent pas `RouteSecurityManager` directement. Au lieu de cela, elles étendent `AbstractRouteSecurityManager`, qui fournit :

- Enregistrement des évaluateurs et tri basé sur la priorité
- Logique d'exécution de la chaîne
- Gestion des refus d'accès avec des redirections automatiques
- Stockage des emplacements de pré-authentification dans la session HTTP
- Comportement par défaut sécurisé

La classe de base implémente l'interface `RouteSecurityManager` et fournit des implémentations concrètes pour la gestion des évaluateurs, l'évaluation des accès et la gestion des refus. Les sous-classes n'ont besoin de fournir que le contexte de sécurité et la configuration. La classe de base gère automatiquement la gestion des évaluateurs, l'exécution de la chaîne et la gestion des refus.
