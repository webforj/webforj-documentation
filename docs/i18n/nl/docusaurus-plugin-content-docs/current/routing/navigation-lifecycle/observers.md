---
sidebar_position: 2
title: Lifecycle Observers
description: >-
  Hook into route lifecycle stages by implementing WillEnter, DidEnter,
  WillLeave, DidLeave, and Activate observer interfaces.
_i18n_hash: 3f39161991064d0d2506c0cb1dcd3503
---
Observers stellen componenten in staat te reageren op levenscyclusgebeurtenissen door interfaces te implementeren voor specifieke fasen. Dit patroon zorgt voor een schone scheiding van verantwoordelijkheden en vereenvoudigt de afhandeling van navigatielogica.

## Beschikbare observers {#available-observers}

- **`WillEnterObserver`**: stelt je in staat om taken af te handelen voordat een route wordt betreden, zoals het ophalen van benodigde gegevens of het blokkeren van navigatie.
- **`DidEnterObserver`**: Ideaal voor het afhandelen van acties nadat de component is gekoppeld, zoals het weergeven van gegevens of het starten van animaties.
- **`WillLeaveObserver`**: biedt een manier om logica te beheren voordat een gebruiker een route verlaat, zoals het controleren op niet-opgeslagen wijzigingen.
- **`DidLeaveObserver`**: wordt gebruikt voor opruimacties of andere taken die moeten worden uitgevoerd nadat een component is losgekoppeld van de DOM.
- **`ActivateObserver`**: <DocChip chip='since' label='25.03' /> Wordt getriggerd wanneer een gecachete component opnieuw geactiveerd wordt, zoals bij navigeren naar dezelfde route met andere parameters.

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

Hier controleert `onWillEnter` of de gebruiker is geauthenticeerd. Als dit niet het geval is, wordt de navigatie tegengehouden, waardoor de navigatie incompleet blijft en in plaats daarvan naar de inlogpagina wordt doorgestuurd.

:::warning Voorbeeld van Geauthenticeerde Routes - Niet Geschikt voor Productie
Dit is alleen een voorbeeld van hoe geauthenticeerde routes te gebruiken.
Dit **is geen** voorbeeld van hoe je een authenticatiesysteem op productieniveau zou schrijven.
Je moet de concepten en patronen die in dit voorbeeld zijn gebruikt aanpassen om te werken met jouw authenticatiestroom/systeem voor je app.
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

Dit voorbeeld demonstreert het gebruik van `DidEnterObserver` om profielgegevens op te halen en weer te geven zodra de component aan de DOM is gekoppeld.

## Voorbeeld: Omgaan met niet-opgeslagen wijzigingen met `WillLeaveObserver` {#example-handling-unsaved-changes-with-willleaveobserver}

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

In dit voorbeeld vraagt `onWillLeave` de gebruiker om een bevestigingsdialoog als er niet-opgeslagen wijzigingen zijn, waarbij de navigatie wordt tegengehouden als de gebruiker ervoor kiest om te blijven.

:::info Navigatieblokkering en Veto-behandeling
Voor meer informatie over het blokkeren van navigatie, zie [Navigatieblokkering en Veto-behandeling](./navigation-blocking)
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

Dit voorbeeld wist notificaties nadat de gebruiker de `NotificationsView` verlaat, waarbij `DidLeaveObserver` wordt gebruikt voor opruiming.

## Voorbeeld: Gegevens vernieuwen met `ActivateObserver` <DocChip chip='since' label='25.03' /> {#example-refreshing-data-with-activateobserver}

```java
@Route(value = "product/:id")
public class ProductView extends Composite<Div> implements ActivateObserver {
  private String currentProductId;

  @Override
  public void onActivate(ActivateEvent event, ParametersBag parameters) {
    String productId = parameters.get("id").orElseThrow();

    // Component wordt hergebruikt met andere parameters
    if (!productId.equals(currentProductId)) {
      currentProductId = productId;
      refreshProductData(productId);
    }
  }

  private void refreshProductData(String productId) {
    // Code om nieuwe productgegevens op te halen en weer te geven
    ProductService.fetchProduct(productId).thenAccept(
        product -> updateProductUI(product));
  }
}
```

Dit voorbeeld demonstreert het gebruik van `ActivateObserver` om gegevens te vernieuwen wanneer je naar dezelfde route met andere parameters navigeert. De component blijft gecached en wordt geactiveerd in plaats van opnieuw aangemaakt, zodat de UI wordt bijgewerkt om de juiste gegevens voor de huidige parameters weer te geven zonder een nieuwe component te instantiëren.

:::tip Activatie in Componenthiërarchieën
Bij het navigeren naar een route wordt de `Activate`-gebeurtenis geactiveerd voor **alle gecachete componenten in de hiërarchie** die in het huidige pad blijven. Bijvoorbeeld, bij het navigeren van `/products/123` naar `/products/456`, ontvangen zowel de bovenliggende `ProductsLayout`-component als de onderliggende `ProductView`-component de `Activate`-gebeurtenis als ze gecached zijn en in de routehiërarchie blijven.
:::
