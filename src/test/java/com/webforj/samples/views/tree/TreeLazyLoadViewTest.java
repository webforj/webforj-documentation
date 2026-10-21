package com.webforj.samples.views.tree;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.webforj.automation.act.UserInteraction;
import com.webforj.automation.act.component.tree.TreeInteraction;
import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.tree.Tree;
import com.webforj.component.tree.TreeNode;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@WebforjTest
class TreeLazyLoadViewTest {
  @Test
  void shouldReplaceTheSpinnerWithTheLoadedChildrenWhenTheUserExpandsANode(WebforjTestbed context) {
    context.add(new TreeLazyLoadView());

    var tree = ComponentLocator.of(Tree.class).locate();
    var first = Tree.findByKey(tree.getRoot(), "Node 1").orElseThrow();
    var second = Tree.findByKey(tree.getRoot(), "Node 2").orElseThrow();

    UserInteraction.perform(tree, TreeInteraction.expand(first));
    assertEquals(Boolean.TRUE, first.getChildren().get(0).getUserData("spinner"));

    context.runIntervals();

    assertEquals(
        List.of("Node 1 - Child 1", "Node 1 - Child 2", "Node 1 - Child 3"),
        first.getChildren().stream().map(TreeNode::getText).toList());
    assertEquals(Boolean.TRUE, second.getChildren().get(0).getUserData("spinner"));
  }

  @Test
  void shouldLoadTheChildrenOnlyOnTheFirstExpansion(WebforjTestbed context) {
    context.add(new TreeLazyLoadView());

    var tree = ComponentLocator.of(Tree.class).locate();
    var first = Tree.findByKey(tree.getRoot(), "Node 1").orElseThrow();

    UserInteraction.perform(tree, TreeInteraction.expand(first));
    context.runIntervals();
    UserInteraction.perform(tree, TreeInteraction.collapse(first));
    UserInteraction.perform(tree, TreeInteraction.expand(first));
    context.runIntervals();

    assertEquals(
        List.of("Node 1 - Child 1", "Node 1 - Child 2", "Node 1 - Child 3"),
        first.getChildren().stream().map(TreeNode::getText).toList());
  }
}
