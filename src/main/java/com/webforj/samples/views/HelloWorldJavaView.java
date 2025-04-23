package com.webforj.samples.views;

import static com.webforj.component.optiondialog.OptionDialog.showMessageDialog;

import com.webforj.annotation.InlineStyleSheet;
import com.webforj.component.Composite;
import com.webforj.component.Expanse;
import com.webforj.component.button.Button;
import com.webforj.component.button.ButtonTheme;
import com.webforj.component.html.elements.Paragraph;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

/**
 * A simple HelloWorld app.
 */
@InlineStyleSheet(/* css */"""
  .mainFrame {
    margin: 20px;
    padding: 20px;
    border: 1px dotted;
    width: 150px;
  }
""")
@Route("/")
@FrameTitle("Hello World Java")
public class HelloWorldJavaView extends Composite<FlexLayout> {

  Paragraph hello = new Paragraph("Hello World!");
  Button btn = new Button("Say Hello");

  public HelloWorldJavaView() {
    btn.setTheme(ButtonTheme.SUCCESS)
        .setExpanse(Expanse.XLARGE)
        .addClickListener(e -> showMessageDialog("Hello World!", "Do it all in Java"));

    getBoundComponent().setDirection(FlexDirection.COLUMN)
        .addClassName("mainFrame")
        .add(hello, btn);
  }
}