package com.webforj.samples.views.badge

import com.webforj.component.Composite
import com.webforj.component.badge.BadgeExpanse
import com.webforj.component.badge.BadgeTheme
import com.webforj.component.layout.flexlayout.FlexDirection
import com.webforj.component.layout.flexlayout.FlexLayout
import com.webforj.concern.HasComponents
import com.webforj.kotlin.dsl.component.badge.badge
import com.webforj.kotlin.dsl.component.html.elements.h3
import com.webforj.kotlin.dsl.component.html.elements.paragraph
import com.webforj.kotlin.dsl.component.tabbedpane.contentSlot
import com.webforj.kotlin.dsl.component.tabbedpane.suffixSlot
import com.webforj.kotlin.dsl.component.tabbedpane.tab
import com.webforj.kotlin.dsl.component.tabbedpane.tabbedPane
import com.webforj.kotlin.extension.px
import com.webforj.router.annotation.FrameTitle
import com.webforj.router.annotation.Route

@Route
@FrameTitle("Badge - Tabbed Pane")
class BadgeTabbedPaneKotlinView: Composite<FlexLayout>() {
  private val self = boundComponent

  init {
    self.apply {
      direction = FlexDirection.COLUMN
      spacing = "var(--dwc-space-m)"
      padding = "var(--dwc-space-l)"
      margin = "0 auto"
      maxWidth = 700.px
      h3("Badge in Tabbed Pane")
      filledBadgePane()
      h3("Outlined Badges in Tabs")
      outlinedBadgePane()
    }
  }

  private fun FlexLayout.filledBadgePane() {
    tabbedPane {
      tab("Inbox") {
        suffixSlot { createBadge("12", BadgeTheme.PRIMARY, BadgeExpanse.XSMALL) }
        contentSlot {
          paragraph("You have 12 messages in you inbox.")
        }
      }
      tab("Notifications") {
        suffixSlot { createBadge("3", BadgeTheme.DANGER, BadgeExpanse.XSMALL) }
        contentSlot {
          paragraph("You have 3 unread notifications.")
        }
      }
      tab("Drafts") {
        suffixSlot { createBadge("1", BadgeTheme.GRAY, BadgeExpanse.XSMALL) }
        contentSlot {
          paragraph("You have 1 draft saved.")
        }
      }
      tab("Sent") {
        contentSlot {
          paragraph("You sent messages.")
        }
      }
    }
  }

  private fun FlexLayout.outlinedBadgePane() {
    tabbedPane {
      tab("Taskes") {
        suffixSlot { createBadge("5", BadgeTheme.OUTLINED_PRIMARY, BadgeExpanse.XSMALL) }
        contentSlot {
          paragraph("Your pending tasks.")
        }
      }
      tab("Issues") {
        suffixSlot { createBadge("8", BadgeTheme.OUTLINED_DANGER, BadgeExpanse.XSMALL) }
        contentSlot {
          paragraph("Open issues that need attention.")
        }
      }
      tab("Done") {
        suffixSlot { createBadge("24", BadgeTheme.OUTLINED_SUCCESS, BadgeExpanse.XSMALL) }
        contentSlot {
          paragraph("Completed Items.")
        }
      }
    }
  }

  private fun HasComponents.createBadge(text: String, theme: BadgeTheme, expanse: BadgeExpanse) =
    badge(text) {
      this.theme = theme
      this.expanse = expanse
    }
}
