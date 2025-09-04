---
sidebar_position: 2
title: Lifecycle Observers
_i18n_hash: 18390849527056ed2780b761ae7919c1
---
Beobachter ermöglichen es Komponenten, auf Lebenszyklusereignisse zu reagieren, indem sie Schnittstellen für bestimmte Phasen implementieren. Dieses Muster gewährleistet eine saubere Trennung der Verantwortlichkeiten und vereinfacht die Handhabung der Navigationslogik.

## Verfügbare Beobachter {#available-observers}

- **`WillEnterObserver`**: Ermöglicht es Ihnen, Aufgaben zu erledigen, bevor eine Route betreten wird, wie das Abrufen notwendiger Daten oder das Blockieren der Navigation.
- **`DidEnterObserver`**: Ideal für die Handhabung von Aktionen, nachdem die Komponente angehängt wurde, wie das Rendern von Daten oder das Auslösen von Animationen.
- **`WillLeaveObserver`**: Bietet eine Möglichkeit, Logik zu verwalten, bevor ein Benutzer eine Route verlässt, wie das Überprüfen nicht gespeicherter Änderungen.
- **`DidLeaveObserver`**: Wird für Aufräumaktionen oder andere Aufgaben verwendet, die ausgeführt werden sollten, nachdem eine Komponente vom DOM getrennt wurde.
- **`ActivateObserver`** (seit 25.03): Wird ausgelöst, wenn eine zwischengespeicherte Komponente reaktiviert wird, z. B. beim Navigieren zur selben Route mit unterschiedlichen Parametern.

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

Hier prüft `onWillEnter`, ob der Benutzer authentifiziert ist. Andernfalls wird die Navigation abgelehnt, was die Vollziehung der Navigation verhindert und stattdessen zur Anmeldeseite umleitet.

:::warning Beispiel für authentifizierte Routen - Nicht bereit für die Produktion
Dieses Beispiel zeigt nur, wie man authentifizierte Routen verwendet.
Dies **ist kein** Beispiel dafür, wie man ein authentifiziertes System auf Produktionsniveau schreiben würde.
Sie müssen die Konzepte und Muster, die in diesem Beispiel verwendet werden, anpassen, um mit Ihrem Authentifizierungsfluss/-system für Ihre App zu arbeiten.
:::

## Beispiel: Abrufen von Daten beim Eintritt in die Route mit `DidEnterObserver` {#example-fetching-data-on-route-entry-with-didenterobserver}

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
    // Code zum Aktualisieren der Benutzeroberfläche mit Profildaten
  }
}
```

Dieses Beispiel zeigt die Verwendung von `DidEnterObserver`, um Profildaten abzurufen und anzuzeigen, sobald die Komponente an das DOM angehängt wird.

## Beispiel: Umgang mit nicht gespeicherten Änderungen mit `WillLeaveObserver` {#example-handling-unsaved-changes-with-willleaveobserver}

```java
@Route(value = "edit-profile")
public class EditProfileView extends Composite<Div> implements WillLeaveObserver {
  private boolean hasUnsavedChanges = false;

  public EditProfileView() {
    // Logik zur Erkennung nicht gespeicherter Änderungen
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

In diesem Beispiel fordert `onWillLeave` den Benutzer mit einem Bestätigungsdialog auf, wenn es nicht gespeicherte Änderungen gibt, und blockiert die Navigation, wenn der Benutzer sich entscheidet, zu bleiben.

:::info Navigation Blockierung und Veto-Handhabung
Für weitere Informationen zur Blockierung der Navigation siehe [Navigation Blocking and Veto Handling](./navigation-blocking)
:::

## Beispiel: Aufräumen mit `DidLeaveObserver` {#example-cleanup-with-didleaveobserver}

```java
@Route(value = "notifications")
public class NotificationsView extends Composite<Div> implements DidLeaveObserver {

  @Override
  public void onDidLeave(DidLeaveEvent event, ParametersBag parameters) {
    NotificationService.clearActiveNotifications();
  }
}
```

Dieses Beispiel löscht Benachrichtigungen, nachdem der Benutzer die `NotificationsView` verlässt, und verwendet `DidLeaveObserver` zum Aufräumen.

## Beispiel: Daten aktualisieren mit `ActivateObserver` {#example-refreshing-data-with-activateobserver}

:::info Seit 25.03
Der `ActivateObserver` und `ActivateEvent` sind ab der Version `25.03` von webforJ verfügbar.
:::

```java
@Route(value = "product/:id")
public class ProductView extends Composite<Div> implements ActivateObserver {
  private String currentProductId;

  @Override
  public void onActivate(ActivateEvent event, ParametersBag parameters) {
    String productId = parameters.get("id").orElseThrow();
    
    // Komponente wird mit unterschiedlichen Parametern wiederverwendet
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

Dieses Beispiel zeigt die Verwendung von `ActivateObserver`, um Daten zu aktualisieren, wenn man zur selben Route mit unterschiedlichen Parametern navigiert. Die Komponente bleibt zwischengespeichert und wird reaktiviert, anstatt neu erstellt zu werden, sodass die Benutzeroberfläche aktualisiert wird, um die richtigen Daten für die aktuellen Parameter anzuzeigen, ohne eine neue Komponente zu instanziieren.

:::tip Aktivierung in Komponentenhierarchien
Beim Navigieren zu einer Route wird das `Activate`-Ereignis für **alle zwischengespeicherten Komponenten in der Hierarchie** ausgelöst, die im aktuellen Pfad verbleiben. Zum Beispiel, wenn man von `/products/123` zu `/products/456` navigiert, erhalten sowohl die übergeordnete Komponente `ProductsLayout` als auch die untergeordnete Komponente `ProductView` das `Activate`-Ereignis, wenn sie zwischengespeichert sind und in der Routenhierarchie verbleiben.
:::
