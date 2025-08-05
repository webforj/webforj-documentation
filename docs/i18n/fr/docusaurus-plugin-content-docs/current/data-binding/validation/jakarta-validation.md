---
sidebar_position: 6
title: Jakarta Validation
_i18n_hash: 68a57d576ce21a9f99b121e5db3cf85f
---
[Java Bean Validation](https://beanvalidation.org/) est largement reconnu comme la norme pour intégrer la logique de validation dans les applications Java. Il utilise une approche uniforme de validation en permettant aux développeurs d'annoter les propriétés du modèle de domaine avec des contraintes de validation déclaratives. Ces contraintes sont appliquées à l'exécution, avec des options pour des règles intégrées et celles définies par l'utilisateur.

webforJ s'intègre parfaitement avec Bean Validation via l'adaptateur `JakartaValidator`, offrant un support robuste dès le départ.

## Installation {#installation}

Il est nécessaire d'inclure une implémentation compatible, telle que [Hibernate Validator](https://hibernate.org/validator/), dans votre classpath. Si votre environnement ne prévoit pas cette implémentation par défaut, vous pouvez l'ajouter manuellement en utilisant les dépendances Maven suivantes :

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

### Activation du `JakartaValidator` {#activating-jakartavalidator}

Pour activer le `JakartaValidator` dans tout le contexte, vous utilisez généralement le paramètre `useJakartaValidator` lors de la construction du `BindingContext`.

```java
BindingContext<User> context = new BindingContext<>(User.class, true);
```

### Définition des contraintes pour les propriétés bean {#defining-constraints-for-bean-properties}

Les contraintes basées sur des annotations sont appliquées directement au sein de la classe bean pour spécifier les conditions de validation, comme l'illustre l'exemple ci-dessous :

```java
public class Hero {
  @NotEmpty(message = "Le nom ne peut pas être vide")
  @Length(min = 3, max = 20)
  private String name;

  @NotEmpty(message = "Pouvoir non spécifié")
  @Pattern(regexp = "Fly|Invisible|LaserVision|Speed|Teleportation", message = "Pouvoir invalide")
  private String power;

  // getters and setters
}
```

Ces contraintes sont aussi efficaces que celles définies par programmation lors de l'initialisation de la liaison, garantissant des résultats de validation cohérents.

:::warning
Actuellement, le `JakartaValidator` ne reconnaît que les contraintes directement assignées aux propriétés et ignore toute validation non directement associée aux propriétés.
:::
