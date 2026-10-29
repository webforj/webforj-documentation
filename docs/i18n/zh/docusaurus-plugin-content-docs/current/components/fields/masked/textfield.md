---
title: MaskedTextField
sidebar_position: 15
description: >-
  Enforce formatted text entry with the MaskedTextField, supporting mask
  characters for digits, letters, and literals for IDs and codes.
_i18n_hash: 9ee372191dc17b2510f020bc908f0060
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-textfield" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/field/MaskedTextField" top='true'/>

`MaskedTextField` 组件提供了一个可配置的文本输入，强制执行格式规则和验证。它非常适合需要结构化输入的应用程序，例如金融、电商和医疗系统。

可以用或不用参数实例化此组件。您可以定义初始值、标签、占位符文本以及在值更改时的监听器。

<!-- INTRO_END -->

```java
MaskedTextField field = new MaskedTextField("账户 ID");
field.setMask("ZZZZ-0000")
  .setHelperText("掩码: ZZZZ-0000 - 例如: SAVE-2025");
```

## 掩码规则 {#mask-rules}

`MaskedTextField` 使用掩码格式化文本输入——一个定义每个位置允许使用字符的字符串。这确保了输入的一致性和结构化，如电话号码、邮政编码和身份证格式。

:::tip 程序化应用掩码
要在字段外使用相同的掩码语法格式化字符串，例如在 [`Table`](/docs/components/table/overview) 中渲染数据时，请使用 [`MaskDecorator`](/docs/advanced/mask-decorator) 工具类。
:::

### 支持的掩码字符 {#supported-mask-characters}

| 字符     | 描述                                                                                 |
|----------|---------------------------------------------------------------------------------------|
| `X`      | 任何可打印字符                                                                       |
| `a`      | 任何字母字符（大写或小写）                                                            |
| `A`      | 任何字母字符；小写字母将被转换为大写                                                  |
| `0`      | 任何数字（0–9）                                                                        |
| `z`      | 任何数字或字母（大写或小写）                                                          |
| `Z`      | 任何数字或字母；小写字母将被转换为大写                                                |

掩码中的所有其他字符都被视为字面量，必须完全输入。
例如，像 `XX@XX` 这样的掩码要求用户在中间输入一个 `@`。

- **无效字符** 会被静默忽略。
- **短输入** 会用空格填充。
- **长输入** 会被截断以适应掩码。

### 示例 {#examples}

```java
field.setMask("(000) 000-0000");     // 示例: (123) 456-7890
field.setMask("A0A 0A0");            // 示例: A1B 2C3 (加拿大邮政编码)
field.setMask("ZZZZ-0000");          // 示例: ABCD-1234
field.setMask("0000-0000-0000-0000");// 示例: 1234-5678-9012-3456
```

:::tip 允许全输入
如果掩码仅包含 `X`，则字段表现得像一个标准的 [`TextField`](../textfield)，允许任何可打印输入。
当您想保留格式化的能力，而不施加严格的字符规则时，这非常有用。
:::

<ComponentDemo
path='/webforj/maskedtextfield'
files={['src/main/java/com/webforj/samples/views/fields/maskedtextfield/MaskedTextFieldView.java']}
height='250px'
/>

## 验证模式 {#validation-patterns}

虽然掩码定义了输入的结构，但您可以将它们与验证模式组合，强制施加更具体的输入规则。这为客户端增加了一层额外的验证，使用正则表达式。

使用 `setPattern()` 方法应用自定义正则表达式：

```java
field.setPattern("[A-Za-z0-9]{10}"); // 强制执行一个10个字符的字母数字代码
```

这确保了输入不仅匹配掩码，还符合定义的结构，如长度或允许的字符。

这在以下情况下尤其有用：

- 掩码允许太多的灵活性
- 您想强制执行确切的长度或特定格式（例如：十六进制、Base64、UUID）

:::tip 正则表达式格式
模式必须是有效的 [JavaScript 正则表达式](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_expressions)，由 `RegExp` 类型使用。您可以在 [HTML pattern 属性文档](https://developer.mozilla.org/en-US/docs/Web/HTML/Attributes/pattern#overview)中找到更多详细信息。
:::

## 恢复值 {#restoring-the-value}

`MaskedTextField` 包含一个恢复功能，可以将字段的值重置为预定义或原始状态。这对于撤销用户更改或恢复到默认输入非常有用。

```java
field.setRestoreValue("ABC123");
field.restoreValue();
```

### 恢复值的方法 {#ways-to-restore-the-value}

- **通过程序**，调用 `restoreValue()`
- **通过键盘**，按 <kbd>ESC</kbd>（这是默认的恢复键，除非由事件监听器重写）

您可以使用 `setRestoreValue()` 设置要恢复的值。如果未设置恢复值，则 `restoreValue()` 和 <kbd>ESC</kbd> 都将字段值设置为 `null`。

<ComponentDemo
path='/webforj/maskedtextfieldrestore'
files={['src/main/java/com/webforj/samples/views/fields/maskedtextfield/MaskedTextFieldRestoreView.java']}
height='200px'
/>

## `MaskedTextFieldSpinner` {#maskedtextfieldspinner}

`MaskedTextFieldSpinner` 扩展了 `MaskedTextField`，添加了旋转控制，允许用户浏览预定义值列表。这提升了用户体验，特别是在输入应限制为一组固定的有效选项的情况下。

<ComponentDemo
path='/webforj/maskedtextfieldspinner'
files={['src/main/java/com/webforj/samples/views/fields/maskedtextfield/MaskedTextFieldSpinnerView.java']}
height='120px'
/>

### 主要特点 {#key-features}

- **选项列表支持**
  使用 `setOptions()` 用有效字符串值列表填充旋转器：

  ```java
  spinner.setOptions(List.of("选项 A", "选项 B", "选项 C"));
  ```

- **程序化旋转**
  使用 `spinUp()` 和 `spinDown()` 在选项中移动：

  ```java
  spinner.spinUp();   // 选择下一个选项
  spinner.spinDown(); // 选择上一个选项
  ```

- **索引控制**
  使用以下方法设置或获取当前选择索引：

  ```java
  spinner.setOptionIndex(1);
  int current = spinner.getOptionIndex();
  ```

- **掩码兼容性**
  完全继承所有格式、掩码规则和模式验证自 `MaskedTextField`。

## 样式 {#styling}

<TableBuilder name="MaskedTextField" />
