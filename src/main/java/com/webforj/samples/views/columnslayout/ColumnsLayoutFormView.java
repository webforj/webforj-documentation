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
  private Div self = getBoundComponent();
  private TextField firstName = new TextField("First Name");
  private TextField lastName = new TextField("Last Name");
  private TextField email = new TextField("Email");
  private PasswordField password = new PasswordField("Password");
  private PasswordField passwordConfirm = new PasswordField("Confirm Password");
  private TextField address = new TextField("Address");
  private ChoiceBox states = new ChoiceBox("State");
  private TextField zip = new TextField("Zip");
  private Button submit = new Button("Submit", ButtonTheme.PRIMARY);
  private Button cancel = new Button("Cancel", ButtonTheme.OUTLINED_PRIMARY);
  private ColumnsLayout columnsLayout = new ColumnsLayout(
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

    this.states.insert(listStates);
  }
}