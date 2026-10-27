---
title: Inspecting Components
sidebar_position: 3
description: Browse the component tree webforJ built, select components from the page, and change their properties while the app runs.
---

The Inspector shows the component tree that your Java code built. A `Composite` appears as the class you wrote, holding the children you gave it in the order webforJ holds them, so the structure in craftforJ matches the structure in your source.

![The component tree with a component selected and highlighted in the running app](/img/craftforj/inspector/tree-selection.png#rounded-border)

## Selecting a component {#selecting-a-component}

To select a component from the page, press <kbd>Alt</kbd> + <kbd>Shift</kbd> + <kbd>C</kbd> and click it. craftforJ selects the matching node in the tree. Hovering a node in the tree does the reverse and highlights that component in the page, so you can move between the screen and the tree in either direction.

<div class="videos-container">
  <video controls preload="metadata">
    <source src="https://cdn.webforj.com/webforj-documentation/video/craftforJ/pick-mode.mp4" type="video/mp4" />
  </video>
</div>

To search the tree, press <kbd>Cmd/Ctrl</kbd> + <kbd>F</kbd>. Wrapping a term in slashes treats it as a regular expression. Right-clicking a node opens the actions available for it. You can open its source or hand it to the [assistant](/docs/craftforj/ai).

## Reading and changing properties {#reading-and-changing-properties}

Selecting a component fills the sidebar with its properties, grouped by what they affect. Which properties a component offers depends on the component, and some of them are read-only. Properties that don't read well as plain text get an editor suited to their value instead. Changing a value takes effect in the running app immediately.

:::info Live edits don't modify your files
A property edit changes the app in front of you and nothing else. Getting it into your source is a separate step that you carry out deliberately, described in [Writing changes to source](/docs/craftforj/source-changes).
:::

<div class="videos-container">
  <video controls preload="metadata">
    <source src="https://cdn.webforj.com/webforj-documentation/video/craftforJ/property-edit.mp4" type="video/mp4" />
  </video>
</div>

## Viewing the source of a component {#viewing-the-source-of-a-component}

You can trace any component back to the Java that built it. By default, the source opens in craftforJ as read-only, positioned at the line that created the component. You can configure craftforJ to open it in your editor instead, at the same line. When a component can't be traced to a line, craftforJ reports that rather than opening an empty viewer.

![The source viewer positioned at the line that created the selected component](/img/craftforj/inspector/source-viewer.png#rounded-border)

## Removing a component <DocChip chip='since' label='26.03' /> {#removing-a-component}

Right-clicking a component in the tree and choosing **Remove Component** offers two ways to take it out. One removes it from the app in front of you, and the other removes it from your project.

<!-- TODO: add recording -->

### Removing from the browser only {#removing-from-the-browser-only}

**Remove from browser only** takes the component out of the running app and leaves your java source as it is. Use it to see how a layout looks without a particular component before you commit to anything.

The removal is temporary. The component comes back the next time the app builds that view from your source, such as after a page reload.

### Removing from source {#removing-from-source}

**Remove from source** edits the Java file that created the component, so it stays removed after the app rebuilds. The removal is written straight away rather than added to the [pending changes](/docs/craftforj/source-changes#pending-changes), so there's no review step before it reaches your file.

Writing Java makes your app rebuild and restart, the same as [applying changes](/docs/craftforj/source-changes#after-you-apply). With [live reload](/docs/configuration/deploy-reload/overview) configured, that happens on its own. Without it, restart the app yourself.

For example, take a form with a **Save** and a **Cancel** button, where **Save** has a theme and a click listener:

```java
import com.webforj.component.button.Button;
import com.webforj.component.button.ButtonTheme;
import com.webforj.component.layout.flexlayout.FlexLayout;

public class ContactFormView {
  public ContactFormView(FlexLayout layout) {
    layout.setSpacing("1em");
    Button save = new Button("Save");
    save.setTheme(ButtonTheme.PRIMARY);
    save.onClick(event -> {
      save.setEnabled(false);
    });
    Button cancel = new Button("Cancel");
    layout.add(save, cancel);
  }
}
```

Selecting **Save** and choosing **Remove from source** leaves the following. The declaration, the theme, the listener, and the button's place in the `add()` call are gone, along with the `ButtonTheme` import nothing else uses. After the app rebuilds, **Cancel** is still there:

```java
import com.webforj.component.button.Button;
import com.webforj.component.layout.flexlayout.FlexLayout;

public class ContactFormView {
  public ContactFormView(FlexLayout layout) {
    layout.setSpacing("1em");
    Button cancel = new Button("Cancel");
    layout.add(cancel);
  }
}
```

A removal covers the component and nothing beyond it:

- Removing a container also removes the components it contains.
- Code that only existed to create, configure, and attach the component is cleaned up. That includes fields and imports nothing else uses.
- Code that's still needed elsewhere stays. If another part of the class still reads the component, such as another component's listener, craftforJ only detaches it and leaves the code that creates and configures it.
- Removing an instance of a reusable component, such as a `Composite` you wrote, removes that one usage. The class that defines it isn't deleted or changed.

craftforJ only writes the file if the result still compiles. Files are never deleted.

### When removal isn't available {#when-removal-isnt-available}

**Remove from source** is only offered when writing Java is allowed in both the app's [configuration](/docs/craftforj/configuration#feature-flags) and the **Java source changes** setting in craftforJ. It isn't offered for components built in Kotlin, or for components craftforJ can't trace to a line of source.

Some components can't be removed from source at all:

- Routes, which the router places according tp their `@Route`
- The frame
- Components that another component builds as part of itself. Remove the outer component instead.

craftforJ can also refuse a removal when it can't find the component's source, or can't tell which code to remove. For example, when one line creates more than one component of the same type, the line alone doesn't say which one you picked.

A refused removal leaves your files as they were and reports the reason:

```
Could not remove FlexLayout from source: FlexLayout is a part ContactCard builds for itself, remove that instead
```

### Undoing a removal {#undoing-a-removal}

To bring back a component you removed from source, press <kbd>Cmd/Ctrl</kbd> + <kbd>Z</kbd> in craftforJ. This restores the file as it was before the removal. If you've edited the file in your own editor since then, the undo is blocked rather than overwriting your edits. See [Undoing and redoing](/docs/craftforj/source-changes#undoing-and-redoing) for how undo works and what to do when it's blocked.