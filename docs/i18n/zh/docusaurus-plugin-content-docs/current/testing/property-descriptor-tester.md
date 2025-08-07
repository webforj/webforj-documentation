---
sidebar_position: 4
title: PropertyDescriptorTester
_i18n_hash: f4cd3a02cd03838a015f873a3e5143ef
---
<DocChip chip='since' label='23.06' />
<DocChip chip='experimental' />
<JavadocLink type="foundation" location="com/webforj/component/element/PropertyDescriptorTester" top='true'/>

`PropertyDescriptorTester` 在 webforJ 中简化了集成到您应用程序中的 **第三方网页组件** 的测试。它验证使用 [`PropertyDescriptor`](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/component/element/PropertyDescriptor.html) 定义的属性是否正确链接到其获取器和设置器方法，并确保默认行为保持一致。这个工具对于验证第三方组件暴露的属性的功能特别有用，而无需重复测试逻辑。

:::warning experimental feature
webforJ PropertyDescriptorTester 适配器目前是一个实验性功能。可能会随时引入重大更改。
:::

## 概述 {#overview}

在处理第三方网页组件时，确保属性按预期行为至关重要。`PropertyDescriptorTester` 通过验证属性来自动化此过程：
- 是否正确映射到其获取器和设置器方法。
- 是否保持预期的默认值和自定义行为。
- 避免常见的集成问题，例如属性名称不匹配或默认值不一致。

该工具支持注释以应对更复杂的用例，例如排除无关属性或定义自定义获取器和设置器方法，使其成为集成测试的多功能选项。

## `PropertyDescriptorTester` 如何工作 {#how-propertydescriptortester-works}

测试过程涉及几个自动化步骤：

1. **类扫描**：
   `PropertyDescriptorScanner` 在组件类中识别所有 `PropertyDescriptor` 字段，自动排除使用 `@PropertyExclude` 注释的字段。

2. **方法解析**：
   根据命名约定检测标准获取器和设置器方法（`get<PropertyName>`/`set<PropertyName>`）。对于非标准实现，像 `@PropertyMethods` 这样的注释指定自定义方法名称或目标类。

3. **验证**：
   使用设置器方法分配默认值，使用获取器检索，并进行比较以确保正确性。任何不匹配都会触发 `AssertionError`，突出显示具体问题。

4. **错误报告**：
   测试器提供有关任何验证失败的详细反馈，例如缺少方法、不一致的默认值或属性配置错误。

## 使用 `PropertyDescriptorTester` 编写测试 {#writing-tests-with-propertydescriptortester}

以下是一个演示 `AppLayout` 组件的基本属性验证的示例：

### 示例：基本验证 {#example-basic-validation}

```java title="MyComponent.java"
public class MyComponent extends ElementCompositeContainer {
  private final PropertyDescriptor<Boolean> drawerOpened =
      PropertyDescriptor.property("drawerOpened", false);
  private final PropertyDescriptor<String> headerTitle =
      PropertyDescriptor.property("headerTitle", "Default Title");

  // setters and getters
}
```

#### 测试用例 {#test-case}

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
      fail("PropertyDescriptor 测试失败: " + e.getMessage());
    }
  }
}
```

此测试自动验证：
- `drawerOpened` 是否具有有效的获取器和设置器方法。
- `headerTitle` 是否默认为 `"Default Title"`。

## 使用注释的高级用例 {#advanced-use-cases-with-annotations}

对于更复杂的场景，`PropertyDescriptorTester` 支持注释以自定义或排除测试中的属性。

### 使用 `@PropertyExclude` 排除属性 {#exclude-properties-with-propertyexclude}

排除依赖外部系统或与测试无关的属性。例如：

```java
@PropertyExclude
private final PropertyDescriptor<String> excludedProperty =
    PropertyDescriptor.property("excludedProperty", "Excluded");
```

### 使用 `@PropertyMethods` 自定义方法 {#customize-methods-with-propertymethods}

当默认命名约定不适用时，定义自定义获取器、设置器或目标类：

```java
@PropertyMethods(getter = "retrieveValue", setter = "updateValue", target = InnerClass.class)
private final PropertyDescriptor<String> customProperty =
    PropertyDescriptor.property("customProperty", "Default Value");
```
