---
sidebar_position: 4
title: Transformation
description: >-
  Convert between UI and model data types in webforJ bindings by implementing
  the Transformer interface and wiring it via useTransformer.
_i18n_hash: 7a8064dc7b603cd86ad965a41216c55c
---
Les transformations de données convertissent entre les types de données utilisés dans les composants de l'interface utilisateur et ceux de votre modèle de données. Cela permet de garder les types de données compatibles et correctement formatés lors du transfert de données entre le frontend et le backend de vos applications.

:::tip
Le paramètre du transformateur est le mieux utilisé lorsque le type de données de la propriété bean ne correspond pas au type de données géré par les composants de l'interface utilisateur. Si vous devez simplement transformer des données du même type, la configuration des [getters et setters des liaisons](bindings#binding-getters-and-setters) est l'approche préférée.
:::

## Configuration des transformateurs {#configuring-transformers}

Vous configurez les transformations de données directement au sein de vos liaisons, vous permettant de définir comment les données doivent être transformées pendant le processus de liaison des données.

Vous pouvez ajouter des transformateurs à une liaison en utilisant la méthode `useTransformer` sur le `BindingBuilder`. Les transformateurs doivent implémenter l'interface `Transformer`, qui nécessite la définition de méthodes pour les deux directions de flux de données : du modèle vers l'UI et de l'UI vers le modèle.

```java
context.bind(salaryField, "salary")
  .useTransformer(new CurrencyTransformer())
  .add();
```

Dans l'exemple ci-dessus, le code configure un `CurrencyTransformer` pour gérer les conversions entre le type de données du modèle (par exemple, BigDecimal) et la représentation UI (par exemple, une chaîne formatée).

:::info
Chaque liaison est associée à un seul transformateur. Si la transformation d'une valeur nécessite plusieurs étapes, il est recommandé de mettre en œuvre votre propre transformateur pour ces étapes.
:::

## Mise en œuvre d'un transformateur {#implementing-a-transformer}

Voici un exemple de mise en œuvre d'un transformateur simple qui convertit entre un modèle `LocalDate` et une représentation UI `String` :

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

Ce transformateur gère les champs de date, formatant les dates lors de leur affichage dans l'UI et les analysant de nouveau dans le modèle.

### Utilisation des transformateurs dans les liaisons {#using-transformers-in-bindings}

Une fois que vous avez défini un transformateur, vous pouvez l'appliquer à plusieurs liaisons dans votre application. Cette approche est particulièrement utile pour les formats de données standard qui doivent être traités de manière cohérente dans différentes parties de votre application.

```java
BindingContext<Employee> context = new BindingContext<>(Employee.class);
context.bind(startDateField, "startDate", String.class)
  .useTransformer(new DateTransformer())
  .add();
```

:::info Spécification du type de propriété Bean

Dans la méthode `bind`, la spécification du type de propriété bean en tant que troisième paramètre est essentielle lorsqu'il y a une différence entre le type de données affiché par le composant UI et le type de données utilisé dans le modèle. Par exemple, si le composant gère `startDateField` comme un `LocalDate` Java au sein du composant mais est stocké comme un `String` dans le modèle, définir explicitement le type comme `String.class` indique au mécanisme de liaison de traiter et de convertir les données entre les deux types différents utilisés par le composant et le bean à l'aide du transformateur et des validateurs fournis.
:::

### Simplification des transformations avec `Transformer.of` {#simplifying-transforms-with-transformerof}

Il est possible de simplifier la mise en œuvre de telles transformations à l'aide de la méthode `Transformer.of` fournie par le `Transformer`. Cette méthode est une simplification syntaxique et permet d'écrire une méthode qui gère les transformations en ligne, plutôt que de passer une classe implémentant l'interface `Transformer`.

Dans l'exemple suivant, le code gère une interaction avec une case à cocher dans une application de voyage où les utilisateurs peuvent opter pour des services supplémentaires tels que la location de voiture. L'état de la case à cocher `boolean` doit être transformé en une représentation sous forme de chaîne `"yes"` ou `"no"` que le modèle backend utilise.

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
      // message
      "La case à cocher doit être cochée"
  )
  .add();
```

### Messages d'erreur de transformateur dynamiques <DocChip chip='since' label='25.12' /> {#dynamic-transformer-error-messages}

Par défaut, le message d'erreur affiché en cas d'échec de la transformation est une chaîne statique. Dans les applications qui prennent en charge plusieurs langues, vous pouvez passer un `Supplier<String>` à la place, de sorte que le message est résolu chaque fois que la transformation échoue :

```java {7}
context.bind(quantityField, "quantity", Integer.class)
  .useTransformer(
    Transformer.of(
      str -> Integer.parseInt(str),
      val -> String.valueOf(val)
    ),
    () -> t("validation.quantity.invalid")
  )
  .add();
```

Le fournisseur est invoqué uniquement lorsque la transformation lance une `TransformationException`. Cela signifie que le message reflète toujours le locale actuel au moment de l'échec.

#### Transformateurs sensibles à la locale {#locale-aware-transformers}

Pour les transformateurs réutilisables qui ont besoin d'accéder à la locale actuelle en interne (par exemple, pour formater des nombres ou des dates selon des conventions régionales), implémentez l'interface `LocaleAware`. Lorsque la locale change via `BindingContext.setLocale()`, le contexte propage automatiquement la nouvelle locale aux transformateurs qui implémentent cette interface.
