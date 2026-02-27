package com.webforj.samples.views.login;

import com.webforj.Page;
import com.webforj.annotation.StyleSheet;
import com.webforj.component.Composite;
import com.webforj.component.button.Button;
import com.webforj.component.field.TextField;
import com.webforj.component.html.elements.Div;
import com.webforj.component.html.elements.Paragraph;
import com.webforj.component.login.Login;
import com.webforj.component.login.LoginI18n;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@StyleSheet("ws://css/login/loginCustomFields.css")
@Route
@FrameTitle("Login Custom Fields")
public class LoginCustomFieldsView extends Composite<Div> {
  private final Div self = getBoundComponent();
  // Custom field for customer ID
  private final TextField customerId = new TextField("Customer ID");

  public LoginCustomFieldsView() {
    // Configure custom field
    customerId.setName("customer-id")
        .setRequired(true);

    // Create login and add custom fields
    Login login = new Login();
    login.addClassName("login-form");

    // Add instruction text and custom field before the form
    login.addToBeforeForm(
        new Paragraph("Please enter your customer ID, email address and password to enter the customer portal."));
    login.addToBeforeForm(customerId);

    // Set custom error messages using text block
    LoginI18n i18n = new LoginI18n();
    i18n.getError().setTitle("Incorrect Customer ID, username or password");
    i18n.getError().setMessage("""
        <ul part="list">
          <li>Customer : Tesla</li>
          <li>Username : admin</li>
          <li>Password : admin</li>
        </ul>
        """);
    login.setI18n(i18n);

    // Handle form submission
    login.onSubmit(ev -> {
      String id = (String) ev.getData().get("customer-id");
      String username = ev.getUsername();
      String password = ev.getPassword();

      // Validate credentials: admin/admin with customer ID "Tesla"
      if (username.equals("admin") && password.equals("admin") && id.equals("Tesla")) {
        login.close();
        self.add(new Button("Logout", e -> Page.getCurrent().reload()));
      } else {
        login.setError(true)
            .setEnabled(true);
        customerId.focus();
      }
    });

    login.open();
    self.add(login);
  }
}
