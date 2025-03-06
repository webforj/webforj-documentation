---
title: TextArea
---

<DocChip chip="shadow" />

<DocChip chip="name" label="dwc-textarea" />

<JavadocLink type="foundation" location="com/webforj/component/field/TextArea" top='true'/>

The `TextArea` component in webforJ provides a versatile and robust solution for handling multi-line text input. It's ideal for apps requiring structured or large-volume text entry, such as forms, comments, or document editing.

## Basics

The `TextArea` provides features for entering and managing multiple lines of text. Its rich feature set includes handling paragraphs, enforcing text constraints, and advanced input configurations like predicted text and line wrapping.

Here’s an example of a `TextArea` for entering multi-line text:

<ComponentDemo 
path='/webforj/textarea?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-docs-samples/refs/heads/main/src/main/java/com/webforj/samples/views/textarea/TextAreaView.java'
height = '200px'
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

The `removeParagraph()` method enables you to delete specific paragraphs from the `TextArea` at the specified index.

```java
// Add multiple paragraphs
textArea.addParagraph("Paragraph 1");
textArea.addParagraph("Paragraph 2");
textArea.addParagraph("Paragraph 3");

// Remove the second paragraph
textArea.removeParagraph(1);
```
This demo showcases paragraph management capability within a `TextArea`, allowing users to dynamically add or remove paragraphs at specified positions:

<ComponentDemo 
path='/webforj/textareaparagraphmanager?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-docs-samples/refs/heads/main/src/main/java/com/webforj/samples/views/textarea/TextAreaParagraphManagerView.java'
height = '325px'
/>

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

## Line count and paragraph length

The `TextArea` component provides robust controls to ensure input consistency by limiting the number of lines and the length of individual paragraphs. These restrictions are useful in apps where structured text input is essential, such as in form fields, document editors, or any interface requiring precise formatting.

**setLineCountLimit()** - This method specifies the maximum number of lines that can be entered. Once the limit is reached, additional lines are prevented, ensuring the text remains within a controlled vertical space. This helps avoid excessive scrolling and maintains clean UI layouts.

**setParagraphLengthLimit()** - This method enforces a maximum character count per line within a paragraph. This ensures that each line of text remains readable and adheres to a consistent structure. When the character limit for a line is reached, additional input wraps to the next line if line wrapping is enabled. This is especially useful for maintaining clarity and preventing overly long lines of text.

Here’s an example of a feedback form that uses the `TextArea` component to enforce a maximum number of lines and character limits per paragraph, ensuring concise and structured user input:

<ComponentDemo 
path='/webforj/textarealinelimit?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-docs-samples/refs/heads/main/src/main/java/com/webforj/samples/views/textarea/TextAreaLineLimitView.java'
height = '250px'
/>

## Maximum and minimum length

`TextArea` includes built-in validation features to ensure user input adheres to specific requirements. These features are essential for maintaining data integrity, enhancing user experience, and preventing invalid or incomplete submissions.

The `maxLength` property allows you to define the total number of characters a user can enter. This ensures that input remains concise and within acceptable limits, making it particularly useful for fields like usernames, descriptions, or other constrained text inputs. On the other hand, the `minLength` property enforces a baseline character count, ensuring that users provide enough information before proceeding. This is ideal for fields requiring a minimum level of detail, such as feedback forms or comments.

:::tip Key Difference
- **Line Count and Paragraph Length**: These focus on structuring text visually. `setLineCountLimit()` restricts the number of lines, while `setParagraphLengthLimit()` limits characters per line, wrapping text if necessary.
- **Maximum and Minimum Length**: These focus on content validation. `setMaxLength()` and `setMinLength()` control the total number of characters allowed in the TextArea across all lines.
:::

## Word wrap and line wrapping 

webforJ's `TextArea` component provides flexible text wrapping options to enhance readability and adapt to various layout requirements. These features ensure that long text inputs are displayed effectively without compromising usability or aesthetics.

Line wrapping can be toggled on or off, allowing developers to control whether text continues on the same line with horizontal scrolling or wraps onto the next line. This is particularly useful in scenarios where maintaining a single-line format or maximizing visible text is crucial.

Additionally, the `TextArea` offers two wrapping styles to tailor the user experience further. With `WORD_BOUNDARIES`, text wraps at complete words, ensuring clean and natural breaks for improved readability. Alternatively, `CHARACTER_BOUNDARIES` wraps text at individual characters, offering precise control for fields with strict formatting requirements or technical data entry.

<ComponentDemo 
path='/webforj/textareawrap?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-docs-samples/refs/heads/main/src/main/java/com/webforj/samples/views/textarea/TextAreaWrapView.java'
height = '150px'
/>

## Predicted text

The predicted text feature elevates the user experience by providing dynamic text suggestions as users type.

As the user enters text, predictions are displayed based on the current input. These predictions can be accepted by pressing the `Tab` or `ArrowRight` key, allowing for quicker and more intuitive text entry. If users choose to ignore the suggestions, they can continue typing without interruption.

This feature not only enhances productivity but also improves the accuracy of text input, making it an invaluable addition to apps that benefit from streamlined user interactions.

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

## Scroll bars and selection ranges

To enhance usability and navigation within the `TextArea`, scroll bars and selection ranges provide essential capabilities for handling large volumes of text efficiently.

- **Scroll Bars**: The `TextArea` supports both horizontal and vertical scroll bars, ensuring users can navigate content seamlessly. Horizontal scrolling is ideal for lines exceeding the component’s width, while vertical scrolling accommodates lengthy text. Developers can enable these scroll bars using the `setHorizontalScroll()` and `setVerticalScroll()` methods, providing flexibility for different layout requirements and content types.

- **Selection Ranges**: For precise text manipulation, the `TextArea` allows highlighting specific ranges of text. This feature is particularly useful for scenarios like text editing or searching within the input. The `setSelectionRange()` method enables developers to programmatically define the start and end points of a selection, allowing users to interact with the highlighted text effectively.

Together, these features ensure smooth navigation and improved interaction, making the `TextArea` well-suited for handling extensive or complex text input scenarios.

## Parts and CSS properties

<TableBuilder tag={require('@site/docs/components/_dwc_control_map.json').TextArea} />
