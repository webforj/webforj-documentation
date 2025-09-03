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



## Routing and view structure 

[Routing](../../routing/overview) in webforJ lets your app manage navigation between different views or pages, breaking your UI into focused, modular components.

### Enabling routing in the app class

Annotate your main app class with `@Routify`, specifying the package containing your views:

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

Each view is a separate class, typically extending `Composite<Div>`. Use the `@Route` annotation to assign a URL path, and `@FrameTitle` for the browser title. With Spring Boot, you can inject services (like `CustomerService`) directly into your views via constructor injection.

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
    // Check for 'id' parameter to determine add or edit mode
    // If present, load customer data and pre-fill form; otherwise, show blank form
  }
}
```

- The `@Route("/")` on `DemoView` makes it the default landing page.
- The `@Route("customer/:id?")` on `FormView` allows both add and edit modes, depending on the presence of an `id` parameter.

### Navigation and event handling

Use `Router.getCurrent().navigate(FormView.class)` to navigate to the form, or `Router.getCurrent().navigate(FormView.class, ParametersBag.of("id=" + e.getItemKey()))` to navigate with a specific customer ID for editing.

### Lifecycle: Handling Initialization with `onDidEnter`

When the `Router` navigates to a view, `onDidEnter` is triggered as part of the lifecycle to:
- **Load Data**: Initialize or fetch data required for the view based on route parameters.
- **Set Up the View**: Update UI elements dynamically based on the context.
- **React to State Changes**: Perform actions that depend on the view being active, such as resetting forms or highlighting components.

In `FormView`, the `onDidEnter` method checks for the presence of an `id` parameter and adjusts the form's behavior:
- **Edit Mode**: If an `id` is provided, fetch the customer’s data and pre-fill the form. The `Submit` button updates the entry.
- **Add Mode**: If no `id` is present, show a blank form. The `Submit` button creates a new customer.

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

### Running the app

From the step directory, run:

```bash
mvn spring-boot:run
```

Then open [http://localhost:8080](http://localhost:8080) in your browser.

---

With routing and view separation in place, your app is now ready for further scaling, including advanced data binding and validation in the next step.