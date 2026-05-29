---
sidebar_position: 7
title: State Management
_i18n_hash: 0766f2c08642792af2fe62e832b4fa1a
---
Creating seamless, dynamic Benutzererfahrungen erfordert oft, dass der Zustand Ihrer Webanwendung in der URL reflektiert und über Browser-Navigationsevents hinweg beibehalten wird. Dies können Sie erreichen, ohne die Seite neu zu laden, indem Sie URL-Parameteraktualisierungen und die Verwaltung des Browser-Verlaufs nutzen. Dies stellt sicher, dass Benutzer spezifische Ansichten teilen, als Lesezeichen speichern oder zurückkehren können, während die App sich ihrer vorherigen Interaktionen voll bewusst ist.

## Aktualisieren der URL {#updating-the-url}

Wenn sich der Zustand einer Webseite ändert, wie zum Beispiel beim Filtern einer Produktliste oder beim Navigieren durch verschiedene Ansichten, müssen Sie oft die URL an diese Änderungen anpassen. Sie können die Methoden `replaceState` oder `pushState`, die von der Klasse `BrowserHistory` bereitgestellt werden, verwenden, um die URL zu manipulieren, ohne die Seite neu zu laden:

- **`pushState`**: Fügt einen neuen Eintrag zum Verlauf des Browsers hinzu, ohne die Seite neu zu laden. Dies ist nützlich für die Navigation zwischen verschiedenen Ansichten oder dynamischen Inhalten.
- **`replaceState`**: Aktualisiert den aktuellen Eintrag im Verlauf des Browsers, ohne einen neuen Eintrag hinzuzufügen. Dies ist ideal, um den Zustand innerhalb derselben Ansicht zu aktualisieren.

### Beispiel: Aktualisieren der URL mit Abfrageparametern {#example-updating-the-url-with-query-parameters}

In diesem Beispiel wird die Benutzeroberfläche aktualisiert, um die ausgewählte Kategorie und Sortierung anzuzeigen, und die URL wird mit neuen Abfrageparametern für `category` und `sort` aktualisiert, wenn der Button "Update URL" angeklickt wird:

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
    // UI aktualisieren
    updateUI(category, sort);

    // URL aktualisieren
    updateUrl(category, sort);
  }

  private void updateUI(String category, String sort) {
    paragraph.setText("Angezeigte Kategorie: " + category + " und sortiert nach: " + sort);
  }

  private void updateUrl(String category, String sort) {
    ParametersBag queryParameters = new ParametersBag();
    queryParameters.put("category", category);
    queryParameters.put("sort", sort);

    Location newLocation = new Location("/products?" + queryParameters.getQueryString());
    Router.getCurrent().getHistory()
        // Aktualisiert die URL, ohne die Seite neu zu laden
        .replaceState(null, newLocation);
  }
}
```

### Erklärung: {#explanation}

- **`filter` Methode**: Die Methode behandelt die Aktualisierung sowohl der UI als auch der URL basierend auf der ausgewählten `category` und `sort`.
- **`updateUrl` Methode**: Diese Methode erstellt ein neues `ParametersBag` für Abfrageparameter, konstruiert eine neue URL und verwendet dann `replaceState`, um die URL des Browsers zu aktualisieren, ohne die Seite neu zu laden.
- **`replaceState`**: Diese Methode ändert die URL auf den neuen Standort, während der aktuelle Zustand beibehalten wird, ohne eine Seitenaktualisierung zu verursachen.

## Speichern und Wiederherstellen des Zustands im Browserverlauf {#saving-and-restoring-state-in-browser-history}

Zusätzlich zur Aktualisierung der URL ist es möglich, beliebige Zustandsobjekte im Verlauf des Browsers zu speichern. Das bedeutet, dass Sie zusätzliche Daten, die mit der aktuellen Ansicht verbunden sind (zum Beispiel: Formulareingaben, Filter usw.), speichern können, ohne sie direkt in die URL einzufügen.

### Beispiel: Speichern des Auswahlzustands {#example-saving-selection-state}

Im folgenden Beispiel besteht eine `ProfileView` aus mehreren Tabs (Profil, Bestellungen und Einstellungen). Wenn der Benutzer zwischen den Tabs wechselt, wird der Zustand des ausgewählten Tabs im Verlauf des Browsers mit `replaceState` gespeichert. Dies ermöglicht es der App, den zuletzt aktiven Tab zu merken, wenn der Benutzer zu dieser Ansicht zurücknavigiert oder die Seite aktualisiert.

```java
@Route(value = "profile")
public class ProfileView extends Composite<Div> implements DidEnterObserver {
  private final Div self = getBoundComponent();
  TabbedPane sections = new TabbedPane();
  int currentSection = 0;

  public ProfileView() {
    sections.addTab("Profil");
    sections.addTab("Bestellungen");
    sections.addTab("Einstellungen");

    sections.onSelect(ev -> {
      currentSection = ev.getTabIndex();
       // Zustand mit replaceState speichern
      updateState(currentSection);
    });

    self.add(sections);
  }

  @Override
  public void onDidEnter(DidEnterEvent event, ParametersBag parameters) {
    // Versuchen, den zuletzt gespeicherten Abschnitt aus dem Zustand des Browserverlaufs abzurufen
    Optional<Integer> lastSavedSection = event.getState(Integer.class);

    // Wenn ein Abschnitt gespeichert wurde, die Tab-Auswahl wiederherstellen
    lastSavedSection.ifPresent(section -> sections.select(section));
  }

  private void updateState(int section) {
    Router router = Router.getCurrent();
    Location currentLocation = router.getHistory().getLocation().get();

    // Den aktuellen Zustand mit dem ausgewählten Abschnitt aktualisieren
    Router.getCurrent().getHistory()
        .replaceState(section, currentLocation);
  }
}
```

### Erklärung: {#explanation-1}

1. **TabbedPane-Komponente**: Die Ansicht besteht aus einer `TabbedPane`-Komponente, die drei Tabs hat: Profil, Bestellungen und Einstellungen.
2. **Zustandsprüfung bei Tab-Wechsel**: Jedes Mal, wenn ein Tab ausgewählt wird, wird der aktuelle Abschnittsindex im Verlauf des Browsers mithilfe der Methode `replaceState` gespeichert.
3. **Wiederherstellen des Zustands bei der Navigation**: Wenn der Benutzer zur `ProfileView` zurücknavigiert, ruft die App den gespeicherten Abschnitt aus dem Verlauf mithilfe von `event.getState()` ab und stellt die korrekte Tab-Auswahl wieder her.
