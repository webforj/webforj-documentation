---
sidebar_position: 5
title: Automatische binding
description: >-
  Koppel UI-velden automatisch aan bean-eigenschappen met BindingContext.of via
  de annotaties UseProperty, BindingExclude en UseValidator.
_i18n_title_hash: 666e71f372d1b611262598ca5cd23499
_i18n_description_hash: 985dfb18ded910f005bf2a2e040a8f9d
_i18n_hash: 55ac69466d077caf09fa5b765b76074a
---
webforJ biedt verschillende functies die het configuratie- en automatische bindingsproces voor ontwikkelaars vereenvoudigen. In dit gedeelte wordt getoond hoe je deze functies effectief kunt gebruiken.

## `BindingContext.of` gebruiken {#using-bindingcontextof}

De methode `BindingContext.of` bindt UI-componenten automatisch aan de eigenschappen van een opgegeven bean-klasse. Zo wordt het bindingsproces eenvoudiger en is er minder handmatige configuratie nodig. De methode koppelt bindbare componenten die als velden binnen een formulier of app zijn gedeclareerd aan bean-eigenschappen op basis van hun namen.

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

### De annotatie `UseProperty` {#useproperty-annotation}

Als je een bean-eigenschap wilt binden aan een UI-component met een andere naam, gebruik je de annotatie `UseProperty`.
Deze annotatie zorgt voor een nauwkeurigere koppeling van bean-eigenschappen aan UI-componenten, vooral wanneer je werkt met [geneste bean-eigenschappen](/docs/data-binding/bindings#nested-bean-properties).

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

### De annotatie `BindingExclude` {#bindingexclude-annotation}

Gebruik de annotatie `BindingExclude` om een component uit te sluiten van automatische bindingsconfiguraties wanneer je deze liever handmatig bindt of helemaal wilt uitsluiten.

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

### De annotatie `UseValidator` {#usevalidator-annotation}

Gebruik de annotatie `UseValidator` om validators te declareren die tijdens het binden aanvullende validatieregels afdwingen. Validators worden toegepast in de volgorde waarin je ze opgeeft.

```java
public class UserRegistration extends App {

  @UseValidator(EmailValidator.class)
  TextField email = new TextField("Email Address");
}
```

### De annotatie `UseTransformer` {#usetransformer-annotation}

Gebruik de annotatie `UseTransformer` om rechtstreeks op een UI-veld een transformerklasse te declareren. De `BindingContext` past de opgegeven transformer automatisch toe.

```java
public class UserRegistration extends App {

  @UseProperty("date")
  @UseTransformer(DateTransformer.class)
  DateField dateField = new DateField("Date Field");
}
```

### De annotatie `BindingReadOnly` {#bindingreadonly-annotation}

Gebruik `BindingReadOnly om [een binding als alleen-lezen te markeren](./bindings/#configuring-readonly-bindings).

```java
public class UserRegistration extends App {

  @BindingReadOnly
  TextField IDField = new TextField("User ID");
}
```

### De annotatie `BindingRequired` {#bindingrequired-annotation}

Gebruik `BindingReadOnly` om een binding als verplicht te markeren. Zie ook [detectie van verplichte bindings](#required-binding-detections).


```java
public class UserRegistration extends App {

  @BindingRequired
  TextField emailField = new TextField("User Email");
}
```

## Gegevens automatisch schrijven {#writing-data-automatically}

Om applicaties responsiever en dynamischer te maken, kun je de methode `observe` gebruiken. Deze methode zorgt ervoor dat wijzigingen in UI-componenten direct worden doorgegeven aan het gegevensmodel. Dit is vooral handig wanneer je het gegevensmodel en de UI voortdurend met elkaar wilt synchroniseren.

De methode `observe` registreert een `ValueChangeEvent`-listener op alle bindings in de context om wijzigingen van de gebruiker te volgen. Vervolgens schrijft de methode deze wijzigingen direct naar de gebonden eigenschappen van het model, mits ze geldig zijn. Wanneer je deze methode voor het eerst aanroept, worden de bean-eigenschappen weergegeven in de UI-componenten.

Hier volgt een voorbeeld van het gebruik van `observe`:

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

:::info Updaterichting
Deze automatische binding is unidirectioneel: wijzigingen in UI-componenten worden doorgegeven aan het model, maar wijzigingen in het model worden alleen bij de eerste aanroep van de methode weergegeven in de UI-componenten.
:::

:::tip Aandachtspunten
Hoewel `observe` applicaties interactiever maakt, is het belangrijk om deze functie met beleid te gebruiken:

- **Prestatie-impact**: frequente updates kunnen de prestaties beïnvloeden, vooral bij complexe modellen of trage back-endservices.
- **Gebruikerservaring**: automatische updates mogen de gebruiker niet hinderen bij het comfortabel invoeren van gegevens.
:::


## Detectie van verplichte bindings {#required-binding-detections}

Wanneer je een binding als verplicht markeert, wordt de component als verplicht gemarkeerd, mits de component deze status ondersteunt via de interface `RequiredAware`. De binding dwingt deze status niet zelf af, maar stelt deze waar mogelijk in op de component.

```java
BindingContext<User> context = new BindingContext<>(User.class, true);
context
  .bind(emailField, "email")
    .required()
    .add()
```

Bij gebruik van [Jakarta-annotaties](./validation/jakarta-validation.md) kan de binding de verplichte status automatisch detecteren op basis van de aanwezigheid van een van de volgende annotaties op bean-eigenschappen:

1. `@NotNull`
2. `@NotEmpty`
3. `@NotBlank`
4. `@Size`*

:::note `@Size` gebruiken voor een verplichte binding <DocChip chip='since' label='26.10' />
`@Size` markeert de gebonden component alleen als verplicht als er een waarde voor `min` is opgegeven.
In eerdere versies markeert `@Size` de gebonden component altijd als verplicht.
:::
