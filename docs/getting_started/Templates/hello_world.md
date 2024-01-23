---
sidebar_position: 1
title: Hello World
---

To start a simple application, it is recommended to use the DWCJ's [HelloWorldJava repository](https://github.com/DwcJava/HelloWorldJava) as a template. This can be done using any of the [installation methods](../../installation/installation.md).

Once you have cloned the repository and can see the application, modifying the following sections will allow you to start building your own simple application:

```java
public void run() throws DwcjException {

  Frame mainFrame = new Frame();
  mainFrame.addClassName("mainFrame");

  Paragraph hello = new Paragraph("Hello World!");

  Button btn = new Button("Say Hello");
  btn.setTheme(ButtonTheme.SUCCESS)
      .setExpanse(Expanse.XLARGE)
      .addClickListener(e -> msgbox("Hello World!"));

  mainFrame.add(hello, btn);
}
```

This is the method that will be executed when your application is rendered. Here, since the sample program is quite small, the entirety of the program's functionality has been built into this singular method. 

Feel free to follow this pattern for exploration purposes or simple applications. For more complex applications, it is advised to split functionality into additional methods and classes.

###  CSS

```java
@InlineStyleSheet(/* css */"""
  .mainFrame {
    display: inline-grid;
    gap: 20px;
    margin: 20px;
    padding: 20px;
    border: 1px dotted;
  }
""")
```

This is the inline CSS that will be applied to your application. Feel free to continue adding to this section, or create a separate stylesheet using [this page](../../styling/getting_started.md) as a reference.
