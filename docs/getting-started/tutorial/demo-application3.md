# Third step

## Goals and resources

By completing this step, you will:

- Implement routing to create a scalable and organized app structure.
- Use views for editing and creating customer entries.
- Integrate dynamic data handling and a modular approach using components like `Composite<Div>`.

<!-- TODO provide list of resources -->

## Routing for scalability

To start, routing will be implemented to handle multiple views, which is essential for scalability. To do this, you will be putting the functionality of `DemoApplication.java` into one view and add another view to it.

By assigning unique routes to the different app views, the app can manage new and existing entries effectively. This routing approach ensures that future enhancements can be added with minimal changes to the existing codebase.

## `DemoView`

Move the `DemoApplication` logic into a new file called `DemoView.java`. This file now purely handles the UI display of the `Table`. Furthermore change the `Button` carried over from step one and two into an "add" `Button`.

:::info
In `FormView`, `Composite<Div>` is extended instead of extending just `Div`. This approach prevents all methods of the extended composite being accessible automatically, which ensures control over the views information.
:::

## Step 1: set up `DemoView` as a new view component

1. Create a new Java file named `DemoView.java` within the `views` package `com.webforj.demos.views`.
2. Import necessary classes, especially components like `Table`, `Button`, and layout utilities.

3. **Add `@Route` and `@FrameTitle` Annotations**:
   - Assign the root path `/` to `DemoView` using `@Route("/")`.
   - Set the frame title to `"Demo"` with `@FrameTitle("Demo")`.

   ```java title="DemoView.java"
   @Route("/")
   @FrameTitle("Demo")
   public class DemoView extends Composite<Div> {
       // Class code here
   }
   ```

## Step 2: migrate core elements from `DemoApplication` to `DemoView`

- **Move Components**: Copy the `Table<Customer>` and `Button` elements from `DemoApplication` to `DemoView`.
- **Initialize the Layout**: Set up a `FlexLayout` to organize components vertically within `DemoView`, adding padding and centering them.

   ```java title="DemoView.java"
   private Table<Customer> table = new Table<>();
   private Div self = getBoundComponent();
   private Button add = new Button("Add Customer", ButtonTheme.PRIMARY,
       e -> Router.getCurrent().navigate(FormView.class));

   public DemoView() {
       add.setStyle("margin", "var(--dwc-space-l)").setWidth(200);
       buildTable();
       FlexLayout layout = FlexLayout.create(table, add)
           .vertical().contentAlign().center().build().setPadding("var(--dwc-space-l)");
       self.add(layout);
   }
   ```

## Step 3: Add routing and navigation for the Add Button

- In `DemoView`, set up the `"Add Customer"` button with an event listener that navigates to `FormView` when clicked.
- Use `Router.getCurrent().navigate(FormView.class);` within the button’s click event.

   ```java title="DemoView.java"
   private Button add = new Button("Add Customer", ButtonTheme.PRIMARY,
       e -> Router.getCurrent().navigate(FormView.class));
   ```

## Step 4: Define the `buildTable` Method

In `DemoView`, refactor the `buildTable` method from `DemoApplication` to enhance the table:

- **Add Columns**: Define columns for `First Name`, `Last Name`, `Company`, and `Country`.
- **Enable Sorting**: Add `column.setSortable(true);` for each column to allow sorting.
- **Set Repository**: Use `Service.getCurrent().getCustomers()` to populate the table.

   ```java title="DemoView.java"
   private void buildTable() {
       table.setHeight("300px");
       table.addColumn("First Name", Customer::getFirstName);
       table.addColumn("Last Name", Customer::getLastName);
       table.addColumn("Company", Customer::getCompany);
       table.addColumn("Country", Customer::getCountry);
       table.getColumns().forEach(column -> column.setSortable(true));
       table.setRepository(Service.getCurrent().getCustomers());
   }
   ```

## Step 5: Add editing with `TableItemClickEvent`

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

`FormView` defines form fields (`TextField`s for `firstName`, `lastName`, and `company`, and a `ChoiceBox` for `country`). It also includes buttons (`Submit` and `Cancel`) with defined click events.

The form components are added to a `ColumnsLayout` instance for a structured, multi-column layout. This layout is then added to the `Div` component using `self.add(columnsLayout)`, positioning all elements on the screen.

```java title="FormView.java"
public FormView() {
    fillCountries();
    self.setMaxWidth("600px");
    self.setHeight("100dvh");
    self.addClassName("form");
    self.add(columnsLayout);
}
```

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

### Logic for adding entries

In add mode, when no `id` is provided in the route, the form is initialized as blank. The `submit` button is set to trigger the `submit("add")` method, which invokes `Service.getCurrent().addCustomer(customer)` to add a new customer to the repository.

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

### Editing entries

In edit mode, `FormView` retrieves the selected customer’s data using the provided `id`. The data is loaded into the form fields, allowing the user to make updates.

#### Route logic and `onDidEnter`

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

#### Navigation

Upon successful add or edit operation, the app navigates back to `DemoView` using `Router.getCurrent().navigate(DemoView.class)`. This maintains a smooth workflow, allowing users to manage customer data seamlessly.


