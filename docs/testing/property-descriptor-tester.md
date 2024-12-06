---
sidebar_position: 4
title: PropertyDescriptorTester
---

The `PropertyDescriptorTester` in webforJ is a tool designed to simplify testing for **third-party web components** integrated into your app. It ensures that properties defined with [`PropertyDescriptor`](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/component/element/PropertyDescriptor.html) are correctly linked to their getter and setter methods, and that default behaviors are consistent.

This is particularly useful when integrating third-party components that expose properties as part of their API, allowing you to validate their functionality without needing to write repetitive test logic.

:::warning
The webforJ PropertyDescriptorTester adapter is currently an experimental feature. Breaking changes may be introduced at any time.
:::

## Purpose

When working with third-party web components, you need to ensure that:
1. Properties exposed by the components work as expected.
2. Default values, custom methods, and attributes are properly handled.
3. Integration issues are detected early, such as incorrect property names or mismatched methods.

`PropertyDescriptorTester` automates these checks, ensuring that the properties of these components can be tested quickly and reliably.

## Key features of `PropertyDescriptorTester`

1. **Automated Validation**:
   - Sets default property values using setter methods.
   - Retrieves values using getter methods.
   - Compares results to ensure correctness.

2. **Support for Annotations**:
   - **`@PropertyExclude`**: Exclude properties that don't require testing.
   - **`@PropertyMethods`**: Handle custom getter/setter method names or target classes.

3. **Error Handling**:
   - Detects and reports mismatched getters and setters.
   - Flags inconsistencies in default property values.

## How it works

1. **Scan Class**:
   - Identifies all `PropertyDescriptor` fields in the component class using `PropertyDescriptorScanner`.
   - Skips fields annotated with `@PropertyExclude`.

2. **Resolve Methods**:
   - Determines getter and setter methods based on standard conventions (`get<PropertyName>`/`set<PropertyName>`).
   - Uses custom methods or target classes if specified via `@PropertyMethods`.

3. **Set and Get Validation**:
   - Calls the setter to assign the default value.
   - Calls the getter to retrieve the value.
   - Compares the retrieved value with the expected default.

4. **Error Reporting**:
   - If a property fails validation, an `AssertionError` is thrown, detailing the issue.

## Writing tests with `PropertyDescriptorTester`

### Example: Basic validation

```java
public class AppLayout {
  private final PropertyDescriptor<Boolean> drawerOpened =
      PropertyDescriptor.property("drawerOpened", false);
  private final PropertyDescriptor<String> headerTitle =
      PropertyDescriptor.property("headerTitle", "Default Title");

  // setters and getters
}
```

#### Test case:

```java
import com.webforj.component.element.PropertyDescriptorTester;
import org.junit.jupiter.api.Test;

class AppLayoutTest {

  AppLayout component = new AppLayout();

  @Test
  void validateProperties() {
      AppLayout component = new AppLayout();
      PropertyDescriptorTester.run(AppLayout.class, component);
  }
}
```

This automatically validates:
- The `drawerOpened` property has the correct getter and setter methods.
- The `headerTitle` property defaults to `"Default Title"`.

## Annotations for advanced use cases

### 1. `@PropertyExclude`

Exclude a property from validation when:
- It depends on external systems.
- It’s not relevant for the current test.

**Example**:
```java
@PropertyExclude
private final PropertyDescriptor<String> excludedProperty =
    PropertyDescriptor.property("excludedProperty", "Excluded");
```

### 2. `@PropertyMethods`

Customize the getter, setter, or target class for a property when:
- The method names don't follow the standard conventions.
- The methods are in a different class.

**Example**:
```java
@PropertyMethods(getter = "retrieveValue", setter = "updateValue", target = SomeClass.class)
private final PropertyDescriptor<String> customProperty =
    PropertyDescriptor.property("customProperty", "Default Value");
```