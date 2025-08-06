---
title: Validating and Binding Data
sidebar_position: 5
pagination_next: null
_i18n_hash: 3efedcc32a2111ba6ce08c1a3ee6b477
---
Data binding is een mechanisme dat de UI-componenten van je app direct verbindt met het onderliggende datamodel, wat automatische synchronisatie van waarden tussen de twee mogelijk maakt. Dit elimineert de noodzaak voor repetitieve getter- en setter-aanroepen, waardoor de ontwikkelingstijd wordt verminderd en de betrouwbaarheid van de code verbetert.

Validatie, in deze context, zorgt ervoor dat de gegevens die in het formulier worden ingevoerd voldoen aan vooraf gedefinieerde regels, zoals niet leeg zijn of een specifiek formaat volgen. Door data binding te combineren met validatie, kun je de gebruikerservaring stroomlijnen terwijl je de gegevensintegriteit behoudt zonder uitgebreide handmatige controles te schrijven.

Voor meer informatie over data binding raadpleeg [dit artikel.](../../data-binding/overview) Om de app te draaien:

- Ga naar de `4-validating-and-binding-data` directory
- Voer het `mvn jetty:run` commando uit

<div class="videos-container">
  <video controls>
    <source src="https://cdn.webforj.com/webforj-documentation/video/tutorials/validating-and-binding-data.mp4" type="video/mp4"/>
  </video>
</div>

### Binding van de velden {#binding-the-fields}

De gegevensbinding setup begint met het initialiseren van een `BindingContext` voor het `Customer` model. De `BindingContext` koppelt de model-eigenschappen aan de formulier velden, waardoor automatische gegevensynchronisatie mogelijk is. Dit wordt ingesteld in de `FormView` constructor.

```java title="FormView.java"
BindingContext<Customer> context;
context = BindingContext.of(this, Customer.class, true);
```

`BindingContext.of(this, Customer.class, true)` initialiseert de binding context voor de `Customer` klasse. De derde parameter, `true`, schakelt [jakarta validatie](https://beanvalidation.org/) in.

:::info
Deze implementatie maakt gebruik van auto-binding zoals beschreven in het [Data Binding Artikel](../../data-binding/automatic-binding). Dit werkt als de velden in het gegevensmodel `Customer` dezelfde naam hebben als de overeenkomstige velden in de `FormView`.

Als de velden niet dezelfde naam hebben, kun je de `UseProperty` annotatie boven het veld in het formulier toevoegen dat je wilt binden, zodat ze weten welke gegevensvelden refereren.
:::

### Data binding met `onDidEnter()` {#data-binding-with-ondidenter}

De `onDidEnter` methode maakt gebruik van de gegevensbinding setup om het proces van het populeren van de formulier velden te stroomlijnen. In plaats van handmatig waarden voor elk veld in te stellen, wordt de gegevens nu automatisch gesynchroniseerd met de `BindingContext`.

```java {7}
@Override
  public void onDidEnter(DidEnterEvent event, ParametersBag parameters) {
    parameters.get("id").ifPresent(id -> {
      customer = Service.getCurrent().getCustomerByKey(UUID.fromString(id));
      customerId = id;
    });
    context.read(customer);
  }
```

De `context.read` methode in webforJ's gegevensbinding systeem synchroniseert de velden van een UI-component met de waarden van een datamodel. Het wordt in dit geval gebruikt om formulier velden te vullen met gegevens uit een bestaand model, zodat de UI de huidige staat van de gegevens weergeeft.

## Gegevens valideren {#validating-data}

Validatie zorgt ervoor dat de gegevens die in het formulier worden ingevoerd voldoen aan gespecificeerde regels, waardoor de datakwaliteit verbetert en ongeldige inzendingen worden voorkomen. Met gegevensbinding hoeft validatie niet langer handmatig te worden geïmplementeerd, maar kan deze eenvoudig worden geconfigureerd, wat realtime feedback op gebruikersinvoeren mogelijk maakt.

### Validatieregels definiëren {#defining-validation-rules}

Met [Jakarta](https://beanvalidation.org) en reguliere expressies kun je een groot aantal regels op een veld afdwingen. Vaak gebruikte voorbeelden zijn ervoor zorgen dat het veld niet leeg of null is, of een bepaald patroon volgt. Via annotaties in de klantklasse kun je jakarta validatieparameters aan het veld geven.

:::info
Meer details over de setup van de validatie zijn beschikbaar [hier](../../data-binding/validation/jakarta-validation.md#installation).
:::

```java
  @NotEmpty(message = "Naam mag niet leeg zijn")
  @Pattern(regexp = "[a-zA-Z]*", message = "Ongeldige tekens")
  private String firstName = "";
```

De `onValidate` methode wordt vervolgens toegevoegd om de status van de `Verzend` knop te controleren op basis van de geldigheid van de formulier velden. Dit zorgt ervoor dat alleen geldige gegevens kunnen worden verzonden.

```java title="FormView.java"
context.onValidate(e -> submit.setEnabled(e.isValid()));
```

`e.isValid()` retourneert true als alle velden geldig zijn, en false als dat niet het geval is. Dit betekent dat de `Verzend` knop is ingeschakeld zolang alle velden geldig zijn. Anders blijft deze uitgeschakeld, waardoor verzending wordt voorkomen totdat correcties zijn aangebracht.

### Toevoegen en bewerken van vermeldingen met validatie {#adding-and-editing-entries-with-validation}

De `submitCustomer()` methode valideert nu gegevens met behulp van de `BindingContext` voordat add- of bewerkingoperaties worden uitgevoerd. Deze aanpak elimineert de noodzaak voor handmatige validatiecontroles, waarbij de ingebouwde mechanismen van de context worden gebruikt om ervoor te zorgen dat alleen geldige gegevens worden verwerkt.

- **Toevoegmodus**: Als er geen `id` is opgegeven, is het formulier in de toevoegmodus. De gevalideerde gegevens worden geschreven naar het `Customer` model en aan de repository toegevoegd via `Service.getCurrent().addCustomer(customer)`.
- **Bewerkmodus**: Als er een `id` aanwezig is, haalt de methode de bijbehorende klantgegevens op, werkt deze bij met gevalideerde invoer en commit de wijzigingen aan de repository.

Het aanroepen van `context.write(customer)` zal een instantie van een `ValidationResult` retourneren. Deze klasse geeft aan of de validatie succesvol was en slaat eventuele berichten op die aan dit resultaat zijn gekoppeld.

Deze code zorgt ervoor dat alle wijzigingen worden gevalideerd en automatisch worden toegepast op het model voordat een nieuwe `Customer` wordt toegevoegd of een bestaande wordt bewerkt.

```java title="FormView.java"
private void submitCustomer() {
  ValidationResult results = context.write(customer);
  if (results.isValid()) {
    if (customerId.isEmpty()) {
      Service.getCurrent().addCustomer(customer);
    }
    Router.getCurrent().navigate(DemoView.class);
  }
}
```

Door deze stap te voltooien, ondersteunt de app nu gegevensbinding en validatie, waardoor formulierinvoeren worden gesynchroniseerd met het model en voldoen aan vooraf gedefinieerde regels.
