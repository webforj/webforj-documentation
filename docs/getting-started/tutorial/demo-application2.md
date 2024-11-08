# Second step
## Goals and resources

By completing this step, you will:

- Implement a `Table` to display data in the demo app.
- Integrate a data model with the `Table`, using the repository pattern for organized data access and management.
- Understand how to use `ObjectTable` for single-instance management, context URLs for dynamic resource paths, and `HasEntityKey` to assign unique identifiers to model instances.

The following articles will explain in detail some of the concepts discussed in this step:

<!-- TODO add list of articles -->

## `Customer` model

Create the `Customer` class in `src/main/java/com/webforj/demos/Customer.java`. It represents the app's core data model, encapsulating key attributes such as `firstName`, `lastName`, `company` and `country`. This model could be mapped to a database in a more complex app, and plays a crucial role in organizing customer-related data for the demo.

```java title="Customer.java"
public class Customer implements HasEntityKey {
  private String firstName = "";
  private String lastName = "";
  private String company = "";
  private Country country = Country.UNKNOWN;
  private UUID uuid = UUID.randomUUID();

    // Getters and Setters

  @Override
  public Object getEntityKey() {
    return uuid;
  }
}
```

### `HasEntityKey` Implementation

- **Usage**:
  - `HasEntityKey` provides a mechanism to assign a unique entity key to each model, simplifying data access.
  - Since the `Customer` model is not based on a database it utilizes the java UUID as an entity key.

:::tip
When using `HasEntityKey`, it’s recommended to use the primary key of the database to ensure consistency across database transactions.
:::

## `Service` class

Next create the `Service.java` class. This class serves as a way to access necessary data in various places throughout your app without needing to manually pass it between 
interested parties.

In this case the service will grab the data from the `src/main/resources/data/customers.json` and map it onto customer objects, which it will use to fill an `ArrayList`. This 
`ArrayList` is then used as a basis for a `Repository` which is covered later in this article.

The `Service` class uses a singleton-like pattern to manage customer data efficiently within the app. Instead of creating multiple instances, the app retrieves a single instance of `Service` using the `getCurrent()` method. This method utilizes `ObjectTable` to store the service instance under a unique key, ensuring only one instance exists throughout the app’s lifecycle.

<!-- TODO implement react component -->
<Details
        
        summary={
          <summary>Service.java
          </summary>
        }
      >
      </Details>


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

