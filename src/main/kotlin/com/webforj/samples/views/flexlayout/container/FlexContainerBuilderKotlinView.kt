package com.webforj.samples.views.flexlayout.container

import com.webforj.annotation.StyleSheet
import com.webforj.component.Composite
import com.webforj.component.html.elements.Div
import com.webforj.component.layout.flexlayout.FlexAlignment
import com.webforj.component.layout.flexlayout.FlexContentAlignment
import com.webforj.component.layout.flexlayout.FlexDirection
import com.webforj.component.layout.flexlayout.FlexJustifyContent
import com.webforj.component.layout.flexlayout.FlexLayout
import com.webforj.component.layout.flexlayout.FlexWrap
import com.webforj.kotlin.dsl.component.field.maskedNumberFieldSpinner
import com.webforj.kotlin.dsl.component.layout.flexlayout.flexLayout
import com.webforj.kotlin.dsl.component.layout.flexlayout.horizontal
import com.webforj.kotlin.dsl.component.layout.flexlayout.vertical
import com.webforj.kotlin.dsl.component.list.choiceBox
import com.webforj.kotlin.extension.classNames
import com.webforj.kotlin.extension.plus
import com.webforj.kotlin.extension.set
import com.webforj.kotlin.extension.styles
import com.webforj.router.annotation.FrameTitle
import com.webforj.router.annotation.Route
import com.webforj.samples.components.Box
import com.webforj.samples.components.CodeDisplay
import com.webforj.samples.views.flexlayout.box
import com.webforj.samples.views.flexlayout.codeDisplay

@Route
@StyleSheet("ws://css/flexlayout/container/flexContainerBuilder.css")
@FrameTitle("Container Builder")
class FlexContainerBuilderKotlinView: Composite<Div>() {
  private val self = boundComponent
  private lateinit var boxLayout: FlexLayout
  private val codeDisplay: CodeDisplay
  private val boxes = arrayListOf<Box>()
  private val HUE = 36
  private val REGEX = Regex.fromLiteral("^(.+?)-")
  private var codeSnippetBuilder = hashMapOf<String?, String>()

  init {
    self.apply {
      classNames + "app__frame"
      flexLayout {
        horizontal()
        flexLayout {
          vertical()
          classNames + "flex__options"
          maskedNumberFieldSpinner("Number of Boxes") {
            min = 1.0
            text = "1"
            onModify { ev ->
              val value = ev.text.toInt()
              takeIf { !isInvalid && value > 0 }?.let {
                if (value > boxes.size) {
                  boxLayout.addBox(value)
                } else if (value < boxes.size) {
                  removeBox(value)
                }
              }
            }
          }
          choiceBox("Direction Options") {
            FlexDirection.entries.forEach {
              val label = it.value
              val text = it.name[0] + label.substring(1)
              add(it, text)
            }
            selectIndex(0)
            onSelect {
              val direction = it.selectedItem.key as FlexDirection
              boxLayout.direction = direction
              codeSnippetBuilder[FlexDirection::class.simpleName] = "  direction = FlexDirection.${direction.name}"
              codeDisplay.updateCode()
            }
          }
          choiceBox("Justification Options") {
            FlexJustifyContent.entries.forEach {
              val label = it.value.replace(REGEX, "")
              val text = label.uppercase()[0] + label.substring(1)
              add(it, text)
            }
            selectIndex(0)
            onSelect {
              val justifyContent = it.selectedItem.key as FlexJustifyContent
              boxLayout.justifyContent = justifyContent
              codeSnippetBuilder[FlexJustifyContent::class.simpleName] = "  justifyContent = FlexJustifyContent.${justifyContent.name}"
              codeDisplay.updateCode()
            }
          }
          choiceBox("Alignment Options") {
            FlexAlignment.entries.forEach {
              val label = it.value.replace(REGEX, "")
              val text = label.uppercase()[0] + label.substring(1)
              add(it, text)
            }
            selectIndex(0)
            onSelect {
              val alignment = it.selectedItem.key as FlexAlignment
              boxLayout.alignment = alignment
              codeSnippetBuilder[FlexAlignment::class.simpleName] = "  alignment = FlexAlignment.${alignment.name}"
              codeDisplay.updateCode()
            }
          }
          choiceBox("Content-Alignment Options") {
            FlexContentAlignment.entries.forEach {
              val label = it.value.replace(REGEX, "")
              val text = label.uppercase()[0] + label.substring(1)
              add(it, text)
            }
            selectIndex(0)
            onSelect {
              val alignment = it.selectedItem.key as FlexContentAlignment
              boxLayout.alignContent = alignment
              codeSnippetBuilder[FlexContentAlignment::class.simpleName] = "  alignContent = FlexContentAlignment.${alignment.name}"
              codeDisplay.updateCode()
            }
          }
          choiceBox("Wrap Options") {
            FlexWrap.entries.forEach {
              val label = it.value.replace(REGEX, "")
              val text = label.uppercase()[0] + label.substring(1)
              add(it, text)
            }
            selectIndex(0)
            onSelect {
              val wrap = it.selectedItem.key as FlexWrap
              boxLayout.wrap = wrap
              codeSnippetBuilder[FlexWrap::class.simpleName] = "  wrap = FlexWrap.${wrap.name}"
              codeDisplay.updateCode()
            }
          }
        }
        boxLayout = flexLayout {
          horizontal()
          classNames + "button__container"
          addBox(1)
        }
      }
      codeDisplay = codeDisplay {
        setLanguage("java")
        classNames + "code__block"
        updateCode()
      }
    }
  }

  private fun CodeDisplay.updateCode() {
    val builder = StringBuilder()
    builder.appendLine("flexLayout {")
    codeSnippetBuilder[FlexDirection::class.simpleName]?.let(builder::appendLine)
      ?: builder.appendLine("  direction = FlexDirection.${FlexDirection.ROW.name}")
    codeSnippetBuilder[FlexJustifyContent::class.simpleName]?.let(builder::appendLine)
    codeSnippetBuilder[FlexAlignment::class.simpleName]?.let(builder::appendLine)
    codeSnippetBuilder[FlexContentAlignment::class.simpleName]?.let(builder::appendLine)
    codeSnippetBuilder[FlexWrap::class.simpleName]?.let(builder::appendLine)
    text = builder.append("}").toString()
  }

  private fun FlexLayout.addBox(number: Int) {
    while (number > boxes.size) {
      val hue = (HUE * boxes.size).toString()
      boxes.add(box(boxes.size + 1) {
        styles["background"] = "hsla($hue, 50%, 75%, 0.25)"
        styles["border"] = "2px solid hsl($hue, 50%, 35%)"
        styles["color"] = "hsla($hue, 50%, 25%)"
      })
    }
  }

  private fun removeBox(number: Int) {
    while (number < boxes.size) {
      boxes.last().destroyBox()
      boxes.removeAt(boxes.lastIndex)
    }
  }
}
