package com.webforj.samples.views.usingcomponents;

import com.webforj.annotation.StyleSheet;
import com.webforj.component.Composite;
import com.webforj.component.button.Button;
import com.webforj.component.button.ButtonTheme;
import com.webforj.component.field.TextArea;
import com.webforj.component.field.TextField;
import com.webforj.component.html.elements.Paragraph;
import com.webforj.component.layout.flexlayout.FlexAlignment;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.toast.Toast;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@FrameTitle("Form Validation View")
@StyleSheet("ws://usingcomponents/formvalidation.css")
public class FormValidationView extends Composite<FlexLayout> {
    
  private final FlexLayout self = getBoundComponent();
  
  private TextField nameField;
  private TextField emailField;
  private TextArea messageField;
  private Button submitButton;
  
  public FormValidationView() {
    initializeComponents();
    setupLayout();
    setupValidation();
  }
  
  private void initializeComponents() {
    nameField = new TextField("Name");
    nameField.setPlaceholder("Your name");
      
    emailField = new TextField("Email");
    emailField.setPlaceholder("you@example.com");
      
    messageField = new TextArea("Message");
    messageField.setPlaceholder("How can we help you?");
    messageField.setMaxHeight("150px");
      
    submitButton = new Button("Send Message", ButtonTheme.PRIMARY);
    submitButton.setEnabled(false);
  }
  
  private void setupLayout() {
    Paragraph title = new Paragraph("Contact Us");
    title.addClassName("contact-title");
    title.setStyle("font-size", "1.5rem");
    title.setStyle("font-weight", "600");
      
    Paragraph subtitle = new Paragraph("We'd love to hear from you");
    subtitle.addClassName("contact-subtitle");
      
    FlexLayout form = new FlexLayout();
    form.setDirection(FlexDirection.COLUMN);
    form.setSpacing("var(--dwc-space-l)");
    form.addClassName("contact-card");
    form.add(title, subtitle, nameField, emailField, messageField, submitButton);
      
    self.setDirection(FlexDirection.COLUMN);
    self.setAlignment(FlexAlignment.CENTER);
    self.addClassName("contact-container");
    self.add(form);
  }
  
  private void setupValidation() {
    nameField.addValueChangeListener(e -> validateForm());
    emailField.addValueChangeListener(e -> validateForm());
    messageField.addValueChangeListener(e -> validateForm());
      
    submitButton.onClick(e -> {
      Toast.show("Message sent from " + nameField.getValue() + "!");
    });
  }
  
  private void validateForm() {
    String name = nameField.getValue();
    String email = emailField.getValue();
    String message = messageField.getValue();
      
    boolean nameValid = !name.isEmpty();
    boolean emailValid = email.contains("@");
    boolean messageValid = message.length() >= 10;
      
    submitButton.setEnabled(nameValid && emailValid && messageValid);
  }
}