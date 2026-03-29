---
sidebar_position: 2
title: Validators
sidebar_class_name: updated-content
_i18n_hash: 996b617e97e439660bbe69f15d6355b9
---
Validatoren validieren Daten innerhalb Ihrer UI-Komponenten gegen definierte Einschränkungen, bevor diese Daten dem Datenmodell zugeführt werden. Sie können Validatoren anwenden, um zu überprüfen, ob Daten bestimmten Kriterien entsprechen, wie beispielsweise innerhalb eines festgelegten Bereichs zu sein, einem Muster zu entsprechen oder nicht leer zu sein.

Validierungen werden pro Bindung konfiguriert, sodass spezifische Regeln für jeden Datenpunkt einzeln angewendet werden. Jedes Datenelement unterliegt der Validierung gemäß seinen eigenen Anforderungen.

## Validatoren hinzufügen {#adding-validators}

Fügen Sie einer Bindung Validatoren mit der Methode `useValidator` auf dem `BindingBuilder` hinzu.

```java
context.bind(nameTextField, "name")
  .useValidator(value -> !value.isEmpty(), "Name darf nicht leer sein")
  .useValidator(value -> value.length() >= 3, "Name muss mindestens 3 Zeichen lang sein")
  .add();
```

Im obigen Beispiel überprüfen zwei Validatoren, ob der Name nicht leer ist und ob er mindestens drei Zeichen enthält.

:::tip Verarbeitung von Validatoren
Es gibt keine Begrenzung für die Anzahl der Validatoren, die Sie pro Bindung hinzufügen können. Die Bindung wendet die Validatoren in der Reihenfolge der Einfügung an und stoppt beim ersten Verstoß.
:::

## Validatoren implementieren {#implementing-validators}

Sie können benutzerdefinierte wiederverwendbare Validatoren erstellen, indem Sie das Interface `Validator<T>` implementieren, wobei `T` der Typ der Daten ist, die Sie validieren möchten. Dieses Setup erfordert die Definition der Validierungsmethode, die die Daten überprüft und ein `ValidationResult` zurückgibt.

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

### Validatoren in Bindungen verwenden {#using-validators-in-bindings}

Sobald Sie einen Validator definiert haben, können Sie ihn problemlos auf alle relevanten Bindungen in Ihrer Anwendung anwenden. Dies ist besonders nützlich für Komponenten, die allgemeine Validierungsregeln in verschiedenen Teilen Ihrer Anwendung erfordern, wie z. B. Benutzer-E-Mail-Adressen oder Passwortstärken.

Um den `EmailValidator` auf eine Bindung anzuwenden:

```java
BindingContext<User> context = new BindingContext<>(User.class);
context.bind(ageField, "age")
  .useValidator(new EmailValidator())
  .add();
```

### Fehlermeldungen von Validatoren überschreiben {#overriding-validator-messages}

Sie können die Fehlermeldungen von Validatoren an dem Punkt anpassen, an dem sie an eine spezifische UI-Komponente gebunden sind. Dies ermöglicht es Ihnen, dem Benutzer detailliertere oder kontextbezogene Informationen bereitzustellen, falls die Validierung fehlschlägt. Benutzerdefinierte Nachrichten sind besonders nützlich, wenn derselbe Validator auf mehrere Komponenten angewendet wird, jedoch unterschiedliche Anleitungen basierend auf dem Kontext erfordert, in dem er verwendet wird.

So überschreiben Sie die Standardnachricht eines wiederverwendbaren Validators in einer Bindung:

```java
BindingContext<User> context = new BindingContext<>(User.class);
context.bind(emailField, "email")
  .useValidator(
    Validator.from(new EmailValidator(), "Benutzerdefinierte Nachricht für ungültige E-Mail-Adresse"))
  .add();
```

Im obigen Beispiel wird der `EmailValidator` auf ein E-Mail-Feld mit einer benutzerdefinierten Fehlermeldung angewendet, die speziell für dieses Feld zugeschnitten ist.

:::tip Verständnis von `Validator.from`
Die Methode `Validator.from` umschließt einen übergebenen Validator mit einem neuen, sodass Sie eine benutzerdefinierte Fehlermeldung angeben können, falls der Validator keine angepassten Nachrichten unterstützt. Diese Technik ist besonders nützlich, wenn Sie dieselbe Validierungslogik auf mehrere Komponenten anwenden müssen, jedoch mit unterschiedlichen, kontextspezifischen Fehlermeldungen für jede Instanz.
:::

### Dynamische Validierungsnachrichten <DocChip chip='since' label='25.12' /> {#dynamic-validation-messages}

Standardmäßig sind Validierungsnachrichten statische Zeichenfolgen, die einmal zum Zeitpunkt der Bindung festgelegt werden. In Anwendungen, die mehrere Sprachen unterstützen, werden diese statischen Nachrichten nicht aktualisiert, wenn der Benutzer die Lokalisierung wechselt. Um dies zu lösen, akzeptieren sowohl `useValidator` als auch die Fabrikmethoden `Validator` einen `Supplier<String>`, der jedes Mal aufgerufen wird, wenn die Validierung fehlschlägt, wodurch die Nachricht dynamisch aufgelöst werden kann.

#### Inline-Validatoren mit dynamischen Nachrichten {#inline-validators-with-dynamic-messages}

Übergeben Sie einen `Supplier<String>` anstelle einer einfachen `String` an `useValidator`:

```java {2,3}
context.bind(nameTextField, "name")
  .useValidator(value -> !value.isEmpty(), () -> t("validation.name.required"))
  .useValidator(value -> value.length() >= 3, () -> t("validation.name.minLength"))
  .add();
```

Jedes Mal, wenn die Validierung ausgeführt wird und die Bedingung fehlschlägt, wird das Supplier `t()` aufgerufen, das die Nachricht für die aktuelle Lokalisierung auflöst.

#### Fabrikmethoden mit dynamischen Nachrichten {#factory-methods-with-dynamic-messages}

Die Fabrikmethoden `Validator.of` und `Validator.from` akzeptieren ebenfalls Supplier:

```java {4,10}
// Erstellen Sie einen validierenden Validator mit einer dynamischen Nachricht
Validator<String> required = Validator.of(
  value -> !value.isEmpty(),
  () -> t("validation.required")
);

// Wickeln Sie einen vorhandenen Validator mit einer dynamischen Überschreibungsnachricht ein
Validator<String> email = Validator.from(
  new EmailValidator(),
  () -> t("validation.email.invalid")
);
```

#### Lokalisierungsbewusste benutzerdefinierte Validatoren {#locale-aware-custom-validators}

Für wiederverwendbare Validatoren, die intern lokalisierungsabhängige Nachrichten erzeugen müssen, implementieren Sie das Interface `LocaleAware`. Wenn die Locale über `BindingContext.setLocale()` geändert wird, wird die neue Locale automatisch an alle Validatoren weitergegeben, die dieses Interface implementieren. Die nächste Validierungsdurchführung erzeugt dann Nachrichten in der aktualisierten Sprache.
