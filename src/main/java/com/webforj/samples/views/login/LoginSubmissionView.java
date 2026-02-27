package com.webforj.samples.views.login;

import com.webforj.Page;
import com.webforj.component.Composite;
import com.webforj.component.button.Button;
import com.webforj.component.html.elements.Div;
import com.webforj.component.login.Login;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@FrameTitle("Login Submission")
public class LoginSubmissionView extends Composite<Div> {
  private final Div self = getBoundComponent();

  public LoginSubmissionView() {
    // Create login component
    Login login = new Login();

    // Handle form submission
    login.onSubmit(ev -> {
      String username = ev.getUsername();
      String password = ev.getPassword();

      // Validate credentials (admin/admin)
      if (username.equals("admin") && password.equals("admin")) {
        login.close();
        self.add(new Button("Logout", e -> Page.getCurrent().reload()));
      } else {
        login.setError(true)
            .setEnabled(true);
      }
    });

    login.open();
    self.add(login);
  }
}
