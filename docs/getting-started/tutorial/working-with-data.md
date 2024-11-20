---
title: Working With Data
sidebar_-_position: 3
---

This step focuses on adding data management and display capabilities to the demo app. To do this, dummy data about various `Customer` objects will be created, and the app will be updated to handle this data and display it in a `Table` added to the previous app.

It will outline creating a `Customer` model class, and integrating it with a `Service` class to access and manage the necessary data using the repository pattern. Then, it will detail how to use the retrieved data to implement a `Table` component in the app, displaying customer information in an interactive and structured format. 

By the end of this step, the app created in the [previous step](./basic-app) will display a table with the created data that can then be expanded on in the following steps.

<img src={require('@site/static/img/tutorial_images/step2.png').default} alt="Screenshot of second app" className="tutorial-image" />

## `Customer` model

In order to create a `Table` that displays data in the main app, you'll first need to create a Java bean class that can be used with the `Table` to display data. 

Create the `Customer` class in `src/main/java/com/webforj/demos/data/Customer.java`. class serves as the core data model for the app, encapsulating customer-related attributes such as `firstName`, `lastName`, `company`, and `country`. In a more complex app, this model could also map to a database, but for this demo, it uses a simple structure that makes it easy to represent customer information.

### `HasEntityKey`

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

## `Service` class

Next create the `Service.java` class. This class serves as a way to access necessary data in various places throughout your app without needing to manually pass it between 
interested parties.

In this case the service will grab the data from the `src/main/resources/data/customers.json` and map it onto customer objects, which it will use to fill an `ArrayList`. This 
`ArrayList` is then used as a basis for a `Repository` which is covered later in this article.

The `Service` class uses a singleton-like pattern to manage customer data efficiently within the app. Instead of creating multiple instances, the app retrieves a single instance of `Service` using the `getCurrent()` method. This method utilizes `ObjectTable` to store the service instance under a unique key, ensuring only one instance exists throughout the app’s lifecycle.

### Using the `ObjectTable`

The above class uses an `ObjectTable` to manage object instances dynamically instead of using static data. Using the `ObjectTable`, ensures that data isn't restricted to a single state or instance, allowing for more dynamic data interactions. It also helps with scalability of the app and makes it easy to include and extend the objects in question.

```java 
public static Service getCurrent() {
    String key = "com.webforj.demos.data.service.instance";
    if (ObjectTable.contains(key)) {
      return (Service) ObjectTable.get(key);
    }

    Service instance = new Service();
    ObjectTable.put(key, instance);
    return instance;
  }
  ```



### Storing data in a `Repository`

:::info Data folder
The `data` folder, located in `src/main/resources/data`, contains essential configuration files and data used by the app. This folder is organized to support access to external resources, such as configuration files, data files, or assets required for the app’s operation.
:::

## Context URLs

The `contextURL` in webforJ refers to paths that are dynamically resolved at runtime, enabling the app to adaptively load resources based on the current context. This approach is used to manage URLs for data and other resources flexibly.

- **How to Resolve `contextURL` Paths**:
  - Use webforJ’s URL resolver methods to dynamically load assets or data files.
  - Resolve paths based on the current environment, supporting multiple deployment configurations or runtime changes.
  - Access data based on context URL definitions, which provides flexibility to adapt the resource paths without changing code.

In `Service.java` the `contextUrl`is utilized to load the customer data out of a JSON as follows:

```java
private List<Customer> buildDemoList() {
    ObjectMapper mapper = new ObjectMapper();

    try {
      return mapper.readValue(Assets.contentOf(Assets.resolveContextUrl("context://data/customers.json")),
          new TypeReference<List<Customer>>() {
          });
    } catch (IOException e) {
      return new ArrayList<>();
    }

  }
```

### Updated `DemoApplication.java`

Since the data for the `Table` is handled fully through the `Service` class the only thing necessary in the `DemoApplication.java` is to configure the `Table` and set the `Repository` available via the `Service`. 

To configure the `Table`, set an initial width and height and specify which columns the `Table` should have. Columns are comprised of the name of the column and the method to populate it.

```java title="DemoApplication.java"
// Imports

@InlineStyleSheet("context://css/demoApplication.css")
@AppTitle("Demo Step 2")
public class DemoApplication extends App {
  Paragraph demo = new Paragraph("Demo Application!");
  Button btn = new Button("Info");
  Table<Customer> table = new Table<>();

  @Override
  public void run() throws WebforjException {
    // Previous implementation of step one
    buildTable();
    mainFrame.add(demo, btn, table);
  }

  private void buildTable() {
    table.setHeight("300px");
    table.setWidth(1000);

    table.addColumn("First Name", Customer::getFirstName);
    table.addColumn("Last Name", Customer::getLastName);
    table.addColumn("Company", Customer::getCompany);
    table.addColumn("Country", Customer::getCountry);

    table.setRepository(Service.getCurrent().getCustomers());
  }
}
```

