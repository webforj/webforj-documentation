---
sidebar_position: 4
title: Navigation Events
_i18n_hash: f41ebca54f574eeac4834234cf3a0e5b
---
Además de los eventos del ciclo de vida específicos de los componentes, puedes registrar **escuchas de eventos globales** a nivel del enrutador. Esto permite el seguimiento de la navegación a nivel global a través de toda la aplicación, lo que resulta útil para el registro, la analítica u otras preocupaciones transversales.

## Ejemplo: Escucha de navegación global {#example-global-navigation-listener}

```java
Router.getCurrent().addNavigateListener(event -> {
  Location location = event.getLocation();
  console().log("Navegado a: " + location.getFullURI());
});
```

En este ejemplo, se registra un escuchador global para registrar cada evento de navegación en la aplicación. Esto es útil para auditorías o para rastrear vistas de página.

## Registro de escuchas de eventos del ciclo de vida globales {#registering-global-lifecycle-event-listeners}

Los escuchadores globales se pueden adjuntar a varios eventos del ciclo de vida, incluyendo:

- **`WillEnterEvent`**: Se dispara antes de que se adjunte el componente de cualquier ruta al DOM.
- **`DidEnterEvent`**: Se dispara después de que un componente se adjunta al DOM.
- **`WillLeaveEvent`**: Se dispara antes de que un componente se desadjunte del DOM.
- **`DidLeaveEvent`**: Se dispara después de que un componente se desadjunta del DOM.
- **`NavigateEvent`**: Se dispara cada vez que ocurre una navegación.
- **`ActivateEvent`** (desde 25.03): Se dispara cuando se reactiva un componente en caché.

:::tip Uso de Observadores para Engancharse a Eventos del Ciclo de Vida
También puedes engancharte a los eventos del ciclo de vida utilizando observadores. Para más detalles, consulta los [Observadores del Ciclo de Vida](./observers).
:::

## Ejemplo: Escucha global de `WillLeaveEvent` {#example-global-willleaveevent-listener}

```java
Router.getCurrent().addWillLeaveListener(event -> {
  ConfirmDialog.Result result = showConfirmDialog(
      "¿Estás seguro de que deseas salir de esta vista?",
      "Salir de la Vista",
      ConfirmDialog.OptionType.OK_CANCEL,
      ConfirmDialog.MessageType.WARNING);

  event.veto(result == ConfirmDialog.Result.CANCEL);
});
```

En este caso, el `WillLeaveEvent` se utiliza globalmente para mostrar un cuadro de diálogo de confirmación antes de que el usuario navegue fuera de cualquier vista.
