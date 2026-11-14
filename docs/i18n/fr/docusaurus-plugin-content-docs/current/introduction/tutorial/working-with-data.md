---
title: Travailler avec les données
sidebar_position: 3
description: Step 2 - Use Spring to work with data.
_i18n_hash: f1c000ec07cb278db9d60d5f90d71743
---
Dans cette étape, vous apprendrez à créer un modèle de données avec Spring et à afficher ces données visuellement.  
À la fin de cette étape, l’application créée à l’étape précédente, [Créer une application de base](/docs/introduction/tutorial/creating-a-basic-app), comportera un tableau qui affiche des données sur les clients. En suivant ce tutoriel, vous découvrirez :

- Les annotations Spring
- La gestion des données
- Le composant `Table` de webforJ

En terminant cette étape, vous créerez une version de [2-working-with-data](https://github.com/webforj/webforj-tutorial/tree/main/2-working-with-data).

<!-- <VideoPlayer video="tutorial/working-with-data" width={1920} height={1080} title="Working with data" /> -->

## Exécuter l’application {#running-the-app}

Pendant le développement de votre application, vous pouvez utiliser [2-working-with-data](https://github.com/webforj/webforj-tutorial/tree/main/2-working-with-data) comme référence. Pour voir l’application en action :

1. Accédez au répertoire racine qui contient le fichier `pom.xml`. Si vous suivez la version sur GitHub, il s’agit de `2-working-with-data`.

2. Utilisez la commande Maven suivante pour exécuter l’application Spring Boot localement :
    ```bash
    mvn
    ```

L’exécution de l’application ouvre automatiquement une nouvelle fenêtre de navigateur à l’adresse `http://localhost:8080`.

## Dépendances et configurations {#dependencies-and-configurations}

Ce tutoriel utilise la [base de données H2](https://www.h2database.com/html/main.html) et, lors d’une prochaine étape, l’API Jakarta Persistence (JPA) via
[Spring Data JPA](https://docs.spring.io/spring-data/jpa/reference/index.html). Vous devez pour cela ajouter des dépendances à `pom.xml` et mettre à jour `application.properties`. Ce sera la dernière fois que vous devrez modifier ces deux fichiers pour le reste du tutoriel.

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

Dans `application.properties`, situé dans `src/main/resources`, ajoutez ce qui suit :

```
# H2 Database configuration
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=

# JPA configuration
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.jpa.hibernate.ddl-auto=update
```

:::info Accéder aux données
Ce tutoriel utilise une base de données en mémoire et les identifiants par défaut pour accéder aux données. Consultez la documentation Spring sur [l’accès aux données](https://docs.spring.io/spring-boot/how-to/data-access.html) pour en savoir plus sur les options de configuration spécifiques à Spring Boot.
:::

## Beans Spring {#spring-beans}

Pour utiliser le framework Spring, il est essentiel de comprendre ce que sont les beans. Les beans sont des objets associés à des annotations Spring qui permettent à Spring de les configurer plus facilement en connaissant la fonction prévue de leur classe. Consultez la documentation Spring [Vue d’ensemble des beans](https://docs.spring.io/spring-framework/reference/core/beans/definition.html) pour en savoir plus.

## Créer un modèle de données {#creating-a-data-model}

Avant d’afficher ou de créer les données, ce tutoriel doit pouvoir représenter les données de chaque client, notamment son nom, son pays et son entreprise. Avec Spring, cela se fait à l’aide d’une classe annotée avec `@Entity`.

Créez une classe nommée `Customer.java` dans `src/main/java/com/webforj/tutorial/entity`. Elle doit comporter l’annotation `@Entity` ainsi que des méthodes getter et setter pour les valeurs du client, à l’exception de `id`. Au lieu d’utiliser une méthode de création pour les valeurs de `id`, utilisez les annotations `@Id` et `@GeneratedValue` pour garantir que chaque client possède un `id` unique.


<!-- vale off -->
<ExpandableCode title="Customer.java" language="java" startLine={1} endLine={15}>

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

</ExpandableCode>
<!-- vale on -->

Une fois le modèle de données `Customer` en place, vous pouvez commencer à ajouter la logique métier à votre application.

## Gérer les données {#managing-data}

Après avoir créé un modèle de données, vous allez créer un dépôt et un service pour gérer les données des clients. Ces types de classes vous permettent d’intégrer à votre application des opérations telles que l’ajout, la suppression et la mise à jour des dossiers des clients.

### Créer un dépôt {#creating-a-repository}

La création d’un dépôt rend les données des entités accessibles et permet à votre application de gérer plusieurs clients. L’objectif de ce tutoriel est de rendre les données modifiables, triables et validables. Les fonctionnalités d’un dépôt dépendent du dépôt Spring Data que vous utilisez.

Dans une prochaine étape, [Valider et lier des données](/docs/introduction/tutorial/validating-and-binding-data), vous aurez besoin d’accéder à Spring Data JPA pour valider les propriétés des clients. Le dépôt approprié à utiliser est donc `JpaRepository`.

Dans `src/main/java/com/webforj/tutorial/repository`, créez une interface de dépôt annotée avec `@Repository` de Spring et qui étend `JpaRepository`. Vous devez préciser le type d’entités de ce dépôt ainsi que le type d’objet de `id`. Par souci d’exhaustivité, étendez également `JpaSpecificationExecutor`. Cela vous permettra d’implémenter ultérieurement des options de filtrage avancées, si nécessaire.

```java title="CustomerRepository.java"
@Repository
public interface CustomerRepository
        extends JpaRepository<Customer, Long>,
        JpaSpecificationExecutor<Customer> {
}
```

Le `CustomerRepository` que vous venez de créer ne déclare aucune méthode. Les méthodes de gestion des données (la logique métier de l’application) se trouveront dans une classe de service.

:::info Liens vers la documentation Spring

Voici quatre liens vers la documentation Spring qui vous aideront à mieux comprendre les dépôts Spring :

- [Utiliser les dépôts Spring Data](https://docs.spring.io/spring-data/commons/reference/repositories.html)
- [Présentation de Spring Data JPA](https://docs.spring.io/spring-data/jpa/reference/index.html)
- [Spécifications Spring Data JPA](https://docs.spring.io/spring-data/jpa/reference/jpa/specifications.html)
- [`JpaRepository`](https://docs.spring.io/spring-data/jpa/docs/current/api/org/springframework/data/jpa/repository/JpaRepository.html)
:::

### Créer un service {#creating-a-service}

Dans `src/main/java/com/webforj/tutorial/service`, créez une classe `CustomerService`. Ce service contiendra des méthodes pour créer, mettre à jour, supprimer et rechercher des clients à l’aide de `CustomerRepository`.

De plus, ce service doit disposer d’un mécanisme permettant de relier les dépôts Spring Data aux composants d’interface utilisateur de webforJ. La classe webforJ `SpringDataRepository` permet de créer cette passerelle. Elle simplifie la liaison des données et les opérations CRUD en permettant aux tableaux et aux formulaires webforJ d’interagir librement avec la couche de données gérée par Spring. Pour en savoir plus sur l’intégration de Spring à webforJ, consultez l’article [Spring Data JPA](/docs/integrations/spring/spring-data-jpa).

Pour cette classe de service, vous utiliserez deux annotations Spring :

- **`@Service`** - Cette annotation marque une classe comme composant de service dans Spring. Elle est ainsi détectée et gérée automatiquement comme un bean destiné à la logique métier ou à des opérations réutilisables.

- **`@Transactional`** - Cette annotation indique à Spring d’exécuter la méthode ou la classe au sein d’une transaction de base de données, afin que toutes les opérations soient validées ou annulées ensemble. Pour plus de détails, consultez la documentation Spring, [Utiliser @Transactional](https://docs.spring.io/spring-framework/reference/data-access/transaction/declarative/annotations.html#page-title).


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
      throw new IllegalArgumentException("Customer not found with ID: " + customer.getId());
    }
    return repository.save(customer);
  }

  public void deleteCustomer(Long id) {
    if (!repository.existsById(id)) {
      throw new IllegalArgumentException("Customer not found with ID: " + id);
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
        .orElseThrow(() -> new IllegalArgumentException("Customer not found with ID: " + id));
  }

  public boolean doesCustomerExist(Long id) {
    return repository.existsById(id);
  }

}
```

## Charger les données initiales {#loading-initial-data}

Pour ce tutoriel, les données initiales des clients proviennent d’un fichier JSON. C’est l’application Java, et non le navigateur, qui charge le fichier. Créez-le donc dans `src/main/resources/data` à l’aide des données suivantes :

<!-- vale off -->
<ExpandableCode title="customers.json" language="json" startLine={1} endLine={13}>

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

</ExpandableCode>
<!-- vale on -->

L’application doit ensuite pouvoir récupérer ces données au démarrage. Dans `src/main/java/com/webforj/tutorial/config`, créez une classe `DataInitializer`. Ainsi, lorsque l’application démarre, si aucun client n’est détecté, elle charge les clients depuis le fichier JSON et les ajoute à la base de données H2 :

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

## Afficher les données visuellement {#displaying-data-visually}

La dernière partie de cette étape consiste à utiliser le composant [`Table`](/docs/components/table/overview) et à le connecter aux données Spring.

Une instance de `Table` de webforJ doit disposer d’un type de données pour fonctionner : il s’agit de la classe d’entité créée précédemment dans cette étape.

```java
Table<Customer> table = new Table<>();
```

Une fois le `Table` créé, chaque propriété du client reçoit sa propre colonne. Pour chaque colonne que vous ajoutez, indiquez le nom de la propriété, sa méthode getter dans l’entité `Customer` et utilisez la méthode `setLabel()` pour afficher les informations dans l’ordre souhaité :

```java
table.addColumn("firstName", Customer::getFirstName).setLabel("First Name");
table.addColumn("lastName", Customer::getLastName).setLabel("Last Name");
table.addColumn("company", Customer::getCompany).setLabel("Company");
table.addColumn("country", Customer::getCountry).setLabel("Country");
```

Après avoir ajouté les colonnes, vous devez indiquer le dépôt que le `Table` doit utiliser pour remplir ses données. Cette application récupère le dépôt à partir de la méthode `getRepositoryAdapter()` du `CustomerService` créé précédemment :

```java
table.setRepository(customerService.getRepositoryAdapter());
```

### Dimensionner le tableau {#table-sizing}

Pour le tableau, vous pouvez utiliser `setSize()` afin de définir ses dimensions en pixels ou dans d’autres [unités CSS](https://developer.mozilla.org/en-US/docs/Learn_web_development/Core/Styling_basics/Values_and_units). En définissant une largeur maximale relative à la largeur de l’écran, vous rendez votre application plus adaptable aux écrans plus petits.

Vous pouvez définir individuellement la largeur des colonnes ou utiliser l’une des méthodes de `Table`, comme `setColumnsToAutoFit()`, pour laisser webforJ gérer leurs dimensions :

```java
table.setSize("1000px", "294px");
table.setMaxWidth("90vw");
table.setColumnsToAutoFit();
```

### Interactions utilisateur {#user-interactions}

Le composant `Table` propose également des méthodes pour contrôler la manière dont les utilisateurs interagissent avec les colonnes :

```java
table.setColumnsToResizable(false);
table.getColumns().forEach(column -> column.setSortable(true));
```

Les sections mises en évidence de la classe `Application` ajoutent le composant `Table`, définissent ses colonnes et utilisent `CustomerService` pour récupérer le dépôt :

```java title="Application.java" {7-12,24-25,30-40,46-47}
@SpringBootApplication
@BundleEntry("css/card.css")
@AppTheme("system")
@AppProfile(name = "Customer Application", shortName = "CustomerApp")
public class Application extends App {

  //Add a constructor injection for CustomerService
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
    Paragraph tutorial = new Paragraph("Tutorial App!");
    Button btn = new Button("Info");

    //Add the Table component
    Table<Customer> table = new Table<>();

    mainFrame.setWidth("fit-content");
    mainFrame.addClassName("card");

    //Style the Table component, set the columns, and set the repository
    table.setSize("1000px", "294px");
    table.setMaxWidth("90vw");
    table.addColumn("firstName", Customer::getFirstName).setLabel("First Name");
    table.addColumn("lastName", Customer::getLastName).setLabel("Last Name");
    table.addColumn("company", Customer::getCompany).setLabel("Company");
    table.addColumn("country", Customer::getCountry).setLabel("Country");
    table.setColumnsToAutoFit();
    table.setColumnsToResizable(false);
    table.getColumns().forEach(column -> column.setSortable(true));
    table.setRepository(customerService.getRepositoryAdapter());

    btn.setTheme(ButtonTheme.PRIMARY)
        .setMaxWidth(200)
        .addClickListener(e -> OptionDialog.showMessageDialog("This is a tutorial!", "Info"));

    //Add the Table to the Frame
    mainFrame.add(tutorial, btn, table);
  }

}
```

## Étape suivante {#next-step}

Grâce à ces modifications, l’application charge les données des clients dans la base de données, puis les affiche dans un composant `Table`. L’étape suivante, [Routage et composants composites](/docs/introduction/tutorial/routing-and-composites), présente le routage et les vues multiples permettant d’ajouter de nouveaux clients.
