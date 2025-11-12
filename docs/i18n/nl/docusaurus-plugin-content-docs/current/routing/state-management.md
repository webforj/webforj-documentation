---
sidebar_position: 7
title: Toestandbeheer
_i18n_hash: e10d155e02722ea38419a79813a2f5af
---
Het creëren van naadloze, dynamische gebruikers ervaringen vereist vaak dat de staat van je webapplicatie wordt weergegeven in de URL en behouden blijft tijdens navigatie gebeurtenissen in de browser. Dit kan worden bereikt zonder de pagina opnieuw te laden door gebruik te maken van updates van URL-parameters en het beheren van de geschiedenisstatus van de browser. Dit zorgt ervoor dat gebruikers specifieke weergaven kunnen delen, bladwijzers kunnen toevoegen of terug kunnen keren naar eerdere interacties met de app, waarbij de app volledig op de hoogte is van hun eerdere interacties.

## URL Bijwerken {#updating-the-url}

Wanneer de staat van een webpagina verandert, zoals het filteren van een productlijst of navigeren door verschillende weergaven, moet je vaak de URL laten reflecteren op die veranderingen. Je kunt de `replaceState` of `pushState` methoden gebruiken die door de `BrowserHistory` klasse worden aangeboden om de URL te manipuleren zonder de pagina opnieuw te laden:

- **`pushState`**: Voegt een nieuwe invoer toe aan de geschiedenisstack van de browser zonder de pagina opnieuw te laden. Dit is handig voor het navigeren tussen verschillende weergaven of dynamische inhoud.
- **`replaceState`**:werkt de huidige invoer in de geschiedenis van de browser bij zonder een nieuwe invoer toe te voegen. Dit is ideaal voor het bijwerken van de staat binnen dezelfde weergave.

### Voorbeeld: De URL bijwerken met queryparameters {#example-updating-the-url-with-query-parameters}

In dit voorbeeld, wanneer de knop "Update URL" wordt geklikt, wordt de UI bijgewerkt om de geselecteerde categorie en sortering weer te geven, en de URL wordt bijgewerkt met nieuwe queryparameters voor `category` en `sort`:

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

    // update de URL
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
        // Update de URL zonder de pagina opnieuw te laden
        .replaceState(null, newLocation);
  }
}
```

### Uitleg: {#explanation}

- **`filter` Methode**: De methode handelt het bijwerken van zowel de UI als de URL op basis van de geselecteerde `category` en `sort`.
- **`updateUrl` Methode**: Deze methode maakt een nieuwe `ParametersBag` voor queryparameters, construeert een nieuwe URL en gebruikt vervolgens `replaceState` om de URL van de browser bij te werken zonder de pagina opnieuw te laden.
- **`replaceState`**: Deze methode verandert de URL naar de nieuwe locatie terwijl de huidige staat behouden blijft, zonder een paginareload te veroorzaken.

## Status opslaan en herstellen in de browsergeschiedenis {#saving-and-restoring-state-in-browser-history}

Naast het bijwerken van de URL, is het mogelijk om willekeurige statusobjecten in de geschiedenis van de browser op te slaan. Dit betekent dat je extra gegevens met betrekking tot de huidige weergave kunt opslaan (bijvoorbeeld: formulierinvoer, filters, enz.) zonder ze rechtstreeks in de URL op te nemen.

### Voorbeeld: Opslaan van geselecteerde status {#example-saving-selection-state}

In het volgende voorbeeld bestaat een `ProfileView` uit verschillende tabbladen (Profiel, Bestellingen en Instellingen). Wanneer de gebruiker tussen tabbladen schakelt, wordt de status van het geselecteerde tabblad opgeslagen in de geschiedenis van de browser met behulp van `replaceState`. Dit stelt de app in staat om het laatste actieve tabblad te herinneren als de gebruiker terugkeert naar deze weergave of de pagina vernieuwt.

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
    // Probeer het laatste opgeslagen sectie uit de status van de browsergeschiedenis te halen
    Optional<Integer> lastSavedSection = event.getState(Integer.class);

    // Als er een sectie was opgeslagen, herstel dan de tab selectie
    lastSavedSection.ifPresent(section -> sections.select(section));
  }

  private void updateState(int section) {
    Router router = Router.getCurrent();
    Location currentLocation = router.getHistory().getLocation().get();

    // Werk de huidige staat bij met de geselecteerde sectie
    Router.getCurrent().getHistory()
        .replaceState(section, currentLocation);
  }
}
```

### Uitleg: {#explanation-1}

1. **TabbedPane Component**: De weergave bestaat uit een `TabbedPane` component, dat drie tabbladen heeft: Profiel, Bestellingen en Instellingen.
2. **Status Opslaan bij Tabwisseling**: Elke keer dat een tabblad is geselecteerd, wordt de huidige sectie-index opgeslagen in de geschiedenis van de browser met behulp van de `replaceState` methode.
3. **Herstellen van Status bij Navigatie**: Wanneer de gebruiker terugnavigeert naar de `ProfileView`, haalt de app de opgeslagen sectie op uit de geschiedenis met behulp van `event.getState()` en herstelt de juiste tabselectie.
