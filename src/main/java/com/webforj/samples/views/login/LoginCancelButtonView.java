package com.webforj.samples.views.login;

import com.webforj.component.Composite;
import com.webforj.component.html.elements.Div;
import com.webforj.component.login.Login;
import com.webforj.component.login.LoginI18n;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@FrameTitle("Login Cancel Button")
public class LoginCancelButtonView extends Composite<Div> {
  private final Div self = getBoundComponent();

  public LoginCancelButtonView() {
    // Create login with custom cancel button text
    Login login = new Login();

    LoginI18n i18n = new LoginI18n();
    i18n.setCancel("Cancel");
    login.setI18n(i18n);

    // Handle cancel action
    login.onCancel(ev -> {
      // Close the login dialog and do something else
    });

    login.open();
    self.add(login);
  }
}
