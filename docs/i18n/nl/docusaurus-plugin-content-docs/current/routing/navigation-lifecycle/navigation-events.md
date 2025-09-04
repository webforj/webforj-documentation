---
sidebar_position: 4
title: Navigation Events
_i18n_hash: f41ebca54f574eeac4834234cf3a0e5b
---
Naast component-specifieke lifecycle-gebeurtenissen, kunt u **globale gebeurtenisluisteraars** op routersniveau registreren. Dit maakt het mogelijk om navigatie wereldwijd over de gehele app te volgen, wat nuttig is voor logging, analytics of andere dwarsliggende zorgen.

## Voorbeeld: Globale navigatieluisteraar {#example-global-navigation-listener}

```java
Router.getCurrent().addNavigateListener(event -> {
  Location location = event.getLocation();
  console().log("Genavigeerd naar: " + location.getFullURI());
});
```

In dit voorbeeld wordt een globale luisteraar geregistreerd om elke navigatiegebeurtenis in de app te loggen. Dit is nuttig voor auditing of het volgen van paginaweergaven.

## Registreren van globale lifecycle gebeurtenisluisteraars {#registering-global-lifecycle-event-listeners}

Globale luisteraars kunnen aan verschillende lifecycle-gebeurtenissen worden gekoppeld, waaronder:

- **`WillEnterEvent`**: Wordt geactiveerd voordat een component van een route aan de DOM wordt gehecht.
- **`DidEnterEvent`**: Wordt geactiveerd nadat een component aan de DOM is gehecht.
- **`WillLeaveEvent`**: Wordt geactiveerd voordat een component van de DOM wordt losgekoppeld.
- **`DidLeaveEvent`**: Wordt geactiveerd nadat een component van de DOM is losgekoppeld.
- **`NavigateEvent`**: Wordt geactiveerd elke keer dat navigatie plaatsvindt.
- **`ActivateEvent`** (sinds 25.03): Wordt geactiveerd wanneer een gecached component opnieuw wordt geactiveerd.

:::tip Gebruik van Observers om in Lifecycle-gebeurtenissen te haken
U kunt ook haken in de lifecycle-gebeurtenissen met behulp van observers. Voor meer details, verwijzen we naar de [Lifecycle Observers](./observers).
:::

## Voorbeeld: Globale `WillLeaveEvent`-luisteraar {#example-global-willleaveevent-listener}

```java
Router.getCurrent().addWillLeaveListener(event -> {
  ConfirmDialog.Result result = showConfirmDialog(
      "Weet u zeker dat u dit scherm wilt verlaten?",
      "Verlaat Scherm",
      ConfirmDialog.OptionType.OK_CANCEL,
      ConfirmDialog.MessageType.WARNING);

  event.veto(result == ConfirmDialog.Result.CANCEL);
});
```

In dit geval wordt de `WillLeaveEvent` wereldwijd gebruikt om een bevestigingsdialoog weer te geven voordat de gebruiker navigeert weg van een scherm.
