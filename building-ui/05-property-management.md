# Property Management with PropertyDescriptor

PropertyDescriptor is the type-safe mechanism for managing component state in webforJ.

## Property vs Attribute

Understanding the distinction is crucial:

```java
// PROPERTY - Internal component state (may not reflect to DOM)
private final PropertyDescriptor<String> internalState =
    PropertyDescriptor.property("internalState", "default");

// ATTRIBUTE - DOM-reflected value (appears as HTML attribute)
private final PropertyDescriptor<String> domAttribute =
    PropertyDescriptor.attribute("dom-attribute", "default");
```

## Type-Safe Properties

PropertyDescriptor supports various data types:

```java
public class TypedComponent extends ElementComposite {
    // String properties
    private final PropertyDescriptor<String> titleProp =
        PropertyDescriptor.property("title", "");

    // Numeric properties
    private final PropertyDescriptor<Integer> countProp =
        PropertyDescriptor.property("count", 0);
    private final PropertyDescriptor<Double> percentageProp =
        PropertyDescriptor.property("percentage", 0.0);

    // Boolean properties
    private final PropertyDescriptor<Boolean> enabledProp =
        PropertyDescriptor.property("enabled", true);

    // Complex types
    private final PropertyDescriptor<Map<String, Object>> optionProp =
        PropertyDescriptor.property("options", new HashMap<>());

    public TypedComponent setOptions(Map<String, Object> options) {
      set(optionProp, options);
      return this;
    }

    public Map<String, Object> getOptions() {
      return get(optionProp);
    }

    // Getters and setters with validation
    public TypedComponent setCount(Integer count) {
        if (count < 0) {
            throw new IllegalArgumentException("Count cannot be negative");
        }
        set(countProp, count);
        return this;
    }

    public Integer getCount() {
        return get(countProp);
    }

    public TypedComponent setEnabled(Boolean enabled) {
        set(enabledProp, enabled);
        return this;
    }

    // get prop from the client
    public Integer isEnabled() {
      // if a property can be changed in the client
      // and it should not be cached on the server
      // you got ask the getter to always fetch
      // the prop form the the client, by passing
      // the second param (true) to enable client
      // query and the third param to hint the
      // returned type from the client to convert
      return get(enabled, true, Boolean.class);
    }
}
```

## Property Validation Patterns

```java
public class ValidatedComponent extends ElementComposite {
    private final PropertyDescriptor<String> email =
        PropertyDescriptor.property("email", "");

    public ValidatedComponent setEmail(String email) {
        if (email != null && !isValidEmail(email)) {
            throw new IllegalArgumentException("Invalid email format");
        }
        set(this.email, email);
        return this;
    }

    private boolean isValidEmail(String email) {
        return email.contains("@") && email.contains(".");
    }
}
```

## Enum-Style Properties

```java
public class StyledComponent extends ElementComposite {
    public enum Shadow {
      @SerializedName("none")
      HIDDEN,

      @SerializedName("scroll")
      SCROLL,

      @SerializedName("always")
      ALWAYS;
    }

  private final PropertyDescriptor<Shadow> footerShadowProp =
      PropertyDescriptor.property("footerShadow", Shadow.HIDDEN);

  public AppLayout setFooterShadow(Shadow footerShadow) {
    set(footerShadowProp, footerShadow);
    return this;
  }

  public Shadow getFooterShadow() {
    return get(footerShadowProp);
  }
}
```

---

## Best Practices

1. **Use Properties for State**: Properties manage internal component state
2. **Use Attributes for DOM**: Attributes reflect to HTML attributes
3. **Always Validate**: Add validation in setters for data integrity
4. **Type Safety**: Use specific types instead of Object
5. **Enums for Options**: Use enums with @SerializedName for fixed choices
6. **Client Fetching**: Use get(prop, true, Type.class) for client-side state

## Common Patterns

### Caching vs Fresh Data

```java
// Cached server-side (fast, but might be stale)
public String getCachedValue() {
    return get(valueProp);
}

// Always fetch from client (slower, but always current)
public String getFreshValue() {
    return get(valueProp, true, String.class);
}
```

### Property Chaining

```java
public MyComponent configure() {
    return this
        .setTitle("Example")
        .setEnabled(true)
        .setCount(42);
}
```

## Navigation

- [← Previous: ElementComposite](04-element-composite.md)
- [Next: Event Handling →](06-event-handling.md)
- [Back to Index](00-index.md)