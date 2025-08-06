---
sidebar_position: 2
title: Bindings
_i18n_hash: fa6155c6e1eb2724d684d042f561c8a3
---
Un lien dans webforJ associe une propriété spécifique d'un Java Bean à un composant UI. Ce lien permet des mises à jour automatiques entre l'interface utilisateur et le modèle backend. Chaque liaison peut gérer la synchronisation des données, la validation, la transformation et la gestion des événements.

Vous pouvez initier des liaisons uniquement à travers le `BindingContext`. Il gère une collection d'instances de liaison, chacune reliant un composant UI à une propriété d'un bean. Il facilite les opérations de groupe sur les liaisons, telles que la validation et la synchronisation entre les composants UI et les propriétés du bean. Il agit comme un agrégateur, permettant des actions collectives sur plusieurs liaisons, simplifiant ainsi la gestion du flux de données au sein des applications.

:::tip Liaison Automatique
Cette section introduit les bases de la configuration manuelle des liaisons. De plus, vous pouvez créer automatiquement des liaisons en fonction des composants UI de votre formulaire. Une fois que vous saisissez les fondamentaux, apprenez-en plus en lisant la section [Liaison Automatique](./automatic-binding).
:::

## Configurer les liaisons {#configure-bindings}

Commencez par créer une nouvelle instance de `BindingContext` qui gère toutes les liaisons pour un modèle particulier. Ce contexte garantit que toutes les liaisons peuvent être validées et mises à jour collectivement.

```java
BindingContext<Hero> context = new BindingContext<>(Hero.class);
```

:::info
Chaque formulaire ne doit avoir qu'une seule instance de `BindingContext`, et vous devez utiliser cette instance pour tous les composants du formulaire.
:::

### La propriété liée {#the-bound-property}

Une propriété liée est un champ ou un attribut spécifique d'un Java Bean qui peut être associé à un composant UI dans votre application. Ce lien permet aux modifications dans l'UI d'affecter directement la propriété correspondante du modèle de données, et vice versa, facilitant une expérience utilisateur réactive.

Lors de la configuration d'une liaison, vous devez fournir le nom de la propriété sous forme de chaîne. Ce nom doit correspondre au nom du champ dans la classe Java Bean. Voici un exemple simple :

```java
BindingContext<Hero> context = new BindingContext<>(Hero.class, true);
context
    .bind(textField, "power")
    .add();
```

```java
public class Hero  {
  private String name;
  private String power;

  // setters and getters
}
```

Le méthode `bind` retourne un `BindingBuilder` qui crée l'objet `Binding` et que vous pouvez utiliser pour configurer plusieurs réglages de la liaison, la méthode `add` ajoutant effectivement la liaison au contexte.

### Le composant lié {#the-bound-component}

L'autre côté de la liaison est le composant lié, qui fait référence au composant UI qui interagit avec la propriété du Java Bean. Le composant lié peut être n'importe quel composant UI qui prend en charge l'interaction de l'utilisateur et l'affichage, tels que des champs de texte, des boîtes de sélection, des cases à cocher, ou tout composant personnalisé qui implémente l'interface `ValueAware`.

Le composant lié sert de point d'interaction pour l'utilisateur avec le modèle de données sous-jacent. Il affiche des données à l'utilisateur et capture également les entrées de l'utilisateur qui sont ensuite propagées de nouveau au modèle.

```java
TextField nameTextField = new TextField("Nom");
BindingContext<Hero> context = new BindingContext<>(Hero.class);
context.bind(nameTextField, "name").add();
```

## Lecture et écriture de données {#reading-and-writing-data}

### Lecture de données {#reading-data}

La lecture des données consiste à peupler les composants UI avec des valeurs du modèle de données. Cela se fait généralement lorsque le formulaire est affiché pour la première fois, ou lorsque vous devez recharger les données en raison de changements dans le modèle sous-jacent. La méthode `read` fournie par `BindingContext` rend ce processus assez simple.

```java
// Supposons que l'objet Hero a été instancié et initialisé
Hero hero = new Hero("Clark Kent", "Vol");

// BindingContext est déjà configuré avec des liaisons
context.read(hero);
```

Dans cet exemple, la méthode `read` prend une instance de `Hero` et met à jour tous les composants UI liés pour refléter les propriétés du héros. Si le nom ou le pouvoir du héros change, les composants UI correspondants (comme un `TextField` pour le nom et un `ComboBox` pour les pouvoirs) affichent ces nouvelles valeurs.

### Écriture de données {#writing-data}

L'écriture de données consiste à collecter des valeurs à partir des composants UI et à mettre à jour le modèle de données. Cela se produit généralement lorsqu'un utilisateur soumet un formulaire. La méthode `write` gère la validation et la mise à jour du modèle en une seule étape.

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
avec de nouvelles valeurs des composants liés. Si les données sont valides, vous pouvez les enregistrer dans une base de données ou les traiter davantage. S'il y a des erreurs de validation, vous devez gérer cela de manière appropriée, généralement en affichant des messages d'erreur à l'utilisateur.

:::tip Reporting des Erreurs de Validation
Tous les composants principaux de webforJ ont des configurations par défaut pour signaler automatiquement les erreurs de validation, soit en ligne, soit par le biais d'une fenêtre contextuelle. Vous pouvez personnaliser ce comportement à l'aide des [Reporters](./validation/reporters.md).
:::

<!-- vale off -->
## Données en Lecture Seule {#readonly-data}
<!-- vale on -->

Dans certaines situations, vous pourriez vouloir que votre application affiche des données sans permettre à l'utilisateur final de les modifier directement à travers l'UI. C'est ici que les liaisons de données en lecture seule deviennent cruciales. webforJ prend en charge la configuration des liaisons pour être en lecture seule, garantissant que vous pouvez afficher des données, mais ne pas les modifier à travers les composants UI liés.

### Configuration des liaisons en lecture seule {#configuring-readonly-bindings}

Pour configurer une liaison en lecture seule, vous pouvez paramétrer la liaison pour désactiver ou ignorer l'entrée du composant UI. Cela garantit que les données restent inchangées du point de vue de l'UI, tout en étant mises à jour programmétiquement si besoin.

```java
// Configuration d'un champ de texte pour être en lecture seule dans le contexte de liaison
BindingContext<Hero> context = new BindingContext<>(Hero.class);
context.bind(nameTextField, "name")
    .readOnly()
    .add();
```

Dans cette configuration, `readOnly` garantit que le `nameTextField` n'accepte pas d'entrée de l'utilisateur, rendant ainsi le champ de texte affiché 
les données sans permettre les modifications.

:::info
La liaison peut marquer le composant comme en lecture seule uniquement si les composants UI implémentent l'interface `ReadOnlyAware`.
:::

:::tip Lecture Seule du Composant vs Lecture Seule de la Liaison
Il est important de différencier entre les liaisons que vous configurez comme en lecture seule et les composants UI que vous définissez pour s'afficher en lecture seule. 
Lorsque vous marquez une liaison comme en lecture seule, cela impacte la manière dont la liaison gère les données lors du processus d'écriture, et pas seulement le comportement de l'UI.

Lorsque vous marquez une liaison comme en lecture seule, le système ignore les mises à jour de données. Les modifications apportées au composant UI ne se transmettent pas au modèle de données. 
Cela garantit que même si le composant UI reçoit d'une manière ou d'une autre une entrée de l'utilisateur, il ne met pas à jour le modèle de données sous-jacent. 
Maintenir cette séparation est crucial pour préserver l'intégrité des données dans les scénarios où les actions de l'utilisateur ne devraient pas altérer les données.

En revanche, définir un composant UI comme en lecture seule, sans configurer la liaison elle-même comme en lecture seule, arrête simplement l'utilisateur de faire des changements 
au composant UI mais ne stoppe pas la liaison de mettre à jour le modèle de données si des modifications se produisent par programmation ou par d'autres moyens.
:::

## Getters et Setters de Liaison {#binding-getters-and-setters}

Les setters et getters sont des méthodes en Java qui définissent et retournent les valeurs des propriétés, respectivement. Dans le contexte de la liaison de données, ils sont utilisés pour définir comment les propriétés sont mises à jour et récupérées au sein du cadre de liaison.

### Personnaliser les setters et getters {#customizing-setters-and-getters}

Bien que webforJ puisse automatiquement utiliser les conventions de nommage standard de JavaBean
(par exemple, `getName()`, `setName()` pour une propriété `name`), vous pourriez avoir besoin de définir un comportement personnalisé.
Ceci est nécessaire lorsque la propriété ne suit pas la convention de nommage ou lorsque le traitement des données nécessite une logique supplémentaire.

### Utiliser des getters personnalisés {#using-custom-getters}

Les getters personnalisés sont utilisés lorsque le processus de récupération de la valeur implique plus que de simplement retourner une propriété.
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

Les setters personnalisés interviennent lorsque la définition d'une propriété implique des opérations supplémentaires telles que la validation, la transformation, ou des effets secondaires
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
