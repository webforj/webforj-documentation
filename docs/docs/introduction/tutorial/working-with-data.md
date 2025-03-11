---
title: Working With Data
sidebar_position: 3
---

This step focuses on adding data management and display capabilities to the demo app. To do this, dummy data about various `Customer` objects will be created, and the app will be updated to handle this data and display it in a [`Table`](../../components/table) added to the previous app.

It will outline creating a `Customer` model class, and integrating it with a `Service` class to access and manage the necessary data using the implementation of a repository. Then, it will detail how to use the retrieved data to implement a `Table` component in the app, displaying customer information in an interactive and structured format.

By the end of this step, the app created in the [previous step](./creating-a-basic-app) will display a table with the created data that can then be expanded on in the following steps. To run the app:

- Go to the `2-working-with-data` directory
- Run `mvn jetty:run`

<!-- vale off -->

<div class="videos-container">
  <video controls>
    <source src="/video/tutorials/working-with-data.mp4" type="video/mp4"/>
  </video>
</div>

<!-- vale on -->

## Creating a data model

In order to create a `Table` that displays data in the main app, a Java bean class that can be used with the `Table` to display data needs to be created.

In this program, the `Customer` class in `src/main/java/com/webforj/demos/data/Customer.java` does this. This class serves as the core data model for the app, encapsulating customer-related attributes such as `firstName`, `lastName`, `company`, and `country`. This model will also contain a unique ID.

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

    // Remaining countries
  }

    // Getters and Setters

  @Override
  public Object getEntityKey() {
    return uuid;
  }
}
```

:::info Using `HasEntityKey` for Unique Identifiers

Implementing the `HasEntityKey` interface is crucial for managing unique identifiers in models used with a `Table`. It ensures that each instance of the model has a unique key, allowing the `Table` to identify and manage rows effectively.

For this demo, the `getEntityKey()` method returns a UUID for each customer, ensuring unique identification. While UUIDs are used here for simplicity, in real-world applications, a database primary key is often a better choice for generating unique keys.

If `HasEntityKey` isn't implemented, the `Table` will default to using the Java hash code as the key. Since hash codes aren't guaranteed to be unique, this can cause conflicts when managing rows in the `Table`.
:::

With the `Customer` data model in place, the next step is to manage and organize these models within the app.

## Creating a `Service` class

Acting as a centralized data manager, the `Service` class not only loads `Customer` data but also provides an efficient interface for accessing and interacting with it.

The `Service.java` class is created in `src/main/java/com/webforj/demos/data`. Instead of manually passing data between components or classes, the `Service` acts as a shared resource, allowing interested parties to retrieve and interact with data easily.

In this demo, the `Service` class reads customer data from a JSON file located at `src/main/resources/data/customers.json`. The data is mapped onto `Customer` objects and stored in an `ArrayList`, which forms the foundation for the table's `Repository`.

In webforJ, the `Repository` class provides a structured way to manage and retrieve collections of entities. It acts as an interface between your app and its data, offering methods to query, count, and refresh data while maintaining a clean and consistent structure. It's used by the `Table` class to display the data stored within.

Although the `Repository` doesn’t include methods for updating or deleting entities, it serves as a structured wrapper around a collection of objects. This makes it ideal for providing organized, efficient data access.

```java
public class Service {
  private List<Customer> data = new ArrayList<>();
  private CollectionRepository<Customer> repository;

  private Service() {
    data = buildDemoList();
    repository = new CollectionRepository<>(data);
  }

  //Remaining implementation
}
```

To populate the `Repository` with data, the `Service` class acts as the central manager, handling the loading and organization of assets in the app. Customer data is read from a JSON file and mapped to the `Customer` objects in the `Repository`. 

The `Assets` utility in webforJ makes it easy to load this data dynamically using context URLs To load assets and data in webforJ, the `Service` class uses context URLs with the `Assets` utility. For example, customer data can be loaded from the JSON file as follows:

```java
String content = Assets.contentOf(Assets.resolveContextUrl("context://data/customers.json"));
```

:::tip Using the `ObjectTable`
The `Service` class uses the `ObjectTable` to manage instances dynamically, instead of relying on static fields. This approach addresses a key limitation when using servlets: static fields are tied to the server’s lifecycle and can lead to issues in environments with multiple requests or concurrent sessions. The `ObjectTable` is scoped to the user session, and using it ensures a singleton-like behavior without these limitations, enabling consistent and scalable data management.
:::

```java title="Service.java"
public class Service {

  private List<Customer> data = new ArrayList<>();
  private CollectionRepository<Customer> repository;

  // Private constructor to enforce controlled instantiation
  private Service() {
    // implementation
  }

  // Retrieves the current instance of Service or creates one if it doesn’t exist
  public static Service getCurrent() {
    // implementation
  }

  // Load customer data from the JSON file and map it to Customer objects
  private List<Customer> buildDemoList() {
    // implementation
  }

  // Getter...
}
```

## Creating and using a `Table`

Now that the data needed has been properly created via the `Customer` class, and can is returned as a `Repository` via the `Service` class, the final task in this step is to integrate the `Table` component into the app to display customer data.

:::tip More about the `Table`
For a more detailed overview of the various features of behaviors of the `Table`, see [this article](../../components/table).
:::

The `Table` provides a dynamic and flexible way to display structured data in your app. It's designed to integrate with the `Repository` class, enabling features like data querying, pagination, and efficient updates. A `Table` is highly configurable, allowing you to define columns, control its appearance, and bind it to data repositories with minimal effort.

### Implementing the `Table` in the app

Since the data for the `Table` is handled fully through the `Service` class, the main task in `DemoApplication.java` is configuring the `Table` and linking it to the `Repository` provided by the `Service`.

To configure the `Table`:

- Set its width and height for layout purposes using the `setHeight()` and `setWidth()` methods.
- Define the columns, specifying their names and the methods to fetch the data for each.
- Assign the `Repository` to provide data dynamically.

After doing this, the code will look similar to the following snippet:

```java title="DemoApplication.java"
public class DemoApplication extends App {
  // Other components from step one

  // The Table component for displaying Customer data
  Table<Customer> table = new Table<>();

  @Override
  public void run() throws WebforjException {
    // Previous implementation of step one
    buildTable();
    mainFrame.add(demo, btn, table);
  }

  private void buildTable() {
    // Set the table's height to 300 pixels
    table.setHeight("300px");
    // Set the table's width to 1000 pixels
    table.setWidth(1000);

    // Add the various column titles and assign the appropriate getters
    table.addColumn("First Name", Customer::getFirstName);
    table.addColumn("Last Name", Customer::getLastName);
    table.addColumn("Company", Customer::getCompany);
    table.addColumn("Country", Customer::getCountry);

    // Bind the Table to a Repository containing Customer data
    // The Repository is retrieved through the Service class
    table.setRepository(Service.getCurrent().getCustomers());
  }
}
```

With the completed changes to the app implemented, the following steps will happen when the app runs:

1. The `Service` class retrieves `Customer` data from the JSON file and stores it in a `Repository`.
2. The `Table` integrates the `Repository` for data and populates its rows dynamically.

With the `Table` now displaying `Customer` data, the next step will focus on creating a new screen to modify customer details and integrating routing into the app.

This will allow organization of the app’s logic more effectively by moving it out of the main `App` class, and into constituent screens access via routes.
