# JavaScript Execution in webforJ

webforJ provides sophisticated JavaScript execution capabilities that enable seamless integration between Java server-side logic and client-side JavaScript functionality. This system allows for direct method calls, property manipulation, and complex interactive behaviors while maintaining type safety.

## Understanding JavaScript Execution Context

webforJ JavaScript execution operates within specific contexts that determine scope and available references:

### The `component` Keyword

The most important aspect of webforJ JavaScript execution is the `component` keyword, which provides access to the client-side element instance:

```java
public class ElementJavaScriptContext extends ElementComposite {
    
    /**
     * The 'component' keyword refers to the client-side element
     */
    public void demonstrateComponentContext() {
        getElement().executeJs("""
            // 'component' references the client-side element instance
            component.style.backgroundColor = 'lightblue';
            component.setAttribute('data-processed', 'true');
            
            // Access element properties directly
            console.log('Element tag name:', component.tagName);
            console.log('Element ID:', component.id);
            
            // Call native DOM methods
            component.focus();
            component.scrollIntoView();
            """);
    }

    /**
     * Component context in property manipulation
     */
    public void manipulateProperties() {
        getElement().executeJs("""
            // Set custom properties on the component
            component.customData = {
                initialized: true,
                timestamp: Date.now()
            };
            
            // Create dynamic methods on the component
            component.getValue = function() {
                return component.getAttribute('value') || '';
            };
            
            component.setValue = function(value) {
                component.setAttribute('value', value);
                component.dispatchEvent(new CustomEvent('value-changed', {
                    detail: { value: value }
                }));
            };
            """);
    }
}
```

## JavaScript Execution Methods

### Element-Level Synchronous Execution

Synchronous methods block the executing thread and return results immediately:

```java
public class SynchronousJavaScriptExecution extends ElementComposite {
    
    /**
     * Execute JavaScript synchronously with return value
     * executeJs() only takes a script - no parameter injection
     */
    public Map<String, Object> getElementInfo() {
        Object result = getElement().executeJs("""
            return {
                tagName: component.tagName,
                id: component.id,
                classes: Array.from(component.classList),
                boundingRect: component.getBoundingClientRect(),
                computedStyle: {
                    width: getComputedStyle(component).width,
                    height: getComputedStyle(component).height
                }
            };
            """);
        
        return (Map<String, Object>) result;
    }

    /**
     * Call a JavaScript function synchronously with parameters
     * Parameters are passed to the named function, not available in script
     */
    public boolean validateInput(String input) {
        // First define the validation function on the component
        getElement().executeJs("""
            component.validateInput = function(inputValue) {
                return inputValue != null && inputValue.length >= 3;
            };
            """);
        
        // Then call it with parameters
        Object result = getElement().callJsFunction("validateInput", input);
        return Boolean.TRUE.equals(result);
    }

    /**
     * Call function with multiple parameters
     */
    public int performCalculation(int a, int b, String operation) {
        // Define the calculation function
        getElement().executeJs("""
            component.calculate = function(num1, num2, op) {
                switch(op) {
                    case 'add': return num1 + num2;
                    case 'multiply': return num1 * num2;
                    case 'subtract': return num1 - num2;
                    default: return 0;
                }
            };
            """);
        
        // Call with parameters
        Object result = getElement().callJsFunction("calculate", a, b, operation);
        return result instanceof Number ? ((Number) result).intValue() : 0;
    }
}
```

### Element-Level Asynchronous Execution

Asynchronous methods return `PendingResult` objects and don't block execution:

```java
public class AsynchronousJavaScriptExecution extends ElementComposite {
    
    /**
     * Execute JavaScript asynchronously with result handling
     */
    public void loadDataAsync(String url) {
        PendingResult<Object> result = getElement().executeJsAsync("""
            return fetch(arguments[0])
                .then(response => response.json())
                .then(data => {
                    component.dataset.loaded = 'true';
                    return data;
                });
            """, url);
        
        result.then(data -> {
            System.out.println("Data loaded: " + data);
            handleDataLoaded(data);
        }).exceptionally(error -> {
            System.err.println("Failed to load data: " + error.getMessage());
            return null;
        });
    }

    /**
     * Asynchronous function calling with void return
     */
    public void setupAsyncBehavior() {
        // executeJsVoidAsync for operations without return values
        getElement().executeJsVoidAsync("""
            // Setup async event listeners
            component.addEventListener('click', async (event) => {
                component.classList.add('loading');
                
                try {
                    const result = await component.processClick(event);
                    component.classList.add('success');
                } catch (error) {
                    component.classList.add('error');
                } finally {
                    component.classList.remove('loading');
                }
            });
            """);
    }

    /**
     * Call async JavaScript function and handle completion
     */
    public void triggerAsyncAction() {
        PendingResult<Object> result = getElement().callJsFunctionAsync("performAsyncAction");
        
        result.then(data -> {
            System.out.println("Async action completed: " + data);
        });
    }

    private void handleDataLoaded(Object data) {
        // Process loaded data
    }
}
```

### Page-Level JavaScript Execution

Execute JavaScript at the document/page level for global operations:

```java
public class PageLevelJavaScriptExecution {
    
    /**
     * Execute global page-level JavaScript
     */
    public void setupGlobalConfiguration() {
        Page page = Page.getCurrent();
        
        // Synchronous page execution
        page.executeJs("""
            // Setup global application configuration
            window.appConfig = {
                apiUrl: '/api/v1',
                theme: 'default',
                locale: 'en-US',
                features: {
                    darkMode: true,
                    notifications: true
                }
            };
            
            // Global utility functions
            window.utils = {
                formatCurrency: (amount) => new Intl.NumberFormat('en-US', {
                    style: 'currency',
                    currency: 'USD'
                }).format(amount),
                
                debounce: (func, wait) => {
                    let timeout;
                    return function(...args) {
                        clearTimeout(timeout);
                        timeout = setTimeout(() => func.apply(this, args), wait);
                    };
                }
            };
            """);
    }

    /**
     * Asynchronous page-level JavaScript execution
     */
    public void loadGlobalResources() {
        Page page = Page.getCurrent();
        
        page.executeJsVoidAsync("""
            // Dynamically load external libraries
            async function loadLibrary(src) {
                return new Promise((resolve, reject) => {
                    const script = document.createElement('script');
                    script.src = src;
                    script.onload = resolve;
                    script.onerror = reject;
                    document.head.appendChild(script);
                });
            }
            
            try {
                await loadLibrary('https://cdn.jsdelivr.net/npm/lodash@4.17.21/lodash.min.js');
                console.log('Lodash loaded successfully');
                
                // Setup global event handling
                document.addEventListener('keydown', (event) => {
                    if (event.ctrlKey && event.key === 'k') {
                        event.preventDefault();
                        // Trigger global search
                    }
                });
            } catch (error) {
                console.error('Failed to load external libraries:', error);
            }
            """);
    }

    /**
     * Page-level event registration
     */
    public void setupPageEventHandling() {
        Page page = Page.getCurrent();
        
        // Register page-wide click events
        PageEventOptions eventOptions = new PageEventOptions();
        eventOptions.addData("clientX", "event.clientX");
        eventOptions.addData("clientY", "event.clientY");
        eventOptions.addData("target", "event.target.tagName");
        
        page.addEventListener("click", event -> {
            int x = (Integer) event.getData().get("clientX");
            int y = (Integer) event.getData().get("clientY");
            String target = (String) event.getData().get("target");
            
            System.out.println("Page clicked at (" + x + ", " + y + ") on " + target);
        }, eventOptions);
    }
}
```

## Parameter Passing and Serialization

### How Parameters Work in webforJ

webforJ has two distinct methods for JavaScript execution:

1. **`executeJs(String script)`** - Executes script directly, no parameter injection
2. **`callJsFunction(String functionName, Object... arguments)`** - Calls named functions with parameters

```java
public class ParameterPassingExample extends ElementComposite {
    
    @Override
    protected void onDidCreate() {
        setupJavaScriptFunctions();
    }
    
    /**
     * Define JavaScript functions that will receive parameters
     */
    private void setupJavaScriptFunctions() {
        getElement().executeJs("""
            // Define functions that will receive parameters from Java
            component.handlePrimitive = function(str, num, bool, decimal) {
                console.log('String:', str, 'Number:', num, 'Boolean:', bool, 'Decimal:', decimal);
                return 'Processed: ' + str;
            };
            
            component.handleArray = function(array) {
                console.log('Received array:', array);
                return array.length;
            };
            
            component.configure = function(config) {
                console.log('Configuration:', config);
                component.dataset.title = config.title;
                component.dataset.timeout = config.timeout;
                return config.enabled;
            };
            
            component.setUser = function(user) {
                console.log('User data:', user);
                component.dataset.username = user.username;
                component.dataset.email = user.email;
                return 'User ' + user.username + ' configured';
            };
            
            component.interactWithComponent = function(otherComponent) {
                // otherComponent is the actual client-side component reference
                console.log('Other component:', otherComponent);
                otherComponent.classList.add('interacted');
                return otherComponent.tagName;
            };
            """);
    }
    
    /**
     * Call functions with different parameter types
     */
    public void demonstrateParameterTypes() {
        // Primitive types are serialized and passed to the function
        Object result1 = getElement().callJsFunction("handlePrimitive", "string", 42, true, 3.14);
        System.out.println("Result: " + result1);
        
        // Collections are serialized as arrays
        List<String> stringList = Arrays.asList("apple", "banana", "cherry");
        Object result2 = getElement().callJsFunction("handleArray", stringList);
        System.out.println("Array length: " + result2);
        
        // Maps become JavaScript objects
        Map<String, Object> config = Map.of(
            "title", "Configuration",
            "timeout", 5000,
            "enabled", true,
            "options", Arrays.asList("opt1", "opt2", "opt3")
        );
        Object result3 = getElement().callJsFunction("configure", config);
        System.out.println("Config enabled: " + result3);
        
        // Custom objects are JSON-serialized
        UserProfile user = new UserProfile("john_doe", "john@example.com", 25);
        Object result4 = getElement().callJsFunction("setUser", user);
        System.out.println("User result: " + result4);
    }

    /**
     * Special parameter types: 'this' and Component references
     */
    public void demonstrateSpecialParameters() {
        Button otherButton = new Button("Other Button");
        
        // Component parameters are converted to client-side references
        Object result = getElement().callJsFunction("interactWithComponent", otherButton);
        System.out.println("Other component tag: " + result);
    }

    /**
     * Direct script execution (no parameters)
     */
    public void executeDirectScript() {
        getElement().executeJs("""
            // Direct JavaScript execution without parameters
            component.dataset.timestamp = Date.now();
            component.setAttribute('data-processed', 'true');
            
            // Access component properties directly
            const currentValue = component.value || '';
            console.log('Current value:', currentValue);
            
            // No parameter access via arguments[] - this is for callJsFunction only
            """);
    }

    static class UserProfile {
        private final String username;
        private final String email;
        private final int age;
        
        public UserProfile(String username, String email, int age) {
            this.username = username;
            this.email = email;
            this.age = age;
        }
        
        // Getters for JSON serialization
        public String getUsername() { return username; }
        public String getEmail() { return email; }
        public int getAge() { return age; }
    }
}
```

## Component Lifecycle and DOM Integration

### Timing and Attachment Considerations

webforJ JavaScript execution has specific timing requirements:

```java
public class LifecycleAwareJavaScriptExecution extends ElementComposite {
    
    @Override
    protected void onDidCreate() {
        // JavaScript execution after DOM attachment
        setupInitialBehavior();
    }

    /**
     * JavaScript execution waits for DOM attachment
     */
    private void setupInitialBehavior() {
        // This will wait until element is in DOM
        getElement().executeJs("""
            // Element is guaranteed to be in DOM here
            component.style.opacity = '0';
            component.style.transition = 'opacity 0.3s ease';
            
            // Animate in
            requestAnimationFrame(() => {
                component.style.opacity = '1';
            });
            """);
    }

    /**
     * Using whenAttached for explicit timing control
     */
    public void setupAsyncAttachment() {
        PendingResult<Component> attachmentResult = whenAttached();
        
        attachmentResult.thenAccept(component -> {
            // Execute JavaScript after confirmed attachment
            getElement().executeJsVoidAsync("""
                // Component is definitely attached and rendered
                const rect = component.getBoundingClientRect();
                console.log('Component dimensions:', rect.width, 'x', rect.height);
                
                // Setup observers
                const observer = new ResizeObserver(entries => {
                    for (let entry of entries) {
                        component.dataset.width = entry.contentRect.width;
                        component.dataset.height = entry.contentRect.height;
                    }
                });
                observer.observe(component);
                """);
        });
    }

    /**
     * Handling component arguments in async calls
     */
    public void demonstrateComponentArguments(Component targetComponent) {
        // callJsFunction() won't wait for component arguments to attach
        // This might fail if targetComponent isn't attached
        try {
            getElement().callJsFunction("immediateInteraction", targetComponent);
        } catch (Exception e) {
            System.err.println("Immediate call failed: " + e.getMessage());
        }
        
        // callJsFunctionAsync() will wait for component arguments
        PendingResult<Object> result = getElement().callJsFunctionAsync("safeInteraction", targetComponent);
        result.then(data -> {
            System.out.println("Safe interaction completed: " + data);
        });
    }
}
```

## Event-Driven Integration Patterns

### Bidirectional Communication

Real event-driven communication between Java and JavaScript:

```java
public class EventDrivenIntegration extends ElementComposite {
    
    @Override
    protected void onDidCreate() {
        setupJavaScriptEventBridge();
        registerJavaEventHandlers();
    }

    /**
     * Setup JavaScript-to-Java communication bridge
     */
    private void setupJavaScriptEventBridge() {
        getElement().executeJs("""
            // Create communication bridge
            component.sendToJava = function(eventType, payload) {
                const customEvent = new CustomEvent('java-bridge', {
                    detail: {
                        type: eventType,
                        payload: payload,
                        timestamp: Date.now()
                    }
                });
                component.dispatchEvent(customEvent);
            };
            
            // Setup real-time validation
            component.addEventListener('input', (event) => {
                const value = event.target.value;
                const isValid = value.length >= 3; // Simple validation
                
                component.sendToJava('validation', {
                    value: value,
                    isValid: isValid,
                    field: event.target.name
                });
            });
            
            // Setup user interaction tracking
            component.addEventListener('click', (event) => {
                component.sendToJava('interaction', {
                    type: 'click',
                    target: event.target.tagName,
                    coordinates: { x: event.clientX, y: event.clientY }
                });
            });
            """);
    }

    /**
     * Register Java event handlers with proper ElementEventOptions configuration
     */
    private void registerJavaEventHandlers() {
        // Configure event options to capture the detail payload
        ElementEventOptions bridgeOptions = new ElementEventOptions();
        bridgeOptions.addData("detail", "event.detail");
        
        // Listen for custom bridge events with configured payload
        addEventListener("java-bridge", event -> {
            Map<String, Object> detail = (Map<String, Object>) event.getData().get("detail");
            String eventType = (String) detail.get("type");
            Map<String, Object> payload = (Map<String, Object>) detail.get("payload");
            
            switch (eventType) {
                case "validation" -> handleValidation(payload);
                case "interaction" -> handleUserInteraction(payload);
                default -> System.out.println("Unknown event type: " + eventType);
            }
        }, bridgeOptions);
    }

    private void handleValidation(Map<String, Object> payload) {
        String value = (String) payload.get("value");
        Boolean isValid = (Boolean) payload.get("isValid");
        String field = (String) payload.get("field");
        
        System.out.println("Validation for " + field + ": " + value + " -> " + isValid);
        
        // Update UI based on validation
        if (Boolean.FALSE.equals(isValid)) {
            addClassName("invalid");
        } else {
            removeClassName("invalid");
        }
    }

    private void handleUserInteraction(Map<String, Object> payload) {
        String type = (String) payload.get("type");
        String target = (String) payload.get("target");
        Map<String, Object> coordinates = (Map<String, Object>) payload.get("coordinates");
        
        System.out.println("User " + type + " on " + target + 
                         " at (" + coordinates.get("x") + ", " + coordinates.get("y") + ")");
    }
}
```

## Performance and Best Practices

### Optimized JavaScript Execution

```java
public class OptimizedJavaScriptExecution extends ElementComposite {
    
    private boolean initialized = false;
    
    /**
     * Lazy initialization pattern
     */
    public void ensureInitialized() {
        if (!initialized) {
            getElement().executeJs("""
                // One-time setup
                component._webforjInitialized = true;
                component._cache = new Map();
                
                // Reusable utility functions
                component.utils = {
                    memoize: function(fn) {
                        const cache = new Map();
                        return function(...args) {
                            const key = JSON.stringify(args);
                            if (cache.has(key)) {
                                return cache.get(key);
                            }
                            const result = fn.apply(this, args);
                            cache.set(key, result);
                            return result;
                        };
                    },
                    
                    throttle: function(fn, delay) {
                        let lastCall = 0;
                        return function(...args) {
                            const now = Date.now();
                            if (now - lastCall >= delay) {
                                lastCall = now;
                                return fn.apply(this, args);
                            }
                        };
                    }
                };
                """);
            initialized = true;
        }
    }

    /**
     * Batch operations for efficiency
     */
    public void batchOperations(List<String> operations) {
        StringBuilder script = new StringBuilder();
        script.append("const results = [];\n");
        
        for (int i = 0; i < operations.size(); i++) {
            script.append("results.push(component.processOperation(arguments[")
                  .append(i).append("]));\n");
        }
        
        script.append("return results;");
        
        Object results = getElement().executeJs(script.toString(), operations.toArray());
        processResults(results);
    }

    /**
     * Use async for non-blocking operations
     */
    public void performNonBlockingOperation(String data) {
        getElement().executeJsVoidAsync("""
            // Non-blocking operation
            setTimeout(() => {
                component.processData(arguments[0]);
            }, 0);
            """, data);
    }

    private void processResults(Object results) {
        // Process batch results
    }
}
```

## Best Practices Summary

1. **Understand the two execution methods**:
   - `executeJs(String script)` - Direct script execution, no parameters
   - `callJsFunction(String functionName, Object... arguments)` - Function calls with parameters

2. **Use the `component` keyword** - Always reference the element via `component` in JavaScript

3. **Define functions first, call them second**:
   ```java
   // First: Define the function
   getElement().executeJs("component.myFunc = function(param) { ... };");
   // Then: Call it with parameters
   getElement().callJsFunction("myFunc", myParameter);
   ```

4. **Prefer async methods** - Use `executeJsAsync` and `callJsFunctionAsync` for non-blocking operations

5. **Handle timing correctly** - Understand DOM attachment requirements:
   - `callJsFunction()` - Doesn't wait for component arguments to attach
   - `callJsFunctionAsync()` - Waits for all component arguments to attach

6. **Parameter serialization rules**:
   - Primitive types: passed directly
   - Collections: serialized as JSON arrays  
   - Maps: become JavaScript objects
   - Component instances: replaced with client-side references
   - `this` keyword: replaced with component reference (special handling)

7. **Error handling**:
   - Use try-catch in JavaScript for function definitions
   - Handle PendingResult exceptions for async calls
   - Functions return `null` if component not attached or function doesn't exist

8. **Performance optimization**:
   - Cache function definitions - define once, call multiple times
   - Use `executeJsVoidAsync` when no return value needed
   - Batch operations in single `executeJs` calls when possible

## Navigation

- [← Previous: Testing](09-testing.md)
- [Next: Advanced Techniques →](11-advanced-techniques.md)
- [Back to Index](00-index.md)