---
sidebar_position: 2
title: Bindings
sidebar_class_name: updated-content
description: >-
  Link Java Bean properties to webforJ UI components through BindingContext to
  synchronize reads and writes between model and view.
_i18n_hash: 9a4b6da2f5a3bd524a0b3cf6a1eb86e1
---
Un binding dans webforJ lie une propriété spécifique d'un Java Bean à un composant UI. Ce lien permet des mises à jour automatiques entre l'UI et le modèle backend. Chaque binding peut gérer la synchronisation des données, la validation, la transformation et la gestion des événements.

Vous pouvez initier des bindings uniquement via le `BindingContext`. Il gère une collection d'instances de binding, chacune reliant un composant UI à une propriété d'un bean. Il facilite les opérations de groupe sur les bindings, telles que la validation et la synchronisation entre les composants UI et les propriétés du bean. Il agit comme un agrégateur, permettant des actions collectives sur plusieurs bindings, simplifiant ainsi la gestion du flux de données au sein des applications.

:::tip Binding Automatique
Cette section introduit les bases de la configuration manuelle des bindings. De plus, vous pouvez créer automatiquement des bindings basés sur les composants UI de votre formulaire. Une fois que vous maîtrisez les fondamentaux, apprenez-en plus en lisant la section [Binding Automatique](/docs/data-binding/automatic-binding).
:::

## Configurer les bindings {#configure-bindings}

Commencez par créer une nouvelle instance de `BindingContext` qui gère tous les bindings pour un modèle particulier. Ce contexte valide et met à jour tous les bindings collectivement.

```java
BindingContext<Hero> context = new BindingContext<>(Hero.class);
```

:::info
Chaque formulaire doit avoir une seule instance de `BindingContext`, et vous devez utiliser cette instance pour tous les composants du formulaire.
:::

### La propriété liée {#the-bound-property}

Une propriété de binding est un champ ou un attribut spécifique d'un Java Bean qui peut être lié à un composant UI dans votre application. Ce lien permet aux changements dans l'UI d'affecter directement la propriété correspondante du modèle de données, et vice versa, de sorte que l'UI et le modèle de données restent synchronisés.

Lors de la configuration d'un binding, vous devez fournir le nom de la propriété sous forme de chaîne. Ce nom doit correspondre au nom du champ dans la classe Java Bean. Voici un exemple simple:

```java
BindingContext<Hero> context = new BindingContext<>(Hero.class, true);
context
  .bind(textField, "power")
  .add()
```

```java
public class Hero  {
  private String name;
  private String power;

  // setters et getters
}
```

la méthode `bind` renvoie un `BindingBuilder` qui crée l'objet `Binding` et que vous pouvez utiliser pour configurer plusieurs paramètres du binding, la méthode `add` qui est celle qui permet effectivement d'ajouter le binding au contexte.

### Le composant lié {#the-bound-component}

L'autre côté du binding est le composant lié, qui fait référence au composant UI qui interagit avec la propriété du Java Bean. Le composant lié peut être tout composant UI qui supporte l'interaction utilisateur et l'affichage, comme des champs de texte, des zones de combinaison, des cases à cocher, ou tout composant personnalisé qui implémente l'interface `ValueAware`.

Le composant lié sert de point d'interaction pour l'utilisateur avec le modèle de données sous-jacent. Il affiche des données à l'utilisateur et capture également les entrées de l'utilisateur, qui sont ensuite propagées au modèle.

```java
TextField nameTextField = new TextField("Nom");
BindingContext<Hero> context = new BindingContext<>(Hero.class);
context.bind(nameTextField, "name").add();
```

## Lecture et écriture de données {#reading-and-writing-data}

### Lecture des données {#reading-data}

Lire des données implique de peupler les composants UI avec des valeurs du modèle de données. Cela se fait généralement lorsque le formulaire est initialement affiché, ou lorsque vous devez recharger les données en raison de changements dans le modèle sous-jacent. La méthode `read` fournie par `BindingContext` simplifie ce processus.

```java
// Supposons que l'objet Hero a été instancié et initialisé
Hero hero = new Hero("Clark Kent", "Vol");

// BindingContext est déjà configuré avec des bindings
context.read(hero);
```

Dans cet exemple, la méthode `read` prend une instance de `Hero` et met à jour tous les composants UI liés pour refléter les propriétés du héros. Si le nom ou le pouvoir du héros change, les composants UI correspondants (comme un `TextField` pour le nom et un `ComboBox` pour les pouvoirs) affichent ces nouvelles valeurs.

### Écriture de données {#writing-data}

Écrire des données implique de collecter des valeurs des composants UI et de mettre à jour le modèle de données. Cela se produit généralement lorsqu'un utilisateur soumet un formulaire. La méthode `write` gère la validation et la mise à jour du modèle en une seule étape.

```java
// Cela pourrait être déclenché par un événement de soumission de formulaire
submit.onClick(event -> {
  ValidationResult results = context.write(hero);
  if (results.isValid()) {
    // Les données sont valides, et l'objet héro a été mis à jour
    // repository.save(hero);
  } else {
    // Gérer les erreurs de validation
    // results.getMessages();
  }
});
```

Dans le code ci-dessus, lorsque l'utilisateur clique sur le bouton de soumission, la méthode `write` est appelée. 
Elle effectue toutes les validations configurées et, si les données passent tous les contrôles, met à jour l'objet `Hero` avec de nouvelles valeurs des composants liés. Si les données sont valides, vous pourriez les enregistrer dans une base de données ou les traiter davantage. En cas d'erreurs de validation, vous devriez gérer cela de manière appropriée, généralement en affichant des messages d'erreur à l'utilisateur.

:::tip Rapport d'erreurs de validation
Tous les composants de base de webforJ ont des configurations par défaut pour signaler automatiquement les erreurs de validation, soit en ligne, soit par une popover. Vous pouvez personnaliser ce comportement en utilisant [Reporters](./validation/reporters.md).
:::

## Propriétés de bean imbriquées <DocChip chip='since' label='26.01' /> {#nested-bean-properties}

Une propriété de binding peut être un chemin pointant vers une propriété à l'intérieur d'un bean imbriqué. Chaque segment du chemin suit les conventions de getters et setters JavaBean standard, donc `address.street` se lit via `getAddress().getStreet()` et s'écrit via `getAddress().setStreet()`.

```java
BindingContext<Hero> context = new BindingContext<>(Hero.class);
context.bind(streetField, "address.street").add();
context.bind(cityField, "address.city").add();
```

```java
public class Hero {
  private String name;
  private Address address;

  // getters et setters
}

public class Address {
  private String street;
  private String city;
  private String zip;

  // getters et setters
}
```

Lors de la lecture, un chemin se résout en toute sécurité même lorsqu'un bean intermédiaire est `null`. Si un `Hero` n'a pas d'`Address`, les composants liés à `address.street` et `address.city` se lisent comme vides plutôt que de lancer une exception, de sorte que le formulaire est toujours peuplé.

Lors de l'écriture, le contexte crée tout bean intermédiaire manquant via son constructeur sans argument, donc écrire le formulaire dans un `Hero` sans `Address` produit une nouvelle `Address` peuplée. Une `Address` existante est réutilisée.

Les annotations de [validation Jakarta](/docs/data-binding/validation/jakarta-validation) sur une propriété imbriquée sont détectées de la même manière que sur une propriété de niveau supérieur. Une annotation telle que `@NotNull` sur `Address.street` marque le binding `address.street` comme [obligatoire](/docs/data-binding/automatic-binding#bindingrequired-annotation).

:::info Les chemins sont validés à l'avance
Le chemin complet est validé lorsque vous appelez `bind`. Une faute de frappe dans n'importe quel segment, que ce soit au niveau supérieur ou plus profondément dans le chemin, lance une `IllegalArgumentException`, donc les erreurs de binding se manifestent immédiatement au lieu d'attendre le moment de lecture ou d'écriture.
:::

<!-- vale off -->
## Données en lecture seule {#readonly-data}
<!-- vale on -->

Dans certaines situations, vous pouvez vouloir que votre application affiche des données sans permettre à l'utilisateur final de les modifier directement via l'UI. Les bindings de données en lecture seule répondent à cela. webforJ prend en charge la configuration des bindings en mode lecture seule, afin que vous puissiez afficher des données, mais sans les modifier via des composants UI liés.

### Configurer des bindings en lecture seule {#configuring-readonly-bindings}

Pour configurer un binding en lecture seule, vous pouvez configurer le binding pour désactiver ou ignorer l'entrée du composant UI. Les données restent alors inchangées du point de vue de l'UI, tout en étant mises à jour par programmation si nécessaire.

```java
// Configuration d'un champ de texte pour être en lecture seule dans le contexte de binding
BindingContext<Hero> context = new BindingContext<>(Hero.class);
context.bind(nameTextField, "name")
  .readOnly()
  .add();
```

Dans cette configuration, `readOnly` empêche le `nameTextField` d'accepter des entrées utilisateur, de sorte que le champ de texte affiche les données sans permettre de modifications.

:::info
Le binding peut marquer le composant comme en lecture seule uniquement si les composants UI implémentent l'interface `ReadOnlyAware`.
:::

:::tip Composant Lecture seule vs Binding Lecture seule
Il est important de faire la distinction entre les bindings que vous configurez en lecture seule et les composants UI que vous configurez pour s'afficher en lecture seule. Lorsque vous marquez un binding comme lecture seule, cela impacte la manière dont le binding gère les données lors du processus d'écriture, et pas seulement le comportement de l'UI.

Lorsque vous marquez un binding comme lecture seule, le système ignore les mises à jour des données. Toute modification du composant UI ne sera pas transmise au modèle de données. En conséquence, même si le composant UI reçoit d'une manière ou d'une autre une entrée utilisateur, il ne mettra pas à jour le modèle de données sous-jacent. Maintenir cette séparation protège l'intégrité des données dans les scénarios où les actions des utilisateurs ne devraient pas modifier les données.

En revanche, configurer un composant UI en lecture seule, sans configurer le binding lui-même en lecture seule, empêche simplement l'utilisateur de faire des modifications sur le composant UI, mais n'empêche pas le binding de mettre à jour le modèle de données si des changements se produisent par programmation ou par d'autres moyens.
:::

## Getters et Setters de Binding {#binding-getters-and-setters}

Les setters et getter sont des méthodes en Java qui définissent et obtiennent les valeurs des propriétés, respectivement. Dans le contexte du binding des données, ils sont utilisés pour définir comment les propriétés sont mises à jour et récupérées dans le cadre du framework de binding.

### Personnaliser les setters et getters {#customizing-setters-and-getters}

Bien que webforJ puisse utiliser automatiquement les conventions de nommage JavaBean standard (par exemple, `getName()`, `setName()` pour une propriété `name`), vous pourriez avoir besoin de définir un comportement personnalisé. Cela est nécessaire lorsque la propriété ne suit pas la convention de nommage ou lorsque la gestion des données nécessite une logique supplémentaire.

### Utiliser des getters personnalisés {#using-custom-getters}

Les getters personnalisés sont utilisés lorsque le processus de récupération de la valeur implique plus que de simplement renvoyer une propriété. Par exemple, vous pourriez vouloir formater la chaîne, calculer une valeur ou journaliser certaines actions lorsqu'une propriété est accédée.

```java
BindingContext<Hero> context = new BindingContext<>(Hero.class);
context
  .bind(textField, "power")
  .useGetter(hero -> {
    String name = hero.getName();
    return name.toUpperCase(); // Logique personnalisée: convertir le nom en majuscules
  });
```

### Utiliser des setters personnalisés {#using-custom-setters}

Les setters personnalisés entrent en jeu lorsque définir une propriété implique des opérations supplémentaires telles que la validation, la transformation ou des effets secondaires comme le logging ou la notification d'autres parties de votre application.

```java
BindingContext<Hero> context = new BindingContext<>(Hero.class);
context
  .bind(textField, "power")
  .useSetter((hero, name) -> {
    System.out.println("Mise à jour du nom de " + hero.getName() + " à " + name);
    hero.setName(name); // Opération supplémentaire: logging
  });
```
