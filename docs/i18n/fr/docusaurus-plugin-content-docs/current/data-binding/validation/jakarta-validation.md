---
sidebar_position: 6
title: Jakarta Validation
sidebar_class_name: updated-content
description: >-
  Apply Jakarta Bean Validation annotations to bean properties and activate
  JakartaValidator on a BindingContext with locale-aware messages.
_i18n_hash: e5b90cd31ee5ca5eab453a1c087967da
---
[Java Bean Validation](https://beanvalidation.org/) est largement reconnu comme la norme pour l'intégration de la logique de validation dans les applications Java. Il utilise une approche uniforme de la validation en permettant aux développeurs d'annoter les propriétés du modèle de domaine avec des contraintes de validation déclaratives. Ces contraintes sont appliquées à l'exécution, avec des options pour des règles à la fois intégrées et définies par l'utilisateur.

webforJ s'intègre à Bean Validation via l'adaptateur `JakartaValidator`, offrant un support complet dès le départ.

## Installation {#installation}

Il est nécessaire d'inclure une implémentation compatible, telle que [Hibernate Validator](https://hibernate.org/validator/), dans votre classpath. Si votre environnement ne propose pas cette implémentation par défaut, vous pouvez l'ajouter manuellement en utilisant les dépendances Maven suivantes :

```xml
<dependency>
  <groupId>org.hibernate.validator</groupId>
  <artifactId>hibernate-validator</artifactId>
  <version>8.0.1.Final</version>
</dependency>
<dependency>
  <groupId>org.glassfish.expressly</groupId>
  <artifactId>expressly</artifactId>
  <version>5.0.0</version>
</dependency>
```

## Le `JakartaValidator` {#the-jakartavalidator}

La classe `JakartaValidator` sert d’adaptateur, reliant le contexte de liaison webforJ avec Jakarta Validation. Cette intégration permet l'utilisation de règles de validation complexes directement via des annotations dans la classe bean.

### Activation de `JakartaValidator` {#activating-jakartavalidator}

Pour activer le `JakartaValidator` dans tout le contexte, vous utilisez généralement le paramètre `useJakartaValidator` lors de la construction du `BindingContext`.

```java
BindingContext<User> context = new BindingContext<>(User.class, true);
```

### Définir des contraintes pour les propriétés de bean {#defining-constraints-for-bean-properties}

Les contraintes basées sur des annotations sont directement appliquées au sein de la classe bean pour spécifier les conditions de validation, comme illustré dans l'exemple ci-dessous :

```java
public class Hero {
  @NotEmpty(message = "Le nom ne peut pas être vide")
  @Length(min = 3, max = 20)
  private String name;

  @NotEmpty(message = "Puissance non spécifiée")
  @Pattern(regexp = "Fly|Invisible|LaserVision|Speed|Teleportation", message = "Puissance invalide")
  private String power;

  // getters et setters
}
```

De telles contraintes sont aussi efficaces que celles définies par programmation lors de l'initialisation de la liaison et produisent des résultats de validation cohérents.

:::warning
Actuellement, le `JakartaValidator` ne reconnaît que les contraintes directement affectées aux propriétés et ignore toute validation non directement associée aux propriétés.
:::

### Validation des beans imbriqués <DocChip chip='since' label='26.01' /> {#validating-nested-beans}

Déclarez des contraintes directement sur les propres champs du bean imbriqué. Lorsque vous lier l'un de ces champs via un [chemin de propriété pointillé](/docs/data-binding/bindings#nested-bean-properties), la contrainte sur cette propriété s'applique à la liaison de la même manière qu'elle le fait pour une propriété de niveau supérieur.

```java
public class Address {
  @NotBlank(message = "La rue est requise")
  @Size(max = 80, message = "La rue est trop longue")
  private String street;

  // getters et setters
}
```

```java {6-7}
public class Hero {
  @NotEmpty(message = "Le nom ne peut pas être vide")
  @Length(min = 3, max = 20)
  private String name;

  // Un bean imbriqué avec les contraintes pour address.street
  private Address address;

  // getters et setters
}
```

La liaison `address.street` valide par rapport à `@NotBlank` sur `Address.street`. Chaque liaison valide la propriété à la fin de son chemin.

L'[exemple de beans imbriqués](https://github.com/webforj/built-with-webforj) lie un `Employee` avec des beans imbriqués `Address` et `EmergencyContact` à travers un seul `BindingContext` en utilisant ce modèle.

### Messages de validation sensibles à la locale <DocChip chip='since' label='25.12' /> {#locale-aware-validation-messages}

Jakarta Validation prend en charge les messages de contrainte localisés grâce à une interpolation de messages standard. Lorsque vous changez la locale de l'application, le `JakartaValidator` doit connaître la nouvelle locale afin de pouvoir résoudre les messages dans la langue appropriée.

`JakartaValidator` implémente l'interface `LocaleAware`, ce qui signifie que `BindingContext.setLocale()` propage automatiquement la locale à tous les validateurs Jakarta dans le contexte. Vous n'avez pas besoin de mettre à jour chaque validateur manuellement.

```java {5}
BindingContext<Hero> context = new BindingContext<>(Hero.class, true);

// Lorsque la locale change, les validateurs Jakarta produisent automatiquement
// des messages dans la nouvelle locale
context.setLocale(Locale.GERMAN);
```

Dans un composant qui implémente `LocaleObserver`, appelez `context.setLocale()` à l'intérieur de `onLocaleChange()` pour synchroniser les messages de validation avec la langue de l'UI :

```java {3}
@Override
public void onLocaleChange(LocaleEvent event) {
  context.setLocale(event.getLocale());
}
```

Voir [messages de validation dynamiques](/docs/data-binding/validation/validators#dynamic-validation-messages) pour en savoir plus sur les validateurs sensibles à la locale.
