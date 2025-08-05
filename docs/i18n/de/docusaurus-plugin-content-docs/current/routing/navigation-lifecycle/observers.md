---
sidebar_position: 2
title: Lifecycle Observers
_i18n_hash: be571bd197730689ba8346b2ef702a3f
---
Beobachter ermöglichen es Komponenten, auf Lebenszyklusereignisse zu reagieren, indem sie Schnittstellen für bestimmte Phasen implementieren. Dieses Muster sorgt für eine klare Trennung der Anliegen und vereinfacht die Handhabung der Navigationslogik.

## Verfügbare Beobachter {#available-observers}

- **`WillEnterObserver`**: Ermöglicht es Ihnen, Aufgaben zu erledigen, bevor eine Route betreten wird, z. B. das Abrufen benötigter Daten oder das Blockieren der Navigation.
- **`DidEnterObserver`**: Ideal für die Handhabung von Aktionen, nachdem die Komponente angehängt wurde, z. B. das Rendern von Daten oder das Auslösen von Animationen.
- **`WillLeaveObserver`**: Bietet eine Möglichkeit, Logik zu verwalten, bevor ein Benutzer eine Route verlässt, z. B. das Überprüfen auf ungespeicherte Änderungen.
- **`DidLeaveObserver`**: Wird für Aufräumaktionen oder andere Aufgaben verwendet, die nach dem Abziehen einer Komponente vom DOM ausgeführt werden sollen.

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

Hier überprüft `onWillEnter`, ob der Benutzer authentifiziert ist. Wenn nicht, wird die Navigation abgelehnt, was verhindert, dass die Navigation abgeschlossen wird, und stattdessen zur Login-Seite umgeleitet.

:::warning Beispiel für authentifizierte Routen - Nicht Produktionsbereit
Dieses Beispiel ist nur eine Darstellung, wie man mit authentifizierten Routen arbeitet. 
Dies **ist kein** Beispiel dafür, wie Sie ein authentifiziertes System auf Produktionsniveau schreiben würden.
Sie müssen die Konzepte und Muster aus diesem Beispiel übernehmen und an Ihren Authentifizierungsfluss/Ihr System anpassen.
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

Dieses Beispiel zeigt die Verwendung von `DidEnterObserver`, um Profilinformationen abzurufen und anzuzeigen, sobald die Komponente dem DOM angehängt ist.

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

In diesem Beispiel fordert `onWillLeave` den Benutzer mit einem Bestätigungsdialog auf, wenn es ungespeicherte Änderungen gibt, und lehnt die Navigation ab, wenn der Benutzer bleibt.

:::info Navigation Blockieren und Handling von Veto
Für weitere Informationen über die Blockierung der Navigation siehe [Navigation Blocking and Veto Handling](./navigation-blocking)
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

Dieses Beispiel löscht Benachrichtigungen, nachdem der Benutzer die `NotificationsView` verlässt, und verwendet den `DidLeaveObserver` für das Aufräumen.
