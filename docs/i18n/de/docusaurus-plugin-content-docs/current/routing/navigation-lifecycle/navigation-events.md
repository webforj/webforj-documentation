---
sidebar_position: 4
title: Navigation Events
_i18n_hash: efd06f0c5d04fb782fc27b187d1b063d
---
Neben komponentenspezifischen Lebenszyklusereignissen können Sie **globale Ereignislistener** auf Router-Ebene registrieren. Dies ermöglicht die globale Verfolgung der Navigation in der gesamten App, was nützlich für Protokollierung, Analytik oder andere bereichsübergreifende Belange ist.

## Beispiel: Globaler Navigationslistener {#example-global-navigation-listener}

```java
Router.getCurrent().addNavigateListener(event -> {
  Location location = event.getLocation();
  console().log("Navigiert zu: " + location.getFullURI());
});
```

In diesem Beispiel wird ein globaler Listener registriert, um jedes Navigationsereignis in der App zu protokollieren. Dies ist nützlich für Audits oder zur Verfolgung von Seitenaufrufen.

## Registrieren globaler Lebenszyklusereignis-Listener {#registering-global-lifecycle-event-listeners}

Globale Listener können verschiedenen Lebenszyklusereignissen zugeordnet werden, einschließlich:

- **`WillEnterEvent`**: Wird ausgelöst, bevor eine Komponente einer Route an das DOM angehängt wird.
- **`DidEnterEvent`**: Wird ausgelöst, nachdem eine Komponente an das DOM angehängt wurde.
- **`WillLeaveEvent`**: Wird ausgelöst, bevor eine Komponente vom DOM abgetrennt wird.
- **`DidLeaveEvent`**: Wird ausgelöst, nachdem eine Komponente vom DOM abgetrennt wurde.
- **`NavigateEvent`**: Wird jedes Mal ausgelöst, wenn eine Navigation erfolgt.

:::tip Verwendung von Beobachtern zum Haken in Lebenszyklusereignisse
Sie können auch die Lebenszyklusereignisse über Beobachter hooken. Weitere Details finden Sie in den [Lifecycle Observers](./observers).
:::

## Beispiel: Globaler `WillLeaveEvent`-Listener {#example-global-willleaveevent-listener}

```java
Router.getCurrent().addWillLeaveListener(event -> {
  ConfirmDialog.Result result = showConfirmDialog(
      "Sind Sie sicher, dass Sie diese Ansicht verlassen möchten?",
      "Ansicht verlassen",
      ConfirmDialog.OptionType.OK_CANCEL,
      ConfirmDialog.MessageType.WARNING);

  event.veto(result == ConfirmDialog.Result.CANCEL);
});
```

In diesem Fall wird das `WillLeaveEvent` global verwendet, um einen Bestätigungsdialog anzuzeigen, bevor der Benutzer von einer Ansicht navigiert.
