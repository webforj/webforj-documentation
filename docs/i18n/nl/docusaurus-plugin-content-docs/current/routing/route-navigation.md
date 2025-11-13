---
sidebar_position: 4
title: Route Navigatie
_i18n_hash: 2ca468b09b2ae9e2ab3813119d31bf44
---
In webforJ is navigeren tussen routes de kernmechanisme voor het schakelen van weergaven en componenten op basis van gebruikersacties of URL-wijzigingen. Navigatie stelt gebruikers in staat om naadloos tussen verschillende delen van de app te bewegen zonder de pagina te vernieuwen. Deze navigatie aan de clientzijde houdt de app responsief en soepel terwijl de status van de app behouden blijft.

## Programmaticale navigatie {#programmatic-navigation}

Je kunt navigatie vanuit elke plek in je app activeren door de `Router`-klasse te gebruiken. Dit maakt dynamische wijzigingen in de weergegeven componenten mogelijk op basis van gebeurtenissen zoals knoppenklikken of andere gebruikersinteracties.

Hier is een voorbeeld van hoe te navigeren naar een specifieke route:

```java
@Route(value = "dashboard")
public class DashboardView extends Composite<Div> {
  // Componentlogica hier
}
```

```java
// navigeer naar de weergave
Router.getCurrent().navigate(DashboardView.class);
```

In dit voorbeeld zorgt het programmatically navigeren naar de `DashboardView`-component ervoor dat de `DashboardView`-component wordt weergegeven en de URL van de browser wordt bijgewerkt naar `/dashboard`.

Het is ook mogelijk om naar de weergave te navigeren door een nieuwe `Location` door te geven.

```java
Router.getCurrent().navigate(new Location("/dashboard"));
```

:::tip Klasse vs. Locatie: Methoden voor View Routing
Wanneer je tussen weergaven navigeert, hebben ontwikkelaars twee opties: ze kunnen de weergave- of routeklasse doorgeven, waardoor de router automatisch de URL genereert en de weergave weergeeft, of ze kunnen de locatie direct doorgeven. Beide methoden zijn geldig, maar **het gebruik van de weergaveklasse is de voorkeur** omdat het betere flexibiliteit biedt voor toekomstige wijzigingen. Bijvoorbeeld, als je later besluit om de route bij te werken, hoef je alleen de `@Route`-annotatie te wijzigen, zonder dat je code hoeft te veranderen die de weergaveklasse voor navigatie gebruikt.
:::

### Navigatie met parameters {#navigation-with-parameters}

Wanneer je parameters samen met de route moet doorgeven, staat webforJ je toe om parameters in de URL te embedden. Hier is hoe je naar een route met parameters kunt navigeren:

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
// navigeer naar weergave en geef de gebruikers id door
Router.getCurrent().navigate(
  UserProfileView.class,
  ParametersBag.of("id=JohnDoe")
);
```

Dit navigeert naar `/user/JohnDoe`, waar `JohnDoe` mogelijk een gebruikers-ID representeert. De component voor deze route kan dan de parameter extraheren en deze dienovereenkomstig gebruiken.

## Gemaakte weergave-instantie {#created-view-instance}

De `navigate`-methode accepteert een Java `Consumer` die wordt aangeroepen zodra de navigatie is voltooid. De `Consumer` ontvangt de instantie van de gemaakte weergavecomponent, gewikkeld in een java `Optional`, waardoor de ontwikkelaar na succesvolle navigatie met de weergave kan interageren.

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
De consumer ontvangt een Java `Optional` voor de component omdat deze `null` kan zijn, of om verschillende redenen niet is gemaakt. Bijvoorbeeld, de component wordt mogelijk niet weergegeven als de navigatie-observers de navigatie vetoën en het proces stoppen.
:::

## Navigatie-opties {#navigation-options}

De `NavigationOptions`-klasse stelt ontwikkelaars in staat om fijnere aanpassingen te maken aan hoe navigatie wordt behandeld binnen de app. Door specifieke opties in te stellen, kun je het gedrag van navigatie controleren, zoals of de browsergeschiedenis moet worden bijgewerkt, levenscyclusobservatoren moeten worden ingeschakeld of zelfs navigatie-evenementen moeten worden geactiveerd.

```java
NavigationOptions options = new NavigationOptions();
options.setUpdateHistory(false);

Router.getCurrent().navigate(
  new Location("user/JohnDoe"), options);
```

### Instellen van navigatie-opties {#setting-navigation-options}

De `NavigationOptions`-klasse biedt verschillende methoden voor het aanpassen van het navigatiegedrag. Deze omvatten het controleren van hoe routes worden behandeld, of observatoren worden geïnformeerd, en hoe de geschiedenis van de browser wordt bijgewerkt.

Hier zijn de belangrijkste configuratieopties beschikbaar binnen `NavigationOptions`:

1. **Navigatietype (`setNavigationType`)**  
   Deze optie definieert of de nieuwe route aan de browsergeschiedenis moet worden toegevoegd of de huidige route moet vervangen.

   - **`PUSH`**: Voegt de nieuwe route toe aan de geschiedenisstack, waardoor de huidige locatie behouden blijft.
   - **`REPLACE`**: Vervangt de huidige route in de geschiedenisstack met de nieuwe locatie, waardoor de back-knop voorkomt dat naar de vorige route wordt genavigeerd.

2. **Vuur evenementen (`setFireEvents`)**  
   Bepaal of navigatie [levenscyclus evenementen](./navigation-lifecycle/navigation-events) moet worden geactiveerd tijdens navigatie. Standaard staat dit op `true`, en evenementen worden geactiveerd. Als ingesteld op `false`, worden er geen evenementen geactiveerd, wat nuttig is voor stille navigatie.

3. **Observer aanroepen (`setInvokeObservers`)**  
   Deze vlag controleert of de navigatie [observatoren](./navigation-lifecycle/observers) binnen de genavigeerde componenten moet activeren. Observatoren behandelen doorgaans gebeurtenissen zoals het invoeren of verlaten van een route. Dit op `false` instellen voorkomt dat observatoren worden aangeroepen.

4. **Geschiedenis bijwerken (`setUpdateHistory`)**  
   Wanneer ingesteld op `false`, voorkomt deze optie dat de geschiedenislocatie wordt bijgewerkt. Dit is nuttig wanneer je de weergave wilt veranderen zonder de terug- of vooruitnavigatie van de browser te beïnvloeden. Het beïnvloedt alleen het geschiedenismangement, niet de levenscyclus van de component of routeverwerking.

5. **Toestandobject (`setState`)**  
   [Het toestandobject](./state-management#saving-and-restoring-state-in-browser-history) stelt je in staat om aanvullende informatie door te geven bij het bijwerken van de geschiedenis van de browser. Dit object wordt opgeslagen in de geschiedenisstatus van de browser en kan later voor aangepaste doeleinden worden gebruikt, zoals het opslaan van de status van de app tijdens navigatie.

## Locaties genereren voor weergaven {#generating-locations-for-views}

De router kan de locatie voor weergaven genereren op basis van het routepatroon dat in de weergave is gedefinieerd. Je kunt ook aanvullende parameters voor dynamische en vereiste segmenten in de URL opgeven. Dit kan nuttig zijn bij het construeren van links of het delen van directe toegangspunten tot specifieke weergaven in de app.

Hier is hoe een `Location` te genereren op basis van een weergaveklasse en routeparameters:

```java
Class<UserProfileView> userProfileView = UserProfileView.class;
ParametersBag params = ParametersBag.of("id=JohnDoe");

Optional<Location> location = Router.getCurrent().getLocation(userProfileView, params);
console().log(location.get());
```

Dit genereert een `Location`-object met het pad `/user/JohnDoe`, de volledige URI als een string.
