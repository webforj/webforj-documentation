---
sidebar_position: 3
displayed_sidebar: documentationSidebar
draft: true
---

# Creating a custom component

Building custom components is a fundamental part of modern software development, allowing developers to create specific functionalities that can be reused across different projects. By creating these components, developers can save time and effort, while also improving the quality and consistency of their code.

In this section, we'll walk you through the process of creating custom components step by step. We'll  cover the key concepts and techniques needed to create your own reusable components, and how to import them into your project.

## Building a reusable entry form

For the example custom component, we'll build a simple entry form that can be reused in multiple places in an application. To do this, we'll use the following components:

- [FlexLayout](../layouts/flex-layout.md)

### Create a class for your component 

To begin creating our example form component, we'll create a new file in Java with the name of our desired class. For the purposes of this walkthrough, we'll call it `CustomForm.java`. Once this has been done, extend the `AbstractDwcComponent` class, which will allow this class to be added to webforJ's `Panel` and `Frame` components, as well as various webforJ layouts.
Below is an example snippet for the CustomForm class:

```java
import com.webforj.component.Component;

public class CustomForm extends Component{

}
```

### Override the `create()` method

Once your class's file has been created and extends the proper class, you'll need to override the `create()` method. This method is what is called when a component is added to one of the DWCJ's `window` classes. This method should take an `AbstractWindow` as an argument, 


### Add the desired components to your class

## Import your form