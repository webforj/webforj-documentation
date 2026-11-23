---
title: "Use SAP UI5 or your own design system in a Java web app"
description: "Use your design system in a Java web app: a step-by-step tutorial that wraps SAP UI5 Web Components in webforJ with data binding, validation and browserless tests."
slug: bring-your-own-design-system-java
date: 2026-11-23
authors: Hyyan Abo Fakher
tags: [tutorial, showcase]
image: https://cdn.webforj.com/webforj-documentation/blogs/2026-11-23-bring-your-own-design-system-java/cover.jpg
hide_table_of_contents: false
---

![cover image](https://cdn.webforj.com/webforj-documentation/blogs/2026-11-23-bring-your-own-design-system-java/cover.jpg)

Your company already has a design system, maybe SAP UI5, maybe custom elements your teams share. A Java team now has to build screens with it, and the usual answer is a separate frontend project.

With webforJ the Java team uses the design system directly. This tutorial wraps SAP UI5 Web Components in five steps. The result is a form of UI5 controls that binds to a Java bean, validates, and passes JUnit tests without a browser.

<!-- truncate -->

:::tip Run the finished app
The [UI5 customer app](https://github.com/webforj/built-with-webforj/tree/main/webforj-ui5) is published on GitHub, with every wrapper, view, and test from this tutorial. It runs with Java and Maven, and it's kept up to date with each new webforJ release.
:::

<div style={{textAlign: 'center'}}>

![The UI5 customer app built with webforJ](https://cdn.webforj.com/webforj-documentation/blogs/2026-11-23-bring-your-own-design-system-java/screenshot.png)

</div>

## The result: UI5 data binding {#result}

This is the form view at the end. Every field is a UI5 control:

```java
private final Ui5Input name = new Ui5Input("Name");
private final Ui5Input email = new Ui5Input("Email");
private final Ui5Select<Status> status = new Ui5Select<>("Status");

binding = BindingContext.of(this, Customer.class, true);
```

And saving:

```java
if (binding.write(customer).isValid()) {
  service.save(customer);
}
```

Type an address without an `@` and press Save. The email field turns red the UI5 way, shows the message from the `@Email` constraint on the bean, and nothing reaches the service.

That works because each UI5 control implements the same small interfaces as a built-in webforJ component. The steps below build those controls.

## Step 1: A base class for every field {#step-1}

[Data binding](/docs/data-binding/automatic-binding) doesn't care which component it talks to. It only needs a few interfaces, called concerns: `HasValue` for the value, `HasLabel` for the label, and so on. One base class implements them for all UI5 fields:

```java
public abstract class Ui5Field<T extends Ui5Field<T, V>, V> extends ElementCompositeContainer
    implements HasValue<T, V>, HasLabel<T>, HasRequired<T>, HasEnablement<T>, HasVisibility<T>,
    HasSize<T>, HasClassName<T>, HasStyle<T> {

  private final PropertyDescriptor<String> accessibleNameProp =
      PropertyDescriptor.property("accessibleName", "");

  protected void watchValue(PropertyDescriptor<?> property, String event) {
    synchronize(property, event);
    getElement().addEventListener(event,
        e -> getEventDispatcher().dispatchEvent(new ValueChangeEvent<>(this, getValue())));
  }

  @Override
  public T setLabel(String label) {
    set(accessibleNameProp, label);
    return self();
  }

  // getLabel, setRequired, isRequired and addValueChangeListener follow the same pattern
}
```

**The two type parameters, in plain words:**

- **`V` is the value type.** The input holds a `String`, the switch a `Boolean`, the select your own type, such as `Status`.
- **`T` is the control's own class.** It lets `setLabel` return a `Ui5Input` on an input and a `Ui5Switch` on a switch, so calls chain without casts. `self()` returns `this` as that type.

So `Ui5Input` declares itself as `Ui5Field<Ui5Input, String>`, and `Ui5Switch` as `Ui5Field<Ui5Switch, Boolean>`.

**What the methods do:**

- `setLabel` writes the UI5 `accessibleName` property, because UI5 inputs draw no label of their own.
- `watchValue` connects a UI5 property to the browser event that changes it. It stores the new value first, then fires the change event, so listeners never read an old value.

## Step 2: Show validation errors {#step-2}

UI5 marks an invalid field with `valueState` set to `Negative` and shows the text in a slot called `valueStateMessage`. A second base class maps the `InvalidAware` concern onto that:

```java
public abstract class Ui5ValidatedField<T extends Ui5ValidatedField<T, V>, V>
    extends Ui5Field<T, V> implements InvalidAware<T>, HasReadOnly<T> {

  private final PropertyDescriptor<ValueState> valueStateProp =
      PropertyDescriptor.property("valueState", ValueState.NONE);
  private final Div message = new Div();

  protected Ui5ValidatedField(String label) {
    super(label);
    getElement().add("valueStateMessage", message);
  }

  @Override
  public T setInvalid(boolean invalid) {
    set(valueStateProp, invalid ? ValueState.NEGATIVE : ValueState.NONE);
    return self();
  }

  @Override
  public T setInvalidMessage(String message) {
    this.message.setHtml(message);
    return self();
  }
}
```

`setHtml` is on purpose: when a field breaks two rules, the binding joins the messages with `<br>`.

## Step 3: Wrap a control {#step-3}

Now one UI5 control is a short class. It names the npm package and the element, then says which property holds the value and which event changes it:

```java
@BundlePackage(value = "@ui5/webcomponents", version = "^2.28.1")
@BundleEntry("@ui5/webcomponents/dist/Input.js")
@NodeName("ui5-input")
public class Ui5Input extends Ui5ValidatedField<Ui5Input, String>
    implements HasPlaceholder<Ui5Input> {

  private final PropertyDescriptor<String> valueProp = PropertyDescriptor.property("value", "");

  public Ui5Input(String label) {
    super(label);
    watchValue(valueProp, "input");
  }

  @Override
  public String getValue() {
    return get(valueProp);
  }

  @Override
  public Ui5Input setValue(String value) {
    set(valueProp, value == null ? "" : value);
    return this;
  }
}
```

Every other control follows this shape. With these three classes, the form from the start of the post works.

## Step 4: Hide controls until they load {#step-4}

Before its script loads, a custom element shows up as plain text for a moment. This CSS hides the app shell's controls and every view until all its elements are ready:

```css
.shell :not(:defined),
.page:has(:not(:defined)) {
  visibility: hidden;
}
```

If a UI5 button shows an icon, load the icons first. Put both imports in one file under `src/main/frontend`, and point the button class to it with `@BundleEntry("ui5/button.js")`:

```js
import "@ui5/webcomponents-icons/dist/AllIcons.js";
import "@ui5/webcomponents/dist/Button.js";
```

## Step 5: Test without a browser {#step-5}

The [TestBed](/docs/testing/testbed/overview) runs the view in plain JUnit. No browser opens and no server starts. It finds the UI5 input by its label, because `getLabel` is implemented:

```java
@Test
void shouldShowAValidationErrorOnTheField(WebforjTestbed context) {
  open(context, String.valueOf(id));

  UserInteraction.fireEvent(input("Email"), "input", Map.of("value", "not-an-email"));
  UserInteraction.click(button("Save"));

  assertTrue(input("Email").isInvalid());
  assertEquals("Enter a valid email address", input("Email").getInvalidMessage());
}

private static Ui5Input input(String label) {
  return ComponentLocator.of(Ui5Input.class).byLabel(label).locate();
}
```

The sample app has 25 of these tests.

## Frequently asked questions {#faq}

<!-- vale Google.Headings = NO -->
### Can a Java app use SAP UI5 Web Components? {#faq-ui5-java}
<!-- vale Google.Headings = YES -->

Yes. In webforJ each UI5 element gets a small Java wrapper class, and the views use those classes like any other component. The npm package loads through the frontend bundler, named by annotations on the Java class.

### How do web components bind to a Java bean? {#faq-binding}

Through the same interfaces built-in components use. A wrapper that implements `HasValue` and `InvalidAware` works with `BindingContext`, and the Jakarta constraints on the bean show their messages on the field.

### Can we keep our own design system with webforJ? {#faq-own-design-system}

Yes. The look stays with your design system and its theme. webforJ supplies the behavior: binding, validation, routing, and tests.

## Wrap your own design system {#your-design-system}

The same five steps work for any library of custom elements:

1. One base class that implements `HasValue` and the other concerns
2. One base class that maps `InvalidAware` to how your library shows errors
3. One short class per control
4. CSS that hides undefined elements
5. TestBed tests that find controls by label

For wrapping a single element, see [web components in Java](/blog/web-components-in-java). For the full app, start with `Ui5Field` in the [webforj-ui5 project](https://github.com/webforj/built-with-webforj/tree/main/webforj-ui5).
