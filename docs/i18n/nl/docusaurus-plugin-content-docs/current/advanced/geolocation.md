---
sidebar_position: 35
sidebar_class_name: new-content
title: Geolocation
description: >-
  Request and watch the device's geographic position using the Geolocation
  class, with high-accuracy, timeout, and maximum age controls.
_i18n_hash: 68083cf323f26b69a62bc3147145f4d2
---
# Geolocatie <DocChip chip='since' label='26.01' />

De <JavadocLink type="foundation" location="com/webforj/geolocation/Geolocation" code='true'>Geolocatie</JavadocLink> klasse biedt een interface voor de geolocatiefuncties van de browser. Gebruik het om de huidige locatie van het apparaat één keer op te vragen, of om de locatie continu te volgen voor updates.

<!-- INTRO_END -->

## Setup en vereisten {#setup-and-prerequisites}

De Geolocatie-API vereist:

- Een **veilige context** (HTTPS). De `localhost` oorsprong is hiervan uitgesloten en werkt via HTTP voor lokale ontwikkeling.
- Gebruikers toestemming voor toegang tot locatie. De browser vraagt automatisch om toestemming de eerste keer dat een locatie wordt opgevraagd en de keuze wordt opgeslagen per oorsprong.

Wanneer de subsystem niet beschikbaar is, wordt er een `WebforjRuntimeException` weergegeven.

## Exemplaar {#instance}

Haal het geolocatie-exemplaar op voor de huidige omgeving:

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

## Een locatie opvragen {#requesting-a-position}

Roep `getCurrentPosition()` aan om de huidige geografische locatie van het apparaat op te vragen. De geretourneerde <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink> voltooit met de gerapporteerde <JavadocLink type="foundation" location="com/webforj/geolocation/GeolocationPosition" code='true'>GeolocationPosition</JavadocLink>, of uitzonderlijk met een <JavadocLink type="foundation" location="com/webforj/geolocation/exception/WebforjGeolocationException" code='true'>WebforjGeolocationException</JavadocLink> wanneer de browser niet in staat is om een locatie te verkrijgen.

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

:::info Browser toestemming
De browser kan de gebruiker om toestemming vragen de eerste keer dat een locatie wordt opgevraagd. De prompt wordt weergegeven door de browser zelf en maakt geen deel uit van de app UI.
:::

## De locatie volgen {#watching-the-position}

Registreer een luisteraar om een continu stroom van locatie-updates te ontvangen terwijl het apparaat beweegt.

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

// Later, om te stoppen met volgen:
registration.remove();
```

Een <JavadocLink type="foundation" location="com/webforj/geolocation/event/GeolocationWatchEvent" code='true'>GeolocationWatchEvent</JavadocLink> wordt geactiveerd bij elke update, succesvol of niet. Controleer `isSuccess()` voordat je de positie leest.

## Verzoeken configureren {#configuring-requests}

Drie setters configureren opvolgende verzoeken om geolocatie-informatie.

### Hoge nauwkeurigheid {#high-accuracy}

```java
Geolocation.getCurrent().useHighAccuracy(true);
```

De hoge nauwkeurigheidseigenschap geeft een hint dat de app graag de best mogelijke resultaten wil ontvangen. Dit kan resulteren in tragere responstijden of een hoger energieverbruik. De gebruiker kan deze mogelijkheid ook weigeren, of het apparaat kan niet nauwkeuriger resultaten bieden dan wanneer de vlag niet is opgegeven. Het doel van deze eigenschap is om apps in staat te stellen de browser te informeren dat ze geen hoge precisie geolocatie-resultaten vereisen, zodat de browser kan vermijden geolocatieproviders te gebruiken die veel energie verbruiken. Dit is vooral nuttig voor apps die draaien op apparaten met batterijen, zoals mobiele telefoons.

### Time-out {#timeout}

```java
Geolocation.getCurrent().useTimeout(10.0);
```

De time-out eigenschap geeft de maximale tijd aan, uitgedrukt in seconden, die toegestaan is om te verstrijken vanaf de aanroep naar `getCurrentPosition()` of een luisteraar voor het volgen totdat de browser een locatie retourneert. Als de browser er niet in slaagt om voor de gegeven time-out een nieuwe positie succesvol te verkrijgen, en er in deze periode geen andere fouten zijn opgetreden, wordt het verzoek gerapporteerd met de status `TIMEOUT`. De tijd die besteed wordt aan het verkrijgen van de gebruikers toestemming is niet inbegrepen in de periode die wordt gedekt door de time-out eigenschap. De time-out eigenschap is alleen van toepassing op de locatieverwervingsoperatie.

### Maximale leeftijd {#maximum-age}

```java
Geolocation.getCurrent().useMaximumAge(60.0);
```

De maximale leeftijd eigenschap geeft aan dat de app bereid is een gecachte positie te accepteren waarvan de leeftijd niet groter is dan de gespecificeerde tijd in seconden. Als de maximale leeftijd is ingesteld op `0`, moet de browser onmiddellijk proberen een nieuwe positie te verkrijgen. Als de browser geen gecachte positie heeft die beschikbaar is met een leeftijd die niet groter is dan de gespecificeerde maximale leeftijd, moet de browser een nieuwe positie verkrijgen. In het geval van een watch, verwijst de maximale leeftijd naar de eerste positie die wordt geretourneerd.

## Foutstatus {#failure-status}

Voor een eenmalig verzoek wordt de fout geleverd als een <JavadocLink type="foundation" location="com/webforj/geolocation/exception/WebforjGeolocationException" code='true'>WebforjGeolocationException</JavadocLink> op de `PendingResult`. Voor een update tijdens het volgen wordt de fout geleverd als een `GeolocationWatchEvent` met `isSuccess()` dat `false` retourneert. De uitkomst wordt gerapporteerd via <JavadocLink type="foundation" location="com/webforj/geolocation/GeolocationStatus" code='true'>GeolocationStatus</JavadocLink>.

## Volledig voorbeeld {#complete-example}

De weergave hieronder render de drie gelabelde rijen voor breedtegraad, lengtegraad en hoogte, plus een knop die de huidige positie aanroept. Het resultaat wordt in de rijen geschreven. De status wordt aangekondigd via een enkele hergebruikte toast.

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
  private final Button getPositionButton = new Button("Haal positie op", ButtonTheme.PRIMARY);
  private Toast currentToast;

  public MainView() {
    self.setDirection(FlexDirection.COLUMN).setSpacing("1em");
    self.setMaxWidth("24em").setMargin("4em auto");

    self.add(
        new H2("Geolocatie"),
        row("Breedtegraad", latitudeValue),
        row("Lengtegraad", longitudeValue),
        row("Hoogte", altitudeValue),
        getPositionButton);

    getPositionButton.onClick(ev -> requestPosition());
  }

  private void requestPosition() {
    Geolocation geo = Geolocation.getCurrent();
    geo.useHighAccuracy(true)
        .useTimeout(10.0)
        .useMaximumAge(0.0);

    showToast("Positie aan het opvragen…", Theme.INFO);
    resetRows();

    PendingResult<GeolocationPosition> request = geo.getCurrentPosition();
    request.thenAccept(position -> {
      latitudeValue.setText(formatDegrees(position.getLatitude()));
      longitudeValue.setText(formatDegrees(position.getLongitude()));
      altitudeValue.setText(position.getAltitude()
          .map(alt -> String.format(Locale.ROOT, "%.0f m", alt))
          .orElse("—"));
      showToast("Positie verkregen", Theme.SUCCESS);
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
