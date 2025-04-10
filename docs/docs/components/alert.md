---
title: Alert
sidebar_position: 5
---

<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-alert" />
<JavadocLink type="alert" location="com/webforj/component/alert/Alert" top='true'/>

The `Alert` component in webforJ provides contextual feedback messages for users. It's a versatile way to display important information, warnings, or notifications in your app.

Alerts help draw attention to key information without disrupting the user's workflow. They're perfect for system messages, form validation feedback, or status updates that need to be clearly visible but not intrusive.

Here's an example of an alert component:

<ComponentDemo 
path='/webforj/alert?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/alert/AlertView.java'
height = '100px'
/>

## Dismissing alerts

If you’d like to give users the option to dismiss the `Alert`, you can make it closable by calling the `setClosable()` method. 

```java 
Alert alert = new Alert("Heads up! This alert can be dismissed.");
closableAlert.setClosable(true);
```
Alerts often do more than display messages—they can trigger follow-up actions when dismissed. Use the `AlertCloseEvent` to handle these cases and respond when the user dismisses the `Alert`.

<ComponentDemo 
path='/webforj/closablealert?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/alert/ClosableAlertView.java'
height = '100px'
/>

:::tip Reusable Alert Component
Closing the alert only hides it—it doesn’t destroy the component, so you can reuse it later if needed.
:::


## Styling

### Themes

The `Alert` component supports multiple <JavadocLink type="foundation" location="com/webforj/component/Theme"> themes </JavadocLink> to visually distinguish different types of messages—such as success, error, warning, or info. These themes can be applied using the `setTheme()` method or directly in the constructor.

<ComponentDemo 
path='/webforj/alertthemes?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/alert/AlertThemesView.java' 
height = '475px'
/>


### Expanses

The expanse defines the visual size of the `Alert` component. You can set it using the `setExpanse()` method or pass it directly to the constructor. The available options come from the Expanse enum: `XSMALL`, `SMALL`, `MEDIUM`, `LARGE`, and `XLARGE`.

<ComponentDemo 
path='/webforj/alertexpanses?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/alert/AlertExpansesView.java'
height = '400px'
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