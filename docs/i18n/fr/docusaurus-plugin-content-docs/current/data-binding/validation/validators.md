---
sidebar_position: 2
title: Validators
_i18n_hash: 98f40d70b15464d8c7ee48710b07d8fc
---
Les validateurs vérifient les données de vos composants UI par rapport à des contraintes définies avant de les engager dans le modèle de données. Vous pouvez appliquer des validateurs pour garantir que les données répondent à certains critères, tels que se situer dans une plage spécifiée, correspondre à un modèle ou ne pas être vides.

Les validations sont configurées par liaison, permettant d'appliquer des règles spécifiques à chaque point de données individuellement. Cette configuration garantit que chaque élément de données subisse une validation en fonction de ses propres exigences.

## Ajout de validateurs {#adding-validators}

Ajoutez des validateurs à une liaison en utilisant la méthode `useValidator` sur le `BindingBuilder`.

```java
context.bind(nameTextField, "name")
    .useValidator(value -> !value.isEmpty(), "Le nom ne peut pas être vide")
    .useValidator(value -> value.length() >= 3, "Le nom doit comporter au moins 3 caractères")
    .add();
```

Dans l'exemple ci-dessus, deux validateurs vérifient que le nom n'est pas vide et qu'il contient au moins trois caractères.

:::tip Traitement des validateurs
Il n'y a pas de limite au nombre de validateurs que vous pouvez ajouter par liaison. La liaison applique les validateurs dans l'ordre d'insertion et s'arrête à la première violation.
:::

## Mise en œuvre des validateurs {#implementing-validators}

Vous pouvez créer des validateurs réutilisables personnalisés en implémentant l'interface `Validator<T>`, où `T` est le type de données que vous souhaitez valider. Cette configuration implique de définir la méthode validate, qui vérifie les données et renvoie un `ValidationResult`.

Voici un exemple de validateur réutilisable qui vérifie si l'email d'un utilisateur est valide.

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
        return ValidationResult.invalid("Adresse email invalide");
    }
  }
}
```

## Utilisation des validateurs dans les liaisons {#using-validators-in-bindings}

Une fois que vous avez défini un validateur, vous pouvez facilement l'appliquer à toutes les liaisons pertinentes dans votre application. C'est particulièrement utile pour les composants qui nécessitent des règles de validation communes à travers différentes parties de votre application, comme les adresses email des utilisateurs ou la force des mots de passe.

Pour appliquer le `EmailValidator` à une liaison :

```java
BindingContext<User> context = new BindingContext<>(User.class);
context.bind(ageField, "age")
    .useValidator(new EmailValidator())
    .add();
```

## Surcharge des messages de validateur {#overriding-validator-messages}

Vous pouvez personnaliser les messages d'erreur des validateurs au moment de la liaison à un composant UI spécifique. Cela vous permet de fournir des informations plus détaillées ou contextuellement pertinentes à l'utilisateur si la validation échoue. Les messages personnalisés sont particulièrement utiles lorsque le même validateur s'applique à plusieurs composants mais nécessite une orientation différente pour l'utilisateur en fonction du contexte dans lequel il est utilisé.

Voici comment surcharger le message par défaut d'un validateur réutilisable dans une liaison :

```java
BindingContext<User> context = new BindingContext<>(User.class);
context.bind(emailField, "email")
    .useValidator(
        Validator.from(new EmailValidator(), "Message personnalisé pour adresse email invalide"))
    .add();
```

Dans l'exemple ci-dessus, le code applique le `EmailValidator` à un champ email avec un message d'erreur personnalisé spécifiquement adapté à ce champ. Cela permet une expérience utilisateur plus dirigée et utile si la validation échoue.

:::tip Comprendre `Validator.from`
La méthode `Validator.from` enveloppe un validateur passé avec un nouveau, vous permettant de spécifier un message d'erreur personnalisé au cas où le validateur ne prendrait pas en charge les messages personnalisés. Cette technique est particulièrement utile lorsque vous devez appliquer la même logique de validation à plusieurs composants mais avec des messages d'erreur spécifiques au contexte distincts pour chaque instance.
:::
