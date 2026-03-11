---
sidebar_position: 1
title: Accordion
---

<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-accordion" />
<DocChip chip='name' label="dwc-accordion-panel" />
<DocChip chip='since' label='25.12' />
<JavadocLink type="accordion" location="com/webforj/component/accordion/Accordion" top='true'/>
<!-- vale off -->
<JavadocLink type="accordion" location="com/webforj/component/accordion/AccordionPanel"/>
<!-- vale on -->

The `Accordion` component provides a vertically stacked set of collapsible panels. Each panel has a clickable header that toggles the visibility of its body content. An `AccordionPanel` can be used as a standalone disclosure section, or grouped inside an `Accordion` to coordinate expand and collapse behavior across multiple panels.

<!-- INTRO_END -->

:::tip When to use an accordion
Accordions work well for FAQs, settings pages, and step-by-step flows where revealing all content at once would create an overwhelming layout. If sections are equally important and users benefit from seeing them simultaneously, consider [tabs](/docs/components/tabbedpane) instead.
:::

## AccordionPanel {#accordion-panel}

`AccordionPanel` is the core building block of the accordion system. Pass a label string to the constructor to set the header text, then add child components to populate the body. A panel works on its own without any surrounding `Accordion` group.

```java
// Label only — add body content separately
AccordionPanel panel = new AccordionPanel("Section Title");
panel.add(new Paragraph("Body content goes here."));

// Label with body content in the constructor
AccordionPanel panel = new AccordionPanel("Section Title", new Paragraph("Body content goes here."));
```

<!-- vale off -->
<ComponentDemo
path='/webforj/basic'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/accordion/AccordionBasicView.java'
height='500px'
/>
<!-- vale on -->

### Opening and closing {#opening-and-closing}

Control the open/closed state programmatically at any time:

```java
panel.open();                      // Expands the panel
panel.close();                     // Collapses the panel
boolean isOpen = panel.isOpened(); // Returns true if currently expanded
```

Use `setLabel()` to change the header text after construction. `setText()` is an alias for the same operation:

```java
panel.setLabel("Updated Label");
```

## Accordion groups {#accordion-groups}

Wrapping multiple `AccordionPanel` instances inside an `Accordion` creates a coordinated group. By default the group uses **single mode**: opening one panel automatically collapses all others, keeping only one section visible at a time.

```java
AccordionPanel panel1 = new AccordionPanel("What is webforJ?");
AccordionPanel panel2 = new AccordionPanel("How do grouped panels work?");
AccordionPanel panel3 = new AccordionPanel("Can I have multiple groups?");

Accordion accordion = new Accordion(panel1, panel2, panel3);
```

<!-- vale off -->
<ComponentDemo
path='/webforj/group'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/accordion/AccordionGroupView.java'
height='400px'
/>
<!-- vale on -->

### Multiple mode {#multiple-mode}

Multiple mode allows any number of panels to remain expanded simultaneously. This is useful when users need to compare the content of several sections at once.

```java
accordion.setMultiple(true);
```

With multiple mode active, all panels in the group can be expanded or collapsed at once using the bulk methods:

```java
accordion.openAll();    // Expands every panel in the group
accordion.closeAll();   // Collapses every panel in the group
```

<!-- vale off -->
<ComponentDemo
path='/webforj/multiple'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/accordion/AccordionMultipleView.java'
height='500px'
/>
<!-- vale on -->

:::info Single mode restriction
`openAll()` is only available when multiple mode is enabled. Calling it in single mode has no effect. `closeAll()` works in both modes.
:::

<!-- vale off -->
## Disabled state {#disabled-state}
<!-- vale on -->

Individual panels can be disabled to prevent user interaction while still remaining visible. A disabled panel that was already open remains expanded, but its header can no longer be clicked to collapse it. Disabling the `Accordion` group applies the disabled state to all contained panels at once.

```java
// Disable a single panel
panel.setEnabled(false);

// Disable all panels in the group
accordion.setEnabled(false);
```

<!-- vale off -->
<ComponentDemo
path='/webforj/disabled'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/accordion/AccordionDisabledView.java'
height='600px'
/>
<!-- vale on -->

## Custom header {#custom-header}

A panel's header renders its label as plain text by default. Use `addToHeader()` to replace that text with any component or combination of components, making it straightforward to include icons, badges, status indicators, or other rich markup alongside the panel label.

```java
FlexLayout headerContent = FlexLayout.create()
    .horizontal()
    .build()
    .setSpacing("var(--dwc-space-s)");

headerContent.add(FeatherIcon.SETTINGS.create(), new Span("Custom Header with Icon"));
panel.addToHeader(headerContent);
```

:::info Label replacement
Content added via `addToHeader()` fully replaces the default label text. To keep visible text alongside custom content, include a `Span` within the slotted layout as shown above.
:::

## Custom icon {#custom-icon}

The expand/collapse indicator defaults to a chevron. `setIcon()` replaces it with any component. Passing `null` restores the default chevron. `getIcon()` returns the currently set icon, or `null` if the default chevron is in use.

```java
// Replace the default chevron with a plus icon
panel.setIcon(FeatherIcon.PLUS.create());

// Restore the default chevron
panel.setIcon(null);
```

<!-- vale off -->
<ComponentDemo
path='/webforj/slots'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/accordion/AccordionSlotsView.java'
height='500px'
/>
<!-- vale on -->

## Nested accordions {#nested-accordions}

Accordions can be nested inside other accordion panels, which is useful for representing hierarchical content such as categorized settings or multi-level navigation. Add an inner `Accordion` to an outer `AccordionPanel` as any other child component.

```java
AccordionPanel innerA = new AccordionPanel("Inner Panel A");
AccordionPanel innerB = new AccordionPanel("Inner Panel B");
Accordion innerAccordion = new Accordion(innerA, innerB);

AccordionPanel outer = new AccordionPanel("Outer Panel");
outer.add(innerAccordion);
```

<!-- vale off -->
<ComponentDemo
path='/webforj/nested'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/accordion/AccordionNestedView.java'
height='550px'
/>
<!-- vale on -->

## Events {#events}

`AccordionPanel` fires events at each stage of the toggle lifecycle:

| Event | Fires | Notes |
|-------|-------|-------|
| `AccordionPanelToggleEvent` | Before the state changes | `e.isOpened()` returns `true` when the panel is about to open, `false` when about to close |
| `AccordionPanelOpenEvent` | After the panel has fully opened | — |
| `AccordionPanelCloseEvent` | After the panel has fully closed | — |

```java
panel.onToggle(e -> {
    // Fires before the panel changes state.
    // e.isOpened() reflects the state being transitioned to, not the current state.
    String direction = e.isOpened() ? "opening" : "closing";
});

panel.onOpen(e -> {
    // Fires after the panel is fully open.
});

panel.onClose(e -> {
    // Fires after the panel is fully closed.
});
```

<!-- vale off -->
<ComponentDemo
path='/webforj/events'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/accordion/AccordionEventsView.java'
height='275px'
/>
<!-- vale on -->

## Styling {#styling}

<TableBuilder name="AccordionPanel" />
