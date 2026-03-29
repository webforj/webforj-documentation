package com.webforj.samples.views.tree

import com.webforj.component.Composite
import com.webforj.component.icons.FeatherIcon
import com.webforj.component.layout.flexlayout.FlexDirection
import com.webforj.component.layout.flexlayout.FlexLayout
import com.webforj.kotlin.dsl.component.tree.tree
import com.webforj.kotlin.dsl.component.tree.treeNode
import com.webforj.kotlin.extension.set
import com.webforj.kotlin.extension.styles
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
        setCollapsedIcon(FeatherIcon.CHEVRON_RIGHT.create())
        setExpandedIcon(FeatherIcon.CHEVRON_DOWN.create())
        treeNode("Documents") {
          tooltipText = "Work and personal documents"
          treeNode("Reports") {
            tooltipText = "Monthly and annual reports"
            treeNode("2023") {
              treeNode("January.pdf") {
                tooltipText = "January report"
                setIcon(FeatherIcon.FILE.create())
                setSelectedIcon(FeatherIcon.FILE_TEXT.create())
              }
              treeNode("February.pdf") {
                tooltipText = "February report"
                setIcon(FeatherIcon.FILE.create())
                setSelectedIcon(FeatherIcon.FILE_TEXT.create())
              }
            }
            treeNode("2022") {
              treeNode("Q4.pdf") {
                tooltipText = "Quarter 4 report"
                setIcon(FeatherIcon.FILE.create())
                setSelectedIcon(FeatherIcon.FILE_TEXT.create())
              }
              treeNode("Q3.pdf") {
                tooltipText = "Quarter 3 report"
                setIcon(FeatherIcon.FILE.create())
                setSelectedIcon(FeatherIcon.FILE_TEXT.create())
              }
            }
          }
          treeNode("Invoices") {
            tooltipText = "Invoices and billing"
            treeNode("ClientA.pdf") {
              tooltipText = "Invoice for Client A"
              setIcon(FeatherIcon.FILE.create())
              setSelectedIcon(FeatherIcon.FILE_TEXT.create())
            }
            treeNode("ClientB.pdf") {
              tooltipText = "Invoice for Client B"
              setIcon(FeatherIcon.FILE.create())
              setSelectedIcon(FeatherIcon.FILE_TEXT.create())
            }
          }
        }
        treeNode("Pictures") {
          tooltipText = "Photos and images"
          treeNode("Vacations") {
            treeNode("Beach.png") {
              tooltipText = "Beach photo"
              setIcon(FeatherIcon.IMAGE.create())
              setSelectedIcon(FeatherIcon.IMAGE.create())
            }
            treeNode("Mountains") {
              treeNode("Mountains.png") {
                tooltipText = "Mountain photo"
                setIcon(FeatherIcon.IMAGE.create())
                setSelectedIcon(FeatherIcon.IMAGE.create())
              }
            }
          }
          treeNode("Events") {
            treeNode("Birthday.jpg") {
              tooltipText = "Birthday party"
              setIcon(FeatherIcon.IMAGE.create())
              setSelectedIcon(FeatherIcon.IMAGE.create())
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
