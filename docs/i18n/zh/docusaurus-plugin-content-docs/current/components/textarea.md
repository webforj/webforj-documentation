---
title: TextArea
sidebar_position: 130
_i18n_hash: 0ca8e9c1163e55bb86adf44931de139a
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-textarea" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/field/TextArea" top='true'/>

`TextArea` 组件在 webforJ 中提供了多行文本输入的解决方案。最终用户可以自由输入和编辑文本，而开发人员可以通过设置合理的边界，如最大字符限制、段落结构和验证规则来控制输入。

以下是一个用于输入多行文本的 `TextArea` 示例：

<ComponentDemo 
path='/webforj/textarea?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/textarea/TextAreaView.java'
height = '300px'
/>

## 段落管理 {#managing-paragraphs}

`TextArea` 组件提供了处理文本段落的功能，非常适合需要文档编辑或结构化文本输入的应用。

以下是构建和操作段落内容的快速示例：

```java
TextArea textArea = new TextArea();

// 在开头插入一个段落
textArea.addParagraph(0, "这是第一段。");

// 在末尾追加另一个段落
textArea.addParagraph("这是第二段。");

// 向第一段追加其他内容
textArea.appendToParagraph(0, " 这个句子延续了第一个句子。");

// 删除第二段
textArea.removeParagraph(1);

// 检索并打印所有当前段落
List<String> paragraphs = textArea.getParagraphs();
for (int i = 0; i < paragraphs.size(); i++) {
    System.out.println("段落 " + i + ": " + paragraphs.get(i));
}
```

## 验证 {#validation}

`TextArea` 组件支持两种互补的验证类型：结构约束和内容约束。

**结构约束** 关注文本的组织和视觉布局。例如：
- `setLineCountLimit(int maxLines)` 限制文本区域允许的行数。
- `setParagraphLengthLimit(int maxCharsPerLine)` 限制每个段落（或行）的字符数，帮助确保可读性或格式标准。

**内容约束** 则处理输入的文本总量，不考虑其分布：
- `setMaxLength(int maxChars)` 限制所有段落允许的字符总数。
- `setMinLength(int minChars)` 强制最小长度，确保提供足够的内容。

以下演示让用户实时调整验证限制，比如最大字符数、段落长度和行数，并查看 `TextArea` 如何响应。
	
<ComponentDemo 
path='/webforj/textareavalidation?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/textarea/TextAreaValidationView.java'
height = '550px'
/>

## 单词换行和行换行 {#word-wrap-and-line-wrapping}

您可以使用 `setLineWrap()` 控制文本是换行还是水平滚动。当换行被禁用时，行会继续在可见区域之外水平延伸，需要滚动。当启用时，文本在到达组件边缘时会自动换行到下一行。

为了进一步细化换行的行为，`setWrapStyle()` 让您可以在两种样式之间进行选择：
- `WORD_BOUNDARIES` 在整个单词处换行，保持自然阅读流。
- `CHARACTER_BOUNDARIES` 在单个字符处换行，允许对布局进行更严格的控制，尤其是在狭窄或固定宽度的容器中。

这些换行选项与行数和段落长度限制等结构约束相辅相成。换行确定文本在可用空间内的流动方式，而结构限制定义了文本被允许占据的空间量。它们共同帮助维护视觉结构和用户输入边界。

<ComponentDemo 
path='/webforj/textareawrap?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/textarea/TextAreaWrapView.java'
height = '400px'
/>

## 预测文本 {#predicted-text}

`TextArea` 组件支持智能文本建议，帮助用户更快地输入和减少错误。当用户输入文本时，根据当前输入提供预测建议，使他们可以完成常见或预期的短语。

可以通过按 `Tab` 或 `ArrowRight` 键来接受预测，将建议文本无缝插入输入中。如果在给定时刻没有合适的预测，输入保持不变，用户可以继续输入而不受干扰——确保该功能不会妨碍使用。

这种预测行为提高了速度和准确性，特别是在重复输入场景或短语一致性很重要的应用中。

<ComponentDemo 
path='/webforj/textareapredictedtext?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/textarea/TextAreaPredictedTextView.java'
height = '400px'
/>

:::info
此演示使用 [Datamuse API](https://datamuse.com/) 提供基于用户输入的单词建议。预测的质量和相关性完全取决于 API 的数据集和评分机制。它不使用人工智能模型或大型语言模型（LLMs）；建议由一个轻量级的基于规则的引擎生成，专注于词汇相似性。
:::

## 只读和禁用状态 {#read-only-and-disabled-state}

`TextArea` 组件可以设置为只读或禁用，以控制用户交互。

**只读** 文本区域允许用户查看和选择内容，但不允许编辑。对于显示动态或预填充信息且不应更改的内容非常有用。

另一方面，**禁用** 文本区域阻止所有交互——包括焦点和文本选择——通常被样式设为非活动或灰色。

在内容相关但不可变时使用只读模式，而在输入当前不适用或应暂时禁用时使用禁用模式。

<ComponentDemo 
path='/webforj/textareastates?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/textarea/TextAreaStatesView.java'
height = '300px'
/>

## 样式 {#styling}

<TableBuilder name="TextArea" />
