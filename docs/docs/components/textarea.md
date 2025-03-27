---
title: TextArea
---

<DocChip chip="shadow" />

<DocChip chip="name" label="dwc-textarea" />

<JavadocLink type="foundation" location="com/webforj/component/field/TextArea" top='true'/>

The `TextArea` component in webforJ offers a solution for multi-line text input. End-users can freely type and edit text, while developers can set reasonable boundaries using features like maximum character limits, paragraph structure, and validation rules.

Here's an example of a `TextArea` for entering multi-line text:

<ComponentDemo 
path='/webforj/textarea?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-docs-samples/refs/heads/main/src/main/java/com/webforj/samples/views/textarea/TextAreaView.java'
height = '150px'
/>

## Managing paragraphs

The `TextArea` component provides features for handling text paragraphs, making it ideal for applications that require document editing or structured text input.

### Adding paragraphs

The `addParagraph()` method allows you to add paragraphs to the `TextArea`. You can insert a paragraph at a specific position or append it to the end of the text.

- `addParagraph(int index, String paragraph)`: Inserts a paragraph at the specified index. The index starts from 0, where 0 represents the first paragraph. If the index is less than 0, the paragraph is added at the end.
- `addParagraph(String paragraph)`: Appends a paragraph to the end of the TextArea.

```java
// Add a paragraph at a specific position
textArea.addParagraph(0, "This is the first paragraph.");

// Append a paragraph to the end
textArea.addParagraph("This is another paragraph added to the end.");
```

These methods are useful for adding content dynamically based on user input or programmatic actions.

### Removing paragraphs

To manage text efficiently, the `TextArea` component includes the `removeParagraph()` method, which enables the removal of specific paragraphs by index.

```java
// Add multiple paragraphs
textArea.addParagraph("Paragraph 1");
textArea.addParagraph("Paragraph 2");
textArea.addParagraph("Paragraph 3");

// Remove the second paragraph
textArea.removeParagraph(1);
```

:::tip Invalid Indexes
If the specified index does not exist, the method simply does nothing, preventing unintended errors. 
:::

### Appending text to paragraphs

The `appendToParagraph()` method enables you to add additional content to an existing paragraph while preserving its original structure. Using `appendToParagraph()`, you can append specified text to the paragraph at the given index, which must be 0 or greater. This capability is useful in scenarios where existing paragraphs need to be updated with supplementary information.

```java
// Append text to the existing paragraph
textArea.appendToParagraph(0, " Additional content for the first paragraph.");
```

### Fetching all paragraphs

The `getParagraphs()` method retrieves all the paragraphs currently in the `TextArea`, providing a structured view of the text content. This method is particularly useful for extracting and processing the text entered by users.

```java
// Add multiple paragraphs
textArea.addParagraph("First paragraph.");
textArea.addParagraph("Second paragraph.");

// Fetch and print all paragraphs
List<String> paragraphs = textArea.getParagraphs();
System.out.println("Paragraphs: " + paragraphs);
```

## Validation

### Line count and paragraph length

The `TextArea` component lets you control input by limiting lines and paragraph length. This is helpful for apps that need structured text input, like forms or document editors.

**setLineCountLimit()** - Sets the maximum number of lines allowed. When reached, no more lines can be added, keeping text within a defined space and avoiding excessive scrolling.

**setParagraphLengthLimit()** - Sets the maximum characters per line in a paragraph. This keeps text readable and consistently structured. When the limit is reached, text wraps to the next line if wrapping is enabled.

Here’s an example of a feedback form that uses the `TextArea` component to enforce a maximum number of lines and character limits per paragraph, ensuring concise and structured user input:

<ComponentDemo 
path='/webforj/textarealinelimit?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-docs-samples/refs/heads/main/src/main/java/com/webforj/samples/views/textarea/TextAreaLineLimitView.java'
height = '250px'
/>

### Maximum and minimum length

`TextArea` includes validation features to check user input meets requirements. These help maintain accurate data and prevent invalid submissions.

**setMaxLength()** - Limits the total characters a user can enter, keeping input concise for fields like usernames or descriptions.

**setMinLength()** - Ensures users provide enough information before proceeding, useful for feedback forms or comments.

:::tip Key Difference
- **Line Count and Paragraph Length**: Control text structure visually - limiting number of lines and characters per line
- **Maximum and Minimum Length**: Control content amount - restricting total character count across all lines
:::

## Word wrap and line wrapping

The `TextArea` component provides text wrapping options to enhance readability and adapt to different layouts:

**setLineWrap()** - Toggles line wrapping on or off. When off, text continues on the same line with horizontal scrolling. When on, text wraps to the next line.

**setWrapStyle()** - Offers two wrapping styles:
- `WORD_BOUNDARIES`: Wraps at complete words for natural reading
- `CHARACTER_BOUNDARIES`: Wraps at individual characters for precise formatting control

These options complement the line count and length limits by determining how text flows when it reaches boundaries.

<ComponentDemo 
path='/webforj/textareawrap?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-docs-samples/refs/heads/main/src/main/java/com/webforj/samples/views/textarea/TextAreaWrapView.java'
height = '150px'
/>

## Predicted text

Save typing effort and reduce errors with smart text suggestions that appear as users type. Predicted text helps users complete their input faster while maintaining accuracy.

As the user enters text, predictions are displayed based on the current input. These predictions can be accepted by pressing the `Tab` or `ArrowRight` key, allowing for quicker text entry. If users choose to ignore the suggestions, they can continue typing without interruption.

:::info Prediction Not Found
If no suitable prediction is found, the text remains unchanged.
:::

<ComponentDemo 
path='/webforj/textareapredictedtext?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-docs-samples/refs/heads/main/src/main/java/com/webforj/samples/views/textarea/TextAreaPredictedTextView.java'
height = '125px'
/>

## Placeholder text

Placeholder text is also supported by webforJ's `TextArea`, which provides users with contextual hints about the type of input expected. This text is displayed within the `TextArea` when it's empty and disappears as the user begins typing.

```java
TextArea textArea = new TextArea("Feedback", "", "Enter your feedback here...");
```

## Parts and CSS properties

<TableBuilder tag={require('@site/docs/components/_dwc_control_map.json').TextArea} />
