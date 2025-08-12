---
title: TextArea
sidebar_position: 130
_i18n_hash: f109f006fcd252bf81b6cccb83d38a50
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-textarea" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/field/TextArea" top='true'/>

`TextArea`组件在webforJ中提供了多行文本输入的解决方案。最终用户可以自由输入和编辑文本，而开发人员可以使用最大字符限制、段落结构和验证规则等功能来设置合理的边界。

以下是一个用于输入多行文本的`TextArea`示例：

<ComponentDemo 
path='/webforj/textarea?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/textarea/TextAreaView.java'
height = '300px'
/>

## 管理段落 {#managing-paragraphs}

`TextArea`组件提供了处理文本段落的功能，使其非常适合需要文档编辑或结构化文本输入的应用。

以下是如何构建和操作段落内容的简要示例：

```java
TextArea textArea = new TextArea();

// 在开头插入一个段落
textArea.addParagraph(0, "这是第一段。");

// 在结尾追加另一个段落
textArea.addParagraph("这是第二段。");

// 向第一段追加额外内容
textArea.appendToParagraph(0, " 这一句继续了第一句。");

// 移除第二段
textArea.removeParagraph(1);

// 检索并打印所有当前段落
List<String> paragraphs = textArea.getParagraphs();
for (int i = 0; i < paragraphs.size(); i++) {
    System.out.println("段落 " + i + ": " + paragraphs.get(i));
}
```

## 验证 {#validation}

`TextArea`组件支持两种互补的验证类型：结构约束和内容约束。

**结构约束**着重于文本的组织和视觉布局。例如：
- `setLineCountLimit(int maxLines)`限制文本区域中允许的行数。
- `setParagraphLengthLimit(int maxCharsPerLine)`限制每段（或每行）的字符数，帮助维护可读性或格式标准。

**内容约束**则处理输入的文本总量，而不考虑其分布方式：
- `setMaxLength(int maxChars)`限制所有段落中允许的最大字符总数。
- `setMinLength(int minChars)`强制最小长度，确保提供足够的内容。

以下演示允许用户实时调整验证限制，例如最大字符数、段落长度和行数，并查看`TextArea`的响应。
	
<ComponentDemo 
path='/webforj/textareavalidation?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/textarea/TextAreaValidationView.java'
height = '550px'
/>

## 单词换行和行换行 {#word-wrap-and-line-wrapping}

您可以使用`setLineWrap()`来控制文本是换行还是水平滚动。当换行被禁用时，行会继续在可视区域外水平延伸，需使用滚动条。当启用时，当文本到达组件边缘时，会自动换行到下一行。

为了进一步调整换行的行为，`setWrapStyle()`让您在两种样式之间进行选择：
- `WORD_BOUNDARIES`在完整单词处换行，保持自然的阅读流畅性。
- `CHARACTER_BOUNDARIES`在单个字符处换行，允许在狭窄或固定宽度容器内更紧密的布局控制。

这些换行选项与行数和段落长度限制等结构约束协同工作。当换行决定了文本如何在可用空间内流动时，结构限制定义了文本被允许占据的空间量。二者共同帮助维护视觉结构和用户输入边界。

<ComponentDemo 
path='/webforj/textareawrap?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/textarea/TextAreaWrapView.java'
height = '400px'
/>

## 预测文本 {#predicted-text}

`TextArea`组件支持智能文本建议，帮助用户更快、更少错误地输入。当用户输入文本时，基于当前输入出现预测建议，使其能够完成常见或预期的短语。

可以通过按下`Tab`或`ArrowRight`键来接受预测，将建议的文本无缝插入输入中。如果此时没有合适的预测，输入将保持不变，用户可以继续输入而不会中断—确保此功能绝不会妨碍用户。

这种预测行为增强了输入的速度和准确性，特别是在重复输入的场景或需要保持短语一致性的应用中。

<ComponentDemo 
path='/webforj/textareapredictedtext?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/textarea/TextAreaPredictedTextView.java'
height = '400px'
/>

:::info
该演示使用[Datamuse API](https://datamuse.com/)根据用户的输入提供单词建议。预测的质量和相关性完全取决于API的数据集和评分机制。它不使用AI模型或大型语言模型(LLMs)；建议是由一个轻量级的规则驱动引擎生成，专注于词汇相似性。
:::

## 只读和禁用状态 {#read-only-and-disabled-state}

`TextArea`组件可以设置为只读或禁用，以控制用户交互。

**只读**文本区域允许用户查看和选择内容，但不能编辑。这对于显示应保持不变的动态或预填信息非常有用。

另一方面，**禁用**文本区域则阻止所有交互—包括聚焦和文本选择—通常显示为非活动或灰色。

在内容相关但不可更改时使用只读模式，而在输入当前不适用或应暂时禁用时使用禁用模式。

<ComponentDemo 
path='/webforj/textareastates?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/textarea/TextAreaStatesView.java'
height = '300px'
/>

## 样式 {#styling}

<TableBuilder name="TextArea" />
