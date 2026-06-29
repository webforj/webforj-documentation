---
sidebar_position: 2
title: Bindings
sidebar_class_name: updated-content
description: >-
  Link Java Bean properties to webforJ UI components through BindingContext to
  synchronize reads and writes between model and view.
_i18n_hash: 047676a64833283bcc160d7a8d226559
---
Un binding dans webforJ lie une propriété spécifique d'un Java Bean à un composant UI. Cette liaison permet des mises à jour automatiques entre l'interface utilisateur et le modèle backend. Chaque binding peut gérer la synchronisation des données, la validation, la transformation et la gestion des événements.

Vous pouvez initier des bindings uniquement via le `BindingContext`. Il gère une collection d'instances de binding, chacune liant un composant UI à une propriété d'un bean. Il facilite les opérations groupées sur les bindings, telles que la validation et la synchronisation entre les composants UI et les propriétés du bean. Il agit comme un agrégateur, permettant des actions collectives sur plusieurs bindings, simplifiant ainsi la gestion du flux de données au sein des applications.

:::tip Liaison automatique
Cette section présente les bases de la configuration manuelle des bindings. De plus, vous pouvez créer automatiquement des bindings basés sur les composants UI de votre formulaire. Une fois que vous avez compris les fondamentaux, apprenez-en plus en lisant la section [Liaison automatique](/docs/data-binding/automatic-binding).
:::

## Configurer les bindings {#configure-bindings}

Commencez par créer une nouvelle instance de `BindingContext` qui gère tous les bindings pour un modèle particulier. Ce contexte valide et met à jour tous les bindings collectivement.

```java
BindingContext<Hero> context = new BindingContext<>(Hero.class);
```

:::info
Chaque formulaire ne doit avoir qu'une seule instance de `BindingContext`, et vous devez utiliser cette instance pour tous les composants du formulaire.
:::

### La propriété liée {#the-bound-property}

Une propriété de binding est un champ ou un attribut spécifique d'un Java Bean qui peut être lié à un composant UI dans votre application. Cette liaison permet aux changements dans l'UI d'affecter directement la propriété correspondante du modèle de données, et vice versa, de sorte que l'UI et le modèle de données restent synchronisés.

Lors de la configuration d'un binding, vous devez fournir le nom de la propriété sous forme de chaîne. Ce nom doit correspondre au nom du champ dans la classe Java Bean. Voici un exemple simple :

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

la méthode `bind` retourne un `BindingBuilder` qui crée l'objet `Binding` et que vous pouvez utiliser pour configurer le binding avec plusieurs paramètres, la méthode `add` qui est ce qui ajoute réellement le binding au contexte.

### Le composant lié {#the-bound-component}

L'autre côté du binding est le composant lié, qui se réfère au composant UI qui interagit avec la propriété du Java Bean. Le composant lié peut être n'importe quel composant UI qui supporte l'interaction et l'affichage de l'utilisateur, comme des champs de texte, des listes déroulantes, des cases à cocher, ou tout composant personnalisé qui implémente l'interface `ValueAware`.

Le composant lié sert de point d'interaction pour l'utilisateur avec le modèle de données sous-jacent. Il affiche des données à l'utilisateur et capture également les entrées de l'utilisateur qui sont ensuite propagées au modèle.

```java
TextField nameTextField = new TextField("Nom");
BindingContext<Hero> context = new BindingContext<>(Hero.class);
context.bind(nameTextField, "name").add();
```

## Lecture et écriture des données {#reading-and-writing-data}

### Lecture des données {#reading-data}

Lire des données implique de peupler les composants UI avec des valeurs du modèle de données. Cela se fait généralement lorsque le formulaire est initialement affiché, ou lorsque vous devez recharger les données en raison de changements dans le modèle sous-jacent. La méthode `read` fournie par `BindingContext` rend ce processus simple.

```java
// Supposer que l'objet Hero a été instancié et initialisé
Hero hero = new Hero("Clark Kent", "Vol");

context.read(hero);
```

Dans cet exemple, la méthode `read` prend une instance de `Hero` et met à jour tous les composants UI liés pour refléter les propriétés du héros. Si le nom ou le pouvoir du héros change, les composants UI correspondants (comme un `TextField` pour le nom et un `ComboBox` pour les pouvoirs) affichent ces nouvelles valeurs.

### Écriture des données {#writing-data}

Écrire des données implique de recueillir des valeurs des composants UI et de mettre à jour le modèle de données. Cela se produit généralement lorsqu'un utilisateur soumet un formulaire. La méthode `write` gère la validation et la mise à jour du modèle en une seule étape.

```java
// Cela pourrait être déclenché par un événement de soumission de formulaire
submit.onClick(event -> {
  ValidationResult results = context.write(hero);
  if (results.isValid()) {
    // Les données sont valides, et l'objet hero a été mis à jour
    // repository.save(hero); 
  } else {
    // Gérer les erreurs de validation
    // results.getMessages();
  }
});
```

Dans le code ci-dessus, lorsque l'utilisateur clique sur le bouton de soumission, la méthode `write` est appelée. Elle effectue toutes les validations configurées et, si les données passent tous les contrôles, met à jour l'objet `Hero` avec de nouvelles valeurs provenant des composants liés. Si les données sont valides, vous pouvez sauvegarder dans une base de données ou les traiter davantage. S'il y a des erreurs de validation, vous devez les gérer de manière appropriée, généralement en affichant des messages d'erreur à l'utilisateur.

:::tip Rapport d'erreurs de validation
Tous les composants de base de webforJ ont des configurations par défaut pour signaler automatiquement les erreurs de validation, soit en ligne, soit via un popover. Vous pouvez personnaliser ce comportement en utilisant [Reporters](./validation/reporters.md).
:::

## Propriétés de bean imbriquées <DocChip chip='since' label='26.01' /> {#nested-bean-properties}

Une propriété de binding peut être un chemin avec des points qui pointe vers une propriété à l'intérieur d'un bean imbriqué. Chaque segment dans le chemin suit les conventions de getter et setter standard de JavaBean, donc `address.street` est lu via `getAddress().getStreet()` et écrit via `getAddress().setStreet()`.

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

Lors de la lecture, un chemin se résout en toute sécurité même lorsqu'un bean intermédiaire est `null`. Si un `Hero` n'a pas d'`Address`, les composants liés à `address.street` et `address.city` se lisent comme vides au lieu de générer une erreur, de sorte que le formulaire se peuple toujours.

Lors de l'écriture, le contexte crée n'importe quel bean intermédiaire manquant via son constructeur sans argument, ce qui signifie qu'écrire le formulaire dans un `Hero` sans `Address` produit un nouvel `Address` peuplé. Un `Address` existant est réutilisé.

Les annotations de [validation Jakarta](/docs/data-binding/validation/jakarta-validation) sur une propriété imbriquée sont détectées de la même manière que sur une propriété de premier niveau. Une annotation telle que `@NotNull` sur `Address.street` marque le binding `address.street` comme [required](/docs/data-binding/automatic-binding#bindingrequired-annotation).

:::info Les chemins sont validés à l'avance
Le chemin complet est validé lorsque vous appelez `bind`. Une erreur typographique dans n'importe quel segment, au niveau supérieur ou plus profondément dans le chemin, lance une `IllegalArgumentException`, les erreurs de liaison apparaissent donc immédiatement plutôt qu'au moment de la lecture ou de l'écriture.
:::

<!-- vale off -->
## Données en lecture seule {#readonly-data}
<!-- vale on -->

Dans certaines situations, vous pouvez vouloir que votre application affiche des données sans permettre à l'utilisateur final de les modifier directement via l'interface utilisateur. Les bindings de données en lecture seule répondent à ce besoin. webforJ prend en charge la configuration des bindings comme étant en lecture seule, afin que vous puissiez afficher des données, mais pas les modifier via des composants UI liés.

### Configurer des bindings en lecture seule {#configuring-readonly-bindings}

Pour configurer un binding en lecture seule, vous pouvez configurer le binding pour désactiver ou ignorer les entrées des composants UI. Les données restent alors inchangées du point de vue de l'UI, tout en se mettant à jour par programme lorsque cela est nécessaire.

```java
// Configuration d'un champ de texte pour être en lecture seule dans le contexte de binding
BindingContext<Hero> context = new BindingContext<>(Hero.class);
context.bind(nameTextField, "name")
  .readOnly()
  .add();
```

Dans cette configuration, `readOnly` empêche le `nameTextField` d'accepter les entrées de l'utilisateur, donc le champ de texte affiche les données sans permettre de modifications.

:::info
Le binding peut marquer le composant comme étant en lecture seule uniquement si les composants UI implémentent l'interface `ReadOnlyAware`.
:::

:::tip Composant en lecture seule vs Binding en lecture seule
Il est important de différencier entre les bindings que vous configurez comme étant en lecture seule et les composants UI que vous définissez pour s'afficher comme en lecture seule. Lorsque vous marquez un binding comme étant en lecture seule, cela impacte la manière dont le binding gère les données lors du processus d'écriture, et pas seulement le comportement de l'UI.

Lorsque vous marquez un binding comme étant en lecture seule, le système ignore les mises à jour de données. Toute modification du composant UI ne sera pas transmise au modèle de données. En conséquence, même si le composant UI reçoit d'une manière ou d'une autre une entrée utilisateur, cela ne mettra pas à jour le modèle de données sous-jacent. Maintenir cette séparation protège l'intégrité des données dans les scénarios où les actions des utilisateurs ne devraient pas altérer les données.

En revanche, définir un composant UI comme étant en lecture seule, sans configurer le binding lui-même comme étant en lecture seule, arrête simplement l'utilisateur de modifier le composant UI, mais n'empêche pas le binding de mettre à jour le modèle de données si des modifications se produisent par programme ou par d'autres moyens.
:::

## Getters et setters de binding {#binding-getters-and-setters}

Les setters et les getters sont des méthodes en Java qui définissent et récupèrent respectivement les valeurs des propriétés. Dans le contexte du binding de données, ils sont utilisés pour définir comment les propriétés sont mises à jour et récupérées dans le cadre du framework de binding.

### Personnaliser les setters et les getters {#customizing-setters-and-getters}

Bien que webforJ puisse utiliser automatiquement les conventions de nommage standard de JavaBean (par exemple, `getName()`, `setName()` pour une propriété `name`), vous pourriez avoir besoin de définir un comportement personnalisé. Cela est nécessaire lorsque la propriété ne suit pas la convention de nommage ou lorsque le traitement des données nécessite une logique supplémentaire.

### Utilisation de getters personnalisés {#using-custom-getters}

Les getters personnalisés sont utilisés lorsque le processus de récupération de la valeur implique plus que le simple retour d'une propriété. Par exemple, vous pourriez vouloir formater une chaîne, calculer une valeur, ou journaliser certaines actions lorsqu'une propriété est accédée.

```java
BindingContext<Hero> context = new BindingContext<>(Hero.class);
context
  .bind(textField, "power")
  .useGetter(hero -> {
    String name = hero.getName();
    return name.toUpperCase(); // Logique personnalisée : convertir le nom en majuscules
  });
```

### Utilisation de setters personnalisés {#using-custom-setters}

Les setters personnalisés entrent en jeu lorsque définir une propriété implique des opérations supplémentaires telles que la validation, la transformation, ou des effets secondaires comme la journalisation ou la notification d'autres parties de votre application.

```java
BindingContext<Hero> context = new BindingContext<>(Hero.class);
context
  .bind(textField, "power")
  .useSetter((hero, name) -> {
    System.out.println("Mise à jour du nom de " + hero.getName() + " à " + name);
    hero.setName(name); // Opération supplémentaire : journalisation
  });
```
