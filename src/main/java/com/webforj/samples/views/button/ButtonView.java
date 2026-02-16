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
  private FlexLayout self = getBoundComponent();
  private Button clear;
  private Button submit;
  private TextField firstName;
  private TextField lastName;
  private TextField email;

  public ButtonView() {
    self.setDirection(FlexDirection.COLUMN)
            .setSpacing("var(--dwc-space-l)")
            .setAlignment(FlexAlignment.CENTER)
            .setMargin("var(--dwc-space-l)");
    setupFirstRow();
    setupSecondRow();
    setupThirdRow();
  }

  private void setupFirstRow() {
    firstName = new TextField(Type.TEXT, "First Name", "Jason")
            .setWidth("25%")
            .setStyle("flex", "1");
    lastName = new TextField(Type.TEXT, "Last Name", "Turner")
            .setWidth("25%")
            .setStyle("flex", "1");

    FlexLayout firstRow = FlexLayout.create(firstName, lastName)
            .justify().center()
            .wrap()
            .build()
            .setSpacing("var(--dwc-space-l)")
            .setWidth("50%")
            .addClassName("row");

    self.add(firstRow);
  }

  private void setupSecondRow() {
    email = new TextField(Type.EMAIL, "E-mail:", "turner.jason@email.com")
            .setWidth("100%");

    FlexLayout secondRow = FlexLayout.create(email)
            .justify().center()
            .wrap()
            .build()
            .setSpacing("var(--dwc-space-l)")
            .setWidth("50%")
            .addClassName("row");

    self.add(secondRow);
  }

  private void setupThirdRow() {
    submit = new Button("Submit", ButtonTheme.PRIMARY, e -> {
      String message = "Welcome to the app %s %s!".formatted(firstName.getText(), lastName.getText());
      showMessageDialog(message, "Welcome");
    });
    clear = new Button("Clear", ButtonTheme.DEFAULT, e -> {
      firstName.setText("");
      lastName.setText("");
      email.setText("");
    });

    FlexLayout thirdRow = FlexLayout.create(submit, clear)
            .justify().end()
            .wrap()
            .build()
            .setSpacing("var(--dwc-space-l)")
            .setWidth("50%")
            .addClassName("row", "buttons");

    self.add(thirdRow);
  }
}