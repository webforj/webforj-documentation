---
sidebar_position: 35
sidebar_class_name: new-content
title: Geolocation
description: >-
  Request and watch the device's geographic position using the Geolocation
  class, with high-accuracy, timeout, and maximum age controls.
_i18n_hash: 68083cf323f26b69a62bc3147145f4d2
---
# Paikannus <DocChip chip='since' label='26.01' />

<JavadocLink type="foundation" location="com/webforj/geolocation/Geolocation" code='true'>Paikannus</JavadocLink> -luokka tarjoaa rajapinnan selaimen paikannusjärjestelmään. Käytä sitä kysyäksesi laitteen nykyistä sijaintia kerran tai seuratakseen sijaintia jatkuvia päivityksiä varten.

<!-- INTRO_END -->

## Asetukset ja vaatimukset {#setup-and-prerequisites}

Paikannus-API vaatii:

- **Turvallisen kontekstin** (HTTPS). `localhost` -alkuperä on poikkeus ja toimii HTTP:ssä paikallisessa kehityksessä.
- Käyttäjän luvan sijaintitietojen käyttöön. Selain pyytää automaattisesti ensimmäisellä kerralla, kun sijaintia kysytään, ja valinta tallennetaan per alkuperä.

Kun alijärjestelmä ei ole käytettävissä, sen käyttämisestä heitetään `WebforjRuntimeException`.

## Instanssi {#instance}

Hanki nykyisen ympäristön paikannusinstanssi:

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

## Sijainnin kysyminen {#requesting-a-position}

Kutsu `getCurrentPosition()` kysyäksesi laitteen nykyistä maantieteellistä sijaintia. Palautettu <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink> valmistuu ilmoitetulla <JavadocLink type="foundation" location="com/webforj/geolocation/GeolocationPosition" code='true'>GeolocationPosition</JavadocLink> -oliolla tai poikkeuksellisesti <JavadocLink type="foundation" location="com/webforj/geolocation/exception/WebforjGeolocationException" code='true'>WebforjGeolocationException</JavadocLink> -oliolla, kun selain ei pysty hankkimaan sijaintia.

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

:::info Selain lupa
Selain voi pyytää käyttäjältä lupaa ensimmäisellä kerralla, kun sijaintia kysytään. Kehote näytetään selaimen itsensä toimesta eikä se ole osa sovelluksen käyttöliittymää.
:::

## Sijainnin seuranta {#watching-the-position}

Rekisteröi valvontakuuntelija saadaksesi virran sijaintipäivityksiä, kun laite liikkuu.

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

// Myöhemmin, lopettaaksesi seurannan:
registration.remove();
```

<JavadocLink type="foundation" location="com/webforj/geolocation/event/GeolocationWatchEvent" code='true'>GeolocationWatchEvent</JavadocLink> aktivoituu jokaiselle päivitykselle, onnistuneelle tai ei. Tarkista `isSuccess()` ennen sijainnin lukemista.

## Pyynnön konfigurointi {#configuring-requests}

Kolme asettajaa konfiguroi seuraavat pyynnöt paikannustietoihin.

### Korkea tarkkuus {#high-accuracy}

```java
Geolocation.getCurrent().useHighAccuracy(true);
```

Korkean tarkkuuden attribuutti antaa vihjeen, että sovellus haluaa vastaanottaa parhaat mahdolliset tulokset. Tämä saattaa johtaa hitaampiin vasteaikoihin tai lisääntyneeseen energiankulutukseen. Käyttäjä voi myös kieltää tämän toiminnon tai laite ei välttämättä pysty tarjoamaan tarkempia tuloksia kuin jos lippua ei olisi määritetty. Tämän attribuutin tarkoitus on antaa sovelluksille mahdollisuus ilmoittaa selaimelle, etteivät ne tarvitse korkeaa tarkkuutta paikannuskorjauksia, ja siten selain voi välttää käyttämästä paikannuspalveluja, jotka kuluttavat merkittävästi energiaa. Tämä on erityisen hyödyllistä sovelluksille, jotka toimivat akkukäyttöisissä laitteissa, kuten matkapuhelimissa.

### Aika ylitys {#timeout}

```java
Geolocation.getCurrent().useTimeout(10.0);
```

Aika ylitys -attribuutti määrittelee suurimman ajan, joka saa kulua `getCurrentPosition()` -kutsusta tai valvontakuuntelijasta, ennen kuin selain palauttaa sijainnin. Jos selain ei pysty onnistuneesti hankkimaan uutta sijaintia ennen annettua aikarajaa, ja tämän ajanjakson aikana ei ole ilmennyt muita virheitä, pyyntö ilmoitetaan `TIMEOUT` -tilalla. Aika, joka kuluu käyttäjän luvan hakemiseen, ei sisälly aikarajoituksen kattamaan aikajaksoon. Aika ylitys -attributti koskee vain sijainnin hankintatoimintoa.

### Maksimi-ikä {#maximum-age}

```java
Geolocation.getCurrent().useMaximumAge(60.0);
```

Maksimi-ikä -attribuutti tarkoittaa, että sovellus hyväksyy välimuistissa olevan sijainnin, jonka ikä ei ole suurempi kuin määritetty aika sekunteina. Jos maksimi-ikä on asetettu `0`:ksi, selaimen on heti yritettävä hankkia uusi sijainti. Jos selaimella ei ole käytettävissään välimuistitietoa, jonka ikä ei ole suurempi kuin määritetty maksimi-ikä, sen on hankittava uusi sijainti. Valvonnassa maksimi-ikä viittaa ensimmäiseen palautettuun sijaintiin.

## Häiriötilat {#failure-status}

Yksittäisessä pyynnössä häiriö toimitetaan <JavadocLink type="foundation" location="com/webforj/geolocation/exception/WebforjGeolocationException" code='true'>WebforjGeolocationException</JavadocLink> -oliolla `PendingResult`-objektissa. Valvontapäivityksessä häiriö ilmoitetaan `GeolocationWatchEvent` -oliossa, jonka `isSuccess()` palauttaa `false`. Tulos ilmoitetaan <JavadocLink type="foundation" location="com/webforj/geolocation/GeolocationStatus" code='true'>GeolocationStatus</JavadocLink> -oliolla.

## Täydellinen esimerkki {#complete-example}

Alla oleva näkymä renderöi kolme nimettyä riviä leveysasteelle, pituusasteelle ja korkeudelle, plus painikkeen, joka pyytää nykyistä sijaintia. Tulos kirjoitetaan riveihin. Tila ilmoitetaan yhden käytetyn toastin kautta.

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
  private final Button getPositionButton = new Button("Hanki sijainti", ButtonTheme.PRIMARY);
  private Toast currentToast;

  public MainView() {
    self.setDirection(FlexDirection.COLUMN).setSpacing("1em");
    self.setMaxWidth("24em").setMargin("4em auto");

    self.add(
        new H2("Paikannus"),
        row("Leveysaste", latitudeValue),
        row("Pituusaste", longitudeValue),
        row("Korkeus", altitudeValue),
        getPositionButton);

    getPositionButton.onClick(ev -> requestPosition());
  }

  private void requestPosition() {
    Geolocation geo = Geolocation.getCurrent();
    geo.useHighAccuracy(true)
        .useTimeout(10.0)
        .useMaximumAge(0.0);

    showToast("Pyydetään sijaintia…", Theme.INFO);
    resetRows();

    PendingResult<GeolocationPosition> request = geo.getCurrentPosition();
    request.thenAccept(position -> {
      latitudeValue.setText(formatDegrees(position.getLatitude()));
      longitudeValue.setText(formatDegrees(position.getLongitude()));
      altitudeValue.setText(position.getAltitude()
          .map(alt -> String.format(Locale.ROOT, "%.0f m", alt))
          .orElse("—"));
      showToast("Sijainti hankittu", Theme.SUCCESS);
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
