package com.webforj.samples.views.avatar

import com.webforj.component.Composite
import com.webforj.component.avatar.AvatarTheme
import com.webforj.component.layout.flexlayout.FlexAlignment
import com.webforj.component.layout.flexlayout.FlexDirection
import com.webforj.component.layout.flexlayout.FlexLayout
import com.webforj.kotlin.dsl.component.avatar.avatar
import com.webforj.kotlin.dsl.component.layout.flexlayout.flexLayout
import com.webforj.router.annotation.FrameTitle
import com.webforj.router.annotation.Route

@Route
@FrameTitle("Avatar Themes")
class AvatarThemesKotlinView: Composite<FlexLayout>() {
  private val self = boundComponent

  init {
    self.apply {
      direction = FlexDirection.COLUMN
      spacing = "var(--dwc-space-m)"
      margin = "var(--dwc-space-l)"
      alignment = FlexAlignment.CENTER
      flexLayout {
        spacing = "var(--dwc-space-m)"
        alignment = FlexAlignment.CENTER
        avatar("Default") { theme = AvatarTheme.DEFAULT }
        avatar("Gray") { theme = AvatarTheme.GRAY }
        avatar("Primary") { theme = AvatarTheme.PRIMARY }
        avatar("Success") { theme = AvatarTheme.SUCCESS }
        avatar("Warning") { theme = AvatarTheme.WARNING }
        avatar("Danger") { theme = AvatarTheme.DANGER }
        avatar("Info") { theme = AvatarTheme.INFO }
      }
      flexLayout {
        spacing = "var(--dwc-space-m)"
        alignment = FlexAlignment.CENTER
        avatar("Default") { theme = AvatarTheme.OUTLINED_DEFAULT }
        avatar("Gray") { theme = AvatarTheme.OUTLINED_GRAY }
        avatar("Primary") { theme = AvatarTheme.OUTLINED_PRIMARY }
        avatar("Success") { theme = AvatarTheme.OUTLINED_SUCCESS }
        avatar("Warning") { theme = AvatarTheme.OUTLINED_WARNING }
        avatar("Danger") { theme = AvatarTheme.OUTLINED_DANGER }
        avatar("Info") { theme = AvatarTheme.OUTLINED_INFO }
      }
    }
  }
}
