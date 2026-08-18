---
sidebar_position: 4
title: Route Navigation
description: >-
  Trigger client-side navigation programmatically with Router.navigate, pass
  parameters, and switch views without reloads.
sidebar_class_name: updated-content
_i18n_hash: 0284f2481f307d68da728d81f4b3a6a2
---
In webforJ ermöglicht die Navigation zwischen Routen das zentrale Mechanismus zum Wechseln von Ansichten und Komponenten basierend auf Benutzeraktionen oder URL-Änderungen. Die Navigation erlaubt es den Benutzern, nahtlos zwischen verschiedenen Teilen der App zu wechseln, ohne die Seite neu zu laden. Diese clientseitige Navigation hält die App reaktionsschnell und flüssig, während sie den Status der App bewahrt.

## Programmatische Navigation {#programmatic-navigation}

Sie können die Navigation von überall in Ihrer App aus mit der `Router`-Klasse auslösen. Dies ermöglicht dynamische Änderungen der angezeigten Komponenten basierend auf Ereignissen wie Button-Klicks oder anderen Benutzerinteraktionen.

Hier ein Beispiel, wie man zu einer bestimmten Route navigiert:

```java
@Route(value = "dashboard")
public class DashboardView extends Composite<Div> {
  // Komponentenlogik hier
}
```

```java
// zur Ansicht navigieren
Router.getCurrent().navigate(DashboardView.class);
```

In diesem Beispiel führt die programmatische Navigation zur `DashboardView`-Komponente dazu, dass die `DashboardView`-Komponente gerendert wird und die URL des Browsers auf `/dashboard` aktualisiert wird.

Es ist auch möglich, zur Ansicht zu navigieren, indem eine neue `Location` übergeben wird.

```java
Router.getCurrent().navigate(new Location("/dashboard"));
```

:::tip Klasse vs. Location: Methoden für die Ansichts-Routing
Beim Navigieren zwischen Ansichten haben Entwickler zwei Optionen: Sie können entweder die Ansicht oder die Routenklasse übergeben, sodass der Router die URL automatisch generiert und die Ansicht rendert, oder die Location direkt übergeben. Beide Methoden sind gültig, aber **die Verwendung der Klassensicht ist der bevorzugte Ansatz**, da er bessere Flexibilität für zukünftige Änderungen bietet. Wenn Sie sich zum Beispiel entscheiden, die Route später zu aktualisieren, müssen Sie nur die `@Route`-Annotation ändern, ohne dass Sie den Code ändern müssen, der die Klassensicht für die Navigation verwendet.
:::

### Navigation mit Parametern {#navigation-with-parameters}

Wenn Sie Parameter zusammen mit der Route übergeben müssen, erlaubt es webforJ, Parameter in der URL einzubetten. So navigieren Sie zu einer Route mit Parametern:

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
// zur Ansicht navigieren und die Benutzer-ID übergeben
Router.getCurrent().navigate(
  UserProfileView.class,
  ParametersBag.of("id=JohnDoe")
);
```

Dies navigiert zu `/user/JohnDoe`, wobei `JohnDoe` möglicherweise eine Benutzer-ID darstellt. Die Komponente für diese Route kann dann den Parameter extrahieren und entsprechend verwenden.

## Erstellt Instanz der Ansicht {#created-view-instance}

Die Methode `navigate` akzeptiert einen Java `Consumer`, der aufgerufen wird, sobald die Navigation abgeschlossen ist. Der `Consumer` erhält die Instanz der erstellten Ansichtskomponente, eingekapselt in einem Java `Optional`, sodass der Entwickler nach einer erfolgreichen Navigation mit der Ansicht interagieren kann.

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
Der Consumer erhält ein Java `Optional` für die Komponente, da sie möglicherweise `null` ist oder aus verschiedenen Gründen nicht erstellt wurde. Zum Beispiel wird die Komponente möglicherweise nicht gerendert, wenn die Navigationsbeobachter die Navigation ablehnen und den Prozess stoppen.
:::

## Navigationsoptionen {#navigation-options}

Die Klasse `NavigationOptions` ermöglicht es Entwicklern, die Navigation innerhalb der App anzupassen. Indem Sie spezifische Optionen festlegen, können Sie das Verhalten der Navigation steuern, z.B. ob der Verlauf des Browsers aktualisiert werden soll, die Lebenszyklusbeobachter aufgerufen werden sollen oder sogar Navigationsereignisse ausgelöst werden sollen.

```java
NavigationOptions options = new NavigationOptions();
options.setUpdateHistory(false);

Router.getCurrent().navigate(
  new Location("user/JohnDoe"), options);
```

### Festlegen von Navigationsoptionen {#setting-navigation-options}

Die Klasse `NavigationOptions` bietet mehrere Methoden zur Anpassung des Navigationsverhaltens. Dazu gehört die Steuerung, wie Routen behandelt werden, ob Beobachter benachrichtigt werden und wie der Verlauf des Browsers aktualisiert wird.

Hier sind die wichtigsten Konfigurationsoptionen, die innerhalb von `NavigationOptions` verfügbar sind:

1. **Navigationsart (`setNavigationType`)**

   Diese Option definiert, ob die neue Route zum Verlauf des Browsers hinzugefügt oder die aktuelle Route ersetzt werden soll.

   - **`PUSH`**: Fügt die neue Route zum Verlaufsstapel hinzu und bewahrt den aktuellen Standort.
   - **`REPLACE`**: Ersetzt die aktuelle Route im Verlaufsstapel durch die neue Position, wodurch verhindert wird, dass die Zurück-Taste zur vorherigen Route navigiert.

2. **Ereignisse auslösen (`setFireEvents`)**

   Bestimmt, ob Navigations-[Lebenszyklusereignisse](./navigation-lifecycle/navigation-events) während der Navigation ausgelöst werden sollen. Standardmäßig ist dies auf `true` gesetzt, und Ereignisse werden ausgelöst. Wenn es auf `false` gesetzt ist, werden keine Ereignisse ausgelöst, was für stille Navigation nützlich ist.

3. **Beobachter aufrufen (`setInvokeObservers`)**

   Dieses Flag steuert, ob die Navigation die [Beobachter](./navigation-lifecycle/observers) innerhalb der navigierten Komponenten auslösen soll. Beobachter behandeln typischerweise Ereignisse wie das Betreten oder Verlassen einer Route. Wenn Sie dies auf `false` setzen, werden Beobachter nicht aufgerufen.

4. **Verlauf aktualisieren (`setUpdateHistory`)**

   Wenn auf `false` gesetzt, verhindert diese Option, dass die Verlaufslocation aktualisiert wird. Dies ist nützlich, wenn Sie die Ansicht ändern möchten, ohne die Rückwärts- oder Vorwärtsnavigation des Browsers zu beeinflussen. Es betrifft nur das Verlaufsmanagement, nicht den Lebenszyklus der Komponenten oder die Routenbehandlung.

5. **Zustandsobjekt (`setState`)**

   [Das Zustandsobjekt](./state-management#saving-and-restoring-state-in-browser-history) ermöglicht es Ihnen, zusätzliche Informationen beim Aktualisieren des Verlaufs des Browsers zu übergeben. Dieses Objekt wird im Verlauf des Zustands des Browsers gespeichert und kann später für benutzerdefinierte Zwecke verwendet werden, z.B. zum Speichern des Zustands der App während der Navigation.

6. **Instanzen neu erstellen (`setRecreateFrom`)** <DocChip chip='since' label='26.02' />

   Wenn eine Routenkomponente angegeben ist, ermöglicht diese Option der Navigation, alle gerenderten Instanzen dieser Komponente und der darunter liegenden Komponenten zu zerstören, bevor sie erneut gerendert wird. Dadurch kann dieser Teil der Hierarchie frische Instanzen verwenden, ohne die gerenderten Instanzen vor der angegebenen Komponente zu beeinträchtigen.

   ```java
   NavigationOptions options = new NavigationOptions()
       .setRecreateFrom(DashboardView.class);

   Router.getCurrent().navigate(
       new Location("/dashboard"), options);
   ```

   Die Standardroute für `setRecreateFrom()` ist `null`, sodass der Router gerenderte Routenkomponenten, die im Pfad verbleiben, wiederverwenden kann. Wenn die angegebene Komponente keine gerenderte Instanz hat, verhält sich die Navigation wie gewohnt. Darüber hinaus kann ein Lebenszyklusbeobachter die Zerstörung ablehnen, was die Navigation fehlschlägt.

## Generieren von Locations für Ansichten {#generating-locations-for-views}

Der Router kann die Location für Ansichten basierend auf dem Routenmuster, das in der Ansicht definiert ist, generieren. Sie können auch zusätzliche Parameter für dynamische und erforderliche Segmente in der URL bereitstellen. Dies kann nützlich sein, wenn Sie Links erstellen oder direkte Zugriffspunkte zu bestimmten Ansichten in der App teilen möchten.

So generieren Sie eine `Location` basierend auf einer Klassenansicht und Routenparametern:

```java
Class<UserProfileView> userProfileView = UserProfileView.class;
ParametersBag params = ParametersBag.of("id=JohnDoe");

Optional<Location> location = Router.getCurrent().getLocation(userProfileView, params);
console().log(location.get());
```

Dies generiert ein `Location`-Objekt mit dem Pfad `/user/JohnDoe`, der vollständigen URI als String.
