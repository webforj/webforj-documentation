---
sidebar_position: 4
title: Transformation
_i18n_hash: fccb434a8897618a0197f9883cd94795
---
Les transformations de données sont une fonctionnalité essentielle, facilitant la conversion fluide entre les types de données utilisés dans les composants d'interface utilisateur et ceux dans votre modèle de données. Cette capacité garantit que les types de données sont compatibles et correctement formatés lors du transfert des données entre le frontend et le backend de vos applications.

:::tip
Le paramètre du transformateur est particulièrement utile lorsque le type de données de la propriété du bean ne correspond pas au type de données géré par les composants d'interface utilisateur. Si vous devez simplement transformer des données du même type, la configuration des [getters et setters des liaisons](bindings#binding-getters-and-setters) est l'approche privilégiée.
:::

## Configuration des transformateurs {#configuring-transformers}

Vous configurez les transformations de données directement au sein de vos liaisons, vous permettant de définir comment les données doivent être transformées lors du processus de liaison des données.

Vous pouvez ajouter des transformateurs à une liaison en utilisant la méthode `useTransformer` sur le `BindingBuilder`. Les transformateurs doivent implémenter l'interface `Transformer`, qui nécessite de définir des méthodes pour les deux directions du flux de données : du modèle vers l'UI et de l'UI vers le modèle.

```java
context.bind(salaryField, "salary")
    .useTransformer(new CurrencyTransformer())
    .add();
```

Dans l'exemple ci-dessus, le code configure un `CurrencyTransformer` pour gérer les conversions entre le type de données du modèle (par exemple, BigDecimal) et la représentation UI (par exemple, une chaîne formatée).

:::info
Chaque liaison est associée à un seul transformateur. Si la transformation d'une valeur nécessite plusieurs étapes, il est recommandé d'implémenter votre propre transformateur pour ces étapes.
:::

## Implémentation d'un transformateur {#implementing-a-transformer}

Voici un exemple d'implémentation d'un transformateur simple qui convertit entre un modèle `LocalDate` et une représentation UI `String` :

```java
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.webforj.data.transformation.TransformationException;
import com.webforj.data.transformation.transformer.Transformer;

public class DateTransformer implements Transformer<LocalDate, String> {
  private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

  @Override
  public LocalDate transformToComponent(String modelValue) {
    try {
      return LocalDate.parse(modelValue, formatter);
    } catch (Exception e) {
      throw new TransformationException("Format de date invalide");
    }
  }

  @Override
  public String transformToModel(LocalDate componentValue) {
    try {
      return componentValue.format(formatter);
    } catch (Exception e) {
      throw new TransformationException("Format de date invalide");
    }
  }
}
```

Ce transformateur facilite la gestion des champs de date, garantissant que les dates sont correctement formatées lorsqu'elles sont affichées dans l'interface utilisateur et correctement analysées à nouveau dans le modèle.

## Utilisation des transformateurs dans les liaisons {#using-transformers-in-bindings}

Une fois que vous avez défini un transformateur, vous pouvez l'appliquer à plusieurs liaisons dans votre application. Cette approche est particulièrement utile pour des formats de données standard qui nécessitent un traitement cohérent dans différentes parties de votre application.

```java
BindingContext<Employee> context = new BindingContext<>(Employee.class);
context.bind(startDateField, "startDate", String.class)
    .useTransformer(new DateTransformer())
    .add();
```

:::info Spécification du type de propriété Bean

Dans la méthode `bind`, il est essentiel de spécifier le type de la propriété bean en tant que troisième paramètre lorsqu'il y a une disparité entre le type de données affiché par le composant d'interface utilisateur et le type de données utilisé dans le modèle. Par exemple, si le composant gère `startDateField` comme un `LocalDate` Java au sein du composant mais est stocké en tant que `String` dans le modèle, définir explicitement le type comme `String.class` garantit que le mécanisme de liaison traite et convertit correctement les données entre les deux types différents utilisés par le composant et le bean à l'aide du transformateur et des validateurs fournis.
:::

## Simplification des transformations avec `Transformer.of` {#simplifying-transforms-with-transformerof}

Il est possible de simplifier la mise en œuvre de telles transformations en utilisant la méthode `Transformer.of` fournie par le `Transformer`. Cette méthode est une sucre syntaxique et vous permet d'écrire une méthode qui gère les transformations en ligne, plutôt que de passer une classe implémentant l'interface `Transformer`.

Dans l'exemple suivant, le code gère une interaction de case à cocher dans une application de voyage où les utilisateurs peuvent opter pour des services supplémentaires tels que la location de voiture. L'état de la case à cocher `boolean` doit être transformé en une représentation de chaîne `"yes"` ou `"no"` que le modèle backend utilise.

```java
CheckBox carRental = new CheckBox("Location de voiture");
BindingContext<Trip> context = new BindingContext<>(Trip.class, true);
context.bind(carRental, "carRental", String.class)
  .useTransformer(
      Transformer.of(
        // convertir la valeur du composant en valeur du modèle
        bool -> Boolean.TRUE.equals(bool) ? "yes" : "no",
        // convertir la valeur du modèle en valeur du composant
        str -> str.equals("yes")
      ), 

      // en cas d'échec de la transformation, afficher le message suivant
      "La case doit être cochée"
  )
  .add();
```
