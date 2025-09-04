---
sidebar_position: 2
title: Validators
_i18n_hash: 3d41925977054029c22c2110455dd419
---
Les validateurs vérifient les données au sein de vos composants UI par rapport aux contraintes définies avant de valider ces données dans le modèle de données. Vous pouvez appliquer des validateurs pour vous assurer que les données répondent à certains critères, tels que se situer dans une plage spécifiée, correspondre à un modèle ou ne pas être vides.

Les validations sont configurées par liaison, permettant à des règles spécifiques de s'appliquer à chaque point de donnée individuellement. Cette configuration garantit que chaque élément de donnée subit une validation selon ses propres exigences.

## Ajouter des validateurs {#adding-validators}

Ajoutez des validateurs à une liaison en utilisant la méthode `useValidator` sur le `BindingBuilder`.

```java
context.bind(nameTextField, "name")
    .useValidator(value -> !value.isEmpty(), "Le nom ne peut pas être vide")
    .useValidator(value -> value.length() >= 3, "Le nom doit comporter au moins 3 caractères")
    .add();
```

Dans l'exemple ci-dessus, deux validateurs vérifient que le nom n'est pas vide et qu'il contient au moins trois caractères.

:::tip Traitement des validateurs
Il n’y a aucune limite au nombre de validateurs que vous pouvez ajouter par liaison. La liaison applique les validateurs dans l'ordre d'insertion et s'arrête au premier manquement.
:::

## Mise en œuvre des validateurs {#implementing-validators}

Vous pouvez créer des validateurs réutilisables personnalisés en implémentant l'interface `Validator<T>`, où `T` est le type de donnée que vous souhaitez valider. Cette configuration implique de définir la méthode validate, qui vérifie les données et renvoie un `ValidationResult`.

Voici un exemple de validateur réutilisable qui vérifie si l'adresse e-mail d'un utilisateur est valide.

```java
import com.webforj.data.validation.server.ValidationResult;
import com.webforj.data.validation.server.validator.Validator;

public class EmailValidator implements Validator<String> {
  private static final String PATTERN = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";

  @Override
  public ValidationResult validate(String value) {
    if (value.matches(PATTERN)) {
        return ValidationResult.valid();
    } else {
        return ValidationResult.invalid("Adresse e-mail invalide");
    }
  }
}
```

## Utiliser des validateurs dans les liaisons {#using-validators-in-bindings}

Une fois que vous avez défini un validateur, vous pouvez facilement l'appliquer à toutes les liaisons pertinentes dans votre application. Cela est particulièrement utile pour les composants qui nécessitent des règles de validation communes dans différentes parties de votre application, telles que les adresses e-mail des utilisateurs ou la force des mots de passe.

Pour appliquer le `EmailValidator` à une liaison :

```java
BindingContext<User> context = new BindingContext<>(User.class);
context.bind(ageField, "age")
    .useValidator(new EmailValidator())
    .add();
```

## Remplacer les messages de validateurs {#overriding-validator-messages}

Vous pouvez personnaliser les messages d'erreur des validateurs au point de liaison à un composant UI spécifique. Cela vous permet de fournir des informations plus détaillées ou contextuellement pertinentes à l'utilisateur si la validation échoue. Les messages personnalisés sont particulièrement utiles lorsque le même validateur s'applique à plusieurs composants mais nécessite des conseils différents pour l'utilisateur en fonction du contexte dans lequel il est utilisé.

Voici comment remplacer le message par défaut d'un validateur réutilisable dans une liaison :

```java
BindingContext<User> context = new BindingContext<>(User.class);
context.bind(emailField, "email")
    .useValidator(
        Validator.from(new EmailValidator(), "Message personnalisé pour l'adresse e-mail invalide"))
    .add();
```

Dans l'exemple ci-dessus, le code applique le `EmailValidator` à un champ d'e-mail avec un message d'erreur personnalisé spécifiquement conçu pour ce champ. Cela permet une expérience utilisateur plus ciblée et utile si la validation échoue.

:::tip Comprendre `Validator.from`
La méthode `Validator.from` enveloppe un validateur passé avec un nouveau, vous permettant de spécifier un message d'erreur personnalisé au cas où le validateur ne prendrait pas en charge les messages personnalisés. Cette technique est particulièrement utile lorsque vous devez appliquer la même logique de validation à plusieurs composants, mais avec des messages d'erreur distincts et spécifiques au contexte pour chaque instance.
:::
