---
title: Scaling with Routing and Composites
sidebar_position: 4
_i18n_hash: 50cd3b00cb1fb7731b6328708d6d45ba
---
Deze stap richt zich op het implementeren van routing om de schaalbaarheid en organisatie van de app-structuur te verbeteren. Om dit te bereiken, zal de app worden geüpdatet om meerdere weergaven te ondersteunen, waardoor navigatie tussen verschillende functionaliteiten zoals het bewerken en aanmaken van klantinvoer mogelijk wordt. Het beschrijft het maken van weergaven voor deze functionaliteiten, met behulp van componenten zoals `Composite` om modulaire en herbruikbare lay-outs te bouwen.

De app die is gemaakt in de [vorige stap](./working-with-data) heeft een routingsetup die meerdere weergaven ondersteunt, waardoor gebruikers klantgegevens effectiever kunnen beheren terwijl ze een schone en schaalbare codebase behouden. Om de app uit te voeren:

- Ga naar de `3-scaling-with-routing-and-composites` directory
- Voer het commando `mvn jetty:run` uit

<div class="videos-container">
  <video controls>
    <source src="https://cdn.webforj.com/webforj-documentation/video/tutorials/scaling-with-routing-and-composites.mp4" type="video/mp4"/>
  </video>
</div>

## Routing {#routing}

[Routing](../../routing/overview) is het mechanisme dat uw app in staat stelt om navigatie tussen verschillende weergaven of pagina's te beheren. In plaats van alle logica en gedrag op één locatie te behouden, stelt routing je in staat om je app op te splitsen in kleinere, gerichte componenten.

In zijn kern koppelt routing specifieke URL's aan de weergaven of componenten die die URL's afhandelen. Wanneer een gebruiker interactie heeft met je app—zoals het klikken op een knop of het rechtstreeks invoeren van een URL in hun browser—lost de router de URL op naar de geschikte weergave, initialiseert deze en toont deze op het scherm. Deze aanpak maakt het eenvoudig om navigatie te beheren en de staat van de app te behouden.

Deze stap richt zich op het wijzigen van de `App`-klasse, het maken van bestanden voor de weergaven en het configureren van routes om een soepele navigatie tussen verschillende delen van uw app mogelijk te maken.

In plaats van alle logica binnen de `run()`-methode van `App` te plaatsen, worden weergaven zoals `DemoView` en `FormView` geïmplementeerd als aparte klassen. Deze aanpak sluit beter aan bij de standaard Java-praktijken.

- **DemoView**: Beheert het weergeven van de tabel en het navigeren naar `FormView`.
- **FormView**: Beheert het toevoegen en bewerken van klantgegevens.

### Wijzigen van de `App`-klasse {#changing-the-app-class}

Om routing in je app mogelijk te maken, update je de `App`-klasse met de `@Routify` annotatie. Dit vertelt webforJ om routing te activeren en specifieke pakketten te scannen naar route-enabled weergaven.

```java title="DemoApplication.java" {1}
@Routify(packages = "com.webforj.demos.views", debug = true)
public class DemoApplication extends App {  
}
```

- **`packages`**: Geeft aan welke pakketten worden gescand naar weergaven die routes definiëren.
- **`debug`**: Activeert de debug-modus voor gemakkelijker oplossen van problemen tijdens de ontwikkeling.

### Bestanden maken voor de weergaven en configureren van routes {#creating-files-for-the-views-and-configuring-routes}

Nadat routing is ingeschakeld, worden aparte Java-bestanden voor elke weergave die de app zal bevatten gemaakt, in dit geval `DemoView.java` en `FormView.java`. Unieke routes worden toegewezen aan deze weergaven met behulp van de `@Route` annotatie. Dit zorgt ervoor dat elke weergave toegankelijk is via een specifieke URL.

Wanneer de `@Route` annotatie die aan een klasse met een van deze suffixen is gekoppeld geen waarde heeft, wijst webforJ automatisch de naam van de klasse zonder de suffix toe als de route. Bijvoorbeeld, `DemoView` zal standaard de route `/demo` mappen. Aangezien in dit geval `DemoView` als de standaardroute bedoeld is, moet je het een route toewijzen.

De `/` route fungeert als het standaard toegangspunt voor uw app. Het toewijzen van deze route aan een weergave zorgt ervoor dat het de eerste pagina is die gebruikers zien wanneer ze de app openen. In de meeste gevallen wordt een dashboard- of samenvattingsweergave toegewezen aan `/`.

```java title="DemoView.java" {1}
@Route("/")
@FrameTitle("Demo")
public class DemoView extends Composite<Div> {
  // DemoView logica
}
```

:::info 
Meer informatie over de verschillende routetypen is beschikbaar [hier](../../routing/defining-routes).
:::

Voor de `FormView` gebruikt de route `customer/:id?` een optionele parameter `id` om de modus van de `FormView` te bepalen.

- **Toevoegmodus**: Wanneer `id` niet is opgegeven, initialiseert `FormView` met een leeg formulier voor het toevoegen van nieuwe klantgegevens.
- **Bewerkmodus**: Wanneer `id` is opgegeven, haalt `FormView` de bijbehorende klantgegevens op met behulp van `Service` en vult het formulier voor, waardoor bewerkingen kunnen worden aangebracht in de bestaande invoer.

```java title="FormView.java" {1}
@Route("customer/:id?")
@FrameTitle("Klantformulier")
public class FormView extends Composite<Div> implements DidEnterObserver {
  // FormView logica
}
```

:::info 
Meer informatie over de verschillende manieren om die routepatronen te implementeren is beschikbaar [hier](../../routing/route-patterns).
:::

## Gebruik van `Composite` componenten om pagina's weer te geven {#using-composite-components-to-display-pages}

Composite componenten in webforJ, zoals `Composite<Div>`, stellen je in staat om UI-logica en structuur te encapsuleren binnen een herbruikbare container. Door `Composite` uit te breiden, beperk je de methoden en gegevens die aan de rest van de app worden blootgesteld, wat zorgt voor schonere code en betere encapsulatie.

Bijvoorbeeld, `DemoView` breidt `Composite<Div>` uit in plaats van direct `Div` uit te breiden:

```java title="DemoView.java"
public class DemoView extends Composite<Div> {
  private Table<Customer> table = new Table<>();
  private Button add = new Button("Voeg Klant Toe", ButtonTheme.PRIMARY);  

  public DemoView() {
    setupLayout();
  }

  private void setupLayout() {
    FlexLayout layout = FlexLayout.create(table, add)
        .vertical().contentAlign().center().build().setPadding("var(--dwc-space-l)");
    getBoundComponent().add(layout);
  }
}
```

## Verbinden van de routes {#connecting-the-routes}

Na het configureren van routing en het instellen van weergaven, verbind je de weergaven en gegevens met behulp van evenementenluisteraars en servicemethoden. De eerste stap is om een of meer UI-elementen toe te voegen om van de ene weergave naar de andere te navigeren.

### Knopnavigatie {#button-navigation}

De `Button` component triggert een navigatie-evenement om over te schakelen van de ene weergave naar de andere met de `Router` klasse. Bijvoorbeeld:

```java title="DemoView.java"
private Button add = new Button("Voeg Klant Toe", ButtonTheme.PRIMARY,
  e -> Router.getCurrent().navigate(FormView.class));
```

:::info
De Router klasse gebruikt de opgegeven klasse om de route op te lossen en een URL te bouwen om naartoe te navigeren. Alle browsernavigatie wordt dan afgehandeld zodat het beheer van de geschiedenis en de initialisatie van weergaven geen zorg zijn.
Voor meer details over navigatie, zie het [Route Navigatie Artikel](../../routing/route-navigation).
:::

### Tabelbewerking {#table-editing}

Naast navigatie via een knopklik, stellen veel apps ook navigatie naar andere delen van een app mogelijk wanneer een `Table` dubbel wordt geklikt. De volgende wijzigingen worden aangebracht om gebruikers in staat te stellen om op een item in de tabel te dubbelklikken om naar een formulier te navigeren dat is voorgevuld met de details van het item.

Zodra de details op het juiste scherm zijn bewerkt, worden de wijzigingen opgeslagen en wordt de `Table` bijgewerkt om de gewijzigde gegevens van het geselecteerde item weer te geven.

Om deze navigatie te vergemakkelijken, worden itemklikken in de tabel behandeld door de `TableItemClickEvent<Customer>` luisteraar. Het evenement bevat de `id` van de geklikte klant, die het doorgeeft aan de `FormView` door gebruik te maken van de `navigate()` methode met een `ParametersBag`:

```java title="DemoView.java" 
private void editCustomer(TableItemClickEvent<Customer> e) {
  Router.getCurrent().navigate(FormView.class,
    ParametersBag.of("id=" + e.getItemKey()));
}
```

### Initialisatie afhandelen met `onDidEnter` {#handling-initialization-with-ondidenter}

De `onDidEnter` methode in webforJ maakt deel uit van de routing levenscyclus en wordt geactiveerd wanneer een weergave actief wordt.

Wanneer de `Router` naar een weergave navigeert, wordt `onDidEnter` geactiveerd als onderdeel van de levenscyclus om:
- **Gegevens Laden**: Initialiseert of haalt de benodigde gegevens op voor de weergave op basis van routeparameters.
- **De Weergave Instellen**: Update UI-elementen dynamisch op basis van de context.
- **Reageren op Statuswijzigingen**: Voert acties uit die afhankelijk zijn van de weergave die actief is, zoals het resetten van formulieren of het markeren van componenten.

De `onDidEnter` methode in `FormView` controleert op de aanwezigheid van een `id` parameter in de route en past het gedrag van het formulier dienovereenkomstig aan:

- **Bewerkmodus**: Als er een `id` is opgegeven, haalt de methode de bijbehorende klantgegevens op met behulp van `Service` en vult de formulier velden voor. De `Verstuur` knop is geconfigureerd om de bestaande invoer bij te werken.
- **Toevoegmodus**: Als er geen `id` aanwezig is, blijft het formulier leeg en is de `Verstuur` knop geconfigureerd om een nieuwe klant te creëren.

```java
@Override
  public void onDidEnter(DidEnterEvent event, ParametersBag parameters) {
    parameters.get("id").ifPresentOrElse(id -> {
      customer = Service.getCurrent().getCustomerByKey(UUID.fromString(id));
      firstName.setValue(customer.getFirstName());
      lastName.setValue(customer.getLastName());
      company.setValue(customer.getCompany());
      country.selectKey(customer.getCountry());
      submit.addClickListener(e -> submit("edit"));
    }, () -> submit.addClickListener(e -> submit("add")));
  }
```


### Gegevens indienen {#submitting-data}

Wanneer je klaar bent met het bewerken van de gegevens, is het noodzakelijk om deze in te dienen bij de service die de repository afhandelt. Daarom moet de 
`Service` klasse die al is ingesteld in de vorige stap van deze tutorial nu worden uitgebreid met extra methoden, zodat gebruikers klanten kunnen toevoegen en bewerken. 

De onderstaande snippet laat zien hoe dit te bereiken:

```java title="Service.java"
public void addCustomer(Customer newCustomer) {
  data.add(newCustomer);
  repository.commit(newCustomer);
}

public void editCustomer(Customer editedCustomer) {
  repository.commit(editedCustomer);
}
```

### Gebruik van `commit()` {#using-commit}

De `commit()` methode in de `Repository` klasse houdt de gegevens en UI van de app gesynchroniseerd. Het biedt een mechanisme om de gegevens die in de `Repository` zijn opgeslagen bij te werken, zodat de laatste staat wordt weerspiegeld in de app.

Deze methode kan op twee manieren worden gebruikt:

1) **Alle gegevens vernieuwen:**
  Het aanroepen van `commit()` zonder argumenten herlaadt alle entiteiten vanuit de onderliggende gegevensbron van de repository, zoals een database of een serviceklasse.

2) **Een enkele entiteit vernieuwen:**
  Het aanroepen van `commit(T entity)` ververst een specifieke entiteit, waardoor de status ervan overeenkomt met de laatste wijzigingen in de gegevensbron.

Roep `commit()` aan wanneer de gegevens in de `Repository` wijzigen, zoals na het toevoegen of wijzigen van entiteiten in de gegevensbron.

```java
// Vernieuw alle klantgegevens in de repository
customerRepository.commit();

// Vernieuw een enkele klantentiteit
Customer updatedCustomer = ...; // Geüpdatet vanuit een externe bron
customerRepository.commit(updatedCustomer);

```

Met deze wijzigingen zijn de volgende doelen bereikt:

  1. Routing geïmplementeerd en ingesteld zodat toekomstige weergaven met weinig moeite kunnen worden geïntegreerd.
  2. UI-implementaties uit de `App` verwijderd en in een aparte weergave geplaatst.
  3. Een aanvullende weergave toegevoegd om de gegevens die in de klantentabel worden weergegeven te manipuleren.

Met de aanpassing van de klantgegevens en de routing volbracht, zal de volgende stap zich richten op het implementeren van gegevensbinding en het gebruiken ervan om validatie te vergemakkelijken.
