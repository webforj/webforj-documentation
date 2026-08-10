---
title: Inspecting Components
sidebar_position: 3
description: Browse the component tree webforJ built, select components from the page, and change their properties while the app runs.
---

The Inspector shows the component tree your Java code built. A `Composite` appears as the class you wrote, holding the children you gave it in the order webforJ holds them, so the structure in craftforJ matches the structure in your source.

![The component tree with a component selected and highlighted in the running app](/img/craftforj/inspector/tree-selection.png#rounded-border)

## Selecting a component {#selecting-a-component}

To select a component from the page, press <kbd>Alt</kbd> + <kbd>Shift</kbd> + <kbd>C</kbd> and click it. craftforJ selects the matching node in the tree. Hovering a node in the tree does the reverse and highlights that component in the page, so you can move between the screen and the tree in either direction.

<div class="videos-container">
  <video controls preload="metadata">
    <source src="https://cdn.webforj.com/webforj-documentation/video/craftforJ/pick-mode.mp4" type="video/mp4" />
  </video>
</div>

To search the tree, press <kbd>Cmd/Ctrl</kbd> + <kbd>F</kbd>. Wrapping a term in slashes treats it as a regular expression. Right-clicking a node opens the actions available for it, such as opening its source or handing it to the [assistant](./ai.md).

## Reading and changing properties {#reading-and-changing-properties}

Selecting a component fills the sidebar with its properties, grouped by what they affect. Which properties a component offers depends on the component, and some of them are read-only. Properties that don't read well as plain text get an editor suited to their value instead. Changing a value takes effect in the running app immediately.

:::info Live edits don't modify your files
A property edit changes the app in front of you and nothing else. Getting it into your source is a separate step that you carry out deliberately, described in [Writing changes to source](./source-changes.md).
:::

<div class="videos-container">
  <video controls preload="metadata">
    <source src="https://cdn.webforj.com/webforj-documentation/video/craftforJ/property-edit.mp4" type="video/mp4" />
  </video>
</div>

## Viewing the source of a component {#viewing-the-source-of-a-component}

You can trace any component back to the Java that built it. By default the source opens in craftforJ as read-only, positioned at the line that created the component. You can configure craftforJ to open it in your editor instead, at the same line. When a component can't be traced to a line, craftforJ tells you so rather than opening an empty viewer.

![The source viewer positioned at the line that created the selected component](/img/craftforj/inspector/source-viewer.png#rounded-border)
