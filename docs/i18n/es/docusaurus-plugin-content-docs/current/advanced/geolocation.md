---
sidebar_position: 35
sidebar_class_name: new-content
title: Geolocation
description: >-
  Request and watch the device's geographic position using the Geolocation
  class, with high-accuracy, timeout, and maximum age controls.
_i18n_hash: 68083cf323f26b69a62bc3147145f4d2
---
# Geolocalización <DocChip chip='since' label='26.01' />

La clase <JavadocLink type="foundation" location="com/webforj/geolocation/Geolocation" code='true'>Geolocalización</JavadocLink> proporciona una interfaz al subsistema de geolocalización del navegador. Utilízala para solicitar la posición actual del dispositivo una vez, o para observar la posición para actualizaciones continuas.

<!-- INTRO_END -->

## Configuración y requisitos previos {#setup-and-prerequisites}

La API de Geolocalización requiere:

- Un **contexto seguro** (HTTPS). El origen `localhost` está exento y funciona sobre HTTP para el desarrollo local.
- Permiso del usuario para el acceso a la ubicación. El navegador solicita automáticamente permiso la primera vez que se solicita una posición y la elección se mantiene por origen.

Cuando el subsistema no está disponible, acceder a él arroja una `WebforjRuntimeException`.

## Instancia {#instance}

Obtén la instancia de geolocalización para el entorno actual:

```java
import com.webforj.geolocation.Geolocation;

Geolocation geo = Geolocation.getCurrent();

if (Geolocation.isPresent()) {
  // ...
}

Geolocation.ifPresent(g -> {
  // ...
});
```

## Solicitar una posición {#requesting-a-position}

Llama a `getCurrentPosition()` para solicitar la posición geográfica actual del dispositivo. El <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink> devuelto se completa con la <JavadocLink type="foundation" location="com/webforj/geolocation/GeolocationPosition" code='true'>GeolocationPosition</JavadocLink> informada, o excepcionalmente con una <JavadocLink type="foundation" location="com/webforj/geolocation/exception/WebforjGeolocationException" code='true'>WebforjGeolocationException</JavadocLink> cuando el navegador no puede obtener una posición.

```java
PendingResult<GeolocationPosition> request = Geolocation.getCurrent().getCurrentPosition();
request.thenAccept(position -> {
  double lat = position.getLatitude();
  double lng = position.getLongitude();
  double accuracy = position.getAccuracy();
});
request.exceptionally(throwable -> {
  WebforjGeolocationException error = (WebforjGeolocationException) throwable;
  GeolocationStatus status = error.getStatus();
  String message = error.getMessage();

  return null;
});
```

:::info Permiso del navegador
El navegador puede solicitar al usuario permiso la primera vez que se solicita una posición. El aviso es mostrado por el propio navegador y no forma parte de la interfaz de usuario de la aplicación.
:::

## Observando la posición {#watching-the-position}

Registra un oyente de observación para recibir un flujo de actualizaciones de posición a medida que se mueve el dispositivo.

```java
ListenerRegistration<GeolocationWatchEvent> registration =
    Geolocation.getCurrent().onWatch(event -> {
      if (event.isSuccess()) {
        GeolocationPosition position = event.getPosition().orElseThrow();
        // ...
      } else {
        GeolocationStatus status = event.getStatus();
        String message = event.getMessage().orElse("");
      }
    });

// Más tarde, para dejar de observar:
registration.remove();
```

Un <JavadocLink type="foundation" location="com/webforj/geolocation/event/GeolocationWatchEvent" code='true'>GeolocationWatchEvent</JavadocLink> se dispara para cada actualización, exitosa o no. Verifica `isSuccess()` antes de leer la posición.

## Configurando solicitudes {#configuring-requests}

Tres configuradores configuran solicitudes subsecuentes para información de geolocalización.

### Alta precisión {#high-accuracy}

```java
Geolocation.getCurrent().useHighAccuracy(true);
```

El atributo de alta precisión proporciona una indicación de que la aplicación desea recibir los mejores resultados posibles. Esto puede resultar en tiempos de respuesta más lentos o un mayor consumo de energía. El usuario también puede denegar esta capacidad, o el dispositivo puede no ser capaz de proporcionar resultados más precisos que si la bandera no se hubiera especificado. El propósito de este atributo es permitir que las aplicaciones informen al navegador que no requieren fijaciones de geolocalización de alta precisión y, por lo tanto, el navegador puede evitar utilizar proveedores de geolocalización que consumen una cantidad significativa de energía. Esto es especialmente útil para aplicaciones que se ejecutan en dispositivos alimentados por batería, como teléfonos móviles.

### Tiempo de espera {#timeout}

```java
Geolocation.getCurrent().useTimeout(10.0);
```

El atributo de tiempo de espera denota la longitud máxima de tiempo, expresada en segundos, que se permite que transcurra desde la llamada a `getCurrentPosition()` o un oyente de observación hasta que el navegador devuelva una posición. Si el navegador no puede adquirir con éxito una nueva posición antes de que transcurra el tiempo de espera dado, y no se han producido otros errores en este intervalo, la solicitud se informa con el estado `TIMEOUT`. El tiempo que se gasta en obtener el permiso del usuario no se incluye en el período cubierto por el atributo de tiempo de espera. El atributo de tiempo de espera solo se aplica a la operación de adquisición de ubicación.

### Edad máxima {#maximum-age}

```java
Geolocation.getCurrent().useMaximumAge(60.0);
```

El atributo de edad máxima indica que la aplicación está dispuesta a aceptar una posición en caché cuya edad no sea mayor que el tiempo especificado en segundos. Si la edad máxima se establece en `0`, el navegador debe intentar adquirir inmediatamente una nueva posición. Si el navegador no tiene una posición en caché disponible cuya edad no sea mayor que la edad máxima especificada, entonces debe adquirir una nueva posición. En el caso de una observación, la edad máxima se refiere a la primera posición devuelta.

## Estado de falla {#failure-status}

Para una solicitud única, la falla se entrega como una <JavadocLink type="foundation" location="com/webforj/geolocation/exception/WebforjGeolocationException" code='true'>WebforjGeolocationException</JavadocLink> en el `PendingResult`. Para una actualización de observación, la falla se entrega como un `GeolocationWatchEvent` con `isSuccess()` devolviendo `false`. El resultado se informa a través de <JavadocLink type="foundation" location="com/webforj/geolocation/GeolocationStatus" code='true'>GeolocationStatus</JavadocLink>.

## Ejemplo completo {#complete-example}

La vista a continuación renderiza tres filas etiquetadas para latitud, longitud y altitud, más un botón que solicita la posición actual. El resultado se escribe en las filas. El estado se anuncia a través de un único toast reutilizado.

```java
package com.example;

import com.webforj.PendingResult;
import com.webforj.component.Composite;
import com.webforj.component.Theme;
import com.webforj.component.button.Button;
import com.webforj.component.button.ButtonTheme;
import com.webforj.component.html.elements.H2;
import com.webforj.component.html.elements.Span;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.toast.Toast;
import com.webforj.geolocation.Geolocation;
import com.webforj.geolocation.GeolocationPosition;
import com.webforj.geolocation.exception.WebforjGeolocationException;
import com.webforj.router.annotation.Route;
import java.util.Locale;

@Route("/")
public class MainView extends Composite<FlexLayout> {

  private final FlexLayout self = getBoundComponent();

  private final Span latitudeValue = new Span("—");
  private final Span longitudeValue = new Span("—");
  private final Span altitudeValue = new Span("—");
  private final Button getPositionButton = new Button("Obtener posición", ButtonTheme.PRIMARY);
  private Toast currentToast;

  public MainView() {
    self.setDirection(FlexDirection.COLUMN).setSpacing("1em");
    self.setMaxWidth("24em").setMargin("4em auto");

    self.add(
        new H2("Geolocalización"),
        row("Latitud", latitudeValue),
        row("Longitud", longitudeValue),
        row("Altitud", altitudeValue),
        getPositionButton);

    getPositionButton.onClick(ev -> requestPosition());
  }

  private void requestPosition() {
    Geolocation geo = Geolocation.getCurrent();
    geo.useHighAccuracy(true)
        .useTimeout(10.0)
        .useMaximumAge(0.0);

    showToast("Solicitando posición…", Theme.INFO);
    resetRows();

    PendingResult<GeolocationPosition> request = geo.getCurrentPosition();
    request.thenAccept(position -> {
      latitudeValue.setText(formatDegrees(position.getLatitude()));
      longitudeValue.setText(formatDegrees(position.getLongitude()));
      altitudeValue.setText(position.getAltitude()
          .map(alt -> String.format(Locale.ROOT, "%.0f m", alt))
          .orElse("—"));
      showToast("Posición adquirida", Theme.SUCCESS);
    });
    request.exceptionally(throwable -> {
      WebforjGeolocationException error = (WebforjGeolocationException) throwable;
      showToast(error.getStatus() + ": " + error.getMessage(), Theme.DANGER);

      return null;
    });
  }

  private void showToast(String text, Theme theme) {
    if (currentToast != null) {
      currentToast.close();
    }
    currentToast = Toast.show(text, theme);
  }

  private void resetRows() {
    latitudeValue.setText("—");
    longitudeValue.setText("—");
    altitudeValue.setText("—");
  }

  private static String formatDegrees(double degrees) {
    return String.format(Locale.ROOT, "%.6f", degrees);
  }

  private static FlexLayout row(String label, Span value) {
    Span labelSpan = new Span(label);
    labelSpan.setMinWidth("6em");

    return new FlexLayout(labelSpan, value);
  }
}
```
