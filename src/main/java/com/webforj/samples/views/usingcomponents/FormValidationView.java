package com.webforj.samples.views.usingcomponents;

import com.webforj.component.Composite;
import com.webforj.component.Expanse;
import com.webforj.component.button.Button;
import com.webforj.component.button.ButtonTheme;
import com.webforj.component.card.Card;
import com.webforj.component.field.TextArea;
import com.webforj.component.field.TextField;
import com.webforj.component.html.elements.H2;
import com.webforj.component.html.elements.Paragraph;
import com.webforj.component.layout.flexlayout.FlexAlignment;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexJustifyContent;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.toast.Toast;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@FrameTitle("Form Validation View")
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
    Card card = new Card();
    card.setWidth(400);
    card.setMaxWidth("100%");
    card.setExpanse(Expanse.XLARGE);
    card.setStyle("text-align", "center");
    card.addToTitle(new H2("Contact Us"));
    card.addToCaption(new Paragraph("We'd love to hear from you"));

    FlexLayout form = new FlexLayout();
    form.setDirection(FlexDirection.COLUMN);
    form.setSpacing("var(--dwc-space-l)");
    form.add(nameField, emailField, messageField, submitButton);
    card.addToBody(form);

    self.setDirection(FlexDirection.COLUMN);
    self.setAlignment(FlexAlignment.CENTER);
    self.setJustifyContent(FlexJustifyContent.CENTER);
    self.setPadding("var(--dwc-space-2xl)");
    self.setHeight("100vh");
    self.add(card);
  }

  private void setupValidation() {
    nameField.addValueChangeListener(e -> validateForm());
    emailField.addValueChangeListener(e -> validateForm());
    messageField.addValueChangeListener(e -> validateForm());

    submitButton.onClick(
        e -> {
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
