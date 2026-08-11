---
sidebar_position: 4
title: Route Navigation
description: >-
  Trigger client-side navigation programmatically with Router.navigate, pass
  parameters, and switch views without reloads.
_i18n_hash: c32517b16f185d4b54682b95c82d38d3
---
In webforJ ist das Navigieren zwischen Routen der Kernmechanismus zum Wechseln von Ansichten und Komponenten basierend auf Benutzeraktionen oder URL-Änderungen. Die Navigation ermöglicht es den Benutzern, nahtlos zwischen verschiedenen Teilen der App zu wechseln, ohne die Seite zu aktualisieren. Diese clientseitige Navigation hält die App reaktionsschnell und flüssig, während der Zustand der App erhalten bleibt.

## Programmgesteuerte Navigation {#programmatic-navigation}

Sie können die Navigation von überall in Ihrer App aus mit der `Router`-Klasse auslösen. Dies ermöglicht dynamische Änderungen der angezeigten Komponenten basierend auf Ereignissen wie Schaltflächenklicks oder anderen Benutzerinteraktionen.

Hier ist ein Beispiel, wie Sie zu einer bestimmten Route navigieren können:

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

In diesem Beispiel wird durch die programmgesteuerte Navigation zur `DashboardView`-Komponente die `DashboardView`-Komponente dargestellt und die URL des Browsers auf `/dashboard` aktualisiert.

Es ist auch möglich, zur Ansicht zu navigieren, indem eine neue `Location` übergeben wird.

```java
Router.getCurrent().navigate(new Location("/dashboard"));
```

:::tip Klasse vs. Location: Methoden für die Ansichts-Routing
Bei der Navigation zwischen Ansichten haben Entwickler zwei Optionen: Sie können entweder die Ansichts- oder Routenklasse übergeben, wodurch der Router automatisch die URL generiert und die Ansicht darstellt, oder die Location direkt übergeben. Beide Methoden sind gültig, aber **die Verwendung der Ansichts-klasse ist der bevorzugte Ansatz**, da sie mehr Flexibilität für zukünftige Änderungen bietet. Wenn Sie beispielsweise später die Route aktualisieren möchten, müssen Sie nur die `@Route`-Annotation ändern, ohne den Code zu ändern, der die Ansichts-kategorie für die Navigation verwendet.
:::

### Navigation mit Parametern {#navigation-with-parameters}

Wenn Sie Parameter zusammen mit der Route übergeben müssen, ermöglicht es webforJ Ihnen, Parameter in der URL einzubetten. So navigieren Sie zu einer Route mit Parametern:

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

## Erstellte Ansichtsinstanz {#created-view-instance}

Die `navigate`-Methode akzeptiert einen Java `Consumer`, der aufgerufen wird, sobald die Navigation abgeschlossen ist. Der `Consumer` erhält die Instanz der erstellten Ansichtskomponente, eingehüllt in ein Java `Optional`, wodurch der Entwickler nach einer erfolgreichen Navigation mit der Ansicht interagieren kann.

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
Der Consumer erhält ein Java `Optional` für die Komponente, da sie `null` sein könnte oder aus verschiedenen Gründen nicht erstellt wurde. Beispielsweise wird die Komponente möglicherweise nicht gerendert, wenn die Navigationsbeobachter die Navigation ablehnen und den Prozess stoppen.
:::

## Navigationsoptionen {#navigation-options}

Die `NavigationOptions`-Klasse ermöglicht es Entwicklern, die Navigation innerhalb der App zu optimieren. Durch das Festlegen bestimmter Optionen können Sie das Verhalten der Navigation steuern, z. B. ob der Verlauf des Browsers aktualisiert wird, Lebenszyklusbeobachter aufgerufen werden oder sogar Navigationsereignisse ausgelöst werden.

```java
NavigationOptions options = new NavigationOptions();
options.setUpdateHistory(false);

Router.getCurrent().navigate(
  new Location("user/JohnDoe"), options);
```

### Festlegen von Navigationsoptionen {#setting-navigation-options}

Die `NavigationOptions`-Klasse bietet verschiedene Methoden zur Anpassung des Navigationsverhaltens. Dazu gehört die Steuerung, wie Routen behandelt werden, ob Beobachter benachrichtigt werden und wie der Verlauf des Browsers aktualisiert wird.

Hier sind die Hauptkonfigurationsoptionen innerhalb von `NavigationOptions`:

1. **Navigationsart (`setNavigationType`)**
   Diese Option definiert, ob die neue Route zum Verlauf des Browsers hinzugefügt oder die aktuelle Route ersetzt werden soll.

   - **`PUSH`**: Fügt die neue Route zum Verlaufsspeicher hinzu und bewahrt den aktuellen Standort.
   - **`REPLACE`**: Ersetzt die aktuelle Route im Verlaufsspeicher durch den neuen Standort, wodurch das Zurückspringen zur vorherigen Route verhindert wird.

2. **Ereignisse auslösen (`setFireEvents`)**
   Bestimmt, ob Navigation [Lebenszyklusereignisse](./navigation-lifecycle/navigation-events) während der Navigation ausgelöst werden sollen. Standardmäßig ist dies auf `true` gesetzt, und Ereignisse werden ausgelöst. Wenn dies auf `false` gesetzt ist, werden keine Ereignisse ausgelöst, was für stille Navigation nützlich ist.

3. **Beobachter aufrufen (`setInvokeObservers`)**
   Dieses Flag steuert, ob die Navigation [Beobachter](./navigation-lifecycle/observers) innerhalb der navigierten Komponenten auslösen soll. Beobachter behandeln typischerweise Ereignisse wie das Betreten oder Verlassen der Route. Wenn dieses auf `false` gesetzt ist, werden Beobachter nicht aufgerufen.

4. **Verlauf aktualisieren (`setUpdateHistory`)**
   Wenn auf `false` gesetzt, verhindert diese Option, dass der Standort im Verlauf aktualisiert wird. Dies ist nützlich, wenn Sie die Ansicht ändern möchten, ohne die Vor- oder Zurücknavigation des Browsers zu beeinträchtigen. Es betrifft nur das Verlauf-Management, nicht den Lebenszyklus der Komponente oder die Routenbearbeitung.

5. **Zustandsobjekt (`setState`)**
   [Das Zustandsobjekt](./state-management#saving-and-restoring-state-in-browser-history) ermöglicht es Ihnen, zusätzliche Informationen beim Aktualisieren des Verlaufs des Browsers zu übergeben. Dieses Objekt wird im Zustand des Verlaufs des Browsers gespeichert und kann später für benutzerdefinierte Zwecke verwendet werden, z. B. zum Speichern des Zustands der App während der Navigation.

## Generierung von Standorten für Ansichten {#generating-locations-for-views}

Der Router kann den Standort für Ansichten basierend auf dem Routenmuster generieren, das in der Ansicht definiert ist. Sie können auch zusätzliche Parameter für dynamische und erforderliche Segmente in der URL bereitstellen. Dies kann nützlich sein, wenn Links erstellt oder direkte Zugriffspunkte zu bestimmten Ansichten in der App geteilt werden.

Hier ist, wie Sie eine `Location` basierend auf einer Ansichts-klasse und Routenparametern generieren können:

```java
Class<UserProfileView> userProfileView = UserProfileView.class;
ParametersBag params = ParametersBag.of("id=JohnDoe");

Optional<Location> location = Router.getCurrent().getLocation(userProfileView, params);
console().log(location.get());
```

Dies generiert ein `Location`-Objekt mit dem Pfad `/user/JohnDoe`, die vollständige URI als Zeichenfolge.
