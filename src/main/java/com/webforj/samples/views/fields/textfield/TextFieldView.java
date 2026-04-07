package com.webforj.samples.views.fields.textfield;

import java.util.List;

import com.webforj.component.Composite;
import com.webforj.component.field.TextField;
import com.webforj.component.layout.columnslayout.ColumnsLayout;
import com.webforj.component.layout.columnslayout.ColumnsLayout.Breakpoint;
import com.webforj.component.layout.flexlayout.FlexJustifyContent;
import com.webforj.component.layout.flexlayout.FlexLayout;
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
    private final ColumnsLayout fields = new ColumnsLayout(
        nameField, emailField, telField, urlField, searchField);

  public TextFieldView() {

    self.setWidth("100vw")
        .setMargin("var(--dwc-space-l) 0")    
        .setJustifyContent(FlexJustifyContent.CENTER);

    fields.setBreakpoints(List.of(new Breakpoint(0, 2)));

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

    self.add(fields);
  }
}
