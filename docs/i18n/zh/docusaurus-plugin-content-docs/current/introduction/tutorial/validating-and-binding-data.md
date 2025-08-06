---
title: Validating and Binding Data
sidebar_position: 5
pagination_next: null
_i18n_hash: 3efedcc32a2111ba6ce08c1a3ee6b477
---
数据绑定是一种机制，它将应用程序的用户界面组件直接连接到底层数据模型，从而实现两者之间值的自动同步。这消除了重复调用获取器和设置器的需要，减少了开发时间并提高了代码的可靠性。

在此上下文中，验证确保输入表单中的数据遵循预定义规则，比如不能为空或遵循特定格式。通过将数据绑定与验证相结合，您可以简化用户体验，同时在不进行大量手动检查的情况下保持数据完整性。

有关数据绑定的更多信息，请参考[这篇文章](../../data-binding/overview)。要运行应用程序：

- 转到 `4-validating-and-binding-data` 目录
- 运行 `mvn jetty:run` 命令

<div class="videos-container">
  <video controls>
    <source src="https://cdn.webforj.com/webforj-documentation/video/tutorials/validating-and-binding-data.mp4" type="video/mp4"/>
  </video>
</div>

### 绑定字段 {#binding-the-fields}

数据绑定设置始于为 `Customer` 模型初始化 `BindingContext`。`BindingContext` 将模型属性链接到表单字段，实现自动数据同步。这是在 `FormView` 构造函数中设置的。

```java title="FormView.java"
BindingContext<Customer> context;
context = BindingContext.of(this, Customer.class, true);
```

`BindingContext.of(this, Customer.class, true)` 为 `Customer` 类初始化绑定上下文。第三个参数 `true` 启用 [jakarta 验证](https://beanvalidation.org/)。

:::info
该实现使用自动绑定，如 [数据绑定文章](../../data-binding/automatic-binding) 中所述。如果数据模型 `Customer` 中的字段名称与 `FormView` 中相应字段的名称相同，则此方法有效。

如果字段名称不相同，您可以在表单中对要绑定的字段添加 `UseProperty` 注解，以便系统知道引用哪些数据字段。
:::

### 使用 `onDidEnter()` 进行数据绑定 {#data-binding-with-ondidenter}

`onDidEnter` 方法利用数据绑定设置简化表单字段的填充过程。现在数据与 `BindingContext` 自动同步，而不需要手动为每个字段设置值。

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

webforJ 的数据绑定系统中的 `context.read` 方法将用户界面组件的字段与数据模型中的值进行同步。在这种情况下，它被用于用现有模型的数据填充表单字段，确保用户界面反映数据的当前状态。

## 验证数据 {#validating-data}

验证确保输入表单中的数据遵循指定规则，提高数据质量并防止无效提交。随着数据绑定，验证不再需要手动实现，而是简单地配置，允许实时反馈用户输入。

### 定义验证规则 {#defining-validation-rules}

使用 [Jakarta](https://beanvalidation.org) 和正则表达式，您可以对字段强制一系列规则。常用的示例是确保字段不为空或为 null，或遵循某种模式。
通过在客户类中的注解，您可以给字段提供 jakarta 验证参数。

:::info
有关验证设置的更多详细信息，请参考[这里](../../data-binding/validation/jakarta-validation.md#installation)。
:::

```java
  @NotEmpty(message = "姓名不能为空")
  @Pattern(regexp = "[a-zA-Z]*", message = "无效字符")
  private String firstName = "";
```

然后，在 `onValidate` 方法中控制 `提交` 按钮的状态，基于表单字段的有效性。这确保只有有效数据才能被提交。

```java title="FormView.java"
context.onValidate(e -> submit.setEnabled(e.isValid()));
```

`e.isValid()` 在所有字段有效时返回 true，反之则返回 false。这意味着只要所有字段有效，`提交` 按钮就会被启用。否则，它保持关闭状态，防止提交，直到进行更正。

### 使用验证添加和编辑条目 {#adding-and-editing-entries-with-validation}

`submitCustomer()` 方法现在使用 `BindingContext` 验证数据，然后执行添加或编辑操作。这种方法消除了手动验证检查的需要，利用上下文的内置机制确保只有有效数据被处理。

- **添加模式**：如果未提供 `id`，表单处于添加模式。验证的数据被写入 `Customer` 模型，并通过 `Service.getCurrent().addCustomer(customer)` 添加到存储库。
- **编辑模式**：如果存在 `id`，该方法将检索相应的客户数据，用经过验证的输入更新它，并将更改提交到存储库。

调用 `context.write(customer)` 将返回一个 `ValidationResult` 实例。此类指示验证是否成功，并存储与此结果相关的任何消息。

这段代码确保所有更改都经过验证，并在添加新 `Customer` 或编辑现有 `Customer` 之前自动应用于模型。

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

通过完成这一步，应用程序现在支持数据绑定和验证，确保表单输入与模型同步，并遵循预定义规则。
