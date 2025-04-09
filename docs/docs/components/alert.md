---
title: Alert
sidebar_position: 5
---

<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-alert" />

The `Alert` component provides contextual feedback messages for users. It's a versatile way to display important information, warnings, or notifications in your app.

Here's an example of an alert component:

<ComponentDemo 
path='http://localhost:8080/webforj/alert?' 
height = '100px'
/>

## Dismissing alerts

If you’d like to give users the option to dismiss the `Alert`, you can make it closable either by calling the `setClosable()` method.

```java 
Alert alert = new Alert("Heads up! This alert can be dismissed.");
closableAlert.setClosable(true);
```

<ComponentDemo 
path='http://localhost:8080/webforj/closablealert?' 
height = '125px'
/>


:::tip
Closing the alert only hides it—it doesn’t destroy the component, so you can reuse it later if needed.
:::

### Close event

In many applications, alerts aren’t just for display—they often trigger follow-up logic once dismissed. Whether you need to log user actions, display another message, or reset the UI state, the `AlertCloseEvent` gives you a reliable hook to respond when the user closes the `Alert`.

<ComponentDemo 
path='http://localhost:8080/webforj/alertcloseevent?' 
height = '300px'
/>

## Styling

### Themes

The `Alert` component supports multiple <JavadocLink type="foundation" location="com/webforj/component/Theme"> themes </JavadocLink> to visually distinguish different types of messages—such as success, error, warning, or info. These themes can be applied using the `setTheme()` method or directly in the constructor.

<ComponentDemo 
path='http://localhost:8080/webforj/alertthemes?' 
height = '500px'
/>


### Expanses

The expanse defines the visual size of the `Alert` component. You can set it using the `setExpanse()` method or pass it directly to the constructor. The available options come from the Expanse enum: `XSMALL`, `SMALL`, `MEDIUM`, `LARGE`, and `XLARGE`.

<ComponentDemo 
path='http://localhost:8080/webforj/alertexpanses?' 
height = '425px'
/>


### Shadow Parts
These are the various parts of the shadow DOM for the component, which will be required when styling via CSS is desired.
<TableBuilder tag='dwc-alert' table="parts"/>



### Reflected Attributes

  The reflected attributes of a component will be shown as attributes in the rendered HTML element for the component in the DOM. This means that styling can be applied using these attributes.
  
  <TableBuilder tag='dwc-alert' table="reflects"/>

### Dependencies

  This component relies on the following components - see the related article for more detailed styling information:
  
  <TableBuilder tag='dwc-alert' table="dependencies"/>