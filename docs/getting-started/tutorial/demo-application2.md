# Demo Application Documentation

## Overview

This documentation provides an in-depth look at the `webforJ Demo Application`, focusing on the data model, the structure of the data folder, and the repository pattern implementation. We also explain how components like `ObjectTable`, context URLs, and `HasEntityKey` integrate into this setup, and highlight differences from the previous application version.

## Model Class

### `Customer` Model

The `Customer` class, found in `src/main/java/com/webforj/demos/Customer.java`, represents the application's core data model, encapsulating key attributes such as `customerId`, `name`, and contact information. This model is mapped to the database and plays a crucial role in organizing customer-related data for the demo.

```java title="Customer.java"
package com.webforj.demos;

public class Customer {
    private String customerId;
    private String name;
    private String email;
    private String phone;
    
    // Getters and Setters
}
```

### Purpose of the Model

The `Customer` model encapsulates all the necessary information about a customer in a structured format, providing a unified way to access and manipulate customer data within the application. This structure allows us to simplify data storage, retrieval, and management, supporting scalability and readability.

## Data Folder

The `data` folder, located in `src/main/resources/data`, contains essential configuration files and data used by the application. This folder is organized to support seamless access to external resources, such as configuration files, data files, or assets required for the app’s operation.

- **Why Use a Data Folder?**
  - Centralizes data resources and configuration files.
  - Supports easier management and maintenance of external files.
  - Ensures that external resources are logically separated from code, enhancing modularity and organization.

## ObjectTable

The `ObjectTable` is used in this application to manage object instances dynamically instead of using static data. By leveraging `ObjectTable`, we maintain flexibility and ensure that our data is not restricted to a single state or instance, allowing for more dynamic data interactions.

- **Why Use `ObjectTable` Instead of Static Instances?**
  - Dynamic object management: Avoids limitations of static instances, allowing the application to manage multiple objects more effectively.
  - Enhanced data persistence: Improves data storage by providing an organized structure for managing data instances.
  - Extensibility: Offers flexibility to adapt data models and structures as the application scales or as additional functionality is introduced.

## Context URLs

The `contextURL` in webforJ refers to paths that are dynamically resolved at runtime, enabling the application to adaptively load resources based on the current context. This approach is used to manage URLs for data and other resources flexibly.

- **How to Resolve `contextURL` Paths**:
  - Use webforJ’s URL resolver methods to dynamically load assets or data files.
  - Resolve paths based on the current environment, supporting multiple deployment configurations or runtime changes.
  - Access data based on context URL definitions, which provides flexibility to adapt the resource paths without changing code.

## HasEntityKey and Repository Pattern

The application’s repository pattern uses `HasEntityKey` to map models directly to database entities, allowing each model to be represented by a unique identifier, such as a primary database key. This pattern enables consistent data retrieval and manipulation, facilitating a more structured data access layer.

### `HasEntityKey` Implementation

- **Usage**:
  - `HasEntityKey` provides a mechanism to assign a unique entity key to each model, simplifying data access.
  - For example, the `Customer` model can utilize its primary key in the database as its `entityKey`.

:::tip
When using `HasEntityKey`, it’s recommended to use the primary key of the database to ensure consistency across database transactions.
:::

### Updated Code - `DemoApplication.java`

```java title="DemoApplication.java"
package com.webforj.demos;

import static com.webforj.component.optiondialog.OptionDialog.showMessageDialog;

import com.webforj.App;
import com.webforj.annotation.AppTitle;
import com.webforj.annotation.InlineStyleSheet;
import com.webforj.component.button.Button;
import com.webforj.component.button.ButtonTheme;
import com.webforj.component.html.elements.Paragraph;
import com.webforj.component.table.Table;
import com.webforj.component.window.Frame;
import com.webforj.demos.data.Service;
import com.webforj.demos.models.Customer;
import com.webforj.exceptions.WebforjException;

@InlineStyleSheet("context://css/demoApplication.css")
@AppTitle("Demo Step 2")
public class DemoApplication extends App {
  Paragraph demo = new Paragraph("Demo Application!");
  Button btn = new Button("Info");
  Table<Customer> table = new Table<>();

  @Override
  public void run() throws WebforjException {
    Frame mainFrame = new Frame();
    mainFrame.addClassName("mainFrame");
    buildTable();
    btn.setTheme(ButtonTheme.PRIMARY).setWidth(100)
        .addClickListener(e -> showMessageDialog("This is a demo with a table!", "Info"));

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

- **Key Components**:
  - **Frame**: The main container for application components.
  - **Button**: Configured with a primary theme and click listener to show an info dialog.
  - **Table**: Displays customer data with dynamically populated columns.

### Differences from Previous Versions

In this version, `DemoApplication` is enhanced with:
- **Dynamic Data Handling**: Through `ObjectTable` instead of static data instances, improving flexibility.
- **Repository and `HasEntityKey` Integration**: Directly linking model classes to database entities, simplifying data management.
- **Context URL Resolution**: Enabling dynamic loading of resources based on environment and runtime context.

This setup allows `DemoApplication` to efficiently manage resources, supporting a more adaptable and scalable application structure.
