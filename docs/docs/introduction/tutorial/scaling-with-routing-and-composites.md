---
title: Scaling with routing and composites
sidebar_position: 4
---


This step introduces routing so the app can display multiple views and support navigation between features such as editing and creating customer entries. You’ll create views for these features using `Composite` components for modular, reusable layouts.

The app from the [previous step](./working-with-data) is now structured for routing and navigation, using Spring Boot as the runtime. To run the app:

- Go to the `3-scaling-with-routing-and-composites` directory
- Run the `mvn spring-boot:run` command

<div class="videos-container">
  <video controls>
    <source src="https://cdn.webforj.com/webforj-documentation/video/tutorials/scaling-with-routing-and-composites.mp4" type="video/mp4"/>
  </video>
</div>


## Routing

[Routing](../../routing/overview) allows your app to manage navigation between different views or pages. Instead of keeping all logic in a single location, routing lets you break your app into smaller, focused components.

With Spring Boot, routing in webforJ works the same as in standard webforJ apps, but you can inject Spring beans (like services) into your views.

### Enabling routing in the app class

To enable routing, annotate your main app class with `@Routify` and specify the package containing your views:

```java title="Application.java"
@SpringBootApplication
@StyleSheet("ws://css/app.css")
@AppTheme("system")
@Routify(packages = "com.webforj.demos.views")
@AppProfile(name = "DemoApplication", shortName = "DemoApplication")
public class Application extends App {
  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }
}
```

### Creating views and defining routes

Each view is a separate class, typically extending `Composite<Div>`. Use the `@Route` annotation to assign a URL path, and `@FrameTitle` for the browser title.

**DemoView** (table and navigation):

```java title="DemoView.java"
@Route("/")
@FrameTitle("Demo")
public class DemoView extends Composite<Div> {
  private final CustomerService customerService;
  // ... table, button, and layout setup ...
  public DemoView(CustomerService customerService) {
    this.customerService = customerService;
    // ... UI setup ...
  }
  // ... navigation logic ...
}
```

**FormView** (add/edit customer):

```java title="FormView.java"
@StyleSheet("ws://css/views/form.css")
@Route("customer/:id?")
@FrameTitle("Customer Form")
public class FormView extends Composite<Div> implements DidEnterObserver {
  private final CustomerService customerService;
  // ... form fields and layout ...
  public FormView(CustomerService customerService) {
    this.customerService = customerService;
    // ... UI setup ...
  }
  @Override
  public void onDidEnter(DidEnterEvent event, ParametersBag parameters) {
    // ... load data for edit/add ...
  }
}
```

- The `@Route("/")` on `DemoView` makes it the default landing page.
- The `@Route("customer/:id?")` on `FormView` allows both add and edit modes, depending on the presence of an `id` parameter.

### Navigation and event handling

- Use `Router.getCurrent().navigate(FormView.class)` to navigate to the form.
- Use `Router.getCurrent().navigate(FormView.class, ParametersBag.of("id=" + e.getItemKey()))` to navigate with a specific customer ID for editing.

### Dependency injection

With Spring Boot, you can inject services (like `CustomerService`) directly into your views via constructor injection. This allows you to use Spring-managed beans for data access, business logic, etc.

### Running the app

From the step directory, run:

```bash
mvn spring-boot:run
```

Then open [http://localhost:8080](http://localhost:8080) in your browser.

---

With routing and view separation in place, your app is now ready for further scaling, including advanced data binding and validation in the next step.

### Changing the `App` class {#changing-the-app-class}

To enable routing in your app, update the `App` class with the `@Routify` annotation. This tells webforJ to activate routing and scan specified packages for route-enabled views.

```java title="DemoApplication.java" {1}
@Routify(packages = "com.webforj.demos.views", debug = true)
public class DemoApplication extends App {  
}
```

- **`packages`**: Specifies which packages are scanned for views that define routes.
- **`debug`**: Enables debugging mode for easier troubleshooting during development.

### Creating files for the views and configuring routes {#creating-files-for-the-views-and-configuring-routes}

Once routing is enabled, create separate Java files for each view the app will contain, in this case, `DemoView.java` and `FormView.java`. Assign unique routes to these views using the `@Route` annotation. Each view is then accessible through a specific URL.

When the `@Route` annotation associated with a class with one of these suffixes has no value, webforJ automatically assigns the class's name without the suffix as the route. For example, `DemoView` will map the route `/demo` by default. Since in this case `DemoView` is supposed to be the default route tho you will assign it a route.

The `/` route is the default entry point for your app. Assign this route to a view to make it the first page users see when accessing the app. In most cases, a dashboard or summary view is assigned to `/`.

```java title="DemoView.java" {1}
@Route("/")
@FrameTitle("Demo")
public class DemoView extends Composite<Div> {
  // DemoView logic
}
```

:::info 
More information regarding the different route types is available [here](../../routing/defining-routes).
:::

For the `FormView` the route `customer/:id?` uses an optional parameter `id` to determine the mode of the `FormView`. 

- **Add Mode**: When `id` isn't provided, `FormView` initializes with a blank form for adding new customer data.
- **Edit Mode**: When `id` is provided, `FormView` fetches the corresponding customer’s data using `Service` and pre-fills the form, allowing edits to be made to the existing entry.

```java title="FormView.java" {1}
@Route("customer/:id?")
@FrameTitle("Customer Form")
public class FormView extends Composite<Div> implements DidEnterObserver {
  // FormView logic
}
```

:::info 
More information regarding the different ways to implement those route patterns is available [here](../../routing/route-patterns).
:::

## Using `Composite` components to display pages {#using-composite-components-to-display-pages}

Composite components in webforJ, such as `Composite<Div>`, encapsulate UI logic and structure within a reusable container. By extending `Composite`, you limit the methods and data exposed to the rest of the app, which helps keep your code organized and maintainable.

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

## Connecting the routes {#connecting-the-routes}

After configuring routing and setting up views, connect the views and data using event listeners and service methods. The first step is to add one or more
UI elements to navigate from one view to the other.

### Button navigation {#button-navigation}

The `Button` component triggers a navigation event to transition from one view to another using the `Router` class. For example:

```java title="DemoView.java"
private Button add = new Button("Add Customer", ButtonTheme.PRIMARY,
  e -> Router.getCurrent().navigate(FormView.class));
```

:::info
The Router class uses the given class to resolve the route and build an URL to navigate to. All browser navigation is then handled so that history management
and view initialization is of no concern.
For more details on navigation, see the [Route Navigation Article](../../routing/route-navigation).
:::

### Table editing {#table-editing}

In addition to navigation via button click, many apps also allow for navigation to other parts of an app when a `Table` is double clicked. The following changes are made to allow users to double-click an item in the table to navigate to a form pre-filled with the item's details.

Once the details have been edited on the appropriate screen, the changes are saved, and the `Table` is updated to display the changed data from the selected item.

To enable this navigation, item clicks in the table are handled by the `TableItemClickEvent<Customer>` listener. The event contains the `id` of the clicked customer, which it passes to the `FormView` by using the `navigate()` method with a `ParametersBag`:

```java title="DemoView.java" 
private void editCustomer(TableItemClickEvent<Customer> e) {
  Router.getCurrent().navigate(FormView.class,
    ParametersBag.of("id=" + e.getItemKey()));
}
```

### Handling initialization with `onDidEnter` {#handling-initialization-with-ondidenter}

The `onDidEnter` method in webforJ is part of the routing lifecycle and is triggered when a view becomes active. 

When the `Router` navigates to a view, `onDidEnter` is triggered as part of the lifecycle to:
- **Load Data**: Initialize or fetch data required for the view based on route parameters.
- **Set Up the View**: Update UI elements dynamically based on the context.
- **React to State Changes**: Perform actions that depend on the view being active, such as resetting forms or highlighting components.

The `onDidEnter` method in `FormView` checks for the presence of an `id` parameter in the route and adjusts the form's behavior accordingly:

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


### Submitting data {#submitting-data}

When finished editing the data, it's necessary to submit it to the service handling the repository. Therefore the 
`Service` class that has been already set up in the previous step of this tutorial
now needs additional methods so users can add and edit customers. 

The snippet below shows how to accomplish this:

```java title="Service.java"
public void addCustomer(Customer newCustomer) {
  data.add(newCustomer);
  repository.commit(newCustomer);
}

public void editCustomer(Customer editedCustomer) {
  repository.commit(editedCustomer);
}
```

### Using `commit()` {#using-commit}

The `commit()` method in the `Repository` class keeps the app’s data and UI in sync. It provides a way to refresh the data stored in the `Repository` so the app always displays the latest state.

This method can be used in two ways:

1) **Refreshing all data:**
  Calling `commit()` without arguments reloads all entities from the repository's underlying data source, such as a database or a service class.

2) **Refreshing a single entity:**
  Calling `commit(T entity)` reloads a specific entity so its state matches the latest data source changes.

Call `commit()` when data in the `Repository` changes, such as after adding or modifying entities in the data source.

```java
// Refresh all customer data in the repository
customerRepository.commit();

// Refresh a single customer entity
Customer updatedCustomer = ...; // Updated from an external source
customerRepository.commit(updatedCustomer);

```

With these changes, the following goals have been achieved:

  1. Implemented routing and set it up so future views can be integrated with little effort.
  2. Removed UI implementations out of the `App` and into a separate view.
  3. Added an additional view to manipulate the data that's displayed in the customer table.

With the modification of the customer details and routing accomplished, the next step will focus on
implementing data binding and using it to facilitate validation.