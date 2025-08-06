---
sidebar_position: 7
title: State Management
_i18n_hash: cba905abd01a780dea1f459ec4397cda
---
Het creëren van naadloze, dynamische gebruikerservaringen vereist vaak dat de status van je webapplicatie wordt weerspiegeld in de URL en behouden blijft tijdens navigatie in de browser. Je kunt dit bereiken zonder de pagina opnieuw te laden door gebruik te maken van updates van URL-parameters en het beheer van de status van de browsergeschiedenis. Dit zorgt ervoor dat gebruikers specifieke weergaven kunnen delen, bladwijzers kunnen maken of terug kunnen keren naar specifieke weergaven met de app die zich volledig bewust is van hun eerdere interacties.

## Het bijwerken van de URL {#updating-the-url}

Wanneer de status van een webpagina verandert, zoals het filteren van een productlijst of het navigeren door verschillende weergaven, moet de URL vaak deze wijzigingen weerspiegelen. Je kunt de `replaceState` of `pushState` methoden van de `BrowserHistory` klasse gebruiken om de URL te manipuleren zonder de pagina opnieuw te laden:

- **`pushState`**: Voegt een nieuwe entry toe aan de geschiedenisstack van de browser zonder de pagina opnieuw te laden. Dit is nuttig voor het navigeren tussen verschillende weergaven of dynamische inhoud.
- **`replaceState`**: Werkt de huidige entry in de geschiedenis van de browser bij zonder een nieuwe entry toe te voegen. Dit is ideaal voor het bijwerken van de status binnen dezelfde weergave.

### Voorbeeld: De URL bijwerken met queryparameters {#example-updating-the-url-with-query-parameters}

In dit voorbeeld, wanneer de knop "Update URL" wordt geklikt, wordt de UI bijgewerkt om de geselecteerde categorie en sortering weer te geven en wordt de URL bijgewerkt met nieuwe queryparameters voor `category` en `sort`:

```java
@Route(value = "products")
public class ProductView extends Composite<Div> {
  Paragraph paragraph = new Paragraph();
  Random random = new Random();

  public ProductView() {
    Button update = new Button("Update URL", ButtonTheme.PRIMARY);
    update.onClick(ev -> {
      filter("electronics", String.valueOf(random.nextInt(3) - 1));
    });

    Div div = getBoundComponent();
    div.add(update);
    div.add(paragraph);
  }

  public void filter(String category, String sort) {
    // update de UI
    updateUI(category, sort);

    // werk de URL bij
    updateUrl(category, sort);
  }

  private void updateUI(String category, String sort) {
    paragraph.setText("Categorie bekijken: " + category + " en sorteren op: " + sort);
  }

  private void updateUrl(String category, String sort) {
    ParametersBag queryParameters = new ParametersBag();
    queryParameters.put("category", category);
    queryParameters.put("sort", sort);

    Location newLocation = new Location("/products?" + queryParameters.getQueryString());
    Router.getCurrent().getHistory()
        // Werk de URL bij zonder de pagina opnieuw te laden
        .replaceState(null, newLocation);
  }
}
```

### Uitleg: {#explanation}

- **`filter` Methode**: De methode handelt het bijwerken van zowel de UI als de URL op basis van de geselecteerde `category` en `sort`.
- **`updateUrl` Methode**: Deze methode maakt een nieuwe `ParametersBag` voor queryparameters, construeert een nieuwe URL en gebruikt vervolgens `replaceState` om de URL van de browser bij te werken zonder de pagina opnieuw te laden.
- **`replaceState`**: Deze methode wijzigt de URL naar de nieuwe locatie terwijl de huidige status behouden blijft, zonder een pagina herlaad.

## Status opslaan en herstellen in browsergeschiedenis {#saving-and-restoring-state-in-browser-history}

Naast het bijwerken van de URL is het mogelijk om willekeurige statusobjecten in de geschiedenis van de browser op te slaan. Dit betekent dat je aanvullende gegevens met betrekking tot de huidige weergave kunt opslaan (bijvoorbeeld: formulierinvoeren, filters, enz.) zonder ze direct in de URL te embedden.

### Voorbeeld: Opslaan van selectie status {#example-saving-selection-state}

In het volgende voorbeeld bestaat een `ProfileView` uit verschillende tabbladen (Profiel, Bestellingen en Instellingen). Wanneer de gebruiker tussen tabs schakelt, wordt de status van het geselecteerde tabblad opgeslagen in de geschiedenis van de browser met behulp van `replaceState`. Dit stelt de app in staat om het laatste actieve tabblad te onthouden als de gebruiker terugkeert naar deze weergave of de pagina vernieuwt.

```java
@Route(value = "profile")
public class ProfileView extends Composite<Div> implements DidEnterObserver {
  TabbedPane sections = new TabbedPane();
  int currentSection = 0;

  public ProfileView() {
    sections.addTab("Profiel");
    sections.addTab("Bestellingen");
    sections.addTab("Instellingen");

    sections.onSelect(ev -> {
      currentSection = ev.getTabIndex();
       // Sla de status op met replaceState
      updateState(currentSection);
    });

    getBoundComponent().add(sections);
  }

  @Override
  public void onDidEnter(DidEnterEvent event, ParametersBag parameters) {
    // Probeer de laatst opgeslagen sectie uit de status van de browsergeschiedenis op te halen
    Optional<Integer> lastSavedSection = event.getState(Integer.class);

    // Als er een sectie was opgeslagen, herstel de tabselectie
    lastSavedSection.ifPresent(section -> sections.select(section));
  }

  private void updateState(int section) {
    Router router = Router.getCurrent();
    Location currentLocation = router.getHistory().getLocation().get();

    // Werk de huidige status bij met de geselecteerde sectie
    Router.getCurrent().getHistory()
        .replaceState(section, currentLocation);
  }
}
```

### Uitleg: {#explanation-1}

1. **TabbedPane Component**: De weergave bestaat uit een `TabbedPane` component, dat drie tabs heeft: Profiel, Bestellingen en Instellingen.
2. **Status opslaan bij tabwijziging**: Elke keer dat een tab wordt geselecteerd, wordt de huidige sectie-index opgeslagen in de geschiedenis van de browser met behulp van de `replaceState` methode.
3. **Herstellen van status bij navigatie**: Wanneer de gebruiker terugnavigeert naar de `ProfileView`, haalt de app de opgeslagen sectie op uit de geschiedenis met `event.getState()` en herstelt de juiste tabselectie.
