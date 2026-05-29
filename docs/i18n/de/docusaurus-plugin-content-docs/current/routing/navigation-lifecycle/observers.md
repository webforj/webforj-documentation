---
sidebar_position: 2
title: Lifecycle Observers
_i18n_hash: a584e996523ba2b98ecb9d7ab2f366f3
---
Observers ermöglichen es Komponenten, auf Lebenszyklusereignisse zu reagieren, indem sie Schnittstellen für bestimmte Phasen implementieren. Dieses Muster gewährleistet eine saubere Trennung der Anliegen und vereinfacht die Handhabung der Navigationslogik.

## Verfügbare Beobachter {#available-observers}

- **`WillEnterObserver`**: Ermöglicht das Bearbeiten von Aufgaben, bevor eine Route betreten wird, z. B. das Abrufen notwendiger Daten oder das Blockieren der Navigation.
- **`DidEnterObserver`**: Ideal zum Handhaben von Aktionen, nachdem die Komponente angehängt wurde, z. B. das Rendern von Daten oder das Auslösen von Animationen.
- **`WillLeaveObserver`**: Bietet eine Möglichkeit, Logik zu verwalten, bevor ein Benutzer eine Route verlässt, z. B. das Überprüfen ungesicherter Änderungen.
- **`DidLeaveObserver`**: Wird für Aufräumaktionen oder andere Aufgaben verwendet, die nach dem Abtrennen einer Komponente vom DOM ausgeführt werden sollen.
- **`ActivateObserver`**: <DocChip chip='since' label='25.03' /> Ausgelöst, wenn eine zwischengespeicherte Komponente reaktiviert wird, z. B. beim Navigieren zur gleichen Route mit anderen Parametern.

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

Hier überprüft `onWillEnter`, ob der Benutzer authentifiziert ist. Wenn nicht, wird die Navigation abgelehnt, was verhindert, dass die Navigation abgeschlossen wird, und der Benutzer zur Anmeldeseite umgeleitet wird.

:::warning Beispiel für authentifizierte Routen - Nicht produktionsbereit
Das vorherige Beispiel dient lediglich zur Veranschaulichung, wie man authentifizierte Routen verwendet. Dies **ist kein** Beispiel, wie man ein produktionsreifes Authentifizierungssystem schreiben würde. Sie müssen die Konzepte und Muster, die in diesem Beispiel verwendet werden, anpassen, um mit Ihrem Authentifizierungsfluss/-system für Ihre App zu arbeiten.
:::

## Beispiel: Datenabruf beim Routen-Eintritt mit `DidEnterObserver` {#example-fetching-data-on-route-entry-with-didenterobserver}

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
    // Code zur Aktualisierung der UI mit Profildaten
  }
}
```

Dieses Beispiel zeigt die Verwendung von `DidEnterObserver`, um Profildaten abzurufen und anzuzeigen, sobald die Komponente dem DOM angehängt wird.

## Beispiel: Behandlung ungesicherter Änderungen mit `WillLeaveObserver` {#example-handling-unsaved-changes-with-willleaveobserver}

```java
@Route(value = "edit-profile")
public class EditProfileView extends Composite<Div> implements WillLeaveObserver {
  private boolean hasUnsavedChanges = false;

  public EditProfileView() {
    // Logik zur Erkennung ungesicherter Änderungen
  }

  @Override
  public void onWillLeave(WillLeaveEvent event, ParametersBag parameters) {
    event.veto(hasUnsavedChanges);

    if(hasUnsavedChanges) {
      ConfirmDialog.Result result = showConfirmDialog(
          "Es gibt ungesicherte Änderungen. Möchten Sie diese verwerfen oder speichern?",
          "Ungesicherte Änderungen",
          ConfirmDialog.OptionType.OK_CANCEL,
          ConfirmDialog.MessageType.WARNING);
    }
  }
}
```

In diesem Beispiel zeigt `onWillLeave` dem Benutzer ein Bestätigungsdialogfeld an, wenn ungesicherte Änderungen vorliegen, und lehnt die Navigation ab, wenn der Benutzer entscheidet, zu bleiben.

:::info Navigation blockieren und Veto-Behandlung
Für weitere Informationen über das Blockieren der Navigation siehe [Navigation Blocking and Veto Handling](./navigation-blocking)
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

Dieses Beispiel leert Benachrichtigungen, nachdem der Benutzer die `NotificationsView` verlässt, und verwendet `DidLeaveObserver` für die Bereinigung.

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

Dieses Beispiel zeigt die Verwendung von `ActivateObserver`, um Daten zu aktualisieren, wenn zur gleichen Route mit anderen Parametern navigiert wird. Die Komponente bleibt im Cache und wird reaktiviert, anstatt neu erstellt zu werden, sodass die UI aktualisiert wird, um die korrekten Daten für die aktuellen Parameter anzuzeigen, ohne eine neue Komponente zu instanziieren.

:::tip Aktivierung in Komponenten-Hierarchien
Beim Navigieren zu einer Route wird das `Activate`-Ereignis für **alle zwischengespeicherten Komponenten in der Hierarchie** ausgelöst, die im aktuellen Pfad bleiben. Wenn Sie beispielsweise von `/products/123` zu `/products/456` navigieren, erhalten sowohl die übergeordnete `ProductsLayout`-Komponente als auch die untergeordnete `ProductView`-Komponente das `Activate`-Ereignis, wenn sie zwischengespeichert sind und in der Routenhierarchie bleiben.
:::
