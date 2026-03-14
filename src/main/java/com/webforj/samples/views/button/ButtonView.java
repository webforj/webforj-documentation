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
  // self field enables fluent method chaining from the bound component
  private final FlexLayout self = getBoundComponent();
  private final TextField firstName = new TextField(Type.TEXT, "First Name", "Jason")
      .setWidth("25%")
      .setStyle("flex", "1");
  private final TextField lastName = new TextField(Type.TEXT, "Last Name", "Turner")
      .setWidth("25%")
      .setStyle("flex", "1");
  private final TextField email = new TextField(Type.EMAIL, "E-mail:", "turner.jason@email.com")
      .setWidth("100%");
  private final Button submit;
  private final Button clear;

  public ButtonView() {
    self.setDirection(FlexDirection.COLUMN)
        .setSpacing("var(--dwc-space-l)")
        .setAlignment(FlexAlignment.CENTER)
        .setMargin("var(--dwc-space-l)");

    submit = new Button("Submit", ButtonTheme.PRIMARY, e -> {
      String message = "Welcome to the app %s %s!".formatted(firstName.getText(), lastName.getText());
      showMessageDialog(message, "Welcome");
    });

    clear = new Button("Clear", ButtonTheme.DEFAULT, e -> {
      firstName.setText("");
      lastName.setText("");
      email.setText("");
    });

    // Build and add rows
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

    self.add(firstRow, secondRow, thirdRow);
  }
}