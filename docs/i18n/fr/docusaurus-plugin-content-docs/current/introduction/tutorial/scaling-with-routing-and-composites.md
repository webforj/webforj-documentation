---
title: Scalabilité avec le Routage et les Composites
sidebar_position: 4
_i18n_hash: 50cd3b00cb1fb7731b6328708d6d45ba
---
Cette étape se concentre sur la mise en œuvre du routage pour améliorer l'évolutivité et l'organisation de la structure de l'application. Pour y parvenir, l'application sera mise à jour pour gérer plusieurs vues, permettant la navigation entre différentes fonctionnalités telles que l'édition et la création d'entrées de clients. Elle décrira la création de vues pour ces fonctionnalités, en utilisant des composants comme `Composite` pour construire des mises en page modulaires et réutilisables.

L'application créée à l'[étape précédente](./working-with-data) disposera d'une configuration de routage qui prend en charge plusieurs vues, permettant aux utilisateurs de gérer les données des clients plus efficacement tout en maintenant une base de code propre et évolutive. Pour exécuter l'application :

- Allez dans le répertoire `3-scaling-with-routing-and-composites`
- Exécutez la commande `mvn jetty:run`

<div class="videos-container">
  <video controls>
    <source src="https://cdn.webforj.com/webforj-documentation/video/tutorials/scaling-with-routing-and-composites.mp4" type="video/mp4"/>
  </video>
</div>

## Routage {#routing}

Le [routage](../../routing/overview) est le mécanisme qui permet à votre application de gérer la navigation entre différentes vues ou pages. Au lieu de garder toute la logique et le comportement à un seul endroit, le routage vous permet de décomposer votre application en composants plus petits et ciblés.

Au cœur de ça, le routage relie des URL spécifiques aux vues ou composants qui gèrent ces URL. Lorsqu'un utilisateur interagit avec votre application—par exemple en cliquant sur un bouton ou en entrant directement une URL dans son navigateur—le routeur résout l'URL vers la vue appropriée, l'initialise et l'affiche à l'écran. Cette approche facilite la gestion de la navigation et le maintien de l'état de l'application.

Cette étape se concentre sur la modification de la classe `App`, la création de fichiers pour les vues et la configuration des routes pour permettre une navigation fluide entre les différentes parties de votre application.

Au lieu de placer toute la logique dans la méthode `run()` de `App`, des vues comme `DemoView` et `FormView` sont mises en œuvre en tant que classes séparées. Cette approche est plus en phase avec les pratiques standard en Java.

- **DemoView** : Gère l'affichage du tableau et la navigation vers `FormView`.
- **FormView** : Gère l'ajout et l'édition des données des clients.

### Changer la classe `App` {#changing-the-app-class}

Pour activer le routage dans votre application, mettez à jour la classe `App` avec l'annotation `@Routify`. Cela dit à webforJ d'activer le routage et de scanner les packages spécifiés pour les vues compatibles avec le routage.

```java title="DemoApplication.java" {1}
@Routify(packages = "com.webforj.demos.views", debug = true)
public class DemoApplication extends App {  
}
```

- **`packages`** : Spécifie quels packages sont scannés pour les vues qui définissent des routes.
- **`debug`** : Active le mode débogage pour un dépannage plus facile pendant le développement.

### Créer des fichiers pour les vues et configurer les routes {#creating-files-for-the-views-and-configuring-routes}

Une fois le routage activé, des fichiers Java séparés pour chaque vue que l'application contiendra sont créés, dans ce cas `DemoView.java` et `FormView.java`. Des routes uniques sont assignées à ces vues en utilisant l'annotation `@Route`. Cela garantit que chaque vue est accessible via une URL spécifique.

Lorsque l'annotation `@Route` associée à une classe avec l'un de ces suffixes n'a pas de valeur, webforJ attribue automatiquement le nom de la classe sans le suffixe comme route. Par exemple, `DemoView` mappera par défaut la route `/demo`. Comme dans ce cas `DemoView` est censée être la route par défaut, vous lui assignerez une route.

La route `/` sert de point d'entrée par défaut pour votre application. L'assignation de cette route à une vue garantit qu'elle est la première page que les utilisateurs voient en accédant à l'application. Dans la plupart des cas, un tableau de bord ou une vue de synthèse est assigné à `/`.

```java title="DemoView.java" {1}
@Route("/")
@FrameTitle("Démonstration")
public class DemoView extends Composite<Div> {
  // Logique de DemoView
}
```

:::info 
Plus d'informations concernant les différents types de routes est disponible [ici](../../routing/defining-routes).
:::

Pour le `FormView`, la route `customer/:id?` utilise un paramètre optionnel `id` pour déterminer le mode de `FormView`. 

- **Mode Ajout** : Lorsque `id` n'est pas fourni, `FormView` s'initialise avec un formulaire vierge pour ajouter de nouvelles données clients.
- **Mode Édition** : Lorsque `id` est fourni, `FormView` récupère les données du client correspondant en utilisant `Service` et pré-remplit le formulaire, permettant des modifications sur l'entrée existante.

```java title="FormView.java" {1}
@Route("customer/:id?")
@FrameTitle("Formulaire Client")
public class FormView extends Composite<Div> implements DidEnterObserver {
  // Logique de FormView
}
```

:::info 
Plus d'informations concernant les différentes façons d'implémenter ces modèles de route est disponible [ici](../../routing/route-patterns).
:::

## Utilisation des composants `Composite` pour afficher des pages {#using-composite-components-to-display-pages}

Les composants composites dans webforJ, tels que `Composite<Div>`, vous permettent d'encapsuler la logique et la structure de l'interface utilisateur dans un conteneur réutilisable. En étendant `Composite`, vous limitez les méthodes et les données exposées au reste de l'application, garantissant un code plus propre et une meilleure encapsulation.

Par exemple, `DemoView` étend `Composite<Div>` au lieu d'étendre directement `Div` :

```java title="DemoView.java"
public class DemoView extends Composite<Div> {
  private Table<Customer> table = new Table<>();
  private Button add = new Button("Ajouter Client", ButtonTheme.PRIMARY);  

  public DemoView() {
    setupLayout();
  }

  private void setupLayout() {
    FlexLayout layout = FlexLayout.create(table, add)
        .vertical().contentAlign().center().build().setPadding("var(--dwc-space-l)");
    getBoundComponent().add(layout);
  }
}
```

## Connecter les routes {#connecting-the-routes}

Après avoir configuré le routage et mis en place les vues, connectez les vues et les données à l'aide d'écouteurs d'événements et de méthodes de service. La première étape consiste à ajouter un ou plusieurs éléments d'interface utilisateur pour naviguer d'une vue à l'autre.

### Navigation par bouton {#button-navigation}

Le composant `Button` déclenche un événement de navigation pour passer d'une vue à une autre en utilisant la classe `Router`. Par exemple :

```java title="DemoView.java"
private Button add = new Button("Ajouter Client", ButtonTheme.PRIMARY,
  e -> Router.getCurrent().navigate(FormView.class));
```

:::info
La classe Router utilise la classe donnée pour résoudre la route et construire une URL vers laquelle naviguer. Toute la navigation du navigateur est ensuite gérée, de sorte que la gestion de l'historique et l'initialisation de la vue ne sont pas une préoccupation.
Pour plus de détails sur la navigation, consultez l'[Article sur la Navigation de Route](../../routing/route-navigation).
:::

### Édition de tableau {#table-editing}

En plus de la navigation via un clic sur le bouton, de nombreuses applications permettent également la navigation vers d'autres parties d'une application lorsqu'un `Table` est double-cliqué. Les modifications suivantes sont apportées pour permettre aux utilisateurs de double-cliquer sur un élément dans le tableau pour naviguer vers un formulaire pré-rempli avec les détails de l'élément.

Une fois que les détails ont été modifiés sur l'écran approprié, les modifications sont enregistrées, et le `Table` est mis à jour pour afficher les données modifiées de l'élément sélectionné.

Pour faciliter cette navigation, les clics sur les éléments dans le tableau sont gérés par l'écouteur `TableItemClickEvent<Customer>`. L'événement contient l'`id` du client cliqué, qu'il passe à la `FormView` en utilisant la méthode `navigate()` avec un `ParametersBag` :

```java title="DemoView.java" 
private void editCustomer(TableItemClickEvent<Customer> e) {
  Router.getCurrent().navigate(FormView.class,
    ParametersBag.of("id=" + e.getItemKey()));
}
```

### Gestion de l'initialisation avec `onDidEnter` {#handling-initialization-with-ondidenter}

La méthode `onDidEnter` dans webforJ fait partie du cycle de vie du routage et est déclenchée lorsqu'une vue devient active. 

Lorsque le `Router` navigue vers une vue, `onDidEnter` est déclenché dans le cadre du cycle de vie pour :
- **Charger des données** : Initialiser ou récupérer les données requises pour la vue en fonction des paramètres de route.
- **Configurer la vue** : Mettre à jour dynamiquement les éléments de l'interface utilisateur en fonction du contexte.
- **Réagir aux changements d'état** : Effectuer des actions qui dépendent de la vue active, telles que réinitialiser des formulaires ou mettre en surbrillance des composants.

La méthode `onDidEnter` dans `FormView` vérifie la présence d'un paramètre `id` dans la route et ajuste le comportement du formulaire en conséquence :

- **Mode Édition** : Si un `id` est fourni, la méthode récupère les données du client correspondant en utilisant `Service` et pré-remplit les champs du formulaire. Le bouton `Soumettre` est configuré pour mettre à jour l'entrée existante.
- **Mode Ajout** : Si aucun `id` n'est présent, le formulaire reste vierge, et le bouton `Soumettre` est configuré pour créer un nouveau client.

```java
@Override
  public void onDidEnter(DidEnterEvent event, ParametersBag parameters) {
    parameters.get("id").ifPresentOrElse(id -> {
      customer = Service.getCurrent().getCustomerByKey(UUID.fromString(id));
      firstName.setValue(customer.getFirstName());
      lastName.setValue(customer.getLastName());
      company.setValue(customer.getCompany());
      country.selectKey(customer.getCountry());
      submit.addClickListener(e -> submit("edit"));
    }, () -> submit.addClickListener(e -> submit("add")));
  }
```

### Soumission des données {#submitting-data}

Une fois que vous avez terminé de modifier les données, il est nécessaire de les soumettre au service gérant le référentiel. Par conséquent, la classe `Service` qui a déjà été mise en place dans l'étape précédente de ce tutoriel doit maintenant être améliorée avec des méthodes supplémentaires, permettant aux utilisateurs d'ajouter et d'éditer des clients. 

L'extrait ci-dessous montre comment y parvenir :

```java title="Service.java"
public void addCustomer(Customer newCustomer) {
  data.add(newCustomer);
  repository.commit(newCustomer);
}

public void editCustomer(Customer editedCustomer) {
  repository.commit(editedCustomer);
}
```

### Utilisation de `commit()` {#using-commit}

La méthode `commit()` dans la classe `Repository` permet de synchroniser les données et l'interface utilisateur de l'application. Elle fournit un mécanisme pour rafraîchir les données stockées dans le `Repository`, garantissant que l'état le plus récent soit reflété dans l'application.

Cette méthode peut être utilisée de deux manières :

1) **Rafraîchir toutes les données :**
  Appeler `commit()` sans arguments recharge toutes les entités à partir de la source de données sous-jacente du référentiel, telle qu'une base de données ou une classe de service.

2) **Rafraîchir une seule entité :**
  Appeler `commit(T entity)` recharge une entité spécifique, garantissant que son état corresponde aux dernières modifications de la source de données.

Appelez `commit()` lorsque les données dans le `Repository` changent, par exemple après avoir ajouté ou modifié des entités dans la source de données.

```java
// Rafraîchir toutes les données clients dans le référentiel
customerRepository.commit();

// Rafraîchir une seule entité client
Customer updatedCustomer = ...; // Mis à jour à partir d'une source externe
customerRepository.commit(updatedCustomer);

```

Avec ces changements, les objectifs suivants ont été atteints :

  1. Mise en œuvre du routage et configuration afin que de futures vues puissent être intégrées avec peu d'effort.
  2. Suppression des implémentations de l'interface utilisateur de la classe `App` et déplacement vers une vue séparée.
  3. Ajout d'une vue supplémentaire pour manipuler les données affichées dans le tableau des clients.

Avec la modification des détails du client et le routage accomplis, la prochaine étape se concentrera sur la mise en œuvre de la liaison de données et son utilisation pour faciliter la validation.
