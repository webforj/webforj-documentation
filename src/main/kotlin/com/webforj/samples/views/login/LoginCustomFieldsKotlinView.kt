package com.webforj.samples.views.login

import com.webforj.Page
import com.webforj.annotation.StyleSheet
import com.webforj.component.Composite
import com.webforj.component.field.TextField
import com.webforj.component.html.elements.Div
import com.webforj.kotlin.dsl.component.button.button
import com.webforj.kotlin.dsl.component.field.textField
import com.webforj.kotlin.dsl.component.html.elements.paragraph
import com.webforj.kotlin.dsl.component.login.beforeFormSlot
import com.webforj.kotlin.dsl.component.login.login
import com.webforj.kotlin.dsl.component.login.loginErrorI18n
import com.webforj.kotlin.dsl.component.login.loginI18n
import com.webforj.kotlin.extension.classNames
import com.webforj.kotlin.extension.plus
import com.webforj.router.annotation.FrameTitle
import com.webforj.router.annotation.Route

@StyleSheet("ws://css/login/loginCustomFields.css")
@Route
@FrameTitle("Login Custom Fields")
class LoginCustomFieldsKotlinView: Composite<Div>() {
  private val self = boundComponent
  private lateinit var customerId: TextField

  init {
    self.apply {
      login {
        classNames + "login-form"
        beforeFormSlot {
          paragraph("Please enter your customer ID, email address and password to enter the customer portal.")
          customerId = textField("Customer ID") {
            name = "customer-id"
            isRequired = true
          }
        }
        loginI18n {
          loginErrorI18n {
            title = "Incorrect Customer ID, username or password"
            message = """
              <ul part="list">
                  <li>Customer : Tesla</li>
                  <li>Username : admin</li>
                  <li>Password : admin</li>
              </ul>
            """.trimIndent()
          }
        }
        open()
        onSubmit {
          val id = it.data["customer-id"]
          if (it.username == "admin" &&
            it.password == "admin" &&
            id == "Tesla") {
            close()
            self.apply {
              button("Logout") {
                onClick { Page.getCurrent().reload() }
              }
            }
          } else {
            isError = true
            isEnabled = true
            customerId.focus()
          }
        }
      }
    }
  }
}
