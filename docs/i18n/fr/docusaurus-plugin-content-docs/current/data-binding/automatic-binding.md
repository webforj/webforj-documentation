---
sidebar_position: 5
title: Automatic Binding
description: >-
  Bind UI fields to bean properties automatically with BindingContext.of using
  UseProperty, BindingExclude, and UseValidator annotations.
_i18n_hash: 412c446b42788eae1b7f7e16194afda9
---
webforJ offre plusieurs fonctionnalités qui simplifient le processus de configuration et de liaison automatique pour les développeurs. Cette section démontre comment utiliser ces fonctionnalités de manière efficace.

## Using `BindingContext.of` {#using-bindingcontextof}

La méthode `BindingContext.of` lie automatiquement les composants UI aux propriétés d'une classe bean spécifiée, simplifiant le processus de liaison et réduisant la configuration manuelle. Elle aligne les composants bindables, déclarés comme des champs au sein d'un formulaire ou d'une application, avec les propriétés de bean en fonction de leurs noms.

```java
public class HeroRegistration extends App {
  // Composants bindables
  TextField name = new TextField("Champ de texte");
  ComboBox power = new ComboBox("Pouvoir");

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

  // Setters et getters
}
```

### `UseProperty` annotation {#useproperty-annotation}

Lorsque vous souhaitez lier une propriété de bean à un composant UI portant un nom différent, utilisez l'annotation `UseProperty`. 
Cette annotation offre une plus grande précision lors de la liaison des propriétés de bean avec les composants UI, notamment lorsque vous traitez des [propriétés de bean imbriquées](/docs/data-binding/bindings#nested-bean-properties).

```java
public class HeroRegistration extends App {
  // Lie à la propriété name
  @UseProperty("name")
  TextField nameField = new TextField("Nom");
  
  // Lie à la propriété imbriquée address.street
  @UseProperty("address.street")
  TextField streetField = new TextField("Rue");

  // Lie à la propriété power
  ComboBox power = new ComboBox("Pouvoir");

  // ...
}
```

### `BindingExclude` annotation {#bindingexclude-annotation}

Utilisez l'annotation `BindingExclude` pour exclure un composant des configurations de liaison automatique lorsque vous préférez le lier manuellement ou l'exclure complètement.

```java
public class HeroRegistration extends App {
  // Composants bindables
  @UseProperty("name")
  TextField nameField = new TextField("Champ de texte");

  @BindingExclude
  ComboBox power = new ComboBox("Pouvoir");

  // ...
}
```

### `UseValidator` annotation {#usevalidator-annotation}

Utilisez l'annotation `UseValidator` pour déclarer des validateurs qui appliquent des règles de validation supplémentaires lors de la liaison. Les validateurs s'appliquent dans l'ordre dans lequel vous les spécifiez.

```java
public class UserRegistration extends App {

  @UseValidator(EmailValidator.class)
  TextField email = new TextField("Adresse e-mail");
}
```

### `UseTransformer` annotation {#usetransformer-annotation}

Utilisez l'annotation `UseTransformer` pour déclarer une classe de transformation directement sur un champ UI. Le `BindingContext` applique automatiquement le transformateur spécifié.

```java
public class UserRegistration extends App {

  @UseProperty("date")
  @UseTransformer(DateTransformer.class)
  DateField dateField = new DateField("Champ de date");
}
```

### `BindingReadOnly` annotation {#bindingreadonly-annotation}

Utilisez le `BindingReadOnly` pour [marquer une liaison comme étant en lecture seule](./bindings/#configuring-readonly-bindings).

```java
public class UserRegistration extends App {

  @BindingReadOnly
  TextField IDField = new TextField("ID de l'utilisateur");
}
```

### `BindingRequired` annotation {#bindingrequired-annotation}

Utilisez le `BindingRequired` pour marquer une liaison comme obligatoire. Voir aussi [détections de liaison requises](#required-binding-detections).

```java
public class UserRegistration extends App {

  @BindingRequired
  TextField emailField = new TextField("E-mail de l'utilisateur");
}
```

## Writing data automatically {#writing-data-automatically}

Pour améliorer la réactivité et le dynamisme des applications, vous pouvez utiliser la méthode `observe`. Cette méthode garantit que les modifications apportées aux composants UI se propagent immédiatement au modèle de données. Elle est particulièrement utile lorsque vous avez besoin d'une synchronisation continue entre le modèle de données et l'UI.

La méthode `observe` enregistre un écouteur `ValueChangeEvent` sur toutes les liaisons dans le contexte pour surveiller les modifications effectuées par l'utilisateur, puis écrit instantanément ces modifications dans les propriétés liées du modèle si elles sont valides. Lorsque vous invoquez cette méthode pour la première fois, elle reflète les propriétés de bean dans les composants UI.

Voici un exemple d'utilisation de `observe` :

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
    // Agir avec le bean.
  }
});
```

:::info Update Direction
Cette liaison automatique est unidirectionnelle ; les mises à jour sont reflétées dans le modèle lorsque vous mettez à jour les composants UI, mais les changements dans le modèle ne se reflètent dans les composants UI qu'une fois, lors de la première invocation de la méthode.
:::

:::tip Considerations
Bien que `observe` augmente l'interactivité des applications, il est important de l'utiliser judicieusement :

- **Impact sur les performances** : Des mises à jour fréquentes pourraient affecter les performances, en particulier avec des modèles complexes ou des services backend lents.
- **Expérience utilisateur** : Les mises à jour automatiques ne doivent pas perturber la capacité de l'utilisateur à saisir des données de manière confortable.
:::


## Required binding detections {#required-binding-detections}

Lorsque vous marquez une liaison comme requise, cela marque le composant comme requis, à condition que le composant prenne en charge cet état via l'interface `RequiredAware`. La liaison n'impose pas cet état par elle-même, mais le définit sur le composant lorsque cela est applicable.

```java
BindingContext<User> context = new BindingContext<>(User.class, true);
context
  .bind(emailField, "email")
    .required()
    .add()
```

Lors de l'utilisation des [annotations Jakarta](./validation/jakarta-validation.md), la liaison peut automatiquement détecter l'état requis en fonction de la présence de l'une des annotations suivantes sur les propriétés de bean :

1. `@NotNull` 
2. `@NotEmpty` 
3. `@NotBlank`
4. `@Size`
