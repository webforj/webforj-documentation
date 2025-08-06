---
sidebar_position: 4
title: Route Navigation
_i18n_hash: cf1f9e79aa81f240306313a7c0c5a9c4
---
In webforJ ist die Navigation zwischen Routen der zentrale Mechanismus, um Sichtweisen und Komponenten basierend auf Benutzeraktionen oder URL-Änderungen zu wechseln. Die Navigation ermöglicht es den Benutzern, nahtlos zwischen verschiedenen Teilen der App zu wechseln, ohne die Seite zu aktualisieren. Diese clientseitige Navigation hält die App reaktionsschnell und flüssig, während der Zustand der App erhalten bleibt.

## Programmatische Navigation {#programmatic-navigation}

Sie können die Navigation von überall in Ihrer App auslösen, indem Sie die Klasse `Router` verwenden. Dies ermöglicht dynamische Änderungen an den angezeigten Komponenten basierend auf Ereignissen wie Mausklicks oder anderen Benutzerinteraktionen.

Hier ist ein Beispiel dafür, wie man zu einer bestimmten Route navigiert:

```java
@Route(value = "dashboard")
public class DashboardView extends Composite<Div> {
  // Komponentenlogik hier
}
```

```java
// zu der Ansicht navigieren
Router.getCurrent().navigate(DashboardView.class);
```

In diesem Beispiel führt die programmgesteuerte Navigation zur `DashboardView`-Komponente dazu, dass die `DashboardView`-Komponente gerendert wird und die URL des Browsers auf `/dashboard` aktualisiert wird.

Es ist auch möglich, zur Ansicht zu navigieren, indem eine neue `Location` übergeben wird.

```java
Router.getCurrent().navigate(new Location("/dashboard"));
```

:::tip Klasse vs. Location: Methoden zur Ansichts-Routing
Beim Navigieren zwischen Ansichten haben Entwickler zwei Optionen: Sie können entweder die Klassenansicht oder die Routenklasse übergeben, wodurch der Router die URL automatisch generiert und die Ansicht rendert, oder die Location direkt übergeben. Beide Methoden sind gültig, aber **die Verwendung der Klassenansicht ist der bevorzugte Ansatz**, da er bessere Flexibilität für zukünftige Änderungen bietet. Wenn Sie beispielsweise später beschließen, die Route zu aktualisieren, müssen Sie nur die Annotation `@Route` ändern, ohne den Code zu ändern, der die Klassenansicht für die Navigation verwendet.
:::

### Navigation mit Parametern {#navigation-with-parameters}

Wenn Sie Parameter zusammen mit der Route übergeben müssen, erlaubt webforJ, Parameter in der URL einzubetten. Hier ist, wie Sie zu einer Route mit Parametern navigieren können:

```java
@Route("user/:id")
public class UserProfileView extends Composite<Div> implements DidEnterObserver {
  H1 title = new H1();

  public UserProfileView() {
    getBoundComponent().add(title);
  }

  public void setTile(String title) {
    this.title.setText(title);
  }

  public String getTitle() {
    return title.getText();
  }

  @Override
  public void onDidEnter(DidEnterEvent event, ParametersBag parameters) {
    String id = parameters.getAlpha("id").orElse("Unbekannt");
    setTile(id);
  }
}
```

```java
// zu der Ansicht navigieren und die Benutzer-ID übergeben
Router.getCurrent().navigate(
  UserProfileView.class,
  ParametersBag.of("id=JohnDoe")
);
```

Dies navigiert zu `/user/JohnDoe`, wobei `JohnDoe` eine Benutzer-ID darstellen könnte. Die Komponente für diese Route kann dann den Parameter extrahieren und entsprechend verwenden.

## Erstellte Ansichtsinstanz {#created-view-instance}

Die Methode `navigate` akzeptiert einen Java `Consumer`, der aufgerufen wird, sobald die Navigation abgeschlossen ist. Der `Consumer` erhält die Instanz der erstellten Ansichtskomponente, eingewickelt in ein java `Optional`, was es dem Entwickler ermöglicht, mit der Ansicht nach einer erfolgreichen Navigation zu interagieren.

```java
Router.getCurrent().navigate(
    UserProfileView.class,
    ParametersBag.of("id=JohnDoe"), (component) -> {
      component.ifPresent(view -> {
        console().log("Der neue Titel ist: " + view.getTitle());
      });
    });
```

:::info Null-Instanzen
Der Consumer erhält ein Java `Optional` für die Komponente, da sie `null` sein könnte oder aus verschiedenen Gründen nicht erstellt wurde. Die Komponente könnte beispielsweise nicht gerendert werden, wenn die Navigationsbeobachter die Navigation ablehnen und den Prozess stoppen.
:::

## Navigationsoptionen {#navigation-options}

Die Klasse `NavigationOptions` ermöglicht es den Entwicklern, die Navigation innerhalb der App fein abzustimmen. Durch das Setzen bestimmter Optionen können Sie das Verhalten der Navigation steuern, z. B. ob die Browserhistorie aktualisiert wird, ob Lebenszyklusbeobachter aufgerufen werden oder ob Navigationsereignisse ausgelöst werden.

```java
NavigationOptions options = new NavigationOptions();
options.setUpdateHistory(false);

Router.getCurrent().navigate(
  new Location("user/JohnDoe"), options);
```

### Setzen von Navigationsoptionen {#setting-navigation-options}

Die Klasse `NavigationOptions` bietet mehrere Methoden zur Anpassung des Navigationsverhaltens. Dazu gehört die Steuerung, wie Routen behandelt werden, ob Beobachter benachrichtigt werden und wie die Historie des Browsers aktualisiert wird.

Hier sind die wichtigsten Konfigurationsoptionen, die innerhalb von `NavigationOptions` verfügbar sind:

1. **Navigationsart (`setNavigationType`)**  
   Diese Option definiert, ob die neue Route zur Browserhistorie hinzugefügt oder die aktuelle Route ersetzt werden soll.

   - **`PUSH`**: Fügt die neue Route zum Verlaufsstapel hinzu und bewahrt den aktuellen Standort.
   - **`REPLACE`**: Ersetzt die aktuelle Route im Verlaufsstapel durch den neuen Standort, wodurch verhindert wird, dass die Zurück-Taste zur vorherigen Route navigiert.

2. **Ereignisse auslösen (`setFireEvents`)**  
   Bestimmt, ob während der Navigation [Lebenszyklusereignisse](./navigation-lifecycle/navigation-events) ausgelöst werden sollen. Standardmäßig ist dies auf `true` gesetzt, und Ereignisse werden ausgelöst. Wenn auf `false` gesetzt, werden keine Ereignisse ausgelöst, was nützlich für stille Navigation ist.

3. **Beobachter aufrufen (`setInvokeObservers`)**  
   Dieses Flag steuert, ob die Navigation [Beobachter](./navigation-lifecycle/observers) innerhalb der navigierten Komponenten auslösen soll. Beobachter behandeln typischerweise Ereignisse wie den Eintritt oder Austritt aus der Route. Wenn dies auf `false` gesetzt ist, werden keine Beobachter aufgerufen.

4. **Historie aktualisieren (`setUpdateHistory`)**  
   Wenn auf `false` gesetzt, verhindert diese Option, dass der Standort in der Historie aktualisiert wird. Dies ist nützlich, wenn Sie die Ansicht ändern möchten, ohne die Zurück- oder Vorwärtsnavigation des Browsers zu beeinträchtigen. Es beeinflusst nur das Geschichtsmanagement, nicht den Lebenszyklus der Komponenten oder die Routenbehandlung.

5. **Zustandsobjekt (`setState`)**  
   [Das Zustandsobjekt](./state-management#saving-and-restoring-state-in-browser-history) ermöglicht es Ihnen, zusätzliche Informationen beim Aktualisieren der Historie des Browsers zu übergeben. Dieses Objekt wird im Historiezustand des Browsers gespeichert und kann später für benutzerdefinierte Zwecke verwendet werden, z. B. zum Speichern des Zustands der App während der Navigation.

## Generieren von Standorten für Ansichten {#generating-locations-for-views}

Der Router kann den Standort für Ansichten basierend auf dem Routenmuster, das in der Ansicht definiert ist, generieren. Sie können auch zusätzliche Parameter für dynamische und erforderliche Segmente in der URL bereitstellen. Dies kann nützlich sein, wenn Sie Links erstellen oder direkten Zugriff auf bestimmte Ansichten in der App teilen möchten.

Hier ist, wie man eine `Location` basierend auf einer Klassenansicht und Routenparametern generiert:

```java
Class<UserProfileView> userProfileView = UserProfileView.class;
ParametersBag params = ParametersBag.of("id=JohnDoe");

Optional<Location> location = Router.getCurrent().getLocation(userProfileView, params);
console().log(location.get());
```

Dies erzeugt ein `Location`-Objekt mit dem Pfad `/user/JohnDoe`, der vollständigen URI als Zeichenfolge.
