---
title: TextArea
sidebar_position: 130
---

<DocChip chip="shadow" />

<DocChip chip="name" label="dwc-textarea" />

<JavadocLink type="foundation" location="com/webforj/component/field/TextArea" top='true'/>

The `TextArea` component in webforJ offers a solution for multi-line text input. End-users can freely type and edit text, while developers can set reasonable boundaries using features like maximum character limits, paragraph structure, and validation rules.

Here's an example of a `TextArea` for entering multi-line text:

<ComponentDemo 
path='/webforj/textarea?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-docs-samples/refs/heads/main/src/main/java/com/webforj/samples/views/textarea/TextAreaView.java'
height = '300px'
/>

## Managing paragraphs

The `TextArea` component provides features for handling text paragraphs, making it ideal for applications that require document editing or structured text input.

Here’s a quick example of how to build up and manipulate paragraph content:

```java
TextArea textArea = new TextArea();

// Insert a paragraph at the beginning
textArea.addParagraph(0, "This is the first paragraph.");

// Append another paragraph to the end
textArea.addParagraph("Here’s a second paragraph.");

// Append additional content to the first paragraph
textArea.appendToParagraph(0, " This sentence continues the first one.");

// Remove the second paragraph
textArea.removeParagraph(1);

// Retrieve and print all current paragraphs
List<String> paragraphs = textArea.getParagraphs();
for (int i = 0; i < paragraphs.size(); i++) {
    System.out.println("Paragraph " + i + ": " + paragraphs.get(i));
}
```

## Validation

The `TextArea` component supports two complementary types of validation: structural constraints and content constraints.

**Structural constraints** focus on how the text is organized and visually laid out. For example:
- `setLineCountLimit(int maxLines)` restricts the number of lines allowed in the text area.
- `setParagraphLengthLimit(int maxCharsPerLine)` limits the number of characters per paragraph (or line), helping enforce readability or formatting standards.

**Content constraints**, on the other hand, deal with the overall amount of text entered, regardless of how it’s distributed:
- `setMaxLength(int maxChars)` caps the total number of characters allowed across all paragraphs.
- `setMinLength(int minChars)` enforces a minimum length, ensuring that enough content is provided.

The following demo allows users to adjust validation limits—such as maximum character count, paragraph length, and line count—in real time and see how the `TextArea` responds.
	
<ComponentDemo 
path='/webforj/textareavalidation?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-docs-samples/refs/heads/main/src/main/java/com/webforj/samples/views/textarea/TextAreaValidationView.java'
height = '550px'
/>

## Word wrap and Line wrapping

You can control whether text wraps or scrolls horizontally using `setLineWrap()`. When wrapping is disabled, lines continue horizontally beyond the visible area, requiring scrolling. When enabled, text automatically wraps to the next line when it reaches the edge of the component.

To further refine how wrapping behaves, `setWrapStyle()` lets you choose between two styles:
- `WORD_BOUNDARIES` wraps text at whole words, preserving natural reading flow.
- `CHARACTER_BOUNDARIES` wraps at individual characters, allowing tighter control over layout, especially in narrow or fixed-width containers.

These wrapping options work hand-in-hand with structural constraints like line count and paragraph length limits. While wrapping determines *how* text flows within the available space, the structural limits define *how much* space text is allowed to occupy. Together, they help maintain both visual structure and user input boundaries.

<ComponentDemo 
path='/webforj/textareawrap?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-docs-samples/refs/heads/main/src/main/java/com/webforj/samples/views/textarea/TextAreaWrapView.java'
height = '400px'
/>

## Predicted text

The `TextArea` component supports smart text suggestions to help users type faster and with fewer errors. As users enter text, predictive suggestions appear based on the current input, allowing them to complete common or expected phrases.

Predictions can be accepted by pressing the `Tab` or `ArrowRight` key, inserting the suggested text into the input seamlessly. If no suitable prediction is available at a given moment, the input remains unchanged, and the user can continue typing without interruption—ensuring the feature never gets in the way.

This predictive behavior enhances both speed and accuracy, especially in repetitive input scenarios or applications where consistency of phrasing is important.

<ComponentDemo 
path='/webforj/textareapredictedtext?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-docs-samples/refs/heads/main/src/main/java/com/webforj/samples/views/textarea/TextAreaPredictedTextView.java'
height = '400px'
/>

:::info
This demo uses the [Datamuse API](https://datamuse.com/) to provide word suggestions based on the user’s input. The quality and relevance of the predictions depend entirely on the API's dataset and scoring mechanism. It doesn't use AI models or large language models (LLMs); the suggestions are generated from a lightweight, rule-based engine focused on lexical similarity.
:::

## Read-Only and Disabled state

The `TextArea` component can be set to either read-only or disabled to control user interaction.

A **read-only** text area allows users to view and select the content, but not edit it. This is useful for displaying dynamic or pre-filled information that should remain unchanged.

A **disabled** text area, on the other hand, blocks all interaction—including focus and text selection—and is typically styled as inactive or grayed out.

Use read-only mode when the content is relevant but immutable, and disabled mode when the input isn't currently applicable or should be temporarily inactive.

<ComponentDemo 
path='/webforj/textareastates?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-docs-samples/refs/heads/main/src/main/java/com/webforj/samples/views/textarea/TextAreaStatesView.java'
height = '300px'
/>

## Styling

### Shadow parts

These are the various parts of the [shadow DOM](../glossary#shadow-dom) for the component, which will be required when styling via CSS is desired.

<TableBuilder tag={require('@site/docs/components/_dwc_control_map.json').TextArea} table='parts' />

### Slots

Listed below are the slots available for utilization within the component. These slots act as placeholders within the component that control where the children of a customized element should be inserted within the shadow tree.

<TableBuilder tag={require('@site/docs/components/_dwc_control_map.json').TextArea} table='slots'/>

### CSS properties

These are the various CSS properties that are used in the component, with a short description of their use.

<TableBuilder tag={require('@site/docs/components/_dwc_control_map.json').TextArea} table='properties'/>

### Reflected attributes

The reflected attributes of a component will be shown as attributes in the rendered HTML element for the component in the DOM. This means that styling can be applied using these attributes.

<TableBuilder tag={require('@site/docs/components/_dwc_control_map.json').TextArea} table="reflects"/>

### Dependencies

This component relies on the following components - see the related article for more detailed styling information:

<TableBuilder tag='dwc-button' table="dependencies"/>