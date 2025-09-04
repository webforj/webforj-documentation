---
title: Validating and Binding Data
sidebar_position: 5
pagination_next: null
_i18n_hash: 11d03e09c4c37172713713649c920e9e
---
数据绑定是一种机制，它将应用程序的UI组件与底层数据模型直接连接起来，使两个之间的值能够自动同步。这消除了重复调用getter和setter的需要，减少了开发时间并提高了代码的可靠性。

在此上下文中，验证确保输入到表单中的数据遵循预定义规则，例如非空或者遵循特定格式。通过将数据绑定与验证结合，您可以简化用户体验，同时保持数据完整性，而无需编写大量的手动检查。

有关数据绑定的更多信息，请参考[这篇文章](../../data-binding/overview)。要运行应用程序：

- 进入`4-validating-and-binding-data`目录
- 运行`mvn jetty:run`命令

<div class="videos-container">
  <video controls>
    <source src="https://cdn.webforj.com/webforj-documentation/video/tutorials/validating-and-binding-data.mp4" type="video/mp4"/>
  </video>
</div>

### 绑定字段 {#binding-the-fields}

数据绑定设置从为`Customer`模型初始化一个`BindingContext`开始。`BindingContext`将模型属性链接到表单字段，启用自动数据同步。这是在`FormView`构造函数中设置的。

```java title="FormView.java"
BindingContext<Customer> context;
context = BindingContext.of(this, Customer.class, true);
```

`BindingContext.of(this, Customer.class, true)`为`Customer`类初始化绑定上下文。第三个参数`true`启用[jakarta验证](https://beanvalidation.org/)。

:::info
此实现使用自动绑定，如[数据绑定文章](../../data-binding/automatic-binding)中所述。如果数据模型`Customer`中的字段名称与`FormView`中对应字段名称相同，则此功能有效。

如果字段名称不相同，您可以在表单中添加`UseProperty`注释到您希望绑定的字段上，以便知道引用哪些数据字段。
:::

### 使用`onDidEnter()`进行数据绑定 {#data-binding-with-ondidenter}

`onDidEnter`方法利用数据绑定设置简化了填充表单字段的过程。现在，数据可以自动与`BindingContext`同步，而无需手动为每个字段设置值。

```java {7}
@Override
  public void onDidEnter(DidEnterEvent event, ParametersBag parameters) {
    parameters.get("id").ifPresent(id -> {
      customer = Service.getCurrent().getCustomerByKey(UUID.fromString(id));
      customerId = id;
    });
    context.read(customer);
  }
```

`context.read`方法在webforJ的数据绑定系统中将UI组件的字段与数据模型中的值同步。在这种情况下，它用于用来自现有模型的数据填充表单字段，确保UI反映数据的当前状态。

## 验证数据 {#validating-data}

验证确保输入到表单中的数据遵循指定规则，从而提高数据质量并防止无效提交。借助数据绑定，验证不再需要手动实施，而只需简单配置，即可实时反馈用户输入。

### 定义验证规则 {#defining-validation-rules}

使用[Jakarta](https://beanvalidation.org)和正则表达式，您可以对字段实施许多规则。常用的示例包括确保字段不为空或null，或遵循某种模式。
通过在客户类中的注释，您可以为字段提供jakarta验证参数。

:::info
有关验证设置的更多详细信息，请查看[这里](../../data-binding/validation/jakarta-validation.md#installation)。
:::

```java
  @NotEmpty(message = "姓名不能为空")
  @Pattern(regexp = "[a-zA-Z]*", message = "无效字符")
  private String firstName = "";
```

然后添加`onValidate`方法，根据表单字段的有效性控制`Submit`按钮的状态。这确保只有有效数据才能提交。

```java title="FormView.java"
context.onValidate(e -> submit.setEnabled(e.isValid()));
```

`e.isValid()`如果所有字段有效则返回true，反之返回false。这意味着只要所有字段有效，`Submit`按钮就会启用。否则，它保持关闭状态，直到进行更正为止。

### 添加和编辑条目时的验证 {#adding-and-editing-entries-with-validation}

`submitCustomer()`方法现在在执行添加或编辑操作之前使用`BindingContext`验证数据。这种方法消除了手动验证检查的需要，利用上下文的内置机制确保只有有效的数据被处理。

- **添加模式**：如果未提供`id`，则表单处于添加模式。经过验证的数据写入`Customer`模型并通过`Service.getCurrent().addCustomer(customer)`添加到存储库。
- **编辑模式**：如果提供了`id`，该方法检索相应的客户数据，在验证的输入下更新它，并将更改提交到存储库。

调用`context.write(customer)`将返回一个`ValidationResult`的实例。此类指示验证是否成功，并存储与该结果相关的任何消息。

此代码确保所有更改在添加新客户或编辑现有客户之前都经过验证并自动应用于模型。

```java title="FormView.java"
private void submitCustomer() {
  ValidationResult results = context.write(customer);
  if (results.isValid()) {
    if (customerId.isEmpty()) {
      Service.getCurrent().addCustomer(customer);
    }
    Router.getCurrent().navigate(DemoView.class);
  }
}
```

通过完成此步骤，应用程序现在支持数据绑定和验证，确保表单输入与模型同步并遵循预定义规则。
