---
sidebar_position: 5
title: Automatische Bindung
description: >-
  Binden Sie UI-Felder mit BindingContext.of und den Annotationen UseProperty,
  BindingExclude und UseValidator automatisch an Bean-Eigenschaften.
_i18n_title_hash: 666e71f372d1b611262598ca5cd23499
_i18n_description_hash: 985dfb18ded910f005bf2a2e040a8f9d
_i18n_hash: 55ac69466d077caf09fa5b765b76074a
---
webforJ bietet mehrere Funktionen, die den Konfigurations- und automatischen Bindungsprozess für Entwickler vereinfachen. In diesem Abschnitt wird gezeigt, wie Sie diese Funktionen effektiv einsetzen.

## `BindingContext.of` verwenden {#using-bindingcontextof}

Die Methode `BindingContext.of` bindet UI-Komponenten automatisch an die Eigenschaften einer angegebenen Bean-Klasse. Dadurch wird der Bindungsprozess vereinfacht und der manuelle Konfigurationsaufwand reduziert. Sie ordnet bindbare Komponenten, die als Felder in einem Formular oder einer App deklariert sind, anhand ihrer Namen den Bean-Eigenschaften zu.

```java
public class HeroRegistration extends App {
  // Bindable components
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

  // Setters and getters
}
```

### Annotation `UseProperty` {#useproperty-annotation}

Wenn Sie eine Bean-Eigenschaft an eine UI-Komponente mit einem anderen Namen binden möchten, verwenden Sie die Annotation `UseProperty`.
Diese Annotation ermöglicht eine präzisere Bindung von Bean-Eigenschaften an UI-Komponenten, insbesondere bei [verschachtelten Bean-Eigenschaften](/docs/data-binding/bindings#nested-bean-properties).

```java
public class HeroRegistration extends App {
  // Binds to the name property
  @UseProperty("name")
  TextField nameField = new TextField("Name");

  // Binds to the nested address.street property
  @UseProperty("address.street")
  TextField streetField = new TextField("Street");

  // Binds to the power property
  ComboBox power = new ComboBox("Power");

  // ...
}
```

### Annotation `BindingExclude` {#bindingexclude-annotation}

Verwenden Sie die Annotation `BindingExclude`, um eine Komponente von der automatischen Bindungskonfiguration auszunehmen, wenn Sie sie lieber manuell binden oder ganz ausschließen möchten.

```java
public class HeroRegistration extends App {
  // Bindable components
  @UseProperty("name")
  TextField nameField = new TextField("Text Field");

  @BindingExclude
  ComboBox power = new ComboBox("Power");

  // ...
}
```

### Annotation `UseValidator` {#usevalidator-annotation}

Verwenden Sie die Annotation `UseValidator`, um Validatoren festzulegen, die während der Bindung zusätzliche Validierungsregeln durchsetzen. Die Validatoren werden in der angegebenen Reihenfolge angewendet.

```java
public class UserRegistration extends App {

  @UseValidator(EmailValidator.class)
  TextField email = new TextField("Email Address");
}
```

### Annotation `UseTransformer` {#usetransformer-annotation}

Verwenden Sie die Annotation `UseTransformer`, um eine Transformer-Klasse direkt für ein UI-Feld festzulegen. Der `BindingContext` wendet den angegebenen Transformer automatisch an.

```java
public class UserRegistration extends App {

  @UseProperty("date")
  @UseTransformer(DateTransformer.class)
  DateField dateField = new DateField("Date Field");
}
```

### Annotation `BindingReadOnly` {#bindingreadonly-annotation}

Verwenden Sie `BindingReadOnly`, um eine [Bindung als schreibgeschützt zu kennzeichnen](./bindings/#configuring-readonly-bindings).

```java
public class UserRegistration extends App {

  @BindingReadOnly
  TextField IDField = new TextField("User ID");
}
```

### Annotation `BindingRequired` {#bindingrequired-annotation}

Verwenden Sie `BindingReadOnly`, um eine Bindung als erforderlich zu kennzeichnen. Siehe auch [Erkennung erforderlicher Bindungen](#required-binding-detections).


```java
public class UserRegistration extends App {

  @BindingRequired
  TextField emailField = new TextField("User Email");
}
```

## Daten automatisch schreiben {#writing-data-automatically}

Um Anwendungen reaktionsfähiger und dynamischer zu gestalten, können Sie die Methode `observe` verwenden. Sie sorgt dafür, dass Änderungen an UI-Komponenten sofort an das Datenmodell weitergegeben werden. Das ist besonders nützlich, wenn das Datenmodell und die UI kontinuierlich synchronisiert werden sollen.

Die Methode `observe` registriert für alle Bindungen im Kontext einen `ValueChangeEvent`-Listener, der Änderungen durch den Benutzer überwacht. Gültige Änderungen werden sofort in die gebundenen Eigenschaften des Modells übernommen. Beim ersten Aufruf der Methode werden die Bean-Eigenschaften in den UI-Komponenten abgebildet.

So verwenden Sie `observe`:

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
    // Take action with the bean.
  }
});
```

:::info Aktualisierungsrichtung
Diese automatische Bindung ist unidirektional: Wenn Sie UI-Komponenten aktualisieren, werden die Änderungen im Modell übernommen. Änderungen am Modell werden hingegen nur einmal, beim ersten Aufruf der Methode, in den UI-Komponenten abgebildet.
:::

:::tip Hinweise
Obwohl `observe` die Interaktivität von Anwendungen erhöht, sollte die Methode mit Bedacht eingesetzt werden:

- **Auswirkungen auf die Leistung**: Häufige Aktualisierungen können die Leistung beeinträchtigen, insbesondere bei komplexen Modellen oder langsamen Backend-Diensten.
- **Benutzererfahrung**: Automatische Aktualisierungen sollten die bequeme Dateneingabe durch Benutzer nicht stören.
:::


## Erkennung erforderlicher Bindungen {#required-binding-detections}

Wenn Sie eine Bindung als erforderlich kennzeichnen, wird auch die Komponente als erforderlich markiert, sofern sie diesen Zustand über die Schnittstelle `RequiredAware` unterstützt. Die Bindung selbst erzwingt diesen Zustand nicht, sondern setzt ihn gegebenenfalls für die Komponente.

```java
BindingContext<User> context = new BindingContext<>(User.class, true);
context
  .bind(emailField, "email")
    .required()
    .add()
```

 Bei Verwendung von [Jakarta-Annotationen](./validation/jakarta-validation.md) kann die Bindung den erforderlichen Zustand automatisch anhand folgender Annotationen der Bean-Eigenschaften erkennen:

1. `@NotNull`
2. `@NotEmpty`
3. `@NotBlank`
4. `@Size`*

:::note `@Size` für eine erforderliche Bindung verwenden <DocChip chip='since' label='26.10' />
`@Size` markiert die gebundene Komponente nur dann als erforderlich, wenn ein `min`-Wert festgelegt ist.
In früheren Versionen markiert `@Size` die gebundene Komponente immer als erforderlich.
:::
