---
sidebar_position: 5
title: Automatic Binding
_i18n_hash: 170c308c3b93a933f5fb85c0f0ec4f15
---
webforJ bietet mehrere Funktionen, die den Konfigurations- und automatischen Bindungsprozess für Entwickler optimieren. In diesem Abschnitt wird erläutert, wie diese Funktionen effektiv genutzt werden können.

## Verwendung von `BindingContext.of` {#using-bindingcontextof}

Die Methode `BindingContext.of` bindet automatisch UI-Komponenten an die Eigenschaften einer angegebenen Bean-Klasse, vereinfacht den Bindungsprozess und reduziert den manuellen Aufwand. Sie ordnet bindbare Komponenten, die als Felder innerhalb eines Formulars oder einer Anwendung deklariert sind, entsprechend ihren Namen den Bean-Eigenschaften zu.

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

Im obigen Beispiel heißt das UI-Feld `nameField`, aber die Bean-Eigenschaft ist `name`. Sie können das UI-Feld mit dem Namen der Bean-Eigenschaft annotieren, um eine korrekte Bindung sicherzustellen.

### Annotation `BindingExclude` {#bindingexclude-annotation}

Verwenden Sie die Annotation `BindingExclude`, um eine Komponente von automatischen Bindungskonfigurationen auszuschließen, wenn Sie es vorziehen, sie manuell zu binden oder ganz auszuschließen.

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

Verwenden Sie die Annotation `UseValidator`, um Validatoren zu deklarieren, die zusätzliche Validierungsregeln während der Bindung durchsetzen. Validatoren werden in der Reihenfolge angewendet, in der Sie sie angeben.

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

Verwenden Sie `BindingReadOnly`, um [eine Bindung als schreibgeschützt zu kennzeichnen](./bindings/#configuring-readonly-bindings).

```java
public class UserRegistration extends App {

  @BindingReadOnly
  TextField IDField = new TextField("Benutzer-ID");
}
```

### Annotation `BindingRequired` {#bindingrequired-annotation}

Verwenden Sie `BindingRequired`, um eine Bindung als erforderlich zu kennzeichnen. Siehe auch [Erkennung erforderlicher Bindungen](#required-binding-detections).

```java
public class UserRegistration extends App {

  @BindingRequired
  TextField emailField = new TextField("Benutzer-E-Mail");
}
```

## Daten automatisch schreiben {#writing-data-automatically}

Um die Reaktivität und Dynamik von Anwendungen zu verbessern, können Sie die Methode `observe` verwenden. Diese Methode stellt sicher, dass Änderungen an den UI-Komponenten sofort auf das Datenmodell übertragen werden. Sie ist besonders nützlich, wenn Sie eine kontinuierliche Synchronisierung zwischen dem Datenmodell und der UI benötigen.

Die Methode `observe` registriert einen `ValueChangeEvent`-Listener für alle Bindungen im Kontext, um Änderungen zu überwachen, die vom Benutzer vorgenommen werden. Diese Änderungen werden sofort an die gebundenen Eigenschaften des Modells geschrieben, sofern sie gültig sind. Wenn Sie diese Methode zum ersten Mal aufrufen, spiegelt sie die Bean-Eigenschaften in den UI-Komponenten wider.

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
    // Handlung mit der Bean vornehmen.
  }
});
```

:::info Update-Richtung
Diese automatische Bindung ist unidirektional; Updates werden im Modell reflektiert, wenn Sie UI-Komponenten aktualisieren. Änderungen im Modell spiegeln sich jedoch nur einmal in den UI-Komponenten wider, wenn Sie die Methode zum ersten Mal aufrufen.
:::

:::tip Überlegungen
Während `observe` die Interaktivität von Anwendungen erhöht, ist es wichtig, es mit Bedacht zu verwenden:

- **Leistungsbeeinflussung**: Häufige Updates könnten die Leistung beeinträchtigen, insbesondere bei komplexen Modellen oder langsamen Backend-Diensten.
- **Benutzererfahrung**: Automatische Updates sollten die Fähigkeit des Benutzers, Daten bequem einzugeben, nicht stören.
:::

## Erforderliche Bindungserkennungen {#required-binding-detections}

Wenn Sie eine Bindung als erforderlich kennzeichnen, markiert sie die Komponente als erforderlich, vorausgesetzt, die Komponente unterstützt diesen Zustand über das `RequiredAware`-Interface. Die Bindung erzwingt diesen Zustand nicht von sich aus, sondern setzt ihn auf der Komponente, wenn dies anwendbar ist.

```java
BindingContext<User> context = new BindingContext<>(User.class, true);
context
  .bind(emailField, "email")
    .required()
    .add()
```

Bei der Verwendung von [Jakarta-Annotationen](./validation/jakarta-validation.md) kann die Bindung automatisch den erforderlichen Zustand erkennen, basierend auf dem Vorhandensein einer der folgenden Annotationen auf den Bean-Eigenschaften:

1. `@NotNull` 
2. `@NotEmpty` 
3. `@NotBlank`
4. `@Size`
