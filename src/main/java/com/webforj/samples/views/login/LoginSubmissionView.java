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

  public LoginSubmissionView() {

    Login login = new Login();
    getBoundComponent().add(login);

    login.open();

    login.onSubmit(ev -> {
      String username = ev.getUsername();
      String password = ev.getPassword();

      if (username.equals("admin") &&
          password.equals("admin")) {
        login.close();
        getBoundComponent().add(new Button("Logout", e -> Page.getCurrent().reload()));
      } else {
        login.setError(true);
        login.setEnabled(true);
      }
    });
  }
}
