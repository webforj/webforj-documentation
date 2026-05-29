package com.webforj.samples.views.drawer

import com.webforj.component.Composite
import com.webforj.component.button.ButtonTheme
import com.webforj.component.layout.flexlayout.FlexDirection
import com.webforj.component.layout.flexlayout.FlexLayout
import com.webforj.component.optioninput.CheckBox
import com.webforj.component.toast.Toast
import com.webforj.kotlin.dsl.component.button.button
import com.webforj.kotlin.dsl.component.drawer.drawer
import com.webforj.kotlin.dsl.component.drawer.footerSlot
import com.webforj.kotlin.dsl.component.field.textField
import com.webforj.kotlin.dsl.component.layout.flexlayout.flexLayout
import com.webforj.kotlin.dsl.component.optioninput.checkBox
import com.webforj.kotlin.extension.set
import com.webforj.kotlin.extension.styles
import com.webforj.kotlin.extension.vh
import com.webforj.router.annotation.FrameTitle
import com.webforj.router.annotation.Route

@Route
@FrameTitle("Task Manager Drawer")
class DrawerEventKotlinView: Composite<FlexLayout>() {
  private val self = boundComponent

  init {
    self.apply {
      margin = "var(--dwc-space-l)"
      val openDrawerButton = button("Open Task Manager")
      val drawer = drawer("Task Manager") {
        open()
        onOpen { Toast.show("Drawer Opened", 3000) }
        onClose { Toast.show("Drawer Closed", 3000) }
        val tasks = flexLayout(FlexDirection.COLUMN) {
          spacing = "var(--dwc-space-s)"
          styles["overflow-y"] = "auto"
          maxHeight = 60.vh
          checkBox("Finish project documentation")
          checkBox("Call John about the meeting")
          checkBox("Prepare slides for tomorrow")
        }
        footerSlot {
          flexLayout(FlexDirection.COLUMN) {
            spacing = "var(--dwc-space-s)"
            val newTaskField = textField("New Task") {
              maxLength = 50
            }
            button("Add Task", ButtonTheme.PRIMARY) {
              onClick {
                newTaskField.value.takeIf {
                  it.isNotBlank() && !newTaskField.isInvalid
                }?.let {
                  tasks.checkBox(it)
                  newTaskField.value = ""
                  isEnabled = tasks.components.size < 50
                }
              }
            }
            button("Clear Completed", ButtonTheme.DANGER) {
              onClick {
                tasks.components.map {
                  it as CheckBox
                }.filter {
                  it.isChecked
                }.forEach { tasks.remove(it) }
              }
            }
          }
        }
      }
      openDrawerButton.onClick { drawer.open() }
    }
  }

}
