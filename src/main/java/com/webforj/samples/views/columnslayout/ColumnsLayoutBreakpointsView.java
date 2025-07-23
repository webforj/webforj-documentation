package com.webforj.samples.views.columnslayout;

import com.webforj.component.Composite;
import com.webforj.component.field.PasswordField;
import com.webforj.component.field.TextField;
import com.webforj.component.html.elements.Div;
import com.webforj.component.layout.columnslayout.ColumnsLayout;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

import java.util.List;

@Route
@FrameTitle("Columns Layout Breakpoints")
public class ColumnsLayoutBreakpointsView extends Composite<Div> {
  TextField firstName = new TextField("First Name");
  TextField lastName = new TextField("Last Name");
  TextField email = new TextField("Email");
  PasswordField password = new PasswordField("Password");
  PasswordField confirmPassword = new PasswordField("Confirm Password");
  /**
   * The layout will position the components in 3 columns by default.
   * on medium screens, it will span 2 the email field 2 columns.
   */
  ColumnsLayout columnsLayout = new ColumnsLayout(
      List.of(
          new ColumnsLayout.Breakpoint("default", 0, 1),
          new ColumnsLayout.Breakpoint("small", "20em", 1),
          new ColumnsLayout.Breakpoint("medium", "40em", 2),
          new ColumnsLayout.Breakpoint("large", "60em", 3)),
      firstName, lastName, email, password, confirmPassword);

  public ColumnsLayoutBreakpointsView() {
    columnsLayout.setStyle("padding", "var(--dwc-space-xl)");

    getBoundComponent().setMaxWidth("60em");
    getBoundComponent().setStyle("margin", "0 auto");
    getBoundComponent().setStyle("overflow", "auto");
    getBoundComponent().setStyle("height", "100dvh");
    getBoundComponent().add(columnsLayout);
  }
}