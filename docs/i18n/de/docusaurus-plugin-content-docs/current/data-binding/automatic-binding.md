---
sidebar_position: 5
title: Automatische Bindung
_i18n_hash: e914be874b2c22c5e32f7fce4b5f1885
---
webforJ bietet mehrere Funktionen, die den Konfigurations- und automatischen Bindungsprozess für Entwickler optimieren. In diesem Abschnitt wird gezeigt, wie diese Funktionen effektiv genutzt werden können.

## Verwendung von `BindingContext.of` {#using-bindingcontextof}

Die Methode `BindingContext.of` bindet automatisch UI-Komponenten an die Eigenschaften einer bestimmten Bean-Klasse, was den Bindungsprozess vereinfacht und die manuelle Einrichtung reduziert. Sie gleicht bindbare Komponenten, die als Felder innerhalb eines Formulars oder einer App deklariert sind, mit Bean-Eigenschaften anhand ihrer Namen ab.

```java
public class HeroRegistration extends App {
  // Bindbare Komponenten
  TextField name = new TextField("Textfeld");
  ComboBox power = new ComboBox("Kraft");

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

  // Setters und Getters
}
```

### `UseProperty` Annotation {#useproperty-annotation}

Verwenden Sie die Annotation `UseProperty`, um den Namen der Bean-Eigenschaft anzugeben, wenn der Name des UI-Feldes nicht mit dem Namen der Bean-Eigenschaft übereinstimmt.

```java
public class HeroRegistration extends App {
  // Bindbare Komponenten
  @UseProperty("name")
  TextField nameField = new TextField("Textfeld");
  ComboBox power = new ComboBox("Kraft");

  // ...
}
```

Im obigen Beispiel lautet der Name des UI-Feldes `nameField`, aber die Bean-Eigenschaft ist `name`. Sie können das UI-Feld mit dem Namen der Bean-Eigenschaft annotieren, um eine ordnungsgemäße Bindung sicherzustellen.

### `BindingExclude` Annotation {#bindingexclude-annotation}

Verwenden Sie die Annotation `BindingExclude`, um eine Komponente von automatischen Bindungskonfigurationen auszuschließen, wenn Sie sie manuell binden oder ganz ausschließen möchten.

```java
public class HeroRegistration extends App {
  // Bindbare Komponenten
  @UseProperty("name")
  TextField nameField = new TextField("Textfeld");

  @BindingExclude
  ComboBox power = new ComboBox("Kraft");

  // ...
}
```

### `UseValidator` Annotation {#usevalidator-annotation}

Verwenden Sie die Annotation `UseValidator`, um Validatoren zu deklarieren, die zusätzliche Validierungsregeln während der Bindung durchsetzen. Validatoren werden in der Reihenfolge angewendet, in der Sie sie angeben.

```java
public class UserRegistration extends App {

  @UseValidator(EmailValidator.class)
  TextField email = new TextField("E-Mail-Adresse");
}
```

### `UseTransformer` Annotation {#usetransformer-annotation}

Verwenden Sie die Annotation `UseTransformer`, um eine Transformerkasse direkt auf einem UI-Feld zu deklarieren. Der `BindingContext` wendet automatisch den angegebenen Transformator an.

```java
public class UserRegistration extends App {

  @UseProperty("date")
  @UseTransformer(DateTransformer.class)
  DateField dateField = new DateField("Datumfeld");
}
```

### `BindingReadOnly` Annotation {#bindingreadonly-annotation}

Verwenden Sie die `BindingReadOnly`, um [eine Bindung als schreibgeschützt zu kennzeichnen](./bindings/#configuring-readonly-bindings).

```java
public class UserRegistration extends App {

  @BindingReadOnly
  TextField IDField = new TextField("Benutzer-ID");
}
```

### `BindingRequired` Annotation {#bindingrequired-annotation}

Verwenden Sie `BindingRequired`, um eine Bindung als erforderlich zu kennzeichnen. Siehe auch [erforderliche Bindungserkennungen](#required-binding-detections).

```java
public class UserRegistration extends App {

  @BindingRequired
  TextField emailField = new TextField("Benutzer-E-Mail");
}
```

## Daten automatisch schreiben {#writing-data-automatically}

Um die Reaktionsfähigkeit und Dynamik von Anwendungen zu verbessern, können Sie die Methode `observe` verwenden. Diese Methode stellt sicher, dass Änderungen an UI-Komponenten sofort an das Datenmodell weitergegeben werden. Sie ist besonders nützlich, wenn Sie eine kontinuierliche Synchronisierung zwischen dem Datenmodell und der UI benötigen.

Die Methode `observe` registriert einen `ValueChangeEvent`-Listener für alle Bindungen im Kontext, um Änderungen, die vom Benutzer vorgenommen werden, zu überwachen und schreibt diese Änderungen sofort in die gebundenen Eigenschaften des Modells, sofern sie gültig sind. Wenn Sie diese Methode zum ersten Mal aufrufen, spiegelt sie die Bean-Eigenschaften in den UI-Komponenten wider.

Hier ist ein Beispiel, wie Sie `observe` verwenden können:

```java
Hero bean = new Hero("Superman", "Fliegen");
BindingContext<Hero> context = BindingContext.of(this, Hero.class, true);
context.observe(bean);
context.onValidate(e -> {
  submit.setEnabled(e.isValid());
});

submit.onClick(e -> {
  ValidationResult results = context.validate();
  if (results.isValid()) {
    // Maßnahmen mit der Bean ergreifen.
  }
});
```

:::info Update-Richtung
Diese automatische Bindung ist unidirektional; Änderungen im Modell werden nur dann in den UI-Komponenten reflektiert, wenn Sie die Methode zum ersten Mal aufrufen.
:::

:::tip Überlegungen
Während `observe` die Interaktivität von Anwendungen erhöht, ist es wichtig, sie mit Bedacht zu verwenden:

- **Leistungswirkung**: Häufige Updates können die Leistung beeinträchtigen, insbesondere bei komplexen Modellen oder langsamen Backend-Diensten.
- **Benutzererlebnis**: Automatische Updates sollten die Fähigkeit des Benutzers, Daten bequem einzugeben, nicht stören.
:::

## Erforderliche Bindungserkennungen {#required-binding-detections}

Wenn Sie eine Bindung als erforderlich kennzeichnen, kennzeichnet sie die Komponente als erforderlich, vorausgesetzt, die Komponente unterstützt diesen Zustand über die `RequiredAware`-Schnittstelle. Die Bindung setzt diesen Zustand nicht selbst durch, sondern legt ihn bei entsprechender Anwendung auf der Komponente fest.

```java
BindingContext<User> context = new BindingContext<>(User.class, true);
context
  .bind(emailField, "email")
    .required()
    .add()
```

Beim Verwenden von [Jakarta-Annotationen](./validation/jakarta-validation.md) kann die Bindung automatisch den erforderlichen Zustand erkennen, basierend auf dem Vorhandensein einer der folgenden Annotationen auf Bean-Eigenschaften:

1. `@NotNull` 
2. `@NotEmpty` 
3. `@NotBlank`
4. `@Size`
