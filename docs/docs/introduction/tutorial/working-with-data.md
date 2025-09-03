---
title: Working with data
sidebar_position: 3
---


This step adds data management and display capabilities to the demo app. You’ll define a `Customer` entity, use a Spring Data repository and service for data access, and display customer data in a [`Table`](../../components/table/overview) component.

This and all future steps use a H2 database. To use H2 with Spring Boot, add the H2 dependency to your pom.xml and set `spring.datasource.url=jdbc:h2:mem:testdb` in your `application.properties`. Spring Boot will auto-configure and start the H2 database for you.

By the end of this step, the app from the [previous step](./creating-a-basic-app) will display a table with customer data, ready for further extension. To run the app:

- Go to the `2-working-with-data` directory
- Run `mvn spring-boot:run`

<!-- vale off -->

<div class="videos-container">
  <video controls>
    <source src="https://cdn.webforj.com/webforj-documentation/video/tutorials/working-with-data.mp4" type="video/mp4"/>
  </video>
</div>

<!-- vale on -->


## Creating a data model

The `Customer` entity is a JPA model class, used with Spring Data and webforJ’s `Table`. It encapsulates customer attributes and provides a unique key for each row.

```java title="Customer.java"
@Entity
@Table(name = "customers")
public class Customer implements HasEntityKey {
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

  // Getters, setters, and getEntityKey() returning id
}
```


:::info Using `HasEntityKey` for unique identifiers
Implementing `HasEntityKey` allows the `Table` to uniquely identify each row using the entity’s primary key (`id`). This is important for correct row management and updates.
:::

With the `Customer` data model in place, the next step is to manage and organize these models within the app.


## Creating a service and repository

Spring Boot integration lets you use Spring Data repositories and services for data access. The `CustomerService` class provides methods to create, update, delete, and query customers, and exposes a `SpringDataRepository` for use with webforJ’s `Table`.

Note that there are three spring annotations used here:

- `@Service` marks a class as a service component in Spring, making it automatically detected and managed as a bean for business logic or reusable operations.

- `@Transactional` tells Spring to run the method or class within a database transaction, so all operations inside are committed or rolled back together. More detail is available in their official [documentation](https://docs.spring.io/spring-framework/reference/data-access/transaction/declarative/annotations.html#page-title).

- `@Autowired` tells Spring to automatically inject the required dependency into a field, constructor, or method. This means you don’t have to create the object yourself—Spring finds and supplies it from its app context. More detail is available in their official [documentation](https://docs.spring.io/spring-framework/reference/core/beans/annotation-config/autowired.html).

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
      throw new IllegalArgumentException("Customer not found with ID: " + customer.getEntityKey());
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
}
```

:::note
`SpringDataRepository` is a webforJ wrapper that lets you connect UI components directly to Spring Data repositories. It simplifies data binding and CRUD operations by allowing your webforJ tables and forms to work freely with your Spring-managed data layer.
:::


## Displaying data in a table

The main app class injects the `CustomerService` and configures a `Table<Customer>` to display customer data.

```java title="Application.java"
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

    mainFrame.addClassName("mainFrame");
    table.setHeight("294px");
    table.setWidth(1000);

    table.addColumn("First Name", Customer::getFirstName);
    table.addColumn("Last Name", Customer::getLastName);
    table.addColumn("Company", Customer::getCompany);
    table.addColumn("Country", Customer::getCountry);

    table.setRepository(customerService.getFilterableRepository());
    btn.setTheme(ButtonTheme.PRIMARY).addClickListener(e -> OptionDialog.showMessageDialog("This is a demo!", "Info"));
    mainFrame.add(demo, btn, table);
  }
}
```


With these changes, the app loads customer data from the database (or in-memory store), and displays it in a table. The next step will introduce routing and multiple views for editing and adding customers.
