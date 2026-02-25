---
title: Routing and Composites
sidebar_position: 4
description: Step 3 - Make your app navigable.
---

Up until now, this tutorial has only been a single-page app. This step changes that. Using the data from [Working with Data](/docs/introduction/tutorial/working-with-data), you'll create an app that's able to navigate to another page for adding new customers in the browser by applying these concepts:

- Routing
- Composite components
- Using the [`ColumnsLayout`](/docs/components/columns-layout) component

Completing this step creates a version of [3-routing-and-composites](https://github.com/webforj/webforj-demo-application/tree/main/3-routing-and-composites).

<!-- Insert video here -->

## Routable apps {#routable-apps}

Previously, your app had a single function: displaying a table of existing customer data. But with an app that can also add new customers, it’s beneficial for long-term design, testing, and maintenance to separate these UIs. You’ll need to make your app routable so webforJ can access and load the two UIs individually.

A routable app renders the UI based on the URL. Annotating the class that extends the `App` class with [`@Routify`](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/annotation/Routify.html) tells webforJ the package that'll contain UI components.

When you're adding the `@Routify` annotation to `Application`, also take out the UI components from the `run()` method. You'll move the components to a class that you'll make in the `com.webforj.demos.views` package:

```java title="Application.java" {5-6,15}
@SpringBootApplication
@StyleSheet("ws://css/card.css")
@AppTheme("system")

//Added @Routify annotation
@Routify(packages = "com.webforj.demos.views")

@AppProfile(name = "CustomerApplication", shortName = "CustomerApplication")
public class Application extends App {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

// Removed overridden App.run() method

}
```

:::tip Global CSS
Keeping the `@StyleSheet` annotation in `Application` applies that CSS globally.
:::

### Creating routes {#creating-routes}

Adding the `@Routify` annotation makes your app routable. Once it's routable, your app will look in the `com.webforj.demos.views` package for routes. 
You'll need to create the routes for your UIs and also specify their [Route Types](/docs/routing/route-hierarchy/route-types). The route type determines how to map the UI content to the URL.

The first type of route type is `View`. These kinds of routes map directly to a specific URL segment in your app. The UIs for the table and the form for adding customers will both be `View` routes.

The second route type is `Layout`, which contains UI that appears on multiple pages, such as a header or sidebar. Layout routes also wrap child views without contributing to the URL.

Adding the route type as a suffix to the class name specifies the route’s type for the class. For example, `MainView` is a `View` route type.

To keep the app's two functions separate, your app needs to map the UIs to two unique `View` routes: one for the table and one for the customer form. In `/src/main/java/com/webforj/demos/views`, create two classes with a `View` suffix:

- **`MainView`** - This view will have the `Table` previously in the `Application` class.
- **`FormView`** - This view will have the form for adding new customers.

### Mapping URLs to components {#mapping-urls-to-components}

Your app is routable and knows to look for two `View` routes, `MainView` and `FormView`, but it doesn't have a specific URL to load them at. Using the `@Route` annotation on a view class, you can tell webforJ where to load it based on a given URL segment. For example, using `@Route("about")` in a view locally maps the class to [http://localhost:8080/**about**](http://localhost:8080/about).

As the name implies, `MainView` is the class you want to initially load when the app runs. To achieve this, add a `@Route` annotation that maps `MainView` to the root of the URL of your app:

```java title="MainView.java" {1}
@Route("/")
public class MainView {

  public MainView() {
  }

}
```

For the `FormView`, map the view so it loads when a user goes to [http://localhost:8080/**customer**](http://localhost:8080/customer):

```java title="FormView.java" {1}
@Route("customer")
public class FormView {

  public FormView() {
  }

}
```

:::tip Default behavior
If you don’t explicitly assign a value for the `@Route` annotation, the resulting URL segment becomes the class name converted to all lowercase, with the `View` suffix removed.

- `MainView` would map to `/main`
- `FormView` would map to `/form`
:::

## Shared characteristics {#shared-characteristics}

Besides both being view routes, `MainView` and `FormView` share additional characteristics. Some of these shared traits, like using `Composite` components, are fundamental to using webforJ apps, while others just make it easier to manage your app.

### Using `Composite` components {#using-composite-components}

When the app was single-paged, you stored the components inside a `Frame`. Moving forward, with an app with multiple views, you'll need to wrap those UI components inside [`Composite` components](/docs/building-ui/composite-components).

`Composite` components are wrappers that make it easy to create reusable components. You use `Composite` components by extending a class with a `Composite` and set a bound component, e.g., `Composite<Button>`. Avoid extending a built-in component directly, as doing so could alter its behavior when it appears in different parts of your app.

This tutorial uses `Div` elements as the bound components, but they can be any component, such as [`FlexLayout`](/docs/components/flex-layout) or [`AppLayout`](/docs/components/app-layout). Using the `getBoundComponent()` method, you can reference the bound component and have access to the methods for that type of component. This is helpful when trying to set the sizing, add a CSS class name, add components you want displayed in the `Composite` component, and access to component-specific methods.

For `MainView` and `FormView`, have the classes extend a `Composite` component with a `Div` as the bound component. Then, reference that bound component so you can add in the UIs later down the line. Both views should look similar to the following structure:

```java
// Extend Composite with a bound component
public class BasicView extends Composite<Div> {

  // Access the bound component
  private Div self = getBoundComponent();

  // Create a component UI
  private Button submit = new Button("Submit");

  public BasicView() {

    // Add the UI component to the bound component
    self.add(submit);
  }
}
```

### Shared CSS {#shared-css}

Now that you can reference the bound component in `MainView` and `FormView`, you can style it using the CSS. Using the CSS from the first step, [Creating a Basic App](/docs/introduction/tutorial/creating-a-basic-app#referencing-a-css-file), you can have both views use identical UI container styles. For the bound component in each view, add the CSS class name `card`:

```java {7}
public class BasicView extends Composite<Div> {

  private Div self = getBoundComponent();

  public BasicView() {

    self.addClassName("card");
  }
}
```

### Using `CustomerService` {#using-customerservice}

The `Table` in `MainView` displays each customer, while `FormView` adds new customers. Since both views interact with customer data, they need access to the app's business logic. 

The views get access by using the Spring service created in [Working with Data](/docs/introduction/tutorial/working-with-data#creating-a-service), `CustomerService`. To use the Spring service in each view, make `CustomerService` a constructor parameter:

```java {6-7}
@Route()
public class BasicView extends Composite<Div> {

  private Div self = getBoundComponent();

  public BasicView(CustomerService customerService) {
    this.customerService = customerService;

  }
}
```

### Setting the frame title {#setting-the-frame-tile}

The last shared trait for the views is a set frame title. When a user has multiple tabs in the browser, having a unique frame title helps them quickly identify which part of the app they have opened.

Using the [`@FrameTitle`](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/annotation/FrameTitle.html) annotation defines what appears in the browser's title or page's tab. For both views, add a frame title using the `@FrameTitle` annotation:

```java title="MainView.java" {2}
@Route("/")
@FrameTitle("Customer Table")
public class MainView extends Composite<Div> {

  private Div self = getBoundComponent();

  public MainView(CustomerService customerService) {
    this.customerService = customerService;

    self.addClassName("card");
  }
}
```

```java title="FormView.java" {2}
@Route("customer")
@FrameTitle("Customer Form")
public class FormView extends Composite<Div> {

  private Div self = getBoundComponent();

  public FormView(CustomerService customerService) {
    this.customerService = customerService;

    self.addClassName("card");
  }
}
```

## Creating `MainView` {#creating-mainview}

By making your app routable, giving the views `Composite` component wrappers, and including the `CustomerService`, you’re ready to build the UIs unique to each view. As mentioned previously, `MainView` contains the UI components initially in `Application`. This class also needs a way to navigate to `FormView`.

### Navigating to `FormView`{#navigating-to-formview}

Users need a way to navigate from `MainView` to `FormView` using the UI.

In webforJ, you can directly navigate to a new view by using a class name. Routing via a class instead of a URL segment guarantees webforJ will take the correct path to load the view. Routing like this in webforJ is possible because it uses the current location to determine the next destination.

The [`Router`](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/router/Router.html) class allows you to get the current location with the `getCurrent()` method, then use that as a reference to navigate to a different class using the `navigate()` method.

```java
Router.getCurrent().navigate(FormView.class);
```

You now have a way to programmatically send users to the form that adds new customers. It should be the user’s decision to go from the table to the new form, so it's time to associate the navigation with a user's action.

To allow users to add a new customer, you can either modify or replace the existing info button from `Application`. Instead of opening a message dialog, have the event grab the current `Router`, then send the user to the `FormView` class:

```java
private Button add = new Button("Add Customer", ButtonTheme.PRIMARY,
    e -> Router.getCurrent().navigate(FormView.class));
```

### Grouping the `Table` methods {#grouping-the-table-methods}

With added navigation, your app is growing more complex. It's a good idea to start sectioning parts of your app, so one custom method can make changes to the `Table` at once. Now, your main `MainView` method should only call one `buildTable()` method that adds the columns, sets the sizing, and references the repository:

```java
private void buildTable() {
  table.setSize("1000px", "294px");
  table.setMaxWidth("90vw");
  table.addColumn("firstName", Customer::getFirstName).setLabel("First Name");
  table.addColumn("lastName", Customer::getLastName).setLabel("Last Name");
  table.addColumn("company", Customer::getCompany).setLabel("Company");
  table.addColumn("country", Customer::getCountry).setLabel("Country");
  table.setColumnsToAutoFit();
  table.getColumns().forEach(column -> column.setSortable(true));
  table.setRepository(customerService.getFilterableRepository());
  }
```

## Completed `MainView` {#completed-mainview}

With the navigation to `FormView` and grouped table methods, here's what `MainView` should look like before moving on to creating `FormView`:

<!-- vale off -->
<ExpandableCode title="MainView.java" language="java" startLine={1} endLine={15}>
{`@Route("/")
@FrameTitle("Customer Table")
public class MainView extends Composite<Div> {

  private final CustomerService customerService;

  private Div self = getBoundComponent();
  private Table<Customer> table = new Table<>();
  private Button add = new Button("Add Customer", ButtonTheme.PRIMARY,
      e -> Router.getCurrent().navigate(FormView.class));

  public MainView(CustomerService customerService) {
    this.customerService = customerService;

    add.setWidth(200);
    buildTable();

    self.setWidth("fit-content")
      .addClassName("card")
      .add(table, add);
  }
  
  private void buildTable() {
    table.setSize("1000px", "294px");
    table.setMaxWidth("90vw");
    table.addColumn("firstName", Customer::getFirstName).setLabel("First Name");
    table.addColumn("lastName", Customer::getLastName).setLabel("Last Name");
    table.addColumn("company", Customer::getCompany).setLabel("Company");
    table.addColumn("country", Customer::getCountry).setLabel("Country");
    table.setColumnsToAutoFit();
    table.setColumnsToResizable(false);
    table.getColumns().forEach(column -> column.setSortable(true));
    table.setRepository(customerService.getFilterableRepository());
  }

}
`}
</ExpandableCode>
<!-- vale on -->

## Creating `FormView` {#creating-formview}

`FormView` will need a fillable form for new customers. For each customer property, there should be an editable component for users to interact with. Additionally, there needs to be buttons for users to press whenever they’re ready to submit the data, or a cancel button to discard their changes.

### Creating a `Customer` copy {#creating-a-customer-copy}

When users are editing data for a new customer, changes should only be applied to the repository when the user is ready to submit the form. Giving users a working copy of a `Customer` entity lets them use the UI to edit the data without editing the repository directly. Create a new `Customer` inside `FormView` to use for the form:

```java
private Customer customer = new Customer();
```

To make the created `Customer` copy editable, each property, except for the `id`, should be associated with an editable component. The changes a user makes in the UI should also be reflected in the `Customer` copy.

### Adding `TextField` components {#adding-textfield-components}

The first three editable properties in `Customer` (`firstName`, `lastName`, and `company`) are all `String` values, and should be represented with a common single-line text editor. [`TextField`](/docs/components/fields/textfield) components are a great choice to represent these properties.

With the `TextField` component, you can add a label and an event listener that fires whenever the field value changes. Each event listener should update the `Customer` copy for the corresponding property.

Add declarations for three `TextField` components that update the `Customer` copy:

```java title="FormView.java" {6-8}
public class FormView extends Composite<Div> {
  private final CustomerService customerService;
  private Customer customer = new Customer();
  private Div self = getBoundComponent();

  private TextField firstName = new TextField("First Name", e -> customer.setFirstName(e.getValue()));
  private TextField lastName = new TextField("Last Name", e -> customer.setLastName(e.getValue()));
  private TextField company = new TextField("Company", e -> customer.setCompany(e.getValue()));

  public FormView(CustomerService customerService) {
    this.customerService = customerService;
  }
}
```

:::tip Shared naming convention
Naming the components the same as the properties they're representing for the `Customer` entity makes it easier to bind data in a future step, [Validating and Binding Data](/docs/introduction/tutorial/validating-and-binding-data).
:::

### Adding a `ChoiceBox` component {#adding-a-choicebox-component}

Using a `TextField` for the `country` property wouldn’t be ideal, because the property can only be one of five enum values: `UNKNOWN`, `GERMANY`, `ENGLAND`, `ITALY`, and `USA`.

The component that’s better to use for selecting from a predefined list of options is the [`ChoiceBox`](/docs/components/lists/choicebox) component.

Each option for a `ChoiceBox` component is contained within a `ListItem`. Each `ListItem` has two values, an `Object` key and a `String` text to display in the UI. Having two values for each option allows you to handle the `Object` internally while simultaneously presenting a more readable option for users in the UI.

For example, the `Object` key could be an International Standard Book Number (ISBN), while the `String` text is the book title, which is more human-readable.

```java
new ListItem(isbnNumber, bookTitle);
```

However, this app deals with a list of country names, not books. For each `ListItem`, you want the `Object` to be the `Customer.Country` enum, while the text can be the enum as a `String`.

To add all the `country` options into a `ChoiceBox`, you can use an iterator to create a `ListItem` for each `Customer.Country` enum, and put them into an `ArrayList<ListItem>`. Then, you can insert that `ArrayList<ListItem>` into a `ChoiceBox` component:

```java
//Create the ChoiceBox component
private ChoiceBox country = new ChoiceBox("Country");

//Create an ArrayList of ListItem objects
ArrayList<ListItem> listCountries = new ArrayList<>();

//Add an iterator that creates a ListItem for each Customer.Country option
for (Country countryItem : Customer.Country.values()) {
  listCountries.add(new ListItem(countryItem, countryItem.toString()));
}

//Insert the filled ArrayList into the ChoiceBox
country.insert(listCountries);
```

Then, when the user selects an option in the `ChoiceBox`, the `Customer` copy should update with the key of the selected item, which is a `Customer.Country` value.

```java
private ChoiceBox country = new ChoiceBox("Country",
    e -> customer.setCountry((Customer.Country) e.getSelectedItem().getKey()));
```

To keep the code clean, the iterator that creates the `ArrayList<ListItem>` and adds it to the `ChoiceBox` should be in a separate method. The following is how `FormView` looks when you add a `ChoiceBox` that allows the user to choose the `country` property with the separate method:

```java title="FormView.java" {9-10,15,19-25}
public class FormView extends Composite<Div> {
  private final CustomerService customerService;
  private Customer customer = new Customer();
  private Div self = getBoundComponent();
  private TextField firstName = new TextField("First Name", e -> customer.setFirstName(e.getValue()));
  private TextField lastName = new TextField("Last Name", e -> customer.setLastName(e.getValue()));
  private TextField company = new TextField("Company", e -> customer.setCompany(e.getValue()));

  private ChoiceBox country = new ChoiceBox("Country",
      e -> customer.setCountry((Customer.Country) e.getSelectedItem().getKey()));

  public FormView(CustomerService customerService) {
    this.customerService = customerService;

    fillCountries();

  }

  private void fillCountries() {
    ArrayList<ListItem> listCountries = new ArrayList<>();
    for (Country countryItem : Customer.Country.values()) {
      listCountries.add(new ListItem(countryItem, countryItem.toString()));
    }
    country.insert(listCountries);
  }

}
```

### Adding `Button` components {#adding-button-components}

Now that you have a fillable form for users to add new customers, they should also have the option to either save the new customer's data or discard their changes. The decisions for the user to either submit the data or cancel should be made by clicking a `Button` component:

```java
private Button submit = new Button("Submit");
private Button cancel = new Button("Cancel");
```

After submitting or canceling, you should return the user to `MainView`. This allows them to immediately see the results of their action, whether they see a new customer in the table or if it remains unchanged. Since there will be multiple times `FormView` takes users to `MainView`, that navigation should be put into a recallable method:

```java
private void navigateToMain(){
  Router.getCurrent().navigate(MainView.class);
}
```

**Cancel button**

Discarding the changes on the form doesn’t require any additional code for the event beyond returning to `MainView`. However, since canceling a secondary action, setting the theme of the button to an outline gives the submit button more prominence. The article for the `Button` component includes a [Themes](/docs/components/button#themes) section that lists out each theme available.


```java
private Button cancel = new Button("Cancel", ButtonTheme.OUTLINED_PRIMARY,
    e -> navigateToMain());
```

**Submit button**

When a user's ready to submit changes for a new customer, the submit button should then use the values in the `Customer` copy to create a new entry in the repository.

Using the `CustomerService`, you can take the `Customer` copy to update the H2 database. When this happens, a new and unique `id` is assigned to that `Customer`. After updating the repository, you can redirect users to `MainView`, where they can see the new customer in the table.

```java
private Button submit = new Button("Submit", ButtonTheme.PRIMARY,
    e -> submitCustomer());

//...

private void submitCustomer() {
  customerService.createCustomer(customer);
  navigateToMain();
}
```

### Using a `ColumnsLayout` {#using-a-columnslayout}

By adding the `TextField`, `ChoiceBox`, and `Button` components, you now have all the interactive parts of the form. The last improvement to `FormView` in this step is to visually organize the six components.

This form can use a simple layout that separates the components into two columns.
Using the [`ColumnsLayout`](/docs/components/columns-layout) can easily achieve this desired layout without having to set the width of any interactive components. To create a `ColumnsLayout`, specify each component that should be inside the layout:

```java
private ColumnsLayout layout = new ColumnsLayout(
  firstName, lastName,
  company, country,
  submit, cancel);
```

When you want to set the number of columns for a `ColumnsLayout`, you use a `List` of `Breakpoint` objects. Each `Breakpoint` tells the `ColumnsLayout` the minimum width it must have to apply a specified number of columns. By using the `ColumnsLayout`, you’re able to make a form with two columns, but only if the screen is wide enough to display two columns. On smaller screens, the components are displayed in a single column.

The [Breakpoints](/docs/components/columns-layout#breakpoints) section in the `ColumnsLayout` article explains breakpoints in more detail.

To keep the code maintainable, set the breakpoints in a separate method. In that method, you can also control the horizontal and vertical spacing between the components inside the `ColumnsLayout` with the `setSpacing()` method.

```java
private void setColumnsLayout() {

  //Have two columns in the ColumnsLayout if it's wider than 600px
  List<Breakpoint> breakpoints = List.of(
    new Breakpoint(600, 2));

  //Add the List of breakpoints
  layout.setBreakpoints(breakpoints);

  //Set the spacing between components using a DWC CSS variable
  layout.setSpacing("var(--dwc-space-l)")
}
```

Finally, you can put the newly created `ColumnsLayout` into the bound component of `FormView`, while also setting the max width, and adding the class name from earlier:

```java
self.setMaxWidth(600)
  .addClassName("card")
  .add(layout);
```

## Completed `FormView` {#completed-formview}

After adding a `Customer` copy, the interactive components, and the `ColumnsLayout`, your `FormView` should look like the following:

<!-- vale off -->
<ExpandableCode title="FormView.java" language="java" startLine={1} endLine={15}>
{`@Route("customer")
@FrameTitle("Customer Form")
public class FormView extends Composite<Div> {
  private final CustomerService customerService;
  private Customer customer = new Customer();
  private Div self = getBoundComponent();
  private TextField firstName = new TextField("First Name", e -> customer.setFirstName(e.getValue()));
  private TextField lastName = new TextField("Last Name", e -> customer.setLastName(e.getValue()));
  private TextField company = new TextField("Company", e -> customer.setCompany(e.getValue()));
  private ChoiceBox country = new ChoiceBox("Country",
      e -> customer.setCountry((Customer.Country) e.getSelectedItem().getKey()));
  private Button submit = new Button("Submit", ButtonTheme.PRIMARY, e -> submitCustomer());
  private Button cancel = new Button("Cancel", ButtonTheme.OUTLINED_PRIMARY, e -> navigateToMain());

  private ColumnsLayout layout = new ColumnsLayout(
    firstName, lastName,
    company, country,
    submit, cancel);

  public FormView(CustomerService customerService) {
    this.customerService = customerService;

    fillCountries();
    setColumnsLayout();

    self.setMaxWidth(600)
      .addClassName("card")
      .add(layout);

    submit.setStyle("margin-top", "var(--dwc-space-l)");
    cancel.setStyle("margin-top", "var(--dwc-space-l)");
  }

  private void setColumnsLayout() {
    List<Breakpoint> breakpoints = List.of(
      new Breakpoint(0, 1),
      new Breakpoint(600, 2));

    layout.setSpacing("var(--dwc-space-l)")
      .setBreakpoints(breakpoints);
  }

  private void fillCountries() {
    ArrayList<ListItem> listCountries = new ArrayList<>();
    for (Country countryItem : Customer.Country.values()) {
      listCountries.add(new ListItem(countryItem, countryItem.toString()));
    }
    country.insert(listCountries);
  }

  private void submitCustomer() {
    customerService.createCustomer(customer);
    navigateToMain();
  }

  private void navigateToMain(){
    Router.getCurrent().navigate(MainView.class);
  }

}
`}
</ExpandableCode>
<!-- vale on -->

## Running the app {#running-the-app}

When you've finished this step, you can compare it to [3-routing-and-composites](https://github.com/webforj/webforj-demo-application/tree/main/3-routing-and-composites) on GitHub. To see the app in action:

1. Navigate to the top-level directory containing the `pom.xml` file, this is `3-routing-and-composites` if you're following along with the version on GitHub.

2. Use the following Maven command to run the Spring Boot app locally:
    ```bash
    mvn
    ```

Running the app automatically opens a new browser at http://localhost:8080.

## Next step {#next-step}

Since users can now add customers, your app should be able to edit existing customers using the same form. In the next step, [Observers and Route Parameters](/docs/introduction/tutorial/observers-and-route-parameters), you’ll allow the customer `id` to be an initial parameter for `FormView`, so it can fill in the form with that customer's data and allow users to change the properties.