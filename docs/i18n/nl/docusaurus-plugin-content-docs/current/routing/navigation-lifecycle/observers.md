---
sidebar_position: 2
title: Lifecycle Observers
_i18n_hash: 18390849527056ed2780b761ae7919c1
---
Observers stellen componenten in staat te reageren op levenscyclusgebeurtenissen door interfaces voor specifieke stadia te implementeren. Dit patroon zorgt voor een schone scheiding van verantwoordelijkheden en vereenvoudigt het omgaan met navigatielogica.

## Beschikbare observers {#available-observers}

- **`WillEnterObserver`**: Hiermee kunt u taken afhandelen voordat een route wordt betreden, zoals het ophalen van noodzakelijke gegevens of het blokkeren van navigatie.
- **`DidEnterObserver`**: Ideaal voor het afhandelen van acties nadat de component is bevestigd, zoals het weergeven van gegevens of het activeren van animaties.
- **`WillLeaveObserver`**: Biedt een manier om logica te beheren voordat een gebruiker een route verlaat, zoals het controleren van niet-opgeslagen wijzigingen.
- **`DidLeaveObserver`**: Wordt gebruikt voor opschoonacties of andere taken die moeten worden uitgevoerd nadat een component is losgekoppeld van de DOM.
- **`ActivateObserver`** (sinds 25.03): Geactiveerd wanneer een gecachete component opnieuw wordt geactiveerd, zoals wanneer genavigeerd wordt naar dezelfde route met andere parameters.

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

Hier controleert `onWillEnter` of de gebruiker is geauthenticeerd. Als dat niet het geval is, wordt de navigatie verworpen, waardoor de navigatie niet wordt voltooid en in plaats daarvan wordt doorgestuurd naar de inlogpagina.

:::warning Voorbeeld van Geauthentiseerde Routes - Niet Klaar voor Productie
Dit vorige is slechts een voorbeeld van hoe geauthentiseerde routes te gebruiken.
Dit **Is geen** voorbeeld van hoe je een productieniveau-authenticatiesysteem zou schrijven.
Je zult de concepten en patronen die in dit voorbeeld worden gebruikt moeten aanpassen om te werken met jouw authenticatiestroom/systeem voor jouw app.
:::

## Voorbeeld: gegevens ophalen bij route-invoer met `DidEnterObserver` {#example-fetching-data-on-route-entry-with-didenterobserver}

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
          "Er zijn niet-opgeslagen wijzigingen. Wilt u ze negeren of opslaan?",
          "Niet-opgeslagen Wijzigingen",
          ConfirmDialog.OptionType.OK_CANCEL,
          ConfirmDialog.MessageType.WARNING);
    }
  }
}
```

In dit voorbeeld vraagt `onWillLeave` de gebruiker om een bevestigingsdialoog als er niet-opgeslagen wijzigingen zijn, waarbij de navigatie wordt verworpen als de gebruiker ervoor kiest om te blijven.

:::info Navigatie Blokkeren en Veto Afhandeling
Voor meer informatie over het blokkeren van navigatie, zie [Navigatie Blokkeren en Veto Afhandeling](./navigation-blocking)
:::

## Voorbeeld: Opschonen met `DidLeaveObserver` {#example-cleanup-with-didleaveobserver}

```java
@Route(value = "notifications")
public class NotificationsView extends Composite<Div> implements DidLeaveObserver {

  @Override
  public void onDidLeave(DidLeaveEvent event, ParametersBag parameters) {
    NotificationService.clearActiveNotifications();
  }
}
```

Dit voorbeeld verwijdert meldingen nadat de gebruiker de `NotificationsView` verlaat, met behulp van de `DidLeaveObserver` voor opschoning.

## Voorbeeld: Gegevens vernieuwen met `ActivateObserver` {#example-refreshing-data-with-activateobserver}

:::info Sinds 25.03
De `ActivateObserver` en `ActivateEvent` zijn beschikbaar vanaf webforJ versie `25.03`.
:::

```java
@Route(value = "product/:id")
public class ProductView extends Composite<Div> implements ActivateObserver {
  private String currentProductId;

  @Override
  public void onActivate(ActivateEvent event, ParametersBag parameters) {
    String productId = parameters.get("id").orElseThrow();
    
    // De component wordt hergebruikt met andere parameters
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

Dit voorbeeld demonstreert het gebruik van `ActivateObserver` om gegevens te vernieuwen bij het navigeren naar dezelfde route met andere parameters. De component blijft gecached en wordt geactiveerd in plaats van opnieuw gemaakt, zodat de UI wordt bijgewerkt om de juiste gegevens voor de huidige parameters weer te geven zonder een nieuwe component te instantiëren.

:::tip Activatie in Componenthiërarchieën
Bij het navigeren naar een route wordt het `Activate` evenement geactiveerd voor **alle gecachete componenten in de hiërarchie** die in het huidige pad blijven. Bijvoorbeeld, wanneer genavigeerd wordt van `/products/123` naar `/products/456`, ontvangen zowel de bovenliggende `ProductsLayout` component als de onderliggende `ProductView` component het `Activate` evenement als ze gecached zijn en in de routehiërarchie blijven.
:::
