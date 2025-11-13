---
title: Gegevens Validatie en Binding
sidebar_position: 5
pagination_next: null
_i18n_hash: 11d03e09c4c37172713713649c920e9e
---
Data binding is een mechanisme dat de UI-componenten van uw app rechtstreeks verbindt met het onderliggende datamodel, waardoor automatische synchronisatie van waarden tussen beide mogelijk is. Dit elimineert de noodzaak voor repetitieve getter- en setter-aanroepen, vermindert de ontwikkelingstijd en verbetert de betrouwbaarheid van de code.

Validatie, in deze context, zorgt ervoor dat de gegevens die in het formulier worden ingevoerd voldoen aan vooraf gedefinieerde regels, zoals niet leeg zijn of een specifiek formaat volgen. Door data binding te combineren met validatie, kunt u de gebruikerservaring stroomlijnen terwijl de gegevensintegriteit wordt gewaarborgd zonder uitgebreide handmatige controles te schrijven.

Voor meer informatie over data binding kunt u [dit artikel](../../data-binding/overview) raadplegen. Om de app uit te voeren:

- Ga naar de `4-validating-and-binding-data` directory
- Voer het commando `mvn jetty:run` uit

<div class="videos-container">
  <video controls>
    <source src="https://cdn.webforj.com/webforj-documentation/video/tutorials/validating-and-binding-data.mp4" type="video/mp4"/>
  </video>
</div>

### Binding van de velden {#binding-the-fields}

De setup voor data binding begint met het initialiseren van een `BindingContext` voor het `Customer` model. De `BindingContext` verbindt de modelattributen met de formulier velden, waardoor automatische synchronisatie van gegevens mogelijk is. Dit wordt opgezet in de constructor van `FormView`.

```java title="FormView.java"
BindingContext<Customer> context;
context = BindingContext.of(this, Customer.class, true);
```

`BindingContext.of(this, Customer.class, true)` initialiseert de binding context voor de `Customer` klasse. De derde parameter, `true`, activeert [jakarta validatie](https://beanvalidation.org/).

:::info
Deze implementatie maakt gebruik van auto-binding zoals beschreven in het [Data Binding Artikel](../../data-binding/automatic-binding). Dit werkt als de velden in het datamodel `Customer` dezelfde naam hebben als de overeenkomstige velden in de `FormView`.

Als de velden niet dezelfde naam hebben, kunt u de annotatie `UseProperty` aan het formulier boven het veld dat u wilt binden toevoegen, zodat ze weten naar welke gegevensvelden te verwijzen.
:::

### Data binding met `onDidEnter()` {#data-binding-with-ondidenter}

De `onDidEnter`-methode maakt gebruik van de setup voor data binding om het proces van het populeren van de formulier velden te stroomlijnen. In plaats van handmatig waarden voor elk veld in te stellen, worden de gegevens nu automatisch gesynchroniseerd met de `BindingContext`.

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

De `context.read`-methode in het data bindingsysteem van webforJ synchroniseert de velden van een UI-component met de waarden uit een datamodel. Het wordt in dit geval gebruikt om formulier velden te vullen met gegevens uit een bestaand model, zodat de UI de huidige status van de gegevens weerspiegelt.

## Gegevens valideren {#validating-data}

Validatie zorgt ervoor dat de gegevens die in het formulier worden ingevoerd voldoen aan gespecificeerde regels, wat de gegevenskwaliteit verbetert en ongeldige indieningen voorkomt. Met data binding hoeft validatie niet handmatig te worden geïmplementeerd, maar kan deze in plaats daarvan eenvoudig worden geconfigureerd, zodat er realtime feedback op gebruikersinvoer mogelijk is.

### Definiëren van validatieregels {#defining-validation-rules}

Met [Jakarta](https://beanvalidation.org) en reguliere expressies kunt u een veelheid aan regels op een veld afdwingen. Vaak gebruikte voorbeelden zijn ervoor zorgen dat het veld niet leeg of null is, of een bepaald patroon volgt. Via annotaties in de klantklasse kunt u jakarta validatieparameters aan het veld geven.

:::info
Meer details over de setup van de validatie zijn beschikbaar [hier](../../data-binding/validation/jakarta-validation.md#installation).
:::

```java
  @NotEmpty(message = "Naam mag niet leeg zijn")
  @Pattern(regexp = "[a-zA-Z]*", message = "Ongeldige karakters")
  private String firstName = "";
```

De `onValidate`-methode wordt vervolgens toegevoegd om de status van de `Verstuur`-knop te controleren op basis van de geldigheid van de formulier velden. Dit zorgt ervoor dat alleen geldige gegevens kunnen worden ingediend.

```java title="FormView.java"
context.onValidate(e -> submit.setEnabled(e.isValid()));
```

`e.isValid()` retourneert waar als alle velden geldig zijn, en onwaar als dat niet het geval is. Dit betekent dat de `Verstuur`-knop is ingeschakeld zolang alle velden geldig zijn. Anders blijft deze uitgeschakeld, waardoor indiening wordt voorkomen totdat correcties zijn aangebracht.

### Toevoegen en bewerken van invoeren met validatie {#adding-and-editing-entries-with-validation}

De `submitCustomer()`-methode valideert nu gegevens met behulp van de `BindingContext` voordat add- of editbewerkingen worden uitgevoerd. Deze aanpak elimineert de noodzaak voor handmatige validatiecontroles en benut de ingebouwde mechanismen van de context om ervoor te zorgen dat alleen geldige gegevens worden verwerkt.

- **Toevoegmodus**: Als er geen `id` is opgegeven, is het formulier in de toevoegmodus. De gevalideerde gegevens worden geschreven naar het `Customer` model en toegevoegd aan de repository via `Service.getCurrent().addCustomer(customer)`.
- **Bewerkmodus**: Als er een `id` aanwezig is, haalt de methode de overeenkomstige klantgegevens op, werkt deze bij met gevalideerde invoer en commit de wijzigingen aan de repository.

Bij het aanroepen van `context.write(customer)` wordt een instantie van een `ValidationResult` geretourneerd. Deze klasse geeft aan of de validatie succesvol was of niet, en slaat eventuele berichten die aan dit resultaat zijn gekoppeld op.

Deze code zorgt ervoor dat alle wijzigingen worden gevalideerd en automatisch op het model worden toegepast voordat een nieuwe klant wordt toegevoegd of een bestaande klant wordt bewerkt.

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

Door deze stap te voltooien, ondersteunt de app nu data binding en validatie, waardoor formulierinvoer wordt gesynchroniseerd met het model en voldoet aan vooraf gedefinieerde regels.
