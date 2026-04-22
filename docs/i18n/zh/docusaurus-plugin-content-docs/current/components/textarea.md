---
title: TextArea
sidebar_position: 130
_i18n_hash: e8956f1a5bf39eab9a42244ff8d5ff21
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-textarea" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/field/TextArea" top='true'/>

`TextArea` 组件提供了一个多行文本输入字段，用户可以在其中输入和编辑较长的文本块。它支持最大字符限制、段落结构、换行和验证规则，以控制输入的处理方式。

<!-- INTRO_END -->

## 创建 `TextArea` {#creating-a-textarea}

通过将标签传递给其构造函数来创建 `TextArea`。可以通过 setter 方法配置占位符文本、字符限制和换行行为等属性。

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

// 在末尾添加另一个段落
textArea.addParagraph("这是第二段。");

// 向第一段追加其他内容
textArea.appendToParagraph(0, " 这句话延续了第一句。");

// 删除第二段
textArea.removeParagraph(1);

// 检索并打印所有当前段落
List<String> paragraphs = textArea.getParagraphs();
for (int i = 0; i < paragraphs.size(); i++) {
  System.out.println("第 " + i + " 段: " + paragraphs.get(i));
}
```

## 验证 {#validation}

`TextArea` 组件支持两种互补的验证类型：结构约束和内容约束。

**结构约束** 关注文本的组织和视觉布局。例如：
- `setLineCountLimit(int maxLines)` 限制文本区域中允许的行数。
- `setParagraphLengthLimit(int maxCharsPerLine)` 限制每个段落（或行）的字符数，帮助确保可读性或格式标准。

**内容约束** 则处理输入文本的总体量，不论其如何分布：
- `setMaxLength(int maxChars)` 限制所有段落中允许的字符总数。
- `setMinLength(int minChars)` 强制设定最小长度，确保提供足够的内容。

以下演示允许用户实时调整验证限制——例如最大字符数、段落长度和行数，并查看 `TextArea` 如何响应。

<ComponentDemo 
path='/webforj/textareavalidation?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/textarea/TextAreaValidationView.java'
height = '550px'
/>

## 单词换行和行换行 {#word-wrap-and-line-wrapping}

您可以通过 `setLineWrap()` 控制文本是换行还是水平滚动。当禁用换行时，行在可见区域之外继续水平延伸，需要滚动。当启用时，文本在达到组件边缘时会自动换行到下一行。

为了进一步精细控制换行行为，`setWrapStyle()` 使您可以选择两种样式：
- `WORD_BOUNDARIES` 以完整单词换行，保持自然的阅读流。
- `CHARACTER_BOUNDARIES` 在单个字符处换行，允许对布局进行更严格的控制，尤其是在狭窄或固定宽度的容器中。

这些换行选项与行数和段落长度限制等结构约束协同工作。换行决定文本在可用空间内 *如何* 流动，而结构限制定义文本被允许占用的 *多少* 空间。二者共同帮助维护视觉结构和用户输入边界。

<ComponentDemo 
path='/webforj/textareawrap?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/textarea/TextAreaWrapView.java'
height = '400px'
/>

## 预测文本 {#predicted-text}

`TextArea` 组件支持智能文本建议，帮助用户更快、更少错误地输入。随着用户输入文本，基于当前输入会出现预测建议，使他们能够完成常见或预期的短语。

通过按 `Tab` 或 `ArrowRight` 键可以接受预测，将建议的文本无缝插入输入中。如果在给定时刻没有合适的预测，输入保持不变，用户可以继续输入而不被打断——确保该功能不会造成干扰。

这种预测行为在重复输入场景或短语一致性至关重要的应用程序中提升了速度和准确性。

<ComponentDemo 
path='/webforj/textareapredictedtext?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/textarea/TextAreaPredictedTextView.java'
cssURL='/css/textarea/text-area-predicted-text-view.css'
height = '400px'
/>

:::info
此演示使用 [Datamuse API](https://datamuse.com/) 根据用户的输入提供单词建议。预测的质量和相关性完全依赖于 API 的数据集和评分机制。它不使用 AI 模型或大型语言模型（LLMs）；建议是由一个轻量级的规则基础引擎生成的，专注于词汇相似性。
:::

## 只读和禁用状态 {#read-only-and-disabled-state}

`TextArea` 组件可以设置为只读或禁用，以控制用户交互。

**只读** 文本区域允许用户查看和选择内容，但不能编辑。这对于显示动态或预填充的信息而不应更改非常有用。

相反，**禁用** 文本区域则阻止所有交互——包括焦点和文本选择，通常被样式化为非活动或灰色。

在内容相关但不可变时使用只读模式，而在输入当前不适用或应暂时禁用时使用禁用模式。

<ComponentDemo 
path='/webforj/textareastates?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/textarea/TextAreaStatesView.java'
height = '300px'
/>

## 样式 {#styling}

<TableBuilder name="TextArea" />
