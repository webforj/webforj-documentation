---
title: Validierung und Bindung von Daten
sidebar_position: 5
pagination_next: null
_i18n_hash: 11d03e09c4c37172713713649c920e9e
---
Datenbindung ist ein Mechanismus, der die UI-Komponenten Ihrer App direkt mit dem zugrunde liegenden Datenmodell verbindet und somit eine automatische Synchronisierung der Werte zwischen beiden ermöglicht. Dadurch entfällt die Notwendigkeit für wiederholte Getter- und Setter-Aufrufe, was die Entwicklungszeit verkürzt und die Zuverlässigkeit des Codes verbessert.

Validierung stellt in diesem Zusammenhang sicher, dass die in das Formular eingegebenen Daten den vordefinierten Regeln entsprechen, wie beispielsweise nicht leer zu sein oder einem bestimmten Format zu folgen. Durch die Kombination von Datenbindung mit Validierung können Sie die Benutzererfahrung optimieren und gleichzeitig die Integrität der Daten gewährleisten, ohne umfangreiche manuelle Überprüfungen schreiben zu müssen.

Für weitere Informationen zur Datenbindung siehe [diesen Artikel](../../data-binding/overview). Um die App auszuführen:

- Gehen Sie in das Verzeichnis `4-validating-and-binding-data`
- Führen Sie den Befehl `mvn jetty:run` aus

<div class="videos-container">
  <video controls>
    <source src="https://cdn.webforj.com/webforj-documentation/video/tutorials/validating-and-binding-data.mp4" type="video/mp4"/>
  </video>
</div>

### Bindung der Felder {#binding-the-fields}

Die Einrichtung der Datenbindung beginnt mit der Initialisierung eines `BindingContext` für das `Customer`-Modell. Der `BindingContext` verknüpft die Modell-Eigenschaften mit den Formularfeldern, wodurch eine automatische Daten-Synchronisierung ermöglicht wird. Dies wird im Konstruktor von `FormView` eingerichtet.

```java title="FormView.java"
BindingContext<Customer> context;
context = BindingContext.of(this, Customer.class, true);
```

`BindingContext.of(this, Customer.class, true)` initialisiert den Binding-Kontext für die `Customer`-Klasse. Der dritte Parameter `true` aktiviert die [jakarta validation](https://beanvalidation.org/).

:::info
Diese Implementierung verwendet Auto-Binding, wie im [Data Binding Artikel](../../data-binding/automatic-binding) beschrieben. Dies funktioniert, wenn die Felder im Datenmodell `Customer` denselben Namen wie die entsprechenden Felder in der `FormView` haben.

Sollten die Felder nicht denselben Namen haben, können Sie die `UseProperty`-Annotation im Formular über das Feld hinzufügen, das Sie binden möchten, damit sie wissen, auf welche Datenfelder verwiesen werden soll.
:::

### Datenbindung mit `onDidEnter()` {#data-binding-with-ondidenter}

Die Methode `onDidEnter` nutzt die Einrichtung der Datenbindung, um den Prozess des Befüllens der Formularfelder zu optimieren. Anstatt die Werte für jedes Feld manuell festzulegen, wird die Daten jetzt automatisch mit dem `BindingContext` synchronisiert.

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

Die Methode `context.read` im Datenbindungssystem von webforJ synchronisiert die Felder einer UI-Komponente mit den Werten aus einem Datenmodell. Sie wird in diesem Fall verwendet, um die Formularfelder mit Daten aus einem vorhandenen Modell zu befüllen und sicherzustellen, dass die UI den aktuellen Status der Daten widerspiegelt.

## Validierung von Daten {#validating-data}

Die Validierung stellt sicher, dass die in das Formular eingegebenen Daten bestimmten Regeln entsprechen, die die Datenqualität verbessern und ungültige Einreichungen verhindern. Mit der Datenbindung muss die Validierung nicht mehr manuell implementiert werden, sondern kann einfach konfiguriert werden, was eine Echtzeit-Rückmeldung zu Benutzeranfragen ermöglicht.

### Definition von Validierungsregeln {#defining-validation-rules}

Mit [Jakarta](https://beanvalidation.org) und regulären Ausdrücken können Sie eine Vielzahl von Regeln für ein Feld durchsetzen. Häufig verwendete Beispiele sind die Sicherstellung, dass das Feld nicht leer oder null ist oder einem bestimmten Muster folgt. Durch Annotationen in der Kundenklasse können Sie den jakarta-Validierungsparametern für das Feld bereitstellen.

:::info
Mehr Details zur Einrichtung der Validierung finden Sie [hier](../../data-binding/validation/jakarta-validation.md#installation).
:::

```java
  @NotEmpty(message = "Name darf nicht leer sein")
  @Pattern(regexp = "[a-zA-Z]*", message = "Ungültige Zeichen")
  private String firstName = "";
```

Die Methode `onValidate` wird dann hinzugefügt, um den Zustand der `Submit`-Schaltfläche basierend auf der Gültigkeit der Formularfelder zu steuern. Dies stellt sicher, dass nur gültige Daten eingereicht werden können.

```java title="FormView.java"
context.onValidate(e -> submit.setEnabled(e.isValid()));
```

`e.isValid()` gibt true zurück, wenn alle Felder gültig sind, und false, wenn nicht. Das bedeutet, dass die `Submit`-Schaltfläche aktiviert ist, solange alle Felder gültig sind. Andernfalls bleibt sie deaktiviert, um die Einreichung zu verhindern, bis Korrekturen vorgenommen werden.

### Hinzufügen und Bearbeiten von Einträgen mit Validierung {#adding-and-editing-entries-with-validation}

Die Methode `submitCustomer()` validiert jetzt die Daten mithilfe des `BindingContext`, bevor sie Hinzufüge- oder Bearbeitungsoperationen durchführt. Dieser Ansatz beseitigt die Notwendigkeit für manuelle Validierungsüberprüfungen und nutzt die integrierten Mechanismen des Kontexts, um sicherzustellen, dass nur gültige Daten verarbeitet werden.

- **Hinzufügen-Modus**: Wenn keine `id` bereitgestellt wird, befindet sich das Formular im Hinzufügen-Modus. Die validierten Daten werden im `Customer`-Modell gespeichert und über `Service.getCurrent().addCustomer(customer)` in das Repository eingefügt.
- **Bearbeiten-Modus**: Wenn eine `id` vorhanden ist, ruft die Methode die entsprechenden Kundendaten ab, aktualisiert diese mit validierten Eingaben und speichert die Änderungen im Repository.

Der Aufruf von `context.write(customer)` gibt eine Instanz von `ValidationResult` zurück. Diese Klasse zeigt an, ob die Validierung erfolgreich war oder nicht, und speichert alle Nachrichten, die mit diesem Ergebnis verbunden sind.

Dieser Code stellt sicher, dass alle Änderungen validiert und automatisch auf das Modell angewendet werden, bevor ein neuer `Customer` hinzugefügt oder ein vorhandener bearbeitet wird.

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

Durch den Abschluss dieses Schrittes unterstützt die App nun Datenbindung und Validierung, wodurch sichergestellt wird, dass die Formularfelder mit dem Modell synchronisiert sind und den vordefinierten Regeln entsprechen.
