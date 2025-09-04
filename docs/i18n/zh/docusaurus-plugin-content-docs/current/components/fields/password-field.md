---
sidebar_position: 30
title: PasswordField
slug: passwordfield
description: A single-line input component for securely entering and masking password data.
sidebar_class_name: updated-content
_i18n_hash: ca055ca343a756152533eb1ab3ec5c8c
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-field" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/field/PasswordField" top='true'/>

<ParentLink parent="Field" />

`PasswordField` 组件允许用户安全地输入密码。它以单行文本编辑器的形式显示，输入的文本被遮蔽，通常用符号如星号（“*”）或圆点（“•”）替代。具体的符号可能会根据浏览器和操作系统的不同而有所变化。

<ComponentDemo 
path='/webforj/passwordfield?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/passwordfield/PasswordFieldView.java'
/>

## 字段值 {#field-value}

`PasswordField` 组件将其值存储和检索为普通 `String`，类似于 `TextField`，但以遮蔽的视觉效果来隐藏字符。

您可以使用以下代码检索当前值：

```java
passwordField.getValue();
```

:::warning 敏感数据
尽管该字段在视觉上会掩盖内容，但 `getValue()` 返回的值仍然是普通字符串。在处理敏感数据时，请注意这一点，并在存储之前对其进行加密或转换。
:::

要以编程方式设置或重置值：

```java
passwordField.setValue("MySecret123!");
```

如果用户没有输入任何值且未设置默认值，则该字段将返回空字符串（`""`）。

这种行为模仿了原生 HTML `<input type="password">`，在其中，`value` 属性保存当前输入。

## 用法 {#usages}

`PasswordField` 最适用于捕获或处理敏感信息的场景，例如密码或其他机密数据。这是一些使用 `PasswordField` 的示例：

1. **用户身份验证和注册**：密码字段在涉及用户身份验证或注册流程的应用程序中至关重要，需要安全的密码输入。

2. **安全表单输入**：在设计需要输入敏感信息的表单时，例如信用卡详情或个人识别号码（PIN），使用 `PasswordField` 可以确保数据的输入安全。

3. **账户管理和个人资料设置**：在涉及账户管理或个人资料设置的应用程序中，密码字段非常有价值，允许用户安全地更改或更新密码。

## 密码可见性 {#password-visibility}

用户可以通过点击显示图标来显示 `PasswordField` 的值。这允许用户验证他们输入的内容，或将信息复制到剪贴板。然而，对于高安全性的环境，您可以使用 `setPasswordReveal()` 来移除显示图标并防止用户查看值。您可以使用 `isPasswordReveal()` 方法验证用户是否可以使用显示图标来查看值。

## 模式匹配 {#pattern-matching}

强烈建议使用 `setPattern()` 方法将正则表达式模式应用于 `PasswordField`。这使您能够强制实施字符规则和结构要求，迫使用户创建安全和合规的凭据。模式匹配在强制执行强密码规则时尤其有用，例如要求同时包含大写字母、小写字母、数字和符号的组合。

模式必须遵循 [JavaScript 正则表达式](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_expressions) 的语法，由浏览器进行解析。内部使用 `u`（Unicode）标志以确保验证所有 Unicode 代码点。请 **勿** 在模式前后包含斜杠（`/`）。

在以下代码段中，模式要求至少一个小写字母、一个大写字母、一个数字，以及最小长度为 8 个字符。

```java
passwordField.setPattern("(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}");
```

如果缺少模式或模式无效，则不应用任何验证。

:::tip
使用 `setLabel()` 提供一个清晰的标签，描述密码字段的用途。为了帮助用户理解密码要求，使用 `setHelperText()` 在字段下方直接显示指导或规则。
:::


## 最小和最大长度 {#minimum-and-maximum-length}

您可以通过在 `PasswordField` 上使用 `setMinLength()` 和 `setMaxLength()` 来控制密码输入的允许长度。

`setMinLength()` 方法定义用户必须在字段中输入的最小字符数以通过验证。此值必须是非负整数，并且不应超过设置的最大长度。

```java
passwordField.setMinLength(8); // 最少 8 个字符
```

如果用户输入的字符数少于最小值，则输入将失败验证约束。此验证仅在用户修改字段值时应用。

`setMaxLength()` 方法设置字段中允许的最大字符数。该值必须为 0 或更大。如果未定义或设置为无效值，则字段没有上限字符限制。

```java
passwordField.setMaxLength(20); // 最多 20 个字符
```

如果输入超过最大字符限制，则字段将失败验证约束。与最小值类似，此规则仅在用户更新字段值时应用。

:::tip
结合使用 `setMinLength()` 和 `setMaxLength()` 来创建有效的输入边界。有关更多参考，请参见 [HTML 长度约束文档](https://developer.mozilla.org/en-US/docs/Web/HTML/Element/input#minlength)。
:::


## 最佳实践 {#best-practices}

由于 `PasswordField` 组件通常与敏感信息相关，因此在使用 `PasswordField` 时请考虑以下最佳实践：

- **提供密码强度反馈**: 结合密码强度指示器或反馈机制，帮助用户创建强大且安全的密码。评估长度、复杂性以及大写字母和小写字母、数字和特殊字符的组合等因素。

- **强制密码存储**: 永远不要以明文形式存储密码。相反，实施适当的安全措施来安全地处理和存储应用程序中的密码。对密码和其他敏感数据使用行业标准的加密算法。

- **密码确认**：如果用户更改或创建密码，请包含一个额外的确认字段。这项措施有助于最小化输入错误的可能性，确保用户准确输入他们想要的密码。

- **允许密码重置**：如果您的应用涉及用户账户，请提供选项让用户重置密码。这可以是“忘记密码”功能，启动密码恢复流程。

- **可访问性**：以可访问性为中心设置 `PasswordField`，以满足可访问性标准，例如提供适当的标签，并与辅助技术兼容。
