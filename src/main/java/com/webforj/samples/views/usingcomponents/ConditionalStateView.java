package com.webforj.samples.views.usingcomponents;

import com.webforj.component.Composite;
import com.webforj.component.Expanse;
import com.webforj.component.button.Button;
import com.webforj.component.button.ButtonTheme;
import com.webforj.component.card.Card;
import com.webforj.component.field.PasswordField;
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
@FrameTitle("Conditional State View")
public class ConditionalStateView extends Composite<FlexLayout> {

  private final FlexLayout self = getBoundComponent();
  private final TextField usernameField = new TextField("Username");
  private final PasswordField passwordField = new PasswordField("Password");
  private final Button submitButton = new Button("Sign In", ButtonTheme.PRIMARY);
  private final Card card = new Card();

  public ConditionalStateView() {
    initializeComponents();
    setupLayout();
    setupEventHandlers();
  }

  private void initializeComponents() {
    usernameField.setExpanse(Expanse.LARGE);
    usernameField.setPlaceholder("Enter your username");

    passwordField.setExpanse(Expanse.LARGE);
    passwordField.setPlaceholder("Enter your password");

    submitButton.setExpanse(Expanse.LARGE);
    submitButton.setEnabled(false);
  }

  private void setupLayout() {

    card.setWidth(400);
    card.setMaxWidth("100%");
    card.setExpanse(Expanse.XLARGE);
    card.setStyle("text-align", "center");

    card.addToTitle(new H2("Welcome Back"));
    card.addToCaption(new Paragraph("Sign in to your account"));

    FlexLayout form = new FlexLayout();
    form.setDirection(FlexDirection.COLUMN);
    form.setSpacing("var(--dwc-space-l)");
    form.add(usernameField, passwordField, submitButton);

    card.addToBody(form);

    self.setDirection(FlexDirection.COLUMN);
    self.setAlignment(FlexAlignment.CENTER);
    self.setJustifyContent(FlexJustifyContent.CENTER);
    self.setPadding("var(--dwc-space-2xl)");
    self.setHeight("100vh");
    self.add(card);
  }

  private void setupEventHandlers() {
    usernameField.addValueChangeListener(event -> checkFieldsAndUpdateButton());
    passwordField.addValueChangeListener(event -> checkFieldsAndUpdateButton());

    submitButton.onClick(
        e -> {
          Toast.show("Signed in as " + usernameField.getValue());
        });
  }

  private void checkFieldsAndUpdateButton() {
    String username = usernameField.getValue().trim();
    String password = passwordField.getValue().trim();
    submitButton.setEnabled(!username.isEmpty() && !password.isEmpty());
  }
}
