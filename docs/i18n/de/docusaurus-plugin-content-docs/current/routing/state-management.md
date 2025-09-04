---
sidebar_position: 7
title: State Management
_i18n_hash: e10d155e02722ea38419a79813a2f5af
---
Creating seamless, dynamic user experiences often requires that the state of your web app be reflected in the URL and retained across browser navigation events. You can achieve this without reloading the page by leveraging URL-Parameteraktualisierungen und das Management des Browserverlaufs. This ensures that users can share, bookmark, or return to specific views with the app fully aware of their prior interactions.

## Aktualisierung der URL {#updating-the-url}

When the state of a web page changes, like filtering a product list or navigating through different views, you often need the URL to reflect those changes. You can use the `replaceState` or `pushState` methods provided by the `BrowserHistory` class to manipulate the URL without reloading the page:

- **`pushState`**: Fügt einen neuen Eintrag zum Browserverlauf hinzu, ohne die Seite neu zu laden. This is useful for navigating between different views or dynamic content.
- **`replaceState`**: Aktualisiert den aktuellen Eintrag im Browserverlauf, ohne einen neuen Eintrag hinzuzufügen. This is ideal for updating state within the same view.

### Beispiel: Aktualisierung der URL mit Abfrageparametern {#example-updating-the-url-with-query-parameters}

In diesem Beispiel, wenn der "URL aktualisieren"-Button geklickt wird, wird die UI aktualisiert, um die ausgewählte Kategorie und Sortierung anzuzeigen, und die URL wird mit neuen Abfrageparametern für `category` und `sort` aktualisiert:

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
    // update the UI
    updateUI(category, sort);

    // update the URL
    updateUrl(category, sort);
  }

  private void updateUI(String category, String sort) {
    paragraph.setText("Anzeigen der Kategorie: " + category + " und Sortieren nach: " + sort);
  }

  private void updateUrl(String category, String sort) {
    ParametersBag queryParameters = new ParametersBag();
    queryParameters.put("category", category);
    queryParameters.put("sort", sort);

    Location newLocation = new Location("/products?" + queryParameters.getQueryString());
    Router.getCurrent().getHistory()
        // Update the URL without reloading the page
        .replaceState(null, newLocation);
  }
}
```

### Erklärung: {#explanation}

- **`filter` Methode**: Die Methode kümmert sich um die Aktualisierung sowohl der UI als auch der URL basierend auf der ausgewählten `category` und `sort`.
- **`updateUrl` Methode**: Diese Methode erstellt eine neue `ParametersBag` für Abfrageparameter, konstruiert eine neue URL und verwendet dann `replaceState`, um die URL des Browsers zu aktualisieren, ohne die Seite neu zu laden.
- **`replaceState`**: Diese Methode ändert die URL auf den neuen Standort, während der aktuelle Zustand beibehalten wird, ohne einen Seitenneuladevorgang zu verursachen.

## Speichern und Wiederherstellen des Zustands im Browserverlauf {#saving-and-restoring-state-in-browser-history}

In addition to updating the URL, it's possible to save arbitrary state objects in the browser's history. This means you can stash additional data related to the current view (for instance: form inputs, filters, etc.) without embedding them directly into the URL.

### Beispiel: Speichern des Auswahlzustands {#example-saving-selection-state}

Im folgenden Beispiel besteht eine `ProfileView` aus mehreren Tabs (Profil, Bestellungen und Einstellungen). Wenn der Benutzer zwischen den Tabs wechselt, wird der Zustand des ausgewählten Tabs im Browserverlauf mithilfe von `replaceState` gespeichert. This allows the app to remember the last active tab if the user navigates back to this view or refreshes the page.

```java
@Route(value = "profile")
public class ProfileView extends Composite<Div> implements DidEnterObserver {
  TabbedPane sections = new TabbedPane();
  int currentSection = 0;

  public ProfileView() {
    sections.addTab("Profil");
    sections.addTab("Bestellungen");
    sections.addTab("Einstellungen");

    sections.onSelect(ev -> {
      currentSection = ev.getTabIndex();
       // Save the state using replaceState
      updateState(currentSection);
    });

    getBoundComponent().add(sections);
  }

  @Override
  public void onDidEnter(DidEnterEvent event, ParametersBag parameters) {
    // Versuchen Sie, den zuletzt gespeicherten Abschnitt aus dem Browserverlauf abzurufen
    Optional<Integer> lastSavedSection = event.getState(Integer.class);

    // Wenn ein Abschnitt gespeichert wurde, stellen Sie die Tab-Auswahl wieder her
    lastSavedSection.ifPresent(section -> sections.select(section));
  }

  private void updateState(int section) {
    Router router = Router.getCurrent();
    Location currentLocation = router.getHistory().getLocation().get();

    // Aktualisieren Sie den aktuellen Zustand mit dem ausgewählten Abschnitt
    Router.getCurrent().getHistory()
        .replaceState(section, currentLocation);
  }
}
```

### Erklärung: {#explanation-1}

1. **TabbedPane-Komponente**: Die Ansicht besteht aus einer `TabbedPane`-Komponente, die drei Tabs enthält: Profil, Bestellungen und Einstellungen.
2. **Zustandspeicherung bei Tab-Wechsel**: Jedes Mal, wenn ein Tab ausgewählt wird, wird der aktuelle Abschnittsindex im Browserverlauf mithilfe der Methode `replaceState` gespeichert.
3. **Wiederherstellung des Zustands bei der Navigation**: Wenn der Benutzer zur `ProfileView` zurückkehrt, ruft die App den gespeicherten Abschnitt aus dem Verlauf mit `event.getState()` ab und stellt die richtige Tab-Auswahl wieder her.
