---
sidebar_position: 4
title: Route Navigation
_i18n_hash: 91739f35b8d47f6e90e276623864aac4
---
In webforJ is navigeren tussen routes de kernmechanisme voor het wisselen van weergaven en componenten op basis van gebruikersacties of URL-wijzigingen. Navigatie stelt gebruikers in staat om naadloos tussen verschillende delen van de app te bewegen zonder de pagina te verversen. Deze client-side navigatie houdt de app responsief en soepel, terwijl de toestand van de app behouden blijft.

## Programmaticale navigatie {#programmatic-navigation}

U kunt navigatie vanuit elke plek in uw app activeren door de `Router`-klasse te gebruiken. Dit maakt dynamische wijzigingen in de weergegeven componenten mogelijk op basis van gebeurtenissen zoals knoppen klikken of andere gebruikersinteracties.

Hier is een voorbeeld van hoe u naar een specifieke route kunt navigeren:

```java
@Route(value = "dashboard")
public class DashboardView extends Composite<Div> {
  // Component logica hier
}
```

```java
// navigeer naar de weergave
Router.getCurrent().navigate(DashboardView.class);
```

In dit voorbeeld zorgt het programmatica navigeren naar de `DashboardView`-component ervoor dat de `DashboardView`-component wordt weergegeven en de URL van de browser wordt bijgewerkt naar `/dashboard`.

Het is ook mogelijk om naar de weergave te navigeren door een nieuwe `Location` door te geven:

```java
Router.getCurrent().navigate(new Location("/dashboard"));
```

:::tip Klasse vs. Locatie: Methoden voor Weergave Routing
Bij het navigeren tussen weergaven hebben ontwikkelaars twee opties: ze kunnen de weergave of routeklasse doorgeven, waardoor de router automatisch de URL genereert en de weergave weergeeft, of de locatie rechtstreeks doorgeven. Beide methoden zijn geldig, maar **het gebruik van de weergaveklasse is de favoriete benadering** omdat het meer flexibiliteit biedt voor toekomstige wijzigingen. Als u bijvoorbeeld later besluit om de route bij te werken, hoeft u alleen de `@Route`-annotatie te wijzigen, zonder dat u enige code hoeft te veranderen die de weergaveklasse voor navigatie gebruikt.
:::

### Navigatie met parameters {#navigation-with-parameters}

Wanneer u parameters samen met de route wilt doorgeven, stelt webforJ u in staat om parameters in de URL in te voegen. Hier leest u hoe u naar een route met parameters kunt navigeren:

```java
@Route("user/:id")
public class UserProfileView extends Composite<Div> implements DidEnterObserver {
  private final Div self = getBoundComponent();
  H1 title = new H1();

  public UserProfileView() {
    self.add(title);
  }

  public void setTile(String title) {
    this.title.setText(title);
  }

  public String getTitle() {
    return title.getText();
  }

  @Override
  public void onDidEnter(DidEnterEvent event, ParametersBag parameters) {
    String id = parameters.getAlpha("id").orElse("Onbekend");
    setTile(id);
  }
}
```

```java
// navigeer naar weergave en geef de gebruikers-id door
Router.getCurrent().navigate(
  UserProfileView.class,
  ParametersBag.of("id=JohnDoe")
);
```

Dit navigeert naar `/user/JohnDoe`, waar `JohnDoe` mogelijk een gebruikers-ID vertegenwoordigt. De component voor deze route kan de parameter vervolgens extraheren en deze dienovereenkomstig gebruiken.

## Gemaakte weergave-instantie {#created-view-instance}

De `navigate`-methode accepteert een Java `Consumer` die wordt aangeroepen zodra de navigatie is voltooid. De `Consumer` ontvangt de instantie van de gemaakte weergavecomponent, verpakt in een java `Optional`, zodat de ontwikkelaar met de weergave kan interageren na een succesvolle navigatie.

```java
Router.getCurrent().navigate(
  UserProfileView.class,
  ParametersBag.of("id=JohnDoe"), (component) -> {
    component.ifPresent(view -> {
      console().log("De nieuwe titel is: " + view.getTitle());
    });
  });
```

:::info Nul-instanties
De consumer ontvangt een Java `Optional` voor de component omdat deze `null` kan zijn, of om verschillende redenen niet is gemaakt. Bijvoorbeeld, de component wordt mogelijk niet weergegeven als de navigatie-observators de navigatie vetoën en het proces stoppen.
:::

## Navigatieopties {#navigation-options}

De `NavigationOptions`-klasse stelt ontwikkelaars in staat om de manier waarop navigatie binnen de app wordt afgehandeld fijn af te stemmen. Door specifieke opties in te stellen, kunt u het gedrag van de navigatie controleren, zoals of de geschiedenis van de browser moet worden bijgewerkt, levenscyclusobservatoren moeten worden aangeroepen of zelfs navigatie evenementen moeten worden geactiveerd.

```java
NavigationOptions options = new NavigationOptions();
options.setUpdateHistory(false);

Router.getCurrent().navigate(
  new Location("user/JohnDoe"), options);
```

### Instellen van navigatie-opties {#setting-navigation-options}

De `NavigationOptions`-klasse biedt verschillende methoden voor het aanpassen van het navigatiegedrag. Deze omvatten het controleren van hoe routes worden afgehandeld, of observatoren worden geïnformeerd en hoe de geschiedenis van de browser wordt bijgewerkt.

Hier zijn de belangrijkste configuratieopties beschikbaar binnen `NavigationOptions`:

1. **Navigatietype (`setNavigationType`)**  
   Deze optie definieert of de nieuwe route aan de geschiedenis van de browser moet worden toegevoegd of de huidige route moet vervangen.

   - **`PUSH`**: Voegt de nieuwe route toe aan de geschiedenisstack en behoudt de huidige locatie.
   - **`REPLACE`**: Vervangt de huidige route in de geschiedenisstack met de nieuwe locatie, waardoor de knop terug niet naar de vorige route kan navigeren.

2. **Vuur evenementen (`setFireEvents`)**  
   Bepaalt of navigatie [levenscyclus evenementen](./navigation-lifecycle/navigation-events) moeten worden aangeroepen tijdens navigatie. Standaard is dit ingesteld op `true`, en evenementen worden geactiveerd. Als ingesteld op `false`, worden er geen evenementen geactiveerd, wat nuttig is voor stille navigatie.

3. **Roep observatoren aan (`setInvokeObservers`)**  
   Deze vlag bepaalt of de navigatie observatoren binnen de genavigeerde componenten moet aanroepen. Observatoren behandelen meestal evenementen zoals route-ingang of -uitgang. Dit instellen op `false` voorkomt dat observatoren worden aangeroepen.

4. **Geschiedenis bijwerken (`setUpdateHistory`)**  
   Wanneer ingesteld op `false`, voorkomt deze optie dat de geschiedenislocatie wordt bijgewerkt. Dit is nuttig wanneer u de weergave wilt wijzigen zonder de navigatie van de browser terug of vooruit te beïnvloeden. Het beïnvloedt alleen het beheer van de geschiedenis, niet de levenscyclus van de component of route-afhandeling.

5. **Statusobject (`setState`)**  
   [Het statusobject](./state-management#saving-and-restoring-state-in-browser-history) stelt u in staat om aanvullende informatie door te geven bij het bijwerken van de geschiedenis van de browser. Dit object wordt opgeslagen in de status van de browsergeschiedenis en kan later voor aangepaste doeleinden worden gebruikt, zoals het opslaan van de status van de app tijdens de navigatie.

## Locaties genereren voor weergaven {#generating-locations-for-views}

De router kan de locatie voor weergaven genereren op basis van het routepatroon dat in de weergave is gedefinieerd. U kunt ook aanvullende parameters opgeven voor dynamische en vereiste segmenten in de URL. Dit kan nuttig zijn bij het construeren van links of het delen van directe toegangen tot specifieke weergaven in de app.

Hier is hoe u een `Location` kunt genereren op basis van een weergaveklasse en routeparameters:

```java
Class<UserProfileView> userProfileView = UserProfileView.class;
ParametersBag params = ParametersBag.of("id=JohnDoe");

Optional<Location> location = Router.getCurrent().getLocation(userProfileView, params);
console().log(location.get());
```

Dit genereert een `Location`-object met het pad `/user/JohnDoe`, de volledige URI als een tekenreeks.
