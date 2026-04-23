package com.webforj.samples.views.terminal

import com.webforj.Interval
import com.webforj.component.Composite
import com.webforj.component.button.Button
import com.webforj.component.button.ButtonTheme
import com.webforj.component.layout.flexlayout.FlexAlignment
import com.webforj.component.layout.flexlayout.FlexDirection
import com.webforj.component.layout.flexlayout.FlexLayout
import com.webforj.component.terminal.Terminal
import com.webforj.kotlin.dsl.component.button.button
import com.webforj.kotlin.dsl.component.icons.tablerIcon
import com.webforj.kotlin.extension.*
import com.webforj.router.annotation.FrameTitle
import com.webforj.router.annotation.Route
import java.time.LocalTime
import java.util.*

@Route
@FrameTitle("Server Logs Stream")
class ServerLogsKotlinView : Composite<FlexLayout>() {
  private val self = boundComponent
  private val terminal: Terminal
  private val startButton: Button
  private lateinit var interval: Interval
  private var isStreaming = false
  private var bufferedLines = 0
  private val HIGH_WATERMARK = 500
  private val LOW_WATERMARK = 200
  private val random = Random()
  private val logLevels = arrayOf("INFO", "WARN", "DEBUG", "ERROR")

  init {
    self.apply {
      direction = FlexDirection.COLUMN
      alignment = FlexAlignment.END
      styles["background-color"] = "#1e1e1e"
      height = 100.vh
      spacing = 0.px
      startButton = button("Start Log Stream", ButtonTheme.PRIMARY) {
        maxWidth = 180.px
        styles["margin"] = 1.rem
        prefixSlot { tablerIcon("player-play") }
        onClick {
          if (isStreaming) {
            startStreaming()
          } else {
            stopStreaming()
          }
        }
      }
      terminal = terminal {
        isAutoFit = true
        styles["margin"] = "0px var(--dwc-space-m)"
        size = 95.percent to 85.vh
        setupInterval()
        printHelp()
      }
    }
  }

  override fun onDestroy() {
    interval.stop()
  }

  private fun startStreaming() {
    isStreaming = true
    startButton.apply {
      text = "Stop Log Stream"
      prefixSlot { tablerIcon("placer-pause") }
    }
    terminal.writeln("\u001B[42;30m  Log streaming started...  \u001B[0m")
    interval.start()
  }

  private fun stopStreaming() {
    isStreaming = false
    startButton.apply {
      text = "Start Log Stream"
      prefixSlot { tablerIcon("placer-play") }
      interval.stop()
      terminal.writeln("\u001B[41;37m  Log streaming stopped.  \u001B[0m")
    }

  }

  private fun setupInterval() {
    interval = Interval(0.01f) {
      if (!isStreaming) {
        return@Interval
      }
      bufferedLines++
      if (bufferedLines > HIGH_WATERMARK) {
        interval.stop()
      }
      terminal.generateLog()
    }
  }

  private fun Terminal.generateLog() {
    val level = logLevels[random.nextInt(logLevels.size)].let {
      when (it) {
        "INFO" -> "\u001B[32mINFO\u001B[0m"
        "WARN" -> "\u001B[33mWARN\u001B[0m"
        "DEBUG" -> "\u001B[34mDEBUG\u001B[0m"
        "ERROR" -> "\u001B[31mERROR\u001B[0m"
        else -> it;
      }
    }
    val message = "[${LocalTime.now()}][$level] Simulated log message..."
    writeln(message) {
      bufferedLines--
      if (bufferedLines < LOW_WATERMARK && isStreaming && !interval.isRunning) {
        interval.start()
      }
    }
  }

  private fun Terminal.printHelp() {
    writeln("""
        \u001B[1;36mServer Log Stream Demo\u001B[0m

        \u001B[1;37mHow to use:\u001B[0m
        - Click the \u001B[32mStart\u001B[0m button to begin streaming logs.
        - Log levels are color-coded: \u001B[32mINFO\u001B[0m, \u001B[33mWARN\u001B[0m, \u001B[34mDEBUG\u001B[0m, \u001B[31mERROR\u001B[0m.
        - The terminal automatically slows down if needed.

        \u001B[1;33mTip:\u001B[0m Watch how buffering is managed with high-speed logs.
        """.trimIndent())
  }
}
