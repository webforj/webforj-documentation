---
sidebar_position: 30
title: PasswordField
slug: passwordfield
description: A single-line input component for securely entering and masking password data.
_i18n_hash: b0641475acf187af7c45d6786506010d
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-field" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/field/PasswordField" top='true'/>

`PasswordField`组件允许用户安全地输入密码。它显示为单行文本编辑器，输入的文字被遮蔽，通常用星号（"*"）或点（"•"）等符号替代。具体符号可能因浏览器和操作系统而异。

<!-- INTRO_END -->

## 使用 `PasswordField` {#using-passwordfield}

<ParentLink parent="Field" />

`PasswordField`扩展了共享的`Field`类，提供了所有字段组件的共同特性。以下示例创建了一个带有标签和占位符文本的`PasswordField`。

<ComponentDemo
path='/webforj/passwordfield'
files={['src/main/java/com/webforj/samples/views/fields/passwordfield/PasswordFieldView.java']}
/>

## 字段值 {#field-value}

`PasswordField`组件将其值存储和检索为简单的`String`，类似于`TextField`，但通过视觉遮蔽的方式隐藏字符。

您可以使用以下方法检索当前值：

```java
passwordField.getValue();
```

:::warning 敏感数据
尽管字段在视觉上掩盖了内容，但从`getValue()`返回的值仍然是一个普通字符串。在处理敏感数据时，请注意这一点，并在存储之前进行加密或转换。
:::

要以编程方式设置或重置值：

```java
passwordField.setValue("MySecret123!");
```

如果用户没有输入任何值且没有设置默认值，则字段将返回空字符串（`""`）。

此行为模仿原生HTML `<input type="password">`，其中`value`属性保存当前输入。

## 用法 {#usages}

`PasswordField`最适合用于捕获或处理敏感信息的场景，例如密码或其他机密数据，以下是一些使用`PasswordField`的示例：

1. **用户认证和注册**：在涉及用户认证或注册流程的应用中，密码字段至关重要，要求安全的密码输入。

2. **安全表单输入**：在设计需要输入敏感信息（如信用卡详细信息或个人识别号码（PIN））的表单时，使用`PasswordField`可以确保数据输入的安全。

3. **账户管理和个人设置**：在涉及账户管理或个人设置的应用中，密码字段非常有价值，允许用户安全地更改或更新密码。

## 密码可见性 {#password-visibility}

用户可以通过点击显示图标来显示`PasswordField`的值。这允许用户验证他们输入的内容，或将信息复制到剪贴板。然而，对于高安全性环境，您可以使用`setPasswordReveal()`方法去除显示图标，防止用户查看该值。您可以使用`isPasswordReveal()`方法验证用户是否可以使用显示图标来显示值。

## 模式匹配 {#pattern-matching}

强烈建议使用`setPattern()`方法为`PasswordField`应用正则表达式模式。这允许您强制实施字符规则和结构要求，迫使用户创建安全和合规的凭证。模式匹配在强密码规则的执行方面特别有用，例如要求包含大写和小写字母、数字和符号的混合。

模式必须遵循[JavaScript正则表达式](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_expressions)的语法，由浏览器解释。内部使用`u`（Unicode）标志以确保对所有Unicode代码点的验证。**请勿**在模式周围包含斜杠（`/`）。

在以下代码片段中，模式要求至少包含一个小写字母、一个大写字母、一个数字和最小长度为8个字符。

```java
passwordField.setPattern("(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}");
```

如果缺少模式或模式无效，则不会应用验证。

:::tip
使用`setLabel()`提供描述密码字段用途的明确标签。为了帮助用户理解密码要求，可以使用`setHelperText()`在字段下方直接显示指导或规则。
:::

## 最小和最大长度 {#minimum-and-maximum-length}

您可以使用`setMinLength()`和`setMaxLength()`控制密码输入的允许长度。

`setMinLength()`方法定义用户必须在字段中输入的最小字符数，以通过验证。该值必须是非负整数，并且在设置最大长度时不得超过最大长度。

```java
passwordField.setMinLength(8); // 最少8个字符
```

如果用户输入的字符少于最小值，则输入将失败约束验证。此验证仅在用户修改字段值时应用。

`setMaxLength()`方法设置字段中允许的最大字符数。该值必须为0或更大。如果未定义或设置为无效值，则字段没有上限字符限制。

```java
passwordField.setMaxLength(20); // 最多20个字符
```

如果输入超过最大字符限制，则字段将失败约束验证。与最小长度验证一样，此规则仅在用户更新字段值时应用。

:::tip
结合使用`setMinLength()`和`setMaxLength()`来创建有效的输入边界。有关更多参考，请参见[HTML长度约束文档](https://developer.mozilla.org/en-US/docs/Web/HTML/Element/input#minlength)。
:::

## 最佳实践 {#best-practices}

由于`PasswordField`组件常常与敏感信息相关，请在使用`PasswordField`时考虑以下最佳实践：

- **提供密码强度反馈**：结合密码强度指示器或反馈机制，帮助用户创建强大且安全的密码。评估长度、复杂性以及大小写字母、数字和特殊字符的组合。

- **强制密码存储**：绝不要以明文形式存储密码。相反，实施适当的安全措施，安全地处理和存储密码。对密码和其他敏感数据使用行业标准的加密算法。

- **密码确认**：在用户更改或创建密码时，包含一个额外的确认字段。这一措施有助于减少输入错误的可能性，并确保用户准确输入他们所期望的密码。

- **允许密码重置**：如果您的应用涉及用户账户，请提供用户重置密码的选项。这可能以“忘记密码”功能的形式出现，以启动密码恢复过程。

- **可访问性**：在设计`PasswordField`时考虑可访问性，以确保符合可访问性标准，例如提供适当的标签，兼容辅助技术。
