---
sidebar_position: 5
title: Automatic Binding
description: >-
  Bind UI fields to bean properties automatically with BindingContext.of using
  UseProperty, BindingExclude, and UseValidator annotations.
_i18n_hash: 412c446b42788eae1b7f7e16194afda9
---
webforJ biedt verschillende functies die het configuratie- en automatische bindingproces voor ontwikkelaars stroomlijnen. Deze sectie laat zien hoe je deze functies effectief kunt gebruiken.

## Gebruik `BindingContext.of` {#using-bindingcontextof}

De methode `BindingContext.of` bindt automatisch UI-componenten aan de eigenschappen van een specifieke bean-klasse, waardoor het bindingproces eenvoudiger wordt en de handmatige configuratie vermindert. Het koppelt bindbare componenten, verklaard als velden binnen een formulier of app, aan bean-eigenschappen op basis van hun namen.

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

Wanneer je een bean-eigenschap wilt binden aan een UI-component die een andere naam heeft, gebruik dan de annotatie `UseProperty`. Deze annotatie biedt meer precisie bij het binden van bean-eigenschappen aan UI-componenten, vooral wanneer je te maken hebt met [geneste bean-eigenschappen](/docs/data-binding/bindings#nested-bean-properties).

```java
public class HeroRegistration extends App {
  // Bindt aan de naam-eigenschap
  @UseProperty("name")
  TextField nameField = new TextField("Naam");
  
  // Bindt aan de geneste address.street-eigenschap
  @UseProperty("address.street")
  TextField streetField = new TextField("Straat");

  // Bindt aan de power-eigenschap
  ComboBox power = new ComboBox("Kracht");

  // ...
}
```

### `BindingExclude` annotatie {#bindingexclude-annotation}

Gebruik de annotatie `BindingExclude` om een component uit automatische bindingconfiguraties uit te sluiten wanneer je deze handmatig wilt binden of helemaal wilt uitsluiten.

```java
public class HeroRegistration extends App {
  // Bindbare componenten
  @UseProperty("name")
  TextField nameField = new TextField("Text Field");

  @BindingExclude
  ComboBox power = new ComboBox("Kracht");

  // ...
}
```

### `UseValidator` annotatie {#usevalidator-annotation}

Gebruik de annotatie `UseValidator` om validators te declareren die extra validatieregels afdwingen tijdens het binden. Validators worden toegepast in de volgorde die je opgeeft.

```java
public class UserRegistration extends App {

  @UseValidator(EmailValidator.class)
  TextField email = new TextField("E-mailadres");
}
```

### `UseTransformer` annotatie {#usetransformer-annotation}

Gebruik de annotatie `UseTransformer` om een transformerklasse rechtstreeks op een UI-veld te declareren. De `BindingContext` past automatisch de gespecificeerde transformer toe.

```java
public class UserRegistration extends App {

  @UseProperty("date")
  @UseTransformer(DateTransformer.class)
  DateField dateField = new DateField("Datumveld");
}
```

### `BindingReadOnly` annotatie {#bindingreadonly-annotation}

Gebruik de `BindingReadOnly` om [een binding als alleen-lezen te markeren](./bindings/#configuring-readonly-bindings).

```java
public class UserRegistration extends App {

  @BindingReadOnly
  TextField IDField = new TextField("Gebruikers-ID");
}
```

### `BindingRequired` annotatie {#bindingrequired-annotation}

Gebruik de `BindingRequired` om een binding als verplicht te markeren. Zie ook [detecties van vereiste bindingen](#required-binding-detections).

```java
public class UserRegistration extends App {

  @BindingRequired
  TextField emailField = new TextField("Gebruikers-email");
}
```

## Gegevens automatisch schrijven {#writing-data-automatically}

Om de responsiviteit en dynamiek van applicaties te verbeteren, kun je de `observe`-methode gebruiken. Deze methode zorgt ervoor dat veranderingen in UI-componenten onmiddellijk worden doorgegeven aan het gegevensmodel. Het is bijzonder nuttig wanneer je continue synchronisatie tussen het gegevensmodel en de UI nodig hebt.

De `observe`-methode registreert een `ValueChangeEvent`-listener op alle bindings in de context om veranderingen te monitoren die door de gebruiker zijn aangebracht en schrijft deze veranderingen onmiddellijk naar de gebonden eigenschappen van het model als ze geldig zijn. Wanneer je deze methode voor het eerst oproept, weerspiegelt het de bean-eigenschappen in de UI-componenten.

Hier is een voorbeeld van hoe je `observe` kunt gebruiken:

```java
Hero bean = new Hero("Superman", "Vliegen");
BindingContext<Hero> context = BindingContext.of(this, Hero.class, true);
context.observe(bean);
context.onValidate(e -> {
  submit.setEnabled(e.isValid());
});

submit.onClick(e -> {
  ValidationResult results = context.validate();
  if (results.isValid()) {
    // Handel met de bean.
  }
});
```

:::info Update Richting
Deze automatische binding is unidirectioneel; updates worden weerspiegeld in het model wanneer je UI-componenten bijwerkt, maar veranderingen in het model worden pas één keer in de UI-componenten weerspiegeld, wanneer je de methode voor het eerst oproept.
:::

:::tip Overwegingen
Hoewel `observe` de interactie van applicaties verhoogt, is het belangrijk om het met mate te gebruiken:

- **Prestaties**: Frequent updates kunnen de prestaties beïnvloeden, vooral met complexe modellen of trage backendservices.
- **Gebruikerservaring**: Automatische updates mogen de gebruiker niet verstoren bij het comfortabel invoeren van gegevens.
:::

## Vereiste binding detecties {#required-binding-detections}

Wanneer je een binding als verplicht markeert, markeert het de component als verplicht, op voorwaarde dat de component deze staat ondersteunt via de `RequiredAware`-interface. De binding handhaaft deze staat niet zelf, maar stelt deze in op de component wanneer van toepassing.

```java
BindingContext<User> context = new BindingContext<>(User.class, true);
context
  .bind(emailField, "email")
    .required()
    .add();
```

Wanneer je [Jakarta-annotaties](./validation/jakarta-validation.md) gebruikt, kan de binding automatisch de vereiste staat detecteren op basis van de aanwezigheid van een van de volgende annotaties op bean-eigenschappen:

1. `@NotNull` 
2. `@NotEmpty` 
3. `@NotBlank`
4. `@Size`
