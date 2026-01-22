---
title: Scaling with Routing and Composites
sidebar_position: 4
description: Step 3 - Learn how to add navigation.
---

Up until now, this tutorial has only been a single-page app. This step will change that. Using the data from [Working with Data](/docs/introduction/tutorial/working-with-data) and applying the following concepts, you’ll create an app that’s able to navigate between multiple pages:

- Composite components
- Passing parameter values through a URL
- Lifecycle observers

Completing this step creates a version of [3-scaling-with-routing-and-composites](https://github.com/webforj/webforj-demo-application/tree/main/3-scaling-with-routing-and-composites).

<div class="videos-container">
  <video controls>
    <source src="https://cdn.webforj.com/webforj-documentation/video/tutorials/scaling-with-routing-and-composites.mp4" type="video/mp4"/>
  </video>
</div>


## Using routing {#using-routing}

The way this tutorial handles navigation is through [Routing](/docs/routing/overview). Each page of the app will now be represented with a view, and a defined URL.

### Enabling routing {#enabling-routing}

To enable routing in your app, annotate the class extending the `App` class with [`@Routify`](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/annotation/Routify.html). In the annotation, specify what package will contain the views. This step also takes out the the UI components from the `run()` method and the `@StyleSheet` annotation. You’ll move these parts to a to a separate view class:

```java title="Application.java" {4}
@SpringBootApplication
@AppTheme("system")
// Removed `@StyleSheet` annotation
@Routify(packages = "com.webforj.demos.views")
@AppProfile(name = "DemoApplication", shortName = "DemoApplication")
public class Application extends App {
  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }
// Removed overridden App.run() method
}
```

:::tip Global CSS
Keeping the `@StyleSheet` annotation in `Application` would apply that CSS to the entire app.
:::

## Creating views {#creating-views}

This tutorial will require two views, each represented by a separate class. The first view, `DemoView`, will have the UI components that were initially in the `Application` class. The second view, `FormView`, will be for editing and adding customer data.

Both views will have the following:

- **Composite components** - Both views extend the `Composite` component. [Composite components](/docs/building-ui/composite-components) are wrappers that make it easy to create reusable components.
  :::tip Other Composite Options
  This tutorial wraps both views in `Div` elements, but `Composite` components can encapsulate any component, like [`FlexLayout`](/docs/components/flex-layout) or [`AppLayout`](/docs/components/app-layout).
  :::
- **`@Route` annotation** - The [`@Route`](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/annotation/Route.html) annotation defines URLs to navigate to specific views.
- **`@FrameTitle` annotation** - The [`@FrameTitle`](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/annotation/FrameTitle.html) annotation defines what appears in the browser's title or page's tab.

## Creating `DemoView` {#creating-demoview}

As mentioned previously, `DemoView` will contain the UI components initially in `Application`. In `/src/main/java/com/webforj/demos/views`, create `DemoView`.

### Making `DemoView` the home page {#making-demoview-the-home-page}

By default, adding the `@Route` annotation creates a URL for the view based on the filename. However, setting it to `@Route("/")` makes `DemoView` the root, so it’ll be the home page for your app. 

### Navigating from `DemoView` to other views {#navigating-from-demoview-to-other-views}

You will use the `Router` class twice to navigate from `DemoView` to `FormView` for adding and editing customer data:

```java
Router.getCurrent().navigate(FormView.class)
```

Navigating to `FormView` for new customers will occur from a `ButtonClickEvent`:

```java
private Button add = new Button("Add Customer", ButtonTheme.PRIMARY,
    e -> Router.getCurrent().navigate(FormView.class));
```

Navigating to `FormView` to edit existing customer data will require the app to know which customer data you’re editing. Using `TableItemClickEvent`, you can determine which row was clicked from the `Table` component and retrieve the `id` of the `Customer` entity created in the previous step, [Working with Data](/docs/introduction/tutorial/working-with-data). Then, the `id` is passed through the `ParmatersBag` to become part of the URL navigation to `FormView`.

```java
private void editCustomer(TableItemClickEvent<Customer> e) {
  Router.getCurrent().navigate(FormView.class,
      ParametersBag.of("id=" + e.getItemKey()));
}
```


### Completed `DemoView` {#completed-demoview}

Here’s a look at what `DemoView` should look like before moving on to create `FormView`:

:::note Cleaner code
As your app grows more complex, it’s a good idea to section parts of your app. The methods for creating the `Table` component were grouped into a single method.
:::

<!-- vale off -->
<ExpandableCode title="DemoView.java" language="java" startLine={1} endLine={15}>
{`
@Route("/")
@FrameTitle("Demo")
public class DemoView extends Composite<Div> {

  private final CustomerService customerService;

  private Table<Customer> table = new Table<>();
  private Div self = getBoundComponent();
  private Button add = new Button("Add Customer", ButtonTheme.PRIMARY,
      e -> Router.getCurrent().navigate(FormView.class));

  public DemoView(CustomerService customerService) {
    this.customerService = customerService;

    add.setWidth(200);
    buildTable();
    table.addItemClickListener(this::editCustomer);
    FlexLayout layout = FlexLayout.create(table, add)
        .vertical().contentAlign().center().build().setPadding("var(--dwc-space-l)");
    self.add(layout);
  }

  private void buildTable() {
    table.setHeight("294px");

    table.addColumn("firstName", Customer::getFirstName).setLabel("First Name");
    table.addColumn("lastName", Customer::getLastName).setLabel("Last Name");
    table.addColumn("company", Customer::getCompany).setLabel("Company");
    table.addColumn("country", Customer::getCountry).setLabel("Country");
    table.setColumnsToAutoFit();
    table.getColumns().forEach(column -> column.setSortable(true));
    table.setRepository(customerService.getFilterableRepository());
  }

  private void editCustomer(TableItemClickEvent<Customer> e) {
    Router.getCurrent().navigate(FormView.class,
        ParametersBag.of("id=" + e.getItemKey()));
  }
}
`}
</ExpandableCode>
<!-- vale on -->

## Creating `FormView` {#creating-formview}

The next view to create in `/src/main/java/com/webforj/demos/views` is `FormView`.
This class will need to distinguish between new and existing customers when loading and populating the editing fields as necessary.

### Getting the customer `id` from the URL {#getting-the-customer-id-from-the-url}

Using `@Route("customer/:id?")` as the route, you allow the URL to have [Query Parameters](/docs/routing/query-parameters) to determine one of the following actions for your app to take based on the `id`:

- Populate the fields with a current customer’s data.
- Leave empty fields for a new customer.
- Navigate back to `DemoView` when there's no matching customer `id`.

Actions are taken during the [Lifecycle Observers](/docs/routing/navigation-lifecycle/observers) `WillEnterObserver` and `DidEnterObserver`.

### Checking the customer `id` {#checking-for-an-invalid-customer-id}

Before doing anything with the customer `id`, you need to verify the `id`. The `Table` in `DemoView` grabs valid `id` values, but there’s nothing stopping someone from manually typing a URL in the browser with an invalid `id`.

If the `id` is invalid, use the `Router` to navigate back to the main page, `DemoView`:

```java
private void navigateToHome(){
  Router.getCurrent().navigate(DemoView.class);
}
```

Testing the `id` happens on `onWillEnter`, so the app can verify the `id` before loading any fields in `FormView`, explicitly using the `accept()` and `reject()` methods for each possiblity guarantees the next action for the app:

```java
@Override
public void onWillEnter(WillEnterEvent event, ParametersBag parameters) {
  parameters.get("id").ifPresent(id -> {
    if (isLong(id)) {
      customerId = Long.parseLong(id);
      try {
        customerService.getCustomerByKey(customerId);
      } catch (NoSuchElementException e) {
         event.reject();
        navigateToHome();
      }
      event.accept();
    } else {
       event.reject();
      navigateToHome();
    }
  });
  event.accept();
```

The `navigateToHome()` method was shown previously, and `isLong()` is a method that returns a boolean based on the `id`:

```java 
private boolean isLong(String id) {
  try {
    Long.parseLong(id);
    return true;
  } catch (NumberFormatException e) {
    return false;
  }
}
```

### Loading data {#loading-data}

The next lifecycle, `onDidEnter`, happens when the `Router` navigates to a view.
During this part of the lifecycle, the app is told to:
- **Load Data**: Initialize or fetch data required for the view based on route parameters.
- **Set Up the View**: Update UI elements dynamically based on the context.
- **React to State Changes**: Perform actions that depend on the view being active, such as resetting forms or highlighting components.

In `FormView`, the `onDidEnter` method checks for the presence of an `id` parameter and adjusts the form's behavior:
- **Edit Mode**: If an `id` is provided, fetch the customer’s data and pre-fill the form. The [Submit] button updates the entry.
- **Add Mode**: If no `id` is present, show a blank form. The [Submit] button creates a new customer.

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

### Completed `FormView` {#completed-formview}

Your `FormView` should now look similar to the following:

:::note Moved `@StyleSheet` annotation
Moving `@StyleSheet` from `Application` means the CSS styling will now only apply to `FormView` and not the entire app.
:::

<!-- vale off -->
<ExpandableCode title="FormView.java" language="java" startLine={1} endLine={15}>
{`
@StyleSheet("ws://css/form.css")
@Route("customer/:id?")
@FrameTitle("Customer Form")
public class FormView extends Composite<Div> implements WillEnterObserver, DidEnterObserver {
  private final CustomerService customerService;
  Customer customer = new Customer();
  Long customerId = Long.parseLong("0");
  Div self = getBoundComponent();
  TextField firstName = new TextField("First Name", e -> customer.setFirstName(e.getValue()));
  TextField lastName = new TextField("Last Name", e -> customer.setLastName(e.getValue()));
  TextField company = new TextField("Company", e -> customer.setCompany(e.getValue()));
  ChoiceBox country = new ChoiceBox("Country",
      e -> customer.setCountry(Country.valueOf(e.getSelectedItem().getText())));
  Button submit = new Button("Submit", ButtonTheme.PRIMARY); 
  Button cancel = new Button("Cancel", ButtonTheme.OUTLINED_PRIMARY, e -> navigateToHome());

  ColumnsLayout columnsLayout = new ColumnsLayout(
      firstName, lastName,
      company, country,
      cancel, submit);

  public FormView(CustomerService customerService) {
    this.customerService = customerService;
    fillCountries();

    self.setMaxWidth("600px");
    self.setHeight("100dvh");
    self.addClassName("form");
    self.add(columnsLayout);
  }

  private void fillCountries() {
    ArrayList<ListItem> listCountries = new ArrayList<>();
    for (Country countryItem : Customer.Country.values()) {
      listCountries.add(new ListItem(countryItem, countryItem.toString()));
    }
    country.insert(listCountries);
  }

  private void submit(String mode) {
    switch (mode) {
      case "edit":
        customerService.updateCustomer(customer);
        break;
      case "add":
        customerService.createCustomer(customer);
        break;
      default:
        App.console().log("Invalid mode");
        break;
    }
    navigateToHome();
  }

  private void navigateToHome(){
    Router.getCurrent().navigate(DemoView.class);
  }

  @Override
  public void onWillEnter(WillEnterEvent event, ParametersBag parameters) {
    parameters.get("id").ifPresent(id -> {
      if (isLong(id)) {
        customerId = Long.parseLong(id);
        try {
          customerService.getCustomerByKey(customerId);
        } catch (NoSuchElementException e) {
          event.reject();
          navigateToHome();
        }
        event.accept();
      } else {
        event.reject();
        navigateToHome();
      }
    });
    event.accept();
  }

  @Override
  public void onDidEnter(DidEnterEvent event, ParametersBag parameters) {
    parameters.get("id").ifPresentOrElse(id -> {
      customerId = Long.parseLong(id);
      customer = customerService.getCustomerByKey(customerId);
      firstName.setValue(customer.getFirstName());
      lastName.setValue(customer.getLastName());
      company.setValue(customer.getCompany());
      country.selectKey(customer.getCountry());
      submit.addClickListener(e -> submit("edit"));
    }, () -> submit.addClickListener(e -> submit("add")));
  }

  private boolean isLong(String id) {
    try {
      Long.parseLong(id);
      return true;
    } catch (NumberFormatException e) {
      return false;
    }
  }

}
`}
</ExpandableCode>
<!-- vale on -->

## Running the app {#running-the-app}

When you’ve finished this step, you can compare it to [3-scaling-with-routing-and-composites](https://github.com/webforj/webforj-demo-application/tree/main/3-scaling-with-routing-and-composites) on GitHub. To see the app in action:

1. Navigate to the top level directory containing the `pom.xml` file, this is `3-scaling-with-routing-and-composites` if you're following along with the version on GitHub.

2. Use the following Maven command to run the Spring Boot app locally:
    ```bash
    mvn
    ```

3. Open your browser and go to http://localhost:8080 to view the app.

With routing and view separation in place, your app is now ready for further scaling, including advanced data binding and validation in the next step.