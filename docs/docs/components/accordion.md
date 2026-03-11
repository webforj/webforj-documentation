---
title: Accordion
sidebar_position: 1
sidebar_class_name: new-content
---

<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-accordion" />
<DocChip chip='name' label="dwc-accordion-panel" />
<DocChip chip='since' label='25.12' />
<JavadocLink type="accordion" location="com/webforj/component/accordion/AccordionPanel" top='true'/>

The `Accordion` component provides a vertically stacked set of collapsible panels. Each panel consists of a clickable header that toggles the visibility of its body content. An `AccordionPanel` can be used independently as a standalone disclosure section, or grouped inside an `Accordion` to coordinate expand and collapse behavior across multiple panels.

Accordions are well-suited for FAQs, settings pages, step-by-step instructions, and any layout where showing all content at once would create an overwhelming or cluttered experience.

<!-- INTRO_END -->

## AccordionPanel {#accordion-panel}

`AccordionPanel` is the core building block of the accordion system. Pass a title string to the constructor to set the panel's header label, then add child components to populate its body. A panel works on its own without any surrounding `Accordion` group.

```java
AccordionPanel panel = new AccordionPanel("Section Title");
panel.add(new Paragraph("Body content goes here."));
```

{/* TODO: insert AccordionBasicView demo */}

### Opening and closing

The open/closed state of a panel can be controlled programmatically at any time:

```java
panel.open();                      // Expands the panel
panel.close();                     // Collapses the panel
boolean isOpen = panel.isOpened(); // Returns true if currently expanded
```

## Accordion groups {#accordion-groups}

Wrapping multiple `AccordionPanel` instances inside an `Accordion` creates a coordinated group. By default the group uses **single mode**: opening one panel automatically collapses all others, keeping only one section visible at a time.

```java
AccordionPanel panel1 = new AccordionPanel("What is webforJ?");
AccordionPanel panel2 = new AccordionPanel("How do grouped panels work?");
AccordionPanel panel3 = new AccordionPanel("Can I have multiple groups?");

Accordion accordion = new Accordion(panel1, panel2, panel3);
```

{/* TODO: insert AccordionGroupView demo */}

### Multiple mode

When single-mode behavior is too restrictive, multiple mode allows any number of panels to be expanded simultaneously. This is useful when users need to compare the content of several sections at once.

```java
accordion.setMultiple(true);
```

With multiple mode active, all panels in the group can be expanded or collapsed at once using the bulk methods:

```java
accordion.openAll();    // Expands every panel in the group
accordion.closeAll();   // Collapses every panel in the group
```

{/* TODO: insert AccordionMultipleView demo */}

:::info Single mode restriction
`openAll()` and `closeAll()` only apply when `setMultiple(true)` has been called. In single mode, only one panel can be open at a time regardless of programmatic calls.
:::

## Disabled state {#disabled-state}

Individual panels can be disabled to prevent user interaction while still remaining visible in the layout. A disabled panel that was already open remains visible but can no longer be collapsed by the user. Disabling the `Accordion` group applies the disabled state to all of its panels at once.

```java
// Disable a single panel
panel.setEnabled(false);

// Disable all panels in the group
accordion.setEnabled(false);
```

{/* TODO: insert AccordionDisabledView demo */}

## Custom header {#custom-header}

By default, a panel's header renders its title as plain text. The header slot replaces that text with any component or combination of components, making it straightforward to include icons, badges, status indicators, or other rich markup alongside the panel label.

```java
FlexLayout headerContent = FlexLayout.create()
    .horizontal()
    .build()
    .setSpacing("var(--dwc-space-s)");

headerContent.add(FeatherIcon.SETTINGS.create(), new Span("Custom Header with Icon"));
panel.addToHeader(headerContent);
```

:::info Label replacement
Content added via `addToHeader()` replaces the default label text entirely. To keep a visible text label alongside custom content, include a `Span` within the slotted layout as shown above.
:::

## Custom icon {#custom-icon}

The expand/collapse indicator defaults to a chevron. `setIcon()` replaces it with any component. Passing `null` restores the original default chevron.

```java
// Replace the default chevron with a plus icon
panel.setIcon(FeatherIcon.PLUS.create());

// Restore the default chevron
panel.setIcon(null);
```

{/* TODO: insert AccordionSlotsView demo */}

## Nested accordions {#nested-accordions}

Accordions can be nested inside other accordion panels, which is useful for representing hierarchical content such as categorized settings or multi-level navigation. An inner `Accordion` is added to an outer `AccordionPanel` as any other child component.

```java
AccordionPanel innerA = new AccordionPanel("Inner Panel A");
AccordionPanel innerB = new AccordionPanel("Inner Panel B");
Accordion innerAccordion = new Accordion(innerA, innerB);

AccordionPanel outer = new AccordionPanel("Outer Panel");
outer.add(innerAccordion);
```

{/* TODO: insert AccordionNestedView demo */}

## Events {#events}

`AccordionPanel` fires events at each stage of the toggle lifecycle. `AccordionPanelToggleEvent` fires immediately before the state changes, and its `isOpened()` method returns the state the panel is transitioning to rather than the current one. `AccordionPanelOpenEvent` and `AccordionPanelCloseEvent` fire after the panel has fully opened or closed respectively.

{/* TODO: insert AccordionEventsView demo */}

## Styling {#styling}

<TableBuilder name="AccordionPanel" />