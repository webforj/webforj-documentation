---
title: Validating and Binding Data
sidebar_position: 6
pagination_next: null
description: Step 5 - Add validation checks and bind data to the UI.
---

Your app from [Scaling with Routing and Composites](/docs/introduction/tutorial/scaling-with-routing-and-composites) can now support multiple pages, which allowed you to create `FormView`, a UI dedicated to editing and adding customer data. This step uses [Data binding](/docs/data-binding/overview), which connects UI components directly to the data model for automatic value synchronization. This reduces boilerplate in your app and lets you add validation checks to the Spring entity `Customer`, making your users provide complete and accurate information when filling out forms. This step covers the following concepts:

- [Jakarta validation](https://beanvalidation.org)
- Using the [`BindingContext`](https://javadoc.io/doc/com.webforj/webforj-data/latest/com/webforj/data/binding/BindingContext.html) class

Completing this step creates a version of [5-validating-and-binding-data](https://github.com/webforj/webforj-demo-application/tree/main/5-validating-and-binding-data).

<!-- <div class="videos-container">
  <video controls>
    <source src="https://cdn.webforj.com/webforj-documentation/video/tutorials/validating-and-binding-data.mp4" type="video/mp4"/>
  </video>
</div> -->

## Defining validation rules {#defining-validation-rules}

Developing an app with editable data should include validation. Validation checks help maintain meaningful and accurate user-submitted data. If left unchecked, it could lead to issues, so it’s important to catch the kinds of errors users can make when filling out a form in real time.

Since what’s considered valid can differ between properties, you'll need to define what makes each property valid and inform the user if there's something that's invalid. Fortunately, you can easily do this with [Jakarta Validation](https://beanvalidation.org). Jakarta validation allows you to add constraints to properties as annotations.

This tutorial uses two Jakarta annotations, `@NotEmpty` and `@NotPattern`. `@NotEmpty` checks for null and empty strings, while `@NotPattern` checks if the property matches a regular expression that you set. Both annotations allow you to add a message to display when the property becomes invalid.

To require that both first and last names are mandatory and contain only letters, while making the company name optional and allowing letters, numbers, and spaces, apply the following annotations to the `Customer` entity:

<!-- vale off -->
<ExpandableCode title="Customer.java" language="java" startLine={8} endLine={28}>
{`@Entity
@Table(name = "customers")
public class Customer {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

// highlight-next-line
  @NotEmpty(message = "Customer first name is required")
// highlight-next-line
  @Pattern(regexp = "[a-zA-Z]*", message = "Invalid characters")
  private String firstName = "";

// highlight-next-line
  @NotEmpty(message = "Customer last name is required")
// highlight-next-line
  @Pattern(regexp = "[a-zA-Z]*", message = "Invalid characters")
  private String lastName = "";

// highlight-next-line
  @Pattern(regexp = "[a-zA-Z0-9 ]*", message = "Invalid characters")
  private String company = "";

  private Country country = Country.UNKNOWN;

  public enum Country {
    UNKNOWN,
    GERMANY,
    ENGLAND,
    ITALY,
    USA
  }

  public Customer(String firstName, String lastName, String company, Country country) {
    setFirstName(firstName);
    setLastName(lastName);
    setCompany(company);
    setCountry(country);
  }

  public Customer(String firstName, String lastName, String company) {
    this(firstName, lastName, company, Country.UNKNOWN);
  }

  public Customer(String firstName, String lastName) {
    this(firstName, lastName, "");
  }

  public Customer(String firstName) {
    this(firstName, "");
  }

  public Customer() {
  }

  public void setFirstName(String newName) {
    firstName = newName;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setLastName(String newName) {
    lastName = newName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setCompany(String newCompany) {
    company = newCompany;
  }

  public String getCompany() {
    return company;
  }

  public void setCountry(Country newCountry) {
    country = newCountry;
  }

  public Country getCountry() {
    return country;
  }

  public Long getId() {
    return id;
  }

}
`}
</ExpandableCode>
<!-- vale on -->

See the [Jakarta Bean Validation constraints reference](https://jakarta.ee/specifications/bean-validation/3.0/apidocs/jakarta/validation/constraints/package-summary.html) for a full list of validations, or learn more from the [webforJ Jakarta Validation article](/docs/data-binding/validation/jakarta-validation).

## Binding the fields {#binding-the-fields}

To use the validation checks in `Customer` for the UI in `FormView`, you’ll make a `BindingContext` for data binding. Before data binding, each field in `FormView` required an event listener to sync with a Spring entity `Customer` manually. Creating a `BindingContext` in `FormView` binds and automatically syncs the `Customer` data model to the UI components.

### Creating a `BindingContext` {#creating-a-bindingcontext}

An instance of `BindingContext` needs the Spring bean that the bindings are synchronized with. In `FormView`, declare a `BindingContext` using the `Customer` entity:

```java title="FormView.java" {4}
public class FormView extends Composite<Div> implements WillEnterObserver {
  private final CustomerService customerService;

  private BindingContext<Customer> context;

  Customer customer = new Customer();
```

Then, to automatically bind UI components to bean properties based on their names, use `BindingContext.of()` with the following parameters:

- **`this`** : Earlier, you declared `context` as the `BindingContext`. The first parameter sets what object contains the bindable components.
- **`Customer.class`** : The second parameter is the class of the bean to use for binding.
- **`true`** : The third parameter enables Jakarta validation, allowing the context to use the validations you set for `Customer`. Doing this will change the style of invalid components and display the set messages.

All together, it'll look like the following line of code:

```java
context = BindingContext.of(this, Customer.class, true);
```

### Making the form responsive {#making-the-form-responsive}

With data binding, your app now automatically performs validation checks. By adding an event listener to the checks, you can prevent users from submitting an invalid form. Add the following to make the submit button active only when the form's valid:

```java {2}
context = BindingContext.of(this, Customer.class, true);
context.onValidate(e -> submit.setEnabled(e.isValid()));
```

### Removing event listners for components {#removing-event-listners-for-components}

Every UI change is now automatically synced with the `BindingContext`. This means you can now remove the event listeners to each field:

**Before**
```java title="FormView.java"
// Without data binding
TextField firstName = new TextField("First Name", e -> customer.setFirstName(e.getValue()));
TextField lastName = new TextField("Last Name", e -> customer.setLastName(e.getValue()));
TextField company = new TextField("Company", e -> customer.setCompany(e.getValue()));
ChoiceBox country = new ChoiceBox("Country",
    e -> customer.setCountry(Country.valueOf(e.getSelectedItem().getText())));
```

**After**
```java title="FormView.java"
// With data binding
TextField firstName = new TextField("First Name");
TextField lastName = new TextField("Last Name");
TextField company = new TextField("Company");
ChoiceBox country = new ChoiceBox("Country");
```

### Binding by property names {#binding-by-property-names}

Since each component's name matched to the data model, webforJ applied [Automatic Binding](/docs/data-binding/automatic-binding). If the names didn't match, you could use the `@UseProperty` annotation to map them.

```java
@UseProperty("firstName")
TextField firstNameField = new TextField("First Name");
```

### Reading data in the `fillForm()` method {#reading-data-in-the-fillForm()-method}

Previously, in the `fillForm()` method, you initialized each component's value by manually retrieving the data from the `Customer` copy. But now, since you’re using a `BindingContext`, you can use the `read()` method. This method fills each bound component with the associated property from the data in the `Customer` copy.

In the `fillForm()` method, replace the `setValue()` methods with `read()`:

```java title="FormView.java" {6}
public void fillForm(Long customerId) {
  customer = customerService.getCustomerByKey(customerId);
  
  // Removed each setValue() method for the UI components
    
    context.read(customer);
  }
```

### Adding validation to `submitCustomer()` {#adding-validation-to-submitcustomer}

The last change to `FormView` for this step will be adding a safeguard to the `submitCustomer()` method. Before committing changes to the H2 database, the app will perform a final validation on the results of the bound context using the `write()` method.

The `write()` method updates a bean's properties using the bound UI components in the `BindingContext` and returns a `ValidationResult`.

Use the `write()` method to write to the `Customer` copy using the bound components in `FormView`. Then, if the returned `ValidationResult` is valid, update the H2 database using the written data.


```java title="FormView.java" {2-3}
private void submitCustomer() {
  ValidationResult results = context.write(customer);
  if (results.isValid()) {
    if (customerService.doesCustomerExist(customerId)) {
      customerService.updateCustomer(customer);
    } else {
      customerService.createCustomer(customer);
    }
    navigateToMain();
  }
}
```

### Completed `FormView`

With these changes, here's what `FormView` looks like. The app now supports data binding and validation using Spring Boot and webforJ. Form inputs are automatically synchronized with the model and checked against validation rules.

<!-- vale off -->
<ExpandableCode title="FormView.java" language="java" startLine={1} endLine={15}>
{`@Route("customer/:id?<[0-9]+>")
@FrameTitle("Customer Form")
public class FormView extends Composite<Div> implements WillEnterObserver {
  private final CustomerService customerService;
  private BindingContext<Customer> context;

  private Customer customer = new Customer();
  private Long customerId = 0L;
  private Div self = getBoundComponent();
  private TextField firstName = new TextField("First Name");
  private TextField lastName = new TextField("Last Name");
  private TextField company = new TextField("Company");
  private ChoiceBox country = new ChoiceBox("Country");
  private Button submit = new Button("Submit", ButtonTheme.PRIMARY, e -> submitCustomer());
  private Button cancel = new Button("Cancel", ButtonTheme.OUTLINED_PRIMARY, e -> navigateToMain());

  private FlexLayout buttonsLayout = FlexLayout.create(submit, cancel)
    .horizontal()
    .justify().between()
    .build();

  private ColumnsLayout columnsLayout = new ColumnsLayout(
    firstName, lastName,
    company, country,
    buttonsLayout);

  public FormView(CustomerService customerService) {
    this.customerService = customerService;

    context = BindingContext.of(this, Customer.class, true);
    context.onValidate(e -> submit.setEnabled(e.isValid()));

    fillCountries();
    setLayouts();

    self.setMaxWidth(600)
      .setHeight("100dvh")
      .setStyle("margin", "var(--dwc-space-l) auto")
      .add(columnsLayout);
  }

  private void setLayouts() {
    buttonsLayout.setSpacing("var(--dwc-space-3xl)")
      .setItemGrow(1, submit, cancel)
      .setMargin("var(--dwc-space-l) 0");

    List<Breakpoint> breakpoints = List.of(
      new Breakpoint(0, 1),
      new Breakpoint(600, 2));

    columnsLayout.setSpacing("var(--dwc-space-3xl)")
      .setStyle("padding", "var(--dwc-space-l)")
      .setSpan(buttonsLayout, 2)
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
    ValidationResult results = context.write(customer);
    if (results.isValid()) {
      if (customerService.doesCustomerExist(customerId)) {
        customerService.updateCustomer(customer);
      } else {
        customerService.createCustomer(customer);
      }
      navigateToMain();
    }
  }

  private void navigateToMain(){
    Router.getCurrent().navigate(MainView.class);
  }

  @Override
  public void onWillEnter(WillEnterEvent event, ParametersBag parameters) {
    parameters.getInt("id").ifPresentOrElse(id -> {
      customerId = Long.valueOf(id);
      if (customerService.doesCustomerExist(customerId)) {
         event.accept();
         fillForm(customerId);
        } else {
          event.reject();
          navigateToMain();
        }

    }, () -> event.accept());
        
  }

  public void fillForm(Long customerId) {
    customer = customerService.getCustomerByKey(customerId);
    context.read(customer);
    }

}
`}
</ExpandableCode>
<!-- vale on -->

## Running the app {#running-the-app}

When you’ve finished this step, you can compare it to [5-validating-and-binding-data](https://github.com/webforj/webforj-demo-application/tree/main/5-validating-and-binding-data) on GitHub. To see the app in action:

1. Navigate to the top-level directory containing the `pom.xml` file, this is `5-validating-and-binding-data` if you're following along with the version on GitHub.

2. Use the following Maven command to run the Spring Boot app locally:
    ```bash
    mvn
    ```

Running the app automatically opens a new browser at http://localhost:8080.

:::info Next steps
Looking for more ways to improve your app from this tutorial? You can try using the [`AppLayout`](/docs/components/app-layout) component as a wrapper to add your customer table and add more features.
:::