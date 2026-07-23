---
sidebar_position: 5
title: Automatic Binding
description: >-
  Bind UI fields to bean properties automatically with BindingContext.of using
  UseProperty, BindingExclude, and UseValidator annotations.
_i18n_hash: 60ea231c7622e56330eef34d26d615cc
---
webforJ biedt verschillende functies die het configuratie- en automatische bindingproces voor ontwikkelaars stroomlijnen. Dit gedeelte laat zien hoe je deze functies effectief kunt gebruiken.

## Gebruik `BindingContext.of` {#using-bindingcontextof}

De `BindingContext.of` methode bindt automatisch UI-componenten aan de eigenschappen van een gespecificeerde bean-klasse, wat het bindingproces vereenvoudigt en de handmatige configuratie vermindert. Het stelt bindbare componenten, die als velden binnen een formulier of app zijn gedeclareerd, gelijk aan bean-eigenschappen op basis van hun namen.

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

Wanneer je een bean-eigenschap wilt binden aan een UI-component met een andere naam, gebruik dan de `UseProperty` annotatie. Deze annotatie biedt meer precisie bij het binden van bean-eigenschappen aan UI-componenten, vooral wanneer je te maken hebt met [geneste bean-eigenschappen](/docs/data-binding/bindings#nested-bean-properties).

```java
public class HeroRegistration extends App {
  // Bindt aan de naam-eigenschap
  @UseProperty("name")
  TextField nameField = new TextField("Naam");

  // Bindt aan de geneste address.street eigenschap
  @UseProperty("address.street")
  TextField streetField = new TextField("Straat");

  // Bindt aan de power-eigenschap
  ComboBox power = new ComboBox("Power");

  // ...
}
```

### `BindingExclude` annotatie {#bindingexclude-annotation}

Gebruik de `BindingExclude` annotatie om een component uit automatische bindingconfiguraties uit te sluiten wanneer je het handmatig wilt binden of helemaal wilt uitsluiten.

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

Gebruik de `UseValidator` annotatie om validators te declareren die aanvullende validatieregels afdwingen tijdens het binden. Validators worden toegepast in de volgorde waarin je ze opgeeft.

```java
public class UserRegistration extends App {

  @UseValidator(EmailValidator.class)
  TextField email = new TextField("E-mailadres");
}
```

### `UseTransformer` annotatie {#usetransformer-annotation}

Gebruik de `UseTransformer` annotatie om een transformer-klasse direct op een UI-veld te declareren. De `BindingContext` past de opgegeven transformer automatisch toe.

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

Gebruik de `BindingRequired` om een binding als vereist te markeren. Zie ook [detecties van vereiste bindingen](#required-binding-detections).

```java
public class UserRegistration extends App {

  @BindingRequired
  TextField emailField = new TextField("E-mailadres gebruiker");
}
```

## Gegevens automatisch schrijven {#writing-data-automatically}

Om de responsiviteit en dynamiek van toepassingen te verbeteren, kun je de `observe` methode gebruiken. Deze methode zorgt ervoor dat wijzigingen in UI-componenten onmiddellijk worden doorgegeven aan het datamodel. Het is bijzonder handig wanneer je een continue synchronisatie tussen het datamodel en de UI nodig hebt.

De `observe` methode registreert een `ValueChangeEvent` listener op alle bindings in de context om wijzigingen door de gebruiker te monitoren en schrijft deze wijzigingen onmiddellijk naar de gebonden eigenschappen van het model als ze geldig zijn. Wanneer je deze methode voor het eerst aanroept, weerspiegelt het de bean-eigenschappen in de UI-componenten.

Hier is een voorbeeld van hoe je `observe` kunt gebruiken:

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
    // Actie ondernemen met de bean.
  }
});
```

:::info Update Richting
Deze automatische binding is unidirectioneel; updates worden weerspiegeld in het model wanneer je UI-componenten bijwerkt, maar wijzigingen in het model worden alleen eenmaal in de UI-componenten weerspiegeld, wanneer je de methode voor het eerst aanroept.
:::

:::tip Overwegingen
Hoewel `observe` de interactiviteit van toepassingen verhoogt, is het belangrijk om het verstandig te gebruiken:

- **Prestaties Impact**: Frequente updates kunnen de prestaties beïnvloeden, vooral bij complexe modellen of trage backend-diensten.
- **Gebruikerservaring**: Automatische updates moeten de gebruiker niet storen bij het comfortabel invoeren van gegevens.
:::


## Detecties van vereiste bindingen {#required-binding-detections}

Wanneer je een binding als vereist markeert, markeert het de component als vereist, op voorwaarde dat de component deze staat ondersteunt via de `RequiredAware` interface. De binding handhaaft deze staat niet zelf, maar stelt deze in op de component wanneer van toepassing.

```java
BindingContext<User> context = new BindingContext<>(User.class, true);
context
  .bind(emailField, "email")
    .required()
    .add()
```

Wanneer je [Jakarta annotaties](./validation/jakarta-validation.md) gebruikt, kan de binding automatisch de vereiste staat detecteren op basis van de aanwezigheid van een van de volgende annotaties op bean-eigenschappen:

1. `@NotNull`
2. `@NotEmpty`
3. `@NotBlank`
4. `@Size`
