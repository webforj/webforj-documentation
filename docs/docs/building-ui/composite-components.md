---
sidebar_position: 4
title: Composite Components
sidebar_class_name: updated-content
---

<DocChip chip='since' label='23.06' />
<JavadocLink type="foundation" location="com/webforj/component/Composite" top='true'/>


The `Composite` component combines existing webforJ components into self-contained, reusable components with custom behavior. Use it to wrap internal webforJ components into reusable business logic units, reuse component patterns throughout your app, and combine multiple components without exposing implementation details.

A `Composite` component has a strong association with an underlying bound component. This gives you control over which methods and properties users can access, unlike traditional inheritance where everything is exposed.

If you need to integrate web components from another source, use specialized alternatives:

- [ElementComposite](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/component/element/ElementComposite.html): For web components with type-safe property management
- [ElementCompositeContainer](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/component/element/ElementCompositeContainer.html): For web components that accept slotted content

## Usage {#usage}

To define a `Composite` component, extend the `Composite` class and specify the type of component it manages. This becomes your bound component, which is the root container that holds your internal structure:

```java title="BasicComposite.java"
public class BasicComposite extends Composite<FlexLayout> {

  public BasicComposite() {
    // Access the bound component to configure it
    getBoundComponent()
      .setDirection(FlexDirection.COLUMN)
      .setSpacing("3px")
      .add(new TextField(), new Button("Submit"));
  }
}
```

The `getBoundComponent()` method provides access to your underlying component, allowing you to configure its properties, add child components, and manage its behavior directly.

The bound component can be any [webforJ component](../components/overview) or [HTML element component](/docs/building-ui/web-components/html-elements). For flexible layouts, consider using [`FlexLayout`](../components/flex-layout) or [`Div`](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/component/html/elements/Div.html) as your bound component.

:::note Component Extension
Never extend `Component` or `DwcComponent` directly. Always use composition patterns with `Composite` to build custom components.
:::

Override `initBoundComponent()` when you need greater flexibility in creating and managing the bound component, such as using parameterized constructors instead of the default no-argument constructor. Use this pattern when the bound component requires components to be passed to its constructor rather than added afterward.


```java title="CustomFormLayout.java"
public class CustomFormLayout extends Composite<FlexLayout> {
 private TextField nameField;
 private TextField emailField;
 private Button submitButton;

 @Override
 protected FlexLayout initBoundComponent() {
   nameField = new TextField("Name");
   emailField = new TextField("Email");
   submitButton = new Button("Submit");

   FlexLayout layout = new FlexLayout(nameField, emailField, submitButton);
   layout.setDirection(FlexDirection.COLUMN);
   layout.setSpacing("10px");

   return layout;
 }
}
```

## Component lifecycle {#component-lifecycle}

webforJ handles all lifecycle management for `Composite` components automatically. By using the `getBoundComponent()` method, most custom behavior can be handled in the constructor, including adding child components, setting properties, basic layout setup, and event registration.

```java
public class UserDashboard extends Composite<FlexLayout> {
 private TextField searchField;
 private Button searchButton;
 private Div resultsContainer;

 public UserDashboard() {
   initializeComponents();
   setupLayout();
   configureEvents();
 }

 private void initializeComponents() {
   searchField = new TextField("Search users...");
   searchButton = new Button("Search");
   resultsContainer = new Div();
 }

 private void setupLayout() {
   FlexLayout searchRow = new FlexLayout(searchField, searchButton);
   searchRow.setAlignment(FlexAlignment.CENTER);
   searchRow.setSpacing("8px");

   getBoundComponent()
     .setDirection(FlexDirection.COLUMN)
     .add(searchRow, resultsContainer);
 }

 private void configureEvents() {
   searchButton.onClick(event -> performSearch());
 }

 private void performSearch() {
   // Search logic here
 }
}
```

If you have additional specific setup or cleanup requirements, you may need to use the optional lifecycle hooks `onDidCreate()` and `onDidDestroy()`:

```java
public class DataVisualizationPanel extends Composite<Div> {
 private Interval refreshInterval;

 @Override
 protected void onDidCreate(Div container) {
   // Initialize components that require DOM attachment
   refreshInterval = new Interval(5.0, event -> updateData());
   refreshInterval.start();
 }

 @Override
 protected void onDidDestroy() {
   // Cleanup resources
   if (refreshInterval != null) {
     refreshInterval.stop();
   }
 }

 private void updateData() {
   // Data update logic
 }
}
```

If you need to perform any actions after the component is attached to the DOM, use the `whenAttached()` method:

```java title="InteractiveMap.java"
public class InteractiveMap extends Composite<Div> {
  public InteractiveMap() {
    setupMapContainer();

    whenAttached().thenAccept(component -> {
      initializeMapLibrary();
      loadMapData();
    });
  }
}
```

## Example `Composite` component {#example-composite-component}

The following example demonstrates a Todo app where each item is a `Composite` component consisting of a [`RadioButton`](../components/radiobutton) styled as a switch and a Div with text: 

<ComponentDemo 
path='/webforj/composite?' 
cssURL='https://raw.githubusercontent.com/webforj/webforj-documentation/main/src/main/resources/static/composite/composite.css'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/composite/CompositeView.java'
height='500px'
/>

## Example: Component grouping {#example-component-grouping}

Sometimes you may want to use a `Composite` to group related components together into a single unit, even when reusability isn't the main concern:

<ComponentDemo
path='/webforj/analyticscardcomposite?'
cssURL='https://raw.githubusercontent.com/webforj/webforj-documentation/main/src/main/resources/static/composite/analyticscomposite.css'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/composite/AnalyticsCardCompositeView.java'
height='500px'
/>
