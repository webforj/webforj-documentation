---
sidebar_position: 4
title: Navigation Events
_i18n_hash: d7beed9a9d607e1decc18fa24417b213
---
Además de los eventos del ciclo de vida específicos de los componentes, puedes registrar **oyentes de eventos globales** a nivel de enrutador. Esto permite rastrear la navegación globalmente a través de toda la aplicación, lo que resulta útil para el registro, análisis u otras preocupaciones transversales.

## Ejemplo: Oyente de navegación global {#example-global-navigation-listener}

```java
Router.getCurrent().addNavigateListener(event -> {
  Location location = event.getLocation();
  console().log("Navegó a: " + location.getFullURI());
});
```

En este ejemplo, se registra un oyente global para registrar cada evento de navegación en la aplicación. Esto es útil para auditar o rastrear vistas de página.

## Registro de oyentes de eventos del ciclo de vida globales {#registering-global-lifecycle-event-listeners}

Los oyentes globales se pueden adjuntar a varios eventos del ciclo de vida, que incluyen:

- **`WillEnterEvent`**: Se dispara antes de que se adjunte el componente de cualquier ruta al DOM.
- **`DidEnterEvent`**: Se dispara después de que un componente se adjunta al DOM.
- **`WillLeaveEvent`**: Se dispara antes de que un componente se desadjunte del DOM.
- **`DidLeaveEvent`**: Se dispara después de que un componente se desadjunta del DOM.
- **`NavigateEvent`**: Se dispara cada vez que ocurre una navegación.

:::tip Uso de Observadores para Engancharse a Eventos del Ciclo de Vida
También puedes engancharte a los eventos del ciclo de vida utilizando observadores. Para más detalles, consulta los [Observadores del Ciclo de Vida](./observers).
:::

## Ejemplo: Oyente global `WillLeaveEvent` {#example-global-willleaveevent-listener}

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
