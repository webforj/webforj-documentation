---
sidebar_position: 4
title: Route Navigation
_i18n_hash: cf1f9e79aa81f240306313a7c0c5a9c4
---
In webforJ is navigeren tussen routes de kernmechanisme voor het wisselen van weergaven en componenten op basis van gebruikersacties of URL-wijzigingen. Navigatie stelt gebruikers in staat om naadloos tussen verschillende delen van de app te bewegen zonder de pagina te verversen. Deze client-side navigatie houdt de app responsief en soepel, terwijl de staat van de app behouden blijft.

## Programmatic navigation {#programmatic-navigation}

Je kunt navigatie vanaf elke plek in je app activeren met behulp van de `Router` klasse. Dit maakt dynamische wijzigingen in de weergegeven componenten mogelijk op basis van evenementen zoals knopklik of andere gebruikersinteracties.

Hier is een voorbeeld van hoe je naar een specifieke route kunt navigeren:

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

In dit voorbeeld leidt het programmatig navigeren naar de `DashboardView` component ertoe dat de `DashboardView` component wordt gerenderd en de URL van de browser wordt bijgewerkt naar `/dashboard`.

Het is ook mogelijk om naar de weergave te navigeren door een nieuwe `Location` door te geven.

```java
Router.getCurrent().navigate(new Location("/dashboard"));
```

:::tip Class vs. Location: Methoden voor View Routing
Bij het navigeren tussen weergaven hebben ontwikkelaars twee opties: ze kunnen ofwel de view- of routeklasse doorgeven, waardoor de router automatisch de URL genereert en de weergave weergeeft, of de locatie direct doorgeven. Beide methoden zijn geldig, maar **het gebruik van de viewklasse is de aanbevolen benadering** omdat het betere flexibiliteit biedt voor toekomstige wijzigingen. Bijvoorbeeld, als je later besluit de route bij te werken, hoef je alleen de `@Route` annotatie te wijzigen, zonder enige code die de viewklasse voor navigatie gebruikt aan te passen.
:::

### Navigation with parameters {#navigation-with-parameters}

Wanneer je parameters samen met de route moet doorgeven, stelt webforJ je in staat om parameters in de URL te embedden. Hier is hoe je naar een route met parameters kunt navigeren:

```java
@Route("user/:id")
public class UserProfileView extends Composite<Div> implements DidEnterObserver {
  H1 title = new H1();

  public UserProfileView() {
    getBoundComponent().add(title);
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
// navigeer naar de weergave en geef de gebruikers-ID door
Router.getCurrent().navigate(
  UserProfileView.class,
  ParametersBag.of("id=JohnDoe")
);
```

Dit navigeert naar `/user/JohnDoe`, waar `JohnDoe` misschien een gebruikers-ID vertegenwoordigt. De component voor deze route kan de parameter extraheren en deze dienovereenkomstig gebruiken.

## Created view instance {#created-view-instance}

De `navigate` methode accepteert een Java `Consumer` die wordt aangeroepen zodra de navigatie is voltooid. De `Consumer` ontvangt de instantie van de gemaakte viewcomponent, omgeven door een java `Optional`, waardoor de ontwikkelaar met de weergave kan interageren na een succesvolle navigatie.

```java
Router.getCurrent().navigate(
    UserProfileView.class,
    ParametersBag.of("id=JohnDoe"), (component) -> {
      component.ifPresent(view -> {
        console().log("De nieuwe titel is: " + view.getTitle());
      });
    });
```

:::info Null-instanties
De consument ontvangt een Java `Optional` voor de component omdat deze `null` kan zijn of om verschillende redenen niet is gemaakt. Bijvoorbeeld, de component wordt mogelijk niet gerenderd als de navigatie-observers de navigatie vetoën en het proces stoppen.
:::

## Navigation options {#navigation-options}

De `NavigationOptions` klasse stelt ontwikkelaars in staat om precies te bepalen hoe navigatie binnen de app wordt behandeld. Door specifieke opties in te stellen, kun je het gedrag van navigatie controleren, zoals het al dan niet bijwerken van de browsergeschiedenis, het aanroepen van levenscyclusobservatoren of zelfs het uitlokken van navigatie-evenementen.

```java
NavigationOptions options = new NavigationOptions();
options.setUpdateHistory(false);

Router.getCurrent().navigate(
  new Location("user/JohnDoe"), options);
```

### Setting navigation options {#setting-navigation-options}

De `NavigationOptions` klasse biedt verschillende methoden voor het aanpassen van het navigatiegedrag. Deze omvatten het controleren hoe routes worden behandeld, of observatoren worden geïnformeerd, en hoe de geschiedenis van de browser wordt bijgewerkt.

Hier zijn de belangrijkste configuratieopties beschikbaar binnen `NavigationOptions`:

1. **Navigatietype (`setNavigationType`)**  
   Deze optie definieert of de nieuwe route aan de browsergeschiedenis moet worden toegevoegd of de huidige route moet vervangen.

   - **`PUSH`**: Voegt de nieuwe route toe aan de geschiedenisstack, waarbij de huidige locatie behouden blijft.
   - **`REPLACE`**: Vervangt de huidige route in de geschiedenisstack met de nieuwe locatie, waardoor de terugknop niet naar de vorige route kan navigeren.

2. **Vuur evenementen (`setFireEvents`)**  
   Bepaalt of navigatie [levenscyclus evenementen](./navigation-lifecycle/navigation-events) moeten worden uitgelokt tijdens navigatie. Standaard is dit ingesteld op `true`, en worden evenementen uitgelokt. Als dit is ingesteld op `false`, worden er geen evenementen uitgelokt, wat nuttig is voor stille navigatie.

3. **Observer aanroepen (`setInvokeObservers`)**  
   Deze vlag controleert of de navigatie [observers](./navigation-lifecycle/observers) binnen de genavigeerde componenten moet activeren. Observatoren behandelen doorgaans evenementen zoals route-ingang of -uitgang. Dit op `false` instellen voorkomt dat observers worden aangeroepen.

4. **Geschiedenis bijwerken (`setUpdateHistory`)**  
   Wanneer ingesteld op `false`, voorkomt deze optie dat de geschiedenislocatie wordt bijgewerkt. Dit is nuttig wanneer je de weergave wilt wijzigen zonder de achterwaartse of voorwaartse navigatie van de browser te beïnvloeden. Het beïnvloedt alleen het geschiedenbeheer, niet de levenscyclus van de component of routebehandeling.

5. **Statusobject (`setState`)**  
   [Het statusobject](./state-management#saving-and-restoring-state-in-browser-history) stelt je in staat om aanvullende informatie door te geven wanneer de geschiedenis van de browser wordt bijgewerkt. Dit object wordt opgeslagen in de status van de browsergeschiedenis en kan later voor aangepaste doeleinden worden gebruikt, zoals het opslaan van de status van de app tijdens navigatie.

## Generating locations for views {#generating-locations-for-views}

De router kan de locatie voor weergaven genereren op basis van het routepatroon dat in de weergave is gedefinieerd. Je kunt ook aanvullende parameters voor dynamische en vereiste segmenten in de URL opgeven. Dit kan nuttig zijn bij het construeren van koppelingen of het delen van directe toegangspunten naar specifieke weergaven in de app.

Hier is hoe je een `Location` kunt genereren op basis van een viewklasse en routeparameters:

```java
Class<UserProfileView> userProfileView = UserProfileView.class;
ParametersBag params = ParametersBag.of("id=JohnDoe");

Optional<Location> location = Router.getCurrent().getLocation(userProfileView, params);
console().log(location.get());
```

Dit genereert een `Location` object met het pad `/user/JohnDoe`, de volledige URI als een string.
