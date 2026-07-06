---
title: Routing and Composites
sidebar_position: 4
description: Step 3 - Make your app navigable.
_i18n_hash: 1ffb9e9bf7ba8d863dad3bc0c42c11d7
---
Jusqu'à présent, ce tutoriel n'a été qu'une application à une seule page. Cette étape change cela. Vous allez déplacer l'interface utilisateur que vous avez créée dans [Travailler avec des données](/docs/introduction/tutorial/working-with-data) vers sa propre page et créer une autre page pour ajouter de nouveaux clients. Ensuite, vous reliez ces pages afin que votre application puisse naviguer entre elles en appliquant ces concepts :

- [Routage](/docs/routing/overview)
- [Composer des composants](/docs/building-ui/composing-components)
- Le composant [`ColumnsLayout`](/docs/components/columns-layout)

L'achèvement de cette étape crée une version de [3-routing-and-composites](https://github.com/webforj/webforj-tutorial/tree/main/3-routing-and-composites).

<!-- Insérer la vidéo ici -->

## Exécution de l'application {#running-the-app}

Au fur et à mesure que vous développez votre application, vous pouvez utiliser [3-routing-and-composites](https://github.com/webforj/webforj-tutorial/tree/main/3-routing-and-composites) comme point de comparaison. Pour voir l'application en action :

1. Naviguez jusqu'au répertoire de niveau supérieur contenant le fichier `pom.xml` ; il s'agit de `3-routing-and-composites` si vous suivez la version sur GitHub.

2. Utilisez la commande Maven suivante pour exécuter l'application Spring Boot localement :
    ```bash
    mvn
    ```

L'exécution de l'application ouvre automatiquement un nouveau navigateur à `http://localhost:8080`.

## Applications routables {#routable-apps}

Auparavant, votre application avait une seule fonction : afficher un tableau de données clients existantes. Dans cette étape, votre application sera également capable de modifier les données clients en ajoutant de nouveaux clients. Séparer les interfaces utilisateur pour l'affichage et la modification est bénéfique pour la maintenance et les tests à long terme, donc vous allez ajouter cette fonctionnalité sous forme de page séparée. Vous rendrez votre application [routable](/docs/routing/overview) afin que webforJ puisse accéder et charger les deux interfaces utilisateur individuellement.

Une application routable rend l'interface utilisateur en fonction de l'URL. Annoter la classe qui étend la classe `App` avec [`@Routify`](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/annotation/Routify.html) active le routage, et l'élément `packages` indique à webforJ quels packages contiennent des composants d'interface utilisateur.

Lorsque vous ajoutez l'annotation `@Routify` à `Application`, supprimez la méthode `run()`. Vous déplacerez les composants de cette méthode vers une classe que vous créerez dans le package `com.webforj.tutorial.views`. Votre fichier `Application.java` mis à jour devrait ressembler à ceci :

```java title="Application.java" {5-6,15}
@SpringBootApplication
@StyleSheet("ws://css/card.css")
@AppTheme("system")

// Ajout de l'annotation @Routify
@Routify(packages = "com.webforj.tutorial.views")

@AppProfile(name = "CustomerApplication", shortName = "CustomerApplication")
public class Application extends App {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

// Méthode App.run() remplacée supprimée

}
```

:::tip CSS global
Conserver l'annotation `@StyleSheet` dans `Application` applique ce CSS globalement.
:::

### Création de routes {#creating-routes}

Ajouter l'annotation `@Routify` rend votre application routable. Une fois qu'elle est routable, votre application cherchera dans le package `com.webforj.tutorial.views` des routes. Vous devrez créer les routes pour vos interfaces utilisateur et également spécifier leurs [Types de route](/docs/routing/route-hierarchy/route-types). Le type de route détermine comment mapper le contenu de l'interface utilisateur à l'URL.

Le premier type de route est `View`. Ce type de routes se mappe directement à un segment d'URL spécifique dans votre application. Les interfaces utilisateur pour le tableau et le formulaire de nouveau client seront toutes deux des routes de type `View`.

Le second type de route est `Layout`, qui contient des éléments d'interface utilisateur qui apparaissent sur plusieurs pages, comme un en-tête ou une barre latérale. Les routes de mise en page englobent également les vues enfants sans contribuer à l'URL.

Pour spécifier le type de route d'une classe, ajoutez le type de route à la fin du nom de la classe en tant que suffixe. Par exemple, `MainView` est un type de route `View`.

Pour garder les deux fonctions de l'application séparées, votre application doit mapper les interfaces utilisateur à deux routes `View` uniques : une pour le tableau et une pour le formulaire client. Dans `/src/main/java/com/webforj/tutorial/views`, créez deux classes avec un suffixe `View` :

- **`MainView`** : Cette vue contiendra le `Table` précédemment dans la classe `Application`.
- **`FormView`** : Cette vue contiendra un formulaire pour ajouter de nouveaux clients.

### Mapper les URL aux composants {#mapping-urls-to-components}

Votre application est routable et sait qu'elle doit rechercher deux routes `View`, `MainView` et `FormView`, mais elle n'a pas d'URL spécifique pour les charger. En utilisant l'annotation `@Route` sur une classe de vue, vous pouvez indiquer à webforJ où la charger en fonction d'un segment d'URL donné. Par exemple, en utilisant `@Route("about")`, une vue se mappe localement à `http://localhost:8080/about`.

Comme son nom l'indique, `MainView` est la classe que vous souhaitez charger initialement lorsque l'application s'exécute. Pour ce faire, ajoutez une annotation `@Route` qui associe `MainView` à l'URL racine de votre application :

```java title="MainView.java" {1}
@Route("/")
public class MainView {

  public MainView() {
  }

}
```

Pour le `FormView`, mappez la vue pour qu'elle se charge lorsqu'un utilisateur va à `http://localhost:8080/customer` :

```java title="FormView.java" {1}
@Route("customer")
public class FormView {

  public FormView() {
  }

}
```

:::tip Comportement par défaut
Si vous n'attribuez pas explicitement une valeur pour l'annotation `@Route`, le segment d'URL est le nom de la classe converti en minuscules, avec le suffixe `View` supprimé.

- `MainView` se mapperait à `/main`
- `FormView` se mapperait à `/form`
:::

## Caractéristiques partagées {#shared-characteristics}

Outre le fait d'être toutes deux des routes de vue, `MainView` et `FormView` partagent des caractéristiques supplémentaires. Certaines de ces caractéristiques partagées, comme l'utilisation de composants `Composite`, sont fondamentales pour l'utilisation des applications webforJ, tandis que d'autres facilitent simplement la gestion de votre application.

### Utilisation de composants `Composite` {#using-composite-components}

Lorsque l'application était à une seule page, vous avez stocké les composants à l'intérieur d'un `Frame`. En avançant, avec une application à plusieurs vues, vous devrez envelopper ces composants d'interface utilisateur à l'intérieur de composants [`Composite`](/docs/building-ui/composing-components).

Les composants `Composite` sont des enveloppes qui facilitent la création de composants réutilisables. Pour créer un composant `Composite`, étendez la classe `Composite` avec un composant lié spécifié qui sert de fondation de la classe, par exemple, `Composite<FlexLayout>`. 

Ce tutoriel utilise des éléments `Div` comme composants liés, mais ils peuvent être n'importe quel composant, tel que [`FlexLayout`](/docs/components/flex-layout) ou [`AppLayout`](/docs/components/app-layout). En utilisant la méthode `getBoundComponent()`, vous pouvez référencer le composant lié et accéder à ses méthodes. Cela vous permet de définir la taille, d'ajouter un nom de classe CSS, d'ajouter des composants que vous souhaitez afficher dans le composant `Composite`, et d'accéder à des méthodes spécifiques aux composants.

Pour `MainView` et `FormView`, étendez `Composite` avec `Div` comme composant lié. Ensuite, référencez ce composant lié afin que vous puissiez ajouter les interfaces utilisateur plus tard. Les deux vues devraient avoir une structure similaire à celle-ci :

```java
// Étendre Composite avec un composant lié
public class MainView extends Composite<Div> {

  // Accéder au composant lié
  private Div self = getBoundComponent();

  // Créer un composant UI
  private Button submit = new Button("Submit");

  public MainView() {

    // Ajouter le composant UI au composant lié
    self.add(submit);
  }
}
```

### Définir le titre de la fenêtre {#setting-the-frame-tile}

Lorsque un utilisateur a plusieurs onglets dans son navigateur, un titre de fenêtre unique l'aide à identifier rapidement quelle partie de l'application il a ouverte.

L'annotation [`@FrameTitle`](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/annotation/FrameTitle.html) définit ce qui apparaît dans le titre du navigateur ou dans l'onglet de la page. Pour les deux vues, ajoutez un titre de fenêtre en utilisant l'annotation `@FrameTitle` :

<Tabs>
  <TabItem value="MainView" label="MainView">
  ```java title="MainView.java" {2}
  @Route("/")
  @FrameTitle("Tableau des clients")
  public class MainView extends Composite<Div> {

    private Div self = getBoundComponent();

    public MainView(CustomerService customerService) {
    }
  }
  ```
  </TabItem>
  <TabItem value="FormView" label="FormView">
  ```java title="FormView.java" {2}
  @Route("customer")
  @FrameTitle("Formulaire client")
  public class FormView extends Composite<Div> {

    private Div self = getBoundComponent();

    public FormView(CustomerService customerService) {
    }
  }
  ```
  </TabItem>
</Tabs>

### CSS partagé {#shared-css}

Avec un composant lié que vous pouvez référencer dans `MainView` et `FormView`, vous pouvez le styliser avec CSS. Vous pouvez utiliser le CSS de la première étape, [Créer une application de base](/docs/introduction/tutorial/creating-a-basic-app#referencing-a-css-file), pour donner aux deux vues des styles de conteneurs d'interface utilisateur identiques. Ajoutez le nom de classe CSS `card` au composant lié dans chaque vue :

<Tabs>
  <TabItem value="MainView" label="MainView">
    ```java {9} title="MainView.java"
    @Route("/")
    @FrameTitle("Tableau des clients")
    public class MainView extends Composite<Div> {

      private Div self = getBoundComponent();

      public MainView() {

        self.addClassName("card");
      }
    }
    ```
  </TabItem>
  <TabItem value="FormView" label="FormView">
    ```java {9} title="FormView.java"
    @Route("customer")
    @FrameTitle("Formulaire client")
    public class FormView extends Composite<Div> {

      private Div self = getBoundComponent();

      public FormView() {

        self.addClassName("card");
      }
    }
    ```
  </TabItem>
</Tabs>

### Utilisation de `CustomerService` {#using-customerservice}

La dernière trait partagé pour les vues est l'utilisation de la classe `CustomerService`. Le `Table` dans `MainView` affiche chaque client, tandis que `FormView` ajoute de nouveaux clients. Puisque les deux vues interagissent avec les données des clients, elles ont besoin d'accéder à la logique métier de l'application. 

Les vues obtiennent accès via le service Spring créé dans [Travailler avec des données](/docs/introduction/tutorial/working-with-data#creating-a-service), `CustomerService`. Pour utiliser le service Spring dans chaque vue, faites de `CustomerService` un paramètre du constructeur :

<Tabs>
  <TabItem value="MainView" label="MainView">
    ```java {7-8} title="MainView.java"
    @Route("/")
    @FrameTitle("Tableau des clients")
    public class MainView extends Composite<Div> {

      private Div self = getBoundComponent();

      public MainView(CustomerService customerService) {
        this.customerService = customerService;
        self.addClassName("card");
      }
    }
    ```
  </TabItem>
  <TabItem value="FormView" label="FormView">
    ```java {7-8} title="FormView.java"
    @Route("customer")
    @FrameTitle("Formulaire client")
    public class FormView extends Composite<Div> {

      private Div self = getBoundComponent();

      public FormView(CustomerService customerService) {
        this.customerService = customerService;
        self.addClassName("card");
      }
    }
    ```
  </TabItem>
</Tabs>

## Création de `MainView` {#creating-mainview}

Après avoir rendu votre application routable, donné aux vues des enveloppes de composants `Composite`, et inclus le `CustomerService`, vous êtes prêt à construire les interfaces utilisateurs uniques à chaque vue. Comme mentionné précédemment, `MainView` contient les composants d'interface utilisateur initialement dans `Application`. Cette classe a également besoin d'un moyen de naviguer vers `FormView`.

### Regroupement des méthodes `Table` {#grouping-the-table-methods}

Alors que vous déplacez les composants de `Application` vers `MainView`, il est bon de commencer à sectionner des parties de votre application, de sorte qu'une méthode personnalisée puisse apporter des modifications au `Table` d'un coup. La section de votre code maintenant le rend plus gérable à mesure que l'application devient plus complexe.

Maintenant, le constructeur de votre `MainView` devrait seulement appeler une méthode `buildTable()` qui ajoute les colonnes, définit les tailles, et référence le référentiel :

```java
private void buildTable() {
  table.setSize("1000px", "294px");
  table.setMaxWidth("90vw");
  table.addColumn("firstName", Customer::getFirstName).setLabel("Prénom");
  table.addColumn("lastName", Customer::getLastName).setLabel("Nom");
  table.addColumn("company", Customer::getCompany).setLabel("Société");
  table.addColumn("country", Customer::getCountry).setLabel("Pays");
  table.setColumnsToAutoFit();
  table.getColumns().forEach(column -> column.setSortable(true));
  table.setRepository(customerService.getRepositoryAdapter());
}
```

### Navigation vers `FormView`{#navigating-to-formview}

Les utilisateurs ont besoin d'un moyen de naviguer de `MainView` à `FormView` en utilisant l'interface utilisateur.

Dans webforJ, vous pouvez naviguer directement vers une nouvelle vue en utilisant la classe de la vue. Le routage via une classe au lieu d'un segment d'URL garantit que webforJ prendra le bon chemin pour charger la vue. 

Pour naviguer vers une vue différente, utilisez la classe [`Router`](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/router/Router.html) pour obtenir l'emplacement actuel avec `getCurrent()`, puis utilisez la méthode `navigate()` avec la classe de la vue comme paramètre : 

```java
Router.getCurrent().navigate(FormView.class);
```

Ce code enverra programmétiquement les utilisateurs vers le formulaire de nouveau client, mais la navigation doit être connectée à une action de l'utilisateur. Pour permettre aux utilisateurs d'ajouter un nouveau client, vous pouvez modifier ou remplacer le bouton d'infos de `Application`. Au lieu d'ouvrir une boîte de dialogue de message, le bouton peut naviguer vers la classe `FormView` :

```java
private Button addCustomer = new Button("Ajouter un client", ButtonTheme.PRIMARY,
    e -> Router.getCurrent().navigate(FormView.class));
```

## `MainView` complété {#completed-mainview}

Avec la navigation vers `FormView` et les méthodes de table regroupées, voici à quoi devrait ressembler `MainView` avant de passer à la création de `FormView` :

<!-- vale off -->
<ExpandableCode title="MainView.java" language="java" startLine={1} endLine={15}>

```java
@Route("/")
@FrameTitle("Tableau des clients")
public class MainView extends Composite<Div> {
  private final CustomerService customerService;
  private Div self = getBoundComponent();
  private Table<Customer> table = new Table<>();
  private Button addCustomer = new Button("Ajouter un client", ButtonTheme.PRIMARY,
      e -> Router.getCurrent().navigate(FormView.class));

  public MainView(CustomerService customerService) {
    this.customerService = customerService;
    addCustomer.setWidth(200);
    buildTable();
    self.setWidth("fit-content")
        .addClassName("card")
        .add(table, addCustomer);
  }

  private void buildTable() {
    table.setSize("1000px", "294px");
    table.setMaxWidth("90vw");
    table.addColumn("firstName", Customer::getFirstName).setLabel("Prénom");
    table.addColumn("lastName", Customer::getLastName).setLabel("Nom");
    table.addColumn("company", Customer::getCompany).setLabel("Société");
    table.addColumn("country", Customer::getCountry).setLabel("Pays");
    table.setColumnsToAutoFit();
    table.setColumnsToResizable(false);
    table.getColumns().forEach(column -> column.setSortable(true));
    table.setRepository(customerService.getRepositoryAdapter());
  }

}
```

</ExpandableCode>
<!-- vale on -->

## Création de `FormView` {#creating-formview}

`FormView` affichera un formulaire pour ajouter de nouveaux clients. Pour chaque propriété du client, `FormView` aura un composant éditable avec lequel les utilisateurs peuvent interagir. De plus, il aura un bouton pour que les utilisateurs soumettent les données et un bouton d'annulation pour les abandonner.

### Création d'une instance `Customer` {#creating-a-customer-instance}

Lorsqu'un utilisateur édite les données d'un nouveau client, les modifications ne devraient être appliquées au référentiel que lorsqu'il est prêt à soumettre le formulaire. Utiliser une instance de l'objet `Customer` est un moyen pratique d'éditer et de maintenir les nouvelles données sans éditer directement le référentiel. Créez un nouveau `Customer` à l'intérieur de `FormView` à utiliser pour le formulaire :

```java
private Customer customer = new Customer();
```

Pour rendre l'instance `Customer` éditable, chaque propriété, sauf pour l'`id`, devrait être associée à un composant éditable. Les modifications qu'un utilisateur apporte dans l'interface utilisateur doivent être reflétées dans l'instance `Customer`.

### Ajout de composants `TextField` {#adding-textfield-components}

Les trois premières propriétés éditables dans `Customer` (`firstName`, `lastName`, et `company`) sont toutes des valeurs `String` et devraient être représentées par un éditeur de texte en ligne unique. Les composants [`TextField`](/docs/components/fields/textfield) sont un excellent choix pour représenter ces propriétés.

Avec le composant `TextField`, vous pouvez ajouter une étiquette et un écouteur d'événements qui se déclenche chaque fois que la valeur du champ change. Chaque écouteur d'événements devrait mettre à jour l'instance `Customer` pour la propriété correspondante.

Ajoutez trois composants `TextField` qui mettent à jour l'instance `Customer` :

```java title="FormView.java" {6-8}
public class FormView extends Composite<Div> {
  private final CustomerService customerService;
  private Customer customer = new Customer();
  private Div self = getBoundComponent();

  private TextField firstName = new TextField("Prénom", e -> customer.setFirstName(e.getValue()));
  private TextField lastName = new TextField("Nom", e -> customer.setLastName(e.getValue()));
  private TextField company = new TextField("Société", e -> customer.setCompany(e.getValue()));

  public FormView(CustomerService customerService) {
    this.customerService = customerService;
    self.addClassName("card");
  }
}
```

:::tip Convention de nommage partagée
Nommmer les composants de la même manière que les propriétés qu'ils représentent pour l'entité `Customer` facilite la liaison des données dans une étape future, [Validation et liaison des données](/docs/introduction/tutorial/validating-and-binding-data).
:::

### Ajout d'un composant `ChoiceBox` {#adding-a-choicebox-component}

L'utilisation d'un `TextField` pour la propriété `country` ne serait pas idéale, car la propriété ne peut être que l'une des cinq valeurs d'énumération : `UNKNOWN`, `GERMANY`, `ENGLAND`, `ITALY`, et `USA`.

Un meilleur composant pour sélectionner parmi une liste d'options prédéfinies est le [`ChoiceBox`](/docs/components/lists/choicebox).

Chaque option pour un composant `ChoiceBox` est représentée par un `ListItem`. Chaque `ListItem` a deux valeurs, une clé `Object` et un texte `String` à afficher dans l'interface utilisateur. Avoir deux valeurs pour chaque option vous permet de gérer l'`Object` en interne tout en présentant simultanément une option plus lisible pour les utilisateurs dans l'interface utilisateur.

Pour ajouter toutes les options `country` dans un `ChoiceBox`, vous pouvez utiliser un itérateur pour créer un `ListItem` pour chaque énumération `Customer.Country` et les mettre dans un `ArrayList<ListItem>`. Ensuite, vous pouvez insérer cet `ArrayList<ListItem>` dans un composant `ChoiceBox` :

```java
// Créer le composant ChoiceBox
private ChoiceBox country = new ChoiceBox("Pays");

// Créer un ArrayList d'objets ListItem
ArrayList<ListItem> listCountries = new ArrayList<>();

// Ajouter un itérateur qui crée un ListItem pour chaque option Customer.Country
for (Country countryItem : Customer.Country.values()) {
  listCountries.add(new ListItem(countryItem, countryItem.toString()));
}

// Insérer l'ArrayList rempli dans le ChoiceBox
country.insert(listCountries);

// Rendre le premier ListItem par défaut lorsque le formulaire se charge
country.selectIndex(0);
```

Ensuite, lorsque l'utilisateur sélectionne une option dans le `ChoiceBox`, l'instance `Customer` doit être mise à jour avec la clé de l'élément sélectionné, qui est une valeur `Customer.Country`.

```java
private ChoiceBox country = new ChoiceBox("Pays",
    e -> customer.setCountry((Customer.Country) e.getSelectedItem().getKey()));
```

Pour garder le code propre, l'itérateur qui crée l'`ArrayList<ListItem>` et l'ajoute au `ChoiceBox` devrait être dans une méthode séparée. Après avoir ajouté un `ChoiceBox` qui permet à l'utilisateur de choisir la propriété `country`, `FormView` devrait ressembler à ceci :

```java title="FormView.java" {9-10,15,18-25}
public class FormView extends Composite<Div> {
  private final CustomerService customerService;
  private Customer customer = new Customer();
  private Div self = getBoundComponent();
  private TextField firstName = new TextField("Prénom", e -> customer.setFirstName(e.getValue()));
  private TextField lastName = new TextField("Nom", e -> customer.setLastName(e.getValue()));
  private TextField company = new TextField("Société", e -> customer.setCompany(e.getValue()));

  private ChoiceBox country = new ChoiceBox("Pays",
      e -> customer.setCountry((Customer.Country) e.getSelectedItem().getKey()));

  public FormView(CustomerService customerService) {
    this.customerService = customerService;
    self.addClassName("card");
    fillCountries();
  }

  private void fillCountries() {
    ArrayList<ListItem> listCountries = new ArrayList<>();
    for (Country countryItem : Customer.Country.values()) {
      listCountries.add(new ListItem(countryItem, countryItem.toString()));
    }
    country.insert(listCountries);
    country.selectIndex(0);
  }

}
```

### Ajout de composants `Button` {#adding-button-components}

Lors de l'utilisation du formulaire de nouveau client, les utilisateurs doivent être capables de sauvegarder ou d'abandonner leurs modifications. Créez deux composants `Button` pour implémenter cette fonctionnalité :

```java
private Button submit = new Button("Soumettre");
private Button cancel = new Button("Annuler");
```

Les boutons de soumission et d'annulation devraient ramener l'utilisateur à `MainView`. Cela permet à l'utilisateur de voir immédiatement les résultats de son action, qu'il voie un nouveau client dans le tableau ou qu'il reste inchangé. Puisque plusieurs entrées dans `FormView` amènent les utilisateurs à `MainView`, la navigation devrait être mise dans une méthode rappelable :

```java
private void navigateToMain() {
  Router.getCurrent().navigate(MainView.class);
}
```

**Bouton d'annulation**

Abandonner les modifications du formulaire ne nécessite pas de code supplémentaire pour l'événement au-delà du retour à `MainView`. Toutefois, puisque l'annulation n'est pas une action principale, définir le thème du bouton comme en mode outline donne plus de visibilité au bouton de soumission. La section [Thèmes](/docs/components/button#themes) de la page du composant `Button` énumère tous les thèmes disponibles.

```java
private Button cancel = new Button("Annuler", ButtonTheme.OUTLINED_PRIMARY,
    e -> navigateToMain());
```

**Bouton de soumission**

Lorsque l'utilisateur appuie sur le bouton de soumission, les valeurs de l'instance `Customer` devraient être utilisées pour créer une nouvelle entrée dans le référentiel.

Utilisant le `CustomerService`, vous pouvez prendre l'instance `Customer` pour mettre à jour la base de données H2. Lorsque cela se produit, un nouvel et unique `id` est attribué à ce `Customer`. Après la mise à jour du référentiel, vous pouvez rediriger les utilisateurs vers `MainView`, où ils peuvent voir le nouveau client dans le tableau.

```java
private Button submit = new Button("Soumettre", ButtonTheme.PRIMARY,
    e -> submitCustomer());

//...

private void submitCustomer() {
  customerService.createCustomer(customer);
  navigateToMain();
}
```

### Utilisation d'un `ColumnsLayout` {#using-a-columnslayout}

En ajoutant les composants `TextField`, `ChoiceBox`, et `Button`, vous avez maintenant toutes les parties interactives du formulaire. La dernière amélioration à `FormView` dans cette étape est d'organiser visuellement les six composants.

Ce formulaire peut utiliser un [`ColumnsLayout`](/docs/components/columns-layout) pour séparer les composants en deux colonnes sans avoir à définir la largeur de n'importe quel composant interactif. Pour créer un `ColumnsLayout`, spécifiez chaque composant qui devrait être à l'intérieur de la mise en page :

```java
private ColumnsLayout layout = new ColumnsLayout(
  firstName, lastName,
  company, country,
  submit, cancel);
```

Pour définir le nombre de colonnes pour un `ColumnsLayout`, utilisez une `List` d'objets `Breakpoint`. Chaque `Breakpoint` indique au `ColumnsLayout` la largeur minimale qu'il doit avoir pour appliquer un certain nombre de colonnes. En utilisant le `ColumnsLayout`, vous pouvez faire un formulaire avec deux colonnes, mais seulement si l'écran est assez large pour afficher deux colonnes. Sur les écrans plus petits, les composants s'affichent dans une seule colonne.

La section [Breakpoints](/docs/components/columns-layout#breakpoints) dans l'article sur `ColumnsLayout` explique les points de rupture plus en détail.

Pour garder le code maintenable, définissez les points de rupture dans une méthode séparée. Dans cette méthode, vous pouvez également contrôler l'espacement horizontal et vertical entre les composants à l'intérieur du `ColumnsLayout` avec la méthode `setSpacing()`.

```java
private void setColumnsLayout() {

  // Avoir deux colonnes dans le ColumnsLayout si sa largeur est supérieure à 600 px
  List<Breakpoint> breakpoints = List.of(
    new Breakpoint(600, 2));

  // Ajouter la liste de points de rupture
  layout.setBreakpoints(breakpoints);

  // Définir l'espacement entre composants en utilisant une variable CSS DWC
  layout.setSpacing("var(--dwc-space-l)")
}
```

Enfin, vous pouvez ajouter le nouveau `ColumnsLayout` créé au composant lié de `FormView`, tout en définissant la largeur maximale et en ajoutant le nom de classe d'auparavant :

```java
self.setMaxWidth(600)
  .addClassName("card")
  .add(layout);
```

## `FormView` complété {#completed-formview}

Après avoir ajouté une instance `Customer`, les composants interactifs et le `ColumnsLayout`, votre `FormView` devrait ressembler à ceci :

<!-- vale off -->
<ExpandableCode title="FormView.java" language="java" startLine={1} endLine={15}>

```java
@Route("customer")
@FrameTitle("Formulaire client")
public class FormView extends Composite<Div> {
  private final CustomerService customerService;
  private Customer customer = new Customer();
  private Div self = getBoundComponent();
  private TextField firstName = new TextField("Prénom", e -> customer.setFirstName(e.getValue()));
  private TextField lastName = new TextField("Nom", e -> customer.setLastName(e.getValue()));
  private TextField company = new TextField("Société", e -> customer.setCompany(e.getValue()));
  private ChoiceBox country = new ChoiceBox("Pays",
      e -> customer.setCountry((Customer.Country) e.getSelectedItem().getKey()));
  private Button submit = new Button("Soumettre", ButtonTheme.PRIMARY, e -> submitCustomer());
  private Button cancel = new Button("Annuler", ButtonTheme.OUTLINED_PRIMARY, e -> navigateToMain());
  private ColumnsLayout layout = new ColumnsLayout(
      firstName, lastName,
      company, country,
      submit, cancel);

  public FormView(CustomerService customerService) {
    this.customerService = customerService;
    fillCountries();
    setColumnsLayout();
    self.setMaxWidth(600)
        .addClassName("card")
        .add(layout);
    submit.setStyle("margin-top", "var(--dwc-space-l)");
    cancel.setStyle("margin-top", "var(--dwc-space-l)");
  }

  private void setColumnsLayout() {
    List<Breakpoint> breakpoints = List.of(
        new Breakpoint(600, 2));
    layout.setSpacing("var(--dwc-space-l)")
        .setBreakpoints(breakpoints);
  }

  private void fillCountries() {
    ArrayList<ListItem> listCountries = new ArrayList<>();
    for (Country countryItem : Customer.Country.values()) {
      listCountries.add(new ListItem(countryItem, countryItem.toString()));
    }
    country.insert(listCountries);
    country.selectIndex(0);
  }

  private void submitCustomer() {
    customerService.createCustomer(customer);
    navigateToMain();
  }

  private void navigateToMain() {
    Router.getCurrent().navigate(MainView.class);
  }

}
```

</ExpandableCode>
<!-- vale on -->

## Étape suivante {#next-step}

Puisque les utilisateurs peuvent maintenant ajouter des clients, votre application devrait être capable de modifier des clients existants en utilisant le même formulaire. Dans la prochaine étape, [Observateurs et paramètres de route](/docs/introduction/tutorial/observers-and-route-parameters), vous permettrez que l'`id` du client soit un paramètre initial pour `FormView`, afin qu'il puisse remplir le formulaire avec les données de ce client et permettre aux utilisateurs de modifier les propriétés.
