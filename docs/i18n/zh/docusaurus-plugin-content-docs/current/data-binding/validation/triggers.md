---
sidebar_position: 5
title: Triggers
_i18n_hash: b158a924f67b7141be94d56b9be8bba3
---
默认情况下，绑定会在用户修改数据时自动重新验证组件，例如输入新文本、勾选复选框或在单选按钮中选择新选项。如果您希望关闭自动验证，并仅在写入数据模型时报告它们，您可以配置绑定以关闭它们。这使您可以控制何时以及如何进行验证，从而根据特定应用程序需求或用户交互来管理验证。

```java
BindingContext<User> context = new BindingContext<>(User.class);
context.bind(emailField, "email")
    .useValidator(
        Validator.from(new EmailValidator(), "无效电子邮件地址的自定义消息"))
    .autoValidate(false)
    .add();
```

还可以为整个上下文关闭自动验证。

```java
BindingContext<User> context = new BindingContext<>(User.class);
context.setAutoValidate(false);
```

:::tip 值更改模式
某些组件，如字段组件，实现了 `ValueChangeModeAware` 接口，该接口允许您控制系统何时报告 `ValueChangeEvent`。例如，您可以设置字段组件仅在失去焦点时报告值更改。这种配置减少了验证的频率，提高了性能，并通过将验证集中在用户完成输入会话时而不是在积极输入时来增强用户体验。

```java
 emailField.setValueChangeMode(ValueChangeMode.ON_BLUR);
```
:::

## 重新验证 {#revalidation}

虽然验证通常在数据写入时自动触发，但您也可以手动调用它们以验证数据的状态，而不尝试将其写入模型。这种手动方法在您希望根据表单数据的有效性启用或关闭功能而不进行更新的场景中特别有用。

考虑一个经典的旅行日期选择器示例，用户必须选择两个日期：旅行的开始日期和结束日期。选择的结束日期不能早于开始日期，或选择的开始日期不能晚于结束日期。您可以通过手动触发验证来解决这些依赖关系：

<Tabs>
<TabItem value="TripBooking" label="TripBooking.java">

```java showLineNumbers
public class TripBooking extends App {
  DateTimeField startDateField = new DateTimeField("开始日期");
  DateTimeField endDateField = new DateTimeField("结束日期");
  FlexLayout layout = FlexLayout.create(startDateField, endDateField).vertical().build().setStyle("margin", "20px auto")
      .setMaxWidth("400px");

  LocalDateTime startDate;
  LocalDateTime endDate;

  @Override
  public void run() throws WebforjException {
    BindingContext<Trip> context = new BindingContext<>(Trip.class);
    context.bind(startDateField, "startDate")
        .useValidator(Objects::nonNull, "开始日期是必填项")
        .useValidator(value -> endDate != null && value.isBefore(endDate),
            "开始日期必须早于结束日期")
        .add();

    context.bind(endDateField, "endDate")
        .useValidator(Objects::nonNull, "结束日期是必填项")
        .useValidator(value -> startDate != null && value.isAfter(startDate),
            "结束日期必须晚于开始日期")
        .add();

    startDateField.setValueChangeMode(ValueChangeMode.ON_BLUR);
    startDateField.addValueChangeListener(event -> {
      startDate = event.getValue();
      context.getBinding("endDate").validate();
    });

    endDateField.setValueChangeMode(ValueChangeMode.ON_BLUR);
    endDateField.addValueChangeListener(event -> {
      endDate = event.getValue();
      context.getBinding("startDate").validate();
    });

    Frame frame = new Frame();
    frame.add(layout);
  }
}
```

</TabItem>
<TabItem value="Trip" label="Trip.java">

```java showLineNumbers
public class Trip {
  private LocalDateTime startDate;
  private LocalDateTime endDate;

  public LocalDateTime getStartDate() {
    return startDate;
  }

  public void setStartDate(LocalDateTime startDate) {
    this.startDate = startDate;
  }

  public LocalDateTime getEndDate() {
    return endDate;
  }

  public void setEndDate(LocalDateTime endDate) {
    this.endDate = endDate;
  }
}
```

</TabItem>
</Tabs>
