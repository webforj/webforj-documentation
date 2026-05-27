---
sidebar_position: 35
sidebar_class_name: new-content
title: Geolocation
description: Request and watch the device's geographic position using the Geolocation class, with high accuracy, timeout, and maximum age controls.
---

# Geolocation <DocChip chip='since' label='26.01' />

The <JavadocLink type="foundation" location="com/webforj/geolocation/Geolocation" code='true'>Geolocation</JavadocLink> class provides an interface to the browser's geolocation subsystem. Use it to request the device's current position once, or to watch the position for continuous updates.

<!-- INTRO_END -->

## Setup and prerequisites {#setup-and-prerequisites}

The Geolocation API requires:

- A **secure context** (HTTPS). The `localhost` origin is exempt and works over HTTP for local development.
- User permission for location access. The browser prompts automatically the first time a position is requested and the choice is persisted per origin.

When the subsystem isn't available, accessing it throws a `WebforjRuntimeException`.

## Instance {#instance}

Get the geolocation instance for the current environment:

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

## Requesting a position {#requesting-a-position}

Call `getCurrentPosition()` to request the device's current geographic position. The returned <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink> completes with the reported <JavadocLink type="foundation" location="com/webforj/geolocation/GeolocationPosition" code='true'>GeolocationPosition</JavadocLink>, or exceptionally with a <JavadocLink type="foundation" location="com/webforj/geolocation/exception/WebforjGeolocationException" code='true'>WebforjGeolocationException</JavadocLink> when the browser is unable to obtain a position.

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

:::info Browser permission
The browser may prompt the user for permission the first time a position is requested. The prompt is shown by the browser itself and isn't part of the app UI.
:::

## Watching the position {#watching-the-position}

Register a watch listener to receive a stream of position updates as the device moves.

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

// Later, to stop watching:
registration.remove();
```

A <JavadocLink type="foundation" location="com/webforj/geolocation/event/GeolocationWatchEvent" code='true'>GeolocationWatchEvent</JavadocLink> fires for every update, successful or not. Check `isSuccess()` before reading the position.

## Configuring requests {#configuring-requests}

Three setters configure subsequent requests for geolocation information.

### High accuracy {#high-accuracy}

```java
Geolocation.getCurrent().useHighAccuracy(true);
```

The high accuracy attribute provides a hint that the app would like to receive the best possible results. This may result in slower response times or increased power consumption. The user might also deny this capability, or the device might not be able to provide more accurate results than if the flag wasn't specified. The intended purpose of this attribute is to allow apps to inform the browser that they don't require high accuracy geolocation fixes and, therefore, the browser can avoid using geolocation providers that consume a significant amount of power. This is especially useful for apps running on battery powered devices, such as mobile phones.

### Timeout {#timeout}

```java
Geolocation.getCurrent().useTimeout(10.0);
```

The timeout attribute denotes the maximum length of time, expressed in seconds, that's allowed to pass from the call to `getCurrentPosition()` or a watch listener until the browser returns a position. If the browser is unable to successfully acquire a new position before the given timeout elapses, and no other errors have occurred in this interval, the request is reported with the `TIMEOUT` status. The time that's spent obtaining the user permission isn't included in the period covered by the timeout attribute. The timeout attribute only applies to the location acquisition operation.

### Maximum age {#maximum-age}

```java
Geolocation.getCurrent().useMaximumAge(60.0);
```

The maximum age attribute indicates that the app is willing to accept a cached position whose age is no greater than the specified time in seconds. If maximum age is set to `0`, the browser must immediately attempt to acquire a new position. If the browser doesn't have a cached position available whose age is no greater than the specified maximum age, then it must acquire a new position. In case of a watch, the maximum age refers to the first position returned.

## Failure status {#failure-status}

For a one shot request the failure is delivered as a <JavadocLink type="foundation" location="com/webforj/geolocation/exception/WebforjGeolocationException" code='true'>WebforjGeolocationException</JavadocLink> on the `PendingResult`. For a watch update the failure is delivered as a `GeolocationWatchEvent` with `isSuccess()` returning `false`. The outcome is reported through <JavadocLink type="foundation" location="com/webforj/geolocation/GeolocationStatus" code='true'>GeolocationStatus</JavadocLink>.

## Complete example {#complete-example}

The view below renders three labeled rows for latitude, longitude, and altitude, plus a button that requests the current position. The result is written into the rows. Status is announced through a single reused toast.

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
  private final Button getPositionButton = new Button("Get Position", ButtonTheme.PRIMARY);
  private Toast currentToast;

  public MainView() {
    self.setDirection(FlexDirection.COLUMN).setSpacing("1em");
    self.setMaxWidth("24em").setMargin("4em auto");

    self.add(
        new H2("Geolocation"),
        row("Latitude", latitudeValue),
        row("Longitude", longitudeValue),
        row("Altitude", altitudeValue),
        getPositionButton);

    getPositionButton.onClick(ev -> requestPosition());
  }

  private void requestPosition() {
    Geolocation geo = Geolocation.getCurrent();
    geo.useHighAccuracy(true)
        .useTimeout(10.0)
        .useMaximumAge(0.0);

    showToast("Requesting position…", Theme.INFO);
    resetRows();

    PendingResult<GeolocationPosition> request = geo.getCurrentPosition();
    request.thenAccept(position -> {
      latitudeValue.setText(formatDegrees(position.getLatitude()));
      longitudeValue.setText(formatDegrees(position.getLongitude()));
      altitudeValue.setText(position.getAltitude()
          .map(alt -> String.format(Locale.ROOT, "%.0f m", alt))
          .orElse("—"));
      showToast("Position acquired", Theme.SUCCESS);
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
