---
sidebar_position: 5
title: Automatic Binding
_i18n_hash: e914be874b2c22c5e32f7fce4b5f1885
---
webforJ offre plusieurs fonctionnalités qui simplifient le processus de configuration et de liaison automatique pour les développeurs. Cette section montre comment utiliser ces fonctionnalités de manière efficace.

## Utilisation de `BindingContext.of` {#using-bindingcontextof}

La méthode `BindingContext.of` lie automatiquement les composants de l'interface utilisateur aux propriétés d'une classe bean spécifiée, simplifiant ainsi le processus de liaison et réduisant la configuration manuelle. Elle aligne les composants bindables, déclarés comme des champs dans un formulaire ou une application, avec les propriétés du bean en fonction de leurs noms.

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

### Annotation `UseProperty` {#useproperty-annotation}

Utilisez l'annotation `UseProperty` pour spécifier le nom de la propriété du bean lorsque le nom du champ de l'interface utilisateur ne correspond pas au nom de la propriété du bean.

```java
public class HeroRegistration extends App {
  // Composants bindables
  @UseProperty("name")
  TextField nameField = new TextField("Champ de texte");
  ComboBox power = new ComboBox("Pouvoir");

  // ...
}
```

Dans l'exemple ci-dessus, le nom du champ de l'interface utilisateur est `nameField`, mais la propriété du bean est `name`. Vous pouvez annoter le champ de l'interface utilisateur avec le nom de la propriété du bean pour garantir une liaison appropriée.

### Annotation `BindingExclude` {#bindingexclude-annotation}

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

### Annotation `UseValidator` {#usevalidator-annotation}

Utilisez l'annotation `UseValidator` pour déclarer des validateurs qui appliquent des règles de validation supplémentaires lors de la liaison. Les validateurs s'appliquent dans l'ordre que vous les spécifiez.

```java
public class UserRegistration extends App {

  @UseValidator(EmailValidator.class)
  TextField email = new TextField("Adresse e-mail");
}
```

### Annotation `UseTransformer` {#usetransformer-annotation}

Utilisez l'annotation `UseTransformer` pour déclarer une classe de transformation directement sur un champ de l'interface utilisateur. Le `BindingContext` applique automatiquement le transformateur spécifié.

```java
public class UserRegistration extends App {

  @UseProperty("date")
  @UseTransformer(DateTransformer.class)
  DateField dateField = new DateField("Champ de date");
}
```

### Annotation `BindingReadOnly` {#bindingreadonly-annotation}

Utilisez `BindingReadOnly` pour [marquer une liaison comme en lecture seule](./bindings/#configuring-readonly-bindings).

```java
public class UserRegistration extends App {

  @BindingReadOnly
  TextField IDField = new TextField("ID Utilisateur");
}
```

### Annotation `BindingRequired` {#bindingrequired-annotation}

Utilisez `BindingReadOnly` pour marquer une liaison comme requise. Voir aussi [détections de liaison requises](#required-binding-detections).


```java
public class UserRegistration extends App {

  @BindingRequired
  TextField emailField = new TextField("Email Utilisateur");
}
```

## Écriture de données automatiquement {#writing-data-automatically}

Pour améliorer la réactivité et le dynamisme des applications, vous pouvez utiliser la méthode `observe`. Cette méthode garantit que les modifications apportées aux composants de l'interface utilisateur se propagent immédiatement au modèle de données. Elle est particulièrement utile lorsque vous avez besoin d'une synchronisation continue entre le modèle de données et l'interface utilisateur.

La méthode `observe` enregistre un écouteur `ValueChangeEvent` sur toutes les liaisons dans le contexte pour surveiller les changements effectués par l'utilisateur, puis elle écrit instantanément ces changements dans les propriétés liées du modèle s'ils sont valides. Lorsque vous invoquez cette méthode pour la première fois, elle reflète les propriétés du bean dans les composants de l'interface utilisateur.

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

:::info Direction de mise à jour
Cette liaison automatique est unidirectionnelle ; les mises à jour se reflètent dans le modèle lorsque vous mettez à jour les composants de l'interface utilisateur, mais les modifications dans le modèle ne se reflètent dans les composants de l'interface utilisateur qu'une seule fois, lors de la première invocation de la méthode.
:::

:::tip Considérations
Bien que `observe` augmente l'interactivité des applications, il est important de l'utiliser judicieusement :

- **Impact sur les performances** : Des mises à jour fréquentes peuvent affecter les performances, en particulier avec des modèles complexes ou des services backend lents.
- **Expérience utilisateur** : Les mises à jour automatiques ne devraient pas perturber la capacité de l'utilisateur à saisir des données confortablement.
:::


## Détections de liaisons requises {#required-binding-detections}

Lorsque vous marquez une liaison comme requise, cela marque le composant comme requis, à condition que le composant prenne en charge cet état grâce à l'interface `RequiredAware`. La liaison n'impose pas cet état par elle-même, mais le définit sur le composant lorsque cela est applicable.

```java
BindingContext<User> context = new BindingContext<>(User.class, true);
context
  .bind(emailField, "email")
    .required()
    .add()
```

 Lors de l'utilisation des [annotations Jakarta](./validation/jakarta-validation.md), la liaison peut automatiquement détecter l'état requis en fonction de la présence de l'une des annotations suivantes sur les propriétés du bean :

1. `@NotNull` 
2. `@NotEmpty` 
3. `@NotBlank`
4. `@Size`
