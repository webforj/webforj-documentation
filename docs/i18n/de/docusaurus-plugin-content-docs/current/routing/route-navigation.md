---
sidebar_position: 4
title: Routenavigation
_i18n_hash: 2ca468b09b2ae9e2ab3813119d31bf44
---
In webforJ ist die Navigation zwischen Routen der zentrale Mechanismus zum Wechseln von Ansichten und Komponenten basierend auf Benutzeraktionen oder URL-Änderungen. Die Navigation ermöglicht es Benutzern, nahtlos zwischen verschiedenen Teilen der Anwendung zu wechseln, ohne die Seite zu aktualisieren. Diese clientseitige Navigation hält die Anwendung responsiv und reibungslos, während der Zustand der Anwendung erhalten bleibt.

## Programmgesteuerte Navigation {#programmatic-navigation}

Sie können die Navigation von überall in Ihrer Anwendung aus über die `Router`-Klasse auslösen. Dies ermöglicht dynamische Änderungen in den angezeigten Komponenten basierend auf Ereignissen wie Mausklicks oder anderen Benutzerinteraktionen.

Hier ist ein Beispiel, wie man zu einer bestimmten Route navigiert:

```java
@Route(value = "dashboard")
public class DashboardView extends Composite<Div> {
  // Komponentelogik hier
}
```

```java
// zur Ansicht navigieren
Router.getCurrent().navigate(DashboardView.class);
```

In diesem Beispiel führt die programmgesteuerte Navigation zur `DashboardView`-Komponente dazu, dass die `DashboardView`-Komponente gerendert wird und die URL des Browsers auf `/dashboard` aktualisiert wird.

Es ist auch möglich, zur Ansicht zu navigieren, indem eine neue `Location` übergeben wird.

```java
Router.getCurrent().navigate(new Location("/dashboard"));
```

:::tip Klasse vs. Location: Methoden für die Ansichtsnavigation
Bei der Navigation zwischen Ansichten haben die Entwickler zwei Optionen: Sie können entweder die Ansicht oder die Routenkasse übergeben, sodass der Router automatisch die URL generiert und die Ansicht rendert, oder die Location direkt übergeben. Beide Methoden sind gültig, aber **die Verwendung der Ansichtsklasse ist der bevorzugte Ansatz**, da er bessere Flexibilität für zukünftige Änderungen bietet. Wenn Sie beispielsweise später entscheiden, die Route zu aktualisieren, müssen Sie nur die Annotation `@Route` ändern, ohne den Code zu ändern, der die Ansichtsklasse für die Navigation verwendet.
:::

### Navigation mit Parametern {#navigation-with-parameters}

Wenn Sie Parameter zusammen mit der Route übergeben müssen, ermöglicht es webforJ, Parameter in der URL einzubetten. So navigieren Sie zu einer Route mit Parametern:

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
// zur Ansicht navigieren und die Benutzer-ID übergeben
Router.getCurrent().navigate(
  UserProfileView.class,
  ParametersBag.of("id=JohnDoe")
);
```

Dies navigiert zu `/user/JohnDoe`, wobei `JohnDoe` möglicherweise eine Benutzer-ID darstellt. Die Komponente für diese Route kann dann den Parameter extrahieren und entsprechend verwenden.

## Erstellt Ansichtinstanz {#created-view-instance}

Die `navigate`-Methode akzeptiert einen Java `Consumer`, der aufgerufen wird, sobald die Navigation abgeschlossen ist. Der `Consumer` erhält die Instanz der erstellten Ansichtskomponente, eingewickelt in ein java `Optional`, sodass der Entwickler nach einer erfolgreichen Navigation mit der Ansicht interagieren kann.

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
Der Consumer erhält ein Java `Optional` für die Komponente, da sie möglicherweise `null` ist oder aus verschiedenen Gründen nicht erstellt wurde. Beispielsweise wird die Komponente möglicherweise nicht gerendert, wenn die Navigationsbeobachter die Navigation ablehnen und den Prozess stoppen.
:::

## Navigationsoptionen {#navigation-options}

Die `NavigationOptions`-Klasse ermöglicht es Entwicklern, zu verfeinern, wie Navigation innerhalb der Anwendung gehandhabt wird. Durch die Festlegung spezifischer Optionen können Sie das Verhalten der Navigation steuern, beispielsweise ob der Verlauf des Browsers aktualisiert werden soll, Lebenszyklusbeobachter aufgerufen werden sollen oder sogar Navigationsereignisse ausgelöst werden sollen.

```java
NavigationOptions options = new NavigationOptions();
options.setUpdateHistory(false);

Router.getCurrent().navigate(
  new Location("user/JohnDoe"), options);
```

### Festlegen von Navigationsoptionen {#setting-navigation-options}

Die `NavigationOptions`-Klasse bietet mehrere Methoden zur Anpassung des Navigationsverhaltens. Dazu gehört die Steuerung, wie Routen behandelt werden, ob Beobachter benachrichtigt werden und wie der Verlauf des Browsers aktualisiert wird.

Hier sind die wichtigsten Konfigurationsoptionen, die innerhalb von `NavigationOptions` verfügbar sind:

1. **Navigationstyp (`setNavigationType`)**  
   Diese Option definiert, ob die neue Route zum Verlauf des Browsers hinzugefügt oder die aktuelle Route ersetzt werden soll.

   - **`PUSH`**: Fügt die neue Route zum Verlauf hinzu und bewahrt den aktuellen Standort.
   - **`REPLACE`**: Ersetzt die aktuelle Route im Verlauf durch den neuen Standort, sodass der Zurück-Button nicht zur vorherigen Route navigiert.

2. **Ereignisse auslösen (`setFireEvents`)**  
   Bestimmt, ob während der Navigation [Lebenszyklusereignisse](./navigation-lifecycle/navigation-events) ausgelöst werden sollen. Standardmäßig ist dies auf `true` eingestellt, und Ereignisse werden ausgelöst. Wenn es auf `false` eingestellt ist, werden keine Ereignisse ausgelöst, was für stille Navigation nützlich ist.

3. **Beobachter aufrufen (`setInvokeObservers`)**  
   Dieses Flag steuert, ob die Navigation [Beobachter](./navigation-lifecycle/observers) innerhalb der navigierten Komponenten auslösen soll. Beobachter behandeln typischerweise Ereignisse wie Eintritt oder Austritt von Routen. Wenn Sie dies auf `false` setzen, werden Beobachter nicht aufgerufen.

4. **Verlauf aktualisieren (`setUpdateHistory`)**  
   Wenn dieser Wert auf `false` gesetzt ist, wird verhindert, dass der Verlaufstand aktualisiert wird. Dies ist nützlich, wenn Sie die Ansicht ändern möchten, ohne die Rückwärts- oder Vorwärtsnavigation des Browsers zu beeinträchtigen. Es beeinflusst nur das Verlaufmanagement, nicht den Lebenszyklus der Komponenten oder die Routenbehandlung.

5. **Zustandsobjekt (`setState`)**  
   [Das Zustandsobjekt](./state-management#saving-and-restoring-state-in-browser-history) ermöglicht es Ihnen, zusätzliche Informationen beim Aktualisieren des Verlaufs des Browsers zu übergeben. Dieses Objekt wird im Verlauf des Browsers gespeichert und kann später für benutzerdefinierte Zwecke verwendet werden, z. B. um den Zustand der Anwendung während der Navigation zu speichern.

## Generieren von Locations für Ansichten {#generating-locations-for-views}

Der Router kann die Location für Ansichten basierend auf dem Routenmuster generieren, das in der Ansicht definiert ist. Sie können auch zusätzliche Parameter für dynamische und erforderliche Segmente in der URL bereitstellen. Dies kann nützlich sein, wenn Sie Links erstellen oder direkten Zugriff auf bestimmte Ansichten in der Anwendung teilen möchten.

So generieren Sie eine `Location`, basierend auf einer Ansichtsklasse und Routenparametern:

```java
Class<UserProfileView> userProfileView = UserProfileView.class;
ParametersBag params = ParametersBag.of("id=JohnDoe");

Optional<Location> location = Router.getCurrent().getLocation(userProfileView, params);
console().log(location.get());
```

Dies generiert ein `Location`-Objekt mit dem Pfad `/user/JohnDoe`, der vollständige URI als String.
