# Third step

## Goals and resources

By completing this step, you will:

- Implement routing to create a scalable and organized app structure.
- Use views for editing and creating customer entries.
- Integrate dynamic data handling and a modular approach using components like `Composite<Div>`.

```plaintext
webforj-demo-application/
├── pom.xml
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── webforj/
│   │   │           └── demos/
│   │   │               |── DemoApplication.java
|   |   |               |── data/
|   |   |               |   └── Service.java
|   |   |               |── models/
|   |   |               |   └── Customer.java
│   │   │               └── views/ 
|   |   |                   |── DemoView.Java
|   |   |                   └── FormView.Java
│   │   └── resources/
│   │       |── css/
|   |       |   └── views/
|   |       |       └── form.css
│   │       └── data/
|   |           └── customers.json
```

The following articles will explain in detail some of the concepts discussed in this step:

- [Routing](../../routing/overview.md)
- [Composite](../../building-ui/composite-components)


## Routing for scalability

To start, routing will be implemented to handle multiple views, which is essential for scalability. To do this, you will be putting the functionality of `DemoApplication.java` into one view and add another view to it.

By assigning unique routes to the different app views, the app can manage new and existing entries effectively. This routing approach ensures that future enhancements can be added with minimal changes to the existing codebase.

In webforJ you can handle routing through annotations in your `DemoApplication.java` and every view you want to route to. 

## `Routify()`

The `Routify()` annotation in the class extending `App` tells webforJ you want to utilize routing in your app.

```java
@Routify(packages = "com.webforj.demos.views", debug = true)
@AppTitle("Demo Step 3")
public class DemoApplication extends App {
}
```

The packages parameter specifies which packages are scanned for available routes.

## `DemoView`

Now you will move the `DemoApplication` logic into a new file called `DemoView.java`. This file now purely handles the UI display of the `Table`. Furthermore change the `Button` carried over from step one and two into an "add" `Button`.

:::info
In `FormView` and `DemoView`, `Composite<Div>` is extended instead of extending just `Div`. This approach prevents all methods of the extended composite being accessible automatically, which ensures control over the views information.
:::

## First set up `DemoView` as a new view component

1. Create a new Java file named `DemoView.java` within the `views` package `com.webforj.demos.views`.
2. Import necessary classes, especially components like `Table`, `Button`, and layout utilities.

   ```java title="DemoView.java"
   public class DemoView extends Composite<Div> {
       // Class code here
   }
   ```

## Next migrate core elements from `DemoApplication` to `DemoView`

- **Move Components**: Copy the `Table<Customer>` and `Button` elements from `DemoApplication` to `DemoView`.
- **Initialize the Layout**: Set up a `FlexLayout` to organize components vertically within `DemoView`, adding padding and centering them.

   ```java title="DemoView.java"
   private Table<Customer> table = new Table<>();
   private Div self = getBoundComponent();
   private Button add = new Button("Add Customer", ButtonTheme.PRIMARY);

   public DemoView() {
       add.setStyle("margin", "var(--dwc-space-l)").setWidth(200);
       buildTable();
       FlexLayout layout = FlexLayout.create(table, add)
           .vertical().contentAlign().center().build().setPadding("var(--dwc-space-l)");
       self.add(layout);
   }
   ```

## Routing

- In `DemoView`, set up the `"Add Customer"` button with an event listener that navigates to `FormView` when clicked.
- Use `Router.getCurrent().navigate(FormView.class);` within the button’s click event.

   ```java title="DemoView.java"
   private Button add = new Button("Add Customer", ButtonTheme.PRIMARY,
       e -> Router.getCurrent().navigate(FormView.class));
   ```

- In `DemoView`, add an `editCustomer` method to handle item clicks on the table.
- Use `TableItemClickEvent<Customer>` to capture the selected customer’s ID, then navigate to `FormView` with that ID.

   ```java title="DemoView.java"
   private void editCustomer(TableItemClickEvent<Customer> e) {
       Router.getCurrent().navigate(FormView.class,
           ParametersBag.of("id=" + e.getItemKey()));
   }
   ```

- Register `editCustomer` as an event listener for table item clicks.

   ```java title="DemoView.java"
   table.addItemClickListener(this::editCustomer);
   ```


## `FormView`

The `FormView` class provides a user interface for adding and editing customer data within the app. This class utilizes a layout for arranging form fields and implements routing to support both add and edit operations.

### Assigning a route

The `FormView` class is assigned the route `customer/:id?` using the `@Route` annotation. This route includes an optional parameter (:id?) that supports two modes:
- **Add Mode**: If `id` isn't provided, `FormView` initializes a blank form for adding a new customer.
- **Edit Mode**: If `id` is specified, `FormView` populates the form with existing customer data for editing.

```java title="FormView.java"
@Route("customer/:id?")
@FrameTitle("Customer Form")
public class FormView extends Composite<Div> implements DidEnterObserver {
    // FormView fields and methods
}
```


### Adding components to the screen

`FormView` defines form fields (`TextField`s for `firstName`, `lastName`, and `company`, and a `ChoiceBox` for `country`). It also includes buttons (`Submit` and `Cancel`) with later defined click events.
  
### `fillCountries()` method

The `fillCountries()` method populates the `country` `ChoiceBox` with country values. By iterating over `Customer.Country.values()`, the method creates a list of `ListItem` instances, each representing a country. This approach allows `FormView` to dynamically load country options without hardcoding them.

```java title="FormView.java"
private void fillCountries() {
    ArrayList<ListItem> listCountries = new ArrayList<>();
    for (Country countryItem : Customer.Country.values()) {
        listCountries.add(new ListItem(countryItem, countryItem.toString()));
    }
    country.insert(listCountries);
}
```

### Service additions

You have to give the options to manipulate the customer data on the `Service` side first so the `FormView` can utilize them. The following public methods allow the form data access.

```java
  public String getCustomerKey(Customer cust) {
    return repository.getKey(cust).toString();
  }

  public void addCustomer(Customer newCustomer) {
    data.add(newCustomer);
    repository.commit(newCustomer);
  }

  public void editCustomer(Customer editedCustomer) {
    repository.commit(editedCustomer);
  }
```


### Route logic and `onDidEnter`

The `onDidEnter` method is triggered when `FormView` is loaded. It checks for the presence of an `id` parameter:
- If `id` is provided, `FormView` fetches the customer’s data using `Service.getCurrent().getCustomerByKey(id)`, then populates the form fields with this data.
- If `id` isn't present, `FormView` defaults to add mode.

```java title="FormView.java"
@Override
public void onDidEnter(DidEnterEvent event, ParametersBag parameters) {
    parameters.get("id").ifPresentOrElse(id -> {
        customer = Service.getCurrent().getCustomerByKey(UUID.fromString(id));
        firstName.setValue(customer.getFirstName());
        lastName.setValue(customer.getLastName());
        company.setValue(customer.getCompany());
        country.selectKey(customer.getCountry());
        submit.addClickListener(e -> submit("edit"));
    }, () -> submit.addClickListener(e -> submit("add")));
}
```

### Logic for adding/editing entries

In add mode, when no `id` is provided in the route, the form is initialized as blank. The `submit` button is set to trigger the `submit("add")` method, which invokes `Service.getCurrent().addCustomer(customer)` to add a new customer to the repository. If an id is provided, `submit` will trigger the `submit(edit)` method to update the preloaded data.

```java title="FormView.java"
private void submit(String mode) {
    switch (mode) {
        case "add":
            Service.getCurrent().addCustomer(customer);
            break;
        case "edit":
            Service.getCurrent().editCustomer(customer);
            break;
        default:
            App.console().log("Invalid mode");
            break;
    }
    Router.getCurrent().navigate(DemoView.class);
}
```

#### Navigation

Upon successful add or edit operation, the app navigates back to `DemoView` using `Router.getCurrent().navigate(DemoView.class)`. This maintains a smooth workflow, allowing users to manage customer data seamlessly.


