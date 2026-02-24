package com.webforj.samples.views.columnslayout;

import com.webforj.component.Composite;
import com.webforj.component.button.Button;
import com.webforj.component.button.ButtonTheme;
import com.webforj.component.field.DateField;
import com.webforj.component.field.TextArea;
import com.webforj.component.field.TextField;
import com.webforj.component.html.elements.Div;
import com.webforj.component.layout.columnslayout.ColumnsLayout;
import com.webforj.component.optioninput.CheckBox;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@FrameTitle("Columns Layout Alignment")
public class ColumnsLayoutAlignmentView extends Composite<Div> {
  private Div self = getBoundComponent();
  private TextField firstName = new TextField("First Name");
  private TextField lastName = new TextField("Last Name");
  private TextField email = new TextField("Email");
  private DateField dateOfBirth = new DateField("Date of Birth");
  private TextArea bio = new TextArea("Bio");
  private CheckBox terms = new CheckBox("I agree to the terms and conditions");
  private Button submit = new Button("Submit", ButtonTheme.PRIMARY);
  private ColumnsLayout columnsLayout = new ColumnsLayout(
          firstName, lastName, email, dateOfBirth, bio, terms, submit);

  public ColumnsLayoutAlignmentView() {
    columnsLayout.setSpan(bio, 2)
            .setSpan(terms, 2)
            .setColumn(submit, 2)
            .setHorizontalAlignment(submit, ColumnsLayout.Alignment.END)
            .setStyle("padding", "var(--dwc-space-xl)");

    self.setMaxWidth("60em")
            .setStyle("margin", "0 auto")
            .setStyle("overflow", "auto")
            .setStyle("height", "100dvh")
            .add(columnsLayout);
  }
}