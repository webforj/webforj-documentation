package com.webforj.samples.views.flexlayout;

import com.webforj.annotation.StyleSheet;
import com.webforj.component.Composite;
import com.webforj.component.button.Button;
import com.webforj.component.button.ButtonTheme;
import com.webforj.component.list.ChoiceBox;
import com.webforj.component.field.MaskedNumberField;
import com.webforj.component.field.PasswordField;
import com.webforj.component.field.TextField;
import com.webforj.component.html.elements.Div;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@StyleSheet("ws://css/flexlayout/flexLayout.css")
@Route
@FrameTitle("Flex Layout")
public class FlexLayoutView extends Composite<Div> {
  private Div self = getBoundComponent();
  private TextField email;
  private PasswordField password;
  private TextField firstName;
  private TextField lastName;
  private TextField address;
  private TextField city;
  private ChoiceBox states;
  private MaskedNumberField zip;
  private Button submit;
  private Button cancel;

  public FlexLayoutView() {
    email = new TextField("Email");
    password = new PasswordField("Password");
    firstName = new TextField("First Name");
    lastName = new TextField("Last Name");
    address = new TextField("Address");
    city = new TextField("City");
    states = new ChoiceBox("State").setMaxRowCount(7);
    zip = new MaskedNumberField("Zip").setWidth("150px");

    populateStates();
    submit = new Button("Submit", ButtonTheme.PRIMARY);
    cancel = new Button("Cancel", ButtonTheme.DEFAULT);

    FlexLayout mainLayout = FlexLayout.create()
            .vertical()
            .build()
            .addClassName("main__layout");

    FlexLayout rowOne = FlexLayout.create(email, password)
            .horizontal()
            .wrap()
            .build();

    FlexLayout rowTwo = FlexLayout.create(firstName, lastName)
            .horizontal()
            .wrap()
            .build();

    FlexLayout addressRow = FlexLayout.create(address)
            .horizontal()
            .wrap()
            .build();

    FlexLayout cityStateZipRow = FlexLayout.create(city, states, zip)
            .horizontal()
            .justify().between()
            .build()
            .setItemBasis("40%", city)
            .setItemBasis("20%", states)
            .setItemBasis("40%", zip);

    FlexLayout rowFour = FlexLayout.create(cancel, submit)
            .horizontalReverse()
            .build();

    mainLayout.add(rowOne, rowTwo, addressRow, cityStateZipRow, rowFour);
    self.add(mainLayout);
  }

  private void populateStates() {
    states.add("AL", "Alabama");
    states.add("AK", "Alaska");
    states.add("AZ", "Arizona");
    states.add("AR", "Arkansas");
    states.add("CA", "California");
    states.add("CO", "Colorado");
    states.add("CT", "Connecticut");
    states.add("DE", "Delaware");
    states.add("FL", "Florida");
    states.add("GA", "Georgia");
    states.add("HI", "Hawaii");
    states.add("ID", "Idaho");
    states.add("IL", "Illinois");
    states.add("IN", "Indiana");
    states.add("IA", "Iowa");
    states.add("KS", "Kansas");
    states.add("KY", "Kentucky");
    states.add("LA", "Louisiana");
    states.add("ME", "Maine");
    states.add("MD", "Maryland");
    states.add("MA", "Massachusetts");
    states.add("MI", "Michigan");
    states.add("MN", "Minnesota");
    states.add("MS", "Mississippi");
    states.add("MO", "Missouri");
    states.add("MT", "Montana");
    states.add("NE", "Nebraska");
    states.add("NV", "Nevada");
    states.add("NH", "New Hampshire");
    states.add("NJ", "New Jersey");
    states.add("NM", "New Mexico");
    states.add("NY", "New York");
    states.add("NC", "North Carolina");
    states.add("ND", "North Dakota");
    states.add("OH", "Ohio");
    states.add("OK", "Oklahoma");
    states.add("OR", "Oregon");
    states.add("PA", "Pennsylvania");
    states.add("RI", "Rhode Island");
    states.add("SC", "South Carolina");
    states.add("SD", "South Dakota");
    states.add("TN", "Tennessee");
    states.add("TX", "Texas");
    states.add("UT", "Utah");
    states.add("VT", "Vermont");
    states.add("VA", "Virginia");
    states.add("WA", "Washington");
    states.add("WV", "West Virginia");
    states.add("WI", "Wisconsin");
    states.add("WY", "Wyoming");
  }
}
