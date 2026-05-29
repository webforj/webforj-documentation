---
sidebar_position: 2
title: Lifecycle Observers
_i18n_hash: a584e996523ba2b98ecb9d7ab2f366f3
---
Les observateurs permettent aux composants de réagir aux événements du cycle de vie en implémentant des interfaces pour des étapes spécifiques. Ce modèle garantit une séparation claire des préoccupations et simplifie la gestion de la logique de navigation.

## Observateurs disponibles {#available-observers}

- **`WillEnterObserver`** : Vous permet de gérer des tâches avant qu'une route ne soit entrée, telles que la récupération de données nécessaires ou le blocage de la navigation.
- **`DidEnterObserver`** : Idéal pour gérer des actions après que le composant a été attaché, telles que le rendu de données ou le déclenchement d'animations.
- **`WillLeaveObserver`** : Fournit un moyen de gérer la logique avant qu'un utilisateur ne quitte une route, comme vérifier les modifications non enregistrées.
- **`DidLeaveObserver`** : Utilisé pour des actions de nettoyage ou d'autres tâches qui doivent être exécutées après qu'un composant a été détaché du DOM.
- **`ActivateObserver`** : <DocChip chip='since' label='25.03' /> Déclenché lorsqu'un composant mis en cache est réactivé, par exemple lors de la navigation vers la même route avec des paramètres différents.

## Exemple : authentification avec `WillEnterObserver` {#example-authentication-with-willenterobserver}

```java
@Route(value = "dashboard")
public class DashboardView extends Composite<Div> implements WillEnterObserver {

  @Override
  public void onWillEnter(WillEnterEvent event, ParametersBag parameters) {
    boolean isAuthenticated = authService.isAuthenticated();
    event.veto(!isAuthenticated);

    if (!isAuthenticated) {
      event.getRouter().navigate(LoginView.class);
    }
  }
}
```

Ici, `onWillEnter` vérifie si l'utilisateur est authentifié. Sinon, la navigation est opposée, empêchant la complétion de la navigation et redirigeant vers la page de connexion.

:::warning Exemple de routes authentifiées - Non prêt pour la production
Cet exemple précédent est juste un exemple de la façon d'utiliser des routes authentifiées.
Ce **n'est pas** un exemple de la façon dont vous écririez un système d'authentification au niveau de la production.
Vous devrez prendre les concepts et modèles utilisés dans cet exemple et les adapter pour qu'ils fonctionnent avec votre flux/systeme d'authentification pour votre application.
:::

## Exemple : récupération de données à l'entrée de la route avec `DidEnterObserver` {#example-fetching-data-on-route-entry-with-didenterobserver}

```java
@Route(value = "profile")
public class ProfileView extends Composite<Div> implements DidEnterObserver {

  @Override
  public void onDidEnter(DidEnterEvent event, ParametersBag parameters) {
    String userId = parameters.get("userId").orElseThrow();
    UserService.fetchProfile(userId).thenAccept(
        profile -> updateProfileUI(profile));
  }

  private void updateProfileUI(Profile profile) {
    // Code pour mettre à jour l'interface utilisateur avec les données du profil
  }
}
```

Cet exemple démontre l'utilisation de `DidEnterObserver` pour récupérer et afficher les données de profil une fois que le composant est attaché au DOM.

## Exemple : Gestion des modifications non enregistrées avec `WillLeaveObserver` {#example-handling-unsaved-changes-with-willleaveobserver}

```java
@Route(value = "edit-profile")
public class EditProfileView extends Composite<Div> implements WillLeaveObserver {
  private boolean hasUnsavedChanges = false;

  public EditProfileView() {
    // Logique pour détecter les modifications non enregistrées
  }

  @Override
  public void onWillLeave(WillLeaveEvent event, ParametersBag parameters) {
    event.veto(hasUnsavedChanges);

    if(hasUnsavedChanges) {
      ConfirmDialog.Result result = showConfirmDialog(
          "Il y a des modifications non enregistrées. Voulez-vous les ignorer ou les sauvegarder ?",
          "Modifications non enregistrées",
          ConfirmDialog.OptionType.OK_CANCEL,
          ConfirmDialog.MessageType.WARNING);
    }
  }
}
```

Dans cet exemple, `onWillLeave` invite l'utilisateur avec une boîte de confirmation s'il y a des modifications non enregistrées, opposant la navigation si l'utilisateur choisit de rester.

:::info Blocage de navigation et gestion de veto
Pour plus d'informations sur le blocage de la navigation, voir [Blocage de navigation et gestion de veto](./navigation-blocking)
:::

## Exemple : Nettoyage avec `DidLeaveObserver` {#example-cleanup-with-didleaveobserver}

```java
@Route(value = "notifications")
public class NotificationsView extends Composite<Div> implements DidLeaveObserver {

  @Override
  public void onDidLeave(DidLeaveEvent event, ParametersBag parameters) {
    NotificationService.clearActiveNotifications();
  }
}
```

Cet exemple efface les notifications après que l'utilisateur ait quitté le `NotificationsView`, utilisant le `DidLeaveObserver` pour le nettoyage.

## Exemple : Actualisation des données avec `ActivateObserver` <DocChip chip='since' label='25.03' /> {#example-refreshing-data-with-activateobserver}

```java
@Route(value = "product/:id")
public class ProductView extends Composite<Div> implements ActivateObserver {
  private String currentProductId;

  @Override
  public void onActivate(ActivateEvent event, ParametersBag parameters) {
    String productId = parameters.get("id").orElseThrow();
    
    // Le composant est réutilisé avec des paramètres différents
    if (!productId.equals(currentProductId)) {
      currentProductId = productId;
      refreshProductData(productId);
    }
  }

  private void refreshProductData(String productId) {
    // Code pour récupérer et afficher de nouvelles données produits
    ProductService.fetchProduct(productId).thenAccept(
        product -> updateProductUI(product));
  }
}
```

Cet exemple démontre l'utilisation de `ActivateObserver` pour rafraîchir les données lors de la navigation vers la même route avec des paramètres différents. Le composant reste mis en cache et est réactivé plutôt que recréé, donc l'interface utilisateur se met à jour pour afficher les données correctes pour les paramètres actifs sans instancier un nouveau composant.

:::tip Activation dans les hiérarchies de composants
Lors de la navigation vers une route, l'événement `Activate` se déclenche pour **tous les composants mis en cache dans la hiérarchie** qui restent dans le chemin actuel. Par exemple, lors de la navigation de `/products/123` à `/products/456`, à la fois le composant parent `ProductsLayout` et le composant enfant `ProductView` reçoivent l'événement `Activate` s'ils sont mis en cache et restent dans la hiérarchie de route.
:::
