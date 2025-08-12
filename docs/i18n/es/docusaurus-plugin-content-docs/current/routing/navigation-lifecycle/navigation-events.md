---
sidebar_position: 4
title: Navigation Events
_i18n_hash: efd06f0c5d04fb782fc27b187d1b063d
---
Además de los eventos de ciclo de vida específicos de los componentes, puedes registrar **escuchadores de eventos globales** a nivel de enrutador. Esto permite rastrear la navegación de manera global en toda la aplicación, lo que resulta útil para el registro, análisis u otras preocupaciones transversales.

## Ejemplo: Escuchador de navegación global {#example-global-navigation-listener}

```java
Router.getCurrent().addNavigateListener(event -> {
  Location location = event.getLocation();
  console().log("Navegado a: " + location.getFullURI());
});
```

En este ejemplo, se registra un escuchador global para registrar cada evento de navegación en la aplicación. Esto es útil para auditorías o para rastrear vistas de página.

## Registro de escuchadores de eventos de ciclo de vida globales {#registering-global-lifecycle-event-listeners}

Los escuchadores globales se pueden adjuntar a varios eventos de ciclo de vida, incluyendo:

- **`WillEnterEvent`**: Se dispara antes de que se adjunte el componente de cualquier ruta al DOM.
- **`DidEnterEvent`**: Se dispara después de que se adjunta un componente al DOM.
- **`WillLeaveEvent`**: Se dispara antes de que se desadjunte un componente del DOM.
- **`DidLeaveEvent`**: Se dispara después de que se desadjunta un componente del DOM.
- **`NavigateEvent`**: Se dispara cada vez que ocurre una navegación.

:::tip Uso de Observadores para Engancharse a Eventos de Ciclo de Vida
También puedes engancharte a los eventos de ciclo de vida utilizando observadores. Para más detalles, consulta los [Observadores de Ciclo de Vida](./observers).
:::

## Ejemplo: Escuchador global `WillLeaveEvent` {#example-global-willleaveevent-listener}

```java
Router.getCurrent().addWillLeaveListener(event -> {
  ConfirmDialog.Result result = showConfirmDialog(
      "¿Estás seguro de que quieres dejar esta vista?",
      "Dejar Vista",
      ConfirmDialog.OptionType.OK_CANCEL,
      ConfirmDialog.MessageType.WARNING);

  event.veto(result == ConfirmDialog.Result.CANCEL);
});
```

En este caso, el `WillLeaveEvent` se utiliza globalmente para mostrar un cuadro de diálogo de confirmación antes de que el usuario navegue fuera de cualquier vista.
