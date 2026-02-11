---
title: Working with Data
sidebar_position: 3
description: Step 2 - Learn about creating a Spring data model.
---

In this step, you’ll learn how to create a data model using Spring and display that data visually. 
By the end of this step, the app created in the previous step, [Creating a Basic App](./creating-a-basic-app), will have a table that displays data about customers. Following along will teach you about:

- Spring annotations
- Managing data
- The webforJ `Table` component

Completing this step creates a version of [2-working-with-data](https://github.com/webforj/webforj-demo-application/tree/main/2-working-with-data).

<!-- <div class="videos-container">
  <video controls>
    <source src="https://cdn.webforj.com/webforj-documentation/video/tutorials/working-with-data.mp4" type="video/mp4"/>
  </video>
</div> -->

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
  <scope>runtime</scope>
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

Create a class in `src/main/java/com/webforj/demos/entity` named `Customer.java`. It should have the `@Entity` annotation and include getter and setter methods for the customer values.

```java title="Customer.java"
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
    UNKNOWN, GERMANY, ENGLAND, ITALY, USA
  }

  // Getters and setters

  public Long getId() {
    return id;
  }
}
```

:::note Unique IDs
The created data model will also need a unique `id` for each entity. Instead of using a creation method for `id` values, use the `@Id` and the `@GeneratedValue` annotations to guarantee every customer gets a unique `id`.

 ```java
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
```
:::

With the `Customer` data model in place, you can now start adding business logic to your app.

## Managing data {#managing-data}

After creating a data model, you’ll create a repository and a service to manage the customer data. Making these types of classes in your app lets you include operations such as adding, deleting, and updating customer records.

### Creating a repository {#creating-a-repository}

Create a repository interface in `src/main/java/com/webforj/demos/repository` that retrieves `Customer` entities. This interface must also have the Spring `@Repository` annotation:

```java title="CustomerRepository.java"
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long>, JpaSpecificationExecutor<Customer> {
}
```

This tutorial’s repository extends `JpaRepository` and `JpaSpecificationExecutor` instead of `CrudRepository` because in a future step, [Validating and Binding Data](/docs/introduction/tutorial/validating-and-binding-data), you’ll learn how to use Jakarta validation for the `Customer` entity.

Additionally, `CustomerRepository` doesn't have declared methods. The methods for managing the data (the app's business logic) will reside in a service class.

:::info Spring documentation links

Here are four links to Spring’s documentation that will help you better understand Spring repositories:

- [Working with Spring Data Repositories](https://docs.spring.io/spring-data/commons/reference/repositories.html)
- [Spring Data JPA Overview](https://docs.spring.io/spring-data/jpa/reference/index.html)
- [Spring Data JPA Specifications](https://docs.spring.io/spring-data/jpa/reference/jpa/specifications.html)
- [`JpaRepository`](https://docs.spring.io/spring-data/jpa/docs/current/api/org/springframework/data/jpa/repository/JpaRepository.html)
:::

### Creating a service {#creating-a-service}

In `src/main/java/com/webforj/demos/service`, create a `CustomerService` class. This service will contain methods to create, update, delete, and query customers by using `CustomerRepository`. It should also expose a way to get a `SpringDataRepository` for the webforJ `Table` component.

This service class you'll create uses three Spring annotations:

- **`@Service`** - This marks a class as a service component in Spring, making it automatically detected and managed as a bean for business logic or reusable operations.

- **`@Transactional`** - This annotation tells Spring to run the method or class within a database transaction, so all operations inside are committed or rolled back together. More detail is available in Spring’s documentation, [Using @Transactional](https://docs.spring.io/spring-framework/reference/data-access/transaction/declarative/annotations.html#page-title).

- **`@Autowired`** Spring uses this annotation to inject required dependencies. For this tutorial, it’s used for a constructor of a Spring component you’ve already defined, `CustomerRepository`. More detail is available in Spring’s documentation, [Using @Autowired](https://docs.spring.io/spring-framework/reference/core/beans/annotation-config/autowired.html).

```java title="CustomerService.java"
@Service
@Transactional
public class CustomerService {

  @Autowired
  private CustomerRepository repository;

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

  public SpringDataRepository<Customer, Long> getFilterableRepository() {
    return new SpringDataRepository<>(repository);
  }

  public Customer getCustomerByKey(Long id) {
    return repository.findById(id).get();
  }

  public boolean doesCustomerExist(Long id) {
    return repository.existsById(id);
  }

}
```

:::tip Filtering
Using the `SpringDataRepository` webforJ wrapper lets you connect UI components directly to Spring Data repositories. It simplifies data binding and CRUD operations by allowing your webforJ tables and forms to work freely with your Spring-managed data layer.
:::

## Loading initial data {#loading-initial-data}

For this tutorial, the initial customer data set comes from a static JSON file.
For your convenience, you can copy the following data and create the static JSON file inside `src/main/resources/static/data`:

<!-- vale off -->
<ExpandableCode title="customers.json" language="json" startLine={1} endLine={13}>
{`
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
`}
</ExpandableCode>
<!-- vale on -->

Then, the app needs a way to retrieve this data when it starts. In `src/main/java/com/webforj/demos/config`, create a `DataInitializer` class. Like the service class, this Spring component will also use the `@Autowired` annotation to inject needed dependencies.

Now, when the app runs, if there are no customers detected, it will load customers from the static JSON file, and put them into the H2 database:

```java title="DataInitializer.java"
@Component
public class DataInitializer implements CommandLineRunner {

  @Autowired
  private CustomerService customerService;

  @Override
  public void run(String... args) {
    if (customerService.getTotalCustomersCount() == 0) {
      loadCustomersFromJson();
    }
  }

  private void loadCustomersFromJson() {
    ObjectMapper mapper = new ObjectMapper();
    try (InputStream is = getClass().getResourceAsStream("/static/data/customers.json")) {
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

After adding the columns, you need to specify which repository the `Table` should use to populate its data. This app gets the repository from the `getFilterableRepository()` method in the created `CustomerService`:

```java
table.setRepository(customerService.getFilterableRepository());
```

As some final touches for this step, you can use `setSize()` to set the table's size in pixels or other [CSS units](https://developer.mozilla.org/en-US/docs/Learn_web_development/Core/Styling_basics/Values_and_units), and `setColumnsToAutoFit()` to automatically make each column the same width:

```java
table.setSize("1000px", "294px");
table.setColumnsToAutoFit();
```

The highlighted portions of the `Application` class add the `Table` component, define its columns, and use `CustomerService` to retrieve the repository:

```java title="Application.java" {6-10,21-32,34}
@SpringBootApplication
@StyleSheet("ws://css/app.css")
@AppTheme("system")
@AppProfile(name = "DemoApplication", shortName = "DemoApplication")
public class Application extends App {
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
    Paragraph demo = new Paragraph("Demo Application!");
    Button btn = new Button("Info");
    Table<Customer> table = new Table<>();

    mainFrame.addClassName("frame--border");
    table.setSize("1000px", "294px");

    table.addColumn("firstName", Customer::getFirstName).setLabel("First Name");
    table.addColumn("lastName", Customer::getLastName).setLabel("Last Name");
    table.addColumn("company", Customer::getCompany).setLabel("Company");
    table.addColumn("country", Customer::getCountry).setLabel("Country");
    table.setColumnsToAutoFit();

    table.setRepository(customerService.getFilterableRepository());
    btn.setTheme(ButtonTheme.PRIMARY).addClickListener(e -> OptionDialog.showMessageDialog("This is a demo!", "Info"));
    mainFrame.add(demo, btn, table);
  }

}
```

## Running the app {#running-the-app}

When you’ve finished this step, you can compare it to [2-working-with-data](https://github.com/webforj/webforj-demo-application/tree/main/2-working-with-data) on GitHub. To see the app in action:

1. Navigate to the top-level directory containing the `pom.xml` file, this is `2-working-with-data` if you're following along with the version on GitHub.

2. Use the following Maven command to run the Spring Boot app locally:
    ```bash
    mvn
    ```

3. Open your browser and go to http://localhost:8080 to view the app.

With these changes, the app loads customer data from the database (or in-memory store), and displays it in a `Table` component. The next step introduces routing and multiple views for editing and adding customers.