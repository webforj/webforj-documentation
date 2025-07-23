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

  TextField firstName = new TextField("First Name");
  TextField lastName = new TextField("Last Name");
  TextField email = new TextField("Email");
  DateField dateOfBirth = new DateField("Date of Birth");
  TextArea bio = new TextArea("Bio");
  CheckBox terms = new CheckBox("I agree to the terms and conditions");
  Button submit = new Button("Submit", ButtonTheme.PRIMARY);
  ColumnsLayout columnsLayout = new ColumnsLayout(
      firstName, lastName, email, dateOfBirth, bio, terms, submit);

  public ColumnsLayoutAlignmentView() {
    columnsLayout.setSpan(bio, 2);
    columnsLayout.setSpan(terms, 2);

    columnsLayout.setColumn(submit, 2);
    columnsLayout.setHorizontalAlignment(submit, ColumnsLayout.Alignment.END);
    columnsLayout.setStyle("padding", "var(--dwc-space-xl)");

    getBoundComponent().setMaxWidth("60em");
    getBoundComponent().setStyle("margin", "0 auto");
    getBoundComponent().setStyle("overflow", "auto");
    getBoundComponent().setStyle("height", "100dvh");
    getBoundComponent().add(columnsLayout);
  }
}