package com.webforj.samples.views.tree;

import static com.webforj.component.tree.Tree.node;

import com.webforj.Interval;
import com.webforj.component.Composite;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.tree.Tree;
import com.webforj.component.tree.TreeNode;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;
import com.webforj.samples.config.RouteConfig;

@Route(RouteConfig.TREE_LAZY_LOAD)
@FrameTitle("Lazy Load Tree View")
public class TreeLazyLoadView extends Composite<FlexLayout> {
  private static final String SPINNER_FLAG = "spinner";
  private final FlexLayout self = getBoundComponent();
  private final Tree tree = new Tree();

  public TreeLazyLoadView() {
    self.setDirection(FlexDirection.COLUMN)
        .setHeight("100vh")
        .setStyle("overflow", "auto");

    tree.add(
        createNodeWithSpinner("Node 1"),
        createNodeWithSpinner("Node 2"),
        createNodeWithSpinner("Node 3"),
        createNodeWithSpinner("Node 4"));

    tree.setStyle("margin", "var(--dwc-space-l)")
        .onExpand(event -> {
          TreeNode node = event.getNode();
          if (node.getChildren().size() == 1) {
            TreeNode child = node.getChildren().get(0);
            if (Boolean.TRUE.equals(child.getUserData(SPINNER_FLAG))) {
              new Interval(1f, e -> {
                e.getInterval().stop();
                node.remove(child);
                node.add(
                    node(node.getText() + " - Child 1"),
                    node(node.getText() + " - Child 2"),
                    node(node.getText() + " - Child 3"));
              }).start();
            }
          }
        });

    self.add(tree);
  }

  private TreeNode createNodeWithSpinner(String name) {
    TreeNode spinner = node("<dwc-spinner></dwc-spinner>");
    spinner.setUserData(SPINNER_FLAG, true);
    TreeNode parent = node(name);
    parent.add(spinner);

    return parent;
  }
}
