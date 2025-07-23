package com.webforj.samples.views.login;

import com.webforj.component.Composite;
import com.webforj.component.html.elements.Div;
import com.webforj.component.login.Login;
import com.webforj.component.login.LoginI18n;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@FrameTitle("Login Internationalization")
public class LoginInternationalizationView extends Composite<Div> {

  public LoginInternationalizationView() {
    Login login = new Login();
    LoginI18n i18n = new LoginI18n();
    i18n.setTitle("Authentifizierung");
    i18n.setUsername("Benutzername");
    i18n.setPassword("Passwort");
    i18n.setRememberMe("Angemeldet bleiben");
    i18n.setSubmit("Anmelden");
    i18n.getError().setTitle("Falscher Benutzername oder falsches Passwort");
    i18n.getError().setMessage(
        "Stellen Sie sicher, dass Sie den richtigen Benutzernamen und das richtige Passwort eingegeben haben und versuchen Sie es erneut.");

    login.setI18n(i18n);
    login.setError(true);
    login.open();

    getBoundComponent().add(login);
  }
}
