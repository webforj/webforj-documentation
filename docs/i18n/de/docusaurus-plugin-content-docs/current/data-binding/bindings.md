---
sidebar_position: 2
title: Bindings
sidebar_class_name: updated-content
description: >-
  Link Java Bean properties to webforJ UI components through BindingContext to
  synchronize reads and writes between model and view.
_i18n_hash: 047676a64833283bcc160d7a8d226559
---
Eine Bindung in webforJ verbindet eine spezifische Eigenschaft eines Java Beans mit einer UI-Komponente. Diese Verknüpfung ermöglicht automatische Updates zwischen der Benutzeroberfläche und dem Backend-Modell. Jede Bindung kann Daten-Synchronisation, Validierung, Transformation und Ereignismanagement verwalten.

Sie können Bindungen nur über den `BindingContext` einrichten. Er verwaltet eine Sammlung von Bindungsinstanzen, die jede eine UI-Komponente mit einer Eigenschaft eines Beans verknüpfen. Er erleichtert Gruppenoperationen auf Bindungen, wie z.B. Validierung und Synchronisation zwischen den UI-Komponenten und den Eigenschaften des Beans. Er fungiert als Aggregator, der kollektive Aktionen auf mehreren Bindungen ermöglicht und somit das Management des Datenflusses innerhalb von Anwendungen optimiert.

:::tip Automatische Bindung
Dieser Abschnitt führt in die Grundlagen der manuellen Konfiguration von Bindungen ein. Darüber hinaus können Sie Bindungen automatisch basierend auf den UI-Komponenten in Ihrem Formular erstellen. Sobald Sie die Grundlagen verstanden haben, erfahren Sie mehr im Abschnitt [Automatische Bindung](/docs/data-binding/automatic-binding).
:::

## Bindungen konfigurieren {#configure-bindings}

Beginnen Sie mit der Erstellung einer neuen Instanz von `BindingContext`, die alle Bindungen für ein bestimmtes Modell verwaltet. 
Dieser Kontext validiert und aktualisiert alle Bindungen kollektiv.

```java
BindingContext<Hero> context = new BindingContext<>(Hero.class);
```

:::info
Jedes Formular sollte nur eine `BindingContext`-Instanz haben, und Sie sollten diese Instanz für alle Komponenten im Formular verwenden.
:::

### Die gebundene Eigenschaft {#the-bound-property}

Eine Bindungseigenschaft ist ein spezifisches Feld oder Attribut eines Java Beans, das mit einer UI-Komponente in Ihrer App verknüpft werden kann. 
Diese Verknüpfung ermöglicht es, dass Änderungen in der UI direkt die entsprechende Eigenschaft des Datenmodells beeinflussen und umgekehrt, 
so dass die UI und das Datenmodell synchron bleiben.

Beim Einrichten einer Bindung sollten Sie den Eigenschaftsnamen als Zeichenfolge angeben. Dieser Name muss mit dem Feldnamen in der Java Bean-Klasse übereinstimmen. Hier ist ein einfaches Beispiel:

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

Die `bind`-Methoden geben einen `BindingBuilder` zurück, der das `Binding`-Objekt erstellt, das Sie verwenden können, um die Bindungseinstellungen zu konfigurieren. Die `add`-Methode fügt die Bindung tatsächlich zum Kontext hinzu.

### Die gebundene Komponente {#the-bound-component}

Die andere Seite der Bindung ist die gebundene Komponente, die sich auf die UI-Komponente bezieht, die mit der Eigenschaft des Java Beans interagiert. 
Die gebundene Komponente kann jede UI-Komponente sein, die Benutzerinteraktion und Anzeige unterstützt, wie z.B. Textfelder, Kombinationsfelder, Kontrollkästchen oder 
jede benutzerdefinierte Komponente, die das `ValueAware`-Interface implementiert.

Die gebundene Komponente dient als Schnittstelle für den Benutzer zur Interaktion mit dem zugrunde liegenden Datenmodell. 
Sie zeigt Daten für den Benutzer an und erfasst auch die Benutzereingaben, die dann zurück an das Modell propagiert werden.

```java
TextField nameTextField = new TextField("Name");
BindingContext<Hero> context = new BindingContext<>(Hero.class);
context.bind(nameTextField, "name").add();
```

## Lesen und Schreiben von Daten {#reading-and-writing-data}

### Daten lesen {#reading-data}

Daten lesen umfasst das Befüllen von UI-Komponenten mit Werten aus dem Datenmodell. 
Dies geschieht typischerweise, wenn ein Formular ursprünglich angezeigt wird oder wenn Sie die Daten aufgrund von Änderungen im zugrunde liegenden Modell neu laden müssen. 
Die `read`-Methode, die von `BindingContext` bereitgestellt wird, macht diesen Prozess unkompliziert.

```java
// Angenommen, das Hero-Objekt wurde instanziiert und initialisiert
Hero hero = new Hero("Clark Kent", "Fliegen");

// BindingContext ist bereits mit Bindungen konfiguriert
context.read(hero);
```

In diesem Beispiel nimmt die `read`-Methode eine Instanz von `Hero` und aktualisiert alle gebundenen UI-Komponenten, um die Eigenschaften des Helden widerzuspiegeln. 
Wenn sich der Name oder die Kraft des Helden ändert, zeigen die entsprechenden UI-Komponenten (wie ein `TextField` für den Namen und ein `ComboBox` für die Kräfte) diese neuen Werte an.

### Daten schreiben {#writing-data}

Das Schreiben von Daten umfasst das Sammeln von Werten aus den UI-Komponenten und das Aktualisieren des Datenmodells. 
Dies geschieht typischerweise, wenn ein Benutzer ein Formular absendet. Die `write`-Methode behandelt die Validierung und das Aktualisieren des Modells in einem Schritt.

```java
// Dies könnte durch ein Ereignis beim Absenden des Formulars ausgelöst werden
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

Im obigen Code, wenn der Benutzer auf die Schaltfläche "Absenden" klickt, wird die Methode `write` aufgerufen. 
Sie führt alle konfigurierten Validierungen durch und wenn die Daten alle Prüfungen bestehen, wird das `Hero`-Objekt 
mit neuen Werten aus den gebundenen Komponenten aktualisiert. 
Wenn die Daten gültig sind, möchten Sie möglicherweise in einer Datenbank speichern oder sie weiterverarbeiten. Wenn es Validierungsfehler gibt, 
sollten Sie diese angemessen behandeln, typischerweise durch Anzeige von Fehlermeldungen für den Benutzer.

:::tip Fehlermeldungen bei der Validierung
Alle Kernkomponenten von webforJ verfügen über Standardkonfigurationen, um Validierungsfehler automatisch zu melden, entweder inline oder über ein Popover. Sie können dieses Verhalten mit [Reportern](./validation/reporters.md) anpassen.
:::

## Verschachtelte Bean-Eigenschaften <DocChip chip='since' label='26.01' /> {#nested-bean-properties}

Eine Bindungseigenschaft kann ein gepunkteter Pfad sein, der auf eine Eigenschaft innerhalb eines verschachtelten Beans zeigt. Jede Segment im Pfad folgt den Standard-JavaBean-Getter- und Setter-Konventionen, sodass `address.street` durch `getAddress().getStreet()` gelesen und durch `getAddress().setStreet()` geschrieben wird.

```java
BindingContext<Hero> context = new BindingContext<>(Hero.class);
context.bind(streetField, "address.street").add();
context.bind(cityField, "address.city").add();
```

```java
public class Hero {
  private String name;
  private Address address;

  // getter und setter
}

public class Address {
  private String street;
  private String city;
  private String zip;

  // getter und setter
}
```

Beim Lesen löst ein Pfad sicher aus, selbst wenn ein zwischenliegender Bean `null` ist. Wenn ein `Hero` keine `Address` hat, werden die an `address.street` und `address.city` gebundenen Komponenten als leer gelesen, statt eine Ausnahme auszulösen, sodass das Formular dennoch befüllt wird.

Beim Schreiben erstellt der Kontext jeden fehlenden zwischenliegenden Bean über seinen Standardkonstruktor, sodass das Schreiben des Formulars in ein `Hero` ohne `Address` ein neues, befülltes `Address` erzeugt. Eine bestehende `Address` wird wiederverwendet.

[Jakarta-Validierung](/docs/data-binding/validation/jakarta-validation) Annotationen auf einer verschachtelten Eigenschaft werden auf die gleiche Weise erkannt wie bei einer obersten Eigenschaft. Eine Annotation wie `@NotNull` auf `Address.street` kennzeichnet die Bindung `address.street` als [erforderlich](/docs/data-binding/automatic-binding#bindingrequired-annotation).

:::info Pfade werden im Vorfeld validiert
Der vollständige Pfad wird validiert, wenn Sie `bind` aufrufen. Ein Tippfehler in einem Segment, auf oberster Ebene oder tiefer im Pfad, wirft eine `IllegalArgumentException`, sodass Bindungsfehler sofort sichtbar werden und nicht erst beim Lesen oder Schreiben.
:::

<!-- vale off -->
## Nur-Lese-Daten {#readonly-data}
<!-- vale on -->

In bestimmten Szenarien möchten Sie möglicherweise, dass Ihre App Daten anzeigt, ohne dass der Endbenutzer sie direkt über die UI ändern kann. 
Nur-Lese-Datenbindungen adressieren dies. webforJ unterstützt die Konfiguration von Bindungen als nur lesbar, so dass 
Sie Daten anzeigen können, aber sie nicht durch gebundene UI-Komponenten bearbeiten können.

### Konfigurieren von Nur-Lese-Bindungen {#configuring-readonly-bindings}

Um eine Nur-Lese-Bindung einzurichten, können Sie die Bindung so konfigurieren, dass sie die Benutzereingabe für UI-Komponenten deaktiviert oder ignoriert. 
Die Daten bleiben dann aus Sicht der UI unverändert, während sie bei Bedarf weiterhin programmgesteuert aktualisiert werden.

```java
// Konfigurieren eines Textfelds als nur lesbar im Binding-Kontext
BindingContext<Hero> context = new BindingContext<>(Hero.class);
context.bind(nameTextField, "name")
  .readOnly()
  .add();
```

In dieser Konfiguration verhindert `readOnly`, dass das `nameTextField` Benutzereingaben akzeptiert, sodass das Textfeld die 
Daten anzeigt, ohne Änderungen zuzulassen.

:::info
Die Bindung kann die Komponente nur als nur lesbar markieren, wenn die UI-Komponenten das `ReadOnlyAware`-Interface implementieren.
:::

:::tip Nur-Lese-Komponente vs. Nur-Lese-Bindung
Es ist wichtig, zwischen Bindungen zu unterscheiden, die Sie als nur lesbar konfigurieren, und UI-Komponenten, die Sie so einstellen, dass sie als nur lesbar angezeigt werden. 
Wenn Sie eine Bindung als nur lesbar markieren, wirkt sich dies darauf aus, wie die Bindung Daten während des Schreibprozesses verwaltet, nicht nur auf das Verhalten der UI.

Wenn Sie eine Bindung als nur lesbar markieren, überspringt das System Datenaktualisierungen. Änderungen an der UI-Komponente werden nicht an das Datenmodell zurückübertragen. 
Infolgedessen wird das zugrunde liegende Datenmodell nicht aktualisiert, selbst wenn die UI-Komponente irgendwie Benutzereingaben erhält. 
Diese Trennung schützt die Datenintegrität in Szenarien, in denen Benutzeraktionen die Daten nicht ändern sollten.

Im Gegensatz dazu stoppt das Setzen einer UI-Komponente als nur lesbar, ohne die Bindung selbst als nur lesbar zu konfigurieren, einfach den Benutzer daran, Änderungen an der UI-Komponente vorzunehmen, 
verhindert jedoch nicht, dass die Bindung das Datenmodell aktualisiert, wenn Änderungen programmgesteuert oder auf andere Weise auftreten.
:::

## Binding-Getter und -Setter {#binding-getters-and-setters}

Getter und Setter sind Methoden in Java, die jeweils die Werte von Eigenschaften setzen und abrufen.
Im Kontext der Datenbindung werden sie verwendet, um zu definieren, wie Eigenschaften innerhalb des Bindungsrahmenwerks aktualisiert und abgerufen werden.

### Anpassen von Gettern und Settern {#customizing-setters-and-getters}

Obwohl webforJ die standardmäßigen JavaBean-Namenskonventionen automatisch verwenden kann 
(zum Beispiel `getName()`, `setName()` für eine Eigenschaft `name`), müssen Sie möglicherweise benutzerdefiniertes Verhalten definieren. 
Dies ist notwendig, wenn die Eigenschaft nicht den konventionellen Namen folgt oder wenn die Datenverarbeitung zusätzliche Logik erfordert.

### Benutzerdefinierte Getter verwenden {#using-custom-getters}

Benutzerdefinierte Getter werden verwendet, wenn der Wertabrufprozess mehr als nur das Zurückgeben einer Eigenschaft umfasst.
Zum Beispiel möchten Sie möglicherweise den String formatieren, einen Wert berechnen oder bestimmte Aktionen protokollieren, wenn auf eine Eigenschaft zugegriffen wird.

```java
BindingContext<Hero> context = new BindingContext<>(Hero.class);
context
  .bind(textField, "power")
  .useGetter(hero -> {
    String name = hero.getName();
    return name.toUpperCase(); // Benutzerdefinierte Logik: Namen in Großbuchstaben umwandeln
  });
```

### Benutzerdefinierte Setter verwenden {#using-custom-setters}

Benutzerdefinierte Setter kommen zur Anwendung, wenn das Setzen einer Eigenschaft zusätzliche Operationen wie Validierung, Transformation oder Nebenwirkungen 
wie Protokollierung oder Benachrichtigung anderer Teile Ihrer App erfordert.

```java
BindingContext<Hero> context = new BindingContext<>(Hero.class);
context
  .bind(textField, "power")
  .useSetter((hero, name) -> {
    System.out.println("Aktualisierung des Namens von " + hero.getName() + " zu " + name);
    hero.setName(name); // Zusätzliche Operation: Protokollierung
  });
```
