---
sidebar_position: 4
title: Routenavigatie
description: >-
  Trigger client-side navigation programmatically with Router.navigate, pass
  parameters, and switch views without reloads.
sidebar_class_name: updated-content
_i18n_hash: 0284f2481f307d68da728d81f4b3a6a2
---
In webforJ is navigeren tussen routes de kernmechanisme voor het schakelen van weergaven en componenten op basis van gebruikersacties of URL-wijzigingen. Navigatie stelt gebruikers in staat om naadloos tussen verschillende delen van de app te verplaatsen zonder de pagina te vernieuwen. Deze client-side navigatie houdt de app responsief en soepel terwijl de status van de app behouden blijft.

## Programmatic navigation {#programmatic-navigation}

Je kunt navigatie vanuit elke plek in je app activeren met de `Router`-klasse. Dit maakt dynamische veranderingen mogelijk in de weergegeven componenten op basis van gebeurtenissen zoals knopklikken of andere gebruikersinteracties.

Hier is een voorbeeld van hoe te navigeren naar een specifieke route:

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

In dit voorbeeld zorgt het programmatic navigeren naar de `DashboardView`-component ervoor dat de `DashboardView`-component weergegeven wordt en de URL van de browser bijgewerkt wordt naar `/dashboard`.

Het is ook mogelijk om naar de weergave te navigeren door een nieuwe `Location` door te geven.

```java
Router.getCurrent().navigate(new Location("/dashboard"));
```

:::tip Klasse vs. Locatie: Methoden voor View Routing
Bij het navigeren tussen weergaven hebben ontwikkelaars twee opties: ze kunnen ofwel de view- of routeklasse doorgeven, waardoor de router automatisch de URL genereert en de weergave rendert, of de locatie rechtstreeks doorgeven. Beide methoden zijn geldig, maar **het gebruik van de viewklasse is de voorkeur aanpak** omdat het meer flexibiliteit biedt voor toekomstige wijzigingen. Als je bijvoorbeeld later besluit de route bij te werken, hoef je alleen de `@Route`-annotatie te wijzigen, zonder dat je enige code die de viewklasse voor navigatie gebruikt, hoeft te veranderen.
:::

### Navigatie met parameters {#navigation-with-parameters}

Wanneer je parameters samen met de route moet doorgeven, stelt webforJ je in staat om parameters in de URL te embedden. Hier is hoe je naar een route met parameters kunt navigeren:

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

Dit navigeert naar `/user/JohnDoe`, waar `JohnDoe` misschien een gebruikers-ID vertegenwoordigt. De component voor deze route kan dan de parameter extraheren en deze dienovereenkomstig gebruiken.

## Gemaakte weergave-instantie {#created-view-instance}

De `navigate`-methode accepteert een Java `Consumer` die wordt aangeroepen zodra de navigatie is voltooid. De `Consumer` ontvangt de instantie van de gemaakte weergavecomponent, verpakt in een java `Optional`, waardoor de ontwikkelaar met de weergave kan omgaan na een succesvolle navigatie.

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
De consumer ontvangt een Java `Optional` voor de component omdat deze mogelijk `null` is, of om verschillende redenen niet is aangemaakt. Bijvoorbeeld, de component wordt mogelijk niet weergegeven als de navigatie-waarnemers de navigatie afkeuren en het proces stoppen.
:::

## Navigatie-opties {#navigation-options}

De `NavigationOptions`-klasse stelt ontwikkelaars in staat om de manier waarop navigatie in de app wordt behandeld, fijn af te stemmen. Door specifieke opties in te stellen, kun je het gedrag van navigatie controleren, zoals of de geschiedenis van de browser moet worden bijgewerkt, levenscyclus-waarnemers moet aanroepen, of zelfs navigatie-evenementen moet afvuren.

```java
NavigationOptions options = new NavigationOptions();
options.setUpdateHistory(false);

Router.getCurrent().navigate(
  new Location("user/JohnDoe"), options);
```

### Navigatie-opties instellen {#setting-navigation-options}

De `NavigationOptions`-klasse biedt verschillende methoden voor het aanpassen van het navigatiegedrag. Deze omvatten het regelen van hoe routes worden behandeld, of waarnemers worden geïnformeerd, en hoe de geschiedenis van de browser wordt bijgewerkt.

Hier zijn de belangrijkste configuratieopties beschikbaar binnen `NavigationOptions`:

1. **Navigatietype (`setNavigationType`)**

   Deze optie definieert of de nieuwe route aan de geschiedenis van de browser moet worden toegevoegd of de huidige route moet vervangen.

   - **`PUSH`**: Voegt de nieuwe route toe aan de geschiedenisstapel, waardoor de huidige locatie behouden blijft.
   - **`REPLACE`**: Vervangt de huidige route in de geschiedenisstapel door de nieuwe locatie, waardoor de terugknop niet naar de vorige route kan navigeren.

2. **Evenementen afvuren (`setFireEvents`)**

   Bepaalt of navigatie [levenscyclusgebeurtenissen](./navigation-lifecycle/navigation-events) moeten worden afgevuurd tijdens navigatie. Standaard is dit ingesteld op `true`, en gebeurtenissen worden afgevuurd. Als het is ingesteld op `false`, worden er geen evenementen afgevuurd, wat nuttig is voor stille navigatie.

3. **Waarnemers aanroepen (`setInvokeObservers`)**

   Deze vlag controleert of de navigatie [waarnemers](./navigation-lifecycle/observers) binnen de genavigeerde componenten moet triggeren. Waarnemers behandelen doorgaans gebeurtenissen zoals route-ingang of -uitgang. Het instellen hiervan op `false` voorkomt dat waarnemers worden aangeroepen.

4. **Geschiedenis bijwerken (`setUpdateHistory`)**

   Wanneer ingesteld op `false`, voorkomt deze optie dat de geschiedenislocatie wordt bijgewerkt. Dit is nuttig wanneer je de weergave wilt veranderen zonder de achteruit- of vooruitnavigeerfunctie van de browser te beïnvloeden. Het heeft alleen invloed op het beheer van de geschiedenis, niet op de levenscyclus van de component of routebehandeling.

5. **Statusobject (`setState`)**

   [Het statusobject](./state-management#saving-and-restoring-state-in-browser-history) stelt je in staat om aanvullende informatie door te geven bij het bijwerken van de geschiedenis van de browser. Dit object wordt opgeslagen in de staat van de browsergeschiedenis en kan later voor aangepaste doeleinden worden gebruikt, zoals het opslaan van de status van de app tijdens navigatie.

6. **Instanties opnieuw maken (`setRecreateFrom`)** <DocChip chip='since' label='26.02' />

    Wanneer een routecomponent is opgegeven, stelt deze optie navigatie in staat om alle weergegeven instanties van die component en daaronder gelegen componenten te vernietigen voordat deze opnieuw wordt weergegeven. Dit stelt dat deel van de hiërarchie in staat om nieuwe instanties te gebruiken, zonder de weergegeven instanties vóór de gegeven component aan te raken.

    ```java
    NavigationOptions options = new NavigationOptions()
        .setRecreateFrom(DashboardView.class);

    Router.getCurrent().navigate(
        new Location("/dashboard"), options);
    ```

    De standaardroute voor `setRecreateFrom()` is `null`, waardoor de router weergegeven routecomponenten die in het pad blijven hergebruikt. Als de gegeven component geen weergegeven instantie heeft, gedraagt de navigatie zich zoals gebruikelijk. Bovendien kan een levenscycluswaarnemer de vernietiging afkeuren, wat de navigatie faalt.

## Locaties genereren voor weergaven {#generating-locations-for-views}

De router kan de locatie voor weergaven genereren op basis van het routepatroon dat in de weergave is gedefinieerd. Je kunt ook aanvullende parameters bieden voor dynamische en vereiste segmenten in de URL. Dit kan nuttig zijn wanneer je links construeert of directe toegangspunten naar specifieke weergaven in de app deelt.

Hier is hoe je een `Location` kunt genereren op basis van een view-klasse en routeparameters:

```java
Class<UserProfileView> userProfileView = UserProfileView.class;
ParametersBag params = ParametersBag.of("id=JohnDoe");

Optional<Location> location = Router.getCurrent().getLocation(userProfileView, params);
console().log(location.get());
```

Dit genereert een `Location`-object met het pad `/user/JohnDoe`, de volledige URI als een string.
