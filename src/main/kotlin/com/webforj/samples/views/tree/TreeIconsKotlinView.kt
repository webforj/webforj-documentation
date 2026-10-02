package com.webforj.samples.views.tree

import com.webforj.component.Composite
import com.webforj.component.icons.FeatherIcon
import com.webforj.component.layout.flexlayout.FlexDirection
import com.webforj.component.layout.flexlayout.FlexLayout
import com.webforj.kotlin.dsl.component.tree.tree
import com.webforj.kotlin.dsl.component.tree.treeNode
import com.webforj.kotlin.extension.set
import com.webforj.kotlin.extension.styles
import com.webforj.kotlin.extension.toQualifiedName
import com.webforj.kotlin.extension.vh
import com.webforj.router.annotation.FrameTitle
import com.webforj.router.annotation.Route

@Route
@FrameTitle("Tree Icons View")
class TreeIconsKotlinView: Composite<FlexLayout>() {
  private val self = boundComponent

  init {
    self.apply {
      direction = FlexDirection.COLUMN
      height = 100.vh
      styles["overflow"] = "auto"
      tree {
        styles["margin"] = "var(--dwc-space-l)"
        collapsedIcon = FeatherIcon.CHEVRON_RIGHT.create().toQualifiedName()
        expandedIcon = FeatherIcon.CHEVRON_DOWN.create().toQualifiedName()
        treeNode("Documents") {
          tooltipText = "Work and personal documents"
          treeNode("Reports") {
            tooltipText = "Monthly and annual reports"
            treeNode("2023") {
              treeNode("January.pdf") {
                tooltipText = "January report"
                icon = FeatherIcon.FILE.create().toQualifiedName()
                selectedIcon = FeatherIcon.FILE_TEXT.create().toQualifiedName()
              }
              treeNode("February.pdf") {
                tooltipText = "February report"
                icon = FeatherIcon.FILE.create().toQualifiedName()
                selectedIcon = FeatherIcon.FILE_TEXT.create().toQualifiedName()
              }
            }
            treeNode("2022") {
              treeNode("Q4.pdf") {
                tooltipText = "Quarter 4 report"
                icon = FeatherIcon.FILE.create().toQualifiedName()
                selectedIcon = FeatherIcon.FILE_TEXT.create().toQualifiedName()
              }
              treeNode("Q3.pdf") {
                tooltipText = "Quarter 3 report"
                icon = FeatherIcon.FILE.create().toQualifiedName()
                selectedIcon = FeatherIcon.FILE_TEXT.create().toQualifiedName()
              }
            }
          }
          treeNode("Invoices") {
            tooltipText = "Invoices and billing"
            treeNode("ClientA.pdf") {
              tooltipText = "Invoice for Client A"
              icon = FeatherIcon.FILE.create().toQualifiedName()
              selectedIcon = FeatherIcon.FILE_TEXT.create().toQualifiedName()
            }
            treeNode("ClientB.pdf") {
              tooltipText = "Invoice for Client B"
              icon = FeatherIcon.FILE.create().toQualifiedName()
              selectedIcon = FeatherIcon.FILE_TEXT.create().toQualifiedName()
            }
          }
        }
        treeNode("Pictures") {
          tooltipText = "Photos and images"
          treeNode("Vacations") {
            treeNode("Beach.png") {
              tooltipText = "Beach photo"
              icon = FeatherIcon.IMAGE.create().toQualifiedName()
              selectedIcon = FeatherIcon.IMAGE.create().toQualifiedName()
            }
            treeNode("Mountains.png") {
              tooltipText = "Mountain photo"
              icon = FeatherIcon.IMAGE.create().toQualifiedName()
              selectedIcon = FeatherIcon.IMAGE.create().toQualifiedName()
            }
          }
          treeNode("Events") {
            treeNode("Birthday.jpg") {
              tooltipText = "Birthday party"
              icon = FeatherIcon.IMAGE.create().toQualifiedName()
              selectedIcon = FeatherIcon.IMAGE.create().toQualifiedName()
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
