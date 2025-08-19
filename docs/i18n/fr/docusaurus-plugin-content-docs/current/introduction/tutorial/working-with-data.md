---
title: Working With Data
sidebar_position: 3
_i18n_hash: 42dff7cecf07f976ccbe007e04e78a22
---
Cette étape se concentre sur l'ajout de capacités de gestion et d'affichage des données à l'application de démonstration. Pour ce faire, des données fictives concernant divers objets `Customer` seront créées, et l'application sera mise à jour pour gérer ces données et les afficher dans une [`Table`](../../components/table/overview) ajoutée à l'application précédente.

Elle décrira la création d'une classe modèle `Customer` et son intégration avec une classe `Service` pour accéder et gérer les données nécessaires en utilisant l'implémentation d'un dépôt. Ensuite, elle expliquera comment utiliser les données récupérées pour implémenter un composant `Table` dans l'application, affichant les informations des clients dans un format interactif et structuré.

À la fin de cette étape, l'application créée dans la [étape précédente](./creating-a-basic-app) affichera un tableau avec les données créées qui pourront ensuite être développées dans les étapes suivantes. Pour exécuter l'application :

- Allez dans le répertoire `2-working-with-data`
- Exécutez `mvn jetty:run`

<!-- vale off -->

<div class="videos-container">
  <video controls>
    <source src="https://cdn.webforj.com/webforj-documentation/video/tutorials/working-with-data.mp4" type="video/mp4"/>
  </video>
</div>

<!-- vale on -->

## Création d'un modèle de données {#creating-a-data-model}

Pour créer une `Table` qui affiche des données dans l'application principale, une classe Java bean qui peut être utilisée avec la `Table` pour afficher des données doit être créée.

Dans ce programme, la classe `Customer` dans `src/main/java/com/webforj/demos/data/Customer.java` le fait. Cette classe sert de modèle de données principal pour l'application, englobant les attributs liés aux clients tels que `firstName`, `lastName`, `company`, et `country`. Ce modèle contiendra également un ID unique.

```java title="Customer.java"
public class Customer implements HasEntityKey {
  private String firstName = "";
  private String lastName = "";
  private String company = "";
  private Country country = Country.UNKNOWN;
  private UUID uuid = UUID.randomUUID();

  public enum Country {

    @SerializedName("Germany")
    GERMANY,

    // Autres pays
  }

    // Getters et Setters

  @Override
  public Object getEntityKey() {
    return uuid;
  }
}
```

:::info Utilisation de `HasEntityKey` pour Identifiants Uniques

L'implémentation de l'interface `HasEntityKey` est cruciale pour la gestion des identifiants uniques dans les modèles utilisés avec une `Table`. Cela garantit que chaque instance du modèle possède une clé unique, permettant à la `Table` d'identifier et de gérer efficacement les lignes.

Pour cette démonstration, la méthode `getEntityKey()` retourne un UUID pour chaque client, assurant une identification unique. Bien que les UUID soient utilisés ici pour des raisons de simplicité, dans les applications réelles, une clé primaire de base de données est souvent un meilleur choix pour générer des clés uniques.

Si `HasEntityKey` n'est pas implémenté, la `Table` utilisera par défaut le code de hachage Java comme clé. Étant donné que les codes de hachage ne sont pas garantis d'être uniques, cela peut entraîner des conflits lors de la gestion des lignes dans la `Table`.
:::

Avec le modèle de données `Customer` en place, l'étape suivante consiste à gérer et organiser ces modèles au sein de l'application.

## Création d'une classe `Service` {#creating-a-service-class}

Agissant en tant que gestionnaire de données centralisé, la classe `Service` non seulement charge les données des `Customer`, mais fournit également une interface efficace pour y accéder et interagir avec elle.

La classe `Service.java` est créée dans `src/main/java/com/webforj/demos/data`. Au lieu de passer manuellement les données entre composants ou classes, le `Service` agit comme une ressource partagée, permettant à des parties intéressées de récupérer et d'interagir facilement avec les données.

Dans cette démonstration, la classe `Service` lit les données des clients à partir d'un fichier JSON situé à `src/main/resources/data/customers.json`. Les données sont mappées sur des objets `Customer` et stockées dans un `ArrayList`, qui forme la base du `Repository` de la table.

Dans webforJ, la classe `Repository` fournit une manière structurée de gérer et de récupérer des collections d'entités. Elle agit comme une interface entre votre application et ses données, offrant des méthodes pour interroger, compter et rafraîchir les données tout en maintenant une structure propre et cohérente. Elle est utilisée par la classe `Table` pour afficher les données stockées à l'intérieur.

Bien que le `Repository` n'inclut pas de méthodes pour mettre à jour ou supprimer des entités, il sert de wrapper structuré autour d'une collection d'objets. Cela le rend idéal pour fournir un accès organisé et efficace aux données.

```java
public class Service {
  private List<Customer> data = new ArrayList<>();
  private CollectionRepository<Customer> repository;

  private Service() {
    data = buildDemoList();
    repository = new CollectionRepository<>(data);
  }

  // Reste de l'implémentation
}
```

Pour peupler le `Repository` avec des données, la classe `Service` agit en tant que gestionnaire central, s'occupant du chargement et de l'organisation des actifs dans l'application. Les données des clients sont lues à partir d'un fichier JSON et mappées aux objets `Customer` dans le `Repository`.

L'utilitaire `Assets` dans webforJ facilite le chargement de ces données de manière dynamique en utilisant des URL de contexte. Pour charger des actifs et des données dans webforJ, la classe `Service` utilise des URL de contexte avec l'utilitaire `Assets`. Par exemple, les données des clients peuvent être chargées à partir du fichier JSON comme suit :

```java
String content = Assets.contentOf(Assets.resolveContextUrl("context://data/customers.json"));
```

:::tip Utilisation de l'`ObjectTable`
La classe `Service` utilise l'`ObjectTable` pour gérer les instances de manière dynamique, plutôt que de s'appuyer sur des champs statiques. Cette approche répond à une limitation clé lors de l'utilisation de servlets : les champs statiques sont liés au cycle de vie du serveur et peuvent entraîner des problèmes dans des environnements avec plusieurs requêtes ou sessions concurrentes. L'`ObjectTable` est limité à la session utilisateur, et son utilisation garantit un comportement de type singleton sans ces limitations, permettant une gestion des données cohérente et évolutive.
:::

```java title="Service.java"
public class Service {

  private List<Customer> data = new ArrayList<>();
  private CollectionRepository<Customer> repository;

  // Constructeur privé pour imposer une instanciation contrôlée
  private Service() {
    // implémentation
  }

  // Récupère l'instance actuelle de Service ou en crée une si elle n'existe pas
  public static Service getCurrent() {
    // implémentation
  }

  // Charge les données des clients à partir du fichier JSON et les mappe à des objets Customer
  private List<Customer> buildDemoList() {
    // implémentation
  }

  // Getter...
}
```

## Création et utilisation d'une `Table` {#creating-and-using-a-table}

Maintenant que les données nécessaires ont été correctement créées via la classe `Customer`, et peuvent être retournées sous la forme d'un `Repository` grâce à la classe `Service`, la tâche finale de cette étape consiste à intégrer le composant `Table` dans l'application pour afficher les données des clients.

:::tip Plus sur la `Table`
Pour un aperçu plus détaillé des différentes fonctionnalités du comportement de la `Table`, consultez [cet article](../../components/table/overview).
:::

La `Table` fournit un moyen dynamique et flexible d'afficher des données structurées dans votre application. Elle est conçue pour s'intégrer à la classe `Repository`, permettant des fonctionnalités telles que la requête de données, la pagination et des mises à jour efficaces. Une `Table` est hautement configurable, vous permettant de définir des colonnes, de contrôler son apparence et de lier à des dépôts de données avec un minimum d'effort.

### Mise en œuvre de la `Table` dans l'application {#implementing-the-table-in-the-app}

Puisque les données pour la `Table` sont entièrement gérées par la classe `Service`, la tâche principale dans `DemoApplication.java` consiste à configurer la `Table` et à la lier au `Repository` fourni par le `Service`.

Pour configurer la `Table` :

- Définissez sa largeur et sa hauteur pour les besoins de mise en page à l'aide des méthodes `setHeight()` et `setWidth()`.
- Définissez les colonnes, en spécifiant leurs noms et les méthodes pour récupérer les données pour chacune.
- Assignez le `Repository` pour fournir des données dynamiquement.

Après avoir fait cela, le code ressemblera à l'extrait suivant :

```java title="DemoApplication.java"
public class DemoApplication extends App {
  // Autres composants de la première étape

  // Le composant Table pour afficher les données des clients
  Table<Customer> table = new Table<>();

  @Override
  public void run() throws WebforjException {
    // Implémentation précédente de la première étape
    buildTable();
    mainFrame.add(demo, btn, table);
  }

  private void buildTable() {
    // Définir la hauteur de la table à 300 pixels
    table.setHeight("300px");
    // Définir la largeur de la table à 1000 pixels
    table.setWidth(1000);

    // Ajouter les différents titres de colonnes et assigner les getters appropriés
    table.addColumn("First Name", Customer::getFirstName);
    table.addColumn("Last Name", Customer::getLastName);
    table.addColumn("Company", Customer::getCompany);
    table.addColumn("Country", Customer::getCountry);

    // Lier la Table à un Repository contenant les données des clients
    // Le Repository est récupéré via la classe Service
    table.setRepository(Service.getCurrent().getCustomers());
  }
}
```

Avec les modifications apportées à l'application mises en œuvre, les étapes suivantes se produiront lorsque l'application s'exécutera :

1. La classe `Service` récupère les données des `Customer` à partir du fichier JSON et les stocke dans un `Repository`.
2. La `Table` intègre le `Repository` pour les données et peuple ses lignes de manière dynamique.

Avec la `Table` affichant maintenant des données `Customer`, l'étape suivante se concentrera sur la création d'un nouvel écran pour modifier les détails des clients et l'intégration de la navigation dans l'application.

Cela permettra d'organiser la logique de l'application plus efficacement en la déplaçant de la classe principale `App` vers les écrans constituants accessibles via des routes.
