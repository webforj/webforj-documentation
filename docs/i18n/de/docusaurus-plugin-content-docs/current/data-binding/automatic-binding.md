---
sidebar_position: 5
title: Automatic Binding
description: >-
  Bind UI fields to bean properties automatically with BindingContext.of using
  UseProperty, BindingExclude, and UseValidator annotations.
_i18n_hash: 60ea231c7622e56330eef34d26d615cc
---
webforJ bietet verschiedene Funktionen, die den Konfigurations- und automatischen Bindungsprozess für Entwickler vereinfachen. Dieser Abschnitt zeigt, wie man diese Funktionen effektiv nutzt.

## Verwendung von `BindingContext.of` {#using-bindingcontextof}

Die Methode `BindingContext.of` bindet automatisch UI-Komponenten an die Eigenschaften einer bestimmten Bean-Klasse, was den Bindungsprozess vereinfacht und die manuelle Einrichtung reduziert. Sie bringt bindbare Komponenten, die als Felder innerhalb eines Formulars oder einer App deklariert sind, basierend auf ihren Namen mit den Bean-Eigenschaften in Einklang.

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

  // Setter und Getter
}
```

### Annotation `UseProperty` {#useproperty-annotation}

Wenn Sie eine Bean-Eigenschaft an eine UI-Komponente mit einem anderen Namen binden möchten, verwenden Sie die Annotation `UseProperty`. Diese Annotation bietet mehr Präzision beim Binden von Bean-Eigenschaften an UI-Komponenten, insbesondere wenn Sie mit [verschachtelten Bean-Eigenschaften](/docs/data-binding/bindings#nested-bean-properties) arbeiten.

```java
public class HeroRegistration extends App {
  // Bindet an die name-Eigenschaft
  @UseProperty("name")
  TextField nameField = new TextField("Name");

  // Bindet an die verschachtelte address.street-Eigenschaft
  @UseProperty("address.street")
  TextField streetField = new TextField("Straße");

  // Bindet an die power-Eigenschaft
  ComboBox power = new ComboBox("Kraft");

  // ...
}
```

### Annotation `BindingExclude` {#bindingexclude-annotation}

Verwenden Sie die Annotation `BindingExclude`, um eine Komponente aus automatischen Bindungskonfigurationen auszuschließen, wenn Sie sie manuell binden oder ganz ausschließen möchten.

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

### Annotation `UseValidator` {#usevalidator-annotation}

Verwenden Sie die Annotation `UseValidator`, um Validatoren zu deklarieren, die zusätzliche Validierungsregeln während der Bindung durchsetzen. Validatoren gelten in der Reihenfolge, in der Sie sie angeben.

```java
public class UserRegistration extends App {

  @UseValidator(EmailValidator.class)
  TextField email = new TextField("E-Mail-Adresse");
}
```

### Annotation `UseTransformer` {#usetransformer-annotation}

Verwenden Sie die Annotation `UseTransformer`, um eine Transformator-Klasse direkt auf ein UI-Feld zu deklarieren. Der `BindingContext` wendet automatisch den angegebenen Transformator an.

```java
public class UserRegistration extends App {

  @UseProperty("date")
  @UseTransformer(DateTransformer.class)
  DateField dateField = new DateField("Datumsfeld");
}
```

### Annotation `BindingReadOnly` {#bindingreadonly-annotation}

Verwenden Sie die Annotation `BindingReadOnly`, um [eine Bindung als schreibgeschützt zu markieren](./bindings/#configuring-readonly-bindings).

```java
public class UserRegistration extends App {

  @BindingReadOnly
  TextField IDField = new TextField("Benutzer-ID");
}
```

### Annotation `BindingRequired` {#bindingrequired-annotation}

Verwenden Sie die Annotation `BindingRequired`, um eine Bindung als erforderlich zu kennzeichnen. Siehe auch [erforderliche Bindungsüberprüfungen](#required-binding-detections).

```java
public class UserRegistration extends App {

  @BindingRequired
  TextField emailField = new TextField("E-Mail des Benutzers");
}
```

## Automatisches Schreiben von Daten {#writing-data-automatically}

Um die Reaktionsfähigkeit und Dynamik von Anwendungen zu verbessern, können Sie die Methode `observe` verwenden. Diese Methode stellt sicher, dass Änderungen an UI-Komponenten sofort auf das Datenmodell übertragen werden. Sie ist besonders nützlich, wenn Sie eine kontinuierliche Synchronisierung zwischen dem Datenmodell und der UI benötigen.

Die Methode `observe` registriert einen Listener für `ValueChangeEvent` auf allen Bindungen im Kontext, um Änderungen, die vom Benutzer vorgenommen wurden, zu überwachen und schreibt diese Änderungen sofort in die gebundenen Eigenschaften des Modells, sofern sie gültig sind. Wenn Sie diese Methode erstmals aufrufen, werden die Bean-Eigenschaften in den UI-Komponenten angezeigt.

Hier ist ein Beispiel dafür, wie Sie `observe` verwenden:

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
Diese automatische Bindung ist unidirektional; Aktualisierungen werden im Modell widergespiegelt, wenn Sie UI-Komponenten aktualisieren, aber Änderungen im Modell spiegeln sich nur einmal in den UI-Komponenten wider, wenn Sie die Methode erstmals aufrufen.
:::

:::tip Überlegungen
Während `observe` die Interaktivität von Anwendungen erhöht, ist es wichtig, es mit Bedacht zu verwenden:

- **Leistungsimpact**: Häufige Updates könnten die Leistung beeinträchtigen, insbesondere bei komplexen Modellen oder langsamen Backend-Services.
- **Benutzererfahrung**: Automatische Updates sollten die Fähigkeit des Benutzers, Daten komfortabel einzugeben, nicht stören.
:::


## Erforderliche Bindungsüberprüfungen {#required-binding-detections}

Wenn Sie eine Bindung als erforderlich markieren, wird die Komponente als erforderlich markiert, vorausgesetzt, die Komponente unterstützt diesen Zustand über das `RequiredAware`-Interface. Die Bindung erzwingt diesen Zustand nicht von sich aus, sondern setzt ihn bei der Komponente, wenn dies zutrifft.

```java
BindingContext<User> context = new BindingContext<>(User.class, true);
context
  .bind(emailField, "email")
    .required()
    .add();
```

Bei der Verwendung von [Jakarta-Annotationen](./validation/jakarta-validation.md) kann die Bindung den erforderlichen Zustand automatisch erkennen, basierend auf dem Vorhandensein einer der folgenden Annotationen auf Bean-Eigenschaften:

1. `@NotNull`
2. `@NotEmpty`
3. `@NotBlank`
4. `@Size`
