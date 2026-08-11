---
sidebar_position: 2
title: Bindings
sidebar_class_name: updated-content
description: >-
  Link Java Bean properties to webforJ UI components through BindingContext to
  synchronize reads and writes between model and view.
_i18n_hash: 9a4b6da2f5a3bd524a0b3cf6a1eb86e1
---
Ein Binding in webforJ verknüpft eine bestimmte Eigenschaft eines Java Beans mit einer UI-Komponente. Diese Verknüpfung ermöglicht automatische Updates zwischen der Benutzeroberfläche und dem Backend-Modell. Jedes Binding kann die Datensynchronisation, Validierung, Transformation und Ereignisverwaltung übernehmen.

Bindings können nur über den `BindingContext` initiiert werden. Dieser verwaltet eine Sammlung von Binding-Instanzen, die jeweils eine UI-Komponente mit einer Eigenschaft eines Beans verknüpfen. Er erleichtert Gruppenoperationen über Bindings, wie Validierung und Synchronisation zwischen den UI-Komponenten und den Eigenschaften des Beans. Er fungiert als Aggregator, der kollektive Aktionen auf mehreren Bindings ermöglicht und somit die Verwaltungs des Datenflusses innerhalb von Anwendungen optimiert.

:::tip Automatisches Binding
In diesem Abschnitt werden die Grundlagen der manuellen Konfiguration von Bindings vorgestellt. Darüber hinaus können Sie Bindings automatisch basierend auf den UI-Komponenten in Ihrem Formular erstellen. Sobald Sie die Grundlagen verstanden haben, erfahren Sie mehr in dem Abschnitt [Automatisches Binding](/docs/data-binding/automatic-binding).
:::

## Bindings konfigurieren {#configure-bindings}

Beginnen Sie mit der Erstellung einer neuen Instanz von `BindingContext`, die alle Bindings für ein bestimmtes Modell verwaltet. Dieser Kontext validiert und aktualisiert alle Bindings kollektiv.

```java
BindingContext<Hero> context = new BindingContext<>(Hero.class);
```

:::info
Jedes Formular sollte nur eine `BindingContext`-Instanz haben, und Sie sollten diese Instanz für alle Komponenten im Formular verwenden.
:::

### Die gebundene Eigenschaft {#the-bound-property}

Eine Bindungseigenschaft ist ein bestimmtes Feld oder Attribut eines Java Beans, das mit einer UI-Komponente in Ihrer App verknüpft werden kann. Diese Verknüpfung ermöglicht es, Änderungen in der UI direkt auf die entsprechende Eigenschaft des Datenmodells zu übertragen und umgekehrt, so dass die UI und das Datenmodell synchron bleiben.

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

Die `bind`-Methode gibt einen `BindingBuilder` zurück, der das `Binding`-Objekt erstellt und mit dem Sie mehrere Einstellungen für das Binding konfigurieren können, sowie die `add`-Methode, mit der das Binding tatsächlich zum Kontext hinzugefügt wird.

### Die gebundene Komponente {#the-bound-component}

Die andere Seite des Bindings ist die gebundene Komponente, die sich auf die UI-Komponente bezieht, die mit der Eigenschaft des Java Beans interagiert. Die gebundene Komponente kann jede UI-Komponente sein, die Benutzerinteraktionen und Anzeige unterstützt, wie Textfelder, Kombinationsfelder, Kontrollkästchen oder jede benutzerdefinierte Komponente, die das `ValueAware`-Interface implementiert.

Die gebundene Komponente dient als Interaktionspunkt für den Benutzer mit dem zugrunde liegenden Datenmodell. Sie zeigt Daten dem Benutzer an und erfasst Benutzereingaben, die dann zurück an das Modell propagiert werden.

```java
TextField nameTextField = new TextField("Name");
BindingContext<Hero> context = new BindingContext<>(Hero.class);
context.bind(nameTextField, "name").add();
```

## Daten lesen und schreiben {#reading-and-writing-data}

### Daten lesen {#reading-data}

Daten lesen umfasst das Befüllen von UI-Komponenten mit Werten aus dem Datenmodell. Dies geschieht typischerweise, wenn ein Formular zunächst angezeigt wird oder wenn die Daten aufgrund von Änderungen im zugrunde liegenden Modell neu geladen werden müssen. Die `read`-Methode, die vom `BindingContext` bereitgestellt wird, macht diesen Prozess einfach.

```java
// Angenommen, das Hero-Objekt wurde instanziiert und initialisiert
Hero hero = new Hero("Clark Kent", "Fliegen");

// BindingContext ist bereits mit Bindings konfiguriert
context.read(hero);
```

In diesem Beispiel nimmt die `read`-Methode eine Instanz von `Hero` und aktualisiert alle gebundenen UI-Komponenten, um die Eigenschaften des Helden widerzuspiegeln. Wenn sich der Name oder die Kraft des Helden ändert, zeigen die entsprechenden UI-Komponenten (wie ein `TextField` für den Namen und ein `ComboBox` für Kräfte) diese neuen Werte an.

### Daten schreiben {#writing-data}

Daten schreiben umfasst das Sammeln von Werten aus den UI-Komponenten und das Aktualisieren des Datenmodells. Dies geschieht typischerweise, wenn ein Benutzer ein Formular absendet. Die `write`-Methode behandelt Validierung und Aktualisierung des Modells in einem Schritt.

```java
// Dies könnte durch ein Formularüberreichungsereignis ausgelöst werden
submit.onClick(event -> {
  ValidationResult results = context.write(hero);
  if (results.isValid()) {
    // Daten sind gültig, und das Hero-Objekt wurde aktualisiert
    // repository.save(hero);
  } else {
    // Fehler bei der Validierung behandeln
    // results.getMessages();
  }
});
```

Im obigen Code wird die `write`-Methode aufgerufen, wenn der Benutzer die Schaltfläche „Absenden“ klickt. Sie führt alle konfigurierten Validierungen durch und aktualisiert das `Hero`-Objekt mit neuen Werten von den gebundenen Komponenten, wenn die Daten alle Prüfungen bestehen. Wenn die Daten gültig sind, möchten Sie sie möglicherweise in einer Datenbank speichern oder weiterverarbeiten. Bei Validierungsfehlern sollten Sie dies angemessen behandeln, typischerweise, indem Sie Fehlermeldungen dem Benutzer anzeigen.

:::tip Berichterstattung über Validierungsfehler
Alle Kernkomponenten von webforJ verfügen über Standardkonfigurationen zur automatischen Berichterstattung über Validierungsfehler, entweder inline oder über ein Popover. Sie können dieses Verhalten mit [Reportern](./validation/reporters.md) anpassen.
:::

## Verschachtelte Bean-Eigenschaften <DocChip chip='since' label='26.01' /> {#nested-bean-properties}

Eine Bindungseigenschaft kann ein gepunkteter Pfad sein, der auf eine Eigenschaft innerhalb eines verschachtelten Beans verweist. Jedes Segment im Pfad folgt den Standard-JavaBean Getter- und Setter-Konventionen, sodass `address.street` über `getAddress().getStreet()` gelesen und über `getAddress().setStreet()` geschrieben wird.

```java
BindingContext<Hero> context = new BindingContext<>(Hero.class);
context.bind(streetField, "address.street").add();
context.bind(cityField, "address.city").add();
```

```java
public class Hero {
  private String name;
  private Address address;

  // Getter und Setter
}

public class Address {
  private String street;
  private String city;
  private String zip;

  // Getter und Setter
}
```

Beim Lesen wird ein Pfad sicher aufgelöst, selbst wenn ein Zwischen-Bean `null` ist. Wenn ein `Hero` keine `Address` hat, werden die Komponenten, die an `address.street` und `address.city` gebunden sind, leer gelesen, anstatt eine Ausnahme auszulösen, sodass das Formular trotzdem gefüllt wird.

Beim Schreiben erstellt der Kontext jedes fehlende Zwischen-Bean über dessen Konstruktor ohne Argumente, sodass das Schreiben des Formulars in einen `Hero` ohne `Address` eine neue, befüllte `Address` erzeugt. Eine vorhandene `Address` wird wiederverwendet.

[Jakarta validation](/docs/data-binding/validation/jakarta-validation) Annotationen an einer verschachtelten Eigenschaft werden auf die gleiche Weise erkannt wie an einer obersten Eigenschaft. Eine Annotation wie `@NotNull` auf `Address.street` kennzeichnet das `address.street` Binding als [erforderlich](/docs/data-binding/automatic-binding#bindingrequired-annotation).

:::info Pfade werden vorab validiert
Der vollständige Pfad wird validiert, wenn Sie `bind` aufrufen. Ein Tippfehler in einem Segment, auf der obersten Ebene oder tiefer im Pfad, wirft eine `IllegalArgumentException`, sodass Bindungsfehler sofort auffallen, anstatt erst beim Lesen oder Schreiben.
:::

<!-- vale off -->
## Nur-Lesen-Daten {#readonly-data}
<!-- vale on -->

In bestimmten Szenarien möchten Sie möglicherweise, dass Ihre App Daten anzeigt, ohne dem Endbenutzer zu erlauben, diese direkt über die UI zu ändern. Nur-Lesen-Daten-Bindings adressieren dies. webforJ unterstützt die Konfiguration von Bindings als nur-lesend, sodass Sie Daten anzeigen, aber nicht über gebundene UI-Komponenten bearbeiten können.

### Konfigurieren von Nur-Lesen-Bindings {#configuring-readonly-bindings}

Um ein Nur-Lesen-Binding einzurichten, können Sie das Binding so konfigurieren, dass es die Eingabe der UI-Komponente deaktiviert oder ignoriert. Die Daten bleiben somit aus der Sicht der UI unverändert, während sie bei Bedarf programmatisch aktualisiert werden.

```java
// Konfigurieren eines Textfelds als nur-lesend im Binding-Kontext
BindingContext<Hero> context = new BindingContext<>(Hero.class);
context.bind(nameTextField, "name")
  .readOnly()
  .add();
```

In dieser Konfiguration verhindert `readOnly`, dass das `nameTextField` Benutzereingaben akzeptiert, sodass das Textfeld die Daten anzeigt, ohne Änderungen zuzulassen.

:::info
Das Binding kann die Komponente nur dann als nur-lesend markieren, wenn die UI-Komponente das `ReadOnlyAware`-Interface implementiert.
:::

:::tip Komponente Nur-Lesen vs Binding Nur-Lesen
Es ist wichtig, zwischen Bindings zu unterscheiden, die Sie als nur-lesend konfigurieren, und UI-Komponenten, die Sie als nur-lesend anzeigen. Wenn Sie ein Binding als nur-lesend markieren, hat das Auswirkungen darauf, wie das Binding Daten während des Schreibprozesses verwaltet, nicht nur auf das Verhalten der UI.

Wenn Sie ein Binding als nur-lesend markieren, überspringt das System die Datenaktualisierungen. Änderungen an der UI-Komponente werden nicht an das Datenmodell übertragen. Selbst wenn die UI-Komponente dennoch Benutzereingaben erhält, wird das zugrunde liegende Datenmodell nicht aktualisiert. Diese Trennung schützt die Datenintegrität in Szenarien, in denen Benutzeraktionen die Daten nicht verändern sollten.

Im Gegensatz dazu verhindert das Festlegen einer UI-Komponente als nur-lesend, ohne das Binding selbst als nur-lesend zu konfigurieren, lediglich, dass der Benutzer Änderungen an der UI-Komponente vornimmt, hindert jedoch das Binding nicht daran, das Datenmodell zu aktualisieren, wenn Änderungen programmatisch oder auf andere Weise erfolgen.
:::

## Binding-Getter und -Setter {#binding-getters-and-setters}

Setter und Getter sind Methoden in Java, die die Werte von Eigenschaften setzen bzw. abrufen. Im Kontext des Datenbindens werden sie verwendet, um zu definieren, wie Eigenschaften innerhalb des Binding-Frameworks aktualisiert und abgerufen werden.

### Anpassen von Settern und Gettern {#customizing-setters-and-getters}

Obwohl webforJ automatisch die standardmäßigen JavaBean-Namenskonventionen verwenden kann (zum Beispiel `getName()`, `setName()` für eine Eigenschaft `name`), müssen Sie möglicherweise ein benutzerdefiniertes Verhalten definieren. Dies ist notwendig, wenn die Eigenschaft nicht der konventionellen Namensgebung folgt oder wenn die Datenverarbeitung zusätzliche Logik erfordert.

### Verwendung von benutzerdefinierten Gettern {#using-custom-getters}

Benutzerdefinierte Getter werden verwendet, wenn der Abrufprozess eines Wertes mehr als nur das Zurückgeben einer Eigenschaft umfasst. Zum Beispiel möchten Sie möglicherweise den String formatieren, einen Wert berechnen oder bestimmte Aktionen protokollieren, wenn auf eine Eigenschaft zugegriffen wird.

```java
BindingContext<Hero> context = new BindingContext<>(Hero.class);
context
  .bind(textField, "power")
  .useGetter(hero -> {
    String name = hero.getName();
    return name.toUpperCase(); // Benutzerdefinierte Logik: den Namen in Großbuchstaben umwandeln
  });
```

### Verwendung von benutzerdefinierten Settern {#using-custom-setters}

Benutzerdefinierte Setter kommen ins Spiel, wenn das Setzen einer Eigenschaft zusätzliche Operationen wie Validierung, Transformation oder Nebeneffekte erfordert, wie Protokollierung oder Benachrichtigung anderer Teile Ihrer App.

```java
BindingContext<Hero> context = new BindingContext<>(Hero.class);
context
  .bind(textField, "power")
  .useSetter((hero, name) -> {
    System.out.println("Aktualisiere den Namen von " + hero.getName() + " zu " + name);
    hero.setName(name); // Zusätzliche Operation: Protokollierung
  });
```
