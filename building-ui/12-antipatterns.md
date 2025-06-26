# Common Anti-Patterns and Pitfalls

## Anti-Pattern 1: Excessive Client Queries

```java
// DON'T: Query component state in event handlers
private void badEventHandler(ComponentEvent event) {
    MyComponent comp = (MyComponent) event.getComponent();
    String value = comp.getValue();     // Unnecessary round-trip
    boolean enabled = comp.isEnabled(); // Unnecessary round-trip
}

// DO: Use event payload or maintain state
private void goodEventHandler(MyComponentEvent event) {
    String value = event.getValue();    // From event payload
    boolean enabled = this.isEnabled(); // From local state
}
```

## Anti-Pattern 2: Ignoring Lifecycle Management

```java
// DON'T: Forget to clean up resources
public class LeakyComponent extends ElementComposite {
    private Interval interval;

    public LeakyComponent() {
        interval = new Interval(1f, e -> updateDisplay());
        interval.start(); // Never stopped!
    }
}

// DO: Properly manage resources
public class CleanComponent extends ElementComposite {
    private Interval interval;

    @Override
    protected void onDidCreate() {
        interval = new Interval(1f, e -> updateDisplay());
        interval.start();
    }

    @Override
    protected void onDidDestroy() {
        super.onDidDestroy();

        if (interval != null) {
            interval.stop();
        }
    }
}
```

## Anti-Pattern 3: Overusing Element Access

```java
// DON'T: Bypass the ElementComposite abstraction
public class BadComponent extends ElementComposite {
    public void setValue(String value) {
        getElement().setProperty("value", value);
    }
}

// DO: Use PropertyDescriptor pattern
public class GoodComponent extends ElementComposite {
    private final PropertyDescriptor<String> value =
        PropertyDescriptor.property("value", "");

    public void setValue(String value) {
        set(this.value, value); // Proper abstraction
    }
}
```

## Anti-Pattern 4: Inconsistent Property Naming

```java
// DON'T: Inconsistent naming
public class BadNamingComponent extends ElementComposite {
   // Inconsistent naming
    private final PropertyDescriptor<String> desc_Value =
        PropertyDescriptor.property("value", "");

    public String getValue() {
        return get(desc_Value);
    }

    public void changeValue(String val) { // Inconsistent method naming
        set(desc_Value, val);
    }
}

// DO: Consistent, clear naming
public class GoodNamingComponent extends ElementComposite {
    private final PropertyDescriptor<String> valueProp =
        PropertyDescriptor.property("value", "");

    public String getValue() {
        return get(valueProp);
    }

    public GoodNamingComponent setValue(String value) {
        set(valueProp, value);
        return this; // Fluent interface
    }
}
```

## Anti-Pattern 5: Missing Property Testing

```java
// DON'T: Skip property testing
public class UntestedComponent extends ElementComposite {
    private final PropertyDescriptor<String> value =
        PropertyDescriptor.property("value", "");
    // No tests for property behavior
}

// DO: Include comprehensive testing
@Test
void testProperties() {
    PropertyDescriptorTester.run(GoodComponent.class, new GoodComponent());
}
```

---

## Additional Anti-Patterns

### Anti-Pattern 6: Extending Framework Classes

```java
// WRONG: Never do this!
public class MyComponent extends Component {
    // This will cause errors and is not supported
    @Override
    protected void onCreate(Window window) {
        // Don't extend Component directly!
    }
}

// WRONG: Never do this!
public class MyButton extends Button {
    // Button is final - cannot be extended
}

// WRONG: Never do this!
public class MyTextField extends DwcComponent<MyTextField> {
    // DwcComponent is for framework internals only, runtime error will be thrown.
}
```

### Anti-Pattern 7: Improper Event Handling

```java
// DON'T: Generic event handling without payload
addEventListener("input", event -> {
    // No data available - must query component
    String value = getElement().getProperty("value"); // Round-trip!
});

// DO: Configure event payload
ElementEventOptions options = new ElementEventOptions();
options.addData("value", "event.target.value");
addEventListener("input", event -> {
    String value = (String) event.getData().get("value"); // From payload
}, options);
```

### Anti-Pattern 8: JavaScript Parameter Confusion

```java
// DON'T: Try to use parameters in executeJs
getElement().executeJs("console.log(arguments[0]);", "test"); // Won't work!

// DO: Define function first, call with parameters
getElement().executeJs("component.log = function(msg) { console.log(msg); };");
getElement().callJsFunction("log", "test"); // Correct!
```

## Key Takeaways

1. **Never extend Component or DwcComponent** - Always use composition
2. **Clean up resources** - Use onDidDestroy() for cleanup
3. **Use PropertyDescriptor** - Don't bypass the abstraction
4. **Be consistent** - Use clear, consistent naming patterns
5. **Test your properties** - Use PropertyDescriptorTester
6. **Configure event payload** - Don't rely on component queries
7. **Understand JavaScript execution** - Two different methods for different purposes

## Navigation

- [← Previous: Advanced Techniques](11-advanced-techniques.md)
- [Next: Examples & Best Practices →](13-examples-best-practices.md)
- [Back to Index](00-index.md)