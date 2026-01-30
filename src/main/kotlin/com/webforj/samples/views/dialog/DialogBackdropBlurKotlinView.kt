package com.webforj.samples.views.dialog

import com.webforj.annotation.InlineStyleSheet
import com.webforj.component.Composite
import com.webforj.component.layout.flexlayout.FlexLayout
import com.webforj.kotlin.dsl.component.button.button
import com.webforj.kotlin.dsl.component.dialog.dialog
import com.webforj.kotlin.dsl.component.dialog.addToHeader
import com.webforj.kotlin.dsl.component.html.elements.div
import com.webforj.kotlin.extension.set
import com.webforj.kotlin.extension.styles
import com.webforj.router.annotation.FrameTitle
import com.webforj.router.annotation.Route

@InlineStyleSheet(
  id = "login-form", value =  /* css */"""
  .loginForm {
    background-color: #263238;
    background-image: url(https://images.pling.com/img/00/00/59/97/06/1588511/1c58fba17fc4c48cd52cf17dd3f36556396e73e34a3d37e5aec6098ccdb01f3d1867.jpg);
    background-size: 100% 100%;
    background-repeat: no-repeat;
  }
"""
)
@Route
@FrameTitle("Dialog Backdrop Blur")
class DialogBackdropBlurKotlinView: Composite<FlexLayout>() {
  private val self = boundComponent

  init {
      self.apply {
        dialog {
          addClassName("loginForm")
          addToHeader { div("Background Blur") }
          button("Toggle Background Blur") {
            styles["display"] = "flex"
            styles["justify-content"] = "center"
            onClick {
              this@dialog.isBlurred = !this@dialog.isBlurred
            }
          }
          setCloseable(false)
          open()
        }
      }
  }
}
