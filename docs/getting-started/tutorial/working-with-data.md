---
title: Working With Data
sidebar_position: 3
---

This step focuses on adding data management and display capabilities to the demo app. To do this, dummy data about various `Customer` objects will be created, and the app will be updated to handle this data and display it in a `Table` added to the previous app.

It will outline creating a `Customer` model class, and integrating it with a `Service` class to access and manage the necessary data using the repository pattern. Then, it will detail how to use the retrieved data to implement a `Table` component in the app, displaying customer information in an interactive and structured format.

By the end of this step, the app created in the [previous step](./basic-app) will display a table with the created data that can then be expanded on in the following steps.

To begin, go to the `2-step-2` directory, and run `mvn jetty:run`

<img src={require('@site/static/img/tutorial_images/step2.png').default} alt="Screenshot of second app" className="tutorial-image" />

## `Customer` model

In order to create a `Table` that displays data in the main app, you'll first need to create a Java bean class that can be used with the `Table` to display data.

Create the `Customer` class in `src/main/java/com/webforj/demos/data/Customer.java`. class serves as the core data model for the app, encapsulating customer-related attributes such as `firstName`, `lastName`, `company`, and `country`. In a more complex app, this model could also map to a database, but for this demo, it uses a simple structure that makes it easy to represent customer information.

### Implementing `HasEntityKey`

The `HasEntityKey` interface is essential for managing unique identifiers in models used with a `Table`. Implementing this interface ensures that every instance of the model has a unique key, which the `Table` uses to identify and manage rows.

By implementing `HasEntityKey`, you can control how unique keys are generated for your model. In this demo, the `getEntityKey()` method returns a UUID for each customer, ensuring each instance is uniquely identifiable. While this demo uses a UUID for simplicity, most real-world apps would typically use a key from a database, such as a primary key.

:::info Omitting `HasEntityKey`
If you don't implement `HasEntityKey`, the `Table` will fall back to using the Java hash code of the object as the key. However, Java allows different objects to have the same hash code, which can lead to conflicts when managing data in the Table.
:::

Once implemented, each instance of the class will call `UUID.randomUUID()`, and override the `getEntityKey()` method to return this unique identifier when needed:

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

## Creating a `Service` class

Next create the `Service.java` class in `src/main/java/com/webforj/demos/data`. The `Service` class provides a centralized way to manage and access data throughout your app. Instead of passing data manually between components or classes, the `Service` acts as a shared resource that interested parties can retrieve and interact with easily.

In this demo, the `Service` class reads customer data from a JSON file located at `src/main/resources/data/customers.json`. The data is mapped onto `Customer` objects and stored in an `Array-List`, which serves as the foundation for a `Repository`, which is discussed later in this article.

:::tip Using the `ObjectTable`
The `Service` class uses the `ObjectTable` to manage instances dynamically, instead of relying on static fields. This approach addresses a key limitation when using servelets: static fields are tied to the server’s lifecycle and can lead to issues in environments with multiple requests or concurrent sessions. The `ObjectTable` is scoped to the user session, and using it ensures a singleton-like behavior without these limitations, enabling consistent and scalable data management.
:::

<!-- TODO ask whether context or ws here -->
:::tip Loading the data
TODO
:::

```java
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

## Storing data in a `Repository`

In webforJ, the `Repository` class provides a structured way to manage and retrieve collections of entities. It acts as an interface between your app and its data, offering methods to query, count, and refresh data while maintaining a clean and consistent structure. It is used by the `Table` class to display the data stored within.

Although it doesn’t include methods for updating or deleting entities, it serves as a wrapper around a collection of objects, making it ideal for organized and efficient data access.

:::tip The `Repository` and ORMs 
In many apps, a `Repository` is often backed by an Object-Relational Mapper to map data to database records. However, in this tutorial, the `Repository` interacts directly with a list of `Customer` entities for simplicity.
:::

### Coupling a `Table` and `Repository`

The `Table` component in webforJ works with the `Repository` class by using its standardized interface for data access and management. This coupling allows the `Table` to display data dynamically, refresh its contents, and manage rows efficiently. This enables the `Table` to:

- **Retrieve Rows**: Use methods like `findAll()` or `findBy()` to populate rows in the `Table`.
- **Count Data**: Use the `size()` method to determine the total number of rows for pagination or display.
- **Refresh Data**: Use the `commit()` method to reload data and update the `Table` in real-time.

```java
// Create a repository from a list of customers
List<Customer> customerList = Service.getCurrent().getCustomers();
Repository<Customer> customerRepository = new CustomerRepository(customerList);

// Create a table and bind it to the repository
Table<Customer> customerTable = new Table<>(customerRepository);
```

### Using `commit()`

The `commit()` method in the `Repository` class keeps your app’s data and UI in sync. It provides a mechanism to refresh the data stored in the `Repository`, ensuring the latest state is reflected in the app.

This method can be used in two ways:

1) **Refreshing all data:**
  Calling `commit()` without arguments reloads all entities from the repository's underlying data source, such as a database or a service class.

2) **Refreshing a single sntity:**
  Calling `commit(T entity)` reloads a specific entity, ensuring its state matches the latest data source changes.

Call `commit()` when data in the `Repository` changes, such as after adding or modifying entities in the data source.

```java
// Refresh all customer data in the repository
customerRepository.commit();

// Refresh a single customer entity
Customer updatedCustomer = ...; // Updated from an external source
customerRepository.commit(updatedCustomer);

```

## Creating and using a `Table`

Now that the data needed has been properly created via the `Customer` class, and can is returned as a `Repository` via the `Service` class, the final task in this step is to integrate the `Table` component into the app to display customer data. 

:::tip More about the `Table`
For a more detailed overview of the various features of behaviors of the `Table`, see [this article](../../components/table/table).
:::

The `Table` provides a dynamic and flexible way to display structured data in your app. It's designed to integrate with the `Repository` class, enabling features like data querying, pagination, and efficient updates. A `Table` is highly configurable, allowing you to define columns, control its appearance, and bind it to data repositories with minimal effort.


### Implementing the `Table` in the app

Since the data for the `Table` is handled fully through the `Service` class, the main task in `DemoApplication.java` is configuring the `Table` and linking it to the `Repository` provided by the `Service`.

To configure the `Table`:

- Set its width and height for layout purposes using the `setHeight()` and `setWidth()` methods.
- Define the columns, specifying their names and the methods to fetch the data for each.
- Assign the `Repository` to provide data dynamically.

After doing this, the code should look similar to the following snippet:

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

After updating the code, the following steps will happen when the app runs:

1) The `Service` class retrieves `Customer` data from the JSON file and stores it in a `Repository`.
2) The `Table` integrates the `Repository` for data and populates its rows dynamically.
3) Changes in the `Repository`'s underlying structure can be reflected in the `Table` using the `commit()` method.

With the `Table` now displaying `Customer` data, the next step will focus on creating a new screen to modify customer details and integrating routing into the app. 

This will allow organization of the app’s logic more effectively by moving it out of the main `App` class, and into constituent screens access via routes.