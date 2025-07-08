---
sidebar_position: 4
title: PropertyDescriptorTester
---

<DocChip chip='since' label='23.06' />
<DocChip chip='experimental' />
<JavadocLink type="foundation" location="com/webforj/component/element/PropertyDescriptorTester" top='true'/>

The `PropertyDescriptorTester` in webforJ simplifies testing for **third-party web components** integrated into your app. It validates that properties defined with [`PropertyDescriptor`](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/component/element/PropertyDescriptor.html) are correctly linked to their getter and setter methods and ensures that default behaviors are handled consistently. This tool is especially useful for verifying the functionality of properties exposed by third-party components without requiring repetitive test logic.

:::warning experimental feature
The webforJ PropertyDescriptorTester adapter is currently an experimental feature. Breaking changes may be introduced at any time.
:::

## Overview

When working with third-party web components, ensuring that properties behave as expected is essential. The `PropertyDescriptorTester` automates this process by validating that properties:
- Are correctly mapped to their getter and setter methods.
- Maintain expected default values and custom behaviors.
- Avoid common integration issues, such as mismatched property names or inconsistent defaults.

The tool supports annotations for more complex use cases, such as excluding irrelevant properties or defining custom getter and setter methods, making it a versatile option for integration testing.

## How `PropertyDescriptorTester` works

The testing process involves several automated steps:

1. **Class Scanning**: 
   The `PropertyDescriptorScanner` identifies all `PropertyDescriptor` fields within a component class, automatically excluding fields annotated with `@PropertyExclude`.

2. **Method Resolution**:
   Standard getter and setter methods are detected based on naming conventions (`get<PropertyName>`/`set<PropertyName>`). For non-standard implementations, annotations like `@PropertyMethods` specify custom method names or target classes.

3. **Validation**:
   Default values are assigned using the setter method, retrieved using the getter, and compared to ensure correctness. Any mismatch triggers an `AssertionError`, highlighting the specific issue.

4. **Error Reporting**:
   The tester provides detailed feedback on any validation failures, such as missing methods, inconsistent defaults, or property misconfigurations.

## Writing tests with `PropertyDescriptorTester`

Here’s an example demonstrating basic property validation for an `AppLayout` component:

### Example: Basic validation

```java title="MyComponent.java"
public class MyComponent extends ElementCompositeContainer {
  private final PropertyDescriptor<Boolean> drawerOpened =
      PropertyDescriptor.property("drawerOpened", false);
  private final PropertyDescriptor<String> headerTitle =
      PropertyDescriptor.property("headerTitle", "Default Title");

  // setters and getters
}
```

#### Test case

```java title="MyComponentTest.java"
import com.webforj.component.element.PropertyDescriptorTester;
import org.junit.jupiter.api.Test;

class MyComponentTest {

  MyComponent component = new MyComponent();

  @Test
  void validateProperties() {
    try {
      PropertyDescriptorTester.run(MyComponent.class, component);
    } catch (Exception e) {
      fail("PropertyDescriptor test failed: " + e.getMessage());
    }
  }
}
```

This test automatically verifies:
- That `drawerOpened` has valid getter and setter methods.
- That `headerTitle` defaults to `"Default Title"`.

## Advanced use cases with annotations

For more complex scenarios, `PropertyDescriptorTester` supports annotations to customize or exclude properties from testing.

### Exclude properties with `@PropertyExclude`

Exclude properties that rely on external systems or aren't relevant to the test. For instance:

```java
@PropertyExclude
private final PropertyDescriptor<String> excludedProperty =
    PropertyDescriptor.property("excludedProperty", "Excluded");
```

### Customize methods with `@PropertyMethods`

Define custom getter, setter, or target class when the default naming conventions don’t apply:

```java
@PropertyMethods(getter = "retrieveValue", setter = "updateValue", target = InnerClass.class)
private final PropertyDescriptor<String> customProperty =
    PropertyDescriptor.property("customProperty", "Default Value");
```

<GiscusComments />