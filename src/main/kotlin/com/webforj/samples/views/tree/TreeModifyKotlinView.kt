package com.webforj.samples.views.tree

import com.webforj.annotation.InlineStyleSheet
import com.webforj.component.Composite
import com.webforj.component.button.ButtonTheme
import com.webforj.component.layout.flexlayout.FlexDirection
import com.webforj.component.layout.flexlayout.FlexLayout
import com.webforj.component.optiondialog.InputDialog
import com.webforj.kotlin.dsl.component.tree.tree
import com.webforj.kotlin.dsl.component.tree.treeNode
import com.webforj.kotlin.extension.set
import com.webforj.kotlin.extension.styles
import com.webforj.kotlin.extension.vh
import com.webforj.router.annotation.FrameTitle
import com.webforj.router.annotation.Route

@Route
@FrameTitle("Tree Modify View")
@InlineStyleSheet(
  """
    :root {
      --dwc-tree-icon-fill: var(--dwc-color-primary);
    }

  """
)
class TreeModifyKotlinView : Composite<FlexLayout>() {
  private val self = boundComponent

  init {
    self.apply {
      direction = FlexDirection.COLUMN
      height = 100.vh
      styles["overflow"] = "auto"
      tree {
        styles["margin"] = "var(--dwc-space-l)"
        treeNode("Projects") {
          treeNode("Alpha") {
            treeNode("Planning")
            treeNode("Execution")
            treeNode("Review")
          }
          treeNode("Beta") {
            treeNode("Design")
            treeNode("Development")
            treeNode("Testing")
          }
        }
        treeNode("Departments") {
          treeNode("Engineering") {
            treeNode("Software")
            treeNode("Hardware")
          }
          treeNode("Marketing")
          treeNode("Human Resources")
        }
        expand("Departments")
        onDoubleClick { event ->
          event.node?.let { node ->
            val result = InputDialog(
              "Enter a new name for the node: ${node.text}",
              "Modify Node"
            ).apply {
              defaultValue = node.text
              firstButtonText = "Modify"
              secondButtonText = "Cancel"
              setFirstButtonTheme(ButtonTheme.PRIMARY)
            }.run { show() }
            result?.takeIf { it.isNotEmpty() }?.let {
              node.text = it
              node.tooltipText = "Modified: $it"
            }
          }
        }
      }
    }
  }
}
