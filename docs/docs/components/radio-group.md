---
title: RadioButtonGroup
slug: radiobuttongroup
sidebar_position: 100
sidebar_class_name: updated-content
---

<DocChip chip='since' label='23.01' />
<JavadocLink type="foundation" location="com/webforj/component/optioninput/RadioButtonGroup" top='true'/>

The `RadioButtonGroup` manages a collection of [`RadioButton`](/docs/components/radiobutton) components. Only one `RadioButton` can be selected in a `RadioButtonGroup`. When a user checks a new radio button, the previously selected one in the group is automatically unchecked.

<ComponentDemo 
path='/webforj/radiobuttongroup?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/radiobutton/RadioButtonGroupView.java'
height="200px"
/>

:::important `RadioButtonGroup` rendering
The `RadioButtonGroup` component doesn't render an HTML element. It only provides logic to make `RadioButton` components behave as a group rather than individually.
:::

## Adding and removing `RadioButton` components {#adding-and-removing-radiobuttons}

You can include `RadioButton` components in the `RadioButtonGroup` constructor to create a group out of the provided components.
To add or remove a `RadioButton` from an existing `RadioButtonGroup`, use the `add()` or `remove()` methods.

:::tip Getting the Group of a `RadioButton`
The `RadioButton` component has the `getButtonGroup()` method, which returns the `RadioButtonGroup` it belongs to, or `null` if it doesn’t have a group.
:::

## Nesting <DocChip chip='since' label='25.11' /> {#nesting}

Like other components, you can nest a `RadioButtonGroup` within a container, so you don't have to directly add each individual `RadioButton`.

```java
RadioButton agree = new RadioButton("Agree");
RadioButton neutral = new RadioButton("Neutral");
RadioButton disagree = new RadioButton("Disagree");

RadioButtonGroup group = new RadioButtonGroup("choices", agree, neutral, disagree);

Fieldset fieldset = new Fieldset("Options");
fieldset.add(group);
```

## Using `RadioButtonGroupChangeEvent` {#using-radiobuttongroupchangeevent}

Each `RadioButton` can have its own event listener to detect when a user toggles it. However, one advantage of using a `RadioButtonGroup` is that you can use a single event listener that responds to all the radio buttons in the group with the [`RadioButtonGroupChangeEvent`](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/component/optioninput/event/RadioButtonGroupChangeEvent.html).

**Adding event listeners to each `RadioButton`**

```java 
agree.onValueChange(e -> changeEvent());
neutral.onValueChange(e -> changeEvent());
disagree.onValueChange(e -> changeEvent());
```

**Adding a single event listener to the `RadioButtonGroup`**

```java
RadioButtonGroup group = new RadioButtonGroup("choices", agree, neutral, disagree);
group.onChange(e -> changeEvent());
```

The following sample from [Drawer Placement](/docs/components/drawer#placement) uses the `RadioButtonGroupChangeEvent` to automatically change the placement of the `Drawer` component:

<ComponentDemo
path='/webforj/drawerplacement?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/drawer/DrawerPlacementView.java'
height='600px'
/>

## Naming {#naming}

The `name` attribute in a `RadioButtonGroup` groups related RadioButtons together, allowing users to make a single choice from the options provided and enforcing exclusivity among the RadioButtons. The name of a group isn't reflected in the DOM, however, and is a convenience utility for the Java developer.