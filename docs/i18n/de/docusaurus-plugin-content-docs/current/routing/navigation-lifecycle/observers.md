---
sidebar_position: 2
title: Lifecycle Observers
_i18n_hash: 2c66b4194e4d93a762d9a8cd75918e49
---
Beobachter ermöglichen es Komponenten, auf Lebenszyklusereignisse zu reagieren, indem sie Schnittstellen für spezifische Phasen implementieren. Dieses Muster gewährleistet eine klare Trennung der Anliegen und vereinfacht die Handhabung der Navigationslogik.

## Verfügbare Beobachter {#available-observers}

- **`WillEnterObserver`**: Ermöglicht es Ihnen, Aufgaben zu handhaben, bevor eine Route betreten wird, wie das Abrufen notwendiger Daten oder das Sperren der Navigation.
- **`DidEnterObserver`**: Ideal für die Handhabung von Aktionen, nachdem die Komponente angefügt wurde, wie das Rendern von Daten oder das Auslösen von Animationen.
- **`WillLeaveObserver`**: Bietet eine Möglichkeit, Logik zu verwalten, bevor ein Benutzer eine Route verlässt, wie das Überprüfen von ungespeicherten Änderungen.
- **`DidLeaveObserver`**: Wird für Bereinigungsaktionen oder andere Aufgaben verwendet, die ausgeführt werden sollen, nachdem eine Komponente vom DOM getrennt wurde.

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

Hier überprüft `onWillEnter`, ob der Benutzer authentifiziert ist. Falls nicht, wird die Navigation vetoisiert, wodurch die Navigation nicht abgeschlossen wird und stattdessen zur Anmeldeseite umgeleitet wird.

:::warning Beispiel für authentifizierte Routen - Nicht für die Produktion geeignet
Dieses Beispiel dient nur als Hinweis, wie Sie authentifizierte Routen verwenden können.
Dies **ist** kein Beispiel dafür, wie Sie ein produktionsreifes Authentifizierungssystem schreiben würden.
Sie müssen die Konzepte und Muster, die in diesem Beispiel verwendet werden, anpassen, um mit Ihrem Authentifizierungsfluss/Ihrem System für Ihre App zu arbeiten.
:::

## Beispiel: Daten beim Routenauftritt mit `DidEnterObserver` abrufen {#example-fetching-data-on-route-entry-with-didenterobserver}

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
    // Code zum Aktualisieren der UI mit Profildaten
  }
}
```

Dieses Beispiel zeigt die Verwendung von `DidEnterObserver`, um Profilinformationen abzurufen und anzuzeigen, sobald die Komponente dem DOM hinzugefügt wurde.

## Beispiel: Umgang mit ungespeicherten Änderungen mit `WillLeaveObserver` {#example-handling-unsaved-changes-with-willleaveobserver}

```java
@Route(value = "edit-profile")
public class EditProfileView extends Composite<Div> implements WillLeaveObserver {
  private boolean hasUnsavedChanges = false;

  public EditProfileView() {
    // Logik zur Erkennung ungespeicherter Änderungen
  }

  @Override
  public void onWillLeave(WillLeaveEvent event, ParametersBag parameters) {
    event.veto(hasUnsavedChanges);

    if(hasUnsavedChanges) {
      ConfirmDialog.Result result = showConfirmDialog(
          "Es gibt ungespeicherte Änderungen. Möchten Sie diese verwerfen oder speichern?",
          "Ungespeicherte Änderungen",
          ConfirmDialog.OptionType.OK_CANCEL,
          ConfirmDialog.MessageType.WARNING);
    }
  }
}
```

In diesem Beispiel fordert `onWillLeave` den Benutzer mit einem Bestätigungsdialog auf, wenn es ungespeicherte Änderungen gibt, und vetoisiert die Navigation, wenn der Benutzer sich entscheidet, zu bleiben.

:::info Navigationssperrung und Veto-Behandlung
Für weitere Informationen zur Blockierung der Navigation siehe [Navigationssperrung und Veto-Behandlung](./navigation-blocking)
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

Dieses Beispiel löscht die Benachrichtigungen, nachdem der Benutzer die `NotificationsView` verlässt, wobei der `DidLeaveObserver` für die Bereinigung verwendet wird.
