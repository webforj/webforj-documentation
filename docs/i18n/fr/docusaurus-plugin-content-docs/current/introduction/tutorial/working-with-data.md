---
title: Working with Data
sidebar_position: 3
description: Step 2 - Use Spring to work with data.
_i18n_hash: eb93bafc77e98de6453cfb0fa0ea60a3
---
Dans cette étape, vous apprendrez à créer un modèle de données utilisant Spring et à afficher visuellement ces données. À la fin de cette étape, l'application créée lors de l'étape précédente, [Créer une application de base](./creating-a-basic-app), disposera d'un tableau affichant des données sur les clients. Suivre cette étape vous enseignera :

- Les annotations Spring
- La gestion des données
- Le composant `Table` de webforJ

En complétant cette étape, vous créerez une version de [2-working-with-data](https://github.com/webforj/webforj-tutorial/tree/main/2-working-with-data).

## Exécution de l'application {#running-the-app}

Au fur et à mesure que vous développez votre application, vous pouvez utiliser [2-working-with-data](https://github.com/webforj/webforj-tutorial/tree/main/2-working-with-data) comme comparaison. Pour voir l'application en action :

1. Naviguez vers le répertoire de niveau supérieur contenant le fichier `pom.xml`, c'est `2-working-with-data` si vous suivez la version sur GitHub.

2. Utilisez la commande Maven suivante pour exécuter l'application Spring Boot localement :
    ```bash
    mvn
    ```

L'exécution de l'application ouvre automatiquement un nouveau navigateur à l'adresse `http://localhost:8080`.

## Dépendances et configurations {#dependencies-and-configurations}

Ce tutoriel utilise la [base de données H2](https://www.h2database.com/html/main.html) et, dans une étape future, l'API Jakarta Persistence (JPA) via [Spring Data JPA](https://docs.spring.io/spring-data/jpa/reference/index.html). Cela nécessite d'ajouter des dépendances dans le `pom.xml` et de mettre à jour `application.properties`. Ce sera la dernière fois que vous devrez modifier ces deux fichiers pour le reste du tutoriel.

Dans votre POM, ajoutez les dépendances suivantes :

```xml
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>
<dependency>
  <groupId>com.h2database</groupId>
  <artifactId>h2</artifactId>
</dependency>
```

Dans `application.properties`, à l'intérieur de `src/main/resources`, ajoutez ce qui suit :

```
# Configuration de la base de données H2
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=

# Configuration de JPA
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.jpa.hibernate.ddl-auto=update
```

:::info Accéder aux données
Ce tutoriel utilise une base de données en mémoire et les identifiants par défaut pour accéder aux données. Consultez la documentation sur [l'accès aux données](https://docs.spring.io/spring-boot/how-to/data-access.html) de Spring pour en apprendre davantage sur les options de configuration spécifiques à Spring Boot.
:::

## Beans Spring {#spring-beans}

Une partie clé de l'utilisation du framework Spring est de comprendre ce que sont les beans. Les beans sont des objets avec des annotations Spring définies qui facilitent la configuration par Spring en connaissant l'objectif de la classe. Consultez la [vue d'ensemble des beans](https://docs.spring.io/spring-framework/reference/core/beans/definition.html) de Spring pour en savoir plus.

## Création d'un modèle de données {#creating-a-data-model}

Avant d'afficher visuellement ou de créer les données, ce tutoriel a besoin d'un moyen de représenter les données de chaque client, y compris leur nom, leur pays et leur entreprise. Avec Spring, cela se fait à l'aide d'une classe dotée de l'annotation `@Entity`.

Créez une classe dans `src/main/java/com/webforj/tutorial/entity` nommée `Customer.java`. Elle doit avoir l'annotation `@Entity` et inclure des méthodes getter et setter pour les valeurs du client, sauf pour l'`id`. Au lieu d'utiliser une méthode de création pour les valeurs `id`, utilisez les annotations `@Id` et `@GeneratedValue` pour garantir que chaque client obtient un `id` unique.

```java
@Entity
  @Table(name = "customers")
  public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName = "";
    private String lastName = "";
    private String company = "";
    private Country country = Country.UNKNOWN;

    public enum Country {
      UNKNOWN,
      GERMANY,
      ENGLAND,
      ITALY,
      USA
    }

    public Customer(String firstName, String lastName, String company, Country country) {
      setFirstName(firstName);
      setLastName(lastName);
      setCompany(company);
      setCountry(country);
    }

    public Customer(String firstName, String lastName, String company) {
      this(firstName, lastName, company, Country.UNKNOWN);
    }

    public Customer(String firstName, String lastName) {
      this(firstName, lastName, "");
    }

    public Customer(String firstName) {
      this(firstName, "");
    }

    public Customer() {
    }

    public void setFirstName(String newName) {
      firstName = newName;
    }

    public String getFirstName() {
      return firstName;
    }

    public void setLastName(String newName) {
      lastName = newName;
    }

    public String getLastName() {
      return lastName;
    }

    public void setCompany(String newCompany) {
      company = newCompany;
    }

    public String getCompany() {
      return company;
    }

    public void setCountry(Country newCountry) {
      country = newCountry;
    }

    public Country getCountry() {
      return country;
    }

    public Long getId() {
      return id;
    }

  }
```

Avec le modèle de données `Customer` en place, vous pouvez maintenant commencer à ajouter de la logique métier à votre application.

## Gestion des données {#managing-data}

Après avoir créé un modèle de données, vous créerez un dépôt et un service pour gérer les données des clients. La création de ces types de classes dans votre application vous permet d'inclure des opérations telles que l'ajout, la suppression et la mise à jour des enregistrements clients.

### Création d'un dépôt {#creating-a-repository}

La création d'un dépôt rend les données des entités accessibles, afin que votre application puisse contenir plusieurs clients. L'objectif de ce tutoriel est de rendre les données éditables, triables et validables. Vous déterminez les capacités d'un dépôt par le dépôt Spring Data que vous utilisez.

Dans une étape future, [Validation et liaison des données](/docs/introduction/tutorial/validating-and-binding-data), vous aurez besoin d'accéder à Spring Data JPA pour valider les propriétés des clients. Par conséquent, le dépôt approprié à utiliser est le `JpaRepository`.

Dans `src/main/java/com/webforj/tutorial/repository`, créez une interface de dépôt qui a l'annotation Spring `@Repository` et qui étend `JpaRepository`. Vous devrez spécifier quel type d'entités se trouve dans ce dépôt, ainsi que le type d'objet que représente l'`id`. Pour plus de sécurité, étendez également `JpaSpecificationExecutor`. Cet ajout vous permet de mettre en œuvre des options de filtrage avancées plus tard, si nécessaire.

```java title="CustomerRepository.java"
@Repository
public interface CustomerRepository
        extends JpaRepository<Customer, Long>,
        JpaSpecificationExecutor<Customer> {
}
```

Le `CustomerRepository` que vous venez de créer n'aura pas de méthodes déclarées. Les méthodes de gestion des données (la logique métier de l'application) se trouveront dans une classe de service.

:::info Liens vers la documentation Spring

Voici quatre liens vers la documentation de Spring qui vous aideront à mieux comprendre les dépôts Spring :

- [Travailler avec des dépôts Spring Data](https://docs.spring.io/spring-data/commons/reference/repositories.html)
- [Vue d'ensemble de Spring Data JPA](https://docs.spring.io/spring-data/jpa/reference/index.html)
- [Spécifications de Spring Data JPA](https://docs.spring.io/spring-data/jpa/reference/jpa/specifications.html)
- [`JpaRepository`](https://docs.spring.io/spring-data/jpa/docs/current/api/org/springframework/data/jpa/repository/JpaRepository.html)
:::

### Création d'un service {#creating-a-service}

Dans `src/main/java/com/webforj/tutorial/service`, créez une classe `CustomerService`. Ce service contiendra des méthodes pour créer, mettre à jour, supprimer et interroger des clients en utilisant `CustomerRepository`.

De plus, ce service a besoin d'un mécanisme pour connecter les dépôts Spring Data aux composants UI de webforJ. En utilisant la classe `SpringDataRepository` de webforJ, vous pouvez créer ce pont. Cela simplifie la liaison des données et les opérations CRUD en permettant à vos tables et formulaires webforJ de fonctionner librement avec votre couche de données gérée par Spring. Consultez plus d'informations sur l'intégration de Spring par webforJ dans l'article [Spring Data JPA](/docs/integrations/spring/spring-data-jpa).

Pour cette classe de service, vous utiliserez deux annotations Spring :

- **`@Service`** - Cela marque une classe comme un composant de service dans Spring, ce qui la rend automatiquement détectée et gérée comme un bean pour la logique métier ou les opérations réutilisables.

- **`@Transactional`** - Cette annotation indique à Spring d'exécuter la méthode ou la classe dans une transaction de base de données, de sorte que toutes les opérations à l'intérieur soient engagées ou annulées ensemble. Plus de détails sont disponibles dans la documentation de Spring, [Utilisation de @Transactional](https://docs.spring.io/spring-framework/reference/data-access/transaction/declarative/annotations.html#page-title).

```java title="CustomerService.java"
@Service
@Transactional
public class CustomerService {
  private final CustomerRepository repository;

  public CustomerService(CustomerRepository repository) {
    this.repository = repository;
  }

  public Customer createCustomer(Customer customer) {
    return repository.save(customer);
  }

  public Customer updateCustomer(Customer customer) {
    if (!repository.existsById(customer.getId())) {
      throw new IllegalArgumentException("Client non trouvé avec l'ID : " + customer.getId());
    }
    return repository.save(customer);
  }

  public void deleteCustomer(Long id) {
    if (!repository.existsById(id)) {
      throw new IllegalArgumentException("Client non trouvé avec l'ID : " + id);
    }
    repository.deleteById(id);
  }

  public long getTotalCustomersCount() {
    return repository.count();
  }

  public SpringDataRepository<Customer, Long> getRepositoryAdapter() {
    return new SpringDataRepository<>(repository);
  }

  public Customer getCustomerByKey(Long id) {
    return repository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Client non trouvé avec l'ID : " + id));
  }

  public boolean doesCustomerExist(Long id) {
    return repository.existsById(id);
  }

}
```

## Chargement des données initiales {#loading-initial-data}

Pour ce tutoriel, l'ensemble initial de données clients provient d'un fichier JSON. Pour éviter l'accès direct par le navigateur, la ressource de fichier doit être créée en dehors de `src/main/resources/static`. Pour votre commodité, vous pouvez créer le fichier JSON à l'intérieur de `src/main/resources/data` en utilisant les données suivantes :

```json
[
    {
      "firstName": "Alice",
      "lastName": "Smith",
      "company": "TechCorp",
      "country": "GERMANY"
    },
    {
      "firstName": "John",
      "lastName": "Doe",
      "company": "Innovatech",
      "country": "ITALY"
    },
    {
      "firstName": "Emma",
      "lastName": "Brown",
      "company": "SoftSolutions",
      "country": "ENGLAND"
    },
    {
      "firstName": "Liam",
      "lastName": "Jones",
      "company": "FinWise",
      "country": "UNKNOWN"
    },
    {
      "firstName": "Sophia",
      "lastName": "Taylor",
      "company": "DataWorks",
      "country": "GERMANY"
    },
    {
      "firstName": "Noah",
      "lastName": "Wilson",
      "company": "EcoBuild",
      "country": "ITALY"
    },
    {
      "firstName": "Olivia",
      "lastName": "Moore",
      "company": "NextGen",
      "country": "ENGLAND"
    },
    {
      "firstName": "James",
      "lastName": "Anderson",
      "company": "BlueTech",
      "country": "UNKNOWN"
    },
    {
      "firstName": "Isabella",
      "lastName": "Thomas",
      "company": "FutureLogic",
      "country": "GERMANY"
    },
    {
      "firstName": "Lucas",
      "lastName": "White",
      "company": "GreenEnergy",
      "country": "ITALY"
    }
]
```

Ensuite, l'application doit avoir un moyen de récupérer ces données lors de son démarrage. Dans `src/main/java/com/webforj/tutorial/config`, créez une classe `DataInitializer`. Maintenant, lorsque l'application s'exécute, si aucun client n'est détecté, elle chargera les clients depuis le fichier JSON et les mettra dans la base de données H2 :

```java title="DataInitializer.java"
@Component
public class DataInitializer implements CommandLineRunner {
  private final CustomerService customerService;

  public DataInitializer(CustomerService customerService) {
    this.customerService = customerService;
  }

  @Override
  public void run(String... args) {
    if (customerService.getTotalCustomersCount() == 0) {
      loadCustomersFromJson();
    }
  }

  private void loadCustomersFromJson() {
    ObjectMapper mapper = new ObjectMapper();
    try (InputStream is = getClass().getResourceAsStream("/data/customers.json")) {
      List<Customer> customers = mapper.readValue(is, new TypeReference<List<Customer>>() {
      });
      for (Customer customer : customers) {
        customerService.createCustomer(customer);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
```

## Affichage des données visuellement {#displaying-data-visually}

La dernière partie de cette étape consiste à utiliser le composant [`Table`](/docs/components/table/overview) et à le connecter aux données Spring.

Une instance du `Table` de webforJ doit avoir un type de données pour fonctionner, qui est la classe d'entité créée plus tôt dans cette étape :

```java
Table<Customer> table = new Table<>();
```

Une fois que vous avez un `Table`, chaque propriété de client obtient sa propre colonne. Pour chaque colonne que vous ajoutez, utilisez le nom de la propriété, sa méthode getter dans l'entité `Customer`, et la méthode `setLabel()` pour afficher les informations dans l'ordre que vous souhaitez :

```java
table.addColumn("firstName", Customer::getFirstName).setLabel("Prénom");
table.addColumn("lastName", Customer::getLastName).setLabel("Nom");
table.addColumn("company", Customer::getCompany).setLabel("Société");
table.addColumn("country", Customer::getCountry).setLabel("Pays");
```

Après avoir ajouté les colonnes, vous devez préciser quel dépôt le `Table` doit utiliser pour peupler ses données. Cette application obtient le dépôt depuis la méthode `getRepositoryAdapter()` dans le `CustomerService` créé :

```java
table.setRepository(customerService.getRepositoryAdapter());
```

### Dimensionnement de la table {#table-sizing}

Pour la table, vous pouvez utiliser `setSize()` pour définir sa taille en pixels ou d'autres [unités CSS](https://developer.mozilla.org/en-US/docs/Learn_web_development/Core/Styling_basics/Values_and_units). En définissant une largeur maximale par rapport à la largeur de l'écran, vous aidez votre application à être plus adaptable aux écrans plus petits.

Pour les colonnes, vous pouvez définir les largeurs individuellement ou utiliser l'une des méthodes `Table` comme `setColumnsToAutoFit()` pour laisser webforJ gérer les largeurs pour vous :

```java
table.setSize("1000px", "294px");
table.setMaxWidth("90vw");
table.setColumnsToAutoFit();
```

### Interactions utilisateur {#user-interactions}

Le composant `Table` a également des méthodes pour contrôler comment les utilisateurs interagissent avec les colonnes :

```java
table.setColumnsToResizable(false);
table.getColumns().forEach(column -> column.setSortable(true));
```

Les parties mises en évidence de la classe `Application` ajoutent le composant `Table`, définissent ses colonnes et utilisent `CustomerService` pour récupérer le dépôt :

```java title="Application.java" {7-12,24-25,30-40,46-47}
@SpringBootApplication
@StyleSheet("ws://css/card.css")
@AppTheme("system")
@AppProfile(name = "Application Client", shortName = "CustomerApp")
public class Application extends App {
  
  // Ajoutez une injection de constructeur pour CustomerService
  private final CustomerService customerService;

  public Application(CustomerService customerService) {
    this.customerService = customerService;
  }

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  @Override
  public void run() throws WebforjException {
    Frame mainFrame = new Frame();
    Paragraph tutorial = new Paragraph("Application Tutoriel !");
    Button btn = new Button("Info");

    // Ajoutez le composant Table
    Table<Customer> table = new Table<>();

    mainFrame.setWidth("fit-content");
    mainFrame.addClassName("card");

    // Stylez le composant Table, définissez les colonnes et définissez le dépôt
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

    btn.setTheme(ButtonTheme.PRIMARY)
        .setMaxWidth(200)
        .addClickListener(e -> OptionDialog.showMessageDialog("Ceci est un tutoriel !", "Info"));

    // Ajoutez la Table au Frame
    mainFrame.add(tutorial, btn, table);
  }

}
```

## Étape suivante {#next-step}

Avec ces modifications, l'application charge les données des clients dans la base de données, puis les affiche dans un composant `Table`. L'étape suivante, [Routage et composites](/docs/introduction/tutorial/routing-and-composites), introduit le routage et plusieurs vues pour ajouter de nouveaux clients.
