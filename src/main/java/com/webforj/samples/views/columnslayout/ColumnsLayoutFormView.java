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

import java.util.ArrayList;

@Route
@FrameTitle("Columns Layout Form")
public class ColumnsLayoutFormView extends Composite<Div> {
  TextField firstName = new TextField("First Name");
  TextField lastName = new TextField("Last Name");
  TextField email = new TextField("Email");
  PasswordField password = new PasswordField("Password");
  PasswordField passwordConfirm = new PasswordField("Confirm Password");
  TextField address = new TextField("Address");
  ChoiceBox states = new ChoiceBox("State");
  TextField zip = new TextField("Zip");
  Button submit = new Button("Submit", ButtonTheme.PRIMARY);
  Button cancel = new Button("Cancel", ButtonTheme.OUTLINED_PRIMARY);
  ColumnsLayout columnsLayout = new ColumnsLayout(
      firstName, lastName,
      email,
      password, passwordConfirm,
      address,
      states, zip,
      cancel, submit);

  public ColumnsLayoutFormView() {
    populateStates();

    columnsLayout.setSpan(email, 2);
    columnsLayout.setSpan(address, 2);
    columnsLayout.setStyle("padding", "var(--dwc-space-xl)");

    submit.setStyle("margin-top", "var(--dwc-space-l)");
    cancel.setStyle("margin-top", "var(--dwc-space-l)");

    getBoundComponent().setMaxWidth("600px");
    getBoundComponent().setStyle("margin", "0 auto");
    getBoundComponent().setStyle("overflow", "auto");
    getBoundComponent().setStyle("height", "100dvh");
    getBoundComponent().add(columnsLayout);
  }

  private void populateStates() {
    ArrayList<ListItem> listStates = new ArrayList<>();
    states.add(new ListItem("AL", "Alabama"));
    states.add(new ListItem("AK", "Alaska"));
    states.add(new ListItem("AZ", "Arizona"));
    states.add(new ListItem("AR", "Arkansas"));
    states.add(new ListItem("CA", "California"));
    states.add(new ListItem("CO", "Colorado"));
    states.add(new ListItem("CT", "Connecticut"));
    states.add(new ListItem("DE", "Delaware"));
    states.add(new ListItem("FL", "Florida"));
    states.add(new ListItem("GA", "Georgia"));
    states.add(new ListItem("HI", "Hawaii"));
    states.add(new ListItem("ID", "Idaho"));
    states.add(new ListItem("IL", "Illinois"));
    states.add(new ListItem("IN", "Indiana"));
    states.add(new ListItem("IA", "Iowa"));
    states.add(new ListItem("KS", "Kansas"));
    states.add(new ListItem("KY", "Kentucky"));
    states.add(new ListItem("LA", "Louisiana"));
    states.add(new ListItem("ME", "Maine"));
    states.add(new ListItem("MD", "Maryland"));
    states.add(new ListItem("MA", "Massachusetts"));
    states.add(new ListItem("MI", "Michigan"));
    states.add(new ListItem("MN", "Minnesota"));
    states.add(new ListItem("MS", "Mississippi"));
    states.add(new ListItem("MO", "Missouri"));
    states.add(new ListItem("MT", "Montana"));
    states.add(new ListItem("NE", "Nebraska"));
    states.add(new ListItem("NV", "Nevada"));
    states.add(new ListItem("NH", "New Hampshire"));
    states.add(new ListItem("NJ", "New Jersey"));
    states.add(new ListItem("NM", "New Mexico"));
    states.add(new ListItem("NY", "New York"));
    states.add(new ListItem("NC", "North Carolina"));
    states.add(new ListItem("ND", "North Dakota"));
    states.add(new ListItem("OH", "Ohio"));
    states.add(new ListItem("OK", "Oklahoma"));
    states.add(new ListItem("OR", "Oregon"));
    states.add(new ListItem("PA", "Pennsylvania"));
    states.add(new ListItem("RI", "Rhode Island"));
    states.add(new ListItem("SC", "South Carolina"));
    states.add(new ListItem("SD", "South Dakota"));
    states.add(new ListItem("TN", "Tennessee"));
    states.add(new ListItem("TX", "Texas"));
    states.add(new ListItem("UT", "Utah"));
    states.add(new ListItem("VT", "Vermont"));
    states.add(new ListItem("VA", "Virginia"));
    states.add(new ListItem("WA", "Washington"));
    states.add(new ListItem("WV", "West Virginia"));
    states.add(new ListItem("WI", "Wisconsin"));
    states.add(new ListItem("WY", "Wyoming"));

    this.states.insert(listStates);
  }
}