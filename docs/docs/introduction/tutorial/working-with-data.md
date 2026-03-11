---
title: Working with Data
sidebar_position: 3
description: Step 2 - Use Spring to work with data.
---

In this step, you’ll learn how to create a data model using Spring and display that data visually. 
By the end of this step, the app created in the previous step, [Creating a Basic App](./creating-a-basic-app), will have a table that displays data about customers. Following along will teach you about:

- Spring annotations
- Managing data
- The webforJ `Table` component

Completing this step creates a version of [2-working-with-data](https://github.com/webforj/webforj-tutorial/tree/main/2-working-with-data).

<!-- <div class="videos-container">
  <video controls>
    <source src="https://cdn.webforj.com/webforj-documentation/video/tutorials/working-with-data.mp4" type="video/mp4"/>
  </video>
</div> -->

## Running the app {#running-the-app}

As you develop your app, you can use [2-working-with-data](https://github.com/webforj/webforj-tutorial/tree/main/2-working-with-data) as a comparison. To see the app in action:

1. Navigate to the top-level directory containing the `pom.xml` file, this is `2-working-with-data` if you're following along with the version on GitHub.

2. Use the following Maven command to run the Spring Boot app locally:
    ```bash
    mvn
    ```

Running the app automatically opens a new browser at http://localhost:8080.

## Dependencies and configurations {#dependencies-and-configurations}

This tutorial uses [H2 database](https://www.h2database.com/html/main.html) and in a future step, the Jakarta Persistence API (JPA) through
[Spring Data JPA](https://docs.spring.io/spring-data/jpa/reference/index.html). This requires you to add dependencies to `pom.xml` and update `application.properties`. This will be the last time you’ll need to modify these two files for the rest of the tutorial.

In your POM, add the following dependencies:

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

In `application.properties`, inside `src/main/resources`, add the following:

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

:::info Accessing data
This tutorial uses an in-memory database and the default credentials for accessing data. Go to Spring's [Data Access](https://docs.spring.io/spring-boot/how-to/data-access.html) documentation to learn about specific Spring Boot configuration options.
:::

## Spring beans {#spring-beans}

One key part of using the Spring framework is understanding what beans are. Beans are objects with defined Spring annotations that make it easier for Spring to configure them by knowing the class's intended purpose. Go to Spring’s [Bean Overview](https://docs.spring.io/spring-framework/reference/core/beans/definition.html) documentation to learn more.

## Creating a data model {#creating-a-data-model}

Before visually displaying or creating the data, this tutorial needs a way to represent each customer's data, including their name, country, and company. Using Spring, this is done with a class that has a `@Entity` annotation.

Create a class in `src/main/java/com/webforj/tutorial/entity` named `Customer.java`. It should have the `@Entity` annotation and include getter and setter methods for the customer values, except for the `id`. Instead of using a creation method for `id` values, use the `@Id` and the `@GeneratedValue` annotations to guarantee every customer gets a unique `id`.


<!-- vale off -->
<ExpandableCode title="Customer.java" language="java" startLine={1} endLine={15}>
{`@Entity
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

`}
</ExpandableCode>
<!-- vale on -->

With the `Customer` data model in place, you can now start adding business logic to your app.

## Managing data {#managing-data}

After creating a data model, you’ll create a repository and a service to manage the customer data. Making these types of classes in your app lets you include operations such as adding, deleting, and updating customer records.

### Creating a repository {#creating-a-repository}

Creating a repository makes the entities' data accessible, so your app can contain multiple customers. The goal of this tutorial is to make the data editable, sortable, and validatable. You determine a repository’s capabilities by the Spring Data repository you use.

In a future step, [Validating and Binding Data](/docs/introduction/tutorial/validating-and-binding-data), you’ll require access to Spring Data JPA to validate customer properties. Therefore, the appropriate repository to use is the `JpaRepository`.

In `src/main/java/com/webforj/tutorial/repository`, create a repository interface that has the Spring `@Repository` annotation and extends `JpaRepository`. You’ll need to specify what type of entities are in this repository, and what type of object the `id` is. For good measure, also extend `JpaSpecificationExecutor`. This addition allows you to implement advanced filtering options later, if needed.

```java title="CustomerRepository.java"
@Repository
public interface CustomerRepository
        extends JpaRepository<Customer, Long>,
        JpaSpecificationExecutor<Customer> {
}
```

The `CustomerRepository` you just created won't have declared methods. The methods for managing the data (the app's business logic) will reside in a service class.

:::info Spring documentation links

Here are four links to Spring’s documentation that will help you better understand Spring repositories:

- [Working with Spring Data Repositories](https://docs.spring.io/spring-data/commons/reference/repositories.html)
- [Spring Data JPA Overview](https://docs.spring.io/spring-data/jpa/reference/index.html)
- [Spring Data JPA Specifications](https://docs.spring.io/spring-data/jpa/reference/jpa/specifications.html)
- [`JpaRepository`](https://docs.spring.io/spring-data/jpa/docs/current/api/org/springframework/data/jpa/repository/JpaRepository.html)
:::

### Creating a service {#creating-a-service}

In `src/main/java/com/webforj/tutorial/service`, create a `CustomerService` class. This service will contain methods to create, update, delete, and query customers by using `CustomerRepository`. 

Additionally, this service needs a mechanism to connect Spring Data repositories to webforJ's UI components. Using the `SpringDataRepository` webforJ class lets you create this bridge. It simplifies data binding and CRUD operations by allowing your webforJ tables and forms to work freely with your Spring-managed data layer. See more information about webforJ’s Spring integration in the [Spring Data JPA](/docs/integrations/spring/spring-data-jpa) article.

For this service class you'll use two Spring annotations:

- **`@Service`** - This marks a class as a service component in Spring, making it automatically detected and managed as a bean for business logic or reusable operations.

- **`@Transactional`** - This annotation tells Spring to run the method or class within a database transaction, so all operations inside are committed or rolled back together. More detail is available in Spring’s documentation, [Using @Transactional](https://docs.spring.io/spring-framework/reference/data-access/transaction/declarative/annotations.html#page-title).


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

## Loading initial data {#loading-initial-data}

For this tutorial, the initial customer data set comes from a JSON file. To prevent direct browser access, the file resource should be created outside of `src/main/resources/static`. For your convenience, you can create the JSON file inside `src/main/resources/data` using the following data:

<!-- vale off -->
<ExpandableCode title="customers.json" language="json" startLine={1} endLine={13}>
{`[
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
`}
</ExpandableCode>
<!-- vale on -->

Then, the app needs a way to retrieve this data when it starts. In `src/main/java/com/webforj/tutorial/config`, create a `DataInitializer` class. Now, when the app runs, if there are no customers detected, it will load customers from the JSON file, and put them into the H2 database:

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

## Displaying data visually {#displaying-data-visually}

The final part of this step is to use the [`Table`](/docs/components/table/overview) component and connect it to the Spring data.

An instance of a webforJ `Table` needs to have a data type to work, that’s the entity class made earlier in this step:

```java
Table<Customer> table = new Table<>();
```

Once you have a `Table`, each customer property gets its own column. For each column you add, use the property name, its getter method in the `Customer` entity, and the `setLabel()` method to display the information in the order that you want:

```java
table.addColumn("firstName", Customer::getFirstName).setLabel("First Name");
table.addColumn("lastName", Customer::getLastName).setLabel("Last Name");
table.addColumn("company", Customer::getCompany).setLabel("Company");
table.addColumn("country", Customer::getCountry).setLabel("Country");
```

After adding the columns, you need to specify which repository the `Table` should use to populate its data. This app gets the repository from the `getRepositoryAdapter()` method in the created `CustomerService`:

```java
table.setRepository(customerService.getRepositoryAdapter());
```

### Table sizing {#table-sizing}

For the table, you can use `setSize()` to set its size in pixels or other [CSS units](https://developer.mozilla.org/en-US/docs/Learn_web_development/Core/Styling_basics/Values_and_units). By setting a maximum width relative to the screen’s width, you help your app be  more adaptive to smaller screens.

For the columns, you can set the widths individually, or use one of the `Table` methods like `setColumnsToAutoFit()` to let webforJ handle the widths for you:

```java
table.setSize("1000px", "294px");
table.setMaxWidth("90vw");
table.setColumnsToAutoFit();
```

### User interactions {#user-interactions}

The `Table` component also has methods to control how users interact with the columns:

```java
table.setColumnsToResizable(false);
table.getColumns().forEach(column -> column.setSortable(true));
```

The highlighted portions of the `Application` class add the `Table` component, define its columns, and use `CustomerService` to retrieve the repository:

```java title="Application.java" {7-12,24-25,30-40,46-47}
@SpringBootApplication
@StyleSheet("ws://css/card.css")
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

## Next step {#next-step}

With these changes, the app loads customer data into the database, then displays it in a `Table` component. The next step, [Routing and Composites](/docs/introduction/tutorial/routing-and-composites), introduces routing and multiple views for adding new customers.