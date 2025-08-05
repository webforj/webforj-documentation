---
sidebar_position: 2
title: Bindings
_i18n_hash: fa6155c6e1eb2724d684d042f561c8a3
---
Ein Binding in webforJ verknüpft eine bestimmte Eigenschaft eines Java Beans mit einer UI-Komponente. Diese Verknüpfung ermöglicht automatische Aktualisierungen zwischen der Benutzeroberfläche und dem Backend-Modell. Jedes Binding kann Daten-Synchronisierung, Validierung, Transformation und Ereignisverwaltung durchführen.

Bindings können nur über den `BindingContext` initiiert werden. Er verwaltet eine Sammlung von Binding-Instanzen, die jede eine UI-Komponente mit einer Eigenschaft eines Beans verknüpfen. Er erleichtert Gruppenoperationen auf Bindings, wie Validierung und Synchronisierung zwischen den UI-Komponenten und den Eigenschaften des Beans. Er fungiert als Aggregator, der kollektive Aktionen auf mehreren Bindings ermöglicht und so die Verwaltung des Datenflusses innerhalb von Anwendungen strafft.

:::tip Automatisches Binding
Dieser Abschnitt führt in die Grundlagen der manuellen Konfiguration von Bindings ein. Zusätzlich können Sie automatisch Bindings basierend auf den UI-Komponenten in Ihrem Formular erstellen. Nachdem Sie die Grundlagen verstanden haben, erfahren Sie mehr im Abschnitt [Automatisches Binding](./automatic-binding).
:::

## Bindings konfigurieren {#configure-bindings}

Beginnen Sie damit, eine neue Instanz von `BindingContext` zu erstellen, die alle Bindings für ein bestimmtes Modell verwaltet. Dieser Kontext stellt sicher, dass alle Bindings kollektiv validiert und aktualisiert werden können.

```java
BindingContext<Hero> context = new BindingContext<>(Hero.class);
```

:::info
Jedes Formular sollte nur eine `BindingContext`-Instanz haben, und Sie sollten diese Instanz für alle Komponenten im Formular verwenden.
:::

### Die gebundene Eigenschaft {#the-bound-property}

Eine Binding-Eigenschaft ist ein bestimmtes Feld oder Attribut eines Java Beans, das mit einer UI-Komponente in Ihrer App verknüpft werden kann. Diese Verknüpfung ermöglicht es, dass Änderungen in der Benutzeroberfläche die entsprechende Eigenschaft des Datenmodells direkt beeinflussen und umgekehrt, was ein reaktives Benutzererlebnis erleichtert.

Beim Einrichten eines Bindings sollten Sie den Eigenschaftsnamen als Zeichenfolge angeben. Dieser Name muss mit dem Feldnamen in der Java Bean-Klasse übereinstimmen. Hier ist ein einfaches Beispiel:

```java
BindingContext<Hero> context = new BindingContext<>(Hero.class, true);
context
    .bind(textField, "power")
    .add();
```

```java
public class Hero  {
  private String name;
  private String power;

  // setter und getter
}
```

Die `bind`-Methoden geben einen `BindingBuilder` zurück, der das `Binding`-Objekt erstellt, und Sie können damit mehrere Einstellungen für das Binding konfigurieren, sowie die `add`-Methode, die das Binding tatsächlich zum Kontext hinzufügt.

### Die gebundene Komponente {#the-bound-component}

Die andere Seite des Bindings ist die gebundene Komponente, die sich auf die UI-Komponente bezieht, die mit der Eigenschaft des Java Beans interagiert. Die gebundene Komponente kann jede UI-Komponente sein, die Benutzerinteraktion und Anzeige unterstützt, wie Textfelder, Kombinationsfelder, Kontrollkästchen oder jede benutzerdefinierte Komponente, die das `ValueAware`-Interface implementiert.

Die gebundene Komponente dient als der Punkt der Benutzerinteraktion mit dem zugrunde liegenden Datenmodell. Sie zeigt Daten dem Benutzer an und erfasst auch Benutzereingaben, die dann an das Modell zurückgegeben werden.

```java
TextField nameTextField = new TextField("Name");
BindingContext<Hero> context = new BindingContext<>(Hero.class);
context.bind(nameTextField, "name").add();
```

## Daten lesen und schreiben {#reading-and-writing-data}

### Daten lesen {#reading-data}

Daten lesen bedeutet, UI-Komponenten mit Werten aus dem Datenmodell zu befüllen. Dies geschieht typischerweise, wenn ein Formular zunächst angezeigt wird oder wenn Sie die Daten aufgrund von Änderungen im zugrunde liegenden Modell erneut laden müssen. Die `read`-Methode des `BindingContext` macht diesen Prozess einfach.

```java
// Angenommen, das Hero-Objekt wurde instanziiert und initialisiert
Hero hero = new Hero("Clark Kent", "Flying");

// BindingContext ist bereits mit Bindings konfiguriert
context.read(hero);
```

In diesem Beispiel nimmt die `read`-Methode eine Instanz von `Hero` und aktualisiert alle gebundenen UI-Komponenten, um die Eigenschaften des Helden widerzuspiegeln. Wenn sich der Name oder die Kraft des Helden ändert, zeigen die entsprechenden UI-Komponenten (wie ein `TextField` für den Namen und ein `ComboBox` für die Kräfte) diese neuen Werte an.

### Daten schreiben {#writing-data}

Daten schreiben bedeutet, Werte aus den UI-Komponenten zu sammeln und das Datenmodell zu aktualisieren. Dies geschieht typischerweise, wenn ein Benutzer ein Formular einreicht. Die `write`-Methode übernimmt die Validierung und das Aktualisieren des Modells in einem Schritt.

```java
// Dies könnte durch ein Ereignis zur Formularübermittlung ausgelöst werden
submit.onClick(event -> {
  ValidationResult results = context.write(hero);
  if (results.isValid()) {
    // Daten sind gültig, und das Held-Objekt wurde aktualisiert
    // repository.save(hero); 
  } else {
    // Validierungsfehler behandeln
    // results.getMessages();
  }
});
```

Im obigen Code wird bei einem Klick auf die Schaltfläche "Absenden" die `write`-Methode aufgerufen. Sie führt alle konfigurierten Validierungen durch und, falls die Daten alle Prüfungen bestehen, aktualisiert sie das `Hero`-Objekt mit neuen Werten aus den gebundenen Komponenten. Wenn die Daten gültig sind, können Sie sie in einer Datenbank speichern oder weiter verarbeiten. Wenn es Validierungsfehler gibt, sollten Sie dies angemessen behandeln, typischerweise durch das Anzeigen von Fehlermeldungen an den Benutzer.

:::tip Berichterstattung über Validierungsfehler
Alle Kernkomponenten von webforJ haben Standardkonfigurationen, um Validierungsfehler automatisch zu melden, entweder inline oder über ein Popover. Sie können dieses Verhalten mit [Reportern](./validation/reporters.md) anpassen.
:::

<!-- vale off -->
## ReadOnly-Daten {#readonly-data}
<!-- vale on -->

In bestimmten Szenarien möchten Sie vielleicht, dass Ihre App Daten anzeigt, ohne dass der Endbenutzer diese direkt über die Benutzeroberfläche ändern kann. Hier werden schreibgeschützte Datenbindings entscheidend. webforJ unterstützt die Konfiguration von Bindings, um schreibgeschützt zu sein, sodass Sie Daten anzeigen, aber nicht über gebundene UI-Komponenten bearbeiten können.

### Konfiguration von schreibgeschützten Bindings {#configuring-readonly-bindings}

Um ein schreibgeschütztes Binding einzurichten, können Sie das Binding so konfigurieren, dass die Eingabe von UI-Komponenten deaktiviert oder ignoriert wird. Dies stellt sicher, dass die Daten aus der Sicht der Benutzeroberfläche unverändert bleiben, während sie bei Bedarf programmatisch aktualisiert werden können.

```java
// Konfiguration eines Textfeldes als schreibgeschützt im Binding-Kontext
BindingContext<Hero> context = new BindingContext<>(Hero.class);
context.bind(nameTextField, "name")
    .readOnly()
    .add();
```

In dieser Konfiguration stellt `readOnly` sicher, dass das `nameTextField` keine Benutzereingaben akzeptiert und somit das Textfeld die Daten anzeigt, ohne Änderungen zuzulassen.

:::info
Das Binding kann die Komponente nur dann als schreibgeschützt kennzeichnen, wenn die UI-Komponente das `ReadOnlyAware`-Interface implementiert.
:::

:::tip Leser- vs. Binding-ReadOnly
Es ist wichtig, zwischen Bindings zu unterscheiden, die Sie als schreibgeschützt konfigurieren, und UI-Komponenten, die Sie so einstellen, dass sie als schreibgeschützt angezeigt werden. Wenn Sie ein Binding als schreibgeschützt kennzeichnen, wirkt sich dies darauf aus, wie das Binding Daten während des Schreibvorgangs verwaltet, nicht nur auf das UI-Verhalten.

Wenn Sie ein Binding als schreibgeschützt kennzeichnen, überspringt das System Datenaktualisierungen. Änderungen an der UI-Komponente werden nicht an das Datenmodell zurückübertragen. Dies stellt sicher, dass selbst wenn die UI-Komponente irgendwie Benutzereingaben erhält, das zugrunde liegende Datenmodell nicht aktualisiert wird. Die Aufrechterhaltung dieser Trennung ist entscheidend, um die Datenintegrität in Szenarien zu wahren, in denen Benutzeraktionen die Daten nicht verändern sollten.

Im Gegensatz dazu stoppt das Setzen einer UI-Komponente als schreibgeschützt, ohne das Binding selbst als schreibgeschützt zu konfigurieren, lediglich den Benutzer daran, Änderungen an der UI-Komponente vorzunehmen, verhindert jedoch nicht, dass das Binding das Datenmodell aktualisiert, wenn Änderungen programmatisch oder durch andere Mittel auftreten.
:::

## Binding-Getter und -Setter {#binding-getters-and-setters}

Setter und Getter sind Methoden in Java, die die Werte von Eigenschaften setzen und abrufen. Im Kontext des Daten-Bindings werden sie verwendet, um festzulegen, wie Eigenschaften innerhalb des Binding-Frameworks aktualisiert und abgerufen werden.

### Anpassen von Settern und Gettern {#customizing-setters-and-getters}

Obwohl webforJ die standardmäßigen JavaBean-Namenskonventionen automatisch verwenden kann (zum Beispiel `getName()`, `setName()` für eine Eigenschaft `name`), müssen Sie möglicherweise benutzerdefiniertes Verhalten definieren. Dies ist erforderlich, wenn die Eigenschaft nicht den konventionellen Benennungen folgt oder wenn die Datenverarbeitung zusätzliche Logik erfordert.

### Verwendung benutzerdefinierter Getter {#using-custom-getters}

Benutzerdefinierte Getter werden verwendet, wenn der Prozess des Abrufens des Wertes mehr als nur die Rückgabe einer Eigenschaft umfasst. Zum Beispiel möchten Sie möglicherweise den String formatieren, einen Wert berechnen oder bestimmte Aktionen protokollieren, wenn auf eine Eigenschaft zugegriffen wird.

```java
BindingContext<Hero> context = new BindingContext<>(Hero.class);
context
    .bind(textField, "power")
    .useGetter(hero -> {
        String name = hero.getName();
        return name.toUpperCase(); // Benutzerdefinierte Logik: Namen in Großbuchstaben umwandeln
    });
```

### Verwendung benutzerdefinierter Setter {#using-custom-setters}

Benutzerdefinierte Setter kommen zum Einsatz, wenn das Setzen einer Eigenschaft zusätzliche Vorgänge wie Validierung, Transformation oder Nebeneffekte wie Protokollierung oder Benachrichtigung anderer Teile Ihrer App umfasst.

```java
BindingContext<Hero> context = new BindingContext<>(Hero.class);
context
    .bind(textField, "power")
    .useSetter((hero, name) -> {
        System.out.println("Aktualisiere Namen von " + hero.getName() + " zu " + name);
        hero.setName(name); // Zusätzliche Operation: Protokollierung
    });
```
