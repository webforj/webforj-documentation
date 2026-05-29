---
sidebar_position: 6
title: Jakarta Validation
sidebar_class_name: updated-content
_i18n_hash: 813ccefe385954366010291f50215611
---
[Java Bean Validation](https://beanvalidation.org/) est largement reconnu comme la norme pour intégrer la logique de validation dans les applications Java. Il utilise une approche uniforme pour la validation en permettant aux développeurs d'annoter les propriétés du modèle de domaine avec des contraintes de validation déclaratives. Ces contraintes sont appliquées à l'exécution, avec des options pour des règles intégrées et des règles définies par l'utilisateur.

webforJ s'intègre à Bean Validation via l'adaptateur `JakartaValidator`, offrant un support complet dès la sortie de la boîte.

## Installation {#installation}

Il est nécessaire d'inclure une implémentation compatible, telle que [Hibernate Validator](https://hibernate.org/validator/), dans votre classpath. Si votre environnement ne fournit pas cette implémentation par défaut, vous pouvez l'ajouter manuellement en utilisant les dépendances Maven suivantes :

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

La classe `JakartaValidator` sert d'adaptateur, reliant le contexte de liaison webforJ avec Jakarta Validation. Cette intégration permet l'utilisation de règles de validation complexes directement via des annotations dans la classe bean.

### Activation de `JakartaValidator` {#activating-jakartavalidator}

Pour activer le `JakartaValidator` sur l'ensemble du contexte, vous utilisez typiquement le paramètre `useJakartaValidator` lors de la construction du `BindingContext`.

```java
BindingContext<User> context = new BindingContext<>(User.class, true);
```

### Définition des contraintes pour les propriétés des beans {#defining-constraints-for-bean-properties}

Les contraintes basées sur des annotations sont appliquées directement au sein de la classe bean pour spécifier les conditions de validation, comme illustré dans l'exemple ci-dessous :

```java
public class Hero {
  @NotEmpty(message = "Le nom ne peut pas être vide")
  @Length(min = 3, max = 20)
  private String name;

  @NotEmpty(message = "Pouvoir non spécifié")
  @Pattern(regexp = "Fly|Invisible|LaserVision|Speed|Teleportation", message = "Pouvoir invalide")
  private String power;

  // getters et setters
}
```

De telles contraintes sont aussi efficaces que celles définies par programmation lors de l'initialisation de la liaison et produisent des résultats de validation cohérents.

:::warning
Actuellement, le `JakartaValidator` ne reconnaît que les contraintes qui sont directement assignées aux propriétés et ignore toutes les validations non directement associées aux propriétés.
:::

### Messages de validation sensibles aux paramètres régionaux <DocChip chip='since' label='25.12' /> {#locale-aware-validation-messages}

Jakarta Validation prend en charge les messages de contrainte localisés via l'interpolation de messages standard. Lorsque vous changez la langue de l'application, le `JakartaValidator` doit connaître la nouvelle langue afin de résoudre les messages dans la bonne langue.

`JakartaValidator` implémente l'interface `LocaleAware`, ce qui signifie que `BindingContext.setLocale()` transmet automatiquement la langue à tous les validateurs Jakarta dans le contexte. Vous n'avez pas besoin de mettre à jour chaque validateur manuellement.

```java {5}
BindingContext<Hero> context = new BindingContext<>(Hero.class, true);

// Lorsque la langue change, les validateurs Jakarta produisent automatiquement
// des messages dans la nouvelle langue
context.setLocale(Locale.GERMAN);
```

Dans un composant qui implémente `LocaleObserver`, appelez `context.setLocale()` dans `onLocaleChange()` pour maintenir les messages de validation synchronisés avec la langue de l'interface utilisateur :

```java {3}
@Override
public void onLocaleChange(LocaleEvent event) {
  context.setLocale(event.getLocale());
}
```

Voir [messages de validation dynamiques](/docs/data-binding/validation/validators#dynamic-validation-messages) pour en savoir plus sur les validateurs sensibles aux paramètres régionaux.
