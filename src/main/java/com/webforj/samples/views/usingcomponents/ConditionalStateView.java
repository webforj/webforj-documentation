package com.webforj.samples.views.usingcomponents;

import com.webforj.annotation.StyleSheet;
import com.webforj.component.Composite;
import com.webforj.component.Expanse;
import com.webforj.component.button.Button;
import com.webforj.component.button.ButtonTheme;
import com.webforj.component.field.PasswordField;
import com.webforj.component.field.TextField;
import com.webforj.component.html.elements.Div;
import com.webforj.component.html.elements.H2;
import com.webforj.component.html.elements.Paragraph;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.layout.flexlayout.FlexAlignment;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.toast.Toast;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@FrameTitle("Conditional State View")
@StyleSheet("ws://usingcomponents/conditionalstate.css")
public class ConditionalStateView extends Composite<FlexLayout> {

    private final FlexLayout self = getBoundComponent();

    private TextField usernameField;
    private PasswordField passwordField;
    private Button submitButton;

    public ConditionalStateView() {
        initializeComponents();
        setupLayout();
        setupEventHandlers();
    }

    private void initializeComponents() {
        usernameField = new TextField("Username");
        usernameField.setExpanse(Expanse.LARGE);
        usernameField.setPlaceholder("Enter your username");

        passwordField = new PasswordField("Password");
        passwordField.setExpanse(Expanse.LARGE);
        passwordField.setPlaceholder("Enter your password");

        submitButton = new Button("Sign In", ButtonTheme.PRIMARY);
        submitButton.setExpanse(Expanse.LARGE);
        submitButton.setVisible(false);
    }

    private void setupLayout() {
        Div card = new Div();
        card.addClassName("conditional-card");

        H2 title = new H2("Welcome Back");
        title.addClassName("conditional-title");

        Paragraph subtitle = new Paragraph("Sign in to your account");
        subtitle.addClassName("conditional-subtitle");

        FlexLayout form = new FlexLayout();
        form.setDirection(FlexDirection.COLUMN);
        form.setSpacing("var(--dwc-space-l)");
        form.add(usernameField, passwordField, submitButton);

        FlexLayout cardContent = new FlexLayout();
        cardContent.setDirection(FlexDirection.COLUMN);
        cardContent.add(title, subtitle, form);

        card.add(cardContent);

        self.setDirection(FlexDirection.COLUMN);
        self.setAlignment(FlexAlignment.CENTER);
        self.addClassName("conditional-container");
        self.add(card);
    }

    private void setupEventHandlers() {
        usernameField.addValueChangeListener(event -> checkFieldsAndToggleButton());
        passwordField.addValueChangeListener(event -> checkFieldsAndToggleButton());
        
        submitButton.onClick(e -> {
            Toast.show("Signed in as " + usernameField.getValue());
        });
    }

    private void checkFieldsAndToggleButton() {
        String username = usernameField.getValue().trim();
        String password = passwordField.getValue().trim();

        boolean bothFieldsFilled = !username.isEmpty() && !password.isEmpty();

        submitButton.setVisible(bothFieldsFilled);
    }
}