---
sidebar_position: 4
title: Transformation
_i18n_hash: fe3acbd17750ab0092cbc3609b967969
---
数据转换是一个关键功能，使得 UI 组件中使用的数据类型与您数据模型中的数据类型之间的无缝转换成为可能。此功能确保在前端和后端之间移动数据时，数据类型兼容且格式正确。

:::tip
当 bean 属性的数据类型与 UI 组件处理的数据类型不匹配时，转换器设置的最佳使用场景。如果您只是需要转换相同类型的数据，则配置 [绑定的获取器和设置器](bindings#binding-getters-and-setters) 是首选方法。
:::

## 配置转换器 {#configuring-transformers}

您可以直接在绑定中配置数据转换，使您能够定义数据在数据绑定过程中的转换方式。

您可以使用 `BindingBuilder` 上的 `useTransformer` 方法为绑定添加转换器。转换器必须实现 `Transformer` 接口，该接口要求为数据流的两个方向（从模型到 UI 和从 UI 到模型）定义方法。

```java
context.bind(salaryField, "salary")
    .useTransformer(new CurrencyTransformer())
    .add();
```

在上面的示例中，代码配置了一个 `CurrencyTransformer` 来处理模型数据类型（例如，BigDecimal）与 UI 表示（例如，格式化字符串）之间的转换。

:::info
每个绑定关联一个转换器。如果转换值需要多个步骤，建议为这些步骤实现您自己的转换器。
:::

## 实现一个转换器 {#implementing-a-transformer}

以下是实现一个简单转换器的示例，该转换器在 `LocalDate` 模型与 `String` UI 表示之间进行转换：

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

此转换器便于处理日期字段，确保在 UI 中显示的日期格式正确，并能够正确解析回模型。

## 在绑定中使用转换器 {#using-transformers-in-bindings}

一旦您定义了转换器，您可以在应用程序内的多个绑定中应用它。这种方法对需要在应用程序不同部分进行一致处理的标准数据格式特别有用。

```java
BindingContext<Employee> context = new BindingContext<>(Employee.class);
context.bind(startDateField, "startDate", String.class)
    .useTransformer(new DateTransformer())
    .add();
```

:::info 指定 Bean 属性类型

在 `bind` 方法中，当 UI 组件显示的数据类型与模型中使用的数据类型存在差异时，必须将 bean 属性的类型作为第三个参数进行指定。例如，如果组件将 `startDateField` 视为一个 Java `LocalDate`，而在模型中存储为 `String`，则显式定义类型为 `String.class` 确保绑定机制能够准确处理并转换两种由组件和 bean 使用的不同数据类型之间的数据，使用提供的转换器和验证器。
:::

## 使用 `Transformer.of` 简化转换 {#simplifying-transforms-with-transformerof}

可以使用 `Transformer` 提供的 `Transformer.of` 方法来简化此类转换的实现。此方法是语法糖，允许您编写处理内联转换的方法，而不是传递实现了 `Transformer` 接口的类。

在下面的示例中，代码处理了一个旅行应用中的复选框交互，用户可以选择额外的服务，例如汽车租赁。复选框状态的 `boolean` 需要转换为后端模型使用的字符串表示 `"yes"` 或 `"no"`。

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
