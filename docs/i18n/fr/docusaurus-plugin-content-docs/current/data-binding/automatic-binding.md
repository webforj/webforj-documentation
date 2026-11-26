---
sidebar_position: 5
title: Liaison automatique
description: >-
  Liez automatiquement les champs de l’interface utilisateur aux propriétés d’un
  bean avec BindingContext.of et les annotations UseProperty, BindingExclude et
  UseValidator.
_i18n_title_hash: 666e71f372d1b611262598ca5cd23499
_i18n_description_hash: 985dfb18ded910f005bf2a2e040a8f9d
_i18n_hash: 55ac69466d077caf09fa5b765b76074a
---
webforJ offre plusieurs fonctionnalités qui simplifient la configuration et le processus de liaison automatique pour les développeurs. Cette section montre comment utiliser efficacement ces fonctionnalités.

## Utiliser `BindingContext.of` {#using-bindingcontextof}

La méthode `BindingContext.of` lie automatiquement les composants d’interface utilisateur aux propriétés d’une classe bean spécifiée, ce qui simplifie le processus de liaison et réduit la configuration manuelle. Elle associe les composants liables, déclarés comme champs dans un formulaire ou une application, aux propriétés du bean en fonction de leurs noms.

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

Pour lier une propriété de bean à un composant d’interface utilisateur portant un nom différent, utilisez l’annotation `UseProperty`.
Cette annotation permet de lier avec davantage de précision les propriétés du bean aux composants d’interface utilisateur, en particulier lorsque vous travaillez avec des [propriétés de bean imbriquées](/docs/data-binding/bindings#nested-bean-properties).

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

Utilisez l’annotation `BindingExclude` pour exclure un composant de la configuration de liaison automatique si vous préférez le lier manuellement ou l’exclure complètement.

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

Utilisez l’annotation `UseValidator` pour déclarer des validateurs qui appliquent des règles de validation supplémentaires lors de la liaison. Les validateurs sont appliqués dans l’ordre où vous les spécifiez.

```java
public class UserRegistration extends App {

  @UseValidator(EmailValidator.class)
  TextField email = new TextField("Email Address");
}
```

### Annotation `UseTransformer` {#usetransformer-annotation}

Utilisez l’annotation `UseTransformer` pour déclarer une classe de transformation directement sur un champ d’interface utilisateur. `BindingContext` applique automatiquement le transformateur spécifié.

```java
public class UserRegistration extends App {

  @UseProperty("date")
  @UseTransformer(DateTransformer.class)
  DateField dateField = new DateField("Date Field");
}
```

### Annotation `BindingReadOnly` {#bindingreadonly-annotation}

Utilisez `BindingReadOnly` pour [marquer une liaison comme étant en lecture seule](./bindings/#configuring-readonly-bindings).

```java
public class UserRegistration extends App {

  @BindingReadOnly
  TextField IDField = new TextField("User ID");
}
```

### Annotation `BindingRequired` {#bindingrequired-annotation}

Utilisez `BindingReadOnly` pour marquer une liaison comme étant obligatoire. Consultez également la section sur la [détection des liaisons obligatoires](#required-binding-detections).


```java
public class UserRegistration extends App {

  @BindingRequired
  TextField emailField = new TextField("User Email");
}
```

## Écriture automatique des données {#writing-data-automatically}

Pour améliorer la réactivité et le dynamisme des applications, vous pouvez utiliser la méthode `observe`. Cette méthode garantit que les modifications apportées aux composants d’interface utilisateur sont immédiatement répercutées dans le modèle de données. Elle est particulièrement utile lorsque vous avez besoin d’une synchronisation continue entre le modèle de données et l’interface utilisateur.

La méthode `observe` enregistre un écouteur `ValueChangeEvent` sur toutes les liaisons du contexte afin de surveiller les modifications apportées par l’utilisateur, puis écrit immédiatement ces modifications dans les propriétés liées du modèle si elles sont valides. Lors du premier appel à cette méthode, les propriétés du bean sont répercutées dans les composants d’interface utilisateur.

Voici un exemple d’utilisation de `observe` :

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

:::info Sens de mise à jour
Cette liaison automatique est unidirectionnelle : les mises à jour sont répercutées dans le modèle lorsque vous modifiez les composants d’interface utilisateur, mais les modifications du modèle ne sont répercutées dans les composants d’interface utilisateur qu’une seule fois, lors du premier appel à la méthode.
:::

:::tip Points à prendre en compte
Bien que `observe` améliore l’interactivité des applications, il est important de l’utiliser avec discernement :

- **Impact sur les performances** : des mises à jour fréquentes peuvent affecter les performances, en particulier avec des modèles complexes ou des services backend lents.
- **Expérience utilisateur** : les mises à jour automatiques ne doivent pas entraver la saisie confortable des données par l’utilisateur.
:::


## Détection des liaisons obligatoires {#required-binding-detections}

Lorsque vous marquez une liaison comme obligatoire, le composant est également marqué comme obligatoire, à condition qu’il prenne en charge cet état via l’interface `RequiredAware`. La liaison n’impose pas cet état à elle seule, mais le définit sur le composant lorsque cela est possible.

```java
BindingContext<User> context = new BindingContext<>(User.class, true);
context
  .bind(emailField, "email")
    .required()
    .add()
```

 Lors de l’utilisation des [annotations Jakarta](./validation/jakarta-validation.md), la liaison peut détecter automatiquement l’état obligatoire si l’une des annotations suivantes est présente sur les propriétés du bean :

1. `@NotNull`
2. `@NotEmpty`
3. `@NotBlank`
4. `@Size`*

:::note Utiliser `@Size` pour une liaison obligatoire <DocChip chip='since' label='26.10' />
`@Size` marque le composant lié comme obligatoire uniquement si une valeur `min` est définie.
Dans les versions antérieures, `@Size` marque toujours le composant lié comme obligatoire.
:::
