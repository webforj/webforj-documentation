package com.webforj.samples.views.tree

import com.webforj.component.Composite
import com.webforj.component.layout.flexlayout.FlexDirection
import com.webforj.component.layout.flexlayout.FlexLayout
import com.webforj.component.optiondialog.OptionDialog.showMessageDialog
import com.webforj.component.tree.Tree
import com.webforj.component.tree.TreeNode
import com.webforj.kotlin.dsl.component.button.button
import com.webforj.kotlin.dsl.component.optioninput.switch
import com.webforj.kotlin.dsl.component.tree.tree
import com.webforj.kotlin.dsl.component.tree.treeNode
import com.webforj.kotlin.extension.px
import com.webforj.kotlin.extension.set
import com.webforj.kotlin.extension.styles
import com.webforj.router.annotation.FrameTitle
import com.webforj.router.annotation.Route

@Route
@FrameTitle("Tree Selection Example")
class TreeSelectionKotlinView: Composite<FlexLayout>() {
  private val self = boundComponent

  init {
    self.apply {
      direction = FlexDirection.COLUMN
      maxWidth = 400.px
      styles["margin"] = "0 auto"
      styles["padding"] = "var(--dwc-space-l)"
      styles["overflow"] = "auto"
      styles["height"] = "calc(100vh - 2 * var(--dwc-space-l)"
      val multiSelectToggle = switch("Enable Multi-selection", true)
      val tree = tree {
        selectionMode = Tree.SelectionMode.MULTIPLE
        treeNode("Colors") {
          treeNode("Red")
          treeNode("Green")
          treeNode("Blue")
        }.also {
          expand(it)
          selectChildren(it)
        }
        treeNode("Shapes") {
          treeNode("Circle")
          treeNode("Square")
          treeNode("Triangle")
        }
        treeNode("Animals") {
          treeNode("Dog")
          treeNode("Cat")
          treeNode("Bird")
        }
        multiSelectToggle.onToggle {
          if (it.isToggled) {
            selectionMode = Tree.SelectionMode.MULTIPLE
          } else {
            selectionMode = Tree.SelectionMode.SINGLE
          }
        }
      }
      button("Show Selected Nodes") {
        onClick {
          val msg = tree.selectedItems.takeIf { it.isNotEmpty() }?.let { selectedNodes ->
            val nodes = selectedNodes.map(TreeNode::getText)
              .joinToString("", "<ul>", "</ul>") { "<li>$it</li>" }
            """
              <html> You have selected the following nodes
              $nodes
              </html>
            """.trimIndent()
          } ?: "There are no node selected"
          showMessageDialog(msg, "Node Selection")
        }
      }
    }
  }
}
