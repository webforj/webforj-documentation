package com.webforj.samples.views.columnslayout;

import com.webforj.component.Composite;
import com.webforj.component.button.Button;
import com.webforj.component.button.ButtonTheme;
import com.webforj.component.field.PasswordField;
import com.webforj.component.field.TextField;
import com.webforj.component.html.elements.Div;
import com.webforj.component.layout.columnslayout.ColumnsLayout;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@FrameTitle("Columns Layout")
public class ColumnsLayoutView extends Composite<Div> {
  TextField firstName = new TextField("First Name");
  TextField lastName = new TextField("Last Name");
  TextField email = new TextField("Email");
  PasswordField password = new PasswordField("Password");
  PasswordField confirmPassword = new PasswordField("Confirm Password");
  Button submit = new Button("Submit", ButtonTheme.PRIMARY);

  /*
   * The layout will position the components in 2 columns by default.
   */
  ColumnsLayout columnsLayout = new ColumnsLayout(firstName, lastName, email, password, confirmPassword, submit);

  public ColumnsLayoutView() {
    columnsLayout.setSpan(email, 2);
    columnsLayout.setSpan(submit, 2);
    columnsLayout.setStyle("padding", "var(--dwc-space-xl)");

    submit.setStyle("margin-top", "var(--dwc-space-l)");

    getBoundComponent().setMaxWidth("600px");
    getBoundComponent().setStyle("margin", "0 auto");
    getBoundComponent().setStyle("overflow", "auto");
    getBoundComponent().setStyle("height", "100dvh");
    getBoundComponent().add(columnsLayout);
  }
}