---
title: TextArea
sidebar_position: 130
_i18n_hash: c25007720c315e5b0b26197e1fdfff61
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-textarea" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/field/TextArea" top='true'/>

`TextArea` 组件提供了一个多行文本输入框，用户可以在其中输入和编辑较长的文本块。它支持最大字符限制、段落结构、换行和验证规则，以控制输入的处理方式。

<!-- INTRO_END -->

## 创建 `TextArea` {#creating-a-textarea}

通过将标签传递给其构造函数来创建 `TextArea`。可以通过设置方法来配置占位符文本、字符限制和换行行为。

<ComponentDemo 
path='/webforj/textarea?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/textarea/TextAreaView.java'
height = '300px'
/>

## 管理段落 {#managing-paragraphs}

`TextArea` 组件提供了处理文本段落的功能，使其非常适合需要文档编辑或结构化文本输入的应用程序。

以下是如何构建和操作段落内容的快速示例：

```java
TextArea textArea = new TextArea();

// 在开头插入一个段落
textArea.addParagraph(0, "这是第一段。");

// 在末尾附加另一个段落
textArea.addParagraph("这是第二段。");

// 向第一个段落添加额外内容
textArea.appendToParagraph(0, " 此句继续第一句。");

// 移除第二个段落
textArea.removeParagraph(1);

// 检索并打印当前所有段落
List<String> paragraphs = textArea.getParagraphs();
for (int i = 0; i < paragraphs.size(); i++) {
  System.out.println("段落 " + i + ": " + paragraphs.get(i));
}
```

## 验证 {#validation}

`TextArea` 组件支持两种互补的验证类型：结构约束和内容约束。

**结构约束** 关注文本的组织和视觉布局。例如：
- `setLineCountLimit(int maxLines)` 限制文本区域内允许的行数。
- `setParagraphLengthLimit(int maxCharsPerLine)` 限制每个段落（或行）的字符数，以帮助实施可读性或格式标准。

**内容约束** 则处理输入文本的总量，而不管其分布方式：
- `setMaxLength(int maxChars)` 限制所有段落中允许的总字符数。
- `setMinLength(int minChars)` 强制最小长度，确保提供足够的内容。

以下演示允许用户实时调整验证限制——例如最大字符数、段落长度和行数，并观察 `TextArea` 的响应。
	
<ComponentDemo 
path='/webforj/textareavalidation?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/textarea/TextAreaValidationView.java'
height = '550px'
/>

## 字词换行和行换行 {#word-wrap-and-line-wrapping}

您可以通过使用 `setLineWrap()` 控制文本是换行还是水平滚动。当禁用换行时，行会在可见区域之外水平延续，需要滚动。当启用时，文本在达到组件边缘时会自动换到下一行。

为了进一步优化换行行为，`setWrapStyle()` 让您可以在两种风格之间选择：
- `WORD_BOUNDARIES` 在整个单词处换行，保持自然的阅读流畅性。
- `CHARACTER_BOUNDARIES` 在单个字符处换行，尤其在狭窄或固定宽度的容器中，允许对布局进行更严格的控制。

这些换行选项与行数和段落长度限制等结构约束配合使用。换行决定了文本在可用空间内的流动方式，而结构限制则定义了文本被允许占用的空间大小。它们共同帮助维护视觉结构和用户输入边界。

<ComponentDemo 
path='/webforj/textareawrap?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/textarea/TextAreaWrapView.java'
height = '400px'
/>

## 预测文本 {#predicted-text}

`TextArea` 组件支持智能文本建议，帮助用户更快、更少错误地输入。当用户输入文本时，基于当前输入的预测建议会出现，允许他们完成常见或预期的短语。

可以通过按 `Tab` 或 `ArrowRight` 键来接受预测，将建议文本无缝插入输入框。如果在给定时刻没有合适的预测，输入保持不变，用户可以继续输入而不受干扰——确保功能不会妨碍用户。

这种预测行为增强了速度和准确性，尤其在重复输入场景或需要陈述一致性的应用程序中。

<ComponentDemo 
path='/webforj/textareapredictedtext?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/textarea/TextAreaPredictedTextView.java'
height = '400px'
/>

:::info
此演示使用 [Datamuse API](https://datamuse.com/) 提供基于用户输入的单词建议。预测的质量和相关性完全依赖于 API 的数据集和评分机制。它不使用 AI 模型或大型语言模型 (LLMs)；建议是由一个轻量级的基于规则的引擎生成，专注于词汇相似性。
:::

## 只读和禁用状态 {#read-only-and-disabled-state}

`TextArea` 组件可以设置为只读或禁用状态，以控制用户交互。

**只读** 文本区域允许用户查看和选择内容，但不能进行编辑。这对于显示应该保持不变的动态或预填信息很有用。

**禁用** 文本区域则阻止所有交互——包括焦点和文本选择——通常呈现为非活动或灰色样式。

在内容相关但不可变时使用只读模式，而在输入当前不适用或应暂时不活动时使用禁用模式。

<ComponentDemo 
path='/webforj/textareastates?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/textarea/TextAreaStatesView.java'
height = '300px'
/>

## 样式 {#styling}

<TableBuilder name="TextArea" />
