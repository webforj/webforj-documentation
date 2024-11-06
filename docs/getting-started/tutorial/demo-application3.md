# Demo Application Documentation

## Overview

This updated version of the `webforJ Demo Application` leverages routing to create a more scalable and organized structure, specifically designed for managing customer entries. Key enhancements include the use of views for editing/creating customer entries, dynamic data handling, and a modular approach with components like `Composite<ColumnsLayout>`. These improvements support a more flexible, maintainable application architecture.

## Routing for Scalability

Routing is used in this application to handle multiple views, which is essential for scalability. By assigning unique routes to the different application views, such as `DemoView` and `FormView`, the app can manage new and existing entries effectively. This routing approach ensures that future enhancements can be added with minimal changes to the existing codebase, improving scalability and maintainability.

## Creating a New View for Adding/Editing Entries

### `FormView` for Customer Entries

The `FormView` class is the dedicated view for adding or editing customer information. It handles user input and stores customer data in fields like `firstName`, `lastName`, `company`, and `country`.

- **Route Assignment**:
  - `FormView` is assigned a route with an optional parameter `customer/:id?`, allowing it to dynamically switch between “add” and “edit” modes based on the URL.
  - **Note**: The parameter in `Route` is optional, so the form can be loaded with or without an ID, depending on whether a customer is being added or edited.

```java title="FormView.java"
@Route("customer/:id?")
@FrameTitle("Customer Form")
public class FormView extends Composite<Div> implements DidEnterObserver {
    // Fields, buttons, layout, etc.
}
```

## Extending `Composite<ColumnsLayout>`

### Design Choice: `Composite<ColumnsLayout>` vs. Extending Components Directly

In `FormView`, we extend `Composite<ColumnsLayout>` instead of using individual components like `Div`. This approach enhances flexibility and modularity:

- **Why `Composite<ColumnsLayout>`?** Columns layout organizes form fields into a clean, structured layout, making data entry more intuitive.
- **Why Not Extend a Component Directly?** Extending `Composite<ColumnsLayout>` provides flexibility to add or modify child components easily. It allows us to work within a layout manager (in this case, columns) to control the positioning of elements with higher precision.
  
```java title="FormView.java"
public class FormView extends Composite<Div> {
    ColumnsLayout columnsLayout = new ColumnsLayout(firstName, lastName, company, country, cancel, submit);
}
```

## Adding Components to the Screen

In `DemoView`, the application dynamically adds components to the screen within a vertical layout. This layout includes a table listing customers and an “Add Customer” button, which navigates users to `FormView` for adding new customer data.

```java title="DemoView.java"
FlexLayout layout = FlexLayout.create(table, add)
    .vertical()
    .contentAlign()
    .center()
    .build()
    .setPadding("var(--dwc-space-l)");

self.add(layout);
```

- **Components Added**:
  - **Table**: Displays customer data (first name, last name, company, country).
  - **Button**: “Add Customer” button navigates to `FormView`.

## Populating the Country ChoiceBox

`fillCountries()` in `FormView` populates the `country` field using values from the `Customer.Country` enum. This method dynamically creates a list of available countries, ensuring that the form offers a choice for users to select from without hardcoding values.

```java title="FormView.java"
private void fillCountries() {
    ArrayList<ListItem> listCountries = new ArrayList<>();
    for (Country countryItem : Customer.Country.values()) {
        listCountries.add(new ListItem(countryItem, countryItem.toString()));
    }
    country.insert(listCountries);
}
```

## Adding New Entries (ADD Mode)

The `FormView` uses the route parameter to decide between “add” and “edit” modes:

- **Add Mode**: If no `id` parameter is passed, the view initializes the form for adding a new customer entry.
- **Edit Mode**: If an `id` is present, the form loads customer data based on that `id`.

### Logic for Adding Only

When adding a new entry, the application initializes a blank `Customer` object and attaches an action to the `Submit` button that invokes the `addCustomer` method from `Service`. This method adds the new customer to the repository.

```java title="FormView.java"
submit.addClickListener(e -> submit("add"));
```

- **Difference from Edit**: Unlike the edit mode, the form fields in add mode are blank, allowing users to input new customer details. The `submit` button triggers `Service.getCurrent().addCustomer(customer)`.

## Editing Existing Entries (EDIT Mode)

When a user selects a customer from the table in `DemoView`, they are directed to `FormView` with the `id` of the selected customer passed as a parameter. The form then retrieves the customer’s data and populates the fields for editing.

### Route Logic in `DemoView`

The `editCustomer` method in `DemoView` handles the route logic by navigating to `FormView` with the selected customer’s ID.

```java title="DemoView.java"
private void editCustomer(TableItemClickEvent<Customer> e) {
    Router.getCurrent().navigate(FormView.class, ParametersBag.of("id=" + e.getItemKey()));
}
```

### onDidEnter Logic in `FormView`

The `onDidEnter` method in `FormView` checks for the presence of an `id` parameter. If `id` is present, it retrieves the customer data and sets the fields accordingly. Otherwise, the form initializes in add mode.

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

## Navigation and View Switching

Navigation is handled through the `Router` component. In both `DemoView` and `FormView`, `Router.getCurrent().navigate()` is used to switch between views, supporting seamless transitions across different parts of the application.

## Extracting Logic into `DemoView`

The `DemoApplication` logic has been modularized into `DemoView` and `FormView`, separating table display from form management. This design allows for better organization, scalability, and reusability of code. Key elements like routing, table setup, and data handling are encapsulated within `DemoView`, leaving `FormView` to handle the entry form.
