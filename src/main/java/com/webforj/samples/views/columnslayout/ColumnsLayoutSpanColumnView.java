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
@FrameTitle("Columns Layout Spans")
public class ColumnsLayoutSpanColumnView extends Composite<Div> {
  // self field enables fluent method chaining from the bound component
  private final Div self = getBoundComponent();
  private final TextField firstName = new TextField("First Name");
  private final TextField lastName = new TextField("Last Name");
  private final TextField email = new TextField("Email");
  private final PasswordField password = new PasswordField("Password");
  private final PasswordField confirmPassword = new PasswordField("Confirm Password");

  // The layout will position the components in 3 columns by default.
  // On medium screens, it will span the email field to 2 columns.
  private final ColumnsLayout columnsLayout = new ColumnsLayout(
      List.of(
          new ColumnsLayout.Breakpoint("default", 0, 1),
          new ColumnsLayout.Breakpoint("small", "20em", 1),
          new ColumnsLayout.Breakpoint("medium", "40em", 2),
          new ColumnsLayout.Breakpoint("large", "60em", 3)),
      firstName, lastName, email, password, confirmPassword);

  public ColumnsLayoutSpanColumnView() {
    columnsLayout.setSpan(email, "medium", 2)
        .setStyle("padding", "var(--dwc-space-xl)");

    self.setMaxWidth("60em")
        .setStyle("margin", "0 auto")
        .setStyle("overflow", "auto")
        .setStyle("height", "100dvh")
        .add(columnsLayout);
  }
}