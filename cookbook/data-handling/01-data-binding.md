# Data Binding & Validation

## Q: How do I implement automatic data binding between forms and model objects?

**A:** Use `BindingContext` for automatic data synchronization:

```java
import com.webforj.data.binding.BindingContext;

public class CustomerFormView extends Composite<Div> {
    private TextField firstName;  // Must match Customer.firstName
    private TextField lastName;   // Must match Customer.lastName
    private TextField email;      // Must match Customer.email
    private Button submit;
    
    private BindingContext<Customer> context;
    private Customer customer = new Customer();

    public CustomerFormView() {
        initializeComponents();
        setupDataBinding();
    }
    
    private void setupDataBinding() {
        // Enable automatic binding with validation
        context = BindingContext.of(this, Customer.class, true);
        
        // Listen for validation changes
        context.addValidateListener(e -> submit.setEnabled(e.isValid()));
        
        // Read data into form
        context.read(customer);
    }
    
    private void submitForm() {
        ValidationResult result = context.write(customer);
        if (result.isValid()) {
            processCustomer(customer);
            showSuccessMessage();
        }
    }
}
```

## Q: How do I create custom validation rules for data binding?

**A:** Use manual binding with custom validation logic:

```java
private void setupManualBinding() {
    context = new BindingContext<>(Customer.class);
    
    // Bind with custom validation
    context.bind(nameField, "name")
        .useValidator(value -> !value.isEmpty(), "Name cannot be empty")
        .useValidator(value -> value.length() >= 3, "Name must be at least 3 characters")
        .add();
    
    context.bind(emailField, "email")
        .useValidator(this::isValidEmail, "Invalid email format")
        .useReporter((component, messages) -> {
            // render the validation message
        })
        .add();
}

private boolean isValidEmail(String email) {
    return email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
}
```

## Q: How do I use Jakarta validation annotations with webforJ?

**A:** Add validation annotations to your model and enable validation in the binding context:

```java
import jakarta.validation.constraints.*;

public class Customer {
    @NotEmpty(message = "Name cannot be empty")
    @Pattern(regexp = "[a-zA-Z ]*", message = "Invalid characters in name")
    private String firstName = "";
    
    @NotEmpty(message = "Last name is required")
    private String lastName = "";
    
    @Email(message = "Invalid email format")
    @NotEmpty(message = "Email is required")
    private String email = "";
    
    @Min(value = 18, message = "Must be at least 18 years old")
    @Max(value = 120, message = "Age cannot exceed 120")
    private Integer age;
    
    // Getters and setters...
}

// In your view
private void setupValidation() {
    // Enable Jakarta validation (third parameter = true)
    context = BindingContext.of(this, Customer.class, true);
    
    context.addValidateListener(validationEvent -> {
        boolean isValid = validationEvent.isValid();
        submitButton.setEnabled(isValid);
        
        if (isValid) {
            // save data
        }
    });
}
```

---

## Navigation

- [← Previous: Layout Systems](../layout/01-layout-systems.md)
- [Next: Event Handling →](../events/01-event-handling.md)
- [Back to Cookbook Index](../00-index.md)