---
sidebar_position: 4
title: Transformation
description: >-
  Convert between UI and model data types in webforJ bindings by implementing
  the Transformer interface and wiring it via useTransformer.
_i18n_hash: 7a8064dc7b603cd86ad965a41216c55c
---
数据转换在 UI 组件使用的数据类型与您数据模型中的数据类型之间进行转换。这确保在前端和后端之间移动数据时，数据类型是兼容的并且格式正确。

:::tip
当 bean 属性的数据类型与 UI 组件处理的数据类型不匹配时，变换器设置最好使用。如果您只是需要转换相同类型的数据，配置 [绑定的 getter 和 setter](bindings#binding-getters-and-setters) 是首选方法。
:::

## 配置变换器 {#configuring-transformers}

您可以直接在绑定中配置数据转换，这使您能够定义在数据绑定过程中数据应如何被转换。

您可以使用 `BindingBuilder` 上的 `useTransformer` 方法将变换器添加到绑定中。变换器必须实现 `Transformer` 接口，该接口要求定义数据流两个方向的方法：从模型到 UI 和从 UI 到模型。

```java
context.bind(salaryField, "salary")
  .useTransformer(new CurrencyTransformer())
  .add();
```

在上面的例子中，这段代码配置了一个 `CurrencyTransformer` 来处理模型数据类型（例如，BigDecimal）与 UI 表示（例如，格式化字符串）之间的转换。

:::info
每个绑定与单个变换器相关联。如果转换一个值需要多个步骤，建议为这些步骤实现您自己的变换器。
:::

## 实现变换器 {#implementing-a-transformer}

下面是实现一个简单变换器的示例，该变换器在 `LocalDate` 模型和 `String` UI 表示之间进行转换：

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

这个变换器处理日期字段，在 UI 中显示时格式化日期并将其解析回模型中。

### 在绑定中使用变换器 {#using-transformers-in-bindings}

一旦定义了变换器，您可以在应用程序的多个绑定中应用它。这种方法特别适用于需要在应用程序不同部分一致处理的标准数据格式。

```java
BindingContext<Employee> context = new BindingContext<>(Employee.class);
context.bind(startDateField, "startDate", String.class)
  .useTransformer(new DateTransformer())
  .add();
```

:::info 指定 Bean 属性类型

在 `bind` 方法中，将 bean 属性的类型作为第三个参数指定是必要的，当 UI 组件显示的数据类型与模型中使用的数据类型不匹配时。例如，如果组件将 `startDateField` 作为 Java `LocalDate` 处理，但在模型中存储为 `String`，则显式指定类型为 `String.class` 告诉绑定机制准确地处理和转换组件与 bean 之间使用的两个不同类型的数据。
:::

### 使用 `Transformer.of` 简化转换 {#simplifying-transforms-with-transformerof}

可以使用 `Transformer` 提供的 `Transformer.of` 方法简化这些转换的实现。此方法是语法糖，允许您编写一个处理转换的行内方法，而不需要传递一个实现 `Transformer` 接口的类。

在以下示例中，代码处理旅行应用中的复选框交互，用户可以选择额外服务，如汽车租赁。复选框状态的 `boolean` 需要转换为后端模型使用的字符串表示 `"yes"` 或 `"no"`。

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

### 动态变换器错误消息 <DocChip chip='since' label='25.12' /> {#dynamic-transformer-error-messages}

默认情况下，当转换失败时显示的错误消息是静态字符串。在支持多种语言的应用程序中，您可以传递一个 `Supplier<String>`，这样每次转换失败时消息都会被解析：

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

仅当转换抛出 `TransformationException` 时，供应商才会被调用。这意味着消息始终反映失败时的当前区域设置。

#### 了解区域设置的变换器 {#locale-aware-transformers}

对于需要内部访问当前区域设置的可重用变换器（例如，根据地区惯例格式化数字或日期），实现 `LocaleAware` 接口。当区域设置通过 `BindingContext.setLocale()` 更改时，上下文会自动将新区域设置传播到实现该接口的变换器。
