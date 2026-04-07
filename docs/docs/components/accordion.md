---
sidebar_position: 1
title: Accordion
sidebar_class_name: new-content
---

<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-accordion" />
<DocChip chip='name' label="dwc-accordion-panel" />
<DocChip chip='since' label='25.12' />
<JavadocLink type="accordion" location="com/webforj/component/accordion/Accordion" top='true'/>

The `Accordion` component provides a vertically stacked set of collapsible panels. Each panel has a clickable header that toggles the visibility of its body content. An `AccordionPanel` can be used as a standalone disclosure section, or grouped inside an `Accordion` to coordinate expand and collapse behavior across multiple panels.

<!-- INTRO_END -->

:::tip When to use an accordion
Accordions work well for FAQs, settings pages, and step-by-step flows where revealing all content at once would create an overwhelming layout. If sections are equally important and users benefit from seeing them simultaneously, consider [tabs](/docs/components/tabbedpane) instead.
:::

## `AccordionPanel` {#accordion-panel}

`AccordionPanel` is the core building block of the accordion system. Pass a label string to the constructor to set the header text, then add child components to populate the body. A panel works on its own without any surrounding `Accordion` group, making it a useful lightweight disclosure widget when you just need a single collapsible section. The no-argument constructor is also available when you prefer to configure the panel entirely after construction.

```java
// Label only - add body content separately
AccordionPanel panel = new AccordionPanel("Section Title");
panel.add(new Paragraph("Body content goes here."));

// Label and body content passed directly in the constructor
AccordionPanel panel = new AccordionPanel("Title", new Paragraph("Body content."));
```

<!-- vale off -->
<ComponentDemo
path='/webforj/accordionbasic'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/accordion/AccordionBasicView.java'
height='500px'
/>
<!-- vale on -->

### Opening and closing {#opening-and-closing}

Control the open/closed state programmatically at any time. `isOpened()` is useful when you need to read the current state before deciding what to do. For example, you might toggle a panel to the opposite state or conditionally showing or hiding other parts of the UI.

```java
// Expand the panel
panel.open();

// Collapse the panel
panel.close();                    

// Returns true if currently expanded
boolean isOpen = panel.isOpened();
```

Use `setLabel()` to update the header text after construction. `setText()` is an alias for the same operation, so the label can be kept in sync with dynamic data:

```java
panel.setLabel("Updated Label");
```

## Accordion groups {#accordion-groups}

Wrapping multiple `AccordionPanel` instances inside an `Accordion` creates a coordinated group. By default the group uses **single mode**: opening one panel automatically collapses all others, keeping only one section visible at a time. This default is intentional, it keeps the user focused on one piece of content and prevents the page from becoming visually overwhelming when panels have substantial body content.

Panels are constructed independently and passed to the `Accordion`, so you can configure each one before grouping them. Multiple separate `Accordion` instances can also exist on the same page—each group manages its own state independently, so expanding a panel in one group has no effect on another.

```java
AccordionPanel panel1 = new AccordionPanel("What is webforJ?");
AccordionPanel panel2 = new AccordionPanel("How do grouped panels work?");
AccordionPanel panel3 = new AccordionPanel("Can I have multiple groups?");

Accordion accordion = new Accordion(panel1, panel2, panel3);
```

<!-- vale off -->
<ComponentDemo
path='/webforj/accordiongroup'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/accordion/AccordionGroupView.java'
height='400px'
/>
<!-- vale on -->

### Multiple mode {#multiple-mode}

Multiple mode allows any number of panels to remain expanded simultaneously. This is useful when users need to compare the content of several sections at once, or when each panel is short enough that expanding several at once doesn't create a cluttered layout.

```java
accordion.setMultiple(true);
```

With multiple mode active, all panels in the group can be expanded or collapsed at once using the bulk methods:

```java
// Expand every panel in the group
accordion.openAll();

// Collapse every panel in the group
accordion.closeAll();   
```

<!-- vale off -->
<ComponentDemo
path='/webforj/accordionmultiple'
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

Individual panels can be disabled to prevent user interaction while still remaining visible. This is handy during loading states or when certain sections are conditionally unavailable, showing the panel structure without allowing premature interaction. A disabled panel that was already open remains expanded, but its header can no longer be clicked to collapse it. Disabling the `Accordion` group applies the disabled state to all contained panels at once, so you don't need to loop through panels individually.

```java
// Disable a single panel
panel.setEnabled(false);

// Disable all panels in the group
accordion.setEnabled(false);
```

<!-- vale off -->
<ComponentDemo
path='/webforj/accordiondisabled'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/accordion/AccordionDisabledView.java'
height='600px'
/>
<!-- vale on -->

## Customizing panels {#customizing-panels}

Beyond labels and basic open/close behavior, each `AccordionPanel` supports richer customization of both its header content and the expand/collapse icon.

### Custom header {#custom-header}

A panel's header renders its label as plain text by default. Use `addToHeader()` to replace that text with any component or combination of components, making it straightforward to include icons, badges, status indicators, or other rich markup alongside the panel label. This is particularly useful in dashboards or settings panels where each section header needs to convey extra context at a glance, such as an item count, a warning badge, or a completion status, without requiring the user to expand the panel first.

```java
FlexLayout headerContent = FlexLayout.create()
    .horizontal()
    .build()
    .setSpacing("var(--dwc-space-s)");

headerContent.add(FeatherIcon.SETTINGS.create(), new Span("Custom Header with Icon"));
panel.addToHeader(headerContent);
```

:::info Label replacement
Content added via `addToHeader()` fully replaces the default label text. `setLabel()` and `setText()` continue to work alongside `addToHeader()`, but since the header slot takes visual precedence, the label text won't be shown unless you include it explicitly in your slotted content.
:::

<!-- vale off -->
<ComponentDemo
path='/webforj/accordioncustomheader'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/accordion/AccordionCustomHeaderView.java'
height='300px'
/>
<!-- vale on -->

### Custom icon {#custom-icon}

The expand/collapse indicator defaults to a chevron that's visible in both the open and closed states. `setIcon()` replaces it with any [`Icon`](/docs/components/icon) component, useful for branded iconography or when a different visual metaphor fits the content better. Passing `null` restores the default chevron. `getIcon()` returns the currently set icon, or `null` if the default chevron is in use.

```java
// Replace the default chevron with a plus icon
panel.setIcon(FeatherIcon.PLUS.create());

// Restore the default chevron
panel.setIcon(null);
```

<!-- vale off -->
<ComponentDemo
path='/webforj/accordioncustomicon'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/accordion/AccordionCustomIconView.java'
height='200px'
/>
<!-- vale on -->

## Nested accordions {#nested-accordions}

Accordions can be nested inside other accordion panels, which is useful for representing hierarchical content such as categorized settings or multi-level navigation. Add an inner `Accordion` to an outer `AccordionPanel` as any other child component. Keep nesting shallow. One or two levels is usually enough. Deeper hierarchies tend to be harder to navigate and often signal that the content structure itself needs rethinking.

```java
AccordionPanel innerA = new AccordionPanel("Inner Panel A");
AccordionPanel innerB = new AccordionPanel("Inner Panel B");
Accordion innerAccordion = new Accordion(innerA, innerB);

AccordionPanel outer = new AccordionPanel("Outer Panel");
outer.add(innerAccordion);
```

<!-- vale off -->
<ComponentDemo
path='/webforj/accordionnested'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/accordion/AccordionNestedView.java'
height='550px'
/>
<!-- vale on -->

## Events {#events}

`AccordionPanel` fires events at each stage of the toggle lifecycle. The three event types cover different moments, so choose based on when your logic needs to run:

| Event | Fires |
|-------|-------|
| `AccordionPanelToggleEvent` | Before the state changes |
| `AccordionPanelOpenEvent` | After the panel has fully opened |
| `AccordionPanelCloseEvent` | After the panel has fully closed |

```java
panel.onToggle(e -> {
    // Fires before the panel changes state.
    // e.isOpened() reflects the state being transitioned to, not the current state.
    String direction = e.isOpened() ? "opening" : "closing";
});

panel.onOpen(e -> {
    // Fires after the panel is fully open — good for lazy-loading content.
});

panel.onClose(e -> {
    // Fires after the panel is fully closed — good for cleanup or summary updates.
});
```

## Styling {#styling}

<TableBuilder name={['Accordion', 'AccordionPanel']} />
