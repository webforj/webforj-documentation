---
sidebar_position: 2
title: Lifecycle Observers
_i18n_hash: 18390849527056ed2780b761ae7919c1
---
Les observateurs permettent aux composants de réagir aux événements du cycle de vie en implémentant des interfaces pour des étapes spécifiques. Ce modèle assure une séparation claire des préoccupations et simplifie la gestion de la logique de navigation.

## Observateurs disponibles {#available-observers}

- **`WillEnterObserver`** : Permet de gérer des tâches avant qu'un itinéraire ne soit entrée, comme la récupération des données nécessaires ou le blocage de la navigation.
- **`DidEnterObserver`** : Idéal pour gérer des actions après que le composant a été attaché, comme le rendu des données ou le déclenchement d'animations.
- **`WillLeaveObserver`** : Fournit un moyen de gérer la logique avant qu'un utilisateur ne quitte un itinéraire, comme vérifier les modifications non enregistrées.
- **`DidLeaveObserver`** : Utilisé pour des actions de nettoyage ou d'autres tâches qui doivent s'exécuter après qu'un composant a été détaché du DOM.
- **`ActivateObserver`** (depuis le 25.03) : Déclenché lorsqu'un composant mis en cache est réactivé, comme lors de la navigation vers le même itinéraire avec des paramètres différents.

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

Ici, `onWillEnter` vérifie si l'utilisateur est authentifié. Si ce n'est pas le cas, la navigation est veto, empêchant l'achèvement de la navigation et redirigeant vers la page de connexion.

:::warning Exemple de routes authentifiées - Pas prêt pour la production
Ce qui précède est juste un exemple de la façon d'utiliser des routes authentifiées.
Ce **n'est pas** un exemple de la façon dont vous écririez un système d'authentification de niveau production.
Vous devrez prendre les concepts et modèles utilisés dans cet exemple et les adapter à votre flux/systeme d'authentification pour votre application.
:::

## Exemple : récupération des données à l'entrée d'un itinéraire avec `DidEnterObserver` {#example-fetching-data-on-route-entry-with-didenterobserver}

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
    // Code pour mettre à jour l'UI avec les données du profil
  }
}
```

Cet exemple démontre l'utilisation de `DidEnterObserver` pour récupérer et afficher les données du profil une fois que le composant est attaché au DOM.

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
          "Il y a des modifications non enregistrées. Voulez-vous les ignorer ou les enregistrer ?",
          "Modifications non enregistrées",
          ConfirmDialog.OptionType.OK_CANCEL,
          ConfirmDialog.MessageType.WARNING);
    }
  }
}
```

Dans cet exemple, `onWillLeave` invite l'utilisateur à un dialogue de confirmation s'il y a des modifications non enregistrées, tout en veto la navigation si l'utilisateur choisit de rester.

:::info Blocage de la navigation et gestion du veto
Pour plus d'informations sur le blocage de la navigation, consultez [Blocage de la navigation et gestion du veto](./navigation-blocking)
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

Cet exemple efface les notifications après que l'utilisateur quitte la `NotificationsView`, en utilisant le `DidLeaveObserver` pour le nettoyage.

## Exemple : Actualisation des données avec `ActivateObserver` {#example-refreshing-data-with-activateobserver}

:::info Depuis le 25.03
Le `ActivateObserver` et `ActivateEvent` sont disponibles à partir de la version webforJ `25.03`.
:::

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
    // Code pour récupérer et afficher les nouvelles données du produit
    ProductService.fetchProduct(productId).thenAccept(
        product -> updateProductUI(product));
  }
}
```

Cet exemple démontre l'utilisation de `ActivateObserver` pour actualiser les données lors de la navigation vers le même itinéraire avec des paramètres différents. Le composant reste mis en cache et est réactivé plutôt que recréé, de sorte que l'interface utilisateur se met à jour pour afficher les bonnes données pour les paramètres actuels sans instancier un nouveau composant.

:::tip Activation dans les hiérarchies de composants
Lors de la navigation vers un itinéraire, l'événement `Activate` se déclenche pour **tous les composants mis en cache dans la hiérarchie** qui demeurent dans le chemin actuel. Par exemple, lors de la navigation de `/products/123` à `/products/456`, à la fois le composant parent `ProductsLayout` et le composant enfant `ProductView` reçoivent l'événement `Activate` s'ils sont mis en cache et demeurent dans la hiérarchie des routes.
:::
