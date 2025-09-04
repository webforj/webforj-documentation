---
sidebar_position: 4
title: Transformation
_i18n_hash: fe3acbd17750ab0092cbc3609b967969
---
Les transformations de données sont une fonctionnalité essentielle, facilitant la conversion transparente entre les types de données utilisés dans les composants UI et ceux de votre modèle de données. Cette capacité garantit que les types de données sont compatibles et correctement formatés lors du transfert de données entre le frontend et le backend de vos applications.

:::tip
Le paramètre de transformation est le mieux utilisé lorsque le type de données de la propriété bean ne correspond pas au type de données traité par les composants UI. Si vous avez simplement besoin de transformer des données du même type, configurer [les getters et setters des liaisons](bindings#binding-getters-and-setters) est l'approche préférée.
:::

## Configuration des transformateurs {#configuring-transformers}

Vous configurez les transformations de données directement dans vos liaisons, vous permettant de définir comment les données doivent être transformées pendant le processus de liaison des données.

Vous pouvez ajouter des transformateurs à une liaison en utilisant la méthode `useTransformer` sur le `BindingBuilder`. Les transformateurs doivent implémenter l'interface `Transformer`, qui nécessite la définition de méthodes pour les deux directions du flux de données : du modèle vers l'UI et de l'UI vers le modèle.

```java
context.bind(salaryField, "salary")
    .useTransformer(new CurrencyTransformer())
    .add();
```

Dans l'exemple ci-dessus, le code configure un `CurrencyTransformer` pour gérer les conversions entre le type de données du modèle (par exemple, BigDecimal) et la représentation UI (par exemple, une chaîne formatée).

:::info
Chaque liaison est associée à un seul transformateur. Si la transformation d'une valeur nécessite plusieurs étapes, il est recommandé de mettre en œuvre votre propre transformateur pour ces étapes.
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

Ce transformateur facilite la gestion des champs de date, garantissant que les dates sont correctement formatées lorsqu'elles sont affichées dans l'UI et correctement analysées à nouveau dans le modèle.

## Utilisation des transformateurs dans les liaisons {#using-transformers-in-bindings}

Une fois que vous avez défini un transformateur, vous pouvez l'appliquer à plusieurs liaisons dans votre application. Cette approche est particulièrement utile pour les formats de données standard qui nécessitent un traitement cohérent dans différentes parties de votre application.

```java
BindingContext<Employee> context = new BindingContext<>(Employee.class);
context.bind(startDateField, "startDate", String.class)
    .useTransformer(new DateTransformer())
    .add();
```

:::info Spécification du type de propriété Bean

Dans la méthode `bind`, spécifier le type de la propriété bean en tant que troisième paramètre est essentiel lorsqu'il y a une disparité entre le type de données affiché par le composant UI et le type de données utilisé dans le modèle. Par exemple, si le composant traite `startDateField` comme un `LocalDate` Java dans le composant mais stocké comme un `String` dans le modèle, définir explicitement le type comme `String.class` garantit que le mécanisme de liaison traite et convertit avec précision les données entre les deux types différents utilisés par le composant et le bean en utilisant le transformateur et les validateurs fournis.
:::

## Simplification des transformations avec `Transformer.of` {#simplifying-transforms-with-transformerof}

Il est possible de simplifier l'implémentation de telles transformations en utilisant la méthode `Transformer.of` fournie par le `Transformer`. Cette méthode est un sucre syntaxique et vous permet d'écrire une méthode qui gère les transformations en ligne, au lieu de passer une classe implémentant l'interface `Transformer`.

Dans l'exemple suivant, le code gère une interaction de case à cocher dans une application de voyage où les utilisateurs peuvent opter pour des services supplémentaires comme la location de voiture. L'état de la case à cocher `boolean` doit être transformé en une représentation en chaîne `"yes"` ou `"no"` que le modèle backend utilise.

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
      "La case à cocher doit être cochée"
  )
  .add();
```
