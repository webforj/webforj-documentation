---
sidebar_position: 4
title: Evaluator Chain
_i18n_hash: 5055a72d450daf8b98bdb995380a2e13
---
La chaîne d'évaluateurs est le cœur du système de sécurité de webforJ. C'est une séquence d'évaluateurs ordonnée par priorité qui examine les routes et prend des décisions d'accès en utilisant le modèle de conception de la chaîne de responsabilité. Comprendre comment fonctionne la chaîne vous aide à créer des évaluateurs personnalisés et à résoudre des refus d'accès inattendus.

## Le modèle de chaîne de responsabilité {#the-chain-of-responsibility-pattern}

La chaîne d'évaluateurs utilise le modèle de chaîne de responsabilité, où chaque évaluateur de la séquence peut soit traiter une demande de navigation, soit la passer à l'évaluateur suivant. Cela crée un système où la logique de sécurité est répartie sur plusieurs évaluateurs spécialisés plutôt que centralisée dans un seul vérificateur monolithique.

Lorsqu'une route nécessite une évaluation, le gestionnaire de sécurité crée une chaîne et la démarre au premier évaluateur. Cet évaluateur examine la route et fait l'un des trois choix suivants :

1. **Accorder l'accès :** L'évaluateur approuve la route et retourne immédiatement. Aucun autre évaluateur n'est exécuté.
2. **Refuser l'accès :** L'évaluateur bloque la route et retourne immédiatement. Aucun autre évaluateur n'est exécuté.
3. **Déléguer :** L'évaluateur ne prend pas de décision et appelle `chain.evaluate()` pour passer le contrôle à l'évaluateur suivant.

Ce modèle permet aux évaluateurs de se concentrer sur des cas spécifiques. Chaque évaluateur implémente `supports(Class<?> routeClass)` pour indiquer quelles routes il traite. Par exemple, `AnonymousAccessEvaluator` ne s'exécute que pour les routes marquées avec `@AnonymousAccess`, le gestionnaire ne l'invoque jamais pour d'autres routes.

## Comment la chaîne est construite {#how-the-chain-is-built}

Le gestionnaire de sécurité maintient une liste d'évaluateurs enregistrés, chacun avec une priorité associée. Lorsqu'une route nécessite une évaluation, le gestionnaire trie les évaluateurs par priorité (nombres plus bas en premier) et crée une chaîne.

Les évaluateurs sont enregistrés en utilisant la méthode `registerEvaluator()` du gestionnaire :

```java
// Enregistrer les évaluateurs intégrés
securityManager.registerEvaluator(new DenyAllEvaluator(), 0);
securityManager.registerEvaluator(new AnonymousAccessEvaluator(), 1);
securityManager.registerEvaluator(new PermitAllEvaluator(), 2);
securityManager.registerEvaluator(new RolesAllowedEvaluator(), 3);

// Enregistrer des évaluateurs personnalisés
securityManager.registerEvaluator(new SubscriptionEvaluator(), 10);
```

La priorité détermine l'ordre d'évaluation. Les priorités plus basses s'exécutent en premier, leur donnant la première occasion de prendre des décisions d'accès. Cela est important pour la sécurité car cela permet aux évaluateurs critiques de bloquer l'accès avant que les évaluateurs permissifs ne puissent l'accorder.

La chaîne est sans état et créée fraîche pour chaque demande de navigation afin qu'une évaluation de navigation n'affecte pas une autre.

## Flux d'exécution de la chaîne {#chain-execution-flow}

Lorsque la chaîne commence, elle débute au premier évaluateur (priorité la plus basse) et progresse séquentiellement :

```mermaid
flowchart TD
  Start["Le gestionnaire démarre la chaîne"] --> Eval["Exécuter les évaluateurs<br/>(dans l'ordre de priorité)"]

  Eval --> Check{"Décision de l'évaluateur ?"}
  Check -->|Accorder| Grant["Accorder l'accès<br/>STOP"]
  Check -->|Refuser| Deny["Refuser l'accès<br/>STOP"]
  Check -->|Déléguer| Next["Prochain évaluateur"]

  Next --> Eval

  Check -->|Chaîne épuisée| Default{"Sécurisé par défaut ?"}
  Default -->|Oui ET pas authentifié| DenyDefault["Refuser l'authentification<br/>STOP"]
  Default -->|Non OU authentifié| GrantDefault["Accorder l'accès<br/>STOP"]
```

La chaîne s'arrête dès qu'un évaluateur accorde ou refuse l'accès. Si tous les évaluateurs délèguent, la chaîne s'épuise et retombe sur un comportement sécurisé par défaut.

## Ordre des évaluateurs intégrés {#built-in-evaluator-ordering}

Quatre évaluateurs intégrés traitent les annotations standard :

| Évaluateur | Annotation | Comportement | Comportement de la chaîne | Ordre typique |
|-----------|------------|----------|----------------|---------------|
| `DenyAllEvaluator` | `@DenyAll` | Bloque toujours l'accès | Arrête la chaîne (terminal) | S'exécute en premier |
| `AnonymousAccessEvaluator` | `@AnonymousAccess` | Permet à tout le monde (authentifié ou non) | Arrête la chaîne (terminal) | S'exécute tôt |
| `PermitAllEvaluator` | `@PermitAll` | Nécessite une authentification, permet à tous les utilisateurs authentifiés | Arrête la chaîne (terminal) | S'exécute au milieu de la chaîne |
| `RolesAllowedEvaluator` | `@RolesAllowed` | Nécessite une authentification et un rôle spécifique | **Continue la chaîne** (composable) | S'exécute plus tard |

:::note
Les numéros de priorité exacts sont attribués lors de l'enregistrement des évaluateurs et diffèrent entre les implémentations. Consultez [Spring Security](/docs/security/getting-started) ou [Implémentation Personnalisée](/docs/security/architecture/custom-implementation) pour des valeurs spécifiques.
:::

## Comment les évaluateurs délèguent {#how-evaluators-delegate}

Avant d'invoquer un évaluateur, le gestionnaire appelle sa méthode `supports(Class<?> routeClass)`. Seuls les évaluateurs renvoyant `true` sont invoqués. Ce filtrage oblige les évaluateurs à ne s'exécuter que pour les routes qu'ils sont conçus pour traiter.

Lorsqu'un évaluateur est invoqué, il peut soit :
- **Prendre une décision :** Retourner accorder ou refuser pour arrêter la chaîne
- **Déléguer :** Appeler `chain.evaluate()` pour passer le contrôle à l'évaluateur suivant dans la séquence de priorité

Par exemple, `RolesAllowedEvaluator` vérifie si l'utilisateur a le rôle requis. Si c'est le cas, il appelle `chain.evaluate()` pour permettre des vérifications supplémentaires par des évaluateurs de priorité supérieure. Cette délégation active permet la composition des évaluateurs.

Les évaluateurs terminaux comme `PermitAllEvaluator` prennent des décisions finales sans appeler la chaîne, empêchant ainsi une évaluation ultérieure.

## Lorsque la chaîne s'épuise {#when-the-chain-exhausts}

Si chaque évaluateur délègue et qu'aucun ne prend de décision, la chaîne s'épuise, il n'y a plus d'évaluateurs à exécuter. À ce stade, le système de sécurité applique un retour basé sur la configuration `isSecureByDefault()` :

**Sécurisé par défaut activé** (`isSecureByDefault() == true`) :
- Si l'utilisateur est authentifié : Accorder l'accès
- Si l'utilisateur n'est pas authentifié : Refuser avec authentification requise

**Sécurisé par défaut désactivé** (`isSecureByDefault() == false`) :
- Accorder l'accès indépendamment de l'authentification

Les routes sans annotations de sécurité ont tout de même un comportement défini. Avec la sécurité par défaut activée, les routes non annotées nécessitent une authentification. Avec elle désactivée, les routes non annotées sont publiques.

## Priorités des évaluateurs personnalisés {#custom-evaluator-priorities}

Lors de la création d'évaluateurs personnalisés, choisissez soigneusement les priorités :

- **0-9** : Réservé aux évaluateurs du framework de base. Évitez d'utiliser ces priorités à moins de remplacer des évaluateurs intégrés.
- **10-99** : Recommandé pour les évaluateurs de logique métier personnalisés. Ceux-ci s'exécutent après les évaluateurs de base mais avant les solutions de repli génériques.

Exemple :

```java title="SubscriptionEvaluator.java"
// Évaluateur personnalisé pour l'accès basé sur l'abonnement
@RegisteredEvaluator(priority = 10)
public class SubscriptionEvaluator implements RouteSecurityEvaluator {
  @Override
  public boolean supports(Class<?> routeClass) {
    return routeClass.isAnnotationPresent(RequiresSubscription.class);
  }

  @Override
  public RouteAccessDecision evaluate(Class<?> routeClass,
                                       NavigationContext context,
                                       RouteSecurityContext securityContext,
                                       SecurityEvaluatorChain chain) {
    // Vérifier si l'utilisateur a un abonnement actif
    boolean hasSubscription = checkSubscription(securityContext);

    if (!hasSubscription) {
      return RouteAccessDecision.deny("Un abonnement actif est requis");
    }

    // L'utilisateur a un abonnement - continuer la chaîne pour des vérifications supplémentaires
    return chain.evaluate(routeClass, context, securityContext);
  }
}
```

Cet évaluateur s'exécute avec la priorité 10, après les évaluateurs de base. Si l'utilisateur a un abonnement actif, il délègue à la chaîne, permettant la composition avec d'autres évaluateurs.

## Composition des évaluateurs {#evaluator-composition}

La plupart des évaluateurs intégrés sont **terminaux**, ils prennent une décision finale et arrêtent la chaîne. Seul `RolesAllowedEvaluator` continue la chaîne après avoir accordé l'accès, permettant la composition avec des évaluateurs personnalisés.

**Évaluateurs terminaux (ne peuvent pas être composés) :**
- `@DenyAll` : Refuse toujours, arrête la chaîne
- `@AnonymousAccess` : Accorde toujours, arrête la chaîne
- `@PermitAll` : Accorde aux utilisateurs authentifiés, arrête la chaîne

**Évaluateurs composables :**
- `@RolesAllowed` : Si l'utilisateur a le rôle, **continue la chaîne** pour permettre des vérifications supplémentaires

### Composition qui fonctionne {#composition-that-works}

Vous pouvez composer `@RolesAllowed` avec des évaluateurs personnalisés :

```java
@Route("/premium-admin")
@RolesAllowed("ADMIN")  // Vérifie le rôle, puis continue la chaîne
@RequiresSubscription   // Vérification personnalisée s'exécute après la vérification du rôle
public class PremiumAdminView extends Composite<Div> {
  // Nécessite le rôle ADMIN ET un abonnement actif
}
```

Flux :
1. `RolesAllowedEvaluator` vérifie si l'utilisateur a le rôle `ADMIN`
2. Si oui, appelle `chain.evaluate()` pour continuer
3. `SubscriptionEvaluator` vérifie l'état de l'abonnement (s'exécute plus tard dans la chaîne)
4. Si l'abonnement est actif, accorde l'accès ; sinon refuse

### Composition qui ne fonctionne pas {#composition-that-does-not-work}

Vous **ne pouvez pas** combiner `@PermitAll` avec d'autres évaluateurs car cela arrête la chaîne :

```java
@Route("/wrong")
@PermitAll           // Accorde immédiatement, arrête la chaîne
@RolesAllowed("ADMIN")  // NE S'EXÉCUTE JAMAIS !
public class WrongView extends Composite<Div> {
  // Cela accorde l'accès à TOUS les utilisateurs authentifiés
  // @RolesAllowed est ignoré
}
```

`PermitAllEvaluator` s'exécute en premier (enregistré avec une priorité plus basse), accorde l'accès à tout utilisateur authentifié, et retourne sans appeler `chain.evaluate()`. L'`RolesAllowedEvaluator` ne s'exécute jamais.
