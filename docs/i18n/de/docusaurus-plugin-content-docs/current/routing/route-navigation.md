---
sidebar_position: 4
title: Route Navigation
_i18n_hash: 91739f35b8d47f6e90e276623864aac4
---
In webforJ ist die Navigation zwischen Routen der zentrale Mechanismus zum Wechseln von Ansichten und Komponenten basierend auf Benutzerinteraktionen oder Änderungen der URL. Die Navigation ermöglicht es den Benutzern, nahtlos zwischen verschiedenen Teilen der App zu wechseln, ohne die Seite zu aktualisieren. Diese clientseitige Navigation hält die App reaktionsschnell und flüssig, während der Status der App erhalten bleibt.

## Programmatische Navigation {#programmatic-navigation}

Sie können die Navigation von überall in Ihrer App aus mit der `Router`-Klasse auslösen. Dies ermöglicht dynamische Änderungen der angezeigten Komponenten basierend auf Ereignissen wie Button-Klicks oder anderen Benutzerinteraktionen.

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

In diesem Beispiel sorgt die programmgesteuerte Navigation zur `DashboardView`-Komponente dafür, dass die `DashboardView`-Komponente gerendert wird und die URL des Browsers auf `/dashboard` aktualisiert wird.

Es ist auch möglich, zur Ansicht zu navigieren, indem Sie eine neue `Location` übergeben:

```java
Router.getCurrent().navigate(new Location("/dashboard"));
```

:::tip Klasse vs. Location: Methoden zur Routensteuerung
Beim Navigieren zwischen Ansichten haben Entwickler zwei Optionen: Entweder sie geben die Ansicht oder die Routenklasse an, sodass der Router automatisch die URL generiert und die Ansicht rendert, oder sie geben die Location direkt an. Beide Methoden sind gültig, aber **die Verwendung der View-Klasse ist der bevorzugte Ansatz**, da dies bessere Flexibilität für zukünftige Änderungen bietet. Wenn Sie beispielsweise später die Route aktualisieren möchten, müssen Sie nur die `@Route`-Annotation ändern, ohne den Code, der die View-Klasse für die Navigation verwendet, anpassen zu müssen.
:::

### Navigation mit Parametern {#navigation-with-parameters}

Wenn Sie Parameter zusammen mit der Route übergeben müssen, erlaubt webforJ, Parameter in der URL einzubetten. Hier ist, wie Sie zu einer Route mit Parametern navigieren können:

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

Dies navigiert zu `/user/JohnDoe`, wobei `JohnDoe` eine Benutzer-ID darstellen könnte. Die Komponente für diese Route kann dann den Parameter extrahieren und entsprechend verwenden.

## Erstellt Instanz der Ansicht {#created-view-instance}

Die Methode `navigate` akzeptiert einen Java `Consumer`, der aufgerufen wird, sobald die Navigation abgeschlossen ist. Der `Consumer` erhält die Instanz der erstellten View-Komponente, eingekapselt in einem Java `Optional`, was dem Entwickler ermöglicht, nach einer erfolgreichen Navigation mit der Ansicht zu interagieren.

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
Der Consumer erhält ein Java `Optional` für die Komponente, da diese `null` sein könnte oder aus verschiedenen Gründen nicht erstellt wurde. Beispielsweise kann die Komponente nicht gerendert werden, wenn die Navigationsbeobachter die Navigation ablehnen und den Prozess stoppen.
:::

## Navigationsoptionen {#navigation-options}

Die Klasse `NavigationOptions` ermöglicht Entwicklern, feine Einstellungen vorzunehmen, wie Navigation innerhalb der App behandelt wird. Durch das Setzen bestimmter Optionen können Sie das Verhalten der Navigation steuern, beispielsweise ob der Verlauf des Browsers aktualisiert werden soll, Lebenszyklusbeobachter aufgerufen werden oder sogar Navigationsereignisse ausgelöst werden sollen.

```java
NavigationOptions options = new NavigationOptions();
options.setUpdateHistory(false);

Router.getCurrent().navigate(
  new Location("user/JohnDoe"), options);
```

### Einstellen von Navigationsoptionen {#setting-navigation-options}

Die Klasse `NavigationOptions` bietet mehrere Methoden zur Anpassung des Navigationsverhaltens. Dazu gehört die Steuerung, wie Routen behandelt werden, ob Beobachter benachrichtigt werden und wie der Verlauf des Browsers aktualisiert wird.

Hier sind die wichtigsten Konfigurationsoptionen, die innerhalb von `NavigationOptions` verfügbar sind:

1. **Navigationsart (`setNavigationType`)**  
   Diese Option definiert, ob die neue Route dem Verlauf des Browsers hinzugefügt oder die aktuelle Route ersetzt werden soll.

   - **`PUSH`**: Fügt die neue Route zum Verlaufsstapel hinzu und bewahrt den aktuellen Standort.
   - **`REPLACE`**: Ersetzt die aktuelle Route im Verlaufsstapel durch den neuen Standort und verhindert, dass die Zurück-Taste zur vorherigen Route navigiert.

2. **Ereignisse auslösen (`setFireEvents`)**  
   Bestimmt, ob während der Navigation [Lebenszyklusereignisse](./navigation-lifecycle/navigation-events) ausgelöst werden sollen. Standardmäßig ist dies auf `true` gesetzt, und Ereignisse werden ausgelöst. Wenn auf `false` gesetzt, werden keine Ereignisse ausgelöst, was für stille Navigation nützlich ist.

3. **Beobachter aufrufen (`setInvokeObservers`)**  
   Dieses Flag steuert, ob die Navigation [Beobachter](./navigation-lifecycle/observers) innerhalb der navigierten Komponenten auslösen soll. Beobachter behandeln typischerweise Ereignisse wie den Eintritt oder Austritt aus Routen. Das Setzen auf `false` verhindert, dass Beobachter aufgerufen werden.

4. **Verlauf aktualisieren (`setUpdateHistory`)**  
   Wenn auf `false` gesetzt, verhindert diese Option, dass die Verlaufinformation aktualisiert wird. Dies ist nützlich, wenn Sie die Ansicht ändern möchten, ohne die Rückwärts- oder Vorwärtsnavigation des Browsers zu beeinträchtigen. Es wirkt sich nur auf die Verlaufverwaltung aus, nicht auf den Lebenszyklus der Komponente oder die Routenbearbeitung.

5. **Statusobjekt (`setState`)**  
   [Das Statusobjekt](./state-management#saving-and-restoring-state-in-browser-history) ermöglicht es Ihnen, zusätzliche Informationen beim Aktualisieren des Verlaufs des Browsers zu übergeben. Dieses Objekt wird im Verlaufsstatus des Browsers gespeichert und kann später für benutzerdefinierte Zwecke verwendet werden, z. B. um den Zustand der App während der Navigation zu speichern.

## Generierung von Standorten für Ansichten {#generating-locations-for-views}

Der Router kann den Standort für Ansichten basierend auf dem Routenmuster, das in der Ansicht definiert ist, generieren. Sie können auch zusätzliche Parameter für dynamische und erforderliche Segmente in der URL bereitstellen. Dies kann nützlich sein, wenn Sie Links erstellen oder direkten Zugang zu bestimmten Ansichten in der App teilen möchten.

Hier ist, wie Sie eine `Location` basierend auf einer View-Klasse und Routenparametern generieren können:

```java
Class<UserProfileView> userProfileView = UserProfileView.class;
ParametersBag params = ParametersBag.of("id=JohnDoe");

Optional<Location> location = Router.getCurrent().getLocation(userProfileView, params);
console().log(location.get());
```

Dies erzeugt ein `Location`-Objekt mit dem Pfad `/user/JohnDoe`, die vollständige URI als String.
