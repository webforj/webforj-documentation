package com.webforj.samples.views.login

import com.webforj.Page
import com.webforj.component.Composite
import com.webforj.component.html.elements.Div
import com.webforj.kotlin.dsl.component.button.button
import com.webforj.kotlin.dsl.component.login.login
import com.webforj.router.annotation.FrameTitle
import com.webforj.router.annotation.Route

@Route
@FrameTitle("Login Submission")
class LoginSubmissionKotlinView: Composite<Div>() {

  init {
    boundComponent.apply {
      login {
        open()
        onSubmit {
          if (it.username == "admin" && it.password == "admin") {
            close()
            this@apply.button("Logout").onClick {
              Page.getCurrent().reload()
            }
          } else {
            isError = true
            isEnabled = true
          }
        }
      }
    }
  }
}
