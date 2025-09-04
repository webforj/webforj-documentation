---
sidebar_position: 4
title: Navigation Events
_i18n_hash: f41ebca54f574eeac4834234cf3a0e5b
---
Zusätzlich zu komponentenspezifischen Lebenszyklusereignissen können Sie **globale Ereignislistener** auf Routerniveau registrieren. Dies ermöglicht das Tracking der Navigation global über die gesamte App hinweg, was nützlich für Protokollierung, Analytik oder andere bereichsübergreifende Anliegen ist.

## Beispiel: Globaler Navigationslistener {#example-global-navigation-listener}

```java
Router.getCurrent().addNavigateListener(event -> {
  Location location = event.getLocation();
  console().log("Navigiert zu: " + location.getFullURI());
});
```

In diesem Beispiel wird ein globaler Listener registriert, um jedes Navigationsereignis in der App zu protokollieren. Dies ist nützlich für die Überprüfung oder das Tracking von Seitenaufrufen.

## Registrierung globaler Lebenszyklusereignislistener {#registering-global-lifecycle-event-listeners}

Globale Listener können an verschiedene Lebenszyklusereignisse angehängt werden, einschließlich:

- **`WillEnterEvent`**: Wird ausgelöst, bevor das Komponent einer Route an das DOM angehängt wird.
- **`DidEnterEvent`**: Wird ausgelöst, nachdem ein Komponent an das DOM angehängt wurde.
- **`WillLeaveEvent`**: Wird ausgelöst, bevor ein Komponent vom DOM getrennt wird.
- **`DidLeaveEvent`**: Wird ausgelöst, nachdem ein Komponent vom DOM getrennt wurde.
- **`NavigateEvent`**: Wird jedes Mal ausgelöst, wenn eine Navigation erfolgt.
- **`ActivateEvent`** (seit 25.03): Wird ausgelöst, wenn ein zwischengespeichertes Komponent reaktiviert wird.

:::tip Verwendung von Observatoren zum Hooken in Lebenszyklusereignisse
Sie können auch in die Lebenszyklusereignisse mithilfe von Observatoren hooken. Weitere Details finden Sie in den [Lifecycle Observers](./observers).
:::

## Beispiel: Globaler `WillLeaveEvent`-Listener {#example-global-willleaveevent-listener}

```java
Router.getCurrent().addWillLeaveListener(event -> {
  ConfirmDialog.Result result = showConfirmDialog(
      "Sind Sie sicher, dass Sie diese Ansicht verlassen möchten?",
      "Ansicht Verlassen",
      ConfirmDialog.OptionType.OK_CANCEL,
      ConfirmDialog.MessageType.WARNING);

  event.veto(result == ConfirmDialog.Result.CANCEL);
});
```

In diesem Fall wird das `WillLeaveEvent` global verwendet, um ein Bestätigungsdialogfeld anzuzeigen, bevor der Benutzer von einer Ansicht navigiert.
