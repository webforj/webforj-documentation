---
sidebar_position: 2
title: Bindings
_i18n_hash: c567705312942e83f5e83a77f1d510a4
---
Un binding dans webforJ relie une propriété spécifique d'un Java Bean à un composant UI. Ce lien permet des mises à jour automatiques entre l'interface utilisateur et le modèle backend. Chaque binding peut gérer la synchronisation des données, la validation, la transformation et la gestion des événements.

Vous pouvez initier des bindings uniquement par l'intermédiaire du `BindingContext`. Il gère une collection d'instances de binding, chacune reliant un composant UI à une propriété d'un bean. Il facilite les opérations groupées sur les bindings, telles que la validation et la synchronisation entre les composants UI et les propriétés du bean. Il agit comme un agrégateur, permettant des actions collectives sur plusieurs bindings, simplifiant ainsi la gestion du flux de données au sein des applications.

:::tip Binding Automatique
Cette section présente les bases de la configuration manuelle des bindings. De plus, vous pouvez créer automatiquement des bindings en fonction des composants UI de votre formulaire. Une fois que vous avez compris les fondamentaux, apprenez-en davantage en consultant la section [Binding Automatique](./automatic-binding).
:::

## Configurer des bindings {#configure-bindings}

Commencez par créer une nouvelle instance de `BindingContext` qui gère tous les bindings pour un modèle particulier. Ce contexte garantit que tous les bindings peuvent être validés et mis à jour collectivement.

```java
BindingContext<Hero> context = new BindingContext<>(Hero.class);
```

:::info
Chaque formulaire ne doit avoir qu'une seule instance de `BindingContext`, et vous devez utiliser cette instance pour tous les composants du formulaire.
:::

### La propriété liée {#the-bound-property}

Une propriété de binding est un champ ou attribut spécifique d'un Java Bean qui peut être lié à un composant UI dans votre application. 
Ce lien permet aux changements dans l'interface utilisateur d'affecter directement la propriété correspondante du modèle de données, et vice versa, 
facilitant une expérience utilisateur réactive.

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

  // setters and getters
}
```

La méthode `bind` retourne un `BindingBuilder` qui crée l'objet `Binding` et que vous pouvez utiliser pour configurer plusieurs paramètres du binding, la méthode `add` qui
est ce qui ajoute en fait le binding au contexte.

### Le composant lié {#the-bound-component}

L'autre côté du binding est le composant lié, qui fait référence au composant UI qui interagit avec la propriété du Java Bean. 
Le composant lié peut être n'importe quel composant UI qui prend en charge l'interaction de l'utilisateur et l'affichage, comme des champs de texte, des zones de sélection, des cases à cocher, ou 
tout composant personnalisé qui implémente l'interface `ValueAware`.

Le composant lié sert de point d'interaction pour l'utilisateur avec le modèle de données sous-jacent. 
Il affiche des données à l'utilisateur et capture également les saisies de l'utilisateur qui sont ensuite propagées de nouveau vers le modèle.

```java
TextField nameTextField = new TextField("Nom");
BindingContext<Hero> context = new BindingContext<>(Hero.class);
context.bind(nameTextField, "name").add();
```

## Lire et écrire des données {#reading-and-writing-data}

### Lire des données {#reading-data}

Lire des données implique de peupler les composants UI avec des valeurs provenant du modèle de données. 
Cela se fait généralement lorsque le formulaire est affiché pour la première fois, ou lorsque vous devez recharger les données en raison de changements dans le modèle sous-jacent. 
La méthode `read` fournie par `BindingContext` rend ce processus simple.

```java
// Supposons que l'objet Hero a été instancié et initialisé
Hero hero = new Hero("Clark Kent", "Vol");

// BindingContext est déjà configuré avec des bindings
context.read(hero);
```

Dans cet exemple, la méthode `read` prend une instance de `Hero` et met à jour tous les composants UI liés pour refléter les propriétés du héros. 
Si le nom ou le pouvoir du héros change, les composants UI correspondants (comme un `TextField` pour le nom et un `ComboBox` pour les pouvoirs) 
affichent ces nouvelles valeurs.

### Écrire des données {#writing-data}

Écrire des données implique de collecter des valeurs à partir des composants UI et de mettre à jour le modèle de données. 
Cela se produit généralement lorsqu'un utilisateur soumet un formulaire. La méthode `write` gère la validation et la mise à jour du modèle en une seule étape.

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

Dans le code ci-dessus, lorsque l'utilisateur clique sur le bouton de soumission, la méthode `write` est appelée. 
Elle effectue toutes les validations configurées et, si les données passent tous les contrôles, met à jour l'objet `Hero` 
avec les nouvelles valeurs des composants liés. 
Si les données sont valides, vous pouvez les enregistrer dans une base de données ou les traiter davantage. En cas d'erreurs de validation, 
vous devez gérer cela de manière appropriée, généralement en affichant des messages d'erreur à l'utilisateur.

:::tip Signalement des Erreurs de Validation
Tous les composants principaux de webforJ ont des configurations par défaut pour signaler automatiquement les erreurs de validation, soit en ligne, soit par le biais d'une popover. Vous pouvez personnaliser ce comportement en utilisant [Reporters](./validation/reporters.md).
:::

<!-- vale off -->
## Données en lecture seule {#readonly-data}
<!-- vale on -->

Dans certains scénarios, vous pouvez vouloir que votre application affiche des données sans permettre à l'utilisateur final de les modifier directement par l'interface utilisateur. 
C'est là que les bindings de données en lecture seule deviennent cruciaux. webforJ prend en charge la configuration de bindings pour qu'ils soient en lecture seule, garantissant que 
vous pouvez afficher des données, mais pas les modifier à travers des composants UI liés.

### Configurer des bindings en lecture seule {#configuring-readonly-bindings}

Pour configurer un binding en lecture seule, vous pouvez configurer le binding pour désactiver ou ignorer l'entrée du composant UI. 
Cela garantit que les données restent inchangées du point de vue de l'UI, tout en pouvant être mises à jour de manière programmatique si nécessaire.

```java
// Configuration d'un champ de texte pour être en lecture seule dans le contexte de binding
BindingContext<Hero> context = new BindingContext<>(Hero.class);
context.bind(nameTextField, "name")
  .readOnly()
  .add();
```

Dans cette configuration, `readOnly` garantit que le `nameTextField` n'accepte pas les entrées de l'utilisateur, rendant efficacement le champ de texte affichant 
les données sans permettre de modifications.

:::info
Le binding peut marquer le composant comme en lecture seule uniquement si les composants UI implémentent l'interface `ReadOnlyAware`.
:::

:::tip Composant en Lecture Seule vs Binding en Lecture Seule
Il est important de différencier entre les bindings que vous configurez comme en lecture seule et les composants UI que vous définissez pour s'afficher en tant qu'en lecture seule. 
Lorsque vous marquez un binding comme en lecture seule, cela impacte la manière dont le binding gère les données durant le processus d'écriture, et pas seulement le comportement UI.

Lorsque vous marquez un binding comme en lecture seule, le système ignore les mises à jour de données. Tout changement dans le composant UI ne sera pas transmis au modèle de données. 
Cela garantit que même si le composant UI reçoit d'une manière ou d'une autre une saisie utilisateur, il ne mettra pas à jour le modèle de données sous-jacent. 
Maintenir cette séparation est crucial pour préserver l'intégrité des données dans des scénarios où les actions de l'utilisateur ne devraient pas altérer les données.

En revanche, définir un composant UI comme en lecture seule, sans configurer le binding lui-même comme en lecture seule, arrête simplement l'utilisateur de faire des changements 
au composant UI mais ne stoppe pas le binding de mettre à jour le modèle de données si des changements se produisent de manière programmatique ou par d'autres moyens.
:::

## Getters et Setters de Binding {#binding-getters-and-setters}

Les setters et getters sont des méthodes en Java qui définissent et obtiennent les valeurs des propriétés, respectivement.
Dans le contexte du binding de données, ils sont utilisés pour définir comment les propriétés sont mises à jour et récupérées au sein du cadre de binding.

### Personnaliser les setters et getters {#customizing-setters-and-getters}

Bien que webforJ puisse utiliser automatiquement les conventions de nommage standard de JavaBean 
(par exemple, `getName()`, `setName()` pour une propriété `name`), vous pourriez avoir besoin de définir un comportement personnalisé.
Cela est nécessaire lorsque la propriété ne suit pas la convention de nommage ou lorsque le traitement des données nécessite une logique supplémentaire.

### Utiliser des getters personnalisés {#using-custom-getters}

Les getters personnalisés sont utilisés lorsque le processus de récupération de la valeur implique plus que simplement retourner une propriété.
Par exemple, vous pourriez vouloir formater la chaîne, calculer une valeur, ou enregistrer certaines actions lorsque une propriété est accédée.

```java
BindingContext<Hero> context = new BindingContext<>(Hero.class);
context
  .bind(textField, "power")
  .useGetter(hero -> {
    String name = hero.getName();
    return name.toUpperCase(); // Logique personnalisée : convertir le nom en majuscules
  });
```

### Utiliser des setters personnalisés {#using-custom-setters}

Les setters personnalisés entrent en jeu lorsque la définition d'une propriété implique des opérations supplémentaires telles que la validation, la transformation, ou des effets secondaires 
comme l'enregistrement ou la notification d'autres parties de votre application.

```java
BindingContext<Hero> context = new BindingContext<>(Hero.class);
context
  .bind(textField, "power")
  .useSetter((hero, name) -> {
    System.out.println("Mise à jour du nom de " + hero.getName() + " à " + name);
    hero.setName(name); // Opération supplémentaire : enregistrement
  });
```
