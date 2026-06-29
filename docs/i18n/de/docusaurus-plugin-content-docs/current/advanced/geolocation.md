---
sidebar_position: 35
sidebar_class_name: new-content
title: Geolocation
description: >-
  Request and watch the device's geographic position using the Geolocation
  class, with high-accuracy, timeout, and maximum age controls.
_i18n_hash: 68083cf323f26b69a62bc3147145f4d2
---
# Geolokalisierung <DocChip chip='since' label='26.01' />

Die <JavadocLink type="foundation" location="com/webforj/geolocation/Geolocation" code='true'>Geolocation</JavadocLink>-Klasse bietet eine Schnittstelle zum Geolocation-Subsystem des Browsers. Verwenden Sie sie, um die aktuelle Position des Geräts einmalig anzufordern oder um die Position für kontinuierliche Updates zu überwachen.

<!-- INTRO_END -->

## Einrichtung und Voraussetzungen {#setup-and-prerequisites}

Die Geolocation-API erfordert:

- Ein **sicherer Kontext** (HTTPS). Der `localhost`-Ursprung ist ausgenommen und funktioniert über HTTP für die lokale Entwicklung.
- Benutzerberechtigung für den Standortzugriff. Der Browser fordert beim ersten Mal automatisch zur Erlaubnis auf, wenn eine Position angefordert wird, und die Wahl wird pro Ursprung gespeichert.

Wenn das Subsystem nicht verfügbar ist, wird beim Zugriff darauf eine `WebforjRuntimeException` ausgelöst.

## Instanz {#instance}

Holen Sie sich die Geolocation-Instanz für die aktuelle Umgebung:

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

## Anfordern einer Position {#requesting-a-position}

Rufen Sie `getCurrentPosition()` auf, um die aktuelle geografische Position des Geräts anzufordern. Das zurückgegebene <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink> wird mit der gemeldeten <JavadocLink type="foundation" location="com/webforj/geolocation/GeolocationPosition" code='true'>GeolocationPosition</JavadocLink> abgeschlossen oder außergewöhnlich mit einer <JavadocLink type="foundation" location="com/webforj/geolocation/exception/WebforjGeolocationException" code='true'>WebforjGeolocationException</JavadocLink>, wenn der Browser nicht in der Lage ist, eine Position zu erwerben.

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

:::info Browserberechtigung
Der Browser kann den Benutzer beim ersten Mal zur Erlaubnis auffordern, wenn eine Position angefordert wird. Die Aufforderung wird vom Browser selbst angezeigt und ist nicht Teil der App-UI.
:::

## Überwachen der Position {#watching-the-position}

Registrieren Sie einen Watch-Listener, um einen Stream von Positionsupdates zu erhalten, während sich das Gerät bewegt.

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

// Später, um das Überwachen zu stoppen:
registration.remove();
```

Ein <JavadocLink type="foundation" location="com/webforj/geolocation/event/GeolocationWatchEvent" code='true'>GeolocationWatchEvent</JavadocLink> wird bei jedem Update, erfolgreich oder nicht, ausgelöst. Überprüfen Sie `isSuccess()`, bevor Sie die Position lesen.

## Anpassen von Anfragen {#configuring-requests}

Drei Setzer konfigurieren nachfolgende Anfragen nach Geolocation-Informationen.

### Hohe Genauigkeit {#high-accuracy}

```java
Geolocation.getCurrent().useHighAccuracy(true);
```

Das Attribut hohe Genauigkeit gibt einen Hinweis darauf, dass die App die bestmöglichen Ergebnisse erhalten möchte. Dies kann zu langsameren Antwortzeiten oder einem erhöhten Stromverbrauch führen. Der Benutzer könnte diese Fähigkeit auch ablehnen, oder das Gerät könnte nicht in der Lage sein, genauere Ergebnisse als ohne den angegebenen Flag zu liefern. Der Zweck dieses Attributs besteht darin, Apps zu ermöglichen, dem Browser mitzuteilen, dass sie keine hochgenauen Geolocation-Fixes benötigen und der Browser daher geolocation-Anbieter vermeiden kann, die einen erheblichen Stromverbrauch verursachen. Dies ist besonders nützlich für Apps, die auf batteriebetriebenen Geräten wie Mobiltelefonen ausgeführt werden.

### Timeout {#timeout}

```java
Geolocation.getCurrent().useTimeout(10.0);
```

Das Attribut Timeout gibt die maximale Zeitdauer, ausgedrückt in Sekunden, an, die zwischen dem Aufruf von `getCurrentPosition()` oder einem Watch-Listener vergehen darf, bis der Browser eine Position zurückgibt. Wenn der Browser nicht in der Lage ist, innerhalb des gegebenen Timeouts erfolgreich eine neue Position zu erwerben, und in diesem Zeitraum keine weiteren Fehler aufgetreten sind, wird die Anfrage mit dem Status `TIMEOUT` gemeldet. Die Zeit, die für den Erwerb der Benutzererlaubnis benötigt wird, ist nicht in dem Zeitraum enthalten, der durch das Timeout-Attribut abgedeckt ist. Das Timeout-Attribut gilt nur für den Standortakquisitionsvorgang.

### Maximales Alter {#maximum-age}

```java
Geolocation.getCurrent().useMaximumAge(60.0);
```

Das Attribut maximales Alter gibt an, dass die App bereit ist, eine zwischengespeicherte Position zu akzeptieren, deren Alter nicht größer ist als die angegebene Zeit in Sekunden. Wenn das maximale Alter auf `0` gesetzt ist, muss der Browser sofort versuchen, eine neue Position zu erwerben. Wenn der Browser keine zwischengespeicherte Position hat, deren Alter nicht größer ist als das angegebene maximale Alter, muss er eine neue Position erwerben. Im Fall eines Watches bezieht sich das maximale Alter auf die erste zurückgegebene Position.

## Fehlerstatus {#failure-status}

Bei einer einmaligen Anfrage wird der Fehler als <JavadocLink type="foundation" location="com/webforj/geolocation/exception/WebforjGeolocationException" code='true'>WebforjGeolocationException</JavadocLink> im `PendingResult` ausgeliefert. Bei einem Watch-Update wird der Fehler als `GeolocationWatchEvent` ausgeliefert, wobei `isSuccess()` `false` zurückgibt. Das Ergebnis wird über <JavadocLink type="foundation" location="com/webforj/geolocation/GeolocationStatus" code='true'>GeolocationStatus</JavadocLink> gemeldet.

## Vollständiges Beispiel {#complete-example}

Die untenstehende Ansicht rendert drei beschriftete Zeilen für Breite, Länge und Höhe sowie einen Button, der die aktuelle Position anfordert. Das Ergebnis wird in die Zeilen geschrieben. Der Status wird über einen einzelnen wiederverwendeten Toast angekündigt.

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
  private final Button getPositionButton = new Button("Position abrufen", ButtonTheme.PRIMARY);
  private Toast currentToast;

  public MainView() {
    self.setDirection(FlexDirection.COLUMN).setSpacing("1em");
    self.setMaxWidth("24em").setMargin("4em auto");

    self.add(
        new H2("Geolokalisierung"),
        row("Breite", latitudeValue),
        row("Länge", longitudeValue),
        row("Höhe", altitudeValue),
        getPositionButton);

    getPositionButton.onClick(ev -> requestPosition());
  }

  private void requestPosition() {
    Geolocation geo = Geolocation.getCurrent();
    geo.useHighAccuracy(true)
        .useTimeout(10.0)
        .useMaximumAge(0.0);

    showToast("Position wird angefordert…", Theme.INFO);
    resetRows();

    PendingResult<GeolocationPosition> request = geo.getCurrentPosition();
    request.thenAccept(position -> {
      latitudeValue.setText(formatDegrees(position.getLatitude()));
      longitudeValue.setText(formatDegrees(position.getLongitude()));
      altitudeValue.setText(position.getAltitude()
          .map(alt -> String.format(Locale.ROOT, "%.0f m", alt))
          .orElse("—"));
      showToast("Position erworben", Theme.SUCCESS);
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
