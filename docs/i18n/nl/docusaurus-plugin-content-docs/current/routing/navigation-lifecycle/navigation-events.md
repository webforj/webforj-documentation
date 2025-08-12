---
sidebar_position: 4
title: Navigation Events
_i18n_hash: efd06f0c5d04fb782fc27b187d1b063d
---
Naast component-specifieke levenscyclusgebeurtenissen, kun je **globale gebeurtenisluisteraars** op routerniveau registreren. Dit maakt het mogelijk om navigatie wereldwijd in de hele app te volgen, wat nuttig is voor logging, analytics of andere dwarsdoorsnijdende zorgen.

## Voorbeeld: Globale navigatieluisteraar {#example-global-navigation-listener}

```java
Router.getCurrent().addNavigateListener(event -> {
  Location location = event.getLocation();
  console().log("Genavigeerd naar: " + location.getFullURI());
});
```

In dit voorbeeld wordt een globale luisteraar geregistreerd om elk navigatie-evenement in de app te loggen. Dit is nuttig voor auditing of het volgen van paginabezoeken.

## Registreren van globale levenscyclusgebeurtenisluisteraars {#registering-global-lifecycle-event-listeners}

Globale luisteraars kunnen worden gekoppeld aan verschillende levenscyclusgebeurtenissen, waaronder:

- **`WillEnterEvent`**: Wordt geactiveerd voordat de component van een route aan de DOM wordt gekoppeld.
- **`DidEnterEvent`**: Wordt geactiveerd nadat een component aan de DOM is gekoppeld.
- **`WillLeaveEvent`**: Wordt geactiveerd voordat een component van de DOM wordt losgekoppeld.
- **`DidLeaveEvent`**: Wordt geactiveerd nadat een component van de DOM is losgekoppeld.
- **`NavigateEvent`**: Wordt geactiveerd telkens wanneer navigatie plaatsvindt.

:::tip Gebruik van Observers om in Levenscyclusgebeurtenissen in te haken
Je kunt ook in de levenscyclusgebeurtenissen inhaken met behulp van observers. Voor meer details, zie de [Lifecycle Observers](./observers).
:::

## Voorbeeld: Globale `WillLeaveEvent` luisteraar {#example-global-willleaveevent-listener}

```java
Router.getCurrent().addWillLeaveListener(event -> {
  ConfirmDialog.Result result = showConfirmDialog(
      "Weet je zeker dat je deze weergave wilt verlaten?",
      "Verlaat weergave",
      ConfirmDialog.OptionType.OK_CANCEL,
      ConfirmDialog.MessageType.WARNING);

  event.veto(result == ConfirmDialog.Result.CANCEL);
});
```

In dit geval wordt de `WillLeaveEvent` globaal gebruikt om een bevestigingsdialoog weer te geven voordat de gebruiker naar een andere weergave navigeert.
