---
sidebar_position: 2
title: Bindings
_i18n_hash: 0afea0971d509f25324b46172b5e020e
---
Un lien dans webforJ relie une propriété spécifique d'un Java Bean à un composant UI. Ce lien permet des mises à jour automatiques entre l'UI et le modèle backend. Chaque lien peut gérer la synchronisation des données, la validation, la transformation et la gestion des événements.

Vous pouvez initier des liens uniquement par l'intermédiaire du `BindingContext`. Il gère une collection d'instances de liaison, chacune reliant un composant UI à une propriété d'un bean. Il facilite les opérations groupées sur les liaisons, telles que la validation et la synchronisation entre les composants UI et les propriétés du bean. Il agit comme un agrégateur, permettant des actions collectives sur plusieurs liaisons, facilitant ainsi la gestion du flux de données au sein des applications.

:::tip Liaison Automatique
Cette section introduit les bases de la configuration manuelle des liaisons. De plus, vous pouvez créer automatiquement des liaisons en fonction des composants UI de votre formulaire. Une fois que vous maitrisez les fondamentaux, apprenez-en plus en lisant la section [Liaison Automatique](./automatic-binding).
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

Une propriété liée est un champ ou un attribut spécifique d'un Java Bean qui peut être relié à un composant UI de votre application. Ce lien permet aux changements dans l'UI d'affecter directement la propriété correspondante du modèle de données, et vice versa, facilitant une expérience utilisateur réactive.

Lors de la configuration d'une liaison, vous devez fournir le nom de la propriété sous forme de chaîne. Ce nom doit correspondre au nom du champ dans la classe Java Bean. Voici un exemple simple :

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

La méthode `bind` renvoie un `BindingBuilder` qui crée l'objet `Binding` et vous permet de configurer la liaison avec plusieurs paramètres, la méthode `add` étant celle qui ajoute réellement la liaison au contexte.

### Le composant lié {#the-bound-component}

L'autre côté de la liaison est le composant lié, qui fait référence au composant UI qui interagit avec la propriété du Java Bean. Le composant lié peut être n'importe quel composant UI qui supporte l'interaction avec l'utilisateur et l'affichage, comme des champs de texte, des listes déroulantes, des cases à cocher, ou tout composant personnalisé qui implémente l'interface `ValueAware`.

Le composant lié sert de point d'interaction pour l'utilisateur avec le modèle de données sous-jacent. Il affiche des données à l'utilisateur et capture également les saisies utilisateur qui sont ensuite renvoyées au modèle.

```java
TextField nameTextField = new TextField("Name");
BindingContext<Hero> context = new BindingContext<>(Hero.class);
context.bind(nameTextField, "name").add();
```

## Lecture et écriture des données {#reading-and-writing-data}

### Lecture des données {#reading-data}

Lire des données implique de peupler les composants UI avec des valeurs provenant du modèle de données. Cela se fait généralement lorsque le formulaire est affiché pour la première fois, ou lorsque vous devez recharger les données en raison de modifications dans le modèle sous-jacent. La méthode `read` fournie par `BindingContext` rend ce processus simple.

```java
// Supposons que l'objet Hero a été instancié et initialisé
Hero hero = new Hero("Clark Kent", "Flying");

// BindingContext est déjà configuré avec des liaisons
context.read(hero);
```

Dans cet exemple, la méthode `read` prend une instance de `Hero` et met à jour tous les composants UI liés pour refléter les propriétés du héros. Si le nom ou le pouvoir du héros change, les composants UI correspondants (comme un `TextField` pour le nom et un `ComboBox` pour les pouvoirs) affichent ces nouvelles valeurs.

### Écriture des données {#writing-data}

Écrire des données implique de collecter des valeurs des composants UI et de mettre à jour le modèle de données. Cela se produit généralement lorsqu'un utilisateur soumet un formulaire. La méthode `write` gère la validation et la mise à jour du modèle en une seule étape.

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

Dans le code ci-dessus, lorsque l'utilisateur clique sur le bouton de soumission, la méthode `write` est appelée. Elle effectue toutes les validations configurées et, si les données passent tous les contrôles, met à jour l'objet `Hero` avec de nouvelles valeurs provenant des composants liés. Si les données sont valides, vous pouvez les enregistrer dans une base de données ou les traiter davantage. S'il y a des erreurs de validation, vous devez les gérer de manière appropriée, généralement en affichant des messages d'erreur à l'utilisateur.

:::tip Rapport d'erreurs de validation
Tous les composants de base de webforJ ont des configurations par défaut pour signaler automatiquement les erreurs de validation, soit en ligne, soit par le biais d'une infobulle. Vous pouvez personnaliser ce comportement à l'aide de [Reporters](./validation/reporters.md).
:::

<!-- vale off -->
## Données en lecture seule {#readonly-data}
<!-- vale on -->

Dans certaines scénarios, vous pouvez vouloir que votre application affiche des données sans permettre à l'utilisateur final de les modifier directement via l'UI. C'est là que les liaisons de données en lecture seule deviennent cruciales. webforJ permet de configurer des liaisons pour qu'elles soient en lecture seule, garantissant que vous pouvez afficher des données, mais pas les modifier via des composants UI liés.

### Configuration des liaisons en lecture seule {#configuring-readonly-bindings}

Pour mettre en place une liaison en lecture seule, vous pouvez configurer la liaison pour désactiver ou ignorer l'entrée des composants UI. Cela garantit que les données restent inchangées du point de vue de l'UI, tout en étant mises à jour par programmation si nécessaire.

```java
// Configuration d'un champ de texte pour être en lecture seule dans le contexte de liaison
BindingContext<Hero> context = new BindingContext<>(Hero.class);
context.bind(nameTextField, "name")
    .readOnly()
    .add();
```

Dans cette configuration, `readOnly` garantit que le `nameTextField` n'accepte pas les saisies utilisateur, rendant effectivement le champ de texte capable d'afficher les données sans permettre de modifications.

:::info
La liaison peut marquer le composant comme en lecture seule uniquement si le composant UI implémente l'interface `ReadOnlyAware`.
:::

:::tip Composant en Lecture Seule vs Liaison en Lecture Seule
Il est important de différencier entre les liaisons que vous configurez comme en lecture seule et les composants UI que vous définissez comme affichant en lecture seule. Lorsque vous marquez une liaison comme en lecture seule, cela impacte la manière dont la liaison gère les données lors du processus d'écriture, et pas seulement le comportement de l'UI.

Lorsque vous marquez une liaison comme en lecture seule, le système saute les mises à jour de données. Toute modification du composant UI ne sera pas renvoyée au modèle de données. Cela garantit que même si le composant UI reçoit d'une manière ou d'une autre des entrées utilisateur, il ne mettra pas à jour le modèle de données sous-jacent. Maintenir cette séparation est crucial pour préserver l'intégrité des données dans des scénarios où les actions de l'utilisateur ne devraient pas altérer les données.

En revanche, définir un composant UI comme en lecture seule, sans configurer la liaison elle-même comme en lecture seule, arrête simplement l'utilisateur de faire des modifications au composant UI mais n'empêche pas la liaison de mettre à jour le modèle de données si des changements se produisent par programmation ou par d'autres moyens.
:::

## Accessoires et mutateurs de liaison {#binding-getters-and-setters}

Les mutateurs et accessoires sont des méthodes en Java qui définissent et obtiennent les valeurs des propriétés, respectivement. Dans le contexte de la liaison de données, ils sont utilisés pour définir comment les propriétés sont mises à jour et récupérées dans le cadre de la framework de liaison.

### Personnalisation des mutateurs et accessoires {#customizing-setters-and-getters}

Bien que webforJ puisse automatiquement utiliser les conventions de nommage standards de JavaBean (par exemple, `getName()`, `setName()` pour une propriété `name`), vous pourriez avoir besoin de définir un comportement personnalisé. Cela est nécessaire lorsque la propriété ne suit pas la convention de nommage ou lorsque la gestion des données nécessite une logique supplémentaire.

### Utilisation d'accessoires personnalisés {#using-custom-getters}

Les accessoires personnalisés sont utilisés lorsque le processus de récupération de valeur implique plus que simplement retourner une propriété. Par exemple, vous pourriez vouloir formater la chaîne, calculer une valeur ou consigner certaines actions lorsque la propriété est accédée.

```java
BindingContext<Hero> context = new BindingContext<>(Hero.class);
context
    .bind(textField, "power")
    .useGetter(hero -> {
        String name = hero.getName();
        return name.toUpperCase(); // Logique personnalisée : convertir le nom en majuscule
    });
```

### Utilisation de mutateurs personnalisés {#using-custom-setters}

Les mutateurs personnalisés entrent en jeu lorsque la définition d'une propriété implique des opérations supplémentaires telles que la validation, la transformation ou des effets secondaires comme la consignation ou la notification d'autres parties de votre application.

```java
BindingContext<Hero> context = new BindingContext<>(Hero.class);
context
    .bind(textField, "power")
    .useSetter((hero, name) -> {
        System.out.println("Mise à jour du nom de " + hero.getName() + " à " + name);
        hero.setName(name); // Opération supplémentaire : consignation
    });
```
