package com.webforj.samples.views.drawer

import com.webforj.component.Composite
import com.webforj.component.button.Button
import com.webforj.component.button.ButtonTheme
import com.webforj.component.drawer.Drawer
import com.webforj.component.layout.flexlayout.FlexDirection
import com.webforj.component.layout.flexlayout.FlexLayout
import com.webforj.component.optioninput.CheckBox
import com.webforj.component.toast.Toast
import com.webforj.kotlin.dsl.component.button.button
import com.webforj.kotlin.dsl.component.drawer.addToFooter
import com.webforj.kotlin.dsl.component.drawer.drawer
import com.webforj.kotlin.dsl.component.field.textField
import com.webforj.kotlin.dsl.component.layout.flexlayout.flexLayout
import com.webforj.kotlin.dsl.component.optioninput.checkBox
import com.webforj.kotlin.extension.set
import com.webforj.kotlin.extension.styles
import com.webforj.router.annotation.FrameTitle
import com.webforj.router.annotation.Route

@Route
@FrameTitle("Task Manager Drawer")
class DrawerEventKotlinView : Composite<FlexLayout>() {
  private val drawer: Drawer
  private lateinit var addTaskButton: Button
  private lateinit var tasks: FlexLayout
  private val taskList = ArrayList<CheckBox>()

  init {
    boundComponent.apply {
      margin = "var(--dwc-space-l)"
      button("Open Task Manager").onClick { drawer.open() }
      drawer = drawer("Task Manager") {
        onOpen { Toast.show("Drawer Opened", 3000) }
        onClose { Toast.show("Drawer Closed", 3000) }
        open()
        tasks = flexLayout(FlexDirection.COLUMN) {
          spacing = "var(--dwc-space-s)"
          styles["overflow-y"] = "auto"
          maxHeight = "60vh"
        }
        addToFooter {
          flexLayout(FlexDirection.COLUMN) {
            spacing = "var(--dwc-space-s)"
            val newTaskField = textField("New Task") {
              maxLength = 50
            }
            addTaskButton = button("Add Task", ButtonTheme.PRIMARY) {
              onClick {
                val taskText = newTaskField.value
                if (taskText.isNotBlank() && !newTaskField.isInvalid) {
                  tasks.addTask(taskText)
                  newTaskField.value = ""
                }
              }
            }
            button("Clear Completed", ButtonTheme.DANGER).onClick {
              clearCompletedTasks()
            }
          }
        }
        tasks.apply {
          addTask("Finish project documentation")
          addTask("Call John about the meeting")
          addTask("Prepare slides for tomorrow")
        }
      }
    }
  }

  private fun FlexLayout.addTask(taskText: String) {
    val task = checkBox(taskText)
    taskList.add(task)
    checkTaskLimit()
  }

  private fun clearCompletedTasks() {
    val completedTasks = taskList.filter { it.isChecked }
    taskList.removeAll(completedTasks)
    tasks.remove(*completedTasks.toTypedArray())
  }

  private fun checkTaskLimit() {
    addTaskButton.isEnabled = taskList.size < 50
  }
}
