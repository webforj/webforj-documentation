---
sidebar_position: 4
title: Navigation Events
_i18n_hash: d7beed9a9d607e1decc18fa24417b213
---
Zusätzlich zu komponentenspezifischen Lebenszyklusereignissen können Sie **globale Ereignislistener** auf Router-Ebene registrieren. Dies ermöglicht die globale Verfolgung der Navigation in der gesamten Anwendung, was nützlich für Protokollierung, Analytik oder andere übergreifende Anliegen ist.

## Beispiel: Globaler Navigation-Listener {#example-global-navigation-listener}

```java
Router.getCurrent().addNavigateListener(event -> {
  Location location = event.getLocation();
  console().log("Navigiert zu: " + location.getFullURI());
});
```

In diesem Beispiel wird ein globaler Listener registriert, um jedes Navigationsereignis in der App zu protokollieren. Dies ist nützlich für die Prüfung oder Verfolgung von Seitenaufrufen.

## Registrierung globaler Lebenszyklusereignis-Listener {#registering-global-lifecycle-event-listeners}

Globale Listener können an verschiedene Lebenszyklusereignisse angehängt werden, einschließlich:

- **`WillEnterEvent`**: Wird ausgelöst, bevor die Komponente einer Route an das DOM angehängt wird.
- **`DidEnterEvent`**: Wird ausgelöst, nachdem eine Komponente an das DOM angehängt wurde.
- **`WillLeaveEvent`**: Wird ausgelöst, bevor eine Komponente vom DOM abgetrennt wird.
- **`DidLeaveEvent`**: Wird ausgelöst, nachdem eine Komponente vom DOM abgetrennt wurde.
- **`NavigateEvent`**: Wird jedes Mal ausgelöst, wenn eine Navigation stattfindet.

:::tip Verwendung von Beobachtern zum Hook in Lebenszyklusereignisse
Sie können auch in die Lebenszyklusereignisse mit Beobachtern eingreifen. Für weitere Details siehe die [Lifecycle Observers](./observers).
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

In diesem Fall wird das `WillLeaveEvent` global verwendet, um ein Bestätigungsdialogfeld anzuzeigen, bevor der Benutzer von einer Ansicht navigiert.
