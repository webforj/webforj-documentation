---
title: TextArea
sidebar_position: 130
_i18n_hash: 423b70520e8f64a463d2c7b1d0e35ddc
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-textarea" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/field/TextArea" top='true'/>

`TextArea` 组件提供了一个多行文本输入框，用户可以在其中输入和编辑更长的文本块。它支持最大字符限制、段落结构、换行和验证规则，以控制输入的处理方式。

<!-- INTRO_END -->

## 创建 `TextArea` {#creating-a-textarea}

通过将标签传递给其构造函数来创建 `TextArea`。可以通过设置方法配置占位符文本、字符限制和换行行为等属性。

<ComponentDemo 
path='/webforj/textarea?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/textarea/TextAreaView.java'
height = '300px'
/>

## 管理段落 {#managing-paragraphs}

`TextArea` 组件提供了处理文本段落的功能，非常适合需要文档编辑或结构化文本输入的应用程序。

以下是构建和操作段落内容的快速示例：

```java
TextArea textArea = new TextArea();

// 在开头插入段落
textArea.addParagraph(0, "这是第一段。");

// 在最后添加另一段
textArea.addParagraph("这是第二段。");

// 往第一段追加内容
textArea.appendToParagraph(0, " 这一句延续了第一段。");

// 移除第二段
textArea.removeParagraph(1);

// 检索并打印所有当前段落
List<String> paragraphs = textArea.getParagraphs();
for (int i = 0; i < paragraphs.size(); i++) {
    System.out.println("段落 " + i + ": " + paragraphs.get(i));
}
```

## 验证 {#validation}

`TextArea` 组件支持两种互补类型的验证：结构约束和内容约束。

**结构约束** 关注文本的组织和视觉布局。例如：
- `setLineCountLimit(int maxLines)` 限制文本区域允许的行数。
- `setParagraphLengthLimit(int maxCharsPerLine)` 限制每段（或每行）的字符数，帮助确保可读性或格式标准。

**内容约束** 则处理输入的总文本量，无论其分布情况：
- `setMaxLength(int maxChars)` 限制所有段落允许的字符总数。
- `setMinLength(int minChars)` 强制设置最小长度，确保提供足够的内容。

以下演示允许用户实时调整验证限制——例如最大字符数、段落长度和行数——并观察 `TextArea` 如何响应。

<ComponentDemo 
path='/webforj/textareavalidation?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/textarea/TextAreaValidationView.java'
height = '550px'
/>

## 单词换行和行换行 {#word-wrap-and-line-wrapping}

您可以使用 `setLineWrap()` 控制文本是换行还是水平滚动。当换行被禁用时，行会在可见区域外继续，需滚动查看。当启用时，文本在到达组件边缘时自动换行至下一行。

为了进一步细化换行行为，`setWrapStyle()` 让您可以在两种样式之间进行选择：
- `WORD_BOUNDARIES` 在完整单词处换行，保持自然阅读流程。
- `CHARACTER_BOUNDARIES` 在单个字符处换行，允许对布局进行更严格的控制，特别是在狭窄或固定宽度的容器中。

这些换行选项与结构约束如行数和段落长度限制协同工作。换行决定了文本在可用空间内的流动方式，而结构限制定义了文本被允许占据的空间量。两者结合有助于维持视觉结构和用户输入边界。

<ComponentDemo 
path='/webforj/textareawrap?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/textarea/TextAreaWrapView.java'
height = '400px'
/>

## 预测文本 {#predicted-text}

`TextArea` 组件支持智能文本建议，帮助用户更快地输入并减少错误。当用户输入文本时，基于当前输入的预测建议会出现，允许他们完成常见或预期的短语。

通过按 `Tab` 或 `ArrowRight` 键可以接受预测，将建议的文本无缝插入输入框。如果此时没有合适的预测，输入保持不变，用户可以继续输入而不会被打断——确保该功能不会妨碍用户操作。

这种预测行为提高了速度和准确性，特别是在重复输入场景或短语一致性重要的应用程序中。

<ComponentDemo 
path='/webforj/textareapredictedtext?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/textarea/TextAreaPredictedTextView.java'
height = '400px'
/>

:::info
该演示使用 [Datamuse API](https://datamuse.com/) 根据用户的输入提供单词建议。预测的质量和相关性完全取决于 API 的数据集和评分机制。它不使用 AI 模型或大型语言模型（LLMs）；建议是从一个轻量级、基于规则的引擎生成的，专注于词汇相似性。
:::

## 只读和禁用状态 {#read-only-and-disabled-state}

`TextArea` 组件可以设置为只读或禁用，以控制用户交互。

**只读** 文本区域允许用户查看和选择内容，但不能编辑。这对于显示动态或预先填充的信息非常有用，这些信息应保持不变。

另一方面，**禁用** 文本区域阻止所有交互——包括聚焦和文本选择——通常以非活动或灰色样式显示。

当内容相关但不可变时使用只读模式，当输入当前不适用或应暂时处于非活动状态时使用禁用模式。

<ComponentDemo 
path='/webforj/textareastates?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/textarea/TextAreaStatesView.java'
height = '300px'
/>

## 样式 {#styling}

<TableBuilder name="TextArea" />
