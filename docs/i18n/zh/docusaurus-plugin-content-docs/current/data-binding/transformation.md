---
sidebar_position: 4
title: Transformation
sidebar_class_name: updated-content
_i18n_hash: e03ca3208470e53be7128ffb972c2670
---
数据转换在 UI 组件使用的数据类型与数据模型中的数据类型之间进行转换。这可以确保在将数据在应用程序的前端和后端之间移动时，数据类型兼容且格式正确。

:::tip
当 bean 属性的数据类型与 UI 组件处理的数据类型不匹配时，转换器设置最为有效。如果您仅需要转换相同类型的数据，配置 [绑定的 getter 和 setter](bindings#binding-getters-and-setters) 是首选的方法。
:::

## 配置转换器 {#configuring-transformers}

您可以直接在绑定中配置数据转换，从而定义在数据绑定过程中数据应该如何转换。

您可以使用 `BindingBuilder` 上的 `useTransformer` 方法将转换器添加到绑定中。转换器必须实现 `Transformer` 接口，该接口要求定义数据流的两个方向的方法：从模型到 UI 和从 UI 到模型。

```java
context.bind(salaryField, "salary")
  .useTransformer(new CurrencyTransformer())
  .add();
```

在上面的示例中，代码配置了一个 `CurrencyTransformer` 来处理模型数据类型（例如，BigDecimal）和 UI 表示（例如，格式化字符串）之间的转换。

:::info
每个绑定与单个转换器相关联。如果转换值需要多个步骤，建议为这些步骤实现自己的转换器。
:::

## 实现转换器 {#implementing-a-transformer}

以下是实现一个简单转换器的示例，它在 `LocalDate` 模型和 `String` UI 表示之间进行转换：

```java
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.webforj.data.transformation.TransformationException;
import com.webforj.data.transformation.transformer.Transformer;

public class DateTransformer implements Transformer<LocalDate, String> {
  private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

  @Override
  public LocalDate transformToComponent(String modelValue) {
    try {
      return LocalDate.parse(modelValue, formatter);
    } catch (Exception e) {
      throw new TransformationException("无效的日期格式");
    }
  }

  @Override
  public String transformToModel(LocalDate componentValue) {
    try {
      return componentValue.format(formatter);
    } catch (Exception e) {
      throw new TransformationException("无效的日期格式");
    }
  }
}
```

此转换器处理日期字段，在 UI 中显示日期时进行格式化，并将其解析回模型中。

### 在绑定中使用转换器 {#using-transformers-in-bindings}

一旦您定义了一个转换器，就可以在应用程序中的多个绑定中应用它。这种方法对于需要在应用程序不同部分一致处理的标准数据格式特别有用。

```java
BindingContext<Employee> context = new BindingContext<>(Employee.class);
context.bind(startDateField, "startDate", String.class)
  .useTransformer(new DateTransformer())
  .add();
```

:::info 指定 Bean 属性类型

在 `bind` 方法中，当 UI 组件显示的数据类型与模型中使用的数据类型不一致时，作为第三个参数指定 bean 属性的类型是至关重要的。例如，如果组件将 `startDateField` 作为 Java `LocalDate` 处理，但在模型中存储为 `String`，则显式地将类型定义为 `String.class` 告诉绑定机制准确地处理并转换组件和 bean 之间使用提供的转换器和验证器的两种不同类型的数据。
:::

### 使用 `Transformer.of` 简化转换 {#simplifying-transforms-with-transformerof}

可以使用 `Transformer` 提供的 `Transformer.of` 方法简化这种转换的实现。该方法是语法糖，允许您编写一个在内联处理转换的方法，而不是传递一个实现 `Transformer` 接口的类。

在以下示例中，代码处理旅行应用程序中的复选框交互，用户可以选择附加服务，如汽车租赁。复选框状态 `boolean` 需要转换为后端模型使用的字符串表示 `"yes"` 或 `"no"`。

```java
CheckBox carRental = new CheckBox("汽车租赁");
BindingContext<Trip> context = new BindingContext<>(Trip.class, true);
context.bind(carRental, "carRental", String.class)
  .useTransformer(
      Transformer.of(
        // 将组件值转换为模型值
        bool -> Boolean.TRUE.equals(bool) ? "yes" : "no",
        // 将模型值转换为组件值
        str -> str.equals("yes")
      ),

      // 如果转换失败，显示以下消息
      "复选框必须被选中"
  )
  .add();
```

### 动态转换器错误消息 <DocChip chip='since' label='25.12' /> {#dynamic-transformer-error-messages}

默认情况下，当转换失败时显示的错误消息是静态字符串。在支持多种语言的应用程序中，您可以传递一个 `Supplier<String>`，以便在每次转换失败时解析该消息：

```java {7}
context.bind(quantityField, "quantity", Integer.class)
  .useTransformer(
    Transformer.of(
      str -> Integer.parseInt(str),
      val -> String.valueOf(val)
    ),
    () -> t("validation.quantity.invalid")
  )
  .add();
```

只有在转换抛出 `TransformationException` 时，才会调用供应商。这意味着消息始终反映失败时的当前区域设置。

#### 区域感知转换器 {#locale-aware-transformers}

对于需要内部访问当前区域设置的可重用转换器（例如，根据区域习惯格式化数字或日期），请实现 `LocaleAware` 接口。当通过 `BindingContext.setLocale()` 更改区域设置时，上下文会自动将新区域设置传播到实现此接口的转换器。
