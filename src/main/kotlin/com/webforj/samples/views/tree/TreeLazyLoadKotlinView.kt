package com.webforj.samples.views.tree

import com.webforj.Interval
import com.webforj.component.Composite
import com.webforj.component.layout.flexlayout.FlexDirection
import com.webforj.component.layout.flexlayout.FlexLayout
import com.webforj.component.tree.Tree
import com.webforj.kotlin.dsl.component.tree.tree
import com.webforj.kotlin.dsl.component.tree.treeNode
import com.webforj.kotlin.extension.set
import com.webforj.kotlin.extension.styles
import com.webforj.kotlin.extension.vh
import com.webforj.router.annotation.FrameTitle
import com.webforj.router.annotation.Route

@Route
@FrameTitle("Lazy Load Tree View")
class TreeLazyLoadKotlinView: Composite<FlexLayout>() {
  private val self = boundComponent

  init {
    self.apply {
      direction = FlexDirection.COLUMN
      height = 100.vh
      styles["overflow"] = "auto"
      tree {
        styles["margin"] = "var(--dwc-space-l)"
        for (i in 1..4) {
          treeNode("Node $i") {
            treeNode("<dwc-spinner></dwc-spinner") {
              setUserData("spinner", true)
            }
          }
        }
        onExpand { event ->
          val node = event.node
          if (node.children.size == 1) {
            val child = node.children.first()
            if (child.getUserData("spinner") == true) {
              Interval(1f) {
                it.interval.stop()
                node.remove(child)
                for (i in 1..3) {
                  node.treeNode("${node.text} - Child $i")
                }
              }.start()
            }
          }
        }
      }
    }
  }
}
