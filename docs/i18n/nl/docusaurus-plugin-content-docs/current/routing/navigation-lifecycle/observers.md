---
sidebar_position: 2
title: Lifecycle Observers
_i18n_hash: be571bd197730689ba8346b2ef702a3f
---
Observers stellen componenten in staat om te reageren op levenscyclusgebeurtenissen door interfaces te implementeren voor specifieke fasen. Dit patroon zorgt voor een duidelijke scheiding van verantwoordelijkheden en vereenvoudigt het omgaan met navigatielogica.

## Beschikbare observers {#available-observers}

- **`WillEnterObserver`**: Hiermee kun je taken afhandelen voordat een route betreden wordt, zoals het ophalen van noodzakelijke gegevens of het blokkeren van navigatie.
- **`DidEnterObserver`**: Ideaal voor het afhandelen van acties nadat de component is bevestigd, zoals het weergeven van gegevens of het starten van animaties.
- **`WillLeaveObserver`**: Biedt een manier om logica te beheren voordat een gebruiker een route verlaat, zoals het controleren op niet-opgeslagen wijzigingen.
- **`DidLeaveObserver`**: Wordt gebruikt voor opruimacties of andere taken die moeten worden uitgevoerd nadat een component uit de DOM is verwijderd.

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

Hier controleert `onWillEnter` of de gebruiker is geauthenticeerd. Zo niet, dan wordt de navigatie verworpen, waardoor de navigatie niet wordt voltooid en in plaats daarvan naar de inlogpagina wordt omgeleid.

:::warning Voorbeeld van Geauthenticeerde Routes - Niet Klaar voor Productie
Dit vorige is slechts een voorbeeld van hoe je geauthenticeerde routes kunt gebruiken.
Dit **Is geen** voorbeeld van hoe je een authenticatiesysteem op productieniveau zou schrijven.
Je moet de concepten en patronen die in dit voorbeeld worden gebruikt, aanpassen voor jouw authenticatiestroom/systeem voor jouw app.
:::

## Voorbeeld: gegevens ophalen bij het betreden van een route met `DidEnterObserver` {#example-fetching-data-on-route-entry-with-didenterobserver}

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

Dit voorbeeld demonstreert het gebruik van `DidEnterObserver` om profielgegevens op te halen en weer te geven zodra de component aan de DOM is bevestigd.

## Voorbeeld: Ongeslagen wijzigingen afhandelen met `WillLeaveObserver` {#example-handling-unsaved-changes-with-willleaveobserver}

```java
@Route(value = "edit-profile")
public class EditProfileView extends Composite<Div> implements WillLeaveObserver {
  private boolean hasUnsavedChanges = false;

  public EditProfileView() {
    // Logica om onopgeslagen wijzigingen te detecteren
  }

  @Override
  public void onWillLeave(WillLeaveEvent event, ParametersBag parameters) {
    event.veto(hasUnsavedChanges);

    if(hasUnsavedChanges) {
      ConfirmDialog.Result result = showConfirmDialog(
          "Er zijn onopgeslagen wijzigingen. Wil je ze Negeer of Opslaan?",
          "Ongeslagen Wijzigingen",
          ConfirmDialog.OptionType.OK_CANCEL,
          ConfirmDialog.MessageType.WARNING);
    }
  }
}
```

In dit voorbeeld vraagt `onWillLeave` de gebruiker om bevestiging via een dialoogvenster als er onopgeslagen wijzigingen zijn, waarbij de navigatie wordt verworpen als de gebruiker ervoor kiest om te blijven.

:::info Navigatieblokkering en Veto-afhandeling
Voor meer informatie over het blokkeren van navigatie, zie [Navigatieblokkering en Veto-afhandeling](./navigation-blocking)
:::

## Voorbeeld: Opruiming met `DidLeaveObserver` {#example-cleanup-with-didleaveobserver}

```java
@Route(value = "notifications")
public class NotificationsView extends Composite<Div> implements DidLeaveObserver {

  @Override
  public void onDidLeave(DidLeaveEvent event, ParametersBag parameters) {
    NotificationService.clearActiveNotifications();
  }
}
```

Dit voorbeeld wist meldingen nadat de gebruiker de `NotificationsView` verlaat, en gebruikt de `DidLeaveObserver` voor opruiming.
