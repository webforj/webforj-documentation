package com.webforj.samples.views.fields.textfield;

import com.webforj.component.Composite;
import com.webforj.component.field.TextField;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.layout.flexlayout.FlexWrap;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@FrameTitle("Text Field Form")
public class TextFieldView extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();
  private final TextField nameField = new TextField();
  private final TextField emailField = new TextField();
  private final TextField telField = new TextField();
  private final TextField urlField = new TextField();
  private final TextField searchField = new TextField();

  public TextFieldView() {
    self.setMargin("var(--dwc-space-l)")
        .setSpacing("var(--dwc-space-l)")
        .setDirection(FlexDirection.ROW)
        .setWrap(FlexWrap.WRAP);

    nameField.setPlaceholder("Name")
        .setType(TextField.Type.TEXT)
        .setLabel("Enter Name")
        .setValue("John Doe");

    emailField.setPlaceholder("Email")
        .setType(TextField.Type.EMAIL)
        .setLabel("Enter Email")
        .setValue("example@email.com");

    telField.setPlaceholder("Phone Number")
        .setType(TextField.Type.TEL)
        .setLabel("Enter Phone Number")
        .setValue("(123) 456-7890");

    urlField.setPlaceholder("URL")
        .setType(TextField.Type.URL)
        .setLabel("Enter URL")
        .setValue("https://www.example.com");

    searchField.setPlaceholder("Search")
        .setType(TextField.Type.SEARCH)
        .setLabel("Enter Your Search")
        .setValue("Search...");

    self.add(nameField, emailField, telField, urlField, searchField);
  }
}
