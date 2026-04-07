package com.webforj.samples.views.login

import com.webforj.component.Composite
import com.webforj.component.html.elements.Div
import com.webforj.kotlin.dsl.component.login.login
import com.webforj.kotlin.dsl.component.login.loginI18n
import com.webforj.router.annotation.FrameTitle
import com.webforj.router.annotation.Route

@Route
@FrameTitle("Login Cancel Button")
class LoginCancelButtonKotlinView: Composite<Div>() {
  private val self = boundComponent

  init {
      self.apply {
        login {
          loginI18n {
            cancel = "Cancel"
          }
          onCancel {
            // Close the login dialog and do something else
          }
          open()
        }
      }
  }
}
