---
title: MaskedTextField
sidebar_position: 15
_i18n_hash: 31f236456f3f30e15115a03275be9132
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-textfield" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/field/MaskedTextField" top='true'/>

`MaskedTextField` 组件旨在提供一个可配置且易于验证的文本输入。非常适合需要格式化输入的应用程序，如金融、电子商务和医疗应用。

## 基础 {#basics}

`MaskedTextField` 可以带或不带参数进行实例化。您可以定义初始值、标签、占位符文本，以及在值更改时的监听器。

```java
MaskedTextField field = new MaskedTextField("账户 ID");
field.setMask("ZZZZ-0000")
  .setHelperText("掩码：ZZZZ-0000 - 例如：SAVE-2025")
```

## 掩码规则 {#mask-rules}

`MaskedTextField` 使用掩码对文本输入进行格式化 - 这是一个定义在每个位置上允许哪些字符的字符串。这确保了像电话号码、邮政编码和身份证格式等内容的一致、结构化输入。

### 支持的掩码字符 {#supported-mask-characters}

| 字符 | 描述                                                                                 |
|-------|--------------------------------------------------------------------------------------|
| `X`   | 任何可打印字符                                                                       |
| `a`   | 任何字母字符（大写或小写）                                                           |
| `A`   | 任何字母字符；小写字母会被转换为大写                                                 |
| `0`   | 任何数字（0–9）                                                                       |
| `z`   | 任何数字或字母（大写或小写）                                                         |
| `Z`   | 任何数字或字母；小写字母会被转换为大写                                              |

掩码中的所有其他字符都被视为文本字面量，必须准确输入。
例如，像 `XX@XX` 这样的掩码要求用户在中间输入 `@`。

- **无效字符** 被默默忽略。
- **短输入** 用空格填充。
- **长输入** 被截断以适应掩码。

### 示例 {#examples}

```java
field.setMask("(000) 000-0000");     // 例如：(123) 456-7890
field.setMask("A00 000");            // 例如：A1B 2C3（加拿大邮政编码）
field.setMask("ZZZZ-0000");          // 例如：ABCD-1234
field.setMask("0000-0000-0000-0000");// 例如：1234-5678-9012-3456
```

:::tip 允许完整输入
如果掩码只包含 `X`，该字段的行为类似于标准 [`TextField`](../text-field.md)，允许任何可打印的输入。
这在您希望保留格式化能力而不施加严格字符规则时很有用。
:::

<ComponentDemo 
path='/webforj/maskedtextfield?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskedtextfield/MaskedTextFieldView.java'
height='250px'
/>

## 验证模式 {#validation-patterns}

虽然掩码定义了输入的结构，您可以将它们与验证模式结合使用，以强制执行更具体的输入规则。这为客户端验证添加了额外的一层，使用正则表达式。

使用 `setPattern()` 方法应用自定义正则表达式：

```java
field.setPattern("[A-Za-z0-9]{10}"); // 强制执行10个字符的字母数字代码
```

这确保输入不仅匹配掩码，还符合定义的结构，例如长度或允许的字符。

这在以下情况下尤其有用：

- 掩码允许过多的灵活性
- 您希望强制执行确切的长度或特定格式（例如，十六进制，Base64，UUID）

:::tip 正则表达式格式
模式必须是有效的 [JavaScript 正则表达式](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_expressions)，如 `RegExp` 类型所使用的。您可以在 [HTML 模式属性文档](https://developer.mozilla.org/en-US/docs/Web/HTML/Attributes/pattern#overview) 中找到更多细节。
:::

## 恢复值 {#restoring-the-value}

`MaskedTextField` 包含一个恢复功能，可以将字段的值重置为预定义或原始状态。
这对于撤消用户更改或恢复默认输入非常有用。

```java
field.setRestoreValue("ABC123");
field.restoreValue();
```

### 恢复值的方法 {#ways-to-restore-the-value}

- **以编程方式**，通过调用 `restoreValue()`
- **通过键盘**，按 <kbd>ESC</kbd>（这是默认的恢复键，除非被事件监听器覆盖）

您可以使用 `setRestoreValue()` 设置要恢复的值。如果未设置恢复值，则字段将恢复到渲染时的初始值。

<ComponentDemo 
path='/webforj/maskedtextfieldrestore?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskedtextfield/MaskedTextFieldRestoreView.java'
height='200px'
/>

## `MaskedTextFieldSpinner` {#maskedtextfieldspinner}

`MaskedTextFieldSpinner` 通过添加旋转控制扩展了 [`MaskedTextField`](#basics)，允许用户循环选择一组预定义的值。
这在输入需要限制为固定有效选项集的情况下改善了用户体验。

<ComponentDemo 
path='/webforj/maskedtextfieldspinner?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskedtextfield/MaskedTextFieldSpinnerView.java'
height='120px'
/>

### 主要功能 {#key-features}

- **选项列表支持**  
  使用 `setOptions()` 用有效字符串值列表填充旋转器：

  ```java
  spinner.setOptions(List.of("选项 A", "选项 B", "选项 C"));
  ```

- **编程旋转**  
  使用 `spinUp()` 和 `spinDown()` 在选项之间移动：

  ```java
  spinner.spinUp();   // 选择下一个选项
  spinner.spinDown(); // 选择上一个选项
  ```

- **索引控制**  
  使用以下方法设置或检索当前选择索引：

  ```java
  spinner.setOptionIndex(1);
  int current = spinner.getOptionIndex();
  ```

- **掩码兼容性**  
  完全继承 `MaskedTextField` 的所有格式化、掩码规则和模式验证。

## 样式 {#styling}

<TableBuilder name="MaskedTextField" />
