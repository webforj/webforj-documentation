package com.webforj.samples.views.tree;

import static com.webforj.component.tree.Tree.node;

import com.webforj.annotation.InlineStyleSheet;
import com.webforj.component.Composite;
import com.webforj.component.button.ButtonTheme;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.optiondialog.InputDialog;
import com.webforj.component.tree.Tree;
import com.webforj.component.tree.event.TreeDoubleClickEvent;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@FrameTitle("Tree Modify View")
@InlineStyleSheet( /* css */"""
    :root {
      --dwc-tree-icon-fill: var(--dwc-color-primary);
    }
  """)
public class TreeModifyView extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();
  private final Tree tree = new Tree();

  public TreeModifyView() {
    self.setDirection(FlexDirection.COLUMN)
      .setHeight("100vh")
      .setStyle("overflow", "auto");

    tree.add(
      node("Projects")
        .add(
          node("Alpha")
            .add(
              node("Planning"),
              node("Execution"),
              node("Review")),
          node("Beta")
            .add(
              node("Design"),
              node("Development"),
              node("Testing"))),
      node("Departments")
        .add(
          node("Engineering")
            .add(
              node("Software"),
              node("Hardware")),
          node("Marketing"),
          node("Human Resources")));

    tree.setStyle("margin", "var(--dwc-space-l)")
      .expand("Departments")
      .onDoubleClick(this::handleNodeDoubleClick);

    self.add(tree);
  }

  private void handleNodeDoubleClick(TreeDoubleClickEvent event) {
    var node = event.getNode();
    if (node != null) {
      String nodeText = node.getText();
      var dialog = new InputDialog(
        "Enter a new name for the node: " + nodeText,
        "Modify Node")
        .setDefaultValue(nodeText)
        .setFirstButtonText("Modify")
        .setSecondButtonText("Cancel")
        .setFirstButtonTheme(ButtonTheme.PRIMARY);

      String result = dialog.show();
      if (result != null && !result.isEmpty()) {
        node.setText(result);
        node.setTooltipText("Modified: " + result);
      }
    }
  }
}
