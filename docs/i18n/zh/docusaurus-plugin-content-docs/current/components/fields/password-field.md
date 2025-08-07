---
sidebar_position: 30
title: PasswordField
slug: passwordfield
description: A single-line input component for securely entering and masking password data.
sidebar_class_name: updated-content
_i18n_hash: 180bd1578c78bf1ee9e746d23f76ec96
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-field" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/field/PasswordField" top='true'/>

<ParentLink parent="Field" />

`PasswordField` 组件允许用户安全地输入密码。它作为单行文本编辑器显示，输入的文本被遮蔽，通常用符号如星号（"*"）或点（"•"）替代。确切的符号可能因浏览器和操作系统而异。

<ComponentDemo 
path='/webforj/passwordfield?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/passwordfield/PasswordFieldView.java'
/>

## 字段值 {#field-value}

`PasswordField` 组件将其值存储和检索为普通 `String`，类似于 `TextField`，但具有遮蔽的视觉渲染以隐藏字符。

您可以使用以下方式检索当前值：

```java
passwordField.getValue();
```

:::warning 敏感数据
虽然字段在视觉上掩盖了内容，但从 `getValue()` 返回的值仍然是一个普通字符串。处理敏感数据时，要注意这一点，并在存储之前进行加密或转换。
:::

要以编程方式设置或重置值：

```java
passwordField.setValue("MySecret123!");
```

如果用户未输入任何值且未设置默认值，则字段将返回一个空字符串（`""`）。

这种行为与原生 HTML `<input type="password">` 的行为相似，其中 `value` 属性保留当前输入。

## 用法 {#usages}

`PasswordField` 最适用于捕获或处理敏感信息的场景，例如密码或其他机密数据，这对您的应用程序至关重要。以下是一些使用 `PasswordField` 的示例：

1. **用户身份验证和注册**：在涉及用户身份验证或注册流程的应用程序中，密码字段至关重要，需要安全的密码输入。

2. **安全表单输入**：在设计需要输入敏感信息的表单时，例如信用卡详细信息或个人识别号码 (PIN)，使用 `PasswordField` 可确保此类数据的安全输入。

3. **帐户管理和个人资料设置**：在涉及帐户管理或个人资料设置的应用中，密码字段非常有价值，使用户能够安全地更改或更新他们的密码。

## 密码可见性 {#password-visibility}

用户可以通过单击显示图标来显示 `PasswordField` 的值。这使用户能够验证输入的内容或将信息复制到剪贴板。然而，对于高安全性环境，您可以使用 `setPasswordReveal()` 移除显示图标，防止用户查看该值。您可以使用 `isPasswordReveal()` 方法验证用户是否能够使用显示图标来显示值。

## 模式匹配 {#pattern-matching}

强烈建议使用 `setPattern()` 方法对 `PasswordField` 应用正则表达式模式。这使您能够强制执行字符规则和结构要求，迫使用户创建安全和合规的凭证。模式匹配在强密码规则执行时尤其有用，例如，要求同时包含大写字母、小写字母、数字和符号。

该模式必须遵循 [JavaScript 正则表达式](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_expressions) 的语法，由浏览器解释。内部使用 `u`（Unicode）标志以确保对所有 Unicode 代码点的验证。请勿在模式周围包含斜杠（`/`）。

在以下片段中，模式要求至少包含一个小写字母、一个大写字母、一个数字，并且长度至少为 8 个字符。

```java
passwordField.setPattern("(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}");
```

如果缺少模式或模式无效，将不会应用任何验证。

:::tip
使用 `setLabel()` 提供清晰的标签以描述密码字段的目的。为了帮助用户理解密码要求，使用 `setHelperText()` 直接在字段下方显示指导或规则。
:::

## 最小和最大长度 {#minimum-and-maximum-length}

您可以通过在 `PasswordField` 上使用 `setMinLength()` 和 `setMaxLength()` 控制密码输入的允许长度。

`setMinLength()` 方法定义用户在字段中输入以通过验证所需的最小字符数。此值必须为非负整数，如果设置了最大长度，则不应超过最大长度。

```java
passwordField.setMinLength(8); // 最小 8 个字符
```

如果用户输入的字符少于最小值，则输入将失败约束验证。此验证仅在用户修改字段值时应用。

`setMaxLength()` 方法设置字段中允许的最大字符数。该值必须大于或等于 0。如果未定义或设置为无效值，则该字段没有上限字符限制。

```java
passwordField.setMaxLength(20); // 最大 20 个字符
```

如果输入超过最大字符限制，则字段将失败约束验证。与最小长度一样，只有在用户更新字段值时，此规则才适用。

:::tip
同时使用 `setMinLength()` 和 `setMaxLength()` 创建有效的输入边界。有关更多参考，请参见 [HTML 长度约束文档](https://developer.mozilla.org/en-US/docs/Web/HTML/Element/input#minlength)。
:::

## 最佳实践 {#best-practices}

由于 `PasswordField` 组件通常与敏感信息相关，因此在使用 `PasswordField` 时请考虑以下最佳实践：

- **提供密码强度反馈**：结合密码强度指示器或反馈机制，以帮助用户创建强大和安全的密码。评估长度、复杂性及大写和小写字母、数字和特殊字符的混合等因素。

- **强制执行密码存储**：切勿以明文形式存储密码。相反，请实施适当的安全措施，以安全地处理和存储应用程序中的密码。使用行业标准加密算法处理密码和其他敏感数据。

- **密码确认**：当用户更改或创建密码时，包含额外的确认字段。这一措施有助于减少打字错误的可能性，并确保用户正确输入所需的密码。

- **允许密码重置**：如果您的应用程序涉及用户帐户，请提供用户重置密码的选项。这可以采用“忘记密码”功能，以启动密码恢复过程。

- **可访问性**：在设计时考虑可访问性，使 `PasswordField` 符合可访问性标准，例如提供正确的标签，并与辅助技术兼容。
