---
sidebar_position: 4
title: Transformation
_i18n_hash: fccb434a8897618a0197f9883cd94795
---
数据转换是一个关键功能，促进UI组件使用的数据类型与数据模型中使用的类型之间的无缝转换。该功能确保在应用程序的前端和后端之间移动数据时，数据类型兼容且格式正确。

:::tip
当bean属性的数据类型与UI组件处理的数据类型不匹配时，最好使用变换器设置。如果您只需要转换相同类型的数据，配置[绑定的getter和setter](bindings#binding-getters-and-setters)是首选方法。
:::

## 配置变换器 {#configuring-transformers}

您可以直接在绑定中配置数据转换，从而定义在数据绑定过程中数据应如何被转换。

您可以使用`BindingBuilder`上的`useTransformer`方法向绑定添加变换器。变换器必须实现`Transformer`接口，该接口要求定义数据流的两个方向的方法：从模型到UI和从UI到模型。

```java
context.bind(salaryField, "salary")
    .useTransformer(new CurrencyTransformer())
    .add();
```

在上面的示例中，代码配置了一个`CurrencyTransformer`来处理模型数据类型（例如，BigDecimal）与UI表示（例如，格式化字符串）之间的转换。

:::info
每个绑定与单个变换器相关联。如果转换一个值需要多个步骤，建议为这些步骤实现您自己的变换器。
:::

## 实现变换器 {#implementing-a-transformer}

以下是实现一个简单变换器的示例，该变换器在`LocalDate`模型和`String` UI表示之间进行转换：

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

这个变换器便于处理日期字段，确保日期在UI中正确格式化并能正确解析回模型中。

## 在绑定中使用变换器 {#using-transformers-in-bindings}

定义变换器后，您可以在应用中的多个绑定中应用它。这种方法特别适用于需要在应用不同部分一致处理的标准数据格式。

```java
BindingContext<Employee> context = new BindingContext<>(Employee.class);
context.bind(startDateField, "startDate", String.class)
    .useTransformer(new DateTransformer())
    .add();
```

:::info 指定Bean属性类型

在`bind`方法中，将bean属性的类型作为第三个参数进行指定是至关重要的，特别是在UI组件显示的数据类型与模型中使用的数据类型之间存在差异时。例如，如果组件将`startDateField`视为Java `LocalDate`，而在模型中储存为`String`，则明确将类型定义为`String.class`确保绑定机制准确地处理和转换这两种不同类型之间的数据。
:::

## 使用`Transformer.of`简化转换 {#simplifying-transforms-with-transformerof}

可以使用`Transformer`提供的`Transformer.of`方法来简化此类转换的实现。此方法是语法糖，允许您编写处理转换的内联方法，而不是传递实现了`Transformer`接口的类。

在以下示例中，代码处理旅行应用中的复选框交互，用户可以选择额外的服务，如汽车租赁。复选框状态`boolean`需要转变为后端模型使用的字符串表示`"yes"`或`"no"`。

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
