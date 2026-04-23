package com.webforj.samples.views.terminal

import com.webforj.annotation.InlineStyleSheet
import com.webforj.component.Composite
import com.webforj.component.terminal.Terminal
import com.webforj.component.terminal.event.TerminalDataEvent
import com.webforj.kotlin.extension.percent
import com.webforj.kotlin.extension.set
import com.webforj.kotlin.extension.size
import com.webforj.kotlin.extension.styles
import com.webforj.router.annotation.FrameTitle
import com.webforj.router.annotation.Route
import com.webforj.samples.views.terminal.commands.*

@Route
@FrameTitle("Custom Terminal")
@InlineStyleSheet(
  """
    dwc-window-center {
      background-color: #1e1e1e;
    }

  """
)
class TerminalKotlinVIew: Composite<Terminal>() {
  private val self = boundComponent
  private val commandBuffer = StringBuilder()
  private val commandHistory = arrayListOf<String>()
  private val commands = hashMapOf<String, TerminalCommand>()
  private var historyIndex = -1

  init {
    self.apply {
      isAutoFit = true
      size = 95.percent to 95.percent
      styles["margin"] = "var(--dwc-space-m)"
      writeln("\u001B[1;32mWelcome 👋  to the webforJ terminal!\u001B[0m")
      writeln("Type \u001B[1;33m`help`\u001B[0m to see a list of supported commands.")
      write("$ ")
      addDataListener(::onData)
      focus()
    }
    registerCommands()
  }

  private fun registerCommands() {
    listOf(
      TimeCommand(),
      DateCommand(),
      PromptCommand(),
      MsgCommand(),
      ClearCommand(),
      HelpCommand(commands)
    ).forEach { commands[it.name] = it }
  }

  private fun processCommand() {
    val commandLine = commandBuffer.toString()
    commandBuffer.setLength(0)

    if (commandLine.isBlank()) {
      self.write("$ ")
      return
    }

    val args =
      commandLine.trim { it <= ' ' }.split("\\s+".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
    commands[args[0]]?.execute(self, args) ?: self.writeln("Unknown command: " + args[0])

    self.write("$ ")
  }

  private fun onData(e: TerminalDataEvent) {
    val input = e.value
    val isPrintable = input.chars().allMatch { (it in 0x20..0x7E) || it >= 0xA0 }

    when (input) {
      "\r" -> {
        self.write("\r\n")
        if (commandBuffer.isNotEmpty()) {
          commandHistory.add(commandBuffer.toString())
          historyIndex = commandHistory.size
        }
        processCommand()
      }

      "\u007F", "\b" -> {
        if (commandBuffer.isNotEmpty()) {
          commandBuffer.deleteCharAt(commandBuffer.length - 1)
          self.write("\b \b")
        }
      }

      "\u001b[A" -> {
        if (historyIndex > 0) {
          historyIndex--
          replaceCommandBuffer(commandHistory[historyIndex])
        }
      }

      "\u001b[B" -> {
        if (historyIndex < commandHistory.size - 1) {
          historyIndex++
          replaceCommandBuffer(commandHistory[historyIndex])
        } else if (historyIndex == commandHistory.size - 1) {
          historyIndex++
          replaceCommandBuffer("")
        }
      }

      else -> {
        if (isPrintable) {
          commandBuffer.append(input)
          self.write(input)
        }
      }
    }
  }

  private fun replaceCommandBuffer(newBuffer: String) {
    while (commandBuffer.isNotEmpty()) {
      self.write("\b \b")
      commandBuffer.setLength(commandBuffer.length - 1)
    }
    commandBuffer.append(newBuffer)
    self.write(newBuffer)
  }

}
