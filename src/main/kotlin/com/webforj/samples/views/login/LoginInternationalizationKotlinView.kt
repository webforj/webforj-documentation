package com.webforj.samples.views.login

import com.webforj.component.Composite
import com.webforj.component.html.elements.Div
import com.webforj.kotlin.dsl.component.login.login
import com.webforj.kotlin.dsl.component.login.loginErrorI18n
import com.webforj.kotlin.dsl.component.login.loginI18n
import com.webforj.router.annotation.FrameTitle
import com.webforj.router.annotation.Route

@Route
@FrameTitle("Login Internationalization")
class LoginInternationalizationKotlinView: Composite<Div>() {

  init {
      boundComponent.apply {
        login {
          loginI18n {
            title = "Authentifizierung"
            username = "Benutzername"
            password = "Passwort"
            rememberMe = "Angemeldet bleiben"
            submit = "Anmelden"
            loginErrorI18n {
              title = "Falscher Benutzername oder falsches Passwort"
              message =
                "Stellen Sie sicher, dass Sie den richtigen Benutzernamen und das richtige Passwort eingegeben haben und versuchen Sie es erneut."
            }
          }
          isError = true
          open()
        }
      }
  }
}
