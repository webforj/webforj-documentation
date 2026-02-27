package com.webforj.samples.views.login;

import com.webforj.component.Composite;
import com.webforj.component.html.elements.Div;
import com.webforj.component.login.Login;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@FrameTitle("Login Basics")
public class LoginBasicView extends Composite<Div> {
  private final Div self = getBoundComponent();

  public LoginBasicView() {
    // Create and open the login component
    Login login = new Login();
    login.open();

    self.add(login);
  }
}
