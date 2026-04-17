package com.webforj.samples.views.markdownviewer

import com.webforj.Interval
import com.webforj.annotation.StyleSheet
import com.webforj.component.Composite
import com.webforj.component.Theme
import com.webforj.component.event.KeypressEvent
import com.webforj.component.field.TextField
import com.webforj.component.html.elements.Div
import com.webforj.component.icons.IconButton
import com.webforj.component.icons.TablerIcon
import com.webforj.component.layout.flexlayout.FlexDirection
import com.webforj.component.layout.flexlayout.FlexLayout
import com.webforj.component.markdown.MarkdownViewer
import com.webforj.kotlin.dsl.component.field.textField
import com.webforj.kotlin.dsl.component.html.elements.div
import com.webforj.kotlin.dsl.component.html.elements.span
import com.webforj.kotlin.dsl.component.icons.iconButton
import com.webforj.kotlin.dsl.component.icons.tablerIcon
import com.webforj.kotlin.dsl.component.layout.flexlayout.flexLayout
import com.webforj.kotlin.dsl.component.layout.flexlayout.vertical
import com.webforj.kotlin.dsl.component.markdown.markdownViewer
import com.webforj.kotlin.extension.*
import com.webforj.router.annotation.FrameTitle
import com.webforj.router.annotation.Route
import java.util.Random
import kotlin.math.min

@Route
@FrameTitle("Streaming Demo")
@StyleSheet("ws://css/markdownviewer/markdownviewerstreaming.css")
class MarkdownViewerStreamingKotlinView : Composite<FlexLayout>() {
  private val responses = listOf(
    """
      ## Quick Pasta Recipe 🍝

      Here's a simple **aglio e olio** you can make in 15 minutes:

      1. Boil spaghetti until al dente
      2. Sauté sliced garlic in olive oil
      3. Add red pepper flakes
      4. Toss with pasta and parsley

      > Pro tip: Save some pasta water to make the sauce silky!

      """.trimIndent(),
    """
      ### Random Facts About Space

      The universe is **wild**. Consider this:

      - A day on Venus is longer than its year
      - There's a planet made of diamonds (55 Cancri e)
      - Space is completely silent
      - Neutron stars can spin 600 times per second

      | Planet | Fun Fact |
      |--------|----------|
      | Jupiter | Has 95 known moons |
      | Saturn | Could float in water |
      | Mars | Home to the tallest volcano |

      """.trimIndent(),
    """
      Here's a quick **productivity tip**:

      The **Pomodoro Technique** works like this:

      ```
      1. Work for 25 minutes
      2. Take a 5-minute break
      3. Repeat 4 times
      4. Take a longer 15-30 min break
      ```

      It's simple but effective for maintaining focus throughout the day!

      > "Focus is more about saying no than saying yes." - Steve Jobs

      """.trimIndent()
  )
  private val self = boundComponent
  private val messageArea: FlexLayout
  private lateinit var viewer: MarkdownViewer
  private lateinit var input: TextField
  private lateinit var sendButton: IconButton
  private lateinit var stopButton: IconButton
  private var thinkingIndicator: Div? = null
  private val random = Random()
  private var delayInterval: Interval? = null
  private var streamInterval: Interval? = null

  init {
    self.apply {
      direction = FlexDirection.COLUMN
      classNames + "chat"
      styles["overflow"] = "hidden"

      div {
        classNames + "chat__header"

        tablerIcon("message-chatbot")
        span("AI Chat Demo")
      }
      messageArea = flexLayout {
        vertical()
        classNames + "chat__messages"
        styles["overflowY"] = "auto"

        viewer = markdownViewer {
          isProgressiveRender = true
          isAutoScroll = true
          renderSpeed = 6
        }
      }
      setItemGrow(1.0, messageArea)
      div {
        classNames + "chat__input-area"

        input = textField {
          placeholder = "Type a message..."
          width = 100.percent
          onKeypress { e ->
            e.keyCode.takeIf { it == KeypressEvent.Key.ENTER }?.let {
              sendMessage()
            }
          }

          suffixSlot {
            flexLayout {
              styles["gap"] = "var(--dwc-space-s)"
              styles["padding"] = "0 var(--dwc-space-xs)"

              sendButton = iconButton("send", "tabler") {
                classNames + "chat__send-button"
                onClick { input.sendMessage() }
              }
              stopButton = iconButton(TablerIcon.create("square", TablerIcon.Variate.FILLED)) {
                theme = Theme.DANGER
                isVisible = false
                classNames + "chat__stop-button"
                onClick {
                  stopStreaming()
                }
              }
            }
          }
        }
      }
    }
  }

  private fun TextField.sendMessage() {
    text?.takeIf { it.trim().isNotEmpty() }?.let { message ->
      viewer.content.takeIf { it.isNotEmpty() }?.let {
        viewer.append("\n\n---\n\n")
      }
      viewer.append("<p style=\"text-align:right;color:var(--dwc-color-primary);font-weight:500\">$message</p>")
      text = ""
      messageArea.showThinking()

      delayInterval = Interval(0.6f) {
        it.interval.stop()
        messageArea.hideThinking()
        startStreaming()
      }.apply { start() }
    }
  }

  private fun startStreaming() {
    sendButton.isVisible = false
    stopButton.isVisible = true

    val response = responses.random()
    var index = 0

    streamInterval = Interval(0.04f) {
      val current = index
      if (current < response.length) {
        val end = min(current + 4 + random.nextInt(4), response.length)
        viewer.append(response.substring(current, end))
        index = end
      } else {
        it.interval.stop()
        viewer.whenRenderComplete().thenAccept {
          sendButton.isVisible = true
          stopButton.isVisible = false
          input.focus()
        }
      }
    }.apply { start() }
  }

  private fun stopStreaming() {
    delayInterval?.apply {
      stop()
      delayInterval = null
    }
    streamInterval?.apply {
      stop()
      streamInterval = null
    }
    messageArea.hideThinking()
    viewer.stop()
    sendButton.isVisible = true
    stopButton.isVisible = false
    input.focus()
  }

  private fun FlexLayout.showThinking() {
    thinkingIndicator = div {
      classNames + "chat__thinking"

      tablerIcon("loader-2")
      span("Thinking...")
    }
  }

  private fun FlexLayout.hideThinking() {
    thinkingIndicator?.let {
      messageArea.remove(it)
      thinkingIndicator = null
    }
  }

}
