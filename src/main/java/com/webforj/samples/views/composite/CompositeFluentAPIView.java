package com.webforj.samples.views.composite;

import com.webforj.annotation.StyleSheet;
import com.webforj.component.Composite;
import com.webforj.component.button.Button;
import com.webforj.component.button.ButtonTheme;
import com.webforj.component.field.TextField;
import com.webforj.component.html.elements.Div;
import com.webforj.component.html.elements.H1;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@FrameTitle("Fluent API")
@StyleSheet("ws://composite/composite.css")
public class CompositeFluentAPIView extends Composite<Div> {

  H1 title = new H1("User Profile Form");
  UserProfileForm form = new UserProfileForm();

  public CompositeFluentAPIView() {
    getBoundComponent().addClassName("frame");

    form.setUserName("John Doe")
        .setUserEmail("john@example.com")
        .setSubmitText("Update Profile");

    getBoundComponent().add(title, form);
  }

  public static class UserProfileForm extends Composite<FlexLayout> {
    
    private TextField nameField;
    private TextField emailField;
    private Button submitButton;

    public UserProfileForm() {
      initializeComponents();
      setupLayout();
    }

    private void initializeComponents() {
      nameField = new TextField()
          .setLabel("Full Name");

      emailField = new TextField()
          .setLabel("Email Address");

      submitButton = new Button("Save")
          .setTheme(ButtonTheme.PRIMARY);
    }

    private void setupLayout() {
      getBoundComponent()
          .setDirection(FlexDirection.COLUMN)
          .setSpacing("var(--dwc-space-l)")
          .add(nameField, emailField, submitButton);
    }

    public UserProfileForm setUserName(String name) {
      nameField.setValue(name);
      return this;
    }
    
    public UserProfileForm setUserEmail(String email) {
      emailField.setValue(email);
      return this;
    }
    
    public UserProfileForm setSubmitText(String text) {
      submitButton.setText(text);
      return this;
    }
    
    public UserProfileForm setRequired(boolean required) {
      nameField.setRequired(required);
      emailField.setRequired(required);
      return this;
    }
  }
}