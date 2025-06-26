# Custom Components

## Q: How do I create reusable composite components with proper encapsulation?

**A:** Build composites with clear APIs and internal state management:

```java
public class UserForm extends Composite<FlexLayout> {
    private TextField firstName;
    private TextField lastName;
    private TextField company;
    private ChoiceBox<String> country;
    private Button submit;
    
    private BindingContext<Customer> context;
    private Customer customer = new Customer();
    private String customerId = "";

    public UserForm() {
        initializeComponents();
        setupDataBinding();
        setupLayout();
    }
    
    private void initializeComponents() {
        firstName = new TextField("First Name")
            .setRequired(true)
            .setInvalidMessage("First name is required");
        lastName = new TextField("Last Name")
            .setRequired(true)
            .setInvalidMessage("Last name is required");
        company = new TextField("Company");
        country = new ChoiceBox<>("Country");
        country.insert("USA", "Canada", "Mexico", "UK", "Germany");
        submit = new Button("Submit", ButtonTheme.PRIMARY);
        submit.setEnabled(false);
    }
    
    private void setupDataBinding() {
        context = BindingContext.of(this, Customer.class, true);
        context.addValidateListener(e -> submit.setEnabled(e.isValid()));
        context.read(customer);
    }
    
    private void setupLayout() {
        getBoundComponent()
            .setDirection(FlexDirection.COLUMN)
            .setSpacing("var(--dwc-space-m)")
            .add(
                firstName,
                lastName,
                company,
                country,
                submit
            );
    }
    
    // Public API methods
    public void bindToCustomer(Customer customer) {
        this.customer = customer;
        context.read(customer);
    }
    
    public ValidationResult writeToCustomer(Customer customer) {
        return context.write(customer);
    }
    
    public boolean isValid() {
        return context.isValid();
    }
    
    public Customer getCustomer() {
        return customer;
    }
}
```

## Q: How do I integrate third-party web components using ElementComposite?

**A:** Use `ElementComposite` with property descriptors and event handling:

```java
import com.webforj.component.element.ElementComposite;
import com.webforj.component.element.PropertyDescriptor;
import com.webforj.component.element.annotation.NodeName;
import com.webforj.annotation.JavaScript;

@JavaScript(value = "https://cdn.example.com/my-slider.js", 
            attributes = {@Attribute(name = "type", value = "module")})
@NodeName("my-slider")
public final class MySlider extends ElementComposite 
    implements HasValue<MySlider, Double>, HasStyle<MySlider> {

    private final PropertyDescriptor<Double> valueProp =
        PropertyDescriptor.property("value", 0.0);
    private final PropertyDescriptor<Double> minProp =
        PropertyDescriptor.property("min", 0.0);
    private final PropertyDescriptor<Double> maxProp =
        PropertyDescriptor.property("max", 100.0);
    private final PropertyDescriptor<Double> stepProp =
        PropertyDescriptor.property("step", 1.0);

    public MySlider() {
        // Component automatically initialized by framework
    }

    @Override
    public MySlider setValue(Double value) {
        set(valueProp, value);
        return this;
    }

    @Override
    public Double getValue() {
        return get(valueProp);
    }

    public MySlider setMin(Double min) {
        set(minProp, min);
        return this;
    }

    public Double getMin() {
        return get(minProp);
    }

    public MySlider setMax(Double max) {
        set(maxProp, max);
        return this;
    }

    public Double getMax() {
        return get(maxProp);
    }

    public MySlider setStep(Double step) {
        set(stepProp, step);
        return this;
    }

    public Double getStep() {
        return get(stepProp);
    }
}
```

---

## Navigation

- [← Previous: Event Handling](../events/01-event-handling.md)
- [Next: Testing →](02-testing.md)
- [Back to Cookbook Index](../00-index.md)