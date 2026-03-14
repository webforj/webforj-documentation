package com.webforj.samples.views.columnslayout;

import com.webforj.component.Composite;
import com.webforj.component.button.Button;
import com.webforj.component.button.ButtonTheme;
import com.webforj.component.field.PasswordField;
import com.webforj.component.field.TextField;
import com.webforj.component.html.elements.Div;
import com.webforj.component.layout.columnslayout.ColumnsLayout;
import com.webforj.component.list.ChoiceBox;
import com.webforj.component.list.ListItem;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

import java.util.List;

@Route
@FrameTitle("Columns Layout Form")
public class ColumnsLayoutFormView extends Composite<Div> {
  // self field enables fluent method chaining from the bound component
  private final Div self = getBoundComponent();
  private final TextField firstName = new TextField("First Name");
  private final TextField lastName = new TextField("Last Name");
  private final TextField email = new TextField("Email");
  private final PasswordField password = new PasswordField("Password");
  private final PasswordField passwordConfirm = new PasswordField("Confirm Password");
  private final TextField address = new TextField("Address");
  private final ChoiceBox states = new ChoiceBox("State");
  private final TextField zip = new TextField("Zip");
  private final Button submit = new Button("Submit", ButtonTheme.PRIMARY);
  private final Button cancel = new Button("Cancel", ButtonTheme.OUTLINED_PRIMARY);
  private final ColumnsLayout columnsLayout = new ColumnsLayout(
      firstName, lastName,
      email,
      password, passwordConfirm,
      address,
      states, zip,
      cancel, submit);

  public ColumnsLayoutFormView() {
    populateStates();

    columnsLayout.setSpan(email, 2)
        .setSpan(address, 2)
        .setStyle("padding", "var(--dwc-space-xl)");

    submit.setStyle("margin-top", "var(--dwc-space-l)");
    cancel.setStyle("margin-top", "var(--dwc-space-l)");

    self.setMaxWidth("600px")
        .setStyle("margin", "0 auto")
        .setStyle("overflow", "auto")
        .setStyle("height", "100dvh")
        .add(columnsLayout);
  }

  private void populateStates() {
    // Use immutable List.of() for cleaner state list
    List<ListItem> listStates = List.of(
        new ListItem("AL", "Alabama"),
        new ListItem("AK", "Alaska"),
        new ListItem("AZ", "Arizona"),
        new ListItem("AR", "Arkansas"),
        new ListItem("CA", "California"),
        new ListItem("CO", "Colorado"),
        new ListItem("CT", "Connecticut"),
        new ListItem("DE", "Delaware"),
        new ListItem("FL", "Florida"),
        new ListItem("GA", "Georgia"),
        new ListItem("HI", "Hawaii"),
        new ListItem("ID", "Idaho"),
        new ListItem("IL", "Illinois"),
        new ListItem("IN", "Indiana"),
        new ListItem("IA", "Iowa"),
        new ListItem("KS", "Kansas"),
        new ListItem("KY", "Kentucky"),
        new ListItem("LA", "Louisiana"),
        new ListItem("ME", "Maine"),
        new ListItem("MD", "Maryland"),
        new ListItem("MA", "Massachusetts"),
        new ListItem("MI", "Michigan"),
        new ListItem("MN", "Minnesota"),
        new ListItem("MS", "Mississippi"),
        new ListItem("MO", "Missouri"),
        new ListItem("MT", "Montana"),
        new ListItem("NE", "Nebraska"),
        new ListItem("NV", "Nevada"),
        new ListItem("NH", "New Hampshire"),
        new ListItem("NJ", "New Jersey"),
        new ListItem("NM", "New Mexico"),
        new ListItem("NY", "New York"),
        new ListItem("NC", "North Carolina"),
        new ListItem("ND", "North Dakota"),
        new ListItem("OH", "Ohio"),
        new ListItem("OK", "Oklahoma"),
        new ListItem("OR", "Oregon"),
        new ListItem("PA", "Pennsylvania"),
        new ListItem("RI", "Rhode Island"),
        new ListItem("SC", "South Carolina"),
        new ListItem("SD", "South Dakota"),
        new ListItem("TN", "Tennessee"),
        new ListItem("TX", "Texas"),
        new ListItem("UT", "Utah"),
        new ListItem("VT", "Vermont"),
        new ListItem("VA", "Virginia"),
        new ListItem("WA", "Washington"),
        new ListItem("WV", "West Virginia"),
        new ListItem("WI", "Wisconsin"),
        new ListItem("WY", "Wyoming")
    );

    states.insert(listStates);
  }
}