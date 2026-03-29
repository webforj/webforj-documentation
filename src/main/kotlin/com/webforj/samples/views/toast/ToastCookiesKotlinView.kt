package com.webforj.samples.views.toast

import com.webforj.component.Composite
import com.webforj.component.Theme
import com.webforj.component.button.ButtonTheme
import com.webforj.component.html.elements.Div
import com.webforj.component.layout.flexlayout.FlexAlignment
import com.webforj.component.toast.Toast
import com.webforj.kotlin.dsl.component.button.button
import com.webforj.kotlin.dsl.component.html.elements.anchor
import com.webforj.kotlin.dsl.component.html.elements.paragraph
import com.webforj.kotlin.dsl.component.icons.tablerIcon
import com.webforj.kotlin.dsl.component.layout.flexlayout.flexLayout
import com.webforj.kotlin.dsl.component.layout.flexlayout.horizontal
import com.webforj.kotlin.dsl.component.layout.flexlayout.vertical
import com.webforj.kotlin.extension.px
import com.webforj.kotlin.extension.set
import com.webforj.kotlin.extension.styles
import com.webforj.router.annotation.FrameTitle
import com.webforj.router.annotation.Route

@Route
@FrameTitle("Toast Cookies")
class ToastCookiesKotlinView : Composite<Div>() {
  private val cookiesToast = Toast()

  init {
    createAndOpenToast()
  }

  private fun createAndOpenToast() {
    cookiesToast.apply {
      duration = -1
      theme = Theme.DEFAULT
      placement = Toast.Placement.CENTER
      open()
      flexLayout {
        vertical()
        alignment = FlexAlignment.CENTER
        spacing = "var(--dwc-space-m)"
        tablerIcon("cookie") {
          styles["width"] = 100.px
          styles["height"] = 100.px
        }
        paragraph {
          text = """
            We use cookies to improve your experience.
            By clicking 'Accept all cookies', you agree to our
          """.trimIndent()
          anchor("#", "Cookie Policy")
        }
        flexLayout {
          horizontal()
          spacing = "var(--dwc-space-l)";
          button("Accept all cookies", ButtonTheme.PRIMARY) {
            onClick {
              close()
              createAndOpenToast()
            }
          }
          button("Necessary cookies only", ButtonTheme.OUTLINED_PRIMARY) {
            onClick {
              close()
              createAndOpenToast()
            }
          }
        }
      }
    }
  }
}
