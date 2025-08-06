---
sidebar_position: 7
title: State Management
_i18n_hash: cba905abd01a780dea1f459ec4397cda
---
Die Erstellung nahtloser, dynamischer Benutzererlebnisse erfordert oft, dass der Zustand Ihrer Web-App in der URL widergespiegelt und über Browser-NavigationEvents hinweg beibehalten wird. Sie können dies erreichen, ohne die Seite neu zu laden, indem Sie URL-Parameteraktualisierungen und das Management des Browser-Verlaufs nutzen. Dies gewährleistet, dass Benutzer spezifische Ansichten teilen, einfügen oder zu ihnen zurückkehren können, während die App sich ihrer vorherigen Interaktionen bewusst ist.

## Aktualisierung der URL {#updating-the-url}

Wenn sich der Zustand einer Webseite ändert, wie das Filtern einer Produktauswahl oder das Navigieren durch verschiedene Ansichten, müssen Sie häufig die URL aktualisieren, um diese Änderungen widerzuspiegeln. Sie können die Methoden `replaceState` oder `pushState`, die von der Klasse `BrowserHistory` bereitgestellt werden, verwenden, um die URL zu manipulieren, ohne die Seite neu zu laden:

- **`pushState`**: Fügt einen neuen Eintrag zum Verlauf des Browsers hinzu, ohne die Seite neu zu laden. Dies ist nützlich für die Navigation zwischen verschiedenen Ansichten oder dynamischen Inhalten.
- **`replaceState`**: Aktualisiert den aktuellen Eintrag im Verlauf des Browsers, ohne einen neuen Eintrag hinzuzufügen. Dies ist ideal, um den Zustand innerhalb derselben Ansicht zu aktualisieren.

### Beispiel: Aktualisierung der URL mit Abfrageparametern {#example-updating-the-url-with-query-parameters}

In diesem Beispiel wird die Benutzeroberfläche aktualisiert, um die ausgewählte Kategorie und Sortierung anzuzeigen, und die URL wird mit neuen Abfrageparametern für `category` und `sort` aktualisiert, wenn der Button "URL aktualisieren" angeklickt wird:

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
    // UI aktualisieren
    updateUI(category, sort);

    // URL aktualisieren
    updateUrl(category, sort);
  }

  private void updateUI(String category, String sort) {
    paragraph.setText("Kategorie anzeigen: " + category + " und sortieren nach: " + sort);
  }

  private void updateUrl(String category, String sort) {
    ParametersBag queryParameters = new ParametersBag();
    queryParameters.put("category", category);
    queryParameters.put("sort", sort);

    Location newLocation = new Location("/products?" + queryParameters.getQueryString());
    Router.getCurrent().getHistory()
        // URL aktualisieren, ohne die Seite neu zu laden
        .replaceState(null, newLocation);
  }
}
```

### Erklärung: {#explanation}

- **`filter`-Methode**: Diese Methode kümmert sich um die Aktualisierung sowohl der Benutzeroberfläche als auch der URL basierend auf der ausgewählten `category` und `sort`.
- **`updateUrl`-Methode**: Diese Methode erstellt einen neuen `ParametersBag` für Abfrageparameter, konstruiert eine neue URL und verwendet dann `replaceState`, um die URL des Browsers zu aktualisieren, ohne die Seite neu zu laden.
- **`replaceState`**: Diese Methode ändert die URL zur neuen Adresse, während der aktuelle Zustand beibehalten wird, ohne dass ein Seitenneuladen erfolgt.

## Speichern und Wiederherstellen des Zustands im Verlauf des Browsers {#saving-and-restoring-state-in-browser-history}

Neben der Aktualisierung der URL ist es möglich, beliebige Zustandsobjekte im Verlauf des Browsers zu speichern. Dies bedeutet, dass Sie zusätzliche Daten, die mit der aktuellen Ansicht zusammenhängen (z. B. Formulareingaben, Filter usw.), speichern können, ohne sie direkt in die URL einzubetten.

### Beispiel: Speichern des Auswahlzustands {#example-saving-selection-state}

Im folgenden Beispiel besteht eine `ProfileView` aus mehreren Tabs (Profil, Bestellungen und Einstellungen). Wenn der Benutzer zwischen den Tabs wechselt, wird der Zustand des ausgewählten Tabs im Verlauf des Browsers mit `replaceState` gespeichert. Dies ermöglicht es der App, den zuletzt aktiven Tab zu merken, wenn der Benutzer zu dieser Ansicht navigiert oder die Seite aktualisiert.

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
       // Zustand speichern mit replaceState
      updateState(currentSection);
    });

    getBoundComponent().add(sections);
  }

  @Override
  public void onDidEnter(DidEnterEvent event, ParametersBag parameters) {
    // Versuchen, den zuletzt gespeicherten Abschnitt aus dem Verlauf des Browsers abzurufen
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
2. **Zustandspeicherung beim Tab-Wechsel**: Jedes Mal, wenn ein Tab ausgewählt wird, wird der aktuelle Abschnittsindex im Verlauf des Browsers mit der Methode `replaceState` gespeichert.
3. **Wiederherstellung des Zustands bei der Navigation**: Wenn der Benutzer zur `ProfileView` zurückkehrt, ruft die App den gespeicherten Abschnitt aus dem Verlauf ab, indem sie `event.getState()` verwendet, und stellt die korrekte Tab-Auswahl wieder her.
