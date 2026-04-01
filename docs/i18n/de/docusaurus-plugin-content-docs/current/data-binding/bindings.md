---
sidebar_position: 2
title: Bindings
_i18n_hash: c567705312942e83f5e83a77f1d510a4
---
Eine Bindung in webforJ verknüpft eine bestimmte Eigenschaft eines Java Beans mit einer UI-Komponente. Diese Verknüpfung ermöglicht automatische Aktualisierungen zwischen der Benutzeroberfläche und dem Backend-Modell. Jede Bindung kann die Daten-Synchronisation, Validierung, Transformation und Ereignisverwaltung übernehmen.

Sie können Bindungen nur über den `BindingContext` initiieren. Dieser verwaltet eine Sammlung von Bindungsinstanzen, die jeweils eine UI-Komponente mit einer Eigenschaft eines Beans verknüpfen. Er erleichtert Gruppenoperationen zu Bindungen, wie z.B. Validierung und Synchronisation zwischen den UI-Komponenten und den Eigenschaften des Beans. Er fungiert als Aggregator, der kollektive Aktionen auf mehreren Bindungen ermöglicht und damit das Management des Datenflusses innerhalb von Anwendungen optimiert.

:::tip Automatische Bindung
Dieser Abschnitt führt in die Grundlagen der manuellen Konfiguration von Bindungen ein. Darüber hinaus können Sie Bindungen automatisch basierend auf den UI-Komponenten in Ihrem Formular erstellen. Sobald Sie die Grundlagen verstanden haben, lesen Sie mehr im Abschnitt [Automatische Bindung](./automatic-binding).
:::

## Bindungen konfigurieren {#configure-bindings}

Beginnen Sie damit, eine neue Instanz von `BindingContext` zu erstellen, die alle Bindungen für ein bestimmtes Modell verwaltet. Dieser Kontext stellt sicher, dass alle Bindungen kollektiv validiert und aktualisiert werden können.

```java
BindingContext<Hero> context = new BindingContext<>(Hero.class);
```

:::info
Jedes Formular sollte nur eine `BindingContext`-Instanz haben, und Sie sollten diese Instanz für alle Komponenten im Formular verwenden.
:::

### Die gebundene Eigenschaft {#the-bound-property}

Eine Bindungseigenschaft ist ein spezifisches Feld oder Attribut eines Java Beans, das mit einer UI-Komponente in Ihrer App verknüpft werden kann. Diese Verknüpfung ermöglicht es, dass Änderungen in der Benutzeroberfläche direkt die entsprechende Eigenschaft des Datenmodells beeinflussen und umgekehrt, was ein reaktives Benutzererlebnis erleichtert.

Beim Einrichten einer Bindung sollten Sie den Eigenschaftsnamen als String bereitstellen. Dieser Name muss mit dem Feldnamen in der Java Bean-Klasse übereinstimmen. Hier ist ein einfaches Beispiel:

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

Die Methode `bind` gibt ein `BindingBuilder` zurück, mit dem das `Binding`-Objekt erstellt wird und das Sie verwenden können, um die Bindung mit verschiedenen Einstellungen zu konfigurieren; die Methode `add`, die tatsächlich die Bindung zum Kontext hinzufügt.

### Die gebundene Komponente {#the-bound-component}

Die andere Seite der Bindung ist die gebundene Komponente, die sich auf die UI-Komponente bezieht, die mit der Eigenschaft des Java Beans interagiert. Die gebundene Komponente kann jede UI-Komponente sein, die Benutzerinteraktion und -darstellung unterstützt, wie z.B. Textfelder, Kombinationsfelder, Kontrollkästchen oder jede benutzerdefinierte Komponente, die das `ValueAware`-Interface implementiert.

Die gebundene Komponente dient als Schnittstelle für den Benutzer zur Interaktion mit dem zugrunde liegenden Datenmodell. Sie zeigt Daten dem Benutzer an und erfasst auch Benutzereingaben, die dann zurück an das Modell weitergeleitet werden.

```java
TextField nameTextField = new TextField("Name");
BindingContext<Hero> context = new BindingContext<>(Hero.class);
context.bind(nameTextField, "name").add();
```

## Daten lesen und schreiben {#reading-and-writing-data}

### Daten lesen {#reading-data}

Das Lesen von Daten beinhaltet das Befüllen von UI-Komponenten mit Werten aus dem Datenmodell. Dies geschieht in der Regel, wenn ein Formular anfangs angezeigt wird oder wenn Sie die Daten aufgrund von Änderungen im zugrunde liegenden Modell neu laden müssen. Die `read`-Methode, die von `BindingContext` bereitgestellt wird, erleichtert diesen Prozess.

```java
// Angenommen, das Hero-Objekt wurde instanziiert und initialisiert
Hero hero = new Hero("Clark Kent", "Fliegen");

// BindingContext ist bereits mit Bindungen konfiguriert
context.read(hero);
```

In diesem Beispiel nimmt die `read`-Methode eine Instanz von `Hero` und aktualisiert alle gebundenen UI-Komponenten, um die Eigenschaften des Helden widerzuspiegeln. Wenn sich der Name oder die Kraft des Helden ändert, zeigen die entsprechenden UI-Komponenten (wie ein `TextField` für den Namen und ein `ComboBox` für die Kräfte) diese neuen Werte an.

### Daten schreiben {#writing-data}

Das Schreiben von Daten beinhaltet das Sammeln von Werten aus den UI-Komponenten und das Aktualisieren des Datenmodells. Dies geschieht typischerweise, wenn ein Benutzer ein Formular absendet. Die `write`-Methode kümmert sich um Validierung und Modellaktualisierung in einem Schritt.

```java
// Dies könnte durch ein Formularübermittlungsereignis ausgelöst werden
submit.onClick(event -> {
  ValidationResult results = context.write(hero);
  if (results.isValid()) {
    // Daten sind gültig, und das Hero-Objekt wurde aktualisiert
    // repository.save(hero); 
  } else {
    // Validierungsfehler behandeln
    // results.getMessages();
  }
});
```

Im obigen Code wird die Methode `write` aufgerufen, wenn der Benutzer die Schaltfläche „Abschicken“ klickt. Sie führt alle konfigurierten Validierungen durch, und wenn die Daten alle Prüfungen bestehen, aktualisiert sie das `Hero`-Objekt mit neuen Werten aus den gebundenen Komponenten. Wenn die Daten gültig sind, könnten Sie sie in einer Datenbank speichern oder weiterverarbeiten. Wenn es Validierungsfehler gibt, sollten Sie diese entsprechend behandeln, typischerweise durch die Anzeige von Fehlermeldungen an den Benutzer.

:::tip Berichterstattung über Validierungsfehler
Alle Kernkomponenten von webforJ haben Standardkonfigurationen, um Validierungsfehler automatisch zu melden, entweder inline oder über ein Popover. Sie können dieses Verhalten mit [Reporters](./validation/reporters.md) anpassen.
:::

<!-- vale off -->
## Nur-Lese-Daten {#readonly-data}
<!-- vale on -->

In bestimmten Szenarien möchten Sie möglicherweise, dass Ihre App Daten anzeigt, ohne dass der Endbenutzer diese direkt über die Benutzeroberfläche ändern kann. Hier kommen die Bindungen für Nur-Lese-Daten ins Spiel. webforJ unterstützt die Konfiguration von Bindungen als nur-lesend, wodurch sichergestellt wird, dass Sie Daten anzeigen, aber nicht über gebundene UI-Komponenten bearbeiten können.

### Konfigurieren von Nur-Lese-Bindungen {#configuring-readonly-bindings}

Um eine Nur-Lese-Bindung einzurichten, können Sie die Bindung so konfigurieren, dass sie die Eingabe der UI-Komponente deaktiviert oder ignoriert. Dies stellt sicher, dass die Daten aus Sicht der Benutzeroberfläche unverändert bleiben, während sie bei Bedarf programmgesteuert aktualisiert werden können.

```java
// Konfigurieren eines Textfelds, das im Binding-Kontext nur-lesend ist
BindingContext<Hero> context = new BindingContext<>(Hero.class);
context.bind(nameTextField, "name")
  .readOnly()
  .add();
```

In dieser Konfiguration stellt `readOnly` sicher, dass das `nameTextField` keine Benutzereingaben akzeptiert und das Textfeld daher die Daten anzeigt, ohne Änderungen zuzulassen.

:::info
Die Bindung kann die Komponente nur dann als nur-lesend markieren, wenn die UI-Komponenten das `ReadOnlyAware`-Interface implementieren.
:::

:::tip Komponenten-Nur-Lese- vs. Binding-Nur-Lese
Es ist wichtig, zwischen Bindungen, die Sie als nur-lesend konfigurieren, und UI-Komponenten, die Sie als nur-lesend festlegen, zu unterscheiden. Wenn Sie eine Bindung als nur-lesend markieren, wirkt sich dies darauf aus, wie die Bindung die Daten während des Schreibprozesses verwaltet, nicht nur auf das Verhalten der Benutzeroberfläche.

Wenn Sie eine Bindung als nur-lesend markieren, überspringt das System die Datenaktualisierungen. Änderungen an der UI-Komponente werden nicht an das Datenmodell übertragen. So wird sichergestellt, dass selbst wenn die UI-Komponente irgendwie Benutzereingaben erhält, das zugrunde liegende Datenmodell nicht aktualisiert wird. Diese Trennung aufrechtzuerhalten ist entscheidend, um die Datenintegrität in Szenarien zu wahren, in denen Benutzeraktionen die Daten nicht ändern sollten.

Im Gegensatz dazu wird das Setzen einer UI-Komponente als nur-lesend, ohne die Bindung selbst als nur-lesend zu konfigurieren, einfach den Benutzer daran hindern, Änderungen an der UI-Komponente vorzunehmen, verhindert jedoch nicht, dass die Bindung das Datenmodell aktualisiert, wenn Änderungen programmgesteuert oder auf andere Weise erfolgen.
:::

## Bindungsgetter und -setter {#binding-getters-and-setters}

Setter und Getter sind Methoden in Java, die die Werte von Eigenschaften setzen und abrufen. Im Kontext der Datenbindung werden sie verwendet, um zu definieren, wie Eigenschaften innerhalb des Bindungsrahmens aktualisiert und abgerufen werden.

### Anpassen von Settern und Gettern {#customizing-setters-and-getters}

Obwohl webforJ die Standard-JavaBean-Namenskonventionen automatisch verwenden kann (zum Beispiel `getName()`, `setName()` für eine Eigenschaft `name`), müssen Sie möglicherweise benutzerdefinierte Verhaltensweisen definieren. Dies ist erforderlich, wenn die Eigenschaft nicht den konventionellen Namenskonventionen folgt oder wenn die Datenverarbeitung zusätzliche Logik erfordert.

### Verwendung benutzerdefinierter Getter {#using-custom-getters}

Benutzerdefinierte Getter werden verwendet, wenn der Prozess des Abrufens des Werts mehr beinhaltet, als nur eine Eigenschaft zurückzugeben. Beispielsweise möchten Sie möglicherweise den String formatieren, einen Wert berechnen oder bestimmte Aktionen protokollieren, wenn auf eine Eigenschaft zugegriffen wird.

```java
BindingContext<Hero> context = new BindingContext<>(Hero.class);
context
  .bind(textField, "power")
  .useGetter(hero -> {
    String name = hero.getName();
    return name.toUpperCase(); // Benutzerdefinierte Logik: Name in Großbuchstaben umwandeln
  });
```

### Verwendung benutzerdefinierter Setter {#using-custom-setters}

Benutzerdefinierte Setter kommen ins Spiel, wenn das Setzen einer Eigenschaft zusätzliche Operationen erfordert, wie z.B. Validierung, Transformation oder Nebenwirkungen wie Protokollierung oder Benachrichtigung anderer Teile Ihrer App.

```java
BindingContext<Hero> context = new BindingContext<>(Hero.class);
context
  .bind(textField, "power")
  .useSetter((hero, name) -> {
    System.out.println("Aktualisieren des Namens von " + hero.getName() + " auf " + name);
    hero.setName(name); // Zusätzliche Operation: Protokollierung
  });
```
