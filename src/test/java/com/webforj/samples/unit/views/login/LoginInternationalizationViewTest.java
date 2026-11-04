package com.webforj.samples.unit.views.login;

import static com.webforj.testbed.assertion.WebforjAssertions.verifyThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.login.Login;
import com.webforj.samples.views.login.LoginInternationalizationView;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import org.junit.jupiter.api.Test;

@WebforjTest
class LoginInternationalizationViewTest {
  @Test
  void shouldMatchTheSnapshot(WebforjTestbed context) {
    var view = new LoginInternationalizationView();
    context.add(view);

    verifyThat(view).matchesSnapshot();
  }

  @Test
  void shouldShowTheGermanTitleLabelsAndErrorThroughLoginI18n(WebforjTestbed context) {
    context.add(new LoginInternationalizationView());

    var login = ComponentLocator.of(Login.class).locate();
    var i18n = login.getI18n();

    assertEquals("Authentifizierung", i18n.getTitle());
    assertEquals("Benutzername", i18n.getUsername());
    assertEquals("Passwort", i18n.getPassword());
    assertEquals("Angemeldet bleiben", i18n.getRememberMe());
    assertEquals("Anmelden", i18n.getSubmit());
    assertEquals("Falscher Benutzername oder falsches Passwort", i18n.getError().getTitle());
    assertEquals(
        "Stellen Sie sicher, dass Sie den richtigen Benutzernamen und das richtige Passwort"
            + " eingegeben haben und versuchen Sie es erneut.",
        i18n.getError().getMessage());
    assertTrue(login.isError());
  }
}
