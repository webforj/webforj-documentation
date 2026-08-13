---
title: Page Visibility
sidebar_position: 32
sidebar_class_name: new-content
description: >-
  Detect when the tab hosting your app moves between the foreground and the
  background, and react in Java.
_i18n_hash: 8382d0314f6143663c03e11409de08d5
---
<DocChip chip='since' label='26.01' />
<JavadocLink type="foundation" location="com/webforj/Page" top='true'/>

La clase `Page` puede determinar cuándo el usuario ha cambiado de pestaña, minimizado la ventana o regresado. Úsala para pausar el polling y las animaciones cuando nadie está mirando, restringir notificaciones o refrescar datos obsoletos cuando la pestaña recupera el enfoque.

La API tiene dos componentes:

- Una consulta tipada, `getVisibilityState()`, que devuelve el estado actual.
- Un listener, `addVisibilityChangeListener(...)`, que se activa cada vez que cambia el estado.

## Estados de visibilidad {#visibility-states}

`PageVisibilityState` tiene dos valores:

| Estado | Significado |
| --- | --- |
| `VISIBLE` | El contenido de la página es al menos parcialmente visible. La pestaña está en primer plano de una ventana no minimizada. |
| `HIDDEN` | El contenido de la página no es visible para el usuario. La pestaña está en segundo plano, la ventana está minimizada, la pantalla está bloqueada o el sistema operativo está mostrando un salvapantallas. |

## Leyendo el estado actual {#reading-the-current-state}

`Page.getVisibilityState()` devuelve un `PendingResult<PageVisibilityState>` que se resuelve con el estado actual.

```java
Page.getCurrent().getVisibilityState().thenAccept(state -> {
  if (state == PageVisibilityState.VISIBLE) {
    // el usuario está viendo la pestaña
  }
});
```

Llama a esto cuando necesites una respuesta única, por ejemplo, cuando una tarea programada se activa. Para reacciones continuas, registra un listener en su lugar.

## Escuchando cambios {#listening-for-changes}

`addVisibilityChangeListener(...)` registra un listener que se notifica cada vez que cambia el estado de visibilidad. El alias correspondiente es `onVisibilityChange(...)`.

```java
ListenerRegistration<PageVisibilityChangeEvent> registration =
    Page.getCurrent().onVisibilityChange(event -> {
      if (event.isHidden()) {
        pauseRendering();
      } else {
        resumeRendering();
      }
    });
```

El evento trae el nuevo estado y algunos accesores de conveniencia:

| Método | Retorna |
| --- | --- |
| `getState()` | El nuevo `PageVisibilityState`. |
| `isVisible()` | `true` cuando el nuevo estado es `VISIBLE`. |
| `isHidden()` | `true` cuando el nuevo estado es `HIDDEN`. |
| `getPage()` | La `Page` que generó el evento. |

Elimina un solo listener con el `ListenerRegistration` devuelto.

## Ejemplo: notificar solo cuando la pestaña está oculta {#example-notify-when-hidden}

Un caso de uso común es elegir el canal de entrega en función de si el usuario está viendo actualmente la pestaña. El fragmento a continuación programa una notificación cinco segundos en el futuro. Si la pestaña está oculta cuando el temporizador se activa, genera una notificación de escritorio y dibuja una insignia en el favicon. Si la pestaña es visible, muestra un toast en la aplicación.

```java
@Route("/")
public class NotifyView extends Composite<FlexLayout> {

  private final FlexLayout self = getBoundComponent();
  private final Button notifyButton = new Button("Notificar en 5 segundos");
  private final Debouncer schedule = new Debouncer(5.0f);

  private ListenerRegistration<PageVisibilityChangeEvent> visibilityRegistration;
  private DesktopNotification activeNotification;

  public NotifyView() {
    self.setDirection(FlexDirection.COLUMN)
        .setAlignment(FlexAlignment.CENTER);

    H1 title = new H1("Demostración de Visibilidad de Página");
    Paragraph hint = new Paragraph(
        "Haz clic en el botón, luego cambia a otra pestaña antes de que termine el temporizador.");

    notifyButton.setPrefixComponent(FeatherIcon.BELL.create())
        .setTheme(ButtonTheme.PRIMARY)
        .onClick(e -> schedule.run(this::deliver));

    self.add(title, hint, notifyButton);

    visibilityRegistration = Page.getCurrent().onVisibilityChange(this::onVisibility);
  }

  private void deliver() {
    Page page = Page.getCurrent();
    page.getVisibilityState().thenAccept(state -> {
      if (state == PageVisibilityState.HIDDEN) {
        page.setIconBadge(1);
        activeNotification = new DesktopNotification("Demostración de Visibilidad de Página",
            "El temporizador se activó mientras la pestaña estaba oculta.");
        activeNotification.open();
      } else {
        Toast.show("Temporizador activado mientras la pestaña era visible.", Theme.SUCCESS);
      }
    });
  }

  private void onVisibility(PageVisibilityChangeEvent event) {
    if (event.isVisible() && activeNotification != null) {
      Page.getCurrent().setIconBadge(0);
      activeNotification.close();
      activeNotification = null;
    }
  }

  @Override
  protected void onDidDestroy() {
    schedule.cancel();
    if (visibilityRegistration != null) {
      visibilityRegistration.remove();
    }
  }
}
```

El listener de visibilidad elimina la insignia del favicon y cierra la notificación de escritorio cuando el usuario regresa a la pestaña.

## Cuándo usarlo {#when-to-use-it}

- **Pausar trabajo en segundo plano.** Detén el polling, intervalos y animaciones cuando la página esté oculta para ahorrar ancho de banda y CPU. Reanúdalos cuando se vuelva a visible.
- **Restringir notificaciones.** Muestra un `Toast` cuando el usuario puede ver la pestaña y una `DesktopNotification` cuando no puede.
- **Refrescar datos obsoletos al regresar.** Cuando la página vuelve de `HIDDEN`, decide si ha pasado suficiente tiempo para volver a obtener datos.
- **Rastrear el compromiso.** Marca una sesión como inactiva mientras la pestaña está oculta.
