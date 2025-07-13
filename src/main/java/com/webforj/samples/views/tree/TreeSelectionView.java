package com.webforj.samples.views.tree;

import static com.webforj.component.optiondialog.OptionDialog.showMessageDialog;
import static com.webforj.component.tree.Tree.node;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.webforj.component.Composite;
import com.webforj.component.button.Button;
import com.webforj.component.optioninput.RadioButton;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.tree.Tree;
import com.webforj.component.tree.TreeNode;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;
import com.webforj.samples.config.RouteConfig;

@Route(RouteConfig.TREE_SELECTION)
@FrameTitle("Tree Selection Example")
public class TreeSelectionView extends Composite<FlexLayout> {

  private final FlexLayout self = getBoundComponent();
  private final Tree tree = new Tree();
  private final Button showSelected = new Button("Show Selected Nodes");
  private final RadioButton multiSelectToggle = RadioButton.Switch("Enable Multi-selection", true);

  public TreeSelectionView() {
    self.setDirection(FlexDirection.COLUMN)
        .setMaxWidth(400)
        .setStyle("margin", "0 auto")
        .setStyle("padding", "var(--dwc-space-l)")
        .setStyle("overflow", "auto")
        .setStyle("height", "calc(100vh - 2 * var(--dwc-space-l))");

    buildTree();
    TreeNode colors = tree.getChildren().get(0);
    tree.setSelectionMode(Tree.SelectionMode.MULTIPLE)
        .expand(colors)
        .selectChildren(colors);

    multiSelectToggle.onToggle(e -> {
      if (e.isToggled()) {
        tree.setSelectionMode(Tree.SelectionMode.MULTIPLE);
      } else {
        tree.setSelectionMode(Tree.SelectionMode.SINGLE);
      }
    });

    showSelected.onClick(e -> {
      List<TreeNode> selectedNodes = tree.getSelectedItems();
      String msg = "There are no node selected";

      if (!selectedNodes.isEmpty()) {
        msg = "<html> You have selected the following nodes"
            + selectedNodes.stream().map(TreeNode::getText).map(title -> "<li>" + title + "</li>")
                .collect(Collectors.joining("", "<ul>", "</ul>"))
            + "</html>";
      }

      showMessageDialog(msg, "Node Selection");
    });

    self.add(multiSelectToggle, tree, showSelected);
  }

  private void buildTree() {
    List<TreeNode> nodes = Arrays.asList(
        node("Colors").add(
            node("Red"),
            node("Green"),
            node("Blue")),
        node("Shapes").add(
            node("Circle"),
            node("Square"),
            node("Triangle")),
        node("Animals").add(
            node("Dog"),
            node("Cat"),
            node("Bird")));

    nodes.forEach(tree::add);
  }
}
