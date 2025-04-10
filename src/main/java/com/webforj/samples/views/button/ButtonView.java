package com.webforj.samples.views.button;

import static com.webforj.component.optiondialog.OptionDialog.showMessageDialog;

import com.webforj.component.Composite;
import com.webforj.component.button.Button;
import com.webforj.component.button.ButtonTheme;
import com.webforj.component.field.TextField;
import com.webforj.component.field.TextField.Type;
import com.webforj.component.layout.flexlayout.FlexAlignment;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

/**
 * Simple demo application to display button functionality.
 */
@Route
@FrameTitle("Button Demo")
public class ButtonView extends Composite<FlexLayout> {

  Button clear;
  Button submit;
  TextField firstName;
  TextField lastName;
  TextField email;

  public ButtonView() {

    getBoundComponent().setDirection(FlexDirection.COLUMN).setSpacing("var(--dwc-space-l)")
        .setAlignment(FlexAlignment.CENTER).setMargin("var(--dwc-space-l)");
    clear = new Button("Clear", ButtonTheme.DEFAULT, e -> {
      firstName.setText("");
      lastName.setText("");
      email.setText("");
    });

    submit = new Button("Submit", ButtonTheme.PRIMARY,
        e -> showMessageDialog("Welcome to the application " + firstName.getText() + " " + lastName.getText() + "!"));

    firstName = new TextField(Type.TEXT, "First Name", "Jason").setWidth("25%");
    firstName.setStyle("flex", "1");
    lastName = new TextField(Type.TEXT, "Last Name", "Turner").setWidth("25%");
    lastName.setStyle("flex", "1");
    email = new TextField(Type.EMAIL, "E-mail:", "turner.jason@email.com").setWidth("100%");

    FlexLayout firstRow = FlexLayout.create(firstName, lastName)
        .justify().center()
        .wrap()
        .build()
        .setSpacing("var(--dwc-space-l)")
        .setWidth("50%")
        .addClassName("row");

    FlexLayout secondRow = FlexLayout.create(email)
        .justify().center()
        .wrap()
        .build()
        .setSpacing("var(--dwc-space-l)")
        .setWidth("50%")
        .addClassName("row");

    FlexLayout thirdRow = FlexLayout.create(submit, clear)
        .justify().end()
        .wrap()
        .build()
        .setSpacing("var(--dwc-space-l)")
        .setWidth("50%")
        .addClassName("row", "buttons");

    getBoundComponent().add(firstRow, secondRow, thirdRow);
  }
}