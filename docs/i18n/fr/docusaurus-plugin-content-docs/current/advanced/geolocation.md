---
sidebar_position: 35
sidebar_class_name: new-content
title: Geolocation
description: >-
  Request and watch the device's geographic position using the Geolocation
  class, with high-accuracy, timeout, and maximum age controls.
_i18n_hash: 68083cf323f26b69a62bc3147145f4d2
---
# Géolocalisation <DocChip chip='since' label='26.01' />

La classe <JavadocLink type="foundation" location="com/webforj/geolocation/Geolocation" code='true'>Géolocalisation</JavadocLink> fournit une interface au sous-système de géolocalisation du navigateur. Utilisez-la pour demander la position actuelle de l'appareil une fois, ou pour suivre la position pour des mises à jour continues.

<!-- INTRO_END -->

## Configuration et prérequis {#setup-and-prerequisites}

L'API de géolocalisation nécessite :

- Un **contexte sécurisé** (HTTPS). L'origine `localhost` est exemptée et fonctionne sur HTTP pour le développement local.
- La permission de l'utilisateur pour l'accès à la localisation. Le navigateur invite automatiquement la première fois qu'une position est demandée et le choix est conservé pour chaque origine.

Lorsque le sous-système n'est pas disponible, y accéder lance une `WebforjRuntimeException`.

## Instance {#instance}

Obtenez l'instance de géolocalisation pour l'environnement actuel :

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

## Demander une position {#requesting-a-position}

Appelez `getCurrentPosition()` pour demander la position géographique actuelle de l'appareil. Le <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink> retourné se termine avec le <JavadocLink type="foundation" location="com/webforj/geolocation/GeolocationPosition" code='true'>GeolocationPosition</JavadocLink> rapporté, ou exceptionnellement avec un <JavadocLink type="foundation" location="com/webforj/geolocation/exception/WebforjGeolocationException" code='true'>WebforjGeolocationException</JavadocLink> lorsque le navigateur est incapable d'obtenir une position.

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

:::info Permission du navigateur
Le navigateur peut demander la permission à l'utilisateur la première fois qu'une position est demandée. L'invite est affichée par le navigateur lui-même et ne fait pas partie de l'interface utilisateur de l'application.
:::

## Surveiller la position {#watching-the-position}

Enregistrez un écouteur de surveillance pour recevoir un flux de mises à jour de position à mesure que l'appareil se déplace.

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

// Plus tard, pour arrêter de surveiller :
registration.remove();
```

Un <JavadocLink type="foundation" location="com/webforj/geolocation/event/GeolocationWatchEvent" code='true'>GeolocationWatchEvent</JavadocLink> se déclenche pour chaque mise à jour, qu'elle soit réussie ou non. Vérifiez `isSuccess()` avant de lire la position.

## Configurer les demandes {#configuring-requests}

Trois setters configurent les demandes ultérieures d'informations de géolocalisation.

### Haute précision {#high-accuracy}

```java
Geolocation.getCurrent().useHighAccuracy(true);
```

L'attribut de haute précision fournit un indice que l'application souhaite recevoir les meilleurs résultats possibles. Cela peut entraîner des temps de réponse plus lents ou une consommation d'énergie accrue. L'utilisateur peut également refuser cette capacité, ou l'appareil peut ne pas être capable de fournir des résultats plus précis que si le drapeau n'était pas spécifié. L'objectif de cet attribut est de permettre aux applications d'informer le navigateur qu'elles ne nécessitent pas de corrections de géolocalisation à haute précision et, par conséquent, le navigateur peut éviter d'utiliser des fournisseurs de géolocalisation qui consomment une quantité significative d'énergie. Cela est particulièrement utile pour les applications fonctionnant sur des appareils alimentés par batterie, comme les téléphones mobiles.

### Délai d'attente {#timeout}

```java
Geolocation.getCurrent().useTimeout(10.0);
```

L'attribut de délai d'attente désigne la durée maximale de temps, exprimée en secondes, qui est autorisée à passer depuis l'appel à `getCurrentPosition()` ou un écouteur de surveillance jusqu'à ce que le navigateur retourne une position. Si le navigateur est incapable d'acquérir avec succès une nouvelle position avant l'expiration du délai donné, et qu'aucune autre erreur n'est survenue durant cet intervalle, la demande est rapportée avec le statut `TIMEOUT`. Le temps consacré à l'obtention de la permission de l'utilisateur n'est pas inclus dans la période couverte par l'attribut de délai d'attente. L'attribut de délai d'attente s'applique uniquement à l'opération d'acquisition de la localisation.

### Âge maximum {#maximum-age}

```java
Geolocation.getCurrent().useMaximumAge(60.0);
```

L'attribut d'âge maximum indique que l'application est prête à accepter une position mise en cache dont l'âge ne dépasse pas le temps spécifié en secondes. Si l'âge maximum est défini sur `0`, le navigateur doit immédiatement tenter d'acquérir une nouvelle position. Si le navigateur ne dispose pas d'une position mise en cache disponible dont l'âge ne dépasse pas l'âge maximum spécifié, il doit acquérir une nouvelle position. Dans le cas d'une surveillance, l'âge maximum fait référence à la première position retournée.

## Statut d'échec {#failure-status}

Pour une demande unique, l'échec est livré sous la forme d'une <JavadocLink type="foundation" location="com/webforj/geolocation/exception/WebforjGeolocationException" code='true'>WebforjGeolocationException</JavadocLink> sur le `PendingResult`. Pour une mise à jour de surveillance, l'échec est livré sous la forme d'un `GeolocationWatchEvent` avec `isSuccess()` retournant `false`. Le résultat est rapporté via <JavadocLink type="foundation" location="com/webforj/geolocation/GeolocationStatus" code='true'>GeolocationStatus</JavadocLink>.

## Exemple complet {#complete-example}

La vue ci-dessous rend trois lignes étiquetées pour la latitude, la longitude et l'altitude, plus un bouton qui demande la position actuelle. Le résultat est écrit dans les lignes. L'état est annoncé via un toast réutilisé.

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
  private final Button getPositionButton = new Button("Obtenir la position", ButtonTheme.PRIMARY);
  private Toast currentToast;

  public MainView() {
    self.setDirection(FlexDirection.COLUMN).setSpacing("1em");
    self.setMaxWidth("24em").setMargin("4em auto");

    self.add(
        new H2("Géolocalisation"),
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

    showToast("Demande de position…", Theme.INFO);
    resetRows();

    PendingResult<GeolocationPosition> request = geo.getCurrentPosition();
    request.thenAccept(position -> {
      latitudeValue.setText(formatDegrees(position.getLatitude()));
      longitudeValue.setText(formatDegrees(position.getLongitude()));
      altitudeValue.setText(position.getAltitude()
          .map(alt -> String.format(Locale.ROOT, "%.0f m", alt))
          .orElse("—"));
      showToast("Position acquise", Theme.SUCCESS);
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
