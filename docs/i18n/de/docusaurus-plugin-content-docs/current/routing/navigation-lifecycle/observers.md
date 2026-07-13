---
sidebar_position: 2
title: Lifecycle Observers
description: >-
  Hook into route lifecycle stages by implementing WillEnter, DidEnter,
  WillLeave, DidLeave, and Activate observer interfaces.
_i18n_hash: 3f39161991064d0d2506c0cb1dcd3503
---
Beobachter ermöglichen es Komponenten, auf Lebenszyklusereignisse zu reagieren, indem sie Schnittstellen für spezifische Phasen implementieren. Dieses Muster gewährleistet eine saubere Trennung der Bedenken und vereinfacht die Handhabung von Navigationslogik.

## Verfügbare Beobachter {#available-observers}

- **`WillEnterObserver`**: Ermöglicht es Ihnen, Aufgaben zu erledigen, bevor eine Route betreten wird, z. B. das Abrufen notwendiger Daten oder das Blockieren der Navigation.
- **`DidEnterObserver`**: Ideal zum Handhaben von Aktionen, nachdem die Komponente angehängt wurde, z. B. das Rendern von Daten oder das Auslösen von Animationen.
- **`WillLeaveObserver`**: Bietet eine Möglichkeit, Logik zu verwalten, bevor ein Benutzer eine Route verlässt, z. B. das Überprüfen nicht gespeicherter Änderungen.
- **`DidLeaveObserver`**: Wird für Bereinigungsaktionen oder andere Aufgaben verwendet, die ausgeführt werden sollen, nachdem eine Komponente vom DOM getrennt wurde.
- **`ActivateObserver`**: <DocChip chip='since' label='25.03' /> Wird ausgelöst, wenn eine zwischengespeicherte Komponente reaktiviert wird, z. B. beim Navigieren zur selben Route mit unterschiedlichen Parametern.

## Beispiel: Authentifizierung mit `WillEnterObserver` {#example-authentication-with-willenterobserver}

```java
@Route(value = "dashboard")
public class DashboardView extends Composite<Div> implements WillEnterObserver {

  @Override
  public void onWillEnter(WillEnterEvent event, ParametersBag parameters) {
    boolean isAuthenticated = authService.isAuthenticated();
    event.veto(!isAuthenticated);

    if (!isAuthenticated) {
      event.getRouter().navigate(LoginView.class);
    }
  }
}
```

Hier überprüft `onWillEnter`, ob der Benutzer authentifiziert ist. Wenn nicht, wird die Navigation abgelehnt, wodurch die Navigation nicht abgeschlossen wird und stattdessen zur Anmeldeseite umgeleitet wird.

:::warning Beispiel für authentifizierte Routen - Nicht für die Produktion geeignet
Dieses Beispiel zeigt nur, wie man authentifizierte Routen verwendet.
Dies **ist kein** Beispiel dafür, wie Sie ein authentifizierungsbasiertes System auf Produktionsebene schreiben würden.
Sie müssen die Konzepte und Muster, die in diesem Beispiel verwendet werden, anpassen, um mit Ihrem Authentifizierungsfluss/-system für Ihre App zu arbeiten.
:::

## Beispiel: Daten beim Routen-Eintritt abrufen mit `DidEnterObserver` {#example-fetching-data-on-route-entry-with-didenterobserver}

```java
@Route(value = "profile")
public class ProfileView extends Composite<Div> implements DidEnterObserver {

  @Override
  public void onDidEnter(DidEnterEvent event, ParametersBag parameters) {
    String userId = parameters.get("userId").orElseThrow();
    UserService.fetchProfile(userId).thenAccept(
        profile -> updateProfileUI(profile));
  }

  private void updateProfileUI(Profile profile) {
    // Code zur Aktualisierung der Benutzeroberfläche mit Profildaten
  }
}
```

Dieses Beispiel demonstriert die Verwendung von `DidEnterObserver`, um Profildaten abzurufen und anzuzeigen, sobald die Komponente im DOM angehängt ist.

## Beispiel: Umgang mit nicht gespeicherten Änderungen mit `WillLeaveObserver` {#example-handling-unsaved-changes-with-willleaveobserver}

```java
@Route(value = "edit-profile")
public class EditProfileView extends Composite<Div> implements WillLeaveObserver {
  private boolean hasUnsavedChanges = false;

  public EditProfileView() {
    // Logik zum Erkennen nicht gespeicherter Änderungen
  }

  @Override
  public void onWillLeave(WillLeaveEvent event, ParametersBag parameters) {
    event.veto(hasUnsavedChanges);

    if(hasUnsavedChanges) {
      ConfirmDialog.Result result = showConfirmDialog(
          "Es gibt nicht gespeicherte Änderungen. Möchten Sie sie verwerfen oder speichern?",
          "Nicht gespeicherte Änderungen",
          ConfirmDialog.OptionType.OK_CANCEL,
          ConfirmDialog.MessageType.WARNING);
    }
  }
}
```

In diesem Beispiel zeigt `onWillLeave` dem Benutzer ein Bestätigungsdialogfeld an, wenn nicht gespeicherte Änderungen vorhanden sind, und lehnt die Navigation ab, wenn der Benutzer sich entscheidet zu bleiben.

:::info Navigation blockieren und Veto-Handling
Für weitere Informationen zum Blockieren der Navigation siehe [Navigation Blocking and Veto Handling](./navigation-blocking)
:::

## Beispiel: Bereinigung mit `DidLeaveObserver` {#example-cleanup-with-didleaveobserver}

```java
@Route(value = "notifications")
public class NotificationsView extends Composite<Div> implements DidLeaveObserver {

  @Override
  public void onDidLeave(DidLeaveEvent event, ParametersBag parameters) {
    NotificationService.clearActiveNotifications();
  }
}
```

Dieses Beispiel leert die Benachrichtigungen, nachdem der Benutzer die `NotificationsView` verlässt, und verwendet den `DidLeaveObserver` für die Bereinigung.

## Beispiel: Datenaktualisierung mit `ActivateObserver` <DocChip chip='since' label='25.03' /> {#example-refreshing-data-with-activateobserver}

```java
@Route(value = "product/:id")
public class ProductView extends Composite<Div> implements ActivateObserver {
  private String currentProductId;

  @Override
  public void onActivate(ActivateEvent event, ParametersBag parameters) {
    String productId = parameters.get("id").orElseThrow();

    // Komponente wird mit anderen Parametern wiederverwendet
    if (!productId.equals(currentProductId)) {
      currentProductId = productId;
      refreshProductData(productId);
    }
  }

  private void refreshProductData(String productId) {
    // Code zum Abrufen und Anzeigen neuer Produktdaten
    ProductService.fetchProduct(productId).thenAccept(
        product -> updateProductUI(product));
  }
}
```

Dieses Beispiel demonstriert die Verwendung von `ActivateObserver`, um Daten zu aktualisieren, wenn zu derselben Route mit unterschiedlichen Parametern navigiert wird. Die Komponente bleibt zwischengespeichert und wird reaktiviert, anstatt neu erstellt zu werden, sodass die Benutzeroberfläche aktualisiert wird, um die richtigen Daten für die aktuellen Parameter anzuzeigen, ohne eine neue Komponente zu instanziieren.

:::tip Aktivierung in Komponenten-Hierarchien
Beim Navigieren zu einer Route wird das `Activate`-Ereignis für **alle zwischengespeicherten Komponenten in der Hierarchie** ausgelöst, die im aktuellen Pfad verbleiben. Beispielsweise erhalten sowohl die übergeordnete `ProductsLayout`-Komponente als auch die untergeordnete `ProductView`-Komponente das `Activate`-Ereignis, wenn sie zwischengespeichert sind und in der Routen-Hierarchie bleiben.
:::
