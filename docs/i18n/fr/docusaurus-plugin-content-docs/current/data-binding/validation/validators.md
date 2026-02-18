---
sidebar_position: 2
title: Validators
sidebar_class_name: updated-content
_i18n_hash: 4af002debda2abb59282b5c6a1bf01d7
---
Les validateurs valident les données au sein de vos composants UI par rapport à des contraintes définies avant de soumettre ces données au modèle de données. Vous pouvez appliquer des validateurs pour vérifier que les données répondent à certains critères, tels que se situer dans une plage spécifiée, correspondre à un modèle ou ne pas être vides.

Les validations sont configurées par liaison, permettant à des règles spécifiques de s'appliquer à chaque point de données individuellement. Chaque donnée subit une validation selon ses propres exigences.

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
Il n'y a pas de limite au nombre de validateurs que vous pouvez ajouter par liaison. La liaison applique les validateurs par ordre d'insertion et s'arrête à la première violation.
:::

## Mise en œuvre des validateurs {#implementing-validators}

Vous pouvez créer des validateurs réutilisables personnalisés en implémentant l'interface `Validator<T>`, où `T` est le type de données que vous souhaitez valider. Cette configuration implique de définir la méthode validate, qui vérifie les données et retourne un `ValidationResult`.

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

### Utilisation des validateurs dans les liaisons {#using-validators-in-bindings}

Une fois que vous avez défini un validateur, vous pouvez facilement l'appliquer à toutes les liaisons pertinentes au sein de votre application. Cela est particulièrement utile pour les composants qui nécessitent des règles de validation communes à travers différentes parties de votre application, telles que les adresses email des utilisateurs ou la force des mots de passe.

Pour appliquer le `EmailValidator` à une liaison :

```java
BindingContext<User> context = new BindingContext<>(User.class);
context.bind(ageField, "age")
    .useValidator(new EmailValidator())
    .add();
```

### Surcharge des messages de validateurs {#overriding-validator-messages}

Vous pouvez personnaliser les messages d'erreur des validateurs au moment de la liaison à un composant UI spécifique. Cela vous permet de fournir des informations plus détaillées ou contextuellement pertinentes à l'utilisateur en cas d'échec de la validation. Les messages personnalisés sont particulièrement utiles lorsque le même validateur s'applique à plusieurs composants mais nécessite des conseils différents en fonction du contexte dans lequel il est utilisé.

Voici comment remplacer le message par défaut d'un validateur réutilisable dans une liaison :

```java
BindingContext<User> context = new BindingContext<>(User.class);
context.bind(emailField, "email")
    .useValidator(
        Validator.from(new EmailValidator(), "Message personnalisé pour une adresse email invalide"))
    .add();
```

Dans l'exemple ci-dessus, le code applique le `EmailValidator` à un champ email avec un message d'erreur personnalisé spécifiquement adapté à ce champ.

:::tip Comprendre `Validator.from`
La méthode `Validator.from` enveloppe un validateur passé avec un nouveau, vous permettant de spécifier un message d'erreur personnalisé au cas où le validateur ne prend pas en charge les messages personnalisés. Cette technique est particulièrement utile lorsque vous avez besoin d'appliquer la même logique de validation à plusieurs composants mais avec des messages d'erreur distincts et spécifiques au contexte pour chaque instance.
:::

### Messages de validation dynamiques <DocChip chip='since' label='25.12' /> {#dynamic-validation-messages}

Par défaut, les messages de validation sont des chaînes statiques définies une fois au moment de la liaison. Dans les applications qui prennent en charge plusieurs langues, ces messages statiques ne se mettent pas à jour lorsque l'utilisateur change de locale. Pour résoudre cela, les méthodes `useValidator` et les méthodes de fabrication `Validator` acceptent un `Supplier<String>` invoqué chaque fois qu'une validation échoue, permettant au message de se résoudre dynamiquement.

#### Validateurs en ligne avec des messages dynamiques {#inline-validators-with-dynamic-messages}

Passez un `Supplier<String>` au lieu d'une simple `String` à `useValidator` :

```java {2,3}
context.bind(nameTextField, "name")
    .useValidator(value -> !value.isEmpty(), () -> t("validation.name.required"))
    .useValidator(value -> value.length() >= 3, () -> t("validation.name.minLength"))
    .add();
```

Chaque fois que la validation s'exécute et que le prédicat échoue, le fournisseur appelle `t()` qui résout le message pour la locale actuelle.

#### Méthodes de fabrication avec des messages dynamiques {#factory-methods-with-dynamic-messages}

Les méthodes de fabrication `Validator.of` et `Validator.from` acceptent également des fournisseurs :

```java {4,10}
// Créer un validateur basé sur un prédicat avec un message dynamique
Validator<String> required = Validator.of(
    value -> !value.isEmpty(),
    () -> t("validation.required")
);

// Envelopper un validateur existant avec un message de remplacement dynamique
Validator<String> email = Validator.from(
    new EmailValidator(),
    () -> t("validation.email.invalid")
);
```

#### Validateurs personnalisés sensibles à la langue {#locale-aware-custom-validators}

Pour les validateurs réutilisables qui doivent produire des messages sensibles à la langue en interne, implémentez l'interface `LocaleAware`. Lorsque la locale change via `BindingContext.setLocale()`, le contexte propage automatiquement la nouvelle locale à tous les validateurs qui implémentent cette interface. La prochaine exécution de validation produira alors des messages dans la locale mise à jour.
