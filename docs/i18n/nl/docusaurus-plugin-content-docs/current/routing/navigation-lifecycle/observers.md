---
sidebar_position: 2
title: Lifecycle Observers
_i18n_hash: a584e996523ba2b98ecb9d7ab2f366f3
---
Observers stellen componenten in staat om te reageren op levenscyclusgebeurtenissen door interfaces voor specifieke fasen te implementeren. Dit patroon zorgt voor een duidelijke scheiding van verantwoordelijkheden en vereenvoudigt het omgaan met navigatielogica.

## Beschikbare observers {#available-observers}

- **`WillEnterObserver`**: Hiermee kunt u taken afhandelen voordat een route wordt betreden, zoals het ophalen van benodigde gegevens of het blokkeren van navigatie.
- **`DidEnterObserver`**: Ideaal voor het afhandelen van acties nadat de component is bevestigd, zoals het weergeven van gegevens of het activeren van animaties.
- **`WillLeaveObserver`**: Biedt een manier om logica te beheren voordat een gebruiker een route verlaat, zoals het controleren op niet-opgeslagen wijzigingen.
- **`DidLeaveObserver`**: Wordt gebruikt voor opruimacties of andere taken die moeten worden uitgevoerd nadat een component van de DOM is losgekoppeld.
- **`ActivateObserver`**: <DocChip chip='since' label='25.03' /> Geactiveerd wanneer een gecachete component opnieuw wordt geactiveerd, zoals bij navigeren naar dezelfde route met verschillende parameters.

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

Hier controleert `onWillEnter` of de gebruiker is geauthenticeerd. Als dat niet het geval is, wordt de navigatie veto, waardoor de navigatie niet wordt voltooid en in plaats daarvan naar de inlogpagina wordt doorgestuurd.

:::warning Voorbeeld van Geauthenticeerde Routes - Niet Klaar Voor Productie
Dit voorgaande is slechts een voorbeeld van hoe u geauthenticeerde routes kunt gebruiken.
Dit **Is Geen** voorbeeld van hoe u een productieklare authenticatiesysteem zou schrijven.
U moet de concepten en patronen die in dit voorbeeld worden gebruikt, nemen en aanpassen zodat ze werken met uw authenticatiestroom/systeem voor uw app.
:::

## Voorbeeld: gegevens ophalen bij route-ingang met `DidEnterObserver` {#example-fetching-data-on-route-entry-with-didenterobserver}

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

Dit voorbeeld toont aan hoe `DidEnterObserver` wordt gebruikt om profielgegevens op te halen en weer te geven zodra de component aan de DOM is bevestigd.

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
          "Er zijn niet-opgeslagen wijzigingen. Wilt u ze vervallen of opslaan?",
          "Niet-opgeslagen Wijzigingen",
          ConfirmDialog.OptionType.OK_CANCEL,
          ConfirmDialog.MessageType.WARNING);
    }
  }
}
```

In dit voorbeeld vraagt `onWillLeave` de gebruiker om bevestiging via een dialoogvenster als er niet-opgeslagen wijzigingen zijn, waarbij de navigatie wordt verworpen als de gebruiker ervoor kiest om te blijven.

:::info Navigatieblokkering en Veto-afhandeling
Voor meer informatie over het blokkeren van navigatie, zie [Navigatieblokkering en Veto-afhandeling](./navigation-blocking)
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

Dit voorbeeld wist meldingen nadat de gebruiker de `NotificationsView` verlaat, gebruikmakend van de `DidLeaveObserver` voor opruiming.

## Voorbeeld: Gegevens vernieuwen met `ActivateObserver` <DocChip chip='since' label='25.03' /> {#example-refreshing-data-with-activateobserver}

```java
@Route(value = "product/:id")
public class ProductView extends Composite<Div> implements ActivateObserver {
  private String currentProductId;

  @Override
  public void onActivate(ActivateEvent event, ParametersBag parameters) {
    String productId = parameters.get("id").orElseThrow();
    
    // Component wordt hergebruikt met verschillende parameters
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

Dit voorbeeld toont aan hoe `ActivateObserver` wordt gebruikt om gegevens te vernieuwen bij navigeren naar dezelfde route met verschillende parameters. De component blijft gecached en wordt opnieuw geactiveerd in plaats van opnieuw te worden aangemaakt, zodat de UI wordt bijgewerkt om de juiste gegevens voor de huidige parameters weer te geven zonder een nieuwe component te instantieren.

:::tip Activatie in Componenthiërarchieën
Bij navigeren naar een route, wordt de `Activate` gebeurtenis geactiveerd voor **alle gecachete componenten in de hiërarchie** die in het huidige pad blijven. Bijvoorbeeld, wanneer je navigeert van `/products/123` naar `/products/456`, ontvangt zowel de bovenliggende `ProductsLayout` component als de onderliggende `ProductView` component de `Activate` gebeurtenis als ze gecached zijn en in de routen hiërarchie blijven.
:::
