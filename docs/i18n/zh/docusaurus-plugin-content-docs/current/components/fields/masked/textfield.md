---
title: MaskedTextField
sidebar_position: 15
_i18n_hash: 701dcaccf198fbf507d1cd19c4bd995d
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-textfield" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/field/MaskedTextField" top='true'/>

`MaskedTextField` 组件旨在提供可配置且易于验证的文本输入。它非常适合需要格式化输入的应用程序，例如金融、电子商务和医疗应用。

## 基本知识 {#basics}

`MaskedTextField` 可以根据需要进行初始化，可以包含也可以不包含参数。您可以定义初始值、标签、占位符文本以及在值发生更改时的监听器。

```java
MaskedTextField field = new MaskedTextField("账户 ID");
field.setMask("ZZZZ-0000")
  .setHelperText("掩码: ZZZZ-0000 - 例如: SAVE-2025")
```

## 掩码规则 {#mask-rules}

`MaskedTextField` 使用掩码格式化文本输入——一个定义每个位置允许什么字符的字符串。这确保了诸如电话号码、邮政编码和身份证格式等输入的一致性和结构化。

### 支持的掩码字符 {#supported-mask-characters}

| 字符 | 描述                                                              |
|------|------------------------------------------------------------------|
| `X`  | 任何可打印字符                                                    |
| `a`  | 任何字母字符（大写或小写）                                       |
| `A`  | 任何字母字符；小写字母转换为大写                                  |
| `0`  | 任何数字（0–9）                                                  |
| `z`  | 任何数字或字母（大写或小写）                                    |
| `Z`  | 任何数字或字母；小写字母转换为大写                                |

掩码中的所有其他字符都视为字面量，必须精确输入。 
例如，像 `XX@XX` 这样的掩码要求用户在中间输入 `@`。

- **无效字符** 被默默忽略。
- **短输入** 用空格填充。
- **长输入** 被截断以适应掩码。

### 示例 {#examples}

```java
field.setMask("(000) 000-0000");     // 示例: (123) 456-7890
field.setMask("A00 000");            // 示例: A1B 2C3 (加拿大邮政编码)
field.setMask("ZZZZ-0000");          // 示例: ABCD-1234
field.setMask("0000-0000-0000-0000");// 示例: 1234-5678-9012-3456
```

:::tip 允许完整输入
如果掩码仅包含 `X`，则字段表现得像一个标准的 [`TextField`](../text-field.md)，允许任何可打印的输入。
这在您想保留格式化的能力而不应用严格的字符规则时非常有用。
:::

<ComponentDemo 
path='/webforj/maskedtextfield?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskedtextfield/MaskedTextFieldView.java'
height='250px'
/>

## 验证模式 {#validation-patterns}

掩码定义了输入的结构，您可以将掩码与验证模式组合，以强制执行更特定的输入规则。这为客户端验证添加了额外的层，使用正则表达式。

使用 `setPattern()` 方法应用自定义正则表达式：

```java
field.setPattern("[A-Za-z0-9]{10}"); // 强制一个10字符的字母数字代码
```

这确保输入不仅匹配掩码，还符合定义的结构，例如长度或允许的字符。

这在以下情况下特别有用：

- 掩码允许过多的灵活性
- 您想强制执行确切的长度或特定格式（例如，十六进制、Base64、UUID）

:::tip 正则表达式格式
模式必须是有效的 [JavaScript 正则表达式](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_expressions)，如 `RegExp` 类型所使用的那样。您可以在 [HTML 模式属性文档](https://developer.mozilla.org/en-US/docs/Web/HTML/Attributes/pattern#overview) 中找到更多细节。
:::

## 恢复值 {#restoring-the-value}

`MaskedTextField` 包含恢复功能，可以将字段的值重置为预定义或原始状态。 
这对于撤销用户更改或恢复到默认输入非常有用。

```java
field.setRestoreValue("ABC123");
field.restoreValue();
```

### 恢复值的方法 {#ways-to-restore-the-value}

- **通过编程方式**，调用 `restoreValue()`
- **通过键盘**，按 <kbd>ESC</kbd>（这是默认恢复键，除非被事件监听器重写）

您可以通过 `setRestoreValue()` 设置要恢复的值。如果未设置恢复值，字段将在渲染时恢复到初始值。

<ComponentDemo 
path='/webforj/maskedtextfieldrestore?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskedtextfield/MaskedTextFieldRestoreView.java'
height='200px'
/>

## `MaskedTextFieldSpinner` {#maskedtextfieldspinner}

`MaskedTextFieldSpinner` 扩展了 [`MaskedTextField`](#basics)，通过添加旋转控制，让用户在预定义值列表中循环选择。 
在输入应限制在固定的一组有效选项的情况下，这提高了用户体验。

<ComponentDemo 
path='/webforj/maskedtextfieldspinner?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskedtextfield/MaskedTextFieldSpinnerView.java'
height='120px'
/>

### 关键特性 {#key-features}

- **选项列表支持**  
  使用 `setOptions()` 方法填充旋转器的有效字符串值列表：

  ```java
  spinner.setOptions(List.of("选项 A", "选项 B", "选项 C"));
  ```

- **程序化旋转**  
  使用 `spinUp()` 和 `spinDown()` 在选项之间移动：

  ```java
  spinner.spinUp();   // 选择下一个选项
  spinner.spinDown(); // 选择上一个选项
  ```

- **索引控制**  
  设置或检索当前选择的索引：

  ```java
  spinner.setOptionIndex(1);
  int current = spinner.getOptionIndex();
  ```

- **掩码兼容性**  
  完全继承 `MaskedTextField` 的所有格式、掩码规则和模式验证。

## 样式 {#styling}

<TableBuilder name="MaskedTextField" />
