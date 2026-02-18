---
sidebar_position: 2
title: Validators
sidebar_class_name: updated-content
_i18n_hash: 4af002debda2abb59282b5c6a1bf01d7
---
Validator überprüfen Daten innerhalb Ihrer UI-Komponenten gegen festgelegte Einschränkungen, bevor diese Daten im Datenmodell gespeichert werden. Sie können Validatoren anwenden, um zu überprüfen, ob Daten bestimmten Kriterien entsprechen, wie zum Beispiel innerhalb eines festgelegten Bereichs zu liegen, ein Muster zu erfüllen oder nicht leer zu sein.

Validierungen werden pro Bindung konfiguriert, sodass spezifische Regeln auf jeden Datenpunkt einzeln angewendet werden. Jedes Stück Daten durchläuft die Validierung gemäß seinen eigenen Anforderungen.

## Hinzufügen von Validatoren {#adding-validators}

Fügen Sie einer Bindung Validatoren mit der Methode `useValidator` auf dem `BindingBuilder` hinzu.

```java
context.bind(nameTextField, "name")
    .useValidator(value -> !value.isEmpty(), "Der Name darf nicht leer sein")
    .useValidator(value -> value.length() >= 3, "Der Name muss mindestens 3 Zeichen lang sein")
    .add();
```

Im obigen Beispiel überprüfen zwei Validatoren, dass der Name nicht leer ist und dass er mindestens drei Zeichen enthält.

:::tip Verarbeitung von Validatoren
Es gibt keine Begrenzung für die Anzahl von Validatoren, die Sie pro Bindung hinzufügen können. Die Bindung wendet die Validatoren in der Reihenfolge der Hinzufügung an und stoppt bei der ersten Verletzung.
:::

## Implementierung von Validatoren {#implementing-validators}

Sie können wiederverwendbare benutzerdefinierte Validatoren erstellen, indem Sie das Interface `Validator<T>` implementieren, wobei `T` der Typ der Daten ist, die Sie validieren möchten. Diese Einrichtung umfasst die Definition der Methode validate, die die Daten überprüft und ein `ValidationResult` zurückgibt.

Hier ist ein Beispiel für einen wiederverwendbaren Validator, der überprüft, ob die E-Mail eines Benutzers gültig ist.

```java
import com.webforj.data.validation.server.ValidationResult;
import com.webforj.data.validation.server.validator.Validator;

public class EmailValidator implements Validator<String> {
  private static final String PATTERN = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";

  @Override
  public ValidationResult validate(String value) {
    if (value.matches(PATTERN)) {
        return ValidationResult.valid();
    } else {
        return ValidationResult.invalid("Ungültige E-Mail-Adresse");
    }
  }
}
```

### Verwendung von Validatoren in Bindungen {#using-validators-in-bindings}

Sobald Sie einen Validator definiert haben, können Sie ihn problemlos auf alle relevanten Bindungen innerhalb Ihrer App anwenden. Dies ist besonders nützlich für Komponenten, die gemeinsame Validierungsregeln in verschiedenen Teilen Ihrer App erfordern, wie z.B. E-Mail-Adressen von Benutzern oder Passwortstärke.

Um den `EmailValidator` auf eine Bindung anzuwenden:

```java
BindingContext<User> context = new BindingContext<>(User.class);
context.bind(ageField, "age")
    .useValidator(new EmailValidator())
    .add();
```

### Überschreiben von Validatornachrichten {#overriding-validator-messages}

Sie können die Fehlermeldungen von Validatoren an dem Punkt anpassen, an dem sie an eine bestimmte UI-Komponente gebunden sind. Dies ermöglicht es Ihnen, detailliertere oder kontextuell relevante Informationen bereitzustellen, wenn die Validierung fehlschlägt. Benutzerdefinierte Nachrichten sind besonders nützlich, wenn derselbe Validator auf mehrere Komponenten angewendet wird, aber unterschiedliche Anleitungen je nach Kontext erfordert.

So überschreiben Sie die Standardnachricht eines wiederverwendbaren Validators in einer Bindung:

```java
BindingContext<User> context = new BindingContext<>(User.class);
context.bind(emailField, "email")
    .useValidator(
        Validator.from(new EmailValidator(), "Benutzerdefinierte Nachricht für ungültige E-Mail-Adresse"))
    .add();
```

Im obigen Beispiel wird der `EmailValidator` auf ein E-Mail-Feld mit einer benutzerdefinierten Fehlermeldung angewendet, die speziell für dieses Feld angepasst ist.

:::tip Verständnis von `Validator.from`
Die Methode `Validator.from` wickelt einen übergebenen Validator in einen neuen, sodass Sie eine benutzerdefinierte Fehlermeldung angeben können, falls der Validator keine benutzerdefinierten Nachrichten unterstützt. Diese Technik ist besonders nützlich, wenn Sie denselben Validierungslogik auf mehrere Komponenten anwenden müssen, jedoch mit unterschiedlichen, kontextspezifischen Fehlermeldungen für jede Instanz.
:::

### Dynamische Validierungsnachrichten <DocChip chip='since' label='25.12' /> {#dynamic-validation-messages}

Standardmäßig sind Validierungsnachrichten statische Zeichenfolgen, die einmal beim Binden festgelegt werden. In Apps, die mehrere Sprachen unterstützen, werden diese statischen Nachrichten nicht aktualisiert, wenn der Benutzer die Gebietsschemas ändert. Um dies zu lösen, akzeptieren sowohl `useValidator` als auch die Fabrikmethoden `Validator` einen `Supplier<String>`, der bei jeder fehlgeschlagenen Validierung aufgerufen wird, sodass die Nachricht dynamisch aufgelöst werden kann.

#### Inline-Validatoren mit dynamischen Nachrichten {#inline-validators-with-dynamic-messages}

Übergeben Sie einen `Supplier<String>` anstelle eines einfachen `String` an `useValidator`:

```java {2,3}
context.bind(nameTextField, "name")
    .useValidator(value -> !value.isEmpty(), () -> t("validation.name.required"))
    .useValidator(value -> value.length() >= 3, () -> t("validation.name.minLength"))
    .add();
```

Jedes Mal, wenn die Validierung durchgeführt wird und das Prädikat fehlschlägt, ruft der Anbieter `t()` auf, der die Nachricht für das aktuelle Gebietsschema auflöst.

#### Fabrikmethoden mit dynamischen Nachrichten {#factory-methods-with-dynamic-messages}

Die Fabrikmethoden `Validator.of` und `Validator.from` akzeptieren ebenfalls Anbieter:

```java {4,10}
// Erstellen Sie einen prädikatbasierten Validator mit einer dynamischen Nachricht
Validator<String> required = Validator.of(
    value -> !value.isEmpty(),
    () -> t("validation.required")
);

// Wickeln Sie einen vorhandenen Validator mit einer dynamischen Überschreibnachricht ein
Validator<String> email = Validator.from(
    new EmailValidator(),
    () -> t("validation.email.invalid")
);
```

#### Gebietsschema-sensible benutzerdefinierte Validatoren {#locale-aware-custom-validators}

Für wiederverwendbare Validatoren, die interne, gebietsschemasensible Nachrichten erzeugen müssen, implementieren Sie das Interface `LocaleAware`. Wenn das Gebietsschema über `BindingContext.setLocale()` geändert wird, propagiert der Kontext automatisch das neue Gebietsschema an alle Validatoren, die dieses Interface implementieren. Der nächste Validierungslauf erzeugt dann Nachrichten im aktualisierten Gebietsschema.
