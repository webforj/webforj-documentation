---
title: Scaling with Routing and Composites
sidebar_position: 4
---

This step focuses on implementing routing to enhance the scalability and organization of the app structure. To achieve this, the app will be updated to handle multiple views, allowing navigation between different functionalities such as editing and creating customer entries. It will outline creating views for these functionalities, using components like `Composite` to build modular and reusable layouts.

The app created in the [previous step](./working-with-data) will have a robust routing setup that supports multiple views, enabling users to manage customer data more effectively while maintaining a clean and scalable codebase.

## Routing
<!-- TODO quick very broad intro whats routing -->
Routing allows your app to manage multiple views and scale effectively. This step focuses on changing the `App` class, creating files for the views, and configuring routes to enable smooth navigation between different parts of your app.

### Changing the `App` class

To enable routing in your app, update the `App` class with the `@Routify` annotation. This tells webforJ to activate routing and scan specified packages for route-enabled views.

```java title="DemoApplication.java"
@Routify(packages = "com.webforj.demos.views", debug = true)
public class DemoApplication extends App {
}
```

- **`packages`**: Specifies which packages are scanned for views that define routes.
- **`debug`**: Enables debugging mode for easier troubleshooting during development.

### Creating files for the views and configuring routes

Once routing has been enabled, create separate Java files for each view, the app will contain. In this case, `DemoView.java` and `FormView.java`. Assign unique routes to these views using the `@Route` annotation. This ensures that each view is accessible through a specific URL.

There are two suffixes webforJ looks for by default which are *View and *Layout. Using either of these suffixes at the end of a classname of a component associated with a route is advised as best practice.

When the `@Route` annotation is left blank above a class with one of these suffixes, webforJ automatically assigns the class's name without the suffix as the route. For example, `DemoView` would use the route `/demo` by default.

See this in `FormView.java`:

```java title="FormView.java"
@Route("customer/:id?")
@FrameTitle("Customer Form")
public class FormView extends Composite<Div> implements DidEnterObserver {
    // FormView logic
}
```

The `/` route serves as the default entry point for your app. Assigning this route to a view ensures that it's the first page users see when accessing the app. In most cases, a dashboard or summary view is assigned to `/`.

The route `customer/:id?` uses an optional parameter `id` to determine the mode of the `FormView`. 

- **Add Mode**: When `id` isn't provided, `FormView` initializes with a blank form for adding new customer data.
- **Edit Mode**: When `id` is provided, `FormView` fetches the corresponding customer’s data using `Service` and pre-fills the form, allowing edits to be made to the existing entry.

## Using `Composite` components to display pages

Composite components in webforJ, such as `Composite<Div>`, allow you to encapsulate UI logic and structure within a reusable container. By extending `Composite`, you limit the methods and data exposed to the rest of the app, ensuring cleaner code and better encapsulation.

For example, `DemoView` extends `Composite<Div>` instead of directly extending `Div`:

```java title="DemoView.java"
public class DemoView extends Composite<Div> {
    private Table<Customer> table = new Table<>();
    private Button add = new Button("Add Customer", ButtonTheme.PRIMARY);

    public DemoView() {
        setupLayout();
    }

    private void setupLayout() {
        FlexLayout layout = FlexLayout.create(table, add)
            .vertical().contentAlign().center().build().setPadding("var(--dwc-space-l)");
        getBoundComponent().add(layout);
    }
}
```

### How to implement pages outside of the `run` method

Instead of placing all logic within the `run()` method of `App`, views like `DemoView` and `FormView` are implemented as separate classes. This approach more closely aligns with standard Java practices.

- **DemoView**: Handles displaying the table and navigating to `FormView`.
- **FormView**: Manages adding and editing customer data.

## Connecting the routes

After configuring routing and setting up views, connect the views and data using event listeners and service methods. The first step is to add one or more
UI elements to navigate from one view to the other.

### Button navigation

The `Button` component triggers a navigation event to transition from one view to another using the `Router` class. For example:

```java title="DemoView.java"
private Button add = new Button("Add Customer", ButtonTheme.PRIMARY,
    e -> Router.getCurrent().navigate(FormView.class));
```

For more details on navigation, see the [Routing Overview](../../routing/overview).

### Table editing

Item clicks in `DemoView` are handled by the `TableItemClickEvent<Customer>` listener, which passes the selected customer’s `id` to the `FormView`:

```java title="DemoView.java"
private void editCustomer(TableItemClickEvent<Customer> e) {
    Router.getCurrent().navigate(FormView.class,
        ParametersBag.of("id=" + e.getItemKey()));
}
```

This enables a smooth transition to edit mode, where customer data is pre-filled in the form. For more on event handling, see the [Table Documentation](../../components/table).


### Handling Initialization with `onDidEnter`

The `onDidEnter` method in `FormView` is triggered when the view is loaded. It checks for the presence of an `id` parameter in the route and adjusts the form's behavior accordingly:

- **Edit Mode**: If an `id` is provided, the method fetches the corresponding customer’s data using `Service` and pre-fills the form fields. The `Submit` button is configured to update the existing entry.
- **Add Mode**: If no `id` is present, the form remains blank, and the `Submit` button is configured to create a new customer.

```java
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


### Service Integration

The `Service` class you already set up in the previous step of this tutorial
now needs to be enhanced with additional methods, allowing you to add and edit customers. Since you are using the `Repository` for data they're fairly simple.

```java title="Service.java"
public void addCustomer(Customer newCustomer) {
    data.add(newCustomer);
    repository.commit(newCustomer);
}

public void editCustomer(Customer editedCustomer) {
    repository.commit(editedCustomer);
}
```

---

With your changes you now have achieved the following goals:

    1. Implemented routing and set it up so future views can be integrated with little effort.
    2. Removed UI implementations out of your `App` and into a separate view.
    3. Added an additional view to manipulate the data that is displayed in the customer table.

With you now being able to modify the customer details and having setup routing the next step will focus on
implementing databinding and using it to facilitate validation.