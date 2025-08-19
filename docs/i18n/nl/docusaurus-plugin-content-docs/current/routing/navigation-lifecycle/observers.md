---
sidebar_position: 2
title: Lifecycle Observers
_i18n_hash: 2c66b4194e4d93a762d9a8cd75918e49
---
Observers stelt componenten in staat om te reageren op levenscyclusgebeurtenissen door interfaces voor specifieke fasen te implementeren. Dit patroon zorgt voor een schone scheiding van verantwoordelijkheden en vereenvoudigt het afhandelen van navigatielogica.

## Beschikbare observers {#available-observers}

- **`WillEnterObserver`**: Hiermee kun je taken afhandelen voordat een route wordt binnengegaan, zoals het ophalen van noodzakelijke gegevens of het blokkeren van navigatie.
- **`DidEnterObserver`**: Ideaal voor het afhandelen van acties nadat het component is bevestigd, zoals het weergeven van gegevens of het starten van animaties.
- **`WillLeaveObserver`**: Biedt een manier om logica te beheren voordat een gebruiker een route verlaat, zoals het controleren op niet-opgeslagen wijzigingen.
- **`DidLeaveObserver`**: Wordt gebruikt voor opruimacties of andere taken die moeten worden uitgevoerd nadat een component van de DOM is losgekoppeld.

## Voorbeeld: authenticatie met `WillEnterObserver` {#example-authentication-with-willenterobserver}

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

Hier controleert `onWillEnter` of de gebruiker geauthenticeerd is. Als dit niet het geval is, wordt de navigatie verworpen, waardoor de navigatie niet volledig kan worden uitgevoerd en in plaats daarvan wordt doorgestuurd naar de aanmeldpagina.

:::warning Voorbeeld van Geauthenticeerde Routes - Niet Geschikt voor Productie
Dit voorbeeld is slechts een demonstratie van hoe geauthenticeerde routes te gebruiken.
Dit **is geen** voorbeeld van hoe je een authenticatiesysteem op productie-niveau zou schrijven.
Je moet de concepten en patronen die in dit voorbeeld worden gebruikt, aanpassen om te werken met jouw authenticatiestroom/systeem voor jouw app.
:::

## Voorbeeld: gegevens ophalen bij het inloggen met `DidEnterObserver` {#example-fetching-data-on-route-entry-with-didenterobserver}

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
    // Code om de UI bij te werken met profielgegevens
  }
}
```

Dit voorbeeld laat zien hoe `DidEnterObserver` wordt gebruikt om profielgegevens op te halen en weer te geven zodra het component aan de DOM is bevestigd.

## Voorbeeld: Afhandelen van niet-opgeslagen wijzigingen met `WillLeaveObserver` {#example-handling-unsaved-changes-with-willleaveobserver}

```java
@Route(value = "edit-profile")
public class EditProfileView extends Composite<Div> implements WillLeaveObserver {
  private boolean hasUnsavedChanges = false;

  public EditProfileView() {
    // Logica om niet-opgeslagen wijzigingen te detecteren
  }

  @Override
  public void onWillLeave(WillLeaveEvent event, ParametersBag parameters) {
    event.veto(hasUnsavedChanges);

    if(hasUnsavedChanges) {
      ConfirmDialog.Result result = showConfirmDialog(
          "Er zijn niet-opgeslagen wijzigingen. Wil je ze negeren of opslaan?",
          "Niet-opgeslagen Wijzigingen",
          ConfirmDialog.OptionType.OK_CANCEL,
          ConfirmDialog.MessageType.WARNING);
    }
  }
}
```

In dit voorbeeld vraagt `onWillLeave` de gebruiker om een bevestigingsdialoog als er niet-opgeslagen wijzigingen zijn, waarbij de navigatie wordt verworpen als de gebruiker ervoor kiest om te blijven.

:::info Navigatie blokkeren en Veto-afhandeling
Voor meer informatie over het blokkeren van navigatie, zie [Navigatie Blokkeren en Veto-afhandeling](./navigation-blocking)
:::

## Voorbeeld: Opruimen met `DidLeaveObserver` {#example-cleanup-with-didleaveobserver}

```java
@Route(value = "notifications")
public class NotificationsView extends Composite<Div> implements DidLeaveObserver {

  @Override
  public void onDidLeave(DidLeaveEvent event, ParametersBag parameters) {
    NotificationService.clearActiveNotifications();
  }
}
```

Dit voorbeeld verwijdert meldingen nadat de gebruiker de `NotificationsView` verlaat, met gebruik van de `DidLeaveObserver` voor opruimen.
