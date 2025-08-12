---
sidebar_position: 2
title: Validators
_i18n_hash: 3d41925977054029c22c2110455dd419
---
Validator überprüfen Daten innerhalb Ihrer UI-Komponenten gegen definierte Einschränkungen, bevor diese Daten im Datenmodell gespeichert werden. Sie können Validatoren anwenden, um sicherzustellen, dass Daten bestimmten Kriterien entsprechen, wie z.B. in einem bestimmten Bereich zu liegen, ein Muster zu erfüllen oder nicht leer zu sein.

Validierungen werden pro Bindung konfiguriert, sodass spezifische Regeln für jeden Datensatz einzeln gelten. Dieses Setup stellt sicher, dass jedes Datenelement entsprechend seinen eigenen Anforderungen validiert wird.

## Hinzufügen von Validatoren {#adding-validators}

Fügen Sie einer Bindung Validatoren mithilfe der `useValidator`-Methode auf dem `BindingBuilder` hinzu.

```java
context.bind(nameTextField, "name")
    .useValidator(value -> !value.isEmpty(), "Name darf nicht leer sein")
    .useValidator(value -> value.length() >= 3, "Name muss mindestens 3 Zeichen lang sein")
    .add();
```

Im obigen Beispiel überprüfen zwei Validatoren, dass der Name nicht leer ist und dass er mindestens drei Zeichen enthält.

:::tip Verarbeitung von Validatoren
Es gibt keine Begrenzung für die Anzahl der Validatoren, die Sie pro Bindung hinzufügen können. Die Bindung wendet die Validatoren in der Reihenfolge der Einfügung an und stoppt bei der ersten Verletzung.
:::

## Implementierung von Validatoren {#implementing-validators}

Sie können benutzerdefinierte wiederverwendbare Validatoren erstellen, indem Sie das `Validator<T>`-Interface implementieren, wobei `T` der Typ der Daten ist, die Sie validieren möchten. Dieses Setup umfasst die Definition der Validierungsmethode, die die Daten überprüft und ein `ValidationResult` zurückgibt.

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

## Verwendung von Validatoren in Bindungen {#using-validators-in-bindings}

Sobald Sie einen Validator definiert haben, können Sie ihn problemlos auf alle relevanten Bindungen innerhalb Ihrer App anwenden. Dies ist besonders nützlich für Komponenten, die gängige Validierungsregeln in verschiedenen Teilen Ihrer App erfordern, wie z.B. Benutzer-E-Mail-Adressen oder Passwortstärken.

Um den `EmailValidator` auf eine Bindung anzuwenden:

```java
BindingContext<User> context = new BindingContext<>(User.class);
context.bind(ageField, "age")
    .useValidator(new EmailValidator())
    .add();
```

## Überschreiben von Validatornachrichten {#overriding-validator-messages}

Sie können die Fehlermeldungen von Validatoren beim Binden an eine bestimmte UI-Komponente anpassen. Dies ermöglicht es Ihnen, detailliertere oder kontextuell relevantere Informationen bereitzustellen, wenn die Validierung fehlschlägt. Benutzerdefinierte Nachrichten sind besonders nützlich, wenn derselbe Validator auf mehrere Komponenten angewendet wird, aber unterschiedliche Benutzeranleitungen je nach Kontext benötigt werden.

So können Sie die Standardnachricht eines wiederverwendbaren Validators in einer Bindung überschreiben:

```java
BindingContext<User> context = new BindingContext<>(User.class);
context.bind(emailField, "email")
    .useValidator(
        Validator.from(new EmailValidator(), "Benutzerdefinierte Nachricht für ungültige E-Mail-Adresse"))
    .add();
```

Im obigen Beispiel wird der `EmailValidator` auf ein E-Mail-Feld mit einer benutzerdefinierten Fehlermeldung angewendet, die speziell für dieses Feld angepasst ist. Dies ermöglicht ein gezielteres und hilfreicheres Benutzererlebnis, wenn die Validierung fehlschlägt.

:::tip Verständnis von `Validator.from`
Die Methode `Validator.from` umschließt einen übergebenen Validator mit einem neuen, sodass Sie eine benutzerdefinierte Fehlermeldung angeben können, falls der Validator keine angepassten Nachrichten unterstützt. Diese Technik ist besonders nützlich, wenn Sie dieselbe Validierungslogik auf mehrere Komponenten anwenden müssen, aber für jede Instanz unterschiedliche, kontextspezifische Fehlermeldungen benötigen.
:::
