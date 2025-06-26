# Testing Strategies

webforJ applications require comprehensive testing strategies that combine unit testing for component logic with end-to-end testing for user experience validation.

## Testing Philosophy

webforJ supports two primary testing approaches:
1. **Unit Testing** - Validates individual components and backend logic in isolation
2. **End-to-End (E2E) Testing** - Tests complete user journeys and system integration

## PropertyDescriptorTester: Automated Property Validation

The PropertyDescriptorTester is webforJ's most powerful testing tool for validating component properties:

```java
import com.webforj.component.element.PropertyDescriptorTester;

class MyComponentTest {
    private MyComponent component;

    @BeforeEach
    void setUp() {
        component = new MyComponent();
    }

    @Test
    void validateAllProperties() {
        assertDoesNotThrow(() -> {
            PropertyDescriptorTester.run(MyComponent.class, component);
        });
    }
}
```

### How PropertyDescriptorTester Works

The PropertyDescriptorTester automates property validation through:

1. **Class Scanning** - Identifies all PropertyDescriptor fields
2. **Method Resolution** - Detects getter/setter methods based on naming conventions
3. **Validation** - Tests default values and property synchronization
4. **Error Reporting** - Provides detailed feedback on validation failures

### Advanced PropertyDescriptorTester Usage

For complex scenarios, use annotations to customize testing:

```java
public class AdvancedComponent extends ElementComposite {
    // Exclude properties that depend on external systems
    @PropertyExclude
    private final PropertyDescriptor<String> externalProperty =
        PropertyDescriptor.property("externalProperty", "");

    // Custom getter/setter method names
    @PropertyMethods(getter = "retrieveTitle", setter = "updateTitle")
    private final PropertyDescriptor<String> title =
        PropertyDescriptor.property("title", "Default");

    // Custom target class for nested properties
    @PropertyMethods(target = InnerConfig.class)
    private final PropertyDescriptor<String> configValue =
        PropertyDescriptor.property("configValue", "");

    public String retrieveTitle() {
        return get(title);
    }

    public AdvancedComponent updateTitle(String title) {
        set(this.title, title);
        return this;
    }

    public static class InnerConfig {
        // Custom configuration class
    }
}
```

## Unit Testing Best Practices

### Property Validation Testing

```java
class ComponentPropertyTest {
    private QRCode qrCode;

    @BeforeEach
    void setUp() {
        qrCode = new QRCode();
    }

    @Test
    void testPropertyDefaults() {
        assertEquals("", qrCode.getValue());
        assertEquals(128, qrCode.getSize());
        assertEquals("#000000", qrCode.getColor().toString());
    }

    @Test
    void testFluentInterface() {
        QRCode result = qrCode.setValue("https://webforj.com")
                             .setSize(200)
                             .setColor("#0059B8");

        assertSame(qrCode, result, "Methods should return this for chaining");
        assertEquals("https://webforj.com", qrCode.getValue());
        assertEquals(200, qrCode.getSize());
        assertEquals("#0059B8", qrCode.getColor());
    }

    @Test
    void testPropertyValidation() {
        assertThrows(IllegalArgumentException.class, () -> {
            qrCode.setSize(-1);
        }, "Negative size should throw exception");

        assertThrows(IllegalArgumentException.class, () -> {
            qrCode.setColor(null);
        }, "Null color should throw exception");
    }

    @Test
    void testPropertyDescriptorIntegrity() {
        PropertyDescriptorTester.run(QRCode.class, qrCode);
    }
}
```

## Event Testing Patterns

### Component Event Testing

```java
class EventTestingPatterns {
    private AppLayout component;

    @BeforeEach
    void setUp() {
        component = new AppLayout();
    }

    @Test
    void shouldAddOpenListener() {
      component.onDrawerOpen(event -> {
      });

      List<EventListener<AppLayoutDrawerOpenEvent>> listeners =
          component.getEventListeners(AppLayoutDrawerOpenEvent.class);

      assertEquals(1, listeners.size());
      assertTrue(listeners.get(0) instanceof EventListener<AppLayoutDrawerOpenEvent>);
    }

    @Test
    void shouldAddCloseListener() {
      component.onDrawerClose(event -> {
      });

      List<EventListener<AppLayoutDrawerCloseEvent>> listeners =
          component.getEventListeners(AppLayoutDrawerCloseEvent.class);

      assertEquals(1, listeners.size());
      assertTrue(listeners.get(0) instanceof EventListener<AppLayoutDrawerCloseEvent>);
    }
}
```

## End-to-End Testing with Selenium

```java
class SeleniumE2ETest {
    private WebDriver driver;
    private static final String PORT = System.getProperty("server.port", "8080");

    @BeforeAll
    static void setupAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setup() {
        driver = new ChromeDriver();
        driver.get("http://localhost:" + PORT + "/");
        new WebDriverWait(driver, ofSeconds(30))
                .until(titleIs("webforJ Application"));
    }

    @AfterEach
    void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    void testButtonInteraction() {
        WebElement button = driver.findElement(By.tagName("dwc-button"));
        assertEquals("Say Hello", button.getText());
        
        button.click();
        
        // Verify result
        WebElement toast = new WebDriverWait(driver, ofSeconds(5))
                .until(ExpectedConditions.presenceOfElementLocated(
                    By.tagName("dwc-toast")));
        
        assertTrue(toast.getText().contains("Welcome"));
    }

    @Test
    void testFormSubmission() {
        WebElement input = driver.findElement(By.tagName("dwc-textfield"));
        WebElement button = driver.findElement(By.tagName("dwc-button"));
        
        input.sendKeys("Test Input");
        button.click();
        
        // Verify form processing
        WebElement result = driver.findElement(By.className("result"));
        assertEquals("Processed: Test Input", result.getText());
    }
}
```

## End-to-End Testing with Playwright

```java
class PlaywrightE2ETest {
    static Playwright playwright = Playwright.create();
    Browser browser;
    Page page;
    String port = System.getProperty("server.port", "8080");

    @BeforeEach
    void setUp() {
        browser = playwright.chromium().launch();
        page = browser.newPage();
        page.navigate("http://localhost:" + port + "/");
    }

    @AfterEach
    void tearDown() {
        if (browser != null) {
            browser.close();
        }
    }

    @Test
    void testComponentInteraction() {
        page.locator("dwc-textfield").fill("webforJ");
        page.getByText("Say Hello").click();
        
        assertThat(page.locator("dwc-toast").first())
            .containsText("Welcome to webforJ");
    }

    @Test
    void testDynamicComponentRendering() {
        // Test dynamic component creation
        page.getByText("Add Component").click();
        
        // Verify component appears
        assertThat(page.locator(".dynamic-component"))
            .isVisible();
        
        // Test component functionality
        page.locator(".dynamic-component dwc-button").click();
        assertThat(page.locator(".result"))
            .containsText("Dynamic component clicked");
    }
}
```

---

## Testing Best Practices

1. **Use PropertyDescriptorTester** - Automatically validates all component properties
2. **Test Fluent Interfaces** - Verify method chaining works correctly
3. **Validate Input Parameters** - Test edge cases and error conditions
4. **Test Event Handling** - Verify event listeners are registered correctly
5. **E2E for User Flows** - Test complete user journeys end-to-end
6. **Mock External Dependencies** - Isolate component logic from external systems

## Common Testing Patterns

### Component Lifecycle Testing

```java
@Test
void testComponentLifecycle() {
    MyComponent component = new MyComponent();
    
    // Test initial state
    assertFalse(component.isAttached());
    
    // Simulate attachment
    component.whenAttached().then(c -> {
        assertTrue(c.isAttached());
        // Test post-attachment behavior
    });
}
```

### Property Synchronization Testing

```java
@Test
void testPropertySync() {
    MyComponent component = new MyComponent();
    
    component.setValue("test");
    assertEquals("test", component.getValue());
    
    // Test property descriptor integrity
    PropertyDescriptorTester.run(MyComponent.class, component);
}
```

## Navigation

- [← Previous: Styling & CSS](08-styling-css.md)
- [Next: JavaScript Execution →](10-javascript-execution.md)
- [Back to Index](00-index.md)