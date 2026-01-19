---
title: "Modernization Tutorial"
sidebar_position: 4
---

This tutorial walks through modernizing an existing Java Swing app by integrating it with webforJ using the `WebswingConnector`. You'll learn how to make a traditional desktop app web-accessible and incrementally add modern web features such as web-based dialogs and interactive forms using webforJ components.

:::note Prerequisites
Before starting this tutorial, complete the [Setup and Configuration](./setup) steps to configure your Webswing server and CORS settings.
:::

:::tip Source Code
The complete source code for this tutorial is available on GitHub: [webforj/webforj-webswing-integration-tutorial](https://github.com/webforj/webforj-webswing-integration-tutorial)
:::

<div class="videos-container">
  <video controls preload="metadata">
    <source src="https://cdn.webforj.com/webforj-documentation/video/tutorials/webswing/modernization-tutorial.mp4#t=5" type="video/mp4"/>
  </video>
</div>

## The scenario {#the-scenario}

Imagine you have a customer management app built with Swing that's been in production for years. It works well, but users now expect web access and a modern interface. Rather than rewriting from scratch, you'll use Webswing to make it web-accessible immediately, then incrementally add modern web features such as web-based dialogs and forms using webforJ components.

## Starting point: the Swing app {#starting-point-the-swing-app}

The example Swing app is a customer table with typical CRUD operations. Like many enterprise Swing apps, it follows standard patterns:

```java
public class Application {
  private List<Customer> customers;
  private DefaultTableModel model;
  private JTable table;

  private void createTable() {
    String[] columnNames = { "Name", "Company", "Email" };
    model = new DefaultTableModel(columnNames, 0) {
      @Override
      public boolean isCellEditable(int row, int column) {
        return false;
      }
    };

    table = new JTable(model);
    table.setRowHeight(30);
    table.setRowSelectionAllowed(true);

    table.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 2) {
          // Handle double-click to edit
        }
      }
    });
  }

  private void showEditDialog(Customer customer) {
    JTextField nameField = new JTextField(customer.getName());
    JTextField companyField = new JTextField(customer.getCompany());
    JTextField emailField = new JTextField(customer.getEmail());

    Object[] fields = {
        "Name:", nameField,
        "Company:", companyField,
        "Email:", emailField
    };

    int result = JOptionPane.showConfirmDialog(null, fields, "Edit Customer",
        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
  }
}
```

This app works perfectly as a desktop app but lacks web accessibility. Users must install Java and run the JAR file locally.

## Step 1: making it Webswing-aware {#step-1-making-it-webswing-aware}

The first step is making the Swing app detect whether it's running under Webswing. This allows it to adapt its behavior without breaking desktop compatibility.

### Detecting the Webswing environment {#detecting-the-webswing-environment}

Add the Webswing API dependency to your Swing project:

```xml
<dependency>
  <groupId>org.webswing</groupId>
  <artifactId>webswing-api</artifactId>
  <version>25.1</version>
</dependency>
```

Then modify your app to detect the Webswing runtime:

```java
private void initWebswing() {
  api = WebswingUtil.getWebswingApi();
  isWebswing = api != null;

  if (isWebswing) {
    setupWebswingListeners();
  }
}
```

The key insight here is that `WebswingUtil.getWebswingApi()` returns `null` when running as a regular desktop app, allowing you to maintain dual-mode compatibility.

### Adapting behavior for web deployment {#adapting-behavior-for-web-deployment}

With detection in place, you can now adapt the app's behavior. The most important change is how user interactions are handled:

```java
private void handleDoubleClick(MouseEvent e) {
  int row = table.rowAtPoint(e.getPoint());
  if (row >= 0 && row < customers.size()) {
    Customer customer = customers.get(row);

    if (isWebswing) {
      api.sendActionEvent("select-customer", gson.toJson(customer), null);
    } else {
      showEditDialog(customer);
    }
  }
}
```

By branching behavior according to the value of `isWebswing`, the codebase can handle both environments.

## Step 2: creating the webforJ wrapper {#step-2-creating-the-webforj-wrapper}

Now that the Swing app can communicate via events, create a webforJ app that embeds the Swing app and adds modern web features such as web-based dialogs and forms.

### Setting up the connector {#setting-up-the-connector}

The `WebswingConnector` component embeds your Webswing-hosted app within a webforJ view:

```java
@Route("/")
public class CustomerTableView extends Composite<FlexLayout> {
  private FlexLayout self = getBoundComponent();

  public CustomerTableView(@Value("${webswing.connector.url}") String webswingUrl) {
    WebswingConnector connector = new WebswingConnector(webswingUrl);
    connector.setSize("100vw", "100vh");

    self.add(connector);
  }
}
```

The connector connects to your Webswing server, establishing a bidirectional communication channel.

### Handling events from Swing {#handling-events-from-swing}

When the Swing app sends events (like when a user double-clicks a row), the connector receives them:

```java
connector.onAction(event -> {
  switch (event.getActionName()) {
    case "select-customer":
      event.getActionData().ifPresent(data -> {
        JsonObject customer = JsonParser.parseString(data).getAsJsonObject();
        CustomerForm dialog = new CustomerForm(customer);
        self.add(dialog);
        dialog.onSave(() -> {
          Gson gson = new Gson();
          connector.performAction("update-customer", gson.toJson(customer));
        });
      });
      break;
  }
});
```

Now, instead of the Swing dialog, users see a modern web form built with webforJ components.

## Step 3: bidirectional communication {#step-3-bidirectional-communication}

The integration becomes powerful when communication flows both ways. The webforJ app can send updates back to the Swing app, keeping both UIs synchronized.

### Sending updates to Swing {#sending-updates-to-swing}

After the user edits a customer in the webforJ dialog:

```java
dialog.onSave(() -> {
  // Send updated customer back to Swing
  connector.performAction("update-customer", gson.toJson(customer));
});
```

### Processing updates in Swing {#processing-updates-in-swing}

The Swing app listens for these updates and refreshes its display:

```java
private void setupWebswingListeners() {
  api.addBrowserActionListener(event -> {
    if ("update-customer".equals(event.getActionName())) {
      Customer updated = gson.fromJson(event.getData(), Customer.class);
      updateCustomer(updated);
    }
  });
}
```

## Architecture benefits {#architecture-benefits}

This approach provides several advantages over a complete rewrite:

### Immediate web deployment {#immediate-web-deployment}

Your Swing app becomes web-accessible immediately without code changes. Users can access it through a browser while you work on enhancements.

### Progressive enhancement {#progressive-enhancement}

Start by replacing just the edit dialog, then gradually replace more components:

1. **Phase 1**: Embed the entire Swing app, replace only the edit dialog
2. **Phase 2**: Add webforJ navigation and menus around the embedded app
3. **Phase 3**: Replace the table with a webforJ table, keeping Swing for irreplaceable features
4. **Phase 4**: Eventually replace all Swing components

### Risk mitigation {#risk-mitigation}

Since the original Swing app remains functional, you can:

- Fall back to desktop deployment if needed
- Test new features alongside existing ones
- Migrate users gradually
- Maintain the same business logic
