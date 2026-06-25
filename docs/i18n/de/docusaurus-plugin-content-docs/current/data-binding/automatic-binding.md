---
sidebar_position: 5
title: Automatic Binding
description: >-
  Bind UI fields to bean properties automatically with BindingContext.of using
  UseProperty, BindingExclude, and UseValidator annotations.
_i18n_hash: 412c446b42788eae1b7f7e16194afda9
---
webforJ bietet mehrere Funktionen, die den Konfigurations- und automatischen Bindungsprozess für Entwickler vereinfachen. Dieser Abschnitt zeigt, wie man diese Funktionen effektiv nutzt.

## Verwendung von `BindingContext.of` {#using-bindingcontextof}

Die Methode `BindingContext.of` bindet automatisch UI-Komponenten an die Eigenschaften einer bestimmten Bean-Klasse, wodurch der Bindungsprozess vereinfacht und die manuelle Einrichtung reduziert wird. Sie ordnet bindbare Komponenten, die als Felder innerhalb eines Formulars oder einer App deklariert sind, den Bean-Eigenschaften basierend auf ihren Namen zu.

```java
public class HeroRegistration extends App {
  // Bindbare Komponenten
  TextField name = new TextField("Text Field");
  ComboBox power = new ComboBox("Power");

  // ...

  @Override
  public void run() throws WebforjException {
    BindingContext<Hero> context = BindingContext.of(this, Hero.class, true);
    // ...
  }
}
```

```java
public class Hero {
  private String name;
  private String power;

  // Setter und Getter
}
```

### `UseProperty` Annotation {#useproperty-annotation}

Wenn Sie eine Bean-Eigenschaft an eine UI-Komponente binden möchten, die einen anderen Namen hat, verwenden Sie die `UseProperty` Annotation. Diese Annotation bietet größere Präzision beim Binden von Bean-Eigenschaften an UI-Komponenten, insbesondere beim Umgang mit [verschachtelten Bean-Eigenschaften](/docs/data-binding/bindings#nested-bean-properties).

```java
public class HeroRegistration extends App {
  // Bindet an die Name-Eigenschaft
  @UseProperty("name")
  TextField nameField = new TextField("Name");
  
  // Bindet an die verschachtelte address.street-Eigenschaft
  @UseProperty("address.street")
  TextField streetField = new TextField("Street");

  // Bindet an die Power-Eigenschaft
  ComboBox power = new ComboBox("Power");

  // ...
}
```

### `BindingExclude` Annotation {#bindingexclude-annotation}

Verwenden Sie die `BindingExclude` Annotation, um eine Komponente von automatischen Bindungseinstellungen auszuschließen, wenn Sie sie manuell binden oder ganz ausschließen möchten.

```java
public class HeroRegistration extends App {
  // Bindbare Komponenten
  @UseProperty("name")
  TextField nameField = new TextField("Text Field");

  @BindingExclude
  ComboBox power = new ComboBox("Power");

  // ...
}
```

### `UseValidator` Annotation {#usevalidator-annotation}

Verwenden Sie die `UseValidator` Annotation, um Validatoren zu deklarieren, die zusätzliche Validierungsregeln während des Bindens durchsetzen. Validatoren werden in der Reihenfolge angewendet, in der Sie sie angeben.

```java
public class UserRegistration extends App {

  @UseValidator(EmailValidator.class)
  TextField email = new TextField("Email Address");
}
```

### `UseTransformer` Annotation {#usetransformer-annotation}

Verwenden Sie die `UseTransformer` Annotation, um eine Transformator-Klasse direkt auf ein UI-Feld zu deklarieren. Der `BindingContext` wendet automatisch den angegebenen Transformator an.

```java
public class UserRegistration extends App {

  @UseProperty("date")
  @UseTransformer(DateTransformer.class)
  DateField dateField = new DateField("Date Field");
}
```

### `BindingReadOnly` Annotation {#bindingreadonly-annotation}

Verwenden Sie die `BindingReadOnly`, um [eine Bindung als schreibgeschützt zu kennzeichnen](./bindings/#configuring-readonly-bindings).

```java
public class UserRegistration extends App {

  @BindingReadOnly
  TextField IDField = new TextField("User ID");
}
```

### `BindingRequired` Annotation {#bindingrequired-annotation}

Verwenden Sie die `BindingRequired`, um eine Bindung als erforderlich zu kennzeichnen. Siehe auch [erforderliche Bindungserkennungen](#required-binding-detections).

```java
public class UserRegistration extends App {

  @BindingRequired
  TextField emailField = new TextField("User Email");
}
```

## Automatisches Schreiben von Daten {#writing-data-automatically}

Um die Reaktionsfähigkeit und Dynamik von Anwendungen zu verbessern, können Sie die Methode `observe` verwenden. Diese Methode stellt sicher, dass Änderungen in UI-Komponenten sofort auf das Datenmodell übertragen werden. Sie ist besonders nützlich, wenn Sie eine kontinuierliche Synchronisation zwischen dem Datenmodell und der UI benötigen.

Die Methode `observe` registriert einen `ValueChangeEvent` Listener für alle Bindungen im Kontext, um Änderungen durch den Benutzer zu überwachen, und schreibt diese Änderungen sofort in die gebundenen Eigenschaften des Modells, wenn sie gültig sind. Wenn Sie diese Methode zum ersten Mal aufrufen, spiegeln sich die Bean-Eigenschaften in den UI-Komponenten wider.

Hier ist ein Beispiel, wie man `observe` verwendet:

```java
Hero bean = new Hero("Superman", "Fly");
BindingContext<Hero> context = BindingContext.of(this, Hero.class, true);
context.observe(bean);
context.onValidate(e -> {
  submit.setEnabled(e.isValid());
});

submit.onClick(e -> {
  ValidationResult results = context.validate();
  if (results.isValid()) {
    // Handeln Sie mit der Bean.
  }
});
```

:::info Update-Richtlinie
Diese automatische Bindung ist unidirektional; Updates spiegeln sich im Modell wider, wenn Sie UI-Komponenten aktualisieren, aber Änderungen im Modell spiegeln sich nur einmal in den UI-Komponenten wider, wenn Sie die Methode zum ersten Mal aufrufen.
:::

:::tip Überlegungen
Obwohl `observe` die Interaktivität von Anwendungen erhöht, ist es wichtig, sie mit Bedacht zu verwenden:

- **Leistungsbeeinträchtigung**: Häufige Updates können die Leistung beeinträchtigen, insbesondere bei komplexen Modellen oder langsamen Backend-Services.
- **Benutzererfahrung**: Automatische Updates sollten die Fähigkeit des Benutzers nicht stören, Daten bequem einzugeben.
:::

## Erforderliche Bindungserkennungen {#required-binding-detections}

Wenn Sie eine Bindung als erforderlich kennzeichnen, wird die Komponente als erforderlich markiert, vorausgesetzt, die Komponente unterstützt diesen Zustand über das `RequiredAware` Interface. Die Bindung erzwingt diesen Zustand nicht von sich aus, sondern setzt ihn bei der Komponente, wenn dies zutrifft.

```java
BindingContext<User> context = new BindingContext<>(User.class, true);
context
  .bind(emailField, "email")
    .required()
    .add()
```

Bei der Nutzung von [Jakarta-Anmerkungen](./validation/jakarta-validation.md) kann die Bindung den erforderlichen Zustand automatisch erkennen, basierend auf dem Vorhandensein von einer der folgenden Anmerkungen auf Bean-Eigenschaften:

1. `@NotNull` 
2. `@NotEmpty` 
3. `@NotBlank`
4. `@Size`
