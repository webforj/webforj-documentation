---
sidebar_position: 7
title: State Management
_i18n_hash: 0766f2c08642792af2fe62e832b4fa1a
---
Creating seamless, dynamische gebruikerservaringen vereist vaak dat de staat van uw webapp in de URL wordt weerspiegeld en behouden blijft tijdens browsernavigatie-evenementen. U kunt dit bereiken zonder de pagina te herladen door gebruik te maken van URL-parameterupdates en het beheer van de geschiedenisstatus van de browser. Dit zorgt ervoor dat gebruikers specifieke weergaven kunnen delen, bookmarken of terugkeren naar de app, waarbij deze volledig op de hoogte is van hun eerdere interacties.

## De URL bijwerken {#updating-the-url}

Wanneer de staat van een webpagina verandert, zoals het filteren van een productlijst of navigeren tussen verschillende weergaven, moet u vaak de URL laten weerspiegelen welke wijzigingen zijn aangebracht. U kunt de `replaceState` of `pushState` methoden van de `BrowserHistory` klasse gebruiken om de URL te manipuleren zonder de pagina te herladen:

- **`pushState`**: Voegt een nieuwe entiteit toe aan de geschiedenisstack van de browser zonder de pagina te herladen. Dit is handig voor het navigeren tussen verschillende weergaven of dynamische inhoud.
- **`replaceState`**: Werk de huidige entiteit in de geschiedenis van de browser bij zonder een nieuwe entiteit toe te voegen. Dit is ideaal voor het bijwerken van de staat binnen dezelfde weergave.

### Voorbeeld: De URL bijwerken met queryparameters {#example-updating-the-url-with-query-parameters}

In dit voorbeeld, wanneer de knop "Update URL" wordt ingedrukt, wordt de UI bijgewerkt om de geselecteerde categorie en sortering weer te geven, en wordt de URL bijgewerkt met nieuwe queryparameters voor `category` en `sort`:

```java
@Route(value = "products")
public class ProductView extends Composite<Div> {
  private final Div self = getBoundComponent();
  Paragraph paragraph = new Paragraph();
  Random random = new Random();

  public ProductView() {
    Button update = new Button("Update URL", ButtonTheme.PRIMARY);
    update.onClick(ev -> {
      filter("electronics", String.valueOf(random.nextInt(3) - 1));
    });

    self.add(update);
    self.add(paragraph);
  }

  public void filter(String category, String sort) {
    // update de UI
    updateUI(category, sort);

    // update de URL
    updateUrl(category, sort);
  }

  private void updateUI(String category, String sort) {
    paragraph.setText("Weergave categorie: " + category + " en sorteren op: " + sort);
  }

  private void updateUrl(String category, String sort) {
    ParametersBag queryParameters = new ParametersBag();
    queryParameters.put("category", category);
    queryParameters.put("sort", sort);

    Location newLocation = new Location("/products?" + queryParameters.getQueryString());
    Router.getCurrent().getHistory()
        // Update de URL zonder de pagina te herladen
        .replaceState(null, newLocation);
  }
}
```

### Uitleg: {#explanation}

- **`filter` Methode**: De methode zorgt voor het bijwerken van zowel de UI als de URL op basis van de geselecteerde `category` en `sort`.
- **`updateUrl` Methode**: Deze methode maakt een nieuwe `ParametersBag` voor queryparameters, construeert een nieuwe URL, en gebruikt vervolgens `replaceState` om de URL van de browser bij te werken zonder de pagina te herladen.
- **`replaceState`**: Deze methode verandert de URL naar de nieuwe locatie terwijl de huidige staat behouden blijft, zonder dat er een pagina-herlaad plaatsvindt.

## Status opslaan en herstellen in de browsergeschiedenis {#saving-and-restoring-state-in-browser-history}

Naast het bijwerken van de URL, is het mogelijk om willekeurige statusobjecten in de geschiedenis van de browser op te slaan. Dit betekent dat u extra gegevens met betrekking tot de huidige weergave (bijvoorbeeld: formulierinvoeren, filters, enz.) kunt opslaan zonder deze direct in de URL op te nemen.

### Voorbeeld: Opslaan van de geselecteerde staat {#example-saving-selection-state}

In het volgende voorbeeld bestaat een `ProfileView` uit verschillende tabbladen (Profiel, Bestellingen en Instellingen). Wanneer de gebruiker tussen tabbladen wisselt, wordt de staat van het geselecteerde tabblad opgeslagen in de geschiedenis van de browser met behulp van `replaceState`. Dit stelt de app in staat om het laatst actieve tabblad te onthouden als de gebruiker terug navigeert naar deze weergave of de pagina vernieuwt.

```java
@Route(value = "profile")
public class ProfileView extends Composite<Div> implements DidEnterObserver {
  private final Div self = getBoundComponent();
  TabbedPane sections = new TabbedPane();
  int currentSection = 0;

  public ProfileView() {
    sections.addTab("Profiel");
    sections.addTab("Bestellingen");
    sections.addTab("Instellingen");

    sections.onSelect(ev -> {
      currentSection = ev.getTabIndex();
       // Sla de staat op met replaceState
      updateState(currentSection);
    });

    self.add(sections);
  }

  @Override
  public void onDidEnter(DidEnterEvent event, ParametersBag parameters) {
    // Probeer de laatst opgeslagen sectie uit de status van de browsergeschiedenis op te halen
    Optional<Integer> lastSavedSection = event.getState(Integer.class);

    // Als een sectie was opgeslagen, herstel dan de tabselectie
    lastSavedSection.ifPresent(section -> sections.select(section));
  }

  private void updateState(int section) {
    Router router = Router.getCurrent();
    Location currentLocation = router.getHistory().getLocation().get();

    // Update de huidige status met de geselecteerde sectie
    Router.getCurrent().getHistory()
        .replaceState(section, currentLocation);
  }
}
```

### Uitleg: {#explanation-1}

1. **TabbedPane Component**: De weergave bestaat uit een `TabbedPane` component, dat drie tabbladen heeft: Profiel, Bestellingen en Instellingen.
2. **Status opslaan bij tabwisseling**: Elke keer wanneer een tabblad wordt geselecteerd, wordt de index van de actuele sectie opgeslagen in de geschiedenis van de browser met behulp van de `replaceState` methode.
3. **Herstellen van de status bij navigatie**: Wanneer de gebruiker terugnavigeert naar de `ProfileView`, haalt de app de opgeslagen sectie op uit de geschiedenis met behulp van `event.getState()` en herstelt de correcte tabselectie.
