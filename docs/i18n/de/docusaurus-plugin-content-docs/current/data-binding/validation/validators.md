---
sidebar_position: 2
title: Validators
_i18n_hash: 98f40d70b15464d8c7ee48710b07d8fc
---
Validator überprüfen Daten in Ihren UI-Komponenten gegen definierte Einschränkungen, bevor diese Daten dem Datenmodell übertragen werden. Sie können Validatoren anwenden, um sicherzustellen, dass die Daten bestimmten Kriterien entsprechen, wie z. B. innerhalb eines angegebenen Bereichs, einem Muster zu entsprechen oder nicht leer zu sein.

Validierungen werden pro Bindung konfiguriert, sodass spezifische Regeln für jeden datapunkt individuell gelten. Diese Einrichtung sorgt dafür, dass jedes Datenelement die Validierung gemäß seinen eigenen Anforderungen durchläuft.

## Hinzufügen von Validatoren {#adding-validators}

Fügen Sie Validatoren zu einer Bindung hinzu, indem Sie die Methode `useValidator` auf dem `BindingBuilder` verwenden.

```java
context.bind(nameTextField, "name")
    .useValidator(value -> !value.isEmpty(), "Name darf nicht leer sein")
    .useValidator(value -> value.length() >= 3, "Name muss mindestens 3 Zeichen lang sein")
    .add();
```

Im obigen Beispiel überprüfen zwei Validatoren, dass der Name nicht leer ist und dass er mindestens drei Zeichen enthält.

:::tip Verarbeitung von Validatoren
Es gibt keine Grenze für die Anzahl der Validatoren, die Sie pro Bindung hinzufügen können. Die Bindung wendet die Validatoren in der Reihenfolge der Einfügung an und stoppt bei der ersten Verletzung.
:::

## Implementierung von Validatoren {#implementing-validators}

Sie können benutzerdefinierte wiederverwendbare Validatoren erstellen, indem Sie das Interface `Validator<T>` implementieren, wobei `T` der Typ der zu validierenden Daten ist. Diese Einrichtung beinhaltet die Definition der Methode validate, die die Daten überprüft und ein `ValidationResult` zurückgibt.

Hier ist ein Beispiel für einen wiederverwendbaren Validator, der überprüft, ob die E-Mail eines Nutzers gültig ist.

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

Sobald Sie einen Validator definiert haben, können Sie ihn einfach auf beliebige relevante Bindungen in Ihrer App anwenden. Dies ist besonders nützlich für Komponenten, die gemeinsame Validierungsregeln in verschiedenen Teilen Ihrer App erfordern, wie z. B. E-Mail-Adressen von Benutzern oder Passwortstärke.

Um den `EmailValidator` auf eine Bindung anzuwenden:

```java
BindingContext<User> context = new BindingContext<>(User.class);
context.bind(ageField, "age")
    .useValidator(new EmailValidator())
    .add();
```

## Überschreibung von Validatornachrichten {#overriding-validator-messages}

Sie können die Fehlermeldungen von Validatoren an dem Punkt anpassen, an dem sie an eine spezifische UI-Komponente gebunden werden. Dies ermöglicht es Ihnen, detailliertere oder kontextbezogene Informationen an den Benutzer bereitzustellen, falls die Validierung fehlschlägt. Benutzerdefinierte Nachrichten sind besonders nützlich, wenn derselbe Validator auf mehrere Komponenten angewendet wird, jedoch unterschiedliche Benutzeranleitungen basierend auf dem Kontext, in dem er verwendet wird, benötigt.

So können Sie die Standardnachricht eines wiederverwendbaren Validators in einer Bindung überschreiben:

```java
BindingContext<User> context = new BindingContext<>(User.class);
context.bind(emailField, "email")
    .useValidator(
        Validator.from(new EmailValidator(), "Benutzerdefinierte Nachricht für ungültige E-Mail-Adresse"))
    .add();
```

Im obigen Beispiel wendet der Code den `EmailValidator` auf ein E-Mail-Feld mit einer benutzerdefinierten Fehlermeldung an, die speziell für dieses Feld zugeschnitten ist. Dies ermöglicht ein gezielteres und hilfreicheres Benutzererlebnis, falls die Validierung fehlschlägt.

:::tip Verstehen von `Validator.from`
Die Methode `Validator.from` umhüllt einen übergebenen Validator mit einem neuen, sodass Sie eine benutzerdefinierte Fehlermeldung angeben können, falls der Validator keine benutzerdefinierten Nachrichten unterstützt. Diese Technik ist besonders nützlich, wenn Sie dieselbe Validierungslogik in mehreren Komponenten anwenden müssen, jedoch mit unterschiedlichen, kontextspezifischen Fehlermeldungen für jede Instanz.
:::
