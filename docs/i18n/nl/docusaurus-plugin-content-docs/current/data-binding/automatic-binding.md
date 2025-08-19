---
sidebar_position: 5
title: Automatic Binding
_i18n_hash: e914be874b2c22c5e32f7fce4b5f1885
---
webforJ biedt verschillende functies die het configuratie- en automatische bindproces voor ontwikkelaars stroomlijnen. Deze sectie toont aan hoe je deze functies effectief kunt gebruiken.

## Gebruik `BindingContext.of` {#using-bindingcontextof}

De `BindingContext.of` methode bindt automatisch UI-componenten aan de eigenschappen van een opgegeven bean-klasse, waardoor het bindproces vereenvoudigd wordt en handmatige configuratie verminderd. Het legt bindbare componenten, die zijn verklaard als velden binnen een formulier of app, in lijn met bean-eigenschappen op basis van hun namen.

```java
public class HeroRegistration extends App {
  // Bindbare componenten
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

  // Setters en getters
}
```

### `UseProperty` annotatie {#useproperty-annotation}

Gebruik de `UseProperty` annotatie om de naam van de bean-eigenschap op te geven wanneer de naam van het UI-veld niet overeenkomt met de naam van de bean-eigenschap.

```java
public class HeroRegistration extends App {
  // Bindbare componenten
  @UseProperty("name")
  TextField nameField = new TextField("Text Field");
  ComboBox power = new ComboBox("Power");

  // ...
}
```

In het bovenstaande voorbeeld is de naam van het UI-veld `nameField`, maar de bean-eigenschap is `name`. Je kunt het UI-veld annoteren met de naam van de bean-eigenschap om een juiste binding te waarborgen.

### `BindingExclude` annotatie {#bindingexclude-annotation}

Gebruik de `BindingExclude` annotatie om een component uit te sluiten van automatische bindconfiguraties wanneer je deze handmatig wilt binden of helemaal wilt uitsluiten.

```java
public class HeroRegistration extends App {
  // Bindbare componenten
  @UseProperty("name")
  TextField nameField = new TextField("Text Field");

  @BindingExclude
  ComboBox power = new ComboBox("Power");

  // ...
}
```

### `UseValidator` annotatie {#usevalidator-annotation}

Gebruik de `UseValidator` annotatie om validators te declareren die extra validatieregels afdwingen tijdens binding. Validators worden toegepast in de volgorde waarin je ze opgeeft.

```java
public class UserRegistration extends App {

  @UseValidator(EmailValidator.class)
  TextField email = new TextField("Email Address");
}
```

### `UseTransformer` annotatie {#usetransformer-annotation}

Gebruik de `UseTransformer` annotatie om een transformerklasse direct op een UI-veld te declareren. De `BindingContext` past automatisch de opgegeven transformer toe.

```java
public class UserRegistration extends App {

  @UseProperty("date")
  @UseTransformer(DateTransformer.class)
  DateField dateField = new DateField("Date Field");
}
```

### `BindingReadOnly` annotatie {#bindingreadonly-annotation}

Gebruik de `BindingReadOnly` om een binding als alleen-lezen te markeren.

```java
public class UserRegistration extends App {

  @BindingReadOnly
  TextField IDField = new TextField("User ID");
}
```

### `BindingRequired` annotatie {#bindingrequired-annotation}

Gebruik de `BindingRequired` om een binding als vereist te markeren. Zie ook [vereiste binddetecties](#required-binding-detections).

```java
public class UserRegistration extends App {

  @BindingRequired
  TextField emailField = new TextField("User Email");
}
```

## Gegevens automatisch schrijven {#writing-data-automatically}

Om de responsiviteit en dynamiek van applicaties te verbeteren, kun je de `observe` methode gebruiken. Deze methode zorgt ervoor dat wijzigingen in UI-componenten onmiddellijk worden doorgegeven aan het datamodel. Het is bijzonder nuttig wanneer je continue synchronisatie tussen het datamodel en de UI nodig hebt.

De `observe` methode registreert een `ValueChangeEvent` listener op alle bindings in de context om wijzigingen van de gebruiker te monitoren, waarna het deze wijzigingen onmiddellijk naar de gebonden eigenschappen van het model schrijft als ze geldig zijn. Wanneer je deze methode voor het eerst aanroept, worden de bean-eigenschappen weerspiegeld in de UI-componenten.

Hier is een voorbeeld van hoe je `observe` gebruikt:

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
    // Neem actie met de bean.
  }
});
```

:::info Update Richting
Deze automatische binding is unidirectioneel; updates worden weerspiegeld in het model wanneer je UI-componenten bijwerkt, maar wijzigingen in het model worden alleen eenmaal in de UI-componenten weerspiegeld, wanneer je de methode voor het eerst aanroept.
:::

:::tip Overwegingen
Hoewel `observe` de interactie van applicaties verhoogt, is het belangrijk om het verstandig te gebruiken:

- **Prestatie-impact**: Frequent updates kunnen de prestaties beïnvloeden, vooral met complexe modellen of trage backend-services.
- **Gebruikerservaring**: Automatische updates mogen de gebruiker niet verstoren bij het comfortabel invoeren van gegevens.
:::

## Vereiste binddetecties {#required-binding-detections}

Wanneer je een binding als vereist markeert, markeert dit de component als vereist, mits de component deze staat ondersteunt via de `RequiredAware` interface. De binding handhaaft deze staat niet zelf, maar stelt het in op de component wanneer van toepassing.

```java
BindingContext<User> context = new BindingContext<>(User.class, true);
context
  .bind(emailField, "email")
    .required()
    .add()
```

Wanneer je gebruikmaakt van [Jakarta-annotaties](./validation/jakarta-validation.md), kan de binding automatisch de vereiste staat detecteren op basis van de aanwezigheid van een van de volgende annotaties op bean-eigenschappen:

1. `@NotNull` 
2. `@NotEmpty` 
3. `@NotBlank`
4. `@Size`
