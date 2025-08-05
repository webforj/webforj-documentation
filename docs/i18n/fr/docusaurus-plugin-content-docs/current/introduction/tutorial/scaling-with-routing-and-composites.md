---
title: Scaling with Routing and Composites
sidebar_position: 4
_i18n_hash: fdfd4b4255de20775bb12bcd863630f7
---
Cette étape se concentre sur la mise en œuvre du routage pour améliorer l'évolutivité et l'organisation de la structure de l'application. Pour ce faire, l'application sera mise à jour pour gérer plusieurs vues, permettant la navigation entre différentes fonctionnalités telles que l'édition et la création d'entrées clients. Elle détaillera la création de vues pour ces fonctionnalités, utilisant des composants comme `Composite` pour construire des mises en page modulaires et réutilisables.

L'application créée dans l'[étape précédente](./working-with-data) disposera d'une configuration de routage qui prend en charge plusieurs vues, permettant aux utilisateurs de gérer les données clients plus efficacement tout en maintenant une codebase propre et évolutive. Pour exécuter l'application :

- Allez dans le répertoire `3-scaling-with-routing-and-composites`
- Exécutez la commande `mvn jetty:run`

<div class="videos-container">
  <video controls>
    <source src="https://cdn.webforj.com/webforj-documentation/video/tutorials/scaling-with-routing-and-composites.mp4" type="video/mp4"/>
  </video>
</div>

## Routage {#routing}

Le [routage](../../routing/overview) est le mécanisme qui permet à votre application de gérer la navigation entre différentes vues ou pages. Au lieu de garder toute la logique et le comportement dans un seul endroit, le routage vous permet de diviser votre application en composants plus petits et ciblés.

Au cœur du routage, des URL spécifiques sont connectées aux vues ou composants qui gèrent ces URL. Lorsqu'un utilisateur interagit avec votre application, comme cliquer sur un bouton ou entrer une URL directement dans son navigateur, le routeur résout l'URL vers la vue appropriée, l'initialise et l'affiche à l'écran. Cette approche facilite la gestion de la navigation et le maintien de l'état de l'application.

Cette étape se concentre sur le changement de la classe `App`, la création de fichiers pour les vues et la configuration des routes pour permettre une navigation fluide entre les différentes parties de votre application.

Au lieu de placer toute la logique dans la méthode `run()` de `App`, des vues comme `DemoView` et `FormView` sont mises en œuvre en tant que classes séparées. Cette approche s'aligne plus étroitement sur les pratiques Java standard.

- **DemoView** : Gère l'affichage du tableau et la navigation vers `FormView`.
- **FormView** : Gère l'ajout et l'édition des données clients.

### Changement de la classe `App` {#changing-the-app-class}

Pour activer le routage dans votre application, mettez à jour la classe `App` avec l'annotation `@Routify`. Cela indique à webforJ d'activer le routage et de scanner les packages spécifiés pour les vues activées par les routes.

```java title="DemoApplication.java" {1}
@Routify(packages = "com.webforj.demos.views", debug = true)
public class DemoApplication extends App {  
}
```

- **`packages`** : Spécifie quels packages sont scannés pour les vues qui définissent des routes.
- **`debug`** : Active le mode de débogage pour un dépannage facilité lors du développement.

### Création de fichiers pour les vues et configuration des routes {#creating-files-for-the-views-and-configuring-routes}

Une fois le routage activé, des fichiers Java séparés pour chaque vue que l'application contiendra sont créés, dans ce cas, `DemoView.java` et `FormView.java`. Des routes uniques sont attribuées à ces vues en utilisant l'annotation `@Route`. Cela garantit que chaque vue est accessible via une URL spécifique.

Lorsque l'annotation `@Route` associée à une classe avec l'un de ces suffixes n'a pas de valeur, webforJ attribue automatiquement le nom de la classe sans le suffixe comme route. Par exemple, `DemoView` mappera par défaut la route `/demo`. Comme dans ce cas `DemoView` est censé être la route par défaut, vous lui attribuerez une route.

La route `/` sert de point d'entrée par défaut pour votre application. L'attribution de cette route à une vue assure qu'elle soit la première page vue par les utilisateurs en accédant à l'application. Dans la plupart des cas, un tableau de bord ou une vue de résumé est attribuée à `/`.

```java title="DemoView.java" {1}
@Route("/")
@FrameTitle("Démo")
public class DemoView extends Composite<Div> {
  // Logique de DemoView
}
```

:::info 
Plus d'informations concernant les différents types de routes sont disponibles [ici](../../routing/defining-routes).
:::

Pour le `FormView`, la route `customer/:id?` utilise un paramètre optionnel `id` pour déterminer le mode du `FormView`. 

- **Mode Ajout** : Lorsque `id` n'est pas fourni, le `FormView` s'initialise avec un formulaire vide pour l'ajout de nouvelles données clients.
- **Mode Édition** : Lorsque `id` est fourni, le `FormView` récupère les données correspondantes du client à l'aide de `Service` et pré-remplit le formulaire, permettant des modifications sur l'entrée existante.

```java title="FormView.java" {1}
@Route("customer/:id?")
@FrameTitle("Formulaire Client")
public class FormView extends Composite<Div> implements DidEnterObserver {
  // Logique de FormView
}
```

:::info 
Plus d'informations sur les différentes manières d'implémenter ces modèles de route sont disponibles [ici](../../routing/route-patterns).
:::

## Utilisation des composants `Composite` pour afficher des pages {#using-composite-components-to-display-pages}

Les composants Composite dans webforJ, tels que `Composite<Div>`, vous permettent d'encapsuler la logique et la structure de l'interface utilisateur dans un conteneur réutilisable. En étendant `Composite`, vous limitez les méthodes et les données exposées au reste de l'application, garantissant un code plus propre et une meilleure encapsulation.

Par exemple, `DemoView` étend `Composite<Div>` au lieu d'étendre directement `Div` :

```java title="DemoView.java"
public class DemoView extends Composite<Div> {
  private Table<Customer> table = new Table<>();
  private Button add = new Button("Ajouter un Client", ButtonTheme.PRIMARY);  

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

## Connexion des routes {#connecting-the-routes}

Après avoir configuré le routage et mis en place les vues, connectez les vues et les données à l'aide des gestionnaires d'événements et des méthodes de service. La première étape consiste à ajouter un ou plusieurs éléments d'interface utilisateur pour naviguer d'une vue à l'autre.

### Navigation par bouton {#button-navigation}

Le composant `Button` déclenche un événement de navigation pour passer d'une vue à une autre en utilisant la classe `Router`. Par exemple :

```java title="DemoView.java"
private Button add = new Button("Ajouter un Client", ButtonTheme.PRIMARY,
  e -> Router.getCurrent().navigate(FormView.class));
```

:::info
La classe Router utilise la classe donnée pour résoudre la route et construire une URL vers laquelle naviguer. Toute la navigation du navigateur est ensuite gérée pour que la gestion de l'historique et l'initialisation de la vue ne soient pas une préoccupation.
Pour plus de détails sur la navigation, voir l'[article sur la navigation des routes](../../routing/route-navigation).
:::

### Édition du tableau {#table-editing}

En plus de la navigation par clic sur un bouton, de nombreuses applications permettent également la navigation vers d'autres parties d'une application lorsqu'un élément de la `Table` est double-cliqué. Les modifications suivantes sont apportées pour permettre aux utilisateurs de double-cliquer sur un élément du tableau pour naviguer vers un formulaire pré-rempli avec les détails de l'élément.

Une fois les détails modifiés sur l'écran approprié, les changements sont enregistrés et le `Table` est mis à jour pour afficher les données modifiées de l'élément sélectionné.

Pour faciliter cette navigation, les clics sur les éléments dans le tableau sont gérés par le gestionnaire d'événements `TableItemClickEvent<Customer>`. L'événement contient l'`id` du client cliqué, qu'il passe à la `FormView` en utilisant la méthode `navigate()` avec un `ParametersBag` :

```java title="DemoView.java" 
private void editCustomer(TableItemClickEvent<Customer> e) {
  Router.getCurrent().navigate(FormView.class,
    ParametersBag.of("id=" + e.getItemKey()));
}
```

### Gestion de l'initialisation avec `onDidEnter` {#handling-initialization-with-ondidenter}

La méthode `onDidEnter` dans webforJ fait partie du cycle de vie du routage et est déclenchée lorsqu'une vue devient active. 

Lorsque le `Router` navigue vers une vue, `onDidEnter` est déclenché dans le cadre du cycle de vie pour :
- **Charger des Données** : Initialiser ou récupérer les données requises pour la vue en fonction des paramètres de route.
- **Configurer la Vue** : Mettre à jour dynamiquement les éléments de l'interface utilisateur en fonction du contexte.
- **Réagir aux Changements d'État** : Effectuer des actions qui dépendent de l'activation de la vue, comme réinitialiser des formulaires ou mettre en surbrillance des composants.

La méthode `onDidEnter` de `FormView` vérifie la présence d'un paramètre `id` dans la route et ajuste le comportement du formulaire en conséquence :

- **Mode Édition** : Si un `id` est fourni, la méthode récupère les données correspondantes du client à l'aide de `Service` et pré-remplit les champs du formulaire. Le bouton `Soumettre` est configuré pour mettre à jour l'entrée existante.
- **Mode Ajout** : Si aucun `id` n'est présent, le formulaire reste vide, et le bouton `Soumettre` est configuré pour créer un nouveau client.

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

Une fois l'édition des données terminée, il est nécessaire de les soumettre au service gérant le dépôt. Par conséquent, la classe `Service` qui a été déjà configurée dans l'étape précédente de ce tutoriel doit maintenant être améliorée avec des méthodes supplémentaires, permettant aux utilisateurs d'ajouter et d'éditer des clients. 

L'extrait ci-dessous montre comment accomplir cela :

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

La méthode `commit()` dans la classe `Repository` maintient les données et l'interface utilisateur de l'application en synchronisation. Elle fournit un mécanisme pour rafraîchir les données stockées dans le `Repository`, garantissant que l'état le plus récent est reflété dans l'application.

Cette méthode peut être utilisée de deux manières :

1) **Rafraîchissement de toutes les données :**
  Appeler `commit()` sans arguments recharge toutes les entités depuis la source de données sous-jacente du dépôt, comme une base de données ou une classe de service.

2) **Rafraîchissement d'une seule entité :**
  Appeler `commit(T entity)` recharge une entité spécifique, garantissant que son état correspond aux dernières modifications de la source de données.

Appelez `commit()` lorsque les données dans le `Repository` changent, comme après l'ajout ou la modification d'entités dans la source de données.

```java
// Rafraîchir toutes les données clients dans le dépôt
customerRepository.commit();

// Rafraîchir une seule entité client
Customer updatedCustomer = ...; // Mis à jour à partir d'une source externe
customerRepository.commit(updatedCustomer);

```

Avec ces modifications, les objectifs suivants ont été atteints :

  1. Mise en œuvre du routage et configuration pour que de futures vues puissent être intégrées avec peu d'effort.
  2. Élimination des implémentations de l'interface utilisateur de `App` et transfert vers une vue séparée.
  3. Ajout d'une vue supplémentaire pour manipuler les données affichées dans le tableau des clients.

Avec la modification des détails du client et le routage accomplis, la prochaine étape se concentrera sur la mise en œuvre de la liaison de données et son utilisation pour faciliter la validation.
