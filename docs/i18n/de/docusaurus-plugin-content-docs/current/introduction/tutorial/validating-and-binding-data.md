---
title: Validating and Binding Data
sidebar_position: 5
pagination_next: null
_i18n_hash: 3efedcc32a2111ba6ce08c1a3ee6b477
---
Datenbindung ist ein Mechanismus, der die UI-Komponenten Ihrer Anwendung direkt mit dem zugrunde liegenden Datenmodell verbindet und die automatische Synchronisierung von Werten zwischen beiden ermöglicht. Dies eliminiert die Notwendigkeit für wiederholte Getter- und Setter-Aufrufe, reduziert die Entwicklungszeit und verbessert die Zuverlässigkeit des Codes.

Validierung stellt in diesem Kontext sicher, dass die in das Formular eingegebenen Daten definierten Regeln entsprechen, wie beispielsweise nicht leer oder einem bestimmten Format zu folgen. Durch die Kombination von Datenbindung mit Validierung können Sie die Benutzererfahrung optimieren und dabei die Datenintegrität gewährleisten, ohne umfangreiche manuelle Überprüfungen durchführen zu müssen.

Für weitere Informationen zur Datenbindung siehe [diesen Artikel.](../../data-binding/overview) Um die App auszuführen:

- Gehen Sie in das Verzeichnis `4-validating-and-binding-data`.
- Führen Sie den Befehl `mvn jetty:run` aus.

<div class="videos-container">
  <video controls>
    <source src="https://cdn.webforj.com/webforj-documentation/video/tutorials/validating-and-binding-data.mp4" type="video/mp4"/>
  </video>
</div>

### Felder binden {#binding-the-fields}

Die Datenbindungseinrichtung beginnt mit der Initialisierung eines `BindingContext` für das `Customer`-Modell. Der `BindingContext` verknüpft die Modell-Eigenschaften mit den Formularfeldern und ermöglicht eine automatische Datensynchronisierung. Dies wird im Konstruktor von `FormView` eingerichtet.

```java title="FormView.java"
BindingContext<Customer> context;
context = BindingContext.of(this, Customer.class, true);
```

`BindingContext.of(this, Customer.class, true)` initialisiert den Bindungskontext für die Klasse `Customer`. Der dritte Parameter, `true`, aktiviert die [jakarta-Validierung](https://beanvalidation.org/).

:::info
Diese Implementierung nutzt die automatische Bindung, wie im [Artikel über Datenbindung](../../data-binding/automatic-binding) beschrieben. Dies funktioniert, wenn die Felder im Datenmodell `Customer` denselben Namen wie die entsprechenden Felder im `FormView` haben.

Sollten die Felder nicht denselben Namen tragen, können Sie die `UseProperty`-Annotation im Formular über dem Feld hinzufügen, das Sie binden möchten, damit sie wissen, auf welche Datenfelder verwiesen werden soll.
:::

### Datenbindung mit `onDidEnter()` {#data-binding-with-ondidenter}

Die Methode `onDidEnter` nutzt die Datenbindungs-Einrichtung, um den Prozess des Befüllens der Formularfelder zu vereinfachen. Anstatt Werte manuell für jedes Feld festzulegen, werden die Daten jetzt automatisch mit dem `BindingContext` synchronisiert.

```java {7}
@Override
  public void onDidEnter(DidEnterEvent event, ParametersBag parameters) {
    parameters.get("id").ifPresent(id -> {
      customer = Service.getCurrent().getCustomerByKey(UUID.fromString(id));
      customerId = id;
    });
    context.read(customer);
  }
```

Die Methode `context.read` im Datenbindungssystem von webforJ synchronisiert die Felder einer UI-Komponente mit den Werten aus einem Datenmodell. Sie wird in diesem Fall verwendet, um Formularfelder mit Daten aus einem vorhandenen Modell zu befüllen und sicherzustellen, dass die UI den aktuellen Zustand der Daten widerspiegelt.

## Daten validieren {#validating-data}

Die Validierung stellt sicher, dass die in das Formular eingegebenen Daten den festgelegten Regeln entsprechen, die Datenqualität verbessern und ungültige Übermittlungen verhindern. Mit der Datenbindung muss die Validierung nicht mehr manuell implementiert werden, sondern kann einfach konfiguriert werden, sodass Echtzeit-Feedback zu Benutzereingaben gegeben wird.

### Validierungsregeln definieren {#defining-validation-rules}

Mit [Jakarta](https://beanvalidation.org) und regulären Ausdrücken können Sie eine Vielzahl von Regeln für ein Feld festlegen. Häufig verwendete Beispiele sind die Sicherstellung, dass das Feld nicht leer oder null ist oder einem bestimmten Muster folgt. Durch Annotationen in der Klasse `Customer` können Sie jakarta-Validierungsparameter für das Feld angeben.

:::info
Weitere Details zur Einrichtung der Validierung sind [hier](../../data-binding/validation/jakarta-validation.md#installation) verfügbar.
:::

```java
  @NotEmpty(message = "Name darf nicht leer sein")
  @Pattern(regexp = "[a-zA-Z]*", message = "Ungültige Zeichen")
  private String firstName = "";
```

Die `onValidate`-Methode wird dann hinzugefügt, um den Zustand des `Submit`-Buttons basierend auf der Gültigkeit der Formularfelder zu steuern. Dies stellt sicher, dass nur gültige Daten übermittelt werden können.

```java title="FormView.java"
context.onValidate(e -> submit.setEnabled(e.isValid()));
```

`e.isValid()` gibt true zurück, wenn alle Felder gültig sind, und false, wenn nicht. Das bedeutet, dass der `Submit`-Button aktiviert ist, solange alle Felder gültig sind. Andernfalls bleibt er deaktiviert, was eine Übermittlung verhindert, bis Korrekturen vorgenommen wurden.

### Einträge hinzufügen und bearbeiten mit Validierung {#adding-and-editing-entries-with-validation}

Die Methode `submitCustomer()` validiert jetzt die Daten mithilfe des `BindingContext`, bevor sie hinzuzufügende oder bearbeitende Operationen durchführen. Dieser Ansatz beseitigt die Notwendigkeit für manuelle Validierungsprüfungen und nutzt die integrierten Mechanismen des Kontexts, um sicherzustellen, dass nur gültige Daten verarbeitet werden.

- **Hinzufügen-Modus**: Wenn keine `id` angegeben ist, befindet sich das Formular im Hinzufügen-Modus. Die validierten Daten werden in das `Customer`-Modell geschrieben und über `Service.getCurrent().addCustomer(customer)` in das Repository hinzugefügt.
- **Bearbeiten-Modus**: Wenn eine `id` vorhanden ist, ruft die Methode die entsprechenden Kundendaten ab, aktualisiert sie mit validierten Eingaben und überträgt die Änderungen in das Repository.

Durch den Aufruf von `context.write(customer)` wird eine Instanz eines `ValidationResult` zurückgegeben. Diese Klasse zeigt an, ob die Validierung erfolgreich war, und speichert alle Nachrichten, die mit diesem Ergebnis verbunden sind.

Dieser Code stellt sicher, dass alle Änderungen validiert und automatisch auf das Modell angewendet werden, bevor ein neuer Eintrag hinzugefügt oder ein bestehender `Customer` bearbeitet wird.

```java title="FormView.java"
private void submitCustomer() {
  ValidationResult results = context.write(customer);
  if (results.isValid()) {
    if (customerId.isEmpty()) {
      Service.getCurrent().addCustomer(customer);
    }
    Router.getCurrent().navigate(DemoView.class);
  }
}
```

Durch den Abschluss dieses Schrittes unterstützt die Anwendung nun Datenbindung und Validierung und stellt sicher, dass die Formulareingaben mit dem Modell synchronisiert werden und den vordefinierten Regeln entsprechen.
