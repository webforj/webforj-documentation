---
sidebar_position: 2
title: Lifecycle Observers
_i18n_hash: be571bd197730689ba8346b2ef702a3f
---
Les observateurs permettent aux composants de réagir aux événements du cycle de vie en implémentant des interfaces pour des étapes spécifiques. Ce modèle garantit une séparation claire des préoccupations et simplifie la gestion de la logique de navigation.

## Observateurs disponibles {#available-observers}

- **`WillEnterObserver`** : Vous permet de gérer des tâches avant qu'une route ne soit entrée, comme le fetch de données nécessaires ou le blocage de la navigation.
- **`DidEnterObserver`** : Idéal pour gérer des actions après que le composant a été attaché, comme le rendu de données ou le déclenchement d'animations.
- **`WillLeaveObserver`** : Fournit un moyen de gérer la logique avant qu'un utilisateur ne quitte une route, comme vérifier les modifications non enregistrées.
- **`DidLeaveObserver`** : Utilisé pour des actions de nettoyage ou d'autres tâches qui devraient s'exécuter après qu'un composant a été détaché du DOM.

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

Ici, `onWillEnter` vérifie si l'utilisateur est authentifié. Si ce n'est pas le cas, la navigation est refusée, empêchant la navigation d'être complète et redirigeant plutôt vers la page de connexion.

:::warning Exemple de Routes Authentifiées - Pas Prêt pour la Production
Ce qui précède est juste un exemple de la façon d'utiliser des routes authentifiées. 
Cela **n'est pas** un exemple de la manière dont vous écririez un système d'authentification au niveau de la production. 
Vous devrez prendre les concepts et les modèles utilisés dans cet exemple et les adapter pour qu'ils fonctionnent avec votre flux/systeme d'authentification pour votre application.
:::

## Exemple : récupération de données lors de l'entrée dans la route avec `DidEnterObserver` {#example-fetching-data-on-route-entry-with-didenterobserver}

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

Dans cet exemple, `onWillLeave` invite l'utilisateur avec une boîte de dialogue de confirmation s'il y a des modifications non enregistrées, refusant la navigation si l'utilisateur choisit de rester.

:::info Blocage de navigation et gestion des refus
Pour plus d'informations sur le blocage de la navigation, voir [Blocage de la navigation et gestion des refus](./navigation-blocking)
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
