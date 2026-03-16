---
sidebar_position: 40
title: View Transitions
sidebar_class_name: new-content
---

<!-- vale Google.Units = NO -->

<JavadocLink type="foundation" location="com/webforj/ViewTransition" top='true'/>

<DocChip chip='since' label='25.11' />
<DocChip chip='experimental' />

View transitions provide animated transitions when the [DOM](/docs/glossary#dom) changes, reducing visual jarring and maintaining spatial context during navigation or content updates. webforJ integrates with the browser's [View Transition API](https://developer.mozilla.org/en-US/docs/Web/API/View_Transition_API) to handle the complexity of coordinating animations between old and new states.

<ComponentDemo
  path='/webforj/viewtransitionchat?'
  javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/viewtransitions/ViewTransitionChatView.java'
  cssURL='/css/viewtransitions/chat.css'
  height='450px'
/>

:::warning Experimental API
This API is marked as experimental since 25.11 and may change in future releases. The API signature, behavior, and performance characteristics are subject to modification.
:::

## Basic usage {#basic-usage}

To create a view transition, use `Page.getCurrent().startViewTransition()`, which returns a builder for configuring the transition:

```java
Page.getCurrent().startViewTransition()
    .onUpdate(done -> {
        container.remove(oldView);
        container.add(newView);
        done.run();
    })
    .start();
```

The transition process captures a snapshot of the current state, applies your DOM changes in the `onUpdate` callback, then animates from the old snapshot to the new content. You must call `done.run()` to signal when your changes are complete.

:::warning The `onUpdate` callback is required
Calling `start()` without setting an update callback throws an `IllegalStateException`.
:::

## Applying transitions {#applying-transitions}

webforJ provides predefined transition types that you can apply to components entering or exiting the DOM:

| Constant | Effect |
|----------|--------|
| `ViewTransition.NONE` | No animation |
| `ViewTransition.FADE` | Crossfade between old and new content |
| `ViewTransition.SLIDE_LEFT` | Content flows left (like forward navigation) |
| `ViewTransition.SLIDE_RIGHT` | Content flows right (like back navigation) |
| `ViewTransition.SLIDE_UP` | Content flows upward |
| `ViewTransition.SLIDE_DOWN` | Content flows downward |
| `ViewTransition.ZOOM` | Old content shrinks away, new content grows in |
| `ViewTransition.ZOOM_OUT` | Old content grows away, new content shrinks in |

Use `enter()` to animate a component being added and `exit()` to animate a component being removed:

```java
// Animate a component entering the DOM
Page.getCurrent().startViewTransition()
    .enter(chatPanel, ViewTransition.ZOOM)
    .onUpdate(done -> {
        container.add(chatPanel);
        done.run();
    })
    .start();

// Animate a component exiting the DOM
Page.getCurrent().startViewTransition()
    .exit(chatPanel, ViewTransition.FADE)
    .onUpdate(done -> {
        container.remove(chatPanel);
        done.run();
    })
    .start();
```

## Shared component transitions {#shared-component-transitions}

Shared component transitions create a morphing effect where a component appears to transform from its position in the old view to its position in the new view. This is achieved by giving components the same transition name using the `setViewTransitionName()` method, available on any component that implements the <JavadocLink type="foundation" location="com/webforj/concern/HasStyle" code='true'>HasStyle</JavadocLink> interface.

```java
// In the card view
image.setViewTransitionName("blog-image");

// In the detail view - same name creates the morph
image.setViewTransitionName("blog-image");
```

When transitioning between these views, the browser animates the component between positions, creating a connected visual experience.

:::tip Use unique names
When working with lists or repeated components, include a unique identifier in the transition name. Each component requires its own distinct name to morph correctly to its corresponding component in the new view. Using the same name for multiple visible components causes undefined behavior.
:::

<ComponentDemo
  path='/webforj/viewtransitionmorph?'
  javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/viewtransitions/ViewTransitionMorphView.java'
  urls={[
    'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/viewtransitions/components/BlogCard.java',
    'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/viewtransitions/components/BlogDetail.java'
  ]}
  cssURL='/css/viewtransitions/morph.css'
  height='650px'
/>

### List reordering {#list-reordering}

A common use case for shared component transitions is animating list items when their order changes. By assigning a unique `view-transition-name` to each item, the browser automatically animates components to their new positions:

```java
// Each card gets a unique transition name based on its ID
card.setViewTransitionName("card-" + item.id());

// When shuffling, just update the DOM - the browser handles animation
Page.getCurrent().startViewTransition()
    .onUpdate(done -> {
        renderList();
        done.run();
    })
    .start();
```

<ComponentDemo
  path='/webforj/viewtransitionshuffle?'
  javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/viewtransitions/ViewTransitionShuffleView.java'
  urls={[
    'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/viewtransitions/components/ShuffleCard.java'
  ]}
  cssURL='/css/viewtransitions/shuffle.css'
  height='550px'
/>

## Custom CSS animations {#custom-css-animations}

For full control over animations, you can define custom CSS keyframes. webforJ appends `-enter` or `-exit` suffixes to your transition names, which you use to target the view transition pseudo-elements:

```css
/* Define keyframes for entering components */
@keyframes flip-enter {
  from {
    opacity: 0;
    transform: perspective(1000px) rotateX(-90deg);
  }
  to {
    opacity: 1;
    transform: perspective(1000px) rotateX(0deg);
  }
}

/* Apply to the view transition pseudo-element */
::view-transition-new(flip-in-enter) {
  animation: flip-enter 450ms cubic-bezier(0.34, 1.56, 0.64, 1);
  transform-origin: top center;
}

::view-transition-old(flip-in-enter) {
  display: none;
}
```

Reference your custom animation by passing its name (without the suffix) to `enter()` or `exit()`:

```java
// Use "flip-in" - webforJ adds "-enter" suffix automatically
Page.getCurrent().startViewTransition()
    .enter(notification, "flip-in")
    .onUpdate(done -> {
        stage.add(notification);
        done.run();
    })
    .start();

// Use "blur-out" for exit - webforJ adds "-exit" suffix
Page.getCurrent().startViewTransition()
    .exit(notification, "blur-out")
    .onUpdate(done -> {
        stage.remove(notification);
        done.run();
    })
    .start();
```

<ComponentDemo
  path='/webforj/viewtransitionenterexit?'
  javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/viewtransitions/ViewTransitionEnterExitView.java'
  cssURL='/css/viewtransitions/enterexit.css'
  height='400px'
/>

## CSS customization {#css-customization}

Each predefined transition type exposes CSS custom properties for fine-tuning:

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>Fade</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      | Variable | Default | Description |
      |----------|---------|-------------|
      | `--vt-fade-duration` | `200ms` | Animation duration |
      | `--vt-fade-easing` | `cubic-bezier(0.4, 0, 0.2, 1)` | Easing function |
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>Slide left</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      | Variable | Default | Description |
      |----------|---------|-------------|
      | `--vt-slide-left-duration` | `200ms` | Animation duration |
      | `--vt-slide-left-easing` | `cubic-bezier(0.4, 0, 0.2, 1)` | Easing function |
      | `--vt-slide-left-distance` | `30%` | Slide distance |
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>Slide right</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      | Variable | Default | Description |
      |----------|---------|-------------|
      | `--vt-slide-right-duration` | `200ms` | Animation duration |
      | `--vt-slide-right-easing` | `cubic-bezier(0.4, 0, 0.2, 1)` | Easing function |
      | `--vt-slide-right-distance` | `30%` | Slide distance |
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>Slide up</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      | Variable | Default | Description |
      |----------|---------|-------------|
      | `--vt-slide-up-duration` | `200ms` | Animation duration |
      | `--vt-slide-up-easing` | `cubic-bezier(0.4, 0, 0.2, 1)` | Easing function |
      | `--vt-slide-up-distance` | `30%` | Slide distance |
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>Slide down</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      | Variable | Default | Description |
      |----------|---------|-------------|
      | `--vt-slide-down-duration` | `200ms` | Animation duration |
      | `--vt-slide-down-easing` | `cubic-bezier(0.4, 0, 0.2, 1)` | Easing function |
      | `--vt-slide-down-distance` | `30%` | Slide distance |
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>Zoom</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      | Variable | Default | Description |
      |----------|---------|-------------|
      | `--vt-zoom-duration` | `200ms` | Animation duration |
      | `--vt-zoom-easing` | `cubic-bezier(0.4, 0, 0.2, 1)` | Easing function |
      | `--vt-zoom-scale` | `0.8` | Scale factor (old zooms out to this, new zooms in from this) |
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>Zoom out</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      | Variable | Default | Description |
      |----------|---------|-------------|
      | `--vt-zoom-out-duration` | `200ms` | Animation duration |
      | `--vt-zoom-out-easing` | `cubic-bezier(0.4, 0, 0.2, 1)` | Easing function |
      | `--vt-zoom-out-scale` | `1.2` | Scale factor (old zooms in to this, new zooms out from this) |
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>Overriding variables</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      To customize, override these variables in your CSS:

      ```css
      :root {
        --vt-fade-duration: 300ms;
        --vt-slide-left-distance: 50%;
      }
      ```

      For advanced customization, target the view transition pseudo-elements directly:

      ```css
      ::view-transition-old(vt-slide-left-exit) {
        animation-duration: 400ms;
      }

      ::view-transition-new(vt-slide-left-enter) {
        animation-timing-function: ease-out;
      }
      ```
    </div>
  </AccordionDetails>
</Accordion>
<br />