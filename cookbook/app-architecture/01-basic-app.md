# Basic App Architecture

## Q: How do I create a basic webforJ application with proper structure?

**A:** Create a class extending `App` with the `run()` method for non-routed apps, or use `@Routify` for routed applications:

```java
// Basic application
import com.webforj.App;
import com.webforj.Frame;
import com.webforj.component.button.Button;
import com.webforj.exceptions.WebforjException;

public class MyApplication extends App {
    @Override
    public void run() throws WebforjException {
        Frame frame = new Frame();
        Button button = new Button("Hello World!");
        frame.add(button);
    }
}

// Routed application
import com.webforj.App;
import com.webforj.annotation.Routify;
import com.webforj.annotation.AppTitle;

@Routify(packages = "com.myapp.views", debug = true)
@AppTitle("My Routed App")
public class MyRoutedApplication extends App {
    // No run() method needed - routing handles initialization
}
```

## Q: How do I add CSS and external resources to my application?

**A:** Use the `@StyleSheet` annotation to include CSS files and external files:

```java
import com.webforj.annotation.StyleSheet;

@StyleSheet("ws://css/app.css")
@StyleSheet("https://example.com/file.css")
public class MyApplication extends App {
    @Override
    public void run() throws WebforjException {
        // Application logic
    }
}
```

## Q: How do I structure a multi-view application with proper separation of concerns?

**A:** Use routing with separate view classes that extend `Composite`:

```java
// Main application
@Routify(packages = "com.myapp.views")
@AppTitle("Enterprise App")
public class EnterpriseApplication extends App {
    // Framework handles routing automatically
}

// Home view
import com.webforj.router.annotation.Route;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.component.Composite;
import com.webforj.component.html.elements.Div;

@Route("/")
@FrameTitle("Home")
public class HomeView extends Composite<Div> {
    public HomeView() {
        getBoundComponent().add(new H1("Welcome Home!"));
    }
}

// Customer management view
@Route("/customers")
@FrameTitle("Customer Management")
public class CustomerView extends Composite<Div> {
    private Table<Customer> customerTable;
    private Button addButton;
    
    public CustomerView() {
        setupComponents();
        setupLayout();
    }
    
    private void setupComponents() {
        customerTable = new Table<>(Customer.class);
        addButton = new Button("Add Customer", ButtonTheme.PRIMARY);
    }
    
    private void setupLayout() {
        FlexLayout layout = FlexLayout.create()
            .vertical()
            .spacing("var(--dwc-space-l)")
            .build();
            
        layout.add(addButton, customerTable);
        getBoundComponent().add(layout);
    }
}
```

---

## Navigation

- [Next: Routing & Navigation →](02-routing.md)
- [Back to Cookbook Index](../00-index.md)