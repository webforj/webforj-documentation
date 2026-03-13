---
sidebar_position: 4
title: Route Navigation
_i18n_hash: 103905bf14bb1fe9f4813dfa26fd6828
---
In webforJ is navigeren tussen routes de kernmechanisme voor het wisselen van weergaven en componenten op basis van gebruikersacties of URL-wijzigingen. Navigatie stelt gebruikers in staat om naadloos tussen verschillende delen van de app te bewegen zonder de pagina te verversen. Deze client-side navigatie houdt de app responsief en soepel terwijl de staat van de app behouden blijft.

## Programmatic navigation {#programmatic-navigation}

Je kunt navigatie vanuit elke plek in je app triggeren door gebruik te maken van de `Router` klasse. Dit maakt dynamische wijzigingen in de getoonde componenten mogelijk op basis van gebeurtenissen zoals knoppen klikken of andere gebruikersinteracties.

Hier is een voorbeeld van hoe je naar een specifieke route navigeert:

```java
@Route(value = "dashboard")
public class DashboardView extends Composite<Div> {
  // Componentlogica hier
}
```

```java
// navigeer naar de view
Router.getCurrent().navigate(DashboardView.class);
```

In dit voorbeeld zorgt het programmatig navigeren naar de `DashboardView` component ervoor dat de `DashboardView` component wordt weergegeven en de URL van de browser wordt bijgewerkt naar `/dashboard`.

Het is ook mogelijk om naar de view te navigeren door een nieuwe `Location` door te geven.

```java
Router.getCurrent().navigate(new Location("/dashboard"));
```

:::tip Klasse vs. Locatie: Methoden voor View Routing
Wanneer je tussen views navigeert, hebben ontwikkelaars twee opties: ze kunnen ofwel de view- of routeklasse doorgeven, waardoor de router automatisch de URL genereert en de view weergeeft, of de locatie direct doorgeven. Beide methoden zijn valide, maar **het gebruik van de viewklasse is de aanbevolen aanpak** omdat het betere flexibiliteit biedt voor toekomstige wijzigingen. Als je bijvoorbeeld later besluit om de route bij te werken, hoef je alleen de `@Route` annotatie aan te passen, zonder code te veranderen die de viewklasse voor navigatie gebruikt.
:::

### Navigatie met parameters {#navigation-with-parameters}

Wanneer je parameters samen met de route moet doorgeven, stelt webforJ je in staat om parameters in de URL in te voegen. Hier is hoe je naar een route met parameters kunt navigeren:

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
// navigeer naar view en geef de gebruikers id door
Router.getCurrent().navigate(
  UserProfileView.class,
  ParametersBag.of("id=JohnDoe")
);
```

Dit navigeert naar `/user/JohnDoe`, waar `JohnDoe` een gebruikers-ID kan vertegenwoordigen. De component voor deze route kan vervolgens de parameter extraheren en deze dienovereenkomstig gebruiken.

## Gemaakt view instantie {#created-view-instance}

De `navigate` methode accepteert een Java `Consumer` die wordt aangeroepen zodra de navigatie is voltooid. De `Consumer` ontvangt de instantie van de gemaakte viewcomponent, gewikkeld in een Java `Optional`, zodat de ontwikkelaar kan interageren met de view na een succesvolle navigatie.

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
De consumer ontvangt een Java `Optional` voor de component omdat deze mogelijk `null` is of om verschillende redenen niet is gemaakt. Bijvoorbeeld, de component wordt mogelijk niet weergegeven als de navigatie-observers de navigatie vetoën en het proces stoppen.
:::

## Navigatie-opties {#navigation-options}

De `NavigationOptions` klasse stelt ontwikkelaars in staat om nauwkeurig te bepalen hoe navigatie binnen de app wordt behandeld. Door specifieke opties in te stellen, kun je het gedrag van navigatie controleren, zoals of de geschiedenis van de browser moet worden bijgewerkt, lifecycle-observers moeten worden aangeroepen, of zelfs navigatie-evenementen moeten worden geactiveerd.

```java
NavigationOptions options = new NavigationOptions();
options.setUpdateHistory(false);

Router.getCurrent().navigate(
  new Location("user/JohnDoe"), options);
```

### Instellen van navigatie-opties {#setting-navigation-options}

De `NavigationOptions` klasse biedt verschillende methoden voor het aanpassen van het navigatiegedrag. Deze omvatten het beheersen van hoe routes worden afgehandeld, of observers op de hoogte worden gesteld, en hoe de geschiedenis van de browser wordt bijgewerkt.

Hier zijn de belangrijkste configuratieopties die beschikbaar zijn binnen `NavigationOptions`:

1. **Navigatietype (`setNavigationType`)**  
   Deze optie definieert of de nieuwe route moet worden toegevoegd aan de geschiedenis van de browser of de huidige route moet vervangen.

   - **`PUSH`**: Voegt de nieuwe route toe aan de geschiedenisstack en behoudt de huidige locatie.
   - **`REPLACE`**: Vervangt de huidige route in de geschiedenisstack met de nieuwe locatie, waardoor de terugknop niet naar de vorige route leidt.

2. **Vuur evenementen (`setFireEvents`)**  
   Bepaalt of navigatie [lifecycle-evenementen](./navigation-lifecycle/navigation-events) tijdens navigatie moeten worden geactiveerd. Standaard is dit ingesteld op `true`, en worden evenementen geactiveerd. Als het is ingesteld op `false`, worden er geen evenementen geactiveerd, wat handig is voor stille navigatie.

3. **Steun observers (`setInvokeObservers`)**  
   Deze vlag controleert of de navigatie [observers](./navigation-lifecycle/observers) binnen de genavigeerde componenten moet activeren. Observers behandelen doorgaans gebeurtenissen zoals toegang tot of exit van routes. Dit op `false` zetten voorkomt dat observers worden geactiveerd.

4. **Update geschiedenis (`setUpdateHistory`)**  
   Wanneer ingesteld op `false`, voorkomt deze optie dat de geschiedenislocatie wordt bijgewerkt. Dit is handig wanneer je de view wilt wijzigen zonder de terug- of voordeeldelingen van de browser te beïnvloeden. Het beïnvloedt alleen het beheer van de geschiedenis, niet de lifecycle van de component of het afhandelen van routes.

5. **Toestandobject (`setState`)**  
   [Het toestandobject](./state-management#saving-and-restoring-state-in-browser-history) stelt je in staat om aanvullende informatie door te geven bij het bijwerken van de geschiedenis van de browser. Dit object wordt opgeslagen in de status van de geschiedenis van de browser en kan later voor aangepaste doeleinden worden gebruikt, zoals het opslaan van de toestand van de app tijdens navigatie.

## Locaties genereren voor views {#generating-locations-for-views}

De router kan de locatie voor views genereren op basis van het routepatroon dat in de view is gedefinieerd. Je kunt ook aanvullende parameters voor dynamische en vereiste segments in de URL opgeven. Dit kan nuttig zijn bij het construeren van links of het delen van directe toegangspunten naar specifieke views in de app.

Hier is hoe je een `Location` kunt genereren op basis van een viewklasse en routeparameters:

```java
Class<UserProfileView> userProfileView = UserProfileView.class;
ParametersBag params = ParametersBag.of("id=JohnDoe");

Optional<Location> location = Router.getCurrent().getLocation(userProfileView, params);
console().log(location.get());
```

Dit genereert een `Location` object met het pad `/user/JohnDoe`, de volledige URI als een string.
