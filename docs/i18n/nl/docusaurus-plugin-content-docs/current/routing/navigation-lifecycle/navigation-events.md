---
sidebar_position: 4
title: Navigation Events
_i18n_hash: d7beed9a9d607e1decc18fa24417b213
---
Naast component-specifieke levenscyclusgebeurtenissen, kun je **globale gebeurtenisluisteraars** registreren op het routerniveau. Dit maakt het mogelijk om navigatie wereldwijd over de hele app te volgen, wat nuttig is voor loggen, analytics of andere cross-cutting concerns.

## Voorbeeld: Globale navigatie luisteren {#example-global-navigation-listener}

```java
Router.getCurrent().addNavigateListener(event -> {
  Location location = event.getLocation();
  console().log("Genavigeerd naar: " + location.getFullURI());
});
```

In dit voorbeeld wordt een globale luisteraar geregistreerd om elk navigatie-evenement in de app te loggen. Dit is nuttig voor auditing of het volgen van paginaweergaven.

## Registreren van globale levenscyclusgebeurtenisluisteraars {#registering-global-lifecycle-event-listeners}

Globale luisteraars kunnen worden gekoppeld aan verschillende levenscyclusgebeurtenissen, waaronder:

- **`WillEnterEvent`**: Wordt afgegeven vóór het aanhechten van een component van een route aan de DOM.
- **`DidEnterEvent`**: Wordt afgegeven nadat een component aan de DOM is gehecht.
- **`WillLeaveEvent`**: Wordt afgegeven vóór het ontkoppelen van een component van de DOM.
- **`DidLeaveEvent`**: Wordt afgegeven nadat een component van de DOM is ontkoppeld.
- **`NavigateEvent`**: Wordt afgegeven telkens wanneer er navigatie plaatsvindt.

:::tip Gebruik van Observers om in te haken op Levenscyclusgebeurtenissen
Je kunt ook inhaken op de levenscyclusgebeurtenissen met behulp van observers. Voor meer details, zie de [Lifecycle Observers](./observers).
:::

## Voorbeeld: Globale `WillLeaveEvent` luisteraar {#example-global-willleaveevent-listener}

```java
Router.getCurrent().addWillLeaveListener(event -> {
  ConfirmDialog.Result result = showConfirmDialog(
      "Weet je zeker dat je deze weergave wilt verlaten?",
      "Verlaat Weergave",
      ConfirmDialog.OptionType.OK_CANCEL,
      ConfirmDialog.MessageType.WARNING);

  event.veto(result == ConfirmDialog.Result.CANCEL);
});
```

In dit geval wordt de `WillLeaveEvent` globaal gebruikt om een bevestigingsdialoog weer te geven voordat de gebruiker naar een andere weergave navigeert.
