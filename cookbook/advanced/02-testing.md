# Testing Patterns

## Q: How do I test custom components effectively?

**A:** Use `PropertyDescriptorTester` and standard testing patterns:

```java
import com.webforj.component.element.PropertyDescriptorTester;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserFormTest {
    private UserForm component;

    @BeforeEach
    void setUp() {
        component = new UserForm();
    }

    @Test
    void shouldInitializeWithDefaultValues() {
        assertNotNull(component.getNameField());
        assertNotNull(component.getEmailField());
        assertNotNull(component.getSaveButton());
        assertFalse(component.getSaveButton().isEnabled());
    }

    @Test
    void shouldEnableSaveButtonWhenFormIsValid() {
        component.getNameField().setValue("John Doe");
        component.getEmailField().setValue("john@example.com");
        
        // Trigger validation
        component.validateForm();
        
        assertTrue(component.getSaveButton().isEnabled());
    }
}

// Test ElementComposite properties
public class MySliderTest {
    private MySlider slider;

    @BeforeEach
    void setUp() {
        slider = new MySlider();
    }

    @Test
    void validateProperties() {
        try {
            PropertyDescriptorTester.run(MySlider.class, slider);
        } catch (Exception e) {
            fail("PropertyDescriptor test failed: " + e.getMessage());
        }
    }

    @Test
    void shouldSetAndGetValue() {
        slider.setValue(50.0);
        assertEquals(50.0, slider.getValue());
    }

    @Test
    void shouldRespectMinMaxConstraints() {
        slider.setMin(0.0).setMax(100.0);
        
        assertEquals(0.0, slider.getMin());
        assertEquals(100.0, slider.getMax());
    }
}
```

---

## Navigation

- [← Previous: Custom Components](01-custom-components.md)
- [Next: Performance →](03-performance.md)
- [Back to Cookbook Index](../00-index.md)