---
sidebar_position: 4
title: Route Navigation
_i18n_hash: 103905bf14bb1fe9f4813dfa26fd6828
---
In webforJ ist die Navigation zwischen Routen der zentrale Mechanismus zum Wechseln von Ansichten und Komponenten basierend auf Benutzeraktionen oder Änderungen in der URL. Die Navigation ermöglicht es den Benutzern, nahtlos zwischen verschiedenen Teilen der App zu wechseln, ohne die Seite neu zu laden. Diese clientseitige Navigation hält die App reaktionsschnell und flüssig, während der Zustand der App erhalten bleibt.

## Programmatische Navigation {#programmatic-navigation}

Sie können die Navigation von überall in Ihrer App aus auslösen, indem Sie die `Router`-Klasse verwenden. Dies ermöglicht dynamische Änderungen an den angezeigten Komponenten basierend auf Ereignissen wie Schaltflächenklicks oder anderen Benutzerinteraktionen.

Hier ist ein Beispiel, wie Sie zu einer bestimmten Route navigieren können:

```java
@Route(value = "dashboard")
public class DashboardView extends Composite<Div> {
  // Komponentenlogik hier
}
```

```java
// navigiere zur Ansicht
Router.getCurrent().navigate(DashboardView.class);
```

In diesem Beispiel bewirkt die programmgesteuerte Navigation zur `DashboardView`-Komponente, dass die `DashboardView`-Komponente gerendert wird und die URL des Browsers auf `/dashboard` aktualisiert wird.

Es ist auch möglich, zur Ansicht zu navigieren, indem Sie eine neue `Location` übergeben.

```java
Router.getCurrent().navigate(new Location("/dashboard"));
```

:::tip Klasse vs. Location: Methoden für die Routennavigation
Bei der Navigation zwischen Ansichten haben Entwickler zwei Optionen: Sie können entweder die Ansicht oder die Routenklasse übergeben, wodurch der Router automatisch die URL generiert und die Ansicht rendert, oder die Location direkt übergeben. Beide Methoden sind gültig, aber **die Verwendung der Klassenansicht ist der bevorzugte Ansatz**, da sie mehr Flexibilität für zukünftige Änderungen bietet. Wenn Sie beispielsweise später die Route aktualisieren möchten, müssen Sie nur die `@Route`-Annotation ändern, ohne den Code, der die Klassenansicht für die Navigation verwendet, ändern zu müssen.
:::

### Navigation mit Parametern {#navigation-with-parameters}

Wenn Sie Parameter zusammen mit der Route übergeben müssen, ermöglicht es webforJ, Parameter in der URL einzubetten. So können Sie zu einer Route mit Parametern navigieren:

```java
@Route("user/:id")
public class UserProfileView extends Composite<Div> implements DidEnterObserver {
  private final Div self = getBoundComponent();
  H1 title = new H1();

  public UserProfileView() {
    self.add(title);
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
// navigiere zur Ansicht und übergebe die Benutzer-ID
Router.getCurrent().navigate(
  UserProfileView.class,
  ParametersBag.of("id=JohnDoe")
);
```

Dies navigiert zu `/user/JohnDoe`, wobei `JohnDoe` möglicherweise eine Benutzer-ID darstellt. Die Komponente für diese Route kann dann den Parameter extrahieren und entsprechend verwenden.

## Erstellte View-Instanz {#created-view-instance}

Die `navigate`-Methode akzeptiert einen Java `Consumer`, der aufgerufen wird, sobald die Navigation abgeschlossen ist. Der `Consumer` erhält die Instanz der erstellten Ansichtskomponente, eingewickelt in ein java `Optional`, das es dem Entwickler ermöglicht, mit der Ansicht nach einer erfolgreichen Navigation zu interagieren.

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
Der Consumer erhält ein Java `Optional` für die Komponente, weil sie `null` sein könnte oder aus verschiedenen Gründen nicht erstellt wurde. Beispielsweise wird die Komponente möglicherweise nicht gerendert, wenn die Navigationsbeobachter die Navigation ablehnen und den Prozess stoppen.
:::

## Navigationsoptionen {#navigation-options}

Die `NavigationOptions`-Klasse ermöglicht es Entwicklern, die Navigation innerhalb der App fein abzustimmen. Durch das Setzen spezifischer Optionen können Sie das Verhalten der Navigation steuern, z. B. ob der Verlauf des Browsers aktualisiert werden soll, ob Lebenszyklusbeobachter aufgerufen werden sollen oder ob sogar Navigationsereignisse ausgelöst werden sollen.

```java
NavigationOptions options = new NavigationOptions();
options.setUpdateHistory(false);

Router.getCurrent().navigate(
  new Location("user/JohnDoe"), options);
```

### Navigationseinstellungen festlegen {#setting-navigation-options}

Die `NavigationOptions`-Klasse bietet mehrere Methoden zur Anpassung des Navigationsverhaltens. Dazu gehören die Steuerung, wie Routen behandelt werden, ob Beobachter benachrichtigt werden und wie der Verlauf des Browsers aktualisiert wird.

Hier sind die wichtigsten Konfigurationsoptionen, die innerhalb von `NavigationOptions` verfügbar sind:

1. **Navigationsart (`setNavigationType`)**  
   Diese Option definiert, ob die neue Route dem Verlauf des Browsers hinzugefügt oder die aktuelle Route ersetzt werden soll.

   - **`PUSH`**: Fügt die neue Route zum Verlauf hinzu, wodurch die aktuelle Position erhalten bleibt.
   - **`REPLACE`**: Ersetzt die aktuelle Route im Verlauf durch die neue Position und verhindert, dass die Zurück-Taste zur vorherigen Route navigiert.

2. **Ereignisse auslösen (`setFireEvents`)**  
   Bestimmt, ob Navigations-[Lebenszyklusereignisse](./navigation-lifecycle/navigation-events) während der Navigation ausgelöst werden sollen. Standardmäßig ist dies auf `true` gesetzt, und Ereignisse werden ausgelöst. Wenn es auf `false` gesetzt ist, werden keine Ereignisse ausgelöst, was nützlich für stille Navigation ist.

3. **Beobachter aufrufen (`setInvokeObservers`)**  
   Dieses Flag steuert, ob die Navigation [Beobachter](./navigation-lifecycle/observers) innerhalb der navigierten Komponenten auslösen soll. Beobachter behandeln in der Regel Ereignisse wie den Eintritt oder Austritt von Routen. Wenn dies auf `false` gesetzt ist, werden keine Beobachter aufgerufen.

4. **Verlauf aktualisieren (`setUpdateHistory`)**  
   Wenn dies auf `false` gesetzt ist, wird verhindert, dass der Verlauf aktualisiert wird. Dies ist nützlich, wenn Sie die Ansicht ändern möchten, ohne die Rück- oder Vorwärtsnavigation des Browsers zu beeinflussen. Es betrifft nur das Geschichtemanagement, nicht den Lebenszyklus der Komponente oder die Routenausführung.

5. **Zustandsobjekt (`setState`)**  
   [Das Zustandsobjekt](./state-management#saving-and-restoring-state-in-browser-history) ermöglicht es Ihnen, zusätzliche Informationen beim Aktualisieren des Verlaufs des Browsers zu übergeben. Dieses Objekt wird im Verlauf des Zustands des Browsers gespeichert und kann später für benutzerdefinierte Zwecke verwendet werden, z. B. zum Speichern des Zustands der App während der Navigation.

## Standorte für Ansichten generieren {#generating-locations-for-views}

Der Router kann den Standort für Ansichten basierend auf dem Routenmuster generieren, das in der Ansicht definiert ist. Sie können auch zusätzliche Parameter für dynamische und erforderliche Segmente in der URL bereitstellen. Dies kann nützlich sein, wenn Sie Links erstellen oder direkten Zugriff auf bestimmte Ansichten in der App teilen möchten.

So generieren Sie ein `Location`-Objekt basierend auf einer Klassenansicht und Routenparametern:

```java
Class<UserProfileView> userProfileView = UserProfileView.class;
ParametersBag params = ParametersBag.of("id=JohnDoe");

Optional<Location> location = Router.getCurrent().getLocation(userProfileView, params);
console().log(location.get());
```

Dies generiert ein `Location`-Objekt mit dem Pfad `/user/JohnDoe`, die vollständige URI als Zeichenfolge.
