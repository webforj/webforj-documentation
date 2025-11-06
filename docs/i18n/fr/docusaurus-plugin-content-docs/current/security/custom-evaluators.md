---
sidebar_position: 6
title: Custom Evaluators
_i18n_hash: 9b448cdd74811b257b78cc6c9f04e7c2
---
Les évaluateurs personnalisés étendent le système de sécurité de webforJ avec une logique de contrôle d'accès spécialisée au-delà de l'authentification de base et des vérifications de rôle. Utilisez-les lorsque vous devez vérifier des conditions dynamiques qui dépendent du contexte de la requête, et pas seulement des autorisations d'utilisateur.

:::info Axé sur Spring Security
Ce guide couvre les évaluateurs personnalisés pour Spring Security. Si vous n'utilisez pas Spring Boot, consultez le [guide de la chaîne d'évaluateurs](/docs/security/architecture/evaluator-chain) pour comprendre comment fonctionnent les évaluateurs et [Implémentation complète](/docs/security/architecture/custom-implementation) pour un exemple fonctionnel.
:::

## Qu'est-ce que des évaluateurs personnalisés {#what-are-custom-evaluators}

Un évaluateur détermine si un utilisateur peut accéder à une route spécifique en fonction de la logique personnalisée. Les évaluateurs sont vérifiés lors de la navigation avant que tout composant soit rendu, vous permettant d'intercepter et de contrôler l'accès de manière dynamique.

webforJ inclut des évaluateurs intégrés pour les annotations Jakarta standard :

- `AnonymousAccessEvaluator` - Gère `@AnonymousAccess`
- `PermitAllEvaluator` - Gère `@PermitAll`
- `RolesAllowedEvaluator` - Gère `@RolesAllowed`
- `DenyAllEvaluator` - Gère `@DenyAll`

Les évaluateurs personnalisés suivent le même modèle, vous permettant de créer vos propres annotations et logique de contrôle d'accès.

:::tip[En savoir plus sur les annotations intégrées]
Pour des détails sur `@AnonymousAccess`, `@PermitAll`, `@RolesAllowed` et `@DenyAll`, voir le [guide des annotations de sécurité](/docs/security/annotations).
:::

## Cas d'utilisation : Vérification de propriété {#use-case-ownership-verification}

Une exigence courante est de permettre aux utilisateurs d'accéder uniquement à leurs propres ressources. Par exemple, les utilisateurs ne devraient pouvoir modifier que leur propre profil, et non celui de quelqu'un d'autre.

**Le problème** : `@RolesAllowed("USER")` accorde l'accès à tous les utilisateurs authentifiés, mais ne vérifie pas si l'utilisateur accède à sa propre ressource. Vous devez comparer l'ID de l'utilisateur connecté avec l'ID de la ressource dans l'URL.

**Scénario d'exemple :**
- L'utilisateur ID `123` est connecté
- Il navigue vers `/users/456/edit`
- Peut-il accéder à cette page ? **NON** - il ne peut modifier que `/users/123/edit`

Vous ne pouvez pas résoudre cela avec des rôles car cela dépend du paramètre de route `:userId`, qui change pour chaque requête.

### Création d'une annotation personnalisée {#creating-a-custom-annotation}

Définissez une annotation pour marquer les routes nécessitant une vérification de propriété :

```java title="RequireOwnership.java"
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequireOwnership {
  /**
   * Le nom du paramètre de la route qui contient l'ID de l'utilisateur.
   */
  String value() default "userId";
}
```

Utilisez-la sur les routes qui nécessitent des vérifications de propriété :

```java title="EditProfileView.java"
@Route(value = "/users/:userId/edit", outlet = MainLayout.class)
@RequireOwnership("userId")
public class EditProfileView extends Composite<Div> {
  private final Div self = getBoundComponent();

  public EditProfileView() {
    self.setText("Page de modification du profil");
  }
}
```

### Implémentation de l'évaluateur {#implementing-the-evaluator}

Créez un évaluateur géré par Spring qui compare l'ID de l'utilisateur connecté avec le paramètre de route :

```java title="OwnershipEvaluator.java"
@RegisteredEvaluator(priority = 10)
public class OwnershipEvaluator implements RouteSecurityEvaluator {

  @Override
  public boolean supports(Class<?> routeClass) {
    return routeClass.isAnnotationPresent(RequireOwnership.class);
  }

  @Override
  public RouteAccessDecision evaluate(Class<?> routeClass, NavigationContext context,
      RouteSecurityContext securityContext, SecurityEvaluatorChain chain) {

    // D'abord vérifier l'authentification
    if (!securityContext.isAuthenticated()) {
      return RouteAccessDecision.denyAuthentication();
    }

    // Obtenir l'annotation
    RequireOwnership annotation = routeClass.getAnnotation(RequireOwnership.class);
    String paramName = annotation.value();

    // Obtenir l'ID de l'utilisateur connecté à partir du contexte de sécurité
    String currentUserId = securityContext.getPrincipal()
        .filter(p -> p instanceof UserDetails)
        .map(p -> ((UserDetails) p).getUsername())
        .orElse(null);

    // Obtenir :userId à partir des paramètres de la route
    String requestedUserId = context.getRouteParameters()
        .get(paramName)
        .orElse(null);

    // Vérifier s'ils correspondent
    if (currentUserId != null && currentUserId.equals(requestedUserId)) {
      // Propriété vérifiée - continuer la chaîne pour permettre à d'autres évaluateurs
      return chain.evaluate(routeClass, context, securityContext);
    }

    return RouteAccessDecision.deny("Vous ne pouvez accéder qu'à vos propres ressources");
  }
}
```

Spring découvre automatiquement et enregistre les évaluateurs annotés avec `@RegisteredEvaluator`.

### Comment cela fonctionne {#how-it-works}

L'implémentation de l'évaluateur a deux méthodes clés :

#### `supports(Class<?> routeClass)` {#supports-method}

- Renvoie `true` si cet évaluateur doit traiter la route
- Seuls les évaluateurs qui renvoient `true` seront invoqués pour la route
- Filtre les routes en vérifiant la présence de l'annotation `@RequireOwnership`

#### `evaluate(...)` {#evaluate-method}

- Vérifie d'abord si l'utilisateur est authentifié
- Obtient l'ID de l'utilisateur connecté à partir de `securityContext.getPrincipal()`
- Obtient la valeur du paramètre de route à partir de `context.getRouteParameters().get(paramName)`
- Compare les deux ID
- S'ils correspondent, délègue à `chain.evaluate()` pour permettre l'exécution d'autres évaluateurs
- S'ils ne correspondent pas, renvoie `deny()` avec une raison

### Exemple de flux {#flow-example}

**Lorsque la vérification de propriété échoue :**

1. L'utilisateur `123` se connecte et navigue vers `/users/456/edit`
2. `OwnershipEvaluator.supports()` renvoie `true` (la route a `@RequireOwnership`)
3. `OwnershipEvaluator.evaluate()` s'exécute :
   - `currentUserId = "123"` (à partir du contexte de sécurité)
   - `requestedUserId = "456"` (à partir du paramètre de route `:userId`)
   - `"123".equals("456")` → `false`
   - Renvoie `RouteAccessDecision.deny("Vous ne pouvez accéder qu'à vos propres ressources")`
4. L'utilisateur est redirigé vers une page d'accès refusé

**Lorsque la vérification de propriété réussit :**

1. L'utilisateur `123` se connecte et navigue vers `/users/123/edit`
2. `OwnershipEvaluator.evaluate()` s'exécute :
   - `currentUserId = "123"`, `requestedUserId = "123"`
   - Les ID correspondent → appelle `chain.evaluate()` pour continuer
3. Si aucun autre évaluateur ne refuse l'accès, l'utilisateur se voit accorder l'accès

## Comprendre la chaîne d'évaluateurs {#understanding-the-evaluator-chain}

Le système de sécurité utilise un **modèle de chaîne de responsabilité** où les évaluateurs sont traités par ordre de priorité. Les évaluateurs peuvent soit prendre des décisions terminales, soit déléguer à la chaîne pour combiner plusieurs vérifications.

### Comment la chaîne fonctionne {#how-chain-works}

1. Les évaluateurs sont triés par priorité (nombres plus bas en premier)
2. Pour chaque évaluateur, `supports(routeClass)` est appelé pour vérifier s'il s'applique
3. Si `supports()` renvoie `true`, la méthode `evaluate()` de l'évaluateur est appelée
4. L'évaluateur peut soit :
   - **Retourner une décision terminale** (`grant()` ou `deny()`) - **interrompt la chaîne**
   - **Délègue à la chaîne** en appelant `chain.evaluate()` - **permet à d'autres évaluateurs de s'exécuter**
5. Si la chaîne se termine sans décision et que la sécurité par défaut est activée, les utilisateurs non authentifiés sont refusés

### Décisions terminales {#terminal-decisions}

Arrêtez immédiatement la chaîne :

#### `RouteAccessDecision.grant()`

- Accorde l'accès et arrête l'évaluation supplémentaire
- Utilisé par `@AnonymousAccess` et `@PermitAll` - ce sont des autorisations complètes qui ne se combinent pas avec d'autres vérifications

#### `RouteAccessDecision.deny(reason)`

- Refuse l'accès et arrête l'évaluation supplémentaire
- Utilisé par `@DenyAll` et lorsque des vérifications personnalisées échouent
- Exemple : `RouteAccessDecision.deny("Vous ne pouvez accéder qu'à vos propres ressources")`

#### `RouteAccessDecision.denyAuthentication()`

- Redirige vers la page de connexion
- Utilisé lorsque l'authentification est requise mais absente

### Délégation de chaîne {#chain-delegation}

Permet de combiner des vérifications :

#### `chain.evaluate(routeClass, context, securityContext)`

- Passe le contrôle au prochain évaluateur de la chaîne
- Permet de combiner plusieurs vérifications d'autorisation
- Utilisé par `@RolesAllowed` et `@RouteAccess` après que leurs vérifications aient réussi
- Les évaluateurs personnalisés devraient utiliser ce modèle lorsque les vérifications réussissent pour permettre la composition

## Priorité des évaluateurs {#evaluator-priority}

Les évaluateurs sont vérifiés par ordre de priorité (nombres plus bas en premier). Les évaluateurs du cadre utilisent la priorité 1-9, les évaluateurs personnalisés doivent utiliser 10 ou plus.

Les évaluateurs intégrés sont enregistrés dans cet ordre :

```java
// Priorité 1 : @DenyAll - bloque tout
// Priorité 2 : @AnonymousAccess - permet l'accès anonymisé
// Priorité 3 : AuthenticationRequiredEvaluator - assure l'auth pour @PermitAll/@RolesAllowed
// Priorité 4 : @PermitAll - nécessite uniquement l'authentification
// Priorité 5 : @RolesAllowed - nécessite des rôles spécifiques
// Priorité 6 : @RouteAccess - expressions SpEL (Spring Security uniquement)
// Priorité 10+ : Évaluateurs personnalisés (comme @RequireOwnership)
```

### Comment la priorité affecte l'évaluation {#priority-affects-evaluation}

- Les évaluateurs de priorité inférieure s'exécutent en premier et peuvent "court-circuiter" la chaîne
- `@DenyAll` (priorité 1) s'exécute en premier - s'il est présent, l'accès est toujours refusé
- `@AnonymousAccess` (priorité 2) s'exécute ensuite - s'il est présent, l'accès est toujours accordé (même sans authentification)
- `AuthenticationRequiredEvaluator` (priorité 3) vérifie si la route nécessite une authentification et si l'utilisateur est authentifié
- Si aucun évaluateur ne traite la route, la logique de sécurité par défaut s'applique

### Définir la priorité {#setting-priority}

Définissez la priorité avec l'annotation `@RegisteredEvaluator` :

```java
@RegisteredEvaluator(priority = 10)  // S'exécute après les évaluateurs intégrés
public class OwnershipEvaluator implements RouteSecurityEvaluator {
  // ...
}
```

:::tip Plage de priorités
Les évaluateurs personnalisés doivent utiliser une priorité de 10 ou plus. Les priorités 1-9 sont réservées aux évaluateurs du framework. Si vous utilisez une priorité dans la plage réservée, vous recevrez un avertissement dans les journaux.
:::

## Combinaison d'évaluateurs {#combining-evaluators}

Les évaluateurs qui délèguent à la chaîne peuvent être combinés pour créer une logique d'autorisation complexe. Les routes peuvent avoir plusieurs annotations de sécurité :

### Combinaison de vérifications de rôle avec une logique personnalisée {#combining-roles-custom}

```java
@Route("/users/:userId/settings")
@RolesAllowed("USER")
@RequireOwnership("userId")
public class UserSettingsView extends Composite<Div> {
  // Doit avoir le rôle USER ET accéder à ses propres paramètres
}
```

**Comment ça fonctionne :**
1. `RolesAllowedEvaluator` (priorité 5) vérifie si l'utilisateur a le rôle "USER"
2. Si oui, appelle `chain.evaluate()` pour continuer
3. `OwnershipEvaluator` (priorité 10) vérifie si `userId` correspond à l'utilisateur connecté
4. Si oui, appelle `chain.evaluate()` pour continuer
5. La chaîne se termine → accès accordé

### Combinaison d'expressions SpEL avec une logique personnalisée {#combining-spel-custom}

```java
@Route("/admin/users/:userId/edit")
@RouteAccess("hasRole('ADMIN')")
@RequireOwnership("userId")
public class AdminEditUserView extends Composite<Div> {
  // Doit être administrateur ET accéder à son propre compte
}
```

### Ce qui ne peut pas être combiné {#cant-combine}

`@AnonymousAccess` et `@PermitAll` prennent des **décisions terminales** - elles accordent immédiatement l'accès sans appeler la chaîne. Vous ne pouvez pas les combiner avec des évaluateurs personnalisés :

```java
// @PermitAll accorde immédiatement l'accès, @RequireOwnership ne s'exécute jamais
@Route("/users/:userId/profile")
@PermitAll
@RequireOwnership("userId")
public class ProfileView extends Composite<Div> {
  // ...
}
```

Pour les ressources auxquelles tous les utilisateurs authentifiés peuvent accéder, utilisez `@RolesAllowed` avec un rôle commun à la place :

```java
// @RolesAllowed délègue à la chaîne
@Route("/users/:userId/profile")
@RolesAllowed("USER")
@RequireOwnership("userId")
public class ProfileView extends Composite<Div> {
  // Doit être un utilisateur authentifié ET accéder à son propre profil
}
```
