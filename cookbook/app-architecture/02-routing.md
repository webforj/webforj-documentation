# Routing & Navigation

## Q: How do I define routes and handle navigation parameters?

**A:** Use `@Route` annotation and implement navigation observers:

```java
import com.webforj.router.annotation.Route;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.event.DidEnterObserver;
import com.webforj.router.event.DidEnterEvent;
import com.webforj.router.history.ParametersBag;

@Route("/customer/:id?")
@FrameTitle("Customer Form")
public class CustomerFormView extends Composite<Div> implements DidEnterObserver {
    private TextField nameField;
    private TextField emailField;
    private BindingContext<Customer> context;
    private String customerId = "";
    
    @Override
    public void onDidEnter(DidEnterEvent event, ParametersBag parameters) {
        parameters.get("id").ifPresentOrElse(
            id -> {
                // Edit mode - load existing customer
                this.customerId = id;
                Customer customer = customerService.findById(UUID.fromString(id));
                context.read(customer);
                Page.getCurrent().setTitle("Edit Customer - " + customer.getName());
            },
            () -> {
                // Add mode - create new customer
                this.customerId = "";
                Customer customer = new Customer();
                context.read(customer);
                Page.getCurrent().setTitle("New Customer");
            }
        );
    }
}
```

## Q: How do I implement programmatic navigation between views?

**A:** Use the `Router` class for navigation:

```java
import com.webforj.router.Router;
import com.webforj.router.history.ParametersBag;

public class CustomerListView extends Composite<Div> {
    
    private void setupNavigation() {
        // Simple navigation to another view
        addCustomerButton.onClick(e -> 
            Router.getCurrent().navigate(CustomerFormView.class));
        
        // Navigation with parameters
        table.onItemDoubleClick(event -> {
            Customer customer = event.getItem();
            Router.getCurrent().navigate(
                CustomerFormView.class,
                ParametersBag.of("id", customer.getId().toString())
            );
        });
        
        // Navigation with callback
        editButton.onClick(e -> {
            String customerId = getSelectedCustomerId();
            Router.getCurrent().navigate(
                CustomerFormView.class,
                ParametersBag.of("id", customerId),
                (component) -> {
                    component.ifPresent(view -> {
                        // Do something after navigation
                        System.out.println("Navigated to customer form");
                    });
                }
            );
        });
    }
}
```

## Q: How do I prevent navigation away from forms with unsaved changes?

**A:** Use `WillLeaveObserver` to implement form protection:

```java
import com.webforj.router.event.WillLeaveObserver;
import com.webforj.router.event.WillLeaveEvent;

@Route("/customer/:id?")
public class CustomerFormView extends Composite<Div> 
    implements WillLeaveObserver {
    
    private boolean isDirty = false;
    
    @Override
    public void onWillLeave(WillLeaveEvent event, ParametersBag parameters) {
        if (isDirty) {
            event.veto(true);
            OptionDialog.showConfirmDialog(
                "Unsaved Changes",
                "You have unsaved changes. Are you sure you want to leave?",
                (result) -> {
                    if (result == OptionDialog.Result.YES) {
                        isDirty = false;
                        event.getRouter().navigate(event.getTargetRoute());
                    }
                }
            );
        } else {
           event.veto(false);
        }
    }
    
    private void markFormDirty() {
        isDirty = true;
    }
    
    private void markFormClean() {
        isDirty = false;
    }
}
```

## Q: Why am I getting `java.lang.IllegalArgumentException: Class name 'Layout' is not allowed for generating a route path` when using `@Route("")` for my `MainLayout` class?**

**A:** The webforJ router, by default, attempts to generate a route path based on the class name. When a class name ends with "Layout" (like `MainLayout`), and you provide an empty path `""` in the `@Route` annotation, the router's `AUTO_DETECT` mechanism for route types can lead to this conflict. It tries to treat it as a `VIEW` route with an auto-generated path, but class names ending in 'Layout' are reserved for `LAYOUT` type routes which are pathless by default.


One solution could be to use just `@Route` without specifying a value for the `MainLayout` class. This allows the router to correctly interpret it as a layout route, which doesn't have a path segment in the URL.

According to the `Route.java` annotation documentation:
- `String value() default AUTO_GENERATED_NAME;`: If no path is set, the router will generate a path based on the route's component class name.
- `Type type() default Type.AUTO_DETECT;`: This will automatically determine the type of the route. If the class name ends with "Layout", the type will be `LAYOUT`.

By using `@Route` (which defaults `value` to `AUTO_GENERATED_NAME` and `type` to `AUTO_DETECT`), the router correctly identifies `MainLayout` as a `LAYOUT` type, which are pathless and thus avoids the error.


---

## Navigation

- [← Previous: Basic App](01-basic-app.md)
- [Next: Authentication & Security →](03-authentication.md)
- [Back to Cookbook Index](../00-index.md)