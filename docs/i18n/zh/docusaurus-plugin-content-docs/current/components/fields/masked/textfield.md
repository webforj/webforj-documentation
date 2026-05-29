---
title: MaskedTextField
sidebar_position: 15
_i18n_hash: 8ef566720a30ba07ae47b5a957804c52
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-textfield" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/field/MaskedTextField" top='true'/>

`MaskedTextField` 组件提供一个可配置的文本输入，强制执行格式规则和验证。 它非常适合需要结构化输入的应用程序，如金融、电子商务和医疗保健系统。

<!-- INTRO_END -->

## 基本信息 {#basics}

`MaskedTextField` 可以无参数或带参数实例化。 您可以定义初始值、标签、占位符文本和一个监听器以便在值更改时使用。

```java
MaskedTextField field = new MaskedTextField("账户 ID");
field.setMask("ZZZZ-0000")
  .setHelperText("掩码：ZZZZ-0000 - 例如：SAVE-2025")
```

## 掩码规则 {#mask-rules}

`MaskedTextField` 使用掩码格式化文本输入——一个定义每个位置允许什么字符的字符串。 这确保了一致的、结构化的输入，例如电话号码、邮政编码和 ID 格式。

:::tip 以编程方式应用掩码
要在字段外使用相同的掩码语法格式化字符串，例如在 [`Table`](/docs/components/table/overview) 中渲染数据时，请使用 [`MaskDecorator`](/docs/advanced/mask-decorator) 小工具类。
:::

### 支持的掩码字符 {#supported-mask-characters}

| 字符      | 描述                                                                                   |
|-----------|-----------------------------------------------------------------------------------------|
| `X`       | 任何可打印字符                                                                         |
| `a`       | 任何字母字符（大写或小写）                                                               |
| `A`       | 任何字母字符；小写字母将转换为大写                                                      |
| `0`       | 任何数字（0–9）                                                                         |
| `z`       | 任何数字或字母（大写或小写）                                                            |
| `Z`       | 任何数字或字母；小写字母将转换为大写                                                    |

掩码中的所有其他字符都被视为字面意思，必须精确输入。 
例如，一个像 `XX@XX` 的掩码要求用户在中间输入一个 `@`。

- **无效字符** 被静默忽略。
- **短输入** 用空格填充。
- **长输入** 被截断以适应掩码。

### 示例 {#examples}

```java
field.setMask("(000) 000-0000");     // 例如: (123) 456-7890
field.setMask("A00 000");            // 例如: A1B 2C3 (加拿大邮政编码)
field.setMask("ZZZZ-0000");          // 例如: ABCD-1234
field.setMask("0000-0000-0000-0000");// 例如: 1234-5678-9012-3456
```

:::tip 允许完整输入
如果掩码仅包含 `X`，则该字段表现得像标准的 [`TextField`](../textfield)，允许任何可打印输入。
当您希望保留格式化而不应用严格的字符规则时，这非常有用。
:::

<ComponentDemo
path='/webforj/maskedtextfield'
files={['src/main/java/com/webforj/samples/views/fields/maskedtextfield/MaskedTextFieldView.java']}
height='250px'
/>

## 验证模式 {#validation-patterns}

虽然掩码定义了输入的结构，但您可以将它们与验证模式结合使用，以强制执行更具体的输入规则。 这为客户端验证添加了一层额外的正则表达式。

使用 `setPattern()` 方法应用自定义正则表达式：

```java
field.setPattern("[A-Za-z0-9]{10}"); // 强制执行 10 个字符的字母数字代码
```

这确保输入不仅匹配掩码，还符合已定义的结构，例如长度或允许的字符。

这在以下情况下尤其有用：

- 当掩码允许过多的灵活性
- 当您想强制确切的长度或特定格式（例如十六进制、Base64、UUID）

:::tip 正则表达式格式
模式必须是有效的 [JavaScript 正则表达式](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_expressions)，由 `RegExp` 类型使用。 您可以在 [HTML 模式属性文档](https://developer.mozilla.org/en-US/docs/Web/HTML/Attributes/pattern#overview) 中找到更多详细信息。
:::

## 恢复值 {#restoring-the-value}

`MaskedTextField` 包含一个恢复功能，可以将字段的值重置为预定义或原始状态。 
这对于撤消用户更改或还原为默认输入很有用。

```java
field.setRestoreValue("ABC123");
field.restoreValue();
```

### 恢复值的方法 {#ways-to-restore-the-value}

- **通过编程**，调用 `restoreValue()`
- **通过键盘**，按 <kbd>ESC</kbd> 键（这是默认的恢复键，除非通过事件监听器覆盖）

您可以使用 `setRestoreValue()` 设置要恢复的值。 如果没有设置恢复值，字段将在渲染时还原为初始值。

<ComponentDemo
path='/webforj/maskedtextfieldrestore'
files={['src/main/java/com/webforj/samples/views/fields/maskedtextfield/MaskedTextFieldRestoreView.java']}
height='200px'
/>

## `MaskedTextFieldSpinner` {#maskedtextfieldspinner}

`MaskedTextFieldSpinner` 通过添加旋转控件扩展了 [`MaskedTextField`](#basics)，让用户可以循环选择预定义值列表。 
这改善了在输入应限制为一组固定有效选项的情况中的用户体验。

<ComponentDemo
path='/webforj/maskedtextfieldspinner'
files={['src/main/java/com/webforj/samples/views/fields/maskedtextfield/MaskedTextFieldSpinnerView.java']}
height='120px'
/>

### 关键特性 {#key-features}

- **选项列表支持**  
  使用 `setOptions()` 用有效字符串值列表填充旋转器：

  ```java
  spinner.setOptions(List.of("选项 A", "选项 B", "选项 C"));
  ```

- **编程旋转**  
  使用 `spinUp()` 和 `spinDown()` 在选项中移动：

  ```java
  spinner.spinUp();   // 选择下一个选项
  spinner.spinDown(); // 选择上一个选项
  ```

- **索引控制**  
  使用以下方法设置或检索当前选择的索引：

  ```java
  spinner.setOptionIndex(1);
  int current = spinner.getOptionIndex();
  ```

- **掩码兼容性**  
  完全继承 `MaskedTextField` 的所有格式、掩码规则和模式验证。

## 样式 {#styling}

<TableBuilder name="MaskedTextField" />
