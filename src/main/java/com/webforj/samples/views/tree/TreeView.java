package com.webforj.samples.views.tree;

import java.util.Arrays;

import com.webforj.component.Composite;
import com.webforj.component.icons.TablerIcon;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.tree.Tree;
import com.webforj.component.tree.TreeNode;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@FrameTitle("Tree")
public class TreeView extends Composite<FlexLayout> {

  FlexLayout self = getBoundComponent();
  Tree tree = new Tree();

  public TreeView() {
    self.setStyle("margin", "1em auto")
        .setPadding("1em");
    tree.setConnected(false);

    // Root node
    TreeNode companyNode = Tree.node("Company").setIcon(TablerIcon.create("building-skyscraper"));
    
    // Internal nodes
    TreeNode engineeringNode = createNode("Engineering", "settings");
    TreeNode frontEndNode = createNode("Front End", "code-circle-2");
    TreeNode backEndNode = createNode("Back End", "cloud-computing");
    TreeNode marketingNode = createNode("Marketing", "graph");
    TreeNode billingNode = createNode("Billing", "credit-card");

    // Leaf nodes
    frontEndNode.add(createEmployee("Alive", "Bob"));
    backEndNode.add(createEmployee("Eve", "Frank", "Gina"));
    marketingNode.add(createEmployee("Dave", "Carol"));
    billingNode.add(createEmployee("Grace"));

    engineeringNode.add(frontEndNode, backEndNode);
    companyNode.add(engineeringNode, marketingNode, billingNode);

    tree.add(companyNode);
    tree.expandFrom(companyNode);
    self.add(tree);
  }

  private TreeNode[] createEmployee(String... names) {
    return Arrays.stream(names)
        .map(name -> Tree.node(name)
            .setIcon(TablerIcon.create("user"))
            .setSelectedIcon(TablerIcon.create("user", TablerIcon.Variate.FILLED)))
        .toArray(TreeNode[]::new);
  }

  private TreeNode createNode(String name, String iconName) {
    return Tree.node(name).setIcon(TablerIcon.create(iconName))
        .setSelectedIcon(TablerIcon.create(iconName, TablerIcon.Variate.FILLED));
  }
}
