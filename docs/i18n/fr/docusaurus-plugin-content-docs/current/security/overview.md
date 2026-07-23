---
sidebar_position: 1
title: Security
hide_table_of_contents: true
hide_giscus_comments: true
description: >-
  Protect webforJ routes with declarative annotations and centralized
  authentication and authorization enforcement.
_i18n_hash: 850b9636996cb17a07a7aff25ac3cd0e
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

# Sécurité <DocChip chip='since' label='25.10' />

:::note Aperçu Public
Cette fonctionnalité est en aperçu public et prête pour un usage en production. Pendant la période d'aperçu, les API peuvent être affinées en fonction des retours de la communauté des développeurs. Tout changement sera annoncé à l'avance par le biais de notes de version et des guides de migration seront fournis si nécessaire.
:::

Dans les applications web modernes, **la sécurité** fait référence au contrôle d'accès aux différentes parties de votre application en fonction de l'identité et des permissions de l'utilisateur. Dans webforJ, la sécurité fournit un cadre pour le **contrôle d'accès au niveau des routes**, où vous pouvez protéger des vues, exiger une authentification et faire respecter des permissions basées sur les rôles.

<AISkillTip skill="webforj-securing-apps" />

## Routage traditionnel VS routage sécurisé {#traditional-vs-secured-routing}

Dans le routage traditionnel non sécurisé, toutes les routes de votre application sont accessibles à quiconque connaît l'URL. Cela signifie que les utilisateurs peuvent naviguer vers des pages sensibles comme des panneaux d'administration ou des tableaux de bord d'utilisateur sans aucune vérification d'authentification ou d'autorisation. Le fardeau incombe aux développeurs de vérifier manuellement les permissions dans chaque composant, ce qui conduit à une application incohérente de la sécurité et à des vulnérabilités potentielles.

Cette approche introduit plusieurs problèmes :

1. **Vérifications manuelles** : Les développeurs doivent se souvenir d'ajouter la logique de sécurité dans chaque vue ou mise en page protégée.
2. **Application incohérente** : Les vérifications de sécurité disséminées dans la base de code entraînent des lacunes et des erreurs.
3. **Surcharge de maintenance** : Changer les règles d'accès nécessite de mettre à jour plusieurs fichiers.
4. **Pas de contrôle centralisé** : Aucun endroit unique pour comprendre ou gérer la sécurité de l'application.

**Le routage sécurisé** dans webforJ résout ce problème en permettant le contrôle d'accès directement au niveau des routes. Le système de sécurité applique automatiquement les règles avant que tout composant ne soit rendu, fournissant une approche centralisée et déclarative de la sécurité de l'application. Voici comment cela fonctionne :

1. **Annotations déclaratives** : Marquez les routes avec des annotations de sécurité pour définir les exigences d'accès.
2. **Application automatique** : Le système de sécurité vérifie les permissions avant de rendre toute vue.
3. **Configuration centralisée** : Définissez le comportement de sécurité à un seul endroit et appliquez-le de manière cohérente.
4. **Implémentations flexibles** : Choisissez entre l'intégration de Spring Security ou une implémentation Java simple personnalisée.

Cette conception permet l'**authentification** (vérification de l'identité de l'utilisateur) et l'**autorisation** (vérification de ce que l'utilisateur peut accéder), ainsi seuls les utilisateurs autorisés se voient accorder l'accès aux routes protégées. Les utilisateurs non autorisés sont automatiquement redirigés ou se voient refuser l'accès en fonction des règles de sécurité configurées.

## Exemple de routage sécurisé dans webforJ {#example-of-secured-routing-in-webforj}

Voici un exemple montrant différents niveaux de sécurité dans une application webforJ :

```java title="LoginView.java"
// Page de connexion publique - accessible à tous
@Route("/login")
@AnonymousAccess
public class LoginView extends Composite<Login> {
  private final Login self = getBoundComponent();

  public LoginView() {
    self.onSubmit(e -> {
      handleLogin(e.getUsername(), e.getPassword());
    });

    whenAttached().thenAccept(c -> {
      self.open();
    });
  }
}
```

```java title="ProductsView.java"
// Produits - nécessite une authentification
@Route(value = "/", outlet = MainLayout.class)
public class ProductsView extends Composite<FlexLayout> {

  public ProductsView() {
    // vue des produits
  }
}
```

```java title="InvoicesView.java"
// Factures - nécessite le rôle COMPTABLE
@Route(value = "/invoices", outlet = MainLayout.class)
@RolesAllowed("ACCOUNTANT")
public class InvoicesView extends Composite<FlexLayout> {

  public InvoicesView() {
    // vue des factures
  }
}
```

Dans cette configuration :

- La `LoginView` est marquée avec `@AnonymousAccess`, permettant aux utilisateurs non authentifiés d'y accéder.
- La `ProductsView` n'a pas d'annotation de sécurité, ce qui signifie qu'elle nécessite une authentification par défaut (lorsque le mode `secure-by-default` est activé).
- La `InvoicesView` nécessite le rôle `ACCOUNTANT`, donc seuls les utilisateurs ayant des permissions comptables peuvent accéder aux factures.

## Comment fonctionne la sécurité {#how-security-works}

Lorsque un utilisateur tente de naviguer vers une route, le système de sécurité suit ce flux :

1. **Navigation initiée** : L'utilisateur clique sur un lien ou saisit une URL.
2. **Vérification de sécurité** : Avant de rendre le composant, le système évalue les annotations et les règles de sécurité.
3. **Décision** : En fonction de l'état d'authentification de l'utilisateur et des rôles :
   - **Accorder** : Autoriser la navigation et rendre le composant.
   - **Refuser** : Bloquer la navigation et rediriger vers la page de connexion ou une page d'accès refusé.
4. **Rendre ou rediriger** : Soit le composant demandé s'affiche, soit l'utilisateur est redirigé de manière appropriée.

Avec l'application automatique, les règles de sécurité sont appliquées de manière cohérente dans l'ensemble de votre application, de sorte que le contrôle d'accès est géré avant que tout composant ne soit rendu et que les développeurs n'ont pas besoin d'ajouter de vérifications manuelles dans chaque vue.

## Authentification VS autorisation {#authentication-vs-authorization}

Pour mettre en œuvre la sécurité dans votre application correctement, il est important de connaître la différence entre ces deux concepts :

- **Authentification** : Vérification de qui est l'utilisateur. Cela se produit généralement lors de la connexion lorsque l'utilisateur fournit des identifiants (nom d'utilisateur et mot de passe). Une fois authentifié, l'identité de l'utilisateur est stockée dans la session ou le contexte de sécurité.

- **Autorisation** : Vérification de ce que l'utilisateur authentifié peut accéder. Cela implique de vérifier si l'utilisateur a les rôles ou permissions requis pour accéder à une route spécifique. L'autorisation se produit chaque fois qu'un utilisateur navigue vers une route protégée.

Le système de sécurité de webforJ gère les deux aspects :

- Les annotations comme `@PermitAll` gèrent les exigences d'authentification.
- Les annotations comme `@RolesAllowed` gèrent les exigences d'autorisation.

## Débuter {#getting-started}

Ce guide suppose que vous utilisez **Spring Boot avec Spring Security**, qui est l'approche recommandée pour la plupart des applications webforJ. Spring Security fournit une authentification et une autorisation conformes aux normes industrielles avec une configuration automatique via Spring Boot.

Le reste de cette documentation vous guide à travers la sécurisation de vos routes avec Spring Security, de la configuration de base à des fonctionnalités avancées. Si vous n'utilisez pas Spring Boot ou si vous avez besoin d'une implémentation de sécurité personnalisée, consultez le [guide d'architecture de sécurité](/docs/security/architecture/overview) pour en apprendre davantage sur le fonctionnement du système et comment implémenter une sécurité personnalisée.

## Sujets {#topics}

<DocCardList className="topics-section" />
