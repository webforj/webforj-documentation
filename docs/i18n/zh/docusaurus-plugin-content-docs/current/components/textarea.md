---
title: TextArea
sidebar_position: 130
_i18n_hash: 5e61ae2b47786f23e6f1f6eba317ed54
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-textarea" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/field/TextArea" top='true'/>

`TextArea` 组件提供一个多行文本输入字段，用户可以在其中输入和编辑更长的文本块。它支持最大字符限制、段落结构、换行和用于控制输入处理的验证规则。

<!-- INTRO_END -->

## 创建 `TextArea` {#creating-a-textarea}

通过将标签传递给其构造函数来创建 `TextArea`。可以通过设置方法配置占位符文本、字符限制和换行行为等属性。

<ComponentDemo
path='/webforj/textarea'
files={['src/main/java/com/webforj/samples/views/textarea/TextAreaView.java']}
height='300px'
/>

## 管理段落 {#managing-paragraphs}

`TextArea` 组件提供处理文本段落的功能，使其非常适合需要文档编辑或结构化文本输入的应用程序。

以下是如何构建和操作段落内容的快速示例：

```java
TextArea textArea = new TextArea();

// 在开头插入一个段落
textArea.addParagraph(0, "这是第一段。");

// 将另一个段落附加到末尾
textArea.addParagraph("这是第二段。");

// 向第一段附加额外内容
textArea.appendToParagraph(0, " 这句话继续了第一段的内容。");

// 删除第二段
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
- `setLineCountLimit(int maxLines)` 限制文本区域中允许的行数。
- `setParagraphLengthLimit(int maxCharsPerLine)` 限制每个段落（或行）的字符数，以帮助执行可读性或格式标准。

**内容约束** 则处理输入的总文本量，无论其分布如何：
- `setMaxLength(int maxChars)` 限制所有段落允许的总字符数。
- `setMinLength(int minChars)` 强制执行最小长度，确保提供充分的内容。

以下演示允许用户实时调整验证限制——例如最大字符计数、段落长度和行计数，并查看 `TextArea` 如何响应。

<ComponentDemo
path='/webforj/textareavalidation'
files={['src/main/java/com/webforj/samples/views/textarea/TextAreaValidationView.java']}
height='550px'
/>

## 单词换行和行换行 {#word-wrap-and-line-wrapping}

您可以使用 `setLineWrap()` 控制文本是否换行或水平滚动。当换行被禁用时，行超出可见区域继续水平延续，需要滚动。当启用时，当文本到达组件边缘时，会自动换行至下一行。

为了进一步细化换行的行为，`setWrapStyle()` 让您选择两种样式：
- `WORD_BOUNDARIES` 在整个单词处换行，保持自然的阅读流畅性。
- `CHARACTER_BOUNDARIES` 在单个字符处换行，允许对布局进行更紧密的控制，特别是在狭窄或固定宽度的容器中。

这些换行选项与结构约束（如行计数和段落长度限制）齐心协力。换行决定文本在可用空间内如何流动，而结构限制定义文本被允许占用多少空间。它们共同帮助维护视觉结构和用户输入边界。

<ComponentDemo
path='/webforj/textareawrap'
files={['src/main/java/com/webforj/samples/views/textarea/TextAreaWrapView.java']}
height='400px'
/>

## 预测文本 {#predicted-text}

`TextArea` 组件支持智能文本建议，以帮助用户更快地输入并减少错误。当用户输入文本时，基于当前输入的预测建议会出现，允许他们完成常见或预期的短语。

通过按 `Tab` 或 `ArrowRight` 键可以接受预测，将建议的文本无缝插入输入中。如果在某一时刻没有合适的预测可用，输入将保持不变，用户可以继续输入而不会中断——确保该特性从未妨碍用户体验。

这种预测行为提高了速度和准确性，特别是在重复输入场景或对短语一致性要求较高的应用程序中。

<ComponentDemo
path='/webforj/textareapredictedtext'
files={[
  'src/main/java/com/webforj/samples/views/textarea/TextAreaPredictedTextView.java',
  'src/main/resources/static/css/textarea/text-area-predicted-text-view.css',
]}
height='400px'
/>

:::info
此演示使用 [Datamuse API](https://datamuse.com/) 根据用户的输入提供单词建议。建议的质量和相关性完全取决于 API 的数据集和评分机制。它不使用 AI 模型或大型语言模型（LLMs）；建议是从一个轻量级的基于规则的引擎生成的，专注于词汇相似性。
:::

## 只读和禁用状态 {#read-only-and-disabled-state}

`TextArea` 组件可以设置为只读或禁用，以控制用户交互。

**只读** 文本区域允许用户查看和选择内容，但不允许编辑。这对于展示动态或预填写的信息非常有用，这些信息应该保持不变。

另一方面，**禁用** 文本区域会阻止所有交互——包括焦点和文本选择——通常被样式处理为非活动状态或变灰。

在内容相关但不可变的情况下使用只读模式，而在输入当前不适用或应该临时禁用的情况下使用禁用模式。

<ComponentDemo
path='/webforj/textareastates'
files={['src/main/java/com/webforj/samples/views/textarea/TextAreaStatesView.java']}
height='300px'
/>

## 样式 {#styling}

<TableBuilder name="TextArea" />
