package com.webforj.samples.views.avatar

import com.webforj.annotation.StyleSheet
import com.webforj.component.Composite
import com.webforj.component.avatar.AvatarExpanse
import com.webforj.component.avatar.AvatarTheme
import com.webforj.component.button.ButtonTheme
import com.webforj.component.html.elements.Div
import com.webforj.component.layout.flexlayout.FlexAlignment
import com.webforj.component.layout.flexlayout.FlexDirection
import com.webforj.component.layout.flexlayout.FlexLayout
import com.webforj.kotlin.dsl.component.avatar.avatar
import com.webforj.kotlin.dsl.component.button.button
import com.webforj.kotlin.dsl.component.dialog.dialog
import com.webforj.kotlin.dsl.component.html.elements.div
import com.webforj.kotlin.dsl.component.html.elements.h4
import com.webforj.kotlin.dsl.component.html.elements.img
import com.webforj.kotlin.dsl.component.html.elements.span
import com.webforj.kotlin.dsl.component.icons.tablerIcon
import com.webforj.kotlin.dsl.component.layout.flexlayout.flexLayout
import com.webforj.kotlin.dsl.component.layout.flexlayout.horizontal
import com.webforj.kotlin.extension.classNames
import com.webforj.kotlin.extension.plusAssign
import com.webforj.kotlin.extension.set
import com.webforj.kotlin.extension.styles
import com.webforj.router.annotation.FrameTitle
import com.webforj.router.annotation.Route

@Route
@FrameTitle("Team Members")
@StyleSheet("ws://css/avatar/avatar.css")
class AvatarKotlinView: Composite<FlexLayout>() {
  private val self = boundComponent

  init {
      self.apply {
        direction = FlexDirection.COLUMN
        margin = "var(--dwc-space-l)"

        div {
          classNames += "avatar-demo__panel"

          flexLayout(FlexDirection.ROW) {
            classNames += "avatar-demo__project-header"
            spacing = "var(--dwc-space-m)"
            alignment = FlexAlignment.CENTER

            tablerIcon("folder")
            h4("Project Alpha") {
              classNames += "avatar-demo__project-name"
            }
          }
          span("Team") {
            classNames += "avatar-demo__section-label"
          }
          createMember("Sarah Chen", "Product Lead",
            "ws://img/avatar/avatar1.png", AvatarTheme.SUCCESS)
          createMember("Marcus Johnson", "Developer",
            null, AvatarTheme.SUCCESS)
          createMember("Elena Rodriguez", "Designer",
            "ws://img/avatar/avatar2.png", AvatarTheme.WARNING)
          createMember("David Kim", "Developer",
            null, AvatarTheme.GRAY)
          createInviteMember()
        }
      }
  }

  private fun Div.createMember(name: String, role: String, imageUrl: String?, avatarTheme: AvatarTheme) {
    flexLayout {
      horizontal()
      alignment = FlexAlignment.CENTER
      classNames += "avatar-demo__row"
      spacing = "var(--dwc-space-m)"

      avatar(name) {
        imageUrl?.let { img(it, name) }
        theme = avatarTheme

        onClick { showProfileDialog(name, role, imageUrl, avatarTheme) }
      }
      flexLayout(FlexDirection.COLUMN) {
        classNames += "avatar-demo__info"

        span(name) {
          classNames += "avatar-demo__name"
        }
        span(role) {
          classNames += "avatar-demo__role"
        }
      }
    }
  }

  private fun Div.createInviteMember() {
    flexLayout {
      horizontal()
      alignment = FlexAlignment.CENTER
      classNames += "avatar-demo__row"
      spacing = "var(--dwc-space-m)"

      avatar {
        theme = AvatarTheme.OUTLINED_GRAY

        tablerIcon("user")
      }
      span("Invite Member") {
        classNames += "avatar-demo__name"
      }
    }
  }

  private fun showProfileDialog(name: String, role: String, imageUrl: String?, avatarTheme: AvatarTheme) {
    self.apply {
      dialog {
        maxWidth = "260px"

        flexLayout(FlexDirection.COLUMN) {
          alignment = FlexAlignment.CENTER
          styles["padding"] = "var(--dwc-space-l)"
          spacing = "var(--dwc-space-s)"

          avatar(name) {
            imageUrl?.let { img(it, name) }
            expanse = AvatarExpanse.XXLARGE
            theme = avatarTheme
          }
          h4(name) {
            styles["margin"] = "0"
          }
          span(role) {
            styles["color"] = "var(--dwc-color-default-text)"
          }
          button("View Profile", ButtonTheme.PRIMARY)
        }
        open()
      }
    }
  }

}
