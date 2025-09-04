---
sidebar_position: 2
title: Bindings
_i18n_hash: 0afea0971d509f25324b46172b5e020e
---
Ein Binding in webforJ verknüpft eine spezifische Eigenschaft eines Java Beans mit einer UI-Komponente. Diese Verknüpfung ermöglicht automatische Aktualisierungen zwischen der Benutzeroberfläche und dem Backend-Modell. Jedes Binding kann Daten-Synchronisation, Validierung, Transformation und Ereignisverwaltung durchführen.

Sie können Bindings nur über den `BindingContext` initiieren. Er verwaltet eine Sammlung von Binding-Instanzen, die jeweils eine UI-Komponente mit einer Eigenschaft eines Beans verknüpfen. Er erleichtert Gruppenoperationen auf Bindings, wie Validierung und Synchronisation zwischen den UI-Komponenten und den Eigenschaften des Beans. Er fungiert als Aggregator und ermöglicht kollektive Aktionen auf mehrere Bindings, wodurch das Management des Datenflusses innerhalb von Anwendungen vereinfacht wird.

:::tip Automatisches Binding
Dieser Abschnitt führt in die Grundlagen der manuellen Konfiguration von Bindings ein. Darüber hinaus können Sie Bindings automatisch basierend auf den UI-Komponenten in Ihrem Formular erzeugen. Sobald Sie die Grundlagen verstanden haben, lernen Sie mehr, indem Sie den Abschnitt [Automatisches Binding](./automatic-binding) lesen.
:::

## Bindings konfigurieren {#configure-bindings}

Beginnen Sie mit der Erstellung einer neuen Instanz von `BindingContext`, die alle Bindings für ein bestimmtes Modell verwaltet. 
Dieser Kontext stellt sicher, dass alle Bindings kollektiv validiert und aktualisiert werden können.

```java
BindingContext<Hero> context = new BindingContext<>(Hero.class);
```

:::info
Jedes Formular sollte nur eine `BindingContext`-Instanz haben, und Sie sollten diese Instanz für alle Komponenten im Formular verwenden.
:::

### Die gebundene Eigenschaft {#the-bound-property}

Eine Bindungseigenschaft ist ein spezifisches Feld oder Attribut eines Java Beans, das mit einer UI-Komponente in Ihrer App verknüpft werden kann. 
Diese Verknüpfung ermöglicht es, dass Änderungen in der UI direkt die entsprechende Eigenschaft des Datenmodells beeinflussen, und umgekehrt, was eine reaktive Benutzererfahrung erleichtert.

Beim Einrichten eines Bindings sollten Sie den Eigenschaftsnamen als String angeben. Dieser Name muss mit dem Feldnamen in der Java Bean-Klasse übereinstimmen. Hier ist ein einfaches Beispiel:

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

  // Setter und Getter
}
```

Die Methode `bind` gibt einen `BindingBuilder` zurück, der das `Binding`-Objekt erstellt und Sie können damit mehrere Einstellungen für das Binding konfigurieren. Die Methode `add`, die das Binding tatsächlich zum Kontext hinzufügt.

### Die gebundene Komponente {#the-bound-component}

Die andere Seite der Bindung ist die gebundene Komponente, die sich auf die UI-Komponente bezieht, die mit der Eigenschaft des Java Beans interagiert. 
Die gebundene Komponente kann jede UI-Komponente sein, die Benutzerinteraktion und Anzeige unterstützt, wie Textfelder, Kombinationsfelder, Kontrollkästchen oder 
jede benutzerdefinierte Komponente, die das `ValueAware`-Interface implementiert.

Die gebundene Komponente dient als Punkt der Benutzerinteraktion mit dem zugrunde liegenden Datenmodell. 
Sie zeigt Daten dem Benutzer an und erfasst Benutzerantworten, die dann an das Modell zurückgegeben werden.

```java
TextField nameTextField = new TextField("Name");
BindingContext<Hero> context = new BindingContext<>(Hero.class);
context.bind(nameTextField, "name").add();
```

## Daten lesen und schreiben {#reading-and-writing-data}

### Daten lesen {#reading-data}

Das Lesen von Daten beinhaltet das Befüllen von UI-Komponenten mit Werten aus dem Datenmodell. 
Dies erfolgt typischerweise, wenn ein Formular zunächst angezeigt wird oder wenn Sie die Daten aufgrund von Änderungen im zugrunde liegenden Modell neu laden müssen. 
Die `read`-Methode des `BindingContext` macht diesen Prozess einfach.

```java
// Angenommen, das Hero-Objekt wurde instanziiert und initialisiert
Hero hero = new Hero("Clark Kent", "Flying");

// BindingContext ist bereits mit Bindings konfiguriert
context.read(hero);
```

In diesem Beispiel nimmt die `read`-Methode eine Instanz von `Hero` und aktualisiert alle gebundenen UI-Komponenten, um die Eigenschaften des Helden widerzuspiegeln. 
Wenn sich der Name oder die Kraft des Helden ändert, zeigen die entsprechenden UI-Komponenten (wie ein `TextField` für den Namen und ein `ComboBox` für die Kräfte) 
diese neuen Werte an.

### Daten schreiben {#writing-data}

Das Schreiben von Daten beinhaltet das Sammeln von Werten aus den UI-Komponenten und das Aktualisieren des Datenmodells. 
Dies erfolgt typischerweise, wenn ein Benutzer ein Formular absendet. Die `write`-Methode übernimmt die Validierung und Aktualisierung des Modells in einem Schritt.

```java
// Dies könnte durch ein Ereignis zur Formularübermittlung ausgelöst werden
submit.onClick(event -> {
  ValidationResult results = context.write(hero);
  if (results.isValid()) {
    // Daten sind gültig und das Hero-Objekt wurde aktualisiert
    // repository.save(hero); 
  } else {
    // Validierungsfehler behandeln
    // results.getMessages();
  }
});
```

Im obigen Code wird die Methode `write` aufgerufen, wenn der Benutzer auf die Schaltfläche zum Einreichen klickt. 
Sie führt alle konfigurierten Validierungen durch und aktualisiert, falls die Daten alle Prüfungen bestehen, das `Hero`-Objekt 
mit neuen Werten aus den gebundenen Komponenten. 
Wenn die Daten gültig sind, könnten Sie sie in einer Datenbank speichern oder weiterverarbeiten. Wenn es Validierungsfehler gibt, 
sollten Sie diese entsprechend behandeln, typischerweise indem Sie Fehlermeldungen an den Benutzer anzeigen.

:::tip Berichterstattung über Validierungsfehler
Alle Kernkomponenten von webforJ verfügen über Standardkonfigurationen zur automatischen Berichterstattung über Validierungsfehler, entweder inline oder über ein Popover. Sie können dieses Verhalten über [Reporter](./validation/reporters.md) anpassen.
:::

<!-- vale off -->
## Nur-Lese-Daten {#readonly-data}
<!-- vale on -->

In bestimmten Szenarien möchten Sie möglicherweise, dass Ihre App Daten anzeigt, ohne dass der Endbenutzer diese direkt über die UI ändern kann. 
Hier werden schreibgeschützte Datenbindungen entscheidend. webforJ unterstützt die Konfiguration von Bindings als schreibgeschützt, sodass 
Sie Daten anzeigen, aber nicht über gebundene UI-Komponenten bearbeiten können.

### Konfiguration schreibgeschützter Bindings {#configuring-readonly-bindings}

Um ein schreibgeschütztes Binding einzurichten, können Sie das Binding so konfigurieren, dass die Eingaben der UI-Komponente deaktiviert oder ignoriert werden. 
Dies stellt sicher, dass die Daten aus Sicht der UI unverändert bleiben, während sie bei Bedarf programmatisch aktualisiert werden können.

```java
// Konfiguration eines Textfeldes als schreibgeschützt im Binding-Kontext
BindingContext<Hero> context = new BindingContext<>(Hero.class);
context.bind(nameTextField, "name")
    .readOnly()
    .add();
```

In dieser Konfiguration stellt `readOnly` sicher, dass das `nameTextField` keine Benutzereingaben akzeptiert, wodurch das Textfeld die 
Daten anzeigt, ohne dass Änderungen zulässig sind.

:::info
Das Binding kann die Komponente nur dann als schreibgeschützt markieren, wenn die UI-Komponenten das `ReadOnlyAware`-Interface implementiert.
:::

:::tip Komponenten-ReadOnly vs. Binding-ReadOnly
Es ist wichtig, zwischen Bindings, die Sie als schreibgeschützt konfigurieren, und UI-Komponenten, die Sie so einstellen, dass sie schreibgeschützt angezeigt werden, zu unterscheiden. 
Wenn Sie ein Binding als schreibgeschützt markieren, beeinflusst dies, wie das Binding Daten während des Schreibprozesses verwaltet und nicht nur das Verhalten der UI.

Wenn Sie ein Binding als schreibgeschützt markieren, überspringt das System die Datenaktualisierungen. Änderungen an der UI-Komponente werden nicht an das Datenmodell übermittelt. 
Dies stellt sicher, dass selbst wenn die UI-Komponente irgendwie Benutzereingaben erhält, das zugrunde liegende Datenmodell nicht aktualisiert wird. 
Die Aufrechterhaltung dieser Trennung ist entscheidend für den Erhalt der Datenintegrität in Szenarien, in denen Benutzeraktionen die Daten nicht verändern sollten.

Im Gegensatz dazu stoppt das Festlegen einer UI-Komponente als schreibgeschützt, ohne das Binding selbst als schreibgeschützt zu konfigurieren, einfach, dass der Benutzer Änderungen an der UI-Komponente vornehmen kann, 
stoppt jedoch das Binding nicht davon, das Datenmodell zu aktualisieren, wenn Änderungen programmatisch oder auf andere Weise erfolgen.
:::

## Binding-Getter und -Setter {#binding-getters-and-setters}

Setter und Getter sind Methoden in Java, die die Werte von Eigenschaften setzen und abrufen. 
Im Kontext des Datenbindens werden sie verwendet, um zu definieren, wie Eigenschaften innerhalb des Binding-Frameworks aktualisiert und abgerufen werden.

### Anpassen von Settern und Gettern {#customizing-setters-and-getters}

Obwohl webforJ standardisierte JavaBean-Namenskonventionen automatisch verwenden kann 
(z. B. `getName()`, `setName()` für eine Eigenschaft `name`), müssen Sie möglicherweise individuelles Verhalten definieren. 
Dies ist erforderlich, wenn die Eigenschaft nicht der konventionellen Benennung folgt oder wenn die Datenverarbeitung zusätzliche Logik erfordert.

### Verwendung benutzerdefinierter Getter {#using-custom-getters}

Benutzerdefinierte Getter werden verwendet, wenn der Prozess des Wertabrufs mehr als nur das Zurückgeben einer Eigenschaft umfasst. 
Zum Beispiel möchten Sie möglicherweise die Zeichenfolge formatieren, einen Wert berechnen oder bestimmte Aktionen protokollieren, wenn eine Eigenschaft abgerufen wird.

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

Benutzerdefinierte Setter kommen zum Einsatz, wenn das Setzen einer Eigenschaft zusätzliche Operationen erfordert, wie z. B. Validierung, Transformation oder Nebeneffekte
wie Protokollierung oder Benachrichtigung anderer Teile Ihrer App.

```java
BindingContext<Hero> context = new BindingContext<>(Hero.class);
context
    .bind(textField, "power")
    .useSetter((hero, name) -> {
        System.out.println("Aktualisiere den Namen von " + hero.getName() + " auf " + name);
        hero.setName(name); // Zusätzliche Operation: Protokollierung
    });
```
