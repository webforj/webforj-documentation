---
title: Working With Data
sidebar_position: 3
_i18n_hash: 3afbf6e4eb4921183cc11d87c8457150
---
Cette étape se concentre sur l'ajout de capacités de gestion et d'affichage des données à l'application de démonstration. Pour ce faire, des données fictives concernant divers objets `Customer` seront créées, et l'application sera mise à jour pour gérer ces données et les afficher dans un [`Table`](../../components/table/overview) ajouté à l'application précédente.

Elle décrira la création d'une classe modèle `Customer`, et son intégration avec une classe `Service` pour accéder et gérer les données nécessaires en utilisant l'implémentation d'un dépôt. Ensuite, elle détaillera comment utiliser les données récupérées pour implémenter un composant `Table` dans l'application, affichant les informations des clients dans un format interactif et structuré.

À la fin de cette étape, l'application créée dans l'[étape précédente](./creating-a-basic-app) affichera un tableau avec les données créées qui pourront ensuite être développées dans les étapes suivantes. Pour exécuter l'application :

- Allez dans le répertoire `2-working-with-data`
- Exécutez `mvn jetty:run`

## Création d'un modèle de données {#creating-a-data-model}

Pour créer un `Table` qui affiche des données dans l'application principale, une classe Java bean qui peut être utilisée avec le `Table` pour afficher des données doit être créée.

Dans ce programme, la classe `Customer` dans `src/main/java/com/webforj/demos/data/Customer.java` le fait. Cette classe sert de modèle de données principal pour l'application, encapsulant des attributs liés au client tels que `firstName`, `lastName`, `company` et `country`. Ce modèle contiendra également un identifiant unique.

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

:::info Utilisation de `HasEntityKey` pour les identifiants uniques

L'implémentation de l'interface `HasEntityKey` est cruciale pour la gestion des identifiants uniques dans les modèles utilisés avec un `Table`. Elle garantit que chaque instance du modèle possède une clé unique, permettant au `Table` d'identifier et de gérer efficacement les lignes.

Pour cette démonstration, la méthode `getEntityKey()` retourne un UUID pour chaque client, garantissant une identification unique. Bien que les UUID soient utilisés ici pour des raisons de simplicité, dans des applications réelles, une clé primaire de base de données est souvent un meilleur choix pour la génération de clés uniques.

Si `HasEntityKey` n'est pas implémenté, le `Table` par défaut utilisera le code hash Java comme clé. Étant donné que les codes hash ne sont pas garantis d'être uniques, cela peut entraîner des conflits lors de la gestion des lignes dans le `Table`.
:::

Avec le modèle de données `Customer` en place, la prochaine étape est de gérer et d'organiser ces modèles au sein de l'application.

## Création d'une classe `Service` {#creating-a-service-class}

Agissant comme un gestionnaire de données centralisé, la classe `Service` charge non seulement les données des `Customer` mais fournit également une interface efficace pour y accéder et interagir avec elle.

La classe `Service.java` est créée dans `src/main/java/com/webforj/demos/data`. Au lieu de passer manuellement les données entre les composants ou les classes, le `Service` agit comme une ressource partagée, permettant aux parties intéressées de récupérer et d'interagir facilement avec les données.

Dans cette démonstration, la classe `Service` lit les données des clients à partir d'un fichier JSON situé à `src/main/resources/data/customers.json`. Les données sont mappées sur des objets `Customer` et stockées dans une `ArrayList`, qui forme la base du `Repository` du tableau.

Dans webforJ, la classe `Repository` fournit un moyen structuré de gérer et de récupérer des collections d'entités. Elle agit comme une interface entre votre application et ses données, offrant des méthodes pour interroger, compter et actualiser les données tout en maintenant une structure propre et cohérente. Elle est utilisée par la classe `Table` pour afficher les données stockées.

Bien que le `Repository` n'inclue pas de méthodes pour mettre à jour ou supprimer des entités, il sert de wrapper structuré autour d'une collection d'objets. Cela le rend idéal pour fournir un accès aux données organisé et efficace.

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

Pour remplir le `Repository` avec des données, la classe `Service` agit comme le gestionnaire central, s'occupant du chargement et de l'organisation des actifs dans l'application. Les données clients sont lues à partir d'un fichier JSON et mappées aux objets `Customer` dans le `Repository`. 

L'utilitaire `Assets` dans webforJ facilite le chargement dynamique de ces données en utilisant des URL de contexte. Pour charger des actifs et des données dans webforJ, la classe `Service` utilise des URL de contexte avec l'utilitaire `Assets`. Par exemple, les données des clients peuvent être chargées à partir du fichier JSON comme suit :

```java
String content = Assets.contentOf(Assets.resolveContextUrl("context://data/customers.json"));
```

:::tip Utilisation de l'`ObjectTable`
La classe `Service` utilise l'`ObjectTable` pour gérer les instances de manière dynamique, plutôt que de s'appuyer sur des champs statiques. Cette approche aborde une limitation clé lors de l'utilisation des servlets : les champs statiques sont liés au cycle de vie du serveur et peuvent entraîner des problèmes dans des environnements avec plusieurs requêtes ou sessions concurrentes. L'`ObjectTable` est scoped à la session utilisateur, et son utilisation garantit un comportement semblable à un singleton sans ces limitations, permettant une gestion des données cohérente et évolutive.
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

## Création et utilisation d'un `Table` {#creating-and-using-a-table}

Maintenant que les données nécessaires ont été correctement créées via la classe `Customer`, et peuvent être renvoyées sous forme de `Repository` via la classe `Service`, la tâche finale de cette étape est d'intégrer le composant `Table` dans l'application pour afficher les données des clients.

:::tip En savoir plus sur le `Table`
Pour un aperçu plus détaillé des différentes caractéristiques et comportements du `Table`, consultez [cet article](../../components/table/overview).
:::

Le `Table` fournit un moyen dynamique et flexible d'afficher des données structurées dans votre application. Il est conçu pour s'intégrer à la classe `Repository`, permettant des fonctionnalités telles que la requête de données, la pagination et des mises à jour efficaces. Un `Table` est hautement configurable, vous permettant de définir des colonnes, de contrôler son apparence et de le lier à des dépôts de données avec un minimum d'effort.

### Implémentation du `Table` dans l'application {#implementing-the-table-in-the-app}

Puisque les données pour le `Table` sont entièrement gérées via la classe `Service`, la tâche principale dans `DemoApplication.java` est de configurer le `Table` et de le lier au `Repository` fourni par le `Service`.

Pour configurer le `Table` :

- Définissez sa largeur et sa hauteur pour les besoins de mise en page à l'aide des méthodes `setHeight()` et `setWidth()`.
- Définissez les colonnes, en spécifiant leurs noms et les méthodes pour récupérer les données pour chacune.
- Assignez le `Repository` pour fournir des données dynamiquement.

Après cela, le code ressemblera au snippet suivant :

```java title="DemoApplication.java"
public class DemoApplication extends App {
  // Autres composants de l'étape une

  // Le composant Table pour afficher les données des clients
  Table<Customer> table = new Table<>(); 

  @Override
  public void run() throws WebforjException {
    // Implémentation précédente de l'étape une
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

    // Lier le Table à un Repository contenant des données sur les clients
    // Le Repository est récupéré via la classe Service
    table.setRepository(Service.getCurrent().getCustomers());
  }
}
```

Avec les modifications terminées, les étapes suivantes se produiront lorsque l'application s'exécutera :

1. La classe `Service` récupère les données des clients à partir du fichier JSON et les stocke dans un `Repository`.
2. Le `Table` intègre le `Repository` pour les données et remplit ses lignes de manière dynamique.

Avec le `Table` affichant maintenant les données des `Customer`, la prochaine étape sera de créer un nouvel écran pour modifier les détails des clients et d'intégrer le routage dans l'application.

Cela permettra d'organiser la logique de l'application de manière plus efficace en la déplaçant hors de la classe `App` principale, et dans des écrans constitutifs accessibles via des routes.
