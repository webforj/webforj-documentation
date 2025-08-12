---
sidebar_position: 6
title: Jakarta Validation
_i18n_hash: eec00fac283efce49d856b1d40a48252
---
[Java Bean Validation](https://beanvalidation.org/) est largement reconnu comme la norme pour intégrer une logique de validation dans les applications Java. Il utilise une approche uniforme pour la validation en permettant aux développeurs d'annoter les propriétés du modèle de domaine avec des contraintes de validation déclaratives. Ces contraintes sont appliquées au moment de l'exécution, avec des options pour des règles intégrées et personnalisées.

webforJ s'intègre parfaitement avec Bean Validation grâce à l'adaptateur `JakartaValidator`, offrant un support robuste dès la sortie de la boîte.

## Installation {#installation}

Il est nécessaire d'inclure une implémentation compatible, telle que [Hibernate Validator](https://hibernate.org/validator/), dans votre classpath. Si votre environnement ne contient pas cette implémentation par défaut, vous pouvez l'ajouter manuellement en utilisant les dépendances Maven suivantes :

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

La classe `JakartaValidator` sert d'adaptateur, faisant le lien entre le contexte de liaison webforJ et Jakarta Validation. Cette intégration permet l'utilisation de règles de validation complexes directement via des annotations dans la classe bean.

### Activation du `JakartaValidator` {#activating-jakartavalidator}

Pour activer le `JakartaValidator` dans l'ensemble du contexte, vous utilisez généralement le paramètre `useJakartaValidator` lors de la construction du `BindingContext`.

```java
BindingContext<User> context = new BindingContext<>(User.class, true);
```

### Définition des contraintes pour les propriétés de bean {#defining-constraints-for-bean-properties}

Les contraintes basées sur des annotations sont directement appliquées dans la classe bean pour spécifier les conditions de validation, comme l'illustre l'exemple ci-dessous :

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

De telles contraintes sont aussi efficaces que celles définies par programmation lors de l'initialisation de la liaison, garantissant des résultats de validation cohérents.

:::warning
Actuellement, le `JakartaValidator` ne reconnaît que les contraintes qui sont directement assignées aux propriétés et ignore toute validation non directement associée aux propriétés.
:::
