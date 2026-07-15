---
sidebar_position: 4
title: Route Navigation
description: >-
  Trigger client-side navigation programmatically with Router.navigate, pass
  parameters, and switch views without reloads.
_i18n_hash: c32517b16f185d4b54682b95c82d38d3
---
In webforJ is navigeren tussen routes de belangrijkste mechanismen voor het wisselen van weergaven en componenten op basis van gebruikersacties of wijzigingen in de URL. Navigatie stelt gebruikers in staat om naadloos tussen verschillende delen van de app te bewegen zonder de pagina te vernieuwen. Deze client-side navigatie houdt de app responsief en soepel terwijl de staat van de app behouden blijft.

## Programmatic navigation {#programmatic-navigation}

Je kunt navigatie vanuit iedere plaats in je app triggeren door gebruik te maken van de `Router`-klasse. Dit maakt dynamische wijzigingen in de weergegeven componenten mogelijk op basis van gebeurtenissen zoals klikken op knoppen of andere gebruikersinteracties.

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

In dit voorbeeld veroorzaakt het programatisch navigeren naar de `DashboardView`-component dat de `DashboardView`-component wordt gerenderd en de URL van de browser wordt bijgewerkt naar `/dashboard`.

Het is ook mogelijk om naar de weergave te navigeren door een nieuwe `Location` door te geven.

```java
Router.getCurrent().navigate(new Location("/dashboard"));
```

:::tip Klasse vs. Locatie: Methoden voor Weergave Routing
Bij het navigeren tussen weergaven hebben ontwikkelaars twee opties: ze kunnen ofwel de weergave- of routeklasse doorgeven, waardoor de router automatisch de URL genereert en de weergave rendert, of ze kunnen de locatie rechtstreeks doorgeven. Beide methoden zijn geldig, maar **het gebruik van de weergaveklasse is de voorkeur** omdat dit betere flexibiliteit biedt voor toekomstige wijzigingen. Als je bijvoorbeeld later besluit om de route bij te werken, hoef je alleen de `@Route`-annotatie aan te passen, zonder dat je code hoeft te wijzigen die de weergaveklasse voor navigatie gebruikt.
:::

### Navigatie met parameters {#navigation-with-parameters}

Wanneer je parameters samen met de route moet doorgeven, staat webforJ je toe om parameters in de URL in te voegen. Hier is hoe je naar een route met parameters kunt navigeren:

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
// navigeer naar de weergave en geef de gebruikers-id door
Router.getCurrent().navigate(
  UserProfileView.class,
  ParametersBag.of("id=JohnDoe")
);
```

Dit navigeert naar `/user/JohnDoe`, waar `JohnDoe` een gebruikers-id kan vertegenwoordigen. De component voor deze route kan vervolgens de parameter extraheren en deze dienovereenkomstig gebruiken.

## Gemaakte weergave-instantie {#created-view-instance}

De `navigate`-methode accepteert een Java `Consumer` die wordt aangeroepen zodra de navigatie is voltooid. De `Consumer` ontvangt de instantie van de gemaakte weergavecomponent, verpakt in een java `Optional`, waarmee de ontwikkelaar kan interageren met de weergave na een succesvolle navigatie.

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
De consumer ontvangt een Java `Optional` voor de component omdat deze mogelijk `null` is of om verschillende redenen niet is gemaakt. Bijvoorbeeld, de component wordt mogelijk niet gerenderd als de navigatie-observatoren de navigatie vetoën en het proces stoppen.
:::

## Navigatie-opties {#navigation-options}

De `NavigationOptions`-klasse stelt ontwikkelaars in staat om nauwkeurig te tunen hoe navigatie binnen de app wordt afgehandeld. Door specifieke opties in te stellen, kun je het gedrag van de navigatie controleren, zoals of de geschiedenis van de browser moet worden bijgewerkt, levenscyclusobservatoren moeten worden aangeroepen of zelfs navigatie-evenementen moeten worden geactiveerd.

```java
NavigationOptions options = new NavigationOptions();
options.setUpdateHistory(false);

Router.getCurrent().navigate(
  new Location("user/JohnDoe"), options);
```

### Instellen van navigatieopties {#setting-navigation-options}

De `NavigationOptions`-klasse biedt verschillende methoden voor het aanpassen van het navigatiegedrag. Deze omvatten het controleren van hoe routes worden afgehandeld, of observatoren worden geïnformeerd en hoe de geschiedenis van de browser wordt bijgewerkt.

Hier zijn de belangrijkste configuratieopties binnen `NavigationOptions`:

1. **Navigatietype (`setNavigationType`)**
   Deze optie definieert of de nieuwe route aan de geschiedenis van de browser moet worden toegevoegd of de huidige route moet vervangen.

   - **`PUSH`**: Voegt de nieuwe route toe aan de geschiedenisstack, en behoudt de huidige locatie.
   - **`REPLACE`**: Vervangt de huidige route in de geschiedenisstack door de nieuwe locatie, waardoor de terugknop niet naar de vorige route kan navigeren.

2. **Evenementen Activeren (`setFireEvents`)**
   Bepaalt of navigatie [levenscyclusevenementen](./navigation-lifecycle/navigation-events) tijdens navigatie moeten worden geactiveerd. Standaard is dit ingesteld op `true`, en worden evenementen geactiveerd. Als dit is ingesteld op `false`, worden er geen evenementen geactiveerd, wat handig is voor stille navigatie.

3. **Observatoren Aanroepen (`setInvokeObservers`)**
   Deze vlag controleert of de navigatie [observatoren](./navigation-lifecycle/observers) binnen de genavigeerde componenten moet activeren. Observatoren behandelen doorgaans gebeurtenissen zoals het betreden of verlaten van routes. Dit op `false` instellen voorkomt dat observatoren worden aangeroepen.

4. **Geschiedenis Bijwerken (`setUpdateHistory`)**
   Wanneer ingesteld op `false`, voorkomt deze optie dat de geschiedenislocatie wordt bijgewerkt. Dit is handig wanneer je de weergave wilt wijzigen zonder de terug- of vooruitnavigatie van de browser te beïnvloeden. Dit heeft alleen invloed op het geschiedenisbeheer, niet op de levenscyclus van componenten of route-afhandeling.

5. **State Object (`setState`)**
   [Het state-object](./state-management#saving-and-restoring-state-in-browser-history) stelt je in staat om extra informatie door te geven bij het bijwerken van de geschiedenis van de browser. Dit object wordt opgeslagen in de geschiedenisstatus van de browser en kan later voor specifieke doeleinden worden gebruikt, zoals het opslaan van de status van de app tijdens navigatie.

## Locaties genereren voor weergaven {#generating-locations-for-views}

De router kan de locatie voor weergaven genereren op basis van het routepatroon dat in de weergave is gedefinieerd. Je kunt ook extra parameters voor dynamische en vereiste segments in de URL opgeven. Dit kan handig zijn bij het construeren van links of het delen van directe toegangspunten tot specifieke weergaven in de app.

Hier is hoe je een `Location` kunt genereren op basis van een weergaveklasse en routeparameters:

```java
Class<UserProfileView> userProfileView = UserProfileView.class;
ParametersBag params = ParametersBag.of("id=JohnDoe");

Optional<Location> location = Router.getCurrent().getLocation(userProfileView, params);
console().log(location.get());
```

Dit genereert een `Location`-object met het pad `/user/JohnDoe`, de complete URI als een string.
