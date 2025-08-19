---
sidebar_position: 4
title: PropertyDescriptorTester
_i18n_hash: 9ec1cde5a7d91d75dfd454741a6e8ee3
---
<DocChip chip='since' label='23.06' />
<DocChip chip='experimental' />
<JavadocLink type="foundation" location="com/webforj/component/element/PropertyDescriptorTester" top='true'/>

`PropertyDescriptorTester` 在 webforJ 中简化了整合到您的应用程序中的 **第三方 web 组件** 的测试。它验证使用 [`PropertyDescriptor`](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/component/element/PropertyDescriptor.html) 定义的属性是否正确链接到其获取和设置方法，并确保默认行为一致处理。这个工具在确认由第三方组件暴露的属性功能时尤其有用，而不需要重复的测试逻辑。

:::warning experimental feature
webforJ PropertyDescriptorTester 适配器目前是一个实验性功能。可能随时会引入重大更改。
:::

## 概述 {#overview}

在使用第三方 web 组件时，确保属性按预期工作至关重要。`PropertyDescriptorTester` 通过验证属性来自动化此过程：
- 正确映射到其获取和设置方法。
- 保持预期的默认值和自定义行为。
- 避免常见的集成问题，例如属性名称不匹配或默认值不一致。

该工具支持更复杂用例的注解，例如排除不相关的属性或定义自定义的获取和设置方法，使其成为集成测试的多功能选项。

## `PropertyDescriptorTester` 的工作原理 {#how-propertydescriptortester-works}

测试过程涉及几个自动化步骤：

1. **类扫描**： 
   `PropertyDescriptorScanner` 确定组件类中的所有 `PropertyDescriptor` 字段，自动排除用 `@PropertyExclude` 注解的字段。

2. **方法解析**：
   基于命名约定（`get<PropertyName>`/`set<PropertyName>`）检测标准获取和设置方法。对于非标准实现，像 `@PropertyMethods` 这样的注解可以指定自定义方法名或目标类。

3. **验证**：
   使用设置方法分配默认值，使用获取方法检索，并进行比较以确保正确性。任何不匹配都会触发 `AssertionError`，突出显示具体问题。

4. **错误报告**：
   测试器提供有关任何验证失败的详细反馈，例如缺失方法、不一致的默认值或属性配置错误。

## 使用 `PropertyDescriptorTester` 编写测试 {#writing-tests-with-propertydescriptortester}

以下是演示 `AppLayout` 组件的基本属性验证的示例：

### 示例：基本验证 {#example-basic-validation}

```java title="MyComponent.java"
public class MyComponent extends ElementCompositeContainer {
  private final PropertyDescriptor<Boolean> drawerOpened =
      PropertyDescriptor.property("drawerOpened", false);
  private final PropertyDescriptor<String> headerTitle =
      PropertyDescriptor.property("headerTitle", "Default Title");

  // 设置和获取方法
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
- `drawerOpened` 具有有效的获取和设置方法。
- `headerTitle` 默认值为 `"Default Title"`。

## 使用注解的高级用例 {#advanced-use-cases-with-annotations}

对于更复杂的场景，`PropertyDescriptorTester` 支持注解以自定义或排除测试属性。

### 使用 `@PropertyExclude` 排除属性 {#exclude-properties-with-propertyexclude}

排除依赖外部系统或与测试无关的属性。例如：

```java
@PropertyExclude
private final PropertyDescriptor<String> excludedProperty =
    PropertyDescriptor.property("excludedProperty", "Excluded");
```

### 使用 `@PropertyMethods` 自定义方法 {#customize-methods-with-propertymethods}

在默认命名约定不适用时，定义自定义获取、设置或目标类：

```java
@PropertyMethods(getter = "retrieveValue", setter = "updateValue", target = InnerClass.class)
private final PropertyDescriptor<String> customProperty =
    PropertyDescriptor.property("customProperty", "Default Value");
```
