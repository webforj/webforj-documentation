---
sidebar_position: 4
title: Navigation Events
_i18n_hash: f41ebca54f574eeac4834234cf3a0e5b
---
En plus des événements de cycle de vie spécifiques aux composants, vous pouvez enregistrer des **écouteurs d'événements globaux** au niveau du routeur. Cela permet de suivre la navigation de manière globale dans toute l'application, ce qui est utile pour la journalisation, l'analyse ou d'autres préoccupations transversales.

## Exemple : Écouteur de navigation global {#example-global-navigation-listener}

```java
Router.getCurrent().addNavigateListener(event -> {
  Location location = event.getLocation();
  console().log("Navigué vers : " + location.getFullURI());
});
```

Dans cet exemple, un écouteur global est enregistré pour journaliser chaque événement de navigation dans l'application. Cela est utile pour l'audit ou le suivi des vues de pages.

## Enregistrement d'écouteurs d'événements de cycle de vie globaux {#registering-global-lifecycle-event-listeners}

Des écouteurs globaux peuvent être attachés à divers événements de cycle de vie, y compris :

- **`WillEnterEvent`** : Déclenché avant qu'un composant de route ne soit attaché au DOM.
- **`DidEnterEvent`** : Déclenché après qu'un composant soit attaché au DOM.
- **`WillLeaveEvent`** : Déclenché avant qu'un composant ne soit détaché du DOM.
- **`DidLeaveEvent`** : Déclenché après qu'un composant soit détaché du DOM.
- **`NavigateEvent`** : Déclenché chaque fois qu'une navigation se produit.
- **`ActivateEvent`** (depuis 25.03) : Déclenché lorsqu'un composant mis en cache est réactivé.

:::tip Utilisation des observateurs pour s'accrocher aux événements de cycle de vie
Vous pouvez également vous accrocher aux événements de cycle de vie en utilisant des observateurs. Pour plus de détails, référez-vous aux [Observateurs de cycle de vie](./observers).
:::

## Exemple : Écouteur global `WillLeaveEvent` {#example-global-willleaveevent-listener}

```java
Router.getCurrent().addWillLeaveListener(event -> {
  ConfirmDialog.Result result = showConfirmDialog(
      "Êtes-vous sûr de vouloir quitter cette vue?",
      "Quitter la vue",
      ConfirmDialog.OptionType.OK_CANCEL,
      ConfirmDialog.MessageType.WARNING);

  event.veto(result == ConfirmDialog.Result.CANCEL);
});
```

Dans ce cas, le `WillLeaveEvent` est utilisé globalement pour afficher une boîte de dialogue de confirmation avant que l'utilisateur ne navigue loin de n'importe quelle vue.
