---
title: Scaling with Routing and Composites
sidebar_position: 4
_i18n_hash: fdfd4b4255de20775bb12bcd863630f7
---
Deze stap richt zich op het implementeren van routing om de schaalbaarheid en organisatie van de app-structuur te verbeteren. Om dit te bereiken, wordt de app bijgewerkt om meerdere weergaven te verwerken, waardoor navigatie tussen verschillende functionaliteiten zoals het bewerken en aanmaken van klantinvoer mogelijk wordt. Het zal het maken van weergaven voor deze functionaliteiten schetsen, waarbij componenten zoals `Composite` worden gebruikt om modulaire en herbruikbare lay-outs op te bouwen.

De app die is gemaakt in de [vorige stap](./working-with-data) zal een routing-setup hebben die meerdere weergaven ondersteunt, zodat gebruikers klantgegevens effectiever kunnen beheren, terwijl de codebase schoon en schaalbaar blijft. Om de app uit te voeren:

- Ga naar de `3-scaling-with-routing-and-composites` directory
- Voer het commando `mvn jetty:run` uit

<div class="videos-container">
  <video controls>
    <source src="https://cdn.webforj.com/webforj-documentation/video/tutorials/scaling-with-routing-and-composites.mp4" type="video/mp4"/>
  </video>
</div>

## Routing {#routing}

[Routing](../../routing/overview) is het mechanisme dat uw app in staat stelt om navigatie tussen verschillende weergaven of pagina's te beheren. In plaats van alle logica en functionaliteit op één locatie te houden, stelt routing u in staat uw app op te splitsen in kleinere, gefocuste componenten.

In de kern verbindt routing specifieke URL's met de weergaven of componenten die deze URL's afhandelen. Wanneer een gebruiker met uw app interactie heeft—zoals het klikken op een knop of het rechtstreeks invoeren van een URL in hun browser—lost de router de URL op naar de juiste weergave, initialiseert deze en toont deze op het scherm. Deze benadering maakt het gemakkelijk om navigatie te beheren en de status van de app te behouden.

Deze stap richt zich op het wijzigen van de `App`-klasse, het maken van bestanden voor de weergaven en het configureren van routes om soepele navigatie tussen verschillende delen van uw app mogelijk te maken.

In plaats van alle logica binnen de `run()`-methode van `App` te plaatsen, worden weergaven zoals `DemoView` en `FormView` geïmplementeerd als afzonderlijke klassen. Deze aanpak komt dichter in de buurt van standaard Java-praktijken.

- **DemoView**: Beheert het weergeven van de tabel en navigeren naar `FormView`.
- **FormView**: Beheert het toevoegen en bewerken van klantgegevens.

### Wijzigen van de `App`-klasse {#changing-the-app-class}

Om routing in uw app mogelijk te maken, werkt u de `App`-klasse bij met de `@Routify` annotatie. Dit vertelt webforJ om routing te activeren en gespecificeerde pakketten te scannen op route-ingeschakelde weergaven.

```java title="DemoApplication.java" {1}
@Routify(packages = "com.webforj.demos.views", debug = true)
public class DemoApplication extends App {  
}
```

- **`packages`**: Specificeert welke pakketten worden gescand op weergaven die routes definiëren.
- **`debug`**: Schakelt de debugmodus in voor gemakkelijker probleemoplossing tijdens de ontwikkeling.

### Bestanden voor de weergaven maken en routes configureren {#creating-files-for-the-views-and-configuring-routes}

Wanneer routing is ingeschakeld, worden aparte Java-bestanden gemaakt voor elke weergave die de app zal bevatten, in dit geval `DemoView.java` en `FormView.java`. Unieke routes worden toegewezen aan deze weergaven met behulp van de `@Route` annotatie. Dit zorgt ervoor dat elke weergave toegankelijk is via een specifieke URL.

Wanneer de `@Route` annotatie die is gekoppeld aan een klasse met een van deze achtervoegsels geen waarde heeft, wijst webforJ automatisch de naam van de klasse zonder het achtervoegsel toe als de route. Bijvoorbeeld, `DemoView` zal standaard de route `/demo` toewijzen. Aangezien in dit geval `DemoView` de standaardroute moet zijn, moet u het echter een route toewijzen.

De `/` route dient als het standaard toegangspunt voor uw app. Het toewijzen van deze route aan een weergave zorgt ervoor dat dit de eerste pagina is die gebruikers zien wanneer ze de app openen. In de meeste gevallen is een dashboard of samenvattingsweergave toegewezen aan `/`.

```java title="DemoView.java" {1}
@Route("/")
@FrameTitle("Demo")
public class DemoView extends Composite<Div> {
  // DemoView-logica
}
```

:::info 
Meer informatie over de verschillende routetypen is beschikbaar [hier](../../routing/defining-routes).
:::

Voor de `FormView` gebruikt de route `customer/:id?` een optionele parameter `id` om de modus van de `FormView` te bepalen. 

- **Toevoegmodus**: Wanneer `id` niet is opgegeven, initialiseert `FormView` met een leeg formulier voor het toevoegen van nieuwe klantgegevens.
- **Bewerkmodus**: Wanneer `id` is opgegeven, haalt `FormView` de gegevens van de bijbehorende klant op met behulp van `Service` en vult het formulier vooraf in, zodat wijzigingen aan de bestaande invoer kunnen worden aangebracht.

```java title="FormView.java" {1}
@Route("customer/:id?")
@FrameTitle("Klantformulier")
public class FormView extends Composite<Div> implements DidEnterObserver {
  // FormView-logica
}
```

:::info 
Meer informatie over de verschillende manieren om deze routepatronen te implementeren is beschikbaar [hier](../../routing/route-patterns).
:::

## Gebruik van `Composite` componenten om pagina's weer te geven {#using-composite-components-to-display-pages}

Composite-componenten in webforJ, zoals `Composite<Div>`, stellen u in staat om UI-logica en -structuur binnen een herbruikbare container te encapsuleren. Door `Composite` uit te breiden, beperkt u de methoden en gegevens die aan de rest van de app worden blootgesteld, wat zorgt voor schonere code en betere encapsulatie.

Bijvoorbeeld, `DemoView` breidt `Composite<Div>` uit in plaats van rechtstreeks `Div` uit te breiden:

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

Nadat routing is geconfigureerd en weergaven zijn ingesteld, verbindt u de weergaven en gegevens met behulp van gebeurtenislijsten en servicemethoden. De eerste stap is om een of meer UI-elementen toe te voegen om van de ene weergave naar de andere te navigeren.

### Navigatie met knoppen {#button-navigation}

De `Button` component triggert een navigatie-evenement om over te schakelen van de ene weergave naar de andere met behulp van de `Router` klasse. Bijvoorbeeld:

```java title="DemoView.java"
private Button add = new Button("Voeg Klant Toe", ButtonTheme.PRIMARY,
  e -> Router.getCurrent().navigate(FormView.class));
```

:::info
De Router klasse gebruikt de gegeven klasse om de route op te lossen en een URL te bouwen om naar te navigeren. Alle browser-navigatie wordt vervolgens afgehandeld, zodat geschiedenisbeheer en weergave-initialisatie geen zorg zijn.
Voor meer details over navigatie, zie het [Route Navigatie-artikel](../../routing/route-navigation).
:::

### Tabelbewerking {#table-editing}

Naast navigatie via een knopklik, stellen veel apps ook navigatie naar andere delen van een app mogelijk wanneer een `Table` dubbel wordt geklikt. De volgende wijzigingen worden aangebracht om gebruikers in staat te stellen een item in de tabel dubbel te klikken om naar een formulier te navigeren dat is vooraf ingevuld met de details van het item.

Zodra de details op het juiste scherm zijn bewerkt, worden de wijzigingen opgeslagen en wordt de `Table` bijgewerkt om de gewijzigde gegevens van het geselecteerde item weer te geven.

Om deze navigatie mogelijk te maken, worden itemklikken in de tabel behandeld door de `TableItemClickEvent<Customer>` listener. Het evenement bevat de `id` van de geklikte klant, die het doorgeeft aan de `FormView` door gebruik te maken van de `navigate()` methode met een `ParametersBag`:

```java title="DemoView.java" 
private void editCustomer(TableItemClickEvent<Customer> e) {
  Router.getCurrent().navigate(FormView.class,
    ParametersBag.of("id=" + e.getItemKey()));
}
```

### Afhandelen van initialisatie met `onDidEnter` {#handling-initialization-with-ondidenter}

De `onDidEnter` methode in webforJ maakt deel uit van de routinglevenscyclus en wordt geactiveerd wanneer een weergave actief wordt.

Wanneer de `Router` naar een weergave navigeert, wordt `onDidEnter` geactiveerd als onderdeel van de levenscyclus om:
- **Gegevens Laden**: Initialiseert of haalt de gegevens op die nodig zijn voor de weergave op basis van routeparameters.
- **De Weergave Instellen**: Update UI-elementen dynamisch op basis van de context.
- **Reageren op Statuswijzigingen**: Voer acties uit die afhankelijk zijn van de actieve weergave, zoals het resetten van formulieren of het markeren van componenten.

De `onDidEnter` methode in `FormView` controleert op de aanwezigheid van een `id` parameter in de route en past het gedrag van het formulier dienovereenkomstig aan:

- **Bewerkmodus**: Als er een `id` is opgegeven, wordt de bijbehorende klantgegevens opgehaald met behulp van `Service` en worden de formulier velden vooraf ingevuld. De `Indienen` knop is geconfigureerd om de bestaande invoer bij te werken.
- **Toevoegmodus**: Als er geen `id` aanwezig is, blijft het formulier leeg en is de `Indienen` knop geconfigureerd om een nieuwe klant aan te maken.

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

Wanneer u klaar bent met het bewerken van de gegevens, is het noodzakelijk om deze in te dienen bij de service die de repository beheert. Daarom moet de 
`Service` klasse die al in de vorige stap van deze tutorial is ingesteld nu worden uitgebreid met aanvullende methoden, zodat gebruikers klanten kunnen toevoegen en bewerken. 

De onderstaande snippet laat zien hoe u dit kunt bereiken:

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

De `commit()` methode in de `Repository` klasse houdt de gegevens en UI van de app in sync. Het biedt een mechanisme om de in de `Repository` opgeslagen gegevens te verversen, zodat de nieuwste status in de app wordt weergegeven.

Deze methode kan op twee manieren worden gebruikt:

1) **Verversen van alle gegevens:**
  Het aanroepen van `commit()` zonder argumenten herlaadt alle entiteiten uit de onderliggende gegevensbron van de repository, zoals een database of een serviceklasse.

2) **Verversen van een enkele entiteit:**
  Het aanroepen van `commit(T entity)` herlaadt een specifieke entiteit, zodat de staat overeenkomt met de nieuwste wijzigingen in de gegevensbron.

Roep `commit()` aan wanneer gegevens in de `Repository` wijzigen, zoals na het toevoegen of wijzigen van entiteiten in de gegevensbron.

```java
// Ververs alle klantgegevens in de repository
customerRepository.commit();

// Ververs een enkele klantentiteit
Customer updatedCustomer = ...; // Geüpdatet vanuit een externe bron
customerRepository.commit(updatedCustomer);

```

Met deze wijzigingen zijn de volgende doelen bereikt:

  1. Routing geïmplementeerd en zo ingesteld dat toekomstige weergaven met weinig moeite kunnen worden geïntegreerd.
  2. UI-implementaties uit de `App` verwijderd en in een aparte weergave geplaatst.
  3. Een aanvullende weergave toegevoegd om de gegevens te manipuleren die in de klantentabel worden weergegeven.

Met de wijziging van de klantgegevens en routing voltooid, zal de volgende stap zich richten op het implementeren van gegevensbinding en het gebruik ervan om validatie te vergemakkelijken.
