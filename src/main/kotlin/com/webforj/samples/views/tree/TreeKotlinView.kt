package com.webforj.samples.views.tree

import com.webforj.component.Composite
import com.webforj.component.layout.flexlayout.FlexDirection
import com.webforj.component.layout.flexlayout.FlexLayout
import com.webforj.kotlin.dsl.component.tree.tree
import com.webforj.kotlin.dsl.component.tree.treeNode
import com.webforj.kotlin.extension.set
import com.webforj.kotlin.extension.size
import com.webforj.kotlin.extension.styles
import com.webforj.kotlin.extension.vh
import com.webforj.router.annotation.FrameTitle
import com.webforj.router.annotation.Route

@Route
@FrameTitle("Tree View")
class TreeKotlinView: Composite<FlexLayout>() {
  private val self = boundComponent

  init {
    self.apply {
      direction = FlexDirection.COLUMN
      size = "min-content" to 100.vh
      styles["overflow"] = "auto"
      tree {
        styles["margin"] = "var(--dwc-space-l)"
        treeNode("Documents") {
          tooltipText = "Work and personal documents"
          treeNode("Reports") {
            tooltipText = "Monthly and annual reports"
            treeNode("2023") {
              treeNode("January.pdf") {
                tooltipText = "January report"
              }
              treeNode("February.pdf") {
                tooltipText = "February report"
              }
            }
            treeNode("2022") {
              treeNode("Q4.pdf") {
                tooltipText = "Quarter 4 report"
              }
              treeNode("Q3.pdf") {
                tooltipText = "Quarter 3 report"
              }
            }
          }
          treeNode("Invoices") {
            tooltipText = "Invoices and billing"
            treeNode("ClientA.pdf") {
              tooltipText = "Invoice for Client A"
            }
            treeNode("ClientB.pdf") {
              tooltipText = "Invoice for Client B"
            }
          }
        }
        treeNode("Pictures") {
          tooltipText = "Photos and images"
          treeNode("Vacations") {
            treeNode("Beach.png") {
              tooltipText = "Beach photo"
            }
            treeNode("Mountains.png") {
              tooltipText = "Mountain photo"
            }
          }
          treeNode("Events") {
            treeNode("Birthday.jpg") {
              tooltipText = "Birthday party"
            }
          }
        }
        expand("Documents")
        expand("Pictures")
        expand("Vacations")
        selectKey("Mountains.png")
      }
    }
  }
}
